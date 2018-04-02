package openCVHarness;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;

import org.opencv.core.Mat;

import miscellaneous.Helper;
import miscellaneous.OperationMenuItem;
import operations.OpenCVOperation;
import operations.OpenCVOperation.OpenCVOperationTransferable;
import passableTypes.IOData;
import passableTypes.IOData.IOType;

public class ImagePanel {

    OpenCVOperation inputOperation = null;
    IOData.ImageMat inputImage = null;
    ImagePanelOrganizer parentOrganizer;
    DrawingPanel drawingPanel;

    public ImagePanel() {
        super();
        drawingPanel = new DrawingPanel();
        drawingPanel.setBorder( new LineBorder(Color.BLACK));
        drawingPanel.addMouseListener(new ImagePanelMouseListener());
        drawingPanel.setTransferHandler(new ImagePanelTransferHandler());
    }

    public void setParentOrganizer(ImagePanelOrganizer parentOrganizer) {
        this.parentOrganizer = parentOrganizer;
    }

    public JPanel getDrawingPanel(){
        return drawingPanel;
    }

    public void repaint() {
        drawingPanel.repaint();
    }

    public void revalidate() {
        drawingPanel.revalidate();
    }

    //TODO: Improve the mat handling of this function. What do we have to consider when converting CV types??
    static private BufferedImage matToBufferedImage(Mat imgMat) {
        int type = 0;
        Mat matCopy = new Mat();
        imgMat.copyTo(matCopy);
        if (matCopy.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (matCopy.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else if (matCopy.channels() == 4) {
            type = BufferedImage.TYPE_4BYTE_ABGR;
        }

        if(matCopy.type() == 5) {
            matCopy.convertTo(matCopy, org.opencv.core.CvType.CV_8U, 1);
        }

        BufferedImage image = new BufferedImage(matCopy.width(), matCopy.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        matCopy.get(0, 0, data);

        return image;
    }

    private JMenu createOperationsJMenu() {
        JMenu newMenu = new JMenu("Select Viewer Input");

        ArrayList<IOData.ImageMat> imageMatList = 
                Helper.getWebcamHarnessWindow().getListManager().getIODataArrayList( IOData.ImageMat.class, IOType.OUTPUT );
        for(IOData.ImageMat imageMat : imageMatList) {
            OperationMenuItem newMenuItem = new OperationMenuItem(imageMat);
            newMenu.add(newMenuItem);
            newMenuItem.setText(imageMat.getName());

            //Add a listener to the newMenuItem that will set the inputOperation of this ImagePanel to the selected operation.
            newMenuItem.addActionListener( e -> {
                inputImage = newMenuItem.getImageMat();
                this.revalidate();
                this.repaint();
            });
        }

        return newMenu;
    }

    private class ImagePanelMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON3) {
                //Create new popup menu.
                JPopupMenu jpm = new JPopupMenu();
                //Create a submenu for choosing an operation to be drawn.
                JMenu submenu = createOperationsJMenu();
                if(submenu.getItemCount() == 0) {
                    submenu.add( new JMenuItem("No Operations Available"));
                }
                jpm.add(submenu);

                //Add a remove panel option to the popup menu.
                JMenuItem jmi = new JMenuItem("Remove Panel");
                jmi.addActionListener( evt -> parentOrganizer.removePanel(ImagePanel.this));
                jpm.add(jmi);

                jpm.show( e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private class DrawingPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            BufferedImage image;

            //Set the background color of the imagePanel.
            if(inputImage != null) {
                drawingPanel.setBackground(new Color( 238, 238, 238));
            } else {
                drawingPanel.setBackground(new Color( 255, 245, 245));
                return;
            }

            //Convert the Mat of the inputOperation to a bufferedImage.
            if( inputImage != null && !inputImage.getData().empty() && inputImage.getData().width() > 0 && inputImage.getData().height() > 0) {
                image = ImagePanel.matToBufferedImage(inputImage.getData());
                drawingPanel.setBackground(new Color( 238, 238, 238));
            } else {
                return;
            }

            //Create a graphics2D object for drawing the image.
            Graphics2D g2d = (Graphics2D) g.create();
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            if(image != null) {
                if(panelWidth >= imageWidth && panelHeight >= imageHeight ) {
                    //If image panel is greater in width and height then draw the regular image since it will fit.
                    g2d.drawImage( image, 0, 0, null);
                } else if (panelWidth < imageWidth && panelHeight >= imageHeight) {
                    //If the panelWidth is less than the imageWidth, we need to scale the image. To preserve the aspect ratio we can use the ratio of the panelwidth:imagewidth.
                    double scaleFactor = (double)((double)panelWidth/imageWidth);
                    g2d.drawImage(image, 0, 0, panelWidth, (int)(imageHeight*scaleFactor), 0, 0, imageWidth, imageHeight, null);
                } else if (panelWidth >= imageWidth && panelHeight < imageHeight) {
                    //If the panelHeight is less than the imageHeight, we need to scale the image. To preserve the aspect ratio we can use the ratio of the panelHeight:imageHeight.
                    double scaleFactor = (double)((double)panelHeight/(double)imageHeight);
                    g2d.drawImage(image, 0, 0, (int)(imageWidth*scaleFactor), panelHeight, 0, 0, imageWidth, imageHeight, null);
                } else { //panelWidth < imageWidth && panelHeight < imageHeight
                    if((double)imageWidth/panelWidth > (double)imageHeight/panelHeight) {
                        double scaleFactor = (double)imageWidth/panelWidth;
                        g2d.drawImage(image, 0, 0, panelWidth, (int)(imageHeight/scaleFactor), 0, 0, imageWidth, imageHeight, null);
                    } else {
                        double scaleFactor = (double)imageHeight/panelHeight;
                        g2d.drawImage(image, 0, 0, (int)(imageWidth/scaleFactor), panelHeight, 0, 0, imageWidth, imageHeight, null);
                    }
                }
            } 

            g2d.dispose();
        }
    }

    private class ImagePanelTransferHandler extends TransferHandler {

        /** Generated serial id. */
        private static final long serialVersionUID = 2680504291208009913L;

        public ImagePanelTransferHandler() {
            super();
        }

        @Override
        public boolean canImport(TransferSupport transferSupport) {
            // we only import OpenCVOperations
            if (!transferSupport.isDataFlavorSupported(OpenCVOperation.OpenCVOperationTransferable.OPENCV_OPERATION_DATA_FLAVOR)) {
                return false;
            }
            return true;
        }

        @Override
        public boolean importData( TransferSupport transferSupport) {
            if(canImport( transferSupport )) {
                try {
                    long droppedOperationID = ((OpenCVOperation)transferSupport.getTransferable().getTransferData( OpenCVOperationTransferable.OPENCV_OPERATION_DATA_FLAVOR)).getID();
                    ArrayList<OpenCVOperation> operationsList = Helper.getWebcamHarnessWindow().getListManager().getOperationsArrayList();
                    for(OpenCVOperation op : operationsList) {
                        if(op.getID() == droppedOperationID) {
                            System.out.println("importing image panel data");
                            ImagePanel.this.inputOperation = op;
                        }
                    }
                } catch (UnsupportedFlavorException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            Transferable transferable = null;
            if (c instanceof JList) {
                @SuppressWarnings("unchecked")
                JList<OpenCVOperation> list = (JList<OpenCVOperation>) c;
                Object value = list.getSelectedValue();
                if (value instanceof OpenCVOperation) {
                    OpenCVOperation operation = (OpenCVOperation) value;
                    transferable = operation.getTransferable(list.getSelectedIndex());
                }
            }
            return transferable;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
        }
    }

}

package openCVHarness;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.DefaultListModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;

import org.opencv.core.Mat;

import miscellaneous.Helper;
import operations.OpenCVOperation;
import operations.OperationMenuItem;


public class ImagePanel {

	OpenCVOperation inputOperation = null;
	ImagePanelOrganizer parentOrganizer;
	DrawingPanel drawingPanel;
	
	public ImagePanel( ) {
		super();
		drawingPanel = new DrawingPanel();
		drawingPanel.setBorder( new LineBorder(Color.BLACK));
		drawingPanel.addMouseListener(new ImagePanelMouseListener());
	}
	
	public void setParentOrganizer( ImagePanelOrganizer parentOrganizer ) {
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
	    
	    if( matCopy.type() == 5 ) {
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
    	
    	DefaultListModel<OpenCVOperation> operationsList = Helper.getWebcamHarnessWindow().getOperationsList();
    	for( int i = 0; i < operationsList.getSize(); i++ ) {
			OpenCVOperation selectedOperation = operationsList.getElementAt( i );
			OperationMenuItem newMenuItem = new OperationMenuItem( selectedOperation );
			newMenu.add(newMenuItem);
			newMenuItem.setText(selectedOperation.getOutputName());
			
			//Add a listener to the newMenuItem that will set the inputOperation of this ImagePanel to the selected operation.
			newMenuItem.addActionListener( e -> {
				inputOperation = newMenuItem.getOpenCVOperation();
				this.revalidate();
				this.repaint();
			});
    	}
    	
    	return newMenu;
    }
    
    private class ImagePanelMouseListener extends MouseAdapter {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		if( e.getButton() == MouseEvent.BUTTON3 ) {
    			//Create new popup menu.
    			JPopupMenu jpm = new JPopupMenu();
    			//Create a submenu for choosing an operation to be drawn.
    			JMenu submenu = createOperationsJMenu();
    			if( submenu.getItemCount() == 0 ) {
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
    
    private class DrawingPanel extends JPanel
    {
		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            
            BufferedImage image;
            
            //Set the background color of the imagePanel.
            if( inputOperation != null ) {
				drawingPanel.setBackground( new Color( 238, 238, 238 ));
            } else {
				drawingPanel.setBackground( new Color( 255, 245, 245 ));
            	return;
            }
            
            //Convert the Mat of the inputOperation to a bufferedImage.
    		if( inputOperation.getOutputMat().width() > 0 && inputOperation.getOutputMat().height() > 0 ) {
    			image = ImagePanel.matToBufferedImage( inputOperation.getOutputMat() );
    			drawingPanel.setBackground( new Color( 238, 238, 238 ));
    		} else {
    			return;
    		}
    		
    		//Create a graphics2D object for drawing the image.
            Graphics2D g2d = (Graphics2D) g.create();
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
        	int imageWidth = image.getWidth();
        	int imageHeight = image.getHeight();

            if( image != null ) {
            	if( panelWidth >= imageWidth && panelHeight >= imageHeight  ) {
                	//If image panel is greater in width and height then draw the regular image since it will fit.
            		g2d.drawImage( image, 0, 0, null);
            	} else if ( panelWidth < imageWidth && panelHeight >= imageHeight ) {
            		//If the panelWidth is less than the imageWidth, we need to scale the image. To preserve the aspect ratio we can use the ratio of the panelwidth:imagewidth.
            		double scaleFactor = (double)((double)panelWidth/imageWidth);
                	g2d.drawImage(image, 0, 0, panelWidth, (int)(imageHeight*scaleFactor), 0, 0, imageWidth, imageHeight, null);
            	} else if ( panelWidth >= imageWidth && panelHeight < imageHeight ) {
            		//If the panelHeight is less than the imageHeight, we need to scale the image. To preserve the aspect ratio we can use the ratio of the panelHeight:imageHeight.
            		double scaleFactor = (double)((double)panelHeight/(double)imageHeight);
                	g2d.drawImage(image, 0, 0, (int)(imageWidth*scaleFactor), panelHeight, 0, 0, imageWidth, imageHeight, null);
            	} else { //panelWidth < imageWidth && panelHeight < imageHeight
            		if( (double)imageWidth/panelWidth > (double)imageHeight/panelHeight ) {
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
}

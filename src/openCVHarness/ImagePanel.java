package openCVHarness;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.opencv.core.Mat;

import miscellaneous.Helper;
import openCVOperations.OpenCVOperation;
import openCVOperations.OperationMenuItem;


public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	OpenCVOperation viewerInput = null;
	ImagePanelOrganizer parentOrganizer;
	
	public ImagePanel( ) {
		super();
		this.setBorder( BorderFactory.createLineBorder(Color.black));
		this.addMouseListener( new ImagePanelMouseListener());
	}
	
	public void setParentOrganizer( ImagePanelOrganizer parentOrganizer ) {
		this.parentOrganizer = parentOrganizer;
	}
	
	private BufferedImage getInputImage()
	{
		if( viewerInput != null && viewerInput.getOutputMat().width() > 0 && viewerInput.getOutputMat().height() > 0 )
			return matToBufferedImage( viewerInput.getOutputMat() );
		return null;
	}
	
	//TODO: Improve the mat handling of this function. What do we have to consider when converting CV types??
	static BufferedImage matToBufferedImage(Mat imgMat) {
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
	    
	    if( matCopy.type() == 5 )
	    {
	    	matCopy.convertTo(matCopy, org.opencv.core.CvType.CV_8U, 1);
	    }
	    
	    BufferedImage image = new BufferedImage(matCopy.width(), matCopy.height(), type);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    matCopy.get(0, 0, data);
	
	    return image;
	}
	
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        
        BufferedImage image = getInputImage();

        //TODO: Add code to enable/disable aspect ratio scaling based on an interface setting.
        if( image != null ) {
        	int imageWidth = image.getWidth();
        	int imageHeight = image.getHeight();
        	if( panelWidth > imageWidth && panelHeight > imageWidth  ) {
            	//If image panel is greater in width and height then draw the regular image since it will fit.
        		g2d.drawImage( image, 0, 0, null);
        	} else if ( panelWidth < imageWidth && panelHeight > imageHeight ) {
        		//If the panelwidth is less than the imagewidth, we need to scale the image. To preserve the aspect ratio we can use the ratio of the panelwidth:imagewidth.
        		double scaleFactor = panelWidth/imageWidth;
            	g2d.drawImage(image, 0, 0, panelWidth, (int)(imageHeight*scaleFactor), 0, 0, imageWidth, imageHeight, null);
        	} else if ( panelWidth > imageWidth && panelHeight < imageHeight ) {
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
    
    private JMenu createOperationsJMenu()
    {
    	JMenu newMenu = new JMenu("Select Viewer Input");
    	
    	DefaultListModel<OpenCVOperation> operationsList = Helper.getWebcamHarnessWindow().getOperationsList();
    	for( int i = 0; i < operationsList.getSize(); i++ )
    	{
			OpenCVOperation selectedOperation = operationsList.getElementAt( i );
			OperationMenuItem newMenuItem = new OperationMenuItem( selectedOperation );
			newMenu.add(newMenuItem);
			newMenuItem.setText(selectedOperation.getOutputName());
			
			//Add a listener to the operationmenuitem that will set the inputoperation of the right clicked operation to the selected operationmenuitem.
			newMenuItem.addActionListener( e -> {
				viewerInput = newMenuItem.getOpenCVOperation();
				this.revalidate();
				this.repaint();
			});
    	}
    	
    	return newMenu;
    }
    
    private class ImagePanelMouseListener extends MouseAdapter
    {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		if( e.getButton() == MouseEvent.BUTTON3 )
    		{
    			JPopupMenu jpm = new JPopupMenu();
    			JMenu submenu = createOperationsJMenu();
    			if( submenu.getItemCount() == 0 ) {
    				submenu.add( new JMenuItem("No Operations Available"));
    			}
    			jpm.add(submenu);
    			
    			{
        			JMenuItem jmi = new JMenuItem("Test item.");
        			jmi.addActionListener( evt -> parentOrganizer.removePanel(ImagePanel.this));
        			jpm.add(jmi);
    			}
    			
	    		jpm.show( e.getComponent(), e.getX(), e.getY());
    		}
    	}
    }
}

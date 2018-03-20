package openCVHarness;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	BufferedImage image;

	public void drawImage( BufferedImage image )
	{
		this.image = image;
		this.revalidate();
		this.repaint();
	}
	
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();

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
        		//TODO:Add code to handle scaling and maintaining aspect ratio.
        		System.out.println("Draw case four");
            	g2d.drawImage(image, 0, 0, panelWidth, panelHeight, 0, 0, imageWidth, imageHeight, null);
        	}
        }
		
        g2d.dispose();
    }
}

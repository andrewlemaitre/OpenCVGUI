package openCVHarness;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class ImagePanelOrganizer {
	
	JPanel imagePanelHolder;
	
	public ImagePanelOrganizer() {
		imagePanelHolder = new JPanel();
		imagePanelHolder.setBorder( new LineBorder(Color.RED));
		migLayout = new MigLayout("", "[grow]", "[grow]");
		imagePanelHolder.setLayout( migLayout );
	}

	ArrayList<ImagePanel> panelList = new ArrayList<>();
	MigLayout migLayout;

	public List<ImagePanel> getPanels() {
		return panelList;
	}
	
	public void removePanel( ImagePanel imagePanel )
	{
		panelList.remove( imagePanel );
		imagePanelHolder.remove(imagePanel.getDrawingPanel());
		this.organizePanels();
		imagePanelHolder.revalidate();
		imagePanelHolder.repaint();
	}
	
	public JPanel getOrganizerPanel(){
		return imagePanelHolder;
	}
	
	public void createNewImagePanel() {
	    //Create new image panel and set its parent organizer to this image panel organizer.
	    ImagePanel imagePanel = new ImagePanel();
        imagePanel.setParentOrganizer( this );
        
        //Add the new image panel to the panels list.
        panelList.add( imagePanel );
        
        //Add the new image panel to the image panels holder and the mig layout.
	    imagePanelHolder.add(imagePanel.getDrawingPanel());
	    migLayout.addLayoutComponent(imagePanel.getDrawingPanel(), "");
	    
	    //Organize the panels since we added a new one.
	    organizePanels();
	}
	
	public void addImagePanel( ImagePanel imagePanel )
	{
        //Set the image panel's parent organizer to this image panel organizer.
		imagePanel.setParentOrganizer( this );
		
	    //Add the new image panel to the panels list.
		panelList.add( imagePanel );

        //Add the new image panel to the image panels holder and the mig layout.
        imagePanelHolder.add(imagePanel.getDrawingPanel());
		migLayout.addLayoutComponent(imagePanel.getDrawingPanel(), "");
		
		//Organize the panels since we added a new one.
		organizePanels();
	}
	
	public ArrayList<ImagePanel> getImagePanels(){
		return panelList;
	}
	
	public void organizePanels()
	{
		int squareSize = (int)Math.ceil( Math.sqrt(panelList.size()));
		
		String rowColConstraints = "";
		
		for( int i = 0; i < squareSize; i++ ) {
			rowColConstraints = rowColConstraints + "[grow]";
		}
		migLayout.setColumnConstraints(rowColConstraints);
		migLayout.setRowConstraints(rowColConstraints);
		
		for( int i = 0; i < squareSize; i++ ) {
			for( int j = 0; j < squareSize && i*squareSize + j < panelList.size(); j++ ) {
				String compConstraints = String.format("cell %d %d,grow", j, i);
				migLayout.setComponentConstraints(panelList.get(i*squareSize+j).getDrawingPanel(), compConstraints);
			}
		}
		imagePanelHolder.revalidate();
		imagePanelHolder.repaint();
	}
}

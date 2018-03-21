package openCVHarness;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class ImagePanelOrganizer extends JPanel {
	
	public ImagePanelOrganizer() {
		this.setBorder( new LineBorder(Color.RED));
		migLayout = new MigLayout("", "[grow]", "[grow]");
		setLayout( migLayout );
	}
	
	private static final long serialVersionUID = 1L;

	ArrayList<ImagePanel> panelList = new ArrayList<>();
	MigLayout migLayout;

	public List<ImagePanel> getPanels() {
		return panelList;
	}
	
	public void removePanel( ImagePanel imagePanel )
	{
		panelList.remove( imagePanel );
		this.remove(imagePanel);
		this.organizePanels();
		this.revalidate();
		this.repaint();
	}
	
	public void addPanel( ImagePanel imagePanel )
	{
		add(imagePanel, "cell 0 0,grow");
		panelList.add( imagePanel );
		migLayout.addLayoutComponent(imagePanel, "");
		imagePanel.setParentOrganizer( this );
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
				migLayout.setComponentConstraints(panelList.get(i*squareSize+j), compConstraints);
			}
		}
		revalidate();
		repaint();
	}
}

package openCVHarness;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import dialogs.NewOperationDialog;
import miscellaneous.Helper;
import operations.OpenCVOperation;
import operations.OperationMenuItem;

import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import javax.swing.Action;

@SuppressWarnings("serial")
public class OpenCVHarnessWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OpenCVHarness webcamHarness;
	ImagePanel imagePanel;
	ImagePanelOrganizer imagePanelOrganizer;
	JPanel operationsListPanel;
	JList<OpenCVOperation> imageOperationsJList;
	
	BufferedImage image;
	DefaultListModel<OpenCVOperation> operationsList;

	private final Action runOperationsAction = new RunOperationsAction();
	private final Action newOperationAction = new NewOperationAction();
	private final Action removeOperationAction = new RemoveOperationAction();
	private final Action copyOperationAction = new CopyOperationAction();
	private final Action editOperationAction = new EditOperationAction();
	private final Action newImagePanelAction = new NewImagePanelAction();
	 	
	public OpenCVHarnessWindow( OpenCVHarness webcamHarness ) {
		this.webcamHarness = webcamHarness;
		Helper.setWebcamHarnessWindow(this);
		initialize();
	}
	
	void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Image Frame");
		this.setSize(1024,768);
		this.setLocation(50, 50);
		this.setVisible(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel viewerPanel = new JPanel();
		getContentPane().add(viewerPanel, BorderLayout.CENTER);
		viewerPanel.setLayout(new BorderLayout(0, 0));
		
		imagePanelOrganizer = new ImagePanelOrganizer();
		viewerPanel.add(imagePanelOrganizer.getOrganizerPanel());

		imagePanel = new ImagePanel();
		imagePanelOrganizer.addPanel(imagePanel);
		JPanel viewerControlsPanel = new JPanel();
		viewerPanel.add(viewerControlsPanel, BorderLayout.SOUTH);
		viewerControlsPanel.setLayout(new BoxLayout(viewerControlsPanel, BoxLayout.X_AXIS));
		 
		JButton runOperationsButton = new JButton();
		runOperationsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		runOperationsButton.setAction(runOperationsAction);
		viewerControlsPanel.add(runOperationsButton);
		
		JButton button = new JButton("New button");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAction(newImagePanelAction);
		viewerControlsPanel.add(button);
		
		JPanel operationsPanel = new JPanel();
		getContentPane().add(operationsPanel, BorderLayout.EAST);
		
		operationsListPanel = new JPanel();
		operationsPanel.add(operationsListPanel);
		operationsListPanel.setLayout( new BoxLayout(operationsListPanel, BoxLayout.PAGE_AXIS));

		operationsList = new DefaultListModel<>();
		imageOperationsJList = new JList<>( operationsList );
		imageOperationsJList.addMouseListener( new ImageOperationsListListener() );
		imageOperationsJList.setCellRenderer( new ImageOperationsCellRenderer() );
		JScrollPane operationsListScrollPane = new JScrollPane(imageOperationsJList);
		operationsListScrollPane.setPreferredSize( new Dimension(368, 240));
		operationsListPanel.add(operationsListScrollPane);
		
		JPanel operationsListButtonPanel = new JPanel();
		operationsListButtonPanel.setLayout( new BoxLayout(operationsListButtonPanel, BoxLayout.LINE_AXIS));
		operationsListPanel.add(operationsListButtonPanel);
		
				JButton newOperationButton = new JButton();
				newOperationButton.setAction(newOperationAction);
				operationsListButtonPanel.add(newOperationButton);
				JButton CopyOperationButton = new JButton();
				CopyOperationButton.setAction(copyOperationAction);
				operationsListButtonPanel.add(CopyOperationButton);
				JButton RemoveOperationButton = new JButton();
				RemoveOperationButton.setAction(removeOperationAction);
				operationsListButtonPanel.add(RemoveOperationButton);
				JButton editOperationButton = new JButton();
				editOperationButton.setAction(editOperationAction);
				operationsListButtonPanel.add(editOperationButton);
		
		JPanel menuBarPanel = new JPanel();
		getContentPane().add(menuBarPanel, BorderLayout.NORTH);
		menuBarPanel.setLayout(new BoxLayout(menuBarPanel, BoxLayout.X_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		menuBarPanel.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
//		editOperationButton .addActionListener( new EditButtonListener());

	}
	
	public JList<OpenCVOperation> getImageOperationsJList() {
		return imageOperationsJList;
	}

	public DefaultListModel<OpenCVOperation> getOperationsList() {
		return operationsList;
	}
	
	void refreshMainView() {
		this.revalidate();
		this.repaint();
	}
	
	void editOperation() {
		OpenCVOperation selectedOperation = imageOperationsJList.getSelectedValue();
		if( selectedOperation != null ) {
			JDialog dialog = selectedOperation.openDialogBox( );
			
			dialog.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosed(WindowEvent e) {
			        refreshMainView();
			    }
			});
			
			dialog.setLocationRelativeTo( this );
			dialog.setVisible(true);
		} else {
			System.err.println("Edit operation failed because no operation is selected");
		}
	}

	public void newOperation( OpenCVOperation op ) {
		operationsList.addElement(op);
	}
	
	void newOperation() {
		NewOperationDialog nod = new NewOperationDialog( this );
		nod.setLocationRelativeTo(this);
		nod.setVisible(true);
	}
	
	void copyOperation() {
		if( imageOperationsJList.getSelectedIndex() >= 0 && operationsList.getSize() > 0)
		{
			OpenCVOperation operationToCopy = operationsList.get(imageOperationsJList.getSelectedIndex());
			newOperation( operationToCopy.newOperationCopy() );
		}
	}
	
	void runOperations() {
		if( operationsList.size() == 0 ) {
			System.err.println("There are no operations to run.");
			return;
		}
		for( int i = 0; i < operationsList.size(); i++ )
		{
			operationsList.getElementAt(i).performOperation();
		}

		if( operationsList.getElementAt( operationsList.getSize()-1 ).getOutputMat().empty() )
		{
			System.err.println("Attempted to draw empty mat.");
			return;
		}
		for( ImagePanel ip : imagePanelOrganizer.getImagePanels() )
		{
			ip.revalidate();
			ip.repaint();
		}
	}
	
	void removeSelectedElement() {
		if( imageOperationsJList.getSelectedIndex() >= 0 && operationsList.getSize() > 0)
			operationsList.removeElementAt( this.imageOperationsJList.getSelectedIndex() );
	}

	private class RunOperationsAction extends AbstractAction {
		public RunOperationsAction() {
			putValue(NAME, "Run Operations");
			putValue(SHORT_DESCRIPTION, "Performs all of the operations in the operations list.");
		}
		public void actionPerformed(ActionEvent e) {
			runOperations();
		}
	}
	
	private class NewOperationAction extends AbstractAction {
		public NewOperationAction() {
			putValue(NAME, "New Operation");
			putValue(SHORT_DESCRIPTION, "Opens a dialog to create new operations.");
		}
		public void actionPerformed(ActionEvent e) {
			newOperation();
		}
	}
	
	private class EditOperationAction extends AbstractAction {
		public EditOperationAction() {
			putValue(NAME, "Edit");
			putValue(SHORT_DESCRIPTION, "Edit the settings of the selected operation.");
		}
		public void actionPerformed(ActionEvent e) {
			OpenCVOperation selectedOperation = imageOperationsJList.getSelectedValue();
			if( selectedOperation != null ) {
				JDialog dialog = selectedOperation.openDialogBox( );
				
				dialog.addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosed(WindowEvent e) {
				        refreshMainView();
				    }
				});
				
				dialog.setLocationRelativeTo( Helper.getWebcamHarnessWindow() );
				dialog.setVisible(true);
				dialog.pack();
			} else {
				System.err.println("Edit operation failed because no operation is selected");
			}
		}
	}
	
	private class RemoveOperationAction extends AbstractAction {
		public RemoveOperationAction() {
			putValue(NAME, "Remove");
			putValue(SHORT_DESCRIPTION, "Remove the topmost selected operation.");
		}
		public void actionPerformed(ActionEvent e) {
			removeSelectedElement();
		}
	}
	
	private class CopyOperationAction extends AbstractAction {
		public CopyOperationAction() {
			putValue(NAME, "Copy");
			putValue(SHORT_DESCRIPTION, "This button will create a new version of the currently selected operation.");
		}
		public void actionPerformed(ActionEvent e) {
			copyOperation();
		}
	}
	
	private class NewImagePanelAction extends AbstractAction {
		public NewImagePanelAction() {
			putValue( NAME, "New Image Panel");
			putValue( SHORT_DESCRIPTION, "This button will create a new image viewer for viewing the output of an operation.");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ImagePanel ip = new ImagePanel();
			imagePanelOrganizer.addPanel( ip );
		}
	}
	
    private class ImageOperationsCellRenderer extends JLabel implements ListCellRenderer<OpenCVOperation> {
        public ImageOperationsCellRenderer() {
            setOpaque(true);
        }
        
		@Override
		public Component getListCellRendererComponent(JList<? extends OpenCVOperation> list, 
														OpenCVOperation value,
														int index, 
														boolean isSelected, 
														boolean cellHasFocus) {
			setText( value.getOperationName() );
			
			Color background;
			Color foreground;
			
			if( isSelected ) {
				background = new Color(184,207,229);
				foreground = Color.BLACK;
				this.setBorder( new LineBorder( new Color( 99, 130, 191 )));
			} else {
				if( value.isValid() ) {
					background = Color.GREEN;
					foreground = Color.BLACK;
					this.setBorder( new LineBorder( Color.GREEN ));
				} else {
					background = Color.YELLOW;
					foreground = Color.BLACK;
					this.setBorder( new LineBorder( Color.YELLOW ));
				}
			}
			
			setBackground( background );
			setForeground( foreground );
			
			return this;
			
		}
    }
    
	//TODO: Make edit operations here and on the button call a editOperation( int i ) function so we have a common code base for creating edit dialogs, showing them and packing them.
	private class ImageOperationsListListener extends MouseAdapter {
		@Override
	    public void mouseClicked(MouseEvent evt) {
			
	        JList<?> list = (JList<?>)evt.getSource();
	        
	        //If the click count is equal to two and we have clicked the left mouse button.
	        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 ) {
	            int index = list.locationToIndex(evt.getPoint());
	            if( index >= 0 ) {
	            	JDialog odb = operationsList.getElementAt(index).openDialogBox();
	            	odb.pack();
	            	java.awt.Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
	            	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	            	int x, y;
	            	
	            	if( mouseLocation.x + odb.getWidth() > screenSize.getWidth() ) {
	            		x = (int)screenSize.getWidth()-odb.getWidth();
	            	} else {
	            		x = mouseLocation.x;
	            	}
	            	
	            	if( mouseLocation.y + odb.getHeight() > screenSize.getHeight() ) {
	            		y = (int)screenSize.getHeight()-odb.getHeight();
	            	} else {
	            		y = mouseLocation.y;
	            	}
	            		odb.setLocation( x, y );
	            		
	            	odb.setVisible(true);
	            }
	        } else if ( list.locationToIndex(evt.getPoint()) >= 0 && evt.getButton() == MouseEvent.BUTTON3 ) {
	        	int selectedIndex = list.locationToIndex(evt.getPoint());
	    		
	    		JPopupMenu jpm = new JPopupMenu();
	    		JMenu inputOperationsMenu = getInputOperationsForPopupMenu(selectedIndex);
	    		jpm.add(inputOperationsMenu);
	    		
	    		jpm.show( evt.getComponent(), evt.getX(), evt.getY());
	        }
	        
	    }
	}
    
	private JMenu getInputOperationsForPopupMenu( int selectedIndex )
    {
    	//Create a new JMenu
    	JMenu inputOperationsMenu = new JMenu("Input Operation");
    	
		for( int i = 0; i < selectedIndex; i++ ) {
			//Get each operation up to the selected index (The element that we right clicked in the JList)
			//Create a new OperationMenu item for each operation, add it to the JMenu and set its text to the operation output name.
			OpenCVOperation selectedOperation = operationsList.getElementAt( i );
			JMenuItem newMenuItem = new OperationMenuItem( selectedOperation );
			inputOperationsMenu.add(newMenuItem);
			newMenuItem.setText(selectedOperation.getOutputName());
			
			//Add a listener to the operationmenuitem that will set the inputoperation of the right clicked operation to the selected operationmenuitem.
			newMenuItem.addActionListener( e -> {
				operationsList.getElementAt( selectedIndex ).setInputOperation( ((OperationMenuItem)newMenuItem).getOpenCVOperation() );
			});
		}
		return inputOperationsMenu;
    }

}

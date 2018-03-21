package dialogs;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import net.miginfocom.swing.MigLayout;
import openCVOperations.OpenCVOperation;
import listeners.*;
import miscellaneous.IntFlagItem;
import miscellaneous.IntFlagMenuItem;
import passableTypes.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;


public class OperationDialogBox extends JDialog {

	private static final long serialVersionUID = 1L;
	private int elementCount = 0;
	JPanel settingsPanel;
	MigLayout migLayout = new MigLayout("", "[right][grow]", "");
	
	public OperationDialogBox( )
	{
		this.setSize(600,300);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);
		
		settingsPanel = new JPanel();
		scrollPane.setViewportView(settingsPanel);
		settingsPanel.setLayout(migLayout);

	}
	
	public void repack()
	{
		this.setSize(600,300);
		this.pack();
	}
	
	public void addFileChooser( String label, String fileFilterLabel, PassableFile passableFile, String... validExtensions )
	{
		addMigRow();
		addSettingLabel( label );
		
		JButton btnChooseFile = new JButton("Choose File");
		addSettingControl( btnChooseFile, "flowx" );
		
		JLabel lblFileTitle = new JLabel();
		if( passableFile.getValue() != null ) {
			lblFileTitle.setText( passableFile.getValue().getPath() );
		} else {
			lblFileTitle.setText("No File Selected");
		}
		addSettingControl( lblFileTitle, "wmax 150px" );
		
		elementCount++;
		
		btnChooseFile.addActionListener( new FileChangeListener( passableFile ){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				
			    FileNameExtensionFilter filter = new FileNameExtensionFilter( fileFilterLabel, validExtensions );
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog( (JButton)e.getSource() );
			    
			    if(returnVal == JFileChooser.APPROVE_OPTION) {

			    	this.f.setValue( chooser.getSelectedFile() );
			    	lblFileTitle.setText(this.f.getValue().getPath());
			     }
				
			}
			
		});
		
	}
	
	public void addButton()
	{
		
	}
	
	public void addSourceMatSelector( String label, OpenCVOperation openCVOperation )
	{
		addMigRow();
		addSettingLabel( label );
		
		JButton button = new JButton();
		if( openCVOperation.getInputOperation() != null )	{
			button.setText(openCVOperation.getInputOperation().getOutputName());
		} else {
			button.setText("Select Input Operation.");
		}
		addSettingControl( button, "growx" );
		
		elementCount++;
		button.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				SrcMatSelectorDialog smsd = new SrcMatSelectorDialog( openCVOperation );
				smsd.addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosed(WindowEvent e) {
				        if( openCVOperation.getInputOperation() != null )
				        button.setText( openCVOperation.getInputOperation().getOutputName() );
				    }
				});
			}
		});
	}
	
	public void addComboBox( String label, IntFlagItem[] itemList, PassableIntFlagItem passableIntFlagItem )
	{
		addMigRow();
		addSettingLabel( label );
		
		JComboBox<IntFlagItem> comboBox = new JComboBox<>();
		addSettingControl(comboBox);
		
		elementCount++;

		for( IntFlagItem ifi : itemList )
		{
			comboBox.addItem( ifi );
		}
		
		//If we have already picked an intflagitem previously then we should show that in the combo box.
		if( passableIntFlagItem.getValue() != null ) {
			//Search for a matching intflagitem in the combobox list.
			for( int i = 0; i < comboBox.getItemCount(); i++ )
			{
				if( comboBox.getItemAt(i).equals(passableIntFlagItem.getValue()) )
				{
					comboBox.setSelectedIndex(i);
					break;
				}
			}
		}
		
		comboBox.setRenderer( new ComboBoxRenderer() );
		
		comboBox.addActionListener( new ComboBoxListener( passableIntFlagItem ){
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<IntFlagItem> comboBox = (JComboBox<IntFlagItem>)e.getSource();
				this.i.setValue( comboBox.getItemAt(comboBox.getSelectedIndex()) );
			}
		});
	}
	
	public void addPopUpMenu( String label, IntFlagItem intFlagItem, JPopupMenu popupMenu )
	{
		addMigRow();
		addSettingLabel( label );

		JButton popupMenuButton = new JButton();
		if( intFlagItem != null && intFlagItem.getName() != null ) {
			popupMenuButton.setText( intFlagItem.getName() + "," + intFlagItem.getValue() );
		} else {
			popupMenuButton.setText("Select");
		}
		addSettingControl(popupMenuButton);
		
		elementCount++;
		
		addPopup(popupMenuButton, popupMenu);
		menuRecursiveButtonAdd(popupMenu, popupMenuButton);

	}
	
	private void menuRecursiveButtonAdd( Component popupMenu, JButton popupMenuButton )
	{
		if( popupMenu instanceof JPopupMenu )
		{
			Component[] cc = ((JPopupMenu)popupMenu).getComponents();
			for( Component c : cc )
			{
				if( c instanceof JMenu ) {
					menuRecursiveButtonAdd( c, popupMenuButton );
				} else if ( c instanceof IntFlagMenuItem ) {
					((IntFlagMenuItem)c).setMenuButton(popupMenuButton);
				} else {
					System.err.println("Found unexpected type:" + c.getClass() + " in recursive search of JPopupMenu.");
				}
			}
		}
		
		if( popupMenu instanceof JMenu )
		{
			JMenu menu = ((JMenu)popupMenu);
			
			for( int i = 0; i < menu.getItemCount(); i++ ) {
				if( menu.getItem(i) instanceof JMenu ) {
					menuRecursiveButtonAdd( menu.getItem(i), popupMenuButton );
				}
				else if( menu.getItem(i) instanceof IntFlagMenuItem ) {
					((IntFlagMenuItem)menu.getItem(i)).setMenuButton(popupMenuButton);
				}
				else {
					System.err.println("Found unexpected type:" + menu.getItem(i).getClass() + " in recursive search of JMenu.");
				}
			}
		}
	}
	
	public void addRadioButtonGroup()
	{
		
	}
	
	public void addCheckboxes()
	{
		
	}
	
	public void addTextBox( String label, String initialText, PassableString passableString ) {
		addMigRow();
		addSettingLabel( label );
		
		JTextField textField;
		if( passableString != null && !passableString.getValue().equals("") ) {
			textField = new JTextField( passableString.getValue() );
		} else {
			textField = new JTextField( initialText );
		}
		addSettingControl( textField, "growx" );
		
		elementCount++;
		
		textField.getDocument().addDocumentListener( new DocumentChangeListener( passableString ){
			
	        @Override
	        public void insertUpdate(DocumentEvent e) {
				try {
					this.s.setValue(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
	        }

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					this.s.setValue(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void addSliderSetting( String label, int min, int max, int initialValue, PassableInt passableInt ) {
		addMigRow();
		addSettingLabel( label );
		
		JSlider slider = new JSlider( SwingConstants.HORIZONTAL, min, max, initialValue);
		addSettingControl(slider, "flowx");
		
		elementCount++;
		
		slider.addChangeListener( new SliderChangeListener( passableInt ){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				this.i.setValue( source.getValue() );
			}
		});
	}
	
	private class ComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ComboBoxRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list,
				Object value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {


			if( value instanceof IntFlagItem ) {
				setText( ((IntFlagItem)value).getName());
			} else {
				setText(value.toString());
			}


			Color background;
			Color foreground;

			// check if this cell represents the current DnD drop location
			JList.DropLocation dropLocation = list.getDropLocation();
			if (dropLocation != null
					&& !dropLocation.isInsert()
					&& dropLocation.getIndex() == index) {

				background = Color.BLACK;
				foreground = Color.RED;

				// check if this cell is selected
			} else if (isSelected) {
				background = new Color(163,184,204);
				foreground = Color.BLACK;

				// unselected, and not the DnD drop location
			} else {
				background = new Color( 240, 240, 240 );
				foreground = Color.BLACK;
			};

			setBackground(background);
			setForeground(foreground);

			return this;
		}
	}

	private void addSettingControl( Component component )
	{
		settingsPanel.add(component, String.format("cell 1 %d,growx", elementCount));
	}

	private void addSettingControl( Component component, String constraints )
	{
		settingsPanel.add(component, String.format("cell 1 %d,"+constraints, elementCount));
	}
	
	private void addMigRow() {
		String rowConstraints = (String) migLayout.getRowConstraints();
		rowConstraints = rowConstraints.concat("[]");
		migLayout.setRowConstraints(rowConstraints);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON1 ) {
					showMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3 ) {
					//showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	@SuppressWarnings("unused")
	private void addMigRow( String constraints ) {
		String rowConstraints = (String) migLayout.getRowConstraints();
		rowConstraints = rowConstraints.concat("[" + constraints + "]");
		migLayout.setRowConstraints(rowConstraints);
	}

	private void addSettingLabel( String text )	{
		JLabel newLabel = new JLabel(text);
		settingsPanel.add(newLabel, String.format("cell 0 %d", elementCount));
	}
}

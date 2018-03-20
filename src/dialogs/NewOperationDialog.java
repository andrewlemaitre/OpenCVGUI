package dialogs;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import openCVHarness.OpenCVHarnessWindow;
import openCVOperations.*;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;


public class NewOperationDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Action action = new SwingAction();
	JComboBox<OpenCVOperation> comboBox;
	OpenCVHarnessWindow whw;
	private final Action closeAction = new CloseAction();

	public NewOperationDialog( JFrame parent )
	{
		super(parent);
		whw = (OpenCVHarnessWindow)parent;
		setTitle("New Operation");
		setModal(true);
		this.setSize(599,258);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);
		
		JPanel panel_4 = new JPanel();
		scrollPane.setViewportView(panel_4);
		panel_4.setLayout(new MigLayout("", "[right][grow]", "[][]"));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_4.add(lblNewLabel, "cell 0 0");
		
		comboBox = new JComboBox<>();
		comboBox.setRenderer( new MyCellRenderer() );
		comboBox.addItem( new ImReadOperation() );
		comboBox.addItem( new CvtColorOperation() );
		comboBox.addItem( new ThresholdOperation() );
		comboBox.addItem( new DistanceTransformOperation() );
		panel_4.add(comboBox, "cell 1 0");
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.setAction(action);
		panel_4.add(btnNewButton, "cell 1 1");
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setAction(closeAction);
		panel_4.add(btnNewButton_1, "cell 1 1");
		
		this.pack();
	}
	
	void createOperation()
	{
		OpenCVOperation operation = comboBox.getItemAt(comboBox.getSelectedIndex());
//		 whw.newOperation( comboBox.getItemAt(comboBox.getSelectedIndex()).clone() );
		whw.newOperation( operation.newOperationCopy());
	}

	class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyCellRenderer() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList<?> list,
				Object value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {

			if( value instanceof OpenCVOperation ) {
				setText( ((OpenCVOperation)value).getOperationName());
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
				background = Color.WHITE;
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
	
	private class SwingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "Create");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			createOperation();
		}
	}
	private class CloseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public CloseAction() {
			putValue(NAME, "Close");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}

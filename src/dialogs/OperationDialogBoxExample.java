package dialogs;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JPopupMenu;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;


public class OperationDialogBoxExample extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public OperationDialogBoxExample(  )
	{
		this.setSize(599,258);
		this.setVisible(true);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);
		
		JPanel panel_4 = new JPanel();
		scrollPane.setViewportView(panel_4);
		panel_4.setLayout(new MigLayout("", "[right][grow]", "[][][grow][][grow][][][]"));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_4.add(lblNewLabel, "cell 0 0");
		
		JSlider slider = new JSlider();
		panel_4.add(slider, "cell 1 0,growx");
		
		JLabel lblExtraExtraLong = new JLabel("<html>Extra Extra Long Label</html>");
		panel_4.add(lblExtraExtraLong, "cell 0 1");
		
		JButton btnThisIsA = new JButton("THIS IS A BUTTON");
		panel_4.add(btnThisIsA, "cell 1 1,growx");
		
		JLabel lblNewLabel_1 = new JLabel("<html><body><p>AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA</p></body></html>");
		lblNewLabel_1.setMaximumSize( new Dimension( 100, 35));
		panel_4.add(lblNewLabel_1, "cell 0 2");
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("String1");
		comboBox.addItem("String2");
		comboBox.addItem("String3");
		comboBox.addItem("String4");
		panel_4.add(comboBox, "cell 1 2,growx");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel_4.add(lblNewLabel_2, "cell 0 3,aligny top");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton);
		panel_4.add(rdbtnNewRadioButton, "flowy,cell 1 3,growx");
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		panel_4.add(lblNewLabel_3, "cell 0 4");
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		panel_4.add(lblNewLabel_4, "cell 0 5");
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		panel_4.add(lblNewLabel_5, "cell 0 6");
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		panel_4.add(lblNewLabel_6, "cell 0 7");
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton_1);
		panel_4.add(rdbtnNewRadioButton_1, "cell 1 3");
		
		this.pack();
	}
	
	void addOperationSetting()
	{
		JPanel jpanel = new JPanel();
		JLabel label = new JLabel("slider:");
		jpanel.add(label);
		jpanel.setSize( 400, 25 );
		JSlider jslider = new JSlider();
		jpanel.add(jslider);
		this.getContentPane().add(jpanel);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

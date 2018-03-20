package miscellaneous;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import listeners.MenuItemListener;
import java.awt.event.ActionEvent;


public class IntFlagMenuItem extends JMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2616063843864328266L;
	String name;
	int value;
	IntFlagItem intFlagItem;
	JButton menuButton;
	
	public IntFlagMenuItem( String name, int value, IntFlagItem ifi )
	{
		this.name = name;
		this.value = value;
		this.intFlagItem = ifi;
		this.setText( name + ", " + value);
		this.addActionListener( new MenuItemListener( intFlagItem ){

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked item.");
				this.ifi.setName( getIntFlagItem().getName() ); 
				this.ifi.setValue( getIntFlagItem().getValue() ); 
				if ( menuButton != null ) {
					menuButton.setText( intFlagItem.getName() + "," + intFlagItem.getValue() );
				} else {
					System.out.println("Could not update popup menu button because the menuButton" + 
										" variable was not set. use the menuRecursiveButtonAdd function" +
										" in operationdialogbox.");
				}
			}
		});
	}
	
	public void setMenuButton( JButton button )
	{
		this.menuButton = button;
	}

	public String getName()
	{
		return name;
	}
	
	public int getValue()
	{
		return value;
	}

	public IntFlagItem getIntFlagItem() {
		return new IntFlagItem( name, value );
	}
}

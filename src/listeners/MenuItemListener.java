package listeners;


import javax.swing.AbstractAction;
import javax.swing.JButton;

import miscellaneous.IntFlagItem;

public abstract class MenuItemListener extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected IntFlagItem ifi;
	
	public MenuItemListener( IntFlagItem ifi )
	{
		this.ifi = ifi;
	}

}

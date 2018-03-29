package listeners;


import javax.swing.AbstractAction;

import passableTypes.IntegerFlag;

public abstract class MenuItemListener extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected IntegerFlag ifi;
	
	public MenuItemListener( IntegerFlag ifi )
	{
		this.ifi = ifi;
	}

}

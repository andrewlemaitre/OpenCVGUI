package passableTypes;

import javax.swing.JMenuItem;

public class PassableIntFlagMenuItem extends JMenuItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name = "";
	private int value = 0;
	
	public PassableIntFlagMenuItem( String name, int value )
	{
		this.name = name;
		this.value = value;
		this.setText( name + ", " + value);
	}
	
	public String getName()	{
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public void setText( String text ){
		super.setText(text);
	}
}

package passableTypes;

import miscellaneous.IntFlagItem;

public class PassableIntFlagItem {
	
	private IntFlagItem value;
	
	public PassableIntFlagItem(){}
	
	public PassableIntFlagItem( String flagName, int flagValue ) {
		IntFlagItem ifi = new IntFlagItem( flagName, flagValue );
		setValue( ifi );
	}
	
	public void setValue( IntFlagItem i ) {
		value = i;
	}
	
	public IntFlagItem getValue() {
		return value;
	}
}

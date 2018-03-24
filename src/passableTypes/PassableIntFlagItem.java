package passableTypes;

import java.io.Serializable;

import miscellaneous.IntFlagItem;

public class PassableIntFlagItem implements Serializable {
	
    /** Generated serial id */
    private static final long serialVersionUID = 7573980744854091092L;
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

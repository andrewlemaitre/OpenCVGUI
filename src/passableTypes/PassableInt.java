package passableTypes;

import java.io.Serializable;

public class PassableInt implements Serializable {
	
    /** Generated serial id */
    private static final long serialVersionUID = -3938763484007618097L;
    private int value;
	
	public PassableInt() {}
	
	public PassableInt( int value )
	{
		this.value = value;
	}
	
	public void setValue( int i ) {
		value = i;
	}
	
	public int getValue() {
		return value;
	}
}

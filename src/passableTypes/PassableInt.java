package passableTypes;

public class PassableInt {
	
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

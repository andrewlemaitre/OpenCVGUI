package miscellaneous;

import java.io.Serializable;

public class IntFlagItem implements Serializable {

    /** Generated serial id */
    private static final long serialVersionUID = 435057375374010000L;
    String name;
	int value;
	
	public IntFlagItem(){
	}
	
	public IntFlagItem( String name, int value ){
		this.name = name;
		this.value = value;
	}
	
	//TODO: Override the hashcode method and provide an implementation for this class.
	@Override
	public boolean equals( Object o ){
		if( o == this )
			return true;
		
		if( !(o instanceof IntFlagItem) ){
			return false;
		}
		
		IntFlagItem ifi = (IntFlagItem)o;
		
		return ifi.getName().compareTo(this.getName()) == 0 &&
				ifi.getValue() == this.getValue();
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getValue(){
		return value;
	}

	public void setValue(int value){
		this.value = value;
	}
	
	public void setValue( String name, int value ) {
	    this.name = name;
	    this.value = value;
	}
	
    public void setValue( IntFlagItem ifi ) {
        this.name = ifi.getName();
        this.value = ifi.getValue();
    }
	
}

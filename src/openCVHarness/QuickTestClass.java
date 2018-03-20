package openCVHarness;

import java.util.ArrayList;

public class QuickTestClass {

	public static void main ( String[] args ){
		ArrayList<Integer> intlist = new ArrayList<Integer>();
		intlist.add(1);
		intlist.add(2);
		intlist.add(6);
		intlist.add(3);
		intlist.add(3);
		intlist.add(3);
		intlist.add(4);
		intlist.add(10);
		intlist.add(5);
		intlist.add(6);
		intlist.add(6);
		intlist.add(6);
		intlist.add(7);
		intlist.add(8);
		intlist.add(9);
		intlist.add(6);
		intlist.add(5);
		intlist.add(10);
		intlist.add(10);
		intlist.add(10);
		
		for( int i = 0; i < intlist.size()-1; i++ )
		{
			for( int j = i+1; j < intlist.size(); j++ )
			{
				if( intlist.get(i) == intlist.get(j) )
				{
					System.out.println("Removing index " + j + " with value " + intlist.get(j));
					intlist.remove(j);
					j--;
				}
			}
		}
		
		for( int i = 0; i < intlist.size(); i++ )
		{
			System.out.println("after: " + intlist.get(i));
		}
		System.out.println("Fibonacci");
		fibonacci( 0, 1, 25 );
		
		reverseString("abcdefghijklmnopqrstuvwxyz");
	}
	
	static void reverseString( String s )
	{
		String output = "";
		for( int i = s.length()-1; i >= 0; i-- )
		{
//			output = output.concat(s.substring(i,i+1));
//			output = output.concat(String.valueOf(s.charAt(i)));
			output = output + s.charAt(i);
		}
		System.out.println(output);
	}
	
	static void fibonacci( int oldVal, int val, int steps )
	{
		System.out.println(val);
		int newVal = val + oldVal;
		steps--;
		if( steps > 0 )
			fibonacci( val, newVal, steps ); 
	}
}

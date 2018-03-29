package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import passableTypes.IntegerFlag;

public abstract class ComboBoxListener implements ActionListener {

	protected IntegerFlag i;
	
	public ComboBoxListener( IntegerFlag i ) {
		this.i = i;
	}
	
	public abstract void actionPerformed(ActionEvent e);
}

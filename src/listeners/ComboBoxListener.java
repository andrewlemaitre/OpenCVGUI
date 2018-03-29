package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import miscellaneous.IntFlagItem;

public abstract class ComboBoxListener implements ActionListener {

	protected IntFlagItem i;
	
	public ComboBoxListener( IntFlagItem i ) {
		this.i = i;
	}
	
	public abstract void actionPerformed(ActionEvent e);
}

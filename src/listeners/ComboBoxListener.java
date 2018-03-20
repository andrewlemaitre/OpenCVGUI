package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import miscellaneous.IntFlagItem;
import passableTypes.PassableInt;
import passableTypes.PassableIntFlagItem;

public abstract class ComboBoxListener implements ActionListener {

	protected PassableIntFlagItem i;
	
	public ComboBoxListener( PassableIntFlagItem i ) {
		this.i = i;
	}
	
	public abstract void actionPerformed(ActionEvent e);
}

package listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import passableTypes.PassableString;

public abstract class DocumentChangeListener implements DocumentListener {
	
	protected PassableString s;
	
	@Override
	abstract public void insertUpdate(DocumentEvent e);
	
	@Override
	abstract public void removeUpdate(DocumentEvent e);

	@Override
	public void changedUpdate(DocumentEvent e){}
	
	public DocumentChangeListener( PassableString s )
	{
		this.s = s;
	}

}

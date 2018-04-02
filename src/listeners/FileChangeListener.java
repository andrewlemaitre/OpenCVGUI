package listeners;

import passableTypes.PassableFile;

import java.awt.event.ActionListener;

public abstract class FileChangeListener implements ActionListener {

    protected PassableFile f;

    public FileChangeListener(PassableFile f) {
        this.f = f;
    }

}

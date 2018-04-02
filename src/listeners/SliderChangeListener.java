package listeners;

import javax.swing.event.ChangeListener;

import passableTypes.PassableInt;

public abstract class SliderChangeListener implements ChangeListener {

    protected PassableInt i;

    public SliderChangeListener(PassableInt i) {
        this.i = i;
    }

}

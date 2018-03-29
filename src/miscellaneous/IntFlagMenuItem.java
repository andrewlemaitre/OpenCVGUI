package miscellaneous;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import listeners.MenuItemListener;
import passableTypes.IntegerFlag;

import java.awt.event.ActionEvent;

public class IntFlagMenuItem extends JMenuItem {

    /** Generated serial ID. */
    private static final long serialVersionUID = -2616063843864328266L;
    private String name;
    private int value;
    private IntegerFlag intFlagItem;
    private JButton menuButton;

    public IntFlagMenuItem(final String name, final int value, final IntegerFlag ifi) {
        this.name = name;
        this.value = value;
        this.intFlagItem = ifi;
        this.setText(name + ", " + value);
        this.addActionListener(new MenuItemListener(intFlagItem) {

            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                this.ifi.setName(getIntFlagItem().getName());
                this.ifi.setValue(getIntFlagItem().getValue());
                if (menuButton != null) {
                    menuButton.setText(intFlagItem.getName() + "," + intFlagItem.getValue());
                } else {
                    System.err.println("Could not update popup menu button because the menuButton"
                                        + " variable was not set. use the menuRecursiveButtonAdd function"
                                        + " in operationdialogbox.");
                }
            }
        });
    }

    public final void setMenuButton(final JButton button) {
        this.menuButton = button;
    }

    public final String getName() {
        return name;
    }

    public final int getValue() {
        return value;
    }

    public final IntegerFlag getIntFlagItem() {
        return new IntegerFlag(name, value);
    }
}

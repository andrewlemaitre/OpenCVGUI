package passableTypes;

import java.io.Serializable;

/** A class that holds a string and integer value. Typically used to populate combo boxes.
 * @author Andrew LeMaitre
 */
public class IntegerFlag implements Serializable {

    /** Generated serial id. */
    private static final long serialVersionUID = 435057375374010000L;
    /** The name of an IntegerFlag instance. */
    private String name;
    /** The integer value of an IntegerFlag instance. */
    private int value;


    /** A constructor to create an IntegerFlag with a null name and integer value = 0. */
    public IntegerFlag() {
    }

    /** A constructor to create an IntegerFlag with the specified name and integer value.
     * @param name The name of the newly created IntegerFlag.
     * @param value The integer value of the newly create IntegerFlag.
     */
    public IntegerFlag(final String name, final int value) {
        this.name = name;
        this.value = value;
    }

    //TODO: Override the hashcode method and provide an implementation for this class.
    @Override
    public final boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof IntegerFlag)) {
            return false;
        }

        IntegerFlag ifi = (IntegerFlag) o;

        return ifi.getName().compareTo(this.getName()) == 0
                && ifi.getValue() == this.getValue();
    }

    /** Gets the name of an IntegerFlag instance.
     * @return The name of an IntegerFlag instance.
     */
    public final String getName() {
        return name;
    }

    /** Updates the name of the integer flag.
     * @param name The new name of this IntegerFlag.
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /** Gets the integer value of an IntegerFlag instance.
     * @return The integer value of an IntegerFlag instance.
     */
    public final int getValue() {
        return value;
    }

    /** Sets the integer value of an IntegerFlag instance.
     * @param value The new value for the IntegerFlag instance.
     */
    public final void setValue(final int value) {
        this.value = value;
    }

    /** Sets the name and integer value of an IntegerFlag instance.
     * @param name The new name for the IntegerFlag instance.
     * @param value The new integer value for the IntegerFlag instance.
     */
    public final void setValue(final String name, final int value) {
        this.name = name;
        this.value = value;
    }

    /** A convenience method to set the name and value of an IntegerFlag to that of another.
     * @param ifi The IntegerFlag from which the name and integer value will be copied.
     */
    public final void setValue(final IntegerFlag ifi) {
        this.name = ifi.getName();
        this.value = ifi.getValue();
    }

}

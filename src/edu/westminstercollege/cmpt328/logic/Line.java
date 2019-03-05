package edu.westminstercollege.cmpt328.logic;

/**
 * An interface describing a line, i.e. a wire that connects (or can connect) two circuit elements such as gates.
 */
public interface Line {

    /** The ground line; its state is always {@link Bit#ZERO} */
    public static final Line GROUND = new Line() {
        @Override
        public Bit getState() {
            return Bit.ZERO;
        }

        @Override
        public String toString() {
            return "GROUND";
        }
    };

    /** The current line; its state is always {@link Bit#ONE} */
    public static final Line CURRENT = new Line() {
        @Override
        public Bit getState() {
            return Bit.ONE;
        }

        @Override
        public String toString() {
            return "CURRENT";
        }
    };

    /**
     * Returns the current state of this line &mdash; the bit (voltage) being applied to it.
     * @throws UnconnectedLineException if this Line is not connected and hence has indeterminate state
     */
    Bit getState() throws UnconnectedLineException;
}

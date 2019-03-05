package edu.westminstercollege.cmpt328.logic.gates;

import edu.westminstercollege.cmpt328.logic.*;

/**
 * A basic binary "not" gate, a.k.a. an inverter. The input, {@link #IN}, can be connected to an arbitrary other line.
 */
public class NotGate implements Gate {

    public final InputLine IN = new InputLine();

    public NotGate() {}

    public NotGate(Line in) {
        IN.connect(in);
    }

    @Override
    public Bit getState() throws UnconnectedLineException {
        return IN.getState().not();
    }
}

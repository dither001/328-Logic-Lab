package edu.westminstercollege.cmpt328.logic.gates;

import edu.westminstercollege.cmpt328.logic.*;

/**
 * A basic binary "or" gate. The inputs, {@link #A} and {@link #B}, can be connected to arbitrary other lines.
 */
public class OrGate implements Gate {

    public final InputLine A = new InputLine(),
                           B = new InputLine();

    public OrGate() {}

    public OrGate(Line inA, Line inB) {
        A.connect(inA);
        B.connect(inB);
    }

    @Override
    public Bit getState() throws UnconnectedLineException {
        return A.getState().or(B::getState);
    }
}

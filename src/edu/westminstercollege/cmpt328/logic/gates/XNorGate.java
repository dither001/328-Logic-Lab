package edu.westminstercollege.cmpt328.logic.gates;

import edu.westminstercollege.cmpt328.logic.*;

/**
 * A basic binary "xnor" (not XOR) gate. The inputs, {@link #A} and {@link #B}, can be connected to arbitrary other lines.
 */
public class XNorGate implements Gate {

    public final InputLine A = new InputLine();
    public final InputLine B = new InputLine();

    public XNorGate() {}

    public XNorGate(Line inA, Line inB) {
        A.connect(inA);
        B.connect(inB);
    }

    @Override
    public Bit getState() throws UnconnectedLineException {
        return A.getState().xor(B.getState()).not();
    }
}

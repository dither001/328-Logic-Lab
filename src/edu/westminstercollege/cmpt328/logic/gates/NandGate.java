package edu.westminstercollege.cmpt328.logic.gates;

import edu.westminstercollege.cmpt328.logic.*;

/**
 * A basic binary "nand" (not AND) gate. The inputs, {@link #A} and {@link #B}, can be connected to arbitrary other lines.
 */
public class NandGate implements Gate {

    public final InputLine A = new InputLine();
    public final InputLine B = new InputLine();

    public NandGate() {}

    public NandGate(Line inA, Line inB) {
        A.connect(inA);
        B.connect(inB);
    }

    @Override
    public Bit getState() throws UnconnectedLineException {
        return A.getState().and(B::getState).not();
    }
}

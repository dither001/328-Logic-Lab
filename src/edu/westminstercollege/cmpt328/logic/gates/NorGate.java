package edu.westminstercollege.cmpt328.logic.gates;

import edu.westminstercollege.cmpt328.logic.*;

/**
 * A basic binary "nor" (not OR) gate. The inputs, {@link #A} and {@link #B}, can be connected to arbitrary other lines.
 */
public class NorGate implements Gate {

    public final InputLine A = new InputLine();
    public final InputLine B = new InputLine();

    public NorGate() {}

    public NorGate(Line inA, Line inB) {
        A.connect(inA);
        B.connect(inB);
    }

    @Override
    public Bit getState() throws UnconnectedLineException {
        return A.getState().or(B::getState).not();
    }
}

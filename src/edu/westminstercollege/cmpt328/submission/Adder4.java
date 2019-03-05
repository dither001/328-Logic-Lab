package edu.westminstercollege.cmpt328.submission;

import edu.westminstercollege.cmpt328.logic.*;

public class Adder4 {
	// Inputs to the circuit: InputLines
	public final InputMultiLine A = new InputMultiLine(4);
	public final InputMultiLine B = new InputMultiLine(4);
	public final InputLine Cin = new InputLine();

	// Outputs of the circuit: Lines (initialize in constructor)
	public final MultiLine SUM;
	public final Line Cout;

	public Adder4() {
		FullAdder[] adders = new FullAdder[4];
		
		
		SUM = null;
		Cout = null;
	}
}

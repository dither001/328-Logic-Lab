package edu.westminstercollege.cmpt328.submission;

import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.gates.*;

/*
 * FullAdder class created for CMPT-328 Architecture
 * @Author Nick Foster
 */

public class FullAdder {
	// Inputs to the circuit: InputLines
	public final InputLine A = new InputLine();
	public final InputLine B = new InputLine();
	public final InputLine Cin = new InputLine();

	// Outputs of the circuit: Lines (initialize in constructor)
	public final Line SUM;
	public final Line Cout;

	public FullAdder() {
		HalfAdder adder1 = new HalfAdder();
		adder1.A.connect(A);
		adder1.B.connect(B);

		HalfAdder adder2 = new HalfAdder();
		adder2.A.connect(Cin);
		adder2.B.connect(adder1.SUM);
		
		SUM = adder2.SUM;
		Cout = new XorGate(adder1.C, adder2.C);
	}
}

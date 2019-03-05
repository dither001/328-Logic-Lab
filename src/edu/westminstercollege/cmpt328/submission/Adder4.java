package edu.westminstercollege.cmpt328.submission;

import edu.westminstercollege.cmpt328.logic.*;

/*
 * Adder4 class created for CMPT-328 Architecture
 * @Author Nick Foster
 */

public class Adder4 {
	// Inputs to the circuit: InputLines
	public final InputMultiLine A = new InputMultiLine(4);
	public final InputMultiLine B = new InputMultiLine(4);
	public final InputLine Cin = new InputLine();
	FullAdder[] adders = new FullAdder[] { new FullAdder(), new FullAdder(), new FullAdder(), new FullAdder() };

	// Outputs of the circuit: Lines (initialize in constructor)
	public final MultiLine SUM;
	public final Line Cout;

	public Adder4() {
		int i = 0;
		adders[i].A.connect(A.getLine(i));
		adders[i].B.connect(B.getLine(i));
		adders[i].Cin.connect(Cin);

		++i;
		adders[i].A.connect(A.getLine(i));
		adders[i].B.connect(B.getLine(i));
		adders[i].Cin.connect(adders[i - 1].Cout);

		++i;
		adders[i].A.connect(A.getLine(i));
		adders[i].B.connect(B.getLine(i));
		adders[i].Cin.connect(adders[i - 1].Cout);

		++i;
		adders[i].A.connect(A.getLine(i));
		adders[i].B.connect(B.getLine(i));
		adders[i].Cin.connect(adders[i - 1].Cout);

		SUM = MultiLine.of(adders[0].SUM, adders[1].SUM, adders[2].SUM, adders[3].SUM);
		Cout = adders[3].Cout;
	}
}

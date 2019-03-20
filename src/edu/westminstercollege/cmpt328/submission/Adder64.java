package edu.westminstercollege.cmpt328.submission;

import edu.westminstercollege.cmpt328.logic.*;

/*
 * Adder64 class created for CMPT-328 Architecture
 * @Author Nick Foster
 */

public class Adder64 {
	// Inputs to the circuit: InputLines
			public final InputMultiLine A = new InputMultiLine(64);
			public final InputMultiLine B = new InputMultiLine(64);
			public final InputLine Cin = new InputLine();
			Adder16[] adders = new Adder16[] { new Adder16(), new Adder16(), new Adder16(), new Adder16() };

			// Outputs of the circuit: Lines (initialize in constructor)
			public final MultiLine SUM;
			public final Line Cout;

			public Adder64() {
				int i = 0;
				adders[i].A.connectAll(A.lineRange(0, 16));
				adders[i].B.connectAll(B.lineRange(0, 16));
				adders[i].Cin.connect(Cin);

				++i;
				adders[i].A.connectAll(A.lineRange(16, 32));
				adders[i].B.connectAll(B.lineRange(16, 32));
				adders[i].Cin.connect(adders[i - 1].Cout);

				++i;
				adders[i].A.connectAll(A.lineRange(32, 48));
				adders[i].B.connectAll(B.lineRange(32, 48));
				adders[i].Cin.connect(adders[i - 1].Cout);

				++i;
				adders[i].A.connectAll(A.lineRange(48, 64));
				adders[i].B.connectAll(B.lineRange(48, 64));
				adders[i].Cin.connect(adders[i - 1].Cout);

				SUM = MultiLine.join(adders[0].SUM, adders[1].SUM, adders[2].SUM, adders[3].SUM);
				Cout = adders[3].Cout;
			}
}

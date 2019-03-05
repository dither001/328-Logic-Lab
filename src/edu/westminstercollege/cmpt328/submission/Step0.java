package edu.westminstercollege.cmpt328.submission;

import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.gates.*;

/*
 * Step0 class created for CMPT-328 Architecture
 * @Author Nick Foster
 */

public class Step0 {
	public static void main(String... args) {
		// Make an AND gate and connect some stuff to it
		// AndGate gate = new AndGate();
		OrGate gate = new OrGate();

		// gate.A.connect(Line.GROUND);
		// gate.B.connect(Line.CURRENT);
		gate.A.connect(Line.GROUND);
		gate.B.connect(Line.GROUND);

		System.out.println(gate.getState());
	}
}

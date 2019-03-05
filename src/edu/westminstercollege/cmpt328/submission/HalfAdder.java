package edu.westminstercollege.cmpt328.submission;

import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.gates.*;

public class HalfAdder {

	// Inputs to the circuit: InputLines
    public final InputLine A = new InputLine();
    public final InputLine B = new InputLine();
    
    // Outputs of the circuit: Lines (initialize in constructor)
    public final Line SUM;
    public final Line C;
    
    public HalfAdder() {
        // Put code here that creates the needed logic gates and connects them to the inputs
    	
        // Your code should finish with...
        SUM = new XorGate(A, B);
        C = new AndGate(A, B); 
    }

}

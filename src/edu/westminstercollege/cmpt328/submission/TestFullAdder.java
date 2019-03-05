package edu.westminstercollege.cmpt328.submission;
import edu.westminstercollege.cmpt328.logic.*;

import static edu.westminstercollege.cmpt328.logic.Bit.*;
import static edu.westminstercollege.cmpt328.logic.Line.*;

public class TestFullAdder {

    private static final FullAdder adder = new FullAdder();

    public static void main(String... args) {
        test(GROUND, GROUND, GROUND,  ZERO, ZERO);
        test(GROUND, GROUND, CURRENT,  ONE, ZERO);
        test(GROUND, CURRENT, GROUND,  ONE, ZERO);
        test(GROUND, CURRENT, CURRENT,  ZERO, ONE);
        test(CURRENT, GROUND, GROUND,   ONE, ZERO);
        test(CURRENT, GROUND, CURRENT,  ZERO, ONE);
        test(CURRENT, CURRENT, GROUND,  ZERO, ONE);
        test(CURRENT, CURRENT, CURRENT,  ONE, ONE);
    }

    private static void test(Line A, Line B, Line Cin,
                             Bit expectedSUM, Bit expectedCout) {
        adder.A.connect(A);
        adder.B.connect(B);
        adder.Cin.connect(Cin);

        Bit SUM = adder.SUM.getState();
        Bit Cout = adder.Cout.getState();

        System.out.printf("%s + %s + C %s = %s C %s    %s\n",
                A.getState(), B.getState(), Cin.getState(),
                SUM, Cout,
                (SUM == expectedSUM && Cout == expectedCout) ? "PASS" : "FAIL");
    }
}

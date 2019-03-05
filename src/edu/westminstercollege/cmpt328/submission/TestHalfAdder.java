package edu.westminstercollege.cmpt328.submission;
import edu.westminstercollege.cmpt328.logic.*;

import static edu.westminstercollege.cmpt328.logic.Bit.*;
import static edu.westminstercollege.cmpt328.logic.Line.*;

public class TestHalfAdder {

    private static final HalfAdder adder = new HalfAdder();

    public static void main(String... args) {
        test(GROUND, GROUND, ZERO, ZERO);
        test(GROUND, CURRENT, ONE, ZERO);
        test(CURRENT, GROUND, ONE, ZERO);
        test(CURRENT, CURRENT, ZERO, ONE);
    }

    private static void test(Line A, Line B, Bit expectedSUM, Bit expectedC) {
        adder.A.connect(A);
        adder.B.connect(B);

        Bit SUM = adder.SUM.getState();
        Bit C = adder.C.getState();

        System.out.printf("%s + %s = %s C %s    %s\n",
                A.getState(), B.getState(), SUM, C,
                (SUM == expectedSUM && C == expectedC) ? "PASS" : "FAIL");
    }
}

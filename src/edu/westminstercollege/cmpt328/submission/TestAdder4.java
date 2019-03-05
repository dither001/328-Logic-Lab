package edu.westminstercollege.cmpt328.submission;
import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.ic.*;

import static edu.westminstercollege.cmpt328.logic.Bit.*;
import static edu.westminstercollege.cmpt328.logic.Line.*;

public class TestAdder4 {

    private static final Adder4 adder = new Adder4();

    public static void main(String... args) {
        NumberFormat format = NumberFormat.unsigned(4);
        NumberSource sourceA = new NumberSource(format);
        NumberSource sourceB = new NumberSource(format);
        adder.A.connectAll(sourceA);
        adder.B.connectAll(sourceB);

        NumberDisplay displaySUM = new NumberDisplay(format);
        displaySUM.IN.connectAll(adder.SUM);

        // Exhaustively test every possible combination of inputs
        int passes = 0, failures = 0;
        for (int A = format.minimumValue().intValue(); A <= format.maximumValue().intValue(); ++A) {
            sourceA.setValue(A);
            for (int B = format.minimumValue().intValue(); B <= format.maximumValue().intValue(); ++B) {
                sourceB.setValue(B);
                for (int Cin = 0; Cin < 2; ++Cin) {
                    adder.Cin.connect(Cin == 0 ? GROUND : CURRENT);

                    int expectedSum = (A + B + Cin) % (format.maximumValue().intValue() + 1);
                    Bit expectedCout = Bit.of((A + B + Cin) > format.maximumValue().intValue());
                    int sum = (int) displaySUM.getValue();
                    Bit Cout = adder.Cout.getState();

                    boolean pass = (sum == expectedSum && Cout == expectedCout);
                    if (pass)
                        ++passes;
                    else
                        ++failures;

                    System.out.printf("%2d + %2d + C %s = %2d C %s    %s\n",
                            A, B, Cin,
                            sum, Cout,
                            pass ? "PASS" : "FAIL");
                }
            }
        }

        System.out.printf("\n%d out of %d cases passed!\n", passes, passes + failures);
        if (failures == 0)
            System.out.println("\nGood job! Move on to the next step.");
        else
            System.out.println("\nMake sure you fix it before moving on!");
    }
}

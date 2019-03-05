package edu.westminstercollege.cmpt328.submission;
import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.ic.*;

import java.util.Random;

import static edu.westminstercollege.cmpt328.logic.Bit.*;
import static edu.westminstercollege.cmpt328.logic.Line.*;

public class TestAdder16 {

    private static final Adder16 adder = new Adder16();

    public static void main(String... args) {
        NumberFormat format = NumberFormat.unsigned(16);
        NumberSource sourceA = new NumberSource(format);
        NumberSource sourceB = new NumberSource(format);
        adder.A.connectAll(sourceA);
        adder.B.connectAll(sourceB);

        NumberDisplay displaySUM = new NumberDisplay(format);
        displaySUM.IN.connectAll(adder.SUM);

        // There are now 2^33 ≈ 8.6 bn possible combinations of inputs so we'll just content ourselves with running a
        // bunch of tests!

        // Random number generator to use in generating test cases
        Random random = new Random(0); // seed is fixed so always generates same "random" test cases
        // If the test passes, consider replacing the above line with this and running another time or two:
        // Random random = new Random(); // seed is system clock so generates different "random" test cases every time

        final int TEST_COUNT = 50000;

        System.out.printf("Running %d test cases selected at random....\n", TEST_COUNT);
        int passes = 0, failures = 0;
        for (int i = 0; i < TEST_COUNT; ++i) {
            // Choose random integers from the format for A and B and 0 or 1 for Cin
            int A = format.random(random).intValue(),
                B = format.random(random).intValue(),
                Cin = random.nextInt(2);
            sourceA.setValue(A);
            sourceB.setValue(B);
            adder.Cin.connect((Cin == 0) ? GROUND : CURRENT);

            int expectedSum = (A + B + Cin) % (format.maximumValue().intValue() + 1);
            Bit expectedCout = Bit.of((A + B + Cin) > format.maximumValue().intValue());
            int sum = (int)displaySUM.getValue();
            Bit Cout = adder.Cout.getState();

            boolean pass = (sum == expectedSum && Cout == expectedCout);
            if (pass)
                ++passes;
            else
                ++failures;

            if (!pass)
                System.out.printf("Case %5d/%5d: %5d + %5d + C %s = %5d C %s    FAIL: expected %5d C %s\n",
                        i, TEST_COUNT,
                        A, B, Cin, sum, Cout,
                        expectedSum, expectedCout);

            if (failures >= 50) {
                System.out.println("\n50 test cases have failed - bailing out.");
                break;
            }
        }

        if (passes + failures == TEST_COUNT)
            System.out.printf("\n%d of %d cases passed!\n", passes, passes + failures);
        if (failures == 0)
            System.out.printf("\nGood job! You may wish to run again with a different Random (see code).\n");
        else
            System.out.printf("\nMake sure you fix it before moving on!");
    }
}

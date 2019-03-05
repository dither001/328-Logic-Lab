package edu.westminstercollege.cmpt328.submission;
import edu.westminstercollege.cmpt328.logic.*;
import edu.westminstercollege.cmpt328.logic.ic.*;

import java.math.BigInteger;
import java.util.Random;

import static edu.westminstercollege.cmpt328.logic.Bit.*;
import static edu.westminstercollege.cmpt328.logic.Line.*;

public class TestAdder64 {

    private static final Adder64 adder = new Adder64();
    private static final BigInteger MAX_UNSIGNED_LONG = BigInteger.valueOf(Long.MAX_VALUE).shiftLeft(1).add(BigInteger.ONE);

    public static void main(String... args) {
        // Using 64-bit 2s complement format because this is the same as the Java long type!
        NumberFormat format = NumberFormat.twosComplement(64);
        NumberSource sourceA = new NumberSource(format);
        NumberSource sourceB = new NumberSource(format);
        adder.A.connectAll(sourceA);
        adder.B.connectAll(sourceB);

        NumberDisplay displaySUM = new NumberDisplay(format);
        displaySUM.IN.connectAll(adder.SUM);

        // There are now 2^129 ≈ 6.8×10^38 possible combinations of inputs so we'll just content ourselves with running a
        // bunch of tests!

        // Random number generator to use in generating test cases
        Random random = new Random(0); // seed is fixed so always generates same "random" test cases
        // If the test passes, consider replacing the above line with this and running another time or two:
        // Random random = new Random(); // seed is system clock so generates different "random" test cases every time

        final int TEST_COUNT = 10000;

        System.out.printf("Running %d test cases selected at random....\n", TEST_COUNT);
        int passes = 0, failures = 0;
        for (int i = 0; i < TEST_COUNT; ++i) {
            // Choose random integers from the format for A and B and 0 or 1 for Cin
            long A = format.random(random).longValue(),
                 B = format.random(random).longValue(),
                 Cin = random.nextInt(2);
            sourceA.setValue(A);
            sourceB.setValue(B);
            adder.Cin.connect((Cin == 0) ? GROUND : CURRENT);

            long expectedSum = A + B + Cin;
            Bit expectedCout = Bit.of(expectOverflow(A, B, Cin, format));
            long sum = displaySUM.getValue();
            Bit Cout = adder.Cout.getState();

            boolean pass = (sum == expectedSum && Cout == expectedCout);
            if (pass)
                ++passes;
            else
                ++failures;

            if (!pass)
                System.out.printf("Case %5d/%5d: %,28d + %,28d + C %s = %,28d C %s   FAIL: expected %,28d C %s\n",
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
            System.out.println("\nGood job! You may wish to run again with a different Random (see code).\n");
        else
            System.out.println("\nMake sure you fix it before moving on!\n");
    }

    private static boolean expectOverflow(long A, long B, long Cin, NumberFormat format) {
        BigInteger bigA = unsigned(A), bigB = unsigned(B);
        BigInteger sum = bigA.add(bigB).add(BigInteger.valueOf(Cin));
        return sum.compareTo(MAX_UNSIGNED_LONG) > 0;
    }

    // Returns a nonnegative BigInteger whose bits are the same as x
    private static BigInteger unsigned(long x) {
        if (x < 0)
            return BigInteger.valueOf(x & Long.MAX_VALUE).setBit(Long.SIZE - 1);
        else
            return BigInteger.valueOf(x);
    }
}

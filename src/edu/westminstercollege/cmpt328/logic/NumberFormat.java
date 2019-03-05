package edu.westminstercollege.cmpt328.logic;

import java.math.BigInteger;
import java.util.Random;

/**
 * An abstract class representing a method of encoding numbers (integers) into {@link Bit}s, e.g. unsigned or 2s
 * complement. Methods are provided for accessing individual bits of a given number and assembling a number out of bits.
 */
public abstract class NumberFormat {

    private NumberFormat() {}

    /**
     * Returns the least value this format can represent as a {@link java.math.BigInteger}.
     */
    public abstract BigInteger minimumValue();

    /**
     * Returns the greatest value this format can represent as a {@link java.math.BigInteger}.
     */
    public abstract BigInteger maximumValue();

    /**
     * Returns the number of bits this format uses to represent an integer.
     */
    public abstract int getBits();

    /**
     * Returns a single {@link Bit} in this format's representation of the given numeric value.
     * @param value the number to extract one bit from
     * @param bit the bit number to extract &mdash; 0 is the least-significant bit (1's place) and the largest allowed
     *            value is <code>getBits() - 1</code>
     * @return {@link Bit#ZERO} or {@link Bit#ONE}
     * @throws IllegalArgumentException if <code>value</code> is not between {@link #minimumValue()} and
     *                                  {@link #maximumValue()} or if <code>bit</code> is not between <code>0</code> and
     *                                  <code>getBits() - 1</code>
     */
    public abstract Bit bitOf(BigInteger value, int bit);

    /**
     * Returns the numeric value corresponding to the given sequence of bits. There should be exactly <code>getBits()</code>
     * elements of the array; <code>bits[0]</code> is treated as the least-significant bit (1's place) and so on.
     * @param bits the bits to form into a numeric value
     * @return the numeric value whose bits are given as a {@link java.math.BigInteger}
     * @throws IllegalArgumentException if <code>bits.length != getBitCount()</code> or if any array element is <code>null</code>
     */
    public abstract BigInteger numberFromBits(Bit[] bits);

    /**
     * Returns a human-readable string description of this format (e.g. "16-bit unsigned")
     */
    public abstract String toString();

    /**
     * Utility method that returns <code>true</code> if <code>value</code> is between <code>minimumValue()</code> and
     * <code>maximumValue()</code>
     */
    public boolean checkRange(BigInteger value) {
        return value.compareTo(minimumValue()) >= 0
                && value.compareTo(maximumValue()) <= 0;
    }

    /**
     * Returns a randomly-selected valid {@link java.math.BigInteger} value in this format
     * @param random a random number generator to use
     */
    public BigInteger random(Random random) {
        Bit[] bits = new Bit[getBits()];
        for (int i = 0; i < bits.length; ++i)
            bits[i] = Bit.of(random.nextBoolean());
        return numberFromBits(bits);
    }

    protected void check(BigInteger value) {
        if (!checkRange(value))
            throw new IllegalArgumentException(String.format("Value %,d not in range", value));
    }

    private abstract static class BigIntegerRangeFormat extends NumberFormat {
        private final int bits;
        private final BigInteger minimum, maximum;

        private BigIntegerRangeFormat(int bits, BigInteger minimum, BigInteger maximum) {
            this.bits = bits;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        public BigInteger minimumValue() {
            return minimum;
        }

        @Override
        public BigInteger maximumValue() {
            return maximum;
        }

        @Override
        public int getBits() {
            return bits;
        }

        @Override
        public Bit bitOf(BigInteger value, int bit) {
            check(value);
            return Bit.of(value.testBit(bit));
        }

        @Override
        public BigInteger numberFromBits(Bit[] fromBits) {
            if (fromBits.length != this.bits)
                throw new IllegalArgumentException(String.format("Expected %d bits but got %d", this.bits, fromBits.length));
            BigInteger b = BigInteger.ZERO;
            for (int i = 0; i < fromBits.length; ++i)
                if (fromBits[i] == Bit.ONE)
                    b = b.setBit(i);

            return b;
        }
    }

    /**
     * Returns an unsigned number format with the given number of bits. Such a format can represent integers between
     * 0 and 2<sup>bits</sup> - 1.
     * @param bits the number of bits for this format
     * @throws IllegalArgumentException if <code>bits < 1</code>
     */
    public static NumberFormat unsigned(final int bits) {
        if (bits < 1)
            throw new IllegalArgumentException("Bits must be at least 1");
        return new BigIntegerRangeFormat(bits, BigInteger.ZERO, BigInteger.ONE.shiftLeft(bits).subtract(BigInteger.ONE)) {
            @Override
            public String toString() {
                return String.format("%d-bit unsigned", bits);
            }
        };
    }

    /**
     * Returns a 2s complement signed number format with the given number of bits. Such a format can represent integers
     * between -2<sup>bits - 1</sup> and +2<sup>bits - 1</sup> - 1.
     * @param bits the number of bits for this format
     * @throws IllegalArgumentException if <code>bits < 2</code>
     */
    public static NumberFormat twosComplement(final int bits) {
        if (bits < 2)
            throw new IllegalArgumentException("Bits must be at least 2");
        return new BigIntegerRangeFormat(bits,
                BigInteger.ONE.shiftLeft(bits - 1).negate(),
                BigInteger.ONE.shiftLeft(bits - 1).subtract(BigInteger.ONE)) {
            @Override
            public BigInteger numberFromBits(Bit[] fromBits) {
                BigInteger b = super.numberFromBits(fromBits); // unsigned version of number
                if (fromBits[bits - 1] == Bit.ONE)
                    b = b.subtract(BigInteger.ONE.shiftLeft(bits));
                return b;
            }

            @Override
            public String toString() {
                return String.format("%d-bit 2s complement", bits);
            }
        };
    }
}

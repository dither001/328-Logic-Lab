package edu.westminstercollege.cmpt328.logic;

import java.util.function.Supplier;

/**
 * An enumeration of the two possible values of a bit: zero and one. Convenience methods are provided for basic logic
 * functions.
 */
public enum Bit {

    /** Zero. */
    ZERO {
        @Override
        public Bit not() {
            return ONE;
        }

        @Override
        public Bit and(Bit other) {
            return ZERO;
        }

        @Override
        public Bit and(Supplier<Bit> other) {
            return ZERO;
        }

        @Override
        public Bit or(Bit other) {
            return other;
        }

        @Override
        public Bit or(Supplier<Bit> other) {
            return other.get();
        }

        @Override
        public Bit xor(Bit other) {
            return other;
        }

        @Override
        public String toString() {
            return "0";
        }
    },

    /** One. */
    ONE {
        @Override
        public Bit not() {
            return ZERO;
        }

        @Override
        public Bit and(Bit other) {
            return other;
        }

        @Override
        public Bit and(Supplier<Bit> other) {
            return other.get();
        }

        @Override
        public Bit or(Bit other) {
            return ONE;
        }

        @Override
        public Bit or(Supplier<Bit> other) {
            return ONE;
        }

        @Override
        public Bit xor(Bit other) {
            return other.not();
        }

        @Override
        public String toString() {
            return "1";
        }
    };

    /** Returns the value of this Bit (0 for {@link #ZERO}, 1 for {@link #ONE}) */
    public int value() {
        return ordinal();
    }

    /** Returns the "not" of this Bit */
    public abstract Bit not();

    /** Returns the "and" of this Bit and other */
    public abstract Bit and(Bit other);

    /**
     * Returns the "and" of this Bit and the bit returned by other. The parameter is not a Bit but a supplier of a Bit;
     * this allows short-circuiting as that there is no need to evaluate other.get() if this Bit is 0.
     */
    public abstract Bit and(Supplier<Bit> other);

    /** Returns the "or" (inclusive or) of this Bit and other */
    public abstract Bit or(Bit other);

    /**
     * Returns the "or" of this Bit and the bit returned by other. The parameter is not a Bit but a supplier of a Bit;
     * this allows short-circuiting as that there is no need to evaluate other.get() if this Bit is 1.
     */
    public abstract Bit or(Supplier<Bit> other);

    /** Returns the "xor" (exclusive or) of this Bit and other */
    public abstract Bit xor(Bit other);

    /** Returns "0" or "1" */
    @Override
    public abstract String toString();

    /** Returns a Bit whose integral value is the parameter.
     * @return {@link Bit#ZERO} or {@link Bit#ONE}
     * @throws IllegalArgumentException if x is neither 0 nor 1
     */
    public static Bit of(int x) {
        return (x == 0) ? ZERO : ONE;
    }

    /** Returns a Bit whose truth value is the parameter.
     * @return {@link Bit#ZERO} if <code>b == false</code> or {@link Bit#ONE} otherwise
     */
    public static Bit of(boolean b) {
        return b ? ONE : ZERO;
    }
}

package edu.westminstercollege.cmpt328.logic.ic;

import edu.westminstercollege.cmpt328.logic.*;

import java.math.BigInteger;

public class NumberSource implements MultiLine {

    private final NumberFormat format;
    private BigInteger value;
    private BigInteger ones = null;
    private final Bit[] bits;

    private static final Line[] LINES = { Line.GROUND, Line.CURRENT };

    public NumberSource(NumberFormat format) {
        this(format, 0);
    }

    public NumberSource(NumberFormat format, long initValue) {
        this.format = format;
        bits = new Bit[format.getBits()];
        setValue(initValue);
    }

    public NumberSource(NumberFormat format, BigInteger initValue) {
        this.format = format;
        bits = new Bit[format.getBits()];
        setValue(value);
    }

    public void setValue(long value) {
        setValue(BigInteger.valueOf(value));
    }

    public void setValue(BigInteger value) {
        if (!format.checkRange(value))
            throw new IllegalArgumentException(String.format("Value %,d out of range for %s", value, format));
        this.value = value;
        for (int i = 0; i < bits.length; ++i)
            bits[i] = format.bitOf(value, i);
    }

    public long getValue() {
        return value.longValueExact();
    }

    public BigInteger getBigValue() {
        return value;
    }

    @Override
    public int getLineCount() {
        return bits.length;
    }

    @Override
    public Line getLine(final int line) {
        return () -> bits[line];
    }
}

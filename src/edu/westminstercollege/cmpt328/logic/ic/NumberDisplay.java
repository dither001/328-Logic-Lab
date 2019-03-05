package edu.westminstercollege.cmpt328.logic.ic;

import edu.westminstercollege.cmpt328.logic.Bit;
import edu.westminstercollege.cmpt328.logic.InputMultiLine;
import edu.westminstercollege.cmpt328.logic.MultiLine;
import edu.westminstercollege.cmpt328.logic.NumberFormat;

import java.math.BigInteger;

public class NumberDisplay {

    private final NumberFormat format;
    private final Bit[] bits;

    public final InputMultiLine IN;

    public NumberDisplay(NumberFormat format) {
        this.format = format;
        this.bits = new Bit[format.getBits()];
        IN = new InputMultiLine(format.getBits());
    }

    public NumberDisplay(NumberFormat format, MultiLine in) {
        this(format);
        IN.connectAll(in);
    }

    public long getValue() {
        return getBigValue().longValueExact();
    }

    public BigInteger getBigValue() {
        for (int i = 0; i < bits.length; ++i)
            bits[i] = IN.getState(i);
        return format.numberFromBits(bits);
    }
}

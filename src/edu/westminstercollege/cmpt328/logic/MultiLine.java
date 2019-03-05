package edu.westminstercollege.cmpt328.logic;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An interface representing multiple lines that logically belong together (e.g., a MultiLine of 32 <code>Line</code>s
 * could carry a 32-bit integer).
 */
public interface MultiLine {

    /**
     * Returns the number of {@link Line}s grouped together in this MultiLine.
     */
    int getLineCount();

    /**
     * Returns one {@link Line} from this MultiLine.
     * @throws IllegalArgumentException if <code>line</code> is not in the range <code>0...getLineCount()-1</code>
     */
    Line getLine(int line);

    /**
     * Returns an immutable list of all the {@link Line}s in this MultiLine.
     */
    default List<Line> getAllLines() {
        return new AbstractList<Line>() {
            @Override
            public Line get(int i) {
                return getLine(i);
            }

            @Override
            public int size() {
                return getLineCount();
            }
        };
    }

    /**
     * Returns the state (a {@link Bit}) of one {@link Line} of this MultiLine.
     * @throws IllegalArgumentException if <code>line</code> is not in the range <code>0...getLineCount()-1</code>
     * @throws UnconnectedLineException if the selected {@link Line} is not connected and hence has indeterminate state
     */
    default Bit getState(int line) throws UnconnectedLineException {
        return getLine(line).getState();
    }

    /**
     * Returns an immutable list of the states of each {@link Line} in this MultiLine. The returned list is
     * live, so its values will change as the states of the {@link Line}s change. The returned list will throw an
     * {@link UnconnectedLineException} if the state of an unconnected line is requested but will <strong>not</strong>
     * throw one immediately as this method is called.
     * @see InputLine#getState()
     */
    default List<Bit> getAllStates() {
        return new AbstractList<Bit>() {
            @Override
            public Bit get(int i) {
                return getState(i);
            }

            @Override
            public int size() {
                return getLineCount();
            }
        };
    }

    /**
     * Returns a MultiLine resulting from coupling all the given {@link Line}. The returned MultiLine has a line count of
     * <code>lines.length</code>.
     */
    static MultiLine of(final Line... lines) {
        return new MultiLine() {
            @Override
            public int getLineCount() {
                return lines.length;
            }

            @Override
            public Line getLine(int line) {
                return lines[line];
            }
        };
    }

    /**
     * Joins this MultiLine with another, returning a new MultiLine whose first <code>this.getLineCount()</code> lines
     * come from <code>this</code> and the remaining <code>other.getLineCount()</code> lines come from <code>other</code>.
     */
    default MultiLine join(final MultiLine other) {
        return new MultiLine() {
            private final int n = MultiLine.this.getLineCount();

            @Override
            public int getLineCount() {
                return n + other.getLineCount();
            }

            @Override
            public Line getLine(int line) {
                if (line < n)
                    return MultiLine.this.getLine(line);
                else
                    return other.getLine(line - n);
            }
        };
    }

    /**
     * Joins several MultiLines together, forming a new MultiLine whose number of lines is the sum of all parameters.
     * The first <code>multiLines[0].getLineCount()</code> lines will come from <code>multLines[0]</code> and so on.
     */
    static MultiLine join(final MultiLine... multiLines) {
        if (multiLines.length == 0)
            throw new IllegalArgumentException("Must have at least one MultiLine");
        else if (multiLines.length == 1)
            return multiLines[0];
        MultiLine join = multiLines[0];
        for (int i = 1; i < multiLines.length; ++i)
            join = join.join(multiLines[i]);
        return join;
    }

    /**
     * Returns a MultiLine resulting from picking out certain lines from this MultiLine. The returned MultiLine has a
     * line count of <code>lineNumbers.length</code>.
     */
    default MultiLine lines(final int... lineNumbers) {
        return new MultiLine() {
            @Override
            public int getLineCount() {
                return lineNumbers.length;
            }

            @Override
            public Line getLine(int line) {
                return MultiLine.this.getLine(lineNumbers[line]);
            }
        };
    }

    /**
     * Returns a new MultiLine resulting from picking out a range of lines from this MultiLine. The returned MultiLine
     * has <code>b - a</code> lines from a to b-1.
     * @param a the start index (inclusive)
     * @param b the end index (exclusive)
     */
    default MultiLine lineRange(final int a, final int b) {
        return new MultiLine() {
            @Override
            public int getLineCount() {
                return b - a;
            }

            @Override
            public Line getLine(int line) {
                return MultiLine.this.getLine(line + a);
            }
        };
    }
}

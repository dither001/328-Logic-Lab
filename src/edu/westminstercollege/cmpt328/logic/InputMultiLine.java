package edu.westminstercollege.cmpt328.logic;

import java.util.AbstractList;
import java.util.List;
import java.util.Optional;

/**
 * A class representing multiple lines that logically belong together (e.g., a MultiLine of 32 <code>Line</code>s
 * could carry a 32-bit integer). Further, an InputMultiLine can be connected to another {@link MultiLine} in the same
 * way that an {@link InputLine} connects to a {@link Line}, and individual lines can be connected to {@link Line}s.
 */
public class InputMultiLine implements MultiLine {

    private Line[] connectedLines;

    /**
     * Creates an InputMultiLine that is initially not connected to anything.
     * @param lineCount the number of lines in this MultiLine
     * @throws IllegalArgumentException if lineCount < 0
     */
    public InputMultiLine(int lineCount) {
        if (lineCount < 0)
            throw new IllegalArgumentException("lineCount cannot be negative");
        connectedLines = new Line[lineCount];
    }

    /**
     * Creates an InputMultiLine that is initially connected to the given lines (which cannot be <code>null</code>). Its
     * number of lines will match the parameter's.
     */
    public InputMultiLine(MultiLine connectedLines) {
        this(connectedLines.getLineCount());
        connectAll(connectedLines);
    }

    @Override
    public int getLineCount() {
        return connectedLines.length;
    }

    /**
     * Returns one {@link InputLine} from this InputMultiLine. This InputLine can be {@link InputLine#connect(Line)}ed
     * individually to another {@link Line} which has the same effect as connecting it using the {@link #connect(int, Line)}
     * method.
     * @throws IllegalArgumentException if <code>line</code> is not in the range <code>0...getLineCount()-1</code>
     */
    @Override
    public InputLine getLine(final int line) {
        return new InputLine() {
            @Override
            public void connect(Line other) {
                InputMultiLine.this.connect(line, other);
            }

            @Override
            public void disconnect() {
                InputMultiLine.this.disconnect(line);
            }

            @Override
            public Optional<Line> getConnectedLine() {
                return InputMultiLine.this.getConnectedLine(line);
            }

            @Override
            public Bit getState() throws UnconnectedLineException {
                if (connectedLines[line] == null)
                    throw new UnconnectedLineException("Line #" + line + " not connected");
                return connectedLines[line].getState();
            }
        };
    }

    /**
     * Returns an immutable list of all the {@link InputLine}s in this InputMultiLine.
     */
    public List<InputLine> getAllInputLines() {
        return new AbstractList<InputLine>() {
            @Override
            public InputLine get(int i) {
                return getLine(i);
            }

            @Override
            public int size() {
                return getLineCount();
            }
        };
    }

    /**
     * Connects a single {@link InputLine} of this InputMultiLine to another line.
     * @see InputLine#connect(Line)
     * @throws IllegalArgumentException if <code>line</code> is not in the range <code>0...getLineCount()-1</code> or
     *         if <code>other</code> is <code>null</code>
     */
    public void connect(int line, Line other) {
        if (line < 0 || line >= getLineCount())
            throw new IllegalArgumentException("Invalid line number");
        if (other == null)
            throw new IllegalArgumentException("Line to connect to cannot be null");
        connectedLines[line] = other;
    }

    /**
     * Connects this InputMultiLine to another {@link MultiLine}, connecting each individual {@link InputLine} of this
     * InputMultiLine to the corresponding {@link Line} of <code>other</code>.
     * @throws IllegalArgumentException if <code>other</code> is <code>null</code> or if <code>getLineCount() != other.getLineCount()</code>.
     */
    public void connectAll(MultiLine other) {
        if (other == null)
            throw new IllegalArgumentException("Lines to connect cannot be null");
        if (getLineCount() != other.getLineCount())
            throw new IllegalArgumentException("Incompatible number of lines");
        disconnectAll();
        for (int i = 0; i < getLineCount(); ++i)
            this.connectedLines[i] = other.getLine(i);
    }

    /**
     * Disconnects this InputMultiLine from all {@link Line}s it was previously connected to (if any). Calling this method on
     * an InputMultiLine that is not connected does nothing.
     */
    public void disconnectAll() {
        for (int i = 0; i < getLineCount(); ++i)
            disconnect(i);
    }

    /**
     * Disconnects one line of this InputMultiLine from the {@link Line} it was connected to (if any).
     * @param line the line number to disconnect (<code>0...getLineCount()-1</code>)
     * @throws IllegalArgumentException if <code>line</code> is not in the range <code>0...getLineCount()-1</code>
     */
    public void disconnect(int line) {
        if (line < 0 || line >= getLineCount())
            throw new IllegalArgumentException("Invalid line number");
        connectedLines[line] = null;
    }

    /**
     * Returns the {@link Line} this {@link InputLine} of the InputMultiLine is currently connected to, if any.
     */
    public Optional<Line> getConnectedLine(int line) {
        return Optional.ofNullable(connectedLines[line]);
    }

    /**
     * Returns an immutable list of the {@link Line}s the {@link InputLine}s of this InputMultiLine are connected to.
     */
    public List<Optional<Line>> getAllConnectedLines() {
        return new AbstractList<Optional<Line>>() {
            @Override
            public Optional<Line> get(int i) {
                return getConnectedLine(i);
            }

            @Override
            public int size() {
                return getLineCount();
            }
        };
    }

    /**
     * Return the current state (a {@link Bit}) of one InputLine of this {@link InputMultiLine}.
     * @throws IllegalArgumentException if <code>line</code> is not in the range <code>0...getLineCount()-1</code>
     * @throws UnconnectedLineException if the InputLine is not connected
     * @see InputLine#getState()
     */
    @Override
    public Bit getState(int line) throws UnconnectedLineException {
        if (line < 0 || line >= getLineCount())
            throw new IllegalArgumentException("Invalid line number");
        if (connectedLines[line] == null)
            throw new UnconnectedLineException("Input line not connected to a source");
        return connectedLines[line].getState();
    }

    /**
     * Returns an immutable list of the states of each {@link InputLine} in this InputMultiLine. The returned list is
     * live, so its values will change as the state of the {@link InputLine}s change. The returned list will throw an
     * {@link UnconnectedLineException} if the state of an unconnected line is requested but will <strong>not</strong>
     * throw one immediately as this method is called.
     * @see InputLine#getState()
     */
    @Override
    public List<Bit> getAllStates() {
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
}

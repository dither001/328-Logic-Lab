package edu.westminstercollege.cmpt328.logic;

import java.util.Optional;

/**
 * A class representing a line that acts as input to a circuit element such as a gate. An InputLine can be connected to
 * another {@link Line}, and its state is always that of the connected line.
 */
public class InputLine implements Line {

    private Line connectedLine;

    /**
     * Creates an InputLine that is initially not connected to anything.
     */
    public InputLine() {

    }

    /**
     * Creates an InputLine that is initially connected to the given line (which cannot be <code>null</code>).
     */
    public InputLine(Line connectedLine) {
        connect(connectedLine);
    }

    /**
     * Connects this InputLine to another {@link Line}. An InputLine can be connected to at most one other line at a
     * time, so connecting to this line will disconnect from whatever previous line may have been connected to.
     * @throws IllegalArgumentException if <code>other == null</code>
     */
    public void connect(Line other) {
        if (other == null)
            throw new IllegalArgumentException("Line to connect cannot be null");
        disconnect();
        this.connectedLine = other;
    }

    /**
     * Disconnects this InputLine from the {@link Line} it was previously connected to (if any). Calling this method on
     * an InputLine that is not connected does nothing.
     */
    public void disconnect() {
        this.connectedLine = null;
    }

    /**
     * Returns whether this InputLine is currently connected to another {@link Line}.
     */
    public boolean isConnected() {
        return connectedLine != null;
    }

    /**
     * Returns the {@link Line} this InputLine is currently connected to, if any.
     */
    public Optional<Line> getConnectedLine() {
        return Optional.ofNullable(connectedLine);
    }

    /**
     * Return the current state (a {@link Bit}) of this InputLine. The state of an InputLine is always the state of the
     * {@link Line} that it is connected to.
     * @return {@link Bit#ZERO} or {@link Bit#ONE}
     * @throws UnconnectedLineException if the InputLine is not connected
     */
    @Override
    public Bit getState() throws UnconnectedLineException {
        if (connectedLine == null)
            throw new UnconnectedLineException("Input line not connected to a source");
        return connectedLine.getState();
    }
}

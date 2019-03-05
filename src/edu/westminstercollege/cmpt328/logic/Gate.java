package edu.westminstercollege.cmpt328.logic;

/**
 * An interface representing a gate circuit element, i.e.:
 * <ul>
 *     <li>has one or more {@link InputLine}s;</li>
 *     <li>has exactly one output {@link Line};</li>
 *     <li>the value of the output is determined by the inputs alone.</li>
 * </ul>
 * Since a Gate has a single output, it is basically a line itself, so this interface extends {@link Line}. Thus an
 * {@link InputLine} can be connected to a Gate.
 */
public interface Gate extends Line {

    /**
     * Returns the current state of this gate &mdash; the bit (voltage) coming out of it.
     * @throws UnconnectedLineException if any of this Gate's input lines are disconnected and hence it has
     *                                  indeterminate state
     */
    @Override
    Bit getState() throws UnconnectedLineException;
}

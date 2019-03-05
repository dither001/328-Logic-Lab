package edu.westminstercollege.cmpt328.logic;

/**
 * An exception thrown when the state of a {@link Line} is requested but the line is not connected. If this exception is
 * thrown, it most likely means there is an {@link InputLine} or {@link InputMultiLine} somewhere in your program that
 * has not been connected.
 */
public class UnconnectedLineException extends RuntimeException {

    public UnconnectedLineException() {
        super();
    }

    public UnconnectedLineException(String message) {
        super(message);
    }

    public UnconnectedLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnconnectedLineException(Throwable cause) {
        super(cause);
    }

    protected UnconnectedLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

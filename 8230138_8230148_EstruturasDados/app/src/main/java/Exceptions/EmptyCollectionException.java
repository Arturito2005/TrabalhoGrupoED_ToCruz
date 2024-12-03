package Exceptions;

public class EmptyCollectionException extends RuntimeException{

    public EmptyCollectionException() {
    }

    /**
     * Constructs an instance of <code>EmptyCollectionExection</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyCollectionException(String msg) {
        super(msg);
    }
}

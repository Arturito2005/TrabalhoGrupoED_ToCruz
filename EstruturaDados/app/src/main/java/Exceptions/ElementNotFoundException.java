package Exceptions;

/**
 * This class defines a custom exception called
 * {@code ElementNotFoundException}.
 */
public class ElementNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of <code>ElementNotFoundException</code> without
     * detail message.
     */
    public ElementNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ElementNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}

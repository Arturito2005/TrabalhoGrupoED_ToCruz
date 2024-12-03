package Exceptions;

public class WrongTypeItem extends RuntimeException {
    public WrongTypeItem(String message) {
        super(message);
    }
}

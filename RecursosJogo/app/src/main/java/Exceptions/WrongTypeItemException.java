package Exceptions;

/**
 * Classe da Exceção WrongTypeItem, esta exceção é chamada sempre que é introduzido um tipo de item errado
 *
 * @author Artur Pinto
 * @author Francisco
 * @version 1.0
 * */
public class WrongTypeItemException extends RuntimeException {

    /**
     * Metodo construtor da classe WrongTypeItem
     * */
    public WrongTypeItemException(String message) {
        super(message);
    }
}

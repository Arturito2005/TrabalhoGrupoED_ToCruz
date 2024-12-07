package Exceptions;

/**
 * Classe da Exceção WrongTypeItem, esta exceção é chamada sempre que é introduzido um tipo de item errado
 * */
public class WrongTypeItem extends RuntimeException {

    /**
     * Metodo construtor da classe WrongTypeItem
     * */
    public WrongTypeItem(String message) {
        super(message);
    }
}

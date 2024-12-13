package Exceptions;

/**
 * Classe da Exceção WrongTypeItem, esta exceção é chamada sempre que é introduzido um tipo de item errado
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 * */
public class WrongTypeItemException extends RuntimeException {

    /**
     * Construtor da exceção WrongTypeItemException.
     * Inicializa a exceção com uma mensagem detalhando o erro.
     *
     * @param message A mensagem que descreve o erro específico.
     */
    public WrongTypeItemException(String message) {
        super(message);
    }
}

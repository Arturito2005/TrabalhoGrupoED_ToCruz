package Exceptions;

/**
 * Exceção personalizada que indica uma tentativa de reutilizar um item já coletado ou usado em um contexto onde isso não é permitido.
 *
 * @author Artur Pinto
 * @author Francisco
 * @version 1.0
 */
public class UsedColectedItemException extends RuntimeException {

    /**
     * Construtor da exceção que aceita uma mensagem descritiva.
     *
     * @param message Mensagem que descreve o motivo do erro.
     */
    public UsedColectedItemException(String message) {
        super(message);
    }
}

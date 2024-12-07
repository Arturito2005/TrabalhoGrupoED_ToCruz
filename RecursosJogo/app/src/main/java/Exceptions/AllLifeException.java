package Exceptions;

/**
 * Exceção personalizada que é lançada quando o To Cruz tem a vida toda e tenta curar-se.
 *
 * @author Artur
 * @version 1.0
 */
public class AllLifeException extends RuntimeException {

    /**
     * Construtor da exceção AllLifeException.
     * Inicializa a exceção com uma mensagem específica.
     *
     * @param message A mensagem detalhada explicando o motivo da exceção.
     */
    public AllLifeException(String message) {
        super(message);
    }
}

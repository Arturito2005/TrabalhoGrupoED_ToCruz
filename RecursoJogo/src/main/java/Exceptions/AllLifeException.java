package Exceptions;

/**
 * Exceção personalizada que é lançada quando o To Cruz tem a vida cheia e tenta curar-se.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class AllLifeException extends RuntimeException {

    /**
     * Construtor da exceção AllLifeException.
     * Inicializa a exceção com uma mensagem específica.
     *
     * @param message A mensagem detalhada a explicar o motivo da exceção.
     */
    public AllLifeException(String message) {
        super(message);
    }
}

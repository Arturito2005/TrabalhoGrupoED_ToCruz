package Exceptions;

/**
 * Exceção personalizada que é lançada quando o To Cruz tem a vida toda e tenta curar-se.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
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

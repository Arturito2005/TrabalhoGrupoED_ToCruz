package Exceptions;

/**
 * Exceção personalizada que representa uma tentativa de uso ou manipulação de um item de tipo inválido em um contexto específico.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class InvalidTypeItemException extends RuntimeException {

    /**
     * Construtor da exceção que aceita uma mensagem descritiva.
     *
     * @param message Mensagem que descreve o motivo do erro.
     */
    public InvalidTypeItemException(String message) {
        super(message);
    }
}

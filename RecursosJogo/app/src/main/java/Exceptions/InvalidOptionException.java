package Exceptions;

/**
 * Exceção personalizada que representa uma situação onde uma opção inválida é selecionada.
 *
 * @author Artur Pinto
 * @author Francisco
 * @version 1.0
 */
public class InvalidOptionException extends RuntimeException {

    /**
     * Construtor que permite criar a exceção com uma mensagem detalhada.
     *
     * @param message Mensagem explicativa da exceção, fornecida para detalhar
     *                o motivo do erro.
     */
    public InvalidOptionException(String message) {
        super(message);
    }
}

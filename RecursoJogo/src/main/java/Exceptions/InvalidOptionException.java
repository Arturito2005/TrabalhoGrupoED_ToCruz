package Exceptions;

/**
 * Exceção personalizada que é lançada quando uma opção inválida é selecionada.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class InvalidOptionException extends RuntimeException {

    /**
     * Construtor que permite criar a exceção com uma mensagem detalhada.
     *
     * @param message Mensagem explicativa da exceção fornecida para detalhar
     *                o motivo do erro.
     */
    public InvalidOptionException(String message) {
        super(message);
    }
}

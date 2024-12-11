package Personagens;

/**
 * Esta classe representa um inimigo no jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 *
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public class Inimigo extends Personagem {

    /**
     * Construtor da classe Inimigo.
     *
     * @param nome  Nome do inimigo.
     * @param poder Poder do inimigo.
     */
    public Inimigo(String nome, double poder) {
        super(nome, poder);
    }

    /**
     * Construtor da classe Inimigo.
     * Inicializa um novo objeto Inimigo com as propriedades fornecidas.
     *
     * @param id_personagem O ID do personagem inimigo.
     * @param nome O nome do inimigo.
     * @param vida A quantidade de vida do inimigo.
     * @param poder O poder do inimigo.
     */
    public Inimigo(int id_personagem, String nome, double vida, double poder) {
        super(id_personagem, nome, vida, poder);
    }

    /**
     * Retorna a representação uma String com os dados do inimigo.
     *
     * @return String com os detalhes do inimigo.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Compara este alvo com outro objeto para verificar se são iguais.
     * A comparação é baseada no identificador único e no nome do alvo.
     *
     * @param o o objeto a ser comparado.
     * @return true se os alvos forem iguais (mesmo identificador ou mesmo nome),
     * caso contrário false.
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * Gera um código hash para o alvo baseado no nome.
     *
     * @return o código hash do nome do alvo.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

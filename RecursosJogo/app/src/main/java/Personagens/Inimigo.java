package Personagens;

/**
 * Esta classe representa um inimigo no jogo.
 *
 * @author Francisco
 * @author Artur Pinto
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

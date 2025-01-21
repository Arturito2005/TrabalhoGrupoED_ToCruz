package Jogo;

import Interfaces.Jogo.JogoInterface;
import Interfaces.QueueADT;
import Interfaces.StackADT;
import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;

import java.util.Iterator;
import java.util.Random;

public class Jogo implements JogoInterface {

    private boolean collectedAlvo;

    private ToCruz toCruz;

    private UnorderedListADT<Inimigo> inimigosDead;

    private UnorderedListADT<Item> collectedItem;

    private QueueADT<Divisao> percursoToCruz;

    private Edificio edificio;

    public Jogo(Edificio edificio) {
        this.collectedAlvo = false;
        this.toCruz = new ToCruz();
        this.inimigosDead = new LinearLinkedUnorderedList<>();
        this.collectedItem = new LinearLinkedUnorderedList<>();
        this.percursoToCruz = new LinkedQueue<>();
        this.edificio = edificio;
    }

    @Override
    public void getAlvo() {
        this.collectedAlvo = true;
    }

    @Override
    public void inimigoDead(Inimigo inimigo) {
        this.inimigosDead.addToRear(inimigo);
    }

    @Override
    public void collectedItem(Item item) {
        this.collectedItem.addToRear(item);
    }

    @Override
    public void attackInimigo(Inimigo inimigo) {
        long vidaTo = toCruz.getVida() - inimigo.getPoder();
        toCruz.setVida(vidaTo);

        System.out.println("O imimigo " + inimigo.getNome() + " atacou o To Cruz");

        if (toCruz.isDead()) {
            System.out.println("O inimigo " + inimigo.getNome() + " matou o To Cruz");
            toCruz.setVida(0);
        } else {
            System.out.println("To Cruz resiste ao ataque e fica com " + vidaTo + " HP");
        }
    }

    /**
     * Metodo que calcula o poder total final de uma divisão, somando o poder inicial
     * fornecido com o poder dos inimigos presentes na divisão.
     *
     * Caso a divisão não tenha inimigos ou contenha apenas um, o poder inicial é retornado.
     * Caso existam vários inimigos, os seus poderes são somados ao poder inicial.
     *
     * @param div A divisão cuja força total será calculada.
     * @param poder_inimigo O poder inicial base dos inimigos.
     * @return O poder total calculado da divisão.
     */
    private double poderFinalDivisao(Divisao div, long poder_inimigo) {
        long poder_total = poder_inimigo;
        if (div.getInimigos().isEmpty() && div.getInimigos().size() > 1) {
            for (Inimigo inimigo_div : div.getInimigos()) {
                poder_total += inimigo_div.getPoder();
            }
        }

        return poder_total;
    }

    @Override
    public Divisao moverInimigo(Divisao divisao_atual, Inimigo inimigo) {
        Random randomizer = new Random();
        int numMoves = randomizer.nextInt(3);
        long poder_inimigo = inimigo.getPoder();

        for (int i = 0; i < numMoves; i++) {
            edificio.updateWeight(divisao_atual, poderFinalDivisao(divisao_atual, poder_inimigo) - poder_inimigo);
            StackADT<Divisao> stDiv = new LinkedStack<>();
            Divisao divisaoEscolhida;
            Iterator<Divisao> itrDiv = this.edificio.getNextDivisoes(divisao_atual);

            while (itrDiv.hasNext()) {
                stDiv.push(itrDiv.next());
            }

            int rand = randomizer.nextInt(stDiv.size());

            while (stDiv.size() - 1 > rand && !stDiv.isEmpty()) {
                stDiv.pop();
            }

            divisaoEscolhida = stDiv.peek();
            divisao_atual.removeInimigo(inimigo);

            divisaoEscolhida.addInimigo(inimigo);
            this.edificio.updateWeight(divisaoEscolhida, poderFinalDivisao(divisaoEscolhida, poder_inimigo));
            divisao_atual = divisaoEscolhida;
        }
        System.out.println("O inimigo " + inimigo.getNome() + " moveu-se para a sala: " + divisao_atual.getName());
        return divisao_atual;
    }

    //Meter throw InvalidDivisaoExeception, por exemplo
    @Override
    public void usarItemDivisao(Item item, Divisao divisao){
        if (divisao.containItem(item)) {
            try {
                this.toCruz.usarItem((ItemCura) item);
                this.collectedItem.addToRear(item);
                divisao.removeItem(item);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("O item desta divisao ja foi usado anteriormente");
        }
    }

    //Meter os Thorws do guardarKit, ou verificação para permitir apenas guardarKits
    public void guardarItemMochila(Item item, Divisao divisao) {
        if(divisao.containItem(item)) {
            try {
                this.toCruz.guardarKit((ItemCura) item);
                this.collectedItem.addToRear(item);
                divisao.removeItem(item);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void moverToCruz(Divisao divisaoAtual, Divisao novaDivisao) {
        divisaoAtual.removeToCruz();
        percursoToCruz.enqueue(divisaoAtual);
        novaDivisao.addToCruz(toCruz);
    }

    @Override
    public void attackToCruz(Inimigo inimigo, Divisao divisao) {
        System.out.println("Turno do To Cruz atacar!");
        inimigo.setVida(inimigo.getVida() - this.toCruz.getPoder());

        if (inimigo.isDead()) {
            System.out.println("O To Cruz matou o inimigo " + inimigo.getNome());
            inimigosDead.addToRear(inimigo);
            divisao.removeInimigo(inimigo);
        } else {
            System.out.println("O inimigo " + inimigo.getNome() + " resitiu ao ataque do To Cruz e ficou com: " + inimigo.getVida() + " HP");
        }
    }

    @Override
    public void relatorioMissao() {
        System.out.println(" ");
        System.out.println("!---------------------------------!");
        System.out.println(" ");
        System.out.println("Fim do jogo");
        System.out.println("Relatorio do jogo: ");
        System.out.println("Vida final do ToCruz --> " + toCruz.getVida());

        if (this.toCruz.getVida() > 0 && toCruz.isColectedAlvo()) {
            System.out.println("Missao realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
        } else {
            System.out.println("Missao falhada ಥ_ಥ");
        }

        
        if (!inimigosDead.isEmpty()) {
            System.out.println("Numero de inimigos mortos: " + inimigosDead.size());
            System.out.println(inimigosDead.toString());
        }
        
        if (!collectedItem.isEmpty()) {
            System.out.println("Numero de itensColetados: " + collectedItem.size());
            System.out.println("Itens coletados pelo ToCruz:");
            this.collectedItem.toString();
        } else {
            System.out.println("O ToCruz não conseguiu coletar nenhum item.");
        }

        StackADT<ItemCura> mochila = toCruz.getMochila();
        if (!mochila.isEmpty()) {
            System.out.println("Itens na mochila do ToCruz:");
            mochila.toString();
        } else {
            System.out.println("A mochila do To Cruz esta vazia.");
        }

        System.out.println("Percurso feito pelo ToCruz:");
        System.out.println(percursoToCruz.toString());
    }
}

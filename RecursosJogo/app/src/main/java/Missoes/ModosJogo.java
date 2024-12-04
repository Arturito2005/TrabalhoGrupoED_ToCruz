package Missoes;

import Items.ItemCura;
import Mapa.Divisao;
import Personagens.Inimigo;

import java.util.Iterator;
import java.util.Random;

public abstract class ModosJogo extends Missao{
    protected boolean ToCruzinExit() {
        boolean exit = false;

        if (toCruz.getDivisao().isEntrada_saida()) {
            exit = true;
        }

        return exit;
    }

    protected boolean existeConfronto() {
        boolean existe = false;

        if (!this.inimigos_confronto.isEmpty()) {
            existe = true;
        }

        return existe;
    }

    protected void iniciarConfronto() {
        Iterator<Inimigo> iterator = inimigos.iterator();

        while (iterator.hasNext()) {
            Inimigo inimigo = iterator.next();
            if (inimigo.getDivisao().equals(toCruz.getDivisao())) {
                iterator.remove(); // Remove da lista de inimigos
                this.inimigos_confronto.addToRear(inimigo); // Adiciona à lista de confronto
            }
        }
    }

    protected void usarItem(ItemCura item) {
        toCruz.usarItem(item);
        this.item_colected.push(this.item_list.remove(item));
    }

    protected void guardarItem(ItemCura item) {
        toCruz.guardarKit(item);
        this.item_colected.push(this.item_list.remove(item));
    }

    protected void attackToCruz() {
        Iterator<Inimigo> iterator = inimigos_confronto.iterator();
        while (iterator.hasNext()) {
            Inimigo inimigo = iterator.next();
            inimigo.setVida(inimigo.getVida() - toCruz.getPoder());

            if (inimigo.isDead()) {
                System.out.println("O inimigo " + inimigo.getNome() + " HP");
                iterator.remove();
                this.inimigos_dead.push(inimigo);
            } else {
                System.out.println("Vida do inimigo " + inimigo.getNome() + ": " + inimigo.getVida() + " HP");
            }
        }
    }

    private void attackInimigo(Inimigo inimigo) {
        double vidaTo = toCruz.getVida() - inimigo.getPoder();
        toCruz.setVida(toCruz.getVida() - inimigo.getPoder());

        System.out.println("O imimigo" + inimigo.getNome() + " atacou o To Cruz");

        if (toCruz.isDead()) {
            System.out.println("O inimigo " + inimigo.getNome() + "matou o To Cruz");
        } else {
            System.out.println("To Cruz resiste ao ataque e fica com " + vidaTo + " HP");
        }
    }

    protected ItemCura colectItem() {
        ItemCura item_div = null;
        if (!item_list.isEmpty()) {
            boolean find = false;
            Iterator<ItemCura> itr = this.item_list.iterator();

            while (itr.hasNext() && !find) {
                ItemCura item = itr.next();
                if (toCruz.getDivisao().equals(item.getDivisao())) {
                    this.item_colected.push(this.item_list.remove(item));
                    item_div = item;
                    find = true;
                }
            }
        }

        return item_div;
    }

    private void moverInimigo(Inimigo inimigo) {
        Random randomizer = new Random();
        int numMoves = randomizer.nextInt(3);
        Divisao[] tmpDivisoes = new Divisao[getEdificio().getPlantaEdificio().size()];
        Divisao divisaoEscolhida = null;
        Iterator<Divisao> it = null;
        int cont = 0;

        for (int i = 0; i < numMoves; i++) {
            it = getEdificio().getPlantaEdificio().iteratorBFSNextDivisoes(inimigo.getDivisao());
            while (it.hasNext()) {
                // guarda cada uma das divisões adjacentes num array auxiliar
                tmpDivisoes[cont++] = it.next();
            }
            // escolhe uma das divisões adjacentes à divisão onde o inimigo está
            divisaoEscolhida = tmpDivisoes[randomizer.nextInt(cont)];
            inimigo.setDivisao(divisaoEscolhida);
            // limpar array para o caso de haver o próximo move
            for (int j = 0; j < cont; j++) {
                tmpDivisoes[j] = null;
            }
            cont = 0;
        }
    }

    private void UpdateWeightInimigo(Inimigo inimigo, double weight) {
        if(inimigo.getVida() > toCruz.getPoder()) {
            this.edificio.updateLigacoa(inimigo.getDivisao(), weight);
        }
    }

    protected void turnoInimigo() {
        //Depois retirar println quando tivermos o mapa
        for (Inimigo inimigo : inimigos) {
            UpdateWeightInimigo(inimigo, 0);

            System.out.println(inimigo.getDivisao());
            moverInimigo(inimigo);
            System.out.println(inimigo.getDivisao());

            UpdateWeightInimigo(inimigo, inimigo.getPoder());
        }

        if (existeConfronto()) {
            for (Inimigo inimigo : inimigos_confronto) {
                attackInimigo(inimigo);
            }
        }
    }

    protected void relatoriosMissao() {
        if (!toCruz.isDead() && (alvo.isAtinigido())) {
            System.out.println("Missão realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
            System.out.println("Total de vida do ToCruz --> " + toCruz.getVida());
        } else {
            System.out.println("Missão falhada ಥ_ಥ");
        }

        System.out.println("Numero de inimigos mortos: " + inimigos_dead.size());

        if (!inimigos_dead.isEmpty()) {
            System.out.println("Inimigos mortos pelo o To Cruz:");
            System.out.println(inimigos_dead.toString());
        }

        System.out.println("Numero de itensColetados: " + item_colected.size());
        if (!item_colected.isEmpty()) {
            System.out.println("Itens coletados pelo o ToCruz:");
            System.out.println(item_colected.toString());
        }

        if (!toCruz.getMochila().isEmpty()) {
            System.out.println("Itens na mochilda do ToCruz:");
            System.out.println(toCruz.getMochila().toString());
        } else {
            System.out.println("A mochila do To Cruz está vazia.");
        }

        System.out.println("Percurso feito pelo o ToCruz:");
        System.out.print(trajeto_to.toString());
    }
}

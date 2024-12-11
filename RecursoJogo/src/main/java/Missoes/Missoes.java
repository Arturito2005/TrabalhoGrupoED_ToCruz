package Missoes;

import Interfaces.MissoesInt;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedUnorderedList;

public class Missoes implements MissoesInt {

    private int contMissoes;

    private UnorderedListADT<Missao> listaMissao;

    public Missoes() {
        this.contMissoes = 0;
        listaMissao = new LinearLinkedUnorderedList<Missao>();
    }

    public int getContMissoes() {
        return this.contMissoes;
    }

    public UnorderedListADT<Missao> getListaMissao() {
        return this.listaMissao;
    }

    @Override
    public void addMissao(Missao missao) throws NullPointerException {
        if(missao == null) {
            throw new NullPointerException("Não é possível introduzir missões nulas");
        }
        listaMissao.addToRear(missao);
        this.contMissoes++;
    }

    /*
    * Talvez é melhor meter o exportar aqui porque tenho acesso a todas as missões!
    * E assim é só fazer um foreach das minhas para guarda-las.
    * */
    public void exportarMissoes() {
        /*
         *Adpatar com o codigo do Xico.
         * ExportarDado exportarDado = new ExportarDado();

        for(Missao missao: listaMissao) {
        * if(!missao.simulacoes.IsEmpty()) {
            exportarDado.exportarDados(missao);
        }
         *  */
    }

}
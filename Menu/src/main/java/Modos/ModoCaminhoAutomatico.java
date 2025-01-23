package Modos;

import Mapa.Edificio;

public class ModoCaminhoAutomatico {

    /**
     * Executa o modo automático da missão, mostrando o caminho mais curto para o ToCruz entrar no
     * edifício e coletar o alvo, e o melhor caminho que ele deve fazer, se possível,
     * para sair do edifício com o alvo.
     */
    @Override
    public void modoAutomatico() {
        Jogo simulacaoAutomatica = new Jogo(tot_simulacoes, new Edificio(this.edificio.getId(), this.edificio.getName(), this.edificio.getPlantaEdificio()));
        simulacaoAutomatica.modojogoAutomatico();
    }
}

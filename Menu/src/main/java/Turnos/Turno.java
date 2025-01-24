package Turnos;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosPersonagem;
import Interfaces.Turno.TurnoInt;
import Mapa.Divisao;

/*Talvez seja melhot em vez de receber os cenarios, receber antes as simulações e inicializar cada cenarios com a mesma simulação
* E meter uma variavel de instacia para guardar a simulação dos cenarios
* */
public abstract class Turno implements TurnoInt {

    private CenariosPersonagem cenarioPersonagens;

    private CenariosDivisao cenariosDivisao;

    /*Para resolver este problema este problmea de cenarios e garantir que ambos são da mesma simulação posso criar uma enum para o tipo de turno, caso seja To inicia o cenario como cenario do To, se nao cenario do inimigo
    * */
    public Turno(CenariosPersonagem cenarioPersonagens, CenariosDivisao cenariosDivisao) {
        this.cenarioPersonagens = cenarioPersonagens;
        this.cenariosDivisao = cenariosDivisao;
    }

    public CenariosPersonagem getCenarioPersonagens() {
        return cenarioPersonagens;
    }

    public CenariosDivisao getCenariosDivisao() {
        return cenariosDivisao;
    }

    public abstract void turno(Divisao divisao);
}

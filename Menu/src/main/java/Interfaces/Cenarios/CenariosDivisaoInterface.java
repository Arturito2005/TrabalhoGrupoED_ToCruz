package Interfaces.Cenarios;

import Exceptions.ElementNotFoundException;
import Mapa.Divisao;

public interface CenariosDivisaoInterface {

    public boolean isToCruzInExit(Divisao divisao) throws ElementNotFoundException;

    public boolean isToCruzInDivisaoAlvo(Divisao divisao) throws ElementNotFoundException;

    public boolean haveConfronto(Divisao divisao) throws ElementNotFoundException;
}

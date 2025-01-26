package Interfaces.Cenarios;

import Exceptions.ElementNotFoundException;
import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Mapa.Divisao;
import Personagens.ToCruz;

public interface CenariosDivisaoInterface {

    public boolean isToCruzInExit(Divisao divisao) throws ElementNotFoundException;

    public boolean isToCruzInDivisaoAlvo(Divisao divisao) throws ElementNotFoundException;

    public boolean haveConfronto(Divisao divisao) throws ElementNotFoundException;

    public void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException;

    public void collectAlvo();
}

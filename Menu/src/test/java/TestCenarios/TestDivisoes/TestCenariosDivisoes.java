package TestCenarios.TestDivisoes;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosToCruz;
import Importar.ImportarMapa;
import Jogo.Simulacao;
import Mapa.Alvo;
import Mapa.Divisao;
import Missoes.Missao;
import Missoes.Versao;
import Personagens.ToCruz;
import TestCenarios.TestCenarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testes completos
 */
public class TestCenariosDivisoes extends TestCenarios {

    private static CenariosDivisao cenariosDivisao;

    private static CenariosToCruz cenariosToCruz;

    private static ToCruz toCruz;

    @BeforeEach
    void setUp() {
        ImportarMapa importarMapa = new ImportarMapa();
        Missao missao;

        try {
            missao = importarMapa.gerarMapa(path_name_def);
            Versao versao = missao.getVersao();
            simulacao = new Simulacao(versao);
            toCruz = simulacao.getToCruz();
            cenariosDivisao = new CenariosDivisao(simulacao);
            cenariosToCruz = new CenariosToCruz(simulacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testIsToCruzInExitComSucesso() {
        cenariosDivisao.collectAlvo();
        Divisao divisao_atual = new Divisao("Teste");
        Divisao divisao_final = new Divisao(90, "Saida teste", true, null, null, null, toCruz);
        cenariosToCruz.moverToCruz(divisao_atual, divisao_final);
        assertTrue(cenariosDivisao.isToCruzInExit(divisao_final));
    }

    @Test
    void testIsToCruzInExitDaSemSucesso() {
        Divisao divisao_atual = new Divisao("Teste");
        Divisao divisao_final = new Divisao(90, "Saida teste", false, null, null, null, toCruz);
        cenariosToCruz.moverToCruz(divisao_atual, divisao_final);
        assertFalse(cenariosDivisao.isToCruzInExit(divisao_final));
    }

    @Test
    void testIsToCruzInDivisaoAlvoComSucesso() {
        Alvo alvo = new Alvo("Alvo");
        Divisao divisao_final = new Divisao(90, "Saida teste", true, alvo, null, null, toCruz);
        cenariosToCruz.moverToCruz(divisao_final, divisao_final);

        assertTrue(cenariosDivisao.isToCruzInDivisaoAlvo(divisao_final));
    }

    @Test
    void testHaveConfrontoSemSucesso() {
        Alvo alvo = new Alvo("Alvo");
        Divisao divisao_final = new Divisao(90, "Saida teste", true, alvo, null, null, null);
        assertFalse(cenariosDivisao.isToCruzInDivisaoAlvo(divisao_final));
    }
}

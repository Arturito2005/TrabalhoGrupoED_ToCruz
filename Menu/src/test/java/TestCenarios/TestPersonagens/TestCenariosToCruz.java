package TestCenarios.TestPersonagens;

import Cenarios.Personagens.CenariosToCruz;
import Importar.ImportarMapa;
import Jogo.Simulacao;
import Mapa.Divisao;
import Mapa.Edificio;
import Missoes.Missao;
import Missoes.Versao;
import Personagens.Inimigo;
import TestCenarios.TestCenarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestCenariosToCruz extends TestCenarios {

    private CenariosToCruz cenariosToCruz;

    private Edificio edificio;

    @BeforeEach
    void setUp() {
        ImportarMapa importarMapa = new ImportarMapa();
        Missao missao;

        try {
            missao = importarMapa.gerarMapa(path_name_def);
            Versao versao = missao.getVersao();
            simulacao = new Simulacao(versao);
            edificio = simulacao.getEdificio();
            cenariosToCruz = new CenariosToCruz(simulacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Divisao getDivisaoToCruz() {
        Iterator<Divisao> itrDiv = edificio.IteratorMapa();
        Divisao div = new Divisao("Divisao Teste");

        boolean found = false;

        while (itrDiv.hasNext() && !found) {
            Divisao div_i = itrDiv.next();
            if(div_i.getToCruz() != null) {
                div = div_i;
            }
        }

        return div;
    }

    @Test
    void testSugestaoCaminhoToCruzKitEAlvo() {
        Divisao divisaoAtual = getDivisaoToCruz();
        cenariosToCruz.sugestaoCaminhoToCruzKitEAlvo(divisaoAtual);
    }

    @Test
    void testAtaqueToCruzComInimigos() {
        Divisao divisao = getDivisaoToCruz();
        Inimigo inimigo1 = new Inimigo(2,"Inimigo1", 10, 20);
        Inimigo inimigo2 = new Inimigo(1, "Inimigo2", 10, 20);
        divisao.addInimigo(inimigo1);
        divisao.addInimigo(inimigo2);

        cenariosToCruz.ataqueToCruz(divisao);

        assertTrue(inimigo1.isDead() || inimigo2.isDead());
    }

    @Test
    void testMoverToCruzComDivisaoNula() {
        Divisao divisaoAtual = getDivisaoToCruz();

        assertThrows(NullPointerException.class, () -> cenariosToCruz.moverToCruz(divisaoAtual, null));
        assertThrows(NullPointerException.class, () -> cenariosToCruz.moverToCruz(null, divisaoAtual));
    }

    @Test
    void testMoverToCruzComDivisoesValidas() {
        Divisao divisaoAtual = getDivisaoToCruz();
        Divisao novaDivisao = new Divisao("Nova Sala");

        assertDoesNotThrow(() -> cenariosToCruz.moverToCruz(divisaoAtual, novaDivisao));

        assertNull(divisaoAtual.getToCruz());
        assertNotNull(novaDivisao.getToCruz());
    }
}

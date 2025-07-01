package TestCenarios.TestPersonagens;

import Cenarios.Personagens.CenariosInimigos;
import Importar.ImportarMapa;
import Mapa.Divisao;
import Missoes.Missao;
import Personagens.Inimigo;
import Jogo.Simulacao;
import Missoes.Versao;
import TestCenarios.TestCenarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class TestCenariosInimigos extends TestCenarios {

    private CenariosInimigos cenariosIni;

    @BeforeEach
    void setUp() {
        ImportarMapa importarMapa = new ImportarMapa();
        Missao missao;

        try {
            missao = importarMapa.gerarMapa(path_name_def);
            Versao versao = missao.getVersao();
            simulacao = new Simulacao(versao);
            cenariosIni = new CenariosInimigos(simulacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void andarComSucesso() {
        Divisao divisao = new Divisao(1, "Heliporto", true, null, null, null, null);
        Inimigo inimigo = new Inimigo(15, "Inimigo Teste", 100, 15);
        cenariosIni.andar(inimigo, divisao);
    }

    /*
    Este teste por vezes passa e outros não
    * @Test
    void testMovimentacaoInimigoEntreDivisoes() {
        Edificio edificio = new Edificio();
        Divisao divA = new Divisao("A");
        Divisao divB = new Divisao("B");
        edificio.addDivisao(divA);
        edificio.addDivisao(divB);
        edificio.addLigacao(divA, divB, 12);

        Simulacao simulacao = new Simulacao(new Versao(1, edificio));
        CenariosInimigos cenarios = new CenariosInimigos(simulacao);

        Inimigo inimigo = new Inimigo("Inimigo", 10);
        divA.addInimigo(inimigo);

        Divisao resultado = cenarios.andar(inimigo, divA);

        assertFalse(divA.getInimigos().contains(inimigo));
        assertTrue(resultado.getInimigos().contains(inimigo));
        assertTrue(resultado == divA || resultado == divB);
    }
    * */



}

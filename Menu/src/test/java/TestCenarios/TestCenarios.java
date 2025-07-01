package TestCenarios;

import Cenarios.Divisao.CenariosDivisao;
import Importar.ImportarMapa;
import Jogo.Simulacao;
import Missoes.Missao;
import Missoes.Versao;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCenarios {

    protected Simulacao simulacao;

    protected static final String path_name_def = "./Jsons/Import/FicheiroMissao.json";

    @Test
    public void testGetSimulacao() {
        ImportarMapa importarMapa = new ImportarMapa();
        Missao missao;

        try {
            missao = importarMapa.gerarMapa(path_name_def);
            Versao versao = missao.getVersao();
            Simulacao simulacao = new Simulacao(versao);
            CenariosDivisao cenariosDivisao = new CenariosDivisao(simulacao);
            assertEquals(cenariosDivisao.getSimulacao(), simulacao, "As duas simulacoes são iguais");
            System.out.println(simulacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package TestCenarios.TestPersonagens;

import Cenarios.Personagens.CenariosPersonagem;
import Importar.ImportarMapa;
import Jogo.Simulacao;
import Missoes.Missao;
import Missoes.Versao;
import Personagens.Inimigo;
import Personagens.Personagem;
import Personagens.ToCruz;
import Stacks.LinkedStack;
import TestCenarios.TestCenarios;
import Cenarios.Personagens.CenariosToCruz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestCenariosPersonagens extends TestCenarios {

    private CenariosPersonagem cena_personagem;

    private Personagem atacante;

    private Personagem atacado;

    @BeforeEach
    public void setup() {
        ImportarMapa importarMapa = new ImportarMapa();
        Missao missao;

        try {
            missao = importarMapa.gerarMapa(path_name_def);
            Versao versao = missao.getVersao();
            simulacao = new Simulacao(versao);
            cena_personagem = new CenariosToCruz(simulacao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testComUmaPersonagemMorta() {
        atacante = new ToCruz(1, "Atacante", 0, 10, new LinkedStack<>()); // vida zero
        atacado  = new Inimigo(2,"Atacado", 100, 5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cena_personagem.ataque(atacante, atacado);
        });
        assertEquals("Um dos personagens já se encontra morto!", exception.getMessage());
    }

    @Test
    public void testeAtaqueNaoMataPersonagem() {
        int vidaInicialAtacado = 50;
        int poderAtacante = 20;
        atacante = new ToCruz(1,"Atacante", 100, poderAtacante, new LinkedStack<>());
        atacado  = new Inimigo(1, "Atacado", vidaInicialAtacado, 5);

        cena_personagem.ataque(atacante, atacado);

        int vidaEsperada = vidaInicialAtacado - poderAtacante;
        assertEquals(vidaEsperada, atacado.getVida());
        assertFalse(atacado.isDead());
    }

    /**
     * Testa um ataque que “mata” o personagem atacado.
     * Mesmo que o resultado da subtração seja negativo, a vida final deve ser fixada em 0.
     */
    @Test
    public void testeAtaqueMataPersonagem() {
        int vidaInicialAtacado = 15;
        int poderAtacante = 20;
        Personagem atacante = new ToCruz(1, "Atacante", 100, poderAtacante, new LinkedStack<>());
        Personagem atacado  = new Inimigo(1, "Atacado", vidaInicialAtacado, 5);

        cena_personagem.ataque(atacante, atacado);

        assertEquals(0, atacado.getVida());
        assertTrue(atacado.isDead());
    }
}

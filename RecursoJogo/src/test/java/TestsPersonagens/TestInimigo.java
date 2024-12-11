package TestsPersonagens;

import static org.junit.jupiter.api.Assertions.*;

import Personagens.Inimigo;
import Personagens.Personagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInimigo {
    private Personagem personagem1;
    private Personagem personagem2;
    private Personagem personagem3;

    @BeforeEach
    public void setUp() {
        personagem1 = new Inimigo(1, "Aragorn", 100, 50);
        personagem2 = new Inimigo(2,"Legolas", 100, 60);
        personagem3 = new Inimigo(3, "Aragorn", 100, 50);
    }

    @Test
    public void testEquals() {
        assertTrue(personagem1.equals(personagem3));
        assertFalse(personagem1.equals(personagem2));
        assertFalse(personagem1.equals(null));
        assertFalse(personagem1.equals("OutraClasse"));
    }

    @Test
    public void testIsDead() {
        personagem1.setVida(100);
        assertFalse(personagem1.isDead());
        personagem1.setVida(0);
        assertTrue(personagem1.isDead());
        personagem2.setVida(-1);
        assertTrue(personagem2.isDead());
    }
}

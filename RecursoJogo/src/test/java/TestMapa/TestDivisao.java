package TestMapa;

import Mapa.Divisao;
import Personagens.Inimigo;
import Personagens.ToCruz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDivisao {

    private Divisao divisao;

    private ToCruz toCruz;

    private Inimigo inimigo;

    @BeforeEach
    public void setUp() {
        divisao = new Divisao("Sala Teste");
        toCruz = new ToCruz(1, "ToCruz", 100, 20, null);
        inimigo = new Inimigo(2, "Inimigo", 50, 10);
        divisao.addToCruz(toCruz);
    }

    @Test
    public void testRemoveInimigoComSucesso() {
        divisao.addInimigo(inimigo);
        Inimigo removido = divisao.removeInimigo(inimigo);
        assertEquals(removido, inimigo, "Como o inimigo foi removido, será retornado o mesmo");
    }

    @Test
    public void testRemoveInimigoListaVazia() {
        Inimigo removido = divisao.removeInimigo(inimigo);
        assertNull(removido, "Quando a lista está vazia, o método ainda deve retornar null");
    }

    @Test
    public void testRemoveInimigoNulo() {
        Inimigo removido = divisao.removeInimigo(null);
        assertNull(removido, "Ao tentar remover um inimigo nulo, o método deve retornar null");
    }

    @Test
    public void testAddInimigoNulo() {
        Inimigo inimigo = null;
        NullPointerException exception = assertThrows(NullPointerException.class, () -> divisao.addInimigo(inimigo), "Adicionar um inimigo nulo deve lançar uma NullPointerException");
    }



    /*
    @Test
    void testHaveInimigoComSucessoeSemSucesso() {
        assertFalse(divisao.haveInimigo());
        divisao.addInimigo(inimigo);
        assertTrue(divisao.haveInimigo());
    }

    @Test
    void testAttackInimigo_ReduceVida() {
        divisao.addToCruz(toCruz);
        divisao.addInimigo(inimigo);
        divisao.attackInimigo(inimigo);
        assertEquals(90, toCruz.getVida(), "A vida de ToCruz deveria ser 90 após o ataque.");
    }

    @Test
    void testAttackInimigo_ToCruzDead() {
        inimigo = new Inimigo(3, "Inimigo Forte", 50, 120);
        divisao.addToCruz(toCruz);
        divisao.addInimigo(inimigo);
        divisao.attackInimigo(inimigo);
        assertEquals(0, toCruz.getVida(), "A vida de ToCruz deveria ser 0 após o ataque fatal.");
        assertTrue(toCruz.isDead(), "ToCruz deveria estar morto.");
    }

    @Test
    void testAttackInimigo_ToCruzSurvives() {
        inimigo = new Inimigo(4, "Inimigo Fraco", 50, 10);
        divisao.addToCruz(toCruz);
        divisao.addInimigo(inimigo);
        divisao.attackInimigo(inimigo);
        assertEquals(90, toCruz.getVida(), "A vida de ToCruz deveria ser 90 após o ataque.");
        assertFalse(toCruz.isDead(), "ToCruz deveria sobreviver ao ataque.");
    }

    * @Test
    public void testAttackToCruz_AllInimigosSurvive() {
        toCruz = new ToCruz(1, "ToCruz", 100, 10, null);
        divisao.addToCruz(toCruz);
        divisao.addInimigo(inimigo1);
        divisao.addInimigo(inimigo2);
        divisao.attackToCruz(deadInimigos);
        assertEquals(2, divisao.getInimigos().size(), "Nenhum inimigo deveria ser removido.");
        assertEquals(40, inimigo1.getVida(), "A vida do inimigo1 deveria ser 40.");
        assertEquals(90, inimigo2.getVida(), "A vida do inimigo2 deveria ser 90.");
        assertTrue(deadInimigos.isEmpty(), "A pilha de inimigos mortos deveria estar vazia.");
    }

    @Test
    public void testAttackToCruz_OneInimigoDies() {
        toCruz = new ToCruz(1, "ToCruz", 100, 50, null);
        divisao.addToCruz(toCruz);
        divisao.addInimigo(inimigo1);
        divisao.addInimigo(inimigo2);
        divisao.attackToCruz(deadInimigos);
        assertEquals(1, divisao.getInimigos().size(), "Deveria restar apenas um inimigo.");
        assertEquals(50, inimigo2.getVida(), "A vida do inimigo2 deveria ser 50.");
        assertFalse(deadInimigos.isEmpty(), "A pilha de inimigos mortos não deveria estar vazia.");
        assertEquals(inimigo1, deadInimigos.removeFirst(), "O inimigo1 deveria estar na pilha de inimigos mortos.");
    }

    @Test
    public void testAttackToCruz_AllInimigosDie() {
        toCruz = new ToCruz(1, "ToCruz", 100, 100, null);
        divisao.addToCruz(toCruz);
        divisao.addInimigo(inimigo1);
        divisao.addInimigo(inimigo2);
        divisao.attackToCruz(deadInimigos);

        assertTrue(divisao.getInimigos().isEmpty(), "Todos os inimigos deveriam estar mortos.");

        assertEquals(2, deadInimigos.size(), "A lista de inimigos mortos deveria conter 2 inimigos.");

        assertTrue(deadInimigos.contains(inimigo2), "O inimigo2 deveria estar na lista de inimigos mortos.");
        assertTrue(deadInimigos.contains(inimigo1), "O inimigo1 deveria estar na lista de inimigos mortos.");
    }

    @Test
    public void testAttackToCruz_NoInimigos() {
        divisao.removeInimigo(inimigo1);
        divisao.removeInimigo(inimigo2);
        divisao.attackToCruz(deadInimigos);
        assertTrue(divisao.getInimigos().isEmpty(), "Não deveria haver inimigos na divisão.");
        assertTrue(deadInimigos.isEmpty(), "A pilha de inimigos mortos deveria estar vazia.");
    }

    @Test
    public void testUsarItemDivisao_ItemJaColetado() {
        itemCura.setCollected(true);
        divisao.setItem(itemCura);
        divisao.usarItemDivisao();
        assertTrue(itemCura.isCollected(), "O item deveria permanecer como coletado.");
        assertEquals(100, toCruz.getVida(), "A vida de ToCruz não deveria mudar, pois o item já foi coletado.");
    }

    @Test
    public void testUsarItemDivisao_NullPointerException() {
        divisao.setItem(null);
        try {
            divisao.usarItemDivisao();
            fail("Deveria lançar uma NullPointerException, pois o item é nulo.");
        } catch (NullPointerException e) {
            assertNotNull(e, "Deveria lançar NullPointerException.");
        }
    }
    */
}

package TestMissoes;

import Interfaces.OrderedListADT;
import LinkedList.LinearLinkedOrderedList;
import Missoes.Missoes;
import Missoes.Missao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestesMissoes {

    private Missoes missoes;
    private Missao missao1;
    private Missao missao2;

    /**
     * Configuração inicial antes de cada teste.
     */
    @BeforeEach
    public void setUp() {
        missoes = new Missoes();
        /*
        * missao1 = new Missao("M001", 1, null);
        missao2 = new Missao("M002", 2, null);
        * */
    }

    /**
     * Testa o construtor padrão e os valores iniciais.
     */
    @Test
    public void testConstrutorPadrao() {
        assertEquals(0, missoes.getContMissoes(), "O contador de missões deve iniciar em 0");
        assertNotNull(missoes.getListaMissao(), "A lista de missões não deve ser nula");
        assertTrue(missoes.getListaMissao() instanceof OrderedListADT, "A lista deve ser uma instância de OrderedListADT");
    }

    /**
     * Testa o método addMissao ao adicionar uma missão válida.
     */
    @Test
    public void testAddMissaoValida() {
        missoes.addMissao(missao1);
        assertEquals(1, missoes.getContMissoes(), "O contador de missões deve ser incrementado após adicionar uma missão");
        assertTrue(missoes.getListaMissao().contains(missao1), "A lista de missões deve conter a missão adicionada");
    }

    /**
     * Testa o método addMissao ao adicionar uma missão nula.
     */
    @Test
    public void testAddMissaoNula() {
        assertThrows(NullPointerException.class, () -> missoes.addMissao(null), "Adicionar uma missão nula deve lançar uma exceção NullPointerException");
    }

    /**
     * Testa a adição de múltiplas missões.
     */
    @Test
    public void testAdicionarMultiplasMissoes() {
        missoes.addMissao(missao1);
        missoes.addMissao(missao2);
        assertEquals(2, missoes.getContMissoes(), "O contador de missões deve refletir o número total de missões adicionadas");
        assertTrue(missoes.getListaMissao().contains(missao1), "A lista deve conter a primeira missão adicionada");
        assertTrue(missoes.getListaMissao().contains(missao2), "A lista deve conter a segunda missão adicionada");
    }

    /**
     * Testa o comportamento da lista ao recuperar missões.
     */
    @Test
    public void testListaMissoes() {
        missoes.addMissao(missao1);
        OrderedListADT<Missao> lista = missoes.getListaMissao();
        assertNotNull(lista, "A lista de missões deve ser retornada");
        assertEquals(1, lista.size(), "A lista deve conter exatamente uma missão após a adição");
    }
}

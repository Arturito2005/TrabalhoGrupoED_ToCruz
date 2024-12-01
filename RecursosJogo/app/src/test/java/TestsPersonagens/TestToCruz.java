package TestsPersonagens;

import Exceptions.WrongTypeItem;
import Items.ItemCura;
import Items.TypeItemCura;
import Mapa.Divisao;
import Personagens.ToCruz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestToCruz {

    private ToCruz toCruz;

    private ItemCura kit;

    private Divisao divisao;

    @BeforeEach
    public void setUp() {
        this.divisao = new Divisao("Heliporto");
        this.kit = new ItemCura(divisao, TypeItemCura.KIT_VIDA, 20);
        this.toCruz = new ToCruz();
    }

    @Test
    public void testguardarKitValida() {
        String excepted = "Item Guardado";
        assertEquals(excepted, toCruz.guardarKit(kit), "Como a mochila não está cheia o kit é guardado");
    }

    @Test
    public void testguardarKitMochilaCheia() {
        String excepted = "Mochila cheia e vida cheia";
        toCruz.guardarKit(kit);
        toCruz.guardarKit(kit);
        toCruz.guardarKit(kit);
        assertEquals(excepted, toCruz.guardarKit(kit), "Como a mochila está cheia e o To Cruz tem a vida cheia o item não é guardado e continua na sala");
    }

    @Test
    public void testguardarKitMochilaCheiaToCruzSemVidaCheia() {
        String excepted = "Curado";
        toCruz.setVida(70);
        toCruz.guardarKit(kit);
        toCruz.guardarKit(kit);
        toCruz.guardarKit(kit);
        assertEquals(excepted, toCruz.guardarKit(kit), "Como a mochila está cheia e o To Cruz tem a vida cheia o item não é guardado e continua na sala");
    }

    @Test
    public void testguardarKitNull() {
        Exception excecao = assertThrows(NullPointerException.class, () -> toCruz.guardarKit(null));
    }

    @Test
    public void testguardarKitTypeDifKit_NesteCasoColete() {
        ItemCura colete = new ItemCura(divisao, TypeItemCura.COLETE, 20);
        Exception excecao = assertThrows(WrongTypeItem.class, () -> toCruz.guardarKit(colete));
    }

    @Test
    public void testUsarKitSemKitsNaMochila() {
        String excepted = "Sem kit medicos na mochila";
        assertEquals(excepted, toCruz.usarKit(), "Como o To Cruz não tem kits na mochila ele não pode se curar");
    }

    @Test
    public void testUsarKitComKitNaMochilaEToComMenosDe100Pontos() {
        String excepted = "Curar";
        //O kit esta a vir a null
        toCruz.guardarKit(kit);
        toCruz.setVida(70);
        assertEquals(excepted, toCruz.usarKit(), "Como o To Cruz tem pelo menos um kit na mochila e ele não tem a vida cheia ele vai poder usar o kit e curar-se");
    }

    @Test
    public void testUsarKitComAvidaCheia() {
        String excepted = "Não é possível usar o kit médico, porque a vida está cheia";
        toCruz.guardarKit(kit);
        assertEquals(excepted, toCruz.usarKit(), "Como a mochila tem pelo menos um kit de vida mas o To Cruz tem a vida cheia ele não poderá utilizar esse kit de vida");
    }

    @Test
    public void testUsarColete() {
        String excepted = "Colete Usado";
        ItemCura item = new ItemCura(divisao, TypeItemCura.COLETE, 20);
        assertEquals(excepted, toCruz.usarColete(item), "Como o To Cruz apanham um colete ele vai utiliza-lo");
    }

    @Test
    public void testUsarColeteNullPoninterException() {
        Exception excecao = assertThrows(NullPointerException.class, () -> toCruz.usarColete(null));
    }

    @Test
    public void testUsarColeteWrongTypeItem() {
        Exception excecao = assertThrows(WrongTypeItem.class, () -> toCruz.usarColete(this.kit));
    }
}
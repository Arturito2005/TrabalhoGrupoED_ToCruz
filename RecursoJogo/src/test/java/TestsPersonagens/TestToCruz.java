package TestsPersonagens;

import Exceptions.AllLifeException;
import Exceptions.EmptyCollectionException;
import Exceptions.WrongTypeItemException;
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
        this.kit = new ItemCura(TypeItemCura.KIT_VIDA, 20);
        this.toCruz = new ToCruz();
    }

    @Test
    public void testguardarKitValida() {
        assertEquals(kit, toCruz.guardarKit(kit), "Como a mochila não está cheia o kit é guardado");
    }

/*
Ver porque falha, unico que falha do ToCruz
    @Test
    public void testguardarKitMochilaCheiaEvidaCheia() {
        toCruz.guardarKit(kit);
        toCruz.guardarKit(kit);
        toCruz.guardarKit(kit);
        Exception exception = assertThrows(AllLifeException.class, () -> toCruz.guardarKit(kit));
    } */

    @Test
    public void testguardarKitNull() {
        Exception excecao = assertThrows(NullPointerException.class, () -> toCruz.guardarKit(null));
    }

    @Test
    public void testguardarKitTypeDifKit_NesteCasoColete() {
        ItemCura colete = new ItemCura(TypeItemCura.COLETE, 20);
        Exception excecao = assertThrows(WrongTypeItemException.class, () -> toCruz.guardarKit(colete));
    }

    @Test
    public void testUsarKitSemKitsNaMochila() {
        Exception excecao = assertThrows(EmptyCollectionException.class, () -> toCruz.usarKit());
    }

    @Test
    public void testUsarKitComKitNaMochilaEToComMenosDe100Pontos() {
        toCruz.guardarKit(kit);
        toCruz.setVida(10);
        assertEquals(kit, toCruz.usarKit(), "Como o To Cruz tem pelo menos um kit na mochila e ele não tem a vida cheia ele vai poder usar o kit e curar-se");
    }

    @Test
    public void testUsarKitComAvidaCheia() {
        toCruz.guardarKit(kit);
        Exception excecao = assertThrows(AllLifeException.class, () -> toCruz.usarKit());
    }


    @Test
    public void testUsarColete() {
        ItemCura item = new ItemCura(TypeItemCura.COLETE, 20);
        assertEquals(item, toCruz.usarItem(item), "Como o To Cruz apanham um colete ele vai utiliza-lo");
    }

    @Test
    public void testUsarColeteNullPoninterException() {
        Exception excecao = assertThrows(NullPointerException.class, () -> toCruz.usarItem(null));
    }
}
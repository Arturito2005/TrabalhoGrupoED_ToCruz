package TestDraw;

import Mapa.Draw;
import Mapa.DrawMap;
import Mapa.Edificio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDrawAbstract {

    protected Edificio edificio;

    @Test
    public void testGetEdificio() {
        edificio = new Edificio();
        Draw draw = new DrawMap(edificio);

        assertEquals(edificio, draw.getEdificio(), "O método getEdificio() não retornou o objeto esperado.");
    }
}

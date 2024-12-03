package TestImportar;

import Importar.ImportarMapa;
import Missoes.Missao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestImportarMapa {
    private static final String path = "C:/Users/artur/OneDrive/Desktop/universidade/2_ano/ED/TrabalhoGrupoED_ToCruz/JSON/FicheiroMissao.json";

    private static final String pathNoDATA = "C:/Users/artur/OneDrive/Desktop/universidade/2_ano/ED/TrabalhoGrupoED_ToCruz/JSON/Ficheiro.json";

    private ImportarMapa importM;

    private Missao missao;

    @BeforeEach
    public void setUp() {
        this.importM = new ImportarMapa();
        this.missao = new Missao();
    }

    @Test
    public void testGerarMapaComFicheiroValido() throws IOException{
        Missao missao1 = importM.gerarMapa(path);
        assertEquals(missao1, importM.gerarMapa(path), "Como o ficheiro existe e tem dados os dados são importados para a missao");
    }

    @Test
    public void testGerarMapaComFicheiroInvalivdoNull() {
        Exception excecao = assertThrows(NullPointerException.class, () -> importM.gerarMapa(null));
    }

}

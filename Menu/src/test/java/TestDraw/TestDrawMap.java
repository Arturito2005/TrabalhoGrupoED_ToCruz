package TestDraw;

import ArrayList.ArrayUnorderedList;
import Importar.ImportarMapa;
import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import Items.TypeItemCura;
import LinkedList.LinearLinkedOrderedList;
import Mapa.Alvo;
import Mapa.Divisao;
import Mapa.DrawMap;
import Mapa.Edificio;
import Missoes.Missao;
import Missoes.Versao;
import Personagens.Inimigo;
import Personagens.ToCruz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDrawMap extends TestDrawAbstract {

    private static final String path_name_def = "./Jsons/Import/FicheiroMissao.json";

    private DrawMap drawMap;

    @BeforeEach
    void setup() {
        ImportarMapa importarMapa = new ImportarMapa();
        Missao missao;

        try {
            missao = importarMapa.gerarMapa(path_name_def);
            Versao versao = missao.getVersao();
            edificio = versao.getEdificio();
            drawMap = new DrawMap(edificio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDrawMapWithEmptyDivisao() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        edificio = new Edificio();

        Divisao div = new Divisao("Sala1");
        edificio.addDivisao(div);
        Iterator<Divisao> itr_div = edificio.IteratorMapa();

        edificio.addLigacao(itr_div.next(), div, 10);

        drawMap = new DrawMap(edificio);
        drawMap.draw();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Lista de Divisoes vizinhas a divisao: Sala1"), "A saída deve conter o cabeçalho com o nome 'Sala1'.");
        assertTrue(output.contains("-"), "A saída deve conter as linhas de borda (formadas por '-' caracteres).");
    }

    @Test
    public void testDrawMapWithFullDivisao() {
        Divisao div = new Divisao("Sala2");
        div.addToCruz(new ToCruz());

        UnorderedListADT<Item> itens = div.getItens();
        ItemCura item = new ItemCura(TypeItemCura.KIT_VIDA, 20);  // ou o valor que corresponda à sua definição
        itens.addToRear(item);

        div.setAlvo(new Alvo("Alvo1"));
        div.setEntrada_saida(true);

        UnorderedListADT<Inimigo> inimigos = div.getInimigos();
        inimigos.addToRear(new Inimigo("Inimigo1", 15));

        edificio.addDivisao(div);
        Iterator<Divisao> itr_div = edificio.IteratorMapa();
        edificio.addLigacao(itr_div.next(), div, 10);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        drawMap.draw();

        System.setOut(originalOut);
        String output = outContent.toString();
        String expectedItemText = item.getType() + " " + item.getVida_recuperada() + " HP ";

        assertTrue(output.contains("Lista de Divisoes vizinhas a divisao: Sala2"), "A saída deve conter o cabeçalho com o nome 'Sala2'.");
        assertTrue(output.contains("ToCruz"), "A saída deve conter o nome do ToCruz ('ToCruz1').");
        assertTrue(output.contains(expectedItemText), "A saída deve conter os detalhes do ItemCura ('" + expectedItemText + "').");
        assertTrue(output.contains("Alvo1"), "A saída deve conter o nome do Alvo ('Alvo1').");
        assertTrue(output.contains("Saida"), "A saída deve indicar 'Saida' para a divisão de entrada/saída.");
        assertTrue(output.contains("Inimigo1"), "A saída deve conter o nome do Inimigo ('Inimigo1').");
        assertTrue(output.contains("Custo: 15"), "A saída deve exibir o custo (Custo) baseado no poder do inimigo (15).");
    }
}

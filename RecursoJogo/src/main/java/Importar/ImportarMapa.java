package Importar;

import Interfaces.ProcessData.Importar;
import Items.ItemCura;
import Items.TypeItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Alvo;
import Mapa.Divisao;
import Mapa.Edificio;
import Missoes.Missao;
import Personagens.Inimigo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe responsável por gerar o Mapa da missão do ToCruz
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class ImportarMapa implements Importar {

    /**
     * Variavel que guarda todas as divisoes do edificio
     */
    private static LinearLinkedUnorderedList<Divisao> lista_divisoes;

    /**
     * Construtor do ImportarMapa
     */
    public void ImportarMapa() {
        lista_divisoes = new LinearLinkedUnorderedList<>();
    }

    /**
     * Metodo auxiliar para encontrar uma divisão pelo nome numa lista de divisões.
     * Cria uma cópia da lista e percorre-a para evitar modificações na lista original.
     *
     * @param name Nome da divisão a ser encontrada.
     * @return A divisão encontrada ou null caso não seja encontrada.
     */
    private Divisao findDivisao(String name) {
        LinearLinkedUnorderedList<Divisao> current = new LinearLinkedUnorderedList<>();

        for (Divisao divisao : lista_divisoes) {
            current.addToRear(divisao);
        }

        boolean find = false;
        Divisao target = null;

        while (!current.isEmpty() && !find) {
            Divisao elemento = current.removeFirst();

            if (elemento.getName().equals(name)) {
                target = elemento;
                find = true;
            }
        }

        return target;
    }

    /**
     * Gera o mapa do edifício e guarda os dados da Missão a partir de um ficheiro JSON.
     *
     * @param path Caminho para o ficheiro JSON.
     * @return O objeto Missão criado com os dados do ficheiro.
     * @throws FileNotFoundException Se o ficheiro não for encontrado.
     * @throws IOException           Se ocorrer algum erro de leitura do ficheiro.
     * @throws NullPointerException  Se o caminho for null.
     */
    @Override
    public Missao gerarMapa(String path) throws NullPointerException, FileNotFoundException, IOException {
        if (path == null) {
            throw new NullPointerException("O caminho do ficheiro nao pode ser null");
        }

        JSONParser jsonP = new JSONParser();
        Missao missao = null;

        try {
            FileReader reader = new FileReader(path);
            Object obj = jsonP.parse(reader);

            if (obj instanceof JSONObject) {
                lista_divisoes = new LinearLinkedUnorderedList<>();
                JSONObject readingObj = (JSONObject) obj;

                String codigo_missao = (String) readingObj.get("cod-missao");
                long versao = (long) readingObj.get("versao");
                Edificio edificio = new Edificio();
                JSONArray edificio_array = (JSONArray) readingObj.get("edificio");

                for (Object edificioNome : edificio_array) {
                    Divisao divisao = new Divisao((String) edificioNome);
                    edificio.addDivisao(divisao);
                    lista_divisoes.addToRear(divisao);
                }

                JSONArray ligacoes_array = (JSONArray) readingObj.get("ligacoes");

                for (Object ligacao : ligacoes_array) {
                    JSONArray ligacao_array = (JSONArray) ligacao;

                    String divisao1 = (String) ligacao_array.get(0);
                    String divisao2 = (String) ligacao_array.get(1);

                    Divisao divisaoc1 = findDivisao(divisao1);
                    Divisao divisaoc2 = findDivisao(divisao2);

                    if (divisaoc1 != null && divisaoc2 != null) {
                        edificio.addLigacao(divisaoc1, divisaoc2, 0);
                    } else {
                        System.out.println("Pelo menos uma das divisoes nao existe");
                    }
                }

                JSONArray inimigos_array = (JSONArray) readingObj.get("inimigos");
                LinearLinkedUnorderedList<Inimigo> inimigos_list = new LinearLinkedUnorderedList<>();
                LinearLinkedUnorderedList<String> divisao_list = new LinearLinkedUnorderedList<>();
                int num_inimigos = 0;
                for (Object cont_obj : inimigos_array) {
                    JSONObject readingObjConte = (JSONObject) cont_obj;

                    String nome = (String) readingObjConte.get("nome");
                    long poder = (long) readingObjConte.get("poder");
                    String divisao = (String) readingObjConte.get("divisao");

                    Divisao div = findDivisao(divisao);


                    long poder_final = poder;
                    if (!div.getInimigos().isEmpty()) {
                        for (Inimigo inimigo : div.getInimigos()) {
                            poder_final += inimigo.getPoder();
                        }
                    }
                    div.addInimigo(new Inimigo(nome, poder));
                    edificio.updateWeight(div, poder_final);
                }

                for (int i = 0; i < num_inimigos; i++) {
                    Divisao div = findDivisao(divisao_list.removeFirst());
                    Inimigo inimigo = inimigos_list.removeFirst();
                    div.addInimigo(inimigo);
                }

                JSONArray entradas_array = (JSONArray) readingObj.get("entradas-saidas");
                for (Object entrada : entradas_array) {
                    Divisao divisao = findDivisao((String) entrada);
                    if (divisao != null) {
                        divisao.setEntrada_saida(true);
                    }
                }

                JSONObject alvo_obj = (JSONObject) readingObj.get("alvo");
                String divisao_alvo = (String) alvo_obj.get("divisao");
                String tipo_alvo = (String) alvo_obj.get("tipo");

                Divisao divisao_al = findDivisao(divisao_alvo);
                Alvo alvo = new Alvo(tipo_alvo);
                divisao_al.setAlvo(alvo);

                JSONArray itens_array = (JSONArray) readingObj.get("itens");
                for (Object cont_obj : itens_array) {
                    JSONObject readingObjConte = (JSONObject) cont_obj;

                    String divisao_item = (String) readingObjConte.get("divisao");
                    long pontos_vida = 0;

                    if (readingObjConte.containsKey("pontos-recuperados")) {
                        pontos_vida = (long) readingObjConte.get("pontos-recuperados");
                    } else if (readingObjConte.containsKey("pontos-extra")) {
                        pontos_vida = (long) readingObjConte.get("pontos-extra");
                    }

                    String tipo = (String) readingObjConte.get("tipo");

                    TypeItemCura type = TypeItemCura.KIT_VIDA;

                    if (tipo.equals("colete")) {
                        type = TypeItemCura.COLETE;
                    }

                    Divisao divisao = findDivisao(divisao_item);
                    ItemCura item = new ItemCura(type, pontos_vida);
                    divisao.setItem(item);
                }

                missao = new Missao(codigo_missao, versao, edificio);
            }
        } catch (ParseException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return missao;
    }
}

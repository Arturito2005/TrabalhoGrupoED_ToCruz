package Exportar;

import Interfaces.ExportarInt;
import Interfaces.QueueADT;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Missoes.Missao;
import Missoes.Simulacoes;
import Personagens.Inimigo;
import Queue.LinkedQueue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Classe que implementa a interface ExportarInt para exportar dados
 * das versões de missões para um arquivo em formato JSON.
 * Esta classe permite gerar um arquivo com informações detalhadas
 * sobre as missões, simulações e inimigos mortos.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 *
 * @version 1.0
 */
public class ExportarDado implements ExportarInt {

    /**
     * Construtor da classe ExportarDado.
     * Inicializa a classe sem a necessidade de parâmetros.
     */
    public ExportarDado() {
    }

    /**
     * Método que exporta as versões das missões e seus dados relacionados para um arquivo JSON.
     * O arquivo JSON gerado contém as versões das missões, simulações e os inimigos abatidos,
     * sendo salvo no caminho especificado.
     *
     * @param path O caminho para onde o arquivo JSON será salvo.
     * @param listaVersoes A lista de missões com suas respectivas versões e simulações.
     */
    @Override
    public void exportarVersoes(String path, UnorderedListADT<Missao> listaVersoes) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cod_missao", listaVersoes.first().getcod_missao());

        JSONArray versaoArray = new JSONArray();

        for (Missao missao : listaVersoes) {
            JSONObject versaoObject = new JSONObject();
            versaoObject.put("versao", missao.getVersao());

            JSONArray simulacoesArray = new JSONArray();
            Iterator<Simulacoes> iterator = missao.getSimulacoes().iterator();
            while (iterator.hasNext()) {
                Simulacoes simulacao = iterator.next();
                JSONObject simulacaoObject = new JSONObject();
                simulacaoObject.put("versao_simulacao", simulacao.getVersao_simulacao());
                simulacaoObject.put("vida_to_cruz", simulacao.getVida_to());

                JSONArray trajetoArray = new JSONArray();
                JSONArray inimigosArray = new JSONArray();

                QueueADT<Divisao> trajetoQueue = simulacao.getTrajeto_to();
                QueueADT<Divisao> tempTrajetoQueue = new LinkedQueue<>();
                UnorderedListADT<Inimigo> inimigosList = simulacao.getInimigos_dead();
                UnorderedListADT<Inimigo> tempInimigosList = new LinearLinkedUnorderedList<>();

                while (!trajetoQueue.isEmpty()) {
                    Divisao divisao = trajetoQueue.dequeue();
                    tempTrajetoQueue.enqueue(divisao);

                    trajetoArray.add(divisao.getName());
                }

                while (!tempTrajetoQueue.isEmpty()) {
                    trajetoQueue.enqueue(tempTrajetoQueue.dequeue());
                }
                simulacaoObject.put("trajetoPercorrido", trajetoArray);

                while (!inimigosList.isEmpty()) {
                    Inimigo inimigo = inimigosList.removeFirst();
                    tempInimigosList.addToFront(inimigo);

                    JSONObject inimigoObject = new JSONObject();
                    inimigoObject.put("nome", inimigo.getNome());
                    inimigoObject.put("poder", inimigo.getPoder());

                    inimigosArray.add(inimigoObject);

                }

                while (!tempInimigosList.isEmpty()) {
                    inimigosList.addToFront(tempInimigosList.removeFirst());
                }

                simulacaoObject.put("inimigosAbatidos", inimigosArray);

                simulacoesArray.add(simulacaoObject);
                versaoObject.put("simulacoes", simulacoesArray);
                versaoArray.add(versaoObject);
            }
        }

        jsonObject.put("versoes", versaoArray);
        jsonObject.put("tot_simulacoes", listaVersoes.first().getTot_simulacoes());

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage() + " ao exportar trajetos");
        }
    }
}
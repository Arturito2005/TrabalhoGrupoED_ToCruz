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
 * Classe responsável por exportar os dados de trajetos de simulações para um ficheiro JSON.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public class ExportarDado implements ExportarInt {
    private Missao missao;

    public ExportarDado(Missao missao) {
        this.missao = missao;
    }

    @Override
    public void exportarTrajetos(String path) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cod_missao", this.missao.getcod_missao());
        jsonObject.put("versao", this.missao.getVersao());

        JSONArray simulacoesArray = new JSONArray();
        Iterator<Simulacoes> iterator = this.missao.getSimulacoes().iterator();
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
                // para a divisão é outra história, não temos acesso "direto"

                inimigosArray.add(inimigoObject);

            }

            while (!tempInimigosList.isEmpty()) {
                inimigosList.addToFront(tempInimigosList.removeFirst());
            }
            simulacaoObject.put("inimigosAbatidos", inimigosArray);

            simulacoesArray.add(simulacaoObject);
        }

        jsonObject.put("tot_simulacoes", this.missao.getTot_simulacoes());
        jsonObject.put("simulacoes", simulacoesArray);

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage() + " ao exportar trajetos");
        }
    }

}

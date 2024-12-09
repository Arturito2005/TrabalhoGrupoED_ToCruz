package Exportar;

import Interfaces.ExportarInt;
import Interfaces.QueueADT;
import Mapa.Divisao;
import Queue.LinkedQueue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsável por exportar os dados de trajetos de simulações para um ficheiro JSON.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class ExportarDado implements ExportarInt {
    /**
     * Codigo da missão a exportar
     * */
    private String cod_missao;

    /**
     * Trajeto realizado pelo ToCruz
     * */
    private QueueADT<QueueADT<Divisao>> trajetosSimulacoes;

    /**
     * Construtor da classe {@link ExportarDado}.
     *
     * @param cod_missao Código da missão a ser exportada.
     * @param trajetosSimulacoes Queue de trajetos simulados, onde cada trajeto é uma fila de divisões.
     */
    public ExportarDado(String cod_missao, QueueADT<QueueADT<Divisao>> trajetosSimulacoes) {
        this.cod_missao = cod_missao;
        this.trajetosSimulacoes = trajetosSimulacoes;
    }

    /**
     * Método responsável por exportar os dados para um ficheiro JSON.
     *
     * Este método percorre a fila de trajetos simulados, gera o ficheiro JSON
     * contendo as divisões de cada trajeto e suas versões, e escreve o ficheiro no caminho fornecido.
     *
     * @param path Caminho do ficheiro onde os dados serão exportados.
     */
    @Override
    public void exportarDados(String path) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cod-missao", this.cod_missao);

        JSONArray simulacoesArray = new JSONArray();
        int versao = 1;

        while (!trajetosSimulacoes.isEmpty()) {
            QueueADT<Divisao> trajeto = trajetosSimulacoes.dequeue();
            QueueADT<Divisao> tempQueue = new LinkedQueue<>();

            while (!trajeto.isEmpty()) {
                Divisao divisao = trajeto.dequeue();
                tempQueue.enqueue(divisao);

                JSONObject simulacaoObject = new JSONObject();
                simulacaoObject.put("versao", versao++);
                simulacaoObject.put("divisao", divisao.getName());
                simulacoesArray.add(simulacaoObject);
            }

            while (!tempQueue.isEmpty()) {
                trajeto.enqueue(tempQueue.dequeue());
            }
        }

        jsonObject.put("simulacoes", simulacoesArray);
        jsonObject.put("tot_versoes", versao - 1);

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage() + " ao exportar trajetos");
        }
    }
}

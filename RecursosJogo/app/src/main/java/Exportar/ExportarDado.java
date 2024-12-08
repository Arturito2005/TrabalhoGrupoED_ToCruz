package Exportar;

import Interfaces.Exportar;
import Interfaces.QueueADT;
import Mapa.Divisao;
import Queue.LinkedQueue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/*
*
* @author Xico
* */
public class ExportarDado implements Exportar {
    private long versao;

    private QueueADT<QueueADT<Divisao>> trajetosSimulacoes;

    public ExportarDado(long versao, QueueADT<QueueADT<Divisao>> trajetosSimulacoes) {
        this.versao = versao;
        this.trajetosSimulacoes = trajetosSimulacoes;
    }

    @Override
    public void exportarDados(String path) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("versao", this.versao);

        JSONArray simulacoesArray = new JSONArray();
        // arranjar uma forma de ter o código para a simulação
        // para tentar tirar isto daqui que não me parece muito boa ideia
        int codigoSimulacao = 0;

        while (!trajetosSimulacoes.isEmpty()) {
            QueueADT<Divisao> trajeto = trajetosSimulacoes.dequeue();

            String trajetoPercorrido = "";

            QueueADT<Divisao> tempQueue = new LinkedQueue<>();

            while (!trajeto.isEmpty()) {
                Divisao divisao = trajeto.dequeue();
                trajetoPercorrido += divisao.getName() + ", ";
                tempQueue.enqueue(divisao);
            }

            while (!tempQueue.isEmpty()) {
                trajeto.enqueue(tempQueue.dequeue());
            }

            JSONObject simulacaoObject = new JSONObject();
            simulacaoObject.put("codigoSimulacao", codigoSimulacao++); // incremento o código aqui
            simulacaoObject.put("trajetoPercorrido", trajetoPercorrido);

            simulacoesArray.add(simulacaoObject);
        }

        jsonObject.put("simulacoes", simulacoesArray);

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage() + "  ao exportar trajetos");
        }
    }
}

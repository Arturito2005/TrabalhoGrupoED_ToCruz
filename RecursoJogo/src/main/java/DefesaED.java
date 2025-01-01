import Importar.ImportarMapa;
import Interfaces.QueueADT;
import Mapa.Divisao;
import Missoes.Missao;
import Queue.LinkedQueue;

import java.io.IOException;
import java.util.Iterator;

public class DefesaED {
    public static void main(String[] args) {
        ImportarMapa importarMapa = new ImportarMapa();

        QueueADT<Divisao> filaDivisao = new LinkedQueue<>();

        try {
            Missao missao = importarMapa.gerarMapa("./Json/FicheiroMissao.json");
            Iterator<Divisao> itr = missao.getEdificio().getPlantaEdificio().iterator();
            Divisao divisao;
            boolean find = false;

            while (itr.hasNext() && !find) {
                Divisao divisao1 = itr.next();

                if(divisao1.isEntrada_saida()) {
                    divisao = divisao1;
                    filaDivisao.enqueue(divisao);
                    Iterator<Divisao> itrAcess = missao.getEdificio().getNextDivisoes(divisao);

                    while(itrAcess.hasNext()) {
                        filaDivisao.enqueue(itrAcess.next());
                    }

                    find = true;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!filaDivisao.isEmpty()) {
            System.out.println(filaDivisao.toString());
        }
    }
}

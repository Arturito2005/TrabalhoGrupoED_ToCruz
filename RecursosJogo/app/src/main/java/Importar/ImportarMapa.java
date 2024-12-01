package Importar;

import Interfaces.Importar;
import Mapa.Divisao;
import Mapa.Edificio;
import Missoes.Missao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
* @author Artur
* */
public class ImportarMapa implements Importar {

    @Override
    public void importarDados(String path, Missao missao) throws FileNotFoundException, IOException{
        JSONParser jsonP = new JSONParser();

        try {
            FileReader reader = new FileReader(path);
            Object obj = jsonP.parse(reader);

            if(obj instanceof JSONObject) {
                JSONObject readingObj = (JSONObject) obj;

                String codigo_missao = (String) readingObj.get("cod-missao");
                //O tipo da versao é long ver se vai ser necessario, mudar o tipo de variavel na classe
                long versao = (long) readingObj.get("versao");

                Edificio edificio = new Edificio();
                JSONArray edificio_array = (JSONArray) readingObj.get("edificio");

                for (Object edificioNome : edificio_array) {
                    //Dá erro num
                    Divisao divisao = new Divisao((String) edificioNome);
                    edificio.addVertice(divisao);
                }

                for (Object cont_obj : edificio_array) {
                    JSONObject readingObjConte = (JSONObject) cont_obj;
                    String codigo_cont = (String) readingObjConte.get("codigo");

                    long capacity = (long) readingObjConte.get("capacidade");

                    /*
                    ContainerImp temp_container = new ContainerImp(codigo_cont, this.container_type(codigo_cont) ,capacity);
                    container[cont_containers++] = temp_container;
                    * */
                }

               /*
                Instant instant = Instant.parse(data_string);
                LocalDateTime data = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();

                long valor = (long) readingObj.get("valor");

                MeasurementImp meas_temp = new MeasurementImp(data, valor);
                ContainerImp container_temp = new ContainerImp(contentor_code, this.container_type(contentor_code), valor);

                int pos_container = this.findContainer(instn, contentor_code);

                if (pos_container != -1) {
                    try {
                        instn.addMeasurement(meas_temp, container_temp);
                    } catch (ContainerException | MeasurementException ex) {
                        System.out.println("Exceção: " + ex.getMessage());
                    }
                }
                else {
                    System.out.println("O container lido não existe em nenhuma aidbox da instituição");
                }
               * */
            }
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("O ficheiro não foi encontrado");
        } catch (IOException ex) {
            throw new IOException("Não foi possível ler o ficheiro");
        } catch (ParseException ex) {
            System.out.println("Exceção: " + ex.getMessage());
        }
    }
}

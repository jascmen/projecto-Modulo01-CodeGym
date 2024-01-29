import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decrypt {

    char [] alfabeto = new Alfabeto().getAlfabeto();

    boolean desencriptarArchivos(List<String> lineas, int tipoEncriptacion){
        List<String> lineasDesEncriptadas = new ArrayList<>();

        for(String linea : lineas){
            char[] caracteres = linea.toCharArray();
            char[] textoDesEncriptado = new char[caracteres.length];

            for(int j = 0; j < caracteres.length; j++){
                char c = caracteres[j];
                boolean found = false;
                for(int i = 0; i < alfabeto.length; i++){
                    if(c == alfabeto[i]){
                        /*calculamos la nueva posición del caracter desencriptado, se  suma el tamaño y se usa mod para
                             que no pase del tamaño del arreglo
                            */
                        int nuevaPosicion = ((i - tipoEncriptacion)+ alfabeto.length) % alfabeto.length;
                        textoDesEncriptado[j] = alfabeto[nuevaPosicion];
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    textoDesEncriptado[j] = c;
                }
            }
            lineasDesEncriptadas.add(new String(textoDesEncriptado));
        }

        //creamos la carpeta donde se guardarán los archivos desencriptados
        File carpeta = new File("archivosDesencriptados");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para el texto encriptado
        File file = new File(carpeta, "textoDesEncriptado_"+count+".txt");
        try (FileWriter writer = new FileWriter(file)) {
            for (String linea : lineasDesEncriptadas) {
                writer.write(linea);
                writer.write(System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encript {
    char [] alfabeto = new Alfabeto().getAlfabeto();

    boolean encriptarArchivo(List<String> lineas, int tipoEncriptacion){
        List<String> lineasEncriptadas = new ArrayList<>();

        for(String linea : lineas){
            char[] caracteres = linea.toCharArray();
            char[] textoEncriptado = new char[caracteres.length];

            for(int j = 0; j < caracteres.length; j++){
                char c = caracteres[j];
                boolean found = false;
                for(int i = 0; i < alfabeto.length; i++){
                    if(c == alfabeto[i]){
                        //calculamos la nueva posición del caracter encriptado, se usa mod para que no se salga del arreglo
                        int nuevaPosicion = (i + tipoEncriptacion) % alfabeto.length;
                        textoEncriptado[j] = alfabeto[nuevaPosicion];
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    textoEncriptado[j] = c;
                }
            }
            lineasEncriptadas.add(new String(textoEncriptado));
        }

        //creamos la carpeta donde se guardarán los archivos encriptados
        File carpeta = new File("archivosEncriptados");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para el texto encriptado
        File file = new File(carpeta, "textoEncriptado_"+count+".txt");
        try (FileWriter writer = new FileWriter(file)) {
            for (String linea : lineasEncriptadas) {
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

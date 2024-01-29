package GestionArchivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GestionarArchivos {

    private String nameFolder = "archivosEncriptados";

    public boolean guardarArchivoEncriptado(List<String> lineas){
        //creamos la carpeta donde se guardarán los archivos encriptados
        File carpeta = new File(this.nameFolder);
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para el texto encriptado
        File file = new File(carpeta, "textoEncriptado_"+count+".txt");
        try (FileWriter writer = new FileWriter(file)) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.write(System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardarArchivoDesencripado(List<String> lineas){
        //creamos la carpeta donde se guardarán los archivos desencriptados
        File carpeta = new File("archivosDesencriptados");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para el texto encriptado
        File file = new File(carpeta, "textoDesEncriptado_"+count+".txt");
        try (FileWriter writer = new FileWriter(file)) {
            for (String linea : lineas) {
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

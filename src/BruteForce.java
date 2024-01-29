import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BruteForce {

    char [] alfabeto = new Alfabeto().getAlfabeto();

    Set<String> diccionario = new Diccionario().getDiccionario();

    private int [] tiposEncriptacion =  new EncriptacionTypes().getTiposEncriptacion();

    boolean desencriptarPorFuerzaBruta(List<String> lineas){
        boolean flag = false;
        int tipoEncriptacion;

        loopGeneral:
        for(int i = 0; i < tiposEncriptacion.length; i++) {
            List<String> lineasDesEncriptadas = new ArrayList<>();
            tipoEncriptacion = tiposEncriptacion[i];

            for (String linea : lineas) {
                char[] caracteres = linea.toCharArray();
                char[] textoDesEncriptado = new char[caracteres.length];

                for (int j = 0; j < caracteres.length; j++) {
                    char c = caracteres[j];
                    boolean found = false;
                    for (int k = 0; k < alfabeto.length; k++) {
                        if (c == alfabeto[k]) {
                            /*calculamos la nueva posición del caracter desencriptado, se  suma el tamaño y se usa mod para
                             que no pase del tamaño del arreglo
                            */
                            int nuevaPosicion = ((k - tipoEncriptacion) + alfabeto.length) % alfabeto.length;
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

            if (textoTieneSentido(lineasDesEncriptadas)) {
                //creamos la carpeta donde se guardarán los archivos desencriptados
                File carpeta = new File("archivosDesencriptados");
                if(!carpeta.exists()){
                    carpeta.mkdir();
                }

                //obtenemos el número de archivos que hay en la carpeta
                int count = carpeta.listFiles().length +1;
                // Crear un nuevo archivo .txt para el texto encriptado
                File file = new File(carpeta, "textoDesEncriptado_"+count+".txt");
                try (FileWriter writer = new FileWriter(file)) {
                    for (String linea : lineasDesEncriptadas) {
                        writer.write(linea);
                        writer.write(System.lineSeparator());
                    }
                    flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    flag =  false;
                }
                break loopGeneral;

            }else {
                flag =  false;
            }

        }

        return flag;
    }

    boolean textoTieneSentido(List<String> lineas){
        boolean flag = false;
        lineasLoop: //etiqueta para salir del ciclo
        for(String linea : lineas){
            //separamos la línea en palabras
            String[] palabras = linea.split(" ");
            for(String palabra : palabras){
                if(diccionario.contains(palabra)){
                    flag = true;
                    break lineasLoop;
                }
            }
        }

        return flag;
    }
}

package Desencriptacion;

import GestionArchivos.GestionarArchivos;
import Tools.Alfabeto;

import java.util.ArrayList;
import java.util.List;

public class Decrypt {

    char [] alfabeto = new Alfabeto().getAlfabeto();
    GestionarArchivos gestionarArchivos = new GestionarArchivos();

    public boolean desencriptarArchivos(List<String> lineas, int tipoEncriptacion){
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

        return gestionarArchivos.guardarArchivoDesencripado(lineasDesEncriptadas);

    }
}

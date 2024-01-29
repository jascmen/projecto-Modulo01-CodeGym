package Encriptacion;

import java.util.ArrayList;
import java.util.List;
import  GestionArchivos.GestionarArchivos;
import Tools.Alfabeto;

public class Encript {
    char [] alfabeto = new Alfabeto().getAlfabeto();
    GestionarArchivos gestionarArchivos = new GestionarArchivos();



    public boolean encriptarArchivo(List<String> lineas, int tipoEncriptacion){
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


        return gestionarArchivos.guardarArchivoEncriptado(lineasEncriptadas);
    }
}

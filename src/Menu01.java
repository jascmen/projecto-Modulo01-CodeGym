import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu01 {
    private JComboBox selectorTipo;
    private JTextArea seleccioneLaOpcionTextArea;
    private JButton encriptarArchivosButton;
    private JButton desencriptarArchivosButton;
    private JButton desencriptacionFuerzaBrutaButton;
    private int [] tiposEncriptacion = {1,2,3,4,5};


    //alfabeto que se usará para encriptar y desencriptar
    private char[] alfabeto = {
            // tu alfabeto aquí, incluyendo el espacio en blanco y el salto de línea
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            ' ', '.', ',', ';', ':', '!', '?', '-', '_', '(', ')', '[', ']', '{', '}', '<', '>', '/', '\\', '|', '@', '#', '$', '%', '^', '&', '*', '+', '=', '~', '`', '\'', '\"', '\n'

    };

    private Set<String> diccionario = new HashSet<>(){{
        add("hola");
        add("como");
        add("estas");
    }};

    public Menu01() {
        JFrame frame = new JFrame("Encriptador");
        JPanel panel = new JPanel();
        seleccioneLaOpcionTextArea.setText("Seleccione la opción con la que desea encriptar o desencriptar su texto");
        panel.add(this.selectorTipo);
        panel.add(this.seleccioneLaOpcionTextArea);
        panel.add(this.encriptarArchivosButton);
        panel.add(this.desencriptarArchivosButton);
        panel.add(this.desencriptacionFuerzaBrutaButton);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(550,300);

        //inicializar, eliminar codigo duplicado, clases

        //Agrega los tipos de encriptación al selector
        for(int i : tiposEncriptacion){
            this.selectorTipo.addItem(i);
        }

        //Evento click para agregar un fileChooser que sirve para seleccionar el archivo a encriptar
        encriptarArchivosButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                //selectedFile es el archivo seleccionado
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) { // Verificar si el archivo existe
                    String nombreArchivo = selectedFile.getName(); // Obtener el nombre del archivo
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1); // Obtener la extensión del archivo
                    if ("txt".equals(extensionArchivo)) { // Verificar si la extensión del archivo es correcta
                        //Esta lista se usará para guardar las líneas del archivo
                        List<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String line;
                            while ((line = reader.readLine()) !=null) {
                                //Agrega cada línea del archivo a la lista
                                lines.add(line);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        //Imprime las líneas del archivo, es temporal
                        /*
                        for(String c : lines){
                            System.out.println(c);
                        }
                         */
                        //Obtiene el tipo de encriptación seleccionado
                        int tipoEncriptacion = (int) this.selectorTipo.getSelectedItem();
                        //Llama a la función encriptarArchivo
                        if(encriptarArchivo(lines, tipoEncriptacion)){
                            //mostramos mensaje de que el archivo fue encriptado
                            JOptionPane.showMessageDialog(null, "El archivo fue encriptado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            JOptionPane.showMessageDialog(null, "El archivo no pudo ser encriptado", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        //System.out.println("Archivo encriptado");
                    } else {
                        JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo .txt", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        desencriptarArchivosButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            //ubicación de la carpeta donde se guardan los archivos encriptados
            File currentDirectory = new File("./archivosEncriptados");
            fileChooser.setCurrentDirectory(currentDirectory);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                //selectedFile es el archivo seleccionado
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) { // Verificar si el archivo existe
                    String nombreArchivo = selectedFile.getName(); // Obtener el nombre del archivo
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1); // Obtener la extensión del archivo
                    if ("txt".equals(extensionArchivo)) { // Verificar si la extensión del archivo es correcta
                        //Esta lista se usará para guardar las líneas del archivo
                        List<String> lineas = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String linea;
                            //Agrega cada línea del archivo a la lista
                            while ((linea = reader.readLine()) != null) {
                                lineas.add(linea);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        //Imprime las líneas del archivo, es temporal
                        /*
                        for(String c : lineas){
                            System.out.println(c);
                        }
                         */
                        //Obtiene el tipo de encriptación seleccionado
                        int tipoEncriptacion = (int) this.selectorTipo.getSelectedItem();
                        //Llama a la función desencriptarArchivo

                        if(desencriptarArchivos(lineas, tipoEncriptacion)){
                            //mostramos mensaje de que el archivo fue desencriptado
                            JOptionPane.showMessageDialog(null, "El archivo fue desencriptado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            JOptionPane.showMessageDialog(null, "El archivo no pudo ser desencriptado", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        //System.out.println("Archivo desencriptado");
                    } else {
                        JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo .txt", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        desencriptacionFuerzaBrutaButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            File currentDirectory = new File("./archivosEncriptados");
            fileChooser.setCurrentDirectory(currentDirectory);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                //selectedFile es el archivo seleccionado
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) { // Verificar si el archivo existe
                    String nombreArchivo = selectedFile.getName(); // Obtener el nombre del archivo
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1); // Obtener la extensión del archivo
                    if ("txt".equals(extensionArchivo)) { // Verificar si la extensión del archivo es correcta
                        //Esta lista se usará para guardar las líneas del archivo
                        List<String> lineas = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String linea;
                            //Agrega cada línea del archivo a la lista
                            while ((linea = reader.readLine()) != null) {
                                lineas.add(linea);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        //Imprime las líneas del archivo, es temporal
                        for(String c : lineas){
                            System.out.println(c);
                        }
                        //Llama a la función desencriptarArchivo
                        if(desencriptarPorFuerzaBruta(lineas)){
                            //mostramos mensaje de que el archivo fue desencriptado
                            JOptionPane.showMessageDialog(null, "El archivo fue desencriptado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                            //System.out.println("Archivo desencriptado");
                        }else {
                            JOptionPane.showMessageDialog(null, "El archivo no pudo ser desencriptado", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo .txt", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }


        });



    }


    public boolean encriptarArchivo(List<String> lineas, int tipoEncriptacion){
        //Lista que se usará para guardar las líneas encriptadas
        List<String> lineasEncriptadas = new ArrayList<>();

        //obtenemos cada línea del archivo
        for(String linea : lineas){
            //convertimos la línea en un arreglo de caracteres
            char[] caracteres = linea.toCharArray();
            //creamos un arreglo de caracteres que se usará para guardar la línea encriptada
            char[] textoEncriptado = new char[caracteres.length];

            //recorremos cada caracter de la línea
            for(int j = 0; j < caracteres.length; j++){
                char c = caracteres[j];
                /*if (c == '\n') {
                    textoEncriptado[j] = c;
                    continue;
                }*/

                for(int i = 0; i < alfabeto.length; i++){
                    if(c == alfabeto[i]){
                        //calculamos la nueva posición del caracter encriptado, se usa mod para que no se salga del arreglo
                        int nuevaPosicion = (i + tipoEncriptacion) % alfabeto.length;
                        textoEncriptado[j] = alfabeto[nuevaPosicion];
                        break;
                    }
                }
            }
            //agregamos la línea encriptada a la lista, convertimos el arreglo de caracteres a String
            //eliminar comentarios explicitos
            lineasEncriptadas.add(new String(textoEncriptado));
        }

        //creamos la carpeta donde se guardarán los archivos encriptados
        File carpeta = new File("archivosEncriptados");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        //obtenemos el número de archivos que hay en la carpeta
        //posible eliminacion
        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para el texto encriptado
        File file = new File(carpeta, "textoEncriptado_"+count+".txt");
        //escribimos las líneas encriptadas en el archivo
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

    public boolean desencriptarArchivos(List<String> lineas, int tipoEncriptacion){
        //Lista que se usará para guardar las líneas desencriptadas
        List<String> lineasDesEncriptadas = new ArrayList<>();

        //obtenemos cada línea del archivo
        for(String linea : lineas){
            //convertimos la línea en un arreglo de caracteres
            char[] caracteres = linea.toCharArray();
            //creamos un arreglo de caracteres que se usará para guardar la línea desencriptada
            char[] textoDesEncriptado = new char[caracteres.length];

            //recorremos cada caracter de la línea
            for(int j = 0; j < caracteres.length; j++){
                char c = caracteres[j];
                /*if (c == '\n') {
                    textoDesEncriptado[j] = c;
                    continue;
                }*/
                //recorremos el alfabeto
                for(int i = 0; i < alfabeto.length; i++){
                    if(c == alfabeto[i]){
                        /*calculamos la nueva posición del caracter desencriptado, se  suma el tamaño
                         del alfabeto y tambien usa mod para que no se salga del arreglo
                         */
                        int nuevaPosicion = ((i - tipoEncriptacion)+ alfabeto.length) % alfabeto.length;
                        textoDesEncriptado[j] = alfabeto[nuevaPosicion];
                        break;
                    }
                }
            }
            //agregamos la línea desencriptada a la lista, convertimos el arreglo de caracteres a String
            lineasDesEncriptadas.add(new String(textoDesEncriptado));
        }

        //creamos la carpeta donde se guardarán los archivos desencriptados
        File carpeta = new File("archivosDesencriptados");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        //obtenemos el número de archivos que hay en la carpeta
        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para cada texto encriptado
        File file = new File(carpeta, "textoDesEncriptado_"+count+".txt");
        //escribimos las líneas desencriptadas en el archivo
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

    public boolean desencriptarPorFuerzaBruta(List<String> lineas){
        boolean flag = false;
        int tipoEncriptacion;

        loopGeneral:
        for(int i = 0; i < tiposEncriptacion.length; i++) {
            List<String> lineasDesEncriptadas = new ArrayList<>();
            tipoEncriptacion = tiposEncriptacion[i];

            //obtenemos cada línea del archivo
            for (String linea : lineas) {
                //convertimos la línea en un arreglo de caracteres
                char[] caracteres = linea.toCharArray();
                //creamos un arreglo de caracteres que se usará para guardar la línea desencriptada
                char[] textoDesEncriptado = new char[caracteres.length];

                //recorremos cada caracter de la línea
                for (int j = 0; j < caracteres.length; j++) {
                    char c = caracteres[j];
                    for (int k = 0; k < alfabeto.length; k++) {
                        if (c == alfabeto[k]) {
                            /*calculamos la nueva posición del caracter desencriptado, se  suma el tamaño y se usa mod para
                             que no pase del tamaño del arreglo
                            */
                            int nuevaPosicion = ((k - tipoEncriptacion) + alfabeto.length) % alfabeto.length;
                            textoDesEncriptado[j] = alfabeto[nuevaPosicion];
                            break;
                        }
                    }
                }
                lineasDesEncriptadas.add(new String(textoDesEncriptado));
            }

            // Comprueba si el texto desencriptado tiene sentido
            if (textoTieneSentido(lineasDesEncriptadas)) {
                //creamos la carpeta donde se guardarán los archivos desencriptados
                File carpeta = new File("archivosDesencriptados");
                if(!carpeta.exists()){
                    carpeta.mkdir();
                }

                //obtenemos el número de archivos que hay en la carpeta
                int count = carpeta.listFiles().length +1;
                // Crear un nuevo archivo .txt para cada texto encriptado
                File file = new File(carpeta, "textoDesEncriptado_"+count+".txt");
                //escribimos las líneas desencriptadas en el archivo
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

    public boolean textoTieneSentido(List<String> lineas){
        boolean flag = false;
        //recorremos cada línea del texto
        lineasLoop: //etiqueta para salir del ciclo
        for(String linea : lineas){
            //separamos cada palabra de la línea
            String[] palabras = linea.split(" ");
            //recorremos cada palabra
            for(String palabra : palabras){
                if(diccionario.contains(palabra)){
                    flag = true;
                    break lineasLoop;
                }
            }
        }

        return flag;
    }


    public static void main(String[] args) {
        new Menu01();
    }


}

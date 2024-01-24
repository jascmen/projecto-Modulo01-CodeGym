import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Menu01 {
    private JComboBox selectorTipo;
    private JTextField seleccioneLaOpcionTextField;
    private JButton encriptarArchivosButton;
    private JButton desencriptarArchivosButton;


    private char[] alfabeto = {
            // tu alfabeto aquí, incluyendo el espacio en blanco y el salto de línea
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            ' ', '.', ',', ';', ':', '!', '?', '-', '_', '(', ')', '[', ']', '{', '}', '<', '>', '/', '\\', '|', '@', '#', '$', '%', '^', '&', '*', '+', '=', '~', '`', '\'', '\"', '\n'

    };

    public Menu01() {
        JFrame frame = new JFrame("Encriptador");
        JPanel panel = new JPanel();
        panel.add(this.selectorTipo);
        panel.add(this.seleccioneLaOpcionTextField);
        panel.add(this.encriptarArchivosButton);
        panel.add(this.desencriptarArchivosButton);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,500);

        int [] tiposEncriptacion = {1,2,3,4,5};
        for(int i : tiposEncriptacion){
            this.selectorTipo.addItem(i);
        }

        //Evento click para agregar un fileChooser que sirve para seleccionar el archivo a encriptar
        encriptarArchivosButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) { // Verificar si el archivo existe
                    String nombreArchivo = selectedFile.getName();
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1); // Obtener la extensión del archivo
                    if ("txt".equals(extensionArchivo)) { // Verificar si la extensión del archivo es correcta
                        List<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String line;
                            while ((line = reader.readLine()) !=null) {
                                lines.add(line);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        for(String c : lines){
                            System.out.print(c);
                        }
                        int tipoEncriptacion = (int) this.selectorTipo.getSelectedItem();
                        encriptarArchivo(lines, tipoEncriptacion);
                        System.out.println("Archivo encriptado");
                        // Ahora, 'characters' contiene todas las letras del archivo
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
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) { // Verificar si el archivo existe
                    String nombreArchivo = selectedFile.getName();
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
                    if ("txt".equals(extensionArchivo)) { // Verificar si la extensión del archivo es correcta
                        List<String> lineas = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String linea;
                            while ((linea = reader.readLine()) != null) {
                                lineas.add(linea);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        for(String c : lineas){
                            System.out.print(c);
                        }
                        int tipoEncriptacion = (int) this.selectorTipo.getSelectedItem();
                        desencriptarArchivos(lineas, tipoEncriptacion);
                        System.out.println("Archivo desencriptado");
                        // Ahora, 'characters' contiene todas las letras del archivo
                    } else {
                        JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo .txt", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });



    }


    public void encriptarArchivo(List<String> lineas, int tipoEncriptacion){
        List<String> lineasEncriptadas = new ArrayList<>();

        for(String linea : lineas){
            char[] caracteres = linea.toCharArray();
            char[] textoEncriptado = new char[caracteres.length];

            for(int j = 0; j < caracteres.length; j++){
                char c = caracteres[j];
                /*if (c == '\n') {
                    textoEncriptado[j] = c;
                    continue;
                }*/

                for(int i = 0; i < alfabeto.length; i++){
                    if(c == alfabeto[i]){
                        int nuevaPosicion = (i + tipoEncriptacion) % alfabeto.length;
                        textoEncriptado[j] = alfabeto[nuevaPosicion];
                        break;
                    }
                }
            }
            lineasEncriptadas.add(new String(textoEncriptado));
        }

        File carpeta = new File("archivosEncriptados");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para cada texto encriptado
        File file = new File(carpeta, "textoEncriptado_"+count+".txt");
        try (FileWriter writer = new FileWriter(file)) {
            for (String linea : lineasEncriptadas) {
                writer.write(linea);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desencriptarArchivos(List<String> lineas, int tipoEncriptacion){
        List<String> lineasDesEncriptadas = new ArrayList<>();

        for(String linea : lineas){
            char[] caracteres = linea.toCharArray();
            char[] textoDesEncriptado = new char[caracteres.length];

            for(int j = 0; j < caracteres.length; j++){
                char c = caracteres[j];
                /*if (c == '\n') {
                    textoDesEncriptado[j] = c;
                    continue;
                }*/
                for(int i = 0; i < alfabeto.length; i++){
                    if(c == alfabeto[i]){
                        int nuevaPosicion = ((i - tipoEncriptacion)+ alfabeto.length) % alfabeto.length;
                        textoDesEncriptado[j] = alfabeto[nuevaPosicion];
                        break;
                    }
                }
            }
            lineasDesEncriptadas.add(new String(textoDesEncriptado));
        }

        File carpeta = new File("archivosDesencriptados");
        if(!carpeta.exists()){
            carpeta.mkdir();
        }

        int count = carpeta.listFiles().length +1;
        // Crear un nuevo archivo .txt para cada texto encriptado
        File file = new File(carpeta, "textoDesEncriptado_"+count+".txt");
        try (FileWriter writer = new FileWriter(file)) {
            for (String linea : lineasDesEncriptadas) {
                writer.write(linea);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new Menu01();
    }


}

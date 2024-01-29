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

    Encript encript = new Encript();
    Decrypt decrypt = new Decrypt();

    BruteForce bruteForce = new BruteForce();


    public Menu01() {
        dibujarInterfaz();
        agregarEventoEncriptar();
        agregarEventoDesencriptar();
        agregarEventoDesencriptacionFuerzaBruta();
    }

    public void dibujarInterfaz(){
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


        //Agrega los tipos de encriptación al selector
        int[] tiposEncriptacion = new EncriptacionTypes().getTiposEncriptacion();
        for(int i : tiposEncriptacion){
            this.selectorTipo.addItem(i);
        }


    }

    public void agregarEventoEncriptar(){
        encriptarArchivosButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) {
                    String nombreArchivo = selectedFile.getName();
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1); // Obtener la extensión del archivo
                    if ("txt".equals(extensionArchivo)) {
                        //Esta lista se usará para guardar las líneas del archivo
                        List<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String line;
                            while ((line = reader.readLine()) !=null) {
                                lines.add(line);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        int tipoEncriptacion = (int) this.selectorTipo.getSelectedItem();
                        if( encript.encriptarArchivo(lines, tipoEncriptacion)){
                            JOptionPane.showMessageDialog(null, "El archivo fue encriptado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            JOptionPane.showMessageDialog(null, "El archivo no pudo ser encriptado", "Error", JOptionPane.ERROR_MESSAGE);
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

    public void agregarEventoDesencriptar(){
        desencriptarArchivosButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            //ubicación de la carpeta donde se guardan los archivos encriptados
            File currentDirectory = new File("./archivosEncriptados");
            fileChooser.setCurrentDirectory(currentDirectory);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) {
                    String nombreArchivo = selectedFile.getName();
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
                    if ("txt".equals(extensionArchivo)) {
                        //Esta lista se usará para guardar las líneas del archivo
                        List<String> lineas = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String linea;
                            while ((linea = reader.readLine()) != null) {
                                lineas.add(linea);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        int tipoEncriptacion = (int) this.selectorTipo.getSelectedItem();

                        if(decrypt.desencriptarArchivos(lineas, tipoEncriptacion)){
                            JOptionPane.showMessageDialog(null, "El archivo fue desencriptado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
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

    public void agregarEventoDesencriptacionFuerzaBruta(){
        desencriptacionFuerzaBrutaButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            File currentDirectory = new File("./archivosEncriptados");
            fileChooser.setCurrentDirectory(currentDirectory);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) {
                    String nombreArchivo = selectedFile.getName();
                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
                    if ("txt".equals(extensionArchivo)) {
                        //Esta lista se usará para guardar las líneas del archivo
                        List<String> lineas = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            String linea;
                            while ((linea = reader.readLine()) != null) {
                                lineas.add(linea);
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        if(bruteForce.desencriptarPorFuerzaBruta(lineas)){
                            JOptionPane.showMessageDialog(null, "El archivo fue desencriptado", "Aviso", JOptionPane.INFORMATION_MESSAGE);

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



    public static void main(String[] args) {

    }


}

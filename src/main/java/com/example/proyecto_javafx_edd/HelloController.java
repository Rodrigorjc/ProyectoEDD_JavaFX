package com.example.proyecto_javafx_edd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.stage.FileChooser;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class HelloController {

    // Campos de la base de datos
    // Ajustar estos campos dependiendo de la configuracion que se tenga de la base de datos y el gestor de la misma.
    String url = "jdbc:mysql://localhost:3306/GestionEmpresas?useSSL=false";
    String user = "root";
    String password = "Rom@te211";
    Connection conn;

    //TAB USUARIOS --> Boton de Agregar datos de archivos DAT
    @FXML
    private Label successLabel;

    @FXML
    private Button boton_dat;
    @FXML
    private Label mensaje_dat;
    public HelloController() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void lectorDatosDAT() {
        // Cargar archivo .dat desde el explorador de archivo una vez que se presione el boton
        boton_dat.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Agregar datos de archivo DAT a BDD");

            // Filtro para que solo soporte archivos .dat y no se puedan ni seleccionar ni ver otros archivos
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
            fileChooser.getExtensionFilters().add(extFilter);

            // Abrir el selector de archivos
            File selectedFile = fileChooser.showOpenDialog(boton_dat.getScene().getWindow());
            if (selectedFile != null) {
                try {
                    // Abrir el archivo seleccionado
                    FileInputStream fis = new FileInputStream(selectedFile);
                    DataInputStream dis = new DataInputStream(fis);

                    // Conectar con la base de datos
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO Alumnos (DNI, Nombre, Apellidos, FechaNacimiento) VALUES (?, ?, ?, ?)");

                    // Leer archivo .dat y leer linea por linea
                    String line;
                    while ((line = dis.readLine()) != null) {
                        // Separa los campos de una linea mediante el delimitador en este caso la coma
                        String[] fields = line.split(",");

                        // Setear los valores anterior mente separados en los campos de la tabla Alumnos

                        ps.setString(1, fields[0]);  // DNI
                        ps.setString(2, fields[1]);  // Nombre
                        ps.setString(3, fields[2]);  // Apellidos
                        ps.setDate(4, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(fields[3]).getTime()));  // FechaNacimiento

                        // Ejecutar la consulta para añadir los campos a la tabla Alumnos
                        ps.executeUpdate();
                    }

                    // Cerrar conexion de base de datos y archivo .dat
                    dis.close();
                    conn.close();

                    // Mensaje de exito mostrado en la interfaz de usuario y en la terminal.
                    mensaje_dat.setText("Informacion del fichero ahora registrada en Alumnos");
                    mensaje_dat.setStyle("-fx-background-color: #22b922;");
                    System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
                } catch (Exception ex) {
                    // Mensaje de error mostrado en la interfaz de usuario y en la terminal.
                    mensaje_dat.setText("ERROR al intentar leer el archivo .dat");
                    mensaje_dat.setStyle("-fx-background-color: #ec0606;");
                    ex.printStackTrace();
                }
            } else {
                // Mensaje de cancelacion mostrado en la terminal.
                System.out.println("Seleccion de archivo dat cancelada");
            }
        });
    }
    @FXML
    public void handleTutorsButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(selectedFile);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("tutor");

                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                        String apellidos = eElement.getElementsByTagName("apellidos").item(0).getTextContent();
                        String correoElectronico = eElement.getElementsByTagName("correoElectronico").item(0).getTextContent();
                        String dni = eElement.getElementsByTagName("dni").item(0).getTextContent();
                        String telefono = eElement.getElementsByTagName("telefono").item(0).getTextContent();

                        String sql = "INSERT INTO Tutores (Nombre, Apellidos, CorreoElectronico, DNI, Telefono) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, nombre);
                        pstmt.setString(2, apellidos);
                        pstmt.setString(3, correoElectronico);
                        pstmt.setString(4, dni);
                        pstmt.setString(5, telefono);
                        pstmt.executeUpdate();
                    }
                }

                // Muestra el mensaje de éxito
                successLabel.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se seleccionó ningún archivo");
        }
    }
}
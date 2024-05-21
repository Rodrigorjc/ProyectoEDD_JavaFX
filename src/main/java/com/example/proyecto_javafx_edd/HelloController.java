package com.example.proyecto_javafx_edd;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.sql.*;

public class HelloController {

    //TAB USUARIOS --> Boton de Agregar datos de archivos DAT
    @FXML
    private Label successLabel;

    @FXML
    private Button confirmarButton;
    @FXML
    private Button boton_dat;

    @FXML
    private ComboBox<String> alumnosComboBox;

    @FXML
    private ComboBox<String> empresasComboBox;

    @FXML
    private ComboBox<String> tutoresComboBox;

    @FXML
    private Label mensajeLabel;
    @FXML
    private Label mensaje_dat;

    // Campos de la base de datos
    // Ajustar estos campos dependiendo de la configuracion que se tenga de la base de datos y el gestor de la misma.
    String url = "jdbc:mysql://localhost:3306/GestionEmpresas";
    String user = "root";
    String password = "Antonio";
    Connection conn;

    public HelloController() {
        try {
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {
        // Llenar los ComboBox con datos de la base de datos
        llenarComboBoxAlumnos();
        llenarComboBoxEmpresas();
        llenarComboBoxTutores();

        // Configurar el botón Confirmar
        confirmarButton.setOnAction(event -> {
            asignarAlumnoAEmpresa();
        });
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

    // Método para inicializar la interfaz de usuario



    // Método para llenar el ComboBox de alumnos
    private void llenarComboBoxAlumnos() {
        try {
            Statement statement = conn.createStatement();

            ResultSet alumnosResultSet = statement.executeQuery("SELECT Nombre FROM Alumnos");
            while (alumnosResultSet.next()) {
                alumnosComboBox.getItems().add(alumnosResultSet.getString("Nombre"));
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para llenar el ComboBox de empresas
    private void llenarComboBoxEmpresas() {
        try {
            Statement statement = conn.createStatement();

            ResultSet empresasResultSet = statement.executeQuery("SELECT RazonSocial FROM Empresas");
            while (empresasResultSet.next()) {
                empresasComboBox.getItems().add(empresasResultSet.getString("RazonSocial"));
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para llenar el ComboBox de tutores
    private void llenarComboBoxTutores() {
        try {
            Statement statement = conn.createStatement();

            ResultSet tutoresResultSet = statement.executeQuery("SELECT Nombre FROM Tutores");
            while (tutoresResultSet.next()) {
                tutoresComboBox.getItems().add(tutoresResultSet.getString("Nombre"));
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para manejar la acción de asignar alumno a empresa
    @FXML
    private void asignarAlumnoAEmpresa() {
        String alumnoSeleccionado = alumnosComboBox.getValue();
        String empresaSeleccionada = empresasComboBox.getValue();
        String tutorSeleccionado = tutoresComboBox.getValue();


        // Aquí debes verificar si el alumno ya está asignado a una empresa
        boolean alumnoYaAsignado = verificarAlumnoAsignado(alumnoSeleccionado);

        // Crear el mensaje resultante
        String mensaje = "";
        if (alumnoYaAsignado) {
            mensaje = "El alumno " + alumnoSeleccionado + " ya está asignado a una empresa.";
        } else {
            //Conectar con la base de datos
            try {

                String sqlAlumno = "SELECT CodigoAlumno From alumnos WHERE Nombre = ?";
                PreparedStatement preparedStatementAlumno = conn.prepareStatement(sqlAlumno);
                preparedStatementAlumno.setString(1, alumnoSeleccionado);
                ResultSet resultSetAlumno = preparedStatementAlumno.executeQuery();
                resultSetAlumno.next();
                int alumnoId = resultSetAlumno.getInt("CodigoAlumno");

                String sqlEmpresa = "SELECT CodigoEmpresa From empresas WHERE RazonSocial = ?";
                PreparedStatement preparedStatementEmpresa = conn.prepareStatement(sqlEmpresa);
                preparedStatementEmpresa.setString(1, empresaSeleccionada);
                ResultSet resultSetEmpresa = preparedStatementEmpresa.executeQuery();
                resultSetEmpresa.next();
                int empresaId = resultSetEmpresa.getInt("CodigoEmpresa");

                String sqlTutor = "SELECT CodigoTutor From tutores WHERE Nombre = ?";
                PreparedStatement preparedStatementTutor = conn.prepareStatement(sqlTutor);
                preparedStatementTutor.setString(1, tutorSeleccionado);
                ResultSet resultSetTutor = preparedStatementTutor.executeQuery();
                resultSetTutor.next();
                int tutorId = resultSetTutor.getInt("CodigoTutor");


                String sqlInsert = "INSERT INTO asignados (CodigoAlumno, CodigoEmpresa, CodigoTutor) VALUES (?, ?, ?)";
                PreparedStatement preparedStatementInsert = conn.prepareStatement(sqlInsert);
                preparedStatementInsert.setInt(1, alumnoId);
                preparedStatementInsert.setInt(2, empresaId);
                preparedStatementInsert.setInt(3, tutorId);
                preparedStatementInsert.executeUpdate();

                mensaje = "Alumno: " + alumnoSeleccionado + ", Empresa: " + empresaSeleccionada + ", Tutor: " + tutorSeleccionado;
                mensajeLabel.setText(mensaje);
                mensajeLabel.setVisible(true);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // Método para verificar si el alumno ya está asignado
    private boolean verificarAlumnoAsignado (String alumno) {
        return false;
    }
}

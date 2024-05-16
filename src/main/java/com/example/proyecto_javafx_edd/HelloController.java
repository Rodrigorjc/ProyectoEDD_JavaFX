package com.example.proyecto_javafx_edd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class HelloController {
    @FXML
    private ComboBox<String> alumnosComboBox;

    @FXML
    private ComboBox<String> empresasComboBox;

    @FXML
    private ComboBox<String> tutoresComboBox;

    @FXML
    private Label mensajeLabel;
    @FXML
    private Button confirmarButton;

    // Método para inicializar la interfaz de usuario
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


    // Método para llenar el ComboBox de alumnos
    private void llenarComboBoxAlumnos() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionempresas", "root", "1234");
            Statement statement = connection.createStatement();

            ResultSet alumnosResultSet = statement.executeQuery("SELECT Nombre FROM Alumnos");
            while (alumnosResultSet.next()) {
                alumnosComboBox.getItems().add(alumnosResultSet.getString("Nombre"));
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para llenar el ComboBox de empresas
    private void llenarComboBoxEmpresas() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionempresas", "root", "1234");
            Statement statement = connection.createStatement();

            ResultSet empresasResultSet = statement.executeQuery("SELECT RazonSocial FROM Empresas");
            while (empresasResultSet.next()) {
                empresasComboBox.getItems().add(empresasResultSet.getString("RazonSocial"));
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para llenar el ComboBox de tutores
    private void llenarComboBoxTutores() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionempresas", "root", "1234");
            Statement statement = connection.createStatement();

            ResultSet tutoresResultSet = statement.executeQuery("SELECT Nombre FROM Tutores");
            while (tutoresResultSet.next()) {
                tutoresComboBox.getItems().add(tutoresResultSet.getString("Nombre"));
            }

            statement.close();
            connection.close();
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
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionempresas", "root", "1234");

                String sqlAlumno = "SELECT CodigoAlumno From alumnos WHERE Nombre = ?";
                PreparedStatement preparedStatementAlumno = connection.prepareStatement(sqlAlumno);
                preparedStatementAlumno.setString(1, alumnoSeleccionado);
                ResultSet resultSetAlumno = preparedStatementAlumno.executeQuery();
                resultSetAlumno.next();
                int alumnoId = resultSetAlumno.getInt("CodigoAlumno");

                String sqlEmpresa = "SELECT CodigoEmpresa From empresas WHERE RazonSocial = ?";
                PreparedStatement preparedStatementEmpresa = connection.prepareStatement(sqlEmpresa);
                preparedStatementEmpresa.setString(1, empresaSeleccionada);
                ResultSet resultSetEmpresa = preparedStatementEmpresa.executeQuery();
                resultSetEmpresa.next();
                int empresaId = resultSetEmpresa.getInt("CodigoEmpresa");

                String sqlTutor = "SELECT CodigoTutor From tutores WHERE Nombre = ?";
                PreparedStatement preparedStatementTutor = connection.prepareStatement(sqlTutor);
                preparedStatementTutor.setString(1, tutorSeleccionado);
                ResultSet resultSetTutor = preparedStatementTutor.executeQuery();
                resultSetTutor.next();
                int tutorId = resultSetTutor.getInt("CodigoTutor");


                String sqlInsert = "INSERT INTO asignados (CodigoAlumno, CodigoEmpresa, CodigoTutor) VALUES (?, ?, ?)";
                PreparedStatement preparedStatementInsert = connection.prepareStatement(sqlInsert);
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
    private boolean verificarAlumnoAsignado(String alumno) {
        // Aquí debes implementar la lógica para verificar si el alumno ya está asignado a una empresa
        // Por ejemplo, puedes consultar la base de datos para verificar si existe una asignación para este alumno
        return false; // Por ahora devolvemos false como ejemplo
    }
}

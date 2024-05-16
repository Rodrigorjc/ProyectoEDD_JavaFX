package com.example.proyecto_javafx_edd;

import javafx.fxml.FXML;
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

public class HelloController {
    @FXML
    private Label successLabel;

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

                // Conexión a la base de datos
                String url = "jdbc:mysql://localhost:3306/GestionEmpresas?useSSL=false";
                String user = "root";
                String password = "Rom@te211";
                Connection conn = DriverManager.getConnection(url, user, password);

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
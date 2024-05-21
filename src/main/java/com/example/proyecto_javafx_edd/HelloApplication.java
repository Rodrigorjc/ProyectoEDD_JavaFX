package com.example.proyecto_javafx_edd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vista.fxml"));
        Parent root = loader.load();

        // Obtener el controlador del archivo FXML
        EmpresaController empresaController = loader.getController();

        // Configurar la escena y mostrarla
        Scene scene = new Scene(root, 680, 1015);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
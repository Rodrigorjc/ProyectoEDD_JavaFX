package com.example.proyecto_javafx_edd;

import java.sql.*;

public class ConsultaAlumnos {
    private static final String URL = "jdbc:mariadb://localhost:3306/nombre_basedatos";
    private static final String USER = "usuario";
    private static final String PASSWORD = "contraseña";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Sentencia SQL para la consulta
            String sql = "SELECT Nombre, Apellidos FROM Alumnos";

            // Preparar la sentencia SQL
            PreparedStatement statement = connection.prepareStatement(sql);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Recorrer los resultados
            while (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                String apellidos = resultSet.getString("Apellidos");
                System.out.println("Nombre: " + nombre + ", Apellidos: " + apellidos);
            }

            // Cerrar la conexión y los statement
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

package com.example.proyecto_javafx_edd;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/GestionEmpresas?serverTimezone=Europe/Madrid&verifyServerCertificate=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Chispa10.";

    public List<Empresa> obtenerTodasLasEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        String query = "SELECT * FROM empresas";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Empresa empresa = new Empresa(
                        rs.getInt("codigoEmpresa"),
                        rs.getString("cif"),
                        rs.getString("razonSocial"),
                        rs.getString("direccion"),
                        rs.getString("cp"),
                        rs.getString("localidad"),
                        rs.getString("tipoJornada"),
                        rs.getString("modalidad"),
                        rs.getString("mailEmpresa")
                );
                empresas.add(empresa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresas;
    }

    public boolean insertarEmpresa(Empresa empresa) {
        String query = "INSERT INTO empresas (cif, razonSocial, direccion, cp, localidad, tipoJornada, modalidad, mailEmpresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, empresa.getCif());
            pstmt.setString(2, empresa.getRazonSocial());
            pstmt.setString(3, empresa.getDireccion());
            pstmt.setString(4, empresa.getCp());
            pstmt.setString(5, empresa.getLocalidad());
            pstmt.setString(6, empresa.getTipoJornada());
            pstmt.setString(7, empresa.getModalidad());
            pstmt.setString(8, empresa.getMailEmpresa());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean eliminarEmpresa(int codigoEmpresa) {
        String query = "DELETE FROM empresas WHERE codigoEmpresa = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoEmpresa);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificarEmpresa(Empresa empresa) {
        String query = "UPDATE empresas SET cif = ?, razonSocial = ?, direccion = ?, cp = ?, localidad = ?, tipoJornada = ?, modalidad = ?, mailEmpresa = ? WHERE codigoEmpresa = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, empresa.getCif());
            pstmt.setString(2, empresa.getRazonSocial());
            pstmt.setString(3, empresa.getDireccion());
            pstmt.setString(4, empresa.getCp());
            pstmt.setString(5, empresa.getLocalidad());
            pstmt.setString(6, empresa.getTipoJornada());
            pstmt.setString(7, empresa.getModalidad());
            pstmt.setString(8, empresa.getMailEmpresa());
            pstmt.setInt(9, empresa.getCodigoEmpresa());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obtenerSiguienteCodigoEmpresa() {
        String query = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'GestionEmpresas' AND TABLE_NAME = 'Empresas'";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // En caso de error
    }

}
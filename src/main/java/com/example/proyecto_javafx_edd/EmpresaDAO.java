package com.example.proyecto_javafx_edd;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/gestionempresas?useSSL=false&serverTimezone=Europe/Madrid&verifyServerCertificate=false";
    private static final String USER = "root";
    private String PASSWORD = "Roma@te211";
    String url = "jdbc:mysql://localhost:3306/gestionempresas?user=root&password=Rom@te211&auth_plugin=mysql_native_password&useSSL=false";

    public int obtenerIdTutor(Tutor tutor) {
        String query = "SELECT CodigoTutor FROM tutores WHERE DNI = ? AND Nombre = ? AND Apellidos = ? AND Telefono = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, tutor.getDniTutor());
            pstmt.setString(2, tutor.getNombreTutor());
            pstmt.setString(3, tutor.getApellidosTutor());
            pstmt.setString(4, tutor.getTelefonoTutor());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CodigoTutor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // En caso de error
    }
    public boolean modificarTutor(Tutor tutor) {
        String query = "UPDATE tutores SET DNI = ?, Nombre = ?, Apellidos = ?, Telefono = ? WHERE CodigoTutor = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, tutor.getDniTutor());
            pstmt.setString(2, tutor.getNombreTutor());
            pstmt.setString(3, tutor.getApellidosTutor());
            pstmt.setString(4, tutor.getTelefonoTutor());
            pstmt.setInt(5, tutor.getCodigoTutor());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int insertarTutor(Tutor tutor) {
        String query = "INSERT INTO tutores (DNI, Nombre, Apellidos, Telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tutor.getDniTutor());
            pstmt.setString(2, tutor.getNombreTutor());
            pstmt.setString(3, tutor.getApellidosTutor());
            pstmt.setString(4, tutor.getTelefonoTutor());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // En caso de error
    }
    public void insertarTutores(Tutor tutorResponsable, Tutor tutorLegal) {
        int idTutorResponsable = insertarTutor(tutorResponsable);
        int idTutorLegal = insertarTutor(tutorLegal);
        // Aquí puedes manejar los IDs de los tutores insertados si lo necesitas
    }
    public Tutor obtenerTutorPorId(int id) {
        String query = "SELECT * FROM tutores WHERE CodigoTutor = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Tutor(rs.getString("DNI"), rs.getString("Nombre"), rs.getString("Apellidos"), rs.getString("Telefono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // En caso de error
    }

    public List<Empresa> obtenerTodasLasEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        String query = "SELECT * FROM empresas";

        try (Connection conn = DriverManager.getConnection(url);
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
                        rs.getString("mailEmpresa"),
                        rs.getInt("Responsable"),
                        rs.getInt("TutorLaboral"));
                empresas.add(empresa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresas;
    }

    public boolean insertarEmpresa(Empresa empresa) {
        String query = "INSERT INTO empresas (CIF, RazonSocial, Direccion, CP, Localidad, TipoJornada, Modalidad, MailEmpresa, Responsable, TutorLaboral ) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, empresa.getCif());
            pstmt.setString(2, empresa.getRazonSocial());
            pstmt.setString(3, empresa.getDireccion());
            pstmt.setString(4, empresa.getCp());
            pstmt.setString(5, empresa.getLocalidad());
            pstmt.setString(6, empresa.getTipoJornada());
            pstmt.setString(7, empresa.getModalidad());
            pstmt.setString(8, empresa.getMailEmpresa());
            pstmt.setInt(9, empresa.getIdTutorResponsable());
            pstmt.setInt(10, empresa.getIdTutorLegal());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean eliminarEmpresa(int codigoEmpresa) {
        String query = "DELETE FROM empresas WHERE codigoEmpresa = ?";
        try (Connection conn = DriverManager.getConnection(url);
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
        try (Connection conn = DriverManager.getConnection(url);
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
        try (Connection conn = DriverManager.getConnection(url);
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
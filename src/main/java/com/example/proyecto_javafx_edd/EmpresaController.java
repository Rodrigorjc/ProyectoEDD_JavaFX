package com.example.proyecto_javafx_edd;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;



public class EmpresaController {

    @FXML
    private TextField txtCodigoEmpresa;
    @FXML
    private TextField txtCif;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtCp;
    @FXML
    private TextField txtLocalidad;
    @FXML
    private ComboBox txtTipoJornada;
    @FXML
    private ComboBox txtModalidad;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtTelefonoTutor;
    @FXML
    private TextField txtDniResponsable;
    @FXML
    private TextField txtDniTutor;
    @FXML
    private TextField txtNombreResponsable;
    @FXML
    private TextField txtApellidosResponsable;
    @FXML
    private TextField txtNombreTutor;
    @FXML
    private TextField txtApellidosTutor;
    @FXML
    private TableView<Empresa> tblEmpresas;
    @FXML
    private TableColumn<Empresa, String> colCif;
    @FXML
    private TableColumn<Empresa, String> colNombre;
    @FXML
    private TableColumn<Empresa, String> colDireccion;
    @FXML
    private TableColumn<Empresa, String> colCp;
    @FXML
    private TableColumn<Empresa, String> colLocalidad;
    @FXML
    private TableColumn<Empresa, String> colTipoJornada;
    @FXML
    private TableColumn<Empresa, String> colModalidad;
    @FXML
    private TableColumn<Empresa, String> colMail;

    private EmpresaDAO empresaDAO;
    private EmpresaDAO tutorDAO;
    private ObservableList<Empresa> empresas;

    public void initialize() {
        empresaDAO = new EmpresaDAO();
        tutorDAO = new EmpresaDAO();
        empresas = FXCollections.observableArrayList(empresaDAO.obtenerTodasLasEmpresas());

        colCif.setCellValueFactory(new PropertyValueFactory<>("cif"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colCp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        colLocalidad.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        colTipoJornada.setCellValueFactory(new PropertyValueFactory<>("tipoJornada"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mailEmpresa"));

        tblEmpresas.setItems(empresas);

        // Obtener y mostrar el siguiente CodigoEmpresa
        int siguienteCodigoEmpresa = empresaDAO.obtenerSiguienteCodigoEmpresa();
        if (siguienteCodigoEmpresa != -1) {
            txtCodigoEmpresa.setText(String.valueOf(siguienteCodigoEmpresa));
        }
        agregarOpcionesTipoJornada();
        agregarOpcionesModalidad();
    }

    private void agregarOpcionesTipoJornada() {
        txtTipoJornada.getItems().addAll("Partida", "Continua");
    }

    @FXML
    private void seleccionarTipoJornada(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String tipoJornada = menuItem.getText();

        // Obtener la fila y la celda seleccionadas
        TablePosition<Empresa, ?> pos = tblEmpresas.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        TableColumn<Empresa, ?> col = pos.getTableColumn();

        // Obtener el valor de la celda seleccionada
        String selectedCell = (String) col.getCellObservableValue(row).getValue();

        // Actualizar el valor de la celda seleccionada con el valor seleccionado del SplitMenuButton
        selectedCell = tipoJornada;
    }


    private void agregarOpcionesModalidad() {
        txtModalidad.getItems().addAll("Presencial", "Semipresencial", "Distancia");
    }


    @FXML
    private void insertarEmpresa(ActionEvent event) {
        try {
            int codigoEmpresa = Integer.parseInt(txtCodigoEmpresa.getText()); // Obtener el código desde el campo de texto
            String cif = txtCif.getText();
            String razonSocial = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String cp = txtCp.getText();
            String localidad = txtLocalidad.getText();
            String tipoJornada = txtTipoJornada.getValue().toString();
            String modalidad = txtModalidad.getValue().toString();
            String mailEmpresa = txtMail.getText();
            String dniTutorResponsable = txtDniResponsable.getText();
            String nombreTutorResponsable = txtNombreResponsable.getText();
            String apellidosTutorResponsable = txtApellidosResponsable.getText();

            String telefonoTutorLegal = txtTelefonoTutor.getText();
            String dniTutorLegal = txtDniTutor.getText();
            String nombreTutorLegal = txtNombreTutor.getText();
            String apellidosTutorLegal = txtApellidosTutor.getText();

            if (dniTutorResponsable.isEmpty() || nombreTutorResponsable.isEmpty() || apellidosTutorResponsable.isEmpty() || telefonoTutorLegal.isEmpty() || dniTutorLegal.isEmpty() || nombreTutorLegal.isEmpty() || apellidosTutorLegal.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos de los tutores deben ser completados.");
                return;
            }
            Tutor tutorResponsable = new Tutor(dniTutorResponsable, nombreTutorResponsable, apellidosTutorResponsable, telefonoTutorLegal);


            Tutor tutorLegal = new Tutor(dniTutorLegal, nombreTutorLegal, apellidosTutorLegal, telefonoTutorLegal);
            int idTutorLegal = tutorDAO.obtenerIdTutor(tutorLegal);
            int idTutorResponsable = tutorDAO.obtenerIdTutor(tutorResponsable);

            if (cif.isEmpty() || razonSocial.isEmpty() || direccion.isEmpty() || cp.isEmpty() || localidad.isEmpty() || tipoJornada.isEmpty() || modalidad.isEmpty() || mailEmpresa.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos deben ser completados.");
                return;
            }

            Empresa empresa = new Empresa(codigoEmpresa, cif, razonSocial, direccion, cp, localidad, tipoJornada, modalidad, mailEmpresa, idTutorResponsable, idTutorLegal);
            empresaDAO.insertarEmpresa(empresa);
            empresas.add(empresa);

            // Actualizar el campo de texto con el siguiente codigo empresa
            int siguienteCodigoEmpresa = empresaDAO.obtenerSiguienteCodigoEmpresa();
            if (siguienteCodigoEmpresa != -1) {
                txtCodigoEmpresa.setText(String.valueOf(siguienteCodigoEmpresa));
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Código Empresa debe ser un número.");
        }
    }


    @FXML
    private void eliminarEmpresa(ActionEvent event) {
        Empresa selectedEmpresa = tblEmpresas.getSelectionModel().getSelectedItem();
        if (selectedEmpresa != null) {
            empresaDAO.eliminarEmpresa(selectedEmpresa.getCodigoEmpresa());
            empresas.remove(selectedEmpresa);
        } else {
            mostrarAlerta("Error", "Seleccione una empresa para eliminar.");
        }
    }

    @FXML
    private void modificarEmpresa(ActionEvent event) {
        Empresa selectedEmpresa = tblEmpresas.getSelectionModel().getSelectedItem();
        if (selectedEmpresa != null) {
            try {
                int codigoEmpresa = selectedEmpresa.getCodigoEmpresa(); // Utiliza el código de la empresa seleccionada
                String cif = txtCif.getText();
                String razonSocial = txtNombre.getText();
                String direccion = txtDireccion.getText();
                String cp = txtCp.getText();
                String localidad = txtLocalidad.getText();
                String tipoJornada = txtTipoJornada.getValue().toString();
                String modalidad = txtModalidad.getValue().toString();
                String mailEmpresa = txtMail.getText();

                String dniTutorResponsable = txtDniResponsable.getText();
                String nombreTutorResponsable = txtNombreResponsable.getText();
                String apellidosTutorResponsable = txtApellidosResponsable.getText();

                String dniTutorLegal = txtDniTutor.getText();
                String nombreTutorLegal = txtNombreTutor.getText();
                String apellidosTutorLegal = txtApellidosTutor.getText();
                String telefonoTutorLegal = txtTelefonoTutor.getText();



                if (cif.isEmpty() || razonSocial.isEmpty() || direccion.isEmpty() || cp.isEmpty() || localidad.isEmpty() || tipoJornada.isEmpty() || modalidad.isEmpty() || mailEmpresa.isEmpty()) {
                    mostrarAlerta("Error", "Todos los campos deben ser completados.");
                    return;
                }
                Tutor tutorResponsable = new Tutor(dniTutorResponsable, nombreTutorResponsable, apellidosTutorResponsable, telefonoTutorLegal);
                int idtr = tutorDAO.obtenerIdTutor(tutorResponsable);
                Tutor tutorLegal = new Tutor(dniTutorLegal, nombreTutorLegal, apellidosTutorLegal, telefonoTutorLegal);
                int idt = tutorDAO.obtenerIdTutor(tutorLegal);

                empresaDAO.modificarTutor(tutorResponsable);
                empresaDAO.modificarTutor(tutorLegal);

                Empresa empresaModificada = new Empresa(codigoEmpresa, cif, razonSocial, direccion, cp, localidad, tipoJornada, modalidad, mailEmpresa, idtr, idt);
                empresaDAO.modificarEmpresa(empresaModificada);

                int selectedIndex = empresas.indexOf(selectedEmpresa);
                empresas.set(selectedIndex, empresaModificada);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Código Empresa debe ser un número.");
            }
        } else {
            mostrarAlerta("Error", "Seleccione una empresa para modificar.");
        }
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCif.clear();
        txtNombre.clear();
        txtDireccion.clear();
        txtCp.clear();
        txtLocalidad.clear();
        txtTipoJornada.getSelectionModel().clearSelection();
        txtModalidad.getSelectionModel().clearSelection();
        txtMail.clear();
    }




    @FXML
    private void seleccionarEmpresa() {
        Empresa selectedEmpresa = tblEmpresas.getSelectionModel().getSelectedItem();
        if (selectedEmpresa != null) {
            // Rellena los campos del formulario con los datos de la empresa seleccionada
            txtCodigoEmpresa.setText(String.valueOf(selectedEmpresa.getCodigoEmpresa()));
            txtCif.setText(selectedEmpresa.getCif());
            txtNombre.setText(selectedEmpresa.getRazonSocial());
            txtDireccion.setText(selectedEmpresa.getDireccion());
            txtCp.setText(selectedEmpresa.getCp());
            txtLocalidad.setText(selectedEmpresa.getLocalidad());
            txtTipoJornada.setValue(selectedEmpresa.getTipoJornada());
            txtModalidad.setValue(selectedEmpresa.getModalidad());
            txtMail.setText(selectedEmpresa.getMailEmpresa());
            // Obtén los datos de los tutores
            Tutor tutorResponsable = empresaDAO.obtenerTutorPorId(selectedEmpresa.getIdTutorResponsable());
            Tutor tutorLegal = empresaDAO.obtenerTutorPorId(selectedEmpresa.getIdTutorLegal());
            // Rellena los campos del formulario con los datos de los tutores
            if (tutorResponsable != null) {
                txtDniResponsable.setText(tutorResponsable.getDniTutor());
                txtNombreResponsable.setText(tutorResponsable.getNombreTutor());
                txtApellidosResponsable.setText(tutorResponsable.getApellidosTutor());
            }

            if (tutorLegal != null) {
                txtDniTutor.setText(tutorLegal.getDniTutor());
                txtNombreTutor.setText(tutorLegal.getNombreTutor());
                txtApellidosTutor.setText(tutorLegal.getApellidosTutor());
                txtTelefonoTutor.setText(tutorLegal.getTelefonoTutor());
            }
        }
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}

package com.example.proyecto_javafx_edd;

import javafx.beans.property.*;

public class Empresa {
    private final IntegerProperty codigoEmpresa;
    private final StringProperty cif;
    private final StringProperty razonSocial;
    private final StringProperty direccion;
    private final StringProperty cp;
    private final StringProperty localidad;
    private final StringProperty tipoJornada;
    private final StringProperty modalidad;
    private final StringProperty mailEmpresa;
    private IntegerProperty idTutorResponsable = null;
    private IntegerProperty idTutorLegal = null;

    public Empresa(int codigoEmpresa, String cif, String razonSocial, String direccion, String cp, String localidad, String tipoJornada, String modalidad, String mailEmpresa, int idTutorResponsable, int idTutorLegal) {
        this.codigoEmpresa = new SimpleIntegerProperty(codigoEmpresa);
        this.cif = new SimpleStringProperty(cif);
        this.razonSocial = new SimpleStringProperty(razonSocial);
        this.direccion = new SimpleStringProperty(direccion);
        this.cp = new SimpleStringProperty(cp);
        this.localidad = new SimpleStringProperty(localidad);
        this.tipoJornada = new SimpleStringProperty(tipoJornada);
        this.modalidad = new SimpleStringProperty(modalidad);
        this.mailEmpresa = new SimpleStringProperty(mailEmpresa);
        this.idTutorResponsable =new SimpleIntegerProperty(idTutorResponsable) ;
        this.idTutorLegal = new SimpleIntegerProperty(idTutorResponsable);
    }

    public Empresa(int codigoEmpresa, String cif, String razonSocial, String direccion, String cp, String localidad, String tipoJornada, String modalidad, String mailEmpresa, String telefonoTutor, String dniResponsable, String dniTutor, String nombreResponsable, String apellidosResponsable, String nombreTutor, String apellidosTutor) {
        this.codigoEmpresa = new SimpleIntegerProperty(codigoEmpresa);
        this.cif = new SimpleStringProperty(cif);
        this.razonSocial = new SimpleStringProperty(razonSocial);
        this.direccion = new SimpleStringProperty(direccion);
        this.cp = new SimpleStringProperty(cp);
        this.localidad = new SimpleStringProperty(localidad);
        this.tipoJornada = new SimpleStringProperty(tipoJornada);
        this.modalidad = new SimpleStringProperty(modalidad);
        this.mailEmpresa = new SimpleStringProperty(mailEmpresa);
        this.idTutorResponsable = idTutorResponsable;
        this.idTutorLegal = idTutorLegal;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa.get();
    }

    public IntegerProperty codigoEmpresaProperty() {
        return codigoEmpresa;
    }

    public String getCif() {
        return cif.get();
    }

    public StringProperty cifProperty() {
        return cif;
    }

    public String getRazonSocial() {
        return razonSocial.get();
    }

    public StringProperty razonSocialProperty() {
        return razonSocial;
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public String getCp() {
        return cp.get();
    }

    public StringProperty cpProperty() {
        return cp;
    }

    public String getLocalidad() {
        return localidad.get();
    }

    public StringProperty localidadProperty() {
        return localidad;
    }

    public String getTipoJornada() {
        return tipoJornada.get();
    }

    public StringProperty tipoJornadaProperty() {
        return tipoJornada;
    }

    public String getModalidad() {
        return modalidad.get();
    }

    public StringProperty modalidadProperty() {
        return modalidad;
    }

    public String getMailEmpresa() {
        return mailEmpresa.get();
    }

    public StringProperty mailEmpresaProperty() {
        return mailEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa.set(codigoEmpresa);
    }

    public void setCif(String cif) {
        this.cif.set(cif);
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial.set(razonSocial);
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public void setCp(String cp) {
        this.cp.set(cp);
    }

    public void setLocalidad(String localidad) {
        this.localidad.set(localidad);
    }

    public void setTipoJornada(String tipoJornada) {
        this.tipoJornada.set(tipoJornada);
    }

    public void setModalidad(String modalidad) {
        this.modalidad.set(modalidad);
    }

    public void setMailEmpresa(String mailEmpresa) {
        this.mailEmpresa.set(mailEmpresa);
    }

    public int getIdTutorResponsable() {
        return idTutorResponsable.get();
    }

    public IntegerProperty idTutorResponsableProperty() {
        return idTutorResponsable;
    }

    public void setIdTutorResponsable(int idTutorResponsable) {
        this.idTutorResponsable.set(idTutorResponsable);
    }

    public int getIdTutorLegal() {
        return idTutorLegal.get();
    }

    public IntegerProperty idTutorLegalProperty() {
        return idTutorLegal;
    }

    public void setIdTutorLegal(int idTutorLegal) {
        this.idTutorLegal.set(idTutorLegal);
    }
}
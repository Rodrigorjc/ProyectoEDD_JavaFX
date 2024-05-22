package com.example.proyecto_javafx_edd;

public class Tutor {
    private final String dniTutor;
    private final String nombreTutor;
    private final String apellidosTutor;
    private final String telefonoTutor;
    private  int CodigoTutor;

    public Tutor(String dniTutor, String nombreTutor, String apellidosTutor, String telefonoTutor) {
        this.dniTutor = dniTutor;
        this.nombreTutor = nombreTutor;
        this.apellidosTutor = apellidosTutor;
        this.telefonoTutor = telefonoTutor;
    }
    public Tutor(String dniTutor, String nombreTutor, String apellidosTutor, String telefonoTutor, int CodigoTutor) {
        this.dniTutor = dniTutor;
        this.nombreTutor = nombreTutor;
        this.apellidosTutor = apellidosTutor;
        this.telefonoTutor = telefonoTutor;
        this.CodigoTutor = CodigoTutor;
    }

    public String getDniTutor() {
        return dniTutor;
    }

    public String getNombreTutor() {
        return nombreTutor;
    }

    public String getApellidosTutor() {
        return apellidosTutor;
    }

    public String getTelefonoTutor() {
        return telefonoTutor;
    }

    public int getCodigoTutor() {
        return CodigoTutor;
    }

    public void setCodigoTutor(int codigoTutor) {
        CodigoTutor = codigoTutor;
    }
}

package com.example.delacruz.finalandroid.model;

public class Envios {

    private String codigo;
    private String n_envio;
    private String transportista;
    private String remitente;
    private String destinatario;
    private String fecha;
    private String peso;
    private String descripcion;

    public Envios() {
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Envios(String codigo, String n_envio, String transportista, String remitente, String fecha, String peso, String descripcion) {
        this.codigo = codigo;
        this.n_envio = n_envio;
        this.transportista = transportista;
        this.remitente = remitente;
        this.fecha = fecha;
        this.peso = peso;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getN_envio() {
        return n_envio;
    }

    public void setN_envio(String n_envio) {
        this.n_envio = n_envio;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

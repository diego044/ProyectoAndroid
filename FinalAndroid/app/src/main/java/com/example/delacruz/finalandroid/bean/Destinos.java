package com.example.delacruz.finalandroid.bean;

public class Destinos {

    private String nom_cliente;
    private String ape_cliente;
    private String direccion;
    private int estado;
    private int id_transportista;
    private double latitud;
    private double longitud;

    public Destinos() {
    }

    public Destinos(String nom_cliente, String ape_cliente, String direccion, int estado, int id_transportista, double latitud, double longitud) {
        this.nom_cliente = nom_cliente;
        this.ape_cliente = ape_cliente;
        this.direccion = direccion;
        this.estado = estado;
        this.id_transportista = id_transportista;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNom_cliente() {
        return nom_cliente;
    }

    public void setNom_cliente(String nom_cliente) {
        this.nom_cliente = nom_cliente;
    }

    public String getApe_cliente() {
        return ape_cliente;
    }

    public void setApe_cliente(String ape_cliente) {
        this.ape_cliente = ape_cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_transportista() {
        return id_transportista;
    }

    public void setId_transportista(int id_transportista) {
        this.id_transportista = id_transportista;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
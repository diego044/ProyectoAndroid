package com.example.delacruz.finalandroid.model;

public class Transportista {

    private String codigo;
    private String nombre;
    private String apellido;
    private String documento;
    private String n_documento;
    private String direccion;
    private String vehiculo;

    public Transportista() {
    }

    public Transportista(String codigo, String nombre, String apellido, String documento, String n_documento, String direccion, String vehiculo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.n_documento = n_documento;
        this.direccion = direccion;
        this.vehiculo = vehiculo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getN_documento() {
        return n_documento;
    }

    public void setN_documento(String nro_documento) {
        this.n_documento = nro_documento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
}

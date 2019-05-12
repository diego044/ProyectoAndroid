package com.example.delacruz.finalandroid.bean;

public class Transportista {

    private String nombre;
    private String apellido;
    private String correo;
    private int id_cliente;
    private String Cliente;


    public Transportista(String nombre, String apellido, String correo, int id_cliente, String cliente) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.id_cliente = id_cliente;
        Cliente = cliente;
    }

    public Transportista() {
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }
}

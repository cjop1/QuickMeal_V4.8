package com.grupo4.quickmeal_v28_def.modelo;

public class Restaurante {

    private String id;
    private String restaurante;
    private String direccion;
    private String locacion;
    private byte[] image;

    public Restaurante() {
    }

    public Restaurante(String id, String restaurante, String direccion, String locacion, byte[] image) {
        this.id = id;
        this.restaurante = restaurante;
        this.direccion = direccion;
        this.locacion = locacion;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

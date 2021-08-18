package com.example.riff.models;

public class CodigoEvento {

    private String codigo, descripcion;
    private int id_tipo_falla;

    public int getId_tipo_falla() {
        return id_tipo_falla;
    }

    public void setId_tipo_falla(int id_tipo_falla) {
        this.id_tipo_falla = id_tipo_falla;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

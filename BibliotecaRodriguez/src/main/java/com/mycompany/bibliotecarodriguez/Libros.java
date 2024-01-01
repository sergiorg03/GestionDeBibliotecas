package com.mycompany.bibliotecarodriguez;

import java.sql.Date;
import java.time.LocalDateTime;



public class Libros {

    // Atributos de la clase
    private int id;
    private String nombre;
    private String descripcion;
    private String autor;
    private Date fechaPublicacion;
    
    // Constructor por defecto
    public Libros(){
        this.id = -1;
        this.nombre = "";
        this.descripcion = "";
        this.autor = "";
        this.fechaPublicacion = Date.valueOf(LocalDateTime.now().toLocalDate());
    }
    
    // Constructor con parametros
    public Libros(int id, String nombre, String desc, String autor, Date fechaPublicacion){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = desc;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
    }
    
    // Métodos GET
    public int getID() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    // Métodos SET
    public void setID(int id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    @Override
    public String toString(){
        return "El libro "+ this.nombre +" publicado por "+ this.autor +" el "+ this.fechaPublicacion +" trada de: "+ this.descripcion;
    }
}
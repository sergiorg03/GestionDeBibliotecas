package Entities;

import java.sql.Date;
import java.time.LocalDateTime;



public class Libros {

    // Atributos de la clase
    private int id;
    private String nombre;
    private String descripcion;
    private String autor;
    private Date fechaPublicacion;
    private boolean disponible;
    
    // Constructor por defecto
    public Libros(){
        this.id = -1;
        this.nombre = "";
        this.descripcion = "";
        this.autor = "";
        this.fechaPublicacion = Date.valueOf(LocalDateTime.now().toLocalDate());
        this.disponible = true;
    }
    
    // Constructor con parametros
    public Libros(int id, String nombre, String desc, String autor, Date fechaPublicacion, boolean disponible){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = desc;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.disponible = disponible;
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
    
    public boolean getDisponibilidad(){
        return disponible;
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
    
    public void setdisponibilidad(boolean disponibleEnBiblioteca){
        this.disponible = disponibleEnBiblioteca;
    } 
    
    @Override
    public String toString(){
        return "El libro "+ this.nombre +" fue publicado por "+ this.autor +" el "+ this.fechaPublicacion +" trada de: "+ this.descripcion +"\n\nEsta disponible en la biblioteca: "+ ((this.disponible)? "Si. ": "No. ");
    }
}
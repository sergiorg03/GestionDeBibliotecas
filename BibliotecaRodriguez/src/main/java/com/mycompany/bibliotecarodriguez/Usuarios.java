package com.mycompany.bibliotecarodriguez;

public class Usuarios {

    // Atributos de la clase
    private String DNI;
    private String nombre;
    private String apellidos;
    private String mail;
    private String telf;
    
    // Constructor por defecto
    public Usuarios(){
        this.DNI = "";
        this.nombre = "";
        this.apellidos = "";
        this.mail = "";
        this.telf = "";
    }
    
    // Constructor con parametros
    public Usuarios(String dni, String nombre, String apellidos, String mail, String telf){
        this.DNI = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
        this.telf = telf;
    }
    
    // Metodos GET
    public String getDNI(){
        return this.DNI;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String getApellidos(){
        return this.apellidos;
    }
    
    public String getMail(){
        return this.mail;
    }
    
    public String getTelf(){
        return this.telf;
    }
    
    // Metodos SET
    public void setDNI (String dni){
        this.DNI = dni;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }
    
    public void setTelf(String telf){
        this.telf = telf;
    }
    
    @Override
    public String toString(){
        return "El cliente "+this.apellidos +", "+ this.nombre +" con DNI "+ this.DNI +" tiene los siguientes datos de contacto: \nEmail: "+ this.mail +"\nTelefono: "+ this.telf;
    }
}
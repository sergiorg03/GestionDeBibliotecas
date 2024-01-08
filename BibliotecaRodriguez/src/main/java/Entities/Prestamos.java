package Entities;

import java.sql.Date;
import java.time.LocalDate;

public class Prestamos {

    private Libros libro_id;
    private Usuarios user_DNI;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private boolean devuelto;

    // Constructor por defecto
    public Prestamos() {
        this.libro_id = new Libros();
        this.user_DNI = new Usuarios();
        this.fechaPrestamo = Date.valueOf(LocalDate.now().toString());
        this.fechaDevolucion = Date.valueOf(LocalDate.now().plusDays(30).toString());
        this.devuelto = false;
    }

    // Constructor con parametros
    public Prestamos(Libros libros, Usuarios user, Date fechaPrestamo, Date fechaDevolucion, boolean devuelto) {
        this.libro_id = libros;
        this.user_DNI = user;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    // Métodos GET
    public Libros getId_libro() {
        return libro_id;
    }

    public Usuarios getDni_us() {
        return user_DNI;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean getDevuelto() {
        return this.devuelto;
    }

    // Métodos SET
    public void setLibro_id(Libros libros) {
        this.libro_id = libros;
    }

    public void setUser_DNI(Usuarios user) {
        this.user_DNI = user;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "El libro " + this.libro_id.getNombre() + " con id " + this.libro_id.getID()
                + " fue prestado al cliente " + this.user_DNI
                + " el "
                + this.fechaPrestamo + " y será devuelto el " + this.fechaDevolucion + ".\nLo ha devuelto: "
                + ((this.devuelto) ? "Si. " : "No. ");
    }
}
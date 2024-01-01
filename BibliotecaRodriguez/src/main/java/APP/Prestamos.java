package APP;

import java.sql.Date;
import java.time.LocalDate;
import oracle.sql.DATE;

public class Prestamos {

    private Libros libros;
    private Usuarios user;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    
    // Constructor por defecto
    public Prestamos(){
        this.libros = new Libros();
        this.user = new Usuarios();
        this.fechaPrestamo = Date.valueOf(LocalDate.now().toString());
        this.fechaDevolucion = Date.valueOf(LocalDate.now().plusDays(30).toString());
    }

    // Constructor con parametros
    public Prestamos(Libros libros, Usuarios user, Date fechaPrestamo, Date fechaDevolucion) {
        this.libros = libros;
        this.user = user;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Métodos GET
    public Libros getId_libro() {
        return libros;
    }

    public Usuarios getDni_us() {
        return user;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    // Métodos SET
    public void setLibros(Libros libros) {
        this.libros = libros;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "El libro "+ this.libros.getNombre() +" fue prestado al cliente "+ this.user.getNombre() +" el "+ this.fechaPrestamo +" y será devuelto el "+ this.fechaDevolucion;
    }
}
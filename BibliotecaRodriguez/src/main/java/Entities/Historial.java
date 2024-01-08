package Entities;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Historial {

    // Atributos de la clase
    private Usuarios user;
    private Map<Libros, Date> librosYFechas;

    public Historial() {
        this.user = new Usuarios();
        this.librosYFechas = new HashMap<>();
    }

    public Historial(Usuarios DNI, Map<Libros, Date> librosYFechas) {
        this.user = DNI;
        this.librosYFechas = librosYFechas;
    }

    // Métodos GET
    public Usuarios getUser() {
        return user;
    }

    public Map<Libros, Date> getLibroYfecha() {
        return librosYFechas;
    }

    // Métodos set
    public void setUser(Usuarios user) {
        this.user = user;
    }

    public void setLibroYfecha(Map<Libros, Date> id_libros) {
        this.librosYFechas = id_libros;
    }

    // Método toString
    @Override
    public String toString() {
        String cadena = "El usuario con DNI: " + this.user.getDNI() + " ha pedido prestado los siguientes libros: ";

        for (Map.Entry<Libros, Date> libroYfecha : librosYFechas.entrySet()) {
            cadena += "\nLibro: " + libroYfecha.getKey().getNombre() + " \nen la fecha "
                    + libroYfecha.getValue().toString();
        }
        return cadena;
    }
}
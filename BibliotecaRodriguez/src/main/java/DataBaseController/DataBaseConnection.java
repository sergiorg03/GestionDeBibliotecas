package DataBaseController;

import APP.DatosPrueba;
import Entities.Libros;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entities.*;
import Utilities.Functions;
import java.io.File;
import java.sql.CallableStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {

    // Atributos de la clase
    private final String CLASSFORNAME = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USER = "bibliotecaRodriguez";
    private final String PASSWORD = "bibliotecaRodriguez";
    private Connection con = null;
    Functions f = new Functions();

    public DataBaseConnection() {
        con = connection();
        try{
            con.setAutoCommit(false);
        }catch(SQLException e){
            f.mensajeColorido("ROJO", "Error al desactivar el autoguardado. ");
        }
    }

    public Connection connection() {

        try {
            Class.forName(CLASSFORNAME);

            con = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException | SQLException e) {
            //e.printStackTrace();
            con = null;
        }

        return con;
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println("No se logró cerrar la conexión. ");
        }
    }

    public boolean testConnection() {
        boolean exitosa = false;
        try {
            if (!con.isClosed() && con != null) {
                exitosa = true;
            }
        } catch (SQLException ex) {
            System.out.println("Se produjo un error al probar la conexion. ");
            //ex.printStackTrace();
        }
        return exitosa;
    }

    public void save() {
        try {
            con.commit();
            f.mensajeColorido("MORADO", "Guardado correctamente. ");
        } catch (SQLException ex) {
            ex.printStackTrace();
            f.mensajeColorido("ROJO", "Se produjo un error a la hora de guardar los datos. ");
        }
    }

    public void rollBack() {
        try {
            con.rollback();
            f.mensajeColorido("MORADO", "Vuelta a la version anterior correctamente. ");
        } catch (SQLException ex) {
            ex.printStackTrace();
            f.mensajeColorido("ROJO", "Se produjo un error al volver a la version anterior. ");
        }
    }

    public void setAutoCommit() {
        try{
            con.setAutoCommit(true);
        }catch(SQLException e){
            f.mensajeColorido("ROJO", "Error al activar el autoguardado. ");
        }
    }

    public void relanzarScript() {
        File script = new File("SCRIPT_v2.sql");
        List<String> ListaSentencias = f.readStatements(script);

        try (Statement s = con.createStatement()) {
            for (int i = 0; i < ListaSentencias.size(); i++) {
                s.execute(ListaSentencias.get(i).substring(0, ListaSentencias.get(i).length() - 1));
            }
            relanzarPLSQL();
            f.mensajeColorido("AZUL", "SCRIPT RELANZADO CORRECTAMENTE. ");
        } catch (SQLException e) {
            e.printStackTrace();
            //f.mensajeColorido("ROJO", "Se produjo un error a la hora de relanzar el script de la base de datos. ");
        }
        script = null;
    }

    public void relanzarPLSQL() throws SQLException {
        File script = new File("plsql.sql");
        List<String> ListaSentencias = f.readPLSQL(script);
        for (int i = 0; i < ListaSentencias.size(); i++) {
            System.out.println("Procedimiento " + ListaSentencias.get(i).trim().substring(0, (ListaSentencias.get(i).trim().length()-1)));
            Statement s = con.createStatement();
            s.execute(ListaSentencias.get(i).trim().substring(0, (ListaSentencias.get(i).trim().length()-1)));
            s.close();
        }
        script = null;
    }

    /*public void createDatabase() {

        // TABLAS Y RESTRICCIONES
        final String[] sql = new String[]{
            // TABLAS
            // Tabla Usuarios
            "CREATE TABLE usuarios(DNI VARCHAR2(9), nombre VARCHAR2(255), apellidos VARCHAR2(255), mail VARCHAR2(255), telf VARCHAR2(9), CONSTRAINT pk_usuario PRIMARY KEY (DNI))",
            // Tabla libros
            "CREATE TABLE libros(id NUMBER(9), nombre VARCHAR2(255), descripcion VARCHAR2(255), autor VARCHAR2(255), fechaPublicacion DATE, disponible char(1), CONSTRAINT pk_libros PRIMARY KEY(id))",
            // Tabla prestamos
            "CREATE TABLE prestamos(libro_p NUMBER(9), usuario_p VARCHAR2(9), fecha_prestamo DATE, fechaDevolucion DATE, CONSTRAINT pk_prestamos PRIMARY KEY(libro_p, usuario_p), CONSTRAINT fk_prestamos_libros FOREIGN KEY (libro_p) REFERENCES libros(id), CONSTRAINT fk_prestamos_usuarios FOREIGN KEY (usuario_p) REFERENCES usuarios(DNI) ON DELETE CASCADE)",
            // Tabla Historial
            "CREATE TABLE historial( id_libro NUMBER(9), dni_us VARCHAR2(9), fecha_prestamo DATE, CONSTRAINT pk_historia PRIMARY KEY (fecha_prestamo, id_libro, dni_us), CONSTRAINT fk_historial_libros FOREIGN KEY (id_libro) REFERENCES libros(id), CONSTRAINT fk_historial_usuarios FOREIGN KEY (dni_us) REFERENCES usuarios(dni))",
            // Check tabla libros
            //"ALTER TABLE libros ADD CONSTRAINT ",
            // SECUENCIA tabla libros
            "CREATE SEQUENCE seq_id_libro INCREMENT BY 1 START WITH 1 MAXVALUE 999999999 NOCYCLE",};

        try (Statement s = con.createStatement()) {

            con.setAutoCommit(false);
            con.commit();
            for (int i = 0; i < sql.length; i++) {
                s.execute(sql[i]); //Numero de líneas afectadas
            }

            //PreparedStatement pstmt = con.prepareStatement("Orden insert (?,?)");
            //pstmt.setString(1, "lo que queramos introducir en el insert");
            //pstmt.setString(2, "lo que queramos introducir en el insert");
            f.mensajeColorido("morado", "Base de datos creada correctamente. ");

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException sqle) {
                f.mensajeColorido("ROJO", "Se produjo un error al volver a la version anterior. ");
            }

            e.printStackTrace();
            f.mensajeColorido("ROJO", "La base de datos ya fue creada con anterioridad. ");
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception e) {
                f.mensajeColorido("ROJO", "Error al activar el autoguardado. ");
            }
        }
    }
    
    public void borradoTablas() {

        final String[] drop_tables = new String[]{
            // Tabla prestamos
            "DROP TABLE prestamos CASCADE CONSTRAINTS",
            // Tabla libros
            "DROP TABLE libros CASCADE CONSTRAINTS",
            // Tabla usuarios
            "DROP TABLE usuarios CASCADE CONSTRAINTS",
            // Tabla historial
            "DROP TABLE historial CASCADE CONSTRAINTS",
            // Secuencia id libros
            "DROP SEQUENCE seq_id_libro",
        };

        try (Statement s = con.createStatement();) {
            con.setAutoCommit(false);
            con.commit();
            for (int i = 0; i < drop_tables.length; i++) {
                s.execute(drop_tables[i]);
            }

        } catch (SQLException e) {
            f.mensajeColorido("ROJO", "Error a la hora del borrado de tablas. ");
            try {
                //e.printStackTrace();
                con.rollback();
            } catch (SQLException ex) {
                f.mensajeColorido("ROJO", "Error al volver a la version anterior. ");
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                f.mensajeColorido("ROJO", "Error a la hora de activar el autoguardado. ");
            }
        }
    }*/
    public void registrarUsuario(String DNI, String nombre, String apellidos, String mail, String telf) {

        final String sql = "INSERT INTO usuarios (DNI, nombre, apellidos, mail, telf) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, DNI);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidos);
            pstmt.setString(4, mail);
            pstmt.setString(5, telf);

            pstmt.executeUpdate();

            f.mensajeColorido("azul", "Se añadió al nuevo usuario correctamente. ");
        } catch (SQLException e) {
            f.mensajeColorido("morado", "Error en la insercion del nuevo usuario. ");
        }
    }

    public List<Usuarios> mostrarUsuarios(String dni) {

        final String sql = "SELECT dni, nombre, apellidos, mail, telf FROM usuarios";
        List<Usuarios> listUsers = new ArrayList<Usuarios>();

        if (dni == "" || dni == null) {

            try (Statement s = con.createStatement()) {

                ResultSet resultado = s.executeQuery(sql);

                while (resultado.next()) {

                    Usuarios u = new Usuarios(resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5));
                    listUsers.add(u);

                }

                resultado.close();
            } catch (SQLException e) {
                f.mensajeColorido("ROJO", "No se pudieron obtener datos. ");
                //e.printStackTrace();
            }
        } else {
            String sql_dni = sql.concat(" WHERE UPPER(DNI) = UPPER(?)");

            try (PreparedStatement pstmt = con.prepareStatement(sql_dni)) {

                //Asignar variable
                pstmt.setString(1, dni);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    Usuarios u = new Usuarios(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5));
                    listUsers.add(u);
                }

            } catch (SQLException e) {
                //e.printStackTrace();
                f.mensajeColorido("ROJO", "No se pudo obtener al usuario indicado. ");
            }

        }

        return listUsers;
    }

    public void eliminarUser(String dni) {

        final String sql = "DELETE FROM usuarios WHERE UPPER(DNI) = UPPER(?)";

        if (dni.isBlank()) {

        } else {
            List<Usuarios> u = mostrarUsuarios(dni);

            if (!u.isEmpty()) {
                try (PreparedStatement pstmt = con.prepareStatement(sql);) {

                    pstmt.setString(1, dni);

                    pstmt.executeUpdate();

                    f.mensajeColorido("azul", "Se borro al usuario correctamente. ");

                } catch (SQLException sqle) {
                    f.mensajeColorido("rojo", "Se produjo un error a la hora de borrar el usuario. ");
                }
            } else {
                f.mensajeColorido("rojo", "El DNI introducido no se corresponde con ningun usuario. ");
            }
        }
    }

    public Usuarios getUser(String dni) {
        Usuarios u = null;
        if (dni != null && dni != "") {

            final String SQL = "SELECT dni, nombre, apellidos, mail, telf FROM usuarios WHERE UPPER(DNI) = UPPER(?)";

            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {

                pstmt.setString(1, dni);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    u = new Usuarios();
                    u.setDNI(r.getString(1));
                    u.setNombre(r.getString(2));
                    u.setApellidos(r.getString(3));
                    u.setMail(r.getString(4));
                    u.setTelf(r.getString(5));
                }
            } catch (SQLException e) {
                f.mensajeColorido("ROJO", "Se produjo un error a la hora de obtener los datos del usuario indicado. ");
            }

        } else;
        return u;
    }

    public void updateUser(Usuarios u, String dni) {
        final String SQL = "UPDATE usuarios SET DNI = ?, nombre = ?, apellidos = ?, mail = ?, telf = ? WHERE UPPER(DNI) = UPPER(?)";

        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, u.getDNI());
            pstmt.setString(2, u.getNombre());
            pstmt.setString(3, u.getApellidos());
            pstmt.setString(4, u.getMail());
            pstmt.setString(5, u.getTelf());
            pstmt.setString(6, dni);

            pstmt.executeUpdate();
            f.mensajeColorido("MORADO", "Usuario actualizado correctamente. ");
        } catch (SQLException sqle) {
            f.mensajeColorido("ROJO", "Se produjo un error a la hora de actualizar los datos del usuario. ");
        }
    }

    public void addBook(String nombre_libro, String autor, String desc, String fechaPublicacion, String disponible) {
        String sql = "INSERT INTO libros (id, nombre, autor, descripcion, fechaPublicacion, disponible) VALUES (seq_id_libro.NEXTVAL,?,?,?, TO_DATE(?, 'DD-MM-YYYY'), ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, nombre_libro);
            pstmt.setString(2, autor);
            pstmt.setString(3, desc);
            pstmt.setString(4, fechaPublicacion);
            pstmt.setString(5, disponible);

            pstmt.executeQuery();

            f.mensajeColorido("azul", "Se añadio el libro correctamente. ");

        } catch (SQLException e) {
            f.mensajeColorido("rojo", "Se produjo un error a la hora de insertar el libro en la base de datos. ");
        }
    }

    public List<Libros> getAllBooks() {
        final String SQL = "SELECT id, nombre, descripcion, autor, fechapublicacion, disponible FROM libros";
        List<Libros> l = new ArrayList<>();

        try (Statement s = con.createStatement()) {
            ResultSet r = s.executeQuery(SQL);

            while (r.next()) {
                Libros libro = new Libros(
                        r.getInt(1),
                        r.getString(2),
                        r.getString(3),
                        r.getString(4),
                        r.getDate(5),
                        r.getBoolean(6)
                );
                l.add(libro);
            }
        } catch (SQLException e) {
            f.mensajeColorido("ROJO", "Se produjo un error a la hora de obtener los libros. ");
        }

        return l;
    }

    public List<Libros> getBookByField(String campo, String valor) {
        List<Libros> libros = new ArrayList<>();
        final String SQL = "SELECT id, nombre, descripcion, autor, fechapublicacion, disponible FROM libros WHERE UPPER(" + campo + ") LIKE UPPER(?)";
        if (campo != null && campo != "" && valor != null && valor != "") {

            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, "%" + valor + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    Libros libro = new Libros(
                            r.getInt(1),
                            r.getString(2),
                            r.getString(3),
                            r.getString(4),
                            r.getDate(5),
                            r.getBoolean(6)
                    );
                    libros.add(libro);
                }
            } catch (SQLException e) {
                f.mensajeColorido("ROJO", "Se produjo un error a la hora de buscar un libro. ");
                e.printStackTrace();
            }
        }
        return libros;
    }

    public void addTestData(String[] query_us, String[] query_lib) {
        final String SQL_US = "insert into usuarios (DNI, nombre, apellidos, mail, telf) values ";
        final String SQL_LIB = "insert into libros (id, nombre, descripcion, autor, fechaPublicacion, disponible) values ";
        for (int i = 0; i < query_us.length; i++) {
            try (PreparedStatement pstmt = con.prepareStatement(SQL_US.concat(query_us[i]))) {
                String dni = DatosPrueba.generarDNI();
                String telf = DatosPrueba.generarTelf();
                pstmt.setString(1, dni);
                pstmt.setString(2, telf);
                pstmt.executeUpdate();

                f.mensajeColorido("MORADO_INTENSO", "Datos de prueba añadidos correctamente. ");
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                f.mensajeColorido("ROJO", "Se produjo un error a la hora de insertar los datos de prueba. ");
            }
        }

        for (int i = 0; i < query_lib.length; i++) {
            try (PreparedStatement pstmt = con.prepareStatement(SQL_LIB.concat(query_lib[i]))) {
                pstmt.executeUpdate();

                f.mensajeColorido("MORADO_INTENSO", "Datos de prueba añadidos correctamente. ");
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                f.mensajeColorido("ROJO", "Se produjo un error a la hora de insertar los datos de prueba. ");
            }
        }
        try (CallableStatement cs = con.prepareCall("call p_insertardatosprueba_historial()")){
            cs.execute();
        } catch (Exception e) {
            e.printStackTrace();
            f.mensajeColorido("ROJO", "Se produjo un error al ejecutar el procedimiento de insercion de datos de prueba. ");
        }
    }

    public void updateBook(Libros libro) {
        final String SQL = "UPDATE libros SET nombre = ?, autor = ?, descripcion = ?, fechaPublicacion = TO_DATE(?, 'DD-MM-YYYY'), disponible = ? WHERE ID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
            pstmt.setString(1, libro.getNombre());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getDescripcion());
            pstmt.setDate(4, libro.getFechaPublicacion());
            pstmt.setString(5, libro.getDisponibilidad() ? "1" : "0");
            pstmt.setInt(6, libro.getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            f.mensajeColorido("ROJO", "Se produjo un error a la hora de actualizar la informacion del libro. ");
        }
    }
}

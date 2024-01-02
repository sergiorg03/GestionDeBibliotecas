package DataBaseController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import APP.Usuarios;
import Utilities.Functions;

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
        } finally {
            return exitosa;
        }
    }

    public void createDatabase() {

        // TABLAS Y RESTRICCIONES
        final String[] sql = new String[]{
            // TABLAS
            // Tabla Usuarios
            "CREATE TABLE usuarios(DNI VARCHAR2(9), nombre VARCHAR2(255), apellidos VARCHAR2(255), mail VARCHAR2(255), telf VARCHAR2(9), CONSTRAINT pk_usuario PRIMARY KEY (DNI))",
            // Tabla libros
            "CREATE TABLE libros(id NUMBER(9), nombre VARCHAR2(255), descripcion VARCHAR2(255), autor VARCHAR2(255), telf VARCHAR2(9), CONSTRAINT pk_libros PRIMARY KEY(id))",
            // Tabla prestamos
            "CREATE TABLE prestamos(libro_p NUMBER(9), usuario_p VARCHAR2(9), fecha_prestamo DATE, fechaDevolucion DATE, CONSTRAINT pk_prestamos PRIMARY KEY(libro_p, usuario_p), CONSTRAINT fk_prestamos_libros FOREIGN KEY (libro_p) REFERENCES libros(id), CONSTRAINT fk_prestamos_usuarios FOREIGN KEY (usuario_p) REFERENCES usuarios(DNI))",
            // Tabla Historial
            //"",
        };

        try {

            Statement s = null;
            for (int i = 0; i < sql.length; i++) {

                s = con.createStatement();
                
                s.execute(sql[i]); //Numero de líneas afectadas
            }

            //PreparedStatement pstmt = con.prepareStatement("Orden insert (?,?)");
            //pstmt.setString(1, "lo que queramos introducir en el insert");
            //pstmt.setString(2, "lo que queramos introducir en el insert");
            s.close();

            System.out.println(f.COLORES.get("MORADO")+"Base de datos creada correctamente. "+f.COLORES.get("RESET"));

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(f.COLORES.get("ROJO")+"La base de datos ya fue creada con anterioridad. "+f.COLORES.get("RESET".toUpperCase()));
        }
    }

    public void borradoTablas(){
    
        final String [] drop_tables = new String[]
        {
            // Tabla prestamos
            "DROP TABLE prestamos CASCADE CONSTRAINTS",
            // Tabla libros
            "DROP TABLE libros CASCADE CONSTRAINTS",
            // Tabla usuarios
            "DROP TABLE usuarios CASCADE CONSTRAINTS",
            // Tabla historial
            //"DROP TABLE historial CASCADE CONSTRAINTS",
        };

        Statement s = null;

        try {

            for (int i = 0; i < drop_tables.length; i++) {
                s = con.createStatement();

                s.execute(drop_tables[i]);
            }

        } catch (SQLException e) {
            System.out.println(f.COLORES.get("morado".toUpperCase())+"Error a la hora del borrado de tablas. "+f.COLORES.get("RESET".toUpperCase()));
            e.printStackTrace();
        }finally{
            try {
                s.close();
            } catch (SQLException e) {
                System.out.println(f.COLORES.get("morado".toUpperCase())+"Error en el cerrado de la instancia Statement. "+f.COLORES.get("RESET".toUpperCase()));
                e.printStackTrace();
            }
        }
    }

    public void registrarUsuario(String DNI, String nombre, String apellidos, String mail, String telf){

        final String sql = "INSERT INTO usuarios (DNI, nombre, apellidos, mail, telf) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, DNI);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidos);
            pstmt.setString(4, mail);
            pstmt.setString(5, telf);

            int lineasAfectadas = pstmt.executeUpdate();

            System.out.println(f.COLORES.get("azul".toUpperCase())+"Se añadió al nuevo usuario correctamente. "+f.COLORES.get("RESET".toUpperCase()));
        } catch (SQLException e) {
            System.out.println(f.COLORES.get("morado".toUpperCase())+"Error en la insercion del nuevo usuario. "+f.COLORES.get("RESET".toUpperCase()));
        }
    }

    public List<Usuarios> mostrarUsuarios(String dni){

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
                System.out.println();
                e.printStackTrace();
            }
        }else{
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
                e.printStackTrace();
                System.out.println();
            }
            
        }
        
        return listUsers;
    }

    public void eliminarUser(String dni) {
        
        final String sql = "DELETE FROM usuarios WHERE UPPER(DNI) = UPPER(?)";
        
        if (dni.isBlank()) {
            
        }else{
            List<Usuarios> u = mostrarUsuarios(dni);
            
            if (!u.isEmpty()) {
                try(PreparedStatement pstmt = con.prepareStatement(sql);){
                    
                    pstmt.setString(1, dni);
                    
                    pstmt.executeUpdate();
                    
                    System.out.println(f.COLORES.get("AZUL".toUpperCase())+"Se borro al usuario correctamente. "+f.COLORES.get("RESET".toUpperCase()));
                    
                }catch(SQLException sqle){
                    System.out.println(f.COLORES.get("ROJO".toUpperCase())+"Se produjo un error a la hora de borrar el usuario. "+f.COLORES.get("RESET".toUpperCase()));
                }
            }else{
                System.out.println(f.COLORES.get("ROJO".toUpperCase())+"El DNI introducido no se corresponde con ningun usuario. "+f.COLORES.get("RESET".toUpperCase()));
            }
        }
    }
}
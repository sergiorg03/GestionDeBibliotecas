package DataBaseController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {

    // Atributos de la clase
    private final String CLASSFORNAME = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USER = "bibliotecaRodriguez";
    private final String PASSWORD = "bibliotecaRodriguez";
    private Connection con = null;

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

    public void crearDatabase() {

        // Tabla usuarios
        String[] sql = new String[]{
            // Tabla Usuarios
            "CREATE TABLE usuarios(DNI VARCHAR2(9), nombre VARCHAR2(255), apellidos VARCHAR2(255), mail VARCHAR2(255), telf VARCHAR2(9), CONSTRAINT pk_usuarios PRIMARY KEY (DNI));",
            // Tabla libros
            "CREATE TABLE libros(id NUMBER(9), nombre VARCHAR2(255), descripcion VARCHAR2(255), autor VARCHAR2(255), telf VARCHAR2(9), CONSTRAINT pk_libros PRIMARY KEY(id));",
            // Tabla prestamos
            "CREATE TABLE prestamos(libro_p NUMBER(9), usuario_p VARCHAR2(9), fecha_prestamo DATE, fechaDevolucion DATE, CONSTRAINT pk_prestamos PRIMARY KEY(libro_p, usuario_p), CONSTRAINT fk_prestamos_libros FOREIGN KEY (libro_p) REFERENCES libros(id), CONSTRAINT fk_prestamos_usuarios FOREIGN KEY (usuario_p) REFERENCES usuarios(DNI));",
        };

        try {

            PreparedStatement pstmt = null;
            for (int i = 0; i < sql.length; i++) {

                pstmt = con.prepareStatement(sql[i]);
                
                int rowsInserted = pstmt.executeUpdate(); //Numero de líneas afectadas

                System.out.println("Número de lineas afectadas: " + rowsInserted);
            }

            //PreparedStatement pstmt = con.prepareStatement("Orden insert (?,?)");
            //pstmt.setString(1, "lo que queramos introducir en el insert");
            //pstmt.setString(2, "lo que queramos introducir en el insert");
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

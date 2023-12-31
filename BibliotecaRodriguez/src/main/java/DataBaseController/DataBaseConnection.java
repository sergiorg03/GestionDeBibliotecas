package DataBaseController;

import java.sql.Connection;
import java.sql.DriverManager;
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
    
    public DataBaseConnection(){
        con = connection();
    }
    
    public Connection connection(){
        
        try {
            Class.forName(CLASSFORNAME);
            
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return con;
    }
    
    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println("No se logró cerrar la conexión. ");
        }
    }
    
    public boolean testConnection(){
        boolean exitosa = false;
        try {    
            if(!con.isClosed() && con != null){
                exitosa = true;
            }
        } catch (SQLException ex) {
            System.out.println("Se produjo un error al probar la conexion. ");
            //ex.printStackTrace();
        }finally{
            return exitosa;
        }
    }
}

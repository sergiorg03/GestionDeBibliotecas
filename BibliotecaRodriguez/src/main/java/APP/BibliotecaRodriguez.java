package APP;

import DataBaseController.DataBaseConnection;
import Utilities.Functions;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author sergi
 */
public class BibliotecaRodriguez {
    
    static Scanner scan;
    static Functions f = new Functions();
    
    public static void main(String[] args) {
    
        DataBaseConnection con = new DataBaseConnection();
        scan = new Scanner(System.in);
        
        int opcion = -1;

        do {
            
            System.out.println();
            System.out.println("**********************************************************************");
            System.out.println("1. Registrar un nuevo usuario.                                       *");
            System.out.println("2. Mostrar un usuario.                                               *");
            System.out.println("3. Mostrar todos los usuarios.                                       *");
            System.out.println("4. Eliminar un usuario.                                              *");
            System.out.println("9. Realizar un test de conexion.                                     *");
            System.out.println("10. Crear base de datos.                                             *");
            System.out.println("11. Reiniciar base de datos.                                         *");
            System.out.println("12. Limpiar terminal.                                                *");
            System.out.println("0. Salir del programa.                                               *");
            System.out.println("**********************************************************************");
            System.out.println();
            
            String op = scan.nextLine();
            opcion = (f.esNumerico(op )? Integer.parseInt(op): -1);
            
            switch (opcion) {
                case 1:
                    System.out.println("introduzca el DNI del usuario: ");
                    String DNI = scan.nextLine();

                    System.out.println("introduzca el nombre del usuario: ");
                    String nombre = scan.nextLine();

                    System.out.println("introduzca los apellidos del usuario: ");
                    String apellidos = scan.nextLine();

                    System.out.println("introduzca el Email del usuario: ");
                    String mail = scan.nextLine(); 

                    System.out.println("introduzca el número de telefono del usuario: ");
                    String telf = scan.nextLine();

                    registrarUsuario(con, DNI, nombre, apellidos, mail, telf);
                    break;
                
                case 2:
                    System.out.println("Introduzca el DNI de la persona a buscar: ");
                    String dni_mostrarPersona = scan.nextLine();
                    mostrarUsuario(con, dni_mostrarPersona);
                    break;
                
                case 3: 
                    showAllUsers(con);
                    break;

                case 4:
                    showAllUsers(con);
                    System.out.println("Introduzca el DNI del usuario a borrar: ");
                    String del_dni = scan.nextLine();
                    deleteUser(con, del_dni);
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 9:
                    testConnection(con);
                    break;
                case 10:
                    createDataBase(con);
                    break;
                case 11:
                    resetDataBase(con);
                    break;
                case 12:
                    cleanScreen();
                    break;
                case 0:
                    con.closeConnection();
                    System.out.println("Adios! ");
                    break;
                default:
                    System.out.println("Debe introducir una opcion valida. ");
                    break;
            }
        } while (opcion!=0);
    }

    private static void deleteUser(DataBaseConnection con, String dni) {
        con.eliminarUser(dni);
    }

    private static void showAllUsers(DataBaseConnection con) {
        List<Usuarios> listaUs = con.mostrarUsuarios(null);
        
        for (int i = 0; i < listaUs.size(); i++) {
            System.out.println("");
            System.out.println("Usuario "+i);
            System.out.println("*****************************************************");
            System.out.println(f.COLORES.get("CIAN".toUpperCase())+listaUs.get(i).toString()+f.COLORES.get("RESET".toUpperCase()));
            System.out.println("*****************************************************");
            System.out.println("");
        }
    }

    protected static void registrarUsuario(DataBaseConnection con, String DNI, String nombre, String apellidos, String mail, String telf){
        con.registrarUsuario(DNI, nombre, apellidos, mail, telf);
    }
    
    protected static void testConnection(DataBaseConnection con){
        if (con.testConnection()){
            System.out.println(f.COLORES.get("VERDE")+"El test de conexion fue exitoso. "+f.COLORES.get("RESET"));
        }else System.out.println(f.COLORES.get("ROJO")+"Se produjo un error al probar la conexion. "+f.COLORES.get("RESET"));
    }
    
    protected static void resetDataBase(DataBaseConnection con){
        con.borradoTablas();
        con.createDatabase();
    }

    protected static void createDataBase(DataBaseConnection con){
        con.createDatabase();
    }

    protected static void mostrarUsuario(DataBaseConnection con, String dni){
        List<Usuarios> listUsers = con.mostrarUsuarios(dni);
        for (int i = 0; i < listUsers.size(); i++) {
            System.out.println("Usuario "+i);
            System.out.println("*****************************************************");
            System.out.println(f.COLORES.get("CIAN".toUpperCase())+listUsers.get(i).toString()+f.COLORES.get("RESET".toUpperCase()));
            System.out.println("*****************************************************");
        }
    }
    
    public static void cleanScreen(){
        
        String os = System.getProperty("os.name").toLowerCase();
        String comando = "";
        
        if (os.contains("win")) {
            comando = "cls";
        }else{
            comando = "clear";
        }
        
        try {
            new ProcessBuilder(comando).inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(f.COLORES.get("ROJO".toUpperCase())+"Se produjjo un error a la hora de limpiar la pantalla. "+f.COLORES.get("RESET".toUpperCase()));
        }
    }
}
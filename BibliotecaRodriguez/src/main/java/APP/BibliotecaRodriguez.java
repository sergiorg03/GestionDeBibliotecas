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
            System.out.println("5. Modificar un usuario.                                             *");
            System.out.println("6. Insertar un nuevo libro.                                          *");
            System.out.println("9. Realizar un test de conexion.                                     *");
            System.out.println("10. Crear base de datos.                                             *");
            System.out.println("11. Reiniciar base de datos.                                         *");
            System.out.println("0. Salir del programa.                                               *");
            System.out.println("**********************************************************************");
            System.out.println();
            
            String op = scan.nextLine();
            opcion = (f.esNumerico(op )? Integer.parseInt(op): -1);
            
            switch (opcion) {
                case 1:
                    System.out.println("introduzca el DNI del usuario: ");
                    String DNI = scan.nextLine().toUpperCase();

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
                    showAllUsers(con);
                    System.out.println("Introduzca el DNI del usuario a modificar. ");
                    String dni_modif = scan.nextLine();
                    updateUser(con, dni_modif);
                    break;
                case 6:
                    System.out.println("Introduzca el nombre del libro a introducir: ");
                    String nombre_libro = scan.nextLine();
                    
                    System.out.println("Introduzca el nombre del autor del libro: ");
                    String autor = scan.nextLine();
                    
                    System.out.println("Introduzca una breve descripcion del libro: ");
                    String desc = scan.nextLine();
                    
                    addBook(con, nombre_libro, autor, desc);
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
                case 0:
                    closeConnection(con);
                    System.out.println("Adios! ");
                    break;
                default:
                f.mensajeColorido("ROJO","Debe introducir una opcion valida. ");
                    break;
            }
        } while (opcion!=0);
    }
    
    public static void closeConnection(DataBaseConnection con){
        con.closeConnection();
    }

    public static void updateUser(DataBaseConnection con, String dni){
        scan = new Scanner(System.in);

        Usuarios u = con.getUser(dni);
        if (u != null){
            int opcion = -1;

            do {
                System.out.println();
                System.out.println("**********************************************************************");
                System.out.println("1. Modificar el DNI.                                                 *");
                System.out.println("2. Modificar el nombre.                                              *");
                System.out.println("3. Moodificar los apellidos.                                         *");
                System.out.println("4. Modificar el Email.                                               *");
                System.out.println("5. Modificar el número de telefono.                                  *");
                System.out.println("0. Salir del modo editor de usuario.                                 *");
                System.out.println("**********************************************************************");
                System.out.println();
                String op = scan.nextLine();
                opcion = f.esNumerico(op)? Integer.parseInt(op): -1;

                switch (opcion) {
                    case 1:
                        f.mensajeColorido("CIAN", "Introduzca el nuevo DNI: ");
                        String nuevo_DNI = scan.nextLine();
                        while (!f.comprobarDNI(nuevo_DNI)) {
                            f.mensajeColorido("ROJO", "El DNI debe tener un formato correcto. Vuelva a introducir el DNI: ");
                            nuevo_DNI = scan.nextLine();
                        }
                        u.setDNI(nuevo_DNI);
                        break;

                    case 2:
                        f.mensajeColorido("CIAN", "Introduzca el nuevo nombre del usuario: ");
                        String nuevoNombre = scan.nextLine();
                        u.setNombre(nuevoNombre);
                        break;

                    case 3:
                        f.mensajeColorido("CIAN", "Introduzca los nuevos apellidos del usuario: ");
                        String nuevoApe = scan.nextLine();
                        u.setApellidos(nuevoApe);
                        break;

                    case 4:
                        f.mensajeColorido("CIAN", "Introduzca el nuevo email del usuario: ");
                        String nuevoEmail = scan.nextLine();
                        while (!f.formatoEmail(nuevoEmail)) {
                            f.mensajeColorido("ROJO", "El Email debe tener un formato correcto. Vuelva a introducir el Email: ");
                            nuevoEmail = scan.nextLine();
                        }
                        u.setMail(nuevoEmail);
                        break;
                    case 5:
                        f.mensajeColorido("CIAN", "Introduzca el nuevo telefono del usuario: ");
                        String nuevoTelf = scan.nextLine();
                        while (nuevoTelf.length() != 9 || !f.esNumerico(nuevoTelf)) {
                            f.mensajeColorido("ROJO", "El número de telefono debe tener un formato correcto. Vuelva a introducir el número de telefono: ");
                            nuevoTelf = scan.nextLine();
                        }
                        u.setTelf(nuevoTelf);
                        break;

                    default:
                        f.mensajeColorido("MORADO", "Debe introducir una opción valida. ");
                        break;
                }
            } while (opcion != 0);

            con.updateUser(u, dni);
        }else f.mensajeColorido("ROJO", "No se pudo obtener el usuario indicado. ");
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
            f.mensajeColorido("CIAN",listaUs.get(i).toString());
            System.out.println("*****************************************************");
            System.out.println("");
        }
    }

    protected static void registrarUsuario(DataBaseConnection con, String DNI, String nombre, String apellidos, String mail, String telf){
        con.registrarUsuario(DNI, nombre, apellidos, mail, telf);
    }
    
    protected static void testConnection(DataBaseConnection con){
        if (con.testConnection()){
            f.mensajeColorido("VERDE","El test de conexion fue exitoso. ");
        }else f.mensajeColorido("ROJO","Se produjo un error al probar la conexion. ");
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
            f.mensajeColorido("CIAN",listUsers.get(i).toString());
            System.out.println("*****************************************************");
        }
    }

    private static void addBook(DataBaseConnection con, String nombre_libro, String autor, String desc) {
        con.addBook(nombre_libro, autor, desc);
    }
}
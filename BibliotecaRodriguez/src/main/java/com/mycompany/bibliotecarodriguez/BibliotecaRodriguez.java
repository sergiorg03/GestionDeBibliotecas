package com.mycompany.bibliotecarodriguez;

import DataBaseController.DataBaseConnection;
import java.sql.Connection;
import java.util.Scanner;

/**
 *
 * @author sergi
 */
public class BibliotecaRodriguez {
    
    static Scanner scan;
    
    public static void main(String[] args) {
    
        DataBaseConnection con = new DataBaseConnection();
        scan = new Scanner(System.in);
        
        int opcion = -1;

        do {
            
            System.out.println();
            System.out.println("**********************************************************************");
            System.out.println("1. Registrar un nuevo usuario.                                       *");
            System.out.println("2. Modificar un usuario.                                             *");
            System.out.println("3. Eliminar un usuario.                                              *");
            System.out.println("4.                                                                   *");
            System.out.println("9. Realizar un test de conexion.                                     *");
            System.out.println("0. Salir del programa.                                               *");
            System.out.println("**********************************************************************");
            System.out.println();
            
            opcion = scan.nextInt();
            scan.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("introduzca su DNI:");
                    String DNI = scan.nextLine();

                    System.out.println("introduzca su nombre:");
                    String nombre = scan.nextLine();

                    System.out.println("introduzca su DNI:");
                    String apellidos = scan.nextLine();

                    System.out.println("introduzca su DNI:");
                    String mail = scan.nextLine(); 

                    System.out.println("introduzca su DNI:");
                    String telf = scan.nextLine();

                    registrarUsuario(DNI, nombre, apellidos, mail, telf);
                    break;
                
                case 2:
                    //modificarUs();
                    break;
                
                case 3: 
                    //eliminarUsuario();
                    break;

                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 9:
                    testConnection(con);
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

    protected static void registrarUsuario(String DNI, String nombre, String apellidos, String mail, String telf){

    }
    
    protected static void testConnection(DataBaseConnection con){
        if (con.testConnection()){
            System.out.println("El test de conexion fue exitoso. ");
        }else System.out.println("Se produjo un error al probar la conexion. ");
    }
    
    
}
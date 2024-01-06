package APP;

import Entities.Usuarios;
import Entities.Libros;
import DataBaseController.DataBaseConnection;
import Utilities.Functions;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
            System.out.println("7. Mostrar todos los libros.                                         *");
            System.out.println("8. Buscar libro.                                                     *");
            System.out.println("9. Modificar un libro.                                               *");
            System.out.println("80. Guardar datos.                                                   *");
            System.out.println("81. Volver a la version anterior.                                    *");
            System.out.println("82. Activar el autoguardado.                                         *");
            System.out.println("90. Realizar un test de conexion.                                    *");
            System.out.println("100. Relanzar SCRIPT DE LA BASE DE DATOS.                            *");
            System.out.println("110. Insertar datos de prueba.                                       *");
            System.out.println("0. Salir del programa.                                               *");
            System.out.println("**********************************************************************");
            System.out.println();

            String op = scan.nextLine();
            opcion = (f.esNumerico(op) ? Integer.parseInt(op) : -1);

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

                    System.out.println("Introduzca la fecha de publicacion del libro (FORMATO DE LA FECHA DIA-MES-AÑO (xx-xx-xxxx)): ");
                    String fecha = scan.nextLine();

                    System.out.println("Introduzca si está disponible o no el libro en la biblioteca: ");
                    String dispo = scan.nextLine().substring(0, 1);

                    addBook(con, nombre_libro, autor, desc, fecha, dispo);
                    break;
                case 7:
                    getBooks(con);
                    break;
                case 8:
                    List<String> listaCampos = new ArrayList<>();
                     {
                        {
                            listaCampos.add("NOMBRE");
                            listaCampos.add("AUTOR");
                            listaCampos.add("DESCRIPCION");
                            listaCampos.add("Fecha de publicacion".toUpperCase());
                        }
                    }
                    ;

                    String opcionesCampos = "";
                    String campo = "";
                    do {
                        f.mensajeColorido("MORADO", "CAMPOS POR LOS QUE BUSCAR UN LIBRO: ");
                        f.mensajeColorido("VERDE", "\tNOMBRE");
                        f.mensajeColorido("VERDE", "\tAUTOR");
                        f.mensajeColorido("VERDE", "\tDESCRIPCION");
                        f.mensajeColorido("VERDE", "\tFecha de publicacion " + f.COLORES.get("ROJO") + "(FORMATO DIA-MES-AÑO xx-xx-xxxx)");
                        f.mensajeColorido("MORADO_INTENSO", "Introduzca el campo por el que desee buscar: ");
                        opcionesCampos = scan.nextLine();
                        switch (opcionesCampos) {
                            case "NOMBRE":
                                campo = opcionesCampos.toUpperCase();
                                break;
                            case "AUTOR":
                                campo = opcionesCampos.toUpperCase();
                                break;
                            case "DESCIPCION":
                                campo = opcionesCampos.toUpperCase();
                                break;
                            case "FECHA DE PUBLICACION":
                                campo = opcionesCampos.toUpperCase();
                                break;
                            default:
                                f.mensajeColorido("rojo", "Debe introducir un campo valido. ");
                                break;
                        }
                    } while (!listaCampos.contains(opcionesCampos));

                    f.mensajeColorido("MORADO_INTENSO", "Introduzca el valor a buscar: ");
                    String valor = scan.nextLine();

                    searchBook(con, campo.toUpperCase(), valor.toUpperCase());
                    break;
                case 9:
                    showBooksWithID(con);
                    f.mensajeColorido("CIAN", "Indique el id del libro a modificar. ");
                    String id = scan.nextLine();
                    updateBook(con, id);
                    break;
                case 80:
                    save(con);
                    break;
                case 81:
                    rollBack(con);
                    break;
                case 82:
                    setAutoCommit(con);
                    break;                    
                case 90:
                    testConnection(con);
                    break;
                case 100:
                    resetDataBase(con);
                    break;
                case 110:
                    DatosPrueba.insertTestData(con);
                    break;
                case 0:
                    closeConnection(con);
                    System.out.println("Adios! ");
                    break;
                default:
                    f.mensajeColorido("ROJO", "Debe introducir una opcion valida. ");
                    break;
            }
        } while (opcion != 0);
    }

    private static void closeConnection(DataBaseConnection con) {
        con.closeConnection();
    }
    
    private static void save(DataBaseConnection con) {
        con.save();
    }

    private static void rollBack(DataBaseConnection con) {
        con.rollBack();
    }
    
    private static void setAutoCommit(DataBaseConnection con){
        con.setAutoCommit();
    }

    private static void updateUser(DataBaseConnection con, String dni) {
        scan = new Scanner(System.in);

        Usuarios u = con.getUser(dni);
        if (u != null) {
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
                opcion = f.esNumerico(op) ? Integer.parseInt(op) : -1;

                switch (opcion) {
                    case 1:
                        f.mensajeColorido("CIAN", "Introduzca el nuevo DNI: ");
                        String nuevo_DNI = scan.nextLine();
                        while (!f.comprobarDNI(nuevo_DNI)) {
                            f.mensajeColorido("ROJO",
                                    "El DNI debe tener un formato correcto. Vuelva a introducir el DNI: ");
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
                            f.mensajeColorido("ROJO",
                                    "El Email debe tener un formato correcto. Vuelva a introducir el Email: ");
                            nuevoEmail = scan.nextLine();
                        }
                        u.setMail(nuevoEmail);
                        break;
                    case 5:
                        f.mensajeColorido("CIAN", "Introduzca el nuevo telefono del usuario: ");
                        String nuevoTelf = scan.nextLine();
                        while (nuevoTelf.length() != 9 || !f.esNumerico(nuevoTelf)) {
                            f.mensajeColorido("ROJO",
                                    "El número de telefono debe tener un formato correcto. Vuelva a introducir el número de telefono: ");
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
        } else {
            f.mensajeColorido("ROJO", "No se pudo obtener el usuario indicado. ");
        }
    }

    private static void deleteUser(DataBaseConnection con, String dni) {
        con.eliminarUser(dni);
    }

    private static void showAllUsers(DataBaseConnection con) {
        List<Usuarios> listaUs = con.mostrarUsuarios(null);
        if (listaUs.size() == 0) {
            f.mensajeColorido("rojo", "No se pudo obtener ningun usuario. ");
        } else {
            for (int i = 0; i < listaUs.size(); i++) {
                System.out.println("");
                System.out.println("Usuario " + i);
                System.out.println("*****************************************************");
                f.mensajeColorido("CIAN", listaUs.get(i).toString());
                System.out.println("*****************************************************");
                System.out.println("");
            }
        }
    }

    private static void registrarUsuario(DataBaseConnection con, String DNI, String nombre, String apellidos,
            String mail, String telf) {
        con.registrarUsuario(DNI, nombre, apellidos, mail, telf);
    }

    private static void testConnection(DataBaseConnection con) {
        if (con.testConnection()) {
            f.mensajeColorido("VERDE", "El test de conexion fue exitoso. ");
        } else {
            f.mensajeColorido("ROJO", "Se produjo un error al probar la conexion. ");
        }
    }

    private static void resetDataBase(DataBaseConnection con) {
        con.relanzarScript();
        //con.borradoTablas();
        //con.createDatabase();
    }

    private static void mostrarUsuario(DataBaseConnection con, String dni) {
        List<Usuarios> listUsers = con.mostrarUsuarios(dni);
        if (listUsers.size() == 0) {
            f.mensajeColorido("rojo", "No se pudo obtener ningun usuario. ");
        } else {
            for (int i = 0; i < listUsers.size(); i++) {
                System.out.println("Usuario " + i);
                System.out.println("*****************************************************");
                f.mensajeColorido("CIAN", listUsers.get(i).toString());
                System.out.println("*****************************************************");
            }
        }
    }

    private static void addBook(DataBaseConnection con, String nombre_libro, String autor, String desc, String fechaPublicacion, String disponible) {
        con.addBook(nombre_libro, autor, desc, fechaPublicacion, disponible);
    }

    private static void getBooks(DataBaseConnection con) {
        List<Libros> libros = con.getAllBooks();
        mostrarLibros(libros);
    }

    private static void searchBook(DataBaseConnection con, String campo, String nombre) {
        List<Libros> libros = con.getBookByField(campo, nombre);
        mostrarLibros(libros);
    }

    private static void mostrarLibros(List<Libros> libros) {
        if (libros.size() != 0) {
            for (int i = 0; i < libros.size(); i++) {
                System.out.println("Libro " + i);
                System.out.println("*****************************************************");
                f.mensajeColorido("CIAN", libros.get(i).toString());
                System.out.println("*****************************************************");
                System.out.println("");
            }
        } else {
            f.mensajeColorido("ROJO", "No se pudo recaudar datos. ");
        }
    }
 
    private static void updateBook(DataBaseConnection con, String id){
        Libros libro = con.getBookByField("ID", id).get(0);
        //System.out.println(libro.toString());
        String opcion = "";
        do {            
            
            System.out.println("");
            System.out.println("Introduzca el nombre del campo a modificar: ");
            System.out.println("Campos disponibles: \n\tNOMBRE\n\tAUTOR\n\tFECHA DE PUBLICACION\n\tDESCRIPCION\n\tDISPONIBILIDAD EN TIENDA");
            System.out.println("Introduzca SALIR para terminar la edición de los datos del libro. ");
            System.out.println("**************************************************");
            System.out.println("");
            opcion = scan.nextLine().toUpperCase().trim();
            
            switch (opcion) {
                case "NOMBRE":
                    f.mensajeColorido("azul", "Indique el nuevo nombre del libro: ");
                    String nombre = scan.nextLine();
                    libro.setNombre(nombre);
                    break;
                case "AUTOR":
                    f.mensajeColorido("azul", "Indique el nuevo autor del libro: ");
                    String autor = scan.nextLine();
                    libro.setAutor(autor);
                    break;
                case "FECHA DE PUBLICACION":
                    f.mensajeColorido("azul", "Indique la fecha de publicacion del libro en formato xxxx-xx-xx (AÑO-MES-DIA): ");
                    String fecPub = scan.nextLine();
                    libro.setFechaPublicacion(Date.valueOf(fecPub));
                    break;
                case "DESCRIPCION":
                    f.mensajeColorido("azul", "Indique la nueva descripcion del libro: ");
                    String desc = scan.nextLine();
                    libro.setDescripcion(desc);
                    break;
                case "DISPONIBILIDAD EN TIENDA":
                    f.mensajeColorido("azul", "Indique si el libro se encuentra disponible o no en la tienda. \nSi el libro está disponible escriba 1. \nSi el libro no está disponible en la tienda escriba 0. ");
                    String dispo = scan.nextLine().substring(0,1);
                    libro.setdisponibilidad((dispo == "1"? true: false));
                    break;
                case "SALIR":
                    f.mensajeColorido("azul", "Saliendo de la edicion de libros..... ");
                    break;
                default:
                    f.mensajeColorido("MORADO", "Debe introducir un campo valido. ");
            }
        } while (!opcion.equalsIgnoreCase("salir"));
        con.updateBook(libro);
    }

    private static void showBooksWithID(DataBaseConnection con) {
        List<Libros> libros = con.getAllBooks();
        System.out.println("Libros: ");
        for (int i = 0; i < libros.size(); i++) {
            System.out.println();
            System.out.println("****************************************************");
            f.mensajeColorido("AZUL", ("ID --> "+libros.get(i).getID() +"\nNombre --> "+ libros.get(i).getNombre() +"\nAutor --> "+libros.get(i).getAutor()));
            System.out.println("****************************************************");
            System.out.println();
        }
    }
}
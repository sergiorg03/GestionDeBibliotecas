CREATE OR REPLACE FUNCTION f_fechaIntroducida
    (v_fecha DATE) RETURN DATE DETERMINISTIC
IS
    v_fecha_sys DATE := SYSDATE;
BEGIN
    IF v_fecha >= v_fecha_sys THEN
        RETURN v_fecha_sys;
    ELSE 
        RETURN v_fecha;
    END IF;
END f_fechaIntroducida;
/

CREATE OR REPLACE PROCEDURE p_insertarDatosPrueba_historial
IS
    CURSOR cur_users IS
        SELECT dni
            FROM usuarios;

    CURSOR cur_libros IS
        SELECT id
            FROM libros;
BEGIN
    FOR v_users IN cur_users LOOP
        FOR v_libro IN cur_libros LOOP
            INSERT INTO historial (id_libro, dni_us, fecha_prestamo_his) VALUES (v_libro.id, v_users.dni, (SELECT SYSDATE FROM DUAL));
        END LOOP;
    END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
END p_insertarDatosPrueba_historial;
/

CREATE OR REPLACE PROCEDURE p_cambiarDisponibilidadLibro
    (v_id_libro IN libros.id%TYPE, v_disponible IN libros.disponible%TYPE)
IS
BEGIN
    UPDATE libros 
        SET disponible = v_disponible
        WHERE id = v_id_libro;
EXCEPTION
    WHEN NO_DATA_FOUND THEN 
        DBMS_OUTPUT.PUT_LINE('Libro no encontrado. ');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error'|| SQLCODE ||': '|| SQLERRM);
END p_cambiarDisponibilidadLibro;
/

CREATE OR REPLACE PROCEDURE p_realizarPrestamo
    (v_id_libro IN libros.id%TYPE, v_dni_us IN usuarios.dni%TYPE)
IS
    v_fecha_prestamo DATE := SYSDATE;
BEGIN
    INSERT INTO prestamos (libro_p, usuario_p, fecha_prestamo, fechaDevolucion, devuelto) 
        VALUES (v_id_libro, v_dni_us, v_fecha_prestamo, v_fecha_prestamo+30, '0');
EXCEPTION
    WHEN OTHERS THEN 
        DBMS_OUTPUT.PUT_LINE('Error'|| SQLCODE ||': '|| SQLERRM);
END p_realizarPrestamo;
/
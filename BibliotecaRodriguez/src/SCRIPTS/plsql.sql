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
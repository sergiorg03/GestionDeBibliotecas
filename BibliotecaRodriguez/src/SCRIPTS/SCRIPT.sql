ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

-- Borrado del usuario 
DROP USER bibliotecaRodriguez CASCADE;

-- Creacion del usuario
CREATE USER bibliotecaRodriguez
    IDENTIFIED BY bibliotecaRodriguez
    QUOTA 5M ON USERS;
    

DROP ROLE rol_bibliotecaRodriguez;
CREATE ROLE rol_bibliotecaRodriguez 
    IDENTIFIED BY bibliotecaRodriguez;

-- Privilegios de sistema
GRANT  
        CONNECT, 
        ALTER SESSION
    TO rol_bibliotecaRodriguez;
  
-- Privilegios de obj  
GRANT 
        CREATE TABLE, 
        CREATE PROCEDURE, 
        CREATE VIEW
    TO rol_bibliotecaRodriguez;

-- Borrado de tablas 
/*
DROP TABLE bibliotecaRodriguez.prestamos CASCADE CONSTRAINTS;
DROP TABLE bibliotecaRodriguez.historial CASCADE CONSTRAINTS;
DROP TABLE bibliotecaRodriguez.libros CASCADE CONSTRAINTS;
DROP TABLE bibliotecaRodriguez.usuarios CASCADE CONSTRAINTS;
DROP SEQUENCE bibliotecaRodriguez.seq_id_libro;
*/

-- Tabla usuarios
CREATE TABLE bibliotecaRodriguez.usuarios(
    DNI VARCHAR2(9),
    nombre VARCHAR2(255),
    apellidos VARCHAR2(255),
    mail VARCHAR2(255) UNIQUE,
    telf VARCHAR2(9)
);

-- Tabla libros
CREATE TABLE bibliotecaRodriguez.libros(
    id NUMBER(9),
    nombre VARCHAR2(255),
    descripcion VARCHAR2(255),
    autor VARCHAR2(255),
    fechaPublicacion DATE,
    disponible char(1) DEFAULT '1'
);

-- Tabla prestamos
CREATE TABLE bibliotecaRodriguez.prestamos(
    libro_p NUMBER(9),
    usuario_p VARCHAR2(9),
    fecha_prestamo DATE DEFAULT SYSDATE,
    fechaDevolucion DATE DEFAULT SYSDATE+30,
    devuelto VARCHAR2(1) DEFAULT '0'
);

-- Tabla historial
CREATE TABLE bibliotecaRodriguez.historial(
    id_libro NUMBER(9),
    dni_us VARCHAR2(9),
    fecha_prestamo_his DATE DEFAULT SYSDATE
);

GRANT 
        SELECT, 
        INSERT,
        DELETE, 
        UPDATE 
    ON bibliotecaRodriguez.libros 
    TO rol_bibliotecaRodriguez;
    

GRANT 
        SELECT, 
        INSERT,
        DELETE, 
        UPDATE 
    ON bibliotecaRodriguez.usuarios
    TO rol_bibliotecaRodriguez;
    
GRANT 
        SELECT, 
        INSERT,
        DELETE, 
        UPDATE 
    ON bibliotecaRodriguez.historial 
    TO rol_bibliotecaRodriguez;
    

GRANT 
        SELECT, 
        INSERT,
        DELETE, 
        UPDATE 
    ON bibliotecaRodriguez.prestamos
    TO rol_bibliotecaRodriguez;

GRANT
        DEBUG ANY PROCEDURE, 
        ALTER ANY PROCEDURE 
    TO rol_bibliotecaRodriguez;
    
GRANT 
        CREATE PROCEDURE, 
        EXECUTE ANY PROCEDURE
    TO rol_bibliotecaRodriguez;
    
GRANT 
        rol_bibliotecaRodriguez
    TO bibliotecaRodriguez;

-- Tabla usuarios 
ALTER TABLE bibliotecaRodriguez.usuarios ADD CONSTRAINT pk_usuario PRIMARY KEY (DNI);
ALTER TABLE bibliotecaRodriguez.usuarios ADD CONSTRAINT ck_dni_us CHECK (REGEXP_LIKE(dni, '^[0-9]{8}[A-Za-z]$'));
ALTER TABLE bibliotecaRodriguez.usuarios ADD CONSTRAINT ck_mail_us CHECK (REGEXP_LIKE(mail, '^.+@.+\..+$'));
ALTER TABLE bibliotecaRodriguez.usuarios ADD CONSTRAINT ck_telf_us CHECK (LENGTH(telf) = 9);

-- Tabla libros 
ALTER TABLE bibliotecaRodriguez.libros ADD CONSTRAINT pk_libros PRIMARY KEY(id);
ALTER TABLE bibliotecaRodriguez.libros ADD CONSTRAINT ck_dispo_libros CHECK (disponible IN ('0', '1'));
-- ALTER TABLE libros ADD CONSTRAINT ck_fechaPub_libro CHECK (F_fechaCorrecta(fechaPublicacion) = 1);

-- Tabla prestamos 
ALTER TABLE bibliotecaRodriguez.prestamos ADD CONSTRAINT pk_prestamos PRIMARY KEY(libro_p, usuario_p);
ALTER TABLE bibliotecaRodriguez.prestamos ADD CONSTRAINT fk_prestamos_libros FOREIGN KEY (libro_p) REFERENCES bibliotecaRodriguez.libros(id);
ALTER TABLE bibliotecaRodriguez.prestamos ADD CONSTRAINT fk_prestamos_usuarios FOREIGN KEY (usuario_p) REFERENCES bibliotecaRodriguez.usuarios(DNI) ON DELETE CASCADE;
ALTER TABLE bibliotecaRodriguez.prestamos ADD CONSTRAINT ck_devuelto CHECK (devuelto IN ('0', '1'));
-- ALTER TABLE prestamos ADD CONSTRAINT ck_prestamos_usuarios CHECK (F_fechaCorrecta(fecha_prestamo) = 1);
-- ALTER TABLE prestamos ADD CONSTRAINT ck_prestamos_usuarios CHECK (F_fechaCorrecta(fechaDevolucion) = 1);

-- Tabla historial 
ALTER TABLE bibliotecaRodriguez.historial ADD CONSTRAINT pk_historia PRIMARY KEY (fecha_prestamo_his, id_libro, dni_us);
ALTER TABLE bibliotecaRodriguez.historial ADD CONSTRAINT fk_historial_libros FOREIGN KEY (id_libro) REFERENCES bibliotecaRodriguez.libros(id);
ALTER TABLE bibliotecaRodriguez.historial ADD CONSTRAINT fk_historial_usuarios FOREIGN KEY (dni_us) REFERENCES bibliotecaRodriguez.usuarios(dni);
-- ALTER TABLE historial ADD CONSTRAINT ck_fechaPrestamo CHECK (F_fechaCorrecta(fecha_prestamo_his) = 1);

-- Secuencia para asignar el id del libro
CREATE SEQUENCE bibliotecaRodriguez.seq_id_libro 
    INCREMENT BY 1 
    START WITH 1 
    MAXVALUE 999999999 
    NOCYCLE;

CREATE OR REPLACE FUNCTION bibliotecaRodriguez.f_fechaIntroducida
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

CREATE OR REPLACE PROCEDURE bibliotecaRodriguez.insertarDatosPrueba_historial
AS
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
END insertarDatosPrueba_historial;
/

CREATE OR REPLACE PROCEDURE bibliotecaRodriguez.p_cambiarDisponibilidadLibro
    (v_id_libro IN bibliotecaRodriguez.libros.id%TYPE, v_disponible IN bibliotecaRodriguez.libros.disponible%TYPE)
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

-- Consultas
-- Privilegios asociados a un rol
SELECT * 
    FROM ROLE_SYS_PRIVS 
    WHERE UPPER(role) = UPPER('rol_bibliotecaRodriguez');

-- Privilegios activos en la sesion
SELECT *
    FROM SESSION_PRIVS;

-- Privilegios de objetos de un usuario
SELECT * 
    FROM USER_TAB_PRIVS 
    WHERE UPPER(GRANTEE) LIKE UPPER('%bibliotecaRodriguez%') 
    ORDER BY PRIVILEGE;

-- Privilegios asociados a un usuario

/* Bloque anonimo de prueba de la funcion f_fechaIntroducida
DECLARE 
    v_fec DATE := TO_DATE('10-12-1984', 'DD-MM-YYYY');
    v_fin DATE;
BEGIN
    v_fin := f_fechaIntroducida(v_fec);
    DBMS_OUT.PUT_LINE(v_fin);
END;
*/
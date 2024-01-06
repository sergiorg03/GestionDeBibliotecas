ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

DROP USER bibliotecaRodriguez;

CREATE USER bibliotecaRodriguez
    IDENTIFIED BY bibliotecaRodriguez
    QUOTA 5M ON USERS;
    
DROP PROFILE perfil_biblioteca;
CREATE PROFILE perfil_biblioteca 
    LIMIT 
        SESSIONS_PER_USER 2
        CPU_PER_SESSION UNLIMITED
        CPU_PER_CALL DEFAULT;

-- Privilegios de sistema
GRANT  
        CONNECT, 
        ALTER SESSION
    TO bibliotecaRodriguez;
  
-- Privilegios de obj  
GRANT 
        CREATE TABLE, 
        CREATE PROCEDURE, 
        CREATE VIEW
    TO bibliotecaRodriguez;
CLEAR SCREEN;
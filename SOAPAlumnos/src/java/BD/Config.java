/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

/**
 *
 * @author jmmartin
 */
public final class Config {

    //Base de datos
    public static final String BD_URL = "localhost/practica3";
    public static final String BD_USUARIO = "user1";
    public static final String BD_PASSWORD = "user1";
    public static final boolean WINDOWS = false;

}
/*
MYSQL
CREATE TABLE IF NOT EXISTS Alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255),
    email VARCHAR(255)
)  ENGINE=INNODB;

DERBY
CREATE TABLE Alumnos
	(id SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY 
	(INCREMENT BY 1), nombre VARCHAR(100), apellidos VARCHAR(100), email VARCHAR(100));
INSERT INTO Alumnos (nombre, apellidos, email)
    VALUES ('Paco', 'Pepe', 'example@mail.com');
*/
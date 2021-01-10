/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paqueteComplejos;

import BD.ConexionBD;
import BD.ConexionDerby;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juanm
 */
@WebService(serviceName = "OperacionesAlumno")
public class OperacionesAlumno {

    ConexionBD conexion;

    public OperacionesAlumno() {
        conexion = new ConexionDerby();
        conexion.Conectar();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listarAlumnos")
    public ArrayList<Alumno> listarAlumnos() throws SQLException {
        //TODO write your implementation code here:
        ArrayList<Alumno> alumnos = new ArrayList<>();
        Alumno a = null;

        try (Statement st = conexion.getConexion().createStatement()) {
            String Sentencia = String.format("Select * From Alumnos");
            try (ResultSet rs = st.executeQuery(Sentencia)) {
                while (rs.next()) {
                    a = new Alumno();
                    a.setId(rs.getInt("id"));
                    a.setNombre(rs.getString("nombre"));
                    a.setApellidos(rs.getString("apellidos"));
                    a.setEmail(rs.getString("email"));
                    alumnos.add(a);
                }
            } catch (Exception ex) {
                System.err.println("Error en Servidor");
                System.err.println(ex.getMessage());
            }
        }

        return alumnos;

    }

    /**
     * Web service operation
     *
     * @param <error>
     * @return
     */
    @WebMethod(operationName = "anadirAlumno")
    public boolean anadirAlumno(@WebParam(name = "nombre") String nombre, @WebParam(name = "apellidos") String apellidos, @WebParam(name = "email") String email) throws SQLException {
        //TODO write your implementation code here:
        boolean insertado = false;
        try (Statement st = conexion.getConexion().createStatement()) {

            String Sentencia = String.format("INSERT INTO ALUMNOS (NOMBRE, APELLIDOS, EMAIL) VALUES ('%s','%s','%s')",
                    nombre, apellidos, email);
            st.execute(Sentencia);
            insertado = true;

        } catch (Exception ex) {
            System.err.println("Error en Servidor");
            System.err.println(ex.getMessage());
        }

        return insertado;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editarAlumno")
    public boolean editarAlumno(@WebParam(name = "id") String id, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellidos") String apellidos, @WebParam(name = "email") String email) throws SQLException {
        //TODO write your implementation code here:
        boolean insertado = false;
        try (Statement st = conexion.getConexion().createStatement()) {

            String Sentencia = "UPDATE ALUMNOS SET nombre = '"+nombre+"' , apellidos = '"+apellidos+"' , email = '"+email+"' WHERE id = "+id;
            st.execute(Sentencia);
            insertado = true;

        } catch (Exception ex) {
            System.err.println("Error en Servidor");
            System.err.println(ex.getMessage());
        }

        return insertado;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "eliminarAlumno")
    public boolean eliminarAlumno(@WebParam(name = "id") String id) {
        //DELETE FROM USER1.ALUMNOS WHERE ID = 1 AND NOMBRE = 'E31' AND APELLIDOS = 'ARCHITECTURE' AND EMAIL = 'E01';
        //TODO write your implementation code here:
        boolean borrado = false;
        try (Statement st = conexion.getConexion().createStatement()) {

            String Sentencia = "DELETE FROM ALUMNOS WHERE ID = "+Integer.parseInt(id);
                    
            st.execute(Sentencia);
            borrado = true;

        } catch (Exception ex) {
            System.err.println("Error en Servidor");
            System.err.println(ex.getMessage());
        }

        return borrado;
        
    }

}

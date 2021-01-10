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
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase que define las operaciones básicas con una base de datos.
 *
 * @author jmmartin
 */
public abstract class ConexionBD {

    protected Connection conexion;

    /**
     * Devuelve la conexión con la base de datos.
     *
     * @return Conexión con la base de datos.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Realiza la conexión con una base de datos.
     */
    public abstract void Conectar();

    /**
     * Realiza la desconexión de la base de datos.
     *
     * @throws SQLException Si hay algún problema en la operación.
     */
    public void Desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
            conexion = null;
        }
    }

    /**
     * Configura el inicio de una transacción.
     *
     * @return True si se realiza con éxito, false en caso contrario.
     * @throws SQLException Si hay algún problema en la operación.
     */
    public boolean InicioTransaccion() throws SQLException {
        if (conexion != null) {
            conexion.setAutoCommit(false);
            return true;
        }

        return false;
    }

    /**
     * Finaliza la transacción realizando un commit.
     *
     * @return True si se realiza con éxito, false en caso contrario.
     * @throws SQLException Si hay algún problema en la operación.
     */
    public boolean FinTransaccionCommit() throws SQLException {
        if (conexion != null) {
            conexion.commit();
            conexion.setAutoCommit(true);
            return true;
        }

        return false;
    }

    /**
     * Finaliza la transacción realizando un rollback.
     *
     * @return True si se realiza con éxito, false en caso contrario.
     * @throws SQLException Si hay algún problema en la operación.
     */
    public boolean FinTransaccionRollback() throws SQLException {
        if (conexion != null) {
            conexion.rollback();
            conexion.setAutoCommit(true);
            return true;
        }

        return false;
    }

}

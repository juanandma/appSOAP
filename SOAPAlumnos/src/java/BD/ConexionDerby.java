package BD;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que define una conexión con bases de datos MySQL.
 *
 * @author jmmartin
 */
public class ConexionDerby extends ConexionBD {

    /**
     * Conecta con una base de datos MySQL.
     */
    @Override
    public void Conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:derby://" + Config.BD_URL, Config.BD_USUARIO, Config.BD_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDerby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

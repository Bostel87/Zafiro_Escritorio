
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_ciu;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla Tbm_ciu
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public interface Tbm_ciuDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_ciu tbm_ciu) throws SQLException;
    public void actualizar(Connection connection, Tbm_ciu tbm_ciu) throws SQLException;
    public List<Tbm_ciu> consultarLisGen(Connection connection, Tbm_ciu tbm_ciu) throws SQLException;
    public List<Tbm_ciu> consultarLisPag(Connection connection, Tbm_ciu tbm_ciu, int intRegIni, int intCanReg) throws SQLException;
    public String consultarCiudad(Connection connection, int intCodCiu) throws SQLException;
}

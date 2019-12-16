
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_tipdocdigsis;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_tipdocdigsis
 * @author Carlos Lainez
 * Guayaquil 17/05/2011
 */
public interface Tbm_tipdocdigsisDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis) throws SQLException;
    public void actualizar(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis) throws SQLException;
    public List<Tbm_tipdocdigsis> consultarLisGen(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis) throws SQLException;
    public List<Tbm_tipdocdigsis> consultarLisPag(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis, int intRegIni, int intCanReg) throws SQLException;
}

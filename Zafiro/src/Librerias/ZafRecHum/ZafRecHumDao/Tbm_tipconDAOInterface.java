
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipcon;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_tipcon
 * @author Carlos Lainez
 * Guayaquil 29/03/2011
 */
public interface Tbm_tipconDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_tipcon tbm_tipcon) throws SQLException;
    public void actualizar(Connection connection, Tbm_tipcon tbm_tipcon) throws SQLException;
    public List<Tbm_tipcon> consultarLisGen(Connection connection, Tbm_tipcon tbm_tipcon) throws SQLException;
    public List<Tbm_tipcon> consultarLisPag(Connection connection, Tbm_tipcon tbm_tipcon, int intRegIni, int intCanReg) throws SQLException;
}

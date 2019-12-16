
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_contra;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_contra
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public interface Tbm_contraDAOInterface {
    public int obtenerMaxId(Connection connection, int intCo_tra) throws SQLException;
    public void grabar(Connection connection, Tbm_contra tbm_contra) throws SQLException;
    public void actualizar(Connection connection, Tbm_contra tbm_contra) throws SQLException;
    public List<Tbm_contra> consultarLisGen(Connection connection, Tbm_contra tbm_contra) throws SQLException;
    public List<Tbm_contra> consultarLisPag(Connection connection, Tbm_contra tbm_contra, int intRegIni, int intCanReg) throws SQLException;
}


package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_comsec;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla Tbm_comSec
 * @author Carlos Lainez
 * Guayaquil 24/03/2011
 */
public interface Tbm_comsecDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_comsec tbm_comSec) throws SQLException;
    public void actualizar(Connection connection, Tbm_comsec tbm_comSec) throws SQLException;
    public List<Tbm_comsec> consultarLisGen(Connection connection, Tbm_comsec tbm_comsec) throws SQLException;
    public List<Tbm_comsec> consultarLisPag(Connection connection, Tbm_comsec tbm_comsec, int intRegIni, int intCanReg) throws SQLException;
    public boolean consultarCodCarRep(Connection connection, String strCodCar, int intCodComSec) throws SQLException;
}

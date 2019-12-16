
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipfamcon;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_tifamcon
 * @author Carlos Lainez
 * Guayaquil 15/04/2011
 */
public interface Tbm_tipfamconDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException;
    public void actualizar(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException;
    public List<Tbm_tipfamcon> consultarLisGen(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException;
    public List<Tbm_tipfamcon> consultarLisPag(Connection connection, Tbm_tipfamcon tbm_tipfamcon, int intRegIni, int intCanReg) throws SQLException;
    public String consultarTipoCargaFamiliar(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException;
}

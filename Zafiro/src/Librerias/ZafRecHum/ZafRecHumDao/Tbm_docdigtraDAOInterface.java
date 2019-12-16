
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_docdigtra;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_docdigtra
 * @author Carlos Lainez
 * Guayaquil 19/05/2011
 */
public interface Tbm_docdigtraDAOInterface {
    public int obtenerMaxId(Connection connection, int intCo_tra) throws SQLException;
    public void grabar(Connection connection, Tbm_docdigtra tbm_docdigtra) throws SQLException;
    public void actualizar(Connection connection, Tbm_docdigtra tbm_docdigtra) throws SQLException;
    public List<Tbm_docdigtra> consultarLisGen(Connection connection, Tbm_docdigtra tbm_docdigtra) throws SQLException;
    public List<Tbm_docdigtra> consultarLisPag(Connection connection, Tbm_docdigtra tbm_docdigtra, int intRegIni, int intCanReg) throws SQLException;
    public boolean existe(Connection connection, int intCodTra, int intCoTipDocDig) throws SQLException;
}

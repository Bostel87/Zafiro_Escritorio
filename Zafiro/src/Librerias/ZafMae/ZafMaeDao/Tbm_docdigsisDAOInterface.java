
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_docdigsis;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_docdigsis
 * @author Carlos Lainez
 * Guayaquil 24/05/2011
 */
public interface Tbm_docdigsisDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_docdigsis tbm_docdigsis) throws SQLException;
    public void actualizar(Connection connection, Tbm_docdigsis tbm_docdigsis) throws SQLException;
    public List<Tbm_docdigsis> consultarLisGen(Connection connection, Tbm_docdigsis tbm_docdigsis) throws SQLException;
    public List<Tbm_docdigsis> consultarLisPag(Connection connection, Tbm_docdigsis tbm_docdigsis, int intRegIni, int intCanReg) throws SQLException;
}

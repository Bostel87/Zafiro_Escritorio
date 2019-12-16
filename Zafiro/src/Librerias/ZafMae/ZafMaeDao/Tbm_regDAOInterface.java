
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_reg;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_reg
 * @author Carlos Lainez
 * Guayaquil 13/05/2011
 */
public interface Tbm_regDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_reg tbm_reg) throws SQLException;
    public void actualizar(Connection connection, Tbm_reg tbm_reg) throws SQLException;
    public List<Tbm_reg> consultarLisGen(Connection connection, Tbm_reg tbm_reg) throws SQLException;
    public List<Tbm_reg> consultarLisPag(Connection connection, Tbm_reg tbm_reg, int intRegIni, int intCanReg) throws SQLException;
}


package Librerias.ZafMae.ZafMaeDao;

import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_emp
 * @author Carlos Lainez
 * Guayaquil 22/06/2011
 */
public interface Tbm_empDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_emp tbm_emp) throws SQLException;
    public void actualizar(Connection connection, Tbm_emp tbm_emp) throws SQLException;
    public List<Tbm_emp> consultarLisGen(Connection connection, Tbm_emp tbm_emp) throws SQLException;
    public List<Tbm_emp> consultarLisPag(Connection connection, Tbm_emp tbm_emp, int intRegIni, int intCanReg) throws SQLException;
    public List<Tbm_emp> consultarLisGenSinEmpGru(Connection connection) throws SQLException;
    public List<Tbm_emp> consultarLisGenPorNomEmp(Connection connection, String strNomEmp) throws SQLException;
}

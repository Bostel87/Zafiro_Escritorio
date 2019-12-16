
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbr_traemp
 * @author Carlos Lainez
 * Guayaquil 17/06/2011
 */
public interface Tbm_traempDAOInterface {
    public void grabar(Connection connection, Tbm_traemp tbr_traemp) throws SQLException;
    public void actualizar(Connection connection, Tbm_traemp tbr_traemp) throws SQLException;
    public void actualizarTraEmpCodHor(Connection connection, Tbm_traemp tbr_traemp) throws SQLException;
    public List<Tbm_traemp> consultarLisGen(Connection connection, Tbm_traemp tbr_traemp) throws SQLException;
    public List<Tbm_traemp> consultarLisPag(Connection connection, Tbm_traemp tbr_traemp, int intRegIni, int intCanReg) throws SQLException;
    public List<Tbm_emp> consultarLisGenPorCodTra(Connection connection, int intCodTra) throws SQLException;
    //public List<String> consultarLisGenPorCodTra2(Connection connection, int intCodTra, int intCoEmp) throws SQLException;
    public List<String> consultarLisGenPorCodTra2(Connection connection, int intCodTra) throws SQLException;
    public List<Tbm_emp> consultarLisGenPorCodTraNomEmp(Connection connection, int intCodTra, String strNomEmp) throws SQLException;
    public void eliminar(Connection connection, int intCodTra, int intCodEmp) throws SQLException;
    public String consultarLisGenPorCodTraPorHor(Connection connection, int intCodTra) throws SQLException;
}

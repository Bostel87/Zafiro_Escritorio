
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_tra
 * @author Carlos Lainez
 * Guayaquil 01/04/2011
 */
public interface Tbm_traDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection,Tbm_tra tbm_tra, ZafParSis objZafParSis) throws SQLException;
    public void grabar(Connection connection,Connection conSec,Tbm_tra tbm_tra, ZafParSis objZafParSis) throws SQLException;
    public void actualizar(Connection connection, Tbm_tra tbm_tra, ZafParSis objZafParSis) throws SQLException;
    public void anular(Connection connection, Tbm_tra tbm_tra, ZafParSis objZafParSis) throws SQLException;
    public List<Tbm_tra> consultarLisGen(Connection connection, Tbm_tra tbm_tra) throws SQLException;
    public List<Tbm_tra> consultarLisGen(Connection connection, int intCodEmp) throws SQLException;
    public List<Tbm_tra> consultarLisGen(Connection connection, Tbm_tra tbm_tra, int intCodEmp) throws SQLException;
    public List<Tbm_tra> consultarLisPag(Connection connection, Tbm_tra tbm_tra, int intRegIni, int intCanReg, ZafParSis objZafParSis) throws SQLException;
    //public boolean consultarCedRep(Connection connection, int intCodTra, String strCedTra) throws SQLException;
    public boolean consultarCedRep(Connection connection, String strCedTra) throws SQLException;
    public List<Tbm_tra> consultarDesHas(Connection connection, String strNomDes, String strNomHas, String strApeDes, String strApeHas) throws SQLException;
    public List<Tbm_tra> consultarDesHas(Connection connection, String strNomDes, String strNomHas, String strApeDes, String strApeHas, int intCodEmp) throws SQLException;
    public List<Tbm_hortra> consultarLisGenCabHorTra(Connection connection) throws SQLException;
}

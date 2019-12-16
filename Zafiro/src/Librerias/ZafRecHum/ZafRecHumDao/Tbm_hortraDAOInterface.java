/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumBo.Tbm_callabBO;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Interfaz para implementación del DAO de las tablas Tbm_cabhortra, Tbm_dethortra
 * @author Roberto Flores
 * Guayaquil 08/09/2011
 */
public interface Tbm_hortraDAOInterface {
    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, List<Tbm_hortra> lisTbm_hortra , Tbm_hortra tbm_horTra, Date datFecHoy, int intMaxAño, Tbm_callabBO tbm_callabBO) throws Exception;
    public void actualizar(Connection connection, Tbm_hortra tbm_horTra, int opcion, Tbm_callabBO tbm_callabBO) throws Exception;
    public List<Tbm_hortra> consultarLisGen(Connection connection, Tbm_hortra tbm_horTra) throws SQLException;
    public List<Tbm_hortra> consultarLisPag(Connection connection, Tbm_hortra tbm_horTra, int intRegIni, int intCanReg) throws SQLException;
    public List<Tbm_hortra> consultar(Connection connection) throws SQLException;
    public boolean consultarNomHorRep(Connection connection, String strNomHor, int intCodHor) throws SQLException;
    public int consultarNomHorRep(Connection connection, String strNomHor) throws SQLException;
    public List<Tbm_hortra> consultarCalLabAct(Connection connection) throws SQLException;
    public int[] consultaCodHor(Connection connection) throws SQLException;
    public int[] consultaDiasValidosIniciales(Connection connection) throws SQLException;
    public int[] consultaMesesValidosIniciales(Connection connection) throws SQLException;
}
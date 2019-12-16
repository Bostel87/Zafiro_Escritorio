/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_MotHorSupExt;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_motHorSupExt
 * @author Roberto Flores
 * Guayaquil 26/03/2012
 */
public interface Tbm_MotHorSupExtDAOInterface {

    public int obtenerMaxId(Connection connection) throws Exception;
    public void grabar(Connection connection, Tbm_MotHorSupExt tbm_motHorSupExt) throws Exception;
    public void actualizar(Connection connection, Tbm_MotHorSupExt tbm_motHorSupExt) throws Exception;
    public List<Tbm_MotHorSupExt> consultarLisPag(Connection connection, Tbm_MotHorSupExt tbm_motHorSupExt, int intRegIni, int intCanReg) throws SQLException;
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_dep;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_dep
 * @author Roberto Flores
 * Guayaquil 21/11/2011
 */
public interface Tbm_depDAOInterface {

    public int obtenerMaxId(Connection connection) throws Exception;
    public void grabar(Connection connection, Tbm_dep tbm_dep) throws Exception;
    public void actualizar(Connection connection, Tbm_dep tbm_dep) throws Exception;
    public List<Tbm_dep> consultarLisPag(Connection connection, Tbm_dep tbm_dep, int intRegIni, int intCanReg) throws SQLException;
}
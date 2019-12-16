/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_callab;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Interfaz para implementación del DAO de la tabla tbm_callab
 * @author Roberto Flores
 * Guayaquil 16/09/2011
 */
public interface Tbm_callabDAOInterface {
    public Date consultaAño(Connection connection) throws SQLException;
    public void grabar(Connection connection, ArrayList<Tbm_callab> arrTbm_callab) throws SQLException;
    public void eliminar(Connection connection, int intCoHor , Date datFe_ultMod) throws Exception;
    //public void eliminar(Connection connection) throws Exception;
    public int obtenerMaxAño(Connection connection) throws SQLException;
}

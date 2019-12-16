/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Interfaz para implementación del DAO de la tablas relacionadas al control de asistencias de los trabajadores.
 * @author Roberto Flores
 * Guayaquil 26/12/2011
 */
public interface Tbm_conasitraDAOInterface {

    public int obtenerCoRegMaxTra(Connection connection, String datFecHoy, int intCoTra) throws SQLException;

}

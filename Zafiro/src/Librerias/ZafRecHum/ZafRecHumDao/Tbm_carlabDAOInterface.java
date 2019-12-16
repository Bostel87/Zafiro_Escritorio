/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_carlab;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla tbm_carlab
 * @author Roberto Flores
 * Guayaquil 17/11/2011
 */
public interface Tbm_carlabDAOInterface {

    public int obtenerMaxId(Connection connection) throws Exception;
    public void grabar(Connection connection, Tbm_carlab tbm_carlab) throws Exception;
    public void actualizar(Connection connection, Tbm_carlab tbm_carlab) throws Exception;
    public boolean consultarCodNomCarLab(Connection connection, String strNomHor, int intCodHor) throws Exception;
    public List<Tbm_carlab> consultarLisPag(Connection connection, Tbm_carlab tbm_carlab, int intRegIni, int intCanReg) throws Exception;

}

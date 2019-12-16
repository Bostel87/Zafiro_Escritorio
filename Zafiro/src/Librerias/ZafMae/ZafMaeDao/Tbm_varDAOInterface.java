/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_var;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz para implementación del DAO de la tabla Tbm_var
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public interface Tbm_varDAOInterface {

    public int obtenerMaxId(Connection connection) throws SQLException;
    public void grabar(Connection connection, Tbm_var tbm_var) throws SQLException;
    public void actualizar(Connection connection, Tbm_var tbm_var) throws SQLException;
    public List<Tbm_var> consultarLisGen(Connection connection, Tbm_var tbm_var) throws SQLException;
    public List<Tbm_var> consultarLisPag(Connection connection, Tbm_var tbm_var, int intRegIni, int intCanReg) throws SQLException;
    public String consultarEstadoCivil(Connection connection, int intEstCiv) throws SQLException;
    public String consultarEstadoCivil(Connection connection, String strEstCiv) throws SQLException;
}

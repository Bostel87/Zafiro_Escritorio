/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_MotHorSupExtDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_MotHorSupExt;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Objeto que se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Roberto Flores
 * Guayaquil 26/03/2012
 */
public class Tbm_MotHorSupExtBO {

    private Tbm_MotHorSupExtDAOInterface tbm_motHorSupExtDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_MotHorSupExtBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_motHorSupExtDAOInterface = (Tbm_MotHorSupExtDAOInterface) Tbm_MotHorSupExtBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_MotHorSupExtDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_MotHorSupExtDAO");
        }
    }

    /**
     * Graba datos en la tabla Tbm_MotHorSupExt
     * @param tbm_motHorSupExt Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabar(Tbm_MotHorSupExt tbm_motHorSupExt) throws Exception{
        int maxId = 0;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_motHorSupExtDAOInterface.obtenerMaxId(con)+1;
                tbm_motHorSupExt.setIntCo_mot(maxId);
                tbm_motHorSupExtDAOInterface.grabar(con, tbm_motHorSupExt);
                con.commit();
            }
        } catch (SQLException ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Actualiza los datos de las tablas Tbm_MotHorSupExt
     * @param tbm_motHorSupExt Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizar(Tbm_MotHorSupExt tbm_motHorSupExt) throws Exception{
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_motHorSupExtDAOInterface.actualizar(con, tbm_motHorSupExt);
                con.commit();
            }
        } catch (SQLException ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_motHorSupExt Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_MotHorSupExt con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_MotHorSupExt> consultarLisPag(Tbm_MotHorSupExt tbm_motHorSupExt, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_MotHorSupExt> lisTbm_motHorSupExt = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_motHorSupExt = tbm_motHorSupExtDAOInterface.consultarLisPag(con, tbm_motHorSupExt, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_motHorSupExt;
    }
}
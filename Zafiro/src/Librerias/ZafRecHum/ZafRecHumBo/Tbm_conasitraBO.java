/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_conasitraDAOInterface;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Roberto Flores
 * Guayaquil 16/11/2011
 */
public class Tbm_conasitraBO {

    private Tbm_conasitraDAOInterface tbm_conasitraDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_conasitraBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_conasitraDAOInterface = (Tbm_conasitraDAOInterface) Tbm_conasitraBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_conasitraDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_callabDAOInterface");
        }
    }

    /**
     * Consulta máximo codigo registro encontrado en la tabla tbm_jusSalConAsiTra por empleado y dia.
     * @param datFecHoy fecha actual
     * @param intCoTra codigo del trabajador
     * @return Retorna 0 si no hay registros y el máximo registro por empleado y dia si los hay.
     * @throws Exception
     */
    public int obtenerCoRegMaxTra(String datFecHoy, int intCoTra) throws Exception{
        int intCoReg = 0;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                intCoReg = tbm_conasitraDAOInterface.obtenerCoRegMaxTra(con, datFecHoy, intCoTra);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return intCoReg;
    }

}

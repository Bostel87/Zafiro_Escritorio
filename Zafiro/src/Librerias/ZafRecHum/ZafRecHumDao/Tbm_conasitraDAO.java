/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import jxl.write.DateTime;

/**
 * Objeto de Acceso a Datos de la tabla tbm_callab
 * Usada por el BO
 * @author Roberto Flores
 * Guayaquil 16/09/2011
 */
public class Tbm_conasitraDAO implements Tbm_conasitraDAOInterface{

    /**
     * Retorna el máximo codigo registro encontrado en la tabla tbm_jusSalConAsiTra por empleado y dia.
     * @param connection
     * @param datFecHoy fecha actual
     * @param intCoTra codigo del trabajador
     * @return Retorna 0 si no hay registros y el máximo registro por empleado y dia si los hay.
     * @throws Exception
     */
    public int obtenerCoRegMaxTra(Connection connection, String datFecHoy, int intCoTra) throws SQLException {
        String strSql = null;
        Statement stmLoc = null;
        ResultSet resSet = null;
        int intCoReg = 0;
        ZafParSis objZafParSis = new ZafParSis();
        ZafUtil objUti = new ZafUtil();

        try{
            strSql = "select max(co_reg) as maxCoReg from tbm_jusSalConAsiTra where fe_dia = '" +
                    objUti.formatearFecha(datFecHoy, "yyyy-MM-dd", objZafParSis.getFormatoFechaBaseDatos()) +
                    "' and co_tra = " + intCoTra;
            stmLoc=connection.createStatement();
            resSet = stmLoc.executeQuery(strSql);
            /*SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d=sdf.parse(datFecHoy);*/
            /*sta.setDate(1, (Date) d);
            sta.setInt(2, intCoReg);*/
            //resSet = sta.executeQuery();

            if(resSet.next()){
                intCoReg = resSet.getInt("maxCoReg");
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{stmLoc.close();}catch(Throwable ignore){}
        }

        return intCoReg;
    }

    public int obtenerCoRegMaxTra(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_callab;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Objeto de Acceso a Datos de la tabla tbm_callab
 * Usada por el BO
 * @author Roberto Flores
 * Guayaquil 16/09/2011
 */
public class Tbm_callabDAO implements Tbm_callabDAOInterface{


    private ZafUtil objUti = new ZafUtil();

    /**
     * Retorna el máximo fecha encontrada en la tabla tbm_callab.
     * @param connection
     * @return Retorna 0 si no hay registros y el máximo id si los hay.
     * @throws Exception
     */
    public Date consultaAño(Connection connection) throws SQLException {
        String strSql = null;
        Statement sta = null;
        ResultSet resSet = null;
        Date date = null;

        try{
            strSql = "select max(fe_dia) as maxAño from tbm_callab";
            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);
            if(resSet.next()){
                date = resSet.getDate("maxAño");
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{sta.close();}catch(Throwable ignore){}
        }

        return date;
    }

    
    public void eliminar(Connection connection, int intCoHor, Date datFe_ultMod) throws SQLException {

        String strSql = "delete from tbm_callab ";
        strSql+= "where co_hor = " + intCoHor;
        strSql+= "and fe_dia >= '" + objUti.formatearFecha(datFe_ultMod, "yyyy-MM-dd hh:mm:ss")+"'";
        PreparedStatement preSta = connection.prepareStatement(strSql);
        preSta.executeUpdate();
    }

    /*public void eliminar(Connection connection) throws SQLException {

        String strSql = "delete from tbm_callab ";
        PreparedStatement preSta = connection.prepareStatement(strSql);
        preSta.executeUpdate();
    }*/

    /**
     * Graba en la tabla tbm_callab
     * @param connection
     * @param arrTbm_callab Array contiene Objetos POJO que contiene los datos de la tabla tbm_callab
     * @throws SQLException
     */
    public void grabar(Connection connection, ArrayList<Tbm_callab> arrTbm_callab) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        DateFormat sdf = new SimpleDateFormat("hh:mm");
        Date date = null;
        
        for(Iterator<Tbm_callab> it = arrTbm_callab.iterator(); it.hasNext();){

            Tbm_callab tbm_callab = it.next();

            try{
                strSql = "insert into tbm_callab(fe_dia, co_hor, st_dialab, ho_ent, ho_sal, ho_mingraent, tx_obs1) ";
                strSql += "values(?,?,?,?,?,?,?)";

                preSta = connection.prepareStatement(strSql);

                if(tbm_callab.getDteFe_Dia()!=null){
                    date = tbm_callab.getDteFe_Dia();
                    preSta.setDate(1, new java.sql.Date(date.getTime()));
                }else{
                    preSta.setNull(1, Types.DATE);
                }
                if(tbm_callab.getIntCo_Hor()>0){
                    preSta.setInt(2, tbm_callab.getIntCo_Hor());
                }else{
                    preSta.setNull(2, Types.INTEGER);
                }
                if(tbm_callab.getStrSt_DiaLab()!=null){
                    preSta.setString(3, tbm_callab.getStrSt_DiaLab());
                }else{
                    preSta.setNull(3, Types.VARCHAR);
                }
                if(tbm_callab.getStrHo_Ent()==null || tbm_callab.getStrHo_Ent().compareTo("")==0){
                    preSta.setTime(4, null);
                }else{
                    date = sdf.parse(tbm_callab.getStrHo_Ent());
                    preSta.setTime(4, new Time(date.getTime()));
                }
                if(tbm_callab.getStrHo_Sal()==null || tbm_callab.getStrHo_Sal().compareTo("")==0){
                    preSta.setTime(5, null);
                }else{
                    date = sdf.parse(tbm_callab.getStrHo_Sal());
                    preSta.setTime(5, new Time(date.getTime()));
                }
                if(tbm_callab.getStrMin_GraEnt()==null || tbm_callab.getStrMin_GraEnt().compareTo("")==0){
                    preSta.setTime(6, null);
                }else{
                    date = sdf.parse(tbm_callab.getStrMin_GraEnt());
                    preSta.setTime(6, new Time(date.getTime()));
                }
                if(tbm_callab.getStrTx_Obs1()!=null){
                    preSta.setString(7, tbm_callab.getStrTx_Obs1());
                }else{
                    preSta.setNull(7, Types.VARCHAR);
                }

                preSta.executeUpdate();

            }catch(Exception e){
                e.printStackTrace();
            }
            finally {
                try{preSta.close();}catch(Throwable ignore){}
            }


        }
    }


    /**
     * Retorna el máximo año de la tabla tbm_callab.
     * @param connection
     * @return Retorna 0 si no hay registros y el máximo año si los hay.
     * @throws Exception
     */
    public int obtenerMaxAño(Connection connection) throws SQLException {

        String strSql = null;
        Statement sta = null;
        ResultSet resSet = null;
        Date date = null;
        int intMaxAño = 0;

        try{
            strSql = "select max(fe_dia) as maxFecha from tbm_callab";
            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);

            if(resSet.next()){
                date = resSet.getDate("maxFecha");
                if(date!=null){
                    String formato="yyyy";
                    SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
                    intMaxAño = Integer.parseInt(dateFormat.format(date));
                }
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{sta.close();}catch(Throwable ignore){}
        }
        return intMaxAño;
    }

    

    

}

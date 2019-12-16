/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_MotHorSupExt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_MotHorSupExt
 * @author Roberto Flores
 * Guayaquil 26/03/2012
 */
public class Tbm_MotHorSupExtDAO implements Tbm_MotHorSupExtDAOInterface{

    /**
     * Retorna el máximo id de la tabla Tbm_motHorSupExt.
     * @param connection
     * @return Retorna 0 si no hay registros y el máximo id si los hay.
     * @throws Exception
     */
    public int obtenerMaxId(Connection connection) throws SQLException {
        String strSql = null;
        Statement sta = null;
        ResultSet resSet = null;
        int intMaxId = 0;

        try{
            strSql = "Select max(co_mot) as maxId from tbm_motHorSupExt";
            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);

            if(resSet.next()){
                intMaxId = resSet.getInt("maxId");
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{sta.close();}catch(Throwable ignore){}
        }

        return intMaxId;
    }

    /**
     * Graba en las tablas Tbm_motHorSupExt.
     * @param connection
     * @param tbm_motHorSupExt Objeto POJO que contiene los datos de la tabla Tbm_motHorSupExt.
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_MotHorSupExt tbm_motHorSupExt) throws Exception {
        String strSql = null;
        PreparedStatement preSta = null;

         try{
             strSql = "insert into tbm_motHorSupExt(co_mot,tx_descor,tx_deslar,st_reg) ";
             strSql += "values(?,?,?,?)";

             preSta = connection.prepareStatement(strSql);

             if(tbm_motHorSupExt.getIntCo_mot()>0){
                preSta.setInt(1, tbm_motHorSupExt.getIntCo_mot());
             }else{
                 preSta.setNull(1, Types.INTEGER);
             }
             if(tbm_motHorSupExt.getStrTx_DesCor()!=null){
                preSta.setString(2, tbm_motHorSupExt.getStrTx_DesCor());
             }else{
                 preSta.setNull(2, Types.VARCHAR);
             }
             if(tbm_motHorSupExt.getStrTx_DesLar()!=null){
                preSta.setString(3, tbm_motHorSupExt.getStrTx_DesLar());
             }else{
                 preSta.setNull(3, Types.VARCHAR);
             }
             if(tbm_motHorSupExt.getStrSt_Reg()!=null){
                preSta.setString(4, tbm_motHorSupExt.getStrSt_Reg());
             }else{
                 preSta.setNull(4, Types.VARCHAR);
             }

            preSta.executeUpdate();

         }catch(Exception e){
             throw new Exception(e.getMessage());
         }
         finally {
            try{preSta.close();}catch(Throwable ignore){}
         }
    }

    /**
     * Actualiza datos de las tablas Tbm_motHorSupExt.
     * @param connection
     * @param tbm_motHorSupExt Objeto con los parámetros a actualizar
     * @throws SQLException
     */

    public void actualizar(Connection connection, Tbm_MotHorSupExt tbm_motHorSupExt) throws Exception {

        String strSql = null;
        PreparedStatement preSta = null;

        try{

            strSql =  "update tbm_motHorSupExt ";
            strSql += "set tx_descor = ?, ";
            strSql += "tx_deslar = ?, ";
            strSql += "st_reg = ? ";
            strSql += "where co_mot = ?";

            preSta = connection.prepareStatement(strSql);

            if(tbm_motHorSupExt.getStrTx_DesCor()!=null){
                preSta.setString(1, tbm_motHorSupExt.getStrTx_DesCor());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            if(tbm_motHorSupExt.getStrTx_DesLar()!=null){
                preSta.setString(2, tbm_motHorSupExt.getStrTx_DesLar());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            if(tbm_motHorSupExt.getStrSt_Reg()!=null){
                preSta.setString(3, tbm_motHorSupExt.getStrSt_Reg());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }

            if(tbm_motHorSupExt.getIntCo_mot()>0){
                preSta.setInt(4, tbm_motHorSupExt.getIntCo_mot());
             }else{
                 preSta.setNull(4, Types.INTEGER);
             }
            
            preSta.executeUpdate();

        }catch(Exception e){
             throw new Exception(e.getMessage());
        }
        finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla Tbm_motHorSupExt aplicando pagineo
     * @param connection
     * @param tbm_motHorSupExt Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_MotHorSupExt con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_MotHorSupExt> consultarLisPag(Connection connection, Tbm_MotHorSupExt tbm_motHorSupExt, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_MotHorSupExt> lisTbm_motHorSupExt = null;

        try{
            strSql = "Select * from Tbm_motHorSupExt ";
            strSql += "where (? is null or co_mot = ?) ";
            strSql += "and (? is null or lower(tx_descor) like lower(?)) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            strSql += "order by co_mot ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_dep
            if(tbm_motHorSupExt.getIntCo_mot()>0){
                preSta.setInt(1, tbm_motHorSupExt.getIntCo_mot());
                preSta.setInt(2, tbm_motHorSupExt.getIntCo_mot());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            //tx_descor
            if(tbm_motHorSupExt.getStrTx_DesCor()!=null){
                preSta.setString(3, tbm_motHorSupExt.getStrTx_DesCor().replace('*', '%'));
                preSta.setString(4, tbm_motHorSupExt.getStrTx_DesCor().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            //tx_deslar
            if(tbm_motHorSupExt.getStrTx_DesLar()!=null){
                preSta.setString(5, tbm_motHorSupExt.getStrTx_DesLar().replace('*', '%'));
                preSta.setString(6, tbm_motHorSupExt.getStrTx_DesLar().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }

            if(tbm_motHorSupExt.getStrSt_Reg()!=null){
                preSta.setString(7, tbm_motHorSupExt.getStrSt_Reg().replace('*', '%'));
                preSta.setString(8, tbm_motHorSupExt.getStrSt_Reg().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }

            //limit 11
            if(intCanReg > 0 ){
                preSta.setInt(9, intCanReg);
            }else{
                preSta.setNull(9, Types.INTEGER);
            }

            //offset 12
            preSta.setInt(10, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_motHorSupExt = new ArrayList<Tbm_MotHorSupExt>();
                do{
                    Tbm_MotHorSupExt tmp = new Tbm_MotHorSupExt();
                    tmp.setIntCo_mot(resSet.getInt("co_mot"));
                    tmp.setStrTx_DesCor(resSet.getString("tx_descor"));
                    tmp.setStrTx_DesLar(resSet.getString("tx_deslar"));
                    tmp.setStrSt_Reg(resSet.getString("st_reg"));
                    lisTbm_motHorSupExt.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_motHorSupExt;
    }

}
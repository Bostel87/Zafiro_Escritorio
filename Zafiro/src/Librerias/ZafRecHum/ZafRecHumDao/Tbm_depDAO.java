/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_dep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_dep
 * @author Roberto Flores
 * Guayaquil 21/11/2011
 */
public class Tbm_depDAO implements Tbm_depDAOInterface{


    /**
     * Retorna el máximo id de la tabla Tbm_dep.
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
            strSql = "Select max(co_dep) as maxId from tbm_dep";
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
     * Graba en las tablas tbm_dep.
     * @param connection
     * @param tbm_dep Objeto POJO que contiene los datos de la tabla tbm_dep.
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_dep tbm_dep) throws Exception {
        String strSql = null;
        PreparedStatement preSta = null;

         try{
             strSql = "insert into tbm_dep(co_dep,tx_descor,tx_deslar,st_reg) ";
             strSql += "values(?,?,?,?)";

             preSta = connection.prepareStatement(strSql);

             if(tbm_dep.getIntCo_Dep()>0){
                preSta.setInt(1, tbm_dep.getIntCo_Dep());
             }else{
                 preSta.setNull(1, Types.INTEGER);
             }
             if(tbm_dep.getStrTx_DesCor()!=null){
                preSta.setString(2, tbm_dep.getStrTx_DesCor());
             }else{
                 preSta.setNull(2, Types.VARCHAR);
             }
             if(tbm_dep.getStrTx_DesLar()!=null){
                preSta.setString(3, tbm_dep.getStrTx_DesLar());
             }else{
                 preSta.setNull(3, Types.VARCHAR);
             }
             if(tbm_dep.getStrSt_Reg()!=null){
                preSta.setString(4, tbm_dep.getStrSt_Reg());
             }else{
                 preSta.setNull(4, Types.VARCHAR);
             }

            preSta.executeUpdate();

         }catch(Exception e){
             e.printStackTrace();
             throw new Exception(e.getMessage());
         }
         finally {
            try{preSta.close();}catch(Throwable ignore){}
         }
    }

    /**
     * Actualiza datos de las tablas tbm_dep
     * @param connection
     * @param tbm_dep Objeto con los parámetros a actualizar
     * @throws SQLException
     */

    public void actualizar(Connection connection, Tbm_dep tbm_dep) throws Exception {

        String strSql = null;
        PreparedStatement preSta = null;

        try{

            strSql =  "update tbm_dep ";
            strSql += "set tx_descor = ?, ";
            strSql += "tx_deslar = ?, ";
            strSql += "st_reg = ? ";
            strSql += "where co_dep = ?";

            preSta = connection.prepareStatement(strSql);

            if(tbm_dep.getStrTx_DesCor()!=null){
                preSta.setString(1, tbm_dep.getStrTx_DesCor());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            if(tbm_dep.getStrTx_DesLar()!=null){
                preSta.setString(2, tbm_dep.getStrTx_DesLar());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            if(tbm_dep.getStrSt_Reg()!=null){
                preSta.setString(3, tbm_dep.getStrSt_Reg());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }

            preSta.setInt(4, tbm_dep.getIntCo_Dep());

            preSta.executeUpdate();

        }catch(Exception e){
             //e.printStackTrace();
             throw new Exception(e.getMessage());
        }
        finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_dep aplicando pagineo
     * @param connection
     * @param tbm_dep Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_dep con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_dep> consultarLisPag(Connection connection, Tbm_dep tbm_dep, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_dep> lisTbm_dep = null;

        try{
            strSql = "Select * from tbm_dep ";
            strSql += "where (? is null or co_dep = ?) ";
            strSql += "and (? is null or lower(tx_descor) like lower(?)) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            strSql += "order by co_dep ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_dep
            if(tbm_dep.getIntCo_Dep()>0){
                preSta.setInt(1, tbm_dep.getIntCo_Dep());
                preSta.setInt(2, tbm_dep.getIntCo_Dep());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            //tx_descor
            if(tbm_dep.getStrTx_DesCor()!=null){
                preSta.setString(3, tbm_dep.getStrTx_DesCor().replace('*', '%'));
                preSta.setString(4, tbm_dep.getStrTx_DesCor().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            //tx_deslar
            if(tbm_dep.getStrTx_DesLar()!=null){
                preSta.setString(5, tbm_dep.getStrTx_DesLar().replace('*', '%'));
                preSta.setString(6, tbm_dep.getStrTx_DesLar().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }

            if(tbm_dep.getStrSt_Reg()!=null){
                preSta.setString(7, tbm_dep.getStrSt_Reg().replace('*', '%'));
                preSta.setString(8, tbm_dep.getStrSt_Reg().replace('*', '%'));
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
                lisTbm_dep = new ArrayList<Tbm_dep>();
                do{
                    Tbm_dep tmp = new Tbm_dep();
                    tmp.setIntCo_Dep(resSet.getInt("co_dep"));
                    tmp.setStrTx_DesCor(resSet.getString("tx_descor"));
                    tmp.setStrTx_DesLar(resSet.getString("tx_deslar"));
                    tmp.setStrSt_Reg(resSet.getString("st_reg"));
                    lisTbm_dep.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_dep;
    }

}

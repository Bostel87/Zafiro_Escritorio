/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_carlab;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla tbm_carlab
 * Usada por el BO
 * @author Roberto Flores
 * Guayaquil 17/11/2011
 */
public class Tbm_carlabDAO implements Tbm_carlabDAOInterface{


    /**
     * Retorna el máximo id de la tabla Tbm_carlab.
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
            strSql = "Select max(co_car) as maxId from tbm_carlab";
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
     * Graba en las tablas tbm_carlab.
     * @param connection
     * @param tbm_carlab Objeto POJO que contiene los datos de la tabla tbm_carlab.
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_carlab tbm_carlab) throws Exception {
        String strSql = null;
        PreparedStatement preSta = null;

         try{
             strSql = "insert into tbm_carlab(co_car,tx_nomcar,co_comsec,tx_codcomsec,tx_nomcarcomsec,nd_minsec,tx_obs1, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) ";
             strSql += "values(?,?,?,?,?,?,?,?,?,?,?,?)";

             preSta = connection.prepareStatement(strSql);

             if(tbm_carlab.getIntCo_Car()>0){
                preSta.setInt(1, tbm_carlab.getIntCo_Car());
             }else{
                 preSta.setNull(1, Types.INTEGER);
             }
             if(tbm_carlab.getStrTx_NomCar()!=null){
                preSta.setString(2, tbm_carlab.getStrTx_NomCar());
             }else{
                 preSta.setNull(2, Types.VARCHAR);
             }
             if(tbm_carlab.getIntCo_ComSec()>0){
                preSta.setInt(3, tbm_carlab.getIntCo_ComSec());
             }else{
                 preSta.setNull(3, Types.INTEGER);
             }
             if(tbm_carlab.getStrTx_CodComSec()!=null){
                preSta.setString(4, tbm_carlab.getStrTx_CodComSec());
             }else{
                 preSta.setNull(4, Types.VARCHAR);
             }
             if(tbm_carlab.getStrTx_NomCarComSec()!=null){
                preSta.setString(5, tbm_carlab.getStrTx_NomCarComSec());
             }else{
                 preSta.setNull(5, Types.VARCHAR);
             }
             if(tbm_carlab.getBigNd_MinSec()!=null){
                preSta.setBigDecimal(6, tbm_carlab.getBigNd_MinSec());
             }else{
                 preSta.setNull(6, Types.NUMERIC);
             }
             if(tbm_carlab.getStrTx_Obs1()!=null){
                preSta.setString(7, tbm_carlab.getStrTx_Obs1());
             }else{
                 preSta.setNull(7, Types.VARCHAR);
             }
             if(tbm_carlab.getStrSt_Reg()!=null){
                preSta.setString(8, tbm_carlab.getStrSt_Reg());
             }else{
                 preSta.setNull(8, Types.VARCHAR);
             }
             if(tbm_carlab.getDatFe_ing()!=null){
                preSta.setTimestamp(9, new Timestamp(tbm_carlab.getDatFe_ing().getTime()));
             }else{
                preSta.setNull(9, Types.TIMESTAMP);
             }
            if(tbm_carlab.getDatFe_ultmod()!=null){
                preSta.setTimestamp(10, new Timestamp(tbm_carlab.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(10, Types.TIMESTAMP);
            }
            if(tbm_carlab.getIntCo_usring()>0){
                preSta.setInt(11, tbm_carlab.getIntCo_usring());
            }else{
                preSta.setNull(11, Types.INTEGER);
            }
            if(tbm_carlab.getIntCo_usrmod()>0){
                preSta.setInt(12, tbm_carlab.getIntCo_usrmod());
            }else{
                preSta.setNull(12, Types.INTEGER);
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
     * Actualiza datos de las tablas tbm_cahortra & tbm_dethortra.
     * @param connection
     * @param tbm_hortra Objeto con los parámetros a actualizar
     * @param opcion entero con el parametro indicando: 1 modificacion, 2 si es eliminacion y anulacion
     * @throws SQLException
     */

    /*
     */
    public void actualizar(Connection connection, Tbm_carlab tbm_carlab) throws Exception {

        String strSql = null;
        PreparedStatement preSta = null;

        try{

            strSql =  "update tbm_carlab ";
            strSql += "set tx_nomcar = ?, ";
            strSql += "co_comsec = ?, ";
            strSql += "tx_codcomsec = ?, ";
            strSql += "tx_nomcarcomsec = ?, ";
            strSql += "nd_minsec = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ? ";
            strSql += "where co_car = ?";

            preSta = connection.prepareStatement(strSql);

            if(tbm_carlab.getStrTx_NomCar()!=null){
                preSta.setString(1, tbm_carlab.getStrTx_NomCar());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            if(tbm_carlab.getIntCo_ComSec()>0){
                preSta.setInt(2, tbm_carlab.getIntCo_ComSec());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            if(tbm_carlab.getStrTx_CodComSec()!=null){
                preSta.setString(3, tbm_carlab.getStrTx_CodComSec());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            if(tbm_carlab.getStrTx_NomCarComSec()!=null){
                preSta.setString(4, tbm_carlab.getStrTx_NomCarComSec());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            if(tbm_carlab.getBigNd_MinSec()!=null){
                preSta.setBigDecimal(5, tbm_carlab.getBigNd_MinSec());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            if(tbm_carlab.getStrTx_Obs1()!=null){
                preSta.setString(6, tbm_carlab.getStrTx_Obs1());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            if(tbm_carlab.getStrSt_Reg()!=null){
                preSta.setString(7, tbm_carlab.getStrSt_Reg());
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }
            if(tbm_carlab.getDatFe_ing()!=null){
                preSta.setTimestamp(8, new Timestamp(tbm_carlab.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(8, Types.TIMESTAMP);
            }
            if(tbm_carlab.getDatFe_ultmod()!=null){
                preSta.setTimestamp(9, new Timestamp(tbm_carlab.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(9, Types.TIMESTAMP);
            }
            if(tbm_carlab.getIntCo_usring()>0){
                preSta.setInt(10, tbm_carlab.getIntCo_usring());
            }else{
                preSta.setNull(10, Types.INTEGER);
            }
            if(tbm_carlab.getIntCo_usrmod()>0){
                preSta.setInt(11, tbm_carlab.getIntCo_usrmod());
            }else{
                preSta.setNull(11, Types.INTEGER);
            }

            preSta.setInt(12, tbm_carlab.getIntCo_Car());

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
     * Consulta si el codigo y nombre horario ya se encuentra registrado en la base
     * @param strNomCar Nombre del cargo a consultar
     * @param intCodCar Código del registro
     * @return Retorna true si ya existe y false caso contrario
     * @throws Exception
     */
    public boolean consultarCodNomCarLab(Connection connection, String strNomCar, int intCodCar) throws SQLException {

        boolean blnRep = false;
        String strSql = null;
        int intNumRep = 0;
        PreparedStatement preSta = null;
        ResultSet resSet = null;

        try{
            strSql = "select 1 as flag from tbm_carlab where tx_nomcar = ? and co_car != ? and st_reg != 'I'";

            preSta = connection.prepareStatement(strSql);
            preSta.setString(1, strNomCar);
            preSta.setInt(2, intCodCar);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                intNumRep = resSet.getInt("flag");
                blnRep = (intNumRep == 1);
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return blnRep;
    }

    /**
     * Consulta general de la tabla tbm_carlab aplicando pagineo
     * @param connection
     * @param tbm_carlab Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_carlab con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_carlab> consultarLisPag(Connection connection, Tbm_carlab tbm_carlab, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_carlab> lisTbm_carlab = null;

        try{
            strSql = "Select * from tbm_carlab ";
            strSql += "where (? is null or co_car = ?) ";
            strSql += "and (? is null or lower(tx_nomcar) like lower(?)) ";
            strSql += "and (? is null or co_comsec = ?) ";
            strSql += "and (? is null or lower(tx_codcomsec) like lower(?)) ";
            strSql += "and (? is null or lower(tx_nomcarcomsec) like lower(?)) ";
            strSql += "and (? is null or nd_minsec = ?) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            //strSql += "and (? is null or fe_ing = ?) ";
            //strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_comsec ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_car
            if(tbm_carlab.getIntCo_Car()>0){
                preSta.setInt(1, tbm_carlab.getIntCo_Car());
                preSta.setInt(2, tbm_carlab.getIntCo_Car());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            //tx_nomcar
            if(tbm_carlab.getStrTx_NomCar()!=null){
                preSta.setString(3, tbm_carlab.getStrTx_NomCar().replace('*', '%'));
                preSta.setString(4, tbm_carlab.getStrTx_NomCar().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            //co_comsec

            if(tbm_carlab.getIntCo_ComSec()>0){
                preSta.setInt(5, tbm_carlab.getIntCo_ComSec());
                preSta.setInt(6, tbm_carlab.getIntCo_ComSec());
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }


            //tx_codcomsec
            if(tbm_carlab.getStrTx_CodComSec()!=null){
                preSta.setString(7, tbm_carlab.getStrTx_CodComSec().replace('*', '%'));
                preSta.setString(8, tbm_carlab.getStrTx_CodComSec().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }

            //tx_nomcarcomsec
            if(tbm_carlab.getStrTx_NomCarComSec()!=null){
                preSta.setString(9, tbm_carlab.getStrTx_NomCarComSec().replace('*', '%'));
                preSta.setString(10, tbm_carlab.getStrTx_NomCarComSec().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }

            //nd_minsec
            if(tbm_carlab.getBigNd_MinSec()!=null&&tbm_carlab.getBigNd_MinSec().longValue()>0){
                preSta.setBigDecimal(11, tbm_carlab.getBigNd_MinSec());
                preSta.setBigDecimal(12, tbm_carlab.getBigNd_MinSec());
            }else{
                preSta.setNull(11, Types.NUMERIC);
                preSta.setNull(12, Types.NUMERIC);
            }

            //tx_obs1
            if(tbm_carlab.getStrTx_Obs1()!=null){
                preSta.setString(13, tbm_carlab.getStrTx_Obs1());
                preSta.setString(14, tbm_carlab.getStrTx_Obs1());
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }

            //st_reg
            if(tbm_carlab.getStrSt_Reg()!=null){
                preSta.setString(15, tbm_carlab.getStrSt_Reg().replace('*', '%'));
                preSta.setString(16, tbm_carlab.getStrSt_Reg().replace('*', '%'));
            }else{
                preSta.setNull(15, Types.VARCHAR);
                preSta.setNull(16, Types.VARCHAR);
            }

            //fe_ing 7
            /*if(tbm_comsec.getDatFe_ing()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_comsec.getDatFe_ing().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_comsec.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }

            //fe_ultmod 8
            if(tbm_comsec.getDatFe_ultmod()!=null){
                preSta.setTimestamp(15, new Timestamp(tbm_comsec.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(16, new Timestamp(tbm_comsec.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(15, Types.TIMESTAMP);
                preSta.setNull(16, Types.TIMESTAMP);
            }*/

            //co_usring 9
            if(tbm_carlab.getIntCo_usring()>0){
                preSta.setInt(17, tbm_carlab.getIntCo_usring());
                preSta.setInt(18, tbm_carlab.getIntCo_usring());
            }else{
                preSta.setNull(17, Types.INTEGER);
                preSta.setNull(18, Types.INTEGER);
            }

            //co_usrmod 10
            if(tbm_carlab.getIntCo_usrmod()>0){
                preSta.setInt(19, tbm_carlab.getIntCo_usrmod());
                preSta.setInt(20, tbm_carlab.getIntCo_usrmod());
            }else{
                preSta.setNull(19, Types.INTEGER);
                preSta.setNull(20, Types.INTEGER);
            }

            //limit 11
            if(intCanReg > 0 ){
                preSta.setInt(21, intCanReg);
            }else{
                preSta.setNull(21, Types.INTEGER);
            }

            //offset 12
            preSta.setInt(22, intRegIni);

            resSet = preSta.executeQuery();

            PreparedStatement preSta2 = null;
            ResultSet resSet2 = null;
            if(resSet.next()){
                lisTbm_carlab = new ArrayList<Tbm_carlab>();
                do{
                    Tbm_carlab tmp = new Tbm_carlab();
                    tmp.setIntCo_Car(resSet.getInt("co_car"));
                    tmp.setStrTx_NomCar(resSet.getString("tx_nomcar"));
                    tmp.setIntCo_ComSec(resSet.getInt("co_comsec"));
                    tmp.setStrTx_CodComSec(resSet.getString("tx_codcomsec"));
                    tmp.setStrTx_NomCarComSec(resSet.getString("tx_nomcarcomsec"));
                    tmp.setBigNd_MinSec(resSet.getBigDecimal("nd_minsec"));
                    tmp.setStrTx_Obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_Reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    String strSqlComSec = "select distinct tx_nomcomsec from tbm_comsec where co_comsec = " + tmp.getIntCo_ComSec();
                    preSta2 = connection.prepareStatement(strSqlComSec);
                    resSet2 = preSta2.executeQuery();
                    if(resSet2.next()){
                        tmp.setStrTx_NomComSec(resSet2.getString("tx_nomcomsec"));
                    }
                    resSet2.close();preSta2.close();
                    lisTbm_carlab.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_carlab;
    }
}
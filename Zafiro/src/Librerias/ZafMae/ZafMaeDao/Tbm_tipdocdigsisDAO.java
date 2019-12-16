
package Librerias.ZafMae.ZafMaeDao;

import Librerias.ZafMae.ZafMaePoj.Tbm_tipdocdigsis;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla tbm_tipdocdigsis
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 17/05/2011
 */
public class Tbm_tipdocdigsisDAO implements Tbm_tipdocdigsisDAOInterface {

    /**
     * Retorna el máximo id de la tabla tbm_tipdocdigsis
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
            strSql = "Select max(co_tipdocdig) as maxId from tbm_tipdocdigsis";
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
     * Graba en la tabla tbm_tipdocdigsis
     * @param connection
     * @param tbm_tipdocdigsis Objeto POJO que contiene los datos de la tabla tbm_tipdocdigsis
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_tipdocdigsis(co_tipdocdig,tx_descor,tx_deslar,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod) ";
            strSql += "values (?,?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_tipdocdig 1
            if(tbm_tipdocdigsis.getIntCo_tipdocdig()>0){
                preSta.setInt(1, tbm_tipdocdigsis.getIntCo_tipdocdig());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_descor 2
            if(tbm_tipdocdigsis.getStrTx_descor()!=null){
                preSta.setString(2, tbm_tipdocdigsis.getStrTx_descor());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_deslar 3
            if(tbm_tipdocdigsis.getStrTx_deslar()!=null){
                preSta.setString(3, tbm_tipdocdigsis.getStrTx_deslar());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //tx_obs1 4
            if(tbm_tipdocdigsis.getStrTx_obs1()!=null){
                preSta.setString(4, tbm_tipdocdigsis.getStrTx_obs1());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipdocdigsis.getStrSt_reg()!=null){
                preSta.setString(5, tbm_tipdocdigsis.getStrSt_reg());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //fe_ing 6
            if(tbm_tipdocdigsis.getDatFe_ing()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_tipdocdigsis.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipdocdigsis.getDatFe_ultmod()!=null){
                preSta.setTimestamp(7, new Timestamp(tbm_tipdocdigsis.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(7, Types.TIMESTAMP);
            }
            //co_usring 8
            if(tbm_tipdocdigsis.getIntCo_usring()>0){
                preSta.setInt(8, tbm_tipdocdigsis.getIntCo_usring());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipdocdigsis.getIntCo_usrmod()>0){
                preSta.setInt(9, tbm_tipdocdigsis.getIntCo_usrmod());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_tipdocdigsis
     * @param connection
     * @param tbm_tipdocdigsis Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "update tbm_tipdocdigsis ";
            strSql += "set tx_descor = ?, ";
            strSql += "tx_deslar = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ? ";
            strSql += "where co_tipdocdig = ? ";

            preSta = connection.prepareStatement(strSql);
            //tx_descor 1
            if(tbm_tipdocdigsis.getStrTx_descor()!=null){
                preSta.setString(1, tbm_tipdocdigsis.getStrTx_descor());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //tx_deslar 2
            if(tbm_tipdocdigsis.getStrTx_deslar()!=null){
                preSta.setString(2, tbm_tipdocdigsis.getStrTx_deslar());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_obs1 3
            if(tbm_tipdocdigsis.getStrTx_obs1()!=null){
                preSta.setString(3, tbm_tipdocdigsis.getStrTx_obs1());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //st_reg 4
            if(tbm_tipdocdigsis.getStrSt_reg()!=null){
                preSta.setString(4, tbm_tipdocdigsis.getStrSt_reg());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //fe_ing 5
            if(tbm_tipdocdigsis.getDatFe_ing()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_tipdocdigsis.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            //fe_ultmod 6
            if(tbm_tipdocdigsis.getDatFe_ultmod()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_tipdocdigsis.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //co_usring 7
            if(tbm_tipdocdigsis.getIntCo_usring()>0){
                preSta.setInt(7, tbm_tipdocdigsis.getIntCo_usring());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }
            //co_usrmod 8
            if(tbm_tipdocdigsis.getIntCo_usrmod()>0){
                preSta.setInt(8, tbm_tipdocdigsis.getIntCo_usrmod());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }
            //co_tipdocdig 9
            if(tbm_tipdocdigsis.getIntCo_tipdocdig()>0){
                preSta.setInt(9, tbm_tipdocdigsis.getIntCo_tipdocdig());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_tipdocdigsis
     * @param connection
     * @param tbm_tipdocdigsis Objeto con los parámetros a actualizar
     * @return
     * @throws SQLException
     */
    public List<Tbm_tipdocdigsis> consultarLisGen(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tipdocdigsis> lisTbm_tipdocdigsis = null;

        try{
            strSql = "select * from tbm_tipdocdigsis ";
            strSql += "where (? is null or co_tipdocdig = ?) ";
            strSql += "and (? is null or tx_descor like ?) ";
            strSql += "and (? is null or tx_deslar like ?) ";
            strSql += "and (? is null or tx_obs1 like ?) ";
            strSql += "and (? is null or st_reg = ?) ";
            strSql += "and (?::timestamp without time zone is null or fe_ing = ?::timestamp without time zone) ";
            strSql += "and (?::timestamp without time zone is null or fe_ultmod = ?::timestamp without time zone) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_tipdocdig";

            preSta = connection.prepareStatement(strSql);
            //co_tipdocdig 1
            if(tbm_tipdocdigsis.getIntCo_tipdocdig()>0){
                preSta.setInt(1, tbm_tipdocdigsis.getIntCo_tipdocdig());
                preSta.setInt(2, tbm_tipdocdigsis.getIntCo_tipdocdig());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_descor 2
            if(tbm_tipdocdigsis.getStrTx_descor()!=null){
                preSta.setString(3, tbm_tipdocdigsis.getStrTx_descor());
                preSta.setString(4, tbm_tipdocdigsis.getStrTx_descor());
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_deslar 3
            if(tbm_tipdocdigsis.getStrTx_deslar()!=null){
                preSta.setString(5, tbm_tipdocdigsis.getStrTx_deslar());
                preSta.setString(6, tbm_tipdocdigsis.getStrTx_deslar());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_obs1 4
            if(tbm_tipdocdigsis.getStrTx_obs1()!=null){
                preSta.setString(7, tbm_tipdocdigsis.getStrTx_obs1());
                preSta.setString(8, tbm_tipdocdigsis.getStrTx_obs1());
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipdocdigsis.getStrSt_reg()!=null){
                preSta.setString(9, tbm_tipdocdigsis.getStrSt_reg());
                preSta.setString(10, tbm_tipdocdigsis.getStrSt_reg());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //fe_ing 6
            if(tbm_tipdocdigsis.getDatFe_ing()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_tipdocdigsis.getDatFe_ing().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_tipdocdigsis.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipdocdigsis.getDatFe_ultmod()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_tipdocdigsis.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_tipdocdigsis.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }
            //co_usring 8
            if(tbm_tipdocdigsis.getIntCo_usring()>0){
                preSta.setInt(15, tbm_tipdocdigsis.getIntCo_usring());
                preSta.setInt(16, tbm_tipdocdigsis.getIntCo_usring());
            }else{
                preSta.setNull(15, Types.INTEGER);
                preSta.setNull(16, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipdocdigsis.getIntCo_usrmod()>0){
                preSta.setInt(17, tbm_tipdocdigsis.getIntCo_usrmod());
                preSta.setInt(18, tbm_tipdocdigsis.getIntCo_usrmod());
            }else{
                preSta.setNull(17, Types.INTEGER);
                preSta.setNull(18, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tipdocdigsis = new ArrayList<Tbm_tipdocdigsis>();
                do{
                   Tbm_tipdocdigsis tmp = new Tbm_tipdocdigsis();
                   tmp.setIntCo_tipdocdig(resSet.getInt("co_tipdocdig"));
                   tmp.setStrTx_descor(resSet.getString("tx_descor"));
                   tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                   tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                   tmp.setStrSt_reg(resSet.getString("st_reg"));
                   tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                   tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                   tmp.setIntCo_usring(resSet.getInt("co_usring"));
                   tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                   
                   lisTbm_tipdocdigsis.add(tmp);
                }while(resSet.next());
            }

        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tipdocdigsis;
    }

    /**
     * Consulta general de la tabla tbm_tipdocdigsis aplicando pagineo
     * @param connection
     * @param tbm_tipdocdigsis Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_comsec con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_tipdocdigsis> consultarLisPag(Connection connection, Tbm_tipdocdigsis tbm_tipdocdigsis, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tipdocdigsis> lisTbm_tipdocdigsis = null;

        try{
            strSql = "select * from tbm_tipdocdigsis ";
            strSql += "where (? is null or co_tipdocdig = ?) ";
            strSql += "and (? is null or tx_descor like ?) ";
            strSql += "and (? is null or tx_deslar like ?) ";
            strSql += "and (? is null or tx_obs1 like ?) ";
            strSql += "and (? is null or st_reg = ?) ";
            //strSql += "and (? is null or fe_ing = ?) ";
            //strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_tipdocdig ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_tipdocdig 1
            if(tbm_tipdocdigsis.getIntCo_tipdocdig()>0){
                preSta.setInt(1, tbm_tipdocdigsis.getIntCo_tipdocdig());
                preSta.setInt(2, tbm_tipdocdigsis.getIntCo_tipdocdig());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_descor 2
            if(tbm_tipdocdigsis.getStrTx_descor()!=null){
                preSta.setString(3, tbm_tipdocdigsis.getStrTx_descor());
                preSta.setString(4, tbm_tipdocdigsis.getStrTx_descor());
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_deslar 3
            if(tbm_tipdocdigsis.getStrTx_deslar()!=null){
                preSta.setString(5, tbm_tipdocdigsis.getStrTx_deslar());
                preSta.setString(6, tbm_tipdocdigsis.getStrTx_deslar());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_obs1 4
            if(tbm_tipdocdigsis.getStrTx_obs1()!=null){
                preSta.setString(7, tbm_tipdocdigsis.getStrTx_obs1());
                preSta.setString(8, tbm_tipdocdigsis.getStrTx_obs1());
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipdocdigsis.getStrSt_reg()!=null){
                preSta.setString(9, tbm_tipdocdigsis.getStrSt_reg());
                preSta.setString(10, tbm_tipdocdigsis.getStrSt_reg());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //fe_ing 6
            /*if(tbm_tipdocdigsis.getDatFe_ing()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_tipdocdigsis.getDatFe_ing().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_tipdocdigsis.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipdocdigsis.getDatFe_ultmod()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_tipdocdigsis.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_tipdocdigsis.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }*/
            //co_usring 8
            if(tbm_tipdocdigsis.getIntCo_usring()>0){
                preSta.setInt(11, tbm_tipdocdigsis.getIntCo_usring());
                preSta.setInt(12, tbm_tipdocdigsis.getIntCo_usring());
            }else{
                preSta.setNull(11, Types.INTEGER);
                preSta.setNull(12, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipdocdigsis.getIntCo_usrmod()>0){
                preSta.setInt(13, tbm_tipdocdigsis.getIntCo_usrmod());
                preSta.setInt(14, tbm_tipdocdigsis.getIntCo_usrmod());
            }else{
                preSta.setNull(13, Types.INTEGER);
                preSta.setNull(14, Types.INTEGER);
            }
            //limit 10
            if(intCanReg > 0 ){
                preSta.setInt(15, intCanReg);
            }else{
                preSta.setNull(15, Types.INTEGER);
            }
            //offset 11
            preSta.setInt(16, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tipdocdigsis = new ArrayList<Tbm_tipdocdigsis>();
                do{
                   Tbm_tipdocdigsis tmp = new Tbm_tipdocdigsis();
                   tmp.setIntCo_tipdocdig(resSet.getInt("co_tipdocdig"));
                   tmp.setStrTx_descor(resSet.getString("tx_descor"));
                   tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                   tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                   tmp.setStrSt_reg(resSet.getString("st_reg"));
                   tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                   tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                   tmp.setIntCo_usring(resSet.getInt("co_usring"));
                   tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                   lisTbm_tipdocdigsis.add(tmp);
                }while(resSet.next());
            }

        } 
        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tipdocdigsis;
    }

}

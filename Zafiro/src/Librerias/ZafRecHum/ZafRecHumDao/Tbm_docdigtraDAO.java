
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_docdigtra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla tbm_docdigtra
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 19/05/2011
 */
public class Tbm_docdigtraDAO implements Tbm_docdigtraDAOInterface {

    /**
     * Retorna el máximo id de la tabla tbm_docdigtra
     * @param connection
     * @param intCo_tra codigo de empleado
     * @return Retorna 0 si no hay registros y el máximo id si los hay
     * @throws SQLException
     */
    public int obtenerMaxId(Connection connection, int intCo_tra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        int intMaxId = 0;

        try{
            strSql = "Select max(co_reg) as maxId from tbm_docdigtra where co_tra = ?";
            
            preSta = connection.prepareStatement(strSql);
            preSta.setInt(1, intCo_tra);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                intMaxId = resSet.getInt("maxId");
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return intMaxId;
    }

    /**
     * Graba en la tabla tbm_docdigtra
     * @param connection
     * @param tbm_docdigtra Objeto POJO que contiene los datos de la tabla tbm_docdigtra
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_docdigtra tbm_docdigtra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_docdigtra(co_tra,co_reg,co_tipdocdig,tx_nomarc,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod) ";
            strSql += "values(?,?,?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_docdigtra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_docdigtra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //co_reg 2
            if(tbm_docdigtra.getIntCo_reg()>0){
                preSta.setInt(2, tbm_docdigtra.getIntCo_reg());
            }else{
                preSta.setNull(2, Types.INTEGER);
            }
            //co_tipdocdig 3
            if(tbm_docdigtra.getIntCo_tipdocdig()>0){
                preSta.setInt(3, tbm_docdigtra.getIntCo_tipdocdig());
            }else{
                preSta.setNull(3, Types.INTEGER);
            }
            //tx_nomarc 4
            if(tbm_docdigtra.getStrTx_nomarc()!=null){
                preSta.setString(4, tbm_docdigtra.getStrTx_nomarc());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_obs1 5
            if(tbm_docdigtra.getStrTx_obs1()!=null){
                preSta.setString(5, tbm_docdigtra.getStrTx_obs1());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //st_reg 6
            if(tbm_docdigtra.getStrSt_reg()!=null){
                preSta.setString(6, tbm_docdigtra.getStrSt_reg());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //fe_ing 7
            if(tbm_docdigtra.getDatFe_ing()!=null){
                preSta.setTimestamp(7, new Timestamp(tbm_docdigtra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(7, Types.TIMESTAMP);
            }
            //fe_ultmod 8
            if(tbm_docdigtra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(8, new Timestamp(tbm_docdigtra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(8, Types.TIMESTAMP);
            }
            //co_usring 9
            if(tbm_docdigtra.getIntCo_usring()>0){
                preSta.setInt(9, tbm_docdigtra.getIntCo_usring());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }
            //co_usrmod 10
            if(tbm_docdigtra.getIntCo_usrmod()>0){
                preSta.setInt(10, tbm_docdigtra.getIntCo_usrmod());
            }else{
                preSta.setNull(10, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_docdigtra
     * @param connection
     * @param tbm_docdigtra Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_docdigtra tbm_docdigtra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "update tbm_docdigtra ";
            strSql += "set co_tipdocdig = ?, ";
            strSql += "tx_nomarc = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ? ";
            strSql += "where co_tra = ? ";
            strSql += "and co_reg = ? ";

            preSta = connection.prepareStatement(strSql);
            //co_tipdocdig 1
            if(tbm_docdigtra.getIntCo_tipdocdig()>0){
                preSta.setInt(1, tbm_docdigtra.getIntCo_tipdocdig());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_nomarc 2
            if(tbm_docdigtra.getStrTx_nomarc()!=null){
                preSta.setString(2, tbm_docdigtra.getStrTx_nomarc());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_obs1 3
            if(tbm_docdigtra.getStrTx_obs1()!=null){
                preSta.setString(3, tbm_docdigtra.getStrTx_obs1());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //st_reg 4
            if(tbm_docdigtra.getStrSt_reg()!=null){
                preSta.setString(4, tbm_docdigtra.getStrSt_reg());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //fe_ing 5
            if(tbm_docdigtra.getDatFe_ing()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_docdigtra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            //fe_ultmod 6
            if(tbm_docdigtra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_docdigtra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //co_usring 7
            if(tbm_docdigtra.getIntCo_usring()>0){
                preSta.setInt(7, tbm_docdigtra.getIntCo_usring());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }
            //co_usrmod 8
            if(tbm_docdigtra.getIntCo_usrmod()>0){
                preSta.setInt(8, tbm_docdigtra.getIntCo_usrmod());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }
            //co_tra 9
            if(tbm_docdigtra.getIntCo_tra()>0){
                preSta.setInt(9, tbm_docdigtra.getIntCo_tra());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }
            //co_reg 10
            if(tbm_docdigtra.getIntCo_reg()>0){
                preSta.setInt(10, tbm_docdigtra.getIntCo_reg());
            }else{
                preSta.setNull(10, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_docdigtra
     * @param connection
     * @param tbm_docdigtra Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_docdigtra cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_docdigtra> consultarLisGen(Connection connection, Tbm_docdigtra tbm_docdigtra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_docdigtra> lisTbm_docdigtra = null;

        try {
            strSql = "select * from tbm_docdigtra ";
            strSql += "where (? is null or co_tra = ?) ";
            strSql += "and (? is null or co_reg = ?) ";
            strSql += "and (? is null or co_tipdocdig = ?) ";
            strSql += "and (? is null or tx_nomarc = ?) ";
            strSql += "and (? is null or tx_obs1 = ?) ";
            strSql += "and (? is null or st_reg = ?) ";
            strSql += "and (?::timestamp without time zone is null or fe_ing = ?::timestamp without time zone) ";
            strSql += "and (?::timestamp without time zone is null or fe_ultmod = ?::timestamp without time zone) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_tra, co_reg";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_docdigtra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_docdigtra.getIntCo_tra());
                preSta.setInt(2, tbm_docdigtra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_reg 2
            if(tbm_docdigtra.getIntCo_reg()>0){
                preSta.setInt(3, tbm_docdigtra.getIntCo_reg());
                preSta.setInt(4, tbm_docdigtra.getIntCo_reg());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }
            //co_tipdocdig 3
            if(tbm_docdigtra.getIntCo_tipdocdig()>0){
                preSta.setInt(5, tbm_docdigtra.getIntCo_tipdocdig());
                preSta.setInt(6, tbm_docdigtra.getIntCo_tipdocdig());
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }
            //tx_nomarc 4
            if(tbm_docdigtra.getStrTx_nomarc()!=null){
                preSta.setString(7, tbm_docdigtra.getStrTx_nomarc().replace('*', '%'));
                preSta.setString(8, tbm_docdigtra.getStrTx_nomarc().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_obs1 5
            if(tbm_docdigtra.getStrTx_obs1()!=null){
                preSta.setString(9, tbm_docdigtra.getStrTx_obs1().replace('*', '%'));
                preSta.setString(10, tbm_docdigtra.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //st_reg 6
            if(tbm_docdigtra.getStrSt_reg()!=null){
                preSta.setString(11, tbm_docdigtra.getStrSt_reg());
                preSta.setString(12, tbm_docdigtra.getStrSt_reg());
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //fe_ing 7
            if(tbm_docdigtra.getDatFe_ing()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_docdigtra.getDatFe_ing().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_docdigtra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }
            //fe_ultmod 8
            if(tbm_docdigtra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(15, new Timestamp(tbm_docdigtra.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(16, new Timestamp(tbm_docdigtra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(15, Types.TIMESTAMP);
                preSta.setNull(16, Types.TIMESTAMP);
            }
            //co_usring 9
            if(tbm_docdigtra.getIntCo_usring()>0){
                preSta.setInt(17, tbm_docdigtra.getIntCo_usring());
                preSta.setInt(18, tbm_docdigtra.getIntCo_usring());
            }else{
                preSta.setNull(17, Types.INTEGER);
                preSta.setNull(18, Types.INTEGER);
            }
            //co_usrmod 10
            if(tbm_docdigtra.getIntCo_usrmod()>0){
                preSta.setInt(19, tbm_docdigtra.getIntCo_usrmod());
                preSta.setInt(20, tbm_docdigtra.getIntCo_usrmod());
            }else{
                preSta.setNull(19, Types.INTEGER);
                preSta.setNull(20, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_docdigtra = new ArrayList<Tbm_docdigtra>();
                do{
                    Tbm_docdigtra tmp = new Tbm_docdigtra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setIntCo_reg(resSet.getInt("co_reg"));
                    tmp.setIntCo_tipdocdig(resSet.getInt("co_tipdocdig"));
                    tmp.setStrTx_nomarc(resSet.getString("tx_nomarc"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_docdigtra.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_docdigtra;
    }

    /**
     * Consulta general de la tabla tbm_docdigtra aplicando pagineo
     * @param connection
     * @param tbm_docdigtra Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return lista de tipo Tbm_docdigtra con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_docdigtra> consultarLisPag(Connection connection, Tbm_docdigtra tbm_docdigtra, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_docdigtra> lisTbm_docdigtra = null;

        try {
            strSql = "select * from tbm_docdigtra ";
            strSql += "where (? is null or co_tra = ?) ";
            strSql += "and (? is null or co_reg = ?) ";
            strSql += "and (? is null or co_tipdocdig = ?) ";
            strSql += "and (? is null or tx_nomarc = ?) ";
            strSql += "and (? is null or tx_obs1 = ?) ";
            strSql += "and (? is null or st_reg = ?) ";
            strSql += "and (? is null or fe_ing = ?) ";
            strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_tra, co_reg ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_docdigtra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_docdigtra.getIntCo_tra());
                preSta.setInt(2, tbm_docdigtra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_reg 2
            if(tbm_docdigtra.getIntCo_reg()>0){
                preSta.setInt(3, tbm_docdigtra.getIntCo_reg());
                preSta.setInt(4, tbm_docdigtra.getIntCo_reg());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }
            //co_tipdocdig 3
            if(tbm_docdigtra.getIntCo_tipdocdig()>0){
                preSta.setInt(5, tbm_docdigtra.getIntCo_tipdocdig());
                preSta.setInt(6, tbm_docdigtra.getIntCo_tipdocdig());
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }
            //tx_nomarc 4
            if(tbm_docdigtra.getStrTx_nomarc()!=null){
                preSta.setString(7, tbm_docdigtra.getStrTx_nomarc().replace('*', '%'));
                preSta.setString(8, tbm_docdigtra.getStrTx_nomarc().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_obs1 5
            if(tbm_docdigtra.getStrTx_obs1()!=null){
                preSta.setString(9, tbm_docdigtra.getStrTx_obs1().replace('*', '%'));
                preSta.setString(10, tbm_docdigtra.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //st_reg 6
            if(tbm_docdigtra.getStrSt_reg()!=null){
                preSta.setString(11, tbm_docdigtra.getStrSt_reg());
                preSta.setString(12, tbm_docdigtra.getStrSt_reg());
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //fe_ing 7
            if(tbm_docdigtra.getDatFe_ing()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_docdigtra.getDatFe_ing().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_docdigtra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }
            //fe_ultmod 8
            if(tbm_docdigtra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(15, new Timestamp(tbm_docdigtra.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(16, new Timestamp(tbm_docdigtra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(15, Types.TIMESTAMP);
                preSta.setNull(16, Types.TIMESTAMP);
            }
            //co_usring 9
            if(tbm_docdigtra.getIntCo_usring()>0){
                preSta.setInt(17, tbm_docdigtra.getIntCo_usring());
                preSta.setInt(18, tbm_docdigtra.getIntCo_usring());
            }else{
                preSta.setNull(17, Types.INTEGER);
                preSta.setNull(18, Types.INTEGER);
            }
            //co_usrmod 10
            if(tbm_docdigtra.getIntCo_usrmod()>0){
                preSta.setInt(19, tbm_docdigtra.getIntCo_usrmod());
                preSta.setInt(20, tbm_docdigtra.getIntCo_usrmod());
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

            if(resSet.next()){
                lisTbm_docdigtra = new ArrayList<Tbm_docdigtra>();
                do{
                    Tbm_docdigtra tmp = new Tbm_docdigtra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setIntCo_reg(resSet.getInt("co_reg"));
                    tmp.setIntCo_tipdocdig(resSet.getInt("co_tipdocdig"));
                    tmp.setStrTx_nomarc(resSet.getString("tx_nomarc"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_docdigtra.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_docdigtra;
    }

    public boolean existe(Connection connection, int intCodTra, int intCoTipDocDig) throws SQLException {

        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        boolean blnRes = false;

        try {
            strSql = "select * from tbm_docdigtra ";
            strSql += "where co_tra = ? ";
            //strSql += "and (? is null or co_reg = ?) ";
            strSql += "and co_tipdocdig = ?";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            //if(intCodTra>0){
            preSta.setInt(1, intCodTra);
            //}
            //co_reg 2
            preSta.setInt(2, intCoTipDocDig);
            resSet = preSta.executeQuery();

            if(resSet.next()){
                blnRes = true;
            }

       }finally {
            try{preSta.close();}catch(Throwable ignore){}
       }

       return blnRes;
       
    }

}

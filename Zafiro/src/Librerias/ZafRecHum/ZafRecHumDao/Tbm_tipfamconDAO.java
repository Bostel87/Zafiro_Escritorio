
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipfamcon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla tbm_tipfamcon
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 15/04/2011
 */
public class Tbm_tipfamconDAO implements Tbm_tipfamconDAOInterface {

    /**
     * Retorna el máximo id de la tabla tbm_tipfamcon.
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
            strSql = "Select max(co_tipfamcon) as maxId from tbm_tipfamcon";
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
     * Graba en la tabla tbm_tipfamcon.
     * @param connection
     * @param tbm_tipfamcon Objeto POJO que contiene los datos de la tabla tbm_tipfamcon
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_tipfamcon(co_tipfamcon,tx_deslar,tx_tipcarfam,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod) ";
            strSql += "values(?,?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_tipfamcon 1
            if(tbm_tipfamcon.getIntCo_tipfamcon()>0){
                preSta.setInt(1, tbm_tipfamcon.getIntCo_tipfamcon());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_deslar 2
            if(tbm_tipfamcon.getStrTx_deslar()!=null){
                preSta.setString(2, tbm_tipfamcon.getStrTx_deslar());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_tipcarfam 3
            if(tbm_tipfamcon.getStrTx_tipcarfam()!=null){
                preSta.setString(3, tbm_tipfamcon.getStrTx_tipcarfam());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //tx_obs1 4
            if(tbm_tipfamcon.getStrTx_obs1()!=null){
                preSta.setString(4, tbm_tipfamcon.getStrTx_obs1());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipfamcon.getStrSt_reg()!=null){
                preSta.setString(5, tbm_tipfamcon.getStrSt_reg());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //fe_ing 6
            if(tbm_tipfamcon.getDatFe_ing()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_tipfamcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipfamcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(7, new Timestamp(tbm_tipfamcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(7, Types.TIMESTAMP);
            }
            //co_usring 8
            if(tbm_tipfamcon.getIntCo_usring()>0){
                preSta.setInt(8, tbm_tipfamcon.getIntCo_usring());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipfamcon.getIntCo_usrmod()>0){
                preSta.setInt(9, tbm_tipfamcon.getIntCo_usrmod());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_tipfamcon.
     * @param connection
     * @param tbm_tipfamcon Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{

            strSql = "update tbm_tipfamcon ";
            strSql += "set tx_deslar = ?, ";
            strSql += "tx_tipcarfam = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ? ";
            strSql += "where co_tipfamcon = ?";

            preSta = connection.prepareStatement(strSql);

            //tx_deslar 1
            if(tbm_tipfamcon.getStrTx_deslar()!=null){
                preSta.setString(1, tbm_tipfamcon.getStrTx_deslar());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //tx_tipcarfam 2
            if(tbm_tipfamcon.getStrTx_tipcarfam()!=null){
                preSta.setString(2, tbm_tipfamcon.getStrTx_tipcarfam());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_obs1 3
            if(tbm_tipfamcon.getStrTx_obs1()!=null){
                preSta.setString(3, tbm_tipfamcon.getStrTx_obs1());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //st_reg 4
            if(tbm_tipfamcon.getStrSt_reg()!=null){
                preSta.setString(4, tbm_tipfamcon.getStrSt_reg());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //fe_ing 5
            if(tbm_tipfamcon.getDatFe_ing()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_tipfamcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            //fe_ultmod 6
            if(tbm_tipfamcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_tipfamcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //co_usring 7
            if(tbm_tipfamcon.getIntCo_usring()>0){
                preSta.setInt(7, tbm_tipfamcon.getIntCo_usring());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }
            //co_usrmod 8
            if(tbm_tipfamcon.getIntCo_usrmod()>0){
                preSta.setInt(8, tbm_tipfamcon.getIntCo_usrmod());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }
            //co_tipfamcon 9
            if(tbm_tipfamcon.getIntCo_tipfamcon()>0){
                preSta.setInt(9, tbm_tipfamcon.getIntCo_tipfamcon());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_tipfamcon
     * @param connection
     * @param tbm_tipfamcon Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_tipfamcon cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_tipfamcon> consultarLisGen(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tipfamcon> lisTbm_tipfamcon = null;

        try{
            strSql = "Select * from tbm_tipfamcon ";
            strSql += "where (? is null or co_tipfamcon = ?) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tipcarfam) like lower(?)) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) like lower(?)) ";
            strSql += "and (?::timestamp without time zone is null or fe_ing = ?::timestamp without time zone) ";
            strSql += "and (?::timestamp without time zone is null or fe_ultmod = ?::timestamp without time zone) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?)";
            strSql += "order by co_tipfamcon ";

            preSta = connection.prepareStatement(strSql);
            //co_tipfamcon 1
            if(tbm_tipfamcon.getIntCo_tipfamcon()>0){
                preSta.setInt(1, tbm_tipfamcon.getIntCo_tipfamcon());
                preSta.setInt(2, tbm_tipfamcon.getIntCo_tipfamcon());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_deslar 2
            if(tbm_tipfamcon.getStrTx_deslar()!=null){
                preSta.setString(3, tbm_tipfamcon.getStrTx_deslar().replace('*', '%'));
                preSta.setString(4, tbm_tipfamcon.getStrTx_deslar().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_tipcarfam 3
            if(tbm_tipfamcon.getStrTx_tipcarfam()!=null){
                preSta.setString(5, tbm_tipfamcon.getStrTx_tipcarfam().replace('*', '%'));
                preSta.setString(6, tbm_tipfamcon.getStrTx_tipcarfam().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_obs1 4
            if(tbm_tipfamcon.getStrTx_obs1()!=null){
                preSta.setString(7, tbm_tipfamcon.getStrTx_obs1().replace('*', '%'));
                preSta.setString(8, tbm_tipfamcon.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipfamcon.getStrSt_reg()!=null){
                preSta.setString(9, tbm_tipfamcon.getStrSt_reg());
                preSta.setString(10, tbm_tipfamcon.getStrSt_reg());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //fe_ing 6
            if(tbm_tipfamcon.getDatFe_ing()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_tipfamcon.getDatFe_ing().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_tipfamcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipfamcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_tipfamcon.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_tipfamcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }
            //co_usring 8
            if(tbm_tipfamcon.getIntCo_usring()>0){
                preSta.setInt(15, tbm_tipfamcon.getIntCo_usring());
                preSta.setInt(16, tbm_tipfamcon.getIntCo_usring());
            }else{
                preSta.setNull(15, Types.INTEGER);
                preSta.setNull(16, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipfamcon.getIntCo_usrmod()>0){
                preSta.setInt(17, tbm_tipfamcon.getIntCo_usrmod());
                preSta.setInt(18, tbm_tipfamcon.getIntCo_usrmod());
            }else{
                preSta.setNull(17, Types.INTEGER);
                preSta.setNull(18, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tipfamcon = new ArrayList<Tbm_tipfamcon>();
                do{
                    Tbm_tipfamcon tmp = new Tbm_tipfamcon();
                    tmp.setIntCo_tipfamcon(resSet.getInt("co_tipfamcon"));
                    tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                    tmp.setStrTx_tipcarfam(resSet.getString("tx_tipcarfam"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_tipfamcon.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tipfamcon;
    }

    /**
     * Consulta general de la tabla tbm_tipfamcon aplicando pagineo
     * @param connection
     * @param tbm_tipfamcon Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_tipfamcon con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_tipfamcon> consultarLisPag(Connection connection, Tbm_tipfamcon tbm_tipfamcon, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tipfamcon> lisTbm_tipfamcon = null;

        try{
            strSql = "Select * from tbm_tipfamcon ";
            strSql += "where (? is null or co_tipfamcon = ?) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tipcarfam) like lower(?)) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) like lower(?)) ";
            //strSql += "and (? is null or fe_ing = ?) ";
            //strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?)";
            strSql += "order by co_tipfamcon ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_tipfamcon 1
            if(tbm_tipfamcon.getIntCo_tipfamcon()>0){
                preSta.setInt(1, tbm_tipfamcon.getIntCo_tipfamcon());
                preSta.setInt(2, tbm_tipfamcon.getIntCo_tipfamcon());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_deslar 2
            if(tbm_tipfamcon.getStrTx_deslar()!=null){
                preSta.setString(3, tbm_tipfamcon.getStrTx_deslar().replace('*', '%'));
                preSta.setString(4, tbm_tipfamcon.getStrTx_deslar().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_tipcarfam 3
            if(tbm_tipfamcon.getStrTx_tipcarfam()!=null){
                preSta.setString(5, tbm_tipfamcon.getStrTx_tipcarfam().replace('*', '%'));
                preSta.setString(6, tbm_tipfamcon.getStrTx_tipcarfam().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_obs1 4
            if(tbm_tipfamcon.getStrTx_obs1()!=null){
                preSta.setString(7, tbm_tipfamcon.getStrTx_obs1().replace('*', '%'));
                preSta.setString(8, tbm_tipfamcon.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipfamcon.getStrSt_reg()!=null){
                preSta.setString(9, tbm_tipfamcon.getStrSt_reg());
                preSta.setString(10, tbm_tipfamcon.getStrSt_reg());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //fe_ing 6
            /*if(tbm_tipfamcon.getDatFe_ing()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_tipfamcon.getDatFe_ing().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_tipfamcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipfamcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_tipfamcon.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_tipfamcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }*/
            //co_usring 8
            if(tbm_tipfamcon.getIntCo_usring()>0){
                preSta.setInt(11, tbm_tipfamcon.getIntCo_usring());
                preSta.setInt(12, tbm_tipfamcon.getIntCo_usring());
            }else{
                preSta.setNull(11, Types.INTEGER);
                preSta.setNull(12, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipfamcon.getIntCo_usrmod()>0){
                preSta.setInt(13, tbm_tipfamcon.getIntCo_usrmod());
                preSta.setInt(14, tbm_tipfamcon.getIntCo_usrmod());
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
                lisTbm_tipfamcon = new ArrayList<Tbm_tipfamcon>();
                do{
                    Tbm_tipfamcon tmp = new Tbm_tipfamcon();
                    tmp.setIntCo_tipfamcon(resSet.getInt("co_tipfamcon"));
                    tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                    tmp.setStrTx_tipcarfam(resSet.getString("tx_tipcarfam"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_tipfamcon.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tipfamcon;
    }

    public String consultarTipoCargaFamiliar(Connection connection, Tbm_tipfamcon tbm_tipfamcon) throws SQLException {
        String strSql = null;
        Statement sta = null;
        ResultSet resSet = null;
        String strTx_TipCarFam = null;

        try{
            strSql = "Select tx_tipcarfam from tbm_tipfamcon where co_tipfamcon = " + tbm_tipfamcon.getIntCo_tipfamcon();
            sta = connection.createStatement();
            resSet = sta.executeQuery(strSql);

            if(resSet.next()){
                strTx_TipCarFam = resSet.getString("tx_tipcarfam");
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{sta.close();}catch(Throwable ignore){}
        }

        return strTx_TipCarFam;
    }

}

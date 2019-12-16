
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipcon;
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
 * Objeto de Acceso a Datos de la tabla tbm_comsec
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 29/03/2011
 */
public class Tbm_tipconDAO implements Tbm_tipconDAOInterface {

    /**
     * Retorna el máximo id de la tabla Tbm_tipcon.
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
            strSql = "Select max(co_tipCon) as maxId from tbm_tipcon";
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
     * Graba en la tabla tbm_tipcon.
     * @param connection
     * @param tbm_tipcon Objeto POJO que contiene los datos de la tabla tbm_tipcon
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_tipcon tbm_tipcon) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_tipcon(co_tipcon,tx_deslar,ne_mescon,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod,ne_diapru) ";
            strSql += "values(?,?,?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_tipcon 1
            if(tbm_tipcon.getIntCo_tipcon()>0){
                preSta.setInt(1, tbm_tipcon.getIntCo_tipcon());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_deslar 2
            if(tbm_tipcon.getStrTx_deslar()!=null){
                preSta.setString(2, tbm_tipcon.getStrTx_deslar());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //ne_mescon 3
            if(tbm_tipcon.getIntNe_mescon()>0){
                preSta.setInt(3, tbm_tipcon.getIntNe_mescon());
            }else{
                preSta.setNull(3, Types.INTEGER);
            }
            //tx_obs1 4
            if(tbm_tipcon.getStrTx_obs1()!=null){
                preSta.setString(4, tbm_tipcon.getStrTx_obs1());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipcon.getStrSt_reg()!=null){
                preSta.setString(5, tbm_tipcon.getStrSt_reg());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //fe_ing 6
            if(tbm_tipcon.getDatFe_ing()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_tipcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(7, new Timestamp(tbm_tipcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(7, Types.TIMESTAMP);
            }
            //co_usring 8
            if(tbm_tipcon.getIntCo_usring()>0){
                preSta.setInt(8, tbm_tipcon.getIntCo_usring());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipcon.getIntCo_usrmod()>0){
                preSta.setInt(9, tbm_tipcon.getIntCo_usrmod());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }
            //ne_diapru 10
            if(tbm_tipcon.getIntNe_diapru()>0){
                preSta.setInt(10, tbm_tipcon.getIntNe_diapru());
            }else{
                preSta.setNull(10, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_tipcon.
     * @param connection
     * @param tbm_tipcon Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_tipcon tbm_tipcon) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "update tbm_tipcon ";
            strSql += "set tx_deslar = ?, ";
            strSql += "ne_mescon = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ?, ";
            strSql += "ne_diapru = ? ";
            strSql += "where co_tipcon = ?";

            preSta = connection.prepareStatement(strSql);
            //tx_deslar 1
            if(tbm_tipcon.getStrTx_deslar()!=null){
                preSta.setString(1, tbm_tipcon.getStrTx_deslar());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //ne_mescon 2
            if(tbm_tipcon.getIntNe_mescon()>0){
                preSta.setInt(2, tbm_tipcon.getIntNe_mescon());
            }else{
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_obs1 3
            if(tbm_tipcon.getStrTx_obs1()!=null){
                preSta.setString(3, tbm_tipcon.getStrTx_obs1());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //st_reg 4
            if(tbm_tipcon.getStrSt_reg()!=null){
                preSta.setString(4, tbm_tipcon.getStrSt_reg());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //fe_ing 5
            if(tbm_tipcon.getDatFe_ing()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_tipcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            //fe_ultmod 6
            if(tbm_tipcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_tipcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //co_usring 7
            if(tbm_tipcon.getIntCo_usring()>0){
                preSta.setInt(7, tbm_tipcon.getIntCo_usring());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }
            //co_usrmod 8
            if(tbm_tipcon.getIntCo_usrmod()>0){
                preSta.setInt(8, tbm_tipcon.getIntCo_usrmod());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }
            //ne_diapru 9
            if(tbm_tipcon.getIntNe_diapru()>0){
                preSta.setInt(9, tbm_tipcon.getIntNe_diapru());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }
            //co_tipcon 10
            if(tbm_tipcon.getIntCo_tipcon()>0){
                preSta.setInt(10, tbm_tipcon.getIntCo_tipcon());
            }else{
                preSta.setNull(10, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_tipcon
     * @param connection
     * @param tbm_tipcon Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_tipcon cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_tipcon> consultarLisGen(Connection connection, Tbm_tipcon tbm_tipcon) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tipcon> lisTbm_tipcon = null;

        try{
            strSql = "Select * from tbm_tipcon ";
            strSql += "where (? is null or co_tipcon = ?) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or ne_mescon = ? ) " ;
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) like lower(?)) ";
            strSql += "and (? is null or fe_ing = ?) ";
            strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "and (? is null or ne_diapru = ?) ";
            strSql += "order by co_tipcon ";

            preSta = connection.prepareStatement(strSql);
            //co_tipcon 1
            if(tbm_tipcon.getIntCo_tipcon()>0){
                preSta.setInt(1, tbm_tipcon.getIntCo_tipcon());
                preSta.setInt(2, tbm_tipcon.getIntCo_tipcon());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_deslar 2
            if(tbm_tipcon.getStrTx_deslar()!=null){
                preSta.setString(3, tbm_tipcon.getStrTx_deslar().replace('*', '%'));
                preSta.setString(4, tbm_tipcon.getStrTx_deslar().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //ne_mescon 3
            if(tbm_tipcon.getIntNe_mescon()>0){
                preSta.setInt(5, tbm_tipcon.getIntNe_mescon());
                preSta.setInt(6, tbm_tipcon.getIntNe_mescon());
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }
            //tx_obs1 4
            if(tbm_tipcon.getStrTx_obs1()!=null){
                preSta.setString(7, tbm_tipcon.getStrTx_obs1().replace('*', '%'));
                preSta.setString(8, tbm_tipcon.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipcon.getStrSt_reg()!=null){
                preSta.setString(9, tbm_tipcon.getStrSt_reg());
                preSta.setString(10, tbm_tipcon.getStrSt_reg());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //fe_ing 6
            if(tbm_tipcon.getDatFe_ing()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_tipcon.getDatFe_ing().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_tipcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_tipcon.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_tipcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }
            //co_usring 8
            if(tbm_tipcon.getIntCo_usring()>0){
                preSta.setInt(15, tbm_tipcon.getIntCo_usring());
                preSta.setInt(16, tbm_tipcon.getIntCo_usring());
            }else{
                preSta.setNull(15, Types.INTEGER);
                preSta.setNull(16, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipcon.getIntCo_usrmod()>0){
                preSta.setInt(17, tbm_tipcon.getIntCo_usrmod());
                preSta.setInt(18, tbm_tipcon.getIntCo_usrmod());
            }else{
                preSta.setNull(17, Types.INTEGER);
                preSta.setNull(18, Types.INTEGER);
            }
            //ne_diapru 10
            if(tbm_tipcon.getIntNe_diapru()>0){
                preSta.setInt(19, tbm_tipcon.getIntNe_diapru());
                preSta.setInt(20, tbm_tipcon.getIntNe_diapru());
            }else{
                preSta.setNull(19, Types.INTEGER);
                preSta.setNull(20, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tipcon = new ArrayList<Tbm_tipcon>();
                do{
                    Tbm_tipcon tmp = new Tbm_tipcon();
                    tmp.setIntCo_tipcon(resSet.getInt("co_tipcon"));
                    tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                    tmp.setIntNe_mescon(resSet.getInt("ne_mescon"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    tmp.setIntNe_diapru(resSet.getInt("ne_diapru"));

                    lisTbm_tipcon.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tipcon;
    }

    /**
     * Consulta general de la tabla tbm_tipcon aplicando pagineo
     * @param connection
     * @param tbm_comsec Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_comsec con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_tipcon> consultarLisPag(Connection connection, Tbm_tipcon tbm_tipcon, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tipcon> lisTbm_tipcon = null;

        try{
            strSql = "Select * from tbm_tipcon ";
            strSql += "where (? is null or co_tipcon = ?) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or ne_mescon = ? ) " ;
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) like lower(?)) ";
            //strSql += "and (? is null or fe_ing = ?) ";
            //strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "and (? is null or ne_diapru = ?) ";
            strSql += "order by co_tipcon ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_tipcon 1
            if(tbm_tipcon.getIntCo_tipcon()>0){
                preSta.setInt(1, tbm_tipcon.getIntCo_tipcon());
                preSta.setInt(2, tbm_tipcon.getIntCo_tipcon());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_deslar 2
            if(tbm_tipcon.getStrTx_deslar()!=null){
                preSta.setString(3, tbm_tipcon.getStrTx_deslar().replace('*', '%'));
                preSta.setString(4, tbm_tipcon.getStrTx_deslar().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //ne_mescon 3
            if(tbm_tipcon.getIntNe_mescon()>0){
                preSta.setInt(5, tbm_tipcon.getIntNe_mescon());
                preSta.setInt(6, tbm_tipcon.getIntNe_mescon());
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }
            //tx_obs1 4
            if(tbm_tipcon.getStrTx_obs1()!=null){
                preSta.setString(7, tbm_tipcon.getStrTx_obs1().replace('*', '%'));
                preSta.setString(8, tbm_tipcon.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_tipcon.getStrSt_reg()!=null){
                preSta.setString(9, tbm_tipcon.getStrSt_reg());
                preSta.setString(10, tbm_tipcon.getStrSt_reg());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //fe_ing 6
            /*if(tbm_tipcon.getDatFe_ing()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_tipcon.getDatFe_ing().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_tipcon.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_tipcon.getDatFe_ultmod()!=null){
                preSta.setTimestamp(13, new Timestamp(tbm_tipcon.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(14, new Timestamp(tbm_tipcon.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(13, Types.TIMESTAMP);
                preSta.setNull(14, Types.TIMESTAMP);
            }*/
            //co_usring 8
            if(tbm_tipcon.getIntCo_usring()>0){
                preSta.setInt(11, tbm_tipcon.getIntCo_usring());
                preSta.setInt(12, tbm_tipcon.getIntCo_usring());
            }else{
                preSta.setNull(11, Types.INTEGER);
                preSta.setNull(12, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_tipcon.getIntCo_usrmod()>0){
                preSta.setInt(13, tbm_tipcon.getIntCo_usrmod());
                preSta.setInt(14, tbm_tipcon.getIntCo_usrmod());
            }else{
                preSta.setNull(13, Types.INTEGER);
                preSta.setNull(14, Types.INTEGER);
            }
            //ne_diapru 10
            if(tbm_tipcon.getIntNe_diapru()>0){
                preSta.setInt(15, tbm_tipcon.getIntNe_diapru());
                preSta.setInt(16, tbm_tipcon.getIntNe_diapru());
            }else{
                preSta.setNull(15, Types.INTEGER);
                preSta.setNull(16, Types.INTEGER);
            }
            //limit 11
            if(intCanReg > 0 ){
                preSta.setInt(17, intCanReg);
            }else{
                preSta.setNull(17, Types.INTEGER);
            }
            //offset 12
            preSta.setInt(18, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tipcon = new ArrayList<Tbm_tipcon>();
                do{
                    Tbm_tipcon tmp = new Tbm_tipcon();
                    tmp.setIntCo_tipcon(resSet.getInt("co_tipcon"));
                    tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                    tmp.setIntNe_mescon(resSet.getInt("ne_mescon"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    tmp.setIntNe_diapru(resSet.getInt("ne_diapru"));

                    lisTbm_tipcon.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tipcon;
    }

}

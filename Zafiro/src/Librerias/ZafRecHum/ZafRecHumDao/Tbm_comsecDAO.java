
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_comsec;
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
 * Objeto de Acceso a Datos de la tabla Tbm_comSec
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 24/03/2011
 */
public class Tbm_comsecDAO implements Tbm_comsecDAOInterface {

    
    /**
     * Retorna el máximo id de la tabla Tbm_comsec.
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
            strSql = "Select max(co_comsec) as maxId from tbm_comsec";
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
     * Graba en la tabla tbm_comsec.
     * @param connection
     * @param tbm_comsec Objeto POJO que contiene los datos de la tabla tbm_comsec
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_comsec tbm_comsec) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_comsec(co_comsec,tx_nomcomsec,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod) ";
            strSql += "values(?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_comsec 1
            if(tbm_comsec.getIntCo_comsec()>0){
                preSta.setInt(1, tbm_comsec.getIntCo_comsec());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_codcar 2
            /*if(tbm_comsec.getStrTx_codcar()!=null){
                preSta.setString(2, tbm_comsec.getStrTx_codcar());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }*/
            //tx_nomcomsec 3
            if(tbm_comsec.getStrTx_nomcomsec()!=null){
                preSta.setString(2, tbm_comsec.getStrTx_nomcomsec());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //nd_minsec 4
            /*if(tbm_comsec.getBigNd_minsec()!=null){
                preSta.setBigDecimal(4, tbm_comsec.getBigNd_minsec());
            }else{
                preSta.setNull(4, Types.NUMERIC);
            }*/
            //tx_obs1 5
            if(tbm_comsec.getStrTx_obs1()!=null){
                preSta.setString(3, tbm_comsec.getStrTx_obs1());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //st_reg 6
            if(tbm_comsec.getStrSt_reg()!=null){
                preSta.setString(4, tbm_comsec.getStrSt_reg());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //fe_ing 7
            if(tbm_comsec.getDatFe_ing()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_comsec.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            //fe_ultmod 8
            if(tbm_comsec.getDatFe_ultmod()!=null){
                preSta.setTimestamp(6, new Timestamp(tbm_comsec.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(6, Types.TIMESTAMP);
            }
            //co_usring 9
            if(tbm_comsec.getIntCo_usring()>0){
                preSta.setInt(7, tbm_comsec.getIntCo_usring());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }
            //co_usrmod 10
            if(tbm_comsec.getIntCo_usrmod()>0){
                preSta.setInt(8, tbm_comsec.getIntCo_usrmod());
            }else{
                preSta.setNull(8, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_comsec.
     * @param connection
     * @param tbm_comsec Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_comsec tbm_comsec) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "update tbm_comsec ";
            //strSql += "set tx_codcar = ?, ";
            strSql += "set tx_nomcomsec = ?, ";
            //strSql += "nd_minsec = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ? ";
            strSql += "where co_comsec = ?";

            preSta = connection.prepareStatement(strSql);
            //tx_codcar 1
            /*if(tbm_comsec.getStrTx_codcar()!=null){
                preSta.setString(1, tbm_comsec.getStrTx_codcar());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }*/
            //tx_nomcomsec 2
            if(tbm_comsec.getStrTx_nomcomsec()!=null){
                preSta.setString(1, tbm_comsec.getStrTx_nomcomsec());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //nd_minsec 3
            /*if(tbm_comsec.getBigNd_minsec()!=null){
                preSta.setBigDecimal(3, tbm_comsec.getBigNd_minsec());
            }else{
                preSta.setNull(3, Types.NUMERIC);
            }*/
            //tx_obs1 4
            if(tbm_comsec.getStrTx_obs1()!=null){
                preSta.setString(2, tbm_comsec.getStrTx_obs1());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //st_reg 5
            if(tbm_comsec.getStrSt_reg()!=null){
                preSta.setString(3, tbm_comsec.getStrSt_reg());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //fe_ing 6
            if(tbm_comsec.getDatFe_ing()!=null){
                preSta.setTimestamp(4, new Timestamp(tbm_comsec.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(4, Types.TIMESTAMP);
            }
            //fe_ultmod 7
            if(tbm_comsec.getDatFe_ultmod()!=null){
                preSta.setTimestamp(5, new Timestamp(tbm_comsec.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(5, Types.TIMESTAMP);
            }
            //co_usring 8
            if(tbm_comsec.getIntCo_usring()>0){
                preSta.setInt(6, tbm_comsec.getIntCo_usring());
            }else{
                preSta.setNull(6, Types.INTEGER);
            }
            //co_usrmod 9
            if(tbm_comsec.getIntCo_usrmod()>0){
                preSta.setInt(7, tbm_comsec.getIntCo_usrmod());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }
            //co_comsec 10
            preSta.setInt(8, tbm_comsec.getIntCo_comsec());

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_comsec
     * @param connection
     * @param tbm_comsec Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_comsec cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_comsec> consultarLisGen(Connection connection, Tbm_comsec tbm_comsec) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_comsec> lisTbm_comsec = null;

        try{
            strSql = "select * from tbm_comsec ";
            strSql += "where (? is null or co_comsec = ?) ";
            //strSql += "and (? is null or lower(tx_codcar) like lower(?)) ";
            strSql += "and (? is null or lower(tx_nomcomsec) like lower(?)) ";
            //strSql += "and (? is null or nd_minsec = ?) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            strSql += "and (? is null or fe_ing = ?) ";
            strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_comsec ";

            preSta = connection.prepareStatement(strSql);
            //co_comsec 1
            if(tbm_comsec.getIntCo_comsec()>0){
                preSta.setInt(1, tbm_comsec.getIntCo_comsec());
                preSta.setInt(2, tbm_comsec.getIntCo_comsec());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            //tx_codcar 2
            /*if(tbm_comsec.getStrTx_codcar()!=null){
                preSta.setString(3, tbm_comsec.getStrTx_codcar().replace('*', '%'));
                preSta.setString(4, tbm_comsec.getStrTx_codcar().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }*/

            //tx_nomcomsec 3
            if(tbm_comsec.getStrTx_nomcomsec()!=null){
                preSta.setString(3, tbm_comsec.getStrTx_nomcomsec().replace('*', '%'));
                preSta.setString(4, tbm_comsec.getStrTx_nomcomsec().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            //nd_minsec 4
            /*if(tbm_comsec.getBigNd_minsec()!=null&&tbm_comsec.getBigNd_minsec().longValue()>0){
                preSta.setBigDecimal(7, tbm_comsec.getBigNd_minsec());
                preSta.setBigDecimal(8, tbm_comsec.getBigNd_minsec());
            }else{
                preSta.setNull(7, Types.NUMERIC);
                preSta.setNull(8, Types.NUMERIC);
            }*/

            //tx_obs1 5
            if(tbm_comsec.getStrTx_obs1()!=null){
                preSta.setString(5, tbm_comsec.getStrTx_obs1().replace('*', '%'));
                preSta.setString(6, tbm_comsec.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }

            //st_reg 6
            if(tbm_comsec.getStrSt_reg()!=null){
                preSta.setString(7, tbm_comsec.getStrSt_reg());
                preSta.setString(8, tbm_comsec.getStrSt_reg());
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }

            //fe_ing 7
            if(tbm_comsec.getDatFe_ing()!=null){
                preSta.setTimestamp(9, new Timestamp(tbm_comsec.getDatFe_ing().getTime()));
                preSta.setTimestamp(10, new Timestamp(tbm_comsec.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(9, Types.TIMESTAMP);
                preSta.setNull(10, Types.TIMESTAMP);
            }

            //fe_ultmod 8
            if(tbm_comsec.getDatFe_ultmod()!=null){
                preSta.setTimestamp(11, new Timestamp(tbm_comsec.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(12, new Timestamp(tbm_comsec.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(11, Types.TIMESTAMP);
                preSta.setNull(12, Types.TIMESTAMP);
            }

            //co_usring 9
            if(tbm_comsec.getIntCo_usring()>0){
                preSta.setInt(13, tbm_comsec.getIntCo_usring());
                preSta.setInt(14, tbm_comsec.getIntCo_usring());
            }else{
                preSta.setNull(13, Types.INTEGER);
                preSta.setNull(14, Types.INTEGER);
            }

            //co_usrmod 10
            if(tbm_comsec.getIntCo_usrmod()>0){
                preSta.setInt(15, tbm_comsec.getIntCo_usrmod());
                preSta.setInt(16, tbm_comsec.getIntCo_usrmod());
            }else{
                preSta.setNull(15, Types.INTEGER);
                preSta.setNull(16, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_comsec = new ArrayList<Tbm_comsec>();
                do{
                    Tbm_comsec tmp = new Tbm_comsec();
                    tmp.setIntCo_comsec(resSet.getInt("co_comsec"));
                    //tmp.setStrTx_codcar(resSet.getString("tx_codcar"));
                    tmp.setStrTx_nomcomsec(resSet.getString("tx_nomcomsec"));
                    //tmp.setBigNd_minsec(resSet.getBigDecimal("nd_minsec"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    lisTbm_comsec.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_comsec;
    }

    /**
     * Consulta general de la tabla tbm_comsec aplicando pagineo
     * @param connection
     * @param tbm_comsec Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_comsec con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_comsec> consultarLisPag(Connection connection, Tbm_comsec tbm_comsec, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_comsec> lisTbm_comsec = null;

        try{
            strSql = "Select * from tbm_comsec ";
            strSql += "where (? is null or co_comsec = ?) ";
            //strSql += "and (? is null or lower(tx_codcar) like lower(?)) ";
            strSql += "and (? is null or lower(tx_nomcomsec) like lower(?)) ";
            //strSql += "and (? is null or nd_minsec = ?) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            //strSql += "and (? is null or fe_ing = ?) ";
            //strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "order by co_comsec ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_comsec 1
            if(tbm_comsec.getIntCo_comsec()>0){
                preSta.setInt(1, tbm_comsec.getIntCo_comsec());
                preSta.setInt(2, tbm_comsec.getIntCo_comsec());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            //tx_codcar 2
            /*if(tbm_comsec.getStrTx_codcar()!=null){
                preSta.setString(3, tbm_comsec.getStrTx_codcar().replace('*', '%'));
                preSta.setString(4, tbm_comsec.getStrTx_codcar().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }*/

            //tx_nomcomsec 3
            if(tbm_comsec.getStrTx_nomcomsec()!=null){
                preSta.setString(3, tbm_comsec.getStrTx_nomcomsec().replace('*', '%'));
                preSta.setString(4, tbm_comsec.getStrTx_nomcomsec().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            //nd_minsec 4
            /*if(tbm_comsec.getBigNd_minsec()!=null&&tbm_comsec.getBigNd_minsec().longValue()>0){
                preSta.setBigDecimal(7, tbm_comsec.getBigNd_minsec());
                preSta.setBigDecimal(8, tbm_comsec.getBigNd_minsec());
            }else{
                preSta.setNull(7, Types.NUMERIC);
                preSta.setNull(8, Types.NUMERIC);
            }*/

            //tx_obs1 5
            if(tbm_comsec.getStrTx_obs1()!=null){
                preSta.setString(5, tbm_comsec.getStrTx_obs1());
                preSta.setString(6, tbm_comsec.getStrTx_obs1());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }

            //st_reg 6
            if(tbm_comsec.getStrSt_reg()!=null){
                preSta.setString(7, tbm_comsec.getStrSt_reg().replace('*', '%'));
                preSta.setString(8, tbm_comsec.getStrSt_reg().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
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
            if(tbm_comsec.getIntCo_usring()>0){
                preSta.setInt(9, tbm_comsec.getIntCo_usring());
                preSta.setInt(10, tbm_comsec.getIntCo_usring());
            }else{
                preSta.setNull(9, Types.INTEGER);
                preSta.setNull(10, Types.INTEGER);
            }

            //co_usrmod 10
            if(tbm_comsec.getIntCo_usrmod()>0){
                preSta.setInt(11, tbm_comsec.getIntCo_usrmod());
                preSta.setInt(12, tbm_comsec.getIntCo_usrmod());
            }else{
                preSta.setNull(11, Types.INTEGER);
                preSta.setNull(12, Types.INTEGER);
            }

            //limit 11
            if(intCanReg > 0 ){
                preSta.setInt(13, intCanReg);
            }else{
                preSta.setNull(13, Types.INTEGER);
            }
            
            //offset 12
            preSta.setInt(14, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_comsec = new ArrayList<Tbm_comsec>();
                do{
                    Tbm_comsec tmp = new Tbm_comsec();
                    tmp.setIntCo_comsec(resSet.getInt("co_comsec"));
                    //tmp.setStrTx_codcar(resSet.getString("tx_codcar"));
                    tmp.setStrTx_nomcomsec(resSet.getString("tx_nomcomsec"));
                    //tmp.setBigNd_minsec(resSet.getBigDecimal("nd_minsec"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_comsec.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_comsec;
    }

    /**
     * Verifica si existe un código de cargo asignado a otro registro
     * @param connection
     * @param strCodCar Código de cargo a buscar
     * @param intCodComSec Código del sistema para excluirlo de la consulta
     * @return Retorna true si existe, caso contrario retorna false
     * @throws SQLException
     */
    public boolean consultarCodCarRep(Connection connection, String strCodCar, int intCodComSec) throws SQLException {
        boolean blnRep = false;
        String strSql = null;
        int intNumRep = 0;
        PreparedStatement preSta = null;
        ResultSet resSet = null;

        try{
            strSql = "select 1 as flag from tbm_comsec where tx_codcar = ? and co_comsec != ? and st_reg != 'I'";

            preSta = connection.prepareStatement(strSql);
            preSta.setString(1, strCodCar);
            preSta.setInt(2, intCodComSec);

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

}

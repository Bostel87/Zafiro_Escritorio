
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_contra;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla tbm_contra
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class Tbm_contraDAO implements Tbm_contraDAOInterface {

    /**
     * Retorna el máximo id de la tabla tbm_contra.
     * @param connection
     * @param intCo_emp Código de la empresa a la que pertenece el empleado
     * @param intCo_tra Código del empleado
     * @return Retorna 0 si no hay registros y el máximo id si los hay.
     * @throws SQLException
     */
    public int obtenerMaxId(Connection connection, int intCo_tra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        int intMaxId = 0;

        try{
            strSql = "select max(co_reg) as maxId from tbm_contra where co_tra = ?";

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
     * Graba en la tabla tbm_contra.
     * @param connection
     * @param tbm_contra Objeto POJO que contiene los datos de la tabla tbm_contra
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_contra tbm_contra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_contra(co_tra,co_reg,tx_nom,tx_tel1,tx_tel2,st_llacaseme,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod,fe_nac,co_tipfamcon) ";
            strSql += "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_contra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_contra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //co_reg 2
            if(tbm_contra.getIntCo_reg()>0){
                preSta.setInt(2, tbm_contra.getIntCo_reg());
            }else{
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_nom 3
             if(tbm_contra.getStrTx_nom()!=null){
                preSta.setString(3, tbm_contra.getStrTx_nom());
             }else{
                preSta.setNull(3, Types.VARCHAR);
             }
            //tx_tel1 4
            if(tbm_contra.getStrTx_tel1()!=null){
                preSta.setString(4, tbm_contra.getStrTx_tel1());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_tel2 5
            if(tbm_contra.getStrTx_tel2()!=null){
                preSta.setString(5, tbm_contra.getStrTx_tel2());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //st_llacaseme 6
            if(tbm_contra.getStrSt_llacaseme()!=null){
                preSta.setString(6, tbm_contra.getStrSt_llacaseme());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_obs1 7
            if(tbm_contra.getStrTx_obs1()!=null){
                preSta.setString(7, tbm_contra.getStrTx_obs1());
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }
            //st_reg 8
            if(tbm_contra.getStrSt_reg()!=null){
                preSta.setString(8, tbm_contra.getStrSt_reg());
            }else{
                preSta.setNull(8, Types.VARCHAR);
            }
            //fe_ing 9
            if(tbm_contra.getDatFe_ing()!=null){
                preSta.setTimestamp(9, new Timestamp(tbm_contra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(9, Types.TIMESTAMP);
            }
            //fe_ultmod 10
            if(tbm_contra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(10, new Timestamp(tbm_contra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(10, Types.TIMESTAMP);
            }
            //co_usring 11
            if(tbm_contra.getIntCo_usring()>0){
                preSta.setInt(11, tbm_contra.getIntCo_usring());
            }else{
                preSta.setNull(11, Types.INTEGER);
            }
            //co_usrmod 12
            if(tbm_contra.getIntCo_usrmod()>0){
                preSta.setInt(12, tbm_contra.getIntCo_usrmod());
            }else{
                preSta.setNull(12, Types.INTEGER);
            }
            //fe_nac 13
            if(tbm_contra.getDatFe_nac()!=null){
                preSta.setDate(13, new Date(tbm_contra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(13, Types.DATE);
            }
            //co_tipfamcon 14
            if(tbm_contra.getIntCo_tipfamcon()>0){
                preSta.setInt(14, tbm_contra.getIntCo_tipfamcon());
            }else{
                preSta.setNull(14, Types.INTEGER);
            }
            
            preSta.executeUpdate();
        } finally{
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_contra.
     * @param connection
     * @param tbm_contra Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_contra tbm_contra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "update tbm_contra ";
            strSql += "set tx_nom = ?, ";
            strSql += "tx_tel1 = ?, ";
            strSql += "tx_tel2 = ?, ";
            strSql += "st_llacaseme = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ?, ";
            strSql += "fe_nac = ?, ";
            strSql += "co_tipfamcon = ? ";
            strSql += "where co_tra = ? ";
            strSql += "and co_reg = ?";
            

            preSta = connection.prepareStatement(strSql);
            //tx_nom 1
             if(tbm_contra.getStrTx_nom()!=null){
                preSta.setString(1, tbm_contra.getStrTx_nom());
             }else{
                preSta.setNull(1, Types.VARCHAR);
             }
            //tx_tel1 2
            if(tbm_contra.getStrTx_tel1()!=null){
                preSta.setString(2, tbm_contra.getStrTx_tel1());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_tel2 3
            if(tbm_contra.getStrTx_tel2()!=null){
                preSta.setString(3, tbm_contra.getStrTx_tel2());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //st_llacaseme 4
            if(tbm_contra.getStrSt_llacaseme()!=null){
                preSta.setString(4, tbm_contra.getStrSt_llacaseme());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_obs1 5
            if(tbm_contra.getStrTx_obs1()!=null){
                preSta.setString(5, tbm_contra.getStrTx_obs1());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //st_reg 6
            if(tbm_contra.getStrSt_reg()!=null){
                preSta.setString(6, tbm_contra.getStrSt_reg());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //fe_ing 7
            if(tbm_contra.getDatFe_ing()!=null){
                preSta.setTimestamp(7, new Timestamp(tbm_contra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(7, Types.TIMESTAMP);
            }
            //fe_ultmod 8
            if(tbm_contra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(8, new Timestamp(tbm_contra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(8, Types.TIMESTAMP);
            }
            //co_usring 9
            if(tbm_contra.getIntCo_usring()>0){
                preSta.setInt(9, tbm_contra.getIntCo_usring());
            }else{
                preSta.setNull(9, Types.INTEGER);
            }
            //co_usrmod 10
            if(tbm_contra.getIntCo_usrmod()>0){
                preSta.setInt(10, tbm_contra.getIntCo_usrmod());
            }else{
                preSta.setNull(10, Types.INTEGER);
            }
            //fe_nac 11
            if(tbm_contra.getDatFe_nac()!=null){
                preSta.setDate(11, new Date(tbm_contra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(11, Types.DATE);
            }
            //co_tipfamcon 12
            if(tbm_contra.getIntCo_tipfamcon()>0){
                preSta.setInt(12, tbm_contra.getIntCo_tipfamcon());
            }else{
                preSta.setNull(12, Types.INTEGER);
            }
            //co_tra 13
            if(tbm_contra.getIntCo_tra()>0){
                preSta.setInt(13, tbm_contra.getIntCo_tra());
            }else{
                preSta.setNull(13, Types.INTEGER);
            }
            //co_reg 14
            if(tbm_contra.getIntCo_reg()>0){
                preSta.setInt(14, tbm_contra.getIntCo_reg());
            }else{
                preSta.setNull(14, Types.INTEGER);
            }
            
            preSta.executeUpdate();
        } finally{
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_contra
     * @param connection
     * @param tbm_contra Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_contra cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_contra> consultarLisGen(Connection connection, Tbm_contra tbm_contra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_contra> lisTbm_contra = null;

        try{
            strSql = "select * from tbm_contra ";
            strSql += "where (? is null or co_tra = ?) ";
            strSql += "and (? is null or co_reg = ?) ";
            strSql += "and (? is null or lower(tx_nom) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel1) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel2) like lower(?)) ";
            strSql += "and (? is null or lower(st_llacaseme) like lower(?)) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) like lower(?)) ";
            strSql += "and (?::timestamp without time zone is null or fe_ing = ?::timestamp without time zone) ";
            strSql += "and (?::timestamp without time zone is null or fe_ultmod = ?::timestamp without time zone) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "and (? is null or fe_nac = ?) ";
            strSql += "and (? is null or co_tipfamcon = ?) ";
            strSql += "order by co_tra, co_reg";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_contra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_contra.getIntCo_tra());
                preSta.setInt(2, tbm_contra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_reg 2
            if(tbm_contra.getIntCo_reg()>0){
                preSta.setInt(3, tbm_contra.getIntCo_reg());
                preSta.setInt(4, tbm_contra.getIntCo_reg());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }
            //tx_nom 3
             if(tbm_contra.getStrTx_nom()!=null){
                preSta.setString(5, tbm_contra.getStrTx_nom().replace('*', '%'));
                preSta.setString(6, tbm_contra.getStrTx_nom().replace('*', '%'));
             }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
             }
            //tx_tel1 4
            if(tbm_contra.getStrTx_tel1()!=null){
                preSta.setString(7, tbm_contra.getStrTx_tel1().replace('*', '%'));
                preSta.setString(8, tbm_contra.getStrTx_tel1().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_tel2 5
            if(tbm_contra.getStrTx_tel2()!=null){
                preSta.setString(9, tbm_contra.getStrTx_tel2().replace('*', '%'));
                preSta.setString(10, tbm_contra.getStrTx_tel2().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //st_llacaseme 6
            if(tbm_contra.getStrSt_llacaseme()!=null){
                preSta.setString(11, tbm_contra.getStrSt_llacaseme());
                preSta.setString(12, tbm_contra.getStrSt_llacaseme());
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //tx_obs1 7
            if(tbm_contra.getStrTx_obs1()!=null){
                preSta.setString(13, tbm_contra.getStrTx_obs1().replace('*', '%'));
                preSta.setString(14, tbm_contra.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }
            //st_reg 8
            if(tbm_contra.getStrSt_reg()!=null){
                preSta.setString(15, tbm_contra.getStrSt_reg());
                preSta.setString(16, tbm_contra.getStrSt_reg());
            }else{
                preSta.setNull(15, Types.VARCHAR);
                preSta.setNull(16, Types.VARCHAR);
            }
            //fe_ing 9
            if(tbm_contra.getDatFe_ing()!=null){
                preSta.setTimestamp(17, new Timestamp(tbm_contra.getDatFe_ing().getTime()));
                preSta.setTimestamp(18, new Timestamp(tbm_contra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(17, Types.TIMESTAMP);
                preSta.setNull(18, Types.TIMESTAMP);
            }
            //fe_ultmod 10
            if(tbm_contra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(19, new Timestamp(tbm_contra.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(20, new Timestamp(tbm_contra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(19, Types.TIMESTAMP);
                preSta.setNull(20, Types.TIMESTAMP);
            }
            //co_usring 11
            if(tbm_contra.getIntCo_usring()>0){
                preSta.setInt(21, tbm_contra.getIntCo_usring());
                preSta.setInt(22, tbm_contra.getIntCo_usring());
            }else{
                preSta.setNull(21, Types.INTEGER);
                preSta.setNull(22, Types.INTEGER);
            }
            //co_usrmod 12
            if(tbm_contra.getIntCo_usrmod()>0){
                preSta.setInt(23, tbm_contra.getIntCo_usrmod());
                preSta.setInt(24, tbm_contra.getIntCo_usrmod());
            }else{
                preSta.setNull(23, Types.INTEGER);
                preSta.setNull(24, Types.INTEGER);
            }
            //fe_nac 13
            if(tbm_contra.getDatFe_nac()!=null){
                preSta.setDate(25, new Date(tbm_contra.getDatFe_nac().getTime()));
                preSta.setDate(26, new Date(tbm_contra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(25, Types.DATE);
                preSta.setNull(26, Types.DATE);
            }
            //co_tipfamcon 14
            if(tbm_contra.getIntCo_tipfamcon()>0){
                preSta.setInt(27, tbm_contra.getIntCo_tipfamcon());
                preSta.setInt(28, tbm_contra.getIntCo_tipfamcon());
            }else{
                preSta.setNull(27, Types.INTEGER);
                preSta.setNull(28, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_contra = new ArrayList<Tbm_contra>();
                do{
                    Tbm_contra tmp = new Tbm_contra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setIntCo_reg(resSet.getInt("co_reg"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_tel2(resSet.getString("tx_tel2"));
                    tmp.setStrSt_llacaseme(resSet.getString("st_llacaseme"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_tipfamcon(resSet.getInt("co_tipfamcon"));

                    lisTbm_contra.add(tmp);
                }while(resSet.next());
            }
        } finally{
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_contra;
    }

    /**
     * Consulta general de la tabla tbm_contra aplicando pagineo
     * @param connection
     * @param tbm_contra Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return lista de tipo Tbm_contra con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_contra> consultarLisPag(Connection connection, Tbm_contra tbm_contra, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_contra> lisTbm_contra = null;

        try{
            strSql = "select * from tbm_contra ";
            strSql += "where (? is null or co_tra = ?) ";
            strSql += "and (? is null or co_reg = ?) ";
            strSql += "and (? is null or lower(tx_nom) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel1) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel2) like lower(?)) ";
            strSql += "and (? is null or lower(st_llacaseme) like lower(?)) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) like lower(?)) ";
            strSql += "and (? is null or fe_ing = ?) ";
            strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?)";
            strSql += "and (? is null or fe_nac = ?) ";
            strSql += "and (? is null or co_tipfamcon = ?) ";
            strSql += "order by co_tra, co_reg ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_contra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_contra.getIntCo_tra());
                preSta.setInt(2, tbm_contra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_reg 2
            if(tbm_contra.getIntCo_reg()>0){
                preSta.setInt(3, tbm_contra.getIntCo_reg());
                preSta.setInt(4, tbm_contra.getIntCo_reg());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }
            //tx_nom 3
             if(tbm_contra.getStrTx_nom()!=null){
                preSta.setString(5, tbm_contra.getStrTx_nom().replace('*', '%'));
                preSta.setString(6, tbm_contra.getStrTx_nom().replace('*', '%'));
             }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
             }
            //tx_tel1 4
            if(tbm_contra.getStrTx_tel1()!=null){
                preSta.setString(7, tbm_contra.getStrTx_tel1().replace('*', '%'));
                preSta.setString(8, tbm_contra.getStrTx_tel1().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_tel2 5
            if(tbm_contra.getStrTx_tel2()!=null){
                preSta.setString(9, tbm_contra.getStrTx_tel2().replace('*', '%'));
                preSta.setString(10, tbm_contra.getStrTx_tel2().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //st_llacaseme 6
            if(tbm_contra.getStrSt_llacaseme()!=null){
                preSta.setString(11, tbm_contra.getStrSt_llacaseme());
                preSta.setString(12, tbm_contra.getStrSt_llacaseme());
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //tx_obs1 7
            if(tbm_contra.getStrTx_obs1()!=null){
                preSta.setString(13, tbm_contra.getStrTx_obs1().replace('*', '%'));
                preSta.setString(14, tbm_contra.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }
            //st_reg 8
            if(tbm_contra.getStrSt_reg()!=null){
                preSta.setString(15, tbm_contra.getStrSt_reg());
                preSta.setString(16, tbm_contra.getStrSt_reg());
            }else{
                preSta.setNull(15, Types.VARCHAR);
                preSta.setNull(16, Types.VARCHAR);
            }
            //fe_ing 9
            if(tbm_contra.getDatFe_ing()!=null){
                preSta.setTimestamp(17, new Timestamp(tbm_contra.getDatFe_ing().getTime()));
                preSta.setTimestamp(18, new Timestamp(tbm_contra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(17, Types.TIMESTAMP);
                preSta.setNull(18, Types.TIMESTAMP);
            }
            //fe_ultmod 10
            if(tbm_contra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(19, new Timestamp(tbm_contra.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(20, new Timestamp(tbm_contra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(19, Types.TIMESTAMP);
                preSta.setNull(20, Types.TIMESTAMP);
            }
            //co_usring 11
            if(tbm_contra.getIntCo_usring()>0){
                preSta.setInt(21, tbm_contra.getIntCo_usring());
                preSta.setInt(22, tbm_contra.getIntCo_usring());
            }else{
                preSta.setNull(21, Types.INTEGER);
                preSta.setNull(22, Types.INTEGER);
            }
            //co_usrmod 12
            if(tbm_contra.getIntCo_usrmod()>0){
                preSta.setInt(23, tbm_contra.getIntCo_usrmod());
                preSta.setInt(24, tbm_contra.getIntCo_usrmod());
            }else{
                preSta.setNull(23, Types.INTEGER);
                preSta.setNull(24, Types.INTEGER);
            }
            //fe_nac 13
            if(tbm_contra.getDatFe_nac()!=null){
                preSta.setDate(25, new Date(tbm_contra.getDatFe_nac().getTime()));
                preSta.setDate(26, new Date(tbm_contra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(25, Types.DATE);
                preSta.setNull(26, Types.DATE);
            }
            //co_tipfamcon 14
            if(tbm_contra.getIntCo_tipfamcon()>0){
                preSta.setInt(27, tbm_contra.getIntCo_tipfamcon());
                preSta.setInt(28, tbm_contra.getIntCo_tipfamcon());
            }else{
                preSta.setNull(27, Types.INTEGER);
                preSta.setNull(28, Types.INTEGER);
            }
            //limit 15
            if(intCanReg > 0 ){
                preSta.setInt(29, intCanReg);
                preSta.setInt(30, intCanReg);
            }else{
                preSta.setNull(29, Types.INTEGER);
                preSta.setNull(30, Types.INTEGER);
            }

            //offset 16
            preSta.setInt(31, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_contra = new ArrayList<Tbm_contra>();
                do{
                    Tbm_contra tmp = new Tbm_contra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setIntCo_reg(resSet.getInt("co_reg"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_nom(resSet.getString("tx_tel2"));
                    tmp.setStrSt_llacaseme(resSet.getString("st_llacaseme"));
                    tmp.setStrTx_nom(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_tipfamcon(resSet.getInt("co_tipfamcon"));

                    lisTbm_contra.add(tmp);
                }while(resSet.next());
            }
        } finally{
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_contra;
    }

}

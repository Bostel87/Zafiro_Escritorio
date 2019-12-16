
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_reg;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_reg
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 13/05/2011
 */
public class Tbm_regDAO implements Tbm_regDAOInterface {

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
            strSql = "Select max(co_reg) as maxId from tbm_reg";
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
     * Graba en la tabla tbm_reg
     * @param connection
     * @param tbm_reg Objeto POJO que contiene los datos de la tabla tbm_reg
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_reg tbm_reg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_reg(co_reg,tx_descor,tx_deslar,ne_mespagdeccuasue,ne_mesinicaldeccuasue,ne_mesfincaldeccuasue,st_reg) ";
            strSql += "values(?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_reg 1
            if(tbm_reg.getIntCo_reg()>0){
                preSta.setInt(1, tbm_reg.getIntCo_reg());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_descor 2
            if(tbm_reg.getStrTx_descor()!=null){
                preSta.setString(2, tbm_reg.getStrTx_descor());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_deslar 3
            if(tbm_reg.getStrTx_deslar()!=null){
                preSta.setString(3, tbm_reg.getStrTx_deslar());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //ne_mespagdeccuasue 4
            if(tbm_reg.getIntNe_mespagdeccuasue()>0){
                preSta.setInt(4, tbm_reg.getIntNe_mespagdeccuasue());
            }else{
                preSta.setNull(4, Types.INTEGER);
            }
            //ne_mesinicaldeccuasue 5
            if(tbm_reg.getIntNe_mesinicaldeccuasue()>0){
                preSta.setInt(5, tbm_reg.getIntNe_mesinicaldeccuasue());
            }else{
                preSta.setNull(5, Types.INTEGER);
            }
            //ne_mesfincaldeccuasue 6
            if(tbm_reg.getIntNe_mesfincaldeccuasue()>0){
                preSta.setInt(6, tbm_reg.getIntNe_mesfincaldeccuasue());
            }else{
                preSta.setNull(6, Types.INTEGER);
            }
            //st_reg 7
            if(tbm_reg.getStrSt_reg()!=null){
                preSta.setString(7, tbm_reg.getStrSt_reg());
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    public void actualizar(Connection connection, Tbm_reg tbm_reg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "update tbm_reg ";
            strSql += "set tx_descor = ?, ";
            strSql += "tx_deslar = ?, ";
            strSql += "ne_mespagdeccuasue = ?, ";
            strSql += "ne_mesinicaldeccuasue = ?, ";
            strSql += "ne_mesfincaldeccuasue = ?, ";
            strSql += "st_reg = ? ";
            strSql += "where co_reg = ?";

            preSta = connection.prepareStatement(strSql);
            //tx_descor 1
            if(tbm_reg.getStrTx_descor()!=null){
                preSta.setString(1, tbm_reg.getStrTx_descor());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //tx_deslar 2
            if(tbm_reg.getStrTx_deslar()!=null){
                preSta.setString(2, tbm_reg.getStrTx_deslar());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //ne_mespagdeccuasue 3
            if(tbm_reg.getIntNe_mespagdeccuasue()>0){
                preSta.setInt(3, tbm_reg.getIntNe_mespagdeccuasue());
            }else{
                preSta.setNull(3, Types.INTEGER);
            }
            //ne_mesinicaldeccuasue 4
            if(tbm_reg.getIntNe_mesinicaldeccuasue()>0){
                preSta.setInt(4, tbm_reg.getIntNe_mesinicaldeccuasue());
            }else{
                preSta.setNull(4, Types.INTEGER);
            }
            //ne_mesfincaldeccuasue 5
            if(tbm_reg.getIntNe_mesfincaldeccuasue()>0){
                preSta.setInt(5, tbm_reg.getIntNe_mesfincaldeccuasue());
            }else{
                preSta.setNull(5, Types.INTEGER);
            }
            //st_reg 6
            if(tbm_reg.getStrSt_reg()!=null){
                preSta.setString(6, tbm_reg.getStrSt_reg());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //co_reg 7
            if(tbm_reg.getIntCo_reg()>0){
                preSta.setInt(7, tbm_reg.getIntCo_reg());
            }else{
                preSta.setNull(7, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_reg
     * @param connection
     * @param tbm_reg Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_reg cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_reg> consultarLisGen(Connection connection, Tbm_reg tbm_reg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_reg> lisTbm_reg = null;

        try{
            strSql = "select * from tbm_reg ";
            strSql += "where (? is null or co_reg = ?) ";
            strSql += "and (? is null or lower(tx_descor) like lower(?)) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or ne_mespagdeccuasue = ?) ";
            strSql += "and (? is null or ne_mesinicaldeccuasue = ?) ";
            strSql += "and (? is null or ne_mesfincaldeccuasue = ?) ";
            strSql += "and (? is null or st_reg = ?) ";
            strSql += "order by co_reg";

            preSta = connection.prepareStatement(strSql);
            //co_reg 1
            if(tbm_reg.getIntCo_reg()>0){
                preSta.setInt(1, tbm_reg.getIntCo_reg());
                preSta.setInt(2, tbm_reg.getIntCo_reg());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_descor 2
            if(tbm_reg.getStrTx_descor()!=null){
                preSta.setString(3, tbm_reg.getStrTx_descor());
                preSta.setString(4, tbm_reg.getStrTx_descor());
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_deslar 3
            if(tbm_reg.getStrTx_deslar()!=null){
                preSta.setString(5, tbm_reg.getStrTx_deslar());
                preSta.setString(6, tbm_reg.getStrTx_deslar());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //ne_mespagdeccuasue 4
            if(tbm_reg.getIntNe_mespagdeccuasue()>0){
                preSta.setInt(7, tbm_reg.getIntNe_mespagdeccuasue());
                preSta.setInt(8, tbm_reg.getIntNe_mespagdeccuasue());
            }else{
                preSta.setNull(7, Types.INTEGER);
                preSta.setNull(8, Types.INTEGER);
            }
            //ne_mesinicaldeccuasue 5
            if(tbm_reg.getIntNe_mesinicaldeccuasue()>0){
                preSta.setInt(9, tbm_reg.getIntNe_mesinicaldeccuasue());
                preSta.setInt(10, tbm_reg.getIntNe_mesinicaldeccuasue());
            }else{
                preSta.setNull(9, Types.INTEGER);
                preSta.setNull(10, Types.INTEGER);
            }
            //ne_mesfincaldeccuasue 6
            if(tbm_reg.getIntNe_mesfincaldeccuasue()>0){
                preSta.setInt(11, tbm_reg.getIntNe_mesfincaldeccuasue());
                preSta.setInt(12, tbm_reg.getIntNe_mesfincaldeccuasue());
            }else{
                preSta.setNull(11, Types.INTEGER);
                preSta.setNull(12, Types.INTEGER);
            }
            //st_reg 7
            if(tbm_reg.getStrSt_reg()!=null){
                preSta.setString(13, tbm_reg.getStrSt_reg());
                preSta.setString(14, tbm_reg.getStrSt_reg());
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_reg = new ArrayList<Tbm_reg>();
                do{
                    Tbm_reg tmp = new Tbm_reg();
                    tmp.setIntCo_reg(resSet.getInt("co_reg"));
                    tmp.setStrTx_descor(resSet.getString("tx_descor"));
                    tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                    tmp.setIntNe_mespagdeccuasue(resSet.getInt("ne_mespagdeccuasue"));
                    tmp.setIntNe_mesinicaldeccuasue(resSet.getInt("ne_mesinicaldeccuasue"));
                    tmp.setIntNe_mesfincaldeccuasue(resSet.getInt("ne_mesfincaldeccuasue"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));

                    lisTbm_reg.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_reg;
    }

    public List<Tbm_reg> consultarLisPag(Connection connection, Tbm_reg tbm_reg, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_reg> lisTbm_reg = null;

        try{
            strSql = "select * from tbm_reg ";
            strSql += "where (? is null or co_reg = ?) ";
            strSql += "and (? is null or lower(tx_descor) like lower(?)) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or ne_mespagdeccuasue = ?) ";
            strSql += "and (? is null or ne_mesinicaldeccuasue = ?) ";
            strSql += "and (? is null or ne_mesfincaldeccuasue = ?) ";
            strSql += "and (? is null or st_reg = ?) ";
            strSql += "order by co_reg ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_reg 1
            if(tbm_reg.getIntCo_reg()>0){
                preSta.setInt(1, tbm_reg.getIntCo_reg());
                preSta.setInt(2, tbm_reg.getIntCo_reg());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_descor 2
            if(tbm_reg.getStrTx_descor()!=null){
                preSta.setString(3, tbm_reg.getStrTx_descor());
                preSta.setString(4, tbm_reg.getStrTx_descor());
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_deslar 3
            if(tbm_reg.getStrTx_deslar()!=null){
                preSta.setString(5, tbm_reg.getStrTx_deslar());
                preSta.setString(6, tbm_reg.getStrTx_deslar());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //ne_mespagdeccuasue 4
            if(tbm_reg.getIntNe_mespagdeccuasue()>0){
                preSta.setInt(7, tbm_reg.getIntNe_mespagdeccuasue());
                preSta.setInt(8, tbm_reg.getIntNe_mespagdeccuasue());
            }else{
                preSta.setNull(7, Types.INTEGER);
                preSta.setNull(8, Types.INTEGER);
            }
            //ne_mesinicaldeccuasue 5
            if(tbm_reg.getIntNe_mesinicaldeccuasue()>0){
                preSta.setInt(9, tbm_reg.getIntNe_mesinicaldeccuasue());
                preSta.setInt(10, tbm_reg.getIntNe_mesinicaldeccuasue());
            }else{
                preSta.setNull(9, Types.INTEGER);
                preSta.setNull(10, Types.INTEGER);
            }
            //ne_mesfincaldeccuasue 6
            if(tbm_reg.getIntNe_mesfincaldeccuasue()>0){
                preSta.setInt(11, tbm_reg.getIntNe_mesfincaldeccuasue());
                preSta.setInt(12, tbm_reg.getIntNe_mesfincaldeccuasue());
            }else{
                preSta.setNull(11, Types.INTEGER);
                preSta.setNull(12, Types.INTEGER);
            }
            //st_reg 7
            if(tbm_reg.getStrSt_reg()!=null){
                preSta.setString(13, tbm_reg.getStrSt_reg());
                preSta.setString(14, tbm_reg.getStrSt_reg());
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }
            //limit 8
            if(intCanReg > 0 ){
                preSta.setInt(15, intCanReg);
            }else{
                preSta.setNull(15, Types.INTEGER);
            }
            //offset 9
            preSta.setInt(16, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_reg = new ArrayList<Tbm_reg>();
                do{
                    Tbm_reg tmp = new Tbm_reg();
                    tmp.setIntCo_reg(resSet.getInt("co_reg"));
                    tmp.setStrTx_descor(resSet.getString("tx_descor"));
                    tmp.setStrTx_deslar(resSet.getString("tx_deslar"));
                    tmp.setIntNe_mespagdeccuasue(resSet.getInt("ne_mespagdeccuasue"));
                    tmp.setIntNe_mesinicaldeccuasue(resSet.getInt("ne_mesinicaldeccuasue"));
                    tmp.setIntNe_mesfincaldeccuasue(resSet.getInt("ne_mesfincaldeccuasue"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));

                    lisTbm_reg.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_reg;
    }

}

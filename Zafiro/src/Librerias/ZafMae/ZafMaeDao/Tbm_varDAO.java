
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_var;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_var
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class Tbm_varDAO implements Tbm_varDAOInterface {

    public int obtenerMaxId(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void grabar(Connection connection, Tbm_var tbm_var) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizar(Connection connection, Tbm_var tbm_var) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Consulta general de la tabla tbm_var
     * @param connection
     * @param tbm_var Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_var cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_var> consultarLisGen(Connection connection, Tbm_var tbm_var) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_var> lisTbm_var = null;

        try{
            strSql = "Select * from tbm_var ";
            strSql += "where (? is null or co_reg = ?) ";
            strSql += "and (? is null or lower(tx_desCor) like lower(?)) ";
            strSql += "and (? is null or lower(tx_desLar) like lower(?)) ";
            strSql += "and (? is null or co_grp = ?) ";
            strSql += "and (? is null or tx_tipunimed = ?) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            strSql += "order by co_reg ";

            preSta = connection.prepareStatement(strSql);
            //co_reg 1
            if(tbm_var.getIntCo_reg()>0){
                preSta.setInt(1, tbm_var.getIntCo_reg());
                preSta.setInt(2, tbm_var.getIntCo_reg());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            //tx_descor 2
            if(tbm_var.getStrTx_descor()!=null){
                preSta.setString(3, tbm_var.getStrTx_descor());
                preSta.setString(4, tbm_var.getStrTx_descor());
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            //tx_deslar 3
            if(tbm_var.getStrTx_deslar()!=null){
                preSta.setString(5, tbm_var.getStrTx_deslar());
                preSta.setString(6, tbm_var.getStrTx_deslar());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }

            //co_grp 4
            if(tbm_var.getIntCo_grp()>0){
                preSta.setInt(7, tbm_var.getIntCo_grp());
                preSta.setInt(8, tbm_var.getIntCo_grp());
            }else{
                preSta.setNull(7, Types.NUMERIC);
                preSta.setNull(8, Types.NUMERIC);
            }

            //tx_tipUniMed 5
            if(tbm_var.getStrTx_tipunimed()!=null){
                preSta.setString(9, tbm_var.getStrTx_tipunimed());
                preSta.setString(10, tbm_var.getStrTx_tipunimed());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }

            //tx_obs1 6
            if(tbm_var.getStrTx_obs1()!=null){
                preSta.setString(11, tbm_var.getStrTx_obs1());
                preSta.setString(12, tbm_var.getStrTx_obs1());
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }

            //st_reg 5
            if(tbm_var.getStrSt_reg()!=null){
                preSta.setString(13, tbm_var.getStrSt_reg());
                preSta.setString(14, tbm_var.getStrSt_reg());
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_var = new ArrayList<Tbm_var>();
                do{
                    Tbm_var tmp = new Tbm_var();
                    tmp.setIntCo_reg(resSet.getInt("co_reg"));
                    tmp.setStrTx_descor(resSet.getString("tx_desCor"));
                    tmp.setStrTx_deslar(resSet.getString("tx_desLar"));
                    tmp.setIntCo_grp(resSet.getInt("co_grp"));
                    tmp.setStrTx_tipunimed(resSet.getString("tx_tipUniMed"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));

                    lisTbm_var.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_var;
    }

    public String consultarEstadoCivil(Connection connection, int intEstCiv) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        String strTx_DesLar = null;

        try{
            strSql = "select tx_desLar from tbm_var where co_reg = ? and co_grp = 1 and st_reg like 'A'";

            preSta = connection.prepareStatement(strSql);
            preSta.setInt(1, intEstCiv);
            resSet = preSta.executeQuery();

            if(resSet.next()){
                strTx_DesLar = resSet.getString("tx_desLar");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return strTx_DesLar;
    }

    public String consultarEstadoCivil(Connection connection, String strEstCiv) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        //int intEstCiv = 0;
        String strResEstCiv = null;

        try{
            strSql = "select co_reg, tx_deslar from tbm_var where lower(tx_deslar) like lower(?) and co_grp = 1 and st_reg like 'A'";

            preSta = connection.prepareStatement(strSql);
            preSta.setString(1, strEstCiv);
            resSet = preSta.executeQuery();

            if(resSet.next()){
                strResEstCiv = resSet.getInt("co_reg") + ";" +resSet.getString("tx_deslar");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return strResEstCiv;
    }

    public List<Tbm_var> consultarLisPag(Connection connection, Tbm_var tbm_var, int intRegIni, int intCanReg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}

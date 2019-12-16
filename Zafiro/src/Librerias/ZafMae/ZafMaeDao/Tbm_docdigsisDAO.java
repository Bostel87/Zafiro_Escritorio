
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_docdigsis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla tbm_docdigsis
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 24/05/2011
 */
public class Tbm_docdigsisDAO implements Tbm_docdigsisDAOInterface {

    public int obtenerMaxId(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void grabar(Connection connection, Tbm_docdigsis tbm_docdigsis) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizar(Connection connection, Tbm_docdigsis tbm_docdigsis) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Tbm_docdigsis> consultarLisGen(Connection connection, Tbm_docdigsis tbm_docdigsis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_docdigsis> lisTbm_docdigsis = null;

        try{
            strSql = "select * from tbm_docdigsis ";
            strSql += "where (? is null or co_emp = ?) ";
            strSql += "and (? is null or co_loc = ?) ";
            strSql += "and (? is null or co_mnu = ?) ";
            strSql += "and (? is null or tx_rutabs = ?) ";
            strSql += "and (? is null or tx_rutrel = ?) ";
            strSql += "order by co_emp, co_loc, co_mnu";

            preSta = connection.prepareStatement(strSql);
            //co_emp 1
            if(tbm_docdigsis.getIntCo_emp()>0){
                preSta.setInt(1, tbm_docdigsis.getIntCo_emp());
                preSta.setInt(2, tbm_docdigsis.getIntCo_emp());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_loc 2
            if(tbm_docdigsis.getIntCo_loc()>0){
                preSta.setInt(3, tbm_docdigsis.getIntCo_loc());
                preSta.setInt(4, tbm_docdigsis.getIntCo_loc());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }
            //co_mnu 3
            if(tbm_docdigsis.getIntCo_mnu()>0){
                preSta.setInt(5, tbm_docdigsis.getIntCo_mnu());
                preSta.setInt(6, tbm_docdigsis.getIntCo_mnu());
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }
            //tx_rutabs 4
            if(tbm_docdigsis.getStrTx_rutabs()!=null){
                preSta.setString(7, tbm_docdigsis.getStrTx_rutabs());
                preSta.setString(8, tbm_docdigsis.getStrTx_rutabs());
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_rutrel 5
            if(tbm_docdigsis.getStrTx_rutrel()!=null){
                preSta.setString(9, tbm_docdigsis.getStrTx_rutrel());
                preSta.setString(10, tbm_docdigsis.getStrTx_rutrel());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_docdigsis = new ArrayList<Tbm_docdigsis>();
                do{
                    Tbm_docdigsis tmp = new Tbm_docdigsis();
                    tmp.setIntCo_emp(resSet.getInt("co_emp"));
                    tmp.setIntCo_loc(resSet.getInt("co_loc"));
                    tmp.setIntCo_mnu(resSet.getInt("co_mnu"));
                    tmp.setStrTx_rutabs(resSet.getString("tx_rutabs"));
                    tmp.setStrTx_rutrel(resSet.getString("tx_rutrel"));

                    lisTbm_docdigsis.add(tmp);
                }while(resSet.next());
            }

        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_docdigsis;
    }

    public List<Tbm_docdigsis> consultarLisPag(Connection connection, Tbm_docdigsis tbm_docdigsis, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_docdigsis> lisTbm_docdigsis = null;

        try{
            strSql = "select * from tbm_docdigsis ";
            strSql += "where (? is null or co_emp = ?) ";
            strSql += "and (? is null or co_loc = ?) ";
            strSql += "and (? is null or co_mnu = ?) ";
            strSql += "and (? is null or tx_rutabs = ?) ";
            strSql += "and (? is null or tx_rutrel = ?) ";
            strSql += "order by co_emp, co_loc, co_mnu ";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_emp 1
            if(tbm_docdigsis.getIntCo_emp()>0){
                preSta.setInt(1, tbm_docdigsis.getIntCo_emp());
                preSta.setInt(2, tbm_docdigsis.getIntCo_emp());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_loc 2
            if(tbm_docdigsis.getIntCo_loc()>0){
                preSta.setInt(3, tbm_docdigsis.getIntCo_loc());
                preSta.setInt(4, tbm_docdigsis.getIntCo_loc());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }
            //co_mnu 3
            if(tbm_docdigsis.getIntCo_mnu()>0){
                preSta.setInt(5, tbm_docdigsis.getIntCo_mnu());
                preSta.setInt(6, tbm_docdigsis.getIntCo_mnu());
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }
            //tx_rutabs 4
            if(tbm_docdigsis.getStrTx_rutabs()!=null){
                preSta.setString(7, tbm_docdigsis.getStrTx_rutabs());
                preSta.setString(8, tbm_docdigsis.getStrTx_rutabs());
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_rutrel 5
            if(tbm_docdigsis.getStrTx_rutrel()!=null){
                preSta.setString(9, tbm_docdigsis.getStrTx_rutrel());
                preSta.setString(10, tbm_docdigsis.getStrTx_rutrel());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //limit 6
            if(intCanReg > 0 ){
                preSta.setInt(11, intCanReg);
            }else{
                preSta.setNull(11, Types.INTEGER);
            }
            //offset 7
            preSta.setInt(12, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_docdigsis = new ArrayList<Tbm_docdigsis>();
                do{
                    Tbm_docdigsis tmp = new Tbm_docdigsis();
                    tmp.setIntCo_emp(resSet.getInt("co_emp"));
                    tmp.setIntCo_loc(resSet.getInt("co_loc"));
                    tmp.setIntCo_mnu(resSet.getInt("co_mnu"));
                    tmp.setStrTx_rutabs(resSet.getString("tx_rutabs"));
                    tmp.setStrTx_rutrel(resSet.getString("tx_rutrel"));

                    lisTbm_docdigsis.add(tmp);
                }while(resSet.next());
            }

        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_docdigsis;
    }

}

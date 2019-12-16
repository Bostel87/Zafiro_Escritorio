
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_ciu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_ciu
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class Tbm_ciuDAO implements Tbm_ciuDAOInterface {

    public int obtenerMaxId(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void grabar(Connection connection, Tbm_ciu tbm_ciu) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizar(Connection connection, Tbm_ciu tbm_ciu) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Consulta general de la tabla tbm_ciu
     * @param connection
     * @param tbm_ciu Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_ciu cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_ciu> consultarLisGen(Connection connection, Tbm_ciu tbm_ciu) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_ciu> lisTbm_ciu = null;

        try{
            strSql = "Select * from tbm_ciu ";
            strSql += "where (? is null or co_ciu = ?) ";
            strSql += "and (? is null or lower(tx_descor) like lower(?)) ";
            strSql += "and (? is null or lower(tx_deslar) like lower(?)) ";
            strSql += "and (? is null or co_pai = ?) ";
            strSql += "and (? is null or lower(st_reg) = lower(?)) ";
            strSql += "order by co_ciu ";

            preSta = connection.prepareStatement(strSql);
            //co_ciu 1
            if(tbm_ciu.getIntCo_ciu()>0){
                preSta.setInt(1, tbm_ciu.getIntCo_ciu());
                preSta.setInt(2, tbm_ciu.getIntCo_ciu());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }

            //tx_descor 2
            if(tbm_ciu.getStrTx_descor()!=null){
                preSta.setString(3, tbm_ciu.getStrTx_descor());
                preSta.setString(4, tbm_ciu.getStrTx_descor());
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }

            //tx_deslar 3
            if(tbm_ciu.getStrTx_deslar()!=null){
                preSta.setString(5, tbm_ciu.getStrTx_deslar());
                preSta.setString(6, tbm_ciu.getStrTx_deslar());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }

            //co_pai 4
            if(tbm_ciu.getIntCo_pai()>0){
                preSta.setInt(7, tbm_ciu.getIntCo_pai());
                preSta.setInt(8, tbm_ciu.getIntCo_pai());
            }else{
                preSta.setNull(7, Types.NUMERIC);
                preSta.setNull(8, Types.NUMERIC);
            }

            //st_reg 5
            if(tbm_ciu.getStrSt_reg()!=null){
                preSta.setString(9, tbm_ciu.getStrSt_reg());
                preSta.setString(10, tbm_ciu.getStrSt_reg());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_ciu = new ArrayList<Tbm_ciu>();
                do{
                    Tbm_ciu tmp = new Tbm_ciu();
                    tmp.setIntCo_ciu(resSet.getInt("co_ciu"));
                    tmp.setStrTx_descor(resSet.getString("tx_desCor"));
                    tmp.setStrTx_deslar(resSet.getString("tx_desLar"));
                    tmp.setIntCo_pai(resSet.getInt("co_pai"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));

                    lisTbm_ciu.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_ciu;
    }
    
    public List<Tbm_ciu> consultarLisPag(Connection connection, Tbm_ciu tbm_ciu, int intRegIni, int intCanReg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String consultarCiudad(Connection connection, int intCodCiu) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        String strTx_DesLar = null;

        try{
            strSql = "Select tx_deslar from tbm_ciu ";
            strSql += "where co_ciu = ? ";

            preSta = connection.prepareStatement(strSql);
            preSta.setInt(1, intCodCiu);
            resSet = preSta.executeQuery();

            if(resSet.next()){
                strTx_DesLar = resSet.getString("tx_deslar");
            }
        }finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return strTx_DesLar;
    }

}

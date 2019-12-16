
package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_tra
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 01/04/2011
 * v1.05
 */
public class Tbm_traDAO implements Tbm_traDAOInterface {

     /**
     * Retorna el máximo id de la tabla Tbm_tra.
     * @param connection
     * @return Retorna 0 si no hay registros y el máximo id si los hay.
     * @throws Exception
     */
    public int obtenerMaxId(Connection connection) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        int intMaxId = 0;

        try{
            strSql = "select max(co_tra) as maxId from tbm_tra";
            preSta = connection. prepareStatement(strSql);
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
     * Graba en la tabla tbm_tra.
     * @param connection
     * @param tbm_tra Objeto POJO que contiene los datos de la tabla tbm_tra
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_tra tbm_tra,ZafParSis objZafParSis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        //Conexion para base alterna.
        Connection con2 = null;
        PreparedStatement preSta2 = null;
        try{
            //Conexion para base alterna
            if ((objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false) {
                con2 = DriverManager.getConnection("jdbc:postgresql://172.16.1.2:5432/Zafiro2006",objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                //con2 = DriverManager.getConnection("jdbc:postgresql://172.16.8.7:5432/Zafiro2006",objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            }else{
                con2 = DriverManager.getConnection("jdbc:postgresql://172.16.1.2:5432/dbCosenco",objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                //con2 = DriverManager.getConnection("jdbc:postgresql://172.16.8.7:5432/dbCosenco",objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            }
           
            strSql = "insert into tbm_tra(co_tra,tx_ide,tx_nom,tx_ape,tx_dir,tx_refdir,tx_tel1,tx_tel2,tx_tel3,tx_corele,tx_sex,fe_nac,co_ciunac,co_estciv,ne_numhij,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod) ";
            strSql += "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_tra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_tra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_ide 2
            if(tbm_tra.getStrTx_ide()!=null){
                preSta.setString(2, tbm_tra.getStrTx_ide());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_nom 3
            if(tbm_tra.getStrTx_nom()!=null){
                preSta.setString(3, tbm_tra.getStrTx_nom());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //tx_ape 4
            if(tbm_tra.getStrTx_ape()!=null){
                preSta.setString(4, tbm_tra.getStrTx_ape());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_dir 5
            if(tbm_tra.getStrTx_dir()!=null){
                preSta.setString(5, tbm_tra.getStrTx_dir());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //tx_refdir 6
            if(tbm_tra.getStrTx_refdir()!=null){
                preSta.setString(6, tbm_tra.getStrTx_refdir());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_tel1 7
            if(tbm_tra.getStrTx_tel1()!=null){
                preSta.setString(7, tbm_tra.getStrTx_tel1());
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }
            //tx_tel2 8
            if(tbm_tra.getStrTx_tel2()!=null){
                preSta.setString(8, tbm_tra.getStrTx_tel2());
            }else{
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_tel3 9
            if(tbm_tra.getStrTx_tel3()!=null){
                preSta.setString(9, tbm_tra.getStrTx_tel3());
            }else{
                preSta.setNull(9, Types.VARCHAR);
            }
            //tx_corele 10
            if(tbm_tra.getStrTx_corele()!=null){
                preSta.setString(10, tbm_tra.getStrTx_corele());
            }else{
                preSta.setNull(10, Types.VARCHAR);
            }
            //tx_sex 11
            if(tbm_tra.getStrTx_sex()!=null){
                preSta.setString(11, tbm_tra.getStrTx_sex());
            }else{
                preSta.setNull(11, Types.VARCHAR);
            }
            //fe_nac 12
            if(tbm_tra.getDatFe_nac()!=null){
                preSta.setDate(12, new Date(tbm_tra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(12, Types.DATE);
            }
            //co_ciunac 13
            if(tbm_tra.getIntCo_ciunac()>0){
                preSta.setInt(13, tbm_tra.getIntCo_ciunac());
            }else{
                preSta.setNull(13, Types.INTEGER);
            }
            //co_estciv 14
            if(tbm_tra.getIntCo_estciv()>0){
                preSta.setInt(14, tbm_tra.getIntCo_estciv());
            }else{
                preSta.setNull(14, Types.INTEGER);
            }
            //ne_numhij 15
            if(tbm_tra.getIntNe_numhij()>0){
                preSta.setInt(15, tbm_tra.getIntNe_numhij());
            }else{
                //preSta.setNull(16, Types.INTEGER);
                preSta.setInt(15, 0);
            }
            //tx_obs1 16
            if(tbm_tra.getStrTx_obs1()!=null){
                preSta.setString(16, tbm_tra.getStrTx_obs1());
            }else{
                preSta.setNull(16, Types.VARCHAR);
            }
            //tx_obs2 17
            if(tbm_tra.getStrTx_obs2()!=null){
                preSta.setString(17, tbm_tra.getStrTx_obs2());
            }else{
                preSta.setNull(17, Types.VARCHAR);
            }
            //st_reg 18
            if(tbm_tra.getStrSt_reg()!=null){
                preSta.setString(18, tbm_tra.getStrSt_reg());
            }else{
                preSta.setNull(19, Types.VARCHAR);
            }
            //fe_ing 19
            if(tbm_tra.getDatFe_ing()!=null){
                preSta.setTimestamp(19, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(19, Types.TIMESTAMP);
            }
            //fe_ultmod 20
            if(tbm_tra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(20, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(20, Types.TIMESTAMP);
            }
            //co_usring 21
            if(tbm_tra.getIntCo_usring()>0){
                preSta.setInt(21, tbm_tra.getIntCo_usring());
            }else{
                preSta.setNull(21, Types.INTEGER);
            }
            //co_usrmod 22
            if(tbm_tra.getIntCo_usrmod()>0){
                preSta.setInt(22, tbm_tra.getIntCo_usrmod());
            }else{
                preSta.setNull(22, Types.INTEGER);
            }
            preSta.executeUpdate();
            
            String strSql2 = "insert into tbm_tra(co_tra,tx_ide,tx_nom,tx_ape,st_reg) ";
            strSql2 += " values("+tbm_tra.getIntCo_tra()
                    + ",'','','','I')";
            preSta2 = con2.prepareStatement(strSql2);
            preSta2.executeUpdate();
        }
        finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }
    
    
/**
     * Graba en la tabla tbm_tra.
     * @param connection
     * @param tbm_tra Objeto POJO que contiene los datos de la tabla tbm_tra
     * @throws SQLException
     */
    public void grabar(Connection connection,Connection conSec, Tbm_tra tbm_tra,ZafParSis objZafParSis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        //Conexion para base alterna.
        Connection con2 = null;
        PreparedStatement preSta2 = null;
        try{
           
            strSql = "insert into tbm_tra(co_tra,tx_ide,tx_nom,tx_ape,tx_dir,tx_refdir,tx_tel1,tx_tel2,tx_tel3,tx_corele,tx_sex,fe_nac,co_ciunac,co_estciv,ne_numhij,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod) ";
            strSql += "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_tra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_tra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //tx_ide 2
            if(tbm_tra.getStrTx_ide()!=null){
                preSta.setString(2, tbm_tra.getStrTx_ide());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_nom 3
            if(tbm_tra.getStrTx_nom()!=null){
                preSta.setString(3, tbm_tra.getStrTx_nom());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //tx_ape 4
            if(tbm_tra.getStrTx_ape()!=null){
                preSta.setString(4, tbm_tra.getStrTx_ape());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_dir 5
            if(tbm_tra.getStrTx_dir()!=null){
                preSta.setString(5, tbm_tra.getStrTx_dir());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //tx_refdir 6
            if(tbm_tra.getStrTx_refdir()!=null){
                preSta.setString(6, tbm_tra.getStrTx_refdir());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_tel1 7
            if(tbm_tra.getStrTx_tel1()!=null){
                preSta.setString(7, tbm_tra.getStrTx_tel1());
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }
            //tx_tel2 8
            if(tbm_tra.getStrTx_tel2()!=null){
                preSta.setString(8, tbm_tra.getStrTx_tel2());
            }else{
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_tel3 9
            if(tbm_tra.getStrTx_tel3()!=null){
                preSta.setString(9, tbm_tra.getStrTx_tel3());
            }else{
                preSta.setNull(9, Types.VARCHAR);
            }
            //tx_corele 10
            if(tbm_tra.getStrTx_corele()!=null){
                preSta.setString(10, tbm_tra.getStrTx_corele());
            }else{
                preSta.setNull(10, Types.VARCHAR);
            }
            //tx_sex 11
            if(tbm_tra.getStrTx_sex()!=null){
                preSta.setString(11, tbm_tra.getStrTx_sex());
            }else{
                preSta.setNull(11, Types.VARCHAR);
            }
            //fe_nac 12
            if(tbm_tra.getDatFe_nac()!=null){
                preSta.setDate(12, new Date(tbm_tra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(12, Types.DATE);
            }
            //co_ciunac 13
            if(tbm_tra.getIntCo_ciunac()>0){
                preSta.setInt(13, tbm_tra.getIntCo_ciunac());
            }else{
                preSta.setNull(13, Types.INTEGER);
            }
            //co_estciv 14
            if(tbm_tra.getIntCo_estciv()>0){
                preSta.setInt(14, tbm_tra.getIntCo_estciv());
            }else{
                preSta.setNull(14, Types.INTEGER);
            }
            //ne_numhij 15
            if(tbm_tra.getIntNe_numhij()>0){
                preSta.setInt(15, tbm_tra.getIntNe_numhij());
            }else{
                //preSta.setNull(16, Types.INTEGER);
                preSta.setInt(15, 0);
            }
            //tx_obs1 16
            if(tbm_tra.getStrTx_obs1()!=null){
                preSta.setString(16, tbm_tra.getStrTx_obs1());
            }else{
                preSta.setNull(16, Types.VARCHAR);
            }
            //tx_obs2 17
            if(tbm_tra.getStrTx_obs2()!=null){
                preSta.setString(17, tbm_tra.getStrTx_obs2());
            }else{
                preSta.setNull(17, Types.VARCHAR);
            }
            //st_reg 18
            if(tbm_tra.getStrSt_reg()!=null){
                preSta.setString(18, tbm_tra.getStrSt_reg());
            }else{
                preSta.setNull(19, Types.VARCHAR);
            }
            //fe_ing 19
            if(tbm_tra.getDatFe_ing()!=null){
                preSta.setTimestamp(19, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(19, Types.TIMESTAMP);
            }
            //fe_ultmod 20
            if(tbm_tra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(20, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(20, Types.TIMESTAMP);
            }
            //co_usring 21
            if(tbm_tra.getIntCo_usring()>0){
                preSta.setInt(21, tbm_tra.getIntCo_usring());
            }else{
                preSta.setNull(21, Types.INTEGER);
            }
            //co_usrmod 22
            if(tbm_tra.getIntCo_usrmod()>0){
                preSta.setInt(22, tbm_tra.getIntCo_usrmod());
            }else{
                preSta.setNull(22, Types.INTEGER);
            }
            preSta.executeUpdate();
            
            String strSql2 = "insert into tbm_tra(co_tra,tx_ide,tx_nom,tx_ape,st_reg) ";
            strSql2 += " values("+tbm_tra.getIntCo_tra()
                    + ",'','','','I')";
            preSta2 = conSec.prepareStatement(strSql2);
            preSta2.executeUpdate();
        }
        finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }    

    /**
     * Actualiza datos de la tabla tbm_tra.
     * @param connection
     * @param tbm_tra Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_tra tbm_tra, ZafParSis objZafParSis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        
        try{
            strSql = "update tbm_tra ";
            strSql += "set tx_ide = ?, ";
            strSql += "tx_nom = ?, ";
            strSql += "tx_ape = ?, ";
            strSql += "tx_dir = ?, ";
            strSql += "tx_refdir = ?, ";
            strSql += "tx_tel1 = ?, ";
            strSql += "tx_tel2 = ?, ";
            strSql += "tx_tel3 = ?, ";
            strSql += "tx_corele = ?, ";
            strSql += "tx_sex = ?, ";
            strSql += "fe_nac = ?, ";
            strSql += "co_ciunac = ?, ";
            strSql += "co_estciv = ?, ";
            strSql += "ne_numhij = ?, ";
            strSql += "tx_obs1 = ?, ";
            strSql += "tx_obs2 = ?, ";
            strSql += "st_reg = ?, ";
            strSql += "fe_ing = ?, ";
            strSql += "fe_ultmod = ?, ";
            strSql += "co_usring = ?, ";
            strSql += "co_usrmod = ? ";
            strSql += "where co_tra = ?";

            preSta = connection.prepareStatement(strSql);
            
            //tx_ide 1
            if(tbm_tra.getStrTx_ide()!=null){
                preSta.setString(1, tbm_tra.getStrTx_ide());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //tx_nom 2
            if(tbm_tra.getStrTx_nom()!=null){
                preSta.setString(2, tbm_tra.getStrTx_nom());
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //tx_ape 3
            if(tbm_tra.getStrTx_ape()!=null){
                preSta.setString(3, tbm_tra.getStrTx_ape());
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //tx_dir 4
            if(tbm_tra.getStrTx_dir()!=null){
                preSta.setString(4, tbm_tra.getStrTx_dir());
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_refdir 5
            if(tbm_tra.getStrTx_refdir()!=null){
                preSta.setString(5, tbm_tra.getStrTx_refdir());
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //tx_tel1 6
            if(tbm_tra.getStrTx_tel1()!=null){
                preSta.setString(6, tbm_tra.getStrTx_tel1());
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_tel2 7
            if(tbm_tra.getStrTx_tel2()!=null){
                preSta.setString(7, tbm_tra.getStrTx_tel2());
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }
            //tx_tel3 8
            if(tbm_tra.getStrTx_tel3()!=null){
                preSta.setString(8, tbm_tra.getStrTx_tel3());
            }else{
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_corele 9
            if(tbm_tra.getStrTx_corele()!=null){
                preSta.setString(9, tbm_tra.getStrTx_corele());
            }else{
                preSta.setNull(9, Types.VARCHAR);
            }
            //tx_sex 10
            if(tbm_tra.getStrTx_sex()!=null){
                preSta.setString(10, tbm_tra.getStrTx_sex());
            }else{
                preSta.setNull(10, Types.VARCHAR);
            }
            //fe_nac 11
            if(tbm_tra.getDatFe_nac()!=null){
                preSta.setDate(11, new Date(tbm_tra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(11, Types.DATE);
            }
            //co_ciunac 12
            if(tbm_tra.getIntCo_ciunac()>0){
                preSta.setInt(12, tbm_tra.getIntCo_ciunac());
            }else{
                preSta.setNull(12, Types.INTEGER);
            }
            //co_estciv 13
            if(tbm_tra.getIntCo_estciv()>0){
                preSta.setInt(13, tbm_tra.getIntCo_estciv());
            }else{
                preSta.setNull(13, Types.INTEGER);
            }
            //ne_numhij 14
            if(tbm_tra.getIntNe_numhij()>0){
                preSta.setInt(14, tbm_tra.getIntNe_numhij());
            }else{
                //preSta.setNull(14, Types.INTEGER);
                preSta.setInt(14, 0);
            }
            //tx_obs1 15
            if(tbm_tra.getStrTx_obs1()!=null){
                preSta.setString(15, tbm_tra.getStrTx_obs1());
            }else{
                preSta.setNull(15, Types.VARCHAR);
            }
            //tx_obs2 16
            if(tbm_tra.getStrTx_obs2()!=null){
                preSta.setString(16, tbm_tra.getStrTx_obs2());
            }else{
                preSta.setNull(16, Types.VARCHAR);
            }
            //st_reg 17
            if(tbm_tra.getStrSt_reg()!=null){
                preSta.setString(17, tbm_tra.getStrSt_reg());
            }else{
                preSta.setNull(17, Types.VARCHAR);
            }
            //fe_ing 18
            if(tbm_tra.getDatFe_ing()!=null){
                preSta.setTimestamp(18, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(18, Types.TIMESTAMP);
            }
            //fe_ultmod 19
            if(tbm_tra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(19, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(19, Types.TIMESTAMP);
            }
            //co_usring 20
            if(tbm_tra.getIntCo_usring()>0){
                preSta.setInt(20, tbm_tra.getIntCo_usring());
            }else{
                preSta.setNull(20, Types.INTEGER);
            }
            //co_usrmod 21
            if(tbm_tra.getIntCo_usrmod()>0){
                preSta.setInt(21, tbm_tra.getIntCo_usrmod());
            }else{
                preSta.setNull(21, Types.INTEGER);
            }
//            //fe_iniCon 22
//            if(tbm_tra.getDatFe_IniCon()!=null){
//                preSta.setDate(22, new Date(tbm_tra.getDatFe_IniCon().getTime()));
//            }else{
//                preSta.setNull(22, Types.DATE);
//            }
//            //fe_finPerPruCon 23
//            if(tbm_tra.getDatFe_FinPerPru()!=null){
//                preSta.setDate(23, new Date(tbm_tra.getDatFe_FinPerPru().getTime()));
//            }else{
//                preSta.setNull(23, Types.DATE);
//            }
//            //fe_finCon 24
//            if(tbm_tra.getDatFe_FinCon()!=null){
//                preSta.setDate(24, new Date(tbm_tra.getDatFe_FinCon().getTime()));
//            }else{
//                preSta.setNull(24, Types.DATE);
//            }
            //co_tra 22
            if(tbm_tra.getIntCo_tra()>0){
                preSta.setInt(22, tbm_tra.getIntCo_tra());
            }else{
                preSta.setNull(22, Types.INTEGER);
            }
            

            preSta.executeUpdate();
            
            ZafUtil objUtil = new ZafUtil();
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                
                
                strSql="";
                strSql = "update tbm_traemp ";
                strSql += "set fe_ultMod = current_timestamp, ";
                strSql += "co_usrMod = "+objZafParSis.getCodigoUsuario()+", ";
                strSql += "tx_numCtaBan = " + objUtil.codificar(tbm_tra.getStrTx_NumCtaBan()) + " , ";
                strSql += "tx_tipCtaBan =  " + objUtil.codificar(tbm_tra.getStrTx_TipCtaBan())+ " , ";
                strSql += "fe_iniCon =  ? , ";
                strSql += "fe_finPerPruCon = ? , ";
                strSql += "fe_finCon = ? , ";
                strSql += "st_perPruCon = ? , ";
                strSql += "st_finCon = ? , ";
                strSql += "fe_reaFinCon = ? ";
                strSql += "where co_tra = ?";
                
                preSta = connection.prepareStatement(strSql);
                
                if(tbm_tra.getDatFe_IniCon()!=null){
                    preSta.setDate(1, new Date(tbm_tra.getDatFe_IniCon().getTime()));
                }else{
                    preSta.setNull(1, Types.DATE);
                }
                if(tbm_tra.getDatFe_FinPerPru()!=null){
                    preSta.setDate(2, new Date(tbm_tra.getDatFe_FinPerPru().getTime()));
                }else{
                    preSta.setNull(2, Types.DATE);
                }
                if(tbm_tra.getDatFe_FinCon()!=null){
                    preSta.setDate(3, new Date(tbm_tra.getDatFe_FinCon().getTime()));
                }else{
                    preSta.setNull(3, Types.DATE);
                }
                if(tbm_tra.getStrSt_perprucon()!=null){
                    preSta.setString(4, tbm_tra.getStrSt_perprucon());
                }else{
                    preSta.setNull(4, Types.VARCHAR);
                }
                if(tbm_tra.getStrSt_fincon()!=null){
                    preSta.setString(5, tbm_tra.getStrSt_fincon());
                }else{
                    preSta.setNull(5, Types.VARCHAR);
                }
                if(tbm_tra.getDatFe_FinReaCon()!=null){
                    preSta.setDate(6, new Date(tbm_tra.getDatFe_FinReaCon().getTime()));
                }else{
                    preSta.setNull(6, Types.DATE);
                }
                
                //co_tra 7
                if(tbm_tra.getIntCo_tra()>0){
                    preSta.setInt(7, tbm_tra.getIntCo_tra());
                }else{
                    preSta.setNull(7, Types.INTEGER);
                }
                
            }else{
                
                strSql="";
                strSql = "update tbm_traemp ";
                strSql += "set fe_ultMod = current_timestamp, ";
                strSql += "co_usrMod = "+objZafParSis.getCodigoUsuario()+", ";
                strSql += "tx_numCtaBan = " + objUtil.codificar(tbm_tra.getStrTx_NumCtaBan()) + " , ";
                strSql += "tx_tipCtaBan =  " + objUtil.codificar(tbm_tra.getStrTx_TipCtaBan()) + " , ";
                strSql += "fe_inicon = ? , ";
                strSql += "fe_finPerPruCon = ? , ";
                strSql += "fe_finCon = ? , ";
                strSql += "st_perPruCon = ? , ";
                strSql += "st_finCon = ? , ";
                strSql += "fe_reaFinCon = ? , ";
                strSql +=" co_dep = ? , ";
                strSql +=" co_pla = ? , ";
                strSql +=" co_car = ? ";
                strSql += " where co_tra = ?";
                strSql += " and co_emp = " + objZafParSis.getCodigoEmpresa();
                
                preSta = connection.prepareStatement(strSql);
                
                if(tbm_tra.getDatFe_IniCon()!=null){
                    preSta.setDate(1, new Date(tbm_tra.getDatFe_IniCon().getTime()));
                }else{
                    preSta.setNull(1, Types.DATE);
                }
                if(tbm_tra.getDatFe_FinPerPru()!=null){
                    preSta.setDate(2, new Date(tbm_tra.getDatFe_FinPerPru().getTime()));
                }else{
                    preSta.setNull(2, Types.DATE);
                }
                if(tbm_tra.getDatFe_FinCon()!=null){
                    preSta.setDate(3, new Date(tbm_tra.getDatFe_FinCon().getTime()));
                }else{
                    preSta.setNull(3, Types.DATE);
                }
                if(tbm_tra.getStrSt_perprucon()!=null){
                    preSta.setString(4, tbm_tra.getStrSt_perprucon());
                }else{
                    preSta.setNull(4, Types.VARCHAR);
                }
                if(tbm_tra.getStrSt_fincon()!=null){
                    preSta.setString(5, tbm_tra.getStrSt_fincon());
                }else{
                    preSta.setNull(5, Types.VARCHAR);
                }
                if(tbm_tra.getDatFe_FinReaCon()!=null){
                    preSta.setDate(6, new Date(tbm_tra.getDatFe_FinReaCon().getTime()));
                }else{
                    preSta.setNull(6, Types.DATE);
                }
                
                //co_dep 7
                if(tbm_tra.getIntCoDep()>0){
                    preSta.setInt(7, tbm_tra.getIntCoDep());
                }else{
                    preSta.setNull(7, Types.INTEGER);
                }
                
                //co_pla 8
                if(tbm_tra.getIntCoPla()>0){
                    preSta.setInt(8, tbm_tra.getIntCoPla());
                }else{
                    preSta.setNull(8, Types.INTEGER);
                }
                
                //co_car 9
                if(tbm_tra.getIntCoCarLab()>0){
                    preSta.setInt(9, tbm_tra.getIntCoCarLab());
                }else{
                    preSta.setNull(9, Types.INTEGER);
                }
                
                //co_tra 10
                if(tbm_tra.getIntCo_tra()>0){
                    preSta.setInt(10, tbm_tra.getIntCo_tra());
                }else{
                    preSta.setNull(10, Types.INTEGER);
                }
                
            }
            
            preSta.executeUpdate();
            
            
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }
    
    /**
     * Actualiza datos de la tabla tbm_tra.
     * @param connection
     * @param tbm_tra Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void anular(Connection connection, Tbm_tra tbm_tra, ZafParSis objZafParSis) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            
            
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                
                strSql="";
                strSql = "update tbm_tra ";
                strSql += "set st_reg = ?";
                strSql += "where co_tra = ?";
                
                preSta = connection.prepareStatement(strSql);
                
                //st_reg 1
                if(tbm_tra.getStrSt_reg()!=null){
                    preSta.setString(1, tbm_tra.getStrSt_reg());
                }else{
                    preSta.setNull(1, Types.VARCHAR);
                } 
                
                
                //co_tra 2
                if(tbm_tra.getIntCo_tra()>0){
                    preSta.setInt(2, tbm_tra.getIntCo_tra());
                }else{
                    preSta.setNull(2, Types.INTEGER);
                }
                
                preSta.executeUpdate();
                
                
                strSql="";
                strSql = "update tbm_traemp ";
                strSql += "set st_reg = ? , fe_reafincon = current_date ";
                strSql += "where co_tra = ?";
                
                preSta = connection.prepareStatement(strSql);
                
                //st_reg 1
                if(tbm_tra.getStrSt_reg()!=null){
                    preSta.setString(1, tbm_tra.getStrSt_reg());
                }else{
                    preSta.setNull(1, Types.VARCHAR);
                } 

                //co_tra 2
                if(tbm_tra.getIntCo_tra()>0){
                    preSta.setInt(2, tbm_tra.getIntCo_tra());
                }else{
                    preSta.setNull(2, Types.INTEGER);
                }
                
                preSta.executeUpdate();
                
                
            }else{
                
                strSql="";
                strSql = "update tbm_traemp ";
                strSql += "set st_reg = ? , fe_reafincon = current_date ";
                strSql += "where co_tra = ?";
                strSql += " and co_emp = " + objZafParSis.getCodigoEmpresa();
                
                preSta = connection.prepareStatement(strSql);
                
                //st_reg 1
                if(tbm_tra.getStrSt_reg()!=null){
                    preSta.setString(1, tbm_tra.getStrSt_reg());
                }else{
                    preSta.setNull(1, Types.VARCHAR);
                } 

                //co_tra 2
                if(tbm_tra.getIntCo_tra()>0){
                    preSta.setInt(2, tbm_tra.getIntCo_tra());
                }else{
                    preSta.setNull(2, Types.INTEGER);
                }
                
                preSta.executeUpdate();
                
            }
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_tra
     * @param connection
     * @param tbm_tra Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_tra cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_tra> consultarLisGen(Connection connection, Tbm_tra tbm_tra) throws SQLException {
        List<Tbm_tra> lisTbm_tra = null;
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;

        try{
            strSql = "select * from tbm_tra ";
            strSql += "where (? is null or co_tra = ?) ";
            strSql += "and (? is null or lower(tx_ide) like lower(?)) ";
            strSql += "and (? is null or lower(tx_nom) like lower(?)) ";
            strSql += "and (? is null or lower(tx_ape) like lower(?)) ";
            strSql += "and (? is null or lower(tx_dir) like lower(?)) ";
            strSql += "and (? is null or lower(tx_refdir) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel1) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel2) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel3) like lower(?)) ";
            strSql += "and (? is null or lower(tx_corele) like lower(?)) ";
            strSql += "and (? is null or lower(tx_sex) like lower(?)) ";
            strSql += "and (? is null or fe_nac = ?) ";
            strSql += "and (? is null or co_ciunac = ?) ";
            strSql += "and (? is null or co_estciv = ?) ";
            strSql += "and (? is null or ne_numhij = ?) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(tx_obs2) like lower(?)) ";
            strSql += "and ('A' is null or lower(st_reg) like lower('A')) ";
            //strSql += "and st_reg like ? ";
            //strSql += "and (? is null or fe_ing = ?) ";
            strSql += "and (?::timestamp without time zone is null or fe_ing = ?::timestamp without time zone) ";
            //strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (?::timestamp without time zone is null or fe_ultmod = ?::timestamp without time zone) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            //strSql += "order by co_tra";
            strSql += "order by (tx_ape || ' ' || tx_nom)";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_tra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_tra.getIntCo_tra());
                preSta.setInt(2, tbm_tra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_ide 2
            if(tbm_tra.getStrTx_ide()!=null){
                preSta.setString(3, tbm_tra.getStrTx_ide().replace('*', '%'));
                preSta.setString(4, tbm_tra.getStrTx_ide().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_nom 3
            if(tbm_tra.getStrTx_nom()!=null){
                preSta.setString(5, tbm_tra.getStrTx_nom().replace('*', '%'));
                preSta.setString(6, tbm_tra.getStrTx_nom().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_ape 4
            if(tbm_tra.getStrTx_ape()!=null){
                preSta.setString(7, tbm_tra.getStrTx_ape().replace('*', '%'));
                preSta.setString(8, tbm_tra.getStrTx_ape().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_dir 5
            if(tbm_tra.getStrTx_dir()!=null){
                preSta.setString(9, tbm_tra.getStrTx_dir().replace('*', '%'));
                preSta.setString(10, tbm_tra.getStrTx_dir().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //tx_refdir 6
            if(tbm_tra.getStrTx_refdir()!=null){
                preSta.setString(11, tbm_tra.getStrTx_refdir().replace('*', '%'));
                preSta.setString(12, tbm_tra.getStrTx_refdir().replace('*', '%'));
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //tx_tel1 7
            if(tbm_tra.getStrTx_tel1()!=null){
                preSta.setString(13, tbm_tra.getStrTx_tel1().replace('*', '%'));
                preSta.setString(14, tbm_tra.getStrTx_tel1().replace('*', '%'));
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }
            //tx_tel2 8
            if(tbm_tra.getStrTx_tel2()!=null){
                preSta.setString(15, tbm_tra.getStrTx_tel2().replace('*', '%'));
                preSta.setString(16, tbm_tra.getStrTx_tel2().replace('*', '%'));
            }else{
                preSta.setNull(15, Types.VARCHAR);
                preSta.setNull(16, Types.VARCHAR);
            }
            //tx_tel3 9
            if(tbm_tra.getStrTx_tel3()!=null){
                preSta.setString(17, tbm_tra.getStrTx_tel3().replace('*', '%'));
                preSta.setString(18, tbm_tra.getStrTx_tel3().replace('*', '%'));
            }else{
                preSta.setNull(17, Types.VARCHAR);
                preSta.setNull(18, Types.VARCHAR);
            }
            //tx_corele 10
            if(tbm_tra.getStrTx_corele()!=null){
                preSta.setString(19, tbm_tra.getStrTx_corele().replace('*', '%'));
                preSta.setString(20, tbm_tra.getStrTx_corele().replace('*', '%'));
            }else{
                preSta.setNull(19, Types.VARCHAR);
                preSta.setNull(20, Types.VARCHAR);
            }
            //tx_sex 11
            if(tbm_tra.getStrTx_sex()!=null){
                preSta.setString(21, tbm_tra.getStrTx_sex().replace('*', '%'));
                preSta.setString(22, tbm_tra.getStrTx_sex().replace('*', '%'));
            }else{
                preSta.setNull(21, Types.VARCHAR);
                preSta.setNull(22, Types.VARCHAR);
            }
            //fe_nac 12
            if(tbm_tra.getDatFe_nac()!=null){
                preSta.setDate(23, new Date(tbm_tra.getDatFe_nac().getTime()));
                preSta.setDate(24, new Date(tbm_tra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(23, Types.DATE);
                preSta.setNull(24, Types.DATE);
            }
            //co_ciunac 13
            if(tbm_tra.getIntCo_ciunac()>0){
                preSta.setInt(25, tbm_tra.getIntCo_ciunac());
                preSta.setInt(26, tbm_tra.getIntCo_ciunac());
            }else{
                preSta.setNull(25, Types.INTEGER);
                preSta.setNull(26, Types.INTEGER);
            }
            //co_estciv 14
            if(tbm_tra.getIntCo_estciv()>0){
                preSta.setInt(27, tbm_tra.getIntCo_estciv());
                preSta.setInt(28, tbm_tra.getIntCo_estciv());
            }else{
                preSta.setNull(27, Types.INTEGER);
                preSta.setNull(28, Types.INTEGER);
            }
            //ne_numhij 15
            if(tbm_tra.getIntNe_numhij()>0){
                preSta.setInt(29, tbm_tra.getIntNe_numhij());
                preSta.setInt(30, tbm_tra.getIntNe_numhij());
            }else{
                preSta.setNull(29, Types.INTEGER);
                preSta.setNull(30, Types.INTEGER);
            }
            //tx_obs1 16
            if(tbm_tra.getStrTx_obs1()!=null){
                preSta.setString(31, tbm_tra.getStrTx_obs1().replace('*', '%'));
                preSta.setString(32, tbm_tra.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(31, Types.VARCHAR);
                preSta.setNull(32, Types.VARCHAR);
            }
            //tx_obs2 17
            if(tbm_tra.getStrTx_obs2()!=null){
                preSta.setString(33, tbm_tra.getStrTx_obs2().replace('*', '%'));
                preSta.setString(34, tbm_tra.getStrTx_obs2().replace('*', '%'));
            }else{
                preSta.setNull(33, Types.VARCHAR);
                preSta.setNull(34, Types.VARCHAR);
            }
            //st_reg 18
            /*if(tbm_tra.getStrSt_reg()!=null){
                preSta.setString(35, tbm_tra.getStrSt_reg());
                preSta.setString(36, tbm_tra.getStrSt_reg());
            }else{
                preSta.setNull(35, Types.VARCHAR);
                preSta.setNull(36, Types.VARCHAR);
            }*/
            //fe_ing 19
            if(tbm_tra.getDatFe_ing()!=null){
                preSta.setTimestamp(35, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
                preSta.setTimestamp(36, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(35, Types.TIMESTAMP);
                preSta.setNull(36, Types.TIMESTAMP);
            }
            //fe_ultmod 20
            if(tbm_tra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(37, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(38, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(37, Types.TIMESTAMP);
                preSta.setNull(38, Types.TIMESTAMP);
            }
            //co_usring 21
            if(tbm_tra.getIntCo_usring()>0){
                preSta.setInt(39, tbm_tra.getIntCo_usring());
                preSta.setInt(40, tbm_tra.getIntCo_usring());
            }else{
                preSta.setNull(39, Types.INTEGER);
                preSta.setNull(40, Types.INTEGER);
            }
            //co_usrmod 22
            if(tbm_tra.getIntCo_usrmod()>0){
                preSta.setInt(41, tbm_tra.getIntCo_usrmod());
                preSta.setInt(42, tbm_tra.getIntCo_usrmod());
            }else{
                preSta.setNull(41, Types.INTEGER);
                preSta.setNull(42, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tra = new ArrayList<Tbm_tra>();
                do{
                    Tbm_tra tmp = new Tbm_tra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setStrTx_ide(resSet.getString("tx_ide"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_ape(resSet.getString("tx_ape"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_refdir(resSet.getString("tx_refdir"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_tel2(resSet.getString("tx_tel2"));
                    tmp.setStrTx_tel3(resSet.getString("tx_tel3"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_sex(resSet.getString("tx_sex"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_ciunac(resSet.getInt("co_ciunac"));
                    tmp.setIntCo_estciv(resSet.getInt("co_estciv"));
                    tmp.setIntNe_numhij(resSet.getInt("ne_numhij"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_tra.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tra;
    }

    public List<Tbm_tra> consultarLisGen(Connection connection, int intCodEmp) throws SQLException {
        List<Tbm_tra> lisTbm_tra = null;
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;

        try{
            strSql = "select * from tbm_tra a where co_tra  in(select co_tra from tbm_traemp b where co_emp = ? and st_reg like 'A') ";
            //strSql += "order by co_tra";
            strSql += "order by (tx_ape || ' ' || tx_nom)";

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(intCodEmp>0){
                preSta.setInt(1, intCodEmp);
                //preSta.setInt(2, intCodEmp);
            }else{
                preSta.setNull(1, Types.INTEGER);
                //preSta.setNull(2, Types.INTEGER);
            }
            /*tx_ide 2
            if(tbm_tra.getStrTx_ide()!=null){
                preSta.setString(3, tbm_tra.getStrTx_ide().replace('*', '%'));
                preSta.setString(4, tbm_tra.getStrTx_ide().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_nom 3
            if(tbm_tra.getStrTx_nom()!=null){
                preSta.setString(5, tbm_tra.getStrTx_nom().replace('*', '%'));
                preSta.setString(6, tbm_tra.getStrTx_nom().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_ape 4
            if(tbm_tra.getStrTx_ape()!=null){
                preSta.setString(7, tbm_tra.getStrTx_ape().replace('*', '%'));
                preSta.setString(8, tbm_tra.getStrTx_ape().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_dir 5
            if(tbm_tra.getStrTx_dir()!=null){
                preSta.setString(9, tbm_tra.getStrTx_dir().replace('*', '%'));
                preSta.setString(10, tbm_tra.getStrTx_dir().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //tx_refdir 6
            if(tbm_tra.getStrTx_refdir()!=null){
                preSta.setString(11, tbm_tra.getStrTx_refdir().replace('*', '%'));
                preSta.setString(12, tbm_tra.getStrTx_refdir().replace('*', '%'));
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //tx_tel1 7
            if(tbm_tra.getStrTx_tel1()!=null){
                preSta.setString(13, tbm_tra.getStrTx_tel1().replace('*', '%'));
                preSta.setString(14, tbm_tra.getStrTx_tel1().replace('*', '%'));
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }
            //tx_tel2 8
            if(tbm_tra.getStrTx_tel2()!=null){
                preSta.setString(15, tbm_tra.getStrTx_tel2().replace('*', '%'));
                preSta.setString(16, tbm_tra.getStrTx_tel2().replace('*', '%'));
            }else{
                preSta.setNull(15, Types.VARCHAR);
                preSta.setNull(16, Types.VARCHAR);
            }
            //tx_tel3 9
            if(tbm_tra.getStrTx_tel3()!=null){
                preSta.setString(17, tbm_tra.getStrTx_tel3().replace('*', '%'));
                preSta.setString(18, tbm_tra.getStrTx_tel3().replace('*', '%'));
            }else{
                preSta.setNull(17, Types.VARCHAR);
                preSta.setNull(18, Types.VARCHAR);
            }
            //tx_corele 10
            if(tbm_tra.getStrTx_corele()!=null){
                preSta.setString(19, tbm_tra.getStrTx_corele().replace('*', '%'));
                preSta.setString(20, tbm_tra.getStrTx_corele().replace('*', '%'));
            }else{
                preSta.setNull(19, Types.VARCHAR);
                preSta.setNull(20, Types.VARCHAR);
            }
            //tx_sex 11
            if(tbm_tra.getStrTx_sex()!=null){
                preSta.setString(21, tbm_tra.getStrTx_sex().replace('*', '%'));
                preSta.setString(22, tbm_tra.getStrTx_sex().replace('*', '%'));
            }else{
                preSta.setNull(21, Types.VARCHAR);
                preSta.setNull(22, Types.VARCHAR);
            }
            //fe_nac 12
            if(tbm_tra.getDatFe_nac()!=null){
                preSta.setDate(23, new Date(tbm_tra.getDatFe_nac().getTime()));
                preSta.setDate(24, new Date(tbm_tra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(23, Types.DATE);
                preSta.setNull(24, Types.DATE);
            }
            //co_ciunac 13
            if(tbm_tra.getIntCo_ciunac()>0){
                preSta.setInt(25, tbm_tra.getIntCo_ciunac());
                preSta.setInt(26, tbm_tra.getIntCo_ciunac());
            }else{
                preSta.setNull(25, Types.INTEGER);
                preSta.setNull(26, Types.INTEGER);
            }
            //co_estciv 14
            if(tbm_tra.getIntCo_estciv()>0){
                preSta.setInt(27, tbm_tra.getIntCo_estciv());
                preSta.setInt(28, tbm_tra.getIntCo_estciv());
            }else{
                preSta.setNull(27, Types.INTEGER);
                preSta.setNull(28, Types.INTEGER);
            }
            //ne_numhij 15
            if(tbm_tra.getIntNe_numhij()>0){
                preSta.setInt(29, tbm_tra.getIntNe_numhij());
                preSta.setInt(30, tbm_tra.getIntNe_numhij());
            }else{
                preSta.setNull(29, Types.INTEGER);
                preSta.setNull(30, Types.INTEGER);
            }
            //tx_obs1 16
            if(tbm_tra.getStrTx_obs1()!=null){
                preSta.setString(31, tbm_tra.getStrTx_obs1().replace('*', '%'));
                preSta.setString(32, tbm_tra.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(31, Types.VARCHAR);
                preSta.setNull(32, Types.VARCHAR);
            }
            //tx_obs2 17
            if(tbm_tra.getStrTx_obs2()!=null){
                preSta.setString(33, tbm_tra.getStrTx_obs2().replace('*', '%'));
                preSta.setString(34, tbm_tra.getStrTx_obs2().replace('*', '%'));
            }else{
                preSta.setNull(33, Types.VARCHAR);
                preSta.setNull(34, Types.VARCHAR);
            }
            //st_reg 18
            if(tbm_tra.getStrSt_reg()!=null){
                preSta.setString(35, tbm_tra.getStrSt_reg());
                preSta.setString(36, tbm_tra.getStrSt_reg());
            }else{
                preSta.setNull(35, Types.VARCHAR);
                preSta.setNull(36, Types.VARCHAR);
            }
            //fe_ing 19
            if(tbm_tra.getDatFe_ing()!=null){
                preSta.setTimestamp(37, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
                preSta.setTimestamp(38, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(37, Types.TIMESTAMP);
                preSta.setNull(38, Types.TIMESTAMP);
            }
            //fe_ultmod 20
            if(tbm_tra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(39, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(40, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(39, Types.TIMESTAMP);
                preSta.setNull(40, Types.TIMESTAMP);
            }
            //co_usring 21
            if(tbm_tra.getIntCo_usring()>0){
                preSta.setInt(41, tbm_tra.getIntCo_usring());
                preSta.setInt(42, tbm_tra.getIntCo_usring());
            }else{
                preSta.setNull(41, Types.INTEGER);
                preSta.setNull(42, Types.INTEGER);
            }
            //co_usrmod 22
            if(tbm_tra.getIntCo_usrmod()>0){
                preSta.setInt(43, tbm_tra.getIntCo_usrmod());
                preSta.setInt(44, tbm_tra.getIntCo_usrmod());
            }else{
                preSta.setNull(43, Types.INTEGER);
                preSta.setNull(44, Types.INTEGER);
            }*/

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tra = new ArrayList<Tbm_tra>();
                do{
                    Tbm_tra tmp = new Tbm_tra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setStrTx_ide(resSet.getString("tx_ide"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_ape(resSet.getString("tx_ape"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_refdir(resSet.getString("tx_refdir"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_tel2(resSet.getString("tx_tel2"));
                    tmp.setStrTx_tel3(resSet.getString("tx_tel3"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_sex(resSet.getString("tx_sex"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_ciunac(resSet.getInt("co_ciunac"));
                    tmp.setIntCo_estciv(resSet.getInt("co_estciv"));
                    tmp.setIntNe_numhij(resSet.getInt("ne_numhij"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_tra.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tra;
    }

    public List<Tbm_tra> consultarLisGen(Connection connection, Tbm_tra tbm_tra, int intCodEmp) throws SQLException {
        List<Tbm_tra> lisTbm_tra = null;
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;

        try{
            strSql = "select * from tbm_tra a where co_tra = "+  tbm_tra.getIntCo_tra() +
                    " and co_tra in(select co_tra from tbm_traemp b where co_emp = " + intCodEmp + " )" +
                    //" order by co_tra";
                    "order by (tx_ape || ' ' || tx_nom)";

            preSta = connection.prepareStatement(strSql);
            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tra = new ArrayList<Tbm_tra>();
                do{
                    Tbm_tra tmp = new Tbm_tra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setStrTx_ide(resSet.getString("tx_ide"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_ape(resSet.getString("tx_ape"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_refdir(resSet.getString("tx_refdir"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_tel2(resSet.getString("tx_tel2"));
                    tmp.setStrTx_tel3(resSet.getString("tx_tel3"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_sex(resSet.getString("tx_sex"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_ciunac(resSet.getInt("co_ciunac"));
                    tmp.setIntCo_estciv(resSet.getInt("co_estciv"));
                    tmp.setIntNe_numhij(resSet.getInt("ne_numhij"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_tra.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tra;
    }

    /**
     * Consulta general de la tabla tbm_tra aplicando pagineo
     * @param connection
     * @param tbm_tra Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return Retorna lista de tipo Tbm_tra con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_tra> consultarLisPag(Connection connection, Tbm_tra tbm_tra, int intRegIni, int intCanReg,  ZafParSis objZafParSis) throws SQLException{
        List<Tbm_tra> lisTbm_tra = null;
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;

        try{
            
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                strSql="";
                strSql += "select * from tbm_tra a ";
                strSql += "inner join tbm_traemp b on (a.co_tra=b.co_tra) ";
                strSql += "where (? is null or a.co_tra = ?) ";
                strSql += "and (? is null or lower(a.tx_ide) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_nom) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_ape) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_dir) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_refdir) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_tel1) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_tel2) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_tel3) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_corele) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_sex) like lower(?)) ";
                strSql += "and (? is null or a.fe_nac = ?::Date) ";
                strSql += "and (? is null or a.co_ciunac = ?) ";
                strSql += "and (? is null or a.co_estciv = ?) ";
                strSql += "and (? is null or a.ne_numhij = ?) ";
                strSql += "and (? is null or lower(a.tx_obs1) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_obs2) like lower(?)) ";
                strSql += "and (? is null or lower(a.st_reg) like lower(?)) ";
                strSql += "and (?::timestamp without time zone is null or a.fe_ing = ?::timestamp without time zone) ";
                strSql += "and (?::timestamp without time zone is null or a.fe_ultmod = ?::timestamp without time zone) ";
                strSql += "and (? is null or a.co_usring = ?) ";
                strSql += "and (? is null or a.co_usrmod = ?) ";
                
                strSql += "and (? is null or b.tx_numctaban = ?) ";
                strSql += "and (? is null or b.tx_tipctaban = ?) ";
                
                
                
//                strSql += "and (? is null or b.fe_iniCon = ?) ";
//                strSql += "and (? is null or b.fe_finPerPruCon = ?) ";
//                strSql += "and (? is null or b.fe_finCon = ?) ";
//                strSql += "and (? is null or b.st_perPruCon = ?) ";
//                strSql += "and (? is null or b.st_finCon = ?) ";
//                strSql += "and (? is null or b.fe_reaFinCon = ?) ";

                
                strSql += "order by a.co_tra ";
                strSql += "limit ? offset ?";
            }else{
                strSql="";
                strSql += "select a.co_tra, a.tx_ide, a.tx_nom, a.tx_ape, a.tx_dir, a.tx_refdir, a.tx_tel1, a.tx_tel2, ";
                strSql += " a.tx_tel3, a.tx_corele, a.tx_sex, a.fe_nac, a.co_ciunac, a.co_estciv, a.ne_numhij, ";
                strSql += " a.tx_obs1, a.tx_obs2, b.st_reg, a.fe_ing, a.fe_ultmod, a.co_usring, a.co_usrmod, b.tx_numctaban, b.tx_tipctaban, b.fe_iniCon, ";
                strSql += " b.fe_finPerPruCon, b.fe_finCon, b.st_perPruCon, b.st_finCon, b.fe_reaFinCon, b.co_pla, b.co_dep, b.co_ofi , b.co_car , b.st_recalm, b.nd_valalm, b.tx_forrecalm  from tbm_tra a ";
                strSql += "inner join tbm_traemp b on (a.co_tra=b.co_tra and b.co_emp = " + objZafParSis.getCodigoEmpresa() + " ) ";
                strSql += "where (? is null or a.co_tra = ?) ";
                strSql += "and (? is null or lower(a.tx_ide) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_nom) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_ape) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_dir) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_refdir) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_tel1) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_tel2) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_tel3) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_corele) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_sex) like lower(?)) ";
                strSql += "and (? is null or a.fe_nac = ?::Date) ";
                strSql += "and (? is null or a.co_ciunac = ?) ";
                strSql += "and (? is null or a.co_estciv = ?) ";
                strSql += "and (? is null or a.ne_numhij = ?) ";
                strSql += "and (? is null or lower(a.tx_obs1) like lower(?)) ";
                strSql += "and (? is null or lower(a.tx_obs2) like lower(?)) ";
                strSql += "and (? is null or lower(a.st_reg) like lower(?)) ";
                strSql += "and (?::timestamp without time zone is null or a.fe_ing = ?::timestamp without time zone) ";
                strSql += "and (?::timestamp without time zone is null or a.fe_ultmod = ?::timestamp without time zone) ";
                strSql += "and (? is null or a.co_usring = ?) ";
                strSql += "and (? is null or a.co_usrmod = ?) ";
                
                strSql += "and (? is null or b.tx_numctaban = ?) ";
                strSql += "and (? is null or b.tx_tipctaban = ?) ";
                
//                strSql += "and (? is null or b.fe_iniCon = ?) ";
//                strSql += "and (? is null or b.fe_finPerPruCon = ?) ";
//                strSql += "and (? is null or b.fe_finCon = ?) ";
//                strSql += "and (? is null or b.st_perPruCon = ?) ";
//                strSql += "and (? is null or b.st_finCon = ?) ";
//                strSql += "and (? is null or b.fe_reaFinCon = ?) ";
                
                strSql += "order by a.co_tra ";
                strSql += "limit ? offset ?";
            }
            

            preSta = connection.prepareStatement(strSql);
            //co_tra 1
            if(tbm_tra.getIntCo_tra()>0){
                preSta.setInt(1, tbm_tra.getIntCo_tra());
                preSta.setInt(2, tbm_tra.getIntCo_tra());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_ide 2
            if(tbm_tra.getStrTx_ide()!=null){
                preSta.setString(3, tbm_tra.getStrTx_ide().replace('*', '%'));
                preSta.setString(4, tbm_tra.getStrTx_ide().replace('*', '%'));
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_nom 3
            if(tbm_tra.getStrTx_nom()!=null){
                preSta.setString(5, tbm_tra.getStrTx_nom().replace('*', '%'));
                preSta.setString(6, tbm_tra.getStrTx_nom().replace('*', '%'));
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_ape 4
            if(tbm_tra.getStrTx_ape()!=null){
                preSta.setString(7, tbm_tra.getStrTx_ape().replace('*', '%'));
                preSta.setString(8, tbm_tra.getStrTx_ape().replace('*', '%'));
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_dir 5
            if(tbm_tra.getStrTx_dir()!=null){
                preSta.setString(9, tbm_tra.getStrTx_dir().replace('*', '%'));
                preSta.setString(10, tbm_tra.getStrTx_dir().replace('*', '%'));
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //tx_refdir 6
            if(tbm_tra.getStrTx_refdir()!=null){
                preSta.setString(11, tbm_tra.getStrTx_refdir().replace('*', '%'));
                preSta.setString(12, tbm_tra.getStrTx_refdir().replace('*', '%'));
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //tx_tel1 7
            if(tbm_tra.getStrTx_tel1()!=null){
                preSta.setString(13, tbm_tra.getStrTx_tel1().replace('*', '%'));
                preSta.setString(14, tbm_tra.getStrTx_tel1().replace('*', '%'));
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }
            //tx_tel2 8
            if(tbm_tra.getStrTx_tel2()!=null){
                preSta.setString(15, tbm_tra.getStrTx_tel2().replace('*', '%'));
                preSta.setString(16, tbm_tra.getStrTx_tel2().replace('*', '%'));
            }else{
                preSta.setNull(15, Types.VARCHAR);
                preSta.setNull(16, Types.VARCHAR);
            }
            //tx_tel3 9
            if(tbm_tra.getStrTx_tel3()!=null){
                preSta.setString(17, tbm_tra.getStrTx_tel3().replace('*', '%'));
                preSta.setString(18, tbm_tra.getStrTx_tel3().replace('*', '%'));
            }else{
                preSta.setNull(17, Types.VARCHAR);
                preSta.setNull(18, Types.VARCHAR);
            }
            //tx_corele 10
            if(tbm_tra.getStrTx_corele()!=null){
                preSta.setString(19, tbm_tra.getStrTx_corele().replace('*', '%'));
                preSta.setString(20, tbm_tra.getStrTx_corele().replace('*', '%'));
            }else{
                preSta.setNull(19, Types.VARCHAR);
                preSta.setNull(20, Types.VARCHAR);
            }
            //tx_sex 11
            if(tbm_tra.getStrTx_sex()!=null){
                preSta.setString(21, tbm_tra.getStrTx_sex().replace('*', '%'));
                preSta.setString(22, tbm_tra.getStrTx_sex().replace('*', '%'));
            }else{
                preSta.setNull(21, Types.VARCHAR);
                preSta.setNull(22, Types.VARCHAR);
            }
            //fe_nac 12
            if(tbm_tra.getDatFe_nac()!=null){
                preSta.setDate(23, new Date(tbm_tra.getDatFe_nac().getTime()));
                preSta.setDate(24, new Date(tbm_tra.getDatFe_nac().getTime()));
            }else{
                preSta.setNull(23, Types.DATE);
                preSta.setNull(24, Types.DATE);
            }
            //co_ciunac 13
            if(tbm_tra.getIntCo_ciunac()>0){
                preSta.setInt(25, tbm_tra.getIntCo_ciunac());
                preSta.setInt(26, tbm_tra.getIntCo_ciunac());
            }else{
                preSta.setNull(25, Types.INTEGER);
                preSta.setNull(26, Types.INTEGER);
            }
            //co_estciv 14
            if(tbm_tra.getIntCo_estciv()>0){
                preSta.setInt(27, tbm_tra.getIntCo_estciv());
                preSta.setInt(28, tbm_tra.getIntCo_estciv());
            }else{
                preSta.setNull(27, Types.INTEGER);
                preSta.setNull(28, Types.INTEGER);
            }
            //ne_numhij 15
            if(tbm_tra.getIntNe_numhij()>0){
                preSta.setInt(29, tbm_tra.getIntNe_numhij());
                preSta.setInt(30, tbm_tra.getIntNe_numhij());
            }else{
                preSta.setNull(29, Types.INTEGER);
                preSta.setNull(30, Types.INTEGER);
            }
            //tx_obs1 16
            if(tbm_tra.getStrTx_obs1()!=null){
                preSta.setString(31, tbm_tra.getStrTx_obs1().replace('*', '%'));
                preSta.setString(32, tbm_tra.getStrTx_obs1().replace('*', '%'));
            }else{
                preSta.setNull(31, Types.VARCHAR);
                preSta.setNull(32, Types.VARCHAR);
            }
            //tx_obs2 17
            if(tbm_tra.getStrTx_obs2()!=null){
                preSta.setString(33, tbm_tra.getStrTx_obs2().replace('*', '%'));
                preSta.setString(34, tbm_tra.getStrTx_obs2().replace('*', '%'));
            }else{
                preSta.setNull(33, Types.VARCHAR);
                preSta.setNull(34, Types.VARCHAR);
            }
            //st_reg 18
            if(tbm_tra.getStrSt_reg()!=null){
                preSta.setString(35, tbm_tra.getStrSt_reg());
                preSta.setString(36, tbm_tra.getStrSt_reg());
            }else{
                preSta.setNull(35, Types.VARCHAR);
                preSta.setNull(36, Types.VARCHAR);
            }
            //fe_ing 19
            if(tbm_tra.getDatFe_ing()!=null){
                preSta.setTimestamp(37, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
                preSta.setTimestamp(38, new Timestamp(tbm_tra.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(37, Types.TIMESTAMP);
                preSta.setNull(38, Types.TIMESTAMP);
            }
            //fe_ultmod 20
            if(tbm_tra.getDatFe_ultmod()!=null){
                preSta.setTimestamp(39, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(40, new Timestamp(tbm_tra.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(39, Types.TIMESTAMP);
                preSta.setNull(40, Types.TIMESTAMP);
            }
            //co_usring 21
            if(tbm_tra.getIntCo_usring()>0){
                preSta.setInt(41, tbm_tra.getIntCo_usring());
                preSta.setInt(42, tbm_tra.getIntCo_usring());
            }else{
                preSta.setNull(41, Types.INTEGER);
                preSta.setNull(42, Types.INTEGER);
            }
            //co_usrmod 22
            if(tbm_tra.getIntCo_usrmod()>0){
                preSta.setInt(43, tbm_tra.getIntCo_usrmod());
                preSta.setInt(44, tbm_tra.getIntCo_usrmod());
            }else{
                preSta.setNull(43, Types.INTEGER);
                preSta.setNull(44, Types.INTEGER);
            }
            
            //tx_numctaban 23
            if(tbm_tra.getStrTx_NumCtaBan()!=null){
                preSta.setString(45, tbm_tra.getStrTx_NumCtaBan());
                preSta.setString(46, tbm_tra.getStrTx_NumCtaBan());
            }else{
                preSta.setNull(45, Types.VARCHAR);
                preSta.setNull(46, Types.VARCHAR);
            }
            
            //tx_numctaban 24
            if(tbm_tra.getStrTx_TipCtaBan()!=null){
                preSta.setString(47, tbm_tra.getStrTx_TipCtaBan());
                preSta.setString(48, tbm_tra.getStrTx_TipCtaBan());
            }else{
                preSta.setNull(47, Types.VARCHAR);
                preSta.setNull(48, Types.VARCHAR);
            }
            
//            //fe_iniCon 25
//            if(tbm_tra.getDatFe_IniCon()!=null){
//                preSta.setDate(49, new Date(tbm_tra.getDatFe_IniCon().getTime()));
//                preSta.setDate(50, new Date(tbm_tra.getDatFe_IniCon().getTime()));
//            }else{
//                preSta.setNull(49, Types.VARCHAR);
//                preSta.setNull(50, Types.VARCHAR);
//            }
//            
//            //fe_finPerPruCon 26
//            if(tbm_tra.getDatFe_FinPerPru()!=null){
//                preSta.setDate(51, new Date(tbm_tra.getDatFe_FinPerPru().getTime()));
//                preSta.setDate(52, new Date(tbm_tra.getDatFe_FinPerPru().getTime()));
//            }else{
//                preSta.setNull(51, Types.VARCHAR);
//                preSta.setNull(52, Types.VARCHAR);
//            }
//            
//            //fe_finCon 27
//            if(tbm_tra.getDatFe_FinCon()!=null){
//                preSta.setDate(53, new Date(tbm_tra.getDatFe_FinCon().getTime()));
//                preSta.setDate(54, new Date(tbm_tra.getDatFe_FinCon().getTime()));
//            }else{
//                preSta.setNull(53, Types.VARCHAR);
//                preSta.setNull(54, Types.VARCHAR);
//            }
//            
//            //st_perPruCon 28
//            if(tbm_tra.getStrSt_perprucon()!=null){
//                preSta.setString(55, tbm_tra.getStrSt_perprucon());
//                preSta.setString(56, tbm_tra.getStrSt_perprucon());
//            }else{
//                preSta.setNull(55, Types.VARCHAR);
//                preSta.setNull(56, Types.VARCHAR);
//            }
//            
//            //st_finCon 29
//            if(tbm_tra.getStrSt_fincon()!=null){
//                preSta.setString(57, tbm_tra.getStrSt_fincon());
//                preSta.setString(58, tbm_tra.getStrSt_fincon());
//            }else{
//                preSta.setNull(57, Types.VARCHAR);
//                preSta.setNull(58, Types.VARCHAR);
//            }
//            
//            //fe_reaFinCon 30
//            if(tbm_tra.getDatFe_FinReaCon()!=null){
//                preSta.setDate(59, new Date(tbm_tra.getDatFe_FinReaCon().getTime()));
//                preSta.setDate(60, new Date(tbm_tra.getDatFe_FinReaCon().getTime()));
//            }else{
//                preSta.setNull(59, Types.VARCHAR);
//                preSta.setNull(60, Types.VARCHAR);
//            }
//            
            //limit 26
            if(intCanReg > 0 ){
                preSta.setInt(49, intCanReg);
            }else{
                preSta.setNull(49, Types.INTEGER);
            }
            //offset 26
            preSta.setInt(50, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tra = new ArrayList<Tbm_tra>();
                do{
                    Tbm_tra tmp = new Tbm_tra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setStrTx_ide(resSet.getString("tx_ide"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_ape(resSet.getString("tx_ape"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_refdir(resSet.getString("tx_refdir"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_tel2(resSet.getString("tx_tel2"));
                    tmp.setStrTx_tel3(resSet.getString("tx_tel3"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_sex(resSet.getString("tx_sex"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_ciunac(resSet.getInt("co_ciunac"));
                    tmp.setIntCo_estciv(resSet.getInt("co_estciv"));
                    tmp.setIntNe_numhij(resSet.getInt("ne_numhij"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    tmp.setStrTx_NumCtaBan(resSet.getString("tx_numctaban"));
                    tmp.setStrTx_TipCtaBan(resSet.getString("tx_tipctaban"));
                    tmp.setDatFe_IniCon(resSet.getDate("fe_iniCon"));
                    tmp.setDatFe_FinPerPru(resSet.getDate("fe_finPerPruCon"));
                    tmp.setDatFe_FinCon(resSet.getDate("fe_finCon"));
                    tmp.setStrSt_perprucon(resSet.getString("st_perPruCon"));
                    tmp.setStrSt_fincon(resSet.getString("st_finCon"));
                    tmp.setDatFe_FinReaCon(resSet.getDate("fe_reaFinCon"));
                    tmp.setIntCoCarLab(resSet.getInt("co_car"));
                    tmp.setIntCoDep(resSet.getInt("co_dep"));
                    tmp.setIntCoOfi(resSet.getInt("co_ofi"));
                    tmp.setIntCoPla(resSet.getInt("co_pla"));
                    tmp.setStrStRecAlm(resSet.getString("st_recalm"));
                    tmp.setStrTxForRecAlm(resSet.getString("tx_forrecalm"));
                    tmp.setDblNdValAlm(resSet.getDouble("nd_valalm"));
                    lisTbm_tra.add(tmp);
                }while(resSet.next());
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tra;
    }

    //public boolean consultarCedRep(Connection connection, int intCodTra, String strCedTra) throws SQLException {
    public boolean consultarCedRep(Connection connection, String strCedTra) throws SQLException {
        String sqlStr = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        int intNumRep = 0;
        boolean blnRep = false;

        try{
            //sqlStr = "select 1 as flag from tbm_tra where tx_ide = ? and co_tra != ? and st_reg != 'I'";
            sqlStr = "select * from tbm_tra where tx_ide = ? and st_reg != 'I'";
            preSta = connection.prepareStatement(sqlStr);
            preSta.setString(1, strCedTra);
            //preSta.setInt(2, intCodTra);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                //intNumRep = resSet.getInt("flag");
                blnRep = true;
            }
        } finally{
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return blnRep;
    }

    public List<Tbm_tra> consultarDesHas(Connection connection, String strNomDes, String strNomHas, String strApeDes, String strApeHas) throws SQLException{
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tra> lisTbm_tra = null;

        try{
            strSql = "select * from tbm_tra ";
            strSql += "where (? is null or (lower(tx_nom) between lower(?) and lower(?)) or lower(tx_nom) like ?) ";
            strSql += "and (? is null or (lower(tx_ape) between lower(?) and lower(?)) or lower(tx_ape) like ?) ";
            strSql += "and st_reg = 'A'";

            preSta = connection.prepareStatement(strSql);
            //strNomDes 1
            if(strNomDes!=null && strNomDes.length()>0){
                preSta.setString(1, strNomDes);
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //strNomDes 2
            if(strNomDes!=null && strNomDes.length()>0){
                preSta.setString(2, strNomDes);
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //strNomHas 3
            if(strNomHas!=null && strNomHas.length()>0){
                preSta.setString(3, strNomHas);
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //strNomDes 4
            if(strNomDes!=null && strNomDes.length()>0){
                preSta.setString(4, strNomDes+"%");
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //strApeDes 5
            if(strApeDes!=null && strApeDes.length()>0){
                preSta.setString(5, strApeDes);
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //strApeDes 6
            if(strApeDes!=null && strApeDes.length()>0){
                preSta.setString(6, strApeDes);
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //strApeHas 7
            if(strApeHas!=null && strApeHas.length()>0){
                preSta.setString(7, strApeHas);
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }
            //strApeDes 8
            if(strApeDes!=null && strApeDes.length()>0){
                preSta.setString(8, strApeDes+"%");
            }else{
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 9
            preSta.setString(9, "A");

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tra = new ArrayList<Tbm_tra>();
                do{
                    Tbm_tra tmp = new Tbm_tra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setStrTx_ide(resSet.getString("tx_ide"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_ape(resSet.getString("tx_ape"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_refdir(resSet.getString("tx_refdir"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_tel2(resSet.getString("tx_tel2"));
                    tmp.setStrTx_tel3(resSet.getString("tx_tel3"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_sex(resSet.getString("tx_sex"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_ciunac(resSet.getInt("co_ciunac"));
                    tmp.setIntCo_estciv(resSet.getInt("co_estciv"));
                    tmp.setIntNe_numhij(resSet.getInt("ne_numhij"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_tra.add(tmp);
                }while(resSet.next());
            }

        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tra;
    }

    public List<Tbm_tra> consultarDesHas(Connection connection, String strNomDes, String strNomHas, String strApeDes, String strApeHas, int intCodEmp) throws SQLException{

        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_tra> lisTbm_tra = null;

        try{

            strSql = "select * from tbm_tra ";
            strSql += "where (? is null or (lower(tx_nom) between lower(?) and lower(?)) or lower(tx_nom) like ?) ";
            strSql += "and (? is null or (lower(tx_ape) between lower(?) and lower(?)) or lower(tx_ape) like ?) ";
            strSql += "and st_reg = ? and co_tra in(select co_tra from tbm_traemp where co_emp = ?)";

            preSta = connection.prepareStatement(strSql);
            //strNomDes 1
            if(strNomDes!=null && strNomDes.length()>0){
                preSta.setString(1, strNomDes);
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //strNomDes 2
            if(strNomDes!=null && strNomDes.length()>0){
                preSta.setString(2, strNomDes);
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }
            //strNomHas 3
            if(strNomHas!=null && strNomHas.length()>0){
                preSta.setString(3, strNomHas);
            }else{
                preSta.setNull(3, Types.VARCHAR);
            }
            //strNomDes 4
            if(strNomDes!=null && strNomDes.length()>0){
                preSta.setString(4, strNomDes+"%");
            }else{
                preSta.setNull(4, Types.VARCHAR);
            }
            //strApeDes 5
            if(strApeDes!=null && strApeDes.length()>0){
                preSta.setString(5, strApeDes);
            }else{
                preSta.setNull(5, Types.VARCHAR);
            }
            //strApeDes 6
            if(strApeDes!=null && strApeDes.length()>0){
                preSta.setString(6, strApeDes);
            }else{
                preSta.setNull(6, Types.VARCHAR);
            }
            //strApeHas 7
            if(strApeHas!=null && strApeHas.length()>0){
                preSta.setString(7, strApeHas);
            }else{
                preSta.setNull(7, Types.VARCHAR);
            }
            //strApeDes 8
            if(strApeDes!=null && strApeDes.length()>0){
                preSta.setString(8, strApeDes+"%");
            }else{
                preSta.setNull(8, Types.VARCHAR);
            }
            //st_reg 9
            preSta.setString(9, "A");
            preSta.setInt(10, intCodEmp);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_tra = new ArrayList<Tbm_tra>();
                do{
                    Tbm_tra tmp = new Tbm_tra();
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));
                    tmp.setStrTx_ide(resSet.getString("tx_ide"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_ape(resSet.getString("tx_ape"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_refdir(resSet.getString("tx_refdir"));
                    tmp.setStrTx_tel1(resSet.getString("tx_tel1"));
                    tmp.setStrTx_tel2(resSet.getString("tx_tel2"));
                    tmp.setStrTx_tel3(resSet.getString("tx_tel3"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_sex(resSet.getString("tx_sex"));
                    tmp.setDatFe_nac(resSet.getDate("fe_nac"));
                    tmp.setIntCo_ciunac(resSet.getInt("co_ciunac"));
                    tmp.setIntCo_estciv(resSet.getInt("co_estciv"));
                    tmp.setIntNe_numhij(resSet.getInt("ne_numhij"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));

                    lisTbm_tra.add(tmp);
                }while(resSet.next());
            }

        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_tra;
    }

    public List<Tbm_hortra> consultarLisGenCabHorTra(Connection connection) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_hortra> lisTbm_hortra = null;

        try{

            strSql = "select * from tbm_cabhortra where st_reg like 'A' order by co_hor";


            preSta = connection.prepareStatement(strSql);
            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_hortra = new ArrayList<Tbm_hortra>();
                do{
                    Tbm_hortra tmp = new Tbm_hortra();
                    tmp.setIntCo_hor(resSet.getInt("co_hor"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    lisTbm_hortra.add(tmp);
                }while(resSet.next());
            }

        }finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_hortra;

    }
}

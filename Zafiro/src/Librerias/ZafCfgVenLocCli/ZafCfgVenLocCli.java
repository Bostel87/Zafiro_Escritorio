/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafCfgVenLocCli;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;

/**
 *
 * @author Sistemas6
 */
public class ZafCfgVenLocCli {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strSql;
    
    
    public ZafCfgVenLocCli(ZafParSis obj, java.awt.Component parent){
    
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            System.out.println("ZafCfgVenLocCli v0.02");
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        
    }
    
    
    public boolean modificaConfiguracionActual(){
        boolean blnRes=false;
        try{
            if(inicia()){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;            
        }
        return blnRes;
    }
    
    
    private boolean inicia(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc.setAutoCommit(false);
            if(conLoc!=null){
                if(procesoConfigurar(conLoc)){
                    if(clientesxVendedor(conLoc)){
                        blnRes=true;
                        conLoc.commit();
                    }
                    else{
                        conLoc.rollback();
                    }
                }
                else{
                    conLoc.rollback();
                }
                conLoc.close();
                conLoc=null;
            }
             
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;            
        }
        return blnRes;
    }
    
    
    
    
    private boolean clientesxVendedor(java.sql.Connection conExt){
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        try {
            System.out.println("clientesxVendedor...");
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                String strCadena="";
                strCadena+=" /* TUVAL INMACONSA */ \n";
                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
                strCadena+=" FROM (	  \n";
                strCadena+="    SELECT CO_EMP, CO_LOC, co_cli, co_ven   \n";
                strCadena+="    FROM tbr_cliLoc as a1  \n";
                strCadena+="    WHERE co_emp=1 and co_loc=4 and co_ven<>9 \n";
                strCadena+=" ) as a1  \n";
                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strCadena+=" tbr_cliLoc.co_loc=12 AND  \n";
                strCadena+=" tbr_cliLoc.co_cli=a1.co_cli ;  \n";
                strCadena+=" /* CASTEK UIO INMACONSA */  \n";
                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
                strCadena+=" FROM ( \n";
                strCadena+="    SELECT CO_EMP, CO_LOC, co_cli, co_ven   \n";
                strCadena+="    FROM tbr_cliLoc as a1  \n";
                strCadena+="    WHERE co_emp=2 and co_loc=1  and co_ven<>9	 \n";
                strCadena+="    ) as a1   \n";
                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND     \n";
                strCadena+="       tbr_cliLoc.co_loc=11 AND  \n";
                strCadena+="       tbr_cliLoc.co_cli=a1.co_cli ;  \n";
                strCadena+=" /* CASTEK MANTA INMACONSA */ \n";
                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
                strCadena+=" FROM ( \n";
                strCadena+="     SELECT CO_EMP, CO_LOC, co_cli, co_ven  \n";
                strCadena+="     FROM tbr_cliLoc as a1  \n";
                strCadena+="     WHERE co_emp=2 and co_loc=4 and co_ven<>9	 \n";
                strCadena+=" ) as a1  \n";
                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strCadena+="        tbr_cliLoc.co_loc=12 AND   \n";
                strCadena+="        tbr_cliLoc.co_cli=a1.co_cli ;  \n";
                strCadena+=" /* CASTEK STO DOM INMACONSA */ \n";
                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
                strCadena+=" FROM ( \n";
                strCadena+="        SELECT CO_EMP, CO_LOC, co_cli, co_ven   \n";
                strCadena+="        FROM tbr_cliLoc as a1  \n";
                strCadena+="        WHERE co_emp=2 and co_loc=6 and co_ven<>9	 \n";
                strCadena+=" ) as a1  \n";
                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strCadena+="        tbr_cliLoc.co_loc=13 AND  \n";
                strCadena+="        tbr_cliLoc.co_cli=a1.co_cli ; \n";
                strCadena+=" /* DIMULTI VIA DAULE INMACONSA */ \n";
                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven \n";
                strCadena+=" FROM  ( \n";
                strCadena+="        SELECT CO_EMP, CO_LOC, co_cli, co_ven  \n";
                strCadena+="        FROM tbr_cliLoc as a1  \n";
                strCadena+="        WHERE co_emp=4 and co_loc=3 and co_ven<>9	 \n";
                strCadena+=" ) as a1  \n";
                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strCadena+="        tbr_cliLoc.co_loc=10 AND  \n";
                strCadena+="        tbr_cliLoc.co_cli=a1.co_cli ; \n";
                strCadena+=" /* DIMULTI DURAN INMACONSA */ \n";
                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
                strCadena+=" FROM ( \n";
                strCadena+="        SELECT CO_EMP, CO_LOC, co_cli, co_ven  \n";
                strCadena+="        FROM tbr_cliLoc as a1  \n";
                strCadena+="        WHERE co_emp=4 and co_loc=12 and co_ven<>9 \n";
                strCadena+=" ) as a1  \n";
                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strCadena+="        tbr_cliLoc.co_loc=13 AND  \n";
                strCadena+="        tbr_cliLoc.co_cli=a1.co_cli ;   \n";
                stmLoc.executeUpdate(strCadena);
                stmLoc.close();
                stmLoc = null;
            }
            
            
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
            System.err.println("ERROR: " + e);
        } 
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
            System.err.println("ERROR: " + e);
        }
        return blnRes;
    }

    
    
    private boolean procesoConfigurar(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if (conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbr_cliLoc SET co_ven = a1.co_usrVen,  fe_ultMod=CURRENT_TIMESTAMP, co_usrMod=a1.co_usrIng \n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a1.co_emp, a2.co_loc, a1.co_cli, a1.tx_nom, a2.co_ven, a3.co_cfg, a3.co_usrVen, a3.co_usrIng  \n";
                strSql+="       FROM tbm_cli as a1  \n";
                strSql+="       INNER JOIN tbr_cliLoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSql+="       LEFT OUTER JOIN ( \n";
                strSql+="           SELECT a1.co_emp, a1.co_loc,a1.co_cli, a1.co_cfg, a1.co_usrVen, a1.tx_obs1, a1.fe_ing, a1.co_usrIng, a1.tx_comIng   \n";
                strSql+="           FROM tbm_preCfgCliLoc as a1  \n";
                strSql+="           INNER JOIN (   \n";
                strSql+="               SELECT a3.co_emp, a3.co_loc, a3.co_cli, MAX(a3.co_cfg) as co_cfg     \n";
                strSql+="               FROM tbm_preCfgCliLoc as a3   \n";
                strSql+="               GROUP BY  a3.co_emp, a3.co_loc, a3.co_cli   \n";
                strSql+="           ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cli=a2.co_cli AND a1.co_cfg=a2.co_cfg) \n";
                strSql+="       ) as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_cli=a3.co_cli)   \n";
                strSql+="       WHERE a3.co_cfg IS NOT NULL AND  \n";
                strSql+="             CASE WHEN a2.co_ven IS NULL AND a3.co_usrVen IS NOT NULL THEN TRUE ELSE  \n";
                strSql+="                   CASE WHEN a2.co_ven <> a3.co_usrVen THEN TRUE ELSE FALSE   \n";
                strSql+="                   END  \n";
                strSql+="             END   \n";
                strSql+=" ) as a1  \n";
                strSql+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strSql+="       tbr_cliLoc.co_loc=a1.co_loc AND  \n";
                strSql+="       tbr_cliLoc.co_cli=a1.co_cli  ;  \n";
                strSql+=" \n";
     		stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;            
        }
        return blnRes;
    }
    
    
    
    
}

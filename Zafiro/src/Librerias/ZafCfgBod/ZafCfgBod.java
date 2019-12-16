/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafCfgBod;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author sistemas3
 */
public class ZafCfgBod {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strSQL="";
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private int intCodEmp;
    private Object objCodBodOriGrp;
    private Object objCodBodDesGrp;
    
    private boolean blnConIng;
    private boolean blnConEgr;
    private String strEgrGen;
    private String strIngGen;

    public ZafCfgBod(ZafParSis obj, Connection conexion, int codigoEmpresaGrupo
            , Object bodegaOrigenGrupo, Object bodegaDestinoGrupo, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            System.out.println("ZafCnfBod v0.1");
            con=conexion;
            intCodEmp=codigoEmpresaGrupo;
            objCodBodOriGrp=bodegaOrigenGrupo;
            objCodBodDesGrp=bodegaDestinoGrupo;
            cargarCnfBod();
                
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    private boolean cargarCnfBod(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_cfg, a1.co_bodOrg, a1.co_bodDes, a1.st_conEgr, a1.st_conIng";
                strSQL+=" , a1.tx_egrGen, a1.tx_ingGen, a1.tx_obs1, a1.st_reg";
                strSQL+=" FROM tbm_cfgbod AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_bodOrg=" + objCodBodOriGrp + "";
                strSQL+=" AND a1.co_bodDes=" + objCodBodDesGrp + "";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=";";
                System.out.println("cargarCnfBod: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    blnConIng=new Boolean((rst.getObject("st_conIng")==null?false:true));
                    blnConEgr=new Boolean((rst.getObject("st_conEgr")==null?false:true));
                    strEgrGen=rst.getObject("tx_egrGen")==null?"":rst.getString("tx_egrGen");
                    strIngGen=rst.getObject("tx_ingGen")==null?"":rst.getString("tx_ingGen");
                }
                System.out.println("blnConIng: " + blnConIng);
                System.out.println("blnConEgr: " + blnConEgr);
                System.out.println("strEgrGen: " + strEgrGen);
                System.out.println("strIngGen: " + strIngGen);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;            
        }
        return blnRes;
    }

    public boolean isConIng() {
        return blnConIng;
    }

//    public void setConIng(boolean blnConIng) {
//        this.blnConIng = blnConIng;
//    }

    public boolean isConEgr() {
        return blnConEgr;
    }

//    public void setConEgr(boolean blnConEgr) {
//        this.blnConEgr = blnConEgr;
//    }

    public String getEgrGen() {
        return strEgrGen;
    }

//    public void setEgrGen(String strEgrGen) {
//        this.strEgrGen = strEgrGen;
//    }

    public String getIngGen() {
        return strIngGen;
    }
//
//    public void setIngGen(String strIngGen) {
//        this.strIngGen = strIngGen;
//    }
    
    
    
    
    
    
    
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

//import Librerias.ZafParSis.ZafParSisServ1;
import GenOD.ZafGenGuiRemDaoPryTra;
import Librerias.ZafPulFacEle.ZafPulFacEle;
import Librerias.ZafUtil.ZafUtil;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *  
 * 
 * @author CMateo
 * Genera los sigtes procesos.
 * Ordenes Despacho : Cliente No Retira, Terminales L, Transferencias.
 * Confirmacion de Egresos Terminales L.
 * Guias de Remision (Terminales L ).
 * Impresion de Ordenes de Despacho y Guias de remision.
 * 
 * 
 */
public class ZafGenGuiRem {
    
    //ZafParSisServ1 objZafParSisServ1;
    ZafGuiRemDAO objZafGuiRemDAO=new ZafGuiRemDAO();
    ZafImpGuiRem objZafImpGuiRem= new ZafImpGuiRem();
    //ZafCon objZafCon=null;
    int codEmpGui=0;
    int codLocGui=0;
    int codTipDocGui=0;
    int codDocGui=0;
    public boolean booSiIns=false;
    private String strImpRptBod2="";
    
    private ZafClassImpGuia_01 objEnvMail;
    private ZafUtil objUti;
    private String strRutaRpt2="";
    private String strNomImpBod2="";
    
    
    private final static int CODEMPIMPTUV=1;    
    private final static int CODLOCIMPTUV=6;
    private final static int CODEMPIMPCASUIO=2;
    private final static int CODLOCIMPCASUIO=9;
    private final static int CODEMPIMPDIM=4;
    private final static int CODLOCIMPDIM=4;    
    
    
    private final static int CODEMPINMTUV=1;    
    private final static int CODLOCINMTUV=10;
    private final static int CODEMPINMCASUIO=2;
    private final static int CODLOCINMCASUIO=5;
    private final static int CODEMPINMDIM=4;
    private final static int CODLOCINMDIM=10;    
    
    
    private final static int CODEMPRESANUMGUIACUENCA=2;
    private final static int CODLOCALNUMGUIACUENCA=10;    
    
    
    private final static int CODLOCINMTUVAL=10;
    private final static int CODLOCINMCASTEKUIO=5;
    private final static int CODLOCINMDIMULTI=10;
    private final static String IMPRIMEBODORD="S";
    private final String strVersion = " v0.4";
    
    
    public ZafGenGuiRem(){
        //objZafParSisServ1=new ZafParSisServ1("tuval");
        objUti=new ZafUtil();
    }
    
    /**
     * Metodo que genera la guia de remision de una orden de despacho confirmada.
     * @param conn conexion acceso a datos.
     * @param intCodEmp codigo de la empres orden de despacho
     * @param intCodLoc codigo del local de la OD. orden de despacho.
     * @param intCodTipDoc codigo del tipo de documento de la OD. orden de despacho.
     * @param intCodDoc  codigo del documento de la OD. orden de despacho.
     */
    public boolean generarGuiRemxConfEgrCliNoRet( Connection conn,int intCodEmp,int  intCodLoc,int intCodTipDoc,int intCodDoc,int intCodBodGrp, int intCodEmpNumGuia, int intCodLocNumGuia){
        boolean booRet=true;
        int intTipNewDoc=0;
        int intCodDocGuiNue=0;
        int intNumGuiFin=0;
        ResultSet rsGenGuiRem=null;
        ResultSet rsDetGuiRem=null;
        ZafGenGuiRemDaoPryTra objGenGuiRemDao=new ZafGenGuiRemDaoPryTra();
        try{
            
            Thread.sleep(8000);// se usa solo para esperar 8 segundos a que se imprima la OD 
            
            rsGenGuiRem=objZafGuiRemDAO.obtenerDatosOrdenDespacho(conn,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrp);
            
            if(rsGenGuiRem.next()){
                rsGenGuiRem.beforeFirst();
                while(rsGenGuiRem.next()){

                    /*if(rsGenGuiRem.getInt("co_tipdoc")==114){
                        intTipNewDoc=102;
                    }else if(rsGenGuiRem.getInt("co_tipdoc")==233){
                        intTipNewDoc=231;
                    }*/
                    /*OBTENCION DEL TIPO DE DOCUMENTO DE GUIA REMISION*/
                    intTipNewDoc=objGenGuiRemDao.obtenerTipDocGuiRem(conn, intCodEmp);
                    /*OBTENCION DEL TIPO DE DOCUMENTO DE GUIA REMISION*/

                    
                    int intCodPtoPar=rsGenGuiRem.getInt("co_ptopar");
                    int intEmpGui=rsGenGuiRem.getInt("coempgui");
                    
                    int intBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn, intEmpGui, intCodPtoPar);                    
                    
                    String[] strRet=new String[4];
                    if(intBodGrp!=15){//Bodega inmaconsa
                         strRet=obtenerRptImpGuiRem(intBodGrp);                        
                    }else{
                        strRet[0]="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
                        strRet[1]="guias_inmaconsa";   
                        strRet[2]=String.valueOf(rsGenGuiRem.getInt("coempgui"));
                        strRet[3]=String.valueOf(rsGenGuiRem.getInt("colocgui"));
                    }
                    
                    intCodDocGuiNue=objZafGuiRemDAO.obtenerSec(conn,rsGenGuiRem.getInt("coempgui"), rsGenGuiRem.getInt("colocgui"), intTipNewDoc);
                    intNumGuiFin=objZafGuiRemDAO.obtenerNumGuiaFin(conn,Integer.parseInt(strRet[2]),Integer.parseInt(strRet[3]), intTipNewDoc);

                    String strSerLocDoc=objZafGuiRemDAO.obtenerSerLocDoc(conn,Integer.parseInt(strRet[2]),Integer.parseInt(strRet[3]));
                    objZafGuiRemDAO.actualizarTipDoc(conn, intNumGuiFin, Integer.parseInt(strRet[2]),Integer.parseInt(strRet[3]), intTipNewDoc);                
                    if(objZafGuiRemDAO.insertarCabGuiRem(conn, rsGenGuiRem, intTipNewDoc, intCodDocGuiNue, intNumGuiFin, strSerLocDoc)){
                        booSiIns=true;
                        rsDetGuiRem=objZafGuiRemDAO.obtenerDatDetGuiRem(conn,rsGenGuiRem.getInt("co_emp" ) , rsGenGuiRem.getInt("co_loc") , rsGenGuiRem.getInt("co_tipdoc") , rsGenGuiRem.getInt("co_doc"));
                        if(!objZafGuiRemDAO.insertarDetGuiRem(conn,rsDetGuiRem, intCodDocGuiNue, intTipNewDoc)){                        
                             booRet=false;
                             break;
                        }
                    }else{
                        booRet=false;
                        break;
                    }
    //<editor-fold defaultstate="collapsed" desc="CODIGO COMENTADO">
    //                conn.commit();//Se necesita hacer commmit porque el Servicio 11 crea una nueva instancia de conexion.
                    //ZafPulFacEle objPulFacEle=new ZafPulFacEle();//Envia el pulso para autorizar la Guia Remision.
                    //objPulFacEle.iniciar();
    //</editor-fold>
                    codDocGui=intCodDocGuiNue;
                    codTipDocGui=intTipNewDoc;
                    codLocGui=rsGenGuiRem.getInt("co_loc");
                    codEmpGui=rsGenGuiRem.getInt("co_emp" );

                    //imprimirGuiRemNoRet(conn,rsGenGuiRem.getInt("co_emp" ) , rsGenGuiRem.getInt("co_loc") , intTipNewDoc , intCodDocGuiNue);

                    rsDetGuiRem.close();
                    rsDetGuiRem=null;
                }
            }else{
                booRet=false;
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }finally{
            try{
                rsGenGuiRem.close();
                rsGenGuiRem=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return booRet;
    }
    
    
    /**
     * Metodo que sirve para realizar la confirmacion automatica de las ordenes de despacho
     * en los que todos los items son terminal L o son todos items de servicio
     * @param intCodBodGrp codigo de la bodega de grupo.
     * @param intCodEmpOD  codigo de la empresa de la OD (orden de despacho).
     * @param intCodLocOd  codigo del local de la OD (orden de despacho).
     * @param intCodTipDoc codigo del tipo de documento de la OD (orden de despacho).
     * @param intCodDoc  codigo del documento de la OD (orden de despacho).
     */
    public boolean generarConfEgreAutxItms_L_o_Itms_S(Connection conn,int intCodBodGrp,int intCodEmpOD, int intCodLocOD, int intCodTipDocOD, int intCodDocOD){
        ZafCnfEgrDAO objZafCnf=new ZafCnfEgrDAO();
        ZafGuiRemDAO objZafGuiRemDAO=new ZafGuiRemDAO();
        boolean booRet=true;
        int intCodDocConfEgr=0;
        int intUltNeNumDoc=0;        
        String fecDesdeItemsServ = "2012-07-12";
        //int intTipDocConfEgr=114;
        int intTipDocConfEgr=0;
        try{
            intTipDocConfEgr=objZafGuiRemDAO.obtenerTipDocCnfAutEgr(conn, intCodEmpOD);
            conn.setAutoCommit(false);
            ResultSet rstODxConf=objZafCnf.obtenerODCnfEgrAut(conn, fecDesdeItemsServ, intCodBodGrp, intCodEmpOD, intCodLocOD, intCodTipDocOD, intCodDocOD);
            if(rstODxConf.next()){
                rstODxConf.beforeFirst();
                while (rstODxConf.next()){
                    intCodDocConfEgr= objZafCnf.obtenerMaxConfEgr(intCodEmpOD, intCodLocOD, intTipDocConfEgr, conn);
                    intUltNeNumDoc = objZafCnf.obtenerUltDoc(intCodEmpOD, intCodLocOD, intTipDocConfEgr, conn);
                    boolean booCabIns=objZafCnf.ingCabConfEgr(conn, intCodEmpOD, intCodLocOD, intCodTipDocOD, intCodDocOD, rstODxConf.getInt("cobod"), intTipDocConfEgr, intCodDocConfEgr, intUltNeNumDoc);
                    ResultSet rsDetOD=objZafCnf.obtenerDetDespConf(intCodEmpOD, intCodLocOD, intCodTipDocOD, intCodDocOD, conn);
                    boolean booDetIns=objZafCnf.insertarDetConfEgr(conn, intCodEmpOD, intCodLocOD, intTipDocConfEgr, intCodDocConfEgr, intCodTipDocOD, intCodDocOD, rstODxConf.getInt("cobod"), rsDetOD);                
                    if(!(booCabIns && booDetIns)){
                        booRet=false;
                        break;
                       //conn.commit();
                    }/*else{
                        conn.rollback();
                    }*/
                }
            }else{
                booRet=false;            
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }
    
    
    /**
     * Método que genera la Orden de Despacho para ventas relacionadas por CLIENTE NO RETIRA.
     * @param conn conexion acceso a datos.
     * @param intCodEmp codigo de empresa.
     * @param intCodBodGrp codigo de grupo.
     * @return boolean valor de retorno de la operacion.
     */
    public boolean generarOrdDesxCliNoRetFacVenRel( Connection conn,int intCodEmp,int  intCodLoc,int intCodTipDoc,int intCodDoc, int intCodBodGrp,String rtaRpte,String nomImp,int intEmpNumGuia, int intLocNumGuia ){
        boolean booRet=true;
        int intTipNewDoc=101;
        int intCodDocGuiNue=0;
        int intNumGuiFin=0;
        int intNumOrdDes=0;
        ResultSet rsDatFac=null;
        ResultSet rsDatFacCab=null;
        ResultSet rsDatFacDet=null;
        ZafImpGuiRem objZafImpGuiRem=new ZafImpGuiRem();
        try{
            rsDatFac=objZafGuiRemDAO.obtenerFactTransEntEmp(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrp);            
            while(rsDatFac.next()){
                
                intCodDocGuiNue=objZafGuiRemDAO.obtenerSec(conn,intCodEmp, rsDatFac.getInt("co_loc") /*intCodLoc*/, intTipNewDoc);
                intNumOrdDes=objZafGuiRemDAO.obtenerNumOrdDes(conn, intEmpNumGuia, /*intCodLoc*/intLocNumGuia);
                objZafGuiRemDAO.actualizarTipDocLoc(conn, intNumOrdDes, intEmpNumGuia, /*intCodLoc*/intLocNumGuia);
                rsDatFacCab=objZafGuiRemDAO.obtenerDatFacCabOrdDes(conn, intCodEmp, /*intCodLoc*/rsDatFac.getInt("co_loc"), intCodBodGrp, rsDatFac.getInt("co_bodgrp"),intCodTipDoc, intCodDoc);
                if(objZafGuiRemDAO.insertarCabOrdDesp(conn, rsDatFacCab, intTipNewDoc,intCodDocGuiNue , intNumOrdDes, rsDatFac.getInt("co_bod"), rsDatFac.getString("dirbor"))){
                    rsDatFacDet=objZafGuiRemDAO.obtenerDatFacDetOrdDes(conn, intCodEmp, /*intCodLoc*/rsDatFac.getInt("co_loc"), intCodBodGrp, rsDatFac.getInt("co_bodgrp"), rsDatFacCab.getInt("co_tipdoc"), rsDatFacCab.getInt("co_doc"));
                    //rsDatFacDet=objZafGuiRemDAO.obtenerDatFacDetOrdDesInmaconsa(conn, intCodEmp, intCodLoc, intCodBodGrp, rsDatFac.getInt("co_bodgrp"), rsDatFacCab.getInt("co_tipdoc"), rsDatFacCab.getInt("co_doc"));                    
                    if(!objZafGuiRemDAO.insertarDetOrdDesp(conn, rsDatFacDet, intCodEmp, /*intCodLoc*/rsDatFac.getInt("co_loc"), intTipNewDoc, intCodDocGuiNue, intNumOrdDes)) {
                         booRet=false;
                         conn.rollback();
                         break;
                    }else{
                        String doc[]=objZafGuiRemDAO.strOD.split("-");
                        int intEmpOD=Integer.parseInt(doc[0]);
                        int intLocOD=Integer.parseInt(doc[1]);
                        int intTipDocOD=Integer.parseInt(doc[2]);
                        int intDocOD=Integer.parseInt(doc[3]);
                        conn.commit();
                        //objZafImpGuiRem.impresionGuiaRemAutBod2(conn, intEmpOD, intLocOD, intTipDocOD, intDocOD,rtaRpte,nomImp);
                        enviarPulsoImpresionGui("imprimeODRemotaComun"+"-"+intEmpOD+"-"+intLocOD+"-"+intTipDocOD+"-"+intDocOD+"-"+rtaRpte+"-"+nomImp, 6000);                        
                    }
                }else{
                    booRet=false;
                    conn.rollback();
                    break;
                }
                
                rsDatFacCab.close();
                rsDatFacDet.close();
                rsDatFacCab=null;
                rsDatFacDet=null;                
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }finally{
            try{
                rsDatFac.close();
                rsDatFac=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return booRet;
    }    
    

    
    /**
     * Método que genera la Orden de Despacho para ventas relacionadas por CLIENTE NO RETIRA.
     * @param conn conexion acceso a datos.
     * @param intCodEmp codigo de empresa.
     * @param intCodBodGrp codigo de grupo.
     * @return boolean valor de retorno de la operacion.
     */
    public boolean generarOrdDesxCliNoRetFacVenRelInmaconsa( Connection conn,int intCodEmp,int  intCodLoc,int intCodTipDoc,int intCodDoc, int intCodBodGrp,String rtaRpte,String nomImp,int intEmpNumGuia, int intLocNumGuia ){
        boolean booRet=true;
        int intTipNewDoc=101;
        int intCodDocGuiNue=0;
        int intNumGuiFin=0;
        int intNumOrdDes=0;
        int INTCODLOCTUVINM=1;
        ResultSet rsDatFac=null;
        ResultSet rsDatFacCab=null;
        ResultSet rsDatFacDet=null;
        ZafImpGuiRem objZafImpGuiRem=new ZafImpGuiRem();
        int intCodEmpOrdDes=0;
        int intCodLocOrdDes=0;
        try{
            rsDatFac=objZafGuiRemDAO.obtenerFactTransEntEmp(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrp);            
            while(rsDatFac.next()){
                
                intCodEmpOrdDes=rsDatFac.getInt("co_empFacEmpRel");
                
                switch (intCodEmpOrdDes)
                {
                    case 1: 
                        intCodLocOrdDes=CODLOCINMTUVAL;
                        break;
                    case 2:
                        intCodLocOrdDes=CODLOCINMCASTEKUIO;
                        break;
                    case 4:
                        intCodLocOrdDes=CODLOCINMDIMULTI;
                        break;
                }                
                
                intCodDocGuiNue=objZafGuiRemDAO.obtenerSec(conn,intCodEmpOrdDes, intCodLocOrdDes, intTipNewDoc);
                intNumOrdDes=objZafGuiRemDAO.obtenerNumOrdDes(conn, intEmpNumGuia, intLocNumGuia);
                objZafGuiRemDAO.actualizarTipDocLoc(conn, intNumOrdDes, intEmpNumGuia, intLocNumGuia);
                rsDatFacCab= objZafGuiRemDAO.obtenerDatFacCabOrdDesInmaconsa(conn, intCodBodGrp, rsDatFac.getInt("co_bodgrp"), intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                if(objZafGuiRemDAO.insertarCabOrdDespInmaconsa(conn, rsDatFacCab, intTipNewDoc, intCodDocGuiNue, intNumOrdDes, rsDatFac.getInt("co_bod"), rsDatFac.getString("dirbor"), intCodEmpOrdDes, intCodLocOrdDes)){
                    //rsDatFacDet=objZafGuiRemDAO.obtenerDatFacDetOrdDes(conn, intCodEmp, /*intCodLoc*/rsDatFac.getInt("co_loc"), intCodBodGrp, rsDatFac.getInt("co_bodgrp"));
                    rsDatFacDet=objZafGuiRemDAO.obtenerDatFacDetOrdDesInmaconsa(conn, intCodEmp, intCodLoc, intCodBodGrp, rsDatFac.getInt("co_bodgrp"), rsDatFacCab.getInt("co_tipdoc"), rsDatFacCab.getInt("co_doc"));
                    //if(!objZafGuiRemDAO.insertarDetOrdDesp(conn, rsDatFacDet, intCodEmp, rsDatFac.getInt("co_loc"), intTipNewDoc, intCodDocGuiNue, intNumOrdDes)) {
                    if(!objZafGuiRemDAO.insertarDetOrdDespInmaconsa(conn, rsDatFacDet, rsDatFac.getInt("co_emp"),rsDatFac.getInt("co_loc"), rsDatFacCab.getInt("co_tipdoc"), rsDatFacCab.getInt("co_doc"),  intNumOrdDes,intCodEmpOrdDes,  intCodLocOrdDes,intTipNewDoc,intCodDocGuiNue)){
                         booRet=false;
                         conn.rollback();
                         break;
                    }else{
                        conn.commit();
                        if(IMPRIMEBODORD.equals("S")){
                            //objZafImpGuiRem.impresionOrdDesTransInm(conn, intCodEmpOrdDes, intCodLocOrdDes, intTipNewDoc, intCodDocGuiNue, intCodEmpOrdDes);
                            enviarPulsoImpresionGui("imprimeODInmaconsa"+"-"+intCodEmpOrdDes+"-"+intCodLocOrdDes+"-"+intTipNewDoc+"-"+intCodDocGuiNue+"-"+intCodEmpOrdDes, 6000);

                        }
                    }
                    /*String doc[]=objZafGuiRemDAO.strOD.split("-");
                    int intEmpOD=Integer.parseInt(doc[0]);
                    int intLocOD=Integer.parseInt(doc[1]);
                    int intTipDocOD=Integer.parseInt(doc[2]);
                    int intDocOD=Integer.parseInt(doc[3]);
                    objZafImpGuiRem.impresionGuiaRemAutBod2(conn, intEmpOD, intLocOD, intTipDocOD, intDocOD,rtaRpte,nomImp);*/
                }else{
                    booRet=false;
                    conn.rollback();
                    break;
                }
                
                rsDatFacCab.close();
                rsDatFacDet.close();
                rsDatFacCab=null;
                rsDatFacDet=null;                
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }finally{
            try{
                rsDatFac.close();
                rsDatFac=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return booRet;
    }     
    
    
    
    
    /**
     * Metodo que genera la Orden de Despacho para ventas relacionadas CUENCA.
     * @param conn Conexión acceso a datos.
     * @param intCodEmp Código de la empresa.
     * @param intCodLoc 
     * @param intCodTipDoc
     * @param intCodDoc
     * @param intCodBodGrp
     * @param rtaRpte
     * @param nomImp
     * @param intEmpNumGuia
     * @param intLocNumGuia
     * @return 
     */
    public boolean generarOrdDesxCliNoRetFacVenRelCuenca( Connection conn,int intCodEmp,int  intCodLoc,int intCodTipDoc,int intCodDoc, int intCodBodGrp,/*String rtaRpte,String nomImp,*/int intEmpNumGuia, int intLocNumGuia ){
        boolean booRet=true;
        int intTipNewDoc=101;
        int intCodDocGuiNue=0;
        int intNumGuiFin=0;
        int intNumOrdDes=0;
        ResultSet rsDatFac=null;
        ResultSet rsDatFacCab=null;
        ResultSet rsDatFacDet=null;
        ZafImpGuiRem objZafImpGuiRem=new ZafImpGuiRem();
        try{
            rsDatFac=objZafGuiRemDAO.obtenerFactTransEntEmp(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrp);            
            while(rsDatFac.next()){
                
                intCodDocGuiNue=objZafGuiRemDAO.obtenerSec(conn,rsDatFac.getInt("co_empFacEmpRel"),  rsDatFac.getInt("co_locFacEmpRel"), intTipNewDoc);
                intNumOrdDes=objZafGuiRemDAO.obtenerNumOrdDes(conn, intEmpNumGuia, intLocNumGuia);
                objZafGuiRemDAO.actualizarTipDocLoc(conn, intNumOrdDes, intEmpNumGuia, /*intCodLoc*/intLocNumGuia);
                rsDatFacCab=objZafGuiRemDAO.obtenerDatFacCabOrdDesCuenca(conn, rsDatFac.getInt("co_emp"), rsDatFac.getInt("co_loc"), intCodBodGrp, rsDatFac.getInt("co_bodgrp"), intCodEmp, intCodLoc, intCodTipDoc,intCodDoc);
                if(objZafGuiRemDAO.insertarCabOrdDespCuenca(conn, rsDatFacCab, intTipNewDoc,intCodDocGuiNue , intNumOrdDes, rsDatFac.getInt("co_bod"), rsDatFac.getString("dirbor"),rsDatFac.getInt("co_empFacEmpRel"),  rsDatFac.getInt("co_locFacEmpRel"))){
                    rsDatFacDet=objZafGuiRemDAO.obtenerDatFacDetOrdDesCuenca(conn, intCodEmp, /*intCodLoc*/rsDatFac.getInt("co_loc"), intCodBodGrp, rsDatFac.getInt("co_bodgrp"),rsDatFacCab.getInt("co_tipDoc"),rsDatFacCab.getInt("co_doc"));
                    //Connection con, ResultSet rsDatDetDes, int intCodEmp, int intCodLoc,int intCodTipODRef, int intCodODRef, int intNumOD, int intCodEmpOrdDesRef, int intCodLocOrdDesRef, int intCodTipDocOrdDesRef, int intCodDocOrdDesRef
                    if(!objZafGuiRemDAO.insertarDetOrdDespCuenca(conn, rsDatFacDet, rsDatFac.getInt("co_emp"),rsDatFac.getInt("co_loc"), rsDatFacCab.getInt("co_tipdoc"), rsDatFacCab.getInt("co_doc"),  intNumOrdDes,rsDatFac.getInt("co_empFacEmpRel"),  rsDatFac.getInt("co_locFacEmpRel"),intTipNewDoc,intCodDocGuiNue)) {
                         booRet=false;
                         conn.rollback();
                         break;
                    }else{
                        String doc[]=objZafGuiRemDAO.strOD.split("-");
                        conn.commit();
                    }
                    /*int intEmpOD=Integer.parseInt(doc[0]);
                    int intLocOD=Integer.parseInt(doc[1]);
                    int intTipDocOD=Integer.parseInt(doc[2]);
                    int intDocOD=Integer.parseInt(doc[3]);
                    objZafImpGuiRem.impresionGuiaRemAutBod2(conn, intEmpOD, intLocOD, intTipDocOD, intDocOD,rtaRpte,nomImp);*/
                }else{
                    booRet=false;
                    conn.rollback();
                    break;
                    
                }
                
                rsDatFacCab.close();
                rsDatFacDet.close();
                rsDatFacCab=null;
                rsDatFacDet=null;                
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            
            booRet=false;
        }finally{
            try{
                rsDatFac.close();
                rsDatFac=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return booRet;
    }     
    
    
    
    
    /**
     * Metodo que genera la OD para documentos de transferencia entre bodegas (ventas relacionadas).
     * @param cnx conexion de acceso.
     * @param intCodBodGrp codigo de la bodega de grupo.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param intCodEmpOD codigo de la empresa OD.
     * @param intCodLocOD codigo del local OD.
     * @return boolean indica si el metodo se ejecuto con exito o con error.
     */
    public boolean generarODTraVenRel(Connection cnx , int intCodBodGrp,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpOD, int intCodLocOD,String strRtaRpt, String strNomImp){        
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        String strTipDocTran="46,153,172";    
        int intCodTipDocOD=101;
        boolean booRet=true;
        try{
            ResultSet rsDocTrans=daoGuiRem.obtenerDatosODxTran(cnx, strTipDocTran, intCodBodGrp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            while (rsDocTrans.next()){
                ResultSet rsDocTrans2=daoGuiRem.obtenerDocTransGenODTran(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                while (rsDocTrans2.next()){
                    if(daoGuiRem.verificaBodOD(cnx,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){  //Verifica existencia de las bodegas de grupo
                        int intSecOD=daoGuiRem.obtenerSec(cnx, intCodEmp, intCodLoc, intCodTipDocOD);
                        int intNumOrDes=daoGuiRem.obtenerNumOrdDes(cnx, intCodEmpOD, intCodLocOD);
                        boolean booRetAct=daoGuiRem.actualizarTipDocLoc(cnx, intNumOrDes, intCodEmpOD, intCodLocOD);                        
                        if(daoGuiRem.insertCabODxTran(cnx, intCodEmp, intCodLoc, intCodTipDocOD, intSecOD, 
                                                   rsDocTrans2.getString("rucemp"), rsDocTrans2.getString("empresa"), rsDocTrans2.getString("tx_dir"), 
                                                   rsDocTrans2.getString("tx_tel"), intNumOrDes, rsDocTrans.getInt("co_bod"), rsDocTrans.getString("tx_dir"), 
                                                   rsDocTrans.getString("docrel"),rsDocTrans2.getString("coclides"), rsDocTrans2.getInt("co_bod"),
                                                   intCodTipDoc, intCodDoc)){
                                ResultSet rsODDet=daoGuiRem.obtenerDatDetODxTran(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, rsDocTrans.getInt("co_bod"), rsDocTrans2.getInt("co_bod"));
                                if(!daoGuiRem.insertarDetODxTran(cnx, rsODDet, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodTipDocOD, intSecOD)){
                                    booRet=false;
                                    cnx.rollback();
                                    break;
                                }else{
                                    cnx.commit();
                                    enviarPulsoImpresionGui("imprimeODRemotaComun"+"-"+intCodEmp+"-"+intCodLoc+"-"+intCodTipDocOD+"-"+intSecOD+"-"+strRtaRpt+"-"+strNomImp, 6000);                                    
                                    //objZafImpGuiRem.impresionGuiaRemAutBod2(cnx, intCodEmp, intCodLoc, intCodTipDocOD, intSecOD, strRtaRpt, strNomImp);
                                }
                        }else{
                            booRet=false;
                            cnx.rollback();
                            break;
                        }
                        //objZafImpGuiRem.impresionGuiaRemAutBod2(cnx, intCodEmp, intCodLoc, intCodTipDocOD, intSecOD, strRtaRpt, strNomImp);
                    }                
                }
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }
    
    
    
    /**
     * Metodo que imprime la Guia Remision de un Cliente que no retira.
     * @param intCodEmp codigo de la empresa de la guia de remision.
     * @param intCodLoc codigo del local de la guia remision.
     * @param intCodTipDoc codigo del tipo de documento de la guia remision.
     * @param intCodDoc codigo del documento de la guia remision.
     */
    public void imprimirGuiRemNoRet(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,String strRtaRpt,String strNomRpt,int intCodBodGrp){
        objZafImpGuiRem.imprimirGuiRemCliNotRet_autSRI(con,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,strRtaRpt, strNomRpt, intCodBodGrp);    
    }
    
    
    /**
     * Metodo que imprime la OD.
     * @param con conexion de acceso a Datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc tipo de documento de la OD.
     * @param intCodDoc codigo del documento de la OD.
     */
    public void imprimirOD(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,String rtaRpte, String nomImp){
        objZafImpGuiRem.impresionGuiaRemAutBod2(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,rtaRpte,nomImp);
    }
    
    

    public int getCodEmpGui() {
        return codEmpGui;
    }

    public int getCodLocGui() {
        return codLocGui;
    }

    public int getCodTipDocGui() {
        return codTipDocGui;
    }

    public int getCodDocGui() {
        return codDocGui;
    }
     
    
    public String[] obtenerRptImpOD(int intCodBodGrp){
        String strRtaRptOD="",strNomImp="",strEmpNumGui="",strLocNumGui="";
        String[] strArrRet=new String[4];
        if(intCodBodGrp==1){//TUVAL
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper";
            strNomImp="od_califormia";  
            strEmpNumGui="1";
            strLocNumGui="4";
            
        }else if(intCodBodGrp==2){//DIMULTI
            strRtaRptOD="/Reportes/Compras/ZafCom23/Dimulti/ZafRptCom23_01.jasper";
            strNomImp="od_dimulti";
            strEmpNumGui="4";
            strLocNumGui="3";            
            
        }else if(intCodBodGrp==3){//QUITO
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Quito/ZafRptCom23_01.jasper";
            strNomImp="od_quito";    
            strEmpNumGui="2";
            strLocNumGui="1";                        
            
        }else if(intCodBodGrp==5){//MANTA
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Manta/ZafRptCom23_01.jasper";
            strNomImp="od_manta";   
            strEmpNumGui="2";
            strLocNumGui="4";                                    
            
        }else if(intCodBodGrp==6){//IMPORTACIONES
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasperXXX";
            strNomImp="PrintBodega2XXX";   
            strEmpNumGui="1";
            strLocNumGui="6";
            
            
        }else if(intCodBodGrp==11){//STO DOMINGO
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/SantoDomingo/ZafRptCom23_01.jasper";
            strNomImp="od_stodgo";      
            strEmpNumGui="2";
            strLocNumGui="6";            
            
        }else if(intCodBodGrp==15){//INMACONSA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper_xxx";
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/Inmaconsa/ZafRptCom23_01.jasper";
            //C:\Users\postgres\Desktop\zafiro_desarrollo\proyecto\Reportes\Compras\ZafCom23
            //strNomImp="od_inmaconsa_xxx";   
            strNomImp="od_inmaconsa";
            strEmpNumGui="1";
            strLocNumGui="10";
            
        }else if(intCodBodGrp==28){//CUENCA
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23_01.jasper";
            strNomImp="od_cuenca";   
            strEmpNumGui="2";
            strLocNumGui="10";
            
        
        }
        strArrRet[0]=strRtaRptOD;
        strArrRet[1]=strNomImp;
        strArrRet[2]=strEmpNumGui;
        strArrRet[3]=strLocNumGui;        
        
        return strArrRet;
    }
    
    public String[] obtenerRptImpGuiRem(int intCodBodGrp){
        String strRtaRptOD="",strNomImp="",strEmpNumGui="",strLocNumGui="";
        String[] strArrRet=new String[4];
        if(intCodBodGrp==1){//TUVAL
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_califormia";  
            strEmpNumGui="1";
            strLocNumGui="4";
            
        }else if(intCodBodGrp==2){//DIMULTI
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Dimulti/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_dimulti";
            strEmpNumGui="4";
            strLocNumGui="3";            
            
        }else if(intCodBodGrp==3){//QUITO
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Quito/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_quito";    
            strEmpNumGui="2";
            strLocNumGui="1";                        
            
        }else if(intCodBodGrp==5){//MANTA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Manta/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_manta";   
            strEmpNumGui="2";
            strLocNumGui="4";                                    
            
        }else if(intCodBodGrp==6){//IMPORTACIONES
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasperXXX";
            strNomImp="PrintBodega2XXX";   
            strEmpNumGui="1";
            strLocNumGui="6";
            
            
        }else if(intCodBodGrp==11){//STO DOMINGO
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/SantoDomingo/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_stodgo";      
            strEmpNumGui="2";
            strLocNumGui="6";            
            
        }else if(intCodBodGrp==15){//INMACONSA
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_inmaconsa";   
            strEmpNumGui="1";
            strLocNumGui="10";
            
        }else if(intCodBodGrp==28){//CUENCA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_cuenca";   
            strEmpNumGui="2";
            strLocNumGui="10";
            
        
        }
        strArrRet[0]=strRtaRptOD;
        strArrRet[1]=strNomImp;
        strArrRet[2]=strEmpNumGui;
        strArrRet[3]=strLocNumGui;        
        
        return strArrRet;
    }    
    
    
    /*public String[] obtenerRptImpGUIREM(int intCodBodGrp){
        String strRtaRptGRM="",strNomImp="";
        String[] strArrRet=new String[2];
        if(intCodBodGrp==1){//TUVAL.
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23.jasper";
            strNomImp="guias_califormia";  
        }else if(intCodBodGrp==2){//DIMULTI.
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Dimulti/ZafRptCom23.jasper";
            strNomImp="guias_dimulti";                       
        }else if(intCodBodGrp==3){//QUITO.
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Castek/Quito/ZafRptCom23.jasper";
            strNomImp="guias_quito";                       
        }else if(intCodBodGrp==5){//MANTA.
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Castek/Manta/ZafRptCom23.jasper";
            strNomImp="guias_manta";                       
        }else if(intCodBodGrp==6){//IMPORTACIONES.
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23.jasperXXX";
            strNomImp="printguiaXXX";                       
        }else if(intCodBodGrp==11){//STO DOMINGO.
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Castek/SantoDomingo/ZafRptCom23.jasper";
            strNomImp="guias_stodgo";                       
        }else if(intCodBodGrp==15){//INMACONSA.
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23.jasper_xxx";
            strNomImp="guias_califormia_xxx";                       
        }else if(intCodBodGrp==28){//Cuenca
            strRtaRptGRM="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23.jasper";
            strNomImp="guias_cuenca";                               
        }
        strArrRet[0]=strRtaRptGRM;
        strArrRet[1]=strNomImp;
        return strArrRet;
    }*/
    
    
    
    //***********************************Metodos para Cliente Retira-LuisParrales********************************//
    
    public void generarOdCliRet(Connection con,int intCodBodGrp){
    
            String strSql;
            ResultSet rstCen;
            Connection connCen;
            Statement stmCen;
            
        
        try {
            stmCen=con.createStatement();
            if (con!=null) {
                strSql=" select x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, x.co_bod,  x.dirbor, a2.co_bodgrp from ( select * from ( "+
                    " SELECT a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc  , a1.co_bod , a4.tx_dir as dirbor ,a.co_ptodes, "+
                    " ( select count(x.co_doc) from tbm_detmovinv as x where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.st_cliretemprel='S' ) as exiCliret " +
                    " FROM tbm_cabmovinv AS a " +
                    " INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
                    " INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod) " +
                    " INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc) " +
                    " INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod) " +
                    " WHERE a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 ) " +
                    " AND a.st_creguirem='N' "+
                    " AND a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'   "+
                    " AND (a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )   "+
                    " GROUP BY  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped, a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes "+
                    " ) as x where exiCliret > 0 ) as x "+
                    " inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes ) "+
                    " GROUP BY x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, x.co_bod, x.dirbor, a2.co_bodgrp ";
            
            
            rstCen=stmCen.executeQuery(strSql);
            
            while(rstCen.next()){
                insertarGuiaRem_cliret(con,rstCen.getInt("co_emp"), rstCen.getInt("co_loc"), rstCen.getInt("co_tipdoc"), rstCen.getInt("co_doc"), rstCen.getInt("co_bod"), rstCen.getString("dirbor"), rstCen.getInt("co_bodgrp")  ); //rstCen.getInt("co_tipdoc"), rstCen.getInt("co_doc"), rstCen.getInt("co_bod"), rstCen.getString("tx_dir"), rstCen.getString("docrel"), rstCen.getString("tx_numped") );
            }
            rstCen.close();
            rstCen=null;
            stmCen.close();
            stmCen=null;
            con.close();
            connCen=null;
            
            }//fin de if
            
            
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(ZafGenGuiRem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//fin de genera OD cliente retira
    
    
    /**
     * agregue campo de coneccion
     */
private boolean insertarGuiaRem_cliret(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, String strDirBod, int intCodBodGrpIng ){ // int intCodTipDoc, int intCodDoc,  int intCodBod, String strDirBod, String strDocRel, String strNumPen ){
            
    boolean blnRes=false;
            java.sql.Connection connCen;
            java.sql.Statement stmCen, stmLoc;
            java.sql.ResultSet rstCen;
            String strSql="";
            int intTipDocGuia=101;
            int intCodDocGuia=0;
            int intNumGuiaDes=0;
            
            int intCodEmpLoc=0;
            int intCodLocLoc=0;
  try{
    //connCen =  java.sql.DriverManager.getConnection( strStrConnDb, strUsrConnDb, strClaConnDb );

    //if(connCen!=null){
        if(con!=null){
      con.setAutoCommit(false);

      stmCen=con.createStatement();
      stmLoc=con.createStatement();

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabguirem WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intTipDocGuia;
      rstCen = stmCen.executeQuery(strSql);
      if(rstCen.next())
           intCodDocGuia = rstCen.getInt("co_doc");
      rstCen.close();
      rstCen=null;
            
            

      intNumGuiaDes=_getNumGuiaDes(con, intCodEmpLoc, intCodLocLoc );

       strSql=" UPDATE tbm_loc SET ne_ultnumorddes="+intNumGuiaDes+" " +
       " WHERE co_emp="+intCodEmpLoc+" AND co_loc="+intCodLocLoc;
       stmLoc.executeUpdate(strSql);

         if( insertarCabGuiaRem_cliret( con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrpIng, intTipDocGuia, intCodDocGuia, intNumGuiaDes, intCodBod, strDirBod  ) ){
          if( insertarDetGuiaRem_cliret( con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodGrpIng, intTipDocGuia, intCodDocGuia , intNumGuiaDes ) ){
               con.commit();

               //almacenarArchHis("Ord.Des.--> "+ intNumGuiaDes );
               //enviarNumODBod(intNumGuiaDes);
               
               //enviarPulsoFacturacionElectronica();

               if(strImpRptBod2.equals("S"))
               impresionGuiaRemAutBod2(con, intCodEmp, intCodLoc, intTipDocGuia, intCodDocGuia );

          }else {  con.rollback(); }
         }else {  con.rollback(); }

    stmLoc.close();
    stmLoc=null;
    stmCen.close();
    stmCen=null;
    con.close();
    con=null;

    blnRes=true;
  }}catch(java.sql.SQLException Evt){ blnRes=false;  System.out.println(""+Evt );  objEnvMail.enviarCorreo(" "+Evt.toString() );  }
    catch(Exception Evt){ blnRes=false;  System.out.println(""+Evt ); objEnvMail.enviarCorreo(" "+Evt.toString() );  }
  return  blnRes;
}
    
    
    private boolean insertarCabGuiaRem_cliret(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBodGrpIng, int intCodTipDocGuia, int intCodDocGuia, int intNumGuiaRem, int intCodBod, String strDirBod ){  //, String strDocRel, String strNumPen ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strSql01="";
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();
       int intCodBodGrp=0;

     strSql=" select a.*  " +
     " from (  " +
     "  select x.* from (  " +
     "  SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc  , a1.co_bod, ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir, a.tx_numped    " +
     "  ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  " +
     "   FROM tbm_cabmovinv AS a    " +
     "   INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)   " +
     "   INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)    " +
     "   INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)    " +
     "   INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)    " +
     "   WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )    " +
     "   AND  a.st_creguirem='N'    " +
     "   AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'    " +
     "   AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )    " +
     "  GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped  " +
     " ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  " +
     "  ) as x   " +
     "  inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )    " +
     "  where  ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpIng+" )    and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+"  and  x.co_tipdoc="+intCodTipDoc+"  and x.co_doc="+intCodDoc+" " +
     "  ) as x   " +
     " INNER JOIN tbm_cabmovinv as a on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc) ";
     
    rstLoc=stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
          strSql01="INSERT INTO tbm_cabguirem( co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_orddes "+
          " ,ne_numorddes, ne_numdoc, co_clides, tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp " +
          " ,st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar " +
          " ,tx_datdocoriguirem, tx_numped, co_ptodes ,st_imporddes  ) "+
          " VALUES( "+rstLoc.getString("co_emp")+", "+rstLoc.getString("co_loc")+", "+intCodTipDocGuia+", "+intCodDocGuia+",  current_date, current_date "+   // '"+rstLoc.getString("fe_doc")+"' " +
          " ,"+intNumGuiaRem+", 0, "+rstLoc.getString("co_cli")+", '"+rstLoc.getString("tx_ruc")+ "', '"+rstLoc.getString("tx_nomcli")+"', '"+rstLoc.getString("tx_dircli")+"' " +
          " ,'"+rstLoc.getString("tx_telcli")+"', '"+rstLoc.getString("tx_ciucli")+ "', 'N' ,'A', current_timestamp , 1 " +
          " ,'P', current_date, current_date, '', 'I' , "+intCodBod+", '"+strDirBod+"'  " +
          " , '' ,'',  "+rstLoc.getString("co_ptodes")+",'S' )";
       //   " , '"+strDocRel+"' ,'"+strNumPen+"',  "+rstLoc.getString("co_ptodes")+" )";

   }
   rstLoc.close();
   rstLoc=null;

   stmLoc.executeUpdate(strSql01);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}catch(java.sql.SQLException Evt){ 
    blnRes=false;  
    System.out.println(""+Evt.toString() ); 
    objEnvMail.enviarCorreo(" "+Evt.toString() );   }
  catch(Exception Evt){ blnRes=false;  
  System.out.println(""+Evt.toString() );  
  objEnvMail.enviarCorreo(" "+Evt.toString() ); }
 return blnRes;
}

private boolean insertarDetGuiaRem_cliret(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBodGrpIng, int intCodTipDocGuia, int intCodDocGuia, int intNumGuiaRem ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", strSql01="";
  int intEstIns=0;
  int intReg=0;
  
  int intCodBodGrp=0;
  
  try{
   if(conn!=null){
      stmLoc=conn.createStatement();

      StringBuffer stbinsGuia=new StringBuffer(); //VARIABLE TIPO BUFFER
       

     strSql=" select x.*  "+
     " , a.co_reg, a.co_itm, a.tx_codalt, a.tx_nomitm, a.tx_unimed, a.nd_can, a.st_meringegrfisbod  "+
     " from (  "+
     " select x.* from (  "+
     "  SELECT  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc  , a1.co_bod, ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir, a.tx_numped    "+
     " ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  "+
     "  FROM tbm_cabmovinv AS a    "+
     "   INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)    "+
     "   INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)    "+
     "   INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)    "+
     "   INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)    "+
     "  WHERE   a.co_tipdoc IN ( 124,125,126,127,136,137,138,139,166,167, 224,225,226,227 )    "+
     "  AND  a.st_creguirem='N'    "+
     "  AND  a.st_reg not in('E','I')  and a1.st_meringegrfisbod='S'    "+
     "  AND ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrp+" )    "+
     "  GROUP BY  a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, a3.tx_descor, a.ne_numdoc, a4.tx_dir, a.tx_numped  "+
     "   ,a.co_cli , a.tx_nomcli, a.tx_dircli, a.co_ptodes  "+
     "    ) as x   "+
     "    inner JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=x.co_emp AND a2.co_bod=x.co_ptodes )    "+
     "    where  ( a2.co_empGrp=0 AND a2.co_bodGrp= "+intCodBodGrpIng+" )    and x.co_emp="+intCodEmp+" and x.co_loc="+intCodLoc+"   and  x.co_tipdoc="+intCodTipDoc+"  and x.co_doc="+intCodDoc+"  "+
     "    ) as x   "+
     "  INNER JOIN tbm_detmovinv as a on (a.co_emp=x.co_emp and a.co_loc=x.co_loc and a.co_tipdoc=x.co_tipdoc and a.co_doc=x.co_doc)    "+
     "  where  a.nd_can < 0  ";

    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
       intReg++;
       if(intEstIns > 0)
         stbinsGuia.append(" UNION ALL ");


       String strObsDocRel = rstLoc.getString("docrel") +"  "+rstLoc.getString("tx_numped");

//<editor-fold defaultstate="collapsed" desc="CODIGO COMENTADO">
       /*stbinsGuia.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDocGuia+", "+intCodDocGuia+" " +
       " ,"+intReg+", "+intCodEmp+","+intCodLoc+","+rstLoc.getInt("co_tipdoc")+","+rstLoc.getInt("co_doc")+","+rstLoc.getInt("co_reg")+" " +
       " , "+rstLoc.getString("co_itm")+", '"+rstLoc.getString("tx_codalt")+"' " +
       " ,'"+rstLoc.getString("tx_nomitm")+"','"+rstLoc.getString("tx_unimed")+"', abs("+rstLoc.getString("nd_can")+")  " +
       " ,'I', '"+strObsDocRel+"',  '"+rstLoc.getString("st_meringegrfisbod")+"' ");
       intEstIns=1;*/
//</editor-fold>

      stbinsGuia.append("SELECT "+intCodEmp+","+intCodLoc+","+intCodTipDocGuia+", "+intCodDocGuia+" " +
      " ,"+intReg+", "+intCodEmp+","+intCodLoc+","+rstLoc.getInt("co_tipdoc")+","+rstLoc.getInt("co_doc")+","+rstLoc.getInt("co_reg")+" " +
      " , "+rstLoc.getString("co_itm")+", '"+rstLoc.getString("tx_codalt")+"' " +
      " ,"+objUti.codificar(rstLoc.getString("tx_nomitm"))+",'"+rstLoc.getString("tx_unimed")+"', abs("+rstLoc.getString("nd_can")+")  " +
      " ,'I', '"+strObsDocRel+"',  '"+rstLoc.getString("st_meringegrfisbod")+"' ");
      intEstIns=1;

       strSql01+=" ; UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumGuiaRem+"  WHERE co_emp="+intCodEmp+" " +
       " and co_loc="+intCodLoc+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc");

       strSql01+=" ; UPDATE tbm_detmovinv SET tx_obs1='"+intNumGuiaRem+"'  WHERE co_emp="+intCodEmp+" " +
       " and co_loc="+intCodLoc+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and co_doc="+rstLoc.getInt("co_doc");


   }
   rstLoc.close();
   rstLoc=null;

     if(intEstIns==1){
         strSql="INSERT INTO  tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel," +
         " co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm, tx_unimed, nd_can, st_regrep, tx_obs1,  st_meregrfisbod ) "+stbinsGuia.toString();
         stmLoc.executeUpdate(strSql);
         stmLoc.executeUpdate(strSql01);
     }

   stbinsGuia=null;
   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}catch(java.sql.SQLException Evt){ 
    blnRes=false;  System.out.println(""+Evt ); 
    objEnvMail.enviarCorreo(" "+Evt.toString() );  
}
  catch(Exception Evt){ 
      blnRes=false;  System.out.println(""+Evt );  
      objEnvMail.enviarCorreo(" "+Evt.toString() ); }
 return blnRes;
}
    

private int _getNumGuiaDes(java.sql.Connection conn, int intCodEmpGuia, int intCodLocGuia ){
 int intNumDocGuiaDes=0;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
  try{
   if(conn != null ){
       stmLoc=conn.createStatement();

       strSql="SELECT CASE WHEN ne_ultnumorddes is null THEN 1 else ne_ultnumorddes+1 END as numOrdDes FROM tbm_loc WHERE co_emp="+intCodEmpGuia+"   "+
       " AND co_loc="+intCodLocGuia+"  ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next())
         intNumDocGuiaDes = rstLoc.getInt("numOrdDes");
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;

 }}catch(java.sql.SQLException Evt){   System.out.println(""+Evt ); objEnvMail.enviarCorreo(" "+Evt.toString() );  }
    catch(Exception Evt){  System.out.println(""+Evt ); objEnvMail.enviarCorreo(" "+Evt.toString() ); }
  return  intNumDocGuiaDes;
}

private boolean impresionGuiaRemAutBod2(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
  String DIRECCION_REPORTE_GUIA="";
  String strDirSis="";
  String strRutSubRpt="";
  try{
    if(conn!=null){

       strDirSis= getDirectorioSistema();
      
       DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2;
      
        strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));

        //System.out.println("Ruta Reporte OD ->  "+strRutSubRpt );
        System.out.println("Normal 2 Ruta Reporte OD ->  "+DIRECCION_REPORTE_GUIA );

        Map parameters = new HashMap();
        parameters.put("co_emp", new Integer(intCodEmp) );
        parameters.put("co_loc", new Integer(intCodLoc) );
        parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
        parameters.put("co_doc",  new Integer(intCodDoc) );

        parameters.put("SUBREPORT_DIR", strRutSubRpt );

        javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
        objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
        javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
        javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);
        net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        objJRPSerExp.exportReport();
        objPriReqAttSet=null;

 }
  }catch (JRException e) {  
      //almacenarArchHis("impresionGuiaRemAutBod2. Error el imprimir en bodega 2.."+e.toString() ); 
      objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }catch (Exception e) {  
      //almacenarArchHis("impresionGuiaRemAutBod2. Error el imprimir en bodega 2.."+e.toString() ); 
      objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }
 return true;
}



    public String getDirectorioSistema()
    {
        String strRes="";
        try
        {
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e)
        {
            strRes=null;
            objEnvMail.enviarCorreo(" "+e.toString() );
        }
        return strRes;
    }
    
    
    
    
    
    /**
     * Metodo que genera la OD para documentos de transferencia entre bodegas (ventas relacionadas IMPORTACIONES).
     * @param cnx conexion acceso a datos.
     * @param intCodBodGrp codigo de bodega de grupo.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de local.
     * @param intCodTipDoc codigo de tipo de documento.
     * @param intCodDoc codigo de documento
     * @param intCodEmpOD codigo de empresa de OD.
     * @param intCodLocOD codigo de local de OD.
     * @param strRtaRpt ruta de reporte.
     * @param strNomImp nombre de la impresora.
     * @return 
     */
    public boolean generarODTraVenRelImportaciones(Connection cnx , int intCodBodGrp,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc/*, int intCodEmpOD, int intCodLocOD,String strRtaRpt, String strNomImp*/){        
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        //String strTipDocTran="46,153,172"; 
        String strTipDocTran="204, 234";
        String strCodBodegas="1,2,3,5,11,15,7";
        int intCodEmpImp=0;
        int intCodLocImp=0;
        int intCodTipDocOD=101;
        String strNomImpIngImp="";
        boolean booRet=true;
        try{
            ResultSet rsDocTrans=daoGuiRem.obtenerDatosODxTran(cnx, strTipDocTran, intCodBodGrp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            while (rsDocTrans.next()){
                
                ResultSet rsDocTrans2=daoGuiRem.obtenerDocTransGenODTranImportaciones(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                
                while (rsDocTrans2.next()){
                    
                    if(rsDocTrans2.getInt("co_emp")==CODEMPIMPTUV)
                    {
                        intCodEmpImp=CODEMPIMPTUV;
                        intCodLocImp=CODLOCIMPTUV;
                        strNomImpIngImp="TUVAL S.A.";
                    }
                    else if (rsDocTrans2.getInt("co_emp")==CODEMPIMPCASUIO)
                    {
                        intCodEmpImp=CODEMPIMPCASUIO;
                        intCodLocImp=CODLOCIMPCASUIO;
                        strNomImpIngImp="CASTEK S.A.";
                    }
                    else if(rsDocTrans2.getInt("co_emp")==CODEMPIMPDIM)
                    {
                        intCodEmpImp=CODEMPIMPDIM;
                        intCodLocImp=CODLOCIMPDIM;
                        strNomImpIngImp="DIMULTI S.A.";
                    }                
                    
                    
                    if(daoGuiRem.verificaBodODImportaciones(cnx,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,strCodBodegas)){  //Verifica existencia de las bodegas de grupo
                        int intSecOD=daoGuiRem.obtenerSec(cnx, intCodEmpImp, intCodLocImp, intCodTipDocOD);
                        int intNumOrDes=daoGuiRem.obtenerNumOrdDes(cnx, intCodEmpImp, intCodLocImp);
                        boolean booRetAct=daoGuiRem.actualizarTipDocLoc(cnx, intNumOrDes, intCodEmpImp, intCodLocImp);                        
                        if(daoGuiRem.insertCabODxTranImp(cnx, intCodEmpImp, intCodLocImp, intCodTipDocOD, intSecOD, 
                                                   rsDocTrans2.getString("rucemp"), rsDocTrans2.getString("empresa"), rsDocTrans2.getString("tx_dir"), 
                                                   rsDocTrans2.getString("tx_tel"), intNumOrDes, rsDocTrans.getInt("co_bod"), rsDocTrans.getString("tx_dir"), 
                                                   rsDocTrans.getString("docrel"),rsDocTrans2.getString("coclides"), rsDocTrans2.getInt("co_bod"),
                                                   intCodTipDoc, intCodDoc,strNomImpIngImp,rsDocTrans.getInt("co_emp"),rsDocTrans.getInt("co_loc"))){
                                ResultSet rsODDet=daoGuiRem.obtenerDatDetODxTran(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, rsDocTrans.getInt("co_bod"), rsDocTrans2.getInt("co_bod"));
                                if(!daoGuiRem.insertarDetODxTranImp(cnx, rsODDet, intCodEmpImp, intCodLocImp, intCodTipDocOD, intSecOD, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                                    booRet=false;
                                    break;
                                }else{
                                    cnx.commit();
                                    enviarPulsoImpresionGui("imprimeODImportaciones"+"-"+intCodEmpImp+"-"+intCodLocImp+"-"+intCodTipDocOD+"-"+intSecOD,6000);
                                    //objZafImpGuiRem.impresionOrdDesImp(cnx, intCodEmpImp, intCodLocImp, /*intCodEmp, intCodLoc, */intCodTipDocOD, intSecOD);
                                    //objZafImpGuiRem.impresionOrdDesTransInm(cnx, intCodEmpImp, intCodLocImp, intCodTipDocOD, intSecOD, intCodEmpImp);
                                }
                        }else{
                            booRet=false;
                            cnx.rollback();
                            break;
                        }
                        //objZafImpGuiRem.impresionOrdDesImp(cnx, intCodEmpImp, intCodLocImp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                    }                
                }
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            booRet=false;
        }
        return booRet;
    }    

    
    
    /**
     * Metodo que genera la OD para documentos de transferencia entre bodegas (ventas relacionadas INMACONSA). 
     * @param cnx Conexion acceso a datos.
     * @param intCodBodGrp codigo de bodega de grupo.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param intCodEmpOD codigo de la empresa OD proveniento del CONFIG ANTIGUO DE INMACONSA.
     * @param intCodLocOD codigo del local OD proveniente del CONFIG ANTIGUO DE INMACONSA.
     * @return boolean
     */
    public boolean generarODTraVenRelInmaconsa(Connection cnx , int intCodBodGrp,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpOD, int intCodLocOD/*,String strRtaRpt, String strNomImp*/){        
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        String strTipDocTran="46,153,172"; 
        String strCodBodegas="1,2,3,4,5,9,11,15,20,21,22,23,28,31";
        int intCodEmpInm=0;
        int intCodLocInm=0;
        int intCodTipDocOD=101;
        boolean booRet=true;
        try{
            ResultSet rsDocTrans=daoGuiRem.obtenerDatosODxTran(cnx, strTipDocTran, intCodBodGrp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            while (rsDocTrans.next()){
                ResultSet rsDocTrans2=daoGuiRem.obtenerDocTransGenODTranInmaconsa(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                
                while (rsDocTrans2.next()){
                 
                    if(rsDocTrans2.getInt("co_emp")==CODEMPINMTUV)
                    {
                        intCodEmpInm=CODEMPINMTUV;
                        intCodLocInm=CODLOCINMTUV;
                    }
                    else if (rsDocTrans2.getInt("co_emp")==CODEMPINMCASUIO)
                    {
                        intCodEmpInm=CODEMPINMCASUIO;
                        intCodLocInm=CODLOCINMCASUIO;
                    }
                    else if(rsDocTrans2.getInt("co_emp")==CODEMPINMDIM)
                    {
                        intCodEmpInm=CODEMPINMDIM;
                        intCodLocInm=CODLOCINMDIM;
                    }                
                    
                    if(daoGuiRem.verificaBodODInmaconsa(cnx,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,strCodBodegas)){  //Verifica existencia de las bodegas de grupo
                        int intSecOD=daoGuiRem.obtenerSec(cnx, intCodEmpInm, intCodLocInm, intCodTipDocOD);
                        int intNumOrDes=daoGuiRem.obtenerNumOrdDes(cnx, intCodEmpOD, intCodLocOD);
                        boolean booRetAct=daoGuiRem.actualizarTipDocLoc(cnx, intNumOrDes, intCodEmpOD, intCodLocOD);                        
                        if(daoGuiRem.insertCabODxTranInm(cnx, intCodEmp, intCodLoc, intCodTipDocOD, intSecOD, 
                                                   rsDocTrans2.getString("rucemp"), rsDocTrans2.getString("empresa"), rsDocTrans2.getString("tx_dir"), 
                                                   rsDocTrans2.getString("tx_tel"), intNumOrDes, rsDocTrans.getInt("co_bod"), rsDocTrans.getString("tx_dir"), 
                                                   rsDocTrans.getString("docrel"),rsDocTrans2.getString("coclides"), rsDocTrans2.getInt("co_bod"),
                                                   intCodTipDoc, intCodDoc,intCodEmpInm,intCodLocInm)){
                                ResultSet rsODDet=daoGuiRem.obtenerDatDetODxTran(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, rsDocTrans.getInt("co_bod"), rsDocTrans2.getInt("co_bod"));
                                if(!daoGuiRem.insertarDetODxTranInm(cnx, rsODDet, intCodEmpInm, intCodLocInm, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodTipDocOD, intSecOD)){
                                    booRet=false;
                                    cnx.rollback();
                                    break;
                                }else{
                                    cnx.commit();
                                    enviarPulsoImpresionGui("imprimeODInmaconsa"+"-"+intCodEmpInm+"-"+intCodLocInm+"-"+intCodTipDocOD+"-"+intSecOD+"-"+intCodEmpInm, 6000);                                    
                                    //objZafImpGuiRem.impresionOrdDesTransInm(cnx, intCodEmpInm, intCodLocInm, intCodTipDocOD, intSecOD, intCodEmpInm);
                                }
                        }else{
                            booRet=false;
                            cnx.rollback();
                            break;
                        }
                        //objZafImpGuiRem.impresionOrdDesTransInm(cnx, intCodEmpInm, intCodLocInm, intCodTipDocOD, intSecOD, intCodEmpInm);
                    }                
                }
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return booRet;
    } 
    
    
    
    /**
     * Metodo que genera la OD por Transferencia CASTEK CUENCA.
     * @param cnx Conexion acceso a base de datos.
     * @param intCodBodGrp codigo de bodega del Grupo.
     * @param intCodEmp codigo de la emrpesa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento
     * @param intCodEmpOD codigo de la empresa proveniente de CONFIG CASTEK CUENCA.
     * @param intCodLocOD codigo del local proveniente de  CONFIG CASTEK CUENCA.
     * @return boolean 
     */
    public boolean generarODTraVenRelCuenca(Connection cnx , int intCodBodGrp,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpOD, int intCodLocOD,String strRtaRpt, String strNomImp){        
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        String strTipDocTran="46,153,172"; 
        String strCodBodegas="1,2,3,4,5,9,11,15,20,21,22,23";
        int intCodTipDocOD=101;
        boolean booRet=true;
        try{
            ResultSet rsDocTrans=daoGuiRem.obtenerDatosODxTran(cnx, strTipDocTran, intCodBodGrp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            while (rsDocTrans.next()){
                ResultSet rsDocTrans2=daoGuiRem.obtenerDocTransGenODTranCuenca(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
                
                while (rsDocTrans2.next()){
                    
                    if(daoGuiRem.verificaBodODCuenca(cnx,intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,strCodBodegas)){  //Verifica existencia de las bodegas de grupo
                        int intSecOD=daoGuiRem.obtenerSec(cnx, intCodEmp, intCodLoc, intCodTipDocOD);
                        int intNumOrDes=daoGuiRem.obtenerNumOrdDes(cnx, intCodEmpOD, intCodLocOD);
                        boolean booRetAct=daoGuiRem.actualizarTipDocLoc(cnx, intNumOrDes, intCodEmpOD, intCodLocOD);                        
                        if(daoGuiRem.insertCabODxTran(cnx, intCodEmp, intCodLoc, intCodTipDocOD, intSecOD, 
                                                   rsDocTrans2.getString("rucemp"), rsDocTrans2.getString("empresa"), rsDocTrans2.getString("tx_dir"), 
                                                   rsDocTrans2.getString("tx_tel"), intNumOrDes, rsDocTrans.getInt("co_bod"), rsDocTrans.getString("tx_dir"), 
                                                   rsDocTrans.getString("docrel"),rsDocTrans2.getString("coclides"), rsDocTrans2.getInt("co_bod"),
                                                   intCodTipDoc, intCodDoc)){
                                ResultSet rsODDet=daoGuiRem.obtenerDatDetODxTran(cnx, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, rsDocTrans.getInt("co_bod"), rsDocTrans2.getInt("co_bod"));
                                if(!daoGuiRem.insertarDetODxTran(cnx, rsODDet, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodTipDocOD, intSecOD)){
                                    booRet=false;
                                    cnx.rollback();
                                    break;
                                }
                        }else{
                            booRet=false;
                            cnx.rollback();
                            break;
                        }
                        cnx.commit();
                        //objZafImpGuiRem.impresionGuiaRemAutBod2(cnx, intCodEmp, intCodLoc, intCodTipDocOD, intSecOD, strRtaRpt, strNomImp);
                    }                
                }
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            
        }
        return booRet;
    }     
    
    /**
     * Metodo que genera la OD cuando se hace la recepcion del cheque.
     * @param i indice de los arreglos de tipos de documentos y codigos de facturas.
     * @param conn Conexion acceso a datos.
     * @param intCodEmp Codigo de empresa.
     * @param intCodLoc Codigo de local.
     * @param arrStrTip arreglo que contiene los tipos de documentos de las facturas seleccionadas para pagar con el cheque.
     * @param arrCodDoc arreglo que contiene los codigo de documentos de las facturas seleccionadas para pagar el cheque.
     * @return boolean.
     */
    public boolean generarODxTraRecChq(int i, Connection conn, int intCodEmp, int intCodLoc, String[] arrStrTip, String[] arrCodDoc){ 
        boolean retorno=false;
        int intCodEmpRel=0, intCodLocRel=0, intCodTipDocRel=0, intCodDocRel=0, intCodBodRel=0;
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        try{
            ResultSet rsRegRel=daoGuiRem.obtenerRegRelPreTra(conn, intCodEmp, intCodLoc, Integer.parseInt(arrStrTip[i]), Integer.parseInt(arrCodDoc[i]));
            while (rsRegRel.next()){
                //u.co_emp, u.co_loc, u.co_tipdoc, u.co_doc, m.co_bod 
               intCodEmpRel = rsRegRel.getInt("co_emp");
               intCodLocRel = rsRegRel.getInt("co_loc");
               intCodTipDocRel = rsRegRel.getInt("co_tipdoc");
               intCodDocRel = rsRegRel.getInt("co_doc");
               intCodBodRel= rsRegRel.getInt("co_bod");

               int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn, intCodEmpRel, intCodBodRel);
               String[] strRet=obtenerRptImpOD(intCodBodGrp);
               //Por Transferencias entre bodegas Generacion de OD.
               if(intCodTipDocRel==46 || intCodTipDocRel==153 || intCodTipDocRel==172){

                    if((intCodEmpRel==1 && intCodLocRel==6) || 
                            (intCodEmpRel==2 && intCodLocRel==9) || 
                            (intCodEmpRel==4 && intCodLocRel==4)){
                                retorno= generarODTraVenRelImportaciones(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, 
                                                    intCodTipDocRel, intCodDocRel);
                    }
                    else if((intCodEmpRel==2 && intCodLocRel==6)|| 
                            (intCodEmpRel==2 && intCodLocRel==1)||
                            (intCodEmpRel==2 && intCodLocRel==4)||
                            (intCodEmpRel==2 && intCodLocRel==10)){                    
                                retorno= generarODTraVenRelInmaconsa(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]));
                    }else{
                         retorno=generarODTraVenRel(conn, intCodBodGrp, intCodEmpRel, 
                                                           intCodLocRel,intCodTipDocRel, intCodDocRel, 
                                                           Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0],strRet[1]);                                    
                    }
               }                                
            }
            rsRegRel.close();
            rsRegRel=null; 
            retorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retorno;    
    }
    
    
    /**
     * 
     * @param i
     * @param conn
     * @param intCodEmp
     * @param intCodLoc
     * @param arrStrTip
     * @param arrCodDoc
     * @return 
     */
    public boolean generarODxPreComVenRecChq(int i, Connection conn, int intCodEmp, int intCodLoc, String[] arrStrTip, String[] arrCodDoc){
        boolean retorno=false;
        int intCodEmpRel=0, intCodLocRel=0, intCodTipDocRel=0, intCodDocRel=0, intCodBodRel=0;
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        try{
            ResultSet rsRegRel=daoGuiRem.obtenerRegRelPreTra(conn, intCodEmp, intCodLoc, Integer.parseInt(arrStrTip[i]), Integer.parseInt(arrCodDoc[i]));
            while (rsRegRel.next()){
                //u.co_emp, u.co_loc, u.co_tipdoc, u.co_doc, m.co_bod 
               intCodEmpRel = rsRegRel.getInt("co_emp");
               intCodLocRel = rsRegRel.getInt("co_loc");
               intCodTipDocRel = rsRegRel.getInt("co_tipdoc");
               intCodDocRel = rsRegRel.getInt("co_doc");
               intCodBodRel= rsRegRel.getInt("co_bod");

               int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn, intCodEmpRel, intCodBodRel);
               String[] strRet=obtenerRptImpOD(intCodBodGrp);
               //Por Transferencias entre bodegas Generacion de OD.
               

                    int intEmpNumGuia=Integer.parseInt(strRet[2]); 
                    int intLocNumGuia=Integer.parseInt(strRet[3]);
                    
                    if(intCodEmpRel==2 && intCodLocRel==10){//Ventas Relacionadas a Castek Cuenca.
                        retorno=generarOrdDesxCliNoRetFacVenRelCuenca(conn, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel, intCodBodGrp, /*strRet[0],strRet[1],*/ intEmpNumGuia, intLocNumGuia);
                    }else if((intCodEmpRel==1 && intCodLocRel==4)||(intCodEmpRel==4 && intCodLocRel==3)){//Ventas relacionadas a INMACONSA.
                        retorno=generarOrdDesxCliNoRetFacVenRelInmaconsa(conn, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel, intCodBodGrp, strRet[0],strRet[1], intEmpNumGuia, intLocNumGuia);
                    }else{
                        retorno=generarOrdDesxCliNoRetFacVenRel(conn,intCodEmpRel,
                                intCodLocRel,intCodTipDocRel,intCodDocRel,
                                intCodBodGrp,strRet[0],strRet[1],intEmpNumGuia,intLocNumGuia);    
                    }

               
               /*if(intCodTipDocRel==46 || intCodTipDocRel==153 || intCodTipDocRel==172){

                    if((intCodEmpRel==1 && intCodLocRel==6) || 
                            (intCodEmpRel==2 && intCodLocRel==9) || 
                            (intCodEmpRel==4 && intCodLocRel==4)){
                                boolean booRetorno= generarODTraVenRelImportaciones(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, 
                                                    intCodTipDocRel, intCodDocRel);
                    }
                    else if((intCodEmpRel==2 && intCodLocRel==6)|| 
                            (intCodEmpRel==2 && intCodLocRel==1)||
                            (intCodEmpRel==2 && intCodLocRel==4)||
                            (intCodEmpRel==2 && intCodLocRel==10)){                    
                                boolean booRetorno2= generarODTraVenRelInmaconsa(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]));
                    }else{
                        boolean booRetorno3=generarODTraVenRel(conn, intCodBodGrp, intCodEmpRel, 
                                                           intCodLocRel,intCodTipDocRel, intCodDocRel, 
                                                           Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0],strRet[1]);                                    
                    }
               }  */                              
            }
            rsRegRel.close();
            rsRegRel=null; 
            retorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retorno;    
    }    


public boolean generarODxTraRecChq( Connection conn, int intCodEmp, int intCodLoc, int intTipDoc, int  intCodDoc){
        boolean retorno=false;
        int intCodEmpRel=0, intCodLocRel=0, intCodTipDocRel=0, intCodDocRel=0, intCodBodRel=0;
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        try{
            // obtiene el registro relacionado de una transferencia
            ResultSet rsRegRel=daoGuiRem.obtenerRegRelPreTra(conn, intCodEmp, intCodLoc, intTipDoc, intCodDoc);
            while (rsRegRel.next()){
                
               intCodEmpRel = rsRegRel.getInt("co_emp");
               intCodLocRel = rsRegRel.getInt("co_loc");
               intCodTipDocRel = rsRegRel.getInt("co_tipdoc");
               intCodDocRel = rsRegRel.getInt("co_doc");
               intCodBodRel= rsRegRel.getInt("co_bod");                

               int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn, intCodEmpRel, intCodBodRel);
               String[] strRet=obtenerRptImpOD(intCodBodGrp);
               
               //Por Transferencias entre bodegas Generacion de OD.
               if(intCodTipDocRel==46 || intCodTipDocRel==153 || intCodTipDocRel==172){

                    if(intCodBodGrp==4){
                            retorno= generarODTraVenRelImportaciones(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, 
                                                    intCodTipDocRel, intCodDocRel);
                    }
                    else if(intCodBodGrp==28){
                             retorno = generarODTraVenRelCuenca(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0],strRet[1]);                    
                    }                    
                    else if(intCodBodGrp==15){                    
                            retorno= generarODTraVenRelInmaconsa(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]));
                    }else{
                         retorno=generarODTraVenRel(conn, intCodBodGrp, intCodEmpRel, 
                                                           intCodLocRel,intCodTipDocRel, intCodDocRel, 
                                                           Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0],strRet[1]);                                    
                    }
               }                                
            }
            rsRegRel.close();
            rsRegRel=null; 
            //retorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return retorno;    
    }    
    
    
    public boolean generarODxPreComVenRecChq(/*int i,*/ Connection conn, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDoc){
        boolean retorno=false;
        int intCodEmpRel=0, intCodLocRel=0, intCodTipDocRel=0, intCodDocRel=0, intCodBodRel=0;
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        try{
            ResultSet rsRegRel=daoGuiRem.obtenerRegRelPreTra(conn, intCodEmp, intCodLoc, intTipDoc, intCodDoc);
            while (rsRegRel.next()){
                
               intCodEmpRel = rsRegRel.getInt("co_emp");
               intCodLocRel = rsRegRel.getInt("co_loc");
               intCodTipDocRel = rsRegRel.getInt("co_tipdoc");
               intCodDocRel = rsRegRel.getInt("co_doc");
               intCodBodRel= rsRegRel.getInt("co_bod");                

               int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn, intCodEmpRel, intCodBodRel);
               String[] strRet=obtenerRptImpOD(intCodBodGrp);
               //Por Transferencias entre bodegas Generacion de OD.
               

                    int intEmpNumGuia=Integer.parseInt(strRet[2]); 
                    int intLocNumGuia=Integer.parseInt(strRet[3]);
                    
                    //if(intCodEmpRel==2 && intCodLocRel==10){//Ventas Relacionadas a Castek Cuenca.
                    if(intCodBodGrp==28){//Ventas Relacionadas a Castek Cuenca.
                        retorno=generarOrdDesxCliNoRetFacVenRelCuenca(conn, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel, intCodBodGrp, /*strRet[0],strRet[1],*/ intEmpNumGuia, intLocNumGuia);
                    }else if(intCodBodGrp==15){
                        retorno=generarOrdDesxCliNoRetFacVenRelInmaconsa(conn, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel, intCodBodGrp, strRet[0],strRet[1], intEmpNumGuia, intLocNumGuia);
                    }else{
                        retorno=generarOrdDesxCliNoRetFacVenRel(conn,intCodEmpRel,
                                intCodLocRel,intCodTipDocRel,intCodDocRel,
                                intCodBodGrp,strRet[0],strRet[1],intEmpNumGuia,intLocNumGuia);   
                    }
                                            
            }
            rsRegRel.close();
            rsRegRel=null; 
            //retorno=true;
        }catch(Exception ex){
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return retorno;    
    }
    
    
    
    public boolean generarProTermL(Connection conn,int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc){
        boolean booRetorno=true;
        String strOD="";
        String strDetFac="";
        Statement stmOD;
        Statement stmBod;
                     
        try{

            /*strDetFac="select distinct co_bod from tbm_detmovinv where co_emp="+intCodEmp+
            " and co_loc="+intCodLoc+
            " and co_tipdoc="+intCodTipDoc+
            " and co_doc="+intCodDoc+
            " and substring(tx_codalt,length(tx_codalt), length(tx_codalt) ) in ('L','P')";
            stmBod=conn.createStatement();
            ResultSet rsBodTerL=stmBod.executeQuery(strDetFac);
            int intCodBod=0;
            if(rsBodTerL.next()){
                intCodBod=rsBodTerL.getInt("co_bod");
            }*/
                         
            //strOD="select * from tbm_cabguirem where substr(tx_datdocoriguirem,8,10)='"+intNumDoc+"' and co_emp="+intCodEmp+" and co_loc="+intCodLoc;
            
            strOD= " select distinct b.co_emp, b.co_loc, b.co_tipdoc, b.co_doc, b.co_ptopar "+
            " from       tbm_cabguirem b "+
            " inner join tbm_detguirem c on b.co_doc=c.co_doc and b.co_tipdoc=c.co_tipdoc and b.co_loc=c.co_loc and b.co_emp = c.co_emp"+
            " inner join tbm_cabmovinv d on c.co_docrel=d.co_doc and c.co_locrel=d.co_loc and c.co_tipdocrel=d.co_tipdoc and c.co_emp= d.co_emp"+
            " where      d.co_emp="+intCodEmp+" and d.co_loc="+intCodLoc+" and d.co_tipdoc = "+intCodTipDoc+"and d.co_doc="+intCodDoc;

            stmOD =conn.createStatement();
            ResultSet rsCabGuiRem=stmOD.executeQuery(strOD);
            if(rsCabGuiRem.next()){
                int intCodEmpOD=rsCabGuiRem.getInt("co_emp");
                int intCodLocOD=rsCabGuiRem.getInt("co_loc");
                int intCodTipDocOD=rsCabGuiRem.getInt("co_tipdoc");
                int intCodDocOD=rsCabGuiRem.getInt("co_doc");
				
                //Agregado para items L
                int intCodBod=rsCabGuiRem.getInt("co_ptopar");
				
                int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn,intCodEmpOD , intCodBod);
                String[] strRet=obtenerRptImpOD(intCodBodGrp); 
                if(generarConfEgreAutxItms_L_o_Itms_S(conn,intCodBodGrp, intCodEmpOD, intCodLocOD, intCodTipDocOD, intCodDocOD )){                             

                   int intNumEmpGuia = Integer.parseInt(strRet[2]);
                   int intNumLocGuia = Integer.parseInt(strRet[3]);
                   if(generarGuiRemxConfEgrCliNoRet(conn, intCodEmpOD, intCodLocOD, intCodTipDocOD, intCodDocOD, intCodBodGrp,intNumEmpGuia,intNumLocGuia)){
                       if(booSiIns){//Solo hace commit cuando exista alguna insercion en por confirmacion.
                           conn.commit();    
                           if(intCodEmpOD >0){
                               //enviarPulsoFacturacionElectronica();
							   enviarPulsoGuiaElectronica();
                               String[] strRetGui=obtenerRptImpGuiRem(intCodBodGrp);
                               enviarPulsoImpresionGui("imprimeGuiaTerminalL"+"-"+getCodEmpGui()+"-"+getCodLocGui()+"-"+getCodTipDocGui()+"-"+getCodDocGui()+"-"+strRetGui[0]+"-"+strRetGui[1]+"-"+intCodBod, 6000);
                               System.out.println("imprimeGuiaTerminalL"+"-"+getCodEmpGui()+"-"+getCodLocGui()+"-"+getCodTipDocGui()+"-"+getCodDocGui()+"-"+strRetGui[0]+"-"+strRetGui[1]+"-"+intCodBod);
                               //imprimirGuiRemNoRet(conn, getCodEmpGui(), getCodLocGui() , getCodTipDocGui(), getCodDocGui(),strRetGui[0],strRetGui[1],intCodBod);
                           }                                        
                       }
                   }else{
                       conn.rollback();
                   } 
                   
                }else{
                    conn.rollback();
                }
            } 
        }catch(Exception ex){
            booRetorno=false;
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(null, ex);
        }
        return booRetorno;
    }
    
    /**
     * Metodo que genera las OD con tipo 101 en transferencias entre bodegas.
     * @param conn conexion acceso a datos.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de local.
     * @param intTipDoc codito del tipo de documento generado por transferencia
     * @param intCodDoc codigo del documento
     * @return boolean indica si la operacion se realizo con exito o no.
     */
    public boolean generarODxTraManual( Connection conn, int intCodEmp, int intCodLoc, int intTipDoc, int  intCodDoc){
        boolean retorno=false;
        int intCodEmpRel=0, intCodLocRel=0, intCodTipDocRel=0, intCodDocRel=0, intCodBodRel=0;
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        try{
            // obtiene el registro relacionado de una transferencia
            ResultSet rsRegRel=daoGuiRem.obtenerRegRelTraMan(conn, intCodEmp, intCodLoc, intTipDoc, intCodDoc);
            while (rsRegRel.next()){
                
               intCodEmpRel = rsRegRel.getInt("co_emp");
               intCodLocRel = rsRegRel.getInt("co_loc");
               intCodTipDocRel = rsRegRel.getInt("co_tipdoc");
               intCodDocRel = rsRegRel.getInt("co_doc");
               intCodBodRel= rsRegRel.getInt("co_bod");                

               int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn, intCodEmpRel, intCodBodRel);
               String[] strRet=obtenerRptImpOD(intCodBodGrp);
               //Por Transferencias entre bodegas Generacion de OD.
               if(intCodTipDocRel==46 || intCodTipDocRel==153 || intCodTipDocRel==172 || intCodTipDocRel==234){

                    if(intCodBodGrp==4){//COMPRAS LOCALES
                                retorno= generarODTraVenRelImportaciones(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, 
                                                    intCodTipDocRel, intCodDocRel);
                    }else if(intCodBodGrp==28){//CUENCA
                        retorno = generarODTraVenRelCuenca(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0], strRet[1]);                    
                    }                    
                    else if(intCodBodGrp==15){   //INMACONSA                 
                                retorno= generarODTraVenRelInmaconsa(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]));
                    }else{
                         retorno=generarODTraVenRel(conn, intCodBodGrp, intCodEmpRel, 
                                                           intCodLocRel,intCodTipDocRel, intCodDocRel, 
                                                           Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0],strRet[1]);                                    
                    }
               }                                
            }
            rsRegRel.close();
            rsRegRel=null; 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retorno;    
    }  
    
    
    /**
     * Metodo que genera la OD por reposicion de transferencias.
     * @param conn conexion a la base de datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @return boolean.
     */
    public boolean generarODxRepTran( Connection conn, int intCodEmp, int intCodLoc, int intTipDoc, int  intCodDoc){
        boolean retorno=false;
        int intCodEmpRel=0, intCodLocRel=0, intCodTipDocRel=0, intCodDocRel=0, intCodBodRel=0;
        ZafGuiRemDAO daoGuiRem=new ZafGuiRemDAO();
        try{
            // obtiene el registro relacionado de una transferencia
            ResultSet rsRegRel=daoGuiRem.obtenerRegRelRepTran(conn, intCodEmp, intCodLoc, intTipDoc, intCodDoc);
            while (rsRegRel.next()){
                
               intCodEmpRel = rsRegRel.getInt("co_emp");
               intCodLocRel = rsRegRel.getInt("co_loc");
               intCodTipDocRel = rsRegRel.getInt("co_tipdoc");
               intCodDocRel = rsRegRel.getInt("co_doc");
               intCodBodRel= rsRegRel.getInt("co_bod");                

               int intCodBodGrp=new ZafGuiRemDAO().obtenerBodGru(conn, intCodEmpRel, intCodBodRel);
               String[] strRet=obtenerRptImpOD(intCodBodGrp);
               //Por Transferencias entre bodegas Generacion de OD.
               if(intCodTipDocRel==46 || intCodTipDocRel==153 || intCodTipDocRel==172){

                    if(intCodBodGrp==4){
                                retorno= generarODTraVenRelImportaciones(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, 
                                                    intCodTipDocRel, intCodDocRel);
                    }else if(intCodBodGrp==28){
                        retorno = generarODTraVenRelCuenca(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0],strRet[1]);                    
                    }else if(intCodBodGrp==15){                    
                        retorno= generarODTraVenRelInmaconsa(conn, intCodBodGrp, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel,Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]));
                    }else{
                         retorno=generarODTraVenRel(conn, intCodBodGrp, intCodEmpRel, 
                                                           intCodLocRel,intCodTipDocRel, intCodDocRel, 
                                                           Integer.parseInt(strRet[2]), Integer.parseInt(strRet[3]),strRet[0],strRet[1]);                                    
                    }
               }                                
            }
            rsRegRel.close();
            rsRegRel=null; 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retorno;    
    }    

    
    private void enviarPulsoFacturacionElectronica() {
        ZafPulFacEle objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciar();
        //objPulFacEle.iniciar();
        
        System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica  ");
    }    
    
    public void enviarPulsoImpresionGui(String strDat, int intPuerto) {
        ZafPulFacEle objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciarImpresion(strDat, intPuerto);        //objPulFacEle.iniciar();        
        System.out.println(" PULSO::::::  enviarPulsoImpresionGui  ");
    }
	
    private void enviarPulsoGuiaElectronica() {
        ZafPulFacEle objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciarConGui(6012);
        //objPulFacEle.iniciar();
        
        System.out.println(" PULSO::::::  enviarPulsoGuiaElectronica  ");
    }    
	
    
    
}//fin de clase

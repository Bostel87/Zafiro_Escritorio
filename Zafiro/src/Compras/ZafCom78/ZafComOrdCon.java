/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras.ZafCom78;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import javax.swing.JDialog;

/**
 *
 * @author efloresa <@>
 * @purpose: Maneja las transacciones necesarias para registrar una orden de conteo.
 * 
 */
public class ZafComOrdCon implements ZafComOrdenConteo {
    
    private JDialog objJDialog;
    
    private Connection conn;
    private ResultSet rst;
    private PreparedStatement pst;
    
    private String strSQL;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    
    private int intCodEmpRel;                //Código de la empresa.
    private int intCodLocRel;                //Código del local.
    private int intCodTipDocRel;
    private int intCodDocRel;                //Código del docuemnto.    
    private int intCodBodRel;                //Código de bodega
    private int intCodBodGrpRel;
    private int intCodEmpGrp;
    private int intCodBodGrp;
    private int intCodUsrResCon;
    private int intCodDep = 24;             //Codigo de departamento inventaristas.
    private final int intCodTipDoc = 117;   //Codigo de de tipo de documento Ordenes de conteo.
    private final int intCodMnu = 1944;     //Codigo de menu de la aplicacion Seguimiento a ordenes de despacho
    
    public ZafComOrdCon(){
        
    }
    
    public ZafComOrdCon(ZafParSis objParSis){
        this.objParSis = objParSis;
        objUti = new ZafUtil();
        this.objJDialog = new JDialog();
    }
    
    public ZafComOrdCon(JDialog objJDialog, ZafParSis objParSis){
        this.objParSis = objParSis;
        this.objJDialog = objJDialog;
        objUti = new ZafUtil();
    }
    
    /**
     * Esta función establece los parámetros que debe utilizar
     * @param intCodEmpRel El código de la empresa.
     * @param intCodLocRel El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDocRel El código del documento.
     */

    public void setParams(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
        this.intCodEmpRel=intCodEmp;
        this.intCodLocRel=intCodLoc;
        this.intCodTipDocRel=intCodTipDoc;
        this.intCodDocRel=intCodDoc;        
    }
    
    public void setParams(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod) {
        this.intCodEmpRel=intCodEmp;
        this.intCodLocRel=intCodLoc;
        this.intCodTipDocRel=intCodTipDoc;
        this.intCodDocRel=intCodDoc;
        this.intCodBodRel=intCodBod;
    }

    @Override
    public void setParams(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodBodGrpRel) {
        this.intCodEmpRel=intCodEmp;
        this.intCodLocRel=intCodLoc;
        this.intCodTipDocRel=intCodTipDoc;
        this.intCodDocRel=intCodDoc;
        this.intCodBodRel=intCodBod;
        this.intCodBodGrpRel=intCodBodGrpRel;
    }
    
    /**
     * Establece la conexion con la base de datos.
     */
    private Connection getConexion(){
        try{
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conn.setAutoCommit(false);
        }catch(SQLException e){
            conn=null;
        }catch(Exception e){
            conn=null;            
        }
        return conn;
    }
    
    public boolean generaOrdenConteo(){
        boolean blnRes=false;
        int intCodDocOrdCon, intNumDocOrdCon, intCodUsrRes, intCodUsrMod=0;
        try{
            getConexion();
            if (getCodEmpCodBodGrp(conn, this.intCodEmpRel, this.intCodBodGrpRel)){
                intCodDocOrdCon = getMaxCodDocOrdConInv(conn, this.intCodEmpGrp, this.intCodBodGrp, this.intCodTipDoc);
                intNumDocOrdCon = getMaxNumDocOrdConInv(conn, this.intCodEmpGrp, this.intCodBodGrp, this.intCodTipDoc);
                intCodUsrRes = getUsrResConOrdConInv(conn, this.intCodEmpGrp, this.intCodBodGrp, this.intCodTipDoc, intCodDocOrdCon);
                intCodDocOrdCon++;
                intNumDocOrdCon++;
                if (insertarCabOrdConInv(conn, this.intCodEmpGrp, this.intCodBodGrp, this.intCodTipDoc, intCodDocOrdCon, intNumDocOrdCon, intCodUsrRes, this.intCodBodGrpRel, "", "", "A", objParSis.getCodigoUsuario(), intCodUsrMod, "I")){
                    if (insertarDetalle(conn, this.intCodEmpRel, this.intCodLocRel, this.intCodTipDocRel, this.intCodDocRel, this.intCodEmpGrp, this.intCodBodGrp, this.intCodTipDoc, intCodDocOrdCon, this.intCodBodGrpRel)){
                        if (actualizarCabTipDoc(conn, this.intCodEmpGrp, this.intCodBodGrp, this.intCodTipDoc)){
                            conn.commit();
                            blnRes=true;
                        }else
                            conn.rollback();                    
                    }else
                        conn.rollback();
                }else
                    conn.rollback();
            }else
                conn.rollback();
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }
        
        return blnRes;
    }
    
    public boolean getCodEmpCodBodGrp(Connection conn, int intCodEmpRel, int intCodBodRel){
        boolean blnRes = false;
        PreparedStatement pstLocal=null;
        ResultSet rstLocal=null;
        String strSqlLoc;
        strSqlLoc = " select co_empgrp, co_bodgrp from tbr_bodEmpBodGrp where co_emp=? and co_bod=? ";
        try{
            if (conn!=null){
                pstLocal = conn.prepareStatement(strSqlLoc);
                pstLocal.setInt(1, intCodEmpRel);
                pstLocal.setInt(2, intCodBodRel);

                rstLocal=pstLocal.executeQuery();
                if (rstLocal.next()){
                    intCodEmpGrp=rstLocal.getInt("co_empgrp");
                    intCodBodGrp=rstLocal.getInt("co_bodgrp");                
                    blnRes=true;
                }
            }
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }finally{
            try{
                if (rstLocal != null) 
                    rstLocal.close();
                rstLocal = null;
                if (pstLocal != null) 
                    pstLocal.close();
                pstLocal = null;   
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        strSqlLoc=null;
        return blnRes;
    }
    
    @Override
    public int getCodItmGrp(Connection conn, int codEmpGrp, String codAltItmEmp){
        int coItm = -1;
        PreparedStatement pstLocal=null;
        ResultSet rstLocal=null;
        String strSqlLoc;
        strSqlLoc = " select co_itm from tbm_inv where co_emp = ? and tx_codalt = ? ";
        try{
            if (conn!= null){
                pstLocal = conn.prepareStatement(strSqlLoc);
                pstLocal.setInt(1, codEmpGrp);
                pstLocal.setString(2, codAltItmEmp);
                rstLocal = pstLocal.executeQuery();                
                if (rstLocal.next())
                    coItm = rstLocal.getInt("co_itm");                 
            }
        }catch (SQLException e){
            System.err.println(e);
            coItm = -1;
        }catch (Exception e){
            System.err.println(e);
            coItm = -1;
        }finally{
            try{
                if (rstLocal != null) 
                    rstLocal.close();
                rstLocal = null;
                if (pstLocal != null) 
                    pstLocal.close();
                pstLocal = null;   
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        strSqlLoc=null;
        return coItm;
    }
    
    @Override
    public int getMaxCodDocOrdConInv(Connection conn, int codEmp, int codLoc, int codTipDoc){
        int codDoc = -1;
        String strSqlLoc;
        ResultSet rstLoc=null;
        PreparedStatement pstLoc=null; 
        strSqlLoc = " select case when max(a1.co_doc) is null or max(a1.co_doc) = 0 then 0 else max(a1.co_doc) end as codoc " +
                    " from tbm_cabordconinv as a1 " +
                    " where a1.co_emp = ? " + 
                    " and a1.co_loc = ?  " +
                    " and a1.co_tipDoc = ? ";
        try{
            if (conn!= null){
                pstLoc = conn.prepareStatement(strSqlLoc);
                pstLoc.setInt(1, codEmp);
                pstLoc.setInt(2, codLoc);
                pstLoc.setInt(3, codTipDoc);                
                rstLoc = pstLoc.executeQuery();                
                if (rstLoc.next())
                    codDoc = rstLoc.getInt("codoc");                
                
            }
        }catch (SQLException e){
            System.err.println(e);
            codDoc = -1;
        }catch (Exception e){
            System.err.println(e);
            codDoc = -1;
        }finally{
            try{
                if (rstLoc != null) 
                    rstLoc.close();
                rstLoc = null;
                if (pstLoc != null) 
                    pstLoc.close();
                pstLoc = null;                   
            }catch (Throwable e){
                e.printStackTrace();
            }
        }        
        strSqlLoc=null;
        return codDoc;
    }
    
    @Override
    public int getUsrResConOrdConInv(Connection conn, int codEmp, int codLoc, int codTipDoc, int codDoc){
        int codUsr = -1;
        String strSqlLoc;
        ResultSet rstLoc=null;
        PreparedStatement pstLoc=null;
        strSqlLoc = " select case when a1.co_usrrescon is null or a1.co_usrrescon = 0 then 0 else a1.co_usrrescon end as cousr " +
                    " from tbm_cabordconinv as a1 " +
                    " where a1.co_emp = ? " + 
                    " and a1.co_loc = ?  " +
                    " and a1.co_tipDoc = ? " + 
                    " and a1.co_doc = ?"  ;
        try{
            if (conn!= null){
                pstLoc = conn.prepareStatement(strSqlLoc); 
                pstLoc.setInt(1, codEmp); 
                pstLoc.setInt(2, codLoc); 
                pstLoc.setInt(3, codTipDoc); 
                pstLoc.setInt(4, codDoc); 
                rstLoc = pstLoc.executeQuery();                
                if (rstLoc.next())
                    codUsr = rstLoc.getInt("cousr");                
            }
        }catch (SQLException e){
            codUsr = -1;
            System.err.println(e);
        }catch (Exception e){
            codUsr = -1;
            System.err.println(e);
        }finally{
            try{
                if (rstLoc != null) 
                    rstLoc.close();
                rstLoc = null;
                if (pstLoc != null) 
                    pstLoc.close();
                pstLoc = null;   
            }catch (Throwable e){
                e.printStackTrace();
            }
        }    
        strSqlLoc=null;
        this.setCodUsrResCon(codUsr);
        return codUsr;
    }
    
    /**
     * Obtiene el codigo de usuario a quien se asigna la orden de conteo usando la funcion random.
     * Se toma el usuario de la tabla TBM_TRA y se asocia con el id_usuario de zafiro mediante el campo TX_USR. 
     * Esto se debe cambiar cuando en TBM_TRA se tenga creado un campo con el id_usuario de zafiro.
     */
    @Override
    public void setUsrResConOrdConInv(Connection conn){
        int codUsr = -1;
        String strSqlLoc;
        ResultSet rstLoc=null;
        PreparedStatement pstLoc=null;
        strSqlLoc=" select c.co_usr, c.tx_usr, random() "
                + " from "
                + " (select d.co_usr, upper(d.tx_usr) as tx_usr "
                + " from tbm_usr as d "
                + " where d.st_reg='A') as c, "
                + " (select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr "
                + " from tbm_tra as a "
                + " inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A') "
                + " where a.st_reg='A') as e "
                + " where c.tx_usr=e.tx_usr "
                + " and e.co_dep=? "
                + " order by random ";
        try{
            if (conn!= null){
                pstLoc = conn.prepareStatement(strSqlLoc); 
                pstLoc.setInt(1, this.intCodDep); 
                rstLoc = pstLoc.executeQuery();                
                if (rstLoc.next())
                    codUsr = rstLoc.getInt("co_usr");                
            }
        }catch (SQLException e){
            codUsr = -1;
            System.err.println(e);
        }catch (Exception e){
            codUsr = -1;
            System.err.println(e);
        }finally{
            try{
                if (rstLoc != null) 
                    rstLoc.close();
                rstLoc = null;
                if (pstLoc != null) 
                    pstLoc.close();
                pstLoc = null;   
            }catch (Throwable e){
                e.printStackTrace();
            }
        }    
        
        this.setCodUsrResCon(codUsr);
    }    
    
    @Override
    public int getMaxNumDocOrdConInv(Connection conn, int codEmp, int codLoc, int codTipDoc){
        int numDoc = -1;
        String strSqlLoc; 
        PreparedStatement pstLoc=null;
        ResultSet rstLoc=null;
        /*strSqlLoc = " select case when max(a1.ne_ultDoc) is null or max(a1.ne_ultDoc) = 0 then 0 else max(a1.ne_ultDoc) end as ne_ultDoc " +
                    " from tbm_cabTipDoc as a1 " +
                    " where a1.co_emp = ? " + 
                    " and a1.co_loc = ?  " +
                    " and a1.co_tipDoc = ? ";*/
        strSqlLoc = " select case when max(a1.ne_ultDoc) is null or max(a1.ne_ultDoc) = 0 then 0 else max(a1.ne_ultDoc) end as ne_ultDoc " +
                    " from tbm_cabTipDoc as a1 " +
                    " where a1.co_emp=? " +
                    " and a1.co_loc=? " +
                    " and a1.co_tipDoc=( select co_tipDoc from tbr_tipDocPrg where co_emp=a1.co_emp and co_loc=a1.co_loc and co_mnu=? and st_reg='S') ";
        try{
            if (conn!= null){
                pstLoc = conn.prepareStatement(strSqlLoc);
                pstLoc.setInt(1, codEmp); 
                pstLoc.setInt(2, codLoc); 
                pstLoc.setInt(3, this.intCodMnu); 
                rstLoc = pstLoc.executeQuery(); 
                if (rstLoc.next())
                    numDoc = rstLoc.getInt("ne_ultDoc");                
                
            }
        }catch (SQLException e){
            System.err.println(e);
            numDoc = -1;
        }catch (Exception e){
            System.err.println(e);
            numDoc = -1;
        }finally{
            try{
                if (rstLoc != null) 
                    rstLoc.close();
                rstLoc = null; 
                if (pstLoc != null) 
                    pstLoc.close();
                pstLoc = null; 
            }catch (Throwable e){
                e.printStackTrace();
            }
        }        
        strSqlLoc=null;
        return numDoc;
    }
    
    @Override
    public int getMaxCodDocConInv(Connection conn, int codEmp){
        int codDoc = -1;
        PreparedStatement pstLoc=null;
        ResultSet rstLoc=null;        
        String strSqlLoc;
        strSqlLoc = " select case when max(a1.co_reg) is null or max(a1.co_reg) = 0 then 1 else max(a1.co_reg) end as codoc " +
                    " from tbm_coninv as a1 " +
                    " where a1.co_emp = ? " ;
        try{
            if (conn!= null){
                pstLoc = conn.prepareStatement(strSqlLoc);
                pstLoc.setInt(1, codEmp);
                rstLoc = pstLoc.executeQuery();                
                if (rstLoc.next())
                    codDoc = rstLoc.getInt("codoc");                
                
            }
        }catch (SQLException e){
            System.err.println(e);
            codDoc = -1;
        }catch (Exception e){
            System.err.println(e);
            codDoc = -1;
        }finally{
            try{
                if (rstLoc != null) 
                    rstLoc.close();
                rstLoc = null;
                if (pstLoc != null) 
                    pstLoc.close();
                pstLoc = null;   
                
            }catch (Throwable e){
                e.printStackTrace();
            }                
        }      
        strSqlLoc=null;
        return codDoc;
    }
    
    
    public int getCodUsrResConInv(Connection conn, int codEmp, int codReg){
        int codUsr = -1;
        PreparedStatement pstLoc=null;
        ResultSet rstLoc=null;        
        String strSqlLoc;
        strSqlLoc = " select case when a1.co_usrrescon is null or a1.co_usrrescon = 0 then 1 else a1.co_usrrescon end as cousr " +
                    " from tbm_coninv as a1 " +
                    " where a1.co_emp = ? " +
                    " and a1.co_reg = ? " ;
        try{
            if (conn!= null){
                pstLoc = conn.prepareStatement(strSqlLoc);
                pstLoc.setInt(1, codEmp);
                pstLoc.setInt(2, codReg);
                rstLoc = pstLoc.executeQuery();                
                if (rstLoc.next())
                    codUsr = rstLoc.getInt("cousr");                
            }
        }catch (SQLException e){
            System.err.println(e);
            codUsr = -1;
        }catch (Exception e){
            System.err.println(e);
            codUsr = -1;
        }finally{
            try{
                if (rstLoc != null) 
                    rstLoc.close();
                rstLoc = null;
                if (pstLoc != null) 
                    pstLoc.close();
                pstLoc = null;   
                
            }catch (Throwable e){
                e.printStackTrace();
            }                
        }      
        strSqlLoc=null;
        return codUsr;
    }
    
    @Override
    public boolean insertarCabOrdConInv(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, int intCodUsrResCon, int intCodBod, String strTxtObs, String strTxtObs2, String strStReg, int intCodUsrIng, int intCodUsrMod, String strStRegRep) {
        boolean blnRes=false;
        //int intUltReg = 0;
        java.sql.Date sDate = null;
        strSQL= " insert into tbm_cabordconinv(co_emp, co_loc, co_tipdoc, co_doc, " +
                " fe_doc, ne_numdoc, co_usrrescon, co_bod, tx_obs1, tx_obs2, " +
                " st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep) " +
                " values ( ?, ?, ?, ?, current_date, ?, ?, ?, ?, ?, ?, current_timestamp, ?, ?, ?, ? ) ";
        try{
            if(conn!=null){
                pst = conn.prepareStatement(strSQL);
                pst.setInt(1, intCodEmp);
                pst.setInt(2, intCodLoc);
                pst.setInt(3, intCodTipDoc);
                pst.setLong(4, intCodDoc);
                //pst.setDate(0, fe_doc);
                pst.setInt(5, intNumDoc);
                pst.setInt(6, intCodUsrResCon);
                pst.setInt(7, intCodBod);
                pst.setString(8, strTxtObs);
                pst.setString(9, strTxtObs2);
                pst.setString(10, strStReg);
                //pst.setDate(0, fe_ing);
                pst.setDate(11, sDate);
                pst.setInt(12, intCodUsrIng);
                pst.setNull(13, Types.INTEGER); //pst.setInt(13, intCodUsrMod);
                pst.setString(14, strStRegRep);
                
                pst.executeUpdate();
                
                //strSQL=null;
                blnRes=true;
            }
        }
        catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }finally{
            try{
                if (pst!=null) 
                    pst.close();
                pst=null;
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }    
    
    private boolean insertarDetalle(Connection conn, int intCodEmpRel, int intCodLocRel, int intCodTipDocRel, int intCodDocRel, int intCodEmpGrp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBodRel){
        boolean blnRes=false;
        int intCoReg = 0, co_regConInv=0, co_usrResConInv, intCodItmGrp=0;
        strSQL= " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, b.co_itm, b.tx_codalt "  +
                " from tbm_cabguirem as a, tbm_detguirem as b " +
                " where a.co_emp=b.co_emp " +
                " and a.co_loc=b.co_loc " +
                " and a.co_tipdoc=b.co_tipdoc " +
                " and a.co_doc=b.co_doc " +
                " and a.co_emp=? " +
                " and a.co_loc=? " +
                " and a.co_tipdoc=? " +
                " and a.co_doc=? " +
                " and (abs(b.nd_can)- (abs(b.nd_cantotguisec) + abs(b.nd_cannunrec))) > 0 " +
                " and (b.nd_canfal > 0 or b.nd_candan > 0) " +
                " group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, b.co_itm, b.tx_codalt ";
        
        /*strSQL= "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, b.co_reg, b.co_itm, b.tx_codalt, b.tx_nomitm, b.tx_unimed, " +
                " case when b.nd_can is null then 0 else b.nd_can end as nd_can, " +
                " (abs(b.nd_can)- (abs(b.nd_cantotguisec) + abs(b.nd_cannunrec))) as nd_canpen, " +
                " case when b.nd_canfal is null then 0 else b.nd_canfal end as nd_canfal, " + 
                " case when b.nd_candan is null then 0 else b.nd_candan end as nd_candan, " +
                " b.tx_obsfaldan, a.ne_numorddes "  +
                " from tbm_cabguirem as a, tbm_detguirem as b " +
                " where a.co_emp=b.co_emp " +
                " and a.co_loc=b.co_loc " +
                " and a.co_tipdoc=b.co_tipdoc " +
                " and a.co_doc=b.co_doc " +
                " and a.co_emp=? " +
                " and a.co_loc=? " +
                " and a.co_tipdoc=? " +
                " and a.co_doc=? " +
                " order by b.co_reg ";*/
        
        try{
            if (conn!=null){
                
                co_regConInv=getMaxCodDocConInv(conn, intCodEmpGrp);
                co_usrResConInv=getCodUsrResConInv(conn, intCodEmpGrp, co_regConInv);
                pst = conn.prepareStatement(strSQL);
                pst.setInt(1, intCodEmpRel);
                pst.setInt(2, intCodLocRel);
                pst.setInt(3, intCodTipDocRel);
                pst.setLong(4, intCodDocRel);
                
                rst = pst.executeQuery();
                while (rst.next()){                    
                    //if (rst.getInt("nd_canfal") > 0 || rst.getInt("nd_candan") > 0  ){
                        intCoReg++;
                        intCodItmGrp=getCodItmGrp(conn, intCodEmpGrp, rst.getString("tx_codalt"));
                        insertarDetOrdConInv(conn, intCodEmpGrp, intCodLoc, intCodTipDoc, intCodDoc, intCoReg, intCodItmGrp, "I");    
                        //insertarDetOrdConInv(conn, intCodEmpGrp, intCodLoc, intCodTipDoc, intCodDoc, intCoReg, rst.getInt("co_itm"), "I");                            
                        co_regConInv++;
                        insertarTbmConInv(conn, intCodEmpGrp, co_regConInv, co_usrResConInv, intCodBodRel, intCodItmGrp, intCodLocRel, intCodTipDocRel, intCodDocRel, 0, 0, 0, 0, 0, "N", "", "I", 0);
                        //insertarTbmConInv(conn, intCodEmpGrp, co_regConInv, co_usrResConInv, intCodBodRel, rst.getInt("co_itm"), intCodLocRel, intCodTipDocRel, intCodDocRel, rst.getInt("co_reg"), 0, 0, 0, 0, "N", "", "I", 0);
                        blnRes=true;
                    //} 
                }                 
            }
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }
        //strSQL=null;        
        return blnRes;
    }

    
    @Override
    public boolean insertarDetOrdConInv(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCoReg, int intCodItm, String strRegRep) {
        boolean blnRes=false;
        PreparedStatement pstLoc=null;
        strSQL= " insert into tbm_detordconinv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, st_regrep) " +
                " values (?, ?, ?, ?, ?, ?, ?)";
        try{            
            if(conn!=null){
                pstLoc = conn.prepareStatement(strSQL);
                pstLoc.setInt(1, intCodEmp);
                pstLoc.setInt(2, intCodLoc);
                pstLoc.setInt(3, intCodTipDoc);
                pstLoc.setLong(4, intCodDoc);
                pstLoc.setInt(5, intCoReg);
                pstLoc.setInt(6, intCodItm);
                pstLoc.setString(7, strRegRep);

                pstLoc.executeUpdate();

                blnRes=true;
            }
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }finally{
            try{
                if (pstLoc!=null) 
                    pstLoc.close();
                pstLoc=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }    
    
    public boolean insertarTbmConInv(Connection conn, int intCodEmp, int intCoReg, int intUsrResCon, int intCodBod, int intCodItm, int intCodLocRel, int intCodTipDocRel, int intCodDocRel, int intCodRegRel, int intStkSis, int intCanIngBod, int intCanEgrBod, int intStkCon, String strConRea, String strObs1, String strRegRep, int intCanSob) {
        boolean blnRes=false;
        PreparedStatement pstLoc=null;
        java.sql.Date datFecSolCon=new java.sql.Date(new java.util.Date().getTime());
        java.sql.Date datFecReaCon=null;
        strSQL= " insert into tbm_coninv(co_emp, co_reg, co_usrrescon, co_bod, " +
                " co_itm, co_locrel, co_tipdocrel, co_docrel, co_regrel, " +
                " fe_solcon, fe_reacon, nd_stksis, nd_caningbod, nd_canegrbod, " +
                " nd_stkcon, st_conrea, tx_obs1, st_regrep, nd_cansob) " +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        
        try{
            if(conn!=null){
                pstLoc = conn.prepareStatement(strSQL);
                pstLoc.setInt(1, intCodEmp);
                pstLoc.setInt(2, intCoReg);
                pstLoc.setInt(3, intUsrResCon);
                pstLoc.setInt(4, intCodBod);
                pstLoc.setLong(5, intCodItm);
                pstLoc.setNull(6, Types.INTEGER);  //pstLoc.setInt(6, intCodLocRel); 
                pstLoc.setNull(7, Types.INTEGER);  //pstLoc.setInt(7, intCodTipDocRel);
                pstLoc.setNull(8, Types.INTEGER);  //pstLoc.setInt(8, intCodDocRel);
                pstLoc.setNull(9, Types.INTEGER);  //pstLoc.setInt(9, intCodRegRel);
                //pstLoc.setDate(10, datFecSolCon);
                pstLoc.setDate(10, datFecReaCon);
                pstLoc.setInt(11, intStkSis); //pstLoc.setNull(12, Types.INTEGER);  //
                pstLoc.setInt(12, intCanIngBod); //pstLoc.setNull(13, Types.INTEGER);  //
                pstLoc.setInt(13, intCanEgrBod); //pstLoc.setNull(14, Types.INTEGER);  //
                pstLoc.setInt(14, intStkCon); //pstLoc.setNull(15, Types.INTEGER);  //
                pstLoc.setString(15, strConRea); //pstLoc.setNull(16, Types.CHAR);  //
                pstLoc.setString(16, strObs1);
                pstLoc.setString(17, strRegRep);
                pstLoc.setNull(18, Types.INTEGER);  //pstLoc.setInt(19, intCanSob);

                pstLoc.executeUpdate();

            }
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }finally{
            try{
                if (pstLoc!=null) 
                    pstLoc.close();
                pstLoc=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }    
    
    public boolean insertarTbmConInv(Connection conn, int intCodEmp, int intCoReg, int intUsrResCon, int intCodBod, int intCodItm, int intCodLocRel, int intCodTipDocRel, int intCodDocRel, int intCodRegRel) {
        boolean blnRes=false;
        PreparedStatement pstLoc=null;
        java.sql.Date datFecReaCon=null;
        strSQL= " insert into tbm_coninv(co_emp, co_reg, co_usrrescon, co_bod, " +
                " co_itm, co_locrel, co_tipdocrel, co_docrel, co_regrel, " +
                " fe_solcon) " +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp )";
        try{
            if(conn!=null){
                pstLoc = conn.prepareStatement(strSQL);
                pstLoc.setInt(1, intCodEmp);
                pstLoc.setInt(2, intCoReg);
                pstLoc.setInt(3, intUsrResCon);
                pstLoc.setInt(4, intCodBod);
                pstLoc.setLong(5, intCodItm);
                pstLoc.setNull(6, Types.INTEGER);  //pstLoc.setInt(6, intCodLocRel); 
                pstLoc.setNull(7, Types.INTEGER);  //pstLoc.setInt(7, intCodTipDocRel);
                pstLoc.setNull(8, Types.INTEGER);  //pstLoc.setInt(8, intCodDocRel);
                pstLoc.setNull(9, Types.INTEGER);  //pstLoc.setInt(9, intCodRegRel);
                //pstLoc.setDate(10, datFecSolCon);
                pstLoc.setDate(10, datFecReaCon);

                pstLoc.executeUpdate();

                blnRes=true;
            }
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }finally{
            try{
                if (pstLoc!=null) 
                    pstLoc.close();
                pstLoc=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }    
    
    @Override
    public boolean insertarTbmConInv(Connection conn, int intCodEmp, int intCoReg, int intUsrResCon, int intCodBod, int intCodItm) {
        boolean blnRes=false;
        PreparedStatement pstLoc=null;
        strSQL= " insert into tbm_coninv(co_emp, co_reg, co_usrrescon, co_bod, " +
                " co_itm, fe_solcon ) " +
                " values (?, ?, ?, ?, ?, current_timestamp )";
        try{
            if(conn!=null){
                pstLoc = conn.prepareStatement(strSQL);
                pstLoc.setInt(1, intCodEmp);
                pstLoc.setInt(2, intCoReg);
                pstLoc.setInt(3, intUsrResCon);
                pstLoc.setInt(4, intCodBod);
                pstLoc.setLong(5, intCodItm);

                pstLoc.executeUpdate();

                blnRes=true;
            }
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }finally{
            try{
                if (pstLoc!=null) 
                    pstLoc.close();
                pstLoc=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }    
    
    @Override
    public boolean actualizarCabTipDoc(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc){
        boolean blnRes=false;
        strSQL=" update tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp = ? and co_loc = ? and co_tipDoc = ? ";
        PreparedStatement pstLoc=null;
        try{
            if(conn!=null){
                pstLoc = conn.prepareStatement(strSQL);
                pstLoc.setInt(1, intCodEmp);
                pstLoc.setInt(2, intCodLoc);
                pstLoc.setInt(3, intCodTipDoc);
                
                pstLoc.executeUpdate();
                
                blnRes = true;
            }
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(objJDialog, e);
        }finally{
            try{
                if (pstLoc!=null) 
                    pstLoc.close();
                pstLoc=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }    
    
    @Override
    public void setCodEmpGrp(int intCodEmpGrp){
        this.intCodEmpGrp = intCodEmpGrp;
    }
    
    /**
     * Obtiene el codigo de la empresa-grupo.
     */
    @Override
    public int getCodEmpGrp(){
        return this.intCodEmpGrp;
    }
    
    @Override
    public void setCodBodGrp(int intCodBodGrp){
        this.intCodBodGrp = intCodBodGrp;
    }
    
    /**
     * Obtiene el codigo de la bodega-grupo.
     */
    @Override
    public int getCodBodGrp(){
        return this.intCodBodGrp;
    }
    
    @Override
    public void setCodUsrResCon(int intCodUsrResCon){
        this.intCodUsrResCon = intCodUsrResCon;
    }
    
    /**
     * Obtiene el codigo de usuario responsable del conteo.
     */
    @Override
    public int getCodUsrResCon(){
        return this.intCodUsrResCon;
    }
    
    /**
     * Obtiene el codigo de tipo de documento para ordenes de conteo.
     */
    @Override
    public int getCodTipDoc(){
        return this.intCodTipDoc;
    }
    
    @Override
    public void setCodDep(int intCodDep){
        this.intCodDep = intCodDep;
    }
    
    @Override
    public int getCodDep(){
        return this.intCodDep;
    }
    
}

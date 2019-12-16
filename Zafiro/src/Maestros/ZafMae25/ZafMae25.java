/*
 * ZafMae01.java
 *
 * Created on December 6, 2006, 12:45 PM
 */

package Maestros.ZafMae25;
import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;

/**
 *
 * @author  ilino
 */
public class ZafMae25 extends javax.swing.JInternalFrame {
    private ZafParSis objZafParSis;
    private ZafThreadGUI objThrGUI;
    private ZafUtil objUti;
    
    private Connection conFun, conGrl, conLoc, conRem, conTmp, conUpdTbmInv, conEquInv, conUpdTbmEquInv;
    private Statement stmGrl, stmLoc, stmRem, stmTmp, stmTmpInv, stmTmpInvBod, stmUpdTbmInv, stmEquInv, stmUpdTbmEquInv;
    private ResultSet rstGrl, rstLoc, rstRem, rstTmp, rstTmpInv, rstTmpInvBod, rstUpdTbmInv, rstEquInv, rstUpdTbmEquInv;
    
    
    
//        
//    private final String strDrv="org.postgresql.Driver";    
//    private final String strConIni="jdbc:postgresql://127.0.0.1:5432/dbZafiro";
//    private final String strUsrIni="postgres";
//    private final String strPswIni="1234";
    

    private String strDrv="";
    private String strConIni="";
    private String strUsrIni="";
    private String strPswIni="";
    
    
    
    
    //PARA LAS CONEXIONES LOCALES SEGUN tbm_cfgbasdatrep
    private String strConLoc;
    private String strUsrLoc;
    private String strPswLoc;
    
    //PARA LAS CONEXIONES TEMPORALES
    private String strConTmp;
    private String strUsrTmp;
    private String strPswTmp;

    private int intCodEmpLoc;
    
    private String strSQL="", strSQLTmp="", strSQLTmpInv, strSQLTmpInvBod;
    private String strSQLLoc="";
    private ArrayList arlDat, arlReg;
    
    private ArrayList arlDatEquInv, arlRegEquInv;
    
    
    private int intTim;
    private ArrayList arlDatInv, arlRegInv;
    private ArrayList arlDatInvBodIns, arlDatInvBodMod, arlRegInvBodIns, arlRegInvBodMod;
    
    //PARA LOS DATOS DE tbm_equinv
    private int INT_COL_COD_ITM_MAE=0;
    private int INT_COL_COD_EMP=1;
    private int INT_COL_COD_IMT=2;
    private int INT_COL_EST_REG_REP=3;
        
    
    //PARA LOS DATOS DE tbm_cfgbasdatrep
    private int INT_COL_BAS_COD_REG=0;
    private int INT_COL_BAS_COD_GRP=1;
    private int INT_COL_BAS_REG_ORI=2;
    private int INT_COL_BAS_REG_DES=3;
    private int INT_COL_BAS_FREC_ACT=4;
    private int INT_COL_BAS_EST_REG=5;

    //PARA LOS DATOS DE TBM_INV
    final int INT_INV_COD_EMP=0;
    final int INT_INV_COD_ITM=1;
    final int INT_INV_COD_ALT=2;
    final int INT_INV_NOM_ITM=3;
    final int INT_INV_COD_CLA_UNO=4;
    final int INT_INV_COD_CLA_DOS=5;    
    final int INT_INV_COD_CLA_TRE=6;
    final int INT_INV_COD_UNI=7;
    final int INT_INV_ITM_UNI=8;
    final int INT_INV_EST_SER=9;
    final int INT_INV_COS_UNI=10;
    final int INT_INV_COS_ULT=11;
    final int INT_INV_PRE_VTA_UNO=12;
    final int INT_INV_PRE_VTA_DOS=13;
    final int INT_INV_PRE_VTA_TRE=14;
    final int INT_INV_STK_ACT=15;
    final int INT_INV_STK_MIN=16;
    final int INT_INV_STK_MAX=17;
    final int INT_INV_IVA_COM=18;
    final int INT_INV_IVA_VEN=19;
    final int INT_INV_OBS_UNO=20;
    final int INT_INV_OBS_DOS=21;
    final int INT_INV_EST_REG=22;
    final int INT_INV_FEC_ING=23;
    final int INT_INV_FEC_MOD=24;
    final int INT_INV_COD_USR_ING=25;
    final int INT_INV_COD_USR_MOD=26;
    final int INT_INV_COD_ALT_DOS=27;
    final int INT_INV_REG_REP=28;
    
    final int INT_COD_GRP_CNF=2;
    
    private boolean blnHil; 
    
    //PARA LOS DATOS DE TBM_INVBOD -- ESTO ME SIRVE SI TENGO DOS ARRAYS LIST... UNO PARA TBM_INV Y OTRO PARA TBM_INVBOD
    private int INT_INVBOD_COD_EMP=0;
    private int INT_INVBOD_COD_BOD=1;
    private int INT_INVBOD_COD_ITM=2;
    private int INT_INVBOD_STK_ACT=3;
    private int INT_INVBOD_STK_MIN=4;
    private int INT_INVBOD_STK_MAX=5;
        
    /** Creates new form ZafMae01 */
    public ZafMae25(ZafParSis obj) {
        try{
            initComponents();
            this.objZafParSis=obj;
            objZafParSis=(ZafParSis)obj.clone();
            arlDat=new ArrayList();
            arlDatInv=new ArrayList();
            arlDatInvBodIns=new ArrayList();
            arlDatInvBodMod=new ArrayList();
            arlDatEquInv=new ArrayList();
            objUti=new ZafUtil();
            
            strDrv=objZafParSis.getDriverConexionCentral();
            strConIni=objZafParSis.getStringConexionCentral();
            strUsrIni=objZafParSis.getUsuarioConexionCentral();
            strPswIni=objZafParSis.getClaveConexionCentral();
                        
//            if (objThrGUI==null){
//                objThrGUI=new ZafThreadGUI();
//                objThrGUI.start();
//            }            
            
            
            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panFrm = new javax.swing.JPanel();
        butDet = new javax.swing.JButton();
        butIni = new javax.swing.JButton();
        butItm = new javax.swing.JButton();
        butRep = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panFrm.setLayout(null);

        butDet.setText("Detener");
        butDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDetActionPerformed(evt);
            }
        });

        panFrm.add(butDet);
        butDet.setBounds(20, 40, 80, 23);

        butIni.setText("Iniciar");
        butIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butIniActionPerformed(evt);
            }
        });

        panFrm.add(butIni);
        butIni.setBounds(20, 10, 80, 23);

        butItm.setText("Crear Items");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });

        panFrm.add(butItm);
        butItm.setBounds(20, 80, 90, 23);

        butRep.setText("Proceso Replicaci\u00f3n");
        butRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRepActionPerformed(evt);
            }
        });

        panFrm.add(butRep);
        butRep.setBounds(20, 130, 170, 23);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-349)/2, (screenSize.height-244)/2, 349, 244);
    }//GEN-END:initComponents

    private void butRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRepActionPerformed
        // TODO add your handling code here:
        Maestros.ZafMae26.ZafMae26 objMae26=new Maestros.ZafMae26.ZafMae26(objZafParSis);
        this.getParent().add(objMae26,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objMae26.show();        
    }//GEN-LAST:event_butRepActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        Maestros.ZafMae27.ZafMae27 objMae27=new Maestros.ZafMae27.ZafMae27(objZafParSis);
        this.getParent().add(objMae27,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objMae27.show();
        
    }//GEN-LAST:event_butItmActionPerformed

    private void butIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butIniActionPerformed
        // TODO add your handling code here:
            if (objThrGUI==null){
                blnHil=true;
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
                System.out.println("INICIAR");
            }        
    }//GEN-LAST:event_butIniActionPerformed

    private void butDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDetActionPerformed
        // TODO add your handling code here:
                blnHil=false;
                System.out.println("DETENER");
                if(objThrGUI!=null){
                    objThrGUI.stop();
                    objThrGUI=null;
                }
                    
    }//GEN-LAST:event_butDetActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butDet;
    private javax.swing.JButton butIni;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butRep;
    private javax.swing.JPanel panFrm;
    // End of variables declaration//GEN-END:variables

    
     private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            try{
                cargarDrv();                
                while(blnHil==true){
                    objThrGUI.sleep(obtieneFrecuencActivid());
                
                
                    if (cargarReg()){
                        try{
                            conGrl=conexDB(strConIni, strUsrIni, strPswIni);
                            conUpdTbmInv=conexDB(strConLoc, strUsrLoc, strPswLoc);
                                if(actualizaTbmInvLocal()){
                                    if(actualizaTbmEquInvLocal()){
                                        conUpdTbmInv.commit();
                                        strConLoc="";
                                        strUsrLoc="";
                                        strPswLoc="";
                                    }
                                    else
                                        conUpdTbmInv.rollback();
                                }
                                else
                                    conUpdTbmInv.rollback();
                            conGrl.close();                        conGrl=null;
                            conUpdTbmInv.close();                  conUpdTbmInv=null;
                        }
                        catch (java.sql.SQLException e)
                        {
                            System.err.println("Excepcion: "+e.toString());
                        }
                        catch (Exception e)
                        {
                            System.err.println("Excepcion: "+e.toString());
                        }      

                    }
                objThrGUI=null;                
                }
                System.out.println("EL HILO MURIO!!!!!");
                
            }
            catch (InterruptedException excp){
                System.err.println("Excepcion: "+excp.toString());
                
            }
            

        }
    }

    private boolean cargarReg()
    {
        boolean blnRes=true;
        arlDat.clear();
        arlDatInv.clear();
        arlDatInvBodIns.clear();
        arlDatInvBodMod.clear();
        arlDatEquInv.clear();
                
        try{
            conGrl=conexDB(strConIni, strUsrIni, strPswIni);
            if(conGrl!=null){
                conGrl.setAutoCommit(false);
                if(modificaTbmBasDat2Inicial()){
                    conGrl.commit();
                }
                else
                    conGrl.rollback();
                
                stmGrl=conGrl.createStatement();
                strSQL="";
                strSQL+="select ";
                strSQL+=" co_reg, co_grp, co_regorg, co_regdes, tx_freact, st_reg";
                strSQL+=" from tbm_cfgbasdatrep as a1";
                strSQL+=" where a1.co_grp=" + INT_COD_GRP_CNF + " and a1.st_reg='P'";
                System.out.println("tbm_cfgbasdatrep: " + strSQL);
                rstGrl=stmGrl.executeQuery(strSQL);

                for(int i=0;rstGrl.next();i++){
                    arlReg=new ArrayList();
                    arlReg.add(INT_COL_BAS_COD_REG, rstGrl.getString("co_reg"));                    
                    arlReg.add(INT_COL_BAS_COD_GRP, rstGrl.getString("co_grp"));
                    arlReg.add(INT_COL_BAS_REG_ORI, rstGrl.getString("co_regorg"));
                    arlReg.add(INT_COL_BAS_REG_DES, rstGrl.getString("co_regdes"));
                    arlReg.add(INT_COL_BAS_FREC_ACT, rstGrl.getString("tx_freact"));
                    arlReg.add(INT_COL_BAS_EST_REG, rstGrl.getString("st_reg"));
                    arlDat.add(arlReg);
                }
                System.out.println("El arlDat: "+arlDat.toString());
                
            //PARA LEER TBM_BASDAT    
                for (int j=0;j<arlDat.size();j++){
                                        
                    stmGrl=conGrl.createStatement();
                    strSQL="";
                    strSQL+="select ";
                    strSQL+=" co_reg, tx_drvcon, tx_strCon, tx_usrcon, tx_clacon, co_emp, tx_nom";
                    strSQL+=" from tbm_basdat as a2";
                    strSQL+=" where a2.co_reg=" + objUti.getStringValueAt(arlDat, j, INT_COL_BAS_REG_ORI) + "";
                    strSQL+=" and st_reg='A'";
                    System.out.println("TBM_BASDAT: " + strSQL);
                    rstGrl=stmGrl.executeQuery(strSQL);
                                        
                    while (rstGrl.next()){
                        intCodEmpLoc=rstGrl.getInt("co_emp");
                        strConLoc=rstGrl.getString("tx_strCon");
                        strUsrLoc=rstGrl.getString("tx_usrcon");
                        strPswLoc=rstGrl.getString("tx_clacon");
                        conLoc=conexDB(strConLoc, strUsrLoc, strPswLoc);
                        conEquInv=conexDB(strConLoc, strUsrLoc, strPswLoc);
                                                
                        stmLoc=conLoc.createStatement();
                        strSQLLoc="";
                        strSQLLoc+="select a1.co_emp as codEmpInv, a1.co_itm as codItmInv, a1.tx_codalt,";
                        strSQLLoc+=" a1.tx_nomitm, a1.co_cla1, a1.co_cla2, a1.co_cla3, a1.co_uni, a1.nd_itmuni,";
                        strSQLLoc+=" a1.st_ser, a1.nd_cosuni, a1.nd_cosult, a1.nd_prevta1, a1.nd_prevta2, a1.nd_prevta3,";
                        strSQLLoc+=" a1.nd_stkact as stkInv, a1.nd_stkmin as stkMinInv, a1.nd_stkmax as stkMaxInv,";
                        strSQLLoc+=" a1.st_ivacom, a1.st_ivaven, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultmod,";
                        strSQLLoc+=" a1.co_usring, a1.co_usrmod, a1.tx_codalt2, a1.st_regrep";
                        strSQLLoc+=" from tbm_inv as a1";
                        //strSQLLoc+=" where a1.co_emp=" + rstGrl.getString("co_emp") + "";
                        strSQLLoc+=" where a1.st_regrep in ('I', 'M')";
                        strSQLLoc+=" order by a1.co_emp, a1.co_itm";
                        System.out.println("TBM_INV: " + strSQLLoc);
                        rstLoc=stmLoc.executeQuery(strSQLLoc);
//                        System.out.println("STRING DE CONEX EN TBM_INV: "+strConLoc);
                        
                        stmEquInv=conEquInv.createStatement();
                        strSQLLoc="";
                        strSQLLoc+="select a3.co_itmmae, a3.co_emp, a3.co_itm, a3.st_regrep";
                        strSQLLoc+=" from tbm_equinv as a3";
                        strSQLLoc+=" where a3.st_regrep='I'";
                        strSQLLoc+=" order by a3.co_emp, a3.co_itmmae, a3.co_itm";
                        System.out.println("EN TBM_EQUINV: "+strSQLLoc);
                        rstEquInv=stmEquInv.executeQuery(strSQLLoc);
                        
                        
                        

                    }//fin del while de rstGrl
                    //la conexion no la cierro porq la necesito mas abajo
                    stmGrl.close();                    stmGrl=null;
                    rstGrl.close();                    rstGrl=null;
                        
                    while(rstLoc.next()){
                        arlRegInv=new ArrayList();
                        //PARA LOS DATOS DE TBM_INV
                        arlRegInv.add(INT_INV_COD_EMP, rstLoc.getString("codEmpInv"));
                        arlRegInv.add(INT_INV_COD_ITM, rstLoc.getString("codItmInv"));
                        arlRegInv.add(INT_INV_COD_ALT, rstLoc.getString("tx_codalt"));
                        arlRegInv.add(INT_INV_NOM_ITM, rstLoc.getString("tx_nomitm"));
                        arlRegInv.add(INT_INV_COD_CLA_UNO, rstLoc.getString("co_cla1"));
                        arlRegInv.add(INT_INV_COD_CLA_DOS, rstLoc.getString("co_cla2"));
                        arlRegInv.add(INT_INV_COD_CLA_TRE, rstLoc.getString("co_cla3"));
                        arlRegInv.add(INT_INV_COD_UNI, rstLoc.getString("co_uni"));
                        arlRegInv.add(INT_INV_ITM_UNI, rstLoc.getString("nd_itmuni"));
                        arlRegInv.add(INT_INV_EST_SER, rstLoc.getString("st_ser"));
                        arlRegInv.add(INT_INV_COS_UNI, rstLoc.getString("nd_cosuni"));
                        arlRegInv.add(INT_INV_COS_ULT, rstLoc.getString("nd_cosult"));
                        arlRegInv.add(INT_INV_PRE_VTA_UNO, rstLoc.getString("nd_prevta1"));
                        arlRegInv.add(INT_INV_PRE_VTA_DOS, rstLoc.getString("nd_prevta2"));
                        arlRegInv.add(INT_INV_PRE_VTA_TRE, rstLoc.getString("nd_prevta3"));
                        arlRegInv.add(INT_INV_STK_ACT, rstLoc.getString("stkInv"));
                        arlRegInv.add(INT_INV_STK_MIN, rstLoc.getString("stkMinInv"));
                        arlRegInv.add(INT_INV_STK_MAX, rstLoc.getString("stkMaxInv"));
                        arlRegInv.add(INT_INV_IVA_COM, rstLoc.getString("st_ivacom"));
                        arlRegInv.add(INT_INV_IVA_VEN, rstLoc.getString("st_ivaven"));
                        arlRegInv.add(INT_INV_OBS_UNO, rstLoc.getString("tx_obs1"));
                        arlRegInv.add(INT_INV_OBS_DOS, rstLoc.getString("tx_obs2"));
                        arlRegInv.add(INT_INV_EST_REG, rstLoc.getString("st_reg"));
                        arlRegInv.add(INT_INV_FEC_ING, rstLoc.getString("fe_ing"));
                        arlRegInv.add(INT_INV_FEC_MOD, rstLoc.getString("fe_ultmod"));
                        arlRegInv.add(INT_INV_COD_USR_ING, rstLoc.getString("co_usring"));
                        arlRegInv.add(INT_INV_COD_USR_MOD, rstLoc.getString("co_usrmod"));
                        arlRegInv.add(INT_INV_COD_ALT_DOS, rstLoc.getString("tx_codalt2"));
                        arlRegInv.add(INT_INV_REG_REP, rstLoc.getString("st_regrep"));
                        arlDatInv.add(arlRegInv);
                    }
                    stmLoc.close();                        stmLoc=null;
                    rstLoc.close();                        rstLoc=null;
                        
                    System.out.println("ARRAY DE INVENTARIO: "+arlDatInv.toString());   

                    while(rstEquInv.next()){
                        arlRegEquInv=new ArrayList();
                        //PARA LOS DATOS DE TBM_INV
                        arlRegEquInv.add(INT_COL_COD_ITM_MAE, rstEquInv.getString("co_itmmae"));
                        arlRegEquInv.add(INT_COL_COD_EMP, rstEquInv.getString("co_emp"));
                        arlRegEquInv.add(INT_COL_COD_IMT, rstEquInv.getString("co_itm"));
                        arlRegEquInv.add(INT_COL_EST_REG_REP, rstEquInv.getString("st_regrep"));
                        arlDatEquInv.add(arlRegEquInv);
                        //arlRegEquInv.clear();                        
                    }
                    stmEquInv.close();                        stmEquInv=null;
                    rstEquInv.close();                        rstEquInv=null;
                    
                     System.out.println("ARRAY DE EQUIVALENCIAS: "+arlDatEquInv.toString());   
                     

                    for (int k=0;k<arlDatInv.size();k++){
                        if(objUti.getStringValueAt(arlDatInv, k, INT_INV_REG_REP).toString().equals("I")){
                            stmLoc=conLoc.createStatement();
                            strSQLLoc="";
                            strSQLLoc+="select";
                            strSQLLoc+=" a2.co_emp as codEmpInvBod,";
                            strSQLLoc+=" a2.co_bod as codBod, a2.co_itm as codItmInvBod, a2.nd_stkact as stkInvBod,";
                            strSQLLoc+=" a2.nd_stkmin as stkMinInvBod, a2.nd_stkmax as stkMaxInvBod";
                            strSQLLoc+=" from tbm_invbod as a2";
                            strSQLLoc+=" where a2.co_emp=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_EMP) + "";
                            strSQLLoc+=" and a2.co_itm=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_ITM) + "";
                            strSQLLoc+=" order by a2.co_emp, a2.co_itm";
                            System.out.println("TBM_INVBOD: " + strSQLLoc);
                            rstLoc=stmLoc.executeQuery(strSQLLoc);
//                            System.out.println("STRING DE CONEX EN TBM_INVBOD: "+strConLoc);

                            while(rstLoc.next()){
                                //PARA LOS DATOS DE TBM_INVBOD
                                arlRegInvBodIns=new ArrayList();
                                arlRegInvBodIns.add(INT_INVBOD_COD_EMP, rstLoc.getString("codEmpInvBod"));
                                arlRegInvBodIns.add(INT_INVBOD_COD_BOD, rstLoc.getString("codBod"));
                                arlRegInvBodIns.add(INT_INVBOD_COD_ITM, rstLoc.getString("codItmInvBod"));
                                arlRegInvBodIns.add(INT_INVBOD_STK_ACT, rstLoc.getString("stkInvBod"));
                                arlRegInvBodIns.add(INT_INVBOD_STK_MIN, rstLoc.getString("stkMinInvBod"));
                                arlRegInvBodIns.add(INT_INVBOD_STK_MAX, rstLoc.getString("stkMaxInvBod"));
                                arlDatInvBodIns.add(arlRegInvBodIns);                            
                            }                            
                        }
                        System.out.println("ARRAY DE INVENTARIO BODEGA INSERTAR: "+arlDatInvBodIns.toString());   
                        if(objUti.getStringValueAt(arlDatInv, k, INT_INV_REG_REP).toString().equals("M")){
                        
                            stmLoc=conLoc.createStatement();
                            strSQLLoc="";
                            strSQLLoc+="select";
                            strSQLLoc+=" a2.co_emp as codEmpInvBod,";
                            strSQLLoc+=" a2.co_bod as codBod, a2.co_itm as codItmInvBod, a2.nd_stkact as stkInvBod,";
                            strSQLLoc+=" a2.nd_stkmin as stkMinInvBod, a2.nd_stkmax as stkMaxInvBod";
                            strSQLLoc+=" from tbm_invbod as a2";
                            strSQLLoc+=" where a2.co_emp=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_EMP) + "";
                            strSQLLoc+=" and a2.co_itm=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_ITM) + "";
                            strSQLLoc+=" order by a2.co_emp, a2.co_itm";
                            System.out.println("TBM_INVBOD: " + strSQLLoc);
                            rstLoc=stmLoc.executeQuery(strSQLLoc);
//                            System.out.println("STRING DE CONEX EN TBM_INVBOD: "+strConLoc);

                            while(rstLoc.next()){
                                //PARA LOS DATOS DE TBM_INVBOD
                                arlRegInvBodMod=new ArrayList();
                                arlRegInvBodMod.add(INT_INVBOD_COD_EMP, rstLoc.getString("codEmpInvBod"));
                                arlRegInvBodMod.add(INT_INVBOD_COD_BOD, rstLoc.getString("codBod"));
                                arlRegInvBodMod.add(INT_INVBOD_COD_ITM, rstLoc.getString("codItmInvBod"));
                                arlRegInvBodMod.add(INT_INVBOD_STK_ACT, rstLoc.getString("stkInvBod"));
                                arlRegInvBodMod.add(INT_INVBOD_STK_MIN, rstLoc.getString("stkMinInvBod"));
                                arlRegInvBodMod.add(INT_INVBOD_STK_MAX, rstLoc.getString("stkMaxInvBod"));
                                arlDatInvBodMod.add(arlRegInvBodMod);                            
                            }                            
                        }
                    }

                     
                    stmTmp=conGrl.createStatement();
                    strSQLTmp="";
                    strSQLTmp+="select ";
                    strSQLTmp+=" co_reg, tx_drvcon, tx_strCon, tx_usrcon, tx_clacon, co_emp, tx_nom";
                    strSQLTmp+=" from tbm_basdat as a2";
                    strSQLTmp+=" where a2.co_reg=" + objUti.getStringValueAt(arlDat, j, INT_COL_BAS_REG_DES) + "";
                    strSQLTmp+=" and st_reg='A'";
                    System.out.println("TBM_BASDAT -  REMOTO: " + strSQLTmp);
                    rstTmp=stmTmp.executeQuery(strSQLTmp);                    
                    while(rstTmp.next()){
                        strConTmp=rstTmp.getString("tx_strCon");
                        strUsrTmp=rstTmp.getString("tx_usrcon");
                        strPswTmp=rstTmp.getString("tx_clacon");                        
                    }                         
                     
                     
                    System.out.println("ARRAY DE INVENTARIO BODEGA MODIFICADO: "+arlDatInvBodMod.toString());    
                                         
                    if(insertarReg(strConTmp, strUsrTmp, strPswTmp, arlDatInv, arlDatInvBodIns, arlDatInvBodMod, arlDat, j, arlDatEquInv)){
                        
                    }
                                                                             
                 conLoc.close();                 conLoc=null;
                 conEquInv.close();              conEquInv=null;
                 
                }//fin de arlDat                             
            conGrl.close();
            conGrl=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }      
        return blnRes;
    }          

    private boolean insertarReg(String ConTmp, String UsrTmp, String PswTmp, ArrayList datInv, ArrayList datInvBodIns, ArrayList datInvBodMod, ArrayList dat, int j, ArrayList datEquInv){
        boolean blnRes=false;
        try{
            //CONEXION REMOTA
            conTmp=conexDB(ConTmp, UsrTmp, PswTmp);
//            conTmp.setAutoCommit(false);
            if (conTmp!=null){
                conTmp.setAutoCommit(false);
                
                if (insertaTbmInv(datInv)){
                    if (insertaTbmInvBob(datInvBodIns)){
                        if (insertaTbmEquInv(datEquInv)){
                            if (modificaTbmInvBob(datInvBodMod)){
                                if (modificaTbmInv(datInv)){
                                        conRem=conexDB(strConIni, strUsrIni, strPswIni);
                                        conRem.setAutoCommit(false);
                                            if(modificaEstRepTbmInvRem(datInv)){
                                                if(modificaEstRepTbmEquInvRem(datEquInv)){
                                                    
                                                    if(modificaTbmBasDat2(dat, j)){
                                                        System.out.println("HIZO TODO DENTRO DE INSERTARREG");
                                                        conTmp.commit();
                                                        conRem.commit();
                                                        conRem.close();
                                                        conRem=null;
                                                        System.out.println("SIPIIIIIIIIII!!!!!!!!");
                                                    }
                                                    else{
                                                        conTmp.rollback();
                                                        conRem.rollback();
                                                        System.out.println("NOPO!!!!!!!!");
                                                    }
                                                }
                                                else{
                                                    conTmp.rollback();
                                                    conRem.rollback();
                                                }
                                            }
                                            else{
                                                conTmp.rollback();
                                                conRem.rollback();
                                            }
                                }
                                else{
                                    conTmp.rollback();
                                }
                            }
                            else{
                                conTmp.rollback();
                            }                            
                            
                        }
                        else{
                            conTmp.rollback();
                        }
                    }
                    else{
                        conTmp.rollback();
                    }
                }
                else{
                    conTmp.rollback();
                }
            conTmp.close();
            }

            conTmp=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        

    private boolean insertaTbmInv(ArrayList arlDatInv){
        boolean blnRes=true;        
        try{
            if (conTmp!=null){
                for (int k=0;k<arlDatInv.size();k++){
                    if(objUti.getStringValueAt(arlDatInv, k, INT_INV_REG_REP).toString().equals("I")){
                        stmTmpInv=conTmp.createStatement();

                        //Armar la sentencia SQL.
                        strSQLTmpInv="";
                        strSQLTmpInv+="insert into tbm_inv(";
                        strSQLTmpInv+=" co_emp, co_itm, tx_codalt, tx_nomitm, co_cla1, co_cla2, co_cla3,";
                        strSQLTmpInv+=" co_uni, nd_itmuni, st_ser, nd_cosuni, nd_cosult, nd_prevta1,";
                        strSQLTmpInv+=" nd_prevta2, nd_prevta3, nd_stkact, nd_stkmin, nd_stkmax, st_ivacom,";
                        strSQLTmpInv+=" st_ivaven, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring,";
                        strSQLTmpInv+=" co_usrmod, tx_codalt2, st_regrep";
                        strSQLTmpInv+=")";
                        strSQLTmpInv+=" values(";
                        strSQLTmpInv+="" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_EMP) + ",";
                        strSQLTmpInv+="" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_ITM) + ",";
                        strSQLTmpInv+="'" + objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_ALT) + "',";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_NOM_ITM),1) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_CLA_UNO),0) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_CLA_DOS),0) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_CLA_TRE),0) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_UNI),0) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_ITM_UNI) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_EST_SER)) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_COS_UNI) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_COS_ULT) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_PRE_VTA_UNO) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_PRE_VTA_DOS) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_PRE_VTA_TRE) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_STK_ACT) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_STK_MIN) + ",";
                        strSQLTmpInv+="" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_STK_MAX) + ",";
                        strSQLTmpInv+="'" + objUti.getStringValueAt(arlDatInv, k, INT_INV_IVA_COM) + "',";
                        strSQLTmpInv+="'" + objUti.getStringValueAt(arlDatInv, k, INT_INV_IVA_VEN) + "',";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_OBS_UNO)) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_OBS_DOS)) + ",";
                        strSQLTmpInv+="'" + objUti.getStringValueAt(arlDatInv, k, INT_INV_EST_REG) + "',";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_FEC_ING)) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_FEC_MOD)) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_USR_ING),0) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_USR_MOD),0) + ",";
                        strSQLTmpInv+="" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_ALT_DOS)) + ",";
                        strSQLTmpInv+="'I'" ;
                        strSQLTmpInv+=")";
//                        System.out.println("INSERTA TBM_INV: "+strSQLTmpInv);
                        stmTmpInv.executeUpdate(strSQLTmpInv);
                        stmTmpInv.close();
                        stmTmpInv=null;                    
                    }
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean modificaTbmInv(ArrayList arlDatInv){
        boolean blnRes=true;
        try
        {
            if (conTmp!=null){
                for (int k=0;k<arlDatInv.size();k++){
                    if(objUti.getStringValueAt(arlDatInv, k, INT_INV_REG_REP).toString().equals("M")){
                        stmTmpInv=conTmp.createStatement();
                        //Armar la sentencia SQL.
                        strSQLTmpInv="";
                        strSQLTmpInv+="update tbm_inv";
                        strSQLTmpInv+=" set co_emp=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_EMP) + "";
                        strSQLTmpInv+=" , co_itm=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_ITM) + "";
                        strSQLTmpInv+=" , tx_codalt=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_ALT)) + "";
                        strSQLTmpInv+=" , tx_nomitm=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_NOM_ITM)) + "";
                        strSQLTmpInv+=" , co_cla1=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_CLA_UNO),0) + "";
                        strSQLTmpInv+=" , co_cla2=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_CLA_DOS),0) + "";
                        strSQLTmpInv+=" , co_cla3=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_CLA_TRE),0) + "";
                        strSQLTmpInv+=" , co_uni=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_UNI) + "";
                        strSQLTmpInv+=" , nd_itmuni=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_ITM_UNI) + "";
                        strSQLTmpInv+=" , st_ser=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_EST_SER)) + "";
                        strSQLTmpInv+=" , nd_cosuni=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_COS_UNI) + "";
                        strSQLTmpInv+=" , nd_cosult=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_COS_ULT) + "";
                        strSQLTmpInv+=" , nd_prevta1=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_PRE_VTA_UNO) + "";
                        strSQLTmpInv+=" , nd_prevta2=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_PRE_VTA_DOS) + "";
                        strSQLTmpInv+=" , nd_prevta3=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_PRE_VTA_TRE) + "";
                        strSQLTmpInv+=" , nd_stkact=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_STK_ACT) + "";
                        strSQLTmpInv+=" , nd_stkmin=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_STK_MIN) + "";
                        strSQLTmpInv+=" , nd_stkmax=" + objUti.getDoubleValueAt(arlDatInv, k, INT_INV_STK_MAX) + "";
                        strSQLTmpInv+=" ,st_ivacom=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_IVA_COM)) + "";
                        strSQLTmpInv+=" , st_ivaven=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_IVA_VEN)) + "";
                        strSQLTmpInv+=" , tx_obs1=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_OBS_UNO)) + "";
                        strSQLTmpInv+=" , tx_obs2=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_OBS_DOS)) + "";
                        strSQLTmpInv+=" , st_reg=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_EST_REG)) + "";
                        strSQLTmpInv+=" , fe_ing='" + objUti.getStringValueAt(arlDatInv, k, INT_INV_FEC_ING) + "'";
                        strSQLTmpInv+=" , fe_ultmod='" + objUti.getStringValueAt(arlDatInv, k, INT_INV_FEC_MOD) + "'";
                        strSQLTmpInv+=" , co_usring=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_USR_ING),0) + "";
                        strSQLTmpInv+=" , co_usrmod=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_USR_MOD),0) + "";
                        strSQLTmpInv+=" , tx_codalt2=" + objUti.codificar(objUti.getStringValueAt(arlDatInv, k, INT_INV_COD_ALT_DOS)) + "";
                        strSQLTmpInv+=" , st_regrep= 'M'" ;
                        strSQLTmpInv+=" where co_emp=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_EMP) + "";
                        strSQLTmpInv+=" and co_itm=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_ITM) + "";
                        System.out.println("modificaTbmInv: " + strSQLTmpInv);
                        stmTmpInv.executeUpdate(strSQLTmpInv);
                        stmTmpInv.close();
                        stmTmpInv=null;                    
                    }
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    private boolean insertaTbmInvBob(ArrayList arlDatInvBod){
        boolean blnRes=true;        
        try{
            if (conTmp!=null){
                for (int k=0;k<arlDatInvBod.size();k++){
                        stmTmpInvBod=conTmp.createStatement();
                        strSQLTmpInvBod="";
                        strSQLTmpInvBod+="insert into tbm_invbod(";
                        strSQLTmpInvBod+=" co_emp, co_bod, co_itm, nd_stkact, nd_stkmin, nd_stkmax";
                        strSQLTmpInvBod+=")";
                        strSQLTmpInvBod+=" values(";
                        strSQLTmpInvBod+="" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_EMP) + ",";
                        strSQLTmpInvBod+="" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_BOD) + ",";
                        strSQLTmpInvBod+="" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_ITM) + ",";
                        strSQLTmpInvBod+="" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_STK_ACT) + ",";
                        strSQLTmpInvBod+="" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_STK_MIN) + ",";
                        strSQLTmpInvBod+="" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_STK_MAX) + "";
                        strSQLTmpInvBod+=")";
//                        System.out.println("INSERTA EN TBM_INVOB:"+strSQLTmpInvBod);
                        stmTmpInvBod.executeUpdate(strSQLTmpInvBod);
                        stmTmpInvBod.close();
                        stmTmpInvBod=null;                    
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean modificaTbmInvBob(ArrayList arlDatInvBod){
        boolean blnRes=true;
        try
        {
            if (conTmp!=null){
                for (int k=0;k<arlDatInvBod.size();k++){
//                    System.out.println("k: "+k);
//                    System.out.println("VECTOR: "+arlDatInvBod.toString());
//                    System.out.println("STOCK:  "+objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_STK_ACT));
                    stmTmpInv=conTmp.createStatement();
                    //Armar la sentencia SQL.
                    strSQLTmpInvBod="";
                    strSQLTmpInvBod+="update tbm_invbod";
                    strSQLTmpInvBod+=" set co_emp=" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_EMP) + "";
                    strSQLTmpInvBod+=", co_bod=" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_BOD) + "";
                    strSQLTmpInvBod+=", co_itm=" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_ITM) + "";
                    strSQLTmpInvBod+=", nd_stkact=" + objUti.getDoubleValueAt(arlDatInvBod, k, INT_INVBOD_STK_ACT) + "";
                    strSQLTmpInvBod+=", nd_stkmin=" + objUti.getDoubleValueAt(arlDatInvBod, k, INT_INVBOD_STK_MIN) + "";
                    strSQLTmpInvBod+=", nd_stkmax=" + objUti.getDoubleValueAt(arlDatInvBod, k, INT_INVBOD_STK_MAX) + "";
                    strSQLTmpInvBod+=" where co_emp=" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_EMP) + "";
                    strSQLTmpInvBod+=" and co_itm=" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_ITM) + "";
                    strSQLTmpInvBod+=" and co_bod=" + objUti.getIntValueAt(arlDatInvBod, k, INT_INVBOD_COD_BOD) + "";
                    System.out.println("ENERO: "+strSQLTmpInvBod);
                    stmTmpInv.executeUpdate(strSQLTmpInvBod);
                    stmTmpInv.close();
                    stmTmpInv=null;                    
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean insertaTbmEquInv(ArrayList arlDatEquInv){
        boolean blnRes=true;        
        try{
            if (conTmp!=null){
                for (int k=0;k<arlDatEquInv.size();k++){
                    if(objUti.getStringValueAt(arlDatEquInv, k, INT_COL_EST_REG_REP).toString().equals("I")){
                        stmEquInv=conTmp.createStatement();
                        //Armar la sentencia SQL.
                        strSQLTmpInv="";
                        strSQLTmpInv+="insert into tbm_equinv(";
                        strSQLTmpInv+=" co_itmmae, co_emp, co_itm, st_regrep";
                        strSQLTmpInv+=")";
                        strSQLTmpInv+=" values(";
                        strSQLTmpInv+="" + objUti.getIntValueAt(arlDatEquInv, k, INT_COL_COD_ITM_MAE) + ",";
                        strSQLTmpInv+="" + objUti.getIntValueAt(arlDatEquInv, k, INT_COL_COD_EMP) + ",";
                        strSQLTmpInv+="" + objUti.getIntValueAt(arlDatEquInv, k, INT_COL_COD_IMT) + ",";
                        strSQLTmpInv+="'I'" ;
                        strSQLTmpInv+=")";
//                        System.out.println("ESTO SE INSERTA: " +strSQLTmpInv);
                        stmEquInv.executeUpdate(strSQLTmpInv);
                        stmEquInv.close();
                        stmEquInv=null;                    
                    }
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        

    //ESTE METODO ACTUALIZA EL ESTADO DE REPLICACION DE LA DB REMOTA, NO DE LA CENTRAL
    private boolean modificaEstRepTbmInvRem(ArrayList arlDat){
        boolean blnRes=true;
        try
        {
            if (conTmp!=null){
                for (int k=0;k<arlDat.size();k++){
                        stmTmpInv=conTmp.createStatement();
                        //Armar la sentencia SQL.
                        strSQLTmpInv="";
                        strSQLTmpInv+="update tbm_inv";
                        strSQLTmpInv+=" set";
                        strSQLTmpInv+=" st_regrep= 'P'" ;
                        strSQLTmpInv+=" where co_emp=" + objUti.getIntValueAt(arlDat, k, INT_INV_COD_EMP) + "";
                        strSQLTmpInv+=" and co_itm=" + objUti.getIntValueAt(arlDat, k, INT_INV_COD_ITM) + "";
                        //strSQLTmpInv+=" and st_regrep<>'P'";
                        System.out.println("modificaTbmInv: " + strSQLTmpInv);
                        stmTmpInv.executeUpdate(strSQLTmpInv);
                        stmTmpInv.close();
                        stmTmpInv=null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
        
    
    private boolean modificaEstRepTbmEquInvRem(ArrayList arlDatEquInv){
        boolean blnRes=true;
        try
        {
            if (conTmp!=null){
                for (int k=0;k<arlDatEquInv.size();k++){
                        stmTmpInv=conTmp.createStatement();
                        //Armar la sentencia SQL.
                        strSQLTmpInv="";
                        strSQLTmpInv+="update tbm_equinv";
                        strSQLTmpInv+=" set";
                        strSQLTmpInv+=" st_regrep= 'P'" ;
                        strSQLTmpInv+=" where co_emp=" + objUti.getIntValueAt(arlDatEquInv, k, INT_COL_COD_EMP) + "";
                        strSQLTmpInv+=" and co_itm=" + objUti.getIntValueAt(arlDatEquInv, k, INT_COL_COD_IMT) + "";
                        strSQLTmpInv+=" and co_itmmae=" + objUti.getIntValueAt(arlDatEquInv, k, INT_COL_COD_ITM_MAE) + "";
                        System.out.println("modificaTbmInv: " + strSQLTmpInv);
                        stmTmpInv.executeUpdate(strSQLTmpInv);
                        stmTmpInv.close();
                        stmTmpInv=null;    
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
            
    
    
    
    
    
    
    private boolean modificaEstRepTbmInv(ArrayList arlDatInv){
        boolean blnRes=true;
        try
        {
            if (conTmp!=null){
                for (int k=0;k<arlDatInv.size();k++){
                    stmTmpInv=conTmp.createStatement();
                    //Armar la sentencia SQL.
                    strSQLTmpInv="";
                    strSQLTmpInv+="update tbm_inv";
                    strSQLTmpInv+=" set";
                    strSQLTmpInv+=" st_regrep= 'P'" ;
                    strSQLTmpInv+=" where co_emp=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_EMP) + "";
                    strSQLTmpInv+=" and co_itm=" + objUti.getIntValueAt(arlDatInv, k, INT_INV_COD_ITM) + "";
                    System.out.println("MODIFICA ESTADO REPLIK  A INV : " + strSQLTmpInv);
                    stmTmpInv.executeUpdate(strSQLTmpInv);
                    stmTmpInv.close();
                    stmTmpInv=null;                    
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    private boolean modificaTbmBasDat2(ArrayList arlDat, int j){
        boolean blnRes=true;
        try
        {
            if (conRem!=null){
                System.out.println("JAVIER 3: "+arlDat.toString());
                    stmRem=conRem.createStatement();
                    //Armar la sentencia SQL.
                    strSQLTmp="";
                    strSQLTmp+="update tbm_cfgbasdatrep";
                    strSQLTmp+=" set st_reg='A'";
                    strSQLTmp+=" where co_grp=" + INT_COD_GRP_CNF + "";
                    strSQLTmp+=" and co_regorg=" + objUti.getStringValueAt(arlDat, j, INT_COL_BAS_REG_ORI) + "";
                    strSQLTmp+=" and co_regdes=" + objUti.getStringValueAt(arlDat, j, INT_COL_BAS_REG_DES) + "";
                    System.out.println("PROCESO CON EXITO: "+strSQLTmp);
                    stmRem.executeUpdate(strSQLTmp);
                    stmRem.close();
                    stmRem=null;                    
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    
    //MODIFICA EN tbm_cfgbasdatrep EL CAMPO ST_REG A 'P' CUANDO SE EMPIEZA A EJECUTAR EL HILO
    private boolean modificaTbmBasDat2Inicial(){
        boolean blnRes=true;
        try
        {
            if (conGrl!=null){
                    stmGrl=conGrl.createStatement();
                    //Armar la sentencia SQL.
                    strSQLTmp="";
                    strSQLTmp+="update tbm_cfgbasdatrep";
                    strSQLTmp+=" set st_reg='P'";
                    strSQLTmp+=" where co_grp=" + INT_COD_GRP_CNF + "";
                    System.out.println("CAMBIA DE EXITO A  PENDIENTE: "+strSQLTmp);
                    stmGrl.executeUpdate(strSQLTmp);
                    stmGrl.close();
                    stmGrl=null;                    
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }                    
    
    private void cargarDrv(){
        try {
              Class.forName(strDrv);
            } catch(java.lang.ClassNotFoundException e) {
                System.err.print("ClassNotFoundException: "); 
                System.err.println(e.getMessage());
            }         
    }
        
    private Connection conexDB(String con, String usu, String clv){
        try{
            conFun=java.sql.DriverManager.getConnection(con,usu,clv);
        }
        catch (java.sql.SQLException e)
        {
//            objUti.mostrarMsgErr_F1(this, e);
            conFun=null;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return conFun;
    }

    
    private boolean actualizaTbmInvLocal(){        
        boolean blnRes=true;
        try
        {
            if (conGrl!=null){
                stmGrl=conGrl.createStatement();
                strSQLTmp="";
                strSQLTmp+="select * from(";
                strSQLTmp+=" 	select *from (";
                strSQLTmp+=" 	select count(co_grp) as cntCodGrp from tbm_cfgbasdatrep";
                strSQLTmp+=" 	where co_grp=" + INT_COD_GRP_CNF + "";
                strSQLTmp+=" 	) as x,";
                strSQLTmp+=" 	(";
                strSQLTmp+=" 	select count(st_reg) as cntEstReg from tbm_cfgbasdatrep";
                strSQLTmp+=" 	where st_reg='A' and co_grp=" + INT_COD_GRP_CNF + "";
                strSQLTmp+=" 	) as y";
                strSQLTmp+=" ) as x";
                strSQLTmp+=" where cntCodGrp=cntEstReg";
                System.out.println("RESTA: "+strSQLTmp);
                rstGrl=stmGrl.executeQuery(strSQLTmp);                    
                if(rstGrl.next()){
                    if(conUpdTbmInv!=null){
                        conUpdTbmInv.setAutoCommit(false);
                        stmUpdTbmInv=conUpdTbmInv.createStatement();
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
                        strSQL+=" set st_regrep='P'";
//                        strSQL+=" where co_emp=" + intCodEmpLoc + "";
//                        strSQL+=" and st_regrep in ('I', 'M')";
                        strSQL+=" where";
                        strSQL+=" st_regrep in ('I', 'M')";
                        
                        
                        System.out.println("ACTUALIZA TBM_INV: "+strSQL);
                        stmUpdTbmInv.executeUpdate(strSQL);
                        //conUpdTbmInv.close();                conUpdTbmInv=null;
                        stmUpdTbmInv.close();                stmUpdTbmInv=null;                        
                    }

                }
                
                rstGrl.close();                rstGrl=null;
                stmGrl.close();                stmGrl=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
        
    private boolean actualizaTbmEquInvLocal(){        
        boolean blnRes=true;
        try
        {
            if (conGrl!=null){
                stmGrl=conGrl.createStatement();
                strSQLTmp="";
                strSQLTmp+="select * from(";
                strSQLTmp+=" 	select *from (";
                strSQLTmp+=" 	select count(co_grp) as cntCodGrp from tbm_cfgbasdatrep";
                strSQLTmp+=" 	where co_grp=" + INT_COD_GRP_CNF + "";
                strSQLTmp+=" 	) as x,";
                strSQLTmp+=" 	(";
                strSQLTmp+=" 	select count(st_reg) as cntEstReg from tbm_cfgbasdatrep";
                strSQLTmp+=" 	where st_reg='A' and co_grp=" + INT_COD_GRP_CNF + "";
                strSQLTmp+=" 	) as y";
                strSQLTmp+=" ) as x";
                strSQLTmp+=" where cntCodGrp=cntEstReg";
                rstGrl=stmGrl.executeQuery(strSQLTmp);                    
                if(rstGrl.next()){
                    if(conUpdTbmInv!=null){
                        conUpdTbmInv.setAutoCommit(false);
                        stmUpdTbmEquInv=conUpdTbmInv.createStatement();
                        strSQL="";
                        strSQL+="UPDATE tbm_equinv";
                        strSQL+=" set st_regrep='P'";
//                        strSQL+=" where co_emp=" + intCodEmpLoc + "";
//                        strSQL+=" and st_regrep in ('I', 'M')";
                        strSQL+=" where";
                        strSQL+=" st_regrep in ('I', 'M')";
                        System.out.println("ACTUALIZA TBM_EQUINV: "+strSQL);
                        stmUpdTbmEquInv.executeUpdate(strSQL);
                        stmUpdTbmEquInv.close();                stmUpdTbmEquInv=null;                        
                    }
                }
                rstGrl.close();                rstGrl=null;
                stmGrl.close();                stmGrl=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    



    
    
    
    private int obtieneFrecuencActivid(){
        
//                        System.out.println("ultima string conex: "+strConIni);
//                        System.out.println("ultima usuario conex: "+strUsrIni);
//                        System.out.println("ultima clave conex: "+strPswIni);
//        
                
        conGrl=conexDB(strConIni, strUsrIni, strPswIni);
        boolean blnRes=true;
        try
        {
            if (conGrl!=null){
                stmGrl=conGrl.createStatement();
                strSQLTmp="";
                strSQLTmp+="select distinct (tx_freact) as freRunHil from tbm_cfgbasdatrep";
                strSQLTmp+=" where co_grp=" + INT_COD_GRP_CNF + "";
                System.out.println("FRECUENCIA DE ACTIVACION DEL HILO: "+strSQLTmp);
                rstGrl=stmGrl.executeQuery(strSQLTmp);                    
                if(rstGrl.next()){
                    intTim=rstGrl.getInt("freRunHil");
                    intTim=intTim*60*1000;
                }
                conGrl.close();                conGrl=null;
                rstGrl.close();                rstGrl=null;
                stmGrl.close();                stmGrl=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        System.out.println("TIEMPO: "+intTim);
        return intTim;                        
        
    }
    
    
    
    
    
    
    
    
    
    
}
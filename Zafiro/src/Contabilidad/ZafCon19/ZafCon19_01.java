/*
 *
 * Created on 9 de marzo de 2006, 9:12
 *
 * v0.1 
 */

package Contabilidad.ZafCon19;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.*;
/**
 *
 * @author  Ingrid Lino
 */

public class ZafCon19_01 extends javax.swing.JDialog {
    //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblBus objTblBus;
    private ZafTblOrd objTblOrd;
    private ZafTblTot objTblTot;

    //configurar tabla de cheques que tienen asignado un banco"
    private final int INT_TBL_CHQ_BAN_ASI_LIN=0;
    private final int INT_TBL_CHQ_BAN_ASI_COD_EMP=1;
    private final int INT_TBL_CHQ_BAN_ASI_COD_LOC=2;
    private final int INT_TBL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ=3;
    private final int INT_TBL_CHQ_BAN_ASI_COD_DOC_REC_CHQ=4;
    private final int INT_TBL_CHQ_BAN_ASI_COD_REG_REC_CHQ=5;
    private final int INT_TBL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN=6;
    private final int INT_TBL_CHQ_BAN_ASI_COD_CLI=7;
    private final int INT_TBL_CHQ_BAN_ASI_NOM_CLI=8;
    private final int INT_TBL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS=9;
    private final int INT_TBL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS=10;
    private final int INT_TBL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS=11;
    private final int INT_TBL_CHQ_BAN_ASI_NUM_CTA=12;
    private final int INT_TBL_CHQ_BAN_ASI_NUM_CHQ=13;
    private final int INT_TBL_CHQ_BAN_ASI_VAL_CHQ=14;
    private final int INT_TBL_CHQ_BAN_ASI_FEC_VEN=15;
    private final int INT_TBL_CHQ_BAN_ASI_FEC_ASG_BCO=16;
    private final int INT_TBL_CHQ_BAN_ASI_COD_CTA_BAN_ASG=17;
    //para almacenar los registros de la consulta de "cheques que tienen asignado un banco"
    private int intAneShw;
    private ArrayList arlDatChqBanAsi;
    private int INT_ARL_CHQ_BAN_ASI_COD_EMP=0;
    private int INT_ARL_CHQ_BAN_ASI_COD_LOC=1;
    private int INT_ARL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ=2;
    private int INT_ARL_CHQ_BAN_ASI_COD_DOC_REC_CHQ=3;
    private int INT_ARL_CHQ_BAN_ASI_COD_REG_REC_CHQ=4;
    private int INT_ARL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN=5;
    private int INT_ARL_CHQ_BAN_ASI_COD_CLI=6;
    private int INT_ARL_CHQ_BAN_ASI_NOM_CLI=7;
    private int INT_ARL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS=8;
    private int INT_ARL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS=9;
    private int INT_ARL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS=10;
    private int INT_ARL_CHQ_BAN_ASI_NUM_CTA=11;
    private int INT_ARL_CHQ_BAN_ASI_NUM_CHQ=12;
    private int INT_ARL_CHQ_BAN_ASI_VAL_CHQ=13;
    private int INT_ARL_CHQ_BAN_ASI_FEC_VEN=14;
    private int INT_ARL_CHQ_BAN_ASI_FEC_ASG_BCO=15;
    private int INT_ARL_CHQ_BAN_ASI_COD_CTA_BAN_ASG=16;

    //configurar tabla de valores autorizados
    private final int INT_TBL_VAL_AUT_PAG_LIN=0;
    private final int INT_TBL_VAL_AUT_PAG_COD_EMP=1;
    private final int INT_TBL_VAL_AUT_PAG_COD_CLI=2;
    private final int INT_TBL_VAL_AUT_PAG_NOM_CLI=3;
    private final int INT_TBL_VAL_AUT_PAG_COD_LOC=4;
    private final int INT_TBL_VAL_AUT_PAG_COD_TIP_DOC=5;
    private final int INT_TBL_VAL_AUT_PAG_DES_COR_TIP_DOC=6;
    private final int INT_TBL_VAL_AUT_PAG_DES_LAR_TIP_DOC=7;
    private final int INT_TBL_VAL_AUT_PAG_COD_DOC=8;
    private final int INT_TBL_VAL_AUT_PAG_COD_REG=9;
    private final int INT_TBL_VAL_AUT_PAG_NUM_DOC=10;
    private final int INT_TBL_VAL_AUT_PAG_FEC_DOC=11;
    private final int INT_TBL_VAL_AUT_PAG_DIA_CRE=12;
    private final int INT_TBL_VAL_AUT_PAG_FEC_VEN=13;
    private final int INT_TBL_VAL_AUT_PAG_MON_PAG=14;
    private final int INT_TBL_VAL_AUT_PAG_ABO=15;
    private final int INT_TBL_VAL_AUT_PAG_VAL_PND=16;
    private final int INT_TBL_VAL_AUT_PAG_COD_CTA=17;
    //para almacenar los registros de la consulta de "valores autorizados a pagar"
    private ArrayList arlDatValAutPag;
    private int INT_ARL_VAL_AUT_PAG_COD_EMP=0;
    private int INT_ARL_VAL_AUT_PAG_COD_CLI=1;
    private int INT_ARL_VAL_AUT_PAG_NOM_CLI=2;
    private int INT_ARL_VAL_AUT_PAG_COD_LOC=3;
    private int INT_ARL_VAL_AUT_PAG_COD_TIP_DOC=4;
    private int INT_ARL_VAL_AUT_PAG_DES_COR_TIP_DOC=5;
    private int INT_ARL_VAL_AUT_PAG_DES_LAR_TIP_DOC=6;
    private int INT_ARL_VAL_AUT_PAG_COD_DOC=7;
    private int INT_ARL_VAL_AUT_PAG_COD_REG=8;
    private int INT_ARL_VAL_AUT_PAG_NUM_DOC=9;
    private int INT_ARL_VAL_AUT_PAG_FEC_DOC=10;
    private int INT_ARL_VAL_AUT_PAG_DIA_CRE=11;
    private int INT_ARL_VAL_AUT_PAG_FEC_VEN=12;
    private int INT_ARL_VAL_AUT_PAG_MON_PAG=13;
    private int INT_ARL_VAL_AUT_PAG_ABO=14;
    private int INT_ARL_VAL_AUT_PAG_VAL_PND=15;
    private int INT_ARL_VAL_AUT_PAG_COD_CTA=16;

    //configurar tabla de cheques emitidos
    private int INT_TBL_CHQ_EMI_LIN=0;
    private int INT_TBL_CHQ_EMI_COD_EMP=1;
    private int INT_TBL_CHQ_EMI_COD_CLI=2;
    private int INT_TBL_CHQ_EMI_NOM_CLI=3;
    private int INT_TBL_CHQ_EMI_COD_LOC=4;
    private int INT_TBL_CHQ_EMI_COD_TIP_DOC=5;
    private int INT_TBL_CHQ_EMI_DES_COR_TIP_DOC=6;
    private int INT_TBL_CHQ_EMI_DES_LAR_TIP_DOC=7;
    private int INT_TBL_CHQ_EMI_COD_DOC=8;
    private int INT_TBL_CHQ_EMI_NUM_DOC_UNO=9;
    private int INT_TBL_CHQ_EMI_NUM_DOC_DOS=10;
    private int INT_TBL_CHQ_EMI_FEC_DOC=11;
    private int INT_TBL_CHQ_EMI_FEC_VEN=12;
    private int INT_TBL_CHQ_EMI_VAL_DOC=13;
    private int INT_TBL_CHQ_EMI_CTA_BAN=14;
    //para almacenar los registros de la consulta de "cheques emitidos"
    private ArrayList arlDatChqEmi;
    private int INT_ARL_CHQ_EMI_COD_EMP=0;
    private int INT_ARL_CHQ_EMI_COD_CLI=1;
    private int INT_ARL_CHQ_EMI_NOM_CLI=2;
    private int INT_ARL_CHQ_EMI_COD_LOC=3;
    private int INT_ARL_CHQ_EMI_COD_TIP_DOC=4;
    private int INT_ARL_CHQ_EMI_DES_COR_TIP_DOC=5;
    private int INT_ARL_CHQ_EMI_DES_LAR_TIP_DOC=6;
    private int INT_ARL_CHQ_EMI_COD_DOC=7;
    private int INT_ARL_CHQ_EMI_NUM_DOC_UNO=8;
    private int INT_ARL_CHQ_EMI_NUM_DOC_DOS=9;
    private int INT_ARL_CHQ_EMI_FEC_DOC=10;
    private int INT_ARL_CHQ_EMI_FEC_VEN=11;
    private int INT_ARL_CHQ_EMI_VAL_DOC=12;
    private int INT_ARL_CHQ_EMI_CTA_BAN=13;

    // Configurar tabla de "Cheques emitidos pendientes de entregar al proveedor"
    private final int INT_TBL_VAL_EGR_CUS_LIN=0;
    private final int INT_TBL_VAL_EGR_CUS_COD_EMP=1;
    private final int INT_TBL_VAL_EGR_CUS_COD_CTA=2;
    private final int INT_TBL_VAL_EGR_CUS_COD_CLI=3;
    private final int INT_TBL_VAL_EGR_CUS_NOM_CLI=4;
    private final int INT_TBL_VAL_EGR_CUS_DES_COR_TIP_DOC=5;
    private final int INT_TBL_VAL_EGR_CUS_DES_LAR_TIP_DOC=6;
    private final int INT_TBL_VAL_EGR_CUS_NUM_DOC=7;
    private final int INT_TBL_VAL_EGR_CUS_FEC_DOC=8;
    private final int INT_TBL_VAL_EGR_CUS_FEC_VEN=9;
    private final int INT_TBL_VAL_EGR_CUS_VAL_CHQ=10;
    
    // Para almacenar los registros de la consulta "Cheques emitidos pendientes de entregar al proveedor"
    private ArrayList arlDatValEgrCus;
    private int INT_ARL_VAL_EGR_CUS_COD_EMP=0;
    private int INT_ARL_VAL_EGR_CUS_COD_CTA=1;
    private int INT_ARL_VAL_EGR_CUS_COD_CLI=2;
    private int INT_ARL_VAL_EGR_CUS_NOM_CLI=3;
    private int INT_ARL_VAL_EGR_CUS_DES_COR_TIP_DOC=4;
    private int INT_ARL_VAL_EGR_CUS_DES_LAR_TIP_DOC=5;
    private int INT_ARL_VAL_EGR_CUS_NUM_DOC=6;
    private int INT_ARL_VAL_EGR_CUS_FEC_DOC=7;
    private int INT_ARL_VAL_EGR_CUS_FEC_VEN=8;
    private int INT_ARL_VAL_EGR_CUS_VAL_CHQ=9;
    
    // Configurar tabla de "Saldo contable"
    private final int INT_TBL_SAL_CON_LIN=0;
    private final int INT_TBL_SAL_CON_COD_EMP=1;
    private final int INT_TBL_SAL_CON_COD_CTA=2;
    private final int INT_TBL_SAL_CON_COD_CLI=3;
    private final int INT_TBL_SAL_CON_NOM_CLI=4;
    private final int INT_TBL_SAL_CON_COD_BCO=5;
    private final int INT_TBL_SAL_CON_DES_COR_BCO=6;
    private final int INT_TBL_SAL_CON_DES_LAR_BCO=7;
    private final int INT_TBL_SAL_CON_NUM_CTACHQ=8;
    private final int INT_TBL_SAL_CON_NUM_CHQ=9;
    private final int INT_TBL_SAL_CON_MON_CHQ=10;
    private final int INT_TBL_SAL_CON_FEC_VENCHQ=11;
    
    // Para almacenar los registros de la consulta "Saldo contable"
    private ArrayList arlDatSalCon;
    private int INT_ARL_SAL_CON_COD_EMP=0;
    private int INT_ARL_SAL_CON_COD_CTA=1;
    private int INT_ARL_SAL_CON_COD_CLI=2;
    private int INT_ARL_SAL_CON_NOM_CLI=3;
    private int INT_ARL_SAL_CON_COD_BCO=4;
    private int INT_ARL_SAL_CON_DES_COR_BCO=5;
    private int INT_ARL_SAL_CON_DES_LAR_BCO=6;
    private int INT_ARL_SAL_CON_NUM_CTACHQ=7;
    private int INT_ARL_SAL_CON_NUM_CHQ=8;
    private int INT_ARL_SAL_CON_MON_CHQ=9;
    private int INT_ARL_SAL_CON_FEC_VENCHQ=10;
    
    // Configurar tabla de "Saldo disponible"
    private final int INT_TBL_SAL_DIS_LIN=0;
    private final int INT_TBL_SAL_DIS_COD_EMP=1;
    private final int INT_TBL_SAL_DIS_COD_CTA=2;
    private final int INT_TBL_SAL_DIS_COD_CLI=3;
    private final int INT_TBL_SAL_DIS_NOM_CLI=4;
    private final int INT_TBL_SAL_DIS_COD_BCO=5;
    private final int INT_TBL_SAL_DIS_DES_COR_BCO=6;
    private final int INT_TBL_SAL_DIS_DES_LAR_BCO=7;
    private final int INT_TBL_SAL_DIS_NUM_CTACHQ=8;
    private final int INT_TBL_SAL_DIS_NUM_CHQ=9;
    private final int INT_TBL_SAL_DIS_MON_CHQ=10;
    private final int INT_TBL_SAL_DIS_FEC_VENCHQ=11;
    
    // Para almacenar los registros de la consulta "Saldo disponible"
    private ArrayList arlDatSalDis;
    private int INT_ARL_SAL_DIS_COD_EMP=0;
    private int INT_ARL_SAL_DIS_COD_CTA=1;
    private int INT_ARL_SAL_DIS_COD_CLI=2;
    private int INT_ARL_SAL_DIS_NOM_CLI=3;
    private int INT_ARL_SAL_DIS_COD_BCO=4;
    private int INT_ARL_SAL_DIS_DES_COR_BCO=5;
    private int INT_ARL_SAL_DIS_DES_LAR_BCO=6;
    private int INT_ARL_SAL_DIS_NUM_CTACHQ=7;
    private int INT_ARL_SAL_DIS_NUM_CHQ=8;
    private int INT_ARL_SAL_DIS_MON_CHQ=9;
    private int INT_ARL_SAL_DIS_FEC_VENCHQ=10;

    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private Vector vecDat, vecCab, vecAux, vecReg;
    private ZafTblMod objTblMod;
    private ZafTblFilCab objTblFilCab;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAdaChqBanAsi objMouMotAda;
    private int intCodCtaBan, intCodEmp;
      
     public ZafCon19_01(java.awt.Frame parent, boolean modal, ZafParSis obj, int codigoAnexoShow) {
         super(parent, modal);
         try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            intAneShw=codigoAnexoShow;
            if(codigoAnexoShow==1){
                if(!configurarTblAsignacionBcos()){
                    dispose();
                }
            }
            if(codigoAnexoShow==2){
                if(!configurarTblValoresAutorizadosPagar()){
                    dispose();
                }
            }
            if(codigoAnexoShow==3){
                if(!configurarTblValoresChequesEmitidos()){
                    dispose();
                }
            }
            
            if(codigoAnexoShow==4){
                if(!configurarTblValEgrCus()){
                    dispose();
                }
            }
            
            if(codigoAnexoShow==5){
                if(!configurarTblSalCon()){
                    dispose();
                }
            }
            
            if(codigoAnexoShow==6){
                if(!configurarTblSalDis()){
                    dispose();
                }
            }
        }
         
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      PanFrm = new javax.swing.JPanel();
      tabFrm = new javax.swing.JTabbedPane();
      panGen = new javax.swing.JPanel();
      panObs = new javax.swing.JPanel();
      spnDat = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      spnTotal = new javax.swing.JScrollPane();
      tblTot = new javax.swing.JTable();
      panBar = new javax.swing.JPanel();
      panBot = new javax.swing.JPanel();
      panBotExe = new javax.swing.JPanel();
      butCan = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
         }
      });

      PanFrm.setLayout(new java.awt.BorderLayout());

      panGen.setLayout(new java.awt.BorderLayout());

      panObs.setPreferredSize(new java.awt.Dimension(9, 120));
      panObs.setLayout(new java.awt.BorderLayout());

      tblDat.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
         },
         new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
         }
      ));
      spnDat.setViewportView(tblDat);

      panObs.add(spnDat, java.awt.BorderLayout.CENTER);

      spnTotal.setPreferredSize(new java.awt.Dimension(320, 18));

      tblTot.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
         },
         new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
         }
      ));
      spnTotal.setViewportView(tblTot);

      panObs.add(spnTotal, java.awt.BorderLayout.SOUTH);

      panGen.add(panObs, java.awt.BorderLayout.CENTER);

      tabFrm.addTab("General", panGen);

      PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

      panBar.setLayout(new java.awt.BorderLayout());

      panBot.setLayout(new java.awt.BorderLayout());

      butCan.setText("Cancelar");
      butCan.setPreferredSize(new java.awt.Dimension(92, 25));
      butCan.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCanActionPerformed(evt);
         }
      });
      panBotExe.add(butCan);

      panBot.add(panBotExe, java.awt.BorderLayout.EAST);

      panBar.add(panBot, java.awt.BorderLayout.CENTER);

      PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

      getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
   }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
   
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel PanFrm;
   private javax.swing.JButton butCan;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panBot;
   private javax.swing.JPanel panBotExe;
   private javax.swing.JPanel panGen;
   private javax.swing.JPanel panObs;
   private javax.swing.JScrollPane spnDat;
   private javax.swing.JScrollPane spnTotal;
   private javax.swing.JTabbedPane tabFrm;
   private javax.swing.JTable tblDat;
   private javax.swing.JTable tblTot;
   // End of variables declaration//GEN-END:variables
       
    
    /** Cerrar la aplicacián. */
    private void exitForm(){
        dispose();
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaChqBanAsi extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_CHQ_BAN_ASI_COD_EMP:
                    strMsg="Codigo de la empresa";
                    break;
                case INT_TBL_CHQ_BAN_ASI_COD_LOC:
                    strMsg="Codigo del local";
                    break;
                case INT_TBL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ:
                    strMsg="Código del tipo de documento de la recepción del cheque";
                    break;
                case INT_TBL_CHQ_BAN_ASI_COD_DOC_REC_CHQ:
                    strMsg="Código del documento de la recepción del cheque";
                    break;
                case INT_TBL_CHQ_BAN_ASI_COD_REG_REC_CHQ:
                    strMsg="Código del registro de la recepción del cheque";
                    break;
                case INT_TBL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN:
                    strMsg="";
                    break;
                case INT_TBL_CHQ_BAN_ASI_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_CHQ_BAN_ASI_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS:
                    strMsg="Código del Banco";
                    break;
                case INT_TBL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS:
                    strMsg="Banco";
                    break;
                case INT_TBL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS:
                    strMsg="Nombre del Banco";
                    break;
                case INT_TBL_CHQ_BAN_ASI_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_CHQ_BAN_ASI_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_CHQ_BAN_ASI_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                case INT_TBL_CHQ_BAN_ASI_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_CHQ_BAN_ASI_FEC_ASG_BCO:
                    strMsg="Fecha de asignación del banco";
                    break;
                case INT_TBL_CHQ_BAN_ASI_COD_CTA_BAN_ASG:
                    strMsg="Código de cuenta bancaria";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaValAutPag extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_VAL_AUT_PAG_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_VAL_AUT_PAG_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_VAL_AUT_PAG_DES_COR_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_VAL_AUT_PAG_DES_LAR_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_VAL_AUT_PAG_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_VAL_AUT_PAG_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_VAL_AUT_PAG_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_VAL_AUT_PAG_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_VAL_AUT_PAG_MON_PAG:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_VAL_AUT_PAG_VAL_PND:
                    strMsg="Valor pendiente";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaValEgrCus extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_VAL_EGR_CUS_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_VAL_EGR_CUS_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_VAL_EGR_CUS_DES_COR_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_VAL_EGR_CUS_DES_LAR_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_VAL_EGR_CUS_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_VAL_EGR_CUS_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_VAL_EGR_CUS_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_VAL_EGR_CUS_VAL_CHQ:
                    strMsg="Valor del documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaSalCon extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_SAL_CON_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_SAL_CON_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_SAL_CON_COD_BCO:
                    strMsg="Código del banco";
                    break;
                case INT_TBL_SAL_CON_DES_COR_BCO:
                    strMsg="Nombre del banco";
                    break;
                case INT_TBL_SAL_CON_DES_LAR_BCO:
                    strMsg="Nombre del banco";
                    break;
                case INT_TBL_SAL_CON_NUM_CTACHQ:
                    strMsg="Número de cuenta del cheque";
                    break;
                case INT_TBL_SAL_CON_NUM_CHQ:
                    strMsg="Número del cheque";
                    break;
                case INT_TBL_SAL_CON_MON_CHQ:
                    strMsg="Valor del cheque";
                    break;
                case INT_TBL_SAL_CON_FEC_VENCHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaSalDis extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_SAL_DIS_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_SAL_DIS_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_SAL_DIS_COD_BCO:
                    strMsg="Código del banco";
                    break;
                case INT_TBL_SAL_DIS_DES_COR_BCO:
                    strMsg="Nombre del banco";
                    break;
                case INT_TBL_SAL_DIS_DES_LAR_BCO:
                    strMsg="Nombre del banco";
                    break;
                case INT_TBL_SAL_DIS_NUM_CTACHQ:
                    strMsg="Número de cuenta del cheque";
                    break;
                case INT_TBL_SAL_DIS_NUM_CHQ:
                    strMsg="Número del cheque";
                    break;
                case INT_TBL_SAL_DIS_MON_CHQ:
                    strMsg="Valor del cheque";
                    break;
                case INT_TBL_SAL_DIS_FEC_VENCHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblAsignacionBcos()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(17);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_CHQ_BAN_ASI_LIN,"");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ,"");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_DOC_REC_CHQ,"");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_REG_REC_CHQ,"");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN,"");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS,"Cód.Ban");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS,"Banco");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS,"Nombre del Banco");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_VAL_CHQ,"Val.Chq.");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_FEC_VEN,"Fec.Ven");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_FEC_ASG_BCO,"Fec.Asi.");
            vecCab.add(INT_TBL_CHQ_BAN_ASI_COD_CTA_BAN_ASG,"");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_DOC_REC_CHQ).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_REG_REC_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_NOM_CLI).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_NUM_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_VAL_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_FEC_ASG_BCO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_CTA_BAN_ASG).setPreferredWidth(60);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_COD_EMP).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAdaChqBanAsi());
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            tcmAux.getColumn(INT_TBL_CHQ_BAN_ASI_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_BAN_ASI_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_BAN_ASI_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_BAN_ASI_COD_DOC_REC_CHQ, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_BAN_ASI_COD_REG_REC_CHQ, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_BAN_ASI_COD_CTA_BAN_ASG, tblDat);
            
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

            //Libero los objetos auxiliares.
            tcmAux=null;

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblValoresAutorizadosPagar()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(18);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_VAL_AUT_PAG_LIN,"");
            vecCab.add(INT_TBL_VAL_AUT_PAG_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_VAL_AUT_PAG_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_DES_LAR_TIP_DOC,"Tipo de Documento");
            vecCab.add(INT_TBL_VAL_AUT_PAG_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_DIA_CRE,"Día.Cre.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_MON_PAG,"Val.Doc.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_ABO,"Abo");
            vecCab.add(INT_TBL_VAL_AUT_PAG_VAL_PND,"Val.Pen.");
            vecCab.add(INT_TBL_VAL_AUT_PAG_COD_CTA,"Cód.Cta");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_NOM_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_LOC).setPreferredWidth(50);
            
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_DES_COR_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_MON_PAG).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_ABO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_VAL_PND).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_CTA).setPreferredWidth(30);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_COD_EMP).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAdaValAutPag());
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_MON_PAG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VAL_AUT_PAG_VAL_PND).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;

            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_AUT_PAG_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_AUT_PAG_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_AUT_PAG_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_AUT_PAG_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_AUT_PAG_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_AUT_PAG_ABO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_AUT_PAG_COD_CTA, tblDat);

            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_VAL_AUT_PAG_MON_PAG, INT_TBL_VAL_AUT_PAG_VAL_PND};
            objTblTot=new ZafTblTot(spnDat, spnTotal, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux=null;

            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblValoresChequesEmitidos()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_CHQ_EMI_LIN,"");
            vecCab.add(INT_TBL_CHQ_EMI_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_CHQ_EMI_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_CHQ_EMI_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_CHQ_EMI_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_CHQ_EMI_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_CHQ_EMI_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_CHQ_EMI_DES_LAR_TIP_DOC,"Tipo de Documento");
            vecCab.add(INT_TBL_CHQ_EMI_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_CHQ_EMI_NUM_DOC_UNO,"Núm.Doc.1");
            vecCab.add(INT_TBL_CHQ_EMI_NUM_DOC_DOS,"Núm.Doc.2");
            vecCab.add(INT_TBL_CHQ_EMI_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_CHQ_EMI_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_CHQ_EMI_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_CHQ_EMI_CTA_BAN,"Cta.Ban.");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_CHQ_EMI_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_NOM_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_DES_COR_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_NUM_DOC_UNO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_NUM_DOC_DOS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_CTA_BAN).setPreferredWidth(30);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_CHQ_EMI_COD_EMP).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAdaChqBanAsi());
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CHQ_EMI_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_EMI_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_EMI_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_EMI_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CHQ_EMI_CTA_BAN, tblDat);


            //Libero los objetos auxiliares.
            tcmAux=null;

            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblValEgrCus()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(11);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_VAL_EGR_CUS_LIN,"");
            vecCab.add(INT_TBL_VAL_EGR_CUS_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_VAL_EGR_CUS_COD_CTA,"Cód.Cta");
            vecCab.add(INT_TBL_VAL_EGR_CUS_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_VAL_EGR_CUS_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_VAL_EGR_CUS_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_VAL_EGR_CUS_DES_LAR_TIP_DOC,"Tipo de Documento");
            vecCab.add(INT_TBL_VAL_EGR_CUS_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_VAL_EGR_CUS_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_VAL_EGR_CUS_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_VAL_EGR_CUS_VAL_CHQ,"Val.Chq.");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_COD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_NOM_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_DES_COR_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_VAL_CHQ).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_COD_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAdaValEgrCus());
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VAL_EGR_CUS_VAL_CHQ).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;

            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_EGR_CUS_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_VAL_EGR_CUS_COD_CTA, tblDat);
            
            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_VAL_EGR_CUS_VAL_CHQ};
            objTblTot=new ZafTblTot(spnDat, spnTotal, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux=null;

            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblSalCon()
    {
        boolean blnRes = true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(12);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_SAL_CON_LIN, "");
            vecCab.add(INT_TBL_SAL_CON_COD_EMP, "Cód.Emp.");
            vecCab.add(INT_TBL_SAL_CON_COD_CTA, "Cód.Cta");
            vecCab.add(INT_TBL_SAL_CON_COD_CLI, "Cód.Cli.");
            vecCab.add(INT_TBL_SAL_CON_NOM_CLI, "Cliente");
            vecCab.add(INT_TBL_SAL_CON_COD_BCO, "Cod.Ban.");
            vecCab.add(INT_TBL_SAL_CON_DES_COR_BCO, "Banco");
            vecCab.add(INT_TBL_SAL_CON_DES_LAR_BCO, "Nombre del Banco");
            vecCab.add(INT_TBL_SAL_CON_NUM_CTACHQ, "Num.Cta.");
            vecCab.add(INT_TBL_SAL_CON_NUM_CHQ, "Num.Chq.");
            vecCab.add(INT_TBL_SAL_CON_MON_CHQ, "Val.Chq.");
            vecCab.add(INT_TBL_SAL_CON_FEC_VENCHQ, "Fec.Ven");
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_SAL_CON_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_SAL_CON_COD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_SAL_CON_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_SAL_CON_NOM_CLI).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_SAL_CON_COD_BCO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_SAL_CON_DES_COR_BCO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_SAL_CON_DES_LAR_BCO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_SAL_CON_NUM_CTACHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_SAL_CON_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_SAL_CON_MON_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_SAL_CON_FEC_VENCHQ).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_SAL_CON_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_SAL_CON_COD_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAdaSalCon());
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_SAL_CON_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_SAL_CON_MON_CHQ).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;

            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_SAL_CON_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_SAL_CON_COD_CTA, tblDat);
            
            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_SAL_CON_MON_CHQ};
            objTblTot=new ZafTblTot(spnDat, spnTotal, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux=null;

            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblSalDis()
    {
        boolean blnRes = true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(12);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_SAL_DIS_LIN, "");
            vecCab.add(INT_TBL_SAL_DIS_COD_EMP, "Cód.Emp.");
            vecCab.add(INT_TBL_SAL_DIS_COD_CTA, "Cód.Cta");
            vecCab.add(INT_TBL_SAL_DIS_COD_CLI, "Cód.Cli.");
            vecCab.add(INT_TBL_SAL_DIS_NOM_CLI, "Cliente");
            vecCab.add(INT_TBL_SAL_DIS_COD_BCO, "Cod.Ban.");
            vecCab.add(INT_TBL_SAL_DIS_DES_COR_BCO, "Banco");
            vecCab.add(INT_TBL_SAL_DIS_DES_LAR_BCO, "Nombre del Banco");
            vecCab.add(INT_TBL_SAL_DIS_NUM_CTACHQ, "Num.Cta.");
            vecCab.add(INT_TBL_SAL_DIS_NUM_CHQ, "Num.Chq.");
            vecCab.add(INT_TBL_SAL_DIS_MON_CHQ, "Val.Chq.");
            vecCab.add(INT_TBL_SAL_DIS_FEC_VENCHQ, "Fec.Ven");
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_SAL_DIS_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_SAL_DIS_COD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_SAL_DIS_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_SAL_DIS_NOM_CLI).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_SAL_DIS_COD_BCO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_SAL_DIS_DES_COR_BCO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_SAL_DIS_DES_LAR_BCO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_SAL_DIS_NUM_CTACHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_SAL_DIS_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_SAL_DIS_MON_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_SAL_DIS_FEC_VENCHQ).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_SAL_DIS_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_SAL_DIS_COD_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAdaSalDis());
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_SAL_DIS_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_SAL_DIS_MON_CHQ).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;

            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_SAL_DIS_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_SAL_DIS_COD_CTA, tblDat);
            
            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_SAL_DIS_MON_CHQ};
            objTblTot=new ZafTblTot(spnDat, spnTotal, tblDat, tblTot, intCol);

            //Libero los objetos auxiliares.
            tcmAux=null;

            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarAsignacionBcos(){
        boolean blnRes=true;
        vecDat.clear();
        int intCodEmpArl=0;
        int intCodCtaArl=0;
        try{
            System.out.println("arlDatChqBanAsi 01: " + arlDatChqBanAsi.toString());
            for(int i=0;i<arlDatChqBanAsi.size(); i++){
                intCodEmpArl=objUti.getIntValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_EMP);
                intCodCtaArl=objUti.getIntValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_CTA_BAN_ASG);
                if(intCodEmpArl==intCodEmp && intCodCtaArl==intCodCtaBan){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_LIN,"");
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_EMP,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_EMP));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_LOC,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_LOC));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ, "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_DOC_REC_CHQ,     "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_DOC_REC_CHQ));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_REG_REC_CHQ,     "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_REG_REC_CHQ));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN,     "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_CLI,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_CLI));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_NOM_CLI,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_NOM_CLI));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS,     "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS, "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS, "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_NUM_CTA,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_NUM_CTA));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_NUM_CHQ,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_NUM_CHQ));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_VAL_CHQ,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_VAL_CHQ));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_FEC_VEN,             "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_FEC_VEN));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_FEC_ASG_BCO,         "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_FEC_ASG_BCO));
                    vecReg.add(INT_TBL_CHQ_BAN_ASI_COD_CTA_BAN_ASG,     "" + objUti.getStringValueAt(arlDatChqBanAsi, i, INT_ARL_CHQ_BAN_ASI_COD_CTA_BAN_ASG));
                    vecDat.add(vecReg);
                }
            }

            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public int getCuenta() {
        return intCodCtaBan;
    }

    public void setCuenta(int intCodCtaBan) {
        this.intCodCtaBan = intCodCtaBan;
    }
    
    public int getCodEmp() {
        return intCodEmp;
    }

    public void setCodEmp(int intCodEmp) {
        this.intCodEmp = intCodEmp;
    }

    public void setArregloDatos(ArrayList arlRefCon19) {
        if(intAneShw==1)
            this.arlDatChqBanAsi = arlRefCon19;
        if(intAneShw==2)
            this.arlDatValAutPag = arlRefCon19;
        if(intAneShw==3)
            this.arlDatChqEmi = arlRefCon19;
        if(intAneShw==4)
            this.arlDatValEgrCus = arlRefCon19;
        if(intAneShw==5)
            this.arlDatSalCon = arlRefCon19;
        if(intAneShw==6)
            this.arlDatSalDis = arlRefCon19;
    }

    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarValoresAutorizados(){
        boolean blnRes=true;
        vecDat.clear();
        int intCodEmpArl=0;
        int intCodCtaArl=0;
        System.out.println("19-01 arlDatValAutPag: " + arlDatValAutPag.toString());
        try{
            for(int i=0;i<arlDatValAutPag.size(); i++){
                intCodEmpArl=objUti.getIntValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_EMP);
                intCodCtaArl=objUti.getIntValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_CTA);
                if(intCodEmpArl==intCodEmp && intCodCtaArl==intCodCtaBan){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_VAL_AUT_PAG_LIN,             "");
                    vecReg.add(INT_TBL_VAL_AUT_PAG_COD_EMP,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_EMP));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_COD_CLI,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_CLI));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_NOM_CLI,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_NOM_CLI));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_COD_LOC,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_LOC));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_COD_TIP_DOC,     "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_TIP_DOC));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_DES_COR_TIP_DOC, "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_DES_COR_TIP_DOC));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_DES_LAR_TIP_DOC, "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_DES_LAR_TIP_DOC));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_COD_DOC,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_DOC));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_COD_REG,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_REG));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_NUM_DOC,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_NUM_DOC));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_FEC_DOC,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_FEC_DOC));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_DIA_CRE,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_DIA_CRE));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_FEC_VEN,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_FEC_VEN));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_MON_PAG,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_MON_PAG));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_ABO,             "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_ABO));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_VAL_PND,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_VAL_PND));
                    vecReg.add(INT_TBL_VAL_AUT_PAG_COD_CTA,         "" + objUti.getStringValueAt(arlDatValAutPag, i, INT_ARL_VAL_AUT_PAG_COD_CTA));
                    vecDat.add(vecReg);
                }
            }

            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            objTblTot.calcularTotales();
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarChequesEmitidos(){
        boolean blnRes=true;
        vecDat.clear();
        int intCodEmpArl=0;
        int intCodCtaArl=0;
        System.out.println("19-01 arlDatChqEmi: " + arlDatChqEmi.toString());
        try{
            for(int i=0;i<arlDatChqEmi.size(); i++){
                intCodEmpArl=objUti.getIntValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_COD_EMP);
                intCodCtaArl=objUti.getIntValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_CTA_BAN);
                if(intCodEmpArl==intCodEmp && intCodCtaArl==intCodCtaBan){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_CHQ_EMI_LIN,             "");
                    vecReg.add(INT_TBL_CHQ_EMI_COD_EMP,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_COD_EMP));
                    vecReg.add(INT_TBL_CHQ_EMI_COD_CLI,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_COD_CLI));
                    vecReg.add(INT_TBL_CHQ_EMI_NOM_CLI,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_NOM_CLI));
                    vecReg.add(INT_TBL_CHQ_EMI_COD_LOC,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_COD_LOC));
                    vecReg.add(INT_TBL_CHQ_EMI_COD_TIP_DOC,     "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_COD_TIP_DOC));
                    vecReg.add(INT_TBL_CHQ_EMI_DES_COR_TIP_DOC, "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_DES_COR_TIP_DOC));
                    vecReg.add(INT_TBL_CHQ_EMI_DES_LAR_TIP_DOC, "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_DES_LAR_TIP_DOC));
                    vecReg.add(INT_TBL_CHQ_EMI_COD_DOC,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_COD_DOC));
                    vecReg.add(INT_TBL_CHQ_EMI_NUM_DOC_UNO,     "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_NUM_DOC_UNO));
                    vecReg.add(INT_TBL_CHQ_EMI_NUM_DOC_DOS,     "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_NUM_DOC_DOS));
                    vecReg.add(INT_TBL_CHQ_EMI_FEC_DOC,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_FEC_DOC));
                    vecReg.add(INT_TBL_CHQ_EMI_FEC_VEN,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_FEC_VEN));
                    vecReg.add(INT_TBL_CHQ_EMI_VAL_DOC,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_VAL_DOC));
                    vecReg.add(INT_TBL_CHQ_EMI_CTA_BAN,         "" + objUti.getStringValueAt(arlDatChqEmi, i, INT_ARL_CHQ_EMI_CTA_BAN));
                    vecDat.add(vecReg);
                }
            }

            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarValEgrCus(){
        boolean blnRes=true;
        vecDat.clear();
        int intCodEmpArl=0;
        int intCodCtaArl=0;
        System.out.println("19-01 arlDatValEgrCus: " + arlDatValEgrCus.toString());
        try{
            for(int i=0;i<arlDatValEgrCus.size(); i++){
                intCodEmpArl=objUti.getIntValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_COD_EMP);
                intCodCtaArl=objUti.getIntValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_COD_CTA);
                if(intCodEmpArl==intCodEmp && intCodCtaArl==intCodCtaBan){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_VAL_EGR_CUS_LIN,             "");
                    vecReg.add(INT_TBL_VAL_EGR_CUS_COD_EMP,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_COD_EMP));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_COD_CTA,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_COD_CTA));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_COD_CLI,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_COD_CLI));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_NOM_CLI,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_NOM_CLI));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_DES_COR_TIP_DOC, "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_DES_COR_TIP_DOC));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_DES_LAR_TIP_DOC, "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_DES_LAR_TIP_DOC));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_NUM_DOC,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_NUM_DOC));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_FEC_DOC,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_FEC_DOC));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_FEC_VEN,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_FEC_VEN));
                    vecReg.add(INT_TBL_VAL_EGR_CUS_VAL_CHQ,         "" + objUti.getStringValueAt(arlDatValEgrCus, i, INT_ARL_VAL_EGR_CUS_VAL_CHQ));
                    vecDat.add(vecReg);
                }
            }
            
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            objTblTot.calcularTotales();
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarSalCon(){
        boolean blnRes = true;
        vecDat.clear();
        int intCodEmpArl = 0;
        int intCodCtaArl = 0;
        System.out.println("arlDatSalCon: " + arlDatSalCon.toString());
        try {
            for (int i = 0; i < arlDatSalCon.size(); i++)
            {   intCodEmpArl = objUti.getIntValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_COD_EMP);
                intCodCtaArl = objUti.getIntValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_COD_CTA);
                if (intCodEmpArl == intCodEmp && intCodCtaArl == intCodCtaBan)
                {   vecReg = new Vector();
                    vecReg.add(INT_TBL_SAL_CON_LIN,         "");
                    vecReg.add(INT_TBL_SAL_CON_COD_EMP,     "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_COD_EMP));
                    vecReg.add(INT_TBL_SAL_CON_COD_CTA,     "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_COD_CTA));
                    vecReg.add(INT_TBL_SAL_CON_COD_CLI,     "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_COD_CLI));
                    vecReg.add(INT_TBL_SAL_CON_NOM_CLI,     "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_NOM_CLI));
                    vecReg.add(INT_TBL_SAL_CON_COD_BCO,     "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_COD_BCO));
                    vecReg.add(INT_TBL_SAL_CON_DES_COR_BCO, "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_DES_COR_BCO));
                    vecReg.add(INT_TBL_SAL_CON_DES_LAR_BCO, "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_DES_LAR_BCO));
                    vecReg.add(INT_TBL_SAL_CON_NUM_CTACHQ,  "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_NUM_CTACHQ));
                    vecReg.add(INT_TBL_SAL_CON_NUM_CHQ,     "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_NUM_CHQ));
                    vecReg.add(INT_TBL_SAL_CON_MON_CHQ,     "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_MON_CHQ));
                    vecReg.add(INT_TBL_SAL_CON_FEC_VENCHQ,  "" + objUti.getStringValueAt(arlDatSalCon, i, INT_ARL_SAL_CON_FEC_VENCHQ));
                    vecDat.add(vecReg);
                }
            }

            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            objTblTot.calcularTotales();
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarSalDis(){
        boolean blnRes = true;
        vecDat.clear();
        int intCodEmpArl = 0;
        int intCodCtaArl = 0;
        System.out.println("arlDatSalDis: " + arlDatSalDis.toString());
        try {
            for (int i = 0; i < arlDatSalDis.size(); i++)
            {   intCodEmpArl = objUti.getIntValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_COD_EMP);
                intCodCtaArl = objUti.getIntValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_COD_CTA);
                if (intCodEmpArl == intCodEmp && intCodCtaArl == intCodCtaBan)
                {   vecReg = new Vector();
                    vecReg.add(INT_TBL_SAL_DIS_LIN,         "");
                    vecReg.add(INT_TBL_SAL_DIS_COD_EMP,     "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_COD_EMP));
                    vecReg.add(INT_TBL_SAL_DIS_COD_CTA,     "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_COD_CTA));
                    vecReg.add(INT_TBL_SAL_DIS_COD_CLI,     "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_COD_CLI));
                    vecReg.add(INT_TBL_SAL_DIS_NOM_CLI,     "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_NOM_CLI));
                    vecReg.add(INT_TBL_SAL_DIS_COD_BCO,     "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_COD_BCO));
                    vecReg.add(INT_TBL_SAL_DIS_DES_COR_BCO, "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_DES_COR_BCO));
                    vecReg.add(INT_TBL_SAL_DIS_DES_LAR_BCO, "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_DES_LAR_BCO));
                    vecReg.add(INT_TBL_SAL_DIS_NUM_CTACHQ,  "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_NUM_CTACHQ));
                    vecReg.add(INT_TBL_SAL_DIS_NUM_CHQ,     "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_NUM_CHQ));
                    vecReg.add(INT_TBL_SAL_DIS_MON_CHQ,     "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_MON_CHQ));
                    vecReg.add(INT_TBL_SAL_DIS_FEC_VENCHQ,  "" + objUti.getStringValueAt(arlDatSalDis, i, INT_ARL_SAL_DIS_FEC_VENCHQ));
                    vecDat.add(vecReg);
                }
            }

            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            objTblTot.calcularTotales();
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}
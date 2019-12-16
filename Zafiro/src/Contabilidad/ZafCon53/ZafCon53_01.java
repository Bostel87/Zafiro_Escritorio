/*
 * ZafVen01_01.java
 *
 * Created on 9 de marzo de 2006, 9:12
 *
 * v0.1 
 */

package Contabilidad.ZafCon53;


import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
//import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
//import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
//import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;

/**
 *
 * @author  Javier Ayapata
 */
public class ZafCon53_01 extends javax.swing.JDialog {
    
    
    //Constantes: Columnas del JTable:    
    //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblPopMnu objTblPopMnu;
    private String strSQL;
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_COD_LOC=2;
    final int INT_TBL_DAT_COD_TIP_DOC=3;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    final int INT_TBL_DAT_COD_DOC=6;
    final int INT_TBL_DAT_COD_PRV=7;
    final int INT_TBL_DAT_NUM_DOC=8;
    final int INT_TBL_DAT_FEC_DOC=9;
    final int INT_TBL_DAT_COD_TIP_RET=10;
    final int INT_TBL_DAT_DES_COR_TIP_RET=11;
    final int INT_TBL_DAT_DES_LAR_TIP_RET=12;
    final int INT_TBL_DAT_POR_RET=13;
    final int INT_TBL_DAT_APL=14;
    final int INT_TBL_DAT_BAS_IMP=15;
    final int INT_TBL_DAT_VAL_RET=16;
    String strAux;
    private Vector vecCab, vecReg, vecDat;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strCorFec;
    private String strCodTipDocFil;
    private String strCodPrv;
    
    
    
     public ZafCon53_01(java.awt.Frame parent, boolean modal, ZafParSis obj) {
         super(parent, modal);
        initComponents();
          //Inicializar objetos.
        objParSis=obj;
        strCorFec="";
        strCodTipDocFil="";
        strCodPrv="";
        configurarFrm();

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Valores a retener");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:        
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
    /** Cerrar la aplicacián. */
    private void exitForm(){
        dispose();
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        strAux="Valores a retener v0.1";
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            configurarTblDat();
                
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(16);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód. Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód. Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Des.Cor.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Des.Lar.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_PRV,"Cód.Prv.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_RET,"Cód.Tip.Ret.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_RET,"Des.Cor.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_RET,"Des.Lar.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_POR_RET,"% Ret.");
            vecCab.add(INT_TBL_DAT_APL,"Apl.");
            vecCab.add(INT_TBL_DAT_BAS_IMP,"Bas.Imp.");
            vecCab.add(INT_TBL_DAT_VAL_RET,"Val.Ret.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_PRV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_RET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_APL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_PRV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_RET, tblDat);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código de tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_COD_PRV:
                    strMsg="Código de proveedor";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_COD_TIP_RET:
                    strMsg="Código de retención";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_RET:
                    strMsg="Descripción corta del tipo de retención";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_RET:
                    strMsg="Descripción larga del tipo de retención";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DAT_APL:
                    strMsg="Aplicado a";
                    break;
                case INT_TBL_DAT_BAS_IMP:
                    strMsg="Base imponible";
                    break;
                case INT_TBL_DAT_VAL_RET:
                    strMsg="Valor a Retener";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    protected boolean cargarDatosRetener(){
        boolean blnRes=true;
        int intNumTotReg, i;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.tx_desCor, a3.tx_desLar, a1.ne_numDoc, a1.fe_doc,";
                strSQL+=" a1.co_cli, b3.tx_nom, (a2.mo_pag + a2.nd_abo) as nd_val, a4.co_tipret, a2.nd_porRet,";
                strSQL+=" a4.tx_desCor AS tx_desCorRet, a4.tx_desLar AS tx_desLarRet, a2.tx_aplRet, a1.nd_sub, a1.nd_porIva";
                strSQL+=" FROM ((tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS b3 ON a1.co_emp=b3.co_emp AND a1.co_cli=b3.co_cli)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc ";
                strSQL+=" AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_cabTipRet AS a4 ON a2.co_emp=a4.co_emp AND a2.co_tipRet=a4.co_tipRet";
                strSQL+=" INNER JOIN (";
                strSQL+=" 		SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+="                                      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+="                                      FROM tbm_cabTipDoc AS b1";
                strSQL+="                                      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                      AND b1.st_reg='A') AS a";
                strSQL+="                              INNER JOIN(";
                strSQL+="                                      SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+="                                              SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+="                                              FROM tbm_visBueMovInv AS b1";
                strSQL+="                                              WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                              AND b1.st_reg='A'";
                strSQL+="                                              ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+="                                      ) AS x";
                strSQL+="                                      GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+="                              ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+=" ) AS p";
                strSQL+=" ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+=" AND (a2.nd_porRet > 0) AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN('A','C')";
                if( ! strCodTipDocFil.equals(""))
                    strSQL+=" AND a1.co_tipDoc IN("  + strCodTipDocFil + ")";
                if( ! strCorFec.equals(""))
                    strSQL+=" " + strCorFec + "";
                if( ! strCodPrv.equals(""))
                    strSQL+=" AND a1.co_cli IN(" + strCodPrv + ")";
                
                
                strSQL+=" AND (a2.tx_numSer IS NOT NULL AND a2.tx_fecCad IS NOT NULL AND a2.tx_numAutSri IS NOT NULL AND a2.tx_numChq IS NOT NULL)";

                strSQL+=" ORDER BY a1.co_doc, a1.co_tipdoc, a1.fe_doc, b3.tx_nom";
                System.out.println("SQL: " + strSQL);
                rst=stm.executeQuery(strSQL);
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while(rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,         "" + rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_PRV,         "" + rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_RET,     "" + rst.getString("co_tipret"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_RET, "" + rst.getString("tx_desCorRet"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_RET, "" + rst.getString("tx_desLarRet"));
                        vecReg.add(INT_TBL_DAT_POR_RET,         "" + rst.getString("nd_porRet"));
                        strAux=rst.getString("tx_aplRet");
                        if (strAux==null){
                            vecReg.add(INT_TBL_DAT_APL,null);
                            vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                        }
                        else if (strAux.equals("S")){
                            vecReg.add(INT_TBL_DAT_APL,"Subtotal");
                            vecReg.add(INT_TBL_DAT_BAS_IMP,rst.getString("nd_sub"));
                        }
                        else if (strAux.equals("I")){
                            vecReg.add(INT_TBL_DAT_APL,"IVA");
                            vecReg.add(INT_TBL_DAT_BAS_IMP,"" + rst.getDouble("nd_sub")*(rst.getDouble("nd_porIva")/100));
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_APL,null);
                            vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                        }
                        vecReg.add(INT_TBL_DAT_VAL_RET,         "" + rst.getString("nd_val"));
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setIndeterminate(false);
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    public String getFechaCorte(){
        return strCorFec;
    }
    
    public void setFechaCorte(String fechaCorte){
        strCorFec=fechaCorte==null?"":fechaCorte;
    }
    
    
    public String getCodigoTipoDocumento(){
        return strCodTipDocFil;
    }
    
    public void setCodigoTipoDocumento(String tipoDocumentoFiltro){
        strCodTipDocFil=tipoDocumentoFiltro==null?"":tipoDocumentoFiltro;
    }
            
            
    
    public String getCodigoProveedor(){
        return strCodPrv;
    }
    
    public void setCodigoProveedor(String codigoProveedor){
        strCodPrv=codigoProveedor==null?"":codigoProveedor;
    }
            
            
}

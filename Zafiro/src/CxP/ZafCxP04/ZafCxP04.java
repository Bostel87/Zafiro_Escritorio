/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package CxP.ZafCxP04;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;


  

/**
 *
 * @author  Eddye Lino
 */
public class ZafCxP04 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;                        //L�nea.
    final int INT_TBL_DAT_COD_EMP=1;                        //Casilla de verificaci�n.
    final int INT_TBL_DAT_COD_LOC=2;                    //C�digo del local.
    final int INT_TBL_DAT_COD_TIP_DOC=3;                //C�digo del tipo de documento.
    final int INT_TBL_DAT_COD_DOC=4;                    //C�digo del documento (Sistema).
    final int INT_TBL_DAT_COD_PRV=5;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_NOM_PRV=6;                //Descripci�n larga del tipo de documento.
    final int INT_TBL_DAT_DES_COR_TIP_DOC=7;                    //C�digo del registro (Sistema).
    final int INT_TBL_DAT_NUM_DOC=8;                    //N�mero del documento.
    final int INT_TBL_DAT_FEC_DOC=9;                   //Fecha de vencimiento.
    final int INT_TBL_DAT_VAL_DOC=10;                   //Valor pendiente.
    final int INT_TBL_DAT_VAL_ABO=11;                   //Valor pendiente.
    final int INT_TBL_DAT_VAL_PEN=12;                   //Valor pendiente.
   
    private ZafColNumerada objColNum;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDes,dtpFecHas;

    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafTblCelRenChk objTblCelRenChkVisBue;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkVisBue;            //Editor: JCheckBox en celda.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strDesCorCta, strDesLarCta;          //Contenido del campo al obtener el foco.
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    private String strCodBen, strNomBen;                //Contenido del campo al obtener el foco.
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private int intAutDoc;                              //Autorizaciones: 1: No se necesita autorizaci�n; 2:Si se necesita autorizaci�n.
    //Variables de la clase.
    private String strIdePrv, strDirPrv;                //Campos: RUC y Direcci�n del Beneficiario.
    private ZafThreadGUI objThrGUI;
    
    private ArrayList arlRegTitCabChk, arlDatTitCabChk;
    final int INT_ARL_COD_VIS_BUE=0;
    final int INT_ARL_NOM_VIS_BUE=1;
    
    private String strVecDat[][];
    
    private int numColModIni, numColModAdd;
    private String strQueIns;
    
    private int intNumFilAddBco;
    
    private ArrayList arlReg, arlDat;
    final int INT_ARL_COD_CTA=0;
    final int INT_ARL_NOM_APR=1;
    
    private ArrayList arlRegVisBueUsr, arlDatVisBueUsr;
    final int INT_ARL_COD_VIS_BUE_USR=0;
    final int INT_ARL_EST_VIS_BUE_USR=1;
    
    private ArrayList arlRegVisBueTipDoc, arlDatVisBueTipDoc;
    final int INT_ARL_COD_VIS_BUE_TIP_DOC=0;
    final int INT_ARL_EST_VIS_BUE_TIP_DOC=1;
    
    private int intNumVisBueTipDoc;
    private int intNumVisBueUsu;
    
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    
    //el siguiente arraylist permite almacenar los datos de acuerdo al documento seleccionado y a sus vistos buenos (A/R)
    //al momento q se han seleccinado :)
    private ArrayList arlRegCodVisBueSel, arlDatCodVisBueSel;
    final int INT_ARL_VIS_BUE_SEL_FIL=0;
    final int INT_ARL_VIS_BUE_SEL_COL=1;
    final int INT_ARL_VIS_BUE_SEL_COD_VIS_BUE=2;
    final int INT_ARL_VIS_BUE_SEL_EST_VIS_BUE=3;
    final int INT_ARL_VIS_BUE_EST_INS=4;
    //se adicionaran los siguientes campos para hacer mas sencillo el proceso de delete e insert :)
    final int INT_ARL_VIS_BUE_COD_EMP=5;
    final int INT_ARL_VIS_BUE_COD_LOC=6;
    final int INT_ARL_VIS_BUE_COD_TIP_DOC=7;
    final int INT_ARL_VIS_BUE_COD_DOC=8;
    
    
    private ArrayList arlRegCodVisBueDB, arlDatCodVisBueDB;
    final int INT_ARL_VIS_BUE_DB_COD_EMP_DB=0;
    final int INT_ARL_VIS_BUE_DB_COD_LOC_DB=1;
    final int INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB=2;
    final int INT_ARL_VIS_BUE_DB_COD_DOC_DB=3;
    final int INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB=4;
    final int INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB=5;
    final int INT_ARL_VIS_BUE_DB_COD_USR_DB=6;
    
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private boolean blnMarTodChkTblEmp=true;
    
    
    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCxP04(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panCabFilDoc = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        chkIncPagRet = new javax.swing.JCheckBox();
        panFecVen = new javax.swing.JPanel();
        panCabFilProDoc = new javax.swing.JPanel();
        panFilFecVen = new javax.swing.JPanel();
        lblNomProDes = new javax.swing.JLabel();
        lblNomProHas = new javax.swing.JLabel();
        panRep = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panPieFrm = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butSav = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 110));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFilDoc.setPreferredSize(new java.awt.Dimension(100, 68));
        panCabFilDoc.setLayout(new java.awt.BorderLayout());

        panCabFil.setLayout(null);

        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panCabFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(108, 6, 56, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 100, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(430, 6, 20, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(165, 6, 264, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(75, 6, 32, 20);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        panCabFil.add(lblPrv);
        lblPrv.setBounds(4, 27, 70, 20);

        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusLost(evt);
            }
        });
        panCabFil.add(txtCodPrv);
        txtCodPrv.setBounds(75, 27, 56, 20);

        txtDesLarPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarPrvActionPerformed(evt);
            }
        });
        txtDesLarPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusLost(evt);
            }
        });
        panCabFil.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(132, 27, 264, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panCabFil.add(butPrv);
        butPrv.setBounds(397, 27, 20, 20);

        chkIncPagRet.setText("Incluir pagos de retenciones");
        panCabFil.add(chkIncPagRet);
        chkIncPagRet.setBounds(2, 46, 350, 16);

        panCabFilDoc.add(panCabFil, java.awt.BorderLayout.CENTER);

        panGenCab.add(panCabFilDoc, java.awt.BorderLayout.NORTH);

        panFecVen.setLayout(new java.awt.BorderLayout());

        panCabFilProDoc.setPreferredSize(new java.awt.Dimension(100, 80));
        panCabFilProDoc.setLayout(new java.awt.BorderLayout());

        panFilFecVen.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de documento"));
        panFilFecVen.setLayout(null);

        lblNomProDes.setText("Desde:");
        panFilFecVen.add(lblNomProDes);
        lblNomProDes.setBounds(40, 18, 60, 14);

        lblNomProHas.setText("Hasta:");
        panFilFecVen.add(lblNomProHas);
        lblNomProHas.setBounds(300, 18, 60, 14);

        panCabFilProDoc.add(panFilFecVen, java.awt.BorderLayout.CENTER);
        panFilFecVen.getAccessibleContext().setAccessibleName("Fecha de documento");

        panFecVen.add(panCabFilProDoc, java.awt.BorderLayout.CENTER);

        panGenCab.add(panFecVen, java.awt.BorderLayout.CENTER);

        jPanel1.add(panGenCab, java.awt.BorderLayout.NORTH);

        panRep.setLayout(new java.awt.BorderLayout());

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

        panRep.add(panGenDet, java.awt.BorderLayout.CENTER);

        jPanel1.add(panRep, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", jPanel1);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panPieFrm.setPreferredSize(new java.awt.Dimension(0, 60));
        panPieFrm.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butSav.setMnemonic('G');
        butSav.setText("Guardar");
        butSav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSavActionPerformed(evt);
            }
        });
        panBot.add(butSav);

        butCer.setMnemonic('r');
        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panPieFrm.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 20));
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

        panPieFrm.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panPieFrm, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtDesLarPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConPrv(2);
                //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
            }
        }
        else
            txtDesLarPrv.setText(strDesLarPrv);
        
    }//GEN-LAST:event_txtDesLarPrvFocusLost

    private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
        strDesLarPrv=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
    }//GEN-LAST:event_txtDesLarPrvFocusGained

    private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
        txtDesLarPrv.transferFocus();
    }//GEN-LAST:event_txtDesLarPrvActionPerformed

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
        {
            if (txtCodPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConPrv(1);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
        

    }//GEN-LAST:event_txtCodPrvFocusLost

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
    }//GEN-LAST:event_txtCodPrvFocusGained

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
        if (!txtCodPrv.getText().equals(""))
        {
            //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
        }
        

        
        
        
        
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else{
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicaci�n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="�Est� seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexi�n si est� abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
// TODO add your handling code here:
                                           
    
//    eliminaColTblDatAdicionadas();
    
    
    
    
    if(getCodTipDoc()){
        if (butCon.getText().equals("Consultar")){
            //objTblTotales.isActivo(false);
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else{
            blnCon=false;
        }        
    }
}//GEN-LAST:event_butConActionPerformed

private void butSavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSavActionPerformed
// TODO add your handling code here:
    if(guardarDatos()){
        mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
//        objTblMod.initRowsState();
        objTblMod.removeAllRows();
        cargarReg();
        
        
    }
    else{
        mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
    }
}//GEN-LAST:event_butSavActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
// TODO add your handling code here:
    exitForm(null);
}//GEN-LAST:event_butCerActionPerformed


private boolean getCodTipDoc(){
    boolean blnRes=true;
    if(txtCodTipDoc.getText().toString().equals("")){
        tabFrm.setSelectedIndex(0);
        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
        blnRes=false;
    }
    return blnRes;
}



    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butSav;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkIncPagRet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomProDes;
    private javax.swing.JLabel lblNomProHas;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCabFilDoc;
    private javax.swing.JPanel panCabFilProDoc;
    private javax.swing.JPanel panFecVen;
    private javax.swing.JPanel panFilFecVen;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panPieFrm;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try{
            arlDatTitCabChk=new ArrayList();
            arlDatVisBueUsr=new ArrayList();
            arlDatVisBueTipDoc=new ArrayList();
            arlDatCodVisBueSel=new ArrayList();
            arlDatCodVisBueDB=new ArrayList();
            objUti=new ZafUtil();
            String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker:
            //DESDE
            dtpFecDes=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDes.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDes.setText(strFecSis);
            panFilFecVen.add(dtpFecDes);
            dtpFecDes.setBounds(100, 16, 120, 20);
            
            //HASTA
            dtpFecHas=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecHas.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecHas.setText(strFecSis);
            panFilFecVen.add(dtpFecHas);
            dtpFecHas.setBounds(356, 16, 120, 20);
            
            
            
            //Inicializar objetos.
            strVecDat=new String [50][50];
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.6");
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConPrv();
            arlDat=new ArrayList();
            //Configurar los JTables.
            configurarTblDat();

//            if(objParSis.getCodigoUsuario()==51){
//                butSav.setVisible(false);
//                butSav.setEnabled(false);
//            }
            
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funci�n configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(13);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"COD.EMP.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"COD.LOC");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"COD.TIP.DOC.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"COD.DOC.");
            vecCab.add(INT_TBL_DAT_COD_PRV,"COD.PRO..");
            if(objParSis.getCodigoMenu()==1848){
                vecCab.add(INT_TBL_DAT_NOM_PRV,"NOM.PRO.");
            }
            else{
                vecCab.add(INT_TBL_DAT_NOM_PRV,"NOM.CLI.");
            }
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"TIP.DOC.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"NUM.DOC.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"FEC.DOC.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"VAL.DOC.");
            vecCab.add(INT_TBL_DAT_VAL_ABO,"VAL.ABO.");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"VAL.PEN.");
            objTblMod=new ZafTblMod();
            
            
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_DOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_ABO, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_PEN, objTblMod.INT_COL_DBL, new Integer(0), null);
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblEmpMouseClicked(evt);
                }
            });


            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_PRV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_PRV).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            

            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            
            //Configurar JTable: Editor de b�squeda.
            objTblBus=new ZafTblBus(tblDat);
            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            numColModIni=tblDat.getColumnCount();
            
        
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                }
            }
        }
        
        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }     
    
    /**
     * Esta funci�n determina si los campos son v�lidos.
     * @return true: Si los campos son v�lidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }

        //Validar el "Proveedor".
        if (txtCodPrv.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Proveedor</FONT> es obligatorio.<BR>Escriba o seleccione un proveedor y vuelva a intentarlo.</HTML>");
            txtCodPrv.requestFocus();
            return false;
        }
               
        return true;
    }

    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta funci�n muestra un mensaje de advertencia al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notif�quelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
        
    /**
     * Esta funci�n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que s�lo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    ///strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    ///strSQL+=" AND a1.co_loc=" + intCodLoc;
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtCodPrv.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    rstCab.first();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
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

    
    /**
     * Esta funci�n permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodPrv.setText("");
            strIdePrv="";
            txtDesLarPrv.setText("");
            strDirPrv="";
            objTblMod.removeAllRows();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("C�digo");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
//                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("C�digo");
            arlAli.add("Identificaci�n");
            arlAli.add("Nombre");
            arlAli.add("Direcci�n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoMenu()==1848){
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_prv='S' AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.tx_nom";
                lblPrv.setText("Proveedor:");
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S' AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.tx_nom";
                lblPrv.setText("Cliente:");
            }

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funci�n muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=";
                strSQL+=" (";
                strSQL+=" SELECT co_tipDoc";
                strSQL+=" FROM tbr_tipDocPrg";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND st_reg='S'";
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
    
    /**
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux=vcoTipDoc.getValueAt(4);
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        configurarColAddModDat();
                    }
                    break;
                case 1: //B�squeda directa por "Descripci�n corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux=vcoTipDoc.getValueAt(4);
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        configurarColAddModDat();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux=vcoTipDoc.getValueAt(4);
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            configurarColAddModDat();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux=vcoTipDoc.getValueAt(4);
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        configurarColAddModDat();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux=vcoTipDoc.getValueAt(4);
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            configurarColAddModDat();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    /**
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
   private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);

                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                            
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                        }
                        else
                        {
                            txtDesLarPrv.setText(strDesLarPrv);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
     */
   class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }

    /**
     * Esta funci�n se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
   private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtDesLarPrv.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
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
                    strMsg="Código de local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código de tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_COD_PRV:
                    strMsg="Código de proveedor";
                    break;
                case INT_TBL_DAT_NOM_PRV:
                    if(objParSis.getCodigoMenu()==1848)
                        strMsg="Nombre de proveedor";
                    else
                        strMsg="Nombre de cliente";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de vencimiento";
                    break;
                    case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    strMsg="Autorización";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Autorización Auxiliar";
                    break;
                    
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 
    /**
     * Esta clase crea un hilo que permite manipular la interface gr�fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que est� ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estar�a informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar �sta clase
     * ya que si no s�lo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
   private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(0);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }

   private boolean cargarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(cargarArregloVistosBuenosDB()){
                    if(cargarDetReg()){
                        if(numVistosBuenosTipoDcto()){
                            if(numVistosBuenosUsuario()){
                                arlDatCodVisBueSel.clear();

                            }
                        }

                    }
                }
                con.close();
                con=null;
            }            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }    
    
   private boolean cargarDetReg(){
        int intNumTotReg, i;
        boolean blnRes=true;
        String strFecDes="", strFecHas="";
        int w=0;
        String strArlVisBueDbCodEmp="", strArlVisBueDbCodLoc="", strArlVisBueDbCodTipDoc="";
        String strArlVisBueDbCodDoc="", strArlVisBueDbCodVisBue="", strArlVisBueDbEstVisBue="", strArlVisBueDbCodUsr="";
        String strRstVisBueDbCodEmp="", strRstVisBueDbCodLoc="", strRstVisBueDbCodTipDoc="", strRstVisBueDbCodDoc="";
        int intVecVisBueCol=0;
        String strAuxTmp="";
        
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (con!=null){
                strAux="";
                strAuxTmp="";
                if(!  txtCodPrv.getText().toString().equals(""))
                    strAux+="   AND a1.co_cli=" + txtCodPrv.getText()  + "";
                if(!  txtCodTipDoc.getText().toString().equals(""))
                    strAux+="   AND a1.co_tipdoc=" + txtCodTipDoc.getText()  + "";
                
//                strFecDes = "" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"";
//                if( ! strFecDes.toString().equals(""))
//                    strAuxTmp+=" AND a2.fe_ven>='" + strFecDes + "'";
//
//                strFecHas = "" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"";
//                if( ! strFecHas.toString().equals(""))
//                    strAuxTmp+=" AND a2.fe_ven<='" + strFecHas + "'";
                

                strFecDes = "" + objUti.formatearFecha(dtpFecDes.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"";
                if( ! strFecDes.toString().equals(""))
                    strAuxTmp+=" AND a1.fe_doc>='" + strFecDes + "'";

                strFecHas = "" + objUti.formatearFecha(dtpFecHas.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"";
                if( ! strFecHas.toString().equals(""))
                    strAuxTmp+=" AND a1.fe_doc<='" + strFecHas + "'";

                
                stm=con.createStatement();
                //--PARA FACTURAS Y NOTAS DE CREDITO
                strSQL="";
                strSQL+="SELECT COUNT(*) FROM(";
                strSQL+=" 		 SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, ";
                strSQL+="                  a1.co_doc, a1.ne_numdoc, a1.co_cli, ";
                strSQL+="                  a1.tx_nomcli, a1.fe_doc, ";
                strSQL+="                  a3.tx_descor, SUM(abs(a2.nd_abo)) AS nd_abo";
                strSQL+="                  FROM tbm_cabMovInv AS a1          INNER JOIN tbm_pagmovinv AS a2";
                strSQL+="                  ON a1.co_emp = a2.co_emp           AND a1.co_loc = a2.co_loc";
                strSQL+="                  	   AND a1.co_tipdoc = a2.co_tipdoc           AND a1.co_doc = a2.co_doc";
                strSQL+="                  	   INNER JOIN tbm_cabtipdoc AS a3";
                strSQL+="                  ON a1.co_emp = a3.co_emp           AND a1.co_loc = a3.co_loc";
                strSQL+="                  	   AND a1.co_tipdoc = a3.co_tipdoc    ";
                strSQL+="                  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                //strSQL+="                  AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=strAux;
                strSQL+=strAuxTmp;
                if(objParSis.getCodigoMenu()==1848){
                    strSQL+="                  AND a3.ne_mod IN(2,3,4,5)   ";
                }
                else{
                    strSQL+="                  AND a3.ne_mod IN(1,3)   ";
                }
                strSQL+="                   AND a1.st_reg NOT IN('I','E')";
                strSQL+="                  AND a2.st_reg IN('A','C')   ";
                if( ! chkIncPagRet.isSelected())
                    strSQL+="               AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                strSQL+="                  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, ";
                strSQL+="                  a1.co_doc, a1.ne_numdoc, a1.co_cli,  ";
                strSQL+="                  a1.tx_nomcli, a1.fe_doc, a3.tx_descor";
                if(txtCodTipDoc.getText().equals("1"))
                    strSQL+="                  HAVING (SUM(abs(a2.mo_pag)) - SUM(abs(a2.nd_abo))  )<0";
                else
                    strSQL+="                  HAVING (SUM(abs(a2.mo_pag)) - SUM(abs(a2.nd_abo))  )>0";
                strSQL+="                  ORDER BY a1.co_doc, a1.co_tipDoc, a1.co_loc";
                strSQL+=" ) AS x";
                strSQL+=" INNER JOIN(";
                strSQL+="                  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, SUM(abs(a2.mo_pag)) AS mo_pag";
                strSQL+=" 		 ,(SUM(abs(a2.mo_pag)) - SUM(abs(a2.nd_abo))  ) AS nd_valPen";
                strSQL+="                  FROM tbm_cabMovInv AS a1          INNER JOIN tbm_pagmovinv AS a2";
                strSQL+="                  ON a1.co_emp = a2.co_emp           AND a1.co_loc = a2.co_loc";
                strSQL+="             	 AND a1.co_tipdoc = a2.co_tipdoc           AND a1.co_doc = a2.co_doc";
                strSQL+="                  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                //strSQL+="                  AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=strAux;
                strSQL+="                  AND a1.st_reg NOT IN('I','E') ";
                strSQL+="                  AND a2.st_reg IN('A','C')   ";
                if( ! chkIncPagRet.isSelected())
                    strSQL+="               AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                strSQL+=" 		 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
                strSQL+=" ) AS y";
                strSQL+=" ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc AND x.co_doc=y.co_doc";

                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;                                
                
                
                strSQL="";
                strSQL+="SELECT x.*, y.mo_pag, y.nd_valPen FROM(";
                strSQL+=" 		 SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, ";
                strSQL+="                  a1.co_doc, a1.ne_numdoc, a1.co_cli, ";
                strSQL+="                  a1.tx_nomcli, a1.fe_doc, ";
                strSQL+="                  a3.tx_descor, SUM(abs(a2.nd_abo)) AS nd_abo";
                strSQL+="                  FROM tbm_cabMovInv AS a1          INNER JOIN tbm_pagmovinv AS a2";
                strSQL+="                  ON a1.co_emp = a2.co_emp           AND a1.co_loc = a2.co_loc";
                strSQL+="                  	   AND a1.co_tipdoc = a2.co_tipdoc           AND a1.co_doc = a2.co_doc";
                strSQL+="                  	   INNER JOIN tbm_cabtipdoc AS a3";
                strSQL+="                  ON a1.co_emp = a3.co_emp           AND a1.co_loc = a3.co_loc";
                strSQL+="                  	   AND a1.co_tipdoc = a3.co_tipdoc    ";
                strSQL+="                  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                //strSQL+="                  AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=strAux;
                strSQL+=strAuxTmp;
                if(objParSis.getCodigoMenu()==1848){
                    strSQL+="                  AND a3.ne_mod IN(2,3,4,5)  ";
                }
                else{
                    strSQL+="                  AND a3.ne_mod IN(1,3)  ";
                }
                
                strSQL+="                  AND a1.st_reg NOT IN('I','E') ";
                strSQL+="                  AND a2.st_reg IN('A','C')   ";
                if( ! chkIncPagRet.isSelected())
                    strSQL+="               AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                strSQL+="                  GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, ";
                strSQL+="                  a1.co_doc, a1.ne_numdoc, a1.co_cli,  ";
                strSQL+="                  a1.tx_nomcli, a1.fe_doc, a3.tx_descor";
                if(txtCodTipDoc.getText().equals("1"))
                    strSQL+="                  HAVING (SUM(abs(a2.mo_pag)) - SUM(abs(a2.nd_abo))  )<0";
                else
                    strSQL+="                  HAVING (SUM(abs(a2.mo_pag)) - SUM(abs(a2.nd_abo))  )>0";
                strSQL+="                  ORDER BY a1.co_doc, a1.co_tipDoc, a1.co_loc";
                strSQL+=" ) AS x";
                strSQL+=" INNER JOIN(";
                strSQL+="                  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, SUM(abs(a2.mo_pag)) AS mo_pag";
                strSQL+=" 		 ,(SUM(abs(a2.mo_pag)) - SUM(abs(a2.nd_abo))  ) AS nd_valPen";
                strSQL+="                  FROM tbm_cabMovInv AS a1          INNER JOIN tbm_pagmovinv AS a2";
                strSQL+="                  ON a1.co_emp = a2.co_emp           AND a1.co_loc = a2.co_loc";
                strSQL+="             	 AND a1.co_tipdoc = a2.co_tipdoc           AND a1.co_doc = a2.co_doc";
                strSQL+="                  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                //strSQL+="                  AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=strAux;
                strSQL+="                  AND a1.st_reg NOT IN('I','E') ";
                strSQL+="                  AND a2.st_reg IN('A','C')   ";
                if( ! chkIncPagRet.isSelected())
                    strSQL+="               AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                strSQL+=" 		 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
                strSQL+=" ) AS y";
                strSQL+=" ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc AND x.co_doc=y.co_doc";
                
                System.out.println("SQL cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                

                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        strRstVisBueDbCodEmp=rst.getString("co_emp");
                        vecReg.add(INT_TBL_DAT_COD_EMP,""           + strRstVisBueDbCodEmp);
                        strRstVisBueDbCodLoc=rst.getString("co_loc");
                        vecReg.add(INT_TBL_DAT_COD_LOC,""           + strRstVisBueDbCodLoc);
                        strRstVisBueDbCodTipDoc=rst.getString("co_tipdoc");
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,""       + strRstVisBueDbCodTipDoc);
                        strRstVisBueDbCodDoc=rst.getString("co_doc");
                        vecReg.add(INT_TBL_DAT_COD_DOC,""           + strRstVisBueDbCodDoc);
                        
                        vecReg.add(INT_TBL_DAT_COD_PRV,""           + rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_PRV,""           + rst.getString("tx_nomcli"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,""   + rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,""           + rst.getString("ne_numdoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,""           + rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,""           + rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_ABO,""           + rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,""           + rst.getString("nd_valPen"));
                        
                        //para adicionar el componente CHK en las columnas adicionadas
//                        vecAux=new Vector();
                        for (int j=0; j<intNumFilAddBco; j++){
                            vecReg.add((numColModIni+j), "");
                            objTblCelRenChkVisBue=new ZafTblCelRenChk();
                            tblDat.getColumnModel().getColumn(numColModIni+j).setCellRenderer(objTblCelRenChkVisBue);
//                            vecAux.add("" + (numColModIni+j));
                             //Configurar JTable: Editor de la tabla.
                            objTblEdi=new ZafTblEdi(tblDat);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                            
                        }
                        //se hacen editables las columnas adicionadas y la de autorizacion.
//                        objTblMod.setColumnasEditables(vecAux);
//                        vecAux=null;
                                                
                        for(int k=0; k<arlDatCodVisBueDB.size(); k++){
                            w=0;
                            strArlVisBueDbCodEmp=objUti.getStringValueAt(arlDatCodVisBueDB,     k, INT_ARL_VIS_BUE_DB_COD_EMP_DB);
                            strArlVisBueDbCodLoc=objUti.getStringValueAt(arlDatCodVisBueDB,     k, INT_ARL_VIS_BUE_DB_COD_LOC_DB);
                            strArlVisBueDbCodTipDoc=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB);
                            strArlVisBueDbCodDoc=objUti.getStringValueAt(arlDatCodVisBueDB,     k, INT_ARL_VIS_BUE_DB_COD_DOC_DB);
                            strArlVisBueDbCodVisBue=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB)==null?"":objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB);
                            strArlVisBueDbEstVisBue=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB)==null?"":objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB);
                            strArlVisBueDbCodUsr=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_USR_DB)==null?"":objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_USR_DB);
                            
                            if(strRstVisBueDbCodEmp.equals(strArlVisBueDbCodEmp)){
                                if(strRstVisBueDbCodLoc.equals(strArlVisBueDbCodLoc)){
                                    if(strRstVisBueDbCodTipDoc.equals(strArlVisBueDbCodTipDoc)){
                                        if(strRstVisBueDbCodDoc.equals(strArlVisBueDbCodDoc)){
                                            if(! strArlVisBueDbCodVisBue.equals("")){
                                                do{
                                                    if(strArlVisBueDbCodVisBue.equals(strVecDat[w][0])){
                                                        intVecVisBueCol=Integer.parseInt(strVecDat[w][2]);

                                                        if(strArlVisBueDbEstVisBue.equals("A")){
                                                            vecReg.setElementAt(new Boolean(true), intVecVisBueCol);
                                                        }
                                                        else if(strArlVisBueDbEstVisBue.equals("D")){
                                                            vecReg.setElementAt(new Boolean(true), (intVecVisBueCol+1));
                                                        }
                                                        break;
                                                    }
                                                    else
                                                        w++;
                                                }while(strVecDat[w][2]!=null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);                        
                        //lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");                        
                    }
                    else{
                        break;
                    }                    
                }
                blnMarTodChkTblEmp=false;
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero s�lo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                
                validaVistosBuenos();
                objTblMod.initRowsState();
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

   private boolean generaNumColBancos(){
        boolean blnRes=true;
        intNumFilAddBco=0;
        strAux="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT COUNT(*) FROM(";
                strSQL+=" SELECT co_visBue, CAST('Aprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" UNION ";
                strSQL+=" SELECT co_visBue, CAST('Reprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" ORDER BY co_visBue";
                strSQL+=" ) AS x";
//                System.out.println("CONTADOR generaNumColBancos: " + strSQL);
                intNumFilAddBco=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumFilAddBco==-1)
                    return false;
                
                strSQL="";
                strSQL+=" SELECT co_visBue, CAST('Aprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" UNION ";
                strSQL+=" SELECT co_visBue, CAST('Reprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" ORDER BY co_visBue";
//                System.out.println("SQL generaNumColBancos: " + strSQL);
                rst=stm.executeQuery(strSQL);                
                for(int i=0; rst.next(); i++){
                    strVecDat[i][0]="" + rst.getInt("co_visBue");
                    strVecDat[i][1]=rst.getString("tx_desLar");
                    arlReg=new ArrayList();
                    arlReg.add(INT_ARL_COD_CTA, "" + rst.getInt("co_visBue"));
                    arlReg.add(INT_ARL_NOM_APR, "" + rst.getString("tx_desLar"));
                    arlDat.add(arlReg);
                }
                stm.close();
                rst.close();
                stm=null;
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   private boolean agregarColTblDat(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        
        
        try{
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);
            
            int intConTmp=0;
            int intLeeDatArlTitCab=0;
            for (int i=0; i<intNumFilAddBco; i++){
                tbc=new javax.swing.table.TableColumn(numColModIni+i);
                tbc.setHeaderValue(" " + strVecDat[i][1] + " ");
                strVecDat[i][2]=String.valueOf((numColModIni+i));
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                
                objTblCelEdiChkVisBue=new ZafTblCelEdiChk(tblDat);
                tbc.setCellEditor(objTblCelEdiChkVisBue);

                //Configurar JTable: Agregar la columna al JTable.
                
                objTblMod.addColumn(tblDat, tbc);
                if(intConTmp==1){
                    ZafTblHeaColGrp objTblHeaColGrp=null;
                    objTblHeaColGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatTitCabChk, intLeeDatArlTitCab, INT_ARL_NOM_VIS_BUE));
                    objTblHeaColGrp.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                    
                    objTblHeaColGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-2)));
                    objTblHeaColGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));
                    intConTmp=0;
                    intLeeDatArlTitCab++;
                }
                else
                    intConTmp++;
                
                objTblCelEdiChkVisBue.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strCodEmp="";
                String strCodLoc="";
                String strCodTipDoc="";
                String strCodDoc="";
                boolean blnValChkSel;
                    
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i = tblDat.getSelectedRow();
                    int x = tblDat.getSelectedColumn();
                    blnValChkSel= (tblDat.getValueAt(i, x)==null?false:(tblDat.getValueAt(i, x).toString().equals("true")?true:false));
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    if(objParSis.getCodigoUsuario()==1){
//
//                    }
//                    else{//esta validado por si tiene o no para conceder visto bueno a traves del programa q esta en maestros-tipos de documentos-configuracion de vistos buenos por tipo de documentos
                            if(objTblMod.isChecked(tblDat.getSelectedRow(), tblDat.getSelectedColumn())){
                                int intProChkSel=isEditable_Anterior(tblDat.getSelectedRow(), tblDat.getSelectedColumn());
        //                        System.out.println("CHECK: " + intProChkSel);
                                String strEstVisBue="";
                                switch(intProChkSel){
                                    case 0:
                                        mostrarMsgInf("<HTML>Para poder seleccionar debe ser aprobado el visto bueno anterior.</HTML>");
                                        objTblMod.setChecked(false, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                                        objTblCelEdiChkVisBue.setCancelarEdicion(true);
                                        break;
                                    case 1:
                                        strCodEmp    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP);
                                        strCodLoc    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC);
                                        strCodTipDoc ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC);
                                        strCodDoc    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC);
                                        if(    puedeModificarAprRepOtroUsuario(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, ""+getCodigoVistoBueno(tblDat.getSelectedColumn()), blnValChkSel, tblDat.getSelectedRow(), tblDat.getSelectedColumn())    ){
                                        }
                                        else{
                                            if(isUnicoSelCodVisBue(tblDat.getSelectedRow(), tblDat.getSelectedColumn())){
                                                objTblCelEdiChkVisBue.setCancelarEdicion(false);
                                                strEstVisBue=getEstadoVistoBueno(tblDat.getSelectedColumn());
                                                if(guardaDatosSeleccionadosArrayTmp(tblDat.getSelectedRow(), tblDat.getSelectedColumn(), strEstVisBue, "I" )){
                                                }
                                            }
                                        }
                                        break;
                                    case 2:
                                        mostrarMsgInf("<HTML>El visto bueno anterior fue reprobado.<BR>No puede aprobar/reprobar un documento que ya ha sido reprobado</HTML>");
                                        objTblMod.setChecked(false, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                                        objTblCelEdiChkVisBue.setCancelarEdicion(true);
                                        break;
                                    default:
                                        strCodEmp    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP);
                                        strCodLoc    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC);
                                        strCodTipDoc ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC);
                                        strCodDoc    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC);
                                        if(    puedeModificarAprRepOtroUsuario(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, ""+getCodigoVistoBueno(tblDat.getSelectedColumn()), blnValChkSel, tblDat.getSelectedRow(), tblDat.getSelectedColumn())    ){
                                        }
                                        else{
                                            if(isUnicoSelCodVisBue(tblDat.getSelectedRow(), tblDat.getSelectedColumn())){
                                                objTblCelEdiChkVisBue.setCancelarEdicion(false);
                                                strEstVisBue=getEstadoVistoBueno(tblDat.getSelectedColumn());
                                                if(guardaDatosSeleccionadosArrayTmp(tblDat.getSelectedRow(), tblDat.getSelectedColumn(), strEstVisBue, "I" )){
                                                }
                                            }
                                        }
                                        break;
                                }
                            }
                            else{

                                if(   isEditable_Siguiente(tblDat.getSelectedRow(), tblDat.getSelectedColumn(), blnValChkSel)  ){
                                    strCodEmp    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP);
                                    strCodLoc    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC);
                                    strCodTipDoc ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC);
                                    strCodDoc    ="" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC);
                                    if(    puedeModificarAprRepOtroUsuario(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, ""+getCodigoVistoBueno(tblDat.getSelectedColumn()), blnValChkSel, tblDat.getSelectedRow(), tblDat.getSelectedColumn())    ){
                                    }
                                    String strEstVisBue="";
                                    strEstVisBue=getEstadoVistoBueno(tblDat.getSelectedColumn());
                                    if(guardaDatosSeleccionadosArrayTmp(tblDat.getSelectedRow(), tblDat.getSelectedColumn(), strEstVisBue, "E" )){
            //                            System.out.println("EL ARRAYLIST CUANDO CHK ES FALSE CONTIENE    : " + arlDatCodVisBueSel.toString());
                                    }
                                }
                            }
//                    }








                }
            });
            }            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDat);
            objTblCelRenLbl=null;
            numColModAdd=tblDat.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }        
                        
        return blnRes;
    }

   private boolean configurarColAddModDat(){
       boolean blnRes=true;
       try{
           con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(con!=null){
               if(eliminaColTblDatAdicionadas()){
                   if( (generaNumColBancos()) ){
                       if(getTituloCabeceraTabla()){
                           if(agregarColTblDat()){
                               objTblMod.removeAllRows();
                           }    
                       }
                   }
               }
               con.close();
               con=null;
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
      
   private boolean eliminaColTblDatAdicionadas(){
        boolean blnRes=true;
        int numColModFin=tblDat.getColumnCount();
        try{
            for (int i=(numColModFin-1); i>=numColModIni; i--){
                //tblDat.removeColumn(tblDat.getColumnModel().getColumn(i));
                objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    } 
    
   private boolean guardarDatos(){
       boolean blnRes=true;
       try{
           con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           con.setAutoCommit(false);
           if(con!=null){
               if(deleteTbmVisBueMovInv()){
                   if(insertarTbmVisBueMovInv()){
                       con.commit();
                   }
                   else{
                       con.rollback();
                       blnRes=false;
                   }
               }
               else{
                   con.rollback();
                   blnRes=false;
               }
               con.close();
               con=null;
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
   
   private boolean getTituloCabeceraTabla(){
       boolean blnRes=true;
       arlDatTitCabChk.clear();
       try{
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+=" SELECT co_visBue, CAST('Visto Bueno '||co_visBue AS CHARACTER VARYING) AS tx_heaTbl";
               strSQL+=" FROM tbr_visBueUsrTipDoc ";
               strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND st_reg='A'";
               strSQL+=" GROUP BY co_visBue ";
               strSQL+=" ORDER BY co_visBue";
               rst=stm.executeQuery(strSQL);
               while(rst.next()){
                   arlRegTitCabChk=new ArrayList();
                   arlRegTitCabChk.add(INT_ARL_COD_VIS_BUE, "" + rst.getString("co_visBue"));
                   arlRegTitCabChk.add(INT_ARL_NOM_VIS_BUE, "" + rst.getString("tx_heaTbl"));
                   arlDatTitCabChk.add(arlRegTitCabChk);
               }
               stm.close();
               stm=null;
               rst.close();
               rst=null;
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
   
   private boolean numVistosBuenosTipoDcto(){
       boolean blnRes=true;
       intNumVisBueTipDoc=0;
       try{
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+=" SELECT COUNT(DISTINCT(co_visBue)) AS ne_numVisBue, co_emp, co_loc, co_tipDoc";
               strSQL+=" FROM tbr_visBueUsrTipDoc";
               strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND st_reg='A'";
               strSQL+=" GROUP BY co_emp, co_loc, co_tipDoc";
               rst=stm.executeQuery(strSQL);
               while(rst.next()){
                   intNumVisBueTipDoc=rst.getInt("ne_numVisBue");
               }
               setIntNumVisBueTipDoc(intNumVisBueTipDoc);
               stm=null;
               rst.close();
               rst=null;
           }
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }

   public int getIntNumVisBueTipDoc() {
       return intNumVisBueTipDoc;
   }
   public void setIntNumVisBueTipDoc(int intNumVisBueTipDoc) {
       this.intNumVisBueTipDoc = intNumVisBueTipDoc;
   }
   
   private boolean numVistosBuenosUsuario(){
       boolean blnRes=true;
       intNumVisBueUsu=0;
       try{
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+=" SELECT COUNT(DISTINCT(co_visBue)) AS ne_numVisBue, co_emp, co_loc, co_tipDoc";
               strSQL+=" FROM tbr_visBueUsrTipDoc";
               strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
               strSQL+=" AND st_reg='A'";
               strSQL+=" GROUP BY co_emp, co_loc, co_tipDoc";
               rst=stm.executeQuery(strSQL);
               while(rst.next()){
                   intNumVisBueUsu=rst.getInt("ne_numVisBue");
               }
               setIntNumVisBueUsu(intNumVisBueUsu);
               stm=null;
               rst.close();
               rst=null;
           }
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return blnRes;
   }

   public int getIntNumVisBueUsu() {
        return intNumVisBueUsu;
    }
   public void setIntNumVisBueUsu(int intNumVisBueUsu) {
        this.intNumVisBueUsu = intNumVisBueUsu;
    }
   
   private boolean getVistosBuenosUsuario(){
       boolean blnRes=true;
       arlDatVisBueUsr.clear();
       try{
           //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+=" SELECT co_visBue, st_necVisBuePre";
               strSQL+=" FROM tbr_visBueUsrTipDoc";
               strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
               strSQL+=" AND st_reg='A'";
               rst=stm.executeQuery(strSQL);
               while(rst.next()){
                   //guardarlo en un arraylist
                   arlRegVisBueUsr=new ArrayList();
                   arlRegVisBueUsr.add(INT_ARL_COD_VIS_BUE_USR, "" + rst.getString("co_visBue"));
                   arlRegVisBueUsr.add(INT_ARL_EST_VIS_BUE_USR, "" + rst.getString("st_necVisBuePre"));
                   arlDatVisBueUsr.add(arlRegVisBueUsr);
               }
               stm=null;
               rst.close();
               rst=null;
               //con.close();
               //con=null;
           }
           
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }

   private boolean getVistosBuenosTipoDocumentos(){
       boolean blnRes=true;
       arlDatVisBueTipDoc.clear();
       try{
           con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+=" SELECT co_visBue, st_necVisBuePre";
               strSQL+=" FROM tbr_visBueUsrTipDoc";
               strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND st_reg='A'";
               rst=stm.executeQuery(strSQL);
               while(rst.next()){
                   //guardarlo en un arraylist
                   arlRegVisBueTipDoc=new ArrayList();
                   arlRegVisBueTipDoc.add(INT_ARL_COD_VIS_BUE_TIP_DOC, "" + rst.getString("co_visBue"));
                   arlRegVisBueTipDoc.add(INT_ARL_EST_VIS_BUE_TIP_DOC, "" + rst.getString("st_necVisBuePre"));
                   arlDatVisBueTipDoc.add(arlRegVisBueTipDoc);
               }
               stm=null;
               rst.close();
               rst=null;
               con.close();
               con=null;
           }
           
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   private boolean validaVistosBuenos(){
       boolean blnRes=true;
       int intValVisBueUsr=getIntNumVisBueTipDoc();
       int intValVisBueTipDoc=getIntNumVisBueUsu();
       getVistosBuenosUsuario();
       int w=0;
       int intArlCodVisBue=0, intVecCodVisBue=0, intVecColEdi=0;
       String strArlEstVisBue="";
       int intArlCodVisBueTipDoc=0;
       try{
           if(intValVisBueUsr<=intValVisBueTipDoc){
               //aqui debo verificar q el codigo de visto bueno sea el adecuado, es decir q se va a ejecutar si y solo si los codigos de
               //vistos buenos anteriores dependientes ya han sido aprobados, si fue reprobado no tiene caso q se pueda aprobar el sgt
               //visto bueno, asi q no se va a permitir aprobar este Visto Bueno :)
               vecAux=new Vector();
               for(int i=0;i<arlDatVisBueUsr.size(); i++){
                   strArlEstVisBue=objUti.getStringValueAt(arlDatVisBueUsr, i, INT_ARL_EST_VIS_BUE_USR);
                   if(strArlEstVisBue.equals("S")){
                       intArlCodVisBue=objUti.getIntValueAt(arlDatVisBueUsr, i, INT_ARL_COD_VIS_BUE_USR);
                       do{
                          intVecCodVisBue=Integer.parseInt(strVecDat[w][0]);
                          if(intArlCodVisBue==intVecCodVisBue){
                              intVecColEdi=Integer.parseInt(strVecDat[w][2]);
                              vecAux.add("" + intVecColEdi);
                              objTblEdi=new ZafTblEdi(tblDat);
                              objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                          }
                          w++;
                       }while(strVecDat[w][0]!=null);
                       w=0;
                   }
                   
                   //nuevo
                   else{
                        intArlCodVisBue=objUti.getIntValueAt(arlDatVisBueUsr, i, INT_ARL_COD_VIS_BUE_USR);
                       do{
                          intVecCodVisBue=Integer.parseInt(strVecDat[w][0]);
                          if(intArlCodVisBue==intVecCodVisBue){
                              intVecColEdi=Integer.parseInt(strVecDat[w][2]);
                              vecAux.add("" + intVecColEdi);
                              objTblEdi=new ZafTblEdi(tblDat);
                              objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                          }
                          w++;
                       }while(strVecDat[w][0]!=null);
                       w=0;
                   }
                   
                   
                   

               }
               //Configurar JTable: Editor de la tabla.
               objTblMod.setColumnasEditables(vecAux);
               vecAux=null;
           }
//           if(intValVisBueUsr<intValVisBueTipDoc){
//               vecAux=new Vector();
//               for(int i=0;i<arlDatVisBueUsr.size(); i++){
//                   strArlEstVisBue=objUti.getStringValueAt(arlDatVisBueUsr, i, INT_ARL_EST_VIS_BUE_USR);
//                   if(strArlEstVisBue.equals("S")){
//                       intArlCodVisBue=objUti.getIntValueAt(arlDatVisBueUsr, i, INT_ARL_COD_VIS_BUE_USR);
//                       do{
//                          intVecCodVisBue=Integer.parseInt(strVecDat[w][0]);
//                          if(intArlCodVisBue==intVecCodVisBue){
//                              intVecColEdi=Integer.parseInt(strVecDat[w][2]);
//                              vecAux.add("" + intVecColEdi);
//                              objTblEdi=new ZafTblEdi(tblDat);
//                              objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
//                          }
//                          w++;
//                       }while(strVecDat[w][0]!=null);
//                       w=0;
//                   }
//               }
//               //Configurar JTable: Editor de la tabla.
//               objTblMod.setColumnasEditables(vecAux);
//               vecAux=null;
//           }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   private int isEditable_Anterior(int fila, int columna){
       int intFil=fila;
       int intCol=columna;
       int w=0, ww=0;
       int intVecCol=0;
       int intVecCodVisBueAct=0;
       int intVecCodVisBueAnt=0;
       int intVecCodVisBue=0, intVecColVisBueAnt=0;
       int intVarRet=99;
       
       int intLaz=0;
       int intIniDosColApr=0, intIniDosColRep=0;
       
       try{
           do{
               intVecCol=Integer.parseInt(strVecDat[w][2]);
               if(intCol==intVecCol){
                   intVecCodVisBueAct=Integer.parseInt(strVecDat[(w)][0]);
                   if(w>1){
                       intVecCodVisBueAnt=Integer.parseInt(strVecDat[(w-1)][0]);
                       if(intVecCodVisBueAct==intVecCodVisBueAnt){
                           w--;
                           intVecCodVisBueAnt=Integer.parseInt(strVecDat[(w-1)][0]);
                           break;
                       }
                       break;
                   }
                   break;
               }
               else
                   w++;
           }while(  (strVecDat[w][0]!=null) );
           
           w=0;
           
           if(intVecCodVisBueAnt!=0){
               //aqui se encuentran cuales son las columnas relacionadas con ese codigo de visto bueno               
               do{
                   intVecCodVisBue=Integer.parseInt(strVecDat[w][0]);
                   if(intVecCodVisBue==intVecCodVisBueAnt){
                       intVecColVisBueAnt=Integer.parseInt(strVecDat[w][2]);
                       if(   (objTblMod.isChecked(intFil, intVecColVisBueAnt)) && (intLaz==0)  ){
                           //objTblCelEdiChkVisBue.setCancelarEdicion(false);
                           intIniDosColApr++;
                           intLaz=1;
                           break;
                       }
                       else
                           intLaz=2;
                           
                       w++;
                       intVecColVisBueAnt=Integer.parseInt(strVecDat[w][2]);
                       if(   (objTblMod.isChecked(intFil, intVecColVisBueAnt))  && (intLaz==2)  ){
                           intIniDosColRep++;
                           break;
                       }
                       
                       if(   (intIniDosColApr==0) &&(intIniDosColRep==0)  ){
                           intLaz=99;
                           break;
                       }
                   }
                   else
                        w++;
               }while(  (strVecDat[w][0]!=null)  ||  (intLaz<2)  );
               
               if( (intIniDosColApr==0) && (intIniDosColRep==0) ){
                   String strTmp=getNecesitaVistosBuenosPrevios(intVecCodVisBueAct);
                   if(strTmp.equals("S"))
                       intVarRet=0;
               }
               else if(   intIniDosColApr!=0) {
                   intVarRet=1;
               }
               else if(intIniDosColRep!=0){
                   intVarRet=2;
               }
               intIniDosColApr=0;
               intIniDosColRep=0;
           }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intVarRet;
   }
      
   private boolean isEditable_Siguiente(int fila, int columna, boolean datoPrevio){
       boolean blnRes=true;
       int intFil=fila;
       int intCol=columna;
       int w=0, ww=0;
       int intVecCol=0;
       int intVecCodVisBueAct=0;
       int intVecCodVisBueSig=0;
       int intVecCodVisBue=0, intVecColVisBueSig=0;
       
       int intLaz=0;
       int intIniDosColApr=0, intIniDosColRep=0;
       boolean blnDatChk=datoPrevio;
       
       try{
           do{
               intVecCol=Integer.parseInt(strVecDat[w][2]);
               if(intCol==intVecCol){
                   intVecCodVisBueAct=Integer.parseInt(strVecDat[(w)][0]);
                   if(strVecDat[(w+1)][2]!=null){
                       intVecCodVisBueSig=Integer.parseInt(strVecDat[(w+1)][0]);
                       if(intVecCodVisBueAct==intVecCodVisBueSig){
                           w++;
                           if(strVecDat[w][2]!=null){
                               if(strVecDat[w+1][2]!=null){
                                   intVecCodVisBueSig=Integer.parseInt(strVecDat[(w+1)][0]);
                               }
                               break;
                           }
                       }
                   }
                   break;
               }
               else
                   w++;
           }while(  (strVecDat[w][0]!=null) );
           
           w=0;
           
           if(intVecCodVisBueSig!=0){
               //aqui se encuentran cuales son las columnas relacionadas con ese codigo de visto bueno               
               do{
                   intVecCodVisBue=Integer.parseInt(strVecDat[w][0]);
                   if(intVecCodVisBue==intVecCodVisBueSig){
                       intVecColVisBueSig=Integer.parseInt(strVecDat[w][2]);
                       if(   (objTblMod.isChecked(intFil, intVecColVisBueSig)) && (intLaz==0)  ){
                           //objTblCelEdiChkVisBue.setCancelarEdicion(false);
                           intIniDosColApr++;
                           intLaz=1;
                           break;
                       }
                       else
                           intLaz=2;
                           
                       w++;
                       if(strVecDat[w][2]!=null){
                           intVecColVisBueSig=Integer.parseInt(strVecDat[w][2]);
                           if(   (objTblMod.isChecked(intFil, intVecColVisBueSig))  && (intLaz==2)  ){
                               intIniDosColRep++;
                               break;
                           }                           
                       }
                   }
                   else
                        w++;
               }while(  (strVecDat[w][0]!=null)  ||  (intLaz<2)  );
               
               if( (intIniDosColApr==0) && (intIniDosColRep==0) ){
               }
               else{
                   blnRes=false;
                   objTblMod.setChecked(blnDatChk, intFil, intCol);
                   mostrarMsgInf("<HTML>El visto bueno seleccionado no se puede desmarcar porque existen visto buenos posteriores.<BR>Desmarque el visto bueno siguiente para poder realizar el proceso.</HTML>");
               }
           }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   private boolean isUnicoSelCodVisBue(int fila, int columna){
       boolean blnRes=true;
       int intFil=fila;
       int intCol=columna;
       int w=0;
       int intVecCodVisBueAct=0, intVecCodVisBueAnt=0, intVecCodVisBueSig=0;
       int intVecColVisBueAct=0, intVecColVisBueAnt=0, intVecColVisBueSig=0;
       
       String strTblColVisBueAct="";
       String strTblColVisBueAnt="";
       String strTblColVisBueSig="";
              
       
       if( objTblMod.isChecked(intFil, intCol) )
               strTblColVisBueAct="S";
       else
           strTblColVisBueAct="N";
       
       if( (intCol-1)>0 ){
           if( objTblMod.isChecked(intFil, (intCol-1)) )
               strTblColVisBueAnt="S";
           else
               strTblColVisBueAnt="N";
       }
       
       if( (intCol+1)<= (objTblMod.getColumnCount()-1) ){
           if( objTblMod.isChecked(intFil, (intCol+1)) )
               strTblColVisBueSig="S";
           else
               strTblColVisBueSig="N";
       }
       
       try{
           do{
               intVecColVisBueAct=Integer.parseInt(strVecDat[w][2]);
               intVecCodVisBueAct=Integer.parseInt(strVecDat[w][0]);
               
               if(intCol==intVecColVisBueAct){
                   intVecCodVisBueAct=Integer.parseInt(strVecDat[(w)][0]);
                   break;
               }
               else
                   w++;
           }while(  (strVecDat[w][0]!=null) );
           w=0;
           
/////////////////////////////////////////////////////////////////////////////
           do{
               intVecColVisBueAnt=Integer.parseInt(strVecDat[w][2]);
               if((intCol-1)==intVecColVisBueAnt){
                   intVecCodVisBueAnt=Integer.parseInt(strVecDat[(w)][0]);
                   break;
               }
               else
                   w++;
           }while(  (strVecDat[w][0]!=null) );
           w=0;
/////////////////////////////////////////////////////////////////////////////
           do{
               intVecColVisBueSig=Integer.parseInt(strVecDat[w][2]);
               if((intCol+1)==intVecColVisBueSig){
                   intVecCodVisBueSig=Integer.parseInt(strVecDat[(w)][0]);
                   break;
               }
               else
                   w++;
           }while(  (strVecDat[w][0]!=null) );           
           
           
           //////////////////////////
           if(intVecCodVisBueAct==intVecCodVisBueAnt){
               if( strTblColVisBueAnt.equals("S") ){
                   mostrarMsgInf("<HTML>La selección no se puede realizar porque el visto bueno seleccionado ya fue Aprobado.<BR>Verifique su selección y vuelva a intentarlo.</HTML>");
                   objTblMod.setChecked(false, intFil, intCol);
                   blnRes=false;
               }
               
           }
           
           if(intVecCodVisBueAct==intVecCodVisBueSig){
               if( strTblColVisBueSig.equals("S") ){
                   mostrarMsgInf("<HTML>La selección no se puede realizar porque el visto bueno seleccionado ya fue Reprobado.<BR>Verifique su selección y vuelva a intentarlo.</HTML>");
                   objTblMod.setChecked(false, intFil, intCol);
                   blnRes=false;
               }
           }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
       
   
   private boolean insertarTbmVisBueMovInv(){
       boolean blnRes=true;
       String strEstArl="";
       try{
           if(con!=null){
               stm=con.createStatement();
               strQueIns="";
               
               
               for(int i=0; i<arlDatCodVisBueSel.size(); i++){
                   strEstArl=objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_EST_INS);
                   if(! strEstArl.equals("E")){
                       strSQL="";
                       strSQL+="INSERT INTO tbm_visbuemovinv(";
                       strSQL+="            co_emp, co_loc, co_tipdoc, co_doc, co_visbue, co_usr, st_reg, ";
                       strSQL+="            st_regrep)";
                       strSQL+="    VALUES (";
                       strSQL+="" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_EMP) + ",";//co_emp
                       strSQL+="" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_LOC) + ",";//co_loc
                       strSQL+="" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_TIP_DOC) + ",";//co_tipdoc
                       strSQL+="" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_DOC) + ",";//co_doc
                       strSQL+="" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_SEL_COD_VIS_BUE) + ",";//co_visbue
                       strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usr
                       strSQL+="'" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_SEL_EST_VIS_BUE) + "',";//st_reg
                       strSQL+="'I'";//st_regrep
                       strSQL+=");";
                       strQueIns+=strSQL;
                   }
               }
               System.out.println("SQL DE INSERT: " + strQueIns);
               stm.executeUpdate(strQueIns);
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
   
   private boolean deleteTbmVisBueMovInv(){
       boolean blnRes=true;
       strQueIns="";
//       String strEstArl="";
       try{
           stm=con.createStatement();
           for(int i=0; i<arlDatCodVisBueSel.size(); i++){
//               strEstArl=objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_EST_INS);
//               if(! strEstArl.equals("E")){
                   strSQL="";
                   strSQL+="DELETE FROM tbm_visbuemovinv";
                   strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_EMP) + "";
                   strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_LOC) + "";
                   strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_TIP_DOC) + "";
                   strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_COD_DOC) + "";
                   strSQL+=" AND co_visBue=" + objUti.getStringValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_SEL_COD_VIS_BUE) + "";
                   strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + ";";
                   strQueIns+=strSQL;
//               }
           }
           System.out.println("deleteTbmVisBueMovInv: " + strQueIns);
           stm.executeUpdate(strQueIns);
           stm.close();
           stm=null;
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   private boolean guardaDatosSeleccionadosArrayTmp(int fila, int columna, String aprob_reprob, String estadoRegistroArreglo){
       
       boolean blnRes=true;
       int intFil=fila;
       int intCol=columna;
       String strEstDoc=aprob_reprob;
       int intCodVisBue=getCodigoVistoBueno(intCol);
       //almacenan los datos para verificar si los datos ya existen en el arraylist y solo se modifican columna y estado, sino se inserta
       int intFilExi=-1;
       int intCodVisBueExi=-1;
       String strDo="I";
       
       //en el arraylist tomar los valores de la fila
       int intFilArl=-1;
       String strEstRegArl=estadoRegistroArreglo;
       System.out.println("guardaDatosSeleccionadosArrayTmp: " + arlDatCodVisBueSel.toString());
       try{
           if(intCodVisBue!=0){
               //verificar q los datos q se desean ingresar en el arraylist ya no esten ingresados sino modifcar los ya ingresados
               for(int i=0; i<arlDatCodVisBueSel.size(); i++){
                   intFilExi=objUti.getIntValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_SEL_FIL);
                   intCodVisBueExi=objUti.getIntValueAt(arlDatCodVisBueSel, i, INT_ARL_VIS_BUE_SEL_COD_VIS_BUE);
                   if(   (intFilExi==intFil)  &&  (intCodVisBueExi==intCodVisBue)   ){
                       strDo="M";
                       intFilArl=i;
                       break;
                   }
               }
               if(strDo.equals("I")){
                   arlRegCodVisBueSel=new ArrayList();
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_SEL_FIL,          "" + intFil);
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_SEL_COL,          "" + intCol);
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_SEL_COD_VIS_BUE,  "" + intCodVisBue);
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_SEL_EST_VIS_BUE,  "" + strEstDoc);
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_EST_INS,          "" + strEstRegArl);
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_COD_EMP,          "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP));
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_COD_LOC,          "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC));
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_COD_TIP_DOC,      "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC));
                   arlRegCodVisBueSel.add(INT_ARL_VIS_BUE_COD_DOC,          "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC));
                                      
                   arlDatCodVisBueSel.add(arlRegCodVisBueSel);
               }
               if(strDo.equals("M")){
                   objUti.setStringValueAt(arlDatCodVisBueSel, intFilArl, INT_ARL_VIS_BUE_SEL_COL, intCol);
                   objUti.setStringValueAt(arlDatCodVisBueSel, intFilArl, INT_ARL_VIS_BUE_SEL_EST_VIS_BUE, strEstDoc);
               }
               objUti.setStringValueAt(arlDatCodVisBueSel, intFilArl, INT_ARL_VIS_BUE_EST_INS, strEstRegArl);
               System.out.println("arlDatCodVisBueSel: " + arlDatCodVisBueSel.toString());
           }
           else{
               blnRes=false;
           }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
      
   //la siguiente funcion permite obtener el codigo de visto bueno de acuerdo a la columna seleccionada
   //esta informacion se obtiene del string del vector de datos.
   private int getCodigoVistoBueno(int columna){
       int intCodVisBue=0;
       int w=0;
       int intCol=columna;
       int intVecCol=0;
       try{
           do{
               intVecCol=Integer.parseInt(strVecDat[w][2]);
               if(intCol==intVecCol){
                   intCodVisBue=Integer.parseInt(strVecDat[w][0]);
                   break;
               }
               else{
                   w++;
               }
           }while(strVecDat[w][2]!=null);
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           
       }
       return intCodVisBue;
   }
   
   private String getEstadoVistoBueno(int columna){
       String strEstVisBue="";
       int intCol=columna;
       int intVecCol=-1, intVecCodVisBue=-1;
       int intVecColAnt=-1, intVecAntCodVisBue=-1;
       int w=0;
           do{
               intVecCol=Integer.parseInt(strVecDat[w][2]);
               intVecCodVisBue=Integer.parseInt(strVecDat[w][0]);
               if(intCol==intVecCol){
                   //verificar si la columna anterior tiene el mismo codigo de visto bueno-->reprobado
                   if(w>0){
                       intVecColAnt=Integer.parseInt(strVecDat[(w-1)][2]);
                       intVecAntCodVisBue=Integer.parseInt(strVecDat[(w-1)][0]);
                       if(intVecAntCodVisBue==intVecCodVisBue)
                           strEstVisBue="D";
                       else
                           strEstVisBue="A";
                   }
                   else{
                       strEstVisBue="A";
                   }
                   break;
               }
               else{
                   w++;
               }
           }while(strVecDat[w][2]!=null);
       return strEstVisBue;
   }
   
   private boolean cargarArregloVistosBuenosDB(){
       boolean blnRes=true;
       arlDatCodVisBueDB.clear();
       try{
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+="SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue, b1.st_reg, b1.co_usr";
               strSQL+=" FROM tbm_visBueMovInv AS b1";
               strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND b1.co_tipDoc=" + txtCodTipDoc.getText() + "";
//               strSQL+=" AND b1.co_usr=" + objParSis.getCodigoUsuario() + "";
               strSQL+=" ORDER BY b1.co_doc, b1.co_visBue";
               System.out.println("cargarArregloVistosBuenosDB: " + strSQL);
               rst=stm.executeQuery(strSQL);
               while(rst.next()){
                   arlRegCodVisBueDB=new ArrayList();
                   arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_EMP_DB,     "" + rst.getString("co_emp"));
                   arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_LOC_DB,     "" + rst.getString("co_loc"));
                   arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB, "" + rst.getString("co_tipDoc"));
                   arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_DOC_DB,     "" + rst.getString("co_doc"));
                   arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB, "" + rst.getString("co_visBue"));
                   arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB, "" + rst.getString("st_reg"));
                   arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_USR_DB,     "" + rst.getString("co_usr"));
                   arlDatCodVisBueDB.add(arlRegCodVisBueDB);
               }
               stm.close();
               stm=null;
               rst.close();
               rst=null;
           }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   
   //usuario 1 puede APROBAR o REPROBAR este codigo de visto bueno
   //usuario 2 tambien puede APROBAR o REPOBRAR el mismo codigo de visto bueno
   //si el usuario 2 ya APROBO o REPROBO el codigo de visto bueno, por mas q el usuario 1 tenga permisos para ese
        //codigo, no lo va a poder modificar a no ser q el usuario 2 reverse la APROBACION o REPROBACION.
   private boolean puedeModificarAprRepOtroUsuario(String empresa, String local, String tipoDocumento, String codigoDocumento, String codigoVistoBueno, boolean checkSeleccionado, int fila, int columna){
       boolean blnRes=false;
       String strArlVisBueDbCodEmp="", strArlVisBueDbCodLoc="", strArlVisBueDbCodTipDoc="", strArlVisBueDbCodDoc="";
       String strArlVisBueDbCodVisBue="", strArlVisBueDbEstVisBue="", strArlVisBueDbCodUsr="";
       String strTblVisBueDbCodEmp=empresa;
       String strTblVisBueDbCodLoc=local;
       String strTblVisBueDbCodTipDoc=tipoDocumento;
       String strTblVisBueDbCodDoc=codigoDocumento;
       String strTblVisBueDbCodVisBue=codigoVistoBueno;
       boolean blnChkSel=checkSeleccionado;
       int intFil=fila;
       int intCol=columna;
       int w=0;
       //System.out.println("puedeModificarAprRepOtroUsuario: " + arlDatCodVisBueDB.toString());
       try{
            for(int k=0; k<arlDatCodVisBueDB.size(); k++){
                w=0;
                strArlVisBueDbCodEmp=objUti.getStringValueAt(arlDatCodVisBueDB,     k, INT_ARL_VIS_BUE_DB_COD_EMP_DB);
                strArlVisBueDbCodLoc=objUti.getStringValueAt(arlDatCodVisBueDB,     k, INT_ARL_VIS_BUE_DB_COD_LOC_DB);
                strArlVisBueDbCodTipDoc=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB);
                strArlVisBueDbCodDoc=objUti.getStringValueAt(arlDatCodVisBueDB,     k, INT_ARL_VIS_BUE_DB_COD_DOC_DB);
                strArlVisBueDbCodVisBue=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB)==null?"":objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB);
                strArlVisBueDbEstVisBue=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB)==null?"":objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB);
                strArlVisBueDbCodUsr=objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_USR_DB)==null?"":objUti.getStringValueAt(arlDatCodVisBueDB,  k, INT_ARL_VIS_BUE_DB_COD_USR_DB);

                if(strTblVisBueDbCodEmp.equals(strArlVisBueDbCodEmp)){
                    if(strTblVisBueDbCodLoc.equals(strArlVisBueDbCodLoc)){
                        if(strTblVisBueDbCodTipDoc.equals(strArlVisBueDbCodTipDoc)){
                            if(strTblVisBueDbCodDoc.equals(strArlVisBueDbCodDoc)){
                                if(strArlVisBueDbCodVisBue.equals(strTblVisBueDbCodVisBue)){
                                    if(  (strArlVisBueDbEstVisBue.equals("A"))  || (strArlVisBueDbEstVisBue.equals("D")) ){
                                        if(! strArlVisBueDbCodUsr.equals(""+objParSis.getCodigoUsuario())){
                                            objTblMod.setChecked(blnChkSel, intFil, intCol);
                                            mostrarMsgInf("<HTML>El visto bueno que intenta modificar ya fue Aprobado/Reprobado por otro usuario.<BR>El otro usuario debe reversar para que ud. pueda realizar la Aprobación/Reprobación,<BR> o dicho usuario deberá modificarlo.  </HTML>");
                                            blnRes=true;
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return blnRes;
   }
   
   private String getNecesitaVistosBuenosPrevios(int codigoVistoBueno){
       String strNecVisBuePre="";
       Connection conNecVisBuePre;
       Statement stmNecVisBuePre;
       ResultSet rstNecVisBuePre;
       int intCodVisBueRef=codigoVistoBueno;
       try{
           conNecVisBuePre=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(conNecVisBuePre!=null){
               stmNecVisBuePre=conNecVisBuePre.createStatement();
               strSQL="";
               strSQL+="SELECT st_necVisBuePre FROM tbr_visBueUsrTipDoc";
               strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND co_visBue=" + intCodVisBueRef + "";
               strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
               strSQL+=" AND st_reg='A'";
               rstNecVisBuePre=stmNecVisBuePre.executeQuery(strSQL);
               if(rstNecVisBuePre.next()){
                   strNecVisBuePre=rstNecVisBuePre.getString("st_necVisBuePre");
               }
               stmNecVisBuePre.close();
               stmNecVisBuePre=null;
               rstNecVisBuePre.close();
               rstNecVisBuePre=null;
               conNecVisBuePre.close();
               conNecVisBuePre=null;
           }
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return strNecVisBuePre;

               
               
               
   }
   

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt){
        int i, intNumFil;
        int intColSel=tblDat.getSelectedColumn();
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intColSel)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, intColSel);
                        objTblMod.setValueAt("M", i, INT_TBL_DAT_LIN);
                        if(guardaDatosSeleccionadosArrayTmp(i, intColSel, getEstadoVistoBueno(tblDat.getSelectedColumn()), "I" )){
                        }
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, intColSel);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    






}
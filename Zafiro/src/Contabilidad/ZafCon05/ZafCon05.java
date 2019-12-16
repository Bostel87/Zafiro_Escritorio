/*
 * ZafCon04.java
 *
 * Created on 02 de noviembre de 2005, 11:25 PM
 * DARIO CARDENAS MODIFICO EL 30/03/2007
 * Se han realizado modificaciones posteiores por Ing. Ingrid Lino
 */
package Contabilidad.ZafCon05;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafPulFacEle.ZafPulFacEle;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.Vector;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.File;
   
/**
 *
 * @author  Eddye Lino
 */
public class ZafCon05 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;                        //Línea.
    final int INT_TBL_DAT_CHK=1;                        //Casilla de verificación.
    final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    final int INT_TBL_DAT_COD_TIP_DOC=3;                //Código del tipo de documento.
    final int INT_TBL_DAT_DEC_TIP_DOC=4;                //Descripción corta del tipo de documento.
    final int INT_TBL_DAT_DEL_TIP_DOC=5;                //Descripción larga del tipo de documento.
    final int INT_TBL_DAT_COD_DOC=6;                    //Código del documento (Sistema).
    final int INT_TBL_DAT_COD_REG=7;                    //Código del registro (Sistema).
    final int INT_TBL_DAT_NUM_DOC=8;                    //Número del documento.
    final int INT_TBL_DAT_FEC_DOC=9;                    //Fecha del documento.
    final int INT_TBL_DAT_COD_TIP_RET=10;               //Código del tipo de retención.
    final int INT_TBL_DAT_DEC_TIP_RET=11;               //Descripción corta del tipo de retención.
    final int INT_TBL_DAT_BUT_TIP_RET=12;               //Botón de consulta de Tipos de retenciones.
    final int INT_TBL_DAT_DEL_TIP_RET=13;               //Descripción larga del tipo de retención.
    final int INT_TBL_DAT_POR_RET=14;                   //Porcentaje de retención.
    final int INT_TBL_DAT_APL_RET=15;                   //Aplicar retencióna a "Subtotal" o "IVA".
    final int INT_TBL_DAT_BAS_IMP=16;                   //Base imponible. Puede ser el "Subtotal" o "IVA" según sea el caso.
    final int INT_TBL_DAT_VAL_DOC=17;                   //Valor del Impuesto.
    final int INT_TBL_DAT_VAL_PEN=18;                   //Valor pendiente.
    final int INT_TBL_DAT_ABO_DOC=19;                   //Abono del documento.
    final int INT_TBL_DAT_AUX_ABO=20;                   //Auxiliar:Abono del documento.
    final int INT_TBL_DAT_SEC_DOC=21;                   //Secuencia del documento. Ejemplo: 001-001
    final int INT_TBL_DAT_NUM_PED=22;                   //Número de pedido. Es el número de la factura del proveedor.
    final int INT_TBL_DAT_NUM_AUT=23;                   //Número de autorización del SRI.
    final int INT_TBL_DAT_FEC_CAD=24;                   //Fecha de caducidad del documento.
    final int INT_TBL_DAT_COD_SRI=25;                   //Codigo del SRI.
    
    //Variables generales.
    Librerias.ZafAsiDia.ZafAsiDia objDiario;
    ///private ZafAsiDia objDiario;
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private Librerias.ZafUtil.ZafTipDoc objTipDoc;
    private Librerias.ZafUtil.ZafCtaCtb objCtaCtb;
//    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtAbo;
    private ZafTblCelEdiBut objTblCelEdiBut;            //Editor: JButton en celda.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafTblCelEdiTxtCon objTblCelEdiTxtCon;      //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private Connection con, conCab;
    private Statement stm, stmCab, stmA ,stm3;

    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg, vecRegDia, vecDetDiario;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strCodBen, strDesLarBen;             //Contenido del campo al obtener el foco.
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    //Variables de la clase.
    //private String strIdeBen, strDirBen;                //Campos: RUC y Dirección del Beneficiario.
    private Object objSecDoc, objNumPed, objAutSRI, objFecCad, objCodSri;
       
    private String strFecDocIni;
    private String strEstImpDoc;

    private boolean blnNecAutAnu;//true si necesita de autorizacion el documento, si no necesita es false
    private boolean blnDocAut;//true si el documento ya fue autorizado para ser anulado, false si falta todavia autorizarlo

    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".
    private String strIdePrv, strDirPrv, strCodPrv, strDesLarPrv;


    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;



    private String strSerFacIni;
    private String strNumFacIni;
    private String strNumAutFacIni;
    private String strFecCadIni;

    private ZafPulFacEle objPulFacEle;
    
    private String strVer=" v1.51";


    /** Crea una nueva instancia de la clase ZafCon05. */
    public ZafCon05(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis);
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    public ZafCon05(ZafParSis obj, Integer codigoCuenta)
    {
        this(obj);
//        txtCodSis.setText(codigoCuenta.toString());
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
    }


    /** Crea una nueva instancia de la clase ZafCon04. */
    public ZafCon05(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objParSis);

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);


            consultarReg();
            objTooBar.setVisible(false);



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
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
        lblBen = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butBen = new javax.swing.JButton();
        lblNumDoc2 = new javax.swing.JLabel();
        txtNumDoc2 = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 84));
        panGenCab.setLayout(null);

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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(100, 4, 56, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 44, 100, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 44, 90, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(392, 4, 20, 20);

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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(156, 4, 236, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(416, 4, 100, 20);

        lblNumDoc1.setText("Número aterno 1:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(416, 24, 100, 20);

        txtNumDoc1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc1);
        txtNumDoc1.setBounds(516, 24, 148, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 4, 32, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panGenCab.add(lblMonDoc);
        lblMonDoc.setBounds(416, 64, 100, 20);

        txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtMonDoc);
        txtMonDoc.setBounds(516, 64, 148, 20);

        lblBen.setText("Beneficiario:");
        lblBen.setToolTipText("Beneficiario");
        panGenCab.add(lblBen);
        lblBen.setBounds(0, 24, 100, 20);

        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusLost(evt);
            }
        });
        panGenCab.add(txtCodPrv);
        txtCodPrv.setBounds(100, 24, 56, 20);

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
        panGenCab.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(156, 24, 236, 20);

        butBen.setText("...");
        butBen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBenActionPerformed(evt);
            }
        });
        panGenCab.add(butBen);
        butBen.setBounds(392, 24, 20, 20);

        lblNumDoc2.setText("Número aterno 2:");
        lblNumDoc2.setToolTipText("Número alterno 2");
        panGenCab.add(lblNumDoc2);
        lblNumDoc2.setBounds(416, 44, 100, 20);

        txtNumDoc2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDoc2FocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc2);
        txtNumDoc2.setBounds(516, 44, 148, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(453, 403));

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

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumDoc2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc2FocusLost
        actualizarGlo();
    }//GEN-LAST:event_txtNumDoc2FocusLost

    private void txtNumDoc1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusLost
        actualizarGlo();
    }//GEN-LAST:event_txtNumDoc1FocusLost

    private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
        if (txtDesLarPrv.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarBen))
            {
                if (txtDesLarPrv.getText().equals(""))
                {
                    txtCodPrv.setText("");
                    txtDesLarPrv.setText("");
                    objTblMod.removeAllRows();
                    txtMonDoc.setText("");
                }
                else
                {
                    mostrarVenConBen(2);
                    //Cargar los documentos pendientes sólo si ha cambiado el beneficiario.
                    if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarBen))
                    {
                        cargarDocPen();
                        validarDocEle(); //Rose
                    }
                }
            }
            else
                txtDesLarPrv.setText(strDesLarBen);
        }
    }//GEN-LAST:event_txtDesLarPrvFocusLost

    private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
        strDesLarBen=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
    }//GEN-LAST:event_txtDesLarPrvFocusGained

    private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
        txtDesLarPrv.transferFocus();
    }//GEN-LAST:event_txtDesLarPrvActionPerformed

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        if (txtCodPrv.isEditable())
        {            
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodPrv.getText().equalsIgnoreCase(strCodBen))
            {
                if (txtCodPrv.getText().equals(""))
                {
                    txtCodPrv.setText("");
                    txtDesLarPrv.setText("");
                    objTblMod.removeAllRows();
                    txtMonDoc.setText("");
                }
                else
                {   
                    mostrarVenConBen(1);
                    //Cargar los documentos pendientes sólo si ha cambiado el beneficiario.
                    if (!txtCodPrv.getText().equalsIgnoreCase(strCodBen))
                    {
                        cargarDocPen();   
                        validarDocEle(); //Rose
                    }
                }
                 
            }
            else
                txtCodPrv.setText(strCodBen);              
        }
      
    }//GEN-LAST:event_txtCodPrvFocusLost

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodBen=txtCodPrv.getText();
        txtCodPrv.selectAll();
    }//GEN-LAST:event_txtCodPrvFocusGained

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void butBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBenActionPerformed
        strCodBen=txtCodPrv.getText();
        mostrarVenConBen(0);

        if (!txtCodPrv.getText().equals(""))
        {
            //Cargar los documentos pendientes sólo si ha cambiado el beneficiario.
            if (!txtCodPrv.getText().equalsIgnoreCase(strCodBen))
            {
                cargarDocPen();
                validarDocEle(); //Rose  
            }
        }
        
    }//GEN-LAST:event_butBenActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
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
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
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
            else
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

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBen;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblNumDoc2;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtMonDoc;
    private javax.swing.JTextField txtNumDoc1;
    private javax.swing.JTextField txtNumDoc2;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(516, 4, 148, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });

            
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            this.setTitle(objParSis.getNombreMenu() + strVer);
            // José Marín 23/Dic/2014
            lblTit.setText(objParSis.getNombreMenu() );
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtMonDoc.setBackground(objParSis.getColorCamposSistema());
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(26);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");//no sale
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc");//no sale
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");//tipDoc//
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");//tipo de documento//
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");//no sale
            vecCab.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");//no sale
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");//NumDoc//
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");//FecDoc//
            vecCab.add(INT_TBL_DAT_COD_TIP_RET,"Cód.Tip.Ret");//no sale
            vecCab.add(INT_TBL_DAT_DEC_TIP_RET,"Tip.Ret.");//TipRet//
            vecCab.add(INT_TBL_DAT_BUT_TIP_RET,"");//no sale
            vecCab.add(INT_TBL_DAT_DEL_TIP_RET,"Tipo de retención");//Tipo de Retencion//
            vecCab.add(INT_TBL_DAT_POR_RET,"%");//Simbolo POrcentaje//
            vecCab.add(INT_TBL_DAT_APL_RET,"Aplicado");//Aplicacion de Retencion//
            vecCab.add(INT_TBL_DAT_BAS_IMP,"Bas.Imp.");//Base Imponible//
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Ret.");//Valor de la Retencion//
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");//Valor Pendiente//
            vecCab.add(INT_TBL_DAT_ABO_DOC,"Abono");//Abono//
            vecCab.add(INT_TBL_DAT_AUX_ABO,"Aux.Abo.");//no sale
            vecCab.add(INT_TBL_DAT_SEC_DOC,"Sec.Doc.");///serie del documento del cliente
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");///numero de pedido del cliente
            vecCab.add(INT_TBL_DAT_NUM_AUT,"Núm.Aut.");///numero de autSri del docum del cliente
            vecCab.add(INT_TBL_DAT_FEC_CAD,"Fec.Cad.");///fecha caducidad del documento del cliente
            vecCab.add(INT_TBL_DAT_COD_SRI,"Cod.SRI.");///codigo del SRI
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_ABO_DOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_RET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_RET).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_APL_RET).setPreferredWidth(52);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ABO_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SEC_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_CAD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SRI).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_RET).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_RET).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_RET).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_RET).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_RET).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_AUX_ABO).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_ABO).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_ABO).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_ABO).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            vecAux.add("" + INT_TBL_DAT_ABO_DOC);
//            vecAux.add("" + INT_TBL_DAT_SEC_DOC);
//            vecAux.add("" + INT_TBL_DAT_NUM_PED);
//            vecAux.add("" + INT_TBL_DAT_NUM_AUT);
//            vecAux.add("" + INT_TBL_DAT_FEC_CAD);
//            vecAux.add("" + INT_TBL_DAT_COD_SRI);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_RET).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ABO_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strSerFac="";
                String strNumFac="";
                String strNumAutFac="";
                String strFecCad="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strSerFac=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_SEC_DOC)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_SEC_DOC).toString();
                    strNumFac=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_PED)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_PED).toString();
                    strNumAutFac=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_AUT)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_AUT).toString();
                    strFecCad=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FEC_CAD)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FEC_CAD).toString();


                    if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK)){
                        strSerFacIni="";
                        strNumFacIni="";
                        strNumAutFacIni="";
                        strFecCadIni="";
                    }
                    else{
                        if(existenFacturasSeleccionadas()){
                            //no hace nada porq no se pueden seleccionar facturas diferentes de proveedor
                            mostrarMsgInf("<HTML>La factura que intenta seleccionar es diferente a la factura seleccionada anteriormente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            objTblCelEdiChk.setCancelarEdicion(true);
                        }
                        else{
                            objTblCelEdiChk.setCancelarEdicion(false);
                        }
                    }




                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK))
                    {
                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                        seleccionaFacturasAsociadas(tblDat.getSelectedRow(), "S");
                        cargarFechaFacturaProveedor(tblDat.getSelectedRow());
                    }
                    else
                    {
                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);

                        seleccionaFacturasAsociadas(tblDat.getSelectedRow(), "N");
                        dtpFecDoc.setText("");

                    }
                    calcularAboTot();
                    ///objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);///linea original que toma a la columna del codtipret///
                    objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
                    ///generaAsiento();
                    actualizarGlo();
//                    setConCelAlm();
                }
            });
            
            objTblCelEdiTxtAbo=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_ABO_DOC).setCellEditor(objTblCelEdiTxtAbo);
            objTblCelEdiTxtAbo.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (   (objTblCelEdiTxtAbo.getText().equals(null))  ||  (objTblCelEdiTxtAbo.getText().equals(""))    )
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                    }
                    else
                    {
                        objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                    }
                    calcularAboTot();
                    objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
                    ///generaAsiento();
                    actualizarGlo();
//                    setConCelAlm();
                }
            });
            
            
            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_SEC_DOC).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_FEC_CAD).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_SRI).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Guardar el contenido de la celda para su posterior uso.
                    objSecDoc=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_SEC_DOC);
                    objNumPed=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_PED);
                    objAutSRI=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_AUT);
                    objFecCad=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FEC_CAD);
                    objCodSri=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SRI);
                    objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                }
            });
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
            
//           objDiario=new ZafAsiDia(objParSis);
//           objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
//           panAsiDia.add(objDiario, java.awt.BorderLayout.CENTER);            
//           vecDat.clear();
            
             String GLO_strnomEmp = "" + objParSis.getNombreEmpresa();
             String strNomEmp = GLO_strnomEmp.substring(0,3);
           
            configurarVenConPrv();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

                        
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public boolean anular()
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public void clickAceptar()
        {
           
        }

        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
                    }
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
        }

        public void clickAnular()
        {
            cargarDetReg();
        }

        public void clickCancelar()
        {

        }

        public void clickConsultar() 
        {
            switch (objTooBar.getEstado())
            {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    mostrarTipDocPre();
                    txtNumDoc1.setText("");
                    txtNumDoc2.setText("");                    
                    txtDesCorTipDoc.requestFocus();
                    break;
                case 'j':
                    cargarDetReg();
                    break;
            }
            //Inicializar las variables que indican cambios.
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }

        public void clickEliminar()
        {
            cargarDetReg();
        }

        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
                    }
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
        }

        public void clickImprimir()
        {
            
        }

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
                    }
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
        }

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                
                limpiarFrm();
                txtCodDoc.setEditable(false);
                txtMonDoc.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objAsiDia.setEditable(true);
                txtCodPrv.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
                if( (objParSis.getCodigoUsuario()==1) || (objParSis.getCodigoUsuario()==91) || (objParSis.getCodigoUsuario()==71)  ||  (objParSis.getCodigoUsuario()==100)   ||  (objParSis.getCodigoUsuario()==84)    ||  (objParSis.getCodigoUsuario()==179)   ||  (objParSis.getCodigoUsuario()==45) || (objParSis.getCodigoUsuario()==381)){
                }
                else{
                    txtNumDoc1.setEditable(false);
                    txtNumDoc1.setEnabled(false);
                    txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                    txtNumDoc2.setEditable(false);
                    txtNumDoc2.setEnabled(false);
                    txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));
                }

                strSerFacIni="";
                strNumFacIni="";
                strNumAutFacIni="";
                strFecCadIni="";

                //dtpFecDoc.setEnabled(false);


            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar()
        {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodPrv.setEditable(false);
            txtDesLarPrv.setEditable(false);
            butBen.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtMonDoc.setEditable(false);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            ///OJO ESTA LINEA ESTABA ACTIVA///
            objAsiDia.setEditable(true);
            switch (objTooBar.getEstado())
            {
                case 'm':
                    cargarReg();
                    if(camposInactivosPermisoModifi()){
                    }
                    break;
            }
            //Inicializar las variables que indican cambios.
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            dtpFecDoc.setEnabled(false);
        }

        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
                    }
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
        }

        public void clickVisPreliminar() 
        {
        }

        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
            try
            {
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objAsiDia.isDiarioModificado())
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
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
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
        
        public boolean vistaPreliminar()
        {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;


        }
        
        
        
        public String obtenerNumeros(java.sql.Connection con){
           String strDato="";
         try{           
           String sql="select a.ne_numdoc from tbm_detpag as pag" +
                      " inner join tbm_cabmovinv  as a on (pag.co_emp=a.co_emp and pag.co_locpag=a.co_loc and " +
                      " pag.co_tipdocpag=a.co_tipdoc and pag.co_docpag=a.co_doc)" +
                      " where pag.co_tipdoc="+txtCodTipDoc.getText()+" and pag.co_emp="+objParSis.getCodigoEmpresa()+" and pag.co_loc="+objParSis.getCodigoLocal()+"  and  pag.co_doc ="+txtCodDoc.getText() + " GROUP BY a.ne_numdoc";
             stm3 = con.createStatement();
             java.sql.ResultSet rst3 = stm3.executeQuery(sql);
              while(rst3.next()){
               strDato=strDato+"  "+rst3.getString(1);  
              }
         } catch (java.sql.SQLException e)  { objUti.mostrarMsgErr_F1(this, e); }     
           return strDato; 
       }

        
        
        public boolean aceptar()
        {            
            return true;           
        }
        
        public boolean imprimir()
        {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(0);
                objThrGUIVisPre.start();
            }
            return true;


        }
        
        public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
            if (objTblMod.getRowCountTrue()==0 || objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK)==false)  //Rose
            {               
                mostrarMsgInf("No es posible grabar la retención sin detalle.\nSeleccione el detalle para poder emitir la retención.");                 
                return false;
            } 
            if(!validarDocEle()){ return false; } //Rose
                
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            return true;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }

            if((blnNecAutAnu) && (!blnDocAut)){
                mostrarMsgInf("El documento requiere autorización para ser anulado.\nNo es posible anular un documento que requiere autorización y no está autorizado.");
                return false;
            }
            
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
        public boolean afterInsertar()
        {
            this.setEstado('w');
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
//            objTooBar.setEstado('w');
//            cargarReg();
            strFecDocIni=dtpFecDoc.getText();
            return true;
        }

        public boolean afterEliminar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }
        
        public boolean afterCancelar()
        {
            return true;
        }
        
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar el "Beneficiario".
        if (txtCodPrv.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Beneficiario</FONT> es obligatorio.<BR>Escriba o seleccione un beneficiario y vuelva a intentarlo.</HTML>");
            txtCodPrv.requestFocus();
            return false;
        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=canChangeDate();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    return true;
                                case 1: //NO_OPTION
                                    return false;
                                case 2: //CANCEL_OPTION
                                    return false;
                            }
                            datFecAux=null;
                        }
                        else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                            
                            switch (mostrarMsgCon(strMsj)){
                                case 0: //YES_OPTION
                                    return true;
                                case 1: //NO_OPTION
                                    return false;
                                case 2: //CANCEL_OPTION
                                    return false;
                            }

                            return true;
                        }
                    }
                    break;
                case 1://no puede cambiarla para nada
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    
                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
        
        //Validar que el "Código alterno" no se repita.
        if (!txtNumDoc1.getText().equals(""))
        {
            strSQL="";
            strSQL+="SELECT a1.ne_numdoc1";
            strSQL+=" FROM tbm_cabPag AS a1 ";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" AND a1.ne_numdoc1='" + txtNumDoc1.getText().replaceAll("'", "''") + "'";
            strSQL+=" AND a1.st_reg<>'E'";
            if (objTooBar.getEstado()=='m')
                strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El número de diario <FONT COLOR=\"blue\">" + txtNumDoc1.getText() + "</FONT> ya existe.<BR>Escriba otro número de diario y vuelva a intentarlo.</HTML>");
                txtNumDoc1.selectAll();
                txtNumDoc1.requestFocus();
                return false;
            }
        }
        //Validar que el "Diario esté cuadrado".
        if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que el "Monto del diario" sea igual al monto del documento.
        if (!objAsiDia.isDocumentoCuadrado(txtMonDoc.getText()))
        {
            mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
            txtMonDoc.selectAll();
            txtMonDoc.requestFocus();
            return false;
        }

        if(existeFacPrvNoAsignada()){
            mostrarMsgInf("<HTML>Existen documentos que no tienen la información completa del proveedor.<BR>Ingrese esos datos y vuelva a intentarlo.</HTML>");
            return false;
        }



        return true;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta función permite cargar los documentos pendiente del proveedor seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDocPen()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.co_tipRet, a4.tx_desCor AS a4_tx_desCor, a4.tx_desLar AS a4_tx_desLar, a2.nd_porRet";
                strSQL+=", a2.tx_aplRet, a1.nd_sub, a1.nd_porIva, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen";
                strSQL+=", a1.tx_numPed, a2.tx_codsri "; ///, a1.tx_codsri";
                strSQL+=", a2.nd_basImp";
                strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad, a2.tx_numChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_tipRet=a4.co_tipRet)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_cli=" + txtCodPrv.getText();
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";
                strSQL+=" AND a2.nd_porRet>0";
                strSQL+=" AND a2.st_reg IN ('A','C') ";
                strSQL+=" AND a2.tx_numSer IS NOT NULL AND a2.tx_numAutSri IS NOT NULL";
                
                //strSQL+=" AND a2.tx_fecCad IS NOT NULL ";
                
                strSQL+=" AND ( case when (length(a2.tx_numAutSri) <> 37 and length(a2.tx_numAutSri) <> 49) then a2.tx_fecCad IS NOT NULL "; //Si NumAutSri <> 37 o <> 49, significa que no es factura electronica y por tanto se debe validar el campo tx_fecCad
                strSQL+="             else 1 = 1"; //Si es factura electronica; por tanto no se debe validar el campo tx_fecCad
                strSQL+="       end ) ";
                
                strSQL+=" AND a2.tx_numChq IS NOT NULL";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                System.out.println("cargarDocumentosPendientes: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,"");
                    vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_RET,rst.getString("co_tipRet"));
                    vecReg.add(INT_TBL_DAT_DEC_TIP_RET,rst.getString("a4_tx_desCor"));
                    vecReg.add(INT_TBL_DAT_BUT_TIP_RET,null);
                    vecReg.add(INT_TBL_DAT_DEL_TIP_RET,rst.getString("a4_tx_desLar"));
                    vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("nd_porRet"));
                    strAux=rst.getString("tx_aplRet");
                    if (strAux==null)
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,null);
                        vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                    }
                    else if (strAux.equals("S"))
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,"Subtotal");
                        vecReg.add(INT_TBL_DAT_BAS_IMP,rst.getString("nd_basImp"));
                    }
                    else if (strAux.equals("I"))
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,"IVA");
                        vecReg.add(INT_TBL_DAT_BAS_IMP,"" + rst.getDouble("nd_basImp"));
                    }
                    else if (strAux.equals("O"))
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,"OTROS");
                        vecReg.add(INT_TBL_DAT_BAS_IMP,"" + rst.getDouble("nd_basImp"));
                    }
                    else
                    {
                        vecReg.add(INT_TBL_DAT_APL_RET,null);
                        vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                    }
                    
                    
                    vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                    vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("nd_pen"));
                    vecReg.add(INT_TBL_DAT_ABO_DOC,"");
                    vecReg.add(INT_TBL_DAT_AUX_ABO,"");
                    vecReg.add(INT_TBL_DAT_SEC_DOC,rst.getString("tx_numSer"));
                    vecReg.add(INT_TBL_DAT_NUM_PED,rst.getString("tx_numChq"));
                    vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("tx_numAutSRI"));
                    vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("tx_fecCad"));
                    vecReg.add(INT_TBL_DAT_COD_SRI,rst.getString("tx_codsri"));
                    ///vecReg.add(INT_TBL_DAT_COD_SRI,"");
                    

                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                objTooBar.setMenSis("Listo");
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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
              if (objTblMod.getRowCountTrue()==0 || objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK)==false)  //Rose
              {               
                 mostrarMsgInf("No es posible grabar la retención sin detalle.\nSeleccione el detalle para poder emitir la retención.");                 
                 return false;
              }  
              if(!validarDocEle()){ return false; } //Rose           
                              
                if (insertarCab())
                {
                    if (insertarDet())
                    {
                        if (actualizarTbmCabMovInv())
                        {
                            if (actualizarTbmPagMovInv(0))
                            {
                                if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))
                                {
                                    con.commit();
                                    enviarPulsoFacturacionElectronica();
                                    blnRes=true;
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
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
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
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
                //Validar que sólo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    //strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" , a1.co_usrIng, b5.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                    strSQL+=" FROM (          (tbm_cabPag AS a1 INNER JOIN tbm_usr AS b5 ON a1.co_usrIng=b5.co_usr)";
                    strSQL+="                  INNER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                    strSQL+="      )";

                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    //strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" , a1.co_usrIng, a5.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                    strSQL+=" FROM (          (tbm_cabPag AS a1 INNER JOIN tbm_usr AS a5 ON a1.co_usrIng=a5.co_usr)";
                    strSQL+="                  INNER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                    strSQL+="      )";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc =" + strAux + "";
                strAux=txtCodPrv.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli =" + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc =" + strAux + "";
                strAux=txtNumDoc1.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1 =" + strAux + "";
                strAux=txtNumDoc2.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc2 =" + strAux + "";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarCab())
                {
                    if (eliminarDet())
                    {
                        if (insertarDet())
                        {
                            if (actualizarTbmCabMovInv())
                            {
                                if (actualizarTbmPagMovInv(1))
                                {
                                    if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                    {
                                        con.commit();
                                        blnRes=true;
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
            enviarPulsoFacturacionElectronica();
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
    
    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (eliminarCab())
                {
                    if (actualizarTbmPagMovInv(2))
                    {
                        if (objAsiDia.eliminarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                        {
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
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
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (anularCab())
                {
                    if (actualizarTbmPagMovInv(3))
                    {
                        if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                        {
                            con.commit();
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
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

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        int intCodUsr, intUltReg, x=10;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, fe_ven, ne_numDoc1, ne_numDoc2, co_cta, co_cli";
                strSQL+=", tx_ruc, tx_nomCli, tx_dirCli, nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, co_mnu, tx_comIng, tx_comMod)"; ///tx_secdoc, tx_numped, tx_numautsri, tx_feccad,
                strSQL+=" VALUES (";
                strSQL+=objParSis.getCodigoEmpresa();//co_emp
                strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                strSQL+=", " + txtCodTipDoc.getText();//co_tipDoc
                strSQL+=", " + intUltReg;//co_doc
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());//fe_doc
                strSQL+=", '" + strAux + "'";//fe_ven
                strSQL+=", '" + strAux + "'";
                strSQL+=", " + objUti.codificar(txtNumDoc1.getText(),2);//ne_numDoc1
                strSQL+=", " + objUti.codificar(txtNumDoc2.getText(),2);//ne_numDoc2
                strSQL+=", Null";//co_cta
                strSQL+=", " + objUti.codificar(txtCodPrv.getText(),2);//co_cli
                strSQL+=", " + objUti.codificar(strIdePrv);//tx_ruc
                strSQL+=", " + objUti.codificar(txtDesLarPrv.getText());//tx_nomCli
                strSQL+=", " + objUti.codificar(strDirPrv);//tx_dirCli
                //strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + x * (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);//inserta el valor de la suma de los detalles nd_mondoc
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);//inserta el valor de la suma de los detalles nd_mondoc
                
                ////datos para las retenciones////
//                strSQL+=", " + objUti.codificar(txtSecDoc.getText());//txt_NumPed
//                strSQL+=", " + objUti.codificar(txtNumPed.getText());//txt_NumFacPro
//                strSQL+=", " + objUti.codificar(txtAutSri.getText());//txt_NumAutSri
//                strSQL+=", " + objUti.codificar(txtFecCad.getText());//txt_FecCadDoc
                
                strSQL+=", " + objUti.codificar(txaObs1.getText());//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText());//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());//fe_ing
                strSQL+=", '" + strAux + "'";
                strSQL+=", '" + strAux + "'";
                strSQL+=", " + intCodUsr;//co_usrIng
                strSQL+=", " + intCodUsr;//co_usrMod
                strSQL+=", " + objParSis.getCodigoMenu() + "";//co_mnu
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=")";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc;
        boolean blnRes=true;
        int intInsDetAdi=0;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;
                
                for (i=0;i<objTblMod.getRowCountTrue();i++){
                    if (objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        intInsDetAdi++;
                    }
                }
                
                if(intInsDetAdi==0){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                    strSQL+=", co_regPag, nd_abo, tx_codsri, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + strCodTipDoc;
                    strSQL+=", " + strCodDoc;
                    strSQL+=", " + j;
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", " + intSig*Double.parseDouble(objUti.codificar(txtMonDoc.getText(), 3));
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";
                    strSQL+=", Null";                        
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                }
                else{
                    for (i=0;i<objTblMod.getRowCountTrue();i++){
                        if (objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                            strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                            strSQL+=" VALUES (";
                            strSQL+="" + intCodEmp;
                            strSQL+=", " + intCodLoc;
                            strSQL+=", " + strCodTipDoc;
                            strSQL+=", " + strCodDoc;
                            strSQL+=", " + j;
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                            strSQL+=", " + intSig*Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC), 3));
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";
                            strSQL+=", Null";                        
                            strSQL+=")";
                            stm.executeUpdate(strSQL);
                            j++;
                        }
                    }
                }
                
                
                

                stm.close();
                stm=null;
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
     * Esta función permite actualizar la tabla "tbm_pagMovInv".
     * @param intTipOpe El tipo de operación.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operación</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmPagMovInv(int intTipOpe)
    {
        int intCodEmp, i;
        double dblAbo1, dblAbo2;
        boolean blnRes=true;
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        switch (intTipOpe)
                        {
                            case 0:
                            case 1:
                                if (objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC)!=objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO))
                                {
                                    dblAbo1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=nd_abo+" + intSig*objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    strSQL+=";";
                                    strSQL+=" UPDATE tbm_detRecDoc";
                                    strSQL+=" SET nd_valApl=" + objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos()) + " FROM(";
                                    strSQL+="       SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.co_reg, a3.nd_valApl";
                                    strSQL+="       FROM tbm_pagMovInv AS a1 INNER JOIN tbr_detRecDocCabMovInv AS a2";
                                    strSQL+="       ON a1.co_emp=a2.co_empRel AND a1.co_loc=a2.co_locRel AND a1.co_tipDoc=a2.co_tipDocRel AND a1.co_doc=a2.co_docRel ";
                                    strSQL+="       INNER JOIN tbm_detRecDoc AS a3";
                                    strSQL+="       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc ";
                                    strSQL+="       AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                                    strSQL+="       WHERE a1.co_emp=" + intCodEmp;
                                    strSQL+="       AND a1.co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+="       AND a1.co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+="       AND a1.co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);

                                    strSQL+="       AND a1.st_reg IN('A','C') AND a2.st_reg NOT IN('I','E') AND a3.st_reg NOT IN('I','E')";
                                    strSQL+="       AND a1.tx_numChq=a3.tx_numChq";
                                    strSQL+="       AND a1.co_reg In(" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG) + ")";
                                    strSQL+="       AND a1.tx_numChq='" + objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED) + "'";
                                    strSQL+="       GROUP BY a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.co_reg, a3.nd_valApl";
                                    strSQL+=") AS x";
                                    strSQL+=" WHERE tbm_detRecDoc.co_emp=x.co_emp AND tbm_detRecDoc.co_loc=x.co_loc AND tbm_detRecDoc.co_tipDoc=x.co_tipDoc";
                                    strSQL+=" AND tbm_detRecDoc.co_doc=x.co_doc AND tbm_detRecDoc.co_reg=x.co_reg";
                                    strSQL+=";";
                                    stm.executeUpdate(strSQL);
                                    objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC),i,INT_TBL_DAT_AUX_ABO);
                                }
                                break;
                            case 2:
                            case 3:
                                dblAbo1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_ABO_DOC));
                                dblAbo2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_pagMovInv";
                                strSQL+=" SET nd_abo=nd_abo+" + intSig*(-dblAbo2);
                                ///strSQL+=" , st_reg = 'I'";
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    strSQL+=";";
                                    strSQL+=" UPDATE tbm_detRecDoc";
                                    strSQL+=" SET nd_valApl=" + objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos()) + " FROM(";
                                    strSQL+="       SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.co_reg, a3.nd_valApl";
                                    strSQL+="       FROM tbm_pagMovInv AS a1 INNER JOIN tbr_detRecDocCabMovInv AS a2";
                                    strSQL+="       ON a1.co_emp=a2.co_empRel AND a1.co_loc=a2.co_locRel AND a1.co_tipDoc=a2.co_tipDocRel AND a1.co_doc=a2.co_docRel ";
                                    strSQL+="       INNER JOIN tbm_detRecDoc AS a3";
                                    strSQL+="       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc ";
                                    strSQL+="       AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                                    strSQL+="       WHERE a1.co_emp=" + intCodEmp;
                                    strSQL+="       AND a1.co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+="       AND a1.co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+="       AND a1.co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);

                                    strSQL+="       AND a1.st_reg IN('A','C') AND a2.st_reg NOT IN('I','E') AND a3.st_reg NOT IN('I','E')";
                                    strSQL+="       AND a1.tx_numChq=a3.tx_numChq";
                                    strSQL+="       AND a1.co_reg In(" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG) + ")";
                                    strSQL+="       AND a1.tx_numChq='" + objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED) + "'";
                                    strSQL+="       GROUP BY a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.co_reg, a3.nd_valApl";
                                    strSQL+=") AS x";
                                    strSQL+=" WHERE tbm_detRecDoc.co_emp=x.co_emp AND tbm_detRecDoc.co_loc=x.co_loc AND tbm_detRecDoc.co_tipDoc=x.co_tipDoc";
                                    strSQL+=" AND tbm_detRecDoc.co_doc=x.co_doc AND tbm_detRecDoc.co_reg=x.co_reg";
                                    strSQL+=";";
                                stm.executeUpdate(strSQL);
                                break;
                        }
                    }
                    stm.close();
                    stm=null;
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetReg())
                {
                    objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        blnNecAutAnu=false;
        blnDocAut=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.fe_ven";
                strSQL+=", a1.ne_numDoc1, a1.ne_numDoc2, a1.co_cta, a1.co_cli";
                strSQL+=", a1.tx_ruc, a1.tx_nomCli, a1.tx_dirCli, a1.nd_monDoc, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+=", a1.st_autAnu";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    strFecDocIni=dtpFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp");
                    
                    strAux=rst.getString("ne_numDoc1");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_numDoc2");
                    txtNumDoc2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cli");
                    txtCodPrv.setText((strAux==null)?"":strAux);
                    strIdePrv=rst.getString("tx_ruc");
                    strAux=rst.getString("tx_nomCli");
                    txtDesLarPrv.setText((strAux==null)?"":strAux);
                    strDirPrv=rst.getString("tx_dirCli");
                    txtMonDoc.setText("" + Math.abs(rst.getDouble("nd_monDoc")));                                       
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);                                        
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);

                    if((rst.getObject("st_autAnu")==null?"":rst.getString("st_autAnu")).equals("A"))
                        blnDocAut=true;


                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }


                strSQL="";
                strSQL+="SELECT st_necAutAnuDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND st_necAutAnuDoc='S'";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    System.out.println("fggy: "+ strSQL);
                    blnNecAutAnu=true;
                }

                
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (!txtCodPrv.getText().equals(""))
            {
//                objTooBar.setMenSis("Obteniendo datos...");
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                    //Armar la sentencia SQL.
                    if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        //Para el modo "Modificar" se muestra: documentos pendientes + documentos pagados.
                        strSQL="";
                        strSQL+="SELECT a1.co_loc AS co_locIng, a2.co_loc, a2.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a2.co_doc, a2.co_reg, a3.ne_numDoc, a3.fe_doc";
                        strSQL+=", a2.co_tipRet, a5.tx_desCor AS a5_tx_desCor, a5.tx_desLar AS a5_tx_desLar, a2.nd_porRet, a2.tx_aplRet";
                        strSQL+=", a3.nd_sub, a3.nd_porIva, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, ABS(a1.nd_abo) AS nd_abo";
                        //strSQL+=", a3.tx_secDoc, a2.tx_numPed, a3.tx_numAutSRI, a3.tx_fecCad, a1.tx_codsri";
                        strSQL+=" , a2.tx_numSer AS tx_secDoc, a2.tx_numAutSri, a2.tx_fecCad, a2.tx_numChq AS tx_numPed, a2.tx_codsri";
                        strSQL+=", a2.nd_basImp";
                        strSQL+=" FROM tbm_detPag AS a1";
                        strSQL+=" RIGHT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg";
                        strSQL+=" AND a1.co_emp=" + rstCab.getString("co_emp");
                        strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                        strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                        strSQL+=" )";
                        strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_tipRet=a5.co_tipRet)";
                        strSQL+=" WHERE a3.co_emp=" + rstCab.getString("co_emp");
                        strSQL+=" AND a3.co_cli=" + txtCodPrv.getText();
                        strSQL+=" AND a3.st_reg IN ('A','R','C','F')";
                        strSQL+=" AND ((a2.mo_pag+a2.nd_abo)>0 OR a1.nd_abo IS NOT NULL)";
                        strSQL+=" AND a2.nd_porRet>0";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                    }
                    else
                    {
                        //Para los demás modos se muestra: sólo los documentos pagados.
                        strSQL="";
                        strSQL+="SELECT a1.co_loc AS co_locIng, a2.co_loc, a2.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a2.co_doc, a2.co_reg, a3.ne_numDoc, a3.fe_doc";
                        strSQL+=", a2.co_tipRet, a5.tx_desCor AS a5_tx_desCor, a5.tx_desLar AS a5_tx_desLar, a2.nd_porRet, a2.tx_aplRet";
                        strSQL+=", a3.nd_sub, a3.nd_porIva, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, ABS(a1.nd_abo) AS nd_abo";
                        //strSQL+=", a3.tx_secDoc, a3.tx_numPed, a3.tx_numAutSRI, a3.tx_fecCad, a1.tx_codsri";
                        strSQL+=" , a2.tx_numSer AS tx_secDoc, a2.tx_numAutSri, a2.tx_fecCad, a2.tx_numChq AS tx_numPed, a2.tx_codsri";
                        strSQL+=", a2.nd_basImp";
                        strSQL+=" FROM tbm_detPag AS a1";
                        strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_tipRet=a5.co_tipRet)";
                        strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                        strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                        strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                        strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                    }
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                    objTooBar.setMenSis("Cargando datos...");
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,null);
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));      //TipDoc
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));      //Tipo de Documento
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));             //
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));             //
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));          //numDoc
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));             //Fec.Doc.
                        vecReg.add(INT_TBL_DAT_COD_TIP_RET,rst.getString("co_tipRet"));      //                                            
                        vecReg.add(INT_TBL_DAT_DEC_TIP_RET,rst.getString("a5_tx_desCor"));   //Tip.Ret
                        vecReg.add(INT_TBL_DAT_BUT_TIP_RET,null);
                        vecReg.add(INT_TBL_DAT_DEL_TIP_RET,rst.getString("a5_tx_desLar"));   //Tipo de Retencion
                        vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("nd_porRet"));          //Porcentaje
                        strAux=rst.getString("tx_aplRet");                                   //Aplicacion del Impuesto
                        if (strAux==null){
                            vecReg.add(INT_TBL_DAT_APL_RET,null);
                            vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                        }
                        else if (strAux.equals("S")){
                            vecReg.add(INT_TBL_DAT_APL_RET,"Subtotal");
                            vecReg.add(INT_TBL_DAT_BAS_IMP,rst.getString("nd_basImp"));         //Bas.Imponible
                        }
                        else if (strAux.equals("I")){
                            vecReg.add(INT_TBL_DAT_APL_RET,"IVA");
                            vecReg.add(INT_TBL_DAT_BAS_IMP,"" + rst.getString("nd_basImp"));
                        }
                        else if(strAux.equals("O")){
                            vecReg.add(INT_TBL_DAT_APL_RET,"OTROS");
                            vecReg.add(INT_TBL_DAT_BAS_IMP,"" + rst.getString("nd_basImp"));
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_APL_RET,null);
                            vecReg.add(INT_TBL_DAT_BAS_IMP,null);
                        }
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("nd_pen"));
                        strAux=rst.getString("nd_abo");
                        vecReg.add(INT_TBL_DAT_ABO_DOC,strAux);
                        vecReg.add(INT_TBL_DAT_AUX_ABO,strAux);
                        vecReg.add(INT_TBL_DAT_SEC_DOC,rst.getString("tx_secDoc"));
                        vecReg.add(INT_TBL_DAT_NUM_PED,rst.getString("tx_numPed"));
                        vecReg.add(INT_TBL_DAT_NUM_AUT,rst.getString("tx_numAutSRI"));
                        vecReg.add(INT_TBL_DAT_FEC_CAD,rst.getString("tx_fecCad"));
                        vecReg.add(INT_TBL_DAT_COD_SRI,rst.getString("tx_codsri"));
                        if (strAux!=null)
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK);
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    vecDat.clear();
//                    objTooBar.setMenSis("Listo");
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strSQL+=", fe_ven='" + strAux + "'";
                strSQL+=", ne_numDoc1=" + objUti.codificar(txtNumDoc1.getText(),2);
                strSQL+=", ne_numDoc2=" + objUti.codificar(txtNumDoc2.getText(),2);
                strSQL+=", co_cli=" + objUti.codificar(txtCodPrv.getText(),2);
                strSQL+=", tx_ruc=" + objUti.codificar(strIdePrv);
                strSQL+=", tx_nomCli=" + objUti.codificar(txtDesLarPrv.getText());
                strSQL+=", tx_dirCli=" + objUti.codificar(strDirPrv);
                strSQL+=", nd_monDoc=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                                
                
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_detPag";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmCabMovInv()
    {
        int i;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_cabMovInv";
                        //strSQL+=" SET tx_secDoc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_SEC_DOC));
                        strSQL+=" SET tx_numPed=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED));
                        //strSQL+=", tx_numAutSRI=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_AUT));
                        //strSQL+=", tx_fecCad=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_FEC_CAD));
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        stm.executeUpdate(strSQL);





                    }
                }
                stm.close();
                stm=null;
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
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
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc1.setText("");
            txtNumDoc2.setText("");
            objTblMod.removeAllRows();
            txtMonDoc.setText("");
            objAsiDia.inicializar();
            txaObs1.setText("");
            txaObs2.setText("");
            objSecDoc=null;
            objNumPed=null;
            objAutSRI=null;
            objFecCad=null;
            objCodSri=null;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
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
                if(objParSis.getCodigoUsuario()==1){
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
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }

                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc1.setText("" + (rst.getInt("ne_ultDoc")+1));
                    txtNumDoc2.setText("" + (rst.getInt("ne_ultDoc")+1));
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        String strAli, strCam;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
        
        try
        {
            strAli="Código, Descripción corta, Descripción larga, Ultimo documento, Naturaleza";
            strCam="a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
            
            if(intCodUsr==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de tipos de documentos");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                        txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (objVenCon.buscar("LOWER(a1.tx_desCor) LIKE '" + txtDesCorTipDoc.getText().toLowerCase() + "'"))
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                        txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesCorTipDoc.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                            txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                            txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (objVenCon.buscar("LOWER(a1.tx_desLar) LIKE '" + txtDesLarTipDoc.getText().toLowerCase() + "'"))
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                        txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesLarTipDoc.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                            txtDesCorTipDoc.setText(objVenCon.GetCamSel(2));
                            txtDesLarTipDoc.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */

    /*
    private boolean mostrarVenConBen(int intTipBus)
    {
        String strAli, strCam;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            strAli="Código, Identificación, Nombre, Dirección";
            strCam="a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            //strSQL+=" AND a1.st_prv='S'";
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de proveedores");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodBen.setText(objVenCon.GetCamSel(1));
                        strIdeBen=objVenCon.GetCamSel(2);
                        txtDesLarBen.setText(objVenCon.GetCamSel(3));
                        strDirBen=objVenCon.GetCamSel(4);
                        actualizarGlo();
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    //if (objVenCon.buscar("LOWER(a1.co_cli) LIKE '" + txtCodBen.getText() + "'"))
                    if (objVenCon.buscar("a1.co_cli=" + txtCodBen.getText() + ""))
                    {
                        txtCodBen.setText(objVenCon.GetCamSel(1));
                        strIdeBen=objVenCon.GetCamSel(2);
                        txtDesLarBen.setText(objVenCon.GetCamSel(3));
                        strDirBen=objVenCon.GetCamSel(4);
                        actualizarGlo();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtCodBen.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(0);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodBen.setText(objVenCon.GetCamSel(1));
                            strIdeBen=objVenCon.GetCamSel(2);
                            txtDesLarBen.setText(objVenCon.GetCamSel(3));
                            strDirBen=objVenCon.GetCamSel(4);
                            actualizarGlo();
                        }
                        else
                        {
                            txtCodBen.setText(strCodBen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (objVenCon.buscar("LOWER(a1.tx_nom) LIKE '" + txtDesLarBen.getText().toLowerCase() + "'"))
                    {
                        txtCodBen.setText(objVenCon.GetCamSel(1));
                        strIdeBen=objVenCon.GetCamSel(2);
                        txtDesLarBen.setText(objVenCon.GetCamSel(3));
                        strDirBen=objVenCon.GetCamSel(4);
                        actualizarGlo();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesLarBen.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodBen.setText(objVenCon.GetCamSel(1));
                            strIdeBen=objVenCon.GetCamSel(2);
                            txtDesLarBen.setText(objVenCon.GetCamSel(3));
                            strDirBen=objVenCon.GetCamSel(4);
                            actualizarGlo();
                        }
                        else
                        {
                            txtDesLarBen.setText(strDesLarBen);
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


    */






    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBen(int intTipBus)
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
                        actualizarGlo();
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        actualizarGlo();
                        objTblMod.removeAllRows();
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
                            actualizarGlo();
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        actualizarGlo();
                        objTblMod.removeAllRows();
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
                            actualizarGlo();
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            txtDesLarPrv.setText(strDesLarPrv);
                        }
                    }
                    break;
            }
            System.out.println("mostrarVenConBen-strIdeBen: " + strIdePrv);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
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
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
//            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_prv='S'";
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";

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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtDesLarPrv.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc1.getDocument().addDocumentListener(objDocLis);
        txtNumDoc2.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    /**
     * Esta función calcula el abono total.
     */
    private void calcularAboTot()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {
            intFilPro=objTblMod.getRowCount();
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_ABO_DOC)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_ABO_DOC).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            //Calcular la diferencia.
            txtMonDoc.setText("" + objUti.redondear(dblTot,objParSis.getDecimalesBaseDatos()));
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
        }
    }

    /**
     * Esta función genera el detalle para el asiento de diario de forma explícita.
     */
    private void generaAsiento()
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        ////////para la cuenta////
        int intCodCtaDeb=0, intCodCtaHab=0;////cod_cta///
        String strNumCtaDeb="", strNumCtaHab=""; ///num_cta///
        String strNomCtaDeb="", strNomCtaHab=""; ///nom_cta///
          try
          {      
                con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                stm = con.createStatement();
                
           
             objDiario.inicializar();
         
             int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
             int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
             int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
             int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
             int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
             int INT_VEC_DEBE   = 5; //5) Debe. 
             int INT_VEC_HABER  = 6; //6) Haber. 
             int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null

               if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
                 else vecDetDiario.removeAllElements();

             java.util.Vector vecRegDia; 


             ///double dblValor=0;
             ///dblValor=Double.parseDouble((txtMonDoc.getText().equals(""))?"0":txtMonDoc.getText());
             ///System.out.println("VALOR EN EL DIARIO...>>>  " + dblValor);
            
             //////para calcular el total de valores seleccionados/// 
             intFilPro=objTblMod.getRowCountTrue();
             for (i=0; i<intFilPro; i++)
             {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_ABO_DOC)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_ABO_DOC).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
             }
             

             ///if(dblValor!=0)
             if(dblTot!=0)
             {                    

                    ////////query de prueba para guardar el cod_ctaDeb///                                    
                    String sqlDeb = "SELECT a.co_ctadeb ,b.tx_codcta,b.tx_deslar FROM tbm_cabtipdoc as a" +
                      " inner join tbm_placta as b on (a.co_Emp=b.co_Emp and a.co_ctadeb=b.co_cta)" +
                      " where a.co_emp ="+objParSis.getCodigoEmpresa()+" and a.co_loc ="+objParSis.getCodigoLocal()+" and a.co_tipdoc ="+ txtCodTipDoc.getText() +"  and a.st_reg='A'";
                    rst = stm.executeQuery(sqlDeb);                    
                    
                    while (rst.next())
                    {
                      intCodCtaDeb = (rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb"));
                      strNumCtaDeb =  (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
                      strNomCtaDeb =  (rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));                      
                    }                    

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    
                    ////////query de preuba para guardar el cod_ctaHab///                                                                                
                    String sqlHab = "SELECT a.co_ctahab ,b.tx_codcta,b.tx_deslar FROM tbm_cabtipdoc as a" +
                      " inner join tbm_placta as b on (a.co_Emp=b.co_Emp and a.co_ctahab=b.co_cta)" +
                      " where a.co_emp ="+objParSis.getCodigoEmpresa()+" and a.co_loc ="+objParSis.getCodigoLocal()+" and a.co_tipdoc ="+txtCodTipDoc.getText()+"  and a.st_reg='A'";
                    rst = stm.executeQuery(sqlHab);
                    
                    while (rst.next())
                    {
                      intCodCtaHab = (rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab"));
                      strNumCtaHab =  (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
                      strNomCtaHab =  (rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));                      
                    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                 
                ///if(dblValor < 0.00)
                    vecRegDia = new java.util.Vector();
                    vecRegDia.add(INT_LINEA, null);                
                    vecRegDia.add(INT_VEC_CODCTA, new Integer(intCodCtaDeb));
                    ///vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaComAju() );///original///
                    vecRegDia.add(INT_VEC_NUMCTA, new String (strNumCtaDeb));///para probar///
                    vecRegDia.add(INT_VEC_BOTON, null);
                    ///vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaComAju() );///original///
                    vecRegDia.add(INT_VEC_NOMCTA, new String (strNomCtaDeb));///para probar///
                    vecRegDia.add(INT_VEC_DEBE, new Double(objUti.redondear(txtMonDoc.getText(),objParSis.getDecimalesMostrar())));
                    vecRegDia.add(INT_VEC_HABER, new Double(0)); 
                    vecRegDia.add(INT_VEC_REF, null);
                    vecDetDiario.add(vecRegDia);
 
                    vecRegDia = new java.util.Vector();
                    vecRegDia.add(INT_LINEA, null);                
                    vecRegDia.add(INT_VEC_CODCTA, new Integer(intCodCtaHab));
                    ///vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );///original///
                    vecRegDia.add(INT_VEC_NUMCTA, new String (strNumCtaHab));///para probar///
                    vecRegDia.add(INT_VEC_BOTON, null);
                    ///vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );////original///
                    vecRegDia.add(INT_VEC_NOMCTA, new String (strNomCtaHab));///para probar///
                    vecRegDia.add(INT_VEC_DEBE, new Double(0)); 
                    vecRegDia.add(INT_VEC_HABER, new Double(objUti.redondear(txtMonDoc.getText(),objParSis.getDecimalesMostrar())));
                    vecRegDia.add(INT_VEC_REF, null);
                    vecDetDiario.add(vecRegDia);
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
             }///fin del if dblTot!0////

            objDiario.setDetalleDiario(vecDetDiario);
            
               
          }
          catch(java.sql.SQLException Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt); }
          catch(Exception Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt); }
    
     }
        
    /**
     * Esta función se utiliza para regenerar el asiento de diario. 
     * La idea principal de ésta función es regenerar el asiento de diario. Pero,
     * puede darse el caso en el que el asiento de diario fue modificado manualmente
     * por el usuario. En  cuyo caso, primero se pregunta si desea regenerar el asiento
     * de diario, no regenerarlo o cancelar.
     * @return true: Si se decidió regenerar/no regenerar el asiento de diario.
     * <BR>false: Si se canceló la regeneración del asiento de diario.
     * Nota.- Como se puede apreciar la función retorna "false" sólo cuando se dió
     * click en el botón "Cancelar".
     */
    private boolean regenerarDiario()
    {
        boolean blnRes=true;
        if (objAsiDia.getGeneracionDiario()==2)
        {
            strAux="¿Desea regenerar el asiento de diario?\n";
            strAux+="El asiento de diario ha sido modificado manualmente.";
            strAux+="\nSi desea que el sistema regenere el asiento de diario de click en SI.";
            strAux+="\nSi desea grabar el asiento de diario tal como está de click en NO.";
            strAux+="\nSi desea cancelar ésta operación de click en CANCELAR.";
            switch (mostrarMsgCon(strAux))
            {
                case 0: //YES_OPTION
                    objAsiDia.setGeneracionDiario((byte)0);
                    objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
                    ///generaAsiento();
                    break;
                case 1: //NO_OPTION
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
            }
        }
        else
        {
            objAsiDia.setGeneracionDiario((byte)0);
            objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
            ///generaAsiento();
        }
        return blnRes;
    }

    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()
    {
        int i;
        strAux="";
        strAux+=txtDesCorTipDoc.getText() + ": " + txtNumDoc1.getText();
        strAux+="; Beneficiario: " + txtDesLarPrv.getText();
        strAux+="; Documentos: ";
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC) + ", ";
        objAsiDia.setGlosa(strAux);
    }
    
    /**
     * Esta función establece el contenido de la celda almacenado.
     * Cada vez que se digita una celda el sistema almacena el último valor que se digitó de
     * manera que la próxima fila que se edite presente automáticamente el último valor almacenado.
     * Esto evita que el usuario tenga que volver a digitar lo que digitó anteriormente.
     */
    private void setConCelAlm()
    {
        int intFilSel;
        intFilSel=tblDat.getSelectedRow();
        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_SEC_DOC)==null)
            objTblMod.setValueAt(objSecDoc, intFilSel, INT_TBL_DAT_SEC_DOC);
        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUM_PED)==null)
            objTblMod.setValueAt(objNumPed, intFilSel, INT_TBL_DAT_NUM_PED);
        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUM_AUT)==null)
            objTblMod.setValueAt(objAutSRI, intFilSel, INT_TBL_DAT_NUM_AUT);
        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_FEC_CAD)==null)
            objTblMod.setValueAt(objFecCad, intFilSel, INT_TBL_DAT_FEC_CAD);
        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_SRI)==null)
            objTblMod.setValueAt(objCodSri, intFilSel, INT_TBL_DAT_COD_SRI);

    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        int intCodEmp, intLoc, intTipDoc, intCodDia;
        
        public ZafThreadGUI(int empresa, int local, int tipoDocumento, int diario)
        {
            intCodEmp=empresa;
            intLoc=local;
            intTipDoc=tipoDocumento;
            intCodDia=diario;
        }
        
        public void run()
        {
        }
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
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Codigo del Local";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_RET:
                    strMsg="Descripción corta del tipo de retención";
                    break;
                case INT_TBL_DAT_DEL_TIP_RET:
                    strMsg="Descripción larga del tipo de retención";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DAT_APL_RET:
                    strMsg="Aplicado a";
                    break;
                case INT_TBL_DAT_BAS_IMP:
                    strMsg="Base imponible";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor de la retención";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_DAT_ABO_DOC:
                    strMsg="Valor a abonar";
                    break;
                case INT_TBL_DAT_SEC_DOC:
                    strMsg="Secuencia del documento. Ej: 001-001";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número de pedido. Ej: Número de factura del proveedor";
                    break;
                case INT_TBL_DAT_NUM_AUT:
                    strMsg="Número de autorización del SRI";
                    break;
                case INT_TBL_DAT_FEC_CAD:
                    strMsg="Fecha de caducidad del documento";
                    break;
                case INT_TBL_DAT_COD_SRI:
                    strMsg="Codigo del SRI para el Documento";
                    break;                    
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función obtiene el tipo de modificación de fecha que el usurio puede realizar en el tipo de documento
     * <BR>Nota.- Los valores que puede tener son:
     * <UL>
     * <LI>1: No se puede modificar la fecha el tipo de documento
     * <LI>2: Se puede modificar la fecha hacia atrás
     * <LI>3: Se puede modificar la fecha hacia adelante
     * <LI>4: Se puede modificar la fecha
     * </UL>
     * @return intTipModFec: El tipo de modificación de fecha que el usuario puede realizar en el tipo de documento
     */
    private int canChangeDate(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModFec=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModFec=rstChaDat.getInt("ne_tipresmodfecdoc");
                    }
                    
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;

            }
        }
        
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
        
    }
    
    /**
     * Esta función obtiene el tipo de modificación que el usurio puede realizar en el tipo de documento
     * <BR>Nota.- Los valores que puede tener son:
     * <UL>
     * <LI>1: No se puede modificar el tipo de documento
     * <LI>2: Se puede modificar el tipo de documento si no ha sido impreso todavía
     * <LI>3: El documento se puede modificar
     * </UL>
     * @return intTipModTipDocUsr: El tipo de modificación que el usuario puede realizar en el tipo de documento
     */
    private int canTipoModificacion(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModTipDocUsr=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModTipDocUsr=3;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmoddoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModTipDocUsr=rstChaDat.getInt("ne_tipresmoddoc");
                    }
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModTipDocUsr;
        
    }

    /**
     * Esta función permite inactivar los campos del formulario
     * de acuerdo al tipo de permiso que tenga el usuario para modificar el tipo de documento
     * @return true: Si se realizó con éxito la conexión a la base de datos
     * <BR>false: En el caso contrario.
     */
    private boolean camposInactivosPermisoModifi(){
        boolean blnRes=true;
        try{
            int intTipModDoc;
            String strFecDocTmp;
            intTipModDoc=canTipoModificacion();
//            txtCodBen.setEditable(true);
//            txtDesLarBen.setEditable(true);
//            butBen.setEnabled(true);
            dtpFecDoc.setEnabled(true);
            txtNumDoc1.setEditable(true);
            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
            txtNumDoc2.setEditable(true);
            txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));                        
            txaObs1.setEditable(true);
            txaObs2.setEditable(true);
            objAsiDia.setEditable(true);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objAsiDia.setEditable(true);

            switch(intTipModDoc){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            txtCodPrv.setEditable(false);
                            txtDesLarPrv.setEditable(false);
                            butBen.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                            txtNumDoc2.setEditable(false);
                            txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));                        
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objAsiDia.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else{
                                String strMsj="";
                                strMsj+="<HTML>EL documento no se puede modificar por las siguientes razones:";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios al documento.";
                                strMsj+="</HTML>";
                                mostrarMsgInf(strMsj);
                            }

                        }
                    }
                    break;
                case 1://no puede modificar nada, incluyendo fecha del documento
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        txtCodPrv.setEditable(false);
                        txtDesLarPrv.setEditable(false);
                        butBen.setEnabled(false);
                        dtpFecDoc.setEnabled(false);
                        txtNumDoc1.setEditable(false);
                        txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                        txtNumDoc2.setEditable(false);
                        txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));                        
                        txaObs1.setEditable(false);
                        txaObs2.setEditable(false);
                        objAsiDia.setEditable(false);
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                        }
                        else
                            mostrarMsgInf("<HTML>Ud. no cuenta con ningún tipo de permiso para Modificar.<BR>.Solicite a su Adminsitrador del Sistema dicho permiso y vuelva a intentarlo.</HTML>");
                    }
                    
                    break;
                case 2://modificación parcial la modificación de la fecha dependerá de si se cuenta con este permiso
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        if( ! strEstImpDoc.equals("S")){//si el documento no está impreso se puede modificar, la modif. de fecha depende de tbr_tipDocUsr.ne_tipresmodfecdoc
                            dtpFecDoc.setEnabled(true);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                        }
                        else{//si el documento está impreso no se permite modificar
                            dtpFecDoc.setEnabled(false);
                            txtCodPrv.setEditable(false);
                            txtDesLarPrv.setEditable(false);
                            butBen.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                            txtNumDoc2.setEditable(false);
                            txtNumDoc2.setBackground(new java.awt.Color(255, 255, 255));                        
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objAsiDia.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else
                                mostrarMsgInf("<HTML>El documento consultado no se puede modificar porque ya está impreso.</HTML>");
                        }
                    }
                    break;
                case 3://modificación completa, la modificación de la fecha dependerá de si se cuenta con este permiso
                    dtpFecDoc.setEnabled(true);
                    break;
                default:
                    break;
            }
            objTooBar.setOperacionSeleccionada("n");
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }


    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        strAux="";
        String strSQLRep="", strSQLSubRep="";
        Statement stmIns;
        String strNomEmp="", strNomEmpAux, strNomEmpRep;
        try
        {
            enviarPulsoFacturacionElectronica();
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (con!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    
                    strNomEmpRep = "";
                    strNomEmpAux = objParSis.getNombreEmpresa().toUpperCase();

                    if (strNomEmpAux.indexOf("TUVAL") != -1)
                    {  
                       strNomEmpRep = "Tuv";
                    }
                    else if (strNomEmpAux.indexOf("CASTEK") != -1)
                    {  
                       strNomEmpRep = "Cas";
                    }
                    else if (strNomEmpAux.indexOf("DIMULTI") != -1)
                    {  
                       strNomEmpRep = "Dim";
                    }
                    else if (strNomEmpAux.indexOf("COSENCO") != -1)
                    {  
                       strNomEmpRep = "Cos";
                    }
                    else if (strNomEmpAux.indexOf("ECUATOSA") != -1)
                    {  
                       strNomEmpRep = "Ecu";
                    }
                    else if (strNomEmpAux.indexOf("DETOPACIO") != -1)
                    {  
                       strNomEmpRep = "Det";
                    }
                    
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 0:
                                default:
//                                    strSQL="";
//                                    strSQL+=" SELECT b1.tx_nomCli, b1.tx_ruc";
//                                    strSQL+=" , extract(day from b1.fe_doc)  ||'-'||";
//                                    strSQL+=" 	case extract(month from b1.fe_doc)";
//                                    strSQL+=" 	when 1 then 'Ene' when 2 then 'Feb' when 3 then 'Mar' when 4 then 'Abr'";
//                                    strSQL+=" 	when 5 then 'May' when 6 then 'Jun' when 7 then 'Jul' when 8 then 'Ago'";
//                                    strSQL+=" 	when 9 then 'Sep' when 10 then 'Oct' when 11 then 'Nov' when 12 then 'Dic'";
//                                    strSQL+=" 	end ||'-'||extract(year from b1.fe_doc) as FecReg";
//                                    strSQL+=" , b1.tx_dirCli, cast( '" + getNumDocCancelados() + "' as character varying) AS ne_numDoc1, a2.tx_numAutSRI, a2.tx_fecCad";
//                                    strSQL+=" , (a2.tx_numSer  || '-' || a2.tx_numChq ) as comprobante, b1.tx_obs2, CAST(extract(year from b1.fe_doc) AS Integer) as aniodoc";
//                                    strSQL+=" FROM tbm_cabPag AS b1 INNER JOIN tbm_detPag AS a1";
//                                    strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
//                                    strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
//                                    strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
//                                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
//                                    strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";
//                                    strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
//                                    strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc") + "";
//                                    strSQL+=" GROUP BY b1.tx_nomCli, b1.tx_ruc, b1.fe_doc, b1.tx_dirCli";
//                                    strSQL+=" , a2.tx_numAutSRI, a2.tx_fecCad,a2.tx_numSer, a2.tx_numChq, b1.tx_obs2, b1.fe_doc";

                                    strSQL="";
                                    strSQL+=" SELECT DISTINCT  x.tx_numDoc,x.tx_rucEmp, x.tx_nomEmp, x.tx_dirEmp,x.tx_dirLoc , x.tx_telEmp,x.tx_faxEmp, x.tx_dirWebEmp, x.tx_corEleEmp,  \n";
                                    strSQL+="         x.tx_desConEspEmp,'' as tx_numSerFac, '' as tx_numAutSri,''  as tx_fecCadFac, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.ne_numDoc1,  \n";
                                    strSQL+="         x.ne_numDocFor, x.fe_doc,x.FecReg, \n";
                                    strSQL+="         x.co_cli, x.tx_rucCli, x.tx_nomCli, x.tx_dirCli, x.tx_obs1, x.tx_obs2, \n";
                                    strSQL+="         x.ne_numOrdComCan, x.ne_numCmpVta, x.ne_aniEjeFis , x.tx_fecCadCli,  \n";
                                    strSQL+="  /*  CAMBIAR */      /*x.tx_numAutSRICli, DEJARA DE FUNCIONAR  */   \n";
                                    strSQL+="         x.tx_numAutSRICli, x.nd_tamAutSRIcli,  \n";
                                    strSQL+="         CASE WHEN x.nd_tamAutSRIcli = 10 THEN tx_numAutSRICli ELSE '' END as tx_numAutSRICli10 ,  /* ANTES DE FACTURACION ELECTRONICA */ \n";
                                    strSQL+=" 	      CASE WHEN x.nd_tamAutSRIcli = 37 THEN tx_numAutSRICli ELSE '' END as tx_numAutSRICli37 ,  /* FACTURACION ELECTRONICA */ \n";
                                    strSQL+=" /*  HASTA AKI JoséMario 29/08/2014  */ \n";
                                    strSQL+="         x.ne_valRet /* JoséMario 23Dic2014 */ ,x.fe_autFacEle,x.tx_claAccFacEle, x.tx_numAutFacEle  /* JoséMario 23Dic2014 */ \n";
                                    strSQL+=" FROM (\n";
                                    strSQL+="SELECT ";
                                    strSQL+="(substring(a5.tx_secdoc,1,3) || '-' || substring(a5.tx_secdoc,5,3) || '-' || to_char(a7.ne_numdoc1, '000000000')) as tx_numdoc, ";
                                    strSQL+=" ";
                                    strSQL+= "a1.tx_ruc AS tx_rucEmp, a1.tx_nom AS tx_nomEmp, a1.tx_dir AS tx_dirEmp, a1.tx_tel AS tx_telEmp,";
                                    strSQL+="a1.tx_fax AS tx_faxEmp, a1.tx_dirWeb AS tx_dirWebEmp, a1.tx_corEle AS tx_corEleEmp, a1.tx_desConEsp AS tx_desConEspEmp,";
                                    strSQL+="a5.tx_dir as tx_dirLoc, a4.tx_numSerFac, a4.tx_numAutSri, a4.tx_fecCadFac, ";
                                    strSQL+="b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                                    strSQL+=", b1.ne_numDoc1, lpad(''|| b1.ne_numDoc1,7,'0') AS ne_numDocFor";
                                    strSQL+=", b1.fe_doc, ";
                                    strSQL+=" extract(day from b1.fe_doc)  ||'-'||";
                                    strSQL+=" 	case extract(month from b1.fe_doc)";
                                    strSQL+=" 	when 1 then 'Ene' when 2 then 'Feb' when 3 then 'Mar' when 4 then 'Abr'";
                                    strSQL+=" 	when 5 then 'May' when 6 then 'Jun' when 7 then 'Jul' when 8 then 'Ago'";
                                    strSQL+=" 	when 9 then 'Sep' when 10 then 'Oct' when 11 then 'Nov' when 12 then 'Dic'";
                                    strSQL+=" 	end ||'-'||extract(year from b1.fe_doc) as FecReg";
                                    //--cliente
                                    strSQL+=", b1.co_cli, b1.tx_ruc AS tx_rucCli, b1.tx_nomCli, b1.tx_dirCli";
                                    strSQL+=", b1.tx_obs1, b1.tx_obs2";
                                    strSQL+=", cast( '" + getNumDocCancelados() + "' as character varying) AS ne_numOrdComCan";
                                    strSQL+=", (a6.tx_numSer  || '-' || a6.tx_numChq ) as ne_numCmpVta";
                                    strSQL+=", CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis";
                                    strSQL+=" , a6.tx_fecCad AS tx_fecCadCli, a6.tx_numAutSRI AS tx_numAutSRICli, length( a6.tx_numAutSRI) AS nd_tamAutSRIcli, ABS(b1.nd_monDoc) AS ne_valRet";
                                    strSQL+="  , a7.fe_autFacEle, a7.tx_claAccFacEle, a7.tx_numAutFacEle  /* JoséMario 23Dic2014 */";
                                    strSQL+=" FROM (tbm_cabPag AS b1 INNER JOIN tbm_emp AS a1 ON(b1.co_emp=a1.co_emp)";
                                    strSQL+=" 	INNER JOIN tbm_loc AS a5 ON (b1.co_emp=a5.co_emp AND b1.co_loc=a5.co_loc)";
                                    strSQL+="	LEFT OUTER JOIN tbm_datAutSri AS a4 ON (b1.co_emp=a4.co_emp AND b1.co_loc=a4.co_loc AND b1.co_tipDoc=a4.co_tipDoc) ";
                                    strSQL+="	INNER JOIN tbm_cabTipDoc AS a2 ON (b1.co_emp=a2.co_emp AND b1.co_loc=a2.co_loc AND b1.co_tipDoc=a2.co_tipDoc)";
                                    strSQL+=" )";
                                    strSQL+=" INNER JOIN tbm_detPag AS b2";
                                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                                    strSQL+=" INNER JOIN tbm_pagMovInv AS a6";
                                    strSQL+=" ON b2.co_emp=a6.co_emp AND b2.co_locPag=a6.co_loc AND b2.co_tipDocPag=a6.co_tipDoc AND b2.co_docPag=a6.co_doc AND b2.co_regPag=a6.co_reg";
                                    strSQL+=" INNER JOIN tbm_cabMovInv AS a3";
                                    strSQL+=" ON a6.co_emp=a3.co_emp AND a6.co_loc=a3.co_loc AND a6.co_tipDoc=a3.co_tipDoc AND a6.co_doc=a3.co_doc";
                                    strSQL+=" INNER JOIN tbm_cabPag as a7 ON (b1.co_emp=a7.co_emp AND b1.co_loc=a7.co_loc AND b1.co_tipDoc=a7.co_tipDoc AND b1.co_doc=a7.co_doc)";
                                    strSQL+=" WHERE b1.co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" AND b1.co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" AND b1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" AND b1.co_doc=" + rstCab.getString("co_doc") + "";
                                    strSQL+=" /*AND b1.ne_numDoc1>=a4.ne_numDocDes AND b1.ne_numDoc1<=a4.ne_numDocHas Jota */";
                                    strSQL+=" GROUP BY a5.tx_secDoc,a7.ne_numDoc1, a1.tx_ruc, a1.tx_nom, a1.tx_dir, a1.tx_tel,";
                                    strSQL+=" a1.tx_fax, a1.tx_dirWeb, a1.tx_corEle, a1.tx_desConEsp,a5.tx_dir,";
                                    strSQL+=" a4.tx_numSerFac, a4.tx_numAutSri, a4.tx_fecCadFac, ";
                                    strSQL+=" b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc1";
                                    strSQL+=" , b1.fe_doc, b1.co_cli, b1.tx_ruc,b1.tx_nomCli, b1.tx_dirCli";
                                    strSQL+=" , b1.tx_obs1, b1.tx_obs2, a6.tx_numSer, a6.tx_numChq";
                                    strSQL+=" , a6.tx_fecCad, a6.tx_numAutSRI, b1.nd_monDoc \n";
                                    strSQL+=" , a7.fe_autFacEle, a7.tx_claAccFacEle, a7.tx_numAutFacEle  ) as x";
                                    strSQLRep=strSQL;
                                    System.out.println("strSQLRep: " + strSQLRep);

                                    strSQL="";
                                    strSQL+="SELECT CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis, SUM(a2.nd_basImp) AS nd_basImp";
                                    //--- Inicio bloque comentado el 19/Jun/2015 ---
                                    //Esta linea fue comentada en la version del programa 1.44 (19/Jun/2015) debido a que, al mostrar la leyenda 
                                    //'IMPUESTO A LA RENTA' se consideraba el valor de 10 en la condicion del campo tbm_pagMovInv.nd_porret, 
                                    //correspondente a "Retencion en la fuente 10%". Se tuvo que quitar este valor de 10 en la condicion debido a 
                                    //que ahora ha aparecido el valor de 10 para "Retencion al IVA 10%" y esto se debe mostrar en la leyenda del 
                                    //reporte 'IVA'.
                                    //
                                    //strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8,10) then 'IMPUESTO A LA RENTA'";
                                    //--- Fin bloque comentado el 19/Jun/2015 ------
                                    strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8) then 'IMPUESTO A LA RENTA'";
                                    strSQL+=" 	when round(a2.nd_porret) in(30,70,100,10,20) then '    I.V.A.' end AS Impuesto";
                                    strSQL+=" , round(a2.nd_porret) AS nd_porret";
                                    strSQL+=" , SUM(ABS(a2.nd_abo)) AS nd_abo, (a2.tx_numSer  || '-' || a2.tx_numChq ) as ne_numCmpVta, a2.tx_codSRI";
                                    strSQL+=" FROM (";
                                    strSQL+=" 	tbm_cabPag AS b1";
                                    strSQL+="	INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
                                    strSQL+=" INNER JOIN tbm_detPag AS a1";
                                    strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
                                    strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
                                    strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
                                    strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
                                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                                    strSQL+=" GROUP BY b1.fe_doc";
                                    strSQL+=" , a2.nd_porret, a2.tx_numSer, a2.tx_numChq, a2.tx_codSRI";
                                    strSQL+=" ORDER BY a2.nd_porret";
                                    strSQLSubRep=strSQL;
                                    System.out.println("strSQLSubRep: " + strSQLSubRep);

                                    strNomEmp = "" + objParSis.getNombreEmpresa();
                                    strNomEmp = strNomEmp.substring(0,3);

//                                    if(strNomEmp.toUpperCase().trim().equals("IND"))
//                                          strRutRpt=objRptSis.getRutaReporte(i) + "Cosenco/";
//                                    
//                                    else if(strNomEmp.toUpperCase().trim().equals("DIM"))
//                                          strRutRpt=objRptSis.getRutaReporte(i) + "Dimulti/";
//                                    
//                                    else if(strNomEmp.toUpperCase().trim().equals("TUV"))
//                                          strRutRpt=objRptSis.getRutaReporte(i) + "Tuval/";
//
////                                    else if(strNomEmp.toUpperCase().trim().equals("CAS"))
////                                          strRutRpt=objRptSis.getRutaReporte(i) + "Castek/";
//
//                                     //para cuando las retenciones son de noviembre
////                                    else if(strNomEmp.toUpperCase().trim().equals("TUV"))
////                                          strRutRpt=objRptSis.getRutaReporte(i) + "Tuval_nov/";
//                                    else
                                        strRutRpt=objRptSis.getRutaReporte(i);

                                    strNomRpt=objRptSis.getNombreReporte(i);

                                    //Inicializar los parametros que se van a pasar al reporte.
                                    java.util.Map mapPar = new java.util.HashMap();
                                    if(objParSis.getCodigoUsuario()==1){
                                        if(intTipRpt==2)
                                            mapPar.put("strCamAudRpt", "" + (rstCab.getString("tx_nomUsrIng") + " / " + rstCab.getString("tx_nomUsrMod") + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1.1    ");
                                        else
                                            mapPar.put("strCamAudRpt", "");//sirve para que no salga nada en el pie de pagina
                                    }
                                    else{
                                        mapPar.put("strCamAudRpt", "");//sirve para que no salga nada en el pie de pagina
                                    }
                                    
                                    mapPar.put("codEmp", new Integer(rstCab.getInt("co_emp")));
                                    mapPar.put("codLoc", new Integer(rstCab.getInt("co_loc")));
                                    mapPar.put("codTipDoc", new Integer(rstCab.getInt("co_tipDoc")));
                                    mapPar.put("codDoc", new Integer(rstCab.getInt("co_doc")));
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    //System.out.println("SUBREPORT_DIR: " + strRutRpt);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("RUTA_LOGO", strRutRpt + "Logos" + File.separator + "log" + strNomEmpRep + ".png");

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    stmIns=con.createStatement();
                                    strSQL="";
                                    strSQL+="UPDATE tbm_cabPag";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                                    strSQL+=";";
                                    strSQL+="UPDATE tbm_cabDia";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" AND co_dia=" + rstCab.getString("co_doc") + "";
                                    stmIns.executeUpdate(strSQL);
                                    stmIns.close();
                                    stmIns=null;
                                    strEstImpDoc="S";
                                    break;
                            }
                        }
                    }
                }
            } //if(con!=null)
            
            con.close();
            con = null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }


    private String getNumDocCancelados(){
        String strNumDocCan="";
        String strNumDocIni="";
        String strNumDocAct="";
        try{
            for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                if(objTblMod.isChecked(d, INT_TBL_DAT_CHK)){
                    strNumDocAct=objTblMod.getValueAt(d, INT_TBL_DAT_NUM_DOC).toString();
                    if( ! strNumDocAct.equals(strNumDocIni))
                        strNumDocCan+="" + objTblMod.getValueAt(d, INT_TBL_DAT_NUM_DOC) + ",";
                    strNumDocIni=objTblMod.getValueAt(d, INT_TBL_DAT_NUM_DOC).toString();
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strNumDocCan;
    }

    private boolean seleccionaFacturasAsociadas(int fila, String chequeado){
        boolean blnRes=true;
        String strSerFacFin="";
        String strNumFacFin="";
        String strNumAutFacFin="";
        String strFecCadFin="";

        try{

            strSerFacIni=objTblMod.getValueAt(fila, INT_TBL_DAT_SEC_DOC)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_SEC_DOC).toString();
            strNumFacIni=objTblMod.getValueAt(fila, INT_TBL_DAT_NUM_PED)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_NUM_PED).toString();
            strNumAutFacIni=objTblMod.getValueAt(fila, INT_TBL_DAT_NUM_AUT)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_NUM_AUT).toString();
            strFecCadIni=objTblMod.getValueAt(fila, INT_TBL_DAT_FEC_CAD)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_FEC_CAD).toString();

            for(int j=0; j<objTblMod.getRowCountTrue(); j++){
                strSerFacFin=objTblMod.getValueAt(j, INT_TBL_DAT_SEC_DOC)==null?"":objTblMod.getValueAt(j, INT_TBL_DAT_SEC_DOC).toString();
                strNumFacFin=objTblMod.getValueAt(j, INT_TBL_DAT_NUM_PED)==null?"":objTblMod.getValueAt(j, INT_TBL_DAT_NUM_PED).toString();
                strNumAutFacFin=objTblMod.getValueAt(j, INT_TBL_DAT_NUM_AUT)==null?"":objTblMod.getValueAt(j, INT_TBL_DAT_NUM_AUT).toString();
                strFecCadFin=objTblMod.getValueAt(j, INT_TBL_DAT_FEC_CAD)==null?"":objTblMod.getValueAt(j, INT_TBL_DAT_FEC_CAD).toString();
                if(  (strSerFacIni.equals(strSerFacFin)) &&  (strNumFacIni.equals(strNumFacFin))  && (strNumAutFacIni.equals(strNumAutFacFin))  && (strFecCadIni.equals(strFecCadFin)) ){
                    if(chequeado.equals("S")){
                        objTblMod.setChecked(true, j, INT_TBL_DAT_CHK);
                        objTblMod.setValueAt(objTblMod.getValueAt(j, INT_TBL_DAT_VAL_PEN), j, INT_TBL_DAT_ABO_DOC);
                    }
                    else{
                        objTblMod.setChecked(false, j, INT_TBL_DAT_CHK);
                        objTblMod.setValueAt(null, j, INT_TBL_DAT_ABO_DOC);
                    }
                }
            }

         


        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




    private boolean existeFacPrvNoAsignada(){
        boolean blnRes=false;
        Connection conCam;
        Statement stmCam;
        ResultSet rstCam;
        String strAsiDocRel="";
        try{
            conCam=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCam!=null){
                stmCam=conCam.createStatement();
                for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        strSQL="";
                        strSQL+="SELECT a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a3.nd_valApl, a3.st_asiDocRel";
                        strSQL+=" FROM ";
                        strSQL+=" tbm_cabRecDoc AS a2 INNER JOIN tbr_detrecdoccabmovinv AS a1";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" INNER JOIN tbm_detRecDoc AS a3";
                        strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a1.co_reg=a3.co_reg";
                        strSQL+=" WHERE a1.co_empRel=" + objParSis.getCodigoEmpresa() + " AND a1.co_locRel=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                        strSQL+=" AND a1.co_tipDocRel=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + " AND a1.co_docRel=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                        strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E') AND a3.st_reg NOT IN('I','E')";
                        strSQL+=" AND a3.tx_numChq='" + objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED) + "'";
                        strSQL+=" GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a3.nd_valApl, a3.st_asiDocRel";
                        System.out.println("existeFacPrvNoAsignada: " + strSQL);
                        rstCam=stmCam.executeQuery(strSQL);
                        while(rstCam.next()){

                            strAsiDocRel=rstCam.getObject("st_asiDocRel")==null?"N":rstCam.getString("st_asiDocRel");

                            if(strAsiDocRel.equals("N")){
                                blnRes=true;
                                break;
                            }
                        }

                        rstCam.close();
                        rstCam=null;
                        if(blnRes)
                            break;
                    }



                }



                conCam.close();
                conCam=null;

                stmCam.close();
                stmCam=null;
            }


        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }


    private boolean existenFacturasSeleccionadas(){
        boolean blnRes=false;
        try{
            for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    System.out.println("entro");
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;

    }


    private boolean cargarFechaFacturaProveedor(int fila){
        boolean blnRes=true;
        Connection conFecFacPrv;
        Statement stmFecFacPrv;
        ResultSet rstFecFacPrv;
        try{
            conFecFacPrv=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conFecFacPrv!=null){
                stmFecFacPrv=conFecFacPrv.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a2.fe_venChq AS fe_facPrv";
                strSQL+=" FROM tbm_cabRecDoc AS a1";
                strSQL+=" INNER JOIN tbm_detRecDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbr_detRecDocCabMovInv AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" WHERE a3.co_empRel=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_locRel=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC) + "";
                strSQL+=" AND a3.co_tipDocRel=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC) + "";
                strSQL+=" AND a3.co_docRel=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC) + "";
                strSQL+=" AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                strSQL+=" AND a2.tx_numChq='" + objTblMod.getValueAt(fila, INT_TBL_DAT_NUM_PED) + "'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" , a2.tx_numChq, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad";
                strSQL+=" ORDER BY a2.co_reg";
                System.out.println("cargarFechaFacturaProveedor: " + strSQL);
                rstFecFacPrv=stmFecFacPrv.executeQuery(strSQL);
                if(rstFecFacPrv.next()){
                    dtpFecDoc.setText(objUti.formatearFecha(rstFecFacPrv.getDate("fe_facPrv"), "dd/MM/yyyy"));
                }
                conFecFacPrv.close();
                conFecFacPrv=null;
                stmFecFacPrv.close();
                stmFecFacPrv=null;
                rstFecFacPrv.close();
                rstFecFacPrv=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }

    
    /**
    * Esta función  valida que el proveedor tenga asignado un correo electrónico
    * en el Maestro/Proveedores antes de emitir la retención electrónica.
    * @return true: Si el proveedor ha proporcionado correo electrónico.
    * <BR>false: En el caso contrario.
    */
    private boolean validarDocEle()
    {
        boolean blnRes=false;
        String strCadena="",strMsg, strTit;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        strTit="Mensaje del Sistema Zafiro";
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());        
            stmLoc= conLoc.createStatement();
            strCadena="";
            strCadena+=" SELECT co_cli, st_reg, st_proCorEleFacEle FROM tbm_cli WHERE co_emp=" + objParSis.getCodigoEmpresa() ;
            strCadena+=" AND co_cli=" + txtCodPrv.getText();
            System.out.println("validarDocEle " + strCadena);
            rstLoc=stmLoc.executeQuery(strCadena);
            if(rstLoc.next()){
                 if(rstLoc.getString("st_proCorEleFacEle")==null || rstLoc.getString("st_proCorEleFacEle").equals(""))
                 {
                    blnRes=false;
                    strMsg="<html> El proveedor no tiene asignado un correo electrónico para enviarle el documento electrónico. <BR>" ;
                    strMsg+="Por favor ingresar el correo electrónico del proveedor antes de emitir retención. <html>";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);                   
                 }
                 else{
                     blnRes=true;
                 } 
            }
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;           
            conLoc.close();
            conLoc=null;
         }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    

private void enviarPulsoFacturacionElectronica(){
    objPulFacEle = new ZafPulFacEle();
    objPulFacEle.iniciar();
    System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica CON  ");
}
    
}
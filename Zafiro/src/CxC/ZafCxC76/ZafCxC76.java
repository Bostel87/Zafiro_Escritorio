/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */


package CxC.ZafCxC76;
import GenOD.ZafGenOdDaoPryTra;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafRptSis.ZafRptSis;
import java.util.Vector;
import java.util.ArrayList;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import java.math.BigDecimal;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import ZafReglas.ZafGenGuiRem;
import ZafReglas.ZafImp;
import ZafReglas.ZafMetImp;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCxC76 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP=2;
    final int INT_TBL_DAT_COD_LOC=3;
    final int INT_TBL_DAT_COD_TIP_DOC=4;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=6;
    final int INT_TBL_DAT_COD_DOC=7;
    final int INT_TBL_DAT_COD_REG=8;
    final int INT_TBL_DAT_NUM_DOC=9;
    final int INT_TBL_DAT_FEC_DOC=10;
    final int INT_TBL_DAT_COD_CTA=11;
    final int INT_TBL_DAT_NUM_CTA=12;
    final int INT_TBL_DAT_NOM_CTA=13;
    final int INT_TBL_DAT_COD_CLI=14;
    final int INT_TBL_DAT_NOM_CLI=15;
    final int INT_TBL_DAT_VAL_DOC=16;
    final int INT_TBL_DAT_OBS=17;
    final int INT_TBL_DAT_BUT_OBS=18;
    final int INT_TBL_DAT_BUT_ANE=19;
    final int INT_TBL_DAT_PRO=20;
    //ALMACENAN LOS CAMPOS PRINCIPALES PARA SABER CUALES FACTURAS SE DEBEN CANCELAR

    //Variables generales.
    private ZafDatePicker dtpFecDoc;
    private String strFecDocIni;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMena en JTable.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCta;                           //Ventana de consulta "Cuenta".
    private ZafVenCon vcoCli;                           //Ventana de consulta "Cuenta".
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecucian del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strDesCorCta, strDesLarCta;          //Contenido del campo al obtener el foco.
    private String strNumDoc1;              //Contenido del campo al obtener el foco.
    private String strMonDoc;                           //Contenido del campo al obtener el foco.
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    //Variables de la clase.
    private String strUbiCta;                //Campos: Ubicacian y Estado de autorizacian de la cuenta.
   
    List<ZafImp>oi=new ArrayList<ZafImp>();
     
    private boolean blnNecAutAnu;//true si necesita de autorizacion el documento, si no necesita es false
    private boolean blnDocAut;//true si el documento ya fue autorizado para ser anulado, false si falta todavia autorizarlo

    private String strEstImpDoc;

    private ZafTblCelRenBut objTblCelRenButObs1;
    private ZafTblCelEdiButDlg objTblCelEdiButObs1;
    private ZafTblCelEdiTxt objTblCelEdiTxtObs;

    private String strTipDocNecAutAnu;//si el tipo de documento necesita autorizacion para anularlo


    final int INT_ARL_COD_REG=0;


    private ZafCxC76_01 objCxC76_01;
    private ZafCxC76_02 objCxC76_02;

    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;

    private ZafTblCelRenBut objTblCelRenButFac;
    private ZafTblCelEdiButDlg objTblCelEdiButFac;


    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.

    private ArrayList arlReg, arlDat;
    final int INT_ARL_COD_EMP_PAG=0;
    final int INT_ARL_COD_LOC_PAG=1;
    final int INT_ARL_COD_TIP_DOC_PAG=2;
    final int INT_ARL_COD_DOC_PAG=3;
    final int INT_ARL_COD_REG_PAG=4;
    final int INT_ARL_VAL_APL_PAG=5;
    final int INT_ARL_VAL_APL_PAG_AUX=6;
    final int INT_ARL_VAL_APL_PAG_AUX_MOD_TBL=7;
    final int INT_ARL_COD_EMP_REL=8;
    final int INT_ARL_COD_LOC_REL=9;
    final int INT_ARL_COD_TIP_DOC_REL=10;
    final int INT_ARL_COD_DOC_REL=11;
    final int INT_ARL_COD_REG_REL=12;
    final int INT_ARL_EST_REG_ELI=13;
    
    final int INT_ARL_COD_NUM_DOC=14;

    private ZafRptSis objRptSis;
    private ZafThreadGUIVisPre objThrGUIVisPre;

    private int INT_ENV_REC_IMP_GUIA = 0;


    private String strIpImpGuia;
    private int intPtoImpGui;
    
    Vector vecOD=new Vector();


    /** Crea una nueva instancia de la clase ZafCxC76. */
    public ZafCxC76(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();

            configurarFrm();
            agregarDocLis();
            arlDat=new ArrayList();


        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }


/** Crea una nueva instancia de la clase ZafCxC76 para programa ZafCon58 */
    public ZafCxC76(ZafParSis obj, int codigoEmpresaRelacionada, int codigoLocalRelacionado, int codigoTipoDocumentoRelacionado, int codigoDocumentoRelacionado, int codigoRegistroRelacionado, int codigoMenuRelacionado)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();

            configurarFrm();
            agregarDocLis();
            arlDat=new ArrayList();


            //estos son documentos "173"  y no "175" que son los q necesita el query, asi que hay q hacer algo adicional
            objParSis.setCodigoLocal(codigoLocalRelacionado);
            //esta funcion setea el valor de tipo de documento y codigo de documento
            obtenerInformacionDocumento(codigoEmpresaRelacionada, codigoLocalRelacionado, codigoTipoDocumentoRelacionado, codigoDocumentoRelacionado, codigoRegistroRelacionado);
            objParSis.setCodigoMenu(codigoMenuRelacionado);

            consultarReg();
            objTooBar.setVisible(false);

            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            tblDat.setEnabled(true);



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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
        lbCta = new javax.swing.JLabel();
        txtCodCta = new javax.swing.JTextField();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 70));
        panGenCab.setRequestFocusEnabled(false);
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
        txtDesCorTipDoc.setBounds(130, 4, 78, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 128, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 44, 130, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(130, 44, 90, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(438, 4, 20, 20);

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
        txtDesLarTipDoc.setBounds(208, 4, 230, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(460, 4, 124, 20);

        lblNumDoc1.setText("Número de documento:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(460, 24, 124, 20);

        txtNumDoc1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc1.setToolTipText("Número de egreso");
        txtNumDoc1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc1);
        txtNumDoc1.setBounds(584, 24, 100, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(98, 4, 32, 20);

        lbCta.setText("Cuenta:");
        lbCta.setToolTipText("Cuenta");
        panGenCab.add(lbCta);
        lbCta.setBounds(0, 24, 90, 20);
        panGenCab.add(txtCodCta);
        txtCodCta.setBounds(98, 24, 32, 20);

        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        panGenCab.add(txtDesCorCta);
        txtDesCorCta.setBounds(130, 24, 78, 20);

        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        panGenCab.add(txtDesLarCta);
        txtDesLarCta.setBounds(208, 24, 230, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        panGenCab.add(butCta);
        butCta.setBounds(438, 24, 20, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panGenCab.add(lblMonDoc);
        lblMonDoc.setBounds(460, 44, 122, 20);

        txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMonDocActionPerformed(evt);
            }
        });
        txtMonDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMonDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMonDocFocusLost(evt);
            }
        });
        panGenCab.add(txtMonDoc);
        txtMonDoc.setBounds(584, 44, 100, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

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
    }// </editor-fold>                        

    private void txtNumDoc1FocusGained(java.awt.event.FocusEvent evt) {                                       
        strNumDoc1=txtNumDoc1.getText();
        txtNumDoc1.selectAll();
    }                                      

    private void txtMonDocActionPerformed(java.awt.event.ActionEvent evt) {                                          
        txtMonDoc.transferFocus();
    }                                         

    private void txtMonDocFocusGained(java.awt.event.FocusEvent evt) {                                      
        strMonDoc=txtMonDoc.getText();
        txtMonDoc.selectAll();
    }                                     

    private void txtNumDoc1FocusLost(java.awt.event.FocusEvent evt) {                                     
        //Validar el contenido de la celda salo si ha cambiado.
        if (!txtNumDoc1.getText().equalsIgnoreCase(strNumDoc1))
        {
            actualizarGlo();
        }
    }                                    

    private void txtMonDocFocusLost(java.awt.event.FocusEvent evt) {                                    
        //Validar el contenido de la celda salo si ha cambiado.
        if (!txtMonDoc.getText().equalsIgnoreCase(strMonDoc)){
            //Desmarcar los documentos en el JTable.
            objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), strUbiCta, txtMonDoc.getText(), txtMonDoc.getText());
        }
    }                                   

    private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {                                       
        //Validar el contenido de la celda salo si ha cambiado.
        if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta))
        {
            if (txtDesLarCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConCta(2);
                cargarDocPen();
            }
        }
        else
            txtDesLarCta.setText(strDesLarCta);
    }                                      

    private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {                                         
        strDesLarCta=txtDesLarCta.getText();
        txtDesLarCta.selectAll();
    }                                        

    private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {                                             
        txtDesLarCta.transferFocus();
    }                                            

    private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {                                       
        //Validar el contenido de la celda salo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta))
        {
            if (txtDesCorCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesLarCta.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConCta(1);
                cargarDocPen();
 
            }
        }
        else
            txtDesCorCta.setText(strDesCorCta);
    }                                      

    private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {                                         
        strDesCorCta=txtDesCorCta.getText();
        txtDesCorCta.selectAll();
    }                                        

    private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {                                             
        txtDesCorCta.transferFocus();
    }                                            

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {                                       
        mostrarVenConCta(0);
    }                                      

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {                                          
        //Validar el contenido de la celda salo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }                                         

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {                                          
        //Validar el contenido de la celda salo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
    }                                         

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {                                            
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }                                           

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {                                                
        txtDesLarTipDoc.transferFocus();
    }                                               

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {                                            
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }                                           

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {                                                
        txtDesCorTipDoc.transferFocus();
    }                                               

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {                                          
        mostrarVenConTipDoc(0);
    }                                         

    /** Cerrar la aplicacian. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {                          
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexian si esta abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
//                if(objTooBar.getEstado()=='n')
//                    reversar_PagosAsociados();



                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }                         
//
//    /** Cerrar la aplicacian. */
//    private void exitForm()
//    {
//        dispose();
//    }
        
    // Variables declaration - do not modify                     
    private javax.swing.JButton butCta;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lbCta;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumDoc1;
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
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCta;
    private javax.swing.JTextField txtDesLarTipDoc;
    public javax.swing.JTextField txtMonDoc;
    private javax.swing.JTextField txtNumDoc1;
    // End of variables declaration                   

   
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(584, 4, 100, 20);
                        
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
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.2");
            lblTit.setText(strAux);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtMonDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setVisible(false);
            txtCodCta.setVisible(false);
            objCxC76_02=new ZafCxC76_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConCta();
            //Configurar los JTables.
            configurarTblDat();


            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);

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
     * Esta funcian configura el JTable "tblDat".
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
            vecCab=new Vector(21);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc. ");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Valor");
            vecCab.add(INT_TBL_DAT_OBS,"Observación");
            vecCab.add(INT_TBL_DAT_BUT_OBS,"");
            vecCab.add(INT_TBL_DAT_BUT_ANE,"");
            vecCab.add(INT_TBL_DAT_PRO,"");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_DOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccian.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mena de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PRO).setPreferredWidth(20);



//            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_PAG).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_PAG).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_PAG).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_PAG).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_COD_REG_PAG).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_VAL_APL_PAG).setPreferredWidth(20);


            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            //vecAux.add("" + INT_TBL_DAT_CHK);
            vecAux.add("" + INT_TBL_DAT_OBS);
            vecAux.add("" + INT_TBL_DAT_BUT_OBS);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;


            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_CLI;
            intColTbl[1]=INT_TBL_DAT_NOM_CLI;

            


            //para el boton de observacion
            objTblCelRenButObs1=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(objTblCelRenButObs1);
            objCxC76_01=new ZafCxC76_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButObs1= new ZafTblCelEdiButDlg(objCxC76_01);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellEditor(objTblCelEdiButObs1);
            objTblCelEdiButObs1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strObs2="";
                String strNueObs="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if(  (objTooBar.getEstado()=='c') || (objTooBar.getEstado()=='w')  ){
                        objTblCelEdiButObs1.setCancelarEdicion(true);
                        objCxC76_01.isEditable("N");
                        strObs2=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS).toString();
                        objCxC76_01.setContenido("" + strObs2);
                    }
                    else{
                        objTblCelEdiButObs1.setCancelarEdicion(false);
                        objCxC76_01.isEditable("S");
                        strObs2=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS).toString();
                        objCxC76_01.setContenido("" + strObs2);
                    }
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strNueObs=objCxC76_01.getContenido();
                    objTblMod.setValueAt(strNueObs, tblDat.getSelectedRow(), INT_TBL_DAT_OBS);
                }
            });
            

            //para el boton de anexo-facturas
            objTblCelRenButFac=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE).setCellRenderer(objTblCelRenButFac);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intCodCli=0;
                int intCodEmpRef=-1;
                int intCodLocRef=-1;
                int intCodTipDocRef=-1;
                int intCodDocRef=-1;
                int intCodRegRef=-1;
                BigDecimal bdeValApl=new BigDecimal("0");
                String strLinPro="";

                BigDecimal bdeValPndGua=new BigDecimal("0");
                BigDecimal bdeValAplGua=new BigDecimal("0");
                BigDecimal bdeValDisGua=new BigDecimal("0");

                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intFilSel, intFilSelVenCon[], i, j;

                    if (tblDat.getSelectedColumn()==INT_TBL_DAT_BUT_ANE){
                        if(objTooBar.getEstado()=='n')
                            objCxC76_02.setEditable("S");
                        else
                            objCxC76_02.setEditable("N");

                        intCodCli=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CLI)==null?0:Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CLI).toString());
                        intCodEmpRef=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)==null?0:Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString());
                        intCodLocRef=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC)==null?0:Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString());
                        intCodTipDocRef=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC)==null?0:Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC).toString());
                        intCodDocRef=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC)==null?0:Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString());
                        intCodRegRef=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_REG)==null?0:Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_REG).toString());

                        bdeValApl=new BigDecimal(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC)==null?"0":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_DOC).toString());
                        //arlFac=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ARL_FAC_APL).toString().equals("")?null:((ArrayList)objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ARL_FAC_APL));
                        strLinPro=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PRO)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PRO).toString();
                        if(strLinPro.equals("")){
                            objCxC76_02.setArregloDatosFacturasAplicadas(arlDat);
                            objCxC76_02.getFacturasAplicables(objTooBar.getEstado(), intCodEmpRef, intCodLocRef, intCodTipDocRef, intCodDocRef, intCodRegRef, intCodCli, bdeValApl, txtCodTipDoc.getText(), txtCodDoc.getText());
                        }
                        else{
                            objCxC76_02.setArregloDatosFacturasAplicadas(arlDat);
                            objCxC76_02.getFacturasAplicables(objTooBar.getEstado(), intCodEmpRef, intCodLocRef, intCodTipDocRef, intCodDocRef, intCodRegRef,intCodCli, bdeValApl, txtCodTipDoc.getText(), txtCodDoc.getText());
                        }
                        objCxC76_02.setVisible(true);
                    }
                    
                    if (objCxC76_02.getSelectedButton()==objCxC76_02.INT_BUT_ACE){
                        intFilSel=tblDat.getSelectedRow();
                        intFilSelVenCon=objCxC76_02.getFilasSeleccionadas();

                        for (i=0; i<intFilSelVenCon.length; i++){
                            if ( ! objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_LIN).equals("P"));{
                                objCxC76_02.setFilaProcesada(intFilSelVenCon[i]);

                                //quitar los registros q estaban anteriormente en el arreglo, solo los relacionados a la fila seleccionada
                                String strArlCodEmpCob="", strArlCodLocCob="", strArlCodTipDocCob="", strArlCodDocCob="", strArlCodRegCob="";
                                String strTblCodEmpCob="", strTblCodLocCob="", strTblCodTipDocCob="", strTblCodDocCob="", strTblCodRegCob="";

                                strTblCodEmpCob=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString();
                                strTblCodLocCob=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString();
                                strTblCodTipDocCob=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIP_DOC).toString();
                                strTblCodDocCob=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC).toString();
                                strTblCodRegCob=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_REG).toString();


                                for(int g=0; g<arlDat.size(); g++){
                                    strArlCodEmpCob=objUti.getStringValueAt(arlDat, g, INT_ARL_COD_EMP_REL);
                                    strArlCodLocCob=objUti.getStringValueAt(arlDat, g, INT_ARL_COD_EMP_REL);
                                    strArlCodTipDocCob=objUti.getStringValueAt(arlDat, g, INT_ARL_COD_EMP_REL);
                                    strArlCodDocCob=objUti.getStringValueAt(arlDat, g, INT_ARL_COD_EMP_REL);
                                    strArlCodRegCob=objUti.getStringValueAt(arlDat, g, INT_ARL_COD_EMP_REL);
                                    if( (strTblCodEmpCob.equals(strArlCodEmpCob)) && (strTblCodLocCob.equals(strArlCodLocCob)) && (strTblCodTipDocCob.equals(strArlCodTipDocCob)) && (strTblCodDocCob.equals(strArlCodDocCob)) && (strTblCodRegCob.equals(strArlCodRegCob))  ){
                                        objUti.setStringValueAt(arlDat, g, INT_ARL_EST_REG_ELI, "E");
                                    }
                                }

                                objTblMod.setValueAt("P", intFilSel, INT_TBL_DAT_PRO);
                                objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK);
                                calcularAboTot();
                                objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                                actualizarGlo();

                                arlReg=new ArrayList();
                                arlReg.add(INT_ARL_COD_EMP_PAG,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_EMP)));
                                arlReg.add(INT_ARL_COD_LOC_PAG,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_LOC)));
                                arlReg.add(INT_ARL_COD_TIP_DOC_PAG, "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_TIP_DOC)));
                                arlReg.add(INT_ARL_COD_DOC_PAG,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_DOC)));
                                arlReg.add(INT_ARL_COD_REG_PAG,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_REG)));
                                arlReg.add(INT_ARL_VAL_APL_PAG,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_VAL_APL_USR)));
                                arlReg.add(INT_ARL_VAL_APL_PAG_AUX, "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_VAL_APL_USR_AUX)));
                                arlReg.add(INT_ARL_VAL_APL_PAG_AUX, "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_VAL_APL_USR_AUX_MOD_TBL)));
                                arlReg.add(INT_ARL_COD_EMP_REL,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_EMP_REL)));
                                arlReg.add(INT_ARL_COD_LOC_REL,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_LOC_REL)));
                                arlReg.add(INT_ARL_COD_TIP_DOC_REL, "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_TIP_DOC_REL)));
                                arlReg.add(INT_ARL_COD_DOC_REL,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_DOC_REL)));
                                arlReg.add(INT_ARL_COD_REG_REL,     "" + (objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_COD_REG_REL)));
                                arlReg.add(INT_ARL_EST_REG_ELI,     "");
                                
                                /*AGREGADO NUEVO SERVICIO UNO*/
                                arlReg.add(INT_ARL_COD_NUM_DOC,     "" +(objCxC76_02.getValueAt(intFilSelVenCon[i], objCxC76_02.INT_TBL_DAT_NUM_DOC)));
                                /*AGREGADO NUEVO SERVICIO UNO*/
                                
                                arlDat.add(arlReg);

                                System.out.println("2 - arlDat: " + arlDat.toString());
                            }
                        }

                        tblDat.requestFocus();
                        objTblMod.removeEmptyRows();
                    }
                }

            });



            //para el campo de texto de observacion
            objTblCelEdiTxtObs=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setCellEditor(objTblCelEdiTxtObs);
            objTblCelEdiTxtObs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_VAL_DOC);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);

            //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_REG);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularAboTot();
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                    //objAsiDia.generarDiario(tblDat, INT_TBL_DAT_CHK, Integer.parseInt(txtCodTipDoc.getText()), INT_TBL_DAT_COD_CTA, INT_TBL_DAT_VAL_DOC);

                    actualizarGlo();
                }
            });

            cargarIpPuertoGuiaEmp();

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
    * Permite obtener la Ip de impresion de guia.
    */
    public void cargarIpPuertoGuiaEmp(){
      try{
         con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
         if(con!=null){
            stm=con.createStatement();
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.co_loc, a1.tx_dirser, a1.ne_pueser FROM tbm_serCliSer AS a ";
            strSQL+=" INNER JOIN tbm_serCliSerLoc AS a1 ON( a1.co_ser=a1.co_ser ) ";
            strSQL+=" WHERE a.co_ser=1  AND a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc="+objParSis.getCodigoLocal() + " ";
            rst=stm.executeQuery(strSQL);
            if(rst.next()){
                strIpImpGuia=rst.getString("tx_dirser");
                intPtoImpGui=rst.getInt("ne_pueser");
            }
            rst.close();
            stm.close();
            stm=null;
            rst=null;
            con.close();
            con=null;
        }
      }
      catch(java.sql.SQLException e){
          objUti.mostrarMsgErr_F1(this, e);
      }
      catch(Exception Evt)  {
          objUti.mostrarMsgErr_F1(this, Evt);
      }
    }




    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Ademas de los botones de navegacian
     * que permiten desplazarse al primero, anterior, siguiente y altimo registro.
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
                            if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                                if(inactivarCampos()){
                                }
                            }
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                        if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                            if(inactivarCampos()){
                            }
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
            cargarDetReg();
            txtDesCorTipDoc.requestFocus();
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
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
                            if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                                if(inactivarCampos()){
                                }
                            }
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                        if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                            if(inactivarCampos()){
                            }
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

        public void clickImprimir(){
            

            
            
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
                            if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                                if(inactivarCampos()){
                                }
                            }
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                        if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                            if(inactivarCampos()){
                            }
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
//                txtMonDoc.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
//                if (!txtCodTipDoc.getText().equals(""))
//                    mostrarCtaPre();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objAsiDia.setEditable(true);
                txtNumDoc1.selectAll();
                txtNumDoc1.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
                txtMonDoc.setEditable(false);
                
                dtpFecDoc.setEnabled(true);
                txtNumDoc1.setEnabled(false);
                //objTblMod.insertRow();
                objTblMod.setDataModelChanged(false);
                cargarDocPen();
                
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

        public void clickModificar(){
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtMonDoc.setEditable(false);            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objAsiDia.setEditable(true);
            cargarReg();
            txtNumDoc1.selectAll();
            txtNumDoc1.requestFocus();
            if(inactivarCampos()){
            }
            
            
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
                            if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                                if(inactivarCampos()){
                                }
                            }
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                        if(  (objTooBar.getEstado()=='x') ||  (objTooBar.getEstado()=='m') ){
                            if(inactivarCampos()){
                            }
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
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.removeEmptyRows();
                objTblMod.removeAllRows();
                arlDat.clear();
                
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
        
        public boolean vistaPreliminar(){
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
            
        }
        
        public boolean aceptar()
        {
            return true;
        }
        
        public boolean imprimir(){           
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(0);
                objThrGUIVisPre.start();
            }
            return true;
            
        }
        
        public boolean beforeInsertar(){
            boolean blnRes=true;

            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();

            return blnRes;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                blnRes=false;
            }

            
            if (!isCamVal())
                blnRes=false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();

            return blnRes;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya esta ELIMINADO.\nNo es posible eliminar un documento eliminado.");
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

        public boolean beforeImprimir(){
            boolean blnRes=true;
            System.out.println("before imprimir");
            if(strEstImpDoc.equals("N")){
            }
            else{
                mostrarMsgInf("<HTML>El documento tiene estado impreso.<BR>No se puede imprimir un documento impreso si no se cuenta con los permisos respectivos.<BR>Si necesita imprimir el documento, solicite al Administrador del Sistema le conceda los permisos respectivos.</HTML>");
                blnRes=false;
            }
            return blnRes;
        }

        public boolean beforeVistaPreliminar()
        {
            boolean blnRes=true;
//            if(strEstImpDoc.equals("N")){
//            }
//            else{
//                mostrarMsgInf("<HTML>El documento tiene estado impreso.<BR>No se puede imprimir un documento impreso si no se cuenta con los permisos respectivos.<BR>Si necesita imprimir el documento, solicite al Administrador del Sistema le conceda los permisos respectivos.</HTML>");
//                blnRes=false;
//            }
            return blnRes;
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
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            //Configurar JFrame de acuerdo al estado del registro.
            objTooBar.setEstado('w');
            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtDesLarCta.setText("");
            consultarReg();
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            tblDat.setEnabled(true);
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;

            return true;
        }

        public boolean afterConsultar()
        {
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            tblDat.setEnabled(true);
            return true;
        }

        public boolean afterModificar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('w');
            cargarReg();
            strFecDocIni=dtpFecDoc.getText();
            objTblMod.clearDataSavedBeforeRemoveRow();
            return true;
        }

        public boolean afterEliminar()
        {
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
     * Esta funcian determina si los campos son validos.
     * @return true: Si los campos son validos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
//        String strExiVal="";

        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        objTblMod.removeEmptyRows();

        if(objTblMod.getRowCountTrue()<=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Se debe registrar algún valor para guardar el documento.<BR>Escriba un valor y vuelva a intentarlo.</HTML>");
            return false;
        }

        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
//        //Validar la "Cuenta".
//        if (txtCodCta.getText().equals("")){
//            tabFrm.setSelectedIndex(0);
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta y vuelva a intentarlo.</HTML>");
//            txtDesCorCta.requestFocus();
//            return false;
//        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
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
                                    System.out.println("POR YES");
                                    return true;
                                case 1: //NO_OPTION
                                    System.out.println("POR NO");
                                    return false;
                                case 2: //CANCEL_OPTION
                                    System.out.println("POR CANCEL");
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
        //Validar que el "Cadigo alterno" no se repita.
            if (!txtNumDoc1.getText().equals("")){
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
                    mostrarMsgInf("<HTML>El número de egreso <FONT COLOR=\"blue\">" + txtNumDoc1.getText() + "</FONT> ya existe.<BR>Escriba otro número de egreso y vuelva a intentarlo.</HTML>");
                    txtNumDoc1.selectAll();
                    txtNumDoc1.requestFocus();
                    return false;
                }
            }
        
        
        

        //Validar que el "Diario esta cuadrado".
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
        return true;
    }

    /**
     * Esta funcian muestra un mensaje informativo al usuario. Se podraa utilizar
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
     * Esta funcian muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta funcian muestra un mensaje de advertencia al usuario. Se podraa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifaquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta funcian inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        ZafGenOdDaoPryTra genODDaoTra=new ZafGenOdDaoPryTra();
        ZafGenOdPryTra genODTra=new ZafGenOdPryTra();
        ZafCnfDoc objValCnf=new ZafCnfDoc(objParSis,this);
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            INT_ENV_REC_IMP_GUIA = 0;
            if (con!=null){
                if (insertarCab()){
                    if(actualiza_tbmPagMovInv()){
                        if (cambiarEstado_tbmDepCliRegDirBanPag("S")){
                            if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc1.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"))){
                                con.commit();
                                blnRes=true;
                                if(INT_ENV_REC_IMP_GUIA==1){ //enviarRequisitoImp(strIpImpGuia, intPtoImpGui);
                                    System.out.println("SE IMPRIMIO  :) ");
                                    
                                    ZafImp im=new ZafImp();
                                    boolean valida;
                                    ZafMetImp om;
                                    //for (int i = 0; i < oi.size(); i++) {
                                    Iterator itVecOD=vecOD.iterator();
                                    //for (int i = 0; i < vecOD.size(); i++) {
                                    while(itVecOD.hasNext()){
                                        String strDat=(String)itVecOD.next();
                                        String[] strArrDat=strDat.split("-");

                                        int intCodEmp=Integer.parseInt((String)strArrDat[0]);
                                        int intCodLoc=Integer.parseInt((String)strArrDat[1]);
                                        int intCodTipDoc2=Integer.parseInt((String)strArrDat[2]);
                                        int intCodDocu=Integer.parseInt((String)strArrDat[3]);
                                        //int intNumDoc=Integer.parseInt((String)strArrDat[4]);
                                        
                                        //int intTipDocOD=genODDaoTra.verificarTipDocOD(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                        /*if(intTipDocOD==101 || intTipDocOD==102){                                        
                                            im.setEmp(intCodEmp);
                                            im.setLoc(intCodLoc);
                                            im.setTipdoc(intCodTipDoc2);
                                            im.setCoDoc(intCodDocu);
                                            im.setNumdoc(intNumDoc);
                                            om=new ZafMetImp(im);
                                            ZafGenGuiRem objZafGuiRem=new ZafGenGuiRem();
                                             valida=om.validarOD(con);
                                             if (valida==false) {
                                                 om.impresionNormal2(con);
                                             }
                                             boolean booRetTer = objZafGuiRem.generarProTermL(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, intNumDoc);
                                        }else{*/
                                        if(!objValCnf.isDocIngPenCnfxFac(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, "I")){
                                            if(!(genODTra.validarODExs(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu))){
                                                if(genODTra.generarNumOD(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, false)){
                                                    con.commit();
                                                    String strIp=genODTra.obtenerIpSerImp(con);
                                                    genODTra.imprimirOdLocal(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu, strIp);                                                
                                                    genODTra.generarTermL(con, intCodEmp, intCodLoc, intCodTipDoc2, intCodDocu);
                                                }else{
                                                    con.rollback();
                                                }
                                            }
                                        }
                                    }
                                    vecOD.clear();
                                    
                                } //enviarRequisitoImp(strIpImpGuia, intPtoImpGui);
                                    
                                
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
     * Esta funcian permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que salo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc=" + strAux + "";
                strAux=txtCodCta.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cta=" + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc=" + strAux + "";
                strAux=txtNumDoc1.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1=" + strAux + "";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
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
     * Esta funcian actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarCab()){
                    if (eliminarDet()){
//                        if (insertarDet()){
                            if (actualizarDet()){
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
//                        }
//                        else
//                            con.rollback();
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
     * Esta funcian elimina el registro de la base de datos.
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
     * Esta funcian anula el registro de la base de datos.
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
                if (anularCab()){
                    if(actualizar_tbmPagMovInv()){
                        if(cambiarEstado_tbmDepCliRegDirBanPag("N")){
                            if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
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
     * Esta funcian permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        int intUltReg;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener el cadigo del altimo registro.
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
                strSQL+="INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_ven, ne_numdoc1, ";
                strSQL+="ne_numdoc2, co_cta, co_cli, tx_ruc, tx_nomcli, tx_dircli, nd_mondoc, ";
                strSQL+="tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, ";
                strSQL+="co_dia, co_ben, tx_benchq, tx_mondocpal, co_mnu, st_regrep, st_imp, ";
                strSQL+="tx_obssolaut, tx_obsautsol, st_aut, ne_valaut, st_condepban, ";
                strSQL+="st_autanu, fe_autanu, co_usrautanu, tx_comautanu, tx_coming, tx_commod)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa() + "";//co_emp
                strSQL+="," + objParSis.getCodigoLocal() + "";//co_loc
                strSQL+=","+ txtCodTipDoc.getText() + "";//co_tipdoc
                strSQL+="," + txtCodDoc.getText() + "";//co_doc
                strSQL+=",'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//fe_ven
                strSQL+="," + txtNumDoc1.getText() + "";//ne_numdoc1
                strSQL+=",Null";//ne_numdoc2
                strSQL+=",Null";//co_cta
                strSQL+=",Null";//co_cli
                strSQL+=",Null";//tx_ruc
                strSQL+=",Null";//tx_nomcli
                strSQL+=",Null";//tx_dircli
                strSQL+="," + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3) + "";//nd_mondoc
                strSQL+="," + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+="," + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=",'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=",'" + strAux + "'";//fe_ing
                strSQL+=",'" + strAux + "'";//fe_ultmod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+=",Null";//co_dia
                strSQL+=",Null";//co_ben
                strSQL+=",Null";//tx_benchq
                strSQL+=",Null";//tx_mondocpal
                strSQL+="," + objParSis.getCodigoMenu() + "";//co_mnu
                strSQL+=",'I'";//st_regrep
                strSQL+=",'N'";//st_imp
                strSQL+=",Null";//tx_obssolaut
                strSQL+=",Null";//tx_obsautsol
                strSQL+=",Null";//st_aut
                strSQL+=",Null";//ne_valaut
                strSQL+=",Null";//st_condepban
                strSQL+="," + (strTipDocNecAutAnu.equals("")?"Null":strTipDocNecAutAnu) + "";//st_autanu
                strSQL+=",Null";//fe_autanu
                strSQL+=",Null";//co_usrautanu
                strSQL+=",Null";//tx_comautanu
                strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_commod
                strSQL+=")";
                System.out.println("INSERTAR CABECERA: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
  
    
      
    /**
     * Esta funcian permite cargar el registro seleccionado.
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
     * Esta funcian permite cargar la cabecera del registro seleccionado.
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
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.fe_ven, a1.ne_numDoc1";
                strSQL+=", a1.co_cta, a3.tx_codCta, a3.tx_desLar AS a3_tx_desLar";
                strSQL+=", a1.nd_monDoc, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+=", a1.st_autAnu, a4.tx_ubiCta";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" LEFT OUTER JOIN (tbm_cabTipDoc AS a2 INNER JOIN tbm_detTipDoc AS a4 ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                System.out.println("cargarCabReg: " + strSQL);
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
                    strUbiCta=rst.getString("tx_ubiCta");
                                        
                    strAux=rst.getString("ne_numDoc1");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cta");
                    txtCodCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_codCta");
                    txtDesCorCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("a3_tx_desLar");
                    txtDesLarCta.setText((strAux==null)?"":strAux);
                    txtMonDoc.setText("" + Math.abs(rst.getDouble("nd_monDoc")));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));

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
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                strSQL+=" AND st_necAutAnuDoc='S'";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    blnNecAutAnu=true;
                }
           

            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posician relativa del registro.
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
     * Esta funcian permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();
            
            if( ! txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a6.co_emp AS co_emp, a6.co_locreldepcliregdirban AS co_loc";
                    strSQL+=" , a6.co_tipdocreldepcliregdirban AS co_tipDoc, a6.co_docreldepcliregdirban AS co_doc, a6.co_regreldepcliregdirban AS co_reg,";
                    strSQL+=" a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                    strSQL+=" , b1.ne_numDoc1, b1.fe_doc, b1.co_cta, a4.tx_codCta, a4.tx_desLar AS tx_nomCta";
                    strSQL+=" , a2.co_cli, a5.tx_nom AS tx_nomCli, a2.nd_valDep, a2.tx_obs1";
                    strSQL+=" FROM  tbm_cabPag AS a1";
                    strSQL+=" INNER JOIN (tbm_detPag AS a6 INNER JOIN tbm_cabTipDoc AS a3";
                    strSQL+=" 	    ON a6.co_emp=a3.co_emp AND a6.co_locreldepcliregdirban=a3.co_loc AND a6.co_tipdocreldepcliregdirban=a3.co_tipDoc";
                    strSQL+=" 	    INNER JOIN (tbm_cabPag AS b1 INNER JOIN tbm_plaCta AS a4 ON b1.co_emp=a4.co_emp AND b1.co_cta=a4.co_cta)";
                    strSQL+=" 	    ON a6.co_emp=b1.co_emp AND a6.co_locreldepcliregdirban=b1.co_loc";
                    strSQL+=" 	    AND a6.co_tipdocreldepcliregdirban=b1.co_tipDoc AND a6.co_docreldepcliregdirban=b1.co_doc)";
                    strSQL+=" ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                    strSQL+=" INNER JOIN tbm_depCliRegDirBanPag AS a2";
                    strSQL+=" ON a6.co_emp=a2.co_emp AND a6.co_locreldepcliregdirban=a2.co_loc";
                    strSQL+=" AND a6.co_tipdocreldepcliregdirban=a2.co_tipDoc AND a6.co_docreldepcliregdirban=a2.co_doc AND a6.co_regreldepcliregdirban=a2.co_reg";
                    strSQL+=" INNER JOIN tbm_cli AS a5";
                    strSQL+=" ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                    strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                    strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                    strSQL+=" AND a2.co_cli IS NOT NULL AND a2.st_reg='A' AND a1.st_reg NOT IN('E') AND a6.st_reg NOT IN('E')";
                    strSQL+=" GROUP BY a6.co_emp, a6.co_locreldepcliregdirban, a6.co_tipdocreldepcliregdirban";
                    strSQL+=" , a6.co_docreldepcliregdirban, a6.co_regreldepcliregdirban,";
                    strSQL+=" a3.tx_desCor, a3.tx_desLar, b1.ne_numDoc1,";
                    strSQL+=" b1.fe_doc, b1.co_cta, a4.tx_codCta, a4.tx_desLar, a2.co_cli, a5.tx_nom, a2.tx_obs1, a2.nd_valDep";
                    strSQL+=" ORDER BY b1.ne_numDoc1, a6.co_regreldepcliregdirban";
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,             "");
                        vecReg.add(INT_TBL_DAT_CHK,             null);
                        vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getObject("tx_desCorTipDoc")==null?"":rst.getString("tx_desCorTipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getObject("tx_desLarTipDoc")==null?"":rst.getString("tx_desLarTipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,         "" + rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,         "" + rst.getObject("co_reg")==null?"":rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + rst.getObject("ne_numDoc1")==null?"":rst.getString("ne_numDoc1"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CTA,         "" + rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,         "" + rst.getObject("tx_codCta")==null?"":rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_DAT_NOM_CTA,         "" + rst.getObject("tx_nomCta")==null?"":rst.getString("tx_nomCta"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,         "" + rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,         "" + rst.getObject("tx_nomCli")==null?"":rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,         "" + rst.getObject("nd_valDep")==null?"":rst.getString("nd_valDep"));
                        vecReg.add(INT_TBL_DAT_OBS,             "" + rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                        vecReg.add(INT_TBL_DAT_BUT_OBS,         "");
                        vecReg.add(INT_TBL_DAT_BUT_ANE,         "");
                        vecReg.add(INT_TBL_DAT_PRO,             "");

                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);

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
     * Esta funcian permite actualizar la cabecera de un registro.
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
                strSQL+=", ne_numDoc1=" + objUti.codificar(txtNumDoc1.getText(),2);
                strSQL+=", co_cta=" + txtCodCta.getText();
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
     * Esta funcian permite eliminar la cabecera de un registro.
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
     * Esta funcian permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()
    {
        boolean blnRes=true;
        int intCodReg=-1;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                System.out.println("arlAux.size(): " + arlAux.size());
                if(arlAux.size()>0){
                    for(int i=0;i<arlAux.size();i++){
                        intCodReg=objUti.getIntValueAt(arlAux, i, INT_ARL_COD_REG);
                        strSQL="";
                        strSQL+="SELECT *FROM tbm_depcliregdirbanpag";
                        strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                        strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                        strSQL+=" AND co_reg=" + intCodReg + "";
                        strSQL+=" AND st_valapl='N'";
                        System.out.println("select -> eliminarDet: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strSQL="";
                            strSQL+="UPDATE tbm_depcliregdirbanpag";
                            strSQL+=" SET st_reg='E'";
                            strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                            strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                            strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                            strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                            strSQL+=" AND co_reg=" + intCodReg + "";
                            System.out.println("update -> eliminarDet: " + strSQL);
                            stm.executeUpdate(strSQL);
                        }
                    }
                    rst.close();
                    rst=null;
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
     * Esta funcian permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        boolean blnRes=true;
        int intCodReg=-1;
        String strEstRepDet="";
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                for(int i=0;i<objTblMod.getRowCountTrue();i++){
                    intCodReg=objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG)==null?-1:Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG).toString());
                        strSQL="";
                        strSQL+="SELECT *FROM tbm_depcliregdirbanpag";
                        strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                        strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                        strSQL+=" AND co_reg=" + intCodReg + "";
                        strSQL+=" AND st_valapl='N'";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strSQL="";
                            strSQL+="UPDATE tbm_depcliregdirbanpag";
                            strSQL+=" SET ";
                            strSQL+=" co_cli=" + (objTblMod.getValueAt(i, INT_TBL_DAT_COD_CLI)==null?"Null":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CLI).equals("")?"Null":objTblMod.getValueAt(i, INT_TBL_DAT_COD_CLI).toString())) + "";//co_cli
                            strSQL+=", nd_valdep=" + intSig*Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_DOC), 3)) + "";//nd_valdep
                            strSQL+=", tx_obs1=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + "";//tx_obs1

                            strEstRepDet=rst.getObject("st_regRep")==null?"":rst.getString("st_regRep");

                            if(strEstRepDet.equals("I"))
                                strSQL+=", st_regrep='I'";//st_regrep
                            else
                                strSQL+=", st_regrep='M'";//st_regrep

                            strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                            strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                            strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                            strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                            strSQL+=" AND co_reg=" + intCodReg + "";
                            stm.executeUpdate(strSQL);
                        }


                }
                rst.close();
                rst=null;
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
     * Esta funcian permite anular la cabecera de un registro.
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
     * Esta funcian permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningan problema.
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
            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtDesLarCta.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc1.setText("");
            objTblMod.removeAllRows();
            txtMonDoc.setText("");
            objAsiDia.inicializar();
            txaObs1.setText("");
            txaObs2.setText("");
            objCxC76_02.getArregloDatosFacturasAplicadas().clear();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta funcian configura la "Ventana de consulta" que sera utilizada para
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
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
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
     * Esta funcian configura la "Ventana de consulta" que sera utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a2.tx_ubiCta");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Ubicación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    


    /**
     * Esta funcian muestra el tipo de documento predeterminado del programa.
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
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1 ";
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
                System.out.println("mostrarTipDocPre: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc1.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strTipDocNecAutAnu=rst.getString("st_necautanudoc");
                    
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
     * Esta funcian muestra la cuenta contable predeterminado del programa
     * de acuerdo al tipo de documento predeterminado.
     * @return true: Si se pudo mostrar la cuenta contable predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarCtaPre()
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
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut, a1.ne_ultnumchq";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();

                System.out.println("ESTADO: " + objTooBar.getEstado());

                if(objTooBar.getEstado()=='n')
                    strSQL+=" AND a2.st_reg='S'";

                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodCta.setText(rst.getString("co_cta"));
                    txtDesCorCta.setText(rst.getString("tx_codCta"));
                    txtDesLarCta.setText(rst.getString("tx_desLar"));
                    strUbiCta=rst.getString("tx_ubiCta");
                    
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
     * Esta funcian permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de basqueda determina si se debe hacer
     * una basqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se esta buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcian que desea utilizar.
     * @param intTipBus El tipo de basqueda a realizar.
     * @return true: Si no se presenta ningan problema.
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



                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        mostrarCtaPre();
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    break;
                case 1: //Basqueda directa por "Descripcian corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        mostrarCtaPre();
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            mostrarCtaPre();
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
                case 2: //Basqueda directa por "Descripcian larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        mostrarCtaPre();
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            mostrarCtaPre();
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
     * Esta funcian permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de basqueda determina si se debe hacer
     * una basqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se esta buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcian que desea utilizar.
     * @param intTipBus El tipo de basqueda a realizar.
     * @return true: Si no se presenta ningan problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCta(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que salo se pueda seleccionar la "Cuenta" si se selecciona el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
                strUbiCta="";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" ORDER BY a1.tx_codCta";
                vcoCta.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.show();
                        if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            regenerarDiario();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                            cargarDocPen();
                        }
                        break;
                    case 1: //Basqueda directa por "Namero de cuenta".
                        if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            regenerarDiario();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                            cargarDocPen();
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(0);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta=vcoCta.getValueAt(4);
                                regenerarDiario();
                                actualizarGlo();
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();
                                cargarDocPen();
                            }
                            else
                            {
                                txtDesCorCta.setText(strDesCorCta);
                            }
                        }
                        break;
                    case 2: //Basqueda directa por "Descripcian larga".
                        if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            strUbiCta=vcoCta.getValueAt(4);
                            regenerarDiario();
                            actualizarGlo();
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                            cargarDocPen();
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                                strUbiCta=vcoCta.getValueAt(4);
                                regenerarDiario();
                                actualizarGlo();
                                txtNumDoc1.selectAll();
                                txtNumDoc1.requestFocus();
                                cargarDocPen();
                            }
                            else
                            {
                                txtDesLarCta.setText(strDesLarCta);
                            }
                        }
                        break;
                }

                

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
     * Se la usa en el sistema para determinar si existe algan cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentara un mensaje advirtiendo que si no guarda los cambios los perdera.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
     * Esta funcian se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc1.getDocument().addDocumentListener(objDocLis);
        txtCodCta.getDocument().addDocumentListener(objDocLis);
        txtDesCorCta.getDocument().addDocumentListener(objDocLis);
        txtDesLarCta.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta funcian se encarga de agregar el listener "DocumentListener" a los objetos
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
//                reversar_PagosAsociados();
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    

    /**
     * Esta funcian se utiliza para regenerar el asiento de diario.
     * La idea principal de asta funcian es regenerar el asiento de diario. Pero,
     * puede darse el caso en el que el asiento de diario fue modificado manualmente
     * por el usuario. En  cuyo caso, primero se pregunta si desea regenerar el asiento
     * de diario, no regenerarlo o cancelar.
     * @return true: Si se decidia regenerar/no regenerar el asiento de diario.
     * <BR>false: Si se cancela la regeneracian del asiento de diario.
     * Nota.- Como se puede apreciar la funcian retorna "false" salo cuando se dia
     * click en el botan "Cancelar".
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
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
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
            objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
        }
        return blnRes;
    }

    /**
     * Esta funcian obtiene la descripcian larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la funcian devuelve una cadena vacaa.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    
    /**
     * Esta funcian se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo(){
        int i;
        strAux="";
        strAux+=txtDesCorTipDoc.getText() + ": " + txtNumDoc1.getText();
        strAux+="; Cuenta: " + txtDesLarCta.getText();
        strAux+="; Cliente/Valor: ";
        for (i=0; i<objTblMod.getRowCountTrue(); i++){
            if (objTblMod.isChecked(i, INT_TBL_DAT_VAL_DOC)){
                strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_NOM_CLI) + " / ";
                strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC) + ", ";
            }
        }
        objAsiDia.setGlosa(strAux);
    }
    


    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mas espacio.
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
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código del registro";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor depositado";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación";
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
    private boolean inactivarCampos(){
        boolean blnRes=true;
        try{
            //objTblMod.insertRow();
            int intTipModDoc;
            intTipModDoc=canTipoModificacion();
            switch(intTipModDoc){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
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
                        dtpFecDoc.setEnabled(false);
                        txtNumDoc1.setEditable(false);
                        txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
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
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
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
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función permite cargar los documentos que están pendientes de pago de un proveedor
     * @return true: Si se pudo cargar la información
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDocPen(){
        boolean blnRes=true;
        int intCodEmp, intCodLoc;
        objAsiDia.inicializar();
        try{
            objTblMod.removeAllRows();
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg,";
                strSQL+=" a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                strSQL+=" , a1.ne_numDoc1, a1.fe_doc, a1.co_cta, a4.tx_codCta, a4.tx_desLar AS tx_nomCta";
                strSQL+=" , a2.co_cli, a5.tx_nom AS tx_nomCli, a2.nd_valdep, a2.tx_obs1, a2.st_valapl";
                strSQL+=" FROM ( (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a4 ON a1.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta)";
                strSQL+=" INNER JOIN tbm_depCliRegDirBanPag AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                if(objUti.utilizarClientesEmpresa(objParSis, intCodEmp, intCodLoc, objParSis.getCodigoUsuario())){
                    strSQL+=" INNER JOIN tbm_cli AS a5";
                    strSQL+=" ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                }
                else{
                    strSQL+=" INNER JOIN tbm_cli AS a5";
                    strSQL+=" ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a6";
                    strSQL+=" ON a5.co_emp=a6.co_emp AND a5.co_cli=a6.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a6.co_loc=" + intCodLoc + "";
                }
                strSQL+=" AND a2.co_cli IS NOT NULL AND a2.st_valApl='N' AND a2.st_reg='A'";
                if( ! txtCodCta.getText().equals(""))
                    strSQL+=" AND a1.co_cta=" + txtCodCta.getText() + "";

                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a2.st_reg NOT IN('I','E')";

                strSQL+=" ORDER BY a1.ne_numDoc1, a2.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,             "");
                    vecReg.add(INT_TBL_DAT_CHK,             null);
                    vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getObject("tx_desCorTipDoc")==null?"":rst.getString("tx_desCorTipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getObject("tx_desLarTipDoc")==null?"":rst.getString("tx_desLarTipDoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,         "" + rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG,         "" + rst.getObject("co_reg")==null?"":rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + rst.getObject("ne_numDoc1")==null?"":rst.getString("ne_numDoc1"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_CTA,         "" + rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                    vecReg.add(INT_TBL_DAT_NUM_CTA,         "" + rst.getObject("tx_codCta")==null?"":rst.getString("tx_codCta"));
                    vecReg.add(INT_TBL_DAT_NOM_CTA,         "" + rst.getObject("tx_nomCta")==null?"":rst.getString("tx_nomCta"));
                    vecReg.add(INT_TBL_DAT_COD_CLI,         "" + rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                    vecReg.add(INT_TBL_DAT_NOM_CLI,         "" + rst.getObject("tx_nomCli")==null?"":rst.getString("tx_nomCli"));
                    vecReg.add(INT_TBL_DAT_VAL_DOC,         "" + rst.getObject("nd_valdep")==null?"":rst.getString("nd_valdep"));
                    vecReg.add(INT_TBL_DAT_OBS,             "" + rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_DAT_BUT_OBS,         "");
                    vecReg.add(INT_TBL_DAT_BUT_ANE,         "");
                    vecReg.add(INT_TBL_DAT_PRO,             "");


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
                objTblMod.initRowsState();
                objCxC76_02.getArregloDatosFacturasAplicadas().clear();
                objCxC76_02.cargarDatosIniciales();


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
     * Esta funcian calcula el abono total.
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
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC).toString();
                    dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                    dblTot+=dblVal;
                }
            }
            //Calcular la diferencia.
            txtMonDoc.setText("" + objUti.redondeo(dblTot,objParSis.getDecimalesBaseDatos()));
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
        }
    }






    private boolean cambiarEstado_tbmDepCliRegDirBanPag(String estado){
        boolean blnRes=true;
        String strUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        strSQL="";
                        strSQL+=" UPDATE tbm_depCliRegDirBanPag";
                        strSQL+=" SET st_valApl='" + estado + "'";
                        strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + "";
                        strSQL+=";";
                        strUpd+=strSQL;
                    }
                }
                System.out.println("cambiarEstado_tbmDepCliRegDirBanPag: " + strUpd);
                stm.executeUpdate(strUpd);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    private boolean actualiza_tbmPagMovInv(){
        boolean blnRes=true;
        //String strUpd="";
        BigDecimal bdeAbo1=new BigDecimal("0");
        BigDecimal bdeAbo2=new BigDecimal("0");
        String strEstReg="";
        String strCodEmpPagMovInvArl="";
        String strCodLocPagMovInvArl="";
        String strCodTipDocPagMovInvArl="";
        String strCodDocPagMovInvArl="";
        String strCodRegPagMovInvArl="";
        BigDecimal bdeValAplPagMovInvArl=new BigDecimal("0");

        String strCodEmpPagMovInvArl2="";
        String strCodLocPagMovInvArl2="";
        String strCodTipDocPagMovInvArl2="";
        String strCodDocPagMovInvArl2="";
        String strCodRegPagMovInvArl2="";
        
        /*Agregado para nuevo Servicio UNO */
        String strNumDocPagMovInvArl2="";
        /*Agregado para nuevo Servicio UNO */


        String strCodEmpPagMovInvTbl="";
        String strCodLocPagMovInvTbl="";
        String strCodTipDocPagMovInvTbl="";
        String strCodDocPagMovInvTbl="";
        String strCodRegPagMovInvTbl="";
        int h=0;
        
        vecOD.clear();
        
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue();i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        strCodEmpPagMovInvTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                        strCodLocPagMovInvTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString();
                        strCodTipDocPagMovInvTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString();
                        strCodDocPagMovInvTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString();
                        strCodRegPagMovInvTbl=objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG).toString();
                        h++;

                        for (int j=0;j<arlDat.size();j++){
                            strEstReg=objUti.getStringValueAt(arlDat, j, INT_ARL_EST_REG_ELI)==null?"":objUti.getStringValueAt(arlDat, j, INT_ARL_EST_REG_ELI).toString();
                            if( ! strEstReg.equals("E")){
                                strCodEmpPagMovInvArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_EMP_REL);
                                strCodLocPagMovInvArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_LOC_REL);
                                strCodTipDocPagMovInvArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_TIP_DOC_REL);
                                strCodDocPagMovInvArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_DOC_REL);
                                strCodRegPagMovInvArl=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_REG_REL);
                                bdeValAplPagMovInvArl=objUti.getBigDecimalValueAt(arlDat, j, INT_ARL_VAL_APL_PAG);

                                strCodEmpPagMovInvArl2=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_EMP_PAG);
                                strCodLocPagMovInvArl2=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_LOC_PAG);
                                strCodTipDocPagMovInvArl2=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_TIP_DOC_PAG);
                                strCodDocPagMovInvArl2=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_DOC_PAG);
                                strCodRegPagMovInvArl2=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_REG_PAG);
                                //Agregado para nuevo servicio UNO ZafReglas
                                strNumDocPagMovInvArl2=objUti.getStringValueAt(arlDat, j, INT_ARL_COD_NUM_DOC);
                                //Agregado para nuevo servicio UNO ZafReglas

                                if( (strCodEmpPagMovInvTbl.equals(strCodEmpPagMovInvArl))  && (strCodLocPagMovInvTbl.equals(strCodLocPagMovInvArl)) &&  (strCodTipDocPagMovInvTbl.equals(strCodTipDocPagMovInvArl)) && (strCodDocPagMovInvTbl.equals(strCodDocPagMovInvArl))  && (strCodRegPagMovInvTbl.equals(strCodRegPagMovInvArl))  ){
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=nd_abo+" + new BigDecimal("" + intSig).multiply(objUti.redondearBigDecimal((bdeValAplPagMovInvArl),objParSis.getDecimalesBaseDatos()));
                                    strSQL+=" WHERE co_emp=" + strCodEmpPagMovInvArl2 + "";
                                    strSQL+=" AND co_loc=" + strCodLocPagMovInvArl2 + "";
                                    strSQL+=" AND co_tipDoc=" + strCodTipDocPagMovInvArl2 + "";
                                    strSQL+=" AND co_doc=" + strCodDocPagMovInvArl2 + "";
                                    strSQL+=" AND co_reg=" + strCodRegPagMovInvArl2 + "";
                                    strSQL+=";";
                                    strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag, co_regPag";
                                    strSQL+=", nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq, st_regrep, tx_codsri, st_reg";
                                    strSQL+=", co_locreldepcliregdirban, co_tipdocreldepcliregdirban,co_docreldepcliregdirban,co_regreldepcliregdirban)";
                                    strSQL+=" VALUES (";
                                    strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                                    strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                                    strSQL+=", " + txtCodTipDoc.getText();//co_tipDoc
                                    strSQL+=", " + txtCodDoc.getText();//co_doc
                                    strSQL+=", " + h;//co_reg
                                    strSQL+=", " + strCodLocPagMovInvArl2;//co_locPag
                                    strSQL+=", " + strCodTipDocPagMovInvArl2;//co_tipDocPag
                                    strSQL+=", " + strCodDocPagMovInvArl2;//co_docPag
                                    strSQL+=", " + strCodRegPagMovInvArl2;//co_regPag
                                    strSQL+=", " + new BigDecimal("" + intSig).multiply(objUti.redondearBigDecimal((bdeValAplPagMovInvArl),objParSis.getDecimalesBaseDatos()));//nd_abo
                                    strSQL+=", Null";//co_tipDocCon
                                    strSQL+=", Null";//co_banChq
                                    strSQL+=", Null";//tx_numCtaChq
                                    strSQL+=", Null";//tx_numChq
                                    strSQL+=", Null";//fe_recChq
                                    strSQL+=", Null";//fe_venChq
                                    strSQL+=", Null";//st_regrep
                                    strSQL+=", Null";//tx_codsri
                                    strSQL+=", 'A'";//st_reg
                                    strSQL+="," + strCodLocPagMovInvArl + "";//co_locreldepcliregdirban
                                    strSQL+="," + strCodTipDocPagMovInvArl + "";//co_tipdocreldepcliregdirban
                                    strSQL+="," + strCodDocPagMovInvArl + "";//co_docreldepcliregdirban
                                    strSQL+="," + strCodRegPagMovInvArl + "";//co_regreldepcliregdirban
                                    strSQL+=");";
                                    h++;
                                    System.out.println("actualiza_tbmPagMovInv: " + strSQL);
                                    stm.executeUpdate(strSQL);

                                    if(_getVerificaPagTotFac("" + objParSis.getCodigoEmpresa(), strCodLocPagMovInvArl2, strCodTipDocPagMovInvArl2, strCodDocPagMovInvArl2  )){
                                        strSQL="";
                                        strSQL+= " UPDATE tbm_cabguirem SET ";
                                        strSQL+="  st_aut='A' ";
                                        strSQL+=" ,fe_aut="+objParSis.getFuncionFechaHoraBaseDatos()+" ";
                                        strSQL+=" ,tx_comAut='" + objParSis.getNombreComputadoraConDirIP()+ "'";
                                        strSQL+=" ,co_usrAut=" + objParSis.getCodigoUsuario() + " ";
                                        strSQL+=" FROM  ( ";
                                        strSQL+="  select co_emp, co_loc, co_tipdoc, co_doc from tbm_detguirem where ";
                                        strSQL+="  co_emprel=" + objParSis.getCodigoEmpresa() + " and co_locrel=" + strCodLocPagMovInvArl2 + "  and  co_tipdocrel=" + strCodTipDocPagMovInvArl2 + " and co_docrel=" + strCodDocPagMovInvArl2 + " ";
                                        strSQL+="  group by co_emp, co_loc, co_tipdoc, co_doc ";
                                        strSQL+=" ) AS x ";
                                        strSQL+=" WHERE x.co_emp= tbm_cabguirem.co_emp and x.co_loc=tbm_cabguirem.co_loc and x.co_tipdoc=tbm_cabguirem.co_tipdoc ";
                                        strSQL+=" and x.co_doc=tbm_cabguirem.co_doc  and  tbm_cabguirem.ne_numdoc=0 and tbm_cabguirem.st_aut='P'  ;   ";
                                        System.out.println("actualiza_tbm_cabguirem: " + strSQL);
                                        stm.executeUpdate(strSQL);

                                        INT_ENV_REC_IMP_GUIA=1;
                                        //vecOD.add(tblDat.getValueAt(i, INT_TBL_CODEMP)+"-"+tblDat.getValueAt(i, INT_TBL_CODLOC)+"-"+tblDat.getValueAt(i, INT_TBL_CODTID)+"-"+tblDat.getValueAt(i, INT_TBL_CODDOC)+"-"+tblDat.getValueAt(i, INT_TBL_NUMDOC));               
                                        vecOD.add(objParSis.getCodigoEmpresa()+"-"+strCodLocPagMovInvArl2+"-"+strCodTipDocPagMovInvArl2+"-"+strCodDocPagMovInvArl2+"-"+strNumDocPagMovInvArl2);
                                        
                                    }
                                }
                            }
                        }
                    }
                }

                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


/**
 * Función que permite verificar si la factura ha sido cancelada en su totalidad
 * @param strCodEmp: Código de la empresa
 * @param strCodLoc: Código del local
 * @param strCodTipDoc: Código del tipo de documento
 * @param strCodDoc: Código del documento
 * @return true: si se ha cancelado todo el valor de la factura
 * <BR> false: caso contrario
 */
    private boolean _getVerificaPagTotFac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
        boolean blnRes=false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rstTmp;
        String strSql="";
        try{
            if (con!=null){
                stmTmp=con.createStatement();
                strSql="select *, (pagfac+monchq) as dif from (  " +
                " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag+a1.nd_Abo) as pagfac , sum(a1.nd_monchq) as monchq  " +
                " from tbm_pagmovinv as a1 " +
                " left join tbr_detrecdocpagmovinv as a2 " +
                " on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg  )  " +
                " where a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_tipdoc="+strCodTipDoc+" and a1.co_doc="+strCodDoc+"  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  " +
                " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc  ) as x  ";
                System.out.println("_getVerificaPagTotFac:  "+strSql);
                rstTmp=stmTmp.executeQuery(strSql);
                if(rstTmp.next()){
                    if( rstTmp.getDouble("monchq") > 0 ){
                        if( rstTmp.getDouble("dif") >= 0 ){
                            if( _getVerificaFecChqFac(con, rstTmp.getString("co_emp"), rstTmp.getString("co_loc"), rstTmp.getString("co_tipdoc"), rstTmp.getString("co_doc") ) )
                                blnRes=true;
                        }
                        else{
                            if( rstTmp.getDouble("dif") >= -0.01 ){
                                if( _getVerificaFecChqFac(con, rstTmp.getString("co_emp"), rstTmp.getString("co_loc"), rstTmp.getString("co_tipdoc"), rstTmp.getString("co_doc") ) )
                                    blnRes=true;
                            }
                        }
                    }
                }
                rstTmp.close();
                rstTmp=null;

                if(!blnRes){

                    strSql="select *, (pagfac+abofac) as dif from (  " +
                    " select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,  sum(a1.mo_pag) as pagfac , sum(a1.nd_abo) as abofac  " +
                    " from tbm_pagmovinv as a1 " +
                    " where a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_tipdoc="+strCodTipDoc+" and a1.co_doc="+strCodDoc+"  and  a1.nd_porret=0  and a1.st_reg in ('A','C')  " +
                    " group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc ) as x   ";
                    System.out.println("_getVerificaPagTotFac-false :  "+strSql);
                    rstTmp=stmTmp.executeQuery(strSql);
                    if(rstTmp.next()){
                        if( rstTmp.getDouble("abofac") > 0 ){
                            if( rstTmp.getDouble("dif") >= -0.01 ){
                                blnRes=true;
                            }
                        }
                    }
                    rstTmp.close();
                    rstTmp=null;
                }
                stmTmp.close();
                stmTmp=null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false; objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false; objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean _getVerificaFecChqFac(java.sql.Connection conn, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
        boolean blnRes=false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rstTmp;
        String strSql="";
        try{
            if (conn!=null){
                stmTmp=conn.createStatement();
                strSql="select * " +
                " , case when fe_venchq <= fe_ven then 'S' else 'N' end as FecCont " +
                " , case when fe_venchq <= fecvenchq then 'S' else 'N' end as FecnoCont " +
                " from ( "+
                "select ne_diacre, fe_ven, (fe_ven+1) as fecvenchq , fe_venchq  from tbm_pagmovinv " +
                " where co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" and co_doc="+strCodDoc+" and nd_porret=0 and st_reg in ('A','C') " +
                " ) as x  ";
                System.out.println("_getVerificaFecChqFac :  "+strSql);
                rstTmp=stmTmp.executeQuery(strSql);
                while(rstTmp.next()){
                   if(rstTmp.getInt("ne_diacre")==0){
                        if(rstTmp.getString("FecCont").equals("S")){
                            blnRes=true;
                        }
                        else{
                            blnRes=false;
                            break;
                        }
                   }
                   else{
                       if(rstTmp.getString("FecnoCont").equals("S")){
                            blnRes=true;
                       }
                       else{
                            blnRes=false;
                            break;
                      }
                   }
                }
                rstTmp.close();
                rstTmp=null;
                stmTmp.close();
                stmTmp=null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false; objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false; objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




    private boolean actualizar_tbmPagMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_pagMovInv";
                strSQL+=" SET nd_abo=tbm_pagMovInv.nd_abo+((x.nd_abo)*(-1)) FROM(";
                strSQL+="    SELECT a6.co_emp AS co_emp, a6.co_locPag AS co_loc";
                strSQL+="     , a6.co_tipdocPag AS co_tipDoc, a6.co_docPag AS co_doc, a6.co_regPag AS co_reg";
                strSQL+="     , SUM(a6.nd_abo) AS nd_abo, a3.tx_natDoc";
                strSQL+="     FROM  tbm_cabPag AS a1";
                strSQL+="     INNER JOIN (tbm_detPag AS a6 INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+="                 ON a6.co_emp=a3.co_emp AND a6.co_locPag=a3.co_loc AND a6.co_tipdocPag=a3.co_tipDoc";
                strSQL+="                 INNER JOIN (tbm_cabPag AS b1 INNER JOIN tbm_plaCta AS a4 ON b1.co_emp=a4.co_emp AND b1.co_cta=a4.co_cta)";
                strSQL+="                 ON a6.co_emp=b1.co_emp AND a6.co_locreldepcliregdirban=b1.co_loc";
                strSQL+="                 AND a6.co_tipdocreldepcliregdirban=b1.co_tipDoc AND a6.co_docreldepcliregdirban=b1.co_doc)";
                strSQL+="     ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+="     INNER JOIN tbm_depCliRegDirBanPag AS a2";
                strSQL+="     ON a6.co_emp=a2.co_emp AND a6.co_locreldepcliregdirban=a2.co_loc";
                strSQL+="     AND a6.co_tipdocreldepcliregdirban=a2.co_tipDoc AND a6.co_docreldepcliregdirban=a2.co_doc AND a6.co_regreldepcliregdirban=a2.co_reg";
                strSQL+="     INNER JOIN tbm_cli AS a5";
                strSQL+="     ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                strSQL+="       WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+="       AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                strSQL+="       AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                strSQL+="       AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                strSQL+="     AND a2.co_cli IS NOT NULL AND a2.st_reg='A' AND a1.st_reg NOT IN('E') AND a6.st_reg NOT IN('E')";
                strSQL+="     GROUP BY a6.co_emp, a6.co_locPag, a6.co_tipdocPag, a6.co_docPag, a6.co_regPag,";
                strSQL+="     a3.tx_natDoc";
                strSQL+="     ORDER BY a6.co_emp, a6.co_locPag, a6.co_tipdocPag, a6.co_docPag, a6.co_regPag";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_pagMovInv.co_emp=x.co_emp AND tbm_pagMovInv.co_loc=x.co_loc";
                strSQL+=" AND tbm_pagMovInv.co_tipDoc=x.co_tipDoc AND tbm_pagMovInv.co_doc=x.co_doc AND tbm_pagMovInv.co_reg=x.co_reg";

                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean obtenerInformacionDocumento(int codEmpRel, int codLocRel, int codTipDocRel, int codDocRel, int codRegRel){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a6.co_loc, a6.co_tipDoc, a6.co_doc";
                strSQL+=" FROM  tbm_cabPag AS a1";
                strSQL+=" INNER JOIN (tbm_detPag AS a6 INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+="     ON a6.co_emp=a3.co_emp AND a6.co_locreldepcliregdirban=a3.co_loc AND a6.co_tipdocreldepcliregdirban=a3.co_tipDoc";
                strSQL+="     INNER JOIN (tbm_cabPag AS b1 INNER JOIN tbm_plaCta AS a4 ON b1.co_emp=a4.co_emp AND b1.co_cta=a4.co_cta)";
                strSQL+="     ON a6.co_emp=b1.co_emp AND a6.co_locreldepcliregdirban=b1.co_loc";
                strSQL+="     AND a6.co_tipdocreldepcliregdirban=b1.co_tipDoc AND a6.co_docreldepcliregdirban=b1.co_doc)";
                strSQL+=" ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+=" INNER JOIN tbm_depCliRegDirBanPag AS a2";
                strSQL+=" ON a6.co_emp=a2.co_emp AND a6.co_locreldepcliregdirban=a2.co_loc";
                strSQL+=" AND a6.co_tipdocreldepcliregdirban=a2.co_tipDoc AND a6.co_docreldepcliregdirban=a2.co_doc AND a6.co_regreldepcliregdirban=a2.co_reg";
                strSQL+=" INNER JOIN tbm_cli AS a5";
                strSQL+=" ON a2.co_emp=a5.co_emp AND a2.co_cli=a5.co_cli";
                strSQL+=" WHERE a1.co_emp=" + codEmpRel + "";
                strSQL+=" AND a6.co_locRelDepCliRegDirBan=" + codLocRel + "";
                strSQL+=" AND a6.co_tipDocRelDepCliRegDirBan=" + codTipDocRel + "";
                strSQL+=" AND a6.co_docRelDepCliRegDirBan=" + codDocRel + "";
                strSQL+=" AND a6.co_regRelDepCliRegDirBan=" + codRegRel + "";
                strSQL+=" AND a2.co_cli IS NOT NULL AND a2.st_reg='A' AND a1.st_reg NOT IN('E') AND a6.st_reg NOT IN('E')";
                strSQL+=" GROUP BY a1.co_emp, a6.co_loc, a6.co_tipDoc, a6.co_doc";
                strSQL+=" ORDER BY a1.co_emp, a6.co_loc, a6.co_tipDoc, a6.co_doc";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    txtCodTipDoc.setText("" +  rst.getString("co_tipDoc"));
                    txtCodDoc.setText("" +  rst.getString("co_doc"));
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
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
                    generarRpt(1);
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
        String strSQLRep="";
        Statement stmIns;
        try
        {
            if(conCab!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 0:
                                default:
                                    strSQL="";
                                    strSQL+="SELECT *FROM(";
                                    strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                                    strSQL+=" 	,a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                                    strSQL+=" 	,a1.ne_numDoc1, a1.fe_doc, a1.nd_monDoc";
                                    strSQL+=" 	, a2.co_locRelDepCliRegDirBan, a2.co_tipDocRelDepCliRegDirBan, a2.co_docRelDepCliRegDirBan, a2.co_regRelDepCliRegDirBan";
                                    strSQL+=" 	, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag";
                                    strSQL+=" 	FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                                    strSQL+=" 	INNER JOIN tbm_detPag AS a2";
                                    strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                                    strSQL+=" 	WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+="       AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+="       AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+="       AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                                    strSQL+=" 	AND a1.st_reg NOT IN('I','E')";
                                    strSQL+=" 	) AS b1";
                                    strSQL+=" 	INNER JOIN(";
                                    strSQL+=" 		SELECT a2.co_emp, a2.co_loc AS co_locDep, a2.co_tipDoc AS co_tipDocDep";
                                    strSQL+="		, a2.co_doc AS co_docDep";
                                    strSQL+=" 		, a1.ne_numDoc1 AS ne_numDocDep, a1.fe_doc AS fe_docDep, SUM(a2.nd_abo) AS nd_valAbo";
                                    strSQL+=" 		, a3.tx_desCor AS tx_desCorTipDocDep, a3.tx_desLar AS tx_desLarTipDocDep";
                                    strSQL+=" 		, a5.co_cta AS co_ctaDep, a5.tx_codCta AS tx_codCtaDep, a5.tx_desLar AS tx_nomCtaDep";
                                    strSQL+=" 		, a6.co_loc, a6.co_tipDoc, a6.co_doc, a6.co_reg, a6.nd_valDep";
                                    strSQL+=" 		, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag";
                                    strSQL+=" 		, a7.tx_desCor AS tx_desCorTipDocFac, a7.tx_desLar AS tx_desLarTipDocFac";
                                    strSQL+=" 		, a8.ne_numDoc AS ne_numDocFac, a8.fe_doc AS fe_docFac, a8.co_cli, a8.tx_nomCli AS tx_nomCliDep";
                                    strSQL+=" 		FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                                    strSQL+=" 		INNER JOIN tbm_plaCta AS a5 ON a1.co_emp=a5.co_emp AND a1.co_cta=a5.co_cta)";
                                    strSQL+=" 		INNER JOIN (tbm_detPag AS a2 INNER JOIN tbm_cabMovInv AS a8 ON a2.co_emp=a8.co_emp AND a2.co_locPag=a8.co_loc";
                                    strSQL+=" 					AND a2.co_tipDocPag=a8.co_tipDoc AND a2.co_docPag=a8.co_doc";
                                    strSQL+=" 				INNER JOIN tbm_cabTipDoc AS a7";
                                    strSQL+=" 				ON a2.co_emp=a7.co_emp AND a2.co_locPag=a7.co_loc AND a2.co_tipDocPag=a7.co_tipDoc)";
                                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_locRelDepCliRegDirBan";
                                    strSQL+=" 		AND a1.co_tipDoc=a2.co_tipDocRelDepCliRegDirBan AND a1.co_doc=a2.co_docRelDepCliRegDirBan";
                                    strSQL+=" 		INNER JOIN tbm_depcliregdirbanpag AS a6";
                                    strSQL+=" 		ON a2.co_emp=a6.co_emp AND a2.co_locRelDepCliRegDirBan=a6.co_loc";
                                    strSQL+=" 		AND a2.co_tipDocRelDepCliRegDirBan=a6.co_tipDoc AND a2.co_docRelDepCliRegDirBan=a6.co_doc";
                                    strSQL+=" 		AND a2.co_regRelDepCliRegDirBan=a6.co_reg";
                                    strSQL+=" 		WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                                    strSQL+=" 		AND a2.co_loc=" + rstCab.getString("co_loc") + "";
                                    strSQL+=" 		AND a2.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                    strSQL+=" 		AND a2.co_doc=" + rstCab.getString("co_doc") + "";
                                    strSQL+=" 		AND a2.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E')";
                                    strSQL+=" 		GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc";
                                    strSQL+=" 		, a2.co_doc, a2.co_reg";
                                    strSQL+=" 		, a1.ne_numDoc1, a1.fe_doc";
                                    strSQL+=" 		, a3.tx_desCor, a3.tx_desLar";
                                    strSQL+=" 		, a5.co_cta, a5.tx_codCta, a5.tx_desLar";
                                    strSQL+=" 		, a6.co_loc, a6.co_tipDoc, a6.co_doc, a6.co_reg, a6.nd_valDep";
                                    strSQL+=" 		, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag";
                                    strSQL+=" 		, a7.tx_desCor, a7.tx_desLar";
                                    strSQL+=" 		, a8.ne_numDoc, a8.fe_doc, a8.co_cli, a8.tx_nomCli";
                                    strSQL+=" 		ORDER BY a6.nd_valDep";
                                    strSQL+=" ) AS b2";
                                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_locRelDepCliRegDirBan=b2.co_loc";
                                    strSQL+=" AND b1.co_tipDocRelDepCliRegDirBan=b2.co_tipDoc AND b1.co_docRelDepCliRegDirBan=b2.co_doc AND b1.co_regRelDepCliRegDirBan=b2.co_reg";
                                    strSQL+=" AND b1.co_locPag=b2.co_locPag AND b1.co_tipDocPag=b2.co_tipDocPag AND b1.co_docPag=b2.co_docPag AND b1.co_regPag=b2.co_regPag";

                                    strSQL+=" ORDER BY b1.co_tipDocRelDepCliRegDirBan, b1.co_docRelDepCliRegDirBan, b1.co_regRelDepCliRegDirBan, b2.co_tipDocPag, b2.co_docPag, b2.co_regPag";

                                    strSQLRep=strSQL;
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.


                                    java.util.Map mapPar = new java.util.HashMap();
                                    mapPar.put("strSQLRep", "" + strSQLRep);
                                    mapPar.put("strTit", "" + objParSis.getNombreMenu());
                                    mapPar.put("strCamAudRpt", this.getClass().getName() + "    " +  "    " + strNomRpt + "    "  + objParSis.getNombreUsuario() + "   " + strFecHorSer);

                                    mapPar.put("codUsrImp", "" + objParSis.getCodigoUsuario());

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    stmIns=conCab.createStatement();
                                    strSQL="";
                                    strSQL+="UPDATE tbm_cabPag";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                                    strSQL+=";";
                                    strSQL+="UPDATE tbm_cabDia";
                                    strSQL+=" SET st_imp='S'";
                                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                                    strSQL+=" AND co_dia=" + txtCodDoc.getText() + "";
                                    stmIns.executeUpdate(strSQL);

                                    stmIns.close();
                                    stmIns=null;

                                    strEstImpDoc="S";

                                    break;


                            }
                        }
                    }
                }
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
 * Función que permite enviar la señal para que se imprima la guía
 * según el estado de autorización
 * @param strIp: dirección IP
 * @param intPuerto: Puerto de la máquina
 */
    private void enviarRequisitoImp(String strIp, int intPto){
        try{
           java.net.Socket s1 = new java.net.Socket(strIp, intPto);
           java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
           dos.writeInt(1);
           dos.close();
           s1.close();
        }
        catch (java.net.ConnectException connExc){
            System.err.println("OCURRIO UN ERROR 1 "+connExc );
        }
        catch (IOException e){
            System.err.println("OCURRIO UN ERROR 2 "+ e );
        }
    }

}
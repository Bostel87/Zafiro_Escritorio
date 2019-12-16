/*
 *
 * Created on 27 de noviembre de 2006, 12:50 PM
 */
package CxP.ZafCxP01;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.util.Vector;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
/**
 *
 * @author  ilino
 */
public class ZafCxP01 extends javax.swing.JInternalFrame 
{
    final int INT_COD_DOC=2;
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_SEL=1;
    final int INT_TBL_DAT_TIP_DOC=2;
    final int INT_TBL_DAT_NOM_DOC=3;
    final int INT_TBL_DAT_NUM_DOC=4;
    final int INT_TBL_DAT_FEC_DOC=5;    
    final int INT_TBL_DAT_BAS_IMP=6;
    final int INT_TBL_DAT_POR_RET=7;
    final int INT_TBL_DAT_MON_DOC=8;    
    final int INT_TBL_DAT_VAL_PND=9;
    final int INT_TBL_DAT_COD_REG=10;
    final int INT_TBL_DAT_COD_LOC=11;
    final int INT_TBL_DAT_COD_DOC=12; 
    final int INT_TBL_DAT_VAL_ABO=13;    
    final int INT_TBL_DAT_AUX_ABO=14;
    final int INT_TBL_DAT_COD_DOC_DIN=15;
    final int INT_TBL_DAT_TIP_DOC_DIN=16;
    final int INT_TBL_BUT_DOC_DIN=17;
    final int INT_TBL_DAT_NOM_DOC_DIN=18;
            
    final String STR_COD_CTA_POR_RET="983";//el codigo de la cuenta de retencion que se encuentra en la tabla de plan de cuentas
    final int INT_POR_RET=1;
    
    final int INT_NUM_DEC=2;
    boolean blnCambios = false; //Detecta ke se hizo cambios en el documento
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafDate.ZafDatePicker txtFecVen;
    private javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecValPen, vecCab;
    private boolean blnHayCam;          //Determina si hay cambios en el formulario.
    private ZafColNumerada objColNum;
    private ZafPopupMenu objPopMnu;
    //private ZafTableModel objTblMod;
    private ZafDefTblCelRen objDefTblCelRen;    //Formatear columnas en JTable.
    private ZafMouMotAda objMouMotAda;          //ToolTipText en TableHeader.
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecución del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodPro, strDesLarPro,strIdePro, strDirPro,strDesCorCta,strDesLarCta;    

    private java.util.Vector vecReg;
    private mitoolbar objTooBar;
    private String sSQLCon;
    private java.sql.Connection conCab, con, conCnsDet,conAnu;

    private java.sql.Statement stmCab, stm, stmCnsDet, stmAnu;
    private java.sql.ResultSet rstCab, rst, rstCnsDet;
    private tblHilo objHilo;
    private ZafAsiDia objAsiDia;

    private ZafTblMod objTblMod;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblEdi objTblEdi;
    private ZafTblCelRenLbl objTblCelRenLblObl;
    private ZafCxP01.ZafTblModLis objTblModLis;
    private double dblMonDoc=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;
    
    private int intSig=1;                                   //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicación de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    private ZafTblCelEdiBut objTblCelEdiBut;
    private ZafTblCelRenBut objTblCelRenBut;

    private double dblBasImp=0.00;
    
    private String strVer="v0.1.2";
    
    
    /** Crea una nueva instancia de la clase ZafCon01. */
    public ZafCxP01(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 

            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panCabDoc.add(txtFecDoc);
            txtFecDoc.setBounds(510, 3, 120, 20);

            txtFecVen = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            txtFecVen.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecVen.setText("");
            panCabDoc.add(txtFecVen);
            txtFecVen.setBounds(510, 24, 120, 20);


            objTooBar=new mitoolbar(this);
                //objDocLis=new ZafDocLis();
            panBar.add(objTooBar);//llama a la barra de botones

            //objDocLis=new ZafDocLis();
            if (!configurarFrm())
                exitForm();
            
            objAsiDia=new ZafAsiDia(objParSis);
            panDia.add(objAsiDia, java.awt.BorderLayout.CENTER);            
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
        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panCabDoc = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtTipDoc = new javax.swing.JTextField();
        txtNomDoc = new javax.swing.JTextField();
        butDoc = new javax.swing.JButton();
        lblCom = new javax.swing.JLabel();
        txtNumAltUno = new javax.swing.JTextField();
        lblAte = new javax.swing.JLabel();
        txtNumAltDos = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblPro = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        lblMonDoc = new javax.swing.JLabel();
        txtNomPro = new javax.swing.JTextField();
        txtCodPro = new javax.swing.JTextField();
        butPro = new javax.swing.JButton();
        txtCodDoc = new javax.swing.JTextField();
        txtMonDoc = new javax.swing.JTextField();
        lblFecVen = new javax.swing.JLabel();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        panVal = new javax.swing.JPanel();
        spnValExc = new javax.swing.JScrollPane();
        tblValPnd = new javax.swing.JTable();
        spnObs = new javax.swing.JScrollPane();
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
        setPreferredSize(new java.awt.Dimension(116, 210));
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Informaci\u00f3n del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panCabDoc.setLayout(null);

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 110));
        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipDoc.setText("Tipo de documento:");
        panCabDoc.add(lblTipDoc);
        lblTipDoc.setBounds(4, 5, 107, 14);

        txtTipDoc.setMaximumSize(null);
        txtTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocActionPerformed(evt);
            }
        });
        txtTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipDocFocusLost(evt);
            }
        });

        panCabDoc.add(txtTipDoc);
        txtTipDoc.setBounds(128, 4, 66, 20);

        txtNomDoc.setMaximumSize(null);
        txtNomDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDocActionPerformed(evt);
            }
        });
        txtNomDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDocFocusLost(evt);
            }
        });

        panCabDoc.add(txtNomDoc);
        txtNomDoc.setBounds(194, 4, 170, 20);

        butDoc.setFont(new java.awt.Font("SansSerif", 1, 12));
        butDoc.setText("...");
        butDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDocActionPerformed(evt);
            }
        });

        panCabDoc.add(butDoc);
        butDoc.setBounds(366, 4, 24, 20);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCom.setText("N\u00famero alterno1:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblCom);
        lblCom.setBounds(396, 46, 100, 15);

        txtNumAltUno.setPreferredSize(new java.awt.Dimension(100, 20));
        panCabDoc.add(txtNumAltUno);
        txtNumAltUno.setBounds(510, 44, 120, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte.setText("N\u00famero alterno2:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblAte);
        lblAte.setBounds(396, 68, 100, 15);

        txtNumAltDos.setPreferredSize(new java.awt.Dimension(6, 20));
        panCabDoc.add(txtNumAltDos);
        txtNumAltDos.setBounds(510, 65, 120, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblFecDoc);
        lblFecDoc.setBounds(396, 5, 120, 15);

        lblPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPro.setText("Proveedor:");
        lblPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblPro);
        lblPro.setBounds(4, 28, 70, 15);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("C\u00f3digo del documento:");
        lblCodDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCodDoc);
        lblCodDoc.setBounds(4, 55, 130, 15);

        lblMonDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMonDoc.setText("Monto del documento:");
        lblMonDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblMonDoc);
        lblMonDoc.setBounds(4, 78, 130, 15);

        txtNomPro.setMaximumSize(null);
        txtNomPro.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomProActionPerformed(evt);
            }
        });
        txtNomPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomProFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomProFocusLost(evt);
            }
        });

        panCabDoc.add(txtNomPro);
        txtNomPro.setBounds(194, 28, 170, 20);

        txtCodPro.setMaximumSize(null);
        txtCodPro.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProActionPerformed(evt);
            }
        });
        txtCodPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodProFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProFocusLost(evt);
            }
        });

        panCabDoc.add(txtCodPro);
        txtCodPro.setBounds(128, 28, 66, 20);

        butPro.setFont(new java.awt.Font("SansSerif", 1, 12));
        butPro.setText("...");
        butPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProActionPerformed(evt);
            }
        });

        panCabDoc.add(butPro);
        butPro.setBounds(366, 28, 24, 20);

        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDocActionPerformed(evt);
            }
        });

        panCabDoc.add(txtCodDoc);
        txtCodDoc.setBounds(128, 52, 124, 20);

        txtMonDoc.setMaximumSize(null);
        txtMonDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtMonDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMonDocActionPerformed(evt);
            }
        });

        panCabDoc.add(txtMonDoc);
        txtMonDoc.setBounds(128, 76, 124, 20);

        lblFecVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecVen.setText("Fecha de vencimiento:");
        lblFecVen.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblFecVen);
        lblFecVen.setBounds(396, 26, 120, 15);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        panCabDoc.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(106, 4, 0, 0);

        panDat.add(panCabDoc, java.awt.BorderLayout.NORTH);

        panVal.setLayout(new java.awt.BorderLayout());

        panVal.setPreferredSize(new java.awt.Dimension(935, 400));
        spnValExc.setBorder(new javax.swing.border.TitledBorder(""));
        spnValExc.setPreferredSize(new java.awt.Dimension(20, 0));
        tblValPnd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linea", "Sel", "Tipo de documento", "Nombre del documento", "Numero de documento", "Fecha del documento", "Base Imponible", "% de Retención", "Valor del documento", "Valor pendiente", "Codigo del Registro", "Codigo Local", "Codigo Documento", "Abono", "Abono Auxiliar", "Cod Doc Dinam", "Desc Corta Doc Dinam", "Boton Doc Dinam", "Desc Larga Doc Dinam"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblValPnd.setMinimumSize(new java.awt.Dimension(600, 64));
        spnValExc.setViewportView(tblValPnd);

        panVal.add(spnValExc, java.awt.BorderLayout.CENTER);

        panDat.add(panVal, java.awt.BorderLayout.CENTER);

        spnObs.setPreferredSize(new java.awt.Dimension(519, 100));
        panObs.setLayout(new java.awt.BorderLayout());

        panObs.setPreferredSize(new java.awt.Dimension(518, 90));
        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        panLbl.setPreferredSize(new java.awt.Dimension(92, 30));
        lblObs1.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observaci\u00f3n 1:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observaci\u00f3n 2:");
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        spnObs1.setViewportView(txaObs1);

        panTxa.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panTxa.add(spnObs2);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        spnObs.setViewportView(panObs);

        panDat.add(spnObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panDat);

        panDia.setLayout(new java.awt.BorderLayout());

        tabFrm.addTab("Asiento de diario", panDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void txtMonDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMonDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMonDocActionPerformed

    private void txtNomDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtNomDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtTipDoc.setText("");
//                txtCodCta.setText("");
//                txtDesCorCta.setText("");
//                txtNomCta.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtNomDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtNomDocFocusLost

    private void txtNomDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusGained
        strDesLarTipDoc=txtNomDoc.getText();
        txtNomDoc.selectAll();
    }//GEN-LAST:event_txtNomDocFocusGained

    private void txtNomDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDocActionPerformed
        txtNomDoc.transferFocus();
    }//GEN-LAST:event_txtNomDocActionPerformed

    private void txtTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocFocusGained
        strTipDoc=txtTipDoc.getText();
        txtTipDoc.selectAll();        
    }//GEN-LAST:event_txtTipDocFocusGained

    private void txtNomProFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomProFocusGained
        strDesLarPro=txtNomPro.getText();
        txtNomPro.selectAll();
    }//GEN-LAST:event_txtNomProFocusGained

    private void txtNomProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomProActionPerformed
        txtNomPro.transferFocus();
    }//GEN-LAST:event_txtNomProActionPerformed

    private void txtCodProFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProFocusGained
        strCodPro=txtCodPro.getText();
        txtCodPro.selectAll();
    }//GEN-LAST:event_txtCodProFocusGained

    private void txtNomProFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomProFocusLost
        if (!txtNomPro.getText().equalsIgnoreCase(strDesLarPro))
        {
            if (txtNomPro.getText().equals(""))
            {
                txtCodPro.setText("");
                txtNomPro.setText("");
                objTblMod.removeAllRows();
                txtMonDoc.setText("");
            }
            else
            {
                mostrarVenConPro(2);
                if (txtCodPro.getText().equals(""))
                {
                    objTblMod.removeAllRows();
                    txtMonDoc.setText("");
                }
                else
                {
                    cargarDocPen();
                    txtMonDoc.setText("");
                }
            }
        }
        else
            txtNomPro.setText(strDesLarPro);                
    }//GEN-LAST:event_txtNomProFocusLost

//    protected void monDoc(double varTmp){	
//        int numFilTbl=tblValPnd.getSelectedRow();        
//            //System.out.println("el valor anterior es:" +dblValAboBef());
//            System.out.println("el valor inicial del documento es:" +dblMonDoc);
//                
//            dblMonDoc=dblMonDoc-varTmp;
//            //txtMonDoc.setText(""+dblMonDoc);
//            System.out.println("el valor despues de la resta del doc. es:" +dblMonDoc);
//            
//            dblMonDoc+=Double.parseDouble(""+tblValPnd.getValueAt(numFilTbl, INT_TBL_DAT_VAL_ABO));
//            System.out.println("el total del documento es:" +dblMonDoc);
//            txtMonDoc.setText("" + dblMonDoc);
//            System.out.println("dentro de monDoc");            
//    }    

    protected int ultCodDoc(){
        int intUltDoc=0;
        boolean blnRes=true;
        java.sql.Connection conNumDoc;
        java.sql.Statement stmNumDoc;
        java.sql.ResultSet rstNumDoc;
        try{            
            conNumDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNumDoc!=null){            
                stmNumDoc=conNumDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlDoc="";
                sqlDoc+="select b2.ne_ultdoc from tbr_tipdocprg as b1 inner join tbm_cabtipdoc as b2";
                sqlDoc+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipdoc=b2.co_tipdoc";
                sqlDoc+=" where b1.co_emp=" + objParSis.getCodigoEmpresa() + " and b1.co_loc=" + objParSis.getCodigoLocal() + "";
                sqlDoc+=" b1.co_mnu=" + objParSis.getCodigoMenu() + "";
                intUltDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), sqlDoc);
                intUltDoc++;                
                //stmNumDoc=conNumDoc.createStatement();
                rstNumDoc=stmNumDoc.executeQuery(sqlDoc);                
                if(rstNumDoc.next())
                    txtCodDoc.setText(String.valueOf(intUltDoc));
                    System.out.println("el codigo del documento es:"+txtCodDoc.getText());       
            }
            conNumDoc.close();
            //stmNumDoc.close();
            //rstNumDoc.close();
            conNumDoc=null;
            stmNumDoc=null;
            rstNumDoc=null;
            
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
        return intUltDoc;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    private void txtCodProFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPro.getText().equalsIgnoreCase(strCodPro))
        {
            if (txtCodPro.getText().equals(""))
            {
                txtCodPro.setText("");
                txtNomPro.setText("");
                objTblMod.removeAllRows();
                txtMonDoc.setText("");
            }
            else
            {
                mostrarVenConPro(1);
                if (txtCodPro.getText().equals(""))
                {
                    objTblMod.removeAllRows();
                    txtMonDoc.setText("");
                }
                else
                {
                    cargarDocPen();
                    txtMonDoc.setText("");
                }
            }
        }
        else
            txtCodPro.setText(strCodPro);         
    }//GEN-LAST:event_txtCodProFocusLost

    
    private boolean mostrarVenConPro(int intTipBus)
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
            strSQL+=" AND a1.st_prv='S' and st_reg='A'";
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de proveedores");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodPro.setText(objVenCon.GetCamSel(1));
                        strIdePro=objVenCon.GetCamSel(2);
                        txtNomPro.setText(objVenCon.GetCamSel(3));
                        strDirPro=objVenCon.GetCamSel(4);
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenCon.buscar("LOWER(a1.co_cli) LIKE '" + txtCodPro.getText() + "'"))
                    {
                        txtCodPro.setText(objVenCon.GetCamSel(1));
                        strIdePro=objVenCon.GetCamSel(2);
                        txtNomPro.setText(objVenCon.GetCamSel(3));
                        strDirPro=objVenCon.GetCamSel(4);
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtCodPro.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(0);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodPro.setText(objVenCon.GetCamSel(1));
                            strIdePro=objVenCon.GetCamSel(2);
                            txtNomPro.setText(objVenCon.GetCamSel(3));
                            strDirPro=objVenCon.GetCamSel(4);
                        }
                        else
                        {
                            txtCodPro.setText(strCodPro);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (objVenCon.buscar("LOWER(a1.tx_nom) LIKE '" + txtNomPro.getText().toLowerCase() + "'"))
                    {
                        txtCodPro.setText(objVenCon.GetCamSel(1));
                        strIdePro=objVenCon.GetCamSel(2);
                        txtNomPro.setText(objVenCon.GetCamSel(3));
                        strDirPro=objVenCon.GetCamSel(4);
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtNomPro.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodPro.setText(objVenCon.GetCamSel(1));
                            strIdePro=objVenCon.GetCamSel(2);
                            txtNomPro.setText(objVenCon.GetCamSel(3));
                            strDirPro=objVenCon.GetCamSel(4);
                        }
                        else
                        {
                            txtNomPro.setText(strDesLarPro);
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
    


    
    
    private void txtCodProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProActionPerformed
        txtCodPro.transferFocus();
    }//GEN-LAST:event_txtCodProActionPerformed

    /*
    private boolean mostrarVenConCta(int intTipBus)
    {
        String strAli, strCam;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            strAli="Código, Cuenta, Nombre, Naturaleza";
            strCam="a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubicta";
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubicta";
            strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
            strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
            strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de cuentas");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
//                        txtCodCta.setText(objVenCon.GetCamSel(1));
//                        txtDesCorCta.setText(objVenCon.GetCamSel(2));
//                        txtNomCta.setText(objVenCon.GetCamSel(3));
//                        strUbiCta=objVenCon.GetCamSel(4);
                        regenerarDiario();
                        txtNumAltUno.selectAll();
                        txtNumAltUno.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (objVenCon.buscar("LOWER(a1.tx_codCta) LIKE '" + txtDesCorCta.getText().toLowerCase() + "'"))
                    {
//                        txtCodCta.setText(objVenCon.GetCamSel(1));
//                        txtDesCorCta.setText(objVenCon.GetCamSel(2));
//                        txtNomCta.setText(objVenCon.GetCamSel(3));
//                        strUbiCta=objVenCon.GetCamSel(4);
                        regenerarDiario();
                        txtNumAltUno.selectAll();
                        txtNumAltUno.requestFocus();
//                        if (objTooBar.getEstado()=='n')
//                        {
//                            strAux=objVenCon.GetCamSel(4);
//                            txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
//                        }
//                        txtNumAltUno.selectAll();
//                        txtNumAltUno.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtDesCorCta.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodCta.setText(objVenCon.GetCamSel(1));
                            txtDesCorCta.setText(objVenCon.GetCamSel(2));
                            txtNomCta.setText(objVenCon.GetCamSel(3));
                            strUbiCta=objVenCon.GetCamSel(4);
                            regenerarDiario();
                            txtNumAltUno.selectAll();
                            txtNumAltUno.requestFocus();
//                            if (objTooBar.getEstado()=='n')
//                            {
//                                strAux=objVenCon.GetCamSel(4);
//                                txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
//                            }
//                            txtNumAltUno.selectAll();
//                            txtNumAltUno.requestFocus();
                        }
                        else
                        {
                            txtDesCorCta.setText(strDesCorCta);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (objVenCon.buscar("LOWER(a1.tx_desLar) LIKE '" + txtNomCta.getText().toLowerCase() + "'"))
                    {
                        txtCodCta.setText(objVenCon.GetCamSel(1));
                        txtDesCorCta.setText(objVenCon.GetCamSel(2));
                        txtNomCta.setText(objVenCon.GetCamSel(3));
                        strUbiCta=objVenCon.GetCamSel(4);
                        regenerarDiario();
                        txtNumAltUno.selectAll();
                        txtNumAltUno.requestFocus();
//                        if (objTooBar.getEstado()=='n')
//                        {
//                            strAux=objVenCon.GetCamSel(4);
//                            txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
//                        }
//                        txtNumAltUno.selectAll();
//                        txtNumAltUno.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtNomCta.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodCta.setText(objVenCon.GetCamSel(1));
                            txtDesCorCta.setText(objVenCon.GetCamSel(2));
                            txtNomCta.setText(objVenCon.GetCamSel(3));
                            strUbiCta=objVenCon.GetCamSel(4);
                            regenerarDiario();
                            txtNumAltUno.selectAll();
                            txtNumAltUno.requestFocus();
//                            if (objTooBar.getEstado()=='n')
//                            {
//                                strAux=objVenCon.GetCamSel(4);
//                                txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
//                            }
//                            txtNumAltUno.selectAll();
//                            txtNumAltUno.requestFocus();
                        }
                        else
                        {
                            txtNomCta.setText(strDesLarCta);
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
    
    
    protected String strNatCta(){
            Connection conNatCta;
            Statement stmNatCta;
            ResultSet rstNatCta;
            String strNatCta="";
            try{
                conNatCta=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conNatCta!=null){
                    stmNatCta=conNatCta.createStatement();
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubicta";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();        
//                    strSQL+=" AND a2.co_cta=" + txtCodCta.getText() + "";
                    rstNatCta=stmNatCta.executeQuery(strSQL);
                    
                    if (rstNatCta.next()){
                        strNatCta=rstNatCta.getString("tx_ubicta");
                        System.out.println("este es el nombre del document:" +strNatCta);
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
            return strNatCta;        
    }
    
    
    
    private void txtTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocActionPerformed
        txtTipDoc.transferFocus();
    }//GEN-LAST:event_txtTipDocActionPerformed

    private void txtCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDocActionPerformed
        // TODO add your handling code here:        
    }//GEN-LAST:event_txtCodDocActionPerformed

    private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
        mostrarVenConPro(0);
        if (txtCodPro.getText().equals(""))
        {
            objTblMod.removeAllRows();
            txtMonDoc.setText("");
        }
        else
        {
            cargarDocPen();
            txtMonDoc.setText("");
        }
    }//GEN-LAST:event_butProActionPerformed

    
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        String strAli, strCam;
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            strAli="Código, Descripción corta, Descripción larga, Ultimo documento, Naturaleza";
            strCam="a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
            strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
            strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
            strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
            objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL, "", objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objVenCon.setTitle("Listado de tipos de documentos");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    objVenCon.show();
                    if (objVenCon.acepto())
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtTipDoc.setText(objVenCon.GetCamSel(2));
                        txtNomDoc.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                            txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                        txtNumAltUno.selectAll();
                        txtNumAltUno.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (objVenCon.buscar("LOWER(a1.tx_desCor) LIKE '" + txtTipDoc.getText().toLowerCase() + "'"))
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtTipDoc.setText(objVenCon.GetCamSel(2));
                        txtNomDoc.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                            txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                        txtNumAltUno.selectAll();
                        txtNumAltUno.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtTipDoc.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(1);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                            txtTipDoc.setText(objVenCon.GetCamSel(2));
                            txtNomDoc.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                                txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                            txtNumAltUno.selectAll();
                            txtNumAltUno.requestFocus();
                        }
                        else
                        {
                            txtTipDoc.setText(strTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (objVenCon.buscar("LOWER(a1.tx_desLar) LIKE '" + txtNomDoc.getText().toLowerCase() + "'"))
                    {
                        txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                        txtTipDoc.setText(objVenCon.GetCamSel(2));
                        txtNomDoc.setText(objVenCon.GetCamSel(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=objVenCon.GetCamSel(4);
                            txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                        txtNumAltUno.selectAll();
                        txtNumAltUno.requestFocus();
                    }
                    else
                    {
                        objVenCon.setFiltroConsulta(txtNomDoc.getText());
                        objVenCon.setSelectedTipBus(2);
                        objVenCon.setSelectedCamBus(2);
                        objVenCon.show();
                        if (objVenCon.acepto())
                        {
                            txtCodTipDoc.setText(objVenCon.GetCamSel(1));
                            txtTipDoc.setText(objVenCon.GetCamSel(2));
                            txtNomDoc.setText(objVenCon.GetCamSel(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=objVenCon.GetCamSel(4);
                                txtNumAltUno.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(objVenCon.GetCamSel(5).equals("I")?1:-1);
                            txtNumAltUno.selectAll();
                            txtNumAltUno.requestFocus();
                        }
                        else
                        {
                            txtNomDoc.setText(strDesLarTipDoc);
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
    
    
    
    private void txtTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtTipDoc.getText().equalsIgnoreCase(strTipDoc))
        {
            if (txtTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtNomDoc.setText("");
//                txtCodCta.setText("");
//                txtDesCorCta.setText("");
//                txtNomCta.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtTipDoc.setText(strTipDoc);
    }//GEN-LAST:event_txtTipDocFocusLost
        
    private void butDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butDocActionPerformed
    
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
    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butDoc;
    private javax.swing.JButton butPro;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecVen;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPro;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDia;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTxa;
    private javax.swing.JPanel panVal;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnValExc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblValPnd;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodPro;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtMonDoc;
    private javax.swing.JTextField txtNomDoc;
    private javax.swing.JTextField txtNomPro;
    private javax.swing.JTextField txtNumAltDos;
    private javax.swing.JTextField txtNumAltUno;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtTipDoc;
    // End of variables declaration//GEN-END:variables

    public String retDesCorTipDocDin(int codDocDin){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";  
        int intCodDocDin=codDocDin;
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_descor from tbm_cabtipdoc";
                que+=" where ";
                que+=" co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and co_tipdoc=" + intCodDocDin + "";                                                
                System.out.println("estoy dentro de la funcion"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_descor");
                    System.out.println("este es el nombre corto del document:" +auxTipDoc);
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;
        
    }        
    

    
    public String retDesLarTipDocDin(int codDocDin){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";  
        int intCodDocDin=codDocDin;
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_deslar from tbm_cabtipdoc";
                que+=" where ";
                que+=" co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and co_tipdoc=" + intCodDocDin + "";                                                
                System.out.println("estoy dentro de la funcion"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_deslar");
                    System.out.println("este es el nombre corto del document:" +auxTipDoc);
                }
            } 
            conTipDoc.close();
            conTipDoc=null;            
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;
        
    }        
    
    
    
    
    public String retDesCorTipDoc(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select *from tbr_tipdocprg as a1, tbm_cabtipdoc as a2";
                que+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and";
                que+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";                                                
                System.out.println("estoy dentro de la funcion"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_descor");
                    System.out.println("este es el nombre del document:" +auxTipDoc);
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
        return auxTipDoc;
        
    }        
    
    
    
    public String retCodTipDoc(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxCodTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select *from tbr_tipdocprg as a1, tbm_cabtipdoc as a2";
                que+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and";
                que+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";                                                
                System.out.println("estoy dentro de la funcion"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxCodTipDoc=rstTipDoc.getString("co_tipdoc");
                    System.out.println("este es el codigo del document:" +auxCodTipDoc);
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
        return auxCodTipDoc;
        
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
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.fe_ven";
                strSQL+=", a1.ne_numDoc1, a1.ne_numDoc2, a1.co_cta, a3.tx_codCta, a3.tx_desLar AS a3_tx_desLar, a1.co_cli";
                strSQL+=", a1.tx_ruc, a1.tx_nomCli, a1.tx_dirCli, a1.nd_monDoc, a1.tx_obs1, a1.tx_obs2, a1.st_reg";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                System.out.println("en cargarCabReg:"+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtNomDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    txtFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    txtFecVen.setText(objUti.formatearFecha(rst.getDate("fe_ven"),"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numDoc1");
                    txtNumAltUno.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_numDoc2");
                    txtNumAltDos.setText((strAux==null)?"":strAux);
//                    strAux=rst.getString("co_cta");
//                    txtCodCta.setText((strAux==null)?"":strAux);
//                    strAux=rst.getString("tx_codCta");
//                    txtDesCorCta.setText((strAux==null)?"":strAux);
//                    strAux=rst.getString("a3_tx_desLar");
//                    txtNomCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cli");
                    txtCodPro.setText((strAux==null)?"":strAux);
                    strIdePro=rst.getString("tx_ruc");
                    strAux=rst.getString("tx_nomCli");
                    txtNomPro.setText((strAux==null)?"":strAux);
                    strDirPro=rst.getString("tx_dirCli");
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
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
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
    
    
    
    
    
    
/*    
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT b4.co_tipdoc, b4.co_doc, b4.co_cta, b4.co_cli, b4.tx_nomcli, b4.ne_numDoc1, b4.ne_numDoc2, b3.tx_deslar as deslarcta, b3.tx_codcta as codNivCta,";
                strSQL+=" b4.fe_doc, b4.nd_monDoc, b4.tx_obs1, b4.tx_obs2, b4.fe_doc, b4.st_reg, b4.fe_ven";
                strSQL+=" from tbr_tipdocprg as b1 inner join tbm_cabtipdoc as b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipdoc=b2.co_tipdoc";
                strSQL+=" right outer join";
                strSQL+=" (";
                strSQL+=" (tbm_placta AS b3 INNER JOIN tbm_cabpag AS b4";
                strSQL+=" ON b3.co_emp=b4.co_emp AND b3.co_cta=b4.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_detpag as b5";
                strSQL+=" ON b4.co_emp=b5.co_emp AND b4.co_loc=b5.co_loc AND b4.co_tipdoc=b5.co_tipdoc AND b4.co_doc=b5.co_doc";
                strSQL+=" )";
                strSQL+=" ON b2.co_emp=b3.co_emp AND b2.co_ctahab=b3.co_cta";
		strSQL+=" WHERE b4.co_emp=" + rstCab.getString("co_emp") + " AND b4.co_loc=" + rstCab.getString("co_loc") + "";
                strSQL+=" AND b4.co_tipdoc=" + rstCab.getString("co_tipdoc") + " AND b4.co_cta=" + rstCab.getString("co_cta") + "";
                strSQL+=" AND b4.co_cli=" + rstCab.getString("co_cli") + " AND b4.co_doc=" + rstCab.getString("co_doc") + "";
                System.out.println("el query es::::"+strSQL);
                rst=stm.executeQuery(strSQL);
                                
                
                if (rst.next())
                {                    
                    strAux=rst.getString("co_tipdoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);                                        
                                                            
                    strAux=retDesCorTipDoc();
                    txtTipDoc.setText((strAux==null)?"":strAux);                                        
                    System.out.println("este es el nombre del DOC:" + strAux);                    
                    
                    strAux=retNomTipDoc();
                    txtNomDoc.setText((strAux==null)?"":strAux);                                        
                    System.out.println("este es el nombre del DOC:" + strAux);                                                            
                                        
                    
                    strAux=rst.getString("codNivCta");
                    txtDesCorCta.setText((strAux==null)?"":strAux);
                    
                    
                    strAux=rst.getString("co_cta");
                    txtCodCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("deslarcta");
                    txtNomCta.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cli");
                    txtCodPro.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomcli");
                    txtNomPro.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("nd_monDoc");
                    txtMonDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_numDoc1");
                    txtNumAltUno.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("ne_numDoc2");
                    txtNumAltDos.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    txtFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));                  
                    txtFecVen.setText(objUti.formatearFecha(rst.getDate("fe_ven"),"dd/MM/yyyy"));                  
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
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
            blnHayCam=false;
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

    
    protected boolean consultarCabIns(){
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
                strSQL="";
                strSQL+="SELECT b4.co_tipdoc, b4.co_doc, b4.co_cta, b4.co_cli, b4.co_emp, b4.co_loc,  z1.ne_ultdoc,b4.st_reg";
                strSQL+=" from (tbm_placta AS b3 INNER JOIN (tbm_cabpag AS b4 inner join tbm_cabtipdoc as z1";
                strSQL+=" on b4.co_emp=z1.co_emp and b4.co_emp=z1.co_emp and b4.co_tipdoc=z1.co_tipdoc)";
                strSQL+=" ON b3.co_emp=b4.co_emp AND b3.co_cta=b4.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_detpag as b5";
                strSQL+=" ON b4.co_emp=b5.co_emp AND b4.co_loc=b5.co_loc AND b4.co_tipdoc=b5.co_tipdoc ";
                strSQL+=" AND b4.co_doc=b5.co_doc";
                strSQL+=" WHERE b4.co_emp=" + objParSis.getCodigoEmpresa() + " AND b4.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND b4.co_tipdoc=" + retCodTipDoc() + "";
                System.out.println("consulta sin filtro:" + strSQL);
                
//                strAux=txtTipDoc.getText();
//                if (!strAux.equals(""))
//                    strSQL+=" AND b4.co_tipdoc LIKE '" + strAux.replaceAll("'", "''") + "'";                                
//                strAux=txtCodCta.getText();
//                if(!strAux.equals(""))
//                    strSQL+=" AND b4.co_cta LIKE '" + strAux.replaceAll("'", "'") + "'";
                strAux=txtCodPro.getText();
                if(!strAux.equals(""))
                    strSQL+=" AND b4.co_cli LIKE '" + strAux.replaceAll("'", "'")  + "'";                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND b4.co_doc LIKE '" + strAux.replaceAll("'", "''") + "'";                                                                
                if (txtFecDoc.isFecha())
                    strSQL+=" AND b4.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                

                System.out.println("SQL con filtro123:"+strSQL);                                
                strSQL+=" ORDER BY b4.co_doc";                                                                                
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
    
  
    
/*    
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                //objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), rstCab.getInt("co_tipdoc"), rstCab.getInt("co_doc"));
            }
            else
            {
                mostrarMsgAdv("");
                blnHayCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
*/


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
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabPag AS a1";
                
                
                strSQL+=" LEFT OUTER JOIN (tbm_cabTipDoc AS a2 LEFT OUTER JOIN tbr_tipdocprg AS z1";
                strSQL+=" ON a2.co_emp=z1.co_emp AND a2.co_loc=z1.co_loc AND a2.co_tipdoc=z1.co_tipdoc)";

                
                
                //strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                strSQL+=" AND a1.st_reg <> 'E'";
                
                
                strSQL+=" AND z1.co_mnu=" + objParSis.getCodigoMenu() + "";
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc LIKE '" + strAux.replaceAll("'", "''") + "'";
//                strAux=txtCodCta.getText();
//                if (!strAux.equals(""))
//                    strSQL+=" AND a1.co_cta LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtCodPro.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";
		if (txtFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (txtFecVen.isFecha())
                    strSQL+=" AND a1.fe_ven='" + objUti.formatearFecha(txtFecVen.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtNumAltUno.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1 LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtNumAltDos.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc2 LIKE '" + strAux.replaceAll("'", "''") + "'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg:" +strSQL);
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
        
    
    
    
    
    /*
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
                strSQL="";
                strSQL+="SELECT b4.co_tipdoc, b4.co_doc, b4.co_cta, b4.co_cli, b4.co_emp, b4.co_loc,  z1.ne_ultdoc,b4.st_reg";
                strSQL+=" from (tbm_placta AS b3 INNER JOIN (tbm_cabpag AS b4 inner join tbm_cabtipdoc as z1";
                strSQL+=" on b4.co_emp=z1.co_emp and b4.co_emp=z1.co_emp and b4.co_tipdoc=z1.co_tipdoc)";
                strSQL+=" ON b3.co_emp=b4.co_emp AND b3.co_cta=b4.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_detpag as b5";
                strSQL+=" ON b4.co_emp=b5.co_emp AND b4.co_loc=b5.co_loc AND b4.co_tipdoc=b5.co_tipdoc ";
                strSQL+=" AND b4.co_doc=b5.co_doc";
                strSQL+=" WHERE b4.co_emp=" + objParSis.getCodigoEmpresa() + " AND b4.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND b4.co_tipdoc=" + retCodTipDoc() + "";
                System.out.println("consulta sin filtro:" + strSQL);
                
                strAux=txtCodCta.getText();
                if(!strAux.equals(""))
                    strSQL+=" AND b4.co_cta LIKE '" + strAux.replaceAll("'", "'") + "'";
                strAux=txtCodPro.getText();
                if(!strAux.equals(""))
                    strSQL+=" AND b4.co_cli LIKE '" + strAux.replaceAll("'", "'")  + "'";                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND b4.co_doc LIKE '" + strAux.replaceAll("'", "''") + "'";                                                                
                if (txtFecDoc.isFecha())
                    strSQL+=" AND b4.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                

                System.out.println("SQL con filtro123:"+strSQL);                                
                strSQL+=" ORDER BY b4.co_doc";                                                                                
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
                strSQL+=" SET st_reg='E'";    ////Eliminacion Logica////
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
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

    
    

/*
    private boolean eliminarCab()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {                
                stm=con.createStatement();
                strSQL="";
                strSQL+="DELETE FROM tbm_detpag";                               
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText();                
                System.out.println("en eliminarCab--detpag:"+strSQL);
                stm.executeUpdate(strSQL);                
                
                strSQL="";
                strSQL+="DELETE FROM tbm_cabpag ";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                System.out.println("en eliminarCab--cabpag:"+strSQL);
                stm.executeUpdate(strSQL);
                objAsiDia.eliminarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));  
                stm.close();
                con.close();
                stm=null;
                con=null;
            }
        }
        catch(java.sql.SQLException Evt)
        {
            try{
                con.rollback();
                con.close();
            }catch(java.sql.SQLException Evts){
                objUti.mostrarMsgErr_F1(jfrThis, Evts);
            }
                        
        }                                
        catch(Exception Evt)
        {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                                
        return blnRes;

    }
  */  
    
    
/*coloca el valor de cero si es q no contiene ningun valor
 *caso conttrario deja el valor recibido en el jtable
 */
//    protected double dblValAboBef(){
//        String strValAboBef="";
//        strValAboBef=""+tblValPnd.getValueAt(tblValPnd.getSelectedRow(), INT_TBL_DAT_VAL_ABO);
//        if(strValAboBef.equals("")){
//            dblValAboBef=0.00;
//            System.out.println("el valor es cero:" +dblValAboBef);
//        }
//        else{
//            dblValAboBef=Double.parseDouble(""+tblValPnd.getValueAt(tblValPnd.getSelectedRow(), INT_TBL_DAT_VAL_ABO));
//            System.out.println("el valor es diferente de cero:" +dblValAboBef);
//        }
//        return dblValAboBef;
//    }
    
 
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
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_ABO)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_ABO).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            //Calcular la diferencia.
            txtMonDoc.setText("" + objUti.redondeo(dblTot,objParSis.getDecimalesMostrar()));
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
        }
    }    
    
    

    
    
    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes=true;
        try {
            this.setTitle(objParSis.getNombreMenu());
            lblTit.setText(objParSis.getNombreMenu());
            txtTipDoc.setBackground(objParSis.getColorCamposObligatorios());
//            txtDesCorCta.setBackground(objParSis.getColorCamposObligatorios());
            txtNomDoc.setBackground(objParSis.getColorCamposObligatorios());
//            txtCodCta.setBackground(objParSis.getColorCamposObligatorios());
//            txtNomCta.setBackground(objParSis.getColorCamposObligatorios());            
            txtCodPro.setBackground(objParSis.getColorCamposObligatorios());
            txtNomPro.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtMonDoc.setBackground(objParSis.getColorCamposSistema());
            txtNumAltUno.setBackground(objParSis.getColorCamposObligatorios());
            /////////////////////////////////////////////////////////////////////////////////
            //Configurar JTable: Establecer el modelo.            
            vecDat=new Vector();
            vecCab=new Vector(16);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_SEL,"");
            vecCab.add(INT_TBL_DAT_TIP_DOC,"Tipo Doc.");
            vecCab.add(INT_TBL_DAT_NOM_DOC,"Nombre Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num. Doc");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec. Doc.");
            vecCab.add(INT_TBL_DAT_BAS_IMP,"Base Imponible");                                    
            vecCab.add(INT_TBL_DAT_POR_RET,"Porcent. Ret.");
            vecCab.add(INT_TBL_DAT_MON_DOC,"Val. Doc.");
            vecCab.add(INT_TBL_DAT_VAL_PND,"Val.Pend.");
            vecCab.add(INT_TBL_DAT_COD_REG, "Cod. Reg.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cod. Loc.");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cod. Doc.");
            vecCab.add(INT_TBL_DAT_VAL_ABO,"Val.Abono");
            vecCab.add(INT_TBL_DAT_AUX_ABO, "Abono Aux.");            
            vecCab.add(INT_TBL_DAT_COD_DOC_DIN, "Cod. Doc.");
            vecCab.add(INT_TBL_DAT_TIP_DOC_DIN,"Tipo Doc.");
            vecCab.add(INT_TBL_BUT_DOC_DIN, "");
            vecCab.add(INT_TBL_DAT_NOM_DOC_DIN, "Nombre Doc.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_ABO, objTblMod.INT_COL_DBL, new Integer(0), null);            
            System.out.println("Aqui toy:" + objTblMod.getColumnDataType(INT_TBL_DAT_VAL_ABO));
            tblValPnd.setModel(objTblMod);
                                    
            tblValPnd.setRowSelectionAllowed(true);
            tblValPnd.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);            

            objColNum=new ZafColNumerada(tblValPnd,INT_TBL_DAT_LIN);
            tblValPnd.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setPreferredWidth(40);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(100);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(40);

            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_BAS_IMP).setPreferredWidth(40);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_MON_DOC).setPreferredWidth(100);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(100);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(100);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC_DIN).setPreferredWidth(100);
            tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_DOC_DIN).setPreferredWidth(30);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NOM_DOC_DIN).setPreferredWidth(100);                        
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC_DIN).setPreferredWidth(100);                        
            
            
            tblValPnd.getTableHeader().setReorderingAllowed(false);
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setResizable(false);
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);

            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);            
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setResizable(false);            

//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC_DIN).setWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC_DIN).setMaxWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC_DIN).setMinWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC_DIN).setPreferredWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC_DIN).setResizable(false);            

            
            
            
            
            //tblValPnd.getTableHeader().setReorderingAllowed(false);         
            objMouMotAda=new ZafMouMotAda();
            tblValPnd.getTableHeader().addMouseMotionListener(objMouMotAda);            
            
            //para hacer editable las celdas
            vecAux=new Vector();                       
            vecAux.add("" + INT_TBL_DAT_SEL);
            vecAux.add("" + INT_TBL_DAT_VAL_ABO);
            vecAux.add("" + INT_TBL_DAT_TIP_DOC_DIN);
            vecAux.add("" + INT_TBL_BUT_DOC_DIN);
            vecAux.add("" + INT_TBL_DAT_NOM_DOC_DIN);
            
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //setEditable(true);
            objTblEdi=new ZafTblEdi(tblValPnd);
            setEditable(true);   
                        
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellRenderer(objTblCelRenChk);
            //objTblCelRenChk=null;
            objTblCelEdiChk=new ZafTblCelEdiChk();
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellEditor(objTblCelEdiChk);

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblCelEdiChk.isChecked()){
                        objTblCelEdiTxt.setCancelarEdicion(false);                                                                                                                        
                        System.out.println("estoy en el if del chk");                                                
                    }
                    else{
                        objTblCelEdiTxt.setCancelarEdicion(false);
                        System.out.println("estoy en el else");            
                    }
                }                                    

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                System.out.println("afterEdit");                                
                                if(objTblCelEdiChk.isChecked())
                                {
                                    objTblMod.setValueAt(objTblMod.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_DAT_VAL_PND),tblValPnd.getSelectedRow(),INT_TBL_DAT_VAL_ABO);
                                }
                                else
                                {
                                    objTblMod.setValueAt(null, tblValPnd.getSelectedRow(), INT_TBL_DAT_VAL_ABO);
                                }
                                calcularAboTot();
                                objAsiDia.generarDiario(tblValPnd, INT_TBL_DAT_SEL, INT_TBL_DAT_COD_DOC_DIN, INT_TBL_DAT_VAL_ABO);
                                
                            }
                });
            

                
            objTblCelRenBut=new ZafTblCelRenBut();
            tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_DOC_DIN).setCellRenderer(objTblCelRenBut);            
            strSQL="";

            strSQL+="select distinct a2.co_tipdoc, a2.tx_descor, a2.tx_deslar from tbm_cabtipdoc as a2, tbr_tipdocprg as a3";
            strSQL+=" where  a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc";
            strSQL+=" and a3.co_emp=" + objParSis.getCodigoEmpresa() + " and a3.co_loc=" +objParSis.getCodigoLocal() + "";
            strSQL+=" and a3.co_mnu=" + objParSis.getCodigoMenu() + "";   
                        
            System.out.println("la ventana de consultas::"+strSQL);
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_DAT_COD_DOC_DIN;
            intColTbl[1]=INT_TBL_DAT_TIP_DOC_DIN;
            intColTbl[2]=INT_TBL_DAT_NOM_DOC_DIN;
                        
            objTblCelEdiBut=new ZafTblCelEdiBut("Listado de cuentas contables", tblValPnd, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a2.co_tipdoc, a2.tx_descor, a2.tx_desLar", "Código, Cuenta, Nombre", intColVen, intColTbl);
            tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_DOC_DIN).setCellEditor(objTblCelEdiBut);
            //objTblCelEdiBut=null;
            intColVen=null;
            intColTbl=null;                                                                        
                
                

            
////////////////////////////////////////////////////////////////
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblCelEdiChk.isChecked()){
                        System.out.println("ya esta chekeado");                                                
                    }
                    else{
                        System.out.println("no esta chekeado");            
                    }
                }                                    

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if ((objTblCelEdiBut.getText().equals("")) || (objTblCelEdiBut.getText().length()==0))
                    {
                        objTblMod.setChecked(false, tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                        objTblMod.setValueAt(null, tblValPnd.getSelectedRow(), INT_TBL_DAT_VAL_ABO);
                    }
                    else
                    {
                        objTblMod.setChecked(true, tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                        //objTblMod.setValueAt(objTblMod.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_DAT_VAL_PND),tblValPnd.getSelectedRow(),INT_TBL_DAT_VAL_ABO);
                    }
                    calcularAboTot();
                    objAsiDia.generarDiario(tblValPnd, INT_TBL_DAT_SEL, INT_TBL_DAT_COD_DOC_DIN, INT_TBL_DAT_VAL_ABO);

                            }
                });
////////////////////////////////////////////////////////////////////////////////            
            
            objTblCelRenLblObl=new ZafTblCelRenLbl();
            objTblCelRenLblObl.setBackground(objParSis.getColorCamposObligatorios());
            
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC_DIN).setCellRenderer(objTblCelRenLblObl);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NOM_DOC_DIN).setCellRenderer(objTblCelRenLblObl);
            
            
            
            
                
                
//lo nuevo
//////////////////////////////////////////////////////
            objTblCelRenLbl=new ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                       
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_MON_DOC).setCellRenderer(objTblCelRenLbl);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setCellRenderer(objTblCelRenLbl);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            
//hasta aqui                 
/////////////////////////////////////////////////////                            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblValPnd);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setCellEditor(objTblCelEdiTxt);                    
///////////////////////////////////////////////////////////////////////////////////////////////////////                       
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblCelEdiChk.isChecked()){
                        System.out.println("estoy en el if");                        
                    }
                    else{
                        System.out.println("estoy en el else");            
                    }
                }                                    

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxt.getText().equals(""))
                    {
                        objTblMod.setValueAt( new Boolean(false), tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    else
                    {
                        objTblMod.setValueAt(new Boolean(true), tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                     }
                    calcularAboTot();
                    objAsiDia.generarDiario(tblValPnd, INT_TBL_DAT_SEL, INT_TBL_DAT_COD_DOC_DIN, INT_TBL_DAT_VAL_ABO);
                            }

                });
/////////////////////////////////////////////////////////////////////////////////////////////////////////
                objTblModLis=new ZafCxP01.ZafTblModLis();
                objTblMod.addTableModelListener(objTblModLis);
                setEditable(true);    
                
                //quitar
            //tblValPnd.setModel(objTblMod);

        }
        catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        //////////////////////////
    }
     
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }   
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////        
    //AQUI VAN LOS RENDERS Y EDITORES
    
    //////////////////////////////////////////////////////////////////////////////////////////
    
    /*para alterar el modelo de datos
     **/
    private class ZafDefTblCelRen extends javax.swing.table.DefaultTableCellRenderer {
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component objCom;
            javax.swing.JLabel lblAux;
            int intAliHor;
            String strFor, strVal;       //Formato, Valor
            objCom=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
            lblAux=(javax.swing.JLabel)objCom;
            strFor=objParSis.getFormatoNumero();
            strVal=(value==null)?"":value.toString();
            switch (column) {
                case INT_TBL_DAT_MON_DOC:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                case INT_TBL_DAT_VAL_PND:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                default:
                    intAliHor=javax.swing.JLabel.LEFT;
                    break;
            }
            lblAux.setHorizontalAlignment(intAliHor);
            lblAux.setText(strVal);
            lblAux=null;
            return this;
        }
    }
    /*para adicionar el tooltip
     **/
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblValPnd.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_SEL:
                    strMsg="Seleccione para escoger el valor a aplicar";
                    break;
                case INT_TBL_DAT_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_DAT_NOM_DOC:
                    strMsg="Nombre del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DAT_MON_DOC:
                    strMsg="Valor total a pagar especificado en el documento";
                    break;
                case INT_TBL_DAT_VAL_PND:
                    strMsg="Valor de exceso cancelado por el cliente";
                    break;
            }
            tblValPnd.getTableHeader().setToolTipText(strMsg);
        }
    }

    
    private class ZafTableModel extends javax.swing.table.AbstractTableModel {
        private Vector vecCabMod;
        private Vector vecDatMod;
        private java.util.Hashtable htbEstFil;
        
        public ZafTableModel() {
            vecCabMod=new Vector();
            vecDatMod=new Vector();
            htbEstFil=new java.util.Hashtable();
        }
        
        public int getRowCount() {
            return vecDatMod.size();
        }
        
        public int getColumnCount() {
            return vecCabMod.size();
        }
        
        public Object getValueAt(int row, int col) {
            Vector vecAux=(Vector)vecDatMod.elementAt(row);
            return vecAux.elementAt(col);
        }
        
        public String getColumnName(int col) {
            return vecCabMod.elementAt(col).toString();
        }
        
        public void setHeader(Vector cabecera) {
            vecCabMod=cabecera;
        }
        
        public void setData(Vector datos) {
            vecDatMod=datos;
            fireTableDataChanged();
        }
        
        public void setValues(Vector cabecera, Vector datos) {
            vecCabMod=cabecera;
            vecDatMod=datos;
            fireTableDataChanged();
        }
        
        public void setValueAt(Object valor, int row, int col) {
            Vector vecAux=(Vector)vecDatMod.elementAt(row);
            vecAux.setElementAt(valor, col);
            //Almacenar el estado de la fila.
            htbEstFil.put("" + row, "M");
            fireTableCellUpdated(row, col);
        }
        
        public boolean isCellEditable(int row, int col) {
            return false;
        }
        
        public Class getColumnClass(int col) {
            Object obj=getValueAt(0, col);
            if (obj==null) {
                return Object.class;
            }
            else {
                return obj.getClass();
            }
        }
        
        
        
        /**
         * Esta función permite obtener el estado de la fila especificada. Hay ocasiones en las que
         * es necesario determinar el estado de una fila y en función de eso determinar la acción
         * a tomar. Por ejemplo, se puede tener 1000 filas y modificar 2 de ellas con lo que
         * comunmente se procede a actualizar las 1000 filas (O sea, se hacen 1000 updates). Esto
         * estaría mal ya que sólo se debería actualizar las 2 filas que en realidad se modificaron.
         * @param row La fila de la que se desea obtener el estado.
         * @return Puede retornar uno de los siguientes valores:
         * <UL>
         * <LI>N: La fila no ha sido alterada
         * <LI>I: La fila ha sido insertada
         * <LI>E: La fila ha sido eliminada
         * <LI>M: La fila ha sido modificada
         * </UL>
         */
        public char getEstadoFila(int row) {
            char chrRes='O';
            try {
                chrRes=htbEstFil.get("" + row).toString().charAt(0);
            }
            catch (NullPointerException e) {
                chrRes='O';
            }
            catch (Exception e) {
                chrRes='O';
            }
            return chrRes;
        }
        
        /**
         * Esta función permite inicializar el estado de todas las filas. Es decir, que todas las
         * filas vuelven a su estado inicial. Esta función se debe utilizar cuando se haya realizado
         * alguna operación (actualización, eliminación, etc) ya que sino se considerará que la fila
         * continua en el estado que se encontraba antes de realizar dicha operación. Por ejemplo,
         * si realizó una actualización lo más conveniente es llamar a ésta función luego de haber hecho
         * dicha operación porque si la idea es modificar unas cuantas filas y grabar y repetir este
         * proceso hasta modificar todas las filas que se tenían que modificar. Lo recomendable es
         * que luego de grabar los cambios que se hicieron a las filas se utilice ésta función para
         * que la próxima vez que se vaya a grabar los cambios sólo se grabe las nuevas filas que han
         * sido alteradas.
         */
        public void inicializarEstadoFilas() {
            htbEstFil.clear();
        }
        
        /**
         * Esta función permite obtener el número total de filas alteradas. Por ejemplo, se
         * pueden tener en total 100 filas de las cuales se han modificado 5, insertado 2 y
         * eliminado 3. Con lo cual ésta función devolvería 10 porque de las 100 filas sólo
         * 10 de ellas han sido alteradas de alguna manera.
         * @return El número de filas alteradas
         */
        public int getNumeroFilasAlteradas() {
            return htbEstFil.size();
        }
        
        /**
         * Esta función permite obtener el indice de las filas alteradas. Por ejemplo, se
         * pueden tener en total 100 filas de las cuales se han alterado sólo las filas 5,
         * 8, 9, 10 y 25 con lo cual ésta función devolvería una enumeración cuyo contenido
         * sería 5, 8, 9, 10 y 25 ya que dichas filas han sido alteradas.
         * @return Una enumeración que contiene el indice de las filas alteradas
         */
        public java.util.Enumeration getFilasAlteradas() {
            return htbEstFil.keys();
        }
    }
    
/*    
    private void clnTextos()
    {
        txtTipDoc.setText("");
        txtNomDoc.setText("");
        txtCodDoc.setText("");
        txtNumAltUno.setText("");
        txtNumAltDos.setText("");        
    }
*/
 /////////////////////////////////////////////////////////////////////////
 //PARA LA BARRA DE HERRAMIENTAS
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtTipDoc.setText("");
            txtNomDoc.setText("");
//            txtCodCta.setText("");
//            txtNomCta.setText("");                        
            txtCodPro.setText("");
            txtNomPro.setText("");
            txtCodDoc.setText("");
            txtMonDoc.setText("");
            txtFecDoc.setText("");           
            txtFecVen.setText("");            
            txtNumAltUno.setText("");
            txtNumAltDos.setText("");
            txaObs1.setText("");
            txaObs2.setText("");            
            txtNumCta.setText("");
            txtNumPro.setText("");
            txtCodTipDoc.setText("");
//            txtDesCorCta.setText("");
            objAsiDia.inicializar();
            objTblMod.removeAllRows();  
            dblMonDoc=0.00;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

  /*  
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
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo";
                strSQL+=" FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cabTipDoc AS a3";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" AND a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cli=" + txtCodPro.getText();
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";
                strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')";
                strSQL+=" AND a2.nd_porret>0";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                System.out.println("cargarDocPen:"+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_SEL, "");
                    //vecReg.add(INT_TBL_DAT_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_TIP_DOC, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_NOM_DOC, rst.getString("tx_deslar"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));                    
                    vecReg.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));
                    vecReg.add(INT_TBL_DAT_BAS_IMP, rst.getString("basImp"));                    
                    vecReg.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));
                    //double dblValDoc=Double.parseDouble(""+rst.getString("mo_pag"));
                    vecReg.add(INT_TBL_DAT_MON_DOC, ""+rst.getString("mo_pag"));
                    //double dblValAbo=Double.parseDouble(""+rst.getDouble("nd_abo"));
                    vecReg.add(INT_TBL_DAT_VAL_PND, ""+rst.getString("valPnd"));                                     
                    vecReg.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));                    
                    vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));                                                           
                    vecReg.add(INT_TBL_DAT_VAL_ABO, "");
                    vecReg.add(INT_TBL_DAT_AUX_ABO, "");                                        
                    vecReg.add(INT_TBL_DAT_COD_DOC_DIN, "");                    
                    vecReg.add(INT_TBL_DAT_TIP_DOC_DIN, "");
                    vecReg.add(INT_TBL_BUT_DOC_DIN, "");                                        
                    vecReg.add(INT_TBL_DAT_NOM_DOC_DIN, "");                                                            
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
                tblValPnd.setModel(objTblMod);
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
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo";
                strSQL+=", (a2.mo_pag/a2.nd_porret) as basImp";
                strSQL+=" FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cabTipDoc AS a3";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" AND a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_cli=" + txtCodPro.getText();
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";
                strSQL+=" AND a2.nd_porRet>0";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                System.out.println("cargarDocPen:"+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {                                                           
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_SEL, "");
                    //vecReg.add(INT_TBL_DAT_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_TIP_DOC, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_NOM_DOC, rst.getString("tx_deslar"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));                    
                    vecReg.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));
                    vecReg.add(INT_TBL_DAT_BAS_IMP, rst.getString("basImp"));                    
                    vecReg.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));
                    //double dblValDoc=Double.parseDouble(""+rst.getString("mo_pag"));
                    vecReg.add(INT_TBL_DAT_MON_DOC, ""+rst.getString("mo_pag"));
                    //double dblValAbo=Double.parseDouble(""+rst.getDouble("nd_abo"));
                    vecReg.add(INT_TBL_DAT_VAL_PND, ""+rst.getString("valPnd"));                                     
                    vecReg.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));                    
                    vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));                                                           
                    vecReg.add(INT_TBL_DAT_VAL_ABO, "");
                    vecReg.add(INT_TBL_DAT_AUX_ABO, "");                                        
                    vecReg.add(INT_TBL_DAT_COD_DOC_DIN, "");                    
                    vecReg.add(INT_TBL_DAT_TIP_DOC_DIN, "");
                    vecReg.add(INT_TBL_BUT_DOC_DIN, "");                                        
                    vecReg.add(INT_TBL_DAT_NOM_DOC_DIN, "");                                                            
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
                tblValPnd.setModel(objTblMod);
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
    
    

    
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        //double dblValPnd=0.00;
        try
        {
            if (!txtCodPro.getText().equals(""))
            {
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
                        strSQL+="select";
                        strSQL+=" a1.co_loc, a1.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, a2.mo_pag, a2.co_reg, a4.nd_abo as aboDoc, a2.co_loc, a2.co_doc as codDoc,";
                        strSQL+=" (a2.mo_pag+a2.nd_abo) as valPnd, a4.nd_abo,";
                        strSQL+=" (a2.mo_pag/a2.nd_porret) as basImp, a4.co_tipdoccon";
                        strSQL+=" FROM tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp";
                        strSQL+=" AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc";
                        strSQL+=" AND a1.co_tipDoc=a3.co_tipDoc) LEFT OUTER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp";
                        strSQL+=" AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag";
                        strSQL+=" AND a2.co_reg=a4.co_regPag AND a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a4.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a4.co_tipDoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND a4.co_doc=" + txtCodDoc.getText() + ")";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a1.co_cli=" + txtCodPro.getText() + "";
                        strSQL+=" AND ((a2.mo_pag+a2.nd_abo)>0";
                        strSQL+=" OR a4.nd_abo IS NOT NULL) AND a2.st_reg IN ('A','C')";
                        strSQL+=" AND a2.nd_porret>0";
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc,";
                        strSQL+=" a1.co_doc, a2.co_reg";
                        
                        
                        
                    }
                    else
                    {
                        //Para los demás modos se muestra: sólo los documentos pagados.
                        strSQL="";
                        strSQL+="SELECT a4.co_doc,a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, a2.mo_pag, a2.co_reg, a4.nd_abo as aboDoc, a2.co_loc, a2.co_doc as codDoc, (a2.mo_pag+a2.nd_abo) as valPnd";
                        strSQL+=", (a2.mo_pag/a2.nd_porret) as basImp, a4.co_tipdoccon";
                        strSQL+=" FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cabTipDoc AS a3, tbm_detPag AS a4";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" AND a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                        strSQL+=" AND a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag";
                        strSQL+=" AND a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        //strSQL+=" AND a2.nd_porRet>0";
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("en cargarDetReg--consulta:"+strSQL);
                    }
                    System.out.println("cargarDetReg:"+strSQL);
                    rst=stm.executeQuery(strSQL);
                    vecDat.clear();

                    
                    for(int i=0;rst.next();i++){                 
                        vecReg=new Vector();                   
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_SEL, "");
                        vecReg.add(INT_TBL_DAT_TIP_DOC, rst.getString("co_tipdoc"));
                        //vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor")==null?);
                        vecReg.add(INT_TBL_DAT_NOM_DOC, rst.getString("tx_deslar"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));                    
                        vecReg.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));
                        vecReg.add(INT_TBL_DAT_BAS_IMP, rst.getString("basImp"));                                        
                        vecReg.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));
                        double dblValDoc=Double.parseDouble(""+rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_MON_DOC, ""+dblValDoc);
                        vecReg.add(INT_TBL_DAT_VAL_PND, ""+rst.getDouble("valPnd"));                 

                        double dblAuxAbo=rst.getString("aboDoc")==null?0:rst.getDouble("aboDoc");                    
                        //double dblAux=dblAuxAbo*(-1);
                        vecReg.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));                    
                        vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("codDoc"));                                                           
                        vecReg.add(INT_TBL_DAT_VAL_ABO, ""+ dblAuxAbo);
                        vecReg.add(INT_TBL_DAT_AUX_ABO, ""+ dblAuxAbo);
                         
                        int intCodDocDin=rst.getInt("co_tipdoccon");
                        vecReg.add(INT_TBL_DAT_COD_DOC_DIN, ""+intCodDocDin);

                        String strTmpDesCor=retDesCorTipDocDin(intCodDocDin);
                        String strTmpDesLar=retDesLarTipDocDin(intCodDocDin);                                                
                        
                        vecReg.add(INT_TBL_DAT_TIP_DOC_DIN,""+strTmpDesCor);
                        vecReg.add(INT_TBL_BUT_DOC_DIN,"");
                        vecReg.add(INT_TBL_DAT_NOM_DOC_DIN,""+strTmpDesLar);

                        if (dblAuxAbo!=0)
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_SEL);
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
                    tblValPnd.setModel(objTblMod);
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
        
    
    
/*    
    protected boolean cargarDetReg(){
        boolean blnRes=true;
        java.sql.Connection conDetReg;
        java.sql.Statement stmDetReg;
        java.sql.ResultSet rstDetReg;
        double dblValPnd=0.00;
        
        try{
            vecDat=new Vector();
            conDetReg=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conDetReg!=null){                      
                stmDetReg=conDetReg.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="select distinct a3.co_tipdoc, a1.tx_descor, a1.tx_deslar, a2.ne_numdoc, a2.fe_doc,";
                strSQL+=" a3.ne_diacre, a3.fe_ven, a3.nd_porret, a3.mo_pag, a3.co_reg, a3.nd_abo,";
                strSQL+=" a3.co_loc, a3.co_doc";
                strSQL+=", (a3.mo_pag+nd_abo) as valPnd";
                strSQL+=" from tbr_tipdocprg as z1 inner join";
                strSQL+=" tbm_cabtipdoc as a1";
                strSQL+=" on z1.co_emp=a1.co_emp and z1.co_loc=a1.co_loc and z1.co_tipdoc=a1.co_tipdoc";
                strSQL+=" left outer join tbm_cabmovinv as a2 on a1.co_emp=a2.co_emp and";
                strSQL+=" a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc left outer join tbm_pagmovinv as a3";
                strSQL+=" on a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc and";
                strSQL+=" a2.co_doc=a3.co_doc";
                strSQL+=" where a3.co_emp=" + rstCnsDet.getString("co_emp") + " and a3.co_loc=" + rstCnsDet.getString("co_loc") + "";
                strSQL+=" and a3.co_tipdoc=" + rstCnsDet.getString("co_tipdoc") + ""; 
                strSQL+=" and a2.co_cli=" + rstCnsDet.getString("co_cli") + "";
                strSQL+=" and (a3.mo_pag+a3.nd_abo)>0";
                System.out.println("en cargarDetReg:"+strSQL);
                rstDetReg=stmDetReg.executeQuery(strSQL);
                
                for(int i=0;rstDetReg.next();i++){                 
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_SEL, "");
                    vecReg.add(INT_TBL_DAT_TIP_DOC, rstDetReg.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_NOM_DOC, rstDetReg.getString("tx_deslar"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rstDetReg.getString("ne_numdoc"));                    
                    vecReg.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rstDetReg.getDate("fe_doc"), "dd-MM-yyyy"));
                    vecReg.add(INT_TBL_DAT_DIA_CRE, rstDetReg.getString("ne_diacre"));                                        
                    vecReg.add(INT_TBL_DAT_FEC_VEN, objUti.formatearFecha(rstDetReg.getDate("fe_ven"), "dd-MM-yyyy"));
                    vecReg.add(INT_TBL_DAT_POR_RET, rstDetReg.getString("nd_porret"));

                    double dblValDoc=Double.parseDouble(""+rstDetReg.getString("mo_pag"));
                    //vecReg.add(INT_TBL_DAT_MON_DOC, rstDetReg.getString("mo_pag"));
                    vecReg.add(INT_TBL_DAT_MON_DOC, ""+dblValDoc);

                    double dblValAbo=Double.parseDouble(""+rstDetReg.getDouble("nd_abo"));
                    dblValAbo=dblValAbo*(-1);

                    dblValPnd=Double.parseDouble(""+rstDetReg.getDouble("valPnd"));
                    vecReg.add(INT_TBL_DAT_VAL_PND, ""+dblValPnd);
                    
                    vecReg.add(INT_TBL_DAT_VAL_ABO, "");
                    vecReg.add(INT_TBL_DAT_COD_REG, rstDetReg.getString("co_reg"));
                    
                    vecReg.add(INT_TBL_DAT_COD_LOC, rstDetReg.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rstDetReg.getString("co_doc"));                                       
                    
                    vecDat.add(vecReg);                                                            
                }                
            }
            conDetReg.close();
            conDetReg=null;
            stmDetReg=null;
            rstDetReg=null;
            objTblMod.setData(vecDat);
            tblValPnd.setModel(objTblMod);
        }
        catch(SQLException Evt)
       {
           blnRes=false;   
           objUti.mostrarMsgErr_F1(jfrThis, Evt);              
        }
        catch(Exception Evt)
        {
           blnRes=false;
           objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        return blnRes;
    }
  */
    
     private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    
    protected boolean lleDetCon(){ 
        boolean blnRes=true;
        try{
            conCnsDet=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),  objParSis.getClaveBaseDatos());
            if(conCnsDet!=null){
                stmCnsDet=conCnsDet.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                    strSQL+="select distinct a3.co_emp, a3.co_loc, a1.co_tipdoc, a2.co_cli, a3.co_doc, a3.co_reg";
                    //strSQL+=", a1.tx_descor, a1.tx_deslar, a2.ne_numdoc, a2.fe_doc,";
//                    strSQL+=" a3.ne_diacre, a3.fe_ven, a3.nd_porret, a3.mo_pag, a3.co_reg";
                    strSQL+=" from tbr_tipdocprg as z1 inner join";
                    strSQL+=" tbm_cabtipdoc as a1";
                    strSQL+=" on z1.co_emp=a1.co_emp and z1.co_loc=a1.co_loc and z1.co_tipdoc=a1.co_tipdoc";
                    strSQL+=" left outer join tbm_cabmovinv as a2 on a1.co_emp=a2.co_emp and";
                    strSQL+=" a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc left outer join tbm_pagmovinv as a3";
                    strSQL+=" on a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc and";
                    strSQL+=" a2.co_doc=a3.co_doc";
                    strSQL+=" where a3.co_emp=" + objParSis.getCodigoEmpresa() + " and a3.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" and a3.co_tipdoc=" + INT_COD_DOC + "";
                    strAux=txtCodPro.getText();
                    if(!strAux.equals("")){
                        strSQL+=" AND a2.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";
                    }
                    System.out.println("SQL con filtro:" +strSQL);
                    rstCnsDet=stmCnsDet.executeQuery(strSQL);

                    if(rstCnsDet.next())
                    {
                        rstCnsDet.last();
                        objTooBar.setMenSis("Se encontraron " + rstCnsDet.getRow() + " registros");
                        rstCnsDet.first();
                        cargarDetReg();


                    }
                    else{
                        mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                        limpiarFrm();
                        objTooBar.setEstado('l');
                        objTooBar.setMenSis("Listo");
                    }
            }
            rstCnsDet.close();
            stmCnsDet.close();
            conCnsDet.close();
            rstCnsDet=null;
            stmCnsDet=null;
            conCnsDet=null;
        }
        catch(java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    private void consultarCamUsr()
    {
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'm': //Modificar
                        System.out.println("Modo Modificar");
                        break;
                    case 'e': //Eliminar
                        System.out.println("Modo Eliminar");
                        break;
                    case 'n': //insertar
                        System.out.println("Modo Ninguno");
//                        objTooBar.insertar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                break;
            case 2: //CANCEL_OPTION
                break;
        }
    //nuevos metodos del toolbar
                    
    }   

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


    private boolean isCamVal()
    {
        if (txtTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de tipo de documento y vuelva a intentarlo.</HTML>");
            txtTipDoc.requestFocus();
            return false;
        }

        if (txtNomDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento para la cuenta y vuelva a intentarlo.</HTML>");
            txtNomDoc.requestFocus();
            return false;
        }

        if(txtCodPro.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Código de Proveedor</FONT> es obligatorio.<BR>Escriba un código de proveedor y vuelva a intentarlo.</HTML>");
            return false;
        }

        if(txtNomPro.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Proveedor</FONT> es obligatorio.<BR>Escriba un nombre de proveedor y vuelva a intentarlo.</HTML>");            
            return false;
        }

        if(txtFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba una fecha de documento y vuelva a intentarlo.</HTML>");            
            return false;
        }

        if(txtFecVen.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Vencimiento</FONT> es obligatorio.<BR>Escriba una fecha de vencimiento y vuelva a intentarlo.</HTML>");            
            return false;
        }     
        
        if(txtNumAltUno.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Retención</FONT> es obligatorio.<BR>Escriba un número de retención y vuelva a intentarlo.</HTML>");            
            return false;
        }        
        
        
        
        for(int i=0; i<tblValPnd.getRowCount();i++){

            if(objTblCelEdiChk.isChecked()){
                if((tblValPnd.getValueAt(i, INT_TBL_DAT_LIN)=="M")||(tblValPnd.getValueAt(i, INT_TBL_DAT_LIN)=="I")){
                    if(tblValPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC_DIN).equals("")){
                        tabFrm.setSelectedIndex(0);
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de Documento</FONT> del detalle es obligatorio.<BR>Escriba el Tipo de Documento y vuelva a intentarlo.</HTML>");            
                        return false;
                    }
                }
                else{
                    System.out.println("la fila no ha sido insertada ni modificada");            
                }
            
            }
        }
                
        
        
        
        
        return true;
    }

    

      /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        int intCodUsr, intUltReg;
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
                strSQL+=", tx_ruc, tx_nomCli, tx_dirCli, nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                strSQL+=" VALUES (";
                strSQL+=objParSis.getCodigoEmpresa();
                strSQL+=", " + objParSis.getCodigoLocal();
                strSQL+=", " + txtCodTipDoc.getText();
                strSQL+=", " + intUltReg;
                strSQL+=", '" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", '" + objUti.formatearFecha(txtFecVen.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", " + objUti.codificar(txtNumAltUno.getText(),2);
                strSQL+=", " + objUti.codificar(txtNumAltDos.getText(),2);
                strSQL+=", Null";
//                strSQL+=", " + txtCodCta.getText();
                strSQL+=", " + objUti.codificar(txtCodPro.getText(),2);
                strSQL+=", " + objUti.codificar(strIdePro);
                strSQL+=", " + objUti.codificar(txtNomPro.getText());
                strSQL+=", " + objUti.codificar(strDirPro);
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=", " + objUti.codificar(txaObs1.getText());
                strSQL+=", " + objUti.codificar(txaObs2.getText());
                strSQL+=", 'A'";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";
                strSQL+=", '" + strAux + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
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

  
  
    
/*
    private boolean insertarCab()
    {
        int intCodEmp, intCodUsr, intUltDoc, intUltReg;
        boolean blnRes=true;
            String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
            String strFecVnc=objUti.formatearFecha(txtFecVen.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");                
        try
        {            
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodUsr=objParSis.getCodigoUsuario();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
            stm = con.createStatement();
            con.setAutoCommit(false);


                String SQL="";
                SQL+="SELECT MAX(a2.co_reg)";
                SQL+=" FROM tbm_detpag AS a2";
                SQL+=" WHERE a2.co_emp=" + intCodEmp + " AND a2.co_loc=" + objParSis.getCodigoLocal() + " AND" ;
                SQL+=" a2.co_tipdoc=" + txtCodTipDoc.getText() + "";
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), SQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                               
                
                String sSQL="";
                sSQL+="SELECT MAX(a2.ne_ultdoc)";
                sSQL+=" FROM tbm_cabtipdoc AS a2";
                sSQL+=" WHERE a2.co_emp=" + intCodEmp + " AND a2.co_loc=" + objParSis.getCodigoLocal() + " AND" ;
                sSQL+=" a2.co_tipdoc=" + txtCodTipDoc.getText() + "";
                intUltDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), sSQL);
                if (intUltDoc==-1)
                    return false;
                intUltDoc++;           
            
                String varCodAlt;
                strSQL="";
                strSQL+="INSERT INTO tbm_cabpag(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, fe_ven, ne_numDoc1, ne_numDoc2,";
		strSQL+=" co_cta,co_cli,tx_nomCli,nd_monDoc,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,co_usrIng,co_usrMod)";                
                strSQL+=" VALUES (";
                strSQL+="" + intCodEmp + "," + objParSis.getCodigoLocal() + "," + txtCodTipDoc.getText() + "," + intUltDoc + "," ;
                strSQL+="'#" + strFecDoc + "#', '#" + strFecVnc + "#'," + txtNumAltUno.getText() + "," + txtNumAltDos.getText() + ",";                                                               
                strSQL+="" + txtCodCta.getText() + ",";
                strSQL+="" + txtCodPro.getText() + ",'" + txtNomPro.getText() + "'," + txtMonDoc.getText() + ",";
                strSQL+="'" + txaObs1.getText() + "', '" + txaObs2.getText() + "'," ;
                strSQL+="" + "'A'" + ",'#" + strFecDoc + "#', '#" + strFecDoc + "#'," + objParSis.getCodigoUsuario() + ",";
                strSQL+="" + objParSis.getCodigoUsuario() + ")";
                System.out.println("SQL de cab:"+strSQL);
                stm.executeUpdate(strSQL);

                System.out.println("antes de detpag");
                //+ tblValPnd.getValueAt(i,INT_TBL_DAT_COD_REG) + 
                
                for(int i=0; i<tblValPnd.getRowCount();i++){
                    if(objTblMod.getEstadoFila(i)=='M'){
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detpag";
                        strSQL+=" (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locpag, co_tipdocpag, co_docpag, co_regpag,";
                        strSQL+=" nd_abo, co_tipdoccon, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq)";
                        strSQL+=" VALUES (";
                        strSQL+="" + objParSis.getCodigoEmpresa() + "," + objParSis.getCodigoLocal() + "," + txtCodTipDoc.getText() + ",";
                        strSQL+="" + intUltDoc + "," + intUltReg + ",";
                        strSQL+="" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_LOC) + "," + tblValPnd.getValueAt(i, INT_TBL_DAT_TIP_DOC) + ",";
                        strSQL+="" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_DOC) + "," + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_REG) + ",";
                        strSQL+="" + tblValPnd.getValueAt(i, INT_TBL_DAT_VAL_ABO) + "," + tblValPnd.getValueAt(i, INT_TBL_DAT_TIP_DOC) + ",";
                        strSQL+="" + null + "," + null + "," + txtNumAltUno.getText() + ",'#" + strFecDoc + "#',";
                        strSQL+="'#" + strFecVnc + "#'";
                        strSQL+=")";
                        System.out.println("SQL de det:"+strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }

                for(int i=0; i<tblValPnd.getRowCount();i++){
                    
                    if(objTblMod.getEstadoFila(i)=='M'){
                        System.out.println("dentro del if de pagmovinv");
                        double dblValAbo1=Double.parseDouble(""+tblValPnd.getValueAt(i, INT_TBL_DAT_VAL_ABO));
                        dblValAbo1=dblValAbo1*(-1);
                        System.out.println("el valor del abono es:" +dblValAbo1);
                        strSQL="";
                                                                        
                        
                        strSQL+="UPDATE tbm_pagmovinv";
                        strSQL+=" SET nd_abo=nd_abo" + dblValAbo1 + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" and co_tipdoc=" + tblValPnd.getValueAt(i, INT_TBL_DAT_TIP_DOC) + " and co_doc=" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_DOC);
                        strSQL+=" and co_reg=" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_REG) + "";
                        System.out.println("SQL de pagmovinv:"+strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }
                
                ////////////////////////////////////////////////////////////////////////////////////                
                asiDia();
                objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),Integer.parseInt(txtCodTipDoc.getText()),intUltDoc);
                con.commit();
                con.setAutoCommit(true);                       
                stm.executeUpdate(strSQL);    
                System.out.println("Despues del insert de diario");
                stm.close();
                con.close();
                stm=null;
                con=null;
            }
        }
        catch(java.sql.SQLException Evt)
        {
            try{
                con.rollback();
                con.close();
            }catch(java.sql.SQLException Evts){
                objUti.mostrarMsgErr_F1(jfrThis, Evts);
            }
                        
        }                                
        catch(Exception Evt)
        {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                                
        return blnRes;
    }
 */
    /*
    private boolean actualizarCab()
    {                
        int intCodEmp, intCodUsr;
        boolean blnRes=true;        
        Librerias.ZafDate.ZafDatePicker dtePckPag2 = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");                    
        int Fec2[] =  dtePckPag2.getFecha(dtePckPag2.getText());
        String strFec2 = "#" + Fec2[2] + "/"+Fec2[1] + "/" + Fec2[0] + "#";        
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodUsr=objParSis.getCodigoUsuario();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();                
                strSQL="";                
                strSQL+="UPDATE tbm_cabpag";
                strSQL+=" SET co_cli=" + txtCodPro.getText()+ ", tx_nomcli='" + txtNomPro.getText() + "', nd_mondoc=" + txtMonDoc.getText()+ ",";                
                strSQL+=" tx_obs1=" + ((strAux=txaObs1.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") + ",";
                strSQL+=" tx_obs2=" + ((strAux=txaObs2.getText().replaceAll("'", "''")).equals("")?"Null":"'" + strAux + "'") + ",";
                strSQL+=" fe_ultMod='" + strFec2 + "',";
                strSQL+=" co_usrMod=" + intCodUsr + "";
                strSQL+=" WHERE co_emp=" + intCodEmp + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc= " + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                System.out.println("SQL del update:"+strSQL);
                stm.executeUpdate(strSQL);
                
                
                if(objAsiDia.isDiarioCuadrado()){
                    if(objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "", objUti.parseDate(txtFecDoc.getText(),"yyyy/MM/dd"), "A")){
                        if(objAsiDia.isDocumentoCuadrado(Double.parseDouble(""+txtMonDoc.getText()))){                          
                                con.commit();
                        }
                        else{
                            mostrarMsgInf("<HTML>El monto no es igual al valor del Asiento de Diario.<BR>Cuadre el monto con el valor de asiento de diario y vuelva a intentarlo.</HTML>");
                            blnRes=false;
                        }                        
                        con.commit();
                    }
                    else{
                        con.rollback();
                    }
                }
                else{
                    mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                    blnRes=false;
                }

                stm.close();
                //con.close();
                stm=null;
                //con=null;
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
                strAux=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strAux=objUti.formatearFecha(txtFecVen.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_ven='" + strAux + "'";
                strSQL+=", ne_numDoc1=" + objUti.codificar(txtNumAltUno.getText(),2);
                strSQL+=", ne_numDoc2=" + objUti.codificar(txtNumAltDos.getText(),2);
//                strSQL+=", co_cta=" + txtCodCta.getText();
                strSQL+=", co_cli=" + objUti.codificar(txtCodPro.getText(),2);
                strSQL+=", tx_ruc=" + objUti.codificar(strIdePro);
                strSQL+=", tx_nomCli=" + objUti.codificar(txtNomPro.getText());
                strSQL+=", tx_dirCli=" + objUti.codificar(strDirPro);
                strSQL+=", nd_monDoc=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                System.out.println("SQL del update:"+strSQL);
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
                    ////objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
                    objAsiDia.generarDiario(tblValPnd, INT_TBL_DAT_SEL, INT_TBL_DAT_TIP_DOC_DIN, INT_TBL_DAT_VAL_ABO);
                    //objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), strUbiCta, txtMonDoc.getText(), INT_POR_RET, STR_COD_CTA_POR_RET);
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
            ////objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), txtMonDoc.getText(), txtMonDoc.getText());
            txtMonDoc.setText("0");
            //objAsiDia.generarDiario(txtCodTipDoc.getText(), txtCodCta.getText(), strUbiCta, txtMonDoc.getText(), INT_POR_RET, STR_COD_CTA_POR_RET);
            objAsiDia.generarDiario(tblValPnd, INT_TBL_DAT_SEL, INT_TBL_DAT_TIP_DOC_DIN, INT_TBL_DAT_VAL_ABO);
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
                    txtTipDoc.setText(rst.getString("tx_desCor"));
                    txtNomDoc.setText(rst.getString("tx_desLar"));
                    txtNumAltUno.setText("" + (rst.getInt("ne_ultDoc")+1));
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
     * Esta función muestra el la cuenta contable predeterminado del programa 
     * de acuerdo al tipo de documento predeterminado.
     * @return true: Si se pudo mostrar la cuenta contable predeterminado.
     * <BR>false: En el caso contrario.
     */
    /*
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
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a2.st_reg='S'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodCta.setText(rst.getString("co_cta"));
                    txtDesCorCta.setText(rst.getString("tx_codCta"));
                    txtNomCta.setText(rst.getString("tx_desLar"));
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
    */

    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet()
    {
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc;
        String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
        String strFecVnc=objUti.formatearFecha(txtFecVen.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");                
        boolean blnRes=true;
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
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_DAT_SEL))
                    {                        
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
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_ABO), 3);
                        System.out.println("el codigo de cabtipdoc es:" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_DOC_DIN));
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_DIN);
                        strSQL+=", Null";
                        strSQL+=", Null";
                        strSQL+=", " + txtNumAltUno.getText();
                        strSQL+=", '#" + strFecDoc;
                        strSQL+="#', '#" + strFecVnc;
                        strSQL+="#')";
                        System.out.println("insertarDet:" +strSQL);
                        stm.executeUpdate(strSQL);
                        j++;
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
    

    protected char equalsFec(){    
        char chrstPos='N';
        if((txtFecDoc.isFecha()) && (txtFecVen.isFecha())){
            int FecDoc[] = txtFecDoc.getFecha(txtFecDoc.getText());
            String strFecDoc = "" + FecDoc[2] + "/" + FecDoc[1] + "/" + FecDoc[0] +"" ;
            
            int FecVen[] = txtFecVen.getFecha(txtFecVen.getText());
            String strFecVen = "" + FecVen[2] + "/" + FecVen[1] + "/" + FecVen[0] +"" ;

            if(strFecDoc.equals(strFecVen)){
                System.out.println("las fechas son iguales");
                //chrstPos='N';
            }
            else{
                System.out.println("las fechas son diferentes");            
                chrstPos='S';
            }
        }
        return chrstPos;
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
                                if (objTblMod.getValueAt(i,INT_TBL_DAT_VAL_ABO)!=objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO))
                                {
                                    dblAbo1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                    dblAbo2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=nd_abo+" + intSig*objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" ,st_pos='"+ equalsFec();
                                    strSQL+="' WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("en actualizarTbmpagmovinv -- 0 y 1:"+strSQL);
                                    stm.executeUpdate(strSQL);
                                    objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_ABO),i,INT_TBL_DAT_AUX_ABO);
                                }
                                break;
                            case 2:
                            case 3:
                                dblAbo1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                dblAbo2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_pagMovInv";
                                strSQL+=" SET nd_abo=nd_abo+" + intSig*(-dblAbo2);
                                strSQL+=" ,st_pos='"+ equalsFec();
                                strSQL+="' WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                                System.out.println("en actualizarTbmpagmovinv--2 y 3:"+strSQL);
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
                if (insertarCab())
                {
                    if (insertarDet())
                    {
                        if (actualizarTbmPagMovInv(0))
                        {
                            //asiDia();
                            //if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumAltUno.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy")))
                            if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
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
                System.out.println("en eliminarDet:"+strSQL);
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
                            if (actualizarTbmPagMovInv(1))
                            {
                                if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumAltUno.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A"))
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
                //if (eliminarDet())
                //{
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
                //}
                //else
                //    con.rollback();
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
    
    
    public class mitoolbar extends ZafToolBar{
            public mitoolbar(javax.swing.JInternalFrame jfrThis){
                super(jfrThis, objParSis);
            }

/*            
      public void clickInsertar() {
            //objTblMod.removeAllRows();  
            limpiarFrm();
            txtTipDoc.setEnabled(true);
            txtNomDoc.setEnabled(true);
            txtCodPro.setEnabled(true);
            txtNomPro.setEnabled(true);
            txtCodCta.setEnabled(true);
            txtNomCta.setEnabled(true);
            txtCodDoc.setEnabled(false);
            txtNumAltUno.setEnabled(true);
            txtNumAltDos.setEnabled(true);
            txtMonDoc.setEnabled(false);
            txtFecDoc.setHoy();
            txtFecVen.setHoy(); 
            objAsiDia.setEditable(true);
            objAsiDia.setDiarioModificado(false);
    }
 */
            
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
                txtFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                txtFecVen.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
//                if (!txtCodTipDoc.getText().equals(""))
//                    mostrarCtaPre();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objAsiDia.setEditable(true);
                txtNumAltUno.selectAll();
                txtNumAltUno.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
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
            
            
            
        public boolean insertar(){

            if (!insertarReg())
                return false;
            limpiarFrm();
            return true;
                        
            
//            boolean blnRes=true;            
//            if(!isCamVal())
//                blnRes=false;
//            if(!insertarCab())
//                blnRes=false;
//            objTooBar.setEstado('n');            
//            return blnRes;

        }
        
        public void clickConsultar(){
            //txtTipDoc.setEnabled(true);
            //txtNomDoc.setEnabled(true);
            //txtCodDoc.setEnabled(true);
            //txtCodPro.setEnabled(false);
            //txtNomPro.setEnabled(false);                
            //txtNumAltUno.setEnabled(true);
            //if(!txtFecDoc.isFecha())
                //txtFecDoc.setHoy();    
            
            cargarDetReg();
            txtTipDoc.requestFocus();
            //txtTipDoc.requestFocus();

            
        }
            
            
       public boolean consultar() {
           //_consultar();
           consultarReg(); 
           return true;                
       }
            
                                    
       public void clickModificar(){
            txtTipDoc.setEditable(false);
            txtNomDoc.setEditable(false);
            butDoc.setEnabled(false);
            txtCodPro.setEditable(false);
            txtNomPro.setEditable(false);
            butPro.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtMonDoc.setEditable(true);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objAsiDia.setEditable(true);
            cargarDetReg();
            txtNumAltUno.selectAll();
            txtNumAltUno.requestFocus();                     
       }
       
       
       public boolean modificar(){                           
            if (!actualizarReg())
                return false;
            return true;

       }
            
        public void clickEliminar(){
            cargarDetReg();
        }
            
        public boolean eliminar(){
            try
            {
                System.out.println("estoy en eliminar");
                if (!eliminarReg()){
                    System.out.println("pase eliminar reg");
                    return false;
                }
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    System.out.println("la siguiente linea llama a cargarReg");
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
            
        
        public void clickAnular(){
                cargarDetReg();
            }

        public boolean anular(){   
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;                        
//            strAux=objTooBar.getEstadoRegistro();
//            System.out.println("el estado del registro es:" + objTooBar.getEstadoRegistro());
//            if (strAux.equals("Eliminado"))
//            {
//                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
//                return false;
//            }
//            if (strAux.equals("Anulado"))
//            {
//                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
//                return false;
//            }            
//
//            System.out.println("Estoy en la opcion de anular");                            
//           try{
//               
//               conAnu =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());                
//               conAnu.setAutoCommit(false);
//               String strStatus = rstCab.getString("st_reg");
//               if(!strStatus.equals("I")){
//                   try{               
//                    if (conAnu!=null)
//                        {
//                            stmAnu=conAnu.createStatement();                
//                            strSQL="";                
//                            strSQL+="UPDATE tbm_cabpag";
//                            strSQL+=" SET st_reg= 'I'";                
//                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipdoc= " + txtCodTipDoc.getText() + "";
//                            strSQL+=" AND co_doc=" + txtCodDoc.getText();                            
//                            stmAnu.executeUpdate(strSQL);
//                            objAsiDia.anularDiario(conAnu, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
//                        }                    
//                            
//                            conAnu.commit();
//                            conAnu.setAutoCommit(true);                                          
//                            
//                            stmAnu.close();
//                            conAnu.close();
//                            stmAnu=null;
//                            conAnu=null;
//                            
//                            objTooBar.setEstadoRegistro("Anulado");
//                            blnHayCam=false;
//                            return true;                                    
//                   }           
//                   catch(SQLException Evt)
//                   {     
//                          try{
//                              conAnu.rollback();
//                              conAnu.close();
//                          }catch(SQLException Evts){
//                              objUti.mostrarMsgErr_F1(jfrThis, Evts);
//                          }
//                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                          return false;
//                    }
//
//                    catch(Exception Evt)
//                    {
//                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                          return false;
//                    }                       
//               }
//                   }catch(SQLException Evt)
//                   {      
//                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                          return false;
//                   }
//            return true;                                            
            }    
            
            
     public void clickInicio() {
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
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
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
     
     public void clickAnterior() {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                        //consultarCamUsr();
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                        //if (!cargarCabReg())
                            //mostrarMsgAdv("");                        
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
     
     
     public void clickSiguiente() {
        try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                            
                        //consultarCamUsr();
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
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
     
     public void clickFin() {
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
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
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
     
     public void clickVisPreliminar(){
         
     }
     
     public void clickAceptar(){
          limpiarFrm();
          objTblMod.removeAllRows();
     }

     public void clickCancelar(){
          limpiarFrm();
          objTblMod.removeAllRows();
     }

     private int Mensaje(){
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
                strMsg+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";

        //        strMsg+="Si no guarda los cambios antes de desplazarse a otro registro";
        //        strMsg+="\n perderï¿½ toda la informaciï¿½n que no haya guardado";
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
                
     }
     private void MensajeValidaCampo(String strNomCampo){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
     }
            

     private boolean validaCampos(){               
                if(txtCodPro.getText().equals("") ){
                   MensajeValidaCampo("Proveedor");
                   txtCodPro.requestFocus();
                   return false;
               }
               if(!txtFecDoc.isFecha()){
                   MensajeValidaCampo("Fecha de Documento");
                   txtFecDoc.requestFocus();
                   return false;
               }
               if(!txtMonDoc.getText().equals("")){
                   MensajeValidaCampo("Monto del Documento");
                   txtMonDoc.requestFocus();
                   return false;
               }                
                
                
               int intColObligadas[] = {INT_TBL_DAT_NUM_DOC,INT_TBL_DAT_MON_DOC,INT_TBL_DAT_VAL_PND,INT_TBL_DAT_VAL_ABO};
               if( !objUti.verifyTbl(tblValPnd,intColObligadas)){
                   tabFrm.setSelectedIndex(0);                  
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                    objUti.verifyTbl(tblValPnd,intColObligadas);
                    return false;
               }
               
               /*
                * Validando que la suma de los abonos sean igual al monto del documento
                */               
               double dblTotAbo=0.00;
               for(int rowDet=0;rowDet<tblValPnd.getRowCount();rowDet++)
               {
                    dblTotAbo += Double.parseDouble((tblValPnd.getValueAt(rowDet,11)==null)?"0":tblValPnd.getValueAt(rowDet,11).toString());
               }
                                             
                if( objUti.redondeo(dblTotAbo,INT_NUM_DEC)!= objUti.redondeo(Double.parseDouble(txtMonDoc.getText()), INT_NUM_DEC)){
                      tabFrm.setSelectedIndex(1);
                      javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                            String strTit, strMsg;
                            strTit="Mensaje del sistema Zafiro";
                            strMsg="La suma del monto a pagar no es igual al de la suma de los abonos.\nCorrija y vuelva a intentarlo.";
                            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                    return false;
                }                              
         return true; 
          }
     
        public boolean vistaPreliminar() {
            return true;
        }        
        
        public boolean afterVistaPreliminar() {
            return true;
        }        
        
        public boolean imprimir() {
            return true;
        }        
        
        public boolean afterAceptar() {
            return true;
        }        
        
        public boolean afterInsertar() {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            objTooBar.setEstado('n');
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }
        
        public boolean afterModificar() {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean aceptar() {                        
            return true;
        }
        
        public boolean cancelar() {
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
     
    
      public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
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
   }      

/////////////////////////////////////////////////////////////////////////////
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            System.out.println("ZafAsiDia.tableChanged");
            System.out.println("Celda modificada: (" + tblValPnd.getEditingRow() + "," + tblValPnd.getEditingColumn() + ")");
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    //camSelChk();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    if (tblValPnd.getEditingColumn()==INT_TBL_DAT_SEL){
                        //System.out.println("para edicion del checkbox");
                        //camSelChk();                        
                        //no se hace nada xq la celda ya es editable
                    }
                    else
                        if(tblValPnd.getEditingColumn()==INT_TBL_DAT_VAL_ABO){
                            //camValApl();//para q se active la celda del checkbox                        
                            if (objHilo==null) {
                                objHilo = new tblHilo();
                                objHilo.start();
                                objHilo = null;
                            }
                    }
                    if (tblValPnd.getEditingColumn()==INT_TBL_DAT_MON_DOC){
                        
                    }
                    break;
            }
        }
    }        
     
    class tblHilo extends Thread{
            public void run(){
                //aboCel();
                System.out.println("antes de llamar a monDoc");
                //monDoc(varTmp);
                System.out.println("despues de llamar a monDoc");
            }
     }

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
    

    
    
    
     public String retObsRet(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxCodTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+="select tx_obs1 from tbm_cabtipret";
                que+=" where";
                que+=" co_emp=" + objParSis.getCodigoEmpresa() + "";
                que+=" and co_cta=" + txtCodTipDoc.getText() + "";
                System.out.println("estoy dentro de la funcion"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxCodTipDoc=rstTipDoc.getString("tx_obs1");
                    System.out.println("este es el codigo del document:" +auxCodTipDoc);
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
        return auxCodTipDoc;
        
    }    
   
    
     public double retPorRet(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que;        
        double auxCodTipDoc=0.00;
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+="select nd_porret from tbm_cabtipret";
                que+=" where";
                que+=" co_emp=" + objParSis.getCodigoEmpresa() + "";
                que+=" and co_cta=" + txtCodTipDoc.getText() + "";
                System.out.println("estoy dentro de la funcion"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxCodTipDoc=rstTipDoc.getDouble("nd_porret");
                    System.out.println("este es el codigo del document:" +auxCodTipDoc);
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
        return auxCodTipDoc;
        
    }    
   

    
    
}
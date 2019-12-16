/*
 * ZafCon59.java
 *
 * Created on 16 de enero de 2005, 17:10 PM
 */

package Contabilidad.ZafCon59;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
/**
 *
 * @author  Eddye Lino
 */
public class ZafCon59 extends javax.swing.JInternalFrame
{
    //FORMULARIO 104 
    //************************************************
    //Constantes: Columnas del JTable:
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_EMP=1;
    //VENTA VALOR BRUTO Y NETO
    private final int INT_TBL_DAT_VTA_CON_IVA_VAL_BRU=2;
    private final int INT_TBL_DAT_VTA_CON_IVA_VAL_NET=3;
    private final int INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU=4;
    private final int INT_TBL_DAT_VTA_SIN_IVA_VAL_NET=5;
    //IVA TOTAL DE VENTAS
    private final int INT_TBL_DAT_IVA=6;
    //VENTA CONTADO Y CREDITO
    private final int INT_TBL_DAT_VTA_CON=7;
    private final int INT_TBL_DAT_VTA_CRE=8;
    //IVA CONTADO Y CREDITO
    private final int INT_TBL_DAT_IVA_CON=9;
    private final int INT_TBL_DAT_IVA_CRE=10;
    //VALOR COMPENSACION SOLIDARIA VENTAS
    private final int INT_TBL_DAT_VAL_COM_SOL_VEN=11;
    //VALOR DE VENTAS A CREDITO DEL MES ANTERIOR
    private final int INT_TBL_DAT_VTA_CRE_MES_ANT=12;
    //COMPRAS CON IVA
    private final int INT_TBL_DAT_COM_CON_IVA_VAL=13;
    private final int INT_TBL_DAT_COM_IVA=14;
    private final int INT_TBL_DAT_COM_SIN_IVA_VAL=15;
    //VALOR COMPENSACION SOLIDARIA COMPRAS
    private final int INT_TBL_DAT_VAL_COM_SOL_COM=16;
    //IMPORTACION
    private final int INT_TBL_DAT_IMP_VAL=17;
    private final int INT_TBL_DAT_IMP_IVA=18;
    //VALOR A PAGAR ANTES DE APLICAR CREDITO FISCAL(SI EXISTE)
    private final int INT_TBL_DAT_VAL_PAG_BRU=19;
    //CREDITO FISCAL PENDIENTE DE APLICAR
    private final int INT_TBL_DAT_CRE_FIS_ANT=20;
    //CREDITO FISCAL QUE QUEDA POR APLICAR
    private final int INT_TBL_DAT_VAL_PAG_SIN_RTE=21;
    //VALOR A PAGAR SIN RETENCIONES
    private final int INT_TBL_DAT_CRE_FIS_ACT=22;
    //RETENCIONES
    private final int INT_TBL_DAT_RET_10P=23;
    private final int INT_TBL_DAT_RET_20P=24;
    private final int INT_TBL_DAT_RET_30P=25;
    private final int INT_TBL_DAT_RET_70P=26;
    private final int INT_TBL_DAT_RET_100P=27;
    //CREDITO TRIBUTARIO
    private final int INT_TBL_DAT_CRE_TRI_10P=28;
    private final int INT_TBL_DAT_CRE_TRI_20P=29;
    private final int INT_TBL_DAT_CRE_TRI_30P=30;
    private final int INT_TBL_DAT_CRE_TRI_50P=31;
    private final int INT_TBL_DAT_CRE_TRI_70P=32;
    private final int INT_TBL_DAT_CRE_TRI_100P=33;
    //VALOR A PAGAR NETO
    private final int INT_TBL_DAT_VAL_PAG_NET=34;
    //VALOR DE DEVOLUCIONES DE COMPRA DADAS DE BAJA CON CRUP --SOLO ES INFORMATIVO SEGUN LO COMENTADO POR JUAN RODAS Y SOLICITADO POR EL.
    private final int INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP=35;
    private final int INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP=36;
    
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    //private ZafPopupMenu objPopMnu;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con, conTot, conCab;
    private Statement stm, stmTot, stmCab;
    private ResultSet rst, rstTot, rstCab;
    private String strSQL, strAux;    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecTipCta, vecNatCta, vecEstReg;
    private boolean blnCon;                     //true: Continua la ejecucián del hilo.
    private String strMsg="";
    private String strTit;
    private JOptionPane oppMsg;
    private ZafTblCelRenLbl objTblCelRenLbl;    
    private ZafTblBus objTblBus;
   
    private ZafSelFec objSelFec;
    private ArrayList arlReg, arlDat;
    
    private final int intCodCtaIvaImpTuv=588;//es la misma cuenta para Tuval y para Cosenco por tanto no creo otra variable para Cosenco
    private final int intCodCtaIvaImpCas=98;
    private final int intCodCtaIvaImpDim=549;

    private final int intCodCtaCreFisTuv=587;//es la misma cuenta para Tuval y para Cosenco por tanto no creo otra variable para Cosenco
    private final int intCodCtaCreFisCas=97;
    private final int intCodCtaCreFisDim=548;
   
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;

    private java.util.Date datFecAux;

    private ArrayList arlReg103CodRetFte, arlDat103CodRetFte;
    private final int INT_ARL_103_COD_RTE_FTE=0;
    private final int INT_ARL_103_BAS_IMP=1;
    private final int INT_ARL_103_VAL_RTE=2;
    private final int INT_ARL_103_EST_REG=3;
    private String strRutFilGen;
    private BigDecimal bdeBasImpIvaVta12, bdeValIvaVta12, bdeBasImpIvaVta14, bdeValIvaVta14, bdeBasImpIvaCom12, bdeValIvaCom12, bdeBasImpIvaCom14;
    private BigDecimal bdeValIvaCom14, bdeSalCreTri, bdeBasImpIvaVtaCon12, bdeValIvaVtaCon12, bdeBasImpIvaVtaCre12, bdeValIvaVtaCre12;
    private BigDecimal bdeBasImpIvaVtaCon14, bdeValIvaVtaCon14, bdeBasImpIvaVtaCre14, bdeValIvaVtaCre14;
    //************************************************
    //FORMULARIO 103
    //************************************************
    //Constantes: Columnas del JTable:
    private final int INT_TBL_DAT_LIN_103=0;
    private final int INT_TBL_DAT_COD_EMP_103=1;
    //RETENCIONES
    private final int INT_TBL_DAT_RET_1P_103=2;
    private final int INT_TBL_DAT_RET_2P_103=3;
    private final int INT_TBL_DAT_RET_8P_103=4;
    private final int INT_TBL_DAT_RET_10P_103=5;
    //************************************************
    private String strVersion="v0.2.29";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCon59(ZafParSis obj)
    {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti=new ZafUtil();
            arlDat103CodRetFte=new ArrayList();
            System.out.println(objParSis.getCodigoMenu());
            if (objParSis.getCodigoMenu()==2795) { //Formulario 104
                if (!configurarFrm104()){
                exitForm();
                }
            }
            if (objParSis.getCodigoMenu()==2789) {//Formulario 103
                if (!configurarFrm103()){
                exitForm();
                }
            }
      }
      catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        panCorRpt = new javax.swing.JPanel();
        panConFor = new javax.swing.JPanel();
        panFor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumSus = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPagImpAnt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPagInt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPagMul = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIntMorSus = new javax.swing.JTextField();
        txtMulSus = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNumNotCre1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumNotCre2 = new javax.swing.JTextField();
        txtValNotCre1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtValNotCre2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboForPag = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        chkForAnt = new javax.swing.JCheckBox();
        panDatLeg = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtRucRepLeg = new javax.swing.JTextField();
        txtNomRepLeg = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtRucConGrl = new javax.swing.JTextField();
        txtNomConGrl = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDatFor = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGenXml = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 12));
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(576, 96));
        panFil.setLayout(new java.awt.BorderLayout());

        panCab.setLayout(new java.awt.BorderLayout());

        panCorRpt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCorRpt.setPreferredSize(new java.awt.Dimension(560, 72));
        panCorRpt.setLayout(new java.awt.BorderLayout());
        panCab.add(panCorRpt, java.awt.BorderLayout.NORTH);
        panCorRpt.getAccessibleContext().setAccessibleName("Codigo");

        panFil.add(panCab, java.awt.BorderLayout.NORTH);

        panConFor.setLayout(new java.awt.BorderLayout());

        panFor.setBorder(javax.swing.BorderFactory.createTitledBorder("Formulario"));
        panFor.setPreferredSize(new java.awt.Dimension(0, 0));
        panFor.setLayout(null);

        jLabel1.setText("Número de Sustitutiva:");
        panFor.add(jLabel1);
        jLabel1.setBounds(16, 20, 132, 14);
        panFor.add(txtNumSus);
        txtNumSus.setBounds(180, 18, 130, 20);

        jLabel3.setText("Pago de impuesto anterior:");
        panFor.add(jLabel3);
        jLabel3.setBounds(30, 62, 150, 14);
        panFor.add(txtPagImpAnt);
        txtPagImpAnt.setBounds(180, 60, 130, 20);

        jLabel4.setText("Pago por interés:");
        panFor.add(jLabel4);
        jLabel4.setBounds(30, 81, 140, 14);
        panFor.add(txtPagInt);
        txtPagInt.setBounds(180, 80, 130, 20);

        jLabel5.setText("Pago por multa:");
        panFor.add(jLabel5);
        jLabel5.setBounds(30, 101, 140, 14);
        panFor.add(txtPagMul);
        txtPagMul.setBounds(180, 100, 130, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Pago previo(informativo):");
        panFor.add(jLabel6);
        jLabel6.setBounds(16, 42, 170, 14);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Pago declaraciones sustitutivas:");
        panFor.add(jLabel7);
        jLabel7.setBounds(16, 126, 210, 14);

        jLabel8.setText("Interés por mora:");
        panFor.add(jLabel8);
        jLabel8.setBounds(30, 146, 140, 14);
        panFor.add(txtIntMorSus);
        txtIntMorSus.setBounds(180, 144, 130, 20);
        panFor.add(txtMulSus);
        txtMulSus.setBounds(180, 164, 130, 20);

        jLabel9.setText("Multas:");
        panFor.add(jLabel9);
        jLabel9.setBounds(30, 166, 140, 14);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Tipo de impresión");
        panFor.add(jLabel10);
        jLabel10.setBounds(340, 130, 154, 14);

        jLabel11.setText("Número:");
        panFor.add(jLabel11);
        jLabel11.setBounds(354, 44, 60, 14);
        panFor.add(txtNumNotCre1);
        txtNumNotCre1.setBounds(410, 40, 90, 20);

        jLabel12.setText("Número:");
        panFor.add(jLabel12);
        jLabel12.setBounds(354, 62, 50, 14);
        panFor.add(txtNumNotCre2);
        txtNumNotCre2.setBounds(410, 60, 90, 20);
        panFor.add(txtValNotCre1);
        txtValNotCre1.setBounds(560, 40, 120, 20);

        jLabel13.setText("Valor:");
        panFor.add(jLabel13);
        jLabel13.setBounds(512, 44, 48, 14);

        jLabel14.setText("Valor:");
        panFor.add(jLabel14);
        jLabel14.setBounds(512, 62, 48, 14);
        panFor.add(txtValNotCre2);
        txtValNotCre2.setBounds(560, 60, 120, 20);

        jLabel15.setText("Forma de pago:");
        panFor.add(jLabel15);
        jLabel15.setBounds(340, 94, 120, 14);

        cboForPag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Banco Machala", "Banco Pacifico" }));
        panFor.add(cboForPag);
        cboForPag.setBounds(450, 92, 170, 18);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Notas crédito a favor:");
        panFor.add(jLabel18);
        jLabel18.setBounds(340, 20, 154, 14);

        chkForAnt.setText("Formato Antigüo");
        panFor.add(chkForAnt);
        chkForAnt.setBounds(360, 150, 180, 23);

        panConFor.add(panFor, java.awt.BorderLayout.CENTER);

        panFil.add(panConFor, java.awt.BorderLayout.CENTER);

        panDatLeg.setPreferredSize(new java.awt.Dimension(100, 44));
        panDatLeg.setRequestFocusEnabled(false);
        panDatLeg.setLayout(null);

        jLabel16.setText("Representante legal:");
        panDatLeg.add(jLabel16);
        jLabel16.setBounds(10, 4, 130, 14);
        panDatLeg.add(txtRucRepLeg);
        txtRucRepLeg.setBounds(140, 2, 170, 20);
        panDatLeg.add(txtNomRepLeg);
        txtNomRepLeg.setBounds(311, 2, 340, 20);

        jLabel17.setText("Ruc del contador:");
        panDatLeg.add(jLabel17);
        jLabel17.setBounds(10, 22, 130, 14);
        panDatLeg.add(txtRucConGrl);
        txtRucConGrl.setBounds(140, 22, 170, 20);
        panDatLeg.add(txtNomConGrl);
        txtNomConGrl.setBounds(311, 22, 340, 20);

        panFil.add(panDatLeg, java.awt.BorderLayout.PAGE_END);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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
        spnDatFor.setViewportView(tblDat);

        panRpt.add(spnDatFor, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 46));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(200, 100));
        panBot.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(296, 26));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel1.add(butCon);

        butGenXml.setText("General Xml");
        butGenXml.setPreferredSize(new java.awt.Dimension(110, 25));
        butGenXml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGenXmlActionPerformed(evt);
            }
        });
        jPanel1.add(butGenXml);

        butImp.setText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        jPanel1.add(butImp);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel1.add(butCer);

        panBot.add(jPanel1, java.awt.BorderLayout.SOUTH);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 18));
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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
                      
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar accián de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();  
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed
    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butGenXmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGenXmlActionPerformed
        // TODO add your handling code here:
        if(objParSis.getCodigoMenu()==2789){//103
            if(cargarFormulario103()){
                mostrarMsgInf("<HTML>El formulario <FONT COLOR=\"blue\">103</FONT> se generó correctamente.</HTML>");}
            else{
                mostrarMsgInf("<HTML>Ocurrió un error al generar o guardar el formulario <FONT COLOR=\"blue\">103</FONT>.<BR>.Verifique y vuelva a intentarlo.</HTML>");}
        }
        else{//104
            //if(cargarFormulario104())
            if(cargarFormulario104_2()){
                mostrarMsgInf("<HTML>El formulario <FONT COLOR=\"blue\">104</FONT> se generó correctamente.</HTML>");}
            else{
                mostrarMsgInf("<HTML>Ocurrió un error al generar o guardar el formulario <FONT COLOR=\"blue\">104</FONT>.<BR>.Verifique y vuelva a intentarlo.</HTML>");}
        }

    }//GEN-LAST:event_butGenXmlActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:
        if (objThrGUIVisPre==null)
        {
            objThrGUIVisPre=new ZafThreadGUIVisPre();
            objThrGUIVisPre.setIndFunEje(1);
            objThrGUIVisPre.start();
        }
    }//GEN-LAST:event_butImpActionPerformed
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podrá utilizar
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
    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGenXml;
    private javax.swing.JButton butImp;
    private javax.swing.JComboBox cboForPag;
    private javax.swing.JCheckBox chkForAnt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panConFor;
    private javax.swing.JPanel panCorRpt;
    private javax.swing.JPanel panDatLeg;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFor;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDatFor;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtIntMorSus;
    private javax.swing.JTextField txtMulSus;
    private javax.swing.JTextField txtNomConGrl;
    private javax.swing.JTextField txtNomRepLeg;
    private javax.swing.JTextField txtNumNotCre1;
    private javax.swing.JTextField txtNumNotCre2;
    private javax.swing.JTextField txtNumSus;
    private javax.swing.JTextField txtPagImpAnt;
    private javax.swing.JTextField txtPagInt;
    private javax.swing.JTextField txtPagMul;
    private javax.swing.JTextField txtRucConGrl;
    private javax.swing.JTextField txtRucRepLeg;
    private javax.swing.JTextField txtValNotCre1;
    private javax.swing.JTextField txtValNotCre2;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm104(){
        boolean blnRes=true;
        try{
            strAux=objParSis.getNombreMenu() + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panCorRpt.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(27);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");            
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");
            vecCab.add(INT_TBL_DAT_VTA_CON_IVA_VAL_BRU,"Val.Bru.");
            vecCab.add(INT_TBL_DAT_VTA_CON_IVA_VAL_NET,"Val.Net.");
            vecCab.add(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU,"Val.Bru.");
            vecCab.add(INT_TBL_DAT_VTA_SIN_IVA_VAL_NET,"Val.Net.");
            vecCab.add(INT_TBL_DAT_IVA,"Iva");
            vecCab.add(INT_TBL_DAT_VTA_CON,"Con.");
            vecCab.add(INT_TBL_DAT_VTA_CRE, "Cre.");
            vecCab.add(INT_TBL_DAT_IVA_CON,"Con.");
            vecCab.add(INT_TBL_DAT_IVA_CRE,"Cre.");
            vecCab.add(INT_TBL_DAT_VAL_COM_SOL_VEN,"Val.Com.Sol.Ven.");
            vecCab.add(INT_TBL_DAT_VTA_CRE_MES_ANT,"Vta.Cre.Mes.Ant.");
            vecCab.add(INT_TBL_DAT_COM_CON_IVA_VAL,"Val.Con.Iva.");
            vecCab.add(INT_TBL_DAT_COM_IVA,"Iva.");
            vecCab.add(INT_TBL_DAT_COM_SIN_IVA_VAL,"Val.Sin.Iva.");
            vecCab.add(INT_TBL_DAT_VAL_COM_SOL_COM,"Val.Com.Sol.Com.");
            vecCab.add(INT_TBL_DAT_IMP_VAL,"Val.");
            vecCab.add(INT_TBL_DAT_IMP_IVA,"Iva.");
            vecCab.add(INT_TBL_DAT_VAL_PAG_BRU,"Val.Pag.Bru.");
            vecCab.add(INT_TBL_DAT_CRE_FIS_ANT,"Cre.Fis.Ant.");
            vecCab.add(INT_TBL_DAT_VAL_PAG_SIN_RTE,"Val.Pag.Sin.Rte.");
            vecCab.add(INT_TBL_DAT_CRE_FIS_ACT,"Cre.Fis.Act.");
            vecCab.add(INT_TBL_DAT_RET_10P,"Ret.10%");
            vecCab.add(INT_TBL_DAT_RET_20P,"Ret.20%.");
            vecCab.add(INT_TBL_DAT_RET_30P,"Ret.30%");
            vecCab.add(INT_TBL_DAT_RET_70P,"Ret.70%.");
            vecCab.add(INT_TBL_DAT_RET_100P,"Ret.100%");
            vecCab.add(INT_TBL_DAT_CRE_TRI_10P,"Cre.Tri.10%");
            vecCab.add(INT_TBL_DAT_CRE_TRI_20P,"Cre.Tri.20%.");
            vecCab.add(INT_TBL_DAT_CRE_TRI_30P,"Cre.Tri.30%");
            vecCab.add(INT_TBL_DAT_CRE_TRI_50P,"Cre.Tri.50%");
            vecCab.add(INT_TBL_DAT_CRE_TRI_70P,"Cre.Tri.70%.");
            vecCab.add(INT_TBL_DAT_CRE_TRI_100P,"Cre.Tri.100%");
            vecCab.add(INT_TBL_DAT_VAL_PAG_NET,"Val.Pag.Net.");
            vecCab.add(INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP,"Bas.Imp.Dev.Com.Cru.");
            vecCab.add(INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP,"Val.Iva.Dev.Com.Cru.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
               
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VTA_CON_IVA_VAL_NET).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VTA_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VTA_CRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_IVA_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_IVA_CRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_COM_SOL_VEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_VTA_CRE_MES_ANT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COM_CON_IVA_VAL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COM_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COM_SIN_IVA_VAL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_COM_SOL_COM).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_IMP_VAL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_IMP_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_BRU).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CRE_FIS_ANT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_SIN_RTE).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CRE_FIS_ACT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_RET_10P).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_RET_20P).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_RET_30P).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_RET_70P).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_RET_100P).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_10P).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_20P).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_30P).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_50P).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_70P).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_100P).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_NET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).setPreferredWidth(70);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VTA_CON_IVA_VAL_NET).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_IVA).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VTA_CON).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VTA_CRE).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_IVA_CON).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_IVA_CRE).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_COM_SOL_VEN).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VTA_CRE_MES_ANT).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COM_CON_IVA_VAL).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COM_IVA).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COM_SIN_IVA_VAL).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_COM_SOL_COM).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_IMP_VAL).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_IMP_IVA).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_PAG_BRU).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_FIS_ANT).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_PAG_SIN_RTE).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_FIS_ACT).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_10P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_20P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_30P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_70P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_100P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_TRI_10P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_TRI_20P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_TRI_30P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_TRI_50P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_TRI_70P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CRE_TRI_100P).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_PAG_NET).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).setCellRenderer(objTblCelRenLbl);

            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);
            
            arlDat=new ArrayList();

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);


            //ventas
            ZafTblHeaGrp objTblHeaGrpSol=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpSol.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpSol=null;
            objTblHeaColGrpSol=new ZafTblHeaColGrp("Ventas");
            objTblHeaColGrpSol.setHeight(16);
            objTblHeaGrpSol.addColumnGroup(objTblHeaColGrpSol);

            ZafTblHeaColGrp objTblHeaColGrpConIva=null;
            objTblHeaColGrpConIva=new ZafTblHeaColGrp("Con IVA");
            objTblHeaColGrpConIva.setHeight(16);
            objTblHeaColGrpSol.add(objTblHeaColGrpConIva);

            objTblHeaColGrpConIva.add(tcmAux.getColumn(INT_TBL_DAT_VTA_CON_IVA_VAL_BRU));
            objTblHeaColGrpConIva.add(tcmAux.getColumn(INT_TBL_DAT_VTA_CON_IVA_VAL_NET));

            ZafTblHeaColGrp objTblHeaColGrpSinIva=null;
            objTblHeaColGrpSinIva=new ZafTblHeaColGrp("Sin IVA");
            objTblHeaColGrpSinIva.setHeight(16);
            objTblHeaColGrpSol.add(objTblHeaColGrpSinIva);

            objTblHeaColGrpSinIva.add(tcmAux.getColumn(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU));
            objTblHeaColGrpSinIva.add(tcmAux.getColumn(INT_TBL_DAT_VTA_SIN_IVA_VAL_NET));

            //ventas de contado y credito
            ZafTblHeaGrp objTblHeaGrpVtaConCre=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpVtaConCre.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpVtaConCre=null;
            objTblHeaColGrpVtaConCre=new ZafTblHeaColGrp("Ventas");
            objTblHeaColGrpVtaConCre.setHeight(16);
            objTblHeaGrpVtaConCre.addColumnGroup(objTblHeaColGrpVtaConCre);

            objTblHeaColGrpVtaConCre.add(tcmAux.getColumn(INT_TBL_DAT_VTA_CON));
            objTblHeaColGrpVtaConCre.add(tcmAux.getColumn(INT_TBL_DAT_VTA_CRE));

            //iva de contado y credito
            ZafTblHeaGrp objTblHeaGrpIvaConCre=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpIvaConCre.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpIvaConCre=null;
            objTblHeaColGrpIvaConCre=new ZafTblHeaColGrp("IVA");
            objTblHeaColGrpIvaConCre.setHeight(16);
            objTblHeaGrpIvaConCre.addColumnGroup(objTblHeaColGrpIvaConCre);

            objTblHeaColGrpIvaConCre.add(tcmAux.getColumn(INT_TBL_DAT_IVA_CON));
            objTblHeaColGrpIvaConCre.add(tcmAux.getColumn(INT_TBL_DAT_IVA_CRE));

            //compras de contado y credito
            ZafTblHeaGrp objTblHeaGrpComConCre=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpComConCre.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpComConCre=null;
            objTblHeaColGrpComConCre=new ZafTblHeaColGrp("Compras");
            objTblHeaColGrpComConCre.setHeight(16);
            objTblHeaGrpComConCre.addColumnGroup(objTblHeaColGrpComConCre);

            objTblHeaColGrpComConCre.add(tcmAux.getColumn(INT_TBL_DAT_COM_CON_IVA_VAL));
            objTblHeaColGrpComConCre.add(tcmAux.getColumn(INT_TBL_DAT_COM_IVA));
            objTblHeaColGrpComConCre.add(tcmAux.getColumn(INT_TBL_DAT_COM_SIN_IVA_VAL));

            //importacion de contado y credito
            ZafTblHeaGrp objTblHeaGrpImpConCre=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpImpConCre.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpImpConCre=null;
            objTblHeaColGrpImpConCre=new ZafTblHeaColGrp("Importación");
            objTblHeaColGrpImpConCre.setHeight(16);
            objTblHeaGrpImpConCre.addColumnGroup(objTblHeaColGrpImpConCre);

            objTblHeaColGrpImpConCre.add(tcmAux.getColumn(INT_TBL_DAT_IMP_VAL));
            objTblHeaColGrpImpConCre.add(tcmAux.getColumn(INT_TBL_DAT_IMP_IVA));
            
            //retenciones
            ZafTblHeaGrp objTblHeaGrpRet=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpRet.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpRet=null;
            objTblHeaColGrpRet=new ZafTblHeaColGrp("Retenciones");
            objTblHeaColGrpRet.setHeight(16);
            objTblHeaGrpRet.addColumnGroup(objTblHeaColGrpRet);

            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_10P));
            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_20P));
            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_30P));
            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_70P));
            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_100P));
            
            //Credito tributario
            ZafTblHeaGrp objTblHeaGrpCreTri=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpCreTri.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpCreTri=null;
            objTblHeaColGrpCreTri=new ZafTblHeaColGrp("Crédito tributario");
            objTblHeaColGrpCreTri.setHeight(16);
            objTblHeaGrpCreTri.addColumnGroup(objTblHeaColGrpCreTri);

            objTblHeaColGrpCreTri.add(tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_10P));
            objTblHeaColGrpCreTri.add(tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_20P));
            objTblHeaColGrpCreTri.add(tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_30P));
            objTblHeaColGrpCreTri.add(tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_50P));
            objTblHeaColGrpCreTri.add(tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_70P));
            objTblHeaColGrpCreTri.add(tcmAux.getColumn(INT_TBL_DAT_CRE_TRI_100P));

            tcmAux=null;

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            txtRucRepLeg.setEnabled(false);
            txtNomRepLeg.setEnabled(false);
            txtRucConGrl.setEnabled(false);
            txtNomConGrl.setEnabled(false);

            if(objParSis.getCodigoMenu()==2789){//103
                panFor.setBorder(javax.swing.BorderFactory.createTitledBorder("Formulario 103"));
            }
            else if(objParSis.getCodigoMenu()==2795){//104
                panFor.setBorder(javax.swing.BorderFactory.createTitledBorder("Formulario 104"));
            }


            cargarDatosLegalesEmpresa();

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
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
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }

    private boolean cargarDetReg104()
    {
        String strNiv;
        int intCodEmp, intNumTotReg, i, intCodCtaIvaTra;
        boolean blnRes=true;
        double dblNumVta=0.00;
        double dblValNet=0.00;
        String strAuxFecDia="";
        int intCodCtaIvaImp=-1;
        int intCodCtaCreFis=-1;
        String strFecFac="";
        //String strFecDevCom="";
        String strNomEmp="";
        String strFecCom="",strAux2;
        BigDecimal bdePorIva;
        
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            if (con!=null){
               intCodCtaIvaTra = 0;
               
                switch(intCodEmp){
                    case 1:
                        intCodCtaIvaImp=intCodCtaIvaImpTuv;
                        intCodCtaCreFis=intCodCtaCreFisTuv;
                        intCodCtaIvaTra = 2949;
                        break;
                    case 2:
                        intCodCtaIvaImp=intCodCtaIvaImpCas;
                        intCodCtaCreFis=intCodCtaCreFisCas;
                        intCodCtaIvaTra = 1250;
                        break;
                    case 4:
                        intCodCtaIvaImp=intCodCtaIvaImpDim;
                        intCodCtaCreFis=intCodCtaCreFisDim;
                        intCodCtaIvaTra = 2226;
                        break;
                }

                strAux="";
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Básqueda por rangos
                        strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strAuxFecDia+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strFecCom=" AND a4.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strFecFac+="    AND a2.fe_venChq BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        //strFecDevCom+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strAuxFecDia+=" AND a1.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strFecCom=" AND a4.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strFecFac+=" AND a2.fe_venChq<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        //strFecDevCom+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strAuxFecDia+=" AND a1.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strFecCom=" AND a4.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strFecFac+=" AND a2.fe_venChq>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        //strFecDevCom+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                
                 int intAniFecDes = obtenerAni(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                int intMesFecDes = obtenerMes(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                int intAniFecHas = obtenerAni(objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                int intMesFecHas = obtenerMes(objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                if (verificaExistePer(intAniFecDes,intMesFecDes,intAniFecHas,intMesFecHas)) {
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT co_emp, ne_ani, ne_mes, nd_valbruconiva, nd_valnetconiva, nd_valbrusiniva, \n";
                    strSQL+=        "       nd_valnetsiniva, nd_valivaven, nd_valvencon, nd_valvencre, nd_valivavencon, \n";
                    strSQL+=        "       nd_valivavencre, nd_valvencremesant, nd_valcomconiva, nd_valivacom, \n";
                    strSQL+=        "       nd_valcomsiniva, nd_valimp, nd_valivaimp, nd_valpagbru, nd_valcrefisant, \n";
                    strSQL+=        "       nd_valpagsinret, nd_valcrefisact, nd_valret10, nd_valret20, nd_valret30, \n";
                    strSQL+=        "       nd_valret70, nd_valret100, nd_valcretri10, nd_valcretri20, nd_valcretri30, \n";
                    strSQL+=        "       nd_valcretri70, nd_valcretri100, nd_valpagnet, nd_valbasimpdevcom, \n";
                    strSQL+=        "       nd_valivadevcom, tx_obs1, tx_obs2, fe_ing, co_usring, nd_valcomsol, nd_valComSolCom\n";
                    strSQL+=        "  FROM tbm_cabdecmensrifor104\n";
                    strSQL+=        "  where co_emp="+objParSis.getCodigoEmpresa();
                    strSQL+=        "  and ne_ani="+intAniFecHas;
                    strSQL+=        "  and ne_mes="+intMesFecHas;
                    //System.out.println("SQL cargarDet:" +strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;                                
                if (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,                 "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,             "");// + objParSis.getCodigoEmpresa());
                        vecReg.add(INT_TBL_DAT_VTA_CON_IVA_VAL_BRU, ""); //+ new BigDecimal(rst.getObject("nd_valbruconiva")==null?"0":(rst.getString("nd_valbruconiva").equals("")?"0":rst.getString("nd_valbruconiva"))).abs());
                        vecReg.add(INT_TBL_DAT_VTA_CON_IVA_VAL_NET, "");// + new BigDecimal(rst.getObject("nd_valnetconiva")==null?"0":(rst.getString("nd_valnetconiva").equals("")?"0":rst.getString("nd_valnetconiva"))).abs());
                        vecReg.add(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU, "");// + new BigDecimal(rst.getObject("nd_valbrusiniva")==null?"0":(rst.getString("nd_valbrusiniva").equals("")?"0":rst.getString("nd_valbrusiniva"))).abs());
                        vecReg.add(INT_TBL_DAT_VTA_SIN_IVA_VAL_NET, "");// + new BigDecimal(rst.getObject("nd_valnetsiniva")==null?"0":(rst.getString("nd_valnetsiniva").equals("")?"0":rst.getString("nd_valnetsiniva"))).abs());
                        vecReg.add(INT_TBL_DAT_IVA,                 "");// + new BigDecimal(rst.getObject("nd_valivaven")==null?"0":(rst.getString("nd_valivaven").equals("")?"0":rst.getString("nd_valivaven"))).abs());
                        vecReg.add(INT_TBL_DAT_VTA_CON,             "");// + new BigDecimal(rst.getObject("nd_valvencon")==null?"0":(rst.getString("nd_valvencon").equals("")?"0":rst.getString("nd_valvencon"))).abs());
                        vecReg.add(INT_TBL_DAT_VTA_CRE,             "");// + new BigDecimal(rst.getObject("nd_valvencre")==null?"0":(rst.getString("nd_valvencre").equals("")?"0":rst.getString("nd_valvencre"))).abs());
                        vecReg.add(INT_TBL_DAT_IVA_CON,             "");// + new BigDecimal(rst.getObject("nd_valivavencon")==null?"0":(rst.getString("nd_valivavencon").equals("")?"0":rst.getString("nd_valivavencon"))).abs());
                        vecReg.add(INT_TBL_DAT_IVA_CRE,             "");// + new BigDecimal(rst.getObject("nd_valivavencre")==null?"0":(rst.getString("nd_valivavencre").equals("")?"0":rst.getString("nd_valivavencre"))).abs());
                        vecReg.add(INT_TBL_DAT_VAL_COM_SOL_VEN,     "" +  new BigDecimal(rst.getObject("nd_valcomsol")==null?"0":(rst.getString("nd_valcomsol").equals("")?"0":rst.getString("nd_valcomsol"))).abs());
                        vecReg.add(INT_TBL_DAT_VTA_CRE_MES_ANT,     "");// + new BigDecimal(rst.getObject("nd_valvencremesant")==null?"0":(rst.getString("nd_valvencremesant").equals("")?"0":rst.getString("nd_valvencremesant"))).abs());
                        vecReg.add(INT_TBL_DAT_COM_CON_IVA_VAL,     "" + new BigDecimal(rst.getObject("nd_valcomconiva")==null?"0":(rst.getString("nd_valcomconiva").equals("")?"0":rst.getString("nd_valcomconiva"))).abs());
                        vecReg.add(INT_TBL_DAT_COM_IVA,             "" + new BigDecimal(rst.getObject("nd_valivacom")==null?"0":(rst.getString("nd_valivacom").equals("")?"0":rst.getString("nd_valivacom"))).abs());
                        vecReg.add(INT_TBL_DAT_COM_SIN_IVA_VAL,     "" + new BigDecimal(rst.getObject("nd_valcomsiniva")==null?"0":(rst.getString("nd_valcomsiniva").equals("")?"0":rst.getString("nd_valcomsiniva"))).abs());
                        vecReg.add(INT_TBL_DAT_VAL_COM_SOL_COM,     "" + new BigDecimal(rst.getObject("nd_valComSolCom")==null?"0":(rst.getString("nd_valComSolCom").equals("")?"0":rst.getString("nd_valComSolCom"))).abs());
                        vecReg.add(INT_TBL_DAT_IMP_VAL,             "");// + new BigDecimal(rst.getObject("nd_valimp")==null?"0":(rst.getString("nd_valimp").equals("")?"0":rst.getString("nd_valimp"))).abs());
                        vecReg.add(INT_TBL_DAT_IMP_IVA,             "");// + new BigDecimal(rst.getObject("nd_valivaimp")==null?"0":(rst.getString("nd_valivaimp").equals("")?"0":rst.getString("nd_valivaimp"))).abs());                        
                        vecReg.add(INT_TBL_DAT_VAL_PAG_BRU,         "");// + new BigDecimal(rst.getObject("nd_valpagbru")==null?"0":(rst.getString("nd_valpagbru").equals("")?"0":rst.getString("nd_valpagbru")))/*.abs()*/);
                        vecReg.add(INT_TBL_DAT_CRE_FIS_ANT,         "");// + new BigDecimal(rst.getObject("nd_valcrefisant")==null?"0":(rst.getString("nd_valcrefisant").equals("")?"0":rst.getString("nd_valcrefisant"))).abs());
                        vecReg.add(INT_TBL_DAT_VAL_PAG_SIN_RTE,     "");// + new BigDecimal(rst.getObject("nd_valpagsinret")==null?"0":(rst.getString("nd_valpagsinret").equals("")?"0":rst.getString("nd_valpagsinret"))).abs());
                        vecReg.add(INT_TBL_DAT_CRE_FIS_ACT,         "");// + new BigDecimal(rst.getObject("nd_valcrefisact")==null?"0":(rst.getString("nd_valcrefisact").equals("")?"0":rst.getString("nd_valcrefisact"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_10P,             "");// + new BigDecimal(rst.getObject("nd_valret10")==null?"0":(rst.getString("nd_valret10").equals("")?"0":rst.getString("nd_valret10"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_20P,             "");// + new BigDecimal(rst.getObject("nd_valret20")==null?"0":(rst.getString("nd_valret20").equals("")?"0":rst.getString("nd_valret20"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_30P,             "");// + new BigDecimal(rst.getObject("nd_valret30")==null?"0":(rst.getString("nd_valret30").equals("")?"0":rst.getString("nd_valret30"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_70P,             "");// + new BigDecimal(rst.getObject("nd_valret70")==null?"0":(rst.getString("nd_valret70").equals("")?"0":rst.getString("nd_valret70"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_100P,            "");// + new BigDecimal(rst.getObject("nd_valret100")==null?"0":(rst.getString("nd_valret100").equals("")?"0":rst.getString("nd_valret100"))).abs());
                        vecReg.add(INT_TBL_DAT_CRE_TRI_10P,         "");// + new BigDecimal(rst.getObject("nd_valcretri10")==null?"0":(rst.getString("nd_valcretri10").equals("")?"0":rst.getString("nd_valcretri10"))).abs());
                        vecReg.add(INT_TBL_DAT_CRE_TRI_20P,         "");// + new BigDecimal(rst.getObject("nd_valcretri20")==null?"0":(rst.getString("nd_valcretri20").equals("")?"0":rst.getString("nd_valcretri20"))).abs());
                        vecReg.add(INT_TBL_DAT_CRE_TRI_30P,         "");// + new BigDecimal(rst.getObject("nd_valcretri30")==null?"0":(rst.getString("nd_valcretri30").equals("")?"0":rst.getString("nd_valcretri30"))).abs());
                        vecReg.add(INT_TBL_DAT_CRE_TRI_50P,         "");// + new BigDecimal(rst.getObject("nd_valcretri30")==null?"0":(rst.getString("nd_valcretri30").equals("")?"0":rst.getString("nd_valcretri30"))).abs());
                        vecReg.add(INT_TBL_DAT_CRE_TRI_70P,         "");// + new BigDecimal(rst.getObject("nd_valcretri70")==null?"0":(rst.getString("nd_valcretri70").equals("")?"0":rst.getString("nd_valcretri70"))).abs());
                        vecReg.add(INT_TBL_DAT_CRE_TRI_100P,        "");// + new BigDecimal(rst.getObject("nd_valcretri100")==null?"0":(rst.getString("nd_valcretri100").equals("")?"0":rst.getString("nd_valcretri100"))).abs());
                        vecReg.add(INT_TBL_DAT_VAL_PAG_NET,         "");// + new BigDecimal(rst.getObject("nd_valpagnet")==null?"0":(rst.getString("nd_valpagnet").equals("")?"0":rst.getString("nd_valpagnet"))).abs());
                        vecReg.add(INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP,"");// + new BigDecimal(rst.getObject("nd_valbasimpdevcom")==null?"0":(rst.getString("nd_valbasimpdevcom").equals("")?"0":rst.getString("nd_valbasimpdevcom"))).abs());
                        vecReg.add(INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP,"");// + new BigDecimal(rst.getObject("nd_valivadevcom")==null?"0":(rst.getString("nd_valivadevcom").equals("")?"0":rst.getString("nd_valivadevcom"))).abs());
                        
                 vecDat.add(vecReg);
                i++;
                pgrSis.setValue(i);

                //Asignar vectores al modelo.                                                
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                }
                }
                //else{
                stm=con.createStatement();
                strSQL="";
                //strSQL+="SELECT SUM(nd_valBruDevSinIva) AS nd_valBruDevSinIva, SUM(nd_valBruDevConIva) AS nd_valBruDevConIva FROM(";
                //strSQL+="	SELECT   CASE WHEN (a1.nd_valIva=0 OR a1.nd_valIva IS NULL) THEN (a1.nd_sub) END AS nd_valBruDevSinIva";
                //strSQL+=" 	       , CASE WHEN a1.nd_valIva<>0 THEN (a1.nd_sub) END AS nd_valBruDevConIva";
                
                strSQL+="SELECT (SUM(case when nd_valBruDevSinIva_DetSoloSinIva is not null then nd_valBruDevSinIva_DetSoloSinIva else 0 end) + SUM(case when nd_valBruDevSinIva_DetConSinIva is not null then nd_valBruDevSinIva_DetConSinIva else 0 end)) as nd_valBruDevSinIva, ";
                strSQL+=" (SUM(case when nd_valcomsol is not null then nd_valcomsol else 0 end))*0.02 AS nd_valcomsol, \n";
                strSQL+="(SUM(case when nd_valBruDevConIva_DetSoloConIva is not null then nd_valBruDevConIva_DetSoloConIva else 0 end) + SUM(case when nd_valBruDevConIva_DetConSinIva is not null then nd_valBruDevConIva_DetConSinIva else 0 end)) AS nd_valBruDevConIva FROM( ";
                //Subquery para traer Valor Bruto de IVA <> 0% donde en tbm_detMovInv solo hallan registros que tengan items con IVA
  	        strSQL+="SELECT ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp ";
	        strSQL+="         from tbm_cabMovInv ";
	        strSQL+="         where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="            and ( select count(a.*) ";
		strSQL+="                  from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	                ) as a ";
		strSQL+="                ) = 1 ";
		strSQL+="            and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="                ) = 'S' ";
	        strSQL+="       ) AS nd_valBruDevConIva_DetSoloConIva, ";

  	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv solo hallan registros que no tengan items con IVA
	        strSQL+="       ( select CASE WHEN a1.nd_valIva = 0 THEN a1.nd_sub ELSE 0 END AS nd_basCer ";
  	        strSQL+="         from tbm_cabMovInv ";
	        strSQL+="         where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="            and ( select count(a.*) ";
		strSQL+="                  from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	                ) as a ";
		strSQL+="                ) = 1 ";
		strSQL+="            and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="                ) = 'N' ";
	        strSQL+="       ) AS nd_valBruDevSinIva_DetSoloSinIva, ";

	        //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="       ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp ";
	        strSQL+="         from tbm_detMovInv ";
	        strSQL+="         where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="            and ( select count(a.*) ";
		strSQL+="                  from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	                ) as a ";
		strSQL+="                ) > 1 ";
	        strSQL+="       ) AS nd_valBruDevConIva_DetConSinIva, ";

	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="       ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basCer ";
	        strSQL+="         from tbm_detMovInv ";
	        strSQL+="         where st_ivacom = 'N' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="            and ( select count(a.*) ";
		strSQL+="                  from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	                ) as a ";
		strSQL+="                ) > 1 ";
	        strSQL+="       ) AS nd_valBruDevSinIva_DetConSinIva, ";
                //Tony Subquery Valor Compensacion Solidaria
                strSQL+=" (case when a1.co_tipDoc in (228) and a1.co_emp = 2 and a1.co_loc = 4 and a1.fe_doc >= '2016-06-01' and a1.fe_doc <='2017-01-31' then \n" +
"		(case when ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp          from tbm_detMovInv          where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc       \n" +
"                and ( select count(a.*)               \n" +
"                from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc\n" +
"                ) as a ) > 1 ) is not null then ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp          from tbm_detMovInv          where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc       \n" +
"                and ( select count(a.*)               \n" +
"                from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc\n" +
"                ) as a ) > 1 )\n" +
"		else 0 end\n" +
"                ) +\n" +
"                ( case when ( \n" +
"                select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp\n" +
"                from tbm_cabMovInv          \n" +
"                where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc\n" +
"                and ( \n" +
"                select count(a.*)\n" +
"                from ( \n" +
"                select distinct st_ivacom \n" +
"                from tbm_detMovInv \n" +
"                where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc) as a) = 1\n" +
"                and ( \n" +
"                select distinct st_ivacom \n" +
"                from tbm_detMovInv \n" +
"                where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
"                and co_doc = a1.co_doc) = 'S') is not null then \n" +
"                ( \n" +
"                select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp\n" +
"                from tbm_cabMovInv          \n" +
"                where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc\n" +
"                and ( \n" +
"                select count(a.*)\n" +
"                from ( \n" +
"                select distinct st_ivacom \n" +
"                from tbm_detMovInv \n" +
"                where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc) as a) = 1\n" +
"                and ( \n" +
"                select distinct st_ivacom \n" +
"                from tbm_detMovInv \n" +
"                where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
"                and co_doc = a1.co_doc) = 'S') else 0 end\n" +
"                )	end\n" +
"                ) as nd_valComSol";
                
                
                
                strSQL+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipdoc AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	" + strAux;
                strSQL+="       AND a1.st_reg NOT IN('E','I')";
                strSQL+=" 	AND a1.co_tipDoc IN(1,228,3,229)";
                strSQL+=") AS x";
                //System.out.println("SQL cargarDet:" +strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;                                
                if (rst.next()){
                    if (blnCon){
                        //vecReg=new Vector();
                        //vecReg.add(INT_TBL_DAT_LIN,                 "");
                        vecReg.setElementAt(objParSis.getCodigoEmpresa(), INT_TBL_DAT_COD_EMP);
                        //vecReg.add(INT_TBL_DAT_COD_EMP,             "" + objParSis.getCodigoEmpresa());
                        vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valBruDevConIva")==null?"0":(rst.getString("nd_valBruDevConIva").equals("")?"0":rst.getString("nd_valBruDevConIva"))).abs(), INT_TBL_DAT_VTA_CON_IVA_VAL_BRU);
                        //vecReg.add(INT_TBL_DAT_VTA_CON_IVA_VAL_BRU, "" + new BigDecimal(rst.getObject("nd_valBruDevConIva")==null?"0":(rst.getString("nd_valBruDevConIva").equals("")?"0":rst.getString("nd_valBruDevConIva"))).abs());
                        vecReg.setElementAt(new BigDecimal("0.00"), INT_TBL_DAT_VTA_CON_IVA_VAL_NET);
                        //vecReg.add(INT_TBL_DAT_VTA_CON_IVA_VAL_NET, "0");
                        vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valBruDevSinIva")==null?"0":(rst.getString("nd_valBruDevSinIva").equals("")?"0":rst.getString("nd_valBruDevSinIva"))).abs(), INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU);
                        //vecReg.add(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU, "" + new BigDecimal(rst.getObject("nd_valBruDevSinIva")==null?"0":(rst.getString("nd_valBruDevSinIva").equals("")?"0":rst.getString("nd_valBruDevSinIva"))).abs());
                        vecReg.setElementAt(new BigDecimal("0.00"), INT_TBL_DAT_VTA_SIN_IVA_VAL_NET);
                        //vecReg.add(INT_TBL_DAT_VTA_SIN_IVA_VAL_NET, "0");
                        //vecReg.add(INT_TBL_DAT_IVA,                 "");
                        //vecReg.add(INT_TBL_DAT_VTA_CON,             "");
                        //vecReg.add(INT_TBL_DAT_VTA_CRE,             "");
                        //vecReg.add(INT_TBL_DAT_IVA_CON,             "");
                        //vecReg.add(INT_TBL_DAT_IVA_CRE,             "");
                        //vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valcomsol")==null?"0":(rst.getString("nd_valcomsol").equals("")?"0":rst.getString("nd_valcomsol"))).abs(), INT_TBL_DAT_VAL_COM_SOL_VEN);
                        //vecReg.add(INT_TBL_DAT_VAL_COM_SOL_VEN,         "" + new BigDecimal(rst.getObject("nd_valcomsol")==null?"0":(rst.getString("nd_valcomsol").equals("")?"0":rst.getString("nd_valcomsol"))).abs());
                        //vecReg.add(INT_TBL_DAT_VTA_CRE_MES_ANT,     "");
                        //vecReg.add(INT_TBL_DAT_COM_CON_IVA_VAL,     "");
                        //vecReg.add(INT_TBL_DAT_COM_IVA,             "");
                        //vecReg.add(INT_TBL_DAT_COM_SIN_IVA_VAL,     "");
                        //vecReg.add(INT_TBL_DAT_VAL_COM_SOL_COM,     "");
                        //vecReg.add(INT_TBL_DAT_IMP_VAL,             "");
                        //vecReg.add(INT_TBL_DAT_IMP_IVA,             "");
                        //vecReg.add(INT_TBL_DAT_VAL_PAG_BRU,         "");
                        //vecReg.add(INT_TBL_DAT_CRE_FIS_ANT,         "");
                        //vecReg.add(INT_TBL_DAT_VAL_PAG_SIN_RTE,     "");
//                        vecReg.add(INT_TBL_DAT_CRE_FIS_ACT,         "");
//                        vecReg.add(INT_TBL_DAT_RET_10P,             "");
//                        vecReg.add(INT_TBL_DAT_RET_20P,             "");
//                        vecReg.add(INT_TBL_DAT_RET_30P,             "");
//                        vecReg.add(INT_TBL_DAT_RET_70P,             "");
//                        vecReg.add(INT_TBL_DAT_RET_100P,            "");
//                        vecReg.add(INT_TBL_DAT_CRE_TRI_10P,             "");
//                        vecReg.add(INT_TBL_DAT_CRE_TRI_20P,             "");
//                        vecReg.add(INT_TBL_DAT_CRE_TRI_30P,             "");
//                        vecReg.add(INT_TBL_DAT_CRE_TRI_70P,             "");
//                        vecReg.add(INT_TBL_DAT_CRE_TRI_100P,            "");
//                        vecReg.add(INT_TBL_DAT_VAL_PAG_NET,         "");
//                        vecReg.add(INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP,         "");
//                        vecReg.add(INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP,         "");
                        
//                        vecDat.add(vecReg);
//                        i++;
//                        pgrSis.setValue(i);
                    }
                }

                BigDecimal bdeValCon=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValCre=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValConIva=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValCreIva=new BigDecimal(BigInteger.ZERO);

                //IVA Ventas 12%
                strSQL="";
                //strSQL+=" SELECT SUM(nd_valNetSinIva) AS nd_valNetSinIva, SUM(nd_valNetConIva) AS nd_valNetConIva";
                strSQL+="SELECT (SUM(case when x.nd_valNetSinIva_DetSoloSinIva is not null then x.nd_valNetSinIva_DetSoloSinIva else 0 end) + SUM(case when x.nd_valNetSinIva_DetConSinIva is not null then x.nd_valNetSinIva_DetConSinIva else 0 end)) as nd_valNetSinIva, ";
                strSQL+="(SUM(case when x.nd_valNetConIva_DetSoloConIva is not null then x.nd_valNetConIva_DetSoloConIva else 0 end) + SUM(case when x.nd_valNetConIva_DetConSinIva is not null then x.nd_valNetConIva_DetConSinIva else 0 end)) AS nd_valNetConIva ";
                strSQL+=" , SUM(nd_valIvaNet) AS nd_valIvaNet";
                strSQL+=" , (SUM(nd_valIvaCon)*-1) AS nd_valIvaCon, (SUM(nd_valIvaCre)*-1) AS nd_valIvaCre";
                strSQL+=" , (SUM(nd_valCon)*-1) AS nd_valCon, (SUM(nd_valCre)*-1) AS nd_valCre";
                strSQL+=" FROM(";
                strSQL+="	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, ";
                //strSQL+="              , CASE WHEN (a1.nd_valIva=0 OR a1.nd_valIva IS NULL)  THEN (a1.nd_sub) END AS nd_valNetSinIva";
                //strSQL+=" 	       , CASE WHEN a1.nd_valIva<>0 THEN (a1.nd_sub) END AS nd_valNetConIva";
                
                //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv solo hallan registros que tengan items con IVA
  	        strSQL+="( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp ";
	        strSQL+="  from tbm_cabMovInv ";
	        strSQL+="  where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="     and ( select count(a.*) ";
		strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	         ) as a ";
		strSQL+="         ) = 1 ";
		strSQL+="     and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="         ) = 'S' ";
	        strSQL+=") AS nd_valNetConIva_DetSoloConIva, ";

  	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv solo hallan registros que no tengan items con IVA
	        strSQL+="( select CASE WHEN a1.nd_valIva = 0 THEN a1.nd_sub ELSE 0 END AS nd_basCer ";
  	        strSQL+="from tbm_cabMovInv ";
	        strSQL+="where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="   and ( select count(a.*) ";
		strSQL+="         from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	       ) as a ";
		strSQL+="       ) = 1 ";
		strSQL+="   and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="       ) = 'N' ";
	        strSQL+=") AS nd_valNetSinIva_DetSoloSinIva, ";

	        //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp ";
	        strSQL+="  from tbm_detMovInv ";
	        strSQL+="  where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="     and ( select count(a.*) ";
		strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	         ) as a ";
		strSQL+="         ) > 1 ";
	        strSQL+=") AS nd_valNetConIva_DetConSinIva, ";

	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basCer ";
	        strSQL+="from tbm_detMovInv ";
	        strSQL+="where st_ivacom = 'N' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="   and ( select count(a.*) ";
		strSQL+="         from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	       ) as a ";
		strSQL+="       ) > 1 ";
	        strSQL+=") AS nd_valNetSinIva_DetConSinIva ";
                
                strSQL+=" 	       , a1.nd_valIva AS nd_valIvaNet";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCon";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCre";
                
                
                
                
               // strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_sub END AS nd_valCon";
               // strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_sub END AS nd_valCre";                
                  strSQL+=" 	       ,   CASE WHEN b1.ne_tipforPag IN(1,4) THEN\n" +
                                "  (CASE WHEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                                "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                                "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                                "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                                " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                                " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) \n" +
                                " IS NOT NULL THEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                                "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                                "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                                "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                                " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                                " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) else 0.00 end + \n" +
                                "  CASE WHEN ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                                "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                                "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                                "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                                " ) as a          ) > 1 )\n" +
                                "  IS NOT NULL THEN \n" +
                                "( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                                "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                                "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                                "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                                " ) as a          ) > 1 ) \n" +
                                " else 0.00 end \n" +
                                " ) end AS nd_valCon";
                  strSQL+=" 	       , CASE WHEN b1.ne_tipforPag not IN(1,4) THEN\n" +
                        "  (CASE WHEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                        "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                        "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                        "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                        " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                        " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) \n" +
                        "\n" +
                        " IS NOT NULL THEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                        "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                        "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                        "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                        " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                        " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) else 0.00 end\n" +
                        "  + \n" +
                        "  CASE WHEN ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                        "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                        "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                        "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                        " ) as a          ) > 1 )\n" +
                        "  IS NOT NULL THEN \n" +
                        "( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                        "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                        "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                        "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                        " ) as a          ) > 1 ) \n" +
                        " else 0.00 end \n" +
                        " ) end AS nd_valCre";    
                
                
                
                strSQL+=" 	FROM ( tbm_cabMovInv AS a1";
                strSQL+="               LEFT OUTER JOIN (tbm_cabForPag AS b1 LEFT OUTER JOIN tbm_detForPag AS b2 ON b1.co_emp=b2.co_emp AND b1.co_forPag=b2.co_forPag)";
                strSQL+="               ON a1.co_emp=b1.co_emp AND a1.co_forPag=b1.co_forPag";
                strSQL+="            )";
                strSQL+=" 	INNER JOIN tbm_cabTipdoc AS a2";
                
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="" + strAux;
                strSQL+=" 	 AND a1.st_reg NOT IN('E','I')";
                strSQL+=" 	AND a1.co_tipDoc IN (1,228,3,229)";
                //Tony ony Filtro pidio hecto
                strSQL+=" 	and a1.nd_valIva<>0";
                

                strSQL+="	UNION";

                strSQL+="	SELECT  a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, ";
                //strSQL+="              , CASE WHEN (a1.nd_valIva=0 OR a1.nd_valIva IS NULL) THEN (a1.nd_sub) END AS nd_valNetSinIva";
                //strSQL+=" 	       , CASE WHEN a1.nd_valIva<>0 THEN (a1.nd_sub) END AS nd_valNetConIva";
                
                //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv solo hallan registros que tengan items con IVA
  	        strSQL+="( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp ";
	        strSQL+="  from tbm_cabMovInv ";
	        strSQL+="  where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="     and ( select count(a.*) ";
		strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	         ) as a ";
		strSQL+="         ) = 1 ";
		strSQL+="     and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="         ) = 'S' ";
	        strSQL+=") AS nd_valNetConIva_DetSoloConIva, ";

  	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv solo hallan registros que no tengan items con IVA
	        strSQL+="( select CASE WHEN a1.nd_valIva = 0 THEN a1.nd_sub ELSE 0 END AS nd_basCer ";
  	        strSQL+="from tbm_cabMovInv ";
	        strSQL+="where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="   and ( select count(a.*) ";
		strSQL+="         from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	       ) as a ";
		strSQL+="       ) = 1 ";
		strSQL+="   and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="       ) = 'N' ";
	        strSQL+=") AS nd_valNetSinIva_DetSoloSinIva, ";

	        //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp ";
	        strSQL+="  from tbm_detMovInv ";
	        strSQL+="  where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="     and ( select count(a.*) ";
		strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	         ) as a ";
		strSQL+="         ) > 1 ";
	        strSQL+=") AS nd_valNetConIva_DetConSinIva, ";

	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basCer ";
	        strSQL+="from tbm_detMovInv ";
	        strSQL+="where st_ivacom = 'N' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="   and ( select count(a.*) ";
		strSQL+="         from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	       ) as a ";
		strSQL+="       ) > 1 ";
	        strSQL+=") AS nd_valNetSinIva_DetConSinIva ";
                
                strSQL+=" 	       , a1.nd_valIva AS nd_valIvaNet";
//                strSQL+=" 	       , CASE WHEN a1.co_forPag IN (1,97) THEN a1.nd_valIva END AS nd_valIvaCon";
//                strSQL+=" 	       , CASE WHEN (a1.co_forPag NOT IN (1,97) OR a1.co_forPag IS NULL) THEN a1.nd_valIva END AS nd_valIvaCre";
//                strSQL+=" 	       , CASE WHEN (a1.co_forPag IN (1,97) AND a1.nd_valIva<>0) THEN a1.nd_sub END AS nd_valCon";
//                strSQL+=" 	       , CASE WHEN ((a1.co_forPag NOT IN (1,97) OR a1.co_forPag IS NULL) AND a1.nd_valIva<>0) THEN a1.nd_sub END AS nd_valCre";
                                
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCon";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCre";
                
                
                
                //strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_sub END AS nd_valCon";
                //strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_sub END AS nd_valCre";
                strSQL+=" 	       ,   CASE WHEN b1.ne_tipforPag IN(1,4) THEN\n" +
                                "  (CASE WHEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                                "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                                "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                                "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                                " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                                " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) \n" +
                                " IS NOT NULL THEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                                "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                                "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                                "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                                " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                                " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) else 0.00 end + \n" +
                                "  CASE WHEN ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                                "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                                "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                                "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                                " ) as a          ) > 1 )\n" +
                                "  IS NOT NULL THEN \n" +
                                "( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                                "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                                "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                                "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                                " ) as a          ) > 1 ) \n" +
                                " else 0.00 end \n" +
                                " ) end AS nd_valCon";
                  strSQL+=" 	       , CASE WHEN b1.ne_tipforPag not IN(1,4) THEN\n" +
                        "  (CASE WHEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                        "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                        "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                        "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                        " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                        " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) \n" +
                        "\n" +
                        " IS NOT NULL THEN ( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp   \n" +
                        "from tbm_cabMovInv   where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc      \n" +
                        "and ( select count(a.*)            from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp \n" +
                        "and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	         ) as a          ) = 1     \n" +
                        " and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc \n" +
                        " and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc          ) = 'S' ) else 0.00 end\n" +
                        "  + \n" +
                        "  CASE WHEN ( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                        "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                        "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                        "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                        " ) as a          ) > 1 )\n" +
                        "  IS NOT NULL THEN \n" +
                        "( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp   \n" +
                        "from tbm_detMovInv   where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc \n" +
                        "and co_doc = a1.co_doc      and ( select count(a.*)            from ( select distinct st_ivacom \n" +
                        "from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc 	        \n" +
                        " ) as a          ) > 1 ) \n" +
                        " else 0.00 end \n" +
                        " ) end AS nd_valCre"; 
                  
                strSQL+=" 	FROM (    ( tbm_cabMovInv AS a1 ";
                strSQL+="                   LEFT OUTER JOIN (tbm_cabForPag AS b1 LEFT OUTER JOIN tbm_detForPag AS b2 ON b1.co_emp=b2.co_emp AND b1.co_forPag=b2.co_forPag)";
                strSQL+="                   ON a1.co_emp=b1.co_emp AND a1.co_forPag=b1.co_forPag";
                strSQL+="                 )";
                strSQL+=" 	INNER JOIN tbm_cabTipdoc AS a2";
                
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_cabDia AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_dia";
                strSQL+=" 	INNER JOIN tbm_detDia AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_dia=a4.co_dia";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="" + strAux;
                strSQL+=" 	 AND a1.st_reg NOT IN('E','I')";
                strSQL+=" 	 AND a1.co_tipDoc IN (28)";
                //strSQL+=" 	 AND a4.co_cta IN(1437,1030, 1434)";
                //Tony ony Filtro pidio hecto
                strSQL+=" 	and a1.nd_valIva<>0";
                
                if(objParSis.getCodigoEmpresa() == 1)
                    strSQL+="           and a4.co_cta in(1434, 1437,1030)";//las mismas cuentas en Tuval y Cosenco por eso no se hace por separado la validacion
                if(objParSis.getCodigoEmpresa() == 2)
                    strSQL+="           and a4.co_cta in(545, 548,224, 551, 1037, 1163)";
                
                if(objParSis.getCodigoEmpresa() == 3)
                    strSQL+="           and a4.co_cta in(1333,1336,945)";
                if(objParSis.getCodigoEmpresa() == 4)
                    strSQL+="           and a4.co_cta in(1373, 1376,977)";

                strSQL+=" ) AS x";

                //System.out.println("SQL <contado:" +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (blnCon){
                        vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valNetConIva")==null?"0":(rst.getString("nd_valNetConIva").equals("")?"0":rst.getString("nd_valNetConIva"))).abs(), INT_TBL_DAT_VTA_CON_IVA_VAL_NET  );
                        //vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valNetSinIva")==null?"0":(rst.getString("nd_valNetSinIva").equals("")?"0":rst.getString("nd_valNetSinIva"))).abs(), INT_TBL_DAT_VTA_SIN_IVA_VAL_NET  );
                        //System.out.println(vecReg.get(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU));
                        vecReg.setElementAt(new BigDecimal(vecReg.get(INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString()).abs(), INT_TBL_DAT_VTA_SIN_IVA_VAL_NET);
                        vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valIvaNet")==null?"0":(rst.getString("nd_valIvaNet").equals("")?"0":rst.getString("nd_valIvaNet"))).abs(), INT_TBL_DAT_IVA  );


                        bdeValCon=new BigDecimal(rst.getObject("nd_valCon")==null?"0":(rst.getString("nd_valCon").equals("")?"0":rst.getString("nd_valCon")));
                        bdeValCre=new BigDecimal(rst.getObject("nd_valCre")==null?"0":(rst.getString("nd_valCre").equals("")?"0":rst.getString("nd_valCre")));
                        bdeValConIva=new BigDecimal(rst.getObject("nd_valIvaCon")==null?"0":(rst.getString("nd_valIvaCon").equals("")?"0":rst.getString("nd_valIvaCon")));
                        bdeValCreIva=new BigDecimal(rst.getObject("nd_valIvaCre")==null?"0":(rst.getString("nd_valIvaCre").equals("")?"0":rst.getString("nd_valIvaCre")));


                        if(bdeValCon.compareTo(new BigDecimal(BigInteger.ZERO))<0){
                            vecReg.setElementAt("0", INT_TBL_DAT_VTA_CON  );
                            vecReg.setElementAt((bdeValCon.add(bdeValCre)), INT_TBL_DAT_VTA_CRE  );
                            vecReg.setElementAt("0", INT_TBL_DAT_IVA_CON  );
                            vecReg.setElementAt((bdeValConIva.add(bdeValCreIva)), INT_TBL_DAT_IVA_CRE  );
                        }
                        else{
                            vecReg.setElementAt("" + bdeValCon, INT_TBL_DAT_VTA_CON  );
                            vecReg.setElementAt("" + bdeValCre, INT_TBL_DAT_VTA_CRE  );
                            vecReg.setElementAt("" + bdeValConIva, INT_TBL_DAT_IVA_CON  );
                            vecReg.setElementAt("" + bdeValCreIva, INT_TBL_DAT_IVA_CRE  );
                        }
                    }
                }
                
                //IVA Ventas 12% y 14%
                strSQL="";
                strSQL+=" SELECT nd_poriva, ";
                //strSQL+=" SUM(nd_valNetSinIva) AS nd_valNetSinIva, SUM(nd_valNetConIva) AS nd_valNetConIva";
                strSQL+="(SUM(case when x.nd_valNetSinIva_DetSoloSinIva is not null then x.nd_valNetSinIva_DetSoloSinIva else 0 end) + SUM(case when x.nd_valNetSinIva_DetConSinIva is not null then x.nd_valNetSinIva_DetConSinIva else 0 end)) as nd_valNetSinIva, ";
                strSQL+="(SUM(case when x.nd_valNetConIva_DetSoloConIva is not null then x.nd_valNetConIva_DetSoloConIva else 0 end) + SUM(case when x.nd_valNetConIva_DetConSinIva is not null then x.nd_valNetConIva_DetConSinIva else 0 end)) AS nd_valNetConIva ";
                strSQL+=" , SUM(nd_valIvaNet) AS nd_valIvaNet";
                strSQL+=" , (SUM(nd_valIvaCon)*-1) AS nd_valIvaCon, (SUM(nd_valIvaCre)*-1) AS nd_valIvaCre";
                strSQL+=" , (SUM(nd_valCon)*-1) AS nd_valCon, (SUM(nd_valCre)*-1) AS nd_valCre";
                strSQL+=" FROM(";
                strSQL+="	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_poriva, a1.ne_numDoc, ";
                //strSQL+="              , CASE WHEN (a1.nd_valIva=0 OR a1.nd_valIva IS NULL)  THEN (a1.nd_sub) END AS nd_valNetSinIva";
                //strSQL+=" 	       , CASE WHEN a1.nd_valIva<>0 THEN (a1.nd_sub) END AS nd_valNetConIva";
                
                //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv solo hallan registros que tengan items con IVA
                strSQL+="( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp ";
                strSQL+="  from tbm_cabMovInv ";
                strSQL+="  where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
   	        strSQL+="     and ( select count(a.*) ";
	        strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="                ) as a ";
	        strSQL+="         ) = 1 ";
	        strSQL+="     and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
	        strSQL+="         ) = 'S' ";
                strSQL+=") AS nd_valNetConIva_DetSoloConIva, ";

                //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv solo hallan registros que no tengan items con IVA
                strSQL+="( select CASE WHEN a1.nd_valIva = 0 THEN a1.nd_sub ELSE 0 END AS nd_basCer ";
	        strSQL+="  from tbm_cabMovInv ";
	        strSQL+="  where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
	        strSQL+="     and ( select count(a.*) ";
		strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="                ) as a ";
		strSQL+="         ) = 1 ";
	        strSQL+="     and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="         ) = 'N' ";
                strSQL+=") AS nd_valNetSinIva_DetSoloSinIva, ";

                //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
                strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp ";
                strSQL+="  from tbm_detMovInv ";
                strSQL+="  where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
	        strSQL+="     and ( select count(a.*) ";
	        strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="                ) as a ";
	        strSQL+="         ) > 1 ";
                strSQL+=") AS nd_valNetConIva_DetConSinIva, ";

                //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
                strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basCer ";
                strSQL+="  from tbm_detMovInv ";
                strSQL+="  where st_ivacom = 'N' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
	        strSQL+="     and ( select count(a.*) ";
	        strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="                ) as a ";
	        strSQL+="         ) > 1 ";
                strSQL+=") AS nd_valNetSinIva_DetConSinIva ";
                
                strSQL+=" 	       , a1.nd_valIva AS nd_valIvaNet";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCon";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCre";
                
                
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_sub END AS nd_valCon";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_sub END AS nd_valCre";                
                
                
                strSQL+=" 	FROM ( tbm_cabMovInv AS a1";
                strSQL+="               LEFT OUTER JOIN (tbm_cabForPag AS b1 LEFT OUTER JOIN tbm_detForPag AS b2 ON b1.co_emp=b2.co_emp AND b1.co_forPag=b2.co_forPag)";
                strSQL+="               ON a1.co_emp=b1.co_emp AND a1.co_forPag=b1.co_forPag";
                strSQL+="            )";
                strSQL+=" 	INNER JOIN tbm_cabTipdoc AS a2";
                
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="" + strAux;
                strSQL+=" 	 AND a1.st_reg NOT IN('E','I')";
                strSQL+=" 	AND a1.co_tipDoc IN (1,228,3,229)";

                strSQL+="	UNION";

                strSQL+="	SELECT  a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_poriva, a1.ne_numDoc, ";
                //strSQL+="              , CASE WHEN (a1.nd_valIva=0 OR a1.nd_valIva IS NULL)  THEN (a1.nd_sub) END AS nd_valNetSinIva";
                //strSQL+=" 	       , CASE WHEN a1.nd_valIva<>0 THEN (a1.nd_sub) END AS nd_valNetConIva";
                
                //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv solo hallan registros que tengan items con IVA
  	        strSQL+="( select CASE WHEN a1.nd_valIva <> 0 THEN a1.nd_sub ELSE 0 END AS nd_basImp ";
	        strSQL+="  from tbm_cabMovInv ";
	        strSQL+="  where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="     and ( select count(a.*) ";
		strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	         ) as a ";
		strSQL+="         ) = 1 ";
		strSQL+="     and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="         ) = 'S' ";
	        strSQL+=") AS nd_valNetConIva_DetSoloConIva, ";

  	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv solo hallan registros que no tengan items con IVA
	        strSQL+="( select CASE WHEN a1.nd_valIva = 0 THEN a1.nd_sub ELSE 0 END AS nd_basCer ";
  	        strSQL+="from tbm_cabMovInv ";
	        strSQL+="where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="   and ( select count(a.*) ";
		strSQL+="         from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	       ) as a ";
		strSQL+="       ) = 1 ";
		strSQL+="   and ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="       ) = 'N' ";
	        strSQL+=") AS nd_valNetSinIva_DetSoloSinIva, ";

	        //Subquery para traer Valor Neto de IVA <> 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basImp ";
	        strSQL+="  from tbm_detMovInv ";
	        strSQL+="  where st_ivacom = 'S' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="     and ( select count(a.*) ";
		strSQL+="           from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	         ) as a ";
		strSQL+="         ) > 1 ";
	        strSQL+=") AS nd_valNetConIva_DetConSinIva, ";

	        //Subquery para traer Valor Neto de IVA 0% donde en tbm_detMovInv hallan registros que tengan items con y sin IVA
	        strSQL+="( select round( (sum( (nd_can * round(nd_preuni,2)) - (nd_can * round(nd_preuni,2) * (nd_pordes / 100)) )), 2) as nd_basCer ";
	        strSQL+="from tbm_detMovInv ";
	        strSQL+="where st_ivacom = 'N' and co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="   and ( select count(a.*) ";
		strSQL+="         from ( select distinct st_ivacom from tbm_detMovInv where co_emp = a1.co_emp and co_loc = a1.co_loc and co_tipdoc = a1.co_tipdoc and co_doc = a1.co_doc ";
		strSQL+="	       ) as a ";
		strSQL+="       ) > 1 ";
	        strSQL+=") AS nd_valNetSinIva_DetConSinIva ";
                
                strSQL+=" 	       , a1.nd_valIva AS nd_valIvaNet";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCon";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_valIva END AS nd_valIvaCre";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag IN(1,4) THEN a1.nd_sub END AS nd_valCon";
                strSQL+=" 	       , CASE WHEN b1.ne_tipforPag NOT IN(1,4) THEN a1.nd_sub END AS nd_valCre";
                
                strSQL+=" 	FROM (    ( tbm_cabMovInv AS a1 ";
                strSQL+="                   LEFT OUTER JOIN (tbm_cabForPag AS b1 LEFT OUTER JOIN tbm_detForPag AS b2 ON b1.co_emp=b2.co_emp AND b1.co_forPag=b2.co_forPag)";
                strSQL+="                   ON a1.co_emp=b1.co_emp AND a1.co_forPag=b1.co_forPag";
                strSQL+="                 )";
                strSQL+=" 	INNER JOIN tbm_cabTipdoc AS a2";
                
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_cabDia AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_dia";
                strSQL+=" 	INNER JOIN tbm_detDia AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_dia=a4.co_dia";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="" + strAux;
                strSQL+=" 	 AND a1.st_reg NOT IN('E','I')";
                strSQL+=" 	 AND a1.co_tipDoc IN (28)";

                if(objParSis.getCodigoEmpresa() == 1)
                    strSQL+="           and a4.co_cta in(1434, 1437,1030)"; //las mismas cuentas en Tuval y Cosenco por eso no se hace por separado la validacion
                if(objParSis.getCodigoEmpresa() == 2)
                    strSQL+="           and a4.co_cta in(545, 548,224, 551, 1037, 1163)";
                
                if(objParSis.getCodigoEmpresa() == 3)
                    strSQL+="           and a4.co_cta in(1333,1336,945)";
                if(objParSis.getCodigoEmpresa() == 4)
                    strSQL+="           and a4.co_cta in(1373, 1376,977)";

                strSQL+=" ) AS x ";
                strSQL+="group by nd_poriva";

                //System.out.println("SQL <contado:" +strSQL);
                rst=stm.executeQuery(strSQL);
                
                bdeBasImpIvaVta12 = new BigDecimal("0");
                bdeValIvaVta12 = new BigDecimal("0");
                bdeBasImpIvaVtaCon12 = new BigDecimal("0");
                bdeValIvaVtaCon12 = new BigDecimal("0");
                bdeBasImpIvaVtaCre12 = new BigDecimal("0");
                bdeValIvaVtaCre12 = new BigDecimal("0");
                bdeBasImpIvaVta14 = new BigDecimal("0");
                bdeValIvaVta14 = new BigDecimal("0");
                bdeBasImpIvaVtaCon14 = new BigDecimal("0");
                bdeValIvaVtaCon14 = new BigDecimal("0");
                bdeBasImpIvaVtaCre14 = new BigDecimal("0");
                bdeValIvaVtaCre14 = new BigDecimal("0");
                
                while (rst.next())
                {  if (blnCon)
                   {  bdePorIva = objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva") ,objParSis.getDecimalesMostrar());
                      if (bdePorIva.equals(new BigDecimal("12.00")))
                      {  bdeBasImpIvaVta12 = new BigDecimal(rst.getObject("nd_valNetConIva")==null?"0":(rst.getString("nd_valNetConIva").equals("")?"0":rst.getString("nd_valNetConIva")));
                         bdeValIvaVta12 = new BigDecimal(rst.getObject("nd_valIvaNet")==null?"0":(rst.getString("nd_valIvaNet").equals("")?"0":rst.getString("nd_valIvaNet")));
                         bdeBasImpIvaVtaCon12 = new BigDecimal(rst.getObject("nd_valCon")==null?"0":(rst.getString("nd_valCon").equals("")?"0":rst.getString("nd_valCon")));
                         bdeValIvaVtaCon12 = new BigDecimal(rst.getObject("nd_valIvaCon")==null?"0":(rst.getString("nd_valIvaCon").equals("")?"0":rst.getString("nd_valIvaCon")));
                         bdeBasImpIvaVtaCre12 = new BigDecimal(rst.getObject("nd_valCre")==null?"0":(rst.getString("nd_valCre").equals("")?"0":rst.getString("nd_valCre")));
                         bdeValIvaVtaCre12 = new BigDecimal(rst.getObject("nd_valIvaCre")==null?"0":(rst.getString("nd_valIvaCre").equals("")?"0":rst.getString("nd_valIvaCre")));
                      }
                      else if (bdePorIva.equals(new BigDecimal("14.00")))
                      {  bdeBasImpIvaVta14 = new BigDecimal(rst.getObject("nd_valNetConIva")==null?"0":(rst.getString("nd_valNetConIva").equals("")?"0":rst.getString("nd_valNetConIva")));
                         bdeValIvaVta14 = new BigDecimal(rst.getObject("nd_valIvaNet")==null?"0":(rst.getString("nd_valIvaNet").equals("")?"0":rst.getString("nd_valIvaNet")));
                         bdeBasImpIvaVtaCon14 = new BigDecimal(rst.getObject("nd_valCon")==null?"0":(rst.getString("nd_valCon").equals("")?"0":rst.getString("nd_valCon")));
                         bdeValIvaVtaCon14 = new BigDecimal(rst.getObject("nd_valIvaCon")==null?"0":(rst.getString("nd_valIvaCon").equals("")?"0":rst.getString("nd_valIvaCon")));
                         bdeBasImpIvaVtaCre14 = new BigDecimal(rst.getObject("nd_valCre")==null?"0":(rst.getString("nd_valCre").equals("")?"0":rst.getString("nd_valCre")));
                         bdeValIvaVtaCre14 = new BigDecimal(rst.getObject("nd_valIvaCre")==null?"0":(rst.getString("nd_valIvaCre").equals("")?"0":rst.getString("nd_valIvaCre")));
                      }
                   }
                }
                
                if ( (bdeBasImpIvaVta12.compareTo(new BigDecimal ("0")) > 0 && bdeBasImpIvaVta14.compareTo(new BigDecimal ("0")) < 0) ||
                     (bdeBasImpIvaVta12.compareTo(new BigDecimal ("0")) < 0 && bdeBasImpIvaVta14.compareTo(new BigDecimal ("0")) > 0) )
                {  //Las condiciones evaluan lo siguiente:
                   //1) Si la Base_Imponible_12% es positiva y si la Base_Imponible_14% es negativa
                   //2) Si la Base_Imponible_12% es negativa y si la Base_Imponible_14% es positiva
                   //En caso de cumplirse estas condiciones, no se debera aplicar la funcion abs() para que el calculo se realice correctamente,
                   //pues si las Bases_Imponibles tienen distinto signo es debido a la presencia de TipDoc DEVVENE y debe efectuarse una resta.
                }
                else
                {  //Caso contrario, se debe aplicar la funcion abs()
                   bdeBasImpIvaVta12.abs();
                   bdeValIvaVta12.abs();
                   bdeBasImpIvaVtaCon12.abs();
                   bdeValIvaVtaCon12.abs();
                   bdeBasImpIvaVtaCre12.abs();
                   bdeValIvaVtaCre12.abs();
                   bdeBasImpIvaVta14.abs();
                   bdeValIvaVta14.abs();
                   bdeBasImpIvaVtaCon14.abs();
                   bdeValIvaVtaCon14.abs();
                   bdeBasImpIvaVtaCre14.abs();
                   bdeValIvaVtaCre14.abs();
                }
                
                //impuesto a liquidar del mes anterior
                        String strFecFinMesAnt="";
                        //primer paso, se obtiene la fecha final del mes anterior
                        //--ultimo dia del mes anterior(la fecha de corte seleccionada en el programa)
                        strSQL="";
                        strSQL+="select (CAST(";
                        strSQL+="	extract('year' FROM cast('" + objUti.formatearFecha(objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy-MM-dd") + "' as date)) || '-' ||";
                        strSQL+=" 	(CASE WHEN extract('month' FROM cast('" + objUti.formatearFecha(objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy-MM-dd") + "' as date)) >9 ";
                        strSQL+="                 THEN '' || extract('month' FROM cast('" + objUti.formatearFecha(objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy-MM-dd") + "' as date))";
                        strSQL+=" 	ELSE '0' || extract('month' FROM cast('" + objUti.formatearFecha(objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy-MM-dd") + "' as date)) END) || '-' ||";
                        strSQL+=" 	('01')";
                        strSQL+=" AS DATE)-1) AS fe_mesFinAnt";
                        //System.out.println("SQL strFecFinMesAnt:" +strSQL);
                        rst=stm.executeQuery(strSQL);
                        if (rst.next()){
                                strFecFinMesAnt=rst.getString("fe_mesFinAnt");

                        }
                        String strFecIniMesAnt="";
                        //--primer dia del mes anterior(la fecha se la envia desde java y es la obtenida en el query anterior("ultimo dia del mes anterior")
                        strSQL="";
                        strSQL+="select (CAST(";
                        strSQL+=" 	extract('year' FROM cast('" + strFecFinMesAnt + "' as date)) || '-' ||";
                        strSQL+=" 	(CASE WHEN extract('month' FROM cast('" + strFecFinMesAnt + "' as date)) >9 ";
                        strSQL+="                 THEN '' || extract('month' FROM cast('" + strFecFinMesAnt + "' as date))";
                        strSQL+=" 	ELSE '0' || extract('month' FROM cast('" + strFecFinMesAnt + "' as date)) END) || '-' ||";
                        strSQL+=" 	('01')";
                        strSQL+="  AS DATE)) AS fe_mesIniAnt";
                        //System.out.println("SQL strFecFinMesAnt:" +strSQL);
                        rst=stm.executeQuery(strSQL);
                        if (rst.next()){
                                strFecIniMesAnt=rst.getString("fe_mesIniAnt");

                        }
                

                strSQL="";
                strSQL+="SELECT CASE WHEN (nd_valConMesAnt) < 0 THEN (nd_valCreMesAnt + nd_valConMesAnt)";
                strSQL+=" ELSE nd_valCreMesAnt END AS nd_valCreMesAnt FROM(";
                strSQL+=" 	SELECT (SUM(nd_valIvaCre)*-1) AS nd_valCreMesAnt, (SUM(nd_valIvaCon)*-1) AS nd_valConMesAnt";
                strSQL+=" 	FROM(";
                strSQL+=" 		 SELECT   CASE WHEN a1.co_forPag IN (1,97) THEN a1.nd_valIva END AS nd_valIvaCon";
                strSQL+=" 		 , CASE WHEN (a1.co_forPag NOT IN (1,97) OR a1.co_forPag IS NULL) THEN a1.nd_valIva END AS nd_valIvaCre";
                strSQL+=" 		 , CASE WHEN (a1.co_forPag NOT IN (1,97) OR a1.co_forPag IS NULL) THEN a1.nd_sub END AS nd_valCre";
                strSQL+=" 		 FROM tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipdoc AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.fe_doc BETWEEN '" + strFecIniMesAnt + "' AND '" + strFecFinMesAnt + "'";
                strSQL+=" 	AND a1.st_reg NOT IN('E','I')";
                strSQL+=" 	AND a1.co_tipDoc IN (1,228,3,229,28)";
                strSQL+="       ) AS x";
                strSQL+=") AS y";
                //System.out.println("venta credito mes anterior:   " +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (blnCon){
                        vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valCreMesAnt")==null?"0":(rst.getString("nd_valCreMesAnt").equals("")?"0":rst.getString("nd_valCreMesAnt"))).abs(), INT_TBL_DAT_VTA_CRE_MES_ANT  );
                    }
                }
                
                //compras con iva
                strSQL="";
                strSQL+=" SELECT (";
                strSQL+=" 		(CASE WHEN b1.nd_valCom IS NULL THEN 0 ELSE b1.nd_valCom END ) +";
                strSQL+=" 		(CASE WHEN b3.nd_valCom IS NULL THEN 0 ELSE b3.nd_valCom END ) +";
                strSQL+=" 		(CASE WHEN b4.nd_valCom IS NULL THEN 0 ELSE b4.nd_valCom END )";
                strSQL+=" 	) AS nd_valCom";
                strSQL+=" 	, (";
                strSQL+=" 		(CASE WHEN b1.nd_valIva IS NULL THEN 0 ELSE b1.nd_valIva END ) +";
                strSQL+=" 		(CASE WHEN b3.nd_valIva IS NULL THEN 0 ELSE b3.nd_valIva END ) +";
                strSQL+=" 		(CASE WHEN b4.nd_valIva IS NULL THEN 0 ELSE b4.nd_valIva END )";
                strSQL+=" 	) AS nd_valIva";
                strSQL+=" FROM(";
                strSQL+="SELECT SUM(z.nd_valCom) AS nd_valCom, SUM(z.nd_valIva) AS nd_valIva FROM(";
                strSQL+="        (";
                strSQL+="                SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_desCor, x.ne_numDoc";
                strSQL+="                   , SUM(x.nd_valCom) AS nd_valCom, x.nd_valIva ";
                strSQL+="                FROM ( select a.* from ";
                strSQL+="                       ( ";
                strSQL+="                       SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END))";
                strSQL+="                          - ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END)*(a5.nd_porDes/100)) AS nd_valCom, ";
                strSQL+="                          (a4.nd_valIva) AS nd_valIva";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc )";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 	ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detMovInv AS a5  ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom; //AND a4.fe_doc BETWEEN '2011-01-01' AND '2011-01-31'
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")"; //2,38,57,104,105,106,118,119,120,121,122,179,185,186
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I') AND a4.nd_valIva <> 0 AND a5.st_ivacom = 'S'"; //--AND a4.co_doc=15734   --3783
                strSQL+="                       EXCEPT ";
                strSQL+="                       SELECT DISTINCT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END))";
                strSQL+="                          - ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END)*(a5.nd_porDes/100)) AS nd_valCom, ";
                strSQL+="                          (a4.nd_valIva) AS nd_valIva";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc )";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 	ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detMovInv AS a5  ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       INNER JOIN tbm_detdia as a1 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc AND a4.co_doc=a1.co_dia) ";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom;
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")";
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I') AND a4.nd_valIva <> 0 AND a5.st_ivacom = 'S'";
                strSQL+="                          and a1.co_cta = " + intCodCtaIvaTra + " and (a1.nd_mondeb <> 0 or a1.nd_monhab <> 0) ";
                strSQL+="                       ) as a ";
                strSQL+="                       GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.tx_desCor, a.nd_valIva, a.co_reg, a.nd_valcom ";
                strSQL+="                       ORDER BY a.co_tipDoc, a.ne_numDoc ";
                //strSQL+="                       GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.ne_numDoc, b12.tx_desCor, a4.nd_valIva, a5.co_reg, a5.nd_can, a5.nd_preUni, a5.nd_porDes, a5.nd_cosUni";
                //strSQL+="                       ORDER BY a4.co_tipDoc, a4.ne_numDoc";
                strSQL+="                     ) AS x";
                strSQL+="                GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_desCor, x.ne_numDoc, x.nd_valIva";
                strSQL+="         )";
                strSQL+="         UNION";
                strSQL+="         (";
                strSQL+="                SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.tx_desCor, y.ne_numDoc, SUM(y.nd_valCom) AS nd_valCom, y.nd_valIva ";
                strSQL+="                FROM ( select a.* from ";
                strSQL+="                       ( ";
                strSQL+="                       SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          (a5.nd_can * a5.nd_preUni) AS nd_valCom, (a4.nd_valIva) AS nd_valIva ";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc)";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detConIntMovInv AS a5 ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom; //AND a4.fe_doc BETWEEN '2011-01-01' AND '2011-01-31'
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")"; //2,38,57,104,105,106,118,119,120,121,122,179,185,186
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL+="                          AND a4.nd_valIva <> 0 AND a5.st_iva = 'S' "; //--AND a4.co_doc=3126  --1577
                strSQL+="                       EXCEPT ";
                strSQL+="                       SELECT DISTINCT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          (a5.nd_can * a5.nd_preUni) AS nd_valCom, (a4.nd_valIva) AS nd_valIva ";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc)";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detConIntMovInv AS a5 ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       INNER JOIN tbm_detdia as a1 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc AND a4.co_doc=a1.co_dia) ";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom;
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")";
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL+="                          AND a4.nd_valIva <> 0 AND a5.st_iva = 'S' ";
                strSQL+="                          and a1.co_cta = " + intCodCtaIvaTra + " and (a1.nd_mondeb <> 0 or a1.nd_monhab <> 0) ";
                strSQL+="                       ) as a ";
                strSQL+="                       GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.tx_desCor, a.nd_valIva, a.co_reg, a.nd_valcom ";
                strSQL+="                       ORDER BY a.co_tipDoc, a.ne_numDoc ";
                //strSQL+="                       GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.ne_numDoc, b12.tx_desCor, a4.nd_valIva, a5.co_reg, a5.nd_can, a5.nd_preUni";
                //strSQL+="                       ORDER BY a4.co_tipDoc, a4.ne_numDoc";
                strSQL+="                     ) AS y";
                strSQL+="                     GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.tx_desCor, y.ne_numDoc, y.nd_valIva";
                strSQL+="         )";
                strSQL+=" ) AS z";

                strSQL+="      ) AS b1";
                strSQL+="  LEFT OUTER JOIN(";
                //--este query lo que hace es presentar DEVCOM
                strSQL+="	 SELECT SUM(nd_basImpDevCom) AS nd_valCom, SUM(nd_valIva) AS nd_valIva ";
                strSQL+="        FROM (";
                strSQL+="	        (";
                strSQL+="		  SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, ";
                strSQL+="                    (a1.nd_tot - a1.nd_valIva) AS nd_basImpDevCom, (a1.nd_valIva) AS nd_valIva, (a1.nd_tot) AS nd_tot, a1.fe_doc AS fe_fac, b1.tx_desCor ";
                strSQL+="		  FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc) ";
                strSQL+="		  INNER JOIN tbm_pagMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="";                    //--esta quemado el tipoDocumento=4 porque solo se quiere sacar DEVCOM
                strSQL+=" 		       AND a1.co_tipDoc IN(4) AND a1.st_reg NOT IN('E','I') AND a1.nd_valIva <> 0 " + strAux;
                strSQL+="                 EXCEPT ";
                strSQL+="		  SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, ";
                strSQL+="                    (a1.nd_tot - a1.nd_valIva) AS nd_basImpDevCom, (a1.nd_valIva) AS nd_valIva, (a1.nd_tot) AS nd_tot, a1.fe_doc AS fe_fac, b1.tx_desCor ";
                strSQL+="		  FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc) ";
                strSQL+="		  INNER JOIN tbm_pagMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                strSQL+="                 INNER JOIN tbm_detdia as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_dia) ";
                strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="";                    //--esta quemado el tipoDocumento=4 porque solo se quiere sacar DEVCOM
                strSQL+=" 		       AND a1.co_tipDoc IN(4) AND a1.st_reg NOT IN('E','I') AND a1.nd_valIva <> 0 " + strAux;
                strSQL+="                      and a3.co_cta = " + intCodCtaIvaTra + " and (a3.nd_mondeb <> 0 or a3.nd_monhab <> 0) ";
                strSQL+="		)";
                strSQL+="	      ) AS w";
                strSQL+=" ) AS b3";
                strSQL+=" ON b1.nd_valCom<>0 OR b3.nd_valCom<>0";
                strSQL+="  LEFT OUTER JOIN(";
                //	--NOTAS DE CREDITO
                //strSQL+="	SELECT SUM(b1.nd_basImpNotCre) AS nd_valCom, SUM(b1.nd_basImpNotCre*0.12) AS nd_valIva FROM(";
                //strSQL+="			SELECT a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc";
                //strSQL+="			, (a4.nd_tot/1.12) AS nd_basImpNotCre";
                
                strSQL+="	SELECT SUM(b1.nd_basImpNotCre) AS nd_valCom, SUM( b1.nd_basImpNotCre * (b1.nd_poriva / 100) ) AS nd_valIva FROM(";
                strSQL+="			SELECT a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc, a4.nd_poriva";
                strSQL+="			, ( a4.nd_tot / ((a4.nd_poriva / 100) + 1) ) AS nd_basImpNotCre";
                strSQL+="			FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b1";
                strSQL+="				ON a4.co_emp=b1.co_emp AND a4.co_loc=b1.co_loc AND a4.co_tipDoc=b1.co_tipDoc )";
                strSQL+="			INNER JOIN tbm_cabDia AS a3";
                strSQL+="			ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc";
                strSQL+=" 			AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_dia";
                strSQL+=" 			INNER JOIN tbm_detDia AS a6";
                strSQL+=" 			ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc";
                strSQL+="			AND a3.co_tipDoc=a6.co_tipDoc AND a3.co_dia=a6.co_dia";
                strSQL+="			WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                       AND a4.co_tipDoc IN(74,156)";//son notas de credito
                strSQL+="" + strFecCom;//AND a4.fe_doc BETWEEN '2011-11-01' AND '2011-11-30'
                strSQL+=" 			AND a4.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('I','E')";
                
                //Tuval y Cosenco (tienen los mismos codigos)
                if(objParSis.getCodigoEmpresa()==1)
                {  //Tuval
                   //584 = 12% IVA COMPRAS
                   //585 = IVA BIENES Y SERVICIOS 12%
                   //4544 = 14% IVA COMPRAS
                   //4543 = IVA BIENES Y SERVICIOS 14%
                   strSQL+="                       AND a6.co_cta IN(584,585,4544,4543)";
                }
                else if(objParSis.getCodigoEmpresa()==2)
                {  //Castek
                   //94 = 12% IVA COMPRAS
                   //95 = IVA BIENES Y SERVICIOS 12%
                   //2217 = 14% IVA COMPRAS
                   //2216 = IVA BIENES Y SERVICIOS 14%
                   strSQL+="                       AND a6.co_cta IN(94,95,2217,2216)";
                }    
                else if(objParSis.getCodigoEmpresa()==4)
                {  //Dimulti
                   //545 = 12% IVA COMPRAS
                   //546 = IVA BIENES Y SERVICIOS 12%
                   //3134 = 14% IVA COMPRAS
                   //3133 = IVA BIENES Y SERVICIOS 14%
                   strSQL+="                       AND a6.co_cta IN(545,546,3134,3133)";
                }
                
                //strSQL+="			GROUP BY a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc, a4.nd_tot";
                strSQL+="			GROUP BY a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc, a4.nd_poriva, a4.nd_tot";
                strSQL+=" 			ORDER BY a4.co_tipdoc, a4.ne_numDoc";
                strSQL+=" 	) AS b1";
                strSQL+=" ) AS b4";
                strSQL+=" ON b1.nd_valCom<>0 OR b4.nd_valCom<>0";
                //System.out.println("SQL compras:" +strSQL);
                rst=stm.executeQuery(strSQL);
                BigDecimal bdeValComTmp=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValIvaTmp=new BigDecimal(BigInteger.ZERO);
                if (rst.next()){
                    if (blnCon){
                        bdeValComTmp=new BigDecimal(rst.getObject("nd_valCom")==null?"0":(rst.getString("nd_valCom").equals("")?"0":rst.getString("nd_valCom")));
                        //vecReg.setElementAt(bdeValComTmp.abs(), INT_TBL_DAT_COM_CON_IVA_VAL  );

                        bdeValIvaTmp=new BigDecimal(rst.getObject("nd_valIva")==null?"0":(rst.getString("nd_valIva").equals("")?"0":rst.getString("nd_valIva")));
                        //vecReg.setElementAt(bdeValIvaTmp.abs(), INT_TBL_DAT_COM_IVA  );
                    }
                }
                
                //Compras con IVA 12% y 14%
                strSQL="";
                strSQL+=" SELECT b1.nd_poriva, (";
                strSQL+=" 		(CASE WHEN b1.nd_valCom IS NULL THEN 0 ELSE b1.nd_valCom END ) +";
                strSQL+=" 		(CASE WHEN b3.nd_valCom IS NULL THEN 0 ELSE b3.nd_valCom END ) +";
                strSQL+=" 		(CASE WHEN b4.nd_valCom IS NULL THEN 0 ELSE b4.nd_valCom END )";
                strSQL+=" 	) AS nd_valCom";
                strSQL+=" 	, (";
                strSQL+=" 		(CASE WHEN b1.nd_valIva IS NULL THEN 0 ELSE b1.nd_valIva END ) +";
                strSQL+=" 		(CASE WHEN b3.nd_valIva IS NULL THEN 0 ELSE b3.nd_valIva END ) +";
                strSQL+=" 		(CASE WHEN b4.nd_valIva IS NULL THEN 0 ELSE b4.nd_valIva END )";
                strSQL+=" 	) AS nd_valIva";
                strSQL+=" FROM(";
                strSQL+="SELECT z.nd_poriva, SUM(z.nd_valCom) AS nd_valCom, SUM(z.nd_valIva) AS nd_valIva FROM(";
                strSQL+="        (";
                strSQL+="                SELECT x.nd_poriva, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_desCor, x.ne_numDoc";
                strSQL+="                   , SUM(x.nd_valCom) AS nd_valCom, x.nd_valIva ";
                strSQL+="                FROM ( select a.* from ";
                strSQL+="                       ( ";
                strSQL+="                       SELECT a4.nd_poriva, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END))";
                strSQL+="                          - ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END)*(a5.nd_porDes/100)) AS nd_valCom, ";
                strSQL+="                          (a4.nd_valIva) AS nd_valIva";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc )";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 	ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detMovInv AS a5  ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom; //AND a4.fe_doc BETWEEN '2011-01-01' AND '2011-01-31'
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")"; //2,38,57,104,105,106,118,119,120,121,122,179,185,186
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I') AND a4.nd_valIva <> 0 AND a5.st_ivacom = 'S'"; //--AND a4.co_doc=15734   --3783
                strSQL+="                       EXCEPT ";
                strSQL+="                       SELECT DISTINCT a4.nd_poriva, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END))";
                strSQL+="                          - ((a5.nd_can * CASE WHEN a5.nd_preUni IS NULL THEN a5.nd_cosUni ELSE a5.nd_preUni END)*(a5.nd_porDes/100)) AS nd_valCom, ";
                strSQL+="                          (a4.nd_valIva) AS nd_valIva";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc )";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 	ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detMovInv AS a5  ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       INNER JOIN tbm_detdia as a1 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc AND a4.co_doc=a1.co_dia) ";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom;
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")";
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I') AND a4.nd_valIva <> 0 AND a5.st_ivacom = 'S'";
                strSQL+="                          and a1.co_cta = " + intCodCtaIvaTra + " and (a1.nd_mondeb <> 0 or a1.nd_monhab <> 0) ";
                strSQL+="                       ) as a ";
                strSQL+="                       GROUP BY a.nd_poriva, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.tx_desCor, a.nd_valIva, a.co_reg, a.nd_valcom ";
                strSQL+="                       ORDER BY a.co_tipDoc, a.ne_numDoc ";
                //strSQL+="                       GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.ne_numDoc, b12.tx_desCor, a4.nd_valIva, a5.co_reg, a5.nd_can, a5.nd_preUni, a5.nd_porDes, a5.nd_cosUni";
                //strSQL+="                       ORDER BY a4.co_tipDoc, a4.ne_numDoc";
                strSQL+="                     ) AS x";
                strSQL+="                GROUP BY x.nd_poriva, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_desCor, x.ne_numDoc, x.nd_valIva";
                strSQL+="         )";
                strSQL+="         UNION";
                strSQL+="         (";
                strSQL+="                SELECT y.nd_poriva, y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.tx_desCor, y.ne_numDoc, SUM(y.nd_valCom) AS nd_valCom, y.nd_valIva ";
                strSQL+="                FROM ( select a.* from ";
                strSQL+="                       ( ";
                strSQL+="                       SELECT a4.nd_poriva, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          (a5.nd_can * a5.nd_preUni) AS nd_valCom, (a4.nd_valIva) AS nd_valIva ";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc)";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detConIntMovInv AS a5 ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom; //AND a4.fe_doc BETWEEN '2011-01-01' AND '2011-01-31'
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")"; //2,38,57,104,105,106,118,119,120,121,122,179,185,186
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL+="                          AND a4.nd_valIva <> 0 AND a5.st_iva = 'S' "; //--AND a4.co_doc=3126  --1577
                strSQL+="                       EXCEPT ";
                strSQL+="                       SELECT DISTINCT a4.nd_poriva, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.co_reg, b12.tx_desCor, a4.ne_numDoc, ";
                strSQL+="                          (a5.nd_can * a5.nd_preUni) AS nd_valCom, (a4.nd_valIva) AS nd_valIva ";
                strSQL+="                       FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b12 ON a4.co_emp=b12.co_emp AND a4.co_loc=b12.co_loc AND a4.co_tipDoc=b12.co_tipDoc)";
                strSQL+="                       INNER JOIN tbm_pagMovInv AS a3 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc)";
                strSQL+="                       INNER JOIN tbm_detConIntMovInv AS a5 ON(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc)";
                strSQL+="                       INNER JOIN tbm_detdia as a1 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc AND a4.co_doc=a1.co_dia) ";
                strSQL+="                       WHERE a4.co_emp = " + objParSis.getCodigoEmpresa() + "";
                strSQL+=                           "" + strFecCom;
                strSQL+="                          AND a4.co_tipDoc IN(" + getTipoDxP() + ")";
                strSQL+="                          AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL+="                          AND a4.nd_valIva <> 0 AND a5.st_iva = 'S' ";
                strSQL+="                          and a1.co_cta = " + intCodCtaIvaTra + " and (a1.nd_mondeb <> 0 or a1.nd_monhab <> 0) ";
                strSQL+="                       ) as a ";
                strSQL+="                       GROUP BY a.nd_poriva, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.ne_numDoc, a.tx_desCor, a.nd_valIva, a.co_reg, a.nd_valcom ";
                strSQL+="                       ORDER BY a.co_tipDoc, a.ne_numDoc ";
                //strSQL+="                       GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.ne_numDoc, b12.tx_desCor, a4.nd_valIva, a5.co_reg, a5.nd_can, a5.nd_preUni";
                //strSQL+="                       ORDER BY a4.co_tipDoc, a4.ne_numDoc";
                strSQL+="                     ) AS y";
                strSQL+="                     GROUP BY y.nd_poriva, y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.tx_desCor, y.ne_numDoc, y.nd_valIva";
                strSQL+="         )";
                strSQL+=" ) AS z";
                strSQL+=" group by z.nd_poriva ";
                strSQL+="      ) AS b1";
                strSQL+="  LEFT OUTER JOIN(";
                //--este query lo que hace es presentar DEVCOM
                strSQL+="	 SELECT w.nd_poriva, SUM(nd_basImpDevCom) AS nd_valCom, SUM(nd_valIva) AS nd_valIva ";
                strSQL+="        FROM (";
                strSQL+="	        (";
                strSQL+="		  SELECT a1.nd_poriva, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, ";
                strSQL+="                    (a1.nd_tot - a1.nd_valIva) AS nd_basImpDevCom, (a1.nd_valIva) AS nd_valIva, (a1.nd_tot) AS nd_tot, a1.fe_doc AS fe_fac, b1.tx_desCor ";
                strSQL+="		  FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc) ";
                strSQL+="		  INNER JOIN tbm_pagMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="";                    //--esta quemado el tipoDocumento=4 porque solo se quiere sacar DEVCOM
                strSQL+=" 		       AND a1.co_tipDoc IN(4) AND a1.st_reg NOT IN('E','I') AND a1.nd_valIva <> 0 " + strAux;
                strSQL+="                 EXCEPT ";
                strSQL+="		  SELECT DISTINCT a1.nd_poriva, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, ";
                strSQL+="                    (a1.nd_tot - a1.nd_valIva) AS nd_basImpDevCom, (a1.nd_valIva) AS nd_valIva, (a1.nd_tot) AS nd_tot, a1.fe_doc AS fe_fac, b1.tx_desCor ";
                strSQL+="		  FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc) ";
                strSQL+="		  INNER JOIN tbm_pagMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                strSQL+="                 INNER JOIN tbm_detdia as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_dia) ";
                strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="";                    //--esta quemado el tipoDocumento=4 porque solo se quiere sacar DEVCOM
                strSQL+=" 		       AND a1.co_tipDoc IN(4) AND a1.st_reg NOT IN('E','I') AND a1.nd_valIva <> 0 " + strAux;
                strSQL+="                      and a3.co_cta = " + intCodCtaIvaTra + " and (a3.nd_mondeb <> 0 or a3.nd_monhab <> 0) ";
                strSQL+="		)";
                strSQL+="	      ) AS w";
                strSQL+="             group by w.nd_poriva ";
                strSQL+=" ) AS b3";
                strSQL+=" ON b1.nd_valCom<>0 OR b3.nd_valCom<>0";
                strSQL+="  LEFT OUTER JOIN(";
                //	--NOTAS DE CREDITO
                //strSQL+="	SELECT SUM(b1.nd_basImpNotCre) AS nd_valCom, SUM(b1.nd_basImpNotCre*0.12) AS nd_valIva FROM(";
                //strSQL+="			SELECT a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc";
                //strSQL+="			, (a4.nd_tot/1.12) AS nd_basImpNotCre";
                
                strSQL+="	SELECT b1.nd_poriva, SUM(b1.nd_basImpNotCre) AS nd_valCom, SUM( b1.nd_basImpNotCre * (b1.nd_poriva / 100) ) AS nd_valIva FROM(";
                strSQL+="			SELECT a4.nd_poriva, a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc";
                strSQL+="			, ( a4.nd_tot / ((a4.nd_poriva / 100) + 1) ) AS nd_basImpNotCre";
                strSQL+="			FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cabTipDoc AS b1";
                strSQL+="				ON a4.co_emp=b1.co_emp AND a4.co_loc=b1.co_loc AND a4.co_tipDoc=b1.co_tipDoc )";
                strSQL+="			INNER JOIN tbm_cabDia AS a3";
                strSQL+="			ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc";
                strSQL+=" 			AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_dia";
                strSQL+=" 			INNER JOIN tbm_detDia AS a6";
                strSQL+=" 			ON a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc";
                strSQL+="			AND a3.co_tipDoc=a6.co_tipDoc AND a3.co_dia=a6.co_dia";
                strSQL+="			WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                       AND a4.co_tipDoc IN(74,156)";//son notas de credito
                strSQL+="" + strFecCom;//AND a4.fe_doc BETWEEN '2011-11-01' AND '2011-11-30'
                strSQL+=" 			AND a4.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('I','E')";
                
                //Tuval y Cosenco (tienen los mismos codigos)
                if(objParSis.getCodigoEmpresa()==1)
                {  //Tuval
                   //584 = 12% IVA COMPRAS
                   //585 = IVA BIENES Y SERVICIOS 12%
                   //4544 = 14% IVA COMPRAS
                   //4543 = IVA BIENES Y SERVICIOS 14%
                   strSQL+="                       AND a6.co_cta IN(584,585,4544,4543)";
                }
                else if(objParSis.getCodigoEmpresa()==2)
                {  //Castek
                   //94 = 12% IVA COMPRAS
                   //95 = IVA BIENES Y SERVICIOS 12%
                   //2217 = 14% IVA COMPRAS
                   //2216 = IVA BIENES Y SERVICIOS 14%
                   strSQL+="                       AND a6.co_cta IN(94,95,2217,2216)";
                }    
                else if(objParSis.getCodigoEmpresa()==4)
                {  //Dimulti
                   //545 = 12% IVA COMPRAS
                   //546 = IVA BIENES Y SERVICIOS 12%
                   //3134 = 14% IVA COMPRAS
                   //3133 = IVA BIENES Y SERVICIOS 14%
                   strSQL+="                       AND a6.co_cta IN(545,546,3134,3133)";
                }
                
                //strSQL+="			GROUP BY a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc, a4.nd_tot";
                strSQL+="			GROUP BY a4.nd_poriva, a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, b1.tx_desCor, a4.ne_numDoc, a4.nd_tot";
                strSQL+=" 			ORDER BY a4.co_tipdoc, a4.ne_numDoc";
                strSQL+=" 	) AS b1";
                strSQL+="       group by b1.nd_poriva ";
                strSQL+=" ) AS b4";
                strSQL+=" ON b1.nd_valCom<>0 OR b4.nd_valCom<>0";
                //System.out.println("SQL compras:" +strSQL);
                rst=stm.executeQuery(strSQL);
                
                bdeBasImpIvaCom12 = new BigDecimal("0");
                bdeValIvaCom12 = new BigDecimal("0");
                bdeBasImpIvaCom14 = new BigDecimal("0");
                bdeValIvaCom14 = new BigDecimal("0");
                
                while (rst.next())
                {  if (blnCon)
                   {  bdePorIva = objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva") ,objParSis.getDecimalesMostrar());
                      if (bdePorIva.equals(new BigDecimal("12.00")))
                      {  bdeBasImpIvaCom12 = new BigDecimal(rst.getObject("nd_valCom")==null?"0":(rst.getString("nd_valCom").equals("")?"0":rst.getString("nd_valCom")));
                         bdeValIvaCom12 = new BigDecimal(rst.getObject("nd_valIva")==null?"0":(rst.getString("nd_valIva").equals("")?"0":rst.getString("nd_valIva")));
                      }
                      else if (bdePorIva.equals(new BigDecimal("14.00")))
                      {  bdeBasImpIvaCom14 = new BigDecimal(rst.getObject("nd_valCom")==null?"0":(rst.getString("nd_valCom").equals("")?"0":rst.getString("nd_valCom")));
                         bdeValIvaCom14 = new BigDecimal(rst.getObject("nd_valIva")==null?"0":(rst.getString("nd_valIva").equals("")?"0":rst.getString("nd_valIva")));
                      }
                   }
                }

                BigDecimal bdeValIvaImp=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValImp=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdePorIvaImp;
                String strFecCalIvaImp;
                java.util.Date datFecCalIvaImp, datFecIniIva14;
                java.util.Date  datFecFinIva14;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                strFecCalIvaImp = "";
                
                //Importaciones
                switch (objSelFec.getTipoSeleccion())
                {   case 0: //Busqueda por rangos
                        strFecCalIvaImp = objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strFecCalIvaImp = objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strFecCalIvaImp = objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                        break;
                    case 3: //Todo.
                        datFecCalIvaImp = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecCalIvaImp = datFecCalIvaImp.toString();
                        break;
                }
                
                datFecCalIvaImp = dateFormat.parse(strFecCalIvaImp);
                datFecIniIva14 = dateFormat.parse("2016-06-01");
                datFecFinIva14 = dateFormat.parse("2017-06-01");
                if (datFecCalIvaImp.compareTo(datFecIniIva14) < 0){
                   bdePorIvaImp = new BigDecimal("12");
                }else if (datFecCalIvaImp.compareTo(datFecIniIva14) >= 0 && datFecCalIvaImp.compareTo(datFecFinIva14) < 0){
                   bdePorIvaImp = new BigDecimal("14");
                }
                else{
                   bdePorIvaImp = objParSis.getPorcentajeIvaCompras();
                }
                strSQL="";
                strSQL+="SELECT SUM(salAct) AS nd_salAct FROM(";
                strSQL+=" 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct";
                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                strSQL+=" 	INNER JOIN tbm_detdia AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                strSQL+=" 	where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	and a2.co_cta=" + intCodCtaIvaImp + "";
                strSQL+=" 	AND a2.nd_monDeb>0";
                strSQL+="" + strAuxFecDia;
                strSQL+=" 	and a1.st_reg='A'";
                strSQL+=" 	UNION";
                strSQL+=" 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAct";
                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                strSQL+=" 	INNER JOIN tbm_detdia AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                strSQL+=" 	where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	and a2.co_cta=" + intCodCtaIvaImp + "";
                strSQL+=" 	AND a2.nd_monDeb>0";
                strSQL+="" + strAuxFecDia;
                strSQL+=" 	and a1.st_reg='A'";
                strSQL+=" ) AS x";
                //System.out.println("SQL cargarDet:" +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (blnCon){
                        bdeValIvaImp=new BigDecimal(rst.getObject("nd_salAct")==null?"0":(rst.getString("nd_salAct").equals("")?"0":rst.getString("nd_salAct")));
                        //bdeValImp=(new BigDecimal("100").divide(new BigDecimal("12"), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(bdeValIvaImp);
                        bdeValImp=(new BigDecimal("100").divide((bdePorIvaImp), objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(bdeValIvaImp);
                        vecReg.setElementAt(bdeValImp.abs(), INT_TBL_DAT_IMP_VAL  );
                        vecReg.setElementAt(bdeValIvaImp.abs(), INT_TBL_DAT_IMP_IVA  );
                    }
                }

                //compras sin iva
                strSQL="";
                strSQL+="SELECT  SUM(CASE WHEN b1.nd_valComSinIva IS NULL THEN 0 ELSE b1.nd_valComSinIva END ) AS nd_valComSinIva";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a7.nd_tot AS nd_valComSinIva";
                strSQL+=" 	FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_detconintmovinv AS a7";
                strSQL+="		ON a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_tipDoc=a7.co_tipDoc AND a1.co_doc=a7.co_doc)";
                strSQL+="	INNER JOIN tbm_pagMovInv AS a2";
                strSQL+="	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="	INNER JOIN tbr_detRecDocCabMovInv AS a4";
                strSQL+="	ON a2.co_emp=a4.co_empRel AND a2.co_loc=a4.co_locRel AND a2.co_tipDoc=a4.co_tipDocRel AND a2.co_doc=a4.co_docRel";
                strSQL+=" 	INNER JOIN tbm_detRecDoc AS a5";
                strSQL+=" 	ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc";
                strSQL+=" 	AND a4.co_reg=a5.co_reg AND a2.tx_numChq=a5.tx_numChq";
                strSQL+=" 	INNER JOIN tbm_cabRecDoc AS a6";
                strSQL+=" 	ON a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_tipDoc=a6.co_tipDoc AND a5.co_doc=a6.co_doc";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="	AND a1.co_tipDoc IN(" + getTipoDxP() + ")";//2,38,57,104,105,106,118,119,120,121,122
                strSQL+="" + strFecFac;//AND a2.fe_venChq BETWEEN '2011-11-01' AND '2011-11-30'
                strSQL+="	AND a1.st_reg NOT IN('E','I') AND a2.st_reg IN('A','C') AND a5.st_reg NOT IN('E','I')";
                strSQL+="	AND a7.st_iva='N'";
                strSQL+="	AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL)";
                strSQL+="	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a7.nd_tot";
                strSQL+="	UNION";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, (a7.nd_can*a7.nd_cosuni) AS nd_valComSinIva";
                strSQL+=" 	FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a7";
                strSQL+=" 		ON a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_tipDoc=a7.co_tipDoc AND a1.co_doc=a7.co_doc)";
                strSQL+=" 	INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbr_detRecDocCabMovInv AS a4";
                strSQL+=" 	ON a2.co_emp=a4.co_empRel AND a2.co_loc=a4.co_locRel AND a2.co_tipDoc=a4.co_tipDocRel AND a2.co_doc=a4.co_docRel";
                strSQL+=" 	INNER JOIN tbm_detRecDoc AS a5";
                strSQL+=" 	ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_doc=a5.co_doc";
                strSQL+=" 	AND a4.co_reg=a5.co_reg AND a2.tx_numChq=a5.tx_numChq";
                strSQL+=" 	INNER JOIN tbm_cabRecDoc AS a6";
                strSQL+=" 	ON a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_tipDoc=a6.co_tipDoc AND a5.co_doc=a6.co_doc";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="	AND a1.co_tipDoc IN(" + getTipoDxP() + ")";//2,38,57,104,105,106,118,119,120,121,122
                strSQL+="" + strFecFac;//AND a2.fe_venChq BETWEEN '2011-11-01' AND '2011-11-30'
                strSQL+="	AND a1.st_reg NOT IN('E','I') AND a2.st_reg IN('A','C') AND a5.st_reg NOT IN('E','I')";
                strSQL+=" 	AND (a7.st_ivacom='N' OR a7.st_ivacom IS NULL)";
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a7.nd_can, a7.nd_cosuni";
                strSQL+=" 	ORDER BY co_tipDoc, co_doc";
                strSQL+="      ) AS b1";
                //System.out.println("SQL compras sin iva:" +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (blnCon){
                        //vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valComSinIva")==null?"0":(rst.getString("nd_valComSinIva").equals("")?"0":rst.getString("nd_valComSinIva"))).abs(), INT_TBL_DAT_COM_SIN_IVA_VAL  );
                    }
                }
                
                //como informativo, se adiciona informacion en presentacion de DEVCOM que fueron canceladas con CRUP
                //IVA  de  DEVCOM
                strSQL="";
                strSQL+="SELECT  SUM(CASE WHEN b1.nd_valIva IS NULL THEN 0 ELSE b1.nd_valIva END ) AS nd_valIvaDevCom_cru";
                strSQL+=", SUM(CASE WHEN b1.nd_basImp IS NULL THEN 0 ELSE b1.nd_basImp END ) AS nd_basImpDevCom_cru ";
                strSQL+=" FROM(";
                strSQL+="	 SELECT a1.co_emp, a1.co_loc";
                strSQL+="	 , a1.co_cli, a1.tx_ruc, a1.tx_nomCli, (a1.nd_tot-a1.nd_valIva) AS nd_basImp, (a1.nd_valIva) AS nd_valIva, (a1.nd_tot) AS nd_tot";
                strSQL+="	 , CAST(''||a1.ne_numDoc AS CHARACTER VARYING) AS tx_numChq, a1.fe_doc AS fe_fac, CAST('' AS CHARACTER VARYING) AS tx_numAutSri";
                strSQL+=" 	 , CAST('' AS CHARACTER VARYING) AS tx_numSer, CAST('' AS CHARACTER VARYING) AS tx_fecCad, CAST('' AS CHARACTER) AS st_colReg, CAST(0 AS SMALLINT) AS co_reg";
                strSQL+=" 	 FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" 	 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	 INNER JOIN tbm_detPag AS a3";
                strSQL+=" 	 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag AND a2.co_tipDoc=a3.co_tipDocPag ";
                strSQL+=" 	 AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag";
                strSQL+=" 	 INNER JOIN tbm_cabPag AS a4";
                strSQL+=" 	 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" 	 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="        AND a1.co_tipDoc=4";//esta quemado el tipoDocumento=4 porque solo se quiere sacar DEVCOM
                strSQL+="	 AND a1.st_reg NOT IN('E','I')";
                strSQL+="	 AND a1.nd_valIva<>0";
                strSQL+="        " + strAux;//AND a1.fe_doc BETWEEN '2012-03-01' AND '2012-03-31'
                strSQL+=" 	 AND a4.co_tipDoc=61";
                strSQL+="	 AND";
                strSQL+="	       CASE WHEN a4.co_emp IN(1,2) THEN a4.co_loc=5";
                strSQL+="	       WHEN a4.co_emp IN(4)   THEN a4.co_loc=2 END";
                strSQL+="	 ORDER BY tx_nomCli";
                strSQL+=") AS b1";
                //System.out.println("SQL iva DEVCOM:" +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (blnCon){
                        vecReg.setElementAt(new BigDecimal(rst.getObject("nd_basImpDevCom_cru")==null?"0":(rst.getString("nd_basImpDevCom_cru").equals("")?"0":rst.getString("nd_basImpDevCom_cru"))).abs(), INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP  );
                        vecReg.setElementAt(new BigDecimal(rst.getObject("nd_valIvaDevCom_cru")==null?"0":(rst.getString("nd_valIvaDevCom_cru").equals("")?"0":rst.getString("nd_valIvaDevCom_cru"))).abs(), INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP  );

                    }
                }

                vecDat.add(vecReg);
                i++;
                pgrSis.setValue(i);

                //Asignar vectores al modelo.                                                
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);

                //valor bruto a pagar
                BigDecimal bdeIvaCon=new BigDecimal(BigInteger.ZERO);//iva de contado
                BigDecimal bdeCreMesAnt=new BigDecimal(BigInteger.ZERO);//credito del mes anterior
                BigDecimal bdeIvaCom=new BigDecimal(BigInteger.ZERO);//iva compras
                BigDecimal bdeIvaImp=new BigDecimal(BigInteger.ZERO);//iva de importacion
                BigDecimal bdeValBruPag=new BigDecimal(BigInteger.ZERO);//valor bruto a pagar(por pagar/credito)
                //iva de contado  +  credito del mes anterior
                //-   (iva compras  + iva de importacion)
                bdeIvaCon=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString()));
                bdeCreMesAnt=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString()));
                bdeIvaCom=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString()));
                bdeIvaImp=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString()));

                bdeValBruPag=(bdeIvaCon.abs().add(bdeCreMesAnt.abs())).subtract((bdeIvaCom.abs().add(bdeIvaImp.abs())));
                objTblMod.setValueAt(bdeValBruPag, 0, INT_TBL_DAT_VAL_PAG_BRU);//si es negativo es valor para credito fiscal, si es positivo es valor a pagar

                //credito fiscal
                        //                //--saldo actual
                        //                BigDecimal bdesSalAct=new BigDecimal(BigInteger.ZERO);
                        //                BigDecimal bdeSalAnt=new BigDecimal(BigInteger.ZERO);
                        //
                        //                strSQL="";
                        //                strSQL+="	SELECT a1.co_emp, a2.co_cta, sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct";
                        //                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                        //                strSQL+=" 	INNER JOIN tbm_detdia AS a2";
                        //                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        //                strSQL+=" 	where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        //                strSQL+="   	and a2.co_cta=" + intCodCtaCreFis + "";
                        //                strSQL+="" + strAuxFecDia;
                        //                strSQL+=" 	and a1.st_reg='A'";
                        //                strSQL+=" 	GROUP BY a1.co_emp, a2.co_cta";
                        //                strSQL+=" 	UNION";
                        //                strSQL+=" 	SELECT a1.co_emp, a2.co_cta, sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAct";
                        //                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                        //                strSQL+=" 	INNER JOIN tbm_detdia AS a2";
                        //                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        //                strSQL+=" 	where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        //                strSQL+=" 	and a2.co_cta=" + intCodCtaCreFis + "";
                        //                strSQL+="" + strAuxFecDia;
                        //                strSQL+=" 	and a1.st_reg='A'";
                        //                strSQL+=" 	GROUP BY a1.co_emp, a2.co_cta";
                        //                System.out.println("SQL cargarDet:" +strSQL);
                        //                rst=stm.executeQuery(strSQL);
                        //                if (rst.next()){
                        //                    if (blnCon){
                        //                        bdesSalAct=new BigDecimal(rst.getObject("salAct")==null?"0":(rst.getString("salAct").equals("")?"0":rst.getString("salAct"))).abs();
                        //                    }
                        //                }
                        //                //--saldo anterior
                        //                strSQL="";
                        //                strSQL+="	SELECT a1.co_emp, a2.co_cta, sum( (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAnt";
                        //                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                        //                strSQL+=" 	 INNER JOIN tbm_detdia AS a2";
                        //                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        //                strSQL+="       where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        //                strSQL+=" 	and a2.co_cta=" + intCodCtaCreFis + "";
                        //                strSQL+=" 	AND a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        //                strSQL+=" 	and a1.st_reg='A'";
                        //                strSQL+=" 	GROUP BY a1.co_emp, a2.co_cta";
                        //                strSQL+=" 	UNION";
                        //                strSQL+=" 	SELECT a1.co_emp, a2.co_cta,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAnt";
                        //                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                        //                strSQL+=" 	INNER JOIN tbm_detdia AS a2";
                        //                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                        //                strSQL+="       where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        //                strSQL+=" 	and a2.co_cta=" + intCodCtaCreFis + "";
                        //                strSQL+=" 	AND a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        //                strSQL+=" 	and a1.st_reg='A'";
                        //                strSQL+=" 	GROUP BY a1.co_emp, a2.co_cta";
                        //                System.out.println("SQL cargarDet:" +strSQL);
                        //                rst=stm.executeQuery(strSQL);
                        //                if (rst.next()){
                        //                    if (blnCon){
                        //                        bdeSalAnt=new BigDecimal(rst.getObject("salAnt")==null?"0":(rst.getString("salAnt").equals("")?"0":rst.getString("salAnt"))).abs();
                        //                    }
                        //                }
                        //                objTblMod.setValueAt((bdeSalAnt.add(bdesSalAct)).abs(), 0, INT_TBL_DAT_CRE_FIS_ANT);

                BigDecimal bdesSalCreFisAnt=new BigDecimal(BigInteger.ZERO);
                strSQL="";
                strSQL+="	SELECT a1.co_emp, a2.co_cta, sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAnt";
                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                strSQL+=" 	INNER JOIN tbm_detdia AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                strSQL+=" 	where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="   	and a2.co_cta=" + intCodCtaCreFis + "";
                strSQL+="       AND a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" 	and a1.st_reg='A'";
                strSQL+=" 	GROUP BY a1.co_emp, a2.co_cta";
                strSQL+=" 	UNION";
                strSQL+=" 	SELECT a1.co_emp, a2.co_cta, sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAnt";
                strSQL+=" 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                strSQL+=" 	INNER JOIN tbm_detdia AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia";
                strSQL+=" 	where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	and a2.co_cta=" + intCodCtaCreFis + "";
                strSQL+="       AND a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" 	and a1.st_reg='A'";
                strSQL+=" 	GROUP BY a1.co_emp, a2.co_cta";
                //System.out.println("bdesSalCreFisAnt:" +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (blnCon){
                        bdesSalCreFisAnt=new BigDecimal(rst.getObject("salAnt")==null?"0":(rst.getString("salAnt").equals("")?"0":rst.getString("salAnt"))).abs();
                    }
                }



                objTblMod.setValueAt(bdesSalCreFisAnt, 0, INT_TBL_DAT_CRE_FIS_ANT);


                //strSQL+="       AND a1.fe_doc BETWEEN '" + strFecIniMesAnt + "' AND '" + strFecFinMesAnt + "'";



                BigDecimal bdeCreFisAnt=new BigDecimal(BigInteger.ZERO);//credito fiscal anterior
                BigDecimal bdeValPagBru=new BigDecimal(BigInteger.ZERO);//credito fiscal actual
                bdeValPagBru=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString()));
                //bdeCreFisAnt=(bdeSalAnt.add(bdesSalAct));
                bdeCreFisAnt=(bdesSalCreFisAnt);

                BigDecimal bdeCreFisActFin=new BigDecimal(BigInteger.ZERO);


                bdeCreFisActFin=(bdeValPagBru.subtract(bdeCreFisAnt));

                if(bdeCreFisActFin.compareTo(new BigDecimal(BigInteger.ZERO))>=0){//
                    objTblMod.setValueAt("0", 0, INT_TBL_DAT_CRE_FIS_ACT);
                    objTblMod.setValueAt(bdeCreFisActFin.abs(), 0, INT_TBL_DAT_VAL_PAG_SIN_RTE);
                }
                else{
                    objTblMod.setValueAt(bdeCreFisActFin.abs(), 0, INT_TBL_DAT_CRE_FIS_ACT);
                    objTblMod.setValueAt("0", 0, INT_TBL_DAT_VAL_PAG_SIN_RTE);
                }

                //retenciones
                BigDecimal bdeRetFte10P=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeRetFte20P=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeRetFte30P=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeRetFte70P=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeRetFte100P=new BigDecimal(BigInteger.ZERO);
                strSQL="";
                strSQL+="SELECT SUM(nd_valRet10P) AS nd_valRet10P, SUM(nd_valRet20P) AS nd_valRet20P, SUM(nd_valRet30P) AS nd_valRet30P, SUM(nd_valRet70P) AS nd_valRet70P, SUM(nd_valRet100P) AS nd_valRet100P FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc1,a3.nd_porRet";
                strSQL+="       , CASE WHEN a3.nd_porRet=10 THEN SUM(a2.nd_abo) ELSE 0 END AS nd_valRet10P";
                strSQL+="       , CASE WHEN a3.nd_porRet=20 THEN SUM(a2.nd_abo) ELSE 0 END AS nd_valRet20P";
                strSQL+=" 	, CASE WHEN a3.nd_porRet=30 THEN SUM(a2.nd_abo) ELSE 0 END AS nd_valRet30P";
                strSQL+=" 	, CASE WHEN a3.nd_porRet IN(70) THEN SUM(a2.nd_abo) ELSE 0 END AS nd_valRet70P";
                strSQL+=" 	, CASE WHEN a3.nd_porRet IN(100) THEN SUM(a2.nd_abo) ELSE 0 END AS nd_valRet100P";
                strSQL+=" 	FROM (((   (tbm_cabPag AS a1  INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc)     INNER JOIN";
                strSQL+=" 	tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
                strSQL+=" 	LEFT OUTER JOIN tbm_datautsri AS b3";
                strSQL+=" 	 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)";
                strSQL+=" 	INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbm_pagMovInv AS a3 	ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strSQL+=" 	INNER JOIN tbm_cabMovInv AS a4";
                strSQL+=" 	 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="" + strAux;
                strSQL+=" 	AND a1.co_tipDoc IN(33,160,158,230)";
                // --- Inicio comentario 12/May/2016 ---
                //En la siguiente condicion de los CodTipRet, se va a quitar el co_tipRet 12 = RF10 = "Retención en la fuente 10%" para evitar obtener un 
                //valor no real al calcular el valor de Ret.Iva 10% debido a que aparece el numero 10 ocasionando conflicto al momento de realizar GROUP BY.
                //strSQL+=" 	AND a3.co_tipRet IN(10,7,9,1,11,8,2,3,12,4,5,6,14,15)";
                strSQL+=" 	AND a3.co_tipRet IN(10,7,9,1,11,8,2,3,4,5,6,14,15)";
                // --- Fin comentario 12/May/2016 ------
                strSQL+=" 	AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL+="	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc1, a3.nd_porRet";
                strSQL+=" 	ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc1";
                strSQL+=" ) AS x";
                //System.out.println("SQL cargarDet:" +strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (blnCon){
                        bdeRetFte10P=new BigDecimal(rst.getObject("nd_valRet10P")==null?"0":(rst.getString("nd_valRet10P").equals("")?"0":rst.getString("nd_valRet10P")));
                        bdeRetFte20P=new BigDecimal(rst.getObject("nd_valRet20P")==null?"0":(rst.getString("nd_valRet20P").equals("")?"0":rst.getString("nd_valRet20P")));
                        bdeRetFte30P=new BigDecimal(rst.getObject("nd_valRet30P")==null?"0":(rst.getString("nd_valRet30P").equals("")?"0":rst.getString("nd_valRet30P")));
                        bdeRetFte70P=new BigDecimal(rst.getObject("nd_valRet70P")==null?"0":(rst.getString("nd_valRet70P").equals("")?"0":rst.getString("nd_valRet70P")));
                        bdeRetFte100P=new BigDecimal(rst.getObject("nd_valRet100P")==null?"0":(rst.getString("nd_valRet100P").equals("")?"0":rst.getString("nd_valRet100P")));
                    }
                }
                objTblMod.setValueAt(bdeRetFte10P.abs(), 0, INT_TBL_DAT_RET_10P);
                objTblMod.setValueAt(bdeRetFte20P.abs(), 0, INT_TBL_DAT_RET_20P);
                objTblMod.setValueAt(bdeRetFte30P.abs(), 0, INT_TBL_DAT_RET_30P);
                objTblMod.setValueAt(bdeRetFte70P.abs(), 0, INT_TBL_DAT_RET_70P);
                objTblMod.setValueAt(bdeRetFte100P.abs(), 0, INT_TBL_DAT_RET_100P);

                BigDecimal bdeValPagAntRet=new BigDecimal(BigInteger.ZERO);
                bdeValPagAntRet=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString()));

                objTblMod.setValueAt(  (bdeValPagAntRet.add(bdeRetFte30P.abs()).add(bdeRetFte70P.abs()).add(bdeRetFte100P.abs())), 0, INT_TBL_DAT_VAL_PAG_NET);
                
                boolean blnIsCosenco=false;
                blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO")> -1) ? true: false;
                
                //Credito tributario
                BigDecimal bdeCreTri10P = new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeCreTri20P = new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeCreTri30P = new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeCreTri50P = new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeCreTri70P = new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeCreTri100P = new BigDecimal(BigInteger.ZERO);
                
                strAux2 = getSaldoCreditoTributario(con, "1.01.07.03.08"); //10% IVA RET. EN VENTAS
                
                if (strAux2.equals("true"))
                {  //Variable bdeSalCreTri es calculado dentro de getSaldoCreditoTributario()
                   bdeCreTri10P = bdeSalCreTri;
                }
                else
                {  //Se presento algun error
                   mostrarMsgInf(strAux2);
                   blnRes = false;
                   return blnRes;
                }
                
                strAux2 = getSaldoCreditoTributario(con, "1.01.07.03.09"); //20% IVA RET. EN VENTAS
                
                if (strAux2.equals("true"))
                {  //Variable bdeSalCreTri es calculado dentro de getSaldoCreditoTributario()
                   bdeCreTri20P = bdeSalCreTri;
                }
                else
                {  //Se presento algun error
                   mostrarMsgInf(strAux2);
                   blnRes = false;
                   return blnRes;
                }

                strAux2 = getSaldoCreditoTributario(con, "1.01.07.03.03"); //30% IVA RET. EN VENTAS
                
                if (strAux2.equals("true"))
                {  //Variable bdeSalCreTri es calculado dentro de getSaldoCreditoTributario()
                   bdeCreTri30P = bdeSalCreTri;
                }
                else
                {  //Se presento algun error
                   mostrarMsgInf(strAux2);
                   blnRes = false;
                   return blnRes;
                }
                
                strAux2 = getSaldoCreditoTributario(con, "1.01.07.03.14"); //50% IVA RET. EN VENTAS
                
                if (strAux2.equals("true"))
                {  //Variable bdeSalCreTri es calculado dentro de getSaldoCreditoTributario()
                   bdeCreTri50P = bdeSalCreTri;
                }
                else
                {  //Se presento algun error
                   mostrarMsgInf(strAux2);
                   blnRes = false;
                   return blnRes;
                }

                strAux2 = getSaldoCreditoTributario(con, "1.01.07.03.06"); //70% IVA RET. EN VENTAS
                
                if (strAux2.equals("true"))
                {  //Variable bdeSalCreTri es calculado dentro de getSaldoCreditoTributario()
                   bdeCreTri70P = bdeSalCreTri;
                }
                else
                {  //Se presento algun error
                   mostrarMsgInf(strAux2);
                   blnRes = false;
                   return blnRes;
                }

                strAux2 = getSaldoCreditoTributario(con, "1.01.07.03.07"); //100% IVA RET.EN VENTAS
                
                if (strAux2.equals("true"))
                {  //Variable bdeSalCreTri es calculado dentro de getSaldoCreditoTributario()
                   bdeCreTri100P = bdeSalCreTri;
                }
                else
                {  //Se presento algun error
                   mostrarMsgInf(strAux2);
                   blnRes = false;
                   return blnRes;
                }
                //Cosenco no tiene retencion 100P o 10P tony
                if (blnIsCosenco) {
                    bdeCreTri10P= new BigDecimal(BigInteger.ZERO);
                    bdeCreTri100P= new BigDecimal(BigInteger.ZERO);
                }
                objTblMod.setValueAt(bdeCreTri10P, 0, INT_TBL_DAT_CRE_TRI_10P);
                objTblMod.setValueAt(bdeCreTri20P, 0, INT_TBL_DAT_CRE_TRI_20P);
                objTblMod.setValueAt(bdeCreTri30P, 0, INT_TBL_DAT_CRE_TRI_30P);
                objTblMod.setValueAt(bdeCreTri50P, 0, INT_TBL_DAT_CRE_TRI_50P);
                objTblMod.setValueAt(bdeCreTri70P, 0, INT_TBL_DAT_CRE_TRI_70P);
                objTblMod.setValueAt(bdeCreTri100P, 0, INT_TBL_DAT_CRE_TRI_100P);
                
                //}
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                pgrSis.setValue(0);
                butCon.setText("Consultar");
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
            } //if (con!=null)
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
    } //Funcion cargarDetReg()
    
    private String getSaldoCreditoTributario(Connection con, String strCodCta)
    {
      Statement stmLoc;
      ResultSet rstLoc;
      String strRes = "", strAuxTmp;
      int intCodCta;
      BigDecimal bdeMonDeb, bdeMonHab;
      
      try
      {  stmLoc = con.createStatement();
         strAuxTmp = "";
      
         switch (objSelFec.getTipoSeleccion()){
            case 0: //Busqueda por rangos
                strAuxTmp+=" AND (a2.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                break;
            case 1: //Fechas menores o iguales que "Hasta".
                strAuxTmp+=" AND (a2.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                break;
            case 2: //Fechas mayores o iguales que "Desde".
                strAuxTmp+=" AND (a2.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                break;
            case 3: //Todo.
                break;
         }
         
         intCodCta = 0;
         strSQL = "SELECT co_cta FROM tbm_placta WHERE st_reg = 'A' ";
         strSQL += "and co_emp = " + objParSis.getCodigoEmpresa() + " ";
         strSQL += "and tx_codcta = '" + strCodCta + "'";
         rstLoc = stmLoc.executeQuery(strSQL);
         
         if (rstLoc.next())
            intCodCta = rstLoc.getInt("co_cta");

         strSQL="";
         strSQL+="SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia,";
         strSQL+=" 	x.fe_dia, x.fe_ing,  x.descrip as descrip,";
         strSQL+=" 	CASE WHEN y.tx_nomCli IS NULL THEN x.tx_glo";
         strSQL+="       WHEN y.tx_nomCli = (cast('' as character varying)) THEN x.tx_glo";
         strSQL+=" 	ELSE y.tx_nomCli END AS tx_nomCli,";
         strSQL+=" 	CASE WHEN y.ne_numDoc IS NULL THEN x.ne_numDoc";
         strSQL+=" 	ELSE y.ne_numDoc END AS ne_numDoc, ";
         strSQL+="       CASE WHEN y.ne_numDoc2 IS NULL THEN x.tx_numDia";
         strSQL+="       ELSE y.ne_numDoc2 END AS ne_numDoc2";
         strSQL+=" 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monDeb)              END AS monDeb";
         strSQL+=" 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monHab)              END AS monHab";
         strSQL+=" 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monDeb - x.monHab)   END AS saldo";
         strSQL+="       , x.co_usrDia, x.tx_nomDia, y.co_usrDoc, y.tx_nomDoc, x.st_reg";
         strSQL+=" FROM(	";
         strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia,";
         strSQL+="	a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, a2.tx_numDia as ne_numDoc, ''||Null as tx_numDia, ";
         strSQL+="         a2.tx_glo as tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb, ";
         strSQL+=" 	SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
         strSQL+=" 	,d1.co_usr AS co_usrDia, d1.tx_nom AS tx_nomDia, a2.st_reg FROM (tbm_cabDia AS a2 LEFT OUTER JOIN tbm_usr AS d1 ON a2.co_usrIng=d1.co_usr) INNER JOIN tbm_detDia AS a3 ";
         strSQL+=" 	ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
         strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
         strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
         strSQL+="       AND a3.co_cta=" + intCodCta + "";
         //strSQL+="       AND (a2.fe_dia BETWEEN"  + strFechaInicial + " AND " + strFechaFinal + ")";
         strSQL+="" + strAuxTmp;
         strSQL+=" 	AND a2.st_reg='A' and a2.co_tipDoc <> 30"; //30 = DIGECO

         strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, a2.fe_dia, a2.fe_ing, a4.tx_desCor, a2.tx_numDia, a2.tx_numDia, a2.tx_glo, a2.tx_glo, d1.co_usr, d1.tx_nom, a2.st_reg";
         strSQL+=" 	ORDER BY fe_dia, descrip, ne_numDoc) AS x";
         strSQL+=" LEFT OUTER JOIN(";
         strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, ";
         strSQL+=" 	a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, ''||a1.ne_numDoc as ne_numDoc, ''||Null AS ne_numDoc2, a1.tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb,";
         strSQL+=" 	SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
         strSQL+=" 	, d1.co_usr AS co_usrDoc, d1.tx_nom AS tx_nomDoc, a2.st_reg FROM (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS d1 ON a1.co_usrIng=d1.co_usr)";
         strSQL+=" 	INNER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
         strSQL+=" 	INNER JOIN tbm_detDia AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
         strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
         strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
         strSQL+="       AND a3.co_cta=" + intCodCta + "";
         //strSQL+="       AND (a2.fe_dia BETWEEN"  + strFechaInicial + " AND " + strFechaFinal + ")";
         strSQL+="" + strAuxTmp;
         strSQL+=" 	AND a2.st_reg='A' and a2.co_tipDoc <> 30"; //30 = DIGECO
         
         strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia,a2.fe_dia,a2.fe_ing, a4.tx_desCor, a1.ne_numDoc, a1.tx_nomCli, a2.tx_glo, d1.co_usr, d1.tx_nom, d1.co_usr, d1.tx_nom, a2.st_reg";
         strSQL+=" 	UNION ALL";
         strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia,";
         strSQL+=" 	a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, ''||a1.ne_numDoc1 as ne_numDoc, ''||a1.ne_numDoc2, a1.tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb, ";
         strSQL+=" 	SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
         strSQL+=" 	, d1.co_usr AS co_usrDoc, d1.tx_nom AS tx_nomDoc, a2.st_reg FROM (tbm_cabPag AS a1 INNER JOIN tbm_usr AS d1 ON a1.co_usrIng=d1.co_usr)";
         strSQL+=" 	left OUTER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
         strSQL+=" 	INNER JOIN tbm_detDia AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
         strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
         strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
         strSQL+="       AND a3.co_cta=" + intCodCta + "";
         strSQL+="" + strAuxTmp;
         strSQL+=" 	AND a2.st_reg='A' and a2.co_tipDoc <> 30"; //30 = DIGECO
         
         strSQL+=" 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, a2.fe_dia, a2.fe_ing, a4.tx_desCor, a1.ne_numDoc1, a1.ne_numDoc2, a1.tx_nomCli, a2.tx_glo, d1.co_usr, d1.tx_nom, a2.st_reg";
         strSQL+=" 	) AS y";
         strSQL+=" ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc AND x.co_dia=y.co_dia";               

         strSQL+=" GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia,";
         strSQL+=" 	x.fe_dia, x.fe_ing, y.tx_nomCli, x.descrip, x.ne_numDoc, y.ne_numDoc, x.tx_numDia";
         strSQL+=" 	, x.tx_nomCli,x.tx_glo, y.ne_numDoc2, x.co_usrDia, x.tx_nomDia, y.co_usrDoc, y.tx_nomDoc, x.st_reg";
         strSQL+=" ORDER BY x.fe_dia, x.fe_ing, x.descrip, x.ne_numDoc";
         rstLoc = stmLoc.executeQuery(strSQL);
         
         bdeMonDeb = new BigDecimal("0");
         bdeMonHab = new BigDecimal("0");
         
         while (rstLoc.next())
         {  bdeMonDeb = bdeMonDeb.add(rstLoc.getBigDecimal("monDeb"));
            bdeMonHab = bdeMonHab.add(rstLoc.getBigDecimal("monHab"));
         }
         
         bdeSalCreTri = new BigDecimal("0");
         bdeSalCreTri = bdeMonDeb.subtract(bdeMonHab);
         
         rstLoc.close();
         rstLoc = null;
         stmLoc.close();
         stmLoc = null;
         
         strRes = "true";
      } //try
      
      catch(Exception e)
      {  
         strRes = "Error al generar el valor de Credito Tributario. " + "\n\n" + e.toString();
      }
      
      return strRes;
    } //Funcion getSQLCreditoTributario()
            
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if (objParSis.getCodigoMenu()==2795) { //Formulario 104
                    cargarDetReg104();
                } else if (objParSis.getCodigoMenu()==2789) {//Formulario 103
                    cargarDetReg103();
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
        try
        {
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCab!=null){
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
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                default:
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    if(objParSis.getCodigoMenu()==2789){//103
                                        strRutRpt=strRutRpt+"103/";
                                        strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    java.util.Map mapPar = new java.util.HashMap();
                                    //mapPar.put("strCamAudRpt", "" + (rstCab.getString("tx_nomUsrIng") + " / " + rstCab.getString("tx_nomUsrMod") + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("strCamAudRpt", "" + (objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("strConIvaVenBru", new BigDecimal(0));

                                    mapPar.put("strSinIvaVenBru", new BigDecimal(0));
                                    mapPar.put("strConIvaVenNet", new BigDecimal(0));
                                    mapPar.put("strSinIvaVenNet", new BigDecimal(0));
                                    //mapPar.put("fe_chq", objUti.formatearFecha("" + datFecAux, "yyyy-MM-dd", "dd 'de' MMMMM 'de' yyyy") );
                                    mapPar.put("strIvaVenNet", new BigDecimal(0));
                                    mapPar.put("strVenCon", new BigDecimal(0));
                                    mapPar.put("strVenCre", new BigDecimal(0));
                                    mapPar.put("strVenConIva", new BigDecimal(0));
                                    mapPar.put("strVenCreIva", new BigDecimal(0));
                                    mapPar.put("strImpLiqMesAnt", new BigDecimal(0));
                                   
                                    mapPar.put("strComValNet", new BigDecimal(0));
                                    mapPar.put("strComIva", new BigDecimal(0) );
                                    
                                    mapPar.put("strImpValNet", new BigDecimal(0));
                                    mapPar.put("strImpIva", new BigDecimal(0));
                                    
                                    mapPar.put("strComSinValNet", new BigDecimal(0));
                                    
                                    mapPar.put("strValBruPorPag", new BigDecimal(0));
                                    mapPar.put("strCreFisAnt", new BigDecimal(0));

                                    BigDecimal bdeValCreFisAct=new BigDecimal(0);
                                    BigDecimal bdeValPagSinRte=new BigDecimal(0);

                                    if(bdeValCreFisAct.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                                        mapPar.put("strCreFisAct", bdeValCreFisAct);
                                    }
                                    else
                                        mapPar.put("strCreFisAct", bdeValPagSinRte.multiply(new BigDecimal("-1")));

                                    mapPar.put("strRetFte10P", new BigDecimal(0));
                                    mapPar.put("strRetFte20P", new BigDecimal(0));
                                    mapPar.put("strRetFte30P", new BigDecimal(0));
                                    mapPar.put("strRetFte70P", new BigDecimal(0));
                                    mapPar.put("strRetFte100P", new BigDecimal(0));
                                    mapPar.put("strNomEmp", "" + objParSis.getNombreEmpresa());
                                    mapPar.put("strMesCor", "" +  objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "MM").toUpperCase() );
                                    mapPar.put("strAni", "" +  objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy").toUpperCase() );
                                    mapPar.put("strValPagNet", new BigDecimal(0));
                                    mapPar.put("strBasImpDevCom_cru", new BigDecimal(0));
                                    mapPar.put("strValIvaDevCom_cru", new BigDecimal(0));

                                    mapPar.put("strBasImpIvaVta12P", bdeBasImpIvaVta12==null? new BigDecimal("0"):bdeBasImpIvaVta12);
                                    mapPar.put("strValIvaVta12P", bdeValIvaVta12==null? new BigDecimal("0"):bdeValIvaVta12);
                                    mapPar.put("strVenCon12P", bdeBasImpIvaVtaCon12==null? new BigDecimal("0"):bdeBasImpIvaVtaCon12);
                                    mapPar.put("strVenConIva12P", bdeValIvaVtaCon12==null? new BigDecimal("0"):bdeValIvaVtaCon12);
                                    mapPar.put("strVenCre12P", bdeBasImpIvaVtaCre12==null? new BigDecimal("0"):bdeBasImpIvaVtaCre12);
                                    mapPar.put("strVenCreIva12P", bdeValIvaVtaCre12==null? new BigDecimal("0"):bdeValIvaVtaCre12);
                                    
                                    mapPar.put("strBasImpIvaVta14P", bdeBasImpIvaVta14==null? new BigDecimal("0"):bdeBasImpIvaVta14);
                                    mapPar.put("strValIvaVta14P", bdeValIvaVta14==null? new BigDecimal("0"):bdeValIvaVta14);
                                    mapPar.put("strVenCon14P", bdeBasImpIvaVtaCon14==null? new BigDecimal("0"):bdeBasImpIvaVtaCon14);
                                    mapPar.put("strVenConIva14P", bdeValIvaVtaCon14==null? new BigDecimal("0"):bdeValIvaVtaCon14);
                                    mapPar.put("strVenCre14P", bdeBasImpIvaVtaCre14==null? new BigDecimal("0"):bdeBasImpIvaVtaCre14);
                                    mapPar.put("strVenCreIva14P", bdeValIvaVtaCre14==null? new BigDecimal("0"):bdeValIvaVtaCre14);
                                    
                                    mapPar.put("strBasImpIvaCom12P", bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                    mapPar.put("strValIvaCom12P", bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                    
                                    BigDecimal bdeBaseImponibleNetaComprasIva = new BigDecimal(0);
                                    BigDecimal bdeIvaComprasIva = new BigDecimal(0);
                                    BigDecimal bdeNuevoValorstrBasImpIvaCom14P = null;
                                    BigDecimal bdeNuevoValorstrValIvaCom14P=null;
                                    //bdeNuevoValorstrBasImpIvaCom14P= bdeBaseImponibleNetaComprasIva.subtract(bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                    //bdeNuevoValorstrValIvaCom14P= bdeIvaComprasIva.subtract(bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                        mapPar.put("strBasImpIvaCom12P", bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                        mapPar.put("strValIvaCom12P", bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                        bdeNuevoValorstrBasImpIvaCom14P= bdeBaseImponibleNetaComprasIva.subtract(bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                        bdeNuevoValorstrValIvaCom14P= bdeIvaComprasIva.subtract(bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                    
                                    mapPar.put("strBasImpIvaCom14P", bdeNuevoValorstrBasImpIvaCom14P);
                                    mapPar.put("strValIvaCom14P", bdeNuevoValorstrValIvaCom14P);
                                    //mapPar.put("strBasImpIvaCom14P", bdeBasImpIvaCom14==null? new BigDecimal("0"):bdeBasImpIvaCom14);
                                    //mapPar.put("strValIvaCom14P", bdeValIvaCom14==null? new BigDecimal("0"):bdeValIvaCom14);
                                    
                                    mapPar.put("strCreTri10P", new BigDecimal(0));
                                    mapPar.put("strCreTri20P", new BigDecimal(0));
                                    mapPar.put("strCreTri30P", new BigDecimal(0));
                                    mapPar.put("strCreTri70P", new BigDecimal(0));
                                    mapPar.put("strCreTri100P", new BigDecimal(0));
                                    
                                    mapPar.put("strValComSol", new BigDecimal(0));
                                    mapPar.put("strValComSolCom", new BigDecimal(0));
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    }
                                    else if(objParSis.getCodigoMenu()==2795){//104
                                        
                                    if (chkForAnt.isSelected()) {
                                        strRutRpt=strRutRpt+"ZafCon59_Antiguo/";
                                    }
                                    Date datJun = new Date("06/01/2016");
                                    Date datJunFin = new Date("06/01/2017");
                                    //System.out.println("Primera Fecha : " + objSelFec.getFechaDesde());
                                    Date datSelFec = new Date(objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "MM/dd/yyyy").toUpperCase());
                                    if ((datSelFec.after(datJun) || datSelFec.equals(datJun))&& datSelFec.before(datJunFin) && !chkForAnt.isSelected() && objParSis.getCodigoEmpresa()!=2) {
                                        strRutRpt=strRutRpt+"Iva14/";
                                    }
                                    if ((datSelFec.after(datJun) || datSelFec.equals(datJun))&& datSelFec.before(datJunFin) && !chkForAnt.isSelected() && objParSis.getCodigoEmpresa()==2) {
                                        strRutRpt=strRutRpt+"Castek/";
                                    }
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    java.util.Map mapPar = new java.util.HashMap();
                                    //mapPar.put("strCamAudRpt", "" + (rstCab.getString("tx_nomUsrIng") + " / " + rstCab.getString("tx_nomUsrMod") + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("strCamAudRpt", "" + (objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("strConIvaVenBru", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString())));

                                    mapPar.put("strSinIvaVenBru", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString())));
                                    mapPar.put("strConIvaVenNet", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())));
                                    mapPar.put("strSinIvaVenNet", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString())));
                                    //mapPar.put("fe_chq", objUti.formatearFecha("" + datFecAux, "yyyy-MM-dd", "dd 'de' MMMMM 'de' yyyy") );
                                    mapPar.put("strIvaVenNet", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())));
                                    mapPar.put("strVenCon", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON).toString())));
                                    mapPar.put("strVenCre", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE).toString())));
                                    mapPar.put("strVenConIva", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString())));
                                    mapPar.put("strVenCreIva", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CRE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CRE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CRE).toString())));
                                    mapPar.put("strImpLiqMesAnt", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString())));
                                   
                                    mapPar.put("strComValNet", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())));
                                    mapPar.put("strComIva", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString())));
                                    
                                    mapPar.put("strImpValNet", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())));
                                    mapPar.put("strImpIva", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString())));
                                    
                                    mapPar.put("strComSinValNet", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())));
                                    
                                    mapPar.put("strValBruPorPag", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString())));
                                    mapPar.put("strCreFisAnt", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT).toString())));

                                    BigDecimal bdeValCreFisAct=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT).toString()));
                                    BigDecimal bdeValPagSinRte=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString()));

                                    if(bdeValCreFisAct.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                                        mapPar.put("strCreFisAct", bdeValCreFisAct);
                                    }
                                    else
                                        mapPar.put("strCreFisAct", bdeValPagSinRte.multiply(new BigDecimal("-1")));

                                    mapPar.put("strRetFte10P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_10P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_10P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_10P).toString())));
                                    mapPar.put("strRetFte20P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_20P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_20P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_20P).toString())));
                                    mapPar.put("strRetFte30P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString())));
                                    mapPar.put("strRetFte70P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString())));
                                    mapPar.put("strRetFte100P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString())));
                                    mapPar.put("strNomEmp", "" + objParSis.getNombreEmpresa());
                                    mapPar.put("strMesCor", "" +  objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "MM").toUpperCase() );
                                    mapPar.put("strAni", "" +  objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy").toUpperCase() );
                                    mapPar.put("strValPagNet", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET).toString())));
                                    mapPar.put("strBasImpDevCom_cru", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_BAS_IMP_DEV_COM_CRUP).toString())));
                                    mapPar.put("strValIvaDevCom_cru", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).toString())));

                                    mapPar.put("strBasImpIvaVta12P", bdeBasImpIvaVta12==null? new BigDecimal("0"):bdeBasImpIvaVta12);
                                    mapPar.put("strValIvaVta12P", bdeValIvaVta12==null? new BigDecimal("0"):bdeValIvaVta12);
                                    mapPar.put("strVenCon12P", bdeBasImpIvaVtaCon12==null? new BigDecimal("0"):bdeBasImpIvaVtaCon12);
                                    mapPar.put("strVenConIva12P", bdeValIvaVtaCon12==null? new BigDecimal("0"):bdeValIvaVtaCon12);
                                    mapPar.put("strVenCre12P", bdeBasImpIvaVtaCre12==null? new BigDecimal("0"):bdeBasImpIvaVtaCre12);
                                    mapPar.put("strVenCreIva12P", bdeValIvaVtaCre12==null? new BigDecimal("0"):bdeValIvaVtaCre12);
                                    
                                    mapPar.put("strBasImpIvaVta14P", bdeBasImpIvaVta14==null? new BigDecimal("0"):bdeBasImpIvaVta14);
                                    mapPar.put("strValIvaVta14P", bdeValIvaVta14==null? new BigDecimal("0"):bdeValIvaVta14);
                                    mapPar.put("strVenCon14P", bdeBasImpIvaVtaCon14==null? new BigDecimal("0"):bdeBasImpIvaVtaCon14);
                                    mapPar.put("strVenConIva14P", bdeValIvaVtaCon14==null? new BigDecimal("0"):bdeValIvaVtaCon14);
                                    mapPar.put("strVenCre14P", bdeBasImpIvaVtaCre14==null? new BigDecimal("0"):bdeBasImpIvaVtaCre14);
                                    mapPar.put("strVenCreIva14P", bdeValIvaVtaCre14==null? new BigDecimal("0"):bdeValIvaVtaCre14);
                                    
                                    mapPar.put("strBasImpIvaCom12P", bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                    mapPar.put("strValIvaCom12P", bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                    
                                    BigDecimal bdeBaseImponibleNetaComprasIva = new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString()));
                                    BigDecimal bdeIvaComprasIva = new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString()));
                                    BigDecimal bdeNuevoValorstrBasImpIvaCom14P = null;
                                    BigDecimal bdeNuevoValorstrValIvaCom14P=null;
                                    //bdeNuevoValorstrBasImpIvaCom14P= bdeBaseImponibleNetaComprasIva.subtract(bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                    //bdeNuevoValorstrValIvaCom14P= bdeIvaComprasIva.subtract(bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                    if ((datSelFec.after(datJun) || datSelFec.equals(datJun)) && datSelFec.before(datJunFin)) {
                                        mapPar.put("strBasImpIvaCom12P", bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                        mapPar.put("strValIvaCom12P", bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                        bdeNuevoValorstrBasImpIvaCom14P= bdeBaseImponibleNetaComprasIva.subtract(bdeBasImpIvaCom12==null? new BigDecimal("0"):bdeBasImpIvaCom12);
                                        bdeNuevoValorstrValIvaCom14P= bdeIvaComprasIva.subtract(bdeValIvaCom12==null? new BigDecimal("0"):bdeValIvaCom12);
                                    }else{
                                        mapPar.put("strBasImpIvaCom12P", bdeBaseImponibleNetaComprasIva==null? new BigDecimal("0"):bdeBaseImponibleNetaComprasIva);
                                        mapPar.put("strValIvaCom12P", bdeIvaComprasIva==null? new BigDecimal("0"):bdeIvaComprasIva);
                                        bdeNuevoValorstrBasImpIvaCom14P=new BigDecimal(0.00);
                                        bdeNuevoValorstrValIvaCom14P=new BigDecimal(0.00);
                                    }
                                    mapPar.put("strBasImpIvaCom14P", bdeNuevoValorstrBasImpIvaCom14P);
                                    mapPar.put("strValIvaCom14P", bdeNuevoValorstrValIvaCom14P);
                                    //mapPar.put("strBasImpIvaCom14P", bdeBasImpIvaCom14==null? new BigDecimal("0"):bdeBasImpIvaCom14);
                                    //mapPar.put("strValIvaCom14P", bdeValIvaCom14==null? new BigDecimal("0"):bdeValIvaCom14);
                                    
                                    mapPar.put("strCreTri10P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_10P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_10P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_10P).toString())));
                                    mapPar.put("strCreTri20P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_20P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_20P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_20P).toString())));
                                    mapPar.put("strCreTri30P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_30P).toString())));
                                    mapPar.put("strCreTri50P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_50P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_50P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_50P).toString())));
                                    mapPar.put("strCreTri70P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_70P).toString())));
                                    mapPar.put("strCreTri100P", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_TRI_100P).toString())));
                                    
                                    mapPar.put("strValComSol", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_COM_SOL_VEN)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_COM_SOL_VEN).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_COM_SOL_VEN).toString())));
                                    mapPar.put("strValComSolCom", new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_COM_SOL_COM)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_COM_SOL_COM).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_COM_SOL_COM).toString())));
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    }
                                    
                                    break;
                            }
                        }
                    }
                }
                conCab.close();
                conCab=null;
            }

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }


    private boolean cargarFormulario103(){
        boolean blnRes=true;
        // TODO add your handling code here:
        String strRucEmp="";
        String strMesCor="";
        String strAniCor="";
        String strNomEmp="";
        String strNomFilXml="";
        PrintWriter xml;

        try{
            //PrintWriter xml = new PrintWriter(new FileOutputStream("archivo.xml"));

            strMesCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "M");
            strAniCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy");


            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, tx_ruc, tx_nom AS tx_nomEmp";
                strSQL+="   FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strRucEmp=rst.getString("tx_ruc");
                    strNomEmp=rst.getString("tx_nomEmp");
                }


                strNomFilXml="103" + ( (txtNumSus.getText().length()>0)?"SUS":"ORI") + "_" + (objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "MMMM")).toUpperCase() + (strAniCor) + strNomEmp + "";
                //PrintWriter xml = new PrintWriter(new FileOutputStream(strNomFilXml + ".xml"));


                if(System.getProperty("os.name").equals("Linux")){
                    xml = new PrintWriter(new FileOutputStream("/tmp/Declaraciones_Zafiro/" + strNomEmp + "/" + strAniCor + "/Formulario_103/" + strNomFilXml + ".xml"));
                    strRutFilGen="" + ( "/tmp/Declaraciones_Zafiro/" + strNomEmp + "/" + strAniCor + "/Formulario_103/" + strNomFilXml + ".xml" );
                }
                else{
                    xml = new PrintWriter(new FileOutputStream("D:\\Declaraciones_Zafiro\\" + strNomEmp + "\\" + strAniCor + "\\Formulario_103\\" + strNomFilXml + ".xml"));
                    strRutFilGen="" + ("D:\\Declaraciones_Zafiro\\" + strNomEmp + "\\" + strAniCor + "\\Formulario_103\\" + strNomFilXml + ".xml");
                }



                strSQL="";
                strSQL+="SELECT x.CODRETF, SUM(x.BASERET) AS BASERET, SUM(x.VALRETF) AS VALRETF FROM(";
                strSQL+=" 	SELECT a3.tx_codsri AS CODRETF, SUM(a2.nd_abo)*(-1) AS VALRETF ,SUM(a3.nd_basImp) AS BASERET";
                strSQL+=" 	FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ";
                strSQL+="         ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc)";
                strSQL+="         INNER JOIN tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
                strSQL+=" 	LEFT OUTER JOIN tbm_datautsri AS b3";
                strSQL+=" 	 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)";
                strSQL+=" 	INNER JOIN tbm_detPag AS a2 	";
                strSQL+="         ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbm_pagMovInv AS a3";
                strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc ";
                strSQL+="         AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strSQL+=" 	INNER JOIN tbm_cabMovInv AS a4";
                strSQL+=" 	 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.co_tipDoc IN(33,160,158,230)";
                strSQL+="                          AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" 	AND a3.co_tipRet IN(10,7,9,1,11,8,2,3,12,4,5,6)";
                strSQL+=" 	AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I') AND a3.tx_codsri NOT IN('')";
                strSQL+=" 	GROUP BY a3.tx_codsri ,a3.tx_fecCad ,a3.nd_porRet";
                strSQL+=" 	ORDER BY a3.tx_codsri";
                strSQL+=" ) AS x";
                strSQL+=" GROUP BY x.CODRETF";
                //strSQL+=" ORDER BY x.CODRETF";

                strSQL+=" UNION";

                strSQL+=" select a2.tx_codSri AS CODRETF, a2.nd_basImp AS BASERET, (a2.nd_basImp*0.12) AS VALRETF";
                strSQL+=" from (  ( tbm_cabmovinv AS a1 ";
                strSQL+=" 	     INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc )";
                strSQL+=" 	 INNER JOIN tbm_cli AS a4 ON a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli";
                strSQL+="      )";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" AND a2.nd_porRet=0 AND a2.tx_aplRet='O' AND a2.nd_basImp>0";
                strSQL+=" GROUP BY a2.tx_codSri, a2.nd_basImp";
                strSQL+=" ORDER BY CODRETF";
                System.out.println("SQL     CODRETF   -: " + strSQL);


                rst=stm.executeQuery(strSQL);

                strSQL="";
                strSQL+="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>	";
                strSQL+="	<formulario version=\"0.2\">	";
                strSQL+="	    <cabecera>	";
                //<codigo_version_formulario>03201202        ->   cod.Formulario=03  año=2012(no se si es año de declaración o de programa)  02(versión actual, antes era 01)
                strSQL+="	        <codigo_version_formulario>03201202</codigo_version_formulario>	";
                strSQL+="	        <ruc>" +  strRucEmp + " </ruc>	";
                strSQL+="	        <codigo_moneda>1</codigo_moneda>	";
                strSQL+="	    </cabecera>	";
                strSQL+="	    <detalle>	";
                if(txtNumSus.getText().length()>0)
                    strSQL+="	        <campo numero=\"31\">S</campo>	";//sustitutiva
                else
                    strSQL+="	        <campo numero=\"31\">O</campo>	";//original
                strSQL+="	        <campo numero=\"101\">" + strMesCor + "</campo>	";
                strSQL+="	        <campo numero=\"102\">" + strAniCor + "</campo>	";
                if(txtNumSus.getText().length()>0)
                    strSQL+="	        <campo numero=\"104\">" + txtNumSus.getText() + "</campo>	";
                else
                    strSQL+="	        <campo numero=\"104\"></campo>	";
                strSQL+="	        <campo numero=\"198\">" + txtRucRepLeg.getText() + "</campo>	";//1706487236
                strSQL+="	        <campo numero=\"199\">" + txtRucConGrl.getText() + "</campo>	";//0908651268001
                strSQL+="	        <campo numero=\"201\">" + strRucEmp + "</campo>	";
                strSQL+="	        <campo numero=\"202\"><![CDATA[" + strNomEmp + "]]></campo>	";
                String strCodRetFte="";
                BigDecimal bdeBasImp=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeTotBasImp=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValRet=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeTotValRet=new BigDecimal(BigInteger.ZERO);


                arlDat103CodRetFte.clear();
                while(rst.next()){
                    strCodRetFte=rst.getObject("CODRETF")==null?"":rst.getString("CODRETF");
                    bdeBasImp=objUti.redondearBigDecimal((rst.getObject("BASERET")==null?"0":rst.getString("BASERET")),objParSis.getDecimalesMostrar());
                    bdeValRet=objUti.redondearBigDecimal((rst.getObject("VALRETF")==null?"0":rst.getString("VALRETF")),objParSis.getDecimalesMostrar());

                    arlReg103CodRetFte=new ArrayList();
                    arlReg103CodRetFte.add(INT_ARL_103_COD_RTE_FTE, strCodRetFte);
                    arlReg103CodRetFte.add(INT_ARL_103_BAS_IMP, bdeBasImp);
                    arlReg103CodRetFte.add(INT_ARL_103_VAL_RTE, bdeValRet);
                    arlReg103CodRetFte.add(INT_ARL_103_EST_REG, "");
                    arlDat103CodRetFte.add(arlReg103CodRetFte);

                }

                String strEstPro="";
                //302
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("302")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("302")){
                        strSQL+="	        <campo numero=\"302\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"302\">0.00</campo>	";


                //303
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("303")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("303")){
                        strSQL+="	        <campo numero=\"303\">" + bdeBasImp + " </campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"303\">0.00</campo>	";

                //304
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("304")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("304")){
                        strSQL+="	        <campo numero=\"304\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                            strSQL+="	        <campo numero=\"304\">0.00</campo>	";

                //307
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("307")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("307")){
                        strSQL+="	        <campo numero=\"307\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"307\">0.00</campo>	";

                //308
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("308")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("308")){
                        strSQL+="	        <campo numero=\"308\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"308\">0.00</campo>	";


                //309
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("309")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("309")){
                        strSQL+="	        <campo numero=\"309\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"309\">0.00</campo>	";


                //310
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("310")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("310")){
                        strSQL+="	        <campo numero=\"310\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"310\">0.00</campo>	";

                //312
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("312")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("312")){
                        strSQL+="	        <campo numero=\"312\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"312\">0.00</campo>	";


                //319
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("319")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("319")){
                        strSQL+="	        <campo numero=\"319\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"319\">0.00</campo>	";

                //320
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("320")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("320")){
                        strSQL+="	        <campo numero=\"320\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"320\">0.00</campo>	";

                //322
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("322")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("322")){
                        strSQL+="	        <campo numero=\"322\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"322\">0.00</campo>	";

                //323
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("323")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }

                    if(strCodRetFte.equals("323")){
                        strSQL+="	        <campo numero=\"323\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"323\">0.00</campo>	";

                //325
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("325")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("325")){
                        strSQL+="	        <campo numero=\"325\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"325\">0.00</campo>	";

                //327
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("327")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("327")){
                        strSQL+="	        <campo numero=\"327\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"327\">0.00</campo>	";

                //328
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("328")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("328")){
                        strSQL+="	        <campo numero=\"328\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"328\">0.00</campo>	";

                //332
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("332")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("332")){
                        strSQL+="	        <campo numero=\"332\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"332\">0.00</campo>	";

                //340
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("340")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("340")){
                        strSQL+="	        <campo numero=\"340\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"340\">0.00</campo>	";

                //341
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("341")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("341")){
                        strSQL+="	        <campo numero=\"341\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"341\">0.00</campo>	";

                //342
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("342")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("342")){
                        strSQL+="	        <campo numero=\"342\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"342\">0.00</campo>	";

                //343
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("343")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("343")){
                        strSQL+="	        <campo numero=\"343\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"343\">0.00</campo>	";

                //344
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("344")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("344")){
                        strSQL+="	        <campo numero=\"344\">" + bdeBasImp + "</campo>	";
                        bdeTotBasImp=bdeTotBasImp.add(bdeBasImp);
                    }
                    else
                        strSQL+="	        <campo numero=\"344\">0.00</campo>	";


                    bdeTotBasImp=objUti.redondearBigDecimal(bdeTotBasImp, objParSis.getDecimalesMostrar());
                    strSQL+="	        <campo numero=\"349\">" + bdeTotBasImp + "</campo>	";



                //VALOR RETENIDO
                for(int i=0;i<arlDat103CodRetFte.size(); i++){
                    //se quita el estado de procesado porque cada registro contiene base y valor retenido y ya lo procesamos por base impon.
                    objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "");
                }
                System.out.println("valor retenido: " + arlDat103CodRetFte.toString());


                //352
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("302")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("302")){
                        strSQL+="	        <campo numero=\"352\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"352\">0.00</campo>	";

                //353
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("303")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("303")){
                        strSQL+="	        <campo numero=\"353\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"353\">0.00</campo>	";


                //354
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("304")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("304")){
                        strSQL+="	        <campo numero=\"354\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"354\">0.00</campo>	";

                //357
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("307")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("307")){
                        strSQL+="	        <campo numero=\"357\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"357\">0.00</campo>	";

                //358
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("308")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("308")){
                        strSQL+="	        <campo numero=\"358\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"358\">0.00</campo>	";

                //359
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("309")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("309")){
                        strSQL+="	        <campo numero=\"359\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"359\">0.00</campo>	";

                //360
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("310")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("310")){
                        strSQL+="	        <campo numero=\"360\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"360\">0.00</campo>	";

                //362
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("312")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("312")){
                        strSQL+="	        <campo numero=\"362\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"362\">0.00</campo>	";

                //369
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("319")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("319")){
                        strSQL+="	        <campo numero=\"369\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"369\">0.00</campo>	";

                //370
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("320")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("320")){
                        strSQL+="	        <campo numero=\"370\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"370\">0.00</campo>	";

                //372
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("322")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("322")){
                        strSQL+="	        <campo numero=\"372\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"372\">0.00</campo>	";

                //373
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("323")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("323")){
                        strSQL+="	        <campo numero=\"373\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"373\">0.00</campo>	";

                //375
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("325")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("325")){
                        strSQL+="	        <campo numero=\"375\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"375\">0.00</campo>	";

                //377
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("327")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("327")){
                       strSQL+="	        <campo numero=\"377\">" + bdeValRet + "</campo>	";
                       bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"377\">0.00</campo>	";

                //378
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("328")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("328")){
                        strSQL+="	        <campo numero=\"378\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"378\">0.00</campo>	";

                //390
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("340")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("340")){
                        strSQL+="	        <campo numero=\"390\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"390\">0.00</campo>	";

                //391
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("341")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("341")){
                        strSQL+="	        <campo numero=\"391\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"391\">0.00</campo>	";

                //392
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("342")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("342")){
                        strSQL+="	        <campo numero=\"392\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"392\">0.00</campo>	";

                //393
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("343")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("343")){
                        strSQL+="	        <campo numero=\"393\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"393\">0.00</campo>	";

                //394
                    strCodRetFte="";
                    bdeBasImp=new BigDecimal(BigInteger.ZERO);
                    bdeValRet=new BigDecimal(BigInteger.ZERO);
                    strEstPro="";
                    for(int i=0;i<arlDat103CodRetFte.size(); i++){
                        strCodRetFte=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_COD_RTE_FTE);
                        strEstPro=objUti.getStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG);
                        if(strEstPro.equals("")){
                            if(strCodRetFte.equals("344")){
                                bdeBasImp=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_BAS_IMP);
                                bdeValRet=objUti.getBigDecimalValueAt(arlDat103CodRetFte, i, INT_ARL_103_VAL_RTE);
                                objUti.setStringValueAt(arlDat103CodRetFte, i, INT_ARL_103_EST_REG, "P");
                                break;
                            }
                        }
                    }
                    if(strCodRetFte.equals("344")){
                        strSQL+="	        <campo numero=\"394\">" + bdeValRet + "</campo>	";
                        bdeTotValRet=bdeTotValRet.add(bdeValRet);
                    }
                    else
                        strSQL+="	        <campo numero=\"394\">0.00</campo>	";



                    bdeTotValRet=objUti.redondearBigDecimal(bdeTotValRet, objParSis.getDecimalesMostrar());

                    strSQL+="	        <campo numero=\"399\">" + bdeTotValRet + "</campo>	";

                    //por pagos al exterior

                    strSQL+="	        <campo numero=\"401\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"403\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"405\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"421\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"427\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"429\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"451\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"453\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"455\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"471\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"498\">0.00</campo>	";

                    strSQL+="	        <campo numero=\"499\">" + bdeTotValRet + "</campo>	";
                    strSQL+="	        <campo numero=\"890\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"897\">" + objUti.redondearBigDecimal((txtPagImpAnt.getText().equals("")?"0":txtPagImpAnt.getText()), objParSis.getDecimalesMostrar()) + "</campo>	";
                    strSQL+="	        <campo numero=\"898\">" + objUti.redondearBigDecimal((txtPagInt.getText().equals("")?"0":txtPagInt.getText()), objParSis.getDecimalesMostrar()) + "</campo>	";
                    strSQL+="	        <campo numero=\"899\">" + objUti.redondearBigDecimal((txtPagMul.getText().equals("")?"0":txtPagMul.getText()), objParSis.getDecimalesMostrar()) + "</campo>	";
                    strSQL+="	        <campo numero=\"902\">" + bdeTotValRet.subtract(objUti.redondearBigDecimal((txtPagMul.getText().equals("")?"0":txtPagMul.getText()), objParSis.getDecimalesMostrar())) + "</campo>	";
                    strSQL+="	        <campo numero=\"903\">" + objUti.redondearBigDecimal((txtIntMorSus.getText().equals("")?"0":txtIntMorSus.getText()), objParSis.getDecimalesMostrar()) + "</campo>	";
                    strSQL+="	        <campo numero=\"904\">" + objUti.redondearBigDecimal((txtMulSus.getText().equals("")?"0":txtMulSus.getText()), objParSis.getDecimalesMostrar()) + "</campo>	";
                    strSQL+="	        <campo numero=\"905\">" + objUti.redondearBigDecimal((bdeTotValRet.subtract(objUti.redondearBigDecimal((txtPagMul.getText().equals("")?"0":txtPagMul.getText()), objParSis.getDecimalesMostrar()))).add(objUti.redondearBigDecimal((txtIntMorSus.getText().equals("")?"0":txtIntMorSus.getText()), objParSis.getDecimalesMostrar())).add(objUti.redondearBigDecimal((txtMulSus.getText().equals("")?"0":txtMulSus.getText()), objParSis.getDecimalesMostrar())), objParSis.getDecimalesMostrar()) + "</campo>	";
                    strSQL+="	        <campo numero=\"907\">0.00</campo>	";
                    strSQL+="	        <campo numero=\"908\">" + txtNumNotCre1.getText() + "</campo>	";
                    strSQL+="	        <campo numero=\"909\">" + objUti.redondearBigDecimal((txtValNotCre1.getText().equals("")?"0":txtValNotCre1.getText()), objParSis.getDecimalesMostrar()) + "</campo>";
                    strSQL+="	        <campo numero=\"910\">" + txtNumNotCre2.getText() + "</campo>	";
                    strSQL+="	        <campo numero=\"911\">" + objUti.redondearBigDecimal((txtValNotCre2.getText().equals("")?"0":txtValNotCre2.getText()), objParSis.getDecimalesMostrar()) + "</campo>";
                    strSQL+="	        <campo numero=\"912\"></campo>	";
                    strSQL+="	        <campo numero=\"913\">0.00</campo>";
//                    strSQL+="	        <campo numero=\"914\"></campo>	";
                    strSQL+="	        <campo numero=\"915\">0.00</campo>";
                    if(cboForPag.getSelectedIndex()==0)//Banco Machala(25)
                        strSQL+="	        <campo numero=\"922\">25</campo>	";
                    else//Banco Pacifico(30)
                        strSQL+="	        <campo numero=\"922\">30</campo>	";

                    strSQL+="	        <campo numero=\"999\">" + objUti.redondearBigDecimal((bdeTotValRet.subtract(objUti.redondearBigDecimal((txtPagMul.getText().equals("")?"0":txtPagMul.getText()), objParSis.getDecimalesMostrar()))).add(objUti.redondearBigDecimal((txtIntMorSus.getText().equals("")?"0":txtIntMorSus.getText()), objParSis.getDecimalesMostrar())).add(objUti.redondearBigDecimal((txtMulSus.getText().equals("")?"0":txtMulSus.getText()), objParSis.getDecimalesMostrar())), objParSis.getDecimalesMostrar()) + "</campo>	";


                strSQL+="	    </detalle>	";
                strSQL+="	</formulario>	";
                xml.println(strSQL);



                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;


                xml.println();
                xml.close();

            }

            strMesCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "M");
            strAniCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy");





    }
    catch(Exception e){
        objUti.mostrarMsgErr_F1(this, e);
        blnRes=false;
    }
        return blnRes;
}



    private boolean cargarFormulario104(){
        boolean blnRes=true;
        // TODO add your handling code here:
        String strRucEmp="";
        String strMesCor="";
        String strAniCor="";
        String strNomEmp="";
        String strNomFilXml="";
        PrintWriter xml;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, tx_ruc, tx_nom AS tx_nomEmp";
                strSQL+="   FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strRucEmp=rst.getString("tx_ruc");
                    strNomEmp=rst.getString("tx_nomEmp");
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

            strMesCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "M");
            strAniCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy");

            strNomFilXml="104" + ( (txtNumSus.getText().length()>0)?"SUS":"ORI") + "_" + (objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "MMMM")).toUpperCase() + (strAniCor) + strNomEmp + "";


            //PrintWriter xml = new PrintWriter(new FileOutputStream(strNomFilXml + ".xml"));

            if(System.getProperty("os.name").equals("Linux")){
                xml = new PrintWriter(new FileOutputStream("/tmp/Declaraciones_Zafiro/" + strNomEmp + "/" + strAniCor + "/Formulario_104/" + strNomFilXml + ".xml"));
                strRutFilGen="" + ( "/tmp/Declaraciones_Zafiro/" + strNomEmp + "/" + strAniCor + "/Formulario_104/" + strNomFilXml + ".xml" );
            }
            else{
                xml = new PrintWriter(new FileOutputStream("D:\\Declaraciones_Zafiro\\" + strNomEmp + "\\" + strAniCor + "\\Formulario_104\\" + strNomFilXml + ".xml"));
                strRutFilGen="" + ("D:\\Declaraciones_Zafiro\\" + strNomEmp + "\\" + strAniCor + "\\Formulario_104\\" + strNomFilXml + ".xml");
            }



            strSQL="";
            strSQL+="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
            strSQL+="	 <formulario version=\"0.2\">	";
            strSQL+="	 <cabecera>	";
            //<codigo_version_formulario>04201201        ->   cod.Formulario=04  año=2012(no se si es año de declaración o de programa)  01(versión actual empieza desde 01)
            strSQL+="	  <codigo_version_formulario>04201201</codigo_version_formulario> 	";
            strSQL+="	  <ruc>" + strRucEmp + "</ruc> 	";
            strSQL+="	  <codigo_moneda>1</codigo_moneda> 	";
            strSQL+="	  </cabecera>	";
            strSQL+="	 <detalle>	";
            if(txtNumSus.getText().length()>0)
                strSQL+="	  <campo numero=\"31\">S</campo> 	";//sustitutiva
            else
                strSQL+="	  <campo numero=\"31\">O</campo> 	";//original
            strSQL+="	  <campo numero=\"101\">" + strMesCor + "</campo> 	";
            strSQL+="	  <campo numero=\"102\">" + strAniCor + "</campo> 	";
            if(txtNumSus.getText().length()>0)
                strSQL+="	  <campo numero=\"104\">" + txtNumSus.getText() + "</campo>";
            else
                strSQL+="	  <campo numero=\"104\" /> 	";
            strSQL+="	  <campo numero=\"198\">" + txtRucRepLeg.getText() + "</campo> 	";
            //strSQL+="	  <campo numero=\"199\">" + txtRucConGrl.getText() + "</campo> 	";
            strSQL+="	  <campo numero=\"201\">" + strRucEmp + "</campo> 	";
            strSQL+="	 <campo numero=\"202\">";
            strSQL+="" + strNomEmp + "";
            strSQL+="	  </campo>	";
            strSQL+="	  <campo numero=\"401\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"402\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"403\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"404\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"405\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";//INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU
            strSQL+="	  <campo numero=\"406\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"407\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"408\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"409\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"411\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"412\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"413\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"414\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"415\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"416\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"417\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"418\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"419\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"421\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"422\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"429\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"431\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"432\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"433\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"434\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"443\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"444\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"480\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"481\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"482\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"483\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"484\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"485\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()).subtract(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
            strSQL+="	  <campo numero=\"499\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString())), objParSis.getDecimalesMostrar()).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
            strSQL+="	  <campo numero=\"501\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"502\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"503\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"504\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"505\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"506\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";//
            strSQL+="	  <campo numero=\"507\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"509\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar())).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
            strSQL+="	  <campo numero=\"511\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"512\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"513\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"514\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"515\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"516\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"517\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"518\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"519\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar())).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
            strSQL+="	  <campo numero=\"521\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"522\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"523\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"524\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"525\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"529\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"531\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"532\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"533\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"534\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"535\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"544\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"545\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"553\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString()))).divide((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString())))), 0, BigDecimal.ROUND_UP),4)   + "</campo> 	";
            strSQL+="	  <campo numero=\"554\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString()))).add((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString())))).multiply(((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString()))).divide((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString())))), 0, BigDecimal.ROUND_UP))), objParSis.getDecimalesMostrar()) + "</campo> 	";
            BigDecimal bdeValPagBru=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString()));
            if(bdeValPagBru.compareTo(new BigDecimal(BigInteger.ZERO))>0){//valor a pagar es un valor(601 tiene el valor y 602 es 0), credito tributario(602 es un valor y 601 es 0)
                strSQL+="	  <campo numero=\"601\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"602\">0.00</campo> 	";
            }
            else{
                strSQL+="	  <campo numero=\"601\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"602\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString())), objParSis.getDecimalesMostrar()).abs() + "</campo> 	";
            }


//            strSQL+="	  <campo numero=\"603\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"604\">0.00</campo> 	";

            strSQL+="	  <campo numero=\"605\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
//            strSQL+="	  <campo numero=\"606\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"607\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"608\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"609\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"610\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"611\">0.00</campo> 	";

//            strSQL+="	  <campo numero=\"612\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"613\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"614\">0.00</campo> 	";

            strSQL+="	  <campo numero=\"615\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
//            strSQL+="	  <campo numero=\"616\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"617\">0.00</campo> 	";

//            strSQL+="	  <campo numero=\"618\">0.00</campo> 	";
            BigDecimal bdeValPagSinRte=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString()));
            if(bdeValPagSinRte.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                strSQL+="	  <campo numero=\"619\">" + objUti.redondearBigDecimal(bdeValPagSinRte, objParSis.getDecimalesMostrar()) + "</campo> 	";//INT_TBL_DAT_VAL_PAG_SIN_RTE
            else
                strSQL+="	  <campo numero=\"619\">0.00</campo> 	";//INT_TBL_DAT_VAL_PAG_SIN_RTE

//            strSQL+="	  <campo numero=\"620\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"621\">0.00</campo> 	";





//            strSQL+="	  <campo numero=\"622\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"623\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"624\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"625\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"626\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"627\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"628\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"629\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"630\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"631\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"632\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"633\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"634\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"635\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"636\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"637\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"638\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"639\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"640\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"641\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"642\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"643\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"644\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"645\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"646\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"647\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"648\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"649\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"650\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"651\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"652\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"653\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"654\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"655\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"656\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"657\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"658\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"659\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"660\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"661\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"662\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"663\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"664\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"665\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"666\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"667\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"668\">0.00</campo> 	";            
//            strSQL+="	  <campo numero=\"669\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"670\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"671\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"672\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"673\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"674\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"675\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"676\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"677\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"678\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"679\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"680\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"681\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"682\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"683\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"684\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"685\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"686\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"687\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"688\">0.00</campo> 	";
            
//            strSQL+="	  <campo numero=\"689\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"690\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"691\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"692\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"693\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"694\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"695\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"696\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"697\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"698\">0.00</campo> 	";

            if(bdeValPagSinRte.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                strSQL+="	  <campo numero=\"699\">" + objUti.redondearBigDecimal(bdeValPagSinRte, objParSis.getDecimalesMostrar()) + "</campo> 	";//INT_TBL_DAT_VAL_PAG_SIN_RTE
            else
                strSQL+="	  <campo numero=\"699\">0.00</campo> 	";


//            strSQL+="	  <campo numero=\"700\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"701\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"702\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"703\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"704\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"705\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"706\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"707\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"708\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"709\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"710\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"711\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"712\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"713\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"714\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"715\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"716\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"717\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"718\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"719\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"720\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"721\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
//            strSQL+="	  <campo numero=\"722\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"723\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
//            strSQL+="	  <campo numero=\"724\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"725\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
//            strSQL+="	  <campo numero=\"726\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"727\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"728\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"729\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"730\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"731\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"732\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"733\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"734\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"735\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"736\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"737\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"738\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"739\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"740\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"741\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"742\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"743\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"744\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"745\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"746\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"747\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"748\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"749\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"750\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"751\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"752\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"753\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"754\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"755\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"756\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"757\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"758\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"759\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"760\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"761\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"762\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"763\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"764\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"765\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"766\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"767\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"768\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"769\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"770\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"771\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"772\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"773\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"774\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"775\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"776\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"777\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"778\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"779\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"780\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"781\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"782\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"783\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"784\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"785\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"786\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"787\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"788\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"789\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"790\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"791\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"792\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"793\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"794\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"795\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"796\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"797\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"798\">0.00</campo> 	";

            strSQL+="	  <campo numero=\"799\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";

//            strSQL+="	  <campo numero=\"800\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"801\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"802\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"803\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"804\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"805\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"806\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"807\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"808\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"809\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"810\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"811\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"812\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"813\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"814\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"815\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"816\">0.00</campo> 	";

//            strSQL+="	  <campo numero=\"817\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"818\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"819\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"820\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"821\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"822\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"823\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"824\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"825\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"826\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"827\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"828\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"829\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"830\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"831\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"832\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"833\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"834\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"835\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"836\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"837\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"838\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"839\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"840\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"841\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"842\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"843\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"844\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"845\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"846\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"847\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"848\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"849\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"850\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"851\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"852\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"853\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"854\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"855\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"856\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"857\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"858\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"859\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
//            strSQL+="	  <campo numero=\"860\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"861\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"862\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"863\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"864\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"865\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"866\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"867\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"868\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"869\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"870\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"871\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"872\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"873\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"874\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"875\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"876\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"877\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"878\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"879\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"880\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"881\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"882\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"883\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"884\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"885\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"886\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"887\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"888\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"889\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"890\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"891\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"892\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"893\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"894\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"895\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"896\">0.00</campo> 	";

            strSQL+="	  <campo numero=\"897\">" + objUti.redondearBigDecimal((txtPagImpAnt.getText().equals("")?"0":txtPagImpAnt.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"898\">" + objUti.redondearBigDecimal((txtPagInt.getText().equals("")?"0":txtPagInt.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"899\">" + objUti.redondearBigDecimal((txtPagMul.getText().equals("")?"0":txtPagMul.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";

//            strSQL+="	  <campo numero=\"900\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"901\">0.00</campo> 	";

            strSQL+="	  <campo numero=\"902\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"903\">" + objUti.redondearBigDecimal((txtIntMorSus.getText().equals("")?"0":txtIntMorSus.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"904\">" + objUti.redondearBigDecimal((txtMulSus.getText().equals("")?"0":txtMulSus.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"905\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"906\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"907\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"908\">" + txtNumNotCre1.getText() + "</campo>	";
            strSQL+="	  <campo numero=\"909\">" + objUti.redondearBigDecimal((txtValNotCre1.getText().equals("")?"0":txtValNotCre1.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  <campo numero=\"910\">" + txtNumNotCre2.getText() + "</campo>	";
            strSQL+="	  <campo numero=\"911\">" + objUti.redondearBigDecimal((txtValNotCre2.getText().equals("")?"0":txtValNotCre2.getText()), objParSis.getDecimalesMostrar()) + "</campo>";
            strSQL+="	  <campo numero=\"912\" /> 	";
            strSQL+="	  <campo numero=\"913\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"914\" /> 	";
            strSQL+="	  <campo numero=\"915\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"916\" /> 	";
            strSQL+="	  <campo numero=\"917\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"918\" /> 	";
            strSQL+="	  <campo numero=\"919\">0.00</campo> 	";

//            strSQL+="	  <campo numero=\"920\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"921\">0.00</campo> 	";

            if(cboForPag.getSelectedIndex()==0)//Banco Machala(25)
                strSQL+="	  <campo numero=\"922\">25</campo> 	";
            else//Banco Pacifico(30)
                strSQL+="	  <campo numero=\"922\">30</campo>	";



//            strSQL+="	  <campo numero=\"923\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"924\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"925\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"926\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"927\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"928\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"929\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"930\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"931\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"932\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"933\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"934\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"935\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"936\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"937\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"938\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"939\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"940\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"941\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"942\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"943\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"944\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"945\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"946\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"947\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"948\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"949\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"950\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"951\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"952\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"953\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"954\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"955\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"956\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"957\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"958\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"959\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"960\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"961\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"962\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"963\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"964\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"965\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"966\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"967\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"968\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"969\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"970\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"971\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"972\">0.00</campo> 	";



//            strSQL+="	  <campo numero=\"973\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"974\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"975\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"976\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"977\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"978\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"979\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"980\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"981\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"982\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"983\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"984\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"985\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"986\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"987\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"988\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"989\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"990\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"991\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"992\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"993\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"994\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"995\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"996\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"997\">0.00</campo> 	";
//            strSQL+="	  <campo numero=\"998\">0.00</campo> 	";
            strSQL+="	  <campo numero=\"999\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
            strSQL+="	  </detalle>	";
            strSQL+="	  </formulario>	";
            System.out.println("strSQL: " + strSQL);


            xml.println(strSQL);




            xml.println();
            xml.close();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean cargarDatosLegalesEmpresa(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.tx_iderepleg, a1.tx_nomrepleg, a1.tx_idecongen, a1.tx_nomcongen";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    txtRucRepLeg.setText(rst.getString("tx_iderepleg"));
                    txtNomRepLeg.setText(rst.getString("tx_nomrepleg"));
                    txtRucConGrl.setText(rst.getString("tx_idecongen"));
                    txtNomConGrl.setText(rst.getString("tx_nomcongen"));
                }
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;

            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private String getTipoDxP(){
        String strTipDocUsr="";
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            if(con!=null){
                stmTmp=con.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT co_tipdoc FROM tbr_tipdocprg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipdoc FROM tbr_tipdocprg AS a1";
                    strSQL+=" INNER JOIN tbr_tipdocusr AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_mnu=a2.co_mnu";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                rstTmp=stmTmp.executeQuery(strSQL);
                for(int i=0; rstTmp.next();i++){
                    if(i==0)
                        strTipDocUsr="" + (rstTmp.getObject("co_tipdoc")==null?"":rstTmp.getString("co_tipdoc"));
                    else
                        strTipDocUsr+="," + (rstTmp.getObject("co_tipdoc")==null?"":rstTmp.getString("co_tipdoc"));
                }
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;

            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strTipDocUsr;
    }
    
    
    
private int obtenerAni(String strFec){
         SimpleDateFormat sdfFil = new SimpleDateFormat("yyyy-MM-dd");
         java.util.Date objDat = new java.util.Date();
         int intAnio=0;
          try {
            objDat = sdfFil.parse(strFec);
            Calendar objCal = new GregorianCalendar();
            objCal.setTime(objDat);
            intAnio = objCal.get(Calendar.YEAR);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
          return intAnio;
    }
    private int obtenerMes(String strFec){
         SimpleDateFormat sdfFil = new SimpleDateFormat("yyyy-MM-dd");
         java.util.Date objDat = new java.util.Date();
         int intMes=0;
          try {
            objDat = sdfFil.parse(strFec);
            Calendar objCal = new GregorianCalendar();
            objCal.setTime(objDat);
            intMes =objCal.get(Calendar.MONTH)+1;;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
          return intMes;
    }
    
    private boolean verificaExistePer(int intAniDes, int intMesDes, int intAniHas, int intMesHas){
    boolean blnRes=false;
        if (intAniDes==intAniHas && intMesDes==intMesHas) {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        String strSql;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                    strSql="";
                    strSql+="SELECT co_emp, ne_ani, ne_mes, nd_valbruconiva, nd_valnetconiva, nd_valbrusiniva, \n";
                    strSql+=        "       nd_valnetsiniva, nd_valivaven, nd_valvencon, nd_valvencre, nd_valivavencon, \n";
                    strSql+=        "       nd_valivavencre, nd_valvencremesant, nd_valcomconiva, nd_valivacom, \n";
                    strSql+=        "       nd_valcomsiniva, nd_valimp, nd_valivaimp, nd_valpagbru, nd_valcrefisant, \n";
                    strSql+=        "       nd_valpagsinret, nd_valcrefisact, nd_valret10, nd_valret20, nd_valret30, \n";
                    strSql+=        "       nd_valret70, nd_valret100, nd_valcretri10, nd_valcretri20, nd_valcretri30, \n";
                    strSql+=        "       nd_valcretri70, nd_valcretri100, nd_valpagnet, nd_valbasimpdevcom, \n";
                    strSql+=        "       nd_valivadevcom, tx_obs1, tx_obs2, fe_ing, co_usring\n";
                    strSql+=        "  FROM tbm_cabdecmensrifor104\n";
                    strSql+=        "  where co_emp="+objParSis.getCodigoEmpresa();
                    strSql+=        "  and ne_ani="+intAniHas;
                    strSql+=        "  and ne_mes="+intMesHas;
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) {
                    blnRes = true;
                } else {
                    blnRes = false;
                }
                //blnRes=false;
                conLoc.close();
                conLoc = null;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(null, Evt);
        }
        }else{
            blnRes=false;
        }
    
    return blnRes;
    }    
    
private boolean cargarFormulario104_2(){
        boolean blnRes=true;
        // TODO add your handling code here:
        String strRucEmp="";
        String strMesCor="";
        String strAniCor="";
        String strNomEmp="";
        String strNomFilXml="";
        PrintWriter xml;
        /*Query para obtener los datos de la tabla tbm_cabdecMenSriFor104 (cambios en formulario 104 realizado 15-Dic-2016)*/
        String strQry="";
        ResultSet rsFor104=null;
        /*Query para obtener los datos de la tabla tbm_cabdecMenSriFor104 (cambios en formulario 104 realizado 15-Dic-2016)*/
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp, tx_ruc, tx_nom AS tx_nomEmp";
                strSQL+="   FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strRucEmp=rst.getString("tx_ruc");
                    strNomEmp=rst.getString("tx_nomEmp");
                }
                //con.close();
                //con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

            strMesCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "M");
            strAniCor=objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "yyyy");

            strNomFilXml="104" + ( (txtNumSus.getText().length()>0)?"SUS":"ORI") + "_" + (objUti.formatearFecha("" + objSelFec.getFechaDesde(), "dd/MM/yyyy", "MMMM")).toUpperCase() + (strAniCor) + strNomEmp + "";


            //PrintWriter xml = new PrintWriter(new FileOutputStream(strNomFilXml + ".xml"));

            if(System.getProperty("os.name").equals("Linux")){
                xml = new PrintWriter(new FileOutputStream("/tmp/Declaraciones_Zafiro/" + strNomEmp + "/" + strAniCor + "/Formulario_104/" + strNomFilXml + ".xml"));
                strRutFilGen="" + ( "/tmp/Declaraciones_Zafiro/" + strNomEmp + "/" + strAniCor + "/Formulario_104/" + strNomFilXml + ".xml" );
            }
            else{
                xml = new PrintWriter(new FileOutputStream("D:\\Declaraciones_Zafiro\\" + strNomEmp + "\\" + strAniCor + "\\Formulario_104\\" + strNomFilXml + ".xml"));
                strRutFilGen="" + ("D:\\Declaraciones_Zafiro\\" + strNomEmp + "\\" + strAniCor + "\\Formulario_104\\" + strNomFilXml + ".xml");
            }

            strQry=" select * from  tbm_cabdecMenSriFor104 where co_emp="+objParSis.getCodigoEmpresa()+ " and ne_ani="+strAniCor+ " and ne_mes="+strMesCor;
            stm=con.createStatement();
            rsFor104=stm.executeQuery(strQry);
            
            if(rsFor104.next()){

                strSQL="";
                strSQL+="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
                strSQL+="	 <formulario version=\"0.2\">	";
                strSQL+="	 <cabecera>	";
                //<codigo_version_formulario>04201201        ->   cod.Formulario=04  año=2012(no se si es año de declaración o de programa)  01(versión actual empieza desde 01)
                strSQL+="	  <codigo_version_formulario>04201201</codigo_version_formulario> 	";
                strSQL+="	  <ruc>" + strRucEmp + "</ruc> 	";
                strSQL+="	  <codigo_moneda>1</codigo_moneda> 	";
                strSQL+="	  </cabecera>	";
                strSQL+="	 <detalle>	";
                if(txtNumSus.getText().length()>0)
                    strSQL+="	  <campo numero=\"31\">S</campo> 	";//sustitutiva
                else
                    strSQL+="	  <campo numero=\"31\">O</campo> 	";//original
                strSQL+="	  <campo numero=\"101\">" + strMesCor + "</campo> 	";
                strSQL+="	  <campo numero=\"102\">" + strAniCor + "</campo> 	";
                if(txtNumSus.getText().length()>0)
                    strSQL+="	  <campo numero=\"104\">" + txtNumSus.getText() + "</campo>";
                else
                    strSQL+="	  <campo numero=\"104\" /> 	";
                strSQL+="	  <campo numero=\"198\">" + txtRucRepLeg.getText() + "</campo> 	";
                //strSQL+="	  <campo numero=\"199\">" + txtRucConGrl.getText() + "</campo> 	";
                strSQL+="	  <campo numero=\"201\">" + strRucEmp + "</campo> 	";
                strSQL+="	 <campo numero=\"202\">";
                strSQL+="" + strNomEmp + "";
                strSQL+="	  </campo>	";
            //while(rsFor104.next()){
                //strSQL+="	  <campo numero=\"401\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"401\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valbruconiva")!=null?rsFor104.getBigDecimal("nd_valbruconiva"):BigDecimal.ZERO),objParSis.getDecimalesMostrar())+ "</campo> 	";
                strSQL+="	  <campo numero=\"402\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"403\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"404\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"405\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";//INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU
                strSQL+="	  <campo numero=\"405\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valbrusiniva")!=null?rsFor104.getBigDecimal("nd_valbrusiniva"):BigDecimal.ZERO),objParSis.getDecimalesMostrar())+"</campo> ";
                strSQL+="	  <campo numero=\"406\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"407\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"408\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"409\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_BRU).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_BRU).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"409\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valbruconiva").add(rsFor104.getBigDecimal("nd_valbrusiniva"))),objParSis.getDecimalesMostrar())+"</campo> 	";
                //strSQL+="	  <campo numero=\"411\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"411\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valnetconiva")!=null?rsFor104.getBigDecimal("nd_valnetconiva"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"412\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"413\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"414\">0.00</campo> 	";                
                //strSQL+="	  <campo numero=\"415\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"415\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valnetsiniva")!=null?rsFor104.getBigDecimal("nd_valnetsiniva"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()) + "</campo> 	";                
                strSQL+="	  <campo numero=\"416\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"417\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"418\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"419\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"419\">" + objUti.redondearBigDecimal(rsFor104.getBigDecimal("nd_valnetconiva").add(rsFor104.getBigDecimal("nd_valnetsiniva")),objParSis.getDecimalesMostrar())+"</campo> 	";
                //strSQL+="	  <campo numero=\"421\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"421\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valivaven")!=null?rsFor104.getBigDecimal("nd_valivaven"):BigDecimal.ZERO),objParSis.getDecimalesMostrar())+"</campo> 	";
                strSQL+="	  <campo numero=\"422\">0.00</campo> 	";

                //strSQL+="	  <campo numero=\"429\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"429\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valivaven")!=null?rsFor104.getBigDecimal("nd_valivaven"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()) + "</campo> 	";                
                strSQL+="	  <campo numero=\"431\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"432\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"433\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"434\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"443\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"444\">0.00</campo> 	";
                
                //strSQL+="	  <campo numero=\"480\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"480\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valVenCon")!=null?rsFor104.getBigDecimal("nd_valVenCon"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()) + "</campo> 	";
                //strSQL+="	  <campo numero=\"481\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"481\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valvencre")!=null?rsFor104.getBigDecimal("nd_valvencre"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()) +"</campo> 	";
                //strSQL+="	  <campo numero=\"482\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"482\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valivaven")!=null?rsFor104.getBigDecimal("nd_valivaven"):BigDecimal.ZERO), objParSis.getDecimalesMostrar()) + "</campo> 	";
                //strSQL+="	  <campo numero=\"483\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"483\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valVenCreMesAnt")!=null?rsFor104.getBigDecimal("nd_valVenCreMesAnt"):BigDecimal.ZERO), objParSis.getDecimalesMostrar())+ "</campo> 	";
                //strSQL+="	  <campo numero=\"484\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"484\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valIVAVenCon")!=null?rsFor104.getBigDecimal("nd_valIVAVenCon"):BigDecimal.ZERO),objParSis.getDecimalesMostrar())+"</campo> 	";                
                //strSQL+="	  <campo numero=\"485\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA).toString())), objParSis.getDecimalesMostrar()).subtract(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
                strSQL+="	  <campo numero=\"485\">" +objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valivaven")!=null?rsFor104.getBigDecimal("nd_valivaven"):BigDecimal.ZERO), objParSis.getDecimalesMostrar()).subtract((rsFor104.getBigDecimal("nd_valIVAVenCon")!=null?rsFor104.getBigDecimal("nd_valIVAVenCon"):BigDecimal.ZERO))+"</campo> 	";
                
                //strSQL+="	  <campo numero=\"499\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CRE_MES_ANT).toString())), objParSis.getDecimalesMostrar()).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IVA_CON).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
                
                strSQL+="	  <campo numero=\"499\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valVenCreMesAnt")!=null?rsFor104.getBigDecimal("nd_valVenCreMesAnt"):BigDecimal.ZERO), objParSis.getDecimalesMostrar()).add((rsFor104.getBigDecimal("nd_valIVAVenCon")!=null?rsFor104.getBigDecimal("nd_valIVAVenCon"):BigDecimal.ZERO))+"</campo> 	";
                //strSQL+="	  <campo numero=\"501\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"501\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valComConIVA")!=null?rsFor104.getBigDecimal("nd_valComConIVA"):BigDecimal.ZERO),objParSis.getDecimalesMostrar())+"</campo> 	";
                strSQL+="	  <campo numero=\"502\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"503\">0.00</campo> 	";
                
                //strSQL+="	  <campo numero=\"504\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                
                strSQL+="	  <campo numero=\"504\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valImp")!=null?rsFor104.getBigDecimal("nd_valImp"):BigDecimal.ZERO), objParSis.getDecimalesMostrar())+"</campo> 	";
                
                strSQL+="	  <campo numero=\"505\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"506\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";//
                strSQL+="	  <campo numero=\"506\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valComSinIVA")!=null?rsFor104.getBigDecimal("nd_valComSinIVA"):BigDecimal.ZERO), objParSis.getDecimalesMostrar()) + "</campo> 	";//                
                strSQL+="	  <campo numero=\"507\">0.00</campo> 	";
                
                //strSQL+="	  <campo numero=\"509\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar())).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
                strSQL+="	  <campo numero=\"509\">" +objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valComConIVA")!=null?rsFor104.getBigDecimal("nd_valComConIVA"):BigDecimal.ZERO).add((rsFor104.getBigDecimal("nd_valImp")!=null?rsFor104.getBigDecimal("nd_valImp"):BigDecimal.ZERO).add((rsFor104.getBigDecimal("nd_valComSinIVA")!=null?rsFor104.getBigDecimal("nd_valComSinIVA"):BigDecimal.ZERO)) ),objParSis.getDecimalesMostrar())+ "</campo> 	";
                
                //strSQL+="	  <campo numero=\"511\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"511\">" +objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valComConIVA")!=null?rsFor104.getBigDecimal("nd_valComConIVA"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()) +"</campo> 	";
                strSQL+="	  <campo numero=\"512\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"513\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"514\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"514\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valImp")!=null?rsFor104.getBigDecimal("nd_valImp"):BigDecimal.ZERO), objParSis.getDecimalesMostrar())+ "</campo> 	";                
                strSQL+="	  <campo numero=\"515\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"516\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"516\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valComSinIVA")!=null?rsFor104.getBigDecimal("nd_valComSinIVA"):BigDecimal.ZERO), objParSis.getDecimalesMostrar()) + "</campo> 	";                
                strSQL+="	  <campo numero=\"517\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"518\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"519\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_CON_IVA_VAL).toString())), objParSis.getDecimalesMostrar()).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_VAL).toString())), objParSis.getDecimalesMostrar())).add(objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_SIN_IVA_VAL).toString())), objParSis.getDecimalesMostrar())) + "</campo> 	";
                strSQL+="	  <campo numero=\"519\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valComConIVA")!=null?rsFor104.getBigDecimal("nd_valComConIVA"):BigDecimal.ZERO).add((rsFor104.getBigDecimal("nd_valImp")!=null?rsFor104.getBigDecimal("nd_valImp"):BigDecimal.ZERO)).add((rsFor104.getBigDecimal("nd_valComSinIVA")!=null?rsFor104.getBigDecimal("nd_valComSinIVA"):BigDecimal.ZERO)), objParSis.getDecimalesMostrar()) + "</campo> 	";
                
                //strSQL+="	  <campo numero=\"521\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"521\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valIVACom")!=null?rsFor104.getBigDecimal("nd_valIVACom"):BigDecimal.ZERO), objParSis.getDecimalesMostrar()) + "</campo> 	";                
                strSQL+="	  <campo numero=\"522\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"523\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"524\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"524\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valIVAImp")!=null?rsFor104.getBigDecimal("nd_valIVAImp"):BigDecimal.ZERO), objParSis.getDecimalesMostrar()) + "</campo> 	";

                strSQL+="	  <campo numero=\"525\">0.00</campo> 	";
//                strSQL+="	  <campo numero=\"529\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"529\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valIVACom")!=null?rsFor104.getBigDecimal("nd_valIVACom"):BigDecimal.ZERO).add((rsFor104.getBigDecimal("nd_valIVAImp")!=null?rsFor104.getBigDecimal("nd_valIVAImp"):BigDecimal.ZERO)), objParSis.getDecimalesMostrar())+"</campo> 	";
                strSQL+="	  <campo numero=\"531\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"532\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"533\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"534\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"535\">0.00</campo> 	";
                           
                //strSQL+="	  <campo numero=\"544\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_IVA_DEV_COM_CRUP).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"544\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valIVADevCom")!=null?rsFor104.getBigDecimal("nd_valIVADevCom"):BigDecimal.ZERO), objParSis.getDecimalesMostrar())+"</campo> 	";
                strSQL+="	  <campo numero=\"545\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"553\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString()))).divide((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString())))), 0, BigDecimal.ROUND_UP),4)   + "</campo> 	";
                strSQL+="	  <campo numero=\"553\">" +objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valNetSinIVA")!=null?rsFor104.getBigDecimal("nd_valNetSinIVA"):BigDecimal.ZERO).divide(rsFor104.getBigDecimal("nd_valNetConIVA")!=null?rsFor104.getBigDecimal("nd_valNetConIVA"):BigDecimal.ZERO,0, BigDecimal.ROUND_UP).add(rsFor104.getBigDecimal("nd_valNetSinIVA")!=null?rsFor104.getBigDecimal("nd_valNetSinIVA"):BigDecimal.ZERO),4) + "</campo> 	";
                
                //strSQL+="	  <campo numero=\"554\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_COM_IVA).toString()))).add((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_IMP_IVA).toString())))).multiply(((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString()))).divide((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_CON_IVA_VAL_NET).toString())).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VTA_SIN_IVA_VAL_NET).toString())))), 0, BigDecimal.ROUND_UP))), objParSis.getDecimalesMostrar()) + "</campo> 	";
                // (nd_valIVACom + nd_valIVAImp) * (nd_valNetSinIVA/(nd_valNetConIVA+nd_valNetSinIVA))
                BigDecimal bgValIvaCom= (rsFor104.getBigDecimal("nd_valIVACom")!=null?rsFor104.getBigDecimal("nd_valIVACom"):BigDecimal.ZERO);
                BigDecimal bgValIvaImp= (rsFor104.getBigDecimal("nd_valIVAImp")!=null?rsFor104.getBigDecimal("nd_valIVAImp"):BigDecimal.ZERO);
                BigDecimal bgValNetSinIva= (rsFor104.getBigDecimal("nd_valNetSinIVA")!=null?rsFor104.getBigDecimal("nd_valNetSinIVA"):BigDecimal.ZERO);
                BigDecimal bgValNetConIva= (rsFor104.getBigDecimal("nd_valNetConIVA")!=null?rsFor104.getBigDecimal("nd_valNetConIVA"):BigDecimal.ZERO);
                BigDecimal bgTot554=bgValIvaCom.add(bgValIvaImp).multiply((bgValNetSinIva.divide((bgValNetConIva.add(bgValNetSinIva)),2, RoundingMode.HALF_UP)));
                
                strSQL+="	  <campo numero=\"554\">" +bgTot554 +"</campo> 	";
                
                //nd_valPagBru
                
                BigDecimal bdeValPagBru=  objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valPagBru")!=null?rsFor104.getBigDecimal("nd_valPagBru"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()); //new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString()));
                if(bdeValPagBru.compareTo(new BigDecimal(BigInteger.ZERO))>0){//valor a pagar es un valor(601 tiene el valor y 602 es 0), credito tributario(602 es un valor y 601 es 0)
            //        strSQL+="	  <campo numero=\"601\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                    strSQL+="	  <campo numero=\"601\">" +bdeValPagBru+"</campo> 	";
                    strSQL+="	  <campo numero=\"602\">0.00</campo> 	";
                }
                else{
                    strSQL+="	  <campo numero=\"601\">0.00</campo> 	";
                    //strSQL+="	  <campo numero=\"602\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_BRU).toString())), objParSis.getDecimalesMostrar()).abs() + "</campo> 	";
                    strSQL+="	  <campo numero=\"602\">" +bdeValPagBru+ "</campo> 	";
                }
                
                //strSQL+="	  <campo numero=\"605\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ANT).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";                
                //nd_valCreFisAnt
                strSQL+="	  <campo numero=\"605\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valCreFisAnt")!=null?rsFor104.getBigDecimal("nd_valCreFisAnt"):BigDecimal.ZERO),objParSis.getDecimalesMostrar())+"</campo> 	";
                strSQL+="	  <campo numero=\"607\">0.00</campo> 	"; 
                strSQL+="	  <campo numero=\"609\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"611\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"613\">0.00</campo> 	";
                //strSQL+="	  <campo numero=\"615\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_CRE_FIS_ACT).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                //nd_valCreFisAct
                strSQL+="	  <campo numero=\"615\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valCreFisAct")!=null?rsFor104.getBigDecimal("nd_valCreFisAct"):BigDecimal.ZERO),objParSis.getDecimalesMostrar())+"</campo> 	";
                strSQL+="	  <campo numero=\"617\">0.00</campo> 	";

                //BigDecimal bdeValPagSinRte=new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_SIN_RTE).toString()));
                BigDecimal bdeValPagSinRte=objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valPagSinRet")!=null?rsFor104.getBigDecimal("nd_valPagSinRet"):BigDecimal.ZERO),objParSis.getDecimalesMostrar());
                if(bdeValPagSinRte.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                    strSQL+="	  <campo numero=\"619\">" + objUti.redondearBigDecimal(bdeValPagSinRte, objParSis.getDecimalesMostrar()) + "</campo> 	";//INT_TBL_DAT_VAL_PAG_SIN_RTE
                else
                    strSQL+="	  <campo numero=\"619\">0.00</campo> 	";//INT_TBL_DAT_VAL_PAG_SIN_RTE

                strSQL+="	  <campo numero=\"621\">0.00</campo> 	";

                if(bdeValPagSinRte.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                    strSQL+="	  <campo numero=\"699\">" + objUti.redondearBigDecimal(bdeValPagSinRte, objParSis.getDecimalesMostrar()) + "</campo> 	";//INT_TBL_DAT_VAL_PAG_SIN_RTE
                else
                    strSQL+="	  <campo numero=\"699\">0.00</campo> 	";

                //strSQL+="	  <campo numero=\"721\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                BigDecimal bgValRet30=objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valRet30")!=null?rsFor104.getBigDecimal("nd_valRet30"):BigDecimal.ZERO),objParSis.getDecimalesMostrar());
                BigDecimal bgValRet70=objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valRet70")!=null?rsFor104.getBigDecimal("nd_valRet70"):BigDecimal.ZERO),objParSis.getDecimalesMostrar());
                BigDecimal bgValRet100=objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valRet100")!=null?rsFor104.getBigDecimal("nd_valRet100"):BigDecimal.ZERO),objParSis.getDecimalesMostrar());            
                BigDecimal bgTot799=bgValRet30.add(bgValRet70).add(bgValRet100);
                strSQL+="	  <campo numero=\"721\">" + bgValRet30+ "</campo> 	";

                //strSQL+="	  <campo numero=\"723\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"723\">" + bgValRet70 + "</campo> ";
                //strSQL+="	  <campo numero=\"725\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"725\">" + bgValRet100 +"</campo> 	";

                strSQL+="	  <campo numero=\"799\">" + bgTot799 + "</campo> 	";

                //strSQL+="	  <campo numero=\"859\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"859\">" + bgTot799 + "</campo> 	";
                strSQL+="	  <campo numero=\"880\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"890\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"897\">" + objUti.redondearBigDecimal((txtPagImpAnt.getText().equals("")?"0":txtPagImpAnt.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"898\">" + objUti.redondearBigDecimal((txtPagInt.getText().equals("")?"0":txtPagInt.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"899\">" + objUti.redondearBigDecimal((txtPagMul.getText().equals("")?"0":txtPagMul.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";



                //strSQL+="	  <campo numero=\"902\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"902\">" + bgTot799 + "</campo> 	";
                strSQL+="	  <campo numero=\"903\">" + objUti.redondearBigDecimal((txtIntMorSus.getText().equals("")?"0":txtIntMorSus.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"904\">" + objUti.redondearBigDecimal((txtMulSus.getText().equals("")?"0":txtMulSus.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
                //strSQL+="	  <campo numero=\"905\">" + objUti.redondearBigDecimal((new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_30P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_70P).toString()))).add(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_RET_100P).toString()))), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"905\">" + bgTot799 + "</campo> 	";
                strSQL+="	  <campo numero=\"906\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"907\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"908\">" + txtNumNotCre1.getText() + "</campo>	";
                strSQL+="	  <campo numero=\"909\">" + objUti.redondearBigDecimal((txtValNotCre1.getText().equals("")?"0":txtValNotCre1.getText()), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"910\">" + txtNumNotCre2.getText() + "</campo>	";
                strSQL+="	  <campo numero=\"911\">" + objUti.redondearBigDecimal((txtValNotCre2.getText().equals("")?"0":txtValNotCre2.getText()), objParSis.getDecimalesMostrar()) + "</campo>";
                strSQL+="	  <campo numero=\"912\" /> 	";
                strSQL+="	  <campo numero=\"913\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"915\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"916\" /> 	";
                strSQL+="	  <campo numero=\"917\">0.00</campo> 	";
                strSQL+="	  <campo numero=\"918\" /> 	";
                strSQL+="	  <campo numero=\"919\">0.00</campo> 	";


                if(cboForPag.getSelectedIndex()==0)//Banco Machala(25)
                    strSQL+="	  <campo numero=\"922\">25</campo> 	";
                else//Banco Pacifico(30)
                    strSQL+="	  <campo numero=\"922\">30</campo>	";

                //strSQL+="	  <campo numero=\"999\">" + objUti.redondearBigDecimal(new BigDecimal(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET)==null?"0":(objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET).toString().equals("")?"0":objTblMod.getValueAt(0, INT_TBL_DAT_VAL_PAG_NET).toString())), objParSis.getDecimalesMostrar()) + "</campo> 	";
                strSQL+="	  <campo numero=\"999\">" + objUti.redondearBigDecimal((rsFor104.getBigDecimal("nd_valPagNet")!=null?rsFor104.getBigDecimal("nd_valPagNet"):BigDecimal.ZERO),objParSis.getDecimalesMostrar()) +"</campo> 	";
                strSQL+="	  </detalle>	";
                strSQL+="	  </formulario>	";
                System.out.println("strSQL: " + strSQL);
                xml.println(strSQL);
                xml.println();
                xml.close();
            
            }else{
                mostrarMsgInf("No existen registros del periodo seleccionado.");
                blnRes=false;
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    /** Configurar el formulario. */
    private boolean configurarFrm103(){
        boolean blnRes=true;
        try{
            strAux=objParSis.getNombreMenu() + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            panConFor.setVisible(false);
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panCorRpt.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN_103,"");            
            vecCab.add(INT_TBL_DAT_COD_EMP_103,"Cod.Emp.");
            vecCab.add(INT_TBL_DAT_RET_1P_103,"Ret.1%");
            vecCab.add(INT_TBL_DAT_RET_2P_103,"Ret.2%.");
            vecCab.add(INT_TBL_DAT_RET_8P_103,"Ret.8%");
            vecCab.add(INT_TBL_DAT_RET_10P_103,"Ret.10%.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
               
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN_103);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN_103).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_103).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_RET_1P_103).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_RET_2P_103).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_RET_8P_103).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_RET_10P_103).setPreferredWidth(70);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_1P_103).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_2P_103).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_8P_103).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_RET_10P_103).setCellRenderer(objTblCelRenLbl);
            

            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);
            
            arlDat=new ArrayList();

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_103, tblDat);

            //retenciones
            ZafTblHeaGrp objTblHeaGrpRet=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpRet.setHeight(16*3);

            ZafTblHeaColGrp objTblHeaColGrpRet=null;
            objTblHeaColGrpRet=new ZafTblHeaColGrp("Retenciones");
            objTblHeaColGrpRet.setHeight(16);
            objTblHeaGrpRet.addColumnGroup(objTblHeaColGrpRet);

            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_1P_103));
            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_2P_103));
            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_8P_103));
            objTblHeaColGrpRet.add(tcmAux.getColumn(INT_TBL_DAT_RET_10P_103));
            tcmAux=null;

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            txtRucRepLeg.setEnabled(false);
            txtNomRepLeg.setEnabled(false);
            txtRucConGrl.setEnabled(false);
            txtNomConGrl.setEnabled(false);

            if(objParSis.getCodigoMenu()==2789){//103
                panFor.setBorder(javax.swing.BorderFactory.createTitledBorder("Formulario 103"));
            }
            else if(objParSis.getCodigoMenu()==2795){//104
                panFor.setBorder(javax.swing.BorderFactory.createTitledBorder("Formulario 104"));
            }


            cargarDatosLegalesEmpresa();

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetReg103()
    {
        int intCodEmp,  i;
        boolean blnRes=true;
        
        int intCodCtaRte1P=-1;
        int intCodCtaRte2P=-1;
        int intCodCtaRte8P=-1;
        int intCodCtaRte10P=-1;
        
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            if (con!=null){
               
                switch(intCodEmp){
                    case 1:
                        intCodCtaRte1P=1044; //2.01.06.03.01 1% RETENCIÓN FUENTE -VARIOS
                        intCodCtaRte2P=1046; //2.01.06.03.05 2% RETENCION EN LA FUENTE
                        intCodCtaRte8P=1060; // 2.01.06.03.06 RET.FTE.8% SERVICIOS P.N.
                        intCodCtaRte10P=1032; //2.01.06.02.06 I.V.A. RET. PERSONAS NATURALES
                        break;
                    case 2:
                        intCodCtaRte1P=238; //2.01.06.03.01 1% RETENCIÓN FUENTE -VARIOS
                        intCodCtaRte2P=244; //2.01.06.03.05 2% RETENCION EN LA FUENTE
                        intCodCtaRte8P=245; // 2.01.06.03.06 RET.FTE.8% SERVICIOS P.N.
                        intCodCtaRte10P=226; //2.01.06.02.06 I.V.A. RET. PERSONAS NATURALES
                        break;
                    case 4:
                        intCodCtaRte1P=991; //2.01.06.03.01 1% RETENCIÓN FUENTE -VARIOS
                        intCodCtaRte2P=1005; //2.01.06.03.05 2% RETENCION EN LA FUENTE
                        intCodCtaRte8P=1006; // 2.01.06.03.06 RET.FTE.8% SERVICIOS P.N.
                        intCodCtaRte10P=979; //2.01.06.02.06 I.V.A. RET. PERSONAS NATURALES
                        break;
                }

                strAux="";
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Básqueda por rangos
                        strAux+=" AND a2.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux+=" AND a2.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux+=" AND a2.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                    
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT SUM(a.monDeb - a.monHab) AS nd_valret1P,SUM(b.monDeb - b.monHab) AS nd_valret2P,SUM(c.monDeb - c.monHab) AS nd_valret8P,SUM(d.monDeb - d.monHab) AS nd_valret10P\n";
                    strSQL+=" FROM(	 	\n" ;
                    strSQL+="     SELECT SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb,  	\n" ;
                    strSQL+="            SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab\n" ;
                    strSQL+="      FROM (tbm_cabDia AS a2 \n" ;
                    strSQL+="            LEFT OUTER JOIN tbm_usr AS d1 ON a2.co_usrIng=d1.co_usr) \n" ;
                    strSQL+="            INNER JOIN tbm_detDia AS a3  ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia) 	\n" ;
                    strSQL+="            INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)       \n" ;
                    strSQL+="            WHERE a2.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_cta="+intCodCtaRte1P+" AND a4.co_tipdoc not in(30) "+strAux+" 	 AND a2.st_reg='A'  	\n" ;
                    strSQL+="      ) AS a,\n" ;
                    strSQL+="      (	 	\n" ;
                    strSQL+="     SELECT SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb,  	\n" ;
                    strSQL+="            SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab\n" ;
                    strSQL+="      FROM (tbm_cabDia AS a2 \n" ;
                    strSQL+="            LEFT OUTER JOIN tbm_usr AS d1 ON a2.co_usrIng=d1.co_usr) \n" ;
                    strSQL+="            INNER JOIN tbm_detDia AS a3  ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia) 	\n" ;
                    strSQL+="            INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)       \n" ;
                    strSQL+="            WHERE a2.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_cta="+intCodCtaRte2P+" AND a4.co_tipdoc not in(30) "+strAux+" 	 AND a2.st_reg='A'  	\n" ;
                    strSQL+="      ) AS b,\n" ;
                    strSQL+="      (	 	\n" ;
                    strSQL+="     SELECT SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb,  	\n" ;
                    strSQL+="            SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab\n" ;
                    strSQL+="      FROM (tbm_cabDia AS a2 \n" ;
                    strSQL+="            LEFT OUTER JOIN tbm_usr AS d1 ON a2.co_usrIng=d1.co_usr) \n" ;
                    strSQL+="            INNER JOIN tbm_detDia AS a3  ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia) 	\n" ;
                    strSQL+="            INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)       \n" ;
                    strSQL+="            WHERE a2.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_cta="+intCodCtaRte8P+" AND a4.co_tipdoc not in(30) "+strAux+" 	 AND a2.st_reg='A'  	\n" ;
                    strSQL+="      ) AS c,\n" ;
                    strSQL+="      (	 	\n" ;
                    strSQL+="     SELECT SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb,  	\n" ;
                    strSQL+="            SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab\n" ;
                    strSQL+="      FROM (tbm_cabDia AS a2 \n" ;
                    strSQL+="            LEFT OUTER JOIN tbm_usr AS d1 ON a2.co_usrIng=d1.co_usr) \n" ;
                    strSQL+="            INNER JOIN tbm_detDia AS a3  ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia) 	\n" ;
                    strSQL+="            INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)       \n" ;
                    strSQL+="            WHERE a2.co_emp="+objParSis.getCodigoEmpresa()+" AND a3.co_cta="+intCodCtaRte10P+" AND a4.co_tipdoc not in(30) "+strAux+" 	 AND a2.st_reg='A'  	\n" ;
                    strSQL+="      ) AS d  ";
                    //System.out.println("SQL cargarDet:" +strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;                                
                if (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN_103,                 "");
                        vecReg.add(INT_TBL_DAT_COD_EMP_103,             ""+ objParSis.getCodigoEmpresa());
                        vecReg.add(INT_TBL_DAT_RET_1P_103,              ""+ new BigDecimal(rst.getObject("nd_valret1P")==null?"0":(rst.getString("nd_valret1P").equals("")?"0":rst.getString("nd_valret1P"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_2P_103,              ""+ new BigDecimal(rst.getObject("nd_valret2P")==null?"0":(rst.getString("nd_valret2P").equals("")?"0":rst.getString("nd_valret2P"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_8P_103,              ""+ new BigDecimal(rst.getObject("nd_valret8P")==null?"0":(rst.getString("nd_valret8P").equals("")?"0":rst.getString("nd_valret8P"))).abs());
                        vecReg.add(INT_TBL_DAT_RET_10P_103,             ""+ new BigDecimal(rst.getObject("nd_valret10P")==null?"0":(rst.getString("nd_valret10P").equals("")?"0":rst.getString("nd_valret10P"))).abs());
                 vecDat.add(vecReg);
                i++;
                pgrSis.setValue(i);
                //Asignar vectores al modelo.                                                
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                }
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                pgrSis.setValue(0);
                butCon.setText("Consultar");
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
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
    } //Funcion cargarDetReg()
}
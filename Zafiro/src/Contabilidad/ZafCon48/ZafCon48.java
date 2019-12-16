/*
 * ZafCon48.java
 *
 * Created on July 30, 2007, 2:39 PM
 */

package Contabilidad.ZafCon48;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafDate.ZafSelectDate;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;
import javax.swing.JScrollBar;
/**
 *
 * @author  ilino
 */
public class ZafCon48 extends javax.swing.JInternalFrame {
    private ZafParSis objParSis;
    //Variables generales.
    private ZafUtil objUti;
    private ZafTblMod objTblModSal, objTblModIncDec, objTblModTot;
    
    private ZafTblMod objTblModSalIni, objTblModSalTotIni;
    private String strCodCtaTipDsc;
    private ZafTblEdi objTblEdi, objTblEdiSal;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblSal, objTblCelRenLblSalTot;            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    
    private ZafVenCon vcoTipRep;                        //Ventana de consulta "Tipo de documento".
    private Connection con, conCab;
    private Statement stm, stmCab, stmSalDsc;
    private ResultSet rst, rstCab, rstSalDsc;
    private ResultSetMetaData rmd;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private Vector vecDatSal, vecCabSal, vecRegSal, vecAuxSal;
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private ZafSelectDate objSelDat;
    private MiToolBar objTooBar;
    private ZafMouMotAdaSaldos objMouMotAdaSal, objMouMotAdaSalTot;
    ZafColNumerada objColNum, objColNumSal;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private int intAniDes, intAniHas, intMesDes, intMesHas;
    
    //PARA LA TABLA DE INCREMENTOS Y DECREMENTOS
    final int INT_TBL_LIN=0;
    final int INT_TBL_ANI=1;
    final int INT_TBL_MES=2;
    final int INT_TBL_PRC=3;
    final int INT_TBL_BLK=4;
    final int INT_TBL_MES_NUM=5;
    
    
    private JScrollBar barDatCta, barDatTot;
    
    //PARA LA TABLA DE DATOS
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_CTA=1;
    final int INT_TBL_DAT_NUM_CTA=2;
    final int INT_TBL_DAT_NOM_CTA=3;
    final int INT_TBL_DAT_BUT_PRC=4;    
    final int INT_TBL_DAT_NOD_PAD=5;
    final int INT_TBL_DAT_NIV=6;
    final int INT_TBL_DAT_COD_REG=7;
    final int INT_TBL_DAT_TIP_DSC=8;
    
    //PARA OBTENER LOS ANIOS Y MESES QUE SE USARAN PARA GENERAR LAS CILUMNAS DE FORMA ALEATORIA
    final int INT_ARL_ANI=0;
    final int INT_ARL_MES=1;
    final int INT_ARL_MES_NOM=2;
    
    private String strDat[][] = new String[100][100];
    private int intNumFilStrDat;
    
    private double dblAct=0.00;

    
    private String strDatIns[][] = new String[100][100];
    private int intNumFilStrDatIns;
    
    
    private int intFrsColOcu;
    //PARA GUARDAR LOS DATOS QUE SE PRESENTARAN INICIALMENTE, ANTES DE REALIZARLE ALGUN CAMBIO
    final int INT_ARL_INI_COD_EMP=0;
    final int INT_ARL_INI_COD_CTA=1;
    final int INT_ARL_INI_NUM_CTA=2;
    final int INT_ARL_INI_NOM_DSC=3;
    final int INT_ARL_INI_COD_REG=4;
    final int INT_ARL_INI_NOD_PAD=5;
    final int INT_ARL_INI_NIV_DSC=6;
    final int INT_ARL_INI_ANI=7;
    final int INT_ARL_INI_TIP_DSC=8;
    final int INT_ARL_INI_SAL_ENE=9;
    final int INT_ARL_INI_SAL_FEB=10;
    final int INT_ARL_INI_SAL_MAR=11;
    final int INT_ARL_INI_SAL_ABR=12;
    final int INT_ARL_INI_SAL_MAY=13;
    final int INT_ARL_INI_SAL_JUN=14;
    final int INT_ARL_INI_SAL_JUL=15;
    final int INT_ARL_INI_SAL_AGO=16;
    final int INT_ARL_INI_SAL_SEP=17;
    final int INT_ARL_INI_SAL_OCT=18;
    final int INT_ARL_INI_SAL_NOV=19;
    final int INT_ARL_INI_SAL_DIC=20;
    
    
    
    //PARA LA TABLA DE INCREMENTOS Y DECREMENTOS
    private int intAniIni, intAniFin;
    private int numColModIni, numColModFin;
    private int numColModIniTot, numColModFinTot;
    private ZafCon48_02 objCon48_02;
    private int intIndFilSel;
//    private ArrayList arlRegTmp, arlDatTmp;
    private double dblPrcIncDecEne, dblPrcIncDecFeb, dblPrcIncDecMar, dblPrcIncDecAbr, dblPrcIncDecMay, dblPrcIncDecJun, dblPrcIncDecJul, dblPrcIncDecAgo;
    private double dblPrcIncDecSep, dblPrcIncDecOct, dblPrcIncDecNov, dblPrcIncDecDic;
    
    
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiBut;
    private int intNumColAdc;
    private ZafCon48_01 objCon48_01;
    
    private int intColRst;
    private JTable tblDatIncDec;
    private int intColDes, intColHas;
    
    private String strCodRep, strNomRep;
    
//    private int numColModFin;
    private ArrayList arlRegAniMes, arlDatAniMes;
    private ArrayList arlRegIni, arlDatIni;
    
    
    private ArrayList arlReg, arlDat, arlDatIns;
    final int INT_ARL_PRC_ANI=0;
    final int INT_ARL_PRC_MES=1;
    final int INT_ARL_PRC_POR=2;
    final int INT_ARL_PRC_COD=3;
    final int INT_ARL_PRC_MES_NUM=4;
    final int INT_ARL_PRC_REG=5;
    final int INT_ARL_PRC_LIN=6;
    
    
    private ArrayList arlRegConPrc, arlDatConPrc;
    
    private ArrayList arlRegOcuColCns, arlDatOcuColCns;
    
    final int INT_ARL_MES_MIN=0;
    final int INT_ARL_ANI_MIN=1;
    final int INT_ARL_MES_MAX=2;
    final int INT_ARL_ANI_MAX=3;
    //para ocultar columnas
    
    
    /** Creates new form ZafCon48 */
    public ZafCon48(ZafParSis obj) {
        try{
            initComponents();
            objParSis=(ZafParSis)obj.clone();
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
    private void initComponents() {//GEN-BEGIN:initComponents
        panGrl = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFrmGrl = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        panCabFilFec = new javax.swing.JPanel();
        panCabFilTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCabFilFecSel = new javax.swing.JPanel();
        panCabFilCfg = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodRep = new javax.swing.JTextField();
        txtNomRep = new javax.swing.JTextField();
        butRep = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCfg = new javax.swing.JTextField();
        panCabFilDia = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        optTodDia = new javax.swing.JRadioButton();
        optDiaCtaEfe = new javax.swing.JRadioButton();
        chkCtaNotAgr = new javax.swing.JCheckBox();
        chkCtaEsp = new javax.swing.JCheckBox();
        panCen = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        chkMosSalAntPorMes = new javax.swing.JCheckBox();
        panFilDat = new javax.swing.JPanel();
        panFilDatRadBut = new javax.swing.JPanel();
        optUtiDatPrs = new javax.swing.JRadioButton();
        optSalMenAniAntMesPry = new javax.swing.JRadioButton();
        optMesUsaAniAntMesPry = new javax.swing.JRadioButton();
        panFilDatPryMesAct = new javax.swing.JPanel();
        chkPryMesAct = new javax.swing.JCheckBox();
        panFrmDat = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        spnTblSal = new javax.swing.JScrollPane();
        tblDatSal = new javax.swing.JTable();
        panTblTot = new javax.swing.JPanel();
        spnTblTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        panPie = new javax.swing.JPanel();
        panTooBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitFrm(evt);
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

        panGrl.setLayout(new java.awt.BorderLayout());

        panFrmGrl.setLayout(new java.awt.BorderLayout());

        panCab.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(10, 214));
        panCabFilFec.setLayout(new java.awt.BorderLayout());

        panCabFilFec.setPreferredSize(new java.awt.Dimension(0, 106));
        panCabFilTit.setLayout(new java.awt.BorderLayout());

        panCabFilTit.setPreferredSize(new java.awt.Dimension(34, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel3");
        lblTit.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblTit.setPreferredSize(new java.awt.Dimension(34, 12));
        panCabFilTit.add(lblTit, java.awt.BorderLayout.CENTER);

        panCabFilFec.add(panCabFilTit, java.awt.BorderLayout.NORTH);

        panCabFilFecSel.setLayout(new java.awt.BorderLayout());

        panCabFilFec.add(panCabFilFecSel, java.awt.BorderLayout.CENTER);

        panCab.add(panCabFilFec, java.awt.BorderLayout.NORTH);

        panCabFilCfg.setLayout(null);

        panCabFilCfg.setPreferredSize(new java.awt.Dimension(10, 60));
        jLabel1.setText("Reporte:");
        panCabFilCfg.add(jLabel1);
        jLabel1.setBounds(10, 3, 50, 15);

        txtCodRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodRepActionPerformed(evt);
            }
        });
        txtCodRep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodRepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodRepFocusLost(evt);
            }
        });

        panCabFilCfg.add(txtCodRep);
        txtCodRep.setBounds(100, 1, 63, 20);

        txtNomRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomRepActionPerformed(evt);
            }
        });
        txtNomRep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomRepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomRepFocusLost(evt);
            }
        });

        panCabFilCfg.add(txtNomRep);
        txtNomRep.setBounds(165, 1, 220, 20);

        butRep.setText("jButton1");
        butRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRepActionPerformed(evt);
            }
        });

        panCabFilCfg.add(butRep);
        butRep.setBounds(387, 0, 22, 22);

        jLabel2.setText("Configuraci\u00f3n:");
        panCabFilCfg.add(jLabel2);
        jLabel2.setBounds(10, 26, 90, 15);

        panCabFilCfg.add(txtCfg);
        txtCfg.setBounds(100, 23, 63, 20);

        panCab.add(panCabFilCfg, java.awt.BorderLayout.CENTER);

        panCabFilDia.setLayout(null);

        panCabFilDia.setBorder(new javax.swing.border.TitledBorder("Para obtener los saldos de las cuentas utilice:"));
        panCabFilDia.setPreferredSize(new java.awt.Dimension(10, 65));
        butCon.setText("Cargar Datos");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });

        panCabFilDia.add(butCon);
        butCon.setBounds(540, 10, 110, 23);

        optTodDia.setSelected(true);
        optTodDia.setText("Todos los diarios");
        optTodDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodDiaActionPerformed(evt);
            }
        });

        panCabFilDia.add(optTodDia);
        optTodDia.setBounds(6, 13, 510, 18);

        optDiaCtaEfe.setText("S\u00f3lo los diarios donde aparezca una cuenta de efectivo");
        panCabFilDia.add(optDiaCtaEfe);
        optDiaCtaEfe.setBounds(6, 28, 510, 18);

        chkCtaNotAgr.setText("Agregar cuentas NO AGRUPADAS");
        panCabFilDia.add(chkCtaNotAgr);
        chkCtaNotAgr.setBounds(40, 43, 240, 18);

        chkCtaEsp.setText("Agregar cuentas ESPECIALES");
        panCabFilDia.add(chkCtaEsp);
        chkCtaEsp.setBounds(280, 43, 200, 18);

        panCab.add(panCabFilDia, java.awt.BorderLayout.SOUTH);

        panFrmGrl.add(panCab, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanel3.setLayout(new java.awt.BorderLayout());

        chkMosSalAntPorMes.setText("Mostrar los saldos anteriores de cada mes");
        jPanel3.add(chkMosSalAntPorMes, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel1, java.awt.BorderLayout.NORTH);

        panFilDat.setLayout(new java.awt.BorderLayout());

        panFilDat.setBorder(new javax.swing.border.TitledBorder("Para obtener los saldos de los meses a proyectar(Meses no transcurridos)"));
        panFilDatRadBut.setLayout(null);

        panFilDatRadBut.setPreferredSize(new java.awt.Dimension(0, 50));
        optUtiDatPrs.setSelected(true);
        optUtiDatPrs.setText("Utilizar los datos del presupuesto");
        panFilDatRadBut.add(optUtiDatPrs);
        optUtiDatPrs.setBounds(0, 0, 441, 15);

        optSalMenAniAntMesPry.setText("Utilizar saldos mensuales correspond. al a\u00f1o anterior para cada mes a proyectar");
        panFilDatRadBut.add(optSalMenAniAntMesPry);
        optSalMenAniAntMesPry.setBounds(0, 15, 490, 15);

        optMesUsaAniAntMesPry.setText("Elegir el mes(es) a utilizar de los a\u00f1os anteriores para cada mes a proyectar");
        panFilDatRadBut.add(optMesUsaAniAntMesPry);
        optMesUsaAniAntMesPry.setBounds(0, 31, 460, 15);

        panFilDat.add(panFilDatRadBut, java.awt.BorderLayout.CENTER);

        panFilDatPryMesAct.setLayout(new java.awt.BorderLayout());

        chkPryMesAct.setSelected(true);
        chkPryMesAct.setText("Proyectar tambi\u00e9n el mes actual");
        panFilDatPryMesAct.add(chkPryMesAct, java.awt.BorderLayout.SOUTH);

        panFilDat.add(panFilDatPryMesAct, java.awt.BorderLayout.SOUTH);

        jPanel4.add(panFilDat, java.awt.BorderLayout.CENTER);

        panCen.add(jPanel4, java.awt.BorderLayout.CENTER);

        panFrmGrl.add(panCen, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("tab1", panFrmGrl);

        panFrmDat.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

        tblDatSal.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTblSal.setViewportView(tblDatSal);

        panTbl.add(spnTblSal, java.awt.BorderLayout.CENTER);

        jPanel5.add(panTbl, java.awt.BorderLayout.CENTER);

        panTblTot.setLayout(new java.awt.BorderLayout());

        panTblTot.setPreferredSize(new java.awt.Dimension(10, 35));
        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTblTot.setViewportView(tblTot);

        panTblTot.add(spnTblTot, java.awt.BorderLayout.CENTER);

        jPanel5.add(panTblTot, java.awt.BorderLayout.SOUTH);

        panFrmDat.add(jPanel5, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("tab2", panFrmDat);

        panGrl.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panGrl, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        panPie.setLayout(new java.awt.BorderLayout());

        panPie.setPreferredSize(new java.awt.Dimension(200, 56));
        panTooBar.setLayout(new java.awt.BorderLayout());

        panTooBar.setBorder(new javax.swing.border.EtchedBorder());
        panTooBar.setPreferredSize(new java.awt.Dimension(10, 44));
        panPie.add(panTooBar, java.awt.BorderLayout.CENTER);

        jPanel2.add(panPie, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-714)/2, (screenSize.height-464)/2, 714, 464);
    }//GEN-END:initComponents

    private void exitFrm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitFrm
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            dispose();
    }//GEN-LAST:event_exitFrm

    private void butRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRepActionPerformed
        // TODO add your handling code here:
        mostrarVenConRep(0);
    }//GEN-LAST:event_butRepActionPerformed

    private void txtNomRepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRepFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomRep.getText().equalsIgnoreCase(strNomRep))
        {
            if (txtNomRep.getText().equals(""))
            {
                txtCodRep.setText("");
            }
            else
            {
                mostrarVenConRep(2);
            }
        }
        else
            txtNomRep.setText(strNomRep);
    }//GEN-LAST:event_txtNomRepFocusLost

    private void txtNomRepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomRepFocusGained
        // TODO add your handling code here:
        strNomRep=txtNomRep.getText();
        txtNomRep.selectAll();
    }//GEN-LAST:event_txtNomRepFocusGained

    private void txtNomRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomRepActionPerformed
        // TODO add your handling code here:
        txtNomRep.transferFocus();
    }//GEN-LAST:event_txtNomRepActionPerformed

    private void txtCodRepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRepFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodRep.getText().equalsIgnoreCase(strCodRep))
        {
            if (txtCodRep.getText().equals(""))
                txtNomRep.setText("");
            else{
                mostrarVenConRep(1);
            }
        }
        else{
            txtCodRep.setText(strCodRep);
        }
    }//GEN-LAST:event_txtCodRepFocusLost

    private void txtCodRepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRepFocusGained
        // TODO add your handling code here:
        strCodRep=txtCodRep.getText();
        txtCodRep.selectAll();
    }//GEN-LAST:event_txtCodRepFocusGained

    private void txtCodRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodRepActionPerformed
        // TODO add your handling code here:
        txtCodRep.transferFocus();
    }//GEN-LAST:event_txtCodRepActionPerformed

    private void optTodDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodDiaActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        arlDat.clear();
        arlDatIns.clear();
        if(txtFiltroReporPersonalizado()){
            if(eliminaColTblDatAdicionadas()){
                if(getAnioMesDesdeHasta()){
                    agregarColTblDat();
                    consultaRegPresupValorInicial();
                        for(int i=0; i<objTblModSal.getRowCountTrue(); i++)
                            for(int j=(INT_TBL_DAT_TIP_DSC+1); j<tblDatSal.getColumnCount(); j++)
                                calcula(i, j);
                    calculaTotales();
                    calculaTblDatosColumnaTotalesInsercion();
                    tabFrm.setSelectedIndex(1);                    
                }
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCon;
    private javax.swing.JButton butRep;
    private javax.swing.JCheckBox chkCtaEsp;
    private javax.swing.JCheckBox chkCtaNotAgr;
    private javax.swing.JCheckBox chkMosSalAntPorMes;
    private javax.swing.JCheckBox chkPryMesAct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optDiaCtaEfe;
    private javax.swing.JRadioButton optMesUsaAniAntMesPry;
    private javax.swing.JRadioButton optSalMenAniAntMesPry;
    private javax.swing.JRadioButton optTodDia;
    private javax.swing.JRadioButton optUtiDatPrs;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabFilCfg;
    private javax.swing.JPanel panCabFilDia;
    private javax.swing.JPanel panCabFilFec;
    private javax.swing.JPanel panCabFilFecSel;
    private javax.swing.JPanel panCabFilTit;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilDat;
    private javax.swing.JPanel panFilDatPryMesAct;
    private javax.swing.JPanel panFilDatRadBut;
    private javax.swing.JPanel panFrmDat;
    private javax.swing.JPanel panFrmGrl;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTblTot;
    private javax.swing.JPanel panTooBar;
    private javax.swing.JScrollPane spnTblSal;
    private javax.swing.JScrollPane spnTblTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatSal;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCfg;
    private javax.swing.JTextField txtCodRep;
    private javax.swing.JTextField txtNomRep;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1 ");
            lblTit.setText(strAux);
            
            objSelDat=new ZafSelectDate(new javax.swing.JFrame(),"d/m/y");
            objSelDat.chkSetSelected(true);
            panCabFilFecSel.add(objSelDat);
            objSelDat.setEnabled(false);
            objSelDat.setBounds(2, 2, 560, 80);
            objTooBar=new MiToolBar(this);
            panTooBar.add(objTooBar);
            
            vecDat=new Vector();    //Almacena los datos
            vecDat.clear();
            
            vecDatSal=new Vector();    //Almacena los datos
            vecDatSal.clear();

            arlDatIni=new ArrayList();
            arlDatIni.clear();
            
            
            arlDatIns=new ArrayList();
            arlDatIns.clear();
            txtCfg.setEditable(false);
            txtCfg.setBackground(objParSis.getColorCamposSistema());
            
            arlDatOcuColCns=new ArrayList();
            arlDatOcuColCns.clear();
            
            tblDatIncDec=new JTable();
//            arlDatTmp=new ArrayList();            
            arlDatAniMes=new ArrayList();
            
            arlDat=new ArrayList();
            arlDat.clear();
            
            
            arlDatConPrc=new ArrayList();
            arlDatConPrc.clear();
            
            
            if(!configurarTblSaldos())
                return false;
            
            configurarVenConRep();
            
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private boolean configurarTblSaldos(){
        boolean blnRes=true;
        try{
            vecCabSal=new Vector(8);  //Almacena las cabeceras
            vecCabSal.clear();
            vecCabSal.add(INT_TBL_DAT_LIN,"");
            vecCabSal.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCabSal.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCabSal.add(INT_TBL_DAT_NOM_CTA,"Nombre Cta.");
            vecCabSal.add(INT_TBL_DAT_BUT_PRC,"...");
            vecCabSal.add(INT_TBL_DAT_NOD_PAD,"Nodo Padre");
            vecCabSal.add(INT_TBL_DAT_NIV,"Nivel");
            vecCabSal.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCabSal.add(INT_TBL_DAT_TIP_DSC,"Tipo Descripción");
            
            objTblModSalIni=new ZafTblMod();
            objTblModSalIni.setHeader(vecCabSal);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatSal.setModel(objTblModSalIni);
            
            objTblModSalTotIni=new ZafTblMod();
            objTblModSalTotIni.setHeader(vecCabSal);
            tblTot.setModel(objTblModSalTotIni);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    private boolean configurarTblSaldosBut(){
        boolean blnRes=true;
        strCodCtaTipDsc="";
        try{
            vecCabSal.clear();
            vecCabSal.add(INT_TBL_DAT_LIN,"");
            vecCabSal.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCabSal.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCabSal.add(INT_TBL_DAT_NOM_CTA,"Nombre Cta.");
            vecCabSal.add(INT_TBL_DAT_BUT_PRC,"...");
            vecCabSal.add(INT_TBL_DAT_NOD_PAD,"Nodo Padre");
            vecCabSal.add(INT_TBL_DAT_NIV,"Nivel");
            vecCabSal.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCabSal.add(INT_TBL_DAT_TIP_DSC,"Tipo Descripción");
            objTblModSal=new ZafTblMod();
            objTblModSal.setHeader(vecCabSal);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatSal.setModel(objTblModSal);
            //Configurar JTable: Establecer tipo de selección.
            tblDatSal.setRowSelectionAllowed(true);
            tblDatSal.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            objTblPopMnu=new ZafTblPopMnu(tblDatSal);
            objColNumSal=new ZafColNumerada(tblDatSal,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatSal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
//            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_BUT_PRC).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_DAT_NOD_PAD).setPreferredWidth(50);
//            tcmAux.getColumn(INT_TBL_DAT_NIV).setPreferredWidth(50);
//            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
//            tcmAux.getColumn(INT_TBL_DAT_TIP_DSC).setPreferredWidth(50);            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatSal.getTableHeader().setReorderingAllowed(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_NOD_PAD).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOD_PAD).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOD_PAD).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOD_PAD).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOD_PAD).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_NIV).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NIV).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NIV).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NIV).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NIV).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setResizable(false);            
            
            tcmAux.getColumn(INT_TBL_DAT_TIP_DSC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DSC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DSC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DSC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DSC).setResizable(false);
            
            objMouMotAdaSal=new ZafMouMotAdaSaldos();
            tblDatSal.getTableHeader().addMouseMotionListener(objMouMotAdaSal);
            //Configurar JTable: Establecer columnas editables.
            vecAuxSal=new Vector();
            vecAuxSal.add("" + INT_TBL_DAT_BUT_PRC);           
            objTblModSal.setColumnasEditables(vecAuxSal);
            vecAuxSal=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdiSal=new ZafTblEdi(tblDatSal);
            
            objTblCelRenLblSal=new ZafTblCelRenLbl();
            objTblCelRenLblSal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSal.setTipoFormato(objTblCelRenLblSal.INT_FOR_NUM);
            objTblCelRenLblSal.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblSal=null;
            tcmAux=null;
                        
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDatSal.getColumnModel().getColumn(INT_TBL_DAT_BUT_PRC).setCellRenderer(objTblCelRenBut);
            objCon48_01=new ZafCon48_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, objTooBar.getEstado());
            objTblCelEdiBut= new ZafTblCelEdiButDlg(objCon48_01);
            tblDatSal.getColumnModel().getColumn(INT_TBL_DAT_BUT_PRC).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTooBar.getEstado()=='n'){
                        strCodCtaTipDsc=""+( objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_NUM_CTA)==null?"":objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_NUM_CTA)  );
                        if(strCodCtaTipDsc.toString().equals(""))
                            objTblCelEdiBut.setCancelarEdicion(true);
                        else{
                            objTblCelEdiBut.setCancelarEdicion(false);
                            getAnioMesDesdeHasta();
                            objCon48_01.setAniMes(intAniDes, intAniHas, intMesDes, intMesHas);
                        }                        
                    }
                    //POR MODIFICACION
                    else{
                        strCodCtaTipDsc=""+( objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_NUM_CTA)==null?"":objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_NUM_CTA)  );
                        if(strCodCtaTipDsc.toString().equals(""))
                            objTblCelEdiBut.setCancelarEdicion(true);
                        else{
                            objTblCelEdiBut.setCancelarEdicion(false);
                            objCon48_01.setAniMes((objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_ANI_MIN)),(objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_ANI_MAX)),(objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MIN)),(objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MAX)));
                            objCon48_01.llenaTablaPorcDb(txtCodRep.getText(), txtCfg.getText(), ""+objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_COD_REG));                            
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objCon48_01.isModChange()){
                        insertaArl();
                        if(objTooBar.getEstado()=='n')
                            calculaPorcentajes();
                        else
                            calculaPorcentajesModificacion();
                        arlDat.clear();
                        for(int i=0; i<objTblModSal.getRowCountTrue(); i++)
                            for(int j=(INT_TBL_DAT_TIP_DSC+1); j<tblDatSal.getColumnCount(); j++)
                                calcula(i, j);
                        
                    }
                    
                }
            });
            objTblModSal.setModoOperacion(objTblModSal.INT_TBL_EDI);
//PARA LA TABLA DE TOTALES                                 
            objTblModTot=new ZafTblMod();
            objTblModTot.setHeader(vecCabSal);
            tblTot.setModel(objTblModTot);
//            tblTot.getTableHeader().setReorderingAllowed(false);
//            tblTot.setTableHeader(null);
            //Configurar JTable: Establecer tipo de selección.
            tblTot.setRowSelectionAllowed(true);
            tblTot.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblTot,INT_TBL_DAT_LIN);
            objTblPopMnu=new ZafTblPopMnu(tblTot);
            tblTot.setAutoResizeMode(tblDatSal.getAutoResizeMode());
            tcmAux=tblTot.getColumnModel();        
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                        
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_BUT_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOD_PAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NIV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DSC).setPreferredWidth(50);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaSal=new ZafMouMotAdaSaldos();
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblSalTot=new ZafTblCelRenLbl();
            objTblCelRenLblSalTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLblSalTot.setTipoFormato(objTblCelRenLblSalTot.INT_FOR_NUM);
            objTblCelRenLblSalTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblSalTot=null;
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDatSal.getColumnCount(); j++){
                tcmAux.getColumn(j).setWidth(tblDatSal.getColumnModel().getColumn(j).getWidth());
                tcmAux.getColumn(j).setMaxWidth(tblDatSal.getColumnModel().getColumn(j).getMaxWidth());
                tcmAux.getColumn(j).setMinWidth(tblDatSal.getColumnModel().getColumn(j).getMinWidth());
                tcmAux.getColumn(j).setPreferredWidth(tblDatSal.getColumnModel().getColumn(j).getPreferredWidth());
                tcmAux.getColumn(j).setResizable(tblDatSal.getColumnModel().getColumn(j).getResizable());
            }
            tcmAux=null;  
//////FIN TOTALES///
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnTblTot.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//            tblTot.setTableHeader(null);
  
            //Adicionar el listener que controla el redimensionamiento de las columnas.
//            ZafTblColModLis objTblColModLisCta=new ZafTblColModLis();
//            tblDatSal.getColumnModel().addColumnModelListener(objTblColModLisCta);
            
            System.out.println("TABLA LISTENER: " +tblDatSal.getColumnCount());

            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDatCta=spnTblSal.getHorizontalScrollBar();
            barDatTot=spnTblTot.getHorizontalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            barDatCta.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {                    
                    barDatTot.setValue(evt.getValue());
                }
            });
            barDatTot.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatCta.setValue(evt.getValue());
                }
            });
            tcmAux=null;            
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean leePorcentIncremDecrem(){
        boolean blnRes=true;
        tblDatIncDec=objCon48_01.getTableIncDec();
        try{
            for(int i=0; i<objCon48_01.numFilTabla(); i++){
                System.out.println("ANIO: " + tblDatIncDec.getValueAt(i, INT_TBL_ANI));
                System.out.println("MES: " + tblDatIncDec.getValueAt(i, INT_TBL_MES));
                System.out.println("PORCENTAJE: " + tblDatIncDec.getValueAt(i, INT_TBL_PRC));
                //AQUI SE DEBE REFRESCAR LA FUNCION CARGARREG
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
   private class MiToolBar extends ZafToolBar{
       public MiToolBar(javax.swing.JInternalFrame ifrFrm){
        super(ifrFrm, objParSis);
       }

       public boolean aceptar() {
           return true;
       }
       
       public boolean afterAceptar() {
           return true;
       }
       
       public boolean afterAnular() {
           return true;
       }
       
       public boolean afterCancelar() {
           return true;
       }
       
       public boolean afterConsultar() {
//           for(int i=0; i<objTblModSal.getRowCountTrue(); i++)
//               for(int j=(INT_TBL_DAT_TIP_DSC+1); j<tblDatSal.getColumnCount(); j++)
//                   calcula(i, j);
           
//           ocultarColumnas();
           objTblModSal.initRowsState();
           tabFrm.setSelectedIndex(1);
           return true;
       }
       
       public boolean afterEliminar() {
           return true;
       }
       
       public boolean afterImprimir() {
           return true;
       }
       
       public boolean afterInsertar() {
           objTooBar.setEstado('w');
           return true;
       }
       
       public boolean afterModificar() {
           arlDat.clear();
           arlDatIns.clear();
           objTblModSal.initRowsState();
           return true;
       }
       
       public boolean afterVistaPreliminar() {
           return true;
       }
       
       public boolean anular() {
           return true;
       }
       
       public boolean beforeAceptar() {
           return true;
       }
       
       public boolean beforeAnular() {
           return true;
       }
       
       public boolean beforeCancelar() {
           return true;
       }
       
       public boolean beforeConsultar() {
//           if(eliminaColTblDatAdicionadas()){
//               if(getAnioMesDesdeHastaConsulta()){
//                   agregarColTblDat();
//               }
//           }
           return true;
       }
       
       public boolean beforeEliminar() {
           return true;
       }
       
       public boolean beforeImprimir() {
           return true;
       }
       
       public boolean beforeInsertar() {
           if(!isCamModSal())
               return false;
           return true;
       }
       
       public boolean beforeModificar() {
           if(!isCamModSal())
               return false;
           return true;
       }
       
       public boolean beforeVistaPreliminar() {
           return true;
       }
       
       public boolean cancelar() {
           return true;
       }
       
       public void clickAceptar() {
       }
       
       public void clickAnterior() {
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
       }
       
       public void clickAnular() {
       }
       
       public void clickCancelar() {
           limpiarFrm();
           if(objTblModSal==null)
               objTblModSalIni.removeAllRows();
           else
               objTblModSal.removeAllRows();

           if(objTblModTot==null){
               objTblModSalTotIni.removeAllRows();
//               objTblModSalTotIni.insertRow();
           }
           else{
               objTblModTot.removeAllRows();
//               objTblModTot.insertRow();
           }

       }
       
       public void clickConsultar() {
           txtCfg.setEditable(true);
           butCon.setEnabled(false);
       }
       
       public void clickEliminar() {
       }
       
       public void clickFin() {
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
       }
       
       public void clickImprimir() {
       }
       
       public void clickInicio(){
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
       }
       
       public void clickInsertar() {
           txtCfg.setEditable(false);
//           objTblModSalIni.setModoOperacion(objTblModSal.INT_TBL_EDI);
       }
       
       public void clickModificar() {
           objTblModSal.setModoOperacion(objTblModSal.INT_TBL_EDI);
       }
       
       public void clickSiguiente() {
           if(objTblModSal.isDataModelChanged())
               blnHayCam=true;
           else
               blnHayCam=false;
           
           try{                
                
                if (!rstCab.isLast()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
       }
       
       public void clickVisPreliminar() {
       }
       
       public boolean consultar() {
           consultarReg();
           return true;
       }
       
       public boolean eliminar() {
           return true;
       }
       
       public boolean imprimir() {
           return true;
       }
       
       public boolean insertar() {
           if (!insertarReg())
               return false;
           return true;
       }
       
       public boolean modificar() {
            if (!actualizarReg())
                return false;
           return true;
       }
       
       public boolean vistaPreliminar() {
           return true;
       }
   }
   
   
    private boolean configurarVenConRep(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_estFin");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre Reporte");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_estFin, a1.tx_nom";
            strSQL+=" FROM tbm_cabEstFinPer AS a1";
            strSQL+=" WHERE ";
            strSQL+=" a1.co_emp IN (" + objParSis.getCodigoEmpresa() + ")";
            strSQL+=" AND st_reg not in ('I', 'E')";
            strSQL+=" ORDER BY a1.co_estFin";
            vcoTipRep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Reportes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipRep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipRep.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    private boolean mostrarVenConRep(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipRep.setCampoBusqueda(1);
                    vcoTipRep.show();
                    if (vcoTipRep.getSelectedButton()==vcoTipRep.INT_BUT_ACE){
                        txtCodRep.setText(vcoTipRep.getValueAt(1));
                        txtNomRep.setText(vcoTipRep.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipRep.buscar("a1.co_estFin", txtCodRep.getText()))
                    {
                        txtCodRep.setText(vcoTipRep.getValueAt(1));
                        txtNomRep.setText(vcoTipRep.getValueAt(2));
                    }
                    else
                    {
                        vcoTipRep.setCampoBusqueda(0);
                        vcoTipRep.setCriterio1(11);
                        vcoTipRep.cargarDatos();
                        vcoTipRep.show();
                        if (vcoTipRep.getSelectedButton()==vcoTipRep.INT_BUT_ACE)
                        {
                            txtCodRep.setText(vcoTipRep.getValueAt(1));
                            txtNomRep.setText(vcoTipRep.getValueAt(2));
                        }
                        else
                        {
                            txtCodRep.setText(strCodRep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipRep.buscar("a1.tx_nom", txtNomRep.getText()))
                    {
                        txtCodRep.setText(vcoTipRep.getValueAt(1));
                        txtNomRep.setText(vcoTipRep.getValueAt(2));
                    }
                    else
                    {
                        vcoTipRep.setCampoBusqueda(1);
                        vcoTipRep.setCriterio1(11);
                        vcoTipRep.cargarDatos();
                        vcoTipRep.show();
                        if (vcoTipRep.getSelectedButton()==vcoTipRep.INT_BUT_ACE)
                        {
                            txtCodRep.setText(vcoTipRep.getValueAt(1));
                            txtNomRep.setText(vcoTipRep.getValueAt(2));
                        }
                        else
                        {
                            txtNomRep.setText(strNomRep);
                        }
                    }
                    break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
   
   
   
   
   
   
    
    private class ZafMouMotAdaSaldos extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDatSal.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Código Cuenta";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número Cuenta";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre Cuenta";
                    break;
                case INT_TBL_DAT_BUT_PRC:
                    strMsg="Nombre Cuenta";
                    break;
                case INT_TBL_DAT_NOD_PAD:
                    strMsg="Nodo Padre";
                    break;
                case INT_TBL_DAT_NIV:
                    strMsg="Nivel de Grupo o Cuenta";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código de Registro";
                    break;
                case INT_TBL_DAT_TIP_DSC:
                    strMsg="Tipo Descripción";
                    break;
                default:
                    break;
            }
            tblDatSal.getTableHeader().setToolTipText(strMsg);
        }
    }
        
   
    
    private boolean getAnioMesDesdeHasta(){
        boolean blnRes=true;
        intAniDes=0;        intAniHas=0;        intMesDes=0;        intMesHas=0;
        try{
            if (objSelDat.chkIsSelected()){
                switch (objSelDat.getTypeSeletion()){
                    case 1: //Búsqueda por rangos
                          intAniDes=objUti.getAnio(objSelDat.getDateFrom(),"dd/MM/yyyy");
                          intAniHas=objUti.getAnio(objSelDat.getDateTo(),"dd/MM/yyyy");
                          intMesDes=(objUti.getMes(objSelDat.getDateFrom(),"dd/MM/yyyy")+1);
                          intMesHas=(objUti.getMes(objSelDat.getDateTo(),"dd/MM/yyyy")+1);
                          break;
                    case 2: //Busqueda por >= a la fecha Desde
                          intAniDes=objUti.getAnio(objSelDat.getDateFrom(),"dd/MM/yyyy");
                          intAniHas=objUti.getAnio(objSelDat.getDateTo(),"dd/MM/yyyy");
                          intMesDes=(objUti.getMes(objSelDat.getDateFrom(),"dd/MM/yyyy")+1);
                          intMesHas=(objUti.getMes(objSelDat.getDateTo(),"dd/MM/yyyy")+1);
                        break;
                    case 3: //Busqueda por <= a la fecha Hasta
                          intAniDes=objUti.getAnio(objSelDat.getDateFrom(),"dd/MM/yyyy");
                          intAniHas=objUti.getAnio(objSelDat.getDateTo(),"dd/MM/yyyy");
                          intMesDes=(objUti.getMes(objSelDat.getDateFrom(),"dd/MM/yyyy")+1);
                          intMesHas=(objUti.getMes(objSelDat.getDateTo(),"dd/MM/yyyy")+1);
                        break;
                    case 4: //Todo.
                        break;
                }
            }
//            System.out.println("EL ANIO DESDE: " + intAniDes);
//            System.out.println("EL ANIO HASTA: " + intAniHas);
//            System.out.println("EL MES DESDE: " + intMesDes);
//            System.out.println("EL MES HASTA: " + intMesHas);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean getAnioMesDesdeHastaConsulta(){
        boolean blnRes=true;
        intAniDes=0;        intAniHas=0;        intMesDes=0;        intMesHas=0;
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="SELECT MIN(ne_ani) AS aniMin, MAX(ne_ani) AS aniMax, MIN(ne_mes) AS minMes, MAX(ne_mes) AS maxMes";
                strSQL+=" FROM tbm_detcfgestfinper";
                strSQL+=" WHERE co_emp IN (" + objParSis.getCodigoEmpresa() + ")";
                if(! txtCodRep.getText().equals(""))
                    strSQL+=" AND co_estFin=" + txtCodRep.getText() + "";
                if(! txtCfg.getText().equals(""))
                    strSQL+=" AND co_cfg=" + txtCfg.getText() + "";
                
                rstTmp=stmTmp.executeQuery(strSQL);
                if(rstTmp.next()){
                  intAniDes=rstTmp.getInt("aniMin");
                  intAniHas=rstTmp.getInt("aniMax");
                  intMesDes=rstTmp.getInt("minMes");
                  intMesHas=rstTmp.getInt("maxMes");
                }
                conTmp.close();
                conTmp=null;
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
        
    
    
    
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    

    private boolean agregarColTblDat(){
        obtieneArrayListGenerarTabla();
        int z=0,intTmp=0;
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc, tbcTot;
        int intAniArl=0;
        String strNomMesArl="";
        int g=0;
        intFrsColOcu=0;
        numColModIni=0;
        
        
        try{
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLblSalTot=new ZafTblCelRenLbl();
            
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblSalTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSalTot.setTipoFormato(objTblCelRenLblSalTot.INT_FOR_NUM);
            objTblCelRenLblSalTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);            
            
            numColModIni=tblDatSal.getColumnCount();
            numColModIniTot=tblTot.getColumnCount();
            for (int i=0; i<arlDatAniMes.size(); i++){
                intAniArl=objUti.getIntValueAt(arlDatAniMes, i, INT_ARL_ANI);
                strNomMesArl=objUti.getStringValueAt(arlDatAniMes, i, INT_ARL_MES_NOM);
                tbc=new javax.swing.table.TableColumn((numColModIni+i));
                tbcTot=new javax.swing.table.TableColumn((numColModIniTot+i));
                tbc.setHeaderValue(" " + strNomMesArl + " " + intAniArl);
                tbcTot.setHeaderValue(" " + strNomMesArl + " " + intAniArl);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                tbcTot.setPreferredWidth(60);
                if((numColModIni+i)<(  (numColModIni+ intMesDes)-1  )){
                    //Configurar JTable: Ocultar la columna.
                    tbc.setWidth(0);                    tbc.setMaxWidth(0);                    tbc.setMinWidth(0);                    tbc.setPreferredWidth(0);
                    tbc.setResizable(false);
                }
                if((numColModIniTot+i)<(  (numColModIniTot+ intMesDes)-1  )){
                    //Configurar JTable: Ocultar la columna.
                    tbcTot.setWidth(0);                    tbcTot.setMaxWidth(0);                    tbcTot.setMinWidth(0);                    tbcTot.setPreferredWidth(0);
                    tbcTot.setResizable(false);
                }
                else{
                    if(g==0){
                        intFrsColOcu=(numColModIni+i);
                        g++;
                    }                    
                }
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbcTot.setCellRenderer(objTblCelRenLblSalTot);
                objTblModSal.addColumn(tblDatSal, tbc);
            }
            
            //PARA ADICIONAR LA COLUMNA DE TOTAL
            for(int i=1;i<2;i++){
                tbc=new javax.swing.table.TableColumn((numColModIni+i));
                tbcTot=new javax.swing.table.TableColumn((numColModIniTot+i));
                tbc.setHeaderValue("...");
                tbcTot.setHeaderValue("...");
                //Configurar JTable: Establecer el ancho de la columna.
//                tbc.setPreferredWidth(100);
//                tbcTot.setPreferredWidth(100);
                
                tbc.setWidth(0);
                tbc.setMaxWidth(0);
                tbc.setMinWidth(0);
                tbc.setPreferredWidth(0);
                tbc.setResizable(false);  
                
                tbcTot.setWidth(0);
                tbcTot.setMaxWidth(0);
                tbcTot.setMinWidth(0);
                tbcTot.setPreferredWidth(0);
                tbcTot.setResizable(false);
                
                tbc.setCellRenderer(objTblCelRenLbl);
                tbcTot.setCellRenderer(objTblCelRenLblSalTot);
                objTblModSal.addColumn(tblDatSal, tbc);
            }
            numColModFin=tblDatSal.getColumnCount();
            numColModFinTot=tblTot.getColumnCount();
            objTblModTot=objTblModSal;
            tblTot.setModel(objTblModTot);
            tblTot.setTableHeader(null);
            javax.swing.table.TableColumnModel tcmAux=tblTot.getColumnModel();
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDatSal.getColumnCount(); j++){
                tcmAux.getColumn(j).setWidth(tblDatSal.getColumnModel().getColumn(j).getWidth());
                tcmAux.getColumn(j).setMaxWidth(tblDatSal.getColumnModel().getColumn(j).getMaxWidth());
                tcmAux.getColumn(j).setMinWidth(tblDatSal.getColumnModel().getColumn(j).getMinWidth());
                tcmAux.getColumn(j).setPreferredWidth(tblDatSal.getColumnModel().getColumn(j).getPreferredWidth());
                tcmAux.getColumn(j).setResizable(tblDatSal.getColumnModel().getColumn(j).getResizable());
            }
            tcmAux=null;
            
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnTblTot.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnTblTot.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//            tblTot.setTableHeader(null);
            objTblModTot.removeAllRows();
            objTblModTot.insertRow();
            objTblModSal.initRowsState();
            objTblModTot.initRowsState();
            
            objColNumSal=new ZafColNumerada(tblTot,INT_TBL_DAT_LIN);    
            
            
            ZafTblColModLis objTblColModLisCta=new ZafTblColModLis();
            tblDatSal.getColumnModel().addColumnModelListener(objTblColModLisCta);
            
            
            
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }        
                        
        return blnRes;
    }
    
    
    private boolean obtieneArrayListGenerarTabla(){
        boolean blnRes=true;
        int intAniDif=0;
        int intAniPrcInt=0;
        arlDatAniMes.clear();
        try{
            if(intAniDes==intAniHas){
                for(int i=1; i<=intMesHas; i++){
                    arlRegAniMes=new ArrayList();
                    arlRegAniMes.add(INT_ARL_ANI, "" + intAniDes);
                    arlRegAniMes.add(INT_ARL_MES, "" + i);
                    switch(i){
                        case 1:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "ENERO");
                            break;
                        case 2:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "FEBRERO");
                            break;
                        case 3:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "MARZO");
                            break;
                        case 4:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "ABRIL");
                            break;
                        case 5:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "MAYO");
                            break;
                        case 6:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "JUNIO");
                            break;
                        case 7:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "JULIO");
                            break;
                        case 8:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "AGOSTO");
                            break;
                        case 9:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "SEPTIEMBRE");
                            break;
                        case 10:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "OCTUBRE");
                            break;
                        case 11:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "NOVIEMBRE");
                            break;
                        case 12:
                            arlRegAniMes.add(INT_ARL_MES_NOM, "DICIEMBRE");
                            break;
                    }
                    arlDatAniMes.add(arlRegAniMes);
                }
            }
            ////////////////////////////////////////////////////
            if(intAniDes!=intAniHas){
                intAniDif=intAniHas-intAniDes;
                ////////////////////////////////////////////////////
                for(int i=intAniDes; i<=intAniDes;i++){
                    //para meses
                    for(int j=1; j<=12;j++){
                        arlRegAniMes=new ArrayList();
                        arlRegAniMes.add(INT_ARL_ANI, "" + intAniDes);
                        arlRegAniMes.add(INT_ARL_MES, "" + j);
                        switch(j){
                            case 1:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "ENERO");
                                break;
                            case 2:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "FEBRERO");
                                break;
                            case 3:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "MARZO");
                                break;
                            case 4:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "ABRIL");
                                break;
                            case 5:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "MAYO");
                                break;
                            case 6:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "JUNIO");
                                break;
                            case 7:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "JULIO");
                                break;
                            case 8:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "AGOSTO");
                                break;
                            case 9:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "SEPTIEMBRE");
                                break;
                            case 10:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "OCTUBRE");
                                break;
                            case 11:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "NOVIEMBRE");
                                break;
                            case 12:
                                arlRegAniMes.add(INT_ARL_MES_NOM, "DICIEMBRE");
                                break;
                        }                        
                        arlDatAniMes.add(arlRegAniMes);
                    }
                }
                ////////////////////////////////////////////////////
                if(intAniDif>1){
                    for(int k=(intAniDes+1); k<=(intAniHas-1); k++){
                        for(int j=1; j<=12;j++){
                            arlRegAniMes=new ArrayList();
                            arlRegAniMes.add(INT_ARL_ANI, "" + k);
                            arlRegAniMes.add(INT_ARL_MES, "" + j);
                            switch(j){
                                case 1:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "ENERO");
                                    break;
                                case 2:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "FEBRERO");
                                    break;
                                case 3:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "MARZO");
                                    break;
                                case 4:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "ABRIL");
                                    break;
                                case 5:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "MAYO");
                                    break;
                                case 6:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "JUNIO");
                                    break;
                                case 7:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "JULIO");
                                    break;
                                case 8:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "AGOSTO");
                                    break;
                                case 9:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "SEPTIEMBRE");
                                    break;
                                case 10:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "OCTUBRE");
                                    break;
                                case 11:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "NOVIEMBRE");
                                    break;
                                case 12:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "DICIEMBRE");
                                    break;
                            }
                            arlDatAniMes.add(arlRegAniMes);
                        }
                    }                    
                }
                ////////////////////////////////////////////////////
                for(int i=intAniHas; i<=intAniHas;i++){
                    for(int j=1; j<=intMesHas;j++){
                        arlRegAniMes=new ArrayList();
                        arlRegAniMes.add(INT_ARL_ANI, "" + intAniHas);
                        arlRegAniMes.add(INT_ARL_MES, "" + j);
                            switch(j){
                                case 1:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "ENERO");
                                    break;
                                case 2:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "FEBRERO");
                                    break;
                                case 3:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "MARZO");
                                    break;
                                case 4:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "ABRIL");
                                    break;
                                case 5:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "MAYO");
                                    break;
                                case 6:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "JUNIO");
                                    break;
                                case 7:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "JULIO");
                                    break;
                                case 8:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "AGOSTO");
                                    break;
                                case 9:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "SEPTIEMBRE");
                                    break;
                                case 10:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "OCTUBRE");
                                    break;
                                case 11:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "NOVIEMBRE");
                                    break;
                                case 12:
                                    arlRegAniMes.add(INT_ARL_MES_NOM, "DICIEMBRE");
                                    break;
                            }
                        arlDatAniMes.add(arlRegAniMes);
                    }
                }
                ////////////////////////////////////////////////////
            }
//            System.out.println("ARRAYLIST COMPLETO: " + arlDatAniMes.toString());
        }
        catch(Exception e){
            System.out.println("ERROR: " +e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean consultaRegPresupValorInicial(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(objParSis.getCodigoEmpresa()!=0){
                    if(consultarPresupValorInicial()){
                        System.out.println("OK");
                    }                    
                }
                else{
                    if(consultPresupValInicGrup_Descrip()){
                        System.out.println("OK");
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
    
    private boolean consultarPresupValorInicial(){
        boolean blnRes=true;
        strAux="";
        int intAniDifLoc=0;
        int flag=0;
        int intCodRegIni=0, intCodRegFin=0 ;
        int intCol=1;
        intCol=(intCol+INT_ARL_INI_SAL_DIC);
        arlDatIni.clear();
        String strVarTmp="";
        int intNivPad=0;
        String strAuxTmp="";
        String strCodCtaCmp="";
        try{
            if( ! (txtCodRep.getText().equals(""))  ){
                strVarTmp=" AND a1.co_estFin=" + txtCodRep.getText() + "";
            }
            
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT q.co_emp, q.co_cta, q.tx_codcta, q.descripc, q.co_reg, q.tx_tip, q.ne_ubi, q.ne_pad, q.ne_niv, q.ne_ani, ";
                strSQL+=" SUM(q.salEne) AS salEne, SUM(q.salFeb) AS salFeb, SUM(q.salMar) AS salMar, SUM(q.salAbr) AS salAbr, SUM(q.salMay) AS salMay, SUM(q.salJun) AS salJun,";
                strSQL+=" SUM(q.salJul) AS salJul, SUM(q.salAgo) AS salAgo, SUM(q.salSep) AS salSep, SUM(q.salOct) AS salOct, SUM(q.salNov) AS salNov, SUM(q.salDic) AS salDic";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT z.co_emp, z.co_cta, z.tx_codcta, z.descripc, z.co_reg, z.tx_tip, z.ne_pad, z.ne_niv, z.ne_ani, z.ne_ubi,";
                strSQL+=" 	CASE WHEN z.ne_mes=1 THEN SUM(z.nd_val) END AS salEne,";
                strSQL+=" 	CASE WHEN z.ne_mes=2 THEN SUM(z.nd_val) END AS salFeb,";
                strSQL+=" 	CASE WHEN z.ne_mes=3 THEN SUM(z.nd_val) END AS salMar,";
                strSQL+=" 	CASE WHEN z.ne_mes=4 THEN SUM(z.nd_val) END AS salAbr,";
                strSQL+=" 	CASE WHEN z.ne_mes=5 THEN SUM(z.nd_val) END AS salMay,";
                strSQL+=" 	CASE WHEN z.ne_mes=6 THEN SUM(z.nd_val) END AS salJun,";
                strSQL+=" 	CASE WHEN z.ne_mes=7 THEN SUM(z.nd_val) END AS salJul,";
                strSQL+=" 	CASE WHEN z.ne_mes=8 THEN SUM(z.nd_val) END AS salAgo,";
                strSQL+=" 	CASE WHEN z.ne_mes=9 THEN SUM(z.nd_val) END AS salSep,";
                strSQL+=" 	CASE WHEN z.ne_mes=10 THEN SUM(z.nd_val) END AS salOct,";
                strSQL+=" 	CASE WHEN z.ne_mes=11 THEN SUM(z.nd_val) END AS salNov,";
                strSQL+=" 	CASE WHEN z.ne_mes=12 THEN SUM(z.nd_val) END AS salDic";
                strSQL+=" 	FROM(";
                
                strSQL+="SELECT * FROM(";
                strSQL+=" SELECT b1.co_emp, b1.co_cta, b1.tx_codcta, b1.descripc, b1.co_reg, b1.tx_tip, b1.ne_pad, b1.ne_niv, b2.ne_ani,b2.ne_mes, b1.ne_ubi,";
                strSQL+=" b2.nd_val FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_cta, b1.tx_codcta,";
                strSQL+=" 	CASE WHEN b1.tx_desLar IS NULL THEN a2.tx_nom";
                strSQL+=" 	ELSE b1.tx_desLar END AS descripc, a2.co_reg, a2.tx_tip, a2.ne_pad,a2.ne_niv, a4.ne_ani, a2.ne_ubi";
                strSQL+=" 	FROM tbm_cabEstFinPer AS a1 INNER JOIN tbm_detEstFinPer AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_estFin=a2.co_estFin";
                strSQL+=" 	LEFT OUTER JOIN tbm_plaCta as b1 ON a2.co_emp=b1.co_emp AND a2.co_cta=b1.co_cta";
                strSQL+=" 	LEFT OUTER JOIN tbm_detEstFinPre AS a4";
                strSQL+=" 	ON b1.co_emp=a4.co_emp AND b1.co_cta=a4.co_cta";
                strSQL+=" 	LEFT OUTER JOIN tbm_cabEstFinPre AS a3";
                strSQL+=" 	ON a4.co_emp=a3.co_emp AND a4.ne_ani=a3.ne_ani";
                strSQL+=" 	WHERE a1.co_emp IN (" + objParSis.getCodigoEmpresa() + ")";
                strSQL+="       " + strVarTmp + "";
                strSQL+=" 	GROUP BY b1.co_emp, a4.ne_ani, a2.ne_pad,a2.ne_niv, b1.co_cta, b1.tx_codcta, b1.tx_desLar, a2.tx_nom, a2.co_reg, a2.tx_tip, a2.ne_ubi";
                strSQL+=" 	ORDER BY a4.ne_ani, a2.co_reg, a2.ne_pad, a2.ne_niv) AS b1";
                strSQL+=" 	LEFT OUTER JOIN (";
                strSQL+=" 	SELECT b1.co_emp, b1.co_cta, a2.co_reg, a2.tx_tip, a4.ne_ani, a4.ne_mes,";
                strSQL+=" 	SUM(a4.nd_val) AS nd_val";
                strSQL+=" 	FROM tbm_cabEstFinPer AS a1 INNER JOIN tbm_detEstFinPer AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_estFin=a2.co_estFin";
                strSQL+=" 	LEFT OUTER JOIN tbm_plaCta as b1 ON a2.co_emp=b1.co_emp AND a2.co_cta=b1.co_cta";
                strSQL+=" 	LEFT OUTER JOIN tbm_detEstFinPre AS a4";
                strSQL+=" 	ON b1.co_emp=a4.co_emp AND b1.co_cta=a4.co_cta";
                strSQL+=" 	LEFT OUTER JOIN tbm_cabEstFinPre AS a3";
                strSQL+=" 	ON a4.co_emp=a3.co_emp AND a4.ne_ani=a3.ne_ani";
                strSQL+=" 	WHERE a1.co_emp IN (" + objParSis.getCodigoEmpresa() + ")";
                strSQL+="       " + strVarTmp + "";
                if(intAniDes==intAniHas){
                    strAux+=" AND a4.ne_ani = " + intAniDes + " AND a4.ne_mes BETWEEN " + intMesDes + " AND " + intMesHas + "";
                }
                if(intAniDes!=intAniHas){
                    intAniDifLoc=intAniHas-intAniDes;
                    strAux+=" AND a4.ne_ani = " + intAniDes + " AND a4.ne_mes BETWEEN " + intMesDes + " AND 12";
                    if(intAniDifLoc>1){
                        strAux+=" OR a4.ne_ani IN( " + (intAniDes+1) + ", " + (intAniHas-1) + ") AND a4.ne_mes BETWEEN 1 AND 12";
                    }
                    strAux+=" OR a4.ne_ani = " + intAniHas + " AND a4.ne_mes BETWEEN 1 AND " + intMesHas + "";
                }
                strSQL+=strAux;
                strAux="";
                
                strSQL+=" 	AND a2.co_empCfgGrp IS NULL";               
                strSQL+=" 	GROUP BY b1.co_emp, a4.ne_ani, a4.ne_mes, b1.co_cta, a2.co_reg, a2.tx_tip";
                strSQL+=" 	ORDER BY a4.ne_ani, a2.co_reg) AS b2";
                strSQL+=" 	ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta AND b1.co_reg=b2.co_reg AND b1.ne_ani=b2.ne_ani)";
                strSQL+=" ) AS w";
                strSQL+=" ORDER BY w.co_reg, w.ne_ani, w.ne_mes, w.ne_pad, w.ne_niv";
                
                strSQL+=" 	) AS z";
                strSQL+=" 	GROUP BY z.co_emp, z.co_cta, z.tx_codcta, z.descripc, z.co_reg, z.tx_tip, z.ne_pad, z.ne_niv, z.ne_ani, z.ne_mes, z.ne_ubi";
                strSQL+=" 	ORDER BY z.co_reg, z.ne_ani, z.ne_pad, z.ne_niv";
                strSQL+=" ) AS q";
                strSQL+=" GROUP BY q.co_emp, q.co_cta, q.tx_codcta, q.descripc, q.co_reg, q.tx_tip, q.ne_pad, q.ne_niv, q.ne_ani, q.ne_ubi";
                strSQL+=" ORDER BY q.ne_ubi, q.ne_ani, q.ne_pad, q.ne_niv";
                System.out.println("consultarPresupValorInicial: " + strSQL);
                rst=stm.executeQuery(strSQL);
                int f=-1;
                
                while(rst.next()){
                    if(flag==0){
                        f++;
                        strDat[f][INT_ARL_INI_COD_EMP]=(rst.getString("co_emp")==null?"":rst.getString("co_emp"));
                        intCodRegIni=(rst.getString("co_reg")==null?0:rst.getInt("co_reg"));
                        strDat[f][INT_ARL_INI_COD_CTA]=(rst.getString("co_cta")==null?"":rst.getString("co_cta"));
                        strCodCtaCmp=(rst.getString("co_cta")==null?"":rst.getString("co_cta"));
                        strDat[f][INT_ARL_INI_NUM_CTA]=(rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
                                                
                        intNivPad=rst.getInt("ne_niv");
                        strAuxTmp="";
                        for (int h=1; h<intNivPad; h++)
                            strAuxTmp+="    ";
                        strDat[f][INT_ARL_INI_NOM_DSC]= strAuxTmp + "" + (rst.getString("descripc")==null?"":rst.getString("descripc"));
                        //strDat[f][INT_ARL_INI_NOM_DSC]=(rst.getString("descripc")==null?"":rst.getString("descripc"));
                        strDat[f][INT_ARL_INI_COD_REG]=(rst.getString("co_reg")==null?"0":"" + rst.getInt("co_reg"));
                        strDat[f][INT_ARL_INI_NOD_PAD]=(rst.getString("ne_pad")==null?"0":"" + rst.getInt("ne_pad"));
                        strDat[f][INT_ARL_INI_NIV_DSC]=(rst.getString("ne_niv")==null?"0":"" + rst.getInt("ne_niv"));
                        strDat[f][INT_ARL_INI_ANI]=(rst.getString("ne_ani")==null?"0":"" + rst.getInt("ne_ani"));
                        strDat[f][INT_ARL_INI_TIP_DSC]=(rst.getString("tx_tip")==null?"":"" + rst.getString("tx_tip"));
                        strDat[f][INT_ARL_INI_SAL_ENE]=(rst.getString("salEne")==null?"0.00":"" + rst.getDouble("salEne"));
                        strDat[f][INT_ARL_INI_SAL_FEB]=(rst.getString("salFeb")==null?"0.00":"" + rst.getDouble("salFeb"));
                        strDat[f][INT_ARL_INI_SAL_MAR]=(rst.getString("salMar")==null?"0.00":"" + rst.getDouble("salMar"));
                        strDat[f][INT_ARL_INI_SAL_ABR]=(rst.getString("salAbr")==null?"0.00":"" + rst.getDouble("salAbr"));
                        strDat[f][INT_ARL_INI_SAL_MAY]=(rst.getString("salMay")==null?"0.00":"" + rst.getDouble("salMay"));
                        strDat[f][INT_ARL_INI_SAL_JUN]=(rst.getString("salJun")==null?"0.00":"" + rst.getDouble("salJun"));
                        strDat[f][INT_ARL_INI_SAL_JUL]=(rst.getString("salJul")==null?"0.00":"" + rst.getDouble("salJul"));
                        strDat[f][INT_ARL_INI_SAL_AGO]=(rst.getString("salAgo")==null?"0.00":"" + rst.getDouble("salAgo"));
                        strDat[f][INT_ARL_INI_SAL_SEP]=(rst.getString("salSep")==null?"0.00":"" + rst.getDouble("salSep"));
                        strDat[f][INT_ARL_INI_SAL_OCT]=(rst.getString("salOct")==null?"0.00":"" + rst.getDouble("salOct"));
                        strDat[f][INT_ARL_INI_SAL_NOV]=(rst.getString("salNov")==null?"0.00":"" + rst.getDouble("salNov"));
                        strDat[f][INT_ARL_INI_SAL_DIC]=(rst.getString("salDic")==null?"0.00":"" + rst.getDouble("salDic"));
                        
//                        strDat[f][INT_ARL_INI_TIP_DSC]=(rst.getString("tx_tip")==null?"":"" + rst.getString("tx_tip"));
                        flag++;
                    }
                    else{
                        intCodRegFin=(rst.getString("co_reg")==null?0:rst.getInt("co_reg"));
                        if(intCodRegIni==intCodRegFin){
                            strDat[f][intCol]=(rst.getString("salEne")==null?"0.00":"" + rst.getDouble("salEne"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salFeb")==null?"0.00":"" + rst.getDouble("salFeb"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salMar")==null?"0.00":"" + rst.getDouble("salMar"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salAbr")==null?"0.00":"" + rst.getDouble("salAbr"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salMay")==null?"0.00":"" + rst.getDouble("salMay"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salJun")==null?"0.00":"" + rst.getDouble("salJun"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salJul")==null?"0.00":"" + rst.getDouble("salJul"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salAgo")==null?"0.00":"" + rst.getDouble("salAgo"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salSep")==null?"0.00":"" + rst.getDouble("salSep"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salOct")==null?"0.00":"" + rst.getDouble("salOct"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salNov")==null?"0.00":"" + rst.getDouble("salNov"));
                            intCol++;
                            strDat[f][intCol]=(rst.getString("salDic")==null?"0.00":"" + rst.getDouble("salDic"));
                        }
                        else{
                            flag=0;
                            intCol=1;
                            intCol=(intCol+INT_ARL_INI_SAL_DIC);
                            rst.previous();
                            
                        }
                    }
                    
                }
                System.out.println("VARIABLE F CONTIENE: " + intNumFilStrDat);
                intNumFilStrDat=(f+1);
                cargarTablaPresupValorInicial();
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
    
    
    
    
    private boolean consultPresupValInicGrup_Descrip(){
        boolean blnRes=true;
        strAux="";
        int intAniDifLoc=0;
        int flag=0;
        int intCodRegIni=0, intCodRegFin=0 ;
        int intCol=1;
        intCol=(intCol+INT_ARL_INI_SAL_DIC);
        arlDatIni.clear();
        String strVarTmp="";
        int intNivPad=0;
        String strAuxTmp="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_estFin, a1.co_reg, a1.ne_ubi, a1.tx_tip,";
                strSQL+=" a1.ne_pad, a1.ne_niv, a1.co_cta, a1.co_empCfgGrp, a2.tx_codCta,";
                strSQL+=" CASE WHEN a1.co_cta IS NULL THEN a1.tx_nom ELSE a2.tx_desLar END AS tx_nom";
                strSQL+=" FROM tbm_detEstFinPer AS a1 LEFT OUTER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_empCfgGrp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_estFin=" + txtCodRep.getText() + "";
                strSQL+=" ORDER BY a1.ne_ubi";
                System.out.println("SQL DE DESCRIPCION: " + strSQL);
                rst=stm.executeQuery(strSQL);
                int f=-1;
                
                
                
                
                        stmSalDsc=con.createStatement();
                        strSQL="";
                        strSQL+="SELECT e1.codGrp, e1.tx_codCta, TRIM(e1.tx_desLar) AS tx_deslar, e1.ne_ani, SUM(salEne) AS salEne, SUM(salFeb) AS salFeb, SUM(salMar) AS salMar,";
                        strSQL+=" SUM(salAbr) AS salAbr, SUM(salMay) AS salMay, SUM(salJun) AS salJun, SUM(salJul) AS salJul, SUM(salAgo) AS salAgo,";
                        strSQL+=" SUM(salSep) AS salSep, SUM(salOct) AS salOct, SUM(salNov) AS salNov, SUM(salDic) AS salDic FROM(";
                        strSQL+=" 	SELECT d1.codGrp, d1.tx_codCta, d1.tx_desLar, d1.ne_ani,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=1 THEN SUM(d1.nd_val) END AS salEne,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=2 THEN SUM(d1.nd_val) END AS salFeb,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=3 THEN SUM(d1.nd_val) END AS salMar,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=4 THEN SUM(d1.nd_val) END AS salAbr,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=5 THEN SUM(d1.nd_val) END AS salMay,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=6 THEN SUM(d1.nd_val) END AS salJun,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=7 THEN SUM(d1.nd_val) END AS salJul,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=8 THEN SUM(d1.nd_val) END AS salAgo,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=9 THEN SUM(d1.nd_val) END AS salSep,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=10 THEN SUM(d1.nd_val) END AS salOct,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=11 THEN SUM(d1.nd_val) END AS salNov,";
                        strSQL+=" 	CASE WHEN d1.ne_mes=12 THEN SUM(d1.nd_val) END AS salDic FROM(";
                        strSQL+=" 		SELECT c1.codGrp, c1.ne_mes, SUM(c1.nd_val) AS nd_val, c1.tx_codCta, c1.tx_desLar, c1.ne_ani FROM(";
                        strSQL+=" 			SELECT CASE WHEN b2.codGrp IS NULL THEN 0 ELSE b2.codGrp END AS codGrp,";
                        strSQL+=" 				b2.co_estFin, b2.co_reg, b2.co_cta, b2.codEmp,";
                        strSQL+=" 					b3.ne_ani, b3.co_cta as codCtaEmp,";
                        strSQL+=" 					b3.ne_mes, b3.nd_val, b3.tx_codCta, b3.tx_desLar FROM(";
                        strSQL+=" 					SELECT b0.co_emp AS codGrp, b0.co_estFin, b0.co_reg, b1.co_cta, b1.co_emp AS codEmp";
                        strSQL+=" 					FROM(";
                        strSQL+=" 					SELECT b2.co_emp AS codEmpGrp, b2.co_estFin, b2.co_reg,";
                        strSQL+=" 					CASE WHEN b1.co_empRea IS NULL THEN 0 ELSE  b1.co_empRea END AS co_emp, b1.co_cta, b2.ne_ubi";
                        strSQL+=" 					FROM tbm_infFluGrp AS b1 LEFT OUTER JOIN tbm_detEstFinPer AS b2";
                        strSQL+=" 					ON b1.co_emp=b2.co_emp AND b1.co_estFin=b2.co_estFin AND b1.co_reg=b2.co_reg";
                        strSQL+="                                         AND b1.co_empRea=b2.co_empCfgGrp AND b1.co_cta=b2.co_cta";
                        strSQL+=" 					WHERE b1.co_estFin=" + txtCodRep.getText() + ") AS b1";
                        strSQL+=" 					LEFT OUTER JOIN(";
                        strSQL+=" 					SELECT *FROM tbm_detEstFinPer WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_estFin=" + txtCodRep.getText() + ") AS b0";
                        strSQL+=" 					ON b0.co_emp=b1.codEmpGrp AND b0.co_estFin=b1.co_estFin AND b0.co_reg=b1.co_reg";
                        strSQL+=" 			) AS b2";
                        strSQL+=" 			LEFT OUTER JOIN";
                        strSQL+=" 			(";
                        strSQL+=" 				SELECT a1.co_emp, a1.ne_ani, a1.co_cta, a1.ne_mes, a1.nd_val, a2.tx_codCta, a2.tx_desLar";
                        strSQL+=" 				FROM (tbm_detEstFinPre AS a1 LEFT OUTER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" 				LEFT OUTER JOIN tbm_cabEstFinPre AS a3";
                        strSQL+=" 				ON a1.co_emp=a3.co_emp AND a1.ne_ani=a3.ne_ani AND a3.st_reg='A'";
                        
                        System.out.println("EL AÑO DESDE ES: " + intAniDes);
                        System.out.println("EL AÑO HASTA ES: " + intAniHas);
                        
                        if(intAniDes==intAniHas){
                            strAux+="                           WHERE a1.ne_ani = " + intAniDes + " AND a1.ne_mes BETWEEN " + intMesDes + " AND " + intMesHas + "";
                        }
                        if(intAniDes!=intAniHas){
                            intAniDifLoc=intAniHas-intAniDes;
                            strAux+="                           WHERE a1.ne_ani = " + intAniDes + " AND a1.ne_mes BETWEEN " + intMesDes + " AND 12";
                            if(intAniDifLoc>1){
                                strAux+="                       OR a1.ne_ani IN( " + (intAniDes+1) + ", " + (intAniHas-1) + ") AND a1.ne_mes BETWEEN 1 AND 12";
                            }
                            strAux+="                           OR a1.ne_ani = " + intAniHas + " AND a1.ne_mes BETWEEN 1 AND " + intMesHas + "";
                        }                        
                        
                        
                        strSQL+=strAux;
                        strAux="";
                        
                        
                        
                        
                        strSQL+=" 			) AS b3";
                        strSQL+=" 			ON b2.codEmp=b3.co_emp AND b2.co_cta=b3.co_cta";
                        strSQL+=" 			WHERE b3.co_cta IS NOT NULL";
                        strSQL+=" 		) AS c1";
                        strSQL+=" 		GROUP BY c1.codGrp, c1.ne_mes, c1.tx_codCta, c1.tx_desLar, c1.ne_ani";
                        strSQL+=" 		ORDER BY codGrp, tx_codCta, ne_mes";
                        strSQL+=" 	) AS d1";
                        strSQL+=" 	GROUP BY d1.codGrp, d1.tx_codCta, d1.tx_desLar, d1.ne_mes, d1.ne_ani";
                        strSQL+=" ) AS e1";
                        String strSQLSalIni=strSQL;
                
                while(rst.next()){
                    if(flag==0){
                        f++;
                        strDat[f][INT_ARL_INI_COD_EMP]=(rst.getString("co_emp")==null?"":rst.getString("co_emp"));
                        intCodRegIni=(rst.getString("co_reg")==null?0:rst.getInt("co_reg"));
                        strDat[f][INT_ARL_INI_COD_CTA]="";
                        strDat[f][INT_ARL_INI_NUM_CTA]=(rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
                                                
                        intNivPad=rst.getInt("ne_niv");
                        strAuxTmp="";
                        for (int h=1; h<intNivPad; h++)
                            strAuxTmp+="    ";
                        strDat[f][INT_ARL_INI_NOM_DSC]= strAuxTmp + "" + (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
                        strDat[f][INT_ARL_INI_COD_REG]=(rst.getString("co_reg")==null?"0":"" + rst.getInt("co_reg"));
                        strDat[f][INT_ARL_INI_NOD_PAD]=(rst.getString("ne_pad")==null?"0":"" + rst.getInt("ne_pad"));
                        strDat[f][INT_ARL_INI_NIV_DSC]=(rst.getString("ne_niv")==null?"0":"" + rst.getInt("ne_niv"));
                        strDat[f][INT_ARL_INI_TIP_DSC]=(rst.getString("tx_tip")==null?"":"" + rst.getString("tx_tip"));
                        strAux="";
                        strAux+=" WHERE e1.tx_codCta='" + (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta")) + "'";
                        strAux+=" AND tx_desLar='" + (rst.getString("tx_nom")==null?"":rst.getString("tx_nom")) + "'";
                        strAux+=" GROUP BY e1.codGrp, e1.tx_codCta, e1.tx_desLar, e1.ne_ani";
                        strSQL=strSQLSalIni;
                        strSQL+=strAux;
                        strSQL+=" ORDER BY e1.ne_ani";
                        System.out.println("SQL DE VALORES: " + strSQL);
                        rstSalDsc=stmSalDsc.executeQuery(strSQL);
                    }
                    else{
                        flag=0;
                        intCol=1;
                        intCol=(intCol+INT_ARL_INI_SAL_DIC);
                        rst.previous();
                        //rstSalDsc.previous();

                    }                    
                    
                    while(rstSalDsc.next()){
                        if(flag==0){
                            strDat[f][INT_ARL_INI_ANI]=(rstSalDsc.getString("ne_ani")==null?"0":"" + rstSalDsc.getInt("ne_ani"));
                            strDat[f][INT_ARL_INI_SAL_ENE]=(rstSalDsc.getString("salEne")==null?"0.00":"" + rstSalDsc.getDouble("salEne"));
                            strDat[f][INT_ARL_INI_SAL_FEB]=(rstSalDsc.getString("salFeb")==null?"0.00":"" + rstSalDsc.getDouble("salFeb"));
                            strDat[f][INT_ARL_INI_SAL_MAR]=(rstSalDsc.getString("salMar")==null?"0.00":"" + rstSalDsc.getDouble("salMar"));
                            strDat[f][INT_ARL_INI_SAL_ABR]=(rstSalDsc.getString("salAbr")==null?"0.00":"" + rstSalDsc.getDouble("salAbr"));
                            strDat[f][INT_ARL_INI_SAL_MAY]=(rstSalDsc.getString("salMay")==null?"0.00":"" + rstSalDsc.getDouble("salMay"));
                            strDat[f][INT_ARL_INI_SAL_JUN]=(rstSalDsc.getString("salJun")==null?"0.00":"" + rstSalDsc.getDouble("salJun"));
                            strDat[f][INT_ARL_INI_SAL_JUL]=(rstSalDsc.getString("salJul")==null?"0.00":"" + rstSalDsc.getDouble("salJul"));
                            strDat[f][INT_ARL_INI_SAL_AGO]=(rstSalDsc.getString("salAgo")==null?"0.00":"" + rstSalDsc.getDouble("salAgo"));
                            strDat[f][INT_ARL_INI_SAL_SEP]=(rstSalDsc.getString("salSep")==null?"0.00":"" + rstSalDsc.getDouble("salSep"));
                            strDat[f][INT_ARL_INI_SAL_OCT]=(rstSalDsc.getString("salOct")==null?"0.00":"" + rstSalDsc.getDouble("salOct"));
                            strDat[f][INT_ARL_INI_SAL_NOV]=(rstSalDsc.getString("salNov")==null?"0.00":"" + rstSalDsc.getDouble("salNov"));
                            strDat[f][INT_ARL_INI_SAL_DIC]=(rstSalDsc.getString("salDic")==null?"0.00":"" + rstSalDsc.getDouble("salDic"));
                            flag++;
                        }
                        else{
                            intCodRegFin=(rst.getString("co_reg")==null?0:rst.getInt("co_reg"));
                            if(intCodRegIni==intCodRegFin){
                                strDat[f][intCol]=(rstSalDsc.getString("salEne")==null?"0.00":"" + rstSalDsc.getDouble("salEne"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salFeb")==null?"0.00":"" + rstSalDsc.getDouble("salFeb"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salMar")==null?"0.00":"" + rstSalDsc.getDouble("salMar"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salAbr")==null?"0.00":"" + rstSalDsc.getDouble("salAbr"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salMay")==null?"0.00":"" + rstSalDsc.getDouble("salMay"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salJun")==null?"0.00":"" + rstSalDsc.getDouble("salJun"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salJul")==null?"0.00":"" + rstSalDsc.getDouble("salJul"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salAgo")==null?"0.00":"" + rstSalDsc.getDouble("salAgo"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salSep")==null?"0.00":"" + rstSalDsc.getDouble("salSep"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salOct")==null?"0.00":"" + rstSalDsc.getDouble("salOct"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salNov")==null?"0.00":"" + rstSalDsc.getDouble("salNov"));
                                intCol++;
                                strDat[f][intCol]=(rstSalDsc.getString("salDic")==null?"0.00":"" + rstSalDsc.getDouble("salDic"));
                            }

                        }
                    }
                    
                    
                    
                    
                    
                    
                }
            System.out.println("VARIABLE F CONTIENE: " + intNumFilStrDat);
            intNumFilStrDat=(f+1);
            cargarTablaPresupValorInicial();                 
            }
            stm.close();
            stm=null;
            rst.close();
            rst=null;
            stmSalDsc.close();
            stmSalDsc=null;
            rstSalDsc.close();
            rstSalDsc=null;
            
            
           
            
//            objTblModSal.setData(vecDat);
//            tblDatSal.setModel(objTblModSal);
//            vecDat.clear();
//            objTblModSal.removeEmptyRows();
            

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
       
    
    
    
    
    
    private boolean cargarTablaPresupValorInicial(){
        boolean blnRes=true;
        int intNumColIni=(numColModIni);
        int intNumColFin=tblDatSal.getColumnCount();
        vecDat.clear();
        try{            
            for(int i=0; i<intNumFilStrDat;i++){
                vecReg=new Vector();
                vecReg.add(INT_TBL_DAT_LIN, "");
                vecReg.add(INT_TBL_DAT_COD_CTA, "" +  strDat[i][INT_ARL_INI_COD_CTA]);                                
                vecReg.add(INT_TBL_DAT_NUM_CTA, "" + strDat[i][INT_ARL_INI_NUM_CTA] );
                vecReg.add(INT_TBL_DAT_NOM_CTA, "" + strDat[i][INT_ARL_INI_NOM_DSC] );
                vecReg.add(INT_TBL_DAT_BUT_PRC, "" );
                vecReg.add(INT_TBL_DAT_NOD_PAD, "" + strDat[i][INT_ARL_INI_NOD_PAD] );
                vecReg.add(INT_TBL_DAT_NIV, "" + strDat[i][INT_ARL_INI_NIV_DSC] );
                vecReg.add(INT_TBL_DAT_COD_REG, "" + strDat[i][INT_ARL_INI_COD_REG] );
                vecReg.add(INT_TBL_DAT_TIP_DSC, "" + strDat[i][INT_ARL_INI_TIP_DSC]);
                               
                
                for(int k=(intNumColIni); k<numColModFin; k++){
                    vecReg.add(k, "" + strDat[i][(k)]==null?"":strDat[i][(k)]);
                }
                vecDat.add(vecReg);
            }
            asignaVectorModeloSaldos();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean asignaVectorModeloSaldos(){
        boolean blnRes=true;
        try{
//            objTblModSal.removeAllRows();
            //Asignar vectores al modelo.
            System.out.println("EL VECTOR CONTIENE: " + vecDat.toString());
            System.out.println("EL VECTOR CONTIENE: " + vecDat.size());
            System.out.println("LA TABLA CONTIENE: " + tblDatSal.getColumnCount());
            System.out.println("EL OBJETO CONTIENE: " + objTblModSal.getColumnCount());
            objTblModSal.setData(vecDat);
            tblDatSal.setModel(objTblModSal);
            vecDat.clear();
            objTblModSal.removeEmptyRows();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean eliminaColTblDatAdicionadas(){
        boolean blnRes=true;
        int intNumColModIniLoc=numColModIni;
        int intNumColModFinLoc=numColModFin;
        
        //PARA TOTALES
        int intNumColModIniTotLoc=numColModIniTot;
        int intNumColModFinTotLoc=numColModFinTot;
        
        try{
            for (int i=(intNumColModFinLoc-1); i>=0; i--){
                tblDatSal.removeColumn(tblDatSal.getColumnModel().getColumn(i));
//                tblTot.removeColumn(tblTot.getColumnModel().getColumn(i));
            }
            
            for (int i=(intNumColModFinTotLoc-1); i>=0; i--){
//                tblDatSal.removeColumn(tblDatSal.getColumnModel().getColumn(i));
                tblTot.removeColumn(tblTot.getColumnModel().getColumn(i));
            }            
            
            configurarTblSaldosBut();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private int getColumnaAdicionada(){
        return numColModIni;
    }
    
    
    private boolean txtFiltroReporPersonalizado(){
        if(txtCodRep.getText().equals("")){
            mostrarMsgInf("<HTML>El filtro de reporte es obligatorio.<BR>Ingrese datos en ese campo y vuelva a intentarlo</HTML>");
            return false;
        }
        return true;
    }

    private boolean insertaArl(){
        boolean blnRes=true;
        tblDatIncDec=objCon48_01.getTableIncDec();
        try{
            for(int i=0;i<tblDatIncDec.getRowCount(); i++){
                arlReg=new ArrayList();
                arlReg.add(INT_ARL_PRC_ANI, "" + ( tblDatIncDec.getValueAt(i, INT_TBL_ANI)==null?"":tblDatIncDec.getValueAt(i, INT_TBL_ANI) ) );
                arlReg.add(INT_ARL_PRC_MES, "" + ( tblDatIncDec.getValueAt(i, INT_TBL_MES)==null?"":tblDatIncDec.getValueAt(i, INT_TBL_MES) ) );
                arlReg.add(INT_ARL_PRC_POR, "" + ( tblDatIncDec.getValueAt(i, INT_TBL_PRC)==""?"0.00":tblDatIncDec.getValueAt(i, INT_TBL_PRC) ) );
                arlReg.add(INT_ARL_PRC_COD, "" + ( objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_COD_CTA)==null?"":objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_COD_CTA) ) );
                arlReg.add(INT_ARL_PRC_MES_NUM, "" + ( tblDatIncDec.getValueAt(i, INT_TBL_MES_NUM)==null?"":tblDatIncDec.getValueAt(i, INT_TBL_MES_NUM) ) );
                arlReg.add(INT_ARL_PRC_REG, "" + ( objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_COD_REG)==null?"":objTblModSal.getValueAt(tblDatSal.getSelectedRow(), INT_TBL_DAT_COD_REG) ) );
                arlReg.add(INT_ARL_PRC_LIN, "" + ( tblDatIncDec.getValueAt(i, INT_TBL_LIN)==null?"":tblDatIncDec.getValueAt(i, INT_TBL_LIN) ) );
                arlDat.add(arlReg);
                arlDatIns.add(arlReg);
                tblDatIncDec.setValueAt(""+0.00,i, INT_TBL_PRC);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean calculaPorcentajes(){
        boolean blnRes=true;
        int k=-1;
        double dblVal=0.00;
        double dblPrcIncDec=0.00;
        double dblValTblMes=0.00;
        String strValTblMes="";
        String strPrcIncDec="";
        String strCodCtaArl="", strCodCtaTbl="";
        try{
            for(int j=intFrsColOcu; j<(tblDatSal.getColumnCount()-1); j++){
                if(k<arlDat.size())
                    k++;
                for(int i=0; i<objTblModSal.getRowCountTrue(); i++){
                    System.out.println("SIZE:   " +arlDat.size());
                    
                    System.out.println("PRIMERA COLUMNA:   " +intFrsColOcu);
                    System.out.println("SIZE COLUMNA:   " +(tblDatSal.getColumnCount()-1));
                    System.out.println("SIZE FILA:   " +objTblModSal.getRowCountTrue());
                    
                    
                    
                    strCodCtaArl="" + (objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_COD)==null?"":objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_COD));
                    
                    strCodCtaTbl=""+(  objTblModSal.getValueAt(i, INT_TBL_DAT_COD_CTA)==null?"":objTblModSal.getValueAt(i, INT_TBL_DAT_COD_CTA)  );
                    if(strCodCtaArl.equals(strCodCtaTbl)){
                        strPrcIncDec="" + objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_POR);
                        dblPrcIncDec=Double.parseDouble(strPrcIncDec);
                        dblPrcIncDec=(dblPrcIncDec/100);
                        strValTblMes=""+ (   (  objTblModSal.getValueAt(i, j)==null?"0.00":objTblModSal.getValueAt(i, j)  )==""?"0.00":(  objTblModSal.getValueAt(i, j)==null?"0.00":objTblModSal.getValueAt(i, j)  )     );
                        dblValTblMes=Double.parseDouble(strValTblMes);
                        dblVal=dblValTblMes+(dblValTblMes*dblPrcIncDec);
                        tblDatSal.setValueAt( "" +(objUti.redondear(dblVal, objParSis.getDecimalesMostrar())), i, j  );
                    }
                }
//                k++;
                System.out.println("K CONTIENE: " +k);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertarCab()){
                    if (insertarDet()){
                        if(updatePorcentajesDetalle()){
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
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    
    private boolean insertarCab(){
        int intCodUsr, intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_cfg)";
                strSQL+=" FROM tbm_cabCfgEstFinPer AS a1";
                strSQL+=" WHERE a1.co_emp IN (" + objParSis.getCodigoEmpresa() + ")";
                System.out.println("MAXIMO: " + strSQL);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCfg.setText("" + intUltReg);
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabcfgestfinper(";
                strSQL+=" co_emp, co_estfin, co_cfg, fe_des,";
                strSQL+=" fe_has, ne_datutipro,";
                strSQL+=" st_promesact, st_reg, fe_ing,";
                strSQL+=" fe_ultmod, co_usring, co_usrmod)";
                strSQL+=" VALUES(";
                strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                strSQL+="" + txtCodRep.getText() + ",";
                strSQL+="" + intUltReg + ",";
                strSQL+="'" + objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                strSQL+="'" + objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";
                
                if(optUtiDatPrs.isSelected())
                    strSQL+="1,";
                if(optSalMenAniAntMesPry.isSelected())
                    strSQL+="2,";
                if(optMesUsaAniAntMesPry.isSelected())
                    strSQL+="3,";
                
                if(chkPryMesAct.isSelected())
                    strSQL+="'S',";
                else
                    strSQL+="'N',";
                
                strSQL+="'A',";
                strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+="" + objParSis.getCodigoUsuario() + ",";
                strSQL+="" + objParSis.getCodigoUsuario() + "";
                strSQL+=")";
                System.out.println("SQL DE CABECERA: " + strSQL);
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
    
    private boolean insertarDet(){
        boolean blnRes=true;
        tblDatIncDec=objCon48_01.getTableIncDec();
        int k=0;
        int intNumFilTbl=objCon48_01.numFilTabla();
        int intNumColIni=(numColModIni);
        
        int intNumColFin=objTblModSal.getColumnCount();
        
        String strArlAni="", strArlCodCta="", strArlMesNum="";
        String strTblAni="", strTblCodCta="", strTblMesNum="";
        strAux="";
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0;i<objTblModSal.getRowCountTrue();i++){
                    for(int j=(intFrsColOcu); j<(intNumColFin-1); j++){
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detcfgestfinper(";
                        strSQL+=" co_emp, co_estfin, co_cfg, ne_ani,";
                        strSQL+=" ne_mes, nd_por, nd_val, co_reg)";
                        strSQL+=" VALUES(";
                        strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                        strSQL+="" + txtCodRep.getText() + ",";
                        strSQL+="" + txtCfg.getText() + ",";
                        strSQL+="" + tblDatIncDec.getValueAt(k, INT_TBL_ANI) + ",";
                        strSQL+="" + tblDatIncDec.getValueAt(k, INT_TBL_MES_NUM) + ",";
                        strSQL+="" + objUti.codificar(strAux, 3) + ",";
                        strSQL+="" + objUti.codificar(objTblModSal.getValueAt(i, j), 3) + ",";
                        strSQL+="" + objUti.codificar(""+objTblModSal.getValueAt(i, INT_TBL_DAT_COD_REG), 3) + "";
                        strSQL+=")";
                        System.out.println("SQL DE DETALLE: " + strSQL);
                        stm.executeUpdate(strSQL);
//                        if(k<tblDatIncDec.getColumnCount())
                            k++;
                        System.out.println("K TIENE: " + k);
                        System.out.println("SIZE fila: " + tblDatIncDec.getRowCount());
                    }
                    k=0;
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
        
    
    private boolean consultarReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                    strSQL="";
                    strSQL+="SELECT distinct(a1.co_emp) as co_emp, a1.co_estFin, a1.co_cfg";
                    strSQL+=" FROM tbm_cabCfgEstFinPer AS a1";
                    strSQL+=" INNER JOIN tbm_detCfgEstFinPer AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_estFin=a2.co_estFin AND a1.co_cfg=a2.co_cfg";
                    strSQL+=" INNER JOIN tbm_detEstFinPer AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_estFin=a3.co_estFin AND a2.co_reg=a3.co_reg";
                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                
                strAux=txtCfg.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cfg LIKE '" + strAux.replaceAll("'", "''") + "'";
                
                strAux=txtCodRep.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_estFin LIKE '" + strAux.replaceAll("'", "''") + "'";                
                
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp, a1.co_estFin, a1.co_cfg";
                System.out.println("CONSULTARREG: " +strSQL);
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
    
    private boolean limpiarFrm(){
        boolean blnRes=true;
        txtCodRep.setText("");
        txtNomRep.setText("");
        txtCfg.setText("");
        return blnRes;
    }
    
    
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if(eliminaColTblDatAdicionadas()){
               if(getAnioMesDesdeHastaConsulta()){
                   if(agregarColTblDat()){
                        if (cargarCabReg()){
                            if (cargarDetReg()){
                                if(ocultarColumnas()){
                                   for(int i=0; i<objTblModSal.getRowCountTrue(); i++)
                                       for(int j=(INT_TBL_DAT_TIP_DSC+1); j<tblDatSal.getColumnCount(); j++){
                                           calcula(i, j);
                                           objTblModSal.initRowsState();
                                       }
                                }
                            }
                        }                   
                   }
               }
            }
        blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_estFin, a2.tx_nom, a1.co_cfg, a1.fe_des, a1.fe_has,";
                strSQL+=" a1.ne_datUtiPro, a1.st_proMesAct, a1.st_reg, a2.st_mosSalAntMes, a2.tx_salCtaUti";
                strSQL+=" FROM tbm_cabCfgEstFinPer AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabEstFinPer AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_estFin=a2.co_estFin";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+=" AND a1.co_cfg=" + rstCab.getString("co_cfg") + "";
                strSQL+=" AND a1.co_estFin=" + rstCab.getString("co_estFin") + "";
                System.out.println("CARGARCABREG: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    objSelDat.setDateFrom( objUti.formatearFecha(rst.getString("fe_des"), "yyyy/MM/dd", "dd/MM/yyyy"));
                    objSelDat.setDateTo( objUti.formatearFecha(rst.getString("fe_has"), "yyyy/MM/dd", "dd/MM/yyyy"));
                    
                    strAux=rst.getString("co_cfg");
                    txtCfg.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_nom");
                    txtNomRep.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_estFin");
                    txtCodRep.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_salCtaUti");
                    if(strAux.toString().equals("T")){
                        optTodDia.setSelected(true);
                        optDiaCtaEfe.setSelected(false);
                    }
                    else{
                        optTodDia.setSelected(false);
                        optDiaCtaEfe.setSelected(true);
                    }
                    
                    strAux=rst.getString("st_mosSalAntMes");
                    if(strAux.toString().equals("S"))
                        chkMosSalAntPorMes.setSelected(true);
                    else
                        chkMosSalAntPorMes.setSelected(false);
                        
                    strAux=rst.getString("ne_datUtiPro");
                    if(strAux.toString().equals("1")){
                        optUtiDatPrs.setSelected(true);
                        optSalMenAniAntMesPry.setSelected(false);
                        optMesUsaAniAntMesPry.setSelected(false);
                    }
                    if(strAux.toString().equals("2")){
                        optUtiDatPrs.setSelected(false);
                        optSalMenAniAntMesPry.setSelected(true);
                        optMesUsaAniAntMesPry.setSelected(false);
                    }
                    if(strAux.toString().equals("3")){
                        optUtiDatPrs.setSelected(false);
                        optSalMenAniAntMesPry.setSelected(false);
                        optMesUsaAniAntMesPry.setSelected(true);
                    }
                    
                    strAux=rst.getString("st_proMesAct");
                    if(strAux.toString().equals("S"))
                        chkPryMesAct.setSelected(true);
                    else
                        chkPryMesAct.setSelected(false);
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                }
                else{
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
    
    
    private String getEstReg(String estado){
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0)){
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
    
    
    private boolean cargarDetReg(){
        boolean blnRes=true;
        strAux="";
        int intAniDifLoc=0;
        int flag=0;
        int intCodRegIni=0, intCodRegFin=0 ;
        int intCol=1;
        intCol=(intCol+INT_ARL_INI_SAL_DIC);
        arlDatIni.clear();
        String strVarTmp="";
        int intNivPad=0;
        String strAuxTmp="";
        String strCodCtaCmp="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT q.co_cta, q.co_emp, q.co_estFin, q.co_cfg, q.co_reg, q.fe_des, q.fe_has, q.tx_nom, q.tx_codCta,";
                strSQL+=" q.ne_ubi, q.tx_tip, q.ne_pad, q.ne_niv, q.ne_ani,";
                strSQL+=" SUM(q.salEne) AS salEne, SUM(q.salFeb) AS salFeb, SUM(q.salMar) AS salMar,";
                strSQL+=" SUM(q.salAbr) AS salAbr, SUM(q.salMay) AS salMay, SUM(q.salJun) AS salJun,";
                strSQL+=" SUM(q.salJul) AS salJul, SUM(q.salAgo) AS salAgo, SUM(q.salSep) AS salSep,";
                strSQL+=" SUM(q.salOct) AS salOct, SUM(q.salNov) AS salNov, SUM(q.salDic) AS salDic";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT w.co_cta, w.co_emp, w.co_estFin, w.co_cfg, w.co_reg, w.fe_des, w.fe_has, w.tx_nom, w.tx_codCta,";
                strSQL+=" 	w.ne_ubi, w.tx_tip, w.ne_pad, w.ne_niv, w.ne_ani,";
                strSQL+=" 	CASE WHEN w.ne_mes=1 THEN SUM(w.nd_val) END AS salEne,";
                strSQL+=" 	CASE WHEN w.ne_mes=2 THEN SUM(w.nd_val) END AS salFeb,";
                strSQL+=" 	CASE WHEN w.ne_mes=3 THEN SUM(w.nd_val) END AS salMar,";
                strSQL+=" 	CASE WHEN w.ne_mes=4 THEN SUM(w.nd_val) END AS salAbr,";
                strSQL+=" 	CASE WHEN w.ne_mes=5 THEN SUM(w.nd_val) END AS salMay,";
                strSQL+=" 	CASE WHEN w.ne_mes=6 THEN SUM(w.nd_val) END AS salJun,";
                strSQL+=" 	CASE WHEN w.ne_mes=7 THEN SUM(w.nd_val) END AS salJul,";
                strSQL+=" 	CASE WHEN w.ne_mes=8 THEN SUM(w.nd_val) END AS salAgo,";
                strSQL+=" 	CASE WHEN w.ne_mes=9 THEN SUM(w.nd_val) END AS salSep,";
                strSQL+=" 	CASE WHEN w.ne_mes=10 THEN SUM(w.nd_val) END AS salOct,";
                strSQL+=" 	CASE WHEN w.ne_mes=11 THEN SUM(w.nd_val) END AS salNov,";
                strSQL+=" 	CASE WHEN w.ne_mes=12 THEN SUM(w.nd_val) END AS salDic";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT distinct (z.co_cta) AS co_cta, z.co_emp, z.co_estFin, z.co_cfg, z.co_reg, z.fe_des, z.fe_has, z.tx_nom, z.tx_codCta,";
                strSQL+=" 			z.ne_ubi, z.tx_tip, z.ne_pad, z.ne_niv, z.nd_val, z.ne_mes, z.ne_ani FROM(";
                strSQL+=" 			SELECT b1.co_emp, b1.co_estFin, b1.co_cfg, b1.co_reg, b1.co_cta, b1.fe_des, b1.fe_has, b1.tx_nom, b1.tx_codCta,";
                strSQL+=" 			b1.ne_ubi, b1.tx_tip, b1.ne_pad, b1.ne_niv, b2.nd_val, b2.ne_mes, b2.ne_ani FROM(";
                strSQL+=" 				SELECT a1.co_emp, a1.co_estFin, a1.co_cfg, a2.co_reg, a1.fe_des, a1.fe_has, a3.co_cta, a3.tx_nom, a4.tx_codCta,";
                strSQL+=" 				a3.ne_ubi, a3.tx_tip, a3.ne_pad, a3.ne_niv, a2.ne_ani";
                strSQL+=" 				FROM tbm_cabCfgEstFinPer AS a1 INNER JOIN tbm_detCfgEstFinPer AS a2";
                strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_estFin=a2.co_estFin AND a1.co_cfg=a2.co_cfg";
                strSQL+=" 				INNER JOIN tbm_detEstFinPer AS a3";
                strSQL+=" 				ON a2.co_emp=a3.co_emp AND a2.co_estFin=a3.co_estFin AND a2.co_reg=a3.co_reg";
                strSQL+=" 				LEFT OUTER JOIN tbm_plaCta AS a4";
                strSQL+=" 				ON a3.co_emp=a4.co_emp AND a3.co_cta=a4.co_cta";
                strSQL+=" 				WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+="                               AND a1.co_estFin=" + rstCab.getString("co_estFin") + "";
                strSQL+="                               AND a1.co_cfg=" + rstCab.getString("co_cfg") + "";
                strSQL+=" 				ORDER BY a3.co_reg, a2.ne_ani, a2.ne_mes, a3.co_cta) AS b1";
                strSQL+=" 			LEFT OUTER JOIN (";
                strSQL+=" 				SELECT a1.co_emp, a1.co_estFin, a1.co_cfg, a2.co_reg, a3.co_cta, a1.fe_des, a1.fe_has,";
                strSQL+=" 				a2.ne_mes, SUM(a2.nd_val) AS nd_val, a2.ne_ani";
                strSQL+=" 				FROM tbm_cabCfgEstFinPer AS a1 INNER JOIN tbm_detCfgEstFinPer AS a2";
                strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_estFin=a2.co_estFin AND a1.co_cfg=a2.co_cfg";
                strSQL+=" 				INNER JOIN tbm_detEstFinPer AS a3";
                strSQL+=" 				ON a2.co_emp=a3.co_emp AND a2.co_estFin=a3.co_estFin AND a2.co_reg=a3.co_reg";
                strSQL+=" 				WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+="                               AND a1.co_estFin=" + rstCab.getString("co_estFin") + "";
                strSQL+="                               AND a1.co_cfg=" + rstCab.getString("co_cfg") + "";
//                strSQL+=" 				AND a1.fe_des='" + objUti.formatearFecha(objSelDat.getDateFrom(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
//                strSQL+="                               AND a1.fe_has='" + objUti.formatearFecha(objSelDat.getDateTo(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" 				GROUP BY a1.co_emp, a1.co_estFin, a1.co_cfg, a2.co_reg, a3.co_cta, a2.ne_ani, a1.fe_des, a1.fe_has, a2.ne_mes";
                strSQL+=" 				ORDER BY a2.co_reg, a1.fe_des, a1.fe_has, a2.ne_mes, a3.co_cta) AS b2";
                strSQL+=" 			ON (b1.co_emp=b2.co_emp AND b1.co_estFin=b2.co_estFin AND b1.co_cfg=b2.co_cfg AND b1.co_reg=b2.co_reg";
                strSQL+=" 			AND b1.fe_des=b2.fe_des AND b1.fe_has=b2.fe_has AND b1.co_cta=b2.co_cta AND b1.ne_ani=b2.ne_ani)";
                strSQL+=" 		 ) AS z";
                strSQL+=" 		ORDER BY z.ne_ubi, z.ne_ani, z.fe_des, z.fe_has, z.ne_mes, z.ne_pad, z.ne_niv";
                strSQL+=" 	) AS w";
                strSQL+=" 	GROUP BY  w.co_cta, w.co_emp, w.co_estFin, w.co_cfg, w.co_reg, w.fe_des, w.fe_has, w.tx_nom, w.tx_codCta,";
                strSQL+=" 	w.ne_ubi, w.tx_tip, w.ne_pad, w.ne_niv, w.ne_ani, w.ne_mes";
                strSQL+=" 	ORDER BY w.ne_ubi, w.ne_ani";
                strSQL+=" ) AS q";
                strSQL+=" GROUP BY q.co_cta, q.co_emp, q.co_estFin, q.co_cfg, q.co_reg, q.fe_des, q.fe_has, q.tx_nom, q.tx_codCta,";
                strSQL+=" 	q.ne_ubi, q.tx_tip, q.ne_pad, q.ne_niv, q.ne_ani";
                strSQL+=" ORDER BY q.ne_ubi, q.ne_ani, q.ne_pad, q.ne_niv";
                System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                int f=-1;
                
                while(rst.next()){
                    
                    
                    
                    
                    
                    
                    if(flag==0){
                        f++;
                        strDatIns[f][INT_ARL_INI_COD_EMP]=(rst.getString("co_emp")==null?"":rst.getString("co_emp"));
                        intCodRegIni=(rst.getString("co_reg")==null?0:rst.getInt("co_reg"));
                        strDatIns[f][INT_ARL_INI_COD_CTA]=(rst.getString("co_cta")==null?"":rst.getString("co_cta"));
                        strCodCtaCmp=(rst.getString("co_cta")==null?"":rst.getString("co_cta"));
                        strDatIns[f][INT_ARL_INI_NUM_CTA]=(rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
                                                
                        intNivPad=rst.getInt("ne_niv");
                        strAuxTmp="";
                        for (int h=1; h<intNivPad; h++)
                            strAuxTmp+="    ";
                        strDatIns[f][INT_ARL_INI_NOM_DSC]= strAuxTmp + "" + (rst.getString("tx_nom")==null?"":rst.getString("tx_nom"));
                        //strDat[f][INT_ARL_INI_NOM_DSC]=(rst.getString("descripc")==null?"":rst.getString("descripc"));
                        strDatIns[f][INT_ARL_INI_COD_REG]=(rst.getString("co_reg")==null?"0":"" + rst.getInt("co_reg"));
                        strDatIns[f][INT_ARL_INI_NOD_PAD]=(rst.getString("ne_pad")==null?"0":"" + rst.getInt("ne_pad"));
                        strDatIns[f][INT_ARL_INI_NIV_DSC]=(rst.getString("ne_niv")==null?"0":"" + rst.getInt("ne_niv"));
                        strDatIns[f][INT_ARL_INI_ANI]=(rst.getString("ne_ani")==null?"0":"" + rst.getInt("ne_ani"));
                        strDatIns[f][INT_ARL_INI_TIP_DSC]=(rst.getString("tx_tip")==null?"":"" + rst.getString("tx_tip"));
                        strDatIns[f][INT_ARL_INI_SAL_ENE]=(rst.getString("salEne")==null?"0.00":"" + rst.getDouble("salEne"));
                        strDatIns[f][INT_ARL_INI_SAL_FEB]=(rst.getString("salFeb")==null?"0.00":"" + rst.getDouble("salFeb"));
                        strDatIns[f][INT_ARL_INI_SAL_MAR]=(rst.getString("salMar")==null?"0.00":"" + rst.getDouble("salMar"));
                        strDatIns[f][INT_ARL_INI_SAL_ABR]=(rst.getString("salAbr")==null?"0.00":"" + rst.getDouble("salAbr"));
                        strDatIns[f][INT_ARL_INI_SAL_MAY]=(rst.getString("salMay")==null?"0.00":"" + rst.getDouble("salMay"));
                        strDatIns[f][INT_ARL_INI_SAL_JUN]=(rst.getString("salJun")==null?"0.00":"" + rst.getDouble("salJun"));
                        strDatIns[f][INT_ARL_INI_SAL_JUL]=(rst.getString("salJul")==null?"0.00":"" + rst.getDouble("salJul"));
                        strDatIns[f][INT_ARL_INI_SAL_AGO]=(rst.getString("salAgo")==null?"0.00":"" + rst.getDouble("salAgo"));
                        strDatIns[f][INT_ARL_INI_SAL_SEP]=(rst.getString("salSep")==null?"0.00":"" + rst.getDouble("salSep"));
                        strDatIns[f][INT_ARL_INI_SAL_OCT]=(rst.getString("salOct")==null?"0.00":"" + rst.getDouble("salOct"));
                        strDatIns[f][INT_ARL_INI_SAL_NOV]=(rst.getString("salNov")==null?"0.00":"" + rst.getDouble("salNov"));
                        strDatIns[f][INT_ARL_INI_SAL_DIC]=(rst.getString("salDic")==null?"0.00":"" + rst.getDouble("salDic"));
//                        strDat[f][INT_ARL_INI_TIP_DSC]=(rst.getString("tx_tip")==null?"":"" + rst.getString("tx_tip"));
                        flag++;
                    }
                    else{
                        intCodRegFin=(rst.getString("co_reg")==null?0:rst.getInt("co_reg"));
                        if(intCodRegIni==intCodRegFin){
                            strDatIns[f][intCol]=(rst.getString("salEne")==null?"0.00":"" + rst.getDouble("salEne"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salFeb")==null?"0.00":"" + rst.getDouble("salFeb"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salMar")==null?"0.00":"" + rst.getDouble("salMar"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salAbr")==null?"0.00":"" + rst.getDouble("salAbr"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salMay")==null?"0.00":"" + rst.getDouble("salMay"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salJun")==null?"0.00":"" + rst.getDouble("salJun"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salJul")==null?"0.00":"" + rst.getDouble("salJul"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salAgo")==null?"0.00":"" + rst.getDouble("salAgo"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salSep")==null?"0.00":"" + rst.getDouble("salSep"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salOct")==null?"0.00":"" + rst.getDouble("salOct"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salNov")==null?"0.00":"" + rst.getDouble("salNov"));
                            intCol++;
                            strDatIns[f][intCol]=(rst.getString("salDic")==null?"0.00":"" + rst.getDouble("salDic"));
                        }
                        else{
                            flag=0;
                            intCol=1;
                            intCol=(intCol+INT_ARL_INI_SAL_DIC);
                            rst.previous();
                            
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                }
                intNumFilStrDatIns=(f+1);
                System.out.println("VARIABLE F CONTIENE: " + intNumFilStrDatIns);
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                cargarTablaConsulta();
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
        
    private boolean cargarTablaConsulta(){
        boolean blnRes=true;
        int intNumColIni=(numColModIni);
        int intNumColFin=tblDatSal.getColumnCount();
        vecDat.clear();
        try{            
            for(int i=0; i<intNumFilStrDatIns;i++){
                vecReg=new Vector();
                vecReg.add(INT_TBL_DAT_LIN, "");
                vecReg.add(INT_TBL_DAT_COD_CTA, "" +  strDatIns[i][INT_ARL_INI_COD_CTA]);                                
                vecReg.add(INT_TBL_DAT_NUM_CTA, "" + strDatIns[i][INT_ARL_INI_NUM_CTA] );
                vecReg.add(INT_TBL_DAT_NOM_CTA, "" + strDatIns[i][INT_ARL_INI_NOM_DSC] );
                vecReg.add(INT_TBL_DAT_BUT_PRC, "" );
                vecReg.add(INT_TBL_DAT_NOD_PAD, "" + strDatIns[i][INT_ARL_INI_NOD_PAD] );
                vecReg.add(INT_TBL_DAT_NIV, "" + strDatIns[i][INT_ARL_INI_NIV_DSC] );
                vecReg.add(INT_TBL_DAT_COD_REG, "" + strDatIns[i][INT_ARL_INI_COD_REG] );
                vecReg.add(INT_TBL_DAT_TIP_DSC, "" + strDatIns[i][INT_ARL_INI_TIP_DSC]);
                for(int k=(intNumColIni); k<numColModFin; k++){
                    vecReg.add(k, "" + strDatIns[i][(k)]==null?"":strDatIns[i][(k)]);
//                    System.out.println("INGRESA EN EL FOR: " + k);
                }
                vecDat.add(vecReg);
            }
            System.out.println("VECTOR DE DATOS: " + vecDat.toString());
            asignaVectorModeloSaldos();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
        
    
    private boolean updatePorcentajesDetalle(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int p=0; p<arlDatIns.size(); p++){
                    strSQL="";
                    strSQL+="update tbm_detcfgEstFinPer";
                    strSQL+=" set nd_por=" + objUti.getStringValueAt(arlDatIns, p, INT_ARL_PRC_POR) + "";
                    strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" and co_estFin=" + txtCodRep.getText() + "";
                    strSQL+=" and co_cfg=" + txtCfg.getText() + "";
                    strSQL+=" and co_reg=" + objUti.getStringValueAt(arlDatIns, p, INT_ARL_PRC_REG) + "";
                    strSQL+=" and ne_ani=" + objUti.getStringValueAt(arlDatIns, p, INT_ARL_PRC_ANI) + "";
                    strSQL+=" and ne_mes=" + objUti.getStringValueAt(arlDatIns, p, INT_ARL_PRC_MES_NUM) + "";
                    System.out.println("updatePorcentajesDetalle: " + strSQL);
                    stm.executeUpdate(strSQL);                    
                }
                stm.close();
                stm=null;
                arlDatIns.clear();
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
    
    
    
    
    
    
   /*
    private void calculaSumaNiveles(){
        int intCodReg=0;
        int intCodRegSetVal=0;
        int intNePadTbl=0, intRegPadTbl=0;
        double dblValMesTbl=0.00, dblValAcuTbl=0.00;
        int m=0;

        int j=(INT_TBL_DAT_TIP_DSC+1);
        System.out.println("EL NUMERO DE COLUMNAS ES: " + tblDatSal.getColumnCount());
        while(j<tblDatSal.getColumnCount()){
            for(int i=0; i<objTblModSal.getRowCountTrue(); i++){
                intCodReg=Integer.parseInt(""+objTblModSal.getValueAt(i, INT_TBL_DAT_COD_REG));
                for(int k=0; k<objTblModSal.getRowCountTrue();k++){
                    intNePadTbl=Integer.parseInt(""+objTblModSal.getValueAt(k, INT_TBL_DAT_NOD_PAD));
                    if(intCodReg==intNePadTbl){
                        dblValMesTbl=Double.parseDouble(""+   (objTblModSal.getValueAt(k, j)==null?"0.00":objTblModSal.getValueAt(k, j))     );
                        dblValAcuTbl+=dblValMesTbl;
                        m++;
                    }                        
                }
                if(m!=0){
                    objTblModSal.setValueAt(""+dblValAcuTbl, i, j);
                    dblValAcuTbl=0.00;
                    m=0;
                }
            }
            j++;
        }

    }
    */
    /*
    private void calcula( int fila, int columna){
        int intFilSel=fila;
        int intColSel=columna;
        int intNePad=Integer.parseInt(""+objTblModSal.getValueAt(intFilSel, INT_TBL_DAT_NOD_PAD));
        int intNeNiv=Integer.parseInt(""+objTblModSal.getValueAt(intFilSel, INT_TBL_DAT_NIV));
        int intNePadTbl=0, intCtaPadTbl=0;
        double dblValMesTbl=0.00, dblValAcuTbl=0.00;
        int intFilCtaPad=0;

        for(int i=0; i<objTblModSal.getRowCountTrue();i++){
            intNePadTbl=Integer.parseInt(""+objTblModSal.getValueAt(i, INT_TBL_DAT_NOD_PAD));
            if(intNePad==intNePadTbl){
                dblValMesTbl=Double.parseDouble(""+objTblModSal.getValueAt(i, intColSel));
                dblValAcuTbl+=dblValMesTbl;
            }
        }
        for(int i=(objTblModSal.getRowCountTrue()-1); i>=0;i--){
            intCtaPadTbl=Integer.parseInt(""+objTblModSal.getValueAt(i, INT_TBL_DAT_COD_REG));
                //System.out.println("intCtaPadTbl:"+intCtaPadTbl);
            if(intNePad==intCtaPadTbl){
                intFilCtaPad=i;
                objTblModSal.setValueAt(""+dblValAcuTbl, intFilCtaPad, intColSel);
                calcula(intFilCtaPad, intColSel);
            }
        }
        intColSel++;
        calcula(tblDatSal.getSelectedRow(), intColSel);
    }
     */
    
    /*
    private void calcula(){
        int intCodRegTbl=0, intPadTbl=0;
        double dblValMesTbl=0.00, dblValAcuTbl=0.00;
        int intFilCtaPad=0;
        int g=0;
        
        for(int m=0; m<objTblModSal.getRowCountTrue(); m++){
            intCodRegTbl=Integer.parseInt(""+objTblModSal.getValueAt(m, INT_TBL_DAT_COD_REG));
            for(int i=0; i<objTblModSal.getRowCountTrue(); i++){
                intPadTbl=Integer.parseInt(""+objTblModSal.getValueAt(i, INT_TBL_DAT_NOD_PAD));
                if(intCodRegTbl==intPadTbl){
                    dblValMesTbl=Double.parseDouble(""+objTblModSal.getValueAt(i, (INT_TBL_DAT_TIP_DSC+1)));
                    dblValAcuTbl+=dblValMesTbl;
                    g++;
                }
            }
            if(g!=0){
                objTblModSal.setValueAt(""+dblValAcuTbl, m, (INT_TBL_DAT_TIP_DSC+1));
                dblValAcuTbl=0.00;
                g=0;
            }
        }
    }
     */
    
    
    private void calcula( int fila, int columna){
        int intFilSel=fila;
        int intColSel=columna;//almacena la columna a sumar                        
        int intNePad=Integer.parseInt(""+objTblModSal.getValueAt(intFilSel, INT_TBL_DAT_NOD_PAD));
        //int intNeNiv=Integer.parseInt(""+objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NIV_CTA));
        int intNePadTbl=0, intCtaPadTbl=0;
        double dblValMesTbl=0.00, dblValAcuTbl=0.00;
        int intFilCtaPad=0;
        for(int i=0; i<objTblModSal.getRowCountTrue();i++){
            intNePadTbl=Integer.parseInt(""+objTblModSal.getValueAt(i, INT_TBL_DAT_NOD_PAD));
            if(intNePad==intNePadTbl){
                dblValMesTbl=Double.parseDouble(""+  (objTblModSal.getValueAt(i, intColSel)==null?"0.00":objTblModSal.getValueAt(i, intColSel))   );
                dblValAcuTbl+=dblValMesTbl;
            }                        
        }
        for(int i=(objTblModSal.getRowCountTrue()-1); i>=0;i--){
            intCtaPadTbl=Integer.parseInt(""+objTblModSal.getValueAt(i, INT_TBL_DAT_COD_REG));
            if(intNePad==intCtaPadTbl){
                intFilCtaPad=i;
                objTblModSal.setValueAt(""+dblValAcuTbl, intFilCtaPad, intColSel);
                calcula(intFilCtaPad, intColSel);                
            }
        }                
    }
    
    private boolean calculaTotales(){
        System.out.println("calculaTotales");
        boolean blnRes=true;
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        int intNodPadDb=0, intNodPadTbl=0;
        double dblValTbl=0.00, dblValAcu=0.00;
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="SELECT MIN(ne_pad) as nodPad FROM tbm_detEstFinPer";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_estFin=" + txtCodRep.getText() + "";
                rstTmp=stmTmp.executeQuery(strSQL);
                if(rstTmp.next()){
                    intNodPadDb=rstTmp.getInt("nodPad");
                }

                for(int j=(INT_TBL_DAT_TIP_DSC+1); j<tblDatSal.getColumnCount(); j++){
                    for(int i=0; i<objTblModSal.getRowCountTrue(); i++){
                        intNodPadTbl=Integer.parseInt(""+objTblModSal.getValueAt(i, INT_TBL_DAT_NOD_PAD));
                        if(intNodPadTbl==intNodPadDb){
                            dblValTbl=Double.parseDouble(""+  (objTblModSal.getValueAt(i, j)==null?"0.00":objTblModSal.getValueAt(i, j))  );
                            dblValAcu+=dblValTbl;
                        }
                    }
                    objTblModTot.setValueAt(""+dblValAcu, 0, j);
                    dblValAcu=0.00;
                }
                
//                tblTot.setValueAt("SALDO", 0, INT_TBL_DAT_NOM_CTA); 
                
//                for(int j=0; j<=INT_TBL_DAT_TIP_DSC;j++){
//                    System.out.println("ENTRE -1");
//                    if(j==INT_TBL_DAT_NOM_CTA){
//                        objTblModTot.setValueAt("SALDO", 0, j);
//                        System.out.println("ENTRE -2-");
//                    }
//                    else{
//                        objTblModTot.setValueAt("", 0, j);
//                        System.out.println("ENTRE -3-");
//                    }
//                }                
                
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

    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
//            System.out.println("TABLA: " +tblDatSal.getColumnCount());
//            System.out.println("TABLA: " +tblTot.getColumnCount());
            
            //PARA CUENTAS
            if (tblDatSal.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDatSal.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDatSal.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTot.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
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
    
    
    
    private boolean consultarPorcentajes(){
        boolean blnRes=true;
        arlDatConPrc.clear();
        int intMes=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT";
                strSQL+=" nd_por, ne_mes, ne_ani,co_reg";
                strSQL+=" FROM tbm_detcfgestfinper";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_estFin=" + txtCodRep.getText() + ""; 
                strSQL+=" AND co_cfg=" + txtCfg.getText() + "";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegConPrc=new ArrayList();
                    arlRegConPrc.add(INT_TBL_LIN, "");                    
                    arlRegConPrc.add(INT_TBL_ANI, "" + rst.getInt("ne_ani"));
                    intMes=rst.getInt("ne_mes");
                    arlRegConPrc.add(INT_TBL_MES, "" + intMes);
                    arlRegConPrc.add(INT_TBL_PRC, "" + rst.getDouble("ne_ani"));
                    arlRegConPrc.add(INT_TBL_BLK, "");
                    arlRegConPrc.add(INT_TBL_MES_NUM, "");
                    arlDatConPrc.add(arlRegConPrc);
                    
                }
                
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
    
    
    private boolean ocultarColumnas(){
        boolean blnRes=true;
        arlDatOcuColCns.clear();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                //para obtener los minimos
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT x.minAni, y.minMes FROM(";
                strSQL+="        SELECT MIN(ne_ani) AS minAni, co_emp, co_estFin, co_cfg";
                strSQL+="         FROM tbm_detCfgEstFinPer";
                strSQL+="         WHERE co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+="         AND co_estFin=" + rstCab.getString("co_estFin") + "";
                strSQL+="         AND co_cfg=" + rstCab.getString("co_cfg") + "";
                strSQL+="         GROUP BY co_emp, co_estFin, co_cfg) AS x,";
                strSQL+="         (";
                strSQL+="         SELECT";
                strSQL+="         MIN(ne_mes) AS minMes, co_emp, co_estFin, co_cfg, ne_ani";
                strSQL+="         FROM tbm_detCfgEstFinPer";
                strSQL+="         WHERE co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+="         AND co_estFin=" + rstCab.getString("co_estFin") + "";
                strSQL+="         AND co_cfg=" + rstCab.getString("co_cfg") + "";
                strSQL+="         GROUP BY co_emp, co_estFin, co_cfg, ne_ani) AS y";
                strSQL+=" WHERE x.co_emp=y.co_emp AND x.co_estFin=y.co_estFin AND x.co_cfg=y.co_cfg AND x.minAni=y.ne_ani";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    arlRegOcuColCns=new ArrayList();
                    arlRegOcuColCns.add(INT_ARL_MES_MIN, "" + rst.getInt("minMes"));
                    arlRegOcuColCns.add(INT_ARL_ANI_MIN, "" + rst.getInt("minAni"));
                }
                
                strSQL="";
                strSQL+="SELECT x.maxAni, y.maxMes FROM(";
                strSQL+="        SELECT MAX(ne_ani) AS maxAni, co_emp, co_estFin, co_cfg";
                strSQL+="         FROM tbm_detCfgEstFinPer";
                strSQL+="         WHERE co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+="         AND co_estFin=" + rstCab.getString("co_estFin") + "";
                strSQL+="         AND co_cfg=" + rstCab.getString("co_cfg") + "";
                strSQL+="         GROUP BY co_emp, co_estFin, co_cfg) AS x,";
                strSQL+="         (";
                strSQL+="         SELECT";
                strSQL+="         MAX(ne_mes) AS maxMes, co_emp, co_estFin, co_cfg, ne_ani";
                strSQL+="         FROM tbm_detCfgEstFinPer";
                strSQL+="         WHERE co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+="         AND co_estFin=" + rstCab.getString("co_estFin") + "";
                strSQL+="         AND co_cfg=" + rstCab.getString("co_cfg") + "";
                strSQL+="         GROUP BY co_emp, co_estFin, co_cfg, ne_ani) AS y";
                strSQL+=" WHERE x.co_emp=y.co_emp AND x.co_estFin=y.co_estFin AND x.co_cfg=y.co_cfg AND x.maxAni=y.ne_ani";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    arlRegOcuColCns.add(INT_ARL_MES_MAX, "" + rst.getInt("maxMes"));
                    arlRegOcuColCns.add(INT_ARL_ANI_MAX, "" + rst.getInt("maxAni"));
                    arlDatOcuColCns.add(arlRegOcuColCns);
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
            System.out.println("ARRAYLIST: " + arlDatOcuColCns.toString());
            
            int intMesMin=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MIN);
            System.out.println("MES MINIMO: " + intMesMin);
            for(int i=(INT_TBL_DAT_TIP_DSC+1);i<(intMesMin+INT_TBL_DAT_TIP_DSC);i++){
                javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
                tcmAux.getColumn(i).setWidth(0);
                tcmAux.getColumn(i).setMaxWidth(0);
                tcmAux.getColumn(i).setMinWidth(0);
                tcmAux.getColumn(i).setPreferredWidth(0);
                tcmAux.getColumn(i).setResizable(false);
                
                javax.swing.table.TableColumnModel tcmAuxTot=tblTot.getColumnModel();
                tcmAuxTot.getColumn(i).setWidth(0);
                tcmAuxTot.getColumn(i).setMaxWidth(0);
                tcmAuxTot.getColumn(i).setMinWidth(0);
                tcmAuxTot.getColumn(i).setPreferredWidth(0);
                tcmAuxTot.getColumn(i).setResizable(false);
            }
            
            int intMesMax=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MAX);
            System.out.println("MES MAXIMO: " + intMesMax);
            
            
            int intNumColIni=0;
            int i=0;
            intNumColIni=(tblDatSal.getColumnCount()-2);
            i=intNumColIni;
            int m=0;
            while (m<(12-intMesMax)){
                
                javax.swing.table.TableColumnModel tcmAux=tblDatSal.getColumnModel();
                tcmAux.getColumn(i).setWidth(0);
                tcmAux.getColumn(i).setMaxWidth(0);
                tcmAux.getColumn(i).setMinWidth(0);
                tcmAux.getColumn(i).setPreferredWidth(0);
                tcmAux.getColumn(i).setResizable(false);
                
                javax.swing.table.TableColumnModel tcmAuxTot=tblTot.getColumnModel();
                tcmAuxTot.getColumn(i).setWidth(0);
                tcmAuxTot.getColumn(i).setMaxWidth(0);
                tcmAuxTot.getColumn(i).setMinWidth(0);
                tcmAuxTot.getColumn(i).setPreferredWidth(0);
                tcmAuxTot.getColumn(i).setResizable(false);
                i--;
                m++;
                
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
        
    
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    
     /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro(){
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
    
    
     private boolean calculaPorcentajesModificacion(){
        boolean blnRes=true;
        int k=0;
        double dblVal=0.00;
        double dblPrcIncDec=0.00;
        double dblValTblMes=0.00;
        String strValTblMes="";
        String strPrcIncDec="";
        String strCodCtaArl="", strCodCtaTbl="";
        String strLinMod="";
        try{
            System.out.println("ARRAYLIST: " + arlDatOcuColCns.toString());
            int intMesMin=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MIN);
            System.out.println("MES MINIMO: " + intMesMin);
            int intMesMax=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MAX);
            System.out.println("MES MAXIMO: " + intMesMax);
            
            
            System.out.println("INICIA EN: " + (intFrsColOcu+intMesMin-1));
            System.out.println("FINALIZA EN: " + (tblDatSal.getColumnCount()-(12-intMesMax)));
            for(int j=(intFrsColOcu+intMesMin-1); j<((tblDatSal.getColumnCount()-(12-intMesMax))-1); j++){
                for(int i=0; i<objTblModSal.getRowCountTrue(); i++){
                    strCodCtaArl="" + (objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_COD)==null?"":objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_COD));
                    strCodCtaTbl=""+(  objTblModSal.getValueAt(i, INT_TBL_DAT_COD_CTA)==null?"":objTblModSal.getValueAt(i, INT_TBL_DAT_COD_CTA)  );
                    strLinMod="" + (objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_LIN)==null?"":objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_LIN));
                    if(strCodCtaArl.equals(strCodCtaTbl)){
                        if(strLinMod.equals("M")){
                            strPrcIncDec="" + objUti.getStringValueAt(arlDat, k, INT_ARL_PRC_POR);
                            dblPrcIncDec=Double.parseDouble(strPrcIncDec);
                            dblPrcIncDec=(dblPrcIncDec/100);
                            strValTblMes=""+ (   (  objTblModSal.getValueAt(i, j)==null?"0.00":objTblModSal.getValueAt(i, j)  )==""?"0.00":(  objTblModSal.getValueAt(i, j)==null?"0.00":objTblModSal.getValueAt(i, j)  )     );
                            dblValTblMes=Double.parseDouble(strValTblMes);
                            dblVal=dblValTblMes+(dblValTblMes*dblPrcIncDec);
                            tblDatSal.setValueAt( "" +(objUti.redondear(dblVal, objParSis.getDecimalesMostrar())), i, j  );                            
                        }
                    }
                }
                k++;
//                System.out.println("K CONTIENE: " +k);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

     
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarCab()){
                    if (updateDet()){

                            if(updatePorcentajesDetalle()){
                                con.commit();
                                blnRes=true;
                            }
                        }
                        else
                            con.rollback();
//                    }
//                    else
//                        con.rollback();
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
    
    
    private boolean actualizarCab(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabCfgEstFinPer";
                strSQL+=" SET";
                strSQL+=" fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_estFin=" + rstCab.getString("co_estFin");
                strSQL+=" AND co_cfg=" + rstCab.getString("co_cfg");
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
    
    
    private boolean eliminarDet(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_detCfgEstFinPer";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_estFin=" + rstCab.getString("co_estFin");
                strSQL+=" AND co_cfg=" + rstCab.getString("co_cfg");
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
    

    
    private boolean insertarDetModific(){
        boolean blnRes=true;
        tblDatIncDec=objCon48_01.getTableIncDec();
        int k=0;
        int intNumFilTbl=objCon48_01.numFilTabla();
        int intNumColIni=(numColModIni);
        
        int intNumColFin=objTblModSal.getColumnCount();
        
        String strArlAni="", strArlCodCta="", strArlMesNum="";
        String strTblAni="", strTblCodCta="", strTblMesNum="";
        strAux="";
        try{
            if (con!=null){
                stm=con.createStatement();
                
                
                
                System.out.println("ARRAYLIST: " + arlDatOcuColCns.toString());
                int intMesMin=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MIN);
                System.out.println("MES MINIMO: " + intMesMin);
                int intMesMax=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MAX);
                System.out.println("MES MAXIMO: " + intMesMax);


                System.out.println("INICIA EN: " + (intFrsColOcu+intMesMin-1));
                System.out.println("FINALIZA EN: " + (tblDatSal.getColumnCount()-(12-intMesMax)));
                
                for(int i=0;i<objTblModSal.getRowCountTrue();i++){
                    for(int j=(intFrsColOcu+intMesMin-1); j<((tblDatSal.getColumnCount()-(12-intMesMax))-1); j++){
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detcfgestfinper(";
                        strSQL+=" co_emp, co_estfin, co_cfg, ne_ani,";
                        strSQL+=" ne_mes, nd_por, nd_val, co_reg)";
                        strSQL+=" VALUES(";
                        strSQL+="" + objParSis.getCodigoEmpresa() + ",";
                        strSQL+="" + txtCodRep.getText() + ",";
                        strSQL+="" + txtCfg.getText() + ",";
                        strSQL+="" + tblDatIncDec.getValueAt(k, INT_TBL_ANI) + ",";
                        strSQL+="" + tblDatIncDec.getValueAt(k, INT_TBL_MES_NUM) + ",";
                        strSQL+="" + objUti.codificar(strAux, 3) + ",";
                        strSQL+="" + objUti.codificar(objTblModSal.getValueAt(i, j), 3) + ",";
                        strSQL+="" + objUti.codificar(""+objTblModSal.getValueAt(i, INT_TBL_DAT_COD_REG), 3) + "";
                        strSQL+=")";
                        System.out.println("SQL DE DETALLE: " + strSQL);
                        stm.executeUpdate(strSQL);
//                        if(k<tblDatIncDec.getColumnCount())
                            k++;
                        System.out.println("K TIENE: " + k);
                        System.out.println("SIZE fila: " + tblDatIncDec.getRowCount());
                    }
                    k=0;
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
         
     private boolean updateDet(){
        boolean blnRes=true;
        tblDatIncDec=objCon48_01.getTableIncDec();
        int k=0;
        int intNumFilTbl=objCon48_01.numFilTabla();
        int intNumColIni=(numColModIni);
        
        int intNumColFin=objTblModSal.getColumnCount();
        
        String strArlAni="", strArlCodCta="", strArlMesNum="";
        String strTblAni="", strTblCodCta="", strTblMesNum="";
        strAux="";
        try{
            if (con!=null){
                stm=con.createStatement();
                
                System.out.println("ARRAYLIST: " + arlDatOcuColCns.toString());
                int intMesMin=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MIN);
                System.out.println("MES MINIMO: " + intMesMin);
                int intMesMax=objUti.getIntValueAt(arlDatOcuColCns, 0, INT_ARL_MES_MAX);
                System.out.println("MES MAXIMO: " + intMesMax);


                System.out.println("INICIA EN: " + (intFrsColOcu+intMesMin-1));
                System.out.println("FINALIZA EN: " + (tblDatSal.getColumnCount()-(12-intMesMax)));
                
                for(int i=0;i<objTblModSal.getRowCountTrue();i++){
                    for(int j=(intFrsColOcu+intMesMin-1); j<((tblDatSal.getColumnCount()-(12-intMesMax))-1); j++){
                        strSQL="";
                        strSQL+="UPDATE tbm_detcfgestfinper";
                        strSQL+=" SET nd_val=" + objUti.codificar(objTblModSal.getValueAt(i, j), 3) + "";
                        strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND co_estFin=" + rstCab.getString("co_estFin") + "";
                        strSQL+=" AND co_cfg=" + rstCab.getString("co_cfg") + "";                        
                        strSQL+=" AND ne_ani="  + tblDatIncDec.getValueAt(k, INT_TBL_ANI) + "";
                        strSQL+=" AND ne_mes="  + tblDatIncDec.getValueAt(k, INT_TBL_MES_NUM) + "";
                        strSQL+=" AND co_reg=" + objUti.codificar(""+objTblModSal.getValueAt(i, INT_TBL_DAT_COD_REG), 3) + "";
                        System.out.println("SQL DE DETALLE: " + strSQL);
                        stm.executeUpdate(strSQL);
//                        if(k<tblDatIncDec.getColumnCount())
                            k++;
                        System.out.println("K TIENE: " + k);
                        System.out.println("SIZE fila: " + tblDatIncDec.getRowCount());
                    }
                    k=0;
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
     
     
     private boolean calculaTblDatosColumnaTotalesInsercion(){
         boolean blnRes=true;
         double dblValUni=0.00;
         double dblValAcu=0.00;
         
         
         try{
            for(int i=0;i<objTblModSal.getRowCountTrue();i++){
                int intNumColFin=objTblModSal.getColumnCount();
                for(int j=(intFrsColOcu); j<(intNumColFin-1); j++){
                    dblValUni=Double.parseDouble("" + (objTblModSal.getValueAt(i, j)==null?"0.00":objTblModSal.getValueAt(i, j))  );
                    dblValAcu+=dblValUni;
                    System.out.println("VALOR SIMPLE: " + dblValAcu);
                    System.out.println("j : " + (intNumColFin-1));
                }
                objTblModSal.setValueAt(""+dblValAcu, i, (intNumColFin-1));
                System.out.println("i: " + i);
                System.out.println("COLUMNA A SETEAR: " + (intNumColFin-1));
                System.out.println("VALOR ACUMULADO: " + dblValAcu);
                dblValAcu=0.00;
            }
         }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
         return blnRes;
     }
        
     private boolean calculaTblTotalColumnaTotalesInsercion(){
         boolean blnRes=true;
         double dblValUni=0.00;
         double dblValAcu=0.00;
         
         int intNumColFin=objTblModTot.getColumnCount();
         try{
            for(int j=(intFrsColOcu); j<(intNumColFin-1); j++){
                dblValUni=Double.parseDouble("" + (objTblModTot.getValueAt(0, j)==null?"0.00":objTblModTot.getValueAt(0, j))  );
                dblValAcu+=dblValUni;
            }
            tblTot.setValueAt(""+dblValAcu, 0, (intNumColFin-1));
            dblValAcu=0.00;
         }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
         return blnRes;
     }
     
     /*
     private boolean isCamModSal(){
         if(! objTblModSal.isDataModelChanged()){
             if(objTooBar.getEstado()=='n'){
                 mostrarMsgInf("<HTML>No se ha ingresado nungún dato en el reporte.</HTML>");
                 return false;
             }
             else{
                mostrarMsgInf("<HTML>No se han realizado cambios en el reporte.</HTML>");
                return false;                 
             }
         }
         return true;    
     }
     */
     
     
     private boolean isCamModSal(){
           if(objTblModSal==null){
               if(! objTblModSalIni.isDataModelChanged()){
                 mostrarMsgInf("<HTML>No se ha ingresado nungún dato en el reporte.</HTML>");
                 return false;                   
               }
           }
           else{
               if(! objTblModSal.isDataModelChanged()){
                    mostrarMsgInf("<HTML>No se han realizado cambios en el reporte.</HTML>");
                    return false;                   
               }
           }
           
           
           
           
         return true;    
     }     
     
     
     
     
    
}
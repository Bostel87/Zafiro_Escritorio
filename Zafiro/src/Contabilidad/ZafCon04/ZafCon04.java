/*
 * ZafCon04.java
 *
 * Created on 02 de noviembre de 2005, 11:25 PM
 * created ilino
 */
package Contabilidad.ZafCon04;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */

public class ZafCon04 extends javax.swing.JInternalFrame

{
    //Constantes: Columnas del JTable General
    final int INT_TBL_GRL_DAT_LIN=0;
    final int INT_TBL_GRL_DAT_DES=1;
    final int INT_TBL_GRL_DAT_CAN=2;
    final int INT_TBL_GRL_DAT_PRE=3;
    final int INT_TBL_GRL_DAT_SUB=4;
    final int INT_TBL_GRL_DAT_EST_IVA=5;
    final int INT_TBL_GRL_DAT_IVA=6;
    final int INT_TBL_GRL_DAT_TOT=7;
    final int INT_TBL_GRL_DAT_REG=8;

    //Constantes: Columnas del JTable Motivos
    final int INT_TBL_MOT_DAT_LIN=0;
    final int INT_TBL_MOT_DAT_COD_MOT=1;
    final int INT_TBL_MOT_DAT_DES_COR_MOT=2;
    final int INT_TBL_MOT_DAT_BUT_MOT=3;
    final int INT_TBL_MOT_DAT_DES_LAR_MOT=4;
    final int INT_TBL_MOT_DAT_COD_REG=5;

    //Constantes: Columnas del JTable Forma de Pago
    final int INT_TBL_FOR_PAG_DAT_LIN=0;
    final int INT_TBL_FOR_PAG_DAT_DIA_CRE=1;
    final int INT_TBL_FOR_PAG_DAT_FEC_VEN=2;
    final int INT_TBL_FOR_PAG_DAT_COD_TIP_RET=3;
    final int INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET=4;
    final int INT_TBL_FOR_PAG_DAT_BUT_TIP_RET=5;
    final int INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET=6;
    final int INT_TBL_FOR_PAG_DAT_POR_RET=7;
    final int INT_TBL_FOR_PAG_DAT_APL=8;
    final int INT_TBL_FOR_PAG_DAT_EST_APL=9;
    final int INT_TBL_FOR_PAG_DAT_BAS_IMP=10;
    final int INT_TBL_FOR_PAG_DAT_COD_SRI=11;
    final int INT_TBL_FOR_PAG_DAT_VAL_RET=12;
    final int INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV=13;

    //Constantes: Columnas del JTable Facturas de Proveedores
    final int INT_TBL_FAC_PRV_DAT_LIN=0;
    final int INT_TBL_FAC_PRV_DAT_NUM_FAC=1;
    final int INT_TBL_FAC_PRV_DAT_FEC_FAC=2;
    final int INT_TBL_FAC_PRV_DAT_VAL_FAC=3;
    final int INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA=4;
    final int INT_TBL_FAC_PRV_DAT_NUM_SER=5;
    final int INT_TBL_FAC_PRV_DAT_NUM_AUT=6;
    final int INT_TBL_FAC_PRV_DAT_FEC_CAD=7;
    final int INT_TBL_FAC_PRV_DAT_SUB=8;
    final int INT_TBL_FAC_PRV_DAT_IVA=9;
    final int INT_TBL_FAC_PRV_DAT_NUM_REG=10;
    final int INT_TBL_FAC_PRV_DAT_VAL_PND_PAG=11;

    //Variables generales.
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModMot,objTblModForPag, objTblModFacPrv;
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private Vector vecDatForPag, vecRegForPag;
    private Vector vecDatFacPrv, vecRegFacPrv;
    private Vector vecDatMot, vecRegMot;
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strCodBen, strNomBen;             //Contenido del campo al obtener el foco.
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private String strFecDocIni;
    private String strEstImpDoc;

    private boolean blnNecAutAnu;//true si necesita de autorizacion el documento, si no necesita es false
    private boolean blnDocAut;//true si el documento ya fue autorizado para ser anulado, false si falta todavia autorizarlo

    private ZafVenCon vcoPrv, vcoCom;                           //Ventana de consulta "Proveedor".
    private String strIdePrv, strDirPrv, strCodPrv, strDesLarPrv, strTelPrv, strCiuPrv;

    private String strNumSerFacPrv;
    private String strNumAutSriFacPrv;
    private String strNumFecCadFacPrv;

    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;

    private ZafTblPopMnu objTblPopMnu, objTblPopMnuMot, objTblPopMnuForPag, objTblPopMnuFacPrv;
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAdaMot objMouMotAdaMot;
    private ZafMouMotAdaForPag objMouMotAdaForPag;
    private ZafMouMotAdaFacPrv objMouMotAdaFacPrv;
    private ZafTblFilCab objTblFilCab;

    private ZafTblCelRenChk objTblCelRenChkIva;
    private ZafTblCelEdiChk objTblCelEdiChkIva;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelEdiTxt objTblCelEdiTxtCan;
    private ZafTblCelEdiTxt objTblCelEdiTxtPre;
    private ZafTblCelEdiTxt objTblCelEdiTxtBasImp;
    private ZafTblCelEdiTxt objTblCelEdiTxtFacPrv;
    private ZafTblCelEdiTxt objTblCelEdiTxtValFac;
    private ZafTblCelEdiTxt objTblCelEdiTxtValFacSinIva;

    private ZafTblCelEdiTxt objTblCelEdiTxtFecCad, objTblCelEdiTxtNumAutSriFacPrv, objTblCelEdiTxtNumSer;
    private ZafTblCelEdiTxt objTblCelEdiTxtNumFac;

    private ZafVenCon vcoMot;
    private ZafTblCelRenBut objTblCelRenButMot;
    private ZafTblCelEdiButVco objTblCelEdiButVcoMot;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoMot;

    private ZafVenCon vcoRet;
    private ZafTblCelRenBut objTblCelRenButForPag;
    private ZafTblCelEdiButVco objTblCelEdiButvcoRet;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoRet;


    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoBen;

    private String strCodForPag, strNomForPag;
    private ZafVenCon vcoForPag;

    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;

    private int intCodCtaIva;
    private String strNumCtaIva;
    private String strNomCtaIva;
    private int intCodCtaTot;
    private String strNomCtaTot;
    private String strNumCtaTot;
    private int intCodCtaSubTot;
    private String strNomCtaSubTot;
    private String strNumCtaSubTot;

    private ZafDatePicker dtpFecVenFacPrv, dtpFecAct;
    private String strFecSis;
    private int intNumRegFacPrv, intNumRegMotDoc;
    private String strAliCom, strNomCom, strCodCom;
    private final int INT_COD_TIP_DOC_REC=177;
    private int intCodDocRec;

    private ZafDatePicker dtpFecFacPrv;
    //private ZafDatePicker dtpFecMaxRecFacPrv;
    private ZafDatePicker dtpFecCorIni;
    private String strCodCatTipDoc;
    private String strNecPerAutPag;
    private ArrayList arlRegAutPag, arlDatAutPag;
    final int INT_ARL_COD_REG=0;
    final int INT_ARL_EST_AUT_PAG=1;
    final int INT_ARL_COD_CTA_PAG=2;

    private ArrayList arlRegPed, arlDatPed;
    private int INT_ARL_PED_COD_EMP=0;
    private int INT_ARL_PED_COD_LOC=1;
    private int INT_ARL_PED_COD_TIP_DOC=2;
    private int INT_ARL_PED_COD_DOC=3;
    private int INT_ARL_PED_FEC_EMB=4;
    private int INT_ARL_PED_MES=5;
    private int INT_ARL_PED_NUM_PED=6;
    private int INT_ARL_PED_IND_SEL=7;

    private ArrayList arlRegDiaPag_ForPag, arlDatDiaPag_ForPag;
    private int INT_ARL_DIA_PAG_COD_EMP=0;
    private int INT_ARL_DIA_PAG_COD_FOR_PAG=1;
    private int INT_ARL_DIA_PAG_NUM_DIA_CRE=2;
    
    private boolean blnDetDocTieIva, blnIsCos_Ecu_Det;

    /** Crea una nueva instancia de la clase ZafCon04. */
    public ZafCon04(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatPed=new ArrayList();
            arlDatDiaPag_ForPag=new ArrayList();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();

            vecDatForPag=new Vector();
            vecDatFacPrv=new Vector();
            vecDatDia=new Vector();
            vecDat=new Vector();
            vecDatMot=new Vector();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            dtpFecFacPrv=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecFacPrv.setText("");

            dtpFecCorIni=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecCorIni.setText("");
            strNecPerAutPag="N";
            arlDatAutPag=new ArrayList();                
            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }


    /** Creates new form ZafCon04 */
    public ZafCon04(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
    {
      try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();


            vecDatForPag=new Vector();
            vecDatFacPrv=new Vector();
            vecDatDia=new Vector();
            vecDat=new Vector();
            vecDatMot=new Vector();


            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoEmpresa(codigoEmpresa);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);

            consultarReg();
            objTooBar.setVisible(false);
            strNecPerAutPag="N";


      }
      catch (CloneNotSupportedException e)
      {
          objUti.mostrarMsgErr_F1(this, e);
      }
    }

    /** Creates new form ZafCon04 Para T/C  */
    public ZafCon04(ZafParSis obj, int codigoTipoDocumento, int codigoProveedor, int codigoBeneficiario, String fecha, BigDecimal valorDxP, int codigoMenu, Connection conexion)
    {
      try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();

            vecDatForPag=new Vector();
            vecDatFacPrv=new Vector();
            vecDatDia=new Vector();
            vecDat=new Vector();
            vecDatMot=new Vector();

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            strNecPerAutPag="N";
            objParSis.setCodigoMenu(codigoMenu);
            objTooBar.clickInsertar();

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            mostrarVenConTipDoc(3);

            txtCodPrv.setText(""+codigoProveedor);
            mostrarVenConPrv(1);

            txtCodBen.setText(""+codigoBeneficiario);
            mostrarVenConBen(1);

            txtCodCom.setText(""+objParSis.getCodigoUsuario());
            mostrarVenConCom(3);

            dtpFecDoc.setText(fecha);
            txtSub.setText(""+valorDxP);
            txtIva.setText("0");
            txtTot.setText(""+valorDxP);

            txtCodForPag.setText("1");

            con=conexion;

            if(setDetConIntMovInv()){//detalle
                if(setPagMovInv()){//forma de pago
                    if(setRetMovInv()){//motivos
                        generarDiarioDocumentoTC();
                        generarObservacion();
                        //se le envia la conexion porque desde ZafCxP22 se abre la conexion y se le hace setAutoCommit(false)
                        //, sino se hiciera esto, la informacion de ZafCxP22 si ocurre error no se guarda, pero la de ZfCon04 si.
                        insertarReg(con);
                        //no se puede hacer a continuacion la consulta porque aun no se ha hecho commit y por tanto no se encuentra aun el registro si se manda a hacer consulta
                        //objTooBar.afterInsertar();

                    }
                }
            }


      }
      catch (CloneNotSupportedException e)
      {
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

      panFrm = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();
      tabFrm = new javax.swing.JTabbedPane();
      panGen = new javax.swing.JPanel();
      panGenCab = new javax.swing.JPanel();
      lblTipDoc = new javax.swing.JLabel();
      txtCodTipDoc = new javax.swing.JTextField();
      txtDesCorTipDoc = new javax.swing.JTextField();
      txtDesLarTipDoc = new javax.swing.JTextField();
      butTipDoc = new javax.swing.JButton();
      lblFecDoc = new javax.swing.JLabel();
      lblPrv = new javax.swing.JLabel();
      txtCodPrv = new javax.swing.JTextField();
      txtDesLarPrv = new javax.swing.JTextField();
      butPrv = new javax.swing.JButton();
      lblNumDoc = new javax.swing.JLabel();
      txtNumDoc = new javax.swing.JTextField();
      butNumDoc = new javax.swing.JButton();
      lblBen = new javax.swing.JLabel();
      txtCodBen = new javax.swing.JTextField();
      txtNomBen = new javax.swing.JTextField();
      butBen = new javax.swing.JButton();
      lblCodDoc = new javax.swing.JLabel();
      txtCodDoc = new javax.swing.JTextField();
      lblCom = new javax.swing.JLabel();
      txtAliCom = new javax.swing.JTextField();
      txtNomCom = new javax.swing.JTextField();
      butCom = new javax.swing.JButton();
      lblNumCot = new javax.swing.JLabel();
      txtNumCot = new javax.swing.JTextField();
      txtCodCom = new javax.swing.JTextField();
      chkEmiChqAntRecFacPrv = new javax.swing.JCheckBox();
      txtRucPrv = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      txtCnfNumRuc = new javax.swing.JTextField();
      chkNecMarLis = new javax.swing.JCheckBox();
      cboNotPed_PedEmb = new javax.swing.JComboBox();
      lblCom1 = new javax.swing.JLabel();
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
      jPanel1 = new javax.swing.JPanel();
      lblSub = new javax.swing.JLabel();
      lblIva = new javax.swing.JLabel();
      lblTot = new javax.swing.JLabel();
      txtSub = new javax.swing.JTextField();
      txtIva = new javax.swing.JTextField();
      txtTot = new javax.swing.JTextField();
      panMot = new javax.swing.JPanel();
      spnMot = new javax.swing.JScrollPane();
      tblMot = new javax.swing.JTable();
      panForPag = new javax.swing.JPanel();
      panFilForPag = new javax.swing.JPanel();
      lblForPag = new javax.swing.JLabel();
      txtCodForPag = new javax.swing.JTextField();
      txtNomForPag = new javax.swing.JTextField();
      butForPag = new javax.swing.JButton();
      txtNumForPag = new javax.swing.JTextField();
      txtTipForPag = new javax.swing.JTextField();
      panDatForPag = new javax.swing.JPanel();
      jScrollPane2 = new javax.swing.JScrollPane();
      tblForPag = new javax.swing.JTable();
      panPieForPag = new javax.swing.JPanel();
      lblDif = new javax.swing.JLabel();
      txtForPagDif = new javax.swing.JTextField();
      panFac = new javax.swing.JPanel();
      panDatFacPrv = new javax.swing.JPanel();
      jScrollPane3 = new javax.swing.JScrollPane();
      tblFacPrv = new javax.swing.JTable();
      panPieFacPrv = new javax.swing.JPanel();
      lblDifFacPrv = new javax.swing.JLabel();
      txtFacPrvDif = new javax.swing.JTextField();
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

      panGenCab.setPreferredSize(new java.awt.Dimension(0, 126));
      panGenCab.setLayout(null);

      lblTipDoc.setText("Tipo de documento:");
      lblTipDoc.setToolTipText("Tipo de documento");
      panGenCab.add(lblTipDoc);
      lblTipDoc.setBounds(0, 4, 100, 20);
      panGenCab.add(txtCodTipDoc);
      txtCodTipDoc.setBounds(66, 4, 32, 20);

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
      txtDesCorTipDoc.setBounds(98, 4, 56, 20);

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
      txtDesLarTipDoc.setBounds(154, 4, 270, 20);

      butTipDoc.setText("...");
      butTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butTipDocActionPerformed(evt);
         }
      });
      panGenCab.add(butTipDoc);
      butTipDoc.setBounds(424, 4, 20, 20);

      lblFecDoc.setText("Fecha del documento:");
      lblFecDoc.setToolTipText("Fecha del documento");
      panGenCab.add(lblFecDoc);
      lblFecDoc.setBounds(444, 4, 128, 20);

      lblPrv.setText("Proveedor:");
      lblPrv.setToolTipText("Proveedor");
      panGenCab.add(lblPrv);
      lblPrv.setBounds(0, 22, 100, 20);

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
      panGenCab.add(txtCodPrv);
      txtCodPrv.setBounds(98, 24, 38, 20);

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
      txtDesLarPrv.setBounds(238, 24, 186, 20);

      butPrv.setText("...");
      butPrv.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butPrvActionPerformed(evt);
         }
      });
      panGenCab.add(butPrv);
      butPrv.setBounds(424, 24, 20, 20);

      lblNumDoc.setText("Número de documento:");
      lblNumDoc.setToolTipText("Número alterno 1");
      panGenCab.add(lblNumDoc);
      lblNumDoc.setBounds(444, 24, 134, 20);

      txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNumDocFocusLost(evt);
         }
      });
      panGenCab.add(txtNumDoc);
      txtNumDoc.setBounds(572, 24, 92, 20);

      butNumDoc.setText("...");
      butNumDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butNumDocActionPerformed(evt);
         }
      });
      panGenCab.add(butNumDoc);
      butNumDoc.setBounds(664, 24, 20, 20);

      lblBen.setText("Beneficiario:");
      lblBen.setToolTipText("Beneficiario");
      panGenCab.add(lblBen);
      lblBen.setBounds(0, 40, 100, 20);

      txtCodBen.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodBenActionPerformed(evt);
         }
      });
      txtCodBen.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCodBenFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodBenFocusLost(evt);
         }
      });
      panGenCab.add(txtCodBen);
      txtCodBen.setBounds(98, 44, 56, 20);

      txtNomBen.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNomBenActionPerformed(evt);
         }
      });
      txtNomBen.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNomBenFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNomBenFocusLost(evt);
         }
      });
      panGenCab.add(txtNomBen);
      txtNomBen.setBounds(154, 44, 270, 20);

      butBen.setText("...");
      butBen.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butBenActionPerformed(evt);
         }
      });
      panGenCab.add(butBen);
      butBen.setBounds(424, 44, 20, 20);

      lblCodDoc.setText("Código del documento:");
      lblCodDoc.setToolTipText("Número alterno 2");
      panGenCab.add(lblCodDoc);
      lblCodDoc.setBounds(444, 44, 130, 20);
      panGenCab.add(txtCodDoc);
      txtCodDoc.setBounds(572, 44, 112, 20);

      lblCom.setText("Comprador:");
      lblCom.setToolTipText("Beneficiario");
      panGenCab.add(lblCom);
      lblCom.setBounds(0, 62, 100, 20);

      txtAliCom.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtAliComActionPerformed(evt);
         }
      });
      txtAliCom.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtAliComFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtAliComFocusLost(evt);
         }
      });
      panGenCab.add(txtAliCom);
      txtAliCom.setBounds(98, 64, 56, 20);

      txtNomCom.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNomComFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNomComFocusLost(evt);
         }
      });
      panGenCab.add(txtNomCom);
      txtNomCom.setBounds(154, 64, 270, 20);

      butCom.setText("...");
      butCom.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butComActionPerformed(evt);
         }
      });
      panGenCab.add(butCom);
      butCom.setBounds(424, 64, 20, 20);

      lblNumCot.setText("Número de cotización:");
      lblNumCot.setToolTipText("Valor del documento");
      panGenCab.add(lblNumCot);
      lblNumCot.setBounds(444, 64, 134, 20);

      txtNumCot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panGenCab.add(txtNumCot);
      txtNumCot.setBounds(572, 64, 112, 20);
      panGenCab.add(txtCodCom);
      txtCodCom.setBounds(66, 64, 30, 20);

      chkEmiChqAntRecFacPrv.setText("Emitir cheque antes de recibir la factura del proveedor");
      panGenCab.add(chkEmiChqAntRecFacPrv);
      chkEmiChqAntRecFacPrv.setBounds(0, 86, 350, 16);
      panGenCab.add(txtRucPrv);
      txtRucPrv.setBounds(136, 24, 102, 20);

      jLabel1.setText("Confirmación de # RUC:");
      panGenCab.add(jLabel1);
      jLabel1.setBounds(350, 88, 140, 14);
      panGenCab.add(txtCnfNumRuc);
      txtCnfNumRuc.setBounds(490, 84, 194, 20);

      chkNecMarLis.setText("Documento listo para ser autorizado");
      panGenCab.add(chkNecMarLis);
      chkNecMarLis.setBounds(0, 86, 350, 16);

      panGenCab.add(cboNotPed_PedEmb);
      cboNotPed_PedEmb.setBounds(100, 104, 240, 16);

      lblCom1.setText("Pedido:");
      lblCom1.setToolTipText("Beneficiario");
      panGenCab.add(lblCom1);
      lblCom1.setBounds(0, 105, 80, 14);

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
      panGenTotLbl.add(lblObs1);

      lblObs2.setText("Observación2:");
      panGenTotLbl.add(lblObs2);

      panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

      panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

      txaObs1.setLineWrap(true);
      spnObs1.setViewportView(txaObs1);

      panGenTotObs.add(spnObs1);

      txaObs2.setLineWrap(true);
      spnObs2.setViewportView(txaObs2);

      panGenTotObs.add(spnObs2);

      panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

      jPanel1.setPreferredSize(new java.awt.Dimension(148, 100));
      jPanel1.setLayout(null);

      lblSub.setText("Subtotal:");
      jPanel1.add(lblSub);
      lblSub.setBounds(2, 4, 50, 14);

      lblIva.setText("IVA:");
      jPanel1.add(lblIva);
      lblIva.setBounds(2, 24, 50, 14);

      lblTot.setText("Total:");
      jPanel1.add(lblTot);
      lblTot.setBounds(2, 44, 50, 14);
      jPanel1.add(txtSub);
      txtSub.setBounds(50, 0, 96, 20);
      jPanel1.add(txtIva);
      txtIva.setBounds(50, 20, 96, 20);
      jPanel1.add(txtTot);
      txtTot.setBounds(50, 40, 96, 20);

      panGenTot.add(jPanel1, java.awt.BorderLayout.EAST);

      panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

      tabFrm.addTab("General", panGen);

      panMot.setLayout(new java.awt.BorderLayout());

      tblMot.setModel(new javax.swing.table.DefaultTableModel(
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
      spnMot.setViewportView(tblMot);

      panMot.add(spnMot, java.awt.BorderLayout.CENTER);

      tabFrm.addTab("Motivo", panMot);

      panForPag.setLayout(new java.awt.BorderLayout());

      panFilForPag.setPreferredSize(new java.awt.Dimension(100, 30));
      panFilForPag.setLayout(null);

      lblForPag.setText("Forma de pago:");
      lblForPag.setToolTipText("Beneficiario");
      panFilForPag.add(lblForPag);
      lblForPag.setBounds(10, 8, 90, 14);

      txtCodForPag.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodForPagActionPerformed(evt);
         }
      });
      txtCodForPag.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCodForPagFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodForPagFocusLost(evt);
         }
      });
      panFilForPag.add(txtCodForPag);
      txtCodForPag.setBounds(110, 4, 60, 20);

      txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNomForPagActionPerformed(evt);
         }
      });
      txtNomForPag.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNomForPagFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNomForPagFocusLost(evt);
         }
      });
      panFilForPag.add(txtNomForPag);
      txtNomForPag.setBounds(170, 4, 300, 20);

      butForPag.setText("...");
      butForPag.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butForPagActionPerformed(evt);
         }
      });
      panFilForPag.add(butForPag);
      butForPag.setBounds(470, 4, 20, 20);
      panFilForPag.add(txtNumForPag);
      txtNumForPag.setBounds(500, 4, 30, 20);
      panFilForPag.add(txtTipForPag);
      txtTipForPag.setBounds(540, 4, 30, 20);

      panForPag.add(panFilForPag, java.awt.BorderLayout.NORTH);

      panDatForPag.setLayout(new java.awt.BorderLayout());

      tblForPag.setModel(new javax.swing.table.DefaultTableModel(
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
      jScrollPane2.setViewportView(tblForPag);

      panDatForPag.add(jScrollPane2, java.awt.BorderLayout.CENTER);

      panForPag.add(panDatForPag, java.awt.BorderLayout.CENTER);

      panPieForPag.setPreferredSize(new java.awt.Dimension(100, 30));
      panPieForPag.setLayout(null);

      lblDif.setText("Diferencia:");
      panPieForPag.add(lblDif);
      lblDif.setBounds(490, 10, 70, 14);
      panPieForPag.add(txtForPagDif);
      txtForPagDif.setBounds(570, 6, 110, 20);

      panForPag.add(panPieForPag, java.awt.BorderLayout.PAGE_END);

      tabFrm.addTab("Forma de Pago", panForPag);

      panFac.setLayout(new java.awt.BorderLayout());

      panDatFacPrv.setLayout(new java.awt.BorderLayout());

      tblFacPrv.setModel(new javax.swing.table.DefaultTableModel(
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
      jScrollPane3.setViewportView(tblFacPrv);

      panDatFacPrv.add(jScrollPane3, java.awt.BorderLayout.CENTER);

      panFac.add(panDatFacPrv, java.awt.BorderLayout.CENTER);

      panPieFacPrv.setPreferredSize(new java.awt.Dimension(100, 30));
      panPieFacPrv.setLayout(null);

      lblDifFacPrv.setText("Diferencia:");
      panPieFacPrv.add(lblDifFacPrv);
      lblDifFacPrv.setBounds(490, 10, 70, 14);
      panPieFacPrv.add(txtFacPrvDif);
      txtFacPrvDif.setBounds(570, 6, 110, 20);

      panFac.add(panPieFacPrv, java.awt.BorderLayout.SOUTH);

      tabFrm.addTab("Facturas", panFac);

      panAsiDia.setLayout(new java.awt.BorderLayout());
      tabFrm.addTab("Asiento de diario", panAsiDia);

      panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

      panBar.setLayout(new java.awt.BorderLayout());
      panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

      getContentPane().add(panFrm);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
   }// </editor-fold>//GEN-END:initComponents

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
        actualizarGlo();
    }//GEN-LAST:event_txtNumDocFocusLost

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

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)) {
            if (txtCodPrv.getText().equals("")) {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                txtCodBen.setText("");
                txtNomBen.setText("");
                objTblMod.removeAllRows();
                objTblModMot.removeAllRows();
                objTblModForPag.removeAllRows();
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
            } else {
                mostrarVenConPrv(1);
            }
        } else
            txtCodPrv.setText(strCodPrv);
}//GEN-LAST:event_txtCodPrvFocusLost

    private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
        txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

    private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
        strDesLarPrv=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

    private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)) {
            if (txtDesLarPrv.getText().equals("")) {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                txtCodBen.setText("");
                txtNomBen.setText("");
                objTblMod.removeAllRows();
                objTblModMot.removeAllRows();
                objTblModForPag.removeAllRows();
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
            } else {
                mostrarVenConPrv(2);
            }
        } else
            txtDesLarPrv.setText(strDesLarPrv);
}//GEN-LAST:event_txtDesLarPrvFocusLost

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

    private void txtCodBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBenActionPerformed
        txtCodBen.transferFocus();
}//GEN-LAST:event_txtCodBenActionPerformed

    private void txtCodBenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBenFocusGained
        strCodBen=txtCodBen.getText();
        txtCodBen.selectAll();
}//GEN-LAST:event_txtCodBenFocusGained

    private void txtCodBenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBenFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodBen.getText().equalsIgnoreCase(strCodBen)) {
            if (txtCodBen.getText().equals("")) {
                txtCodBen.setText("");
                txtNomBen.setText("");
            } else {
                mostrarVenConBen(1);
            }
        } else
            txtCodBen.setText(strCodBen);
}//GEN-LAST:event_txtCodBenFocusLost

    private void txtNomBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBenActionPerformed
        txtNomBen.transferFocus();
}//GEN-LAST:event_txtNomBenActionPerformed

    private void txtNomBenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBenFocusGained
        strNomBen=txtNomBen.getText();
        txtNomBen.selectAll();
}//GEN-LAST:event_txtNomBenFocusGained

    private void txtNomBenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBenFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomBen.getText().equalsIgnoreCase(strNomBen)) {
            if (txtNomBen.getText().equals("")) {
                txtCodBen.setText("");
                txtNomBen.setText("");
            } else {
                mostrarVenConBen(2);
            }
        } else
            txtNomBen.setText(strNomBen);
}//GEN-LAST:event_txtNomBenFocusLost

    private void butBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBenActionPerformed
        mostrarVenConBen(0);
}//GEN-LAST:event_butBenActionPerformed

    private void butNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNumDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butNumDocActionPerformed

    private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
        // TODO add your handling code here:
        txtCodForPag.transferFocus();
    }//GEN-LAST:event_txtCodForPagActionPerformed

    private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
        // TODO add your handling code here:
        strCodForPag=txtCodForPag.getText();
        txtCodForPag.selectAll();
    }//GEN-LAST:event_txtCodForPagFocusGained

    private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodForPag))
        {
            if (txtCodForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();
            }
            else
            {
                mostrarVenConForPag(1);
            }
        }
        else
            txtCodForPag.setText(strCodForPag);
    }//GEN-LAST:event_txtCodForPagFocusLost

    private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
        // TODO add your handling code here:
        txtNomForPag.transferFocus();
    }//GEN-LAST:event_txtNomForPagActionPerformed

    private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
        // TODO add your handling code here:
        strNomForPag=txtNomForPag.getText();
        txtNomForPag.selectAll();
    }//GEN-LAST:event_txtNomForPagFocusGained

    private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomForPag.getText().equalsIgnoreCase(strNomForPag))
        {
            if (txtNomForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();
            }
            else
            {
                mostrarVenConForPag(2);
            }
        }
        else
            txtNomForPag.setText(strNomForPag);
    }//GEN-LAST:event_txtNomForPagFocusLost

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        // TODO add your handling code here:
        strCodForPag=txtCodForPag.getText();
        mostrarVenConForPag(0);

    }//GEN-LAST:event_butForPagActionPerformed

    private void txtAliComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAliComActionPerformed
        // TODO add your handling code here:
        txtAliCom.transferFocus();
    }//GEN-LAST:event_txtAliComActionPerformed

    private void txtAliComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAliComFocusGained
        // TODO add your handling code here:
        strAliCom=txtAliCom.getText();
        txtAliCom.selectAll();
    }//GEN-LAST:event_txtAliComFocusGained

    private void txtAliComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAliComFocusLost
        // TODO add your handling code here:
        if (txtAliCom.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtAliCom.getText().equalsIgnoreCase(strAliCom))
            {
                if (txtAliCom.getText().equals(""))
                {
                    txtCodCom.setText("");
                    txtNomCom.setText("");
                }
                else
                {
                    mostrarVenConCom(1);
                }
            }
            else
                txtAliCom.setText(strAliCom);
        }
    }//GEN-LAST:event_txtAliComFocusLost

    private void txtNomComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomComActionPerformed
        // TODO add your handling code here:
        txtNomCom.transferFocus();
    }//GEN-LAST:event_txtNomComActionPerformed

    private void txtNomComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusGained
        // TODO add your handling code here:
        strNomCom=txtNomCom.getText();
        txtNomCom.selectAll();
    }//GEN-LAST:event_txtNomComFocusGained

    private void txtNomComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusLost
        // TODO add your handling code here:
        if (txtNomCom.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomCom.getText().equalsIgnoreCase(strNomCom))
            {
                if (txtNomCom.getText().equals(""))
                {
                    txtCodCom.setText("");
                    txtAliCom.setText("");
                }
                else
                {
                    mostrarVenConCom(2);
                }
            }
            else
                txtNomCom.setText(strNomCom);
        }
    }//GEN-LAST:event_txtNomComFocusLost

    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        // TODO add your handling code here:
        mostrarVenConCom(0);
    }//GEN-LAST:event_butComActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm()
    {
        dispose();
    }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton butBen;
   private javax.swing.JButton butCom;
   private javax.swing.JButton butForPag;
   private javax.swing.JButton butNumDoc;
   private javax.swing.JButton butPrv;
   private javax.swing.JButton butTipDoc;
   private javax.swing.JComboBox cboNotPed_PedEmb;
   private javax.swing.JCheckBox chkEmiChqAntRecFacPrv;
   private javax.swing.JCheckBox chkNecMarLis;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JScrollPane jScrollPane3;
   private javax.swing.JLabel lblBen;
   private javax.swing.JLabel lblCodDoc;
   private javax.swing.JLabel lblCom;
   private javax.swing.JLabel lblCom1;
   private javax.swing.JLabel lblDif;
   private javax.swing.JLabel lblDifFacPrv;
   private javax.swing.JLabel lblFecDoc;
   private javax.swing.JLabel lblForPag;
   private javax.swing.JLabel lblIva;
   private javax.swing.JLabel lblNumCot;
   private javax.swing.JLabel lblNumDoc;
   private javax.swing.JLabel lblObs1;
   private javax.swing.JLabel lblObs2;
   private javax.swing.JLabel lblPrv;
   private javax.swing.JLabel lblSub;
   private javax.swing.JLabel lblTipDoc;
   private javax.swing.JLabel lblTit;
   private javax.swing.JLabel lblTot;
   private javax.swing.JPanel panAsiDia;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panDatFacPrv;
   private javax.swing.JPanel panDatForPag;
   private javax.swing.JPanel panFac;
   private javax.swing.JPanel panFilForPag;
   private javax.swing.JPanel panForPag;
   private javax.swing.JPanel panFrm;
   private javax.swing.JPanel panGen;
   private javax.swing.JPanel panGenCab;
   private javax.swing.JPanel panGenDet;
   private javax.swing.JPanel panGenTot;
   private javax.swing.JPanel panGenTotLbl;
   private javax.swing.JPanel panGenTotObs;
   private javax.swing.JPanel panMot;
   private javax.swing.JPanel panPieFacPrv;
   private javax.swing.JPanel panPieForPag;
   private javax.swing.JScrollPane spnDat;
   private javax.swing.JScrollPane spnMot;
   private javax.swing.JScrollPane spnObs1;
   private javax.swing.JScrollPane spnObs2;
   private javax.swing.JTabbedPane tabFrm;
   private javax.swing.JTable tblDat;
   private javax.swing.JTable tblFacPrv;
   private javax.swing.JTable tblForPag;
   private javax.swing.JTable tblMot;
   private javax.swing.JTextArea txaObs1;
   private javax.swing.JTextArea txaObs2;
   private javax.swing.JTextField txtAliCom;
   private javax.swing.JTextField txtCnfNumRuc;
   private javax.swing.JTextField txtCodBen;
   private javax.swing.JTextField txtCodCom;
   private javax.swing.JTextField txtCodDoc;
   private javax.swing.JTextField txtCodForPag;
   private javax.swing.JTextField txtCodPrv;
   private javax.swing.JTextField txtCodTipDoc;
   private javax.swing.JTextField txtDesCorTipDoc;
   private javax.swing.JTextField txtDesLarPrv;
   private javax.swing.JTextField txtDesLarTipDoc;
   private javax.swing.JTextField txtFacPrvDif;
   private javax.swing.JTextField txtForPagDif;
   private javax.swing.JTextField txtIva;
   private javax.swing.JTextField txtNomBen;
   private javax.swing.JTextField txtNomCom;
   private javax.swing.JTextField txtNomForPag;
   private javax.swing.JTextField txtNumCot;
   private javax.swing.JTextField txtNumDoc;
   private javax.swing.JTextField txtNumForPag;
   private javax.swing.JTextField txtRucPrv;
   private javax.swing.JTextField txtSub;
   private javax.swing.JTextField txtTipForPag;
   private javax.swing.JTextField txtTot;
   // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {

            dtpFecVenFacPrv=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecVenFacPrv.setText("");

            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(572, 4, 112, 20);

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
            this.setTitle(strAux + " v0.2.35 ");
            lblTit.setText(strAux);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodTipDoc.setVisible(false);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBen.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBen.setBackground(objParSis.getColorCamposObligatorios());
            txtAliCom.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCom.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodDoc.setEditable(false);
            txtNumCot.setBackground(objParSis.getColorCamposObligatorios());
            txtCnfNumRuc.setBackground(objParSis.getColorCamposObligatorios());
            txtRucPrv.setBackground(objParSis.getColorCamposObligatorios());

            txtSub.setBackground(objParSis.getColorCamposSistema());
            txtSub.setEditable(false);
            txtIva.setBackground(objParSis.getColorCamposSistema());
            txtIva.setEditable(false);
            txtTot.setBackground(objParSis.getColorCamposSistema());
            txtTot.setEditable(false);

            //Configurar las ZafVenCon.
            configurarVenConMot();
            configurarVenConRet();
            configurarVenConTipDoc();
            configurarVenConPrv();
            configurarVenConBen();
            configurarVenConForPag();
            configurarVenConCom();
            //configurarVenConNumDoc();
            //Configurar los JTables.
            configurarTblDat();
            configurarTblMot();
            configurarTblForPag();
            configurarTblFacPrv();
            cargarNotaPedido_PedidoEmbarcado();


            txtSub.setEnabled(false);
            txtIva.setEnabled(false);
            txtTot.setEnabled(false);


            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

            //Configurar ZafDatePicker:
            dtpFecAct=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecAct.setText(strFecSis);

            txtNumForPag.setVisible(false);
            txtTipForPag.setVisible(false);

            //solo cuando es por contabilidad los tabs de motivos, forma de pago y facturas se hacen editables
            if(objParSis.getCodigoMenu()==1985){//CxP
                tabFrm.remove(panMot);
            }

            txtCodCom.setVisible(false);
            txtCodCom.setEditable(false);
            txtCodCom.setEnabled(false);


            lblCom1.setVisible(false);
            lblCom1.setEnabled(false);
            cboNotPed_PedEmb.setVisible(false);
            cboNotPed_PedEmb.setEditable(false);
            cboNotPed_PedEmb.setEnabled(false);

            strCodCatTipDoc="";


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
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.

            vecCab=new Vector(7);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_GRL_DAT_LIN,"");
            vecCab.add(INT_TBL_GRL_DAT_DES,"Descripción");
            vecCab.add(INT_TBL_GRL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_GRL_DAT_PRE,"Precio");
            vecCab.add(INT_TBL_GRL_DAT_SUB,"Subtotal");
            vecCab.add(INT_TBL_GRL_DAT_EST_IVA,"Con Iva");
            vecCab.add(INT_TBL_GRL_DAT_IVA,"Iva");
            vecCab.add(INT_TBL_GRL_DAT_TOT,"Total");
            vecCab.add(INT_TBL_GRL_DAT_REG,"#Reg.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_PRE, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_SUB, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_IVA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
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
            tcmAux.getColumn(INT_TBL_GRL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_GRL_DAT_PRE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_GRL_DAT_SUB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_GRL_DAT_EST_IVA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_GRL_DAT_IVA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_GRL_DAT_TOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_GRL_DAT_REG).setPreferredWidth(60);


            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_REG, tblDat);




            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_GRL_DAT_EST_IVA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_GRL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_GRL_DAT_DES);
            vecAux.add("" + INT_TBL_GRL_DAT_CAN);
            vecAux.add("" + INT_TBL_GRL_DAT_PRE);
            vecAux.add("" + INT_TBL_GRL_DAT_EST_IVA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_PRE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_SUB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkIva=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_GRL_DAT_EST_IVA).setCellRenderer(objTblCelRenChkIva);
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkIva=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_GRL_DAT_EST_IVA).setCellEditor(objTblCelEdiChkIva);
            objTblCelEdiChkIva.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeValAplEvn=new BigDecimal(0);
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTotal(intFil);
                    cargarPago();
                    generarDiarioDocumento();
                    generarObservacion();
                }
            });

            objTblCelEdiTxtCan=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CAN).setCellEditor(objTblCelEdiTxtCan);
            objTblCelEdiTxtCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTotal(intFil);
                    cargarPago();
                    generarDiarioDocumento();
                    generarObservacion();
                }
            });

            objTblCelEdiTxtPre=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_GRL_DAT_PRE).setCellEditor(objTblCelEdiTxtPre);
            objTblCelEdiTxtPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTotal(intFil);
                    cargarPago();
                    generarDiarioDocumento();
                    generarObservacion();
                }
            });

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
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblMot(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(6);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_MOT_DAT_LIN,"");
            vecCab.add(INT_TBL_MOT_DAT_COD_MOT,"Cód.Mot.");
            vecCab.add(INT_TBL_MOT_DAT_DES_COR_MOT,"Motivo");
            vecCab.add(INT_TBL_MOT_DAT_BUT_MOT,"");
            vecCab.add(INT_TBL_MOT_DAT_DES_LAR_MOT,"");
            vecCab.add(INT_TBL_MOT_DAT_COD_REG,"");


            objTblModMot=new ZafTblMod();
            objTblModMot.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblMot.setModel(objTblModMot);
            //Configurar JTable: Establecer tipo de seleccián.
            tblMot.setRowSelectionAllowed(true);
            tblMot.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuMot=new ZafTblPopMnu(tblMot);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblMot.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblMot.getColumnModel();
            tcmAux.getColumn(INT_TBL_MOT_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_MOT_DAT_COD_MOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_MOT_DAT_DES_COR_MOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_MOT_DAT_BUT_MOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_MOT_DAT_DES_LAR_MOT).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_MOT_DAT_COD_REG).setPreferredWidth(170);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModMot.addSystemHiddenColumn(INT_TBL_MOT_DAT_COD_REG, tblMot);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_MOT_DAT_COD_MOT).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblMot.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaMot=new ZafMouMotAdaMot();
            tblMot.getTableHeader().addMouseMotionListener(objMouMotAdaMot);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblMot);
            tcmAux.getColumn(INT_TBL_MOT_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_MOT_DAT_DES_COR_MOT);
            vecAux.add("" + INT_TBL_MOT_DAT_BUT_MOT);
            vecAux.add("" + INT_TBL_MOT_DAT_DES_LAR_MOT);
            objTblModMot.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Renderizar celdas.
            objTblCelRenButMot=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_MOT_DAT_BUT_MOT).setCellRenderer(objTblCelRenButMot);
            objTblCelRenButMot=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_MOT_DAT_COD_MOT;
            intColTbl[1]=INT_TBL_MOT_DAT_DES_COR_MOT;
            intColTbl[2]=INT_TBL_MOT_DAT_DES_LAR_MOT;
            objTblCelEdiTxtVcoMot=new ZafTblCelEdiTxtVco(tblMot, vcoMot, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_MOT_DAT_DES_COR_MOT).setCellEditor(objTblCelEdiTxtVcoMot);
            objTblCelEdiTxtVcoMot.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strSQLAdd="";
                int intNumRegMotExi=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (txtCodPrv.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un motivo ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoMot.setCancelarEdicion(true);
                        tabFrm.setSelectedIndex(0);
                        tblMot.setRowSelectionInterval(0, 0);
                        txtCodPrv.requestFocus();
                        objTblCelEdiTxtVcoMot.setCancelarEdicion(true);
                    }
                    else{
                        if(objTblModFacPrv.getRowCountTrue()<=0){
                            if(objTblMod.getRowCountTrue() <  (tblMot.getSelectedRow()+1)  ){
                                objTblCelEdiTxtVcoMot.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>El número de motivos es mayor al detalle del documento.<BR>Verifique y vuelva a intentarlo</HTML>");
                            }
                            else
                                objTblCelEdiTxtVcoMot.setCancelarEdicion(false);
                        }
                        else{
                            objTblCelEdiTxtVcoMot.setCancelarEdicion(false);
                        }
                    }
                    intNumRegMotExi=Integer.parseInt(objTblModMot.getValueAt(tblMot.getSelectedRow(), INT_TBL_MOT_DAT_COD_REG)==null?"0":(objTblModMot.getValueAt(tblMot.getSelectedRow(), INT_TBL_MOT_DAT_COD_REG).toString().equals("")?"0":objTblModMot.getValueAt(tblMot.getSelectedRow(), INT_TBL_MOT_DAT_COD_REG).toString()));
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMot.setCampoBusqueda(1);
                    vcoMot.setCriterio1(11);
                    strSQLAdd="";
                    //strSQLAdd+="         AND a6.co_cli=" + txtCodPrv.getText() + "";
                    strSQLAdd+=" GROUP BY a1.co_mot, a1.tx_desCor, a1.tx_desLar";
                    strSQLAdd+=" ORDER BY tx_desCor, a1.co_mot";
                    vcoMot.setCondicionesSQL(strSQLAdd);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoMot.isConsultaAceptada()){

                        objTblModMot.setValueAt(vcoMot.getValueAt(1), (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT);
                        objTblModMot.setValueAt(vcoMot.getValueAt(2), (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_DES_COR_MOT);
                        objTblModMot.setValueAt(vcoMot.getValueAt(3), (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_DES_LAR_MOT);

                        if(intNumRegMotExi==0){
                            intNumRegMotDoc++;
                            objTblModMot.setValueAt("" + intNumRegMotDoc, (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_REG);
                        }

                        if(strNecPerAutPag.equals("S")  ){
                            cargarFormaPago();
                        }
                        else{
                            if(objTblModFacPrv.getRowCountTrue()>0)
                                cargarFormaPagoFacturaProveedor();
                            else
                                cargarFormaPago();
                        }

                    }
                }
            });

            objTblCelEdiButVcoMot=new ZafTblCelEdiButVco(tblMot, vcoMot, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_MOT_DAT_BUT_MOT).setCellEditor(objTblCelEdiButVcoMot);
            objTblCelEdiButVcoMot.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdd="";
                int intNumRegMotExi=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblMot.getSelectedRow();
                    if (txtCodPrv.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un motivo ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoMot.setCancelarEdicion(true);
                        tabFrm.setSelectedIndex(0);
                        tblMot.setRowSelectionInterval(0, 0);
                        txtCodPrv.requestFocus();
                        objTblCelEdiButVcoMot.setCancelarEdicion(true);
                    }
                    else{
                        if(objTblModFacPrv.getRowCountTrue()<=0){
                            if(objTblMod.getRowCountTrue() <  (tblMot.getSelectedRow()+1)  ){
                                objTblCelEdiButVcoMot.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>El número de motivos es mayor al detalle del documento.<BR>Verifique y vuelva a intentarlo</HTML>");
                            }
                            else
                                objTblCelEdiButVcoMot.setCancelarEdicion(false);
                        }
                        else{
//                            if((tblMot.getSelectedRow()+1)>1){
//                                objTblCelEdiButVcoMot.setCancelarEdicion(true);
//                                mostrarMsgInf("<HTML>Al tener facturas de proveedor aplicadas solo puede asignar un solo motivo<BR>Verifique y vuelva a intentarlo</HTML>");
//                            }
//                            else
                                objTblCelEdiButVcoMot.setCancelarEdicion(false);
                        }

                    }

                    intNumRegMotExi=Integer.parseInt(objTblModMot.getValueAt(tblMot.getSelectedRow(), INT_TBL_MOT_DAT_COD_REG)==null?"0":(objTblModMot.getValueAt(tblMot.getSelectedRow(), INT_TBL_MOT_DAT_COD_REG).toString().equals("")?"0":objTblModMot.getValueAt(tblMot.getSelectedRow(), INT_TBL_MOT_DAT_COD_REG).toString()));


                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strSQLAdd="";
                    //strSQLAdd+="         AND a6.co_cli=" + txtCodPrv.getText() + "";
                    strSQLAdd+=" GROUP BY a1.co_mot, a1.tx_desCor, a1.tx_desLar";
                    strSQLAdd+=" ORDER BY tx_desCor, a1.co_mot";
                    vcoMot.setCondicionesSQL(strSQLAdd);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if (objTblCelEdiButVcoMot.isConsultaAceptada()){

                        objTblModMot.setModoOperacion(objTblModMot.INT_TBL_EDI);
                        objTblModMot.removeEmptyRows();
                        objTblModMot.setModoOperacion(objTblModMot.INT_TBL_INS);
                        objTblModMot.setValueAt(vcoMot.getValueAt(1), (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT);
                        objTblModMot.setValueAt(vcoMot.getValueAt(2), (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_DES_COR_MOT);
                        objTblModMot.setValueAt(vcoMot.getValueAt(3), (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_DES_LAR_MOT);
                        if(intNumRegMotExi==0){
                            intNumRegMotDoc++;
                            objTblModMot.setValueAt("" + intNumRegMotDoc, (objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_REG);
                        }

                        if(objTblModFacPrv.getRowCountTrue()>0)
                            cargarFormaPagoFacturaProveedor();
                        else
                            cargarFormaPago();
                    }
                }
            });
            intColVen=null;
            intColTbl=null;




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
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblForPag(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(14);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FOR_PAG_DAT_LIN,"");
            vecCab.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,"Día.Cré.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,"Cód.Tip.Ret.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,"Tip.Ret.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,"");
            vecCab.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,"Tipo de retención");
            vecCab.add(INT_TBL_FOR_PAG_DAT_POR_RET,"%Ret.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_APL,"Aplicado");
            vecCab.add(INT_TBL_FOR_PAG_DAT_EST_APL,"Aplicado");
            vecCab.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,"Bas.Imp.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_COD_SRI,"Cód.Sri.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_VAL_RET,"Valor");
            vecCab.add(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV,"Num.Reg.Fac.Prv.");


            objTblModForPag=new ZafTblMod();
            objTblModForPag.setHeader(vecCab);

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_BAS_IMP, objTblModForPag.INT_COL_DBL, new Integer(0), null);
//            objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_VAL_RET, objTblModForPag.INT_COL_DBL, new Integer(0), null);
//            objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_POR_RET, objTblModForPag.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblForPag.setModel(objTblModForPag);
            //Configurar JTable: Establecer tipo de seleccián.
            tblForPag.setRowSelectionAllowed(true);
            tblForPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuForPag=new ZafTblPopMnu(tblForPag);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblForPag.getColumnModel();
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DIA_CRE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_POR_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_APL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_EST_APL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BAS_IMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_SRI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_VAL_RET).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).setPreferredWidth(60);



//            Configurar JTable: Ocultar columnas del sistema.
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_APL, tblForPag);

//            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_EST_AUT_PAG, tblForPag);
//            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_COD_CTA_PAG, tblForPag);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setResizable(false);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).setResizable(false);

//            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_EST_AUT_PAG).setResizable(false);
//            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_CTA_PAG).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblForPag.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaForPag=new ZafMouMotAdaForPag();
            tblForPag.getTableHeader().addMouseMotionListener(objMouMotAdaForPag);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblForPag);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET);
            vecAux.add("" + INT_TBL_FOR_PAG_DAT_BUT_TIP_RET);
            vecAux.add("" + INT_TBL_FOR_PAG_DAT_BAS_IMP);
            vecAux.add("" + INT_TBL_FOR_PAG_DAT_COD_SRI);


//            //PARA CASOS DE COMPRAS DE ACTIVO FIJO DESACTIVAR ESTE GRUPO, tambien se debe bloquear el listener de la columna de BASE IMPONIBLE
//            vecAux.add("" + INT_TBL_FOR_PAG_DAT_VAL_RET);
//            vecAux.add("" + INT_TBL_FOR_PAG_DAT_DIA_CRE);
//            vecAux.add("" + INT_TBL_FOR_PAG_DAT_FEC_VEN);
//            vecAux.add("" + INT_TBL_FOR_PAG_DAT_POR_RET);




            objTblModForPag.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Renderizar celdas.
            objTblCelRenButForPag=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET).setCellRenderer(objTblCelRenButForPag);
            objTblCelRenButForPag=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[4];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            int intColTbl[]=new int[4];
            intColTbl[0]=INT_TBL_FOR_PAG_DAT_COD_TIP_RET;
            intColTbl[1]=INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET;
            intColTbl[2]=INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET;
            intColTbl[3]=INT_TBL_FOR_PAG_DAT_POR_RET;
            objTblCelEdiTxtVcoRet=new ZafTblCelEdiTxtVco(tblForPag, vcoRet, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET).setCellEditor(objTblCelEdiTxtVcoRet);
            objTblCelEdiTxtVcoRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if( (txtCodPrv.getText().equals(""))  && (objTblModForPag.getRowCountTrue()<=0) ) {
                        mostrarMsgInf("<HTML>El campo Proveedor o motivo no han sido ingresado.<BR>Para agregar un motivo ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoRet.setCancelarEdicion(true);
                        tabFrm.setSelectedIndex(0);
                        tblForPag.setRowSelectionInterval(0, 0);
                        txtCodPrv.requestFocus();
                        objTblCelEdiTxtVcoRet.setCancelarEdicion(true);
                    }
                    else{

//                        System.out.println("txt-usuario: " + objTblMod.getRowCountTrue());
//                        System.out.println("txt-motivo: " + objTblModMot.getRowCountTrue());

                        objTblCelEdiTxtVcoRet.setCancelarEdicion(false);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblModFacPrv.getRowCountTrue()>0)
                        cargarFormaPagoFacturaProveedor();
                    else
                        cargarFormaPago();
                    if (objTblCelEdiTxtVcoRet.isConsultaAceptada()){

                    }

                }
            });

            objTblCelEdiButvcoRet=new ZafTblCelEdiButVco(tblForPag, vcoRet, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET).setCellEditor(objTblCelEdiButvcoRet);
            objTblCelEdiButvcoRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if( (txtCodPrv.getText().equals(""))  && (objTblModForPag.getRowCountTrue()<=0) ) {
                        mostrarMsgInf("<HTML>El campo Proveedor o motivo no han sido ingresado.<BR>Para agregar un motivo ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoRet.setCancelarEdicion(true);
                        tabFrm.setSelectedIndex(0);
                        tblForPag.setRowSelectionInterval(0, 0);
                        txtCodPrv.requestFocus();
                        objTblCelEdiButvcoRet.setCancelarEdicion(true);
                    }
                    else{

//                        System.out.println("but-usuario: " + objTblMod.getRowCountTrue());
//                        System.out.println("but-motivo: " + objTblModMot.getRowCountTrue());

                        objTblCelEdiButvcoRet.setCancelarEdicion(false);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButvcoRet.isConsultaAceptada()){
                        //cargarFormaPago(0);//1 es de acuerdo a la forma de pago ingresada por el usuario y 0 es el predeterminado del cliente/proveedor, cuando se escoge el motivo automaticamente se selecciona el predeterminado
                        if(objTblModFacPrv.getRowCountTrue()>0)
                            cargarFormaPagoFacturaProveedor();
                        else
                            cargarFormaPago();
                    }
                }
            });
            intColVen=null;
            intColTbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_SRI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl=null;



            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BAS_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_VAL_RET).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;



            objTblCelEdiTxtBasImp=new ZafTblCelEdiTxt(tblForPag);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_BAS_IMP).setCellEditor(objTblCelEdiTxtBasImp);
            objTblCelEdiTxtBasImp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strAplRet="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblForPag.getSelectedRow();
                    strAplRet=objTblModForPag.getValueAt(intFil, INT_TBL_FOR_PAG_DAT_EST_APL)==null?"":objTblModForPag.getValueAt(intFil, INT_TBL_FOR_PAG_DAT_EST_APL).toString();
                    if(strAplRet.equals("O"))
                        objTblCelEdiTxtBasImp.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtBasImp.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularValorRetener(intFil);
                }
            });


            txtForPagDif.setEnabled(false);
            txtForPagDif.setBackground(objParSis.getColorCamposSistema());


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
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblFacPrv(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(12);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FAC_PRV_DAT_LIN,"");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_FAC,"Núm.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_FEC_FAC,"Fec.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_FAC,"Val.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA,"Val.Sin.Iva.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_SER,"Núm.Ser.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_AUT,"Núm.Aut.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_FEC_CAD,"Fec.Cad.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_SUB,"Sub.Fac.Prv.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_IVA,"Iva.Fac.Prv");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_REG,"#Reg.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG,"Val.Pnd.Pag.");

            objTblModFacPrv=new ZafTblMod();
            objTblModFacPrv.setHeader(vecCab);

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModFacPrv.setColumnDataType(INT_TBL_FAC_PRV_DAT_VAL_FAC, objTblModFacPrv.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblFacPrv.setModel(objTblModFacPrv);
            //Configurar JTable: Establecer tipo de seleccián.
            tblFacPrv.setRowSelectionAllowed(true);
            tblFacPrv.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuFacPrv=new ZafTblPopMnu(tblFacPrv);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFacPrv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFacPrv.getColumnModel();
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_SUB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG).setPreferredWidth(60);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFacPrv.addSystemHiddenColumn(INT_TBL_FAC_PRV_DAT_NUM_REG, tblFacPrv);
            objTblModFacPrv.addSystemHiddenColumn(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG, tblFacPrv);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFacPrv.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFacPrv=new ZafMouMotAdaFacPrv();
            tblFacPrv.getTableHeader().addMouseMotionListener(objMouMotAdaFacPrv);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer columnas editables.
            //Ya no se debe permitir la edicion en el JTable de Factura de Proveedor.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_FAC);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_FAC);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_VAL_FAC);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_SER);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_AUT);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_CAD);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_SUB); //En la version v0.2.32 se habilito la edicion de esta columna.
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_IVA); //En la version v0.2.32 se habilito la edicion de esta columna.
            objTblModFacPrv.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_FAC);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_FAC);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_VAL_FAC);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_SER);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_AUT);
//            arlAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_CAD);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_SUB);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_IVA);
            objTblModFacPrv.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModFacPrv.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            txtFacPrvDif.setEnabled(false);
            txtFacPrvDif.setBackground(objParSis.getColorCamposSistema());

            objTblCelEdiTxtFacPrv=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_FAC).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setCellEditor(objTblCelEdiTxtFacPrv);
            objTblCelEdiTxtFacPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strFecFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strFecFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_FAC)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString();
                    if(!strFecFacPrv.equals("")){
                        dtpFecDoc.setText(strFecFacPrv);
                    }
                }
            });
//            objTblCelEdiTxtFacPrv=null;


            objTblCelEdiTxtValFac=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellEditor(objTblCelEdiTxtValFac);
            objTblCelEdiTxtValFac.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeTotFacPrv=new BigDecimal("0");
                BigDecimal bdeSubFacPrv=new BigDecimal("0");
                BigDecimal bdeIvaFacPrv=new BigDecimal("0");
                //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
                BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );

                BigDecimal bdeValFacPrvBef=new BigDecimal("0");
                Object objValIvaPrvBef=new BigDecimal("0");
                Object objValSubPrvBef=new BigDecimal("0");
                int intNumRegFacPrvExi=-1;
                BigDecimal bdeFacPrvSinIva=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    intFil=tblFacPrv.getSelectedRow();
                    bdeValFacPrvBef=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    objValSubPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_SUB);
                    objValIvaPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_IVA);
                    if(validaFechaVencimiento(intFil)){
                        objTblCelEdiTxtValFac.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxtValFac.setCancelarEdicion(true);
                    intNumRegFacPrvExi=Integer.parseInt(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString()));

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    System.out.println("intFil: " + intFil);
                    cargarValorIvaFacturaProveedor();
                    bdeFacPrvSinIva=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString()));

                    if(bdeTotFacPrv.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                        objTblModFacPrv.removeEmptyRows();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        if(sumFacPrv_equals_totDct()){


                            //calcular iva
                            BigDecimal bdeFacIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                            
                            bdeIvaFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeFacIva);
                            bdeIvaFacPrv=objUti.redondearBigDecimal(bdeIvaFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeIvaFacPrv, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            //calcular subtotal --)
                            bdeSubFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).add(bdeFacPrvSinIva);
                            bdeSubFacPrv=objUti.redondearBigDecimal(bdeSubFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeSubFacPrv, intFil, INT_TBL_FAC_PRV_DAT_SUB);

                            if(intNumRegFacPrvExi==0){
                                intNumRegFacPrv++;
                                objTblModFacPrv.setValueAt("" + intNumRegFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);
                            }
                            if(objTblModMot.getRowCountTrue()>1){
                                colocaNumRegFacPrv_NumRegForPag();

                            }
                            else{
                                cargarFormaPagoFacturaProveedor();
                                if(objTblModMot.getRowCountTrue()==0){
                                    for(int i=0; i<objTblModForPag.getRowCountTrue();i++)
                                        objTblModForPag.setValueAt(objTblModFacPrv.getValueAt((objTblModFacPrv.getRowCountTrue()-1), INT_TBL_FAC_PRV_DAT_NUM_REG).toString(), i, INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV);
                                }
                            }
                        }
                        else{
                            mostrarMsgInf("<HTML>La suma de las facturas del proveedor exceden el valor del documento.<BR>Verifique y vuelva a intentarlo</HTML>");

                            //objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG);

                            objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                            objTblModFacPrv.setValueAt(objValSubPrvBef, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            objTblModFacPrv.setValueAt(objValIvaPrvBef, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);

                        }

                    }




                }
            });




            objTblCelEdiTxtValFacSinIva=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellEditor(objTblCelEdiTxtValFacSinIva);
            objTblCelEdiTxtValFacSinIva.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeTotFacPrv=new BigDecimal("0");
                BigDecimal bdeFacPrvSinIva=new BigDecimal("0");
                BigDecimal bdeSubFacPrv=new BigDecimal("0");
                BigDecimal bdeIvaFacPrv=new BigDecimal("0");
                //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
                BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                
                BigDecimal bdeValFacPrvBef=new BigDecimal("0");
                Object objValIvaPrvBef=new BigDecimal("0");
                Object objValSubPrvBef=new BigDecimal("0");
                int intNumRegFacPrvExi=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                    bdeValFacPrvBef=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    objValSubPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_SUB);
                    objValIvaPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_IVA);
                    if(validaFechaVencimiento(intFil)){
                        objTblCelEdiTxtValFacSinIva.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxtValFacSinIva.setCancelarEdicion(true);
                    intNumRegFacPrvExi=Integer.parseInt(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_REG).toString()));
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    bdeFacPrvSinIva=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString()));

                    if(bdeTotFacPrv.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                        objTblModFacPrv.removeEmptyRows();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        if(sumFacPrv_equals_totDct()){
                            //calcular iva
                            BigDecimal bdeFacIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                            bdeIvaFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeFacIva);
                            bdeIvaFacPrv=objUti.redondearBigDecimal(bdeIvaFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeIvaFacPrv, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            //calcular subtotal --)
                            bdeSubFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).add(bdeFacPrvSinIva);
                            bdeSubFacPrv=objUti.redondearBigDecimal(bdeSubFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdeSubFacPrv, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            if(intNumRegFacPrvExi==0){
                                intNumRegFacPrv++;
                                objTblModFacPrv.setValueAt("" + intNumRegFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);
                            }
                            cargarFormaPagoFacturaProveedor();
                        }
                        else{
                            mostrarMsgInf("<HTML>La suma de las facturas del proveedor exceden el valor del documento.<BR>Verifique y vuelva a intentarlo</HTML>");
                            objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                            objTblModFacPrv.setValueAt(objValSubPrvBef, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            objTblModFacPrv.setValueAt(objValIvaPrvBef, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_NUM_REG);
                        }

                    }
                }
            });


            objTblCelEdiTxtNumSer=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setCellEditor(objTblCelEdiTxtNumSer);
            objTblCelEdiTxtNumSer.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strTblNumSerFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strTblNumSerFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_SER)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_SER).toString();
                    if(!strTblNumSerFacPrv.equals("")){
                        strNumSerFacPrv=strTblNumSerFacPrv;
                        objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                     }
                }
            });


            objTblCelEdiTxtNumAutSriFacPrv=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setCellEditor(objTblCelEdiTxtNumAutSriFacPrv);
            objTblCelEdiTxtNumAutSriFacPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strTblNumAutSriFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strTblNumAutSriFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT).toString();
                    if(!strTblNumAutSriFacPrv.equals("")){
                        strNumAutSriFacPrv=strTblNumAutSriFacPrv;
                        objTblModFacPrv.setValueAt(strNumAutSriFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT);
                    }
                }
            });


            objTblCelEdiTxtFecCad=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setCellEditor(objTblCelEdiTxtFecCad);
            objTblCelEdiTxtFecCad.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strTblNumFecCadFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strTblNumFecCadFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD).toString();
                    if(!strTblNumFecCadFacPrv.equals("")){
                        strNumFecCadFacPrv=strTblNumFecCadFacPrv;
                        objTblModFacPrv.setValueAt(strNumFecCadFacPrv, intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD);
                     }
                }
            });



















            objTblCelEdiTxtNumFac=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setCellEditor(objTblCelEdiTxtNumFac);
            objTblCelEdiTxtNumFac.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strNumFacPrvIng="";
                String strTblNumSerFacPrv="";
                String strTblNumAutSriFacPrv="";
                String strTblNumFecCadFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strNumFacPrvIng=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_FAC)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_NUM_FAC).toString();
                    if(!strNumFacPrvIng.equals("")){
                        if( (!strNumSerFacPrv.equals("")) && (!strNumAutSriFacPrv.equals(""))  && (!strNumFecCadFacPrv.equals("")) ){
                            objTblModFacPrv.setValueAt(strNumSerFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_SER);
                            objTblModFacPrv.setValueAt(strNumAutSriFacPrv, intFil, INT_TBL_FAC_PRV_DAT_NUM_AUT);
                            objTblModFacPrv.setValueAt(strNumFecCadFacPrv, intFil, INT_TBL_FAC_PRV_DAT_FEC_CAD);
                        }
                    }
                }
            });




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



                if(strNecPerAutPag.equals("S")){
                    chkNecMarLis.setVisible(true);
                    chkEmiChqAntRecFacPrv.setVisible(false);
                }
                else{
                    chkEmiChqAntRecFacPrv.setVisible(true);
                    chkNecMarLis.setVisible(false);
                }



                if( (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')){

                    if(strNecPerAutPag.equals("S")){
                        if(chkNecMarLis.isSelected()){
                            mostrarMsgInf("<HTML>El documento no puede ser modificado.<BR>Este documento ya fue seleccionado como listo para autorizar pago y banco.</HTML>");
                            objAsiDia.setDiarioModificado(false);
                            blnHayCam=false;
                            objTooBar.setEstado('w');
                            cargarReg();
                            strFecDocIni=dtpFecDoc.getText();
                        }
                        else{
                            regenerarFormaPago();
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


                if(strNecPerAutPag.equals("S")){
                    chkNecMarLis.setVisible(true);
                    chkEmiChqAntRecFacPrv.setVisible(false);
                }
                else{
                    chkEmiChqAntRecFacPrv.setVisible(true);
                    chkNecMarLis.setVisible(false);
                }

                if( (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')){
                    //regenerarFormaPago();

                    if(strNecPerAutPag.equals("S")){
                        if(chkNecMarLis.isSelected()){
                            mostrarMsgInf("<HTML>El documento no puede ser modificado.<BR>Este documento ya fue seleccionado como listo para autorizar pago y banco.</HTML>");
                            objAsiDia.setDiarioModificado(false);
                            blnHayCam=false;
                            objTooBar.setEstado('w');
                            cargarReg();
                            strFecDocIni=dtpFecDoc.getText();
                        }
                        else{
                            regenerarFormaPago();
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

                if(strNecPerAutPag.equals("S")){
                    chkNecMarLis.setVisible(true);
                    chkEmiChqAntRecFacPrv.setVisible(false);
                }
                else{
                    chkEmiChqAntRecFacPrv.setVisible(true);
                    chkNecMarLis.setVisible(false);
                }

                if( (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')){
                    //regenerarFormaPago();

                    if(strNecPerAutPag.equals("S")){
                        if(chkNecMarLis.isSelected()){
                            mostrarMsgInf("<HTML>El documento no puede ser modificado.<BR>Este documento ya fue seleccionado como listo para autorizar pago y banco.</HTML>");
                            objAsiDia.setDiarioModificado(false);
                            blnHayCam=false;
                            objTooBar.setEstado('w');
                            cargarReg();
                            strFecDocIni=dtpFecDoc.getText();
                        }
                        else{
                            regenerarFormaPago();
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
                txtNumCot.setEditable(false);
                txtRucPrv.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                configurarVenConTipDoc();
                objAsiDia.setEditable(true);
                txtCodPrv.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;

                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblModMot.setModoOperacion(objTblModMot.INT_TBL_INS);

                if( strNecPerAutPag.equals("S") ){
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_NO_EDI);
                }
                else{
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                }



                intNumRegFacPrv=0;
                intNumRegMotDoc=0;

                txtCodCom.setText(""+objParSis.getCodigoUsuario());
                mostrarVenConCom(3);


                strNumSerFacPrv="";
                strNumAutSriFacPrv="";
                strNumFecCadFacPrv="";



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
            if(strNecPerAutPag.equals("S")){
                if(chkNecMarLis.isSelected()){
                    mostrarMsgInf("<HTML>El documento no puede ser modificado.<BR>Este documento ya fue seleccionado como listo para autorizar pago y banco.</HTML>");
                    objAsiDia.setDiarioModificado(false);
                    blnHayCam=false;
                    objTooBar.setEstado('w');
                    cargarReg();
                    strFecDocIni=dtpFecDoc.getText();
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_NO_EDI);
                }
            }
            else{
                txtDesCorTipDoc.setEditable(false);
                txtDesLarTipDoc.setEditable(false);
                butTipDoc.setEnabled(false);
                txtCodPrv.setEditable(false);
                txtDesLarPrv.setEditable(false);
                butPrv.setEnabled(false);
                txtCodDoc.setEditable(false);
                txtNumCot.setEditable(false);
                txtCodBen.setEditable(false);
                txtNomBen.setEditable(false);
                butBen.setEnabled(false);
                txtCodCom.setEditable(false);
                txtAliCom.setEditable(false);
                txtNomCom.setEditable(false);
                butCom.setEnabled(false);
                txtNumDoc.setEditable(false);
                txtCnfNumRuc.setEditable(false);
                objAsiDia.setEditable(true);
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
                cargarReg();
                if(camposInactivosPermisoModifi()){
                    objTblModMot.setModoOperacion(objTblModMot.INT_TBL_EDI);
                    objTblModMot.removeEmptyRows();
                    objTblModForPag.removeAllRows();

                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                    objTblModFacPrv.removeEmptyRows();

                }
                regenerarFormaPago();

                objTblModMot.setModoOperacion(objTblModMot.INT_TBL_EDI);
                objTblModMot.removeEmptyRows();
                objTblModForPag.removeAllRows();

                objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                objTblModFacPrv.removeEmptyRows();
                if(objTblModFacPrv.getRowCountTrue()>0){
                    cargarValorIvaFacturaProveedor();

                    if(objTblModMot.getRowCountTrue()>1){
                        cargarFormaPago();
                        colocaNumRegFacPrv_NumRegForPag();
                    }
                    else{
                        cargarFormaPagoFacturaProveedor();
                    }
                }
                else{
                    cargarFormaPago();
                }
                objTblModMot.setModoOperacion(objTblModMot.INT_TBL_INS);
                objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
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

                if(strNecPerAutPag.equals("S")){
                    chkNecMarLis.setVisible(true);
                    chkEmiChqAntRecFacPrv.setVisible(false);
                }
                else{
                    chkEmiChqAntRecFacPrv.setVisible(true);
                    chkNecMarLis.setVisible(false);
                }

                if( (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')){
                    //regenerarFormaPago();

                    if(strNecPerAutPag.equals("S")){
                        if(chkNecMarLis.isSelected()){
                            mostrarMsgInf("<HTML>El documento no puede ser modificado.<BR>Este documento ya fue seleccionado como listo para autorizar pago y banco.</HTML>");
                            objAsiDia.setDiarioModificado(false);
                            blnHayCam=false;
                            objTooBar.setEstado('w');
                            cargarReg();
                            strFecDocIni=dtpFecDoc.getText();
                        }
                        else{
                            regenerarFormaPago();
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
            
            if(existeDocumentoAsociado()){
                mostrarMsgInf("El documento tiene pagos asociados.\nNo es posible modificar un documento con pagos asociados.");
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

            if(existeDocumentoAsociado()){
                mostrarMsgInf("El documento tiene pagos asociados.\nNo es posible modificar un documento con pagos asociados.");
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

            if(existeDocumentoAsociado()){
                mostrarMsgInf("El documento tiene pagos asociados.\nNo es posible modificar un documento con pagos asociados.");
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
            objTooBar.setEstado('w');
            cargarReg();
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
        int intTipCamFec, intAux, i;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        String strMsg="", strNumFac, strNumSer, strAux1, strAux2;
        BigDecimal bdeMontoDebe, bdeSubGen, bdeIvaGen, bdeSubFacPrv, bdeIvaFacPrv;
        boolean blnFound;

        try
        {
            blnIsCos_Ecu_Det = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") != -1
               || objParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") != -1
               || objParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") != -1)? true: false;
            
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
            //Validar el "Beneficiario".
            if (txtCodBen.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Beneficiario</FONT> es obligatorio.<BR>Escriba o seleccione un beneficiario y vuelva a intentarlo.</HTML>");
                txtCodBen.requestFocus();
                return false;
            }
            //Validar el "Comprador".
            if (txtAliCom.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Comprador</FONT> es obligatorio.<BR>Escriba o seleccione un comprador y vuelva a intentarlo.</HTML>");
                txtAliCom.requestFocus();
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

            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (con != null)
            {  //Se va a validar que entre la fecha actual y la fecha de emision del Documento por pagar haya transcurrido como maximo 60 dias.
               stm = con.createStatement();
               strAux1 = dtpFecDoc.getText(); //dtpFecDoc va a estar en formato "dd/MM/yyyy"
               strAux1 = strAux1.substring(6, 10) + "/" + strAux1.substring(3, 5) + "/" + strAux1.substring(0, 2); //strAux1 va a estar en formato "yyyy/MM/dd"
               strSQL = "select (current_date - '" + strAux1 + "') as ne_num_dia_ant";
               rst = stm.executeQuery(strSQL);

               if (rst.next())
               {  strAux1 = rst.getString("ne_num_dia_ant") == null? "" :rst.getString("ne_num_dia_ant");
                  if (!strAux1.equals(""))
                  {  intAux = Integer.parseInt(strAux1);
                     if (intAux > 60)
                     {  //Como entre la fecha actual y la fecha de emision del Documento por pagar ha transcurrido mas de 60 dias, no se debe 
                        //permitir guardar el documento.
                        tabFrm.setSelectedIndex(0);
                        strAux1 =  "<HTML>La fecha de emisión del Documento por pagar tiene una antiguedad de mas de 60 días.<BR><BR>";
                        strAux1 += "Verifique esto y vuelva a intentarlo.</HTML>";
                        mostrarMsgInf(strAux1);
                        dtpFecDoc.requestFocus();
                        return false;
                     }
                  }
               }
               
               rst.close();
               rst = null;
               stm.close();
               stm = null;
            }
            
            con.close();
            con = null;

            //Validar que el "Código alterno" no se repita.
            if (!txtNumDoc.getText().equals(""))
            {
                strSQL="";
                strSQL+="SELECT a1.ne_numdoc";
                strSQL+=" FROM tbm_cabMovInv AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.ne_numdoc='" + txtNumDoc.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                if (objTooBar.getEstado()=='m')
                    strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
                if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El número de documento <FONT COLOR=\"blue\">" + txtNumDoc.getText() + "</FONT> ya existe.<BR>Escriba otro número de documento y vuelva a intentarlo.</HTML>");
                    txtNumDoc.selectAll();
                    txtNumDoc.requestFocus();
                    return false;
                }
            }

            objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
            objTblModFacPrv.removeEmptyRows();

            String strLngNumAutSri="";
            String strLngFecCad="";
            i = 0;
            blnDetDocTieIva = false;
            
            while (i < objTblMod.getRowCountTrue() && blnDetDocTieIva == false)
            {  if (objTblMod.isChecked(i, INT_TBL_GRL_DAT_EST_IVA))
               {  blnDetDocTieIva = true;
               }
               i++;
            }
            
            bdeSubGen = new BigDecimal(txtSub.getText());
            bdeSubGen = objUti.redondearBigDecimal(bdeSubGen, objParSis.getDecimalesMostrar());
            bdeIvaGen = new BigDecimal(txtIva.getText());
            bdeIvaGen = objUti.redondearBigDecimal(bdeIvaGen, objParSis.getDecimalesMostrar());

            for(i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                if (!objTblModFacPrv.isRowComplete(i)){
                    mostrarMsgInf("<HTML>El documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                    tblFacPrv.setRowSelectionInterval(0, 0);
                    tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_VAL_FAC, true, true);
                    tblFacPrv.requestFocus();
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                    return false;
                }
                else{
                    //se valida si debe llevar FECHA CADUCIDAD
                    strLngNumAutSri=objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_AUT)==null?"":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_AUT).toString();
                    strLngFecCad=objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_CAD)==null?"":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_CAD).toString();
                    if( (strLngNumAutSri.length()==37) &&  (strLngFecCad.length()!=0) ){
                        mostrarMsgInf("<HTML>El campo fecha de caducidad no debe ser ingresado.<BR>Elimine el dato ingresado y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_FEC_CAD, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        return false;
                    }
                    else if(   (strLngNumAutSri.length()==10)  &&  (strLngFecCad.length()==0)  ){
                        mostrarMsgInf("<HTML>El campo fecha de caducidad es obligatorio.<BR>Ingrese el dato fecha de caducidad y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_FEC_CAD, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        return false;
                    }
                    else if( (strLngNumAutSri.length()!=10) && (strLngNumAutSri.length()!=37) && (strLngNumAutSri.length()!=49) ){
                        mostrarMsgInf("<HTML>El campo número de autorización debe tener 10, 37 ó 49 dígitos.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_AUT, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        return false;
                    }

                    strNumFac = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC).toString();

                    if (strNumFac.length() != 9)
                    {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Fac.</FONT> debe tener 9 digitos.<BR>Verifique y vuelva a intentarlo.</HTML>");
                       tblFacPrv.setRowSelectionInterval(0, 0);
                       tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_FAC, true, true);
                       tblFacPrv.requestFocus();
                       objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                       return false;
                    }

                    if (objUti.isNumero(strNumFac) == false)
                    {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Fac.</FONT> debe ser numérico.<BR>Verifique y vuelva a intentarlo.</HTML>");
                       tblFacPrv.setRowSelectionInterval(0, 0);
                       tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_FAC, true, true);
                       tblFacPrv.requestFocus();
                       objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                       return false;
                    }

                    //Rose
                    System.out.println("Antes>>> " + strNumFac);
                    String strFac = eliminaCeros(strNumFac);
                    System.out.println("Despues>>> " + strFac);
                    String strSerFac= objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER).toString().trim().replace("-", "");
                    
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') 
                    {   //n = Nuevo; m = Modificar.
                        if (facturaProveedorExisteSistema(strFac, strSerFac, strLngNumAutSri))
                        {
                            return false;
                        }
                    }
                    
                    strNumSer = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER).toString();

                    if (strNumSer.length() != 7)
                    {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Ser.</FONT> debe tener 7 caracteres con formato 999-999 (números separados con guion).<BR>Verifique y vuelva a intentarlo.</HTML>");
                       tblFacPrv.setRowSelectionInterval(0, 0);
                       tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_SER, true, true);
                       tblFacPrv.requestFocus();
                       objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                       return false;
                    }

                    strAux1 = strNumSer.substring(0, 3); // Si la serie es 001-002, va a extraer "001"
                    strAux2 = strNumSer.substring(4, 7); // Si la serie es 001-002, va a extraer "002"
                    blnFound = true;

                    if (strNumSer.lastIndexOf("-") == -1)
                       blnFound = false;

                    if (objUti.isNumero(strAux1) == false || objUti.isNumero(strAux2) == false || blnFound == false)
                    {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Ser.</FONT> debe ser numérico con formato 999-999 (números separados con guion).<BR>Verifique y vuelva a intentarlo.</HTML>");
                       tblFacPrv.setRowSelectionInterval(0, 0);
                       tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_SER, true, true);
                       tblFacPrv.requestFocus();
                       objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                       return false;
                    }
                    
                    bdeSubFacPrv = new BigDecimal("0.00");
                    strAux = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_SUB) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_SUB).toString();
                    
                    if (objUti.isNumero(strAux) == true)
                    {  bdeSubFacPrv = new BigDecimal(strAux);
                       bdeSubFacPrv = objUti.redondearBigDecimal(bdeSubFacPrv, objParSis.getDecimalesMostrar());
                    }
                    
                    bdeIvaFacPrv = new BigDecimal("0.00");
                    strAux = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA).toString();
                    
                    if (objUti.isNumero(strAux) == true)
                    {  bdeIvaFacPrv = new BigDecimal(strAux);
                       bdeIvaFacPrv = objUti.redondearBigDecimal(bdeIvaFacPrv, objParSis.getDecimalesMostrar());
                    }
                    
                    if (!bdeSubGen.equals(bdeSubFacPrv))
                    {  strAux =  "<HTML>El valor del campo <FONT COLOR=\"blue\">Subtotal de la pestaña 'General'</FONT><BR>";
                       strAux += "debe ser igual al valor de la columna <FONT COLOR=\"blue\">Sub.Fac.Prv. de la pestaña 'Facturas'</FONT>.<BR><BR>";
                       strAux += "Verifique y vuelva a intentarlo.</HTML>";
                       mostrarMsgInf(strAux);
                       tblFacPrv.setRowSelectionInterval(0, 0);
                       tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_VAL_FAC, true, true);
                       tblFacPrv.requestFocus();
                       objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                       return false;
                    }
                    
                    if (!bdeIvaGen.equals(bdeIvaFacPrv))
                    {  strAux =  "<HTML>El valor del campo <FONT COLOR=\"blue\">IVA de la pestaña 'General'</FONT><BR>";
                       strAux += "debe ser igual al valor de la columna <FONT COLOR=\"blue\">Iva.Fac.Prv. de la pestaña 'Facturas'</FONT>.<BR><BR>";
                       strAux += "Verifique y vuelva a intentarlo.</HTML>";
                       mostrarMsgInf(strAux);
                       tblFacPrv.setRowSelectionInterval(0, 0);
                       tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_VAL_FAC, true, true);
                       tblFacPrv.requestFocus();
                       objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                       return false;
                    }
                }
            }

            objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);

            //Validar que el "Diario esté cuadrado".
            if (!objAsiDia.isDiarioCuadrado())
            {
                mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                return false;
            }

            if(isEnabledValorDiarioDoble()){
                BigDecimal bdeValDia=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValDoc=new BigDecimal(BigInteger.ZERO);

                if(objAsiDia.isDiarioCuadrado()){
                    bdeValDia=objUti.redondearBigDecimal(new BigDecimal(objAsiDia.getMontoDebe()), objParSis.getDecimalesMostrar());
                    bdeValDoc=new BigDecimal(txtTot.getText());
                }

                if(bdeValDia.compareTo(bdeValDoc)<0){
                    mostrarMsgInf("<HTML>El asiento de diario tiene un valor menor al valor del documento<BR>Verifique el valor del asiento de diario y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            else{
                //Validar que el "Monto del diario" sea igual al monto del documento.
                if (!objAsiDia.isDocumentoCuadrado(txtTot.getText())){
                    mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
                    txtTot.selectAll();
                    txtTot.requestFocus();
                    return false;
                }
            }


            if(strNecPerAutPag.equals("S")  ){

            }
            else{
                if(objTblModFacPrv.getRowCountTrue()>0){
                    //Valida que la suma de las facturas del proveedor sea igual al valor total del documento
                    BigDecimal bdeValFac=new BigDecimal(BigInteger.ZERO);
                    BigDecimal bdeSumValFac=new BigDecimal(BigInteger.ZERO);
                    BigDecimal bdeTot=new BigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()));


                    for(i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                        bdeValFac=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString())));
                        bdeSumValFac=bdeSumValFac.add(bdeValFac);
                    }

                    if(bdeTot.compareTo(bdeSumValFac)!=0){
                        mostrarMsgInf("<HTML>El valor del documento no coincide con el valor de las facturas del proveedor.<BR>Cuadre el valor del documento con el valor de las facturas y vuelva a intentarlo.</HTML>");
                        return false;
                    }
                }
            }


            if(objTblModFacPrv.getRowCountTrue()>0){
                //Valida que la suma de las facturas del proveedor sea igual al valor total del documento
                BigDecimal bdeValFac=new BigDecimal(BigInteger.ZERO);

                for(i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                    bdeValFac=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString())));
                    if(bdeValFac.compareTo(new BigDecimal(BigInteger.ZERO))==0){
                        mostrarMsgInf("<HTML>El documento tiene facturas de proveedor sin valor.<BR>Coloque el valor de la factura del proveedor o elimine la fila y vuelva a intentarlo.</HTML>");
                        return false;
                    }
                }
            }

            //para que no se reciban facturas de proveedor si la fecha excede el numero de dias permitido
            //por ejemplo en Tuval se va poder recibir FP del mes de enero hasta el 15 Feb maximo.

            if(objTblModFacPrv.getRowCountTrue()>0){
                if(!permiteRecibirFacPrvFecha()){
                    mostrarMsgInf("<HTML>La factura del proveedor ingresada está fuera del rango de fecha permitido para ser ingresada.<BR>Se debe solicitar al proveedor el cambio de dicha factura con una fecha actualizada.</HTML>");
                    return false;
                }

            }

            if(objTooBar.getEstado()=='n'){
    //            if(objTblModFacPrv.getRowCountTrue()>0){
    //                if(facturaProveedorExisteSistema()){
    //                    mostrarMsgInf("<HTML>La factura del proveedor ya fue ingresada.<BR>Verifique los datos de la factura del proveedor y vuelva a intentarlo.</HTML>");
    //                    return false;
    //                }
    //            }

                if( (txtCnfNumRuc.getText().equals(""))  ){
                    mostrarMsgInf("<HTML>El número de RUC de confirmación no ha sido ingresado<BR>Ingrese el número de RUC de confirmación y vuelva a intentarlo.</HTML>");
                    return false;
                }

                if( ! (txtRucPrv.getText().equals(txtCnfNumRuc.getText()))  ){
                    mostrarMsgInf("<HTML>El número de RUC existente es diferente al número de RUC de confirmación<BR>Verifique los datos del proveedor y vuelva a intentarlo.</HTML>");
                    return false;
                }

            }

            if( (objTooBar.getEstado()=='x')  || (objTooBar.getEstado()=='m') ){
                if(isRegistroCambio()){//valida si el registro ha cambiado mientras estaba en memoria(otro usuario lo modifico desde otro prog.)
                    mostrarMsgInf("<HTML>El documento fue modificado mientras ud. lo tenia cargado en memoria.<BR>El documento no podrá ser modificado, debe cargar el documento nuevamente para poder modificar.</HTML>");
                    return false;
                }
            }

            System.out.println("txtTot: " + txtTot.getText());
            System.out.println("objAsiDia: " + objAsiDia.getMontoDebeBde());
            //validador
            //if(new BigDecimal(txtTot.getText()).compareTo(objAsiDia.getMontoDebeBde())!=0 ){
            if (! txtCodTipDoc.getText().equals("179")) //179: OPSLC
            {  bdeMontoDebe = objUti.redondearBigDecimal(objAsiDia.getMontoDebeBde(), objParSis.getDecimalesMostrar());
               if (new BigDecimal(txtTot.getText()).compareTo(bdeMontoDebe)!=0 ){
                  strMsg = "<HTML>El campo <FONT COLOR=\"blue\">Total 1</FONT> del documento es diferente al valor del asiento de diario.<BR>";
                  strMsg += "Verifique los valores ingresados en el documento y vuelva a intentarlo.</HTML>";
                  mostrarMsgInf(strMsg);
                  txtTot.selectAll();
                  txtTot.requestFocus();
                  return false;
               }
            }

            if(txtCodForPag.getText().equals("")){
               strMsg = "<HTML>El documento no tiene asociado una forma de pago.<BR>";
               strMsg += "Verifique y seleccione la forma de pago y vuelva a intentarlo.</HTML>";
               mostrarMsgInf(strMsg);
               txtNomForPag.selectAll();
               txtNomForPag.requestFocus();
               return false;
            }

            BigDecimal bdeValForPag=new BigDecimal("0");
            BigDecimal bdeTotForPag=new BigDecimal("0");
            for(i=0; i<objTblModForPag.getRowCountTrue(); i++){
                bdeValForPag=objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_VAL_RET).toString(), objParSis.getDecimalesMostrar());
                bdeTotForPag=bdeTotForPag.add(bdeValForPag);
            }

    //        Double dblValForPag=0.00;
    //        Double dblTotForPag=0.00;
    //        for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
    //            dblValForPag=objUti.redondear(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_VAL_RET).toString(), objParSis.getDecimalesMostrar());
    //            dblTotForPag=dblTotForPag+dblValForPag;
    //        }


            if (! txtCodTipDoc.getText().equals("179")) //179: OPSLC
            {  bdeTotForPag = objUti.redondearBigDecimal(bdeTotForPag, objParSis.getDecimalesMostrar());
               if (new BigDecimal(txtTot.getText()).compareTo(bdeTotForPag)!=0 ){
               //if(Double.parseDouble(txtTot.getText()) !=   dblTotForPag ){
                  strMsg = "<HTML>El campo <FONT COLOR=\"blue\">Total 2</FONT> del documento es diferente al valor del asiento de diario.<BR>";
                  strMsg += "Verifique los valores ingresados en el documento y vuelva a intentarlo.</HTML>";
                  mostrarMsgInf(strMsg);
                  txtTot.selectAll();
                  txtTot.requestFocus();
                  return false;
               }
            }
        } //try
        
        catch(Exception Evt)
        {  
           return false;
        }

        return true;
    } //Funcion isCamVal()

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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes = false;
        boolean blnVrfExiValCrr_PagMovInv; //Verifica si existen Valores Correctos en tbm_pagmovinv.
        String strAux;
        
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);

            if (con!=null){
                if (insertarCabMovInv()){
                    if (insertarDetConIntMovInv()){
                        if (insertarPagMovInv()){
                            if (insertarRetMovInv()){
                                if (insertarCabRecDoc()){//solo si existe factura de proveedor
                                    if (insertarDetRecDoc()){//inserta en tbr_detRecDocCabMovInv....solo si existe factura de proveedor, tb
                                        if (actualizarCli()){//actualiza tbm_cli
                                            if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))
                                            {  blnVrfExiValCrr_PagMovInv = verificarExistenValoresCorrectos_PagMovInv(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                                               
                                               if (blnVrfExiValCrr_PagMovInv == true)
                                               {  
                                                  con.commit();
                                                  blnRes=true;
                                               }
                                               else
                                               {  con.rollback();
                                                  tabFrm.setSelectedIndex(0);
                                                  strAux =  "<HTML>El Documento por pagar no se pudo grabar debido a que<BR>";
                                                  strAux += "se encontró algún valor erróneo en tbm_pagMovInv.<BR><BR>";
                                                  strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                                                  mostrarMsgInf(strAux);
                                                  blnRes = false;
                                               } //if (blnVrfExiValCrr_PagMovInv == true)
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
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_EDI);


            strNumSerFacPrv="";
            strNumAutSriFacPrv="";
            strNumFecCadFacPrv="";



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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(Connection conexion){
        boolean blnRes = false, blnVrfExiCtaIvaAsiDia;
        try{
            con=conexion;
            if (con!=null){
                if (insertarCabMovInv()){
                    if (insertarDetConIntMovInv()){
                        if (insertarPagMovInv()){
                            if (insertarRetMovInv()){
                                if (insertarCabRecDoc()){//solo si existe factura de proveedor
                                    if (insertarDetRecDoc()){//inserta en tbr_detRecDocCabMovInv....solo si existe factura de proveedor, tb
                                        if (actualizarCli()){//actualiza tbm_cli
                                            if (actualizarCabMovInv_DocLisAut()){//actualiza tbm_cli
                                                if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))
                                                {  con.commit();
                                                   blnRes=true;
                                                }
                                                else
                                                    blnRes=false;
                                            }
                                            else
                                                blnRes=false;
                                        }
                                        else
                                            blnRes=false;
                                    }
                                    else
                                        blnRes=false;
                                }
                                else
                                    blnRes=false;
                            }
                            else
                                blnRes=false;
                        }
                        else
                            blnRes=false;
                    }
                    else
                        blnRes=false;
                }
                else
                    blnRes=false;
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_EDI);

            strNumSerFacPrv="";
            strNumAutSriFacPrv="";
            strNumFecCadFacPrv="";
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
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals("")){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" , a1.co_usrIng, b5.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                    strSQL+=" FROM (          (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS b5 ON a1.co_usrIng=b5.co_usr)";
                    strSQL+="                  LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                    strSQL+="      )";

                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" , a1.co_usrIng, a5.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                    strSQL+=" FROM (          (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_usrIng=a5.co_usr)";
                    strSQL+="                  LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                    strSQL+="      )";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
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
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc =" + strAux + "";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.fe_doc, a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes = false, blnVrfExiCtaIvaAsiDia;
        boolean blnVrfExiCtaIvaCrrAsiDia; //Verifica si existe una cuenta de IVA Correcta en el asiento de diario.
        boolean blnVrfExiValCrr_PagMovInv; //Verifica si existen Valores Correctos en tbm_pagmovinv.
        
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarCabMovInv()){
                    if(insertarDetConIntMovInv()){
                        if (actualizarPagMovInv()){
                            if (actualizarRetMovInv()){
                                if (actualizarCabRecDoc()){
                                    if (actualizarDetRecDoc()){
                                        if(actualizarCli()){
                                            if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                            {  blnVrfExiValCrr_PagMovInv = verificarExistenValoresCorrectos_PagMovInv(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                                               
                                               if (blnVrfExiValCrr_PagMovInv == true)
                                               {  if (blnIsCos_Ecu_Det == false)
                                                  {  blnVrfExiCtaIvaAsiDia = verificarExisteCtaIvaAsientoDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText());

                                                     if ( (blnDetDocTieIva == false && blnVrfExiCtaIvaAsiDia == false) ||
                                                          (blnDetDocTieIva == true && blnVrfExiCtaIvaAsiDia == true) )
                                                     {  blnVrfExiCtaIvaCrrAsiDia = verificarExisteCtaIvaCorrectaAsientoDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText());
                                                        
                                                        if ( blnDetDocTieIva == false || (blnDetDocTieIva == true && blnVrfExiCtaIvaCrrAsiDia == true) )
                                                        {  
                                                           con.commit();
                                                           blnRes=true;
                                                        }
                                                        else
                                                        {  con.rollback();
                                                           tabFrm.setSelectedIndex(0);
                                                           strAux =  "<HTML>El Documento por pagar no se pudo grabar debido a que<BR>";
                                                           strAux += "en el asiento de diario se encontró una cuenta de IVA errónea.<BR><BR>";
                                                           strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                                                           mostrarMsgInf(strAux);
                                                           blnRes = false;
                                                        }
                                                     }
                                                     else
                                                     {  con.rollback();
                                                        tabFrm.setSelectedIndex(0);
                                                        strAux =  "<HTML>El Documento por pagar no se pudo grabar por alguno de los siguientes motivos:<BR><BR>";
                                                        strAux += "1) El Documento por pagar no tiene ningún item con IVA y en el asiento de diario se encontró una cuenta de IVA.<BR>";
                                                        strAux += "2) El Documento por pagar tiene algún item con IVA y en el asiento de diario no se encontró una cuenta de IVA.<BR><BR>";
                                                        strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                                                        mostrarMsgInf(strAux);
                                                        blnRes = false;
                                                     }
                                                  }
                                                  else
                                                  {  con.commit();
                                                     blnRes=true;
                                                  } //if (blnIsCos_Ecu_Det == false)
                                               }
                                               else
                                               {  con.rollback();
                                                  tabFrm.setSelectedIndex(0);
                                                  strAux =  "<HTML>El Documento por pagar no se pudo grabar debido a que<BR>";
                                                  strAux += "se encontró algún valor erróneo en tbm_pagMovInv.<BR><BR>";
                                                  strAux += "Verifique esto y vuelva a intentarlo.</HTML>";
                                                  mostrarMsgInf(strAux);
                                                  blnRes = false;
                                               } //if (blnVrfExiValCrr_PagMovInv == true)
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
    
    public boolean verificarExisteCtaIvaAsientoDiario(Connection conexion, int intCodEmp, int intCodLoc, int intCodTipDoc, String strNumDia)
    {
      boolean blnRes = false;
      
      try
      {
         stm = conexion.createStatement();
         
         strSQL =  "select count(a.*) as cont_reg ";
         strSQL += "from tbm_detdia as a ";
         strSQL += "inner join ( select co_emp, co_loc, co_tipdoc, co_dia, tx_numdia ";
         strSQL += "             from tbm_cabdia ";
         strSQL += "             where st_reg = 'A' ";
         strSQL += "                and co_emp = " + intCodEmp;
         strSQL += "                and co_loc = " + intCodLoc;
         strSQL += "                and co_tipdoc = " + intCodTipDoc;
         strSQL += "                and tx_numdia = '" + strNumDia + "' ";
         strSQL += "           ) as b on a.co_emp = b.co_emp and a.co_loc = b.co_loc and a.co_tipdoc = b.co_tipdoc and a.co_dia = b.co_dia " + "\n";
         strSQL += "where a.co_cta in ( select co_cta from tbm_placta where st_reg = 'A' and co_emp = a.co_emp and tx_codcta in (";
         strSQL += "                       '1.01.07.03.02',"; //IVA BIENES Y SERVICIOS 12%
         strSQL += "                       '1.01.07.03.11',"; //IVA BIENES Y SERVICIOS 14%
         strSQL += "                       '1.01.07.03.01',"; //12% IVA COMPRAS
         strSQL += "                       '1.01.07.03.12',"; //14% IVA COMPRAS
         strSQL += "                       '2.01.06.02.04',"; //I.V.A. COBRADO EN VENTAS 12%
         strSQL += "                       '2.01.06.02.20'"; //14 % I.V.A. COBRADO EN VENTAS
         strSQL += "                  ) )";
         rst = stm.executeQuery(strSQL);
         
         if (rst.next())
         {  if (rst.getInt("cont_reg") != 0)
            {  //Si es <> 0, significa que se encontro alguna Cta.Iva en la tabla tbm_detdia
               blnRes = true;
            }
            else
               blnRes = false;
         }
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
    } //Funcion verificarExisteCtaIvaAsientoDiario()
    
    public boolean verificarExisteCtaIvaCorrectaAsientoDiario(Connection conexion, int intCodEmp, int intCodLoc, int intCodTipDoc, String strNumDia)
    {
      boolean blnRes = false;
      String strFecDoc;
      BigDecimal bdePorIvaCom;
      java.util.Date datFecDoc;
      
      try
      {
         stm = conexion.createStatement();
         
         strFecDoc = dtpFecDoc.getText(); //dtpFecDoc va a estar en formato "dd/MM/yyyy"
         strFecDoc = strFecDoc.substring(6, 10) + "/" + strFecDoc.substring(3, 5) + "/" + strFecDoc.substring(0, 2); //strFecDoc va a estar en formato "yyyy/MM/dd"
         datFecDoc = objUti.parseDate(strFecDoc, "yyyy/MM/dd");
         bdePorIvaCom = objParSis.getPorcentajeIvaCompras(intCodEmp, intCodLoc, datFecDoc);
         bdePorIvaCom = objUti.redondearBigDecimal(bdePorIvaCom, objParSis.getDecimalesMostrar());
         
         strSQL =  "select count(a.*) as cont_reg ";
         strSQL += "from tbm_detdia as a ";
         strSQL += "inner join ( select co_emp, co_loc, co_tipdoc, co_dia, tx_numdia ";
         strSQL += "             from tbm_cabdia ";
         strSQL += "             where st_reg = 'A' ";
         strSQL += "                and co_emp = " + intCodEmp;
         strSQL += "                and co_loc = " + intCodLoc;
         strSQL += "                and co_tipdoc = " + intCodTipDoc;
         strSQL += "                and tx_numdia = '" + strNumDia + "' ";
         strSQL += "           ) as b on a.co_emp = b.co_emp and a.co_loc = b.co_loc and a.co_tipdoc = b.co_tipdoc and a.co_dia = b.co_dia " + "\n";
         strSQL += "where a.co_cta in ( select co_cta from tbm_placta where st_reg = 'A' and co_emp = a.co_emp ";
         strSQL += "                        and tx_codcta in ( ";
         
         if (bdePorIvaCom.equals(new BigDecimal("12.00")))
         {  strSQL += "'1.01.07.03.02',"; //IVA BIENES Y SERVICIOS 12%
            strSQL += "'1.01.07.03.01'"; //12% IVA COMPRAS
         }
         else if (bdePorIvaCom.equals(new BigDecimal("14.00")))
         {  strSQL += "'1.01.07.03.11',"; //IVA BIENES Y SERVICIOS 14%
            strSQL += "'1.01.07.03.12'"; //14% IVA COMPRAS
         }
         else
         {  strSQL += "'0'";
         }
         
         strSQL += "                                         )";
         strSQL += "                   )";
         rst = stm.executeQuery(strSQL);
         
         if (rst.next())
         {  if (rst.getInt("cont_reg") != 0)
            {  //Si es <> 0, significa que se encontro alguna Cta.Iva correcta en la tabla tbm_detdia segun lo indicado por la variable bdePorIvaCom.
               blnRes = true;
            }
            else
            {  //Si es = 0, significa que se encontro alguna Cta.Iva erronea en la tabla tbm_detdia segun lo indicado por la variable bdePorIvaCom.
               blnRes = false;
            }
         }
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
    } //Funcion verificarExisteCtaIvaErroneaAsientoDiario()
    
    public boolean verificarExistenValoresCorrectos_PagMovInv(Connection conexion, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
      boolean blnRes = false;
      
      try
      {
         stm = conexion.createStatement();
         
         strSQL =  "SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_tot, b2.nd_pag, (b1.nd_tot-b2.nd_pag) AS nd_dif " + "\n";
         strSQL += "FROM  tbm_cabMovInv AS b1 " + "\n";
         strSQL += "INNER JOIN ( SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag " + "\n";
         strSQL += "             FROM ( SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag " + "\n";
         strSQL += "                    FROM tbm_cabMovInv " + "\n";
         strSQL += "                    WHERE co_emp IN (1,2,4) " + "\n";
         strSQL += "                    UNION ALL " + "\n";
         strSQL += "                    SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(mo_pag) AS nd_pag " + "\n";
         strSQL += "                    FROM tbm_pagMovInv " + "\n";
         strSQL += "                    WHERE co_emp IN (1,2,4) AND st_reg IN ('A','F') " + "\n";
         strSQL += "                    GROUP BY co_emp, co_loc, co_tipDoc, co_doc " + "\n";
         strSQL += "                  ) AS a1 " + "\n";
         strSQL += "             GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc " + "\n";
         strSQL += "           ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc) " + "\n";
         strSQL += "INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc) " + "\n";
         strSQL += "WHERE b1.nd_tot<>b2.nd_pag AND b3.st_genPag='S' AND b1.co_tipDoc NOT IN (124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 165, 166, 167, 168) " + "\n";
         //strSQL += " AND b1.fe_doc > '2017-01-01' ";
         strSQL += "   AND ( (b1.co_emp=2 and b1.co_loc=1) OR (b1.co_emp=2 and b1.co_loc=4) OR " + "\n";
         strSQL += "         (b1.co_emp=2 and b1.co_loc=6) OR (b1.co_emp=2 AND b1.co_loc=11) OR " + "\n";
         strSQL += "         (b1.co_emp=2 AND b1.co_loc=12) OR (b1.co_emp=2 and b1.co_loc=13) OR " + "\n";
         strSQL += "         (b1.co_emp=2 AND b1.co_loc=10) OR (b1.co_emp=2 and b1.co_loc=14) OR " + "\n";
         strSQL += "         (b1.co_emp=1 AND b1.co_loc=4) OR (b1.co_emp=1 AND b1.co_loc=12) OR " + "\n";
         strSQL += "         (b1.co_emp=4 AND b1.co_loc=3) OR (b1.co_emp=4 AND b1.co_loc=10) OR " + "\n";
         strSQL += "         (b1.co_emp=4 AND b1.co_loc=12) OR (b1.co_emp=4 AND b1.co_loc=13) ) " + "\n";
         strSQL += " AND b2.co_emp = " + intCodEmp + " and b2.co_loc = " + intCodLoc + " and b2.co_tipdoc = " + intCodTipDoc + " and b2.co_doc = " + intCodDoc;
         //strSQL += " ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
         rst = stm.executeQuery(strSQL);
         
         if (rst.next())
         {  //Si trae algun resultado, significa que existen valores erroneos en tbm_PagMovInv.
            blnRes = false;
         }
         else
         {  //Si no trae ningun resultado, significa que los valores estan correctos en tbm_PagMovInv.
            blnRes = true;
         }
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
    } //Funcion verificarExistenValoresCorrectos_PagMovInv()

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabMovInv(){
        int intUltReg;
        BigDecimal bdePorIva;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                bdePorIva = objParSis.getPorcentajeIvaCompras();
                //Obtener el cádigo del áltimo registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
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
                strSQL+="INSERT INTO tbm_cabmovinv(";
                strSQL+="               co_emp,co_loc,co_tipDoc,co_doc,ne_secGrp,ne_secEmp,ne_numCot,ne_numDoc,";
                strSQL+="               tx_numPed,ne_numGui,co_dia,fe_doc,fe_ven,co_cli,tx_ruc,tx_nomCli,tx_dirCli,";
                strSQL+="               tx_telCli,tx_ciuCli,co_com,tx_nomVen,tx_ate,co_forPag,tx_desForPag,nd_sub,";
                strSQL+="               nd_porIva,nd_valIva,nd_tot,tx_ptoPar,tx_tra,co_motTra,tx_desMotTra,co_cta,";
                strSQL+="               co_motDoc,co_mnu,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,co_usrIng,";
                strSQL+="               co_usrMod,tx_comIng,tx_comMod,fe_con,tx_obs3,co_forRet,tx_vehRet,tx_choRet,";
                strSQL+="               ne_numVecRecDoc,fe_ultRecDoc,tx_obsSolAut,tx_obsAutSol,st_aut,ne_valAut,";
                strSQL+="               st_tipDev,st_conInv,st_regRep,ne_numDocRee,/*nd_tasInt,ne_tipCal,ne_uniTie,*/";
                strSQL+="               st_creGuiRem,st_conInvTraAut,co_locRelSolDevVen,co_tipDocRelSolDevVen,";
                strSQL+="               co_docRelSolDevVen,st_excDocConVenCon,fe_autExcDocConVenCon,";
                strSQL+="               co_usrAutExcDocConVenCon,tx_comAutExcDocConVenCon,co_ben,tx_benChq,";
                strSQL+="               st_docConMerSalDemDebFac,st_autAnu,fe_autAnu,co_usrAutAnu,tx_comAutAnu, st_emiChqAntRecFacPrv, st_docMarLis)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa() + "";//co_emp
                strSQL+="," + objParSis.getCodigoLocal() + "";//co_loc
                strSQL+="," + txtCodTipDoc.getText() + "";//co_tipdoc
                strSQL+="," + txtCodDoc.getText() + "";//co_doc
                strSQL+=",Null";//ne_secgrp
                strSQL+=",Null";//ne_secemp
                strSQL+=", Null";//ne_numcot
                strSQL+="," + txtNumDoc.getText() + "";//ne_numdoc
                strSQL+=",Null";//tx_numped
                strSQL+=", Null";//ne_numgui
                strSQL+=",Null";//co_dia
                strSQL+=",'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=",Null";//fe_ven
                strSQL+="," + txtCodPrv.getText() + "";//co_cli
                strSQL+="," + objUti.codificar(strIdePrv) + "";//tx_ruc
                strSQL+="," + objUti.codificar(txtDesLarPrv.getText()) + "";//tx_nomcli
                strSQL+="," + objUti.codificar(strDirPrv) + "";//tx_dircli
                strSQL+="," + objUti.codificar(strTelPrv) + "";//tx_telcli
                strSQL+="," + objUti.codificar(strCiuPrv) + "";//tx_ciucli
                strSQL+="," + txtCodCom.getText() + "";//co_com
                strSQL+="," + objUti.codificar(txtNomCom.getText()) + "";//tx_nomven
                strSQL+=",Null";//tx_ate
                strSQL+="," + txtCodForPag.getText() + "";//co_forpag
                strSQL+="," + objUti.codificar(txtNomForPag.getText()) + "";//tx_desforpag
                strSQL+="," + new BigDecimal(txtSub.getText()).multiply(new BigDecimal("" + intSig)) + "";//nd_sub
                
                if ( new BigDecimal(txtIva.getText()).compareTo(BigDecimal.ZERO)>0  )
                {  //strSQL+=", 12.00";//nd_poriva
                   strSQL+=", " + bdePorIva; //nd_poriva
                }
                else
                    strSQL+=", 00"; //nd_poriva
                
                strSQL+="," + new BigDecimal(txtIva.getText()).multiply(new BigDecimal("" + intSig)) + "";//nd_valiva
                strSQL+="," + new BigDecimal(txtTot.getText()).multiply(new BigDecimal("" + intSig)) + "";//nd_tot
                strSQL+=",Null";//tx_ptopar
                strSQL+=",Null";//tx_tra
                strSQL+=",Null";//co_mottra
                strSQL+=",Null";//tx_desmottra
                strSQL+=",Null";//co_cta
                strSQL+=",Null";//co_motdoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=",'N'";//st_imp
                strSQL+="," + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+="," + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=",'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=",'" + strAux + "'";//fe_ing
                strSQL+=",'" + strAux + "'";//fe_ultmod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_commod
                strSQL+=",Null";//fe_con
                strSQL+=",Null";//tx_obs3
                strSQL+=",Null";//co_forret
                strSQL+=",Null";//tx_vehret
                strSQL+=",Null";//tx_choret
                strSQL+=",0";//ne_numvecrecdoc
                strSQL+=",Null";//fe_ultrecdoc
                strSQL+=",Null";//tx_obssolaut
                strSQL+=",Null";//tx_obsautsol
                strSQL+=",Null";//st_aut
                strSQL+=",Null";//ne_valaut
                strSQL+=",'C'";//st_tipdev
                strSQL+=",'P'";//st_coninv
                strSQL+=",'I'";//st_regrep
                strSQL+=",Null";//ne_numdocree
                //strSQL+=",0";//nd_tasInt
                //strSQL+=",1";//ne_tipCal
                //strSQL+=",1";//ne_uniTie
                strSQL+=",'N'";//st_creguirem
                strSQL+=",'N'";//st_coninvtraaut
                strSQL+=",Null";//co_locrelsoldevven
                strSQL+=",Null";//co_tipdocrelsoldevven
                strSQL+=",Null";//co_docrelsoldevven
                strSQL+=",Null";//st_excdocconvencon
                strSQL+=",Null";//fe_autexcdocconvencon
                strSQL+=",Null";//co_usrautexcdocconvencon
                strSQL+=",Null";//tx_comautexcdocconvencon
                strSQL+="," + txtCodBen.getText() + "";//co_ben
                strSQL+="," + objUti.codificar(txtNomBen.getText()) + "";//tx_benchq
                strSQL+=",'N'";//st_docconmersaldemdebfac
                strSQL+=",Null";//st_autanu
                strSQL+=",Null";//fe_autanu
                strSQL+=",Null";//co_usrautanu
                strSQL+=",Null";//tx_comautanu
                if(chkEmiChqAntRecFacPrv.isSelected())
                    strSQL+=",'S'";//st_emiChqAntRecFacPrv
                else
                    strSQL+=",Null";//st_emiChqAntRecFacPrv

                if(chkNecMarLis.isSelected())
                    strSQL+=",'S'";//st_docMarLis
                else
                    strSQL+=",Null";//st_docMarLis

                strSQL+=");";

                System.out.println("INSERTAR CABECERA: " + strSQL);
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
     * Esta función permite actualizar el estado de listo para ser autorizado
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabMovInv_DocLisAut(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabmovinv";
                strSQL+=" SET st_docMarLis='S'";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";//co_loc
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";//co_tipdoc
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";//co_doc
                strSQL+=";";
                System.out.println("ACTUALIZAR CABECERA - ESTADO LISTO: " + strSQL);
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
    private boolean insertarDetConIntMovInv(){
        boolean blnRes=true;
        int j=1;
        String strSQLUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                objTblMod.removeEmptyRows();
                for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detConIntMovInv";
                    strSQL+="            (co_emp,co_loc,co_tipDoc,co_doc,co_reg,tx_des";
                    strSQL+="             ,nd_can,nd_preUni,st_iva,nd_tot,st_regRep)";
                    strSQL+=" SELECT ";
                    strSQL+="" + objParSis.getCodigoEmpresa() + " AS co_emp";//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal() + " AS co_loc";//co_loc
                    strSQL+=", " + txtCodTipDoc.getText() + " AS co_tipDoc";//co_tipDoc
                    strSQL+=", " + txtCodDoc.getText() + " AS co_doc";//co_doc
                    strSQL+=", " + j + " AS co_reg";//co_reg
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_DES)) + " AS tx_des";//tx_des
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CAN) + " AS nd_can";//nd_can
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_PRE) + " AS nd_preUni";//nd_preUni
                    if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_EST_IVA))
                        strSQL+=", 'S' AS st_iva";//st_iva
                    else
                        strSQL+=", 'N' AS st_iva";//st_iva
                    strSQL+=", " + intSig*Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_TOT), 3)) + " AS nd_tot";//nd_tot
                    strSQL+=", 'I' AS st_regRep";//st_regRep
                    strSQL+=" WHERE NOT EXISTS(";
                    strSQL+="                   SELECT *FROM tbm_detConIntMovInv ";
                    strSQL+="                   WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="                   AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="                   AND co_reg=" + j + ")";
                    strSQL+=";";

                    objTblMod.setValueAt(""+j, i, INT_TBL_GRL_DAT_REG);
                    j++;

                    strSQL+="UPDATE tbm_detConIntMovInv";
                    strSQL+="            SET tx_des=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_DES)) + "";//tx_des
                    strSQL+="             ,nd_can=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CAN) + "";//nd_can
                    strSQL+="             ,nd_preUni=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_PRE) + "";//nd_preUni
                    if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_EST_IVA))
                        strSQL+="             ,st_iva='S'";//st_iva
                    else
                        strSQL+="             ,st_iva='N'";//st_iva
                    strSQL+="             ,nd_tot=" + intSig*Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_TOT), 3)) + "";//nd_tot
                    strSQL+="             ,st_regRep='M'";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_REG) + "";

                    strSQL+=" AND EXISTS( SELECT *FROM tbm_detConIntMovInv ";
                    strSQL+="           WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="           AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="           AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_REG) + ")";
                    strSQL+=";";


                    strSQLUpd+=strSQL;




                }
                System.out.println("insertarDetConIntMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarPagMovInv(){
        boolean blnRes=true;
        int j=1;
        String strSQLUpd="";
        int intNumRegFacPrv=0;
        int intNumRegForPagFacPrv=0;
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_NO_EDI);
                objTblModForPag.removeEmptyRows();
                for (int i=0;i<objTblModForPag.getRowCountTrue();i++){
                    intNumRegForPagFacPrv=Integer.parseInt(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV)==null?"0":(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).toString().equals("")?"0":objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).toString()));
                    strSQL="";
                    strSQL+="INSERT INTO tbm_pagMovInv(";
                    strSQL+="            co_emp,co_loc,co_tipDoc,co_doc,co_reg,ne_diaCre,fe_ven,";
                    strSQL+="            co_tipRet,nd_porRet,tx_aplRet,nd_basImp,mo_pag,ne_diaGra,";
                    strSQL+="            nd_abo,st_sop,st_entSop,st_pos,co_banChq,tx_numCtaChq,";
                    strSQL+="            fe_recChq,nd_monChq,co_proChq,st_reg,";
                    strSQL+="            fe_ree,co_usrRee,tx_comRee,st_autPag,co_ctaAutPag,tx_obs1,";
                    strSQL+="            tx_numChq, tx_numSer,tx_numAutSRI,tx_fecCad, fe_venChq, nd_valIva, tx_codSRI,st_regRep)";
                    strSQL+=" VALUES (";

                    strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                    strSQL+=", " + txtCodTipDoc.getText();//co_tipDoc
                    strSQL+=", " + txtCodDoc.getText();//co_doc
                    strSQL+=", " + j;//co_reg
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_DIA_CRE) + "";//ne_diaCre
                    strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_FEC_VEN).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  )+ "";//fe_ven
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_TIP_RET) + "";//co_tipRet
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET) + "";//nd_porRet
                    strSQL+=", " + objUti.codificar(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_EST_APL)) + "";//tx_aplRet
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_BAS_IMP).toString(), objParSis.getDecimalesMostrar()) + "";//nd_basImp
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_VAL_RET).toString(), objParSis.getDecimalesMostrar()) + "";//mo_pag
                    strSQL+=", 0";//ne_diaGra
                    strSQL+=", " + new BigDecimal(BigInteger.ZERO) + "";//nd_abo
                    strSQL+=", 'N'";//st_sop
                    strSQL+=", 'N'";//st_entSop
                    strSQL+=", 'N'";//st_pos
                    strSQL+=", Null";//co_banChq
                    strSQL+=", Null";//tx_numCtaChq
                    strSQL+=", Null";//fe_recChq
                    strSQL+=", Null";//nd_monChq
                    strSQL+=", Null";//co_proChq
                    strSQL+=", 'A'";//st_reg
                    strSQL+=", Null";//fe_ree
                    strSQL+=", Null";//co_usrRee
                    strSQL+=", Null";//tx_comRee
                    strSQL+=", 'N'";//st_autPag
                    strSQL+=", Null";//co_ctaAutPag
                    strSQL+=", Null";//tx_obs1

                    if(intNumRegForPagFacPrv>0){
                        if(objTblModFacPrv.getRowCountTrue()>0){
                            for(int k=0; k<objTblModFacPrv.getRowCountTrue(); k++){
                                intNumRegFacPrv=Integer.parseInt(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_REG).toString());
                                if(intNumRegForPagFacPrv==intNumRegFacPrv){
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_FAC)) + "";//tx_numChq
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_SER)) + "";//tx_numSer
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_AUT)) + "";//tx_numAutSRI
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_CAD)) + "";//tx_fecCad
                                    strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_FAC).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  ) + "";//fe_venChq
                                    strSQL+=", " + objUti.redondearBigDecimal(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_IVA).toString(), objParSis.getDecimalesMostrar()).multiply(new BigDecimal("" + intSig)) + "";//nd_valIva
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        strSQL+=", Null";//tx_numChq
                        strSQL+=", Null";//tx_numSer
                        strSQL+=", Null";//tx_numAutSRI
                        strSQL+=", Null";//tx_fecCad
                        strSQL+=", Null";//fe_venChq
                        strSQL+=", " + new BigDecimal(txtIva.getText()).multiply(new BigDecimal("" + intSig)) ;////nd_valIva
                    }
                    strSQL+=", " + objUti.codificar(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_SRI)) + "";//tx_codSRI
                    strSQL+=", 'I'";//st_regRep
                    strSQL+=");";
                    j++;
                    strSQLUpd+=strSQL;
                }
                System.out.println("insertarPagMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarRetMovInv(){
        boolean blnRes=true;
        String strSQLUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblModMot.setModoOperacion(objTblModMot.INT_TBL_NO_EDI);
                objTblModMot.removeEmptyRows();

                for (int i=0;i<objTblModMot.getRowCountTrue();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbm_retMovInv(";
                    strSQL+="            co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_motDoc,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                    strSQL+=", " + txtCodTipDoc.getText();//co_tipDoc
                    strSQL+=", " + txtCodDoc.getText();//co_doc
                    strSQL+=", " + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_REG);//co_reg
                    strSQL+=", " + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_MOT) + "";//co_motDoc
                    strSQL+=", 'I'";//st_regRep
                    strSQL+=");";
                    strSQLUpd+=strSQL;
                }
                System.out.println("insertarRetMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabRecDoc(){
        int intUltReg;
        int intUltDocIng;
        boolean blnRes=true;
        BigDecimal bdeValFacPrv=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValTotFacPrv=new BigDecimal(BigInteger.ZERO);
        try{
            if(objTblModFacPrv.getRowCountTrue()>0){
                for(int k=0; k<objTblModFacPrv. getRowCountTrue();k++){
                    bdeValFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    bdeValTotFacPrv=bdeValTotFacPrv.add(bdeValFacPrv);
                }
                if (con!=null){
                    stm=con.createStatement();
                    //Obtener el cádigo del áltimo registro.
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_doc)";
                    strSQL+=" FROM tbm_cabRecDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_REC;
                    intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltReg==-1)
                        return false;
                    intUltReg++;
                    intCodDocRec=intUltReg;
                    strSQL="";
                    strSQL+="SELECT MAX(a1.ne_ultdoc)";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_REC;
                    intUltDocIng=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltDocIng==-1)
                        return false;
                    intUltDocIng++;
                    //Obtener la fecha del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabRecDoc(";
                    strSQL+="               co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc1,nd_monDoc,";
                    strSQL+="               co_usrRec,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,";
                    strSQL+="               co_usrIng,co_usrMod,tx_comIng,tx_comUltMod,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() + "";//co_emp
                    strSQL+="," + objParSis.getCodigoLocal() + "";//co_loc
                    strSQL+="," + INT_COD_TIP_DOC_REC + "";//co_tipdoc
                    strSQL+="," + intCodDocRec + "";//co_doc
                    strSQL+=",'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                    strSQL+="," + intUltDocIng + "";//ne_numDoc1
                    strSQL+="," + objUti.redondearBigDecimal(bdeValTotFacPrv, objParSis.getDecimalesMostrar()) + "";//nd_monDoc
                    strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrRec
                    strSQL+=",'N'";//st_imp
                    strSQL+=",Null";//tx_obs1
                    strSQL+=",Null";//tx_obs2
                    strSQL+=",'A'";//st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=",'" + strAux + "'";//fe_ing
                    strSQL+=",'" + strAux + "'";//fe_ultMod
                    strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrIng
                    strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrMod
                    strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comIng
                    strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comUltMod
                    strSQL+=",'I'";//st_regRep
                    strSQL+=");";
                    //System.out.println("insertarCabRecDoc: " + strSQL);
                    stm.executeUpdate(strSQL);
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */


    private boolean insertarDetRecDoc(){
        boolean blnRes=true;
        int j=1;
        String strSQLUpd="";
        BigDecimal bdeValTotDoc=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValFacPrv=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValTotFacPrv=new BigDecimal(BigInteger.ZERO);
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_NO_EDI);
                objTblModFacPrv.removeEmptyRows();

                bdeValTotDoc=new BigDecimal(txtTot.getText());
                if(objTblModFacPrv.getRowCountTrue()>0){
                    for (int i=0;i<objTblModFacPrv.getRowCountTrue();i++){
                        bdeValFacPrv=objUti.redondearBigDecimal(new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString())), objParSis.getDecimalesMostrar());
                        bdeValTotFacPrv=bdeValTotFacPrv.add(bdeValFacPrv);
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detRecDoc(";
                        strSQL+="co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_cli,co_banChq,tx_numCtaChq,";
                        strSQL+="tx_numChq,fe_recChq,fe_venChq,nd_monChq,tx_numSer,tx_numAutSRI,";
                        strSQL+=" tx_fecCad,tx_obs1,tx_obs2,st_reg,st_asiDocRel,nd_valTotAsi,";
                        strSQL+=" fe_asiTipDocCon,co_tipDocCon,nd_valApl,nd_valCon,st_regRep)";
                        strSQL+=" VALUES (";
                        strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                        strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                        strSQL+=", " + INT_COD_TIP_DOC_REC;//co_tipDoc
                        strSQL+=", " + intCodDocRec;//co_doc
                        strSQL+=", " + j;//co_reg
                        strSQL+=", " + txtCodPrv.getText() + "";//co_cli
                        strSQL+=", Null";//co_banChq
                        strSQL+=", Null";//tx_numCtaChq
                        strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC)) + "";//tx_numChq
                        strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_recChq
                        strSQL+=", '" + objUti.formatearFecha(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_venChq
                        strSQL+=", " + bdeValFacPrv + "";//nd_monChq
                        strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER)) + "";//tx_numSer
                        strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_AUT)) + "";//tx_numAutSRI
                        strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_CAD)) + "";//tx_fecCad
                        strSQL+=", Null";//tx_obs1
                        strSQL+=", Null";//tx_obs2
                        strSQL+=", 'A'";//st_reg
                        if(bdeValTotFacPrv.compareTo(bdeValTotDoc)<=0)
                            strSQL+=",'S'";//st_asiDocRel
                        else
                            strSQL+=",'N'";//st_asiDocRel
                        strSQL+="," + bdeValFacPrv + "";//nd_valTotAsi
                        strSQL+=",Null";//fe_asiTipDocCon
                        strSQL+="," + txtCodTipDoc.getText() + "";//co_tipDocCon
                        strSQL+=",0";//nd_valApl
                        strSQL+=",0";//nd_valCon
                        strSQL+=",'I'";//st_regRep
                        strSQL+=");";

                        strSQL+="INSERT INTO tbr_detRecDocCabMovInv(";
                        strSQL+="co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRel,co_locRel,";
                        strSQL+=" co_tipDocRel,co_docRel,nd_valAsi,st_reg,st_regRep)";
                        strSQL+=" VALUES (";
                        strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                        strSQL+="," + objParSis.getCodigoLocal();//co_loc
                        strSQL+="," + INT_COD_TIP_DOC_REC;//co_tipDoc
                        strSQL+="," + intCodDocRec;//co_doc
                        strSQL+="," +j;//co_reg
                        strSQL+="," + objParSis.getCodigoEmpresa();//co_empRel
                        strSQL+="," + objParSis.getCodigoLocal();//co_locRel
                        strSQL+="," + txtCodTipDoc.getText();//co_tipDocRel
                        strSQL+="," + txtCodDoc.getText();//co_docRel
                        strSQL+="," + bdeValFacPrv;//nd_valAsi
                        strSQL+=",'A'";//st_reg
                        strSQL+=",'I'";//st_regRep
                        strSQL+=");";
                        j++;
                        strSQLUpd+=strSQL;
                    }
                    //System.out.println("insertarDetRecDoc: " + strSQL);
                    stm.executeUpdate(strSQLUpd);
                    stm.close();
                    stm=null;
                }


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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabMovInv()){
                if (cargarDetReg()){
                }
                else
                    blnRes=false;

            }
            else
                blnRes=false;
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }


    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if (cargarDetConIntMovInv()){
                    if (cargarPagMovInv()){
                        if (cargarRetMovInv()){
                            if (cargarRecDoc()){
                                objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
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





    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabMovInv(){
        int intPosRel;
        boolean blnRes=true;
        blnNecAutAnu=false;
        blnDocAut=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.fe_doc";
                strSQL+=" , a1.co_cli, a1.tx_ruc, a1.tx_telcli, a1.tx_ciucli, a1.tx_nomCli, a1.tx_dirCli, a4.tx_deslar AS tx_nomCiuCli, a1.ne_numDoc";
                strSQL+=" , a1.co_ben, a1.tx_benChq, a1.co_doc, a1.co_com, a5.tx_usr, a5.tx_nom AS tx_nomCom";
                strSQL+=" , a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.tx_obs1, a1.tx_obs2";
                strSQL+=" ,a1.st_reg, a1.st_imp, a1.st_autAnu";
                strSQL+=" , a3.tx_numSerFacPrv, a3.tx_numAutSriFacPrv, a3.tx_fecCadFacPrv, a1.st_emichqantrecfacprv, a1.st_docMarLis, a2.st_docNecMarLis";
                strSQL+=" FROM (  (tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS a3 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli";
                strSQL+=" 			  LEFT OUTER JOIN tbm_ciu AS a4 ON a3.co_ciu=a4.co_ciu)";
                strSQL+=" 	  LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_com=a5.co_usr)";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                System.out.println("cargarCabMovInv: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){

                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);


                    strFecDocIni=dtpFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp");

                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cli");
                    txtCodPrv.setText((strAux==null)?"":strAux);
                    strIdePrv=rst.getString("tx_ruc");

                    strAux=rst.getString("tx_ruc");
                    txtRucPrv.setText((strAux==null)?"":strAux);

                    strTelPrv=rst.getString("tx_telcli");
                    strCiuPrv=rst.getString("tx_ciucli");
                    strAux=rst.getString("tx_nomCli");
                    txtDesLarPrv.setText((strAux==null)?"":strAux);
                    strDirPrv=rst.getString("tx_dirCli");


                    strNumSerFacPrv=rst.getObject("tx_numSerFacPrv")==null?"":rst.getString("tx_numSerFacPrv");
                    strNumAutSriFacPrv=rst.getObject("tx_numAutSriFacPrv")==null?"":rst.getString("tx_numAutSriFacPrv");
                    strNumFecCadFacPrv=rst.getObject("tx_fecCadFacPrv")==null?"":rst.getString("tx_fecCadFacPrv");


                    txtSub.setText(""+ objUti.redondearBigDecimal((rst.getObject("nd_sub")==null?new BigDecimal(BigInteger.ZERO):(rst.getObject("nd_sub").equals("")?new BigDecimal(BigInteger.ZERO):rst.getBigDecimal("nd_sub").abs())), objParSis.getDecimalesMostrar()));
                    txtIva.setText(""+ objUti.redondearBigDecimal((rst.getObject("nd_valIva")==null?new BigDecimal(BigInteger.ZERO):(rst.getObject("nd_valIva").equals("")?new BigDecimal(BigInteger.ZERO):rst.getBigDecimal("nd_valIva").abs())), objParSis.getDecimalesMostrar()));
                    txtTot.setText(""+ objUti.redondearBigDecimal((rst.getObject("nd_tot")==null?new BigDecimal(BigInteger.ZERO):(rst.getObject("nd_tot").equals("")?new BigDecimal(BigInteger.ZERO):rst.getBigDecimal("nd_tot").abs())), objParSis.getDecimalesMostrar()));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("co_ben");
                    txtCodBen.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_benChq");
                    txtNomBen.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("co_com");
                    txtCodCom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_usr");
                    txtAliCom.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomCom");
                    txtNomCom.setText((strAux==null)?"":strAux);

                    strAux=(rst.getObject("st_emichqantrecfacprv")==null?"N":rst.getString("st_emichqantrecfacprv"));
                    chkEmiChqAntRecFacPrv.setSelected((strAux.equals("S")?true:false));

                    strAux=(rst.getObject("st_docMarLis")==null?"N":rst.getString("st_docMarLis"));
                    chkNecMarLis.setSelected((strAux.equals("S")?true:false));

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

                    strNecPerAutPag=(rst.getObject("st_docNecMarLis")==null?"N":rst.getString("st_docNecMarLis").equals("")?"N":rst.getString("st_docNecMarLis"));
                    if(strNecPerAutPag.equals("S")){
                        chkNecMarLis.setVisible(true);
                        chkEmiChqAntRecFacPrv.setVisible(false);
                    }
                    else{
                        chkEmiChqAntRecFacPrv.setVisible(true);
                        chkNecMarLis.setVisible(false);
                    }





                }
                else{
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
                //System.out.println("st_necAutAnuDoc: " + strSQL);
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
            //Mostrar la posición relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetConIntMovInv(){
        boolean blnRes=true;
        BigDecimal bdeCan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePreUni=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSub=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeIva=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePorIva;
        //BigDecimal bdePorIva=new BigDecimal("0.12");
        //BigDecimal bdePorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        try{
            if (!txtCodPrv.getText().equals("")){
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                    strSQL+=" , a2.tx_des, a2.nd_can, a2.nd_preUni, a2.st_iva, a1.nd_poriva, a2.nd_tot";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detconintmovinv AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                    strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                    strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                    strSQL+=" AND a1.st_reg NOT IN('E')";
                    strSQL+=" ORDER BY a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        bdeCan=objUti.redondearBigDecimal(rst.getBigDecimal("nd_can"), objParSis.getDecimalesBaseDatos());
                        bdePreUni=objUti.redondearBigDecimal(rst.getBigDecimal("nd_preUni"), objParSis.getDecimalesBaseDatos());
                        bdeSub=objUti.redondearBigDecimal(bdeCan.multiply(bdePreUni), objParSis.getDecimalesBaseDatos());
                        bdePorIva=objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva").divide(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos());

                        vecReg=new Vector();
                        vecReg.add(INT_TBL_GRL_DAT_LIN,"");
                        vecReg.add(INT_TBL_GRL_DAT_DES,     "" + rst.getObject("tx_des")==null?"":rst.getString("tx_des"));
                        vecReg.add(INT_TBL_GRL_DAT_CAN,     "" + bdeCan);
                        vecReg.add(INT_TBL_GRL_DAT_PRE,     "" + bdePreUni);
                        vecReg.add(INT_TBL_GRL_DAT_SUB,     "" + bdeSub);
                        vecReg.add(INT_TBL_GRL_DAT_EST_IVA, null);
                        vecReg.add(INT_TBL_GRL_DAT_IVA,     "");
                        vecReg.add(INT_TBL_GRL_DAT_TOT,     "" + objUti.redondearBigDecimal(rst.getBigDecimal("nd_tot"), objParSis.getDecimalesBaseDatos()));
                        vecReg.add(INT_TBL_GRL_DAT_REG,     "" + rst.getString("co_reg"));

                        strAux=rst.getObject("st_iva")==null?"N":(rst.getString("st_iva").equals("")?"N":rst.getString("st_iva"));
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true), INT_TBL_GRL_DAT_EST_IVA);
                            bdeIva=objUti.redondearBigDecimal(bdeSub.multiply(bdePorIva), objParSis.getDecimalesBaseDatos());
                            vecReg.setElementAt(bdeIva, INT_TBL_GRL_DAT_IVA);
                        }

                        vecDat.add(vecReg);

                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarPagMovInv(){
        boolean blnRes=true;
        String strApl="";
        try{
            if (!txtCodPrv.getText().equals("")){
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" ,a2.ne_diaCre, a2.fe_ven, a2.co_tipRet, a3.tx_desCor AS tx_desCorTipRet, a3.tx_desLar AS tx_desLarTipRet";
                    strSQL+=" , a2.nd_porRet, a2.tx_aplRet, a2.nd_basImp, a2.tx_codSri, a2.mo_pag, a2.co_reg";
                    strSQL+=" , a1.co_forPag, a4.tx_des AS tx_desForPag, a4.ne_numpag, a4.ne_tipforpag, ABS(a2.nd_abo) AS nd_abo";
                    strSQL+=" ,a2.st_autpag, a2.co_ctaautpag";
                    strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabForPag AS a4";
                    strSQL+="           ON a1.co_emp=a4.co_emp AND a1.co_forPag=a4.co_forPag)";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_tipRet=a3.co_tipRet AND a3.st_reg NOT IN('I','E')";
                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                    strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                    strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                    strSQL+=" AND a1.st_reg NOT IN('E')";
                    strSQL+=" AND a2.st_reg IN('A', 'C')";
                    strSQL+=" ORDER BY a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatForPag.clear();
                    arlDatAutPag.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecRegForPag=new Vector();
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_LIN, "");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,          "" + rst.getString("ne_diaCre"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,          objUti.formatearFecha(rst.getString("fe_ven"), "yyyy-MM-dd", "dd/MM/yyyy"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,      rst.getString("co_tipRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,  rst.getString("tx_desCorTipRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,      null);
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,  rst.getString("tx_desLarTipRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_POR_RET,          rst.getBigDecimal("nd_porRet"));

                        strApl=rst.getObject("tx_aplRet")==null?"":rst.getString("tx_aplRet");

                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_APL,              strApl.equals("S")?"Subtotal":(strApl.equals("I")?"Iva":(strApl.equals("O")?"Otros":"")));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_EST_APL,          strApl);
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,          objUti.redondearBigDecimal(rst.getBigDecimal("nd_basImp"), objParSis.getDecimalesMostrar())   );
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_SRI,          rst.getString("tx_codSri"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_VAL_RET,          objUti.redondearBigDecimal(rst.getBigDecimal("mo_pag"), objParSis.getDecimalesMostrar()));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV,  rst.getString("co_reg"));


                        vecDatForPag.add(vecRegForPag);


                        txtCodForPag.setText(rst.getString("co_forPag"));
                        txtNomForPag.setText(rst.getString("tx_desForPag"));
                        txtNumForPag.setText(rst.getString("ne_numpag"));
                        txtTipForPag.setText(rst.getString("ne_tipforpag"));



                        arlRegAutPag=new ArrayList();
                        arlRegAutPag.add(INT_ARL_COD_REG, rst.getString("co_reg"));
                        arlRegAutPag.add(INT_ARL_EST_AUT_PAG, rst.getObject("st_autpag")==null?"":rst.getString("st_autpag"));
                        arlRegAutPag.add(INT_ARL_COD_CTA_PAG, rst.getObject("co_ctaautpag")==null?"":rst.getString("co_ctaautpag"));
                        arlDatAutPag.add(arlRegAutPag);
                        System.out.println("CONSULTA- arlDatAutPag: " + arlDatAutPag.toString());


                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    //Asignar vectores al modelo.
                    objTblModForPag.setData(vecDatForPag);
                    tblForPag.setModel(objTblModForPag);
                    vecDatForPag.clear();
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarRetMovInv(){
        boolean blnRes=true;
        try{
            if (!txtCodPrv.getText().equals("")){
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                    strSQL+=" , a2.co_motDoc, a3.tx_desCor As tx_desCorMot, a3.tx_desLar AS tx_desLarMot";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_retMovInv AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbm_motDoc AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_motDoc=a3.co_mot";
                    strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                    strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                    strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a3.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatMot.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecRegMot=new Vector();
                        vecRegMot.add(INT_TBL_MOT_DAT_LIN, "");
                        vecRegMot.add(INT_TBL_MOT_DAT_COD_MOT,     rst.getString("co_motDoc"));
                        vecRegMot.add(INT_TBL_MOT_DAT_DES_COR_MOT, rst.getString("tx_desCorMot"));
                        vecRegMot.add(INT_TBL_MOT_DAT_BUT_MOT,     null);
                        vecRegMot.add(INT_TBL_MOT_DAT_DES_LAR_MOT, rst.getString("tx_desLarMot"));
                        vecRegMot.add(INT_TBL_MOT_DAT_COD_REG,     rst.getString("co_reg"));
                        vecDatMot.add(vecRegMot);


                        intNumRegMotDoc=rst.getInt("co_reg");

                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    //Asignar vectores al modelo.
                    objTblModMot.setData(vecDatMot);
                    tblMot.setModel(objTblModMot);
                    vecDatMot.clear();
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarRecDoc(){
        boolean blnRes=true;
        BigDecimal bdeSubFacPrv=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotFacPrv=new BigDecimal(BigInteger.ZERO);
        //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
        BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        BigDecimal bdeIvaFacPrv=new BigDecimal(BigInteger.ZERO);


        try{
            if (!txtCodPrv.getText().equals("")){
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" , a2.tx_numChq AS ne_numFac, a2.fe_venChq AS fe_fac, a2.nd_monChq AS nd_valFac";
                    strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad";
                    strSQL+=" FROM tbm_cabRecDoc AS a1";
                    strSQL+=" INNER JOIN tbm_detRecDoc AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbr_detRecDocCabMovInv AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                    strSQL+=" WHERE a3.co_empRel=" + rstCab.getString("co_emp") + "";
                    strSQL+=" AND a3.co_locRel=" + rstCab.getString("co_loc") + "";
                    strSQL+=" AND a3.co_tipDocRel=" + rstCab.getString("co_tipDoc") + "";
                    strSQL+=" AND a3.co_docRel=" + rstCab.getString("co_doc") + "";
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" , a2.tx_numChq, a2.fe_venChq, a2.nd_monChq";
                    strSQL+=" , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad";
                    strSQL+=" ORDER BY a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatFacPrv.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecRegFacPrv=new Vector();
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_LIN, "");
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_FAC, rst.getString("ne_numFac"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_FEC_FAC, objUti.formatearFecha(rst.getDate("fe_fac"),"dd/MM/yyyy"));

                        bdeTotFacPrv=objUti.redondearBigDecimal(rst.getBigDecimal("nd_valFac"), objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_FAC, bdeTotFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA, "0");
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_SER, rst.getString("tx_numSer"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_AUT, rst.getString("tx_numAutSri"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_FEC_CAD, rst.getString("tx_fecCad"));

//                        bdeSubFacPrv=bdeTotFacPrv.divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
//                        bdeIvaFacPrv=bdeTotFacPrv.subtract(bdeSubFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_SUB, bdeSubFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_IVA, bdeIvaFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_REG, rst.getString("co_reg"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_PND_PAG, "");






                        vecDatFacPrv.add(vecRegFacPrv);

                        intNumRegFacPrv=rst.getInt("co_reg");


                    }



                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    //Asignar vectores al modelo.
                    objTblModFacPrv.setData(vecDatFacPrv);
                    tblFacPrv.setModel(objTblModFacPrv);
                    vecDatFacPrv.clear();
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
    private boolean actualizarCabMovInv(){
        boolean blnRes=true;
        BigDecimal bdePorIva;
        try{
            if (con!=null){
                stm=con.createStatement();
                bdePorIva = new BigDecimal("0");
                //Se va a obtener el porcentaje de IVA de tbm_cabmovinv
                strSQL = "SELECT nd_poriva ";
                strSQL += "FROM tbm_cabmovinv ";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                   bdePorIva = rst.getBigDecimal("nd_poriva");
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabmovinv";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strSQL+=", ne_numDoc=" + txtNumDoc.getText();
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=", co_forPag=" + txtCodForPag.getText() + "";
                strSQL+=", nd_sub=" + new BigDecimal(txtSub.getText()).multiply(new BigDecimal("" + intSig)) + "";//
                
                if ( new BigDecimal(txtIva.getText()).compareTo(BigDecimal.ZERO)>0 )
                {  //strSQL+=", nd_poriva=12.00";//
                   strSQL+=", nd_poriva=" + bdePorIva; //nd_poriva
                }
                else
                    strSQL+=", nd_poriva=00"; //nd_poriva
                
                strSQL+=", nd_valiva=" + new BigDecimal(txtIva.getText()).multiply(new BigDecimal("" + intSig)) + "";//
                strSQL+=", nd_tot=" + new BigDecimal(txtTot.getText()).multiply(new BigDecimal("" + intSig)) + "";//

                if(chkEmiChqAntRecFacPrv.isSelected())
                    strSQL+=", st_emiChqAntRecFacPrv='S'";//st_emiChqAntRecFacPrv
                else
                    strSQL+=", st_emiChqAntRecFacPrv=Null";//st_emiChqAntRecFacPrv

                if(chkNecMarLis.isSelected())
                    strSQL+=", st_docMarLis='S'";//st_docMarLis
                else
                    strSQL+=", st_docMarLis=Null";//st_docMarLis

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


//    private String getFechaCheque(){
//        String strFecChq="";
//        Statement stmFecChq;
//        ResultSet rstFecChq;
//        String strSQLFecChq="";
//        try{
//            if(con!=null){
//                stmFecChq=con.createStatement();
//                strSQLFecChq="";
//                strSQLFecChq+="SELECT fe_recChq";
//                strSQLFecChq+=" FROM tbm_pagMovInv AS a1 INNER JOIN tbm_autpagmovinv AS a2";
//                strSQLFecChq+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQLFecChq+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
//                strSQLFecChq+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";
//                strSQLFecChq+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
//                strSQLFecChq+=" AND a1.co_doc=" + rstCab.getString("co_doc") + "";
//                strSQLFecChq+=" AND a1.st_reg IN('A','C') AND a2.st_reg='A'";
//                System.out.println("strSQLFecChq: " + strSQLFecChq);
//                rstFecChq=stmFecChq.executeQuery(strSQLFecChq);
//                if(rstFecChq.next()){
//                    strFecChq=rstFecChq.getString("fe_recChq");
//                }
//                System.out.println("strFecChq: " + strFecChq);
//                stmFecChq.close();
//                stmFecChq=null;
//                rstFecChq.close();
//                rstFecChq=null;
//            }
//
//        }
//        catch (java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return strFecChq;
//    }




    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarPagMovInv(){
        boolean blnRes=true;
        int intNumRegForPagFacPrv=0;
        String strSQLUpd="";
        int intUltReg;
        Object objCodCtaAut=null;
        String strAutPag="N";
        int intInsAutPagMovInv=0;
        int intUltRegAut;
        Object objFecChq=null;

        try{
            if (con!=null){

                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_ctaAutPag, a1.st_autPag";
                strSQL+=" FROM tbm_pagMovInv AS a1";
                strSQL+="       WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+="       AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+="       AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+="       AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" 	AND a1.st_reg IN('A','C')";
                strSQL+="       AND a1.st_autPag='S' AND (a1.co_tipRet=0 OR a1.co_tipRet IS NULL)";
                strSQL+="    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_ctaAutPag, a1.st_autPag";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodCtaAut=rst.getObject("co_ctaAutPag");
                    strAutPag=rst.getString("st_autPag");
                }

                stm.close();
                stm=null;
                rst.close();
                rst=null;


                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT fe_recChq";
                strSQL+=" FROM tbm_pagMovInv AS a1 INNER JOIN tbm_autpagmovinv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                strSQL+=" AND a1.st_reg IN('A','C') AND a2.st_reg='A'";
                strSQL+=" AND a1.nd_porRet<=0";
                System.out.println("strSQLFecChq: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objFecChq=rst.getString("fe_recChq");
                }
                System.out.println("objFecChq: " + objFecChq);
                stm.close();
                stm=null;
                rst.close();
                rst=null;





                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_pagMovInv";
                strSQL+=" SET st_reg=";
                strSQL+="    CASE WHEN x.st_reg='A' THEN 'F'";
                strSQL+=" 	WHEN x.st_reg='C' THEN 'I'";
                strSQL+="	END";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.st_reg";
                strSQL+=" 	FROM tbm_pagMovInv AS a1";
                strSQL+="       WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+="       AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+="       AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+="       AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" 	AND a1.st_reg IN('A','C')";
                strSQL+=" 	) AS x";
                strSQL+=" WHERE tbm_pagMovInv.co_emp=x.co_emp AND tbm_pagMovInv.co_loc=x.co_loc AND tbm_pagMovInv.co_tipDoc=x.co_tipDoc";
                strSQL+=" AND tbm_pagMovInv.co_doc=x.co_doc AND tbm_pagMovInv.co_reg=x.co_reg";
                System.out.println("actualizarPagMovInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;





                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg)";
                strSQL+=" FROM tbm_pagMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;

                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg)";
                strSQL+=" FROM tbm_autPagMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                intUltRegAut=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltRegAut==-1)
                    return false;
                intUltRegAut++;

                stm=con.createStatement();
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_NO_EDI);
                objTblModForPag.removeEmptyRows();
                for (int i=0;i<objTblModForPag.getRowCountTrue();i++){
                    intNumRegForPagFacPrv=Integer.parseInt(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV)==null?"0":(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).toString().equals("")?"0":objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).toString()));
                    strSQL="";
                    strSQL+="INSERT INTO tbm_pagMovInv(";
                    strSQL+="            co_emp,co_loc,co_tipDoc,co_doc,co_reg,ne_diaCre,fe_ven,";
                    strSQL+="            co_tipRet,nd_porRet,tx_aplRet,nd_basImp,mo_pag,ne_diaGra,";
                    strSQL+="            nd_abo,st_sop,st_entSop,st_pos,co_banChq,tx_numCtaChq,";
                    strSQL+="            fe_recChq,nd_monChq,co_proChq,st_reg,";
                    strSQL+="            fe_ree,co_usrRee,tx_comRee,st_autPag,co_ctaAutPag,tx_obs1,";
                    strSQL+="            tx_numChq, tx_numSer,tx_numAutSRI,tx_fecCad, fe_venChq, nd_valIva, tx_codSRI,st_regRep)";
                    strSQL+=" VALUES (";

                    strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                    strSQL+=", " + txtCodTipDoc.getText();//co_tipDoc
                    strSQL+=", " + txtCodDoc.getText();//co_doc
                    strSQL+=", " + intUltReg;//co_reg
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_DIA_CRE) + "";//ne_diaCre
                    strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_FEC_VEN).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  )+ "";//fe_ven
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_TIP_RET) + "";//co_tipRet
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET) + "";//nd_porRet
                    strSQL+=", " + objUti.codificar(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_EST_APL)) + "";//tx_aplRet
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_BAS_IMP).toString(), objParSis.getDecimalesMostrar()) + "";//nd_basImp
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_VAL_RET).toString(), objParSis.getDecimalesMostrar()) + "";//mo_pag
                    strSQL+=", 0";//ne_diaGra
                    strSQL+=", " + new BigDecimal(BigInteger.ZERO) + "";//nd_abo
                    strSQL+=", 'N'";//st_sop
                    strSQL+=", 'N'";//st_entSop
                    strSQL+=", 'N'";//st_pos
                    strSQL+=", Null";//co_banChq
                    strSQL+=", Null";//tx_numCtaChq
                    if(objFecChq==null)
                        strSQL+=", " + objFecChq + "";//fe_recChq
                    else
                        strSQL+=", '" + objUti.formatearFecha(objFecChq.toString(),"yyyy-MM-dd",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_recChq
                    strSQL+=", Null";//nd_monChq
                    strSQL+=", Null";//co_proChq
                    strSQL+=", 'C'";//st_reg
                    strSQL+=", Null";//fe_ree
                    strSQL+=", Null";//co_usrRee
                    strSQL+=", Null";//tx_comRee

                    int intCodTipRet=Integer.parseInt(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_TIP_RET)==null?"0":(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_TIP_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_TIP_RET).toString()));

                    if(intCodTipRet==0){
                        if(strAutPag.equals("N")){
                            strSQL+=", 'N'";//st_autPag
                            strSQL+=", Null";//co_ctaAutPag
                        }
                        else{
                            strSQL+=", 'S'";//st_autPag
                            strSQL+=", " + objCodCtaAut + "";//co_ctaAutPag
                            //strAutPag="N";

                            intInsAutPagMovInv=1;

                        }
                    }
                    else{
                        strSQL+=", 'N'";//st_autPag
                        strSQL+=", Null";//co_ctaAutPag
                    }
                    strSQL+=", Null";//tx_obs1


                    System.out.println("intNumRegForPagFacPrv: " + intNumRegForPagFacPrv);

                    if(intNumRegForPagFacPrv>0){
                        if(objTblModFacPrv.getRowCountTrue()>0){
                            for(int k=0; k<objTblModFacPrv.getRowCountTrue(); k++){
                                intNumRegFacPrv=Integer.parseInt(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_REG).toString());
                                if(intNumRegForPagFacPrv==intNumRegFacPrv){
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_FAC)) + "";//tx_numChq
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_SER)) + "";//tx_numSer
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_AUT)) + "";//tx_numAutSRI
                                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_CAD)) + "";//tx_fecCad
                                    strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_FAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())) + "";//fe_venChq
                                    strSQL+=", " + objUti.redondearBigDecimal(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_IVA).toString(), objParSis.getDecimalesMostrar()).multiply(new BigDecimal("" + intSig)) + "";//nd_valIva
                                    break;
                                }
                            }
                        }
                        else{
                            strSQL+=", Null";//tx_numChq
                            strSQL+=", Null";//tx_numSer
                            strSQL+=", Null";//tx_numAutSRI
                            strSQL+=", Null";//tx_fecCad
                            strSQL+=", Null";//fe_venChq
                            strSQL+=", " + new BigDecimal(txtIva.getText()).multiply(new BigDecimal("" + intSig)) ;//nd_valIva
                        }
                    }
                    else{
                        strSQL+=", Null";//tx_numChq
                        strSQL+=", Null";//tx_numSer
                        strSQL+=", Null";//tx_numAutSRI
                        strSQL+=", Null";//tx_fecCad
                        strSQL+=", Null";//fe_venChq
                        strSQL+=", " + new BigDecimal(txtIva.getText()).multiply(new BigDecimal("" + intSig)) ;//nd_valIva
                    }
                    strSQL+=", " + objUti.codificar(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_SRI)) + "";//tx_codSRI
                    strSQL+=", 'I'";//st_regRep
                    strSQL+=");";

                    if(intInsAutPagMovInv==1){
                        strSQL+="INSERT INTO tbm_autpagmovinv(";
                        strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_aut, fe_autpag,";
                        strSQL+="             co_usrautpag, tx_comautpag, co_ctaautpag, st_reg, st_regrep)";
                        strSQL+="(";
                        strSQL+=" 	SELECT co_emp, co_loc, co_tipdoc";
                        strSQL+=" ,     co_doc, " + intUltReg + "AS co_reg, " + intUltRegAut + " AS co_aut, fe_autpag,";
                        strSQL+="       co_usrautpag, tx_comautpag, co_ctaautpag, 'A' AS st_reg, 'I' AS st_regrep";
                        strSQL+="       FROM tbm_autPagMovInv AS a1";
                        strSQL+="       WHERE a1.co_emp=" + rstCab.getString("co_emp");
                        strSQL+="       AND a1.co_loc=" + rstCab.getString("co_loc");
                        strSQL+="       AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                        strSQL+="       AND a1.co_doc=" + rstCab.getString("co_doc");
                        strSQL+="       AND a1.st_reg='A'";
                        strSQL+=" GROUP BY co_emp, co_loc, co_tipdoc, co_doc, fe_autpag, co_usrautpag, tx_comautpag, co_ctaautpag";
                        strSQL+=");";
                        strSQL+="UPDATE tbm_autPagMovInv";
                        strSQL+=" SET st_reg='I' FROM(";
                        strSQL+=" 	SELECT *FROM tbm_autPagMovInv AS a1";
                        strSQL+="       WHERE a1.co_emp=" + rstCab.getString("co_emp");
                        strSQL+="       AND a1.co_loc=" + rstCab.getString("co_loc");
                        strSQL+="       AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                        strSQL+="       AND a1.co_doc=" + rstCab.getString("co_doc");
                        strSQL+="       AND a1.st_reg='A'";
                        strSQL+="       AND a1.co_reg NOT IN(" + intUltReg + ")";
                        strSQL+="       AND a1.co_aut NOT IN(" + intUltRegAut + ")";
                        strSQL+=" 	) AS x";
                        strSQL+=" 	WHERE tbm_autPagMovInv.co_emp=x.co_emp AND tbm_autPagMovInv.co_loc=x.co_loc";
                        strSQL+=" 	AND tbm_autPagMovInv.co_tipDoc=x.co_tipDoc AND tbm_autPagMovInv.co_doc=x.co_doc";
                        strSQL+=" 	AND tbm_autPagMovInv.co_reg=x.co_reg";
                        strSQL+=";";

                        objCodCtaAut=null;
                        strAutPag="N";
                        intInsAutPagMovInv=0;

                    }




                    strSQLUpd+=strSQL;
                    intUltReg++;
                }
                System.out.println("insertarPagMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
                stm.close();
                stm=null;
                datFecAux=null;

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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarRetMovInv(){
        boolean blnRes=true;
        String strSQLUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblModMot.setModoOperacion(objTblModMot.INT_TBL_NO_EDI);
                objTblModMot.removeEmptyRows();

                for (int i=0;i<objTblModMot.getRowCountTrue();i++){
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_retMovInv(";
                    strSQL+=" co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_motDoc,st_regRep)";
                    strSQL+=" (SELECT " + rstCab.getString("co_emp") + " AS co_emp, " + rstCab.getString("co_loc") + " AS co_loc,";
                    strSQL+=" " + rstCab.getString("co_tipDoc") + " AS co_tipDoc, " + rstCab.getString("co_doc") + " AS co_doc";
                    strSQL+=" , " + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_REG) + " AS co_reg, " + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_MOT) + " AS co_motDoc, 'I' AS st_regRep";
                    strSQL+=" WHERE NOT EXISTS(";
                    strSQL+="                   SELECT *FROM tbm_retMovInv ";
                    strSQL+="                   WHERE co_emp=" + rstCab.getString("co_emp") + " AND co_loc=" + rstCab.getString("co_loc") + "";
                    strSQL+="                   AND co_tipDoc=" + rstCab.getString("co_tipDoc") + " AND co_doc=" + rstCab.getString("co_doc") + "";
                    strSQL+="                   AND co_reg=" + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_REG) + ")";
                    strSQL+=");";
                    strSQL+="UPDATE tbm_retMovInv";
                    strSQL+=" SET co_motDoc=" + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_MOT) + "";
                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + " AND co_loc=" + rstCab.getString("co_loc") + "";
                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + " AND co_doc=" + rstCab.getString("co_doc") + "";
                    strSQL+=" AND co_reg=" + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_REG) + "";
                    strSQL+=" AND EXISTS( SELECT *FROM tbm_retMovInv ";
                    strSQL+="           WHERE co_emp=" + rstCab.getString("co_emp") + " AND co_loc=" + rstCab.getString("co_loc") + "";
                    strSQL+="           AND co_tipDoc=" + rstCab.getString("co_tipDoc") + " AND co_doc=" + rstCab.getString("co_doc") + "";
                    strSQL+="           AND co_reg=" + objTblModMot.getValueAt(i,INT_TBL_MOT_DAT_COD_REG) + ")";
                    strSQL+=";";
                    strSQLUpd+=strSQL;

                }
                System.out.println("insertarRetMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCli(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                for (int i=0;i<objTblModFacPrv.getRowCountTrue();i++){
                    if( (!strNumSerFacPrv.equals("")) && (!strNumAutSriFacPrv.equals("")) && (!strNumFecCadFacPrv.equals(""))  ){
                        strSQL="";
                        strSQL+=" UPDATE tbm_cli";
                        strSQL+=" SET tx_numSerFacPrv= " + objUti.codificar(strNumSerFacPrv) + "";
                        strSQL+=", tx_numAutSriFacPrv=" + objUti.codificar(strNumAutSriFacPrv) + "";
                        strSQL+=", tx_fecCadFacPrv=" + objUti.codificar(strNumFecCadFacPrv) + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_cli=" + txtCodPrv.getText() + "";
                        System.out.println("actualizarCli: " + strSQL);
                        stm.executeUpdate(strSQL);
                        break;
                    }
                }
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabRecDoc(){
        boolean blnRes=true;
        String strSQLUpd="";
        int intUltReg, intUltDocIng;
        BigDecimal bdeValFacPrv=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValTotFacPrv=new BigDecimal(BigInteger.ZERO);
        try{
            if (con!=null){
                for(int k=0; k<objTblModFacPrv. getRowCountTrue();k++){
                    bdeValFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    bdeValTotFacPrv=bdeValTotFacPrv.add(bdeValFacPrv);
                }
                stm=con.createStatement();
                objTblModMot.setModoOperacion(objTblModMot.INT_TBL_NO_EDI);
                objTblModMot.removeEmptyRows();

                intCodDocRec=0;
                strSQL="";
                strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc";
                strSQL+=" FROM tbr_detRecDocCabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_cabRecDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc  ";
                strSQL+=" AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" where a1.co_empRel=" + rstCab.getString("co_emp") + " and a1.co_locRel=" + rstCab.getString("co_loc") + "";
                strSQL+=" and a1.co_tipdocRel=" + rstCab.getString("co_tipDoc") + " and a1.co_docRel=" + rstCab.getString("co_doc") + "";
                System.out.println("existe-actualizarCabRecDoc: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodDocRec=rst.getInt("co_doc");
                }
                if(intCodDocRec==0){//no existe el documento
                    stm=con.createStatement();
                    //Obtener el cádigo del áltimo registro.
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_doc)";
                    strSQL+=" FROM tbm_cabRecDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_REC;
                    intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltReg==-1)
                        return false;
                    intUltReg++;
                    intCodDocRec=intUltReg;
                    strSQL="";
                    strSQL+="SELECT MAX(a1.ne_ultdoc)";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_REC;
                    intUltDocIng=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intUltDocIng==-1)
                        return false;
                    intUltDocIng++;
                    //Obtener la fecha del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabRecDoc(";
                    strSQL+="               co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc1,nd_monDoc,";
                    strSQL+="               co_usrRec,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,";
                    strSQL+="               co_usrIng,co_usrMod,tx_comIng,tx_comUltMod,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() + "";//co_emp
                    strSQL+="," + objParSis.getCodigoLocal() + "";//co_loc
                    strSQL+="," + INT_COD_TIP_DOC_REC + "";//co_tipdoc
                    strSQL+="," + intCodDocRec + "";//co_doc
                    strSQL+=",'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                    strSQL+="," + intUltDocIng + "";//ne_numDoc1
                    strSQL+="," + objUti.redondearBigDecimal(bdeValTotFacPrv, objParSis.getDecimalesMostrar()) + "";//nd_monDoc
                    strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrRec
                    strSQL+=",'N'";//st_imp
                    strSQL+=",Null";//tx_obs1
                    strSQL+=",Null";//tx_obs2
                    strSQL+=",'A'";//st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=",'" + strAux + "'";//fe_ing
                    strSQL+=",'" + strAux + "'";//fe_ultMod
                    strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrIng
                    strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrMod
                    strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comIng
                    strSQL+="," + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comUltMod
                    strSQL+=",'I'";//st_regRep
                    strSQL+=");";
                    strSQLUpd+=strSQL;
                }
                else{//si existe y solo se debe modificar

                    //Obtener la fecha del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;

                    strSQL="";
                    strSQL+="UPDATE tbm_cabRecDoc";
                    strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                    strSQL+="               SET fe_doc='" + strAux + "'";
                    strSQL+="               ,nd_monDoc=" + objUti.redondearBigDecimal(bdeValTotFacPrv, objParSis.getDecimalesMostrar());
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+="               ,fe_ultMod='" + strAux + "'";
                    strSQL+="               ,co_usrMod=" + objParSis.getCodigoUsuario() + "";
                    strSQL+="               ,tx_comUltMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                    strSQL+="       WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="       AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="       AND co_tipDoc=" + INT_COD_TIP_DOC_REC + "";
                    strSQL+="       AND co_doc=" + intCodDocRec + "";
                    strSQL+=" AND EXISTS(";
                    strSQL+="       SELECT *FROM tbm_cabRecDoc";
                    strSQL+="       WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="       AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="       AND co_tipDoc=" + INT_COD_TIP_DOC_REC + "";
                    strSQL+="       AND co_doc=" + intCodDocRec + "";
                    strSQL+=");";
                    strSQLUpd+=strSQL;
                }
                System.out.println("actualizarCabRecDoc: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDetRecDoc(){
        boolean blnRes=true;
        String strSQLUpd="";
        BigDecimal bdeValTotDoc=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValFacPrv=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValTotFacPrv=new BigDecimal(BigInteger.ZERO);
        int intUltReg;
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_NO_EDI);
                objTblModFacPrv.removeEmptyRows();
                bdeValTotDoc=new BigDecimal(txtTot.getText());

                strSQL="";
                strSQL+="UPDATE tbm_detRecDoc";
                strSQL+=" SET st_reg='I'";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.st_reg";
                strSQL+=" 	FROM tbm_detRecDoc AS a1";
                strSQL+="       WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+="       AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+="       AND a1.co_tipDoc=" + INT_COD_TIP_DOC_REC;
                strSQL+="       AND a1.co_doc=" + intCodDocRec;
                strSQL+=" 	AND a1.st_reg IN('A')";
                strSQL+=" 	) AS x";
                strSQL+=" WHERE tbm_detRecDoc.co_emp=x.co_emp AND tbm_detRecDoc.co_loc=x.co_loc AND tbm_detRecDoc.co_tipDoc=x.co_tipDoc";
                strSQL+=" AND tbm_detRecDoc.co_doc=x.co_doc AND tbm_detRecDoc.co_reg=x.co_reg";
                strSQL+=";";
                strSQL+="UPDATE tbr_detRecDocCabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.st_reg";
                strSQL+=" 	FROM tbr_detRecDocCabMovInv AS a1";
                strSQL+="       WHERE a1.co_empRel=" + rstCab.getString("co_emp");
                strSQL+="       AND a1.co_locRel=" + rstCab.getString("co_loc");
                strSQL+="       AND a1.co_tipDocRel=" + rstCab.getString("co_tipDoc");
                strSQL+="       AND a1.co_docRel=" + rstCab.getString("co_doc");
                strSQL+=" 	AND a1.st_reg IN('A')";
                strSQL+=" 	) AS x";
                strSQL+=" WHERE tbr_detRecDocCabMovInv.co_empRel=x.co_empRel AND tbr_detRecDocCabMovInv.co_locRel=x.co_locRel ";
                strSQL+=" AND tbr_detRecDocCabMovInv.co_tipdocRel=x.co_tipdocRel AND tbr_detRecDocCabMovInv.co_docRel=x.co_docRel";
                strSQL+=";";


                System.out.println("actualizarDetRecDoc: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;


                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg)";
                strSQL+=" FROM tbm_detRecDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_REC;
                strSQL+=" AND a1.co_doc=" + intCodDocRec;
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;



                stm=con.createStatement();
                for (int i=0;i<objTblModFacPrv.getRowCountTrue();i++){
                    bdeValFacPrv=objUti.redondearBigDecimal(new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString())), objParSis.getDecimalesMostrar());
                    bdeValTotFacPrv=bdeValTotFacPrv.add(bdeValFacPrv);
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detRecDoc(";
                    strSQL+="co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_cli,co_banChq,tx_numCtaChq,";
                    strSQL+="tx_numChq,fe_recChq,fe_venChq,nd_monChq,tx_numSer,tx_numAutSRI,";
                    strSQL+=" tx_fecCad,tx_obs1,tx_obs2,st_reg,st_asiDocRel,nd_valTotAsi,";
                    strSQL+=" fe_asiTipDocCon,co_tipDocCon,nd_valApl,nd_valCon,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                    strSQL+=", " + INT_COD_TIP_DOC_REC;//co_tipDoc
                    strSQL+=", " + intCodDocRec;//co_doc
                    strSQL+=", " + intUltReg;//co_reg
                    strSQL+=", " + txtCodPrv.getText() + "";//co_cli
                    strSQL+=", Null";//co_banChq
                    strSQL+=", Null";//tx_numCtaChq
                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC)) + "";//tx_numChq
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_recChq
                    strSQL+=", '" + objUti.formatearFecha(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_venChq
                    strSQL+=", " + bdeValFacPrv + "";//nd_monChq
                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER)) + "";//tx_numSer
                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_AUT)) + "";//tx_numAutSRI
                    strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_CAD)) + "";//tx_fecCad
                    strSQL+=", Null";//tx_obs1
                    strSQL+=", Null";//tx_obs2
                    strSQL+=", 'A'";//st_reg
                    if(bdeValTotFacPrv.compareTo(bdeValTotDoc)<=0)
                        strSQL+=",'S'";//st_asiDocRel
                    else
                        strSQL+=",'N'";//st_asiDocRel
                    strSQL+="," + bdeValFacPrv + "";//nd_valTotAsi
                    strSQL+=",Null";//fe_asiTipDocCon
                    strSQL+="," + txtCodTipDoc.getText() + "";//co_tipDocCon
                    strSQL+=",0";//nd_valApl
                    strSQL+=",0";//nd_valCon
                    strSQL+=",'I'";//st_regRep
                    strSQL+=");";

                    strSQL+="INSERT INTO tbr_detRecDocCabMovInv(";
                    strSQL+="co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRel,co_locRel,";
                    strSQL+=" co_tipDocRel,co_docRel,nd_valAsi,st_reg,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+="," + objParSis.getCodigoLocal();//co_loc
                    strSQL+="," + INT_COD_TIP_DOC_REC;//co_tipDoc
                    strSQL+="," + intCodDocRec;//co_doc
                    strSQL+="," +intUltReg;//co_reg
                    strSQL+="," + objParSis.getCodigoEmpresa();//co_empRel
                    strSQL+="," + objParSis.getCodigoLocal();//co_locRel
                    strSQL+="," + txtCodTipDoc.getText();//co_tipDocRel
                    strSQL+="," + txtCodDoc.getText();//co_docRel
                    strSQL+="," + bdeValFacPrv;//nd_valAsi
                    strSQL+=",'A'";//st_reg
                    strSQL+=",'I'";//st_regRep
                    strSQL+=");";
                    strSQLUpd+=strSQL;
                    intUltReg++;
                }

                System.out.println("insertarDetRecDoc: " + strSQL);
                stm.executeUpdate(strSQLUpd);
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
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                strSQL+=";";
                strSQL+="UPDATE tbm_cabRecDoc";
                strSQL+=" SET st_reg='E' FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabRecDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_detRecDoc AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbr_detRecDocCabMovInv AS a3";
                strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc ";
                strSQL+="         AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" 	WHERE a3.co_empRel=" + rstCab.getString("co_emp") + "";
                strSQL+=" 	AND a3.co_locRel=" + rstCab.getString("co_loc") + "";
                strSQL+=" 	AND a3.co_tipDocRel=" + rstCab.getString("co_tipDoc") + "";
                strSQL+=" 	AND a3.co_docRel=" + rstCab.getString("co_doc") + "";
                strSQL+=" 	AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabRecDoc.co_emp=x.co_emp AND tbm_cabRecDoc.co_loc=x.co_loc ";
                strSQL+=" AND tbm_cabRecDoc.co_tipDoc=x.co_tipDoc AND tbm_cabRecDoc.co_doc=x.co_doc";
                strSQL+=";";
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
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                strSQL+=";";
                strSQL+="UPDATE tbm_cabRecDoc";
                strSQL+=" SET st_reg='I' FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabRecDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_detRecDoc AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbr_detRecDocCabMovInv AS a3";
                strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc ";
                strSQL+="         AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" 	WHERE a3.co_empRel=" + rstCab.getString("co_emp") + "";
                strSQL+=" 	AND a3.co_locRel=" + rstCab.getString("co_loc") + "";
                strSQL+=" 	AND a3.co_tipDocRel=" + rstCab.getString("co_tipDoc") + "";
                strSQL+=" 	AND a3.co_docRel=" + rstCab.getString("co_doc") + "";
                strSQL+=" 	AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_cabRecDoc.co_emp=x.co_emp AND tbm_cabRecDoc.co_loc=x.co_loc ";
                strSQL+=" AND tbm_cabRecDoc.co_tipDoc=x.co_tipDoc AND tbm_cabRecDoc.co_doc=x.co_doc";
                strSQL+=";";
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
            strTelPrv="";
            strCiuPrv="";
            txtRucPrv.setText("");
            strCodCatTipDoc="";
            txtCnfNumRuc.setText("");
            strNumSerFacPrv="";
            strNumAutSriFacPrv="";
            strNumFecCadFacPrv="";
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            objTblMod.removeAllRows();
            objTblModMot.removeAllRows();
            objTblModForPag.removeAllRows();
            objTblModFacPrv.removeAllRows();
            txtCodForPag.setText("");
            txtNomForPag.setText("");
            txtNumForPag.setText("");
            txtTipForPag.setText("");
            txtNumCot.setText("");
            objAsiDia.inicializar();
            txaObs1.setText("");
            txaObs2.setText("");
            txtCodBen.setText("");
            txtNomBen.setText("");
            txtAliCom.setText("");
            txtNomCom.setText("");
            txtSub.setText("");
            txtIva.setText("");
            txtTot.setText("");
            intNumRegFacPrv=0;
            intNumRegMotDoc=0;
            txtCodCom.setText("");
            chkEmiChqAntRecFacPrv.setSelected(false);
            chkNecMarLis.setSelected(false);
            cboNotPed_PedEmb.setSelectedIndex(0);
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
                    strSQL+=", CASE WHEN a1.st_docNecMarLis IS NULL THEN 'N'";
                    strSQL+="	WHEN a1.st_docNecMarLis='' THEN 'N'";
                    strSQL+=" 	ELSE a1.st_docNecMarLis END AS st_docNecMarLis";
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
                    strSQL+=", CASE WHEN a1.st_docNecMarLis IS NULL THEN 'N'";
                    strSQL+="	WHEN a1.st_docNecMarLis='' THEN 'N'";
                    strSQL+=" 	ELSE a1.st_docNecMarLis END AS st_docNecMarLis";
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
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    if(  (rst.getObject("st_docNecMarLis")==null?"":rst.getString("st_docNecMarLis")).equals("S")  ){
                        chkNecMarLis.setVisible(true);
                        chkEmiChqAntRecFacPrv.setVisible(false);
                    }
                    else{
                        chkEmiChqAntRecFacPrv.setVisible(true);
                        chkNecMarLis.setVisible(false);
                    }
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBen(int intTipBus){
        boolean blnRes=true;
        try{
            //Validar que se pueda seleccionar el "Beneficiario" sálo si se seleccioná el "Proveedor".
            if (txtCodPrv.getText().equals("")){
                txtCodBen.setText("");
                txtNomBen.setText("");
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Proveedor</FONT>.<BR>Escriba o seleccione un proveedor y vuelva a intentarlo.</HTML>");
                txtCodPrv.requestFocus();
            }
            else{
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_reg, a1.tx_benChq, a1.st_reg";
                strSQL+=" FROM tbm_benChq AS a1";
//                strSQL+=" INNER JOIN tbm_cabMovInv AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a1.co_reg=a2.co_ben";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a3";
//                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc ";
//                strSQL+=" AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli=" + txtCodPrv.getText();
                strSQL+=" AND a1.st_reg IN ('A','P')";
                //strSQL+=" AND a3.st_reg IN('A','C') AND a3.st_autpag='S'";
                strSQL+=" GROUP BY a1.co_reg, a1.tx_benChq, a1.st_reg";
                strSQL+=" ORDER BY a1.co_reg";
                vcoBen.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoBen.setCampoBusqueda(1);
                        vcoBen.show();
                        if (vcoBen.getSelectedButton()==vcoBen.INT_BUT_ACE)
                        {
                            txtCodBen.setText(vcoBen.getValueAt(1));
                            txtNomBen.setText(vcoBen.getValueAt(2));
                            actualizarGlo();
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        System.out.println("codigo beneficiario: " + txtCodBen.getText());
                        if (vcoBen.buscar("a1.co_reg", txtCodBen.getText()))
                        {
                            txtCodBen.setText(vcoBen.getValueAt(1));
                            txtNomBen.setText(vcoBen.getValueAt(2));
                            actualizarGlo();
                        }
                        else
                        {
                            vcoBen.setCampoBusqueda(0);
                            vcoBen.setCriterio1(11);
                            vcoBen.cargarDatos();
                            vcoBen.show();
                            if (vcoBen.getSelectedButton()==vcoBen.INT_BUT_ACE)
                            {
                                txtCodBen.setText(vcoBen.getValueAt(1));
                                txtNomBen.setText(vcoBen.getValueAt(2));
                                actualizarGlo();
                            }
                            else
                            {
                                txtCodBen.setText(strCodBen);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoBen.buscar("a1.tx_benChq", txtNomBen.getText()))
                        {
                            txtCodBen.setText(vcoBen.getValueAt(1));
                            txtNomBen.setText(vcoBen.getValueAt(2));
                            actualizarGlo();
                        }
                        else
                        {
                            vcoBen.setCampoBusqueda(1);
                            vcoBen.setCriterio1(11);
                            vcoBen.cargarDatos();
                            vcoBen.show();
                            if (vcoBen.getSelectedButton()==vcoBen.INT_BUT_ACE)
                            {
                                txtCodBen.setText(vcoBen.getValueAt(1));
                                txtNomBen.setText(vcoBen.getValueAt(2));
                                actualizarGlo();
                            }
                            else
                            {
                                txtNomBen.setText(strNomBen);
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
            arlCam.add("a1.tx_tel");
            arlCam.add("a1.tx_nomCiu");
            arlCam.add("a1.tx_numSerFacPrv");
            arlCam.add("a1.tx_numAutSriFacPrv");
            arlCam.add("a1.tx_fecCadFacPrv");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            arlAli.add("Teléfono");
            arlAli.add("Ciudad");
            arlAli.add("Serie");
            arlAli.add("Autorización");
            arlAli.add("Caducidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel, a2.tx_deslar AS tx_nomCiu";
            strSQL+=", a1.tx_numSerFacPrv, a1.tx_numAutSriFacPrv, a1.tx_fecCadFacPrv";
            strSQL+=" FROM tbm_cli AS a1 LEFT OUTER JOIN tbm_ciu AS a2";
            strSQL+=" ON a1.co_ciu=a2.co_ciu AND a2.st_reg='A'";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_prv='S'";
            strSQL+=" AND a1.st_reg='A' ";



            //Ocultar columnas.
            int intColOcu[]=new int[6];
            intColOcu[0]=4;
            intColOcu[1]=5;
            intColOcu[2]=6;
            intColOcu[3]=7;
            intColOcu[4]=8;
            intColOcu[5]=9;
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
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
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
                        //blnRes=objTooBar.insertar();
                        //break;
                       if (objTooBar.beforeInsertar())
                       {  if (objTooBar.insertar())
                          {  if (objTooBar.afterInsertar())
                             {
                                blnRes = true;
                             }
                          }
                       }
                       break;
                    case 'm': //Modificar
                        //blnRes=objTooBar.modificar();
                        //break;
                       if (objTooBar.beforeModificar())
                       {  if (objTooBar.modificar())
                          {  if (objTooBar.afterModificar())
                             {
                                blnRes = true;
                             }
                          }
                       }
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
                    //objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
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
            //objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDat, INT_TBL_DAT_CHK, INT_TBL_DAT_COD_TIP_RET, INT_TBL_DAT_ABO_DOC);
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
        strAux+=txtDesCorTipDoc.getText() + " # " + txtNumDoc.getText();
        strAux+="; Proveedor: " + txtDesLarPrv.getText();
        objAsiDia.setGlosa(strAux);
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
                case INT_TBL_GRL_DAT_DES:
                    strMsg="Descripción";
                    break;
                case INT_TBL_GRL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_GRL_DAT_PRE:
                    strMsg="Precio unitario";
                    break;
                    case INT_TBL_GRL_DAT_SUB:
                    strMsg="Subtotal";
                    break;
                case INT_TBL_GRL_DAT_EST_IVA:
                    strMsg="Lleva IVA";
                    break;
                    case INT_TBL_GRL_DAT_IVA:
                    strMsg="Valor de IVA";
                    break;
                case INT_TBL_GRL_DAT_TOT:
                    strMsg="Total";
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
    private class ZafMouMotAdaMot extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblMot.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_MOT_DAT_COD_MOT:
                    strMsg="Código del motivo";
                    break;
                case INT_TBL_MOT_DAT_DES_COR_MOT:
                    strMsg="Descripción corta del motivo";
                    break;
                case INT_TBL_MOT_DAT_DES_LAR_MOT:
                    strMsg="Descripción larga del motivo";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblMot.getTableHeader().setToolTipText(strMsg);
        }
    }



    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaForPag extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblForPag.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_FOR_PAG_DAT_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_FOR_PAG_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_FOR_PAG_DAT_COD_TIP_RET:
                    strMsg="Código del tipo de retención";
                    break;
                case INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET:
                    strMsg="Descripción corta del tipo de retención";
                    break;
                case INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET:
                    strMsg="Descripción larga del tipo de retención";
                    break;
                case INT_TBL_FOR_PAG_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_FOR_PAG_DAT_APL:
                    strMsg="Aplicado a";
                    break;
                    case INT_TBL_FOR_PAG_DAT_EST_APL:
                    strMsg="Aplicado a";
                    break;
                case INT_TBL_FOR_PAG_DAT_BAS_IMP:
                    strMsg="Base imponible";
                    break;
                case INT_TBL_FOR_PAG_DAT_COD_SRI:
                    strMsg="Código del SRI";
                    break;
                case INT_TBL_FOR_PAG_DAT_VAL_RET:
                    strMsg="Valor a retener";
                    break;
                case INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV:
                    strMsg="Número de registro que lo generó desde el tab de Facturas de Proveedores";
                    break;


                default:
                    strMsg="";
                    break;
            }
            tblForPag.getTableHeader().setToolTipText(strMsg);
        }
    }




    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaFacPrv extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblFacPrv.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_FAC_PRV_DAT_NUM_FAC:
                    strMsg="Número de factura";
                    break;
                case INT_TBL_FAC_PRV_DAT_FEC_FAC:
                    strMsg="Fecha de la factura(dd/MM/yyyy)";
                    break;
                case INT_TBL_FAC_PRV_DAT_VAL_FAC:
                    strMsg="Valor de la factura";
                    break;
                case INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA:
                    strMsg="Valor de la factura sin iva";
                    break;
                case INT_TBL_FAC_PRV_DAT_NUM_SER:
                    strMsg="Número de serie";
                    break;
                case INT_TBL_FAC_PRV_DAT_NUM_AUT:
                    strMsg="Número de autorización del SRI";
                    break;
                case INT_TBL_FAC_PRV_DAT_FEC_CAD:
                    strMsg="Fecha de caducidad(MM/yyyy)";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblFacPrv.getTableHeader().setToolTipText(strMsg);
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
            dtpFecDoc.setEnabled(true);
            txtNumDoc.setEditable(true);
            txaObs1.setEditable(true);
            txaObs2.setEditable(true);
            objAsiDia.setEditable(true);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objAsiDia.setEditable(true);
            objTblModMot.setModoOperacion(objTblMod.INT_TBL_INS);

            switch(intTipModDoc){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            txtCodPrv.setEditable(false);
                            txtDesLarPrv.setEditable(false);
                            butBen.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objAsiDia.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            objTblModMot.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            objTblModForPag.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            txtCodForPag.setEditable(false);
                            txtNomForPag.setEditable(false);
                            butForPag.setEnabled(false);


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
                        txtNumDoc.setEditable(false);
                        txaObs1.setEditable(false);
                        txaObs2.setEditable(false);
                        objAsiDia.setEditable(false);
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        objTblModMot.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        objTblModForPag.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        txtCodForPag.setEditable(false);
                        txtNomForPag.setEditable(false);
                        butForPag.setEnabled(false);
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
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            objTblModMot.setModoOperacion(objTblMod.INT_TBL_EDI);
                            objTblModForPag.setModoOperacion(objTblMod.INT_TBL_EDI);
                            objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_EDI);
                        }
                        else{//si el documento está impreso no se permite modificar
                            dtpFecDoc.setEnabled(false);
                            txtCodPrv.setEditable(false);
                            txtDesLarPrv.setEditable(false);
                            butBen.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            txtNumDoc.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objAsiDia.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            objTblModMot.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            objTblModForPag.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            txtCodForPag.setEditable(false);
                            txtNomForPag.setEditable(false);
                            butForPag.setEnabled(false);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else
                                mostrarMsgInf("<HTML>El documento consultado no se puede modificar porque ya está impreso.</HTML>");
                        }
                    }
                    break;
                case 3://modificación completa, la modificación de la fecha dependerá de si se cuenta con este permiso
                    dtpFecDoc.setEnabled(true);
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                    objTblModMot.setModoOperacion(objTblMod.INT_TBL_EDI);
                    objTblModForPag.setModoOperacion(objTblMod.INT_TBL_EDI);
                    objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_EDI);
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
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresiï¿½n directa.
     * <LI>1: Impresiï¿½n directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {

            String strRutRpt, strNomRpt;
            int i, intNumTotRpt;
            boolean blnRes=true;
            String strSQLRep="", strSQLSubRep="";
            String strFecHorSer="";
            //Inicializar los parametros que se van a pasar al reporte.
            java.util.Map mapPar=new java.util.HashMap();

            Connection conIns;
            try
            {
                conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                try
                {
                    if(conIns!=null)
                    {
                        ///objRptSis.cargarListadoReportes(conCab);
                        objRptSis.cargarListadoReportes();
                        objRptSis.show();
                        if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
                        {
                            //Obtener la fecha y hora del servidor.
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            if (datFecAux==null)
                                return false;
                            strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                            datFecAux=null;

                            intNumTotRpt=objRptSis.getNumeroTotalReportes();
                            for (i=0;i<intNumTotRpt;i++)
                            {
                                if (objRptSis.isReporteSeleccionado(i))
                                {
                                    switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                    {
                                        case 149:
                                                strRutRpt=objRptSis.getRutaReporte(i);
                                                strNomRpt=objRptSis.getNombreReporte(i);

                                                strSQL="";
                                                strSQL+=" SELECT a3.co_emp, a3.tx_nom AS tx_nomEmp, a3.tx_dir AS tx_dirEmp";
                                                strSQL+=" , a3.tx_ruc AS tx_rucEmp, a4.tx_desLar AS tx_nomCiu, a5.tx_desLar AS tx_nomPai";
                                                strSQL+=" , a8.tx_nom AS tx_nomPrg, a1.ne_numDoc, a1.co_cli, a1.tx_nomCli, a1.co_ben";
                                                strSQL+=" , a1.tx_benChq, a1.fe_doc, a1.co_com, a6.tx_usr AS tx_usrCom, a6.tx_nom AS tx_nomCom";
                                                //strSQL+=" , a1.co_usrIng, a9.tx_usr AS tx_usrIng, a9.tx_nom AS tx_nomIng";
                                                //strSQL+=" , a1.co_usrMod, a10.tx_usr AS tx_usrMod, a10.tx_nom AS tx_nomMod";
                                                strSQL+=" , CASE WHEN a1.nd_sub IS NULL THEN 0 ELSE a1.nd_sub END AS nd_sub";
                                                strSQL+=" , CASE WHEN a1.nd_valIva IS NULL THEN 0 ELSE a1.nd_valIva END AS nd_valIva";
                                                strSQL+=" , CASE WHEN a1.nd_tot IS NULL THEN 0 ELSE a1.nd_tot END AS nd_tot";
                                                strSQL+=" , a11.tx_glo, a1.tx_obs2";
                                                strSQL+=" FROM (tbm_cabMovInv AS a1  INNER JOIN tbm_emp AS a3 ON a1.co_emp=a3.co_emp)";
                                                strSQL+=" INNER JOIN tbm_loc AS a2";
                                                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                                                strSQL+=" INNER JOIN tbm_ciu AS a4";
                                                strSQL+=" ON a2.co_ciu=a4.co_ciu";
                                                strSQL+=" INNER JOIN tbm_pai AS a5";
                                                strSQL+=" ON a4.co_pai=a5.co_pai";
                                                strSQL+=" LEFT OUTER JOIN tbm_usr AS a6";
                                                strSQL+=" ON a1.co_com=a6.co_usr";
                                                strSQL+=" INNER JOIN tbm_cabDia AS a11";
                                                strSQL+=" ON a1.co_emp=a11.co_emp AND a1.co_loc=a11.co_loc AND a1.co_tipDoc=a11.co_tipDoc AND a1.co_doc=a11.co_dia";
                                                strSQL+=" LEFT OUTER JOIN tbm_mnusis AS a8";
                                                strSQL+=" ON a1.co_mnu=a8.co_mnu";
                                                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + "";
                                                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                                                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                                                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                                                strSQLRep=strSQL;

                                                strSQL="";
                                                strSQL+="select a3.tx_codcta, a3.tx_deslar, round(a2.nd_mondeb,2) as nd_mondeb, round(a2.nd_monhab,2) as nd_monhab";
                                                strSQL+=" from tbm_cabdia as a1, tbm_detdia as a2, tbm_placta as a3";
                                                strSQL+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc";
                                                strSQL+=" and a1.co_tipdoc=a2.co_tipdoc";
                                                strSQL+=" and a1.co_dia=a2.co_dia";
                                                strSQL+=" and a2.co_emp=a3.co_emp";
                                                strSQL+=" and a2.co_cta=a3.co_cta";
                                                strSQL+=" and a1.co_emp=" + rstCab.getString("co_emp") + "";
                                                strSQL+=" and a1.co_loc=" + rstCab.getString("co_loc") + "";
                                                strSQL+=" and a1.co_tipdoc=" + rstCab.getString("co_tipDoc") + "";
                                                strSQL+=" and a1.co_dia=" + rstCab.getString("co_doc") + "";
                                                strSQLSubRep=strSQL;


                                                mapPar.put("strSQLRep", strSQLRep);
                                                mapPar.put("strSQLSubRep", strSQLSubRep);
                                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                                mapPar.put("strCamAudRpt", "Ing:" + (rstCab.getString("tx_nomUsrIng") + " / Mod:" + rstCab.getString("tx_nomUsrMod") + " / Imp:" + objParSis.getNombreUsuario()) + "      Imp:" + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "");
                                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                        case 260:
                                                strRutRpt=objRptSis.getRutaReporte(i);
                                                strNomRpt=objRptSis.getNombreReporte(i);
                                                mapPar.put("CodEmp", rstCab.getInt("co_emp"));
                                                mapPar.put("CodLoc", rstCab.getInt("co_loc"));
                                                mapPar.put("CodTipDoc", rstCab.getInt("co_tipDoc"));
                                                mapPar.put("CodDoc", rstCab.getInt("co_doc"));
                                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    conIns.close();
                    conIns=null;
                }
                catch (Exception e)
                {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, e);
                }
            }
            catch(SQLException ex)
            {
                System.out.println("Error al conectarse a la base");
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, ex);
            }
            return blnRes;
    }




    public String retGlo(int codDoc){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                que="";
                que+="select tx_glo from tbm_cabdia";
                que+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                que+=" and co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and co_tipdoc=" + txtCodTipDoc.getText() + "";
                que+=" and co_dia=" + txtCodDoc.getText() + "";
                System.out.println("glosa:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next())
                {
                    auxTipDoc=rstTipDoc.getString("tx_glo");
                }
                rstTipDoc.close();
                rstTipDoc = null;

                stmTipDoc.close();
                stmTipDoc = null;
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




//funciones para enviar parametros para el reporte impreso
public String retNomEmp(int codEmp)
{
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                que="";
                que+=" select tx_nom from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
                System.out.println("nombre de la empresa:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
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



public String retDirEmp(int codEmp)
{
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                que="";
                que+=" select tx_dir from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
                System.out.println("direccion de la empresa:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_dir");
                }
                rstTipDoc.close();
               rstTipDoc = null;

               stmTipDoc.close();
               stmTipDoc = null;
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



    public String retRucEmp(int codEmp)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                que="";
                que+=" select tx_ruc from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
                System.out.println("ruc de la empresa:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next())
                {
                    auxTipDoc=rstTipDoc.getString("tx_ruc");
                }
                rstTipDoc.close();
               rstTipDoc = null;

               stmTipDoc.close();
               stmTipDoc = null;
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


public String retNomUsr(int CodUsr)
{
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                que="";
                que+=" select tx_nom from tbm_usr";
                que+=" where co_usr=" + CodUsr + "";
                System.out.println("---Nombre del Ususario: " + que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
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


    private void calcularTotal(int fila){
        int i=fila;
        BigDecimal bdeCan=new BigDecimal("0");
        BigDecimal bdePre=new BigDecimal("0");
        //BigDecimal bdePorIva=new BigDecimal("0.12");
        BigDecimal bdePorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        BigDecimal bdeIva=new BigDecimal("0");
        BigDecimal bdeSub=new BigDecimal("0");
        BigDecimal bdeTot=new BigDecimal("0");

        BigDecimal bdeTotSub=new BigDecimal("0");
        BigDecimal bdeTotIva=new BigDecimal("0");


        try{
            bdeCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CAN)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CAN).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CAN).toString()));
            bdePre=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_PRE)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_PRE).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_PRE).toString()));
            bdeSub=bdeCan.multiply(bdePre);


            objTblMod.setValueAt(bdeSub, i, INT_TBL_GRL_DAT_SUB);

            if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_EST_IVA)){
                bdeIva=bdeSub.multiply(bdePorIva);
            }
            else{
                bdeIva=new BigDecimal("0");
            }
            objTblMod.setValueAt(bdeIva, i, INT_TBL_GRL_DAT_IVA);


            bdeTot=bdeSub.add(bdeIva);
            objTblMod.setValueAt(bdeTot, i, INT_TBL_GRL_DAT_TOT);


            bdeIva=new BigDecimal("0");
            bdeSub=new BigDecimal("0");

            for(int j=0; j<objTblMod.getRowCountTrue(); j++){
                bdeCan=new BigDecimal(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_CAN)==null?"0":(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_CAN).toString().equals("")?"0":objTblMod.getValueAt(j, INT_TBL_GRL_DAT_CAN).toString()));
                bdePre=new BigDecimal(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_PRE)==null?"0":(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_PRE).toString().equals("")?"0":objTblMod.getValueAt(j, INT_TBL_GRL_DAT_PRE).toString()));

                bdeSub=objUti.redondearBigDecimal(bdeCan.multiply(bdePre), objParSis.getDecimalesMostrar());
                bdeTotSub=objUti.redondearBigDecimal(bdeTotSub.add(bdeSub), objParSis.getDecimalesMostrar());
                if(objTblMod.isChecked(j, INT_TBL_GRL_DAT_EST_IVA)){
                    bdeIva=objUti.redondearBigDecimal(bdeSub.multiply(bdePorIva), objParSis.getDecimalesMostrar());
                }
                else{
                    bdeIva=new BigDecimal("0");
                }
                bdeTotIva=objUti.redondearBigDecimal(bdeTotIva.add(bdeIva), objParSis.getDecimalesMostrar());
            }
            txtSub.setText(bdeTotSub.toString());
            txtIva.setText(bdeTotIva.toString());
            txtTot.setText("" + objUti.redondearBigDecimal(bdeTotSub.add(bdeTotIva), objParSis.getDecimalesMostrar())    );
            bdeTotSub=new BigDecimal("0");
            bdeTotIva=new BigDecimal("0");

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeEmptyRows();
            if(objTooBar.getEstado()=='n')
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);



            if(objParSis.getCodigoMenu()==1985){
                txtCodForPag.setText("1");
                mostrarVenConForPag(1);
            }




            if(objTblModMot.getRowCountTrue()>0){
                if(objTblModFacPrv.getRowCountTrue()>0)
                    cargarFormaPagoFacturaProveedor();
                else
                    cargarFormaPago();
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }



    private void generarDiarioDocumento(){
        try{
            ///para generar el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();
            cargarCuentasContables();
            if(objParSis.getCodigoMenu()==1985){//CxP
                //total
                if( new BigDecimal(txtTot.getText()).compareTo(BigDecimal.ZERO)>0  ){
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaTot);//intCodCtaTot
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaTot);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaTot);
                    vecRegDia.add(INT_VEC_DIA_DEB, "" + txtTot.getText());
                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }
                //total
                if( new BigDecimal(txtTot.getText()).compareTo(BigDecimal.ZERO)>0  ){
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaTot);//intCodCtaTot
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaTot);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaTot);
                    vecRegDia.add(INT_VEC_DIA_DEB, "0");
                    vecRegDia.add(INT_VEC_DIA_HAB, "" + txtTot.getText());
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }
            }
            else{
                if(txtCodTipDoc.getText().equals("246")){
                    //iva
                    if( new BigDecimal(txtIva.getText()).compareTo(BigDecimal.ZERO)>0  ){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaIva);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaIva);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaIva);
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + txtIva.getText());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    //subtotal
                    if( new BigDecimal(txtSub.getText()).compareTo(BigDecimal.ZERO)>0  ){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaSubTot);
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaSubTot);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaSubTot);
                        vecRegDia.add(INT_VEC_DIA_DEB, "" +  txtSub.getText()   );
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);                        
                    }
                    //total
                    if( new BigDecimal(txtTot.getText()).compareTo(BigDecimal.ZERO)>0  ){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaTot);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaTot);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaTot);
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + txtTot.getText());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                }
                else{//tipo de documento OPCOLO
                    //iva
                    if( new BigDecimal(txtIva.getText()).compareTo(BigDecimal.ZERO)>0  ){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaIva);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaIva);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaIva);
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + txtIva.getText());
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    //subtotal
                    if( new BigDecimal(txtSub.getText()).compareTo(BigDecimal.ZERO)>0  ){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_DEB, "" +  txtSub.getText()   );
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    //total
                    if( new BigDecimal(txtTot.getText()).compareTo(BigDecimal.ZERO)>0  ){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaTot);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaTot);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaTot);
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + txtTot.getText());
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                }
                
                

            }
            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)2);
            actualizarGlo();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }


    private void generarDiarioDocumentoTC(){
        int intCodCtaDebAsiDia=-1, intCodCtaHabAsiDia=-1;
        try{
            ///para generar el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();

            switch(objParSis.getCodigoEmpresa()){
                case 1://tuval
                    intCodCtaDebAsiDia=3225;
                    intCodCtaHabAsiDia=992;
                    break;
                case 2://castek
                    intCodCtaDebAsiDia=1324;
                    intCodCtaHabAsiDia=178;
                    break;
                case 4://dimulti
                    intCodCtaDebAsiDia=2355;
                    intCodCtaHabAsiDia=1771;
                    break;
            }

            //total
            if( new BigDecimal(txtTot.getText()).compareTo(BigDecimal.ZERO)>0  ){
                //cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaDebAsiDia);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "");
                vecRegDia.add(INT_VEC_DIA_DEB, "" +  txtTot.getText()   );
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaHabAsiDia);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + txtTot.getText());
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }

            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)2);
            actualizarGlo();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConMot()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mot");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código.");
            arlAli.add("Tip.Mot.");
            arlAli.add("Motivo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("234");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" select a1.co_mot, a1.tx_desCor, a1.tx_desLar";
            strSQL+=" FROM tbm_motDoc AS a1";
            strSQL+=" LEFT OUTER JOIN (  (tbm_polRet AS a2 INNER JOIN tbm_tipPer AS a4";
            strSQL+=" 			ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper AND a4.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
            strSQL+=" 				INNER JOIN tbm_emp AS a5";
            strSQL+=" 				ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer AND a5.st_reg NOT IN('E','I'))";
            strSQL+=" 	       LEFT OUTER JOIN tbm_tipPer AS a3";
            strSQL+=" 	       ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper AND a3.st_reg NOT IN('E','I')";
            strSQL+=" 	       INNER JOIN tbm_cli AS a6";
            strSQL+=" 	       ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer AND a6.st_reg NOT IN('E','I'))";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.st_reg NOT IN('E','I')";
            //Ocultar columnas.
            vcoMot=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de motivos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMot.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoMot.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            vcoMot.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);
            vcoMot.setCampoBusqueda(2);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConRet()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipRet");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.nd_porRet");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código.");
            arlAli.add("Tip.Ret.");
            arlAli.add("Retención");
            arlAli.add("Porcentaje");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("234");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="select a1.co_tipRet, a1.tx_desCor, a1.tx_desLar, a1.nd_porRet,";
            strSQL+=" CASE WHEN a1.tx_aplRet='S' THEN 'Subtotal'";
            strSQL+=" 	WHEN a1.tx_aplRet='I' THEN 'Iva'";
            strSQL+=" 	WHEN a1.tx_aplRet='O' THEN 'Otros' END AS tx_aplRet";
            strSQL+=" FROM tbm_cabTipRet AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND a1.st_reg NOT IN('E','I')";
            //Ocultar columnas.
            vcoRet=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Forma de Pago", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoRet.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT, vcoRet.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoRet.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            vcoRet.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);
            vcoRet.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT, vcoRet.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoRet.setCampoBusqueda(2);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean cargarFormaPago(){//0->cargar el valor predeterminado para el proveedor; 1->carga todos las tipos de forma de pago
        boolean blnRes=true;

        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(objTblModMot.getRowCountTrue()>=1){
                    if(cargarFormaPagoRetenciones()){
                        if(cargarPago()){

                        }
                    }
                }
                else{
                    if(cargarPago()){

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
        return blnRes;
    }



    private boolean cargarFormaPagoRetenciones(){
        boolean blnRes=true;
        String strApl="";
        BigDecimal bdeSub=new BigDecimal("0");
        BigDecimal bdeIva=new BigDecimal("0");
        BigDecimal bdeTotSub=new BigDecimal("0");
        BigDecimal bdeTotIva=new BigDecimal("0");
        BigDecimal bdePorRet=new BigDecimal("0");
        BigDecimal bdeValRet=new BigDecimal("0");
        BigDecimal bdeValCer=new BigDecimal("0");
        String strCodMotSelUsr="";


        BigDecimal bdeIvaFacPrv=new BigDecimal("0");
        BigDecimal bdeTotFacPrv=new BigDecimal("0");
        BigDecimal bdeFacPrvSinIva=new BigDecimal("0");
        //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
        BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        try{
            if(con!=null){
                vecDatForPag.clear();
                stm=con.createStatement();
                bdeSub=new BigDecimal(BigInteger.ZERO);
                bdeIva=new BigDecimal(BigInteger.ZERO);
                bdeTotSub=new BigDecimal(BigInteger.ZERO);
                bdeTotIva=new BigDecimal(BigInteger.ZERO);

                if(objTblModMot.getRowCountTrue()>1){
                    for(int i=(objTblMod.getRowCountTrue()-2); i>=0; i--){
                        strCodMotSelUsr=objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-2), INT_TBL_MOT_DAT_COD_MOT)==null?"0":(objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-2), INT_TBL_MOT_DAT_COD_MOT).toString().equals("")?"0":objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-2), INT_TBL_MOT_DAT_COD_MOT).toString());
                        bdeSub=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString()));
                        bdeTotSub=bdeTotSub.add(bdeSub);
                        bdeIva=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA).toString()));
                        bdeTotIva=bdeTotIva.add(bdeIva);
                    }
                    strSQL="";
                    strSQL+=" select a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    strSQL+=",'" + txtCodForPag.getText() + "' AS co_forPag, '" + txtNomForPag.getText() + "' AS tx_des";
                    strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                    strSQL+=" FROM tbm_motDoc AS a1";
                    strSQL+=" LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                    strSQL+=" 			ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                    strSQL+=" 				LEFT OUTER JOIN tbm_emp AS a5";
                    strSQL+=" 				ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_tipPer AS a3";
                    strSQL+=" 	       ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_cli AS a6";
                    strSQL+=" 	       ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_forPagCli AS a7 ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_cabForPag AS a8 ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                    strSQL+=" 	       )";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND CURRENT_DATE between a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                    strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                    strSQL+="                     AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                    strSQL+=" AND a1.co_mot=" + strCodMotSelUsr + "";
                    strSQL+=" AND a6.co_cli=" + txtCodPrv.getText() + "";
                    strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    strSQL+=" , a9.tx_desCor, a9.tx_desLar, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                    strSQL+=" ORDER BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    System.out.println("A-cargarFormaPagoRetenciones: " + strSQL );
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        bdePorRet=new BigDecimal("0");
                        bdeValRet=new BigDecimal("0");

                        vecRegForPag=new Vector();
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_LIN, "");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,          "0");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,          objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,      rst.getString("co_tipRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,  rst.getString("tx_desCorRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,      "");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,  rst.getString("tx_desLarRet"));
                        bdePorRet=rst.getBigDecimal("nd_porRet");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_POR_RET,          bdePorRet);
                        strApl=rst.getString("tx_aplRet");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_APL,              strApl.equals("S")?"Subtotal":(strApl.equals("I")?"Iva":(strApl.equals("O")?"Otros":"")));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_EST_APL,          strApl);
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,          strApl.equals("S")?""+bdeTotSub:(strApl.equals("I")?""+bdeTotIva:"0"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_SRI,          rst.getString("tx_codSri"));
                        if(strApl.equals("S"))
                            bdeValRet=bdeTotSub.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                        if(strApl.equals("I"))
                            bdeValRet=bdeTotIva.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_VAL_RET,          objUti.redondearBigDecimal(bdeValRet, objParSis.getDecimalesMostrar()));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV,  "");





                        vecDatForPag.add(vecRegForPag);
                    }

                    bdeSub=new BigDecimal(BigInteger.ZERO);
                    bdeIva=new BigDecimal(BigInteger.ZERO);
                    bdeTotSub=new BigDecimal(BigInteger.ZERO);
                    bdeTotIva=new BigDecimal(BigInteger.ZERO);


                    //para el segundo motivo
                    for(int i=(objTblMod.getRowCountTrue()-1); i>=(objTblMod.getRowCountTrue()-1); i--){
                        strCodMotSelUsr=objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT)==null?"0":(objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT).toString().equals("")?"0":objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT).toString());
                        bdeSub=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString()));
                        bdeTotSub=bdeTotSub.add(bdeSub);
                        bdeIva=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA).toString()));
                        bdeTotIva=bdeTotIva.add(bdeIva);
                    }
                    strSQL="";
                    strSQL+=" select a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    strSQL+=",'" + txtCodForPag.getText() + "' AS co_forPag, '" + txtNomForPag.getText() + "' AS tx_des";
                    strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                    strSQL+=" FROM tbm_motDoc AS a1";
                    strSQL+=" LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                    strSQL+=" 			ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                    strSQL+=" 				LEFT OUTER JOIN tbm_emp AS a5";
                    strSQL+=" 				ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_tipPer AS a3";
                    strSQL+=" 	       ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_cli AS a6";
                    strSQL+=" 	       ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_forPagCli AS a7 ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_cabForPag AS a8 ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                    strSQL+=" 	       )";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND CURRENT_DATE between a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                    strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                    strSQL+="                     AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                    strSQL+=" AND a1.co_mot=" + strCodMotSelUsr + "";
                    strSQL+=" AND a6.co_cli=" + txtCodPrv.getText() + "";
                    strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    strSQL+=" , a9.tx_desCor, a9.tx_desLar, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                    strSQL+=" ORDER BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    System.out.println("B - cargarFormaPagoRetenciones: " + strSQL );
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){

                        bdePorRet=new BigDecimal("0");
                        bdeValRet=new BigDecimal("0");
                        vecRegForPag=new Vector();
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_LIN, "");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,          "0");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,          objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,      rst.getString("co_tipRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,  rst.getString("tx_desCorRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,      "");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,  rst.getString("tx_desLarRet"));
                        bdePorRet=rst.getBigDecimal("nd_porRet");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_POR_RET,          bdePorRet);
                        strApl=rst.getString("tx_aplRet");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_APL,              strApl.equals("S")?"Subtotal":(strApl.equals("I")?"Iva":(strApl.equals("O")?"Otros":"")));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_EST_APL,          strApl);
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,          strApl.equals("S")?""+bdeTotSub:(strApl.equals("I")?""+bdeTotIva:"0"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_SRI,          rst.getString("tx_codSri"));
                        if(strApl.equals("S"))
                            bdeValRet=bdeTotSub.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                        if(strApl.equals("I"))
                            bdeValRet=bdeTotIva.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_VAL_RET,          objUti.redondearBigDecimal(bdeValRet, objParSis.getDecimalesMostrar()));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV,  "");


                        vecDatForPag.add(vecRegForPag);
                    }

                }
                else{


                    bdeSub=new BigDecimal(BigInteger.ZERO);
                    bdeIva=new BigDecimal(BigInteger.ZERO);
                    bdeTotSub=new BigDecimal(BigInteger.ZERO);
                    bdeTotIva=new BigDecimal(BigInteger.ZERO);

                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        strCodMotSelUsr=objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT)==null?"0":(objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT).toString().equals("")?"0":objTblModMot.getValueAt((objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT).toString());
                        bdeSub=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString()));
                        bdeTotSub=bdeTotSub.add(bdeSub);
                        bdeIva=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_IVA).toString()));
                        bdeTotIva=bdeTotIva.add(bdeIva);
                    }

                    strSQL="";
                    strSQL+=" select a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    strSQL+=",'" + txtCodForPag.getText() + "' AS co_forPag, '" + txtNomForPag.getText() + "' AS tx_des";
                    strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                    strSQL+=" FROM tbm_motDoc AS a1";
                    strSQL+=" LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                    strSQL+=" 			ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                    strSQL+=" 				LEFT OUTER JOIN tbm_emp AS a5";
                    strSQL+=" 				ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_tipPer AS a3";
                    strSQL+=" 	       ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_cli AS a6";
                    strSQL+=" 	       ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_forPagCli AS a7 ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                    strSQL+=" 	       LEFT OUTER JOIN tbm_cabForPag AS a8 ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                    strSQL+=" 	       )";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND CURRENT_DATE between a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                    strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                    strSQL+="                     AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                    strSQL+=" AND a1.co_mot=" + strCodMotSelUsr + "";
                    strSQL+=" AND a6.co_cli=" + txtCodPrv.getText() + "";
                    strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    strSQL+=" , a9.tx_desCor, a9.tx_desLar, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                    strSQL+=" ORDER BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                    System.out.println("C-cargarFormaPagoRetenciones: " + strSQL );
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        bdePorRet=new BigDecimal("0");
                        bdeValRet=new BigDecimal("0");

                        vecRegForPag=new Vector();
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_LIN, "");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,          "0");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,          objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,      rst.getString("co_tipRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,  rst.getString("tx_desCorRet"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,      "");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,  rst.getString("tx_desLarRet"));
                        bdePorRet=rst.getBigDecimal("nd_porRet");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_POR_RET,          bdePorRet);
                        strApl=rst.getString("tx_aplRet");
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_APL,              strApl.equals("S")?"Subtotal":(strApl.equals("I")?"Iva":(strApl.equals("O")?"Otros":"")));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_EST_APL,          strApl);
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,          strApl.equals("S")?""+bdeTotSub:(strApl.equals("I")?""+bdeTotIva:"0"));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_SRI,          rst.getString("tx_codSri"));
                        if(strApl.equals("S"))
                            bdeValRet=bdeTotSub.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                        if(strApl.equals("I"))
                            bdeValRet=bdeTotIva.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_VAL_RET,          objUti.redondearBigDecimal(bdeValRet, objParSis.getDecimalesMostrar()));
                        vecRegForPag.add(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV,  "");

                        vecDatForPag.add(vecRegForPag);
                    }
                }

                rst.close();
                rst=null;

                //Asignar vectores al modelo.
                objTblModForPag.setData(vecDatForPag);
                tblForPag.setModel(objTblModForPag);
                vecDatForPag.clear();



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



    private boolean cargarPago(){
        boolean blnRes=true;
        BigDecimal bdeTot=new BigDecimal("0");
        int intValDia=0;
        BigDecimal bdeValCer=new BigDecimal("0");
        BigDecimal bdePorRet=new BigDecimal("0");
        BigDecimal bdeValRet=new BigDecimal("0");
        String strValTipApl="";
        BigDecimal bdeValRetTot=new BigDecimal("0");
        BigDecimal bdeValApl=new BigDecimal("0");
        int intNumDiaForPag=Integer.parseInt(txtNumForPag.getText()==null?"0":(txtNumForPag.getText().equals("")?"0":txtNumForPag.getText()));
        int intTipDiaForPag=Integer.parseInt(txtTipForPag.getText()==null?"0":(txtTipForPag.getText().equals("")?"0":txtTipForPag.getText()));

        int intIndDiaForPag_Db=0;
        getDiaPag_ForPag();

        try{
            if(intNumDiaForPag>0){
                bdeTot=txtTot.getText()==null?bdeValCer:(txtTot.getText().equals("")?bdeValCer:new BigDecimal(txtTot.getText()));
                for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
                    bdePorRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                    if(bdePorRet.compareTo(bdeValCer)>0){
                        bdeValRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                        bdeValRetTot=bdeValRetTot.add(bdeValRet);
                    }
                }
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_INS);
                for(int i=(objTblModForPag.getRowCountTrue()-1); i>=0; i--){
                    bdePorRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                    strValTipApl=objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL)==null?"":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL).toString().equals("")?"":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL).toString());
                    if( (bdePorRet.compareTo(bdeValCer)<=0) &&  (strValTipApl.equals(""))  ){
                        objTblModForPag.removeRow(i);
                    }
                }
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_EDI);
                objTblModForPag.removeEmptyRows();
                bdeValApl=objUti.redondearBigDecimal((bdeTot.subtract(bdeValRetTot)), objParSis.getDecimalesMostrar());
                bdeValApl=(bdeValApl.divide(new BigDecimal("" + intNumDiaForPag), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));
                int intFil=-1;
                int intInicio=0;
                int intFinal=0;
                if(intTipDiaForPag==1){
                    intInicio=0;
                    intFinal=intNumDiaForPag;
                }
                else{
                    intInicio=1;
                    intFinal=intNumDiaForPag+1;
                }


                intIndDiaForPag_Db=0;

                for(int i=intInicio; i<intFinal; i++ ){
//                    intValDia=30;
//                    intValDia=intValDia*i;
                    intValDia=objUti.getIntValueAt(arlDatDiaPag_ForPag, intIndDiaForPag_Db, INT_ARL_DIA_PAG_NUM_DIA_CRE);
                    objTblModForPag.insertRow();
                    intFil=(objTblModForPag.getRowCountTrue()-1);
                    objTblModForPag.setValueAt("" + intValDia, intFil, INT_TBL_FOR_PAG_DAT_DIA_CRE);
                    objTblModForPag.setValueAt(objUti.formatearFecha(fechaVencimientoPago(intValDia,dtpFecDoc.getText()), "yyyy-MM-dd", "dd/MM/yyyy"), intFil, INT_TBL_FOR_PAG_DAT_FEC_VEN);
                    objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_POR_RET);
                    objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_BAS_IMP);
                    objTblModForPag.setValueAt(bdeValApl, intFil, INT_TBL_FOR_PAG_DAT_VAL_RET);
                    intIndDiaForPag_Db++;
                }

                //por si no cuadra la division de los pagos, se coloca la diferencia al ultimo registro
                BigDecimal bdeValDif=new BigDecimal("0");
                BigDecimal bdeValUltPag=new BigDecimal("0");
                bdeValRetTot=new BigDecimal("0");
                for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
                    bdeValRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                    bdeValRetTot=bdeValRetTot.add(bdeValRet);
                }
                if(bdeTot.compareTo(bdeValRetTot)==0){
                }
                else{
                    bdeValDif=bdeTot.subtract(bdeValRetTot);
                    bdeValUltPag=new BigDecimal(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                    objTblModForPag.setValueAt((bdeValUltPag.add(bdeValDif)), (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET);
                }
                txtForPagDif.setText("" + bdeTot.subtract(bdeValRetTot.add(bdeValDif)));
            }


        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private String fechaVencimientoPago(int intValDia, String fecha){
        String strFecVncPag="";
        Connection conVctPag;
        Statement stmVctPag;
        ResultSet rstVctPag;
        try{
            conVctPag=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conVctPag!=null){
                stmVctPag=conVctPag.createStatement();
                strSQL="";
                strSQL+=" SELECT (CAST('" + objUti.formatearFecha(fecha, "dd/MM/yyyy", "yyyy-MM-dd") + "' AS DATE) + " + intValDia + ") AS fe_ven";
                rstVctPag=stmVctPag.executeQuery(strSQL);
                if(rstVctPag.next()){
                    strFecVncPag=rstVctPag.getString("fe_ven");
                }
                conVctPag.close();
                conVctPag=null;
                stmVctPag.close();
                stmVctPag=null;
                rstVctPag.close();
                rstVctPag=null;
            }

        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecVncPag;
    }



    private boolean cargarFormaPagoFacturaProveedor(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(objTblModMot.getRowCountTrue()>=1){
                    if(cargarFormaPagoRetencionesFacturaProveedor()){
                        if(cargarPagoFacturaProveedor()){

                        }
                    }
                }
                else{
                    if(cargarPagoFacturaProveedor()){

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
        return blnRes;
    }


    private boolean cargarFormaPagoRetencionesFacturaProveedor(){
        boolean blnRes=true;
        String strApl="";
        BigDecimal bdeSub=new BigDecimal("0");
        BigDecimal bdeIva=new BigDecimal("0");
        BigDecimal bdePorRet=new BigDecimal("0");
        BigDecimal bdeValRet=new BigDecimal("0");
        BigDecimal bdeValCer=new BigDecimal("0");


        BigDecimal bdeTotFacPrv=new BigDecimal("0");
        BigDecimal bdeFacPrvSinIva=new BigDecimal("0");
        //BigDecimal bdePorIvaFacPrv=new BigDecimal("1.12");
        BigDecimal bdePorIvaFacPrv=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        BigDecimal bdeSubFacPrv=new BigDecimal("0");
        BigDecimal bdeIvaFacPrv=new BigDecimal("0");





        try{
            if(con!=null){
                for(int i=0;i<objTblModFacPrv.getRowCountTrue(); i++){
                    bdeTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    bdeFacPrvSinIva=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString()));
                    //calcular iva
                    BigDecimal bdeFacIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                    bdeIvaFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeFacIva);
                    bdeIvaFacPrv=objUti.redondearBigDecimal(bdeIvaFacPrv, objParSis.getDecimalesMostrar());
                    objTblModFacPrv.setValueAt(bdeIvaFacPrv, i, INT_TBL_FAC_PRV_DAT_IVA);
                    //calcular subtotal --)
                    bdeSubFacPrv=((bdeTotFacPrv.subtract(bdeFacPrvSinIva)).divide(bdePorIvaFacPrv, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).add(bdeFacPrvSinIva);
                    bdeSubFacPrv=objUti.redondearBigDecimal(bdeSubFacPrv, objParSis.getDecimalesMostrar());
                    objTblModFacPrv.setValueAt(bdeSubFacPrv, i, INT_TBL_FAC_PRV_DAT_SUB);
                }
                if(objTblModMot.getRowCountTrue()<=1){
                    vecDatForPag.clear();
                    stm=con.createStatement();
                    String strCodMotTmp="";
                    for(int i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                        bdeSub=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_SUB)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_SUB).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_SUB).toString()));
                        bdeIva=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA).toString()));
                        strSQL="";
                        strSQL+=" select a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                        strSQL+=",'" + txtCodForPag.getText() + "' AS co_forPag, '" + txtNomForPag.getText() + "' AS tx_des";
                        strSQL+=" , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" FROM tbm_motDoc AS a1";
                        strSQL+=" LEFT OUTER JOIN (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4";
                        strSQL+=" 			ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                        strSQL+=" 				LEFT OUTER JOIN tbm_emp AS a5";
                        strSQL+=" 				ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                        strSQL+=" 	       LEFT OUTER JOIN tbm_tipPer AS a3";
                        strSQL+=" 	       ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                        strSQL+=" 	       LEFT OUTER JOIN tbm_cli AS a6";
                        strSQL+=" 	       ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                        strSQL+=" 	       LEFT OUTER JOIN tbm_forPagCli AS a7 ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli";
                        strSQL+=" 	       LEFT OUTER JOIN tbm_cabForPag AS a8 ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                        strSQL+=" 	       )";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND CURRENT_DATE between a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                        strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                        strSQL+="                     AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                        if(objTblModMot.getRowCountTrue()>0)
                            strCodMotTmp=objTblModMot.getValueAt(0, INT_TBL_MOT_DAT_COD_MOT)==null?"0":(objTblModMot.getValueAt(0, INT_TBL_MOT_DAT_COD_MOT).toString().equals("")?"0":objTblModMot.getValueAt(0, INT_TBL_MOT_DAT_COD_MOT).toString());
                        else
                            strCodMotTmp="0";
                        strSQL+=" AND a1.co_mot=" + strCodMotTmp + "";
                        strSQL+=" AND a6.co_cli=" + txtCodPrv.getText() + "";
                        strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                        strSQL+=" , a9.tx_desCor, a9.tx_desLar, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" ORDER BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                       System.out.println("cargarFormaPagoRetencionesFacturaProveedor: " + strSQL );
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            vecRegFacPrv=new Vector();
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_LIN, "");
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,          "0");//dtpFecDoc.getText()
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,          objUti.formatearFecha(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString(), "dd/MM/yyyy", "dd/MM/yyyy"));
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,      rst.getString("co_tipRet"));
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,  rst.getString("tx_desCorRet"));
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,      "");
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,  rst.getString("tx_desLarRet"));
                            bdePorRet=rst.getBigDecimal("nd_porRet");
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_POR_RET,          bdePorRet);
                            strApl=rst.getString("tx_aplRet");
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_APL,              strApl.equals("S")?"Subtotal":(strApl.equals("I")?"Iva":(strApl.equals("O")?"Otros":"")));
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_EST_APL,          strApl);
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,          strApl.equals("S")?""+bdeSub:(strApl.equals("I")?""+bdeIva:"0"));
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_COD_SRI,          rst.getString("tx_codSri"));
                            if(strApl.equals("S"))
                                bdeValRet=bdeSub.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                            if(strApl.equals("I"))
                                bdeValRet=bdeIva.multiply((bdePorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_VAL_RET,          objUti.redondearBigDecimal(bdeValRet, objParSis.getDecimalesMostrar()));
                            vecRegFacPrv.add(INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV,  "" + objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_REG));



                            vecDatFacPrv.add(vecRegFacPrv);
                        }
                        rst.close();
                        rst=null;
                    }

                    //Asignar vectores al modelo.
                    objTblModForPag.setData(vecDatFacPrv);
                    tblForPag.setModel(objTblModForPag);
                    vecDatFacPrv.clear();
                    stm.close();
                    stm=null;

                    BigDecimal bdeValPndPag=new BigDecimal("0");
                    BigDecimal bdeValRetPag=new BigDecimal("0");
                    BigDecimal bdeSumValRet=new BigDecimal("0");

                    int intRegFacPrv=-1;
                    int intRegForPag=-1;
                    String strPorRetForPag="";
                    for(int h=0; h<objTblModFacPrv.getRowCountTrue(); h++){
                        bdeTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                        objTblModFacPrv.setValueAt(bdeTotFacPrv, h, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG);
                        bdeValPndPag=new BigDecimal(objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG)==null?"0":objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG).toString());
                        intRegFacPrv=Integer.parseInt(objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_NUM_REG)==null?"0":(objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_NUM_REG).toString().equals("")?"0":objTblModFacPrv.getValueAt(h, INT_TBL_FAC_PRV_DAT_NUM_REG).toString()));
                        if(intRegFacPrv>0){
                            for(int g=0; g<objTblModForPag.getRowCountTrue(); g++){
                                intRegForPag=Integer.parseInt(objTblModForPag.getValueAt(g, INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV)==null?"0":(objTblModForPag.getValueAt(g, INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).toString().equals("")?"0":objTblModForPag.getValueAt(g, INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV).toString()));
                                strPorRetForPag=objTblModForPag.getValueAt(g, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"":objTblModForPag.getValueAt(g, INT_TBL_FOR_PAG_DAT_POR_RET).toString();
                                if(intRegForPag>0){
                                    if(!strPorRetForPag.equals("")){
                                        if(intRegFacPrv==intRegForPag){
                                            bdeValRetPag=new BigDecimal(objTblModForPag.getValueAt(g, INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":objTblModForPag.getValueAt(g, INT_TBL_FOR_PAG_DAT_VAL_RET).toString());
                                            bdeSumValRet=bdeSumValRet.add(bdeValRetPag);
                                        }
                                    }
                                }
                            }
                            objTblModFacPrv.setValueAt(bdeValPndPag.subtract(bdeSumValRet), h, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG);
                            bdeSumValRet=new BigDecimal(BigInteger.ZERO);
                        }
                    }
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

    private boolean cargarPagoFacturaProveedor(){
        boolean blnRes=true;

        int intValDia=0;
        BigDecimal bdeValCer=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePorRet=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValApl=new BigDecimal(BigInteger.ZERO);

        BigDecimal bdeValPndPag=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotDoc=new BigDecimal(BigInteger.ZERO);


        int intRegFacPrv=-1;
        int intRegForPag=-1;

        BigDecimal bdeSumValParPag=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValDif=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValUltPag=new BigDecimal(BigInteger.ZERO);



        int intNumDiaForPag=Integer.parseInt(txtNumForPag.getText()==null?"0":(txtNumForPag.getText().equals("")?"0":txtNumForPag.getText()));
        int intTipDiaForPag=Integer.parseInt(txtTipForPag.getText()==null?"0":(txtTipForPag.getText().equals("")?"0":txtTipForPag.getText()));

        int intIndDiaForPag_Db=0;
        getDiaPag_ForPag();


        try{
            if(intNumDiaForPag>0){
                int intFil=-1;
                int intInicio=0;
                int intFinal=0;
                if(intTipDiaForPag==1){
                    intInicio=0;
                    intFinal=intNumDiaForPag;
                }
                else{
                    intInicio=1;
                    intFinal=intNumDiaForPag+1;
                }

                for(int k=0;k<objTblModFacPrv.getRowCountTrue(); k++){
                    bdeValPndPag=new BigDecimal(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG)==null?"0":(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG).toString().equals("")?"0":objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_VAL_PND_PAG).toString()));
                    intRegFacPrv=Integer.parseInt(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_NUM_REG)==null?"0":(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_NUM_REG).toString().equals("")?"0":objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_NUM_REG).toString()));
                    if(intRegFacPrv>0){
                        objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_EDI);
                        objTblModForPag.removeEmptyRows();
                        bdeValApl=objUti.redondearBigDecimal((bdeValPndPag), objParSis.getDecimalesMostrar());
                        bdeValApl=(bdeValApl.divide(new BigDecimal("" + intNumDiaForPag), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));

                        intIndDiaForPag_Db=0;

                        for(int i=intInicio; i<intFinal; i++ ){
                            //intValDia=30;
                            //intValDia=intValDia*i;
                            intValDia=objUti.getIntValueAt(arlDatDiaPag_ForPag, intIndDiaForPag_Db, INT_ARL_DIA_PAG_NUM_DIA_CRE);

                            objTblModForPag.insertRow();
                            intFil=(objTblModForPag.getRowCountTrue()-1);
                            objTblModForPag.setValueAt("" + intValDia, intFil, INT_TBL_FOR_PAG_DAT_DIA_CRE);
                            objTblModForPag.setValueAt(objUti.formatearFecha(fechaVencimientoPago(intValDia, dtpFecDoc.getText()), "yyyy-MM-dd", "dd/MM/yyyy"), intFil, INT_TBL_FOR_PAG_DAT_FEC_VEN);
                            objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_POR_RET);
                            objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_BAS_IMP);
                            objTblModForPag.setValueAt(bdeValApl, intFil, INT_TBL_FOR_PAG_DAT_VAL_RET);
                            objTblModForPag.setValueAt(objTblModFacPrv.getValueAt(k, INT_TBL_FAC_PRV_DAT_NUM_REG), intFil, INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV);
                            bdeSumValParPag=bdeSumValParPag.add(bdeValApl);
                            intIndDiaForPag_Db++;
                        }
                        bdeValDif=bdeValPndPag.subtract(bdeSumValParPag);
                        bdeValUltPag=new BigDecimal(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                        objTblModForPag.setValueAt((bdeValUltPag.add(bdeValDif)), (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET);
                        bdeSumValParPag=new BigDecimal(BigInteger.ZERO);
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }





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
    private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try
        {
            if(strCodCatTipDoc.equals("15")){//categoria 15 equivale a LIQCOM -> solo presenta personas naturales
                strSQLTmp+=" AND a1.tx_tipide='C'";
            }
            strSQLTmp+=" ORDER BY a1.tx_nom";
            vcoPrv.setCondicionesSQL(strSQLTmp);


            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtRucPrv.setText(vcoPrv.getValueAt(2));
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        strTelPrv=vcoPrv.getValueAt(5);
                        strCiuPrv=vcoPrv.getValueAt(6);

                        strNumSerFacPrv=vcoPrv.getValueAt(7);
                        strNumAutSriFacPrv=vcoPrv.getValueAt(8);
                        strNumFecCadFacPrv=vcoPrv.getValueAt(9);

                        mostrarBenPre();
                        actualizarGlo();
                        objTblMod.removeAllRows();
                        objTblModMot.removeAllRows();
                        objTblModForPag.removeAllRows();
                        objTblModFacPrv.removeAllRows();
                        txtCodForPag.setText("");
                        txtNomForPag.setText("");
                        txtNumForPag.setText("");
                        txtTipForPag.setText("");
                        intNumRegFacPrv=0;
                        intNumRegMotDoc=0;
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    System.out.println("codigo proveedor: " + txtCodPrv.getText());
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtRucPrv.setText(vcoPrv.getValueAt(2));
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        strTelPrv=vcoPrv.getValueAt(5);
                        strCiuPrv=vcoPrv.getValueAt(6);

                        strNumSerFacPrv=vcoPrv.getValueAt(7);
                        strNumAutSriFacPrv=vcoPrv.getValueAt(8);
                        strNumFecCadFacPrv=vcoPrv.getValueAt(9);

                        mostrarBenPre();
                        actualizarGlo();
                        objTblMod.removeAllRows();
                        objTblModMot.removeAllRows();
                        objTblModForPag.removeAllRows();
                        objTblModFacPrv.removeAllRows();
                        txtCodForPag.setText("");
                        txtNomForPag.setText("");
                        txtNumForPag.setText("");
                        txtTipForPag.setText("");
                        intNumRegFacPrv=0;
                        intNumRegMotDoc=0;
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
                            txtRucPrv.setText(vcoPrv.getValueAt(2));
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                            strTelPrv=vcoPrv.getValueAt(5);
                            strCiuPrv=vcoPrv.getValueAt(6);

                            strNumSerFacPrv=vcoPrv.getValueAt(7);
                            strNumAutSriFacPrv=vcoPrv.getValueAt(8);
                            strNumFecCadFacPrv=vcoPrv.getValueAt(9);

                            mostrarBenPre();
                            actualizarGlo();
                            objTblMod.removeAllRows();
                            objTblModMot.removeAllRows();
                            objTblModForPag.removeAllRows();
                            objTblModFacPrv.removeAllRows();
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
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
                        txtRucPrv.setText(vcoPrv.getValueAt(2));
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        strTelPrv=vcoPrv.getValueAt(5);
                        strCiuPrv=vcoPrv.getValueAt(6);

                        strNumSerFacPrv=vcoPrv.getValueAt(7);
                        strNumAutSriFacPrv=vcoPrv.getValueAt(8);
                        strNumFecCadFacPrv=vcoPrv.getValueAt(9);

                        mostrarBenPre();
                        actualizarGlo();
                        objTblMod.removeAllRows();
                        objTblModMot.removeAllRows();
                        objTblModForPag.removeAllRows();
                        objTblModFacPrv.removeAllRows();
                        txtCodForPag.setText("");
                        txtNomForPag.setText("");
                        txtNumForPag.setText("");
                        txtTipForPag.setText("");
                        intNumRegFacPrv=0;
                        intNumRegMotDoc=0;
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
                            txtRucPrv.setText(vcoPrv.getValueAt(2));
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                            strTelPrv=vcoPrv.getValueAt(5);
                            strCiuPrv=vcoPrv.getValueAt(6);

                            strNumSerFacPrv=vcoPrv.getValueAt(7);
                            strNumAutSriFacPrv=vcoPrv.getValueAt(8);
                            strNumFecCadFacPrv=vcoPrv.getValueAt(9);

                            mostrarBenPre();
                            actualizarGlo();
                            objTblMod.removeAllRows();
                            objTblModMot.removeAllRows();
                            objTblModForPag.removeAllRows();
                            objTblModFacPrv.removeAllRows();
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
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
            arlCam.add("a2.co_cat");
            arlCam.add("st_docNecMarLis");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Cód.Cat.");
            arlAli.add("Per.Aut.Pag.");

            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, ";
                strSQL+=" a1.ne_ultDoc AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, a2.co_cat";
                strSQL+=", CASE WHEN a1.st_docNecMarLis IS NULL THEN 'N'";
                strSQL+="	WHEN a1.st_docNecMarLis='' THEN 'N'";
                strSQL+=" 	ELSE a1.st_docNecMarLis END AS st_docNecMarLis";
                strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cattipdocsis AS a2 ON a1.co_cat=a2.co_cat)";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar,";
                strSQL+=" a1.ne_ultDoc AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, a2.co_cat";
                strSQL+=", CASE WHEN a1.st_docNecMarLis IS NULL THEN 'N'";
                strSQL+="	WHEN a1.st_docNecMarLis='' THEN 'N'";
                strSQL+=" 	ELSE a1.st_docNecMarLis END AS st_docNecMarLis";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cattipdocsis AS a2 ON a1.co_cat=a2.co_cat)";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }


            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=5;
            intColOcu[1]=6;
            intColOcu[2]=7;
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Beneficiarios".
     */
    private boolean configurarVenConBen()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_reg");
            arlCam.add("a1.tx_benChq");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Beneficiario");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            arlAncCol.add("80");
            vcoBen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de beneficiarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoBen.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta funcián muestra el beneficiario predeterminado del cheque
     * de acuerdo al proveedor seleccionado.
     * @return true: Si se pudo mostrar el beneficiario predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarBenPre()
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
                strSQL+="SELECT a1.co_reg, a1.tx_benChq";
                strSQL+=" FROM tbm_benChq AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli=" + txtCodPrv.getText();
                strSQL+=" AND a1.st_reg='P'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodBen.setText(rst.getString("co_reg"));
                    txtNomBen.setText(rst.getString("tx_benChq"));
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strNecPerAutPag=vcoTipDoc.getValueAt(7);
                        if(strNecPerAutPag.equals("S")  ){
                            chkNecMarLis.setVisible(true);
                            chkEmiChqAntRecFacPrv.setVisible(false);
                        }
                        else{
                            chkEmiChqAntRecFacPrv.setVisible(true);
                            chkNecMarLis.setVisible(false);
                        }

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strCodCatTipDoc=vcoTipDoc.getValueAt(6);
                        actualizarGlo();
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();

                        if(strCodCatTipDoc.equals("15")){
                            txtCodPrv.setText("");
                            strIdePrv="";
                            txtDesLarPrv.setText("");
                            strDirPrv="";
                            strTelPrv="";
                            strCiuPrv="";
                            strNumSerFacPrv="";
                            strNumAutSriFacPrv="";
                            strNumFecCadFacPrv="";
                            txtCodBen.setText("");
                            txtNomBen.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
                        }
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strNecPerAutPag=vcoTipDoc.getValueAt(7);
                        if(strNecPerAutPag.equals("S")  ){
                            chkNecMarLis.setVisible(true);
                            chkEmiChqAntRecFacPrv.setVisible(false);
                        }
                        else{
                            chkEmiChqAntRecFacPrv.setVisible(true);
                            chkNecMarLis.setVisible(false);
                        }

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strCodCatTipDoc=vcoTipDoc.getValueAt(6);
                        actualizarGlo();
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();

                        if(strCodCatTipDoc.equals("15")){
                            txtCodPrv.setText("");
                            strIdePrv="";
                            txtDesLarPrv.setText("");
                            strDirPrv="";
                            strTelPrv="";
                            strCiuPrv="";
                            strNumSerFacPrv="";
                            strNumAutSriFacPrv="";
                            strNumFecCadFacPrv="";
                            txtCodBen.setText("");
                            txtNomBen.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
                        }

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
                            strNecPerAutPag=vcoTipDoc.getValueAt(7);
                            if(strNecPerAutPag.equals("S")  ){
                                chkNecMarLis.setVisible(true);
                                chkEmiChqAntRecFacPrv.setVisible(false);
                            }
                            else{
                                chkEmiChqAntRecFacPrv.setVisible(true);
                                chkNecMarLis.setVisible(false);
                            }

                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strCodCatTipDoc=vcoTipDoc.getValueAt(6);
                            actualizarGlo();
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();

                        if(strCodCatTipDoc.equals("15")){
                            txtCodPrv.setText("");
                            strIdePrv="";
                            txtDesLarPrv.setText("");
                            strDirPrv="";
                            strTelPrv="";
                            strCiuPrv="";
                            strNumSerFacPrv="";
                            strNumAutSriFacPrv="";
                            strNumFecCadFacPrv="";
                            txtCodBen.setText("");
                            txtNomBen.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
                        }



                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strNecPerAutPag=vcoTipDoc.getValueAt(7);
                        if(strNecPerAutPag.equals("S")  ){
                            chkNecMarLis.setVisible(true);
                            chkEmiChqAntRecFacPrv.setVisible(false);
                        }
                        else{
                            chkEmiChqAntRecFacPrv.setVisible(true);
                            chkNecMarLis.setVisible(false);
                        }

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strCodCatTipDoc=vcoTipDoc.getValueAt(6);
                        actualizarGlo();
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();

                        if(strCodCatTipDoc.equals("15")){
                            txtCodPrv.setText("");
                            strIdePrv="";
                            txtDesLarPrv.setText("");
                            strDirPrv="";
                            strTelPrv="";
                            strCiuPrv="";
                            strNumSerFacPrv="";
                            strNumAutSriFacPrv="";
                            strNumFecCadFacPrv="";
                            txtCodBen.setText("");
                            txtNomBen.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
                        }



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
                            strNecPerAutPag=vcoTipDoc.getValueAt(7);
                            if(strNecPerAutPag.equals("S")  ){
                                chkNecMarLis.setVisible(true);
                                chkEmiChqAntRecFacPrv.setVisible(false);
                            }
                            else{
                                chkEmiChqAntRecFacPrv.setVisible(true);
                                chkNecMarLis.setVisible(false);
                            }

                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strCodCatTipDoc=vcoTipDoc.getValueAt(6);
                            actualizarGlo();
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();

                        if(strCodCatTipDoc.equals("15")){
                            txtCodPrv.setText("");
                            strIdePrv="";
                            txtDesLarPrv.setText("");
                            strDirPrv="";
                            strTelPrv="";
                            strCiuPrv="";
                            strNumSerFacPrv="";
                            strNumAutSriFacPrv="";
                            strNumFecCadFacPrv="";
                            txtCodBen.setText("");
                            txtNomBen.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
                        }



                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Descripcián larga".
                    System.out.println("codigo:  " + txtCodTipDoc.getText());
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strNecPerAutPag=vcoTipDoc.getValueAt(7);
                        if(strNecPerAutPag.equals("S")  ){
                            chkNecMarLis.setVisible(true);
                            chkEmiChqAntRecFacPrv.setVisible(false);
                        }
                        else{
                            chkEmiChqAntRecFacPrv.setVisible(true);
                            chkNecMarLis.setVisible(false);
                        }

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strCodCatTipDoc=vcoTipDoc.getValueAt(6);
                        actualizarGlo();
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();

                        if(strCodCatTipDoc.equals("15")){
                            txtCodPrv.setText("");
                            strIdePrv="";
                            txtDesLarPrv.setText("");
                            strDirPrv="";
                            strTelPrv="";
                            strCiuPrv="";
                            strNumSerFacPrv="";
                            strNumAutSriFacPrv="";
                            strNumFecCadFacPrv="";
                            txtCodBen.setText("");
                            txtNomBen.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
                        }



                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(0);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strNecPerAutPag=vcoTipDoc.getValueAt(7);
                            if(strNecPerAutPag.equals("S")  ){
                                chkNecMarLis.setVisible(true);
                                chkEmiChqAntRecFacPrv.setVisible(false);
                            }
                            else{
                                chkEmiChqAntRecFacPrv.setVisible(true);
                                chkNecMarLis.setVisible(false);
                            }

                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strCodCatTipDoc=vcoTipDoc.getValueAt(6);
                            actualizarGlo();
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();

                        if(strCodCatTipDoc.equals("15")){
                            txtCodPrv.setText("");
                            strIdePrv="";
                            txtDesLarPrv.setText("");
                            strDirPrv="";
                            strTelPrv="";
                            strCiuPrv="";
                            strNumSerFacPrv="";
                            strNumAutSriFacPrv="";
                            strNumFecCadFacPrv="";
                            txtCodBen.setText("");
                            txtNomBen.setText("");
                            intNumRegFacPrv=0;
                            intNumRegMotDoc=0;
                        }



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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConForPag(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.

                    vcoForPag.setCampoBusqueda(0);
                    vcoForPag.show();
                    if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        objTblModForPag.removeAllRows();
                        //cargarFormaPago(1);
                        if(objTblModFacPrv.getRowCountTrue()>0){
                            if(objTblModMot.getRowCountTrue()>1){
                                cargarFormaPago();
                                colocaNumRegFacPrv_NumRegForPag();

                            }
                            else{
                                cargarFormaPagoFacturaProveedor();
                            }


                        }
                        else
                            cargarFormaPago();

                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        System.out.println("a");
                        objTblModForPag.removeAllRows();
                        System.out.println("b");
                        //cargarFormaPago(1);
                        if(objTblModFacPrv.getRowCountTrue()>0){

                            if(objTblModMot.getRowCountTrue()>1){
                                cargarFormaPago();
                                colocaNumRegFacPrv_NumRegForPag();

                            }
                            else{
                                cargarFormaPagoFacturaProveedor();
                            }
                        }
                        else{
                            cargarFormaPago();
                        }
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                            txtNumForPag.setText(vcoForPag.getValueAt(3));
                            txtTipForPag.setText(vcoForPag.getValueAt(4));
                            objTblModForPag.removeAllRows();
                            //cargarFormaPago(1);
                            if(objTblModFacPrv.getRowCountTrue()>0){
                                if(objTblModMot.getRowCountTrue()>1){
                                    cargarFormaPago();
                                    colocaNumRegFacPrv_NumRegForPag();

                                }
                                else{
                                    cargarFormaPagoFacturaProveedor();
                                }
                            }

                            else
                                cargarFormaPago();
                        }
                        else
                        {
                            txtCodForPag.setText(strCodForPag);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoForPag.buscar("a1.tx_desForPag", txtNomForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        objTblModForPag.removeAllRows();
                        //cargarFormaPago(1);
                        if(objTblModFacPrv.getRowCountTrue()>0){
                            if(objTblModMot.getRowCountTrue()>1){
                                cargarFormaPago();
                                colocaNumRegFacPrv_NumRegForPag();

                            }
                            else{
                                cargarFormaPagoFacturaProveedor();
                            }
                        }
                        else
                            cargarFormaPago();
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(1);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                            txtNumForPag.setText(vcoForPag.getValueAt(3));
                            txtTipForPag.setText(vcoForPag.getValueAt(4));
                            objTblModForPag.removeAllRows();
                            //cargarFormaPago(1);
                            if(objTblModFacPrv.getRowCountTrue()>0){
                                if(objTblModMot.getRowCountTrue()>1){
                                    cargarFormaPago();
                                    colocaNumRegFacPrv_NumRegForPag();

                                }
                                else{
                                    cargarFormaPagoFacturaProveedor();
                                }
                            }
                            else
                                cargarFormaPago();
                        }
                        else
                        {
                            txtNomForPag.setText(strNomForPag);
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.co_forPag");
            arlCam.add("a8.tx_desForPag");
            arlCam.add("a8.ne_numPag");
            arlCam.add("a8.ne_tipForPag");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            arlAli.add("Número de pagos");
            arlAli.add("Tipo de pago");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+=" select a7.co_forPag, a8.tx_des AS tx_desForPag";
//            strSQL+=", a8.ne_numPag, a8.ne_tipForPag";
//            strSQL+=" FROM tbm_motDoc AS a1";
//            strSQL+=" INNER JOIN (  (tbm_polRet AS a2 INNER JOIN tbm_tipPer AS a4";
//            strSQL+=" 			ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
//            strSQL+=" 				INNER JOIN tbm_emp AS a5";
//            strSQL+=" 				ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
//            strSQL+=" 	       INNER JOIN tbm_tipPer AS a3";
//            strSQL+=" 	       ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
//            strSQL+=" 	       INNER JOIN tbm_cli AS a6";
//            strSQL+=" 	       ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
//            strSQL+=" 	       INNER JOIN tbm_forPagCli AS a7 ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli";
//            strSQL+=" 	       INNER JOIN tbm_cabForPag AS a8 ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag";
//            strSQL+=" 	       )";
//            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
//            strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//            strSQL+=" AND CURRENT_DATE between a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
//            strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
//            strSQL+="                     AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
//            strSQL+=" GROUP BY a7.co_forPag, a8.tx_des, a8.ne_numPag, a8.ne_tipForPag";
//            strSQL+=" ORDER BY a7.co_forPag";

            strSQL="";
            strSQL+="select a8.co_forPag, a8.tx_des AS tx_desForPag";
            strSQL+=" , a8.ne_numPag, a8.ne_tipForPag";
            strSQL+=" FROM tbm_cabForPag AS a8";
            strSQL+=" WHERE a8.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND a8.st_reg NOT IN('E','I') ";
            strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag, a8.ne_tipForPag";
            strSQL+=" ORDER BY a8.tx_des";

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;


            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de forma de pago", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularValorRetener(int fila){
        boolean blnRes=true;
        BigDecimal bdePorRet=new BigDecimal("0");
        BigDecimal bdeValBasImp=new BigDecimal("0");
        BigDecimal bdeValRet=new BigDecimal("0");
        BigDecimal bdeValCer=new BigDecimal("0");

        try{
            bdePorRet=new BigDecimal(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
            if(bdePorRet.compareTo(bdeValCer)>0){
                bdeValBasImp=new BigDecimal(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_BAS_IMP)==null?"0":(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_BAS_IMP).toString().equals("")?"0":objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_BAS_IMP).toString()));
                bdeValRet=(bdeValBasImp.multiply(bdePorRet).divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));

            }

            objTblModForPag.setValueAt(bdeValRet, fila, INT_TBL_FOR_PAG_DAT_VAL_RET);
            cargarPago();

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private boolean sumFacPrv_equals_totDct(){
        boolean blnRes=false;
        BigDecimal bdeValFacPrv=new BigDecimal("0");
        BigDecimal bdeTotFacPrv=new BigDecimal("0");
        BigDecimal bdeTotDocPag=new BigDecimal("0");
        try{
            bdeTotDocPag=new BigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()));
            for(int i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                bdeValFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                bdeTotFacPrv=bdeTotFacPrv.add(bdeValFacPrv);
            }

            txtFacPrvDif.setText("" + bdeTotDocPag.subtract(bdeTotFacPrv));


            if(bdeTotDocPag.compareTo(bdeTotFacPrv)>=0)
                blnRes=true;

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }



    private boolean cargarCuentasContables(){
        boolean blnRes=true;
        intCodCtaIva=0;
        intCodCtaTot=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT x.co_ctaDeb, x.tx_codCtaDeb, x.tx_nomCtaDeb";
                strSQL+=" , y.co_ctaHab, y.tx_codCtaHab, y.tx_nomCtaHab";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaDeb, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaDeb, a2.tx_desLar AS tx_nomCtaDeb";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" 	) AS x";
                strSQL+=" 	INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaHab, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaHab, a2.tx_desLar AS tx_nomCtaHab";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaHab=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 	AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" 	) AS y";
                strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_tipDoc=y.co_tipDoc";
                //System.out.println("cargarCuentasContables: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaIva=rst.getInt("co_ctaDeb");
                    strNumCtaIva=rst.getString("tx_codCtaDeb");
                    strNomCtaIva=rst.getString("tx_nomCtaDeb");
                    intCodCtaTot=rst.getInt("co_ctaHab");
                    strNumCtaTot=rst.getString("tx_codCtaHab");
                    strNomCtaTot=rst.getString("tx_nomCtaHab");
                }
                
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN tbm_detTipDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + " AND a2.co_loc=" + objParSis.getCodigoLocal() + " AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaSubTot=rst.getInt("co_cta");
                    strNomCtaSubTot=rst.getString("tx_desLar");
                    strNumCtaSubTot=rst.getString("tx_codCta");
                }
 
                stm.close();
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
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private boolean validaFechaVencimiento(int fila){
        boolean blnRes=true;
        int intFecSis[];
        int intFecVncChq[];
        String strFecRecChq="";
        String strFecVenChq="";
        try{
            dtpFecVenFacPrv.setText("");strFecVenChq="";
            //fecha del sistema para comparar q no sea mayor a 2 años la fecha q se ingresa con la fecha del sistema
            intFecSis=dtpFecAct.getFecha(dtpFecAct.getText());
            strFecVenChq=objTblModFacPrv.getValueAt(fila, INT_TBL_FAC_PRV_DAT_FEC_FAC)==null?"":objTblModFacPrv.getValueAt(fila, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString();
            if( ! strFecVenChq.toString().equals("")){
                dtpFecVenFacPrv.setText(objUti.formatearFecha(strFecVenChq.trim(),"dd/MM/yyyy", "dd/MM/yyyy"));
                if(dtpFecVenFacPrv.isFecha()){
                    intFecVncChq=dtpFecVenFacPrv.getFecha(dtpFecVenFacPrv.getText());
                    if(   (intFecVncChq[2]) > ((intFecSis[2]))  ){
                        mostrarMsgInf("La fecha ingresada en una factura de proveedor está fuera del presente año");
                        blnRes=false;
                    }
                }
                else{
                    strFecVenChq=strFecVenChq+ "/" + intFecSis[2];
                    dtpFecVenFacPrv.setText(objUti.formatearFecha(strFecVenChq.trim(),"dd/MM/yyyy", "dd/MM/yyyy"));
                    if(dtpFecVenFacPrv.isFecha()){
                        intFecVncChq=dtpFecVenFacPrv.getFecha(dtpFecVenFacPrv.getText());
                        strFecVenChq="" + intFecVncChq[0] + "/" + (intFecVncChq[1]<=9?"0" + intFecVncChq[1]:"" + intFecVncChq[1]) + "/" + (intFecSis[2]);
                        dtpFecVenFacPrv.setText(strFecVenChq);
                        tblDat.setValueAt(strFecVenChq, fila, INT_TBL_FAC_PRV_DAT_FEC_FAC);
                    }
                    else{
                        mostrarMsgInf("Para hacer editable este campo debe ingresar primero la fecha de la factura");
                        blnRes=false;
                    }
                }
            }
            else{
                mostrarMsgInf("Para hacer editable este campo debe ingresar primero la fecha de la factura");
                blnRes=false;
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


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
    private boolean mostrarVenConCom(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCom.setCampoBusqueda(2);
                    vcoCom.show();
                    if (vcoCom.getSelectedButton()==vcoCom.INT_BUT_ACE)
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtAliCom.setText(vcoCom.getValueAt(2));
                        txtNomCom.setText(vcoCom.getValueAt(3));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoCom.buscar("a1.tx_usr", txtAliCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtAliCom.setText(vcoCom.getValueAt(2));
                        txtNomCom.setText(vcoCom.getValueAt(3));
                    }
                    else
                    {
                        vcoCom.setCampoBusqueda(1);
                        vcoCom.setCriterio1(11);
                        vcoCom.cargarDatos();
                        vcoCom.show();
                        if (vcoCom.getSelectedButton()==vcoCom.INT_BUT_ACE)
                        {
                            txtCodCom.setText(vcoCom.getValueAt(1));
                            txtAliCom.setText(vcoCom.getValueAt(2));
                            txtNomCom.setText(vcoCom.getValueAt(3));
                        }
                        else
                        {
                            txtAliCom.setText(strAliCom);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoCom.buscar("a1.tx_nom", txtNomCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtAliCom.setText(vcoCom.getValueAt(2));
                        txtNomCom.setText(vcoCom.getValueAt(3));
                    }
                    else
                    {
                        vcoCom.setCampoBusqueda(2);
                        vcoCom.setCriterio1(11);
                        vcoCom.cargarDatos();
                        vcoCom.show();
                        if (vcoCom.getSelectedButton()==vcoCom.INT_BUT_ACE)
                        {
                            txtCodCom.setText(vcoCom.getValueAt(1));
                            txtAliCom.setText(vcoCom.getValueAt(2));
                            txtNomCom.setText(vcoCom.getValueAt(3));
                        }
                        else
                        {
                            txtNomCom.setText(strNomCom);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Descripcián larga".
                    System.out.println("codigo usuario:  " + txtCodCom.getText());
                    if (vcoCom.buscar("a1.co_usr", txtCodCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtAliCom.setText(vcoCom.getValueAt(2));
                        txtNomCom.setText(vcoCom.getValueAt(3));
                    }
                    else
                    {
                        vcoCom.setCampoBusqueda(0);
                        vcoCom.setCriterio1(11);
                        vcoCom.cargarDatos();
                        vcoCom.show();
                        if (vcoCom.getSelectedButton()==vcoCom.INT_BUT_ACE)
                        {
                            txtCodCom.setText(vcoCom.getValueAt(1));
                            txtAliCom.setText(vcoCom.getValueAt(2));
                            txtNomCom.setText(vcoCom.getValueAt(3));
                        }
                        else
                        {
                            txtCodCom.setText(strCodCom);
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConCom()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1 INNER JOIN tbr_usrEmp AS a2";
                strSQL+=" ON a1.co_usr=a2.co_usr";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.st_reg='A' AND a2.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }

            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de compradores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCom.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




    private boolean regenerarFormaPago(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(objTblModFacPrv.getRowCountTrue()>0){
                    if(objTblModMot.getRowCountTrue()>1){
                        colocaNumRegFacPrv_NumRegForPag();
                    }
                    else{
                        if(cargarFormaPagoRetencionesFacturaProveedor()){
                            if(cargarPagoFacturaProveedor()){
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
        return blnRes;
    }


    private boolean existeDocumentoAsociado(){
        boolean blnRes=false;
        Connection conCam;
        Statement stmCam;
        ResultSet rstCam;
        try{
            System.out.println("existeDocumentoAsociado");
            conCam=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCam!=null){
                System.out.println("A");
                stmCam=conCam.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a3.nd_valApl";
                strSQL+=" FROM tbr_detrecdoccabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_cabRecDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_detRecDoc AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" WHERE a1.co_empRel=" + rstCab.getString("co_emp") + " AND a1.co_locRel=" + rstCab.getString("co_loc") + "";
                strSQL+=" AND a1.co_tipDocRel=" + rstCab.getString("co_tipDoc") + " AND a1.co_docRel=" + rstCab.getString("co_doc") + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E') AND a3.st_reg NOT IN('I','E')";
                strSQL+=" AND a3.nd_valApl=0";
                strSQL+=" GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a3.nd_valApl";
                rstCam=stmCam.executeQuery(strSQL);
                while(rstCam.next()){
                    if(rstCam.getBigDecimal("nd_valApl").compareTo(BigDecimal.ZERO)>0){
                        blnRes=true;
                        break;
                    }
                }

                strSQL="";
                strSQL+="SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc";
                strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_detPag AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag";
                strSQL+=" AND a2.co_tipDoc=a3.co_tipDocPag AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag";
                strSQL+=" INNER JOIN tbm_cabPag AS a4";
                strSQL+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp") + " AND a1.co_loc=" + rstCab.getString("co_loc") + "";
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc") + " AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E') AND a3.st_reg NOT IN('I','E') AND a4.st_reg NOT IN('I','E')";
                strSQL+=" GROUP BY a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc";
                System.out.println("pago asociado: " + strSQL);
                rstCam=stmCam.executeQuery(strSQL);
                if(rstCam.next()){
                    blnRes=true;
                }
                conCam.close();
                conCam=null;
                rstCam.close();
                rstCam=null;
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



    private boolean generarObservacion(){
        boolean blnRes=true;
        String strObs="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strObs+=" " + (objTblMod.getValueAt(i, INT_TBL_GRL_DAT_DES)==null?"":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_DES).toString()) + "";
            }
            txaObs2.setText(strObs);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }


    private boolean colocaNumRegFacPrv_NumRegForPag(){
        boolean blnRes=true;
        BigDecimal bdePorRetForPag=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
                bdePorRetForPag=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                if(bdePorRetForPag.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                    objTblModForPag.setValueAt(objTblModFacPrv.getValueAt((objTblModFacPrv.getRowCountTrue()-1), INT_TBL_FAC_PRV_DAT_NUM_REG).toString(), i, INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV);
                }
                else
                    objTblModForPag.setValueAt(null, i, INT_TBL_FOR_PAG_DAT_NUM_REG_FAC_PRV);
            }




        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }


    private boolean cargarValorIvaFacturaProveedor(){
        boolean blnRes=true;
        BigDecimal bdeSub=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumSub=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeSub=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB)==null?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_SUB).toString());
                if(! objTblMod.isChecked(i, INT_TBL_GRL_DAT_EST_IVA)){
                    bdeSumSub=bdeSumSub.add(bdeSub);
                }
            }
            objTblModFacPrv.setValueAt(bdeSumSub, 0, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA);

            System.out.println("valror iva: " + bdeSumSub);

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }


    private boolean isEnabledValorDiarioDoble(){
        boolean blnRes=false;
        Connection conDiaDbl;
        Statement stmDiaDbl;
        ResultSet rstDiaDbl;
        String strSQLDiaDbl="";
        try{
            conDiaDbl=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conDiaDbl!=null){
                stmDiaDbl=conDiaDbl.createStatement();
                strSQLDiaDbl="";
                strSQLDiaDbl+="SELECT st_perAsiDiaDob FROM tbm_cabTipDoc";
                strSQLDiaDbl+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() +  "";
                strSQLDiaDbl+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQLDiaDbl+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQLDiaDbl+=" AND st_perAsiDiaDob='S'";
                rstDiaDbl=stmDiaDbl.executeQuery(strSQLDiaDbl);
                if(rstDiaDbl.next())
                    blnRes=true;
                conDiaDbl.close();
                conDiaDbl=null;
                stmDiaDbl.close();
                stmDiaDbl=null;
                rstDiaDbl.close();
                rstDiaDbl=null;
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



    private boolean permiteRecibirFacPrvFecha(){
        boolean blnRes=false;
        Connection conValFecFacPrv;
        Statement stmValFecFacPrv;
        ResultSet rstValFecFacPrv;
        int intDiaMaxRecFacPrv=-1;
        String strFecFacPrv="";
        String strFecCorIni="";
        String strFecCorFin="";
        String strFecMaxRecFacPrv="";
        int intFecCorFin[];
        int intFecMaxFacPrv[]=null;
        int intFecFacPrv[];

        try{
            conValFecFacPrv=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conValFecFacPrv!=null){
                stmValFecFacPrv=conValFecFacPrv.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, co_loc,ne_numMaxDiaRecFacPrvMesAnt";
                strSQL+=" FROM tbm_loc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                rstValFecFacPrv=stmValFecFacPrv.executeQuery(strSQL);
                if(rstValFecFacPrv.next()){
                    intDiaMaxRecFacPrv=rstValFecFacPrv.getInt("ne_numMaxDiaRecFacPrvMesAnt");
                }

                for(int i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                    strFecFacPrv=objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_FAC)==null?"":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString();
                    intFecFacPrv=dtpFecFacPrv.getFecha(objUti.formatearFecha(strFecFacPrv, "dd/MM/yyyy", "dd/MM/yyyy"));

                    //fecha pero con ultimo dia del mes anterior
                    strSQL="";
                    strSQL+="select CAST(";
                    strSQL+="         (CASE WHEN extract('year' FROM CURRENT_DATE) >9";
                    strSQL+="                 THEN '' || extract('year' FROM CURRENT_DATE)";
                    strSQL+="         ELSE '0' || extract('year' FROM CURRENT_DATE) END) || '-' ||";
                    strSQL+="         (CASE WHEN extract('month' FROM CURRENT_DATE) >9 ";
                    strSQL+="                 THEN '' || extract('month' FROM CURRENT_DATE)";
                    strSQL+="         ELSE '0' || extract('month' FROM CURRENT_DATE) END) || '-' ||";
                    strSQL+="         ('01')";
                    strSQL+=" AS DATE)   AS fe_ultDiaMesAnt";
                    rstValFecFacPrv=stmValFecFacPrv.executeQuery(strSQL);
                    if(rstValFecFacPrv.next()){
                        intFecMaxFacPrv=dtpFecCorIni.getFecha(objUti.formatearFecha(rstValFecFacPrv.getDate("fe_ultDiaMesAnt"), "dd/MM/yyyy"));
                        strFecMaxRecFacPrv="" + intDiaMaxRecFacPrv + "/" + intFecMaxFacPrv[1] + "/" + intFecMaxFacPrv[2];
                    }

                    //fecha pero con ultimo dia del mes anterior
                    strSQL="";
                    strSQL+="select CAST(";
                    strSQL+="         (CASE WHEN extract('year' FROM CURRENT_DATE) >9";
                    strSQL+="                 THEN '' || extract('year' FROM CURRENT_DATE)";
                    strSQL+="         ELSE '0' || extract('year' FROM CURRENT_DATE) END) || '-' ||";
                    strSQL+="         (CASE WHEN extract('month' FROM CURRENT_DATE) >9 ";
                    strSQL+="                 THEN '' || extract('month' FROM CURRENT_DATE)";
                    strSQL+="         ELSE '0' || extract('month' FROM CURRENT_DATE) END) || '-' ||";
                    strSQL+="         ('01')";
                    strSQL+=" AS DATE)-1   AS fe_ultDiaMesAnt";
                    rstValFecFacPrv=stmValFecFacPrv.executeQuery(strSQL);
                    if(rstValFecFacPrv.next()){
                        strFecCorFin=objUti.formatearFecha(rstValFecFacPrv.getDate("fe_ultDiaMesAnt"), "dd/MM/yyyy");
                    }

                    intFecCorFin=dtpFecCorIni.getFecha(strFecCorFin);
                    strFecCorIni="01/" + intFecCorFin[1] + "/" + intFecCorFin[2];


                    //mes del servidor      fecha de la factura del proveedor
                    if(intFecMaxFacPrv[1]==intFecFacPrv[1]){
                        blnRes=true;
                    }
                    else{
                        //fecha maxima del mes actual(servidor no documento)
                        strSQL="";
                        strSQL+="SELECT *from tbm_emp";
                        strSQL+=" WHERE '" + objUti.formatearFecha(strFecFacPrv, "dd/MM/yyyy", "yyyy-MM-dd") + "'";
                        strSQL+=" <=";
                        strSQL+="'" + objUti.formatearFecha(strFecMaxRecFacPrv, "dd/MM/yyyy", "yyyy-MM-dd") + "'";

                        strSQL+=" AND '" + objUti.formatearFecha(strFecMaxRecFacPrv, "dd/MM/yyyy", "yyyy-MM-dd") + "'";
                        strSQL+=" >= CURRENT_DATE";
                        strSQL+=" AND '" + objUti.formatearFecha(strFecFacPrv, "dd/MM/yyyy", "yyyy-MM-dd") + "'";
                        strSQL+=" BETWEEN '" + objUti.formatearFecha(strFecCorIni, "dd/MM/yyyy", "yyyy-MM-dd") + "'";
                        strSQL+=" AND  '" + objUti.formatearFecha(strFecCorFin, "dd/MM/yyyy", "yyyy-MM-dd") + "'";

                        System.out.println("SQL: " + strSQL);
                        rstValFecFacPrv=stmValFecFacPrv.executeQuery(strSQL);
                        if(rstValFecFacPrv.next()){
                            blnRes=true;
                        }
                    }
                }

                conValFecFacPrv.close();
                conValFecFacPrv=null;
                stmValFecFacPrv.close();
                stmValFecFacPrv=null;
                rstValFecFacPrv.close();
                rstValFecFacPrv=null;

            }//fin de la conexion
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


    /* Función que permite Validar si la factura del proveedor ingresada en el Documento por Pagar no exista.
     * return true: Si existe Factura del Proveedor en el Sistema
     * return false: En el otro caso.
     */
    //Rose
    private boolean facturaProveedorExisteSistema(String strNumFac, String strSerFac, String strNumAutSRI)
    {
	boolean blnRes = false;
        Connection conFacPrvFueIng;
        Statement stmFacPrvFueIng;
        ResultSet rstFacPrvFueIng;
        try {
            conFacPrvFueIng = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conFacPrvFueIng != null) {
                stmFacPrvFueIng = conFacPrvFueIng.createStatement();

                String strSQL = "";
                strSQL += " SELECT a1.co_emp, a1.co_loc, a4.fe_doc, a2.co_tipdoccon, a5.tx_descor, a4.ne_numdoc, a2.tx_numChq as NumFac, a2.tx_numser \n";
                strSQL += " FROM tbm_cabrecdoc AS a1 \n";
                strSQL += " INNER JOIN tbm_detrecdoc AS a2 \n";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc \n";
                strSQL += " INNER JOIN tbr_detRecDocCabMovInv as a3 \n";
                strSQL += " ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc \n";
                strSQL += " INNER JOIN tbm_CabMovInv as a4 \n";
                strSQL += " ON a3.co_emp=a4.co_Emp AND a3.co_loc=a4.co_loc AND a3.co_tipdocrel=a4.co_tipdoc AND a3.co_docrel=a4.co_doc AND a2.co_cli=a4.co_cli \n";
                strSQL += " INNER JOIN tbm_CabTipdoc as a5 \n";
                strSQL += " ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a2.co_tipDocCon=a5.co_tipDoc \n";
                strSQL += " WHERE a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN ('I', 'E')  AND  a3.st_reg NOT IN('E','I')  \n";
                //strSQL += " AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " AND a2.co_cli=" + txtCodPrv.getText() + " \n";
                
                if (objTooBar.getEstado() == 'm')
                {  //m = Modificar.
                   strSQL += " AND a2.co_cli <> " + txtCodPrv.getText() + " \n";
                }
                
                if ( (strNumAutSRI.length()!=37) && (strNumAutSRI.length()!=49) )
                {  //Preimpresas
                   strSQL += " AND CAST( (CASE WHEN a2.tx_numChq IS NULL THEN '0'\n";
                   strSQL += "                 WHEN a2.tx_numChq='' THEN '0'\n";
                   strSQL += "                 WHEN a2.tx_numChq='null' THEN '0'\n";
                   strSQL += "                 WHEN a2.tx_numChq='Null' THEN '0'\n";
                   strSQL += "                 WHEN a2.tx_numChq='NULL' THEN '0'\n";
                   strSQL += "                 ELSE a2.tx_numChq END )    AS INTEGER)= CAST( " + strNumFac + " AS INTEGER) \n"; 
                   strSQL += " AND CAST( (CASE WHEN a2.tx_numser IS NULL THEN '0' \n";		 
                   strSQL += "                 WHEN a2.tx_numser='' THEN '0' \n";
                   strSQL += "                 WHEN a2.tx_numser='null' THEN '0' \n";	          
                   strSQL += "                 WHEN a2.tx_numser='Null' THEN '0' \n";
                   strSQL += "                 WHEN a2.tx_numser='NULL' THEN '0' \n";
                   strSQL += "                 ELSE REPLACE (a2.tx_numser, '-','') END )  AS INTEGER)= CAST( " + strSerFac + " AS INTEGER) \n";
                   strSQL += " AND (CASE WHEN a2.tx_numAutSri IS NULL THEN '0' ELSE REPLACE (a2.tx_numAutSri, '-','') END ) = '" + strNumAutSRI + "' ";
                }
                else 
                {  //Electronicas.
                   //Aqui no seria necesario verificar si ya existe el Num_fac y el Num_serie, ya que solo bastaria con verificar si existe el 
                   //num_autorizacion, debido a que dicho numero es unico para todas las empresas y nunca se va a repetir. Por ejemplo, el proveedor A
                   //nos da una factura con num_fac = 001, num_serie = 001-002 y num_autorizacion = 112233. Luego, el proveedor B nos da una factura con 
                   //num_fac = 001, num_serie = 001-002 y num_autorizacion = 99887. Note que, ambas facturas tienen el mismo num_fac y num_serie, y si
                   //solo se hace la validacion por estos 2 campos el sistema va a guardar 2 Documentos por pagar (Ej: OPADM) lo cual estaria mal 
                   //ya que ambos OPADM tendrian la misma informacion. Para impedir esto, es necesario que la validacion se la haga solamente con el 
                   //num_autorizacion debido a que este campo sera UNICO y por tanto no se podra dar el caso de que haya 2 num_autorizacion con el mismo 
                   //valor.
                   strSQL+=" AND  "; 
                   //strSQL+=" ( ( CAST( (CASE WHEN a2.tx_numChq IS NULL THEN '0' ELSE a2.tx_numChq END ) AS INTEGER) = CAST( " + strNumFac + " AS INTEGER) "; 
                   //strSQL+="     AND CAST( (CASE WHEN a2.tx_numser IS NULL THEN '0' ELSE REPLACE (a2.tx_numser, '-','') END ) AS INTEGER) = CAST( " + strSerFac + " AS INTEGER) ";
                   //strSQL+="   )  OR ";
                   strSQL+="  ( (CASE WHEN a2.tx_numAutSri IS NULL THEN '0' ELSE REPLACE (a2.tx_numAutSri, '-','') END ) = '" + strNumAutSRI + "' ) ";
                   //strSQL+=" ) ";                
                }
                
                strSQL += " GROUP BY a1.co_emp, a1.co_loc, a4.fe_doc, a2.co_tipdoccon, a5.tx_descor, a4.ne_numdoc, a2.tx_numChq, a2.tx_numser \n";
                
                //System.out.println("facturaProveedorExisteSistema: " + strSQL);
                rstFacPrvFueIng = stmFacPrvFueIng.executeQuery(strSQL);

                if (rstFacPrvFueIng.next()) 
                {
                    //System.out.println("facturaProveedorExisteSistema:");
                    String strMsg = "<HTML>La factura " + strNumFac + " del proveedor " + txtDesLarPrv.getText() + " ya fue ingresada,<BR> ";
                    strMsg += "Se encuentra en el documento " + rstFacPrvFueIng.getString("tx_descor") + " # " + rstFacPrvFueIng.getString("ne_numdoc") + " con fecha " + rstFacPrvFueIng.getString("fe_doc");
                    strMsg += ".</HTML>";//
                    mostrarMsgInf(strMsg);
                    blnRes = true;
                }
                rstFacPrvFueIng.close();
                rstFacPrvFueIng = null;
                stmFacPrvFueIng.close();
                stmFacPrvFueIng = null;
                conFacPrvFueIng.close();
                conFacPrvFueIng = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
	            }
	            return blnRes;
        }


    public boolean setDetConIntMovInv(){
        boolean blnRes=true;
        try{
            objTblMod.insertRow();
            objTblMod.setValueAt("Pago por T/C",     (objTblMod.getRowCountTrue()-1), INT_TBL_GRL_DAT_DES);
            objTblMod.setValueAt("1",                (objTblMod.getRowCountTrue()-1), INT_TBL_GRL_DAT_CAN);
            objTblMod.setValueAt(txtSub.getText(),   (objTblMod.getRowCountTrue()-1), INT_TBL_GRL_DAT_PRE);
            objTblMod.setValueAt(txtSub.getText(),   (objTblMod.getRowCountTrue()-1), INT_TBL_GRL_DAT_SUB);
            objTblMod.setValueAt(new Boolean(false), (objTblMod.getRowCountTrue()-1), INT_TBL_GRL_DAT_EST_IVA);
            objTblMod.setValueAt(txtIva.getText(),   (objTblMod.getRowCountTrue()-1), INT_TBL_GRL_DAT_IVA);
            objTblMod.setValueAt(txtTot.getText(),   (objTblMod.getRowCountTrue()-1), INT_TBL_GRL_DAT_TOT);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    public boolean setPagMovInv(){
        boolean blnRes=true;
        try{
            objTblModForPag.insertRow();
            objTblModForPag.setValueAt("0",                (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_DIA_CRE);
            objTblModForPag.setValueAt(dtpFecDoc.getText(),(objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_FEC_VEN);
            objTblModForPag.setValueAt(txtTot.getText(),   (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET);

            objTblModForPag.setValueAt("0",                (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_POR_RET);
            objTblModForPag.setValueAt("0",                (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_BAS_IMP);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    public boolean setRetMovInv(){
        boolean blnRes=true;
        try{
            objTblModMot.insertRow();
            objTblModMot.setValueAt("5",(objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_MOT);
            objTblModMot.setValueAt("1",(objTblModMot.getRowCountTrue()-1), INT_TBL_MOT_DAT_COD_REG);


        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public boolean getAfterInsertar() {
        objTooBar.afterInsertar();
        return true;
    }

    private boolean isRegistroCambio(){
        boolean blnRes=false;
        String strCodReg="", strEstPag="", strCodCta="";
        Connection conExiRegIna;
        Statement stmExiRegIna;
        ResultSet rstExiRegIna;
        try{
            conExiRegIna=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conExiRegIna!=null){
                stmExiRegIna=conExiRegIna.createStatement();

                for(int i=0;i<arlDatAutPag.size();i++){
                    strCodReg="" + objUti.getStringValueAt(arlDatAutPag, i, INT_ARL_COD_REG);
                    strEstPag="" + objUti.getStringValueAt(arlDatAutPag, i, INT_ARL_EST_AUT_PAG);
                    strCodCta="" + objUti.getObjectValueAt(arlDatAutPag, i, INT_ARL_COD_CTA_PAG)==null?"":objUti.getStringValueAt(arlDatAutPag, i, INT_ARL_COD_CTA_PAG);

                    strSQL="";
                    strSQL+="SELECT *FROM tbm_pagMovInv AS a1";
                    strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="   AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="   AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+="   AND a1.co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="   AND a1.co_reg IN(" + strCodReg + ")";
                    strSQL+="   AND a1.st_autpag IN('" + strEstPag + "')";
                    if(strCodCta.equals(""))
                        strSQL+="   AND a1.co_ctaautpag IS NULL";
                    else
                        strSQL+="   AND a1.co_ctaautpag IN(" + strCodCta + ")";
                    System.out.println("isRegistroCambio: " + strSQL);
                    rstExiRegIna=stmExiRegIna.executeQuery(strSQL);
                    if(!rstExiRegIna.next()){
                        blnRes=true;
                        break;
                    }
                }
            }
        }
        catch(java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;

    }




    private boolean cargarNotaPedido_PedidoEmbarcado(){
        boolean blnRes=true;
        int i=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed";
                strSQL+=", d1.tx_numDoc2 FROM(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.tx_mesNotPed, c1.tx_numDoc2";
                strSQL+="  		, c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal";
                strSQL+=" 	FROM(";
                strSQL+="  		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2";
                strSQL+="  		, b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+="  		, (b1.nd_canNotPed - ";
                strSQL+="  			CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END";
                strSQL+="  		  ) AS nd_canNotPedFal";
                strSQL+=" 		FROM(";
                strSQL+="  			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.tx_numDoc2";
                strSQL+="  			, a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+="  			, a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb";
                strSQL+=" 			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesEmb=a5.co_mesemb)";
                strSQL+=" 			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+="  			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+=" 			LEFT OUTER JOIN ";
                strSQL+=" 				(tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+=" 				 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" 				 AND a3.st_reg NOT IN('I','E')";
                strSQL+=" 				)";
                strSQL+=" 			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+=" 			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+=" 			WHERE a1.st_reg NOT IN('E','I')";
                strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.tx_numDoc2";
                strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can";
                strSQL+=" 			ORDER BY a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.nd_canNotPed";
                strSQL+=" 	) AS c1";
                strSQL+="  	WHERE c1.nd_canNotPedFal > 0";
                strSQL+=" ) AS d1";
                strSQL+=" WHERE d1.nd_canNotPedFal > 0";
                strSQL+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed, d1.tx_numDoc2";
                strSQL+=" ORDER BY tx_numDoc2";
                /*UNION
                SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc,
                ''||a1.fe_emb AS fe_emb, a1.tx_numDoc2
                FROM tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabMovInv AS a2
                ON a1.co_emp=a2.co_emprelpedembimp AND a1.co_loc=a2.co_locrelpedembimp
                AND a1.co_tipDoc=a2.co_tipdocrelpedembimp AND a1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')
                WHERE a1.st_reg NOT IN('E','I')
                AND a2.co_emp IS NULL
                ORDER BY co_emp, co_loc, co_tipDoc, co_doc*/
                System.out.println("cargarNotaPedido_PedidoEmbarcado: " + strSQL);
                rst=stm.executeQuery(strSQL);

                cboNotPed_PedEmb.removeAllItems();
                cboNotPed_PedEmb.addItem("----------");
                for(i=0;rst.next(); i++){
                    cboNotPed_PedEmb.addItem("" + rst.getString("tx_numDoc2"));
                    //para saber cual pedido se ha seleccionado
                    arlRegPed=new ArrayList();
                    arlRegPed.add(INT_ARL_PED_COD_EMP,      rst.getString("co_emp"));
                    arlRegPed.add(INT_ARL_PED_COD_LOC,      rst.getString("co_loc"));
                    arlRegPed.add(INT_ARL_PED_COD_TIP_DOC,  rst.getString("co_tipDoc"));
                    arlRegPed.add(INT_ARL_PED_COD_DOC,      rst.getString("co_doc"));
                    arlRegPed.add(INT_ARL_PED_FEC_EMB,      rst.getString("fe_doc"));
                    arlRegPed.add(INT_ARL_PED_MES,          rst.getString("tx_mesNotPed"));
                    arlRegPed.add(INT_ARL_PED_NUM_PED,      rst.getString("tx_numDoc2"));
                    arlRegPed.add(INT_ARL_PED_IND_SEL,      i);
                    arlDatPed.add(arlRegPed);
                }
                cboNotPed_PedEmb.setSelectedIndex(0);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;

    }



    private boolean getDiaPag_ForPag(){
        boolean blnRes=true;
        Connection conDiaPag;
        Statement stmDiaPag;
        ResultSet rstDiaPag;
        String strDiaPag="";
        arlDatDiaPag_ForPag.clear();
        try{
            if(txtCodForPag.getText().length()>0){
                conDiaPag=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conDiaPag!=null){
                    stmDiaPag=conDiaPag.createStatement();
                    strDiaPag="";
                    strDiaPag+="select a8.co_emp, a8.co_forPag, a8.tx_des AS tx_desForPag , a8.ne_numPag, a8.ne_tipForPag, a9.ne_diaCre";
                    strDiaPag+=" FROM tbm_cabForPag AS a8 INNER JOIN tbm_detForPag AS a9";
                    strDiaPag+=" ON a8.co_emp=a9.co_emp AND a8.co_forPag=a9.co_forPag";
                    strDiaPag+=" WHERE a8.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strDiaPag+=" AND a8.co_forPag=" + txtCodForPag.getText() + "";
                    strDiaPag+=" AND a8.st_reg NOT IN('E','I') ";
                    strDiaPag+=" GROUP BY a8.co_emp, a8.co_forPag, a8.tx_des, a8.ne_numPag, a8.ne_tipForPag, a9.ne_diaCre, a9.co_reg";
                    strDiaPag+=" ORDER BY a8.co_forPag, a9.co_reg";
                    rstDiaPag=stmDiaPag.executeQuery(strDiaPag);
                    while(rstDiaPag.next()){
                        arlRegDiaPag_ForPag=new ArrayList();
                        arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_COD_EMP, "" + rstDiaPag.getString("co_emp"));
                        arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_COD_FOR_PAG, "" + rstDiaPag.getString("co_forPag"));
                        arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_NUM_DIA_CRE, "" + rstDiaPag.getString("ne_diaCre"));
                        arlDatDiaPag_ForPag.add(arlRegDiaPag_ForPag);
                    }
                    conDiaPag.close();
                    conDiaPag=null;
                    stmDiaPag.close();
                    stmDiaPag=null;
                    rstDiaPag.close();
                    rstDiaPag=null;
                }
                System.out.println("arlDatDiaPag_ForPag: " + arlDatDiaPag_ForPag.toString());
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

    /*
     * Método que elimina ceros a la izquierda dentro de una cadena de Caracteres.
     */
    public String eliminaCeros(String str) //Rose
    {
        if (str.length() > 0) {
            if (str.charAt(0) == '0') {
                return eliminaCeros(str.substring(1));
            }
        }
        return str;
    }

    /*
     * Método que agrega ceros a la izquierda dentro de una cadena de Caracteres.
     */
    public String agregaCeros(String str) //Rose
    {
        if (str.length() < 9) {
            str.format("%09s", str);
        }
        return str;
    }

}
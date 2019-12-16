/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom68.java
 *
 * Created on Aug 24, 2009, 11:34:14 AM
 */
package Compras.ZafCom68;


import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import java.math.BigInteger;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import Librerias.ZafSegMovInv.ZafSegMovInv;

/**
 *
 * @author ilino
 */
public class ZafCom68 extends javax.swing.JInternalFrame {

    private ZafParSis objParSis;
    private ZafUtil objUti;

    private String strSQL, strAux;
    private final int INT_TBL_DAT_HIS_LIN=0;
    private final int INT_TBL_DAT_HIS_CHK=1;
    private final int INT_TBL_DAT_HIS_ANI=2;
    private final int INT_TBL_DAT_HIS_NUM_MES=3;
    private final int INT_TBL_DAT_HIS_MES=4;
    private Vector vecCabHis, vecCabHis2, vecCabHis3, vecCabHis4, vecRegHis, vecRegHis2, vecRegHis3, vecRegHis4;
    private Vector vecAux, vecAux2, vecAux3, vecAux4, vecDatHis, vecDatHis2, vecDatHis3, vecDatHis4;
    private Vector vecCab, vecReg, vecDat;
    private Vector vecCabFix, vecRegFix, vecDatFix;
    private ZafTblMod objTblModHis, objTblModHis2, objTblModHis3, objTblModHis4, objTblMod, objTblModFix;
    private ZafTblFilCab objTblFilCabHis, objTblFilCabHis2, objTblFilCabHis3, objTblFilCabHis4, objTblFilCab, objTblFilCabFix;
    private ZafTblFilCab objTblFilCabTot, objTblFilCabTotFix;
    private ZafTblCelRenChk objTblCelRenChkHis, objTblCelRenChkHis2, objTblCelRenChkHis3, objTblCelRenChkHis4;
    private ZafTblCelEdiChk objTblCelEdiChkHis, objTblCelEdiChkHis2, objTblCelEdiChkHis3, objTblCelEdiChkHis4;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoItm;

    private boolean blnHayCam;
    private Connection con;
    private Statement stm;
    private ResultSet rst;

    private String strCodAlt, strNomItm;

    //PARA COLUMNAS de la tabla de datos
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_ITM=1;
    private final int INT_TBL_DAT_COD_ALT_ITM=2;
    private final int INT_TBL_DAT_COD_ALT_ITM2=3;
    private final int INT_TBL_DAT_NOM_ITM=4;
    private final int INT_TBL_DAT_UNI_MED=5;
    private final int INT_TBL_DAT_PES_KG=6;
    private final int INT_TBL_DAT_COS_UNI_GRP=7;


    //PARA COLUMNAS de la tabla fija
    private final int INT_TBL_DAT_FIX_LIN=0;
    private final int INT_TBL_DAT_FIX_COD_ITM=1;
    private final int INT_TBL_DAT_FIX_COD_ALT_ITM=2;
    private final int INT_TBL_DAT_FIX_COD_ALT_ITM2=3;
    private final int INT_TBL_DAT_FIX_NOM_ITM=4;
    private final int INT_TBL_DAT_FIX_UNI_MED=5;
    private final int INT_TBL_DAT_FIX_PES_KG=6;
    private final int INT_TBL_DAT_FIX_COS_UNI_GRP=7;
    private final int INT_TBL_DAT_FIX_EST_ELI_ITM=8;
    //agregar

    private ZafTblPopMnu objTblPopMnu, objTblPopMnuFix;
    private ZafTblBus objTblBus, objTblBusFix;
    
    private ArrayList arlRegAniAdd, arlDatAniAdd, arlRegAniAdd2, arlDatAniAdd2, arlRegAniAdd3, arlDatAniAdd3, arlRegAniAdd4, arlDatAniAdd4;
    private final int INT_ARL_ANI=0;
    private final int INT_ARL_NUM_MES=1;
    private final int INT_ARL_MES=2;
    private final int INT_ARL_COL=3;
    private boolean blnCon;
    private ZafThreadGUI objThrGUI;
    private int intNumColEst;//numero de columnas fijas
    private int intNumColAddFecCor, intNumColIniFecCor, intNumColFinFecCor;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddFecCor2, intNumColIniFecCor2, intNumColFinFecCor2;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddFecCor3, intNumColIniFecCor3, intNumColFinFecCor3;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddFecCor4, intNumColIniFecCor4, intNumColFinFecCor4;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddTot, intNumColIniTot, intNumColFinTot;//numero de columnas que se adicionan si hay totales(depende de las columnas de historico)
    private int intNumColAddTot2, intNumColIniTot2, intNumColFinTot2, intNumColAddTot3, intNumColIniTot3, intNumColFinTot3, intNumColAddTot4, intNumColIniTot4, intNumColFinTot4;//numero de columnas que se adicionan si hay totales(depende de las columnas de historico)
    private int intNumColAddProAnu, intNumColIniProAnu, intNumColFinProAnu;//numero de columnas que se adicionan para promedio anual(calculado, manual)
    private int intNumColAddStk, intNumColIniStk, intNumColFinStk;//numero de columnas que se adicionan para Stock actual y de importaciones
    private int intNumColAddPedEmb, intNumColIniPedEmb, intNumColFinPedEmb;//numero de columnas que se adicionan pedidos de importaciones
    
    private final int INT_NUM_COL_HIS_VTA_FEC_TIT=4;//Número de columnas dentro de cada selección del Histórico de Ventas
    private final int INT_NUM_COL_HIS_VTA_STK=0;//Stock (nueva)
    private final int INT_NUM_COL_HIS_VTA_UNI=1;//Unidades
    private final int INT_NUM_COL_HIS_VTA_VEC=2;//Veces
    private final int INT_NUM_COL_HIS_VTA_MES=3;//Meses
    
    
    private int intNumColAddPedPre, intNumColIniPedPre, intNumColFinPedPre;//numero de columnas que se adicionan para datos de notas de pedido previas
    
    private int intNumColAddDis, intNumColIniDis, intNumColFinDis;//numero de columnas que se adicionan para datos de reposicion
    private int intNumColAddVtaPro, intNumColIniVtaPro, intNumColFinVtaPro;//numero de columnas que se adicionan para datos de reposicion
    private int intNumColAddRep, intNumColIniRep, intNumColFinRep;//numero de columnas que se adicionan para datos de reposicion
    private int intNumColAddImp, intNumColIniImp_Ped1, intNumColFinImp_Ped1;//numero de columnas que se adicionan para datos que se van a importar por pedido 1 (comprende Pedido1 y Peso)
    private int intNumColIniImp_Ped2, intNumColFinImp_Ped2;//numero de columnas que se adicionan para datos que se van a importar por pedido 2 (comprende Pedido2 y Peso)
    private int intNumColAddEstItmEli, intNumColIniEstItmEli, intNumColFinEstItmEli;//estado del item a mostrarse
    private int intNumColAddChkItmEli, intNumColIniChkItmEli, intNumColFinChkItmEli;//checkbox que permite cambiar el estado del item a mostrarse

    private ZafTblCelRenLbl objTblCelRenLblTot, objTblCelRenLblTotFix;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtProAnu, objTblCelEdiTxtPed, objTblCelEdiTxtImp1, objTblCelEdiTxtImp2, objTblCelEdiTxtPesItmFix, objTblCelEdiTxtPedPre;
    //para poder dar formato y color al mismo tiempo se crearon varias variables renderizadoras, antes se la trabajaba solo con una variables
    private ZafTblCelRenLbl objTblCelRenLblGrl, objTblCelRenLblAni, objTblCelRenLblAniMes, objTblCelRenLblAni2, objTblCelRenLblAniMes2, objTblCelRenLblAni3, objTblCelRenLblAniMes3, objTblCelRenLblAni4, objTblCelRenLblAniMes4;
    private ZafTblCelRenLbl objTblCelRenLblSumHisVta, objTblCelRenLblProAnu, objTblCelRenLblStk;
    private ZafTblCelRenLbl objTblCelRenLblPedEmb, objTblCelRenLblRep, objTblCelRenLblImpPed1, objTblCelRenLblImpPed2, objTblCelRenLblItmEli, objTblCelRenLblItmEliChk;
    
    private ZafTblCelRenLbl objTblCelRenLblPedPre, objTblCelRenLblPedPreTot;
    
    private ZafTblCelRenLbl objTblCelRenLblColFix, objTblCelRenLblFixStr;
    
    
    private ZafTblCelRenLbl objTblCelRenLblDis, objTblCelRenLblVtaPro;
    
    
    private ZafPerUsr objPerUsr;
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAdaFix objMouMotAdaFix;
    private JScrollBar barDat1, barFix1, barDatVer1, barDatTot1, barDat2, barFix2, barFixVer2, barDatTotFix2, barDat3, barFix3, barFixVer3, barDatTotFix3, barDatTot3;

    //contiene la informacion de los nombres de los pedidos embarcados
    private ArrayList arlRegPedEmb, arlDatPedEmb;
    private final int INT_ARL_PED_EMB_COD_EMP=0;
    private final int INT_ARL_PED_EMB_COD_LOC=1;
    private final int INT_ARL_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_PED_EMB_COD_DOC=3;
    private final int INT_ARL_PED_EMB_NUM_PED=4;
    private final int INT_ARL_PED_EMB_NUM_COL=5;
    private final int INT_ARL_PED_EMB_EST_COL=6;
    private final int INT_ARL_PED_EMB_FEC_EST_PED_EMB=7;

    //contiene la informacion de los valores ingresados para cada item en los pedidos embarcados
    private ArrayList arlRegDetPedEmb, arlDatDetPedEmb;
    private final int INT_ARL_DET_PED_EMB_COD_EMP=0;
    private final int INT_ARL_DET_PED_EMB_COD_LOC=1;
    private final int INT_ARL_DET_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_DET_PED_EMB_COD_DOC=3;
    private final int INT_ARL_DET_PED_EMB_COD_ITM=4;
    private final int INT_ARL_DET_PED_EMB_CAN_PED_EMB=5;
    private final int INT_ARL_DET_PED_EMB_COD_ALT_ITM=6;
    private final int INT_ARL_DET_PED_EMB_EST=7;
    private final int INT_ARL_DET_PED_EMB_NUM_COL=8;
    
    
    private ZafTblMod objTblModTot, objTblModTotFix;
    
    private ArrayList arlRegFilEli, arlDatFilEli;
    final int INT_ARL_FIL_ELI_COD_EMP=0;
    final int INT_ARL_FIL_ELI_COD_ITM=1;
    final int INT_ARL_FIL_ELI_EST_ITM=2;
    
    private ArrayList arlRegExpExc, arlDatExpExc;
    private final int INT_ARL_EXP_EXC_COD_ALT_ITM=0;
    private final int INT_ARL_EXP_EXC_COD_ALT_ITM2=1;
    
    private final int INT_ARL_EXP_EXC_NOM_ITM=2;
    private final int INT_ARL_EXP_EXC_PES_UNI=3;
    private final int INT_ARL_EXP_EXC_CAN_REP_LSC=4;
    private final int INT_ARL_EXP_EXC_PES_TOT_ITM=5;
      

    //contiene la informacion de los pedidos previos -CABECERA
    private ArrayList arlRegPedPre, arlDatPedPre;
    private final int INT_ARL_CAB_PED_PRE_COD_EMP=0;
    private final int INT_ARL_CAB_PED_PRE_COD_LOC=1;
    private final int INT_ARL_CAB_PED_PRE_COD_TIP_DOC=2;
    private final int INT_ARL_CAB_PED_PRE_COD_DOC=3;
    private final int INT_ARL_CAB_PED_PRE_FEC_DOC=4;
    private final int INT_ARL_CAB_PED_PRE_NUM_DOC=5;
    private final int INT_ARL_CAB_PED_PRE_NUM_PED_PRE=6;
    private final int INT_ARL_CAB_PED_PRE_NUM_COL=7;
    private final int INT_ARL_CAB_PED_PRE_COL_MOD=8;//si algun dato de esa columna ha sido modificado
    
    //contiene la informacion de los pedidos previos -DETALLE(items con cantidades)
    private ArrayList arlRegDetPedPre, arlDatDetPedPre;
    private final int INT_ARL_DET_PED_PRE_COD_EMP=0;
    private final int INT_ARL_DET_PED_PRE_COD_LOC=1;
    private final int INT_ARL_DET_PED_PRE_COD_TIP_DOC=2;
    private final int INT_ARL_DET_PED_PRE_COD_DOC=3;
    private final int INT_ARL_DET_PED_PRE_COD_ITM=4;
    private final int INT_ARL_DET_PED_PRE_CAN_PED_PRE=5;
    private final int INT_ARL_DET_PED_PRE_COD_ALT_ITM=6;
    private final int INT_ARL_DET_PED_PRE_EST=7;
    private final int INT_ARL_DET_PED_PRE_NUM_COL=8;
    private final int INT_ARL_DET_PED_PRE_EST_ANU=9;//contendra el estado si se debe Anular la Nota de Pedido Previa.
    
    private int intFilColSel;
    private java.util.Date datFecAux;
    
    private final int INT_EST_COD_TIP_DOC_PED_PRE=207;
    
    private int intCodDocPedPre_canRepLSC, intNumDocPedPre_canRepLSC;
    
    private String strNomPedPreIngLSC;
    
    
    private ZafTblCelRenChk objTblCelRenChkEstEliItm;
    private ZafTblCelEdiChk objTblCelEdiChkEstEliItm;
    
    
    private int intCodEmpExiNomPedPre;
    private int intCodLocExiNomPedPre;
    private int intCodTipDocExiNomPedPre;
    private int intCodDocExiNomPedPre;
    
    private Object objCodSegNotPedPre;   // Objeto de Codigo de Seguimiento.
   
    private int intFilSelCop[];
    private ZafSegMovInv objSegMovInv;
    
    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCom68(ZafParSis obj) {
        try {

            initComponents();
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            vecDatHis=new Vector();
            vecDatHis2=new Vector();
            vecDatHis3=new Vector();
            vecDatHis4=new Vector();
            arlDatAniAdd=new ArrayList();
            arlDatAniAdd2=new ArrayList();
            arlDatAniAdd3=new ArrayList();
            arlDatAniAdd4=new ArrayList();
            arlDatPedEmb=new ArrayList();
            arlDatDetPedEmb=new ArrayList();
            arlDatFilEli=new ArrayList();
            arlDatExpExc=new ArrayList();
            arlDatPedPre=new ArrayList();//contendra las notas de pedido previas, solo datos generales, o sea los de cabecera
            arlDatDetPedPre=new ArrayList();//contendra los datos de las notas de pedido previas, datos del item
            objSegMovInv=new ZafSegMovInv(objParSis, this);

        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCon = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panConFil = new javax.swing.JPanel();
        panRep = new javax.swing.JPanel();
        panCanItmRep1 = new javax.swing.JPanel();
        panOptHis = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        optHisMen = new javax.swing.JRadioButton();
        optHisAnu = new javax.swing.JRadioButton();
        panFilHis = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHis = new javax.swing.JTable();
        panCanItmRep2 = new javax.swing.JPanel();
        panOptHis1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        optHisMen2 = new javax.swing.JRadioButton();
        optHisAnu2 = new javax.swing.JRadioButton();
        panFilHis1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHis2 = new javax.swing.JTable();
        panCanItmRep3 = new javax.swing.JPanel();
        panOptHis2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        optHisMen3 = new javax.swing.JRadioButton();
        optHisAnu3 = new javax.swing.JRadioButton();
        panFilHis2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHis3 = new javax.swing.JTable();
        panCanItmRep4 = new javax.swing.JPanel();
        panOptHis3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        optHisMen4 = new javax.swing.JRadioButton();
        optHisAnu4 = new javax.swing.JRadioButton();
        panFilHis3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHis4 = new javax.swing.JTable();
        panItm = new javax.swing.JPanel();
        panFilItm = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblNumMesRep = new javax.swing.JLabel();
        butRecNumMesChg = new javax.swing.JButton();
        jspNumMesRep = new javax.swing.JSpinner();
        jPanel10 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblCodAltItmDes = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        lblCodAltItmHas = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        txtCodAltItmTer = new javax.swing.JTextField();
        lblCodAltItmTer = new javax.swing.JLabel();
        panSelChk = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        chkItmNotEli = new javax.swing.JCheckBox();
        chkCalRepPes = new javax.swing.JCheckBox();
        panHisVta = new javax.swing.JPanel();
        chkHisVta = new javax.swing.JCheckBox();
        chkHisVtaVec = new javax.swing.JCheckBox();
        chkHisVtaStk = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblFix = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotFix = new javax.swing.JScrollPane();
        tblTotFix = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panPie = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butEliPedPrv = new javax.swing.JButton();
        butExpExc = new javax.swing.JButton();
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

        panCon.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panCon.add(lblTit, java.awt.BorderLayout.PAGE_START);

        panConFil.setPreferredSize(new java.awt.Dimension(710, 330));
        panConFil.setLayout(new java.awt.BorderLayout());

        panRep.setPreferredSize(new java.awt.Dimension(710, 206));
        panRep.setLayout(new java.awt.GridLayout(1, 0));

        panCanItmRep1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCanItmRep1.setPreferredSize(new java.awt.Dimension(380, 100));
        panCanItmRep1.setLayout(new java.awt.BorderLayout());

        panOptHis.setPreferredSize(new java.awt.Dimension(258, 46));
        panOptHis.setLayout(null);

        jLabel1.setText("Histórico de ventas");
        panOptHis.add(jLabel1);
        jLabel1.setBounds(10, 2, 290, 14);

        optHisMen.setText("Histórico mensual");
        optHisMen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMenActionPerformed(evt);
            }
        });
        panOptHis.add(optHisMen);
        optHisMen.setBounds(20, 30, 140, 14);

        optHisAnu.setSelected(true);
        optHisAnu.setText("Histórico anual");
        optHisAnu.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnuActionPerformed(evt);
            }
        });
        panOptHis.add(optHisAnu);
        optHisAnu.setBounds(20, 16, 140, 14);

        panCanItmRep1.add(panOptHis, java.awt.BorderLayout.NORTH);

        panFilHis.setPreferredSize(new java.awt.Dimension(452, 360));
        panFilHis.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblHis);

        panFilHis.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panCanItmRep1.add(panFilHis, java.awt.BorderLayout.CENTER);

        panRep.add(panCanItmRep1);

        panCanItmRep2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCanItmRep2.setPreferredSize(new java.awt.Dimension(380, 100));
        panCanItmRep2.setLayout(new java.awt.BorderLayout());

        panOptHis1.setPreferredSize(new java.awt.Dimension(258, 46));
        panOptHis1.setLayout(null);

        jLabel2.setText("Histórico de ventas");
        panOptHis1.add(jLabel2);
        jLabel2.setBounds(10, 2, 290, 14);

        optHisMen2.setText("Histórico mensual");
        optHisMen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMen2ActionPerformed(evt);
            }
        });
        panOptHis1.add(optHisMen2);
        optHisMen2.setBounds(20, 30, 140, 14);

        optHisAnu2.setSelected(true);
        optHisAnu2.setText("Histórico anual");
        optHisAnu2.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnu2ActionPerformed(evt);
            }
        });
        panOptHis1.add(optHisAnu2);
        optHisAnu2.setBounds(20, 16, 140, 14);

        panCanItmRep2.add(panOptHis1, java.awt.BorderLayout.NORTH);

        panFilHis1.setPreferredSize(new java.awt.Dimension(452, 360));
        panFilHis1.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblHis2);

        panFilHis1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panCanItmRep2.add(panFilHis1, java.awt.BorderLayout.CENTER);

        panRep.add(panCanItmRep2);

        panCanItmRep3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCanItmRep3.setPreferredSize(new java.awt.Dimension(380, 100));
        panCanItmRep3.setLayout(new java.awt.BorderLayout());

        panOptHis2.setPreferredSize(new java.awt.Dimension(258, 46));
        panOptHis2.setLayout(null);

        jLabel3.setText("Histórico de ventas");
        panOptHis2.add(jLabel3);
        jLabel3.setBounds(10, 2, 290, 14);

        optHisMen3.setText("Histórico mensual");
        optHisMen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMen3ActionPerformed(evt);
            }
        });
        panOptHis2.add(optHisMen3);
        optHisMen3.setBounds(20, 30, 130, 14);

        optHisAnu3.setSelected(true);
        optHisAnu3.setText("Histórico anual");
        optHisAnu3.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnu3ActionPerformed(evt);
            }
        });
        panOptHis2.add(optHisAnu3);
        optHisAnu3.setBounds(20, 16, 130, 14);

        panCanItmRep3.add(panOptHis2, java.awt.BorderLayout.NORTH);

        panFilHis2.setPreferredSize(new java.awt.Dimension(452, 360));
        panFilHis2.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblHis3);

        panFilHis2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        panCanItmRep3.add(panFilHis2, java.awt.BorderLayout.CENTER);

        panRep.add(panCanItmRep3);

        panCanItmRep4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCanItmRep4.setPreferredSize(new java.awt.Dimension(380, 100));
        panCanItmRep4.setLayout(new java.awt.BorderLayout());

        panOptHis3.setPreferredSize(new java.awt.Dimension(258, 46));
        panOptHis3.setLayout(null);

        jLabel4.setText("Histórico de ventas");
        panOptHis3.add(jLabel4);
        jLabel4.setBounds(10, 2, 290, 14);

        optHisMen4.setText("Histórico mensual");
        optHisMen4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMen4ActionPerformed(evt);
            }
        });
        panOptHis3.add(optHisMen4);
        optHisMen4.setBounds(20, 30, 130, 14);

        optHisAnu4.setSelected(true);
        optHisAnu4.setText("Histórico anual");
        optHisAnu4.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnu4ActionPerformed(evt);
            }
        });
        panOptHis3.add(optHisAnu4);
        optHisAnu4.setBounds(20, 16, 130, 14);

        panCanItmRep4.add(panOptHis3, java.awt.BorderLayout.NORTH);

        panFilHis3.setPreferredSize(new java.awt.Dimension(452, 360));
        panFilHis3.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblHis4);

        panFilHis3.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        panCanItmRep4.add(panFilHis3, java.awt.BorderLayout.CENTER);

        panRep.add(panCanItmRep4);

        panConFil.add(panRep, java.awt.BorderLayout.CENTER);

        panItm.setPreferredSize(new java.awt.Dimension(100, 200));
        panItm.setLayout(new java.awt.BorderLayout());

        panFilItm.setPreferredSize(new java.awt.Dimension(420, 140));
        panFilItm.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 48));
        jPanel4.setLayout(null);

        lblNumMesRep.setText("Tiempo a reponer:");
        jPanel4.add(lblNumMesRep);
        lblNumMesRep.setBounds(10, 8, 110, 14);

        butRecNumMesChg.setText("C");
        butRecNumMesChg.setToolTipText("Cambia el número de meses a reponer en la tabla");
        butRecNumMesChg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRecNumMesChgActionPerformed(evt);
            }
        });
        jPanel4.add(butRecNumMesChg);
        butRecNumMesChg.setBounds(190, 4, 20, 20);

        jspNumMesRep.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspNumMesRepStateChanged(evt);
            }
        });
        jPanel4.add(jspNumMesRep);
        jspNumMesRep.setBounds(110, 4, 80, 20);

        panFilItm.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel10.setPreferredSize(new java.awt.Dimension(100, 70));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(100, 55));
        jPanel5.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        jPanel5.add(optTod);
        optTod.setBounds(8, 2, 410, 14);

        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        jPanel5.add(optFil);
        optFil.setBounds(8, 18, 410, 14);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Bodega en la que se debe hacer el conteo");
        jPanel5.add(lblItm);
        lblItm.setBounds(30, 32, 36, 16);
        jPanel5.add(txtCodItm);
        txtCodItm.setBounds(50, 32, 40, 20);

        txtCodAltItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusLost(evt);
            }
        });
        txtCodAltItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmActionPerformed(evt);
            }
        });
        jPanel5.add(txtCodAltItm);
        txtCodAltItm.setBounds(90, 32, 100, 20);

        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        jPanel5.add(txtNomItm);
        txtNomItm.setBounds(190, 32, 260, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        jPanel5.add(butItm);
        butItm.setBounds(450, 32, 20, 20);

        jPanel10.add(jPanel5, java.awt.BorderLayout.NORTH);

        panItmDesHas.setPreferredSize(new java.awt.Dimension(100, 40));
        panItmDesHas.setRequestFocusEnabled(false);
        panItmDesHas.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 60));
        jPanel3.setLayout(null);

        lblCodAltItmDes.setText("Desde:");
        jPanel3.add(lblCodAltItmDes);
        lblCodAltItmDes.setBounds(24, 15, 60, 14);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        jPanel3.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(234, 12, 100, 20);

        lblCodAltItmHas.setText("Hasta:");
        jPanel3.add(lblCodAltItmHas);
        lblCodAltItmHas.setBounds(186, 15, 60, 14);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        jPanel3.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(76, 13, 100, 20);

        panItmDesHas.add(jPanel3);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T,I\n</HTML>");
        txtCodAltItmTer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmTerActionPerformed(evt);
            }
        });
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 12, 184, 20);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 14, 100, 20);

        panItmDesHas.add(panCodAltItmTer);

        jPanel10.add(panItmDesHas, java.awt.BorderLayout.CENTER);

        panFilItm.add(jPanel10, java.awt.BorderLayout.CENTER);

        panItm.add(panFilItm, java.awt.BorderLayout.NORTH);

        panSelChk.setPreferredSize(new java.awt.Dimension(100, 50));
        panSelChk.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setLayout(null);

        chkItmNotEli.setText("Mostrar sólo los items que no han sido eliminados");
        jPanel11.add(chkItmNotEli);
        chkItmNotEli.setBounds(3, 10, 330, 14);

        chkCalRepPes.setText("Calcular reposición por peso");
        jPanel11.add(chkCalRepPes);
        chkCalRepPes.setBounds(3, 26, 260, 14);

        panSelChk.add(jPanel11);

        panHisVta.setBorder(javax.swing.BorderFactory.createTitledBorder("Histórico de Ventas"));
        panHisVta.setLayout(null);

        chkHisVta.setText("Mostrar Histórico de Ventas: Unidades");
        chkHisVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaActionPerformed(evt);
            }
        });
        panHisVta.add(chkHisVta);
        chkHisVta.setBounds(3, 14, 310, 14);

        chkHisVtaVec.setText("Mostrar Histórico de Ventas: Veces");
        chkHisVtaVec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaVecActionPerformed(evt);
            }
        });
        panHisVta.add(chkHisVtaVec);
        chkHisVtaVec.setBounds(3, 28, 310, 14);

        chkHisVtaStk.setText("Mostrar Histórico de Ventas: Stock");
        panHisVta.add(chkHisVtaStk);
        chkHisVtaStk.setBounds(3, 42, 310, 14);

        panSelChk.add(panHisVta);

        panItm.add(panSelChk, java.awt.BorderLayout.CENTER);

        panConFil.add(panItm, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panConFil);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(380);
        jSplitPane1.setDividerSize(2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        tblFix.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblFix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblFixFocusGained(evt);
            }
        });
        spnFix.setViewportView(tblFix);

        jPanel1.add(spnFix, java.awt.BorderLayout.CENTER);

        spnTotFix.setPreferredSize(new java.awt.Dimension(320, 42));

        tblTotFix.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotFix.setViewportView(tblTotFix);

        jPanel1.add(spnTotFix, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

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
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        jPanel2.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(320, 42));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTot.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTot.setViewportView(tblTot);

        jPanel2.add(spnTot, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel2);

        jPanel8.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanel8);

        panCon.add(tabFrm, java.awt.BorderLayout.CENTER);

        panPie.setPreferredSize(new java.awt.Dimension(685, 46));
        panPie.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(764, 27));
        panBot.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jPanel7.setPreferredSize(new java.awt.Dimension(279, 26));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 1));

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(104, 28));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel7.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(104, 28));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        jPanel7.add(butGua);

        butEliPedPrv.setText("Eliminar N.P.P.");
        butEliPedPrv.setToolTipText("Eliminar Nota de Pedido Previo");
        butEliPedPrv.setPreferredSize(new java.awt.Dimension(116, 28));
        butEliPedPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliPedPrvActionPerformed(evt);
            }
        });
        jPanel7.add(butEliPedPrv);

        butExpExc.setText("Exportar excel");
        butExpExc.setPreferredSize(new java.awt.Dimension(118, 28));
        butExpExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpExcActionPerformed(evt);
            }
        });
        jPanel7.add(butExpExc);

        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(104, 28));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel7.add(butCer);

        jPanel9.add(jPanel7);

        panBot.add(jPanel9, java.awt.BorderLayout.CENTER);

        panPie.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 16));
        panBarEst.setRequestFocusEnabled(false);
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

        panPie.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panCon.add(panPie, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panCon);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        if(   (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())  ){
            configurarFrm();
            agregarDocLis();

        }
        else{
            mostrarMsgInf("<HTML>Este programa sólo se puede ejecutar a través de Empresas.</HTML>");
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void optHisAnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnuActionPerformed
        // TODO add your handling code here:
        if(optHisAnu.isSelected()){
            optHisMen.setSelected(false);
            cargarAnios(1);
        }
        else{
            optHisMen.setSelected(true);
            cargarAniosMeses(1);
        }
    }//GEN-LAST:event_optHisAnuActionPerformed

    private void optHisMenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMenActionPerformed
        // TODO add your handling code here:
        if(optHisMen.isSelected()){
            optHisAnu.setSelected(false);
            cargarAniosMeses(1);
        }
        else{
            optHisAnu.setSelected(true);
            cargarAnios(1);
        }
    }//GEN-LAST:event_optHisMenActionPerformed

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        // TODO add your handling code here:
        txtCodAltItm.transferFocus();
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        // TODO add your handling code here:
    strCodAlt=txtCodAltItm.getText();
    txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        // TODO add your handling code here:
//Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
    {
        if (txtCodAltItm.getText().equals(""))
        {
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");
        }
        else
        {
            mostrarVenConItm(1);
        }
    }
    else
        txtCodAltItm.setText(strCodAlt);

    if(txtCodAltItm.getText().length()>0){
        optFil.setSelected(true);
        optTod.setSelected(false);
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
    }
    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        // TODO add your handling code here:
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        // TODO add your handling code here:
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtNomItm.setText(strNomItm);

        if(txtNomItm.getText().length()>0){
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        // TODO add your handling code here:
        mostrarVenConItm(0);
        if(txtNomItm.getText().length()>0){
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        // TODO add your handling code here:
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        // TODO add your handling code here:
    // TODO add your handling code here:
        if (txtCodAltItmDes.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);

            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");

            if (txtCodAltItmHas.getText().length()==0)
                txtCodAltItmHas.setText(txtCodAltItmDes.getText());
        }
    }//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        // TODO add your handling code here:
        txtCodAltItmHas.selectAll();
    }//GEN-LAST:event_txtCodAltItmHasFocusGained

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        // TODO add your handling code here:
        if (txtCodAltItmHas.getText().length()>0){
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        if(optTod.isSelected()){
            optFil.setSelected(false);
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
        if(optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
                lblMsgSis.setText("Obteniendo datos...");
            }
        }
        else{
            blnCon=false;
        }
        strNomPedPreIngLSC="";
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodAltItmTerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmTerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodAltItmTerActionPerformed

    private void jspNumMesRepStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspNumMesRepStateChanged
        // TODO add your handling code here:
        objTblModHis.setDataModelChanged(true);
    }//GEN-LAST:event_jspNumMesRepStateChanged

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        int intArlColSel;
        String strColModLib="";

        //se añade ese nombre al arreglo q contine la informacion de los Pedidos Previos
        if(arlDatPedPre.size()>0){
            for(int i=0; i<arlDatPedPre.size();i++){
                intArlColSel=objUti.getIntValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_COL);
                strColModLib=objUti.getStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_COL_MOD);
                if(intArlColSel==(intNumColFinPedPre-2)){
                    if(strColModLib.equals("I")){
                        if(strNomPedPreIngLSC.equals("")){
                            strNomPedPreIngLSC="";
                            strNomPedPreIngLSC=(javax.swing.JOptionPane.showInputDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat), "Ingrese el nombre del Pedido Previo:", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.QUESTION_MESSAGE)).toString();

                            objUti.setStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_PED_PRE, strNomPedPreIngLSC);
                        }
                        break;
                    }
                }
            }        
            
            if (guardarDatos()) {
                mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
                arlDatFilEli.clear();
            } else {
                mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
            }

            strNomPedPreIngLSC="";
            
            
        }
      
        

    }//GEN-LAST:event_butGuaActionPerformed

    private void butRecNumMesChgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRecNumMesChgActionPerformed
        // TODO add your handling code here:
        cambiarNumeroMesesReponerTabla();
        recalcularValoresTabla();
    }//GEN-LAST:event_butRecNumMesChgActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained

    }//GEN-LAST:event_tblDatFocusGained

    private void tblFixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFixFocusGained

    }//GEN-LAST:event_tblFixFocusGained

    private void butExpExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpExcActionPerformed
        // TODO add your handling code here:
        java.io.File filExc;
        Process pro;
        BigDecimal bdeCanRepValExpExc=new BigDecimal("0");
        try{
            
            mostrarMsgInf("<HTML>Recuerde guardar el archivo con otro nombre y en otro directorio.<BR>Si no realiza este paso, al volver a exportar el archivo se sobreescribirá y Ud. perderá todos los cambios efectuados.</HTML>");
            
            //filExc=new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon24\\ZafCon24.xls");
            if(System.getProperty("os.name").equals("Linux")){
                filExc=new java.io.File("/tmp/ZafCom68.xls");
            }
            else{
                filExc=new java.io.File("C:\\Zafiro\\Reportes\\Compras\\ZafCom68\\ZafCom68.xls");
            }
            String strNomHoj="Hoja1";
            
            
            arlDatExpExc.clear();
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    bdeCanRepValExpExc=new BigDecimal(objTblMod.getValueAt(i, k)==null?"0":(objTblMod.getValueAt(i, k).toString().equals("")?"0":objTblMod.getValueAt(i, k).toString()));
                    if(bdeCanRepValExpExc.compareTo(new BigDecimal("0"))!=0){
                        arlRegExpExc=new ArrayList();
                        arlRegExpExc.add(INT_ARL_EXP_EXC_COD_ALT_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString());
                        arlRegExpExc.add(INT_ARL_EXP_EXC_COD_ALT_ITM2, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM2).toString());                        
                        arlRegExpExc.add(INT_ARL_EXP_EXC_NOM_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM).toString());
                        arlRegExpExc.add(INT_ARL_EXP_EXC_PES_UNI, objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString());
                        arlRegExpExc.add(INT_ARL_EXP_EXC_CAN_REP_LSC, bdeCanRepValExpExc);
                        arlRegExpExc.add(INT_ARL_EXP_EXC_PES_TOT_ITM, objTblMod.getValueAt(i, (k+1)).toString());
                        arlDatExpExc.add(arlRegExpExc);
                    }
                }
            }
            System.out.println("arlDatExpExc: " + arlDatExpExc.toString());

            ZafCom68_02 objCom68_02= new ZafCom68_02(arlDatExpExc, filExc, strNomHoj);

            if(objCom68_02.export()){
                mostrarMsgInf("El archivo se cargó correctamente.");
                //Process pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon24/ZafCon24.xls");

                if(System.getProperty("os.name").equals("Linux")){
                    pro = Runtime.getRuntime().exec("oocalc /tmp/ZafCom68.xls");
                    System.out.println("LINUX: " + pro.toString());
                }
                else{
                    pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Compras/ZafCom68/ZafCom68.xls");
                    System.out.println("WINDOWS: " + pro.toString());
                }

            }
            else
                mostrarMsgInf("Falló la carga del archivo. Puede ser que el archivo este abierto.");
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butExpExcActionPerformed

    private void butEliPedPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliPedPrvActionPerformed
        // TODO add your handling code here:
        int intColSelEliPedPrv=tblDat.getSelectedColumn();
        int intColDetPedPreAux;
        String strEstPedPreAnuAux;
        
        if(intColSelEliPedPrv!=-1){
            objTblMod.addSystemHiddenColumn(intColSelEliPedPrv, tblDat);
            
            for(int e=0; e<(arlDatDetPedPre.size()); e++){
                intColDetPedPreAux=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_NUM_COL);
                
                if(intColDetPedPreAux==intColSelEliPedPrv){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST_ANU, "E");
                    break;
                }
            }
            System.out.println("arlDatDetPedPre: " + arlDatDetPedPre.toString());
        }
    }//GEN-LAST:event_butEliPedPrvActionPerformed

    private void optHisMen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMen2ActionPerformed
        // TODO add your handling code here:
        if(optHisMen2.isSelected()){
            optHisAnu2.setSelected(false);
            cargarAniosMeses2(1);
            
        }
        else{
            optHisAnu2.setSelected(true);
            cargarAnios2(1);
        }
    }//GEN-LAST:event_optHisMen2ActionPerformed

    private void optHisAnu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnu2ActionPerformed
        // TODO add your handling code here:
        if(optHisAnu2.isSelected()){
            optHisMen2.setSelected(false);
            cargarAnios2(1);
        }
        else{
            optHisMen2.setSelected(true);
            cargarAniosMeses2(1);
        }
    }//GEN-LAST:event_optHisAnu2ActionPerformed

    private void optHisMen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMen3ActionPerformed
        // TODO add your handling code here:
        if(optHisMen3.isSelected()){
            optHisAnu3.setSelected(false);
            cargarAniosMeses3(1);
        }
        else{
            optHisAnu3.setSelected(true);
            cargarAnios3(1);
        }
    }//GEN-LAST:event_optHisMen3ActionPerformed

    private void optHisAnu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnu3ActionPerformed
        // TODO add your handling code here:
        if(optHisAnu3.isSelected()){
            optHisMen3.setSelected(false);
            cargarAnios3(1);
        }
        else{
            optHisMen3.setSelected(true);
            cargarAniosMeses3(1);
        }        
    }//GEN-LAST:event_optHisAnu3ActionPerformed

    private void optHisMen4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMen4ActionPerformed
        // TODO add your handling code here:
        if(optHisMen4.isSelected()){
            optHisAnu4.setSelected(false);
            cargarAniosMeses4(1);
        }
        else{
            optHisAnu4.setSelected(true);
            cargarAnios4(1);
        }
    }//GEN-LAST:event_optHisMen4ActionPerformed

    private void optHisAnu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnu4ActionPerformed
        // TODO add your handling code here:
        if(optHisAnu4.isSelected()){
            optHisMen4.setSelected(false);
            cargarAnios4(1);
        }
        else{
            optHisMen4.setSelected(true);
            cargarAniosMeses4(1);
        }
    }//GEN-LAST:event_optHisAnu4ActionPerformed

    private void chkHisVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaActionPerformed
        // TODO add your handling code here:
        ocultaColumnasHistoricoVentas();
        ocultaColumnasTotales();
    }//GEN-LAST:event_chkHisVtaActionPerformed

    private void chkHisVtaVecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaVecActionPerformed
        // TODO add your handling code here:
        ocultaColumnaVeces();
    }//GEN-LAST:event_chkHisVtaVecActionPerformed

    
    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
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
//                tblFix.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
//                tblFix.requestFocus();
                //Ubicar el foco en la fila seleccionada.
                tblDat.changeSelection(0, 0, true, true);
//                tblFix.changeSelection(0, 0, true, true);
            }
            objThrGUI=null;
        }
    }


   private boolean cargarReg(){
        boolean blnRes=true;
        try{
            getTipoConfiguracion();
            cargarPedidosEmbarcadosConfigurados();
            cargarNotasPedidoPrevias();
            arlDatFilEli.clear();
            
            if(objTblModHis.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis.setDataModelChanged(true);
            }
            if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis2.setDataModelChanged(true);
            }
            if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis3.setDataModelChanged(true);
            }
            if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis4.setDataModelChanged(true);
            }
            
            
           
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(configurarColumnasAniosMeses()){
                    if(optHisMen.isSelected()){
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                            if(consultaDetalleNotayPedidosEmbarcados()){//los pedidos embarcados
                                if(consultaItemsPedidoPrevio()){
                                    if(consultarRegAniMesGrp()){
                                        recalcularValoresTabla();                                        
                                        escondeColumnasPedidoSinValor();
                                        escondeColumnasPedidoSinValorTotales();
                                        escondeColumnasPedidoPrevioSinValor();
                                        escondeColumnasPedidoPrevioSinValorTotales();
                                        setValMaxCal();
                                        ocultaColumnasHistoricoVentas();
                                        ocultaColumnasTotales();
                                        ocultaColumnaVeces();
                                        
                                        objTblMod.initRowsState();
                                        objTblModFix.initRowsState();
                                        lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
                                    }
                                }                                
                            }
                        }
                    }
                    else if(optHisAnu.isSelected()){
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                            if(consultaDetalleNotayPedidosEmbarcados()){
                                if(consultaItemsPedidoPrevio()){
                                    if(consultarRegAniGrp()){
                                        
                                        recalcularValoresTabla();
                                        
                                        escondeColumnasPedidoSinValor();
                                        escondeColumnasPedidoSinValorTotales();
                                        escondeColumnasPedidoPrevioSinValor();
                                        escondeColumnasPedidoPrevioSinValorTotales();
                                        setValMaxCal();
                                        ocultaColumnasHistoricoVentas();
                                        ocultaColumnasTotales();
                                        ocultaColumnaVeces();
                                        ocultaColumnasStkHis();
                                        ocultaColumnasTotalesStkHis();
                                        objTblMod.initRowsState();
                                        objTblModFix.initRowsState();
                                        lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
                                    }
                                }
                            }
                        }
                    }
                }

                objTblMod.initRowsState();
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }  





    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEliPedPrv;
    private javax.swing.JButton butExpExc;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butRecNumMesChg;
    private javax.swing.JCheckBox chkCalRepPes;
    private javax.swing.JCheckBox chkHisVta;
    private javax.swing.JCheckBox chkHisVtaStk;
    private javax.swing.JCheckBox chkHisVtaVec;
    private javax.swing.JCheckBox chkItmNotEli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSpinner jspNumMesRep;
    private javax.swing.JLabel lblCodAltItmDes;
    private javax.swing.JLabel lblCodAltItmHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumMesRep;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optHisAnu;
    private javax.swing.JRadioButton optHisAnu2;
    private javax.swing.JRadioButton optHisAnu3;
    private javax.swing.JRadioButton optHisAnu4;
    private javax.swing.JRadioButton optHisMen;
    private javax.swing.JRadioButton optHisMen2;
    private javax.swing.JRadioButton optHisMen3;
    private javax.swing.JRadioButton optHisMen4;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCanItmRep1;
    private javax.swing.JPanel panCanItmRep2;
    private javax.swing.JPanel panCanItmRep3;
    private javax.swing.JPanel panCanItmRep4;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panConFil;
    private javax.swing.JPanel panFilHis;
    private javax.swing.JPanel panFilHis1;
    private javax.swing.JPanel panFilHis2;
    private javax.swing.JPanel panFilHis3;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panHisVta;
    private javax.swing.JPanel panItm;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panOptHis;
    private javax.swing.JPanel panOptHis1;
    private javax.swing.JPanel panOptHis2;
    private javax.swing.JPanel panOptHis3;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panSelChk;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JScrollPane spnTotFix;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    private javax.swing.JTable tblHis;
    private javax.swing.JTable tblHis2;
    private javax.swing.JTable tblHis3;
    private javax.swing.JTable tblHis4;
    private javax.swing.JTable tblTot;
    private javax.swing.JTable tblTotFix;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            objUti = new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.4.24");
            lblTit.setText(strAux);
            txtCodItm.setVisible(false);
            txtCodItm.setEditable(false);
            txtCodItm.setEnabled(false);

            jspNumMesRep.setModel(new SpinnerNumberModel(1, 1, 100, 0.1));

            //Configurar las ZafVenCon.
            //Configurar los JTables.
            configurarTblDatHis();
            configurarTblDatHis2();
            configurarTblDatHis3();
            configurarTblDatHis4();
            configurarTblDatFix();
            configurarTblDatFixTot();
            objTblModTotFix.insertRows(2,0);
            objTblModTotFix.setValueAt("Totales - peso: ", 0, INT_TBL_DAT_FIX_NOM_ITM);
            objTblModTotFix.setValueAt("Totales - precio venta neta: ", 1, INT_TBL_DAT_FIX_NOM_ITM);
            
            configurarTblDat();
            configurarTblDatTot();
            
            configurarVenConItm();
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                chkItmNotEli.setVisible(true);
                chkItmNotEli.setEnabled(true);
            }

            butCon.setVisible(false);
            butGua.setVisible(false);
            butEliPedPrv.setVisible(false);
            butExpExc.setVisible(false);
            butCer.setVisible(false);
            
            if(objParSis.getCodigoUsuario()==1){
                butCon.setVisible(true);
                butGua.setVisible(true);
                butEliPedPrv.setVisible(true);
                butExpExc.setVisible(true);
                butCer.setVisible(true);
            }
            else{
                if(objParSis.getCodigoMenu()==2733){//bodegas
                    if(objPerUsr.isOpcionEnabled(2734)){//consultar
                         butCon.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2735)){//guardar
                        butGua.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2736)){//cerrar
                        butCer.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(3838)){//eliminar nota de pedido previa
                        butEliPedPrv.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(3493)){//exportar
                        butExpExc.setVisible(true);
                    }
                }       
            }

            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);
            butRecNumMesChg.setVisible(false);
            
            chkItmNotEli.setSelected(true);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatHis = new Vector();    //Almacena los datos
            vecCabHis = new Vector(5);  //Almacena las cabeceras
            vecCabHis.clear();
            vecCabHis.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis = new ZafTblMod();
            objTblModHis.setHeader(vecCabHis);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis.setModel(objTblModHis);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis.setRowSelectionAllowed(true);
            tblHis.getTableHeader().setReorderingAllowed(false);
            tblHis.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis = new ZafTblFilCab(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis);

            vecAux=new Vector();
            objTblModHis.setModoOperacion(objTblModHis.INT_TBL_EDI);
            vecAux.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis);
            objTblCelRenChkHis=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis=new ZafTblCelEdiChk(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis);

            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    /**
     * Esta funcián configura el JTable "tblDat" para el histórico 2
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis2() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatHis2 = new Vector();    //Almacena los datos
            vecCabHis2 = new Vector(5);  //Almacena las cabeceras
            vecCabHis2.clear();
            vecCabHis2.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis2.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis2.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis2.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis2.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis2 = new ZafTblMod();
            objTblModHis2.setHeader(vecCabHis2);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis2.setModel(objTblModHis2);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis2.setRowSelectionAllowed(true);
            tblHis2.getTableHeader().setReorderingAllowed(false);
            tblHis2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis2.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis2 = new ZafTblFilCab(tblHis2);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis2);

            vecAux2=new Vector();
            objTblModHis2.setModoOperacion(objTblModHis2.INT_TBL_EDI);
            vecAux2.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis2.setColumnasEditables(vecAux2);
            vecAux2=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis2=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis2);
            objTblCelRenChkHis2=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis2=new ZafTblCelEdiChk(tblHis2);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis2);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis2.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis2);

            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis3() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatHis3 = new Vector();    //Almacena los datos
            vecCabHis3 = new Vector(5);  //Almacena las cabeceras
            vecCabHis3.clear();
            vecCabHis3.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis3.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis3.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis3.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis3.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis3 = new ZafTblMod();
            objTblModHis3.setHeader(vecCabHis3);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis3.setModel(objTblModHis3);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis3.setRowSelectionAllowed(true);
            tblHis3.getTableHeader().setReorderingAllowed(false);
            tblHis3.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis3.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis3 = new ZafTblFilCab(tblHis3);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis3);

            vecAux3=new Vector();
            objTblModHis3.setModoOperacion(objTblModHis3.INT_TBL_EDI);
            vecAux3.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis3.setColumnasEditables(vecAux3);
            vecAux3=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis3=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis3);
            objTblCelRenChkHis3=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis3=new ZafTblCelEdiChk(tblHis3);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis3);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis3.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis3);

            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis4() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatHis4 = new Vector();    //Almacena los datos
            vecCabHis4 = new Vector(5);  //Almacena las cabeceras
            vecCabHis4.clear();
            vecCabHis4.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis4.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis4.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis4.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis4.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis4 = new ZafTblMod();
            objTblModHis4.setHeader(vecCabHis4);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis4.setModel(objTblModHis4);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis4.setRowSelectionAllowed(true);
            tblHis4.getTableHeader().setReorderingAllowed(false);
            tblHis4.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis4.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis4 = new ZafTblFilCab(tblHis4);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis4);

            vecAux4=new Vector();
            objTblModHis4.setModoOperacion(objTblModHis4.INT_TBL_EDI);
            vecAux4.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis4.setColumnasEditables(vecAux4);
            vecAux4=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis4=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis4);
            objTblCelRenChkHis4=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis4=new ZafTblCelEdiChk(tblHis4);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis4);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis4.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis4);

            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    

        /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM2, "Cod.Alt2.");            
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Unidad");
            vecCab.add(INT_TBL_DAT_PES_KG, "Peso(kg)");
            vecCab.add(INT_TBL_DAT_COS_UNI_GRP, "Pre.Vta.Net.");
            

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);

             objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 
                 
                 
                 int intArlColSel=-1;                 
                
                int intCodEmpDatPedPre, intCodLocDatPedPre, intCodTipDocDatPedPre, intCodDocDatPedPre;
                int intCodEmpDatDetPedPre, intCodLocDatDetPedPre, intCodTipDocDatDetPedPre, intCodDocDatDetPedPre, intCodItmDatDetPedPre;
                int intCodItmFilSel;
                boolean blnExiCodItm;
                int intFilEli[];
                
                 
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilEli=tblDat.getSelectedRows();
                    System.out.println("intFilEli: " + intFilEli.length);
                    
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    //no comentar esta linea
                    System.out.println("afterClick: " + intFilEli.length);
                    
                                        
                    if (objTblPopMnu.isClickTipoSeleccionSeleccionColumna()){
                        System.out.println("isClickTipoSeleccionSeleccionColumna ");
                        seleccionarCel();
                    }
                    
                    
                    if (objTblPopMnu.isClickCopiar()){
                        System.out.println("isClickCopiar: " + intFilEli.length);
                        intFilSelCop=intFilEli;
                    }
                    
                    intFilSelCop=intFilEli;

                    
                    //no cambiar a la variable global porque esta solo se carga si se da click en "copiar"
                    if (objTblPopMnu.isClickEliminarFila()){
                        for(int p=(intFilEli.length-1);p>=0; p--){
                            //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + objTblModFix.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM));
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
                            arlDatFilEli.add(arlRegFilEli);
                            //ahora si se remueve la fila seleccionada
                            objTblModFix.removeRow(intFilEli[p]);
                        }
                    }
                    

                    //no cambiar a la variable global porque esta solo se carga si se da click en "copiar"
                    if (objTblPopMnu.isClickBorrarContenido()){
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                        objTblModTot.setValueAt("0", 0, (intNumColIniImp_Ped1+1));//
                        objTblModTot.setValueAt("0", 0, (intNumColIniImp_Ped2+1));
                        System.out.println("isClickBorrarContenido: " + intFilEli.length);
                    }

                    System.out.println("**AA: ");
                    
                    if (objTblPopMnu.isClickPegar()){
                        //System.out.println("isClickPegar: " + intFilSelCop.length);
                        System.out.println("**BB: ");
                        
                        
                        if(tblDat.getSelectedColumn()==(intNumColFinPedPre-2)){
                            System.out.println("**CC: ");
                            if(strNomPedPreIngLSC.equals("")){
                                //se presenta un mensaje donde el usuario debe ingresar el numero del pedido
                                strNomPedPreIngLSC="";
                                strNomPedPreIngLSC=(javax.swing.JOptionPane.showInputDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat), "Ingrese el nombre del Pedido Previo:", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.QUESTION_MESSAGE)).toString();
                            }
                            //se añade ese nombre al arreglo q contine la informacion de los Pedidos Previos
                            //tblDat.getColumnModel().getColumn(tblDat.getSelectedColumn()).setHeaderValue(strNomPedPreIngLSC);

                            
                            for(int m=0; m<intFilSelCop.length; m++){
                                if((intFilSelCop[m])< objTblMod.getRowCountTrue()){
                                    System.out.println("m: " + m);
                                    intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ITM).toString());
                                    System.out.println("intCodItmFilSel: " + intCodItmFilSel);
                                    for(int j=0; j<arlDatPedPre.size();j++){
                                        blnExiCodItm=false;
                                        intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);
                                        if(intArlColSel==tblDat.getSelectedColumn()){
                                            objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_PED_PRE, strNomPedPreIngLSC);
                                            objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "I");
                                            for(int k=0; k<arlDatDetPedPre.size();k++){
                                                intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);

                                                    if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                                        System.out.println("intCodItmDatDetPedPre: " + intCodItmDatDetPedPre);
                                                        //se modifica la cantidad en el arreglo
                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());

                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                                        blnExiCodItm=true;
                                                        System.out.println("iguales - arlDatPedPre: " + arlDatPedPre.toString());
                                                        System.out.println("iguales - arlDatDetPedPre: " + arlDatDetPedPre.toString());


                                                        break;
                                                    }
                                                    else{
                                                        blnExiCodItm=false;
                                                    }
                                            }
                                            if(!blnExiCodItm){
                                                System.out.println("false");
                                                arlRegDetPedPre=new ArrayList();
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ALT_ITM).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                                arlDatDetPedPre.add(arlRegDetPedPre);
                                            }
                                        }
                                    }
                                }
                                
                                


                            }
                            System.out.println("pegar - arlDatPedPre: " + arlDatPedPre.toString());
                            System.out.println("pegar - arlDatDetPedPre: " + arlDatDetPedPre.toString());
                        }
                        else{
                            for(int m=0; m<intFilSelCop.length; m++){
                                if((intFilSelCop[m])< objTblMod.getRowCountTrue()){
                                    System.out.println("AAA");
                                    for(int j=0; j<arlDatPedPre.size();j++){
                                        System.out.println("BBB");
                                        blnExiCodItm=false;
                                        intCodEmpDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_EMP);
                                        intCodLocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_LOC);
                                        intCodTipDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                                        intCodDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_DOC);

                                        intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);
                                        if(intArlColSel==tblDat.getSelectedColumn()){
                                            System.out.println("CCC");
                                            objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "M");

                                            //detalle
                                            for(int k=0; k<arlDatDetPedPre.size();k++){
                                                System.out.println("DDD");
                                                intCodEmpDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_EMP);
                                                intCodLocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_LOC);
                                                intCodTipDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                                                intCodDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_DOC);
                                                intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);

                                                intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ITM).toString());

                                                if(  (intCodEmpDatPedPre==intCodEmpDatDetPedPre) && (intCodLocDatPedPre==intCodLocDatDetPedPre) && (intCodTipDocDatPedPre==intCodTipDocDatDetPedPre) && (intCodDocDatPedPre==intCodDocDatDetPedPre)   ){
                                                    System.out.println("EEE");
                                                    if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                                        System.out.println("FFF");
                                                        System.out.println("intCodItmFilSel:" + intCodItmFilSel);
                                                        System.out.println("dato:" + objTblMod.getValueAt((intFilSelCop[m]), intArlColSel));
                                                        //se modifica la cantidad en el arreglo
                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());
                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                                        blnExiCodItm=true;
                                                        break;
                                                    }
                                                    else{
                                                        System.out.println("GGG");
                                                        blnExiCodItm=false;
                                                    }

                                                }
                                            }
                                            if(!blnExiCodItm){
                                                System.out.println("HHH");
                                                arlRegDetPedPre=new ArrayList();
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ALT_ITM).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                                arlDatDetPedPre.add(arlRegDetPedPre);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                            
                        for(int k=0; k<intFilSelCop.length; k++){
                            recalcularValoresTabla((intFilSelCop[k]));
                        }
                        
                        
//                        tblDat.setRowSelectionInterval(0, 0);
//                        tblDat.requestFocus();
//                        //Ubicar el foco en la fila seleccionada.
//                        tblDat.changeSelection(0, 0, true, true);
                        
                        //para seleccionar una fila
                        tblDat.setRowSelectionAllowed(true);
                        tblDat.setColumnSelectionAllowed(false);
                        
                    }
                    
                }
            });
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_GRP).setPreferredWidth(60);
            

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setCellRenderer(objTblCelRenLblGrl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_GRP).setCellRenderer(objTblCelRenLblGrl);
            
            objTblCelRenLblGrl=null;
            

             

            //Libero los objetos auxiliares.
            tcmAux = null;

            intNumColEst=objTblMod.getColumnCount();

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setResizable(false);
            
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setResizable(false);
            
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setResizable(false);

            objTblModHis.initRowsState();
            getTipoConfiguracion();
            cargarPedidosEmbarcadosConfigurados();
            cargarNotasPedidoPrevias();
            

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean configurarTblDatTot()
    {
        boolean blnRes=true;
        try
        {
            objTblModTot=new ZafTblMod();
            objTblModTot.setHeader(vecCab);
            tblTot.setModel(objTblModTot);

            //Configurar JTable: Establecer tipo de selección.
            tblTot.setRowSelectionAllowed(true);
            tblTot.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTot=new ZafTblFilCab(tblTot);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCabTot);
            
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTot.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTot.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_GRP).setPreferredWidth(60);
                      
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);

            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);

            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setResizable(false);
            
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setResizable(false);

            tblTot.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setResizable(false);

            tblTot.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setResizable(false);

            tblTot.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setResizable(false);
            
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setMaxWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setMinWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setResizable(false);
                                    
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDat.getColumnCount(); j++){
                tblTot.getColumnModel().getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tblTot.getColumnModel().getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tblTot.getColumnModel().getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tblTot.getColumnModel().getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
                tblTot.getColumnModel().getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblTot.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setCellRenderer(objTblCelRenLblTot);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI_GRP).setCellRenderer(objTblCelRenLblTot);
            objTblCelRenLblTot=null;
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            //spnFix.setHorizontalScrollBarPolicy(spnDat.getHorizontalScrollBarPolicy());
            spnTot.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTot.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTot objTblColModLis=new ZafTblColModLisTot();
            tblDat.getColumnModel().addColumnModelListener(objTblColModLis);

            ZafTblColModLisTot objTblColModLisTot=new ZafTblColModLisTot();
            tblTot.getColumnModel().addColumnModelListener(objTblColModLisTot);

            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDat1=spnDat.getHorizontalScrollBar();
            barDatTot1=spnTot.getHorizontalScrollBar();
            
            barDatVer1=spnDat.getVerticalScrollBar();
            barFix1=spnFix.getVerticalScrollBar();

            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barDat1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTot1.setValue(evt.getValue());
                }
            });
            //VERTICAL            
            barDatVer1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFix1.setValue(evt.getValue());
                }
            });
            spnTot.setColumnHeader(null);

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
    private boolean configurarTblDatFix() {
        boolean blnRes = true;
        try {
            
            //Configurar JTable: Establecer el modelo.
            vecDatFix = new Vector();    //Almacena los datos
            vecCabFix = new Vector(9);  //Almacena las cabeceras
            vecCabFix.clear();
            vecCabFix.add(INT_TBL_DAT_FIX_LIN, "");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ITM, "Cod.Itm");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM, "Cod.Alt.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM2, "Cod.Alt2.");
            vecCabFix.add(INT_TBL_DAT_FIX_NOM_ITM, "Nombre");
            vecCabFix.add(INT_TBL_DAT_FIX_UNI_MED, "Uni");
            vecCabFix.add(INT_TBL_DAT_FIX_PES_KG, "Peso(kg)");
            vecCabFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "Pre.Vta.Net.");
            vecCabFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "Est.Eli.Itm.");
            
            objTblModFix = new ZafTblMod();

            objTblModFix.setHeader(vecCabFix);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblFix.setModel(objTblModFix);
            //Configurar JTable: Establecer tipo de seleccián.
            tblFix.setRowSelectionAllowed(true);
            tblFix.getTableHeader().setReorderingAllowed(false);

            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            objTblPopMnuFix.setBorrarContenidoEnabled(true);

             objTblPopMnuFix.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 int intFilEli[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilEli=tblFix.getSelectedRows();
                    
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnuFix.isClickEliminarFila()){
                        for(int p=(intFilEli.length-1);p>=0; p--){

                            //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM));
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
                            arlDatFilEli.add(arlRegFilEli);
                            //ahora si se remueve la fila seleccionada
                            objTblMod.removeRow(intFilEli[p]);
                        }
                    }
                }
            });

            tblFix.setAutoResizeMode(tblDat.getAutoResizeMode());
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblFix.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setPreferredWidth(74);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setPreferredWidth(12);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setPreferredWidth(118);
            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setPreferredWidth(34);
            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setPreferredWidth(58);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setPreferredWidth(58);
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setPreferredWidth(58);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ITM, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_EST_ELI_ITM, tblFix);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix = new ZafMouMotAdaFix();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix = new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblFilCabFix);

            //Configurar JTable: Editor de básqueda.
            objTblBusFix = new ZafTblBus(tblFix);

            //INICIO FIXED

            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modDat = tblFix.getSelectionModel();//se descomento
            tblDat.setSelectionModel(modDat);//se descomento

            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modFix = tblDat.getSelectionModel();
            tblFix.setSelectionModel(modFix);
                      
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barFix3=spnFix.getHorizontalScrollBar();
            barDatTotFix3=spnTotFix.getHorizontalScrollBar();
            
            barFixVer3=spnFix.getVerticalScrollBar();
            barDat3=spnDat.getVerticalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barFix3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTotFix3.setValue(evt.getValue());
                }
            });
            //VERTICAL
            barFixVer3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDat3.setValue(evt.getValue());
                }
            });

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_FIX_PES_KG);
            objTblModFix.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);


            ZafTblHeaGrp objTblHeaGrpDatChq=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpDatChq.setHeight(16*4);
            ZafTblHeaColGrp objTblHeaColGrpDatChq=null;
            objTblHeaColGrpDatChq=new ZafTblHeaColGrp("Datos del item");
            objTblHeaColGrpDatChq.setHeight(16);
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_LIN));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM));
            objTblHeaGrpDatChq.addColumnGroup(objTblHeaColGrpDatChq);
            
            //FIN FIXED

            objTblCelRenLblColFix=new ZafTblCelRenLbl();
            objTblCelRenLblColFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColFix.setTipoFormato(objTblCelRenLblColFix.INT_FOR_NUM);
            objTblCelRenLblColFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setCellRenderer(objTblCelRenLblColFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setCellRenderer(objTblCelRenLblColFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setCellRenderer(objTblCelRenLblColFix);
            
            objTblCelRenLblColFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblModFix.getValueAt(objTblCelRenLblColFix.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM)==null?"":objTblModFix.getValueAt(objTblCelRenLblColFix.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM).toString();
                    
                    //
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblColFix.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblColFix.setBackground(Color.WHITE);
                    }
                }
            });
            
            
            
            
            objTblCelRenLblFixStr=new ZafTblCelRenLbl();
            objTblCelRenLblFixStr.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblFixStr.setTipoFormato(objTblCelRenLblFixStr.INT_FOR_GEN);
            //tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setCellRenderer(objTblCelRenLblFixStr);
            
            objTblCelRenLblFixStr.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblModFix.getValueAt(objTblCelRenLblFixStr.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM)==null?"":objTblModFix.getValueAt(objTblCelRenLblFixStr.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM).toString();
                    
                    //
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblFixStr.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblFixStr.setBackground(Color.WHITE);
                    }
                }
            });
            
            
            
            objTblCelEdiTxtPesItmFix=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setCellEditor(objTblCelEdiTxtPesItmFix);
            objTblCelEdiTxtPesItmFix.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdePesItmFix;
                int intFil=-1;
                int intCol=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdePesItmFix=new BigDecimal(BigInteger.ZERO);
                    intFil=tblFix.getSelectedRow();
                    intCol=tblFix.getSelectedColumn();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdePesItmFix=new BigDecimal(objTblModFix.getValueAt(intFil, intCol)==null?"0":(objTblModFix.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModFix.getValueAt(intFil, intCol).toString()));

                    objTblMod.setValueAt(bdePesItmFix, intFil, INT_TBL_DAT_PES_KG);
                    recalcularValoresTabla(tblFix.getSelectedRow());
                  
                }
            });

            tcmAux=null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    private boolean configurarTblDatFixTot()
    {
        boolean blnRes=true;
        try
        {
            objTblModTotFix=new ZafTblMod();
            objTblModTotFix.setHeader(vecCabFix);
            tblTotFix.setModel(objTblModTotFix);

            //Configurar JTable: Establecer tipo de selección.
            tblTotFix.setRowSelectionAllowed(true);
            tblTotFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTotFix=new ZafTblFilCab(tblTotFix);
            tblTotFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblFilCabTotFix);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setPreferredWidth(60);            
            
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_LIN, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ITM, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ALT_ITM, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_NOM_ITM, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_UNI_MED, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_PES_KG, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COS_UNI_GRP, tblTotFix);            
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_EST_ELI_ITM, tblTotFix);            
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblFix.getColumnCount(); j++){
                tblTotFix.getColumnModel().getColumn(j).setWidth(tblFix.getColumnModel().getColumn(j).getWidth());
                tblTotFix.getColumnModel().getColumn(j).setMaxWidth(tblFix.getColumnModel().getColumn(j).getMaxWidth());
                tblTotFix.getColumnModel().getColumn(j).setMinWidth(tblFix.getColumnModel().getColumn(j).getMinWidth());
                tblTotFix.getColumnModel().getColumn(j).setPreferredWidth(tblFix.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotFix.getColumnModel().getColumn(j).setResizable(tblFix.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblTotFix.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTotFix=new ZafTblCelRenLbl();
            objTblCelRenLblTotFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTotFix.setTipoFormato(objTblCelRenLblTotFix.INT_FOR_NUM);
            objTblCelRenLblTotFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblTotFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_PES_KG).setCellRenderer(objTblCelRenLblTotFix);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setCellRenderer(objTblCelRenLblTotFix);
            
            objTblCelRenLblTotFix=null;
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnTotFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotFix.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTotFix objTblColModLisFix=new ZafTblColModLisTotFix();
            tblFix.getColumnModel().addColumnModelListener(objTblColModLisFix);

            ZafTblColModLisTotFix objTblColModLisTotFix=new ZafTblColModLisTotFix();
            tblTotFix.getColumnModel().addColumnModelListener(objTblColModLisTotFix);                        
            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barFix2=spnFix.getHorizontalScrollBar();
            barDatTotFix2=spnTotFix.getHorizontalScrollBar();
            
            barFixVer2=spnFix.getVerticalScrollBar();
            barDat2=spnDat.getVerticalScrollBar();
            

            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barFix2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTotFix2.setValue(evt.getValue());
                }
            });
            //VERTICAL
            barFixVer2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDat2.setValue(evt.getValue());
                }
            });
            spnTotFix.setColumnHeader(null);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    



    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() {
        txtCodItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItm.getDocument().addDocumentListener(objDocLis);
        txtNomItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmDes.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmHas.getDocument().addDocumentListener(objDocLis);
    }


    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }



    
    private boolean cargarAnios(int opcion){
        boolean blnRes=true;
        String strAniSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis=new Vector();
                        vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals("")){
                            vecRegHis.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis.add(vecRegHis);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis.setData(vecDatHis);
                    tblHis.setModel(objTblModHis);
                    vecDatHis.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    

    private boolean cargarAniosMeses(int opcion){
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis=new Vector();
                        vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Enero");                            break;
                            case 2:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Febrero");                            break;
                            case 3:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Marzo");                            break;
                            case 4:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Abril");                            break;
                            case 5:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Mayo");                            break;
                            case 6:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Junio");                            break;
                            case 7:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Julio");                            break;
                            case 8:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Agosto");                            break;
                            case 9:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Septiembre");                            break;
                            case 10:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Octubre");                            break;
                            case 11:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Noviembre");                            break;
                            case 12:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"Diciembre");                            break;
                            default:
                                vecRegHis.add(INT_TBL_DAT_HIS_MES,"");
                                break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals(""))){
                            vecRegHis.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis.add(vecRegHis);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis.setData(vecDatHis);
                    tblHis.setModel(objTblModHis);
                    vecDatHis.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    private boolean cargarAniosMeses2(int opcion){
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis2=new Vector();
                        vecRegHis2.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis2.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis2.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis2.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Enero");                            break;
                            case 2:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Febrero");                            break;
                            case 3:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Marzo");                            break;
                            case 4:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Abril");                            break;
                            case 5:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Mayo");                            break;
                            case 6:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Junio");                            break;
                            case 7:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Julio");                            break;
                            case 8:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Agosto");                            break;
                            case 9:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Septiembre");                            break;
                            case 10:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Octubre");                            break;
                            case 11:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Noviembre");                            break;
                            case 12:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"Diciembre");                            break;
                            default:
                                vecRegHis2.add(INT_TBL_DAT_HIS_MES,"");
                                break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals(""))){
                            vecRegHis2.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis2.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis2.add(vecRegHis2);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis2.setData(vecDatHis2);
                    tblHis2.setModel(objTblModHis2);
                    vecDatHis2.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean cargarAniosMeses3(int opcion){
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis3=new Vector();
                        vecRegHis3.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis3.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis3.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis3.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Enero");                            break;
                            case 2:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Febrero");                            break;
                            case 3:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Marzo");                            break;
                            case 4:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Abril");                            break;
                            case 5:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Mayo");                            break;
                            case 6:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Junio");                            break;
                            case 7:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Julio");                            break;
                            case 8:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Agosto");                            break;
                            case 9:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Septiembre");                            break;
                            case 10:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Octubre");                            break;
                            case 11:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Noviembre");                            break;
                            case 12:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"Diciembre");                            break;
                            default:
                                vecRegHis3.add(INT_TBL_DAT_HIS_MES,"");
                                break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals(""))){
                            vecRegHis3.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis3.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis3.add(vecRegHis3);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis3.setData(vecDatHis3);
                    tblHis3.setModel(objTblModHis3);
                    vecDatHis3.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean cargarAniosMeses4(int opcion){
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis4=new Vector();
                        vecRegHis4.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis4.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis4.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis4.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Enero");                            break;
                            case 2:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Febrero");                            break;
                            case 3:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Marzo");                            break;
                            case 4:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Abril");                            break;
                            case 5:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Mayo");                            break;
                            case 6:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Junio");                            break;
                            case 7:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Julio");                            break;
                            case 8:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Agosto");                            break;
                            case 9:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Septiembre");                            break;
                            case 10:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Octubre");                            break;
                            case 11:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Noviembre");                            break;
                            case 12:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"Diciembre");                            break;
                            default:
                                vecRegHis4.add(INT_TBL_DAT_HIS_MES,"");
                                break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals(""))){
                            vecRegHis4.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis4.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis4.add(vecRegHis4);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis4.setData(vecDatHis4);
                    tblHis4.setModel(objTblModHis4);
                    vecDatHis4.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite cargar los datos de años de la tabla 2
     * @param opcion
     * @return true si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean cargarAnios2(int opcion){
        boolean blnRes=true;
        String strAniSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis2=new Vector();
                        vecRegHis2.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis2.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis2.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis2.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis2.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals("")){
                            vecRegHis2.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis2.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis2.add(vecRegHis2);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis2.setData(vecDatHis2);
                    tblHis2.setModel(objTblModHis2);
                    vecDatHis2.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    
    private boolean cargarAnios3(int opcion){
        boolean blnRes=true;
        String strAniSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis3=new Vector();
                        vecRegHis3.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis3.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis3.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis3.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis3.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals("")){
                            vecRegHis3.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis3.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis3.add(vecRegHis3);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis3.setData(vecDatHis3);
                    tblHis3.setModel(objTblModHis3);
                    vecDatHis3.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    
    
    private boolean cargarAnios4(int opcion){
        boolean blnRes=true;
        String strAniSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0){
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis4=new Vector();
                        vecRegHis4.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis4.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis4.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis4.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis4.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals("")){
                            vecRegHis4.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis4.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis4.add(vecRegHis4);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis4.setData(vecDatHis4);
                    tblHis4.setModel(objTblModHis4);
                    vecDatHis4.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    
    

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            arlCam.add("a2.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" GROUP BY a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
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
    private boolean mostrarVenConItm(int intTipBus){
        boolean blnRes=true;
        try{
                if(configurarVenConItm()){
                    switch (intTipBus){
                        case 0: //Mostrar la ventana de consulta.
                            vcoItm.setCampoBusqueda(1);
                            vcoItm.show();
                            if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                                txtCodItm.setText(vcoItm.getValueAt(1));
                                txtCodAltItm.setText(vcoItm.getValueAt(2));
                                txtNomItm.setText(vcoItm.getValueAt(3));
                            }
                            break;
                        case 1: //Búsqueda directa por "Codigo alterno".
                            if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText())){
                                txtCodItm.setText(vcoItm.getValueAt(1));
                                txtCodAltItm.setText(vcoItm.getValueAt(2));
                                txtNomItm.setText(vcoItm.getValueAt(3));
                            }
                            else{
                                vcoItm.setCampoBusqueda(1);
                                vcoItm.setCriterio1(11);
                                vcoItm.cargarDatos();
                                vcoItm.show();
                                if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                                    txtCodItm.setText(vcoItm.getValueAt(1));
                                    txtCodAltItm.setText(vcoItm.getValueAt(2));
                                    txtNomItm.setText(vcoItm.getValueAt(3));
                                }
                                else{
                                    txtCodAltItm.setText(strCodAlt);
                                }
                            }
                            break;
                        case 2: //Búsqueda directa por "Nombre del item".
                            if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText())){
                                txtCodItm.setText(vcoItm.getValueAt(1));
                                txtCodAltItm.setText(vcoItm.getValueAt(2));
                                txtNomItm.setText(vcoItm.getValueAt(3));
                            }
                            else{
                                vcoItm.setCampoBusqueda(2);
                                vcoItm.setCriterio1(11);
                                vcoItm.cargarDatos();
                                vcoItm.show();
                                if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                                    txtCodItm.setText(vcoItm.getValueAt(1));
                                    txtCodAltItm.setText(vcoItm.getValueAt(2));
                                    txtNomItm.setText(vcoItm.getValueAt(3));
                                }
                                else{
                                    txtNomItm.setText(strNomItm);
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



   private boolean configurarColumnasAniosMeses(){
       boolean blnRes=true;
       try{
            if(con!=null){
                if(eliminaColumnasAdicionadasAnios()){
                    intNumColAddFecCor=numeroColumnasAdicionar();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del PRIMER PANEL
                    intNumColAddFecCor2=numeroColumnasAdicionar2();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del SEGUNDO PANEL
                    intNumColAddFecCor3=numeroColumnasAdicionar3();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del TERCER PANEL
                    intNumColAddFecCor4=numeroColumnasAdicionar4();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del CUARTO PANEL
                   
                    if(optHisAnu.isSelected()){
                        if(agregarColumnasAdicionadasAnios()){
                            if(agregarColumnasAdicionadasAniosTotales()){//totales
                                if(agregarColumnasAdicionadasAnios2()){
                                    if(agregarColumnasAdicionadasAniosTotales2()){
                                        if(agregarColumnasAdicionadasAnios3()){
                                            if(agregarColumnasAdicionadasAniosTotales3()){
                                                if(agregarColumnasAdicionadasAnios4()){
                                                    if(agregarColumnasAdicionadasAniosTotales4()){
                                                        if(agregarColumnasAdicionadasSumHisVta()){
                                                            if(agregarColumnasAdicionadasSumHisVtaTotales()){
                                                                if(agregarColumnasAdicionadasSumHisVta2()){
                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales2()){
                                                                        if(agregarColumnasAdicionadasSumHisVta3()){
                                                                            if(agregarColumnasAdicionadasSumHisVtaTotales3()){
                                                                                if(agregarColumnasAdicionadasSumHisVta4()){
                                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales4()){
                                                                                        if(agregarColumnasPromedioAnual()){
                                                                                            if(agregarColumnasPromedioAnualTotales()){
                                                                                                if(agregarColumnasStock()){
                                                                                                    if(agregarColumnasStockTotales()){
                                                                                                        if(agregarColumnasPedidosEmbarcados()){
                                                                                                            if(agregarColumnasPedidosEmbarcadosTotales()){                                                                
                                                                                                                if(agregarColumnasPedidosPrevios()){
                                                                                                                    if(agregarColumnasPedidosPreviosTotales()){
                                                                                                                        if(agregarColumnaDisponible()){
                                                                                                                            if(agregarColumnaDisponibleTotales()){
                                                                                                                                if(agregarColumnaVentaPromedio()){
                                                                                                                                    if(agregarColumnaVentaPromedioTotales()){
                                                                                                                                        if(agregarColumnasReposicion()){
                                                                                                                                            if(agregarColumnasReposicionTotales()){
                                                                                                                                                if(agregarColumnasImportacion()){
                                                                                                                                                    if(agregarColumnasImportacionTotales()){
                                                                                                                                                        if(agregarColumnasItemEliminado()){
                                                                                                                                                            if(agregarColumnasItemEliminadoTotales()){
                                                                                                                                                                if(agregarColumnasCheckBoxItemEliminado()){
                                                                                                                                                                    if(agregarColumnasCheckBoxItemEliminadoTotales()){
                                                                                                                                                                        objTblModTot.insertRows(2,0);
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }                                                                                            
                                                                                                                                                        }                                                                                        
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }                                                                                        
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }                                                                
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                
                                                                

                                                            }
                                                        }
                                                    }                                                    
                                                }                                                
                                            }                                            
                                        }                                        
                                    }                                    
                                }
                           }
                       }
                   }
                    else if(optHisMen.isSelected()){
                        if(agregarColumnasAdicionadasAniosMeses()){//tabla normal
                            if(agregarColumnasAdicionadasAniosMesesTotales()){//tabla de totales
                                if(agregarColumnasAdicionadasAniosMeses2()){
                                    if(agregarColumnasAdicionadasAniosMesesTotales2()){
                                        if(agregarColumnasAdicionadasAniosMeses3()){
                                            if(agregarColumnasAdicionadasAniosMesesTotales3()){
                                                if(agregarColumnasAdicionadasAniosMeses4()){
                                                    if(agregarColumnasAdicionadasAniosMesesTotales4()){
                                                        if(agregarColumnasAdicionadasSumHisVta()){
                                                            if(agregarColumnasAdicionadasSumHisVtaTotales()){
                                                                if(agregarColumnasAdicionadasSumHisVta2()){
                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales2()){
                                                                        if(agregarColumnasAdicionadasSumHisVta3()){
                                                                            if(agregarColumnasAdicionadasSumHisVtaTotales3()){
                                                                                if(agregarColumnasAdicionadasSumHisVta4()){
                                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales4()){
                                                                                        if(agregarColumnasPromedioAnual()){
                                                                                            if(agregarColumnasPromedioAnualTotales()){
                                                                                                if(agregarColumnasStock()){
                                                                                                    if(agregarColumnasStockTotales()){
                                                                                                        if(agregarColumnasPedidosEmbarcados()){
                                                                                                            if(agregarColumnasPedidosEmbarcadosTotales()){
                                                                                                                if(agregarColumnasPedidosPrevios()){
                                                                                                                    if(agregarColumnasPedidosPreviosTotales()){
                                                                                                                        if(agregarColumnaDisponible()){
                                                                                                                            if(agregarColumnaDisponibleTotales()){
                                                                                                                                if(agregarColumnaVentaPromedio()){
                                                                                                                                    if(agregarColumnaVentaPromedioTotales()){
                                                                                                                                        if(agregarColumnasReposicion()){
                                                                                                                                            if(agregarColumnasReposicionTotales()){
                                                                                                                                                if(agregarColumnasImportacion()){
                                                                                                                                                    if(agregarColumnasImportacionTotales()){
                                                                                                                                                        if(agregarColumnasItemEliminado()){
                                                                                                                                                            if(agregarColumnasItemEliminadoTotales()){
                                                                                                                                                                if(agregarColumnasCheckBoxItemEliminado()){
                                                                                                                                                                    if(agregarColumnasCheckBoxItemEliminadoTotales()){
                                                                                                                                                                        objTblModTot.insertRows(2,0);
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }                                                                                                                                                                       
                                                                                                                                                    }
                                                                                                                                                }                                                                    
                                                                                                                                            }
                                                                                                                                        } 
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        } 
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }                                                    
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }                                                    
                                                }
                                            }
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
           blnRes=false;
       }
       return blnRes;
   }



   private boolean eliminaColumnasAdicionadasAnios(){
        boolean blnRes=true;
        try{

//            arlDatPedPre.clear();
//            arlDatDetPedPre.clear();
            objTblMod.removeAllRows();
            objTblModTot.removeAllRows();

            for (int i=(objTblMod.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblMod.removeColumn(tblDat, i);
            }
 
            //tengo columnas de mas en la de totales
            for (int i=(objTblModTot.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblModTot.removeColumn(tblTot, i);               
            }
          
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


   private int numeroColumnasAdicionar(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd.clear();
       try{
           //for(int i=0; i<objTblModHis.getRowCountTrue(); i++){
           for(int i=(objTblModHis.getRowCountTrue()-1); i>=0; i--){
               strLin=objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
               if(strLin.equals("M")){
                   if(objTblModHis.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                       intNumColAdd++;
                       arlRegAniAdd=new ArrayList();
                       arlRegAniAdd.add(INT_ARL_ANI, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                       arlRegAniAdd.add(INT_ARL_NUM_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                       arlRegAniAdd.add(INT_ARL_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                       arlRegAniAdd.add(INT_ARL_COL, "" );
                       arlDatAniAdd.add(arlRegAniAdd);
                   }
               }
           }
           System.out.println("ARREGLO TIENE: " + arlDatAniAdd.toString());
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }

   private int numeroColumnasAdicionar2(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd2.clear();
       try{
           for(int i=(objTblModHis2.getRowCountTrue()-1); i>=0; i--){
               strLin=objTblModHis2.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis2.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
               if(strLin.equals("M")){
                   if(objTblModHis2.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                       intNumColAdd++;
                       arlRegAniAdd2=new ArrayList();
                       arlRegAniAdd2.add(INT_ARL_ANI, "" + objTblModHis2.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                       arlRegAniAdd2.add(INT_ARL_NUM_MES, "" + objTblModHis2.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis2.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                       arlRegAniAdd2.add(INT_ARL_MES, "" + objTblModHis2.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis2.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                       arlRegAniAdd2.add(INT_ARL_COL, "" );
                       arlDatAniAdd2.add(arlRegAniAdd2);
                   }
               }
           }
           System.out.println("ARREGLO 2 TIENE: " + arlDatAniAdd2.toString());
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }
   
   
   private int numeroColumnasAdicionar3(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd3.clear();
       try{
           for(int i=(objTblModHis3.getRowCountTrue()-1); i>=0; i--){
               strLin=objTblModHis3.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis3.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
               if(strLin.equals("M")){
                   if(objTblModHis3.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                       intNumColAdd++;
                       arlRegAniAdd3=new ArrayList();
                       arlRegAniAdd3.add(INT_ARL_ANI, "" + objTblModHis3.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                       arlRegAniAdd3.add(INT_ARL_NUM_MES, "" + objTblModHis3.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis3.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                       arlRegAniAdd3.add(INT_ARL_MES, "" + objTblModHis3.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis3.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                       arlRegAniAdd3.add(INT_ARL_COL, "" );
                       arlDatAniAdd3.add(arlRegAniAdd3);
                   }
               }
           }
           System.out.println("ARREGLO 3 TIENE: " + arlDatAniAdd3.toString());
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }
   
   
   private int numeroColumnasAdicionar4(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd4.clear();
       try{
           for(int i=(objTblModHis4.getRowCountTrue()-1); i>=0; i--){
               strLin=objTblModHis4.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis4.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
               if(strLin.equals("M")){
                   if(objTblModHis4.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                       intNumColAdd++;
                       arlRegAniAdd4=new ArrayList();
                       arlRegAniAdd4.add(INT_ARL_ANI, "" + objTblModHis4.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                       arlRegAniAdd4.add(INT_ARL_NUM_MES, "" + objTblModHis4.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis4.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                       arlRegAniAdd4.add(INT_ARL_MES, "" + objTblModHis4.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis4.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                       arlRegAniAdd4.add(INT_ARL_COL, "" );
                       arlDatAniAdd4.add(arlRegAniAdd4);
                   }
               }
           }
           System.out.println("ARREGLO 4 TIENE: " + arlDatAniAdd4.toString());
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }
   

    private boolean agregarColumnasAdicionadasAnios(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor=intNumColEst;

        try{
            objTblCelRenLblAni=new ZafTblCelRenLbl();
            objTblCelRenLblAni.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni.setTipoFormato(objTblCelRenLblAni.INT_FOR_NUM);
            objTblCelRenLblAni.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean agregarColumnasAdicionadasAniosTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    


    private boolean agregarColumnasAdicionadasAnios2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor2=intNumColFinFecCor;

        try{
            objTblCelRenLblAni2=new ZafTblCelRenLbl();
            objTblCelRenLblAni2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni2.setTipoFormato(objTblCelRenLblAni2.INT_FOR_NUM);
            objTblCelRenLblAni2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni2.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni2.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni2.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni2.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni2);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor2=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosTotales2(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAnios3(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor3=intNumColFinFecCor2;

        try{
            objTblCelRenLblAni3=new ZafTblCelRenLbl();
            objTblCelRenLblAni3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni3.setTipoFormato(objTblCelRenLblAni3.INT_FOR_NUM);
            objTblCelRenLblAni3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni3.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni3.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni3.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni3.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni3.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni3);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor3=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosTotales3(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean agregarColumnasAdicionadasAnios4(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor4=intNumColFinFecCor3;

        try{            
            objTblCelRenLblAni4=new ZafTblCelRenLbl();
            objTblCelRenLblAni4.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni4.setTipoFormato(objTblCelRenLblAni4.INT_FOR_NUM);
            objTblCelRenLblAni4.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni4.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni4.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni4.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni4.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni4.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni4);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor4=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean agregarColumnasAdicionadasAniosTotales4(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean agregarColumnasAdicionadasAniosMeses(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor=intNumColEst;

        try{
            objTblCelRenLblAniMes=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes.setTipoFormato(objTblCelRenLblAniMes.INT_FOR_NUM);
            objTblCelRenLblAniMes.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblAniMes.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;

                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    
    private boolean agregarColumnasAdicionadasAniosMeses2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor2=intNumColFinFecCor;

        try{
            objTblCelRenLblAniMes2=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes2.setTipoFormato(objTblCelRenLblAniMes2.INT_FOR_NUM);
            objTblCelRenLblAniMes2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblAniMes2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes2.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes2.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes2.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes2.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes2);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd2, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;

                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor2=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMeses3(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor3=intNumColFinFecCor2;

        try{
            objTblCelRenLblAniMes3=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes3.setTipoFormato(objTblCelRenLblAniMes3.INT_FOR_NUM);
            objTblCelRenLblAniMes3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblAniMes3.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes3.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes3.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes3.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes3.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes3);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd3, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;

                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor3=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean agregarColumnasAdicionadasAniosMeses4(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor4=intNumColFinFecCor3;

        try{
            objTblCelRenLblAniMes4=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes4.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes4.setTipoFormato(objTblCelRenLblAniMes4.INT_FOR_NUM);
            objTblCelRenLblAniMes4.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblAniMes4.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes4.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes4.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes4.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes4.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes4);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd4, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;

                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor4=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor+i))).setWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMaxWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMinWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor+i))).setPreferredWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales2(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMaxWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMinWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setPreferredWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales3(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMaxWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMinWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setPreferredWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales4(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMaxWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMinWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setPreferredWidth(0);
                        tblTot.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    

   private boolean agregarColumnasAdicionadasSumHisVta(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot=3;
        intNumColIniTot=intNumColFinFecCor4;

        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   private boolean agregarColumnasAdicionadasSumHisVta2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot2=3;
        intNumColIniTot2=intNumColFinTot;

        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot2; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales2");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot2=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }
   
   
   private boolean agregarColumnasAdicionadasSumHisVta3(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot3=3;
        intNumColIniTot3=intNumColFinTot2;

        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot3; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales3");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot3=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   private boolean agregarColumnasAdicionadasSumHisVta4(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot4=3;
        intNumColIniTot4=intNumColFinTot3;

        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot4; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales4");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot4=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   
   private boolean agregarColumnasAdicionadasSumHisVtaTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddTot; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   private boolean agregarColumnasAdicionadasSumHisVtaTotales2(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddTot2; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }
   
   private boolean agregarColumnasAdicionadasSumHisVtaTotales3(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddTot3; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   private boolean agregarColumnasAdicionadasSumHisVtaTotales4(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddTot4; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   
   
    private boolean agregarColumnasPromedioAnual(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddProAnu=6;//numero de columnas que se adicionan al modelo
        intNumColIniProAnu=intNumColFinTot4;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblProAnu=new ZafTblCelRenLbl();
            objTblCelRenLblProAnu.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblProAnu.setTipoFormato(objTblCelRenLblProAnu.INT_FOR_NUM);
            objTblCelRenLblProAnu.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            objTblCelRenLblProAnu.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblProAnu.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblProAnu.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblProAnu.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblProAnu.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelEdiTxtProAnu=new ZafTblCelEdiTxt();

            for (int i=0; i<intNumColAddProAnu; i++){
                String strSubTit=(i==0?"Cal1":(i==1?"Cal2":(i==2?"Cal3":(i==3?"Cal4":(i==4?"Máx.Cal.":"Manual")))));
                tbc=new javax.swing.table.TableColumn(intNumColIniProAnu+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblProAnu);
                tbc.setCellEditor(objTblCelEdiTxtProAnu);


                objTblCelEdiTxtProAnu.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {

                    BigDecimal bdePedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){

                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdePedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdePedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            
                            recalcularValoresTabla(tblDat.getSelectedRow());
                            
                        }
                        else{
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Promedio anual");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);         
            }
            intNumColFinProAnu=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean agregarColumnasPromedioAnualTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddProAnu; i++){
                String strSubTit=(i==0?"Calculado":"Manual");
                tbc=new javax.swing.table.TableColumn(intNumColIniProAnu+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);     
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }



    private boolean agregarColumnasStock(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddStk=2;//numero de columnas que se adicionan al modelo para stock
        intNumColIniStk=intNumColFinProAnu;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
            objTblCelRenLblStk=new ZafTblCelRenLbl();
            objTblCelRenLblStk.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStk.setTipoFormato(objTblCelRenLblStk.INT_FOR_NUM);
            objTblCelRenLblStk.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblStk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblStk.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblStk.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblStk.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblStk.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddStk; i++){
                String strSubTit=(i==0?"Actual":"Importaciones/Compras Locales");
                tbc=new javax.swing.table.TableColumn(intNumColIniStk+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblStk);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Stock");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinStk=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }


    private boolean agregarColumnasStockTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;

        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddStk; i++){
                String strSubTit=(i==0?"Actual":"Importaciones/Compras Locales");
                tbc=new javax.swing.table.TableColumn(intNumColIniStk+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean agregarColumnasPedidosEmbarcados(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;

        intNumColAddPedEmb=arlDatPedEmb.size();//
        intNumColIniPedEmb=intNumColFinStk;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPed="";
        String strFecEstPedEmb="";//fecha estimada de pedidoEmbarcado
        try{
            objTblCelRenLblPedEmb=new ZafTblCelRenLbl();
            objTblCelRenLblPedEmb.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedEmb.setTipoFormato(objTblCelRenLblPedEmb.INT_FOR_NUM);
            objTblCelRenLblPedEmb.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtPed=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblPedEmb.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblPedEmb.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblPedEmb.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblPedEmb.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblPedEmb.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<intNumColAddPedEmb; i++){
                strNomPed="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_PED);
                strFecEstPedEmb="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_FEC_EST_PED_EMB);
                    
                objUti.setStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_COL, "" + (intNumColIniPedEmb+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomPed);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblPedEmb);
               
                
                tbc.setCellEditor(objTblCelEdiTxtPed);
                objTblCelEdiTxtPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bdePedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){
                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdePedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdePedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                           
                            recalcularValoresTabla(tblDat.getSelectedRow());
                            
                        }
                        else{
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }

                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Pedidos en curso");
                    objTblHeaColGrpTit.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                }
                
                if(i<(intNumColAddPedEmb)-1){
                    objTblHeaColGrpFecEstEmb=new ZafTblHeaColGrp(strFecEstPedEmb);
                    objTblHeaColGrpFecEstEmb.setHeight(16*2);                
                    objTblHeaColGrpTit.add(objTblHeaColGrpFecEstEmb);
                    objTblHeaColGrpFecEstEmb.add(tbc);
                }
                else{
                    objTblHeaColGrpTit.add(tbc);
                }
                
                
            }
            
          
            intNumColFinPedEmb=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
            objTblHeaColGrpFecEstEmb=null;


        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean agregarColumnasPedidosEmbarcadosTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            objTblCelEdiTxtPed=new ZafTblCelEdiTxt();                   
            
            for (int i=0; i<intNumColAddPedEmb; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                tbc.setHeaderValue("");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);                
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean agregarColumnasPedidosPrevios(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;

        intNumColAddPedPre=arlDatPedPre.size();//
        intNumColIniPedPre=intNumColFinPedEmb;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPedPre="";
        try{
            objTblCelRenLblPedPre=new ZafTblCelRenLbl();
            objTblCelRenLblPedPre.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedPre.setTipoFormato(objTblCelRenLblPedPre.INT_FOR_NUM);
            objTblCelRenLblPedPre.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtPedPre=new ZafTblCelEdiTxt();
            
            objTblCelRenLblPedPre.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblPedPre.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblPedPre.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblPedPre.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblPedPre.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelEdiTxtPedPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdePedPre=new BigDecimal(BigInteger.ZERO);
                int intArlColSel=-1;

                int intCodEmpDatPedPre, intCodLocDatPedPre, intCodTipDocDatPedPre, intCodDocDatPedPre;
                int intCodEmpDatDetPedPre, intCodLocDatDetPedPre, intCodTipDocDatDetPedPre, intCodDocDatDetPedPre, intCodItmDatDetPedPre;
                int intCodItmFilSel;
                boolean blnExiCodItm;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){
                        //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                        bdePedPre=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                        objTblMod.setValueAt(""+bdePedPre, tblDat.getSelectedRow(), tblDat.getSelectedColumn());                           
                    }
                    else{
                        objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                    }

                    if(tblDat.getSelectedColumn()==(intNumColFinPedPre-2)){
                            intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM).toString());

                            //se añade ese nombre al arreglo q contine la informacion de los Pedidos Previos
                            //tblDat.getColumnModel().getColumn(tblDat.getSelectedColumn()).setHeaderValue(strNomPedPreIngLSC);
                            for(int j=0; j<arlDatPedPre.size();j++){
                                blnExiCodItm=false;
                                intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);

                                if(intArlColSel==tblDat.getSelectedColumn()){
                                    objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "I");

                                    for(int k=0; k<arlDatDetPedPre.size();k++){
                                        intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);
                                            if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                                //se modifica la cantidad en el arreglo
                                                objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                                objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                                blnExiCodItm=true;
                                                break;
                                            }
                                            else{
                                                blnExiCodItm=false;
                                            }
                                    }
                                    if(!blnExiCodItm){

                                        arlRegDetPedPre=new ArrayList();
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT_ITM).toString());
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                        arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                        arlDatDetPedPre.add(arlRegDetPedPre);
                                    }
                                }
                            }

                    }
                    else{
                        for(int j=0; j<arlDatPedPre.size();j++){
                            blnExiCodItm=false;
                            intCodEmpDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_EMP);
                            intCodLocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_LOC);
                            intCodTipDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                            intCodDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_DOC);

                            intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);
                            if(intArlColSel==tblDat.getSelectedColumn()){
                                objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "M");

                                //detalle
                                for(int k=0; k<arlDatDetPedPre.size();k++){
                                    intCodEmpDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_EMP);
                                    intCodLocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_LOC);
                                    intCodTipDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                                    intCodDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_DOC);
                                    intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);

                                    intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM).toString());

                                    if(  (intCodEmpDatPedPre==intCodEmpDatDetPedPre) && (intCodLocDatPedPre==intCodLocDatDetPedPre) && (intCodTipDocDatPedPre==intCodTipDocDatDetPedPre) && (intCodDocDatPedPre==intCodDocDatDetPedPre)   ){
                                        if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                            //se modifica la cantidad en el arreglo
                                            objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                            objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                            blnExiCodItm=true;
                                            break;
                                        }
                                        else{

                                            blnExiCodItm=false;
                                        }

                                    }                                                
                                }
                                if(!blnExiCodItm){
                                    System.out.println("A1");
                                    arlRegDetPedPre=new ArrayList();
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT_ITM).toString());
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                    arlDatDetPedPre.add(arlRegDetPedPre);
                                }
                            }
                        }
                    }
                    recalcularValoresTabla(tblDat.getSelectedRow());

                    System.out.println("***---------------------------------------***");
                    System.out.println("ERROR - arlDatPedPre: " + arlDatPedPre.toString());
                    System.out.println("ERROR - arlDatDetPedPre: " + arlDatDetPedPre.toString());
                    System.out.println("***---------------------------------------***");
                }





                //}

            });
            
            
            for (int i=0; i<intNumColAddPedPre; i++){
                intFilColSel=i;
                strNomPedPre="" + objUti.getStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_PED_PRE);
                objUti.setStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_COL, "" + (intNumColIniPedPre+i));
                tbc=new javax.swing.table.TableColumn(intNumColIniPedPre+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomPedPre);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblPedPre);
                //editor de la celda
                tbc.setCellEditor(objTblCelEdiTxtPedPre);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Notas de Pedido previas");
                    objTblHeaColGrp.setHeight(16);
                    
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);                
                
            }
          
            intNumColFinPedPre=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrp=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    
    private boolean agregarColumnasPedidosPreviosTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            objTblCelEdiTxtPedPre=new ZafTblCelEdiTxt();                   
            
            for (int i=0; i<intNumColAddPedPre; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniPedPre+i);
                tbc.setHeaderValue("");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);                
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    
    private boolean agregarColumnaDisponible(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddDis=1;//numero de columnas q se deben adicionar al modelo para datos de disponible y de venta
        intNumColIniDis=intNumColFinPedPre;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
            objTblCelRenLblDis=new ZafTblCelRenLbl();
            objTblCelRenLblDis.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDis.setTipoFormato(objTblCelRenLblDis.INT_FOR_NUM);
            objTblCelRenLblDis.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblDis.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblDis.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblDis.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblDis.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblDis.setBackground(Color.WHITE);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    recalcularValoresTabla(tblDat.getSelectedRow());                    
                }
            });
            
            for (int i=0; i<intNumColAddDis; i++){
                String strSubTit="Dis.Stk.Ped.Imp.";
                tbc=new javax.swing.table.TableColumn(intNumColIniDis+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblDis);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Disponible");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            intNumColFinDis=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    
    
    private boolean agregarColumnaVentaPromedio(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddVtaPro=2;//numero de columnas q se deben adicionar al modelo para datos de disponible y de venta
        intNumColIniVtaPro=intNumColFinDis;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
            objTblCelRenLblVtaPro=new ZafTblCelRenLbl();
            objTblCelRenLblVtaPro.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblVtaPro.setTipoFormato(objTblCelRenLblVtaPro.INT_FOR_NUM);
            objTblCelRenLblVtaPro.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblVtaPro.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblVtaPro.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblVtaPro.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblVtaPro.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblVtaPro.setBackground(Color.WHITE);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    recalcularValoresTabla(tblDat.getSelectedRow());                    
                }
            });
            
            for (int i=0; i<intNumColAddVtaPro; i++){
                String strSubTit=(i==0?"Cal.":"Man.");
                //String strSubTit=(i==0?"Cal1.":(i==1?"Cal2.":(i==2?"Cal3.":(i==3?"Cal4.":"Man."))));
                tbc=new javax.swing.table.TableColumn(intNumColIniVtaPro+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblVtaPro);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Venta Promedio");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            intNumColFinVtaPro=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
        
    
    
    

    private boolean agregarColumnasReposicion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddRep=3;//numero de columnas q se deben adicionar al modelo para datos de reposicion
        intNumColIniRep=intNumColFinVtaPro;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
            objTblCelRenLblRep=new ZafTblCelRenLbl();
            objTblCelRenLblRep.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblRep.setTipoFormato(objTblCelRenLblRep.INT_FOR_NUM);
            objTblCelRenLblRep.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblRep.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblRep.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblRep.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblRep.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblRep.setBackground(Color.WHITE);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    recalcularValoresTabla(tblDat.getSelectedRow());                    
                }
            });
            
            
            for (int i=0; i<intNumColAddRep; i++){
                String strSubTit=(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"));
                tbc=new javax.swing.table.TableColumn(intNumColIniRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblRep);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Reposición");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            intNumColFinRep=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    
    
    private boolean agregarColumnaDisponibleTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddDis; i++){
                String strSubTit="";//(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"))
                tbc=new javax.swing.table.TableColumn(intNumColIniDis+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean agregarColumnaVentaPromedioTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddVtaPro; i++){
                String strSubTit="";//(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"))
                tbc=new javax.swing.table.TableColumn(intNumColIniVtaPro+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    

    private boolean agregarColumnasReposicionTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddRep; i++){
                String strSubTit=(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"));
                tbc=new javax.swing.table.TableColumn(intNumColIniRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    
    
    private boolean agregarColumnasImportacion(){
        boolean blnRes=true;
        try{
            if(agregarColumnasImportacionPedido1()){
                if(agregarColumnasImportacionPedido2()){
                    
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    
    private boolean agregarColumnasImportacionTotales(){
        boolean blnRes=true;
        try{
            if(agregarColumnasImportacionPedido1Totales()){
                if(agregarColumnasImportacionPedido2Totales()){
                    
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
   

    private boolean agregarColumnasImportacionPedido1(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddImp=2;//son 5 columnas para cantidad a pedir para importar y 1 mas para el peso.
        intNumColIniImp_Ped1=intNumColFinRep;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
            objTblCelRenLblImpPed1=new ZafTblCelRenLbl();
            objTblCelRenLblImpPed1.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblImpPed1.setTipoFormato(objTblCelRenLblImpPed1.INT_FOR_NUM);
            objTblCelRenLblImpPed1.setFormatoNumerico("###,###,##0",false,true);
            
            objTblCelRenLblImpPed1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblImpPed1.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblImpPed1.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblImpPed1.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblImpPed1.setBackground(Color.WHITE);
                    }
                }
            });
            
            

            objTblCelEdiTxtImp1=new ZafTblCelEdiTxt();

            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+1)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped1+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblImpPed1);
                tbc.setCellEditor(objTblCelEdiTxtImp1);
                objTblCelEdiTxtImp1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bdePedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        System.out.println("1 beforeEdit - agregarColumnasImportacionPedido1: ");
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        System.out.println("1 afterEdit - agregarColumnasImportacionPedido1: ");
                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){

                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdePedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdePedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            
                            recalcularValoresTabla(tblDat.getSelectedRow());
                            
                        }
                        else{
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Pedido");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinImp_Ped1=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean agregarColumnasImportacionPedido1Totales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+1)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped1+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);

            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }       
    
    private boolean agregarColumnasImportacionPedido2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddImp=2;//son 2 columnas para cantidad a pedir para importar y 1 mas para el peso.
        intNumColIniImp_Ped2=intNumColFinImp_Ped1;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
            objTblCelRenLblImpPed2=new ZafTblCelRenLbl();
            objTblCelRenLblImpPed2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblImpPed2.setTipoFormato(objTblCelRenLblImpPed2.INT_FOR_NUM);
            objTblCelRenLblImpPed2.setFormatoNumerico("###,###,##0",false,true);
            
            objTblCelRenLblImpPed2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblImpPed2.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblImpPed2.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblImpPed2.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblImpPed2.setBackground(Color.WHITE);
                    }
                }
            });
            
            

            objTblCelEdiTxtImp2=new ZafTblCelEdiTxt();

            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+2)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblImpPed2);
                tbc.setCellEditor(objTblCelEdiTxtImp2);
                objTblCelEdiTxtImp2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bdePedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        System.out.println("2beforeEdit - calcularPesoCantidadImportar: ");
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){

                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdePedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdePedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());

                            recalcularValoresTabla(tblDat.getSelectedRow());
                            
                        }
                        else{
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Pedido");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinImp_Ped2=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean agregarColumnasImportacionPedido2Totales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+2)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    
    
    
    private boolean agregarColumnasItemEliminado(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddEstItmEli=1;//es una columna solo para cargar el estado del item, si esta eliminado o no (solo es visual la eliminacion)
        intNumColIniEstItmEli=intNumColFinImp_Ped2;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
            objTblCelRenLblItmEli=new ZafTblCelRenLbl();
            objTblCelRenLblItmEli.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblItmEli.setTipoFormato(objTblCelRenLblItmEli.INT_FOR_NUM);
            objTblCelRenLblItmEli.setFormatoNumerico("###,###,##0",false,true);
            
            objTblCelRenLblItmEli.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblItmEli.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblItmEli.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblItmEli.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblItmEli.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddEstItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniEstItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblItmEli);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

            
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMaxWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMinWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setPreferredWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setResizable(false);
                
                
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Estado");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            

            
            intNumColFinEstItmEli=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
            
            

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private boolean agregarColumnasItemEliminadoTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddEstItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniEstItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
                
                tblTot.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMaxWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMinWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setPreferredWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setResizable(false);
                
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
   
    

    
    private boolean agregarColumnasCheckBoxItemEliminado(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddChkItmEli=1;//es una columna solo para cargar el estado del item, si esta eliminado o no (solo es visual la eliminacion)
        intNumColIniChkItmEli=intNumColFinEstItmEli;//numero de columnas que tiene el modelo antes de adicionar las columnas

        try{
//            objTblCelRenLblItmEliChk=new ZafTblCelRenLbl();
//            objTblCelRenLblItmEliChk.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            objTblCelRenLblItmEliChk.setTipoFormato(objTblCelRenLblItmEliChk.INT_FOR_NUM);
//            objTblCelRenLblItmEliChk.setFormatoNumerico("###,###,##0",false,true);
//            
//            objTblCelRenLblItmEliChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                java.awt.Color colFonColCru;
//                String strEstItmEli="";
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
//                    
//                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblItmEliChk.getRowRender(), intNumColIniChkItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblItmEliChk.getRowRender(), intNumColIniChkItmEli).toString();
//
//                    if (strEstItmEli.equals("S")){
//                        objTblCelRenLblItmEliChk.setBackground(colFonColCru);
//                    }
//                    else{
//                        objTblCelRenLblItmEliChk.setBackground(Color.WHITE);
//                    }
//                }
//            });
            
            objTblCelRenChkEstEliItm=new ZafTblCelRenChk();
            objTblCelRenChkEstEliItm.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenChkEstEliItm.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenChkEstEliItm.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenChkEstEliItm.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenChkEstEliItm.setBackground(Color.WHITE);
//                        //Mostrar ToolTipText con el contenido de la celda.
                      objTblCelRenChkEstEliItm.setToolTipText("Celda seleccionada: " + objTblCelRenChkEstEliItm.isSelected());
                    }
                }
            });            
            
            
            objTblCelEdiChkEstEliItm=new ZafTblCelEdiChk(tblDat);
            objTblCelEdiChkEstEliItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int fila=-1;
                int columna=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            

            for (int i=0; i<intNumColAddChkItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniChkItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenChkEstEliItm);
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiChkEstEliItm);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
//                //para ocultar la columna
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMaxWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMinWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setPreferredWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setResizable(false);
                
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Estado");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            

            
            intNumColFinChkItmEli=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
            
            

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    
    
    private boolean agregarColumnasCheckBoxItemEliminadoTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddChkItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniChkItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbc);
                //para ocultar la columna
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMaxWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMinWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setPreferredWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setResizable(false);
                
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    
    
    

    private boolean consultarRegAniMesGrp(){
        boolean blnRes=true;
        int intAni=0, intAniAnt=0;
        int intMes=0, intMesAnt=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intTerTmp=1;
        BigDecimal bdeValPedCur=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValPedImp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValPedPre=new BigDecimal(BigInteger.ZERO);

        try{
            if(con!=null){
                lblMsgSis.setText("Obteniendo datos...");
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strFilItm+="   AND a1.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strFilItm+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                if (txtCodAltItmTer.getText().length()>0)
                    strFilItm+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());   
                
                //panel 1 de Histórico de Ventas
                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    
                    strAux+="		LEFT OUTER JOIN(";

                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT  a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3";
                    strAux+="                           ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                           " + strFilItm + "";
                    strAux+="                           AND a2.co_bod IN(1,2,3,5,11,15,28,31,32)";
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                           , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                               , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                               , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                               , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                               , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                               , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1";
                    strAux+="                               ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                               , a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";
                    
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_salUni) AS " + "b" + (j+2) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_uniVen) AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numVec) AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numMes) AS " + "b" + (j+2) + "_nenumMes" ;                    
                }
                int intSizPan1=arlDatAniAdd.size();
                //panel 2 de Histórico de Ventas 
                for(int j=0; j<arlDatAniAdd2.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd2, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd2, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd2, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd2, j, INT_ARL_NUM_MES);
                    
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd2, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd2, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd2, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd2, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    strAux+="		LEFT OUTER JOIN(";

                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT  a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3";
                    strAux+="                           ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                           " + strFilItm + "";
                    strAux+="                           AND a2.co_bod IN(1,2,3,5,11,15,28,31,32)";
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                           , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                               , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                               , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                               , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                               , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                               , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1";
                    strAux+="                               ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                               , a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";

                    strAux+=" 		) AS b" + (j+2+intSizPan1) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan1) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan1) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan1) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".nd_salUni) AS " + "b" + (j+2+intSizPan1) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan1) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".ne_numVec) AS " + "b" + (j+2+intSizPan1) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".ne_numMes) AS " + "b" + (j+2+intSizPan1) + "_nenumMes" ;                    
                }
                int intSizPan3=(arlDatAniAdd.size()+arlDatAniAdd2.size());
                //panel 3 de Histórico de Ventas 
                for(int j=0; j<arlDatAniAdd3.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd3, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd3, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd3, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd3, j, INT_ARL_NUM_MES);
                    
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd3, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd3, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd3, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd3, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    strAux+="		LEFT OUTER JOIN(";

                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT  a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3";
                    strAux+="                           ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                           " + strFilItm + "";
                    strAux+="                           AND a2.co_bod IN(1,2,3,5,11,15,28,31,32)";
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                           , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                               , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                               , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                               , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                               , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                               , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1";
                    strAux+="                               ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                               , a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";

                    strAux+=" 		) AS b" + (j+2+intSizPan3) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan3) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan3) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan3) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_salUni) AS " + "b" + (j+2+intSizPan3) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan3) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numVec) AS " + "b" + (j+2+intSizPan3) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numMes) AS " + "b" + (j+2+intSizPan3) + "_nenumMes" ;                    
                }
                int intSizPan4=(arlDatAniAdd.size()+arlDatAniAdd2.size()+arlDatAniAdd3.size()+arlDatAniAdd4.size());
                //panel 2 de Histórico de Ventas 
                for(int j=0; j<arlDatAniAdd4.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd4, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd4, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd4, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd4, j, INT_ARL_NUM_MES);
                    
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd4, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd4, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd4, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd4, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    strAux+="		LEFT OUTER JOIN(";

                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT  a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3";
                    strAux+="                           ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                           " + strFilItm + "";
                    strAux+="                           AND a2.co_bod IN(1,2,3,5,11,15,28,31,32)";
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                           , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                               , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                               , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                               , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                               , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                               , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1";
                    strAux+="                               ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                               , a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                   , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";

                    strAux+=" 		) AS b" + (j+2+intSizPan4) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan4) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan4) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan4) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_salUni) AS " + "b" + (j+2+intSizPan4) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan4) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numVec) AS " + "b" + (j+2+intSizPan4) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numMes) AS " + "b" + (j+2+intSizPan4) + "_nenumMes" ;                    
                }
                
                strAux+="   WHERE b1.st_reg NOT IN('U','T','E','I') AND b1.st_ser NOT IN('S','T') ";
                strAux+="   GROUP BY b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.st_itmEli, b1.tx_nomItm, b1.tx_desCor, b1.nd_pesitmkgr, b1.nd_preVta1, b1.nd_stkAct, b1.nd_stkImp ";
                strAux+=" 	, b1.nd_proAnuVenMan";

                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }

                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                                
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="SELECT b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_pesitmkgr, b1.nd_preVta1";
                strSQL+="            " + strCamSel + " ";
                strSQL+="   , b1.nd_stkAct, b1.nd_stkImp";
                strSQL+=" 	, b1.nd_proAnuVenMan";               
                
                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }                
                
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";                    
                    intTerTmp++;
                }
                
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	, b1.st_itmeli";//aqui
                }
                
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++){
                    strSQL+=" 	," + new Boolean(false);//aqui
                }                
                
                strSQL+=" FROM(";
                strSQL+=" SELECT g1.*, h1.nd_stkAct, h1.nd_stkImp FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, c1.co_itmMae";
                strSQL+=" 	, a1.nd_proAnuVenMan";


                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	, a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }     
                
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	, a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	, a1.st_itmeli";//aqui
                }
                
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++){
                    strSQL+=" 	, " + new Boolean(false);//aqui
                }
                
                strSQL+=" 		FROM (     (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                strSQL+="                               INNER JOIN tbm_equInv AS c1";
                strSQL+="                               ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm";
                strSQL+="                     )";
                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                if(chkItmNotEli.isSelected()){//mostrar solo los items que no han sido eliminados
                    strSQL+=" 		AND a1.st_itmEli IS NULL";
                }
                strSQL+="               " + strFilItm + "";
                strSQL+=" ) AS g1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 			SELECT j1.*, k1.nd_stkImp FROM(";
                strSQL+="                                     SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct";
                strSQL+="                                      FROM(";
                strSQL+="                                      	SELECT x.*, y.co_empGrp FROM(";
                strSQL+="                                      		SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae";
                strSQL+="                                      		FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                                      		ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                                      	) AS x";
                strSQL+="                                      	INNER JOIN(";
                strSQL+="                                      		SELECT co_emp, co_bod, co_empGrp";
                strSQL+="                                      		FROM tbr_bodEmpBodGrp";
                strSQL+="                                          		WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                          AND co_bodGrp IN(1,2,3,5,11,15,28,31,32)";//26
                strSQL+="                                      		ORDER BY co_emp";
                strSQL+="                                      	) AS y";
                strSQL+="                                      	ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="                                      ) AS z";
                strSQL+=" 				    GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+=" 			    ) AS j1";
                strSQL+=" 			    INNER JOIN(";
                strSQL+="                                     SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkImp) AS nd_stkImp";
                strSQL+="                                      FROM(";
                strSQL+="                                      	SELECT x.*, y.co_empGrp FROM(";
                strSQL+="                                      		SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct AS nd_stkImp, a2.co_itmMae";
                strSQL+="                                      		FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                                      		ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                                      	) AS x";
                strSQL+="                                      	INNER JOIN(";
                strSQL+="                                      		SELECT co_emp, co_bod, co_empGrp";
                strSQL+="                                      		FROM tbr_bodEmpBodGrp";
                strSQL+="                                          		WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                          AND co_bodGrp IN(6,4)";// antes 6:Importaciones ; ahora se agrego 4:ComprasLocales
                strSQL+="                                      		ORDER BY co_emp";
                strSQL+="                                      	) AS y";
                strSQL+="                                      	ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="                                      ) AS z";
                strSQL+=" 				    GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+=" 			    ) AS k1";
                strSQL+=" 			    ON j1.co_itmMae=k1.co_itmMae";
                strSQL+="          ) AS h1";
                strSQL+="          ON g1.co_itmMae=h1.co_itmMae";
                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                System.out.println("consultarRegAniMesGrp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                BigDecimal bdeTotPed=new BigDecimal(BigInteger.ZERO);
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,     "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,     "" + rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
                        //vecReg.add(INT_TBL_DAT_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
                        vecReg.add(INT_TBL_DAT_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
                        
                        
                        //adicionar las columnas de fecha de corte  panel 1
                        for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        //adicionar las columnas de fecha de corte  panel 2
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        //adicionar las columnas de fecha de corte  panel 3
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        //adicionar las columnas de fecha de corte  panel 4
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        //adicionar las columnas de totales 1
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "0");
                        }                        
                        //adicionar las columnas de totales 2
                        for(int j=intNumColIniTot2; j<intNumColFinTot2;j++){
                            vecReg.add(j,     "0");
                        }                        
                        //adicionar las columnas de totales 3
                        for(int j=intNumColIniTot3; j<intNumColFinTot3;j++){
                            vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de totales 4
                        for(int j=intNumColIniTot4; j<intNumColFinTot4;j++){
                            vecReg.add(j,     "0");
                        }
                        
                        //System.out.println("Promedio anual venta: " + rst.getObject("nd_proAnuVenMan"));
                        //adicionar las columnas de promedio anual                         
                        
                        for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                            if(j==(intNumColFinProAnu-1))
                                vecReg.add(j,     "" + rst.getObject("nd_proAnuVenMan")==null?"0":rst.getString("nd_proAnuVenMan"));
                            else
                                vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de stock
                        for(int j=intNumColIniStk; j<intNumColFinStk;j++){
                            if(j==intNumColIniStk)
                                vecReg.add(j,     rst.getObject("nd_stkAct")==null?"0":rst.getString("nd_stkAct"));
                            else
                                vecReg.add(j,     rst.getObject("nd_stkImp")==null?"0":rst.getString("nd_stkImp"));
                        }
                        
                        //pedidos en curso
                        for(int d=0; d<arlDatPedEmb.size(); d++){
                            vecReg.add((intNumColIniPedEmb+d),     "");
                        }
                        
                        //pedidos previos
                        for(int d=0; d<arlDatPedPre.size(); d++){
                            vecReg.add((intNumColIniPedPre+d),     "");
                        }

                        //adicionar las columnas de disponible/venta en base a los datos ingresados o presentados
                        for(int j=intNumColIniDis; j<intNumColFinDis;j++){
                                vecReg.add(j,     "0");
                        }
                        
                        //adicionar las columnas de disponible/venta en base a los datos ingresados o presentados
                        for(int j=intNumColIniVtaPro; j<intNumColFinVtaPro;j++){
                                vecReg.add(j,     "0");
                        }                        
                        
                        //adicionar las columnas de reposicion calculado en base a los datos ingresados o presentados
                        for(int j=intNumColIniRep; j<intNumColFinRep;j++){
                            if(j==intNumColIniRep)
                                vecReg.add(j,     jspNumMesRep.getValue());//coloca el valor ingresado por el usuario en "numero de meses a reponer"
                            else
                                vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de importaciones
                        intTerTmp=1;

                        for(int j=intNumColIniImp_Ped1; j<(intNumColFinImp_Ped1-1);j++){
                            bdeValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": ((rst.getString("nd_canPed" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canPed" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                            vecReg.add(j,     "" + bdeValPedImp );
                            bdeValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": ((rst.getString("nd_canpedpes" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                            vecReg.add((j+1),     "" + bdeValPedImp );
                            intTerTmp++;
                        }
                        
                        for(int j=intNumColIniImp_Ped2; j<(intNumColFinImp_Ped2-1);j++){
                            bdeValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": ((rst.getString("nd_canPed" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canPed" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                            vecReg.add(j,     "" + bdeValPedImp );
                            bdeValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": ((rst.getString("nd_canpedpes" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                            vecReg.add((j+1),     "" + bdeValPedImp );
                            intTerTmp++;
                        }
                        
                        for(int j=intNumColIniEstItmEli; j<intNumColFinEstItmEli;j++){
                            vecReg.add(j,     "" + (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ) );//aqui
                        }
                                
                        for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++){
                            vecReg.add(j,     (  (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ).equals("S")?new Boolean(true):new Boolean(false)  ) );//aqui
                        }
                                
                        vecDat.add(vecReg);

                        //TABLA FIXED
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_DAT_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ITM,     "" + rst.getString("co_itm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecRegFix.add(INT_TBL_DAT_FIX_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_UNI_MED,     "" + rst.getString("tx_desCor"));
                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
                        //vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + rst.getObject("st_itmeli")==null?"0":rst.getString("st_itmeli"));
                        vecDatFix.add(vecRegFix);

                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                vecAux=new Vector();
                //adicionar las columnas de promedio anual
                for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                    if(objParSis.getCodigoUsuario()==1){
                        if(j==(intNumColFinProAnu-1))
                            vecAux.add(""+j);
                    }
                    else{
                        if(objPerUsr.isOpcionEnabled(4072)){
                            if(j==(intNumColFinProAnu-1)){
                                vecAux.add(""+j);
                            }
                        }
                    }
                }

                //adicionar las columnas de importacion
                for(int j=intNumColIniImp_Ped1; j<intNumColFinImp_Ped1;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }
                    

                }
                for(int j=intNumColIniImp_Ped2; j<intNumColFinImp_Ped2;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                }
                              
                for(int j=intNumColIniPedPre; j<intNumColFinPedPre;j++){
                    if(j!=(intNumColFinPedPre-1))
                        vecAux.add(""+j);
                }
                
                for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++){
                        vecAux.add(""+j);
                }
                        
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();

                //se cargan los datos de valores del pedido embarcado
                int intCodItmTbl=0;
                int intCodItmArl=0;
                
                String strEstPedEmb="", strEstPedPre="";
                int intColPedEmb=0, intColPedPre=0;

                //se carga la informacion de los pedidos en las columnas necesarias
                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    
//                    System.out.println("d: " + d);
                    
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
//                    System.out.println("intCodItmTbl: " + intCodItmTbl);
                    
                    for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_COD_ITM);
                        strEstPedEmb=objUti.getStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST);
                        intColPedEmb=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_NUM_COL);                        
                        if(strEstPedEmb.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "S");
                                bdeValPedCur=objUti.getBigDecimalValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_CAN_PED_EMB);

                                objTblMod.setValueAt(bdeValPedCur, d, intColPedEmb);

                                bdeTotPed=bdeTotPed.add(bdeValPedCur);
                                bdeValPedCur=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                    }
                }

                
                intCodItmTbl=0;
                intCodItmArl=0;
                //se carga la información de los pedidos previos en las columnas necesarias
                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
                    for(int e=0; e<(arlDatDetPedPre.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_COD_ITM);
                        strEstPedPre=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST);
                        intColPedPre=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_NUM_COL);                        
                        if(strEstPedPre.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "S");
                                bdeValPedPre=objUti.getBigDecimalValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_CAN_PED_PRE);

                                objTblMod.setValueAt(bdeValPedPre, d, intColPedPre);

                                //bdeTotPed=bdeTotPed.add(bdeValPedPre);
                                bdeValPedPre=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                    }
                }
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "");
                }
                
                
                
                //lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
             
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo:
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }


    private boolean consultarRegAniGrp(){
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intTerTmp=0;
        BigDecimal bdeValPedCur=new BigDecimal(BigInteger.ZERO);//pedido en curso
        BigDecimal bdeValPedImp=new BigDecimal(BigInteger.ZERO);//pedido a importar
        BigDecimal bdeValPedPre=new BigDecimal(BigInteger.ZERO);
        try{
            if(con!=null){
                lblMsgSis.setText("Obteniendo datos...");
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strFilItm+="   AND a1.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strFilItm+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                if (txtCodAltItmTer.getText().length()>0)
                    strFilItm+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());

                //panel 1 de Histórico de Ventas
                for(int j=0; j<arlDatAniAdd.size(); j++){
                    //intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                       , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                     , a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_salUni) AS " + "b" + (j+2) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_uniVen) AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numVec) AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numMes) AS " + "b" + (j+2) + "_nenumMes" ;
                }
                
                int intSizPan1=arlDatAniAdd.size();
                //panel 2 de Histórico de Ventas
                for(int j=0; j<arlDatAniAdd2.size(); j++){                   
                    intAni=objUti.getObjectValueAt(arlDatAniAdd2, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd2, j, INT_ARL_ANI);
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                       , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                     , a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2+intSizPan1) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan1) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan1) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan1) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".nd_salUni) AS " + "b" + (j+2+intSizPan1) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan1) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".ne_numVec) AS " + "b" + (j+2+intSizPan1) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan1) + ".ne_numMes) AS " + "b" + (j+2+intSizPan1) + "_nenumMes" ;
                }
                int intSizPan3=(arlDatAniAdd.size()+arlDatAniAdd2.size());
                //panel 3 de Histórico de Ventas
                for(int j=0; j<arlDatAniAdd3.size(); j++){                   
                    intAni=objUti.getObjectValueAt(arlDatAniAdd3, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd3, j, INT_ARL_ANI);
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                       , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                     , a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2+intSizPan3) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan3) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan3) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan3) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_salUni) AS " + "b" + (j+2+intSizPan3) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan3) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numVec) AS " + "b" + (j+2+intSizPan3) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numMes) AS " + "b" + (j+2+intSizPan3) + "_nenumMes" ;
                }
                int intSizPan4=(arlDatAniAdd.size()+arlDatAniAdd2.size()+arlDatAniAdd3.size()+arlDatAniAdd4.size());
                //panel 4 de Histórico de Ventas 
                for(int j=0; j<arlDatAniAdd4.size(); j++){                   
                    intAni=objUti.getObjectValueAt(arlDatAniAdd4, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd4, j, INT_ARL_ANI);
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                       , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                     , a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2+intSizPan4) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan4) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan4) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan4) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_salUni) AS " + "b" + (j+2+intSizPan4) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan4) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numVec) AS " + "b" + (j+2+intSizPan4) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numMes) AS " + "b" + (j+2+intSizPan4) + "_nenumMes" ;
                }
                
                strAux+="   WHERE b1.st_reg NOT IN('U','T','E','I') AND b1.st_ser NOT IN('S','T') ";

                strAux+=" GROUP BY b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.st_itmEli, b1.tx_nomItm, b1.tx_desCor, b1.nd_pesitmkgr, b1.nd_preVta1, b1.nd_stkAct, b1.nd_stkImp";
                strAux+=" 	, b1.nd_proAnuVenMan";

                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="SELECT b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_pesitmkgr, b1.nd_preVta1";
                strSQL+="            " + strCamSel + " ";
                strSQL+="   , b1.nd_stkAct, b1.nd_stkImp";
                strSQL+=" 	, b1.nd_proAnuVenMan";
                

                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	, b1.st_itmeli";//aqui
                }
                
                
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++){
                    strSQL+=" 	, " + new Boolean(false);//aqui
                }

                strSQL+=" FROM(";
                strSQL+=" SELECT g1.*, h1.nd_stkAct, h1.nd_stkImp FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, c1.co_itmMae";
                strSQL+=" 	, a1.nd_proAnuVenMan";

                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	, a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	, a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	, a1.st_itmeli";//aqui
                }
                
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++){
                    strSQL+=" 	, " + new Boolean(false);//aqui
                }
                
                
                strSQL+=" 		FROM (     (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                strSQL+="                               INNER JOIN tbm_equInv AS c1";
                strSQL+="                               ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm";
                strSQL+="                     )";
                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";

                if(chkItmNotEli.isSelected()){//mostrar solo los items que no han sido eliminados
                    strSQL+=" 		AND a1.st_itmEli IS NULL";
                }
                strSQL+="               " + strFilItm + "";
                strSQL+=" ) AS g1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 			SELECT j1.*, k1.nd_stkImp FROM(";
                strSQL+="                                     SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct";
                strSQL+="                                      FROM(";
                strSQL+="                                      	SELECT x.*, y.co_empGrp FROM(";
                strSQL+="                                      		SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae";
                strSQL+="                                      		FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                                      		ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                                      	) AS x";
                strSQL+="                                      	INNER JOIN(";
                strSQL+="                                      		SELECT co_emp, co_bod, co_empGrp";
                strSQL+="                                      		FROM tbr_bodEmpBodGrp";
                strSQL+="                                          		WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                          AND co_bodGrp IN(1,2,3,5,11,15,28,31,32)";//26
                strSQL+="                                      		ORDER BY co_emp";
                strSQL+="                                      	) AS y";
                strSQL+="                                      	ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="                                      ) AS z";
                strSQL+=" 				    GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+=" 			    ) AS j1";
                strSQL+=" 			    INNER JOIN(";
                strSQL+="                                     SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkImp) AS nd_stkImp";
                strSQL+="                                      FROM(";
                strSQL+="                                      	SELECT x.*, y.co_empGrp FROM(";
                strSQL+="                                      		SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct AS nd_stkImp, a2.co_itmMae";
                strSQL+="                                      		FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                                      		ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                                      	) AS x";
                strSQL+="                                      	INNER JOIN(";
                strSQL+="                                      		SELECT co_emp, co_bod, co_empGrp";
                strSQL+="                                      		FROM tbr_bodEmpBodGrp";
                strSQL+="                                          		WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                          AND co_bodGrp IN(6,4)";
                strSQL+="                                      		ORDER BY co_emp";
                strSQL+="                                      	) AS y";
                strSQL+="                                      	ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="                                      ) AS z";
                strSQL+=" 				    GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+=" 			    ) AS k1";
                strSQL+=" 			    ON j1.co_itmMae=k1.co_itmMae";
                strSQL+="          ) AS h1";
                strSQL+="          ON g1.co_itmMae=h1.co_itmMae";
                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                System.out.println("consultarRegAniGrp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                BigDecimal bdeTotPed=new BigDecimal(BigInteger.ZERO);
                while(rst.next()){
                    if (blnCon){
                        //para cargar los datos en la tabla de datos
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,     "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,     "" + rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
                        //vecReg.add(INT_TBL_DAT_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
                        vecReg.add(INT_TBL_DAT_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
                        
                        
                        System.out.println("PrecioVta: " + rst.findColumn("nd_preVta1"));
                        
                        
                        /////////////////////////////////////////////////////////////////////////////////////////
                        ////////////////////////////////////////////////////////////////////////////////////////
                        
//                        for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++)
//                            System.out.println("j: " + (j+1));
//
//                        System.out.println("--------");
//                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
//                            System.out.println("j: " + (j+1));
//                        }
//                        //adicionar las columnas de fecha de corte panel 3
//                        System.out.println("--------");
//                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
//                            System.out.println("j: " + (j+1));
//                        }
//                        //adicionar las columnas de fecha de corte panel 4
//                        System.out.println("--------");
//                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
//                            System.out.println("j: " + (j+1));
//                        }

                        
                        ////////////////////////////////////////////////////////////////////////////////////////
                        ////////////////////////////////////////////////////////////////////////////////////////
                        
                        //adicionar las columnas de fecha de corte panel 1                  
                        for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        //adicionar las columnas de fecha de corte panel 2
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        //adicionar las columnas de fecha de corte panel 3
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        
                        System.out.println("modelo: " + objTblMod.getColumnCount());
                        System.out.println("intNumColIniFecCor4: " + intNumColIniFecCor4);
                        System.out.println("intNumColFinFecCor4: " + intNumColFinFecCor4);
                        
                        
                        //adicionar las columnas de fecha de corte panel 4
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                            vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));
                        }
                        //adicionar las columnas de totales 1
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de totales 2
                        for(int j=intNumColIniTot2; j<intNumColFinTot2;j++){
                            vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de totales 3
                        for(int j=intNumColIniTot3; j<intNumColFinTot3;j++){
                            vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de totales 4
                        for(int j=intNumColIniTot4; j<intNumColFinTot4;j++){
                            vecReg.add(j,     "0");
                        }
                        
                        //adicionar las columnas de promedio anual
                        for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                            if(j==(intNumColFinProAnu-1))
                                vecReg.add(j,     "" + rst.getObject("nd_proAnuVenMan")==null?"0":rst.getString("nd_proAnuVenMan"));
                            else
                                vecReg.add(j,     "0");

                        }
                        //adicionar las columnas de stock
                        for(int j=intNumColIniStk; j<intNumColFinStk;j++){
                            if(j==intNumColIniStk)
                                vecReg.add(j,     rst.getObject("nd_stkAct")==null?"0":rst.getString("nd_stkAct"));
                            else
                                vecReg.add(j,     rst.getObject("nd_stkImp")==null?"0":rst.getString("nd_stkImp"));
                        }
                        //adicionar las columnas de pedidos en curso
                        for(int d=0; d<arlDatPedEmb.size(); d++){
                            vecReg.add((intNumColIniPedEmb+d),     "");
                        }
                        
                        
                        
                        for(int d=0; d<arlDatPedPre.size(); d++){
                            vecReg.add((intNumColIniPedPre+d),     "");
                        }

                        //adicionar las columnas de reposicion calculado en base a los datos ingresados o presentados
                        for(int j=intNumColIniDis; j<intNumColFinDis;j++){
                                vecReg.add(j,     "0");
                        }

                        //adicionar las columnas de reposicion calculado en base a los datos ingresados o presentados
                        for(int j=intNumColIniVtaPro; j<intNumColFinVtaPro;j++){
                                vecReg.add(j,     "0");
                        }
                    
                        //adicionar las columnas de reposicion calculado en base a los datos ingresados o presentados
                        for(int j=intNumColIniRep; j<intNumColFinRep;j++){
                            if(j==intNumColIniRep)
                                vecReg.add(j,     jspNumMesRep.getValue());//coloca el valor ingresado por el usuario en "numero de meses a reponer"
                            else
                                vecReg.add(j,     "0");
                        }
                        //adicionar 
                        intTerTmp=1;
                        for(int j=intNumColIniImp_Ped1; j<(intNumColFinImp_Ped1-1);j++){
                                bdeValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": (rst.getString("nd_canPed" + intTerTmp + "").equals("")?"0":rst.getString("nd_canPed" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                                vecReg.add(j,     "" + bdeValPedImp );
                                bdeValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": (rst.getString("nd_canpedpes" + intTerTmp + "").equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                                vecReg.add((j+1),     "" + bdeValPedImp );
                                intTerTmp++;

                        }
                        for(int j=intNumColIniImp_Ped2; j<(intNumColFinImp_Ped2-1);j++){
                                bdeValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": (rst.getString("nd_canPed" + intTerTmp + "").equals("")?"0":rst.getString("nd_canPed" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                                vecReg.add(j,     "" + bdeValPedImp );
                                bdeValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": (rst.getString("nd_canpedpes" + intTerTmp + "").equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                                vecReg.add((j+1),     "" + bdeValPedImp );
                                intTerTmp++;

                        }
                        
                        
                        for(int j=intNumColIniEstItmEli; j<intNumColFinEstItmEli;j++){
                            vecReg.add(j,     "" + (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ) );//aqui
                        }
                              
                        for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++){
                            vecReg.add(j,     (  (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ).equals("S")?new Boolean(true):new Boolean(false)  ) );//aqui
                        }
                     
                        vecDat.add(vecReg);
//                        System.out.println("Tamaño consultarRegAniGrp: " + vecDat.size());

                        //para cargar los datos en la tabla fija
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_DAT_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ITM,     "" + rst.getString("co_itm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecRegFix.add(INT_TBL_DAT_FIX_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_UNI_MED,     "" + rst.getString("tx_desCor"));
                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
                        //vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + rst.getObject("st_itmeli")==null?"0":rst.getString("st_itmeli"));
                        vecDatFix.add(vecRegFix);

                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                vecAux=new Vector();
                //adicionar las columnas de promedio anual
                for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){

                    if(objParSis.getCodigoUsuario()==1){
                        if(j==(intNumColFinProAnu-1))
                            vecAux.add(""+j);
                    }
                    else{
                        if(objPerUsr.isOpcionEnabled(4072)){
                            if(j==(intNumColFinProAnu-1))
                                vecAux.add(""+j);
                        }
                    }
                    
                    
                    
                    
                }

                //adicionar las columnas de importacion
                for(int j=intNumColIniImp_Ped1; j<intNumColFinImp_Ped1;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }

                }
                for(int j=intNumColIniImp_Ped2; j<intNumColFinImp_Ped2;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                }

                for(int j=intNumColIniPedPre; j<intNumColFinPedPre;j++){
                    if(j!=(intNumColFinPedPre-1))
                        vecAux.add(""+j);
                }
                
                for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++){
                        vecAux.add(""+j);
                }
                
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                //objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();


                //tabla fija
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();

                //se cargan los datos de valores del pedido embarcado
                int intCodItmTbl=0;
                int intCodItmArl=0;
                
                String strEstPedEmb="", strEstPedPre="";
                int intColPedEmb=0, intColPedPre=0;

                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
                    for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_COD_ITM);
                        strEstPedEmb=objUti.getStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST);
                        intColPedEmb=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_NUM_COL);
                        
                        if(strEstPedEmb.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "S");
                                bdeValPedCur=objUti.getBigDecimalValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_CAN_PED_EMB);

                                objTblMod.setValueAt(bdeValPedCur, d, intColPedEmb);

                                bdeTotPed=bdeTotPed.add(bdeValPedCur);

                            }
                        }

                    }

                }

                intCodItmTbl=0;
                intCodItmArl=0;
                //se carga la información de los pedidos previos en las columnas necesarias
                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
                    for(int e=0; e<(arlDatDetPedPre.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_COD_ITM);
                        strEstPedPre=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST);
                        intColPedPre=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_NUM_COL);                        
                        if(strEstPedPre.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "S");
                                bdeValPedPre=objUti.getBigDecimalValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_CAN_PED_PRE);

                                objTblMod.setValueAt(bdeValPedPre, d, intColPedPre);

                                //bdeTotPed=bdeTotPed.add(bdeValPedPre);
                                bdeValPedPre=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                    }
                }
                
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "");
                }
                
                
                //lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private boolean calcularColumnaTotalesAniMes_1(){
        boolean blnRes=true;
        BigDecimal bdeSumStk1=new BigDecimal(0);
        BigDecimal bdeSumUni1=new BigDecimal(0);
        BigDecimal bdeSumVec1=new BigDecimal(0);
        BigDecimal bdeSumMes1=new BigDecimal(0);
        BigDecimal bdeSumStk2=new BigDecimal(0);
        BigDecimal bdeSumUni2=new BigDecimal(0);
        BigDecimal bdeSumVec2=new BigDecimal(0);
        BigDecimal bdeSumMes2=new BigDecimal(0);
        BigDecimal bdeSumStk3=new BigDecimal(0);
        BigDecimal bdeSumUni3=new BigDecimal(0);
        BigDecimal bdeSumVec3=new BigDecimal(0);
        BigDecimal bdeSumMes3=new BigDecimal(0);
        BigDecimal bdeSumStk4=new BigDecimal(0);
        BigDecimal bdeSumUni4=new BigDecimal(0);
        BigDecimal bdeSumVec4=new BigDecimal(0);
        BigDecimal bdeSumMes4=new BigDecimal(0);
        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //Totales 1
                for(int j=intNumColIniFecCor; j<intNumColFinFecCor; j++){
                    //stock
                    bdeSumStk1=bdeSumStk1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //unidades
                    bdeSumUni1=bdeSumUni1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //veces
                    bdeSumVec1=bdeSumVec1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //meses
                    bdeSumMes1=bdeSumMes1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                bdeSumStk1=new BigDecimal(0);
                bdeSumUni1=new BigDecimal(0);
                bdeSumVec1=new BigDecimal(0);
                bdeSumMes1=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis2.isDataModelChanged()){
                    if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2; j++){
                            //stock
                            bdeSumStk2=bdeSumStk2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni2=bdeSumUni2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec2=bdeSumVec2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes2=bdeSumMes2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2+3));//Meses

                        bdeSumStk2=new BigDecimal(0);
                        bdeSumUni2=new BigDecimal(0);
                        bdeSumVec2=new BigDecimal(0);
                        bdeSumMes2=new BigDecimal(0);
                    }
                }
                
                
                //Totales 3
                if(objTblModHis3.isDataModelChanged()){
                    if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3; j++){
                            //stock
                            bdeSumStk3=bdeSumStk3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni3=bdeSumUni3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec3=bdeSumVec3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes3=bdeSumMes3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3+3));//Meses

                        bdeSumStk3=new BigDecimal(0);
                        bdeSumUni3=new BigDecimal(0);
                        bdeSumVec3=new BigDecimal(0);
                        bdeSumMes3=new BigDecimal(0);
                    }
                }
                
                
                //Totales 4
                if(objTblModHis4.isDataModelChanged()){
                    if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4; j++){
                            //stock
                            bdeSumStk4=bdeSumStk4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni4=bdeSumUni4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec4=bdeSumVec4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes4=bdeSumMes4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4+3));//Meses

                        bdeSumStk4=new BigDecimal(0);
                        bdeSumUni4=new BigDecimal(0);
                        bdeSumVec4=new BigDecimal(0);
                        bdeSumMes4=new BigDecimal(0);
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

    private boolean calcularColumnaTotalesAniMes_3(){
        boolean blnRes=true;
        BigDecimal bdeSumStk1=new BigDecimal(0);
        BigDecimal bdeSumUni1=new BigDecimal(0);
        BigDecimal bdeSumVec1=new BigDecimal(0);
        BigDecimal bdeSumMes1=new BigDecimal(0);
        BigDecimal bdeSumStk2=new BigDecimal(0);
        BigDecimal bdeSumUni2=new BigDecimal(0);
        BigDecimal bdeSumVec2=new BigDecimal(0);
        BigDecimal bdeSumMes2=new BigDecimal(0);
        BigDecimal bdeSumStk3=new BigDecimal(0);
        BigDecimal bdeSumUni3=new BigDecimal(0);
        BigDecimal bdeSumVec3=new BigDecimal(0);
        BigDecimal bdeSumMes3=new BigDecimal(0);
        BigDecimal bdeSumStk4=new BigDecimal(0);
        BigDecimal bdeSumUni4=new BigDecimal(0);
        BigDecimal bdeSumVec4=new BigDecimal(0);
        BigDecimal bdeSumMes4=new BigDecimal(0);
        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //Totales 1
                for(int j=intNumColIniFecCor; j<intNumColFinFecCor; j++){
                    //stock
                    bdeSumStk1=bdeSumStk1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //unidades
                    bdeSumUni1=bdeSumUni1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //veces
                    bdeSumVec1=bdeSumVec1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //meses
                    bdeSumMes1=bdeSumMes1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                bdeSumStk1=new BigDecimal(0);
                bdeSumUni1=new BigDecimal(0);
                bdeSumVec1=new BigDecimal(0);
                bdeSumMes1=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis2.isDataModelChanged()){
                    if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2; j++){
                            //stock
                            bdeSumStk2=bdeSumStk2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni2=bdeSumUni2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec2=bdeSumVec2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes2=bdeSumMes2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                        bdeSumStk2=new BigDecimal(0);
                        bdeSumUni2=new BigDecimal(0);
                        bdeSumVec2=new BigDecimal(0);
                        bdeSumMes2=new BigDecimal(0);
                    }
                }
                
                
                //Totales 3
                if(objTblModHis3.isDataModelChanged()){
                    if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3; j++){
                            //stock
                            bdeSumStk3=bdeSumStk3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni3=bdeSumUni3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec3=bdeSumVec3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes3=bdeSumMes3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                        bdeSumStk3=new BigDecimal(0);
                        bdeSumUni3=new BigDecimal(0);
                        bdeSumVec3=new BigDecimal(0);
                        bdeSumMes3=new BigDecimal(0);
                    }
                }
                
                
                //Totales 4
                if(objTblModHis4.isDataModelChanged()){
                    if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4; j++){
                            //stock
                            bdeSumStk4=bdeSumStk4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni4=bdeSumUni4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec4=bdeSumVec4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes4=bdeSumMes4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                        bdeSumStk4=new BigDecimal(0);
                        bdeSumUni4=new BigDecimal(0);
                        bdeSumVec4=new BigDecimal(0);
                        bdeSumMes4=new BigDecimal(0);
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

    private boolean calcularColumnaTotalesAniMes_4(){
        boolean blnRes=true;
        BigDecimal bdeSumStk1=new BigDecimal(0);
        BigDecimal bdeSumUni1=new BigDecimal(0);
        BigDecimal bdeSumVec1=new BigDecimal(0);
        BigDecimal bdeSumMes1=new BigDecimal(0);
        BigDecimal bdeSumStk2=new BigDecimal(0);
        BigDecimal bdeSumUni2=new BigDecimal(0);
        BigDecimal bdeSumVec2=new BigDecimal(0);
        BigDecimal bdeSumMes2=new BigDecimal(0);
        BigDecimal bdeSumStk3=new BigDecimal(0);
        BigDecimal bdeSumUni3=new BigDecimal(0);
        BigDecimal bdeSumVec3=new BigDecimal(0);
        BigDecimal bdeSumMes3=new BigDecimal(0);
        BigDecimal bdeSumStk4=new BigDecimal(0);
        BigDecimal bdeSumUni4=new BigDecimal(0);
        BigDecimal bdeSumVec4=new BigDecimal(0);
        BigDecimal bdeSumMes4=new BigDecimal(0);
        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //Totales 1
                for(int j=intNumColIniFecCor; j<intNumColFinFecCor; j++){
                    //stock
                    bdeSumStk1=bdeSumStk1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //unidades
                    bdeSumUni1=bdeSumUni1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //veces
                    bdeSumVec1=bdeSumVec1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //meses
                    bdeSumMes1=bdeSumMes1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                bdeSumStk1=new BigDecimal(0);
                bdeSumUni1=new BigDecimal(0);
                bdeSumVec1=new BigDecimal(0);
                bdeSumMes1=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis2.isDataModelChanged()){
                    if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2; j++){
                            //stock
                            bdeSumStk2=bdeSumStk2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni2=bdeSumUni2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec2=bdeSumVec2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes2=bdeSumMes2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                        bdeSumStk2=new BigDecimal(0);
                        bdeSumUni2=new BigDecimal(0);
                        bdeSumVec2=new BigDecimal(0);
                        bdeSumMes2=new BigDecimal(0);
                    }
                }
                
                
                //Totales 3
                if(objTblModHis3.isDataModelChanged()){
                    if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3; j++){
                            //stock
                            bdeSumStk3=bdeSumStk3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni3=bdeSumUni3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec3=bdeSumVec3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes3=bdeSumMes3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                        bdeSumStk3=new BigDecimal(0);
                        bdeSumUni3=new BigDecimal(0);
                        bdeSumVec3=new BigDecimal(0);
                        bdeSumMes3=new BigDecimal(0);
                    }
                }
                
                
                //Totales 4
                if(objTblModHis4.isDataModelChanged()){
                    if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4; j++){
                            //stock
                            bdeSumStk4=bdeSumStk4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bdeSumUni4=bdeSumUni4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bdeSumVec4=bdeSumVec4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bdeSumMes4=bdeSumMes4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                        bdeSumStk4=new BigDecimal(0);
                        bdeSumUni4=new BigDecimal(0);
                        bdeSumVec4=new BigDecimal(0);
                        bdeSumMes4=new BigDecimal(0);
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

    

    private boolean calcularColumnaTotalesAniMes_1(int fila){
        boolean blnRes=true;
        int i=fila;
        BigDecimal bdeSumStk1=new BigDecimal(0);
        BigDecimal bdeSumUni1=new BigDecimal(0);
        BigDecimal bdeSumVec1=new BigDecimal(0);
        BigDecimal bdeSumMes1=new BigDecimal(0);
        BigDecimal bdeSumStk2=new BigDecimal(0);
        BigDecimal bdeSumUni2=new BigDecimal(0);
        BigDecimal bdeSumVec2=new BigDecimal(0);
        BigDecimal bdeSumMes2=new BigDecimal(0);
        BigDecimal bdeSumStk3=new BigDecimal(0);
        BigDecimal bdeSumUni3=new BigDecimal(0);
        BigDecimal bdeSumVec3=new BigDecimal(0);
        BigDecimal bdeSumMes3=new BigDecimal(0);
        BigDecimal bdeSumStk4=new BigDecimal(0);
        BigDecimal bdeSumUni4=new BigDecimal(0);
        BigDecimal bdeSumVec4=new BigDecimal(0);
        BigDecimal bdeSumMes4=new BigDecimal(0);
        
        try{
            //Totales 1
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor; j++){
                //stock
                bdeSumStk1=bdeSumStk1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                j++;
                //unidades
                bdeSumUni1=bdeSumUni1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                j++;
                //veces
                bdeSumVec1=bdeSumVec1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                j++;
                //meses
                bdeSumMes1=bdeSumMes1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
            }
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

            bdeSumStk1=new BigDecimal(0);
            bdeSumUni1=new BigDecimal(0);
            bdeSumVec1=new BigDecimal(0);
            bdeSumMes1=new BigDecimal(0);

            //Totales 2
            if(objTblModHis2.isDataModelChanged()){
                if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2; j++){
                        //stock
                        bdeSumStk2=bdeSumStk2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //unidades
                        bdeSumUni2=bdeSumUni2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //veces
                        bdeSumVec2=bdeSumVec2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //meses
                        bdeSumMes2=bdeSumMes2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    }
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                    bdeSumStk2=new BigDecimal(0);
                    bdeSumUni2=new BigDecimal(0);
                    bdeSumVec2=new BigDecimal(0);
                    bdeSumMes2=new BigDecimal(0);
                }
            }


            //Totales 3
            if(objTblModHis3.isDataModelChanged()){
                if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3; j++){
                        //stock
                        bdeSumStk3=bdeSumStk3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //unidades
                        bdeSumUni3=bdeSumUni3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //veces
                        bdeSumVec3=bdeSumVec3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //meses
                        bdeSumMes3=bdeSumMes3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    }
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                    bdeSumStk3=new BigDecimal(0);
                    bdeSumUni3=new BigDecimal(0);
                    bdeSumVec3=new BigDecimal(0);
                    bdeSumMes3=new BigDecimal(0);
                }
            }


            //Totales 4
            if(objTblModHis4.isDataModelChanged()){
                if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4; j++){
                        //stock
                        bdeSumStk4=bdeSumStk4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //unidades
                        bdeSumUni4=bdeSumUni4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //veces
                        bdeSumVec4=bdeSumVec4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //meses
                        bdeSumMes4=bdeSumMes4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    }
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumUni4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumVec4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeSumMes4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                    bdeSumStk4=new BigDecimal(0);
                    bdeSumUni4=new BigDecimal(0);
                    bdeSumVec4=new BigDecimal(0);
                    bdeSumMes4=new BigDecimal(0);
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    private boolean calcularCantidadReponerCalculada(){
        boolean blnRes=true;

        BigDecimal bdeProAnuCal=new BigDecimal(0);
        BigDecimal bdeNumMesRep=new BigDecimal(0);
        BigDecimal bdeStkAct=new BigDecimal(0);
        BigDecimal bdeStkImp=new BigDecimal(0);
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeTotPedCur=new BigDecimal(0);
        BigDecimal bdeCanRepCal=new BigDecimal(0);
        BigDecimal bdePedPreCur=new BigDecimal(0);
        BigDecimal bdeTotPedPreCur=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeProAnuCal=new BigDecimal(objTblMod.getValueAt(i, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(i, intNumColIniProAnu).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniProAnu).toString()));
                bdeNumMesRep=new BigDecimal(objTblMod.getValueAt(i, intNumColIniRep)==null?"0":(objTblMod.getValueAt(i, intNumColIniRep).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniRep).toString()));
                bdeStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()));
                bdeStkImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+1)).toString()));
                
                for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                    bdePedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeTotPedCur=bdeTotPedCur.add(bdePedCur);
                }
                
                for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                    bdePedPreCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeTotPedPreCur=bdeTotPedPreCur.add(bdePedPreCur);
                }               
                
                bdeCanRepCal=(  ((bdeProAnuCal.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeNumMesRep)).subtract(bdeStkAct).subtract(bdeStkImp).subtract(bdeTotPedCur).subtract(bdeTotPedPreCur));
                
                if(bdeCanRepCal.compareTo(new BigDecimal(0))>0)
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeCanRepCal, Integer.parseInt("0")), i, (intNumColIniRep+1));//columna de cantidad a reponer calculada


                bdeTotPedCur=new BigDecimal(0);
                bdeTotPedPreCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularCantidadReponerCalculada(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeProAnuCal=new BigDecimal(0);
        BigDecimal bdeNumMesRep=new BigDecimal(0);
        BigDecimal bdeStkAct=new BigDecimal(0);
        BigDecimal bdeStkImp=new BigDecimal(0);
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeTotPedCur=new BigDecimal(0);
        BigDecimal bdeCanRepCal=new BigDecimal(0);
        
        BigDecimal bdePedPreCur=new BigDecimal(0);
        BigDecimal bdeTotPedPreCur=new BigDecimal(0);

        try{
            bdeProAnuCal=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(fila, intNumColIniProAnu).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniProAnu).toString()));
            bdeNumMesRep=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniRep)==null?"0":(objTblMod.getValueAt(fila, intNumColIniRep).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniRep).toString()));
            bdeStkAct=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniStk)==null?"0":(objTblMod.getValueAt(fila, intNumColIniStk).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniStk).toString()));
            bdeStkImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+1)).equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString()));

            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                bdePedCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bdeTotPedCur=bdeTotPedCur.add(bdePedCur);
            }
            
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                bdePedPreCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).toString().equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bdeTotPedPreCur=bdeTotPedPreCur.add(bdePedPreCur);
            }
            
            bdeCanRepCal=(  ((bdeProAnuCal.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeNumMesRep)).subtract(bdeStkAct).subtract(bdeStkImp).subtract(bdeTotPedCur).subtract(bdeTotPedPreCur) );
            if(bdeCanRepCal.compareTo(new BigDecimal(0))>0)
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeCanRepCal, Integer.parseInt("0")), fila, (intNumColIniRep+1));//columna de cantidad a reponer calculada
            else
                objTblMod.setValueAt("0", fila, (intNumColIniRep+1));//columna de cantidad a reponer calculada

            bdeTotPedCur=new BigDecimal(0);
            bdeTotPedPreCur=new BigDecimal(0);

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




    private boolean calcularColumnaTotalPedido(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeTotPedCur=new BigDecimal(0);

        try{
            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                bdePedCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bdeTotPedCur=bdeTotPedCur.add(bdePedCur);
            }
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeTotPedCur, objParSis.getDecimalesMostrar()), fila, (intNumColFinPedEmb-1));//columna de cantidad a reponer calculada

            bdeTotPedCur=new BigDecimal(0);

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularColumnaTotalPedidoPrevio(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdePedPre=new BigDecimal(0);
        BigDecimal bdeTotPedPre=new BigDecimal(0);

        try{
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                bdePedPre=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bdeTotPedPre=bdeTotPedPre.add(bdePedPre);
            }
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeTotPedPre, objParSis.getDecimalesMostrar()), fila, (intNumColFinPedPre-1));//columna de cantidad a reponer calculada

            bdeTotPedPre=new BigDecimal(0);

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularColumnaTotalPedido(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeTotPedCur=new BigDecimal(0);

        try{
            for(int k=0; k<objTblMod.getRowCountTrue(); k++){
                for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                    bdePedCur=new BigDecimal(objTblMod.getValueAt(k, j)==null?"0":(objTblMod.getValueAt(k, j).equals("")?"0":objTblMod.getValueAt(k, j).toString()));
                    bdeTotPedCur=bdeTotPedCur.add(bdePedCur);
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeTotPedCur, objParSis.getDecimalesMostrar()), k, (intNumColFinPedEmb-1));//columna de cantidad a reponer calculada

                bdeTotPedCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularColumnaTotalPedidoPrevio(){
        boolean blnRes=true;
        BigDecimal bdePedPre=new BigDecimal(0);
        BigDecimal bdeTotPedPre=new BigDecimal(0);

        try{
            for(int k=0; k<objTblMod.getRowCountTrue(); k++){
                for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                    bdePedPre=new BigDecimal(objTblMod.getValueAt(k, j)==null?"0":(objTblMod.getValueAt(k, j).equals("")?"0":objTblMod.getValueAt(k, j).toString()));
                    bdeTotPedPre=bdeTotPedPre.add(bdePedPre);
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeTotPedPre, objParSis.getDecimalesMostrar()), k, (intNumColFinPedPre-1));//columna de cantidad a reponer calculada

                bdeTotPedPre=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    


    private boolean calcularCantidadReponerManual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeProAnuMan=new BigDecimal(0);
        BigDecimal bdeNumMesRep=new BigDecimal(0);
        BigDecimal bdeStkAct=new BigDecimal(0);
        BigDecimal bdeStkImp=new BigDecimal(0);
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeTotPedCur=new BigDecimal(0);
        BigDecimal bdeCanRepCal=new BigDecimal(0);
        
        BigDecimal bdePedPreCur=new BigDecimal(0);
        BigDecimal bdeTotPedPreCur=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeProAnuMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                bdeNumMesRep=new BigDecimal(objTblMod.getValueAt(i, intNumColIniRep)==null?"0":(objTblMod.getValueAt(i, intNumColIniRep).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniRep).toString()));
                bdeStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()));
                bdeStkImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+1)).toString()));

                for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                    bdePedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeTotPedCur=bdeTotPedCur.add(bdePedCur);
                }
                
                for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                    bdePedPreCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeTotPedPreCur=bdeTotPedPreCur.add(bdePedPreCur);
                }
                
                
                bdeCanRepCal=(  ((bdeProAnuMan.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeNumMesRep)).subtract(bdeStkAct).subtract(bdeStkImp).subtract(bdeTotPedCur).subtract(bdeTotPedPreCur) );

                if(bdeCanRepCal.compareTo(new BigDecimal(0))>0)
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeCanRepCal, Integer.parseInt("0")), i, (intNumColIniRep+2));//columna de cantidad a reponer calculada

                bdeTotPedCur=new BigDecimal(0);
                bdeTotPedPreCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    private boolean calcularPesoCantidadImportar(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeCanImp=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesCanImp=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                
//                bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
//                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
//                bdePesCanImp=bdePesCanImp.add(objUti.redondearBigDecimal((bdeCanImp.multiply(bdePesItm)), i));
//                
//                objTblMod.setValueAt("" + bdePesCanImp, i, (intNumColIniImp_Ped1+1));
//                bdePesCanImp=new BigDecimal(0);
//                bdeCanImp=new BigDecimal(0);
//                bdePesItm=new BigDecimal(0);                
//                
//                bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
//                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
//                bdePesCanImp=bdePesCanImp.add(objUti.redondearBigDecimal((bdeCanImp.multiply(bdePesItm)), i));         
//                
//                objTblMod.setValueAt("" + bdePesCanImp, i, (intNumColIniImp_Ped2+1));
//                bdePesCanImp=new BigDecimal(0);
//                bdeCanImp=new BigDecimal(0);
//                bdePesItm=new BigDecimal(0);
                
                
                if(chkCalRepPes.isSelected()){
                    //para calcular columa Can.Ped.1
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    bdePesCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniImp_Ped1+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniImp_Ped1+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniImp_Ped1+1)).toString()));
                    if(bdePesCanImp.compareTo(new BigDecimal("0"))>0)
                        bdeCanImp=bdeCanImp.add(objUti.redondearBigDecimal((bdePesCanImp.divide(bdePesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));
                    
                    objTblMod.setValueAt("" + bdeCanImp, i, (intNumColIniImp_Ped1));

                    bdeCanImp=new BigDecimal(0);            
                    bdePesCanImp=new BigDecimal(0);

                    //para calcular columa Can.Ped.2
                    bdePesCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniImp_Ped2+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniImp_Ped2+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniImp_Ped2+1)).toString()));
                    if(bdePesCanImp.compareTo(new BigDecimal("0"))>0)
                        bdeCanImp=bdeCanImp.add(objUti.redondearBigDecimal((bdePesCanImp.divide(bdePesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));

                    objTblMod.setValueAt("" + bdeCanImp, i, (intNumColIniImp_Ped2));

                    bdeCanImp=new BigDecimal(0);
                    bdePesCanImp=new BigDecimal(0);

                    bdePesItm=new BigDecimal(0);
                }
                else{
                    //para calcular columa peso(kg)1
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
                    bdePesCanImp=bdePesCanImp.add(objUti.redondearBigDecimal((bdeCanImp.multiply(bdePesItm)), new Integer("0")));

                    objTblMod.setValueAt("" + bdePesCanImp, i, (intNumColIniImp_Ped1+1));

                    bdeCanImp=new BigDecimal(0);            
                    bdePesCanImp=new BigDecimal(0);

                    //para calcular columa peso(kg)2
                    bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
                    bdePesCanImp=bdePesCanImp.add(objUti.redondearBigDecimal((bdeCanImp.multiply(bdePesItm)), new Integer("0")));
                    objTblMod.setValueAt("" + bdePesCanImp, i, (intNumColIniImp_Ped2+1));

                    bdeCanImp=new BigDecimal(0);
                    bdePesCanImp=new BigDecimal(0);

                    bdePesItm=new BigDecimal(0);
                }
                
                
                
                
                
                
            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularPesoCantidadImportar(int fila){
        boolean blnRes=true;
        BigDecimal bdeCanImp=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesCanImp=new BigDecimal(0);

        try{
            if(chkCalRepPes.isSelected()){
                //para calcular columa Can.Ped.1
                bdePesItm=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString()));
                bdePesCanImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniImp_Ped1+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniImp_Ped1+1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniImp_Ped1+1)).toString()));

                if(bdePesCanImp.compareTo(new BigDecimal("0"))>0)
                    bdeCanImp=bdeCanImp.add(objUti.redondearBigDecimal((bdePesCanImp.divide(bdePesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));
                
                objTblMod.setValueAt("" + bdeCanImp, fila, (intNumColIniImp_Ped1));
                
                bdeCanImp=new BigDecimal(0);            
                bdePesCanImp=new BigDecimal(0);
                
                //para calcular columa Can.Ped.2
                bdePesCanImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniImp_Ped2+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniImp_Ped2+1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniImp_Ped2+1)).toString()));
                if(bdePesCanImp.compareTo(new BigDecimal("0"))>0)
                    bdeCanImp=bdeCanImp.add(objUti.redondearBigDecimal((bdePesCanImp.divide(bdePesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));
                
                objTblMod.setValueAt("" + bdeCanImp, fila, (intNumColIniImp_Ped2));

                bdeCanImp=new BigDecimal(0);
                bdePesCanImp=new BigDecimal(0);

                bdePesItm=new BigDecimal(0);
            }
            else{
                //para calcular columa peso(kg)1
                bdePesItm=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString()));
                bdeCanImp=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(fila, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(fila, intNumColIniImp_Ped1).toString()));
                bdePesCanImp=bdePesCanImp.add(objUti.redondearBigDecimal((bdeCanImp.multiply(bdePesItm)), new Integer("0")));

                objTblMod.setValueAt("" + bdePesCanImp, fila, (intNumColIniImp_Ped1+1));

                bdeCanImp=new BigDecimal(0);            
                bdePesCanImp=new BigDecimal(0);

                //para calcular columa peso(kg)2
                bdeCanImp=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(fila, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(fila, intNumColIniImp_Ped2).toString()));
                bdePesCanImp=bdePesCanImp.add(objUti.redondearBigDecimal((bdeCanImp.multiply(bdePesItm)), new Integer("0")));
                objTblMod.setValueAt("" + bdePesCanImp, fila, (intNumColIniImp_Ped2+1));

                bdeCanImp=new BigDecimal(0);
                bdePesCanImp=new BigDecimal(0);

                bdePesItm=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularCantidadReponerManual(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeProAnuMan=new BigDecimal(0);
        BigDecimal bdeNumMesRep=new BigDecimal(0);
        BigDecimal bdeStkAct=new BigDecimal(0);
        BigDecimal bdeStkImp=new BigDecimal(0);
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeTotPedCur=new BigDecimal(0);
        BigDecimal bdeCanRepCal=new BigDecimal(0);
        
        BigDecimal bdePedPreCur=new BigDecimal(0);
        BigDecimal bdeTotPedPreCur=new BigDecimal(0);

        try{
            bdeProAnuMan=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinProAnu-1)).equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinProAnu-1)).toString()));
            bdeNumMesRep=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniRep)==null?"0":(objTblMod.getValueAt(fila, intNumColIniRep).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniRep).toString()));
            bdeStkAct=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniStk)==null?"0":(objTblMod.getValueAt(fila, intNumColIniStk).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniStk).toString()));
            bdeStkImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+1)).equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString()));

            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                bdePedCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bdeTotPedCur=bdeTotPedCur.add(bdePedCur);
            }
            
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                bdePedPreCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).toString().equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bdeTotPedPreCur=bdeTotPedPreCur.add(bdePedPreCur);
            }
                        
            bdeCanRepCal=(  ((bdeProAnuMan.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeNumMesRep)).subtract(bdeStkAct).subtract(bdeStkImp).subtract(bdeTotPedCur).subtract(bdeTotPedPreCur) );

            if(bdeCanRepCal.compareTo(new BigDecimal(0))>0)
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeCanRepCal, Integer.parseInt("0")), fila, (intNumColIniRep+2));//columna de cantidad a reponer calculada
            else
                objTblMod.setValueAt("0", fila, (intNumColIniRep+2));//columna de cantidad a reponer calculada

            bdeTotPedCur=new BigDecimal(0);
            bdeTotPedPreCur=new BigDecimal(0);

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularPromedioAnual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);

        try{
            bdeNumAniSel=new BigDecimal("" + intNumColAddFecCor);

            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //contiene el valor de la columna de UNIDADES en TOTALES
                //Totales 1
                bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot).toString()));
                bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu);
                bdeProAnuCal=new BigDecimal(0);
                bdeTotUni=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis2.isDataModelChanged()){
                    if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot2)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot2).toString()));
                        bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+1));
                        bdeProAnuCal=new BigDecimal(0);
                        bdeTotUni=new BigDecimal(0);
                    }
                }
                
                //Totales 3
                if(objTblModHis3.isDataModelChanged()){
                    if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot3)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot3).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot3).toString()));
                        bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                        bdeProAnuCal=new BigDecimal(0);
                        bdeTotUni=new BigDecimal(0);
                    }
                }
                
                //Totales 4
                if(objTblModHis4.isDataModelChanged()){
                    if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot4)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot4).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot4).toString()));
                        bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                        bdeProAnuCal=new BigDecimal(0);
                        bdeTotUni=new BigDecimal(0);                        
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

    private boolean calcularPromedioAnual(int fila){
        boolean blnRes=true;
        int i=fila;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);

        try{
            bdeNumAniSel=new BigDecimal("" + intNumColAddFecCor);

            //Totales 1
            //contiene el valor de la columna de UNIDADES en TOTALES
            bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot).toString()));
            bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu);
            bdeProAnuCal=new BigDecimal(0);
            bdeTotUni=new BigDecimal(0);
            
            //Totales 2
            if(objTblModHis2.isDataModelChanged()){
                if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot2)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot2).toString()));
                    bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+1));
                    bdeProAnuCal=new BigDecimal(0);
                    bdeTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 3
            if(objTblModHis3.isDataModelChanged()){
                if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot3)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot3).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot3).toString()));
                    bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                    bdeProAnuCal=new BigDecimal(0);
                    bdeTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 4
            if(objTblModHis4.isDataModelChanged()){
                if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot4)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot4).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot4).toString()));
                    bdeProAnuCal=bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                    bdeProAnuCal=new BigDecimal(0);
                    bdeTotUni=new BigDecimal(0);
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
//    estos metodos para promedio mensual y anual crearlo para cada columna agregada, es decir para los 3 historicos de ventas que se han agregado 
    
    
    private boolean calcularPromedioMensual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel1=new BigDecimal(0);
        BigDecimal bdeNumAniSel2=new BigDecimal(0);
        BigDecimal bdeNumAniSel3=new BigDecimal(0);
        BigDecimal bdeNumAniSel4=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);

        try{
            bdeNumAniSel1=new BigDecimal("" + intNumColAddFecCor);
            bdeNumAniSel2=new BigDecimal("" + intNumColAddFecCor2);
            bdeNumAniSel3=new BigDecimal("" + intNumColAddFecCor3);
            bdeNumAniSel4=new BigDecimal("" + intNumColAddFecCor4);
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //Totales 1
                //contiene el valor de la columna de UNIDADES en TOTALES
                bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot+1)).toString()));
                bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel1, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu);
                bdeProAnuCal=new BigDecimal(0);
                bdeTotUni=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis2.isDataModelChanged()){
                    if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        //contiene el valor de la columna de UNIDADES en TOTALES
                        bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot2+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot2+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot2+1)).toString()));
                        bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel2, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+1));
                        bdeProAnuCal=new BigDecimal(0);
                        bdeTotUni=new BigDecimal(0);
                    }
                }
                
                //Totales 3
                if(objTblModHis3.isDataModelChanged()){
                    if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        //contiene el valor de la columna de UNIDADES en TOTALES
                        bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot3+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot3+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot3+1)).toString()));
                        bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel3, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                        bdeProAnuCal=new BigDecimal(0);
                        bdeTotUni=new BigDecimal(0); 
                    }
                }
                
                //Totales 4
                if(objTblModHis4.isDataModelChanged()){
                    if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        //contiene el valor de la columna de UNIDADES en TOTALES
                        bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot4+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot4+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot4+1)).toString()));
                        bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel4, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                        bdeProAnuCal=new BigDecimal(0);
                        bdeTotUni=new BigDecimal(0);
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


    
    private boolean calcularPromedioMensual(int fila){
        boolean blnRes=true;
        int i=fila;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel1=new BigDecimal(0);
        BigDecimal bdeNumAniSel2=new BigDecimal(0);
        BigDecimal bdeNumAniSel3=new BigDecimal(0);
        BigDecimal bdeNumAniSel4=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);

        try{
            //Totales 1
            bdeNumAniSel1=new BigDecimal("" + intNumColAddFecCor);
            bdeNumAniSel2=new BigDecimal("" + intNumColAddFecCor2);
            bdeNumAniSel3=new BigDecimal("" + intNumColAddFecCor3);
            bdeNumAniSel4=new BigDecimal("" + intNumColAddFecCor4);
            //contiene el valor de la columna de UNIDADES en TOTALES
            bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot).toString()));
            bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel1, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu);
            bdeProAnuCal=new BigDecimal(0);
            bdeTotUni=new BigDecimal(0);
            
            //Totales 2
            if(objTblModHis2.isDataModelChanged()){
                if(objTblModHis2.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot2)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot2).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot2).toString()));
                    bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel2, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                    bdeProAnuCal=new BigDecimal(0);
                    bdeTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 3
            if(objTblModHis3.isDataModelChanged()){
                if(objTblModHis3.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot3)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot3).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot3).toString()));
                    bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel3, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                    bdeProAnuCal=new BigDecimal(0);
                    bdeTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 4
            if(objTblModHis4.isDataModelChanged()){
                if(objTblModHis4.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot4)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot4).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot4).toString()));
                    bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel4, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+4));
                    bdeProAnuCal=new BigDecimal(0);
                    bdeTotUni=new BigDecimal(0);
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void exitForm(){
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
    }



    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM2:
                    strMsg = "Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_PES_KG:
                    strMsg = "Peso";
                    break;
                case INT_TBL_DAT_COS_UNI_GRP:
                    strMsg = "Costo unitario del grupo";
                    break;
                default:
                    strMsg = "";
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
    private class ZafMouMotAdaFix extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblFix.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_FIX_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_FIX_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_FIX_COD_ALT_ITM2:
                    strMsg = "Código alterno del item 2";
                    break;
                case INT_TBL_DAT_FIX_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_FIX_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_FIX_PES_KG:
                    strMsg = "Peso";
                    break;
                case INT_TBL_DAT_FIX_COS_UNI_GRP:
                    strMsg = "Costo unitario del grupo";
                    break;
                case INT_TBL_DAT_FIX_EST_ELI_ITM:
                    strMsg = "Estado del item";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblFix.getTableHeader().setToolTipText(strMsg);
        }
    }



    private boolean getTipoConfiguracion(){
        boolean blnRes=true;
        String strTipHis="";
        Object objNumMesRep=null;
        try{
                if(  (objTblModHis.isDataModelChanged()) || (objTblModHis2.isDataModelChanged()) || (objTblModHis3.isDataModelChanged()) || (objTblModHis4.isDataModelChanged()) ){
                    if(optHisAnu.isSelected()){
                        cargarAnios(0);
                        cargarAnios2(0);
                        cargarAnios3(0);
                        cargarAnios4(0);
                    }
                    else{
                        cargarAniosMeses(0);
                        cargarAniosMeses2(0);
                        cargarAniosMeses3(0);
                        cargarAniosMeses4(0);
                    }
                }
                else{
                    System.out.println("Entro aqui!!!!");
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if(con!=null){
                        stm=con.createStatement();
                        strSQL="";
                        strSQL+="SELECT a1.tx_tipHis, a1.ne_numMesRep";
                        strSQL+=" FROM tbm_filCfgImp AS a1";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strTipHis=rst.getString("tx_tipHis");
                            objNumMesRep=rst.getObject("ne_numMesRep");
                            if(strTipHis.equals("A")){
                                optHisAnu.setSelected(true);
                                optHisMen.setSelected(false);
                                //2
                                optHisAnu2.setSelected(true);
                                optHisMen2.setSelected(false);
                                //3
                                optHisAnu3.setSelected(true);
                                optHisMen3.setSelected(false);
                                //4
                                optHisAnu4.setSelected(true);
                                optHisMen4.setSelected(false);
                            }
                            else if(strTipHis.equals("M")){
                                optHisAnu.setSelected(false);
                                optHisMen.setSelected(true);
                                //2
                                optHisAnu2.setSelected(false);
                                optHisMen2.setSelected(true);
                                //3
                                optHisAnu3.setSelected(false);
                                optHisMen3.setSelected(true);
                                //4
                                optHisAnu4.setSelected(false);
                                optHisMen4.setSelected(true);
                            }
                            jspNumMesRep.setValue(objNumMesRep);
                        }

                        con.close();
                        con=null;
                        stm.close();
                        stm=null;
                        rst.close();
                        rst=null;
                        if(optHisAnu.isSelected()){
                            cargarAnios(1);
                            cargarAnios2(1);
                            cargarAnios3(1);
                            cargarAnios4(1);
                        }
                        else
                            cargarAniosMeses(1);
                            cargarAniosMeses2(1);
                            cargarAniosMeses3(1);
                            cargarAniosMeses4(1);
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



    private boolean cargarPedidosEmbarcadosConfigurados(){
        boolean blnRes=true;
        arlDatPedEmb.clear();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.tx_numDoc2";
                strSQL+=" FROM(";
                strSQL+="       SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed AS fe_emb";
                strSQL+="       , d1.tx_numDoc2";
                strSQL+="       FROM(";
                strSQL+="               SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.tx_mesNotPed, c1.tx_numDoc2";
                strSQL+="		, c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal";
                strSQL+="               FROM(";
                strSQL+="                       SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2";
                strSQL+="                       , b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+="                       , (b1.nd_canNotPed - ";
                strSQL+=" 			CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END";
                strSQL+="                       ) AS nd_canNotPedFal";
                strSQL+="                       FROM(";
                strSQL+="                               SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.tx_numDoc2";
                strSQL+="                               , a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+="                               , a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb";
                //strSQL+="			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+="                               FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesArr=a5.co_mesemb)";//la fecha estiamada se presenta en base a ARRIBO
                strSQL+="                               INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+="                               ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="                               LEFT OUTER JOIN ";
                strSQL+="                                       (tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="                                       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+="                                       AND a3.st_reg NOT IN('I','E')";
                strSQL+="                                       )";
                strSQL+="                               ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+="                               AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+="                               WHERE a1.st_reg NOT IN('E','I') AND a1.st_cie='N'";
                strSQL+="                               GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.tx_numDoc2";
                strSQL+="                               , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can";
                strSQL+="                               ORDER BY a1.co_doc";
                strSQL+="                       ) AS b1";
                strSQL+="                       GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.nd_canNotPed";
                strSQL+="               ) AS c1";
                strSQL+="               WHERE c1.nd_canNotPedFal > 0";
                strSQL+="       ) AS d1";
                strSQL+="       WHERE d1.nd_canNotPedFal > 0";
                strSQL+="       GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed, d1.tx_numDoc2";
                strSQL+="       UNION";
                strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc,";
                strSQL+="       ''||a1.fe_arr AS fe_emb, a1.tx_numDoc2";//la fecha estiamada se presenta en base a ARRIBO
                strSQL+="       FROM tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+="       ON a1.co_emp=a2.co_emprelpedembimp AND a1.co_loc=a2.co_locrelpedembimp";
                strSQL+="       AND a1.co_tipDoc=a2.co_tipdocrelpedembimp AND a1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+="       WHERE a1.st_reg NOT IN('E','I') ";
                strSQL+="       AND a2.co_emp IS NULL";
                strSQL+="       ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
                strSQL+=" ) AS a1";
                strSQL+="  ORDER BY a1.fe_emb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.tx_numDoc2";
                System.out.println("cargarPedidosEmbarcadosConfigurados:  "+ strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,"" + rst.getString("co_emp"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,"" + rst.getString("co_loc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,"" + rst.getString("co_tipDoc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,"" + rst.getString("co_doc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,"" + rst.getString("tx_numDoc2"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL,"");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,"");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB,"" + rst.getString("fe_emb"));
                    
                    arlDatPedEmb.add(arlRegPedEmb);
                }
//                //para agregar la columna de cantidades que don Luigui va a posiblemente reponer
//                arlRegPedEmb=new ArrayList();
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,"Proyección");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,"");
//                arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB,"");                
//                arlDatPedEmb.add(arlRegPedEmb);
                
                //para agregar la columna de totales
                arlRegPedEmb=new ArrayList();
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,"Total");
                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB,"");                
                arlDatPedEmb.add(arlRegPedEmb);
                System.out.println("arlDatPedEmb: " + arlDatPedEmb.toString());

                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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


    private boolean calcularTotalCantidadReponerCalculada_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanRepCal=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanRepCal=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCal=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepCal=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniRep + 1)).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                bdePesItm_CanRepCal=bdeCanRepCal.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCal=bdeSum_PesItm_CanRepCal.add(bdePesItm_CanRepCal);
                
                bdeCanRepCal=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanRepCal=new BigDecimal(0);
                
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCal, objParSis.getDecimalesMostrar()), 0, (intNumColIniRep+1));//columna de cantidad a reponer calculada
            bdeSum_PesItm_CanRepCal=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private boolean calcularTotalCantidadReponerManual_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanRepMan=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanRepCal=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCal=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniRep+2)).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                bdePesItm_CanRepCal=bdeCanRepMan.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCal=bdeSum_PesItm_CanRepCal.add(bdePesItm_CanRepCal);
                
                bdeCanRepMan=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanRepCal=new BigDecimal(0);
                
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCal, objParSis.getDecimalesMostrar()), 0, (intNumColIniRep+2));//columna de cantidad a reponer calculada
            bdeSum_PesItm_CanRepCal=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private boolean calcularTotalPromedioAnualCalculada_peso_1(){
        boolean blnRes=true;
        BigDecimal bdeCanRepCal1=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCal1=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);//peso
        BigDecimal bdePesItm_CanRepCal=new BigDecimal(0);//peso        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepCal1=new BigDecimal(objTblMod.getValueAt(i, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(i, intNumColIniProAnu).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniProAnu).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                bdePesItm_CanRepCal=bdeCanRepCal1.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCal1=bdeSum_PesItm_CanRepCal1.add(bdePesItm_CanRepCal);
                
                bdeCanRepCal1=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCal1, objParSis.getDecimalesMostrar()), 0, intNumColIniProAnu);//columna de promedio anual calculado
            bdeSum_PesItm_CanRepCal1=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean calcularTotalPromedioAnualCalculada_peso_2(){
        boolean blnRes=true;
        BigDecimal bdeCanRepCal2=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCal2=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);//peso
        BigDecimal bdePesItm_CanRepCal=new BigDecimal(0);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepCal2=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                bdePesItm_CanRepCal=bdeCanRepCal2.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCal2=bdeSum_PesItm_CanRepCal2.add(bdePesItm_CanRepCal);
                
                bdeCanRepCal2=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCal2, objParSis.getDecimalesMostrar()), 0, (intNumColIniProAnu+1));//columna de promedio anual calculado
            bdeSum_PesItm_CanRepCal2=new BigDecimal(0);

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean calcularTotalPromedioAnualCalculada_peso_3(){
        boolean blnRes=true;
        BigDecimal bdeCanRepCal3=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCal3=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);//peso
        BigDecimal bdePesItm_CanRepCal=new BigDecimal(0);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepCal3=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                bdePesItm_CanRepCal=bdeCanRepCal3.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCal3=bdeSum_PesItm_CanRepCal3.add(bdePesItm_CanRepCal);
                
                bdeCanRepCal3=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCal3, objParSis.getDecimalesMostrar()), 0, (intNumColIniProAnu+2));//columna de promedio anual calculado
            bdeSum_PesItm_CanRepCal3=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean calcularTotalPromedioAnualCalculada_peso_4(){
        boolean blnRes=true;
        BigDecimal bdeCanRepCal4=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCal4=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);//peso
        BigDecimal bdePesItm_CanRepCal=new BigDecimal(0);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepCal4=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+3))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                bdePesItm_CanRepCal=bdeCanRepCal4.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCal4=bdeSum_PesItm_CanRepCal4.add(bdePesItm_CanRepCal);
                
                bdeCanRepCal4=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCal4, objParSis.getDecimalesMostrar()), 0, (intNumColIniProAnu+3));//columna de promedio anual calculado
            bdeSum_PesItm_CanRepCal4=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    private boolean calcularTotalPromedioAnualManual_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanRepCal=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanRepCal=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCal=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepCal=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                bdePesItm_CanRepCal=bdeCanRepCal.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCal=bdeSum_PesItm_CanRepCal.add(bdePesItm_CanRepCal);
                
                bdeCanRepCal=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanRepCal=new BigDecimal(0);
                
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCal, objParSis.getDecimalesMostrar()), 0, (intNumColFinProAnu-1));//columna de promedio anual calculado
            bdeSum_PesItm_CanRepCal=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean calcularTotalStockActual_Importacion_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanRepCalStkAct=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanRepCalStkAct=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCalStkAct=new BigDecimal(0);

        BigDecimal bdeCanRepCalImp=new BigDecimal(0);
        BigDecimal bdePesItm_CanRepCalImp=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanRepCalImp=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCanRepCalStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()));
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                bdePesItm_CanRepCalStkAct=bdeCanRepCalStkAct.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCalStkAct=bdeSum_PesItm_CanRepCalStkAct.add(bdePesItm_CanRepCalStkAct);
                                
                bdeCanRepCalImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk+1).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniStk + 1).toString()));
                bdePesItm_CanRepCalImp=bdeCanRepCalImp.multiply(bdePesItm);
                bdeSum_PesItm_CanRepCalImp=bdeSum_PesItm_CanRepCalImp.add(bdePesItm_CanRepCalImp);

                bdeCanRepCalStkAct=new BigDecimal(0);
                bdePesItm_CanRepCalStkAct=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdeCanRepCalImp=new BigDecimal(0);
                bdePesItm_CanRepCalImp=new BigDecimal(0);

            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCalStkAct, objParSis.getDecimalesMostrar()), 0, intNumColIniStk);//columna de promedio anual calculado
            bdeSum_PesItm_CanRepCalStkAct=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanRepCalImp, objParSis.getDecimalesMostrar()), 0, (intNumColIniStk+1));//columna de promedio anual calculado
            bdeSum_PesItm_CanRepCalImp=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularTotalPedido_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanPed=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanPed=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanPed=new BigDecimal(0);

        try{           
            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb); j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCanPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    bdePesItm_CanPed=bdeCanPed.multiply(bdePesItm);
                    bdeSum_PesItm_CanPed=bdeSum_PesItm_CanPed.add(bdePesItm_CanPed);
                    

                    bdeCanPed=new BigDecimal(0);
                    bdePesItm=new BigDecimal(0);
                    bdePesItm_CanPed=new BigDecimal(0);
                    
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanPed, objParSis.getDecimalesMostrar()), 0, j);//columna de promedio anual calculado
                bdeSum_PesItm_CanPed=new BigDecimal(0);
            }
                
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean calcularTotalPedidoPrevio_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanPed=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanPed=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanPed=new BigDecimal(0);

        try{           
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre); j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCanPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    bdePesItm_CanPed=bdeCanPed.multiply(bdePesItm);
                    bdeSum_PesItm_CanPed=bdeSum_PesItm_CanPed.add(bdePesItm_CanPed);
                    

                    bdeCanPed=new BigDecimal(0);
                    bdePesItm=new BigDecimal(0);
                    bdePesItm_CanPed=new BigDecimal(0);
                    
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanPed, objParSis.getDecimalesMostrar()), 0, j);//columna de promedio anual calculado
                bdeSum_PesItm_CanPed=new BigDecimal(0);
            }
                
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean calcularTotalCantidadImportar_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanPed=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanPed=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanPed1=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanPed2=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                //Pedido 1
                bdeCanPed=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
                bdePesItm_CanPed=bdeCanPed.multiply(bdePesItm);
                bdeSum_PesItm_CanPed1=bdeSum_PesItm_CanPed1.add(bdePesItm_CanPed);
                bdeCanPed=new BigDecimal(0);
                bdePesItm_CanPed=new BigDecimal(0);
                //Pedido 2
                bdeCanPed=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
                bdePesItm_CanPed=bdeCanPed.multiply(bdePesItm);
                bdeSum_PesItm_CanPed2=bdeSum_PesItm_CanPed2.add(bdePesItm_CanPed);
                //se setea los campos de calculo
                bdeCanPed=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanPed=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanPed1, objParSis.getDecimalesMostrar()), 0, (intNumColIniImp_Ped1+1));//columna de promedio anual calculado del Pedido 1
            bdeSum_PesItm_CanPed1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanPed2, objParSis.getDecimalesMostrar()), 0, (intNumColIniImp_Ped2+1));//columna de promedio anual calculado del Pedido 2
            bdeSum_PesItm_CanPed2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    
    
    private boolean calcularTotalesCantidadReposicion_costo(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(0);
        BigDecimal bdeCanImp=new BigDecimal(0);
        BigDecimal bdeSum_CanCos1=new BigDecimal(0);
        BigDecimal bdeSum_CanCos2=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                //Calculada
                bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniRep+1)).toString()));
                bdeSum_CanCos1=bdeSum_CanCos1.add(bdeCanImp.multiply(bdeCosUniGrp));
                bdeCanImp=new BigDecimal(0);
                //Manual
                bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniRep+2)).toString()));
                bdeSum_CanCos2=bdeSum_CanCos2.add(bdeCanImp.multiply(bdeCosUniGrp));
                //se setea los campos de calculo
                bdeCanImp=new BigDecimal(0);
                bdeCosUniGrp=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_CanCos1, objParSis.getDecimalesMostrar()), 1, (intNumColIniRep+1));//columna de promedio anual calculado del Pedido 1
            bdeSum_CanCos1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_CanCos2, objParSis.getDecimalesMostrar()), 1, (intNumColIniRep+2));//columna de promedio anual calculado del Pedido 2
            bdeSum_CanCos2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private boolean calcularTotalesCantidadImportar_costo(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(0);
        BigDecimal bdeCanImp=new BigDecimal(0);
        BigDecimal bdeSum_CanCos1=new BigDecimal(0);
        BigDecimal bdeSum_CanCos2=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                //Pedido 1
                bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
                bdeSum_CanCos1=bdeSum_CanCos1.add(bdeCanImp.multiply(bdeCosUniGrp));
                bdeCanImp=new BigDecimal(0);
                //Pedido 2
                bdeCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
                bdeSum_CanCos2=bdeSum_CanCos2.add(bdeCanImp.multiply(bdeCosUniGrp));
                //se setea los campos de calculo
                bdeCanImp=new BigDecimal(0);
                bdeCosUniGrp=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_CanCos1, objParSis.getDecimalesMostrar()), 1, (intNumColIniImp_Ped1+1));//columna de promedio anual calculado del Pedido 1
            bdeSum_CanCos1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_CanCos2, objParSis.getDecimalesMostrar()), 1, (intNumColIniImp_Ped2+1));//columna de promedio anual calculado del Pedido 2
            bdeSum_CanCos2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    boolean escondeColumnasPedidoSinValor(){
        boolean blnRes=true;
        BigDecimal bdeValPed=new BigDecimal(BigInteger.ZERO);
        int intNumCol=-1;
        String strEstArl="";
        int intNumColEli=0;
        try{
            objTblMod.removeAllSystemHiddenColumns(tblDat);
            for(int j=(intNumColFinPedEmb-2); j>=intNumColIniPedEmb; j--){//es menos 1 porque la ultima columna es en realidad de TOTAL
                for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                    bdeValPed=bdeValPed.add(new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString())));
                }               
                if( (bdeValPed.compareTo(new BigDecimal("0"))>0) ||  (bdeValPed.compareTo(new BigDecimal("0"))<0) ){
                }
                else{//se elimina la columna y se coloca como estado 'E' en el arraylist
                    for(int k=0;k<arlDatPedEmb.size(); k++){

                        intNumCol=objUti.getIntValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_NUM_COL);
                        strEstArl=objUti.getStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL);
                        
                        if(! strEstArl.equals("E")){
                            if(j==intNumCol){
                                objUti.setStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL, "E");
                                objTblMod.addSystemHiddenColumn(intNumCol, tblDat);
                                intNumColEli++;
                                break;
                            }
                        }
                    }
                }
                bdeValPed=new BigDecimal(BigInteger.ZERO);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    boolean escondeColumnasPedidoSinValorTotales(){
        boolean blnRes=true;
        int intNumCol=-1;
        String strEstArl="";
        try{
            objTblModTot.removeAllSystemHiddenColumns(tblTot);
            for(int k=0;k<arlDatPedEmb.size(); k++){
                intNumCol=objUti.getIntValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_NUM_COL);
                strEstArl=objUti.getStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL);
                if(strEstArl.equals("E")){
                    for(int j=(intNumColFinPedEmb-2); j>=intNumColIniPedEmb; j--){//es menos 1 porque la ultima columna es en realidad de TOTAL
                        if(j==intNumCol){
                            objTblModTot.addSystemHiddenColumn(intNumCol, tblTot);
                            break;
                        }
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
      
    boolean escondeColumnasPedidoPrevioSinValor(){
        boolean blnRes=true;
        BigDecimal bdeValPed=new BigDecimal(BigInteger.ZERO);
        int intNumCol=-1;
        String strEstArl="";
        int intNumColEli=0;
        try{
            for(int j=(intNumColFinPedPre-3); j>=intNumColIniPedPre; j--){//es menos 1 porque la ultima columna es en realidad de TOTAL y otra mas para q se vea la columna libre
                for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                    bdeValPed=bdeValPed.add(new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString())));
                }
               
                
                if( (bdeValPed.compareTo(new BigDecimal("0"))>0) ||  (bdeValPed.compareTo(new BigDecimal("0"))<0) ){
                }
                else{//se elimina la columna y se coloca como estado 'E' en el arraylist
                    for(int k=0;k<arlDatPedPre.size(); k++){

                        intNumCol=objUti.getIntValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_COL);
                        strEstArl=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD);//se marca con 'E' si es q se oculta en el modelo
                        
                        if(! strEstArl.equals("E")){
                            if(j==intNumCol){
                                objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD, "E");
                                objTblMod.addSystemHiddenColumn(intNumCol, tblDat);
                                intNumColEli++;
                                break;
                            }
                        }
                    }
                }
                bdeValPed=new BigDecimal(BigInteger.ZERO);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    boolean escondeColumnasPedidoPrevioSinValorTotales(){
        boolean blnRes=true;
        int intNumCol=-1;
        String strEstArl="";
        try{
            //objTblModTot.removeAllSystemHiddenColumns(tblTot);
            for(int k=0;k<arlDatPedPre.size(); k++){
                intNumCol=objUti.getIntValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_COL);
                strEstArl=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD);
                if(strEstArl.equals("E")){
                    for(int j=(intNumColFinPedPre-2); j>=intNumColIniPedPre; j--){//es menos 1 porque la ultima columna es en realidad de TOTAL
                        if(j==intNumCol){
                            objTblModTot.addSystemHiddenColumn(intNumCol, tblTot);
                            break;
                        }
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
     
    
    
    
    
    
    
    private boolean consultaDetalleNotayPedidosEmbarcados(){
        boolean blnRes=true;
        Statement stmCanPedEmb;
        ResultSet rstCanPedEmb;
        try{
            if(con!=null){
                stmCanPedEmb=con.createStatement();
                //crear un arreglo y colocar ahi la informacion para luego ser leida antes de terminar de hacer la consulta.
                strSQL="";
                strSQL+="SELECT g1.co_emp, g1.co_loc, g1.co_tipDoc, g1.co_doc, g1.co_itm, g1.nd_can, a1.tx_codAlt";
                strSQL+="  FROM tbm_inv AS a1 ";
                strSQL+="  INNER JOIN(";
                strSQL+="  SELECT f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f2.co_itm, SUM(f2.nd_can) AS nd_can";
                strSQL+="     FROM (tbm_cabPedEmbImp AS f1 ";
                strSQL+="  		LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+="  		ON f1.co_emp=a2.co_emprelpedembimp AND f1.co_loc=a2.co_locrelpedembimp ";
                strSQL+="  		AND f1.co_tipDoc=a2.co_tipdocrelpedembimp AND f1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+="  	)";
                strSQL+="         INNER JOIN tbm_detPedEmbImp AS f2";
                strSQL+="         ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc ";
                strSQL+="         WHERE f1.st_reg NOT IN('E','I') AND a2.co_emp IS NULL";
                strSQL+="         GROUP BY f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f2.co_itm";
                strSQL+="       ) AS g1";
                strSQL+="       ON a1.co_emp=g1.co_emp AND a1.co_itm=g1.co_itm";
                strSQL+=" UNION ALL";
                strSQL+=" SELECT g2.co_emp, g2.co_loc, g2.co_tipDoc, g2.co_doc, g2.co_itm, g2.nd_canNotPedFal, a1.tx_codAlt";
                strSQL+=" FROM tbm_inv AS a1 ";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_itm";
                strSQL+=" 		/*, c1.nd_canNotPed, c1.nd_canPedEmb*/, c1.nd_canNotPedFal";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_itm";
                strSQL+="		, b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+="		, (b1.nd_canNotPed - ";
                strSQL+="			CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END";
                strSQL+="		  ) AS nd_canNotPedFal";
                strSQL+="		FROM(	";
                strSQL+="			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.tx_numDoc2";
                strSQL+="			, a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+="			, CAST(a4.nd_can AS NUMERIC(18,6) ) AS nd_canNotPed, CAST(a2.nd_can AS NUMERIC(18,6) ) AS nd_canPedEmb";
                strSQL+=" 			, a4.co_itm";
                strSQL+=" 			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+="			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+="			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="			LEFT OUTER JOIN ";
                strSQL+="				(tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="				 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+="				 AND a3.st_reg NOT IN('I','E')";
                strSQL+="				)";
                strSQL+=" 			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+=" 			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+=" 			WHERE a1.st_reg NOT IN('E','I')	AND a1.st_cie='N'	";
                strSQL+="			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.tx_numDoc2";
                strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can, a4.co_itm";
                strSQL+=" 			ORDER BY a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.nd_canNotPed, b1.co_itm";
                strSQL+=" 	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+=") AS g2";
                strSQL+=" ON a1.co_emp=g2.co_emp AND a1.co_itm=g2.co_itm";
                strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
                
                //System.out.println("consultaDetalleNotayPedidosEmbarcados:  "+ strSQL);
                rstCanPedEmb=stmCanPedEmb.executeQuery(strSQL);
                arlDatDetPedEmb.clear();
                while(rstCanPedEmb.next()){
                    arlRegDetPedEmb=new ArrayList();
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_EMP, rstCanPedEmb.getString("co_emp"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_LOC, rstCanPedEmb.getString("co_loc"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_TIP_DOC, rstCanPedEmb.getString("co_tipDoc"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_DOC, rstCanPedEmb.getString("co_doc"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_ITM, rstCanPedEmb.getString("co_itm"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_CAN_PED_EMB, rstCanPedEmb.getString("nd_can"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_ALT_ITM, rstCanPedEmb.getString("tx_codAlt"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_EST, "");
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_NUM_COL, "0");                    
                    arlDatDetPedEmb.add(arlRegDetPedEmb);                    
                }
                stmCanPedEmb.close();
                stmCanPedEmb=null;
                rstCanPedEmb.close();
                rstCanPedEmb=null;
                
                
//                System.out.println("arlDatPedEmb:    " + arlDatPedEmb.toString());
//                System.out.println("arlDatDetPedEmb: " + arlDatDetPedEmb.toString());
                
                //se cargan los datos de valores del pedido embarcado
                int intCodEmpNomPedEmb=-1;
                int intCodLocNomPedEmb=-1;
                int intCodTipDocNomPedEmb=-1;
                int intCodDocNomPedEmb=-1;
                int intNumColNomPedEmb=-1;                
                int intCodEmpCanPedEmb=-1;
                int intCodLocCanPedEmb=-1;
                int intCodTipDocCanPedEmb=-1;
                int intCodDocCanPedEmb=-1;                
                String strEstPedEmb="";
                
                for(int d=0; d<(arlDatPedEmb.size()); d++){
                    intCodEmpNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_EMP);
                    intCodLocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_LOC);
                    intCodTipDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_TIP_DOC);
                    intCodDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_DOC);
                    intNumColNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_NUM_COL);
                    
                    for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                        intCodEmpCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_EMP);
                        intCodLocCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_LOC);
                        intCodTipDocCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_TIP_DOC);
                        intCodDocCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_DOC);
                        
                        strEstPedEmb=objUti.getStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST);
                        if(strEstPedEmb.equals("")){
                            if( (intCodEmpNomPedEmb==intCodEmpCanPedEmb) && (intCodLocNomPedEmb==intCodLocCanPedEmb) && (intCodTipDocNomPedEmb==intCodTipDocCanPedEmb) && (intCodDocNomPedEmb==intCodDocCanPedEmb) ){
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "S");
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_NUM_COL, ""+intNumColNomPedEmb);
                            }
                        }
                    }                    
                }                
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "");
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
 
   private boolean guardarDatos(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if(elimina_tbm_filCfgImp()){
                    if(inserta_tbm_filCfgImp()){
                        if(elimina_tbm_filAniSelCfgImp()){
                            if(inserta_tbm_filAniSelCfgImp()){
                                if(elimina_tbm_filMesSelCfgImp()){
                                    if(inserta_tbm_filMesSelCfgImp()){
                                        if(update_tbmInv()){
                                            if(update_tbmCabNotPedPreImp()){
                                                if(update_tbmDetNotPedPreImp()){
//                                                    if(getCodSegNotExiNotPedPre()) {
//                                                        if(objSegMovInv.setSegMovInvCompra(con, objCodSegNotPedPre, 0, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), INT_EST_COD_TIP_DOC_PED_PRE, intCodDocPedPre_canRepLSC)){
                                                            con.commit();
//                                                        }
//                                                        else  { 
//                                                            con.rollback();  
//                                                            System.out.println("FALLO setSegMovInvCompra");
//                                                            blnRes=false;              
//                                                        }
//                                                    }
//                                                    else{
//                                                        con.rollback();
//                                                        System.out.println("FALLO getCodSegNotExiNotPedPre");
//                                                        blnRes=false;
//                                                    }
                                                }else{
                                                    con.rollback();
                                                    System.out.println("FALLO update_tbmDetNotPedPreImp");
                                                    blnRes=false;
                                                }
                                            }
                                            else{
                                                con.rollback();
                                                System.out.println("FALLO update_tbmCabNotPedPreImp");
                                                blnRes=false;
                                            }                                            
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
    
   private boolean elimina_tbm_filCfgImp(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="DELETE FROM tbm_filCfgImp";
                stm.executeUpdate(strSQL);
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

   private boolean inserta_tbm_filCfgImp(){
        boolean blnRes=true;
        String strTipHis="";
        try{
            if(con!=null){
                stm=con.createStatement();
                if(optHisAnu.isSelected())
                    strTipHis="'A'";
                else
                    strTipHis="'M'";
                strSQL="";
                strSQL+="INSERT INTO tbm_filCfgImp(tx_tipHis, ne_numMesRep)";
                strSQL+="     VALUES(" + strTipHis + "";
                strSQL+="      , " + jspNumMesRep.getValue() + "";
                strSQL+="     );";
                stm.executeUpdate(strSQL);
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
   
   private boolean elimina_tbm_filAniSelCfgImp(){
        boolean blnRes=true;
        try{
            if(con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="DELETE FROM tbm_filAniSelCfgImp";
                    stm.executeUpdate(strSQL);
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
   
   private boolean inserta_tbm_filAniSelCfgImp(){
        boolean blnRes=true;
        int intAniSel=-1;
        int intAniSelAnt=-1;
        String strUpdSQL="";
        try{
            if(con!=null){
                if(optHisAnu.isSelected()){
                   stm=con.createStatement();
                   for(int i=0; i<arlDatAniAdd.size(); i++){
                       intAniSel=objUti.getIntValueAt(arlDatAniAdd, i, INT_ARL_ANI);
                       if(intAniSelAnt!=intAniSel){
                            strSQL="";
                            strSQL+="INSERT INTO tbm_filAniSelCfgImp(ne_aniSel)";
                            strSQL+="     VALUES( " + intAniSel + ");";
                            strUpdSQL+=strSQL;
                            intAniSelAnt=intAniSel;
                       }
                   }
                    stm.executeUpdate(strUpdSQL);
                    stm.close();
                    stm=null;
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

   private boolean elimina_tbm_filMesSelCfgImp(){
        boolean blnRes=true;
        try{
            if(con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="DELETE FROM tbm_filMesSelCfgImp";
                    stm.executeUpdate(strSQL);
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
   
   private boolean inserta_tbm_filMesSelCfgImp(){
        boolean blnRes=true;
        int intAniSel=-1;
        int intMesSel=-1;
        String strUpdSQL="";
        try{
            if(con!=null){
                if(optHisMen.isSelected()){
                   stm=con.createStatement();
                   for(int i=0; i<arlDatAniAdd.size(); i++){
                       intAniSel=objUti.getIntValueAt(arlDatAniAdd, i, INT_ARL_ANI);
                       intMesSel=objUti.getIntValueAt(arlDatAniAdd, i, INT_ARL_NUM_MES);
                        strSQL="";
                        strSQL+="INSERT INTO tbm_filMesSelCfgImp(ne_aniSel, ne_mesSel)";
                        strSQL+="     VALUES( " + intAniSel + ", " + intMesSel + ");";
                        strUpdSQL+=strSQL;
                   }
                   stm.executeUpdate(strUpdSQL);
                   stm.close();
                   stm=null;
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
   
   private boolean update_tbmInv(){
        boolean blnRes=true;
        String strCodItm="";
        String strUpdSQL="";
        BigDecimal bdeProAnuVenMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeCanImpPie=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeCanImpPes=new BigDecimal(BigInteger.ZERO);
        int intTerTmp=0;
        String strLin="";
        try{
            if(con!=null){
               stm=con.createStatement();
               for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                   strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                   if(strLin.equals("M")){
                        strCodItm=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString();
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
                        strSQL+=" SET";
                        strSQL+=" nd_pesitmkgr=" + new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0": objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString())) + "";
                        bdeProAnuVenMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                        if(bdeProAnuVenMan.compareTo(new BigDecimal("0"))==0)
                            strSQL+=", nd_proAnuVenMan=Null";
                        else
                            strSQL+="  ,nd_proAnuVenMan=" + bdeProAnuVenMan + "";

                        //aqui va para las columnas de importacion que se agregaron
                        intTerTmp=1;
                        for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
            
                                bdeCanImpPie=new BigDecimal(objTblMod.getValueAt(i, k)==null?"0":(objTblMod.getValueAt(i, k).toString().equals("")?"0":objTblMod.getValueAt(i, k).toString()));
                                if(bdeCanImpPie.compareTo(new BigDecimal("0"))==0)
                                    strSQL+=", nd_canPed" + intTerTmp + "=Null";
                                else
                                    strSQL+=", nd_canPed" + intTerTmp + "=" + bdeCanImpPie + "";//intNumColIniPed
                                
                                bdeCanImpPes=new BigDecimal(objTblMod.getValueAt(i, (k+1))==null?"0":(objTblMod.getValueAt(i, (k+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (k+1)).toString()));
                                if(bdeCanImpPes.compareTo(new BigDecimal("0"))==0)
                                    strSQL+=", nd_canPedPes" + intTerTmp + "=Null";
                                else
                                    strSQL+=", nd_canPedPes" + intTerTmp + "=" + bdeCanImpPes + "";//intNumColIniPed
                                
                                       
                            intTerTmp++;
                        }
                        for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                  
                                bdeCanImpPie=new BigDecimal(objTblMod.getValueAt(i, k)==null?"0":(objTblMod.getValueAt(i, k).toString().equals("")?"0":objTblMod.getValueAt(i, k).toString()));
                                if(bdeCanImpPie.compareTo(new BigDecimal("0"))==0)
                                    strSQL+=", nd_canPed" + intTerTmp + "=Null";
                                else
                                    strSQL+=", nd_canPed" + intTerTmp + "=" + bdeCanImpPie + "";//intNumColIniPed
                                
                                bdeCanImpPes=new BigDecimal(objTblMod.getValueAt(i, (k+1))==null?"0":(objTblMod.getValueAt(i, (k+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (k+1)).toString()));
                                if(bdeCanImpPes.compareTo(new BigDecimal("0"))==0)
                                    strSQL+=", nd_canPedPes" + intTerTmp + "=Null";
                                else
                                    strSQL+=", nd_canPedPes" + intTerTmp + "=" + bdeCanImpPes + "";//intNumColIniPed
                        
                            intTerTmp++;
                        }

                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_itm=" + strCodItm + ";";
                        //actualiza el peso en todas las empresas
                        strSQL+=" UPDATE tbm_inv";
                        strSQL+=" SET";
                        strSQL+=" nd_pesitmkgr=" + new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString())) + "";
                        strSQL+=" FROM (";
                        strSQL+=" 	SELECT a1.co_emp, a2.co_itm";
                        strSQL+=" 	FROM tbm_inv AS a1";
                        strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                        strSQL+=" 	WHERE a2.co_itmMae=(";
                        strSQL+=" 		SELECT a2.co_itmMae FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                        strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="                 AND a1.co_itm=" + strCodItm + "";
                        strSQL+=" 	)";
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm";
                        strSQL+=" ;";
                        strUpdSQL+=strSQL;
                        
                        if(objTblMod.isChecked(i, intNumColIniChkItmEli)){
                            //agrego los items eliminados al arraylist, pero fueron eliminados por checkbox
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + strCodItm);
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
                            arlDatFilEli.add(arlRegFilEli);
                        }
                        else{
                            //agrego los items eliminados al arraylist, pero fueron eliminados por checkbox
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + strCodItm);
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "N");
                            arlDatFilEli.add(arlRegFilEli);
                        }
                        
                   }

               }
               
               
               int intCodEmpFilEstEli=-1;
               int intCodItmFilEstEli=-1;
               String strEstEliItmChk="";

               for(int i=0; i<arlDatFilEli.size(); i++){
                   intCodEmpFilEstEli=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_EMP);
                   intCodItmFilEstEli=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_ITM);
                   strEstEliItmChk=objUti.getStringValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_EST_ITM);
                   
                   strSQL="";
                   strSQL+=" UPDATE tbm_inv";
                   strSQL+=" SET";
                   if(strEstEliItmChk.equals("S"))
                        strSQL+=" st_itmEli='S'";
                   else
                       strSQL+=" st_itmEli=Null";
                   strSQL+=" WHERE co_emp=" + intCodEmpFilEstEli + "";
                   strSQL+=" AND co_itm=" + intCodItmFilEstEli + "";
                   strSQL+=" ;";
                   strUpdSQL+=strSQL;
               }

               System.out.println("update_tbmInv: " + strUpdSQL);
               stm.executeUpdate(strUpdSQL);
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
   
   
   
   private boolean update_tbmCabNotPedPreImp(){
        boolean blnRes=true;
        String strUpdSQL="";
        String strLin="";
        int intColArl=-1;
        String strNomPedIngNow="";
        try{
            if(con!=null){
               stm=con.createStatement();
               //Obtener la fecha del servidor.
               datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
               if (datFecAux==null)
                    return false;
               
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) AS co_doc";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                System.out.println("MAX codigoDocumento: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodDocPedPre_canRepLSC=rst.getInt("co_doc");
                if (intCodDocPedPre_canRepLSC==-1)
                    return false;
                intCodDocPedPre_canRepLSC++;
                
                strSQL="";
                strSQL+="SELECT MAX(a1.ne_numDoc) AS ne_numDoc";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                System.out.println("MAX numeroDocumento: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumDocPedPre_canRepLSC=rst.getInt("ne_numDoc");
                if (intNumDocPedPre_canRepLSC==-1)
                    return false;
                intNumDocPedPre_canRepLSC++;
                
                System.out.println("antes - arlDatPedPre: " + arlDatPedPre.toString());
                //validar si existe el nombre Pedido Previo en la DB
                for(int k=0; k<arlDatPedPre.size();k++){
                    strLin=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD);
                    if( strLin.equals("I")){
                        strNomPedIngNow=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_PED_PRE);
                        if(existeNombrePedidoPrevioDB(strNomPedIngNow)){
                            //coloco los campos del primary key de la tabla en el arreglo que tenia estado 'I', los saco de la DB si me retorna algo el query q valida el nombrePedPrevio
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_EMP, "" + Integer.parseInt(""+ intCodEmpExiNomPedPre));
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_LOC, "" + Integer.parseInt(""+ intCodLocExiNomPedPre));
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_TIP_DOC, "" + Integer.parseInt(""+ intCodTipDocExiNomPedPre));
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_DOC, "" + Integer.parseInt(""+ intCodDocExiNomPedPre));
                            //cambio el estado de 'I' a 'M'
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD, "M");
                        }
                    }
                }
                System.out.println("despues - arlDatPedPre: " + arlDatPedPre.toString());
                
                strLin="";
                strNomPedIngNow="";
                
               //obtengo la columna del pedido previo y se arma el SQL de acuerdo al pedido previo para guardar por pedido previo y no item por item
               for(int k=0; k<arlDatPedPre.size();k++){
                   strLin=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD);
                   intColArl=objUti.getIntValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_COL);
                   if( strLin.equals("I")){
                       strNomPedIngNow=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_PED_PRE);
                       if(intColArl==(intNumColFinPedPre-2)){
                            strSQL="";
                            strSQL+="INSERT INTO tbm_cabNotPedPreImp(";
                            strSQL+=" co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, tx_numdoc2, ";
                            strSQL+=" st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_cie, co_locRel, co_tipDocRel, co_docRel)";
                            strSQL+=" VALUES(";
                            strSQL+="       " + objParSis.getCodigoEmpresa() + "";//co_emp
                            strSQL+="       ," + objParSis.getCodigoLocal() + "";//co_loc
                            strSQL+="       ," + INT_EST_COD_TIP_DOC_PED_PRE + "";//co_tipdoc
                            strSQL+="       ," + intCodDocPedPre_canRepLSC + "";//co_doc
                            strSQL+="       ,'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                            strSQL+="       ," + intNumDocPedPre_canRepLSC + "";//ne_numdoc
                            strSQL+="       ," + objUti.codificar(strNomPedIngNow) + "";//tx_numdoc2
                            strSQL+="       ,'A'";//st_reg
                            strSQL+="       ,'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";//fe_ing
                            strSQL+="       ,'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";//fe_ultmod
                            strSQL+="       ," + objParSis.getCodigoUsuario() + "";//co_usring
                            strSQL+="       ," + objParSis.getCodigoUsuario() + "";//co_usrmod
                            strSQL+="       ,'N'";//st_cie
                            strSQL+="       ,Null";//co_locRel
                            strSQL+="       ,Null";//co_tipDocRel
                            strSQL+="       ,Null";//co_docRel
                            strSQL+=" );";
                            strUpdSQL+=strSQL;
                       }
                    }
                    else if(strLin.equals("M")) {
                        strSQL="";
                        strSQL+="UPDATE tbm_cabNotPedPreImp";
                        strSQL+=" SET co_usrMod=" + objParSis.getCodigoUsuario() + "";
                        strSQL+=" , fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_EMP) + "";
                        strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_LOC) + "";
                        strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_TIP_DOC) + "";
                        strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_DOC) + "";
                        strSQL+=" ;";
                        strUpdSQL+=strSQL;
                    }                   
               }
               
               //para anular las Notas de Pedido Previas que se eliminaron cuando el usuario selecciono la columna
               int intCodEmpEstNotPedPreAnu, intCodLocEstNotPedPreAnu, intCodTipDocEstNotPedPreAnu, intCodDocEstNotPedPreAnu;
               String strEstNotPedPreAnu;
               
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    strEstNotPedPreAnu=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST_ANU);
                    
                    intCodEmpEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_EMP);
                    intCodLocEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_LOC);
                    intCodTipDocEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                    intCodDocEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_DOC);
                    if(strEstNotPedPreAnu.equals("E")){
                        strSQL="";
                        strSQL+="UPDATE tbm_cabNotPedPreImp";
                        strSQL+=" SET st_reg='I'";
                        strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario() + "";
                        strSQL+=" , fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                        strSQL+=" WHERE co_emp=" + intCodEmpEstNotPedPreAnu + "";
                        strSQL+=" AND co_loc=" + intCodLocEstNotPedPreAnu + "";
                        strSQL+=" AND co_tipDoc=" + intCodTipDocEstNotPedPreAnu + "";
                        strSQL+=" AND co_doc=" + intCodDocEstNotPedPreAnu + "";
                        strSQL+=" ;";
                        strUpdSQL+=strSQL;
                    }
                }
               
               System.out.println("update_tbmCabNotPedPreImp: " + strUpdSQL);
               stm.executeUpdate(strUpdSQL);
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
   
   
   
   private boolean update_tbmDetNotPedPreImp(){
        boolean blnRes=true;
        String strUpdSQL="";
        String strColArlCab="";
        int intCodEmpCab=-1, intCodLocCab=-1, intCodTipDocCab=-1, intCodDocCab=-1;
        int intCodEmpDet=-1, intCodLocDet=-1, intCodTipDocDet=-1, intCodDocDet=-1, intCodItmDet=-1;
        int intColModCab=-1;
        int intUltRegPedPre=0;
        BigDecimal bdeCanPedPrePza=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeCanPedPrePes=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeCanPesItmTmp=new BigDecimal(BigInteger.ZERO);
        //String strEstPedPre="";
        String strLinMod="";
        try{
            if(con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
               
                System.out.println("arlDatPedPre: " + arlDatPedPre.toString());
                
               //obtengo la columna del pedido previo que se ha modificado, ya con esto obtengo que columnas debo modificar y no senso en vano las otras columnas y filas
               for(int c=0; c<arlDatPedPre.size();c++){
                   strColArlCab=objUti.getStringValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COL_MOD);

                   intCodEmpCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_EMP);
                   intCodLocCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_LOC);
                   intCodTipDocCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                   intCodDocCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_DOC);
                   
                   intColModCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_NUM_COL);
                   
                   
                   //obtengo el ultimo registro insertado
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_reg) AS nd_ultCodReg";
                    strSQL+=" FROM tbm_detNotPedPreImp AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpCab + "";
                    strSQL+=" AND a1.co_loc=" + intCodLocCab + "";
                    strSQL+=" AND a1.co_tipdoc=" + intCodTipDocCab + "";
                    strSQL+=" AND a1.co_doc=" + intCodDocCab + "";
                    System.out.println("MAX registroDocumento: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intUltRegPedPre=rst.getInt("nd_ultCodReg");
                    if (intUltRegPedPre==-1)
                        intUltRegPedPre=0;
                   
                   
                   if(strColArlCab.equals("I")){
                       if(intColModCab==(intNumColFinPedPre-2)){
                           for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                               intCodItmDet=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString());
                               
                               if(chkCalRepPes.isSelected()){
                                    bdeCanPedPrePes=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                    bdeCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                    bdeCanPedPrePza=calcularPesoCantidadImportar(bdeCanPedPrePes, bdeCanPesItmTmp);
                               }
                               else{//pieza
                                   bdeCanPedPrePza=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                   bdeCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                   bdeCanPedPrePes=calcularPesoCantidadImportar(bdeCanPedPrePza, bdeCanPesItmTmp);
                                   bdeCanPedPrePes=new BigDecimal("0");//se manda a cero porque para cuando es por pieza, no se guarda la TM
                               }                               
                               strLinMod=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString(); 
                               
                               if(strLinMod.equals("M")){
                                    if(bdeCanPedPrePza.compareTo(new BigDecimal("0"))>0){
                                        intUltRegPedPre++;

                                        //si el item no existe se inserta en el detalle(en este caso si tengo los datos del primary key
                                        strSQL="";
                                        strSQL+="INSERT INTO tbm_detNotPedPreImp(";
                                        strSQL+="        co_emp, co_loc, co_tipdoc, co_doc, co_reg";
                                        strSQL+="        , co_itm, nd_can, nd_pestotkgr";
                                        strSQL+="        )";
                                        strSQL+=" (";
                                        strSQL+="   SELECT ";
                                        strSQL+="   " + objParSis.getCodigoEmpresa() + " AS co_emp";//co_emp
                                        strSQL+="   ," + objParSis.getCodigoLocal() + " AS co_loc";//co_loc
                                        strSQL+="   ," + INT_EST_COD_TIP_DOC_PED_PRE + " AS co_tipdoc";//co_tipdoc
                                        strSQL+="   ," + intCodDocPedPre_canRepLSC + " AS co_doc";//co_doc
                                        strSQL+="   ," + intUltRegPedPre + " AS co_reg";//co_reg
                                        strSQL+="   ," + intCodItmDet + " AS co_itm";//co_itm
                                                
                                        strSQL+="   ," + bdeCanPedPrePza + " AS nd_can";//nd_can
                                        strSQL+="   ," + bdeCanPedPrePes + " AS nd_pestotkgr";//nd_can
                                        
                                        strSQL+="	    WHERE NOT EXISTS(";
                                        strSQL+="           SELECT *FROM tbm_detNotPedPreImp";
                                        strSQL+="           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                        strSQL+="           AND co_loc=" + objParSis.getCodigoLocal() + "";
                                        strSQL+="           AND co_tipDoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                                        strSQL+="           AND co_doc=" + intCodDocPedPre_canRepLSC + "";
                                        strSQL+="           AND co_itm=" + intCodItmDet + "";
                                        strSQL+="	   )";
                                        strSQL+=");";
                                        strUpdSQL+=strSQL;
                                        bdeCanPedPrePza=new BigDecimal(BigInteger.ZERO);
                                        bdeCanPedPrePes=new BigDecimal(BigInteger.ZERO);
                                    }
                                }
                            }
                       }
                   }
                   else if(strColArlCab.equals("M")){
                        for(int b=intNumColIniPedPre; b<intNumColFinPedPre;b++){
                            if(intColModCab==b){
                                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                                    intCodItmDet=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString()); 
                                    //bdeCanPedPrePza=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                    

                                    
                                    if(chkCalRepPes.isSelected()){
                                            bdeCanPedPrePes=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString())   );
                                            bdeCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                            bdeCanPedPrePza=calcularPesoCantidadImportar(bdeCanPedPrePes, bdeCanPesItmTmp);
                                    }
                                    else{//pieza
                                        bdeCanPedPrePza=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                        bdeCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                        bdeCanPedPrePes=calcularPesoCantidadImportar(bdeCanPedPrePza, bdeCanPesItmTmp);
                                        bdeCanPedPrePes=new BigDecimal("0");//se manda a cero porque para cuando es por pieza, no se guarda la TM
                                    }
                                    
                                    
                                    strLinMod=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                                    if(strLinMod.equals("M")){
                                        if(bdeCanPedPrePza.compareTo(new BigDecimal("0"))>=0){
                                            intUltRegPedPre++;

                                            //objUti.getStringValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_EMP)
                                            //Armar la sentencia SQL.
                                            strSQL="";
                                            strSQL+="UPDATE tbm_detNotPedPreImp";
                                            strSQL+=" SET nd_can=" + bdeCanPedPrePza + "";
                                            strSQL+=" , nd_pestotkgr=" + bdeCanPedPrePes + "";
                                            strSQL+=" WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+=" AND co_loc=" + intCodLocCab + "";
                                            strSQL+=" AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+=" AND co_doc=" + intCodDocCab + "";
                                            strSQL+=" AND co_itm=" + intCodItmDet + "";
                                            strSQL+=" AND EXISTS(";
                                            strSQL+="           SELECT *FROM tbm_detNotPedPreImp";
                                            strSQL+="           WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+="           AND co_loc=" + intCodLocCab + "";
                                            strSQL+="           AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+="           AND co_doc=" + intCodDocCab + "";
                                            strSQL+="           AND co_itm=" + intCodItmDet + "";
                                            strSQL+="           )";
                                            strSQL+=";";
                                            //System.out.println("UPDATE - strSQL: " + strSQL);
                                            strUpdSQL+=strSQL;
                                            strSQL="";
                                            //si el item no existe se inserta en el detalle(en este caso si tengo los datos del primary key
                                            strSQL+="INSERT INTO tbm_detNotPedPreImp(";
                                            strSQL+="        co_emp, co_loc, co_tipdoc, co_doc, co_reg";
                                            strSQL+="        , co_itm, nd_can, nd_pestotkgr";
                                            strSQL+="        )";
                                            strSQL+=" (";
                                            strSQL+="   SELECT ";
                                            strSQL+="   " + intCodEmpCab + " AS co_emp";//co_emp
                                            strSQL+="   ," + intCodLocCab + " AS co_loc";//co_loc
                                            strSQL+="   ," + intCodTipDocCab + " AS co_tipdoc";//co_tipdoc
                                            strSQL+="   ," + intCodDocCab + " AS co_doc";//co_doc
                                            strSQL+="   ," + intUltRegPedPre + " AS co_reg";//co_reg
                                            strSQL+="   ," + intCodItmDet + " AS co_itm";//co_itm
                                            strSQL+="   ," + bdeCanPedPrePza + " AS nd_can";//nd_can
                                            strSQL+="   ," + bdeCanPedPrePes + " AS nd_pestotkgr";//nd_pestotkgr
                                            strSQL+="	    WHERE NOT EXISTS(";
                                            strSQL+="           SELECT *FROM tbm_detNotPedPreImp";
                                            strSQL+="           WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+="           AND co_loc=" + intCodLocCab + "";
                                            strSQL+="           AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+="           AND co_doc=" + intCodDocCab + "";
                                            strSQL+="           AND co_itm=" + intCodItmDet + "";
                                            strSQL+="	   )";
                                            strSQL+=");";
                                            strUpdSQL+=strSQL;
                                            bdeCanPedPrePza=new BigDecimal(BigInteger.ZERO);
                                            bdeCanPedPrePes=new BigDecimal(BigInteger.ZERO);
                                            
                                            strSQL="";
                                            strSQL+=" DELETE FROM tbm_detNotPedPreImp";
                                            strSQL+=" WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+=" AND co_loc=" + intCodLocCab + "";
                                            strSQL+=" AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+=" AND co_doc=" + intCodDocCab + "";
                                            strSQL+=" AND nd_can=0";
                                            strSQL+=";";
                                            //System.out.println("UPDATE - strSQL: " + strSQL);
                                            strUpdSQL+=strSQL;
                                            
                                            
                                            
                                        }
                                    }
                                }
                            }
                        }
                   }
               }//fin del for
               

               
               
               
               
               System.out.println("update_tbmDetNotPedPreImp: " + strUpdSQL);
               stm.executeUpdate(strUpdSQL);
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
   
   
   

   private boolean cambiarNumeroMesesReponerTabla(){
       boolean blnRes=true;
       try{
           for(int i=0; i<objTblMod.getRowCountTrue(); i++){
               objTblMod.setValueAt(jspNumMesRep.getValue(), i, intNumColIniRep);
           }        
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }   
      
   private boolean recalcularValoresTabla(){
       boolean blnRes=true;
       try{          
            calcularColumnaTotalPedido();
            calcularColumnaTotalPedidoPrevio();
            calcularColumnaTotalesAniMes_1();
            if(optHisMen.isSelected())
                calcularPromedioMensual();
            else
                calcularPromedioAnual();
            calcularCantidadReponerCalculada();
            calcularCantidadReponerManual();
            calcularPesoCantidadImportar();
            calcularTotalPromedioAnualCalculada_peso_1();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_2();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_3();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_4();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualManual_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion manual)
            calcularTotalStockActual_Importacion_peso();//calcula el valor de la columna en la tabla de totales(columna de stock e importacion)
            calcularTotalPedido_peso();//calcula el valor de la columna en la tabla de todades(cada columna del pedido)
            calcularTotalPedidoPrevio_peso();
            calcularTotalCantidadReponerCalculada_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion calculada)
            calcularTotalCantidadReponerManual_peso();//calcula el valor de la columna en la tabla de totales(olumna de reposicion manual)
            calcularTotalCantidadImportar_peso();//calcula el valor de la columna en la tabla de totales(columna de importacion)
            
            //calcularTotalesCostoUniVen_costo();
            calcularTotalesCostoProAnuCalMan_costo();                                                                
            calcularTotalesHistoricoTotales_costo_1();
            calcularTotalesHistoricoTotales_costo_2();
            calcularTotalesHistoricoTotales_costo_3();
            calcularTotalesHistoricoTotales_costo_4();
            calcularTotalesStock_costo();
            calcularTotalesPedidosCurso_costo();
            calcularTotalesPedidosPrevios_costo();
            calcularTotalesCantidadReposicion_costo();
            calcularTotalesCantidadImportar_costo();
            calcularTotalesHistoricoTotalesPeso_peso_1();
            calcularTotalesHistoricoTotalesPeso_peso_2();
            calcularTotalesHistoricoTotalesPeso_peso_3();
            calcularTotalesHistoricoTotalesPeso_peso_4();

            calcularDisponible();//calcula disponible(Stock inicial+importaciones+llegadas)
            calcularVentaPromedioManualCalculado_1();//ventas promedio a 12 meses
            calcularTotalVentaPromedio_peso();
            calcularTotalVentaPromedio_costo();
           
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   
   private boolean recalcularValoresTabla(int fila){
       boolean blnRes=true;
       int i=fila;
       try{
                     
            calcularColumnaTotalPedido(i);
            calcularColumnaTotalPedidoPrevio(i);
            calcularColumnaTotalesAniMes_1(i);
            if(optHisMen.isSelected())
                calcularPromedioMensual(i);
            else
                calcularPromedioAnual(i);
            
            calcularCantidadReponerCalculada(i);
            calcularCantidadReponerManual(i);
            calcularPesoCantidadImportar(i);
            
            
            calcularTotalPromedioAnualCalculada_peso_1();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_2();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_3();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_4();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualManual_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion manual)
            calcularTotalStockActual_Importacion_peso();//calcula el valor de la columna en la tabla de totales(columna de stock e importacion)
            calcularTotalPedido_peso();//calcula el valor de la columna en la tabla de todades(cada columna del pedido)
            calcularTotalPedidoPrevio_peso();
            calcularTotalCantidadReponerCalculada_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion calculada)
            calcularTotalCantidadReponerManual_peso();//calcula el valor de la columna en la tabla de totales(olumna de reposicion manual)
            calcularTotalCantidadImportar_peso();//calcula el valor de la columna en la tabla de totales(columna de importacion)
            
            //--2
            //calcularTotalesCostoUniVen_costo();//fila de costo
            calcularTotalesCostoProAnuCalMan_costo();//fila de costo
            calcularTotalesHistoricoTotales_costo_1();
            calcularTotalesHistoricoTotales_costo_2();
            calcularTotalesHistoricoTotales_costo_3();
            calcularTotalesHistoricoTotales_costo_4();
            calcularTotalesStock_costo();
            calcularTotalesPedidosCurso_costo();
            calcularTotalesPedidosPrevios_costo();
            calcularTotalesCantidadReposicion_costo();
            calcularTotalesCantidadImportar_costo();
            calcularTotalesHistoricoTotalesPeso_peso_1();
            calcularTotalesHistoricoTotalesPeso_peso_2();
            calcularTotalesHistoricoTotalesPeso_peso_3();
            calcularTotalesHistoricoTotalesPeso_peso_4();
           
           
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistoricoTotales_costo_1(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdeCosUniGrp.multiply(bdeTotHisVta1));
                    //Limpia las variables
                    bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta1: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 1, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistoricoTotales_costo_2(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdeCosUniGrp.multiply(bdeTotHisVta1));
                    //Limpia las variables
                    bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta2: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 1, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistoricoTotales_costo_3(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdeCosUniGrp.multiply(bdeTotHisVta1));
                    //Limpia las variables
                    bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta3: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 1, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistoricoTotales_costo_4(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdeCosUniGrp.multiply(bdeTotHisVta1));
                    //Limpia las variables
                    bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta4: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 1, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
   private boolean calcularTotalesCostoProAnuCalMan_costo(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeProAnuCal1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumProAnuCal1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeProAnuCal2=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumProAnuCal2=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeProAnuCal3=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumProAnuCal3=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeProAnuCal4=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumProAnuCal4=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeProAnuMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                //calculado 1
                bdeProAnuCal1=new BigDecimal(objTblMod.getValueAt(i, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(i, intNumColIniProAnu).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniProAnu).toString()));
                bdeSumProAnuCal1=bdeSumProAnuCal1.add(bdeCosUniGrp.multiply(bdeProAnuCal1));
                //calculado 2
                bdeProAnuCal2=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString()));
                bdeSumProAnuCal2=bdeSumProAnuCal2.add(bdeCosUniGrp.multiply(bdeProAnuCal2));
                //calculado 3
                bdeProAnuCal3=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString()));
                bdeSumProAnuCal3=bdeSumProAnuCal3.add(bdeCosUniGrp.multiply(bdeProAnuCal3));
                //calculado 4
                bdeProAnuCal4=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+3))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString()));
                bdeSumProAnuCal4=bdeSumProAnuCal4.add(bdeCosUniGrp.multiply(bdeProAnuCal4));
                
                //manual
                bdeProAnuMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                bdeSumProAnuMan=bdeSumProAnuMan.add(bdeCosUniGrp.multiply(bdeProAnuMan));
                
                bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
                bdeProAnuCal1=new BigDecimal(BigInteger.ZERO);
                bdeProAnuCal2=new BigDecimal(BigInteger.ZERO);
                bdeProAnuCal3=new BigDecimal(BigInteger.ZERO);
                bdeProAnuCal4=new BigDecimal(BigInteger.ZERO);
                bdeProAnuMan=new BigDecimal(BigInteger.ZERO);
            }
            objTblModTot.setValueAt(bdeSumProAnuCal1, 1, intNumColIniProAnu);
            objTblModTot.setValueAt(bdeSumProAnuCal2, 1, (intNumColIniProAnu+1));
            objTblModTot.setValueAt(bdeSumProAnuCal3, 1, (intNumColIniProAnu+2));
            objTblModTot.setValueAt(bdeSumProAnuCal4, 1, (intNumColIniProAnu+3));
            objTblModTot.setValueAt(bdeSumProAnuMan, 1, (intNumColFinProAnu-1));
            bdeSumProAnuCal1=new BigDecimal(BigInteger.ZERO);
            bdeSumProAnuCal2=new BigDecimal(BigInteger.ZERO);
            bdeSumProAnuCal3=new BigDecimal(BigInteger.ZERO);
            bdeSumProAnuCal4=new BigDecimal(BigInteger.ZERO);
            bdeSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

   
   

   

   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistoricoTotalesPeso_peso_1(){
        boolean blnRes=true;
        BigDecimal bdePesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    System.out.println("bdePesItm: " + bdePesItm);
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdePesItm.multiply(bdeTotHisVta1));
                    System.out.println("bdeTotHisVta1: " + bdeTotHisVta1);
                    //Limpia las variables
                    bdePesItm=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta1: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 0, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistoricoTotalesPeso_peso_2(){
        boolean blnRes=true;
        BigDecimal bdePesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    System.out.println("bdePesItm: " + bdePesItm);
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdePesItm.multiply(bdeTotHisVta1));
                    System.out.println("bdeTotHisVta1: " + bdeTotHisVta1);
                    //Limpia las variables
                    bdePesItm=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta1: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 0, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistoricoTotalesPeso_peso_3(){
        boolean blnRes=true;
        BigDecimal bdePesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    System.out.println("bdePesItm: " + bdePesItm);
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdePesItm.multiply(bdeTotHisVta1));
                    System.out.println("bdeTotHisVta1: " + bdeTotHisVta1);
                    //Limpia las variables
                    bdePesItm=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta1: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 0, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistoricoTotalesPeso_peso_4(){
        boolean blnRes=true;
        BigDecimal bdePesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    System.out.println("bdePesItm: " + bdePesItm);
                    //Histórico de ventas 1
                    bdeTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumTotHisVta1=bdeSumTotHisVta1.add(bdePesItm.multiply(bdeTotHisVta1));
                    System.out.println("bdeTotHisVta1: " + bdeTotHisVta1);
                    //Limpia las variables
                    bdePesItm=new BigDecimal(BigInteger.ZERO);
                    bdeTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                System.out.println("bdeSumTotHisVta1: " + bdeSumTotHisVta1);
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bdeSumTotHisVta1, 0, j);
                bdeSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean calcularTotalesStock_costo(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStk=new BigDecimal(0);
        BigDecimal bdeSumStkCos=new BigDecimal(0);

        try{
            for(int j=intNumColIniStk; j<intNumColFinStk; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                    
                    bdeStk=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    bdeSumStkCos=bdeSumStkCos.add(bdeStk.multiply(bdeCosUniGrp));

                    bdeCosUniGrp=new BigDecimal(0);
                    bdeStk=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSumStkCos, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bdeSumStkCos=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private boolean calcularTotalesPedidosCurso_costo(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeSumPedCur=new BigDecimal(0);

        try{
            for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                    
                    bdePedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    bdeSumPedCur=bdeSumPedCur.add(bdePedCur.multiply(bdeCosUniGrp));

                    bdeCosUniGrp=new BigDecimal(0);
                    bdePedCur=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSumPedCur, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bdeSumPedCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private boolean calcularTotalesPedidosPrevios_costo(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePedCur=new BigDecimal(0);
        BigDecimal bdeSumPedCur=new BigDecimal(0);

        try{
            for(int j=intNumColIniPedPre; j<intNumColFinPedPre; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                    
                    bdePedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    bdeSumPedCur=bdeSumPedCur.add(bdePedCur.multiply(bdeCosUniGrp));

                    bdeCosUniGrp=new BigDecimal(0);
                    bdePedCur=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSumPedCur, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bdeSumPedCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private class ZafTblColModLisTot implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
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



    private class ZafTblColModLisTotFix implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblFix.getTableHeader().getResizingColumn()!=null){
                intColSel=tblFix.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblFix.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotFix.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
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
    
    
    private boolean cargarNotasPedidoPrevias(){
        boolean blnRes=true;
        String strCodItm="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc, a1.tx_numdoc2, ";
                strSQL+=" a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.st_cie";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_cie='N'";
                strSQL+=" ORDER BY a1.co_doc";

                rst=stm.executeQuery(strSQL);
                arlDatPedPre.clear();
                while(rst.next()){
                    arlRegPedPre=new ArrayList();
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_EMP,        "" + rst.getString("co_emp"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_LOC,        "" + rst.getString("co_loc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_TIP_DOC,    "" + rst.getString("co_tipdoc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_DOC,        "" + rst.getString("co_doc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_FEC_DOC,        "" + rst.getString("fe_doc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_DOC,        "" + rst.getString("ne_numdoc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_PED_PRE,    "" + rst.getString("tx_numdoc2"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_COL,        "");
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COL_MOD,        "");
                    
                    arlDatPedPre.add(arlRegPedPre);
                }
                
                //para almacenar cantidades del nuevo pedido, los datos se copian de la columna que ha ingresado LSC, FRUIZ o AP
                arlRegPedPre=new ArrayList();
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_EMP,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_LOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_TIP_DOC,    "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_FEC_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_PED_PRE,    "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_COL,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COL_MOD,        "");
                
                arlDatPedPre.add(arlRegPedPre);
                
                //para almacenar cantidades del nuevo pedido, los datos se copian de la columna que ha ingresado LSC, FRUIZ o AP
                arlRegPedPre=new ArrayList();
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_EMP,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_LOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_TIP_DOC,    "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_FEC_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_PED_PRE,    "Totales");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_COL,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COL_MOD,        "");
                
                arlDatPedPre.add(arlRegPedPre);
                
                System.out.println("arlDatPedPre: " + arlDatPedPre.toString());
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
    
   




    private boolean consultaItemsPedidoPrevio(){
        boolean blnRes=true;
        Statement stmCanPedPre;
        ResultSet rstCanPedPre;
        try{
            if(con!=null){
                stmCanPedPre=con.createStatement();
                //crear un arreglo y colocar ahi la informacion para luego ser leida antes de terminar de hacer la consulta.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc";
                strSQL+=" , a1.tx_numdoc2, a2.co_itm, a2.nd_can, a3.tx_codAlt";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1 INNER JOIN tbm_detNotPedPreImp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_inv AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_cie='N'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc, a1.tx_numdoc2";
                strSQL+=" , a2.co_itm, a2.nd_can, a3.tx_codAlt";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
                System.out.println("consultaItemsPedidoPrevio:  "+ strSQL);
                rstCanPedPre=stmCanPedPre.executeQuery(strSQL);
                arlDatDetPedPre.clear();
                while(rstCanPedPre.next()){
                    arlRegDetPedPre=new ArrayList();
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     rstCanPedPre.getString("co_emp"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     rstCanPedPre.getString("co_loc"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, rstCanPedPre.getString("co_tipDoc"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     rstCanPedPre.getString("co_doc"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     rstCanPedPre.getString("co_itm"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, rstCanPedPre.getString("nd_can"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, rstCanPedPre.getString("tx_codAlt"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "");
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     "0");
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");
                    arlDatDetPedPre.add(arlRegDetPedPre);
                }
                
                stmCanPedPre.close();
                stmCanPedPre=null;
                rstCanPedPre.close();
                rstCanPedPre=null;
                
//                System.out.println("arlDatPedPre:     " + arlDatPedPre.toString());
//                System.out.println("arlDatDetPedPre:  " + arlDatDetPedPre.toString());
                
                
                //se cargan los datos de valores del pedido previos
                int intCodEmpNomPedPre=-1;
                int intCodLocNomPedPre=-1;
                int intCodTipDocNomPedPre=-1;
                int intCodDocNomPedPre=-1;
                int intNumColNomPedPre=-1;                
                int intCodEmpDetPedPre=-1;
                int intCodLocDetPedPre=-1;
                int intCodTipDocDetPedPre=-1;
                int intCodDocDetPedPre=-1;                
                String strEstPedPre="";
                
                
                
                for(int d=0; d<(arlDatPedPre.size()); d++){
                    intCodEmpNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_EMP);
                    intCodLocNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_LOC);
                    intCodTipDocNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                    intCodDocNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_DOC);
                    intNumColNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_NUM_COL);
           
                    for(int e=0; e<(arlDatDetPedPre.size()); e++){
                        
                        
                        intCodEmpDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_EMP);
                        intCodLocDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_LOC);
                        intCodTipDocDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                        intCodDocDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_DOC);
                        
                        strEstPedPre=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST);
                        
       
                        if(strEstPedPre.equals("")){
                            if( (intCodEmpNomPedPre==intCodEmpDetPedPre) && (intCodLocNomPedPre==intCodLocDetPedPre) && (intCodTipDocNomPedPre==intCodTipDocDetPedPre) && (intCodDocNomPedPre==intCodDocDetPedPre) ){
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "S");
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_NUM_COL, ""+intNumColNomPedPre);
                            }
                        }
                    }
                }                
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "");
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
    
    private boolean existeNombrePedidoPrevioDB(String nombrePedido){
        boolean blnRes=false;
        String strSQLAux="";
        Statement stmExiNomPedPre;
        ResultSet rstExiNomPedPre;
        Connection conExiNomPedPre;

        try{
            conExiNomPedPre=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conExiNomPedPre!=null){
                stmExiNomPedPre=conExiNomPedPre.createStatement();
                strSQLAux="";
                strSQLAux+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.tx_numdoc2";
                strSQLAux+=" FROM tbm_cabnotpedpreimp AS a1";
                strSQLAux+=" WHERE a1.tx_numdoc2='" + nombrePedido + "'";
                strSQLAux+=" AND a1.st_reg='A' AND a1.st_cie='N'";
                strSQLAux+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
                rstExiNomPedPre=stmExiNomPedPre.executeQuery(strSQLAux);
                if(rstExiNomPedPre.next()){
                    intCodEmpExiNomPedPre=rstExiNomPedPre.getInt("co_emp");
                    intCodLocExiNomPedPre=rstExiNomPedPre.getInt("co_loc");
                    intCodTipDocExiNomPedPre=rstExiNomPedPre.getInt("co_tipdoc");
                    intCodDocExiNomPedPre=rstExiNomPedPre.getInt("co_doc");
                    blnRes=true;
                }
                
                System.out.println("intCodEmpExiNomPedPre: " + intCodEmpExiNomPedPre);
                System.out.println("intCodLocExiNomPedPre: " +intCodLocExiNomPedPre);
                System.out.println("intCodTipDocExiNomPedPre: " + intCodTipDocExiNomPedPre);
                System.out.println("intCodDocExiNomPedPre: " + intCodDocExiNomPedPre);
                
                
                conExiNomPedPre.close();
                conExiNomPedPre=null;
                stmExiNomPedPre.close();
                stmExiNomPedPre=null;
                rstExiNomPedPre.close();
                rstExiNomPedPre=null;
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
     * Esta función selecciona las celdas de un JTable de acuerdo al tipo de selección.
     * El tipo de selección permite que se selecionen todas las celdas de una fila, de una columna
     * o ciertas celdas en particular. Esta selección es aparente ya que cuando se utilizan las
     * funciones "getSelectedRows()" y "getSelectedColumns()" para determinar las celdas seleccionadas
     * sólo se devuelve como celda seleccionada la celda actual cuando en realidad están selecionadas
     * todas las celdas de una columna(s) en particular o todas las celdas de una fila(s) en particular
     * Los métodos que permiten cortar, copiar, etc. utilizan dichos métodos para determinar las celdas
     * seleccionadas. Es por eso que antes de cortar, copiar, etc. se utiliza éste método para seleccionar
     * las celdas que deben estar seleccionadas de acuerdo al tipo de selección de celdas que tiene
     * establecido el JTable.
     * @return true: Si se pudieron seleccionar las celdas.
     * <BR>false: En el caso contrario.
     */
    private boolean seleccionarCel()
    {
        boolean blnRes=true;
        try
        {
            if (tblDat.getRowSelectionAllowed())
            {
                if (tblDat.getColumnSelectionAllowed())
                {
                    //Selección por celda.
                }
                else
                {
                    //Selección por fila.
                    tblDat.setColumnSelectionInterval(0, tblDat.getColumnCount()-1);
                }
            }
            else
            {
                if (tblDat.getColumnSelectionAllowed())
                {
                    //Selección por columna.
                    tblDat.setRowSelectionInterval(0, tblDat.getRowCount()-1);
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
/**
 * Función que permite obtener la cantidad en peso y en piezas
 * @param cantidad
 * @return 
 */
    
    private BigDecimal calcularPesoCantidadImportar(BigDecimal cantidad_Peso, BigDecimal pesoItem){
        BigDecimal bdeCanImp=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesTotKgr=new BigDecimal(0);
        BigDecimal bdeValRet_segTipImp=new BigDecimal(0);//pieza o tm
        try{
            bdePesItm=pesoItem;
            if(chkCalRepPes.isSelected()){//tengo peso obtengo la pieza
                //para calcular cantidad en PIEZAS
                
                bdePesTotKgr=cantidad_Peso;//es peso
                if(bdePesTotKgr.compareTo(new BigDecimal("0"))>0)
                    bdeCanImp=bdeCanImp.add((bdePesTotKgr.divide(bdePesItm, 0, BigDecimal.ROUND_HALF_UP)));//cantidad que se calcula
                
                //retorna pieza
                bdeValRet_segTipImp=bdeCanImp;
            }
            else{
                //para calcular cantidad en TM
                bdeCanImp=cantidad_Peso;
                bdePesTotKgr=bdePesTotKgr.add((bdeCanImp.multiply(bdePesItm)));//cantidad que se calcula
                
                //retorna bdePesTotKgr
                bdeValRet_segTipImp=bdePesTotKgr;
            }
            
            bdeValRet_segTipImp=new BigDecimal("" + Math.ceil(Double.parseDouble(""+bdeValRet_segTipImp))); //redondeando a mas
            
        }
        catch(Exception e){
            bdeValRet_segTipImp=BigDecimal.ZERO;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bdeValRet_segTipImp;
    }
//    
//    private boolean getCostoMovimientoImportacion(){
//        boolean blnRes=true;
//        try{
//            
//        }
//        catch(Exception e){
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    

    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido previa
     * @return true Si no existe seguimiento asociado para la Nota de Pedido Previa
     * <BR> false Caso contrario
     */
    private boolean getCodSegNotExiNotPedPre(){
        boolean blnRes=true;
        objCodSegNotPedPre=null;
        try{
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a3.co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" ON a1.co_emp=a3.co_empRelCabNotPedPreImp AND a1.co_loc=a3.co_locRelCabNotPedPreImp";
                strSQL+=" AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedPreImp AND a1.co_doc=a3.co_docRelCabNotPedPreImp";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                strSQL+=" AND a1.co_doc=" + intCodDocPedPre_canRepLSC + "";
                System.out.println("getCodSegNotExiNotPedPre: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    objCodSegNotPedPre=rst.getObject("co_seg");
                   // blnRes=true;
                }
//                if(objCodSegNotPedPre==null){//no existe seguimiento por tanto se debe ingresar 
//                   // blnRes=true;
//                }
//                else{//ya existe en el seguimiento la nota de pedido por tanto no se debe hacer nada
//                    //blnRes=false; no es necesario porque desde el inicio está en "false"
//                }
                
                
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - getCodSegExiNotPedPre SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - getCodSegExiNotPedPre: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    private boolean calcularDisponible(){
        boolean blnRes=true;
        BigDecimal bdeStkActIni=BigDecimal.ZERO;
        BigDecimal bdeStkImp=BigDecimal.ZERO;
        
        BigDecimal bdeStkPedEmb=BigDecimal.ZERO;//Pedido Embarcado
        BigDecimal bdeStkPedPre=BigDecimal.ZERO;//Pedido Previo        
        BigDecimal bdeStkPedLle=BigDecimal.ZERO;//Pedido Embarcado+Pedido Previo
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeStkActIni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()))    ;//Stock inicial
                bdeStkImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+1)).toString()))    ;//Stock importaciones
                bdeStkPedEmb=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinPedEmb-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinPedEmb-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinPedEmb-1)).toString()))    ;//Pedido Embarcado
                bdeStkPedPre=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinPedPre-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinPedPre-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinPedPre-1)).toString()))    ;//Pedido Previo
                bdeStkPedLle=bdeStkPedEmb.add(bdeStkPedPre);
                
                objTblMod.setValueAt(bdeStkPedLle, i, intNumColIniDis);//disponible
            }            
        }
        catch(Exception e){
            System.out.println("Error - calcularDisponible: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean calcularVentaPromedioManualCalculado_1(){
        boolean blnRes=true;
        Object objProCalVta;
        Object objProManVta;
        BigDecimal bdeProCalVtaMen;
        BigDecimal bdeProManVtaMen;

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                objProCalVta=objTblMod.getValueAt(i, intNumColIniProAnu);//Calculado
                objProManVta=objTblMod.getValueAt(i, (intNumColFinProAnu-1));//Manual
                
                bdeProCalVtaMen=new BigDecimal(objProCalVta==null?"0":(objProCalVta.toString().equals("")?"0":objProCalVta.toString())).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                bdeProManVtaMen=new BigDecimal(objProManVta==null?"0":(objProManVta.toString().equals("")?"0":objProManVta.toString())).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                
                objTblMod.setValueAt(bdeProCalVtaMen, i, intNumColIniVtaPro);//calculado1
                objTblMod.setValueAt(bdeProManVtaMen, i, (intNumColIniVtaPro+1));//manual
            }            
        }
        catch(Exception e){
            System.out.println("Error - calcularVentaPromedio: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Función que permite calcular el valor de total manual y calculado de las Ventas Promedio
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calcularTotalVentaPromedio_costo(){
        boolean blnRes=true;
        BigDecimal bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeProAnuCal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeProAnuMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumProAnuCal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_GRP).toString()));
                //calculado
                bdeProAnuCal=new BigDecimal(objTblMod.getValueAt(i, intNumColIniVtaPro)==null?"0":(objTblMod.getValueAt(i, intNumColIniVtaPro).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniVtaPro).toString()));
                bdeSumProAnuCal=bdeSumProAnuCal.add(bdeCosUniGrp.multiply(bdeProAnuCal));
                //manual
                bdeProAnuMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniVtaPro+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString()));
                bdeSumProAnuMan=bdeSumProAnuMan.add(bdeCosUniGrp.multiply(bdeProAnuMan));
                
                bdeCosUniGrp=new BigDecimal(BigInteger.ZERO);
                bdeProAnuCal=new BigDecimal(BigInteger.ZERO);
                bdeProAnuMan=new BigDecimal(BigInteger.ZERO);
            }
            objTblModTot.setValueAt(bdeSumProAnuCal, 1, intNumColIniVtaPro);
            objTblModTot.setValueAt(bdeSumProAnuMan, 1, (intNumColIniVtaPro+1));
            bdeSumProAnuCal=new BigDecimal(BigInteger.ZERO);
            bdeSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    
    private boolean calcularTotalVentaPromedio_peso(){
        boolean blnRes=true;
        BigDecimal bdeCanPed=new BigDecimal(0);
        BigDecimal bdePesItm=new BigDecimal(0);
        BigDecimal bdePesItm_CanPed=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanPed1=new BigDecimal(0);
        BigDecimal bdeSum_PesItm_CanPed2=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdePesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                //Pedido 1
                bdeCanPed=new BigDecimal(objTblMod.getValueAt(i, intNumColIniVtaPro)==null?"0":(objTblMod.getValueAt(i, intNumColIniVtaPro).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniVtaPro).toString()));
                bdePesItm_CanPed=bdeCanPed.multiply(bdePesItm);
                bdeSum_PesItm_CanPed1=bdeSum_PesItm_CanPed1.add(bdePesItm_CanPed);
                bdeCanPed=new BigDecimal(0);
                bdePesItm_CanPed=new BigDecimal(0);
                //Pedido 2
                bdeCanPed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniVtaPro+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString()));
                bdePesItm_CanPed=bdeCanPed.multiply(bdePesItm);
                bdeSum_PesItm_CanPed2=bdeSum_PesItm_CanPed2.add(bdePesItm_CanPed);
                //se setea los campos de calculo
                bdeCanPed=new BigDecimal(0);
                bdePesItm=new BigDecimal(0);
                bdePesItm_CanPed=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanPed1, objParSis.getDecimalesMostrar()), 0, intNumColIniVtaPro);//columna de promedio anual calculado del Pedido 1
            bdeSum_PesItm_CanPed1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bdeSum_PesItm_CanPed2, objParSis.getDecimalesMostrar()), 0, (intNumColIniVtaPro+1));//columna de promedio anual calculado del Pedido 2
            bdeSum_PesItm_CanPed2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean setValMaxCal(){
        boolean blnRes=true;
        BigDecimal bdeMaxCal=BigDecimal.ZERO;
        BigDecimal bdeValCal=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                    if((j>=intNumColIniProAnu) && (j<(intNumColFinProAnu-2)) ){//Calculado 1 hasta calculado 4
                        bdeValCal=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if(bdeValCal.compareTo(bdeMaxCal)>0)
                            bdeMaxCal=bdeValCal;
                    }
                }                
                objTblMod.setValueAt((bdeMaxCal.compareTo(BigDecimal.ZERO)==0?null:bdeMaxCal), i, (intNumColFinProAnu-2));
                bdeMaxCal=BigDecimal.ZERO;
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite ocultar el histórico de ventas según el filtro seleccionado
     */
    private void ocultaColumnasHistoricoVentas(){
        try{
            if(!chkHisVta.isSelected()){
                for(int i=intNumColIniFecCor; i<intNumColFinFecCor; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor2; i<intNumColFinFecCor2; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor3; i<intNumColFinFecCor3; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor4; i<intNumColFinFecCor4; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
            }
            else{
                for(int i=intNumColIniFecCor; i<intNumColFinFecCor; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor2; i<intNumColFinFecCor2; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor3; i<intNumColFinFecCor3; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor4; i<intNumColFinFecCor4; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Función que permite ocultar columnas de totales según el filtro seleccionado
     */
    private void ocultaColumnasTotales(){
        try{
            if(!chkHisVta.isSelected()){
                for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                }
            }
            else{
                for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                    objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }


    
    /**
     * Función que permite ocultar columna de veces según el filtro seleccionado
     */
    private void ocultaColumnaVeces(){
        try{
            if(!chkHisVtaVec.isSelected()){
                objTblMod.addSystemHiddenColumn((intNumColIniFecCor+2), tblDat);
            }
            else{
                objTblMod.removeSystemHiddenColumn((intNumColIniFecCor+2), tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    
    /**
     * Función que permite ocultar la columna de stock histórico(inicial)
     */
    private void ocultaColumnasStkHis(){
        try{
            if(optHisAnu.isSelected()){
                for(int i=intNumColIniFecCor; i<intNumColFinFecCor; i++){
                    if(i==intNumColIniFecCor)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor2; i<intNumColFinFecCor2; i++){
                    if(i==intNumColIniFecCor2)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor3; i<intNumColFinFecCor3; i++){
                    if(i==intNumColIniFecCor3)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor4; i<intNumColFinFecCor4; i++){
                    if(i==intNumColIniFecCor4)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
            }
            else{
                for(int i=intNumColIniFecCor; i<intNumColFinFecCor; i++){
                    if(i==intNumColIniFecCor)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor2; i<intNumColFinFecCor2; i++){
                    if(i==intNumColIniFecCor2)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor3; i<intNumColFinFecCor3; i++){
                    if(i==intNumColIniFecCor3)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniFecCor4; i<intNumColFinFecCor4; i++){
                    if(i==intNumColIniFecCor4)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Función que permite ocultar columnas de totales según el filtro seleccionado
     */
    private void ocultaColumnasTotalesStkHis(){
        try{
            if(optHisAnu.isSelected()){
                for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                    if(i==intNumColIniTot)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                    if(i==intNumColIniTot2)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                    if(i==intNumColIniTot3)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                    if(i==intNumColIniTot4)
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                }
            }
            else{
                for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                    if(i==intNumColIniTot)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                    if(i==intNumColIniTot2)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                    if(i==intNumColIniTot3)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
                for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                    if(i==intNumColIniTot4)
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
   
}
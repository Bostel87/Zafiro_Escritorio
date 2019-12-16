/*
 * ZafImp30.java
 *
 * Created on 09 de Noviembre de 2017, 15:35 PM
 * 
 */
package Importaciones.ZafImp30;
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
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
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
 * @author  Rosa Zambrano
 */
public class ZafImp30 extends javax.swing.JInternalFrame
{
    //Constantes: Tab General
    static final int INT_TBL_GRL_DAT_LIN=0;
    static final int INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB=1;
    static final int INT_TBL_GRL_DAT_CODDOC_PED_EMB=2;
    static final int INT_TBL_GRL_DAT_NUM_PED_EMB=3;
    static final int INT_TBL_GRL_DAT_COD_CTA_PED_EMB=4;                
    static final int INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB=5;                
    static final int INT_TBL_GRL_DAT_CHK_ASI_PRO=6;
    static final int INT_TBL_GRL_DAT_BUT_PED_EMB=7;                //Botón del Pedido Embarcado.
    static final int INT_TBL_GRL_DAT_COD_CAR_PAG=8;
    static final int INT_TBL_GRL_DAT_DES_CAR_PAG=9;
    static final int INT_TBL_GRL_DAT_BUT_CAR_PAG=10;                //Botón del cargo a pagar.
    static final int INT_TBL_GRL_DAT_COD_MOT=11;
    static final int INT_TBL_GRL_DAT_DES_COR_MOT=12;
    static final int INT_TBL_GRL_DAT_DES_LAR_MOT=13;
    static final int INT_TBL_GRL_DAT_CHK_MOT=14;                    //Check del Motivo.
    static final int INT_TBL_GRL_DAT_BUT_MOT=15;                    //Botón del Motivo.
    static final int INT_TBL_GRL_DAT_COD_REG=16;
    static final int INT_TBL_GRL_DAT_CAN=17;
    static final int INT_TBL_GRL_DAT_PRE=18;
    static final int INT_TBL_GRL_DAT_VAL_SUBTOT=19;
    static final int INT_TBL_GRL_DAT_CHK_IVA=20;
    static final int INT_TBL_GRL_DAT_VAL_IVA=21;
    static final int INT_TBL_GRL_DAT_VAL_TOT=22;

    //Constantes: Tab Forma de Pago
    static final int INT_TBL_FOR_PAG_DAT_LIN=0;
    static final int INT_TBL_FOR_PAG_DAT_DIA_CRE=1;
    static final int INT_TBL_FOR_PAG_DAT_FEC_VEN=2;
    static final int INT_TBL_FOR_PAG_DAT_COD_TIP_RET=3;
    static final int INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET=4;
    static final int INT_TBL_FOR_PAG_DAT_BUT_TIP_RET=5;
    static final int INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET=6;
    static final int INT_TBL_FOR_PAG_DAT_POR_RET=7;
    static final int INT_TBL_FOR_PAG_DAT_APL=8;
    static final int INT_TBL_FOR_PAG_DAT_EST_APL=9;
    static final int INT_TBL_FOR_PAG_DAT_BAS_IMP=10;
    static final int INT_TBL_FOR_PAG_DAT_COD_SRI=11;
    static final int INT_TBL_FOR_PAG_DAT_VAL_RET=12;

    //Constantes: Tab Facturas de Proveedores
    static final int INT_TBL_FAC_PRV_DAT_LIN=0;
    static final int INT_TBL_FAC_PRV_DAT_NUM_FAC=1;
    static final int INT_TBL_FAC_PRV_DAT_FEC_FAC=2;
    static final int INT_TBL_FAC_PRV_DAT_NUM_SER=3;
    static final int INT_TBL_FAC_PRV_DAT_NUM_AUT=4;
    static final int INT_TBL_FAC_PRV_DAT_FEC_CAD=5;
    static final int INT_TBL_FAC_PRV_DAT_VAL_FAC=6;
    static final int INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA=7;
    static final int INT_TBL_FAC_PRV_DAT_SUB=8;
    static final int INT_TBL_FAC_PRV_DAT_IVA=9;
    static final int INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG=10;

    //Constantes: Tab Asiento de Diario.
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
    
    //ArrayList para consultar
    private ArrayList arlDatConDxP, arlRegConDxP;
    private static final int INT_ARL_CON_DXP_COD_EMP=0;  
    private static final int INT_ARL_CON_DXP_COD_LOC=1;   
    private static final int INT_ARL_CON_DXP_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_DXP_COD_DOC=3;  
    private static final int INT_ARL_CON_DXP_TXT_USRING=4;  
    private static final int INT_ARL_CON_DXP_TXT_USRMOD=5;  
    private int intIndiceDxP=0;
    
    //ArrayList para Agrupar datos de motivos
    private ArrayList arlDatMot, arlRegMot;
    private static final int INT_ARL_MOT_COD_MOT=0;  
    
    //ArrayList para Agrupar datos de pedidos embarcados.
    public ArrayList arlDatPedEmb, arlRegPedEmb;
    public static final int INT_ARL_PED_EMB_COD_EMP=0;  
    public static final int INT_ARL_PED_EMB_COD_LOC=1;  
    public static final int INT_ARL_PED_EMB_COD_TIPDOC=2;  
    public static final int INT_ARL_PED_EMB_COD_DOC=3;  
    public static final int INT_ARL_PED_EMB_NUM_PED=4;                          
    public static final int INT_ARL_PED_EMB_COD_CTA_PED=5;  
    public static final int INT_ARL_PED_EMB_COD_CTA_PRO=6;  
    public static final int INT_ARL_PED_EMB_VAL_DXP_ACT=7;  
    public static final int INT_ARL_PED_EMB_VAL_PRO_ACT=8;  
    public static final int INT_ARL_PED_EMB_VAL_GAS_PRO=9;  
    public static final int INT_ARL_PED_EMB_VAL_DXP_TOT=10;  
    public static final int INT_ARL_PED_EMB_VAL_PRO_TOT=11;  
    public static final int INT_ARL_PED_EMB_CHK_ASI_PRO=12;  
            
    //ArrayList:
    private ArrayList arlRegAutPag, arlDatAutPag;
    final int INT_ARL_COD_REG=0;
    final int INT_ARL_EST_AUT_PAG=1;
    final int INT_ARL_COD_CTA_PAG=2;

    //ArrayList: Forma de Pago
    private ArrayList arlDatDiaPag_ForPag, arlRegDiaPag_ForPag;
    private int INT_ARL_DIA_PAG_COD_EMP=0;
    private int INT_ARL_DIA_PAG_COD_FOR_PAG=1;
    private int INT_ARL_DIA_PAG_NUM_DIA_CRE=2;
    
    //ArrayList: Cuentas Subtotales y Provisiones
    private ArrayList arlDatCtaSubTot, arlRegCtaSubTot;
    private ArrayList arlDatCtaPro, arlRegCtaPro;
    private int INT_ARL_CTA_COD_CTA=0;
    private int INT_ARL_CTA_NUM_CTA=1;
    private int INT_ARL_CTA_NOM_CTA=2;
    
    //
    private final int INT_COD_TIP_DOC_REC=177;
   
    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod,objTblModForPag, objTblModFacPrv;
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private ZafSegMovInv objSegMovInv;
    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".
    private ZafVenCon vcoCom;  
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoBen;
    private ZafVenCon vcoForPag;
    private ZafVenCon vcoMot;
    private ZafVenCon vcoRet;
    private ZafVenCon vcoPedEmb;                           //Ventana de consulta "Pedido Embarcado".
    private ZafVenCon vcoCarPag;                           //Ventana de consulta "Cargos a Pagar".
    private ZafDocLis objDocLis;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuMot, objTblPopMnuForPag, objTblPopMnuFacPrv;
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAdaForPag objMouMotAdaForPag;
    private ZafMouMotAdaFacPrv objMouMotAdaFacPrv;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChkAsiPro;
    private ZafTblCelRenChk objTblCelRenChkMot;
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
    private ZafTblCelRenBut objTblCelRenButForPag;
    private ZafTblCelEdiButVco objTblCelEdiButVcoCarPag;
    private ZafTblCelEdiButVco objTblCelEdiButVcoPedEmb;
    private ZafTblCelEdiButVco objTblCelEdiButVcoMot;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCarPag;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoPedEmb;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoMot;
    private ZafTblCelEdiButVco objTblCelEdiButvcoRet;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoRet;
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafDatePicker dtpFecVenFacPrv, dtpFecAct;
    private ZafDatePicker dtpFecFacPrv;
    private ZafDatePicker dtpFecCorIni;
    private Object objCodSegPedEmb;
    private ZafImp30_01 objImp30_01;
    
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private boolean blnNecAutAnu;                       //true si necesita de autorizacion el documento, si no necesita es false
    private boolean blnDocAut;                          //true si el documento ya fue autorizado para ser anulado, false si falta todavia autorizarlo
    private boolean blnDetDocTieIva, blnIsCos_Ecu_Det;
    private boolean blnMosVenCiePedLoc;                 //True: Indica que debe mostrar ventana para cerrar pagos locales de los pedidos embarcados.
    private Vector vecDat, vecCab, vecReg, vecAux;
    private Vector vecDatForPag, vecRegForPag;
    private Vector vecDatFacPrv, vecRegFacPrv;
    
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private int intCodCtaIva;
    private int intCodCtaTot;
    private int intCodDocRec;
    public int intCodEmpGrp;
    public int intCodLocGrp;
    
    private String strSQL, strAux;
    private String strIdePrv, strDirPrv, strCodPrv, strDesLarPrv, strTelPrv, strCiuPrv;
    private String strCodForPag, strNomForPag;
    private String strNumSerFacPrv;
    private String strNumAutSriFacPrv;
    private String strNumFecCadFacPrv;
    private String strFecDocIni;
    private String strEstImpDoc;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strCodBen, strNomBen;                //Contenido del campo al obtener el foco.
    private String strAliCom, strNomCom, strCodCom;
    private String strNumCtaIva;
    private String strNomCtaIva;
    private String strNomCtaTot;
    private String strNumCtaTot;
    private String strCodCtaGas;
    private String strNumCtaGas;
    private String strNomCtaGas;        
    private String strFecSis;
    private String strCodCatTipDoc;
    private String strNecPerAutPag;
    private String strCodTipDocOpimpo="106";
    private String strCodTipDocOpcolo="246";
    private String strVersion=" v0.1.8 ";

    /** Crea una nueva instancia de la clase ZafImp30. */
    public ZafImp30(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatDiaPag_ForPag=new ArrayList();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();

            vecDatForPag=new Vector();
            vecDatFacPrv=new Vector();
            vecDatDia=new Vector();
            vecDat=new Vector();

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


    /** Creates new form ZafImp30 */
    public ZafImp30(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
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
            
            //Por Grupo: Solo consulta de DxP
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                objTooBar.setVisibleInsertar(false);
            }
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleAnular(false);
            
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            
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
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodTipDoc.setVisible(false);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtRucPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBen.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBen.setBackground(objParSis.getColorCamposObligatorios());
            txtUsrCom.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCom.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodDoc.setEditable(false);
            txtGasPro.setEditable(false);
            
            txtSub.setBackground(objParSis.getColorCamposSistema());
            txtSub.setEditable(false);
            txtIva.setBackground(objParSis.getColorCamposSistema());
            txtIva.setEditable(false);
            txtTot.setBackground(objParSis.getColorCamposSistema());
            txtTot.setEditable(false);

            //Configurar las ZafVenCon.
            configurarVenConCarPag();
            configurarVenConPedEmb();
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
            configurarTblForPag();
            configurarTblFacPrv();

            txtSub.setEnabled(false);
            txtIva.setEnabled(false);
            txtTot.setEnabled(false);

            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

            //Configurar ZafDatePicker:
            dtpFecAct=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecAct.setText(strFecSis);

            txtNumForPag.setVisible(false);
            txtTipForPag.setVisible(false);

            txtCodCom.setVisible(false);
            txtCodCom.setEditable(false);
            txtCodCom.setEnabled(false);
            
            strCodCatTipDoc="";
            
            intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
            intCodLocGrp=1;
            
            if(objParSis.getCodigoUsuario()!=1){
                lblGasPro.setVisible(false);
                txtGasPro.setVisible(false);
            }
        } 
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_GRL_DAT_LIN,"");
            vecCab.add(INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB,"Cód.Tip.Doc.Ped.Emb.");
            vecCab.add(INT_TBL_GRL_DAT_CODDOC_PED_EMB,"Cód.Ped.Emb.");
            vecCab.add(INT_TBL_GRL_DAT_NUM_PED_EMB,"Ped.Emb.");
            vecCab.add(INT_TBL_GRL_DAT_COD_CTA_PED_EMB,"Cta.Ped.");
            vecCab.add(INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB,"Cta.Pro.");
            vecCab.add(INT_TBL_GRL_DAT_CHK_ASI_PRO,"Con Asi.Pro.");
            vecCab.add(INT_TBL_GRL_DAT_BUT_PED_EMB,"...");
            vecCab.add(INT_TBL_GRL_DAT_COD_CAR_PAG,"Cód.Car.");
            vecCab.add(INT_TBL_GRL_DAT_DES_CAR_PAG,"Cargo a Pagar");
            vecCab.add(INT_TBL_GRL_DAT_BUT_CAR_PAG,"...");
            vecCab.add(INT_TBL_GRL_DAT_COD_MOT,"Cód.Mot.");
            vecCab.add(INT_TBL_GRL_DAT_DES_COR_MOT,"Motivo");
            vecCab.add(INT_TBL_GRL_DAT_DES_LAR_MOT,"Des.Lar.Mot.");
            vecCab.add(INT_TBL_GRL_DAT_CHK_MOT,"Con Mot.");
            vecCab.add(INT_TBL_GRL_DAT_BUT_MOT,"...");
            vecCab.add(INT_TBL_GRL_DAT_COD_REG,"#Reg.");
            vecCab.add(INT_TBL_GRL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_GRL_DAT_PRE,"Precio");
            vecCab.add(INT_TBL_GRL_DAT_VAL_SUBTOT,"Subtotal");
            vecCab.add(INT_TBL_GRL_DAT_CHK_IVA,"Con Iva");
            vecCab.add(INT_TBL_GRL_DAT_VAL_IVA,"Iva");
            vecCab.add(INT_TBL_GRL_DAT_VAL_TOT,"Total");            

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_PRE, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_VAL_SUBTOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_VAL_IVA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_GRL_DAT_VAL_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
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
            tcmAux.getColumn(INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CODDOC_PED_EMB).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_NUM_PED_EMB).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_GRL_DAT_COD_CTA_PED_EMB).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_ASI_PRO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_PED_EMB).setPreferredWidth(20);            
            tcmAux.getColumn(INT_TBL_GRL_DAT_COD_CAR_PAG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_CAR_PAG).setPreferredWidth(87);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_CAR_PAG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_COD_MOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_COR_MOT).setPreferredWidth(58);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_LAR_MOT).setPreferredWidth(58);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_MOT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_MOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_GRL_DAT_COD_REG).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CAN).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_GRL_DAT_PRE).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_GRL_DAT_VAL_SUBTOT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_IVA).setPreferredWidth(43);
            tcmAux.getColumn(INT_TBL_GRL_DAT_VAL_IVA).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_GRL_DAT_VAL_TOT).setPreferredWidth(75);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_COD_CAR_PAG, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_CODDOC_PED_EMB, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_COD_MOT, tblDat);     
            //objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_DES_LAR_MOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_BUT_MOT, tblDat);     
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_COD_REG, tblDat);     
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_COD_CTA_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_CHK_ASI_PRO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_GRL_DAT_CHK_MOT, tblDat);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_ASI_PRO).setResizable(false);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_MOT).setResizable(false);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_IVA).setResizable(false);
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
            vecAux.add("" + INT_TBL_GRL_DAT_DES_CAR_PAG);   
            vecAux.add("" + INT_TBL_GRL_DAT_BUT_CAR_PAG);   
            vecAux.add("" + INT_TBL_GRL_DAT_NUM_PED_EMB);   
            vecAux.add("" + INT_TBL_GRL_DAT_BUT_PED_EMB);   
            //vecAux.add("" + INT_TBL_GRL_DAT_DES_COR_MOT);   
            //vecAux.add("" + INT_TBL_GRL_DAT_BUT_MOT);  
            vecAux.add("" + INT_TBL_GRL_DAT_CAN);
            vecAux.add("" + INT_TBL_GRL_DAT_PRE);
            vecAux.add("" + INT_TBL_GRL_DAT_CHK_IVA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_CAR_PAG).setResizable(false);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_PED_EMB).setResizable(false);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_MOT).setResizable(false);
            
            //Establecer formato botón.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_CAR_PAG).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_PED_EMB).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_MOT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_CAR_PAG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_NUM_PED_EMB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_COR_MOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_LAR_MOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;            

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_PRE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_VAL_SUBTOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_VAL_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_VAL_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                                
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkAsiPro=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_ASI_PRO).setCellRenderer(objTblCelRenChkAsiPro);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkMot=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_MOT).setCellRenderer(objTblCelRenChkMot);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkIva=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_IVA).setCellRenderer(objTblCelRenChkIva);
            
            //Configurar JTable: Editor de celdas
            objTblCelEdiChkIva=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_GRL_DAT_CHK_IVA).setCellEditor(objTblCelEdiChkIva);
            objTblCelEdiChkIva.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdgValAplEvn=new BigDecimal(0);
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    isExiAsiDiaPro(intFil);
                    calcularTot(intFil);
                    cargarValFacFacPrv();
                    calcularPagosDxP();
                    generarAsiDiaDxP();
                    generarObsDxP();
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
                    isExiAsiDiaPro(intFil);
                    calcularTot(intFil);
                    cargarValFacFacPrv();
                    calcularPagosDxP();
                    generarAsiDiaDxP();
                    generarObsDxP();
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
                    isExiAsiDiaPro(intFil);
                    calcularTot(intFil);
                    cargarValFacFacPrv();
                    calcularPagosDxP();
                    generarAsiDiaDxP();
                    generarObsDxP();
                }
            });
            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Pedidos Embarcados.*/">
            int intColVen[]=new int[5];            
            intColVen[0]=3; //co_tipDoc
            intColVen[1]=4; //co_doc
            intColVen[2]=5; //tx_numDoc2
            intColVen[3]=6; //co_CtaPed
            intColVen[4]=7; //co_ctaPro
            int intColTbl[]=new int[5];            
            intColTbl[0]=INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB;
            intColTbl[1]=INT_TBL_GRL_DAT_CODDOC_PED_EMB;
            intColTbl[2]=INT_TBL_GRL_DAT_NUM_PED_EMB;
            intColTbl[3]=INT_TBL_GRL_DAT_COD_CTA_PED_EMB;
            intColTbl[4]=INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB;
            
            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoPedEmb=new ZafTblCelEdiTxtVco(tblDat, vcoPedEmb, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_NUM_PED_EMB).setCellEditor(objTblCelEdiTxtVcoPedEmb);
            objTblCelEdiTxtVcoPedEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bgdCan = new BigDecimal("1");
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese el campo tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoPedEmb.setCancelarEdicion(true);
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtCodPrv.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un pedido embarcado ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoPedEmb.setCancelarEdicion(true);
                        txtCodPrv.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoPedEmb.setCampoBusqueda(4);
                    vcoPedEmb.setCriterio1(11);
                    strSQL="";
                    if(txtCodTipDoc.getText().equals(strCodTipDocOpimpo)){ //opimpo
                        strSQL+=" WHERE b1.co_CtaPed IS NOT NULL";
                    }
                    strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc2, b1.co_CtaPed, b1.co_ctaPro";
                    strSQL+=" ORDER BY b1.tx_numDoc2 ";   
                    vcoPedEmb.setCondicionesSQL(strSQL);
                    //System.out.println("vcoPedEmb.condicion: "+vcoPedEmb.getCondicionesSQL());
                    //System.out.println("vcoPedEmb.getSentenciaSQL(): "+vcoPedEmb.getSentenciaSQL());                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoPedEmb.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(3), intFil, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(4), intFil, INT_TBL_GRL_DAT_CODDOC_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(5), intFil, INT_TBL_GRL_DAT_NUM_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(6), intFil, INT_TBL_GRL_DAT_COD_CTA_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(7), intFil, INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB);
                        //Limpiar Datos de la Tabla, cada vez que elige un nuevo pedido embarcado
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_COD_CAR_PAG);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_DES_CAR_PAG);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_COD_MOT);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_DES_COR_MOT);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_DES_LAR_MOT);
                        objTblMod.setValueAt(false, intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        //Inicializar la ventana de consulta de Cargos a Pagar.
                        vcoCarPag.limpiar();
                        //Asignar por defecto en cantidad: 1, o dejar la cantidad que estaba ingresada.
                        bgdCan=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN)==null?"1":(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString().equals("")?"1":objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString()));
                        objTblMod.setValueAt(bgdCan, intFil, INT_TBL_GRL_DAT_CAN);
                        //Genera Asiento de diario DxP
                        isExiAsiDiaPro(intFil);     //INT_TBL_GRL_DAT_CHK_ASI_PRO  
                        generarAsiDiaDxP();                        
                    }
                }
            });
            //Búsqueda por botón.
            objTblCelEdiButVcoPedEmb=new ZafTblCelEdiButVco(tblDat, vcoPedEmb, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_PED_EMB).setCellEditor(objTblCelEdiButVcoPedEmb);
            objTblCelEdiButVcoPedEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bgdCan = new BigDecimal("1");
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese el campo tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoPedEmb.setCancelarEdicion(true);
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtCodPrv.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un pedido embarcado ingrese el campo proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoPedEmb.setCancelarEdicion(true);
                        txtCodPrv.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoPedEmb.setCampoBusqueda(4);
                    vcoPedEmb.setCriterio1(11);
                    strSQL="";
                    if(txtCodTipDoc.getText().equals(strCodTipDocOpimpo)){ //opimpo
                        strSQL+=" WHERE b1.co_CtaPed IS NOT NULL";
                    }
                    strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc2, b1.co_CtaPed, b1.co_ctaPro";
                    strSQL+=" ORDER BY b1.tx_numDoc2 ";   
                    vcoPedEmb.setCondicionesSQL(strSQL);
                    //System.out.println("vcoPedEmb.condicion: "+vcoPedEmb.getCondicionesSQL());
                    //System.out.println("vcoPedEmb.getSentenciaSQL(): "+vcoPedEmb.getSentenciaSQL());         
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoPedEmb.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(3), intFil, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(4), intFil, INT_TBL_GRL_DAT_CODDOC_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(5), intFil, INT_TBL_GRL_DAT_NUM_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(6), intFil, INT_TBL_GRL_DAT_COD_CTA_PED_EMB);
                        objTblMod.setValueAt(vcoPedEmb.getValueAt(7), intFil, INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB);
                        //Limpiar Datos de la Tabla, cada vez que elige un nuevo pedido embarcado
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_COD_CAR_PAG);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_DES_CAR_PAG);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_COD_MOT);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_DES_COR_MOT);
                        objTblMod.setValueAt("", intFil, INT_TBL_GRL_DAT_DES_LAR_MOT);
                        objTblMod.setValueAt(false, intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        //Inicializar la ventana de consulta de Cargos a Pagar.
                        vcoCarPag.limpiar();
                        //Asignar por defecto en cantidad: 1, o dejar la cantidad que estaba ingresada.
                        bgdCan=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN)==null?"1":(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString().equals("")?"1":objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString()));
                        objTblMod.setValueAt(bgdCan, intFil, INT_TBL_GRL_DAT_CAN);
                        //Genera Asiento de diario DxP
                        isExiAsiDiaPro(intFil);     //INT_TBL_GRL_DAT_CHK_ASI_PRO                                        
                        generarAsiDiaDxP();                           
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Cargos a Pagar.*/">
            intColVen=new int[5];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=4;
            intColVen[3]=5;
            intColVen[4]=6;
            intColTbl=new int[5];
            intColTbl[0]=INT_TBL_GRL_DAT_COD_CAR_PAG;
            intColTbl[1]=INT_TBL_GRL_DAT_DES_CAR_PAG;
            intColTbl[2]=INT_TBL_GRL_DAT_COD_MOT;
            intColTbl[3]=INT_TBL_GRL_DAT_DES_COR_MOT;
            intColTbl[4]=INT_TBL_GRL_DAT_DES_LAR_MOT;
            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoCarPag=new ZafTblCelEdiTxtVco(tblDat, vcoCarPag, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_CAR_PAG).setCellEditor(objTblCelEdiTxtVcoCarPag);
            objTblCelEdiTxtVcoCarPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCodMot=-1;
                String strCodPedEmb="";
                BigDecimal bgdCan = new BigDecimal("1");
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strCodPedEmb=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CODDOC_PED_EMB));
                    if (strCodPedEmb.equals("")){
                        mostrarMsgInf("<HTML>El campo Pedido Embarcado no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese un pedido embarcado y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoCarPag.setCancelarEdicion(true);
                        objTblEdi.seleccionarCelda(intFil, INT_TBL_GRL_DAT_NUM_PED_EMB);  
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCarPag.setCampoBusqueda(1);
                    vcoCarPag.setCriterio1(11);
                    strSQL =" AND a2.co_doc=" +strCodPedEmb;
                    strSQL+=" GROUP BY a1.co_carPag, a1.tx_nom, a4.co_emp, a4.co_mot, a4.tx_desCor, a4.tx_desLar, a5.ne_tipNotPed";
                    strSQL+=" ORDER BY a1.ne_ubi";
                    vcoCarPag.setCondicionesSQL(strSQL);
                    //System.out.println("vcoCarPag.getSentenciaSQL(): "+vcoCarPag.getSentenciaSQL());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoCarPag.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoCarPag.getValueAt(1), intFil, INT_TBL_GRL_DAT_COD_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(2), intFil, INT_TBL_GRL_DAT_DES_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(4), intFil, INT_TBL_GRL_DAT_COD_MOT);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(5), intFil, INT_TBL_GRL_DAT_DES_COR_MOT);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(6), intFil, INT_TBL_GRL_DAT_DES_LAR_MOT);
                        
                        //objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                        intCodMot=Integer.parseInt(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT)==null?"0":(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString().equals("")?"0":objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString()));
                        if(intCodMot>0)
                            objTblMod.setChecked(new Boolean (true), intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        else
                            objTblMod.setChecked(new Boolean (false), intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        
                        //Asignar por defecto en cantidad: 1, o dejar la cantidad que estaba ingresada.
                        bgdCan=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN)==null?"1":(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString().equals("")?"1":objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString()));
                        objTblMod.setValueAt(bgdCan, intFil, INT_TBL_GRL_DAT_CAN);

                        generarForPag();
                      
                        //Inicializar la ventana de consulta de Motivos
                        //vcoMot.limpiar();  //No descomentar
                    }
                    generarObsDxP();
                }
            });
            //Búsqueda por botón.
            objTblCelEdiButVcoCarPag=new ZafTblCelEdiButVco(tblDat, vcoCarPag, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_CAR_PAG).setCellEditor(objTblCelEdiButVcoCarPag);
            objTblCelEdiButVcoCarPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCodMot=-1;
                String strCodPedEmb="";
                BigDecimal bgdCan = new BigDecimal("1");
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strCodPedEmb=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CODDOC_PED_EMB));
                    if (strCodPedEmb.equals("")){
                        mostrarMsgInf("<HTML>El campo Pedido Embarcado no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese un pedido embarcado y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoCarPag.setCancelarEdicion(true);
                        //txtCodPrv.requestFocus();
                        objTblEdi.seleccionarCelda(intFil, INT_TBL_GRL_DAT_NUM_PED_EMB);  
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCarPag.setCampoBusqueda(1);
                    vcoCarPag.setCriterio1(11);
                    strSQL =" AND a2.co_doc=" +strCodPedEmb;
                    strSQL+=" GROUP BY a1.co_carPag, a1.tx_nom, a4.co_emp, a4.co_mot, a4.tx_desCor, a4.tx_desLar, a5.ne_tipNotPed";
                    strSQL+=" ORDER BY a1.ne_ubi";
                    vcoCarPag.setCondicionesSQL(strSQL);
                    //System.out.println("vcoCarPag.getSentenciaSQL(): "+vcoCarPag.getSentenciaSQL());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoCarPag.isConsultaAceptada()) {
                        objTblMod.setValueAt(vcoCarPag.getValueAt(1), intFil, INT_TBL_GRL_DAT_COD_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(2), intFil, INT_TBL_GRL_DAT_DES_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(4), intFil, INT_TBL_GRL_DAT_COD_MOT);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(5), intFil, INT_TBL_GRL_DAT_DES_COR_MOT);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(6), intFil, INT_TBL_GRL_DAT_DES_LAR_MOT);
                        
                        //objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                        intCodMot=Integer.parseInt(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT)==null?"0":(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString().equals("")?"0":objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString()));
                        if(intCodMot>0)
                            objTblMod.setChecked(new Boolean (true), intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        else
                            objTblMod.setChecked(new Boolean (false), intFil, INT_TBL_GRL_DAT_CHK_MOT);

                        //Asignar por defecto en cantidad: 1, o dejar la cantidad que estaba ingresada.
                        bgdCan=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN)==null?"1":(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString().equals("")?"1":objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_CAN).toString()));
                        objTblMod.setValueAt(bgdCan, intFil, INT_TBL_GRL_DAT_CAN);
                        
                        generarForPag();
          
                        //Inicializar la ventana de consulta de Motivos
                        //vcoMot.limpiar();  //No descomentar
                    }
                    generarObsDxP();
                }
            });
            intColVen=null;
            intColTbl=null;
            //</editor-fold>
               
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Motivos.*/">
            intColVen=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColTbl=new int[3];
            intColTbl[0]=INT_TBL_GRL_DAT_COD_MOT;
            intColTbl[1]=INT_TBL_GRL_DAT_DES_COR_MOT;
            intColTbl[2]=INT_TBL_GRL_DAT_DES_LAR_MOT;
            //Búsqueda por Descripción corta.
            objTblCelEdiTxtVcoMot=new ZafTblCelEdiTxtVco(tblDat, vcoMot, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_DES_COR_MOT).setCellEditor(objTblCelEdiTxtVcoMot);
            objTblCelEdiTxtVcoMot.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCodMot=-1;
                String strCodCarPag="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strCodCarPag=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_COD_CAR_PAG));                    
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese un tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoMot.setCancelarEdicion(true);
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtCodPrv.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un motivo ingrese un proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoMot.setCancelarEdicion(true);
                        txtCodPrv.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMot.setCampoBusqueda(1);
                    vcoMot.setCriterio1(11);
                    strSQL =" AND a2.co_carPag=" +strCodCarPag;
                    strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a1.tx_desLar";
                    strSQL+=" ORDER BY tx_desCor, a1.co_mot";
                    vcoMot.setCondicionesSQL(strSQL);      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoMot.isConsultaAceptada()){
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                        objTblMod.setValueAt(vcoMot.getValueAt(1), intFil, INT_TBL_GRL_DAT_COD_MOT);
                        objTblMod.setValueAt(vcoMot.getValueAt(2), intFil, INT_TBL_GRL_DAT_DES_COR_MOT);
                        objTblMod.setValueAt(vcoMot.getValueAt(3), intFil, INT_TBL_GRL_DAT_DES_LAR_MOT);
                        intCodMot=Integer.parseInt(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT)==null?"0":(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString().equals("")?"0":objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString()));
                        if(intCodMot>0)
                            objTblMod.setChecked(new Boolean (true), intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        else
                            objTblMod.setChecked(new Boolean (false), intFil, INT_TBL_GRL_DAT_CHK_MOT);

                        generarForPag();
                    }
                }
            });
            //Búsqueda por botón.
            objTblCelEdiButVcoMot=new ZafTblCelEdiButVco(tblDat, vcoMot, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_GRL_DAT_BUT_MOT).setCellEditor(objTblCelEdiButVcoMot);
            objTblCelEdiButVcoMot.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCodMot=-1;
                String strCodCarPag="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strCodCarPag=objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_GRL_DAT_COD_CAR_PAG));          
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese un tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoMot.setCancelarEdicion(true);
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtCodPrv.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Proveedor no ha sido ingresado.<BR>Para agregar un motivo ingrese un proveedor y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoMot.setCancelarEdicion(true);
                        txtCodPrv.requestFocus();
                    }            
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMot.setCampoBusqueda(1);
                    vcoMot.setCriterio1(11);
                    strSQL =" AND a2.co_carPag=" +strCodCarPag;
                    strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a1.tx_desLar";
                    strSQL+=" ORDER BY tx_desCor, a1.co_mot";
                    vcoMot.setCondicionesSQL(strSQL);                
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoMot.isConsultaAceptada()){
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                        objTblMod.setValueAt(vcoMot.getValueAt(1), intFil, INT_TBL_GRL_DAT_COD_MOT);
                        objTblMod.setValueAt(vcoMot.getValueAt(2), intFil, INT_TBL_GRL_DAT_DES_COR_MOT);
                        objTblMod.setValueAt(vcoMot.getValueAt(3), intFil, INT_TBL_GRL_DAT_DES_LAR_MOT);

                        //VALIDAR CUANDO SE ELIMINA EL MOTIVO DE LA TABLA.               
                        intCodMot=Integer.parseInt(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT)==null?"0":(objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString().equals("")?"0":objTblMod.getValueAt(intFil,INT_TBL_GRL_DAT_COD_MOT).toString()));
                        if(intCodMot>0)
                            objTblMod.setChecked(new Boolean (true), intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        else
                            objTblMod.setChecked(new Boolean (false), intFil, INT_TBL_GRL_DAT_CHK_MOT);
                        
                        generarForPag();
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            //</editor-fold>

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
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblForPag()
    {
        boolean blnRes=true;
        try
        {
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

            objTblModForPag=new ZafTblMod();
            objTblModForPag.setHeader(vecCab);

            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            //objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_BAS_IMP, objTblModForPag.INT_COL_DBL, new Integer(0), null);
            //objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_VAL_RET, objTblModForPag.INT_COL_DBL, new Integer(0), null);
            //objTblModForPag.setColumnDataType(INT_TBL_FOR_PAG_DAT_POR_RET, objTblModForPag.INT_COL_DBL, new Integer(0), null);

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

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET, tblForPag);
            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_APL, tblForPag);

            //objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_EST_AUT_PAG, tblForPag);
            //objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_COD_CTA_PAG, tblForPag);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setResizable(false);

            //tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_EST_AUT_PAG).setResizable(false);
            //tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_CTA_PAG).setResizable(false);

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

            //PARA CASOS DE COMPRAS DE ACTIVO FIJO DESACTIVAR ESTE GRUPO, tambien se debe bloquear el listener de la columna de BASE IMPONIBLE
            //vecAux.add("" + INT_TBL_FOR_PAG_DAT_VAL_RET);
            //vecAux.add("" + INT_TBL_FOR_PAG_DAT_DIA_CRE);
            //vecAux.add("" + INT_TBL_FOR_PAG_DAT_FEC_VEN);
            //vecAux.add("" + INT_TBL_FOR_PAG_DAT_POR_RET);

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
                        objTblCelEdiTxtVcoRet.setCancelarEdicion(false);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    generarForPag();
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
                        objTblCelEdiButvcoRet.setCancelarEdicion(false);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButvcoRet.isConsultaAceptada()){
                        generarForPag();
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
                    calcularValRet(intFil);
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
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblFacPrv()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(11);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FAC_PRV_DAT_LIN,"");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_FAC,"Núm.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_FEC_FAC,"Fec.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_SER,"Núm.Ser.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_NUM_AUT,"Núm.Aut.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_FEC_CAD,"Fec.Cad.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_FAC,"Val.Fac.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA,"Val.Sin.Iva.");            
            vecCab.add(INT_TBL_FAC_PRV_DAT_SUB,"Sub.Fac.Prv.");
            vecCab.add(INT_TBL_FAC_PRV_DAT_IVA,"Iva.Fac.Prv");
            vecCab.add(INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG,"Val.Pen.Pag.");

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
            
            //Mostrar Borrar Contenido
            objTblPopMnuFacPrv.setBorrarContenidoEnabled(true);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFacPrv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFacPrv.getColumnModel();
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_FAC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setPreferredWidth(65);            
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_SUB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_IVA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG).setPreferredWidth(60);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFacPrv.addSystemHiddenColumn(INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG, tblFacPrv);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFacPrv.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFacPrv=new ZafMouMotAdaFacPrv();
            tblFacPrv.getTableHeader().addMouseMotionListener(objMouMotAdaFacPrv);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer columnas editables.
            //DBN: Ya no se debe permitir la edicion en el JTable de Factura de Proveedor.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_FAC);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_FAC);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_SER);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_AUT);
            vecAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_CAD);
            //vecAux.add("" + INT_TBL_FAC_PRV_DAT_VAL_FAC);      //En la versión v0.1.6 se bloquea edición de esta columna.
            //vecAux.add("" + INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA);  //En la versión v0.1.6 se bloquea edición de esta columna.        
            //vecAux.add("" + INT_TBL_FAC_PRV_DAT_SUB);          //En la versión v0.1.6 se bloquea edición de esta columna.
            //vecAux.add("" + INT_TBL_FAC_PRV_DAT_IVA);          //En la versión v0.1.6 se bloquea edición de esta columna.
            objTblModFacPrv.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_FAC);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_FAC);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_VAL_FAC);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_SER);
            arlAux.add("" + INT_TBL_FAC_PRV_DAT_NUM_AUT);
            //arlAux.add("" + INT_TBL_FAC_PRV_DAT_FEC_CAD);
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
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_SER).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_NUM_AUT).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_FEC_CAD).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellEditor(objTblCelEdiTxtFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellEditor(objTblCelEdiTxtFacPrv);            
            objTblCelEdiTxtFacPrv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strFecFacPrv="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strFecFacPrv=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_FAC)==null?"":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_FEC_FAC).toString();
                    if(!strFecFacPrv.equals("")){
                        if(validarFecVen(intFil)){
                            dtpFecDoc.setText(strFecFacPrv);
                        }
                        generarForPag();
                    }
                }
            });
            //objTblCelEdiTxtFacPrv=null;

            objTblCelEdiTxtValFac=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_FAC).setCellEditor(objTblCelEdiTxtValFac);
            objTblCelEdiTxtValFac.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdgTotFacPrv=new BigDecimal("0");
                BigDecimal bgdSubFacPrv=new BigDecimal("0");
                BigDecimal bdgIvaFacPrv=new BigDecimal("0");
                //BigDecimal bdgFacPrvPorIva=new BigDecimal("1.12");
                BigDecimal bdgFacPrvPorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );

                BigDecimal bdeValFacPrvBef=new BigDecimal("0");
                Object objValIvaPrvBef=new BigDecimal("0");
                Object objValSubPrvBef=new BigDecimal("0");
                BigDecimal bdgFacPrvSinIva=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                    bdeValFacPrvBef=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    objValSubPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_SUB);
                    objValIvaPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_IVA);
                    if(validarFecVen(intFil)){
                        objTblCelEdiTxtValFac.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxtValFac.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdgTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    cargarValSinIvaFacPrv();
                    bdgFacPrvSinIva=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString()));

                    if(bdgTotFacPrv.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                        objTblModFacPrv.removeEmptyRows();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        if(sumFacPrv_equals_totDct()){
                            //calcular iva
                            BigDecimal bdeFacIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                            
                            bdgIvaFacPrv=((bdgTotFacPrv.subtract(bdgFacPrvSinIva)).divide(bdgFacPrvPorIva, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeFacIva);
                            bdgIvaFacPrv=objUti.redondearBigDecimal(bdgIvaFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdgIvaFacPrv, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            //calcular subtotal --)
                            bgdSubFacPrv=((bdgTotFacPrv.subtract(bdgFacPrvSinIva)).divide(bdgFacPrvPorIva, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).add(bdgFacPrvSinIva);
                            bgdSubFacPrv=objUti.redondearBigDecimal(bgdSubFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bgdSubFacPrv, intFil, INT_TBL_FAC_PRV_DAT_SUB);

                            generarForPag();
                        }
                        else{
                            mostrarMsgInf("<HTML>La suma de las facturas del proveedor exceden el valor del documento.<BR>Verifique y vuelva a intentarlo</HTML>");
                            //objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG);
                            objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                            objTblModFacPrv.setValueAt(objValSubPrvBef, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            objTblModFacPrv.setValueAt(objValIvaPrvBef, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                        }
                    }
                }
            });

            objTblCelEdiTxtValFacSinIva=new ZafTblCelEdiTxt(tblFacPrv);
            tcmAux.getColumn(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).setCellEditor(objTblCelEdiTxtValFacSinIva);
            objTblCelEdiTxtValFacSinIva.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdgTotFacPrv=new BigDecimal("0");
                BigDecimal bdgFacPrvSinIva=new BigDecimal("0");
                BigDecimal bgdSubFacPrv=new BigDecimal("0");
                BigDecimal bdgIvaFacPrv=new BigDecimal("0");
                //BigDecimal bdgFacPrvPorIva=new BigDecimal("1.12");
                BigDecimal bdgFacPrvPorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                
                BigDecimal bdeValFacPrvBef=new BigDecimal("0");
                Object objValIvaPrvBef=new BigDecimal("0");
                Object objValSubPrvBef=new BigDecimal("0");
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFacPrv.getSelectedRow();
                    bdeValFacPrvBef=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    objValSubPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_SUB);
                    objValIvaPrvBef=objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_IVA);
                    if(validarFecVen(intFil)){
                        objTblCelEdiTxtValFacSinIva.setCancelarEdicion(false);
                    }
                    else
                        objTblCelEdiTxtValFacSinIva.setCancelarEdicion(true);                   
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdgTotFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                    bdgFacPrvSinIva=new BigDecimal(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA)==null?"0":(objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA).toString()));

                    if(bdgTotFacPrv.compareTo(new BigDecimal(BigInteger.ZERO))>0){
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                        objTblModFacPrv.removeEmptyRows();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        if(sumFacPrv_equals_totDct()){
                            //calcular iva
                            BigDecimal bdeFacIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                            bdgIvaFacPrv=((bdgTotFacPrv.subtract(bdgFacPrvSinIva)).divide(bdgFacPrvPorIva, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bdeFacIva);
                            bdgIvaFacPrv=objUti.redondearBigDecimal(bdgIvaFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bdgIvaFacPrv, intFil, INT_TBL_FAC_PRV_DAT_IVA);
                            //calcular subtotal --)
                            bgdSubFacPrv=((bdgTotFacPrv.subtract(bdgFacPrvSinIva)).divide(bdgFacPrvPorIva, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).add(bdgFacPrvSinIva);
                            bgdSubFacPrv=objUti.redondearBigDecimal(bgdSubFacPrv, objParSis.getDecimalesMostrar());
                            objTblModFacPrv.setValueAt(bgdSubFacPrv, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                         }
                        else{
                            mostrarMsgInf("<HTML>La suma de las facturas del proveedor exceden el valor del documento.<BR>Verifique y vuelva a intentarlo</HTML>");
                            objTblModFacPrv.setValueAt(bdeValFacPrvBef, intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                            objTblModFacPrv.setValueAt(objValSubPrvBef, intFil, INT_TBL_FAC_PRV_DAT_SUB);
                            objTblModFacPrv.setValueAt(objValIvaPrvBef, intFil, INT_TBL_FAC_PRV_DAT_IVA);
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
                BigDecimal bdgSubTot= BigDecimal.ZERO;
                BigDecimal bdgIva= BigDecimal.ZERO;
                BigDecimal bdgTot= BigDecimal.ZERO;
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
                        //Se asigna los valores del DxP cuando se digite el número de factura.  
                        cargarValFacFacPrv();
                    }
                    else{
                        //Cuando no se ingresa factura, se limpian datos de valores de la factura.
                        objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                        objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA);
                        objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_SUB);
                        objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_IVA);
                        objTblModFacPrv.setValueAt("", intFil, INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG);
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
                case INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB:
                    strMsg="Código del tipo de documento del Pedido Embarcado";
                    break;                    
                case INT_TBL_GRL_DAT_CODDOC_PED_EMB:
                    strMsg="Código del Pedido Embarcado";
                    break;
                case INT_TBL_GRL_DAT_NUM_PED_EMB:
                    strMsg="Pedido Embarcado";
                    break;
                case INT_TBL_GRL_DAT_COD_CTA_PED_EMB:
                    strMsg="Código de cuenta de pedido embarcado";
                    break;                    
                case INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB:
                    strMsg="Código de cuenta de provisión del pedido embarcado";
                    break;       
                case INT_TBL_GRL_DAT_CHK_ASI_PRO:
                    strMsg="Con Asiento de Diario de Provisiones";
                    break;                          
                case INT_TBL_GRL_DAT_BUT_PED_EMB:
                    strMsg="Búsqueda del pedido embarcado";
                    break;                 
                case INT_TBL_GRL_DAT_COD_CAR_PAG:
                    strMsg="Código del cargo a pagar";
                    break;
                case INT_TBL_GRL_DAT_DES_CAR_PAG:
                    strMsg="Descripción del cargo a pagar";
                    break;  
                case INT_TBL_GRL_DAT_BUT_CAR_PAG:
                    strMsg="Búsqueda del cargo a pagar";  
                    break; 
                case INT_TBL_GRL_DAT_COD_MOT:
                    strMsg="Código del Motivo";
                    break;
                case INT_TBL_GRL_DAT_DES_COR_MOT:
                    strMsg="Motivo";
                    break;                     
                case INT_TBL_GRL_DAT_DES_LAR_MOT:
                    strMsg="Descripción del motivo";
                    break;  
                case INT_TBL_GRL_DAT_CHK_MOT:
                    strMsg="Con Motivo";
                    break;                       
                case INT_TBL_GRL_DAT_BUT_MOT:
                    strMsg="Búsqueda del motivo";
                    break;                       
                case INT_TBL_GRL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_GRL_DAT_PRE:
                    strMsg="Precio unitario";
                    break;
                    case INT_TBL_GRL_DAT_VAL_SUBTOT:
                    strMsg="Subtotal";
                    break;
                case INT_TBL_GRL_DAT_CHK_IVA:
                    strMsg="Lleva IVA";
                    break;
                    case INT_TBL_GRL_DAT_VAL_IVA:
                    strMsg="Valor de IVA";
                    break;
                case INT_TBL_GRL_DAT_VAL_TOT:
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
                case INT_TBL_FAC_PRV_DAT_NUM_SER:
                    strMsg="Número de serie, formato 999-999 (números separados con guión)";
                    break;
                case INT_TBL_FAC_PRV_DAT_NUM_AUT:
                    strMsg="Número de autorización del SRI";
                    break;
                case INT_TBL_FAC_PRV_DAT_FEC_CAD:
                    strMsg="Fecha de caducidad(dd/MM/yyyy)";
                    break;
                case INT_TBL_FAC_PRV_DAT_VAL_FAC:
                    strMsg="Valor de la factura";
                    break;
                case INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA:
                    strMsg="Valor de la factura sin iva";
                    break;                    
                default:
                    strMsg="";
                    break;
            }
            tblFacPrv.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
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
       
    private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try
        {
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
                        objTblModForPag.removeAllRows();
                        objTblModFacPrv.removeAllRows();
                        txtCodForPag.setText("");
                        txtNomForPag.setText("");
                        txtNumForPag.setText("");
                        txtTipForPag.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
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
                        objTblModForPag.removeAllRows();
                        objTblModFacPrv.removeAllRows();
                        txtCodForPag.setText("");
                        txtNomForPag.setText("");
                        txtNumForPag.setText("");
                        txtTipForPag.setText("");
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
                            objTblModForPag.removeAllRows();
                            objTblModFacPrv.removeAllRows();
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
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
                        objTblModForPag.removeAllRows();
                        objTblModFacPrv.removeAllRows();
                        txtCodForPag.setText("");
                        txtNomForPag.setText("");
                        txtNumForPag.setText("");
                        txtTipForPag.setText("");
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
                            objTblModForPag.removeAllRows();
                            objTblModFacPrv.removeAllRows();
                            txtCodForPag.setText("");
                            txtNomForPag.setText("");
                            txtNumForPag.setText("");
                            txtTipForPag.setText("");
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
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
                        txtUsrCom.setText(vcoCom.getValueAt(2));
                        txtNomCom.setText(vcoCom.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoCom.buscar("a1.tx_usr", txtUsrCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtUsrCom.setText(vcoCom.getValueAt(2));
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
                            txtUsrCom.setText(vcoCom.getValueAt(2));
                            txtNomCom.setText(vcoCom.getValueAt(3));
                        }
                        else
                        {
                            txtUsrCom.setText(strAliCom);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCom.buscar("a1.tx_nom", txtNomCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtUsrCom.setText(vcoCom.getValueAt(2));
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
                            txtUsrCom.setText(vcoCom.getValueAt(2));
                            txtNomCom.setText(vcoCom.getValueAt(3));
                        }
                        else
                        {
                            txtNomCom.setText(strNomCom);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCom.buscar("a1.co_usr", txtCodCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtUsrCom.setText(vcoCom.getValueAt(2));
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
                            txtUsrCom.setText(vcoCom.getValueAt(2));
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningún problema.
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
                //strSQL+=" INNER JOIN tbm_cabMovInv AS a2";
                //strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli AND a1.co_reg=a2.co_ben";
                //strSQL+=" INNER JOIN tbm_pagMovInv AS a3";
                //strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc ";
                //strSQL+=" AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
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
                    case 1: //Búsqueda directa por "Número de cuenta".
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
                    case 2: //Búsqueda directa por "Descripción larga".
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
     * Esta función muestra el beneficiario predeterminado del cheque
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
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

    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
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
                        }
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
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
                        }                                                
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
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
                            }
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
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
                        }
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
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
                            }
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }                            

                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
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
                        }
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
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
                            }
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Formas de Pagos".
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
                        generarForPag();
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        objTblModForPag.removeAllRows();
                        generarForPag();
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
                            generarForPag();
                        }
                        else  {
                            txtCodForPag.setText(strCodForPag);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoForPag.buscar("a1.tx_desForPag", txtNomForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        objTblModForPag.removeAllRows();
                        generarForPag();
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
                            generarForPag();
                        }
                        else {
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Retenciones".
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
            strSQL+=" SELECT a1.co_tipRet, a1.tx_desCor, a1.tx_desLar, a1.nd_porRet";
            strSQL+="      , CASE WHEN a1.tx_aplRet='S' THEN 'Subtotal'";
            strSQL+=" 	          WHEN a1.tx_aplRet='I' THEN 'Iva'";
            strSQL+=" 	          WHEN a1.tx_aplRet='O' THEN 'Otros' END AS tx_aplRet";
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
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Pedido Embarcado".
     */
    private boolean configurarVenConPedEmb()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.tx_numDoc2");
            arlCam.add("a1.co_ctaPed");
            arlCam.add("a1.co_ctaPro");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Ped.Emb.");
            arlAli.add("Cód.Cta.Ped.");
            arlAli.add("Cód.Cta.Pro.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("50");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc2, b1.co_CtaPed, b1.co_ctaPro";
            strSQL+=" FROM(";
            strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_numDoc2, a2.co_CtaPed, a3.co_ctaPro";		
            strSQL+="    FROM tbm_cabPedEmbImp AS a1";
            strSQL+="    LEFT OUTER JOIN (SELECT co_emp, co_Cta as co_CtaPed FROM tbm_plaCta as a WHERE a.st_Reg IN ('A') ) AS a2";    
            strSQL+="    ON a1.co_imp=a2.co_emp AND a1.co_ctaAct=a2.co_CtaPed"; 
            strSQL+="    LEFT OUTER JOIN (SELECT co_emp, co_Cta as co_ctaPro FROM tbm_plaCta as a WHERE a.st_Reg IN ('A')) AS a3 "; 
            strSQL+="    ON a1.co_imp=a3.co_emp AND a1.co_ctaPro=a3.co_ctaPro ";
            strSQL+="    WHERE a1.st_reg NOT IN('E','I') ";
            strSQL+="    AND a1.st_ciePagLoc IN ('N')";  //Muestre pedidos abiertos, es decir, que no hayan sido cerrados para generar pagos.
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                strSQL+="    AND a1.co_imp="+objParSis.getCodigoEmpresa();
            strSQL+=" ) AS b1";            

            //System.out.println("configurarVenConPedEmb: "+strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[6];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            intColOcu[4]=6;
            intColOcu[5]=7;
            
            if(objParSis.getCodigoUsuario()==1){
                vcoPedEmb=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedido Embarcado", strSQL, arlCam, arlAli, arlAncCol);
            }
            else {
                vcoPedEmb=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedido Embarcado", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            }
            
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPedEmb.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPedEmb.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoPedEmb.setCampoBusqueda(4);
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
     * mostrar los "Cargos a Pagar".
     */
    private boolean configurarVenConCarPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_carPag");
            arlCam.add("a1.tx_nomCar");
            arlCam.add("a1.co_empMot");
            arlCam.add("a1.co_mot");
            arlCam.add("a1.tx_DesCorMot");
            arlCam.add("a1.tx_desLarMot");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Car.");
            arlAli.add("Cargo");
            arlAli.add("Cód.Emp.Mot.");
            arlAli.add("Cód.Mot.");
            arlAli.add("Mot.Doc.");
            arlAli.add("Motivo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("120");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_carPag, a1.tx_nom as tx_nomCar, a4.co_emp as co_empMot, a4.co_mot, a4.tx_desCor as tx_DesCorMot, a4.tx_desLar as tx_desLarMot, a5.ne_tipNotPed";
            strSQL+=" FROM tbm_carPagImp AS a1";
            strSQL+=" INNER JOIN tbm_carPagPedEmbImp AS a2 ON a1.co_carPag=a2.co_CarPag";
            strSQL+=" INNER JOIN tbm_cabPedEmbImp as a5 ON a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc AND a2.co_doc=a5.co_doc";
            strSQL+=" INNER JOIN tbr_carPagImpMotDoc AS a3 ON a1.co_carPag=a3.co_carPag";
            strSQL+=" INNER JOIN tbm_motDoc AS a4 ON a3.co_emp=a4.co_emp AND a3.co_mot=a4.co_mot";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" AND a1.tx_tipCarPag IN ('G', 'V', 'F')"; //Mostrar sólo cargos tipo G=Gastos Y Valor de la factura, F=Flete.
            /*Alfredo Paulson - 15Feb2018: Solo poder generar pagos y provisión de fletes por FOB (ne_tipNotPed: 1 y 3). CFR no generar DxP Fletes (ne_tipNotPed: 2 y 4)*/
            strSQL+=" AND (CASE WHEN a1.tx_tipCarPag IN ('F') AND a5.ne_tipNotPed IN (2,4) THEN FALSE ELSE TRUE END)"; 
            //Mostrar solo cargos que generen provisión. Y Valor de la factura que no genera provisión pero es utilizado para compras locales.
            strSQL+=" AND (CASE WHEN a1.tx_tipCarPag IN ('V') OR (CASE WHEN a1.st_genPro IS NOT NULL THEN a1.st_genPro END) IN ('S') THEN TRUE ELSE FALSE END) "; 
            strSQL+=" AND a4.co_emp="+objParSis.getCodigoEmpresa();
            
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=3; //co_empMot
            intColOcu[1]=4; //co_mot
            
            vcoCarPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Cargos a Pagar", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCarPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCarPag.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            vcoCarPag.setCampoBusqueda(1);
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
     * mostrar los "Motivos".
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
            strSQL+=" SELECT a1.co_mot, a1.tx_desCor, a1.tx_desLar";
            strSQL+=" FROM tbm_motDoc AS a1";
            strSQL+=" INNER JOIN tbr_carPagImpMotDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_mot ";
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
        txtRucPrv = new javax.swing.JTextField();
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
        txtCodCom = new javax.swing.JTextField();
        txtUsrCom = new javax.swing.JTextField();
        txtNomCom = new javax.swing.JTextField();
        butCom = new javax.swing.JButton();
        lblGasPro = new javax.swing.JLabel();
        txtGasPro = new javax.swing.JTextField();
        chkEmiChqAntRecFacPrv = new javax.swing.JCheckBox();
        chkNecMarLis = new javax.swing.JCheckBox();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenSur = new javax.swing.JPanel();
        panGenLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panTot = new javax.swing.JPanel();
        lblSub = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        panForPag = new javax.swing.JPanel();
        panFilForPag = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtNomForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        txtNumForPag = new javax.swing.JTextField();
        txtTipForPag = new javax.swing.JTextField();
        panDatForPag = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblForPag = new javax.swing.JTable();
        panPieForPag = new javax.swing.JPanel();
        lblDif = new javax.swing.JLabel();
        txtForPagDif = new javax.swing.JTextField();
        panFac = new javax.swing.JPanel();
        panDatFacPrv = new javax.swing.JPanel();
        spnFacPrv = new javax.swing.JScrollPane();
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
        lblTit.setText("Título de la ventana");
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

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(98, 4, 56, 20);

        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
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
        lblPrv.setBounds(0, 24, 100, 20);

        txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusLost(evt);
            }
        });
        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodPrv);
        txtCodPrv.setBounds(98, 24, 38, 20);
        panGenCab.add(txtRucPrv);
        txtRucPrv.setBounds(136, 24, 102, 20);

        txtDesLarPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusLost(evt);
            }
        });
        txtDesLarPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarPrvActionPerformed(evt);
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
        lblNumDoc.setBounds(444, 24, 140, 20);

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
        lblBen.setBounds(0, 44, 100, 20);

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
        lblCom.setBounds(0, 64, 80, 20);
        panGenCab.add(txtCodCom);
        txtCodCom.setBounds(68, 64, 30, 20);

        txtUsrCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsrComActionPerformed(evt);
            }
        });
        txtUsrCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsrComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsrComFocusLost(evt);
            }
        });
        panGenCab.add(txtUsrCom);
        txtUsrCom.setBounds(98, 64, 56, 20);

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

        lblGasPro.setText("Total Gasto Provisión:");
        lblGasPro.setToolTipText("Número alterno 2");
        panGenCab.add(lblGasPro);
        lblGasPro.setBounds(444, 64, 130, 20);
        lblGasPro.getAccessibleContext().setAccessibleName("");
        lblGasPro.getAccessibleContext().setAccessibleDescription("");

        panGenCab.add(txtGasPro);
        txtGasPro.setBounds(572, 64, 112, 20);

        chkEmiChqAntRecFacPrv.setText("Emitir cheque antes de recibir la factura del proveedor");
        panGenCab.add(chkEmiChqAntRecFacPrv);
        chkEmiChqAntRecFacPrv.setBounds(0, 90, 400, 16);

        chkNecMarLis.setText("Documento listo para ser autorizado");
        panGenCab.add(chkNecMarLis);
        chkNecMarLis.setBounds(0, 90, 300, 16);

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

        panGenSur.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenSur.setLayout(new java.awt.BorderLayout());

        panGenLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        panGenLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        panGenLbl.add(lblObs2);

        panGenSur.add(panGenLbl, java.awt.BorderLayout.WEST);

        panGenObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenObs.add(spnObs2);

        panGenSur.add(panGenObs, java.awt.BorderLayout.CENTER);

        panTot.setPreferredSize(new java.awt.Dimension(148, 100));
        panTot.setLayout(null);

        lblSub.setText("Subtotal:");
        panTot.add(lblSub);
        lblSub.setBounds(2, 4, 50, 14);
        panTot.add(txtSub);
        txtSub.setBounds(50, 0, 96, 20);

        lblIva.setText("IVA:");
        panTot.add(lblIva);
        lblIva.setBounds(2, 24, 50, 14);
        panTot.add(txtIva);
        txtIva.setBounds(50, 20, 96, 20);

        lblTot.setText("Total:");
        panTot.add(lblTot);
        lblTot.setBounds(2, 44, 50, 14);
        panTot.add(txtTot);
        txtTot.setBounds(50, 40, 96, 20);

        panGenSur.add(panTot, java.awt.BorderLayout.EAST);

        panGen.add(panGenSur, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

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
        spnForPag.setViewportView(tblForPag);

        panDatForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

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
        spnFacPrv.setViewportView(tblFacPrv);

        panDatFacPrv.add(spnFacPrv, java.awt.BorderLayout.CENTER);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtNomComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomComActionPerformed
        txtNomCom.transferFocus();
    }//GEN-LAST:event_txtNomComActionPerformed

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        strCodForPag=txtCodForPag.getText();
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
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

    private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
        strNomForPag=txtNomForPag.getText();
        txtNomForPag.selectAll();
    }//GEN-LAST:event_txtNomForPagFocusGained

    private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
        txtNomForPag.transferFocus();
    }//GEN-LAST:event_txtNomForPagActionPerformed

    private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
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

    private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
        strCodForPag=txtCodForPag.getText();
        txtCodForPag.selectAll();
    }//GEN-LAST:event_txtCodForPagFocusGained

    private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
        txtCodForPag.transferFocus();
    }//GEN-LAST:event_txtCodForPagActionPerformed

    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        mostrarVenConCom(0);
    }//GEN-LAST:event_butComActionPerformed

    private void txtNomComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusLost
        if (txtNomCom.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomCom.getText().equalsIgnoreCase(strNomCom))
            {
                if (txtNomCom.getText().equals(""))
                {
                    txtCodCom.setText("");
                    txtUsrCom.setText("");
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

    private void txtNomComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusGained
        strNomCom=txtNomCom.getText();
        txtNomCom.selectAll();
    }//GEN-LAST:event_txtNomComFocusGained

    private void txtUsrComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrComFocusLost
        if (txtUsrCom.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtUsrCom.getText().equalsIgnoreCase(strAliCom))
            {
                if (txtUsrCom.getText().equals(""))
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
            txtUsrCom.setText(strAliCom);
        }
    }//GEN-LAST:event_txtUsrComFocusLost

    private void txtUsrComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsrComFocusGained
        strAliCom=txtUsrCom.getText();
        txtUsrCom.selectAll();
    }//GEN-LAST:event_txtUsrComFocusGained

    private void txtUsrComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsrComActionPerformed
        txtUsrCom.transferFocus();
    }//GEN-LAST:event_txtUsrComActionPerformed

    private void butBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBenActionPerformed
        mostrarVenConBen(0);
    }//GEN-LAST:event_butBenActionPerformed

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

    private void txtNomBenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBenFocusGained
        strNomBen=txtNomBen.getText();
        txtNomBen.selectAll();
    }//GEN-LAST:event_txtNomBenFocusGained

    private void txtNomBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBenActionPerformed
        txtNomBen.transferFocus();
    }//GEN-LAST:event_txtNomBenActionPerformed

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

    private void txtCodBenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBenFocusGained
        strCodBen=txtCodBen.getText();
        txtCodBen.selectAll();
    }//GEN-LAST:event_txtCodBenFocusGained

    private void txtCodBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBenActionPerformed
        txtCodBen.transferFocus();
    }//GEN-LAST:event_txtCodBenActionPerformed

    private void butNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNumDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butNumDocActionPerformed

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
        actualizarGlo();
    }//GEN-LAST:event_txtNumDocFocusLost

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        strCodPrv=txtCodPrv.getText();
        configurarVenConPrv();
        mostrarVenConPrv(0);
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
        txtDesLarPrv.transferFocus();
    }//GEN-LAST:event_txtDesLarPrvActionPerformed

    private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)) {
            if (txtDesLarPrv.getText().equals("")) {
                txtCodPrv.setText("");
                txtRucPrv.setText("");
                txtDesLarPrv.setText("");
                txtCodBen.setText("");
                txtNomBen.setText("");
                objTblMod.removeAllRows();
                objTblModForPag.removeAllRows();
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
            } else {
                configurarVenConPrv();
                mostrarVenConPrv(2);
            }
        }
        else
        txtDesLarPrv.setText(strDesLarPrv);
    }//GEN-LAST:event_txtDesLarPrvFocusLost

    private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
        strDesLarPrv=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
    }//GEN-LAST:event_txtDesLarPrvFocusGained

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
    }//GEN-LAST:event_txtCodPrvActionPerformed

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)) {
            if (txtCodPrv.getText().equals("")) {
                txtCodPrv.setText("");
                txtRucPrv.setText("");
                txtDesLarPrv.setText("");
                txtCodBen.setText("");
                txtNomBen.setText("");
                objTblMod.removeAllRows();
                objTblModForPag.removeAllRows();
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
            } else {
                configurarVenConPrv();
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

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        configurarVenConTipDoc();
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

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
                    configurarVenConTipDoc();
                    mostrarVenConTipDoc(2);
                }
            }
            else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

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
                    configurarVenConTipDoc();
                    mostrarVenConTipDoc(1);
                }
            }
            else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

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
    private javax.swing.JCheckBox chkEmiChqAntRecFacPrv;
    private javax.swing.JCheckBox chkNecMarLis;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblDifFacPrv;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblGasPro;
    private javax.swing.JLabel lblIva;
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
    private javax.swing.JPanel panGenLbl;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenSur;
    private javax.swing.JPanel panPieFacPrv;
    private javax.swing.JPanel panPieForPag;
    private javax.swing.JPanel panTot;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFacPrv;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFacPrv;
    private javax.swing.JTable tblForPag;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
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
    private javax.swing.JTextField txtGasPro;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomBen;
    private javax.swing.JTextField txtNomCom;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumForPag;
    private javax.swing.JTextField txtRucPrv;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTipForPag;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtUsrCom;
    // End of variables declaration//GEN-END:variables

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
        txtGasPro.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            if(objTooBar.insertar()){  
                                if(objTooBar.afterInsertar()){  
                                    blnRes=true;
                                }
                            }                      
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            if(objTooBar.modificar()){  
                                if(objTooBar.afterModificar()){  
                                    blnRes=true;
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
     * La idea principal de ésta función es regenerar el asiento de diario. 
     * Pero, puede darse el caso en el que el asiento de diario fue modificado manualmente
     * por el usuario. En  cuyo caso, primero se pregunta si desea regenerar el asiento
     * de diario, no regenerarlo o cancelar.
     * @return true: Si se decidió regenerar/no regenerar el asiento de diario.
     * <BR>false: Si se canceló la regeneración del asiento de diario.
     * Nota.- Como se puede apreciar la función retorna "false" sólo cuando se dió click en el botón "Cancelar".
     */
    private boolean mostrarMsgRegenerarAsiDia()
    {
        boolean blnRes=true;
        strAux="¿Desea regenerar el asiento de diario?\n";
        strAux+="El asiento de diario ha sido modificado manualmente.";
        strAux+="\nSi desea que el sistema regenere el asiento de diario de click en SI.";
        strAux+="\nSi desea grabar el asiento de diario tal como está de click en NO.";
        strAux+="\nSi desea cancelar ésta operación de click en CANCELAR.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                objAsiDia.setGeneracionDiario((byte)0);
                generarAsiDiaDxP();
                break;
            case 1: //NO_OPTION
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
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
            strNumSerFacPrv="";
            strNumAutSriFacPrv="";
            strNumFecCadFacPrv="";
            dtpFecDoc.setText("");
            txtGasPro.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            objTblMod.removeAllRows();
            objTblModForPag.removeAllRows();
            objTblModFacPrv.removeAllRows();
            txtCodForPag.setText("");
            txtNomForPag.setText("");
            txtNumForPag.setText("");
            txtTipForPag.setText("");
            objAsiDia.inicializar();
            txaObs1.setText("");
            txaObs2.setText("");
            txtCodBen.setText("");
            txtNomBen.setText("");
            txtUsrCom.setText("");
            txtNomCom.setText("");
            txtSub.setText("");
            txtIva.setText("");
            txtTot.setText("");
            txtCodCom.setText("");
            chkEmiChqAntRecFacPrv.setSelected(false);
            chkNecMarLis.setSelected(false);
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }  
    
    /**
     * Función que elimina ceros a la izquierda dentro de una cadena de caracteres.
     * @param str
     * @return 
     */
    private String eliminarCeros(String str) 
    {
        if (str.length() > 0) {
            if (str.charAt(0) == '0') {
                return eliminarCeros(str.substring(1));
            }
        }
        return str;
    }

    /**
     * Función que agrega ceros a la izquierda dentro de una cadena de caracteres.
     * @param str
     * @return 
     */
    private String agregarCeros(String str) 
    {
        if (str.length() < 9) {
            str.format("%09s", str);
        }
        return str;
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
   
        public void clickInicio()
        {
            try{
                if(arlDatConDxP.size()>0){
                    if(intIndiceDxP>0){
                        if (blnHayCam || objAsiDia.isDiarioModificado()) {
                            if (isRegPro()) {
                                intIndiceDxP=0;
                                cargarReg();
                                inactivarCamModPerUsr();
                            }
                        }
                        else {
                            intIndiceDxP=0;
                            cargarReg();
                            inactivarCamModPerUsr();
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
                            generarForPag();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickAnterior()
        {
            try{
                if(arlDatConDxP.size()>0){
                    if(intIndiceDxP>0){
                        if (blnHayCam || objAsiDia.isDiarioModificado()){
                            if (isRegPro()) {
                                intIndiceDxP--;
                                cargarReg();
                                inactivarCamModPerUsr();
                            }
                        }
                        else {
                            intIndiceDxP--;
                            cargarReg();
                            inactivarCamModPerUsr();
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
                            generarForPag();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }
        
        public void clickSiguiente()
        {
            try{
                if(arlDatConDxP.size()>0){
                    if(intIndiceDxP < arlDatConDxP.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado()){
                            if (isRegPro()) {
                                intIndiceDxP++;
                                cargarReg();
                                inactivarCamModPerUsr();
                            }
                        }
                        else {
                            intIndiceDxP++;
                            cargarReg();
                            inactivarCamModPerUsr();
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
                            generarForPag();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }  
        }        
               
        public void clickFin()
        {
            try{
                if(arlDatConDxP.size()>0){
                    if(intIndiceDxP<arlDatConDxP.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado()) {
                            if (isRegPro()) {
                                intIndiceDxP=arlDatConDxP.size()-1;
                                cargarReg();
                                inactivarCamModPerUsr();
                            }
                        }
                        else {
                            intIndiceDxP=arlDatConDxP.size()-1;
                            cargarReg();
                            inactivarCamModPerUsr();
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
                            generarForPag();
                        }
                    }
                }
            }
            catch (Exception e){
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
                limpiarFrm();
                
                txtCodDoc.setEditable(false);
                txtGasPro.setEnabled(false);
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

                if( strNecPerAutPag.equals("S") ){
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_NO_EDI);
                }
                else{
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                }

                txtCodCom.setText(""+objParSis.getCodigoUsuario());
                mostrarVenConCom(3);

                strNumSerFacPrv="";
                strNumAutSriFacPrv="";
                strNumFecCadFacPrv="";
                               
                txtSub.setText("0");
                txtIva.setText("0");
                txtTot.setText("0");
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
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
                dtpFecDoc.setEnabled(false);
                txtCodPrv.setEditable(false);
                txtRucPrv.setEditable(false);
                txtDesLarPrv.setEditable(false);
                butPrv.setEnabled(false);
                txtNumDoc.setEditable(false);
                txtGasPro.setEditable(false);
                txtCodDoc.setEditable(false);
                txtCodBen.setEditable(false);
                txtNomBen.setEditable(false);
                butBen.setEnabled(false);
                txtCodCom.setEditable(false);
                txtUsrCom.setEditable(false);
                txtNomCom.setEditable(false);
                butCom.setEnabled(false);
                txtNumDoc.setEditable(false);
                objAsiDia.setEditable(true);
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
                cargarReg();
                if(inactivarCamModPerUsr()){
                    objTblModForPag.removeAllRows();
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                    objTblModFacPrv.removeEmptyRows();
                }
                generarForPag();
                objTblModForPag.removeAllRows();
                objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
                objTblModFacPrv.removeEmptyRows();
                objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
            }
        }

        public void clickEliminar()
        {
            cargarDetReg();
        }
               
        public void clickAnular()
        {
            cargarDetReg();
        }
        
        public void clickImprimir()
        {
        }

        public void clickVisPreliminar()
        {
        }

        public void clickAceptar()
        {
        }        
        
        public void clickCancelar()
        {
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }
                
        public boolean consultar()
        {
            consultarReg();
            return true;
        }
        
        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }
        
        public boolean eliminar()
        {
            try
            {
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceDxP<arlDatConDxP.size()-1){
                    intIndiceDxP++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return true;
        }
        
        public boolean anular()
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
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
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
                  
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
            
            if(validarExiDocAso()){
                mostrarMsgInf("El documento tiene pagos asociados.\nNo es posible modificar un documento con pagos asociados.");
                return false;
            }
            
            if (!isCamVal())
                return false;

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

            if(validarExiDocAso()){
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

            if(validarExiDocAso()){
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
        String strMsg="";
        int i;
        BigDecimal bdeMontoDebe;
        try
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
            //Validar el "Beneficiario".
            if (txtCodBen.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Beneficiario</FONT> es obligatorio.<BR>Escriba o seleccione un beneficiario y vuelva a intentarlo.</HTML>");
                txtCodBen.requestFocus();
                return false;
            }
            //Validar el "Comprador".
            if (txtUsrCom.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Comprador</FONT> es obligatorio.<BR>Escriba o seleccione un comprador y vuelva a intentarlo.</HTML>");
                txtUsrCom.requestFocus();
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
            
            //Validar "Fecha de emisión del DxP" no sea mayor a 60 días.
            if(!validarFecEmiDocCad())
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La fecha de emisión del Documento por pagar tiene una antiguedad de mas de 60 días.<BR><BR>Verifique esto y vuelva a intentarlo.</HTML>");
                dtpFecDoc.requestFocus();
                return false;
            }
                        
            //Validar que el "Número de Documento" no se repita.
            if(!validarIsExiNumDoc())
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El número de documento <FONT COLOR=\"blue\">" + txtNumDoc.getText() + "</FONT> ya existe.<BR>Escriba otro número de documento y vuelva a intentarlo.</HTML>");
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                return false;                
            }
            
            //Validar "Datos de Tabla Factura de Proveedor" sean correctos.
            if(!validarDatTblFacPrv()) {
                return false;  
            }

            //Validar que el "Diario esté cuadrado".
            if (!objAsiDia.isDiarioCuadrado())
            {
                mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                return false;
            }
            
            if(isEnabledValAsiDiaDob())
            {
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

            if(!strNecPerAutPag.equals("S")  )
            {
                if(objTblModFacPrv.getRowCountTrue()>0)
                {
                    //Validar que la suma de las facturas del proveedor sea igual al valor total del documento
                    BigDecimal bdeValFac=new BigDecimal(BigInteger.ZERO);
                    BigDecimal bdeSumValFac=new BigDecimal(BigInteger.ZERO);
                    BigDecimal bdgTot=new BigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()));

                    for(i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                        bdeValFac=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString())));
                        bdeSumValFac=bdeSumValFac.add(bdeValFac);
                    }

                    if(bdgTot.compareTo(bdeSumValFac)!=0){
                        mostrarMsgInf("<HTML>El valor del documento no coincide con el valor de las facturas del proveedor.<BR>Cuadre el valor del documento con el valor de las facturas y vuelva a intentarlo.</HTML>");
                        return false;
                    }
                }
            }

            //Validar que la suma de las facturas del proveedor sea igual al valor total del documento
            if(objTblModFacPrv.getRowCountTrue()>0){
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
                if(!isPerRecFacPrvFec()){
                    mostrarMsgInf("<HTML>La factura del proveedor ingresada está fuera del rango de fecha permitida para ser ingresada.<BR>Se debe solicitar al proveedor el cambio de dicha factura con una fecha actualizada.</HTML>");
                    return false;
                }
            }

            if( (objTooBar.getEstado()=='x')  || (objTooBar.getEstado()=='m') ){
                if(isRegCam()){//valida si el registro ha cambiado mientras estaba en memoria(otro usuario lo modifico desde otro prog.)
                    mostrarMsgInf("<HTML>El documento fue modificado mientras ud. lo tenia cargado en memoria.<BR>El documento no podrá ser modificado, debe cargar el documento nuevamente para poder modificar.</HTML>");
                    return false;
                }
            }

            //Validador
            //Valida el total 2 para los documentos distintos a OPSLC
            bdeMontoDebe = objUti.redondearBigDecimal(objAsiDia.getMontoDebeBde(), objParSis.getDecimalesMostrar());
            if (new BigDecimal(txtTot.getText()).compareTo(bdeMontoDebe)!=0 ){
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Total 1</FONT> del documento es diferente al valor del asiento de diario.<BR>"
                             +" Verifique los valores ingresados en el documento y vuelva a intentarlo.</HTML>");
                txtTot.selectAll();
                txtTot.requestFocus();
                return false;
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
            BigDecimal bdgTotForPag=new BigDecimal("0");
            for(i=0; i<objTblModForPag.getRowCountTrue(); i++){
                bdeValForPag=objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_VAL_RET).toString(), objParSis.getDecimalesMostrar());
                bdgTotForPag=bdgTotForPag.add(bdeValForPag);
            }

            //Valida el total 2 para los documentos distintos a OPSLC
            bdgTotForPag = objUti.redondearBigDecimal(bdgTotForPag, objParSis.getDecimalesMostrar());
            if (new BigDecimal(txtTot.getText()).compareTo(bdgTotForPag)!=0 ){
                strMsg = "<HTML>El campo <FONT COLOR=\"blue\">Total 2</FONT> del documento es diferente al valor del asiento de diario.<BR>";
                strMsg += "Verifique los valores ingresados en el documento y vuelva a intentarlo.</HTML>";
                mostrarMsgInf(strMsg);
                txtTot.selectAll();
                txtTot.requestFocus();
                return false;
            }
            
            //Valida que indique si desea registrar mas facturas o no.
            if(!mostrarMsgRegFacPedEmb()){
                return false;
            }
            
            //Valida si el asiento de diario fue modificado, preguntando si se va a regenerar o no.
            if (objAsiDia.isDiarioModificado())
            {
                if(!mostrarMsgRegenerarAsiDia())
                    return false;
            }
            
        } 
        catch(Exception Evt)
        {  
           return false;
        }

        return true;
    } 
    //Fin Función isCamVal()

    /* Función que permite Validar si la factura del proveedor ingresada en el Documento por Pagar existe en el sistema.
     * return true: Si existe Factura del Proveedor en el Sistema
     * return false: En el otro caso.
     */
    private boolean validarExiFacPrv(String strNumFac, String strSerFac, String strNumAutSRI)
    {
	boolean blnRes = false;
        Connection conFacPrvFueIng;
        Statement stmFacPrvFueIng;
        ResultSet rstFacPrvFueIng;
        String strMsg="", strMsgDet="";
        try
        {
            conFacPrvFueIng = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conFacPrvFueIng != null) {
                stmFacPrvFueIng = conFacPrvFueIng.createStatement();
                strSQL = "";
                strSQL+=" SELECT a1.co_emp, a6.tx_nom as tx_nomEmp, a1.co_loc, a4.fe_doc, a2.co_tipdoccon, a5.tx_descor, a4.ne_numdoc";
                strSQL+="      , a2.tx_numChq as NumFac, a2.tx_numser, a4.co_cli, a4.tx_nomCli";
                strSQL+=" FROM tbm_cabrecdoc AS a1";
                strSQL+=" INNER JOIN tbm_detrecdoc AS a2  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_emp as a6 ON a1.co_emp=a6.co_emp";
                strSQL+=" INNER JOIN tbr_detRecDocCabMovInv as a3  ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" INNER JOIN tbm_CabMovInv as a4  ON a3.co_emp=a4.co_Emp AND a3.co_loc=a4.co_loc AND a3.co_tipdocrel=a4.co_tipdoc AND a3.co_docrel=a4.co_doc AND a2.co_cli=a4.co_cli";
                strSQL+=" INNER JOIN tbm_CabTipdoc as a5  ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a2.co_tipDocCon=a5.co_tipDoc";
                strSQL+=" WHERE  a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN ('I', 'E')  AND  a3.st_reg NOT IN('E','I')";
                //strSQL+=" AND a4.co_emp=" + objParSis.getCodigoEmpresa() + " AND a2.co_cli=" + txtCodPrv.getText() + " ;
                strSQL+=" AND  (CASE WHEN a2.tx_numSer IS NOT NULL THEN TRUE ELSE FALSE END) ";
                
                if ( (strNumAutSRI.length()!=37) && (strNumAutSRI.length()!=49) ){ //Preimpresas
                    strSQL+=" AND CAST( (CASE WHEN a2.tx_numChq IS NULL THEN '0' ELSE a2.tx_numChq END ) AS INTEGER) = CAST( " + strNumFac + " AS INTEGER) "; 
                    strSQL+=" AND CAST( (CASE WHEN a2.tx_numser IS NULL THEN '0' ELSE REPLACE (a2.tx_numser, '-','') END ) AS INTEGER) = CAST( " + strSerFac + " AS INTEGER) ";
                    strSQL+=" AND (CASE WHEN a2.tx_numAutSri IS NULL THEN '0' ELSE REPLACE (a2.tx_numAutSri, '-','') END ) = '" + strNumAutSRI + "' ";
                }
                else { //Electrónicas
                    strSQL+=" AND  "; 
                    //strSQL+=" ( ( CAST( (CASE WHEN a2.tx_numChq IS NULL THEN '0' ELSE a2.tx_numChq END ) AS INTEGER) = CAST( " + strNumFac + " AS INTEGER) "; 
                    //strSQL+="     AND CAST( (CASE WHEN a2.tx_numser IS NULL THEN '0' ELSE REPLACE (a2.tx_numser, '-','') END ) AS INTEGER) = CAST( " + strSerFac + " AS INTEGER) ";
                    //strSQL+="   )  OR ";
                    strSQL+="  ( (CASE WHEN a2.tx_numAutSri IS NULL THEN '0' ELSE REPLACE (a2.tx_numAutSri, '-','') END ) = '" + strNumAutSRI + "' ) ";
                    //strSQL+=" ) ";                
                }
                strSQL+=" GROUP BY a1.co_emp, a6.tx_nom, a1.co_loc, a4.fe_doc, a2.co_tipdoccon, a5.tx_descor, a4.ne_numdoc";
                strSQL+="        , a2.tx_numChq, a2.tx_numser, a2.tx_numAutSri, a4.co_cli, a4.tx_nomCli";
                //System.out.println("validarExiFacPrv:"+strSQL);
                rstFacPrvFueIng = stmFacPrvFueIng.executeQuery(strSQL);
                strMsgDet="";
                while (rstFacPrvFueIng.next()) 
                {
                    blnRes = true;
                    strMsgDet+="<tr><td align=\"center\"> "+rstFacPrvFueIng.getString("tx_nomEmp")+"</td>";  
                    strMsgDet+="<td align=\"center\"> "+rstFacPrvFueIng.getString("tx_descor")+"</td>";  
                    strMsgDet+="<td align=\"center\"> "+rstFacPrvFueIng.getString("ne_numdoc")+"</td>"; 
                    strMsgDet+="<td align=\"center\"> "+rstFacPrvFueIng.getString("fe_doc")+"</tr></td>"; 
                }
                
                if(blnRes){
                    strMsg ="<HTML>Los datos de la factura ya aparecen registrados en el sistema, ";
                    strMsg+="<BR>Verifique los siguientes documentos: <BR> ";
                    strMsg+="<BR><CENTER><TABLE BORDER=1><tr><td> Empresa </td><td> Tip.Doc. </td><td> Núm.Doc. </td><td> Fec.Doc. </td></tr>";
                    strMsg+=strMsgDet;
                    strMsg+="</TABLE></HTML>";
                    mostrarMsgInf(strMsg);
                }
                
                rstFacPrvFueIng.close();
                rstFacPrvFueIng = null;
                stmFacPrvFueIng.close();
                stmFacPrvFueIng = null;
                conFacPrvFueIng.close();
                conFacPrvFueIng = null;
            }
        } 
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que valida si existen documentos asociados.
     * @return 
     */
    private boolean validarExiDocAso(){
        boolean blnRes=false;
        Connection conCam;
        Statement stmCam;
        ResultSet rstCam;
        try{
            conCam=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCam!=null){
                stmCam=conCam.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a3.nd_valApl";
                strSQL+=" FROM tbr_detrecdoccabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_cabRecDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_detRecDoc AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" WHERE a1.co_empRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND a1.co_locRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDocRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) ;
                strSQL+=" AND a1.co_docRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
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
                strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc";
                strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_detPag AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag";
                strSQL+=" AND a2.co_tipDoc=a3.co_tipDocPag AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag";
                strSQL+=" INNER JOIN tbm_cabPag AS a4";
                strSQL+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E') AND a3.st_reg NOT IN('I','E') AND a4.st_reg NOT IN('I','E')";
                strSQL+=" GROUP BY a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc";
                //System.out.println("pago asociado: " + strSQL);
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
    
    /**
     * Validar fecha de vencimiento
     * @param fila
     * @return 
     */
    private boolean validarFecVen(int fila){
        boolean blnRes=true;
        int intFecSis[];
        int intFecVncChq[];
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
                        objTblModFacPrv.setValueAt("", fila, INT_TBL_FAC_PRV_DAT_FEC_FAC);
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
                        objTblModFacPrv.setValueAt(strFecVenChq, fila, INT_TBL_FAC_PRV_DAT_FEC_FAC);
                    }
                    else{
                        mostrarMsgInf("Para hacer editable este campo debe ingresar primero la fecha de la factura");
                        objTblModFacPrv.setValueAt("", fila, INT_TBL_FAC_PRV_DAT_FEC_FAC);
                        blnRes=false;
                    }
                }
            }
            else{
                mostrarMsgInf("Para hacer editable este campo debe ingresar primero la fecha de la factura");
                objTblModFacPrv.setValueAt("", fila, INT_TBL_FAC_PRV_DAT_FEC_FAC);
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
     * Verifica si es habilitado el Valor de asiento de diario doble.
     * @return 
     */
    private boolean isEnabledValAsiDiaDob(){
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
    
    /**
     * Verifica si existe asiento de provisión para el pedido embarcado
     * @return 
     */
    private boolean isExiAsiDiaPro(int intFila){
        boolean blnRes=false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strCodTipDocPedEmb="", strCodDocPedEmb;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strCodTipDocPedEmb=objTblMod.getValueAt(intFila, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB)==null?"0":objTblMod.getValueAt(intFila, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).toString();
                strCodDocPedEmb=objTblMod.getValueAt(intFila, INT_TBL_GRL_DAT_CODDOC_PED_EMB)==null?"0":objTblMod.getValueAt(intFila, INT_TBL_GRL_DAT_CODDOC_PED_EMB).toString();
                strSQL ="";
                strSQL+=" SELECT a.co_empRelCabDia, a.co_locRelCabDia, a.co_TipDocRelCabDia, a.co_diaRelCabDia ";
                strSQL+=" FROM tbm_CabSegMovInv AS a ";
                strSQL+=" INNER JOIN tbm_CabDia AS a1 ";
                strSQL+=" ON a.co_empRelCabDia=a1.co_emp AND  a.co_locRelCabDia=a1.co_loc AND a.co_TipDocRelCabDia=a1.co_tipDoc AND a.co_diaRelCabDia=a1.co_dia";
                strSQL+=" WHERE a1.st_reg IN ('A')";
                strSQL+=" AND a.co_empRelCabPedEmbImp="+intCodEmpGrp;
                strSQL+=" AND a.co_locRelCabPedEmbImp="+intCodLocGrp;
                strSQL+=" AND a.co_tipDocRelCabPedEmbImp="+strCodTipDocPedEmb;
                strSQL+=" AND a.co_docRelCabPedEmbImp="+strCodDocPedEmb;
                strSQL+=" GROUP BY a.co_empRelCabDia, a.co_locRelCabDia, a.co_TipDocRelCabDia, a.co_diaRelCabDia ";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                    blnRes=true;
                
                //Actualiza si tiene o no asiento de provisión el pedido.
                objTblMod.setChecked(blnRes, intFila, INT_TBL_GRL_DAT_CHK_ASI_PRO);
                
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
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

    /**
     * Función que permite recibir factura del proveedor basado en la fecha.
     * @return 
     */
    private boolean isPerRecFacPrvFec(){
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
                strSQL+=" SELECT co_emp, co_loc,ne_numMaxDiaRecFacPrvMesAnt";
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

                        //System.out.println("isPerRecFacPrvFec: " + strSQL);
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
    
    /**
     * Función que verifica que existen cambios registrados.
     * @return 
     */
    private boolean isRegCam(){
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
                    strSQL+=" SELECT *FROM tbm_pagMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND a1.co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=" AND a1.co_reg IN(" + strCodReg + ")";
                    strSQL+=" AND a1.st_autpag IN('" + strEstPag + "')";
                    if(strCodCta.equals(""))
                        strSQL+="   AND a1.co_ctaautpag IS NULL";
                    else
                        strSQL+="   AND a1.co_ctaautpag IN(" + strCodCta + ")";
                    //System.out.println("isRegCam: " + strSQL);
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
    
    /**
     * Función obtiene si la empresa por la cual se está ingresando el DxP es Cosenco, Ecuatosa o Detopacio.
     * @return true: Si la empresa por la que se ingresa es: Cosenco, Ecuatosa o Detopacio.
     * false: Caso contrario.
     */
    private boolean getIsEmpCosEcuDet()
    {
        blnIsCos_Ecu_Det = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") != -1
                             || objParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") != -1
                             || objParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") != -1)? true: false;
        return blnIsCos_Ecu_Det;
    }
    
    /**
     * Función obtiene si el documento tiene algun detalle con IVA.
     * @return true: Si la empresa por la que se ingresa es: Cosenco, Ecuatosa o Detopacio.
     * false: Caso contrario.
     */
    private boolean getDocTieIva()
    {
        int i;
        //Verifica si existe algún detalle del documento por pagar que tenga IVA.
        i = 0;
        blnDetDocTieIva = false;
        while (i < objTblMod.getRowCountTrue() && blnDetDocTieIva == false)
        {  
            if (objTblMod.isChecked(i, INT_TBL_GRL_DAT_CHK_IVA)){  
               blnDetDocTieIva = true;
            }
            i++;
        }        
        return blnDetDocTieIva;
    }
    
    /**
     * Función que valida si la fecha de emisión del DxP tiene una antiguedad de más de 60 días.
     * @return true: Si está dentro de la fecha permitida.
     * false: Caso contrario.
     */
    private boolean validarFecEmiDocCad()            
    {
        boolean blnRes=true;
        String strAux;
        int intAux;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            { 
                stm = con.createStatement();
                strAux = dtpFecDoc.getText(); //dtpFecDoc va a estar en formato "dd/MM/yyyy"
                strAux = strAux.substring(6, 10) + "/" + strAux.substring(3, 5) + "/" + strAux.substring(0, 2); //strAux va a estar en formato "yyyy/MM/dd"
                strSQL = "select (current_date - '" + strAux + "') as ne_numDiaAnt";
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {  
                    strAux = rst.getString("ne_numDiaAnt") == null? "" :rst.getString("ne_numDiaAnt");
                    if (!strAux.equals(""))
                    { 
                        intAux = Integer.parseInt(strAux);
                        if (intAux > 60)
                        {  //Como entre la fecha actual y la fecha de emision del Documento por pagar ha transcurrido mas de 60 dias, no se debe 
                           //permitir guardar el documento.    
                           blnRes=false;
                        }
                     }
                }
                rst.close();
                rst = null;
                stm.close();
                stm = null;
                con.close();
                con = null;
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
     * Función que valida si el número de documento existe en el sistema.
     * @return true: Si número de documento no existe en el sistema.
     * false: Caso contrario.
     */
    private boolean validarIsExiNumDoc()            
    {
        boolean blnRes=true;
        try
        {
            if (!txtNumDoc.getText().equals(""))
            {
                //Arma sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.ne_numdoc";
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
                    //Número de documento ya existe.
                    blnRes=false;
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
     * Función que valida todos los datos de la tabla de Factura del Proveedor sean correctos.
     * @return true: Si todos los datos están correctos.
     * false: Caso contrario.
     */
    private boolean validarDatTblFacPrv()            
    {
        boolean blnRes=true;
        BigDecimal bgdSubGen, bdgIvaGen, bgdSubFacPrv, bdgIvaFacPrv;
        boolean blnIsGui;
        int i;
        String strLngNumAutSri="";
        String strLngFecCad="";
        String strNumFac;    //Número de factura con 9 nueve digitos (incluido los "0").
        String strNumSer;    //Número de serie de la factura, con "-".
        String strNumSerAux1, strNumSerAux2;
        String strFac="";    //Número de factura, sin "0" a la izquierda.
        String strSerFac=""; //Número de serie de la factura, sin "-".
        try
        {
            objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_EDI);
            objTblModFacPrv.removeEmptyRows();
        
            bgdSubGen = new BigDecimal(txtSub.getText());
            bgdSubGen = objUti.redondearBigDecimal(bgdSubGen, objParSis.getDecimalesMostrar());
            bdgIvaGen = new BigDecimal(txtIva.getText());
            bdgIvaGen = objUti.redondearBigDecimal(bdgIvaGen, objParSis.getDecimalesMostrar());

            for(i=0; i<objTblModFacPrv.getRowCountTrue(); i++)
            {
                //Valida que no existan filas con datos incompletos.
                if (!objTblModFacPrv.isRowComplete(i))
                {
                    mostrarMsgInf("<HTML>El documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                    tblFacPrv.setRowSelectionInterval(0, 0);
                    tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_VAL_FAC, true, true);
                    tblFacPrv.requestFocus();
                    objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                    blnRes=false;
                }
                else
                {
                    //Valida si debe llevar "Fecha de caducidad" ó debe llevar "Número de Autorización SRI".
                    strLngNumAutSri=objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_AUT)==null?"":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_AUT).toString();
                    strLngFecCad=objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_CAD)==null?"":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_FEC_CAD).toString();
                    if( (strLngNumAutSri.trim().length()==37) &&  (strLngFecCad.length()!=0) )
                    {
                        mostrarMsgInf("<HTML>El campo fecha de caducidad no debe ser ingresado.<BR>Elimine el dato ingresado y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_FEC_CAD, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }
                    else if(   (strLngNumAutSri.trim().length()==10)  &&  (strLngFecCad.length()==0)  ){
                        mostrarMsgInf("<HTML>El campo fecha de caducidad es obligatorio.<BR>Ingrese el dato fecha de caducidad y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_FEC_CAD, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }
                    else if( (strLngNumAutSri.trim().length()!=10) && (strLngNumAutSri.trim().length()!=37) && (strLngNumAutSri.trim().length()!=49) )
                    {
                        mostrarMsgInf("<HTML>El campo número de autorización debe tener 10, 37 ó 49 dígitos.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_AUT, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }

                    //Valida "Número de Factura" debe tener 9 digitos.
                    strNumFac = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_FAC).toString();
                    if (strNumFac.length() != 9)
                    {  
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Fac.</FONT> debe tener 9 digitos.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0); 
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_FAC, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }
                    
                    //Valida "Número de Factura" debe ser Númerico.
                    if (objUti.isNumero(strNumFac) == false)
                    {  
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Fac.</FONT> debe ser numérico.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_FAC, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }

                    //Valida que no exista ingresada la factura del proveedor en el sistema.
                    strFac = eliminarCeros(strNumFac);
                    strSerFac= objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER).toString().trim().replace("-", "");
                    if (objTooBar.getEstado() == 'n') 
                    {
                        if (validarExiFacPrv(strFac, strSerFac, strLngNumAutSri)) 
                        {
                            blnRes=false;
                        }
                    }
                    
                    //Valida "Número de serie de la factura", debe tener 7 digitos.
                    strNumSer = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_NUM_SER).toString();
                    if (strNumSer.length() != 7)
                    {  
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Ser.</FONT> debe tener 7 caracteres con formato 999-999 (números separados con guion).<BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_SER, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }
                    
                    //Valida "Número de serie de la factura", debe ser númerico.
                    strNumSerAux1 = strNumSer.substring(0, 3); // Si la serie es 001-002, va a extraer "001"
                    strNumSerAux2 = strNumSer.substring(4, 7); // Si la serie es 001-002, va a extraer "002"
                    blnIsGui = true;

                    if (strNumSer.lastIndexOf("-") == -1)
                        blnIsGui = false;
                    
                    if (objUti.isNumero(strNumSerAux1) == false || objUti.isNumero(strNumSerAux2) == false || blnIsGui == false)
                    { 
                        mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Núm.Ser.</FONT> debe ser numérico con formato 999-999 (números separados con guion).<BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_NUM_SER, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }
                    
                    //Valida "Valor de Subtotal de la factura"
                    bgdSubFacPrv = new BigDecimal("0.00");
                    strAux = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_SUB) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_SUB).toString();
                    
                    if (objUti.isNumero(strAux) == true)
                    { 
                        bgdSubFacPrv = new BigDecimal(strAux);
                        bgdSubFacPrv = objUti.redondearBigDecimal(bgdSubFacPrv, objParSis.getDecimalesMostrar());
                    }
                    
                    //Valida "Valor de IVA de la factura"
                    bdgIvaFacPrv = new BigDecimal("0.00");
                    strAux = objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA) == null? "" :objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA).toString();
                    
                    if (objUti.isNumero(strAux) == true)
                    {  
                        bdgIvaFacPrv = new BigDecimal(strAux);                       
                        bdgIvaFacPrv = objUti.redondearBigDecimal(bdgIvaFacPrv, objParSis.getDecimalesMostrar());
                    }
                    
                    //Valida "Valor de Sutotal de la Pestaña "General" "
                    if (!bgdSubGen.equals(bgdSubFacPrv))
                    {  
                        mostrarMsgInf("<HTML>El valor del campo <FONT COLOR=\"blue\">Subtotal de la pestaña 'General'</FONT>"
                                    + "<BR> debe ser igual al valor de la columna <FONT COLOR=\"blue\">Sub.Fac.Prv. de la pestaña 'Facturas'</FONT>."
                                    + "<BR><BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_VAL_FAC, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }
                    //Valida "Valor de Sutotal de la Pestaña "General" "
                    if (!bdgIvaGen.equals(bdgIvaFacPrv))
                    { 
                        mostrarMsgInf("<HTML>El valor del campo <FONT COLOR=\"blue\">IVA de la pestaña 'General'</FONT>"
                                    + "<BR>debe ser igual al valor de la columna<FONT COLOR=\"blue\">Iva.Fac.Prv. de la pestaña 'Facturas'</FONT>."
                                    + "<BR><BR>Verifique y vuelva a intentarlo.</HTML>");
                        tblFacPrv.setRowSelectionInterval(0, 0);
                        tblFacPrv.changeSelection(i, INT_TBL_FAC_PRV_DAT_VAL_FAC, true, true);
                        tblFacPrv.requestFocus();
                        objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
                        blnRes=false;
                    }
                }
            }
            objTblModFacPrv.setModoOperacion(objTblModFacPrv.INT_TBL_INS);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;      
    }

    public boolean verificarExiCtaIvaAsiDia(Connection conexion, int intCodEmp, int intCodLoc, int intCodTipDoc, String strNumDia)
    {
        boolean blnRes = false;
        try
        {
           stm = con.createStatement();
           strSQL =  "select count(a.*) as cont_reg ";
           strSQL += "from tbm_detdia as a ";
           strSQL += "inner join ( select co_emp, co_loc, co_tipdoc, co_dia, tx_numdia ";
           strSQL += "             from tbm_cabdia ";
           strSQL += "             where st_reg = 'A' ";
           strSQL += "                and co_emp = " + intCodEmp;
           strSQL += "                and co_loc = " + intCodLoc;
           strSQL += "                and co_tipdoc = " + intCodTipDoc;
           strSQL += "                and tx_numdia = '" + strNumDia + "' ";
           strSQL += "           ) as b on a.co_emp = b.co_emp and a.co_loc = b.co_loc and a.co_tipdoc = b.co_tipdoc and a.co_dia = b.co_dia ";
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
    } 

    public boolean verificarAsiDia()
    {
        boolean blnRes = true;
        boolean blnVrfExiCtaIvaAsiDia;
        try
        {
            if (getIsEmpCosEcuDet() == false)
            {  
                blnVrfExiCtaIvaAsiDia = verificarExiCtaIvaAsiDia(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText());

                if ( (getDocTieIva() == false && blnVrfExiCtaIvaAsiDia == false) ||
                     (getDocTieIva() == true && blnVrfExiCtaIvaAsiDia == true) )
                {  
                    System.out.println("OKK");
                }
                else
                {  
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El Documento por pagar no se pudo grabar por alguno de los siguientes motivos:<BR>"
                                + "<BR>1) El Documento por pagar no tiene ningún item con IVA y en el asiento de diario se encontró una Cuenta de Iva."
                                + "<BR>2) El Documento por pagar tiene algún item con IVA y en el asiento de diario no se encontró una Cuenta de Iva.<BR>"
                                + "<BR>Verifique esto y vuelva a intentarlo.</HTML>");
                    blnRes=false;
                }
            }
        } 
        catch(Exception e)
        {
           blnRes = false;
        }
        return blnRes;
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
    private int obtenerTipDocUsrModDoc(){
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
                    strSQL+=" SELECT ne_tipresmoddoc";
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
    private boolean inactivarCamModPerUsr(){
        boolean blnRes=true;
        try{
            int intTipModDoc;
            intTipModDoc=obtenerTipDocUsrModDoc();
            dtpFecDoc.setEnabled(true);
            txtNumDoc.setEditable(true);
            txaObs1.setEditable(true);
            txaObs2.setEditable(true);
            objAsiDia.setEditable(true);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objAsiDia.setEditable(true);

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
                        objTblModForPag.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        objTblModFacPrv.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                        txtCodForPag.setEditable(false);
                        txtNomForPag.setEditable(false);
                        butForPag.setEnabled(false);
                        if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                        }
                        else
                            mostrarMsgInf("<HTML>Ud. no cuenta con ningún tipo de permiso para Modificar.<BR>.Solicite a su Administrador del Sistema dicho permiso y vuelva a intentarlo.</HTML>");
                    }

                    break;
                case 2://modificación parcial la modificación de la fecha dependerá de si se cuenta con este permiso
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        if( ! strEstImpDoc.equals("S")){//si el documento no está impreso se puede modificar, la modif. de fecha depende de tbr_tipDocUsr.ne_tipresmodfecdoc
                            dtpFecDoc.setEnabled(true);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
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

    private void calcularTot(int fila)
    {
        int i=fila;
        BigDecimal bdeCan=new BigDecimal("0");
        BigDecimal bdePre=new BigDecimal("0");
        BigDecimal bdePorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras()).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        BigDecimal bdgIva=new BigDecimal("0");
        BigDecimal bgdSub=new BigDecimal("0");
        BigDecimal bdgTot=new BigDecimal("0");
        BigDecimal bdgTotSub=new BigDecimal("0");
        BigDecimal bdgTotIva=new BigDecimal("0");
        try{
            bdeCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CAN)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CAN).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CAN).toString()));
            bdePre=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_PRE)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_PRE).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_PRE).toString()));
            bgdSub=bdeCan.multiply(bdePre);

            objTblMod.setValueAt(bgdSub, i, INT_TBL_GRL_DAT_VAL_SUBTOT);

            if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_CHK_IVA)){
                bdgIva=bgdSub.multiply(bdePorIva);
            }
            else{
                bdgIva=new BigDecimal("0");
            }
            objTblMod.setValueAt(bdgIva, i, INT_TBL_GRL_DAT_VAL_IVA);
            bdgTot=bgdSub.add(bdgIva);
            objTblMod.setValueAt(bdgTot, i, INT_TBL_GRL_DAT_VAL_TOT);
            bdgIva=new BigDecimal("0");
            bgdSub=new BigDecimal("0");

            for(int j=0; j<objTblMod.getRowCountTrue(); j++){
                bdeCan=new BigDecimal(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_CAN)==null?"0":(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_CAN).toString().equals("")?"0":objTblMod.getValueAt(j, INT_TBL_GRL_DAT_CAN).toString()));
                bdePre=new BigDecimal(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_PRE)==null?"0":(objTblMod.getValueAt(j, INT_TBL_GRL_DAT_PRE).toString().equals("")?"0":objTblMod.getValueAt(j, INT_TBL_GRL_DAT_PRE).toString()));
                bgdSub=objUti.redondearBigDecimal(bdeCan.multiply(bdePre), objParSis.getDecimalesMostrar());
                bdgTotSub=objUti.redondearBigDecimal(bdgTotSub.add(bgdSub), objParSis.getDecimalesMostrar());
                if(objTblMod.isChecked(j, INT_TBL_GRL_DAT_CHK_IVA)){
                    bdgIva=objUti.redondearBigDecimal(bgdSub.multiply(bdePorIva), objParSis.getDecimalesMostrar());
                }
                else{
                    bdgIva=new BigDecimal("0");
                }
                bdgTotIva=objUti.redondearBigDecimal(bdgTotIva.add(bdgIva), objParSis.getDecimalesMostrar());
            }
            txtSub.setText(bdgTotSub.toString());
            txtIva.setText(bdgTotIva.toString());
            txtTot.setText("" + objUti.redondearBigDecimal(bdgTotSub.add(bdgTotIva), objParSis.getDecimalesMostrar())    );
            bdgTotSub=new BigDecimal("0");
            bdgTotIva=new BigDecimal("0");

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeEmptyRows();
            if(objTooBar.getEstado()=='n')
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

            //Se genera los datos en forma de pago.
            generarForPag();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    private boolean calcularValRet(int fila){
        boolean blnRes=true;
        BigDecimal bdgPorRet=new BigDecimal("0");
        BigDecimal bdeValBasImp=new BigDecimal("0");
        BigDecimal bdgValRet=new BigDecimal("0");
        BigDecimal bdeValCer=new BigDecimal("0");
        try{
            bdgPorRet=new BigDecimal(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
            if(bdgPorRet.compareTo(bdeValCer)>0){
                bdeValBasImp=new BigDecimal(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_BAS_IMP)==null?"0":(objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_BAS_IMP).toString().equals("")?"0":objTblModForPag.getValueAt(fila, INT_TBL_FOR_PAG_DAT_BAS_IMP).toString()));
                bdgValRet=(bdeValBasImp.multiply(bdgPorRet).divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));
            }
            objTblModForPag.setValueAt(bdgValRet, fila, INT_TBL_FOR_PAG_DAT_VAL_RET);
            calcularPagosDxP();
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
        BigDecimal bdgTotFacPrv=new BigDecimal("0");
        BigDecimal bdgTotDocPag=new BigDecimal("0");
        try{
            bdgTotDocPag=new BigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()));
            for(int i=0; i<objTblModFacPrv.getRowCountTrue(); i++){
                bdeValFacPrv=new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString()));
                bdgTotFacPrv=bdgTotFacPrv.add(bdeValFacPrv);
            }
            txtFacPrvDif.setText("" + bdgTotDocPag.subtract(bdgTotFacPrv));
            if(bdgTotDocPag.compareTo(bdgTotFacPrv)>=0)
                blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que asigna los valores de DxP a los datos de la factura
     * @return 
     */
    private boolean cargarValFacFacPrv(){
        boolean blnRes=true;
        BigDecimal bdgTot=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdgSub=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdgIva=new BigDecimal(BigInteger.ZERO);        
        try{
            if(!objTblModFacPrv.isRowEmpty(0)){
                System.out.println("Existen datos en factura");
                bdgTot=new BigDecimal(txtTot.getText()==null?"0":(txtTot.getText().equals("")?"0":txtTot.getText()));
                bdgSub=new BigDecimal(txtSub.getText()==null?"0":(txtSub.getText().equals("")?"0":txtSub.getText()));
                bdgIva=new BigDecimal(txtIva.getText()==null?"0":(txtIva.getText().equals("")?"0":txtIva.getText()));
                objTblModFacPrv.setValueAt(""+bdgTot, 0, INT_TBL_FAC_PRV_DAT_VAL_FAC);
                objTblModFacPrv.setValueAt(""+bdgSub, 0, INT_TBL_FAC_PRV_DAT_SUB);
                objTblModFacPrv.setValueAt(""+bdgIva, 0, INT_TBL_FAC_PRV_DAT_IVA);
                objTblModFacPrv.setValueAt(""+bdgTot, 0, INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG);
                cargarValSinIvaFacPrv();
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }   

    private boolean cargarValSinIvaFacPrv(){
        boolean blnRes=true;
        BigDecimal bgdSubSinIva=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumSub=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeSumSub=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT)==null?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT).toString());
                if(! objTblMod.isChecked(i, INT_TBL_GRL_DAT_CHK_IVA)){
                    bgdSubSinIva=bgdSubSinIva.add(bdeSumSub);
                }
            }
            objTblModFacPrv.setValueAt(bgdSubSinIva, 0, INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
        
    
    /**
     * Obtiene fecha de vencimiento de pago.
     * @param intValDia
     * @param fecha
     * @return 
     */
    private String getFecVenPag(int intValDia, String fecha){
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
                    strDiaPag+=" SELECT a8.co_emp, a8.co_forPag, a8.tx_des AS tx_desForPag , a8.ne_numPag, a8.ne_tipForPag, a9.ne_diaCre";
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
                //System.out.println("arlDatDiaPag_ForPag: " + arlDatDiaPag_ForPag.toString());
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
     * Función que genera observación.
     * @return 
     */
    private boolean generarObsDxP(){
        boolean blnRes=true;
        String strObs="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strObs+=" " + (objTblMod.getValueAt(i, INT_TBL_GRL_DAT_NUM_PED_EMB)==null?"":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_NUM_PED_EMB).toString()) + ": ";
                strObs+=" " + (objTblMod.getValueAt(i, INT_TBL_GRL_DAT_DES_CAR_PAG)==null?"":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_DES_CAR_PAG).toString()) + " - ";
            }
            txaObs2.setText(strObs);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }    

    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()
    {
        //Glosa asiento de DxP
        strAux="";
        strAux+=txtDesCorTipDoc.getText() + " # " + txtNumDoc.getText();
        strAux+="; Proveedor: " + txtDesLarPrv.getText();
        objAsiDia.setGlosa(strAux);   
    }
    
    private boolean actualizaExiAsiPro(){
        boolean blnRes=true;
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            isExiAsiDiaPro(i);
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
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="      , a1.co_usrIng, b5.tx_usr AS tx_nomUsrIng";
                strSQL+="      , a1.co_usrMod, a6.tx_usr AS tx_nomUsrMod";
                strSQL+=" FROM ( (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS b5 ON a1.co_usrIng=b5.co_usr)";
                strSQL+="         LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                strSQL+=" )";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.st_reg<>'E'";
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc="+ objParSis.getCodigoLocal();
                }
                //Validar que sólo se muestre los documentos asignados al programa.
                if(txtCodTipDoc.getText().equals("")) {
                    if(objParSis.getCodigoUsuario()==1)
                        strSQL+=" AND a1.co_TipDoc in ( select co_tipDoc from tbr_tiPDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+")";
                    else
                        strSQL+=" AND a1.co_TipDoc in ( select co_tipDoc from tbr_tiPDocUsr where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+")";
                }
                else 
                    strSQL+=" AND a1.co_tipDoc =" + txtCodTipDoc.getText() + "";
                    
                if (!txtCodDoc.getText().equals(""))
                    strSQL+=" AND a1.co_doc =" + txtCodDoc.getText() + "";
                if (!txtNumDoc.getText().equals(""))
                    strSQL+=" AND a1.ne_numDoc =" + txtNumDoc.getText() + "";
                if (!txtCodPrv.getText().equals(""))
                    strSQL+=" AND a1.co_cli =" + txtCodPrv.getText() + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=" ORDER BY a1.co_loc, a1.fe_doc, a1.co_tipDoc, a1.co_doc";
                
                //System.out.println("consultarReg:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConDxP = new ArrayList();
                while(rst.next())
                {
                    arlRegConDxP = new ArrayList();
                    arlRegConDxP.add(INT_ARL_CON_DXP_COD_EMP,   rst.getInt("co_emp"));
                    arlRegConDxP.add(INT_ARL_CON_DXP_COD_LOC,   rst.getInt("co_loc"));
                    arlRegConDxP.add(INT_ARL_CON_DXP_COD_TIPDOC,rst.getInt("co_tipDoc"));
                    arlRegConDxP.add(INT_ARL_CON_DXP_COD_DOC,   rst.getInt("co_doc"));
                    arlRegConDxP.add(INT_ARL_CON_DXP_TXT_USRING,rst.getString("tx_nomUsrIng"));
                    arlRegConDxP.add(INT_ARL_CON_DXP_TXT_USRMOD,rst.getString("tx_nomUsrMod"));
                    arlDatConDxP.add(arlRegConDxP);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConDxP.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConDxP.size()) + " registros");
                    intIndiceDxP=arlDatConDxP.size()-1;
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    blnRes=true;
                }
            }  
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
        boolean blnRes=false;
        try{
            if (cargarTabGen()){ 
                if (cargarTabForPag()){
                    if (cargarTabFacPrv()){
                        if(objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP)
                                                   , objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC)
                                                   , objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC)
                                                   , objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC))){
                            blnRes=true;
                        }
                    }
                }                    
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
    private boolean cargarCabReg(){
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
                strSQL+="      , a1.co_cli, a1.tx_ruc, a1.tx_telcli, a1.tx_ciucli, a1.tx_nomCli, a1.tx_dirCli, a4.tx_deslar AS tx_nomCiuCli, a1.ne_numDoc";
                strSQL+="      , a1.co_ben, a1.tx_benChq, a1.co_doc, a1.co_com, a5.tx_usr, a5.tx_nom AS tx_nomCom";
                strSQL+="      , a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.tx_obs1, a1.tx_obs2";
                strSQL+="      , a1.st_reg, a1.st_imp, a1.st_autAnu";
                strSQL+="      , a3.tx_numSerFacPrv, a3.tx_numAutSriFacPrv, a3.tx_fecCadFacPrv, a1.st_emichqantrecfacprv, a1.st_docMarLis, a2.st_docNecMarLis";
                strSQL+=" FROM ( (tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS a3 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli";
                strSQL+=" 			      LEFT OUTER JOIN tbm_ciu AS a4 ON a3.co_ciu=a4.co_ciu)";
                strSQL+=" 	  LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_com=a5.co_usr)";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                //System.out.println("cargarCabReg: " + strSQL);
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
                    txtUsrCom.setText((strAux==null)?"":strAux);
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
                strSQL+=" SELECT st_necAutAnuDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + "";
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
            intPosRel = intIndiceDxP+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConDxP.size()) );
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
    private boolean cargarTabGen()
    {
        boolean blnRes=true;
        BigDecimal bdeCan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePreUni=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSub=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdgIva=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePorIva;
        try{
            if (!txtCodPrv.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a5.co_regRel as co_carPag, a2.tx_des as tx_desCarPag";
                    strSQL+="      , a6.co_empPedEmb, a6.co_locPedEmb, a6.co_TipDocPedEmb, a6.co_docPedEmb, a6.tx_numPedEmb, a6.co_CtaPedEmb, a6.co_CtaPro";
                    strSQL+="      , a2.nd_can, a2.nd_preUni, a2.st_iva, a1.nd_poriva, a2.nd_tot, a3.co_motDoc, a4.tx_desCor As tx_desCorMot, a4.tx_desLar AS tx_desLarMot";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detConIntMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" LEFT OUTER JOIN (tbm_retMovInv AS a3 INNER JOIN tbm_motDoc AS a4 ";
                    strSQL+="                  ON a3.co_emp=a4.co_emp AND a3.co_motDoc=a4.co_mot AND a4.st_reg NOT IN('I','E')";                 
                    strSQL+=" )"; 
                    strSQL+=" ON ( case when a1.fe_doc>'2018-01-03' then (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc AND a2.co_Reg=a3.co_reg )";    
                    strSQL+="      ELSE (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                    strSQL+="      END  ";
                    strSQL+="      AND CASE WHEN (select COUNT(co_reg) from tbm_retMovInv ";
                    strSQL+="                     where co_emp="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                    strSQL+="                     and co_loc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                    strSQL+="                     and co_tipDoc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                    strSQL+="                     and co_doc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC)+")=1 THEN TRUE ";
                    strSQL+="          ELSE"; 
                    strSQL+="      	     CASE WHEN (a2.co_reg < (select COUNT(co_reg) from tbm_detConIntMovInv as a1 ";
                    strSQL+="      	                              where a1.co_emp="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                    strSQL+="      	                              and a1.co_loc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                    strSQL+="      	                              and a1.co_tipDoc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                    strSQL+="      	                              and a1.co_doc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC)+")) ";
                    strSQL+="      	     THEN ((select a1.co_motDoc from tbm_retMovInv as a1 ";
                    strSQL+="      	            where a1.co_emp="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                    strSQL+="      	            and a1.co_loc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                    strSQL+="      	            and a1.co_tipDoc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                    strSQL+="      	            and a1.co_doc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                    strSQL+="      	            and a1.co_reg=1 ) = a3.co_motDoc ) ";    
                    strSQL+=" 	     ELSE ";
                    strSQL+=" 		((select a1.co_motDoc from tbm_retMovInv as a1 ";
                    strSQL+=" 		  where a1.co_emp="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                    strSQL+=" 		  and a1.co_loc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                    strSQL+=" 		  and a1.co_tipDoc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                    strSQL+=" 		  and a1.co_doc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                    strSQL+=" 		  and a1.co_reg=2 ) = a3.co_motDoc ) ";
                    strSQL+="        END "; 
                    strSQL+="    END "; 
                    strSQL+=" )"; 
                    strSQL+=" LEFT OUTER JOIN ";
                    strSQL+=" (";
                    strSQL+="   SELECT * FROM tbr_detConIntCarPagPedEmbImp ";
                    strSQL+=" ) as a5 ON a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc AND a2.co_doc=a5.co_doc AND a2.co_reg=a5.co_Reg";
                    strSQL+=" LEFT OUTER JOIN ";
                    strSQL+=" (";
                    strSQL+="     SELECT a.co_emp as co_empPedEmb, a.co_loc as co_locPedEmb, a.co_tipDoc as co_TipDocPedEmb, a.co_doc AS co_docPedEmb ";
                    strSQL+="          , a.tx_numDoc2 AS tx_numPedEmb, a.co_ctaAct as co_CtaPedEmb, a.co_ctaPro, a1.* ";
                    strSQL+="     FROM tbm_cabPedEmbImp as a";
                    strSQL+="     INNER JOIN tbm_carPagPedEmbImp as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_Doc";
                    strSQL+=" )AS a6 on a5.co_empRel=a6.co_emp AND a5.co_locRel=a6.co_loc AND a5.co_tipDocRel=a6.co_tipDoc AND a5.co_docRel=a6.co_doc AND a5.co_regRel=a6.co_carPag";
                    strSQL+=" WHERE a1.co_emp="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                    strSQL+=" AND a1.co_loc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                    strSQL+=" AND a1.co_doc="+objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                    strSQL+=" AND a1.st_reg NOT IN('E')"; 
                    strSQL+=" ORDER BY a2.co_reg";
                    //System.out.println("cargarTabGen: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        bdeCan=objUti.redondearBigDecimal(rst.getBigDecimal("nd_can"), objParSis.getDecimalesBaseDatos());
                        bdePreUni=objUti.redondearBigDecimal(rst.getBigDecimal("nd_preUni"), objParSis.getDecimalesBaseDatos());
                        bgdSub=objUti.redondearBigDecimal(bdeCan.multiply(bdePreUni), objParSis.getDecimalesBaseDatos());
                        bdePorIva=objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva").divide(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos());

                        vecReg=new Vector();
                        vecReg.add(INT_TBL_GRL_DAT_LIN,"");
                        vecReg.add(INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB,   "" + rst.getObject("co_TipDocPedEmb")==null?"":rst.getString("co_TipDocPedEmb"));
                        vecReg.add(INT_TBL_GRL_DAT_CODDOC_PED_EMB,      "" + rst.getObject("co_docPedEmb")==null?"":rst.getString("co_docPedEmb"));
                        vecReg.add(INT_TBL_GRL_DAT_NUM_PED_EMB,         "" + rst.getObject("tx_numPedEmb")==null?"":rst.getString("tx_numPedEmb"));
                        vecReg.add(INT_TBL_GRL_DAT_COD_CTA_PED_EMB,     "" + rst.getObject("co_CtaPedEmb")==null?"":rst.getString("co_CtaPedEmb"));
                        vecReg.add(INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB, "" + rst.getObject("co_CtaPro")==null?"":rst.getString("co_CtaPro"));
                        vecReg.add(INT_TBL_GRL_DAT_CHK_ASI_PRO, null);
                        vecReg.add(INT_TBL_GRL_DAT_BUT_PED_EMB, null);                     
                        vecReg.add(INT_TBL_GRL_DAT_COD_CAR_PAG,  "" + rst.getObject("co_carPag")==null?"":rst.getString("co_carPag"));
                        vecReg.add(INT_TBL_GRL_DAT_DES_CAR_PAG,  "" + rst.getObject("tx_desCarPag")==null?"":rst.getString("tx_desCarPag"));
                        vecReg.add(INT_TBL_GRL_DAT_BUT_CAR_PAG, null);
                        vecReg.add(INT_TBL_GRL_DAT_COD_MOT, rst.getString("co_motDoc"));
                        vecReg.add(INT_TBL_GRL_DAT_DES_COR_MOT,  "" + rst.getObject("tx_desCorMot")==null?"":rst.getString("tx_desCorMot")); 
                        vecReg.add(INT_TBL_GRL_DAT_DES_LAR_MOT,  "" + rst.getObject("tx_desLarMot")==null?"":rst.getString("tx_desLarMot")); 
                        vecReg.add(INT_TBL_GRL_DAT_CHK_MOT, null);
                        vecReg.add(INT_TBL_GRL_DAT_BUT_MOT, null); 
                        vecReg.add(INT_TBL_GRL_DAT_COD_REG, "" + rst.getString("co_reg"));
                        vecReg.add(INT_TBL_GRL_DAT_CAN,     "" + bdeCan);
                        vecReg.add(INT_TBL_GRL_DAT_PRE,     "" + bdePreUni);
                        vecReg.add(INT_TBL_GRL_DAT_VAL_SUBTOT,     "" + bgdSub);
                        vecReg.add(INT_TBL_GRL_DAT_CHK_IVA, null);
                        vecReg.add(INT_TBL_GRL_DAT_VAL_IVA,     "");
                        vecReg.add(INT_TBL_GRL_DAT_VAL_TOT,     "" + objUti.redondearBigDecimal(rst.getBigDecimal("nd_tot"), objParSis.getDecimalesBaseDatos()));
                        
                        strAux=rst.getObject("st_iva")==null?"N":(rst.getString("st_iva").equals("")?"N":rst.getString("st_iva"));
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true), INT_TBL_GRL_DAT_CHK_IVA);
                            bdgIva=objUti.redondearBigDecimal(bgdSub.multiply(bdePorIva), objParSis.getDecimalesBaseDatos());
                            vecReg.setElementAt(bdgIva, INT_TBL_GRL_DAT_VAL_IVA);
                        }
                        strAux=rst.getObject("co_motDoc")==null?"N":(rst.getString("co_motDoc").equals("")?"N":"S");
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true), INT_TBL_GRL_DAT_CHK_MOT);
                        }
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    con.close();
                    con=null;
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    vecDat.clear();
                    actualizaExiAsiPro();
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
    private boolean cargarTabForPag(){
        boolean blnRes=true;
        String strApl="";
        try{
            if (!txtCodPrv.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+="      , a2.ne_diaCre, a2.fe_ven, a2.co_tipRet, a3.tx_desCor AS tx_desCorTipRet, a3.tx_desLar AS tx_desLarTipRet";
                    strSQL+="      , a2.nd_porRet, a2.tx_aplRet, a2.nd_basImp, a2.tx_codSri, a2.mo_pag, a2.co_reg";
                    strSQL+="      , a1.co_forPag, a4.tx_des AS tx_desForPag, a4.ne_numpag, a4.ne_tipforpag, ABS(a2.nd_abo) AS nd_abo";
                    strSQL+="      , a2.st_autpag, a2.co_ctaautpag";
                    strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabForPag AS a4 ON a1.co_emp=a4.co_emp AND a1.co_forPag=a4.co_forPag)";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_tipRet=a3.co_tipRet AND a3.st_reg NOT IN('I','E')";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
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
                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    con.close();
                    con=null;
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
    private boolean cargarTabFacPrv(){
        boolean blnRes=true;
        BigDecimal bgdSubFacPrv=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdgTotFacPrv=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdgFacPrvPorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
        BigDecimal bdgIvaFacPrv=new BigDecimal(BigInteger.ZERO);
        try{
            if (!txtCodPrv.getText().equals("")){
                con = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(con != null)
                {
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+="     , a2.tx_numChq AS ne_numFac, a2.fe_venChq AS fe_fac, a2.nd_monChq AS nd_valFac";
                    strSQL+="     , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad, a2.nd_valIva";
                    strSQL+=" FROM tbm_cabRecDoc AS a1";
                    strSQL+=" INNER JOIN tbm_detRecDoc AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbr_detRecDocCabMovInv AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                    strSQL+=" WHERE a3.co_empRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                    strSQL+=" AND a3.co_locRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + "";
                    strSQL+=" AND a3.co_tipDocRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + "";
                    strSQL+=" AND a3.co_docRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
                    strSQL+=" AND a1.st_reg NOT IN('E') AND a2.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+="        , a2.tx_numChq, a2.fe_venChq, a2.nd_monChq";
                    strSQL+="        , a2.tx_numSer, a2.tx_numAutSri, a2.tx_fecCad, a2.nd_valIva";
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
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_SER, rst.getString("tx_numSer"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_NUM_AUT, rst.getString("tx_numAutSri"));
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_FEC_CAD, rst.getString("tx_fecCad"));
                        
                        bdgTotFacPrv=objUti.redondearBigDecimal(rst.getBigDecimal("nd_valFac"), objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_FAC, bdgTotFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_SIN_IVA, "0");                        

                        //bgdSubFacPrv=bdgTotFacPrv.divide(bdgFacPrvPorIva, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                        //bdgIvaFacPrv=bdgTotFacPrv.subtract(bgdSubFacPrv);
                        //bdgIvaFacPrv=objUti.redondearBigDecimal(rst.getBigDecimal("nd_valIva"), objParSis.getDecimalesMostrar());
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_SUB, bgdSubFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_IVA, bdgIvaFacPrv);
                        vecRegFacPrv.add(INT_TBL_FAC_PRV_DAT_VAL_PEN_PAG, "");

                        vecDatFacPrv.add(vecRegFacPrv);
                    }
                    rst.close();
                    stm.close();
                    rst=null;
                    stm=null;
                    con.close();
                    con=null;
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
     * Función que carga forma de pago.
     * Cuando NO existen ingresados datos de la factura de proveedor.
     * @return 
     */
    private boolean generarForPag()
    {
        System.out.println("generarForPag:::");
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){  
                if(cargarForPagRet()){      
                    if(calcularPagosDxP()){ 
                        blnRes=true;
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

    /**
     * Cargar Forma de pago de retenciones.
     * @return 
     */
    private boolean cargarForPagRet(){
        System.out.println("cargarForPagRet::::");
        boolean blnRes=true;
        BigDecimal bgdSub=new BigDecimal("0");
        BigDecimal bdgIva=new BigDecimal("0");
        BigDecimal bdgPorRet=new BigDecimal("0");
        BigDecimal bdgValRet=new BigDecimal("0");
        BigDecimal bdgDxPTotSub=new BigDecimal("0");
        BigDecimal bdgDxPTotIva=new BigDecimal("0");
        String strApl="", strCodMotSelUsr="", strCodMot;
        int intTotMot=0;  
        try{
            if(con!=null){
                //if(objTblMod.isCheckedAnyRow(INT_TBL_GRL_DAT_CHK_MOT)){  
                    vecDatForPag.clear();
                    stm=con.createStatement();
                    intTotMot=getDatMot().size();
                    for(int j=0; j<intTotMot;j++)   //Los motivos agrupados que existen en tblDat
                    { 
                        strCodMot=objUti.getStringValueAt(arlDatMot, j, INT_ARL_MOT_COD_MOT);
                        strCodMotSelUsr="";
                        bgdSub=new BigDecimal(BigInteger.ZERO);
                        bdgIva=new BigDecimal(BigInteger.ZERO);
                        bdgDxPTotSub=new BigDecimal(BigInteger.ZERO);
                        bdgDxPTotIva=new BigDecimal(BigInteger.ZERO);
                        for(int i=0; i<objTblMod.getRowCountTrue();i++){
                            strCodMotSelUsr=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_COD_MOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_COD_MOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_COD_MOT).toString());
                            if(strCodMot.equals(strCodMotSelUsr))
                            {
                                bgdSub=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT).toString()));
                                bdgDxPTotSub=bdgDxPTotSub.add(bgdSub);
                                bdgIva=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_IVA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_IVA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_IVA).toString()));
                                bdgDxPTotIva=bdgDxPTotIva.add(bdgIva);
                            }
                        }
                        strSQL="";
                        strSQL+=" SELECT a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                        strSQL+="      ,'" + txtCodForPag.getText() + "' AS co_forPag, '" + txtNomForPag.getText() + "' AS tx_des";
                        strSQL+="      , a9.tx_desCor AS tx_desCorRet, a9.tx_desLar AS tx_desLarRet, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" FROM tbm_motDoc AS a1";
                        strSQL+=" LEFT OUTER JOIN  ";
                        strSQL+=" (  (tbm_polRet AS a2 LEFT OUTER JOIN tbm_tipPer AS a4 ON a2.co_emp=a4.co_emp AND a2.co_ageRet=a4.co_tipper";
                        strSQL+=" 	               LEFT OUTER JOIN tbm_emp AS a5 ON a4.co_emp=a5.co_emp AND a4.co_tipPer=a5.co_tipPer)";
                        strSQL+="      LEFT OUTER JOIN tbm_tipPer AS a3 ON a2.co_emp=a3.co_emp AND a2.co_sujret=a3.co_tipper";
                        strSQL+="      LEFT OUTER JOIN tbm_cli AS a6 ON a3.co_emp=a6.co_emp AND a3.co_tipPer=a6.co_tipPer";
                        strSQL+="      LEFT OUTER JOIN tbm_forPagCli AS a7 ON a6.co_emp=a7.co_emp AND a6.co_cli=a7.co_cli AND a7.st_reg='S'";
                        strSQL+="      LEFT OUTER JOIN tbm_cabForPag AS a8 ON a7.co_emp=a8.co_emp AND a7.co_forPag=a8.co_forPag AND a7.st_reg='S'";
                        strSQL+=" ) ON a1.co_emp=a2.co_emp AND a1.co_mot=a2.co_motTra";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipRet AS a9 ON a2.co_emp=a9.co_emp AND a2.co_tipRet=a9.co_tipRet ";
                        strSQL+=" WHERE a1.co_emp=" +  objParSis.getCodigoEmpresa() + ""; 
                        strSQL+=" AND CURRENT_DATE between a2.fe_vigDes AND (CASE WHEN a2.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a2.fe_vigHas END)";
                        strSQL+=" AND a1.st_reg NOT IN('E','I') AND a2.st_reg NOT IN('E','I')";
                        strSQL+=" AND a6.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                        strSQL+=" AND a1.co_mot=" + strCodMot + "";
                        strSQL+=" AND a6.co_cli=" + txtCodPrv.getText() + "";
                        strSQL+=" GROUP BY a1.co_mot, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet ";
                        strSQL+="        , a9.tx_desCor, a9.tx_desLar, a9.nd_porRet, a9.tx_aplRet, a9.co_cta";
                        strSQL+=" ORDER BY a1.co_mot, a2.co_tipRet, a1.tx_desCor, a2.tx_codSri, a2.co_tipRet";
                        //System.out.println("cargarForPagRet: " + strSQL );
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            bdgPorRet=new BigDecimal("0");
                            bdgValRet=new BigDecimal("0");
                            vecRegForPag=new Vector();
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_LIN, "");
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,          "0");
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,          objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_TIP_RET,      rst.getString("co_tipRet"));
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_COR_TIP_RET,  rst.getString("tx_desCorRet"));
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BUT_TIP_RET,      "");
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_DES_LAR_TIP_RET,  rst.getString("tx_desLarRet"));
                            bdgPorRet=rst.getBigDecimal("nd_porRet");
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_POR_RET,          bdgPorRet);
                            strApl=rst.getString("tx_aplRet");
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_APL,              strApl.equals("S")?"Subtotal":(strApl.equals("I")?"Iva":(strApl.equals("O")?"Otros":"")));
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_EST_APL,          strApl);
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_BAS_IMP,          strApl.equals("S")?""+bdgDxPTotSub:(strApl.equals("I")?""+bdgDxPTotIva:"0"));
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_COD_SRI,          rst.getString("tx_codSri"));
                            if(strApl.equals("S"))
                                bdgValRet=bdgDxPTotSub.multiply((bdgPorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                            if(strApl.equals("I"))
                                bdgValRet=bdgDxPTotIva.multiply((bdgPorRet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                            //System.out.println("strApl: "+strApl);
                            //System.out.println("bdgValRet: "+bdgValRet);
                            vecRegForPag.add(INT_TBL_FOR_PAG_DAT_VAL_RET,          objUti.redondearBigDecimal(bdgValRet, objParSis.getDecimalesMostrar()));
                            vecDatForPag.add(vecRegForPag);
                        }
                        rst.close();
                        rst=null;                        
                    }           
                    //Asignar vectores al modelo.
                    objTblModForPag.setData(vecDatForPag);
                    tblForPag.setModel(objTblModForPag);
                    vecDatForPag.clear();

                    stm.close();
                    stm=null;
                //}
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
     * Función que calcula pagos en la pestaña "Forma de Pago" del documento por pagar.
     * @return 
     */
    private boolean calcularPagosDxP(){
        System.out.println("calcularPagosDxP:::: ");
        boolean blnRes=true;
        BigDecimal bdgTot=new BigDecimal("0");
        BigDecimal bdeValCer=new BigDecimal("0");
        BigDecimal bdgPorRet=new BigDecimal("0");
        BigDecimal bdgValRet=new BigDecimal("0");
        BigDecimal bdgValRetTot=new BigDecimal("0");
        BigDecimal bdgValApl=new BigDecimal("0");
        int intNumDiaForPag=Integer.parseInt(txtNumForPag.getText()==null?"0":(txtNumForPag.getText().equals("")?"0":txtNumForPag.getText()));
        int intTipDiaForPag=Integer.parseInt(txtTipForPag.getText()==null?"0":(txtTipForPag.getText().equals("")?"0":txtTipForPag.getText()));
        int intIndDiaForPag_Db=0;
        int intValDia=0;
        String strValTipApl="";
        getDiaPag_ForPag();
        try{
            if(intNumDiaForPag>0){
                bdgTot=txtTot.getText()==null?bdeValCer:(txtTot.getText().equals("")?bdeValCer:new BigDecimal(txtTot.getText()));
                for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
                    bdgPorRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                    if(bdgPorRet.compareTo(bdeValCer)>0){
                        bdgValRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                        bdgValRetTot=bdgValRetTot.add(bdgValRet);
                    }
                }
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_INS);
                for(int i=(objTblModForPag.getRowCountTrue()-1); i>=0; i--){
                    bdgPorRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                    strValTipApl=objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL)==null?"":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL).toString().equals("")?"":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_EST_APL).toString());
                    if( (bdgPorRet.compareTo(bdeValCer)<=0) &&  (strValTipApl.equals(""))  ){
                        objTblModForPag.removeRow(i);
                    }
                }
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_EDI);
                objTblModForPag.removeEmptyRows();
                bdgValApl=objUti.redondearBigDecimal((bdgTot.subtract(bdgValRetTot)), objParSis.getDecimalesMostrar());
                bdgValApl=(bdgValApl.divide(new BigDecimal("" + intNumDiaForPag), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));
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
                    intValDia=objUti.getIntValueAt(arlDatDiaPag_ForPag, intIndDiaForPag_Db, INT_ARL_DIA_PAG_NUM_DIA_CRE);
                    objTblModForPag.insertRow();
                    intFil=(objTblModForPag.getRowCountTrue()-1);
                    objTblModForPag.setValueAt("" + intValDia, intFil, INT_TBL_FOR_PAG_DAT_DIA_CRE);
                    //Calcula la fecha de vencimiento de los pagos y retenciones
                    objTblModForPag.setValueAt(objUti.formatearFecha(getFecVenPag(intValDia,dtpFecDoc.getText()), "yyyy-MM-dd", "dd/MM/yyyy"), intFil, INT_TBL_FOR_PAG_DAT_FEC_VEN);
                    objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_POR_RET);
                    objTblModForPag.setValueAt("0.00", intFil, INT_TBL_FOR_PAG_DAT_BAS_IMP);
                    objTblModForPag.setValueAt(bdgValApl, intFil, INT_TBL_FOR_PAG_DAT_VAL_RET);
                    intIndDiaForPag_Db++;
                }

                //por si no cuadra la division de los pagos, se coloca la diferencia al ultimo registro
                BigDecimal bdgValDif=new BigDecimal("0");
                BigDecimal bdgValUltPag=new BigDecimal("0");
                bdgValRetTot=new BigDecimal("0");
                for(int i=0; i<objTblModForPag.getRowCountTrue(); i++){
                    bdgValRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                    bdgValRetTot=bdgValRetTot.add(bdgValRet);
                }
                if(bdgTot.compareTo(bdgValRetTot)==0){
                }
                else{
                    bdgValDif=bdgTot.subtract(bdgValRetTot);
                    bdgValUltPag=new BigDecimal(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt((objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                    objTblModForPag.setValueAt((bdgValUltPag.add(bdgValDif)), (objTblModForPag.getRowCountTrue()-1), INT_TBL_FOR_PAG_DAT_VAL_RET);
                }
                txtForPagDif.setText("" + bdgTot.subtract(bdgValRetTot.add(bdgValDif)));
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        System.out.println("**************** INSERTAR ****************");
        boolean blnRes = false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertarCabMovInv()){
                    if (insertarDetConIntMovInv()){
                        if (insertarRelDetConIntCarPag()){
                            if (insertarPagMovInv()){
                                if (insertarRetMovInv()){
                                    if (insertarCabRecDoc()){ //solo si existe factura de proveedor
                                        if (insertarDetRecDoc()){ //inserta en tbr_detRecDocCabMovInv....solo si existe factura de proveedor, tb
                                            if (actualizarCli()){ //actualiza tbm_cli
                                                if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))  {
                                                    if(insertarCabSegMovInvDxP()){   
                                                        if(insertarRelDxP_PedEmb()){   
                                                            if(insertarRelDxP_CarPag()){   
                                                                if(insertarRelDxP_AsiDiaProImpPedEmb()){
                                                                    if(generarCiePagLocPedEmb()){
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
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc)";
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
                strSQL+="  " + objParSis.getCodigoEmpresa() + "";//co_emp
                strSQL+=", " + objParSis.getCodigoLocal() + "";  //co_loc
                strSQL+=", " + txtCodTipDoc.getText() + "";      //co_tipdoc
                strSQL+=", " + txtCodDoc.getText() + "";         //co_doc
                strSQL+=", Null";//ne_secgrp
                strSQL+=", Null";//ne_secemp
                strSQL+=", Null";//ne_numcot
                strSQL+=", " + txtNumDoc.getText() + "";//ne_numdoc
                strSQL+=", Null";//tx_numped
                strSQL+=", Null";//ne_numgui
                strSQL+=", Null";//co_dia
                strSQL+=",' " + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//fe_ven
                strSQL+=", " + txtCodPrv.getText() + "";         //co_cli
                strSQL+=", " + objUti.codificar(strIdePrv) + ""; //tx_ruc
                strSQL+=", " + objUti.codificar(txtDesLarPrv.getText()) + ""; //tx_nomcli
                strSQL+=", " + objUti.codificar(strDirPrv) + ""; //tx_dircli
                strSQL+=", " + objUti.codificar(strTelPrv) + ""; //tx_telcli
                strSQL+=", " + objUti.codificar(strCiuPrv) + ""; //tx_ciucli
                strSQL+=", " + txtCodCom.getText() + "";         //co_com
                strSQL+=", " + objUti.codificar(txtNomCom.getText()) + ""; //tx_nomven
                strSQL+=", Null"; //tx_ate
                strSQL+=", " + txtCodForPag.getText() + ""; //co_forpag
                strSQL+=", " + objUti.codificar(txtNomForPag.getText()) + ""; //tx_desforpag
                strSQL+=", " + new BigDecimal(txtSub.getText()).multiply(new BigDecimal("" + intSig)) + ""; //nd_sub
                
                if ( new BigDecimal(txtIva.getText()).compareTo(BigDecimal.ZERO)>0  )
                {  
                   strSQL+=", " + bdePorIva; //nd_poriva
                }
                else
                    strSQL+=", 00"; //nd_poriva
                
                strSQL+=", " + new BigDecimal(txtIva.getText()).multiply(new BigDecimal("" + intSig)) + "";//nd_valiva
                strSQL+=", " + new BigDecimal(txtTot.getText()).multiply(new BigDecimal("" + intSig)) + "";//nd_tot
                strSQL+=", Null";//tx_ptopar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_mottra
                strSQL+=", Null";//tx_desmottra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motdoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultmod
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_commod
                strSQL+=", Null";//fe_con
                strSQL+=", Null";//tx_obs3
                strSQL+=", Null";//co_forret
                strSQL+=", Null";//tx_vehret
                strSQL+=", Null";//tx_choret
                strSQL+=", 0";//ne_numvecrecdoc
                strSQL+=", Null";//fe_ultrecdoc
                strSQL+=", Null";//tx_obssolaut
                strSQL+=", Null";//tx_obsautsol
                strSQL+=", Null";//st_aut
                strSQL+=", Null";//ne_valaut
                strSQL+=", 'C'";//st_tipdev
                strSQL+=", 'P'";//st_coninv
                strSQL+=", 'I'";//st_regrep
                strSQL+=", Null";//ne_numdocree
                //strSQL+=",0";//nd_tasInt
                //strSQL+=",1";//ne_tipCal
                //strSQL+=",1";//ne_uniTie
                strSQL+=", 'N'";//st_creguirem
                strSQL+=", 'N'";//st_coninvtraaut
                strSQL+=", Null";//co_locrelsoldevven
                strSQL+=", Null";//co_tipdocrelsoldevven
                strSQL+=", Null";//co_docrelsoldevven
                strSQL+=", Null";//st_excdocconvencon
                strSQL+=", Null";//fe_autexcdocconvencon
                strSQL+=", Null";//co_usrautexcdocconvencon
                strSQL+=", Null";//tx_comautexcdocconvencon
                strSQL+=", " + txtCodBen.getText() + "";//co_ben
                strSQL+=", " + objUti.codificar(txtNomBen.getText()) + "";//tx_benchq
                strSQL+=", 'N'";//st_docconmersaldemdebfac
                strSQL+=", Null";//st_autanu
                strSQL+=", Null";//fe_autanu
                strSQL+=", Null";//co_usrautanu
                strSQL+=", Null";//tx_comautanu
                if(chkEmiChqAntRecFacPrv.isSelected())
                    strSQL+=", 'S'";//st_emiChqAntRecFacPrv
                else
                    strSQL+=", Null";//st_emiChqAntRecFacPrv

                if(chkNecMarLis.isSelected())
                    strSQL+=", 'S'";//st_docMarLis
                else
                    strSQL+=", Null";//st_docMarLis

                strSQL+=");";

                //System.out.println("insertarCabMovInv: " + strSQL);
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
                    strSQL+=" INSERT INTO tbm_detConIntMovInv";
                    strSQL+=" (co_emp,co_loc,co_tipDoc,co_doc,co_reg,tx_des,nd_can,nd_preUni,st_iva,nd_tot,st_regRep)";
                    strSQL+="  SELECT ";
                    strSQL+="  " + objParSis.getCodigoEmpresa() + " AS co_emp";//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal() + " AS co_loc";  //co_loc
                    strSQL+=", " + txtCodTipDoc.getText() + " AS co_tipDoc";   //co_tipDoc
                    strSQL+=", " + txtCodDoc.getText() + " AS co_doc";         //co_doc
                    strSQL+=", " + j + " AS co_reg"; //co_reg
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_DES_CAR_PAG)) + " AS tx_des";//tx_des
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CAN) + " AS nd_can";   //nd_can
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_PRE) + " AS nd_preUni";//nd_preUni
                    if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_CHK_IVA))
                        strSQL+=", 'S' AS st_iva";//st_iva
                    else
                        strSQL+=", 'N' AS st_iva";//st_iva
                    strSQL+=", " + intSig*Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_VAL_TOT), 3)) + " AS nd_tot";//nd_tot
                    strSQL+=", 'I' AS st_regRep";//st_regRep
                    strSQL+=" WHERE NOT EXISTS(";
                    strSQL+="    SELECT *FROM tbm_detConIntMovInv ";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="    AND co_reg=" + j + "";
                    strSQL+=" );";

                    objTblMod.setValueAt(""+j, i, INT_TBL_GRL_DAT_COD_REG);
                    j++;

                    strSQL+=" UPDATE tbm_detConIntMovInv";
                    strSQL+=" SET tx_des=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_DES_CAR_PAG)) + "";//tx_des
                    strSQL+="   , nd_can=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CAN) + "";//nd_can
                    strSQL+="   , nd_preUni=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_PRE) + "";//nd_preUni
                    if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_CHK_IVA))
                        strSQL+="   ,st_iva='S'";//st_iva
                    else
                        strSQL+="   ,st_iva='N'";//st_iva
                    strSQL+="   ,nd_tot=" + intSig*Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_VAL_TOT), 3)) + "";//nd_tot
                    strSQL+="   ,st_regRep='M'";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + "";
                    strSQL+=" AND EXISTS( ";
                    strSQL+="    SELECT *FROM tbm_detConIntMovInv ";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="    AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + "";
                    strSQL+=" );";

                    strSQLUpd+=strSQL;
                }
                //System.out.println("insertarDetConIntMovInv: " + strSQLUpd);
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
     * Esta función permite insertar la relación del detalle de un registro y cargos a pagar.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarRelDetConIntCarPag(){
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
                    strSQL+=" INSERT INTO tbr_detConIntCarPagPedEmbImp";
                    strSQL+=" (co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRel,co_locRel,co_tipDocRel,co_docRel,co_regRel)";
                    strSQL+="  SELECT ";
                    strSQL+="  " + objParSis.getCodigoEmpresa() + " AS co_emp"; //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal() + " AS co_loc";   //co_loc
                    strSQL+=", " + txtCodTipDoc.getText() + " AS co_tipDoc";    //co_tipDoc
                    strSQL+=", " + txtCodDoc.getText() + " AS co_doc";          //co_doc
                    strSQL+=", " + j + " AS co_reg";                            //co_reg
                    strSQL+=", " + intCodEmpGrp + " AS co_empRel";              //co_empRel
                    strSQL+=", " + intCodLocGrp + " AS co_locRel";              //co_locRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB)) + " AS co_tipDocRel";//co_tipDocRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODDOC_PED_EMB)) + " AS co_docRel";      //co_docRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CAR_PAG)) + " AS co_regRel";         //co_regRel                    
                    strSQL+=" WHERE NOT EXISTS( ";
                    strSQL+="    SELECT * FROM tbr_detConIntCarPagPedEmbImp ";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="    AND co_reg=" + j + "";
                    strSQL+=" );";

                    objTblMod.setValueAt(""+j, i, INT_TBL_GRL_DAT_COD_REG);
                    j++;

                    strSQL+=" UPDATE tbr_detConIntCarPagPedEmbImp ";
                    strSQL+=" SET co_empRel=" + intCodEmpGrp + "";//co_empRel
                    strSQL+="   , co_locRel=" + intCodLocGrp + "";//co_locRel
                    strSQL+="   , co_tipDocRel=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB)) + "";//co_tipDocRel
                    strSQL+="   , co_docRel=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODDOC_PED_EMB)) + "";      //co_docRel
                    strSQL+="   , co_regRel=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CAR_PAG)) + "";       //co_regRel
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + "";
                    strSQL+=" AND EXISTS( ";
                    strSQL+="    SELECT * FROM tbr_detConIntCarPagPedEmbImp ";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="    AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + "";
                    strSQL+=" );";

                    strSQLUpd+=strSQL;
                }
                //System.out.println("insertarRelDetConIntCarPag: " + strSQLUpd);
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
        double dblPorRet=0;
        BigDecimal bgdValRet= new BigDecimal("-1");
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_NO_EDI);
                objTblModForPag.removeEmptyRows();
                for (int i=0;i<objTblModForPag.getRowCountTrue();i++){
                    bgdValRet=new BigDecimal(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET)==null?"0":(objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i, INT_TBL_FOR_PAG_DAT_VAL_RET).toString()));
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_pagMovInv(";
                    strSQL+="            co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_diaCre, fe_ven,";
                    strSQL+="            co_tipRet, nd_porRet, tx_aplRet, nd_basImp, mo_pag, ne_diaGra,";
                    strSQL+="            nd_abo, st_sop, st_entSop, st_pos, co_banChq, tx_numCtaChq,";
                    strSQL+="            fe_recChq, nd_monChq, co_proChq, st_reg,";
                    strSQL+="            fe_ree, co_usrRee, tx_comRee, st_autPag, co_ctaAutPag, tx_obs1,";
                    strSQL+="            tx_numChq, tx_numSer, tx_numAutSRI, tx_fecCad, fe_venChq, nd_valIva, tx_codSRI, st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();   //co_loc
                    strSQL+=", " + txtCodTipDoc.getText();       //co_tipDoc
                    strSQL+=", " + txtCodDoc.getText();          //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_DIA_CRE) + ""; //ne_diaCre
                    strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_FEC_VEN).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  )+ ""; //fe_ven
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_COD_TIP_RET) + ""; //co_tipRet
                    strSQL+=", " + objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET) + "";     //nd_porRet
                    strSQL+=", " + objUti.codificar(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_EST_APL)) + ""; //tx_aplRet
                    strSQL+=", " + objUti.redondearBigDecimal(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_BAS_IMP).toString(), objParSis.getDecimalesMostrar()) + ""; //nd_basImp
                    strSQL+=", " + objUti.redondearBigDecimal(bgdValRet, objParSis.getDecimalesMostrar()) + ""; //mo_pag
                    strSQL+=", 0";//ne_diaGra
                    strSQL+=", " + new BigDecimal(BigInteger.ZERO) + "";//nd_abo
                    strSQL+=", 'N'"; //st_sop
                    strSQL+=", 'N'"; //st_entSop
                    strSQL+=", 'N'"; //st_pos
                    strSQL+=", Null";//co_banChq
                    strSQL+=", Null";//tx_numCtaChq
                    strSQL+=", Null";//fe_recChq
                    strSQL+=", Null";//nd_monChq
                    strSQL+=", Null";//co_proChq
                    strSQL+=", 'A'"; //st_reg
                    strSQL+=", Null";//fe_ree
                    strSQL+=", Null";//co_usrRee
                    strSQL+=", Null";//tx_comRee
                    strSQL+=", 'N'"; //st_autPag
                    strSQL+=", Null";//co_ctaAutPag
                    strSQL+=", Null";//tx_obs1
                    
                    dblPorRet=Double.parseDouble(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                    if(objTblModFacPrv.getRowCountTrue()>0 && dblPorRet>0){
                        for(int k=0; k<objTblModFacPrv.getRowCountTrue(); k++){
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_FAC)) + "";//tx_numChq
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_SER)) + "";//tx_numSer
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_AUT)) + "";//tx_numAutSRI
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_CAD)) + "";//tx_fecCad
                            strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_FAC).toString(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos())  ) + "";//fe_venChq
                            strSQL+=", " + objUti.redondearBigDecimal(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_IVA).toString(), objParSis.getDecimalesMostrar()).multiply(new BigDecimal("" + intSig)) + "";//nd_valIva
                            break;
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
                //System.out.println("insertarPagMovInv: " + strSQLUpd);
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
        int j=1;
        try{
            if (con!=null){
                if(objTblMod.isCheckedAnyRow(INT_TBL_GRL_DAT_CHK_MOT)){    
                    stm=con.createStatement();
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                    objTblMod.removeEmptyRows();
                    for (int i=0;i<objTblMod.getRowCountTrue();i++){
                        if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_CHK_MOT)){    //objTblModMot
                            strSQL="";
                            strSQL+=" INSERT INTO tbm_retMovInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_motDoc,st_regRep)";
                            strSQL+=" VALUES (";
                            strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                            strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                            strSQL+=", " + txtCodTipDoc.getText();      //co_tipDoc
                            strSQL+=", " + txtCodDoc.getText();         //co_doc
                            strSQL+=", " + j;                           //co_reg
                            strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_MOT) + "";//co_motDoc
                            strSQL+=", 'I'";//st_regRep
                            strSQL+=");";
                            strSQLUpd+=strSQL;
                        }
                        j++;
                    }
                    //System.out.println("insertarRetMovInv: " + strSQLUpd);
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
                    //Obtener el código del último registro.
                    strSQL="";
                    strSQL+=" SELECT MAX(a1.co_doc)";
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
                    strSQL+=" SELECT MAX(a1.ne_ultdoc)";
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
                    strSQL+=" INSERT INTO tbm_cabRecDoc(";
                    strSQL+="               co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc1,nd_monDoc,";
                    strSQL+="               co_usrRec,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,";
                    strSQL+="               co_usrIng,co_usrMod,tx_comIng,tx_comUltMod,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + objParSis.getCodigoEmpresa() + "";//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal() + "";//co_loc
                    strSQL+=", " + INT_COD_TIP_DOC_REC + "";//co_tipdoc
                    strSQL+=", " + intCodDocRec + "";//co_doc
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                    strSQL+=", " + intUltDocIng + "";//ne_numDoc1
                    strSQL+=", " + objUti.redondearBigDecimal(bdeValTotFacPrv, objParSis.getDecimalesMostrar()) + "";//nd_monDoc
                    strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrRec
                    strSQL+=", 'N'";//st_imp
                    //strSQL+=", '"+txtDesCorTipDoc.getText()+ " # " + txtNumDoc.getText()+"'";//tx_obs1
                    strSQL+=", " + objUti.codificar(txtDesCorTipDoc.getText()+" # "+txtNumDoc.getText()+"") + ""; //tx_obs1
                    strSQL+=", Null";//tx_obs2
                    strSQL+=", 'A'";//st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'";//fe_ing
                    strSQL+=", '" + strAux + "'";//fe_ultMod
                    strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrIng
                    strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrMod
                    strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comIng
                    strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comUltMod
                    strSQL+=", 'I'";//st_regRep
                    strSQL+=" );";
                    
                    strSQL+=" UPDATE tbm_cabTipDoc SET ne_ultdoc=" + intUltDocIng;
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() +" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_tipdoc=" + INT_COD_TIP_DOC_REC+";";
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
                        strSQL+=" INSERT INTO tbm_detRecDoc(";
                        strSQL+="             co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_cli,co_banChq,tx_numCtaChq,";
                        strSQL+="             tx_numChq,fe_recChq,fe_venChq,nd_monChq,tx_numSer,tx_numAutSRI,";
                        strSQL+="             tx_fecCad,tx_obs1,tx_obs2,st_reg,st_asiDocRel,nd_valTotAsi,";
                        strSQL+="             fe_asiTipDocCon,co_tipDocCon,nd_valApl,nd_valCon,st_regRep)";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
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
                        strSQL+=", 'A'"; //st_reg
                        if(bdeValTotFacPrv.compareTo(bdeValTotDoc)<=0)
                            strSQL+=", 'S'";//st_asiDocRel
                        else
                            strSQL+=", 'N'";//st_asiDocRel
                        strSQL+=", " + bdeValFacPrv + "";//nd_valTotAsi
                        strSQL+=", Null";//fe_asiTipDocCon
                        strSQL+=", " + txtCodTipDoc.getText() + "";//co_tipDocCon
                        strSQL+=", 0";  //nd_valApl
                        strSQL+=", 0";  //nd_valCon
                        strSQL+=", 'I'";//st_regRep
                        strSQL+=");";

                        strSQL+=" INSERT INTO tbr_detRecDocCabMovInv(";
                        strSQL+="             co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRel,co_locRel,";
                        strSQL+="             co_tipDocRel,co_docRel,nd_valAsi,st_reg,st_regRep)";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                        strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                        strSQL+=", " + INT_COD_TIP_DOC_REC;         //co_tipDoc
                        strSQL+=", " + intCodDocRec;//co_doc
                        strSQL+=", " +j;//co_reg
                        strSQL+=", " + objParSis.getCodigoEmpresa();//co_empRel
                        strSQL+=", " + objParSis.getCodigoLocal();  //co_locRel
                        strSQL+=", " + txtCodTipDoc.getText();      //co_tipDocRel
                        strSQL+=", " + txtCodDoc.getText();         //co_docRel
                        strSQL+=", " + bdeValFacPrv;//nd_valAsi
                        strSQL+=", 'A'";//st_reg
                        strSQL+=", 'I'";//st_regRep
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
     * Función que permite insertar el Documento por pagar en la tabla de seguimiento.
     * @return 
     */
    private boolean insertarCabSegMovInvDxP(){
        boolean blnRes=false;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int j=0; j<arlDatPedEmb.size(); j++)
                {
                    objCodSegPedEmb=null;
                    //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                    strSQL="";
                    strSQL+=" SELECT *FROM(";
                    strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                    strSQL+="         ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                    strSQL+=" 	      AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                    strSQL+=" 	WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_EMP) + "";
                    strSQL+=" 	AND a1.co_loc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_LOC) + "";
                    strSQL+=" 	AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC)+ "";
                    strSQL+=" 	AND a1.co_doc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC) + "";
                    strSQL+=" ) AS b1";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        objCodSegPedEmb=rst.getObject("co_segPedEmb");  //Ya existe seguimiento del pedido embarcado.
                    }
                    //System.out.println("Seguimiento # "+objCodSegPedEmb);
                    
                    //Insertar en Seguimiento: Documento por Pagar.
                    if(objSegMovInv.setSegMovInvCompra(con, objCodSegPedEmb, 5, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                    {
                        blnRes=true;
                    }
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
     
    /**
     * Función que permite relación entre Documento por Pagar y Pedido Embarcado.
     * @return 
     */
    private boolean insertarRelDxP_PedEmb(){
        boolean blnRes=false;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int j=0; j<arlDatPedEmb.size(); j++)
                {
                    objCodSegPedEmb=null;
                    //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                    strSQL="";
                    strSQL+=" SELECT *FROM(";
                    strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                    strSQL+="         ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                    strSQL+=" 	      AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                    strSQL+=" 	WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_EMP) + "";
                    strSQL+=" 	AND a1.co_loc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_LOC) + "";
                    strSQL+=" 	AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC)+ "";
                    strSQL+=" 	AND a1.co_doc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC) + "";
                    strSQL+=" ) AS b1";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        objCodSegPedEmb=rst.getObject("co_segPedEmb");  //Ya existe seguimiento del pedido embarcado.
                    }
                    
                    //Insertar en Seguimiento - Relación: Documento por Pagar vs Pedido Embarcado
                    if(objSegMovInv.setSegMovInvCompra(con, objCodSegPedEmb
                                                       , 5, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "Null"
                                                       , 2
                                                       ,  Integer.parseInt(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_EMP))
                                                       ,  Integer.parseInt(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_LOC)) 
                                                       ,  Integer.parseInt(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC))
                                                       ,  Integer.parseInt(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC)), "Null"
                       ))
                    {
                        blnRes=true;
                    }
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite relación entre Documento por Pagar y Cargos a Pagar
     * @return 
     */
    private boolean insertarRelDxP_CarPag(){
        boolean blnRes=false;
        String strCodTipDocPedEmb, strCodDocPedEmb, strCodCarPag;
        try{
            if(con!=null){
                stm=con.createStatement();
                       
                for(int i=0; i<objTblMod.getRowCountTrue();i++)
                {       
                    objCodSegPedEmb=null;
                    strCodTipDocPedEmb=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).toString());
                    strCodDocPedEmb=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODDOC_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODDOC_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODDOC_PED_EMB).toString());
                    strCodCarPag=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_COD_CAR_PAG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_COD_CAR_PAG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_COD_CAR_PAG).toString());
                    
                    //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                    strSQL="";
                    strSQL+=" SELECT *FROM(";
                    strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                    strSQL+="         ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                    strSQL+=" 	      AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                    strSQL+=" 	WHERE a1.co_emp=" + intCodEmpGrp + "";
                    strSQL+=" 	AND a1.co_loc=" + intCodLocGrp + "";
                    strSQL+=" 	AND a1.co_tipDoc=" + strCodTipDocPedEmb+ "";
                    strSQL+=" 	AND a1.co_doc=" + strCodDocPedEmb + "";
                    strSQL+=" ) AS b1";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        objCodSegPedEmb=rst.getObject("co_segPedEmb");  //Ya existe seguimiento del pedido embarcado.
                    }
                    
                    //Insertar en Seguimiento - Relación: Documento por Pagar vs Cargos a Pagar
                    if(objSegMovInv.setSegMovInvCompra(con, objCodSegPedEmb
                                                       , 5, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "Null"
                                                       , 9, intCodEmpGrp, intCodLocGrp,  Integer.parseInt(strCodTipDocPedEmb), Integer.parseInt(strCodDocPedEmb), Integer.parseInt(strCodCarPag)
                       )){
                        blnRes=true;
                    }
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite relación entre Documento por Pagar y Asiento Diario de Provisión de Importación del Pedido Embarcado.
     * En caso de existir un asiento de provisión generado enlazarlo con el DxP actual.
     * @return 
     */
    private boolean insertarRelDxP_AsiDiaProImpPedEmb(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int j=0; j<arlDatPedEmb.size(); j++)
                {
                    objCodSegPedEmb=null;
                    //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                    strSQL="";
                    strSQL+=" SELECT *FROM(";
                    strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                    strSQL+="         ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                    strSQL+=" 	      AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                    strSQL+=" 	WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_EMP) + "";
                    strSQL+=" 	AND a1.co_loc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_LOC) + "";
                    strSQL+=" 	AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC)+ "";
                    strSQL+=" 	AND a1.co_doc=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC) + "";
                    strSQL+=" ) AS b1";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        objCodSegPedEmb=rst.getObject("co_segPedEmb");  //Ya existe seguimiento del pedido embarcado.
                    }
                    //Armar sentencia SQL que determina cual es el asiento de diario de provisión de importación.
                    strSQL ="";
                    strSQL+=" SELECT a.co_empRelCabDia, a.co_locRelCabDia, a.co_TipDocRelCabDia, a.co_diaRelCabDia";
                    strSQL+=" FROM tbm_CabSegMovInv AS a INNER JOIN tbm_CabDia AS a1";
                    strSQL+=" ON a.co_empRelCabDia=a1.co_emp AND  a.co_locRelCabDia=a1.co_loc AND a.co_TipDocRelCabDia=a1.co_tipDoc AND a.co_diaRelCabDia=a1.co_dia";
                    strSQL+=" INNER JOIN tbm_detDia AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia ";
                    strSQL+=" WHERE a1.st_reg IN ('A')";
                    strSQL+=" AND a.co_empRelCabPedEmbImp="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_EMP);
                    strSQL+=" AND a.co_locRelCabPedEmbImp="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_LOC);
                    strSQL+=" AND a.co_tipDocRelCabPedEmbImp=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC)+ "";
                    strSQL+=" AND a.co_docRelCabPedEmbImp=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC) + "";
                    strSQL+=" AND a2.co_cta=" + objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_CTA_PRO) + "";
                    strSQL+=" GROUP BY a.co_empRelCabDia, a.co_locRelCabDia, a.co_TipDocRelCabDia, a.co_diaRelCabDia ";                
                    //System.out.println("AsientoProvisión.PK: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        //Insertar en Seguimiento - Relación: Documento por Pagar vs Asiento Diario de Provisión de Importación del Pedido Embarcado.
                        if(!objSegMovInv.setSegMovInvCompra(con, objCodSegPedEmb
                                                           , 5, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "Null"
                                                           , 8, rst.getInt("co_empRelCabDia"), rst.getInt("co_locRelCabDia"), rst.getInt("co_TipDocRelCabDia"), rst.getInt("co_diaRelCabDia"), "Null"
                           ))
                        {
                            blnRes=false;
                        }
                    }  
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
   
    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes = false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarCabMovInv()){
                    if(insertarDetConIntMovInv()){
                        if(insertarRelDetConIntCarPag()){
                            if (actualizarPagMovInv()){
                                if (actualizarRetMovInv()){
                                    if (actualizarCabRecDoc()){
                                        if (actualizarDetRecDoc()){
                                            if(actualizarCli()){
                                                if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                                {  
                                                    if(verificarAsiDia()){
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
                strSQL =" SELECT nd_poriva ";
                strSQL+=" FROM tbm_cabmovinv ";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
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

                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
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

    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarPagMovInv(){
        boolean blnRes=true;
        String strSQLUpd="";
        int intUltReg;
        Object objCodCtaAut=null;
        String strAutPag="N";
        int intInsAutPagMovInv=0;
        int intUltRegAut;
        Object objFecChq=null;
        int intPorRet=0;
        try{
            if (con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_ctaAutPag, a1.st_autPag";
                strSQL+=" FROM tbm_pagMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                strSQL+=" AND a1.st_reg IN('A','C')";
                strSQL+=" AND a1.st_autPag='S' AND (a1.co_tipRet=0 OR a1.co_tipRet IS NULL)";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_ctaAutPag, a1.st_autPag";
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
                strSQL ="";
                strSQL+=" SELECT fe_recChq";
                strSQL+=" FROM tbm_pagMovInv AS a1 INNER JOIN tbm_autpagmovinv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + "";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
                strSQL+=" AND a1.st_reg IN('A','C') AND a2.st_reg='A'";
                strSQL+=" AND a1.nd_porRet<=0 ";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objFecChq=rst.getString("fe_recChq");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" UPDATE tbm_pagMovInv";
                strSQL+=" SET st_reg=CASE WHEN x.st_reg='A' THEN 'F' WHEN x.st_reg='C' THEN 'I' END";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.st_reg";
                strSQL+=" 	FROM tbm_pagMovInv AS a1";
                strSQL+="       WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+="       AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+="       AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+="       AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                strSQL+=" 	AND a1.st_reg IN('A','C')";
                strSQL+=" ) AS x";
                strSQL+=" WHERE tbm_pagMovInv.co_emp=x.co_emp AND tbm_pagMovInv.co_loc=x.co_loc AND tbm_pagMovInv.co_tipDoc=x.co_tipDoc";
                strSQL+=" AND tbm_pagMovInv.co_doc=x.co_doc AND tbm_pagMovInv.co_reg=x.co_reg";
                //System.out.println("actualizarPagMovInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;

                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_reg)";
                strSQL+=" FROM tbm_pagMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;

                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_reg)";
                strSQL+=" FROM tbm_autPagMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                intUltRegAut=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltRegAut==-1)
                    return false;
                intUltRegAut++;

                stm=con.createStatement();
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_NO_EDI);
                objTblModForPag.removeEmptyRows();
                for (int i=0;i<objTblModForPag.getRowCountTrue();i++)
                {
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_pagMovInv(";
                    strSQL+="            co_emp,co_loc,co_tipDoc,co_doc,co_reg,ne_diaCre,fe_ven,";
                    strSQL+="            co_tipRet,nd_porRet,tx_aplRet,nd_basImp,mo_pag,ne_diaGra,";
                    strSQL+="            nd_abo,st_sop,st_entSop,st_pos,co_banChq,tx_numCtaChq,";
                    strSQL+="            fe_recChq,nd_monChq,co_proChq,st_reg,";
                    strSQL+="            fe_ree,co_usrRee,tx_comRee,st_autPag,co_ctaAutPag,tx_obs1,";
                    strSQL+="            tx_numChq, tx_numSer,tx_numAutSRI,tx_fecCad, fe_venChq, nd_valIva, tx_codSRI,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);//co_emp
                    strSQL+=", " + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);//co_loc
                    strSQL+=", " + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);//co_tipDoc
                    strSQL+=", " + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);//co_doc
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

                    intPorRet=Integer.parseInt(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET)==null?"0":(objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET).toString().equals("")?"0":objTblModForPag.getValueAt(i,INT_TBL_FOR_PAG_DAT_POR_RET).toString()));
                    System.out.println("actualizarPagMovInv.intPorRet: "+intPorRet);
                    if(objTblModFacPrv.getRowCountTrue()>0 && intPorRet>0){
                        for(int k=0; k<objTblModFacPrv.getRowCountTrue(); k++){
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_FAC)) + "";//tx_numChq
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_SER)) + "";//tx_numSer
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_NUM_AUT)) + "";//tx_numAutSRI
                            strSQL+=", " + objUti.codificar(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_CAD)) + "";//tx_fecCad
                            strSQL+=", " + objUti.codificar(objUti.formatearFecha(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_FEC_FAC).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())) + "";//fe_venChq
                            strSQL+=", " + objUti.redondearBigDecimal(objTblModFacPrv.getValueAt(k,INT_TBL_FAC_PRV_DAT_IVA).toString(), objParSis.getDecimalesMostrar()).multiply(new BigDecimal("" + intSig)) + "";//nd_valIva
                            break;
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
                        strSQL+=" INSERT INTO tbm_autpagmovinv(";
                        strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_aut, fe_autpag,";
                        strSQL+="             co_usrautpag, tx_comautpag, co_ctaautpag, st_reg, st_regrep)";
                        strSQL+=" (";
                        strSQL+=" 	SELECT co_emp, co_loc, co_tipdoc, co_doc";
                        strSQL+="            , " + intUltReg + "AS co_reg, " + intUltRegAut + " AS co_aut, fe_autpag";
                        strSQL+="            , co_usrautpag, tx_comautpag, co_ctaautpag, 'A' AS st_reg, 'I' AS st_regrep";
                        strSQL+="       FROM tbm_autPagMovInv AS a1";
                        strSQL+="       WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                        strSQL+="       AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                        strSQL+="       AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                        strSQL+="       AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                        strSQL+="       AND a1.st_reg='A'";
                        strSQL+="       GROUP BY co_emp, co_loc, co_tipdoc, co_doc, fe_autpag, co_usrautpag, tx_comautpag, co_ctaautpag";
                        strSQL+=" );";
                        strSQL+=" UPDATE tbm_autPagMovInv";
                        strSQL+=" SET st_reg='I' ";
                        strSQL+=" FROM(";
                        strSQL+=" 	SELECT *FROM tbm_autPagMovInv AS a1";
                        strSQL+="       WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                        strSQL+="       AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                        strSQL+="       AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                        strSQL+="       AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                        strSQL+="       AND a1.st_reg='A'";
                        strSQL+="       AND a1.co_reg NOT IN(" + intUltReg + ")";
                        strSQL+="       AND a1.co_aut NOT IN(" + intUltRegAut + ")";
                        strSQL+=" ) AS x";
                        strSQL+=" WHERE tbm_autPagMovInv.co_emp=x.co_emp AND tbm_autPagMovInv.co_loc=x.co_loc";
                        strSQL+=" AND tbm_autPagMovInv.co_tipDoc=x.co_tipDoc AND tbm_autPagMovInv.co_doc=x.co_doc";
                        strSQL+=" AND tbm_autPagMovInv.co_reg=x.co_reg";
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
                if(objTblMod.isCheckedAnyRow(INT_TBL_GRL_DAT_CHK_MOT)){    //objTblModMot
                    stm=con.createStatement();
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                    objTblMod.removeEmptyRows();

                    for (int i=0;i<objTblMod.getRowCountTrue();i++){
                        if(objTblMod.isChecked(i, INT_TBL_GRL_DAT_CHK_MOT)){    //objTblModMot
                            strSQL="";
                            strSQL+=" INSERT INTO tbm_retMovInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_motDoc,st_regRep)";
                            strSQL+=" (SELECT " + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + " AS co_emp";
                            strSQL+=" ,"+ objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + " AS co_loc";
                            strSQL+=" ,"+ objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + " AS co_tipDoc";
                            strSQL+=" ,"+ objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + " AS co_doc";
                            strSQL+=" ,"+ objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + " AS co_reg";
                            strSQL+=" ,"+ objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_MOT) + " AS co_motDoc, 'I' AS st_regRep";
                            strSQL+=" WHERE NOT EXISTS(";
                            strSQL+="       SELECT *FROM tbm_retMovInv ";
                            strSQL+="       WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                            strSQL+="       AND co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                            strSQL+="       AND co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                            strSQL+="       AND co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                            strSQL+="       AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + ")";
                            strSQL+=" );";
                            strSQL+=" UPDATE tbm_retMovInv";
                            strSQL+=" SET co_motDoc=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_MOT) + "";
                            strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                            strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                            strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                            strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                            strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + "";
                            strSQL+=" AND EXISTS( SELECT *FROM tbm_retMovInv ";
                            strSQL+="           WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                            strSQL+="           AND co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                            strSQL+="           AND co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                            strSQL+="           AND co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
                            strSQL+="           AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_REG) + ")";
                            strSQL+=" ;";
                            strSQLUpd+=strSQL;
                        }
                    }
                    System.out.println("actualizarRetMovInv: " + strSQLUpd);
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
                        strSQL+=" , tx_numAutSriFacPrv=" + objUti.codificar(strNumAutSriFacPrv) + "";
                        strSQL+=" , tx_fecCadFacPrv=" + objUti.codificar(strNumFecCadFacPrv) + "";
                        strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                        strSQL+=" AND co_cli=" + txtCodPrv.getText() + "";
                        //System.out.println("actualizarCli: " + strSQL);
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
                intCodDocRec=0;
                strSQL="";
                strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc";
                strSQL+=" FROM tbr_detRecDocCabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_cabRecDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc  ";
                strSQL+=" AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" where a1.co_empRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" and a1.co_locRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" and a1.co_tipdocRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" and a1.co_docRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                //System.out.println("existe-actualizarCabRecDoc: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodDocRec=rst.getInt("co_doc");
                }
                if(intCodDocRec==0){//no existe el documento
                    stm=con.createStatement();
                    //Obtener el código del último registro.
                    strSQL="";
                    strSQL+=" SELECT MAX(a1.co_doc)";
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
                    strSQL+=" SELECT MAX(a1.ne_ultdoc)";
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
                    strSQL+=" INSERT INTO tbm_cabRecDoc(";
                    strSQL+="               co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc1,nd_monDoc,";
                    strSQL+="               co_usrRec,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,";
                    strSQL+="               co_usrIng,co_usrMod,tx_comIng,tx_comUltMod,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa() + "";//co_emp
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
                    strSQL+=" UPDATE tbm_cabRecDoc";
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
        BigDecimal bdeValIva=new BigDecimal(BigInteger.ZERO);
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
                strSQL+="       WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+="       AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
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
                strSQL+="       WHERE a1.co_empRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+="       AND a1.co_locRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+="       AND a1.co_tipDocRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+="       AND a1.co_docRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
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
                strSQL+=" SELECT MAX(a1.co_reg)";
                strSQL+=" FROM tbm_detRecDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_REC;
                strSQL+=" AND a1.co_doc=" + intCodDocRec;
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;

                stm=con.createStatement();
                for (int i=0;i<objTblModFacPrv.getRowCountTrue();i++){
                    bdeValIva=objUti.redondearBigDecimal(new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_IVA).toString())), objParSis.getDecimalesMostrar());
                    bdeValFacPrv=objUti.redondearBigDecimal(new BigDecimal(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC)==null?"0":(objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString().equals("")?"0":objTblModFacPrv.getValueAt(i, INT_TBL_FAC_PRV_DAT_VAL_FAC).toString())), objParSis.getDecimalesMostrar());
                    bdeValTotFacPrv=bdeValTotFacPrv.add(bdeValFacPrv);
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detRecDoc(";
                    strSQL+=" co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_cli,co_banChq,tx_numCtaChq,";
                    strSQL+=" tx_numChq,fe_recChq,fe_venChq,nd_monChq,tx_numSer,tx_numAutSRI,";
                    strSQL+=" tx_fecCad,tx_obs1,tx_obs2,st_reg,st_asiDocRel,nd_valTotAsi,nd_valIva,";
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
                    strSQL+="," + bdeValIva + "";//nd_valIva
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
                strSQL+=" UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                strSQL+=";";
                strSQL+=" UPDATE tbm_cabRecDoc";
                strSQL+=" SET st_reg='E' FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabRecDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_detRecDoc AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbr_detRecDocCabMovInv AS a3";
                strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc ";
                strSQL+="         AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" 	WHERE a3.co_empRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                strSQL+=" 	AND a3.co_locRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC)+ "";
                strSQL+=" 	AND a3.co_tipDocRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + "";
                strSQL+=" 	AND a3.co_docRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
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
                strSQL+=" UPDATE tbm_cabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=" , fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" , tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) ;
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC);
                strSQL+=" ;";
                strSQL+=" UPDATE tbm_cabRecDoc";
                strSQL+=" SET st_reg='I' FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabRecDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_detRecDoc AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbr_detRecDocCabMovInv AS a3";
                strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" 	WHERE a3.co_empRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                strSQL+=" 	AND a3.co_locRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + "";
                strSQL+=" 	AND a3.co_tipDocRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + "";
                strSQL+=" 	AND a3.co_docRel=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
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
     * Esta función permite obtener los motivos ingresados en el detalle.
     * @return los motivos de manera agrupada, es decir,
     * si existen 10 registros con el mismo motivo devuelve 1 solo registro en el arraylist.
     */
    private ArrayList getDatMot(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intCodMot=0;
        arlDatMot = new ArrayList();
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL ="";
                if(objTblMod.isCheckedAnyRow(INT_TBL_GRL_DAT_CHK_MOT)){  
                    for (int i=0;i<objTblMod.getRowCountTrue();i++){
                        intCodMot=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_MOT)==null?"0":(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_MOT).toString().equals("")?"0":objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_MOT).toString()));
                        //Armar sentencia SQL
                        if(i==0) {
                            strSQL+=" SELECT * FROM (";
                        }
                        strSQL+="     SELECT CAST("+intCodMot+" AS INTEGER) AS co_mot";

                       if(i<objTblMod.getRowCountTrue()-1){
                           strSQL+=" UNION ALL";
                       }
                       else{
                           strSQL+=" ) as a WHERE a.co_mot <>0 ";
                           strSQL+=" GROUP BY a.co_mot ";
                           strSQL+=" ORDER BY a.co_mot ";
                       }
                    }
                    //System.out.println("getDatMot:  " + strSQL);         
                    rstLoc=stmLoc.executeQuery(strSQL);
                    arlDatMot = new ArrayList();
                    while(rstLoc.next())
                    {
                        arlRegMot = new ArrayList();
                        arlRegMot.add(INT_ARL_MOT_COD_MOT,   rstLoc.getInt("co_mot"));
                        arlDatMot.add(arlRegMot);
                    }
                    rstLoc.close();
                    rstLoc=null;
                }
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatMot;
    }

    /**
     * Esta función permite obtener los pedidos embarcados ingresados en el detalle.
     * @return los pedidos embarcados de manera agrupada, es decir,
     * si existen 10 registros con el mismo pedido embarcado devuelve 1 solo registro en el arraylist.
     */
    private boolean getArrDatPedEmb()
    {
        boolean blnRes=true;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intCodTipDocPedEmb=0;
        int intCodDocPedEmb=0;
        int intCodCtaPedEmb=0;
        int intCodCtaProPedEmb=0;
        String strChkAsiPro="";
        String strNumPedEmb="";
        arlDatPedEmb = new ArrayList();
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                           
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.removeEmptyRows();
                if(objTooBar.getEstado()=='n')
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                
                strSQL ="";
                for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    intCodTipDocPedEmb=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB)==null?"0":(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).toString()));
                    intCodDocPedEmb=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODDOC_PED_EMB)==null?"0":(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODDOC_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i,INT_TBL_GRL_DAT_CODDOC_PED_EMB).toString()));
                    strNumPedEmb=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_NUM_PED_EMB)==null?"":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_NUM_PED_EMB).toString().equals("")?"":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_NUM_PED_EMB).toString());            
                    intCodCtaPedEmb=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CTA_PED_EMB)==null?"0":(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CTA_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CTA_PED_EMB).toString()));
                    intCodCtaProPedEmb=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB)==null?"0":(objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i,INT_TBL_GRL_DAT_COD_CTA_PRO_PED_EMB).toString()));
                    strChkAsiPro=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CHK_ASI_PRO).toString().equals("true")?"S":"N";
                            
                    //Armar sentencia SQL
                    if(i==0) {
                        strSQL+=" SELECT * FROM (";
                    }
                    strSQL+="     SELECT CAST("+intCodTipDocPedEmb+" AS INTEGER) AS co_tipDocPedEmb";
                    strSQL+="          , CAST("+intCodDocPedEmb+" AS INTEGER) AS co_docPedEmb ";
                    strSQL+="          , CAST('"+strNumPedEmb+"' AS CHARACTER VARYING) AS ne_numPedEmb ";
                    strSQL+="          , CAST("+intCodCtaPedEmb+" AS INTEGER) AS co_ctaPedEmb ";
                    strSQL+="          , CAST("+intCodCtaProPedEmb+" AS INTEGER) AS co_ctaProPedEmb ";
                    strSQL+="          , CAST('"+strChkAsiPro+"' AS CHARACTER VARYING) AS st_exiAsiPro ";
                   
                   if(i<objTblMod.getRowCountTrue()-1){
                       strSQL+=" UNION ALL";
                   }
                   else{
                       strSQL+=" ) as a WHERE (a.co_tipDocPedEmb <>0 AND a.co_docPedEmb<>0)";
                       strSQL+=" GROUP BY a.co_tipDocPedEmb, a.co_docPedEmb, a.ne_numPedEmb, a.co_ctaPedEmb, a.co_ctaProPedEmb, a.st_exiAsiPro ";
                       strSQL+=" ORDER BY a.co_docPedEmb ";
                   }
                }
                //System.out.println("getArrDatPedEmb:  " + strSQL);         
                rstLoc=stmLoc.executeQuery(strSQL);
                arlDatPedEmb = new ArrayList();
                while(rstLoc.next())
                {
                    arlRegPedEmb = new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,  intCodEmpGrp);
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,  intCodLocGrp);
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIPDOC,  rstLoc.getInt("co_tipDocPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,     rstLoc.getInt("co_docPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,     rstLoc.getString("ne_numPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_CTA_PED, rstLoc.getInt("co_ctaPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_CTA_PRO, rstLoc.getInt("co_ctaProPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_VAL_DXP_ACT, 0);
                    arlRegPedEmb.add(INT_ARL_PED_EMB_VAL_PRO_ACT, 0);
                    arlRegPedEmb.add(INT_ARL_PED_EMB_VAL_GAS_PRO, 0);
                    arlRegPedEmb.add(INT_ARL_PED_EMB_VAL_DXP_TOT, 0);
                    arlRegPedEmb.add(INT_ARL_PED_EMB_VAL_PRO_TOT, 0);
                    arlRegPedEmb.add(INT_ARL_PED_EMB_CHK_ASI_PRO, rstLoc.getString("st_exiAsiPro"));
                    arlDatPedEmb.add(arlRegPedEmb);
                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
                conLoc.close();
                conLoc=null;
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
     * Función que obtiene el valor total de los DxP enlazados a la provisión del pedido embarcado
     * @param codTipDocPedEmb
     * @param codDocPedEmb
     * @param bgdValDxPAct
     * @return 
     */
    public BigDecimal getValTotDxPProImp(String codTipDocPedEmb, String codDocPedEmb, BigDecimal bgdValDxPAct)
    {              
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        BigDecimal bdeDxP=new BigDecimal("0");
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                
                //Armar sentencia SQL 
                strSQL ="";
                strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a1.co_cta, SUM(a1.nd_monDeb) AS nd_canDxP ";
                strSQL+=" FROM tbm_cabDia AS a ";
                strSQL+=" INNER JOIN tbm_detDia AS a1 ON a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_TipDoc=a1.co_tipDoc AND a.co_dia=a1.co_Dia ";
                strSQL+=" INNER JOIN tbm_CabMovInv AS a2 ON a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_dia=a2.co_Doc ";
                strSQL+=" INNER JOIN ";
                strSQL+=" ( ";
                strSQL+="     SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_imp, a.co_ctaAct, a.co_ctaPro ";
                strSQL+="     FROM tbm_cabPedEmbImp AS a ";
                strSQL+="     INNER JOIN tbm_plaCta AS a1 ON a.co_imp=a1.co_emp AND a.co_CtaAct=a1.co_Cta ";
                strSQL+="     WHERE a1.st_reg IN ('A') ";
                strSQL+="     AND a.co_emp="+intCodEmpGrp;
                strSQL+="     AND a.co_loc="+intCodLocGrp;
                strSQL+="     AND a.co_TipDoc="+codTipDocPedEmb;
                strSQL+="     AND a.co_doc="+codDocPedEmb;
                strSQL+="     GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_imp, a.co_ctaAct, a.co_ctaPro ";
                //strSQL+=" ) AS a3 ON a.co_Emp=a3.co_imp AND a1.co_cta=a3.co_ctaAct";  //Trae todos los DxP con cuenta de activo
                strSQL+=" ) AS a3 ON a.co_Emp=a3.co_imp AND a1.co_cta=a3.co_ctaPro"; //Trae todos los DxP con cuenta de provisión
                strSQL+=" WHERE a2.st_reg IN ('A') ";
                strSQL+=" AND a2.co_tipDoc IN ("+strCodTipDocOpimpo+", "+strCodTipDocOpcolo+") ";
                strSQL+=" GROUP BY a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a1.co_cta";
                //System.out.println("getValTotDxPProImp: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    bdeDxP=new BigDecimal(rstLoc.getString("nd_canDxP"));
                }  
                
                bdeDxP=bdeDxP.add(bgdValDxPAct);
                //System.out.println("Valor Total DxP: "+bdeDxP);
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bdeDxP;
    }
    
    /**
     * Función que obtiene el valor del asiento de diario de provisiones enlazado al pedido
     * @param codTipDocPedEmb
     * @param codDocPedEmb
     * @param bgdValDxPAct
     * @return 
     */
    public BigDecimal getValTotPro(String codTipDocPedEmb, String codDocPedEmb, String codCtaProPedEmb)
    {                
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        BigDecimal bgdPro=new BigDecimal("0");
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                
                //Armar sentencia SQL 
                strSQL ="";
                //strSQL+=" SELECT a.co_empRelCabDia, a.co_locRelCabDia, a.co_TipDocRelCabDia, a.co_diaRelCabDia, SUM(a2.nd_monHab) AS nd_canPro ";
                strSQL+=" SELECT a.co_empRelCabPedEmbImp, a.co_locRelCabPedEmbImp, a.co_tipDocRelCabPedEmbImp, a.co_docRelCabPedEmbImp, SUM(a2.nd_monHab) AS nd_canPro ";
                strSQL+=" FROM tbm_CabSegMovInv AS a INNER JOIN tbm_CabDia AS a1";
                strSQL+=" ON a.co_empRelCabDia=a1.co_emp AND  a.co_locRelCabDia=a1.co_loc AND a.co_TipDocRelCabDia=a1.co_tipDoc AND a.co_diaRelCabDia=a1.co_dia";
                strSQL+=" INNER JOIN tbm_detDia AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia ";
                strSQL+=" WHERE a1.st_reg IN ('A')";
                strSQL+=" AND a.co_empRelCabPedEmbImp="+intCodEmpGrp;
                strSQL+=" AND a.co_locRelCabPedEmbImp="+intCodLocGrp;
                strSQL+=" AND a.co_tipDocRelCabPedEmbImp="+codTipDocPedEmb;
                strSQL+=" AND a.co_docRelCabPedEmbImp="+codDocPedEmb;
                strSQL+=" AND a2.co_cta="+codCtaProPedEmb;
                strSQL+=" GROUP BY a.co_empRelCabPedEmbImp, a.co_locRelCabPedEmbImp, a.co_tipDocRelCabPedEmbImp, a.co_docRelCabPedEmbImp ";     
                //strSQL+=" GROUP BY a.co_empRelCabDia, a.co_locRelCabDia, a.co_TipDocRelCabDia, a.co_diaRelCabDia ";                
                System.out.println("getValTotPro: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    bgdPro=new BigDecimal(rstLoc.getString("nd_canPro"));
                }  
                
                System.out.println("Valor Total Provisiones: "+bgdPro);
                conLoc.close();
                conLoc=null;
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdPro;
    }

    /**
     * Genera Asiento de Diario del Documento por Pagar
     * @return 
     */
    private boolean generarAsiDiaDxP(){ 
        boolean blnRes=false;
        try
        {
            //Se inicializa el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();
            if(getArrDatPedEmb()){
                if(cargarCtaCon()){
                    if(calcularValAsiDia()){
                        if(cargarDetAsiDia()){
                            //System.out.println("generarAsiDiaDxP");
                            blnRes=true;
                        }
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
    
    /*
     * Función que carga las cuentas contables a utilizarse en el asiento de diario del DxP.
     * @return 
     */
    private boolean cargarCtaCon(){
        boolean blnRes=true;
        intCodCtaIva=0;
        strNumCtaIva="";
        strNomCtaIva="";
        intCodCtaTot=0;
        strNumCtaTot="";
        strNomCtaTot="";
        strCodCtaGas="";
        strNumCtaGas="";
        strNomCtaGas="";
        arlDatCtaSubTot=new ArrayList();
        arlRegCtaSubTot=new ArrayList();
        arlDatCtaPro=new ArrayList();
        arlRegCtaPro=new ArrayList();

        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();               
        
                //Asiento de DxP: Obtiene Cuenta Iva y Total.
                strSQL ="";
                strSQL+=" SELECT x.co_ctaDeb, x.tx_codCtaDeb, x.tx_nomCtaDeb";
                strSQL+="      , y.co_ctaHab, y.tx_codCtaHab, y.tx_nomCtaHab";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaDeb, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaDeb, a2.tx_desLar AS tx_nomCtaDeb";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS x";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaHab, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaHab, a2.tx_desLar AS tx_nomCtaHab";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaHab=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";  
                strSQL+=" 	AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 	AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS y ON x.co_emp=y.co_emp AND x.co_tipDoc=y.co_tipDoc";
                //System.out.println("cargarCtaCon.Cuenta Iva y Total: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaIva=rst.getInt("co_ctaDeb");
                    strNumCtaIva=rst.getString("tx_codCtaDeb");
                    strNomCtaIva=rst.getString("tx_nomCtaDeb");
                    intCodCtaTot=rst.getInt("co_ctaHab");
                    strNumCtaTot=rst.getString("tx_codCtaHab");
                    strNomCtaTot=rst.getString("tx_nomCtaHab");
                }
                                
                //Asiento de DxP: Obtiene cuenta Subtotal
                if(txtCodTipDoc.getText().equals(strCodTipDocOpcolo))  //OPCOLO: Cuenta de Compras Locales.
                {
                    strSQL ="";
                    strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN tbm_detTipDoc AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() ;
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND a1.tx_niv1 NOT IN ('6', '7')";
                    //System.out.println("cargarCtaCon.Cuenta Subtotal: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    arlDatCtaSubTot=new ArrayList();
                    while(rst.next()){
                        arlRegCtaSubTot=new ArrayList();
                        arlRegCtaSubTot.add(INT_ARL_CTA_COD_CTA, rst.getString("co_cta"));
                        arlRegCtaSubTot.add(INT_ARL_CTA_NUM_CTA, rst.getString("tx_codCta"));
                        arlRegCtaSubTot.add(INT_ARL_CTA_NOM_CTA, rst.getString("tx_desLar"));
                        arlDatCtaSubTot.add(arlRegCtaSubTot);
                    }
                }
                else if(txtCodTipDoc.getText().equals(strCodTipDocOpimpo))  //OPIMPO: Cuenta de Activo del Pedido.
                {                  
                    arlDatCtaSubTot=new ArrayList();
                    for(int j=0; j<arlDatPedEmb.size(); j++)
                    {
                        //Validar si tiene Asiento de provision. S=Tiene Provision, N=Tiene Provision.
                        if(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_CHK_ASI_PRO).equals("N"))
                        {
                            //Cuando no tiene provisión se maneja cuenta de subtotal.
                            strSQL ="";
                            strSQL+=" SELECT a1.co_Cta, a1.tx_codCta, a1.tx_DesLar ";
                            strSQL+=" FROM tbm_cabPedEmbImp AS a ";
                            strSQL+=" INNER JOIN tbm_plaCta AS a1 ON a.co_imp=a1.co_emp AND a.co_CtaAct=a1.co_Cta ";
                            strSQL+=" WHERE a1.st_reg IN ('A') AND a.st_Reg IN ('A')";
                            strSQL+=" AND a.co_emp="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_EMP);
                            strSQL+=" AND a.co_loc="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_LOC);  
                            strSQL+=" AND a.co_TipDoc="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC);
                            strSQL+=" AND a.co_doc="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC);
                            //System.out.println("cargarCtaCon.Cuenta Subtotal: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                arlRegCtaSubTot=new ArrayList();
                                arlRegCtaSubTot.add(INT_ARL_CTA_COD_CTA, rst.getString("co_cta"));
                                arlRegCtaSubTot.add(INT_ARL_CTA_NUM_CTA, rst.getString("tx_codCta"));
                                arlRegCtaSubTot.add(INT_ARL_CTA_NOM_CTA, rst.getString("tx_desLar"));
                                arlDatCtaSubTot.add(arlRegCtaSubTot);
                            }                            
                        }
                    }
                }

                //Asiento de DxP: Obtiene cuenta Provisiones
                //if(txtCodTipDoc.getText().equals(strCodTipDocOpimpo))  //OPIMPO: Cuenta de Activo del Pedido.  
                //{  
                    arlDatCtaPro=new ArrayList();
                    for(int j=0; j<arlDatPedEmb.size(); j++)
                    {
                        //Validar si tiene Asiento de provision. S=Tiene Provision, N=Tiene Provision.
                        if(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_CHK_ASI_PRO).equals("S"))
                        {
                            //Cuando tiene provisión se maneja cuenta de provision.
                            strSQL ="";
                            strSQL+=" SELECT a1.co_Cta, a1.tx_codCta, a1.tx_DesLar ";
                            strSQL+=" FROM tbm_cabPedEmbImp AS a ";
                            strSQL+=" INNER JOIN tbm_plaCta AS a1 ON a.co_imp=a1.co_emp AND a.co_CtaPro=a1.co_Cta ";
                            strSQL+=" WHERE a1.st_reg IN ('A') AND a.st_Reg IN ('A')";
                            strSQL+=" AND a.co_emp="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_EMP);
                            strSQL+=" AND a.co_loc="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_LOC);
                            strSQL+=" AND a.co_TipDoc="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC);
                            strSQL+=" AND a.co_doc="+objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC);
                            //System.out.println("cargarCtaCon.Cuenta Provisiones: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                arlRegCtaPro=new ArrayList();
                                arlRegCtaPro.add(INT_ARL_CTA_COD_CTA, rst.getString("co_cta"));
                                arlRegCtaPro.add(INT_ARL_CTA_NUM_CTA, rst.getString("tx_codCta"));
                                arlRegCtaPro.add(INT_ARL_CTA_NOM_CTA, rst.getString("tx_desLar"));
                                arlDatCtaPro.add(arlRegCtaPro);
                            }
                        }
                    }
                //}  
                
                //Asiento de DxP: Obtiene Cuenta de Gastos
                //if(txtCodTipDoc.getText().equals(strCodTipDocOpimpo))  //OPIMPO  
                //{ 
                    //Validar si algún pedido tiene provisión o no.
                    if( objTblMod.isCheckedAnyRow(INT_TBL_GRL_DAT_CHK_ASI_PRO))
                    {
                        //Cuando tiene provisión se maneja cuenta de gasto.
                        strSQL="";
                        strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                        strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                        strSQL+=" AND a1.tx_niv1='6' ";
                        //System.out.println("cargarCtaCon.Cuenta Gastos: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if (rst.next())
                        {
                            strCodCtaGas=rst.getString("co_cta");
                            strNumCtaGas=rst.getString("tx_codCta");
                            strNomCtaGas=rst.getString("tx_desLar");
                        }
                    }
                //}  
 
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
           
    private boolean calcularValAsiDia()
    {
        boolean blnRes=true;
        BigDecimal bgdAux=new BigDecimal("0");
        BigDecimal bgdSubTot=new BigDecimal("0");
        BigDecimal bgdValTotDxPPro=new BigDecimal("0");
        BigDecimal bgdValTotPro=new BigDecimal("0");
        BigDecimal bgdValDxPAct=new BigDecimal("0");
        BigDecimal bgdValProAct=new BigDecimal("0");
        BigDecimal bgdValGasPro=new BigDecimal("0");
        String strCodTipDocPedEmbSel="", strCodDocPedEmbSel="";
        String strCodTipDocPedEmb="", strCodDocPedEmb="",  strCodCtaProPedEmb="";
        try
        {
            txtGasPro.setText("");
            for(int j=0; j<arlDatPedEmb.size(); j++)
            {
                strCodTipDocPedEmb=objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_TIPDOC);
                strCodDocPedEmb=objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_DOC);
                strCodCtaProPedEmb=objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_CTA_PRO);
                
                bgdAux=new BigDecimal("0");
                bgdSubTot=new BigDecimal("0");
                for(int i=0; i<objTblMod.getRowCountTrue();i++)
                {
                    strCodTipDocPedEmbSel=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODTIPDOC_PED_EMB).toString());
                    strCodDocPedEmbSel=objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODDOC_PED_EMB)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODDOC_PED_EMB).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_CODDOC_PED_EMB).toString());
                    if(strCodTipDocPedEmb.equals(strCodTipDocPedEmbSel) && strCodDocPedEmb.equals(strCodDocPedEmbSel) ){
                        //Obtiene Subtotal del Pedido Embarcado
                        bgdAux=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_GRL_DAT_VAL_SUBTOT).toString()));  
                        bgdSubTot=bgdSubTot.add(bgdAux);
                    }
                }
                
                //Obtiene el valor del DxP Actual
                bgdValDxPAct=bgdSubTot;     
                //System.out.println("bgdValDxPAct: "+bgdValDxPAct);
                        
                //Obtiene Valor de Provisión del Asiento de Diario del Pedido Embarcado.
                bgdValTotPro=getValTotPro(strCodTipDocPedEmb, strCodDocPedEmb, strCodCtaProPedEmb);
                //System.out.println("bgdValTotPro: "+bgdValTotPro);
                
                //Obtiene el Valor Total de DxP enlazados a la provisión del pedido embarcado, incluye el valor del DxP Actual.
                bgdValTotDxPPro=getValTotDxPProImp(strCodTipDocPedEmb, strCodDocPedEmb, bgdValDxPAct);
                //System.out.println("bgdValTotDxPPro: "+bgdValTotDxPPro);
          
                /*Actualizar datos del pedido embarcado.*/
                //Validar si tiene Asiento de provision. S=Tiene Provision, N=Tiene Provision.
                if(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_CHK_ASI_PRO).equals("S"))
                {
                    //Cálculo del valor de Provisión del Pedido embarcado en el DxP Actual.
                    //Ingrid Lino - 07Dic2017: Alfredo Paulson indicó que el valor de la provisión se calculará en base al subtotal.
                    bgdValProAct=(bgdValTotPro.compareTo(bgdValTotDxPPro)>0?bgdSubTot:(bgdSubTot.subtract(bgdValTotDxPPro.subtract(bgdValTotPro))));
                
                    //Cálculo del valor de Gastos (Provisión) del Pedido embarcado en el DxP Actual.
                    bgdValGasPro=bgdValGasPro.add((bgdValProAct.compareTo(bgdValDxPAct)>0?new BigDecimal("0"):(bgdValDxPAct.subtract(bgdValProAct))));
                    
                    objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_DXP_ACT, 0);
                    objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_PRO_ACT, bgdValProAct.doubleValue());
                    objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_GAS_PRO, bgdValGasPro.doubleValue());
                }
                else{
                    objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_DXP_ACT, bgdValDxPAct.doubleValue());
                    objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_PRO_ACT, 0);
                    objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_GAS_PRO, 0);
                }
                objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_DXP_TOT, bgdValTotDxPPro.doubleValue());
                objUti.setDoubleValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_PRO_TOT, bgdValTotPro.doubleValue());
            }
            txtGasPro.setText(""+bgdValGasPro);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que carga valores que tendrá el asiento de diario del DxP.
     * @return 
     */
    private boolean cargarDetAsiDia()
    {
        boolean blnRes=true;
        BigDecimal bgdSub=new BigDecimal("0");
        BigDecimal bgdPro=new BigDecimal("0");  //Provisión.
        String strCodCtaPedSel="", strCodCtaPed="";
        String strCodCtaProSel="", strCodCtaPro="";
        try
        {
            //Iva
            if( new BigDecimal(txtIva.getText()).compareTo(BigDecimal.ZERO)>0  ){
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaIva);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaIva);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaIva);
                vecRegDia.add(INT_VEC_DIA_DEB, "" + new BigDecimal(txtIva.getText()));
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }

            //Subtotal
            //System.out.println("Cuentas Subtotal: "+arlDatCtaSubTot.size());
            for(int k=0; k<arlDatCtaSubTot.size();k++)
            {
                strCodCtaPedSel=objUti.getStringValueAt(arlDatCtaSubTot, k, INT_ARL_CTA_COD_CTA);
                for(int j=0; j<arlDatPedEmb.size(); j++)
                {
                    bgdSub=new BigDecimal(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_DXP_ACT).equals("")?"0":objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_DXP_ACT));
                    strCodCtaPed=objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_CTA_PED).equals("")?"0":objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_CTA_PED);
                    if(strCodCtaPedSel.equals(strCodCtaPed) || txtCodTipDoc.getText().equals(strCodTipDocOpcolo))   //OPCOLO: Cuenta de Compras Locales. 
                    {
                        if(bgdSub.compareTo(new BigDecimal("0"))>0)
                        {
                            vecRegDia=new java.util.Vector();
                            vecRegDia.add(INT_VEC_DIA_LIN, "");
                            vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaSubTot, k, INT_ARL_CTA_COD_CTA));
                            vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaSubTot, k, INT_ARL_CTA_NUM_CTA));
                            vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                            vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaSubTot, k, INT_ARL_CTA_NOM_CTA));
                            vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdSub);
                            vecRegDia.add(INT_VEC_DIA_HAB, "0");
                            vecRegDia.add(INT_VEC_DIA_REF, "");
                            vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                            vecDatDia.add(vecRegDia);
                        }                        
                    }
                }
            } 
            
            //Provision
            //System.out.println("Cuentas Provision: "+arlDatCtaPro.size());
            for(int k=0; k<arlDatCtaPro.size();k++)
            {
                strCodCtaProSel=objUti.getStringValueAt(arlDatCtaPro, k, INT_ARL_CTA_COD_CTA);
                for(int j=0; j<arlDatPedEmb.size(); j++)
                {
                    bgdPro=new BigDecimal(objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_PRO_ACT).equals("")?"0":objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_VAL_PRO_ACT));
                    strCodCtaPro=objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_CTA_PRO).equals("")?"0":objUti.getStringValueAt(arlDatPedEmb, j, INT_ARL_PED_EMB_COD_CTA_PRO);
                    
                    if(strCodCtaProSel.equals(strCodCtaPro))   
                    {
                        if(bgdPro.compareTo(new BigDecimal("0"))>0)
                        {
                            vecRegDia=new java.util.Vector();
                            vecRegDia.add(INT_VEC_DIA_LIN, "");
                            vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaPro, k, INT_ARL_CTA_COD_CTA));
                            vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaPro, k, INT_ARL_CTA_NUM_CTA));
                            vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                            vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaPro, k, INT_ARL_CTA_NOM_CTA));
                            vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdPro);
                            vecRegDia.add(INT_VEC_DIA_HAB, "0");
                            vecRegDia.add(INT_VEC_DIA_REF, "");
                            vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                            vecDatDia.add(vecRegDia);
                        }
                    }
                }
            } 
                        
            //Gastos de importación (Provision): Cuenta para asiento DxP.
            if( new BigDecimal(txtGasPro.getText()).compareTo(BigDecimal.ZERO)>0  ){
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaGas);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaGas);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaGas);
                vecRegDia.add(INT_VEC_DIA_DEB, ""+ new BigDecimal(txtGasPro.getText()));
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            
            //Total
            if( new BigDecimal(txtTot.getText()).compareTo(BigDecimal.ZERO)>0  ){
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaTot);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaTot);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaTot);
                vecRegDia.add(INT_VEC_DIA_DEB, "0" );
                vecRegDia.add(INT_VEC_DIA_HAB, ""+ new BigDecimal(txtTot.getText()));
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }

            //System.out.println("vecDatDia: "+vecDatDia);
            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)1);
            actualizarGlo();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
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
                    ///objRptSis.cargarListadoReportes(con);
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

                                        strSQL ="";
                                        strSQL+=" SELECT a3.co_emp, a3.tx_nom AS tx_nomEmp, a3.tx_dir AS tx_dirEmp";
                                        strSQL+="      , a3.tx_ruc AS tx_rucEmp, a4.tx_desLar AS tx_nomCiu, a5.tx_desLar AS tx_nomPai";
                                        strSQL+="      , a8.tx_nom AS tx_nomPrg, a1.ne_numDoc, a1.co_cli, a1.tx_nomCli, a1.co_ben";
                                        strSQL+="      , a1.tx_benChq, a1.fe_doc, a1.co_com, a6.tx_usr AS tx_usrCom, a6.tx_nom AS tx_nomCom";
                                        //strSQL+="      , a1.co_usrIng, a9.tx_usr AS tx_usrIng, a9.tx_nom AS tx_nomIng";
                                        //strSQL+="      , a1.co_usrMod, a10.tx_usr AS tx_usrMod, a10.tx_nom AS tx_nomMod";
                                        strSQL+="      , CASE WHEN a1.nd_sub IS NULL THEN 0 ELSE a1.nd_sub END AS nd_sub";
                                        strSQL+="      , CASE WHEN a1.nd_valIva IS NULL THEN 0 ELSE a1.nd_valIva END AS nd_valIva";
                                        strSQL+="      , CASE WHEN a1.nd_tot IS NULL THEN 0 ELSE a1.nd_tot END AS nd_tot";
                                        strSQL+="      , a11.tx_glo, a1.tx_obs2";
                                        strSQL+=" FROM (tbm_cabMovInv AS a1  INNER JOIN tbm_emp AS a3 ON a1.co_emp=a3.co_emp)";
                                        strSQL+=" INNER JOIN tbm_loc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                                        strSQL+=" INNER JOIN tbm_ciu AS a4 ON a2.co_ciu=a4.co_ciu";
                                        strSQL+=" INNER JOIN tbm_pai AS a5 ON a4.co_pai=a5.co_pai";
                                        strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_com=a6.co_usr";
                                        strSQL+=" INNER JOIN tbm_cabDia AS a11";
                                        strSQL+=" ON a1.co_emp=a11.co_emp AND a1.co_loc=a11.co_loc AND a1.co_tipDoc=a11.co_tipDoc AND a1.co_doc=a11.co_dia";
                                        strSQL+=" LEFT OUTER JOIN tbm_mnusis AS a8 ON a1.co_mnu=a8.co_mnu";
                                        strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                                        strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + "";
                                        strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + "";
                                        strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
                                        strSQLRep=strSQL;

                                        strSQL ="";
                                        strSQL+=" SELECT a3.tx_codcta, a3.tx_deslar, round(a2.nd_mondeb,2) as nd_mondeb, round(a2.nd_monhab,2) as nd_monhab";
                                        strSQL+=" FROM tbm_cabdia as a1, tbm_detdia as a2, tbm_placta as a3";
                                        strSQL+=" WHERE a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc";
                                        strSQL+=" AND a1.co_tipdoc=a2.co_tipdoc";
                                        strSQL+=" AND a1.co_dia=a2.co_dia";
                                        strSQL+=" AND a2.co_emp=a3.co_emp";
                                        strSQL+=" AND a2.co_cta=a3.co_cta";
                                        strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP) + "";
                                        strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC) + "";
                                        strSQL+=" AND a1.co_tipdoc=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC) + "";
                                        strSQL+=" AND a1.co_dia=" + objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC) + "";
                                        strSQLSubRep=strSQL;

                                        mapPar.put("strSQLRep", strSQLRep);
                                        mapPar.put("strSQLSubRep", strSQLSubRep);
                                        mapPar.put("SUBREPORT_DIR", strRutRpt);
                                        mapPar.put("strCamAudRpt", "Usr.Ing.:" + (  objUti.getStringValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_TXT_USRING) + " / Usr.Mod.:" + objUti.getStringValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_TXT_USRMOD) + " / Usr.Imp.:" + objParSis.getNombreUsuario()) + "    Fec.Hor.Imp.:" + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "");
                                        objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                    case 260:
                                        strRutRpt=objRptSis.getRutaReporte(i);
                                        strNomRpt=objRptSis.getNombreReporte(i);
                                        mapPar.put("CodEmp", objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_EMP));
                                        mapPar.put("CodLoc", objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_LOC));
                                        mapPar.put("CodTipDoc", objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_TIPDOC));
                                        mapPar.put("CodDoc", objUti.getIntValueAt(arlDatConDxP, intIndiceDxP, INT_ARL_CON_DXP_COD_DOC));
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
    
    /**
     * Esta función se utiliza para realizar cierre de pagos Locales
     * La idea principal de ésta función es indicar que no se ingresarán mas DxP enlazados al pedido embarcado, por motivo de gastos.
     * Pero debe indicarse si el pedido va a cerrarse o no para pagos locales, 
     * porque puede darse el caso en que el pedido embarcado deba generar un asiento de cierre de provisiones
     * En  cuyo caso, primero se pregunta si desea realizar el cierre de pagos locales, no o cancelar.
     * @return true: Si se decidió cerrar los pagos locales del pedido embarcado.
     * Si el pedido embarcado tiene asiento de provisión (PROIMP) y la Provisión excede al total de DxP, 
     * entonces se genera un asiento de cierre de provisiones. 
     * <BR>false: Si se canceló el cierre de pagos locales del pedido embarcado.
     * Nota.- Como se puede apreciar la función retorna "false" sólo cuando se dió
     * click en el botón "Cancelar".
     */
    private boolean mostrarMsgRegFacPedEmb()
    {
        boolean blnRes=true;
        blnMosVenCiePedLoc=false;
        //Rose: Consultar si sera el Último DxP a ingresarse (Factura).
        strAux ="¿Desea realizar el registro de más facturas enlazadas al pedido embarcado.?\n";
        //strAux+="\nSi desea continuar registrando más facturas enlazadas al pedido de click en SI.";
        //strAux+="\nSi desea finalizar con el registro de facturas enlazadas al pedido de click en NO.";
        //strAux+="\nSi desea cancelar ésta operación de click en CANCELAR.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                //Se permitirá guardar el DxP actual y el pedido seguirá abierto para registrar más facturas.
                break;
            case 1: //NO_OPTION
                //Se realiza el cierre de pagos locales de los pedidos seleccionados. 
                blnMosVenCiePedLoc=true;
                //En caso de tener Provisión excedente al total de DxP, se generará el asiento de cierre de provisiones.
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Función que genera cierre de pagos de los pedidos embarcados seleccionados.
     * @return 
     */
    private boolean generarCiePagLocPedEmb()
    {
        boolean blnRes=false;
        boolean blnProAutCiePag=true;
        try
        {
            if(blnMosVenCiePedLoc){
                /*Si existe mas de un pedido a procesar, se muestra una ventana con pedidos a cerrar pagos. 
                  El proceso de cierre se realiza con la acción del usuario.*/
                if(arlDatPedEmb.size()>1){
                    blnProAutCiePag=false;
                }
                //System.out.println("arlDatPedEmb: "+arlDatPedEmb);
                objImp30_01=new ZafImp30_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, con, arlDatPedEmb, blnProAutCiePag, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                
                //Muestra ventana con listado de pedidos a cerrar cuando es mas de 1 pedido en el DxP.
                if(!blnProAutCiePag) {
                    objImp30_01.setVisible(true);
                }

                if(objImp30_01.isCiePagLocPro()) {
                    blnRes=true;
                }
            }
            else {
                blnRes=true;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return blnRes;
    }
    


    
    
    

}
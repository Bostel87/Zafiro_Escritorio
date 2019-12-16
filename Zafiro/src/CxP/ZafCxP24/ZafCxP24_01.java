/*
 * ZafCxP24_01.java
 *
 *  Created on 04 de abril del 2018
 */
package CxP.ZafCxP24;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafAsiDia.ZafAsiDia;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import java.math.BigDecimal;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
/**
 *
 * @author sistemas9
 */
public class ZafCxP24_01 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_TIP_TRS=1;
    final int INT_TBL_DAT_COD_EMP=2;
    final int INT_TBL_DAT_BUT_EMP=3;
    final int INT_TBL_DAT_NOM_EMP=4;
    final int INT_TBL_DAT_COD_CTA=5;
    final int INT_TBL_DAT_NUM_CTA=6;
    final int INT_TBL_DAT_BUT_CTA=7;
    final int INT_TBL_DAT_NOM_CTA=8;
    final int INT_TBL_DAT_VAL_TRS=9;
    final int INT_TBL_DAT_BUT_ANE=10;
    final int INT_TBL_DAT_OPC_TRS=11;
    final int INT_TBL_DAT_CUA=12;
    final int INT_TBL_DAT_COD_EMP_REL=13;
    final int INT_TBL_DAT_COD_SEC=14;
    final int INT_TBL_DAT_FIL_PRO=15;
    final int INT_TBL_DAT_FEC_CAR=16;
    final int INT_TBL_DAT_COD_CTA_BAN_EGR=17;
    final int INT_TBL_DAT_NUM_CTA_BAN_EGR=18;
    final int INT_TBL_DAT_BUT_CTA_BAN_EGR=19;
    final int INT_TBL_DAT_COD_BCO_EGR=20;
    final int INT_TBL_DAT_DES_COR_BCO_EGR=21;
    final int INT_TBL_DAT_DES_LAR_BCO_EGR=22;
    final int INT_TBL_DAT_COD_CTA_BAN_ING=23;
    final int INT_TBL_DAT_NUM_CTA_BAN_ING=24;
    final int INT_TBL_DAT_BUT_CTA_BAN_ING=25;
    final int INT_TBL_DAT_COD_BCO_ING=26;
    final int INT_TBL_DAT_DES_COR_BCO_ING=27;
    final int INT_TBL_DAT_DES_LAR_BCO_ING=28;
    final int INT_TBL_DAT_CHK_MOS_PAR_RES=29;
    final int INT_TBL_DAT_MOT_TRS_BAN=30;
    final int INT_TBL_DAT_VAL_CAR=31;
    final int INT_TBL_DAT_AD_VAL=32;
    final int INT_TBL_DAT_FOD_INF=33;
    
    //ArrayList: para consultar Documentos
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceCon=0;       
    
    //ArrayList: Documentos Asociados    
    private ArrayList arlRegDocAso, arlDatDocAso;
    final int INT_ARL_DOC_ASO_COD_EMP_REL=0;
    final int INT_ARL_DOC_ASO_COD_LOC_REL=1;
    final int INT_ARL_DOC_ASO_COD_TIP_DOC_REL=2;
    final int INT_ARL_DOC_ASO_COD_DOC_REL=3;

    //ArrayList: Transferencias
    private ArrayList arlRegDetTrs, arlDatDetTrs;
    final int INT_ARL_DET_TRS_COD_EMP=0;
    final int INT_ARL_DET_TRS_COD_CTA=1;
    final int INT_ARL_DET_TRS_VAL_DEB=2;
    final int INT_ARL_DET_TRS_VAL_HAB=3;
    final int INT_ARL_DET_TRS_EST_REG=4;
    final int INT_ARL_DET_TRS_EST_CON_BAN=5;
    final int INT_ARL_DET_TRS_COD_SEC=6;
    final int INT_ARL_DET_TRS_EST_ELI=7;
    final int INT_ARL_DET_TRS_GLO_DIA=8;
    final int INT_ARL_DET_TRS_REF=9;

    private Vector vecRegDetTrs, vecDatDetTrs;
    private int INT_VEC_TRS_LIN=0;
    private int INT_VEC_TRS_COD_CTA=1;
    private int INT_VEC_TRS_NUM_CTA=2;
    private int INT_VEC_TRS_BUT=3;
    private int INT_VEC_TRS_NOM_CTA=4;
    private int INT_VEC_TRS_DEB=5;
    private int INT_VEC_TRS_HAB=6;
    private int INT_VEC_TRS_REF=7;
    private int INT_VEC_TRS_EST_CON=8;

    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafDatePicker dtpFecDoc;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCodEmp, objTblCelEdiTxtVcoNumCta;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoEmp, vcoCta, vcoNumCtaBanEgr;
    private ZafTblCelRenCbo objTblCelRenCmbBoxTrs;
    private ZafTblCelEdiCbo objTblCelEdiCmbBoxTrs;
    private ZafTblCelEdiButGen objTblCelEdiButGenEmp;
    private ZafTblCelRenBut    objTblCelRenButEmp;
    private ZafTblCelEdiButGen objTblCelEdiButGenNumCta;
    private ZafTblCelRenBut    objTblCelRenButNumCta;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenBut    objTblCelRenButAnx;
    private ZafTblCelEdiButGen objTblCelEdiButGenAnx;
    private ZafTblCelEdiButGen objTblCelEdiButGenNumCtaBanEgr;
    private ZafTblCelRenBut    objTblCelRenButNumCtaBanEgr;
    private ZafTblCelEdiButGen objTblCelEdiButGenNumCtaBanIng;
    private ZafTblCelRenBut    objTblCelRenButNumCtaBanIng;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafCxP24 objCxP24;
    private ZafCxP24_02 objCxP24_02;    
    private ZafCxP24_03 objCxP24_03;
    
    private java.util.Date datFecAux;                      //Auxiliar: Para almacenar fechas.

    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnHayCam;                             //Determina si hay cambios en el formulario.
    
    private int intSig=1;                                  //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private int intCodRegTmp; 
    
    private String strDesCorTipDoc, strDesLarTipDoc;       //Contenido del campo al obtener el foco.
    private String strNumDoc1, strFecDocIni, strEstImpDoc; //Contenido del campo al obtener el foco.
    private String strSQL, strAux;    
    

    /** Crea una nueva instancia de la clase ZafCxP24_01. */
    public ZafCxP24_01(ZafParSis obj){
        try{
            intCodRegTmp=0;
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objCxP24= new ZafCxP24(objParSis);
            
            configurarFrm();
            agregarDocLis();
            arlDatDetTrs=new ArrayList();
            vecDatDetTrs=new Vector();
            arlDatDocAso=new ArrayList();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Crea una nueva instancia de la clase ZafCxP24_01. */
    public ZafCxP24_01(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
    {
        try
        {
            intCodRegTmp=0;
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objCxP24= new ZafCxP24(objParSis);
            
            configurarFrm();
            agregarDocLis();

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);

            consultarReg();
            objTooBar.setVisible(false);

            arlDatDetTrs=new ArrayList();
            vecDatDetTrs=new Vector();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + objCxP24.strVer);
            lblTit.setText(strAux);
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 4, 110, 20);
            
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

            //panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setVisible(false);

            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConCodEmp();

            //configurarVenConCodCta();
            //Configurar los JTables.
            configurarTblDat();

            objTooBar.setVisibleModificar(false);

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
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(34);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_TIP_TRS,"Transacción");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_BUT_EMP,"");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Num.Cta.");
            vecCab.add(INT_TBL_DAT_BUT_CTA,"");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_VAL_TRS,"Valor");
            vecCab.add(INT_TBL_DAT_BUT_ANE,"");
            vecCab.add(INT_TBL_DAT_OPC_TRS,"");
            vecCab.add(INT_TBL_DAT_CUA,"");
            vecCab.add(INT_TBL_DAT_COD_EMP_REL,"");
            vecCab.add(INT_TBL_DAT_COD_SEC,"");
            vecCab.add(INT_TBL_DAT_FIL_PRO,"");

            vecCab.add(INT_TBL_DAT_FEC_CAR,"Fec.Car.");
            vecCab.add(INT_TBL_DAT_COD_CTA_BAN_EGR,"Cód.Cta.Ban.Egr.");
            vecCab.add(INT_TBL_DAT_NUM_CTA_BAN_EGR,"Núm.Cta.Ban.Egr.");
            vecCab.add(INT_TBL_DAT_BUT_CTA_BAN_EGR,"");
            vecCab.add(INT_TBL_DAT_COD_BCO_EGR,"Cód.Ban.Egr.");
            vecCab.add(INT_TBL_DAT_DES_COR_BCO_EGR,"Ali.Nom.Ban.Egr.");
            vecCab.add(INT_TBL_DAT_DES_LAR_BCO_EGR,"Nom.Ban.Egr.");

            vecCab.add(INT_TBL_DAT_COD_CTA_BAN_ING,"Cód.Cta.Ban.Ing.");
            vecCab.add(INT_TBL_DAT_NUM_CTA_BAN_ING,"Núm.Cta.Ban.Ing.");
            vecCab.add(INT_TBL_DAT_BUT_CTA_BAN_ING,"");
            vecCab.add(INT_TBL_DAT_COD_BCO_ING,"Cód.Ban.Ing");
            vecCab.add(INT_TBL_DAT_DES_COR_BCO_ING,"Ali.Ban.Ing");
            vecCab.add(INT_TBL_DAT_DES_LAR_BCO_ING,"Nom.Ban.Ing");

            vecCab.add(INT_TBL_DAT_CHK_MOS_PAR_RES,"Mos.Par.Res.");
            vecCab.add(INT_TBL_DAT_MOT_TRS_BAN,"Mot.Tra./DAU");
            vecCab.add(INT_TBL_DAT_VAL_CAR,"Val.Car.");
            vecCab.add(INT_TBL_DAT_AD_VAL,"Advaloren");
            vecCab.add(INT_TBL_DAT_FOD_INF,"Fodinfa");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_TRS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_CAR, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_AD_VAL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_FOD_INF, objTblMod.INT_COL_DBL, new Integer(0), null);
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
            tcmAux.getColumn(INT_TBL_DAT_TIP_TRS).setPreferredWidth(160);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TRS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_OPC_TRS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CUA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_REL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_SEC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FIL_PRO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FEC_CAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA_BAN_EGR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA_BAN_EGR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA_BAN_EGR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_BCO_EGR).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_BCO_EGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_BCO_EGR).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA_BAN_ING).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA_BAN_ING).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA_BAN_ING).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_BCO_ING).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_BCO_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_BCO_ING).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_CHK_MOS_PAR_RES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_MOT_TRS_BAN).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_AD_VAL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FOD_INF).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_OPC_TRS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CUA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SEC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FIL_PRO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA_BAN_EGR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BCO_EGR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_BCO_EGR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA_BAN_ING, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BCO_ING, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_BCO_ING, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TIP_TRS);
            vecAux.add("" + INT_TBL_DAT_COD_EMP);
            vecAux.add("" + INT_TBL_DAT_BUT_EMP);
            vecAux.add("" + INT_TBL_DAT_NUM_CTA);
            vecAux.add("" + INT_TBL_DAT_BUT_CTA);
            vecAux.add("" + INT_TBL_DAT_NOM_CTA);
            vecAux.add("" + INT_TBL_DAT_VAL_TRS);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE);
            vecAux.add("" + INT_TBL_DAT_FEC_CAR);
            vecAux.add("" + INT_TBL_DAT_BUT_CTA_BAN_EGR);
            vecAux.add("" + INT_TBL_DAT_BUT_CTA_BAN_ING);
            vecAux.add("" + INT_TBL_DAT_CHK_MOS_PAR_RES);
            vecAux.add("" + INT_TBL_DAT_MOT_TRS_BAN);
            vecAux.add("" + INT_TBL_DAT_VAL_CAR);
            vecAux.add("" + INT_TBL_DAT_AD_VAL);
            vecAux.add("" + INT_TBL_DAT_FOD_INF);

            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TRS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_AD_VAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FOD_INF).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //para editor de codigo de empresa
            //Configurar JTable: Editor de celdas.
            int intColVenEmp[]=new int[2];
            intColVenEmp[0]=1;
            intColVenEmp[1]=2;
            int intColTblEmp[]=new int[2];
            intColTblEmp[0]=INT_TBL_DAT_COD_EMP;
            intColTblEmp[1]=INT_TBL_DAT_NOM_EMP;

            objTblCelEdiTxtVcoCodEmp=new ZafTblCelEdiTxtVco(tblDat, vcoEmp, intColVenEmp, intColTblEmp);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellEditor(objTblCelEdiTxtVcoCodEmp);
            objTblCelEdiTxtVcoCodEmp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstValAplReg="";
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoEmp.setCampoBusqueda(0);
                    vcoEmp.setCriterio1(7);
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoCodEmp.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoEmp.getValueAt(1), tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP);
                        objTblMod.setValueAt(vcoEmp.getValueAt(2), tblDat.getSelectedRow(), INT_TBL_DAT_NOM_EMP);
                    }
                }
            });

            //PARA EL BOTON DE empresa
            objTblCelRenButEmp=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_EMP).setCellRenderer(objTblCelRenButEmp);
            objTblCelEdiButGenEmp=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_EMP).setCellEditor(objTblCelEdiButGenEmp);
            objTblCelEdiButGenEmp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    mostrarVenConEmp(0);
                }
            });

            objTblCelRenButEmp=null;
            objTblCelEdiButGenEmp=null;

            //para editor de numero de cuenta
            //Configurar JTable: Editor de celdas.
            int intColVenCta[]=new int[3];
            intColVenCta[0]=1;
            intColVenCta[1]=2;
            intColVenCta[2]=3;
            int intColTblCta[]=new int[3];
            intColTblCta[0]=INT_TBL_DAT_COD_CTA;
            intColTblCta[1]=INT_TBL_DAT_NUM_CTA;
            intColTblCta[2]=INT_TBL_DAT_NOM_CTA;
            
            //PARA EL BOTON DE numero de cuenta
            objTblCelRenButNumCta=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_CTA).setCellRenderer(objTblCelRenButNumCta);
            objTblCelEdiButGenNumCta=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_CTA).setCellEditor(objTblCelEdiButGenNumCta);
            objTblCelEdiButGenNumCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strCodEmpSel="";
                String strSenSQLAdi="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strCodEmpSel=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString();
                    configurarVenConCodCta(strCodEmpSel);
                    if( ! strCodEmpSel.equals("")){
                        vcoCta.setCondicionesSQL(strSenSQLAdi);
                        mostrarVenConCta(0);
                    }
                    else
                        mostrarMsgInf("Debe ingresar una empresa antes de seleccionar la cuenta.");
                }
            });

            objTblCelEdiTxtVcoNumCta=new ZafTblCelEdiTxtVco(tblDat, vcoCta, intColVenCta, intColTblCta);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellEditor(objTblCelEdiTxtVcoNumCta);
            objTblCelEdiTxtVcoNumCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.setCriterio1(7);
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoNumCta.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoCta.getValueAt(1), tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA);
                        objTblMod.setValueAt(vcoCta.getValueAt(2), tblDat.getSelectedRow(), INT_TBL_DAT_NUM_CTA);
                        objTblMod.setValueAt(vcoCta.getValueAt(3), tblDat.getSelectedRow(), INT_TBL_DAT_NOM_CTA);
                    }
                }
            });           

            objTblCelRenCmbBoxTrs = new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_TIP_TRS).setCellRenderer(objTblCelRenCmbBoxTrs);
            objTblCelRenCmbBoxTrs = null;

            objTblCelEdiCmbBoxTrs = new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TIP_TRS).setCellEditor(objTblCelEdiCmbBoxTrs);
            objTblCelEdiCmbBoxTrs.addItem("Transferencia bancaria al exterior");
            objTblCelEdiCmbBoxTrs.addItem("Transferencia arancelaria");
            objTblCelEdiCmbBoxTrs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if( (objTblCelEdiCmbBoxTrs.getSelectedIndex()==0) || (objTblCelEdiCmbBoxTrs.getSelectedIndex()==1)   ){
                        if(objTblCelEdiCmbBoxTrs.getSelectedIndex()==0)
                            objTblMod.setValueAt("T", tblDat.getSelectedRow(), INT_TBL_DAT_OPC_TRS);
                        if(objTblCelEdiCmbBoxTrs.getSelectedIndex()==1)
                            objTblMod.setValueAt("A", tblDat.getSelectedRow(), INT_TBL_DAT_OPC_TRS);

                        objTblMod.setValueAt("" + (intCodRegTmp++), tblDat.getSelectedRow(), INT_TBL_DAT_COD_SEC);

                        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                        objTblMod.removeEmptyRows();
                        objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                    }
                }
            });
            
            //para el campo de valor
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TRS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_AD_VAL).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_FOD_INF).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //PARA EL BOTON DE ANEXO
            objTblCelRenButAnx=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE).setCellRenderer(objTblCelRenButAnx);
            objTblCelEdiButGenAnx=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE).setCellEditor(objTblCelEdiButGenAnx);
            objTblCelEdiButGenAnx.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                String strOpcTrsSel="";
                int intFilSelVenCon[];
                BigDecimal bdeArlValDeb=new BigDecimal("0");
                String strArlCodCta="", strArlNumCta="", strArlNomCta="";
                String strLinPro="";
                String strArlSec="";
                String strIsCtaBan="";
                String strEstEli="";
                String strCodSec="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(objTooBar.getEstado()=='n'){
                        strOpcTrsSel=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_OPC_TRS)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_OPC_TRS).toString();
                        if(  (strOpcTrsSel.equals("T"))  || (strOpcTrsSel.equals("A"))  ){
                            objTblCelEdiButGenAnx.setCancelarEdicion(false);
                        }
                        else{
                            objTblCelEdiButGenAnx.setCancelarEdicion(true);
                        }
                    }
                    else{
                        objTblCelEdiButGenAnx.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTooBar.getEstado()=='n'){
                        if(   (strOpcTrsSel.equals("T")) || (strOpcTrsSel.equals("A"))    ){
                            if (tblDat.getSelectedColumn()==INT_TBL_DAT_BUT_ANE){
                                    int intCodEmp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP)==null?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString());
                                    mostrarVentanaTransferenciaExterior(intFilSel, intCodEmp, strOpcTrsSel);
                                    String strCodCta=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_CTA)==null?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_CTA).toString();
                                    String strNumCta=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUM_CTA)==null?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUM_CTA).toString();
                                    String strNomCta=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_CTA)==null?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_CTA).toString();
                                    BigDecimal bdeValTrs=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_VAL_TRS)==null?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_VAL_TRS).toString());
                                    strCodSec=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_SEC)==null?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_SEC).toString();
                                    objCxP24_02.setValorCuentaBancaria(bdeValTrs);
                                    strLinPro=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FIL_PRO)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FIL_PRO).toString();
                                    if(strLinPro.equals("")){
                                        objCxP24_02.setDetalleTransferencia(strCodCta, strNumCta, strNomCta);
                                    }
                                    else{
                                        objCxP24_02.setDetalleTransferencia(strCodCta, strNumCta, strNomCta);
                                        for(int g=0; g<arlDatDetTrs.size(); g++){
                                            strArlCodCta=objUti.getStringValueAt(arlDatDetTrs, g, INT_ARL_DET_TRS_COD_CTA);
                                            strArlSec=objUti.getStringValueAt(arlDatDetTrs, g, INT_ARL_DET_TRS_COD_SEC);
                                            bdeArlValDeb=objUti.getBigDecimalValueAt(arlDatDetTrs, g, INT_ARL_DET_TRS_VAL_DEB);
                                            strIsCtaBan=objUti.getStringValueAt(arlDatDetTrs, g, INT_ARL_DET_TRS_EST_REG);
                                            strEstEli=objUti.getStringValueAt(arlDatDetTrs, g, INT_ARL_DET_TRS_EST_ELI);

                                            if( strCodSec.equals(strArlSec)){
                                                if( ! strIsCtaBan.equals("B")){
                                                    if( ! strEstEli.equals("E")){
                                                        strArlNumCta="" + getNumeroCuenta(intCodEmp, strArlCodCta);
                                                        strArlNomCta="" + getNombreCuenta(intCodEmp, strArlCodCta);

                                                        objCxP24_02.setDetalleTransferenciaCuentaContrapartida(strArlCodCta, strArlNumCta, strArlNomCta, bdeArlValDeb);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                objCxP24_02.setDetalleTransferenciaVector();
                                objCxP24_02.setVisible(true);
                            }
                            if (objCxP24_02.getSelectedButton()==objCxP24_02.INT_BUT_ACE){
                                intFilSelVenCon=objCxP24_02.getFilasSeleccionadas();
                                for (int i=0; i<intFilSelVenCon.length; i++){
                                    if(i==0){
                                        for(int g=0; g<arlDatDetTrs.size(); g++){
                                            strArlSec=objUti.getStringValueAt(arlDatDetTrs, g, INT_ARL_DET_TRS_COD_SEC);
                                            if( strCodSec.equals(strArlSec)){
                                                objUti.setStringValueAt(arlDatDetTrs, g, INT_ARL_DET_TRS_EST_ELI, "E");
                                            }
                                        }
                                    }
                                    arlRegDetTrs=new ArrayList();
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_COD_EMP,     "" + (objUti.getStringValueAt((objCxP24_02.getDetalleDiarioTransferenciaCon02_02()), i, 0)     ));
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_COD_CTA,     "" + (objUti.getStringValueAt((objCxP24_02.getDetalleDiarioTransferenciaCon02_02()), i, 1)     ));
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_VAL_DEB,     "" + (objUti.getStringValueAt((objCxP24_02.getDetalleDiarioTransferenciaCon02_02()), i, 2)     ));
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_VAL_HAB,     "" + (objUti.getStringValueAt((objCxP24_02.getDetalleDiarioTransferenciaCon02_02()), i, 3)     ));
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_EST_REG,     "" + (objUti.getStringValueAt((objCxP24_02.getDetalleDiarioTransferenciaCon02_02()), i, 4)     ));
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_EST_CON_BAN, "" + (objUti.getStringValueAt((objCxP24_02.getDetalleDiarioTransferenciaCon02_02()), i, 5)     ));
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_COD_SEC,     "" + strCodSec);
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_EST_ELI,     "");
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_GLO_DIA,     ""+ objCxP24_02.getGlosaTransferenciaExterior());
                                    arlRegDetTrs.add(INT_ARL_DET_TRS_REF,     "" + (objUti.getStringValueAt((objCxP24_02.getDetalleDiarioTransferenciaCon02_02()), i, 6)     ));
                                    arlDatDetTrs.add(arlRegDetTrs);
                                }
                                objTblMod.setValueAt("P", intFilSel, INT_TBL_DAT_FIL_PRO);
                                tblDat.requestFocus();
                                objTblMod.removeEmptyRows();
                            }
                        }
                    }
                    else{
                        if( ! mostrarVentanaDocumentosAsociados(intFilSel)){
                            mostrarMsgInf("<HTML>Ocurrio un problema y no se pudieron cargar los datos.</HTML>");
                        }
                    }
                }
            });

            //datos para las cartas
            ZafTblHeaGrp objTblHeaGrpDatCar=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpDatCar.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpDatCar=null;
            objTblHeaColGrpDatCar=new ZafTblHeaColGrp("Datos de la carta");
            objTblHeaColGrpDatCar.setHeight(16);
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_FEC_CAR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_COD_CTA_BAN_EGR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_NUM_CTA_BAN_EGR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_BUT_CTA_BAN_EGR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_COD_BCO_EGR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_BCO_EGR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_BCO_EGR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_COD_CTA_BAN_ING));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_NUM_CTA_BAN_ING));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_BUT_CTA_BAN_ING));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_COD_BCO_ING));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_BCO_ING));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_BCO_ING));

            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_CHK_MOS_PAR_RES));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_MOT_TRS_BAN));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_VAL_CAR));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_AD_VAL));
            objTblHeaColGrpDatCar.add(tcmAux.getColumn(INT_TBL_DAT_FOD_INF));
            objTblHeaGrpDatCar.addColumnGroup(objTblHeaColGrpDatCar);
            
            //PARA EL BOTON DE numero de cuenta
            objTblCelRenButNumCtaBanEgr=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_CTA_BAN_EGR).setCellRenderer(objTblCelRenButNumCtaBanEgr);
            objTblCelEdiButGenNumCtaBanEgr=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_CTA_BAN_EGR).setCellEditor(objTblCelEdiButGenNumCtaBanEgr);
            objTblCelEdiButGenNumCtaBanEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strCodEmpSel="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strCodEmpSel=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString();
                    configurarVenConNumCtaBanEgr(strCodEmpSel);
                    if( ! strCodEmpSel.equals("")) {
                        mostrarVenConNumCtaBanEgr(0, "E");
                    }
                    else
                        mostrarMsgInf("Debe ingresar una empresa antes de seleccionar la cuenta.");
                }
            });

            //PARA EL BOTON DE numero de cuenta
            objTblCelRenButNumCtaBanIng=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_CTA_BAN_ING).setCellRenderer(objTblCelRenButNumCtaBanIng);
            objTblCelEdiButGenNumCtaBanIng=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_CTA_BAN_ING).setCellEditor(objTblCelEdiButGenNumCtaBanIng);
            objTblCelEdiButGenNumCtaBanIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strCodEmpSel="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strCodEmpSel=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString();
                    configurarVenConNumCtaBanEgr(strCodEmpSel);
                    if( ! strCodEmpSel.equals("")) {
                        mostrarVenConNumCtaBanEgr(0, "I");
                    }
                    else
                        mostrarMsgInf("Debe ingresar una empresa antes de seleccionar la cuenta.");
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_MOS_PAR_RES).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_MOS_PAR_RES).setCellEditor(objTblCelEdiChk);

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                 
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
                case INT_TBL_DAT_TIP_TRS:
                    strMsg="Transacción";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de la cuenta";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_VAL_TRS:
                    strMsg="Valor";
                    break;
                case INT_TBL_DAT_BUT_ANE:
                    strMsg="Muestra el listado de documentos generados";
                    break;
                case INT_TBL_DAT_FEC_CAR:
                    strMsg="Fecha de la carta";
                    break;
                case INT_TBL_DAT_COD_CTA_BAN_EGR:
                    strMsg="Código de cuenta bancaria(Egreso)";
                    break;
                case INT_TBL_DAT_NUM_CTA_BAN_EGR:
                    strMsg="Número de cuenta bancaria(Egreso)";
                    break;
                case INT_TBL_DAT_COD_BCO_EGR:
                    strMsg="Nombre del banco(Egreso)";
                    break;
                case INT_TBL_DAT_DES_COR_BCO_EGR:
                    strMsg="Nombre del banco(Egreso)";
                    break;
                case INT_TBL_DAT_DES_LAR_BCO_EGR:
                    strMsg="Nombre del banco(Egreso)";
                    break;
                case INT_TBL_DAT_COD_CTA_BAN_ING:
                    strMsg="Código de cuenta bancaria(Ingreso)";
                    break;
                case INT_TBL_DAT_NUM_CTA_BAN_ING:
                    strMsg="Número de cuenta bancaria(Ingreso)";
                    break;
                case INT_TBL_DAT_COD_BCO_ING:
                    strMsg="Nombre del banco(Ingreso)";
                    break;
                case INT_TBL_DAT_DES_COR_BCO_ING:
                    strMsg="Nombre del banco(Ingreso)";
                    break;
                case INT_TBL_DAT_DES_LAR_BCO_ING:
                    strMsg="Nombre del banco(Ingreso)";
                    break;
                case INT_TBL_DAT_CHK_MOS_PAR_RES:
                    strMsg="Mostrar párrafo de responsabilidad";
                    break;
                case INT_TBL_DAT_MOT_TRS_BAN:
                    strMsg="Motivo de la transferencia bancaria";
                    break;
                case INT_TBL_DAT_VAL_CAR:
                    strMsg="Valor de la carta";
                    break;
                case INT_TBL_DAT_AD_VAL:
                    strMsg="Advaloren";
                    break;
                case INT_TBL_DAT_FOD_INF:
                    strMsg="Fodinfa";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private void mostrarVentanaTransferenciaExterior(int fila, int codigoEmpresa, String strOpcTrsSel){
        objCxP24_02=new ZafCxP24_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, codigoEmpresa, objParSis.getCodigoLocal(), strOpcTrsSel);
    }    
    
    private boolean mostrarVentanaDocumentosAsociados(int fila){
        boolean blnRes=true;
        int intCodEmpRel=-1, intCodLocRel=-1, intCodTipDocRel=-1, intCodDocRel=-1, intCodRegDet=-1;
        try{
            intCodEmpRel=objParSis.getCodigoEmpresa();
            intCodLocRel=objParSis.getCodigoLocal();
            intCodTipDocRel=Integer.parseInt(txtCodTipDoc.getText());
            intCodDocRel=Integer.parseInt(txtCodDoc.getText());
            intCodRegDet=Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_SEC).toString());
            objCxP24_03=new ZafCxP24_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis,intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel, intCodRegDet, objTooBar.getEstado());
            objCxP24_03.setVisible(true);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
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
            arlCam.add("tblActNumDoc");
            arlCam.add("a2.co_grpTipDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Ref.Tbl.Act.");
            arlAli.add("Cod.Grp.Tbl.Tip.Doc.");
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
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.tx_natDoc, a2.co_grpTipDoc ";
                strSQL+="      , CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+="      , CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc";
                strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.tx_natDoc, a2.co_grpTipDoc";
                strSQL+="      , CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+="      , CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 INNER JOIN (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=7;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empresas".
     */
    private boolean configurarVenConCodEmp(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("250");
            //Armar la sentencia SQL.

            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E','I') AND a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" ORDER BY a1.co_emp";
            }
            else{
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp";
                strSQL+=" WHERE a2.co_usr=" + objParSis.getCodigoUsuario() + " AND a1.st_reg NOT IN('E','I')";
                strSQL+=" AND a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" ORDER BY a1.co_emp";
            }

            //Ocultar columnas.
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Cuentas Contables".
     */
    private boolean configurarVenConCodCta(String strCodEmpSel){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codcta");
            arlCam.add("a1.tx_deslar");
            arlCam.add("a2.nd_salcta");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Número");
            arlAli.add("Nombre");
            arlAli.add("Saldo Bancario");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("250");
            arlAncCol.add("80");
            //Armar la sentencia SQL.

            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codcta, a1.tx_deslar, SUM(a2.nd_salcta) AS nd_salcta";
            strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN tbm_salCta AS a2";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
            strSQL+=" WHERE a1.st_ctaBan='S'";
            strSQL+=" AND a1.st_reg NOT IN('E','I')";
            strSQL+=" AND a1.co_emp=" + strCodEmpSel + "";
            strSQL+=" GROUP BY a1.co_emp, a1.co_cta, a1.tx_codcta, a1.tx_deslar";
            strSQL+=" ORDER BY a1.tx_codcta";
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas bancarias", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
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
     * mostrar los "Cuentas Contables".
     */
    private boolean configurarVenConNumCtaBanEgr(String strCodEmpSel){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_ctaban");
            arlCam.add("a1.tx_numctaban");
            arlCam.add("a1.co_ban");            
            arlCam.add("a2.tx_desCorBco");
            arlCam.add("a2.tx_desLarBco");
            arlCam.add("co_prvLocExp");
            arlCam.add("tx_nomPrvLocExp");

            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Cta.Ban.");
            arlAli.add("Núm.Cta.Bco.");
            arlAli.add("Cód.Cta.Bco.");
            arlAli.add("Banco");
            arlAli.add("Nombre de banco");
            arlAli.add("Cód.Prv.");
            arlAli.add("Proveedor");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("0");
            arlAncCol.add("0");
            arlAncCol.add("80");
            arlAncCol.add("0");
            arlAncCol.add("60");
            arlAncCol.add("150");
            arlAncCol.add("56");
            arlAncCol.add("160");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.co_ctaban, a1.tx_numctaban, a1.co_ban, a2.tx_desCor AS tx_desCorBco, a2.tx_desLar AS tx_desLarBco";
            strSQL+="      , CASE WHEN a3.co_cli IS NOT NULL THEN a3.co_cli ELSE a4.co_exp END AS co_prvLocExp";
            strSQL+="      , CASE WHEN a3.tx_nom IS NOT NULL THEN a3.tx_nom ELSE a4.tx_nom END AS tx_nomPrvLocExp";
            strSQL+=" FROM tbm_ctaban AS a1 INNER JOIN tbm_var AS a2 ON (a1.co_ban=a2.co_reg)";
            strSQL+=" LEFT OUTER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaPrvLoc=a3.co_cli AND a3.st_reg  NOT IN('E','I')  )";
            strSQL+=" LEFT OUTER JOIN tbm_expImp AS a4 ON (a1.co_ctaExp=a4.co_exp AND a4.st_reg  NOT IN('E','I')  )";
            strSQL+=" AND a2.co_grp=8";
            strSQL+=" WHERE a1.co_emp=" + strCodEmpSel + "";
            strSQL+=" AND a1.st_reg  NOT IN('E','I') AND a2.st_reg  NOT IN('E','I')";
            vcoNumCtaBanEgr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas bancarias", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoNumCtaBanEgr.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
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
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(1);
                    vcoEmp.show();
                    
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        objTblMod.setValueAt(vcoEmp.getValueAt(1), tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP);
                        objTblMod.setValueAt(vcoEmp.getValueAt(2), tblDat.getSelectedRow(), INT_TBL_DAT_NOM_EMP);
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
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCta(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.show();
                    if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE){
                        objTblMod.setValueAt(vcoCta.getValueAt(1), tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA);
                        objTblMod.setValueAt(vcoCta.getValueAt(2), tblDat.getSelectedRow(), INT_TBL_DAT_NUM_CTA);
                        objTblMod.setValueAt(vcoCta.getValueAt(3), tblDat.getSelectedRow(), INT_TBL_DAT_NOM_CTA);
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
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a2.ne_ultDoc AS ne_ultDocGrp, a1.tx_natDoc,a2.co_grpTipDoc";
                    strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
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
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a2.ne_ultDoc AS ne_ultDocGrp, a1.tx_natDoc,a2.co_grpTipDoc";
                    strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
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
                    String strNumDoc=rst.getObject("ne_ultDocGrp")==null?"":rst.getString("ne_ultDocGrp");
                    if(strNumDoc.equals("")){
                        txtNumDoc1.setText("" + (rst.getInt("ne_ultDoc")+1));
                    }
                    else{
                        txtNumDoc1.setText("" + (rst.getInt("ne_ultDocGrp")+1));
                    }
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
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)      {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
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
                        actualizarGlo();
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
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
                case 2: //Básqueda directa por "Descripcián larga".
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
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConNumCtaBanEgr(int intTipBus, String naturaleza){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoNumCtaBanEgr.setCampoBusqueda(1);
                    vcoNumCtaBanEgr.show();
                    if (vcoNumCtaBanEgr.getSelectedButton()==vcoNumCtaBanEgr.INT_BUT_ACE){
                        if(naturaleza.equals("E")){
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(2), tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA_BAN_EGR);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(3), tblDat.getSelectedRow(), INT_TBL_DAT_NUM_CTA_BAN_EGR);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(4), tblDat.getSelectedRow(), INT_TBL_DAT_COD_BCO_EGR);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(5), tblDat.getSelectedRow(), INT_TBL_DAT_DES_COR_BCO_EGR);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(6), tblDat.getSelectedRow(), INT_TBL_DAT_DES_LAR_BCO_EGR);
                        }
                        else if(naturaleza.equals("I")){
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(2), tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA_BAN_ING);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(3), tblDat.getSelectedRow(), INT_TBL_DAT_NUM_CTA_BAN_ING);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(4), tblDat.getSelectedRow(), INT_TBL_DAT_COD_BCO_ING);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(5), tblDat.getSelectedRow(), INT_TBL_DAT_DES_COR_BCO_ING);
                            objTblMod.setValueAt(vcoNumCtaBanEgr.getValueAt(6), tblDat.getSelectedRow(), INT_TBL_DAT_DES_LAR_BCO_ING);
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
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtValCarEur = new javax.swing.JCheckBox();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
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

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 70));
        panGenCab.setRequestFocusEnabled(false);
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(82, 4, 32, 20);

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
        txtDesCorTipDoc.setBounds(114, 4, 60, 20);
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(174, 4, 262, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(436, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(458, 4, 112, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 120, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(114, 24, 80, 20);

        lblNumDoc1.setText("Número aterno 1:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(458, 24, 100, 20);

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
        txtNumDoc1.setBounds(570, 24, 110, 20);

        txtValCarEur.setText("Valor de la carta en Euros");
        txtValCarEur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValCarEurActionPerformed(evt);
            }
        });
        panGenCab.add(txtValCarEur);
        txtValCarEur.setBounds(0, 46, 240, 14);

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

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
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
                dispose();
            }
        }
        catch (Exception e)
        {            
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtNumDoc1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNumDoc1.getText().equalsIgnoreCase(strNumDoc1)) {
            actualizarGlo();
        }
}//GEN-LAST:event_txtNumDoc1FocusLost

    private void txtNumDoc1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusGained
        strNumDoc1=txtNumDoc1.getText();
        txtNumDoc1.selectAll();
}//GEN-LAST:event_txtNumDoc1FocusGained

    private void txtValCarEurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValCarEurActionPerformed
        if(txtValCarEur.isSelected())
            txaObs2.setText("EUROS");
        else
            txaObs2.setText("USD");
        
    }//GEN-LAST:event_txtValCarEurActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
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
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc1;
    private javax.swing.JCheckBox txtValCarEur;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa utilizar
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
            
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
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
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc1.setText("");
            objTblMod.removeAllRows();
            objAsiDia.inicializar();
            txaObs1.setText("");
            txaObs2.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene la descripcián larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacáa.
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
                    estado="Pendiente de impresián";
                    break;
                case 'C':
                    estado="Pendiente confirmacián de inventario";
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
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()
    {
        int i;
        strAux="";
        strAux+=txtDesCorTipDoc.getText() + ": " + txtNumDoc1.getText();
        strAux+="; Transacción/Empresa/Cuenta/Valor: ";
        for (i=0; i<objTblMod.getRowCountTrue(); i++){
            strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_TRS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_TRS).toString() + " / ";
            strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_NOM_EMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NOM_EMP).toString() + " / ";
            strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_NOM_CTA)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NOM_CTA).toString() + " / ";
            strAux+=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_TRS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_TRS).toString() + ", ";
        }
        objAsiDia.setGlosa(strAux);
    }
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc1.getDocument().addDocumentListener(objDocLis);
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
                        if(objTooBar.beforeInsertar()){
                            blnRes=objTooBar.insertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            blnRes=objTooBar.modificar();
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
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegacián
     * que permiten desplazarse al primero, anterior, siguiente y áltimo registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }
        
        public void clickInicio() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam || objAsiDia.isDiarioModificado()) {
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }         
        
        public void clickAnterior() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ( blnHayCam || objAsiDia.isDiarioModificado() ){
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }              
        }

        public void clickSiguiente() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado()){
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }  

        public void clickFin() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam || objAsiDia.isDiarioModificado() ) {
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                                inactivarCampos();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                            inactivarCampos();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        } 
        
        public void clickConsultar() 
        {
            cargarDetReg();
            txtDesCorTipDoc.requestFocus();
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
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objAsiDia.setEditable(true);
                txtNumDoc1.selectAll();
                txtNumDoc1.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;                
                dtpFecDoc.setEnabled(true);
                txtNumDoc1.setEnabled(false);
                intCodRegTmp=1;
                arlDatDetTrs.clear();
                txaObs2.setText("USD");
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar(){            
        }
        
        public void clickEliminar()
        {
            cargarDetReg();
        }
        
        public void clickAnular()
        {
            cargarDetReg();
        }

        public void clickImprimir(){
        }
        
        public void clickVisPreliminar() {
        }        
        
        public void clickCancelar() {
        }

        public void clickAceptar()
        {
        }            

        public boolean consultar() 
        {
            consultarReg();
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
            return true;
        }
        
        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceCon<arlDatCon.size()-1){
                    intIndiceCon++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                  
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                return true;
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
        
        public boolean imprimir(){
            return true;            
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
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }        
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeInsertar(){
            boolean blnRes=true;
            if (!isCamVal())
                return false;
            return blnRes;
        }        

        public boolean beforeModificar()
        {
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

        public boolean beforeImprimir(){
            boolean blnRes=true;
            return blnRes;
        }

        public boolean beforeVistaPreliminar()
        {
            boolean blnRes=true;
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
        
        public boolean afterInsertar()
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            //Configurar JFrame de acuerdo al estado del registro.
            objTooBar.setEstado('w');
            consultarReg();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            return true;
        }

        public boolean afterModificar()
        {
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
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=getTipModFecUsr();
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
        
        

        //Validar que el "Cádigo alterno" no se repita.
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

        if( ! validaTransferencia()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existe alguna transferencia ingresada cuyo asiento de diario está incompleto.<BR>Verifique los datos ingresados y vuelva a intentarlo.</HTML>");
            return false;
        }
        if( ! validaValor()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Alguno de los registros no tiene el valor asignado.<BR>Ingrese un valor y vuelva a intentarlo.</HTML>");
            return false;
        }

        return true;
    }
    
    private boolean validaValor(){
        boolean blnRes=true;
        BigDecimal bdeVal=new BigDecimal("0");
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeVal=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_TRS)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_TRS).toString());
                if(bdeVal.compareTo(new BigDecimal("0"))==0){
                    blnRes=false;
                    break;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }  

    private boolean validaTransferencia(){
        boolean blnRes=true;
        String strOpcTrsVal="";
        String strTrsPro="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strOpcTrsVal=objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS).toString();
                if(   (strOpcTrsVal.equals("T")) || (strOpcTrsVal.equals("A"))  ){
                    strTrsPro=objTblMod.getValueAt(i, INT_TBL_DAT_FIL_PRO)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_FIL_PRO).toString();
                    if( ! strTrsPro.equals("P")){
                        blnRes=false;
                        break;
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabOtrMovBan AS a1";
                strSQL+=" INNER JOIN tbm_detOtrMovBan AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc)";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                strSQL+=" WHERE a3.tx_tiptra IN ('T', 'A')";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();

                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux + "";
                strAux=txtNumDoc1.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC,rst.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_doc"));
                    arlDatCon.add(arlRegCon);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
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
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
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
                    if(cargarDocumentosAsociados()){
                        blnRes=true;
                        objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                    }
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
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc";
                strSQL+="      , a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+=" FROM tbm_cabOtrMovBan AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
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
                    
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
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
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );   
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
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
        boolean blnRes=true;
        String strTipTrs="";
        String strFecCar="";
        try
        {
            objTblMod.removeAllRows();
            if( ! txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.tx_tiptra, a2.co_emptra";
                    strSQL+="      , a2.co_ctatra, a2.nd_valtra, a2.st_regrep, a3.co_cta, a3.tx_codCta, a3.tx_desLar AS tx_nomCta";
                    strSQL+="      , a4.tx_nom AS tx_nomEmp, a2.co_reg, b3.fe_car, b3.co_ctabanegr, b3.co_ctabaning, b3.nd_valtra AS nd_valtraCar";
                    strSQL+="      , b3.nd_adv, b3.nd_fod, b3.tx_valtrapal AS tx_valtrapalCar, b3.st_mosparresp, b3.tx_mottra";
                    strSQL+="      , b3.tx_ejeasi, b3.st_fircon, b3.tx_firaut1, b3.tx_carfiraut1, b3.tx_firaut2,        b3.tx_carfiraut2";
                    strSQL+="      , b4.co_ban AS co_banEgr, b4.tx_numctaban AS tx_numctabanEgr, b5.tx_desCor AS tx_desCorBcoEgr, b5.tx_desLar AS tx_desLarBcoEgr";
                    strSQL+="      , b6.co_ban AS co_banIng, b6.tx_numctaban AS tx_numctabanIng, b7.tx_desCor AS tx_desCorBcoIng, b7.tx_desLar AS tx_desLarBcoIng";
                    strSQL+=" FROM tbm_cabOtrMovBan AS a1  ";
                    strSQL+=" INNER JOIN ( tbm_detOtrMovBan AS a2 LEFT OUTER JOIN tbm_docgenotrmovban AS b2";
                    strSQL+="              ON a2.co_emp=b2.co_emp AND a2.co_loc=b2.co_loc AND a2.co_tipDoc=b2.co_tipDoc AND a2.co_doc=b2.co_doc AND a2.co_reg=b2.co_reg";     
                    strSQL+="              LEFT OUTER JOIN ( ( tbm_cartrabandia AS b3 INNER JOIN tbm_ctaban AS b4 ON b3.co_emp=b4.co_emp AND b3.co_ctaBanEgr=b4.co_ctaban";
                    strSQL+="                                  LEFT OUTER JOIN tbm_var AS b5 ON b4.co_ban=b5.co_reg )";
                    strSQL+="                                INNER JOIN tbm_ctaban AS b6 ON b3.co_emp=b6.co_emp AND b3.co_ctaBanIng=b6.co_ctaban";
                    strSQL+=" 				     LEFT OUTER JOIN tbm_var AS b7 ON b6.co_ban=b7.co_reg";
                    strSQL+=" 		   ) ON b2.co_empRelCabDia=b3.co_emp AND b2.co_locRelCabDia=b3.co_loc AND b2.co_tipDocRelCabDia=b3.co_tipDoc AND b2.co_docRelCabDia=b3.co_dia";
                    strSQL+=" ) ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbm_plaCta AS a3 ON a2.co_empTra=a3.co_emp AND a2.co_ctaTra=a3.co_cta";
                    strSQL+=" INNER JOIN tbm_emp AS a4 ON a2.co_empTra=a4.co_emp";
                    strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a2.tx_tiptra IN ('T', 'A')";
                    strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,         "");
                        strTipTrs=rst.getString("tx_tiptra");
                        vecReg.add(INT_TBL_DAT_TIP_TRS,     "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,     "" + rst.getString("co_empTra"));
                        vecReg.add(INT_TBL_DAT_BUT_EMP,     "");
                        vecReg.add(INT_TBL_DAT_NOM_EMP,     "" + rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_COD_CTA,     "" + rst.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,     "" + rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_DAT_BUT_CTA,     "");
                        vecReg.add(INT_TBL_DAT_NOM_CTA,     "" + rst.getString("tx_nomCta"));
                        vecReg.add(INT_TBL_DAT_VAL_TRS,     "" + rst.getString("nd_valtra"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE,     "");
                        vecReg.add(INT_TBL_DAT_OPC_TRS,     "");
                        vecReg.add(INT_TBL_DAT_CUA,         "");
                        vecReg.add(INT_TBL_DAT_COD_EMP_REL, "");
                        vecReg.add(INT_TBL_DAT_COD_SEC,     "" + rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_FIL_PRO,     "");
                        strFecCar=rst.getObject("fe_car")==null?"":rst.getString("fe_car");
                        if(strFecCar.equals(""))
                            vecReg.add(INT_TBL_DAT_FEC_CAR,     "");
                        else
                            vecReg.add(INT_TBL_DAT_FEC_CAR,     "" + objUti.formatearFecha(strFecCar, "yyyy-MM-dd", objParSis.getFormatoFecha()));
                        vecReg.add(INT_TBL_DAT_COD_CTA_BAN_EGR,     "" + rst.getObject("co_ctabanegr")==null?"":rst.getString("co_ctabanegr"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA_BAN_EGR,     "" + rst.getObject("tx_numctabanEgr")==null?"":rst.getString("tx_numctabanEgr"));
                        vecReg.add(INT_TBL_DAT_BUT_CTA_BAN_EGR,     "");
                        vecReg.add(INT_TBL_DAT_COD_BCO_EGR,         "" + rst.getObject("co_banEgr")==null?"":rst.getString("co_banEgr"));
                        vecReg.add(INT_TBL_DAT_DES_COR_BCO_EGR,     "" + rst.getObject("tx_desCorBcoEgr")==null?"":rst.getString("tx_desCorBcoEgr"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_BCO_EGR,     "" + rst.getObject("tx_desLarBcoEgr")==null?"":rst.getString("tx_desLarBcoEgr"));
                        vecReg.add(INT_TBL_DAT_COD_CTA_BAN_ING,     "" + rst.getObject("co_ctabaning")==null?"":rst.getString("co_ctabaning"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA_BAN_ING,     "" + rst.getObject("tx_numctabanIng")==null?"":rst.getString("tx_numctabanIng"));
                        vecReg.add(INT_TBL_DAT_BUT_CTA_BAN_ING,     "");
                        vecReg.add(INT_TBL_DAT_COD_BCO_ING,         "" + rst.getObject("co_banIng")==null?"":rst.getString("co_banIng"));
                        vecReg.add(INT_TBL_DAT_DES_COR_BCO_ING,     "" + rst.getObject("tx_desCorBcoIng")==null?"":rst.getString("tx_desCorBcoIng"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_BCO_ING,     "" + rst.getObject("tx_desLarBcoIng")==null?"":rst.getString("tx_desLarBcoIng"));
                        vecReg.add(INT_TBL_DAT_CHK_MOS_PAR_RES,     null);
                        vecReg.add(INT_TBL_DAT_MOT_TRS_BAN,         "" + rst.getObject("tx_mottra")==null?"":rst.getString("tx_mottra"));

                        vecReg.add(INT_TBL_DAT_VAL_CAR,             "" + rst.getObject("nd_valtraCar")==null?"":rst.getString("nd_valtraCar"));
                        vecReg.add(INT_TBL_DAT_AD_VAL,              "" + rst.getObject("nd_adv")==null?"":rst.getString("nd_adv"));
                        vecReg.add(INT_TBL_DAT_FOD_INF,             "" + rst.getObject("nd_fod")==null?"":rst.getString("nd_fod"));

                        if( ! ( rst.getObject("st_mosparresp")==null?"":rst.getString("st_mosparresp")  ).equals("")  ){
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_MOS_PAR_RES);
                        }

                        if(strTipTrs.equals("T"))
                            vecReg.setElementAt("Transferencia bancaria al exterior", INT_TBL_DAT_TIP_TRS);
                        else if(strTipTrs.equals("A"))
                            vecReg.setElementAt("Transferencia arancelaria", INT_TBL_DAT_TIP_TRS);

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
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarDocumentosAsociados(){
        boolean blnRes=true;
        arlDatDocAso.clear();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+="      , a3.co_empRelCabDia AS co_empRel, a3.co_locRelCabDia AS co_locRel, a3.co_tipDocRelCabDia AS co_tipDocRel, a3.co_docRelCabDia AS co_docRel";
                strSQL+="      , a5.tx_desCor, a5.tx_desLar, a4.tx_numDia AS ne_numDoc, a4.fe_dia AS fe_doc";
                strSQL+=" FROM tbm_cabotrmovban AS a1 INNER JOIN tbm_detotrmovban AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_docgenotrmovban AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
                strSQL+=" INNER JOIN tbm_cabDia AS a4";
                strSQL+=" ON a3.co_empRelCabDia=a4.co_emp AND a3.co_locRelCabDia=a4.co_loc AND a3.co_tipDocRelCabDia=a4.co_tipDoc AND a3.co_docRelCabDia=a4.co_dia";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a5";
                strSQL+=" ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I') AND a1.st_pro='I'";
                strSQL+=" AND a3.co_empRelCabDia IS NOT NULL";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegDocAso=new ArrayList();
                    arlRegDocAso.add(INT_ARL_DOC_ASO_COD_EMP_REL, rst.getString("co_empRel"));
                    arlRegDocAso.add(INT_ARL_DOC_ASO_COD_LOC_REL, rst.getString("co_locRel"));
                    arlRegDocAso.add(INT_ARL_DOC_ASO_COD_TIP_DOC_REL, rst.getString("co_tipDocRel"));
                    arlRegDocAso.add(INT_ARL_DOC_ASO_COD_DOC_REL, rst.getString("co_docRel"));
                    arlDatDocAso.add(arlRegDocAso);
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
            if (con!=null){
                if (insertarCab()){
                    if (insertarDet()){
                        if (insertarDiario()){
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
        int intCodUsr, intUltReg;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el cádigo del áltimo registro.
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabotrmovban AS a1";
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
                strSQL ="";
                strSQL+=" INSERT INTO tbm_cabotrmovban (";
                strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_mnu ";
                strSQL+="   , st_imp, st_pro, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod ";
                strSQL+="   , co_usring, co_usrmod, tx_coming, tx_comultmod, st_regrep)";
                strSQL+=" VALUES (";
                strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                strSQL+=", " + objParSis.getCodigoLocal();   //co_loc
                strSQL+=", " + txtCodTipDoc.getText();       //co_tipDoc
                strSQL+=", " + intUltReg; //co_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL+=", " + objUti.codificar(txtNumDoc1.getText(),2); //ne_numDoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", 'I'";//st_pro
                strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+=", " + intCodUsr; //co_usrIng
                strSQL+=", " + intCodUsr; //co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_comultmod
                strSQL+=", 'I');";

                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + ";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
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
    private boolean insertarDet(){
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc;
        boolean blnRes=true;
        String strUpd="";
        String strOpcTrs="";
        try{
            if (con!=null){
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;              
                for (i=0;i<objTblMod.getRowCountTrue();i++){
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_detotrmovban(";
                    strSQL+="      co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_tiptra, co_emptra";
                    strSQL+="    , co_ctatra, nd_valtra, st_regrep, fe_car, co_ctaBanEgr";
                    strSQL+="    , co_ctaBanIng, st_mosParRes, tx_motTra, nd_adv, nd_fod";
                    strSQL+=" )";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;//co_emp
                    strSQL+=", " + intCodLoc;//co_loc
                    strSQL+=", " + strCodTipDoc;//co_tipdoc
                    strSQL+=", " + strCodDoc;//co_doc
                    strSQL+=", " + j;//co_reg
                    strSQL+=", '" + objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS) + "'";//tx_tiptra
                    strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";//co_emptra
                    strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA) + "";//co_ctatra
                    strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_VAL_TRS) + "";//nd_valtra
                    strSQL+=", 'I'";//st_regrep
                    strOpcTrs=objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS).toString();
                    if( strOpcTrs.equals("T") ){
                        strSQL+=", '" + objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_DAT_FEC_CAR).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())  + "'";//fe_car
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA_BAN_EGR) + "";//co_ctaBanEgr
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA_BAN_ING) + "";//co_ctaBanIng
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_MOS_PAR_RES))
                            strSQL+=", 'S'";//st_mosParRes
                        else
                            strSQL+=", Null";//st_mosParRes
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_MOT_TRS_BAN)) + "";//tx_motTra
                    }
                     else{
                        strSQL+=", Null";//fe_car
                        strSQL+=", Null";//co_ctaBanEgr
                        strSQL+=", Null";//co_ctaBanIng
                        strSQL+=", Null";//st_mosParRes
                        strSQL+=", Null";//tx_motTra
                     }
                    if(  strOpcTrs.equals("A")  ){
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_AD_VAL) + "";//nd_adv
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_FOD_INF) + "";//nd_fod
                    }
                    else{
                        strSQL+=", Null";//nd_adv
                        strSQL+=", Null";//nd_fod
                    }

                    strSQL+=");";
                    j++;
                    strUpd+=strSQL;
                }
                stm.executeUpdate(strUpd);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean insertarDiario(){
        boolean blnRes=false;
        int intCodEmp=0, intCodLoc=0;
        int intCodTipDia=-1;
        String strNumDia="", strFecDia="";
        String strValDia="";
        int intCodCtaDeb=0, intCodCtaHab=0;
        String strTipTrs="";
        int intCodEmpRel=-1;
        String strOpcTrs="";
        int intNumDia=-1, intCodDia=-1;
        int j=1;
        int intSec=-1;
        String strUpd="";
        String strEstEli="";
        String strTblCodSec="", strArlCodSec="";
        ResultSet rstInsDia;
        String strGloDiaTrsExt="";
        int ultRegCabTraBanDia=-1;
        try{
            if(con!=null){
                stm=con.createStatement();

                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    vecDatDetTrs.clear();
                    objAsiDia.inicializar();

                    intCodEmp=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                    intCodLoc=getCodigoLocalPredeterminado(intCodEmp);

                    strTblCodSec=objTblMod.getValueAt(i, INT_TBL_DAT_COD_SEC)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_SEC).toString();

                    strOpcTrs=objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS).toString();
                    if(strOpcTrs.equals("T")){
                        intCodTipDia=87;
                    }
                    else if(strOpcTrs.equals("A")){
                        intCodTipDia=88;
                    }

                    strSQL ="";
                    strSQL+=" SELECT ne_ultdoc AS tx_numDia";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDia + "";
                    rstInsDia=stm.executeQuery(strSQL);
                    if(rstInsDia.next())
                        intNumDia=rstInsDia.getInt("tx_numDia");
                    if (intNumDia==-1)
                        return false;
                    intNumDia++;
                    strNumDia="" + intNumDia;

                    strSQL ="";
                    strSQL+=" SELECT MAX(a1.co_dia) AS co_dia";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDia + "";
                    rstInsDia=stm.executeQuery(strSQL);
                    if(rstInsDia.next())
                        intCodDia=rstInsDia.getInt("co_dia");
                    if (intCodDia==-1)
                        return false;
                    intCodDia++;

                    strSQL ="";
                    strSQL+=" SELECT MAX(a1.co_reg) AS co_reg";
                    strSQL+=" FROM tbm_cartrabandia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDia + "";
                    strSQL+=" AND a1.co_dia=" + intCodDia + "";

                    rstInsDia=stm.executeQuery(strSQL);
                    if(rstInsDia.next())
                        ultRegCabTraBanDia=rstInsDia.getInt("co_reg");
                    if (ultRegCabTraBanDia==-1)
                        return false;
                    ultRegCabTraBanDia++;

                    strSQL ="";
                    strSQL+=" SELECT MAX(a1.co_sec) AS co_sec";
                    strSQL+=" FROM tbm_docgenotrmovban AS a1";
                    rstInsDia=stm.executeQuery(strSQL);
                    if(rstInsDia.next())
                        intSec=rstInsDia.getInt("co_sec");
                    if (intSec==-1)
                        return false;
                    intSec++;

                    strFecDia=dtpFecDoc.getText();
                    strValDia=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_TRS)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_TRS).toString();
                    strTipTrs=objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_OPC_TRS).toString();
                    intCodEmpRel=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL).toString());

                    if(  (strTipTrs.equals("T"))  || (strTipTrs.equals("A"))  ) {
                        for(int a=0;a<arlDatDetTrs.size(); a++){
                            strEstEli=objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_EST_ELI);
                            strArlCodSec=objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_COD_SEC);
                            if( ! strEstEli.equals("E")){
                                if(strArlCodSec.equals(strTblCodSec)){
                                    vecRegDetTrs = new java.util.Vector();
                                    vecRegDetTrs.add(INT_VEC_TRS_LIN, null);
                                    vecRegDetTrs.add(INT_VEC_TRS_COD_CTA, objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_COD_CTA));
                                    vecRegDetTrs.add(INT_VEC_TRS_NUM_CTA, objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_COD_CTA));
                                    vecRegDetTrs.add(INT_VEC_TRS_BUT, null);
                                    vecRegDetTrs.add(INT_VEC_TRS_NOM_CTA, objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_COD_CTA));///para probar///
                                    vecRegDetTrs.add(INT_VEC_TRS_DEB, objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_VAL_DEB));
                                    vecRegDetTrs.add(INT_VEC_TRS_HAB, objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_VAL_HAB));
                                    vecRegDetTrs.add(INT_VEC_TRS_REF, objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_REF));
                                    vecRegDetTrs.add(INT_VEC_TRS_EST_CON, objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_EST_CON_BAN));
                                    vecDatDetTrs.add(vecRegDetTrs);
                                    strGloDiaTrsExt=objUti.getStringValueAt(arlDatDetTrs, a, INT_ARL_DET_TRS_GLO_DIA);
                                }
                            }
                        }
                        objAsiDia.setGlosa("" + strGloDiaTrsExt);
                        objAsiDia.setDetalleDiario(vecDatDetTrs);
                        objAsiDia.setGeneracionDiario((byte)2);
                        objAsiDia.getDetalleDiario();
                    }
                    if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDia, ""+intCodDia, strNumDia, objUti.parseDate(strFecDia,"dd/MM/yyyy"), objParSis.getCodigoMenu()))
                    {
                        blnRes=true;
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_docgenotrmovban(";
                        strSQL+="       co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_sec, co_emprelcabmovinv ";
                        strSQL+="     , co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv ";
                        strSQL+="     , co_emprelcabpag, co_locrelcabpag, co_tipdocrelcabpag, co_docrelcabpag ";
                        strSQL+="     , co_emprelcabdia, co_locrelcabdia, co_tipdocrelcabdia, co_docrelcabdia, st_regrep)";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                        strSQL+=", " + objParSis.getCodigoLocal();  //co_loc
                        strSQL+=", " + txtCodTipDoc.getText();      //co_tipdoc
                        strSQL+=", " + txtCodDoc.getText();         //co_doc
                        strSQL+=", " + j;//co_reg
                        strSQL+=", " + intSec;//co_sec
                        strSQL+=", Null";//co_emprelcabmovinv
                        strSQL+=", Null";//co_locrelcabmovinv
                        strSQL+=", Null";//co_tipdocrelcabmovinv
                        strSQL+=", Null";//co_docrelcabmovinv
                        strSQL+=", Null";//co_emprelcabpag
                        strSQL+=", Null";//co_locrelcabpag
                        strSQL+=", Null";//co_tipdocrelcabpag
                        strSQL+=", Null";//co_docrelcabpag
                        strSQL+=", " + intCodEmp + "";//co_emprelcabdia
                        strSQL+=", " + intCodLoc + "";//co_locrelcabdia
                        strSQL+=", " + intCodTipDia + "";//co_tipdocrelcabdia
                        strSQL+=", " + intCodDia + "";//co_docrelcabdia
                        strSQL+=", 'I'";//st_regrep
                        strSQL+=");";
                        j++;
                        intSec++;
                        strUpd+=strSQL;

                        /////////////////////////////////////////////////////////////////////
                        //Armar la sentencia SQL.
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_cartrabandia(";
                        strSQL+="   co_emp, co_loc, co_tipdoc, co_dia, co_reg, fe_car, co_ctabanegr,";
                        strSQL+="   co_ctabaning, nd_valtra, tx_valtrapal, st_mosparresp, tx_mottra,";
                        strSQL+="   tx_ejeasi, st_fircon, tx_firaut1, tx_carfiraut1, tx_firaut2,";
                        strSQL+="   tx_carfiraut2, nd_adv, nd_fod";
                        strSQL+=" )";
                        strSQL+=" (SELECT ";
                        strSQL+="  " + intCodEmp + " AS co_emp";//co_emp
                        strSQL+=", " + intCodLoc + " AS co_loc";//co_loc
                        strSQL+=", " + intCodTipDia + " AS co_tipdoc";//co_tipdoc
                        strSQL+=", " + intCodDia + " AS co_dia";//co_dia
                        strSQL+=", " + ultRegCabTraBanDia + " AS co_reg";//co_reg
                        strSQL+=", '" + objUti.formatearFecha(objTblMod.getValueAt(i, INT_TBL_DAT_FEC_CAR).toString(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AS fe_car";//fe_car
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA_BAN_EGR) + " AS co_ctabanegr";//co_ctabanegr
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA_BAN_ING) + " AS co_ctabaning";//co_ctabaning
                        //transferencia al exterior 'T'
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR) + " AS nd_valtra";//nd_valtra
                        //Cantidad en palabras.
                        strSQL+=", " + objUti.codificar(getValorPalabras(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR).toString())) + " AS tx_valtrapal";//tx_valtrapal
                        
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_MOS_PAR_RES))
                            strSQL+=", 'S' AS st_mosParRes";//st_mosParRes
                        else
                            strSQL+=", Null AS st_mosParRes";//st_mosParRes
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_MOT_TRS_BAN)) + " AS tx_motTra";//tx_motTra
                        strSQL+=", a1.tx_ejeasi ";//tx_ejeasi
                        strSQL+=", a1.st_fircon ";//st_fircon
                        strSQL+=", a1.tx_firaut1";//tx_firaut1
                        strSQL+=", a1.tx_carfiraut1";//tx_carfiraut1
                        strSQL+=", a1.tx_firaut2";//tx_firaut2
                        strSQL+=", a1.tx_carfiraut2";//tx_carfiraut2
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_AD_VAL) + " AS nd_adv";//nd_adv
                        strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_FOD_INF) + " AS nd_fod";//nd_fod
                        strSQL+=" from tbm_ctaban AS a1";
                        strSQL+=" where a1.co_emp=" + intCodEmp + "";
                        strSQL+=" and a1.co_ctaban=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_CTA_BAN_EGR) + "";
                        strSQL+=");";
                        strUpd+=strSQL;
                    }
                    else{
                        blnRes=false;
                        break;
                    }
                    rstInsDia.close();
                    rstInsDia=null;
                }
                stm.executeUpdate(strUpd);
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

    private int getCodigoLocalPredeterminado(int empresaBanco){
        int intCodLocPre=-1;
        Statement stmLocPre;
        try{
            if(con!=null){
                stmLocPre=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_loc FROM tbm_loc";
                strSQL+=" WHERE co_emp=" + empresaBanco + "";
                strSQL+=" AND st_reg='P'";
                rst=stmLocPre.executeQuery(strSQL);
                if(rst.next()){
                    intCodLocPre=rst.getInt("co_loc");
                }
                stmLocPre.close();
                stmLocPre=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodLocPre;
    }

    private String getNumeroCuenta(int empresaCuenta, String codigoCuenta){
        String strNumCta="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT tx_codCta FROM tbm_plaCta";
                strSQL+=" WHERE co_emp=" + empresaCuenta + "";
                strSQL+=" AND co_cta=" + codigoCuenta + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strNumCta=rst.getString("tx_codCta");
                }
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
        return strNumCta;
    }

    private String getNombreCuenta(int empresaCuenta, String codigoCuenta){
        String strNomCta="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT tx_desLar FROM tbm_plaCta";
                strSQL+=" WHERE co_emp=" + empresaCuenta + "";
                strSQL+=" AND co_cta=" + codigoCuenta + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strNomCta=rst.getString("tx_desLar");
                }
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
        return strNomCta;
    }
     
    private String getValorPalabras(String valorConvertir){
        String valPal="";
        try{
            Librerias.ZafUtil.ZafNumLetra numero;
            double cantidad= objUti.redondear(valorConvertir, objParSis.getDecimalesMostrar());
            String decimales=String.valueOf(cantidad).toString();
            decimales=decimales.substring(decimales.indexOf('.') + 1);
            decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
            int deci= Integer.parseInt(decimales);
            int m_pesos=(int)cantidad;
            numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
            String	res = numero.convertirLetras(m_pesos);
            if(txtValCarEur.isSelected())
                res = res+" "+decimales+"/100  EUROS";
            else
                res = res+" "+decimales+"/100  DOLARES";
            res=res.toUpperCase();
            valPal=res;

            numero=null;
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
        return valPal;
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
        catch (java.sql.SQLException e)    {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
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
                strSQL+=" UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
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
                    if(anularDiariosDocumentosAsociados()){
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
        catch (java.sql.SQLException e)    {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
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
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabOtrMovBan";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comultmod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
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
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularDiariosDocumentosAsociados()
    {
        boolean blnRes=true;
        int intCodEmpRel=-1;
        int intCodLocRel=-1;
        int intCodTipDocRel=-1;
        int intCodDocRel=-1;        
        try{
            if (con!=null){
                if(arlDatDocAso.size()>0){
                    for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                        intCodEmpRel=objUti.getIntValueAt(arlDatDocAso, i, INT_ARL_DOC_ASO_COD_EMP_REL);
                        intCodLocRel=objUti.getIntValueAt(arlDatDocAso, i, INT_ARL_DOC_ASO_COD_LOC_REL);
                        intCodTipDocRel=objUti.getIntValueAt(arlDatDocAso, i, INT_ARL_DOC_ASO_COD_TIP_DOC_REL);
                        intCodDocRel=objUti.getIntValueAt(arlDatDocAso, i, INT_ARL_DOC_ASO_COD_DOC_REL);
                        if( ! (objAsiDia.anularDiario(con, intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel))  ){
                            blnRes=false;
                            break;
                        }
                    }
                }
                else{
                    mostrarMsgInf("El registro ya tiene pagos asociados");
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
    private int getTipModFecUsr(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intTipModFec=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        intTipModFec=rstLoc.getInt("ne_tipresmodfecdoc");
                    }                    
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;
                }
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
    private int getTipModDocUsr(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intTipModTipDocUsr=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
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
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        intTipModTipDocUsr=rstLoc.getInt("ne_tipresmoddoc");
                    }
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;
                }
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
            int intTipModDoc;
            intTipModDoc=getTipModDocUsr();
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
            objTooBar.setOperacionSeleccionada("n");
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public ArrayList getArlDatDetTrs() {
        return arlDatDetTrs;
    }

    public void setArlDatDetTrs(ArrayList arlDatDetTrs) {
        this.arlDatDetTrs = arlDatDetTrs;
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
        boolean blnRes=true;
        boolean blnRpt;
        Connection conRpt;
        Statement stmRpt;
        ResultSet rstRpt;        
        int i, intNumTotRpt;    
        String strSQLRep="", strSQLSubRep="";
        String strRutRpt="", strNomRpt="", strFecHorSer="", strRucEmp="", strRucCon="";        
        String strTipTrs="", strFecCar="", strRutImg="";
        java.util.Map mapPar = new java.util.HashMap();
        try
        {
            conRpt =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conRpt!=null){            
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
                            for(int k=0;k<objTblMod.getRowCountTrue();k++){
                                blnRpt=false;
                                strTipTrs=objTblMod.getValueAt(k, INT_TBL_DAT_TIP_TRS)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_TIP_TRS).toString();

                                switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                {   
                                    case 276: 
                                        //<editor-fold defaultstate="collapsed" desc="/* Otros movimientos bancarios (Asientos Diario)... */"> 
                                        if (strTipTrs.equals("Transferencia arancelaria") || strTipTrs.equals("Transferencia bancaria al exterior")) {
                                            stmRpt=conRpt.createStatement();
                                            //Cabecera de Reporte 
                                            strSQL ="";
                                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a3.co_Emprelcabdia AS co_empRel, a3.co_locrelcabdia AS co_locRel";
                                            strSQL+="      , a3.co_tipdocrelcabdia AS co_tipDocRel, a3.co_Docrelcabdia AS co_dia, a4.tx_numdia, a4.fe_Dia, a4.tx_glo ";
                                            strSQL+="      , a4.co_usring, a6.tx_nom AS tx_nomUsrIng, a4.co_usrMod, a7.tx_nom AS tx_nomUsrMod, a4.st_Reg, a4.st_imp, a5.tx_Descor, a5.tx_Deslar ";
                                            strSQL+=" FROM tbm_cabotrmovban AS a1 ";
                                            strSQL+=" INNER JOIN tbm_detotrmovban AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                                            strSQL+=" INNER JOIN tbm_docgenotrmovban AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg ";
                                            strSQL+=" INNER JOIN tbm_cabDia AS a4 ON a3.co_empRelCabDia=a4.co_emp AND a3.co_locRelCabDia=a4.co_loc AND a3.co_tipDocRelCabDia=a4.co_tipDoc AND a3.co_docRelCabDia=a4.co_dia ";
                                            strSQL+=" INNER JOIN tbm_cabTipDoc AS a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc ";
                                            strSQL+=" INNER JOIN tbm_usr as a6 ON a4.co_usring=a6.co_usr ";
                                            strSQL+=" INNER JOIN tbm_usr as a7 ON a4.co_usrMod=a7.co_usr ";
                                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                                            strSQL+=" AND a1.co_doc=" + txtCodDoc.getText() + "";
                                            strSQL+=" AND a2.co_Reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strSQL+=" AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I') AND a1.st_pro='I' AND a3.co_empRelCabDia IS NOT NULL ";
                                            rstRpt = stmRpt.executeQuery(strSQL);
                                            if (rstRpt.next()) {
                                                blnRpt = true;
                                            }

                                            strSQLRep = strSQL;
                                            strRutRpt = objRptSis.getRutaReporte(i);
                                            strNomRpt = objRptSis.getNombreReporte(i);

                                            //Detalle de Reporte 
                                            strSQL ="";
                                            strSQL+=" SELECT a3.co_emprelcabdia AS co_emp, a3.co_locrelcabdia AS co_loc, a3.co_tipdocrelcabdia AS co_Tipdoc ";
                                            strSQL+="      , a4.co_Dia, a6.co_cta, a6.tx_CodCta, a6.tx_Deslar AS tx_nomCta, a5.nd_monDeb, a5.nd_monHab ";
                                            strSQL+=" FROM tbm_cabotrmovban AS a1 ";
                                            strSQL+=" INNER JOIN tbm_detotrmovban AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc ";
                                            strSQL+=" INNER JOIN tbm_docgenotrmovban AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg ";
                                            strSQL+=" INNER JOIN tbm_cabDia AS a4 ON a3.co_empRelCabDia=a4.co_emp AND a3.co_locRelCabDia=a4.co_loc AND a3.co_tipDocRelCabDia=a4.co_tipDoc AND a3.co_docRelCabDia=a4.co_dia ";
                                            strSQL+=" INNER JOIN tbm_detDia as a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc AND a4.co_dia=a5.co_dia ";
                                            strSQL+=" INNER JOIN tbm_placta as a6 ON a5.co_emp=a6.co_emp AND a5.co_cta=a6.co_cta ";
                                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                                            strSQL+=" AND a1.co_doc=" + txtCodDoc.getText() + "";
                                            strSQL+=" AND a2.co_Reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strSQL+=" AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I') AND a1.st_pro='I' AND a3.co_empRelCabDia IS NOT NULL ";
                                            strSQL+=" ORDER BY  a5.nd_monHab, a5.nd_monDeb";
                                            strSQLSubRep = strSQL;

                                            //Inicializar los parametros que se van a pasar al reporte.                                            
                                            mapPar.put("strCamAudRpt", "" + (rstRpt.getString("tx_nomUsrIng") + " / " + rstRpt.getString("tx_nomUsrMod") + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + " v0.1    ");
                                            mapPar.put("codUsrImp", "" + objParSis.getCodigoUsuario());
                                            mapPar.put("codEmp", rstRpt.getInt("co_empRel"));
                                            mapPar.put("codLoc", rstRpt.getInt("co_locRel"));
                                            mapPar.put("codTipDoc", rstRpt.getInt("co_tipDocRel"));
                                            mapPar.put("codDia", new Integer(rstRpt.getInt("co_dia")));
                                            mapPar.put("nomEmp", getNomEmp(rstRpt.getInt("co_empRel")));
                                            mapPar.put("rucEmp", getRucEmp(rstRpt.getInt("co_empRel")));
                                            mapPar.put("nomCorTipDoc", rstRpt.getString("tx_Descor"));
                                            mapPar.put("nomLarTipDoc", rstRpt.getString("tx_Deslar"));
                                            mapPar.put("fecAct", "" + strFecHorSer);
                                            mapPar.put("fecDoc", objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                                            mapPar.put("numDia", rstRpt.getString("tx_numdia"));
                                            mapPar.put("SUBREPORT_DIR", strRutRpt);
                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strSQLSubRep", strSQLSubRep);
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;
                                        } else {
                                            mostrarMsgInf("<HTML>Solo existe Reporte de Asiento Diario para TRBADA Y TRBAEX.</HTML>");
                                        }
                                        //</editor-fold>
                                        break;
                                        
                                    case 384: 
                                        //<editor-fold defaultstate="collapsed" desc="/* Formulario transferencias al exterior (Banco de Machala)... */"> 
                                        if(strTipTrs.equals("Transferencia bancaria al exterior")){
                                            stmRpt=conRpt.createStatement();
                                            strSQL ="";
                                            strSQL+=" SELECT a1.fe_car, a2.tx_nom AS tx_nomEmp, a2.tx_dir AS tx_dirEmp, a2.tx_tel AS tx_telEmp, a2.tx_ruc, 'X' as marca_ruc";
                                            strSQL+="      , ROUND(a1.nd_valTra, 2) as nd_valTra, a1.tx_valTraPal";
                                            strSQL+="      , a3.tx_numCtaBan AS tx_numCtaBanEgr, CASE WHEN a3.tx_tipCtaBan='A' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrAho";
                                            strSQL+="      , CASE WHEN a3.tx_tipCtaBan='C' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrCor, a5.tx_desLar AS tx_nomBanCtaBanEgr";
                                            strSQL+="      , a6.tx_desLar AS tx_nomBanCtaBanIng, a7.tx_desLar AS tx_nomCiuCtaBanIng, a8.tx_desLar AS tx_nomPaiCtaBanIng";
                                            strSQL+="      , a4.tx_codIba, a4.tx_codAba, a4.tx_codSwi, a9.tx_nom2 AS tx_nomBenCtaBanIng, a4.tx_numCtaBan AS tx_numCtaBanIng ";
                                            strSQL+="      , a9.tx_dir AS tx_dirBenCtaBanIng, a10.tx_desLar AS tx_nomCiuBenCtaBanIng, a11.tx_desLar AS tx_nomPaiBenCtaBanIng ";
                                            strSQL+="      , a9.tx_tel1 as tx_telefBenef, a1.tx_motTra, 'X' as gastos_exterior, a4.tx_dirBan";
                                            strSQL+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2 INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strSQL+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strSQL+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strSQL+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strSQL+=" INNER JOIN( tbm_carTraBanDia AS a1";
                                            strSQL+=" 		  INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)";
                                            strSQL+=" 		  INNER JOIN tbm_ctaBan AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaBanEgr=a3.co_ctaBan)";
                                            strSQL+=" 		  INNER JOIN tbm_ctaBan AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_ctaBanIng=a4.co_ctaBan)";
                                            strSQL+=" 		  LEFT OUTER JOIN tbm_var AS a5 ON (a3.co_ban=a5.co_reg)";
                                            strSQL+=" 		  LEFT OUTER JOIN tbm_var AS a6 ON (a4.co_ban=a6.co_reg)";
                                            strSQL+=" 		  LEFT OUTER JOIN tbm_ciu AS a7 ON (a4.co_ciuCtaBan=a7.co_ciu)";
                                            strSQL+=" 		  LEFT OUTER JOIN tbm_pai AS a8 ON (a7.co_pai=a8.co_pai)";
                                            strSQL+=" 		  LEFT OUTER JOIN tbm_expImp AS a9 ON (a4.co_ctaExp=a9.co_exp)";
                                            strSQL+=" 		  LEFT OUTER JOIN tbm_ciu AS a10 ON (a9.co_ciu=a10.co_ciu)";
                                            strSQL+="		  LEFT OUTER JOIN tbm_pai AS a11 ON (a10.co_pai=a11.co_pai)";
                                            strSQL+=" ) ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strSQL+=" WHERE f2.tx_tipTra='T'";
                                            strSQL+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strSQL+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strSQL+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strSQL+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strSQL+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strSQL+=" ORDER BY f2.co_emp, f2.co_loc, f2.co_tipdoc, f2.co_doc, f2.co_reg";
                                            rstRpt=stmRpt.executeQuery(strSQL);
                                            if(rstRpt.next())
                                                blnRpt=true;
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;                                            
                                            strSQLRep=strSQL;
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);
                                        
                                            stmRpt=conRpt.createStatement();
                                            strFecCar ="";
                                            strFecCar+=" SELECT a1.fe_car, CASE WHEN a1.tx_valTraPal LIKE '%DOLARES%' THEN 'USD'";
                                            strFecCar+="                        WHEN a1.tx_valTraPal LIKE '%EUROS%' THEN 'EURO' END AS tx_tipMon";
                                            strFecCar+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2  INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strFecCar+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strFecCar+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strFecCar+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strFecCar+=" INNER JOIN tbm_carTraBanDia AS a1";
                                            strFecCar+=" ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strFecCar+=" WHERE f2.tx_tipTra='T'";
                                            strFecCar+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strFecCar+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strFecCar+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strFecCar+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strFecCar+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strFecCar+=" GROUP BY a1.fe_car, a1.tx_valTraPal";
                                            rstRpt=stmRpt.executeQuery(strFecCar);
                                            if(rstRpt.next()){
                                                if(objRptSis.getCodigoReporte(i).equals("429")){
                                                    mapPar.put("fe_carAni", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "yyyy") );
                                                    mapPar.put("fe_carMes", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "MMMMM") );
                                                    mapPar.put("fe_carDia", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "dd") );
                                                }
                                                else{
                                                    mapPar.put("fe_car", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "dd 'de' MMMMM 'de' yyyy") );
                                                }                                                
                                                mapPar.put("tx_tipMon", rstRpt.getString("tx_tipMon"));
                                            }
                                            else{
                                                mapPar.put("fe_car", "" );
                                                mapPar.put("tx_tipMon", "");
                                            }
                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strRutRpt", strRutRpt);
                                            rstRpt.close();
                                            rstRpt=null;                                            
                                            stmRpt.close();
                                            stmRpt=null;
                                        }
                                        //</editor-fold>
                                        break;
                                        
                                    case 385: 
                                        //<editor-fold defaultstate="collapsed" desc="/* Formulario declaración informativa de transacciones SRI... */"> 
                                        if(strTipTrs.equals("Transferencia bancaria al exterior")){
                                            stmRpt=conRpt.createStatement();
                                            strSQL ="";                                            
                                            strSQL+=" SELECT a1.fe_car as fecha_transaccion, TO_CHAR(a1.fe_car, 'YYYY') as anio_fecha_transaccion";
                                            strSQL+=" 	   , TO_CHAR(a1.fe_car, 'MM') as mes_fecha_transaccion, TO_CHAR(a1.fe_car, 'DD') as dia_fecha_transaccion";
                                            strSQL+=" 	   , a2.tx_ruc, a2.tx_nom AS tx_nomEmp";
                                            strSQL+="      ,( SELECT tx_deslar FROM tbm_ciu WHERE co_ciu = 1 ) as ciudad_domicilio_remitente";
                                            strSQL+="      ,( SELECT tx_dir FROM tbm_emp WHERE co_emp = 1 ) as calle_principal_domicilio_remitente";
                                            strSQL+="      ,( SELECT SUBSTR(tx_dir, LENGTH(tx_dir) - 6, LENGTH(tx_dir)) FROM tbm_emp WHERE co_emp = 1 ) as numero_calle";
                                            strSQL+="	   , a2.tx_dir AS tx_dirEmp, ROUND(a1.nd_valTra, 2) as nd_valTra";
                                            strSQL+="	   , a3.tx_rucban as ruc_bco_egreso, a5.tx_desLar AS tx_nomBanCtaBanEgr";
                                            strSQL+="	   , a9.tx_nom2 AS tx_nomBenCtaBanIng, a11.tx_desLar AS tx_nomPaiBenCtaBanIng";
                                            strSQL+="      ,( SELECT tx_ced FROM tbm_usr WHERE co_usr = 101) as ruc_contador_tuval";
                                            strSQL+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2     INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strSQL+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strSQL+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strSQL+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strSQL+=" INNER JOIN ( tbm_carTraBanDia AS a1";
                                            strSQL+=" 		   INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)";
                                            strSQL+=" 		   INNER JOIN tbm_ctaBan AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaBanEgr=a3.co_ctaBan)";
                                            strSQL+=" 		   INNER JOIN tbm_ctaBan AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_ctaBanIng=a4.co_ctaBan)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_var AS a5 ON (a3.co_ban=a5.co_reg)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_var AS a6 ON (a4.co_ban=a6.co_reg)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_ciu AS a7 ON (a4.co_ciuCtaBan=a7.co_ciu)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_pai AS a8 ON (a7.co_pai=a8.co_pai)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_expImp AS a9 ON (a4.co_ctaExp=a9.co_exp)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_ciu AS a10 ON (a9.co_ciu=a10.co_ciu)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_pai AS a11 ON (a10.co_pai=a11.co_pai)";
                                            strSQL+=" ) ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strSQL+=" WHERE f2.tx_tipTra='T'";
                                            strSQL+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strSQL+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strSQL+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strSQL+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strSQL+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strSQL+=" ORDER BY f2.co_emp, f2.co_loc, f2.co_tipdoc, f2.co_doc, f2.co_reg";
                                            rstRpt=stmRpt.executeQuery(strSQL);
                                            if(rstRpt.next())
                                                blnRpt=true;
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;                                                   

                                            String sCadena="", sCadenaCon="";
                                            String strCadEsp="", strCadEspCon="";
                                            String sCadenaConDia="", sCadenaConMes="", sCadenaConAni="";
                                            String sRucBcoEgr="";

                                            stmRpt=conRpt.createStatement();
                                            rstRpt=stmRpt.executeQuery(strSQL);
                                            if(rstRpt.next()){
                                                sCadena=rstRpt.getString("tx_ruc");
                                                sCadenaCon=rstRpt.getString("ruc_contador_tuval");
                                                sCadenaConDia=rstRpt.getString("dia_fecha_transaccion");
                                                sCadenaConMes=rstRpt.getString("mes_fecha_transaccion");
                                                sCadenaConAni=rstRpt.getString("anio_fecha_transaccion");
                                                sRucBcoEgr=rstRpt.getString("ruc_bco_egreso");
                                            }
                                            else{
                                                sCadena="";
                                                sCadenaCon="";
                                                sCadenaConDia="";
                                                sCadenaConMes="";
                                                sCadenaConAni="";
                                                sRucBcoEgr="";
                                            }
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;                                            

                                            //dia
                                            String strCadDia="", strDia="";
                                            char[] aDia = sCadenaConDia.toCharArray();
                                            for (int x=0;x<aDia.length;x++){
                                                strCadDia+="   " + aDia[x];
                                                strDia=strCadDia;
                                            }

                                            //mes
                                            String strCadMes="", strMes="";
                                            char[] aMes = sCadenaConMes.toCharArray();
                                            for (int x=0;x<aMes.length;x++){
                                                strCadMes+="   " + aMes[x];
                                                strMes=strCadMes;
                                            }

                                            //anio
                                            String strCadAni="", strAni="";
                                            char[] aAni = sCadenaConAni.toCharArray();
                                            for (int x=0;x<aAni.length;x++){
                                                if(x==2)
                                                    strCadAni+="         " + aAni[x];
                                                else
                                                    strCadAni+="       " + aAni[x];
                                                strAni=strCadAni;
                                            }

                                            //ruc de empresa
                                            char[] aCaracteres = sCadena.toCharArray();
                                            for (int x=0;x<aCaracteres.length;x++){
                                                if(  (x==0) )
                                                    strCadEsp+="   " + aCaracteres[x];
                                                else if((x == 3) || (x == 6) )
                                                    strCadEsp+="      " + aCaracteres[x];
                                                else if((x == 9) || (x == 11) )
                                                    strCadEsp+="       " + aCaracteres[x];
                                                else
                                                    strCadEsp+="    " + aCaracteres[x];
                                                strRucEmp=strCadEsp;
                                            }

                                            //ruc del banco de egreso
                                            String strRucBcoEgr="";
                                            strCadEsp="";
                                            char[] aRucBcoEgr = sRucBcoEgr.toCharArray();
                                            for (int x=0;x<aRucBcoEgr.length;x++){
                                                if(  (x==0) )
                                                    strCadEsp+="   " + aRucBcoEgr[x];
                                                else if((x == 3) || (x == 6) )
                                                    strCadEsp+="      " + aRucBcoEgr[x];
                                                else if((x == 9) || (x == 11) )
                                                    strCadEsp+="       " + aRucBcoEgr[x];
                                                else
                                                    strCadEsp+="    " + aRucBcoEgr[x];
                                                strRucBcoEgr=strCadEsp;
                                            }

                                            //ruc del contador
                                            char[] aCaracteresCon = sCadenaCon.toCharArray();
                                            for (int x=0;x<aCaracteresCon.length;x++){
                                                if(  (x==0) )
                                                    strCadEspCon+="  " + aCaracteresCon[x];
                                                else if((x == 2) || (x == 3) || (x == 5) || (x == 7) || (x == 8) )
                                                    strCadEspCon+="    " + aCaracteresCon[x];
                                                else
                                                    strCadEspCon+="      " + aCaracteresCon[x];
                                                strRucCon=strCadEspCon;
                                            }
                                            //ruc del representante legal
                                            String strRepLeg="1706487236"; //LSC
                                            String strCadEspRepLeg="";
                                            String strRucRepLeg="";
                                            char[] astrRepLeg = strRepLeg.toCharArray();
                                            for (int x=0;x<astrRepLeg.length;x++){
                                                strCadEspRepLeg+="   " + astrRepLeg[x];
                                                strRucRepLeg=strCadEspRepLeg;
                                            }

                                            strSQLRep=strSQL;
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);

                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strRutRpt", strRutRpt);
                                            mapPar.put("strRucEmp", strRucEmp);//ruc del sujeto pasivo(remitente)
                                            mapPar.put("strRucCon", strRucCon);//ruc del contador
                                            mapPar.put("strRucRepLeg", strRucRepLeg);//ruc del contador
                                            mapPar.put("strDia", strDia);//dia
                                            mapPar.put("strMes", strMes);//mes
                                            mapPar.put("strAni", strAni);//anio
                                            mapPar.put("strRucBcoEgr", strRucBcoEgr);//anio
                                        }
                                        //</editor-fold>
                                        break;

                                    case 388: 
                                        //<editor-fold defaultstate="collapsed" desc="/* Carta transferencias por derechos arancelarios (Banco del Pacífico)... */"> 
                                        //</editor-fold>
                                       break;
                                       
                                    case 389: 
                                        //<editor-fold defaultstate="collapsed" desc="/* Carta transferencias por derechos arancelarios (Produbanco)... */"> 
                                        //</editor-fold>
                                       break;
                                        
                                    case 424: 
                                        //<editor-fold defaultstate="collapsed" desc="/* Carta transferencias por derechos arancelarios (Internacional)... */"> 
                                        if(strTipTrs.equals("Transferencia arancelaria")){
                                            stmRpt=conRpt.createStatement();
                                            strSQL ="";
                                            strSQL+=" SELECT a1.fe_car, a2.tx_nom AS tx_nomEmp, a2.tx_dir AS tx_dirEmp, a2.tx_tel AS tx_telEmp, a2.tx_ruc, 'X' as marca_ruc";
                                            strSQL+="      , ROUND(a1.nd_valTra, 2) as nd_valTra,  a1.tx_valTraPal";
                                            strSQL+="      , ROUND((a1.nd_valTra - ((CASE WHEN f2.nd_adv IS NULL THEN 0 ELSE f2.nd_adv END) + (CASE WHEN f2.nd_fod IS NULL THEN 0 ELSE f2.nd_fod END))  ), 2) as nd_valIva";                                            
                                            strSQL+="      , a3.tx_numCtaBan AS tx_numCtaBanEgr, CASE WHEN a3.tx_tipCtaBan='A' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrAho";
                                            strSQL+="      , CASE WHEN a3.tx_tipCtaBan='C' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrCor, a5.tx_desLar AS tx_nomBanCtaBanEgr";
                                            strSQL+="      , a6.tx_desLar AS tx_nomBanCtaBanIng, a7.tx_desLar AS tx_nomCiuCtaBanIng, a8.tx_desLar AS tx_nomPaiCtaBanIng";
                                            strSQL+="      , a4.tx_codIba, a4.tx_codAba, a4.tx_codSwi, a9.tx_nom2 AS tx_nomBenCtaBanIng, a4.tx_numCtaBan AS tx_numCtaBanIng";
                                            strSQL+="      , a9.tx_dir AS tx_dirBenCtaBanIng, a10.tx_desLar AS tx_nomCiuBenCtaBanIng";
                                            strSQL+="      , a11.tx_desLar AS tx_nomPaiBenCtaBanIng, a9.tx_tel1 as tx_telefBenef, a1.tx_motTra AS nd_valdau, 'X' as gastos_exterior";
                                            strSQL+="      , a4.tx_dirBan, f2.tx_motTra, CASE WHEN f2.nd_adv IS NULL THEN 0 ELSE f2.nd_adv END AS nd_adv, CASE WHEN f2.nd_fod IS NULL THEN 0 ELSE f2.nd_fod END AS nd_fod";
                                            strSQL+="      , a1.tx_firAut1, a1.tx_carFirAut1, a1.tx_firAut2, a1.tx_carFirAut2, a1.st_fircon, f1.tx_obs1";
                                            strSQL+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2  INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strSQL+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strSQL+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strSQL+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strSQL+=" INNER JOIN (  tbm_carTraBanDia AS a1";
                                            strSQL+=" 		    INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)";
                                            strSQL+=" 	            INNER JOIN tbm_ctaBan AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaBanEgr=a3.co_ctaBan)";
                                            strSQL+=" 		    INNER JOIN tbm_ctaBan AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_ctaBanIng=a4.co_ctaBan)";
                                            strSQL+=" 		    LEFT OUTER JOIN tbm_var AS a5 ON (a3.co_ban=a5.co_reg)";
                                            strSQL+=" 		    LEFT OUTER JOIN tbm_var AS a6 ON (a4.co_ban=a6.co_reg)";
                                            strSQL+=" 		    LEFT OUTER JOIN tbm_ciu AS a7 ON (a4.co_ciuCtaBan=a7.co_ciu)";
                                            strSQL+=" 		    LEFT OUTER JOIN tbm_pai AS a8 ON (a7.co_pai=a8.co_pai)";
                                            strSQL+=" 		    LEFT OUTER JOIN tbm_expImp AS a9 ON (a4.co_ctaExp=a9.co_exp)";
                                            strSQL+=" 		    LEFT OUTER JOIN tbm_ciu AS a10 ON (a9.co_ciu=a10.co_ciu)";
                                            strSQL+="		    LEFT OUTER JOIN tbm_pai AS a11 ON (a10.co_pai=a11.co_pai)";
                                            strSQL+=" ) ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strSQL+=" WHERE f2.tx_tipTra='A'";
                                            strSQL+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strSQL+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strSQL+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strSQL+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strSQL+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";

                                            if(Integer.parseInt(objRptSis.getCodigoReporte(i))==388)//para banco del pacifico
                                                strSQL+=" AND  (a5.tx_desLar LIKE lower('%fi%') OR a5.tx_desLar LIKE upper('%FI%')  )";
                                            else if(Integer.parseInt(objRptSis.getCodigoReporte(i))==389)//para banco produbanco
                                                strSQL+=" AND  (a5.tx_desLar LIKE lower('%DU%') OR a5.tx_desLar LIKE upper('%DU%')  )";
                                            else if(Integer.parseInt(objRptSis.getCodigoReporte(i))==424)//para banco internacional
                                                strSQL+=" AND  (a5.tx_desLar LIKE lower('%ternacio%') OR a5.tx_desLar LIKE upper('%TERNACIO%')  )";

                                            strSQL+=" ORDER BY f2.co_emp, f2.co_loc, f2.co_tipdoc, f2.co_doc, f2.co_reg";
                                            rstRpt=stmRpt.executeQuery(strSQL);
                                            if(rstRpt.next())
                                                blnRpt=true;
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;                                            
                                            strSQLRep=strSQL;
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);

                                            stmRpt=conRpt.createStatement();
                                            strFecCar ="";
                                            strFecCar+=" SELECT a1.fe_car, f3.co_emp";
                                            strFecCar+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2     INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strFecCar+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strFecCar+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strFecCar+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strFecCar+=" INNER JOIN tbm_carTraBanDia AS a1";
                                            strFecCar+=" ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strFecCar+=" WHERE f2.tx_tipTra='A'";
                                            strFecCar+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strFecCar+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strFecCar+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strFecCar+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strFecCar+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strFecCar+=" GROUP BY a1.fe_car, f3.co_emp";
                                            rstRpt=stmRpt.executeQuery(strFecCar);
                                            if(rstRpt.next()){
                                                if( Integer.parseInt(objRptSis.getCodigoReporte(i))==389 ){//debito por aranceles - copia exacta
                                                    mapPar.put("fe_car", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "dd/MM/yyyy") );
                                                }
                                                else{
                                                    mapPar.put("fe_car", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "dd 'de' MMMMM 'de' yyyy") );
                                                }

                                                switch(rstRpt.getInt("co_emp")){
                                                    case 1:
                                                        strRutImg=objRptSis.getRutaReporte(i) + "\\logCabTuv.png";
                                                        break;
                                                    case 2:
                                                        strRutImg=objRptSis.getRutaReporte(i) + "\\logCabCas.png";
                                                        break;
                                                    case 4:
                                                        strRutImg=objRptSis.getRutaReporte(i) + "\\logCabDim.png";
                                                        break;                                                        
                                                }
                                            }
                                            else{
                                                mapPar.put("fe_car", "" );
                                                strRutImg="";
                                            }
                                            //otros parametros
                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strRutRpt", strRutRpt);
                                            mapPar.put("strRutImg", strRutImg);
                                            
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;
                                        }
                                        //</editor-fold>
                                        break;

                                    case 427:
                                        //<editor-fold defaultstate="collapsed" desc="/* Carta de transferencias al exterior (Pichincha-Miami)... */"> 
                                        if(strTipTrs.equals("Transferencia bancaria al exterior")){
                                            stmRpt=conRpt.createStatement();
                                            strSQL ="";
                                            strSQL+=" SELECT a2.tx_ruc, a2.tx_nom AS tx_nomEmp";
                                            strSQL+="      , CAST('Guayaquil' AS CHARACTER VARYING) as ciuRem";
                                            strSQL+="      , CAST('Guayas' AS CHARACTER VARYING) as prvRem";
                                            strSQL+="      , CAST('Ecuador' AS CHARACTER VARYING) as paiRem";
                                            strSQL+=" 	   , a2.tx_dir AS tx_dirEmp, ROUND(a1.nd_valTra, 2) as nd_valTra";
                                            strSQL+="	   , a3.tx_rucban as ruc_bco_egreso, a3.tx_numCtaBan AS tx_numCtaBanEgr";
                                            strSQL+="	   , a5.tx_desLar AS tx_nomBanCtaBanEgr, a9.tx_nom2 AS tx_nomBenCtaBanIng";
                                            strSQL+="  	   , a11.tx_desLar AS tx_nomPaiBenCtaBanIng, f1.tx_obs2, a1.tx_valtrapal";
                                            strSQL+="      , a4.tx_numctaban AS tx_numCtaBanIng, a9.tx_dir AS tx_dirBenIng";                                            
                                            strSQL+="      , a12.tx_desLar AS tx_prvBen, a10.tx_desLar AS tx_ciuBen, a11. tx_desCor AS tx_paiBen";
                                            strSQL+="      , a6.tx_desLar AS tx_nomBanBen, a7.tx_desCor AS tx_desCorCiuBanBen, a7.tx_desLar AS tx_desLarCiuBanBen";
                                            strSQL+="      , a8.tx_desCor AS tx_desCorPaiBanBen, a8.tx_desLar AS tx_desLarPaiBanBen";
                                            strSQL+="      , a4.tx_codSwi AS tx_codSwiIng, a4.tx_codIba AS tx_codIbaIng, a4.tx_codAba AS tx_codAbaIng";
                                            strSQL+="      , a3.st_fircon, a3.tx_firaut1, a3.tx_carfiraut1, a3.tx_firaut2, a3.tx_carfiraut2";
                                            strSQL+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN ( tbm_detOtrMovBan AS f2 INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strSQL+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strSQL+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strSQL+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strSQL+=" INNER JOIN(  tbm_carTraBanDia AS a1";
                                            strSQL+="  		   INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)";
                                            strSQL+="  		   INNER JOIN tbm_ctaBan AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaBanEgr=a3.co_ctaBan)";
                                            strSQL+="  		   INNER JOIN tbm_ctaBan AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_ctaBanIng=a4.co_ctaBan)";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_var AS a5 ON (a3.co_ban=a5.co_reg) ";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_var AS a6 ON (a4.co_ban=a6.co_reg) ";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_ciu AS a7 ON (a4.co_ciuCtaBan=a7.co_ciu)";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_pai AS a8 ON (a7.co_pai=a8.co_pai)";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_expImp AS a9 ON (a4.co_ctaExp=a9.co_exp)";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_ciu AS a10 ON (a9.co_ciu=a10.co_ciu)";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_pro AS a12 ON (a10.co_pro=a12.co_pro)";
                                            strSQL+="  		   LEFT OUTER JOIN tbm_pai AS a11 ON (a12.co_pai=a11.co_pai)";
                                            strSQL+=" ) ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strSQL+=" WHERE f2.tx_tipTra='T'";
                                            strSQL+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strSQL+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strSQL+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strSQL+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strSQL+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strSQL+=" AND (a5.tx_desLar LIKE lower('%ichin%iami%') OR a5.tx_desLar LIKE upper('%PICHIN%IAMI%')  )";
                                            strSQL+=" ORDER BY f2.co_emp, f2.co_loc, f2.co_tipdoc, f2.co_doc, f2.co_reg";
                                            rstRpt=stmRpt.executeQuery(strSQL);
                                            if(rstRpt.next())
                                                blnRpt=true;
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;                                              

                                            strSQLRep=strSQL;
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);
                                            
                                            stmRpt=conRpt.createStatement();
                                            strFecCar ="";
                                            strFecCar+=" SELECT a1.fe_car, f3.co_emp";
                                            strFecCar+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN ( tbm_detOtrMovBan AS f2 INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strFecCar+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strFecCar+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strFecCar+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strFecCar+=" INNER JOIN tbm_carTraBanDia AS a1";
                                            strFecCar+=" ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strFecCar+=" WHERE f2.tx_tipTra='T'";
                                            strFecCar+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strFecCar+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strFecCar+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strFecCar+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strFecCar+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strFecCar+=" GROUP BY a1.fe_car, f3.co_emp";
                                            rstRpt=stmRpt.executeQuery(strFecCar);
                                            if(rstRpt.next()){
                                                mapPar.put("fe_car", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "MMMM dd ',' yyyy") );
                                            }
                                            else{
                                                mapPar.put("fe_car", "" );
                                            }                                            
                                            strRutImg=objRptSis.getRutaReporte(i) + "\\logBcoPchMia.png";                                            
                                            
                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strRutRpt", strRutRpt);
                                            mapPar.put("strRucEmp", strRucEmp);//ruc del sujeto pasivo(remitente)
                                            mapPar.put("strRucCon", strRucCon);//ruc del contador
                                            mapPar.put("strRutImg", strRutImg);
                                            
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;                                            
                                        }
                                        //</editor-fold>
                                        break;

                                    case 429:
                                        //<editor-fold defaultstate="collapsed" desc="/* Carta de transferencias al exterior (Produbank Panama)... */"> 
                                        if(strTipTrs.equals("Transferencia bancaria al exterior")){
                                            stmRpt=conRpt.createStatement();
                                            strSQL ="";
                                            strSQL+=" SELECT a1.fe_car, a2.tx_nom AS tx_nomEmp, a2.tx_dir AS tx_dirEmp, a2.tx_tel AS tx_telEmp, a2.tx_ruc, 'X' as marca_ruc";
                                            strSQL+="      , ROUND(a1.nd_valTra, 2) as nd_valTra, a1.tx_valTraPal";
                                            strSQL+="      , a3.tx_numCtaBan AS tx_numCtaBanEgr, CASE WHEN a3.tx_tipCtaBan='A' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrAho";
                                            strSQL+="      , CASE WHEN a3.tx_tipCtaBan='C' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrCor, a5.tx_desLar AS tx_nomBanCtaBanEgr";
                                            strSQL+="      , a6.tx_desLar AS tx_nomBanCtaBanIng, a7.tx_desLar AS tx_nomCiuCtaBanIng, a8.tx_desLar AS tx_nomPaiCtaBanIng";
                                            strSQL+="      , a4.tx_codIba, a4.tx_codAba, a4.tx_codSwi, a9.tx_nom2 AS tx_nomBenCtaBanIng, a4.tx_numCtaBan AS tx_numCtaBanIng";
                                            strSQL+="      , a9.tx_dir AS tx_dirBenCtaBanIng, a10.tx_desLar AS tx_nomCiuBenCtaBanIng, a12.tx_desLar AS tx_nomPrvBenCtaBanIng";
                                            strSQL+="      , a11.tx_desLar AS tx_nomPaiBenCtaBanIng, a9.tx_tel1 as tx_telefBenef, a1.tx_motTra, 'X' as gastos_exterior, a4.tx_dirBan";
                                            strSQL+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2     INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strSQL+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strSQL+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strSQL+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strSQL+=" INNER JOIN ( tbm_carTraBanDia AS a1";
                                            strSQL+=" 		   INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)";
                                            strSQL+=" 		   INNER JOIN tbm_ctaBan AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaBanEgr=a3.co_ctaBan)";
                                            strSQL+=" 		   INNER JOIN tbm_ctaBan AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_ctaBanIng=a4.co_ctaBan)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_var AS a5 ON (a3.co_ban=a5.co_reg)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_var AS a6 ON (a4.co_ban=a6.co_reg)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_ciu AS a7 ON (a4.co_ciuCtaBan=a7.co_ciu)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_pai AS a8 ON (a7.co_pai=a8.co_pai)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_expImp AS a9 ON (a4.co_ctaExp=a9.co_exp)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_ciu AS a10 ON (a9.co_ciu=a10.co_ciu)";
                                            strSQL+=" 		   LEFT OUTER JOIN tbm_pro AS a12 ON (a10.co_pro=a12.co_pro)";
                                            strSQL+="		   LEFT OUTER JOIN tbm_pai AS a11 ON (a10.co_pai=a11.co_pai)";
                                            strSQL+=" ) ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strSQL+=" WHERE f2.tx_tipTra='T'";
                                            strSQL+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strSQL+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strSQL+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strSQL+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strSQL+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strSQL+=" AND  (a5.tx_desLar LIKE lower('%rodubank%') OR a5.tx_desLar LIKE upper('%RODUBANK%')  )";
                                            strSQL+=" ORDER BY f2.co_emp, f2.co_loc, f2.co_tipdoc, f2.co_doc, f2.co_reg";
                                            rstRpt=stmRpt.executeQuery(strSQL);
                                            if(rstRpt.next())
                                                blnRpt=true;
                                            rstRpt.close();
                                            rstRpt=null;
                                            stmRpt.close();
                                            stmRpt=null;                                            
                                            
                                            strSQLRep=strSQL;
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);

                                            stmRpt=conRpt.createStatement();
                                            strFecCar ="";
                                            strFecCar+=" SELECT a1.fe_car, CASE WHEN a1.tx_valTraPal LIKE '%DOLARES%' THEN 'USD'";
                                            strFecCar+="                        WHEN a1.tx_valTraPal LIKE '%EUROS%' THEN 'EURO' END AS tx_tipMon";
                                            strFecCar+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2     INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                            strFecCar+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                            strFecCar+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                            strFecCar+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                            strFecCar+=" INNER JOIN tbm_carTraBanDia AS a1";
                                            strFecCar+=" ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc";
                                            strFecCar+=" AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                            strFecCar+=" WHERE f2.tx_tipTra='T'";
                                            strFecCar+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                            strFecCar+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                            strFecCar+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                            strFecCar+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                            strFecCar+=" AND f2.co_reg=" + objTblMod.getValueAt(k, INT_TBL_DAT_COD_SEC) + "";
                                            strFecCar+=" GROUP BY a1.fe_car, a1.tx_valTraPal";
                                            rstRpt=stmRpt.executeQuery(strFecCar);
                                            if(rstRpt.next()){
                                                mapPar.put("fe_carAni", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "yyyy") );
                                                mapPar.put("fe_carMes", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "MMMMM") );
                                                mapPar.put("fe_carDia", objUti.formatearFecha(rstRpt.getString("fe_car"), "yyyy-MM-dd", "dd") );
                                                mapPar.put("tx_tipMon", rstRpt.getString("tx_tipMon"));
                                            }
                                            else{
                                                mapPar.put("fe_car", "" );
                                                mapPar.put("tx_tipMon", "");
                                            }
                                            mapPar.put("strSQLRep", strSQLRep);
                                            mapPar.put("strRutRpt", strRutRpt);
                                            rstRpt.close();
                                            rstRpt=null;                                            
                                            stmRpt.close();
                                            stmRpt=null;
                                        }
                                        //</editor-fold> 
                                        break;
                                        
                                }
                                if(blnRpt)
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                            }
                        }
                    }
                }
                conRpt.close();
                conRpt=null;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    private String getNomEmp(int intCodEmp) {
        String strNomEmp= "";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSQL = " SELECT tx_nom FROM tbm_emp WHERE co_emp=" + intCodEmp;
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    strNomEmp = rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);} 
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return strNomEmp;
    }

    private String getRucEmp(int intCodEmp) {
        String strRucEmp= "";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSQL=" SELECT tx_ruc FROM tbm_emp WHERE co_emp=" + intCodEmp;
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) {
                    strRucEmp = rstLoc.getString("tx_ruc");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);} 
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return strRucEmp;
    }   



    
}
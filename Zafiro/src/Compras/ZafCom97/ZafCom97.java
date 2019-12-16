/*
 * ZafCom97.java
 *
 * Created on 26 de diciembre de 2017, 08:54 AM
 */
package Compras.ZafCom97;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafGetDat.ZafDatBod;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafUtil.UltDocPrint;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafCom97 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_ITM=1;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT_ITM=2;                 //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALT_ITM_DOS=3;             //Código Alterno 2 del item 
    static final int INT_TBL_DAT_BUT_ITM=4;                     //Botón del item.
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_UNI_MED=6;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_STK_ACT=7;                     //Cantidad Stock Actual.
    static final int INT_TBL_DAT_DIS_ACT=8;                     //Cantidad disponible.
    static final int INT_TBL_DAT_CAN_EGR=9;                     //Cantidad egresada.
    static final int INT_TBL_DAT_CAN_ING=10;                    //Cantidad ingresada.
    static final int INT_TBL_DAT_COS_UNI=11;                    //Costo unitario.
    static final int INT_TBL_DAT_COS_TOT=12;                    //Costo total 
    static final int INT_TBL_DAT_EST_SER=13;                    //Estado de Item de Servicio.
    
    //Constantes: Asiento de Diario.
    private Vector vecDatDia, vecRegDia;
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
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceCon=0;   
    
    //Contendedor: Items ZafStkInv MovimientoStock
    private static final int INT_ARL_STK_INV_COD_ITM_GRP = 0;
    private static final int INT_ARL_STK_INV_COD_ITM_EMP = 1;
    private static final int INT_ARL_STK_INV_COD_ITM_MAE = 2;
    private static final int INT_ARL_STK_INV_COD_LET_ITM = 3;
    private static final int INT_ARL_STK_INV_CAN_ITM = 4;
    private static final int INT_ARL_STK_INV_COD_BOD_EMP = 5;
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    
    /**
     * Indice que permite obtener el nombre del campo que se desea actualizar
     * @param indiceNombreCampo 
     *          <HTML>
     *              <BR>  0: Actualiza en campo "nd_stkAct"
     *              <BR>  1: Actualiza en campo "nd_canPerIng"
     *              <BR>  2: Actualiza en campo "nd_canPerEgr"
     *              <BR>  3: Actualiza en campo "nd_canBodIng"
     *              <BR>  4: Actualiza en campo "nd_canBodEgr"
     *              <BR>  5: Actualiza en campo "nd_canDesIng"
     *              <BR>  6: Actualiza en campo "nd_canDesEgr"
     *              <BR>  7: Actualiza en campo "nd_canTra"
     *              <BR>  8: Actualiza en campo "nd_canRev"
     *              <BR>  9: Actualiza en campo "nd_canRes"
     *              <BR> 10: Actualiza en campo "nd_canDis"
     *          </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    final int INT_ARL_STK_INV_STK_ACT=0;      // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
    final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng
    final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr
    final int INT_ARL_STK_INV_CAN_DES_ENT_BOD=5;
    final int INT_ARL_STK_INV_CAN_DES_ENT_CLI=6;
    final int INT_ARL_STK_INV_CAN_TRA=7;
    final int INT_ARL_STK_INV_CAN_REV=8;
    final int INT_ARL_STK_INV_CAN_RES=9;
    final int INT_ARL_STK_INV_CAN_DIS=10;     // nd_canDis
    final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafStkInv objStkInv;
    private ZafDatBod objDatBod;
    private ZafUtil objUti;
    private ZafSegMovInv objSegMovInv;
    private UltDocPrint objUltDocPrint2;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtEgr;                 //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtIng;                 //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtCosUni;              //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenCon vcoBod;                                   //Ventana de consulta "Bodega".
    private MiToolBar objTooBar;                                //Barra de botones.
    private ZafDocLis objDocLis;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafAsiDia objAsiDia;
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.

    private Vector vecDat, vecAux, vecCab, vecReg;
    private Date datFecDoc;                                     //Fecha del documento.
    private Date datFecAux;                                     //Auxiliar: Para almacenar fechas.
    private boolean blnHayCam;                                  //Determina si hay cambios en el formulario.

    private String strSQL, strAux;
    private String strCodBod, strNomBod;                           //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc, strDesLarTipDoc, strNatTipDoc; //Contenido del campo al obtener el foco.
    private String strNumCtaDeb="", strNomCtaDeb="";
    private String strNumCtaHab="", strNomCtaHab="";
    private String strRevItm="";
    
    private int intCodEmp, intCodLoc;                              //Código de la empresa y local.
    private int intCodCtaDeb=0;
    private int intCodCtaHab=0;
    
    private Object objCodSeg;
    private BigDecimal bgdSig=new BigDecimal("1");                 //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    
    private int intSecGrp, intSecEmp;
    
    /**
     * Crea una nueva instancia de la clase ZafCom27.
     * @param obj El objeto ZafParSis.
     */
    public ZafCom97(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
                        
            if(intCodEmp!=objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                if (!configurarFrm())
                    exitForm();
                agregarDocLis();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * Crea una nueva instancia de la clase ZafCom27 y carga el documento especificado.
     * @param obj El objeto ZafParSis.
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public ZafCom97(ZafParSis obj, Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento)
    {
        this(obj);
        cargarDocumento(codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento);
    }

    /**
     * Esta función carga el documento especificado.
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public void cargarDocumento(Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento)
    {
        intCodEmp=codigoEmpresa.intValue();
        intCodLoc=codigoLocal.intValue();
        txtCodTipDoc.setText(codigoTipoDocumento.toString());
        txtCodDoc.setText(codigoDocumento.toString());
        objTooBar.setVisibleModificar(false);
        objTooBar.setVisibleEliminar(false);
        objTooBar.setVisibleAnular(false);
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1");
            lblTit.setText(strAux);
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 4, 100, 20);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            objUltDocPrint2 = new UltDocPrint(objParSis);
            objTooBar=new MiToolBar(this);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleAnular(false);
            panBar.add(objTooBar);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            if (!objPerUsr.isOpcionEnabled(4182)) 
            {
                objTooBar.setVisibleInsertar(false);
            }
            if (!objPerUsr.isOpcionEnabled(4183)) 
            {
                objTooBar.setVisibleConsultar(false);
            }
            if (!objPerUsr.isOpcionEnabled(4187)) 
            {
                objTooBar.setVisibleImprimir(false);
            }
            if (!objPerUsr.isOpcionEnabled(4188)) 
            {
                objTooBar.setVisibleVistaPreliminar(false);
            }
            
            objDocLis=new ZafDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objStkInv = new ZafStkInv(objParSis);
            objDatBod = new ZafDatBod(objParSis, this);
            objAsiDia = new ZafAsiDia(objParSis);
            
            //***********************************************************************
            //Asiento de diario
            vecDatDia = new Vector();
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
               public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                  if (txtCodTipDoc.getText().equals(""))
                      objAsiDia.setCodigoTipoDocumento(-1);
                  else
                      objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
             });
            
            objAsiDia.setEditable(true);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            //***********************************************************************            

            //Campos
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            //Configurar ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConItm();
            configurarVenConBod();
            //Configurar los JTables.
            configurarTblDat();
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
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
            vecCab=new Vector(14);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_STK_ACT,"Stock");
            vecCab.add(INT_TBL_DAT_DIS_ACT,"Disponible");
            vecCab.add(INT_TBL_DAT_CAN_EGR,"Egreso");
            vecCab.add(INT_TBL_DAT_CAN_ING,"Ingreso");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT,"Cos.Tot.");
            vecCab.add(INT_TBL_DAT_EST_SER,"Servicio");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_STK_ACT, ZafTblMod.INT_COL_DBL, new Integer(0), null); 
            objTblMod.setColumnDataType(INT_TBL_DAT_DIS_ACT, ZafTblMod.INT_COL_DBL, new Integer(0), null); 
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_EGR, ZafTblMod.INT_COL_DBL, new Integer(0), null); 
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_ING, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_UNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_TOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            //objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(165);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DIS_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK_ACT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DIS_ACT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_SER, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_CAN_EGR);
            vecAux.add("" + INT_TBL_DAT_CAN_ING);
            vecAux.add("" + INT_TBL_DAT_COS_UNI);  //Editable para ingresos.
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
                        
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            arlAux.add("" + INT_TBL_DAT_COS_UNI);
            arlAux.add("" + INT_TBL_DAT_COS_TOT);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            //objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios()); 
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIS_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                        
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[9];
            intColVen[0]=1;  //co_itm
            intColVen[1]=2;  //tx_codAlt
            intColVen[2]=3;  //tx_codAlt2
            intColVen[3]=4;  //tx_nomItm
            intColVen[4]=5;  //tx_desCor
            intColVen[5]=6;  //nd_stkAct
            intColVen[6]=7;  //nd_canDis
            intColVen[7]=8;  //nd_cosUni
            intColVen[8]=9;  //st_ser
            int intColTbl[]=new int[9];
            intColTbl[0]=INT_TBL_DAT_COD_ITM;
            intColTbl[1]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM_DOS;
            intColTbl[3]=INT_TBL_DAT_NOM_ITM;
            intColTbl[4]=INT_TBL_DAT_UNI_MED;
            intColTbl[5]=INT_TBL_DAT_STK_ACT;
            intColTbl[6]=INT_TBL_DAT_DIS_ACT;
            intColTbl[7]=INT_TBL_DAT_COS_UNI;
            intColTbl[8]=INT_TBL_DAT_EST_SER;
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (txtCodBod.getText().equals(""))
                    {
                        mostrarMsgInf("<HTML>Debe seleccionar una bodega antes de ingresar un item.<BR>Seleccione una bodega y vuelva intentarlo.</BR></HTML>");
                        objTblCelEdiTxtVcoItm.setCancelarEdicion(true);
                        txtCodBod.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //configurarVenConItm();
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setCriterio1(11);
                    vcoItm.setCondicionesSQL(" AND a2.co_bod=" + txtCodBod.getText());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel = tblDat.getSelectedRow();
                    if(!objTblMod.isRowEmpty(intFilSel)){
                        if (objTblCelEdiTxtVcoItm.isConsultaAceptada())
                        {
                            if (!objUti.parseString(objTblCelEdiTxtVcoItm.getCellEditorValue()).equals(""))
                            {
                                //Validar que no se repita el item.
                                if(!validaExiCodAlt()){                                
                                    //Valida stock y disponible del item.
                                    if (validaExiStkDis()){
                                        //Valida que costos negativos.
                                        if(validaExiCosNeg()){
                                            if(strNatTipDoc.equals("E")){
                                                objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_CAN_EGR);
                                                objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_EGR);  
                                            }
                                            else{
                                                objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_CAN_ING);
                                                objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_ING);  
                                            }
                                        }
                                    }
                                }                                
                                calcularTotFil();
                                calcularValTotDoc();
                                generarAsiDia(); 
                            }
                        }
                    }
                }
            });
            
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (txtCodBod.getText().equals(""))
                    {
                        mostrarMsgInf("<HTML>Debe seleccionar una bodega antes de ingresar un item.<BR>Seleccione una bodega y vuelva intentarlo.</BR></HTML>");
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                        txtCodBod.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //configurarVenConItm();
                    vcoItm.setCondicionesSQL(" AND a2.co_bod=" + txtCodBod.getText());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel = tblDat.getSelectedRow();
                    if(!objTblMod.isRowEmpty(intFilSel)){
                        if (objTblCelEdiButVcoItm.isConsultaAceptada())
                        {
                            //Validar que no se repita el item.
                            if(!validaExiCodAlt()){
                                //Valida stock y disponible del item.
                                if (validaExiStkDis()){
                                    if(validaExiCosNeg()){
                                        if(strNatTipDoc.equals("E")){
                                            objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_CAN_EGR);
                                            objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_EGR);  
                                        }
                                        else{
                                            objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_CAN_ING);
                                            objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_ING);  
                                        }
                                    }
                                }
                            }
                            calcularTotFil();
                            calcularValTotDoc();
                            generarAsiDia(); 
                        }
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            
            //Cantidad: Egreso
            objTblCelEdiTxtEgr=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellEditor(objTblCelEdiTxtEgr);
            objTblCelEdiTxtEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(strNatTipDoc.equals("E")){
                        objTblCelEdiTxtEgr.setCancelarEdicion(false);
                    }
                    else{
                        objTblCelEdiTxtEgr.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>Ingrese la cantidad en la columna de ingreso.</HTML>");
                        objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_ING);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    validaCanMovPer();
                    calcularTotFil();
                    calcularValTotDoc();
                    generarAsiDia(); 
                }
            });
            
            //Cantidad: Ingreso
            objTblCelEdiTxtIng=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellEditor(objTblCelEdiTxtIng);
              objTblCelEdiTxtIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(strNatTipDoc.equals("I")){
                        objTblCelEdiTxtIng.setCancelarEdicion(false);
                    }
                    else{
                        objTblCelEdiTxtIng.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>Ingrese la cantidad en la columna de egreso.</HTML>");
                        objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_EGR);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTotFil();
                    calcularValTotDoc();
                    generarAsiDia(); 
                }
            });
              
            //Costo Unitario.
            objTblCelEdiTxtCosUni=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellEditor(objTblCelEdiTxtCosUni);
            objTblCelEdiTxtCosUni.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(strNatTipDoc.equals("I")){
                        objTblCelEdiTxtCosUni.setCancelarEdicion(false);
                    }
                    else{
                        objTblCelEdiTxtCosUni.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>No está permitido ingresar un costo unitario cuando es un documento de egreso.</HTML>");
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTotFil();
                    calcularValTotDoc();
                    generarAsiDia(); 
                }
            });
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
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
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM_DOS:
                    strMsg="Código alterno 2 del item";
                    break;                    
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_STK_ACT:
                    strMsg="Stock Actual";
                    break;
                case INT_TBL_DAT_DIS_ACT:
                    strMsg="Disponible Actual";
                    break;                    
                case INT_TBL_DAT_CAN_EGR:
                    strMsg="Cantidad (Egreso)";
                    break;
                case INT_TBL_DAT_CAN_ING:
                    strMsg="Cantidad (Ingreso)";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg="Costo total ";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularValTotDoc();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    calcularValTotDoc();
                    break;
            }
        }
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
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n'){
                        txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    }
                    bgdSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strNatTipDoc=rst.getString("tx_natDoc");
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
            arlCam.add("a1.st_merIngEgrFisBod");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Est.Mer.Ing.Egr.Fis.Bod.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("325");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
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
     * mostrar las "Bodegas".
     */
    private boolean configurarVenConBod()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("485");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom";
                strSQL+=" FROM tbm_bod AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_bod";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom";
                strSQL+=" FROM tbm_bod AS a1";
                strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
                strSQL+=" WHERE a2.co_emp=" + intCodEmp;
                strSQL+=" AND a2.co_loc=" + intCodLoc;
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_bod";
            }
            vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a3.tx_desCor");
            arlCam.add("a2.nd_stkAct");
            arlCam.add("a2.nd_canDis");
            arlCam.add("a1.nd_cosUni");
            arlCam.add("a1.st_ser");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Uni.Med.");
            arlAli.add("Stock");
            arlAli.add("Disponible");
            arlAli.add("Costo");
            arlAli.add("Servicio");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("225");
            arlAncCol.add("55");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("55");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor, a2.nd_stkAct, a2.nd_canDis, a1.nd_cosUni, a1.st_ser";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER); //tx_codAlt2
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER); //tx_desCor
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(8, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(9, javax.swing.JLabel.CENTER); //st_ser
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        strNatTipDoc=vcoTipDoc.getValueAt(5);
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                            txtCodBod.setText("");
                            txtNomBod.setText("");     
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {                                           
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        strNatTipDoc=vcoTipDoc.getValueAt(5);
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                            txtCodBod.setText("");
                            txtNomBod.setText("");     
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {                                            
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            strNatTipDoc=vcoTipDoc.getValueAt(5);
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                                txtCodBod.setText("");
                                txtNomBod.setText("");     
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            strNatTipDoc=vcoTipDoc.getValueAt(5);
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                                txtCodBod.setText("");
                                txtNomBod.setText("");     
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            strNatTipDoc=vcoTipDoc.getValueAt(5);
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                                txtCodBod.setText("");
                                txtNomBod.setText("");     
                            }
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
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
    private boolean mostrarVenConBod(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBod.setCampoBusqueda(1);
                    vcoBod.setVisible(true);
                    if (vcoBod.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                                                
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado la "Bodega".
                        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtCodBod.selectAll();
                        txtCodBod.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText()))
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado la "Bodega".
                        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtCodBod.selectAll();
                        txtCodBod.requestFocus();                        
                    }
                    else
                    {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.setVisible(true);
                        if (vcoBod.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado la "Bodega".
                            if (!txtCodBod.getText().equalsIgnoreCase(strCodBod))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
                            txtCodBod.selectAll();
                            txtCodBod.requestFocus();                            
                        }
                        else
                        {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText()))
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                                             
                        //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado la "Bodega".
                        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtCodBod.selectAll();
                        txtCodBod.requestFocus();
                    }
                    else
                    {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.setVisible(true);
                        if (vcoBod.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                            //Limpiar "Detalle", "Bodega" y "Diario" sólo si se cambiado la "Bodega".
                            if (!txtCodBod.getText().equalsIgnoreCase(strCodBod))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
                            txtCodBod.selectAll();
                            txtCodBod.requestFocus();                            
                        }
                        else
                        {
                            txtNomBod.setText(strNomBod);
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
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
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

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 64));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 114, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(84, 4, 32, 20);

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
        txtDesCorTipDoc.setBounds(116, 4, 56, 20);

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
        txtDesLarTipDoc.setBounds(172, 4, 236, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(408, 4, 20, 20);

        lblBod.setText("Bodega:");
        lblBod.setToolTipText("Bodega");
        panGenCab.add(lblBod);
        lblBod.setBounds(0, 24, 114, 20);

        txtCodBod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        panGenCab.add(txtCodBod);
        txtCodBod.setBounds(116, 24, 56, 20);

        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        panGenCab.add(txtNomBod);
        txtNomBod.setBounds(172, 24, 236, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panGenCab.add(butBod);
        butBod.setBounds(408, 24, 20, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 44, 114, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(116, 44, 80, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(445, 4, 125, 20);

        lblNumDoc.setText("Número de documento:");
        lblNumDoc.setToolTipText("Número de documento");
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(445, 24, 125, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(570, 24, 100, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setPreferredSize(new java.awt.Dimension(60, 14));
        panGenCab.add(lblValDoc);
        lblValDoc.setBounds(445, 44, 125, 20);
        panGenCab.add(txtValDoc);
        txtValDoc.setBounds(570, 44, 100, 20);

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

        panGenObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        vcoBod.limpiar();
        configurarVenConBod();
        //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
        if (objParSis.getCodigoUsuario()==1)
        {
            mostrarVenConBod(0);
        }
        else
        {
            if (txtCodTipDoc.getText().equals(""))
            {
                mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
            }
            else
            {
                vcoBod.setCondicionesSQL(" AND a2.co_tipDoc=" + txtCodTipDoc.getText());
                mostrarVenConBod(0);
            }
        }
    }//GEN-LAST:event_butBodActionPerformed

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (txtNomBod.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomBod.getText().equalsIgnoreCase(strNomBod))
            {
                if (txtNomBod.getText().equals(""))
                {
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                }
                else
                {
                    configurarVenConBod();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        mostrarVenConBod(2);
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            txtNomBod.setText("");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_tipDoc=" + txtCodTipDoc.getText());
                            mostrarVenConBod(2);
                        }
                    }
                }
            }
            else
                txtNomBod.setText(strNomBod);
        }
    }//GEN-LAST:event_txtNomBodFocusLost

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (txtCodBod.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodBod.getText().equalsIgnoreCase(strCodBod))
            {
                if (txtCodBod.getText().equals(""))
                {
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                }
                else
                {
                    configurarVenConBod();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        mostrarVenConBod(1);
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            txtCodBod.setText("");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND a2.co_tipDoc=" + txtCodTipDoc.getText());
                            mostrarVenConBod(1);
                        }
                    }
                }
            }
            else
                txtCodBod.setText(strCodBod);
        }
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

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
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    txtCodBod.setText("");
                    txtNomBod.setText("");
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
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    txtCodBod.setText("");
                    txtNomBod.setText("");
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
        configurarVenConTipDoc();        
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e)
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
    private javax.swing.JButton butBod;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
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
            dtpFecDoc.setText("");
            txtCodBod.setText("");
            txtNomBod.setText("");
            txtNumDoc.setText("");
            txtCodDoc.setText("");
            objTblMod.removeAllRows();
            txaObs1.setText("");
            txaObs2.setText("");
            txtValDoc.setText("");
            strNatTipDoc="";
            strRevItm="";
        }
        catch (Exception e)
        {
            blnRes=false;
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
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
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
        if (blnHayCam || objTblMod.isDataModelChanged())
        {
            strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux))
            {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado())
                    {
                        case 'n': //Insertar
                            blnRes=objTooBar.beforeInsertar();
                            if (blnRes)
                                blnRes=objTooBar.insertar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    if (objTooBar.getBotonClick()==objTooBar.INT_BUT_CON || objTooBar.getBotonClick()==objTooBar.INT_BUT_ELI || objTooBar.getBotonClick()==objTooBar.INT_BUT_ANU || objTooBar.getBotonClick()==objTooBar.INT_BUT_IMP || objTooBar.getBotonClick()==objTooBar.INT_BUT_VIS)
                    {
                        blnRes=cargarReg();
                    }
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
                    break;
            }
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

        public void clickInicio()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
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
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
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
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        //if (blnHayCam || objTblMod.isDataModelChanged()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
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
                 if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
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
                limpiarFrm();
                txtCodDoc.setEditable(false);
                txtValDoc.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
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
                    break;
            }
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
        }
        
        public void clickModificar()
        {
        }        
        public void clickEliminar()
        {
        }
        public void clickAnular()
        {
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
            return true;
        }
        public boolean eliminar()
        {
            return true;
        }        
        public boolean anular()
        {
            return true;
        }
        public boolean imprimir()
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }
        public boolean vistaPreliminar()
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        } 
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                objTblMod.clearRowHeaderRaise();
                limpiarFrm();
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }
        
        public boolean aceptar()
        {
            return true;
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
            return true;
        }

        public boolean beforeEliminar()
        {
            return true;
        }

        public boolean beforeAnular()
        {
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
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            objTblMod.initRowsState();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar()
        {
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

        public boolean beforeClickInicio()
        {
            return isRegPro();
        }

        public boolean beforeClickAnterior()
        {
            return isRegPro();
        }

        public boolean beforeClickSiguiente()
        {
            return isRegPro();
        }

        public boolean beforeClickFin()
        {
            return isRegPro();
        }

        public boolean beforeClickInsertar()
        {
            return isRegPro();
        }

        public boolean beforeClickConsultar()
        {
            return isRegPro();
        }

        public boolean beforeClickEliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickAnular()
        {
            return isRegPro();
        }

        public boolean beforeClickImprimir()
        {
            return isRegPro();
        }

        public boolean beforeClickVistaPreliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickCancelar()
        {
            return isRegPro();
        }
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
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
        //Validar la "Bodega".
        if (txtCodBod.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtCodBod.requestFocus();
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
        //Validar el "Número de documento".
        if (txtNumDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nùmero de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        //Validar que el "Número de documento" no se repita.
        if (!txtNumDoc.getText().equals(""))
        {
            strSQL ="";
            strSQL+=" SELECT a1.ne_numdoc";
            strSQL+=" FROM tbm_cabMovInv AS a1";
            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
            strSQL+=" AND a1.co_loc=" + intCodLoc;
            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" AND a1.ne_numdoc='" + txtNumDoc.getText().replaceAll("'", "''") + "'";
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
        
        objTblMod.removeEmptyRows();
        
        //Validar "Cantidades Ingreso/Egreso"
        if (!validaCanIngEgr()) 
        {
            mostrarMsgInf("<HTML>Existe un detalle sin cantidades<BR>Ingrese una cantidad y vuelva a intentarlo.</HTML>"); 
            if(strNatTipDoc.equals("E")){
                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_EGR); 
            }
            else{
                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_ING); 
            }
            return false;
        }
               
        //Validar "Costo Unitario"
        if (!validaExiCosUni()) 
        {
            mostrarMsgInf("<HTML>Existe un detalle sin costo.<BR>Ingrese un costo unitario y vuelva a intentarlo.</HTML>"); 
            objTblEdi.seleccionarCelda(INT_TBL_DAT_COS_UNI);  
            return false;
        }
        
        //Validar que el JTable de detalle esté completo.
        if (!objTblMod.isAllRowsComplete())
        {
            mostrarMsgInf("<HTML>El detalle del documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.changeSelection(0, INT_TBL_DAT_COD_ALT_ITM, true, true);
            tblDat.requestFocus();
            return false;
        }

        return true;
    }
    
    /**
     * Esta función calcula el total de la fila.
     */
    private void calcularTotFil()
    {
        int intFilSel;
        BigDecimal bgdCan=BigDecimal.ZERO;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        intFilSel=tblDat.getSelectedRow();
        if (intFilSel!=-1)
        {
            if (strNatTipDoc.equals("E"))  {
                bgdCan=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR).toString()));
                bgdCan=bgdCan.multiply(bgdSig);  //Cambios del signo del Costo total. Para cuando sean egresos, aparezca en color rojo.
            }
            else {
                bgdCan=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING).toString()));
            }
            bgdCosUni=new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI).toString()));
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCan.multiply(bgdCosUni), objParSis.getDecimalesBaseDatos()), intFilSel, INT_TBL_DAT_COS_TOT);
        }
    }
    
    /**
     * Esta función calcula el total del documento.
     */
    private void calcularValTotDoc()
    {
        int intFil, i;
        BigDecimal bgdValTot=BigDecimal.ZERO;
        try
        {
            intFil=objTblMod.getRowCount();
            for (i=0; i<intFil; i++)
            {
                bgdValTot=bgdValTot.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString())));
            }
            //Ing.Eddye Lino 03/Ene/2017; Se debe almacenar el valor de asiento de diario, detMovInv, cabMovInv con 6 decimales por el momento.
            txtValDoc.setText("" + objUti.redondearBigDecimal(bgdValTot, objParSis.getDecimalesBaseDatos()));
        }
        catch (NumberFormatException e)
        {
            txtValDoc.setText("[ERROR]");
        }
    }   
        
    /**
     * Esta función valida que no se repita el item ingresado.
     * Si el item existe muestra un mensaje y lo borra para evitar problemas de stock.
     * @return true: Si el item existe.
     * <BR>false: En el caso contrario.
     */
    private boolean validaExiCodAlt()
    {
        int intFilSel;
        String strCodAlt;
        boolean blnRes=false;
        try
        {
            intFilSel=tblDat.getSelectedRow();
            strCodAlt=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT_ITM));
            for (int i=0; i<objTblMod.getRowCountTrue(); i++)
            {
                if (i!=intFilSel)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)).equals(strCodAlt))
                    {
                        blnRes=true;
                        mostrarMsgInf("<HTML>El item <FONT COLOR=\"blue\">" + strCodAlt + "</FONT> fue ingresado en la fila " + (i+1) + ".<BR>El item será eliminado para evitar problemas de stock.</HTML>");
                        objTblMod.removeRow(intFilSel);
                        tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                        objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_COD_ALT_ITM);
                        break;
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
     * Función que valida que el item seleccionado tenga el stock y disponible suficiente.
     * @return true: Cuando existe el stock y disponible suficiente para realizar la transacción.
     * <BR> false: Caso contrario.
     */
    private boolean validaExiStkDis()
    {
        boolean blnRes=true;
        int intFilSel;
        BigDecimal bgdStk=BigDecimal.ZERO;
        BigDecimal bgdDis=BigDecimal.ZERO;
        String strCodAlt="";
        try
        {
            if(strNatTipDoc.equals("E")) //Solo EGBOFA 
            {
                intFilSel=tblDat.getSelectedRow();
                bgdStk= new BigDecimal( (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_STK_ACT)==null)?"0":objTblMod.getValueAt(intFilSel,INT_TBL_DAT_STK_ACT).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_STK_ACT).toString());
                bgdDis= new BigDecimal( (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_DIS_ACT)==null)?"0":objTblMod.getValueAt(intFilSel,INT_TBL_DAT_DIS_ACT).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DIS_ACT).toString());
                strCodAlt=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT_ITM).toString();

                if(bgdStk.compareTo(BigDecimal.ZERO)<=0){
                    mostrarMsgInf("<HTML>El item <FONT COLOR=\"blue\">" + strCodAlt + "</FONT> no tiene suficiente stock.<BR>El item será eliminado para evitar problemas de stock.</HTML>");
                    blnRes=false;
                }
                else if(bgdDis.compareTo(BigDecimal.ZERO)<=0){
                    mostrarMsgInf("<HTML>El item <FONT COLOR=\"blue\">" + strCodAlt + "</FONT> no tiene cantidad disponible suficiente.<BR>El item será eliminado para evitar problemas en los disponibles.</HTML>");
                    blnRes=false;
                }

                if(!blnRes){
                    objTblMod.removeRow(intFilSel);
                    tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                    objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_COD_ALT_ITM);            
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
     * Función que valida la cantidad ingresada sea menor o igual a la disponible.
     * @return true: Cuando existe el stock y disponible suficiente para realizar la transacción.
     * <BR> false: Caso contrario.
     */
    private boolean validaCanMovPer()
    {
        boolean blnRes=true;
        int intFilSel;
        BigDecimal bgdStk=BigDecimal.ZERO;
        BigDecimal bgdDis=BigDecimal.ZERO;
        BigDecimal bgdCanMov=BigDecimal.ZERO;
        try
        {
            if(strNatTipDoc.equals("E")) //Solo EGBOFA 
            {
                intFilSel=tblDat.getSelectedRow();
                bgdStk= new BigDecimal( (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_STK_ACT)==null)?"0":objTblMod.getValueAt(intFilSel,INT_TBL_DAT_STK_ACT).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_STK_ACT).toString());
                bgdDis= new BigDecimal( (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_DIS_ACT)==null)?"0":objTblMod.getValueAt(intFilSel,INT_TBL_DAT_DIS_ACT).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_DIS_ACT).toString());
                bgdCanMov= new BigDecimal( (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_CAN_EGR)==null)?"0":objTblMod.getValueAt(intFilSel,INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR).toString());
                if( (bgdCanMov.compareTo(bgdStk)>0) ||  (bgdCanMov.compareTo(bgdDis)>0) ) {
                    mostrarMsgInf("La cantidad solicitada es menor a lo disponible");
                    blnRes=false;
                }

                if(!blnRes) {
                    tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                    objTblMod.setValueAt(bgdDis, intFilSel, INT_TBL_DAT_CAN_EGR);
                    objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_CAN_EGR);    
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
     * Función que valida que el item seleccionado tenga un costo mayor a 0.
     * @return true: Cuando tiene un costo mayor a 0.
     * <BR> false: Caso contrario.
     */
    private boolean validaExiCosNeg()
    {
        boolean blnRes=true;
        int intFilSel;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        String strCodAlt="";
        try
        {
            intFilSel=tblDat.getSelectedRow();
            bgdCosUni= new BigDecimal( (objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COS_UNI)==null)?"0":objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI).toString());
            strCodAlt=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT_ITM).toString();

            if(strNatTipDoc.equals("E")){
                if(bgdCosUni.compareTo(BigDecimal.ZERO)<=0){
                    blnRes=false;
                }
            }
            else{
                if(bgdCosUni.compareTo(BigDecimal.ZERO)<0){
                    blnRes=false;
                }
            }
            
            if(!blnRes){
                mostrarMsgInf("<HTML>El item <FONT COLOR=\"blue\">" + strCodAlt + "</FONT> tiene costos negativos.<BR>El item será eliminado para evitar problemas de costos.</HTML>");
                objTblMod.removeRow(intFilSel);
                tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                objTblEdi.seleccionarCelda(intFilSel, INT_TBL_DAT_COD_ALT_ITM);  
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
     * Función que valida que el item seleccionado tenga costo unitario
     * @return true: Cuando tiene costo unitario.
     * <BR> false: Caso contrario.
     */
    private boolean validaExiCosUni()
    {
        boolean blnRes=true;
        BigDecimal bgdCosUni=BigDecimal.ZERO;
        try
        {
            for (int i=0; i<objTblMod.getRowCountTrue(); i++)
            {
                bgdCosUni=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI)==null)?"0":objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()),objParSis.getDecimalesBaseDatos());
                if(bgdCosUni.compareTo(BigDecimal.ZERO)<=0){
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
     * Función que valida que el item seleccionado tenga cantidad ingresada, ya sea de ingreso o egreso.
     * @return true: Cuando tiene cantidades ingresadas
     * <BR> false: Caso contrario.
     */
    private boolean validaCanIngEgr()
    {
        boolean blnRes=true;
        BigDecimal bgdCanMov=BigDecimal.ZERO;
        try
        {
            for (int i=0; i<objTblMod.getRowCountTrue(); i++)
            {
                if(strNatTipDoc.equals("E")){
                    bgdCanMov=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)==null)?"0":objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString()),objParSis.getDecimalesBaseDatos());
                }
                else{
                    bgdCanMov=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING)==null)?"0":objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING).toString()),objParSis.getDecimalesBaseDatos());
                }
                
                if(bgdCanMov.compareTo(BigDecimal.ZERO)<=0){
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
     * Función que obtiene las ultimas secuencias por empresa y grupo.
     * Además de obtener las secuencias, actualiza el campo ne_secUltDocTbmCabMovInv.
     * @return 
     */
    private boolean getSecuencias(){
        boolean blnRes=true;
        try{
            if(con!=null){
                intSecGrp=objUltDocPrint2.getNumSecDoc(con, objParSis.getCodigoEmpresaGrupo() ); //La empresa Grupo.
                intSecEmp=objUltDocPrint2.getNumSecDoc(con, intCodEmp );                         //La empresa por la que ingresa.
           }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    /**
     * Genera Asiento de Diario del documento
     * @return 
     */
    private boolean generarAsiDia()
    {  
        boolean blnRes=false;
        try
        {
            //Se inicializa el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();
            if(cargarCtaCon()){
                if(cargarDetAsiDia()){
                    blnRes=true;
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
     * Función que carga las cuentas contables a utilizarse en el asiento de diario.
     * @return 
     */
    private boolean cargarCtaCon(){
        boolean blnRes=true;
        intCodCtaDeb=0;
        strNumCtaDeb="";
        strNomCtaDeb="";
        intCodCtaHab=0;
        strNumCtaHab="";
        strNomCtaHab="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();               
        
                //Cuenta Debe 
                strSQL ="";
                strSQL+=" SELECT x.co_ctaDeb, x.tx_codCtaDeb, x.tx_nomCtaDeb";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaDeb, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaDeb, a2.tx_desLar AS tx_nomCtaDeb";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS x";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaDeb=rst.getInt("co_ctaDeb");
                    strNumCtaDeb=rst.getString("tx_codCtaDeb");
                    strNomCtaDeb=rst.getString("tx_nomCtaDeb");
                }
                
                //Cuenta Haber
                strSQL ="";
                strSQL+=" SELECT x.co_ctaHab, x.tx_codCtaHab, x.tx_nomCtaHab";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_ctaHab, a1.co_tipDoc, a2.tx_codCta AS tx_codCtaHab, a2.tx_desLar AS tx_nomCtaHab";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaHab=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";  
                strSQL+=" 	AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 	AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS x";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaHab=rst.getInt("co_ctaHab");
                    strNumCtaHab=rst.getString("tx_codCtaHab");
                    strNomCtaHab=rst.getString("tx_nomCtaHab");
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

   /**
     * Función que carga valores que tendrá el asiento de diario del DxP.
     * @return 
     */
    private boolean cargarDetAsiDia()
    {
        boolean blnRes=true;
        BigDecimal bgdVal=BigDecimal.ZERO;
        try
        {
            //Ing.Eddye Lino 03/Ene/2017; Se debe almacenar el valor de asiento de diario, detMovInv, cabMovInv con 6 decimales por el momento.
            bgdVal=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesBaseDatos());
            bgdVal=bgdVal.multiply(bgdSig);
            
            if( bgdVal.compareTo(BigDecimal.ZERO)>0  ){
                //Deber: Total
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaDeb);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaDeb);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaDeb);
                vecRegDia.add(INT_VEC_DIA_DEB, ""+ bgdVal );
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                
                //Haber: Total
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaHab);
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaHab);
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaHab);
                vecRegDia.add(INT_VEC_DIA_DEB, "0" );
                vecRegDia.add(INT_VEC_DIA_HAB, ""+ bgdVal);
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            
            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)2);
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
                        if (actualizaStkInv())
                        {
                            if(objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()),  txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy") )) 
                            {
                                if (objUti.set_ne_ultDoc_tbm_cabTipDoc(this, con, intCodEmp, intCodLoc, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtNumDoc.getText())))
                                {
                                    if(objSegMovInv.setSegMovInvCompra(con, objCodSeg, 5, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                                    {
                                        costearItm();
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
        int intCodUsr, intCodDoc;
        BigDecimal bgdValTot=BigDecimal.ZERO;
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

                getSecuencias();
                intCodUsr=objParSis.getCodigoUsuario();

                //Obtener el código para "co_doc".
                strSQL ="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intCodDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intCodDoc==-1)
                    return false;
                intCodDoc++;
                txtCodDoc.setText("" + intCodDoc);

                //Valor del documento 
                //Ing.Eddye Lino 03/Ene/2017; Se debe almacenar el valor de asiento de diario, detMovInv, cabMovInv con 6 decimales por el momento.
                bgdValTot=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesBaseDatos());
                if(strNatTipDoc.equals("E")){
                    if(bgdValTot.compareTo(BigDecimal.ZERO)>0){
                        bgdValTot=bgdValTot.multiply(bgdSig); 
                    }
                }
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabMovInv (co_emp, co_loc, co_tipDoc, co_doc, ne_secGrp, ne_secEmp, ne_numCot, ne_numDoc, tx_numPed";
                strSQL+="   , ne_numGui, co_dia, fe_doc, co_cli, tx_ruc, tx_nomCli, tx_dirCli, tx_telCli, tx_ciuCli, co_com, tx_nomVen, tx_ate, co_forPag";
                strSQL+="   , tx_desForPag, nd_sub, nd_porIva, nd_tot, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, co_cta, co_motDoc, co_mnu, tx_obs1 ,tx_obs2";
                strSQL+="   , st_reg, st_imp, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comMod, fe_con, tx_obs3, co_forRet, tx_vehRet, tx_choRet, st_regRep, st_conInv, st_tipDev, st_excDocConVenCon)";
                strSQL+=" VALUES (";
                strSQL+="  " +intCodEmp;  //co_emp
                strSQL+=", " + intCodLoc; //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipDoc
                strSQL+=", " + intCodDoc; //co_doc
                strSQL+=", " + intSecGrp; //ne_secGrp
                strSQL+=", " + intSecEmp; //ne_secEmp
                strSQL+=", 0"; //ne_numCot
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),3); //ne_numDoc
                strSQL+=", Null"; //tx_numPed
                strSQL+=", 0"; //ne_numGui
                strSQL+=", " + intCodDoc; //co_dia
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_doc
                strSQL+=", Null"; //co_cli
                strSQL+=", Null"; //tx_ruc
                strSQL+=", Null"; //tx_nomCli
                strSQL+=", Null"; //tx_dirCli
                strSQL+=", Null"; //tx_telCli
                strSQL+=", Null"; //tx_ciuCli
                strSQL+=", Null"; //co_com
                strSQL+=", Null"; //tx_nomVen
                strSQL+=", Null"; //tx_ate
                strSQL+=", Null"; //co_forPag
                strSQL+=", Null"; //tx_desForPag
                strSQL+=", " + bgdValTot; //nd_sub
                strSQL+=", 0"; //nd_porIva
                strSQL+=", " + bgdValTot; //nd_tot
                strSQL+=", Null"; //tx_ptoPar
                strSQL+=", Null"; //tx_tra
                strSQL+=", Null"; //co_motTra
                strSQL+=", Null"; //tx_desMotTra
                strSQL+=", Null"; //co_cta
                strSQL+=", Null"; //co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                strSQL+=", 'A'"; //st_reg
                strSQL+=", 'S'"; //st_imp
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+=", " + intCodUsr;     //co_usrIng
                strSQL+=", " + intCodUsr;     //co_usrMod
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'"; //tx_comIng
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'"; //tx_comMod
                strSQL+=", Null"; //fe_con
                strSQL+=", Null"; //tx_obs3
                strSQL+=", Null"; //co_forRet
                strSQL+=", Null"; //tx_vehRet
                strSQL+=", Null"; //tx_choRet
                strSQL+=", 'I'";  //st_regRep
                strSQL+=", 'F'";  //st_conInv
                strSQL+=", 'C'";  //st_tipDev
                strSQL+=", 'S'";  //st_excDocConVenCon
                strSQL+=")";
                System.out.println("insertarCab: "+strSQL);
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
        int i, j;
        String strCodTipDoc, strCodDoc;
        BigDecimal bgdCanMov=BigDecimal.ZERO;
        BigDecimal bgdValEgr=BigDecimal.ZERO;
        BigDecimal bgdValIng=BigDecimal.ZERO;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                arlDatStkInvItm = new ArrayList(); 
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;
                //Almacena los items que deben revisarse.
                strRevItm="";
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {                    
                    //Armar la sentencia SQL (Egreso).
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detMovInv (co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_numFil, co_itm, tx_codAlt, tx_codAlt2";
                    strSQL+=" , tx_nomItm, tx_uniMed, co_bod, tx_nomBodOrgDes, nd_can, nd_canOrg, nd_cosUni, nd_preUni, nd_porDes, st_ivaCom, nd_tot, nd_cosTot";
                    strSQL+=" , nd_exi, nd_valExi, nd_cosPro, nd_cosUniGrp, nd_cosTotGrp, nd_exiGrp, nd_valExiGrp, nd_cosProGrp, st_reg, st_merIngEgrFisBod, nd_canCon, co_itmAct";
                    strSQL+=" , tx_obs1, co_usrCon, st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + intCodEmp;    //co_emp
                    strSQL+=", " + intCodLoc;    //co_loc
                    strSQL+=", " + strCodTipDoc; //co_tipDoc
                    strSQL+=", " + strCodDoc;    //co_doc
                    strSQL+=", " + j;            //co_reg
                    strSQL+=", " + (i+1);        //ne_numfil
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM); //co_itm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM));    //tx_codAlt
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM_DOS)); //tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM));    //tx_nomItm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_UNI_MED));    //tx_uniMed
                    strSQL+=", " + txtCodBod.getText(); //co_bod
                    strSQL+=", '" + txtNomBod.getText() + "'";//tx_nomBodOrgDes         
                    bgdValEgr=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)==null)?"0":objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString()),objParSis.getDecimalesBaseDatos());
                    bgdValIng=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING)==null)?"0":objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING).toString()),objParSis.getDecimalesBaseDatos());
                    bgdCanMov=bgdValIng.subtract(bgdValEgr);
                         
                    if( (bgdValEgr.compareTo(BigDecimal.ZERO)>0) ){
                        strSQL+=", " + bgdValEgr.multiply(bgdSig); //nd_can
                    }
                    else if( (bgdValIng.compareTo(BigDecimal.ZERO)>0) ){
                        strSQL+=", " + bgdValIng.multiply(bgdSig); //nd_can
                    }
                  
                    strSQL+=", Null"; //nd_canOrg
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUni
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_preUni
                    strSQL+=", 0"; //nd_porDes
                    strSQL+=", 'N'"; //st_ivaCom
                    
                    //Ing.Eddye Lino 03/Ene/2017; Se debe almacenar el valor de asiento de diario, detMovInv, cabMovInv con 6 decimales por el momento.
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_tot        //Campo maneja (-)egresos/ (+)ingresos.
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTot     //Campo maneja (-)egresos/ (+)ingresos.
                    strSQL+=", Null"; //nd_exi
                    strSQL+=", Null"; //nd_valExi
                    strSQL+=", Null"; //nd_cosPro
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUniGrp
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTotGrp  //Campo maneja (-)egresos/ (+)ingresos.
                    
                    strSQL+=", Null"; //nd_exiGrp
                    strSQL+=", Null"; //nd_valExiGrp
                    strSQL+=", Null"; //nd_cosProGrp
                    strSQL+=", Null"; //st_reg
                    strSQL+=", 'N'";  //st_merIngEgrFisBod
                    strSQL+=", 0";    //nd_canCon
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM); //co_itmAct
                    strSQL+=", Null"; //tx_obs1
                    strSQL+=", Null"; //co_usrCon
                    strSQL+=", 'I'";  //st_regRep
                    strSQL+=")";
                    System.out.println("insertarDet: "+strSQL);
                    stm.executeUpdate(strSQL);
                    j++;
                    
                    //Genera contenedor que mueve inventario             
                    if(objTooBar.getEstado()=='n'){
                        if(!getDatItmMovStk(intCodEmp, Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString()), bgdCanMov, Integer.parseInt(txtCodBod.getText()))){
                            blnRes=false;
                        }
                    }
                }
                
                if(!blnRes){
                    mostrarMsgInf("<HTML>Problemas de stock y disponible en los items ingresados.<BR>Verifique el contenido de las filas y vuelva a intentarlo.<BR>"
                                + "<BR><CENTER><TABLE BORDER=1><tr><td> Items que necesitan revisión </td></tr>"
                                + " "+strRevItm+"</table><BR></CENTER></HTML>");                
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
     * Función que obtiene datos de los items a actualizarse en el inventario.
     * @param intCodEmp
     * @param intCodItmEmp
     * @param dlbCanMov
     * @param intCodBodEmp
     * @return 
     */
    private boolean getDatItmMovStk(int intCodEmp, int intCodItmEmp, BigDecimal bgdCanMov, int intCodBodEmp)
    {
        boolean blnRes=true;
        int intCodItmGrp=0, intCodItmMae=0, intCodBodGrp=0;
        String strCodLetItm="";
        
        BigDecimal bgdCanStkEmp=BigDecimal.ZERO;
        BigDecimal bgdCanDisEmp=BigDecimal.ZERO;
        BigDecimal bgdStkAux=BigDecimal.ZERO;
        BigDecimal bgdDisAux=BigDecimal.ZERO;
        try
        {
            if (con!=null)
            {
                intCodItmGrp=objStkInv.getCodItmGrp(intCodEmp, intCodItmEmp);
                intCodItmMae=objStkInv.getCodMaeItm(intCodEmp, intCodItmEmp);
                strCodLetItm=objStkInv.getCodLetItm(intCodEmp, intCodItmEmp);
                intCodBodGrp=objDatBod.getCodigoBodegaGrupo(intCodEmp, intCodBodEmp);

                bgdCanStkEmp = BigDecimal.valueOf(objStkInv.getStkItmEmp(con, intCodItmMae, intCodBodGrp, intCodEmp ));
                bgdCanDisEmp = BigDecimal.valueOf(objStkInv.getDisItmEmp(con, intCodItmMae, intCodBodGrp, intCodEmp ));
                bgdStkAux = bgdCanStkEmp.add(bgdCanMov);
                bgdDisAux = bgdCanDisEmp.add(bgdCanMov);
                
                //Valida que Disponible no sea mayor que el stock. 
                if( (bgdStkAux.compareTo(BigDecimal.ZERO)>= 0) && (bgdDisAux.compareTo(BigDecimal.ZERO)>= 0) )
                {
                    if(intCodItmEmp==0 || intCodItmGrp==0 || intCodItmMae==0 || intCodBodEmp==0 || strCodLetItm.equals(""))
                    {
                        //Error...Datos a enviar en el arreglo arlDatStkInvItm estan incorrectos.
                        mostrarMsgInf("<HTML>Datos incorrectos del item.</HTML>");
                        blnRes=false;
                    }
                    else
                    {
                        //Tener en cuenta que aumente y disminuya el stock, de acuerdo al signo.
                        arlRegStkInvItm = new ArrayList();
                        arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP, intCodItmGrp);
                        arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP, intCodItmEmp);
                        arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE, intCodItmMae);
                        arlRegStkInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodLetItm);
                        arlRegStkInvItm.add(INT_ARL_STK_INV_CAN_ITM, bgdCanMov );
                        arlRegStkInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP, intCodBodEmp);
                        arlDatStkInvItm.add(arlRegStkInvItm);
                    }
                }
                else
                { 
                    //Error...Stock es menor al disponible.
                    strRevItm+="<tr><td align=\"center\"> "+strCodLetItm+"</tr></td>";                   
                    blnRes=false;
                }                
            }
            System.out.println("arlDatStkInvItm: "+arlDatStkInvItm);
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }     
    
    private boolean actualizaStkInv()
    {
        boolean blnRes=true;
        try
        {
            //Agregar validaciones de stock de inventario antes de actualizar. 
            if(objTooBar.getEstado()=='n')
            {
                 if(objStkInv.actualizaInventario(con, intCodEmp,INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
                    if(objStkInv.actualizaInventario(con, intCodEmp, INT_ARL_STK_INV_CAN_DIS, "+", 0, arlDatStkInvItm)){
                        arlDatStkInvItm.clear();
                   }else{blnRes=false;}
                }else{blnRes=false;}
            }  
            if(!blnRes)
            {
                mostrarMsgInf("<HTML>Problemas al actualizar el inventario.<BR>Verifique el contenido de las filas y vuelva a intentarlo.</HTML>");
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
     * Esta función permite recostear los itmes de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean costearItm()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                switch (objTooBar.getEstado())
                {
                    case 'n':
                        objUti.costearDocumento(this, objParSis, con, intCodEmp, objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        break;
                    default:
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
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc " ;                                                                           
                strSQL+=" FROM tbm_cabMovInv as a ";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a1 ON (a.co_emp = a1.co_emp AND a.co_loc = a1.co_loc AND a.co_tipdoc = a1.co_tipdoc) ";
                strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg as a2 ON (a2.co_emp = a1.co_emp AND a2.co_loc = a1.co_loc AND a2.co_tipdoc = a1.co_tipdoc)" ;
                strSQL+=" WHERE a.st_reg NOT IN('E')";
                strSQL+=" AND a.co_emp  = " + intCodEmp ; 
                strSQL+=" AND a.co_loc  = " + intCodLoc;
                strSQL+=" AND a2.co_mnu = " + objParSis.getCodigoMenu();
                /* Filtros */
                if(!txtCodTipDoc.getText().equals("")) { //Por Código Tipo de Documento
                    strSQL+=" AND a.co_tipdoc = " + txtCodTipDoc.getText(); 
                }
                if(!txtCodDoc.getText().equals("")) { //Por Código de Documento
                    strSQL+=" AND a.co_doc = " + txtCodDoc.getText();
                }
                if(!txtNumDoc.getText().equals("")) { //Por Número de Documento
                    strSQL+=" AND a.ne_numdoc = " + txtNumDoc.getText();
                }
                if(dtpFecDoc.isFecha()){ //Por Fecha
                    strSQL+=" AND a.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                }  
                strSQL+=" ORDER BY a.co_emp, a.co_loc, a.co_tipDoc, a.ne_numDoc";                
                System.out.println("consultarReg: "+strSQL);
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
        boolean blnRes=false;
        try
        {
            if (cargarCabReg())
            {
                if(cargarDetReg()){
                    if(objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                                , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)))
                    {
                        blnRes=true;
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
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a2.st_merIngEgrFisBod, a1.co_doc, a1.fe_doc";
                strSQL+="      , a1.ne_numDoc, a1.nd_tot, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_regRep";
                strSQL+=" FROM tbm_cabMovInv AS a1";
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
                    bgdSig=new BigDecimal(strAux.equals("I")?1:-1);
                    strNatTipDoc=rst.getString("tx_natDoc");
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    datFecDoc=rst.getDate("fe_doc");
                    dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                      
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    //Ing.Eddye Lino 03/Ene/2017; Se debe almacenar el valor de asiento de diario, detMovInv, cabMovInv con 6 decimales por el momento.
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesBaseDatos())));
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
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
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

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        BigDecimal bgdCan=BigDecimal.ZERO;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.tx_uniMed, a1.co_bod, a2.tx_nom as tx_nomBod, a1.nd_can, a1.nd_cosUni, a1.nd_tot, a3.st_ser";
                strSQL+=" FROM tbm_detMovInv AS a1";
                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
                strSQL+=" INNER JOIN tbm_inv AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg";
                System.out.println("cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                txtCodBod.setText("");
                while (rst.next())
                {
                    if (txtCodBod.getText().equals(""))
                    {
                        strAux=rst.getString("co_bod");
                        txtCodBod.setText(strAux);
                        txtNomBod.setText(rst.getString("tx_nomBod"));
                    }
                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_ITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM, rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_BUT_ITM, null);
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("tx_uniMed"));
                    vecReg.add(INT_TBL_DAT_STK_ACT,null);
                    vecReg.add(INT_TBL_DAT_DIS_ACT,null);
                        
                    bgdCan=objUti.redondearBigDecimal(new BigDecimal(rst.getString("nd_can")),objParSis.getDecimalesBaseDatos());
                    
                    if(bgdCan.compareTo(BigDecimal.ZERO)<0){  //Egresos de inventario.
                        vecReg.add(INT_TBL_DAT_CAN_EGR,"" + bgdCan.multiply(bgdSig));
                        vecReg.add(INT_TBL_DAT_CAN_ING,null);
                    }
                    else //Ingresos de inventario.
                    {
                        vecReg.add(INT_TBL_DAT_CAN_EGR,null);
                        vecReg.add(INT_TBL_DAT_CAN_ING,"" + bgdCan);
                    }
                        
                    strAux=rst.getObject("nd_cosUni")==null?"0":(rst.getString("nd_cosUni").equals("")?"0":rst.getString("nd_cosUni"));
                    vecReg.add(INT_TBL_DAT_COS_UNI, strAux);
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    vecReg.add(INT_TBL_DAT_COS_TOT, strAux);
                    vecReg.add(INT_TBL_DAT_EST_SER, rst.getString("st_ser"));
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
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
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
        try
        {
            objRptSis.cargarListadoReportes(con);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("co_emp", new Integer(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP))  );
                                mapPar.put("co_loc", new Integer(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC))  );
                                mapPar.put("co_tipDoc", new Integer(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC))  );
                                mapPar.put("co_doc", new Integer(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC))  );
                                mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
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
    
    


    

}

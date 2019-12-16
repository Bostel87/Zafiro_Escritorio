/*
 * ZafCom55.java
 *
 * Created on 02 de noviembre de 2005, 11:25 PM
 */
package Compras.ZafCom55;
import Compras.ZafCom91.ZafCom91;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
//import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblOrd.ZafHeaRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafToolBar.ZafToolBar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.DriverManager;
import Librerias.ZafReaVenComAut.ZafReaVenComAut;
import ZafReglas.ZafGenGuiRem;
import java.util.Iterator;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafSegMovInv.ZafSegMovInv;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom55 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_EMP=1;
    private final int INT_TBL_DAT_CHK=2;
    private final int INT_TBL_DAT_COD_ITM=3;
    private final int INT_TBL_DAT_COD_ITM_MAE=4;
    private final int INT_TBL_DAT_COD_ALT_ITM=5;
    private final int INT_TBL_DAT_COD_ALT_DOS=6;
    private final int INT_TBL_DAT_NOM_ITM=7;
    private final int INT_TBL_DAT_DES_COR_UNI_MED=8;  
    private final int INT_TBL_DAT_UBI=9; 
    private final int INT_TBL_DAT_STK_ACT_BOD_CEN_DIS=10;
    private final int INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP=11;
    private final int INT_TBL_DAT_CAN_DIS=12;
    private final int INT_TBL_DAT_CAN_REP_CAL=13;
    private final int INT_TBL_DAT_CAN_REP_SEL=14;    
    private final int INT_TBL_DAT_CAN_REP_DIS=15;
    private final int INT_TBL_DAT_PES=16;    
    private final int INT_TBL_DAT_PES_TOT_KGR=17;    
    private final int INT_TBL_DAT_STK_MIN=18;
    private final int INT_TBL_DAT_STK_ACT=19;    
    private final int INT_TBL_DAT_MIN=20;
    private final int INT_TBL_DAT_MAX=21;    
    private final int INT_TBL_DAT_UNI_MED=22;
    
    //PARA EL TAB DE TRANSFERENCIA
    private final int INT_TBL_DAT_TRS_LIN=0;
    private final int INT_TBL_DAT_TRS_CHK=1;
    private final int INT_TBL_DAT_TRS_COD_EMP=2;
    private final int INT_TBL_DAT_TRS_COD_LOC=3;
    private final int INT_TBL_DAT_TRS_COD_TIP_DOC=4;
    private final int INT_TBL_DAT_TRS_DES_COR_TIP_DOC=5;
    private final int INT_TBL_DAT_TRS_COD_DOC=6;
    private final int INT_TBL_DAT_TRS_NUM_DOC=7;
    private final int INT_TBL_DAT_TRS_FEC_DOC=8;
    private final int INT_TBL_DAT_TRS_BUT=9;
    private final int INT_TBL_DAT_TRS_COD_BOD_ORI=10;
    private final int INT_TBL_DAT_TRS_COD_ITM=11;
    private final int INT_TBL_DAT_TRS_COD_ITM_MAE=12;
    private final int INT_TBL_DAT_TRS_COD_ALT_ITM=13;
    private final int INT_TBL_DAT_TRS_NOM_ITM=14;
    private final int INT_TBL_DAT_TRS_UNI_MED=15;
    private final int INT_TBL_DAT_TRS_CAN_REP=16;
    private final int INT_TBL_DAT_TRS_STK_ACT=17;
    private final int INT_TBL_DAT_TRS_STK_MIN=18;
    private final int INT_TBL_DAT_TRS_STK_DIS=19;
    private final int INT_TBL_DAT_TRS_COS_UNI=20;
    private final int INT_TBL_DAT_TRS_PRE_UNI=21;
    private final int INT_TBL_DAT_TRS_COS_TOT=22;
    private final int INT_TBL_DAT_TRS_PES_GRP=23;
    private final int INT_TBL_DAT_TRS_COD_BOD_DES=24;
       
    //ArrayList para consultar.
    private ArrayList arlDatConCnf, arlRegConCnf;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceConCnf=0;
    
    //ArrayList: Contenedor de solicitud de transferencias.
    private ArrayList arlDatConSolTraInv, arlRegConSolTraInv;
    final int INT_ARL_GEN_SOL_TRA_ITM_GRP=0;
    final int INT_ARL_GEN_SOL_TRA_CAN=1;
    final int INT_ARL_GEN_SOL_PES_UNI=2;
    final int INT_ARL_GEN_SOL_PES_TOT_ITM=3;
    
    //ArrayList: Para obtener solicitud de transferencia
    private ArrayList arlDatSol;
    
    // CODIGO DE LA CONFIGURACION PARA SOLICITUDES DE REPOSICIONES DE INVENTARIO
    final int INT_COD_CFG_TIP_AUT_SOL_REP_INV=2001; 
    
    // CODIGO DE LA CONFIGURACION PARA TRANSFERENCIAS DE REPOSICIONES DE INVENTARIO
    final int INT_COD_CFG_TIP_AUT_TRA_REP_INV=16; 
    
    //Código de Bodega de Centro de Distribución.
    private final int INT_COD_BOD_CEN_DIS=15;
    
    //CÓDIGO DE TIPO DOCUMENTO REPOSICION 
    final String STR_COD_TIP_DOC_REINBO="161"; 
    final String STR_COD_TIP_DOC_REINBD="223"; 
    
    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum, objColNumTrs;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafTblFilCab objTblFilCab, objTblFilCabTrs;
    private ZafTblMod objTblModRep, objTblModRepTrs;
    private ZafTblEdi objTblEdi;                                       //Editor: Editor del JTable.
    private java.util.Date datFecAux;                                  //Auxiliar: Para almacenar fechas.
    private ZafTblCelRenLbl objTblCelRenLbl;                           //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                                 //ToolTipText en TableHeader.
    private ZafMouMotAdaTrs objMouMotAdaTrs;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuTrs;                //PopupMenu: Establecer PeopuMená en JTable.
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkTrs;       //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkTrs;       //Editor: JCheckBox en celda.
    private ZafVenCon vcoTipDoc, vcoBodDes, vcoBodOri;                 //Ventana de consulta "Bodega".
    private ZafThreadGUI objThrGUI;
    private ZafPerUsr objPerUsr;
    private ZafHeaRenLbl objHeaRenLbl;
    private ZafDocLis objDocLis;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtCanRep,objTblCelEdiTxt2;
    private ZafTblOrd objTblOrd;                                      //JTable de ordenamiento.
    private ZafTblCelRenBut    objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private Librerias.ZafUtil.UltDocPrint  objUltDocPrint2;
    private ZafReaVenComAut objReaVenComAut;
    private MiToolBar objTooBar;
    private ZafCom91 objZafCom91;
    private ZafSegMovInv objSegMovInv;
    private ZafCfgSer objCfgSer;                                      //Obtiene Configuracion de Servicios.
    private GenOD.ZafGenOdPryTra objGenOD;    /*Jose Mario Marin - 22/Junio/2016*/
    
    private Vector vecDat,  vecCab,  vecReg;
    private Vector vecDatTrs,  vecCabTrs,  vecRegTrs;
    private Vector vecAux;
    Vector vecDocSinPnd=new Vector();
    
    private boolean blnCon;                                           //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                                        //Determina si hay cambios en el formulario.
    private boolean blnChnEstImp;

    private BigDecimal bdePesTotTrs;
    private BigDecimal bdePorVarGrl; 

    private int intCodEmpGrp, intCodLocGrp;
    private int intOpcRedondeoStock, intOpcRedondeoMinimo;
    private int intNumRegSel;
    
    private String strSQL,  strAux;
    private String strDesCorTipDoc,  strDesLarTipDoc;                 //Contenido del campo al obtener el foco.
    private String strDesCorRes,  strDesLarRes;                       //Contenido del campo al obtener el foco.
    private String strCodBod,  strNomBod;                             //Contenido del campo al obtener el foco.
    private String strCodBodDes, strNomBodDes, strCodBodOri, strNomBodOri;
    private String strEstImpDoc;
    private String strIpImp;                                         //Ip donde se ejecuta el servidor de Impresion de OD y Guias.
    private String strPesTotKgrSolTra;                               //Obtiene el peso total de la solicitud de transferencia.
    
    private String strVersion=" v0.18.13 ";
    

    /** Crea una nueva instancia de la clase ZafCom55. */
    public ZafCom55(ZafParSis obj)
    {
        try
        {
            objParSis = (ZafParSis) obj.clone();

            intCodEmpGrp = objParSis.getCodigoEmpresa();
            intCodLocGrp = objParSis.getCodigoLocal();

            intOpcRedondeoStock = 0;
            intOpcRedondeoMinimo = 0;      
            if(intCodEmpGrp==objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                configurarFrm();
                agregarDocLis();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
            blnChnEstImp=false;
        } 
        catch (CloneNotSupportedException e) 
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Crea una nueva instancia de la clase ZafCom55. llamada desde el ZafCom57(Programa de seguimiento)*/
    public ZafCom55(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento) 
    {
        try 
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            intCodEmpGrp=codigoEmpresa;
            intCodLocGrp=codigoLocal;
            
            if(intCodEmpGrp==objParSis.getCodigoEmpresaGrupo())
            {
                initComponents();
                txtCodTipDoc.setText("" + codigoTipoDocumento);
                txtCodDoc.setText("" + codigoDocumento);
                configurarFrm();
                agregarDocLis();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }

            consultarReg();
            objTooBar.afterConsultar();
            objTooBar.setVisibleInsertar(false);
            objTooBar.setVisibleImprimir(false);
            objTooBar.setVisibleVistaPreliminar(false);

            blnChnEstImp=false;

        } 
        catch (CloneNotSupportedException e) 
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
        
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes = true;
        try 
        {
            objPerUsr=new ZafPerUsr(objParSis);
            objUti = new ZafUtil();
            //String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker:
            //DESDE
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(560, 6, 120, 20);

            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            // José Marín 28/Jul/2014 ....  9.04
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodOri.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodOri.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtPesTot.setBackground(objParSis.getColorCamposSistema());
            txtPesDis.setBackground(objParSis.getColorCamposObligatorios());

            txtCodTipDoc.setVisible(false);
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            //configurarVenConBodDes();
            //configurarVenConBodOri();

            objTooBar=new MiToolBar(this);
            panBarBot.add(objTooBar);
            objTooBar.agregarSeparador();
            //objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.agregarBoton(butCarItm);
            
            objSegMovInv=new ZafSegMovInv(objParSis, this);

            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if(objParSis.getCodigoUsuario()==1)
            {
                //optPorVar.setVisible(false);
                optPorVar.setEnabled(false);
                
                //optItmApl.setVisible(false);
                optItmApl.setEnabled(false);
                
                optItmSelUsr.setVisible(true);
                optItmSelUsr.setEnabled(true);

            }
            else
            {
                if(objParSis.getCodigoMenu()==2336) //programa de reposicion de inventario desde otras bodegas
                {
                    //Ficha "General": Opción "Por porcentaje de variación"
                    if (!objPerUsr.isOpcionEnabled(3961)) 
                    {
                        //optPorVar.setVisible(false);
                        optPorVar.setEnabled(false);
                    }
                    //Ficha "General": Opción "Los primeros 13 items generados por el Sistema"
                    if (!objPerUsr.isOpcionEnabled(3962)) 
                    {
                        //optItmApl.setVisible(false);
                        optItmApl.setEnabled(false);
                    }
                    //Ficha "General": Opción "Items seleccionados por usuario (13)"
                    if(!objPerUsr.isOpcionEnabled(3958)) 
                    {
                        //optItmSelUsr.setVisible(false);
                        optItmSelUsr.setEnabled(false);
                    }
                }       
            }
            //Configurar los JTables.
            configurarTblDat();
            configurarTblDatTrs();
        } 
        catch (Exception e)
        {
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
    private boolean configurarTblDat()  
    {
        boolean blnRes = true;
        try
        {
            //System.out.println("configurarTblDat ");
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cod.Emp.");
            vecCab.add(INT_TBL_DAT_CHK, " ");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cod.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS, "Cod.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Unidad");
            vecCab.add(INT_TBL_DAT_UBI, "Ubicación"); 
            vecCab.add(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS, "Stk.Cen.Dis.");
            vecCab.add(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, "Per.Cen.Dis.");
            vecCab.add(INT_TBL_DAT_CAN_DIS,"Can.Dis.Bod.Ori.");
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL, "Can.Rep.Cal.");//cantidad a reponer calculada por el sistema segun parametros
            vecCab.add(INT_TBL_DAT_CAN_REP_SEL, "Can.Rep.Sel.");//cantidad a reponer seleccionada
            vecCab.add(INT_TBL_DAT_CAN_REP_DIS, "Can.Rep.Dis.");//cantidad disponible a reponer(segun stock disponible en las empresas)
            vecCab.add(INT_TBL_DAT_PES, "Peso(kg).");
            vecCab.add(INT_TBL_DAT_PES_TOT_KGR,"Pes.Tot(kg).");
            vecCab.add(INT_TBL_DAT_STK_MIN, "Stock Minimo");
            vecCab.add(INT_TBL_DAT_STK_ACT, "Stock Actual");
            vecCab.add(INT_TBL_DAT_MIN, "Min.");
            vecCab.add(INT_TBL_DAT_MAX, "Max.");
            vecCab.add(INT_TBL_DAT_UNI_MED, "UNI.");
          
            objTblModRep = new ZafTblMod();

            objTblModRep.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_SEL, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_DIS, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_PES, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_PES_TOT_KGR, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_MIN, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_ACT, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_MIN, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_MAX, objTblModRep.INT_COL_INT, new Integer(0), null);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblModRep);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(160);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_UBI).setPreferredWidth(80); 
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_DAT_MIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAX).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setResizable(false);
            //tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_REP_DIS, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_MIN, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_ACT, tblDat);
            //objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_MIN, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_MAX, tblDat);
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_UNI_MED, tblDat);
            //objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_DIS, tblDat);  
            
            if(! objTooBar.isOpcionEnabled(2337)){
                objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_REP_CAL, tblDat);
            }

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
          
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                BigDecimal bdeCanReaRep=new BigDecimal(0);
                BigDecimal bdeCanStkDis=new BigDecimal(0);
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
                        intNumRegSel++;
                        bdeCanReaRep=objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL)==null?new BigDecimal(0):new BigDecimal(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL).toString());
                        bdeCanStkDis=objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?new BigDecimal(0):new BigDecimal(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());
                        if( (intNumRegSel<=13))
                        {
                            ////////////////////////////////////////////////////////////////////////////////////////////////////
                            //Validación por tipo de documento de retorno que no verifique stock de la bodega de distribución.
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)) //REINBO - Reposición de inventario desde otras bodegas
                            {
                                if(bdeCanStkDis.compareTo(bdeCanReaRep)>=0)
                                {
                                    objTblModRep.setValueAt(bdeCanReaRep, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                }
                                else
                                {
                                    objTblModRep.setValueAt(bdeCanStkDis, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                }
                            }
                            else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)) //REINBD - Retorno de inventario a bodega de Distribución
                            {
                                if(bdeCanReaRep.compareTo(new BigDecimal("0"))>0)
                                {
                                    objTblModRep.setValueAt(bdeCanReaRep, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                }
                            }
                            ////////////////////////////////////////////////////////////////////////////////////////////////////
                            
                            if(bdeCanReaRep.compareTo(new BigDecimal(BigInteger.ONE))<=0)
                            {
                                String strMsg="";
                                strMsg+="<HTML>";
                                strMsg+="El registro seleccionado tiene un valor menor a la unidad. <BR>";
                                strMsg+="¿Está seguro que desea seleccionar la cantidad a reponer?";
                                strMsg+="</HTML>";
                                switch (mostrarMsgCon(strMsg))
                                {
                                    case 0: //YES_OPTION

                                        break;
                                    case 1: //NO_OPTION
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                        objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                        intNumRegSel--;
                                        break;
                                    case 2: //CANCEL_OPTION
                                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                        objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                        intNumRegSel--;
                                }
                            }
                            if (!getSumaTotalPeso(tblDat.getSelectedRow())) 
                            {
                                mostrarMsgInf("<HTML>El registro seleccionado tiene un peso mayor al disponible.</HTML>");
                                objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                intNumRegSel--;
                            }
                        }
                        
                        else{
                            mostrarMsgInf("<HTML>Se han seleccionado mas de 13 registros.</HTML>");
                            objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                            objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                            intNumRegSel--;
                        }

                    }
                    else{
                        if(getRestaTotalPeso(tblDat.getSelectedRow())){
                        }
                        intNumRegSel--;
                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                    }

                    double dblCan; // José Marín M.
                    if(objTblCelEdiChk.isChecked())
                    {
                        dblCan=Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL).toString()) * (Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES).toString()));
                        objTblModRep.setValueAt(dblCan, tblDat.getSelectedRow(), INT_TBL_DAT_PES_TOT_KGR);
                    }
                    else{
                        dblCan=Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL).toString()) * (Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES).toString()));
                        objTblModRep.setValueAt(dblCan, tblDat.getSelectedRow(), INT_TBL_DAT_PES_TOT_KGR);
                    }
                    objTooBar.setMenSis("El número de registros seleccionados es: " + intNumRegSel);
                }
            });

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_PES).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellEditor(objTblCelEdiTxt);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_SEL, objTblModRep.INT_COL_DBL, new Integer(0), null);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UBI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelEdiTxtCanRep=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellEditor(objTblCelEdiTxtCanRep);
            objTblCelEdiTxtCanRep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                BigDecimal bdeValCanRepBefEdi=new BigDecimal("0");
                BigDecimal bdeValCanRepAftEdi=new BigDecimal("0");
                BigDecimal bdeStkDis=new BigDecimal("0");
                
                BigDecimal bdeValCanRepCal=new BigDecimal("0");
                BigDecimal bdeValKg=new BigDecimal("0");
                BigDecimal bdeValKgTot=new BigDecimal("0");
                Double disponible;///  José Marín M. 15/Jul/2014
                Double dblDisponible_tbm_invBod;  //JMRZ 9/Agosto/2016
                String strUniMed="";
                int fila=-1;
                int columna=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    fila=tblDat.getSelectedRow();
                    columna=tblDat.getSelectedColumn();
                    //bdeValCanRepBefEdi=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
                    bdeValCanRepBefEdi=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString().equals("")?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
                    bdeStkDis=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());
                    strUniMed=objTblModRep.getValueAt(fila, INT_TBL_DAT_DES_COR_UNI_MED)==null?"":objTblModRep.getValueAt(fila, INT_TBL_DAT_DES_COR_UNI_MED).toString();
                    disponible=objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL));///  José Marín M. 15/Jul/2014
                    dblDisponible_tbm_invBod = objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_DIS))<0?0:objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_DIS));///  JM 9/Agosto/2016
                    if(objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL))<=0){
                        objTblModRep.setValueAt(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);///  José Marín M. 16/Jul/2014
                    }

                    quitarItmCanRepCer(); 
                    //<editor-fold defaultstate="collapsed" desc="/* comentado */">
//                    if(objTblModRep.isChecked(fila, INT_TBL_DAT_CHK) && objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL))>0){  ///  José Marín M. 16/Jul/2014
//                        if(strUniMed.equals("MTS")){
//                            objTblCelEdiTxtCanRep.setCancelarEdicion(false);
//                        }
//                        else{
//                            objTblCelEdiTxtCanRep.setCancelarEdicion(true);
//                        }
//                    }
//                    else{
//                        objTblCelEdiTxtCanRep.setCancelarEdicion(false); ///  José Marín M. 16/Jul/2014
//                    }
                    //</editor-fold>
                    
                 }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    bdeValCanRepAftEdi=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString().equals("")?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
                    validaCantidad(fila, bdeStkDis, bdeValCanRepBefEdi, bdeValCanRepAftEdi);
                    
                    if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD))
                    {
                        ////REINBD - Retorno de inventario a bodega de Distribución
                    }
                    else
                    {
                        if (objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL))>disponible) ///  José Marín M. 15/Jul/2014
                        { 
                            mostrarMsgInf("<HTML>La cantidad ingresada es mayor a la permitida.<BR>La cantidad se cambiara a la maxima permitida.<BR>Verifique o cambie en configuración de Item.</HTML>");
                            objTblModRep.setValueAt(disponible, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);///  José Marín M. 15/Jul/2014
                        }
                        if (objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL))>dblDisponible_tbm_invBod) ///  JMRZ 9/Agos/2016
                        { 
                            mostrarMsgInf("<HTML>La cantidad ingresada es mayor a la disponible.<BR>La cantidad se cambiara a la maxima disponible.<BR>Verifique o cambie en configuración de Item.</HTML>");
                            objTblModRep.setValueAt(dblDisponible_tbm_invBod, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL); 
                        }
                    }
                    
                    if(objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL))>0) ///  José Marín M. 15/Jul/2014
                    {
                        objTblModRep.setValueAt(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                    }
                    else   ///  José Marín M. 16/Jul/2014
                    {
                        objTblModRep.setValueAt(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                    }
                    
                    calcularPesTotRep(); 
                }
            });
            objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI); //tabla editable 


            tcmAux=null;
        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función configura el JTable "tblDatTrs".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatTrs() 
    {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatTrs = new Vector();    //Almacena los datos
            vecCabTrs = new Vector(25);  //Almacena las cabeceras
            vecCabTrs.clear();
            vecCabTrs.add(INT_TBL_DAT_TRS_LIN, "");
            vecCabTrs.add(INT_TBL_DAT_TRS_CHK, "");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_EMP, "Cod.Emp.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_LOC, "Cod.Loc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_TIP_DOC, "Cod.Tip.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_DES_COR_TIP_DOC, "Tip.doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_DOC, "Cod.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_NUM_DOC, "Num.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_FEC_DOC, "Fec.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_BUT, "");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_BOD_ORI, "Cod.Bod.Ori.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_ITM, "Cod.Itm.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_ITM_MAE, "Cod.Itm.Mae");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_ALT_ITM, "Cod.Alt.Itm.");
            vecCabTrs.add(INT_TBL_DAT_TRS_NOM_ITM, "Nom.Itm.");
            vecCabTrs.add(INT_TBL_DAT_TRS_UNI_MED, "Uni.Med.");
            vecCabTrs.add(INT_TBL_DAT_TRS_CAN_REP, "Can.Rep.");
            vecCabTrs.add(INT_TBL_DAT_TRS_STK_ACT, "Stk.Act.");
            vecCabTrs.add(INT_TBL_DAT_TRS_STK_MIN, "Stk.Min.");
            vecCabTrs.add(INT_TBL_DAT_TRS_STK_DIS, "Stk.Dis.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COS_UNI, "Cos.Uni.");
            vecCabTrs.add(INT_TBL_DAT_TRS_PRE_UNI, "Pre.Uni.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COS_TOT, "Cos.Tot.");
            vecCabTrs.add(INT_TBL_DAT_TRS_PES_GRP, "Pes.Grp.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_BOD_DES, "Cod.Bod.Des.");

            objTblModRepTrs = new ZafTblMod();

            objTblModRepTrs.setHeader(vecCabTrs);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_CAN_REP, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_STK_ACT, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_STK_MIN, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_STK_DIS, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_COS_UNI, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_PRE_UNI, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_COS_TOT, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_PES_GRP, objTblModRepTrs.INT_COL_INT, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblTrs.setModel(objTblModRepTrs);
            //Configurar JTable: Establecer tipo de seleccián.
            tblTrs.setRowSelectionAllowed(true);
            tblTrs.getTableHeader().setReorderingAllowed(false);
            tblTrs.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumTrs = new ZafColNumerada(tblTrs, INT_TBL_DAT_TRS_LIN);


            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuTrs = new ZafTblPopMnu(tblTrs);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTrs.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTrs.getColumnModel();


            tcmAux.getColumn(INT_TBL_DAT_TRS_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TRS_DES_COR_TIP_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TRS_BUT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_ORI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ALT_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_NOM_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CAN_REP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_MIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_DIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PRE_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PES_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_DES).setPreferredWidth(60);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_ORI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ALT_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_NOM_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_UNI_MED).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CAN_REP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_ACT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_MIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_DIS).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_UNI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PRE_UNI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_TOT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PES_GRP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_DES).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_CHK, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_TIP_DOC, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_DOC, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_BOD_ORI, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_ITM, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_ITM_MAE, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_ALT_ITM, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_NOM_ITM, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_UNI_MED, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_CAN_REP, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_STK_ACT, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_STK_MIN, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_STK_DIS, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COS_UNI, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_PRE_UNI, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COS_TOT, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_PES_GRP, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_BOD_DES, tblTrs);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaTrs = new ZafMouMotAdaTrs();
            tblTrs.getTableHeader().addMouseMotionListener(objMouMotAdaTrs);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTrs = new ZafTblFilCab(tblTrs);
            tcmAux.getColumn(INT_TBL_DAT_TRS_LIN).setCellRenderer(objTblFilCabTrs);


            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkTrs=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setCellRenderer(objTblCelRenChkTrs);
            objTblCelRenChkTrs=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkTrs=new ZafTblCelEdiChk(tblTrs);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setCellEditor(objTblCelEdiChkTrs);


            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);

            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TRS_BUT);
            objTblModRepTrs.setColumnasEditables(vecAux);
            vecAux=null;

            //PARA EL BOTON DE ANEXO UNO, QUE LLAMA A LA VENTANA DE MAESTRO DE CLIENTES
            objTblCelRenBut=new ZafTblCelRenBut();
            tblTrs.getColumnModel().getColumn(INT_TBL_DAT_TRS_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblTrs.getColumnModel().getColumn(INT_TBL_DAT_TRS_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblTrs.getSelectedRow();
                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abreTransferencia(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });


            tcmAux=null;


        } 
        catch (Exception e)
        {
            blnRes = false;
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
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_EMP:
                    strMsg = "Código de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código del item maestro";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del ítem";
                    break;
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg = "Código alterno 2 del ítem";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_UBI:
                    strMsg = "Ubicación del ítem";
                    break;
                case INT_TBL_DAT_STK_ACT_BOD_CEN_DIS:
                    strMsg = "Stock Actual de la Bodega Centro Distribución";
                    break;
                case INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP:
                    strMsg = "Stock permitido en la bodega Centro de Distribución(Según mínimos y máximos).";
                    break;
                case INT_TBL_DAT_CAN_DIS:
                    strMsg = "Cantidad disponible en la bodega Centro de Distribución";
                    break;
                case INT_TBL_DAT_CAN_REP_SEL:
                    strMsg = "Cantidad que el programa repone";
                    break;
                case INT_TBL_DAT_CAN_REP_CAL:
                    strMsg = "Cantidad que se debe reponer según stock";
                    break;
                case INT_TBL_DAT_CAN_REP_DIS:
                    strMsg = "Cantidad disponible que se puede usar para el proceso de reposición";
                    break;
                case INT_TBL_DAT_PES:
                    strMsg = "Peso (kg)";
                    break;
                case INT_TBL_DAT_PES_TOT_KGR:
                    strMsg = "Peso Total en (kg)";
                    break;    
                case INT_TBL_DAT_STK_MIN:
                    strMsg = "Stock Minimo";
                    break;
                case INT_TBL_DAT_STK_ACT:
                    strMsg = "Stock Actual";
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
    private class ZafMouMotAdaTrs extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblTrs.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_TRS_COD_EMP:
                    strMsg = "Código de empresa";
                    break;
                case INT_TBL_DAT_TRS_COD_LOC:
                    strMsg = "Código de local";
                    break;
                case INT_TBL_DAT_TRS_COD_TIP_DOC:
                    strMsg = "Código de tipo de documento";
                    break;
                case INT_TBL_DAT_TRS_DES_COR_TIP_DOC:
                    strMsg = "Tipo de documento";
                    break;
                case INT_TBL_DAT_TRS_COD_DOC:
                    strMsg = "Código de documento";
                    break;
                case INT_TBL_DAT_TRS_NUM_DOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_DAT_TRS_FEC_DOC:
                    strMsg = "Fecha de documento";
                    break;
                case INT_TBL_DAT_TRS_COD_BOD_ORI:
                    strMsg = "Código de bodega origen";
                    break;
                case INT_TBL_DAT_TRS_COD_ITM:
                    strMsg = "Código de item";
                    break;
                case INT_TBL_DAT_TRS_COD_ITM_MAE:
                    strMsg = "Código de item maestro";
                    break;
                case INT_TBL_DAT_TRS_COD_ALT_ITM:
                    strMsg = "Código alterno de item";
                    break;
                case INT_TBL_DAT_TRS_NOM_ITM:
                    strMsg = "Nombre de item";
                    break;
                case INT_TBL_DAT_TRS_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_TRS_CAN_REP:
                    strMsg = "Cantidad a reponer";
                    break;
                case INT_TBL_DAT_TRS_STK_ACT:
                    strMsg = "Stock actual";
                    break;
                case INT_TBL_DAT_TRS_STK_MIN:
                    strMsg = "Stock mínimo";
                    break;
                case INT_TBL_DAT_TRS_STK_DIS:
                    strMsg = "Stock disponible";
                    break;
                case INT_TBL_DAT_TRS_COS_UNI:
                    strMsg = "Costo unitario";
                    break;
                case INT_TBL_DAT_TRS_PRE_UNI:
                    strMsg = "Precio unitario";
                    break;
                case INT_TBL_DAT_TRS_COS_TOT:
                    strMsg = "Costo total";
                    break;
                case INT_TBL_DAT_TRS_PES_GRP:
                    strMsg = "Peso que tiene el item en el grupo, no en empresa";
                    break;
                case INT_TBL_DAT_TRS_COD_BOD_DES:
                    strMsg = "Código de bodega destino";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblTrs.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "";
                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " AND a1.co_emp=" + intCodEmpGrp;
                strSQL += " AND a1.co_loc=" + intCodLocGrp;
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL += " ORDER BY a1.tx_desCor";
            } else {
                strSQL = "";
                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc \n";
                strSQL += " FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1 \n";
                strSQL += " ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc) \n";
                strSQL += " WHERE ";
                strSQL += " a2.co_emp=" + intCodEmpGrp + "";
                strSQL += " AND a2.co_loc=" + intCodLocGrp + "";
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //System.out.println("configurarVenConTipDoc \n" + strSQL);

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 5;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);


        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBodOri() 
    {
        boolean blnRes = true;
        try {
                //Listado de campos.
                ArrayList arlCam = new ArrayList();
                arlCam.add("a1.co_bod");
                arlCam.add("a1.tx_nom");
                //Alias de los campos.
                ArrayList arlAli = new ArrayList();
                arlAli.add("Código Bodega");
                arlAli.add("Nombre de Bodega");
                //Ancho de las columnas.
                ArrayList arlAncCol = new ArrayList();
                arlAncCol.add("50");
                arlAncCol.add("334");
                

                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL = "";
                    strSQL += " SELECT a1.co_bod, a1.tx_nom";
                    strSQL += " FROM tbm_bod AS a1";
                    strSQL += " WHERE a1.co_emp=" + intCodEmpGrp + "";
                    strSQL += " AND a1.st_reg='A'";
                    strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
                }
                else{
                    strSQL = "";
                    strSQL += " SELECT a1.co_bod, a1.tx_nom \n";
                    strSQL += " FROM tbm_bod AS a1 \n";
                    strSQL += " INNER JOIN tbr_bodTipDocPrgUsr AS a2 \n";
                    strSQL += "                  ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod \n";
                    strSQL += " WHERE a1.co_emp=" + intCodEmpGrp + "";
                    strSQL += " AND a2.co_loc=" + intCodLocGrp + "";
                    strSQL += " AND a1.st_reg='A'";
                    strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + " \n";
                    if(txtCodTipDoc.getText().length()>0)
                        strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
                    if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO))
                        strSQL += " AND a2.tx_natBod='A'";
                    else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD))
                        strSQL += " AND a2.tx_natBod='I'";
                    strSQL += " AND a2.st_reg IN('A','P')";
                }
                //System.out.println("configurarVenConBodOri \n" + strSQL);

                vcoBodOri = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Origen", strSQL, arlCam, arlAli, arlAncCol);
                arlCam = null;
                arlAli = null;
                arlAncCol = null;
                //Configurar columnas.
                vcoBodOri.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBodDes() 
    {
        boolean blnRes = true;
        try {
                //Listado de campos.
                ArrayList arlCam = new ArrayList();
                arlCam.add("a1.co_bod");
                arlCam.add("a1.tx_nom");
                //Alias de los campos.
                ArrayList arlAli = new ArrayList();
                arlAli.add("Código Bodega");
                arlAli.add("Nombre de Bodega");
                //Ancho de las columnas.
                ArrayList arlAncCol = new ArrayList();
                arlAncCol.add("50");
                arlAncCol.add("334");

                if(objParSis.getCodigoUsuario()==1){
                    strSQL = "";
                    strSQL += " SELECT a1.co_bod, a1.tx_nom";
                    strSQL += " FROM tbm_bod AS a1";
                    strSQL += " WHERE a1.co_emp=" + intCodEmpGrp + "";
                    strSQL += " AND a1.st_reg='A'";
                    strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
                }
                else{
                    strSQL = "";
                    strSQL += " SELECT a1.co_bod, a1.tx_nom \n";
                    strSQL += " FROM tbm_bod AS a1 \n";
                    strSQL += " INNER JOIN tbr_bodTipDocPrgUsr AS a2 \n";
                    strSQL += "             ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod ";
                    strSQL += " WHERE a1.co_emp=" + intCodEmpGrp + "";
                    strSQL += " AND a2.co_loc=" + intCodLocGrp + "";
                    strSQL += " AND a1.st_reg='A'";
                    strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + " \n";
                    if(txtCodTipDoc.getText().length()>0)
                        strSQL += " AND a2.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
                     if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO))
                        strSQL += " AND a2.tx_natBod='I'";
                    else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD))
                        strSQL += " AND a2.tx_natBod='A'";
                    strSQL += " AND a2.st_reg IN('A','P')";
                }
                //System.out.println("configurarVenConBodDes \n" + strSQL);

                vcoBodDes = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Destino", strSQL, arlCam, arlAli, arlAncCol);
                arlCam = null;
                arlAli = null;
                arlAncCol = null;
                //Configurar columnas.
                vcoBodDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
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
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".

                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        } 
                        else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
            //////////////////
            configurarVenConBodOri();
            configurarVenConBodDes();
       }
        catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConBodDes(int intTipBus) 
    {
        boolean blnRes = true;
        try {
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.

                        vcoBodDes.setCampoBusqueda(2);
                        vcoBodDes.show();
                        if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBodDes.buscar("a1.co_bod", txtCodBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(0);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtCodBodDes.setText(strCodBodDes);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBodDes.buscar("a1.tx_nom", txtNomBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(1);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtNomBodDes.setText(strNomBodDes);
                            }
                        }
                        break;
                }

        } catch (Exception e) {
            blnRes = false;
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
    private boolean mostrarVenConBodOri(int intTipBus) 
    {
        boolean blnRes = true;
        try {
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.
                        vcoBodOri.setCampoBusqueda(2);
                        vcoBodOri.show();
                        if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            vcoBodOri.limpiar();
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBodOri.buscar("a1.co_bod", txtCodBodOri.getText())) {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            vcoBodOri.limpiar();
                        } else {
                            vcoBodOri.setCampoBusqueda(0);
                            vcoBodOri.setCriterio1(11);
                            vcoBodOri.cargarDatos();
                            vcoBodOri.show();
                            if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) {
                                txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                                txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                                vcoBodOri.limpiar();
                            } else {
                                txtCodBodOri.setText(strCodBodOri);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBodOri.buscar("a1.tx_nom", txtNomBodOri.getText())) {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            vcoBodOri.limpiar();
                        } else {
                            vcoBodOri.setCampoBusqueda(1);
                            vcoBodOri.setCriterio1(11);
                            vcoBodOri.cargarDatos();
                            vcoBodOri.show();
                            if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) {
                                txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                                txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                                vcoBodOri.limpiar();
                            } else {
                                txtNomBodOri.setText(strNomBodOri);
                            }
                        }
                        break;
                }

        } catch (Exception e) {
            blnRes = false;
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
        Connection conTipDocPre;
        try
        {
            conTipDocPre=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conTipDocPre!=null){
                stm=conTipDocPre.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc \n";
                strSQL+=" FROM tbm_cabTipDoc AS a1 \n";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
                strSQL+=" AND a1.co_loc=" + intCodLocGrp;
                strSQL+=" AND a1.co_tipDoc=";
                strSQL+=" ( \n";
                strSQL+="       SELECT co_tipDoc \n";
                strSQL+="       FROM tbr_tipDocPrg \n";
                strSQL+="       WHERE co_emp=" + intCodEmpGrp;
                strSQL+="       AND co_loc=" + intCodLocGrp;
                strSQL+="       AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL+="       AND st_reg='S' \n";
                strSQL+=" ) \n";
                //System.out.println("mostrarTipDocPre: \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n')
                        txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                }
                rst.close();
                stm.close();
                conTipDocPre.close();
                rst=null;
                stm=null;
                conTipDocPre=null;
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
        panCon = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblBodOri = new javax.swing.JLabel();
        txtCodBodOri = new javax.swing.JTextField();
        txtNomBodOri = new javax.swing.JTextField();
        butBodOri = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblBodDes = new javax.swing.JLabel();
        txtCodBodDes = new javax.swing.JTextField();
        txtNomBodDes = new javax.swing.JTextField();
        butBodDes = new javax.swing.JButton();
        lblPesDis = new javax.swing.JLabel();
        txtPesDis = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblPesTot = new javax.swing.JLabel();
        txtPesTot = new javax.swing.JTextField();
        butCarItm = new javax.swing.JButton();
        lblPorVar = new javax.swing.JLabel();
        optPorVar = new javax.swing.JRadioButton();
        optItmApl = new javax.swing.JRadioButton();
        optItmSelUsr = new javax.swing.JRadioButton();
        panRep = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panTraInv = new javax.swing.JPanel();
        spnTrs = new javax.swing.JScrollPane();
        tblTrs = new javax.swing.JTable();
        panBarBot = new javax.swing.JPanel();

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

        panCon.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFil.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(3, 2, 122, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(95, 2, 32, 20);

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
        panCabFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(128, 2, 56, 20);

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
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(185, 2, 220, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(406, 2, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabFil.add(lblFecDoc);
        lblFecDoc.setBounds(432, 6, 130, 20);

        lblBodOri.setText("Bodega origen:");
        lblBodOri.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBodOri);
        lblBodOri.setBounds(3, 23, 110, 20);

        txtCodBodOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodOriActionPerformed(evt);
            }
        });
        txtCodBodOri.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodOriFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodOriFocusLost(evt);
            }
        });
        panCabFil.add(txtCodBodOri);
        txtCodBodOri.setBounds(128, 23, 56, 20);

        txtNomBodOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodOriActionPerformed(evt);
            }
        });
        txtNomBodOri.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodOriFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodOriFocusLost(evt);
            }
        });
        panCabFil.add(txtNomBodOri);
        txtNomBodOri.setBounds(185, 23, 220, 20);

        butBodOri.setText("...");
        butBodOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodOriActionPerformed(evt);
            }
        });
        panCabFil.add(butBodOri);
        butBodOri.setBounds(406, 23, 20, 20);

        lblNumDoc.setText("Número documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCabFil.add(lblNumDoc);
        lblNumDoc.setBounds(432, 27, 130, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumDoc);
        txtNumDoc.setBounds(560, 27, 120, 20);

        lblBodDes.setText("Bodega destino:");
        lblBodDes.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBodDes);
        lblBodDes.setBounds(3, 44, 110, 20);

        txtCodBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodDesActionPerformed(evt);
            }
        });
        txtCodBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusLost(evt);
            }
        });
        panCabFil.add(txtCodBodDes);
        txtCodBodDes.setBounds(128, 44, 56, 20);

        txtNomBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodDesActionPerformed(evt);
            }
        });
        txtNomBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusLost(evt);
            }
        });
        panCabFil.add(txtNomBodDes);
        txtNomBodDes.setBounds(185, 44, 220, 20);

        butBodDes.setText("...");
        butBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodDesActionPerformed(evt);
            }
        });
        panCabFil.add(butBodDes);
        butBodDes.setBounds(406, 44, 20, 20);

        lblPesDis.setText("Peso disponible (kg):");
        lblPesDis.setToolTipText("Número alterno 1");
        panCabFil.add(lblPesDis);
        lblPesDis.setBounds(432, 48, 130, 20);

        txtPesDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPesDis.setToolTipText("Número de egreso");
        panCabFil.add(txtPesDis);
        txtPesDis.setBounds(560, 48, 120, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabFil.add(lblCodDoc);
        lblCodDoc.setBounds(3, 64, 130, 20);
        panCabFil.add(txtCodDoc);
        txtCodDoc.setBounds(128, 65, 80, 20);

        lblPesTot.setText("Peso total (kg):");
        lblPesTot.setToolTipText("Número alterno 1");
        panCabFil.add(lblPesTot);
        lblPesTot.setBounds(432, 69, 130, 20);

        txtPesTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPesTot.setToolTipText("Número de egreso");
        txtPesTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesTotFocusGained(evt);
            }
        });
        panCabFil.add(txtPesTot);
        txtPesTot.setBounds(560, 69, 120, 20);

        butCarItm.setText("Cargar items");
        butCarItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarItmActionPerformed(evt);
            }
        });
        panCabFil.add(butCarItm);
        butCarItm.setBounds(330, 70, 100, 23);
        panCabFil.add(lblPorVar);
        lblPorVar.setBounds(255, 90, 60, 14);

        optPorVar.setText("Por porcentaje de variación");
        optPorVar.setEnabled(false);
        optPorVar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPorVarActionPerformed(evt);
            }
        });
        panCabFil.add(optPorVar);
        optPorVar.setBounds(20, 84, 300, 14);

        optItmApl.setText("Los primeros 13 items generados por el sistema");
        optItmApl.setEnabled(false);
        optItmApl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optItmAplActionPerformed(evt);
            }
        });
        panCabFil.add(optItmApl);
        optItmApl.setBounds(20, 98, 430, 14);

        optItmSelUsr.setSelected(true);
        optItmSelUsr.setText("Items seleccionados por usuario(13)");
        optItmSelUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optItmSelUsrActionPerformed(evt);
            }
        });
        panCabFil.add(optItmSelUsr);
        optItmSelUsr.setBounds(20, 112, 400, 14);

        panGenCab.add(panCabFil, java.awt.BorderLayout.CENTER);

        panCon.add(panGenCab, java.awt.BorderLayout.NORTH);

        panRep.setLayout(new java.awt.BorderLayout());

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

        panRep.add(panGenDet, java.awt.BorderLayout.CENTER);

        panCon.add(panRep, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panCon.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panCon);

        panTraInv.setLayout(new java.awt.BorderLayout());

        tblTrs.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTrs.setViewportView(tblTrs);

        panTraInv.add(spnTrs, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Transferencia de inventario", panTraInv);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBarBot.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBarBot, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(430, 180, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
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
            txtCodBodOri.setText("");
            txtNomBodOri.setText("");
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
        }
        else 
            txtDesLarTipDoc.setText(strDesLarTipDoc);

    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
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
            txtCodBodOri.setText("");
            txtNomBodOri.setText("");
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
        }
        else 
            txtDesCorTipDoc.setText(strDesCorTipDoc);

    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        txtCodBodOri.setText("");
        txtNomBodOri.setText("");
        txtCodBodDes.setText("");
        txtNomBodDes.setText("");
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try 
        {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                dispose();
            }
        } 
        catch (Exception e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodOriActionPerformed
        txtCodBodOri.transferFocus();
    }//GEN-LAST:event_txtCodBodOriActionPerformed

    private void txtCodBodOriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOriFocusGained
        strCodBodOri = txtCodBodOri.getText();
        txtCodBodOri.selectAll();
    }//GEN-LAST:event_txtCodBodOriFocusGained

    private void txtCodBodOriFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOriFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else
        {
            if (!txtCodBodOri.getText().equalsIgnoreCase(strCodBodOri)) 
            {
                if (txtCodBodOri.getText().equals("")) 
                {
                    txtCodBodOri.setText("");
                    txtNomBodOri.setText("");
                }
                else 
                {
                    mostrarVenConBodOri(1);
                }
            } 
            else
                txtCodBodOri.setText(strCodBodOri);
        }      
    }//GEN-LAST:event_txtCodBodOriFocusLost

    private void txtNomBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodOriActionPerformed
        txtNomBodOri.transferFocus();
    }//GEN-LAST:event_txtNomBodOriActionPerformed

    private void txtNomBodOriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOriFocusGained
        strNomBodOri = txtNomBodOri.getText();
        txtNomBodOri.selectAll();
    }//GEN-LAST:event_txtNomBodOriFocusGained

    private void txtNomBodOriFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOriFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else
        {
            if (!txtNomBodOri.getText().equalsIgnoreCase(strNomBodOri)) 
            {
                if (txtNomBodOri.getText().equals("")) 
                {
                    txtCodBodOri.setText("");
                    txtNomBodOri.setText("");
                } 
                else 
                {
                    mostrarVenConBodOri(2);
                }
            }
            else
                txtNomBodOri.setText(strNomBodOri);
        } 
           
    }//GEN-LAST:event_txtNomBodOriFocusLost

    private void butBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodOriActionPerformed
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else{
            mostrarVenConBodOri(0);
        }    
    }//GEN-LAST:event_butBodOriActionPerformed

    private void txtCodBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodDesActionPerformed
        txtCodBodDes.transferFocus();
    }//GEN-LAST:event_txtCodBodDesActionPerformed

    private void txtCodBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusGained
        strCodBodDes = txtCodBodDes.getText();
        txtCodBodDes.selectAll();
    }//GEN-LAST:event_txtCodBodDesFocusGained

    private void txtCodBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else{
           if (!txtCodBodDes.getText().equalsIgnoreCase(strCodBodDes)) {
            if (txtCodBodDes.getText().equals("")) {
                txtCodBodDes.setText("");
                txtNomBodDes.setText("");
            } else {
                mostrarVenConBodDes(1);
            }
        } else
            txtCodBodDes.setText(strCodBodDes);
        }   
        
    }//GEN-LAST:event_txtCodBodDesFocusLost

    private void txtNomBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodDesActionPerformed
        txtNomBodDes.transferFocus();
    }//GEN-LAST:event_txtNomBodDesActionPerformed

    private void txtNomBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusGained
        strNomBodDes = txtNomBodDes.getText();
        txtNomBodDes.selectAll();
    }//GEN-LAST:event_txtNomBodDesFocusGained

    private void txtNomBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else{
            if (!txtNomBodDes.getText().equalsIgnoreCase(strNomBodDes)) {
                if (txtNomBodDes.getText().equals("")) {
                    txtCodBodDes.setText("");
                    txtNomBodDes.setText("");
                } else {
                    mostrarVenConBodDes(2);
                }
            }
            else
                txtNomBodDes.setText(strNomBodDes);
        }             
    }//GEN-LAST:event_txtNomBodDesFocusLost

    private void butBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodDesActionPerformed
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else {
            mostrarVenConBodDes(0);
        }
    }//GEN-LAST:event_butBodDesActionPerformed

    private void butCarItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarItmActionPerformed
        bdePesTotTrs=new BigDecimal("0");
        objTblModRep.removeAllRows();
        objTblModRepTrs.removeAllRows();
        txtPesTot.setText("0");
        intNumRegSel=0;
        if (butCarItm.getText().equals("Cargar items")) 
        {
            //objTblTotales.isActivo(false);
            blnCon = true;
            if (objThrGUI == null) 
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else 
        {
            blnCon = false;
        }

    }//GEN-LAST:event_butCarItmActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void optPorVarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPorVarActionPerformed
        if(optPorVar.isSelected())
        {
            optItmApl.setSelected(false);
            optItmSelUsr.setSelected(false);
            objTblModRep.removeAllRows();
            objTblModRepTrs.removeAllRows();
        }
        else
        {
            optPorVar.setSelected(true);
        }
    }//GEN-LAST:event_optPorVarActionPerformed

    private void optItmAplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optItmAplActionPerformed
        if(optItmApl.isSelected())
        {
            optPorVar.setSelected(false);
            optItmSelUsr.setSelected(false);
            objTblModRep.removeAllRows();
            objTblModRepTrs.removeAllRows();
        }
        else
        {
            optItmApl.setSelected(true);
        }
    }//GEN-LAST:event_optItmAplActionPerformed

    private void optItmSelUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optItmSelUsrActionPerformed
        if(optItmSelUsr.isSelected())
        {
            optPorVar.setSelected(false);
            optItmApl.setSelected(false);
            objTblModRep.removeAllRows();
            objTblModRepTrs.removeAllRows();
        }
        else
        {
            optItmSelUsr.setSelected(true);
        }
    }//GEN-LAST:event_optItmSelUsrActionPerformed

    private void txtPesTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesTotFocusGained
       strPesTotKgrSolTra = txtPesTot.getText();
    }//GEN-LAST:event_txtPesTotFocusGained

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBodDes;
    private javax.swing.JButton butBodOri;
    private javax.swing.JButton butCarItm;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBodDes;
    private javax.swing.JLabel lblBodOri;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPesDis;
    private javax.swing.JLabel lblPesTot;
    private javax.swing.JLabel lblPorVar;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optItmApl;
    private javax.swing.JRadioButton optItmSelUsr;
    private javax.swing.JRadioButton optPorVar;
    private javax.swing.JPanel panBarBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panTraInv;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnTrs;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTrs;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBodDes;
    private javax.swing.JTextField txtCodBodOri;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBodDes;
    private javax.swing.JTextField txtNomBodOri;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtPesDis;
    private javax.swing.JTextField txtPesTot;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
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

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() 
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBodOri.getDocument().addDocumentListener(objDocLis);
        txtNomBodOri.getDocument().addDocumentListener(objDocLis);
        txtCodBodDes.getDocument().addDocumentListener(objDocLis);
        txtNomBodDes.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtPesDis.getDocument().addDocumentListener(objDocLis);
        txtPesTot.getDocument().addDocumentListener(objDocLis);
    }

    private class MiToolBar extends ZafToolBar 
    {

        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
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

        public boolean afterConsultar() 
        {
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TRS_BUT);
            objTblModRepTrs.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);
            tblTrs.setEnabled(true);

            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() 
        {
            //System.out.println("afterInsertar");
            //Configurar JFrame de acuerdo al estado del registro.
            blnChnEstImp=true;
            objTooBar.setEstado('w');
            imprimirOrdDes();
            consultarReg();
            blnHayCam=false;
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TRS_BUT);
            objTblModRepTrs.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);
            tblTrs.setEnabled(true);
            blnChnEstImp=false;
            return true;
        }

        public boolean afterModificar() 
        {
            blnHayCam=false;
            return true;
        }

        public boolean afterVistaPreliminar() 
        {
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

        public boolean beforeConsultar() 
        {
            if(objParSis.getCodigoUsuario()!=1)
            {
                if( ! isBodOri())
                    return false;
            }

            return true;
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

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeInsertar() 
        {
            boolean blnRes=false;
            if (isCamVal())
                if(isItmRep())
                    blnRes=true;
            return blnRes;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean cancelar() 
        {
            boolean blnRes=true;
            try{
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
                
        public void clickInicio() {
            try{
                if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf=0;
                            cargarReg();
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
                if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf--;
                            cargarReg();
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
                 if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf < arlDatConCnf.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf++;
                            cargarReg();
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
                 if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf<arlDatConCnf.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf=arlDatConCnf.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf=arlDatConCnf.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }                
        }        

        public void clickAceptar() {
            
        }

        public void clickAnular() {
            
        }

        public void clickCancelar() {
            
        }

        public void clickConsultar()
        {
            mostrarTipDocPre();
            configurarVenConBodOri();
            configurarVenConBodDes();
            if( ! objTooBar.isOpcionEnabled(2337))
            {
                butCarItm.setEnabled(true);
                optPorVar.setEnabled(false);
                optItmApl.setEnabled(false);
                optItmSelUsr.setEnabled(false);
            }
        }
        
        public void clickEliminar() {
            
        }
                
        public void clickImprimir() {
            
        }

        public void clickInsertar() 
        {
            try
            {
                //System.out.println("clickInsertar");
                if (blnHayCam)
                {
                    isRegPro();
                }
                limpiarFrm();
                txtCodDoc.setEditable(false);
                txtPesTot.setEditable(false);
                datFecAux=null;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                optPorVar.setEnabled(false);
                optItmApl.setEnabled(false);
                //datFecAux=null;
                mostrarTipDocPre();
                configurarVenConBodOri();
                configurarVenConBodDes();
                objTblModRep.setModoOperacion(objTblModRep.INT_TBL_INS);
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                blnHayCam=false;
                butCarItm.setEnabled(true);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar() {
            inactivarCampos();
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
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }
        
    }//Fin MiToolBar
  
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="áDesea guardar los cambios efectuados a áste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la informacián que no haya guardado.";
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
            txtCodBodOri.setText("");
            txtNomBodOri.setText("");
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            txtPesDis.setText("");
            txtPesTot.setText("");
            objTblModRep.removeAllRows();
            lblPorVar.setText("");
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
     * @return La descripcián larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacáa.
     */
    private String getEstReg(String estado)
    {
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
                case 'E':
                    estado="Eliminado";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
            
        return estado;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        String strFecDocTmp="";
        int intTipCamFec;
        String strFecAuxTmp="";
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar ""Bodega Origen"
        if (txtCodBodOri.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega origen </FONT> es obligatorio.<BR>Escriba o seleccione una bodega origen y vuelva a intentarlo.</HTML>");
            txtNomBodOri.requestFocus();
            return false;
        }
        //Validar "Bodega Destino"
        if (txtCodBodDes.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega destino </FONT> es obligatorio.<BR>Escriba o seleccione una bodega destino y vuelva a intentarlo.</HTML>");
            txtNomBodDes.requestFocus();
            return false;
        }

        //Validar Otros Datos
        if(objTooBar.isOpcionEnabled(2337))
        {
            //"Numero de Documento."
            if (txtNumDoc.getText().equals("")) 
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNomBodDes.requestFocus();
                return false;
            }
            //"Peso Disponible"
            if (txtPesDis.getText().equals("")) 
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Peso disponible</FONT> es obligatorio.<BR>Escriba el valor de Peso disponible y vuelva a intentarlo.</HTML>");
                txtPesDis.requestFocus();
                return false;
            }
            //"Items"
            if(( ! optPorVar.isSelected())  &&  ( ! optItmApl.isSelected())  &&  ( ! optItmSelUsr.isSelected())  )
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe seleccionar alguno de los filtros para generar items a reponer.<BR>Seleccione alguno y vuelva a intentarlo.</HTML>");
                return false;
            }
            
            //<editor-fold defaultstate="collapsed" desc="/* Fecha del documento*/">.
            if (!dtpFecDoc.isFecha())
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
                dtpFecDoc.requestFocus();
                return false;
            }
            else
            {
                intTipCamFec=canChangeDate();
                switch(intTipCamFec)
                {
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
                                switch (mostrarMsgCon(strMsj)){
                                    case 0: //YES_OPTION
                                        //System.out.println("POR YES");
                                        return true;
                                    case 1: //NO_OPTION
                                        //System.out.println("POR NO");
                                        return false;
                                    case 2: //CANCEL_OPTION
                                        //System.out.println("POR CANCEL");
                                        return false;
                                }
                                datFecAux=null;
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
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
            }
            //</editor-fold>
        }

        return true;
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
            if (!cargarRegRep())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                butCarItm.setText("Cargar items");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(0);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }

   /**
    * Función que carga registros de ítems a Reponer
    * @return 
    */
   private boolean cargarRegRep()
   {
        boolean blnRes=true;
        Connection conLoc1;
        try
        {
            //System.out.println("cargarRegRep");
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc1!=null)
            {
                objTblModRep.setModoOperacion(objTblModRep.INT_TBL_NO_EDI);
                //  objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI);
                if(isCamVal())
                {
                    butCarItm.setText("Obteniendo datos...");
                    if(consultarDatTbl(conLoc1))
                    {
                        if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)) 
                        { 
                            intOpcRedondeoStock=0;
                            intOpcRedondeoMinimo=0;
                        }
                        else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)) 
                        {
                            intOpcRedondeoStock=2;
                            intOpcRedondeoMinimo=2;
                        }
                        if(optPorVar.isSelected()){
                            if(seleccionarItmRepVar()){
                            }
                        }
                        else if(optItmApl.isSelected()){
                            if(seleccionarItmRepItmSel(13)){
                            }
                        }
                        else if(optItmSelUsr.isSelected()) //  José Marín M. 15/Jul/2014
                        {
                            vecAux=new Vector();
                            vecAux.add(""+INT_TBL_DAT_CAN_REP_SEL);
                            objTblModRep.setColumnasEditables(vecAux);
                            vecAux=null;
                            objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI);
                        }
                    }
                }
                butCarItm.setText("Cargar items");
                objTblModRep.initRowsState();
                conLoc1.close();
                conLoc1=null;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    private boolean consultarDatTbl(Connection con)
    {
        boolean blnRes=true;
        int intCanRepEnt=-1;
        float fltCanRepFrc=-1;
        BigDecimal bdeCanRepEntFrc=new BigDecimal("0");
        String strTipUniMed="";

        try
        {
            if(con!=null)
            {
                objTooBar.setMenSis("Obteniendo datos...");
                stm=con.createStatement();
                
                //REINBO  
               if(txtCodTipDoc.getText().equals("161")) //REINBO
                {
                    strSQL="";
                    strSQL+="SELECT b1.co_emp, b1.co_itm, b2.co_itmMae, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor \n";
                    strSQL+=" , b1.tx_desLar, b1.tx_ubi, b1.tx_tipunimed, b2.nd_stkAct, b5.nd_stkActCenDis, b1.nd_pesitmkgr, b1.nd_stkmin AS nd_stkmin, b1.nd_stkExc \n";
                    strSQL+=" ,(b1.nd_stkmin - b2.nd_stkAct) AS nd_valRep, (b3.nd_stkActEmp - (b4.nd_repmin + b4.nd_stkExc)) AS nd_stkActDis \n";
                    strSQL+=" , b1.nd_repmin, b1.nd_repmax, b3.nd_canDisEmp  \n";
                    strSQL+=" FROM( \n";
                    strSQL+="    SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar, a6.tx_ubi, a2.tx_tipunimed \n";
                    strSQL+="    , a3.co_itmMae, a1.nd_pesitmkgr \n";
                    strSQL+="    , CASE WHEN a4.nd_stkmin IS NULL THEN 0 ELSE a4.nd_stkmin END AS nd_stkmin \n";
                    strSQL+="    , CASE WHEN a4.nd_stkExc IS NULL THEN 0 ELSE a4.nd_stkExc END AS nd_stkExc \n";
                    strSQL+="    , CASE WHEN a4.nd_repmin IS NULL THEN 0 ELSE a4.nd_repmin END AS nd_repmin \n";
                    strSQL+="    , CASE WHEN a4.nd_repmax IS NULL THEN 0 ELSE a4.nd_repmax END AS nd_repmax \n";
                    strSQL+="    FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) \n";
                    strSQL+="    INNER JOIN tbm_equInv AS a3 \n";
                    strSQL+="    ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm \n";
                    strSQL+="    INNER JOIN tbm_invBod AS a4 \n";
                    strSQL+="    ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm \n";
                    
                    strSQL+="     LEFT OUTER JOIN  \n";
                    strSQL+=" 	  ( \n";
                    strSQL+=" 	    SELECT DISTINCT b1.co_itmMae, b2.tx_ubi \n";
                    strSQL+=" 	    FROM tbm_equInv as b1 \n";
                    strSQL+=" 	    INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) \n";
                    strSQL+=" 	    INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod) \n";
                    strSQL+=" 	    WHERE b3.co_empgrp=" +objParSis.getCodigoEmpresaGrupo()+" AND b3.co_bodgrp="+txtCodBodOri.getText()+"\n"; 
                    strSQL+=" 	  ) as a6 ON (a3.co_itmMae=a6.co_itmMae)\n";
                    
                    strSQL+="     WHERE a1.co_emp=" + intCodEmpGrp + "\n";
                    strSQL+=" 	  AND (   (UPPER(a1.tx_codAlt) LIKE '%I')  OR (UPPER(a1.tx_codAlt) LIKE '%S') ) AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')\n";
                    strSQL+=" 	  AND a4.co_bod=" + txtCodBodDes.getText() + "\n";
                    strSQL+="     GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, \n";
                    strSQL+="     a2.tx_desLar,  a6.tx_ubi, a2.tx_tipunimed, a3.co_itmMae, a1.nd_pesitmkgr, a4.nd_stkmin, a4.nd_stkExc \n";
                    strSQL+="     ,a4.nd_repmin, a4.nd_repmax \n";
                    strSQL+="     ORDER BY a1.tx_codAlt \n";
                    strSQL+=" ) AS b1 \n";
                    strSQL+=" INNER JOIN ( \n";//para obtener el stock de las bodegas fisicas
                    strSQL+="     SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct \n";  // JM 
                    strSQL+="     FROM( \n";
                    strSQL+="         SELECT x.*, y.co_empGrp \n";
                    strSQL+="         FROM( \n";
                    strSQL+="               SELECT a1.co_emp, a1.co_itm, a1.co_bod, a2.co_itmMae, \n";
                    strSQL+="                      (a1.nd_stkAct - CASE WHEN a1.nd_canResVen IS NULL THEN 0 ELSE a1.nd_canResVen END) as nd_stkAct \n";
                    strSQL+="               FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm \n";
                    strSQL+="              ) AS x \n";
                    strSQL+="         INNER JOIN( \n";
                    strSQL+="               SELECT co_emp, co_bod, co_empGrp \n";
                    strSQL+="               FROM tbr_bodEmpBodGrp \n";
                    strSQL+="               WHERE co_empGrp=" + intCodEmpGrp + " AND co_bodGrp=" + txtCodBodDes.getText() + "\n";
                    strSQL+="               ORDER BY co_emp \n";
                    strSQL+="              ) AS y ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod \n";
                    strSQL+="            ) AS z \n";
                    strSQL+=" GROUP BY z.co_empGrp, z.co_itmMae \n";
                    strSQL+=" ) AS b2 ON b1.co_itmMae=b2.co_itmMae \n";
                    strSQL+=" INNER JOIN ( \n";
                    strSQL+="             SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkActEmp) AS nd_stkActEmp, SUM(z.nd_canDisEmp) as nd_canDisEmp \n";
                    strSQL+="             FROM( \n";
                    strSQL+="                    SELECT x.*, y.co_empGrp  \n";
                    strSQL+="                    FROM(   SELECT a1.co_emp, a1.co_itm, a1.co_bod, \n";
                    strSQL+="                                   case when a1.nd_stkAct is null then 0 else a1.nd_stkAct end AS nd_stkActEmp \n";
                    strSQL+="                                   , CASE WHEN a1.nd_canDis IS NULL THEN 0 ELSE a1.nd_canDis END AS nd_canDisEmp   ";  // JM 
                    strSQL+="                                   , a2.co_itmMae \n";
                    strSQL+="                            FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2 \n";
                    strSQL+="                                             ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm \n";
                    strSQL+="                    ) AS x \n";
                    strSQL+="                    INNER JOIN( \n";
                    strSQL+="                            SELECT co_emp, co_bod, co_empGrp \n";
                    strSQL+="                            FROM tbr_bodEmpBodGrp \n";
                    strSQL+="                            WHERE co_empGrp=" + intCodEmpGrp + "  \n";
                    strSQL+="                                  AND co_bodGrp=" + txtCodBodOri.getText() + "  \n";
                    strSQL+="                            ORDER BY co_emp  \n";
                    strSQL+="                    ) AS y  \n";
                    strSQL+="                    ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod  \n";
                    strSQL+="               ) AS z  \n  ";
                    strSQL+="               GROUP BY z.co_empGrp, z.co_itmMae \n";
                    strSQL+=" ) AS b3 \n";
                    strSQL+=" ON b1.co_itmMae=b3.co_itmMae \n";
                    strSQL+=" INNER JOIN ( \n";
                    strSQL+="       SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar  \n";
                    strSQL+="       , a3.co_itmMae, a1.nd_pesitmkgr  \n";
                    strSQL+="       , CASE WHEN a4.nd_stkmin IS NULL THEN 0 ELSE a4.nd_stkmin END AS nd_stkmin  \n";
                    strSQL+="       , CASE WHEN a4.nd_stkExc IS NULL THEN 0 ELSE a4.nd_stkExc END AS nd_stkExc  \n";
                    strSQL+="       , CASE WHEN a4.nd_repmin IS NULL THEN 0 ELSE a4.nd_repmin END AS nd_repMin  \n";
                    strSQL+="       , CASE WHEN a4.nd_repmax IS NULL THEN 0 ELSE a4.nd_repmax END AS nd_repMax  \n";
                    strSQL+="       FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)  \n";
                    strSQL+="       INNER JOIN tbm_equInv AS a3  \n";
                    strSQL+="       ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm  \n";
                    strSQL+="       INNER JOIN tbm_invBod AS a4  \n";
                    strSQL+="       ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm  \n";
                    strSQL+="       WHERE a1.co_emp=" + intCodEmpGrp + "  \n";
                    strSQL+=" 	    AND (   (UPPER(a1.tx_codAlt) LIKE '%I') OR (UPPER(a1.tx_codAlt) LIKE '%S') ) AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')  \n";
                    strSQL+=" 	    AND a4.co_bod=" + txtCodBodOri.getText() + "  \n";
                    strSQL+="       GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor,  \n";
                    strSQL+="       a2.tx_desLar, a3.co_itmMae, a1.nd_pesitmkgr, a4.nd_stkmin, a4.nd_stkExc, a4.nd_repmin, a4.nd_repmax  \n";
                    strSQL+="       ORDER BY a1.tx_codAlt  \n";
                    strSQL+=" ) AS b4  \n";
                    strSQL+=" ON b1.co_itmMae=b4.co_itmMae  \n";
                    
                    //inmaconsa
                    strSQL+=" INNER JOIN (  \n";
                    strSQL+="                              SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkActEmp) AS nd_stkActCenDis  \n";
                    strSQL+="                               FROM(  \n";
                    strSQL+="                                     SELECT x.*, y.co_empGrp FROM(  \n";
                    strSQL+="                                             SELECT a1.co_emp, a1.co_itm, a1.co_bod,  \n ";
                    strSQL+="                                             case when a1.nd_stkAct is null then 0 else a1.nd_stkAct end AS nd_stkActEmp  \n";
                    strSQL+="                                             , a2.co_itmMae  \n";
                    strSQL+="                                             FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2  \n";
                    strSQL+="                                             ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm  \n";
                    strSQL+="                                     ) AS x  \n";
                    strSQL+="                                     INNER JOIN(  \n";
                    strSQL+="                                             SELECT co_emp, co_bod, co_empGrp  \n";
                    strSQL+="                                             FROM tbr_bodEmpBodGrp  \n";
                    strSQL+="                                             WHERE co_empGrp=" + intCodEmpGrp + "  \n";
                    strSQL+="                                             AND co_bodGrp=" + INT_COD_BOD_CEN_DIS + "  \n";

                    strSQL+="                                             ORDER BY co_emp  \n";
                    strSQL+="                                     ) AS y  \n";
                    strSQL+="                                     ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod  \n";
                    strSQL+="                               ) AS z  \n";
                    strSQL+="               GROUP BY z.co_empGrp, z.co_itmMae  \n";
                    strSQL+=" ) AS b5  \n";
                    strSQL+=" ON b1.co_itmMae=b5.co_itmMae  \n";
                    
                    //Antes: solo si la cantidad stisfacia
                    //  strSQL+=" WHERE (b2.nd_stkAct - b1.nd_repmin) < 0";
                    //  strSQL+=" AND (b3.nd_stkActEmp - b4.nd_stkmin + b4.nd_stkExc) > 0";//disponibilidad por empresa
                    //  strSQL+=" AND b3.nd_stkActEmp>b4.nd_repMax";
                    //Fin
                    
                    //Despues: Si existe algo de stock que se pueda reponer, ya no es necesariamente toda la cantidad, puede ser un parcial de lo que se necesita
                    strSQL+=" WHERE (b2.nd_stkAct - b1.nd_repmin) < 0  \n ";
                    //strSQL+=" AND (b3.nd_stkActEmp - (b4.nd_repmax + b4.nd_stkExc))  > 0";  
                    //Solicitado por Ing. Werner Campoverde: Se cambia la validación de cantidad disponible por grupo del centro de distribución de Máxima a Mínima.
                    strSQL+=" AND (b3.nd_stkActEmp - (b4.nd_repmin + b4.nd_stkExc))  > 0  \n";
                    //Fin
                    
                    
                    if(objParSis.getCodigoUsuario()==24)
                        strSQL+=" ORDER BY tx_codAlt  \n";
                    else
                        strSQL+=" ORDER BY nd_stkAct  \n";
                    
                }
                else if(txtCodTipDoc.getText().equals("223")) //REINBD
                {
                    strSQL="";
                    strSQL+=" SELECT b1.co_emp, b1.co_itm, b2.co_itmMae, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor  \n";
                    strSQL+=" , b1.tx_desLar, b1.tx_ubi, b1.tx_tipunimed, b2.nd_stkAct, b3.nd_stkActCenDis, b1.nd_pesitmkgr, b1.nd_stkmin AS nd_stkmin, b1.nd_stkExc  \n";
                    //valor a reponer es en realidad lo que se debe dvolver por excedente, por tanto el valor a reponer es el mismo del disponible
                    strSQL+=" ,(b2.nd_stkAct - b1.nd_repmax - b1.nd_stkExc) AS nd_valRep, (b2.nd_stkAct - b1.nd_repmin - b1.nd_stkExc) AS nd_stkActDis  \n";
                    strSQL+=" , b1.nd_repmin, b1.nd_repmax  ,b2.nd_canDis as nd_canDisEmp  \n";
                    strSQL+=" FROM(  \n";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor \n";
                    strSQL+=" 	, a2.tx_desLar, a6.tx_ubi, a2.tx_tipunimed, a3.co_itmMae, a1.nd_pesitmkgr  \n";
                    strSQL+=" 	, CASE WHEN a4.nd_stkmin IS NULL THEN 0 ELSE a4.nd_stkmin END AS nd_stkmin  \n";
                    strSQL+=" 	, CASE WHEN a4.nd_stkExc IS NULL THEN 0 ELSE a4.nd_stkExc END AS nd_stkExc  \n";
                    strSQL+="   , CASE WHEN a4.nd_repmin IS NULL THEN 0 ELSE a4.nd_repmin END AS nd_repmin  \n";
                    strSQL+="   , CASE WHEN a4.nd_repmax IS NULL THEN 0 ELSE a4.nd_repmax END AS nd_repmax  \n";
                    strSQL+=" 	FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)  \n";
                    strSQL+=" 	INNER JOIN tbm_equInv AS a3  \n";
                    strSQL+=" 	ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm  \n";
                    strSQL+=" 	INNER JOIN tbm_invBod AS a4  \n";
                    strSQL+=" 	ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm  \n";
                    
                    strSQL+="     LEFT OUTER JOIN  \n";
                    strSQL+=" 	  (  \n";
                    strSQL+=" 	    SELECT DISTINCT b1.co_itmMae, b2.tx_ubi  \n";
                    strSQL+=" 	    FROM tbm_equInv as b1 \n";
                    strSQL+=" 	    INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) \n";
                    strSQL+=" 	    INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod) \n";
                    strSQL+=" 	    WHERE b3.co_empgrp=" +objParSis.getCodigoEmpresaGrupo()+" AND b3.co_bodgrp="+txtCodBodOri.getText()+" \n";
                    strSQL+=" 	  ) as a6 ON (a3.co_itmMae=a6.co_itmMae) \n";
                    
                    strSQL+=" 	  WHERE a1.co_emp=" + intCodEmpGrp + "  \n";
                    strSQL+=" 	   AND (  (UPPER(a1.tx_codAlt) LIKE '%I') OR (UPPER(a1.tx_codAlt) LIKE '%S')  ) AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')  \n";
                    strSQL+=" 	   AND a4.co_bod=" + txtCodBodOri.getText() + " \n";
                    strSQL+=" 	  GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor,  \n";
                    strSQL+=" 	  a2.tx_desLar, a6.tx_ubi, a2.tx_tipunimed, a3.co_itmMae, a1.nd_pesitmkgr, a4.nd_stkmin, a4.nd_stkExc  \n";
                    strSQL+="     ,a4.nd_repmin, a4.nd_repmax \n";
                    strSQL+=" 	  ORDER BY a1.tx_codAlt \n";
                    strSQL+=" ) AS b1  \n";
                    strSQL+=" INNER JOIN (  \n";
                    strSQL+=" 	    SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct, SUM(z.nd_canDis) as nd_canDis   \n";
                    strSQL+=" 	     FROM(  \n";
                    strSQL+=" 		SELECT x.*, y.co_empGrp FROM(  \n";
                    strSQL+=" 			SELECT a1.co_emp, a1.co_itm, a1.co_bod, a2.co_itmMae  \n";
                    strSQL+=" , (a1.nd_stkAct - CASE WHEN a1.nd_canResVen IS NULL THEN 0 ELSE a1.nd_canResVen END ) as nd_stkAct  \n";
                    strSQL+=" , CASE WHEN a1.nd_canDis IS NULL THEN 0 ELSE a1.nd_canDis END as nd_canDis ";
                    strSQL+=" 			FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                    strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+=" 		) AS x  \n";
                    strSQL+=" 		INNER JOIN(  \n";
                    strSQL+=" 			SELECT co_emp, co_bod, co_empGrp  \n";
                    strSQL+=" 			FROM tbr_bodEmpBodGrp";
                    strSQL+=" 				WHERE co_empGrp=" + intCodEmpGrp + "  \n";
                    strSQL+=" 				 AND co_bodGrp=" + txtCodBodOri.getText() + "  \n";
                    strSQL+=" 			ORDER BY co_emp  \n";
                    strSQL+=" 		) AS y  \n";
                    strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod  \n";
                    strSQL+=" 	     ) AS z \n";
                    strSQL+=" GROUP BY z.co_empGrp, z.co_itmMae \n";
                    strSQL+=" ) AS b2  \n";
                    strSQL+=" ON b1.co_itmMae=b2.co_itmMae  \n";
                    
                    //para saber el stock en inmaconsa
                    strSQL+=" INNER JOIN (  \n";
                    strSQL+=" 	    SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkActCenDis  \n";
                    strSQL+=" 	     FROM(  \n";
                    strSQL+=" 		SELECT x.*, y.co_empGrp FROM(  \n";
                    strSQL+=" 			SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae  \n";
                    strSQL+=" 			FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2  \n";
                    strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm  \n";
                    strSQL+=" 		) AS x  \n";
                    strSQL+=" 		INNER JOIN(  \n";
                    strSQL+=" 			SELECT co_emp, co_bod, co_empGrp  \n";
                    strSQL+=" 			FROM tbr_bodEmpBodGrp  \n";
                    strSQL+=" 				WHERE co_empGrp=" + intCodEmpGrp + "  \n";
                    strSQL+=" 				 AND co_bodGrp=" + INT_COD_BOD_CEN_DIS + "  \n";
                    strSQL+=" 			ORDER BY co_emp  \n";
                    strSQL+=" 		) AS y  \n";
                    strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod  \n";
                    strSQL+=" 	     ) AS z  \n";
                    strSQL+=" GROUP BY z.co_empGrp, z.co_itmMae  \n";
                    strSQL+=" ) AS b3  \n";
                    strSQL+=" ON b1.co_itmMae=b3.co_itmMae  \n";
                    
                    //strSQL+=" WHERE (b2.nd_stkAct - b1.nd_stkmin - b1.nd_stkExc) > 0";
                    strSQL+=" WHERE (b2.nd_stkAct - b1.nd_repmin - b1.nd_stkExc) > 0  \n";


                    if(objParSis.getCodigoUsuario()==24)
                        strSQL+=" ORDER BY tx_codAlt  \n";
                    else
                        strSQL+=" ORDER BY nd_stkAct  \n";
                }

                System.out.println("consultarDatTbl:  \n " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                objTooBar.setMenSis("Cargando datos...");

                BigDecimal bdeMin=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeMax=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeStk=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeExc=new BigDecimal(BigInteger.ZERO);

                while(rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,                 "" + rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_CHK,                     null);
                        vecReg.add(INT_TBL_DAT_COD_ITM,                 "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,             "" + rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,             "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,             "" + rst.getString("tx_codAlt2")); 
                        vecReg.add((INT_TBL_DAT_NOM_ITM),               "" + rst.getString("tx_nomItm"));
                        vecReg.add((INT_TBL_DAT_DES_COR_UNI_MED),       "" + rst.getString("tx_desCor"));
                        vecReg.add((INT_TBL_DAT_UBI),       "" + rst.getString("tx_ubi")==null?"":rst.getString("tx_ubi")); 
                        vecReg.add((INT_TBL_DAT_STK_ACT_BOD_CEN_DIS),   "" + rst.getString("nd_stkActCenDis"));
                        vecReg.add((INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP),     "" + rst.getObject("nd_stkActDis")==null?"0":rst.getString("nd_stkActDis"));//
                        vecReg.add((INT_TBL_DAT_CAN_DIS),""+ rst.getObject("nd_canDisEmp")==null?"0":rst.getString("nd_canDisEmp"));  // DIPONIBLE BODEGA DE ORIGEN
                        vecReg.add((INT_TBL_DAT_CAN_REP_CAL),     "");//de acuerdo al tipo de unidade de medida              
                        vecReg.add((INT_TBL_DAT_CAN_REP_SEL),     null);
                        vecReg.add((INT_TBL_DAT_CAN_REP_DIS),     null);
                        vecReg.add((INT_TBL_DAT_PES),             "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
                        vecReg.add((INT_TBL_DAT_PES_TOT_KGR),     "");
                        vecReg.add((INT_TBL_DAT_STK_MIN),         "" + rst.getObject("nd_stkmin")==null?"0":rst.getString("nd_stkmin"));
                        vecReg.add((INT_TBL_DAT_STK_ACT),         "" + rst.getObject("nd_stkAct")==null?"0":rst.getString("nd_stkAct"));

                        bdeMin=new BigDecimal(rst.getObject("nd_repmin")==null?"0":(rst.getString("nd_repmin").equals("")?"0":rst.getString("nd_repmin")));
                        bdeMax=new BigDecimal(rst.getObject("nd_repmax")==null?"0":(rst.getString("nd_repmax").equals("")?"0":rst.getString("nd_repmax")));
                        bdeStk=new BigDecimal(rst.getObject("nd_stkAct")==null?"0":(rst.getString("nd_stkAct").equals("")?"0":rst.getString("nd_stkAct")));
                        bdeExc=new BigDecimal(rst.getObject("nd_stkExc")==null?"0":(rst.getString("nd_stkExc").equals("")?"0":rst.getString("nd_stkExc")));

                        vecReg.add((INT_TBL_DAT_MIN),  "" + bdeMin);//
                        vecReg.add((INT_TBL_DAT_MAX),  "" + bdeMax);//
                        vecReg.add((INT_TBL_DAT_UNI_MED),""+ rst.getObject("tx_tipunimed")==null?"0":rst.getString("tx_tipunimed"));//  José Marín 
                        
                        //el dato sigue en el query por si acaso, antes se calculaba asi
                        // (b2.nd_stkAct - b1.nd_stkmin - b1.nd_stkExc) AS nd_valRep
                        //bdeCanRepEntFrc=new BigDecimal(rst.getObject("nd_valRep")==null?"0":rst.getString("nd_valRep"));//entero y fraccion

                        if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO))
                        {
                            if(bdeStk.compareTo(bdeMin)<0)//se repone
                            {
                                bdeCanRepEntFrc=bdeMax.subtract(bdeStk);
                            }
                            else
                            {
                                bdeCanRepEntFrc=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                        else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD))
                        {
                            //para traer excedente    (b2.nd_stkAct - b1.nd_repmin - b1.nd_stkExc) > 0
                            if((bdeStk.subtract(bdeMax).subtract(bdeExc)).compareTo(new BigDecimal("0")) >0  ){//se repone  bdeExc
                                bdeCanRepEntFrc=bdeStk.subtract(bdeMax).subtract(bdeExc);
                            }
                            else{
                                bdeCanRepEntFrc=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                        intCanRepEnt=(bdeCanRepEntFrc.intValue());//parte entera
                        fltCanRepFrc=((bdeCanRepEntFrc.floatValue()) - (bdeCanRepEntFrc.intValue()));

                        strTipUniMed=rst.getObject("tx_tipunimed")==null?"0":rst.getString("tx_tipunimed"); 
                        
                        if(strTipUniMed.equals("E")) // ENTERA 
                            vecReg.setElementAt("" + intCanRepEnt, INT_TBL_DAT_CAN_REP_CAL);
                        else if(strTipUniMed.equals("F")) //FRACCION
                            vecReg.setElementAt(bdeCanRepEntFrc, INT_TBL_DAT_CAN_REP_CAL);
                        double dblCan; //  José Marín 
                        if(strTipUniMed.equals("E")) {  //  José Marín 
                            dblCan=(Double.parseDouble(String.valueOf(intCanRepEnt))) * (Double.parseDouble(rst.getString("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr")));
                            vecReg.setElementAt("" + dblCan, INT_TBL_DAT_PES_TOT_KGR);
                        }
                        else if(strTipUniMed.equals("F")) { //  José Marín 
                            dblCan=(bdeCanRepEntFrc.floatValue()) * (Double.parseDouble(rst.getString("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr")));
                            vecReg.setElementAt("" + dblCan, INT_TBL_DAT_PES_TOT_KGR);
                        }

                        vecDat.add(vecReg);
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
                vecAux.add("" + INT_TBL_DAT_CAN_REP_SEL);

                if(optItmSelUsr.isSelected()){
                    vecAux.add("" + INT_TBL_DAT_CHK);
                    objTooBar.setMenSis("Listo");
                }

                objTblModRep.setColumnasEditables(vecAux);
                vecAux=null;

                //Asignar vectores al modelo.
                objTblModRep.setData(vecDat);

                tblDat.setModel(objTblModRep);
                vecDat.clear();
                butCarItm.setText("Cargar items");
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);   blnRes=false; }
        return blnRes;
    }


    private boolean seleccionarItmRepVar()
    {
        boolean blnRes=true;
        BigDecimal bdePesDisUsr=new BigDecimal(txtPesDis.getText());
        BigDecimal bdePesDisSel=new BigDecimal("0");
        BigDecimal bdePorVar=new BigDecimal("1");
        int intNumRegChk=0;
        BigDecimal bdeStkMin=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkAct=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkRep=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkMinCalPor=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePesRegSel=new BigDecimal("0");
        BigDecimal bdeStkDisGrp=new BigDecimal(BigInteger.ZERO);
        //captura los valores del ultimo porcentaje de variacion con el ultimo numero de items q se podian reponer
        BigDecimal bdePorVarConVal=new BigDecimal("0");
        int intNumItemPueRep=0;
        intOpcRedondeoStock++;
        String strCodAltItm="", strSubCodAltItm="", strCodAltDos="";//se lo almacena para luego compararlo y saber si esta entre iniciales 124,125,870,871,873,874 TUBERIAS y asi no redondearlos
        String strUniMed="", strUbiItm="";
        try
        {
            do
            {
                intNumRegChk=0;
                bdePesRegSel=new BigDecimal("0");
                bdePesDisSel=new BigDecimal("0");
                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    objTblModRep.setChecked(false, i, INT_TBL_DAT_CHK);
                    objTblModRep.setValueAt(null, i, INT_TBL_DAT_CAN_REP_SEL);
                    txtPesTot.setText("");
                }
                objTblModRep.initRowsState();

                if(bdePorVar.compareTo(new BigDecimal("0"))<=0) //disminuye de 1 en 1
                {
                    lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                    bdePorVarGrl=bdePorVar;
                    break;
                }

                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    bdeStkMin=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN).toString());
                    bdeStkAct=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT).toString());
                    bdeStkDisGrp=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());

                    strCodAltItm=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                    strSubCodAltItm=strCodAltItm.substring(0, 4);
                    
                    strCodAltDos=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS).toString();

                    strUniMed=objTblModRep.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED).toString();

                    strUbiItm=objTblModRep.getValueAt(i, INT_TBL_DAT_UBI)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_UBI).toString();


                    if(bdeStkAct.compareTo(new BigDecimal("0"))<0){
                    }
                    else{
                        if( (strSubCodAltItm.trim().equals("124-")) || (strSubCodAltItm.trim().equals("125-")) || (strSubCodAltItm.trim().equals("870-")) || (strSubCodAltItm.trim().equals("871-")) || (strSubCodAltItm.trim().equals("873-")) || (strSubCodAltItm.trim().equals("874-"))        )
                        {
                            bdeStkMinCalPor=bdeStkMin.multiply(bdePorVar);
                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));

                            if(strUniMed.trim().equals("PZAS"))
                                bdeStkRep=bdeStkRep.divide(new BigDecimal("1"), 0, BigDecimal.ROUND_DOWN);

                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){  
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                              else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.compareTo(new BigDecimal("0"))<0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }
                        else{
                            if(intOpcRedondeoMinimo==0)
                                bdeStkMinCalPor=bdeStkMin.multiply(bdePorVar);
                            else if(intOpcRedondeoMinimo==1)
                                bdeStkMinCalPor=objUti.redondearBigDecimal(bdeStkMin.multiply(bdePorVar), 0);
                            else if(intOpcRedondeoMinimo==2)
                                bdeStkMinCalPor=new BigDecimal( "" + Math.floor(Double.parseDouble(""+ bdeStkMin.multiply(bdePorVar))));


                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));
                            if(intOpcRedondeoStock==1)
                                bdeStkRep=objUti.redondearBigDecimal(bdeStkRep, 0);//redondear
                            else if(intOpcRedondeoStock==2)
                                bdeStkRep=new BigDecimal("" + Math.floor(Double.parseDouble(""+bdeStkRep))); //redondeando a menos
                            else if(intOpcRedondeoStock==3)
                                bdeStkRep=new BigDecimal("" + Math.ceil(Double.parseDouble(""+bdeStkRep))); //redondeando a mas


                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                            else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.compareTo(new BigDecimal("0"))<0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }

                    }
                }
                //lblPorVar.setText("" + bdePorVar);
                lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                bdePorVarGrl=bdePorVar;

            if(intNumRegChk>13){
                objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + ".Ninguno se procesó porque excede el número de items de una guía</HTML>");
                bdePorVarConVal=bdePorVar;
                intNumItemPueRep=intNumRegChk;
            }
            else if(intNumRegChk<=0){
                objTooBar.setMenSis("<HTML>El número de items último que se podian procesar es: " + intNumItemPueRep + " con un porcentaje de variación de: " + (bdePorVarConVal).multiply(new BigDecimal("100")) + " % </HTML>");
            }
            else{
                objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + " con un porcentaje de variación de: " + (bdePorVar).multiply(new BigDecimal("100")) + " % </HTML>");
            }

                
            if(bdePorVar.compareTo(new BigDecimal("0.01"))>0){//disminuye de 1 en 1
                bdePorVar=bdePorVar.subtract(new BigDecimal("0.01"));
            }
            else if(bdePorVar.compareTo(new BigDecimal("0.001"))<0){
                bdePorVar=new BigDecimal("0");
                lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                bdePorVarGrl=bdePorVar;
                break;
            }
            else if(bdePorVar.compareTo(new BigDecimal("0.01"))<=0){//cuando el % llega al 1%
                bdePorVar=bdePorVar.subtract(new BigDecimal("0.001"));
            }

            }while(  (intNumRegChk>13) || (bdePesDisSel.compareTo(bdePesDisUsr)>0) );
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdePesDisSel,objParSis.getDecimalesMostrar()));

            BigDecimal bdeCanRepNeg=new BigDecimal("0");


            for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                bdeCanRepNeg=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                if(bdeCanRepNeg.compareTo(new BigDecimal("0"))<0)
                    objTblModRep.setValueAt((bdeCanRepNeg.multiply(new BigDecimal("-1"))), i, INT_TBL_DAT_CAN_REP_SEL);
            }

            if(intNumRegChk==0){
                for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                    objTblModRep.setValueAt(new BigDecimal("0"), i, INT_TBL_DAT_CAN_REP_SEL);
                }
            }



            if( (intOpcRedondeoStock<3) &&((intNumRegChk>13) || (intNumRegChk<=0))  )
                seleccionarItmRepVar();
            else if( (intNumRegChk<=0) && (intOpcRedondeoMinimo<1) )
            {
                intOpcRedondeoStock=0;
                intOpcRedondeoMinimo++;
                seleccionarItmRepVar();
            }

            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean seleccionarItmRepItmSel(int intNumRegSeleccionados)
    {
        boolean blnRes=true;
        BigDecimal bdePesDisUsr=new BigDecimal(txtPesDis.getText());
        BigDecimal bdePesDisSel=new BigDecimal("0");
        BigDecimal bdePorVar=new BigDecimal("1");
        int intNumRegChk=0;
        BigDecimal bdeStkMin=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkAct=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkRep=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkMinCalPor=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePesRegSel=new BigDecimal("0");
        BigDecimal bdeStkDisGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePorVarConVal=new BigDecimal("0");
        int intNumItemPueRep=0;
        String strCodAltItm="", strSubCodAltItm="", strCodAltDos="";//se lo almacena para luego compararlo y saber si esta entre iniciales 124,125,870,871,873,874 TUBERIAS y asi no redondearlos
        try
        {
            do
            {
                intNumRegChk=0;
                bdePesRegSel=new BigDecimal("0");
                bdePesDisSel=new BigDecimal("0");
                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    objTblModRep.setChecked(false, i, INT_TBL_DAT_CHK);
                    objTblModRep.setValueAt(null, i, INT_TBL_DAT_CAN_REP_SEL);
                    txtPesTot.setText("");
                }
                objTblModRep.initRowsState();
                if(bdePorVar.compareTo(new BigDecimal("0"))<=0) //disminuye de 1 en 1
                {
                    lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                    bdePorVarGrl=bdePorVar;
                    break;
                }
                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    bdeStkMin=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN).toString());
                    bdeStkAct=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT).toString());
                    bdeStkDisGrp=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());

                    strCodAltItm=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                    strSubCodAltItm=strCodAltItm.substring(0, 4);

                    strCodAltDos=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS).toString(); 

                    if(bdeStkAct.compareTo(new BigDecimal("0"))<0){
                    }
                    else{
                        if( (strSubCodAltItm.trim().equals("124-")) || (strSubCodAltItm.trim().equals("125-")) || (strSubCodAltItm.trim().equals("870-")) || (strSubCodAltItm.trim().equals("871-")) || (strSubCodAltItm.trim().equals("873-")) || (strSubCodAltItm.trim().equals("874-"))        )
                        {
                            bdeStkMinCalPor=new BigDecimal( "" + bdeStkMin.multiply(bdePorVar));

                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));
                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);
                            // if(txtCodBodOri.getText().equals("1")){
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){  
                                if((bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                              else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.abs().compareTo(new BigDecimal("0"))>0) && (bdeStkRep.abs().compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }
                        else{
                            if(intOpcRedondeoMinimo==2){
                                bdeStkMinCalPor=new BigDecimal( "" + Math.floor(Double.parseDouble(""+ bdeStkMin.multiply(bdePorVar))));
                            }
                            else
                                bdeStkMinCalPor=new BigDecimal( "" + bdeStkMin.multiply(bdePorVar));

                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));

                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);

                           // if(txtCodBodOri.getText().equals("1")){
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){    
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                            else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.abs().compareTo(new BigDecimal("0"))>0) && (bdeStkRep.abs().compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }
                    }
                    if(intNumRegChk==intNumRegSeleccionados){
                        break;
                    }
                    bdeStkRep=new BigDecimal("0");
                    bdeStkMin=new BigDecimal("0");
                    bdeStkAct=new BigDecimal("0");
                    bdeStkDisGrp=new BigDecimal("0");
                    strCodAltItm="";
                    strSubCodAltItm="";
                }
                lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                bdePorVarGrl=bdePorVar;
                //System.out.println("% VARIACION: " + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                if(intNumRegChk>13){
                    objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + ".Ninguno se procesó porque excede el número de items de una guía</HTML>");
                    bdePorVarConVal=bdePorVar;
                    intNumItemPueRep=intNumRegChk;
                }
                else if(intNumRegChk<=0){
                    objTooBar.setMenSis("<HTML>El número de items último que se podian procesar es: " + intNumItemPueRep + " con un porcentaje de variación de: " + (bdePorVarConVal).multiply(new BigDecimal("100")) + " % </HTML>");
                }
                else{
                    objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + " con un porcentaje de variación de: " + (bdePorVar).multiply(new BigDecimal("100")) + " % </HTML>");
                }


                if(bdePorVar.compareTo(new BigDecimal("0.01"))>0){//disminuye de 1 en 1
                    bdePorVar=bdePorVar.subtract(new BigDecimal("0.01"));
                }
                else if(bdePorVar.compareTo(new BigDecimal("0.001"))<0){
                    bdePorVar=new BigDecimal("0");
                    lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                    bdePorVarGrl=bdePorVar;
                    break;
                }
                else if(bdePorVar.compareTo(new BigDecimal("0.01"))<=0){//cuando el % llega al 1%
                    bdePorVar=bdePorVar.subtract(new BigDecimal("0.001"));
                }


            }while(  (bdePesDisSel.abs().compareTo(bdePesDisUsr)>0) );
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdePesDisSel.abs(),objParSis.getDecimalesMostrar()));

            BigDecimal bdeCanRepNeg=new BigDecimal("0");


            for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                bdeCanRepNeg=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                if(bdeCanRepNeg.compareTo(new BigDecimal("0"))<0)
                    objTblModRep.setValueAt((bdeCanRepNeg.multiply(new BigDecimal("-1"))), i, INT_TBL_DAT_CAN_REP_SEL);
            }

            if(intNumRegChk==0){
                for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                    objTblModRep.setValueAt(new BigDecimal("0"), i, INT_TBL_DAT_CAN_REP_SEL);
                }
            }

            if( (  intNumRegChk==0 ) && (intNumRegSeleccionados>0) ){
                intOpcRedondeoMinimo++;
                intNumRegSeleccionados--;
                seleccionarItmRepItmSel(intNumRegSeleccionados);
            }
//            objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + " con un porcentaje de variación de: " + (bdePorVar).multiply(new BigDecimal("100")) + " % </HTML>");

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
     
    /**
     * Obtiene el tipo de permiso para modificar fecha del documento.
     * @return intTipModFec
     */
    private int canChangeDate()
    {
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModFec=0;
        try
        {
            //System.out.println("canChangeDate");
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null)
            {
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1)
                {
                    intTipModFec=4;
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + intCodEmpGrp + "";
                    strSQL+=" AND co_loc=" + intCodLocGrp + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next())
                    {
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
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
    }


   /**
    * Valida si existen seleccionados items a reponer.
    * @return true: Si existen items a reponer.
    * <BR> false: Caso contrario.
    */
    private boolean isItmRep()
    {
       boolean blnRes=false;
       int intExiDocAut=0;
       
       if(quitarItmCanRepCer())
       {
            for(int i=0;i<objTblModRep.getRowCountTrue(); i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                   intExiDocAut++;
                }
            }
       }
       if(intExiDocAut>0)
           blnRes=true;
   
       return blnRes;
    }

    /**
     * Función que elimina items con cantidad a reponer 0.
     * @return 
     */
    private boolean quitarItmCanRepCer()
    {
        boolean blnRes=true;
        BigDecimal bdeValRepCer=new BigDecimal("0");
        try
        {
            //System.out.println("quitarItmCanRepCer");
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                    bdeValRepCer=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                    if(bdeValRepCer.compareTo(new BigDecimal("0"))==0)
                    {
                        objTblModRep.setChecked(false, i, INT_TBL_DAT_CHK);
                    }
                }
            }
        }
        catch(Exception e)
        {
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
        Connection conLoc;
        objZafCom91 = new ZafCom91(objParSis);
        objGenOD = new GenOD.ZafGenOdPryTra();
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null)
            {
                conLoc.setAutoCommit(false);
                if(insertar_cabRepInvBod(conLoc)){
                    if(insertar_detRepInvBod(conLoc)){
                        if(actualizar_cabTipDoc(conLoc))  {
                            if(insertar_segMovInv(conLoc)){  
                                if(generarArrSolTrs()) {
                                    if(objZafCom91.generaMovimientoTransferenciaReposicion(conLoc, arlDatConSolTraInv, getRepInvBod(), strPesTotKgrSolTra, Integer.parseInt(txtCodBodOri.getText()) , Integer.parseInt(txtCodBodDes.getText()), INT_COD_CFG_TIP_AUT_SOL_REP_INV))
                                    {
                                        if(insertar_traEmiRepInvBod(conLoc))
                                        {
                                            conLoc.commit();
                                            blnRes=true;                                            
                                        }
                                        else{conLoc.rollback();}
                                    }else{conLoc.rollback();}
                                }else{conLoc.rollback();}
                          } else{conLoc.rollback();}
                        }else{conLoc.rollback();}
                    }else{conLoc.rollback();}
                }else{conLoc.rollback();}

                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
        
    /**
     * Función que envía a imprimir orden de despacho.
     * @return 
     */
    private boolean imprimirOrdDes(){
        boolean blnRes=true;
        Connection con;
        ArrayList arlDatSol;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                arlDatSol=objZafCom91.getSolTraInv();
                objZafCom91=null;  //Este objeto se abre en insertarReg().
                //System.out.println("imprimirOrdDes: "+arlDatSol.toString());
                objCfgSer = new ZafCfgSer(objParSis);
                objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(),objCfgSer.INT_TBL_COD_SER_IMP_OD_GR);  
                strIpImp=objCfgSer.getIpHost();
                objGenOD.imprimirOdxEgr(con,arlDatSol,strIpImp); 
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

    private boolean insertar_cabRepInvBod(Connection conLocal)
    {
        boolean blnRes=true;
        int intUltReg;
        try
        {
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabRepInvBod AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
                strSQL+=" AND a1.co_loc=" + intCodLocGrp;
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
                strSQL+="INSERT INTO tbm_cabRepInvBod(";
                strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bodorg,";
                strSQL+="             co_boddes, nd_pesdiskgr, nd_pestotkgr, st_imp, tx_obs1, tx_obs2, ";
                strSQL+="             st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep)";
                strSQL+=" VALUES (";
                strSQL+=" " + intCodEmpGrp + "";//co_emp
                strSQL+=", " + intCodLocGrp + "";//co_loc
                strSQL+=", " + txtCodTipDoc.getText() + "";//co_tipdoc
                strSQL+=", " + txtCodDoc.getText() + "";//co_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + "";//ne_numdoc
                strSQL+=", " + txtCodBodOri.getText() + "";//co_bodorg
                strSQL+=", " + txtCodBodDes.getText() + "";//co_boddes
                strSQL+=", " + txtPesDis.getText() + "";//nd_pesdiskgr
                //strSQL+="," + txtPesTot.getText() + "";//nd_pestotkgr
                strSQL+=", " + objUti.redondearBigDecimal(txtPesTot.getText(),objParSis.getDecimalesBaseDatos()) + "";//nd_pestotkgr
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultmod
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+=", 'I'";//st_regrep
                strSQL+=");";
            
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean insertar_detRepInvBod(Connection conLocal)
    {
        boolean blnRes=true;
        int intReg=0, intCodItmMaeRep=0;
        BigDecimal bdeCanRep=new BigDecimal("0");
        BigDecimal bdePesUniItm = new BigDecimal("0");
        BigDecimal bdePesTotItm=new BigDecimal("0");
        String strInsTmp="";
        try
        {
            //System.out.println("insertar_detRepInvBod");
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
                for(int i=0; i<objTblModRep.getRowCountTrue();i++)
                {
                    if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                    {
                        bdeCanRep=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                        bdePesUniItm=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                        bdePesTotItm=bdeCanRep.multiply(bdePesUniItm);
                        
                        if(bdeCanRep.compareTo(new BigDecimal("0"))>0)
                        {
                            intReg++;
                            intCodItmMaeRep=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?0:Integer.parseInt(objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString());
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="INSERT INTO tbm_detRepInvBod(";
                            strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, nd_canrep, ";
                            strSQL+="             nd_pestotkgr, st_regrep)";
                            strSQL+=" VALUES (";
                            strSQL+="" + intCodEmpGrp + "";//co_emp
                            strSQL+="," + intCodLocGrp + "";//co_loc
                            strSQL+="," + txtCodTipDoc.getText() + "";//co_tipdoc
                            strSQL+="," + txtCodDoc.getText() + "";//co_doc
                            strSQL+=","+ intReg + "";//co_reg
                            strSQL+="," + objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";//co_itm
                            //strSQL+="," + bdeCanRepDisIns + "";//nd_canrep
                            //strSQL+="," + bdeCanRepDisIns.multiply(bdePesIns) + "";//nd_pestotkgr
                            strSQL+="," + bdeCanRep +  "";//nd_canrep
                            strSQL+="," + bdePesTotItm +  "";//nd_pestotkgr
                            strSQL+=",'I'";//st_regrep
                            strSQL+=");";
                            strInsTmp+=strSQL;
                        }
                    }
                }
                System.out.println("insertar_detRepInvBod: "+strInsTmp);
                stm.executeUpdate(strInsTmp);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Insertar seguimiento de reposición
     * @param conLocal
     * @return 
     */
    private boolean insertar_segMovInv(Connection conLocal)
    {
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=1;
        try
        {
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
                //Obtener el código del último seguimiento.
                strSQL = "";
                strSQL+= " SELECT MAX(a1.co_seg)+1 as co_seg \n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                }
                rstLoc.close();
                rstLoc=null;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabRepInvBod, co_locRelCabRepInvBod, co_tipDocRelCabRepInvBod, co_docRelCabRepInvBod) \n";
                strSQL+=" VALUES ("+intCodSeg+","+intCodRegSolTra+", "+intCodEmpGrp+", "+intCodLocGrp+" ,"+  txtCodTipDoc.getText() +","+ txtCodDoc.getText() +"); \n";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    private boolean generarArrSolTrs()
    {
        boolean blnRes=true;
        BigDecimal bdeValRepCer=new BigDecimal("0");
        arlDatConSolTraInv = new ArrayList();
        try
        {
            calcularPesTotRep(); 
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                    bdeValRepCer=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                    if(bdeValRepCer.toString().length()>0)
                    {
                        arlRegConSolTraInv = new ArrayList();
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_TRA_ITM_GRP, Integer.parseInt(tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM).toString()));
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_TRA_CAN, Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString()));
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_PES_UNI, Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_PES).toString()));
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_PES_TOT_ITM, Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_PES_TOT_KGR).toString()));
                        arlDatConSolTraInv.add(arlRegConSolTraInv);
                    }
                }
            }
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que agrega relación de las transferencias con las reposiciones.
     * Inserta relacion de REINBO con TRINRB 
     * @param conLocal
     * @return 
     */
    private boolean insertar_traEmiRepInvBod(Connection conLocal)
    {
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        String strInsTraEmiRep="";
        int intReg=1;
        try
        {
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
               
                //QUERY PARA CONSULTAR LAS TRANSFERENCIAS EXISTENTES
                strSQL =" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a2.co_Seg ";
                strSQL+=" FROM tbm_CabMovInv as  a ";
                strSQL+=" INNER JOIN tbm_cfgTipDocUtiProAut as t ON (t.co_emp=a.co_emp AND t.co_loc=a.co_loc AND t.co_tipDoc=a.co_tipDoc) ";
                strSQL+=" INNER JOIN tbm_cabSolTraInv as a1 ON (a1.co_emp=a.co_empRelCabSolTraInv AND a1.co_loc=a.co_locRelCabSolTraInv AND a1.co_tipDoc=a.co_tipDocRelCabSolTraInv AND a1.co_Doc=a.co_docRelCabSolTraInv) ";
                strSQL+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a2.co_empRelCabSolTraInv=a1.co_emp AND a2.co_locRelCabSolTraInv=a1.co_loc AND a2.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a2.co_docRelCabSolTraInv=a1.co_Doc) ";
                strSQL+=" AND t.co_cfg="+INT_COD_CFG_TIP_AUT_TRA_REP_INV;
                strSQL+=" AND a2.co_Seg in ( ";
                strSQL+="   SELECT a.co_Seg ";
                strSQL+="   FROM tbm_cabSegMovInv as a ";
                strSQL+="   INNER JOIN tbm_cabRepInvBod as a1  ";
                strSQL+="   ON (a1.co_Emp=a.co_EmpRelCabRepInvBod AND a1.co_loc=a.co_LocRelCabRepInvBod AND a1.co_tipDoc=a.co_TipDocRelCabRepInvBod AND a1.co_Doc=a.co_DocRelCabRepInvBod ) ";
                strSQL+="   WHERE a1.co_Emp="+intCodEmpGrp;
                strSQL+="   AND a1.co_loc="+intCodLocGrp;
                strSQL+="   AND a1.co_tipDoc="+txtCodTipDoc.getText();
                strSQL+="   AND a1.co_doc="+txtCodDoc.getText();
                strSQL+="   GROUP BY a.co_Seg ";
                strSQL+="   )";
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a2.co_Seg ";

                //System.out.println("ConsultarTransferenciasREINBO: "+strSQL);
                rstLoc= stm.executeQuery(strSQL);
                while(rstLoc.next())
                {
                    strSQL="";
                    strSQL+="INSERT INTO tbm_traEmiRepInvBod(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel, ";
                    strSQL+="             co_tipdocrel, co_docrel, st_regrep)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + intCodEmpGrp + "";//co_emp
                    strSQL+=" ," + intCodLocGrp + "";//co_loc
                    strSQL+=" ," + txtCodTipDoc.getText() + "";//co_tipdoc
                    strSQL+=" ," + txtCodDoc.getText() + "";//co_doc
                    strSQL+=" ," + intReg + "";//co_reg
                    strSQL+=" ," + rstLoc.getString("co_emp") + "";//co_emprel
                    strSQL+=" ," + rstLoc.getString("co_loc") + "";//co_locrel
                    strSQL+=" ," + rstLoc.getString("co_tipDoc") + "";//co_tipdocrel
                    strSQL+=" ," + rstLoc.getString("co_doc") + "";//co_docrel
                    strSQL+=" ,'I'";//st_regrep
                    strSQL+=" );";
                    strInsTraEmiRep+=strSQL;
                    intReg++;
                }
                rstLoc.close();
                rstLoc=null;
                
                System.out.println("insertar_traEmiRepInvBod: "+strInsTraEmiRep);
                stm.executeUpdate(strInsTraEmiRep);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
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
                //Validar que sálo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabrepinvbod AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLocGrp + "";
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu() + "";
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabrepinvbod AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLocGrp + "";
                }

                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc=" + strAux + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc=" + strAux + "";
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc=" + strAux + "";
                strAux=txtCodBodOri.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_bodOrg=" + strAux + "";
                strAux=txtCodBodDes.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_bodDes=" + strAux + "";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                arlDatConCnf = new ArrayList();
                while(rst.next()){
                    arlRegConCnf = new ArrayList();
                    arlRegConCnf.add(INT_ARL_CON_COD_EMP,rst.getInt("co_emp"));
                    arlRegConCnf.add(INT_ARL_CON_COD_LOC,rst.getInt("co_loc"));
                    arlRegConCnf.add(INT_ARL_CON_COD_TIPDOC,rst.getInt("co_tipDoc"));
                    arlRegConCnf.add(INT_ARL_CON_COD_DOC,rst.getInt("co_doc"));
                    arlDatConCnf.add(arlRegConCnf);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConCnf.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConCnf.size()) + " registros");
                    intIndiceConCnf=arlDatConCnf.size()-1;
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
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    if (cargarTrsReg()){
                        blnRes=true;
                    }
                }
            }
            blnHayCam=false;
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
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar";
                strSQL+=" , a1.co_doc, a1.fe_doc, a1.co_bodOrg, a1.co_bodDes, a1.ne_numDoc, a1.nd_pesDisKgr, a1.nd_pesTotKgr";
                strSQL+=" , a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+=" FROM (tbm_cabRepInvBod AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 		INNER JOIN tbm_bod AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND (a1.co_bodOrg=a3.co_bod OR a1.co_bodDes=a3.co_bod)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar";
                strSQL+=" , a1.co_doc, a1.fe_doc, a1.co_bodOrg, a1.co_bodDes, a1.ne_numDoc, a1.nd_pesDisKgr";
                strSQL+=" , a1.nd_pesTotKgr , a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";

                strSQL="";
                strSQL+="SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.tx_desLar";
                strSQL+=" 	, b1.co_doc, b1.fe_doc, b1.co_bodOrg, b2.tx_nomBodOrg, b1.co_bodDes, b3.tx_nomBodDes";
                strSQL+=" 	, b1.ne_numDoc, b1.nd_pesDisKgr, b1.nd_pesTotKgr";
                strSQL+=" 	, b1.tx_obs1, b1.tx_obs2, b1.st_reg, b1.st_imp";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar";
                strSQL+=" 	, a1.co_doc, a1.fe_doc, a1.co_bodOrg, a1.co_bodDes, a1.ne_numDoc, a1.nd_pesDisKgr, a1.nd_pesTotKgr";
                strSQL+=" 	, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+=" 	FROM tbm_cabRepInvBod AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";

                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar";
                strSQL+=" 	, a1.co_doc, a1.fe_doc, a1.co_bodOrg, a1.co_bodDes, a1.ne_numDoc, a1.nd_pesDisKgr";
                strSQL+=" 	, a1.nd_pesTotKgr , a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp) AS b1";
                strSQL+=" 	INNER JOIN(";
                strSQL+=" 		SELECT co_emp, co_bod, tx_nom AS tx_nomBodOrg FROM tbm_bod AS a3) AS b2";
                strSQL+=" 	ON b1.co_emp=b2.co_emp AND b1.co_bodOrg=b2.co_bod";
                strSQL+=" 	INNER JOIN(";
                strSQL+=" 		SELECT co_emp, co_bod, tx_nom AS tx_nomBodDes FROM tbm_bod AS a4) AS b3";
                strSQL+=" 	ON b1.co_emp=b3.co_emp AND b1.co_bodDes=b3.co_bod";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_bodOrg");
                    txtCodBodOri.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_nomBodOrg");
                    txtNomBodOri.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomBodDes");
                    txtNomBodDes.setText((strAux==null)?"":strAux);

                    
                    strAux=rst.getString("co_bodDes");
                    txtCodBodDes.setText((strAux==null)?"":strAux);

                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));

                    strEstImpDoc=rst.getString("st_imp");

                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);

                    txtPesDis.setText(""+objUti.redondearBigDecimal(rst.getObject("nd_pesDisKgr")==null?"0":rst.getString("nd_pesDisKgr"),objParSis.getDecimalesMostrar()));
                    
                    txtPesTot.setText(""+objUti.redondearBigDecimal(rst.getObject("nd_pesTotKgr")==null?"0":rst.getString("nd_pesTotKgr"),objParSis.getDecimalesMostrar()));

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
            rst=null;
            stm.close();
            stm=null;
            con.close();
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceConCnf+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConCnf.size()) );
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
        boolean blnRes=true;
        try
        {
            objTblModRep.removeAllRows();
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc , a2.co_itm, a3.tx_codAlt, a3.tx_codAlt2 ";
                strSQL+=" , a3.tx_nomitm, a5.tx_desCor , a6.tx_ubi, a2.nd_canRep, a2.nd_pesTotKgr, a2.co_reg ";
                strSQL+=" FROM tbm_cabRepInvBod AS a1 ";
                strSQL+=" INNER JOIN tbm_detRepInvBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc  ";
                strSQL+=" INNER JOIN tbm_inv AS a3 ";
                strSQL+=" INNER JOIN tbm_equInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)  ";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a3.co_uni=a5.co_reg)  "; 
                strSQL+=" LEFT OUTER JOIN  ";  
                strSQL+=" (  ";
	        strSQL+="   SELECT DISTINCT b1.co_itmMae, b2.tx_ubi  ";
                strSQL+="   FROM tbm_equInv as b1  ";
                strSQL+="   INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)  ";
                strSQL+="   INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)  ";
                strSQL+="   WHERE b3.co_empgrp=" +objParSis.getCodigoEmpresaGrupo()+" AND b3.co_bodgrp="+txtCodBodOri.getText();
                strSQL+=" ) as a6 ON (a4.co_itmMae=a6.co_itmMae)  ";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm ";
                strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg  ";
                System.out.println("cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatTrs.clear();
                //Obtener los registros.
                //objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecRegTrs=new Vector();
                    vecRegTrs.add(INT_TBL_DAT_LIN,"");
                    vecRegTrs.add(INT_TBL_DAT_COD_EMP,         "" + rst.getString("co_emp"));
                    vecRegTrs.add(INT_TBL_DAT_CHK,             null);
                    vecRegTrs.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                    vecRegTrs.add(INT_TBL_DAT_COD_ITM_MAE,     null);
                    vecRegTrs.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                    vecRegTrs.add(INT_TBL_DAT_COD_ALT_DOS,     "" + rst.getString("tx_codAlt2")); 
                    vecRegTrs.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomitm"));
                    vecRegTrs.add(INT_TBL_DAT_DES_COR_UNI_MED, "" + rst.getString("tx_desCor"));
                    vecRegTrs.add(INT_TBL_DAT_UBI, "" + rst.getString("tx_ubi")==null?"":rst.getString("tx_ubi"));
                    vecRegTrs.add(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS,     null);
                    vecRegTrs.add(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP,     null);
                    vecRegTrs.add(INT_TBL_DAT_CAN_DIS,     null); 
                    vecRegTrs.add(INT_TBL_DAT_CAN_REP_CAL,     null);
                    vecRegTrs.add(INT_TBL_DAT_CAN_REP_SEL,         "" + rst.getString("nd_canRep"));
                    vecRegTrs.add(INT_TBL_DAT_CAN_REP_DIS,     null);
                    vecRegTrs.add(INT_TBL_DAT_PES,            null);
                    vecRegTrs.add(INT_TBL_DAT_PES_TOT_KGR,      "" + rst.getString("nd_pesTotKgr"));
                    vecRegTrs.add(INT_TBL_DAT_STK_MIN,         null);
                    vecRegTrs.add(INT_TBL_DAT_STK_ACT,         null);
                    vecRegTrs.add(INT_TBL_DAT_MIN,     null);
                    vecRegTrs.add(INT_TBL_DAT_MAX,     null);
                    vecRegTrs.add(INT_TBL_DAT_UNI_MED,     null); 
                  
                if (strAux!=null)
                        vecRegTrs.setElementAt(new Boolean(true),INT_TBL_DAT_CHK);
                    vecDatTrs.add(vecRegTrs);
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                
                //Asignar vectores al modelo.
                objTblModRep.setData(vecDatTrs);
                tblDat.setModel(objTblModRep);
                vecDatTrs.clear();
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
     * Esta función permite cargar el detalle de las transferencias que se realizaron en cada empresa a través del proceso reposición.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTrsReg()
    {
        boolean blnRes=true;
        try
        {
            objTblModRepTrs.removeAllRows();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.ne_numDoc, a3.fe_doc, a4.tx_desCor";
                strSQL+=" FROM tbm_cabRepInvBod AS a1 ";
                strSQL+=" INNER JOIN tbm_traemirepinvbod AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a3";
                strSQL+=" ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4";
                strSQL+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc ";
                strSQL+=" AND a1.co_emp=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_TIPDOC) + "";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatTrs.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecRegTrs=new Vector();
                    vecRegTrs.add(INT_TBL_DAT_TRS_LIN,              "");
                    vecRegTrs.add(INT_TBL_DAT_TRS_CHK,              null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_EMP,          "" + rst.getString("co_emp"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_LOC,          "" + rst.getString("co_loc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_TIP_DOC,      "" + rst.getString("co_tipDoc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_DES_COR_TIP_DOC,  "" + rst.getString("tx_desCor"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_DOC,          "" + rst.getString("co_doc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_NUM_DOC,          "" + rst.getString("ne_numDoc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_FEC_DOC,          "" + rst.getString("fe_doc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_BUT,              null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_BOD_ORI,      null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_ITM,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_ITM_MAE,      null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_ALT_ITM,      null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_NOM_ITM,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_UNI_MED,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_CAN_REP,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_STK_ACT,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_STK_MIN,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_STK_DIS,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COS_UNI,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_PRE_UNI,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COS_TOT,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_PES_GRP,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_BOD_DES,      null);
                    vecDatTrs.add(vecRegTrs);

                    if(blnChnEstImp)
                    {
                        vecDocSinPnd.add(rst.getInt("co_emp")+"-"+ rst.getInt("co_loc")+"-"+rst.getInt("co_tipDoc")+"-"+rst.getInt("co_doc"));
                    }
                }

                rst.close();
                stm.close();
                con.close();
                con=null;
                rst=null;
                stm=null;
                //Asignar vectores al modelo.
                objTblModRepTrs.setData(vecDatTrs);
                tblTrs.setModel(objTblModRepTrs);
                vecDatTrs.clear();
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




    private boolean abreTransferencia(int fila)
    {
        boolean blnRes=true;
        try
        {
            invocarClase("Compras.ZafCom20.ZafCom20");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean invocarClase(String clase)
    {
        int intFilSel;
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objParCla[]=new Class[1];
            objParCla[0]=objParSis.getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objParCla);
            intFilSel=tblTrs.getSelectedRow();
            //Inicializar el constructor que se obtuvo.
            Object objValCla[]=new Object[1];
            objValCla[0]=objParSis;
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objValCla);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.show();
            //Obtener el método que se desea invocar.
            Class objParMet[]=new Class[4];
            objParMet[0]=new Integer(0).getClass();
            objParMet[1]=new Integer(0).getClass();
            objParMet[2]=new Integer(0).getClass();
            objParMet[3]=new Integer(0).getClass();
            java.lang.reflect.Method objMet=ifrVen.getClass().getMethod("cargarDocumento", objParMet);
            //Ejecutar el método que se obtuvo.
            Object objValMet[]=new Object[4];
            objValMet[0]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_EMP).toString());
            objValMet[1]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_LOC).toString());
            objValMet[2]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_TIP_DOC).toString());
            objValMet[3]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_DOC).toString());
            objMet.invoke(ifrVen, objValMet);
        }
        catch (ClassNotFoundException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean inactivarCampos()
    {
        boolean blnRes=true;
        try{
            txtCodTipDoc.setEditable(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodBodOri.setEditable(false);
            txtNomBodOri.setEditable(false);
            butBodOri.setEnabled(false);
            txtCodBodDes.setEditable(false);
            txtNomBodDes.setEditable(false);
            butBodDes.setEnabled(false);
            txtCodDoc.setEditable(false);
            butCarItm.setEnabled(false);
            dtpFecDoc.setEnabled(false);
            txtNumDoc.setEditable(false);
            txtPesDis.setEditable(false);
            txtPesTot.setEditable(false);
            ///////////
            optPorVar.setEnabled(false);
            optItmApl.setEnabled(false);
            ///////// José Mario Marín M. 18/07/2014
            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean isBodOri()
    {
        if (txtCodBodOri.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">bodega origen</FONT> es obligatorio.<BR>Escriba o seleccione la bodega origen y vuelva a intentarlo.</HTML>");
            txtNomBodOri.requestFocus();
            return false;
        }
        return true;
    }


    BigDecimal bdeCanRepSel = new BigDecimal("0");
    BigDecimal bdePesUniItm = new BigDecimal("0");
    BigDecimal bdePesTotItm = new BigDecimal("0");
    BigDecimal bdePesTotRep = new BigDecimal("0");
    private boolean calcularPesTotRep()
    {
        boolean blnRes=false;
        try
        {
            bdePesTotRep=new BigDecimal("0");
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                   bdeCanRepSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                   bdePesUniItm=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                   bdePesTotItm=bdeCanRepSel.multiply(bdePesUniItm);
                   objTblModRep.setValueAt(bdePesTotItm, i, INT_TBL_DAT_PES_TOT_KGR);
                   bdePesTotRep=bdePesTotRep.add(bdePesTotItm);
                   //System.out.println("bdePesTotRep: "+bdePesTotRep+ " i = "+i);
                   blnRes=true;
                }
            }
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdePesTotRep,objParSis.getDecimalesMostrar()));
            strPesTotKgrSolTra = txtPesTot.getText();
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean getSumaTotalPeso(int fila)
    {
        boolean blnRes=false;
        BigDecimal bdePesTotSel=new BigDecimal("0");
        BigDecimal bdeCanRepSel=new BigDecimal("0");
        BigDecimal bdePesRepSel=new BigDecimal("0");
        BigDecimal bdePesRepDis=new BigDecimal("0");
        BigDecimal bdeSumPesTotPesSel=new BigDecimal("0");
        try{
            bdeCanRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
            bdePesRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_PES).toString());
            bdePesRepDis=new BigDecimal(txtPesDis.getText()==null?"0":(txtPesDis.getText().equals("")?"0":txtPesDis.getText())   );//txt usuario
            bdePesTotSel=new BigDecimal(txtPesTot.getText()==null?"0":(txtPesTot.getText().equals("")?"0":txtPesTot.getText())   );//txt sistema
            bdeSumPesTotPesSel=bdePesTotSel.add((bdeCanRepSel.multiply(bdePesRepSel)));//peso total txt sumado al registro seleccionado
            if(bdeSumPesTotPesSel.compareTo(bdePesRepDis)<=0){//si el peso total sumado al nuevo reg seleccionado es <= al ingresado por usuario
                //txtPesTot.setText("" + bdeSumPesTotPesSel);
                txtPesTot.setText("" + objUti.redondearBigDecimal(bdeSumPesTotPesSel,objParSis.getDecimalesMostrar()));

                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean getRestaTotalPeso(int fila)
    {
        boolean blnRes=false;
        BigDecimal bdeCanRepSel=new BigDecimal("0");//cantidad a reponer seleccionada
        BigDecimal bdePesRepSel=new BigDecimal("0");//peso a reponer seleccionado
        BigDecimal bdePesTotSel=new BigDecimal("0");//peso total del campo txt
        BigDecimal bdeSumPesTotPesSel=new BigDecimal("0");//total de peso (cantidad * peso) seleccionado
        try
        {
            bdeCanRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
            bdePesRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_PES).toString());
            bdePesTotSel=new BigDecimal(txtPesTot.getText()==null?"0":(txtPesTot.getText().equals("")?"0":txtPesTot.getText())   );//txt sistema
            bdeSumPesTotPesSel=bdePesTotSel.subtract((bdeCanRepSel.multiply(bdePesRepSel)));//peso total txt sumado al registro seleccionado
                //txtPesTot.setText("" + bdeSumPesTotPesSel);
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdeSumPesTotPesSel,objParSis.getDecimalesMostrar()));
                blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean validaCantidad(int fila, BigDecimal bdeStkDis, BigDecimal bdeValCanRepBefEdi, BigDecimal bdeValCanRepAftEdi)
    {
        boolean blnRes=true;
        BigDecimal bdeStkFilSel=new BigDecimal("0");
        
        BigDecimal bdeCanRepMin=new BigDecimal("0");//cantidad a reponer menos 6 mts me da el rango inicial
        String strUniMed="";
        BigDecimal bdeStkMin=new BigDecimal("0");
        BigDecimal bdeStkAct=new BigDecimal("0");
        BigDecimal bdePorVarVal=new BigDecimal("0");
        bdePorVarGrl=new BigDecimal("1");
                    
        BigDecimal bdeA=new BigDecimal("0");//minimo * porcentaje variacion
        BigDecimal bdeB=new BigDecimal("0");//stockActual - A
        Double disponibleCalculada;
        Double dblStk=bdeStkDis.doubleValue();
        disponibleCalculada=objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL));///  José Marín M. 16/Jul/2014
        strUniMed=objTblModRep.getValueAt(fila, INT_TBL_DAT_DES_COR_UNI_MED)==null?"":objTblModRep.getValueAt(fila, INT_TBL_DAT_DES_COR_UNI_MED).toString();  //José Marín M.
        try
        {
            bdeStkFilSel=objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?new BigDecimal("0"):new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
            if(bdeValCanRepAftEdi.compareTo(new BigDecimal("0"))==0){
                
            }
            else{
                //System.out.println("validaCantidad");
                bdeStkMin=objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_MIN)==null?new BigDecimal("0"):(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_MIN).toString().equals("")?new BigDecimal("0"):new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_MIN).toString()));
                bdeStkAct=objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_ACT)==null?new BigDecimal("0"):(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_ACT).toString().equals("")?new BigDecimal("0"):new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_ACT).toString()));
                bdePorVarVal=bdePorVarGrl;
                bdeA=bdeStkMin.multiply(bdePorVarVal);
                bdeB=bdeStkAct.subtract(bdeA);
                bdeB=bdeB.abs();
                bdeCanRepMin=bdeB.subtract(new BigDecimal("6"));
                if(strUniMed.equals("MTS")){
                    //System.out.println("MTS....");
                    if((bdeValCanRepAftEdi.compareTo(bdeCanRepMin)>=0) && (bdeValCanRepAftEdi.compareTo(bdeB)<=0)){
                    }
                    else{
//                        mostrarMsgInf("<HTML>La cantidad ingresada es mayor a la permitida.<BR>La cantidad permitida debe estar en un rango entre <FONT COLOR=\"blue\">" + objUti.redondearBigDecimal(bdeCanRepMin.abs(), objParSis.getDecimalesMostrar()) +" y " + objUti.redondearBigDecimal(bdeB, objParSis.getDecimalesMostrar()) + "</FONT><BR>Ingrese una cantidad entre ese rango y vuelva a intentarlo.</HTML>");
//                        objTblModRep.setValueAt(bdeValCanRepBefEdi, fila, INT_TBL_DAT_CAN_REP_SEL);/*Ingrid Lino: Solicitado por werner campoverde 19/Agosto/2016*/
                    }
                }
                if(bdeValCanRepAftEdi.compareTo(bdeStkDis)>0){
                    if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD))
                        mostrarMsgInf("<HTML>La cantidad a reponer ingresada es mayor al stock disponible de grupo.<BR>Recuerde que el máximo valor a colocar es el stock disponible de grupo: " + objUti.redondear(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString(), objParSis.getDecimalesMostrar()) + "</HTML>");
                    else
                        mostrarMsgInf("<HTML>La cantidad a reponer ingresada es mayor al stock disponible de grupo</HTML>");
                    if(dblStk>=disponibleCalculada){
                        objTblModRep.setValueAt(disponibleCalculada, fila, INT_TBL_DAT_CAN_REP_SEL);//se le da lo maximo calculado si es posible tener en stock 
                        objTblModRep.setValueAt(false, fila, INT_TBL_DAT_CHK);
                    }
                    else{
                        objTblModRep.setValueAt(dblStk, fila, INT_TBL_DAT_CAN_REP_SEL);//Se le da lo maximo en stock
                        objTblModRep.setValueAt(false, fila, INT_TBL_DAT_CHK);    
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
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_cabTipDoc(Connection conLocal)
    {
        boolean blnRes=true;
        try
        {
            if (conLocal!=null)
            {
                stm=conLocal.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + intCodEmpGrp;
                strSQL+=" AND co_loc=" + intCodLocGrp;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
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
    
    
    public ArrayList getRepInvBod()
    {
        ArrayList arlReg, arlDat;
        arlReg = new ArrayList();
        arlDat = new ArrayList();
        arlReg.add(intCodEmpGrp);
        arlReg.add(intCodLocGrp);
        arlReg.add(txtCodTipDoc.getText());
        arlReg.add(txtCodDoc.getText());
        arlDat.add(arlReg);
        return arlDat;
    }

}
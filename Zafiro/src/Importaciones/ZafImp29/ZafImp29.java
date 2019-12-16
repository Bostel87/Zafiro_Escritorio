/*
 * ZafImp29.java
 *
 * Created on 20 de septiembre de 2017, 13:28 PM
 */
package Importaciones.ZafImp29;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafAjuInv;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafImp.ZafSegAjuInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author Rosa Zambrano
 */
public class ZafImp29 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    private static final int INT_TBL_DAT_LIN=0;                         //Línea
    private static final int INT_TBL_DAT_CHK = 1;                       //Check
    private static final int INT_TBL_DAT_COD_ITM_GRP=2;                 //Código del item del grupo
    private static final int INT_TBL_DAT_COD_ITM=3;                     //Código del item. de la empresa
    private static final int INT_TBL_DAT_COD_ITM_MAE=4;                 //Muestra ventana con información de conteos realizados. 
    private static final int INT_TBL_DAT_COD_ALT_ITM=5;                 //Código alterno del item.
    private static final int INT_TBL_DAT_COD_ALT_DOS=6;                    //Código alterno dos del item.
    private static final int INT_TBL_DAT_NOM_ITM=7;                     //Nombre del item.
    private static final int INT_TBL_DAT_UNI_MED=8;                     //Unidad de medida.
    private static final int INT_TBL_DAT_COD_IMP=9;                     //Codigo del importador.
    private static final int INT_TBL_DAT_NOM_IMP=10;                    //Nombre del importador.
    private static final int INT_TBL_DAT_COD_EMP_ING_IMP=11;            //Muestra ventana con información de conteos realizados.
    private static final int INT_TBL_DAT_COD_LOC_ING_IMP=12;            //Muestra ventana con información de conteos realizados.
    private static final int INT_TBL_DAT_COD_TIP_ING_IMP=13;            //Muestra ventana con información de conteos realizados.
    private static final int INT_TBL_DAT_COD_DOC_ING_IMP=14;            //Codigo documento del ingreso por importación .
    private static final int INT_TBL_DAT_COD_REG_ING_IMP=15;            //Codigo registro del ingreso por importación .
    private static final int INT_TBL_DAT_NUM_DOC_ING_IMP=16;            //Número del ingreso por importación 
    private static final int INT_TBL_DAT_NUM_PED_ING_IMP=17;            //Número del pedido.    
    private static final int INT_TBL_DAT_FEC_ING_IMP=18;                //Fecha del ingreso por importación
    private static final int INT_TBL_DAT_CAN_PED_EMB=19;                //Cantidad de pedido embarcado.
    private static final int INT_TBL_DAT_CAN_ING_IMP=20;                //Cantidad del ingreso por importación .
    private static final int INT_TBL_DAT_COD_EMP_ORD_CON=21;            //Codigo de empresa de orden de conteo.
    private static final int INT_TBL_DAT_COD_LOC_ORD_CON=22;            //Codigo de local de orden de conteo.
    private static final int INT_TBL_DAT_COD_TIP_DOC_ORD_CON=23;        //Codigo de tipo de documento de orden de conteo.
    private static final int INT_TBL_DAT_COD_DOC_ORD_CON=24;            //Codigo de documento de orden de conteo.
    private static final int INT_TBL_DAT_COD_REG_ORD_CON=25;            //Codigo de registro de orden de conteo. 
    private static final int INT_TBL_DAT_COD_BOD_ORD_CON=26;            //Codigo de bodega de orden de conteo.
    private static final int INT_TBL_DAT_CAN_CON_TOT=27;                //Cantidad contada total
    private static final int INT_TBL_DAT_BUT_ANE_CON_HIS=28;            //Muestra ventana con información histórica de conteos realizados.
    private static final int INT_TBL_DAT_CAN_SOLTRA=29;                 //Cantidad solicitada a transferir.  
    private static final int INT_TBL_DAT_BUT_ANE_SOL=30;                //Muestra ventana con información de solicitudes de transferencias asociadas.
    private static final int INT_TBL_DAT_CAN_TRA=31;                    //Cantidad transferida.  
    private static final int INT_TBL_DAT_BUT_ANE_TRA=32;                //Muestra ventana con información de transferencias asociadas.
    private static final int INT_TBL_DAT_CAN_PER_CON=33;                //Cantidad minima permitida para que el usuario ingrese.
    private static final int INT_TBL_DAT_CAN_USR=34;                    //Cantidad nueva que ingresa un usuario.
    private static final int INT_TBL_DAT_EST_ING=35;                    //Estado que indica estado del ingreso por importación.

    //Constantes: Códigos de Menú.
    private static final int INT_COD_MNU_INGIMP=2889;                   //Ingresos por Importacion.
    
    //Contendedor: Items ZafStkInv MovimientoStock
    private static final int INT_ARL_STK_INV_COD_ITM_GRP = 0;
    private static final int INT_ARL_STK_INV_COD_ITM_EMP = 1;
    private static final int INT_ARL_STK_INV_COD_ITM_MAE = 2;
    private static final int INT_ARL_STK_INV_COD_LET_ITM = 3;
    private static final int INT_ARL_STK_INV_CAN_ITM = 4;
    private static final int INT_ARL_STK_INV_COD_BOD_EMP = 5;
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    
    //<editor-fold defaultstate="collapsed" desc="//Indice que permite obtener el nombre del campo que se desea actualizar">
    /*
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
    //</editor-fold>
    final int INT_ARL_STK_INV_STK_ACT=0;      // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
    final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng --> transferencia afectar ingreso 
    final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr --> transferencia afectar egreso
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
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafStkInv objStkInv;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCanUsr; //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenChk objTblCelRenChk;                                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoEmp;                                   //Ventana de consulta "Empresa".
    private ZafVenCon vcoPed;                                   //Ventana de consulta "Pedidos".
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafPerUsr objPerUsr;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafImp objImp;
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    
    private ZafTblCelRenBut objTblCelRenButAneTrs;              //Render: Presentar JButton en JTable - Muestra ventana con información de transferencias asociadas.
    private ZafTblCelRenBut objTblCelRenButAneCon;              //Render: Presentar JButton en JTable - Muestra ventana con información de conteos realizados.
    private ZafTblCelRenBut objTblCelRenButAneSol;              //Render: Presentar JButton en JTable - Muestra ventana con información de solicitudes de transferencias asociadas.

    private ZafTblCelEdiButGen objTblCelEdiButGenAneTrs;        //Editor: Muestra ventana con información de transferencias asociadas.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneCon;        //Editor: Muestra ventana con información de conteos realizados.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneSol;        //Editor: Muestra ventana con información de solicitud de transferencias asociadas.
    
    private ZafAjuInv objAjuInv;   
    private ZafSegAjuInv objSegAjuInv;
    private ZafHisTblBasDat objHisTblBasDat;

    private final Color colFonCol =new Color(228,228,203);//Color para las celdas
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private int intCodEmpIngImp;
    private int intCodLocIngImp;
    private int intCodTipDocIngImp;
    private int intRowsDocAju=-1;
    private int intCodEmpAju, intCodLocAju, intCodTipDocAju,  intCodDocAju;
    private int intCodEmpPedImp;                                //Para GUARDAR
    
    private String strSQL, strAux;
    private String strCodEmp, strNomEmp;                        //Contenido del campo al obtener el foco.
    private String strCodPed, strPedImp;                        //Contenido del campo al obtener el foco.
    private String strCodAlt, strCodAlt2, strNomItm,strNumPed;  //Contenido del campo al obtener el foco.
    
    private String strFecEsqAnt="2017-09-13";  //Valida Fecha para no incrementar disponible. REAL
            
    private String strVersion=" v0.1.1";

    /** Crea una nueva instancia de la clase . */
    public ZafImp29(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                initComponents();   
                if (!configurarFrm())
                    exitForm();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
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
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objTblCelRenButAneTrs=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            objTblCelRenButAneCon=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            objHisTblBasDat=new ZafHisTblBasDat();

            //Objeto de Stock de Inventario
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                       
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(4177))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4178))
            {
                butGua.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4179))
            {
                butCer.setVisible(false);
            }
                      
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha de realización del conteo");
            objSelFec.setBounds(4, 4, 472, 72);
            panFecDoc.add(objSelFec);

            //Configurar objetos.
            txtCodItmMae.setVisible(false);
            txtCodItm.setVisible(false);
            txtCodPed.setVisible(false);
            
            //Configurar las ZafVenCon.
            configurarPedidos();
            configurarVenConItm();
            
            //Configurar los JTables
            configurarTblDat();
            
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
            vecCab=new Vector(36);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK," ");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP,"Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_COD_IMP,"Cód.Imp.");
            vecCab.add(INT_TBL_DAT_NOM_IMP,"Nom.Imp.");
            vecCab.add(INT_TBL_DAT_COD_EMP_ING_IMP,"Cód.Emp.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ING_IMP,"Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_TIP_ING_IMP,"Cód.Tip.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ING_IMP,"Cód.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_REG_ING_IMP,"Cód.Reg.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_NUM_DOC_ING_IMP,"Núm.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_NUM_PED_ING_IMP,"Núm.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_FEC_ING_IMP,"Fec.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CAN_PED_EMB,"Can.Ped.Emb.");
            vecCab.add(INT_TBL_DAT_CAN_ING_IMP,"Can.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_EMP_ORD_CON,"Cód.Emp.Ord.Con.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ORD_CON,"Cód.Loc.Ord.Con.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ORD_CON,"Cód.Tip.Doc.Ord.Con.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ORD_CON,"Cód.Doc.Ord.Con.");
            vecCab.add(INT_TBL_DAT_COD_REG_ORD_CON,"Cód.Reg.Ord.Con.");
            vecCab.add(INT_TBL_DAT_COD_BOD_ORD_CON,"Cód.Bod.Ord.Con.");
            vecCab.add(INT_TBL_DAT_CAN_CON_TOT,"Can.Con.Tot.");
            vecCab.add(INT_TBL_DAT_BUT_ANE_CON_HIS,"..");
            vecCab.add(INT_TBL_DAT_CAN_SOLTRA,"Can.Sol.Tra.");    
            vecCab.add(INT_TBL_DAT_BUT_ANE_SOL,"..");  
            vecCab.add(INT_TBL_DAT_CAN_TRA,"Can.Tra."); 
            vecCab.add(INT_TBL_DAT_BUT_ANE_TRA,"..");
            vecCab.add(INT_TBL_DAT_CAN_PER_CON,"Can.Per.Con.");
            vecCab.add(INT_TBL_DAT_CAN_USR,"Can.Usr.");
            vecCab.add(INT_TBL_DAT_EST_ING,"Est.Ing.Imp.");             
           
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_ING_IMP).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_ING_IMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ING_IMP).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ORD_CON).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ORD_CON).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ORD_CON).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ORD_CON).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ORD_CON).setPreferredWidth(50); 
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORD_CON).setPreferredWidth(50); 
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON_HIS).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOLTRA).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_SOL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PER_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_USR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EST_ING).setPreferredWidth(28);            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ING_IMP, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ING_IMP, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_ING_IMP, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ORD_CON, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ORD_CON, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ORD_CON, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ORD_CON, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_ORD_CON, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD_ORD_CON, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_CON_HIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_SOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_TRA, tblDat);
            
            //if(objParSis.getCodigoUsuario()!=1)
            //{
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_PER_CON, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ING, tblDat);  
            //}
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PED_EMB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOLTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PER_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_USR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_CON_HIS);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_SOL);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_TRA);
            vecAux.add("" + INT_TBL_DAT_CAN_USR);
            objTblMod.setColumnasEditables(vecAux);
                       
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CON_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_PER_CON, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_USR, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            objTblCelRenButAneCon=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON_HIS).setCellRenderer(objTblCelRenButAneCon);
            objTblCelRenButAneCon=null;
            
            objTblCelRenButAneSol=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_SOL).setCellRenderer(objTblCelRenButAneSol);
            objTblCelRenButAneSol=null;
            
            objTblCelRenButAneTrs=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRA).setCellRenderer(objTblCelRenButAneTrs);
            objTblCelRenButAneTrs=null;
            
            //Cantidad que se solicita transferir.
            objTblCelRenLblCanUsr=new ZafTblCelRenLbl();
            objTblCelRenLblCanUsr.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCanUsr.setTipoFormato(objTblCelRenLblCanUsr.INT_FOR_NUM);
            objTblCelRenLblCanUsr.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblCanUsr.setBackground(objParSis.getColorCamposObligatorios());
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_USR).setCellRenderer(objTblCelRenLblCanUsr);
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_USR).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeValCanAux=new BigDecimal("-1");
                BigDecimal bdeValCanConTot=new BigDecimal("-1");
                BigDecimal bgdValCanUsrBef=new BigDecimal("-1");
                //BigDecimal bgdValCanUsrAft=new BigDecimal("-1");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    bdeValCanAux=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PER_CON)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PER_CON).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PER_CON).toString()));
                    bdeValCanConTot=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_CON_TOT)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_CON_TOT).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_CON_TOT).toString()));
                    bgdValCanUsrBef=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString()));
                    
                    //Valida si la Cantidad Auxiliar Permitida es menor a 0, entonces se bloquea la celda.
                    if(bdeValCanAux.compareTo(new BigDecimal("0"))<0 )
                        objTblCelEdiTxt.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    validaCanConUsr(intFil, bdeValCanConTot, bdeValCanAux, bgdValCanUsrBef);
                }
            });   
                        
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="", strTieDocAju="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                }
            });

                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            
            //Anexo 1: Conteos             
            objTblCelEdiButGenAneCon=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON_HIS).setCellEditor(objTblCelEdiButGenAneCon);
            objTblCelEdiButGenAneCon.addTableEditorListener(new ZafTableAdapter() {
                public void beforeEdit(ZafTableEvent evt) {
                }
                public void afterEdit(ZafTableEvent evt) {                    
                }
             });
            
            //Anexo 2: Solicitudes de Transferencias
            objTblCelRenButAneSol=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_SOL).setCellRenderer(objTblCelRenButAneSol);
            objTblCelRenButAneSol.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButAneSol.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ANE_SOL:
                           if (objTblMod.getValueAt(objTblCelRenButAneSol.getRowRender(), INT_TBL_DAT_CAN_SOLTRA)==null )
                               objTblCelRenButAneSol.setText("");
                            else
                               objTblCelRenButAneSol.setText("...");
                        break;
                    }
                }
            });
            objTblCelEdiButGenAneSol=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_SOL).setCellEditor(objTblCelEdiButGenAneSol);
            objTblCelEdiButGenAneSol.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_SOLTRA)==null )
                       objTblCelEdiButGenAneSol.setCancelarEdicion(true);
                 }
                 public void afterEdit(ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    String strEmp, strLoc, strTipDoc, strDoc, strItm;
                    if (tblDat.getValueAt(intCol, INT_TBL_DAT_CAN_SOLTRA)!=null){
                        strEmp=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_EMP_ING_IMP).toString();
                        strLoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_LOC_ING_IMP).toString();
                        strTipDoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_TIP_ING_IMP).toString();
                        strDoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_DOC_ING_IMP).toString();
                        strItm=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_ITM_MAE).toString();
                        mostrarVentanaAnexoSolicitud( strEmp, strLoc, strTipDoc, strDoc, strItm );
                    }
                }
             });
            
            //Anexo 3: Transferencias de inventario
            objTblCelRenButAneTrs=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_TRA).setCellRenderer(objTblCelRenButAneTrs);
            objTblCelRenButAneTrs.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButAneTrs.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ANE_TRA:
                           if (objTblMod.getValueAt(objTblCelRenButAneTrs.getRowRender(), INT_TBL_DAT_CAN_TRA)==null )
                               objTblCelRenButAneTrs.setText("");
                            else
                               objTblCelRenButAneTrs.setText("...");
                        break;
                    }
                }
            });
            objTblCelEdiButGenAneTrs=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRA).setCellEditor(objTblCelEdiButGenAneTrs);
            objTblCelEdiButGenAneTrs.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_TRA)==null )
                       objTblCelEdiButGenAneTrs.setCancelarEdicion(true);
                 }
                 public void afterEdit(ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    String strEmp, strLoc, strTipDoc, strDoc, strItm;
                    if (tblDat.getValueAt(intCol, INT_TBL_DAT_CAN_TRA)!=null){
                        strEmp=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_EMP_ING_IMP).toString();
                        strLoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_LOC_ING_IMP).toString();
                        strTipDoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_TIP_ING_IMP).toString();
                        strDoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_DOC_ING_IMP).toString();
                        strItm=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_ITM_MAE).toString();
                        mostrarVentanaAnexoTransferencia( strEmp, strLoc, strTipDoc, strDoc, strItm );
                    }
                }
             });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

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
                case INT_TBL_DAT_CHK:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg="Código alterno 2 del ítem";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida del item";
                    break;
                case INT_TBL_DAT_COD_IMP:
                    strMsg="Código del Importador";
                    break;
                case INT_TBL_DAT_NOM_IMP:
                    strMsg="Nombre del Importador";
                    break;
                case INT_TBL_DAT_COD_EMP_ING_IMP:
                    strMsg="Código de empresa del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_LOC_ING_IMP:
                    strMsg="Código de local del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_TIP_ING_IMP:
                    strMsg="Código de tipo de documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_DOC_ING_IMP:
                    strMsg="Código de documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_NUM_DOC_ING_IMP:
                    strMsg="Número de documento del Ingreso por Importación";
                    break;    
                case INT_TBL_DAT_NUM_PED_ING_IMP:
                    strMsg="Número de Pedido del Ingreso por Importación";
                    break; 
                case INT_TBL_DAT_FEC_ING_IMP:
                      strMsg="Fecha de Ingreso por Importación";
                    break;
                case INT_TBL_DAT_CAN_PED_EMB:
                    strMsg="Cantidad del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_CAN_ING_IMP:
                    strMsg="Cantidad del Ingreso por Importación";
                    break;                  
                case INT_TBL_DAT_COD_EMP_ORD_CON:
                    strMsg="Código de empresa de Orden de Conteo";
                    break;
                case INT_TBL_DAT_COD_LOC_ORD_CON:
                    strMsg="Código de local de Orden de Conteo";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ORD_CON:
                    strMsg="Código de tipo de documento de Orden de Conteo";
                    break;
                case INT_TBL_DAT_COD_DOC_ORD_CON:
                    strMsg="Código de documento de Orden de Conteo";
                    break;
                case INT_TBL_DAT_COD_REG_ORD_CON:
                    strMsg="Código de registro de Orden de Conteo";
                    break;   
                case INT_TBL_DAT_COD_BOD_ORD_CON:
                    strMsg="Código de Bodega de Orden de Conteo";
                    break;                       
                case INT_TBL_DAT_CAN_CON_TOT:
                    strMsg="Cantidad de contada total";
                    break;
                case INT_TBL_DAT_BUT_ANE_CON_HIS:
                    strMsg="Muestra información histórica de conteos realizados ";
                    break;                    
                case INT_TBL_DAT_CAN_SOLTRA:
                    strMsg="Cantidad de Solicitud de Transferencia";
                    break;   
                case INT_TBL_DAT_BUT_ANE_SOL:
                    strMsg="Muestra información de solicitud de transferencias asociadas";
                    break;                     
                case INT_TBL_DAT_CAN_TRA:
                    strMsg="Cantidad de Transferencias";
                    break;  
                case INT_TBL_DAT_BUT_ANE_TRA:
                    strMsg="Muestra información de transferencias asociadas";
                    break;                          
                case INT_TBL_DAT_CAN_PER_CON:
                    strMsg="Cantidad Mínima Permitida para modificar conteos";
                    break;     
                case INT_TBL_DAT_CAN_USR:
                    strMsg="Cantidad que ingresa el usuario como cantidad contada";
                    break;  
               case INT_TBL_DAT_EST_ING:
                    strMsg="Indica estado de ingreso por importación";
                    break;  
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos".
     */
    private boolean configurarPedidos()
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
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("100");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+=" FROM tbm_cabMovInv AS a1";
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc ";
            strSQL+=" WHERE a1.st_Reg='A' AND a1.co_mnu="+INT_COD_MNU_INGIMP;
            strSQL+=" AND a1.co_tipDoc IN ( select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_mnu="+INT_COD_MNU_INGIMP+")";
            strSQL+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) IN ('B') ";  
            strSQL+=" ORDER BY a1.fe_doc DESC, a1.tx_numDoc2";            
            
            //Ocultar columnas.
            int intColOcu[]=new int[4];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            
            vcoPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPed.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
            arlCam.add("d1.co_itmMae");
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2"); 
            arlAli.add("Nom.Itm."); 
            arlAli.add("Uni.Med.");  
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("80");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
            strSQL+=" ORDER BY a1.tx_codAlt";
            
            //Ocultar Columnas
            int intColOcu[]=new int[1];
            intColOcu[0]=2;  // Cód.Itm.
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
           // vcoItm.setCampoBusqueda(2);
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
    private boolean mostrarPedidos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPed.setCampoBusqueda(5);
                    vcoPed.setVisible(true);
                    if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp = Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp = Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp = Integer.parseInt(vcoPed.getValueAt(3));

                    }
                    break;
                 case 1: //Búsqueda directa por "Código Pedido".
                    if (vcoPed.buscar("a1.co_doc", txtCodPed.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(3);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                        }
                        else
                        {
                            txtCodPed.setText(strCodPed); 
                        }
                    }
                    break;
               
                case 2: //Búsqueda directa por "Número Documento".
                    if (vcoPed.buscar("a1.ne_numDoc", txtNumDocIngImp.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(4);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                        }
                        else
                        {
                            txtNumDocIngImp.setText(strNumPed); 
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoPed.buscar("a1.tx_numDoc2", txtPedIngImp.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(5);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
                        }
                        else
                        {
                            txtPedIngImp.setText(strPedImp);
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
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(2);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5)); 
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodAlt2);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5)); 
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(4);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
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

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItmMae = new javax.swing.JTextField();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panBusItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 75));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panCon.add(panFecDoc, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(0, 440));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 2, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 20, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Item");
        panFil.add(lblItm);
        lblItm.setBounds(30, 50, 50, 20);
        panFil.add(txtCodItmMae);
        txtCodItmMae.setBounds(70, 50, 15, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(85, 50, 15, 20);

        txtCodAlt.setToolTipText("Código Alterno");
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(100, 50, 130, 20);

        txtCodAlt2.setToolTipText("Código Alterno 2 ");
        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(230, 50, 80, 20);

        txtNomItm.setToolTipText("Nombre del Item");
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(310, 50, 330, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 50, 20, 20);

        panBusItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panBusItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panBusItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(27, 18, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panBusItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(70, 18, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panBusItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(183, 18, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panBusItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(235, 18, 100, 20);

        panFil.add(panBusItm);
        panBusItm.setBounds(30, 72, 370, 45);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("Cliente");
        panFil.add(lblPed);
        lblPed.setBounds(30, 125, 70, 20);

        txtCodPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPedActionPerformed(evt);
            }
        });
        txtCodPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPedFocusLost(evt);
            }
        });
        panFil.add(txtCodPed);
        txtCodPed.setBounds(80, 125, 20, 20);

        txtNumDocIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocIngImpActionPerformed(evt);
            }
        });
        txtNumDocIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusLost(evt);
            }
        });
        panFil.add(txtNumDocIngImp);
        txtNumDocIngImp.setBounds(100, 125, 100, 20);

        txtPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPedIngImpActionPerformed(evt);
            }
        });
        txtPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusLost(evt);
            }
        });
        panFil.add(txtPedIngImp);
        txtPedIngImp.setBounds(200, 125, 440, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panFil.add(butPedImp);
        butPedImp.setBounds(640, 125, 20, 20);

        panCon.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panCon);

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
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Filtro");

        panBar.setPreferredSize(new java.awt.Dimension(320, 52));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItmMae.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItmMae.setText("");
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
            txtNomItm.setText(strNomItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomItm.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItmMae.setText("");
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodAlt);
        if (txtCodAlt.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
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

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPedActionPerformed
        txtCodPed.transferFocus();
    }//GEN-LAST:event_txtCodPedActionPerformed

    private void txtCodPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusGained
        strCodPed=txtCodPed.getText();
        txtCodPed.selectAll();
    }//GEN-LAST:event_txtCodPedFocusGained

    private void txtCodPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPed.getText().equalsIgnoreCase(strCodPed))
        {
            if (txtCodPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(1);
            }
        }
        else
            txtCodPed.setText(strCodPed);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodPedFocusLost

    private void txtPedIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPedIngImpActionPerformed
        txtPedIngImp.transferFocus();
    }//GEN-LAST:event_txtPedIngImpActionPerformed

    private void txtPedIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusGained
        strPedImp=txtPedIngImp.getText();
        txtPedIngImp.selectAll();
    }//GEN-LAST:event_txtPedIngImpFocusGained

    private void txtPedIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtPedIngImp.getText().equalsIgnoreCase(strPedImp))
        {
            if (txtPedIngImp.getText().equals(""))
            {
                txtCodPed.setText("");
                txtPedIngImp.setText("");
                txtNumDocIngImp.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(3);
            }
        }
        else
            txtPedIngImp.setText(strPedImp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtPedIngImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtPedIngImpFocusLost

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        configurarPedidos();
        mostrarPedidos(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butPedImpActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
            txtCodPed.setText("");
            txtPedIngImp.setText("");
            txtNumDocIngImp.setText("");
            txtCodItmMae.setText("");
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2 = txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItmMae.setText("");
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtCodAlt2.setText(strCodAlt2);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodAlt2.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtNumDocIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocIngImpActionPerformed
        txtNumDocIngImp.transferFocus();
    }//GEN-LAST:event_txtNumDocIngImpActionPerformed

    private void txtNumDocIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusGained
        strNumPed=txtNumDocIngImp.getText();
        txtNumDocIngImp.selectAll();
    }//GEN-LAST:event_txtNumDocIngImpFocusGained

    private void txtNumDocIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusLost
        if (!txtNumDocIngImp.getText().equalsIgnoreCase(strNumPed))
        {
            if (txtNumDocIngImp.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                configurarPedidos();
                mostrarPedidos(2);
            }
        }
        else
            txtNumDocIngImp.setText(strNumPed);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNumDocIngImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNumDocIngImpFocusLost

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged()) 
        {
            if(isCamVal())
            {
                if (guardar()) 
                {
                    mostrarMsgInf("<HTML>La operación GUARDAR se realizó con éxito.</HTML>");
                    cargarDetReg();          
                }
                else {
                    mostrarMsgErr("No se pudo realizar la operación GUARDAR.<BR>Verifique los datos y vuelva a intentarlo.");
                }
            }
            else {
                mostrarMsgErr("No se pudo realizar la operación GUARDAR.<BR>Verifique los datos y vuelva a intentarlo.");
            }
        } 
        else {
           mostrarMsgInf("<HTML>No se han realizado cambios que guardar.");
        }
    }//GEN-LAST:event_butGuaActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butPedImp;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBusItm;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodItmMae;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtNumDocIngImp;
    private javax.swing.JTextField txtPedIngImp;
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
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
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
      
     /*
      * Muestra la ventana del boton de cuales son las transferencias asociadas.
      * 
      */
     private void mostrarVentanaAnexoTransferencia(String strEmp, String strLoc, String strTipDoc,String strDoc, String strItm )
     {
         int intEmp, intLoc, intTipDoc, intDoc, intItmMae;
         
         intEmp=Integer.parseInt(strEmp);
         intLoc=Integer.parseInt(strLoc);
         intTipDoc=Integer.parseInt(strTipDoc);
         intDoc=Integer.parseInt(strDoc);
         intItmMae= Integer.parseInt(strItm);
        
         //objImp08_01=new ZafImp29_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         //objImp08_01.show();
     }
         
     private void mostrarVentanaAnexoConteo(String strEmp, String strLoc, String strTipDoc,String strDoc, String strItm )
     {
         int intEmp, intLoc, intTipDoc, intDoc, intItmMae;
         
         intEmp=Integer.parseInt(strEmp);
         intLoc=Integer.parseInt(strLoc);
         intTipDoc=Integer.parseInt(strTipDoc);
         intDoc=Integer.parseInt(strDoc);
         intItmMae= Integer.parseInt(strItm);
        
         //objImp08_02=new ZafImp29_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         //objImp08_02.show();
     }
     
     /*
      * Muestra la ventana del boton de cuales son las solicitudes de transferencias asociadas.
      * 
      */
     private void mostrarVentanaAnexoSolicitud(String strEmp, String strLoc, String strTipDoc,String strDoc, String strItm )
     {
         int intEmp, intLoc, intTipDoc, intDoc, intItmMae;
         
         intEmp=Integer.parseInt(strEmp);
         intLoc=Integer.parseInt(strLoc);
         intTipDoc=Integer.parseInt(strTipDoc);
         intDoc=Integer.parseInt(strDoc);
         intItmMae= Integer.parseInt(strItm);
        
         //objImp08_04=new ZafImp29_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         //objImp08_04.show();
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
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                strAux="";
                if(objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strAux+=" AND b1.fe_reaCon BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND b1.fe_reaCon <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND b1.fe_reaCon >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 3: //Todo....
                                break;
                    }
                }

                stm=con.createStatement();
                //Obtener la condición.
                strSQL="";
                strSQL+=getSQLConteo();
                strSQL+=" AND b1.nd_canConTot!=b1.nd_CanSol AND b1.nd_canConTot!=b1.nd_CanTrs\n";
                strSQL+=" AND (CASE WHEN (b1.nd_canConTot<b1.nd_canSol OR b1.nd_canConTot<b1.nd_canTrs) THEN FALSE ELSE TRUE END ) \n";
                
                //Filtros
                if (txtCodItmMae.getText().length() > 0) {
                    strSQL+=" AND b1.co_itmMae=" + txtCodItmMae.getText() +"\n";  
                }
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0){
                    strSQL+=" AND ((LOWER(b3.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(b3.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                if(txtCodPed.getText().length()>0){
                    strSQL+=" AND b1.co_empIngImp=" + intCodEmpIngImp + " AND b1.co_locIngImp=" + intCodLocIngImp + " AND b1.co_tipDocIngImp=" + intCodTipDocIngImp + " AND b1.co_docIngImp=" + txtCodPed.getText() +"\n";  
                }
                strSQL+="  " + strAux+"\n";   
                
                strSQL+=" GROUP BY b1.fe_reaCon, b1.fe_docIngImp, b1.co_imp, b1.tx_nomImp, b1.co_empIngImp, b1.co_locIngImp, b1.co_tipDocIngImp, b1.co_docIngImp, b1.ne_numDocIngImp, b1.tx_numDoc2IngImp \n";
                strSQL+="        , b1.st_reg, b1.st_ingImp, b1.st_tieAju, b1.co_empOrdCon, b1.co_locOrdCon, b1.co_tipDocOrdCon, b1.co_docOrdCon, b1.co_regOrdCon, b1.co_bodGrpOrdCon \n";     
                strSQL+="        , b2.co_regIngImp, b2.co_itmIngImp, b1.co_itmGrp, b1.co_itmMae, b2.tx_codAlt, b2.tx_codAlt2, b2.tx_nomItm, b2.tx_uniMed, b3.tx_codAlt, b3.tx_codAlt2, b3.tx_nomItm, b3.tx_uniMed \n";
                strSQL+="        , b2.nd_canIngImp, b1.nd_canPedEmb, b1.nd_canConTot, b1.nd_CanSol, b1.nd_CanTrs \n";
                strSQL+=" ORDER BY b1.fe_docIngImp, b1.tx_numDoc2IngImp, b3.tx_codAlt, b2.co_regIngImp\n";  
      
                //System.out.println("ZafImp29.cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        //vecReg.add(INT_TBL_DAT_CHK,rst.getString("co_seg") );
                        vecReg.add(INT_TBL_DAT_CHK,null );
                        vecReg.add(INT_TBL_DAT_COD_ITM_GRP,rst.getString("co_itmGrp"));
                        //vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,null);
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("tx_uniMed"));
                        vecReg.add(INT_TBL_DAT_COD_IMP,rst.getString("co_imp"));
                        vecReg.add(INT_TBL_DAT_NOM_IMP,rst.getString("tx_nomImp"));
                        vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,rst.getString("co_empIngImp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,rst.getString("co_locIngImp"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_ING_IMP,rst.getString("co_tipdocIngImp"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,rst.getString("co_docIngImp"));
                        vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,rst.getString("co_regIngImp"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC_ING_IMP,rst.getString("ne_numDocIngImp"));
                        vecReg.add(INT_TBL_DAT_NUM_PED_ING_IMP,rst.getString("tx_numDoc2IngImp"));
                        vecReg.add(INT_TBL_DAT_FEC_ING_IMP,rst.getString("fe_docIngImp"));
                        vecReg.add(INT_TBL_DAT_CAN_PED_EMB,rst.getString("nd_canPedEmb"));
                        vecReg.add(INT_TBL_DAT_CAN_ING_IMP,rst.getString("nd_canIngImp"));
                        vecReg.add(INT_TBL_DAT_COD_EMP_ORD_CON,rst.getString("co_empOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_ORD_CON,rst.getString("co_locOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ORD_CON,rst.getString("co_tipDocOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_ORD_CON,rst.getString("co_docOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_REG_ORD_CON,rst.getString("co_regOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_BOD_ORD_CON,rst.getString("co_bodGrpOrdCon"));
                        vecReg.add(INT_TBL_DAT_CAN_CON_TOT,rst.getString("nd_canConTot"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_CON_HIS, null);
                        vecReg.add(INT_TBL_DAT_CAN_SOLTRA,rst.getString("nd_canSol"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_SOL, null);
                        vecReg.add(INT_TBL_DAT_CAN_TRA,rst.getString("nd_canTrs"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_TRA, null);
                        vecReg.add(INT_TBL_DAT_CAN_PER_CON,rst.getString("nd_canPerCon"));
                        vecReg.add(INT_TBL_DAT_CAN_USR,null);
                        vecReg.add(INT_TBL_DAT_EST_ING,rst.getString("st_ingImp"));

                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
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

                if (blnCon) 
                {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                }
                else 
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                
                
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
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
     * Esta función determina si los campos son válidos.
     * @param intTipVal Indica tipo de validación. 0=Validacion Normal. 1=Validacion Boton Cargar Items. 2=Validacion Consulta Pedidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        //Valida que existan cambios.
        if (objTblMod.getRowCountTrue()==0 || objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK)==false)  
        {               
            mostrarMsgInf("<HTML>No se han realizado cambios que guardar.");
            return false;
        } 
        
        //Valida Datos.
        for(int i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                if(!validaDatosBD(i))
                {
                    return false;
                }
            }
        }
     
        return true;
    }
       
    private boolean validaDatosBD(int intFil) 
    {
        boolean blnRes=false;
        BigDecimal bgdValCanUsrBef=new BigDecimal("-1");
        java.sql.Connection conLoc=null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL ="";
                strSQL+=getSQLConteo();
                strSQL+=" AND b1.co_empIngImp="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                strSQL+=" AND b1.co_locIngImp="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                strSQL+=" AND b1.co_tipDocIngImp="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_ING_IMP);
                strSQL+=" AND b1.co_docIngImp="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                strSQL+=" AND b1.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);
                strSQL+=" GROUP BY b1.fe_reaCon, b1.fe_docIngImp, b1.co_imp, b1.tx_nomImp, b1.co_empIngImp, b1.co_locIngImp, b1.co_tipDocIngImp, b1.co_docIngImp, b1.ne_numDocIngImp, b1.tx_numDoc2IngImp \n";
                strSQL+="        , b1.st_reg, b1.st_ingImp, b1.st_tieAju, b1.co_empOrdCon, b1.co_locOrdCon, b1.co_tipDocOrdCon, b1.co_docOrdCon, b1.co_regOrdCon, b1.co_bodGrpOrdCon \n";     
                strSQL+="        , b2.co_regIngImp, b2.co_itmIngImp, b1.co_itmGrp, b1.co_itmMae, b2.tx_codAlt, b2.tx_codAlt2, b2.tx_nomItm, b2.tx_uniMed, b3.tx_codAlt, b3.tx_codAlt2, b3.tx_nomItm, b3.tx_uniMed \n";
                strSQL+="        , b2.nd_canIngImp, b1.nd_canPedEmb, b1.nd_canConTot, b1.nd_CanSol, b1.nd_CanTrs \n";
                
                bgdValCanUsrBef=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString()));
                
                //System.out.println("validaDatosBD:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    objTblMod.setValueAt( rstLoc.getString("nd_canConTot"), intFil, INT_TBL_DAT_CAN_CON_TOT);
                    objTblMod.setValueAt( rstLoc.getString("nd_canSol"), intFil, INT_TBL_DAT_CAN_SOLTRA);
                    objTblMod.setValueAt( rstLoc.getString("nd_canTrs"), intFil, INT_TBL_DAT_CAN_TRA);
                    objTblMod.setValueAt( rstLoc.getString("nd_canPerCon"), intFil, INT_TBL_DAT_CAN_PER_CON);
                    objTblMod.setValueAt( rstLoc.getString("st_ingImp"), intFil, INT_TBL_DAT_EST_ING);
                    
                    if(rstLoc.getString("st_ingImp").equals("B"))
                    {
                        if(validaCanConUsr(intFil, BigDecimal.valueOf(rstLoc.getDouble("nd_canConTot")) 
                                  , BigDecimal.valueOf(rstLoc.getDouble("nd_canPerCon")) , bgdValCanUsrBef ))
                        {
                            blnRes=true;
                        }
                    }
                    else
                    {
                        mostrarMsgInf("<HTML>El pedido <FONT COLOR=\"blue\">"+objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_PED_ING_IMP)+" </FONT> ha sido cerrado.<BR>No se puede dar de baja conteos de un pedido cerrado.</HTML>");
                    }
                }
            }
            rstLoc.close();
            stmLoc.close();
            conLoc.close();
            rstLoc=null;
            stmLoc=null;
            conLoc=null;
        }
        catch (java.sql.SQLException e) { blnRes=false;   objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

        
    /**
     * Función que retorna la sentencia SQL de los conteos.
     * @return strPedidos
     */
    public String getSQLConteo()
    {
        String strSqlCon="";
        try
        {
            strSqlCon="";
            strSqlCon+=" SELECT b1.fe_reaCon, b1.fe_docIngImp, b1.co_imp, b1.tx_nomImp, b1.co_empIngImp, b1.co_locIngImp, b1.co_tipDocIngImp, b1.co_docIngImp, b1.ne_numDocIngImp, b1.tx_numDoc2IngImp \n";
            strSqlCon+="      , b1.st_reg, b1.st_ingImp, b1.st_tieAju, b1.co_empOrdCon, b1.co_locOrdCon, b1.co_tipDocOrdCon, b1.co_docOrdCon, b1.co_regOrdCon, b1.co_bodGrpOrdCon  \n";      
            strSqlCon+="      , b2.co_regIngImp, b2.co_itmIngImp, b1.co_itmGrp, b1.co_itmMae \n";
            strSqlCon+="      , CASE WHEN b2.tx_codAlt IS NULL THEN b3.tx_codAlt ELSE  b2.tx_codAlt END tx_codAlt \n";
            strSqlCon+="      , CASE WHEN b2.tx_codAlt2 IS NULL THEN b3.tx_codAlt2 ELSE  b2.tx_codAlt2 END tx_codAlt2 \n";
            strSqlCon+="      , CASE WHEN b2.tx_nomItm IS NULL THEN b3.tx_nomItm ELSE  b2.tx_nomItm END tx_nomItm \n";
            strSqlCon+="      , CASE WHEN b2.tx_uniMed IS NULL THEN b3.tx_uniMed ELSE  b2.tx_uniMed END tx_uniMed \n";
            strSqlCon+="      , CASE WHEN b2.nd_canIngImp IS NULL THEN 0 ELSE b2.nd_canIngImp END AS nd_canIngImp \n";
            strSqlCon+="      , b1.nd_canPedEmb, b1.nd_canConTot, b1.nd_CanSol, b1.nd_CanTrs \n";       
            strSqlCon+="      , b1.nd_canConTot - (b1.nd_canConTot - (CASE WHEN b1.nd_CanSol>= b1.nd_canTrs THEN b1.nd_CanSol ELSE b1.nd_canTrs end  ) ) AS nd_canPerCon \n";     
            strSqlCon+=" FROM( \n";
            strSqlCon+="       SELECT a2.fe_reaCon, a1.fe_docIngImp, a1.co_imp, a1.tx_nomImp, a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.ne_numDocIngImp, a1.tx_numDoc2IngImp \n";
            strSqlCon+="            , a1.st_reg, a1.st_ingImp, a1.st_tieAju, a1.co_empOrdCon, a1.co_locOrdCon, a1.co_tipDocOrdCon, a1.co_docOrdCon, a2.co_regOrdCon, a2.co_bodGrpOrdCon  \n";
            strSqlCon+="            , a2.co_itmGrp, a2.co_itmMae, a2.nd_CanConBueEst, a2.nd_canConMalEst \n";
            strSqlCon+="            , (a2.nd_CanConBueEst+ a2.nd_canConMalEst) AS nd_canConTot  \n";
            strSqlCon+="            , CASE WHEN a3.nd_canPedEmb IS NULL THEN 0 ELSE a3.nd_canPedEmb END AS nd_canPedEmb \n";
            strSqlCon+="            , CASE WHEN a4.nd_canSol IS NULL THEN 0 ELSE a4.nd_canSol END AS nd_canSol \n";  
            strSqlCon+="  	    , CASE WHEN a5.nd_canTrs IS NULL THEN 0 ELSE a5.nd_canTrs END AS nd_canTrs \n";                   
            strSqlCon+="       FROM( \n";
            strSqlCon+=" 	   SELECT a1.fe_doc AS fe_docIngImp, a.co_empRel AS co_empIngImp, a.co_locRel AS co_locIngImp, a.co_tipDocRel AS co_tipDocIngImp, a.co_docRel AS co_docIngImp \n";
            strSqlCon+=" 	        , a1.ne_numDoc AS ne_numDocIngImp, a1.tx_numDoc2 AS tx_numDoc2IngImp, a1.st_reg, a1.st_ingImp, a1.st_cieAjuInvIngImp AS st_tieAju \n";
            strSqlCon+="               , a2.co_Emp as co_imp, a2.tx_nom AS tx_nomImp, a.co_emp AS co_empOrdCon, a.co_loc AS co_locOrdCon, a.co_tipDoc AS co_tipDocOrdCon, a.co_doc AS co_docOrdCon \n";
            strSqlCon+="               , a1.co_empRelPedEmbImp AS co_empPedEmb, a1.co_locRelPedEmbImp AS co_locPedEmb, a1.co_tipdocRelPedEmbImp AS co_tipDocPedEmb, a1.co_docRelPedEmbImp AS co_docPedEmb \n";         
            strSqlCon+=" 	   FROM tbm_cabOrdConInv AS a \n";
            strSqlCon+=" 	   INNER JOIN tbm_cabMovInv AS a1 ON a1.co_emp=a.co_empRel AND a1.co_loc=a.co_locRel AND a1.co_tipDoc=a.co_tipDocRel AND a1.co_doc=a.co_docRel \n";	
            strSqlCon+=" 	   INNER JOIN tbm_emp AS a2 ON a2.co_emp=a1.co_emp \n";
            strSqlCon+=" 	   WHERE a1.st_Reg IN ('A') AND a.co_tipDocRel IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+INT_COD_MNU_INGIMP+") \n";
            strSqlCon+="       ) AS a1 \n";
            strSqlCon+="       INNER JOIN( /* CONTEOS */ \n";     
            strSqlCon+=" 	   SELECT a.co_emp, a.co_locRel AS co_loc, a.co_tipDocRel AS co_tipDoc, a.co_docRel AS co_doc, a.co_regRel AS co_regOrdCon, a.co_bod as co_bodGrpOrdCon \n";
            strSqlCon+="                 , a1.co_itmMae, a.co_itm as co_itmGrp, a.nd_stkCon AS nd_CanConBueEst, 0 as nd_canConMalEst, CAST( a.fe_ReaCon AS DATE) AS fe_reaCon \n";
            strSqlCon+=" 	   FROM tbm_conInv AS a \n";
            strSqlCon+=" 	   INNER JOIN tbm_equInv AS a1 ON a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm \n";
            strSqlCon+="       ) AS a2 ON a2.co_emp=a1.co_empOrdCon AND a2.co_loc=a1.co_locOrdCon AND a2.co_tipDoc=a1.co_tipDocOrdCon AND a2.co_doc=a1.co_docOrdCon \n";
            strSqlCon+="       LEFT OUTER JOIN( /* PEDIDO EMBARCADO */ \n";
            strSqlCon+="            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a1.nd_can AS nd_canPedEmb, a3.co_itmMae \n";
            strSqlCon+="            FROM tbm_cabPedEmbImp AS a \n"; 
            strSqlCon+="            INNER JOIN tbm_detPedEmbImp AS a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc \n";
            strSqlCon+="  	   INNER JOIN tbm_equInv AS a3 ON a3.co_emp=a1.co_emp AND a3.co_itm=a1.co_itm \n";
            strSqlCon+="  	   WHERE a.st_reg='A' \n";
            strSqlCon+="       ) AS a3 ON a3.co_emp=a1.co_empPedEmb AND a3.co_loc=a1.co_locPedEmb AND a3.co_tipDoc=a1.co_tipDocPedEmb AND a3.co_doc=a1.co_docPedEmb AND a3.co_itmMae=a2.co_itmMae \n";
            strSqlCon+="       LEFT OUTER JOIN( /* SOLICITUDES DE TRANSFERENCIAS */ \n";
            strSqlCon+="  	   SELECT a2.co_EmpRelCabMovInv AS co_empRel, a2.co_locRelCabMovInv AS co_locRel, a2.co_tipDocRelCabMovInv AS co_tipDocRel, a2.co_docRelCabMovInv AS co_docRel \n";
            strSqlCon+="                 , a3.co_itmMae, SUM(a1.nd_can) AS nd_canSol \n";
            strSqlCon+="  	   FROM tbm_CabSolTraInv as a \n"; 	   
            strSqlCon+="  	   INNER JOIN tbm_detSolTraInv as a1 ON (a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipdoc AND a.co_doc=a1.co_Doc) \n";
            strSqlCon+="          INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ON (a2.co_EmpRelCabSolTraInv=a.co_emp AND a2.co_locRelCabSolTraInv=a.co_loc AND a2.co_tipDocRelCabSolTraInv=a.co_tipdoc AND a2.co_docRelCabSolTraInv=a.co_Doc ) \n";
            strSqlCon+="  	   INNER JOIN tbm_equInv as a3 ON (a3.co_emp=a.co_emp AND a3.co_itm=a1.co_itm) \n";
            strSqlCon+="  	   WHERE a.st_reg in ('A') AND (CASE WHEN a.st_aut IS NULL THEN 'P' ELSE a.st_aut END) NOT IN ('D') \n";
            strSqlCon+="  	   GROUP BY a2.co_EmpRelCabMovInv, a2.co_locRelCabMovInv, a2.co_tipDocRelCabMovInv, a2.co_docRelCabMovInv, a3.co_itmMae \n";
            strSqlCon+="       ) AS a4 ON a4.co_EmpRel=a1.co_empIngImp AND a4.co_locRel=a1.co_locIngImp AND a4.co_tipDocRel=a1.co_tipDocIngImp AND a4.co_docRel=a1.co_docIngImp AND a4.co_itmMae=a2.co_itmMae \n";
            strSqlCon+="       LEFT OUTER JOIN( /* TRANSFERENCIAS */ \n";
            strSqlCon+="  	   SELECT a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a3.co_itmMae, ABS(SUM(a1.nd_can)) AS nd_canTrs \n"; 		      
            strSqlCon+=" 	   FROM (tbr_cabMovInv AS a2 INNER JOIN tbm_cabMovInv AS a ON a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_doc) \n";
            strSqlCon+=" 	   INNER JOIN tbm_detMovInv AS a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc \n";
            strSqlCon+=" 	   INNER JOIN tbm_equInv AS a3 ON a3.co_emp=a1.co_emp AND a3.co_itm=a1.co_itm \n";
            strSqlCon+=" 	   WHERE a1.nd_can<0 AND a.st_Reg in ('A') \n"; 
            strSqlCon+=" 	   AND a.co_tipDoc NOT IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") \n";
            strSqlCon+=" 	   GROUP BY a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a3.co_itmMae \n";
            strSqlCon+="       ) AS a5 ON a5.co_EmpRel=a1.co_empIngImp AND a5.co_locRel=a1.co_locIngImp AND a5.co_tipDocRel=a1.co_tipDocIngImp AND a5.co_docRel=a1.co_docIngImp AND a5.co_itmMae=a2.co_itmMae \n";
            strSqlCon+=" ) AS b1 \n";
            strSqlCon+=" LEFT OUTER JOIN  \n";
            strSqlCon+=" ( \n";
            strSqlCon+="       SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_reg AS co_RegIngImp, a.co_itm as co_itmIngImp, a2.co_itmMae, a.nd_Can as nd_canIngImp \n";
            strSqlCon+="            , a.tx_codAlt, a1.tx_codAlt2, a.tx_nomItm, a.tx_uniMed \n";
            strSqlCon+="       FROM tbm_detMovInv AS a \n";
            strSqlCon+="       INNER JOIN tbm_inv AS a1 ON a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm  \n";
            strSqlCon+="       INNER JOIN tbm_equInv AS a2 ON a2.co_emp=a1.co_emp AND a2.co_itm=a1.co_itm \n";
            strSqlCon+=" ) AS b2 \n";
            strSqlCon+=" ON b2.co_emp=b1.co_empIngImp AND b2.co_loc=b1.co_locIngImp AND b2.co_tipDoc=b1.co_tipDocIngImp AND b2.co_doc=b1.co_docIngImp AND b2.co_itmMae=b1.co_itmMae \n";
            strSqlCon+=" LEFT OUTER JOIN \n";
            strSqlCon+=" ( \n";
            strSqlCon+="       SELECT a2.co_itmMae, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a1.tx_desCor AS tx_uniMed \n";
            strSqlCon+="       FROM tbm_inv AS a \n"; 
            strSqlCon+="       LEFT OUTER JOIN tbm_var AS a1 ON a1.co_reg=a.co_uni \n";
            strSqlCon+="       INNER JOIN tbm_equInv AS a2 ON a2.co_emp=a.co_emp AND a2.co_itm=a.co_itm \n";
            strSqlCon+=" ) AS b3  ON b3.co_itmMae=b1.co_itmMae \n";
            strSqlCon+=" WHERE b1.st_ingImp IN ('B') \n"; 
        }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
        return strSqlCon;
    }
    

    /**
     * Función  que valida la cantidad ingresada para actualizar conteo.
     * @param intFil Fila que se está validando
     * @param bdeValCanConTot Cantidad total contada.
     * @param bdeValCanAux Cantidad que indica el valor minimo permitido que puede ingresar el usuario.
     * @param bgdValCanUsrBef
     * @return 
     */
    private boolean validaCanConUsr(int intFil, BigDecimal bdeValCanConTot, BigDecimal bdeValCanAux, BigDecimal bgdValCanUsrBef)
    {
        boolean blnRes=false;
        BigDecimal bgdValCanUsrAft;
           
        bgdValCanUsrAft=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString()));
        //Si la cantidad que ingresa el usuario es menor a la contada total.
        if(bgdValCanUsrAft.compareTo(bdeValCanConTot)<0){
            //Valida que la cantidad que ingresa el usuario sea mayor o igual a la permitida.
            if(bgdValCanUsrAft.compareTo(bdeValCanAux)>=0){
                blnRes=true;
                if(!objTblMod.isChecked(intFil, INT_TBL_DAT_CHK)) {
                    objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                }
                objTblMod.setValueAt(bgdValCanUsrAft, intFil, INT_TBL_DAT_CAN_USR);
                if(objTblMod.getRowCountTrue()>(intFil+1)) {
                    objTblEdi.seleccionarCelda(intFil+1, INT_TBL_DAT_CAN_USR);
                }
            }
            else  
            {
                mostrarMsgInf("<HTML>La cantidad ingresada es menor a la permitida.<BR>Existen solicitudes y/o transferencias enlazadas al conteo actual.<BR>Verifique y vuelva a intentarlo</HTML>");
                if(bgdValCanUsrBef.compareTo(new BigDecimal("0"))>=0)
                {
                    //Se coloca la cantidad contada anterior.
                    if(!objTblMod.isChecked(intFil, INT_TBL_DAT_CHK)) {
                        objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                    }
                    objTblMod.setValueAt(bgdValCanUsrBef, intFil, INT_TBL_DAT_CAN_USR);
                    objTblEdi.seleccionarCelda(intFil, INT_TBL_DAT_CAN_USR);
                }
                else
                {
                    objTblMod.setValueAt("", intFil, INT_TBL_DAT_CAN_USR);
                    objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                    objTblEdi.seleccionarCelda(intFil, INT_TBL_DAT_CAN_USR);
                }
            }
        }
        else
        {
            if(bgdValCanUsrAft.compareTo(bdeValCanConTot)==0){
                mostrarMsgInf("<HTML>La cantidad ingresada es igual al conteo actual.<BR>Debe ingresar un valor distinto al conteo.</HTML>");
                objTblMod.setValueAt("", intFil, INT_TBL_DAT_CAN_USR);
                objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                objTblEdi.seleccionarCelda(intFil, INT_TBL_DAT_CAN_USR);                
            }
            else
            {
                mostrarMsgInf("<HTML>La cantidad ingresada sobrepasa la cantidad del conteo actual.<BR>Solo está permitido dar de baja a los conteos.</HTML>");
                if(bgdValCanUsrBef.compareTo(new BigDecimal("0"))>=0){
                    objTblMod.setValueAt(bgdValCanUsrBef, intFil, INT_TBL_DAT_CAN_USR);
                    if(!objTblMod.isChecked(intFil, INT_TBL_DAT_CHK)) {
                       objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                    }
                    objTblEdi.seleccionarCelda(intFil, INT_TBL_DAT_CAN_USR);
                }
                else
                {
                    objTblMod.setValueAt("", intFil, INT_TBL_DAT_CAN_USR);
                    objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                    objTblEdi.seleccionarCelda(intFil, INT_TBL_DAT_CAN_USR);
                }
            }
        }
    
        return blnRes;    
    
    }
        
    /**
     * Esta función permite actualizar los registros del detalle.
     * Indica que el pedido ha sido arribado.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar()
    {
        boolean blnRes=false;
        try
        {
            for (int i=0; i<objTblMod.getRowCountTrue(); i++)
            {
                if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("M"))
                {
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                    {
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        con.setAutoCommit(false);
                        if (con!=null)
                        {
                            if(actualizarConteo(i))
                            {   
                                if(generaConItmTbmInvBod(i))
                                {
                                    //(-) Decrementa Disponible.
                                    if(objStkInv.actualizaInventario(con, intCodEmpPedImp, INT_ARL_STK_INV_CAN_DIS, "-", 0, arlDatStkInvItm))
                                    { 
                                        //(+) Incrementa Cantidad por Ingresar.
                                        if(objStkInv.actualizaInventario(con, intCodEmpPedImp, INT_ARL_STK_INV_CAN_ING_BOD, "+", 0, arlDatStkInvItm))
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
                            
                            con.close();
                            con=null;
                        }
                    }
                }
            }
            //Inicializo el estado de las filas.
            objTblMod.initRowsState();
            objTblMod.setDataModelChanged(false);
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
    * Función que permite actualizar el conteo.
    * @return true si se pudo realizar la operación
    * <BR> false caso contrario
    */ 
   private boolean actualizarConteo(int intFil)
   {
       boolean blnRes=true;
       BigDecimal bgdCanUsr=new BigDecimal("0");
       String strFecUltMod;
       try
       {
           if(con!=null)
           {
                stm=con.createStatement();
             
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
               
                bgdCanUsr=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString()));
               
                //Actualiza Conteo (tbm_conInv)
                strSQL ="";
                strSQL+=" UPDATE tbm_conInv";
                strSQL+=" SET fe_reacon='" + strFecUltMod + "'";
                strSQL+="   , co_usrResCon=" + objParSis.getCodigoUsuario();
                strSQL+="   , nd_stkcon=" + bgdCanUsr;
                strSQL+="   , tx_obs1= 'Modificación por baja de conteos.' ||(CASE WHEN tx_obs1 IS NULL THEN '' ELSE (' '||tx_obs1) END)";
                strSQL+="   , st_regRep='M'";
                strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ORD_CON) + "";
                strSQL+=" AND co_locRel=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ORD_CON) + "";
                strSQL+=" AND co_tipDocRel=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ORD_CON) + "";
                strSQL+=" AND co_docRel=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ORD_CON) + "";
                strSQL+=" AND co_regRel=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_REG_ORD_CON) + ";";
                //System.out.println("actualizarConteo.strSQLUpd: "+strSQLUpd);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;          
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_conInv", "tbh_conInv", "WHERE a1.fe_reaCon='" + strFecUltMod + "' AND a1.co_usrResCon=" + objParSis.getCodigoUsuario());
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
    * Función que genera contenedor con datos de los ítems que tendran movimientos en el disponible.
    * Solo se agregara disponible de los ítems que estan en el ingreso por importación.
    * <BR>Actualización en tbm_invBod</BR>
    * <PRE>
    *   nd_stkAct     (NO)
    *   nd_CanIngBod  (+)
    *   nd_CanDis     (-)
    * </PRE>
    * @return 
    */
    private boolean generaConItmTbmInvBod(int intFil)
    {
        boolean blnRes=true;
        String strCodLetItm="";
        int intCodItmGrp=0, intCodItmMae=0, intCodBodEmp=0, intCodBodGrp=0, intCodItmEmp=0;
        BigDecimal bgdCanConUsr= new BigDecimal ("0");
        BigDecimal bgdAuxDis= new BigDecimal ("0");
        BigDecimal bgdCanTotCon= new BigDecimal ("0");
        BigDecimal bgdCanIngImp= new BigDecimal ("0");
        BigDecimal bgdCanStkEmp= new BigDecimal ("0");
        BigDecimal bgdCanDisEmp= new BigDecimal ("0");
        try
        {
            if(con!=null)
            {
                arlDatStkInvItm = new ArrayList(); 
                
                //Se comenta validacion de fecha del pedido porque todos los pedidos se manejaran igual y en los ajustes se regulariza todo
                //if(validaFechaPedido(con, intFil))
                //{
                    intCodEmpPedImp=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP)==null?"":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP).toString().equals("")?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP).toString()));
                    //Datos del Item
                    intCodItmGrp = Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_GRP)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_GRP).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_GRP).toString()));
                    intCodItmMae = Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString()));
                    intCodItmEmp = objStkInv.getCodItmEmp(intCodEmpPedImp, intCodItmMae);
                    strCodLetItm = objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ALT_DOS)==null?"":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ALT_DOS).toString().equals("")?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ALT_DOS).toString());
                    intCodBodGrp = Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_BOD_ORD_CON)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_BOD_ORD_CON).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_BOD_ORD_CON).toString()));
                    intCodBodEmp = getCodigoBodegaEmpresa(intCodEmpPedImp, intCodBodGrp);

                    bgdCanStkEmp = BigDecimal.valueOf(objStkInv.getStkItmEmp(con, intCodItmMae, intCodBodGrp, intCodEmpPedImp ));
                    bgdCanDisEmp = BigDecimal.valueOf(objStkInv.getDisItmEmp(con, intCodItmMae, intCodBodGrp, intCodEmpPedImp ));
                    
                    bgdCanConUsr = BigDecimal.valueOf(Double.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR).toString()) );
                    bgdCanTotCon = BigDecimal.valueOf(Double.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_CON_TOT).toString()) );
                    bgdCanIngImp = BigDecimal.valueOf(Double.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING_IMP).toString()) );
                    
                    //Se valida que solo se pueda actualizar en tbm_invBod los ítems que aparecen en el INIMPO.
                    if( bgdCanIngImp.compareTo(new BigDecimal("0"))> 0 )
                    {
                        //Formula para Can.Dis. y Can.Ing.Bod. a actualizar en tbm_invBod
                        bgdAuxDis = bgdCanIngImp.compareTo(bgdCanTotCon)>0 ? bgdCanTotCon : bgdCanIngImp;
                        bgdAuxDis = bgdAuxDis.subtract(bgdCanConUsr);
                        bgdAuxDis=bgdAuxDis.compareTo(new BigDecimal("0"))<=0 ? new BigDecimal("0"): bgdAuxDis;
                    }
                    else
                    {
                        System.out.println("NO EXISTE EN INIMPO");
                        bgdAuxDis=new BigDecimal("0");
                    }
                    System.out.println("bgdAuxDis: "+bgdAuxDis);

                    //Si la cantidad a actualizar en tbm_invBod es mayor a 0.
                    if( bgdAuxDis.compareTo(new BigDecimal("0"))> 0 )
                    {
                        //Valida que Disponible no sea mayor que el stock. 
                        if( bgdCanStkEmp.compareTo(bgdCanDisEmp.subtract(bgdAuxDis))>= 0 )
                        {
                            if(intCodItmEmp==0 || intCodItmGrp==0 || intCodItmMae==0 || intCodBodEmp==0 || strCodLetItm.equals(""))
                            {
                                //Error...Datos a enviar en el arreglo arlDatStkInvItm estan incorrectos.
                                System.out.println("Error...Datos a enviar en el arreglo arlDatStkInvItm estan incorrectos.");
                                blnRes=false;
                            }
                            else
                            {
                                arlRegStkInvItm = new ArrayList();
                                arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP, intCodItmGrp);
                                arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP, intCodItmEmp);
                                arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE, intCodItmMae);
                                arlRegStkInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodLetItm);
                                arlRegStkInvItm.add(INT_ARL_STK_INV_CAN_ITM, bgdAuxDis );
                                arlRegStkInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP, intCodBodEmp );
                                arlDatStkInvItm.add(arlRegStkInvItm);
                                System.out.println("OKK generaConItmTbmInvBod");
                            }
                        }
                        else
                        {
                            //Error...Stock es menor al disponible.
                            System.out.println("Error...Stock es menor al disponible.");
                            blnRes=false;
                        }
                    }
                    else
                        System.out.println("Error...La cantidad a actualizar en tbm_invBod es menor a 0.");
                //}
                //else
                    //System.out.println("Pedido Antiguo");
                System.out.println("arlDatStkInvItm: "+arlDatStkInvItm);
            }
        }
        catch(Exception e)
        {
           objUti.mostrarMsgErr_F1(null, e);
           blnRes=false;
        }
        return blnRes;
    } 
   
    
       
    /**
     * Valida si el pedido fue ingresado en el esquema anterior o en el nuevo.
     * Valida que no sean pedidos antiguos, ya que estos han incrementado el disponible cuando se realizó el ingreso por importación.
     * @return TRUE: Si pedido esquema nuevo.
     * false: Esquema antiguo, antes del 13Sep2017
     */
    private boolean validaFechaPedido(java.sql.Connection conn, int intFil) 
    {
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        boolean blnRes=false;
        try
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                strSQL ="";
                strSQL+=" SELECT fe_doc, fe_ing FROM tbm_cabMovInv ";
                strSQL+=" WHERE st_reg IN ('A')";
                strSQL+=" AND co_emp=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP) + "";
                strSQL+=" AND co_loc=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP) + "";
                strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_ING_IMP) + "";
                strSQL+=" AND co_doc=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP) + "";
                strSQL+=" AND CAST(fe_ing AS DATE)>='"+strFecEsqAnt+"'";
                //System.out.println("validaFechaPedido: "+strSQL);
                
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()) 
                {
                    //Esquema Nuevo
                    blnRes=true;  
                }
            }
            rstLoc.close();
            stmLoc.close();
            rstLoc=null;
            stmLoc=null;
        }
        catch (java.sql.SQLException e) { blnRes=false;   objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
        
    private int getCodigoBodegaEmpresa(int intCodEmp, int intCodBodGrp)
    {
        int intCodBodEmp=0;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null)
            {
                stmLoc=conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT co_bod as co_bodEmp FROM tbr_bodEmpBodGrp as a ";
                strSQL+=" WHERE a.co_empGrp ="+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a.co_bodGrp ="+intCodBodGrp;
                strSQL+=" AND a.co_Emp ="+intCodEmp;
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                {
                    intCodBodEmp=rstLoc.getInt("co_bodEmp");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            intCodBodEmp=0;
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            intCodBodEmp=0;
        }
        return intCodBodEmp;
    }
    
    
    
    
    
   

}

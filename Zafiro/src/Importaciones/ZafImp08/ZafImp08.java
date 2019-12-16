/*
 * ZafImp08.java
 *
 * Created on 29 de mayo de 2014, 12:07 PM
 */
package Importaciones.ZafImp08;
import Librerias.ZafImp.ZafAjuInv;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafImp.ZafSegAjuInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
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
 * @author  José Marín M.
 */
public class ZafImp08 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    private static final int INT_TBL_DAT_LIN=0;                         //Línea
    private static final int INT_TBL_DAT_CODSEG = 1;                    //Código de Seguimiento.
    private static final int INT_TBL_DAT_COD_ITM_GRP=2;                 //Código del item del grupo
    private static final int INT_TBL_DAT_COD_ITM=3;                     //Código del item. de la empresa
    private static final int INT_TBL_DAT_COD_ITM_MAE=4;                 //Muestra ventana con información de conteos realizados. 
    private static final int INT_TBL_DAT_COD_ALT=5;                     //Código alterno del item.
    private static final int INT_TBL_DAT_COD_ALT_HIS=6;                 //Código alterno del item (Histórico).
    private static final int INT_TBL_DAT_COD_ALT2=7;                    //Código alterno dos del item.
    private static final int INT_TBL_DAT_NOM_ITM=8;                     //Nombre del item.
    private static final int INT_TBL_DAT_UNI_MED=9;                     //Unidad de medida.
    //Ingreso por importación
    private static final int INT_TBL_DAT_COD_IMP=10;                    //Codigo del importador.
    private static final int INT_TBL_DAT_NOM_IMP=11;                    //Nombre del importador.
    //Ingreso por importación
    private static final int INT_TBL_DAT_COD_EMP_ING_IMP=12;            //Muestra ventana con información de conteos realizados.
    private static final int INT_TBL_DAT_COD_LOC_ING_IMP=13;            //Muestra ventana con información de conteos realizados.
    private static final int INT_TBL_DAT_COD_TIP_ING_IMP=14;            //Muestra ventana con información de conteos realizados.
    private static final int INT_TBL_DAT_COD_DOC_ING_IMP=15;            //Codigo documento del ingreso por importación .
    private static final int INT_TBL_DAT_COD_REG_ING_IMP=16;            //Codigo registro del ingreso por importación .
    private static final int INT_TBL_DAT_NUM_DOC_ING_IMP=17;            //Número del ingreso por importación 
    private static final int INT_TBL_DAT_NUM_PED_ING_IMP=18;            //Número del pedido.    
    private static final int INT_TBL_DAT_FEC_ING_IMP=19;                //Fecha del ingreso por importación
    //Cantidades Pedido
    private static final int INT_TBL_DAT_CAN_PED_EMB=20;                //Cantidad de pedido embarcado.
    private static final int INT_TBL_DAT_CAN_ING_IMP=21;                //Cantidad del ingreso por importación .
    //Orden de conteo
    private static final int INT_TBL_DAT_COD_EMP_ORD_CON=22;            //CODIGO PARA EL CONTEO 
    private static final int INT_TBL_DAT_COD_LOC_ORD_CON=23;            //CODIGO PARA EL CONTEO 
    private static final int INT_TBL_DAT_COD_TIP_DOC_ORD_CON=24;        //CODIGO PARA EL CONTEO 
    private static final int INT_TBL_DAT_COD_DOC_ORD_CON=25;            //CODIGO PARA EL CONTEO 
    //Cantidades Contadas
    private static final int INT_TBL_DAT_CAN_CON_BUE_EST=26;            //Cantidad contada buen estado por el inventarista.  // CONTEOS
    private static final int INT_TBL_DAT_CAN_CON_MAL_EST=27;            //Cantidad contada por el inventarista.  // CONTEOS
    private static final int INT_TBL_DAT_BUT_ANE_CON=28;                //Muestra ventana con información de conteos realizados.
    //Cantidad de solicitud de transferencias.
    private static final int INT_TBL_DAT_CAN_SOLTRA=29;                 //Cantidad solicitada a transferir.  //SOTRIN
    private static final int INT_TBL_DAT_BUT_ANE_SOL=30;                //Muestra ventana con información de solicitudes de transferencias asociadas.
    //Cantidad de transferencia
    private static final int INT_TBL_DAT_CAN_TRA=31;                    //Cantidad transferida.  //  TRANS
    private static final int INT_TBL_DAT_BUT_ANE_TRA=32;                //Muestra ventana con información de transferencias asociadas.
    //Cantidades Documentos de Ajustes.
    private static final int INT_TBL_DAT_CAN_AJU=33;                    //Sumatoria total de las cantidades de ingresos por documentos de ajustes.
    private static final int INT_TBL_DAT_BUT_ANE_AJU=34;                //Muestra ventana con información de los documentos de ajustes realizados.
    //Cantidad de solicitud de transferencias.(Ajuste)
    private static final int INT_TBL_DAT_CAN_SOL_AJU=35;                //Sumatoria total de las cantidades de egresos por documentos de ajustes.
    //Chk de documentos de ajustes.
    private static final int INT_TBL_DAT_CHK_AJU=36;                    //Check para indicar si tiene o no documentos de ajustes.
    private static final int INT_TBL_DAT_EST_ING=37;                    //Estado que indica si tiene o no documentos de ajustes.  

    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblColFon;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoEmp;                                   //Ventana de consulta "Empresa".
    private ZafVenCon vcoPed;                                   //Ventana de consulta "Pedidos".
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafPerUsr objPerUsr;
    private ZafImp objImp;
    
    private ZafTblCelRenBut objTblCelRenButAneTrs;              //Render: Presentar JButton en JTable - Muestra ventana con información de transferencias asociadas.
    private ZafTblCelRenBut objTblCelRenButAneCon;              //Render: Presentar JButton en JTable - Muestra ventana con información de conteos realizados.
    private ZafTblCelRenBut objTblCelRenButAneSol;              //Render: Presentar JButton en JTable - Muestra ventana con información de solicitudes de transferencias asociadas.
    private ZafTblCelRenBut objTblCelRenButAneAju;              //Render: Presentar JButton en JTable - Muestra ventana con documentos de ajustes asociados.

    private ZafTblCelEdiButGen objTblCelEdiButGenAneTrs;        //Editor: Muestra ventana con información de transferencias asociadas.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneCon;        //Editor: Muestra ventana con información de conteos realizados.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneSol;        //Editor: Muestra ventana con información de solicitud de transferencias asociadas.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneAju;        //Editor: Muestra ventana con información de documentos de ajustes asociados.
    
    private ZafTblCelRenChk objTblCelRenChkDocAju;              //Render: Presentar JCheckBox en JTable.
    
    private ZafImp08_01 objImp08_01;                            //Muestra ventana con información de conteos realizados.
    private ZafImp08_02 objImp08_02;                            //Muestra ventana con información de solicitudes de transferencias asociadas.        
    private ZafImp08_03 objImp08_03;                            //Muestra ventana con información de transferencias asociadas.
    private ZafAjuInv objAjuInv;   
    private ZafSegAjuInv objSegAjuInv;
    
    private final Color colFonCol =new Color(228,228,203);//Color para las celdas
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private int intCodEmpIngImp;
    private int intCodLocIngImp;
    private int intCodTipDocIngImp;
    private int intRowsDocAju=-1;
    private int intCodEmpAju, intCodLocAju, intCodTipDocAju,  intCodDocAju;
    private String strSQL, strAux;
    private String strCodEmp, strNomEmp;                        //Contenido del campo al obtener el foco.
    private String strCodPed, strPedImp;                        //Contenido del campo al obtener el foco.
    private String strCodAlt, strCodAlt2, strNomItm,strNumPed;  //Contenido del campo al obtener el foco.
    
    private String strVer=" v0.9.13";

    /** Crea una nueva instancia de la clase . */
    public ZafImp08(ZafParSis obj) 
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
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objTblCelRenButAneTrs=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            objTblCelRenButAneCon=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            objTblCelRenLblColFon = new ZafTblCelRenLbl();

            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                       
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3221))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3222))
            {
                butCer.setVisible(false);
            }
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            panFecDoc.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            //Configurar objetos.
            txtCodItmMae.setVisible(false);
            txtCodItm.setVisible(false);
            //txtCodPed.setVisible(false);
            
            //Configurar las ZafVenCon.
            configurarVenConEmpImp();
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
            vecCab=new Vector(38);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP,"Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_HIS,"Cód.Alt.Itm.(His)");
            vecCab.add(INT_TBL_DAT_COD_ALT2,"Cód.Alt.2");
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
            vecCab.add(INT_TBL_DAT_CAN_CON_BUE_EST,"Can.Con.Bue.Est.");
            vecCab.add(INT_TBL_DAT_CAN_CON_MAL_EST,"Can.Con.Mal.Est.");
            vecCab.add(INT_TBL_DAT_BUT_ANE_CON,"..");
            vecCab.add(INT_TBL_DAT_CAN_SOLTRA,"Can.Sol.Tra.");    
            vecCab.add(INT_TBL_DAT_BUT_ANE_SOL,"..");  
            vecCab.add(INT_TBL_DAT_CAN_TRA,"Can.Tra."); 
            vecCab.add(INT_TBL_DAT_BUT_ANE_TRA,"..");
            vecCab.add(INT_TBL_DAT_CAN_AJU,"Can.Aju.");
            vecCab.add(INT_TBL_DAT_BUT_ANE_AJU,"..");  
            vecCab.add(INT_TBL_DAT_CAN_SOL_AJU,"Can.Sol.Aju.");
            vecCab.add(INT_TBL_DAT_CHK_AJU,"Est.Aju."); 
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
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_HIS).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setPreferredWidth(60); // CODIGO PARA LAS TRANSFERENCIAS
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setPreferredWidth(60); // CODIGO PARA LAS TRANSFERENCIAS
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_ING_IMP).setPreferredWidth(60); // CODIGO PARA LAS TRANSFERENCIAS
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_ING_IMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ING_IMP).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ORD_CON).setPreferredWidth(60); // CODIGO PARA LOS CONTEO
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ORD_CON).setPreferredWidth(60); // CODIGO PARA LOS CONTEO
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ORD_CON).setPreferredWidth(60); // CODIGO PARA LOS CONTEO
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ORD_CON).setPreferredWidth(60); // CODIGO PARA LOS CONTEO
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_BUE_EST).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_MAL_EST).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOLTRA).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_SOL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_AJU).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOL_AJU).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AJU).setPreferredWidth(28); 
            tcmAux.getColumn(INT_TBL_DAT_EST_ING).setPreferredWidth(28); 
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);      // CODIGO PARA LAS TRANSFERENCIAS
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ING_IMP, tblDat);  // CODIGO PARA LAS TRANSFERENCIAS
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ING_IMP, tblDat);  // CODIGO PARA LAS TRANSFERENCIAS
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_ING_IMP, tblDat);  // CODIGO PARA LAS TRANSFERENCIAS
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ORD_CON, tblDat);  //CODIGO PARA LOS CONTEO
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ORD_CON, tblDat);  //CODIGO PARA LOS CONTEO
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ORD_CON, tblDat);  //CODIGO PARA LOS CONTEO
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ORD_CON, tblDat); //CODIGO PARA LOS CONTEO
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON_MAL_EST, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_SOL_AJU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_AJU, tblDat);  
            if(objParSis.getCodigoUsuario()!=1)
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ING, tblDat);  
            }
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.          
            // SE OCULTAN LAS COLUMNAS SEGUN PERMISOS 
            if(!objPerUsr.isOpcionEnabled(3954)){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_PED_EMB, tblDat);
            }
            if(!objPerUsr.isOpcionEnabled(3955)){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_IMP, tblDat);
            }
            if(!objPerUsr.isOpcionEnabled(3956)){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON_BUE_EST, tblDat);
                //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON_MAL_EST, tblDat);
                //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_CON, tblDat);
                chkItmCanCon.setVisible(false);
            }
            if(!objPerUsr.isOpcionEnabled(3957)){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_SOLTRA, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_SOL, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TRA, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_TRA, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_AJU, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_AJU, tblDat);
                //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_SOL_AJU, tblDat);
                chkItmCanTra.setVisible(false);
                chkItmPenTra.setVisible(false);
            }

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PED_EMB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_BUE_EST).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_MAL_EST).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOLTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOL_AJU).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT2).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_CON);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_SOL);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_TRA);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_AJU);
            objTblMod.setColumnasEditables(vecAux);
            
            objTblCelRenButAneCon=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON).setCellRenderer(objTblCelRenButAneCon);
            objTblCelRenButAneCon=null;
            
            objTblCelRenButAneSol=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_SOL).setCellRenderer(objTblCelRenButAneSol);
            objTblCelRenButAneSol=null;
            
            objTblCelRenButAneTrs=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRA).setCellRenderer(objTblCelRenButAneTrs);
            objTblCelRenButAneTrs=null;
            
            objTblCelRenButAneAju=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_AJU).setCellRenderer(objTblCelRenButAneAju);
            objTblCelRenButAneAju=null;
                        
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDocAju=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AJU).setCellRenderer(objTblCelRenChkDocAju);
            objTblCelRenChkDocAju=null;            
            
            //Agregar colores a la fila donde se encuentre un conteo y no haya un Ingreso por Importación 
            objTblCelRenLblColFon.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                //pintar las filas 
                int intNumFil = tblDat.getSelectedRow();
                String strCanCon = (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_CAN_CON_BUE_EST).toString());
                String strCanPedEmb = (objTblMod.getValueAt(intNumFil, INT_TBL_DAT_CAN_PED_EMB).toString());
                if(strCanPedEmb.length()<0 && strCanCon.length()>=0) {
                    objTblCelRenLblColFon.setBackground(colFonCol);
                    objTblCelRenLblColFon.setForeground(Color.BLACK);
                    objTblCelRenLblColFon.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                    objTblCelRenLblColFon.setTipoFormato(1);
                    objTblCelRenLblColFon.setFormatoNumerico("###,###.##",false,true);
                }
             }
            });
        
            //Anexo 1: Conteos             
            objTblCelRenButAneCon=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_CON).setCellRenderer(objTblCelRenButAneCon);
            objTblCelRenButAneCon.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButAneCon.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ANE_CON:
                           if (objTblMod.getValueAt(objTblCelRenButAneCon.getRowRender(), INT_TBL_DAT_CAN_CON_BUE_EST)==null )
                               objTblCelRenButAneCon.setText("");
                            else
                               objTblCelRenButAneCon.setText("...");
                        break;
                    }
                }
            });
            objTblCelEdiButGenAneCon=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON).setCellEditor(objTblCelEdiButGenAneCon);
            objTblCelEdiButGenAneCon.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CON_BUE_EST)==null )
                       objTblCelEdiButGenAneCon.setCancelarEdicion(true);
                 }
                 public void afterEdit(ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    String strEmp, strLoc, strTipDoc, strDoc, strItm;
                    if (tblDat.getValueAt(intCol, INT_TBL_DAT_CAN_CON_BUE_EST)!=null){
                        strEmp=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_EMP_ORD_CON).toString();
                        strLoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_LOC_ORD_CON).toString();
                        strTipDoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_TIP_DOC_ORD_CON).toString();
                        strDoc=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_DOC_ORD_CON).toString();
                        strItm=tblDat.getValueAt(intCol, INT_TBL_DAT_COD_ITM_MAE).toString();
                        mostrarVentanaAnexoConteo( strEmp, strLoc, strTipDoc, strDoc, strItm );
                    }
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
            
            //Anexo 4: Documentos de Ajustes
            objTblCelRenButAneAju=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_AJU).setCellRenderer(objTblCelRenButAneAju);
            objTblCelRenButAneAju.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButAneAju.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ANE_AJU:
                            if(objTblMod.isChecked(objTblCelRenButAneAju.getRowRender(), INT_TBL_DAT_CHK_AJU))
                                objTblCelRenButAneAju.setText("...");
                            else
                                objTblCelRenButAneAju.setText("");
                        break;
                    }
                }
            });
            objTblCelEdiButGenAneAju=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_AJU).setCellEditor(objTblCelEdiButGenAneAju);
            objTblCelEdiButGenAneAju.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AJU))
                        objTblCelEdiButGenAneAju.setCancelarEdicion(true);
                 }
                 public void afterEdit(ZafTableEvent evt) {
                    int intFil = tblDat.getSelectedRow();
                    if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AJU)){
                        mostrarVentanaAnexoAjuste(intFil );
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
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código del seguimiento";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_HIS:
                    strMsg="Código alterno del item (Histórico)";
                    break;                    
                case INT_TBL_DAT_COD_ALT2:
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
                case INT_TBL_DAT_CAN_CON_BUE_EST:
                    strMsg="Cantidad de Orden de Conteo en buen estado";
                    break;
                case INT_TBL_DAT_CAN_CON_MAL_EST:
                    strMsg="Cantidad de Orden de Conteo en mal estado";
                    break;
                case INT_TBL_DAT_BUT_ANE_CON:
                    strMsg="Muestra información de conteos realizados";
                    break;                    
                case INT_TBL_DAT_CAN_SOLTRA:
                    strMsg="Cantidad de Solicitud de Transferencia";
                    break;   
                case INT_TBL_DAT_BUT_ANE_SOL:
                    strMsg="Muestra información de solicitud de transferencias asociadas";
                    break;                     
                case INT_TBL_DAT_CAN_TRA:
                    strMsg="Cantidad de Transferencia";
                    break;  
                case INT_TBL_DAT_BUT_ANE_TRA:
                    strMsg="Muestra información de transferencias asociadas";
                    break;                          
                case INT_TBL_DAT_CAN_AJU:
                    strMsg="Cantidad Ajustada";
                    break;     
               case INT_TBL_DAT_BUT_ANE_AJU:
                    strMsg="Muestra información de documentos de ajuste asociados";
                    break;                      
                case INT_TBL_DAT_CAN_SOL_AJU:
                    strMsg="Cantidad de Solicitudes por documento de Ajustes.";
                    break;  
               case INT_TBL_DAT_CHK_AJU:
                    strMsg="Indica si tiene documentos de ajustes o no";
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
     * mostrar las "Empresas Importadora".
     */
    private boolean configurarVenConEmpImp()
    {
        boolean blnRes=true;
        try
        {
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
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol);
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
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+=" FROM tbm_cabMovInv AS a1";
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc ";
            strSQL+=" WHERE a1.st_Reg='A'  "; //Mostrar solo los pedidos activos, solicitado por AP.
            strSQL+=" AND a1.co_tipDoc in(select co_tipDoc from tbr_tiPDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN (" + objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") )";
            strSQL+=" AND a1.co_mnu IN (" + objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+")";
            strSQL+=" ORDER BY a1.fe_doc DESC";
            
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
            strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor";
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
    private boolean mostrarEmpImp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.setVisible(true);
                    if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodImp.getText()))
                    {
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtCodImp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomImp.getText()))
                    {
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtNomImp.setText(strNomEmp);
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
        panBusItmAct = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panBusItmHis = new javax.swing.JPanel();
        lblCodAltDesHis = new javax.swing.JLabel();
        txtCodAltDesHis = new javax.swing.JTextField();
        lblCodAltHasHis = new javax.swing.JLabel();
        txtCodAltHasHis = new javax.swing.JTextField();
        lblEmpImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        chkItmCanCon = new javax.swing.JCheckBox();
        chkItmCanTra = new javax.swing.JCheckBox();
        chkItmPenTra = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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
        txtCodAlt2.setBounds(230, 50, 50, 20);

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
        txtNomItm.setBounds(280, 50, 360, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 50, 20, 20);

        panBusItmAct.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panBusItmAct.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panBusItmAct.add(lblCodAltDes);
        lblCodAltDes.setBounds(20, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panBusItmAct.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 80, 20);

        lblCodAltHas.setText("Hasta:");
        panBusItmAct.add(lblCodAltHas);
        lblCodAltHas.setBounds(150, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panBusItmAct.add(txtCodAltHas);
        txtCodAltHas.setBounds(200, 20, 80, 20);

        panFil.add(panBusItmAct);
        panBusItmAct.setBounds(30, 72, 300, 45);

        panBusItmHis.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item (Histórico)"));
        panBusItmHis.setLayout(null);

        lblCodAltDesHis.setText("Desde:");
        panBusItmHis.add(lblCodAltDesHis);
        lblCodAltDesHis.setBounds(20, 20, 48, 20);

        txtCodAltDesHis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesHisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesHisFocusLost(evt);
            }
        });
        panBusItmHis.add(txtCodAltDesHis);
        txtCodAltDesHis.setBounds(60, 20, 80, 20);

        lblCodAltHasHis.setText("Hasta:");
        panBusItmHis.add(lblCodAltHasHis);
        lblCodAltHasHis.setBounds(150, 20, 48, 20);

        txtCodAltHasHis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasHisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasHisFocusLost(evt);
            }
        });
        panBusItmHis.add(txtCodAltHasHis);
        txtCodAltHasHis.setBounds(200, 20, 80, 20);

        panFil.add(panBusItmHis);
        panBusItmHis.setBounds(350, 72, 300, 45);

        lblEmpImp.setText("Importador:");
        lblEmpImp.setToolTipText("Empresa");
        panFil.add(lblEmpImp);
        lblEmpImp.setBounds(30, 120, 80, 20);

        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        panFil.add(txtCodImp);
        txtCodImp.setBounds(120, 120, 80, 20);

        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        panFil.add(txtNomImp);
        txtNomImp.setBounds(200, 120, 440, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFil.add(butImp);
        butImp.setBounds(640, 120, 20, 20);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("Cliente");
        panFil.add(lblPed);
        lblPed.setBounds(30, 140, 80, 20);

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
        txtCodPed.setBounds(75, 140, 45, 20);

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
        txtNumDocIngImp.setBounds(120, 140, 80, 20);

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
        txtPedIngImp.setBounds(200, 140, 440, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panFil.add(butPedImp);
        butPedImp.setBounds(640, 140, 20, 20);

        chkItmCanCon.setText("Mostrar sólo items con cantidad contada.");
        chkItmCanCon.setToolTipText("");
        chkItmCanCon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkItmCanConItemStateChanged(evt);
            }
        });
        panFil.add(chkItmCanCon);
        chkItmCanCon.setBounds(30, 170, 340, 20);

        chkItmCanTra.setText("Mostrar sólo items con cantidad transferida.");
        chkItmCanTra.setToolTipText("");
        chkItmCanTra.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkItmCanTraItemStateChanged(evt);
            }
        });
        panFil.add(chkItmCanTra);
        chkItmCanTra.setBounds(30, 190, 320, 20);

        chkItmPenTra.setText("Mostrar items que tengan cantidad pendiente por transferir.");
        chkItmPenTra.setToolTipText("");
        chkItmPenTra.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkItmPenTraItemStateChanged(evt);
            }
        });
        panFil.add(chkItmPenTra);
        chkItmPenTra.setBounds(30, 210, 610, 20);

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

        setBounds(0, 0, 700, 450);
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
            else   {
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

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodEmp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodImp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
            }
            else {
                mostrarEmpImp(1);
            }
        }
        else
            txtCodImp.setText(strCodEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodImpFocusLost

    private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
        txtNomImp.transferFocus();
    }//GEN-LAST:event_txtNomImpActionPerformed

    private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
        strNomEmp=txtNomImp.getText();
        txtNomImp.selectAll();
    }//GEN-LAST:event_txtNomImpFocusGained

    private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomImp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
            }
            else
            {
                mostrarEmpImp(2);
            }
        }
        else
            txtNomImp.setText(strNomEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);            
        }
    }//GEN-LAST:event_txtNomImpFocusLost

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        mostrarEmpImp(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butImpActionPerformed

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
            else  {
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
            txtCodImp.setText("");
            txtNomImp.setText("");
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
            txtCodAltDesHis.setText("");
            txtCodAltHasHis.setText("");            
            chkItmCanCon.setSelected(false);
            chkItmCanTra.setSelected(false);
            chkItmPenTra.setSelected(false);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void chkItmCanConItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkItmCanConItemStateChanged
        if (chkItmCanCon.isSelected() && !optFil.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkItmCanConItemStateChanged

    private void chkItmCanTraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkItmCanTraItemStateChanged
        if (chkItmCanTra.isSelected() && !optFil.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkItmCanTraItemStateChanged

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
            else  {
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
            else {
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

    private void chkItmPenTraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkItmPenTraItemStateChanged
        if (chkItmPenTra.isSelected() && !optFil.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkItmPenTraItemStateChanged

    private void txtCodAltDesHisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesHisFocusGained
        txtCodAltDesHis.selectAll();
    }//GEN-LAST:event_txtCodAltDesHisFocusGained

    private void txtCodAltDesHisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesHisFocusLost
        if (txtCodAltDesHis.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            if (txtCodAltHasHis.getText().length()==0)
                txtCodAltHasHis.setText(txtCodAltDesHis.getText());
        }
    }//GEN-LAST:event_txtCodAltDesHisFocusLost

    private void txtCodAltHasHisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasHisFocusGained
        txtCodAltHasHis.selectAll();
    }//GEN-LAST:event_txtCodAltHasHisFocusGained

    private void txtCodAltHasHisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasHisFocusLost
        if (txtCodAltHasHis.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltHasHisFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butPedImp;
    private javax.swing.JCheckBox chkItmCanCon;
    private javax.swing.JCheckBox chkItmCanTra;
    private javax.swing.JCheckBox chkItmPenTra;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltDesHis;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltHasHis;
    private javax.swing.JLabel lblEmpImp;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBusItmAct;
    private javax.swing.JPanel panBusItmHis;
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
    private javax.swing.JTextField txtCodAltDesHis;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltHasHis;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodItmMae;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNomImp;
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
        
         objImp08_03=new ZafImp08_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         objImp08_03.show();
     }
         
     private void mostrarVentanaAnexoConteo(String strEmp, String strLoc, String strTipDoc, String strDoc, String strItm )
     {
         int intEmp, intLoc, intTipDoc, intDoc, intItmMae;
         
         intEmp=Integer.parseInt(strEmp);
         intLoc=Integer.parseInt(strLoc);
         intTipDoc=Integer.parseInt(strTipDoc);
         intDoc=Integer.parseInt(strDoc);
         intItmMae= Integer.parseInt(strItm);
        
         objImp08_01=new ZafImp08_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         objImp08_01.show();
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
        
         objImp08_02=new ZafImp08_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         objImp08_02.show();
     }
     
    /*
     * Muestra la ventana del boton de cuales son los documentos de ajustes asociados.
     * 
     */
    private void mostrarVentanaAnexoAjuste(int fila)
    {
        try
        {
            if(getDocumentosAjuste(fila))
            {
                //if(intRowsDocAju==1)
                //{
                //    con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                //    if (con != null)
                //    {
                //        //Se carga el Ajuste de Inventario
                //        objAjuInv = new ZafAjuInv( javax.swing.JOptionPane.getFrameForComponent(this), objParSis, con, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, objImp.INT_COD_MNU_PRG_AJU_INV, 'c'); 
                //        objAjuInv.show();
                //        //this.getParent().add(objAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                //        //objAjuInv.setVisible(true);
                //        objAjuInv=null; 
                //
                //        con.close();
                //        con=null;
                //    }
                //}
                //else
                //{
                    //Se carga el seguimiento de documento de ajustes.
                    objSegAjuInv=new ZafSegAjuInv(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strSQL, true);
                    objSegAjuInv.show();
                    objSegAjuInv=null;
                                    
                    //this.getParent().add(objSegAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                    //objSegAjuInv.setVisible(true);
                //}
            }
        }
        //catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e){    objUti.mostrarMsgErr_F1(this, e);   }
    }  
    
    private boolean getDocumentosAjuste(int fila)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc ";      
                strSQL+="      , a.fe_doc, a.tx_numDoc2, a.st_aut, a.co_Reg, a.co_itmMae, a.nd_Can, a1.nd_canSol, a2.nd_canTrs";
                strSQL+=" FROM (";
                strSQL+="    SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc";
                strSQL+="          , a.fe_doc, a.tx_numDoc2, a.st_aut, a2.co_Reg, a3.co_itmMae, a2.nd_Can ";
                strSQL+="    FROM ( ";
                strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar ";
                strSQL+="            , a1.ne_numDoc, a1.fe_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a1.st_aut, a1.tx_numDoc2";
                strSQL+="            ";
                //strSQL+="           , CASE WHEN a1.st_ingImp IS NULL THEN 'P' ELSE CASE WHEN a1.st_ingImp ='T' THEN 'A'";
                //strSQL+="            ELSE CASE WHEN a1.st_ingImp IN ('D','C') THEN 'D' ELSE 'P' END END END AS st_aut ";    
                strSQL+="       FROM tbr_cabMovInv as a ";
                strSQL+="       INNER JOIN tbm_cabMovInv as a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc ";
                strSQL+="       INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc ";
                strSQL+="       WHERE a1.st_reg NOT IN('E'/*,'I'*/) ";  //Se comenta para que presente los ajustes denegados, los mismos que tienen st_Reg='I' 31Ago2017
                strSQL+="       AND a1.co_tipDoc in (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="                            and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";
                strSQL+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar ";
                strSQL+="              , a1.ne_numDoc, a1.fe_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a1.st_ingImp , a1.tx_numDoc2";
                strSQL+="    ) as a ";
                strSQL+="    INNER JOIN tbm_CabMovInv as a1 ON a1.co_emp=a.co_empRel AND a1.co_loc=a.co_locRel AND a1.co_tipDoc=a.co_tipDocRel AND a1.co_doc=a.co_docRel ";
                strSQL+="    INNER JOIN tbm_detMovInv as a2 ON a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc AND a2.co_doc=a.co_doc ";
                strSQL+="    INNER JOIN tbm_equInv as a3 ON (a3.co_emp=a1.co_emp AND a3.co_itm=a2.co_itm) ";   
                strSQL+="    WHERE a1.co_emp=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP_ING_IMP); 
                strSQL+="    AND a1.co_loc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC_ING_IMP);
                strSQL+="    AND a1.co_tipDoc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_ING_IMP);
                strSQL+="    AND a1.co_doc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC_ING_IMP);
                strSQL+="    AND a3.co_itmMae=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_ITM_MAE);
                strSQL+="    AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                //strSQL+="    GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc";
                //strSQL+="           , a.fe_doc, a.tx_numDoc2, a.st_aut, a2.co_Reg, a3.co_itmMae, a2.nd_Can ";
                //strSQL+="    ORDER BY a.tx_numDoc2, a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a2.co_reg";
                strSQL+=" ) as a ";
                
                strSQL+=" LEFT OUTER JOIN "; /* Solicitudes de Transferencias */
                strSQL+=" (";
                strSQL+="    SELECT a1.co_itmMae, a2.co_empRelCabMovInv as co_empRel, a2.co_locRelCabMovInv as co_locRel";
                strSQL+="         , a2.co_tipDocRelCabMovInv AS co_tipDocRel, a2.co_docRelCabMovInv AS co_docRel, SUM (a.nd_Can) as nd_canSol";
                strSQL+="    FROM tbm_detSolTraInv as a";
                strSQL+="    INNER JOIN tbm_equInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm)";
                strSQL+="    INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ON (a2.co_empRelCabSolTraInv=a.co_emp AND a2.co_locRelCabSolTraInv=a.co_loc AND a2.co_tipDocRelCabSolTraInv=a.co_tipDoc AND a2.co_docRelCabSolTraInv=a.co_doc)";
                strSQL+="    INNER JOIN tbm_CabSolTraInv as a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc AND a3.co_tipDoc=a.co_tipDoc AND a3.co_doc=a.co_doc)";
                strSQL+="    WHERE a3.st_Reg IN ('A') ";
                strSQL+="    AND a.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="                        and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu IN (" + objImp.INT_COD_MNU_PRG_SOTRINI+", "+objImp.INT_COD_MNU_PRG_SOTRINC+")";
                strSQL+="    AND a2.co_tipDocRelCabMovInv IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="                                     and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";
                strSQL+="    AND a1.co_itmMae=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_ITM_MAE); 
                strSQL+="    GROUP BY a1.co_itmMae, a2.co_empRelCabMovInv, a2.co_locRelCabMovInv, a2.co_tipDocRelCabMovInv, a2.co_docRelCabMovInv";
                strSQL+=" ) AS a1 ON a1.co_EmpRel=a.co_emp AND a1.co_locRel=a.co_loc AND a1.co_tipDocRel=a.co_tipDoc AND a1.co_docRel=a.co_Doc AND a1.co_itmMae=a.co_itmMae";
                 
                strSQL+=" LEFT OUTER JOIN "; /* Transferencias */
                strSQL+=" ( ";
                strSQL+="    SELECT a1.co_itmMae, a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, ABS (SUM (a.nd_Can)) as nd_CanTrs ";
                strSQL+="    FROM tbm_detMovInv as a ";
                strSQL+="    INNER JOIN tbm_equInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) ";  
                strSQL+="    INNER JOIN tbr_cabMovInv as a2 ON (a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc AND a2.co_doc=a.co_doc) ";
                strSQL+="    INNER JOIN tbm_CabMovInv as a3 ON (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc AND a3.co_tipDoc=a.co_tipDoc AND a3.co_doc=a.co_doc) ";
                strSQL+="    WHERE a3.st_reg IN ('A') AND a.nd_can<0 ";
                strSQL+="    AND a2.co_tipDocRel IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="                            and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";
                strSQL+="    AND a1.co_itmMae=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_ITM_MAE); 
                strSQL+="    GROUP BY a1.co_itmMae, a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel ";
                strSQL+=" ) AS a2 ON a2.co_EmpRel=a.co_emp AND a2.co_locRel=a.co_loc AND a2.co_tipDocRel=a.co_tipDoc AND a2.co_docRel=a.co_Doc AND a2.co_itmMae=a.co_itmMae ";
                System.out.println("ZafImp08.getDocumentosAjuste: "+strSQL);
                rst=stm.executeQuery(strSQL);
                intRowsDocAju=0;
                while(rst.next())
                {
                    intCodEmpAju = rst.getInt("co_emp");
                    intCodLocAju = rst.getInt("co_loc");
                    intCodTipDocAju = rst.getInt("co_tipDoc");
                    intCodDocAju = rst.getInt("co_doc");
                    intRowsDocAju=rst.getRow();
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e) {   blnRes= false;  objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e){  blnRes= false;   objUti.mostrarMsgErr_F1(this, e);  }
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
                            strAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND  a.fe_doc <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a.fe_doc >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 3: //Todo....
                                break;
                    }
                }

                stm=con.createStatement();
                //Obtener la condición.
                strSQL="";
                strSQL+=" SELECT a.* , Seg.co_Seg\n";
                strSQL+=" FROM (  \n";
                strSQL+="   SELECT c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_doc, c1.tx_codAlt, c1.tx_codAltHis, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_unimed, c1.co_itmMae, c1.co_itm \n";//co_itm es de la empresa y no del grupo
                strSQL+="        , c1.co_emp AS co_empIngImp, c1.co_loc AS co_locIngImp, c1.co_tipdoc AS co_tipdocIngImp, c1.co_doc AS co_docIngImp, c1.nd_can AS nd_canIngImp, c1.co_reg AS co_regIngImp\n";
                strSQL+="        , c1.ne_numDoc AS ne_numDocIngImp, c1.tx_numDoc2 AS tx_numDoc2IngImp, c1.fe_doc AS fe_docIngImp, c1.fe_doc, c1.co_emp AS co_impIngImp, c1.tx_nom AS tx_nomImpIngImp\n";
                strSQL+="        , c2.co_emp AS co_empPedEmb, c2.co_loc AS co_locPedEmb, c2.co_tipDoc AS co_tipDocPedEmb, c2.co_doc AS co_docPedEmb, c2.nd_can AS nd_canPedEmb\n";
                strSQL+="        , c3.co_emp AS co_empOrdCon, c3.co_loc AS co_locOrdCon, c3.co_tipdoc AS co_tipDocOrdCon, c3.co_doc AS co_docOrdCon, ABS(c3.nd_stkCon) AS nd_canOrdCon, ABS(c3.nd_canMalEst) AS nd_canMalEstOrdCon \n";
                strSQL+="        , c5.nd_canSol, ABS(c4.nd_can) AS nd_canTrs, c1.co_itmGrp, c1.st_reg, c1.st_ingImp, c1.st_tieAju, SUM(c1.nd_canAju) as nd_canAju\n";
                strSQL+="   FROM( \n";
                strSQL+="         SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.ne_numDoc, b1.tx_numDoc2, b1.fe_doc, b1.co_reg \n";
                strSQL+="              , b1.co_itmMae, b1.tx_codAlt, b1.tx_codAltHis, b1.tx_codAlt2, b1.tx_nomItm, b1.nd_can, b1.tx_unimed, b1.tx_nom \n";
                strSQL+="              , b1.co_empRelPedEmbImp, b1.co_locRelPedEmbImp, b1.co_tipdocRelPedEmbImp, b1.co_docRelPedEmbImp \n";
                strSQL+="	       , b1.co_itm, b3.co_itmGrp, b1.st_reg, b1.st_ingImp, CASE WHEN b2.co_empRel IS NULL THEN 'N' ELSE 'S' END as st_tieAju \n";
                strSQL+="	       , CASE WHEN b2.nd_canAju IS NULL THEN 0 ELSE b2.nd_canAju END as nd_canAju \n";
                strSQL+="           FROM( \n";           //Cantidades de Ingreso por Importación
                strSQL+="                 SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc, a2.co_reg \n";
                strSQL+="                      , a4.co_itmMae, a3.tx_codAlt, a2.tx_codAlt as tx_codAltHis, a3.tx_codAlt2, a3.tx_nomItm, a2.nd_can, a2.tx_unimed, a5.tx_nom \n";
                strSQL+="                      , a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipdocRelPedEmbImp, a1.co_docRelPedEmbImp, a3.co_itm, a1.st_reg \n";
                strSQL+="                      , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE CASE WHEN a1.st_ingImp = 'A' THEN 'S'  ELSE CASE WHEN a1.st_ingImp = 'B' THEN 'S' ";
                strSQL+="                        ELSE a1.st_ingImp END END END AS st_ingImp\n";
                strSQL+="                 FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_emp AS a5 ON a1.co_emp=a5.co_emp)\n";
                strSQL+="                 INNER JOIN tbm_detMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc \n";
                strSQL+="                 INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm \n";
                strSQL+="                 INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm \n";
                strSQL+="                 WHERE a1.st_reg='A' AND a1.co_mnu IN (" + objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") \n";               
                strSQL+="           ) AS b1\n";
                strSQL+="           LEFT OUTER JOIN(\n";  //Cantidades de Documentos de Ajustes.
                strSQL+="                SELECT a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a1.co_itm, a1.nd_can as nd_canAju\n";
                strSQL+="                FROM (tbr_cabMovInv  as a2 INNER JOIN tbm_CabMovInv as a ON a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc AND a2.co_doc=a.co_doc)\n";
                strSQL+="                INNER JOIN tbm_detMovInv as a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc\n";
                strSQL+="                WHERE a2.co_tipDocRel IN(select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN (" + objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") )\n";
                strSQL+="                AND a.co_tipDoc IN(select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") \n";
                strSQL+="                AND (CASE WHEN a1.st_Reg IS NULL THEN 'A' ELSE a1.st_Reg END ) NOT IN ('I') \n";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+="                /*AND a.st_Reg in ('A') */ AND a.st_Aut in ('A', 'P') \n"; //Se muestra la sumatoria de los ajustes pendientes de aprobar y aprobados. Los ajustes denegados no se muestran.
                strSQL+="           ) as b2 ON b1.co_emp=b2.co_empRel AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_itm=b2.co_itm\n";
                strSQL+="           INNER JOIN(\n";      
                strSQL+="                SELECT a1.co_emp, a1.co_itm AS co_itmGrp, a1.co_itmMae FROM tbm_equInv AS a1";
                strSQL+="                WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "\n";
                strSQL+="           ) AS b3 ON b1.co_itmMae=b3.co_itmMae \n";
                strSQL+="           GROUP BY b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.ne_numDoc, b1.tx_numDoc2, b1.fe_doc, b1.co_reg\n";
                strSQL+="                  , b1.co_itmMae, b1.tx_codAlt, b1.tx_codAltHis, b1.tx_codAlt2, b1.tx_nomItm, b1.nd_can, b1.tx_unimed, b1.tx_nom\n";
                strSQL+="                  , b1.co_empRelPedEmbImp, b1.co_locRelPedEmbImp, b1.co_tipdocRelPedEmbImp, b1.co_docRelPedEmbImp\n";
                strSQL+="	           , b1.co_itm, b3.co_itmGrp, b1.st_reg, b1.st_ingImp, b2.co_empRel, b2.nd_CanAju \n";
                strSQL+="   ) AS c1\n";
                //Pedidos Embarcados.
                strSQL+="   LEFT OUTER JOIN(\n";
                strSQL+="          SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.nd_can, a4.co_itmMae\n";
                strSQL+="          FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_detPedEmbImp AS a2\n";
                strSQL+="          ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc\n";
                strSQL+=" 	   INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm\n";
                strSQL+=" 	   INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm\n";
                strSQL+=" 	   WHERE a1.st_reg='A'\n";
                strSQL+="   ) AS c2 ON c1.co_empRelPedEmbImp=c2.co_emp AND c1.co_locRelPedEmbImp=c2.co_loc AND c1.co_tipdocRelPedEmbImp=c2.co_tipDoc AND c1.co_docRelPedEmbImp=c2.co_doc AND c1.co_itmMae=c2.co_itmMae\n";
                //Conteos.
                strSQL+="   LEFT OUTER JOIN(\n";
                strSQL+="	  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon, a8.nd_canMalEst, a6.co_itmMae, a2.co_itm\n";
                strSQL+="	       , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel\n";
                strSQL+=" 	  FROM tbm_cabOrdConInv AS a1\n";
                strSQL+=" 	  INNER JOIN (tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8\n";
                strSQL+=" 	              ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel )\n";
                strSQL+=" 	  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc\n";
                strSQL+=" 	  INNER JOIN (tbm_invBod AS a3 INNER JOIN (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)\n";
                strSQL+=" 		      INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)\n";
                strSQL+=" 	  ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm\n";
                strSQL+=" 	  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "\n";
                strSQL+=" 	  AND a1.co_tipDocRel IN(select co_tipDoc from tbr_tiPDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN (" + objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") )\n";
                strSQL+=" 	  AND a1.st_reg='A' \n";
                strSQL+="   ) AS c3 ON c1.co_emp=c3.co_empRel AND c1.co_loc=c3.co_locRel AND c1.co_tipDoc=c3.co_tipDocRel AND c1.co_doc=c3.co_docRel AND c1.co_itmMae=c3.co_itmMae \n";
                //Transferencias.
                strSQL+="   LEFT OUTER JOIN(\n";
                strSQL+=" 	  SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_can, a1.co_itmMae\n";
                strSQL+=" 	  FROM(\n";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_codAlt, a4.co_itmMae, a2.nd_can, a3.tx_codAlt2, a3.tx_nomItm\n";
                strSQL+=" 		     , a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel\n";
                strSQL+=" 		FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1 ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)\n";
                strSQL+=" 		INNER JOIN tbm_detMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc\n";
                strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm\n";
                strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm\n";
                strSQL+=" 		WHERE a2.nd_can<0 AND a1.st_Reg in ('A') \n";
                strSQL+="               AND a1.co_tipDoc NOT IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN ("+objImp.INT_COD_MNU_PRG_AJU_INV+") ) \n";
                strSQL+=" 	  ) AS a1\n"; 
                strSQL+=" 	  GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.co_itmMae\n";
                strSQL+="   ) AS c4 ON c1.co_emp=c4.co_empRel AND c1.co_loc=c4.co_locRel AND c1.co_tipDoc=c4.co_tipDocRel AND c1.co_doc=c4.co_docRel AND c1.co_itmMae=c4.co_itmMae\n";
                //Solicitudes de Transferencias.
                strSQL+="   LEFT OUTER JOIN(\n";
                strSQL+=" 	  SELECT a2.co_EmpRelCabMovInv, a2.co_locRelCabMovInv, a2.co_tipDocRelCabMovInv, a2.co_docRelCabMovInv, SUM(a1.nd_can) AS nd_canSol, equ.co_itmMae\n";  
                strSQL+=" 	  FROM tbm_CabSolTraInv as a\n";
                strSQL+=" 	  INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ON (a2.co_EmpRelCabSolTraInv=a.co_emp AND a2.co_locRelCabSolTraInv=a.co_loc AND a2.co_tipDocRelCabSolTraInv=a.co_tipdoc AND a2.co_docRelCabSolTraInv=a.co_Doc )\n";
                strSQL+=" 	  INNER JOIN tbm_detSolTraInv as a1 ON (a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipdoc AND a.co_doc=a1.co_Doc)\n";
                strSQL+=" 	  INNER JOIN tbm_equInv as equ ON (equ.co_emp=a.co_emp AND equ.co_itm=a1.co_itm)\n";
                strSQL+=" 	  WHERE a.st_reg in ('A') AND (CASE WHEN a.st_aut IS NULL THEN 'P' ELSE a.st_aut END) NOT IN ('D')\n";
                strSQL+=" 	  GROUP BY a2.co_EmpRelCabMovInv, a2.co_locRelCabMovInv, a2.co_tipDocRelCabMovInv, a2.co_docRelCabMovInv, equ.co_itmMae \n"; 
                strSQL+="   ) AS c5";
                strSQL+="   ON c1.co_emp=c5.co_EmpRelCabMovInv AND c1.co_loc=c5.co_locRelCabMovInv AND c1.co_tipDoc=c5.co_tipDocRelCabMovInv AND c1.co_doc=c5.co_docRelCabMovInv AND c1.co_itmMae=c5.co_itmMae\n";
                strSQL+="   GROUP BY c1.tx_codAlt, c1.tx_codAltHis, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_unimed, c1.co_itmMae, c1.co_itm, c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_doc, c1.nd_can, c1.co_reg\n";
                strSQL+="          , c1.ne_numDoc, c1.tx_numDoc2, c1.fe_doc, c1.co_emp, c1.tx_nom, c2.co_emp, c2.co_loc, c2.co_tipDoc, c2.co_doc, c2.nd_can\n";
                strSQL+="          , c3.co_emp, c3.co_loc, c3.co_tipdoc, c3.co_doc, c3.nd_stkCon, c3.nd_canMalEst, c5.nd_canSol, c4.nd_can, c1.co_itmGrp, c1.st_reg, c1.st_ingImp, c1.st_tieAju\n";
                
                //<editor-fold defaultstate="collapsed" desc="/* ITEMS CON AJUSTES Y QUE NO APARECEN EN EL INIMPO */">
                strSQL+="   UNION ALL \n";
                strSQL+="   SELECT c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_doc, c1.tx_codAlt, c1.tx_codAltHis, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_unimed, c1.co_itmMae, c1.co_itm\n";
                strSQL+="        , c1.co_empIngImp, c1.co_locIngImp, c1.co_tipdocIngImp, c1.co_docIngImp,0 as nd_canIngImp, c1.co_regIngImp\n";
                strSQL+="        , c1.ne_numDocIngImp, c1.tx_numDoc2IngImp, c1.fe_docIngImp, c1.fe_doc, c1.co_impIngImp, c1.tx_nomImpIngImp\n";
                strSQL+="        , 0 as co_empPedEmb, 0 AS co_locPedEmb, 0 AS co_tipDocPedEmb, 0 AS co_docPedEmb, 0 AS nd_canPedEmb \n";
                strSQL+="        , 0 AS co_empOrdCon, 0 AS co_locOrdCon, 0 AS co_tipDocOrdCon, 0 AS co_docOrdCon, 0 AS nd_canOrdCon, 0 AS nd_canMalEstOrdCon \n";
                strSQL+="        , c3.nd_canSol, ABS(c2.nd_canTrs) as nd_canTrs, c1.co_itmGrp, c1.st_reg, c1.st_ingImp, c1.st_tieAju \n";
                strSQL+="        , c1.nd_CanAju as nd_CanAju \n";
                strSQL+="   FROM (\n";
                strSQL+="            SELECT b1.*, b2.tx_nom as tx_nomImpIngImp, b3.co_itmGrp FROM (\n";
                strSQL+="   	  SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_empRel AS co_empIngImp, a2.co_locRel AS co_locIngImp \n";
                strSQL+="   	       , a2.co_tipDocRel AS co_tipdocIngImp, a2.co_docRel AS co_docIngImp, 0 AS co_regIngImp \n";      
                strSQL+="   	       , a1.co_itm, a4.co_itmMae, a3.tx_codAlt, a1.tx_codAlt as tx_codAltHis, a1.tx_codAlt2, a1.tx_nomItm \n";
                strSQL+="   	       , a1.nd_Can as nd_CanAju \n";
                strSQL+="   	       , CASE WHEN a1.tx_uniMed='Null' THEN '' ELSE a1.tx_uniMed END AS tx_uniMed, a5.tx_numDoc2 AS tx_numDoc2IngImp, a5.ne_numDoc AS ne_numDocIngImp, a5.fe_doc AS fe_docIngImp, a5.fe_doc\n";
                strSQL+="   	       , a2.co_empRel AS co_impIngImp, 'N' as st_tieAju, a.st_Reg, a.st_ingImp\n";
                strSQL+="   	  FROM (tbr_cabMovInv as a2 INNER JOIN tbm_CabMovInv as a ON a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_doc\n";
                strSQL+="   		INNER JOIN tbm_cabMovInv as a5 ON a5.co_emp=a2.co_empRel AND a5.co_loc=a2.co_locRel AND a5.co_tipDoc=a2.co_tipDocRel AND a5.co_doc=a2.co_docRel\n";        
                strSQL+="   	  )\n";
                strSQL+="   	  INNER JOIN tbm_detMovInv as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc)\n";
                strSQL+="   	  INNER JOIN tbm_inv AS a3 ON (a3.co_emp=a.co_emp AND a3.co_itm=a1.co_itm)\n";
                strSQL+="   	  INNER JOIN tbm_equInv AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_itm=a3.co_itm)\n";
                strSQL+="             WHERE a.st_Reg IN ('A') AND a2.co_tipDocRel IN(select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu IN (" + objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") ) \n";
                strSQL+="             AND a.co_tipDoc IN(select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") \n";
                strSQL+="   	  AND (CASE WHEN a1.st_Reg IS NULL THEN 'A' ELSE a1.st_Reg END ) NOT IN ('I')\n";   //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+="   	  AND NOT EXISTS (\n";
                strSQL+="   	       SELECT * FROM tbm_detMovInv as a6 \n";
                strSQL+="   	       INNER JOIN tbm_inv AS a7 ON (a7.co_emp=a6.co_emp AND a7.co_itm=a6.co_itm)\n";
                strSQL+="   	       INNER JOIN tbm_equInv AS a8 ON (a8.co_emp=a7.co_emp AND a8.co_itm=a7.co_itm)\n";
                strSQL+="   	       WHERE a6.co_emp=a2.co_empRel AND a6.co_loc=a2.co_locRel AND a6.co_tipDoc=a2.co_tipDocRel AND a6.co_doc=a2.co_docRel AND a8.co_itmMae=a4.co_itmMae\n";
                strSQL+="   	  )\n";
                strSQL+="           ) as b1\n";
                strSQL+="   	INNER JOIN tbm_emp as b2 ON b2.co_emp=b1.co_empIngImp\n";
                strSQL+="   	INNER JOIN(\n";
                strSQL+="   	      SELECT a1.co_emp, a1.co_itm AS co_itmGrp, a1.co_itmMae FROM tbm_equInv AS a1 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "\n";
                strSQL+="   	) AS b3 ON b1.co_itmMae=b3.co_itmMae\n"; 
                strSQL+="   ) as c1\n";
                strSQL+="   LEFT OUTER JOIN(\n";//Transferencias.
                strSQL+="           SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_canTrs, a1.co_itmMae\n";
                strSQL+="           FROM(\n";
                strSQL+="   	     SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_codAlt, a4.co_itmMae, a2.nd_can, a3.tx_codAlt2, a3.tx_nomItm\n";
                strSQL+="   	          , a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc,a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel\n";
                strSQL+="   	    FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1 ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)\n";
                strSQL+="   	    INNER JOIN tbm_detMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc\n";
                strSQL+="   	    INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm\n";
                strSQL+="   	    INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm\n";
                strSQL+="   	    WHERE a2.nd_can<0 AND a1.st_Reg in ('A') \n";
                strSQL+="               AND a1.co_tipDoc NOT IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") \n";
                strSQL+="           ) AS a1\n";
                strSQL+="           GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.co_itmMae\n";
                strSQL+="   ) AS c2 \n";
                strSQL+="   ON c1.co_emp=c2.co_empRel AND c1.co_loc=c2.co_locRel AND c1.co_tipDoc=c2.co_tipDocRel AND c1.co_doc=c2.co_docRel AND c1.co_itmMae=c2.co_itmMae\n";
                strSQL+="   LEFT OUTER JOIN(\n";//Solicitudes de Transferencias.
                strSQL+="     SELECT a2.co_EmpRelCabMovInv, a2.co_locRelCabMovInv, a2.co_tipDocRelCabMovInv, a2.co_docRelCabMovInv, SUM(a1.nd_can) AS nd_canSol, equ.co_itmMae\n";  
                strSQL+="     FROM tbm_CabSolTraInv as a\n";
                strSQL+="     INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ON (a2.co_EmpRelCabSolTraInv=a.co_emp AND a2.co_locRelCabSolTraInv=a.co_loc AND a2.co_tipDocRelCabSolTraInv=a.co_tipdoc AND a2.co_docRelCabSolTraInv=a.co_Doc )\n";
                strSQL+="     INNER JOIN tbm_detSolTraInv as a1 ON (a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipdoc AND a.co_doc=a1.co_Doc)\n";
                strSQL+="     INNER JOIN tbm_equInv as equ ON (equ.co_emp=a.co_emp AND equ.co_itm=a1.co_itm)\n";
                strSQL+=" 	  WHERE a.st_reg in ('A') AND (CASE WHEN a.st_aut IS NULL THEN 'P' ELSE a.st_aut END) NOT IN ('D')\n";
                strSQL+="     GROUP BY a2.co_EmpRelCabMovInv, a2.co_locRelCabMovInv, a2.co_tipDocRelCabMovInv, a2.co_docRelCabMovInv, equ.co_itmMae\n";  
                strSQL+="   ) AS c3\n";
                strSQL+="   ON c1.co_emp=c3.co_EmpRelCabMovInv AND c1.co_loc=c3.co_locRelCabMovInv AND c1.co_tipDoc=c3.co_tipDocRelCabMovInv AND c1.co_doc=c3.co_docRelCabMovInv AND c1.co_itmMae=c3.co_itmMae\n";

                //</editor-fold>
                
                strSQL+=" ) AS a \n";
                //Seguimiento de Inventario
                strSQL+=" LEFT OUTER JOIN( \n";
                strSQL+="      SELECT co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc FROM tbm_cabSegMovInv  as a \n ";
                strSQL+="      INNER JOIN tbm_cabMovInv as a1 ON (a1.co_emp=a.co_empRelCabMovInv AND a1.co_loc=a.co_locRelCabMovInv AND a1.co_tipDoc=a.co_tipDocRelCabMovInv AND a1.co_doc=a.co_docRelCabMovInv)\n ";
                strSQL+="      INNER JOIN tbr_tipDocPrg as a3 ON (a3.co_emp=a1.co_emp AND a3.co_loc=a1.co_loc AND a3.co_tipDoc=a1.co_tipDoc AND a3.co_mnu IN (" + objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") ) \n";
                strSQL+=" ) AS Seg ON a.co_empIngImp=Seg.co_emp AND a.co_locIngImp=Seg.co_loc AND a.co_tipdocIngImp=Seg.co_tipDoc AND a.co_docIngImp=Seg.co_doc\n";
                
                //Filtros
                strSQL+=" WHERE a.st_Reg='A' \n";  
                if (txtCodItmMae.getText().length() > 0) {
                    strSQL+=" AND a.co_itmMae=" + txtCodItmMae.getText() +"\n";  
                }
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0){
                    strSQL+=" AND ((LOWER(a.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                if (txtCodAltDesHis.getText().length()>0 || txtCodAltHasHis.getText().length()>0){
                    strSQL+=" AND ((LOWER(a.tx_codAltHis) BETWEEN '" + txtCodAltDesHis.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHasHis.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_codAltHis) LIKE '" + txtCodAltHasHis.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                if(txtCodImp.getText().length()>0){
                    strSQL+=" AND a.co_empIngImp=" + txtCodImp.getText() +"\n";  
                }
                if(txtCodPed.getText().length()>0){
                    strSQL+=" AND a.co_empIngImp=" + intCodEmpIngImp + " AND a.co_locIngImp=" + intCodLocIngImp + " AND a.co_tipDocIngImp=" + intCodTipDocIngImp + " AND a.co_docIngImp=" + txtCodPed.getText() +"\n";  
                }
                // Mostrar sólo items con cantidad contada
                if (chkItmCanCon.isSelected())  {
                    strSQL+=" AND ( a.nd_CanOrdCon > 0 OR a.nd_canMalEstOrdCon >0 ) \n"; 
                }
                // Mostrar sólo items con cantidad transferida
                if (chkItmCanTra.isSelected()  )  {
                    strSQL+=" AND ( a.nd_canTrs > 0 ) \n"; 
                }
                // Mostrar Items con Cantidad Pendiente de Transferir 
                if (chkItmPenTra.isSelected()  ) {
                    strSQL+="  AND ( ( a.nd_canTrs < (a.nd_CanOrdCon + a.nd_canMalEstOrdCon) ) OR (a.nd_canTrs IS NULL) ) /* AND a.fe_docIngImp>='2014-01-01'*/ \n"; //(1)
                    //strSQL+="  AND ( ( a.nd_canTrs < (a.CanSol) ) OR  (a.nd_canTrs IS NULL))  /*AND a.fe_docIngImp>='2014-01-01'*/ "; //(2)
                }
                
                strSQL+="  " + strAux+"\n";   
                strSQL+=" ORDER BY a.fe_docIngImp, a.tx_codAlt, a.tx_codAltHis";
                System.out.println("ZafImp08.cargarDetReg: "+strSQL);
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
                        vecReg.add(INT_TBL_DAT_CODSEG,              rst.getString("co_seg") );
                        vecReg.add(INT_TBL_DAT_COD_ITM_GRP,         rst.getString("co_itmGrp"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,             rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,             rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_HIS,         rst.getString("tx_codAltHis"));
                        vecReg.add(INT_TBL_DAT_COD_ALT2,            rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,             rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,             rst.getString("tx_uniMed"));
                        vecReg.add(INT_TBL_DAT_COD_IMP,             rst.getString("co_impIngImp"));
                        vecReg.add(INT_TBL_DAT_NOM_IMP,             rst.getString("tx_nomImpIngImp"));
                        vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,     rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,     rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_ING_IMP,     rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,     rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,     rst.getString("co_regIngImp"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC_ING_IMP,     rst.getString("ne_numDocIngImp"));
                        vecReg.add(INT_TBL_DAT_NUM_PED_ING_IMP,     rst.getString("tx_numDoc2IngImp"));
                        vecReg.add(INT_TBL_DAT_FEC_ING_IMP,         rst.getString("fe_docIngImp"));
                        vecReg.add(INT_TBL_DAT_CAN_PED_EMB,         rst.getString("nd_canPedEmb"));
                        vecReg.add(INT_TBL_DAT_CAN_ING_IMP,         rst.getString("nd_canIngImp"));
                        vecReg.add(INT_TBL_DAT_COD_EMP_ORD_CON,     rst.getString("co_empOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_ORD_CON,     rst.getString("co_locOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ORD_CON, rst.getString("co_tipDocOrdCon"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_ORD_CON,     rst.getString("co_docOrdCon"));
                        vecReg.add(INT_TBL_DAT_CAN_CON_BUE_EST,     rst.getString("nd_canOrdCon"));
                        vecReg.add(INT_TBL_DAT_CAN_CON_MAL_EST,     rst.getString("nd_canMalEstOrdCon"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_CON, null);
                        vecReg.add(INT_TBL_DAT_CAN_SOLTRA,          rst.getString("nd_canSol"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_SOL, null);
                        vecReg.add(INT_TBL_DAT_CAN_TRA,             rst.getString("nd_canTrs"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_TRA, null);
                        vecReg.add(INT_TBL_DAT_CAN_AJU,             rst.getString("nd_CanAju"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_AJU, null);
                        vecReg.add(INT_TBL_DAT_CAN_SOL_AJU, null);
                        vecReg.add(INT_TBL_DAT_CHK_AJU,     null);
                        vecReg.add(INT_TBL_DAT_EST_ING,             rst.getString("st_ingImp"));
                        
                        if(!  (  (rst.getObject("st_tieAju")==null)  || (rst.getString("st_tieAju").equals("N"))  )  )
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_AJU);   

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
                    hideCantidadesGrupoVentas(); //Se eliminan datos que no pueden ver las vendedoras.
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
 

    private void hideCantidadesGrupoVentas()
    {
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL =" SELECT co_usr, co_grpUsr FROM tbm_usr ";
                strSQL+=" WHERE co_grpUsr=3 AND st_Reg='A' ";
                strSQL+=" AND co_usr= "+objParSis.getCodigoUsuario();
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                    {
                        objTblMod.setValueAt("", i, INT_TBL_DAT_COD_IMP); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_NOM_IMP); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_CAN_PED_EMB); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_COD_EMP_ORD_CON); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_COD_LOC_ORD_CON); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_COD_TIP_DOC_ORD_CON); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_COD_DOC_ORD_CON); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_CAN_CON_BUE_EST); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_CAN_CON_MAL_EST); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_CAN_SOLTRA); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_CAN_TRA); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_CAN_AJU); 
                        objTblMod.setValueAt("", i, INT_TBL_DAT_CAN_SOL_AJU); 
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
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
   
        
    

}

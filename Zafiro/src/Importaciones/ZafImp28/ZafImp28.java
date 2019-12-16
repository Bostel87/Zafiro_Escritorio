/*
 * ZafImp28.java
 *
 * Created on 26 de septiembre de 2017, 17:06 PM
 */
package Importaciones.ZafImp28;
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
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
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
 * @author  Rosa Zambrano
 */
public class ZafImp28 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    private static final int INT_TBL_DAT_LIN=0;                         //Línea
    private static final int INT_TBL_DAT_CHK = 1;                       //Check para indicar que se esta autorizando el item seleccionado.
    private static final int INT_TBL_DAT_CODSEG = 2;                    //Código de Seguimiento.
    private static final int INT_TBL_DAT_COD_ITM_MAE=3;                 //Código maestro del item.
    private static final int INT_TBL_DAT_COD_ITM_EMP=4;                 //Código del item de la empresa
    private static final int INT_TBL_DAT_COD_ALT=5;                     //Código alterno del item.
    private static final int INT_TBL_DAT_COD_ALT2=6;                    //Código alterno dos del item.
    private static final int INT_TBL_DAT_NOM_ITM=7;                     //Nombre del item.
    private static final int INT_TBL_DAT_UNI_MED=8;                     //Unidad de medida.
    private static final int INT_TBL_DAT_COD_IMP=9;                     //Codigo del importador.
    private static final int INT_TBL_DAT_NOM_IMP=10;                    //Nombre del importador.
    private static final int INT_TBL_DAT_COD_EMP=11;                    //Código de empresa.
    private static final int INT_TBL_DAT_COD_LOC=12;                    //Código de local.
    private static final int INT_TBL_DAT_COD_TIPDOC=13;                 //Código de tipo de documento.
    private static final int INT_TBL_DAT_COD_DOC=14;                    //Codigo de documento.
    private static final int INT_TBL_DAT_COD_REG=15;                    //Codigo de registro.
    private static final int INT_TBL_DAT_NUM_DOC=16;                    //Número de documento
    private static final int INT_TBL_DAT_NUM_PED=17;                    //Número del pedido.    
    private static final int INT_TBL_DAT_FEC_DOC=18;                    //Fecha del documento.
    private static final int INT_TBL_DAT_CAN_INGIMP=19;                 //Cantidad del ingreso de importación
    private static final int INT_TBL_DAT_CAN_CONING=20;                 //Cantidad del conteo del ingreso de importación.
    private static final int INT_TBL_DAT_CAN_CONREA=21;                 //Cantidad del conteo realizado
    private static final int INT_TBL_DAT_CAN_AJU=22;                    //Cantidad total de ajuste.
    private static final int INT_TBL_DAT_CAN_CONAJU=23;                 //Cantidad del conteo del ajuste.
    private static final int INT_TBL_DAT_BUT_ANE_CON=24;                //Muestra ventana con información de conteos realizados.
    private static final int INT_TBL_DAT_CAN_SOLTRS=25;                 //Cantidad solicitada a transferir. 
    private static final int INT_TBL_DAT_BUT_ANE_SOL=26;                //Muestra ventana con información de solicitudes de transferencias asociadas.
    private static final int INT_TBL_DAT_CAN_TRS=27;                    //Cantidad transferida. 
    private static final int INT_TBL_DAT_BUT_ANE_TRS=28;                //Muestra ventana con información de transferencias asociadas.
    private static final int INT_TBL_DAT_CAN_AUTTRS=29;                 //Cantidad autorizada a transferir a bodegas.
    private static final int INT_TBL_DAT_BUT_ANE_AUT_TRS=30;            //Muestra ventana donde se puede autorizar transferencias a diferentes bodegas.
    private static final int INT_TBL_DAT_CAN_PEN=31;                    //Cantidad pendiente de procesar.
    private static final int INT_TBL_DAT_EST_ING=32;                    //Indica el estado del ingreso por importación.

    //Constantes: Códigos de Menú.
    private static final int INT_COD_MNU_INGIMP=2889;                   //Ingresos por Importacion.
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCen, objTblCelRenLblNum;     //Render: Presentar JLabel en JTable.
    private ZafStkInv objStkInv;
    private ZafMouMotAda objMouMotAda;                               //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                               //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                     //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                     //JTable de ordenamiento.
    private ZafVenCon vcoPed;                                        //Ventana de consulta "Pedidos".
    private ZafVenCon vcoItm;                                        //Ventana de consulta "Item".
    private ZafPerUsr objPerUsr;
    private ZafImp objImp;
    private Connection conAut;
    
    private ZafTblCelRenBut objTblCelRenButAneTrs;              //Render: Presentar JButton en JTable - Muestra ventana con información de transferencias asociadas.
    private ZafTblCelRenBut objTblCelRenButAneCon;              //Render: Presentar JButton en JTable - Muestra ventana con información de conteos realizados.
    private ZafTblCelRenBut objTblCelRenButAneSol;              //Render: Presentar JButton en JTable - Muestra ventana con información de solicitudes de transferencias asociadas.
    private ZafTblCelRenBut objTblCelRenButAneAutTrs;           //Render: Presentar JButton en JTable - Muestra ventana donde se puede autorizar transferencias a diferentes bodegas que no sea Inmaconsa

    private ZafTblCelEdiButGen objTblCelEdiButGenAneTrs;        //Editor: Muestra ventana con información de transferencias asociadas.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneCon;        //Editor: Muestra ventana con información de conteos realizados.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneSol;        //Editor: Muestra ventana con información de solicitud de transferencias asociadas.
    private ZafTblCelEdiButGen objTblCelEdiButGenAneAutTrs;     //Editor: Muestra ventana donde se puede autorizar transferencias a diferentes bodegas que no sea Inmaconsa
    
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    
    private ZafImp28_01 objImp28_01;                            //Muestra ventana con información de conteos realizados.
    private ZafImp28_02 objImp28_02;                            //Muestra ventana con información de solicitudes de transferencias asociadas.        
    private ZafImp28_03 objImp28_03;                            //Muestra ventana con información de transferencias asociadas.
    private ZafImp28_04 objImp28_04;                            //Muestra ventana donde se puede autorizar transferencias a diferentes bodegas que no sea Inmaconsa
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private int intCodEmpIngImp;
    private int intCodLocIngImp;
    private int intCodTipDocIngImp;
    private String strSQL, strAux;
    private String strCodPed, strPedImp;                        //Contenido del campo al obtener el foco.
    private String strCodAlt, strCodAlt2, strNomItm,strNumPed;  //Contenido del campo al obtener el foco.
    
    private String strVer=" v0.1.5";

    /** Crea una nueva instancia de la clase . */
    public ZafImp28(ZafParSis obj) 
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
            objTblCelRenButAneAutTrs=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            //Objeto de Stock de Inventario
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);

            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                       
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(4074))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4175))
            {
                butGua.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4075))
            {
                butCer.setVisible(false);
            }

            //Configurar objetos.
            txtCodItmMae.setVisible(false);
            txtCodItm.setVisible(false);
            txtCodPed.setVisible(false);
            txtNumDocIngImp.setVisible(false);
            
            //Configurar las ZafVenCon.
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
            vecCab=new Vector(33);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK," ");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT2,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_COD_IMP,"Cód.Imp.");
            vecCab.add(INT_TBL_DAT_NOM_IMP,"Nom.Imp.");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIPDOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CAN_INGIMP,"Can.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CAN_CONING,"Can.Con.Ing.");
            vecCab.add(INT_TBL_DAT_CAN_CONREA,"Can.Con.Rea.");
            vecCab.add(INT_TBL_DAT_CAN_AJU,"Can.Aju.");
            vecCab.add(INT_TBL_DAT_CAN_CONAJU,"Can.Con.Aju.");            
            vecCab.add(INT_TBL_DAT_BUT_ANE_CON,"..");
            vecCab.add(INT_TBL_DAT_CAN_SOLTRS,"Can.Sol.Trs.");    
            vecCab.add(INT_TBL_DAT_BUT_ANE_SOL,"..");  
            vecCab.add(INT_TBL_DAT_CAN_TRS,"Can.Trs."); 
            vecCab.add(INT_TBL_DAT_BUT_ANE_TRS,"..");
            vecCab.add(INT_TBL_DAT_CAN_AUTTRS,"Can.Aut.Trs."); 
            vecCab.add(INT_TBL_DAT_BUT_ANE_AUT_TRS,"");
            vecCab.add(INT_TBL_DAT_CAN_PEN,"Can.Pen."); 
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
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30); 
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_TIPDOC).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CAN_INGIMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CONING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CONREA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CONAJU).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOLTRS).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_SOL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRS).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AUTTRS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_AUT_TRS).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_EST_ING).setPreferredWidth(28); 
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIPDOC, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CONREA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_CON, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_SOL, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ANE_TRS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_PEN, tblDat);
            if(objParSis.getCodigoUsuario()!=1) {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ING, tblDat);  
            }
            
            //Agrupamiento de Columnas.
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrpIngImp = new ZafTblHeaColGrp("Ing.Imp.");
            objTblHeaColGrpIngImp.setHeight(16);
            objTblHeaColGrpIngImp.add(tcmAux.getColumn(INT_TBL_DAT_CAN_INGIMP));
            objTblHeaColGrpIngImp.add(tcmAux.getColumn(INT_TBL_DAT_CAN_CONING));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpIngImp);
            objTblHeaColGrpIngImp = null;

            ZafTblHeaColGrp objTblHeaColGrpAju = new ZafTblHeaColGrp("Ajuste");
            objTblHeaColGrpAju.setHeight(16);
            objTblHeaColGrpAju.add(tcmAux.getColumn(INT_TBL_DAT_CAN_AJU));
            objTblHeaColGrpAju.add(tcmAux.getColumn(INT_TBL_DAT_CAN_CONAJU));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAju);
            objTblHeaColGrpAju = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Establecer Tipo de Columna
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_INGIMP, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CONING, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CONREA, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_AJU, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CONAJU, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_SOLTRS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_TRS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_AUTTRS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_PEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_EST_ING).setCellRenderer(objTblCelRenLbl);
                        
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                BigDecimal bgdCanPen=BigDecimal.ZERO;
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLbl.getRowRender();
                    bgdCanPen=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN) == null ? "0" : (objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString()));
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(bgdCanPen.compareTo(BigDecimal.ZERO)>0){
                        objTblCelRenLbl.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblCen=new ZafTblCelRenLbl();
            objTblCelRenLblCen.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT2).setCellRenderer(objTblCelRenLblCen);
            objTblCelRenLblCen.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                BigDecimal bgdCanPen=BigDecimal.ZERO;
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblCen.getRowRender();
                    bgdCanPen=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN) == null ? "0" : (objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString()));
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(bgdCanPen.compareTo(BigDecimal.ZERO)>0){
                        objTblCelRenLblCen.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenLblCen.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }                    
                }
            });
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_INGIMP).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CONING).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CONREA).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CONAJU).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SOLTRS).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AUTTRS).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setCellRenderer(objTblCelRenLblNum);
               
            objTblCelRenLblNum.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                BigDecimal bgdCanPen=BigDecimal.ZERO;
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblNum.getRowRender();
                    bgdCanPen=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN) == null ? "0" : (objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString()));
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(bgdCanPen.compareTo(BigDecimal.ZERO)>0){
                        objTblCelRenLblNum.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }                    
                }
            });
              
            //Establecer: Check
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                java.awt.Color colFonColCre;
                BigDecimal bgdCanPen=BigDecimal.ZERO;
                int intFil = -1;
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    intFil = objTblCelRenChk.getRowRender();
                    bgdCanPen=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN) == null ? "0" : (objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString()));
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(bgdCanPen.compareTo(BigDecimal.ZERO)>0){
                        objTblCelRenChk.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    } 
                }
            });
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_CON);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_SOL);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_TRS);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_AUT_TRS);
            objTblMod.setColumnasEditables(vecAux);
            
            objTblCelRenButAneCon=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON).setCellRenderer(objTblCelRenButAneCon);
            objTblCelRenButAneCon=null;
            
            objTblCelRenButAneSol=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_SOL).setCellRenderer(objTblCelRenButAneSol);
            objTblCelRenButAneSol=null;
            
            objTblCelRenButAneTrs=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRS).setCellRenderer(objTblCelRenButAneTrs);
            objTblCelRenButAneTrs=null;
            
            objTblCelRenButAneAutTrs=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_AUT_TRS).setCellRenderer(objTblCelRenButAneAutTrs);
            objTblCelRenButAneAutTrs=null;
        
            //Anexo 1: Conteos             
            objTblCelEdiButGenAneCon=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_CON).setCellEditor(objTblCelEdiButGenAneCon);
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
                BigDecimal bgdCan = new BigDecimal ("0");
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    bgdCan= new BigDecimal (Double.parseDouble(objTblMod.getValueAt(objTblCelRenButAneSol.getRowRender(), INT_TBL_DAT_CAN_SOLTRS).toString()));
                    switch (objTblCelRenButAneSol.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ANE_SOL:
                            if (bgdCan.compareTo(new BigDecimal("0"))<=0)
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
                int intFilSel;
                BigDecimal bgdCan = new BigDecimal ("0");
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    bgdCan= new BigDecimal (Double.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_SOLTRS).toString()));
                    if (bgdCan.compareTo(new BigDecimal("0"))<=0)
                       objTblCelEdiButGenAneSol.setCancelarEdicion(true);
                 }
                 public void afterEdit(ZafTableEvent evt) {
                    int intEmp, intLoc, intTipDoc, intDoc, intItmMae; 
                    if (bgdCan.compareTo(new BigDecimal("0"))>0){
                        intEmp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString());
                        intLoc=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString());
                        intTipDoc=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIPDOC).toString());
                        intDoc=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC).toString());
                        intItmMae=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE).toString());
                        mostrarVcoSol( intEmp, intLoc, intTipDoc, intDoc, intItmMae );
                    }
                }
             });
            
            //Anexo 3: Transferencias de inventario
            objTblCelRenButAneTrs=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_TRS).setCellRenderer(objTblCelRenButAneTrs);
            objTblCelRenButAneTrs.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                BigDecimal bgdCan = new BigDecimal ("0");
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    bgdCan= new BigDecimal (Double.parseDouble(objTblMod.getValueAt(objTblCelRenButAneTrs.getRowRender(), INT_TBL_DAT_CAN_TRS).toString()));
                    switch (objTblCelRenButAneTrs.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ANE_TRS:
                           if (bgdCan.compareTo(new BigDecimal("0"))<=0)
                               objTblCelRenButAneTrs.setText("");
                            else
                               objTblCelRenButAneTrs.setText("...");
                        break;
                    }
                }
            });
            objTblCelEdiButGenAneTrs=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_TRS).setCellEditor(objTblCelEdiButGenAneTrs);
            objTblCelEdiButGenAneTrs.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel;
                BigDecimal bgdCan = new BigDecimal ("0");
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    bgdCan= new BigDecimal (Double.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_TRS).toString()));
                    if (bgdCan.compareTo(new BigDecimal("0"))<=0)
                       objTblCelEdiButGenAneTrs.setCancelarEdicion(true);
                 }
                 public void afterEdit(ZafTableEvent evt) {
                    int intEmp, intLoc, intTipDoc, intDoc, intItmMae; 
                    if (bgdCan.compareTo(new BigDecimal("0"))>0) {
                        intEmp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString());
                        intLoc=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString());
                        intTipDoc=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIPDOC).toString());
                        intDoc=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC).toString());
                        intItmMae=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE).toString());
                        mostrarVcoTrs( intEmp, intLoc, intTipDoc, intDoc, intItmMae );
                    }
                }
             });
            
            //Anexo: Aut Trs//Anexo 3: Transferencias de inventario
            objTblCelRenButAneAutTrs=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_AUT_TRS).setCellRenderer(objTblCelRenButAneAutTrs);
            objTblCelRenButAneAutTrs.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                 @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButAneAutTrs.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ANE_AUT_TRS:
                            BigDecimal bgdCan = new BigDecimal(objTblMod.getValueAt(objTblCelRenButAneAutTrs.getRowRender(), INT_TBL_DAT_CAN_INGIMP).toString());
                            BigDecimal bgdCanAju = new BigDecimal(objTblMod.getValueAt(objTblCelRenButAneAutTrs.getRowRender(), INT_TBL_DAT_CAN_AJU).toString());

                            if (objTblMod.getValueAt(objTblCelRenButAneAutTrs.getRowRender(), INT_TBL_DAT_EST_ING).equals("S") ){
                                //Valida que no se permita autorizar items con cantidades de ajustes negativas, es decir faltantes.
                                if ( (bgdCanAju.compareTo(new BigDecimal("0"))>=0) && (bgdCan.compareTo(new BigDecimal("0"))>=0)){
                                    objTblCelRenButAneAutTrs.setText("...");
                                }
                                else{
                                    objTblCelRenButAneAutTrs.setText("");
                                }
                            }
                            else{
                                objTblCelRenButAneAutTrs.setText("");                                           
                            }
                            break;
                    }
                }
            });

            /**
             * Validaciones Varias: Si es el usuario admin podrá visualizar la autorización de la mercadería importada
             * Sin embargo, existen otras validaciones al momento de guardar que no permite a ningún usuario realizar cambios en la orden de distribución 
             * cuando el pedido está cerrado o no ha arribado, etc
             */
            objTblCelEdiButGenAneAutTrs=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_AUT_TRS).setCellEditor(objTblCelEdiButGenAneAutTrs);
            objTblCelEdiButGenAneAutTrs.addTableEditorListener(new ZafTableAdapter() {
                int intFil;
                public void beforeEdit(ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    BigDecimal bgdCan = new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_INGIMP).toString());
                    BigDecimal bgdCanAju = new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_AJU).toString());
                    
                    //Si ya existen datos autorizados se cancela la edición, solo un ítem a la vez.
                    if(objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK)) {
                        objTblCelEdiButGenAneAutTrs.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>Sólo puede autorizarse un ítem a la vez.<BR>Autorice el ítem seleccionado y luego vuelva a intentarlo.</HTML>");
                    }
                    else{
                        System.out.println("Roseeeeeeeee EHHHH");
                        if(objParSis.getCodigoUsuario()==1 ){ 
                            isCalWinAutBodTra(intFil);
                        }
                        else{
                            if (objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_ING).equals("S") ){
                                //Valida que no se permita autorizar items con cantidades de ajustes negativas, es decir faltantes.
                                if ( (bgdCanAju.compareTo(new BigDecimal("0"))>=0) && (bgdCan.compareTo(new BigDecimal("0"))>=0)){
                                    isCalWinAutBodTra(intFil);
                                }
                                else{
                                    objTblCelEdiButGenAneAutTrs.setCancelarEdicion(true);
                                    mostrarMsgInf("<HTML>El item que desea autorizar tiene cantidad negativa.<BR> Solo se pueden autorizar items con cantidad positiva.</HTML>");
                                }
                            }
                            else{
                                objTblCelEdiButGenAneAutTrs.setCancelarEdicion(true);
                                if (objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_ING).equals("C") ){
                                    mostrarMsgInf("<HTML>El ingreso por importación ya ha sido cerrado.</HTML>");
                                }
                                else if (!objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_ING).equals("A") ){
                                    mostrarMsgInf("<HTML>El ingreso por Importación no ha sido arribado.<BR> Indicar el pedido como arribado y luego proceda a realizar la autorización.</HTML>");
                                }                        
                            }
                        }                                
                        
                    }
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                    if(objImp28_04.getSelectedButton()==objImp28_04.INT_BUT_AUT){
                        if(objImp28_04.isAutMod()){
                            objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                        }
                        else {
                            //Si no ha sido guardado correctamente.
                            objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                        }
                    }
                    else{//Para boton cerrar y cualquier otro caso 
                        objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                    }
                    tblDat.requestFocus();
                }
                public void afterEdit(ZafTableEvent evt) {
                    objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                    if(objImp28_04.getSelectedButton()==objImp28_04.INT_BUT_AUT){
                        if(objImp28_04.isAutMod()){
                            objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                        }
                        else {
                            //Si no ha sido guardado correctamente.
                            objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                        }
                    }
                    else{//Para boton cerrar y cualquier otro caso 
                        objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                    }
                    tblDat.requestFocus();
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
                case INT_TBL_DAT_LIN:
                    //strMsg="";
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #FFDDBB'>&nbsp;</td><td>&nbsp;</td><td>Indica que son \"Items pendientes de procesar\".</td></tr>"; //Color Crema
                    //strMsg+="<tr><td>&nbsp;</td><td style='background-color: #FF0000'>&nbsp;</td><td>Indica que son \"Items de ajustes tipo egreso\".</td></tr>"; //Color Rojo
                    strMsg+="</table><br></html>";
                    break;
                case INT_TBL_DAT_CHK:
                    strMsg="Indica autorización de la fila seleccionada.";
                    break; 
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código del seguimiento";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg="Código del item de empresa";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código de local";
                    break;
                case INT_TBL_DAT_COD_TIPDOC:
                    strMsg="Código de tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;    
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número de Pedido";
                    break; 
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_CAN_INGIMP:
                    strMsg="Cantidad del ingreso por importación";
                    break;   
                case INT_TBL_DAT_CAN_CONING:
                    strMsg="Cantidad Contada del ingreso de importación";
                    break;                            
                case INT_TBL_DAT_CAN_CONREA:
                    strMsg="Cantidad Contada real";
                    break;
                case INT_TBL_DAT_CAN_AJU:
                    strMsg="Cantidad del ajuste";
                    break;   
                case INT_TBL_DAT_CAN_CONAJU:
                    strMsg="Cantidad Contada del Ajuste";
                    break;                        
                case INT_TBL_DAT_BUT_ANE_CON:
                    strMsg="Muestra información de conteos realizados";
                    break;                    
                case INT_TBL_DAT_CAN_SOLTRS:
                    strMsg="Cantidad de Solicitud de Transferencia";
                    break;   
                case INT_TBL_DAT_BUT_ANE_SOL:
                    strMsg="Muestra información de solicitud de transferencias asociadas";
                    break;                     
                case INT_TBL_DAT_CAN_TRS:
                    strMsg="Cantidad de Transferencia";
                    break;  
                case INT_TBL_DAT_BUT_ANE_TRS:
                    strMsg="Muestra información de transferencias asociadas";
                    break;                          
                case INT_TBL_DAT_CAN_AUTTRS:
                    strMsg="Cantidad autorizada transferir a las distintas bodegas";
                    break;  
                case INT_TBL_DAT_BUT_ANE_AUT_TRS:
                    strMsg="Autorizar bodegas a transferir mercadería de Ingreso por Importación";
                    break;
                case INT_TBL_DAT_CAN_PEN:
                    strMsg="Cantidad pendiente de procesar";
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
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) ";
            strSQL+=" WHERE a1.st_Reg='A' ";
            strSQL+=" AND a1.co_tipDoc in(select co_tipDoc from tbr_tiPDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+INT_COD_MNU_INGIMP+")";
            strSQL+=" AND a1.co_mnu="+INT_COD_MNU_INGIMP;
           
            //Se muestran pedidos arribados, en proceso de conteo, cerrados y pedidos antiguos adaptados al nuevo esquema.        
            strSQL+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) NOT IN ('N', 'P') ";
            
            /*Filtros*/
            if(!chkMosDocCer.isSelected()){
                //Si no se selecciona mostrar pedidos cerrados, no se mostrarán los pedidos cerrados.
                strSQL+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) NOT IN ('C') ";
            }
            
            
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
        panFil = new javax.swing.JPanel();
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
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
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        chkMosDocCer = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
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

        panFil.setPreferredSize(new java.awt.Dimension(0, 440));
        panFil.setLayout(null);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("Cliente");
        panFil.add(lblPed);
        lblPed.setBounds(25, 20, 60, 20);

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
        txtCodPed.setBounds(62, 20, 15, 20);

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
        txtNumDocIngImp.setBounds(77, 20, 15, 20);

        txtPedIngImp.setBackground(objParSis.getColorCamposObligatorios());
        txtPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusLost(evt);
            }
        });
        txtPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPedIngImpActionPerformed(evt);
            }
        });
        panFil.add(txtPedIngImp);
        txtPedIngImp.setBounds(92, 20, 228, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panFil.add(butPedImp);
        butPedImp.setBounds(320, 20, 20, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Item");
        panFil.add(lblItm);
        lblItm.setBounds(40, 110, 30, 20);
        panFil.add(txtCodItmMae);
        txtCodItmMae.setBounds(70, 110, 15, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(85, 110, 15, 20);

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
        txtCodAlt.setBounds(100, 110, 120, 20);

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
        txtCodAlt2.setBounds(220, 110, 80, 20);

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
        txtNomItm.setBounds(300, 110, 330, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(630, 110, 20, 20);

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
        panBusItm.setBounds(50, 140, 370, 45);

        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(20, 60, 400, 20);

        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(20, 80, 400, 20);

        chkMosDocCer.setText("Mostrar pedidos cerrados.");
        panFil.add(chkMosDocCer);
        chkMosDocCer.setBounds(380, 20, 210, 20);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal())
        {
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

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
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
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

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
        
        if (txtCodAlt2.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }

    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2 = txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

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

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

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

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltHasFocusLost

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
    }//GEN-LAST:event_txtCodPedFocusLost

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
    }//GEN-LAST:event_txtNumDocIngImpFocusLost

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
    }//GEN-LAST:event_txtPedIngImpFocusLost

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        configurarPedidos();
        mostrarPedidos(0);
    }//GEN-LAST:event_butPedImpActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
            txtCodItmMae.setText("");
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        if (optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilItemStateChanged

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if(objImp28_04!=null && objImp28_04.getSelectedButton()==objImp28_04.INT_BUT_AUT && objImp28_04.isAutMod())
        {
            if(guardar()) {
                mostrarMsgInf("<HTML>La operación GUARDAR se realizó con éxito.</HTML>");
            }
            else{
                mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique los datos.</HTML>");
            }
        }
        else{
            mostrarMsgInf("<HTML>No se han realizado cambios que guardar.</HTML>");            
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
    private javax.swing.JCheckBox chkMosDocCer;
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
    
         
     private void mostrarVentanaAnexoConteo(int intEmp, int intLoc, int intTipDoc, int intDoc, int intItmMae )
     {
         objImp28_01=new ZafImp28_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         objImp28_01.show();
         objImp28_01=null;
     }
    
     /*
      * Muestra la ventana del boton de cuales son las solicitudes de transferencias asociadas.
      * 
      */
     private void mostrarVcoSol(int intEmp, int intLoc, int intTipDoc, int intDoc, int intItmMae )
     {
         objImp28_02=new ZafImp28_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         objImp28_02.show();
         objImp28_02=null;
     }
     
     /*
      * Muestra la ventana del boton de cuales son las transferencias asociadas.
      * 
      */
     private void mostrarVcoTrs(int intEmp, int intLoc, int intTipDoc, int intDoc, int intItmMae )
     {
         objImp28_03=new ZafImp28_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intEmp, intLoc, intTipDoc, intDoc, intItmMae );
         objImp28_03.show();
         objImp28_03=null;
     }

    private void isCalWinAutBodTra(int intFilSel)
    {
       int intCodEmpFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString()));
       int intCodLocFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString()));
       int intCodTipDocFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIPDOC)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIPDOC).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIPDOC).toString()));
       int intCodDocFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC).toString()));
       int intCodRegFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_REG)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_REG).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_REG).toString()));
       int intCodItmMaeFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE).toString()));
       int intCodItmEmpFilSel=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_EMP)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_EMP).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_EMP).toString()));
       int intCodItmGrpFilSel=objStkInv.getCodItmGrp(intCodEmpFilSel, intCodItmEmpFilSel);
       String strNomItmFilSel=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ITM)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ITM).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_ITM).toString());
       String strCodAltItmFilSel=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT).toString());
       String strCodAltDosItmFilSel=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT2)==null?"0":(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT2).toString().equals("")?"0":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT2).toString());
       BigDecimal bdgCanIngImp = new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_INGIMP) == null ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_INGIMP).toString().equals("") ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_INGIMP).toString());
       BigDecimal bdgCanAju = new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_AJU) == null ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_AJU).toString().equals("") ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_AJU).toString());
       BigDecimal bdgTotCanIngCanAju=bdgCanIngImp.add(bdgCanAju);
       
       BigDecimal bdgCanConIngImp = new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CONING) == null ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CONING).toString().equals("") ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CONING).toString());
       BigDecimal bdgCanConAju = new BigDecimal(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CONAJU) == null ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CONAJU).toString().equals("") ? "0" : objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_CONAJU).toString());
       BigDecimal bdgTotCanCon=bdgCanConIngImp.add(bdgCanConAju);
       
       objImp28_04=new ZafImp28_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis
                                 , intCodItmMaeFilSel, intCodItmEmpFilSel, intCodItmGrpFilSel, strCodAltItmFilSel, strNomItmFilSel, strCodAltDosItmFilSel
                                 , intCodEmpFilSel, intCodLocFilSel, intCodTipDocFilSel, intCodDocFilSel, intCodRegFilSel, bdgTotCanIngCanAju, bdgTotCanCon);

        objImp28_04.setVisible(true);
    }
    
    private boolean isCamVal() 
    {
        if (txtCodPed.getText().equals("")) 
        {
            mostrarMsgInf("<HTML>El número de Pedido debe ser seleccionado<BR>Seleccione el número de Pedido y vuelva a intentarlo.</HTML>");
            tabFrm.setSelectedIndex(0);
            txtPedIngImp.requestFocus();
            return false;
        }

        return true;
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
        String strAuxTipDocAju="", strAuxTipDocIng="";
        int intCodLocGrp=1;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                strAux="";
                stm=con.createStatement();
                //Obtener la condición.
                strAuxTipDocAju="select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_loc="+intCodLocGrp+" and co_mnu="+objImp.INT_COD_MNU_PRG_AJU_INV;
                strAuxTipDocIng="select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_loc="+intCodLocGrp+" and co_mnu="+INT_COD_MNU_INGIMP;
      
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT b2.co_Seg, b1.*";
                strSQL+="      , CASE WHEN b1.nd_canConRea <= b1.nd_canIngImp THEN ((b1.nd_canConRea) - b1.nd_CanTrs) ELSE ((b1.nd_canIngImp+b1.nd_CanAju) - b1.nd_CanTrs) END as nd_canPen\n";
                strSQL+=" FROM ( /* INIMPO */  \n";
                strSQL+="    SELECT b1.co_itmMae, b1.co_itmGrp, b1.co_itmEmp, b1.tx_CodAlt, b1.tx_CodAlt2, b1.tx_nomItm, b1.tx_uniMed, b1.co_imp, b1.tx_nomImp\n";
                strSQL+="         , b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.tx_numDoc2, b1.fe_doc, b1.st_reg\n";
                strSQL+="         , CASE WHEN b1.st_ingImp IS NULL THEN 'N' ELSE CASE WHEN b1.st_ingImp = 'A' THEN 'S' ELSE CASE WHEN b1.st_ingImp = 'B' THEN 'S'\n";
                strSQL+="           ELSE CASE WHEN b1.st_ingImp = 'T' THEN 'S' ELSE b1.st_ingImp END END END END AS st_ingImp\n";
                strSQL+="         , b1.co_reg, b1.st_tieAju, b1.nd_canIngImp, (CASE WHEN b1.nd_canAju >0 THEN (b1.nd_canConRea-b1.nd_CanConAju) ELSE b1.nd_canConRea END) AS nd_canConIng\n";
                strSQL+="         , b1.nd_canConRea, b1.nd_CanAju, b1.nd_canConAju\n";
                strSQL+="         , (CASE WHEN b2.nd_CanSol IS NOT NULL THEN b2.nd_CanSol ELSE 0 END) AS nd_CanSol\n";   
                strSQL+="         , (CASE WHEN b3.nd_CanTrs IS NOT NULL THEN b3.nd_CanTrs ELSE 0 END) AS nd_CanTrs\n";   
                strSQL+="         , (CASE WHEN b4.nd_canAutTrs IS NOT NULL THEN b4.nd_canAutTrs ELSE 0 END) AS nd_canAutTrs\n";   
                strSQL+="    FROM (\n";
                strSQL+="          SELECT a2.co_itmMae, a2.co_itmGrp\n";
                strSQL+="               , (CASE WHEN a3.co_itm  IS NOT NULL THEN a3.co_itm ELSE a4.co_itm END) AS co_itmEmp\n";  
                strSQL+="               , (CASE WHEN a3.tx_codAlt IS NOT NULL THEN a3.tx_codAlt ELSE a2.tx_codAlt END) AS tx_codAlt\n";             
                strSQL+=" 	        , (CASE WHEN a3.tx_codAlt2 IS NOT NULL THEN a3.tx_codAlt2 ELSE a2.tx_codAlt2 END) AS tx_codAlt2\n";   
                strSQL+="               , (CASE WHEN a3.tx_nomItm IS NOT NULL THEN a3.tx_nomItm ELSE a2.tx_nomItm END) AS tx_nomItm\n";  
                strSQL+=" 	        , (CASE WHEN a3.tx_uniMed IS NOT NULL THEN a3.tx_uniMed ELSE a2.tx_uniMed END) AS tx_uniMed\n";     
                strSQL+="               , a1.co_imp, a1.tx_nomImp, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc\n";
                strSQL+="               , a1.tx_numDoc2, a1.fe_doc, a1.st_reg\n";
                strSQL+="               , (CASE WHEN a4.st_ingImpAju IS NULL THEN a1.st_ingImp ELSE a4.st_ingImpAju END) AS st_ingImp\n"; 
                strSQL+="               , a1.st_tieAju\n";
                strSQL+="               , (CASE WHEN a3.co_reg  IS NOT NULL THEN a3.co_reg END) AS co_reg\n";  
                strSQL+="               , (CASE WHEN a3.nd_canIngImp IS NOT NULL THEN a3.nd_canIngImp ELSE 0 END) AS nd_canIngImp\n";  
                strSQL+=" 	        , (CASE WHEN a2.nd_CanCon IS NOT NULL THEN a2.nd_CanCon ELSE 0 END) AS nd_canConRea\n";  	      
                strSQL+=" 	        , (CASE WHEN a4.nd_canAju IS NOT NULL THEN a4.nd_canAju ELSE 0 END) AS nd_canAju\n";  
                //strSQL+="               , (CASE WHEN a4.nd_CanConAju IS NOT NULL THEN a4.nd_CanConAju ELSE 0 END) AS nd_CanConAju\n";
                strSQL+="               , (CASE WHEN a4.nd_CanConAju IS NOT NULL THEN CASE WHEN a4.nd_canConAju>0 THEN a4.nd_CanConAju ELSE 0 END END) AS nd_CanConAju\n";
                strSQL+="          FROM(\n";
                strSQL+="  	       SELECT a1.fe_doc AS fe_doc, a.co_empRel AS co_emp, a.co_locRel AS co_loc, a.co_tipDocRel AS co_tipDoc\n";
                strSQL+="                   , a.co_docRel AS co_doc, a1.ne_numDoc AS ne_numDoc, a1.tx_numDoc2, a1.st_reg\n";
                strSQL+="                   , (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) AS st_ingImp\n";                  
                strSQL+="                   , (CASE WHEN a1.st_cieAjuInvIngImp IS NULL THEN 'N' ELSE a1.st_cieAjuInvIngImp END) AS st_tieAju\n";
                strSQL+="                   , a2.co_Emp as co_imp, a2.tx_nom AS tx_nomImp, a.co_emp AS co_empOrdCon, a.co_loc AS co_locOrdCon, a.co_tipDoc AS co_tipDocOrdCon, a.co_doc AS co_docOrdCon\n";
                strSQL+="  	       FROM tbm_cabOrdConInv AS a\n";
                strSQL+="  	       INNER JOIN tbm_cabMovInv AS a1 ON a.co_empRel=a1.co_emp AND a.co_locRel=a1.co_loc AND a.co_tipDocRel=a1.co_tipDoc AND a.co_docRel=a1.co_doc\n";
                strSQL+="  	       INNER JOIN tbm_emp AS a2 ON a1.co_emp=a2.co_emp\n";
                strSQL+="  	       WHERE a1.st_Reg IN ('A') AND a.co_tipDocRel IN ("+strAuxTipDocIng+") \n";
                strSQL+="              AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END ) NOT IN ('P')\n";
                strSQL+="  	       AND a.co_empRel="+intCodEmpIngImp+" AND a.co_locRel="+intCodLocIngImp+" AND a.co_tipDocRel="+intCodTipDocIngImp+" AND a.co_docRel="+txtCodPed.getText()+"\n";
                strSQL+="          ) AS a1\n";
                strSQL+="          INNER JOIN( /* CONTEOS */ \n"; 
                strSQL+="  	       SELECT a.co_emp, a.co_locRel AS co_loc, a.co_tipDocRel AS co_tipDoc, a.co_docRel AS co_doc, a2.co_itmMae, a.co_itm as co_itmGrp\n";
                strSQL+="  	            , a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a.nd_stkCon AS nd_CanCon, a3.tx_desCor AS tx_uniMed\n";
                strSQL+="  	       FROM tbm_conInv AS a\n";
                strSQL+="              INNER JOIN tbm_inv AS a1 ON a.co_emp=a1.co_emp AND a.co_itm=a1.co_itm\n";
                strSQL+="  	       INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm\n";
                strSQL+="              LEFT OUTER JOIN tbm_var AS a3 ON a1.co_uni=a3.co_Reg\n";
                strSQL+="              WHERE (CASE WHEN (nd_stkCon=0 AND fe_solCon IS NULL) THEN FALSE ELSE TRUE END) \n"; //Se excluyen items que no estan en Inimpo y que fueron contados y luego dados de baja con conteo 0.
                strSQL+="          ) AS a2 ON a1.co_empOrdCon=a2.co_emp AND a1.co_locOrdCon=a2.co_loc AND a1.co_tipDocOrdCon=a2.co_tipDoc AND a1.co_docOrdCon=a2.co_doc\n"; 
                strSQL+=" 	   LEFT OUTER JOIN /* INIMPO DETALLE*/\n";
                strSQL+=" 	   (\n";   
                strSQL+="              SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.co_reg, a.co_itm, a1.co_itmMae\n";
                strSQL+="                   , a.nd_Can as nd_canIngImp, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a.tx_uniMed\n";
                strSQL+="              FROM tbm_detMovInv AS a\n";
                strSQL+="              INNER JOIN tbm_equInv AS a1 ON a.co_emp=a1.co_emp AND a.co_itm=a1.co_itm\n";
                strSQL+="              WHERE (CASE WHEN a.st_Reg IS NULL THEN 'A' ELSE a.st_Reg END ) NOT IN ('I')\n";
                strSQL+="              AND a.co_emp="+intCodEmpIngImp+" AND a.co_loc="+intCodLocIngImp+" AND a.co_tipDoc="+intCodTipDocIngImp+" AND a.co_doc="+txtCodPed.getText()+"\n";
                strSQL+="          ) AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc AND a2.co_itmMae=a3.co_itmMae\n";
                strSQL+="          LEFT OUTER JOIN /* AJUSTES  */ \n";
                strSQL+="          (\n";
                strSQL+="              SELECT a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a4.co_itmMae, a3.co_itm, SUM(a3.nd_can) AS nd_canAju, SUM(a3.nd_can) AS nd_canConAju ";
                strSQL+="                   , (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) AS st_ingImpAju \n";    
                strSQL+="  	       FROM (tbr_cabMovInv as a\n"; 
                strSQL+="                    INNER JOIN tbm_CabMovInv as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc  /*Ajuste*/\n";
                strSQL+="  	             INNER JOIN tbm_cabMovInv as a2 ON a.co_empRel=a2.co_emp AND a.co_locRel=a2.co_loc AND a.co_tipDocRel=a2.co_tipDoc AND a.co_docRel=a2.co_doc /*Inimpo*/\n";
                strSQL+="  	       )\n";
                strSQL+="  	       INNER JOIN tbm_DetMovInv as a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc\n";
                strSQL+="  	       INNER JOIN tbm_equInv AS a4 ON a1.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm\n";
                strSQL+="  	       WHERE a1.st_Reg IN ('A')\n"; //Excluya ajustes con st_Reg 'O'
                strSQL+="              AND (CASE WHEN a3.st_Reg IS NULL THEN 'A' ELSE a3.st_Reg END ) NOT IN ('I')\n";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                //strSQL+="  	       AND a3.nd_Can>0 /* Solo ingresos de los ajustes.*/\n"; 
                strSQL+="              AND a.co_tipDoc IN ("+strAuxTipDocAju+") \n";
                strSQL+="              AND a.co_tipDocRel IN ("+strAuxTipDocIng+") \n";
                strSQL+="              AND a.co_empRel="+intCodEmpIngImp+" AND a.co_locRel="+intCodLocIngImp+" AND a.co_tipDocRel="+intCodTipDocIngImp+" AND a.co_docRel="+txtCodPed.getText()+"\n";
                strSQL+="              GROUP BY a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a4.co_itmMae, a3.co_itm, a1.st_ingImp\n";           
                strSQL+="          ) AS a4 ON a1.co_emp=a4.co_empRel AND a1.co_loc=a4.co_locRel AND a1.co_tipDoc=a4.co_tipDocRel AND a1.co_doc=a4.co_docRel AND a2.co_itmMae=a4.co_itmMae\n";
                strSQL+="    ) as b1\n";
                strSQL+="    LEFT OUTER JOIN( /* SOLICITUDES DE TRANSFERENCIAS */\n";
                strSQL+="          SELECT a3.co_itmMae, SUM(a1.nd_can) AS nd_canSol\n";
                strSQL+=" 	   FROM tbm_CabSolTraInv as a \n";
                strSQL+=" 	   INNER JOIN tbm_detSolTraInv as a1 ON (a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipdoc AND a.co_doc=a1.co_Doc)\n";
                strSQL+=" 	   INNER JOIN tbr_cabSolTraInvCabMovInv as a2 ON (a2.co_EmpRelCabSolTraInv=a.co_emp AND a2.co_locRelCabSolTraInv=a.co_loc AND a2.co_tipDocRelCabSolTraInv=a.co_tipdoc AND a2.co_docRelCabSolTraInv=a.co_Doc )\n";
                strSQL+=" 	   INNER JOIN tbm_equInv AS a3 ON a.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm\n";
                strSQL+=" 	   WHERE a.st_reg in ('A') AND (CASE WHEN a.st_aut IS NULL THEN 'P' ELSE a.st_aut END) NOT IN ('D')\n";
                strSQL+=" 	   AND EXISTS\n";
                strSQL+=" 	   (\n";
                strSQL+=" 	      SELECT * FROM(\n";
                strSQL+=" 	          SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc\n";
                strSQL+=" 		       , a.co_emp AS co_empRel, a.co_loc as co_locRel, a.co_tipDoc AS co_tipDocRel, a.co_Doc AS co_docRel\n";
                strSQL+=" 	          FROM tbm_CabMovInv AS a\n";
                strSQL+=" 	          WHERE a.co_emp="+intCodEmpIngImp+" AND a.co_loc="+intCodLocIngImp+" AND a.co_tipDoc="+intCodTipDocIngImp+" AND a.co_doc="+txtCodPed.getText()+"\n";
                strSQL+=" 	          UNION ALL \n";
                strSQL+=" 	          SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc\n";
                strSQL+=" 		       , a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel\n";
                strSQL+=" 	          FROM tbr_CabMovInv AS a\n";
                strSQL+=" 	          WHERE a.co_empRel="+intCodEmpIngImp+" AND a.co_locRel="+intCodLocIngImp+" AND a.co_tipDocRel="+intCodTipDocIngImp+" AND a.co_docRel="+txtCodPed.getText()+"\n";
                strSQL+=" 	          AND a.co_tipDoc IN ("+strAuxTipDocAju+") \n";
                strSQL+=" 	       ) AS a4 \n";
                strSQL+=" 	       WHERE a2.co_empRelCabMovInv=a4.co_emp AND a2.co_locRelCabMovInv=a4.co_loc AND a2.co_tipDocRelCabMovInv=a4.co_tipDoc AND a2.co_docRelCabMovInv=a4.co_doc\n";
                strSQL+="          )\n";
                strSQL+="          GROUP BY a3.co_itmMae\n";
                strSQL+="    ) AS b2 ON b1.co_itmMae=b2.co_itmMae\n";
                strSQL+="    LEFT OUTER JOIN( /* TRANSFERENCIAS */\n";
                strSQL+="          SELECT a.co_empRel, a1.co_itm, a1.co_itmMae, ABS(SUM (a.nd_canTrs)) AS nd_canTrs\n";  
                strSQL+="          FROM (\n";
                strSQL+=" 	       SELECT a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a1.co_itm, SUM (a1.nd_can) AS nd_canTrs \n";
                strSQL+="              FROM (tbr_cabMovInv AS a2 INNER JOIN tbm_cabMovInv AS a ON a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_doc)\n";
                strSQL+="              INNER JOIN tbm_detMovInv AS a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc\n";
                strSQL+="              WHERE a1.nd_can<0 AND a.st_Reg in ('A')\n"; 
                strSQL+="              AND a.co_tipDoc NOT IN ("+strAuxTipDocAju+") \n";
                strSQL+="              AND EXISTS\n";
                strSQL+="              (\n";
                strSQL+="                 SELECT * FROM(\n";
                strSQL+=" 	            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.co_emp AS co_empRel, a.co_loc as co_locRel, a.co_tipDoc AS co_tipDocRel, a.co_Doc AS co_docRel\n";
                strSQL+=" 	            FROM tbm_CabMovInv AS a\n";
                strSQL+=" 	            WHERE a.co_emp="+intCodEmpIngImp+" AND a.co_loc="+intCodLocIngImp+" AND a.co_tipDoc="+intCodTipDocIngImp+" AND a.co_doc="+txtCodPed.getText()+"\n";
                strSQL+=" 	            UNION ALL \n";
                strSQL+=" 	            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel\n";
                strSQL+=" 	            FROM tbr_CabMovInv AS a\n";
                strSQL+=" 	            WHERE a.co_empRel="+intCodEmpIngImp+" AND a.co_locRel="+intCodLocIngImp+" AND a.co_tipDocRel="+intCodTipDocIngImp+" AND a.co_docRel="+txtCodPed.getText()+"\n";
                strSQL+=" 	            AND a.co_tipDoc IN ("+strAuxTipDocAju+") \n";
                strSQL+="                 ) AS a4\n";
                strSQL+="                 WHERE a2.co_empRel=a4.co_emp AND a2.co_locRel=a4.co_loc AND a2.co_tipDocRel=a4.co_tipDoc AND a2.co_docRel=a4.co_doc\n";
                strSQL+="              )\n";
                strSQL+="              GROUP BY a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a1.co_itm\n";
                strSQL+="          ) AS a \n";
                strSQL+="          INNER JOIN tbm_equInv AS a1 ON a.co_empRel=a1.co_emp AND a.co_itm=a1.co_itm\n";
                strSQL+="          GROUP BY a.co_empRel, a1.co_itm, a1.co_itmMae \n";     
                strSQL+="    ) AS b3 ON b1.co_itmMae=b3.co_itmMae\n";
                strSQL+="    LEFT OUTER JOIN( /* AUTORIZACION A BODEGAS */\n";
                strSQL+="          SELECT a2.co_itmMae, SUM(a1.nd_can) AS nd_canAutTrs\n";  
                strSQL+="          FROM tbm_cabOrdDis AS a \n";                
                strSQL+="          INNER JOIN tbm_detOrdDis AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc\n";
                strSQL+="          INNER JOIN tbm_equInv AS a2 ON a.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm\n";
                strSQL+="          WHERE a.st_reg='A' ";
                //strSQL+="          AND a1.nd_can>0  \n";  /*Solo items de ajustes de ingreso*/  Se comento para que presente los datos correctos OKKK
                strSQL+="          AND EXISTS\n";
                strSQL+="          (\n";
                strSQL+=" 	       SELECT * FROM(\n";
                strSQL+="                 SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.co_emp AS co_empRel\n";
                strSQL+="                      , a.co_loc as co_locRel, a.co_tipDoc AS co_tipDocRel, a.co_Doc AS co_docRel, a2.co_Reg\n";
		strSQL+="              	  FROM tbm_CabMovInv AS a\n";
                strSQL+="              	  INNER JOIN tbm_DetMovInv as a2 ON a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_Doc";                 
		strSQL+="                 WHERE a2.nd_Can<>0 ";
                strSQL+="                 AND a.co_emp="+intCodEmpIngImp+" AND a.co_loc="+intCodLocIngImp+" AND a.co_tipDoc="+intCodTipDocIngImp+" AND a.co_doc="+txtCodPed.getText()+"\n";
		strSQL+="                 UNION ALL \n";
                strSQL+="                 SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc\n";
                strSQL+="                      , a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a2.co_Reg\n";
                strSQL+="                 FROM tbr_CabMovInv AS a\n";
                strSQL+="                 INNER JOIN tbm_cabMovInv as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+="                 INNER JOIN tbm_DetMovInv as a2 ON a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_Doc";
                strSQL+="                 WHERE a1.st_reg IN ('A') "; //Ajustes activos, excluye los ajustes con st_Reg IN ('O', 'I', 'E')
                strSQL+="                 AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) NOT IN ('I')";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+="                 AND a.co_empRel="+intCodEmpIngImp+" AND a.co_locRel="+intCodLocIngImp+" AND a.co_tipDocRel="+intCodTipDocIngImp+" AND a.co_docRel="+txtCodPed.getText()+"\n";
                strSQL+="                 AND a.co_tipDoc IN ("+strAuxTipDocAju+") \n";
                strSQL+=" 	       ) AS a4\n"; 
                strSQL+=" 	       WHERE a1.co_empRel=a4.co_emp AND a1.co_locRel=a4.co_loc AND a1.co_tipDocRel=a4.co_tipDoc AND a1.co_docRel=a4.co_doc AND a1.co_regRel=a4.co_reg\n";
                strSQL+="          )\n";
                strSQL+="          GROUP BY a2.co_itmMae\n";
                strSQL+="    ) AS b4 ON b1.co_itmMae=b4.co_itmMae\n";
                strSQL+=" ) AS b1\n";
                strSQL+="  LEFT OUTER JOIN( /*Seguimiento de Inventario*/\n";
                strSQL+="       SELECT co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc FROM tbm_cabSegMovInv  as a \n";
                strSQL+="       INNER JOIN tbm_cabMovInv as a1 ON (a.co_empRelCabMovInv=a1.co_emp AND a.co_locRelCabMovInv=a1.co_loc AND a.co_tipDocRelCabMovInv=a1.co_tipDoc AND a.co_docRelCabMovInv=a1.co_doc)\n";
                strSQL+="  ) AS b2 ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipdoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc\n";
                strSQL+="  WHERE b1.st_reg IN ('A')\n";
                strSQL+="  AND (CASE WHEN b1.nd_CanIngImp=0 AND b1.nd_canAju=0 AND b1.nd_canConRea=0 THEN FALSE ELSE TRUE END)";//Se excluyen items que estaban en el Ingreso por importación y fueron eliminados al modificar el Ing.Imp.
                if (txtCodItmMae.getText().length() > 0) {
                    strSQL+=" AND b1.co_itmMae=" + txtCodItmMae.getText() +"\n";  
                }
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0){
                    strSQL+=" AND ((LOWER(b1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(b1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                strSQL+="  ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg\n";
                System.out.println("ZafImp28.cargarDetReg: "+strSQL);
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
                        vecReg.add(INT_TBL_DAT_CHK, null);
                        vecReg.add(INT_TBL_DAT_CODSEG,rst.getString("co_seg") );
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_EMP,rst.getString("co_itmEmp"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT2,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("tx_uniMed"));
                        vecReg.add(INT_TBL_DAT_COD_IMP,rst.getString("co_imp"));
                        vecReg.add(INT_TBL_DAT_NOM_IMP,rst.getString("tx_nomImp"));
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIPDOC,rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_NUM_PED,rst.getString("tx_numDoc2"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_CAN_INGIMP,rst.getString("nd_canIngImp"));
                        vecReg.add(INT_TBL_DAT_CAN_CONING,rst.getString("nd_canConIng"));
                        vecReg.add(INT_TBL_DAT_CAN_CONREA,rst.getString("nd_canConRea"));
                        vecReg.add(INT_TBL_DAT_CAN_AJU,rst.getString("nd_canAju"));
                        vecReg.add(INT_TBL_DAT_CAN_CONAJU,rst.getString("nd_canConAju"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_CON, null);
                        vecReg.add(INT_TBL_DAT_CAN_SOLTRS,rst.getString("nd_canSol"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_SOL, null);
                        vecReg.add(INT_TBL_DAT_CAN_TRS,rst.getString("nd_canTrs"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_TRS, null);
                        vecReg.add(INT_TBL_DAT_CAN_AUTTRS,rst.getString("nd_canAutTrs"));
                        vecReg.add(INT_TBL_DAT_BUT_ANE_AUT_TRS, null);
                        vecReg.add(INT_TBL_DAT_CAN_PEN,rst.getString("nd_canPen"));
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

                if (blnCon) {
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
    * Función que permite ejecutar las sentencias SQL que se encuentran en el SQL enviada al JDialogo
    * @param opcion
    * @return 
    */
    private boolean guardar()
    {
        boolean blnRes = true;
        try 
        {
            conAut = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conAut != null) 
            {
                //Ejecuta sentencia
                stm=conAut.createStatement();
                stm.executeUpdate(objImp28_04.getSQLAut().toString());
                stm.close();
                stm=null;
                conAut.close();
                conAut = null;
                
                //Desmarca todas las casillas
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
                    }                    
                }
                
                //Limpiar StringBuffer
                objImp28_04=null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } 
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }
    
    

}

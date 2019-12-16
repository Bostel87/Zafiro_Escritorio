/*
 * ZafCom75_04.java
 * Created on 9 de marzo de 2012, 9:12
 * v0.1 
 */
package Compras.ZafCom75;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import java.math.BigDecimal;
import java.math.BigInteger;
/**
 *
 * @author  Ingrid
 */
public class ZafCom75_04 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable:        
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP=2;
    final int INT_TBL_DAT_COD_LOC=3;
    final int INT_TBL_DAT_COD_TIP_DOC=4;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=6;
    final int INT_TBL_DAT_COD_DOC=7;
    final int INT_TBL_DAT_NUM_DOC_SIS=8;
    final int INT_TBL_DAT_NUM_PED_PRE=9;
    final int INT_TBL_DAT_FEC_DOC=10;

    //contendra el pedido previo que se cargara en la nota de pedido
    private ArrayList arlDatCab, arlRegCab;  //contendra la cabecera, sea referenciada o cargada a traves del boton consultar
    final int INT_TBL_ARL_COD_EMP=0;
    final int INT_TBL_ARL_COD_LOC=1;
    final int INT_TBL_ARL_COD_TIP_DOC=2;
    final int INT_TBL_ARL_DES_COR_TIP_DOC=3;
    final int INT_TBL_ARL_DES_LAR_TIP_DOC=4;
    final int INT_TBL_ARL_COD_DOC=5;
    final int INT_TBL_ARL_NUM_DOC_SIS=6;
    final int INT_TBL_ARL_NUM_DOC_PED=7;
    final int INT_TBL_ARL_FEC_DOC=8;

    //TABLA DE DETALLE
    public static final int INT_TBL_DAT_DET_LIN=0;
    public static final int INT_TBL_DAT_DET_CHK=1;
    public static final int INT_TBL_DAT_DET_COD_EMP=2;
    public static final int INT_TBL_DAT_DET_COD_LOC=3;
    public static final int INT_TBL_DAT_DET_COD_TIP_DOC=4;
    public static final int INT_TBL_DAT_DET_COD_DOC=5;
    public static final int INT_TBL_DAT_DET_COD_REG=6;
    public static final int INT_TBL_DAT_DET_COD_ITM_MAE=7;
    public static final int INT_TBL_DAT_DET_COD_ITM=8;
    public static final int INT_TBL_DAT_DET_COD_ALT_ITM=9;
    public static final int INT_TBL_DAT_DET_COD_LET_ITM=10;
    public static final int INT_TBL_DAT_DET_NOM_ITM=11;    
    public static final int INT_TBL_DAT_DET_CAN_SUM_NOT_PED=12;
    public static final int INT_TBL_DAT_DET_CAN_PED_PRE_DIS=13;
    public static final int INT_TBL_DAT_DET_CAN_PED_PRE=14;//pieza
    public static final int INT_TBL_DAT_DET_CAN_PED_PRE_AUX=15;
    public static final int INT_TBL_DAT_DET_CAN_PED_PRE_TON_MET=16;//tonelada metrica

    //ARREGLO DE DETALLE
    private ArrayList arlDatDet, arlRegDet;//contendra el detalle, sea referenciado o cargado a traves del boton consultar
    public static final int INT_TBL_ARL_DET_COD_EMP=0;
    public static final int INT_TBL_ARL_DET_COD_LOC=1;
    public static final int INT_TBL_ARL_DET_COD_TIP_DOC=2;
    public static final int INT_TBL_ARL_DET_COD_DOC=3;
    public static final int INT_TBL_ARL_DET_COD_REG=4;
    public static final int INT_TBL_ARL_DET_COD_ITM_MAE=5;
    public static final int INT_TBL_ARL_DET_COD_ITM=6;
    public static final int INT_TBL_ARL_DET_COD_ALT_ITM=7;
    public static final int INT_TBL_ARL_DET_COD_LET_ITM=8;
    public static final int INT_TBL_ARL_DET_NOM_ITM=9;    
    public static final int INT_TBL_ARL_DET_CAN_SUM_NOT_PED=10;
    public static final int INT_TBL_ARL_DET_CAN_PED_PRE_DIS=11;
    public static final int INT_TBL_ARL_DET_CAN_PED_PRE=12;    //Cantidad de pieza
    public static final int INT_TBL_ARL_DET_CAN_PED_PRE_TON_MET=13;//Cantidad de Tonelada metrica

    //Variable
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModDet;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuDet;
    private ZafTblFilCab objTblFilCab, objTblFilDet;
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkDet;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkDet;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAdaDet objMouMotAdaDet;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblBus objTblBus, objTblBusDet;
    private ZafTblOrd objTblOrd, objTblOrdDet;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblDet;
    private ZafThreadGUI objThrGUI;

    private Vector vecReg, vecDat, vecCab, vecAux;
    private char chrOpcManDatTooBar;
    private int intButPre;
    private String strNomPedSelUsr;
    private String strSQL;
    

    public ZafCom75_04(java.awt.Frame parent, boolean modal, ZafParSis obj, char opcionTooBar) {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        arlDatCab=new ArrayList();
        arlDatDet=new ArrayList();
        intButPre=0;//no se ha seleccionado el boton
        chrOpcManDatTooBar=opcionTooBar;
        if(configurarFrm()){
            if(configurarTablaDet()){
                if(cargarPedidosPrevios()){
                }
            }
        }
    }

    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(11);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC_SIS,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED_PRE,"Núm.Ped.Pre.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            
            objUti=new ZafUtil();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            vecCab.clear();
            vecCab=null;
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
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_PRE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(60);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                boolean blnChkSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblModDet.removeAllRows();
                    if(objTblMod.isChecked(intFil, intCol)){
                        cargarMovReg(intFil);
                    }                        
                    
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        if(i!=intFil){
                            objTblMod.setChecked(false, i, intCol);
                        }
                    }
                }

            });

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);

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
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC_SIS:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_NUM_PED_PRE:
                    strMsg="Número de pedido";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private boolean configurarTablaDet(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_DET_LIN,"");
            vecCab.add(INT_TBL_DAT_DET_CHK,"");
            vecCab.add(INT_TBL_DAT_DET_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_DET_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_DET_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DET_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DET_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_DET_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_DET_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_DET_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_DET_COD_LET_ITM,"Cód.Let.Itm.");
            vecCab.add(INT_TBL_DAT_DET_NOM_ITM,"Nombre del item");
            
            vecCab.add(INT_TBL_DAT_DET_CAN_SUM_NOT_PED,"Can.Ped.Emb.");
            vecCab.add(INT_TBL_DAT_DET_CAN_PED_PRE_DIS,"Can.Pen.Not.Ped.");
            vecCab.add(INT_TBL_DAT_DET_CAN_PED_PRE,"Can.Ped.Pre.");
            vecCab.add(INT_TBL_DAT_DET_CAN_PED_PRE_AUX,"Can.Not.Ped.Aux.");
            
            vecCab.add(INT_TBL_DAT_DET_CAN_PED_PRE_TON_MET,"Can.Ped.Pre.TM.");
            
            objTblModDet=new ZafTblMod();
            objTblModDet.setHeader(vecCab);
            tblDet.setModel(objTblModDet);

            //Configurar JTable: Establecer tipo de selección.
            tblDet.setRowSelectionAllowed(true);
            tblDet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuDet=new ZafTblPopMnu(tblDet);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDet.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_DET_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DET_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_REG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_LET_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DET_NOM_ITM).setPreferredWidth(150);
            
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_SUM_NOT_PED).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE_DIS).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE_AUX).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE_TON_MET).setPreferredWidth(75);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDet.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilDet=new ZafTblFilCab(tblDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_LIN).setCellRenderer(objTblFilDet);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDet=new ZafMouMotAdaDet();
            tblDet.getTableHeader().addMouseMotionListener(objMouMotAdaDet);
            //Configurar JTable: Editor de búsqueda.
            objTblBusDet=new ZafTblBus(tblDet);
            //configurar el ordenamiento en tabla de detalle
            objTblOrdDet=new ZafTblOrd(tblDet);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblDet=new ZafTblCelRenLbl();
            objTblCelRenLblDet.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDet.setTipoFormato(objTblCelRenLblDet.INT_FOR_GEN);
            objTblCelRenLblDet.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_ITM_MAE).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_ITM).setCellRenderer(objTblCelRenLblDet);
            objTblCelRenLblDet=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblDet=new ZafTblCelRenLbl();
            objTblCelRenLblDet.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDet.setTipoFormato(objTblCelRenLblDet.INT_FOR_NUM);
            objTblCelRenLblDet.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_SUM_NOT_PED).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE_DIS).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE_AUX).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE_TON_MET).setCellRenderer(objTblCelRenLblDet);
            
            objTblCelRenLblDet=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDet=new ZafTblCelRenChk();
            tblDet.getColumnModel().getColumn(INT_TBL_DAT_DET_CHK).setCellRenderer(objTblCelRenChkDet);
            objTblCelRenChkDet=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDet=new ZafTblCelEdiChk(tblDet);
            tblDet.getColumnModel().getColumn(INT_TBL_DAT_DET_CHK).setCellEditor(objTblCelEdiChkDet);

            objTblCelEdiChkDet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeValCanPndNotPed=new BigDecimal(BigInteger.ZERO);
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDet.getSelectedRow();
                    bdeValCanPndNotPed=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_DIS)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_DIS).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_DIS).toString()));
                    if(bdeValCanPndNotPed.compareTo(new BigDecimal(BigInteger.ZERO))<=0){
                        objTblCelEdiChkDet.setCancelarEdicion(true);
                    }
                    else{
                        objTblCelEdiChkDet.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblModDet.isChecked(intFilSel, INT_TBL_DAT_DET_CHK)){
                        objTblModDet.setValueAt(bdeValCanPndNotPed, intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE);
                    }
                    else{
                        objTblModDet.setValueAt("0", intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE);
                    }
                }

            });

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_CAN_PED_PRE).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeValCanPndNotPed=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValCanPedEmb=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdeValCanPedEmbAnt=new BigDecimal(BigInteger.ZERO);

                BigDecimal bdeValCanPedEmbAux=new BigDecimal(BigInteger.ZERO);
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDet.getSelectedRow();
                    bdeValCanPndNotPed=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_DIS)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_DIS).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_DIS).toString()));
                    bdeValCanPedEmbAnt=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE).toString()));
                    bdeValCanPedEmbAux=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_AUX)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_AUX).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE_AUX).toString()));
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeValCanPedEmb=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE).toString()));
                    if( (chrOpcManDatTooBar=='x')  ||  (chrOpcManDatTooBar=='m')  ){//por consulta
                        if(bdeValCanPedEmbAux.add(bdeValCanPndNotPed).compareTo(bdeValCanPedEmb)<0){

                            if(msgPermitirValorExcedidoEmbarque()){
                                objTblModDet.setValueAt(bdeValCanPedEmb, intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE);//se coloca el valor ingresado
                                if(bdeValCanPedEmb.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DAT_DET_CHK);
                            }
                            else{
                                objTblModDet.setValueAt(bdeValCanPedEmbAnt, intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE);//se deja el valor que estaba antes de intentar cambiar
                                if(bdeValCanPedEmbAnt.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DAT_DET_CHK);
                            }
                        }
                        else{
                                objTblModDet.setChecked(true, intFilSel, INT_TBL_DAT_DET_CHK);
                        }
                    }
                    else{
                        if(bdeValCanPedEmb.compareTo(bdeValCanPndNotPed)>0){
                            if(msgPermitirValorExcedidoEmbarque()){
                                objTblModDet.setValueAt(bdeValCanPedEmb, intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE);
                                if(bdeValCanPedEmb.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DAT_DET_CHK);
                            }
                            else{
                                objTblModDet.setValueAt(bdeValCanPedEmbAnt, intFilSel, INT_TBL_DAT_DET_CAN_PED_PRE);
                                if(bdeValCanPedEmbAnt.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DAT_DET_CHK);
                            }
                            
                        }
                        else{
                            objTblModDet.setChecked(true, intFilSel, INT_TBL_DAT_DET_CHK);
                        }
                    }
                }
            });

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_DET_CHK);
            vecAux.add("" + INT_TBL_DAT_DET_CAN_PED_PRE);
            objTblModDet.setColumnasEditables(vecAux);
            vecAux=null;

            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_NO_EDI);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_COD_EMP, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_COD_LOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_COD_TIP_DOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_COD_DOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_COD_REG, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_COD_ITM_MAE, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_COD_ITM, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_CAN_SUM_NOT_PED, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_CAN_PED_PRE_AUX, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_DET_CAN_PED_PRE_DIS, tblDet);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    

    private class ZafMouMotAdaDet extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDet.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_DET_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_DET_COD_LOC:
                    strMsg="Código de local";
                    break;
                case INT_TBL_DAT_DET_COD_TIP_DOC:
                    strMsg="Código de tipo de documento";
                    break;
                case INT_TBL_DAT_DET_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_DET_COD_REG:
                    strMsg="Código de registro";
                    break;
                case INT_TBL_DAT_DET_COD_ITM_MAE:
                    strMsg="Código de item maestro";
                    break;
                case INT_TBL_DAT_DET_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_DET_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_DET_COD_LET_ITM:
                    strMsg="Código en letras del item";
                    break;
                case INT_TBL_DAT_DET_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DET_CAN_SUM_NOT_PED:
                    strMsg="Cantidad total aplicada en Nota de Pedido";
                    break;
                case INT_TBL_DAT_DET_CAN_PED_PRE_DIS:
                    strMsg="Cantidad disponible para aplicar";
                    break;
                case INT_TBL_DAT_DET_CAN_PED_PRE:
                    strMsg="Cantidad de pedido previo";
                    break;
                case INT_TBL_DAT_DET_CAN_PED_PRE_AUX:
                    strMsg="Cantidad del pedido previo auxiliar(sirve para modificaciones)";
                    break;
                case INT_TBL_DAT_DET_CAN_PED_PRE_TON_MET:
                    strMsg="Cantidad del pedido por peso";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDet.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDet = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPgrSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de Notas de Pedido");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.5);
        sppRpt.setOneTouchExpandable(true);

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

        sppRpt.setLeftComponent(spnDat);

        panDet.setLayout(new java.awt.BorderLayout());

        tblDet.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet.setViewportView(tblDet);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        sppRpt.setRightComponent(panDet);

        panGenDet.add(sppRpt, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panSubBot.add(butCon);

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBot.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPgrSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPgrSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPgrSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPgrSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPgrSis, java.awt.BorderLayout.EAST);

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if( (objTblMod.isDataModelChanged()) || (objTblModDet.isDataModelChanged()) ){
            intButPre=1;//se presiono aceptar
        }
        else{
            intButPre=2;//se presiono aceptar pero se lo toma como cancelar porque no cambio nada en nincuna de las dos tablas
        }
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intButPre=2;//se presiono cancelar
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (butCon.getText().equals("Consultar")){
            //blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            intButPre=2;//se presiono la X de cerrar formulario
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panPgrSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDet;
    // End of variables declaration//GEN-END:variables
     
    private boolean msgPermitirValorExcedidoEmbarque(){
        boolean blnRes=false;
        String strTit="", strMsg="";
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="<HTML>El valor ingresado es mayor al valor de la Nota Previa.<BR>¿Está seguro que desea continuar?</HTML>";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                blnRes=true;
            }
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
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if(isNotaPedidoMarcada()){//SE CONSULTA
                //Limpiar objetos.
                if (!cargarPedidosPrevios()){
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0)
                {
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
                objTblMod.setDataModelChanged(true);
                objTblModDet.setDataModelChanged(true);
            }
            else{//NO SE HACE NADA
            }
            objThrGUI=null;
        }
    }
    
    private boolean cargarMovReg(int fila){
        boolean blnRes=true;
        int intUltPosTbl=-1;
        BigDecimal bdeCanPedPre=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeCanPedPreTonMet=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeSumCanNotPed=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeCanDisPedPre=new BigDecimal(BigInteger.ZERO);
        try{
            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_INS);
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
                strSQL+="     , a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                strSQL+="     , b1.nd_can, b1.nd_pestotkgr";
                strSQL+=" FROM tbm_detNotPedPreImp AS b1 INNER JOIN";
                strSQL+=" (";
                strSQL+="   (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                strSQL+="    LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                strSQL+="  )";
                strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                strSQL+=" WHERE b1.co_emp=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP) + "";
                strSQL+=" AND b1.co_loc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC) + "";
                strSQL+=" AND b1.co_tipDoc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC) + "";
                strSQL+=" AND b1.co_doc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC) + "";
                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
                strSQL+="        , a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                strSQL+="        , b1.nd_can, b1.nd_pestotkgr";
                strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    intUltPosTbl=objTblModDet.getRowCountTrue();
                    objTblModDet.insertRow(intUltPosTbl);
                    objTblModDet.setValueAt(new Boolean(true), intUltPosTbl, INT_TBL_DAT_DET_CHK);
                    objTblModDet.setValueAt(rst.getString("co_emp"), intUltPosTbl, INT_TBL_DAT_DET_COD_EMP);
                    objTblModDet.setValueAt(rst.getString("co_loc"), intUltPosTbl, INT_TBL_DAT_DET_COD_LOC);
                    objTblModDet.setValueAt(rst.getString("co_tipDoc"), intUltPosTbl, INT_TBL_DAT_DET_COD_TIP_DOC);
                    objTblModDet.setValueAt(rst.getString("co_doc"), intUltPosTbl, INT_TBL_DAT_DET_COD_DOC);
                    objTblModDet.setValueAt(rst.getString("co_reg"), intUltPosTbl, INT_TBL_DAT_DET_COD_REG);                    
                    objTblModDet.setValueAt(rst.getString("co_itmMae"), intUltPosTbl, INT_TBL_DAT_DET_COD_ITM_MAE);
                    objTblModDet.setValueAt(rst.getString("co_itm"), intUltPosTbl, INT_TBL_DAT_DET_COD_ITM);
                    objTblModDet.setValueAt(rst.getString("tx_codAlt"), intUltPosTbl, INT_TBL_DAT_DET_COD_ALT_ITM);
                    objTblModDet.setValueAt(rst.getString("tx_codAlt2"), intUltPosTbl, INT_TBL_DAT_DET_COD_LET_ITM);
                    objTblModDet.setValueAt(rst.getString("tx_nomItm"), intUltPosTbl, INT_TBL_DAT_DET_NOM_ITM);

                    bdeCanPedPre=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                    bdeSumCanNotPed=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                    bdeCanDisPedPre=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                    bdeCanPedPreTonMet=new BigDecimal(rst.getObject("nd_pestotkgr")==null?"0":(rst.getString("nd_pestotkgr").equals("")?"0":rst.getString("nd_pestotkgr")));
                    if(bdeCanPedPreTonMet.compareTo(new BigDecimal("0"))>0)
                        bdeCanPedPreTonMet=bdeCanPedPreTonMet.divide(new BigDecimal("1000"), 0, BigDecimal.ROUND_HALF_UP);
                    
                    objTblModDet.setValueAt("0", intUltPosTbl, INT_TBL_DAT_DET_CAN_SUM_NOT_PED);
                    objTblModDet.setValueAt(bdeCanPedPre, intUltPosTbl, INT_TBL_DAT_DET_CAN_PED_PRE_DIS);
                    objTblModDet.setValueAt(bdeCanPedPre, intUltPosTbl, INT_TBL_DAT_DET_CAN_PED_PRE);
                    objTblModDet.setValueAt("0", intUltPosTbl, INT_TBL_DAT_DET_CAN_PED_PRE_AUX);
                    objTblModDet.setValueAt(bdeCanPedPreTonMet, intUltPosTbl, INT_TBL_DAT_DET_CAN_PED_PRE_TON_MET);
                    
                }
                objTblModDet.setModoOperacion(objTblModDet.INT_TBL_NO_EDI);
                objTblModDet.removeEmptyRows();
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

    private boolean isNotaPedidoMarcada(){
        boolean blnRes=true;
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="<HTML>Tenga presente que los registros quedarán sin selección.<BR>¿Está seguro que desea realizar una consulta.?</HTML>";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                objTblMod.removeAllRows();
                objTblModDet.removeAllRows();
            }
            else{
                blnRes=false;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;//debe retornar true,
        }
        return blnRes;
    }

    private boolean cargarPedidosPrevios(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" ,a2.tx_desCor AS tx_desCorTipDoc, a2.tx_desLar AS tx_desLarTipDoc";
                strSQL+=" FROM (tbm_cabNotPedPreImp AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_detNotPedPreImp AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.st_reg='A' AND a1.st_cie='N'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" ,a2.tx_desCor, a2.tx_desLar";
                strSQL+=" ORDER BY a1.tx_numDoc2";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,             null);
                    vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCorTipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLarTipDoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC_SIS,     rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_DAT_NUM_PED_PRE,     rst.getString("tx_numDoc2"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));                        
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

    public ArrayList getCabeceraSeleccionada(){
        try{
            if(intButPre==1){//se refresca el arreglo con los nuevos datos
                arlDatCab.clear();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        arlRegCab=new ArrayList();
                        arlRegCab.add(INT_TBL_ARL_COD_EMP,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP),3));
                        arlRegCab.add(INT_TBL_ARL_COD_LOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC),3));
                        arlRegCab.add(INT_TBL_ARL_COD_TIP_DOC,      objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC),3));
                        arlRegCab.add(INT_TBL_ARL_DES_COR_TIP_DOC,  objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_TIP_DOC),1));
                        arlRegCab.add(INT_TBL_ARL_DES_LAR_TIP_DOC,  objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_LAR_TIP_DOC),1));
                        arlRegCab.add(INT_TBL_ARL_COD_DOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC),3));
                        arlRegCab.add(INT_TBL_ARL_NUM_DOC_SIS,      objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC_SIS),1));
                        arlRegCab.add(INT_TBL_ARL_NUM_DOC_PED,      objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE),1));
                        arlRegCab.add(INT_TBL_ARL_FEC_DOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FEC_DOC),1));
                        arlDatCab.add(arlRegCab);
                    }
                }
            }
            else{//no se refresca y se mantienen los datos anteriores
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatCab;
    }

    public ArrayList getDetalleSeleccionado(){
        try{
            if(intButPre==1){//se refresca el arreglo con los nuevos datos
                arlDatDet.clear();
                for(int i=0; i<objTblModDet.getRowCountTrue(); i++){
                    if(objTblModDet.isChecked(i, INT_TBL_DAT_DET_CHK)){
                        arlRegDet=new ArrayList();
                        arlRegDet.add(INT_TBL_ARL_DET_COD_EMP,              objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_EMP));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_LOC,              objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_LOC));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_TIP_DOC,          objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_TIP_DOC));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_DOC,              objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_DOC));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_REG,              objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_REG));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_ITM_MAE,          objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_ITM_MAE));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_ITM,              objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_ALT_ITM,          objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_ALT_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_LET_ITM,          objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_LET_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_NOM_ITM,              objTblModDet.getValueAt(i, INT_TBL_DAT_DET_NOM_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_SUM_NOT_PED,      objTblModDet.getValueAt(i, INT_TBL_DAT_DET_CAN_SUM_NOT_PED));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_PED_PRE_DIS,      objTblModDet.getValueAt(i, INT_TBL_DAT_DET_CAN_PED_PRE_DIS));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_PED_PRE,          objTblModDet.getValueAt(i, INT_TBL_DAT_DET_CAN_PED_PRE));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_PED_PRE_TON_MET,  objTblModDet.getValueAt(i, INT_TBL_DAT_DET_CAN_PED_PRE_TON_MET));
                        arlDatDet.add(arlRegDet);
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatDet;
    }


    public int getBotonPresionado() {
        return intButPre;
    }

    public void setBotonPresionado(int intButPre) {
        this.intButPre = intButPre;
    }


    public void setModoOperacionTooBar(char opcionTooBar){
        chrOpcManDatTooBar=opcionTooBar;
    }

    public String getNotaPedidoSeleccionado() {
        strNomPedSelUsr="";
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                if(strNomPedSelUsr.equals(""))
                    strNomPedSelUsr="" + (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE).toString());
                else
                    strNomPedSelUsr+="/" + (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED_PRE).toString());
            }
        }
        return strNomPedSelUsr;
    }
 
    

}
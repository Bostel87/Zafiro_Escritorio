/*
 * ZafImp33_03.java
 * "Ventana de consulta de notas de pedido listas"
 *
 */

package Importaciones.ZafImp33;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
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
public class ZafImp33_03 extends javax.swing.JDialog 
{
    //Constantes: Cabecera - Columnas del JTable "TblDat" 
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_CHK=1;
    private static final int INT_TBL_DAT_COD_EMP=2;
    private static final int INT_TBL_DAT_COD_LOC=3;
    private static final int INT_TBL_DAT_COD_TIP_DOC=4;
    private static final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    private static final int INT_TBL_DAT_DES_LAR_TIP_DOC=6;
    private static final int INT_TBL_DAT_COD_DOC=7;
    private static final int INT_TBL_DAT_NUM_DOC=8;
    private static final int INT_TBL_DAT_NUM_PED=9;
    private static final int INT_TBL_DAT_FEC_DOC=10;
    private static final int INT_TBL_DAT_COD_EXP=11;
    private static final int INT_TBL_DAT_NOM_EXP=12;
    private static final int INT_TBL_DAT_NOM_ALT_EXP=13;
    private static final int INT_TBL_DAT_VAL_DOC=14;
    private static final int INT_TBL_DAT_TIP_NOT_PED=15;
    private static final int INT_TBL_DAT_COD_IMP=16;
    
    //Constantes: Detalle - Columnas del JTable "TblDet" 
    private static final int INT_TBL_DET_LIN=0;
    private static final int INT_TBL_DET_CHK=1;
    private static final int INT_TBL_DET_COD_ITM_MAE=2;
    private static final int INT_TBL_DET_COD_ITM=3;
    private static final int INT_TBL_DET_COD_ALT_ITM=4;
    private static final int INT_TBL_DET_COD_LET_ITM=5;
    private static final int INT_TBL_DET_NOM_ITM=6;
    private static final int INT_TBL_DET_CAN_TON_MET=7;        //Cantidad de Tonelada metrica
    private static final int INT_TBL_DET_CAN_NOT_PED=8;
    private static final int INT_TBL_DET_CAN_PED_EMB=9;
    private static final int INT_TBL_DET_CAN_PEN_NOT_PED=10;
    private static final int INT_TBL_DET_CAN_UTI_PED_EMB=11;
    private static final int INT_TBL_DET_COD_EXP=12;           //Código de exportador
    private static final int INT_TBL_DET_NOM_EXP=13;           //Exportador    
    private static final int INT_TBL_DET_COD_EMP=14;
    private static final int INT_TBL_DET_COD_LOC=15;
    private static final int INT_TBL_DET_COD_TIP_DOC=16;
    private static final int INT_TBL_DET_COD_DOC=17;
    private static final int INT_TBL_DET_COD_REG=18;
    private static final int INT_TBL_DET_PES=19;               //Peso del item
    private static final int INT_TBL_DET_COD_ARA=20;           //Codigo Arancel
    private static final int INT_TBL_DET_NOM_ARA=21;           //Nombre Arancel
    private static final int INT_TBL_DET_DES_ARA=22;           //Descripcion Arancel
    private static final int INT_TBL_DET_POR_ARA=23;           //Porcentaje Arancel
    private static final int INT_TBL_DET_PRE_UNI=24;           //Precio Unitario
    private static final int INT_TBL_DET_TOT_FOB_CFR=25;       //Total de FOB / CFR    
    private static final int INT_TBL_DET_VAL_FLE=26;           //Valor de flete
    private static final int INT_TBL_DET_VAL_CFR=27;           //Valor CFR
    private static final int INT_TBL_DET_VAL_CFR_ARA=28;       //Valor CFR * Porcentaje de arancel    
    private static final int INT_TBL_DET_VAL_ISD=29;           //Valor ISD * Porcentaje de ISD
    private static final int INT_TBL_DET_CHK_ISD_CRE_TRI=30;   //ISD: SI es credito tributario o NO.    
    private static final int INT_TBL_DET_TOT_ARA=31;           //Total de arancel
    private static final int INT_TBL_DET_TOT_GAS=32;           //Total de gasto
    private static final int INT_TBL_DET_TOT_COS=33;           //Total de costo
    private static final int INT_TBL_DET_COS_UNI=34;           //Costo unitario
    private static final int INT_TBL_DET_CAN_PED_EMB_AUX=35;

    //ArrayList: Cabecera
    private ArrayList arlDatCab, arlRegCab;
    public static final int INT_TBL_ARL_DAT_COD_EMP=0;
    public static final int INT_TBL_ARL_DAT_COD_LOC=1;
    public static final int INT_TBL_ARL_DAT_COD_TIP_DOC=2;
    public static final int INT_TBL_ARL_DAT_DES_COR_TIP_DOC=3;
    public static final int INT_TBL_ARL_DAT_DES_LAR_TIP_DOC=4;
    public static final int INT_TBL_ARL_DAT_COD_DOC=5;
    public static final int INT_TBL_ARL_DAT_NUM_DOC=6;
    public static final int INT_TBL_ARL_DAT_NUM_PED=7;
    public static final int INT_TBL_ARL_DAT_FEC_DOC=8;
    public static final int INT_TBL_ARL_DAT_COD_EXP=9;
    public static final int INT_TBL_ARL_DAT_NOM_EXP=10;
    public static final int INT_TBL_ARL_DAT_NOM_ALT_EXP=11;
    public static final int INT_TBL_ARL_DAT_VAL_DOC=12;
    public static final int INT_TBL_ARL_DAT_TIP_NOT_PED=13;
    public static final int INT_TBL_ARL_DAT_COD_IMP=14;
    
    //ArrayList: Detalle
    private ArrayList arlDatDet, arlRegDet;
    public static final int INT_TBL_ARL_DET_COD_ITM_MAE=0;
    public static final int INT_TBL_ARL_DET_COD_ITM=1;
    public static final int INT_TBL_ARL_DET_COD_ALT_ITM=2;
    public static final int INT_TBL_ARL_DET_COD_LET_ITM=3;
    public static final int INT_TBL_ARL_DET_NOM_ITM=4;

    public static final int INT_TBL_ARL_DET_CAN_TON_MET=5;     //Cantidad de Tonelada metrica
    public static final int INT_TBL_ARL_DET_CAN_NOT_PED=6;
    public static final int INT_TBL_ARL_DET_CAN_PZA=7;
    public static final int INT_TBL_ARL_DET_CAN_PEN_NOT_PED=8;
    public static final int INT_TBL_ARL_DET_CAN_UTI_PED_EMB=9;
    public static final int INT_TBL_ARL_DET_COD_EXP=10;        //Codigo Exportador
    public static final int INT_TBL_ARL_DET_NOM_EXP=11;        //Nombre Exportador
    public static final int INT_TBL_ARL_DET_COD_EMP=12;
    public static final int INT_TBL_ARL_DET_COD_LOC=13;
    public static final int INT_TBL_ARL_DET_COD_TIP_DOC=14;
    public static final int INT_TBL_ARL_DET_COD_DOC=15;
    public static final int INT_TBL_ARL_DET_COD_REG=16;
    public static final int INT_TBL_ARL_DET_PES=17;            //Peso del item
    public static final int INT_TBL_ARL_DET_COD_ARA=18;        //Codigo Arancel
    public static final int INT_TBL_ARL_DET_NOM_ARA=19;        //Nombre Arancel
    public static final int INT_TBL_ARL_DET_DES_ARA=20;        //Descripcion Arancel
    public static final int INT_TBL_ARL_DET_POR_ARA=21;        //Porcentaje Arancel
    public static final int INT_TBL_ARL_DET_PRE_UNI=22;        //Precio Unitario
    public static final int INT_TBL_ARL_DET_TOT_FOB_CFR=23;    //Total de FOB / CFR    
    public static final int INT_TBL_ARL_DET_VAL_FLE=24;        //Valor de flete
    public static final int INT_TBL_ARL_DET_VAL_CFR=25;        //Valor CFR
    public static final int INT_TBL_ARL_DET_VAL_CFR_ARA=26;    //Valor CFR * Porcentaje de arancel
    public static final int INT_TBL_ARL_DET_VAL_ISD=27;        //Valor ISD * Porcentaje de ISD
    public static final int INT_TBL_ARL_DET_CHK_ISD_CRE_TRI=28;//ISD: SI es credito tributario o NO.    
    public static final int INT_TBL_ARL_DET_TOT_ARA=29;        //Total de arancel
    public static final int INT_TBL_ARL_DET_TOT_GAS=30;        //Total de gasto
    public static final int INT_TBL_ARL_DET_TOT_COS=31;        //Total de costo
    public static final int INT_TBL_ARL_DET_COS_UNI=32;        //Costo unitario
    public static final int INT_TBL_ARL_DET_CAN_PED_EMB_AUX=33;//Cantidad Pedido Embarcado auxiliar
    public static final int INT_TBL_ARL_DET_EST_REG=34;        //Estado    
    
    //ArrayList: Filas Eliminadas
    private ArrayList arlRegImp33_03FilEli;
    static final int INT_CFE_IMP33_03_COD_EMP=0;               //Columnas de las filas eliminadas: Código de la empresa
    static final int INT_CFE_IMP33_03_COD_LOC=1;               //Columnas de las filas eliminadas: Código del local
    static final int INT_CFE_IMP33_03_COD_TIP_DOC=2;           //Columnas de las filas eliminadas: Código del tipo de documento
    static final int INT_CFE_IMP33_03_COD_DOC=3;               //Columnas de las filas eliminadas: Código del documento.
    static final int INT_CFE_IMP33_03_COD_REG=4;               //Columnas de las filas eliminadas: Código del registro.
    static final int INT_CFE_IMP33_03_CAN_PED_EMB_AUX=5;       //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    static final int INT_CFE_IMP33_03_CAN_UTI_PED_EMB=6;       //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    static final int INT_CFE_IMP33_03_CAN_NOT_PED=7;           //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    static final int INT_CFE_IMP33_03_CAN_PED_EMB=8;           //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    static final int INT_CFE_IMP33_03_CAN_PEN_NOT_PED=9;       //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    static final int INT_CFE_IMP33_03_COD_EXP=10;              //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    static final int INT_CFE_IMP33_03_NOM_EXP=11;              //Columnas de las filas eliminadas: Cantidad que esta guardada en la db, esta es la que se debe reversar
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModDet;
    private ZafTblEdi objTblEdi;                                     //Editor: Editor del JTable.
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuDet;
    private ZafTblFilCab objTblFilCab, objTblFilDet;
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkDet;     //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkDet;     //Editor: JCheckBox en celda.    
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAdaDet objMouMotAdaDet;
    private ZafTblBus objTblBus, objTblBusDet;
    private ZafTblOrd objTblOrd, objTblOrdDet;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblDet;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    
    private ZafImp33_02 objImp33_02;

    private Vector vecReg, vecDatDat, vecDatCab, vecAux;
    private Vector vecDetDat, vecDetCab;

    private int intButPre;
    private char chrOpcManDatTooBar;
    private String strSQL;
    private String strTipNotPed, strNomPedSelUsr;
    
    public ArrayList arlRegExpPed, arlDatExpPed;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_EMP=0;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_LOC=1;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC=2;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_DOC=3;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_EXP=4;
    public static final int INT_ARL_EXP_PED_IMP33_03_NOM_EXP=5;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_CTA_ACT=6;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_CTA_PAS=7;
    public static final int INT_ARL_EXP_PED_IMP33_03_TOT_DIA=8;
    public static final int INT_ARL_EXP_PED_IMP33_03_COD_IMP=9;

    public ArrayList arlRegExpPedDb, arlDatExpPedDb;

    public ZafImp33_03(java.awt.Frame parent, boolean modal, ZafParSis obj, String tipoNotaPedido, String codigoExportador, ZafImp33_02 objImp33_02Ref, char opcionTooBar) {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        strTipNotPed=tipoNotaPedido;
        arlDatExpPed=new ArrayList();
        arlDatExpPedDb=new ArrayList();
        intButPre=0;//no se ha seleccionado el boton
        objImp33_02=objImp33_02Ref;
        chrOpcManDatTooBar=opcionTooBar;
        configurarFrm();
    }
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            arlDatCab=new ArrayList();
            arlDatDet=new ArrayList();
        
            //Configurar JTables
            configurarTblDat();
            configurarTblDet();
            cargarNotasPedido();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatDat=new Vector();    //Almacena los datos
            vecDatCab=new Vector(17);  //Almacena las cabeceras
            vecDatCab.clear();
            vecDatCab.add(INT_TBL_DAT_LIN,"");
            vecDatCab.add(INT_TBL_DAT_CHK,"");
            vecDatCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecDatCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecDatCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecDatCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecDatCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecDatCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecDatCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecDatCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            vecDatCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecDatCab.add(INT_TBL_DAT_COD_EXP,"Cód.Exp.");
            vecDatCab.add(INT_TBL_DAT_NOM_EXP,"Nom.Exp.");
            vecDatCab.add(INT_TBL_DAT_NOM_ALT_EXP,"Nom.Alt.Exp.");
            vecDatCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecDatCab.add(INT_TBL_DAT_TIP_NOT_PED,"Tip.Not.Ped.");
            vecDatCab.add(INT_TBL_DAT_COD_IMP,"Cód.Imp.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecDatCab);
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
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ALT_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(30);
           
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
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EXP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_EXP, tblDat);

//            if(objParSis.getCodigoMenu()==895){
//                if(objPerUsr.isOpcionEnabled(896)){//consultar
//                    butCon.setVisible(true);
//                    butCon.setEnabled(true);
//                }
//                if(objPerUsr.isOpcionEnabled(897)){//cerrar
//                    butCer.setVisible(true);
//                    butCer.setEnabled(true);
//                }
//            }

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
                boolean blnCarDet=true;
                boolean blnManSel=false;
                String strTipNotPedFilSel="";
                int isFilSel=0;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    objTblCelEdiChk.setCancelarEdicion(false);
                    strTipNotPedFilSel=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_NOT_PED)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_NOT_PED).toString();
                    if(objTblMod.isChecked(intFil, intCol)){
                        if(isNotaPedidoDesmarcadaItemsDesmarcados(intFil)){
                            blnManSel=true;//mantener la seleccion del registro
                            blnCarDet=false;
                        }
                        else{
                            blnCarDet=false;
                            blnManSel=false;
                        }
                    }
                    else{//si no esta seleccionado
                        isFilSel=0;
                        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                                isFilSel++;
                        }
                        if(isFilSel==0){
                            strTipNotPed="";
                        }
                        blnCarDet=true;
                        if( (strTipNotPed.equals("")) ){
                            strTipNotPed=strTipNotPedFilSel;
                            objTblCelEdiChk.setCancelarEdicion(false);                            
                        }
                        if(! ( strTipNotPed.equals(strTipNotPedFilSel) )  ){
                            mostrarMsgInf("<HTML>La nota de pedido que desea seleccionar, tiene un tipo de nota de pedido diferente.<BR>Sólo se permiten seleccionar notas de pedido con el mismo tipo.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            objTblCelEdiChk.setCancelarEdicion(true);
                        }                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(blnCarDet){
                        cargarMovDet(intFil);
                    }
                    if(!blnManSel)
                        objTblCelEdiChk.setChecked(true, intFil, INT_TBL_DAT_CHK);
                }
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
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
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número de pedido";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_COD_EXP:
                    strMsg="Código del exportador";
                    break;
                case INT_TBL_DAT_NOM_EXP:
                    strMsg="Nombre del exportador";
                    break;
                case INT_TBL_DAT_NOM_ALT_EXP:
                    strMsg="Nombre alterno del exportador";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor de documento";
                    break;
                case INT_TBL_DAT_TIP_NOT_PED:
                    strMsg="Tipo de nota de pedido";
                    break;
                case INT_TBL_DAT_COD_IMP:
                    strMsg="Código de Importador";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private boolean configurarTblDet(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDetDat=new Vector();    //Almacena los datos
            vecDetCab=new Vector(36);  //Almacena las cabeceras
            vecDetCab.clear();
            vecDetCab.add(INT_TBL_DET_LIN,"");
            vecDetCab.add(INT_TBL_DET_CHK,"");
            vecDetCab.add(INT_TBL_DET_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecDetCab.add(INT_TBL_DET_COD_ITM,    "Cód.Itm.");
            vecDetCab.add(INT_TBL_DET_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecDetCab.add(INT_TBL_DET_COD_LET_ITM,"Cód.Let.Itm.");
            vecDetCab.add(INT_TBL_DET_NOM_ITM,    "Nombre del item");
            vecDetCab.add(INT_TBL_DET_CAN_TON_MET,"Can.Ton.Met.");
            vecDetCab.add(INT_TBL_DET_CAN_NOT_PED,"Can.Not.Ped.");
            vecDetCab.add(INT_TBL_DET_CAN_PED_EMB,"Can.Ped.Emb.");
            vecDetCab.add(INT_TBL_DET_CAN_PEN_NOT_PED,"Can.Pen.Not.Ped.");
            vecDetCab.add(INT_TBL_DET_CAN_UTI_PED_EMB,"Can.Uti.Ped.Emb.");
            vecDetCab.add(INT_TBL_DET_COD_EXP,"Cód.Exp.");
            vecDetCab.add(INT_TBL_DET_NOM_EXP,"Exportador");            
            vecDetCab.add(INT_TBL_DET_COD_EMP,"Cód.Emp.");
            vecDetCab.add(INT_TBL_DET_COD_LOC,"Cód.Loc.");
            vecDetCab.add(INT_TBL_DET_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecDetCab.add(INT_TBL_DET_COD_DOC,"Cód.Doc.");
            vecDetCab.add(INT_TBL_DET_COD_REG,"Cód.Reg.");
            vecDetCab.add(INT_TBL_DET_PES,    "Peso");
            vecDetCab.add(INT_TBL_DET_COD_ARA,"Cód.Ara.");
            vecDetCab.add(INT_TBL_DET_NOM_ARA,"Nom.Ara.");
            vecDetCab.add(INT_TBL_DET_DES_ARA,"Des.Ara.");
            vecDetCab.add(INT_TBL_DET_POR_ARA,"Por.Ara.");
            vecDetCab.add(INT_TBL_DET_PRE_UNI,"Pre.Uni.");
            vecDetCab.add(INT_TBL_DET_TOT_FOB_CFR,"Tot.Fob.Cfr");
            vecDetCab.add(INT_TBL_DET_VAL_FLE,"Val.Fle.");
            vecDetCab.add(INT_TBL_DET_VAL_CFR,"Val.Cfr.");
            vecDetCab.add(INT_TBL_DET_VAL_CFR_ARA,"Val.Cfr.Ara.");
            vecDetCab.add(INT_TBL_DET_VAL_ISD,    "Val.ISD");
            vecDetCab.add(INT_TBL_DET_CHK_ISD_CRE_TRI,"Cre.Tri.");            
            vecDetCab.add(INT_TBL_DET_TOT_ARA,"Tot.Ara.");
            vecDetCab.add(INT_TBL_DET_TOT_GAS,"Tot.Gas.");
            vecDetCab.add(INT_TBL_DET_TOT_COS,"Tot.Cos.");
            vecDetCab.add(INT_TBL_DET_COS_UNI,"Cos.Uni.");
            vecDetCab.add(INT_TBL_DET_CAN_PED_EMB_AUX,"Can.Ped.Emb.Aux.");

            objTblModDet=new ZafTblMod();
            objTblModDet.setHeader(vecDetCab);
            tblDet.setModel(objTblModDet);

            //Configurar JTable: Establecer tipo de selección.
            tblDet.setRowSelectionAllowed(true);
            tblDet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuDet=new ZafTblPopMnu(tblDet);
            objTblPopMnuDet.setMarcarCasillasVerificacionEnabled(true);
            objTblPopMnuDet.setDesmarcarCasillasVerificacionEnabled(true);
            
            objTblPopMnuDet.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intFilSel[];
                BigDecimal bgdValCanPndNotPed=BigDecimal.ZERO;
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilSel=tblDet.getSelectedRows();
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    for (int i=0; i<intFilSel.length; i++){
                        bgdValCanPndNotPed=new BigDecimal(objTblModDet.getValueAt(intFilSel[i], INT_TBL_DET_CAN_PEN_NOT_PED)==null?"0":(objTblModDet.getValueAt(intFilSel[i], INT_TBL_DET_CAN_PEN_NOT_PED).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel[i], INT_TBL_DET_CAN_PEN_NOT_PED).toString()));
                        if(bgdValCanPndNotPed.compareTo(new BigDecimal(BigInteger.ZERO))<=0){
                            objTblCelEdiChkDet.setCancelarEdicion(true);
                        }
                        else{
                            objTblCelEdiChkDet.setCancelarEdicion(false);
                        }
                        
                        if(objTblPopMnuDet.isClickMarcarCasillasVerificacion()){
                            objTblModDet.setChecked(true, intFilSel[i], INT_TBL_DET_CHK);
                            objTblModDet.setValueAt(bgdValCanPndNotPed, intFilSel[i], INT_TBL_DET_CAN_PED_EMB);
                        }
                        else if(objTblPopMnuDet.isClickDesmarcarCasillasVerificacion()){
                            objTblModDet.setChecked(false, intFilSel[i], INT_TBL_DET_CHK);
                            objTblModDet.setValueAt("0", intFilSel[i], INT_TBL_DET_CAN_PED_EMB);
                            agregarItemNotaPedidoArregloEliminados(intFilSel[i]);
                        }
                    }
                }
            });  
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDet.getColumnModel();
            tcmAux.getColumn(INT_TBL_DET_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DET_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DET_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_COD_LET_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DET_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DET_CAN_TON_MET).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DET_CAN_NOT_PED).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DET_CAN_PED_EMB).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DET_CAN_PEN_NOT_PED).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DET_CAN_UTI_PED_EMB).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DET_COD_EXP).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DET_NOM_EXP).setPreferredWidth(90);    
            tcmAux.getColumn(INT_TBL_DET_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COD_REG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_PES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COD_ARA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_NOM_ARA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_DES_ARA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_POR_ARA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_PRE_UNI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_TOT_FOB_CFR).setPreferredWidth(30);            
            tcmAux.getColumn(INT_TBL_DET_VAL_FLE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_VAL_CFR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_VAL_CFR_ARA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_VAL_ISD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_CHK_ISD_CRE_TRI).setPreferredWidth(30);            
            tcmAux.getColumn(INT_TBL_DET_TOT_ARA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_TOT_GAS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_TOT_COS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COS_UNI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_CAN_PED_EMB_AUX).setPreferredWidth(30);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDet.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilDet=new ZafTblFilCab(tblDet);
            tcmAux.getColumn(INT_TBL_DET_LIN).setCellRenderer(objTblFilDet);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDet=new ZafMouMotAdaDet();
            tblDet.getTableHeader().addMouseMotionListener(objMouMotAdaDet);
            //Configurar JTable: Editor de búsqueda.
            objTblBusDet=new ZafTblBus(tblDet);
            //configurar el ordenamiento en tabla de detalle
            objTblOrdDet=new ZafTblOrd(tblDet);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_ITM_MAE, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_ITM, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_CAN_UTI_PED_EMB, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_EMP, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_LOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_TIP_DOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_DOC, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_REG, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COD_ARA, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_NOM_ARA, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_DES_ARA, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_POR_ARA, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_PRE_UNI, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_TOT_FOB_CFR, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_PES, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_VAL_FLE, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_VAL_CFR, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_VAL_CFR_ARA, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_VAL_ISD, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_CHK_ISD_CRE_TRI, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_TOT_ARA, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_TOT_GAS, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_TOT_COS, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_COS_UNI, tblDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DET_CAN_PED_EMB_AUX, tblDet);            

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblDet=new ZafTblCelRenLbl();
            objTblCelRenLblDet.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDet.setTipoFormato(objTblCelRenLblDet.INT_FOR_GEN);
            objTblCelRenLblDet.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DET_COD_ITM_MAE).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DET_COD_ITM).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DET_COD_EXP).setCellRenderer(objTblCelRenLblDet);
            objTblCelRenLblDet=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblDet=new ZafTblCelRenLbl();
            objTblCelRenLblDet.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDet.setTipoFormato(objTblCelRenLblDet.INT_FOR_NUM);
            objTblCelRenLblDet.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DET_CAN_TON_MET).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DET_CAN_NOT_PED).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DET_CAN_PED_EMB).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DET_CAN_PEN_NOT_PED).setCellRenderer(objTblCelRenLblDet);
            tcmAux.getColumn(INT_TBL_DET_CAN_UTI_PED_EMB).setCellRenderer(objTblCelRenLblDet);           
            objTblCelRenLblDet=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDet=new ZafTblCelRenChk();
            tblDet.getColumnModel().getColumn(INT_TBL_DET_CHK).setCellRenderer(objTblCelRenChkDet);
            objTblCelRenChkDet=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDet=new ZafTblCelEdiChk(tblDet);
            tblDet.getColumnModel().getColumn(INT_TBL_DET_CHK).setCellEditor(objTblCelEdiChkDet);

            objTblCelEdiChkDet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bgdValCanPndNotPed=new BigDecimal(BigInteger.ZERO);
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDet.getSelectedRow();
                    bgdValCanPndNotPed=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PEN_NOT_PED)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PEN_NOT_PED).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PEN_NOT_PED).toString()));
                    if(bgdValCanPndNotPed.compareTo(new BigDecimal(BigInteger.ZERO))<=0){
                        objTblCelEdiChkDet.setCancelarEdicion(true);
                    }
                    else{
                        objTblCelEdiChkDet.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblModDet.isChecked(intFilSel, INT_TBL_DET_CHK)){
                        objTblModDet.setValueAt(bgdValCanPndNotPed, intFilSel, INT_TBL_DET_CAN_PED_EMB);
                    }
                    else{
                        objTblModDet.setValueAt("0", intFilSel, INT_TBL_DET_CAN_PED_EMB);
                        agregarItemNotaPedidoArregloEliminados(intFilSel);
                    }
                }

            });

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDet);
            tcmAux.getColumn(INT_TBL_DET_CAN_PED_EMB).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bgdValCanPndNotPed=new BigDecimal(BigInteger.ZERO);
                BigDecimal bgdValCanPedEmb=new BigDecimal(BigInteger.ZERO);
                BigDecimal bgdValCanPedEmbAnt=new BigDecimal(BigInteger.ZERO);
                BigDecimal bgdValCanPedEmbAux=new BigDecimal(BigInteger.ZERO);
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDet.getSelectedRow();
                    bgdValCanPndNotPed=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PEN_NOT_PED)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PEN_NOT_PED).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PEN_NOT_PED).toString()));
                    bgdValCanPedEmbAnt=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB).toString()));
                    bgdValCanPedEmbAux=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB_AUX)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB_AUX).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB_AUX).toString()));
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bgdValCanPedEmb=new BigDecimal(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB)==null?"0":(objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB).toString().equals("")?"0":objTblModDet.getValueAt(intFilSel, INT_TBL_DET_CAN_PED_EMB).toString()));
                    if( (chrOpcManDatTooBar=='x')  ||  (chrOpcManDatTooBar=='m')  ){//por consulta
                        if(bgdValCanPedEmbAux.add(bgdValCanPndNotPed).compareTo(bgdValCanPedEmb)<0){

                            if(msgPermitirValorExcedidoEmbarque()){
                                objTblModDet.setValueAt(bgdValCanPedEmb, intFilSel, INT_TBL_DET_CAN_PED_EMB);//se coloca el valor ingresado
                                if(bgdValCanPedEmb.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DET_CHK);
                            }
                            else{
                                objTblModDet.setValueAt(bgdValCanPedEmbAnt, intFilSel, INT_TBL_DET_CAN_PED_EMB);//se deja el valor que estaba antes de intentar cambiar
                                if(bgdValCanPedEmbAnt.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DET_CHK);
                            }
                        }
                        else{
                            objTblModDet.setChecked(true, intFilSel, INT_TBL_DET_CHK);
                        }
                    }
                    else{
                        if(bgdValCanPedEmb.compareTo(bgdValCanPndNotPed)>0){
                            if(msgPermitirValorExcedidoEmbarque()){
                                objTblModDet.setValueAt(bgdValCanPedEmb, intFilSel, INT_TBL_DET_CAN_PED_EMB);
                                if(bgdValCanPedEmb.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DET_CHK);
                            }
                            else{
                                objTblModDet.setValueAt(bgdValCanPedEmbAnt, intFilSel, INT_TBL_DET_CAN_PED_EMB);
                                if(bgdValCanPedEmbAnt.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    objTblModDet.setChecked(true, intFilSel, INT_TBL_DET_CHK);
                            }
                        }
                        else{
                            objTblModDet.setChecked(true, intFilSel, INT_TBL_DET_CHK);
                        }
                    }
                }
            });

            //Establecer columnas editables
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DET_CHK);
            vecAux.add("" + INT_TBL_DET_CAN_PED_EMB);
            objTblModDet.setColumnasEditables(vecAux);
            vecAux=null;

            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);
            
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
                case INT_TBL_DET_COD_ITM_MAE:
                    strMsg="Código de item maestro";
                    break;
                case INT_TBL_DET_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DET_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DET_COD_LET_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DET_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DET_CAN_NOT_PED:
                    strMsg="Cantidad de la nota de pedido";
                    break;
                case INT_TBL_DET_CAN_PEN_NOT_PED:
                    strMsg="Cantidad pendiente de la nota de pedido";
                    break;
                case INT_TBL_DET_CAN_PED_EMB:
                    strMsg="Cantidad del pedido";
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
        butCon = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
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

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if( (objTblMod.isDataModelChanged()) || (objTblModDet.isDataModelChanged()) ){//Si existen cambios
            getNombrePedidoSeleccionado();
            intButPre=1;//se presiono aceptar
        }
        else{ //No ha cambiado
            intButPre=2; //Se presionó aceptar pero se lo toma como cancelar porque no cambio nada en nincuna de las dos tablas
        }
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intButPre=2;//se presiono cancelar
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (butCon.getText().equals("Consultar")){
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
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDet;
    // End of variables declaration//GEN-END:variables
           
    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean msgPermitirValorExcedidoEmbarque(){
        boolean blnRes=false;
        String strTit="", strMsg="";
        try{

            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="<HTML>El valor ingresado es mayor al rango permitido(10% del valor de la nota de pedido).<BR>¿Está seguro que desea continuar?</HTML>";
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
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar �sta clase
     * ya que si no sólo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if(isNotaPedidoMarcada()){//SE CONSULTA
                //Limpiar objetos.
                if (!cargarNotasPedido()){
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
            objThrGUI=null;
        }
    }    
    
    private boolean cargarNotasPedido(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed, d1.tx_numDoc2, d1.tx_numDoc3, d1.ne_numdoc";
                strSQL+="      , d1.tx_desCor, d1.tx_desLar, d1.nd_valdoc, d1.st_notpedlis, d1.co_exp, d1.tx_nomExp, d1.tx_nomAltExp, d1.ne_tipnotped, d1.co_ctaAct, d1.co_ctaPas, d1.co_imp";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_regNotPed, c1.fe_doc, c1.tx_mesNotPed, c1.tx_numDoc2, c1.tx_numDoc3";
                strSQL+=" 	     , c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal";
                strSQL+=" 	     , c1.tx_desCor, c1.tx_desLar, c1.ne_numdoc";
                strSQL+=" 	     , c1.nd_valdoc, c1.st_notpedlis";
                strSQL+="            , c1.co_exp, c1.tx_nomExp, c1.tx_nomAltExp, c1.ne_tipnotped, c1.co_ctaAct, c1.co_ctaPas, c1.co_imp";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.ne_numdoc, b1.tx_numDoc2, b1.tx_numDoc3";
                strSQL+=" 		     , b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+=" 		     , (b1.nd_canNotPed - CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END  ) AS nd_canNotPedFal";
                strSQL+="                    , b1.tx_desCor, b1.tx_desLar";
                strSQL+="                    , b1.nd_valdoc, b1.st_notpedlis";
                strSQL+="                    , b1.co_exp, b1.tx_nomExp, b1.tx_nomAltExp, b1.ne_tipnotped, b1.co_regNotPed, b1.co_ctaAct, b1.co_ctaPas, b1.co_imp";
                strSQL+=" 		FROM(	";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.ne_numdoc, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+=" 			     , a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+=" 			     , a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb";
                strSQL+="                            , j2.tx_desCor, j2.tx_desLar, a1.nd_valdoc, a1.st_notpedlis, j5.co_exp, j5.tx_nom AS tx_nomExp";
                strSQL+="                            , j5.tx_nom2 AS tx_nomAltExp, a1.ne_tipnotped, a4.co_reg AS co_regNotPed, a2.co_regRel, a1.co_ctaAct, a1.co_ctaPas, a1.co_imp ";
                strSQL+=" 			FROM ((tbm_cabNotPedImp AS a1 INNER JOIN tbm_cabTipDoc AS j2";
                strSQL+="                               ON (a1.co_emp=j2.co_emp AND a1.co_loc=j2.co_loc AND a1.co_tipDoc=j2.co_tipDoc)";
                strSQL+="                               INNER JOIN tbm_expImp AS j5 ON(a1.co_exp=j5.co_exp)";
                strSQL+=" 			)";
                strSQL+=" 			INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+=" 			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+=" 			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+=" 			LEFT OUTER JOIN ";
                strSQL+=" 			(tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+=" 			 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a3.st_reg NOT IN('I','E')";
                strSQL+=" 			)";
                strSQL+=" 			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+=" 			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 			AND a1.st_reg NOT IN('E','I')	AND a1.st_notpedlis='S'	AND a1.st_cie='N'";
                strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.ne_numdoc, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+=" 			       , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can";
                strSQL+="                              , j2.tx_desCor, j2.tx_desLar, a1.nd_valdoc, a1.st_notpedlis";
                strSQL+="                              , j5.co_exp, j5.tx_nom, j5.tx_nom2, a1.ne_tipnotped, a4.co_reg, a2.co_regRel, a1.co_ctaAct, a1.co_ctaPas, a1.co_imp";
                strSQL+=" 			ORDER BY a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.tx_numDoc3, b1.nd_canNotPed";
                strSQL+="                      , b1.tx_desCor, b1.tx_desLar, b1.ne_numdoc, b1.nd_valdoc, b1.st_notpedlis, b1.co_exp, b1.tx_nomExp, b1.tx_nomAltExp, b1.ne_tipnotped, b1.co_regNotPed, b1.co_ctaAct, b1.co_ctaPas, b1.co_imp";
                strSQL+=" 	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+=" ) AS d1";
                strSQL+=" WHERE d1.nd_canNotPedFal > 0";
                strSQL+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed, d1.tx_numDoc2, d1.tx_numDoc3, d1.ne_numdoc";
                strSQL+=" 	 , d1.tx_desCor, d1.tx_desLar, d1.nd_valdoc, d1.st_notpedlis, d1.co_exp, d1.tx_nomExp, d1.tx_nomAltExp, d1.ne_tipnotped, d1.co_ctaAct, d1.co_ctaPas, d1.co_imp";
                strSQL+=" ORDER BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc";
                //System.out.println("SQL cargarNotasPedido: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,             null);
                    vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numDoc3"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));
                    vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));
                    vecReg.add(INT_TBL_DAT_NOM_ALT_EXP,     rst.getString("tx_nomAltExp"));
                    vecReg.add(INT_TBL_DAT_VAL_DOC,         rst.getString("nd_valdoc"));
                    vecReg.add(INT_TBL_DAT_TIP_NOT_PED,     rst.getString("ne_tipnotped"));
                    vecReg.add(INT_TBL_DAT_COD_IMP,         rst.getString("co_imp"));
                    vecDatDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDatDat);
                tblDat.setModel(objTblMod);
                vecDatDat.clear();
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

    private boolean cargarMovDet(int fila)
    {
        boolean blnRes=true;
        int intUltPosTbl=-1;
        BigDecimal bgdCanUtiPedEmb=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanNotPedPza=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanNotPedTon=new BigDecimal(BigInteger.ZERO);
        try{
            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_INS);
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
                strSQL+="      , a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                strSQL+="      , a5.co_parara, a5.tx_par, a5.tx_des, b1.co_exp, b2.tx_nom as tx_nomExp";
                strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                strSQL+="      , b1.nd_can";
                //strSQL+="      , ((b1.nd_can*0.10)+b1.nd_can) AS nd_can";//Alfredo dijo que se puede ingresar hasta el 10% del valor de la nota de pedido adicional
                strSQL+="     , b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valISD, b1.st_impISDCreTri, b1.nd_valTotAra";
                strSQL+="     , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, b1.nd_canUtiPedEmb";
                strSQL+=" FROM tbm_detNotPedImp AS b1 INNER JOIN";
                strSQL+=" (";
                strSQL+="    ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                strSQL+=" 	LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                strSQL+="    )";
                strSQL+="    LEFT OUTER JOIN";
                strSQL+="    (   tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                strSQL+=" 	 INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                strSQL+="    ) ON a1.co_grpImp=a4.co_grp";
                strSQL+=" )";
                strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                strSQL+=" INNER JOIN tbm_expImp AS b2 ON(b1.co_exp=b2.co_exp)";
                strSQL+=" WHERE b1.co_emp=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP) + "";
                strSQL+=" AND b1.co_loc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC) + "";
                strSQL+=" AND b1.co_tipDoc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC) + "";
                strSQL+=" AND b1.co_doc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC) + "";
                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
                strSQL+="        , a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                strSQL+="        , a5.co_parara, a5.tx_par, a5.tx_des, b1.co_exp, b2.tx_nom, b1.nd_ara";
                strSQL+="        , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valISD, b1.st_impISDCreTri, b1.nd_valTotAra";
                strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, b1.nd_canUtiPedEmb";
                strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                //System.out.println("cargarMovDet:" +strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    intUltPosTbl=objTblModDet.getRowCountTrue();
                    objTblModDet.insertRow(intUltPosTbl);
                    objTblModDet.setValueAt(rst.getString("co_itmMae"), intUltPosTbl, INT_TBL_DET_COD_ITM_MAE);
                    objTblModDet.setValueAt(rst.getString("co_itm"), intUltPosTbl, INT_TBL_DET_COD_ITM);
                    objTblModDet.setValueAt(rst.getString("tx_codAlt"), intUltPosTbl, INT_TBL_DET_COD_ALT_ITM);
                    objTblModDet.setValueAt(rst.getString("tx_codAlt2"), intUltPosTbl, INT_TBL_DET_COD_LET_ITM);
                    objTblModDet.setValueAt(rst.getString("tx_nomItm"), intUltPosTbl, INT_TBL_DET_NOM_ITM);

                    bgdCanUtiPedEmb=new BigDecimal(rst.getObject("nd_canUtiPedEmb")==null?"0":(rst.getString("nd_canUtiPedEmb").equals("")?"0":rst.getString("nd_canUtiPedEmb")));
                    bgdCanNotPedPza=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                    bgdCanNotPedTon=new BigDecimal(rst.getObject("nd_canTonMet")==null?"0":(rst.getString("nd_canTonMet").equals("")?"0":rst.getString("nd_canTonMet")));

                    objTblModDet.setValueAt(bgdCanNotPedTon, intUltPosTbl, INT_TBL_DET_CAN_TON_MET);
                    objTblModDet.setValueAt(bgdCanNotPedPza, intUltPosTbl, INT_TBL_DET_CAN_NOT_PED);
                    objTblModDet.setValueAt("0", intUltPosTbl, INT_TBL_DET_CAN_PED_EMB);
                    objTblModDet.setValueAt((bgdCanNotPedPza.subtract(bgdCanUtiPedEmb)), intUltPosTbl, INT_TBL_DET_CAN_PEN_NOT_PED);
                    objTblModDet.setValueAt(bgdCanUtiPedEmb, intUltPosTbl, INT_TBL_DET_CAN_UTI_PED_EMB);
                    objTblModDet.setValueAt(rst.getString("co_exp"), intUltPosTbl, INT_TBL_DET_COD_EXP);
                    objTblModDet.setValueAt(rst.getString("tx_nomExp"), intUltPosTbl, INT_TBL_DET_NOM_EXP);
                    objTblModDet.setValueAt(rst.getString("co_emp"), intUltPosTbl, INT_TBL_DET_COD_EMP);
                    objTblModDet.setValueAt(rst.getString("co_loc"), intUltPosTbl, INT_TBL_DET_COD_LOC);
                    objTblModDet.setValueAt(rst.getString("co_tipDoc"), intUltPosTbl, INT_TBL_DET_COD_TIP_DOC);
                    objTblModDet.setValueAt(rst.getString("co_doc"), intUltPosTbl, INT_TBL_DET_COD_DOC);
                    objTblModDet.setValueAt(rst.getString("co_reg"), intUltPosTbl, INT_TBL_DET_COD_REG);
                    objTblModDet.setValueAt(rst.getString("nd_pesitmkgr"), intUltPosTbl, INT_TBL_DET_PES);
                    objTblModDet.setValueAt(rst.getString("co_parara"), intUltPosTbl, INT_TBL_DET_COD_ARA);
                    objTblModDet.setValueAt(rst.getString("tx_par"), intUltPosTbl, INT_TBL_DET_NOM_ARA);
                    objTblModDet.setValueAt(rst.getString("tx_des"), intUltPosTbl, INT_TBL_DET_DES_ARA);
                    objTblModDet.setValueAt(rst.getString("nd_ara"), intUltPosTbl, INT_TBL_DET_POR_ARA);
                    objTblModDet.setValueAt(rst.getString("nd_preUni"), intUltPosTbl, INT_TBL_DET_PRE_UNI);
                    objTblModDet.setValueAt(rst.getString("nd_valTotFobCfr"), intUltPosTbl, INT_TBL_DET_TOT_FOB_CFR);
                    
                    objTblModDet.setValueAt(rst.getString("nd_valFle"), intUltPosTbl, INT_TBL_DET_VAL_FLE);
                    objTblModDet.setValueAt(rst.getString("nd_valCfr"), intUltPosTbl, INT_TBL_DET_VAL_CFR);
                    objTblModDet.setValueAt("0" , intUltPosTbl, INT_TBL_DET_VAL_CFR_ARA);
                    
                    objTblModDet.setValueAt(rst.getString("nd_valISD"), intUltPosTbl, INT_TBL_DET_VAL_ISD);
                    
                    /* Cuando la partida arancelaria aplica al costo, NO genera credito Tributario. */
                    String strChkISD=rst.getObject("st_impISDCreTri")==null?"N":(rst.getString("st_impISDCreTri").equals("")?"N":rst.getString("st_impISDCreTri"));
                    if(strChkISD.equals("S")){
                        objTblModDet.setValueAt(new Boolean(true), intUltPosTbl, INT_TBL_DET_CHK_ISD_CRE_TRI);
                    }  
                    else{
                        objTblModDet.setValueAt(null, intUltPosTbl, INT_TBL_DET_CHK_ISD_CRE_TRI);
                    }
                    
                    objTblModDet.setValueAt(rst.getString("nd_valTotAra"), intUltPosTbl, INT_TBL_DET_TOT_ARA);
                    objTblModDet.setValueAt(rst.getString("nd_valTotGas"), intUltPosTbl, INT_TBL_DET_TOT_GAS);
                    objTblModDet.setValueAt(rst.getString("nd_valTotCos"), intUltPosTbl, INT_TBL_DET_TOT_COS);
                    objTblModDet.setValueAt(rst.getString("nd_cosUni"), intUltPosTbl, INT_TBL_DET_COS_UNI);
                    objTblModDet.setValueAt("0", intUltPosTbl, INT_TBL_DET_CAN_PED_EMB_AUX);
                }
                objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);
                objTblModDet.removeEmptyRows();

                rst.close();
                rst=null;
                stm.close();
                stm=null;
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

    public boolean setNotasPedidoSeleccionadas(ArrayList arregloCabecera)
    {
        boolean blnRes=true;
        try{
            arlDatCab=arregloCabecera;
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed, d1.tx_numDoc2, d1.tx_numDoc3, d1.ne_numdoc";
                strSQL+="      , d1.tx_desCor, d1.tx_desLar, d1.nd_valdoc, d1.st_notpedlis, d1.co_exp, d1.tx_nomExp, d1.tx_nomAltExp, d1.ne_tipnotped, d1.co_imp";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_regNotPed, c1.fe_doc, c1.tx_mesNotPed, c1.tx_numDoc2, c1.tx_numDoc3";
                strSQL+=" 	     , c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal, c1.tx_desCor, c1.tx_desLar, c1.ne_numdoc";
                strSQL+=" 	     , c1.nd_valdoc, c1.st_notpedlis, c1.co_exp, c1.tx_nomExp, c1.tx_nomAltExp, c1.ne_tipnotped, c1.co_imp";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.ne_numdoc, b1.tx_numDoc2, b1.tx_numDoc3";
                strSQL+=" 		     , b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+=" 		     , (b1.nd_canNotPed - CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END ) AS nd_canNotPedFal";
                strSQL+="                    , b1.tx_desCor, b1.tx_desLar";
                strSQL+="                    , b1.nd_valdoc, b1.st_notpedlis";
                strSQL+="                    , b1.co_exp, b1.tx_nomExp, b1.tx_nomAltExp, b1.ne_tipnotped, b1.co_regNotPed, b1.co_imp";
                strSQL+=" 		FROM(	";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.ne_numdoc, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+=" 			     , a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+=" 			     , a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb, j2.tx_desCor, j2.tx_desLar, a1.nd_valdoc, a1.st_notpedlis";
                strSQL+="                            , j5.co_exp, j5.tx_nom AS tx_nomExp, j5.tx_nom2 AS tx_nomAltExp, a1.ne_tipnotped, a4.co_reg AS co_regNotPed, a2.co_regRel, a1.co_imp ";
                strSQL+=" 			FROM ((tbm_cabNotPedImp AS a1 INNER JOIN tbm_cabTipDoc AS j2 ON (a1.co_emp=j2.co_emp AND a1.co_loc=j2.co_loc AND a1.co_tipDoc=j2.co_tipDoc)";
                strSQL+="                              INNER JOIN tbm_expImp AS j5 ON(a1.co_exp=j5.co_exp) )";
                strSQL+=" 			       INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+=" 			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+=" 			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+=" 			LEFT OUTER JOIN ";
                strSQL+=" 			(tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+=" 			 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a3.st_reg NOT IN('I','E')";
                strSQL+=" 			)";
                strSQL+=" 			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+=" 			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 			AND a1.st_reg NOT IN('E','I') AND a1.st_notpedlis='S' AND a1.st_cie='N'";
                strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.ne_numdoc, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+=" 			       , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can";
                strSQL+="                              , j2.tx_desCor, j2.tx_desLar, a1.nd_valdoc, a1.st_notpedlis";
                strSQL+="                              , j5.co_exp, j5.tx_nom, j5.tx_nom2, a1.ne_tipnotped, a4.co_reg, a2.co_regRel, a1.co_imp";
                strSQL+=" 			ORDER BY a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.tx_numDoc3, b1.nd_canNotPed";
                strSQL+="                      , b1.tx_desCor, b1.tx_desLar, b1.ne_numdoc, b1.nd_valdoc, b1.st_notpedlis, b1.co_exp, b1.tx_nomExp, b1.tx_nomAltExp, b1.ne_tipnotped, b1.co_regNotPed, b1.co_imp";
                strSQL+=" 	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+=" ) AS d1";
                strSQL+=" WHERE d1.nd_canNotPedFal > 0";
                strSQL+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.tx_mesNotPed, d1.tx_numDoc2, d1.tx_numDoc3, d1.ne_numdoc";
                strSQL+=" 	 , d1.tx_desCor, d1.tx_desLar, d1.nd_valdoc, d1.st_notpedlis, d1.co_exp, d1.tx_nomExp, d1.tx_nomAltExp, d1.ne_tipnotped, d1.co_imp";
                strSQL+=" ORDER BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc";
                System.out.println("setNotasPedidoSeleccionadas: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatDat.clear();
                //Obtener los registros.
                while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,             null);
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numdoc"));
                        vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numDoc3"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));
                        vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));
                        vecReg.add(INT_TBL_DAT_NOM_ALT_EXP,     rst.getString("tx_nomAltExp"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,         rst.getString("nd_valdoc"));
                        vecReg.add(INT_TBL_DAT_TIP_NOT_PED,     rst.getString("ne_tipnotped"));
                        vecReg.add(INT_TBL_DAT_COD_IMP,         rst.getString("co_imp"));
                        vecDatDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDatDat);
                tblDat.setModel(objTblMod);
                vecDatDat.clear();

                String strArlCodEmp="", strArlCodLoc="", strArlCodTipDoc="", strArlCodDoc="";
                String strTblCodEmp="", strTblCodLoc="", strTblCodTipDoc="", strTblCodDoc="";

                //System.out.println("arlDatCab: " + arlDatCab.toString());

                if(arlDatCab!=null){
                    for(int i=0; i<arlDatCab.size(); i++){
                        strArlCodEmp=objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_EMP)==null?"":objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_EMP).toString();
                        strArlCodLoc=objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_LOC)==null?"":objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_LOC).toString();
                        strArlCodTipDoc=objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_TIP_DOC)==null?"":objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_TIP_DOC).toString();
                        strArlCodDoc=objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_DOC)==null?"":objUti.getStringValueAt(arlDatCab, i, INT_TBL_ARL_DAT_COD_DOC).toString();

                        for(int k=0; k<objTblMod.getRowCountTrue(); k++){
                            strTblCodEmp=objTblMod.getValueAt(k, INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_EMP).toString();
                            strTblCodLoc=objTblMod.getValueAt(k, INT_TBL_DAT_COD_LOC)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_LOC).toString();
                            strTblCodTipDoc=objTblMod.getValueAt(k, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_TIP_DOC).toString();
                            strTblCodDoc=objTblMod.getValueAt(k, INT_TBL_DAT_COD_DOC)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_DOC).toString();
                            if( (strArlCodEmp.equals(strTblCodEmp)) && (strArlCodLoc.equals(strTblCodLoc)) && (strArlCodTipDoc.equals(strTblCodTipDoc)) && (strArlCodDoc.equals(strTblCodDoc)) ){
                                objTblMod.setValueAt(new Boolean(true), k, INT_TBL_DAT_CHK);
                                break;
                            }
                        }
                    }
                }

                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
   
    private boolean isNotaPedidoDesmarcadaItemsDesmarcados(int fila)
    {
        boolean blnRes=true;
        String strCodEmpCab="", strCodEmpDet="";
        String strCodLocCab="", strCodLocDet="";
        String strCodTipDocCab="", strCodTipDocDet="";
        String strCodDocCab="", strCodDocDet="";
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="<HTML>¿Está seguro que desea quitar la selección de esta Nota de pedido?<BR>Tenga presente que el detalle asociado también será eliminado</HTML>";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                strCodEmpCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP).toString();
                strCodLocCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString();
                strCodTipDocCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString();
                strCodDocCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString();

                objTblModDet.setModoOperacion(objTblModDet.INT_TBL_INS);
                for(int i=(objTblModDet.getRowCountTrue()-1); i>=0; i--){
                    strCodEmpDet=objTblModDet.getValueAt(i, INT_TBL_DET_COD_EMP)==null?"":objTblModDet.getValueAt(i, INT_TBL_DET_COD_EMP).toString();
                    strCodLocDet=objTblModDet.getValueAt(i, INT_TBL_DET_COD_LOC)==null?"":objTblModDet.getValueAt(i, INT_TBL_DET_COD_LOC).toString();
                    strCodTipDocDet=objTblModDet.getValueAt(i, INT_TBL_DET_COD_TIP_DOC)==null?"":objTblModDet.getValueAt(i, INT_TBL_DET_COD_TIP_DOC).toString();
                    strCodDocDet=objTblModDet.getValueAt(i, INT_TBL_DET_COD_DOC)==null?"":objTblModDet.getValueAt(i, INT_TBL_DET_COD_DOC).toString();
                    if( (strCodEmpCab.equals(strCodEmpDet)) && (strCodLocCab.equals(strCodLocDet)) && (strCodTipDocCab.equals(strCodTipDocDet)) && (strCodDocCab.equals(strCodDocDet)) ){
                        if(agregarItemsNotaPedidoArregloEliminadosCabecera(fila)){
                            objTblModDet.removeRow(i);
                        }
                    }
                }
                objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);
                objTblModDet.removeEmptyRows();
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


    private boolean isNotaPedidoMarcada(){
        boolean blnRes=true;
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="<HTML>Tenga presente que los registros quedarán sin selección.<BR>¿Está seguro que desea realizar una consulta.?</HTML>";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                if(agregarItemsNotaPedidoArregloEliminados()){
                    objTblMod.removeAllRows();
                    objTblModDet.removeAllRows();
                }
                else
                    blnRes=false;
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

    public ArrayList getCabeceraSeleccionada(){
        
        try{
            if(intButPre==1){//se refresca el arreglo con los nuevos datos
                arlDatCab.clear();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                        arlRegCab=new ArrayList();
                        arlRegCab.add(INT_TBL_ARL_DAT_COD_EMP,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP),3));
                        arlRegCab.add(INT_TBL_ARL_DAT_COD_LOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC),3));
                        arlRegCab.add(INT_TBL_ARL_DAT_COD_TIP_DOC,      objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC),3));
                        arlRegCab.add(INT_TBL_ARL_DAT_DES_COR_TIP_DOC,  objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_TIP_DOC),1));
                        arlRegCab.add(INT_TBL_ARL_DAT_DES_LAR_TIP_DOC,  objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_LAR_TIP_DOC),1));
                        arlRegCab.add(INT_TBL_ARL_DAT_COD_DOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC),3));
                        arlRegCab.add(INT_TBL_ARL_DAT_NUM_DOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC),1));
                        arlRegCab.add(INT_TBL_ARL_DAT_NUM_PED,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED),1));
                        arlRegCab.add(INT_TBL_ARL_DAT_FEC_DOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FEC_DOC),1));
                        arlRegCab.add(INT_TBL_ARL_DAT_COD_EXP,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EXP),3));
                        arlRegCab.add(INT_TBL_ARL_DAT_NOM_EXP,          objTblMod.getValueAt(i, INT_TBL_DAT_NOM_EXP));
                        arlRegCab.add(INT_TBL_ARL_DAT_NOM_ALT_EXP,      objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ALT_EXP));
                        arlRegCab.add(INT_TBL_ARL_DAT_VAL_DOC,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_DOC),3));
                        arlRegCab.add(INT_TBL_ARL_DAT_TIP_NOT_PED,      objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_TIP_NOT_PED),3));
                        arlRegCab.add(INT_TBL_ARL_DAT_COD_IMP,          objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_IMP),3));
                        arlDatCab.add(arlRegCab);
                    }
                }
            }
            else{//no se refresca y se mantienen los datos anteriores
            }
            //System.out.println("arlDatCab: " +arlDatCab);
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
                    if(objTblModDet.isChecked(i, INT_TBL_DET_CHK)){
                        arlRegDet=new ArrayList();
                        arlRegDet.add(INT_TBL_ARL_DET_COD_ITM_MAE,  objTblModDet.getValueAt(i, INT_TBL_DET_COD_ITM_MAE));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_ITM,      objTblModDet.getValueAt(i, INT_TBL_DET_COD_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_ALT_ITM,  objTblModDet.getValueAt(i, INT_TBL_DET_COD_ALT_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_LET_ITM,  objTblModDet.getValueAt(i, INT_TBL_DET_COD_LET_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_NOM_ITM,      objTblModDet.getValueAt(i, INT_TBL_DET_NOM_ITM));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_TON_MET,  objTblModDet.getValueAt(i, INT_TBL_DET_CAN_TON_MET));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_NOT_PED,  objTblModDet.getValueAt(i, INT_TBL_DET_CAN_NOT_PED));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_PZA,      objTblModDet.getValueAt(i, INT_TBL_DET_CAN_PED_EMB));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_PEN_NOT_PED,  objTblModDet.getValueAt(i, INT_TBL_DET_CAN_PEN_NOT_PED));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_UTI_PED_EMB,  objTblModDet.getValueAt(i, INT_TBL_DET_CAN_UTI_PED_EMB));    
                        arlRegDet.add(INT_TBL_ARL_DET_COD_EXP,      objTblModDet.getValueAt(i, INT_TBL_DET_COD_EXP));
                        
                        arlRegDet.add(INT_TBL_ARL_DET_NOM_EXP,      objTblModDet.getValueAt(i, INT_TBL_DET_NOM_EXP));   
                        arlRegDet.add(INT_TBL_ARL_DET_COD_EMP,      objTblModDet.getValueAt(i, INT_TBL_DET_COD_EMP));
                        
                        arlRegDet.add(INT_TBL_ARL_DET_COD_LOC,      objTblModDet.getValueAt(i, INT_TBL_DET_COD_LOC));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_TIP_DOC,  objTblModDet.getValueAt(i, INT_TBL_DET_COD_TIP_DOC));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_DOC,      objTblModDet.getValueAt(i, INT_TBL_DET_COD_DOC));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_REG,      objTblModDet.getValueAt(i, INT_TBL_DET_COD_REG));
                        arlRegDet.add(INT_TBL_ARL_DET_PES,          objTblModDet.getValueAt(i, INT_TBL_DET_PES));
                        arlRegDet.add(INT_TBL_ARL_DET_COD_ARA,      objTblModDet.getValueAt(i, INT_TBL_DET_COD_ARA));
                        arlRegDet.add(INT_TBL_ARL_DET_NOM_ARA,      objTblModDet.getValueAt(i, INT_TBL_DET_NOM_ARA));
                        arlRegDet.add(INT_TBL_ARL_DET_DES_ARA,      objTblModDet.getValueAt(i, INT_TBL_DET_DES_ARA));
                        arlRegDet.add(INT_TBL_ARL_DET_POR_ARA,      objTblModDet.getValueAt(i, INT_TBL_DET_POR_ARA));
                        
                        arlRegDet.add(INT_TBL_ARL_DET_PRE_UNI,      objTblModDet.getValueAt(i, INT_TBL_DET_PRE_UNI));
                        arlRegDet.add(INT_TBL_ARL_DET_TOT_FOB_CFR,  objTblModDet.getValueAt(i, INT_TBL_DET_TOT_FOB_CFR));
                        
                        arlRegDet.add(INT_TBL_ARL_DET_VAL_FLE,      objTblModDet.getValueAt(i, INT_TBL_DET_VAL_FLE));
                        arlRegDet.add(INT_TBL_ARL_DET_VAL_CFR,      objTblModDet.getValueAt(i, INT_TBL_DET_VAL_CFR));
                        arlRegDet.add(INT_TBL_ARL_DET_VAL_CFR_ARA,  objTblModDet.getValueAt(i, INT_TBL_DET_VAL_CFR_ARA));
                        
                        arlRegDet.add(INT_TBL_ARL_DET_VAL_ISD,      objTblModDet.getValueAt(i, INT_TBL_DET_VAL_ISD));
                        arlRegDet.add(INT_TBL_ARL_DET_CHK_ISD_CRE_TRI,  objTblModDet.getValueAt(i, INT_TBL_DET_CHK_ISD_CRE_TRI));
                        
                        arlRegDet.add(INT_TBL_ARL_DET_TOT_ARA,      objTblModDet.getValueAt(i, INT_TBL_DET_TOT_ARA));
                        arlRegDet.add(INT_TBL_ARL_DET_TOT_GAS,      objTblModDet.getValueAt(i, INT_TBL_DET_TOT_GAS));
                        arlRegDet.add(INT_TBL_ARL_DET_TOT_COS,      objTblModDet.getValueAt(i, INT_TBL_DET_TOT_COS));
                        arlRegDet.add(INT_TBL_ARL_DET_COS_UNI,      objTblModDet.getValueAt(i, INT_TBL_DET_COS_UNI));
                        arlRegDet.add(INT_TBL_ARL_DET_CAN_PED_EMB_AUX,   objTblModDet.getValueAt(i, INT_TBL_DET_CAN_PED_EMB_AUX));
                        arlRegDet.add(INT_TBL_ARL_DET_EST_REG,      "");
                        arlDatDet.add(arlRegDet);
                    }
                }
            }
            //System.out.println("arlDatDet: " +arlDatDet);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatDet;
    }

    public boolean setDetalleSeleccionado(ArrayList arregloDetalle){
        boolean blnRes=true;
        int intUltPosTbl=0;
        String strTblCodEmp="", strTblCodLoc="", strTblCodTipDoc="", strTblCodDoc="", strTblCodReg="", strTblCodItm="", strTblCodItmMae="";
        String strArlCodEmp="", strArlCodLoc="", strArlCodTipDoc="", strArlCodDoc="", strArlCodReg="", strArlCodItm="", strArlCodItmMae="";
        String strEstReg="";
        BigDecimal bgdCanPedEmbPza=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanUtiPedEmb=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanNotPed=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanTonMet=new BigDecimal(BigInteger.ZERO);

        try{
            objTblModDet.removeAllRows();
            arlDatDet=arregloDetalle;
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();

                for(int k=0; k<objTblMod.getRowCountTrue(); k++){
                    if(objTblMod.isChecked(k, INT_TBL_DAT_CHK)){
                        strTblCodEmp=objTblMod.getValueAt(k, INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_EMP).toString();
                        strTblCodLoc=objTblMod.getValueAt(k, INT_TBL_DAT_COD_LOC)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_LOC).toString();
                        strTblCodTipDoc=objTblMod.getValueAt(k, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_TIP_DOC).toString();
                        strTblCodDoc=objTblMod.getValueAt(k, INT_TBL_DAT_COD_DOC)==null?"":objTblMod.getValueAt(k, INT_TBL_DAT_COD_DOC).toString();
                        
                        strSQL ="";
                        strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
                        strSQL+="      , a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                        strSQL+="      , a5.co_parara, a5.tx_par, a5.tx_des, b1.co_exp, b2.tx_nom as tx_nomExp";
                        strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                        strSQL+="      , b1.nd_can";
                        //strSQL+="      , ((b1.nd_can*0.10)+b1.nd_can) AS nd_can";//Alfredo dijo que se puede ingresar hasta el 10% del valor de la nota de pedido adicional
                        strSQL+="      , b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                        strSQL+="      , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, b1.nd_canUtiPedEmb";
                        strSQL+=" FROM tbm_detnotpedimp AS b1 INNER JOIN";
                        strSQL+=" (";
                        strSQL+="   ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                        strSQL+="     LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                        strSQL+="   )";
                        strSQL+="   LEFT OUTER JOIN(";
                        strSQL+="      tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                        strSQL+="      INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                        strSQL+="   ) ON a1.co_grpImp=a4.co_grp";
                        strSQL+=" ) ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                        strSQL+=" INNER JOIN tbm_expImp AS b2 ON(b1.co_exp=b2.co_exp)";
                        strSQL+=" WHERE b1.co_emp=" + strTblCodEmp + "";
                        strSQL+=" AND b1.co_loc=" + strTblCodLoc + "";
                        strSQL+=" AND b1.co_tipDoc=" + strTblCodTipDoc + "";
                        strSQL+=" AND b1.co_doc=" + strTblCodDoc + "";
                        strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg ";
                        strSQL+="        , a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                        strSQL+="        , a5.co_parara, a5.tx_par, a5.tx_des, b1.co_exp, b2.tx_nom, b1.nd_ara";
                        strSQL+="        , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                        strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg, b1.nd_canUtiPedEmb";
                        strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                        //System.out.println("setDetalleSeleccionado:" +strSQL);
                        rst=stm.executeQuery(strSQL);
                        while(rst.next()){
                            intUltPosTbl=objTblModDet.getRowCountTrue();
                            objTblModDet.insertRow(intUltPosTbl);
                            objTblModDet.setValueAt(rst.getString("co_itmMae"),  intUltPosTbl, INT_TBL_DET_COD_ITM_MAE);
                            objTblModDet.setValueAt(rst.getString("co_itm"),     intUltPosTbl, INT_TBL_DET_COD_ITM);
                            objTblModDet.setValueAt(rst.getString("tx_codAlt"),  intUltPosTbl, INT_TBL_DET_COD_ALT_ITM);
                            objTblModDet.setValueAt(rst.getString("tx_codAlt2"), intUltPosTbl, INT_TBL_DET_COD_LET_ITM);
                            objTblModDet.setValueAt(rst.getString("tx_nomItm"),  intUltPosTbl, INT_TBL_DET_NOM_ITM);

                            bgdCanUtiPedEmb=new BigDecimal(rst.getObject("nd_canUtiPedEmb")==null?"0":(rst.getString("nd_canUtiPedEmb").equals("")?"0":rst.getString("nd_canUtiPedEmb")));
                            bgdCanNotPed=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                            bgdCanTonMet=new BigDecimal(rst.getObject("nd_canTonMet")==null?"0":(rst.getString("nd_canTonMet").equals("")?"0":rst.getString("nd_canTonMet")));
                            
                            objTblModDet.setValueAt(bgdCanTonMet, intUltPosTbl, INT_TBL_DET_CAN_TON_MET);
                            objTblModDet.setValueAt(bgdCanNotPed, intUltPosTbl, INT_TBL_DET_CAN_NOT_PED);
                            objTblModDet.setValueAt((bgdCanNotPed.subtract(bgdCanUtiPedEmb)), intUltPosTbl, INT_TBL_DET_CAN_PEN_NOT_PED);
                            objTblModDet.setValueAt(bgdCanUtiPedEmb, intUltPosTbl, INT_TBL_DET_CAN_UTI_PED_EMB);
                            objTblModDet.setValueAt(rst.getString("co_exp"),    intUltPosTbl, INT_TBL_DET_COD_EXP);
                            objTblModDet.setValueAt(rst.getString("tx_nomExp"), intUltPosTbl, INT_TBL_DET_NOM_EXP);                            
                            objTblModDet.setValueAt(rst.getString("co_emp"),    intUltPosTbl, INT_TBL_DET_COD_EMP);
                            objTblModDet.setValueAt(rst.getString("co_loc"),    intUltPosTbl, INT_TBL_DET_COD_LOC);
                            objTblModDet.setValueAt(rst.getString("co_tipDoc"), intUltPosTbl, INT_TBL_DET_COD_TIP_DOC);
                            objTblModDet.setValueAt(rst.getString("co_doc"),    intUltPosTbl, INT_TBL_DET_COD_DOC);
                            objTblModDet.setValueAt(rst.getString("co_reg"),    intUltPosTbl, INT_TBL_DET_COD_REG);
                            objTblModDet.setValueAt(rst.getString("nd_pesitmkgr"), intUltPosTbl, INT_TBL_DET_PES);
                            objTblModDet.setValueAt(rst.getString("co_parara"), intUltPosTbl, INT_TBL_DET_COD_ARA);
                            objTblModDet.setValueAt(rst.getString("tx_par"),    intUltPosTbl, INT_TBL_DET_NOM_ARA);
                            objTblModDet.setValueAt(rst.getString("tx_des"),    intUltPosTbl, INT_TBL_DET_DES_ARA);
                            objTblModDet.setValueAt(rst.getString("nd_ara"),    intUltPosTbl, INT_TBL_DET_POR_ARA);
                            objTblModDet.setValueAt(rst.getString("nd_preUni"), intUltPosTbl, INT_TBL_DET_PRE_UNI);
                            objTblModDet.setValueAt(rst.getString("nd_valTotFobCfr"), intUltPosTbl, INT_TBL_DET_TOT_FOB_CFR);
                            objTblModDet.setValueAt(rst.getString("nd_valFle"), intUltPosTbl, INT_TBL_DET_VAL_FLE);
                            objTblModDet.setValueAt(rst.getString("nd_valCfr"), intUltPosTbl, INT_TBL_DET_VAL_CFR);
                            objTblModDet.setValueAt(rst.getString("nd_valTotAra"), intUltPosTbl, INT_TBL_DET_TOT_ARA);
                            objTblModDet.setValueAt(rst.getString("nd_valTotGas"), intUltPosTbl, INT_TBL_DET_TOT_GAS);
                            objTblModDet.setValueAt(rst.getString("nd_valTotCos"), intUltPosTbl, INT_TBL_DET_TOT_COS);
                            objTblModDet.setValueAt(rst.getString("nd_cosUni"), intUltPosTbl, INT_TBL_DET_COS_UNI);
                        }
                        rst.close();
                        rst=null;
                    }
                }

                objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);
                objTblModDet.removeEmptyRows();
                con.close();
                con=null;
                stm.close();
                stm=null;

                if(arlDatDet!=null){
                    for(int i=0; i<arlDatDet.size(); i++){
                        objUti.setStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_EST_REG, "");
                    }

                    for(int i=0; i<arlDatDet.size(); i++){
                        strArlCodEmp=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_EMP)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_EMP).toString();
                        strArlCodLoc=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_LOC)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_LOC).toString();
                        strArlCodTipDoc=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_TIP_DOC)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_TIP_DOC).toString();
                        strArlCodDoc=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_DOC)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_DOC).toString();
                        strArlCodReg=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_REG)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_REG).toString();
                        strArlCodItm=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_ITM)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_ITM).toString();
                        strArlCodItmMae=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_ITM_MAE)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_ITM_MAE).toString();

                        strEstReg=objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_EST_REG)==null?"":objUti.getStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_EST_REG).toString();
                        if(strEstReg.equals("")){
                            for(int k=0; k<objTblModDet.getRowCountTrue(); k++){
                                strTblCodEmp=objTblModDet.getValueAt(k, INT_TBL_DET_COD_EMP)==null?"":objTblModDet.getValueAt(k, INT_TBL_DET_COD_EMP).toString();
                                strTblCodLoc=objTblModDet.getValueAt(k, INT_TBL_DET_COD_LOC)==null?"":objTblModDet.getValueAt(k, INT_TBL_DET_COD_LOC).toString();
                                strTblCodTipDoc=objTblModDet.getValueAt(k, INT_TBL_DET_COD_TIP_DOC)==null?"":objTblModDet.getValueAt(k, INT_TBL_DET_COD_TIP_DOC).toString();
                                strTblCodDoc=objTblModDet.getValueAt(k, INT_TBL_DET_COD_DOC)==null?"":objTblModDet.getValueAt(k, INT_TBL_DET_COD_DOC).toString();
                                strTblCodReg=objTblModDet.getValueAt(k, INT_TBL_DET_COD_REG)==null?"":objTblModDet.getValueAt(k, INT_TBL_DET_COD_REG).toString();
                                strTblCodItm=objTblModDet.getValueAt(k, INT_TBL_DET_COD_ITM)==null?"":objTblModDet.getValueAt(k, INT_TBL_DET_COD_ITM).toString();
                                strTblCodItmMae=objTblModDet.getValueAt(k, INT_TBL_DET_COD_ITM_MAE)==null?"":objTblModDet.getValueAt(k, INT_TBL_DET_COD_ITM_MAE).toString();

                                if( (strArlCodEmp.equals(strTblCodEmp)) && (strArlCodLoc.equals(strTblCodLoc)) && (strArlCodTipDoc.equals(strTblCodTipDoc)) && (strArlCodDoc.equals(strTblCodDoc)) && (strArlCodItm.equals(strTblCodItm))  && (strArlCodItmMae.equals(strTblCodItmMae)) && (strArlCodReg.equals(strTblCodReg))  ){
                                    objTblModDet.setValueAt(new Boolean(true), k, INT_TBL_DET_CHK);
                                    bgdCanPedEmbPza=objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_CAN_PZA);
                                    objTblModDet.setValueAt(bgdCanPedEmbPza, k, INT_TBL_DET_CAN_PED_EMB);

                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_CAN_TON_MET), k, INT_TBL_DET_CAN_TON_MET);

                                    //objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_CAN_UTI_PED_EMB).subtract(bgdCanPedEmb), k, INT_TBL_DET_CAN_UTI_PED_EMB);
                                    //objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_CAN_PEN_NOT_PED).subtract(bgdCanPedEmb), k, INT_TBL_DET_CAN_PEN_NOT_PED);

                                    //objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_CAN_NOT_PED).subtract(bgdCanPedEmb), k, INT_TBL_DET_CAN_NOT_PED);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_PES),     k, INT_TBL_DET_PES);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_COD_ARA), k, INT_TBL_DET_COD_ARA);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_NOM_ARA), k, INT_TBL_DET_NOM_ARA);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_DES_ARA), k, INT_TBL_DET_DES_ARA);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_POR_ARA), k, INT_TBL_DET_POR_ARA);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_PRE_UNI), k, INT_TBL_DET_PRE_UNI);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_TOT_FOB_CFR), k, INT_TBL_DET_TOT_FOB_CFR);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_VAL_FLE),     k, INT_TBL_DET_VAL_FLE);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_VAL_CFR),     k, INT_TBL_DET_VAL_CFR);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_VAL_CFR_ARA), k, INT_TBL_DET_VAL_CFR_ARA);
                                    
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_VAL_ISD), k, INT_TBL_DET_VAL_ISD);
                                    objTblModDet.setValueAt(null, intUltPosTbl, INT_TBL_DET_CHK_ISD_CRE_TRI);
                                    
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_TOT_ARA),     k, INT_TBL_DET_TOT_ARA);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_TOT_GAS),     k, INT_TBL_DET_TOT_GAS);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_TOT_COS),     k, INT_TBL_DET_TOT_COS);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_COS_UNI),     k, INT_TBL_DET_COS_UNI);
                                    objTblModDet.setValueAt(objUti.getBigDecimalValueAt(arlDatDet, i, INT_TBL_ARL_DET_CAN_PED_EMB_AUX), k, INT_TBL_DET_CAN_PED_EMB_AUX);
                                    objUti.setStringValueAt(arlDatDet, i, INT_TBL_ARL_DET_EST_REG, "E");
                                }
                            }
                        }
                    }
                }

                objTblModDet.initRowsState();
                objTblModDet.setDataModelChanged(false);
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public int getBotonPresionado() {
        return intButPre;
    }

    public void setBotonPresionado(int intButPre) {
        this.intButPre = intButPre;
    }

    private boolean agregarItemsNotaPedidoArregloEliminados(){
        boolean blnRes=true;
        String strCodEmpFilEliCab="", strCodLocFilEliCab="", strCodTipDocFilEliCab="", strCodDocFilEliCab="";
        String strCodEmpFilEliDet="", strCodLocFilEliDet="", strCodTipDocFilEliDet="", strCodDocFilEliDet="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    strCodEmpFilEliCab=objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                    strCodLocFilEliCab=objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString();
                    strCodTipDocFilEliCab=objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString();
                    strCodDocFilEliCab=objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString();
                    for(int j=0; j<objTblModDet.getRowCountTrue(); j++){
                        strCodEmpFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_EMP).toString();
                        strCodLocFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_LOC).toString();
                        strCodTipDocFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_TIP_DOC).toString();
                        strCodDocFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_DOC).toString();
                        if( (strCodEmpFilEliCab.equals(strCodEmpFilEliDet)) && (strCodLocFilEliCab.equals(strCodLocFilEliDet)) && (strCodTipDocFilEliCab.equals(strCodTipDocFilEliDet)) && (strCodDocFilEliCab.equals(strCodDocFilEliDet))  ){
                            //se debe llenar un arreglo para adicionar en el arreglo de filas eliminadas de ZafImp33_02
                            arlRegImp33_03FilEli=new ArrayList();
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_EMP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_EMP));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_LOC,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_LOC));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_TIP_DOC,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_TIP_DOC));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_DOC,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_DOC));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_REG,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_REG));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PED_EMB_AUX,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PED_EMB_AUX));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_UTI_PED_EMB,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_UTI_PED_EMB));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_NOT_PED,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_NOT_PED));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PED_EMB,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PED_EMB));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PEN_NOT_PED,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PEN_NOT_PED));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_EXP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_EXP));
                            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_NOM_EXP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_NOM_EXP));
                            
                            objImp33_02.addArregloFilasEliminadas(arlRegImp33_03FilEli);
                            //System.out.println("SE AGREGA ARREGLO ELIMINADOS: " + arlRegImp33_03FilEli.toString());
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

    private boolean agregarItemNotaPedidoArregloEliminados(int fila){
        boolean blnRes=true;
        int j=fila;
        try{
            //se debe llenar un arreglo para adicionar en el arreglo de filas eliminadas de ZafImp33_02
            arlRegImp33_03FilEli=new ArrayList();
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_EMP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_EMP));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_LOC,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_LOC));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_TIP_DOC,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_TIP_DOC));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_DOC,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_DOC));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_REG,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_REG));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PED_EMB_AUX,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PED_EMB_AUX));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_UTI_PED_EMB,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_UTI_PED_EMB));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_NOT_PED,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_NOT_PED));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PED_EMB,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PED_EMB));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PEN_NOT_PED,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PEN_NOT_PED));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_EXP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_EXP));
            arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_NOM_EXP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_NOM_EXP));
            objImp33_02.addArregloFilasEliminadas(arlRegImp33_03FilEli);
            //System.out.println("SE AGREGA ARREGLO ELIMINADOS: " + arlRegImp33_03FilEli.toString());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean agregarItemsNotaPedidoArregloEliminadosCabecera(int fila){
        boolean blnRes=true;
        String strCodEmpFilEliCab="", strCodLocFilEliCab="", strCodTipDocFilEliCab="", strCodDocFilEliCab="";
        String strCodEmpFilEliDet="", strCodLocFilEliDet="", strCodTipDocFilEliDet="", strCodDocFilEliDet="";
        try{
            strCodEmpFilEliCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP).toString();
            strCodLocFilEliCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString();
            strCodTipDocFilEliCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString();
            strCodDocFilEliCab=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString();
            for(int j=0; j<=(objTblModDet.getRowCountTrue()-1); j++){ //Se resta -1     
                strCodEmpFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_EMP).toString();
                strCodLocFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_LOC).toString();
                strCodTipDocFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_TIP_DOC).toString();
                strCodDocFilEliDet=objTblModDet.getValueAt(j, INT_TBL_DET_COD_DOC).toString();
                if( (strCodEmpFilEliCab.equals(strCodEmpFilEliDet)) && (strCodLocFilEliCab.equals(strCodLocFilEliDet)) && (strCodTipDocFilEliCab.equals(strCodTipDocFilEliDet)) && (strCodDocFilEliCab.equals(strCodDocFilEliDet))  ){
                    //se debe llenar un arreglo para adicionar en el arreglo de filas eliminadas de ZafImp33_02
                    arlRegImp33_03FilEli=new ArrayList();
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_EMP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_EMP));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_LOC,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_LOC));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_TIP_DOC,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_TIP_DOC));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_DOC,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_DOC));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_REG,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_REG));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PED_EMB_AUX,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PED_EMB_AUX));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_UTI_PED_EMB,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_UTI_PED_EMB));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_NOT_PED,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_NOT_PED));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PED_EMB,       "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PED_EMB));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_CAN_PEN_NOT_PED,   "" + objTblModDet.getValueAt(j, INT_TBL_DET_CAN_PEN_NOT_PED));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_COD_EXP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_COD_EXP));
                    arlRegImp33_03FilEli.add(INT_CFE_IMP33_03_NOM_EXP,           "" + objTblModDet.getValueAt(j, INT_TBL_DET_NOM_EXP));
                    objImp33_02.addArregloFilasEliminadas(arlRegImp33_03FilEli);
                    //System.out.println("SE AGREGA ARREGLO ELIMINADOS: " + arlRegImp33_03FilEli.toString());
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public void setModoOperacionTooBar(char opcionTooBar){
        chrOpcManDatTooBar=opcionTooBar;
    }

    private boolean getNombrePedidoSeleccionado(){
        boolean blnRes=true;
        strNomPedSelUsr="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    if(strNomPedSelUsr.equals(""))
                        strNomPedSelUsr="" + (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED).toString());
                    else
                        strNomPedSelUsr+="/" + (objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_PED).toString());
                }
            }
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    public String getNotaPedidoSeleccionado() {
        return strNomPedSelUsr;
    }
 
    /**
     * Función que permite obtener los datos del Pedido y del Exportador para poder generar el dato correspondiente al asiento de diario
     * @return true Si se pudo generar la operación
     * <BR> false Caso contrario
     */
    public ArrayList getDatExpPedSelGenDia(){
        try{
            arlDatExpPed.clear();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    arlRegExpPed=new ArrayList();
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP));
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_LOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC));
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_DOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC));
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_EXP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_EXP));
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_NOM_EXP, objTblMod.getValueAt(i, INT_TBL_DAT_NOM_EXP));
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_CTA_ACT, null);
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_CTA_PAS, null);
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_TOT_DIA, null);
                    arlRegExpPed.add(INT_ARL_EXP_PED_IMP33_03_COD_IMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_IMP));
                    arlDatExpPed.add(arlRegExpPed);
                }
            }
            System.out.println("arlDatExpPed-3: " + arlDatExpPed.toString());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatExpPed;
    }
    
    /**
     * Función que permite obtener los datos de la Nota de Pedido
     * @param conexion
     * @param codigoTipoDocumento Código de Tipo de Documento
     * @param codigoDocumento Código de documento
     * @return 
     */
    public ArrayList getDatExpPedSelGenDia(Connection conexion, int codigoTipoDocumento, int codigoDocumento){
        try{
            arlDatExpPedDb.clear();
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_exp, a2.co_imp";
                strSQL+=" FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_detPedEmbImp AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" INNER JOIN tbm_cabNotPedImp AS a2";
                strSQL+=" ON a3.co_emp=a2.co_emp AND a3.co_locRel=a2.co_loc AND a3.co_tipDocRel=a2.co_tipDoc AND a3.co_docRel=a2.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento + " AND a1.co_doc=" + codigoDocumento + "";
                strSQL+=" GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_exp";
                //System.out.println("getDatExpPedSelGenDia-parametros: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegExpPedDb=new ArrayList();
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_EMP, rst.getString("co_emp"));
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_LOC, rst.getString("co_loc"));
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_TIP_DOC, rst.getString("co_tipDoc"));
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_DOC, rst.getString("co_doc"));
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_EXP, rst.getString("co_exp"));
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_NOM_EXP, "");
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_CTA_ACT, null);
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_CTA_PAS, null);
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_TOT_DIA, null);
                    arlRegExpPedDb.add(INT_ARL_EXP_PED_IMP33_03_COD_IMP, rst.getString("co_imp"));
                    arlDatExpPedDb.add(arlRegExpPedDb);
                }
                //System.out.println("arlDatExpPedDb-parametros: " + arlDatExpPedDb.toString());
                stm.close();
                stm=null;
                rst.close();
                rst=null;
           }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatExpPedDb;
    }

    public ArrayList getArregloDatoExpPedEmbDb() {
        return arlDatExpPedDb;
    }
    
    
    
    
    
    
    
    
    

}
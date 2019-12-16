/*
 * ZafCom89.java
 *
 */
package Compras.ZafCom89;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Ingrid Lino
 */
public class ZafCom89 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_FIX_LIN=0;
    final int INT_TBL_DAT_FIX_COD_EMP=1;//es el codigo del grupo
    final int INT_TBL_DAT_FIX_COD_ITM=2;
    final int INT_TBL_DAT_FIX_COD_ITM_MAE=3;
    final int INT_TBL_DAT_FIX_COD_ALT_ITM=4;
    final int INT_TBL_DAT_FIX_COD_ALT_DOS=5;
    final int INT_TBL_DAT_FIX_NOM_ITM=6;
    final int INT_TBL_DAT_FIX_DES_COR_UNI_MED=7;
    final int INT_TBL_DAT_FIX_DES_LAR_UNI_MED=8; 
    
    //Constantes: Columnas del JTable "TblDat"
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;//es el codigo del grupo
    final int INT_TBL_DAT_COD_ITM=2;
    final int INT_TBL_DAT_COD_ITM_MAE=3;
    final int INT_TBL_DAT_COD_ALT_ITM=4;
    final int INT_TBL_DAT_NOM_ITM=5;
    final int INT_TBL_DAT_DES_COR_UNI_MED=6;
    final int INT_TBL_DAT_DES_LAR_UNI_MED=7;        
    final int INT_TBL_DAT_COL_DIN=8;  
    
    //Constantes: Columnas Dinámicas del JTable "TblDat"
    final int INT_TBL_DAT_STK_ACT=0;
    final int INT_TBL_DAT_STK_FAL=1;
    final int INT_TBL_DAT_STK_SOB=2;
    final int INT_TBL_DAT_STK_CON=3;
    final int INT_TBL_DAT_DIF_CAN=4;
    final int INT_TBL_DAT_DIF_CNF=5;
    final int INT_TBL_DAT_TXT_USR=6; 
    final int INT_TBL_DAT_FEC_CON=7; 
    final int INT_TBL_DAT_CAN_ING=8;
    final int INT_TBL_DAT_CAN_EGR=9;    
    final int INT_TBL_DAT_COD_REG=10; 
    
    //Constantes: Número de columnas que se presentan por bodega
    final int intColPreModDat=11;    

    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.	
	
    //Constantes: ArrayList
    private ArrayList arlRegBodSel, arlDatBodSel;
    final int INT_ARL_COD_BOD_SEL=0; 
    final int INT_ARL_NOM_BOD_SEL=1;	
    
    //Variables generales.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblBus objTblBus, objTblBusFix;
    private ZafTblFilCab objTblFilCab, objTblFilCabBod, objTblFilCabFix;
    private ZafTblMod objTblMod, objTblModFix;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCon;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
    private ZafMouMotAdaFix objMouMotAdaFix;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuFix;                     //PopupMenu: Establecer PeopuMená en JTable.
    private ZafDocLis objDocLis;
    private ZafThreadGUI objThrGUI;    
    private ZafTblCelEdiTxt objTblCelEdiTxt;    
    private ZafTblOrd objTblOrd, objTblOrdFix;                             //JTable de ordenamiento.
    private ZafVenCon vcoBod;                                              //Ventana de consulta "Bodega".
    private ZafVenCon vcoItm;                                              //Ventana de consulta "Item".    
    private ZafSelFec objFecReaCon;
    private ZafPerUsr objPerUsr;
    private ZafTblCelRenBut    objTblCelRenButEgr, objTblCelRenButIng;
    private ZafTblCelEdiButGen objTblCelEdiButIng, objTblCelEdiButEgr;
    private ZafTblCelRenLbl objTblCelRenLblTot;
    private ZafTblMod objTblModBod;
    private ZafTblCelRenChk objTblCelRenChkBod;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkBod;            //Editor: JCheckBox en celda.	
    private ZafCom89_01 objCom79_01;
    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatFix, vecCabFix, vecRegFix;
    private Vector vecAux;
    private boolean blnCon;                                                 //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                                              //Determina si hay cambios en el formulario.	
    private int intNumColIni;//numero de columnas estaticas
    private int intNumColIniCon, intNumColFinCon, intNumColAdiCon;
    private int intNumColBodCodEmpGrp;
    private int intNumColIniConTot, intNumColFinConTot, intNumColAdiConTot;
    
    private String strSQL, strSQLUpd, strAux;
    private String strCodAlt, strCodAlt2, strNomItm;                                    //Contenido del campo al obtener el foco.
    
    private Vector vecDatBod, vecCabBod;
    private boolean blnMarTodChkTblBod=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    
    private JScrollBar barFix3, barFixVer3, barDat3;//, , barDatTotFix3, barDatTot3
   
    
    /** Crea una nueva instancia de la clase ZafCom89. */
    public ZafCom89(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();       
            objPerUsr=new ZafPerUsr(objParSis);
            arlDatBodSel=new ArrayList();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try{
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.2");
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();

            //Configurar ZafSelFec:
            objFecReaCon=new ZafSelFec();
            objFecReaCon.setCheckBoxVisible(true);
            objFecReaCon.setCheckBoxChecked(false);
            objFecReaCon.setTitulo("Fecha de realización del conteo");
            panFilFecCon.add(objFecReaCon);
            objFecReaCon.setBounds(0, 66, 472, 72);

            //Configurar los JTables.
            configurarTblBod();
            configurarTblDatFix();
            configurarTblDat();
            configurarVenConItm();
            cargarBod();

            if(objParSis.getCodigoMenu()==2590){
                if(objPerUsr.isOpcionEnabled(2591))
                    butCon.setEnabled(true);
                else
                    butCon.setEnabled(false);
                if(objPerUsr.isOpcionEnabled(2592))
                    butCer.setEnabled(true);
                else
                    butCer.setEnabled(false);
            }

            txtCodItm.setVisible(false);
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
            vecCab=new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED,"Unidad");
            vecCab.add(INT_TBL_DAT_DES_LAR_UNI_MED,"Uni.Med.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_UNI_MED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_LAR_UNI_MED, tblDat);
            
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
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColIni=objTblMod.getColumnCount();            
            
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código de item";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código de item maestro";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno de item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre de item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg="Unidad de medida (Descripción corta)";
                    break;
                case INT_TBL_DAT_DES_LAR_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }       
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatFix()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFix=new Vector();    //Almacena los datos
            vecCabFix=new Vector(9);  //Almacena las cabeceras
            vecCabFix.clear();
            vecCabFix.add(INT_TBL_DAT_FIX_LIN,"");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_EMP,"Cód.Emp.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ITM,"Cód.Itm.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_DOS,"Cód.Alt.2");
            vecCabFix.add(INT_TBL_DAT_FIX_NOM_ITM,"Item");
            vecCabFix.add(INT_TBL_DAT_FIX_DES_COR_UNI_MED,"Unidad");
            vecCabFix.add(INT_TBL_DAT_FIX_DES_LAR_UNI_MED,"Uni.Med.");

            objTblModFix=new ZafTblMod();
            objTblModFix.setHeader(vecCabFix);
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            tblFix.setModel(objTblModFix);
            //Configurar JTable: Establecer tipo de seleccián.
            tblFix.setRowSelectionAllowed(true);
            tblFix.getTableHeader().setReorderingAllowed(false);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_DOS).setPreferredWidth(42);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setPreferredWidth(175);
            tcmAux.getColumn(INT_TBL_DAT_FIX_DES_COR_UNI_MED).setPreferredWidth(38);
            tcmAux.getColumn(INT_TBL_DAT_FIX_DES_LAR_UNI_MED).setPreferredWidth(45);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_FIX_DES_COR_UNI_MED).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_EMP, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ITM_MAE, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_DES_LAR_UNI_MED, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ITM, tblFix);
            
            //Establecer formato
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;   
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix=new ZafMouMotAdaFix();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix=new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblFilCabFix);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBusFix=new ZafTblBus(tblFix);
            objTblOrdFix=new ZafTblOrd(tblFix);
            
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_EDI);
            
            //<editor-fold defaultstate="collapsed" desc="/* FIXED */">
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            //Controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modDat = tblFix.getSelectionModel();//se descomento
            tblDat.setSelectionModel(modDat);//se descomento
            //Controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modFix = tblDat.getSelectionModel();
            tblFix.setSelectionModel(modFix);
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barFix3=spnFix.getHorizontalScrollBar();
            
            barFixVer3=spnFix.getVerticalScrollBar();
            barDat3=spnDat.getVerticalScrollBar();

            //PARA DESPLAZAMIENTOS DE CELDAS
            //VERTICAL
            barFixVer3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDat3.setValue(evt.getValue());
                }
            });
            
            //VERTICAL
            barDat3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFixVer3.setValue(evt.getValue());
                }
            });
            
            ZafTblHeaGrp objTblHeaGrpDatChq=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpDatChq.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpDatItm=null;
            objTblHeaColGrpDatItm=new ZafTblHeaColGrp("Datos del item");
            objTblHeaColGrpDatItm.setHeight(16);
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_LIN));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_EMP));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM_MAE));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_DOS));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_DES_COR_UNI_MED));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_FIX_DES_LAR_UNI_MED));
            objTblHeaGrpDatChq.addColumnGroup(objTblHeaColGrpDatItm);
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
   private class ZafMouMotAdaFix extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblFix.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_FIX_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_FIX_COD_ITM:
                    strMsg="Código de item";
                    break;
                case INT_TBL_DAT_FIX_COD_ITM_MAE:
                    strMsg="Código de item maestro";
                    break;
                case INT_TBL_DAT_FIX_COD_ALT_ITM:
                    strMsg="Código alterno de item";
                    break;
                case INT_TBL_DAT_FIX_COD_ALT_DOS:
                    strMsg="Código alterno 2 de item";
                    break;                    
                case INT_TBL_DAT_FIX_NOM_ITM:
                    strMsg="Nombre de item";
                    break;
                case INT_TBL_DAT_FIX_DES_COR_UNI_MED:
                    strMsg="Unidad de medida (Descripción corta)";
                    break;
                case INT_TBL_DAT_FIX_DES_LAR_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblFix.getTableHeader().setToolTipText(strMsg);
        }
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
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            arlCam.add("a2.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("300");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
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
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.show();
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else{
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else{
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Código alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else{
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else{
                            txtCodAlt2.setText(strCodAlt2);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else{
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else{
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
    
    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatBod=new Vector();    //Almacena los datos
            vecCabBod=new Vector(6);   //Almacena las cabeceras
            vecCabBod.clear();
            vecCabBod.add(INT_TBL_BOD_LIN,"");
            vecCabBod.add(INT_TBL_BOD_CHK,"");
            vecCabBod.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCabBod.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCabBod.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCabBod.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCabBod);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabBod=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCabBod);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkBod=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChkBod);
            objTblCelRenChkBod=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkBod=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChkBod);
            objTblCelEdiChkBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblBod.getSelectedRow();
                    
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
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
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las bodegas.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo las bodegas de la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
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
                    strSQL+=" SELECT a2.st_reg, a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a2.st_reg, a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_bodlocprgusr AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E')";
                    strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" AND a3.st_reg IN('A','P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                }

                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatBod.clear();
                intNumColBodCodEmpGrp=0;
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    if (rst.getString("st_reg").equals("A"))
                        vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_BOD_CHK,null);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDatBod.add(vecReg);

                    if(rst.getInt("co_emp")==objParSis.getCodigoEmpresaGrupo())
                        intNumColBodCodEmpGrp++;

                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDatBod);
                tblBod.setModel(objTblModBod);
                vecDatBod.clear();
                blnMarTodChkTblBod=false;
            }
        }
        catch (java.sql.SQLException e) {
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }

    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)  {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)   {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=false;
                }
                else  {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)  {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }   
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDocumentosPendientesConfirmarIngresos(int intCodigoRegistro){
        boolean blnRes=true;
        try{
            objCom79_01=new ZafCom89_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "I", intCodigoRegistro);
            //objCom50_01.setCodigoRegistro(intCodigoRegistro);
            objCom79_01.show();
            objCom79_01=null;
        }
        catch (Exception e){
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
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDocumentosPendientesConfirmarEgresos(int intCodigoRegistro){
        boolean blnRes=true;
        try{
            objCom79_01=new ZafCom89_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, "E", intCodigoRegistro);
            //objCom50_01.setCodigoRegistro(intCodigoRegistro);
            objCom79_01.show();
            objCom79_01=null;
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
            
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
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
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        panFilItm = new javax.swing.JPanel();
        optTodItm = new javax.swing.JRadioButton();
        optItmSel = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        lblItmDes = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblItmHas = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        panFilFecCon = new javax.swing.JPanel();
        panFilBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        panFilChk = new javax.swing.JPanel();
        chkMosItmSinConAso = new javax.swing.JCheckBox();
        chkMosItmConAso = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        spnRpt = new javax.swing.JSplitPane();
        panRptItm = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblFix = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panRptCon = new javax.swing.JPanel();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panFil.setPreferredSize(new java.awt.Dimension(9, 180));
        panFil.setRequestFocusEnabled(false);
        panFil.setLayout(new java.awt.BorderLayout());

        panFilItm.setPreferredSize(new java.awt.Dimension(100, 100));
        panFilItm.setRequestFocusEnabled(false);
        panFilItm.setLayout(null);

        optTodItm.setSelected(true);
        optTodItm.setText("Todos los items");
        optTodItm.setPreferredSize(new java.awt.Dimension(93, 6));
        optTodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodItmActionPerformed(evt);
            }
        });
        panFilItm.add(optTodItm);
        optTodItm.setBounds(0, 0, 400, 17);

        optItmSel.setText("Sólo los items que cumplan el criterio seleccionado");
        optItmSel.setPreferredSize(new java.awt.Dimension(93, 16));
        panFilItm.add(optItmSel);
        optItmSel.setBounds(0, 18, 570, 17);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFilItm.add(lblItm);
        lblItm.setBounds(20, 35, 30, 20);

        txtCodItm.setEditable(false);
        txtCodItm.setEnabled(false);
        panFilItm.add(txtCodItm);
        txtCodItm.setBounds(60, 35, 20, 20);

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
        panFilItm.add(txtCodAltItm);
        txtCodAltItm.setBounds(80, 35, 100, 20);

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
        panFilItm.add(txtNomItm);
        txtNomItm.setBounds(260, 35, 300, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFilItm.add(butItm);
        butItm.setBounds(560, 35, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmDesHas.setLayout(null);

        lblItmDes.setText("Desde:");
        panItmDesHas.add(lblItmDes);
        lblItmDes.setBounds(20, 20, 60, 14);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(65, 15, 70, 20);

        lblItmHas.setText("Hasta:");
        panItmDesHas.add(lblItmHas);
        lblItmHas.setBounds(150, 20, 50, 14);

        txtCodAltItmHas.setVerifyInputWhenFocusTarget(false);
        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(200, 15, 70, 20);

        panFilItm.add(panItmDesHas);
        panItmDesHas.setBounds(20, 58, 280, 40);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmTer.setLayout(null);

        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(30, 18, 90, 14);

        txtCodAltItmTer.setVerifyInputWhenFocusTarget(false);
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(140, 14, 70, 20);

        panFilItm.add(panItmTer);
        panItmTer.setBounds(310, 58, 230, 40);

        txtCodAlt2.setToolTipText("Código Alterno 2 ");
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        panFilItm.add(txtCodAlt2);
        txtCodAlt2.setBounds(180, 35, 80, 20);

        panFil.add(panFilItm, java.awt.BorderLayout.NORTH);

        panFilFecCon.setLayout(new java.awt.BorderLayout());
        panFil.add(panFilFecCon, java.awt.BorderLayout.CENTER);

        panGen.add(panFil, java.awt.BorderLayout.NORTH);

        panFilBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panFilBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panFilBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panGen.add(panFilBod, java.awt.BorderLayout.CENTER);

        panFilChk.setPreferredSize(new java.awt.Dimension(417, 20));
        panFilChk.setLayout(null);

        chkMosItmSinConAso.setText("Mostrar items no contados");
        chkMosItmSinConAso.setPreferredSize(new java.awt.Dimension(201, 20));
        chkMosItmSinConAso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosItmSinConAsoActionPerformed(evt);
            }
        });
        panFilChk.add(chkMosItmSinConAso);
        chkMosItmSinConAso.setBounds(280, 0, 201, 20);

        chkMosItmConAso.setSelected(true);
        chkMosItmConAso.setText("Mostrar items con conteos asociados");
        chkMosItmConAso.setPreferredSize(new java.awt.Dimension(201, 20));
        chkMosItmConAso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkMosItmConAsoStateChanged(evt);
            }
        });
        panFilChk.add(chkMosItmConAso);
        chkMosItmConAso.setBounds(0, 0, 270, 20);

        panGen.add(panFilChk, java.awt.BorderLayout.PAGE_END);

        tabFrm.addTab("Filtro", panGen);

        panRpt.setLayout(new java.awt.BorderLayout());

        panDat.setLayout(new java.awt.BorderLayout());

        spnRpt.setDividerLocation(360);
        spnRpt.setDividerSize(2);

        panRptItm.setLayout(new java.awt.BorderLayout());

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

        panRptItm.add(spnFix, java.awt.BorderLayout.CENTER);

        spnRpt.setLeftComponent(panRptItm);

        panRptCon.setLayout(new java.awt.BorderLayout());

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

        panRptCon.add(spnDat, java.awt.BorderLayout.CENTER);

        spnRpt.setRightComponent(panRptCon);

        panDat.add(spnRpt, java.awt.BorderLayout.CENTER);

        panRpt.add(panDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(639, 40));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 15));
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

        getContentPane().add(PanFrm);

        setBounds(0, 0, 720, 470);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexián si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm
                                    

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (butCon.getText().equals("Consultar")){
            //objTblTotales.isActivo(false);
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else
            blnCon=false;
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void chkMosItmConAsoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMosItmConAsoStateChanged
       if(chkMosItmConAso.isSelected()){
           chkMosItmSinConAso.setSelected(false);
       }
    }//GEN-LAST:event_chkMosItmConAsoStateChanged

    private void chkMosItmSinConAsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosItmSinConAsoActionPerformed
       if(chkMosItmSinConAso.isSelected()){
           chkMosItmConAso.setSelected(false);
       }
    }//GEN-LAST:event_chkMosItmSinConAsoActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        if(txtNomItm.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
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

        if(txtNomItm.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        txtCodAltItm.transferFocus();
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        strCodAlt=txtCodAltItm.getText();
        txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAltItm.getText().equals(""))  {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else  {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAltItm.setText(strCodAlt);

        if(txtCodAltItm.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        if (txtCodAltItmDes.getText().length()>0)
        {
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);

            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");
            txtCodAltItmTer.setText("");

            if (txtCodAltItmHas.getText().length()==0)
                txtCodAltItmHas.setText(txtCodAltItmDes.getText());
        }
    }//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        txtCodAltItmHas.selectAll();
    }//GEN-LAST:event_txtCodAltItmHasFocusGained

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        if (txtCodAltItmHas.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmHas.setText("");
            txtCodAltItmDes.setText("");
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void optTodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodItmActionPerformed
        if (txtCodAltItmTer.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmHas.setText("");
            txtCodAltItmDes.setText("");
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_optTodItmActionPerformed

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2 = txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))  {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else  {
                mostrarVenConItm(2);
            }
        }
        else
            txtCodAlt2.setText(strCodAlt2);

        if(txtCodAlt2.getText().length()>0){
            optItmSel.setSelected(true);
            optTodItm.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void tblFixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFixFocusGained
        // TODO add your handling code here:
        //        int intFil=-1, intCol=-1;
        //        intFil=tblFix.getSelectedRow();
        //        intCol=tblFix.getSelectedColumn();
        //        if(intFil!=-1){
            //            //Seleccionar la fila donde se encontrÃ³ el valor buscado.
            //            tblDat.setRowSelectionInterval(intFil, intFil);
            //            //Ubicar el foco en la fila seleccionada.
            //            tblDat.changeSelection(intFil, intCol, true, true);
            //        }
    }//GEN-LAST:event_tblFixFocusGained

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained
        // TODO add your handling code here:
        //        int intFil=-1, intCol=-1;
        //        intFil=tblDat.getSelectedRow();
        //        intCol=tblDat.getSelectedColumn();
        //        if(intFil!=-1){
            //            //Seleccionar la fila donde se encontrÃ³ el valor buscado.
            //            tblFix.setRowSelectionInterval(intFil, intFil);
            //            //Ubicar el foco en la fila seleccionada.
            //            tblFix.changeSelection(intFil, intCol, true, true);
            //        }
    }//GEN-LAST:event_tblDatFocusGained

/** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkMosItmConAso;
    private javax.swing.JCheckBox chkMosItmSinConAso;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmDes;
    private javax.swing.JLabel lblItmHas;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optItmSel;
    private javax.swing.JRadioButton optTodItm;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilBod;
    private javax.swing.JPanel panFilChk;
    private javax.swing.JPanel panFilFecCon;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptCon;
    private javax.swing.JPanel panRptItm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JSplitPane spnRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
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
        txtCodItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItm.getDocument().addDocumentListener(objDocLis);
        txtNomItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmDes.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmHas.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmTer.getDocument().addDocumentListener(objDocLis);
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
   private class ZafThreadGUI extends Thread{
        public void run(){
            if (!cargarReg()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
   
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            objTblMod.removeAllRows();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(eliminaTodasColumnasAdicionadas()){
                    if(adicionaColumnasModelo()){
                        if(adicionaColumnaTotales_CantidadContada()){
                            if(cargar_tbmConInv()){
                                if(cargarDatFiltros()) {
                                    blnRes=true;
                                }
                            }
                        }
                    }
                }
                con.close();
                con=null;                
            }            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaTodasColumnasAdicionadas(){
        boolean blnRes=true;
        try{
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColIni); i--){
                objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que adiciona las columnas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean adicionaColumnasModelo(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        String strTitTbl="";
        intNumColAdiCon=0;
        arlDatBodSel.clear();
        try{
            //borrame
            vecAux=new Vector();
            
            intNumColIniCon=intNumColIni;
            int p=0, k=0;
            //Para saber cuantos bloques se van a agregar, segun el numero de bodegas seleccionadas.
            for(int j=0; j<objTblModBod.getRowCountTrue(); j++){
                if(objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                    intNumColAdiCon++;
                    
                    arlRegBodSel=new ArrayList();
                    arlRegBodSel.add(INT_ARL_COD_BOD_SEL, objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD));
                    arlRegBodSel.add(INT_ARL_NOM_BOD_SEL, objTblModBod.getValueAt(j, INT_TBL_BOD_NOM_BOD));
                    arlDatBodSel.add(arlRegBodSel);
                }
            }
      
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrp=null;

            java.awt.Color colFonColPar, colFonColImp;
            colFonColPar=new java.awt.Color(255,221,187);//naranja
            colFonColImp=new java.awt.Color(228,228,203);//verde agua

            objTblCelEdiButIng=new ZafTblCelEdiButGen();
            objTblCelEdiButIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                //String strCnfIngEgr="";
                int intCodReg=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intCodReg=Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()+4))==null?"0":objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()+4)).toString());//se le suman 4 columnas a la columna seleccionada porque al sumarle me da la columna donde esta el codigo del registro
                    mostrarDocumentosPendientesConfirmarIngresos(intCodReg);
                }
            });
            objTblCelEdiButEgr=new ZafTblCelEdiButGen();
            objTblCelEdiButEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                int intCodReg=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intCodReg=Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()+3))==null?"0":objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()+3)).toString());//se le suman 3 columnas a la columna seleccionada porque al sumarle me da la columna donde esta el codigo del registro
                    mostrarDocumentosPendientesConfirmarEgresos(intCodReg);
                }
            });
            
            for (int i=0; i<(intNumColAdiCon*intColPreModDat); i++){
                strTitTbl=(p==INT_TBL_DAT_STK_ACT?"Stk.Bod.":(p==INT_TBL_DAT_STK_FAL?"Stk.Fal.":(p==INT_TBL_DAT_STK_SOB?"Stk.Sob":(p==INT_TBL_DAT_STK_CON?"Can.Con."
                         :(p==INT_TBL_DAT_DIF_CAN?"Dif.Can.":(p==INT_TBL_DAT_DIF_CNF?"Dif.Can.Sin.Cnf.":(p==INT_TBL_DAT_TXT_USR?"Usu.Con.":(p==INT_TBL_DAT_FEC_CON?"Fec.Con.":(p==INT_TBL_DAT_CAN_ING?"I":(p==INT_TBL_DAT_CAN_EGR?"E":(p==INT_TBL_DAT_COD_REG?"Cód.Reg.":"")))))))))));
                
                if((p==INT_TBL_DAT_STK_ACT) || (p==INT_TBL_DAT_STK_FAL) || (p==INT_TBL_DAT_STK_SOB) || (p==INT_TBL_DAT_STK_CON) || (p==INT_TBL_DAT_DIF_CAN) || (p==INT_TBL_DAT_DIF_CNF) ){//formato numero
                    objTblCelRenLblCon=new ZafTblCelRenLbl();
                    objTblCelRenLblCon.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                    objTblCelRenLblCon.setTipoFormato(objTblCelRenLblCon.INT_FOR_NUM);
                    if(p==INT_TBL_DAT_STK_CON)
                        objTblCelRenLblCon.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
                    else
                        objTblCelRenLblCon.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                    tbc=new javax.swing.table.TableColumn(intNumColIni+i);
                    tbc.setHeaderValue("" + strTitTbl + "");
                    //Configurar JTable: Establecer el ancho de la columna.
                    if(p==INT_TBL_DAT_DIF_CAN  || p==INT_TBL_DAT_DIF_CNF  )
                        tbc.setPreferredWidth(70);
                    else
                        tbc.setPreferredWidth(55);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblCon);
                }
                else if((p==INT_TBL_DAT_TXT_USR) || (p==INT_TBL_DAT_FEC_CON) || (p==INT_TBL_DAT_COD_REG) ){//formato de texto
                    objTblCelRenLblCon=new ZafTblCelRenLbl();
                    objTblCelRenLblCon.setHorizontalAlignment(javax.swing.JLabel.LEFT);
                    objTblCelRenLblCon.setTipoFormato(objTblCelRenLblCon.INT_FOR_GEN);
                    tbc=new javax.swing.table.TableColumn(intNumColIni+i);
                    tbc.setHeaderValue("" + strTitTbl + "");
                    if(p==INT_TBL_DAT_FEC_CON){
                        tbc.setPreferredWidth(120);
                    }
                    else{
                        tbc.setPreferredWidth(60);
                    }
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblCon);
                }
                else{//formato para boton
                    tbc=new javax.swing.table.TableColumn(intNumColIni+i);
                    tbc.setHeaderValue("" + strTitTbl + "");
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(20);
                    if(p==INT_TBL_DAT_CAN_ING){
                        //PARA EL BOTON DE ingresos
                        objTblCelRenButIng=new ZafTblCelRenBut();
                        tbc.setCellRenderer(objTblCelRenButIng);
                        tbc.setCellEditor(objTblCelEdiButIng);
                    }
                    else{
                        objTblCelRenButEgr=new ZafTblCelRenBut();
                        tbc.setCellRenderer(objTblCelRenButEgr);
                        tbc.setCellEditor(objTblCelEdiButEgr);
                    }
                    vecAux.add("" + (intNumColIni+i));
                }
                //Agrupar las columnas.
                if (p==INT_TBL_DAT_STK_ACT){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatBodSel, k, INT_ARL_NOM_BOD_SEL) );                    
                    objTblHeaColGrp.setHeight(16);
                    k++;//esta variable se incrementa de 1 en 1 por cada bloque
                }
                
                if(objTblCelRenLblCon!=null){
                    if((k % 2)==0){
                        objTblCelRenLblCon.setBackground(colFonColPar);
                    }
                    else
                        objTblCelRenLblCon.setBackground(colFonColImp);
                }
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

                if(p==INT_TBL_DAT_COD_REG)
                    p=0;
                else
                    p++;

            }
            tbc=null;
            intNumColFinCon=objTblMod.getColumnCount();
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            p=0;
            for (int i=0; i<(intNumColAdiCon*intColPreModDat); i++){
                if((p==INT_TBL_DAT_COD_REG) ){//oculta la columna de codigoRegistro
                    tblDat.getColumnModel().getColumn(intNumColIni+i).setWidth(0);
                    tblDat.getColumnModel().getColumn(intNumColIni+i).setMaxWidth(0);
                    tblDat.getColumnModel().getColumn(intNumColIni+i).setMinWidth(0);
                    tblDat.getColumnModel().getColumn(intNumColIni+i).setPreferredWidth(0);
                    tblDat.getColumnModel().getColumn(intNumColIni+i).setResizable(false);
                }
                if(p==INT_TBL_DAT_COD_REG)
                    p=0;
                else
                    p++;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que adiciona las columnas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean adicionaColumnaTotales_CantidadContada(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        intNumColAdiConTot=1;
        try{
            intNumColIniConTot=objTblMod.getColumnCount();
      
            tbc=new javax.swing.table.TableColumn(intNumColIniConTot);
            tbc.setHeaderValue("");
            //Configurar JTable: Establecer el ancho de la columna.
            tbc.setPreferredWidth(80);
            //Configurar JTable: Agregar la columna al JTable.
            objTblMod.addColumn(tblDat, tbc);
            tbc=null;
            
            //tbc.setCellRenderer(objTblCelRenLblTot);
             
            intNumColFinConTot=objTblMod.getColumnCount();            
      
            tblDat.getColumnModel().getColumn(intNumColFinCon).setWidth(0);
            tblDat.getColumnModel().getColumn(intNumColFinCon).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(intNumColFinCon).setMinWidth(0);
            tblDat.getColumnModel().getColumn(intNumColFinCon).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(intNumColFinCon).setResizable(false);
        
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
  
    private boolean cargar_tbmConInv()
    {
        boolean blnRes=true;
        int i, intNumFilTblBod;
        String strFecReaCon="";
        String strCodRegExi="E";
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intNumFilTblBod=objTblModBod.getRowCountTrue();
            
            if (con!=null){
                strAux="";

                if(! txtCodItm.getText().toString().equals(""))
                    strAux+=" AND a1.co_itm=" + txtCodItm.getText()  + "";
                
                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                if (txtCodAltItmTer.getText().length()>0)
                    strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                
                if(objFecReaCon.isCheckBoxChecked()){
                    switch (objFecReaCon.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strFecReaCon+=" AND CAST(a3.fe_reaCon AS date) BETWEEN '" + objUti.formatearFecha(objFecReaCon.getFechaDesde(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objFecReaCon.getFechaHasta(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strFecReaCon+=" AND CAST(a3.fe_reaCon AS date)<='" + objUti.formatearFecha(objFecReaCon.getFechaHasta(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strFecReaCon+=" AND CAST(a3.fe_reaCon AS date)>='" + objUti.formatearFecha(objFecReaCon.getFechaDesde(), objFecReaCon.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }

                //hacer que solo se presente el ultimo conteo basado por bodega(solo el ultimo)
                stm=con.createStatement();
                
                strSQL ="";
                strSQL+=" SELECT b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.co_itmMae, b1.tx_desCor, b1.tx_desLar";
                for (int j=0; j<intNumFilTblBod; j++){
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                        strSQL+=", SUM(b" + (j+2) + ".nd_stkAct) AS b" + (j+2) + "_nd_stkAct";//7
                        strSQL+=", SUM(b" + (j+2) + ".nd_canFalBodAso) AS b" + (j+2) + "_nd_canFalBodAso";
                        strSQL+=", SUM(b" + (j+2) + ".nd_stkSob) AS b" + (j+2) + "_nd_stkSob";                        
                        strSQL+=", SUM(b" + (j+2) + ".nd_stkCon) AS b" + (j+2) + "_nd_stkCon";
                        strSQL+=", ((SUM(b" + (j+2) + ".nd_stkCon)) - (SUM(b" + (j+2) + ".nd_stkAct)) )  AS b" + (j+2) + "_nd_difCan  ";
                        strSQL+=", ((SUM(b" + (j+2) + ".nd_stkCon)) - (SUM(b" + (j+2) + ".nd_stkAct) - (SUM(b" + (j+2) + ".nd_canIngBod)) + (SUM(b" + (j+2) + ".nd_canEgrBod))  ) )  AS b" + (j+2) + "_nd_difCnf  ";
                        strSQL+=", SUM(b" + (j+2) + ".nd_canIngBod) AS b" + (j+2) + "_nd_canIngBod";
                        strSQL+=", SUM(b" + (j+2) + ".nd_canEgrBod) AS b" + (j+2) + "_nd_canEgrBod";                        
                        strSQL+=", b" + (j+2) + ".fe_reaCon AS b" + (j+2) + "_fe_reaCon";
                        strSQL+=", b" + (j+2) + ".tx_usr AS b" + (j+2) + "_tx_usr";
                        strSQL+=", b" + (j+2) + ".co_reg AS b" + (j+2) + "_co_reg";                        
                    }
                }
                strSQL+=" FROM(";
                strSQL+="     SELECT b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.co_itmMae, b1.tx_desCor, b1.tx_desLar";
                strSQL+="     FROM(";
                strSQL+=" 	   SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm,c1.co_itmMae, b1.tx_desCor, b1.tx_desLar, a2.co_bod AS co_bodGrp";
                strSQL+=" 	   FROM ((tbm_inv AS a1 INNER JOIN tbm_var AS b1 ON a1.co_uni=b1.co_reg) INNER JOIN tbm_equInv AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm)";
                strSQL+=" 	   INNER JOIN tbm_invBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+=" 	   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	   AND a1.tx_codAlt NOT LIKE '%L'";
                strSQL+="          " + strAux + "";
                strSQL+=" 	   GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm,c1.co_itmMae, b1.tx_desCor, b1.tx_desLar, a2.co_bod";
                strSQL+=" 	   ORDER BY a1.tx_codAlt";
                strSQL+="     ) AS b1";
                strSQL+="     GROUP BY b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm,b1.co_itmMae, b1.tx_desCor, b1.tx_desLar";
                strSQL+="     ORDER BY b1.tx_codAlt";
                strSQL+=" ) AS b1";
                
                for (int j=0; j<intNumFilTblBod; j++){
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                        strSQL+=" LEFT OUTER JOIN (";
                        strSQL+="     SELECT b1.co_itmMae, b2.nd_stkAct, b2.nd_canSob AS nd_stkSob, b2.nd_canFalBodAso, b2.nd_stkCon, b1.co_bod";
                        strSQL+=" 	   , b1.co_reg, b2.nd_caningbod, b2.nd_canegrbod, b2.co_usrrescon, b2.fe_reacon, b2.tx_usr";
                        strSQL+="     FROM(";
                        strSQL+=" 	   SELECT a3.co_emp, MAX(a3.co_reg) AS co_reg, a3.co_itm, a5.co_itmMae, a3.co_bod";
                        strSQL+=" 	   FROM tbm_conInv AS a3 INNER JOIN tbm_inv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                        strSQL+=" 	   INNER JOIN tbm_equInv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
                        strSQL+=" 	   WHERE a3.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + "";
                        strSQL+=" 	   AND a3.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + "";
                        strSQL+="          AND a3.st_conrea='S'";
                        strSQL+="          " + strFecReaCon + "";
                        strSQL+=" 	   GROUP BY a3.co_emp, a3.co_itm, a5.co_itmMae, a3.co_bod";
                        strSQL+="     ) AS b1";
                        strSQL+="     INNER JOIN(";
                        strSQL+=" 	   SELECT a2.co_itmMae, SUM(a3.nd_stksis) AS nd_stkAct, a3.co_bod, a3.co_reg";
                        strSQL+=" 	        , SUM(a3.nd_canSob) AS nd_canSob, SUM(a3.nd_canfalbodaso) AS nd_canFalBodAso, SUM(a3.nd_stkcon) AS nd_stkCon";
                        strSQL+=" 		, a3.nd_caningbod, a3.nd_canegrbod, a3.co_usrrescon, a3.fe_reacon, c1.tx_usr";
                        strSQL+=" 	   FROM (tbm_conInv AS a3 INNER JOIN tbm_usr AS c1 ON a3.co_usrrescon=c1.co_usr)";
                        strSQL+=" 	   INNER JOIN tbm_equInv AS a2 ON (a3.co_emp=a2.co_emp AND a3.co_itm=a2.co_itm)";
                        strSQL+=" 	   WHERE a3.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + "";
                        strSQL+=" 	   AND a3.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + "";
                        strSQL+=" 	   AND a3.st_conrea='S'";
                        strSQL+="          " + strFecReaCon + "";
                        strSQL+=" 	   GROUP BY a2.co_itmMae, a3.co_bod, a3.co_reg, a3.nd_caningbod, a3.nd_canegrbod, a3.co_usrrescon, a3.fe_reacon, c1.tx_usr";
                        strSQL+="     ) AS b2 ON b1.co_itmMae=b2.co_itmMae AND b1.co_bod=b2.co_bod AND b1.co_reg=b2.co_reg";
                        strSQL+="     ORDER BY b1.co_itmMae";
                        strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
                    }
                }
                strSQL+=" GROUP BY b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.co_itmMae, b1.tx_desCor, b1.tx_desLar";
                
                for (int j=0; j<intNumFilTblBod; j++){
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                        strSQL+=", b" + (j+2) + ".co_reg";
                        strSQL+=", b" + (j+2) + ".co_usrResCon";
                        strSQL+=", b" + (j+2) + ".fe_reaCon";
                        strSQL+=", b" + (j+2) + ".tx_usr";
                        strSQL+=", b" + (j+2) + ".co_bod";
                    }
                }                
                strSQL+=" ORDER BY b1.tx_codAlt";
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                vecDatFix.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;
    
                while(rst.next()){
                    if (blnCon){
                        //TABLA DATOS
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,             "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getObject("co_itm")==null?"":rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,     "" + rst.getObject("co_itmMae")==null?"":rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getObject("tx_codAlt")==null?"":rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getObject("tx_nomItm")==null?"":rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED, "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_UNI_MED, "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));

                        for (int j=(intNumFilTblBod-1); j>=0; j--){
                            if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_STK_ACT), "" + rst.getObject("b" + (j+2) + "_nd_stkAct")==null?"0":rst.getString("b" + (j+2) + "_nd_stkAct"));//nd_stkAct
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_STK_FAL), "" + rst.getObject("b" + (j+2) + "_nd_canFalBodAso")==null?"0":rst.getString("b" + (j+2) + "_nd_canFalBodAso"));//_nd_stkFal
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_STK_SOB), "" + rst.getObject("b" + (j+2) + "_nd_stkSob")==null?"0":rst.getString("b" + (j+2) + "_nd_stkSob"));//_nd_stkSob
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_STK_CON), "" +(rst.getObject("b" + (j+2) + "_nd_stkCon")==null?"":rst.getString("b" + (j+2) + "_nd_stkCon").equals("")?"":rst.getString("b" + (j+2) + "_nd_stkCon") )   );//_nd_stkCon
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_DIF_CAN), "" + rst.getObject("b" + (j+2) + "_nd_difCan")==null?"0":rst.getString("b" + (j+2) + "_nd_difCan"));//dif.can   
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_DIF_CNF), "" + rst.getObject("b" + (j+2) + "_nd_difCnf")==null?"0":rst.getString("b" + (j+2) + "_nd_difCnf"));//dif.config
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_TXT_USR), "" + rst.getObject("b" + (j+2) + "_tx_usr")==null?"0":rst.getString("b" + (j+2) + "_tx_usr"));//usuario conteo
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_FEC_CON), "" +(rst.getObject("b" + (j+2) + "_fe_reaCon")==null?"": (rst.getObject("b" + (j+2) + "_fe_reaCon").toString().equals("") ?"":(objUti.formatearFecha(rst.getString("b" + (j+2) + "_fe_reaCon"), "yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd    hh:mm:ss")) ) )   );//fecha de conteo     
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_CAN_ING), "" + rst.getObject("b" + (j+2) + "_nd_canIngBod")==null?"0":rst.getString("b" + (j+2) + "_nd_canIngBod"));//pendiente de ingresar
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_CAN_EGR), "" + rst.getObject("b" + (j+2) + "_nd_canEgrBod")==null?"0":rst.getString("b" + (j+2) + "_nd_canEgrBod"));//pendiente de egresar                                
                                vecReg.add( (INT_TBL_DAT_COL_DIN+INT_TBL_DAT_COD_REG), "" + rst.getObject("b" + (j+2) + "_co_reg")==null?"0":rst.getString("b" + (j+2) + "_co_reg"));//codigo de registro del conteo
                                
                                if(strCodRegExi.equals("E")){
                                    if( ! (  (rst.getObject("b" + (j+2) + "_co_reg")==null?"":rst.getString("b" + (j+2) + "_co_reg")).equals("")))
                                        strCodRegExi="";//existe algun codigo de conteo
                                }
                            }
                        }
                        
                        //adicionar columna de totales solo de las columnas de conteo, me sirven para ver cuales debo eliminar porque no se han realizado conteo.
                        for(int j=intNumColIniConTot; j<intNumColFinConTot;j++){
                            vecReg.add(j,     "" + strCodRegExi);
                        }
                        strCodRegExi="E";

                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                        
                        //TABLA FIXED
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_DAT_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_EMP,          "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ITM,          "" + rst.getObject("co_itm")==null?"":rst.getString("co_itm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ITM_MAE,      "" + rst.getObject("co_itmMae")==null?"":rst.getString("co_itmMae"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM,      "" + rst.getObject("tx_codAlt")==null?"":rst.getString("tx_codAlt"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_DOS,      "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecRegFix.add(INT_TBL_DAT_FIX_NOM_ITM,          "" + rst.getObject("tx_nomItm")==null?"":rst.getString("tx_nomItm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_DES_COR_UNI_MED,  "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        vecRegFix.add(INT_TBL_DAT_FIX_DES_LAR_UNI_MED,  "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                        
                        vecDatFix.add(vecRegFix);
                    }
                    else{
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                
                //Asignar vectores al modelo.
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();
                
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                objTblMod.initRowsState();

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
    
    private boolean cargarDatFiltros(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(chkMosItmConAso.isSelected()){
                    if(quitaFilasSinConteoAsociado()){
                        blnRes=true;
                    }
                }
                else if(chkMosItmSinConAso.isSelected()){
                    if(quitaFilasConConteoAsociado()){
                        blnRes=true;
                    }
                }
            }            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean quitaFilasSinConteoAsociado(){
        boolean blnRes=true;
        String strEstColExi="";
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);
            
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                for(int j=intNumColIniConTot; j<intNumColFinConTot;j++){
                    strEstColExi=(objTblMod.getValueAt(i, j).toString());
                    if(strEstColExi.equals("E")){
                        objTblMod.removeRow(i);
                        objTblModFix.removeRow(i);
                    }
                }
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
            objTblModFix.removeEmptyRows();
            
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean quitaFilasConConteoAsociado(){
        boolean blnRes=true;
        String strEstColExi="";
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);
            
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                for(int j=intNumColIniConTot; j<intNumColFinConTot;j++){
                    strEstColExi=(objTblMod.getValueAt(i, j).toString());
                    if(!strEstColExi.equals("E")){
                        objTblMod.removeRow(i);
                        objTblModFix.removeRow(i);
                    }
                }
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
            objTblModFix.removeEmptyRows();
            
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }       
    
    

}
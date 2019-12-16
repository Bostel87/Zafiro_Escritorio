/*
 * ZafImp21.java
 *
 *  Created on 21 de Septiembre de 2015
 */
package Importaciones.ZafImp21;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Compras.ZafCom91.ZafCom91;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafRptSis.ZafRptSis;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import java.math.BigDecimal;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.util.Vector;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp21 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP_ING_IMP=2;
    final int INT_TBL_DAT_COD_LOC_ING_IMP=3;
    final int INT_TBL_DAT_COD_TIP_DOC_ING_IMP=4;
    final int INT_TBL_DAT_COD_DOC_ING_IMP=5;
    final int INT_TBL_DAT_COD_REG_ING_IMP=6;
    final int INT_TBL_DAT_COD_ITM_GRP=7;
    final int INT_TBL_DAT_COD_ITM_EMP=8;
    final int INT_TBL_DAT_COD_ITM_MAE=9;
    final int INT_TBL_DAT_COD_ALT_ITM=10;
    final int INT_TBL_DAT_COD_ITM_ALTDOS=11;
    final int INT_TBL_DAT_NOM_ITM=12;
    final int INT_TBL_DAT_DES_COR_UNI_MED=13;
    final int INT_TBL_DAT_CAN_ING_IMP=14;
    final int INT_TBL_DAT_CAN_AUT=15;
    final int INT_TBL_DAT_CAN_CON=16;
    final int INT_TBL_DAT_CAN_UTI=17;
    final int INT_TBL_DAT_CAN_TRA_OK=18;
    final int INT_TBL_DAT_CAN_PEN=19;
    final int INT_TBL_DAT_CAN_USR_SOLTRA=20;
    final int INT_TBL_DAT_COS_UNI=21;
    final int INT_TBL_DAT_COS_TOT=22;
    final int INT_TBL_DAT_PES_UNI=23;
    final int INT_TBL_DAT_PES_TOT=24;
    final int INT_TBL_DAT_EST_ITM_EXI_ING_IMP=25;
    final int INT_TBL_DAT_EST_ITM_TIE_DOC_AJU=26;                               //Indica que el item tiene documento de ajuste.
    
    //Para Inmaconsa
    final int INT_ARL_DAT_BOD_INM_COD_EMP_ING_IMP=0;
    final int INT_ARL_DAT_BOD_INM_COD_LOC_ING_IMP=1;
    final int INT_ARL_DAT_BOD_INM_COD_TIP_DOC_ING_IMP=2;
    final int INT_ARL_DAT_BOD_INM_COD_DOC_IMP=3;
    final int INT_ARL_DAT_BOD_INM_COD_REG_ING_IMP=4;
    final int INT_ARL_DAT_BOD_INM_COD_ITM_EMP=5;
    final int INT_ARL_DAT_BOD_INM_COD_ITM_MAE=6;
    final int INT_ARL_DAT_BOD_INM_CAN_ING_IMP=7;
    final int INT_ARL_DAT_BOD_INM_CAN_AUT_BOD=8;
    final int INT_ARL_DAT_BOD_INM_CAN_PEN_TRS=9;
    final int INT_ARL_DAT_BOD_INM_CAN_CON=10;
    final int INT_ARL_DAT_BOD_INM_CAN_MAL_EST=11;
        
    // Constantes: Para el contenedor a enviar al programa de Solicitudes de Transferencias (ZafCom91).
    private ArrayList arlDatItm, arlRegItm;
    private ArrayList arlDatSolTraInv, arlRegSolTraInv;
    private static final int INT_ARL_GEN_DAT_SOL_PK_INGIMP=0;
    private static final int INT_ARL_GEN_DAT_SOL_CODEMP_INGIMP=1;
    private static final int INT_ARL_GEN_DAT_SOL_CODLOC_INGIMP=2;
    private static final int INT_ARL_GEN_DAT_SOL_CODTIPDOC_INGIMP=3;
    private static final int INT_ARL_GEN_DAT_SOL_CODDOC_INGIMP=4;
    private static final int INT_ARL_GEN_DAT_SOL_NUMPED_INGIMP=5;
    private static final int INT_ARL_GEN_DAT_SOL_ITM_GRP=6;
    private static final int INT_ARL_GEN_DAT_SOL_CAN=7;
    private static final int INT_ARL_GEN_DAT_SOL_PES_UNI=8;
    private static final int INT_ARL_GEN_DAT_SOL_PES_UNITOT=9;
        
    //Arreglos para Solicitudes que se van a imprimir.
    private ArrayList arlDatSolImp, arlRegSolImp;
    private static final int INT_ARL_IMP_SOL_TRA_COD_EMP=0;  
    private static final int INT_ARL_IMP_SOL_TRA_COD_LOC=1;   
    private static final int INT_ARL_IMP_SOL_TRA_COD_TIPDOC=2;  
    private static final int INT_ARL_IMP_SOL_TRA_COD_DOC=3;  
    
    //ArrayList para consultar
    private ArrayList arlDatConSol, arlRegConSol;
    private static final int INT_ARL_CON_SOL_TRA_COD_EMP=0;  
    private static final int INT_ARL_CON_SOL_TRA_COD_LOC=1;   
    private static final int INT_ARL_CON_SOL_TRA_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_SOL_TRA_COD_DOC=3;  
    private static final int INT_ARL_CON_SOL_TRA_NUM_PED=4;
    private int intIndiceSolTra=0;
    
    //Configuracion Documentos Automaticos.
    private static final int INT_COD_CFG_TIP_AUT_SOTRIN_IMP=2002;               //Codigo de la configuracion solicitud de IMPORTACIONES.
    private static final int INT_COD_CFG_TIP_AUT_SOTRIN_LOC=2003;               //Codigo de la configuracion solicitud de COMPRAS LOCALES.
    
    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private java.util.Date datFecAux;                                           //Auxiliar: Para almacenar fechas.
    private ZafTblCelRenLbl objTblCelRenLbl,objTblCelRenLblCanUsr, objTblCelRenLblTxt; //Render: Presentar JLabel en JTable.
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafMouMotAda objMouMotAda;                                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafVenCon vcoTipDoc;                                                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoBodOri, vcoBodDes;                                     //Ventana de consulta "Bodega".
    private ZafVenCon vcoPedIngImp;                                             //Ventana de consulta "Pedidos".
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenChk objTblCelRenChk;                                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                                    //Editor: JCheckBox en celda.
    private ZafRptSis objRptSis;                                                //Reportes del Sistema.
    private ZafThreadGUI objThrGUI;
    private ZafCom91 objCom91;
    private MiToolBar objTooBar;
    private ZafImp objImp;

    private String strSQL,  strAux;
    private Vector vecDat,  vecCab,  vecReg;
    private Vector vecAux;
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnHayCam;                                                  //Determina si hay cambios en el formulario.    
    private boolean blnVenConEme=false;                                         //Ventana Consulta Emergente: Para identificar cuando sea llamado desde otro programa.
     
    private int intCodEmpPedIngImp;
    private int intCodLocPedIngImp;
    private int intCodTipDocPedIngImp;
    private int intCodDocPedIngImp;
    private int intCodCfgSolTra;                                                //Parametro que contiene código de configuración para la solicitud de transferencias de inventario.
    
    private String strDesCorTipDoc,  strDesLarTipDoc;                           //Contenido del campo al obtener el foco.        
    private String strCodBodOri,  strNomBodOri;                                 //Contenido del campo al obtener el foco.
    private String strCodBodDes,  strNomBodDes;                                 //Contenido del campo al obtener el foco.
    private String strPesTotKgrSolTra;                                          //Obtiene el peso total de la solicitud de transferencia.
    private String strCodPedIngImp, strPedIngImp;                               //Contenido del campo al obtener el foco. (strPedIngImp=Número de Pedido de Importacion correspondiente a la solicitud de transferencia.)
    
    private String strVer=" v0.1.23";
    
    
    /** Crea una nueva instancia de la clase ZafImp21. */
    public ZafImp21(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e) 
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /**
     * Constructor utilizado para ser llamado desde otra ventana.
     * @author Rosa Zambrano
     * @fecha 12/Jun/2017
     */
    public ZafImp21(ZafParSis obj, String strCodEmp, String strCodLoc, String strTipDoc, String strCodDoc) 
    {
        try
        {
            objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText("Solicitudes de Transferencia de Inventario (Importaciones)");

            objParSis.setCodigoEmpresa(Integer.parseInt(strCodEmp)) ;
            objParSis.setCodigoLocal(Integer.parseInt(strCodLoc)) ;
            txtCodTipDoc.setText(strTipDoc);
            txtCodDoc.setText(strCodDoc);
            
            objTooBar.setVisible(false);
            blnVenConEme=true;
            
            consultarReg();
        }
        catch (CloneNotSupportedException e) {        objUti.mostrarMsgErr_F1(this, e);      }
    } 
    
    /**
     * Constructor utilizado como HashMap para usarlo cuando se llama a la clase en forma dinamica.
     * @author Rosa Zambrano
     * @fecha 12/Jun/2017
     * @param map 
     */
    public ZafImp21(HashMap map) 
    {
        ZafParSis obj;
        Integer codigoEmpresa, codigoLocal;
        
        obj = (ZafParSis) map.get("objParSis");
        codigoEmpresa = Integer.valueOf(new String(map.get("strCodEmp").toString()));
        codigoLocal = Integer.valueOf(new String(map.get("strCodLoc").toString()));
        
        try
        {
            //this.objParSis = (ZafParSis) obj.clone();
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText("Solicitudes de Transferencia de Inventario (Importaciones)");

            objParSis.setCodigoEmpresa(codigoEmpresa) ;
            objParSis.setCodigoLocal(codigoLocal) ;
            txtCodTipDoc.setText(new String(map.get("strCodTipDoc").toString()));
            txtCodDoc.setText(new String(map.get("strCodDoc").toString()));
            
            objTooBar.setVisible(false);
            blnVenConEme=true;
            
            consultarReg();
        }
        catch (CloneNotSupportedException e) {        objUti.mostrarMsgErr_F1(this, e);      }
    }    
    
    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try {
            //Inicializar objetos.
            objUti = new ZafUtil();
            objCom91 = new ZafCom91(objParSis);
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            //String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker:
            //DESDE
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(558, 6, 122, 20);
            
            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodPedIngImp.setBackground(objParSis.getColorCamposObligatorios());
            txtPedIngImp.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodOri.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodOri.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());

            txtCodCfg.setVisible(false);
            txtCodTipDoc.setVisible(false);
            txtCodPedIngImp.setVisible(false);
            txtCodBodOriEmp.setVisible(false);
            txtCodBodDesEmp.setVisible(false);
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            
            //Configurar los JTables.
            configurarTblDat();   
            
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);
            
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butCarItmPenTrs);
            
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);

        }
        catch (Exception e)  {
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
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(27);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK, "");
            vecCab.add(INT_TBL_DAT_COD_EMP_ING_IMP, "Cód.Emp.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ING_IMP, "Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, "Cód.Tip.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ING_IMP, "Cód.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_REG_ING_IMP, "Cód.Reg.Ing.Imp.");            
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP, "Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP, "Cód.Itm.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_ALTDOS, "Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_CAN_ING_IMP, "Can.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CAN_AUT, "Can.Aut.");
            vecCab.add(INT_TBL_DAT_CAN_CON, "Can.Con.");
            vecCab.add(INT_TBL_DAT_CAN_UTI, "Can.Uti.");
            vecCab.add(INT_TBL_DAT_CAN_TRA_OK, "Can.Trs.");
            vecCab.add(INT_TBL_DAT_CAN_PEN, "Can.Pen."); 
            vecCab.add(INT_TBL_DAT_CAN_USR_SOLTRA, "Can.Usr.Sol.Trs.");
            vecCab.add(INT_TBL_DAT_COS_UNI, "Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT, "Cos.Tot.");
            vecCab.add(INT_TBL_DAT_PES_UNI, "Pes.Uni.");
            vecCab.add(INT_TBL_DAT_PES_TOT, "Pes.Tot.");
            vecCab.add(INT_TBL_DAT_EST_ITM_EXI_ING_IMP, "Itm.Exi.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_EST_ITM_TIE_DOC_AJU, "Itm.Tie.Doc.Aju.");
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_USR_SOLTRA, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el menó de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AUT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_UTI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRA_OK).setPreferredWidth(70);     
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_USR_SOLTRA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST_ITM_EXI_ING_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
         
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_AUT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_UTI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TRA_OK, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_TOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ITM_EXI_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_ITM_TIE_DOC_AJU, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de bósqueda.
            objTblBus = new ZafTblBus(tblDat);

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CAN_USR_SOLTRA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="", strTieDocAju="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP)==null?"":objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP).toString();
                    strTieDocAju=objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU)==null?"":objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).toString();
                    
                    if (strEstExiItm.equals("N") && strTieDocAju.equals("N")){
                        objTblCelRenChk.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
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

            //Libero los objetos auxiliares.
            tcmAux = null;
            
            objTblCelRenLblTxt=new ZafTblCelRenLbl();
            objTblCelRenLblTxt.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblTxt.setTipoFormato(objTblCelRenLblTxt.INT_FOR_GEN);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM_GRP).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM_EMP).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM_ALTDOS).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setCellRenderer(objTblCelRenLblTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ITM_EXI_ING_IMP).setCellRenderer(objTblCelRenLblTxt);   
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).setCellRenderer(objTblCelRenLblTxt);   
            
            objTblCelRenLblTxt.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="", strTieDocAju="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP)==null?"":objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP).toString();
                    strTieDocAju=objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU)==null?"":objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).toString();

                    if (strEstExiItm.equals("N") && strTieDocAju.equals("N")){
                        objTblCelRenLblTxt.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblTxt.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_ING_IMP).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_AUT).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_CON).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_UTI).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_TRA_OK).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_PEN).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_UNI).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_TOT).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="", strTieDocAju="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP)==null?"":objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP).toString();
                    strTieDocAju=objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU)==null?"":objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).toString();

                    if (strEstExiItm.equals("N") && strTieDocAju.equals("N")){
                        objTblCelRenLbl.setBackground(colFonColCru);
                    }
                    else{
                         objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            //Cantidad que se solicita transferir.
            objTblCelRenLblCanUsr=new ZafTblCelRenLbl();
            objTblCelRenLblCanUsr.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCanUsr.setTipoFormato(objTblCelRenLblCanUsr.INT_FOR_NUM);
            objTblCelRenLblCanUsr.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_USR_SOLTRA).setCellRenderer(objTblCelRenLblCanUsr);
            
            objTblCelRenLblCanUsr.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="", strTieDocAju="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenLblCanUsr.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP)==null?"":objTblMod.getValueAt(objTblCelRenLblCanUsr.getRowRender(), INT_TBL_DAT_EST_ITM_EXI_ING_IMP).toString();
                    strTieDocAju=objTblMod.getValueAt(objTblCelRenLblCanUsr.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU)==null?"":objTblMod.getValueAt(objTblCelRenLblCanUsr.getRowRender(), INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).toString();

                    if (strEstExiItm.equals("N") && strTieDocAju.equals("N")){
                        objTblCelRenLblCanUsr.setBackground(colFonColCru);
                    }
                    else if (strEstExiItm.equals("S") || strTieDocAju.equals("S")){
                        objTblCelRenLblCanUsr.setBackground(objParSis.getColorCamposObligatorios());
                    }
                    else  {
                        objTblCelRenLblCanUsr.setBackground( javax.swing.UIManager.getColor("Table.background") );
                    }
                }
            });
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_USR_SOLTRA).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeValCanAut=new BigDecimal("0");
                BigDecimal bdeValCanPenBef=new BigDecimal("0");
                BigDecimal bdeValCanUsrSolTrsBef=new BigDecimal("0");
                BigDecimal bdeValCanUsrSolTrsAft=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    //actualizaCantidadesBD(intFil); //No se validara por item, sino antes de insertar.
                    
                    bdeValCanAut=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_AUT)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_AUT).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_AUT).toString()));
                    bdeValCanPenBef=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_PEN).toString()));
                    bdeValCanUsrSolTrsBef=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR_SOLTRA)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR_SOLTRA).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR_SOLTRA).toString()));
                    
                    //Valida si la Cantidad Autorizada es igual o menor a 0, entonces se bloquea la celda.
                    if(bdeValCanAut.compareTo(new BigDecimal("0"))<=0 )
                        objTblCelEdiTxt.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeValCanUsrSolTrsAft=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR_SOLTRA)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR_SOLTRA).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_USR_SOLTRA).toString()));
                    
                    //Si la cantidad pendiente es mayor o igual cantidad a transferir.
                    if(bdeValCanPenBef.compareTo(bdeValCanUsrSolTrsAft)>=0){
                        //Si la cantida a transferir es mayor a 0.
                        if(bdeValCanUsrSolTrsAft.compareTo(new BigDecimal("0"))>0){
                            //Cuando ya se habia colocado anteriormente un valor para transferir, y si el nuevo valor no cumple el criterio, regresar al valor anterior.
                            if(bdeValCanUsrSolTrsAft.compareTo(bdeValCanPenBef)>0){//se coloca la cantidad anterior
                                objTblMod.setValueAt(bdeValCanUsrSolTrsBef, intFil, INT_TBL_DAT_CAN_USR_SOLTRA);
                                if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK))
                                    objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                            }
                            else{
                                if(!objTblMod.isChecked(intFil, INT_TBL_DAT_CHK))
                                    objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                            }
                        }
                        else{//estoy borrando la cantidad y colocando CERO o borrando con DEL
                            if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK))
                                objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                        }
                    }
                    else{
                        if(bdeValCanUsrSolTrsBef.compareTo(new BigDecimal("0"))>0){
                            objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK);
                            objTblMod.setValueAt(bdeValCanUsrSolTrsBef, intFil, INT_TBL_DAT_CAN_USR_SOLTRA);
                        }
                        else{
                            objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK);
                            objTblMod.setValueAt(bdeValCanUsrSolTrsBef, intFil, INT_TBL_DAT_CAN_USR_SOLTRA);
                        }
                    }
                    
                    calcularCosPesVal(intFil);//Función que calcula los valores de las columnas de Costo Unitario, Costo Total, Peso Total(valores de columnas) y setea valores del documento y peso total de la cabecera
                    calcularValPesDoc();
                }
            });            

            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);

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
     * resulta muy corto para mostrar leyendas que requieren mós espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_DAT_COD_EMP_ING_IMP:
                    strMsg = "Código de empresa ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_LOC_ING_IMP:
                    strMsg = "Código de local ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ING_IMP:
                    strMsg = "Código de tipo de documento ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_DOC_ING_IMP:
                    strMsg = "Código de documento ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_REG_ING_IMP:
                    strMsg = "Código de registro ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_ITM_GRP:
                    strMsg = "Código de item de grupo";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg = "Código de item de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código maestro de item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ITM_ALTDOS:
                    strMsg = "Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre de Item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg = "Unidad de Medida (Descripción Corta)";
                    break;
                case INT_TBL_DAT_CAN_ING_IMP:
                    strMsg = "Cantidad del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_CAN_AUT:
                    strMsg = "Cantidad autorizada hacia la bodega destino";
                    break;
                case INT_TBL_DAT_CAN_CON:
                    strMsg = "Cantidad contada por inventaristas";
                    break;                    
                case INT_TBL_DAT_CAN_UTI:
                    strMsg = "Cantidad utilizada en la transferencia a la bodega destino";
                    break;  
                case INT_TBL_DAT_CAN_TRA_OK:
                    strMsg = "Cantidad transferida a bodega";
                    break;
                case INT_TBL_DAT_CAN_PEN:
                    strMsg = "Cantidad pendiente de transferir";
                    break;
                case INT_TBL_DAT_CAN_USR_SOLTRA:
                    strMsg = "Cantidad que el usuario solicita transferir";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg = "Costo unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg = "Costo total";
                    break;
                case INT_TBL_DAT_PES_UNI:
                    strMsg = "Peso unitario";
                    break;
                case INT_TBL_DAT_PES_TOT:
                    strMsg = "Peso total";
                    break;
                case INT_TBL_DAT_EST_ITM_EXI_ING_IMP:
                    strMsg = "Item existe en el Ingreso por Importación";
                    break;    
                case INT_TBL_DAT_EST_ITM_TIE_DOC_AJU:
                    strMsg = "Item tiene documento de ajuste";
                    break;   
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }   

    /**
     * Esta función configura la "Ventana de consulta" que seró utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.co_cfg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Cod.Cfg.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("50");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) 
            {
                strSQL = "";
                strSQL+=" SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a3.co_cfg";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2, tbm_cfgTipDocUtiProAut AS a3";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=a3.co_emp AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_tipdoc";
            } 
            else  
            {
                strSQL ="";
                strSQL+=" SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a3.co_Cfg";
                strSQL+=" FROM tbr_tipDocUsr AS a2 INNER JOIN tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_cfgTipDocUtiProAut AS a3";
                strSQL+=" ON (a1.co_emp=a3.co_emp AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.st_reg NOT IN('I','E') ";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" ORDER BY a1.co_tipdoc";
            }

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
            vcoTipDoc.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para mostrar los "Pedidos".
     * @return true Si se pudo cargar la información
     * <BR> false caso contrario
     */
    private boolean configurarPedidosIngImp()
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
            strSQL+=getSQLPedidos();
            strSQL+=" ORDER BY a.tx_numDoc2";
            System.out.println("configurarPedidosIngImp: " + strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[5];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            intColOcu[4]=5;
            
            vcoPedIngImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPedIngImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPedIngImp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que retorna la sentencia SQL de Pedidos.
     * @return strPedidos
     */
    public String getSQLPedidos()
    {
        String strPedidos="";
        try
        {
            strPedidos+=" SELECT a.* ";
            strPedidos+=" FROM (";
            strPedidos+="    SELECT CASE WHEN Aju.st_ingImpAju IS NULL THEN Ing.st_ingImp ELSE Aju.st_ingImpAju END AS st_ingImp";
            strPedidos+="          , Ing.co_emp, Ing.co_loc, Ing.co_tipDoc, Ing.co_Doc, Ing.ne_numDoc, Ing.tx_numDoc2 ";
            strPedidos+="    FROM ";
            strPedidos+="    (";
            strPedidos+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strPedidos+="            , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END AS st_ingImp";
            strPedidos+="       FROM tbm_cabMovInv AS a1 "; 
            strPedidos+="       INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) ";
            strPedidos+="       INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp) ";
            strPedidos+="       LEFT OUTER JOIN tbm_expImp AS a5 ON (a1.co_exp=a5.co_exp) ";
            strPedidos+="       WHERE a1.st_reg='A' AND a1.co_mnu IN ("+objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+")";
            if(objTooBar.getEstado()!='c')
            {
                if(intCodCfgSolTra==INT_COD_CFG_TIP_AUT_SOTRIN_IMP)
                    strPedidos+=" AND a1.co_tipDoc IN("+objImp.INT_COD_TIP_DOC_ING_IMP+")";
                else if(intCodCfgSolTra==INT_COD_CFG_TIP_AUT_SOTRIN_LOC)
                    strPedidos+=" AND a1.co_tipDoc IN("+objImp.INT_COD_TIP_DOC_COM_LOC+")";
                
                //Valida que NO muestre pedidos no arribados
                strPedidos+=" AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END ) NOT IN ('N', 'P', 'A') ";
            }
            strPedidos+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2, a1.st_ingImp ";
            strPedidos+="    ) as Ing";
            //Valida los pedidos que tengan documentos de ajustes pendientes de transferir
            strPedidos+="    LEFT OUTER JOIN ";
            strPedidos+="    (";
            strPedidos+="       SELECT a1.co_emp AS co_empRel, a1.co_loc AS co_locRel, a1.co_tipDoc AS co_tipDocRel, a1.co_Doc AS co_DocRel, a1.tx_numDoc2, a.st_ingImpAju ";
            strPedidos+="       FROM ( ";
            strPedidos+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a1.st_aut";        
            strPedidos+="               , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END AS st_ingImpAju";
            strPedidos+="           FROM tbr_cabMovInv as a ";
            strPedidos+="           INNER JOIN tbm_cabMovInv as a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc";
            strPedidos+="           WHERE a1.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+ objParSis.getCodigoEmpresa();
            strPedidos+="                                  and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+")";
            strPedidos+="           AND a1.st_reg IN('A') ";
            strPedidos+="           AND a1.st_aut IN('A') ";
            //strPedidos+="           AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END) IN ('T')";
            strPedidos+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a1.st_aut , a1.st_ingImp";         
            strPedidos+="       ) as a ";
            strPedidos+="       INNER JOIN tbm_CabMovInv as a1 ON a1.co_emp=a.co_empRel AND a1.co_loc=a.co_locRel AND a1.co_tipDoc=a.co_tipDocRel AND a1.co_doc=a.co_docRel"; 
            strPedidos+="       INNER JOIN tbm_detMovInv as a2 ON a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc AND a2.co_doc=a.co_doc ";
            strPedidos+="       WHERE a2.nd_Can>0   /*AND a2.st_tipAju='S'*/";
            strPedidos+="       AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
            strPedidos+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_Doc, a1.tx_numDoc2, a.st_ingImpAju";
            strPedidos+="    ) as Aju ";
            strPedidos+=" ON (Aju.co_empRel=Ing.co_emp AND Aju.co_locRel=Ing.co_loc  AND Aju.co_tipDocRel=Ing.co_tipDoc  AND Aju.co_docRel=Ing.co_doc)";
            strPedidos+=" ) AS a ";
            if(objTooBar.getEstado()!='c')
            {
                strPedidos+=" WHERE a.st_ingImp IN ('T', 'A', 'B', 'M')"; //Importante que se actualice el st_ingImp='T' para saber que el documento de ajuste esta pendiente de solicitud de transferencia.
            }            
        }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
        return strPedidos;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que seró utilizada para
     * mostrar las "Bodegas de Origen".
     */
    private boolean configurarVenConBodOri() 
    {
        boolean blnRes = true;
        try {            
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bodGrp");
            arlCam.add("a1.tx_nomBodGrp");
            arlCam.add("a2.co_bodEmp");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega de Grupo");
            arlAli.add("Nombre de Bodega");
            arlAli.add("Código Bodega de Empresa");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            arlAncCol.add("50");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_bodGrp, a1.tx_nomBodGrp, a2.co_bodEmp";
            strSQL+=" FROM(";
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL+=" 	SELECT a1.co_emp AS co_empGrp, a1.co_bod AS co_bodGrp, a1.tx_nom AS tx_nomBodGrp";
                strSQL+=" 	FROM tbm_bod AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            }
            else  //no se coloca en el filtro al usuario porque no es el usuario q ingresa al sistema, sino q quiero q salgan todos los que estan configurados en la tabla "tbr_bodtipdocprgusr"
            {
                strSQL+="       SELECT a1.co_emp AS co_empGrp, a1.co_bod AS co_bodGrp, a1.tx_nom AS tx_nomBodGrp";
                strSQL+="       FROM tbm_bod AS a1 INNER JOIN tbr_bodTipDocPrgUsr AS a2";
                strSQL+="       ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="       AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+="       AND a2.tx_natbod='E'";
                strSQL+="       AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=3;

            vcoBodOri = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas por Usuarios", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu=null;
            //Configurar columnas.
            vcoBodOri.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } 
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que seró utilizada para
     * mostrar las "Bodegas de Destino".
     */
    private boolean configurarVenConBodDes() 
    {
        boolean blnRes = true;
        try 
        {            
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bodGrp");
            arlCam.add("a1.tx_nomBodGrp");
            arlCam.add("a2.co_bodEmp");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega de Grupo");
            arlAli.add("Nombre de Bodega");
            arlAli.add("Código Bodega de Empresa");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            arlAncCol.add("50");
            
            strSQL="";
            strSQL+="SELECT a1.co_bodGrp, a1.tx_nomBodGrp, a2.co_bodEmp";
            strSQL+=" FROM(";
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL+=" 	SELECT a1.co_emp AS co_empGrp, a1.co_bod AS co_bodGrp, a1.tx_nom AS tx_nomBodGrp";
                strSQL+=" 	FROM tbm_bod AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            }
            else //no se coloca en el filtro al usuario porque no es el usuario q ingresa al sistema, sino q quiero q salgan todos los que estan configurados en la tabla "tbr_bodtipdocprgusr"
            {
                strSQL+="       SELECT a1.co_emp AS co_empGrp, a1.co_bod AS co_bodGrp, a1.tx_nom AS tx_nomBodGrp";
                strSQL+="       FROM tbm_bod AS a1 INNER JOIN tbr_bodTipDocPrgUsr AS a2";
                strSQL+="       ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+="       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="       AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+="       AND a2.tx_natbod='I'";
                strSQL+="       AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=3;
            
            vcoBodDes = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas por Usuarios", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu=null;
            //Configurar columnas.
            vcoBodDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bósqueda determina si se debe hacer
     * una bósqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estó buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de bósqueda a realizar.
     * @return true: Si no se presentó ningón problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                    {
                        txtCodCfg.setText(vcoTipDoc.getValueAt(6));
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intCodCfgSolTra=Integer.parseInt(vcoTipDoc.getValueAt(6));   
                        txtCodPedIngImp.setText("");
                        txtPedIngImp.setText("");
                        txtCodBodOriEmp.setText("");
                        txtCodBodOri.setText("");
                        txtNomBodOri.setText("");
                        txtCodBodDesEmp.setText("");
                        txtCodBodDes.setText("");
                        txtNomBodDes.setText("");
                        configurarPedidosIngImp();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) 
                    {
                        txtCodCfg.setText(vcoTipDoc.getValueAt(6));
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intCodCfgSolTra=Integer.parseInt(vcoTipDoc.getValueAt(6));
                        txtCodPedIngImp.setText("");
                        txtPedIngImp.setText("");
                        txtCodBodOriEmp.setText("");
                        txtCodBodOri.setText("");
                        txtNomBodOri.setText("");
                        txtCodBodDesEmp.setText("");
                        txtCodBodDes.setText("");
                        txtNomBodDes.setText("");                        
                        configurarPedidosIngImp();
                    }
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) 
                        {
                            txtCodCfg.setText(vcoTipDoc.getValueAt(6));
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            intCodCfgSolTra=Integer.parseInt(vcoTipDoc.getValueAt(6));
                            txtCodPedIngImp.setText("");
                            txtPedIngImp.setText("");
                            txtCodBodOriEmp.setText("");
                            txtCodBodOri.setText("");
                            txtNomBodOri.setText("");
                            txtCodBodDesEmp.setText("");
                            txtCodBodDes.setText("");
                            txtNomBodDes.setText("");                            
                            configurarPedidosIngImp();
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
                        txtCodCfg.setText(vcoTipDoc.getValueAt(6));
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intCodCfgSolTra=Integer.parseInt(vcoTipDoc.getValueAt(6));
                        txtCodPedIngImp.setText("");
                        txtPedIngImp.setText("");
                        txtCodBodOriEmp.setText("");
                        txtCodBodOri.setText("");
                        txtNomBodOri.setText("");
                        txtCodBodDesEmp.setText("");
                        txtCodBodDes.setText("");
                        txtNomBodDes.setText("");                        
                        configurarPedidosIngImp();
                    } 
                    else 
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodCfg.setText(vcoTipDoc.getValueAt(6));
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            intCodCfgSolTra=Integer.parseInt(vcoTipDoc.getValueAt(6));
                            txtCodPedIngImp.setText("");
                            txtPedIngImp.setText("");
                            txtCodBodOriEmp.setText("");
                            txtCodBodOri.setText("");
                            txtNomBodOri.setText("");
                            txtCodBodDesEmp.setText("");
                            txtCodBodDes.setText("");
                            txtNomBodDes.setText("");                            
                            configurarPedidosIngImp();
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
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarPedidos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPedIngImp.setCampoBusqueda(5);
                    vcoPedIngImp.setVisible(true);
                    if (vcoPedIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodPedIngImp.setText(vcoPedIngImp.getValueAt(4));
                        txtPedIngImp.setText(vcoPedIngImp.getValueAt(6));
                        intCodEmpPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(1));
                        intCodLocPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(2));
                        intCodTipDocPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(3));
                        intCodDocPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(4));
                        strPedIngImp=vcoPedIngImp.getValueAt(6);
                        txtCodBodOri.setEnabled(true);
                        txtNomBodOri.setEnabled(true);
                        txtCodBodDes.setEnabled(true);
                        txtNomBodDes.setEnabled(true);
                        butBodOri.setEnabled(true);
                        butBodDes.setEnabled(true);
                        configurarVenConBodOri();
                        configurarVenConBodDes();
                        objTblMod.removeAllRows();
                        
                        if(txtPedIngImp.getText().length()>0)
                        {
                            if(isCamVal(2))
                            { 
                                if(objTooBar.getEstado()!='c')
                                    mostrarNumTipDoc();
                            }
                        }
                    }
                    break;
                 case 1: //Búsqueda directa por "Código Pedido".
                    if (vcoPedIngImp.buscar("a1.co_doc", txtCodPedIngImp.getText()))
                    {
                        txtCodPedIngImp.setText(vcoPedIngImp.getValueAt(4));
                        txtPedIngImp.setText(vcoPedIngImp.getValueAt(6));
                        intCodEmpPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(1));
                        intCodLocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(2));
                        intCodTipDocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(3));
                        intCodDocPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(4));
                        strPedIngImp=vcoPedIngImp.getValueAt(6);
                        txtCodBodOri.setEnabled(true);
                        txtNomBodOri.setEnabled(true);
                        txtCodBodDes.setEnabled(true);
                        txtNomBodDes.setEnabled(true);
                        butBodOri.setEnabled(true);
                        butBodDes.setEnabled(true);
                        configurarVenConBodOri();
                        configurarVenConBodDes();
                        objTblMod.removeAllRows();
                        if(txtPedIngImp.getText().length()>0)
                        {
                            if(isCamVal(2))
                            { 
                                if(objTooBar.getEstado()!='c')
                                    mostrarNumTipDoc();
                            }
                        }
                    }
                    else
                    {
                        vcoPedIngImp.setCampoBusqueda(3);
                        vcoPedIngImp.setCriterio1(11);
                        vcoPedIngImp.cargarDatos();
                        vcoPedIngImp.setVisible(true);
                        if (vcoPedIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPedIngImp.setText(vcoPedIngImp.getValueAt(4));
                            txtPedIngImp.setText(vcoPedIngImp.getValueAt(6));
                            intCodEmpPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(1));
                            intCodLocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(2));
                            intCodTipDocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(3));
                            intCodDocPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(4));
                            strPedIngImp=vcoPedIngImp.getValueAt(6);
                            txtCodBodOri.setEnabled(true);
                            txtNomBodOri.setEnabled(true);
                            txtCodBodDes.setEnabled(true);
                            txtNomBodDes.setEnabled(true);
                            butBodOri.setEnabled(true);
                            butBodDes.setEnabled(true);
                            configurarVenConBodOri();
                            configurarVenConBodDes();
                            objTblMod.removeAllRows();
                            if(txtPedIngImp.getText().length()>0)
                            {
                                if(isCamVal(2))
                                { 
                                    if(objTooBar.getEstado()!='c')
                                        mostrarNumTipDoc();
                                }
                            }
                        }
                        else
                        {
                            txtCodPedIngImp.setText(strCodPedIngImp); 
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoPedIngImp.buscar("a1.tx_numDoc2", txtPedIngImp.getText()))
                    {
                        txtCodPedIngImp.setText(vcoPedIngImp.getValueAt(4));
                        txtPedIngImp.setText(vcoPedIngImp.getValueAt(6));
                        intCodEmpPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(1));
                        intCodLocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(2));
                        intCodTipDocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(3));
                        intCodDocPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(4));
                        strPedIngImp=vcoPedIngImp.getValueAt(6);
                        txtCodBodOri.setEnabled(true);
                        txtNomBodOri.setEnabled(true);
                        txtCodBodDes.setEnabled(true);
                        txtNomBodDes.setEnabled(true);
                        butBodOri.setEnabled(true);
                        butBodDes.setEnabled(true);
                        configurarVenConBodOri();
                        configurarVenConBodDes();
                        objTblMod.removeAllRows();
                        if(txtPedIngImp.getText().length()>0)
                        {
                            if(isCamVal(2))
                            { 
                                if(objTooBar.getEstado()!='c')
                                    mostrarNumTipDoc();
                            }
                        }
                    }
                    else
                    {
                        vcoPedIngImp.setCampoBusqueda(5);
                        vcoPedIngImp.setCriterio1(11);
                        vcoPedIngImp.cargarDatos();
                        vcoPedIngImp.setVisible(true);
                        if (vcoPedIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPedIngImp.setText(vcoPedIngImp.getValueAt(4));
                            txtPedIngImp.setText(vcoPedIngImp.getValueAt(6));
                            intCodEmpPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(1));
                            intCodLocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(2));
                            intCodTipDocPedIngImp=Integer.parseInt(vcoPedIngImp.getValueAt(3));
                            intCodDocPedIngImp = Integer.parseInt(vcoPedIngImp.getValueAt(4));
                            strPedIngImp=vcoPedIngImp.getValueAt(6);
                            txtCodBodOri.setEnabled(true);
                            txtNomBodOri.setEnabled(true);
                            txtCodBodDes.setEnabled(true);
                            txtNomBodDes.setEnabled(true);
                            butBodOri.setEnabled(true);
                            butBodDes.setEnabled(true);
                            configurarVenConBodOri();
                            configurarVenConBodDes();
                            objTblMod.removeAllRows();
                            if(txtPedIngImp.getText().length()>0)
                            {
                                if(isCamVal(2))
                                { 
                                    if(objTooBar.getEstado()!='c')
                                        mostrarNumTipDoc();
                                }
                            }
                        }
                        else
                        {
                            txtPedIngImp.setText(strPedIngImp);
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

    private boolean mostrarVenConBodOri(int intTipBus) 
    {
        boolean blnRes = true;
        String strSQLTmp="";
        try 
        {
            strSQLTmp+=" ) AS a1";
            strSQLTmp+=" LEFT OUTER JOIN(";
            strSQLTmp+=" 	SELECT a1.co_emp AS co_empEmp, a1.co_bod AS co_bodEmp, a1.co_empGrp, a1.co_bodGrp";
            strSQLTmp+=" 	FROM tbr_bodEmpBodGrp AS a1";
            strSQLTmp+=" 	WHERE a1.co_emp=" + intCodEmpPedIngImp + "";
            strSQLTmp+=") AS a2";
            strSQLTmp+=" ON a1.co_empGrp=a2.co_empGrp AND a1.co_bodGrp=a2.co_bodGrp";
            strSQLTmp+=" ORDER BY a1.co_empGrp, a1.co_bodGrp";
            vcoBodOri.setCondicionesSQL(strSQLTmp);
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBodOri.setCampoBusqueda(2);
                    vcoBodOri.show();
                    if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) 
                    {
                        txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                        txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                        txtCodBodOriEmp.setText(vcoBodOri.getValueAt(3));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Búsqueda directa por "Bodega".
                    if (vcoBodOri.buscar("a1.co_bodGrp", txtCodBodOri.getText())) 
                    {
                        txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                        txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                        txtCodBodOriEmp.setText(vcoBodOri.getValueAt(3));
                        objTblMod.removeAllRows();
                    } 
                    else
                    {
                        vcoBodOri.setCampoBusqueda(0);
                        vcoBodOri.setCriterio1(11);
                        vcoBodOri.cargarDatos();
                        vcoBodOri.show();
                        if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) 
                        {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            txtCodBodOriEmp.setText(vcoBodOri.getValueAt(3));
                            objTblMod.removeAllRows();
                        } 
                        else 
                        {
                            txtCodBodOri.setText(strCodBodOri);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoBodOri.buscar("a1.tx_nomBodGrp", txtNomBodOri.getText())) 
                    {
                        txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                        txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                        txtCodBodOriEmp.setText(vcoBodOri.getValueAt(3));
                        objTblMod.removeAllRows();
                    } 
                    else 
                    {
                        vcoBodOri.setCampoBusqueda(1);
                        vcoBodOri.setCriterio1(11);
                        vcoBodOri.cargarDatos();
                        vcoBodOri.show();
                        if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) 
                        {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            txtCodBodOriEmp.setText(vcoBodOri.getValueAt(3));
                            objTblMod.removeAllRows();
                        } 
                        else 
                        {
                            txtNomBodOri.setText(strNomBodOri);
                        }
                    }
                    break;
            }
        } 
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarVenConBodDes(int intTipBus) 
    {
        boolean blnRes = true;
        String strSQLTmp="";
        try 
        {
            strSQLTmp+=" ) AS a1";
            strSQLTmp+=" LEFT OUTER JOIN(";
            strSQLTmp+=" 	SELECT a1.co_emp AS co_empEmp, a1.co_bod AS co_bodEmp, a1.co_empGrp, a1.co_bodGrp";
            strSQLTmp+=" 	FROM tbr_bodEmpBodGrp AS a1";
            strSQLTmp+=" 	WHERE a1.co_emp=" + intCodEmpPedIngImp + "";
            strSQLTmp+=") AS a2";
            strSQLTmp+=" ON a1.co_empGrp=a2.co_empGrp AND a1.co_bodGrp=a2.co_bodGrp";
            strSQLTmp+=" ORDER BY a1.co_empGrp, a1.co_bodGrp";
            vcoBodDes.setCondicionesSQL(strSQLTmp);

            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBodDes.setCampoBusqueda(2);
                    vcoBodDes.show();
                    if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) 
                    {
                        txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                        txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        txtCodBodDesEmp.setText(vcoBodDes.getValueAt(3));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoBodDes.buscar("a1.co_bodGrp", txtCodBodDes.getText()))
                    {
                        txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                        txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        txtCodBodDesEmp.setText(vcoBodDes.getValueAt(3));
                        objTblMod.removeAllRows();
                    } 
                    else 
                    {
                        vcoBodDes.setCampoBusqueda(0);
                        vcoBodDes.setCriterio1(11);
                        vcoBodDes.cargarDatos();
                        vcoBodDes.show();
                        if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) 
                        {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            txtCodBodDesEmp.setText(vcoBodDes.getValueAt(3));
                            objTblMod.removeAllRows();
                        } 
                        else 
                        {
                            txtCodBodDes.setText(strCodBodDes);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoBodDes.buscar("a1.tx_nomBodGrp", txtNomBodDes.getText())) 
                    {
                        txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                        txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        txtCodBodDesEmp.setText(vcoBodDes.getValueAt(3));
                        objTblMod.removeAllRows();
                    } 
                    else 
                    {
                        vcoBodDes.setCampoBusqueda(1);
                        vcoBodDes.setCriterio1(11);
                        vcoBodDes.cargarDatos();
                        vcoBodDes.show();
                        if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) 
                        {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            txtCodBodDesEmp.setText(vcoBodDes.getValueAt(3));
                            objTblMod.removeAllRows();
                        } 
                        else 
                        {
                            txtNomBodDes.setText(strNomBodDes);
                        }
                    }
                    break;
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
                if(objParSis.getCodigoUsuario()==1)
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a2.co_cfg";
                    strSQL+="       , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" INNER JOIN tbm_cfgTipDocUtiProAut AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocPrg";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="    AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+="    AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a2.co_cfg";
                    strSQL+="       , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" INNER JOIN tbm_cfgTipDocUtiProAut AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocUsr";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="    AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+="    AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="    AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                }
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodCfg.setText(rst.getString("co_cfg"));
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    //txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intCodCfgSolTra=Integer.parseInt(txtCodCfg.getText());
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
     * Esta función muestra el número del tipo de documento del importador del pedido elegido en el programa.
     * @return true: Si se pudo mostrar el número del tipo de documento del importador del pedido seleccionado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarNumTipDoc()
    {
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                //System.out.println("mostrarNumTipDoc: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
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
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodCfg = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblPedIngImp = new javax.swing.JLabel();
        txtCodPedIngImp = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        lblBodOri = new javax.swing.JLabel();
        txtCodBodOriEmp = new javax.swing.JTextField();
        txtCodBodOri = new javax.swing.JTextField();
        txtNomBodOri = new javax.swing.JTextField();
        butBodOri = new javax.swing.JButton();
        lblBodDes = new javax.swing.JLabel();
        txtCodBodDesEmp = new javax.swing.JTextField();
        txtCodBodDes = new javax.swing.JTextField();
        txtNomBodDes = new javax.swing.JTextField();
        butBodDes = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblPesTot = new javax.swing.JLabel();
        txtPesTotKgr = new javax.swing.JTextField();
        butCarItmPenTrs = new javax.swing.JButton();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

        jButton1.setText("jButton1");

        setClosable(true);
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

        panCon.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 116));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFil.setPreferredSize(new java.awt.Dimension(0, 130));
        panCabFil.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 115, 20);
        panCabFil.add(txtCodCfg);
        txtCodCfg.setBounds(100, 6, 10, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(110, 6, 10, 20);

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
        txtDesCorTipDoc.setBounds(120, 6, 55, 20);

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
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(175, 6, 230, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(405, 6, 20, 20);

        lblPedIngImp.setText("Pedido:");
        panCabFil.add(lblPedIngImp);
        lblPedIngImp.setBounds(4, 27, 90, 20);
        panCabFil.add(txtCodPedIngImp);
        txtCodPedIngImp.setBounds(100, 26, 20, 20);

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
        panCabFil.add(txtPedIngImp);
        txtPedIngImp.setBounds(120, 26, 285, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panCabFil.add(butPedImp);
        butPedImp.setBounds(405, 26, 20, 20);

        lblBodOri.setText("Bodega origen:");
        lblBodOri.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBodOri);
        lblBodOri.setBounds(4, 46, 110, 20);
        panCabFil.add(txtCodBodOriEmp);
        txtCodBodOriEmp.setBounds(100, 46, 20, 20);

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
        txtCodBodOri.setBounds(120, 46, 55, 20);

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
        txtNomBodOri.setBounds(175, 46, 230, 20);

        butBodOri.setText("...");
        butBodOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodOriActionPerformed(evt);
            }
        });
        panCabFil.add(butBodOri);
        butBodOri.setBounds(405, 46, 20, 20);

        lblBodDes.setText("Bodega destino:");
        lblBodDes.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBodDes);
        lblBodDes.setBounds(4, 66, 110, 20);
        panCabFil.add(txtCodBodDesEmp);
        txtCodBodDesEmp.setBounds(100, 66, 20, 20);

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
        txtCodBodDes.setBounds(120, 66, 55, 20);

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
        txtNomBodDes.setBounds(175, 66, 230, 20);

        butBodDes.setText("...");
        butBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodDesActionPerformed(evt);
            }
        });
        panCabFil.add(butBodDes);
        butBodDes.setBounds(405, 66, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabFil.add(lblFecDoc);
        lblFecDoc.setBounds(430, 6, 129, 20);

        lblNumDoc.setText("Número documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCabFil.add(lblNumDoc);
        lblNumDoc.setBounds(430, 27, 129, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        panCabFil.add(txtNumDoc);
        txtNumDoc.setBounds(558, 26, 120, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabFil.add(lblCodDoc);
        lblCodDoc.setBounds(430, 46, 129, 20);
        panCabFil.add(txtCodDoc);
        txtCodDoc.setBounds(558, 46, 120, 20);

        lblPesTot.setText("Peso total(Kg):");
        lblPesTot.setToolTipText("Código del documento");
        panCabFil.add(lblPesTot);
        lblPesTot.setBounds(430, 66, 129, 20);

        txtPesTotKgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPesTotKgr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesTotKgrFocusGained(evt);
            }
        });
        panCabFil.add(txtPesTotKgr);
        txtPesTotKgr.setBounds(558, 66, 120, 20);

        butCarItmPenTrs.setText("Cargar items");
        butCarItmPenTrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarItmPenTrsActionPerformed(evt);
            }
        });
        panCabFil.add(butCarItmPenTrs);
        butCarItmPenTrs.setBounds(570, 90, 100, 23);

        panGenCab.add(panCabFil, java.awt.BorderLayout.NORTH);

        panCon.add(panGenCab, java.awt.BorderLayout.NORTH);

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

        panCon.add(panGenDet, java.awt.BorderLayout.CENTER);

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

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 50));
        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodCfg.setText("");
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else 
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodCfg.setText("");
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
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
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                dispose();
            }
        } 
        catch (Exception e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void txtCodBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodDesActionPerformed
    txtCodBodDes.transferFocus();
}//GEN-LAST:event_txtCodBodDesActionPerformed

private void txtCodBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusGained
    strCodBodDes = txtCodBodDes.getText();
    txtCodBodDes.selectAll();
}//GEN-LAST:event_txtCodBodDesFocusGained

private void txtCodBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodBodDes.getText().equalsIgnoreCase(strCodBodDes)) {
        if (txtCodBodDes.getText().equals("")) {
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
            txtCodBodDesEmp.setText("");
        } else {
            mostrarVenConBodDes(1);
        }
    } else 
        txtCodBodDes.setText(strCodBodDes);
}//GEN-LAST:event_txtCodBodDesFocusLost

private void txtNomBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodDesActionPerformed
    txtNomBodDes.transferFocus();
}//GEN-LAST:event_txtNomBodDesActionPerformed

private void txtNomBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusGained
    strNomBodDes = txtNomBodDes.getText();
    txtNomBodDes.selectAll();
}//GEN-LAST:event_txtNomBodDesFocusGained

private void txtNomBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomBodDes.getText().equalsIgnoreCase(strNomBodDes)) {
        if (txtNomBodDes.getText().equals("")) {
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
            txtCodBodDesEmp.setText("");
        } else {
            mostrarVenConBodDes(2);
        }
    } else 
        txtNomBodDes.setText(strNomBodDes);
}//GEN-LAST:event_txtNomBodDesFocusLost

private void butBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodDesActionPerformed
    mostrarVenConBodDes(0);
}//GEN-LAST:event_butBodDesActionPerformed

    private void txtCodBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodOriActionPerformed
        txtCodBodOri.transferFocus();
    }//GEN-LAST:event_txtCodBodOriActionPerformed

    private void txtCodBodOriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOriFocusGained
        strCodBodOri = txtCodBodOri.getText();
        txtCodBodOri.selectAll();
    }//GEN-LAST:event_txtCodBodOriFocusGained

    private void txtCodBodOriFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOriFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodBodOri.getText().equalsIgnoreCase(strCodBodOri)) {
            if (txtCodBodOri.getText().equals("")) {
                txtCodBodOri.setText("");
                txtNomBodOri.setText("");
                txtCodBodOriEmp.setText("");
            } else {
                mostrarVenConBodOri(1);
            }
        } else 
            txtCodBodOri.setText(strCodBodOri);
    }//GEN-LAST:event_txtCodBodOriFocusLost

    private void txtNomBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodOriActionPerformed
        txtNomBodOri.transferFocus();
    }//GEN-LAST:event_txtNomBodOriActionPerformed

    private void txtNomBodOriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOriFocusGained
        strNomBodOri = txtNomBodOri.getText();
        txtNomBodOri.selectAll();
    }//GEN-LAST:event_txtNomBodOriFocusGained

    private void txtNomBodOriFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOriFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomBodOri.getText().equalsIgnoreCase(strNomBodOri)) {
            if (txtNomBodOri.getText().equals("")) {
                txtCodBodOri.setText("");
                txtNomBodOri.setText("");
                txtCodBodOriEmp.setText("");
            } else {
                mostrarVenConBodOri(2);
            }
        } else 
            txtNomBodOri.setText(strNomBodOri);
    }//GEN-LAST:event_txtNomBodOriFocusLost

    private void butBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodOriActionPerformed
        mostrarVenConBodOri(0);
    }//GEN-LAST:event_butBodOriActionPerformed

    private void butCarItmPenTrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarItmPenTrsActionPerformed
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());    
            if(con!=null){
                if (isCamVal(1)){
                    if(cargarItmPenTrs()){ //informacion general del item
                        //if(eliminaItmNotTieCanPen()){
                            objTblMod.initRowsState();
                        //} else   objTblMod.removeAllRows();
                    }
                    else 
                        objTblMod.removeAllRows();
                }
                else  
                    objTblMod.removeAllRows();
                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butCarItmPenTrsActionPerformed

    private void txtPesTotKgrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesTotKgrFocusGained
        strPesTotKgrSolTra = txtPesTotKgr.getText();
    }//GEN-LAST:event_txtPesTotKgrFocusGained

    private void txtPedIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPedIngImpActionPerformed
        txtPedIngImp.transferFocus();
    }//GEN-LAST:event_txtPedIngImpActionPerformed

    private void txtPedIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusGained
        strPedIngImp=txtPedIngImp.getText();
        txtPedIngImp.selectAll();
    }//GEN-LAST:event_txtPedIngImpFocusGained

    private void txtPedIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtPedIngImp.getText().equalsIgnoreCase(strPedIngImp))
        {
            if (txtPedIngImp.getText().equals(""))
            {
                txtCodPedIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                if(isCamVal(2))
                { 
                    configurarPedidosIngImp();
                    mostrarPedidos(2);
                }
                else
                {
                    txtCodPedIngImp.setText("");
                    txtPedIngImp.setText("");
                }
            }
        }
        else
            txtPedIngImp.setText(strPedIngImp);

    }//GEN-LAST:event_txtPedIngImpFocusLost

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        if(isCamVal(2))
        { 
            configurarPedidosIngImp();
            mostrarPedidos(0);
        }
    }//GEN-LAST:event_butPedImpActionPerformed


    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBodDes;
    private javax.swing.JButton butBodOri;
    private javax.swing.JButton butCarItmPenTrs;
    private javax.swing.JButton butPedImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblBodDes;
    private javax.swing.JLabel lblBodOri;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPedIngImp;
    private javax.swing.JLabel lblPesTot;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBodDes;
    private javax.swing.JTextField txtCodBodDesEmp;
    private javax.swing.JTextField txtCodBodOri;
    private javax.swing.JTextField txtCodBodOriEmp;
    private javax.swing.JTextField txtCodCfg;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodPedIngImp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBodDes;
    private javax.swing.JTextField txtNomBodOri;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtPedIngImp;
    private javax.swing.JTextField txtPesTotKgr;
    // End of variables declaration//GEN-END:variables


    /**
     * Esta función muestra un mensaje informativo al usuario. Se podróa utilizar
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podróa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifóquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    } 

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algón cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentaró un mensaje advirtiendo que si no guarda los cambios los perderó.
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
        txtCodCfg.getDocument().addDocumentListener(objDocLis);
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodPedIngImp.getDocument().addDocumentListener(objDocLis);
        txtPedIngImp.getDocument().addDocumentListener(objDocLis);
        txtCodBodOri.getDocument().addDocumentListener(objDocLis);
        txtNomBodOri.getDocument().addDocumentListener(objDocLis);
        txtCodBodDes.getDocument().addDocumentListener(objDocLis);
        txtNomBodDes.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtPesTotKgr.getDocument().addDocumentListener(objDocLis);
    }

    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningón problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodCfg.setText("");
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodPedIngImp.setText("");
            txtPedIngImp.setText("");
            txtCodBodOri.setText("");
            txtNomBodOri.setText("");
            txtCodBodOriEmp.setText("");
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
            txtCodBodDesEmp.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            objTblMod.removeAllRows();
            intCodEmpPedIngImp=0;
            intCodLocPedIngImp=0;
            intCodTipDocPedIngImp=0;
            intCodDocPedIngImp=0;
            intCodCfgSolTra=0;
            txtNumDoc.setEnabled(false);
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
            txtPesTotKgr.setEnabled(false);
            txtPesTotKgr.setText("");
            txtCodBodOri.setEnabled(false);
            txtNomBodOri.setEnabled(false);
            txtCodBodDes.setEnabled(false);
            txtNomBodDes.setEnabled(false);
            butBodOri.setEnabled(false);
            butBodDes.setEnabled(false);
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacóa.
     */
    private String getEstReg(String estado){
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
    

    private class MiToolBar extends ZafToolBar 
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
            super(ifrFrm, objParSis);
        }
        
        public void clickInicio() {
            try{
                if(arlDatConSol.size()>0){
                    if(intIndiceSolTra>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra=0;
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
                if(arlDatConSol.size()>0){
                    if(intIndiceSolTra>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra--;
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
                 if(arlDatConSol.size()>0){
                    if(intIndiceSolTra < arlDatConSol.size()-1){
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                intIndiceSolTra++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra++;
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
                 if(arlDatConSol.size()>0){
                    if(intIndiceSolTra<arlDatConSol.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra=arlDatConSol.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra=arlDatConSol.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }  

        public void clickConsultar() {
            mostrarTipDocPre();
        }

        public void clickInsertar() {
            try{
                if (blnHayCam){
                    isRegPro();
                }
                limpiarFrm();
                opcionesClickInsertar();
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar() {            
        }
        
        public void clickAnular() {
            cargarDetReg();
        }
        
        public void clickEliminar() {
        }
        public void clickVisPreliminar() {
        }
        public void clickImprimir() {
        }        
        public void clickAceptar() {
        }
        public void clickCancelar() {
        }        
        
        public boolean consultar() {
            consultarReg();
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

        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        
        public boolean eliminar() {
            return true;
        }

        public boolean vistaPreliminar() {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }
        
        public boolean imprimir() {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }     
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean cancelar() {
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

        
        public boolean beforeConsultar() {
            return true;
        }
        
        public boolean beforeInsertar() {
            if (!isCamVal(0)) 
                return false;             
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }
        public boolean beforeAnular() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))  {
                mostrarMsgInf("El documento estó ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))   {
                mostrarMsgInf("El documento ya estó ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            if (!isCamVal(0))
                blnRes=false;
            
            return blnRes;
        }
        
        public boolean beforeEliminar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))  {
                mostrarMsgInf("El documento ya esta ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                blnRes=false;
            }
            
            if (!isCamVal(0))
                blnRes=false;
            
            return blnRes;
        }     
        
        public boolean beforeVistaPreliminar() {
            return true;
        }
        public boolean beforeImprimir() {
            return true;
        }
        public boolean beforeAceptar() {
            return true;
        }
        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterInsertar() 
        {
            objTooBar.setEstado('w');
            if(!consultarReg()){
                mostrarMsgInf("No se han encontrado registros.");
            }
            if(!imprimirDocumento()){
                mostrarMsgInf("La impresión de la(s) solicitud(es) no ha sido realizada.");
            }
     
            blnHayCam=false;  
            return true;
        }

        public boolean afterModificar() {
            return true;
        }        
        public boolean afterAnular() {
            return true;
        }
        public boolean afterEliminar() {
            return true;
        }
        public boolean afterVistaPreliminar() {            
            return true;
        }
        public boolean afterImprimir() {
            return true;
        }        
        public boolean afterAceptar() {
            return true;
        }        
        public boolean afterCancelar() {
            return true;
        }
 
        
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @param intTipVal Indica tipo de validación. 0=Validacion Normal. 1=Validacion Boton Cargar Items. 2=Validacion Consulta Pedidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(int intTipVal) 
    {
        //Validar siempre. (Especialmente antes de consultar la ventana pedidos)
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("") || intCodCfgSolTra==0) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            limpiarFrm();
            return false;
        }
        
        //Validar antes de cargar items y antes de insertar.
        if(intTipVal!=2)
        {
            if( (intCodEmpPedIngImp==0)||(intCodLocPedIngImp==0)||(intCodTipDocPedIngImp==0)||(intCodDocPedIngImp==0)){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Pedido</FONT> es obligatorio.<BR>Escriba un número de pedido y vuelva a intentarlo.</HTML>");
                txtPedIngImp.requestFocus();
                return false;
            }
            
            //Valida que pedido no haya sido cerrado.
            if(!validaPedido()){
                mostrarMsgInf("<HTML>El pedido <FONT COLOR=\"blue\">"+txtPedIngImp.getText()+" </FONT> ha sido cerrado.<BR>No se puede procesar ninguna solicitud de este pedido.</HTML>");
                limpiarFrm();
                if(objTooBar.getEstado()=='n'){
                    opcionesClickInsertar();
                }
                return false;
            }

            //Validar "Bodega Origen".
            if (txtCodBodOri.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega Origen</FONT> es obligatorio.<BR>Escriba o seleccione una bodega origen y vuelva a intentarlo.</HTML>");
                txtCodBodOri.requestFocus();
                return false;
            }

            //Validar "Bodega Destino".
            if (txtCodBodDes.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega Destino</FONT> es obligatorio.<BR>Escriba o seleccione una bodega destino y vuelva a intentarlo.</HTML>");
                txtCodBodDes.requestFocus();
                return false;
            }
        }
        
        //Validar antes de insertar.
        if(intTipVal==0)
        {
            //Validar el "Fecha del documento".
            if (!dtpFecDoc.isFecha()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
                dtpFecDoc.requestFocus();
                return false;
            }

            if (txtNumDoc.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNomBodDes.requestFocus();
                return false;
            }
    
            //<editor-fold defaultstate="collapsed" desc="/* Valida que la cantidad este autorizada.*/">
            BigDecimal bgdCanAut=new BigDecimal("0");
            BigDecimal bgdCanTrsOk=new BigDecimal("0");
            BigDecimal bgdCanUsrSolTrs=new BigDecimal("0");
            BigDecimal bgdCanPenBodDifInm=new BigDecimal("0");

            for(int i=0; i<objTblMod.getRowCountTrue(); i++)
            {
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK))
                {
                    if(actualizaCantidadesBD(i))
                    {
                        bgdCanAut=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AUT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AUT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AUT).toString()));
                        bgdCanTrsOk=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TRA_OK)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TRA_OK).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_TRA_OK).toString()));
                        bgdCanUsrSolTrs=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_USR_SOLTRA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_USR_SOLTRA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_USR_SOLTRA).toString()));
                        bgdCanPenBodDifInm=bgdCanAut.subtract(bgdCanTrsOk);

                        //System.out.println("bgdCanPenBodDifInm: "+bgdCanPenBodDifInm+" <> bgdCanTrsOk: "+bgdCanTrsOk+" <> bgdCanUsrSolTrs: "+bgdCanUsrSolTrs+" <> bgdCanAut: "+bgdCanAut);

                        //Cuando la cantidad que se esta ingresando es mayor a la autorizada.
                        if(bgdCanPenBodDifInm.compareTo(bgdCanUsrSolTrs)<0)
                        {
                            tabFrm.setSelectedIndex(0);
                            mostrarMsgInf("<HTML>La cantidad que se desea solicitar transferir no ha sido autorizada en la bodega seleccionada.<BR>Seleccione la Bodega Centro de Distribución o autorice la cantidad en la Bodega Especificada.</HTML>");
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            //</editor-fold>

            //Valida que exista detalle antes de insertar solicitud
            if (objTblMod.getRowCountTrue()==0 || objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK)==false)  
            {               
                mostrarMsgInf("No es posible realizar solicitud de transferencia.\nDebe tener items a solicitar transferencia.");                 
                return false;
            } 
        }
        
        return true;
    }
    
    private boolean opcionesClickInsertar()
    {
        boolean blnRes=true;
        try
        {
            txtCodDoc.setEditable(false);
            txtCodBodOri.setEnabled(false);
            txtNomBodOri.setEnabled(false);
            txtCodBodDes.setEnabled(false);
            txtNomBodDes.setEnabled(false);
            butBodOri.setEnabled(false);
            butBodDes.setEnabled(false);

            datFecAux=null;
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

            mostrarTipDocPre();

            butCarItmPenTrs.setEnabled(true); 
            
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
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
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc, a.tx_numDoc2, a1.fe_Doc";
                strSQL+="      , a1.co_bodOrg, a1.co_bodDes, a1.nd_pesTotKgr, a1.tx_obs1, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel "; 
                strSQL+=" FROM tbm_CabMovInv as a";
                strSQL+=" INNER JOIN"; 
                strSQL+=" (";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.co_bodOrg, a1.co_bodDes, a1.fe_doc, a1.nd_pesTotKgr, a1.tx_obs1";       
                strSQL+=" 	      ,a.co_EmpRelCabMovInv as co_empRel, a.co_locRelCabMovInv as co_locRel, a.co_tipDocRelCabMovInv as co_tipDocRel, a.co_docRelCabMovInv as co_docRel";
                strSQL+=" 	FROM tbr_cabSolTraInvCabMovInv as a";
                strSQL+=" 	INNER JOIN tbm_CabSolTraInv as a1 ON (a1.co_emp=a.co_EmpRelCabSolTraInv AND a1.co_loc=a.co_locRelCabSolTraInv AND a1.co_tipDoc=a.co_tipDocRelCabSolTraInv AND a1.co_Doc=a.co_DocRelCabSolTraInv )";
                strSQL+=" 	WHERE a.co_tipDocRelCabMovInv NOT IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="                           and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";
                strSQL+=" 	UNION ALL";
                //Trae las solicitudes de los documentos de ajustes.
                strSQL+=" 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numDoc, a2.co_bodOrg, a2.co_bodDes, a2.fe_doc, a2.nd_pesTotKgr, a2.tx_obs1";
                strSQL+="             ,a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel";
                strSQL+=" 	FROM tbr_CabMovInv as a";
                strSQL+=" 	INNER JOIN tbr_cabSolTraInvCabMovInv as a1 ON (a1.co_empRelCabMovInv=a.co_Emp AND a1.co_locRelCabMovInv=a.co_loc AND a1.co_TipDocRelCabMovInv=a.co_tipDoc AND a1.co_DocRelCabMovInv=a.co_doc)";
                strSQL+=" 	INNER JOIN tbm_CabSolTraInv as a2 ON (a2.co_emp=a1.co_EmpRelCabSolTraInv AND a2.co_loc=a1.co_locRelCabSolTraInv AND a2.co_tipDoc=a1.co_tipDocRelCabSolTraInv AND a2.co_Doc=a1.co_DocRelCabSolTraInv )";
                strSQL+=" ) as a1 ON a1.co_EmpRel=a.co_emp AND a1.co_locRel=a.co_loc AND a1.co_tipDocRel=a.co_tipDoc AND a1.co_docRel=a.co_Doc";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a2.co_emp=a1.co_Emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc)";

                if(!blnVenConEme)  {
                    if(objParSis.getCodigoUsuario()==1){ //Validar que sólo se muestre los documentos asignados al programa.
                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc AND a3.co_mnu=" + objParSis.getCodigoMenu() + " )";    
                    }
                    else{
                        strSQL+=" INNER JOIN tbr_tipDocUsr AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc AND a3.co_mnu=" + objParSis.getCodigoMenu() + " AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";                         
                    }
                }

                strSQL+=" WHERE a.st_reg<>'E'";

                if(objTooBar.getEstado()!='n')  {
                    if (txtCodTipDoc.getText().length()>0)
                        strSQL+=" AND a1.co_tipDoc = " + txtCodTipDoc.getText().replaceAll("'", "''") + "";     

                    if (txtCodDoc.getText().length()>0)
                        strSQL+=" AND a1.co_doc = " + txtCodDoc.getText().replaceAll("'", "''") + "";  

                    if (txtNumDoc.getText().length()>0)
                        strSQL+=" AND a1.ne_numDoc = " + txtNumDoc.getText().replaceAll("'", "''") + "";

                    if (dtpFecDoc.isFecha())
                        strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                else  { //Solo consulte las solicitudes que se han generado.
                    strAux ="";
                    for(int i=0; i<arlDatSolImp.size(); i++)
                    {
                        if(i==0) {   strAux+=" AND (";  }  else {   strAux+=" OR ";  }
                        strAux+="(";
                        strAux+=" a1.co_emp = "+objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_EMP);
                        strAux+=" AND a1.co_loc = "+objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_LOC);
                        strAux+=" AND a1.co_tipDoc = "+objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_TIPDOC);
                        strAux+=" AND a1.co_Doc = "+objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_DOC);
                        strAux+=")";
                        if(i==arlDatSolImp.size()-1){    strAux+=")";     }
                    }
                    strSQL+=strAux;
                }
               
                //se selecciono un pedido especifico
                if(intCodEmpPedIngImp!=0){
                    strSQL+=" AND a1.co_empRel IN(" + intCodEmpPedIngImp + ")";//se filtra la empresa
                    strSQL+=" AND a1.co_locRel IN(" + intCodLocPedIngImp + ")";//se filtra el local
                    strSQL+=" AND a1.co_tipDocRel IN(" + intCodTipDocPedIngImp + ")";//se filtra el tipo de documento
                    strSQL+=" AND a1.co_docRel IN(" + intCodDocPedIngImp + ")";//se filtra el documento
                }
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc, a.tx_numDoc2, a1.fe_Doc";
                strSQL+="        , a1.co_bodOrg, a1.co_bodDes, a1.nd_pesTotKgr, a1.tx_obs1, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel "; 
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc";
                
                //System.out.println("consultarReg:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConSol = new ArrayList();
                while(rst.next()){
                    arlRegConSol = new ArrayList();
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_EMP,    rst.getInt("co_emp"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_LOC,    rst.getInt("co_loc"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_TIPDOC, rst.getInt("co_tipDoc"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_DOC,    rst.getInt("co_doc"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_NUM_PED,    rst.getString("tx_numDoc2"));
                    arlDatConSol.add(arlRegConSol);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConSol.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConSol.size()) + " registros");
                    intIndiceSolTra=arlDatConSol.size()-1;
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
            if (cargarCabReg()){
                if (cargarDetReg()){
                    blnRes = true;
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
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.nd_pesTotKgr ";
                strSQL+="       ,CAST('"+objUti.getStringValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_NUM_PED)+"' AS CHARACTER VARYING) as tx_numDoc2";  
                strSQL+="       ,a1.tx_obs1, a1.tx_obs2, a1.co_bodOrg, a4.tx_nom as tx_nomBodOrg, a1.co_bodDes, a5.tx_nom as tx_nomBodDes, a1.st_Reg";
                strSQL+=" FROM tbm_CabSolTraInv as a1 ";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a2.co_emp=a1.co_Emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc)";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_bod AS a4 ON (a4.co_emp=a1.co_emp AND a4.co_bod=a1.co_bodOrg)";
                strSQL+=" INNER JOIN tbm_bod AS a5 ON (a5.co_emp=a1.co_emp AND a5.co_bod=a1.co_bodDes)"; 
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC);
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                strSQL+="        , a1.nd_pesTotKgr, a1.tx_obs1, a1.tx_obs2, a1.co_bodOrg, a4.tx_nom, a1.co_bodDes, a5.tx_nom, a1.st_Reg"; 
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc";
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
                    
                    strAux=rst.getString("co_doc");
                    txtCodPedIngImp.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_numDoc2");
                    txtPedIngImp.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_bodDes");
                    txtCodBodDes.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_nomBodDes");
                    txtNomBodDes.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_bodOrg");
                    txtCodBodOri.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_nomBodOrg");
                    txtNomBodOri.setText((strAux==null)?"":strAux); 
                    
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getObject("nd_pesTotKgr")==null?"0":(rst.getString("nd_pesTotKgr").equals("")?"0":rst.getString("nd_pesTotKgr"));
                    txtPesTotKgr.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("I"))
                        strAux="Anulado";
                    else if (strAux.equals("E"))
                        strAux="Eliminado";
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
            rst=null;
            stm.close();
            stm=null;
            con.close();
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceSolTra+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConSol.size()) );
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
            objTblMod.removeAllRows();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.co_itm, a2.tx_codAlt, a2.tx_codAlt2 ";
                strSQL+="      , a2.tx_nomItm, a1.nd_can, CASE WHEN a3.tx_desCor IS NULL THEN '' ELSE a3.tx_desCor END as tx_uniMed, a1.nd_pesUniKgr, a1.nd_pesTotKgr ";                    
                strSQL+=" FROM tbm_DetSolTraInv as a1 ";
                strSQL+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) ";
                strSQL+=" LEFT OUTER JOIN tbm_var as a3 ON (a3.co_reg=a2.co_uni) ";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_EMP) + "";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_LOC) + "";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC) + "";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC) + "";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.co_itm, a2.tx_codAlt, a2.tx_codAlt2";
                strSQL+="        , a2.tx_nomItm, a1.nd_can, a3.tx_desCor, a1.nd_pesUniKgr, a1.nd_pesTotKgr";
                strSQL+=" ORDER BY a1.co_Reg";
                //System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,"" + rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,"" + rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP,"" + rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,"" + rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,"" + rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_GRP,"");
                    vecReg.add(INT_TBL_DAT_COD_ITM_EMP,"" + rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,"");
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,"" + rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_ALTDOS,"" + rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_NOM_ITM,"" + rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,"" + rst.getString("tx_uniMed"));
                    vecReg.add(INT_TBL_DAT_CAN_ING_IMP,"");
                    vecReg.add(INT_TBL_DAT_CAN_AUT,"");                    
                    vecReg.add(INT_TBL_DAT_CAN_CON,"");
                    vecReg.add(INT_TBL_DAT_CAN_UTI,"");
                    vecReg.add(INT_TBL_DAT_CAN_TRA_OK,"");
                    vecReg.add(INT_TBL_DAT_CAN_PEN,"");
                    vecReg.add(INT_TBL_DAT_CAN_USR_SOLTRA,"" + rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_COS_UNI,"");
                    vecReg.add(INT_TBL_DAT_COS_TOT,"");
                    vecReg.add(INT_TBL_DAT_PES_UNI,"" + rst.getString("nd_pesUniKgr"));
                    vecReg.add(INT_TBL_DAT_PES_TOT,"" + rst.getString("nd_pesTotKgr"));
                    vecReg.add(INT_TBL_DAT_EST_ITM_EXI_ING_IMP,"");
                    vecReg.add(INT_TBL_DAT_EST_ITM_TIE_DOC_AJU,"");
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
   
    private boolean cargarItmPenTrs()
    {
        boolean blnRes=true;
        BigDecimal bgdCanIngImp=new BigDecimal("0");
        BigDecimal bgdCanAut=new BigDecimal("0");
        BigDecimal bgdCanCon=new BigDecimal("0");
        BigDecimal bgdCanTrs=new BigDecimal("0");  //bgdCanUti
        BigDecimal bgdCanPen=new BigDecimal("0");
        try{            
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT * FROM (";
                strSQL+="  SELECT a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_regIngImp, a1.co_itmEmp, a1.co_itmMae, a2.co_itmGrp\n";
                strSQL+="       , a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosUni, a2.nd_pesitmkgr, a1.st_regExiItmIngImp, a1.nd_canIngImp\n";
                strSQL+="       , CASE WHEN a1.nd_canAut IS NULL THEN 0 ELSE a1.nd_canAut END AS nd_canAut";
                strSQL+="       , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                strSQL+="       , CASE WHEN a1.nd_canTrs IS NULL THEN 0 ELSE a1.nd_canTrs END AS nd_canTrs";
                strSQL+="       , a1.nd_canUtiOrdDis, a1.st_IsDocAju\n"; 
                strSQL+="  FROM(	\n";
                strSQL+=" 	SELECT a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_regIngImp, a1.co_itmEmp, a1.co_itmMae\n";	      
                strSQL+="            , a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosUni, a1.st_regExiItmIngImp, a1.nd_canIngImp, a3.nd_canAut, a4.nd_canCon\n";
                strSQL+="            , (CASE WHEN a2.nd_canTrs IS NULL THEN 0 ELSE a2.nd_canTrs END ) AS nd_canTrs, a1.nd_canUtiOrdDis, CAST('N' AS CHARACTER VARYING) AS st_IsDocAju\n";  
                strSQL+=" 	FROM(  /* INIMPO */ \n"; 
                strSQL+="             SELECT a1.co_emp AS co_empIngImp, a1.co_loc AS co_locIngImp, a1.co_tipDoc AS co_tipDocIngImp, a1.co_doc AS co_docIngImp\n";		      
                strSQL+="                  , a2.co_reg AS co_regIngImp, a2.co_itm AS co_itmEmp, a4.co_itmMae, a2.tx_codAlt, a3.tx_codAlt2, a2.tx_nomItm, a2.tx_unimed\n";		      
                strSQL+="                  , a2.nd_can AS nd_canIngImp, a2.nd_cosUni, CAST('S' AS CHARACTER VARYING) AS st_regExiItmIngImp, a2.nd_canUtiOrdDis\n"; 			      
                strSQL+="             FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n"; 		
                strSQL+="             INNER JOIN tbm_inv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)\n"; 		
                strSQL+="             INNER JOIN tbm_equInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)\n"; 
                strSQL+="             WHERE (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END ) NOT IN ('N', 'P') "; //Valida que no presente pedidos cerrados o pendientes de arribo.
                strSQL+="             AND a1.co_emp=" + intCodEmpPedIngImp + " AND a1.co_loc=" + intCodLocPedIngImp + "";
                strSQL+=" 	      AND a1.co_tipDoc=" + intCodTipDocPedIngImp + " AND a1.co_doc=" + intCodDocPedIngImp + "\n";
                strSQL+=" 	) AS a1	\n";
                strSQL+=" 	LEFT OUTER JOIN(  /* TRANSFERENCIAS */ \n"; 
                strSQL+="             SELECT a4.co_itmMae, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel, ABS(SUM(a2.nd_can)) AS nd_canTrs, a6.co_bodGrp\n";	      
                strSQL+="             FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1 ON (a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc) )\n";		
                strSQL+="             INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n"; 		
                strSQL+="             INNER JOIN tbm_equInv AS a4 ON (a1.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) \n";		
                strSQL+="             INNER JOIN tbr_bodEmpBodGrp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_bod=a6.co_bod AND a6.co_bodGrp=" + txtCodBodDes.getText()+") \n";	
                strSQL+="             WHERE a2.nd_can>0 AND a1.st_reg='A' \n";
                strSQL+="             AND a5.co_empRel=" + intCodEmpPedIngImp + " AND a5.co_locRel=" + intCodLocPedIngImp ;
                strSQL+="             AND a5.co_tipdocRel=" + intCodTipDocPedIngImp + " AND a5.co_docRel=" + intCodDocPedIngImp +"\n";
                strSQL+="             GROUP BY a4.co_itmMae, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel, a6.co_bodGrp\n";		      
                strSQL+=" 	) AS a2\n";
                strSQL+=" 	ON a1.co_empIngImp=a2.co_empRel AND a1.co_locIngImp=a2.co_locRel AND a1.co_tipDocIngImp=a2.co_tipDocRel AND a1.co_docIngImp=a2.co_docRel AND a1.co_itmMae=a2.co_itmMae\n";
                strSQL+=" 	LEFT OUTER JOIN( /* CANTIDAD AUTORIZADA */ \n"; 
                strSQL+="             SELECT a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a2.co_regRel, a2.nd_can AS nd_canAut \n";	
                strSQL+="             FROM tbm_cabOrdDis AS a1 INNER JOIN tbm_detOrdDis AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_bod=" + txtCodBodDes.getText() + ") \n"; 		
                strSQL+="             WHERE a1.st_reg='A' \n";	
                strSQL+="             AND a2.co_empRel=" + intCodEmpPedIngImp + " AND a2.co_locRel=" + intCodLocPedIngImp ;
                strSQL+="             AND a2.co_tipdocRel=" + intCodTipDocPedIngImp + " AND a2.co_docRel=" + intCodDocPedIngImp+"\n" ;
                strSQL+=" 	) AS a3 \n";
                strSQL+=" 	ON a1.co_empIngImp=a3.co_empRel AND a1.co_locIngImp=a3.co_locRel AND a1.co_tipDocIngImp=a3.co_tipDocRel AND a1.co_docIngImp=a3.co_docRel AND a1.co_regIngImp=a3.co_regRel\n";	
                strSQL+=" 	LEFT OUTER JOIN( /* CONTEOS */ \n"; 
                strSQL+="             SELECT a4.co_itmMae, a5.nd_stkcon AS nd_canCon, a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel \n";			      
                strSQL+="             FROM tbm_cabOrdConInv AS a1 \n";
                strSQL+="             INNER JOIN ( tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_locRel\n";
                strSQL+="                          AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel AND a2.co_reg=a5.co_regRel) \n";							
                strSQL+="             ) ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";		
                strSQL+="             INNER JOIN tbm_equInv AS a4 ON (a1.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) \n";
                strSQL+="             WHERE a1.co_empRel=" + intCodEmpPedIngImp + " AND a1.co_locRel=" + intCodLocPedIngImp ;
                strSQL+="             AND a1.co_tipdocRel=" + intCodTipDocPedIngImp + " AND a1.co_docRel=" + intCodDocPedIngImp +"\n";
                strSQL+=" 	) AS a4 \n";
                strSQL+=" 	ON a1.co_empIngImp=a4.co_empRel AND a1.co_locIngImp=a4.co_locRel AND a1.co_tipDocIngImp=a4.co_tipDocRel AND a1.co_docIngImp=a4.co_docRel AND a1.co_itmMae=a4.co_itmMae \n";	
                strSQL+=" 	GROUP BY a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_regIngImp \n";	        
                strSQL+="              , a1.co_itmEmp, a1.tx_codAlt, a1.tx_unimed, a1.tx_codAlt2, a1.tx_nomItm, a1.co_itmMae, a1.nd_canIngImp \n";	        
                strSQL+="              , a1.nd_cosUni, a1.st_regExiItmIngImp, a2.nd_canTrs, a1.nd_canUtiOrdDis, a4.nd_canCon, a3.nd_canAut \n";  
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                strSQL+="       UNION  /* ITEMS CONTADOS QUE NO ESTAN EN INIMPO  */ \n"; 
                strSQL+=" 	SELECT a1.co_empRel AS co_empIngImp, a1.co_locRel AS co_locIngImp, a1.co_tipDocRel AS co_tipDocIngImp, a1.co_docRel AS co_docIngImp\n";
                strSQL+=" 	     , a2.co_reg AS co_regIngImp, a2.co_itm AS co_itmEmp, a4.co_itmMae, a3.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, '' AS tx_unimed\n";
                strSQL+=" 	     , a3.nd_cosUni, 'N' AS st_regExiItmIngImp, 0 AS nd_canIngImp, NULL AS nd_canAut, a5.nd_stkcon AS nd_canCon\n";
                strSQL+=" 	     , NULL AS nd_canTrs, NULL AS nd_canUtiOrdDis, CAST('N' AS CHARACTER VARYING) AS  st_IsDocAju\n";            
                strSQL+=" 	FROM tbm_cabOrdConInv AS a1 INNER JOIN tbm_detOrdConInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n";       
                strSQL+=" 	INNER JOIN tbm_conInv AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_locRel AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel AND a2.co_reg=a5.co_regRel)\n";       
                strSQL+=" 	INNER JOIN tbm_inv AS a3 ON (a5.co_emp=a3.co_emp AND a5.co_itm=a3.co_itm) \n";      
                strSQL+=" 	INNER JOIN tbm_equInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)\n";       
                strSQL+=" 	WHERE a5.fe_solCon IS NULL \n";
                strSQL+="       AND a1.co_empRel=" + intCodEmpPedIngImp + " AND a1.co_locRel=" + intCodLocPedIngImp + "";
                strSQL+="       AND a1.co_tipDocRel=" + intCodTipDocPedIngImp + " AND a1.co_docRel=" + intCodDocPedIngImp + "\n";
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                strSQL+=" UNION  /* ITEMS CON AJUSTES */\n"; 
                strSQL+=" 	SELECT a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_regIngImp, a1.co_itmEmp, a1.co_itmMae\n"; 
                strSQL+=" 	     , a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosUni, 'N' AS st_regExiItmIngImp, a1.nd_canIngImp";
                strSQL+="            , CASE WHEN a3.nd_canAut IS NULL THEN 0 ELSE a3.nd_canAut END AS nd_canAut"; 
                strSQL+="            , CASE WHEN a1.nd_canCon IS NULL THEN 0 ELSE a1.nd_canCon END AS nd_canCon";
                strSQL+="            , CASE WHEN a2.nd_canTrs IS NULL THEN 0 ELSE a2.nd_canTrs END AS nd_canTrs";
                strSQL+=" 	     , a1.nd_canUtiOrdDis, a1.st_IsDocAju\n"; 
                strSQL+=" 	FROM (	/* DOCUMENTO AJUSTE */\n"; 
                strSQL+=" 	    SELECT a1.co_emp AS co_empIngImp, a1.co_loc AS co_locIngImp, a1.co_tipdoc AS co_tipDocIngImp, a1.co_doc AS co_docIngImp, a2.co_reg AS co_regIngImp\n";
                strSQL+=" 		 , a2.co_itm AS co_itmEmp, a3.co_itmMae, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_unimed, a2.nd_can AS nd_canIngImp, a2.nd_can as nd_canAut, a2.nd_can as nd_canCon\n";
                strSQL+=" 		 , a2.nd_cosUni, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel, cast('S' as character varying) as st_IsDocAju,0 as nd_canUtiOrdDis\n";    		 	
                strSQL+=" 	    FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1 ON (a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc) ) \n";
                strSQL+=" 	    INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n"; 	
                strSQL+=" 	    INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)\n";	
                strSQL+=" 	    WHERE a1.st_reg='A' AND a2.nd_can>0 \n"; 
                strSQL+="           AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+=" 	    AND a1.co_tipDoc IN ( select co_tipDoc from tbr_tipDocPrg WHERE co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" 	                          and co_loc="+objParSis.getCodigoLocal()+" and co_mnu ="+objImp.INT_COD_MNU_PRG_AJU_INV+")";
                strSQL+=" 	    AND (CASE WHEN a1.st_ingImp IS NULL THEN 'N' ELSE a1.st_ingImp END ) IN ('T') ";                 
                strSQL+=" 	    AND a1.st_aut IN ('A') ";
                strSQL+=" 	    AND a5.co_empRel=" + intCodEmpPedIngImp + " AND a5.co_locRel=" + intCodLocPedIngImp + "";
                strSQL+=" 	    AND a5.co_tipDocRel=" + intCodTipDocPedIngImp + " AND a5.co_docRel=" + intCodDocPedIngImp + "\n";
                strSQL+=" 	) AS a1	\n "; 
                strSQL+=" 	LEFT OUTER JOIN( /* TRANSFERENCIAS AJUSTE */ \n"; 
                strSQL+=" 	    SELECT a4.co_itmMae, a2.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed \n"; 
                strSQL+=" 		 , a5.co_empRel AS co_empAju, a5.co_locRel AS co_locAju, a5.co_tipdocRel AS co_tipDocAju, a5.co_docRel AS co_docAju \n"; 
                strSQL+=" 		 , ABS(SUM(CASE WHEN a2.nd_can IS NULL THEN 0 ELSE a2.nd_can END )) AS nd_canTrs\n";
                strSQL+=" 	    FROM ( tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1 ON (a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc) ) \n"; 
                strSQL+=" 	    INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n"; 
                strSQL+=" 	    INNER JOIN tbr_cabMovInv as a6 ON (a5.co_empRel=a6.co_emp and a5.co_locRel=a6.co_loc and a5.co_tipDocRel=a6.co_tipDoc and a5.co_docRel=a6.co_Doc) \n"; 
                strSQL+=" 	    INNER JOIN (tbm_inv AS a3  INNER JOIN tbm_equInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm) )ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n"; 
                strSQL+="           INNER JOIN tbr_bodEmpBodGrp as a7 ON (a2.co_emp=a7.co_emp AND a2.co_bod=a7.co_bod) \n";	
                strSQL+=" 	    WHERE a2.nd_can<0 AND a1.st_reg='A' \n"; 
                strSQL+=" 	    AND a6.co_empRel=" + intCodEmpPedIngImp + " AND a6.co_locRel=" + intCodLocPedIngImp + "";
                strSQL+=" 	    AND a6.co_tipDocRel=" + intCodTipDocPedIngImp + " AND a6.co_docRel=" + intCodDocPedIngImp + "\n";
                strSQL+="           AND a7.co_bodGrp=" + txtCodBodDes.getText() ;
                strSQL+="           GROUP BY a4.co_itmMae, a2.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed \n"; 
                strSQL+=" 		   , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel \n"; 
                strSQL+=" 	) AS a2 ON a1.co_empIngImp=a2.co_empAju AND a1.co_locIngImp=a2.co_locAju AND a1.co_tipDocIngImp=a2.co_tipDocAju AND a1.co_docIngImp=a2.co_docAju AND a1.co_itmMae=a2.co_itmMae \n";   
                //Se agrega validacion para ajustes con autorizacion de mercaderia en las distintas bodegas, solicitado por AP 12/Oct/2017
                strSQL+=" 	LEFT OUTER JOIN( /* CANTIDAD AUTORIZADA AJUSTE */ \n"; 
                strSQL+="             SELECT a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a2.co_regRel, a2.nd_can AS nd_canAut \n";			
                strSQL+="             FROM tbm_cabOrdDis AS a1 INNER JOIN tbm_detOrdDis AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n "; 		
                strSQL+="             INNER JOIN tbr_cabMovInv as a3 ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                strSQL+="             WHERE a1.co_bod=" + txtCodBodDes.getText() + " AND a1.st_reg='A' \n";
                strSQL+=" 	      AND a3.co_empRel=" + intCodEmpPedIngImp + " AND a3.co_locRel=" + intCodLocPedIngImp + "";
                strSQL+=" 	      AND a3.co_tipDocRel=" + intCodTipDocPedIngImp + " AND a3.co_docRel=" + intCodDocPedIngImp + "\n";                
                strSQL+=" 	) AS a3 \n";
                strSQL+=" 	ON a1.co_empIngImp=a3.co_empRel AND a1.co_locIngImp=a3.co_locRel AND a1.co_tipDocIngImp=a3.co_tipDocRel AND a1.co_docIngImp=a3.co_docRel AND a1.co_regIngImp=a3.co_regRel\n";	
                //
                strSQL+=" 	GROUP BY a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_regIngImp, a1.co_itmEmp, a1.co_itmMae, a1.tx_codAlt\n"; 
                strSQL+=" 	       , a1.tx_codAlt2, a1.tx_nomItm, a1.tx_unimed, a1.nd_cosUni, a1.nd_canIngImp, a3.nd_canAut, a1.nd_canCon, a2.nd_canTrs, a1.nd_canUtiOrdDis, a1.st_IsDocAju\n"; 
                strSQL+="  ) AS a1 \n";
                //PESO
                strSQL+="  INNER JOIN( \n";  	
                strSQL+="       SELECT a3.co_emp, a4.co_itmMae, a3.co_itm AS co_itmGrp, CASE WHEN a3.nd_pesitmkgr IS NULL THEN 0 ELSE a3.nd_pesitmkgr END AS nd_pesitmkgr \n";   	
                strSQL+="       FROM tbm_inv AS a3 INNER JOIN tbm_equInv AS a4  ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm) \n"; 	
                strSQL+="       WHERE a3.co_emp=" + objParSis.getCodigoEmpresa() + "\n";
                strSQL+="  ) AS a2 ON a1.co_itmMae=a2.co_itmMae \n";
                strSQL+=" ) AS x \n";
                // Muestra solo items pendientes de transferir
                strSQL+=" WHERE (CASE WHEN x.nd_canCon <= x.nd_canIngImp THEN ( CASE WHEN (x.nd_canCon - x.nd_CanTrs)>0 THEN TRUE ELSE FALSE END ) ELSE (CASE WHEN (x.nd_canIngImp - x.nd_CanTrs) > 0 THEN TRUE ELSE FALSE END) END) ";
                strSQL+=" ORDER BY x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.co_regIngImp";
                
                System.out.println("cargarItmPenTrs: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK, null);
                        vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,     rst.getString("co_empIngImp") );
                        vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,     rst.getString("co_locIngImp") );
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, rst.getString("co_tipDocIngImp") );
                        vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,     rst.getString("co_docIngImp") );
                        vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,     rst.getString("co_regIngImp") );
                        vecReg.add(INT_TBL_DAT_COD_ITM_GRP,         rst.getString("co_itmGrp"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_EMP,         rst.getString("co_itmEmp"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,         rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_ALTDOS,      rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,             rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,     rst.getString("tx_unimed"));
                        
                        bgdCanIngImp=new BigDecimal(rst.getObject("nd_canIngImp")==null?"0":(rst.getString("nd_canIngImp").equals("")?"0":rst.getString("nd_canIngImp")));
                        bgdCanAut=new BigDecimal(rst.getObject("nd_canAut")==null?"0":(rst.getString("nd_canAut").equals("")?"0":rst.getString("nd_canAut")));
                        bgdCanCon=new BigDecimal(rst.getObject("nd_canCon")==null?"0":(rst.getString("nd_canCon").equals("")?"0":rst.getString("nd_canCon")));
                        bgdCanTrs=new BigDecimal(rst.getObject("nd_canTrs")==null?"0":(rst.getString("nd_canTrs").equals("")?"0":rst.getString("nd_canTrs")));                        
                        
                        bgdCanPen=getCanPenTrs(bgdCanIngImp, bgdCanAut, bgdCanCon, bgdCanTrs);
                        
                        vecReg.add(INT_TBL_DAT_CAN_ING_IMP,         rst.getString("nd_canIngImp"));
                        vecReg.add(INT_TBL_DAT_CAN_AUT,             bgdCanAut);
                        vecReg.add(INT_TBL_DAT_CAN_CON,             bgdCanCon);
                        vecReg.add(INT_TBL_DAT_CAN_UTI,             bgdCanTrs);
                        vecReg.add(INT_TBL_DAT_CAN_TRA_OK,          bgdCanTrs);
                        vecReg.add(INT_TBL_DAT_CAN_PEN,             bgdCanPen);
                        vecReg.add(INT_TBL_DAT_CAN_USR_SOLTRA,             "");
                        vecReg.add(INT_TBL_DAT_COS_UNI,             rst.getString("nd_cosUni"));
                        vecReg.add(INT_TBL_DAT_COS_TOT,             "");
                        vecReg.add(INT_TBL_DAT_PES_UNI,             rst.getString("nd_pesitmkgr"));
                        vecReg.add(INT_TBL_DAT_PES_TOT,             "");
                        vecReg.add(INT_TBL_DAT_EST_ITM_EXI_ING_IMP, rst.getString("st_regExiItmIngImp"));      
                        vecReg.add(INT_TBL_DAT_EST_ITM_TIE_DOC_AJU, rst.getString("st_IsDocAju"));       
                        vecDat.add(vecReg);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)     {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;        
    }
    
    /**
     * Función que obtiene cantidad pendiente de transferir.
     * @param fila
     * @return 
     */
    private BigDecimal getCanPenTrs(BigDecimal bgdCanIngImp, BigDecimal bgdCanAut, BigDecimal bgdCanCon, BigDecimal bgdCanTrs)
    {
        BigDecimal bgdCanPen=new BigDecimal("0");
        //La cantidad Menor es la que se establece.
        /* Si existe conteo */
        if(bgdCanCon.compareTo(new BigDecimal("0"))>0){
            //Si CanIngImp >= CanAut, entonces CanPen=CanAut , caso contrario CanPen=CanIngImp
            bgdCanPen=(bgdCanIngImp.compareTo(bgdCanAut)>=0?bgdCanAut:bgdCanIngImp);
            //Si CanPen(Ing/Aut) >= CanCon, entonces CanPen=CanCon, caso contrario CanPen=CanPen(Ing/Aut)
            bgdCanPen=(bgdCanPen.compareTo(bgdCanCon)>=0?bgdCanCon:bgdCanPen).subtract(bgdCanTrs);
        }
        else{
            bgdCanPen=new BigDecimal("0"); //Sino existe conteo, no presentar item.
        }
        return bgdCanPen;
    }
  
    /**
     * Función que permite calcular los valores de las columnas de Costo Unitario, Costo Total, Peso Total
     * del registro seleccionado
     * Calcula y setea valores del documento y peso total del documento
     * @param fila La fila seleccionada
     */
    private void calcularCosPesVal(int fila)
    {
        BigDecimal bdeCanTrs=new BigDecimal("0");//lo tengo
        BigDecimal bdeCosUni=new BigDecimal("0");//lo tengo
        BigDecimal bdePesUni=new BigDecimal("0");
        String strLin="";
        strLin=objTblMod.getValueAt(fila, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_LIN).toString();
        if(strLin.equals("M")){
            bdeCanTrs=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_USR_SOLTRA)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_USR_SOLTRA).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_USR_SOLTRA).toString()));
            bdeCosUni=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI).toString()));
            bdePesUni=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_UNI)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_UNI).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_PES_UNI).toString()));
            objTblMod.setValueAt( objUti.redondearBigDecimal((bdeCanTrs.multiply(bdeCosUni)), objParSis.getDecimalesBaseDatos()), fila, INT_TBL_DAT_COS_TOT);
            objTblMod.setValueAt( objUti.redondearBigDecimal((bdeCanTrs.multiply(bdePesUni)), objParSis.getDecimalesBaseDatos()), fila, INT_TBL_DAT_PES_TOT);
        }            
    }
    
    /**
     * Función que permite calcular valor del documento y peso total del documento.
     */
    private void calcularValPesDoc()
    {
        String strLin="";
        BigDecimal bdeCosTot=new BigDecimal("0");
        BigDecimal bdePesTotDoc=new BigDecimal("0");
        
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
            if(strLin.equals("M")){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    bdeCosTot=bdeCosTot.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString())));
                    bdePesTotDoc=bdePesTotDoc.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT).toString())));
                }
            }
        }
        txtPesTotKgr.setText(""+ objUti.redondearBigDecimal(bdePesTotDoc, objParSis.getDecimalesBaseDatos()));
    }
    
    private boolean eliminaItmNotTieCanPen()
    {
        boolean blnRes=true;
        BigDecimal bgdCanPen=new BigDecimal("0");
        try
        {
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--)
            {
                bgdCanPen=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PEN)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PEN).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_PEN).toString()));
                if(bgdCanPen.compareTo(new BigDecimal("0"))<=0)
                    objTblMod.removeRow(i);
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            System.err.println("ERROR eliminaItmNotTieCanPen: " + e);
        }
        return blnRes;
    }
    
    /**
     * Valida que pedido no haya sido cerrado o este pendiente de arribo, etc...
     * @return TRUE: Si pedido esta apto para realizar transferencia.
     */
    private boolean validaPedido() 
    {
        boolean blnRes=false;
        Connection conLoc=null;
        Statement stmLoc = null;
        ResultSet rstLoc = null;
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL ="";
                strSQL+=getSQLPedidos();
                strSQL+=" AND a.co_emp="+intCodEmpPedIngImp;
                strSQL+=" AND a.co_loc="+intCodLocPedIngImp;
                strSQL+=" AND a.co_tipDoc="+intCodTipDocPedIngImp;
                strSQL+=" AND a.co_doc="+intCodDocPedIngImp;
                //System.out.println("validaPedido:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()) 
                {
                    blnRes=true;  
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
        
    
    private boolean actualizaCantidadesBD(int intFil) 
    {
        boolean blnRes=true;
        Connection conLoc=null;
        Statement stmLoc = null;
        ResultSet rstLoc = null;
        String strIsDocAju="";
        
        BigDecimal bgdCanIngImp=new BigDecimal("0");
        BigDecimal bgdCanAut= new BigDecimal("0");
        BigDecimal bgdCanCon= new BigDecimal("0");
        BigDecimal bgdCanTrs= new BigDecimal("0");
        BigDecimal bgdCanPen= new BigDecimal("0");
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strIsDocAju=objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_ITM_TIE_DOC_AJU)==null?"N":(objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).toString().equals("")?"N":objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_ITM_TIE_DOC_AJU).toString());     
                
                if(strIsDocAju.equals("S"))   {
                    strSQL =" 	SELECT a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_regIngImp, a1.co_itmEmp, a1.co_itmMae"; 
                    strSQL+=" 	     , 'N' AS st_regExiItmIngImp, a1.nd_canIngImp, a1.nd_canAut, a1.nd_canCon, (CASE WHEN a2.nd_canTrs IS NULL THEN 0 ELSE a2.nd_canTrs END) AS nd_canTrs, a1.st_IsDocAju"; 
                    strSQL+=" 	FROM (	"; // DOCUMENTO AJUSTE 
                    strSQL+=" 	    SELECT a1.co_emp AS co_empIngImp, a1.co_loc AS co_locIngImp, a1.co_tipdoc AS co_tipDocIngImp, a1.co_doc AS co_docIngImp, a2.co_reg AS co_regIngImp";
                    strSQL+=" 		 , a2.co_itm AS co_itmEmp, a3.co_itmMae, a2.nd_can AS nd_canIngImp, a2.nd_can as nd_canAut, a2.nd_can as nd_canCon";
                    strSQL+=" 		 , cast('S' as character varying) as st_IsDocAju";    		 	
                    strSQL+=" 	    FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1 ON (a5.co_empRel=a1.co_emp AND a5.co_locRel=a1.co_loc AND a5.co_tipDocRel=a1.co_tipDoc AND a5.co_docRel=a1.co_doc)	)";
                    strSQL+=" 	    INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)"; 	
                    strSQL+=" 	    INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";	
                    strSQL+=" 	    WHERE a1.st_reg='A' AND a1.st_aut IN ('A') AND a2.nd_Can>0 ";
                    strSQL+="       AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END )  NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                    strSQL+=" 	    AND a1.co_emp="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                    strSQL+=" 	    AND a1.co_loc="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                    strSQL+="       AND a1.co_tipDoc="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ING_IMP);
                    strSQL+="       AND a1.co_doc="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                    strSQL+="       AND a3.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);      	
                    strSQL+=" 	) AS a1	 "; 
                    strSQL+=" 	LEFT OUTER JOIN( "; // TRANSFERENCIAS AJUSTE
                    strSQL+=" 	    SELECT a2.co_empRel AS co_empAju, a2.co_locRel AS co_locAju, a2.co_tipDocRel AS co_tipDocAju, a2.co_docRel AS co_docAju";
                    strSQL+=" 	         , a5.co_itmMae, ABS(SUM(a3.nd_Can)) as nd_CanTrs";          
                    strSQL+=" 	    FROM tbm_CabMovInv as a";
                    strSQL+=" 	    INNER JOIN tbm_cabTipDoc AS a1 oN (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc) ";    
                    strSQL+=" 	    INNER JOIN tbr_cabMovinv as a2 ON (a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc AND a2.co_doc=a.co_doc)";
                    strSQL+=" 	    INNER JOIN tbm_detMovInv AS a3 oN (a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc AND a3.co_tipDoc=a.co_tipDoc AND a3.co_doc=a.co_doc)";
                    strSQL+=" 	    INNER JOIN tbm_inv AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_itm=a3.co_itm)";
                    strSQL+=" 	    INNER JOIN tbm_equInv AS a5 ON (a5.co_emp=a4.co_emp AND a5.co_itm=a4.co_itm)";
                    strSQL+=" 	    WHERE a3.nd_Can<0 AND a.st_reg='A' ";
                    strSQL+=" 	    AND a2.co_empRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                    strSQL+=" 	    AND a2.co_locRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                    strSQL+=" 	    AND a2.co_tipDocRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ING_IMP);
                    strSQL+=" 	    AND a2.co_docRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                    strSQL+=" 	    AND a5.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);    
                    strSQL+=" 	    GROUP BY a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a5.co_itmMae";
                    strSQL+=" 	) AS a2 ON a1.co_empIngImp=a2.co_empAju AND a1.co_locIngImp=a2.co_locAju AND a1.co_tipDocIngImp=a2.co_tipDocAju AND a1.co_docIngImp=a2.co_docAju AND a1.co_itmMae=a2.co_itmMae";   
                    strSQL+=" 	GROUP BY a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_regIngImp, a1.co_itmEmp, a1.co_itmMae"; 
                    strSQL+=" 	       , a1.nd_canIngImp, a1.nd_canAut, a1.nd_canCon, a2.nd_canTrs, a1.st_IsDocAju"; 
                }
                else  {
                    strSQL =" SELECT a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_itmMae";
                    strSQL+="      , a1.nd_canIngImp, a3.nd_canAut, a4.nd_canCon, a2.nd_canTrs";
                    strSQL+=" 	FROM( ";  // INIMPO
                    strSQL+="             SELECT a1.co_emp AS co_empIngImp, a1.co_loc AS co_locIngImp, a1.co_tipDoc AS co_tipDocIngImp, a1.co_doc AS co_docIngImp";
                    strSQL+="                  , a2.co_reg AS co_regIngImp, a2.co_itm AS co_itmEmp, a4.co_itmMae, a2.nd_can AS nd_canIngImp";               		      
                    strSQL+="             FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)"; 		
                    strSQL+="             INNER JOIN tbm_equInv AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)"; 		
                    strSQL+="             WHERE a1.st_Reg='A' AND a1.co_emp="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                    strSQL+="             AND a1.co_loc="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                    strSQL+="             AND a1.co_tipDoc="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ING_IMP);
                    strSQL+="             AND a1.co_doc="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                    strSQL+="             AND a4.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);  	
                    strSQL+=" 	) AS a1	";
                    strSQL+=" 	LEFT OUTER JOIN( ";	//TRANSFERENCIAS	
                    strSQL+="             SELECT a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel, a4.co_itmMae, ABS(SUM(a2.nd_can)) AS nd_canTrs, a6.co_bodGrp";
                    strSQL+="             FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1 ON (a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc) )";		
                    strSQL+="             INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)"; 
                    strSQL+="             INNER JOIN tbm_equInv AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) ";		
                    strSQL+="             INNER JOIN tbr_bodEmpBodGrp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_bod=a6.co_bod)";
                    strSQL+="             WHERE a2.nd_can>0 AND a1.st_reg='A' ";
                    strSQL+="             AND a6.co_bodGrp=" + txtCodBodDes.getText() ;
                    strSQL+="             AND a5.co_empRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                    strSQL+="             AND a5.co_locRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                    strSQL+="             AND a5.co_tipdocRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ING_IMP);
                    strSQL+="             AND a5.co_docRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                    strSQL+="             AND a4.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);  	
                    strSQL+="             GROUP BY a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel, a4.co_itmMae, a6.co_bodGrp";
                    strSQL+=" 	) AS a2";
                    strSQL+=" 	ON a1.co_empIngImp=a2.co_empRel AND a1.co_locIngImp=a2.co_locRel AND a1.co_tipDocIngImp=a2.co_tipDocRel AND a1.co_docIngImp=a2.co_docRel AND a1.co_itmMae=a2.co_itmMae"; 	
                    strSQL+=" 	LEFT OUTER JOIN( "; //CANTIDAD AUTORIZADA	
                    strSQL+="             SELECT a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a2.co_RegRel, a4.co_itmMae, ABS(SUM (a2.nd_can)) AS nd_canAut";		
                    strSQL+="             FROM tbm_cabOrdDis AS a1 INNER JOIN tbm_detOrdDis AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)"; 	
                    strSQL+="             INNER JOIN tbm_equInv AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)"; 		
                    strSQL+="             WHERE a1.co_bod=" + txtCodBodDes.getText() + " AND a1.st_reg='A'";	
                    strSQL+="             AND a2.co_empRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                    strSQL+="             AND a2.co_locRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                    strSQL+="             AND a2.co_tipdocRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ING_IMP);
                    strSQL+="             AND a2.co_docRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                    strSQL+="             AND a4.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);  	
                    strSQL+="             GROUP BY a2.co_empRel, a2.co_locRel, a2.co_tipdocRel, a2.co_docRel, a2.co_RegRel, a4.co_itmMae";	
                    strSQL+=" 	) AS a3";
                    strSQL+=" 	ON a1.co_empIngImp=a3.co_empRel AND a1.co_locIngImp=a3.co_locRel AND a1.co_tipDocIngImp=a3.co_tipDocRel AND a1.co_docIngImp=a3.co_docRel AND a1.co_regIngImp=a3.co_regRel";	
                    strSQL+=" 	LEFT OUTER JOIN(  ";	//CONTEOS	
                    strSQL+="              SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a4.co_itmMae, ABS(SUM(a5.nd_stkcon)) AS nd_canCon";                 
                    strSQL+="              FROM tbm_cabOrdConInv AS a1 ";
                    strSQL+="              INNER JOIN ( tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_locRel";
                    strSQL+="                           AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel AND a2.co_reg=a5.co_regRel) ";							
                    strSQL+="              ) ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";			
                    strSQL+="              INNER JOIN tbm_equInv AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) ";
                    strSQL+="              WHERE a1.co_empRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                    strSQL+="              AND a1.co_locRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                    strSQL+="              AND a1.co_tipdocRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ING_IMP);
                    strSQL+="              AND a1.co_docRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                    strSQL+="              AND a4.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);  			
                    strSQL+="              GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a4.co_itmMae";
                    strSQL+=" 	) AS a4 ";
                    strSQL+=" 	ON a1.co_empIngImp=a4.co_empRel AND a1.co_locIngImp=a4.co_locRel AND a1.co_tipDocIngImp=a4.co_tipDocRel AND a1.co_docIngImp=a4.co_docRel AND a1.co_itmMae=a4.co_itmMae ";	
                    strSQL+=" 	GROUP BY a1.co_empIngImp, a1.co_locIngImp, a1.co_tipDocIngImp, a1.co_docIngImp, a1.co_itmMae, a1.nd_canIngImp, a3.nd_canAut, a4.nd_canCon, a2.nd_canTrs";
                    /* ITEMS CONTADOS QUE NO ESTAN EN INIMPO  */
                    strSQL+="     UNION  "; 
                    strSQL+=" 	SELECT a1.co_empRel AS co_empIngImp, a1.co_locRel AS co_locIngImp, a1.co_tipDocRel AS co_tipDocIngImp";
                    strSQL+=" 	     , a1.co_docRel AS co_docIngImp, a4.co_itmMae, 0 AS nd_canIngImp, NULL AS nd_canAut";
                    strSQL+=" 	     , a5.nd_stkcon AS nd_canCon, NULL AS nd_canTrs";	           
                    strSQL+=" 	FROM tbm_cabOrdConInv AS a1 INNER JOIN tbm_detOrdConInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";       
                    strSQL+=" 	INNER JOIN tbm_conInv AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_locRel AND a2.co_tipDoc=a5.co_tipDocRel AND a2.co_doc=a5.co_docRel AND a2.co_reg=a5.co_regRel)";       
                    strSQL+=" 	INNER JOIN tbm_inv AS a3 ON (a5.co_emp=a3.co_emp AND a5.co_itm=a3.co_itm) ";      
                    strSQL+=" 	INNER JOIN tbm_equInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";       
                    strSQL+=" 	WHERE a5.fe_solCon IS NULL AND a1.co_empRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP_ING_IMP);
                    strSQL+=" 	AND a1.co_locRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_LOC_ING_IMP);
                    strSQL+=" 	AND a1.co_tipDocRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC_ING_IMP);
                    strSQL+=" 	AND a1.co_docRel="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_DOC_ING_IMP);
                    strSQL+=" 	AND a4.co_itmMae="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE);  	
                }
                //System.out.println("actualizaCantidadesBD:"+strSQL);                
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    bgdCanIngImp=new BigDecimal(rstLoc.getObject("nd_canIngImp")==null?"0":(rstLoc.getString("nd_canIngImp").equals("")?"0":rstLoc.getString("nd_canIngImp")));
                    bgdCanAut=new BigDecimal(rstLoc.getObject("nd_canAut")==null?"0":(rstLoc.getString("nd_canAut").equals("")?"0":rstLoc.getString("nd_canAut")));
                    bgdCanCon=new BigDecimal(rstLoc.getObject("nd_canCon")==null?"0":(rstLoc.getString("nd_canCon").equals("")?"0":rstLoc.getString("nd_canCon")));
                    bgdCanTrs=new BigDecimal(rstLoc.getObject("nd_canTrs")==null?"0":(rstLoc.getString("nd_canTrs").equals("")?"0":rstLoc.getString("nd_canTrs")));

                    bgdCanPen=getCanPenTrs(bgdCanIngImp, bgdCanAut, bgdCanCon, bgdCanTrs);
                    
                    objTblMod.setValueAt( bgdCanAut, intFil, INT_TBL_DAT_CAN_AUT);
                    objTblMod.setValueAt( bgdCanCon, intFil, INT_TBL_DAT_CAN_CON);
                    objTblMod.setValueAt( bgdCanTrs, intFil, INT_TBL_DAT_CAN_UTI);
                    objTblMod.setValueAt( bgdCanTrs, intFil, INT_TBL_DAT_CAN_TRA_OK);
                    objTblMod.setValueAt( bgdCanPen, intFil, INT_TBL_DAT_CAN_PEN);
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
     * ESQUEMA "SOLICITUDES DE TRANSFERENCIAS DE INVENTARIO".
     * Esta función inserta el registro en la base de datos.
     *
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                con.setAutoCommit(false);
                if(getDatSolTrs())
                {
                    if(generarSolTrs()) {
                        con.commit();
                        blnRes=true; 
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
        catch (java.sql.SQLException e) {
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
     * Obtiene todos los datos para generar las solicitudes de transferencias de inventario.
     * Primero se almacenaran todos los datos en un arreglo para luego utilizar el arreglo en el insertar.
     * @return 
     */
    private boolean getDatSolTrs()
    {
        boolean blnRes=true;
        String strDatPK="", strCodEmpIng="", strCodLocIng="", strCodTipDocIng="", strCodDocIng="";
        BigDecimal bgdPesoTotalUnitario= new BigDecimal("0");
        try
        {
            //Obtiene Datos Items a Solicitar Transferir.
            arlDatItm = new ArrayList();
            for(int i=0; i<objTblMod.getRowCountTrue();i++)
            {
                if( (objTblMod.isChecked(i, INT_TBL_DAT_CHK)) && (objTblMod.getValueAt(i, INT_TBL_DAT_CAN_USR_SOLTRA).toString().length()>0) )
                {
                    strCodEmpIng = objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_ING_IMP).toString();
                    strCodLocIng = objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_ING_IMP).toString();
                    strCodTipDocIng = objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_ING_IMP).toString();
                    strCodDocIng = objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_ING_IMP).toString();
                    strDatPK=strCodEmpIng+"-"+strCodLocIng+"-"+strCodTipDocIng+"-"+strCodDocIng;

                    //Datos Solicitud
                    arlRegItm = new ArrayList();
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_PK_INGIMP,        strDatPK);
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_CODEMP_INGIMP,    strCodEmpIng);
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_CODLOC_INGIMP,    strCodLocIng);
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_CODTIPDOC_INGIMP, strCodTipDocIng);
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_CODDOC_INGIMP,    strCodDocIng);
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_NUMPED_INGIMP,    "Ped.# "+strPedIngImp);
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_ITM_GRP,          Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString()));
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_CAN,              new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_USR_SOLTRA).toString()));

                    bgdPesoTotalUnitario = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI).toString()) ;
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_PES_UNI, bgdPesoTotalUnitario);

                    bgdPesoTotalUnitario = bgdPesoTotalUnitario.multiply(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_USR_SOLTRA).toString()));
                    arlRegItm.add(INT_ARL_GEN_DAT_SOL_PES_UNITOT, bgdPesoTotalUnitario);

                    arlDatItm.add(arlRegItm);
                }                    
            }
        }
        catch (Exception e) {    blnRes = false;       objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    /**
     * Función que genera las solicitudes de transferencias de acuerdo a las pk dentro del pedido.
     * Ejemplo: Cuando existen documentos de ajustes se generará una solicitud por cada ajuste.
     * @return 
     */
    private boolean generarSolTrs()
    {
        boolean blnRes=true;
        boolean blnSol=false;
        String strDatPK="", strDatPKAft="";
        BigDecimal bgdPesoTotalSol= new BigDecimal("0");
        try
        {
            if(con!=null)
            {
                arlDatSolTraInv = new ArrayList();   //Arreglo de solicitudes.
                arlDatSolImp = new ArrayList();      //Arreglo para almacenar solicitudes a imprimir.
                
                for(int i=0; i<arlDatItm.size(); i++)
                {
                    if(blnSol) {    arlDatSolTraInv = new ArrayList();    }

                    //strDatPKBef = ( ( (i-1) ==-1)?"":objUti.getStringValueAt(arlDatItm, (i-1), INT_ARL_GEN_DAT_SOL_PK_INGIMP) );
                    strDatPKAft = ( ( (i+1) == arlDatItm.size())?"":objUti.getStringValueAt(arlDatItm, (i+1), INT_ARL_GEN_DAT_SOL_PK_INGIMP) );
                    strDatPK = objUti.getStringValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_PK_INGIMP);

                    arlRegSolTraInv = new ArrayList();
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_PK_INGIMP,        objUti.getStringValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_PK_INGIMP));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_CODEMP_INGIMP,    objUti.getStringValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_CODEMP_INGIMP));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_CODLOC_INGIMP,    objUti.getStringValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_CODLOC_INGIMP));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_CODTIPDOC_INGIMP, objUti.getStringValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_CODTIPDOC_INGIMP));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_CODDOC_INGIMP,    objUti.getStringValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_CODDOC_INGIMP));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_NUMPED_INGIMP,    objUti.getStringValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_NUMPED_INGIMP));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_ITM_GRP,          objUti.getIntValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_ITM_GRP));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_CAN,              objUti.getBigDecimalValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_CAN));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_PES_UNI,          objUti.getBigDecimalValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_PES_UNI));
                    arlRegSolTraInv.add(INT_ARL_GEN_DAT_SOL_PES_UNITOT,       objUti.getBigDecimalValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_PES_UNITOT));
                    arlDatSolTraInv.add(arlRegSolTraInv);

                    bgdPesoTotalSol = bgdPesoTotalSol.add(objUti.getBigDecimalValueAt(arlDatItm, i, INT_ARL_GEN_DAT_SOL_PES_UNITOT));

                    if(!strDatPK.equals(strDatPKAft)) 
                    {
                        //Genera Solicitud de Transferencia
                        if(objCom91.insertarSolicitudTransferenciaImportaciones(con, arlDatSolTraInv, bgdPesoTotalSol, Integer.parseInt(txtCodBodOri.getText()) , Integer.parseInt(txtCodBodDes.getText()), intCodCfgSolTra))
                        {
                            bgdPesoTotalSol= new BigDecimal("0");
                            blnSol=true;   //System.out.println("Se genero solicitud con exito!!!");
                            
                            //Se almacenan las solicitudes que se estan insertando, para posteriormente imprimirlas.
                            arlRegSolImp = new ArrayList();
                            arlRegSolImp.add(INT_ARL_IMP_SOL_TRA_COD_EMP,    ""+objCom91.intCodEmpGenSolTraInv);
                            arlRegSolImp.add(INT_ARL_IMP_SOL_TRA_COD_LOC,    ""+objCom91.intCodLocGenSolTraInv);
                            arlRegSolImp.add(INT_ARL_IMP_SOL_TRA_COD_TIPDOC, ""+objCom91.intCodTipDocGenSolTraInv);
                            arlRegSolImp.add(INT_ARL_IMP_SOL_TRA_COD_DOC,    ""+objCom91.intCodDocGenSolTraInv);
                            arlDatSolImp.add(arlRegSolImp);
                        }
                        else  {
                            blnRes=false;
                            blnSol=false;
                        }
                    }
                    else  {
                        blnSol=false;
                    }
                }
            }
            
        }
        catch (Exception e) {    
            blnRes = false;       
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
            if (con!=null)
            {
                con.setAutoCommit(false);
                if (anular_tbmCabOrdDis())
                {
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e) {
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
    private boolean anular_tbmCabOrdDis()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabOrdDis";
                strSQL+=" SET st_reg='I'";
                strSQL+=" , fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC);
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
        catch (Exception e)  {
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
    public boolean generarRpt(int intTipRpt)
    {
        boolean blnRes=false;
        Connection conRpt;
        String strRutRpt, strNomRpt, strFecHorSer;
        String strSQLRep="", strSQLSubRep="";
        int i, intNumTotRpt;
        
        try
        {
            conRpt =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conRpt!=null){
                objRptSis.cargarListadoReportes(conRpt);
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
                {
                    strSQLRep+=objCom91.getSQLSolTrsCab(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                    strSQLSubRep+=objCom91.getSQLSolTrsDet(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));

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
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strFecImp",  strFecHorSer );
                                    mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + " - " + objRptSis.getNombreReporte(0) + "   ");

                                    if (objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt)) 
                                    {
                                        blnRes = true;
                                        //System.out.println("Se genero reporte de Solicitud de Transferencia de Inventario");
                                    }
                                    break;
                            }
                        }
                    }
                }
            conRpt.close();
            conRpt=null;
            }
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private String getNomPrintServer(int intCodBod) 
    {
        //Boolean blnPruebas =true;
        String strNomPrnSer = "";
        if(objParSis.getTipoRutaReportes().equals("R")) //Pruebas   //A=Equipos Windows ; R=Equipos Linux.
        //if(blnPruebas)
        {
            strNomPrnSer="laser_sistemasss";
        }
        else
        {
            switch (intCodBod) 
            {
                case 4://Compras Locales
                    strNomPrnSer="printOrdDesBodImp";
                    break;
                case 6://Importaciones
                    strNomPrnSer="printOrdDesBodImp";
                    break;
                default:
                    break;
            }
        }
        return strNomPrnSer;
    }
    
    /**
     * Esta función permite imprimir de forma automática la solicitud de transferencia generada
     * @return true: Si se pudo imprimir la solicitud.
     * <BR>false: En el caso contrario.
     */
    private boolean imprimirSolicitudTransferencia(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc) 
    {
        boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strFecHorSer, strNomPrnSer="", strRutRpt = "", strSubRepDir="", strNomRpt="" ;
        String strSQLRep="", strSQLSubRep="";
        int intCodBod = 0;
        java.util.Date datFecAux;
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null)
            {
                stmLoc = conLoc.createStatement();
           
                strSQLRep+=objCom91.getSQLSolTrsCab(CodEmp, CodLoc, CodTipDoc, CodDoc);
                strSQLSubRep+=objCom91.getSQLSolTrsDet(CodEmp, CodLoc, CodTipDoc, CodDoc);
                //System.out.println("imprimirSolicitudTransferencia--> strSQLRep:  " + strSQLRep+" \n strSQLSubRep:" + strSQLSubRep);
                rstLoc = stmLoc.executeQuery(strSQLRep);
                if (rstLoc.next())    {
                    intCodBod = rstLoc.getInt("co_bodOrg");
                }
            
                strNomPrnSer = getNomPrintServer( intCodBod );
                //System.out.println("strNomPrnSer: "+strNomPrnSer);

                strNomRpt="ZafRptCom91.jasper";   //Nombre del Reporte

                //Ruta del Reporte
                if (System.getProperty("os.name").equals("Linux")) { //Linux
                    strSubRepDir = "/Zafiro/Reportes/Compras/ZafCom91/";
                } else {//Windows
                    strSubRepDir = "//172.16.1.2/Zafiro/Reportes/Compras/ZafCom91/";
                }
                strRutRpt=strSubRepDir + strNomRpt;

                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux = null;

                //Inicializar los parametros que se van a pasar al reporte.
                java.util.Map mapPar = new java.util.HashMap();           
                mapPar.put("strSQLRep", strSQLRep);
                mapPar.put("strSQLSubRep", strSQLSubRep);
                mapPar.put("SUBREPORT_DIR", strSubRepDir);
                mapPar.put("strFecImp",  strFecHorSer );
                mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + " - "+strNomRpt+"  ");

                //Impresion Automatica Esquema Antiguo
                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutRpt, mapPar, conLoc);
                javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strNomPrnSer, null);
                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                printServiceAttributeSet.add(printerName);
                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExp.exportReport();
                objPriReqAttSet = null;
               
                blnRes = true;
                
                stmLoc.close();
                stmLoc = null;
                rstLoc.close();
                rstLoc = null;
                conLoc.close();
                conLoc = null;
            }
        } 
        catch (Exception e) 
        {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean imprimirDocumento()
    {
        boolean blnRes=false;
        try
        {
            for(int i=0; i<arlDatSolImp.size(); i++)
            {
                if(imprimirSolicitudTransferencia(objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_EMP),
                                                  objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_LOC),
                                                  objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_TIPDOC),
                                                  objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_DOC) )){
                    
                    if(objCom91.setEstImp(objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_EMP),
                                          objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_LOC),
                                          objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_TIPDOC),
                                          objUti.getIntValueAt(arlDatSolImp, i, INT_ARL_IMP_SOL_TRA_COD_DOC) ) ){     
                        blnRes=true;
                    }                    
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    
    }
    
    
    
    
    
    
    
    
}



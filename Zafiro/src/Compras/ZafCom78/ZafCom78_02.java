/*
 */

package Compras.ZafCom78;

import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Maestros.ZafMae07.ZafMae07_01;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author  EFLORESA.
 * REGISTRO DE ITEMS FALTANTES/DAÑADOS
 * GENERACION DE ORDEN DE CONTEO
 * ENVIO DE EMAIL DE NOTIFICACION.
 * 
 */
public class ZafCom78_02 extends JDialog {
    
    //Constantes: Columnas del JTable:
    private static final int INT_TBL_DAT_LIN          = 0;               //Línea
    private static final int INT_TBL_DAT_COD_EMP      = 1;               //EMPRESA
    private static final int INT_TBL_DAT_COD_LOC      = 2;               //LOCAL
    private static final int INT_TBL_DAT_COD_TIPDOC   = 3;               //TIPO DE DOCUMENTO
    private static final int INT_TBL_DAT_COD_DOC      = 4;               //COD. DE DOCUMENTO
    private static final int INT_TBL_DAT_COD_REG      = 5;               //COD REGISTRO
    private static final int INT_TBL_DAT_COD_ITM      = 6;               //COD. ITEM
    private static final int INT_TBL_DAT_COD_ALT      = 7;               //COD. ALTERNO
    private static final int INT_TBL_DAT_DES_ITM      = 8;               //
    private static final int INT_TBL_DAT_UNI_MED      = 9;               //
    private static final int INT_TBL_DAT_CAN          = 10;              //
    private static final int INT_TBL_DAT_CAN_PEN      = 11;              //
    private static final int INT_TBL_DAT_CAN_FAL      = 12;              //
    private static final int INT_TBL_DAT_CAN_DAN      = 13;              //
    private static final int INT_TBL_DAT_OBS          = 14;              //
    private static final int INT_TBL_DAT_BUT_OBS      = 15;              //
    private static final int INT_TBL_DAT_HIDE_CAN_FAL = 16;              //
    private static final int INT_TBL_DAT_HIDE_CAN_DAN = 17;              //
    private static final int INT_TBL_DAT_NE_NUMDOC    = 18;              //
    private static final int INT_TBL_DAT_CLIRET       = 19;              //
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblCelRenBut objTblDocCelRenBut;
    private ZafTableColBut_uni objTableColButUni;
    private ZafPerUsr objPerUsr;
    private ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    
    private Frame frmPadre;
    
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rst;
    
    private String strSQL;
    private String strMensCorEle;
    private String strVersion = " v. 1.7 ";
    private String strTitulo="Notificacion de Egresos con Faltantes/Dañados";
    private String strTit="Mensaje del Sistema Zafiro";
    private String strIpProd = "172.16.1.2";
    private String strArrDirCor [][] = { //TUVAL
                                         {"1","4","2906","sistemas2@tuvalsa.com", "1"} , 
                                         {"1","4","2906","efloresavelino@hotmail.com", "0"} , 
                                         {"1","4","2906","robertitoflores@hotmail.com", "0"} , 
                                         {"1","4","2906","gabtmora23@hotmail.com", "0"} , 
                                         //CASTEK UIO
                                         {"2","1","2906","efloresavelino@hotmail.com", "0"} , 
                                         //CASTEK MANTA
                                         {"2","4","2906","efloresavelino@hotmail.com", "0"} , 
                                         //CASTEK SDO
                                         {"2","6","2906","efloresavelino@hotmail.com", "0"} , 
                                         //DIMULTI
                                         {"4","3","2906","efloresavelino@hotmail.com", "0" } , 
                                       };
    // {co_emp, co_loc, co_mnu, diremail, tipdoc}
    private String strArrDirCorPro[][]={ //TUVAL
                                         {"1","4","2906","bodega@tuvalsa.com", "0"} , 
                                         {"1","4","2906","ventas_tuval@tuvalsa.com", "1"} , 
                                         {"1","4","2906","operaciones@tuvalsa.com", "0"} , 
                                         {"1","4","2906","sistemas@tuvalsa.com", "0"} , 
                                         {"1","4","2906","gerenciatuval@tuvalsa.com", "0"},
                                         {"1","4","2906","inventario@tuvalsa.com", "0"},
                                         //CASTEK UIO
                                         {"2","1","2906","bodegacastekquito@castek.ec", "0"} , 
                                         {"2","1","2906","ventas_castek_quito@castek.ec", "1"},
                                         {"2","1","2906","operaciones@tuvalsa.com", "0"} , 
                                         {"2","1","2906","sistemas@tuvalsa.com", "0"} , 
                                         {"2","1","2906","gerenciatuval@tuvalsa.com", "0"},
                                         {"2","1","2906","inventario@tuvalsa.com", "0"},
                                         //CASTEK MANTA
                                         {"2","4","2906","bodegacastekmanta@castek.ec", "0"} , 
                                         {"2","4","2906","ventas_castek_manta@castek.ec", "1"} , 
                                         {"2","4","2906","operaciones@tuvalsa.com", "0"} , 
                                         {"2","4","2906","sistemas@tuvalsa.com", "0"} , 
                                         {"2","4","2906","gerenciatuval@tuvalsa.com", "0"},
                                         {"2","4","2906","inventario@tuvalsa.com", "0"},
                                         //CASTEK SDO
                                         {"2","6","2906","ventas_stodgo@castek.ec", "0"} , 
                                         {"2","6","2906","ventas_castek_stodgo@castek.ec", "1"} , 
                                         {"2","6","2906","operaciones@tuvalsa.com", "0"} , 
                                         {"2","6","2906","sistemas@tuvalsa.com", "0"} , 
                                         {"2","6","2906","gerenciatuval@tuvalsa.com", "0"},
                                         {"2","6","2906","inventario@tuvalsa.com", "0"},
                                         //DIMULTI
                                         {"4","3","2906","bodegadimulti@dimulti.com", "0"} , 
                                         {"4","3","2906","ventas_dimulti@dimulti.com", "1"} , 
                                         {"4","3","2906","operaciones@tuvalsa.com", "0"} , 
                                         {"4","3","2906","sistemas@tuvalsa.com", "0"} , 
                                         {"4","3","2906","gerenciatuval@tuvalsa.com", "0"},
                                         {"4","3","2906","inventario@tuvalsa.com", "0"},
                                       };
    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    
    //Variables de la clase.
    private int intOpcSelDlg;     //Opción seleccionada en el JDialog.
    private int intCodEmp;        //Código de la empresa.
    private int intCodLoc;        //Código del local.
    private int intCodTipDoc;     //Código del tipo de documento.
    private int intCodDoc;        //Código del docuemnto.
    private int intCodBod;        //Código de bodega.
    private int intCodBodGrp;     //Código de bodega.
    private int intModOper = 0;   //0: desa 1:prod
    
    private double dblCanFal = 0; 
    private double dblCanDan = 0;
    
    private boolean blnHayCambios = false;
    private boolean blnResp = false;
    private boolean blnEnviarEmail = false;
    private boolean blnGenerarOrdenConteo = false;
    
    private ZafCom78 objCom78;
    //private ZafComOrdCon objOrdCon;
    private ZafComOrdenConteo objComOrdCon;
    //private ZafConItm objConItm;
    
    /** Creates new form ZafCom78_02 */
    public ZafCom78_02(Frame parent, boolean modal, ZafParSis obj) {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        intOpcSelDlg=0;
        frmPadre=parent;
        objPerUsr=new ZafPerUsr(obj);
        objInvItm = new ZafInvItm(this, obj); 
        //objConItm = new ZafConItm(obj, parent);
        if (!configurarFrm())
            exitForm();
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes=true;
        try {
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle(strTitulo + strVersion);
            lblTit.setText(strTitulo);
            configurarTblDat();
            configurarVentanaPermisos();
            setModoOperacion();
        }catch(Exception e) {
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
    private boolean configurarTblDat() {
        boolean blnRes=true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"Linea");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cod.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cod.Item.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cod.Alt.");
            vecCab.add(INT_TBL_DAT_DES_ITM,"Nombre del Item.");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Unidad.");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad.");
            vecCab.add(INT_TBL_DAT_CAN_PEN,"Pendiente.");
            vecCab.add(INT_TBL_DAT_CAN_FAL,"Faltante.");
            vecCab.add(INT_TBL_DAT_CAN_DAN,"Dañado.");
            vecCab.add(INT_TBL_DAT_OBS,"Observacion");
            vecCab.add(INT_TBL_DAT_BUT_OBS,"");
            vecCab.add(INT_TBL_DAT_HIDE_CAN_FAL,"");
            vecCab.add(INT_TBL_DAT_HIDE_CAN_DAN,"");
            vecCab.add(INT_TBL_DAT_NE_NUMDOC,"");
            vecCab.add(INT_TBL_DAT_CLIRET,"Cliente Retira.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_FAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_DAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_HIDE_CAN_FAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_HIDE_CAN_DAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_ITM).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(20);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIDE_CAN_FAL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_HIDE_CAN_DAN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NE_NUMDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CLIRET, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            /*vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CAN_FAL);
            vecAux.add("" + INT_TBL_DAT_CAN_DAN);
            vecAux.add("" + INT_TBL_DAT_BUT_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;*/
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);            
           
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HIDE_CAN_FAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HIDE_CAN_DAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAL).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    validarFaltDan();
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(objTblDocCelRenBut);

            objTableColButUni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_OBS, this.getTitle()) {
                @Override
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(frmPadre, true, strObs);
                    zafMae07_01.setVisible(true);
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_OBS);
                    }
                    zafMae07_01=null;
                }
            };

            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función establece los parámetros que debe utilizar el JDialog.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intCodTipDoc El código del tipo de documento.
     * @param intCodDoc El código del documento.
     */
    public void setParams(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
        this.intCodEmp=intCodEmp;
        this.intCodLoc=intCodLoc;
        this.intCodTipDoc=intCodTipDoc;
        this.intCodDoc=intCodDoc;
    }

    public void setParams(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ZafCom78 objCom78) {
        this.intCodEmp=intCodEmp;
        this.intCodLoc=intCodLoc;
        this.intCodTipDoc=intCodTipDoc;
        this.intCodDoc=intCodDoc;
        this.objCom78=objCom78;
    }

    public void setParams(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodBodGrp, ZafCom78 objCom78) {
        this.intCodEmp=intCodEmp;
        this.intCodLoc=intCodLoc;
        this.intCodTipDoc=intCodTipDoc;
        this.intCodDoc=intCodDoc;
        this.intCodBod=intCodBod;
        this.intCodBodGrp=intCodBodGrp;
        this.objCom78=objCom78;
    }

    /** Cerrar la aplicación. */
    private void exitForm() {
        blnResp=false;
        //hide();
        setVisible(false);
        dispose();
        //Runtime.getRuntime().gc();
    }
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarReg(){
        boolean blnRes=false;
        try {
            if (cargarDetReg()) {
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0) {
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
                blnRes=true;
            }
        }catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     * Se agrega funcionalidad para conocer si la orden de despacho tienen items que cliente retira en otra bodega.
     */
    private boolean cargarDetReg() {
        boolean blnRes=false;
        /*strSQL=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, b.co_reg, b.co_itm, b.tx_codalt, b.tx_nomitm, b.tx_unimed, " +
               " case when b.nd_can is null then 0 else b.nd_can end as nd_can, " +
               " (abs(b.nd_can)- (abs(b.nd_cantotguisec) + abs(b.nd_cannunrec))) as nd_canpen, " +
               " case when b.nd_canfal is null then 0 else b.nd_canfal end as nd_canfal, " + 
               " case when b.nd_candan is null then 0 else b.nd_candan end as nd_candan, " +
               " b.tx_obsfaldan, a.ne_numorddes "  +
               " from tbm_cabguirem as a, tbm_detguirem as b " +
               " where a.co_emp=b.co_emp " +
               " and a.co_loc=b.co_loc " +
               " and a.co_tipdoc=b.co_tipdoc " +
               " and a.co_doc=b.co_doc " +
               " and a.co_emp=? " +
               " and a.co_loc=? " +
               " and a.co_tipdoc=? " +
               " and a.co_doc=? " +
               " order by b.co_reg ";*/
        
        /*strSQL=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, b.co_reg, b.co_itm, b.tx_codalt, b.tx_nomitm, b.tx_unimed, " +
               " case when b.nd_can is null then 0 else b.nd_can end as nd_can, " +
               " (abs(b.nd_can)- (abs(b.nd_cantotguisec) + abs(b.nd_cannunrec))) as nd_canpen, " +
               " case when b.nd_canfal is null then 0 else b.nd_canfal end as nd_canfal, " + 
               " case when b.nd_candan is null then 0 else b.nd_candan end as nd_candan, " +
               " b.tx_obsfaldan, a.ne_numorddes, " +
               " (select cliret from (select abs( sum(a4.nd_can) ) as canegr," +
               " case when a4.st_cliretemprel is null then 'N' else 'S' end as cliret " +
               " from tbm_detguirem as a7 " +
               " inner join tbm_detmovinv as a1 on (a1.co_emp=a7.co_emprel and a1.co_loc=a7.co_locrel and a1.co_tipdoc=a7.co_tipdocrel and a1.co_doc=a7.co_docrel and a1.co_reg=a7.co_regrel ) " +
               " inner join tbr_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg ) " +
               " inner join tbr_detmovinv as a3 on (a3.co_emprel=a2.co_emprel and a3.co_locrel=a2.co_locrel and a3.co_tipdocrel=a2.co_tipdocrel and a3.co_docrel=a2.co_docrel and a3.co_regrel=a2.co_regrel and a3.co_doc != a2.co_doc ) " +
               " inner join tbm_detmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a3.co_reg ) " +
               " inner join tbm_bod as a5 on (a5.co_emp=a4.co_emp and a5.co_bod=a4.co_bod ) " +
               " inner join tbm_bod as a6 on (a6.co_emp=a1.co_emp and a6.co_bod=a1.co_bod ) " +
               " where a7.co_emp=a.co_emp and a7.co_loc=a.co_loc and a7.co_tipdoc=a.co_tipdoc and a7.co_doc=a.co_doc and a7.co_reg=b.co_reg and a7.co_tipdocrel=1 " +
               " group by a1.nd_can, a4.st_cliretemprel) as z where canegr > 0)  as cliret "  +
               " from tbm_cabguirem as a, tbm_detguirem as b " +
               " where a.co_emp=b.co_emp " +
               " and a.co_loc=b.co_loc " +
               " and a.co_tipdoc=b.co_tipdoc " +
               " and a.co_doc=b.co_doc " +
               " and a.co_emp=? " +
               " and a.co_loc=? " +
               " and a.co_tipdoc=? " +
               " and a.co_doc=? " +
               " order by b.co_reg ";*/
        
        /*strSQL=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, b.co_reg, b.co_itm, b.tx_codalt, b.tx_nomitm, b.tx_unimed, " +
               " case when b.nd_can is null then 0 else b.nd_can end as nd_can, " +
               " (abs(b.nd_can)- (abs(b.nd_cancon) + abs(b.nd_cannunrec))) as nd_canpen, " +
               " case when b.nd_canfal is null then 0 else b.nd_canfal end as nd_canfal, " + 
               " case when b.nd_candan is null then 0 else b.nd_candan end as nd_candan, " +
               " b.tx_obsfaldan, a.ne_numorddes, " +
               " (select case when a1.st_cliretemprel is null or a1.st_cliretemprel = 'N' then 'N' else 'S' end as cliret " +
               " from tbm_detguirem as a7 " +
               " inner join tbm_detmovinv as a1 on (a1.co_emp=a7.co_emprel and a1.co_loc=a7.co_locrel and a1.co_tipdoc=a7.co_tipdocrel and a1.co_doc=a7.co_docrel and a1.co_reg=a7.co_regrel ) " +
               " inner join tbr_detmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc and a2.co_reg=a1.co_reg ) " +
               " inner join tbr_detmovinv as a3 on (a3.co_emprel=a2.co_emprel and a3.co_locrel=a2.co_locrel and a3.co_tipdocrel=a2.co_tipdocrel and a3.co_docrel=a2.co_docrel and a3.co_regrel=a2.co_regrel and a3.co_doc != a2.co_doc ) " +
               " inner join tbm_detmovinv as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc and a4.co_reg=a3.co_reg ) " +
               " inner join tbm_bod as a5 on (a5.co_emp=a4.co_emp and a5.co_bod=a4.co_bod ) " +
               " inner join tbm_bod as a6 on (a6.co_emp=a1.co_emp and a6.co_bod=a1.co_bod ) " +
               " where a7.co_emp=a.co_emp and a7.co_loc=a.co_loc and a7.co_tipdoc=a.co_tipdoc and a7.co_doc=a.co_doc and a7.co_reg=b.co_reg " +
               " ) as cliret "  +
               " from tbm_cabguirem as a, tbm_detguirem as b " +
               " where a.co_emp=b.co_emp " +
               " and a.co_loc=b.co_loc " +
               " and a.co_tipdoc=b.co_tipdoc " +
               " and a.co_doc=b.co_doc " +
               " and a.co_emp=? " +
               " and a.co_loc=? " +
               " and a.co_tipdoc=? " +
               " and a.co_doc=? " +
               " order by b.co_reg "; */
        
        strSQL=" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, b.co_reg, b.co_itm, b.tx_codalt, b.tx_nomitm, b.tx_unimed, " +
               " case when b.nd_can is null then 0 else b.nd_can end as nd_can, " +
               " (abs(b.nd_can)- (abs(b.nd_cancon) + abs(b.nd_cannunrec))) as nd_canpen, " +
               " case when b.nd_canfal is null then 0 else b.nd_canfal end as nd_canfal, " + 
               " case when b.nd_candan is null then 0 else b.nd_candan end as nd_candan, " +
               " b.tx_obsfaldan, a.ne_numorddes, " +
               " (select case when a4.st_cliretemprel is null or a4.st_cliretemprel = 'N' then 'N' else 'S' end as cliret " +
               " from tbm_detguirem as a7 " +
               " inner join tbr_detmovinv as a2 on (a2.co_emp=a7.co_emprel and a2.co_loc=a7.co_locrel and a2.co_tipdoc=a7.co_tipdocrel and a2.co_doc=a7.co_docrel and a2.co_reg=a7.co_regrel ) " +
               " inner join tbr_detmovinv as a3 on (a3.co_emprel=a2.co_emprel and a3.co_locrel=a2.co_locrel and a3.co_tipdocrel=a2.co_tipdocrel and a3.co_docrel=a2.co_docrel and a3.co_regrel=a2.co_regrel and ( a3.co_emp!=a2.co_emp or a3.co_loc!=a2.co_loc or a3.co_tipdoc!=a2.co_tipdoc or a3.co_doc!=a2.co_doc ) ) " +
               " inner join tbr_detmovinv as a6 on (a6.co_emp=a3.co_emp and a6.co_loc=a3.co_loc and a6.co_tipdoc=a3.co_tipdoc and a6.co_doc=a3.co_doc and a6.co_reg=a3.co_reg ) " +
               " inner join tbm_detmovinv as a4 on (a4.co_emp=a6.co_emp and a4.co_loc=a6.co_loc and a4.co_tipdoc=a6.co_tipdoc and a4.co_doc=a6.co_doc and a4.co_reg=a6.co_reg ) " +
               " inner join tbm_detguirem as a5 on (a5.co_emprel=a4.co_emp and a5.co_locrel=a4.co_loc and a5.co_tipdocrel=a4.co_tipdoc and a5.co_docrel=a4.co_doc and a5.co_regrel=a4.co_reg ) " +
               " inner join tbm_cabguirem as a8 on (a8.co_emp=a5.co_emp and a8.co_loc=a5.co_loc and a8.co_tipdoc=a5.co_tipdoc and a8.co_doc=a5.co_doc ) " +
               " where a7.co_emp=a.co_emp and a7.co_loc=a.co_loc and a7.co_tipdoc=a.co_tipdoc and a7.co_doc=a.co_doc and a7.co_reg=b.co_reg and a2.co_emprel=a.co_emp and a8.st_reg not in ('I','E') " +
               " group by a4.st_cliretemprel " +
               " ) as cliret "  +
               " from tbm_cabguirem as a, tbm_detguirem as b " +
               " where a.co_emp=b.co_emp " +
               " and a.co_loc=b.co_loc " +
               " and a.co_tipdoc=b.co_tipdoc " +
               " and a.co_doc=b.co_doc " +
               " and a.co_emp=? " +
               " and a.co_loc=? " +
               " and a.co_tipdoc=? " +
               " and a.co_doc=? " +
               " order by b.co_reg ";
        
        //System.out.println("ZafCom78_02.cargarDetReg: " + strSQL);
        try {
            getConexion();
            
            if (conn!=null) {
                
                pst = conn.prepareStatement(strSQL);
                pst.setLong(1, intCodEmp);
                pst.setLong(2, intCodLoc);
                pst.setLong(3, intCodTipDoc);
                pst.setLong(4, intCodDoc);
                
                rst = pst.executeQuery();
                
                //Limpiar vector de datos.
                vecDat.clear();
                
                //Obtener los registros.
                while (rst.next()) {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIPDOC, rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_COD_ITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT, rst.getString("tx_codalt"));
                    vecReg.add(INT_TBL_DAT_DES_ITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_DAT_UNI_MED, rst.getString("tx_unimed"));
                    vecReg.add(INT_TBL_DAT_CAN, objUti.parseDouble(rst.getString("nd_can")));
                    vecReg.add(INT_TBL_DAT_CAN_PEN, objUti.redondear(objUti.parseDouble(rst.getString("nd_canpen")), 2));
                    vecReg.add(INT_TBL_DAT_CAN_FAL, objUti.redondear(objUti.parseDouble(rst.getString("nd_canfal")), 2));
                    vecReg.add(INT_TBL_DAT_CAN_DAN, objUti.redondear(objUti.parseDouble(rst.getString("nd_candan")), 2));
                    vecReg.add(INT_TBL_DAT_OBS, rst.getString("tx_obsfaldan"));
                    vecReg.add(INT_TBL_DAT_BUT_OBS, "");
                    vecReg.add(INT_TBL_DAT_HIDE_CAN_FAL, objUti.redondear(objUti.parseDouble(rst.getString("nd_canfal")), 2));
                    vecReg.add(INT_TBL_DAT_HIDE_CAN_DAN, objUti.redondear(objUti.parseDouble(rst.getString("nd_candan")), 2));
                    vecReg.add(INT_TBL_DAT_NE_NUMDOC, rst.getString("ne_numorddes"));
                    vecReg.add(INT_TBL_DAT_CLIRET, rst.getString("cliret"));
                    vecDat.add(vecReg);
                }
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                blnRes=true;
            }
        }catch (SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally{
            try{
                if (rst!=null)
                    rst.close();
                if (pst!=null) 
                    pst.close();
                if (conn!=null) 
                    conn.close();
                rst=null;
                pst=null;
                conn=null; 
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }        
    
    private Connection getConexion(){
        try{
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conn.setAutoCommit(false);
        }catch(SQLException e){
            conn=null;
        }catch(Exception e){
            conn=null;            
        }
        return conn;
    }

    /**
     * Esta función valida. 
     * @return true: .
     * <BR>false: En el caso contrario.
     */
    private boolean validarFaltDan() {
        boolean blnRes=true;
        
        double dblCanPen = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_PEN).toString()); 
        double dblCanFal = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_FAL).toString()); 
        double dblCanDan = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_DAN).toString()); 
        
        double dblCanFalHide = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_HIDE_CAN_FAL).toString()); 
        double dblCanDanHide = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_HIDE_CAN_DAN).toString()); 
        
        if (isOpcionHabilitada()){
            if (isClienteRetira()){
                mostrarMsgErr("El item es de opcion Cliente Retira, no se puede registrar como faltante/dañado.");                
                objTblMod.setValueAt("0.00", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_LIN);
                blnRes=false;
                blnHayCambios = false;
            }else{                
                if ((dblCanFal + dblCanDan) > dblCanPen){
                    mostrarMsgErr("La cantidad de faltantes y dañados no puede ser mayor que la cantidad pendiente. Favor corrija la informacion.");
                    objTblMod.setValueAt("0.00", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                    objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_LIN);
                    blnHayCambios = false;
                }else
                    blnHayCambios = true;
            }
        }else{
            mostrarMsgErr("No tiene permisos para modificar esta opcion.");
            objTblMod.setValueAt(Double.toString(dblCanFalHide), tblDat.getSelectedRow(), INT_TBL_DAT_CAN_FAL);
            objTblMod.setValueAt(Double.toString(dblCanDanHide), tblDat.getSelectedRow(), INT_TBL_DAT_CAN_DAN);
            blnRes=false;
        }        
        
        return blnRes;
    }
    
    /**
     * Deteermina los permisos para registrar/modificar faltantes y dañados.
     */
    private boolean isOpcionHabilitada(){
        boolean blnRes = false;
        int intCanPer = 0;
        String strModOpeFal = "", strModOpeDan = "";
        double dblCanFalHide = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_HIDE_CAN_FAL).toString()); 
        double dblCanDanHide = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_HIDE_CAN_DAN).toString()); 
        
        double dblCanFal = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_FAL).toString()); 
        double dblCanDan = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_DAN).toString()); 
        
        if ((dblCanFalHide == 0) && (dblCanFal != 0)) {
            strModOpeFal = "IF";
            intCanPer++;
        }
        
        if ((dblCanFalHide > 0) && (dblCanFalHide != dblCanFal)){
            strModOpeFal = "MF";
            intCanPer++;
        }
        
        if ((dblCanDanHide == 0) && (dblCanDan != 0)){
            strModOpeDan = "ID";
            intCanPer++;
        }
        
        if ((dblCanDanHide > 0) && (dblCanDanHide != dblCanDan)){
            strModOpeDan = "MD";
            intCanPer++;
        }
        
        if (strModOpeFal.equals("IF")){
            blnRes=objPerUsr.isOpcionEnabled(3116);
        }
        
        if (strModOpeFal.equals("MF")){
            blnRes=objPerUsr.isOpcionEnabled(3117);
        }
        
        if (strModOpeDan.equals("ID")){
            blnRes=objPerUsr.isOpcionEnabled(3118);
        }
        
        if (strModOpeDan.equals("MD")){
            blnRes=objPerUsr.isOpcionEnabled(3119);
        }
        
        return blnRes;
    }
    
    /**
     * Para el item seleccionado determina si es retirado en la bodega actual o en otra bodega (cliente retira).
     */
    private boolean isClienteRetira(){
        boolean blnRes=false;
        String strCliRet;
        double dblCanFal = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_FAL).toString()); 
        double dblCanDan = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_DAN).toString()); 
        if (objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CLIRET) != null){
            strCliRet = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CLIRET).toString();
            if (strCliRet.equals("S") && ((dblCanFal+dblCanDan) > 0)){
                blnRes=true;
            }
        }
        return blnRes;
    }
    
    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg() {
        boolean blnRes=false;
        blnEnviarEmail = false;
        blnGenerarOrdenConteo=false;
        try {
            getConexion();
            
            if (conn!=null) {
                if (actualizarDet(conn)) {
                    if (actualizarCab(conn)){
                        conn.commit();
                        blnRes=true;
                        if (blnEnviarEmail) enviarEmailNotificacion(conn);
                        if (blnGenerarOrdenConteo) generarOrdenConteo(conn);
                        if (blnGenerarOrdenConteo) objInvItm.enviarReqAlertaFaltantes(conn, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal());
                        this.objCom78.consultar(); 
                    }else
                        conn.rollback();
                }else
                    conn.rollback();
            }
            if (conn!=null) 
                conn.close();
            conn=null;
        }catch (SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab(Connection conn) {
        boolean blnRes=false;
        String strFal = null, strDan = null;
        strSQL =  " update tbm_cabguirem set st_orddesfal = ?, st_orddesdan = ? "
                + " where co_emp = ? "
                + " and co_loc = ? "
                + " and co_tipdoc = ? "
                + " and co_doc = ?";
        try {            
            if (conn!=null) {
                strFal = (dblCanFal > 0)?"S":"N";
                strDan = (dblCanDan > 0)?"S":"N";
                                
                pst = conn.prepareStatement(strSQL);
                pst.setString(1, strFal);
                pst.setString(2, strDan);
                pst.setLong(3, intCodEmp);
                pst.setLong(4, intCodLoc);
                pst.setLong(5, intCodTipDoc);
                pst.setLong(6, intCodDoc);
                
                pst.executeUpdate();
                
                pst.close();
                pst=null;
                blnRes=true;                
            }
        }catch (SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar el detalle de un registro.
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet(Connection conn) {        
        boolean blnRes=false;
        boolean blnEjecuta=false;
        String strSql2 =  " update tbm_detguirem set fe_ultmodfaldan = current_timestamp, co_usrmodfaldan = ? ";
        String strCondicion = " where co_emp = ? and co_loc = ? and co_tipdoc = ? and co_doc = ? and co_reg = ? ";
        String strAux = " ";
        String obsFalDan;
        double canFal = 0, canDan = 0;
        int regs = 0;
        dblCanFal = 0;
        dblCanDan = 0;
        try {
            if (conn!=null) { 
                for (int i=0;i<objTblMod.getRowCountTrue();i++)  {
                    if (objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0) {
                        //Armar la sentencia SQL.
                        strAux = " ";
                        obsFalDan = "";
                        strSQL = "";
                        if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL) != null) {
                            canFal = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL).toString());
                            strAux = strAux + " , nd_canfal = " +  canFal;
                            dblCanFal+=canFal;
                            blnEjecuta = true;
                        }
                        
                        if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN) != null) {
                            canDan = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN).toString());
                            strAux = strAux + " , nd_candan = " +  canDan;
                            dblCanDan+=canDan;
                            blnEjecuta=true;
                        }
                        
                        if (objTblMod.getValueAt(i, INT_TBL_DAT_OBS) != null){ 
                            obsFalDan = objTblMod.getValueAt(i, INT_TBL_DAT_OBS).toString();
                            strAux = strAux + " , tx_obsfaldan = " + objUti.codificar(obsFalDan) ;  
                            blnEjecuta=true;
                        } 
                        
                        if ((dblCanFal+dblCanDan) > 0) {
                            blnEnviarEmail=true;
                            if (dblCanFal > 0 || dblCanDan > 0 ) 
                                blnGenerarOrdenConteo=true;
                        }

                        if (blnEjecuta) {
                            strSQL = strSql2 + strAux + strCondicion;                         
                            pst = conn.prepareStatement(strSQL);
                            pst.setLong(1, objParSis.getCodigoUsuario());
                            pst.setLong(2, intCodEmp);
                            pst.setLong(3, intCodLoc);
                            pst.setLong(4, intCodTipDoc);
                            pst.setLong(5, intCodDoc);
                            pst.setLong(6, Long.parseLong(objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG).toString()));                        
                            pst.executeUpdate();
                            regs++;
                        } 
                    }else{
                        if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL) != null) {
                            canFal = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL).toString());
                            dblCanFal+=canFal;
                        }

                        if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN) != null) {
                            canDan = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN).toString());
                            dblCanDan+=canDan;
                        }
                    }
                }
                
                if (regs > 0) 
                    blnRes = true;  
                
                if (pst!=null) 
                    pst.close();
                pst=null;
            }
        }catch (SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    public boolean getAceptar(){
       return blnResp;
    }
    
    private void configurarVentanaPermisos() {
        Vector vecAuxLoc=new Vector();

        if (objPerUsr.isOpcionEnabled(3116) || objPerUsr.isOpcionEnabled(3117)){ 
            vecAuxLoc.add("" + INT_TBL_DAT_CAN_FAL);
        }

        if (objPerUsr.isOpcionEnabled(3118) || objPerUsr.isOpcionEnabled(3119)){ 
            vecAuxLoc.add("" + INT_TBL_DAT_CAN_DAN);
        }            
        
        if (objPerUsr.isOpcionEnabled(3116) || objPerUsr.isOpcionEnabled(3117) || objPerUsr.isOpcionEnabled(3118) || objPerUsr.isOpcionEnabled(3119)){ 
            vecAuxLoc.add("" + INT_TBL_DAT_BUT_OBS);
            butAce.setEnabled(true);
        }
        
        if (! vecAuxLoc.isEmpty()) {
            objTblMod.setColumnasEditables(vecAuxLoc);
            vecAuxLoc=null;            
        }
    }    
    
    private void enviarEmailNotificacion(Connection conn){ 
        String strDirCorreos = "";
        String strLocal = "";
        String strDesLarDoc = "";
        String strNomCli = "";
        String strObservacion="";
        double canFal = 0, canDan = 0;
        int intCodLocRel=0, intCodDocRel=0, intNumDocRel=0, intCodCliRel=0;        
        
        strSQL = "select tx_nom from tbm_loc where co_emp=? and co_loc=? and st_reg in ('A','P')";
        try{
            if (conn != null){
                pst = conn.prepareStatement(strSQL);
                pst.setLong(1, intCodEmp);
                pst.setLong(2, intCodLoc);
                
                rst = pst.executeQuery();
                if (rst.next()){
                    strLocal = rst.getString("tx_nom");
                } 
                
                if (pst!=null) 
                    pst.close();
                pst=null;
            }
        }catch(SQLException e){
            strLocal = objParSis.getNombreLocal();
        }catch(Exception e){
            strLocal = objParSis.getNombreLocal();            
        }
        
        /* obtener los datos del documento de origen de la orden de despacho (FACVEN, TRAINV, etc) */
        strSQL=" select c.co_emp, c.co_loc, c.co_tipdoc, c.co_doc, c.ne_numdoc as numFac, " +
                    " case when c.co_cli is null then a.co_clides else c.co_cli end as co_cli, " +
                    " case when c.tx_nomcli is null then a.tx_nomclides else c.tx_nomcli end as tx_nomcli, " +
                    " d.tx_deslar as tx_deslardoc " +
                    " from tbm_cabguirem as a " +
                    " inner join tbm_detguirem as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc) " +
                    " inner join tbm_cabmovinv as c on (b.co_emprel=c.co_emp and b.co_locrel=c.co_loc and b.co_tipdocrel=c.co_tipdoc and b.co_docrel=c.co_doc) " +
                    " inner join tbm_cabtipdoc as d on (d.co_emp=c.co_emp and d.co_loc=c.co_loc and d.co_tipdoc=c.co_tipdoc) " +
                    " where a.co_emp=? " +
                    " and a.co_loc=? " +
                    " and a.co_tipdoc=? " +
                    " and a.co_doc=? " +
                    " group by c.co_emp, c.co_loc, c.co_tipdoc, c.co_doc, c.ne_numdoc, c.co_cli, c.tx_nomcli, a.co_clides, a.tx_nomclides, d.tx_deslar " +
                    " order by c.co_doc ";
        try{
            if (conn != null){
                pst = conn.prepareStatement(strSQL);
                pst.setLong(1, intCodEmp);
                pst.setLong(2, intCodLoc);
                pst.setLong(3, intCodTipDoc);
                pst.setLong(4, intCodDoc);
                
                rst = pst.executeQuery();
                if (rst.next()){
                    intCodLocRel = rst.getInt("co_loc");
                    intCodDocRel = rst.getInt("co_tipdoc");
                    intNumDocRel = rst.getInt("numFac"); 
                    intCodCliRel = rst.getInt("co_cli"); 
                    strNomCli = rst.getString("tx_nomcli");       
                    strDesLarDoc = rst.getString("tx_deslardoc");                    
                } 
                
                if (pst!=null) 
                    pst.close();
                pst=null;
            }
        }catch(SQLException e){
            intCodDocRel = 0;
            intNumDocRel = 0; 
            strNomCli = "";       
            strDesLarDoc = "";  
        }catch(Exception e){
            intCodDocRel = 0;
            intNumDocRel = 0; 
            strNomCli = "";       
            strDesLarDoc = "";  
        }
        
        /*int intInd = strLocal.indexOf("(");
        if (intInd < 0)  
            intInd = strLocal.length();
        strLocal = strLocal.substring(0, intInd);
        strLocal = strLocal.replace('(', ' ');
        strLocal = strLocal.trim();*/
        
        strLocal =  strLocal.substring(0, ((strLocal.indexOf("(")<0)?strLocal.length():strLocal.indexOf("(")) ).replace('(', ' ').trim();
        
        //System.out.println(strLocal);
        
        /*strMensCorEle = "<br>" + strLocal  + "<br> LA ORDEN DE DESPACHO # "+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NE_NUMDOC).toString() + " CONTIENE ";         
        if (dblCanFal > 0) 
            strMensCorEle += "<br> " + (int)dblCanFal + " ITEM(S) FALTANTE(S) ";            
        if (dblCanDan > 0) 
            strMensCorEle += "<br> " + (int)dblCanDan + " ITEM(S) DA&Ntilde;ADO(S)   "; */
        
        if ((intCodLocRel == 5) || (intCodCliRel == 3516 || intCodCliRel == 603 || intCodCliRel == 1039 || intCodCliRel == 3117 || intCodCliRel == 498 || intCodCliRel == 886 || intCodCliRel == 2854 || intCodCliRel == 446 || intCodCliRel == 789 )) 
            intCodDocRel=0;
        
        strMensCorEle = " <HTML> <body> ";
        strMensCorEle+= " <h1>" + strLocal + "</h1> ";
        strMensCorEle+= " <h4>" + " ORDEN DE DESPACHO # "+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NE_NUMDOC).toString() + "</h4> ";
        if (!strDesLarDoc.equals("")){
            if (intCodDocRel != 0) 
                strMensCorEle+= " <h4>" + " REFERENTE A LA "+ strDesLarDoc.substring(0, ((strDesLarDoc.indexOf("(") < 0)?strDesLarDoc.length():strDesLarDoc.indexOf("("))).replace('(', ' ').trim().toUpperCase() + " # " + intNumDocRel + "</h4> ";
            
            strMensCorEle+= " <h4>" + " CLIENTE: "+ strNomCli.toUpperCase() + "</h4> ";
        }
        strMensCorEle+= " <br>" ;
        strMensCorEle+= " <TABLE BORDER=1 cellpadding=10> <TR> <th><FONT COLOR=\"blue\"> ITEM </FONT></th> <th><FONT COLOR=\"blue\"> DESCRIPCION </FONT></th> <th><FONT COLOR=\"blue\"> CAN.FAL </FONT></th> <th><FONT COLOR=\"blue\"> CAN.DAN </FONT></th> <th><FONT COLOR=\"blue\"> OBSERVACION </FONT></th> </TR> ";
        
        for (int i=0;i<objTblMod.getRowCountTrue();i++) {
            strObservacion="";
            if (objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0) {
                if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL) != null) 
                    canFal = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL).toString());

                if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN) != null) 
                    canDan = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN).toString());
                
                if (objTblMod.getValueAt(i,INT_TBL_DAT_OBS) != null) 
                    strObservacion = objTblMod.getValueAt(i,INT_TBL_DAT_OBS).toString();
                
                strMensCorEle+="<TR><TD> "+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString() +" </TD> <TD> "+ objTblMod.getValueAt(i,INT_TBL_DAT_DES_ITM).toString() +" </TD> <TD align=\"center\"> "+ ((canFal>0)?(int)canFal:"") +"</TD> <TD align=\"center\"> "+((canDan>0)?(int)canDan:"") +" </TD> <TD align=\"center\"> "+strObservacion.trim()+" </TD></TR>";                                
            }            
        }
        
        strMensCorEle+=" </TABLE> </body> </HMTL> ";
        
        if (intModOper==1) 
            this.strArrDirCor = this.strArrDirCorPro;
        
        /*for (int i = 0; i < strArrDirCor.length; i++){
            if (strArrDirCor[i][0].equals(String.valueOf(intCodEmp))) 
                if (strArrDirCor[i][1].equals(String.valueOf(intCodLoc)) ) 
                    if (strArrDirCor[i][2].equals(String.valueOf(objParSis.getCodigoMenu())) ){
                        if (strArrDirCor[i][4].equals(String.valueOf(intCodDocRel)) )
                            objInvItm.enviarCorreo(strArrDirCor[i][3], "Sistema Zafiro: Items Faltantes/Dañados", strMensCorEle );
                        else if (strArrDirCor[i][4].equals("0") )
                            objInvItm.enviarCorreo(strArrDirCor[i][3], "Sistema Zafiro: Items Faltantes/Dañados", strMensCorEle );
                    } 
        } */       
        
        for (int i = 0; i < strArrDirCor.length; i++){
            if (strArrDirCor[i][0].equals(String.valueOf(intCodEmp))) 
                if (strArrDirCor[i][1].equals(String.valueOf(intCodLoc)) ) 
                    if (strArrDirCor[i][2].equals(String.valueOf(objParSis.getCodigoMenu())) ){
                        if (strArrDirCor[i][4].equals(String.valueOf(intCodDocRel)) )
                            strDirCorreos=strDirCorreos+strArrDirCor[i][3] + ";";
                        else if (strArrDirCor[i][4].equals("0") )
                            strDirCorreos=strDirCorreos+strArrDirCor[i][3] + ";";
                    } 
        }        
        
        objInvItm.enviarCorreoMasivo(strDirCorreos, "Sistema Zafiro: Items Faltantes/Dañados", strMensCorEle);
        
        /*strSQL =  " select tx_corele from tbm_coreleprgmnu where co_emp = ? and co_loc = ? and co_mnu = ? and st_reg = 'A' ";          
        try{
            if (conn != null){
                pst = conn.prepareStatement(strSQL);
                pst.setLong(1, objParSis.getCodigoEmpresa());
                pst.setLong(2, objParSis.getCodigoLocal());
                pst.setLong(3, objParSis.getCodigoMenu());
                
                rst = pst.executeQuery();
                
                while(rst.next()){
                    objInvItm.enviarCorreo(rst.getString("tx_corele"), "Sistema Zafiro: Items Faltantes/Dañados", strMensCorEle );
                }
                
                if (rst!=null) 
                    rst.close();
                rst=null;
                
                if (pst!=null) 
                    pst.close();
                pst=null;
            }
        }catch (SQLException e){
            e.toString();
        }catch (Exception e){
            e.toString();            
        }*/        
    }
    
    private void generarOrdenConteo(Connection conn){
        boolean blnActTipDoc=false;
        
        int intCodUsrMod=0, intCodDocOrdCon, intNumDocOrdCon, intCodUsrRes, co_regConInv, co_usrResConInv, intCoReg=0, intCodItmGrp=0;
        double canFal=0, canDan=0;
        dblCanFal=0;
        dblCanDan=0;
        objComOrdCon = new ZafComOrdCon(ZafCom78_02.this, objParSis);
        objComOrdCon.setParams(this.intCodEmp, this.intCodLoc, this.intCodTipDoc, this.intCodDoc, this.intCodBod, this.intCodBodGrp);
        //objOrdCon = new ZafComOrdCon(ZafCom78_02.this, objParSis);
        //objOrdCon.setParams(this.intCodEmp, this.intCodLoc, this.intCodTipDoc, this.intCodDoc, this.intCodBod, this.intCodBodGrp);
        /*if (objOrdCon.generaOrdenConteo() == false){
            mostrarMsgErr("No se pudo crear la orden de conteo");
        }*/
        
        try{
            if (conn!=null){
                //if (objOrdCon.getCodEmpCodBodGrp(conn, this.intCodEmp, this.intCodBodGrp)){
                
                    //objOrdCon.setCodEmpGrp(0); 
                    //objOrdCon.setCodBodGrp(1);
                    objComOrdCon.setCodEmpGrp(0); 
                    objComOrdCon.setCodBodGrp(1);
                    
                    //intCodDocOrdCon = objOrdCon.getMaxCodDocOrdConInv(conn, objOrdCon.intCodEmpGrp, objOrdCon.intCodBodGrp, objOrdCon.intCodTipDoc);
                    //intNumDocOrdCon = objOrdCon.getMaxNumDocOrdConInv(conn, objOrdCon.intCodEmpGrp, objOrdCon.intCodBodGrp, objOrdCon.intCodTipDoc);
                    //intCodUsrRes = objOrdCon.getUsrResConOrdConInv(conn, objOrdCon.intCodEmpGrp, objOrdCon.intCodBodGrp, objOrdCon.intCodTipDoc, intCodDocOrdCon);
                    
                    //intCodDocOrdCon = objOrdCon.getMaxCodDocOrdConInv(conn, objOrdCon.getCodEmpGrp(), objOrdCon.getCodBodGrp(), objOrdCon.getCodTipDoc());
                    //intNumDocOrdCon = objOrdCon.getMaxNumDocOrdConInv(conn, objOrdCon.getCodEmpGrp(), objOrdCon.getCodBodGrp(), objOrdCon.getCodTipDoc());
                    intCodDocOrdCon = objComOrdCon.getMaxCodDocOrdConInv(conn, objComOrdCon.getCodEmpGrp(), objComOrdCon.getCodBodGrp(), objComOrdCon.getCodTipDoc());
                    intNumDocOrdCon = objComOrdCon.getMaxNumDocOrdConInv(conn, objComOrdCon.getCodEmpGrp(), objComOrdCon.getCodBodGrp(), objComOrdCon.getCodTipDoc());
                    
                    //objOrdCon.getUsrResConOrdConInv(conn, objOrdCon.getCodEmpGrp(), objOrdCon.getCodBodGrp(), objOrdCon.getCodTipDoc(), intCodDocOrdCon);
                    
                    //objOrdCon.setUsrResConOrdConInv(conn);
                    //intCodUsrRes = objOrdCon.getCodUsrResCon();
                    objComOrdCon.setUsrResConOrdConInv(conn);
                    intCodUsrRes = objComOrdCon.getCodUsrResCon();
                    intCodDocOrdCon++;
                    intNumDocOrdCon++; 
                    
                    //co_regConInv = objOrdCon.getMaxCodDocConInv(conn, objOrdCon.getCodEmpGrp()); 
                    co_regConInv = objComOrdCon.getMaxCodDocConInv(conn, objComOrdCon.getCodEmpGrp()); 
                    //co_usrResConInv = objOrdCon.getCodUsrResConInv(conn, objOrdCon.intCodEmpGrp, co_regConInv);

                    //if (objOrdCon.insertarCabOrdConInv(conn, objOrdCon.getCodEmpGrp(), objOrdCon.getCodBodGrp(), objOrdCon.getCodTipDoc(), intCodDocOrdCon, intNumDocOrdCon, intCodUsrRes, this.intCodBodGrp, "", "", "A", objParSis.getCodigoUsuario(), intCodUsrMod, "I")){
                    if (objComOrdCon.insertarCabOrdConInv(conn, objComOrdCon.getCodEmpGrp(), objComOrdCon.getCodBodGrp(), objComOrdCon.getCodTipDoc(), intCodDocOrdCon, intNumDocOrdCon, intCodUsrRes, this.intCodBodGrp, "", "", "A", objParSis.getCodigoUsuario(), intCodUsrMod, "I")){
                        for (int i=0;i<objTblMod.getRowCountTrue();i++)  {
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0) {
                                if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL) != null) {
                                    canFal = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL).toString());                                    
                                }

                                if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN) != null) {
                                    canDan = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN).toString());                                    
                                }
                                
                                if ((canFal+canDan) > 0){                                
                                    intCoReg++;
                                    //intCodItmGrp=objOrdCon.getCodItmGrp(conn, objOrdCon.getCodEmpGrp(), objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString());
                                    intCodItmGrp=objComOrdCon.getCodItmGrp(conn, objComOrdCon.getCodEmpGrp(), objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString());
                                    //if (objOrdCon.insertarDetOrdConInv(conn, objOrdCon.getCodEmpGrp(), objOrdCon.getCodBodGrp(), objOrdCon.getCodTipDoc(), intCodDocOrdCon, intCoReg, intCodItmGrp, "I")){
                                    if (objComOrdCon.insertarDetOrdConInv(conn, objComOrdCon.getCodEmpGrp(), objComOrdCon.getCodBodGrp(), objComOrdCon.getCodTipDoc(), intCodDocOrdCon, intCoReg, intCodItmGrp, "I")){
                                        co_regConInv++;
                                        //if ((objOrdCon.insertarTbmConInv(conn, objOrdCon.intCodEmpGrp, co_regConInv, co_usrResConInv, objOrdCon.intCodBodGrp, intCodItmGrp, intCodLoc, intCodTipDoc, intCodDoc, Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG).toString()), 0, 0, 0, 0, "N", "", "I", 0))){
                                        //if ((objOrdCon.insertarTbmConInv(conn, objOrdCon.getCodEmpGrp(), co_regConInv, intCodUsrRes, objOrdCon.getCodBodGrp(), intCodItmGrp))){
                                        if ((objComOrdCon.insertarTbmConInv(conn, objComOrdCon.getCodEmpGrp(), co_regConInv, intCodUsrRes, objComOrdCon.getCodBodGrp(), intCodItmGrp))){
                                            blnActTipDoc=true;                                        
                                        }else{
                                            blnActTipDoc=false;
                                            conn.rollback();
                                            mostrarMsgErr("No se pudo crear la orden de conteo");
                                            break;
                                        } 
                                    }else { 
                                        blnActTipDoc=false;
                                        conn.rollback();
                                        mostrarMsgErr("No se pudo crear la orden de conteo");
                                        break;
                                    } 
                                } 
                            } 
                        } 
                        
                        if (blnActTipDoc){ 
                            //if (objOrdCon.actualizarCabTipDoc(conn, objOrdCon.getCodEmpGrp(), objOrdCon.getCodBodGrp(), objOrdCon.getCodTipDoc())){
                            if (objComOrdCon.actualizarCabTipDoc(conn, objComOrdCon.getCodEmpGrp(), objComOrdCon.getCodBodGrp(), objComOrdCon.getCodTipDoc())){
                                conn.commit();                                
                            }else{ 
                                conn.rollback();                    
                                mostrarMsgErr("No se pudo crear la orden de conteo");
                            } 
                        } 
                    }else{ 
                        conn.rollback();
                        mostrarMsgErr("No se pudo crear la orden de conteo");
                    } 
                /*}else{ 
                    conn.rollback();
                    mostrarMsgErr("No se pudo crear la orden de conteo");
                } */
            }
        }catch(SQLException e) { 
            objUti.mostrarMsgErr_F1(this, e);
            mostrarMsgErr("No se pudo crear la orden de conteo");
        }catch(Exception e) { 
            objUti.mostrarMsgErr_F1(this, e);
            mostrarMsgErr("No se pudo crear la orden de conteo");
        }finally{
            ;
        }
    }
    
    private void setModoOperacion(){
        String strConexion = objParSis.getStringConexion();
        String ip = "";
        strConexion = strConexion.substring(0, strConexion.lastIndexOf(":"));
        strConexion = strConexion.substring(strConexion.lastIndexOf(":") + 1, strConexion.length());
        strConexion = strConexion.replace("/", "").trim();
        ip = strConexion;
        if (ip.equals(this.strIpProd)) 
            this.intModOper=1;
        else 
            this.intModOper=0;        
    }
    
    private void verificaCambios(){
        double dblCanFal=0, dblCanDan=0, dblTot=0;
        
        for (int i=0;i<objTblMod.getRowCountTrue();i++)  {
            if (objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0) {
                if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_FAL) != null) 
                    dblCanFal = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_FAL).toString()); 
                if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_DAN) != null)
                    dblCanDan = objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_DAN).toString()); 
                dblTot=dblTot+dblCanFal+dblCanDan;
                if (dblTot > 0) 
                    blnHayCambios=true;
            }
        }
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {        
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg) {        
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.ERROR_MESSAGE);
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
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 484));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

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

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setText("Aceptar");
        butAce.setEnabled(false);
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:
        String strMsg = "¿Esta seguro que desea modificar la informacion?";
        this.verificaCambios();
        if (blnHayCambios){
            if (mostrarMsgCon(strMsg)==JOptionPane.YES_OPTION) 
                if (objThrGUI == null) {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
        }
        setVisible(false);
        dispose();
        
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formWindowClosing
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
        
    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar.
     * <LI>1: Click en el botón Aceptar.
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getOpcSelDlg() {
        return intOpcSelDlg;
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
    private class ZafThreadGUI extends Thread {
        
        public ZafThreadGUI() {
            
        }
        
        @Override
        public void run() {
            blnResp=actualizarReg();
            if (blnResp) {                
                mostrarMsgInf("La operacion registrar faltantes/dañados se realizó con exito.");
            }
            objThrGUI=null;
        }        
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends MouseMotionAdapter {        
        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Codigo del Item";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Codigo Alterno del Item";
                    break;
                case INT_TBL_DAT_DES_ITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de Medida";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad Solicitada";
                    break;
                case INT_TBL_DAT_CAN_PEN:
                    strMsg="Cantidad Pendiente de confirmar";
                    break;
                case INT_TBL_DAT_CAN_FAL:
                    strMsg="Cantidad Faltante";
                    break;
                case INT_TBL_DAT_CAN_DAN:
                    strMsg="Cantidad Dañados";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observacion";
                    break;
                case INT_TBL_DAT_BUT_OBS:
                    strMsg="";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}

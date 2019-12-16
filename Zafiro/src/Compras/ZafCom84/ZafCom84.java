/*
 * ZafCom84.java  
 *
 * Created on Jun 01, 2016, 17:00:03 
 */
package Compras.ZafCom84;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Tony Sanginez
 */
public class ZafCom84 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Jtable: Tabla de Datos
    private static final int INT_TBL_LIN = 0;                                   //Línea
    private static final int INT_TBL_CODSEG = 1;                                //Código de Seguimiento
    private static final int INT_TBL_CODREG = 2;                                //Código de Registro
    private static final int INT_TBL_CODEMPCOT = 3;                             //Código Empresa cotizacion
    private static final int INT_TBL_CODLOCCOT = 4;                             //Código Local cotizacion
    private static final int INT_TBL_LOCCOT = 5;                                //Nombre del Local cotizacion
    private static final int INT_TBL_CODCOT = 6;                                //Código Cotización
    private static final int INT_TBL_BUTCOT = 7;                                //Muestra  Cotización
    
    private static final int INT_TBL_CODEMPSOL = 8;                             //Código de la empresa de la solicitud
    private static final int INT_TBL_CODLOCSOL = 9;                             //Código local de la solicitud
    private static final int INT_TBL_CODTIPDOCSOL = 10;                         //Código tipo de documento de la solicitud
    private static final int INT_TBL_DESCORTIPDOCSOL = 11;                      //Descripcion corta del documento de la solicitud
    private static final int INT_TBL_DESLARTIPDOCSOL = 12;                      //Descripcion larga del documento de la solicitud
    private static final int INT_TBL_CODDOCSOL = 13;                            //Código de documento de la solicitud
    private static final int INT_TBL_NUMDOCSOL = 14;                            //Numero del documento de la solicitud
    private static final int INT_TBL_FECDOCSOL = 15;                            //Fecha del documento de la solicitud
    private static final int INT_TBL_BODORGSOL = 16;                            //Bodega Origen
    private static final int INT_TBL_BODDESSOL = 17;                            //Bodega Destino
    private static final int INT_TBL_PESSOL = 18;                               //Peso
    
    private static final int INT_TBL_BUTSOLTRAINV = 19;                         //Muestra la solicitud  de tranferencia de inventario
    
    private static final int INT_TBL_CODITM = 20;                               //Codigo de item
    private static final int INT_TBL_CODALTITM = 21;                            //Codigo alterno item
    private static final int INT_TBL_CODALTDOS = 22;                            //Código alterno 2 de Item.
    private static final int INT_TBL_NOMITM = 23;                               //Nombre item
    private static final int INT_TBL_UNIMED = 24;                               //Unidad de Medida.
    private static final int INT_TBL_CANITM = 25;                               //Cantidad item
    private static final int INT_TBL_PESUNIITM = 26;                            //Peso unidad item
    private static final int INT_TBL_CANPENITM  = 27;                           //Cantidad pendiente item
    private static final int INT_TBL_CANCONFITM = 28;                           //Cantidad confirmada item
    private static final int INT_TBL_CANTRAITM = 29;                            //Cantidad en transito item

    private static final int INT_TBL_BUT_ITM = 30;                              //Muestra columna.
    
    private static final int INT_TBL_CODEMPMOVINV = 31;                         //Código de empresa movimiento de inventario
    private static final int INT_TBL_CODLOCMOVINV = 32;                         //Código local movimiento de invntario
    private static final int INT_TBL_CODTIPDOCMOVINV = 33;                      //Código tipo de dcoumento movimiento de inventario
    private static final int INT_TBL_DESCORTIPDOCMOVINV = 34;                   //Descripcion corta tipo de dcoumento movimiento de inventario
    private static final int INT_TBL_DESLARTIPDOCMOVINV = 35;                   //Descripcion larga tipo de dcoumento movimiento de inventario
    private static final int INT_TBL_CODDOCMOVINV = 36;                         //Código de documento movimiento de inventario
    private static final int INT_TBL_NUMDOCMOVINV = 37;                         //Numero de documento movimiento de inventario
    private static final int INT_TBL_BUTMOVINV = 38;                            //Muestra documento de inventario
    
    private static final int INT_TBL_CODEMPODGUI = 39;                          //Código de empresa od y guias
    private static final int INT_TBL_CODLOCODGUI = 40;                          //Código local od y guias
    private static final int INT_TBL_CODTIPDOCODGUI = 41;                       //Código tipo de documento od y guias
    private static final int INT_TBL_DESCORTIPDOCODGUI = 42;                    //Descripcion corta tipo de documento od y guias
    private static final int INT_TBL_DESLARTIPDOCODGUI = 43;                    //Descripcion larga tipo de documento od y guias
    private static final int INT_TBL_CODDOCODGUI = 44;                          //Código de documento od y guias
    private static final int INT_TBL_NUMDOCODGUI = 45;                          //Numero de documento od y guias
    private static final int INT_TBL_BUTODGUI = 46;                             //Muestra la orden de despacho o guia de remision
    
    private static final int INT_TBL_CODEMPCONF = 47;                           //Código de empresa confirmacione
    private static final int INT_TBL_CODLOCCONF = 48;                           //Código local confirmaciones
    private static final int INT_TBL_CODTIPDOCCONF = 49;                        //codigo tipo de documento confirmaciones
    private static final int INT_TBL_DESCORTIPDOCCONF = 50;                     //Desripcion corta tipo de documento confirmaciones
    private static final int INT_TBL_DESLARTIPDOCCONF = 51;                     //Desripcion larga tipo de documento confirmaciones
    private static final int INT_TBL_CODDOCCONF = 52;                           //Código de documento confirmaciones
    private static final int INT_TBL_NUMDOCCONF = 53;                           //Numero de documento confirmaciones
    private static final int INT_TBL_CODBODGRPCONF = 54;                        //Código de Bodega Grupo Confirmación.
    private static final int INT_TBL_NATDOCTIPDOCCONF = 55;                     //Naturaleza Tipo Documento de Confirmación.
    private static final int INT_TBL_CODMNUDOCCONF = 56;                        //Código de Menú Confirmación.
    private static final int INT_TBL_BUTCONF = 57;                              //Muestra la confirmacion  de inventario
    
    private static final int INT_TBL_CHKPRO = 58;                               //Proceso Completo

    //JTable: Tabla de Bodega Origen.
    private static final int INT_TBL_BODORI_LIN = 0;
    private static final int INT_TBL_BODORI_CHKBOD = 1;
    private static final int INT_TBL_BODORI_CODBOD = 2;
    private static final int INT_TBL_BODORI_NOMBOD = 3;

    //JTable: Tabla de Bodega Destino.
    private static final int INT_TBL_BODDES_LIN = 0;
    private static final int INT_TBL_BODDES_CHKBOD = 1;
    private static final int INT_TBL_BODDES_CODBOD = 2;
    private static final int INT_TBL_BODDES_NOMBOD = 3;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBodOri, objTblModBodDes;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblNum, objTblCelRenLblColDat; //Render: Presentar JLabel en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafThreadGUI objThrGUI;
    private ZafTblPopMnu objTblPopMnu;
    private ZafPerUsr objPerUsr;                                                //Permisos Usuarios.
    private ZafVenCon vcoItm;                                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    JTextField txtCodItm = new JTextField();
    private final java.awt.Color colFonColNar = new java.awt.Color(255, 221, 187);
    private final java.awt.Color colFonColVer = new java.awt.Color(228, 228, 203);
    private Vector vecDat, vecCab, vecReg, vecAux, vecEstReg;
    private boolean blnMarTodChkTblBodOri = true;                               //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnMarTodChkTblBodDes = true;                               //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnVenEmeCon=false;                                         //Ventana Emergente de Consulta. true: Consulta el seguimiento de acuerdo a la solicitud indicada.
    private String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";   //Utilizadas para la pk de la solicitud de transferencia.
    private String strSQL = "", strAux="";
    private String strCodItm="", strCodAlt="", strCodLetItm="",  strNomItm="";
    private String strVersion = " v0.1 ";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom84(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
            if (!configurarFrm()) 
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Constructor para Botón Consultar Seguimiento de Solicitud de Transferencia de Inventario */
    public ZafCom84(HashMap map) 
    {
        try 
        {
            blnVenEmeCon=true;
            
            ZafParSis obj  = (ZafParSis)map.get("objParSis");
            this.strCodEmp  = (String)map.get("strCodEmp");
            this.strCodLoc  = (String)map.get("strCodLoc");
            this.strCodTipDoc  = (String)map.get("strCodTipDoc");
            this.strCodDoc     = (String)map.get("strCodDoc");
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            cargarDetReg(sqlConFil());
            tabFrm.setSelectedIndex(1);
            tabFrm.removeTabAt(0); 
            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText("Seguimiento de solicitudes de transferencias de inventario (Formato 2: Detalle)...");
        } 
        catch (CloneNotSupportedException e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }        
            
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti = new ZafUtil();
            objTblCelEdiChk = new ZafTblCelEdiChk();
            objTblCelEdiButGenDG1 = new ZafTblCelEdiButGen();
            objTblCelRenButDG1 = new ZafTblCelRenBut();

            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
                
            //Configurar ZafSelFec
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            panFilCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            configurarFechaDesde();
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3342)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3343)) 
            {
                butCer.setVisible(false);
            }
            //Configurar Combo.
            configurarComboEstReg();
             
            //Configurar ZafVenCon
            configurarVenConItm();
            
            //Configurar los JTables.
            configurarTblBodOri();
            configurarTblBodDes();
            configurarTblDat();
            
            //Carga Bodegas
            cargarBodOri();
            cargarBodDes();
            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }

    /* Configurar Fecha Desde 1 mes atras. */
    private void configurarFechaDesde()
    {
        ZafDatePicker txtFecDoc;
        txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
        txtFecDoc.setPreferredSize(new Dimension(70, 20));
        txtFecDoc.setHoy();
        Calendar objFec = Calendar.getInstance();
        ZafDatePicker dtePckPag =  new ZafDatePicker(new JFrame(),"d/m/y");
        int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
        if (fecDoc!=null)
        {
            objFec.set(Calendar.DAY_OF_MONTH, fecDoc[0]);
            objFec.set(Calendar.MONTH, fecDoc[1] - 1);
            objFec.set(Calendar.YEAR, fecDoc[2]);
        }
        Calendar objFecPagActual = Calendar.getInstance();
        objFecPagActual.setTime(objFec.getTime());
        objFecPagActual.add(Calendar.DATE, -30 );

        dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH)+1);
        dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
        String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
        java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
        objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
    }
    
    private boolean configurarComboEstReg() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("P");
            vecEstReg.add("D");
            vecEstReg.add("I");
            vecEstReg.add("V");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("A: Autorizadas");
            cboEstReg.addItem("P: Pendientes de Autorización");
            cboEstReg.addItem("D: Denegadas");
            cboEstReg.addItem("I: Anuladas");
            cboEstReg.addItem("V: Preautorizadas por Venta");
            cboEstReg.setSelectedIndex(0); 
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    private boolean configurarTblDat()
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN, "");
            vecCab.add(INT_TBL_CODSEG, "Cód.Seg.");
            vecCab.add(INT_TBL_CODREG, "Cód.Reg.");
            vecCab.add(INT_TBL_CODEMPCOT, "Cód.Emp.");
            vecCab.add(INT_TBL_CODLOCCOT, "Cód.Loc.");
            vecCab.add(INT_TBL_LOCCOT, "Local");
            vecCab.add(INT_TBL_CODCOT, "Núm.Cot.");
            vecCab.add(INT_TBL_BUTCOT, "...");
            vecCab.add(INT_TBL_CODEMPSOL, "Cód.Emp.");
            vecCab.add(INT_TBL_CODLOCSOL, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTIPDOCSOL, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DESCORTIPDOCSOL, "Tip.Doc.");
            vecCab.add(INT_TBL_DESLARTIPDOCSOL, "Tipo de documento");
            vecCab.add(INT_TBL_CODDOCSOL, "Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOCSOL, "Núm.Doc.");
            vecCab.add(INT_TBL_FECDOCSOL, "Fec.Doc.");
            vecCab.add(INT_TBL_BODORGSOL, "Bodega Origen");
            vecCab.add(INT_TBL_BODDESSOL, "Bodega Destino");
            vecCab.add(INT_TBL_PESSOL, "Peso (Kg)");            
            vecCab.add(INT_TBL_BUTSOLTRAINV, "...");
            
            vecCab.add(INT_TBL_CODITM, "Cód.Itm.");
            vecCab.add(INT_TBL_CODALTITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_CODALTDOS, "Cód.Alt.2");
            vecCab.add(INT_TBL_NOMITM, "Ítem");
            vecCab.add(INT_TBL_UNIMED, "Uni.Med.");
            vecCab.add(INT_TBL_CANITM, "Cantidad");
            vecCab.add(INT_TBL_PESUNIITM, "Pes.Uni.Itm.");
            vecCab.add(INT_TBL_CANPENITM, "Can.Pen.");
            vecCab.add(INT_TBL_CANCONFITM, "Can.Con.");
            vecCab.add(INT_TBL_CANTRAITM, "Can.Tra.");          
            vecCab.add(INT_TBL_BUT_ITM, "...");            
            
            vecCab.add(INT_TBL_CODEMPMOVINV, "Cód.Emp.");
            vecCab.add(INT_TBL_CODLOCMOVINV, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTIPDOCMOVINV, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DESCORTIPDOCMOVINV, "Tip.Doc.");
            vecCab.add(INT_TBL_DESLARTIPDOCMOVINV, "Tipo de documento");
            vecCab.add(INT_TBL_CODDOCMOVINV, "Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOCMOVINV, "Núm.Doc.");
            vecCab.add(INT_TBL_BUTMOVINV, "...");
            vecCab.add(INT_TBL_CODEMPODGUI, "Cód.Emp.");
            vecCab.add(INT_TBL_CODLOCODGUI, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTIPDOCODGUI, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DESCORTIPDOCODGUI, "Tip.Doc.");
            vecCab.add(INT_TBL_DESLARTIPDOCODGUI, "Tipo de documento");
            vecCab.add(INT_TBL_CODDOCODGUI, "Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOCODGUI, "Núm.Doc.");
            vecCab.add(INT_TBL_BUTODGUI, "...");
            vecCab.add(INT_TBL_CODEMPCONF, "Cód.Emp.");
            vecCab.add(INT_TBL_CODLOCCONF, "Cód.Loc.");
            vecCab.add(INT_TBL_CODTIPDOCCONF, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DESCORTIPDOCCONF, "Tip.Doc.");
            vecCab.add(INT_TBL_DESLARTIPDOCCONF, "Tipo de documento");
            vecCab.add(INT_TBL_CODDOCCONF, "Cód.Doc.");
            vecCab.add(INT_TBL_NUMDOCCONF, "Núm.Doc.");
            vecCab.add(INT_TBL_CODBODGRPCONF, "Cód.Bod.Grp.");
            vecCab.add(INT_TBL_NATDOCTIPDOCCONF, "Nat.Doc.Conf.");
            vecCab.add(INT_TBL_CODMNUDOCCONF, "Cód.Mnu.Conf.");
            vecCab.add(INT_TBL_BUTCONF, "...");
            vecCab.add(INT_TBL_CHKPRO, "PROCESO");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat, INT_TBL_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat);

            //Libero los objetos auxiliares.
            new ZafTblOrd(tblDat);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODREG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODEMPCOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODLOCCOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOCCOT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CODCOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_BUTCOT).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_CODEMPSOL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODLOCSOL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODTIPDOCSOL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCSOL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCSOL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODDOCSOL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NUMDOCSOL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FECDOCSOL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_BODORGSOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BODDESSOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_PESSOL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTSOLTRAINV).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODALTITM).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_CODALTDOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_UNIMED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PESUNIITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANPENITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANCONFITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CANTRAITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUT_ITM).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_CODEMPMOVINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODLOCMOVINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODTIPDOCMOVINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCMOVINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCMOVINV).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODDOCMOVINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NUMDOCMOVINV).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUTMOVINV).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_CODEMPODGUI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODLOCODGUI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODTIPDOCODGUI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCODGUI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCODGUI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODDOCODGUI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NUMDOCODGUI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUTODGUI).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_CODEMPCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODLOCCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODTIPDOCCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCCONF).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCCONF).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODDOCCONF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NUMDOCCONF).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CODBODGRPCONF).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_NATDOCTIPDOCCONF).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODMNUDOCCONF).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_BUTCONF).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_CHKPRO).setPreferredWidth(80);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_BUTCOT);
            vecAux.add("" + INT_TBL_BUTSOLTRAINV);
            vecAux.add("" + INT_TBL_BUT_ITM);
            vecAux.add("" + INT_TBL_BUTMOVINV);
            vecAux.add("" + INT_TBL_BUTODGUI);
            vecAux.add("" + INT_TBL_BUTCONF);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

            //Columnas Ocultas
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPCOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOCCOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOCSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOCSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOCSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPODGUI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOCODGUI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOCODGUI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOCODGUI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPCONF, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOCCONF, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOCCONF, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOCCONF, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_NATDOCTIPDOCCONF, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODMNUDOCCONF, tblDat);

            //Color Filas
            objTblCelRenLblColDat = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_CODSEG).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODREG).setCellRenderer(objTblCelRenLblColDat);

            tcmAux.getColumn(INT_TBL_CODEMPCOT).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODLOCCOT).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_LOCCOT).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODCOT).setCellRenderer(objTblCelRenLblColDat);

            tcmAux.getColumn(INT_TBL_CODEMPSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODLOCSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODTIPDOCSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODDOCSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_NUMDOCSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_FECDOCSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_BODORGSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_BODDESSOL).setCellRenderer(objTblCelRenLblColDat);

            tcmAux.getColumn(INT_TBL_CODITM).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODALTITM).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODALTDOS).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_NOMITM).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_UNIMED).setCellRenderer(objTblCelRenLblColDat);
            
            tcmAux.getColumn(INT_TBL_CODEMPMOVINV).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODLOCMOVINV).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODTIPDOCMOVINV).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCMOVINV).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCMOVINV).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODDOCMOVINV).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_NUMDOCMOVINV).setCellRenderer(objTblCelRenLblColDat);

            tcmAux.getColumn(INT_TBL_CODEMPODGUI).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODLOCODGUI).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODTIPDOCODGUI).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCODGUI).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCODGUI).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODDOCODGUI).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_NUMDOCODGUI).setCellRenderer(objTblCelRenLblColDat);

            tcmAux.getColumn(INT_TBL_CODEMPCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODLOCCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODTIPDOCCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOCCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOCCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODDOCCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_NUMDOCCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODBODGRPCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_NATDOCTIPDOCCONF).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_CODMNUDOCCONF).setCellRenderer(objTblCelRenLblColDat);
            
            objTblCelRenLblColDat.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Combina los colores de acuerdo al codigo de seguimiento.
                    int intCell=objTblCelRenLblColDat.getRowRender();
                    int intNumSeg=Integer.parseInt(tblDat.getValueAt(intCell, INT_TBL_CODSEG).toString());
                    //System.out.println("intNumSeg:"+intNumSeg);
                    
                    if(esImpar(intNumSeg))
                    {
                        objTblCelRenLblColDat.setBackground(colFonColNar);
                        objTblCelRenLblColDat.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColDat.setBackground(colFonColVer);
                        objTblCelRenLblColDat.setForeground(Color.BLACK);
                    }
                }
            });
            
            tcmAux.getColumn(INT_TBL_CHKPRO).setCellRenderer(objTblCelRenLblColDat);
            
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_PESSOL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANITM, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PESUNIITM, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANPENITM, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANCONFITM, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTRAITM, objTblMod.INT_COL_DBL, new Integer(0), null);
            
           
            //Formato
            objTblCelRenLblNum = new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(objTblCelRenLblNum.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_PESSOL).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_CANITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_PESUNIITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_CANPENITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_CANCONFITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_CANTRAITM).setCellRenderer(objTblCelRenLblNum);
            
            objTblCelRenLblNum.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Combina los colores de acuerdo al codigo de seguimiento.
                    int intCell=objTblCelRenLblNum.getRowRender();
                    int intNumSeg=Integer.parseInt(tblDat.getValueAt(intCell, INT_TBL_CODSEG).toString());
                    //System.out.println("intNumSeg:"+intNumSeg);
                    
                    if(esImpar(intNumSeg))
                    {
                        objTblCelRenLblNum.setBackground(colFonColNar);
                        objTblCelRenLblNum.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblNum.setBackground(colFonColVer);
                        objTblCelRenLblNum.setForeground(Color.BLACK);
                    }
                }
            });
                        
            //Establecer: Check
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKPRO).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.setBackground(colFonColNar);
            objTblCelRenChk = null;

            tcmAux.getColumn(INT_TBL_CHKPRO).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPCONF) != null)
                        {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_CHKPRO);
                        }
                        else 
                        {
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_CHKPRO);
                        }
                    }

                }
            });
            objTblCelRenChk = null;

            //Establecer Botón.
            tcmAux.getColumn(INT_TBL_BUTCOT).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTSOLTRAINV).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUT_ITM).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTMOVINV).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTODGUI).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTCONF).setCellRenderer(objTblCelRenButDG1);

            objTblCelRenButDG1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter()
            {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButDG1.getColumnRender()) 
                    {
                        case INT_TBL_BUTCOT:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTSOLTRAINV:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUT_ITM:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTMOVINV:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTODGUI:
                            objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTCONF:
                            objTblCelRenButDG1.setText("...");
                            break;
                    }
                }
            });

            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTCOT).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTSOLTRAINV).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUT_ITM).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTMOVINV).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTODGUI).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTCONF).setCellRenderer(objTblCelRenButDG1);

            objTblCelRenButDG1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() 
            {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButDG1.getColumnRender()) 
                    {
                        case INT_TBL_BUTCOT:
                            if (objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_CODEMPCOT) == null) 
                                objTblCelRenButDG1.setText("");
                            else 
                                objTblCelRenButDG1.setText("...");
                            break;

                        case INT_TBL_BUTSOLTRAINV:
                            if (objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_CODEMPSOL) == null) 
                                objTblCelRenButDG1.setText("");
                            else 
                                objTblCelRenButDG1.setText("...");
                            break;
                            
                        case INT_TBL_BUT_ITM:                            
                                objTblCelRenButDG1.setText("");
                            break;

                        case INT_TBL_BUTMOVINV:
                            if ((objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_CODEMPMOVINV) == null))
                                objTblCelRenButDG1.setText("");
                            else
                                objTblCelRenButDG1.setText("...");
                            break;

                        case INT_TBL_BUTODGUI:
                            if ((objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_CODEMPODGUI) == null))
                                objTblCelRenButDG1.setText("");
                            else 
                                objTblCelRenButDG1.setText("...");
                            break;
                        case INT_TBL_BUTCONF:
                            if ((objTblMod.getValueAt(objTblCelRenButDG1.getRowRender(), INT_TBL_CODEMPCONF) == null)) 
                                objTblCelRenButDG1.setText("");
                            else
                                objTblCelRenButDG1.setText("...");
                            break;
                    }
                }
            });

            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTCOT).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTSOLTRAINV).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUT_ITM).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTMOVINV).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTODGUI).setCellEditor(objTblCelEdiButGenDG1);
            tcmAux.getColumn(INT_TBL_BUTCONF).setCellEditor(objTblCelEdiButGenDG1);

            objTblCelEdiButGenDG1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_BUTCOT:
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPCOT) == null) 
                                    objTblCelEdiButGenDG1.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUTSOLTRAINV:
                                if ((objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPSOL) == null)) 
                                    objTblCelEdiButGenDG1.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUT_ITM:
                                    objTblCelEdiButGenDG1.setCancelarEdicion(true);
                                break;   
                            case INT_TBL_BUTMOVINV:
                                if ((objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPMOVINV) == null)) 
                                    objTblCelEdiButGenDG1.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUTODGUI:
                                if ((objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPODGUI) == null)) 
                                    objTblCelEdiButGenDG1.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUTCONF:
                                if ((objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPCONF) == null))
                                    objTblCelEdiButGenDG1.setCancelarEdicion(true);
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_BUTCOT:
                                llamarCotVen(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPCOT).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOCCOT).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODCOT).toString() );
                                break;
                            case INT_TBL_BUTSOLTRAINV:
                                llamarSolTraInv(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPSOL).toString(),  objTblMod.getValueAt(intFilSel, INT_TBL_CODLOCSOL).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOCSOL).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOCSOL).toString() );
                                break;
                            case INT_TBL_BUT_ITM:
                                //llamarSolTraInv(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPSOL).toString(),  objTblMod.getValueAt(intFilSel, INT_TBL_CODLOCSOL).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOCSOL).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOCSOL).toString() );
                                break;
                            case INT_TBL_BUTMOVINV:
                                llamarMovInv(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPMOVINV).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOCMOVINV).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOCMOVINV).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOCMOVINV).toString());
                                break;
                            case INT_TBL_BUTODGUI:
                                llamarPantGuia(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPODGUI).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOCODGUI).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOCODGUI).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOCODGUI).toString());
                                break;
                            case INT_TBL_BUTCONF:
                                llamarConf(objTblMod.getValueAt(intFilSel, INT_TBL_CODEMPCONF).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODLOCCONF).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOCCONF).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODDOCCONF).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_NATDOCTIPDOCCONF).toString(),objTblMod.getValueAt(intFilSel, INT_TBL_CODBODGRPCONF).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_CODMNUDOCCONF).toString() );                                
                                break;
                        }
                    }
                }
            });
            
            //Agrupamiento de Columnas.
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrpCotVen = new ZafTblHeaColGrp("COTIZACIONES DE VENTA");
            objTblHeaColGrpCotVen.setHeight(16);
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_CODEMPCOT));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_CODLOCCOT));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_LOCCOT));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_CODCOT));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_BUTCOT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCotVen);
            objTblHeaColGrpCotVen = null;

            ZafTblHeaColGrp objTblHeaColGrpSolTraInv = new ZafTblHeaColGrp("SOLICITUDES DE TRANSFERENCIA DE INVENTARIO");
            objTblHeaColGrpSolTraInv.setHeight(16);
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_CODEMPSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_CODLOCSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_CODTIPDOCSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DESCORTIPDOCSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DESLARTIPDOCSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_CODDOCSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_NUMDOCSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_FECDOCSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_BODORGSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_BODDESSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_PESSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_BUTSOLTRAINV));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSolTraInv);
            objTblHeaColGrpSolTraInv = null;
            
            ZafTblHeaColGrp objTblHeaColGrpSolItm = new ZafTblHeaColGrp(" ÍTEMS ");
            objTblHeaColGrpSolItm.setHeight(16);
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_CODITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_CODALTITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_CODALTDOS));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_NOMITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_UNIMED));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_CANITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_PESUNIITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_CANPENITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_CANCONFITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_CANTRAITM));
            objTblHeaColGrpSolItm.add(tcmAux.getColumn(INT_TBL_BUT_ITM));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSolItm);
            objTblHeaColGrpSolItm = null;

            ZafTblHeaColGrp objTblHeaColGrpMovInv = new ZafTblHeaColGrp("MOVIMIENTOS DE INVENTARIO");
            objTblHeaColGrpMovInv.setHeight(16);
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_CODEMPMOVINV));
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_CODLOCMOVINV));
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_CODTIPDOCMOVINV));
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_DESCORTIPDOCMOVINV));
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_DESLARTIPDOCMOVINV));
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_CODDOCMOVINV));
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_NUMDOCMOVINV));
            objTblHeaColGrpMovInv.add(tcmAux.getColumn(INT_TBL_BUTMOVINV));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMovInv);
            objTblHeaColGrpMovInv = null;

            ZafTblHeaColGrp objTblHeaColGrpGuiOrd = new ZafTblHeaColGrp("ORDENES DE DESPACHO / GUIAS DE REMISIÓN");
            objTblHeaColGrpGuiOrd.setHeight(16);
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_CODEMPODGUI));
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_CODLOCODGUI));
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_CODTIPDOCODGUI));
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_DESCORTIPDOCODGUI));
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_DESLARTIPDOCODGUI));
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_CODDOCMOVINV));
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_NUMDOCODGUI));
            objTblHeaColGrpGuiOrd.add(tcmAux.getColumn(INT_TBL_BUTODGUI));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpGuiOrd);
            objTblHeaColGrpGuiOrd = null;

            ZafTblHeaColGrp objTblHeaColGrpConf = new ZafTblHeaColGrp("CONFIRMACIONES");
            objTblHeaColGrpConf.setHeight(16);
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_CODEMPCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_CODLOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_CODTIPDOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_DESCORTIPDOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_DESLARTIPDOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_CODDOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_NUMDOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_CODBODGRPCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_NATDOCTIPDOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_CODMNUDOCCONF));
            objTblHeaColGrpConf.add(tcmAux.getColumn(INT_TBL_BUTCONF));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpConf);
            objTblHeaColGrpConf = null;

            tcmAux = null;
            setEditable(true);

        }
        catch (Exception e) {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_CODSEG:
                    strMsg = "Código de Seguimiento";
                    break;
                case INT_TBL_CODREG:
                    strMsg = "Código de Registro";
                    break;
                case INT_TBL_CODEMPCOT:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_CODLOCCOT:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_LOCCOT:
                    strMsg = "Nombre del Local";
                    break;
                case INT_TBL_CODCOT:
                    strMsg = "Número de Cotización";
                    break;
                case INT_TBL_BUTCOT:
                    strMsg = "Cotización de Venta";
                    break;
                    
                case INT_TBL_CODEMPSOL:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_CODLOCSOL:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_CODTIPDOCSOL:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DESCORTIPDOCSOL:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DESLARTIPDOCSOL:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CODDOCSOL:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_NUMDOCSOL:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_FECDOCSOL:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_BODORGSOL:
                    strMsg = "Origen (Punto de envío)";
                    break;
                case INT_TBL_BODDESSOL:
                    strMsg = "Destino (Punto de llegada)";
                    break;
                case INT_TBL_PESSOL:
                    strMsg = "Peso (Kg)";
                    break;
                case INT_TBL_BUTSOLTRAINV:
                    strMsg = "Solicitud de Transferencia de Inventario";
                    break;  
                    
                case INT_TBL_CODITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_CODALTITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_CODALTDOS:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_NOMITM:
                    strMsg="Nombre del item";
                    break;   
                case INT_TBL_UNIMED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_CANITM:
                    strMsg="Cantidad Solicitada";
                    break;
                case INT_TBL_PESUNIITM:
                    strMsg="Peso Unitario del ítem";
                    break;                        
                case INT_TBL_CANPENITM:
                    strMsg="Cantidad Pendiente del ítem";
                    break;
                case INT_TBL_CANCONFITM:
                    strMsg="Cantidad Confirmada del ítem";
                    break;
                case INT_TBL_CANTRAITM:
                    strMsg="Cantidad en Transito del ítem";
                    break;                    
                case INT_TBL_BUT_ITM:
                    strMsg = "";
                    break;
                    
                case INT_TBL_CODEMPMOVINV:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_CODLOCMOVINV:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_CODTIPDOCMOVINV:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DESCORTIPDOCMOVINV:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DESLARTIPDOCMOVINV:
                    strMsg = "Descripción larga del tipo de documento";
                    break;   
                case INT_TBL_CODDOCMOVINV:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_NUMDOCMOVINV:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_BUTMOVINV:
                    strMsg = "Documento de Inventario";
                    break;

                case INT_TBL_CODEMPODGUI:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_CODLOCODGUI:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_CODTIPDOCODGUI:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DESCORTIPDOCODGUI:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DESLARTIPDOCODGUI:
                    strMsg = "Descripción larga del tipo de documento";
                    break;  
                case INT_TBL_CODDOCODGUI:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_NUMDOCODGUI:
                    strMsg = "Número de documento (Orden de Despacho/Guías de Remisión)";
                    break;
                case INT_TBL_BUTODGUI:
                    strMsg = "Orden de Despacho/Guía de Remisión";
                    break;

                case INT_TBL_CODEMPCONF:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_CODLOCCONF:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_CODTIPDOCCONF:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DESCORTIPDOCCONF:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DESLARTIPDOCCONF:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CODDOCCONF:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_NUMDOCCONF:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_CODBODGRPCONF:
                    strMsg = "Código de Bodega Grupo Confirmación";
                    break;    
                case INT_TBL_NATDOCTIPDOCCONF:
                    strMsg = "Naturaleza de Tipo Documento de Confirmación";
                    break;   
                case INT_TBL_CODMNUDOCCONF:
                    strMsg = "Código Menú de Confirmación";
                    break;       
                case INT_TBL_BUTCONF:
                    strMsg = "Confirmación de Inventario";
                    break;
                case INT_TBL_CHKPRO:
                    strMsg = "Proceso Completo";
                    break;
                default:
                    strMsg = " ";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    public boolean esImpar(int iNumero) 
    {
        if (iNumero % 2 != 0) 
            return true;
        else
            return false;
    }
    
    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        else
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
    }

    private void llamarCotVen(String strCodEmp, String strCodLoc, String strCodCot) 
    {
        try 
        {
            Ventas.ZafVen01.ZafVen01 obj1 = new Ventas.ZafVen01.ZafVen01(objParSis, strCodEmp, strCodLoc, strCodCot);
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        }
        catch (Exception Evt) {    objUti.mostrarMsgErr_F1(this, Evt);   }
    }

    private void llamarSolTraInv(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try 
        {
            Compras.ZafCom91.ZafCom91 obj1 = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        } 
        catch (Exception Evt) {        objUti.mostrarMsgErr_F1(this, Evt);     }
    }
    
    private void llamarPantGuia(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        Compras.ZafCom23.ZafCom23 obj1 = new Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 0);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    private void llamarConf(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strNatTipDoc, String strCodBodGrp, String strCodMnuCon) 
    {
        try 
        {
            int intCodBodGrp = Integer.parseInt(strCodBodGrp);
            int intCodMnuConf= Integer.parseInt(strCodMnuCon);
            Compras.ZafCom94.ZafCom94 obj1 = new Compras.ZafCom94.ZafCom94(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strNatTipDoc, intCodBodGrp, intCodMnuConf);
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
            obj1.show();
        }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);     }
    }

    private void llamarMovInv(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        java.sql.Connection con;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strClaBus = "", strCodMnu = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stmLoc = con.createStatement();
                strSQL = "";
                strSQL += " SELECT a1.tx_Acc, a2.co_tipDoc,a1.co_mnu\n";
                strSQL += " FROM tbm_mnusis as a1 \n";
                strSQL += " INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_mnu=a2.co_mnu)\n";
                strSQL += " RIGHT JOIN tbr_tipDocPrg as a3 ON (a2.co_tipDoc=a3.co_tipDoc AND a3.co_mnu=" + objParSis.getCodigoMenu() + ") ";
                strSQL += " WHERE a2.co_mnu in (14,45,779)  ";
                strSQL += " AND a3.co_Emp= " + objParSis.getCodigoEmpresa();
                strSQL += " AND a3.co_tipdoc= " + strCodTipDoc;
                strSQL += " AND a3.co_loc=" + objParSis.getCodigoLocal(); 
                strSQL += " GROUP BY a1.tx_Acc, a2.co_tipDoc, a1.co_mnu";
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    strClaBus = rstLoc.getString("tx_acc");
                    strCodMnu = rstLoc.getString("co_mnu");
                }
            }
            invocarClase(strClaBus, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, Integer.valueOf(strCodMnu));
        }
        catch (Exception Evt) 
        {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
    
    private boolean invocarClase(String clase, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, int intCodMnu) 
    {
        boolean blnRes = true;
        java.util.HashMap map = new java.util.HashMap();
        //HashMap<Object,String> map = new HashMap<Object, String>();
        try
        {
            //Agregar parametros al HashMap para enviarlos al constructor respectivo
            map.put("objParSis", objParSis);
            map.put("strCodEmp", strCodEmp);
            map.put("strCodLoc", strCodLoc);
            map.put("strCodTipDoc", strCodTipDoc);
            map.put("strCodDoc", strCodDoc);
            map.put("intCodMnu", intCodMnu);

            //Obtener el constructor de la clase que se va a invocar.
            Class objVen = Class.forName(clase);
            Class objCla[] = new Class[1];
            objCla[0] = map.getClass();

            java.lang.reflect.Constructor objCon = objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[] = new Object[1];
            objObj[0] = map;
            javax.swing.JInternalFrame ifrVen;
            ifrVen = (javax.swing.JInternalFrame) objCon.newInstance(objObj);
            this.getParent().add(ifrVen, javax.swing.JLayeredPane.DEFAULT_LAYER);

            ifrVen.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                    System.gc();
                }

                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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
            ifrVen.show();
        } 
        catch (ClassNotFoundException e)   {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (NoSuchMethodException e)    {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    }
        catch (SecurityException e)        {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (InstantiationException e)   {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (IllegalAccessException e)   {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (IllegalArgumentException e) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (java.lang.reflect.InvocationTargetException e) {     blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        panFilCab = new javax.swing.JPanel();
        panBodOri = new javax.swing.JPanel();
        spnBodOri = new javax.swing.JScrollPane();
        tblBodOri = new javax.swing.JTable();
        panBodDes = new javax.swing.JPanel();
        spnBodDes = new javax.swing.JScrollPane();
        tblBodDes = new javax.swing.JTable();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panBusSol = new javax.swing.JPanel();
        panSolDesHas = new javax.swing.JPanel();
        lblSolTraDes = new javax.swing.JLabel();
        txtSolTraDes = new javax.swing.JTextField();
        lblSolTraHas = new javax.swing.JLabel();
        txtSolTraHas = new javax.swing.JTextField();
        lblEst = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        chkSolTraProPen = new javax.swing.JCheckBox();
        chkSolTraProCom = new javax.swing.JCheckBox();
        panBusItm = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        lblDesItm = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblHasItm = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panBusSeg = new javax.swing.JPanel();
        lblSegDes = new javax.swing.JLabel();
        txtSegDes = new javax.swing.JTextField();
        lblSegHas = new javax.swing.JLabel();
        txtSegHas = new javax.swing.JTextField();
        panRep = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panSur = new javax.swing.JPanel();
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
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        spnFil.setPreferredSize(new java.awt.Dimension(0, 600));

        panFilCab.setPreferredSize(new java.awt.Dimension(0, 600));
        panFilCab.setLayout(null);

        panBodOri.setBorder(javax.swing.BorderFactory.createTitledBorder("Origen: Listado de bodegas"));
        panBodOri.setLayout(new java.awt.BorderLayout());

        tblBodOri.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBodOri.setViewportView(tblBodOri);

        panBodOri.add(spnBodOri, java.awt.BorderLayout.CENTER);

        panFilCab.add(panBodOri);
        panBodOri.setBounds(5, 94, 320, 100);

        panBodDes.setBorder(javax.swing.BorderFactory.createTitledBorder("Destino: Listado de bodegas"));
        panBodDes.setLayout(new java.awt.BorderLayout());

        tblBodDes.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBodDes.setViewportView(tblBodDes);

        panBodDes.add(spnBodDes, java.awt.BorderLayout.CENTER);

        panFilCab.add(panBodDes);
        panBodDes.setBounds(347, 94, 310, 100);

        buttonGroup1.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFilCab.add(optTod);
        optTod.setBounds(10, 200, 550, 20);

        buttonGroup1.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optFilStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFilCab.add(optFil);
        optFil.setBounds(10, 220, 590, 20);

        panBusSol.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda por Solicitud de Transferencia "));
        panBusSol.setLayout(null);

        panSolDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Solicitud de Transferencia"));
        panSolDesHas.setLayout(null);

        lblSolTraDes.setText("Desde:");
        panSolDesHas.add(lblSolTraDes);
        lblSolTraDes.setBounds(10, 20, 48, 20);

        txtSolTraDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusLost(evt);
            }
        });
        panSolDesHas.add(txtSolTraDes);
        txtSolTraDes.setBounds(60, 20, 100, 20);

        lblSolTraHas.setText("Hasta:");
        panSolDesHas.add(lblSolTraHas);
        lblSolTraHas.setBounds(170, 20, 48, 20);

        txtSolTraHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusLost(evt);
            }
        });
        panSolDesHas.add(txtSolTraHas);
        txtSolTraHas.setBounds(220, 20, 100, 20);

        panBusSol.add(panSolDesHas);
        panSolDesHas.setBounds(20, 30, 340, 50);

        lblEst.setText("Estado de solicitud:");
        panBusSol.add(lblEst);
        lblEst.setBounds(370, 30, 140, 20);

        panBusSol.add(cboEstReg);
        cboEstReg.setBounds(370, 50, 180, 20);

        chkSolTraProPen.setSelected(true);
        chkSolTraProPen.setText("Mostrar las solicitudes que tienen procesos pendientes.");
        chkSolTraProPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolTraProPenActionPerformed(evt);
            }
        });
        panBusSol.add(chkSolTraProPen);
        chkSolTraProPen.setBounds(20, 90, 460, 20);

        chkSolTraProCom.setText("Mostrar las solicitudes que tienen el proceso completo.");
        chkSolTraProCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolTraProComActionPerformed(evt);
            }
        });
        panBusSol.add(chkSolTraProCom);
        chkSolTraProCom.setBounds(20, 110, 460, 20);

        panFilCab.add(panBusSol);
        panBusSol.setBounds(50, 320, 560, 140);

        panBusItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda por ítem"));
        panBusItm.setLayout(null);

        lblItm.setText("Item:");
        panBusItm.add(lblItm);
        lblItm.setBounds(10, 20, 40, 20);

        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panBusItm.add(txtCodAlt);
        txtCodAlt.setBounds(50, 20, 90, 20);

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
        panBusItm.add(txtCodAlt2);
        txtCodAlt2.setBounds(140, 20, 63, 20);

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
        panBusItm.add(txtNomItm);
        txtNomItm.setBounds(200, 20, 320, 20);

        butItm.setText(".."); // NOI18N
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panBusItm.add(butItm);
        butItm.setBounds(520, 20, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmDesHas.setLayout(null);

        lblDesItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDesItm.setText("Desde:");
        panItmDesHas.add(lblDesItm);
        lblDesItm.setBounds(12, 20, 60, 16);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(60, 20, 80, 20);

        lblHasItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblHasItm.setText("Hasta:");
        panItmDesHas.add(lblHasItm);
        lblHasItm.setBounds(150, 20, 50, 16);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(190, 20, 90, 20);

        panBusItm.add(panItmDesHas);
        panItmDesHas.setBounds(20, 50, 290, 48);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmTer.setLayout(null);

        lblItmTer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(12, 20, 80, 16);

        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(96, 20, 92, 20);

        panBusItm.add(panItmTer);
        panItmTer.setBounds(310, 50, 230, 48);

        panFilCab.add(panBusItm);
        panBusItm.setBounds(50, 470, 560, 110);

        panBusSeg.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda por Seguimiento"));
        panBusSeg.setLayout(null);

        lblSegDes.setText("Desde:");
        panBusSeg.add(lblSegDes);
        lblSegDes.setBounds(30, 30, 48, 20);

        txtSegDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSegDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSegDesFocusLost(evt);
            }
        });
        panBusSeg.add(txtSegDes);
        txtSegDes.setBounds(90, 30, 130, 20);

        lblSegHas.setText("Hasta:");
        panBusSeg.add(lblSegHas);
        lblSegHas.setBounds(250, 30, 48, 20);

        txtSegHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSegHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSegHasFocusLost(evt);
            }
        });
        panBusSeg.add(txtSegHas);
        txtSegHas.setBounds(300, 30, 140, 20);

        panFilCab.add(panBusSeg);
        panBusSeg.setBounds(50, 250, 560, 60);

        spnFil.setViewportView(panFilCab);

        tabFrm.addTab("Filtro", spnFil);

        panRep.setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRep.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

        panCen.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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

        panSur.add(panBot, java.awt.BorderLayout.CENTER);

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

        panSur.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panSur, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            if (butCon.getText().equals("Consultar")) 
            {
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
        }
}//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm();
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void chkSolTraProPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolTraProPenActionPerformed
        if (!chkSolTraProPen.isSelected()) 
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolTraProPenActionPerformed

    private void chkSolTraProComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolTraProComActionPerformed
        if (!chkSolTraProCom.isSelected()) 
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolTraProComActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
            optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtSolTraDes.setText("");
            txtSolTraHas.setText("");
            cboEstReg.setSelectedIndex(0);
            chkSolTraProCom.setSelected(false);
            
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
            
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilActionPerformed

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilStateChanged

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
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
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodLetItm=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodLetItm))
        {
            if (txtCodAlt2.getText().equals(""))
            {
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
        txtCodAlt2.setText(strCodLetItm);
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

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

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        if (txtCodAltItmDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt.setText("");
            txtNomItm.setText("");
            if (txtCodAltItmHas.getText().length() == 0)
            {
                txtCodAltItmHas.setText(txtCodAltItmDes.getText());
            }
        }
    }//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        txtCodAltItmHas.selectAll();
    }//GEN-LAST:event_txtCodAltItmHasFocusGained

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        if (txtCodAltItmHas.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtSolTraDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusGained
        txtSolTraDes.selectAll();
    }//GEN-LAST:event_txtSolTraDesFocusGained

    private void txtSolTraDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusLost
        if (txtSolTraDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            if (txtSolTraHas.getText().length() == 0)
            txtSolTraHas.setText(txtSolTraDes.getText());
        }
    }//GEN-LAST:event_txtSolTraDesFocusLost

    private void txtSolTraHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusGained
        txtSolTraHas.selectAll();
    }//GEN-LAST:event_txtSolTraHasFocusGained

    private void txtSolTraHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusLost
        if (txtSolTraHas.getText().length() > 0)
        optFil.setSelected(true);
    }//GEN-LAST:event_txtSolTraHasFocusLost

    private void txtSegDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegDesFocusGained
        txtSegDes.selectAll();
    }//GEN-LAST:event_txtSegDesFocusGained

    private void txtSegDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegDesFocusLost
        if (txtSegDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            if (txtSegHas.getText().length() == 0)
                txtSegHas.setText(txtSegDes.getText());
        }
    }//GEN-LAST:event_txtSegDesFocusLost

    private void txtSegHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegHasFocusGained
        txtSegHas.selectAll();
    }//GEN-LAST:event_txtSegHasFocusGained

    private void txtSegHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSegHasFocusLost
        if (txtSegHas.getText().length() > 0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtSegHasFocusLost

    private void exitForm() 
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
            dispose();
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JCheckBox chkSolTraProCom;
    private javax.swing.JCheckBox chkSolTraProPen;
    private javax.swing.JLabel lblDesItm;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblHasItm;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblSegDes;
    private javax.swing.JLabel lblSegHas;
    private javax.swing.JLabel lblSolTraDes;
    private javax.swing.JLabel lblSolTraHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBodDes;
    private javax.swing.JPanel panBodOri;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBusItm;
    private javax.swing.JPanel panBusSeg;
    private javax.swing.JPanel panBusSol;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panSolDesHas;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBodDes;
    private javax.swing.JScrollPane spnBodOri;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBodDes;
    private javax.swing.JTable tblBodOri;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtSegDes;
    private javax.swing.JTextField txtSegHas;
    private javax.swing.JTextField txtSolTraDes;
    private javax.swing.JTextField txtSolTraHas;
    // End of variables declaration//GEN-END:variables


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes = true;
        String strSQL="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Let.Itm.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
            strSQL+=" SELECT a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a1.tx_desCor ";
            strSQL+=" FROM tbm_inv as a LEFT JOIN tbm_var as a1 ON (a1.co_reg=a.co_uni) ";
            strSQL+=" WHERE  a.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a.st_reg NOT IN ('U','T') ";  
            strSQL+=" ORDER BY a.tx_codalt ";
            System.out.println("configurarVenConItm: "+strSQL);
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodLetItm);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblBodOri".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBodOri() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BODORI_LIN, "");
            vecCab.add(INT_TBL_BODORI_CHKBOD, "");
            vecCab.add(INT_TBL_BODORI_CODBOD, "Cód.Bod.");
            vecCab.add(INT_TBL_BODORI_NOMBOD, "Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBodOri = new ZafTblMod();
            objTblModBodOri.setHeader(vecCab);
            tblBodOri.setModel(objTblModBodOri);
            //Configurar JTable: Establecer tipo de selección.
            tblBodOri.setRowSelectionAllowed(true);
            tblBodOri.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblBodOri);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBodOri.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblBodOri.getColumnModel();
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BODORI_NOMBOD).setPreferredWidth(190);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBodOri.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBodOri.getTableHeader().addMouseMotionListener(new ZafCom84.ZafMouMotAdaBodOri());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBodOri.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodOriMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BODORI_CHKBOD);
            objTblModBodOri.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            //objTblEdi=new ZafTblEdi(tblBodOri);
            //Configurar JTable: Editor de búsqueda.
            //objTblBus=new ZafTblBus(tblBodOri);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblBodOri);
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblBodOri);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                    if (objTblModBodOri.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                    {
//                        //Mostrar columnas.
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
//                    else
//                    {
//                        //Ocultar columnas.
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBodOri+.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBodOri.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux = null;
        } 
        catch (Exception e) {      blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblBodDes".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBodDes() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BODDES_LIN, "");
            vecCab.add(INT_TBL_BODDES_CHKBOD, "");
            vecCab.add(INT_TBL_BODDES_CODBOD, "Cód.Bod.");
            vecCab.add(INT_TBL_BODDES_NOMBOD, "Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBodDes = new ZafTblMod();
            objTblModBodDes.setHeader(vecCab);
            tblBodDes.setModel(objTblModBodDes);
            //Configurar JTable: Establecer tipo de selección.
            tblBodDes.setRowSelectionAllowed(true);
            tblBodDes.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblBodDes);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBodDes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblBodDes.getColumnModel();
            tcmAux.getColumn(INT_TBL_BODDES_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODDES_CHKBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODDES_CODBOD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BODDES_NOMBOD).setPreferredWidth(190);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BODDES_CHKBOD).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBodDes.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBodDes.getTableHeader().addMouseMotionListener(new ZafCom84.ZafMouMotAdaBodDes());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBodDes.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodDesMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_BODDES_CHKBOD);
            objTblModBodDes.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Editor de la tabla.
            //objTblEdi=new ZafTblEdi(tblBodDes);
            //Configurar JTable: Editor de búsqueda.
            //objTblBus=new ZafTblBus(tblBodDes);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblBodDes);
            tcmAux.getColumn(INT_TBL_BODDES_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BODDES_CHKBOD).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BODDES_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblBodDes);
            tcmAux.getColumn(INT_TBL_BODDES_CHKBOD).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                    if (objTblModBodDes.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                    {
//                        //Mostrar columnas.
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
//                    else
//                    {
//                        //Ocultar columnas.
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBodDes+.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBodDes.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch (Exception e) {      blnRes = false;       objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblBodOriMouseClicked(java.awt.event.MouseEvent evt) 
    {
        int i, intNumFil;
        try
        {
            intNumFil = objTblModBodOri.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblBodOri.columnAtPoint(evt.getPoint()) == INT_TBL_BODORI_CHKBOD) 
            {
                if (blnMarTodChkTblBodOri)
                {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++)
                    {
                        objTblModBodOri.setChecked(true, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri = false;
                } 
                else 
                {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        objTblModBodOri.setChecked(false, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri = true;
                }
//                //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                {
//                    //Mostrar columnas.
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
//                else
//                {
//                    //Ocultar columnas.
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
            }
        }
        catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);       }
    }

    private void tblBodDesMouseClicked(java.awt.event.MouseEvent evt) 
    {
        int i, intNumFil;
        try
        {
            intNumFil = objTblModBodDes.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1 && tblBodDes.columnAtPoint(evt.getPoint()) == INT_TBL_BODDES_CHKBOD) {
                if (blnMarTodChkTblBodDes) 
                {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        objTblModBodDes.setChecked(true, i, INT_TBL_BODDES_CHKBOD);
                    }
                    blnMarTodChkTblBodDes = false;
                }
                else 
                {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        objTblModBodDes.setChecked(false, i, INT_TBL_BODDES_CHKBOD);
                    }
                    blnMarTodChkTblBodDes = true;
                }
//                //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
//                if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
//                {
//                    //Mostrar columnas.
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
//                else
//                {
//                    //Ocultar columnas.
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
//                }
            }
        }
        catch (Exception e) {        objUti.mostrarMsgErr_F1(this, e);     }
    }

    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBodOri extends java.awt.event.MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblBodOri.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_BODORI_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_BODORI_CHKBOD:
                    strMsg = "";
                    break;
                case INT_TBL_BODORI_CODBOD:
                    strMsg = "Código de la bodega";
                    break;
                case INT_TBL_BODORI_NOMBOD:
                    strMsg = "Nombre de la bodega";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblBodOri.getTableHeader().setToolTipText(strMsg);
        }
    }

    private class ZafMouMotAdaBodDes extends java.awt.event.MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblBodDes.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_BODDES_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_BODDES_CHKBOD:
                    strMsg = "";
                    break;
                case INT_TBL_BODDES_CODBOD:
                    strMsg = "Código de la bodega";
                    break;
                case INT_TBL_BODDES_NOMBOD:
                    strMsg = "Nombre de la bodega";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblBodDes.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función permite consultar las bodegas de acuerdo al siguiente
     * criterio: El listado de bodegas se presenta en función de la empresa a la
     * que se ingresa (Empresa Grupo u otra empresa) , el usuario que ingresa
     * (Administrador u otro usuario) y el menú desde el cual es llamado (237,
     * 886 o 907).
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBodOri() 
    {
        boolean blnRes = true;
        try
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();

                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario() == 1) 
                {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += " SELECT a2.co_bod, a2.tx_nom, a2.st_reg ";
                    strSQL += " FROM tbm_emp AS a1 ";
                    strSQL += " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='A' ";
                    strSQL += " ORDER BY a1.co_emp, a2.co_bod ";
                } 
                else 
                {
                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
                    {
                        //Armar la sentencia SQL.
                        strSQL = "";
                        strSQL += " SELECT a1.co_bodGrp as co_bod, a2.tx_nom ";
                        strSQL += " FROM tbr_bodlocprgusr as a ";
                        strSQL += " INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                        strSQL += " INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                        strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL += " AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL += " AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL += " AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL += " AND a.tx_natBod in ('E', 'A')  ";
                        strSQL += " ORDER BY a1.co_bodGrp ";
                    }
                    else 
                    {
                        strSQL = "";
                        strSQL += " SELECT a.co_bod, a2.tx_nom ";
                        strSQL += " FROM tbr_bodlocprgusr as a  ";
                        strSQL += " INNER JOIN tbm_bod as a2 ON (a.co_Emp=a2.co_emp and a.co_bod=a2.co_bod) ";
                        strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL += " AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL += " AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL += " AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL += " AND a.tx_natBod in ('E', 'A') ";
                        strSQL += " ORDER BY a.co_bod ";
                    }
                }
                rst = stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BODORI_LIN, "");
                    vecReg.add(INT_TBL_BODORI_CHKBOD, true);
                    vecReg.add(INT_TBL_BODORI_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BODORI_NOMBOD, rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblModBodOri.setData(vecDat);
                tblBodOri.setModel(objTblModBodOri);
                vecDat.clear();
                blnMarTodChkTblBodOri = false;
            }
        }
        catch (java.sql.SQLException e) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      } 
        catch (Exception e) {      blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }

    private boolean cargarBodDes()
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario() == 1) 
                {
                    //Armar la sentencia SQL.
                    strSQL = "";
                    strSQL += " SELECT a2.co_bod, a2.tx_nom, a2.st_reg ";
                    strSQL += " FROM tbm_emp AS a1 ";
                    strSQL += " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='A' ";
                    strSQL += " ORDER BY a1.co_emp, a2.co_bod ";
                } 
                else 
                {
                    //Armar la sentencia SQL.
                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
                    {
                        strSQL = "";
                        strSQL += " SELECT a1.co_bodGrp as co_bod, a2.tx_nom ";
                        strSQL += " FROM tbr_bodlocprgusr as a ";
                        strSQL += " INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                        strSQL += " INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                        strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL += " AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL += " AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL += " AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL += " AND a.tx_natBod in ('I', 'A') ";
                        strSQL += " ORDER BY a1.co_bodGrp ";
                    }
                    else 
                    {
                        strSQL = "";
                        strSQL += " SELECT a.co_bod, a2.tx_nom ";
                        strSQL += " FROM tbr_bodlocprgusr as a  ";
                        strSQL += " INNER JOIN tbm_bod as a2 ON (a.co_Emp=a2.co_emp and a.co_bod=a2.co_bod) ";
                        strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL += " AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL += " AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL += " AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL += " AND a.tx_natBod in ('I', 'A') ";
                        strSQL += " ORDER BY a.co_bod ";
                    }
                }
                rst = stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BODDES_LIN, "");
                    vecReg.add(INT_TBL_BODDES_CHKBOD, true);
                    vecReg.add(INT_TBL_BODDES_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BODDES_NOMBOD, rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblModBodDes.setData(vecDat);
                tblBodDes.setModel(objTblModBodDes);
                vecDat.clear();
                blnMarTodChkTblBodDes = false;
            }
        }
        catch (java.sql.SQLException e) {       blnRes = false;     objUti.mostrarMsgErr_F1(this, e);     } 
        catch (Exception e) {      blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblBodOri, intNumFilTblBodDes, i, j;

        //Validar que esté seleccionado al menos una bodega (Origen).
        intNumFilTblBodOri = objTblModBodOri.getRowCountTrue();
        i = 0;
        for (j = 0; j < intNumFilTblBodOri; j++) 
        {
            if (objTblModBodOri.isChecked(j, INT_TBL_BODORI_CHKBOD)) 
            {
                i++;
                break;
            }
        }
        if (i == 0) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos una bodega Origen.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            tblBodOri.requestFocus();
            return false;
        }

        //Validar que esté seleccionado al menos una bodega (Destino).
        intNumFilTblBodDes = objTblModBodDes.getRowCountTrue();
        i = 0;
        for (j = 0; j < intNumFilTblBodDes; j++) 
        {
            if (objTblModBodDes.isChecked(j, INT_TBL_BODDES_CHKBOD))
            {
                i++;
                break;
            }
        }
        if (i == 0) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos una bodega Destino.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            tblBodDes.requestFocus();
            return false;
        }

        return true;
    }
    
     /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
     * un JProgressBar con lo cual el usuario estaría informado en todo momento
     * de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        @Override
        public void run()
        {
            if (!cargarDetReg(sqlConFil()))
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
    
    /** Filtro de Búsqueda */
    private String sqlConFil() 
    {
        String sqlFil = "";
        String strBodOrg = "", strBodDes = "", strTerItm="";
        boolean blnBodOrg = false, blnBodDes = false, blnTerItm=false;
        
        if(!blnVenEmeCon)
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    sqlFil += " WHERE a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil += " WHERE a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil += " WHERE a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }

            //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Origen. */">
            for (int j = 0; j < tblBodOri.getRowCount(); j++) 
            {
                if (tblBodOri.getValueAt(j, INT_TBL_BODORI_CHKBOD).toString().equals("true")) 
                {
                    if (!blnBodOrg) //Primera vez que ingresa.
                    {
                        strBodOrg += " AND ( ";
                    } 
                    else 
                    {
                        strBodOrg += " OR ";
                    }
                    strBodOrg += " (a.co_bodOrg=" + tblBodOri.getValueAt(j, INT_TBL_BODORI_CODBOD).toString() + ") ";
                    blnBodOrg = true;
                }
            }
            if (blnBodOrg) 
            {
                strBodOrg += " ) ";
                sqlFil += strBodOrg;
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Destino. */">
            for (int j = 0; j < tblBodDes.getRowCount(); j++) 
            {
                if (tblBodDes.getValueAt(j, INT_TBL_BODDES_CHKBOD).toString().equals("true")) 
                {
                    if (!blnBodDes) //Primera vez que ingresa.
                    {
                        strBodDes += " AND ( ";
                    }
                    else 
                    {
                        strBodDes += " OR ";
                    }
                    strBodDes += " (a.co_bodDes=" + tblBodDes.getValueAt(j, INT_TBL_BODDES_CODBOD).toString() + ") ";
                    blnBodDes = true;
                }
            }
            if (blnBodDes) 
            {
                strBodDes += " ) ";
                sqlFil += strBodDes;
            }
            //</editor-fold>

            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Solicitud. */">
            if (txtSolTraDes.getText().length()>0 && txtSolTraHas.getText().length()>0)
                sqlFil+="  AND a.ne_numDoc BETWEEN "+objUti.codificar(txtSolTraDes.getText())+" AND "+objUti.codificar(txtSolTraHas.getText());
            //</editor-fold>
            
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Seguimiento. */">
            if (txtSegDes.getText().length()>0 && txtSegHas.getText().length()>0)
                sqlFil+="  AND a1.co_Seg BETWEEN "+objUti.codificar(txtSegDes.getText())+" AND "+objUti.codificar(txtSegHas.getText());
            //</editor-fold>

            if (cboEstReg.getSelectedIndex() > 0) 
            {
                sqlFil += " AND a.st_Aut = '" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
            }


            if (!(chkSolTraProPen.isSelected() && chkSolTraProCom.isSelected())) 
            {
                if (chkSolTraProCom.isSelected()) 
                {
                   sqlFil += " AND a.st_conInv IN ('C')";
                }
                else if (chkSolTraProPen.isSelected()) 
                {
                   sqlFil += " AND (a.st_conInv IS NULL OR a.st_conInv IN ('N', 'P', 'E'))";
                }
            }
            
            if (optFil.isSelected()) 
            {
                //Búsqueda por ítem.
                if (txtCodAlt.getText().length()>0)
                    sqlFil+=" AND inv.tx_codalt='"+objUti.codificar(txtCodAlt.getText())+"'";
                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                   sqlFil+=" AND ((LOWER(inv.tx_codalt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(inv.tx_codalt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                //Items Terminan Con.
                if (!txtCodAltItmTer.getText().equals("")) 
                {
                    //blnTerItm=false;
                    String[] result = objUti.codificar(txtCodAltItmTer.getText()).split(",");
                    strTerItm = " AND  ( ";
                    for (int x = 0; x < result.length; x++)
                    {
                        if (blnTerItm) 
                        {
                            strTerItm += " or ";
                        }
                        strTerItm += "  upper(inv.tx_codalt) like '%" + result[x].toString().toUpperCase() + "'";
                        blnTerItm=true;
                    }
                    strTerItm+= " ) ";
                    sqlFil+=strTerItm;
                }
            }
            
        
        }
        else  //Ventana de Consulta Emergente
        {
           sqlFil += " WHERE a.co_emp="+strCodEmp+" AND a.co_loc="+strCodLoc+" AND a.co_tipDoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
        }
        
        return sqlFil;
    }
    
    private boolean cargarDetReg(String strFil) 
    {
        boolean blnRes = true;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;        
        String strAuxAut="";
        
        try 
        {
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Obteniendo datos...");
            butCon.setText("Detener");
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stmLoc = conn.createStatement();
                strSQL ="";
                strSQL+="  SELECT Seg.co_seg, Seg.co_reg, Cot.co_emp as CodEmpCot, Cot.co_loc as CodLocCot, Loc.tx_nom as LocCot, Cot.co_cot as NumCot, "+"\n";
                strSQL+="         Sol.co_emp as CodEmpSolTraInv, Sol.co_loc as CodLocSolTraInv, Sol.co_tipdoc as CodTipDocSolTraInv, t.tx_descor as DesCorSolTraInv,  "+"\n"; 
                strSQL+="         t.tx_deslar as DesLarSolTraInv, Sol.co_doc as CodDocSolTraInv, Sol.ne_numdoc as NumDocSolTraInv, Sol.fe_doc as FecDocSolTraInv,  "+"\n";
                strSQL+="         Sol.co_bodOrg, BodOrg.tx_nom as NomBodOrg, Sol.co_bodDes, BodDes.tx_nom as NomBodDes, Sol.nd_pesTotKgr, /*Fil.co_reg,*/   "+"\n";
                strSQL+="         CASE WHEN SolDet.co_reg IS NOT NULL THEN Fil.co_reg ELSE SolDet.co_reg END as CodRegVVV,   "+"\n";
                strSQL+="         SolDet.co_reg as CodRegItmSol, SolDet.co_itm as CodItmSol, inv.tx_codAlt, inv.tx_codAlt2, inv.tx_nomItm, a7.tx_desCor as UniMed, "+"\n";
                strSQL+="         SolDet.nd_Can as CanSol, SolDet.nd_pesTotKgr as PesTotItmSol,  "+"\n";
                strSQL+="         MovInv.co_emp as CodEmpMovInv, MovInv.co_loc as CodLocMovInv, MovInv.co_tipdoc as CodTipDocMovInv, t1.tx_descor as DesCorMovInv,  "+"\n";
                strSQL+="         t1.tx_desLar as DesLarMovInv, MovInv.co_doc as CodDocMovInv, MovInv.ne_numdoc as NumDocMovInv,  "+"\n";
                strSQL+="         a9.co_emp as CodEmpGuiRem, a9.co_loc as CodLocGuiRem, a9.co_tipdoc as CodTipDocGuiRem, a10.tx_descor as DesCorGuiRem,   "+"\n";
                strSQL+="         a10.tx_deslar as DesLarGuiRem, a9.co_doc as CodDocGuiRem,  "+"\n";
                strSQL+="         CASE WHEN a9.ne_numdoc >0 THEN a9.ne_numdoc ELSE a9.ne_numOrdDes END as NumDocGuiRem,  "+"\n";
                strSQL+="         a12.tx_natDoc, a11.co_mnu as CodMnuConf, a11.co_emp as CodEmpIngEgr, a11.co_loc as CodLocIngEgr, a11.co_tipdoc as CodTipDocIngEgr, a12.tx_descor as DesCorIngEgr,   "+"\n";
                strSQL+="         a12.tx_desLar as DesLarIngEgr, a11.co_doc as CodDocIngEgr, a11.ne_numdoc as NumDocIngEgr, a13.co_bodGrp as CodBodGrpConf, Sol.st_ConInv  "+"\n";
                strSQL+="  FROM tbm_cabSegMovInv as Seg  "+"\n";
                strSQL+="  LEFT JOIN tbm_cabcotven as Cot on (Seg.co_emprelcabcotven=Cot.co_emp and Seg.co_locrelcabcotven=Cot.co_loc and Seg.co_cotrelcabcotven=Cot.co_cot)  "+"\n";
                strSQL+="  LEFT JOIN tbm_loc as Loc on (Loc.co_emp=Cot.co_emp and Loc.co_loc=Cot.co_loc)  "+"\n";
                strSQL+="  LEFT JOIN tbm_cabSoltrainv Sol on (Seg.co_empRelCabSolTraInv=Sol.co_emp and Seg.co_locRelCabSolTraInv=Sol.co_loc and Seg.co_tipDocRelCabSolTraInv=Sol.co_tipdoc and Seg.co_docRelCabSolTraInv=Sol.co_doc)  "+"\n";
                strSQL+="  LEFT JOIN tbm_CabTipDoc as t ON (t.co_emp=Sol.co_emp AND t.co_loc=Sol.co_loc AND t.co_tipDoc=Sol.co_tipDoc)   "+"\n";
                strSQL+="  LEFT JOIN tbm_bod as BodOrg ON (BodOrg.co_emp=Sol.co_emp AND BodOrg.co_bod=Sol.co_bodOrg)  "+"\n";
                strSQL+="  LEFT JOIN tbm_bod as BodDes ON (BodDes.co_emp=Sol.co_emp AND BodDes.co_bod=Sol.co_bodDes)  "+"\n";
                strSQL+="  LEFT JOIN tbm_cabmovinv a5 on (Sol.co_emp=a5.co_emp and Sol.co_loc=a5.co_loc and Sol.co_tipdoc=a5.co_tipdoc and Sol.co_doc=a5.co_doc )  "+"\n";
                strSQL+="  LEFT JOIN tbm_cabmovinv MovInv on (Seg.co_empRelCabMovInv=MovInv.co_emp and Seg.co_locRelCabMovInv=MovInv.co_loc and Seg.co_tipDocRelCabMovInv=MovInv.co_tipdoc and Seg.co_docRelCabMovInv=MovInv.co_doc )  "+"\n";
                strSQL+="  LEFT JOIN tbm_CabTipDoc as t1 ON (t1.co_emp=MovInv.co_emp AND t1.co_loc=MovInv.co_loc AND t1.co_tipDoc=MovInv.co_tipDoc)   "+"\n";
                strSQL+="  LEFT JOIN tbm_cabGuiRem as a9 on (Seg.co_emprelcabguirem=a9.co_emp and Seg.co_locrelcabguirem=a9.co_loc and Seg.co_tipdocrelcabguirem=a9.co_tipdoc and Seg.co_docrelcabguirem=a9.co_doc)  "+"\n";
                strSQL+="  LEFT JOIN tbm_CabTipDoc as a10 ON (a10.co_emp=a9.co_emp AND a10.co_loc=a9.co_loc AND a10.co_tipDoc=a9.co_tipDoc)   "+"\n";
                strSQL+="  LEFT JOIN tbm_cabIngEgrMerBod as a11 on (Seg.co_empRelCabIngEgrMerBod=a11.co_emp and Seg.co_locRelCabIngEgrMerBod=a11.co_loc and Seg.co_tipDocRelCabIngEgrMerBod=a11.co_tipdoc and Seg.co_docRelCabIngEgrMerBod=a11.co_doc)  "+"\n";
                strSQL+="  LEFT JOIN tbm_CabTipDoc as a12 ON (a12.co_emp=a11.co_emp AND a12.co_loc=a11.co_loc AND a12.co_tipDoc=a11.co_tipDoc)   "+"\n";
                strSQL+="  LEFT JOIN tbr_bodEmpBodGrp as a13 ON (a13.co_Emp=a11.co_Emp AND a13.co_bod=a11.co_bod)  "+"\n";
                strSQL+="  INNER JOIN   "+"\n";
                strSQL+="  (  "+"\n";
                strSQL+="     SELECT a1.co_Seg, a2.co_reg  "+"\n";
                strSQL+="     FROM tbm_cabSoltrainv as a   "+"\n";
                strSQL+="     INNER JOIN tbm_cabSegMovInv as a1 ON (a1.co_empRelCabSolTraInv=a.co_emp and a1.co_locRelCabSolTraInv=a.co_loc and a1.co_tipDocRelCabSolTraInv=a.co_tipdoc and a1.co_docRelCabSolTraInv=a.co_doc)  "+"\n";
                strSQL+="     INNER JOIN tbm_detSolTraInv as a2 ON (a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_doc)  "+"\n";
                strSQL+="     INNER JOIN tbm_inv as inv ON (inv.co_Emp=a2.co_Emp AND inv.co_itm=a2.co_itm)   "+"\n";
                strSQL+="     "+strFil +"\n";
                strSQL+="  ) as Fil  ON (Fil.co_Seg=Seg.co_seg )  "+"\n";
                strSQL+=" LEFT JOIN tbm_detSolTraInv as SolDet ON (Sol.co_emp=SolDet.co_emp AND Sol.co_loc=SolDet.co_loc AND Sol.co_tipDoc=SolDet.co_tipDoc AND Sol.co_doc=SolDet.co_doc AND Fil.co_reg=SolDet.co_reg) "+"\n";
                strSQL+=" LEFT JOIN tbm_inv as inv ON (inv.co_Emp=SolDet.co_Emp AND inv.co_itm=SolDet.co_itm)   "+"\n";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a7 ON (inv.co_uni=a7.co_reg)   "+"\n";
                strSQL+=" GROUP BY Seg.co_seg, Seg.co_reg, Cot.co_emp, Cot.co_loc, Loc.tx_nom, Cot.co_cot,  "+"\n";
                strSQL+="          Sol.co_emp, Sol.co_loc, Sol.co_tipdoc, t.tx_descor, t.tx_deslar, Sol.co_doc, Sol.ne_numdoc,  "+"\n";
                strSQL+="          Sol.fe_doc, Sol.co_bodOrg, BodOrg.tx_nom, Sol.co_bodDes, BodDes.tx_nom, Sol.nd_pesTotKgr, /* Fil.co_reg,*/  "+"\n";
                strSQL+="          CASE WHEN SolDet.co_reg IS NOT NULL THEN Fil.co_reg ELSE SolDet.co_reg END,  "+"\n";
                strSQL+="          SolDet.co_reg,  SolDet.co_itm, inv.tx_codAlt, inv.tx_codAlt2, inv.tx_nomItm,  a7.tx_desCor, SolDet.nd_Can, SolDet.nd_pesTotKgr,  "+"\n";
                strSQL+="          MovInv.co_emp, MovInv.co_loc, MovInv.co_tipdoc, t1.tx_descor, t1.tx_desLar, MovInv.co_doc, MovInv.ne_numdoc,  "+"\n";
                strSQL+="          a9.co_emp, a9.co_loc, a9.co_tipdoc, a10.tx_descor, a10.tx_deslar, a9.co_doc, a9.ne_numdoc, a9.ne_numOrdDes,    "+"\n";    
                strSQL+="          a12.tx_natDoc, a11.co_mnu, a11.co_emp, a11.co_loc, a11.co_tipdoc, a12.tx_descor, a12.tx_desLar, a11.co_doc, a11.ne_numdoc, a13.co_bodGrp, Sol.st_ConInv  "+"\n";
                strSQL+=" ORDER BY Seg.co_Seg, Seg.co_Reg ";
                
                System.out.println("cargarDetReg: " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                vecDat.clear();
                while (rstLoc.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN, "");
                    vecReg.add(INT_TBL_CODSEG, rstLoc.getString("co_seg"));
                    vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                    vecReg.add(INT_TBL_CODEMPCOT, rstLoc.getString("CodEmpCot"));
                    vecReg.add(INT_TBL_CODLOCCOT, rstLoc.getString("CodLocCot"));
                    vecReg.add(INT_TBL_LOCCOT, rstLoc.getString("LocCot"));
                    vecReg.add(INT_TBL_CODCOT, rstLoc.getString("NumCot"));
                    vecReg.add(INT_TBL_BUTCOT, "");

                    vecReg.add(INT_TBL_CODEMPSOL, rstLoc.getString("CodEmpSolTraInv"));
                    vecReg.add(INT_TBL_CODLOCSOL, rstLoc.getString("CodLocSolTraInv"));
                    vecReg.add(INT_TBL_CODTIPDOCSOL, rstLoc.getString("CodTipDocSolTraInv"));
                    vecReg.add(INT_TBL_DESCORTIPDOCSOL, rstLoc.getString("DesCorSolTraInv"));
                    vecReg.add(INT_TBL_DESLARTIPDOCSOL, rstLoc.getString("DesLarSolTraInv"));
                    vecReg.add(INT_TBL_CODDOCSOL, rstLoc.getString("CodDocSolTraInv"));
                    vecReg.add(INT_TBL_NUMDOCSOL, rstLoc.getString("NumDocSolTraInv"));
                    vecReg.add(INT_TBL_FECDOCSOL, rstLoc.getString("FecDocSolTraInv"));
                    vecReg.add(INT_TBL_BODORGSOL, rstLoc.getString("NomBodOrg"));
                    vecReg.add(INT_TBL_BODDESSOL, rstLoc.getString("NomBodDes"));
                    vecReg.add(INT_TBL_PESSOL, rstLoc.getString("nd_pesTotKgr"));                    
                    vecReg.add(INT_TBL_BUTSOLTRAINV, "");
                    
                    vecReg.add(INT_TBL_CODITM, rstLoc.getString("CodItmSol"));
                    vecReg.add(INT_TBL_CODALTITM, rstLoc.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_CODALTDOS, rstLoc.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_UNIMED, rstLoc.getString("UniMed"));
                    vecReg.add(INT_TBL_CANITM, rstLoc.getString("CanSol"));
                    vecReg.add(INT_TBL_PESUNIITM, rstLoc.getString("PesTotItmSol"));
                    vecReg.add(INT_TBL_CANPENITM, rstLoc.getString("CanSol"));
                    vecReg.add(INT_TBL_CANCONFITM, "");
                    vecReg.add(INT_TBL_CANTRAITM, "");
                    vecReg.add(INT_TBL_BUT_ITM, "");
                    
                    vecReg.add(INT_TBL_CODEMPMOVINV, rstLoc.getString("CodEmpMovInv"));
                    vecReg.add(INT_TBL_CODLOCMOVINV, rstLoc.getString("CodLocMovInv"));
                    vecReg.add(INT_TBL_CODTIPDOCMOVINV, rstLoc.getString("CodTipDocMovInv"));
                    vecReg.add(INT_TBL_DESCORTIPDOCMOVINV, rstLoc.getString("DesCorMovInv"));
                    vecReg.add(INT_TBL_DESLARTIPDOCMOVINV, rstLoc.getString("DesLarMovInv"));
                    vecReg.add(INT_TBL_CODDOCMOVINV, rstLoc.getString("CodDocMovInv"));
                    vecReg.add(INT_TBL_NUMDOCMOVINV, rstLoc.getString("NumDocMovInv"));
                    vecReg.add(INT_TBL_BUTMOVINV, "");

                    vecReg.add(INT_TBL_CODEMPODGUI, rstLoc.getString("CodEmpGuiRem"));
                    vecReg.add(INT_TBL_CODLOCODGUI, rstLoc.getString("CodLocGuiRem"));
                    vecReg.add(INT_TBL_CODTIPDOCODGUI, rstLoc.getString("CodTipDocGuiRem"));
                    vecReg.add(INT_TBL_DESCORTIPDOCODGUI, rstLoc.getString("DesCorGuiRem"));
                    vecReg.add(INT_TBL_DESLARTIPDOCODGUI, rstLoc.getString("DesLarGuiRem"));
                    vecReg.add(INT_TBL_CODDOCODGUI, rstLoc.getString("CodDocGuiRem"));
                    vecReg.add(INT_TBL_NUMDOCODGUI, rstLoc.getString("NumDocGuiRem"));
                    vecReg.add(INT_TBL_BUTODGUI, "");

                    vecReg.add(INT_TBL_CODEMPCONF, rstLoc.getString("CodEmpIngEgr"));
                    vecReg.add(INT_TBL_CODLOCCONF, rstLoc.getString("CodLocIngEgr"));
                    vecReg.add(INT_TBL_CODTIPDOCCONF, rstLoc.getString("CodTipDocIngEgr"));
                    vecReg.add(INT_TBL_DESCORTIPDOCCONF, rstLoc.getString("DesCorIngEgr"));
                    vecReg.add(INT_TBL_DESLARTIPDOCCONF, rstLoc.getString("DesLarIngEgr"));
                    vecReg.add(INT_TBL_CODDOCCONF, rstLoc.getString("CodDocIngEgr"));
                    vecReg.add(INT_TBL_NUMDOCCONF, rstLoc.getString("NumDocIngEgr"));
                    vecReg.add(INT_TBL_CODBODGRPCONF, rstLoc.getString("CodBodGrpConf"));
                    vecReg.add(INT_TBL_NATDOCTIPDOCCONF, rstLoc.getString("tx_natDoc"));
                    vecReg.add(INT_TBL_CODMNUDOCCONF, rstLoc.getString("CodMnuConf"));
                    vecReg.add(INT_TBL_BUTCONF, "");

                    if(rstLoc.getString("st_ConInv")!=null)   strAuxAut=rstLoc.getString("st_ConInv");
                    vecReg.add(INT_TBL_CHKPRO,((strAuxAut.compareTo("C")==0)? Boolean.TRUE:Boolean.FALSE));
                     
                    vecDat.add(vecReg);

                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);

            }
        }
        catch (java.sql.SQLException e) {       blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
        catch (Exception e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }


    
}

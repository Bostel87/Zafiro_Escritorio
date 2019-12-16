/*
 * ZafCom83.java  
 *
 * Created on May 08, 2016, 11:00:03 AM
 */
package Compras.ZafCom83;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
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
import Librerias.ZafVenCon.ZafVenCon;
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

/**
 *
 * @author Tony Sanginez
 */
public class ZafCom83 extends javax.swing.JInternalFrame 
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
    private static final int INT_TBL_DESSOL = 16;                               //Destinatario solicitud
    private static final int INT_TBL_BODORGSOL = 17;                            //Bodega Origen
    private static final int INT_TBL_BODDESSOL = 18;                            //Bodega Destino
    private static final int INT_TBL_PESSOL = 19;                               //Peso
    private static final int INT_TBL_BUTSOLTRAINV = 20;                         //Muestra la solicitud  de tranferencia de inventario
    private static final int INT_TBL_CODEMPMOVINV = 21;                         //Código de empresa movimiento de inventario
    private static final int INT_TBL_CODLOCMOVINV = 22;                         //Código local movimiento de invntario
    private static final int INT_TBL_CODTIPDOCMOVINV = 23;                      //Código tipo de dcoumento movimiento de inventario
    private static final int INT_TBL_DESCORTIPDOCMOVINV = 24;                   //Descripcion corta tipo de dcoumento movimiento de inventario
    private static final int INT_TBL_DESLARTIPDOCMOVINV = 25;                   //Descripcion larga tipo de dcoumento movimiento de inventario
    private static final int INT_TBL_CODDOCMOVINV = 26;                         //Código de documento movimiento de inventario
    private static final int INT_TBL_NUMDOCMOVINV = 27;                         //Numero de documento movimiento de inventario
    private static final int INT_TBL_BUTMOVINV = 28;                            //Muestra documento de inventario
    private static final int INT_TBL_CODEMPODGUI = 29;                          //Código de empresa od y guias
    private static final int INT_TBL_CODLOCODGUI = 30;                          //Código local od y guias
    private static final int INT_TBL_CODTIPDOCODGUI = 31;                       //Código tipo de documento od y guias
    private static final int INT_TBL_DESCORTIPDOCODGUI = 32;                    //Descripcion corta tipo de documento od y guias
    private static final int INT_TBL_DESLARTIPDOCODGUI = 33;                    //Descripcion larga tipo de documento od y guias
    private static final int INT_TBL_CODDOCODGUI = 34;                          //Código de documento od y guias
    private static final int INT_TBL_NUMDOCODGUI = 35;                          //Numero de documento od y guias
    private static final int INT_TBL_BUTODGUI = 36;                             //Muestra la orden de despacho o guia de remision
    private static final int INT_TBL_CODEMPCONF = 37;                           //Código de empresa confirmacione
    private static final int INT_TBL_CODLOCCONF = 38;                           //Código local confirmaciones
    private static final int INT_TBL_CODTIPDOCCONF = 39;                        //codigo tipo de documento confirmaciones
    private static final int INT_TBL_DESCORTIPDOCCONF = 40;                     //Desripcion corta tipo de documento confirmaciones
    private static final int INT_TBL_DESLARTIPDOCCONF = 41;                     //Desripcion larga tipo de documento confirmaciones
    private static final int INT_TBL_CODDOCCONF = 42;                           //Código de documento confirmaciones
    private static final int INT_TBL_NUMDOCCONF = 43;                           //Numero de documento confirmaciones
    private static final int INT_TBL_CODBODGRPCONF = 44;                        //Código de Bodega Grupo Confirmación.
    private static final int INT_TBL_NATDOCTIPDOCCONF = 45;                     //Naturaleza Tipo Documento de Confirmación.
    private static final int INT_TBL_CODMNUDOCCONF = 46;                        //Código de Menú Confirmación.
    private static final int INT_TBL_BUTCONF = 47;                              //Muestra la confirmacion  de inventario
    private static final int INT_TBL_CHKPRO = 48;                               //Proceso Completo

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
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblNum, objTblCelRenLblColDat;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafThreadGUI objThrGUI;
    private ZafTblPopMnu objTblPopMnu;
    private ZafPerUsr objPerUsr;                                                //Permisos Usuarios.
    private ZafVenCon vcoTipDoc;                                                //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private final java.awt.Color colFonColNar = new java.awt.Color(255, 221, 187);
    private final java.awt.Color colFonColVer = new java.awt.Color(228, 228, 203);
    private Vector vecDat, vecCab, vecReg, vecAux /*, vecEstReg*/;
    private boolean blnMarTodChkTblBodOri = true;                               //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnMarTodChkTblBodDes = true;                               //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnVenEmeCon=false;                                         //Ventana Emergente de Consulta. true: Consulta el seguimiento de acuerdo a la solicitud indicada.
    private boolean blnConSeg=false, blnConSol=false, blnConCot=false;          //Booleano para establecer consulta si es por solicitud o seguimiento.
    private String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";   //Utilizadas para la pk de la solicitud de transferencia.
    private String strDesCorTipDoc, strDesLarTipDoc;                            //Contenido del campo al obtener el foco.
    private String strSQL = "", strAux="", sqlFilSeg="", sqlFilCot="", strCot="";
    private String strVersion = " v0.5 ";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom83(ZafParSis obj) 
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
    public ZafCom83(HashMap map) 
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
            lblTit.setText("Seguimiento de solicitudes de transferencias de inventario (Formato 1: Cabecera)...");
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
            objSelFec.setTitulo("Fecha de la solicitud");
            panFilCab.add(objSelFec);
            objSelFec.setBounds(20, 70, 472, 72);
            
            //configurarFechaDesde(); //Consulta 1 mes hacia atras
            configurarFechaDesdeActual();
            
            txtCodTipDoc.setVisible(false);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3339)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3340)) 
            {
                butCer.setVisible(false);
            }
            //Configurar Combo.
            //configurarComboEstReg();
            
            //Configurar Ventanas de Consultas
            configurarVenConTipDoc();
            
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
    
    /* Configurar Fecha Desde el mismo día. */
    private void configurarFechaDesdeActual()
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
            objFec.set(Calendar.MONTH, fecDoc[1] );
            objFec.set(Calendar.YEAR, fecDoc[2]);
        }
        Calendar objFecPagActual = Calendar.getInstance();
        objFecPagActual.setTime(objFec.getTime());

        dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH));
        dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
        String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
        java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
        objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
    }
    
    /*private boolean configurarComboEstReg() 
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
    }*/
    
    
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
            vecCab.add(INT_TBL_DESSOL, "Destinatario");
            vecCab.add(INT_TBL_BODORGSOL, "Bodega Origen");
            vecCab.add(INT_TBL_BODDESSOL, "Bodega Destino");
            vecCab.add(INT_TBL_PESSOL, "Peso (Kg)");
            vecCab.add(INT_TBL_BUTSOLTRAINV, "...");
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
            tcmAux.getColumn(INT_TBL_DESSOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BODORGSOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BODDESSOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_PESSOL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTSOLTRAINV).setPreferredWidth(20);

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
            vecAux.add("" + INT_TBL_BUTMOVINV);
            vecAux.add("" + INT_TBL_BUTODGUI);
            vecAux.add("" + INT_TBL_BUTCONF);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            objTblCelRenLbl = null;

            //Columnas Ocultas
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPCOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOCCOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMPSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODLOCSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODTIPDOCSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_CODDOCSOL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DESSOL, tblDat);
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
            tcmAux.getColumn(INT_TBL_DESSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_BODORGSOL).setCellRenderer(objTblCelRenLblColDat);
            tcmAux.getColumn(INT_TBL_BODDESSOL).setCellRenderer(objTblCelRenLblColDat);

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
                    objTblCelRenLblColDat.setForeground(Color.BLACK);
                    //int intSeg=0;
                    int intCell=objTblCelRenLblColDat.getRowRender();
                    int intNumSeg=Integer.parseInt(tblDat.getValueAt(intCell, INT_TBL_CODSEG).toString());
                    //int intCellAnt=intCell-1==-1?1:intCell-1;
                    //int intNumSegAnt=Integer.parseInt(tblDat.getValueAt(intCellAnt, INT_TBL_CODSEG).toString());
                  
                    if(esImpar(intNumSeg))
                    {
                        objTblCelRenLblColDat.setBackground(colFonColNar);
                    }
                    else 
                    {
                        objTblCelRenLblColDat.setBackground(colFonColVer);
                    }
//                    for(int i=0; i< objTblMod.getRowCount(); i++)
//                    {
//                        if(intNumSegAnt!=intNumSeg)
//                        {
//                            intSeg++;
//                            if(esImpar(intSeg))
//                            {
//                                if()
//                                objTblCelRenLblColDat.setBackground(colFonColNar);
//                            }
//                            else 
//                            {
//                                objTblCelRenLblColDat.setBackground(colFonColVer);
//                            }
//                        }
//                    }
                    
                
                }
            });
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_PESSOL, objTblMod.INT_COL_DBL, new Integer(0), null);
           
            //Formato
            objTblCelRenLblNum = new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(objTblCelRenLblNum.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_PESSOL).setCellRenderer(objTblCelRenLblNum);

            objTblCelRenLblNum.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Combina los colores de acuerdo al codigo de seguimiento.
                    objTblCelRenLblNum.setForeground(Color.BLACK);
                    int intCell=objTblCelRenLblNum.getRowRender();
                    int intNumSeg=Integer.parseInt(tblDat.getValueAt(intCell, INT_TBL_CODSEG).toString());
                    //System.out.println("intNumSeg:"+intNumSeg);
                    
                    if(esImpar(intNumSeg))
                    {
                        objTblCelRenLblNum.setBackground(colFonColNar);
                    }
                    else 
                    {
                        objTblCelRenLblNum.setBackground(colFonColVer);
                    }
                }
                
            });
            
            
            //Establecer: Check
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKPRO).setCellRenderer(objTblCelRenChk);
            //objTblCelRenChk.setBackground(colFonColVer);
            //objTblCelRenChk = null;
            
            objTblCelRenChk.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenChk.getColumnRender()) 
                    {
                        case INT_TBL_CHKPRO:
                            //Combina los colores de acuerdo al codigo de seguimiento.
                            objTblCelRenChk.setForeground(Color.BLACK);
                            int intNumSeg=Integer.parseInt(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_CODSEG).toString() );
                            //System.out.println("intNumSeg:"+intNumSeg);
                            
                            if(esImpar(intNumSeg))
                            {
                                objTblCelRenChk.setBackground(colFonColNar); 
                            }
                            else
                            {
                                objTblCelRenChk.setBackground(colFonColVer); 
                            }
                        break;  
                            
                    }
                }
            });
            
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

            //Establecer Botón.
            tcmAux.getColumn(INT_TBL_BUTCOT).setCellRenderer(objTblCelRenButDG1);
            tcmAux.getColumn(INT_TBL_BUTSOLTRAINV).setCellRenderer(objTblCelRenButDG1);
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
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DESSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_BODORGSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_BODDESSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_PESSOL));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_BUTSOLTRAINV));

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSolTraInv);
            objTblHeaColGrpSolTraInv = null;

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
                case INT_TBL_DESSOL:
                    strMsg = "Destinatario";
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
        int intCodMnu=3497;
        if(strCodTipDoc.equalsIgnoreCase("231"))   intCodMnu=1793;
        Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
        obj=null;
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
            obj1=null;
        }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);     }
    }

    private void llamarMovInv(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strClaBus = "", strCodMnu = "";
        try 
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL = "";
                strSQL+=" SELECT a1.tx_Acc, a2.co_tipDoc,a1.co_mnu\n";
                strSQL+=" FROM tbm_mnusis as a1 \n";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_mnu=a2.co_mnu)\n";
                strSQL+=" RIGHT JOIN tbr_tipDocDetPrg as a3 ON (a2.co_tipDoc=a3.co_tipDoc AND a3.co_mnu=" + objParSis.getCodigoMenu() + ") ";
                strSQL+=" WHERE a2.co_mnu in (14, 45, 779, 2889, 1918)  ";
                strSQL+=" AND a3.co_Emp= " + objParSis.getCodigoEmpresa();
                strSQL+=" AND a3.co_tipdoc= " + strCodTipDoc;
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal(); 
                strSQL+=" GROUP BY a1.tx_Acc, a2.co_tipDoc, a1.co_mnu";
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    strClaBus = rstLoc.getString("tx_acc");
                    strCodMnu = rstLoc.getString("co_mnu");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
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
    
    
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
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
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        int intTipo=2;  
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
          
            //Armar la sentencia SQL.
            strSQL=obtenerQueryTipoDocumento(intTipo);
            
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
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
        panBusNumDoc = new javax.swing.JPanel();
        lblSolTra = new javax.swing.JLabel();
        lblSolTraDes = new javax.swing.JLabel();
        txtSolTraDes = new javax.swing.JTextField();
        lblSolTraHas = new javax.swing.JLabel();
        txtSolTraHas = new javax.swing.JTextField();
        lblCotVen = new javax.swing.JLabel();
        lblCotDes = new javax.swing.JLabel();
        txtCotDes = new javax.swing.JTextField();
        lblCotHas = new javax.swing.JLabel();
        txtCotHas = new javax.swing.JTextField();
        chkSolTraProPen = new javax.swing.JCheckBox();
        chkSolTraProCom = new javax.swing.JCheckBox();
        panSeg = new javax.swing.JPanel();
        lblSegDes = new javax.swing.JLabel();
        txtSegDes = new javax.swing.JTextField();
        lblSegHas = new javax.swing.JLabel();
        txtSegHas = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
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

        spnFil.setPreferredSize(new java.awt.Dimension(0, 375));

        panFilCab.setPreferredSize(new java.awt.Dimension(0, 450));
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
        panBodOri.setBounds(20, 140, 320, 100);

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
        panBodDes.setBounds(340, 140, 310, 100);

        buttonGroup1.add(optTod);
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
        optTod.setBounds(10, 20, 550, 20);

        buttonGroup1.add(optFil);
        optFil.setSelected(true);
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
        optFil.setBounds(10, 40, 590, 20);

        panBusNumDoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda por Número de "));
        panBusNumDoc.setLayout(null);

        lblSolTra.setText("Solicitud de Transferencia:");
        panBusNumDoc.add(lblSolTra);
        lblSolTra.setBounds(15, 15, 170, 20);

        lblSolTraDes.setText("Desde:");
        panBusNumDoc.add(lblSolTraDes);
        lblSolTraDes.setBounds(30, 40, 48, 20);

        txtSolTraDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusLost(evt);
            }
        });
        panBusNumDoc.add(txtSolTraDes);
        txtSolTraDes.setBounds(80, 40, 80, 20);

        lblSolTraHas.setText("Hasta:");
        panBusNumDoc.add(lblSolTraHas);
        lblSolTraHas.setBounds(170, 40, 48, 20);

        txtSolTraHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusLost(evt);
            }
        });
        panBusNumDoc.add(txtSolTraHas);
        txtSolTraHas.setBounds(220, 40, 80, 20);

        lblCotVen.setText("Cotización de Venta:");
        panBusNumDoc.add(lblCotVen);
        lblCotVen.setBounds(15, 70, 170, 20);

        lblCotDes.setText("Desde:");
        panBusNumDoc.add(lblCotDes);
        lblCotDes.setBounds(30, 90, 48, 20);

        txtCotDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCotDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCotDesFocusLost(evt);
            }
        });
        panBusNumDoc.add(txtCotDes);
        txtCotDes.setBounds(80, 90, 80, 20);

        lblCotHas.setText("Hasta:");
        panBusNumDoc.add(lblCotHas);
        lblCotHas.setBounds(170, 90, 48, 20);

        txtCotHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCotHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCotHasFocusLost(evt);
            }
        });
        panBusNumDoc.add(txtCotHas);
        txtCotHas.setBounds(220, 90, 80, 20);

        panFilCab.add(panBusNumDoc);
        panBusNumDoc.setBounds(330, 240, 320, 120);
        panBusNumDoc.getAccessibleContext().setAccessibleName("Número Solicitud de Transferencia");

        chkSolTraProPen.setText("Mostrar las solicitudes que tienen procesos pendientes.");
        chkSolTraProPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolTraProPenActionPerformed(evt);
            }
        });
        panFilCab.add(chkSolTraProPen);
        chkSolTraProPen.setBounds(20, 400, 460, 20);

        chkSolTraProCom.setText("Mostrar las solicitudes que tienen el proceso completo.");
        chkSolTraProCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolTraProComActionPerformed(evt);
            }
        });
        panFilCab.add(chkSolTraProCom);
        chkSolTraProCom.setBounds(20, 420, 460, 20);

        panSeg.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Seguimiento"));
        panSeg.setLayout(null);

        lblSegDes.setText("Desde:");
        panSeg.add(lblSegDes);
        lblSegDes.setBounds(12, 20, 48, 20);

        txtSegDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSegDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSegDesFocusLost(evt);
            }
        });
        panSeg.add(txtSegDes);
        txtSegDes.setBounds(60, 20, 90, 20);

        lblSegHas.setText("Hasta:");
        panSeg.add(lblSegHas);
        lblSegHas.setBounds(160, 20, 48, 20);

        txtSegHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSegHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSegHasFocusLost(evt);
            }
        });
        panSeg.add(txtSegHas);
        txtSegHas.setBounds(210, 20, 90, 20);

        panFilCab.add(panSeg);
        panSeg.setBounds(20, 240, 310, 50);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFilCab.add(lblTipDoc);
        lblTipDoc.setBounds(20, 370, 130, 20);
        panFilCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(120, 370, 32, 20);

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panFilCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(150, 370, 94, 20);

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
        panFilCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(244, 370, 377, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFilCab.add(butTipDoc);
        butTipDoc.setBounds(620, 370, 20, 20);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //if (isCamVal()) 
        //{
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
        //}
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
            optFil.setSelected(false);
            txtSolTraDes.setText("");
            txtSolTraHas.setText("");
            txtCotDes.setText("");
            txtCotHas.setText("");
            txtSegDes.setText("");
            txtSegHas.setText("");
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            objSelFec.setCheckBoxChecked(false);
            chkSolTraProPen.setSelected(false);
            chkSolTraProCom.setSelected(false);
            //cboEstReg.setSelectedIndex(0);
            
            blnMarTodChkTblBodOri=false;
            blnMarTodChkTblBodDes=false;
            
            //tblBodDesMouseClicked
            tblBodOri.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodDesMouseClicked(evt);
                }
            });   
            
            tblBodDes.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodDesMouseClicked(evt);
                }
            });   
           
        }
    }//GEN-LAST:event_optTodStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilActionPerformed

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected())
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilStateChanged

    private void txtSolTraHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusLost
        if (txtSolTraHas.getText().length() > 0)
           optFil.setSelected(true);
    }//GEN-LAST:event_txtSolTraHasFocusLost

    private void txtSolTraHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusGained
        txtSolTraHas.selectAll();
    }//GEN-LAST:event_txtSolTraHasFocusGained

    private void txtSolTraDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusLost
        if (txtSolTraDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            if (txtSolTraHas.getText().length() == 0)
                txtSolTraHas.setText(txtSolTraDes.getText());
        }
    }//GEN-LAST:event_txtSolTraDesFocusLost

    private void txtSolTraDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusGained
        txtSolTraDes.selectAll();
    }//GEN-LAST:event_txtSolTraDesFocusGained

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

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtCotDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotDesFocusGained
        txtCotDes.selectAll();
    }//GEN-LAST:event_txtCotDesFocusGained

    private void txtCotDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotDesFocusLost
        if (txtCotDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            if (txtCotHas.getText().length() == 0)
                txtCotHas.setText(txtCotDes.getText());
        }
    }//GEN-LAST:event_txtCotDesFocusLost

    private void txtCotHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotHasFocusGained
        txtCotHas.selectAll();
    }//GEN-LAST:event_txtCotHasFocusGained

    private void txtCotHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotHasFocusLost
        if (txtCotHas.getText().length() > 0)
           optFil.setSelected(true);
    }//GEN-LAST:event_txtCotHasFocusLost

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
    private javax.swing.JButton butTipDoc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkSolTraProCom;
    private javax.swing.JCheckBox chkSolTraProPen;
    private javax.swing.JLabel lblCotDes;
    private javax.swing.JLabel lblCotHas;
    private javax.swing.JLabel lblCotVen;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblSegDes;
    private javax.swing.JLabel lblSegHas;
    private javax.swing.JLabel lblSolTra;
    private javax.swing.JLabel lblSolTraDes;
    private javax.swing.JLabel lblSolTraHas;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBodDes;
    private javax.swing.JPanel panBodOri;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBusNumDoc;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panSeg;
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
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCotDes;
    private javax.swing.JTextField txtCotHas;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtSegDes;
    private javax.swing.JTextField txtSegHas;
    private javax.swing.JTextField txtSolTraDes;
    private javax.swing.JTextField txtSolTraHas;
    // End of variables declaration//GEN-END:variables

    
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
            tblBodOri.getTableHeader().addMouseMotionListener(new ZafCom83.ZafMouMotAdaBodOri());
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
            tblBodDes.getTableHeader().addMouseMotionListener(new ZafCom83.ZafMouMotAdaBodDes());
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
                    strSQL+=" SELECT a2.co_bod, a2.tx_nom, a2.st_reg ";
                    strSQL+=" FROM tbm_emp AS a1 ";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='A' ";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod ";
                } 
                else 
                {
                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
                    {
                        //Armar la sentencia SQL.
                        strSQL = "";
                        strSQL+=" SELECT a1.co_bodGrp as co_bod, a2.tx_nom ";
                        strSQL+=" FROM tbr_bodlocprgusr as a ";
                        strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                        strSQL+=" INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                        strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND a.tx_natBod in ('E', 'A')  ";
                        strSQL+=" ORDER BY a1.co_bodGrp ";
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
                    vecReg.add(INT_TBL_BODORI_CHKBOD, false);
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
                    strSQL+= " SELECT a2.co_bod, a2.tx_nom, a2.st_reg ";
                    strSQL+= " FROM tbm_emp AS a1 ";
                    strSQL+= " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSQL+= " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='A' ";
                    strSQL+= " ORDER BY a1.co_emp, a2.co_bod ";
                } 
                else 
                {
                    //Armar la sentencia SQL.
                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
                    {
                        strSQL = "";
                        strSQL+= " SELECT a1.co_bodGrp as co_bod, a2.tx_nom ";
                        strSQL+= " FROM tbr_bodlocprgusr as a ";
                        strSQL+= " INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                        strSQL+= " INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                        strSQL+= " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+= " AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+= " AND a.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+= " AND a.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+= " AND a.tx_natBod in ('I', 'A') ";
                        strSQL+= " ORDER BY a1.co_bodGrp ";
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
                    vecReg.add(INT_TBL_BODDES_CHKBOD, false);
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
        String strBodOrg = "", strBodDes = "";
        blnConSol=false;
        blnConSeg=false;
        blnConCot=false;
        boolean blnBodOrg=false, blnBodDes=false; 
        
        if(!blnVenEmeCon)
        {
            
            if(objSelFec.isCheckBoxChecked())
            {
                blnConSeg=false;
                blnConCot=false;
                switch (objSelFec.getTipoSeleccion()) 
                {
                    case 0: //Búsqueda por rangos
                        sqlFil+= " AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+= " AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+= " AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
            }

            //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Origen. */">
            strBodOrg="";
            for (int j = 0; j < tblBodOri.getRowCount(); j++) 
            {
                if (tblBodOri.getValueAt(j, INT_TBL_BODORI_CHKBOD) != null) 
                {
                    if (tblBodOri.getValueAt(j, INT_TBL_BODORI_CHKBOD).toString().equals("true")) 
                    {
                        blnConSeg=false;
                        blnConCot=false;
                        if(strBodOrg.equals("")) strBodOrg +=  tblBodOri.getValueAt(j, INT_TBL_BODORI_CODBOD).toString();
                        else strBodOrg += ","+tblBodOri.getValueAt(j, INT_TBL_BODORI_CODBOD).toString();
                        blnBodOrg=true;
                    } 
                }
            }
            if(blnBodOrg)
            {
               sqlFil+=" AND ( a.co_bodOrg in ( "+strBodOrg+" ) )";
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Destino. */">
            strBodDes="";
            for (int j = 0; j < tblBodDes.getRowCount(); j++) 
            {
                if (tblBodDes.getValueAt(j, INT_TBL_BODDES_CHKBOD) != null) 
                {
                    if (tblBodDes.getValueAt(j, INT_TBL_BODDES_CHKBOD).toString().equals("true")) 
                    {
                        blnConSeg=false;
                        blnConCot=false;
                        if(strBodDes.equals("")) strBodDes +=  tblBodDes.getValueAt(j, INT_TBL_BODDES_CODBOD).toString();
                        else strBodDes += ","+tblBodDes.getValueAt(j, INT_TBL_BODDES_CODBOD).toString();
                        blnBodDes=true;
                    } 
                }
            }
            if(blnBodDes)
            {
                sqlFil+=" AND ( a.co_bodDes in ( "+strBodDes+" ) )";
            }
            //</editor-fold>

            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Solicitud. */">
            if (txtSolTraDes.getText().length()>0 && txtSolTraHas.getText().length()>0)
            {
                blnConSeg=false;
                blnConCot=false;
                sqlFil+="  AND a.ne_numDoc BETWEEN "+objUti.codificar(txtSolTraDes.getText())+" AND "+objUti.codificar(txtSolTraHas.getText());
            }
            //</editor-fold>
            
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Tipo Documento. */">
            if (txtCodTipDoc.getText().length() > 0) 
            {
                blnConSeg=false;
                blnConCot=false;
                sqlFil+="  AND a.co_tipDoc=" + txtCodTipDoc.getText().replaceAll("'", "''") + " ";
            }
            //</editor-fold>

            //Estado de Confirmación de Solicitudes
            if (!(chkSolTraProPen.isSelected() && chkSolTraProCom.isSelected())) 
            {
                if (chkSolTraProCom.isSelected()) 
                {
                    blnConSeg=false;
                    blnConCot=false;
                    sqlFil+= " AND a.st_conInv IN ('C', 'N')";
                }
                else if (chkSolTraProPen.isSelected()) 
                {
                    blnConSeg=false;
                    blnConCot=false;
                    sqlFil+= " AND (a.st_conInv IS NULL OR a.st_conInv IN ('P', 'E'))";
                }
            }
            
            
            if(!sqlFil.equals(""))
                blnConSol=true;
            
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Seguimiento. */">
            if (txtCotDes.getText().length()>0 && txtCotHas.getText().length()>0)
            {
                blnConCot=true;
                sqlFilCot="  AND a2.co_Cot BETWEEN "+objUti.codificar(txtCotDes.getText())+" AND "+objUti.codificar(txtCotHas.getText());
            }
            //</editor-fold>
            
            
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Seguimiento. */">
            if (txtSegDes.getText().length()>0 && txtSegHas.getText().length()>0)
            {
                blnConSeg=true;
                sqlFilSeg="  AND a1.co_Seg BETWEEN "+objUti.codificar(txtSegDes.getText())+" AND "+objUti.codificar(txtSegHas.getText());
                sqlFil+=sqlFilSeg;
            }
            //</editor-fold>
        
        }
        else  //Ventana de Consulta Emergente
        {
            blnConSeg=false;
            sqlFil=" AND a.co_emp="+strCodEmp+" AND a.co_loc="+strCodLoc+" AND a.co_tipDoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
        }
        
        return sqlFil;
    }
    
    
    private boolean cargarDetReg(String strFil) 
    {
        boolean blnRes = true;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;        
        String strConInv="";
        
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
                strSQL+="  SELECT Seg.co_seg, Seg.co_reg, Cot.co_emp as CodEmpCot, Cot.co_loc as CodLocCot, Loc.tx_nom as LocCot, Cot.co_cot as NumCot, \n";
                strSQL+="         Sol.co_emp as CodEmpSolTraInv, Sol.co_loc as CodLocSolTraInv, Sol.co_tipdoc as CodTipDocSolTraInv, t.tx_descor as DesCorSolTraInv,  \n";
                strSQL+="         t.tx_deslar as DesLarSolTraInv, Sol.co_doc as CodDocSolTraInv, Sol.ne_numdoc as NumDocSolTraInv, Sol.fe_doc as FecDocSolTraInv,\n";
                strSQL+="         Sol.co_bodOrg, BodOrg.tx_nom as NomBodOrg, Sol.co_bodDes, BodDes.tx_nom as NomBodDes, Sol.nd_pesTotKgr,  \n";
                strSQL+="         CliSol.tx_nom  as NomCliDes,  "+"\n";
                strSQL+="         MovInv.co_emp as CodEmpMovInv, MovInv.co_loc as CodLocMovInv, MovInv.co_tipdoc as CodTipDocMovInv, t1.tx_descor as DesCorMovInv, \n";
                strSQL+="         t1.tx_desLar as DesLarMovInv, MovInv.co_doc as CodDocMovInv, MovInv.ne_numdoc as NumDocMovInv, \n";
                strSQL+="         a9.co_emp as CodEmpGuiRem, a9.co_loc as CodLocGuiRem, a9.co_tipdoc as CodTipDocGuiRem, a10.tx_descor as DesCorGuiRem, \n";
                strSQL+="         a10.tx_deslar as DesLarGuiRem, a9.co_doc as CodDocGuiRem, \n";
                strSQL+="         CASE WHEN a9.ne_numdoc >0 THEN a9.ne_numdoc ELSE a9.ne_numOrdDes END as NumDocGuiRem, \n";
                strSQL+="         a12.tx_natDoc, a11.co_mnu as CodMnuConf, a11.co_emp as CodEmpIngEgr, a11.co_loc as CodLocIngEgr, a11.co_tipdoc as CodTipDocIngEgr, a12.tx_descor as DesCorIngEgr, \n";
                strSQL+="         a12.tx_desLar as DesLarIngEgr, a11.co_doc as CodDocIngEgr, a11.ne_numdoc as NumDocIngEgr, a13.co_bodGrp as CodBodGrpConf, \n";
                strSQL+="         CASE WHEN Sol.st_ConInv IS NULL THEN 'P' ELSE Sol.st_ConInv END as st_ConInv \n";
                strSQL+=" FROM tbm_cabSegMovInv as Seg \n";
                strSQL+=" LEFT JOIN tbm_cabcotven as Cot on (Seg.co_emprelcabcotven=Cot.co_emp and Seg.co_locrelcabcotven=Cot.co_loc and Seg.co_cotrelcabcotven=Cot.co_cot) \n";
                strSQL+=" LEFT JOIN tbm_loc as Loc on (Loc.co_emp=Cot.co_emp and Loc.co_loc=Cot.co_loc) \n";
                strSQL+=" LEFT JOIN tbm_cli as CliCot ON (Cot.co_emp=CliCot.co_emp AND Cot.co_cli=CliCot.co_cli)  \n";
                strSQL+=" LEFT JOIN tbm_cabSoltrainv Sol on (Seg.co_empRelCabSolTraInv=Sol.co_emp and Seg.co_locRelCabSolTraInv=Sol.co_loc and Seg.co_tipDocRelCabSolTraInv=Sol.co_tipdoc and Seg.co_docRelCabSolTraInv=Sol.co_doc) \n";
                strSQL+=" LEFT JOIN tbm_CabTipDoc as t ON (t.co_emp=Sol.co_emp AND t.co_loc=Sol.co_loc AND t.co_tipDoc=Sol.co_tipDoc)  \n";
                strSQL+=" LEFT JOIN tbm_bod as BodOrg ON (BodOrg.co_emp=Sol.co_emp AND BodOrg.co_bod=Sol.co_bodOrg) \n";
                strSQL+=" LEFT JOIN tbm_bod as BodDes ON (BodDes.co_emp=Sol.co_emp AND BodDes.co_bod=Sol.co_bodDes) \n";
                strSQL+=" LEFT JOIN tbm_emp as e ON (e.co_emp=BodDes.co_empDueBod)  \n";
                strSQL+=" LEFT JOIN tbm_cfgEmpRel as f ON (f.co_empOrg=2 AND f.co_EmpDes=BodDes.co_EmpDueBod) \n";
                strSQL+=" LEFT JOIN tbm_cli as CliSol ON (CliSol.co_emp=f.co_empOrg AND CliSol.co_cli=f.co_cliEmpOrg) \n";
                strSQL+=" LEFT JOIN tbm_cabmovinv a5 on (Sol.co_emp=a5.co_emp and Sol.co_loc=a5.co_loc and Sol.co_tipdoc=a5.co_tipdoc and Sol.co_doc=a5.co_doc ) "+"\n";
                strSQL+=" LEFT JOIN tbm_cabmovinv MovInv on (Seg.co_empRelCabMovInv=MovInv.co_emp and Seg.co_locRelCabMovInv=MovInv.co_loc and Seg.co_tipDocRelCabMovInv=MovInv.co_tipdoc and Seg.co_docRelCabMovInv=MovInv.co_doc ) \n";
                strSQL+=" LEFT JOIN tbm_CabTipDoc as t1 ON (t1.co_emp=MovInv.co_emp AND t1.co_loc=MovInv.co_loc AND t1.co_tipDoc=MovInv.co_tipDoc)  \n";
                strSQL+=" LEFT JOIN tbm_cabGuiRem as a9 on (Seg.co_emprelcabguirem=a9.co_emp and Seg.co_locrelcabguirem=a9.co_loc and Seg.co_tipdocrelcabguirem=a9.co_tipdoc and Seg.co_docrelcabguirem=a9.co_doc)\n";
                strSQL+=" LEFT JOIN tbm_CabTipDoc as a10 ON (a10.co_emp=a9.co_emp AND a10.co_loc=a9.co_loc AND a10.co_tipDoc=a9.co_tipDoc) \n";
                strSQL+=" LEFT JOIN tbm_cabIngEgrMerBod as a11 on (Seg.co_empRelCabIngEgrMerBod=a11.co_emp and Seg.co_locRelCabIngEgrMerBod=a11.co_loc and Seg.co_tipDocRelCabIngEgrMerBod=a11.co_tipdoc and Seg.co_docRelCabIngEgrMerBod=a11.co_doc) \n";
                strSQL+=" LEFT JOIN tbm_CabTipDoc as a12 ON (a12.co_emp=a11.co_emp AND a12.co_loc=a11.co_loc AND a12.co_tipDoc=a11.co_tipDoc)  \n";
                strSQL+=" LEFT JOIN tbr_bodEmpBodGrp as a13 ON (a13.co_Emp=a11.co_Emp AND a13.co_bod=a11.co_bod) \n";
               
                strSQL+=" INNER JOIN \n";
                strSQL+=" ( \n";
                
                if(blnConSeg && !blnConSol)
                {
                    strSQL+="    SELECT a1.co_Seg \n";
                    strSQL+="    FROM tbm_cabSegMovInv as a1 \n";
                    strSQL+="    WHERE co_Seg>0 "+sqlFilSeg +"\n";
                    strSQL+="    GROUP BY a1.co_Seg \n";
                    strSQL+="  UNION \n";
                }
                if(blnConCot)
                {
                    strCot="    SELECT a1.co_Seg \n";
                    strCot+="   FROM tbm_cabSegMovInv as a1 \n";
                    strCot+="   INNER JOIN tbm_cabCotVen as a2 ON (a2.co_emp=a1.co_empRelCabCotVen AND a2.co_loc=a1.co_locRelCabCotVen AND a2.co_cot=a1.co_CotRelCabCotVen) \n";
                    strCot+="   WHERE co_Seg>0 "+sqlFilCot +"\n";
                    strCot+="   GROUP BY a1.co_Seg \n";
                    if(!blnConSol)
                    { 
                        strSQL+=strCot+" \n";
                        strSQL+=" UNION \n";
                    }
                }
                strSQL+="    SELECT a1.co_Seg \n";
                strSQL+="    FROM tbm_cabSoltrainv as a  \n";
                strSQL+="    INNER JOIN tbm_cabSegMovInv as a1 ON (a1.co_empRelCabSolTraInv=a.co_emp and a1.co_locRelCabSolTraInv=a.co_loc and a1.co_tipDocRelCabSolTraInv=a.co_tipdoc and a1.co_docRelCabSolTraInv=a.co_doc) \n";
                strSQL+="    WHERE a1.co_Seg>0 "+strFil +"\n";
                if(blnConCot)
                    strSQL+="    AND a1.co_Seg in ("+strCot+")\n";
                strSQL+="    GROUP BY a1.co_Seg \n";
                strSQL+=" ) as Fil  ON (Fil.co_Seg=Seg.co_seg) \n";
                
                strSQL+=" GROUP BY Seg.co_seg, Seg.co_reg, Cot.co_emp, Cot.co_loc, Loc.tx_nom, Cot.co_cot, Sol.co_emp, Sol.co_loc, Sol.co_tipdoc, t.tx_descor,  \n";
                strSQL+="          t.tx_deslar, Sol.co_doc, Sol.ne_numdoc, Sol.fe_doc, Sol.co_bodOrg, BodOrg.tx_nom, Sol.co_bodDes, BodDes.tx_nom, Sol.nd_pesTotKgr,  \n";
                strSQL+="          CliSol.tx_nom,  MovInv.co_emp, MovInv.co_loc, MovInv.co_tipdoc, t1.tx_descor, t1.tx_desLar, MovInv.co_doc, MovInv.ne_numdoc, \n";
                strSQL+="          a9.co_emp, a9.co_loc, a9.co_tipdoc, a10.tx_descor, a10.tx_deslar, a9.co_doc,   \n";
                strSQL+="          CASE WHEN a9.ne_numdoc >0 THEN a9.ne_numdoc ELSE a9.ne_numOrdDes END,  \n";
                strSQL+="          a12.tx_natDoc, a11.co_mnu, a11.co_emp, a11.co_loc, a11.co_tipdoc, a12.tx_descor, a12.tx_desLar, a11.co_doc, a11.ne_numdoc, a13.co_bodGrp, Sol.st_ConInv  \n";
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
                    vecReg.add(INT_TBL_DESSOL, rstLoc.getString("NomCliDes"));
                    vecReg.add(INT_TBL_BODORGSOL, rstLoc.getString("NomBodOrg"));
                    vecReg.add(INT_TBL_BODDESSOL, rstLoc.getString("NomBodDes"));
                    vecReg.add(INT_TBL_PESSOL, rstLoc.getString("nd_pesTotKgr"));
                    vecReg.add(INT_TBL_BUTSOLTRAINV, "");

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
                    
                    strConInv=rstLoc.getString("st_ConInv"); 
                    vecReg.add(INT_TBL_CHKPRO,((strConInv.compareTo("C")==0)? Boolean.TRUE:(strConInv.compareTo("N")==0)? Boolean.TRUE: Boolean.FALSE));
                     
                    vecDat.add(vecReg);

                }
                rstLoc.close();
                stmLoc.close();
                conn.close();
                rstLoc = null;
                stmLoc = null;
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

    public boolean esImpar(int iNumero) 
    {
        if (iNumero%2!=0)
           return true;
        else
          return false;
    }

    /**
     * Esta función obtiene el query de los Tipos de Documentos 
     * configurados de acuerdo al usuario.
     */
    private String obtenerQueryTipoDocumento(int intTipo)
    {
        String strTipDoc="";
        try
        {
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
//            if (objParSis.getCodigoUsuario()==1)
//            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc ";
                if(intTipo!=1)
                  strSQL+="  ,a1.tx_desCor, a1.tx_desLar ";
                strSQL+=" FROM tbr_tipDocPrg as a ";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a1 ON (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc and a.co_tipDoc=a1.co_tipDoc)";
                strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
//            }
//            else
//            {
//                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+=" SELECT a1.co_tipDoc ";
//                if(intTipo!=1)
//                  strSQL+="  ,a1.tx_desCor, a1.tx_desLar ";
//                strSQL+=" FROM tbm_cabTipDoc AS a1";
//                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
//                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
//                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
//                strSQL+=" ORDER BY a1.tx_desCor";
//            }
            strTipDoc=strSQL;
            System.out.println("strTipDoc: "+strTipDoc);
        }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);   }
        return strTipDoc;
    }
    
}

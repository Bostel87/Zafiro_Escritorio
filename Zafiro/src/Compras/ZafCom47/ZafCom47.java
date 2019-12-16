/**
 *
 * ZafCom47.java
 *
 * Created on 06 de Mayo de 2013, 11:30 AM
 */
package Compras.ZafCom47;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**  
 *
 * @author Alex Morán 
 */
public class ZafCom47 extends javax.swing.JInternalFrame 
{
    //Constantes: JTable "TblDat"
    static final int INT_TBL_DAT_LIN   =0;
    static final int INT_TBL_DAT_CODEMPITM=1;
    static final int INT_TBL_DAT_CODITM=2;
    static final int INT_TBL_DAT_CODALTITM=3;
    static final int INT_TBL_DAT_CODALTDOS=4;
    static final int INT_TBL_DAT_NOMITM=5;
    static final int INT_TBL_DAT_UNIITM=6;
    
    static final int INT_TBL_DAT_CODEMPING=7;
    static final int INT_TBL_DAT_CODLOCING=8;
    static final int INT_TBL_DAT_CODTIPDOCING=9;
    static final int INT_TBL_DAT_DESTIPDOCING=10;
    static final int INT_TBL_DAT_CODDOCING=11;
    static final int INT_TBL_DAT_CODREGING=12;
    static final int INT_TBL_DAT_NUMDOCING=13;
    static final int INT_TBL_DAT_FECDOCING=14;

    static final int INT_TBL_DAT_CODBODING=15;
    static final int INT_TBL_DAT_NOMBODING=16;
    static final int INT_TBL_DAT_CANTOTING=17;
    static final int INT_TBL_DAT_PESTOTING=18;
    static final int INT_TBL_DAT_CODCLIING=19;
    static final int INT_TBL_DAT_NOMCLIING=20;    
    static final int INT_TBL_DAT_BUTDOCING=21;
    
    static final int INT_TBL_DAT_CHKPENING=22;
    static final int INT_TBL_DAT_CHKPARING=23;
    static final int INT_TBL_DAT_CHKTOTING=24;
    static final int INT_TBL_DAT_CANCONING=25;
    static final int INT_TBL_DAT_BUTCONING=26;
    static final int INT_TBL_DAT_CODEMPEGR=27;
    static final int INT_TBL_DAT_CODLOCEGR=28;
    static final int INT_TBL_DAT_CODTIPDOCEGR=29;
    static final int INT_TBL_DAT_DESTIPDOCCEGR=30;
    static final int INT_TBL_DAT_CODDOCEGR=31;
    static final int INT_TBL_DAT_CODREGEGR=32;
    static final int INT_TBL_DAT_NUMDOCEGR=33;
    static final int INT_TBL_DAT_FECDOCEGR=34;
    static final int INT_TBL_DAT_CODBODEGR=35;
    static final int INT_TBL_DAT_NOMBODEGR=36;
    static final int INT_TBL_DAT_BUTDOCEGR=37;
    
    static final int INT_TBL_DAT_CHKPENEGR=38;
    static final int INT_TBL_DAT_CHKPAREGR=39;
    static final int INT_TBL_DAT_CHKTOTEGR=40;
    static final int INT_TBL_DAT_CANCONEGR=41;
    static final int INT_TBL_DAT_BUTCONEGR=42;
    
    static final int INT_TBL_DAT_BUTGUIREM=43;
    static final int INT_TBL_DAT_ACCMNU =44;

    //Constante: Tamaño para las columnas de check
    static final int INT_TAM_CHKESTDOC=60;   
    
    //Constantes: JTable "TblBodIng"
    static final int INT_TBL_BIN_LIN   =0;
    static final int INT_TBL_BIN_CHKBOD=1;    
    static final int INT_TBL_BIN_CODBOD=2;
    static final int INT_TBL_BIN_NOMBOD=3;

    //Constantes: JTable "TblBodEgr"
    static final int INT_TBL_BEG_LIN   =0;
    static final int INT_TBL_BEG_CHKBOD=1;    
    static final int INT_TBL_BEG_CODBOD=2;
    static final int INT_TBL_BEG_NOMBOD=3;    
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblModTblDat, objTblModTblBodEgr ,objTblModTblBodIng;
    private ZafTblEdi objTblEdi;                              //Editor: Editor del JTable.
    private ZafTblCelRenChk objTblCelRenChkBE, objTblCelRenChkBI, ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE, objTblCelEdiChkBI;
    private ZafTblCelRenLbl objTblCelRenLblDatIng, objTblCelRenLblNumIng, objTblCelRenLblDatEgr, objTblCelRenLblNumEgr, objTblCelRenLblCen;
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;
    private ZafTblPopMnu objTblPopMnu;                        //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                              //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                              //JTable de ordenamiento.    
    private ZafTblFilCab objTblFilCab;
    private ZafVenCon vcoItm;
    private ZafPerUsr objPerUsr;
    
    private Vector vecDat, vecCab, vecReg;
    private boolean blnMarTodCanTblBodIng=true;
    private boolean blnMarTodCanTblBodEgr=true;
    
    private String strSQL, strAux;
    private String strBodEgr="", strBodIng="";
    private String strCodAlt, strNomItm;
    private String strVer = " v0.3 ";
    
    /** Crea una nueva instancia de la clase ZafCom47. */
    public ZafCom47(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            if (!configurarFrm())
                exitForm();
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
            objTblCelRenLblNumIng = new ZafTblCelRenLbl();
            objTblCelRenLblDatIng = new ZafTblCelRenLbl();
            objTblCelRenLblDatEgr = new ZafTblCelRenLbl();
            objTblCelRenLblNumEgr = new ZafTblCelRenLbl();
            objTblCelRenLblCen = new ZafTblCelRenLbl();
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setFlechaDerechaSeleccionada(true);
            objSelFec.setFlechaIzquierdaSeleccionada(true);
            panFilCab.add(objSelFec);
            objSelFec.setBounds(10, 4, 472, 72);
            
            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );

            //*******************************************************************************
            
            //Configurar objetos.
            txtCodItm.setVisible(false);
            
            //Configurar Ventanas de consulta.
            configurarVenConItm();
            configurarTblBodIng();
            configurarTblBodEgr();

            cargarBod("I");
            cargarBod("E");            
            
            //Configurar los JTables.
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
            vecCab=new Vector(45);  //Almacena las cabeceras
            vecCab.clear();

            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CODEMPITM, "Cód.Emp.Itm.");
            vecCab.add(INT_TBL_DAT_CODITM,    "Cód.Itm.");
            vecCab.add(INT_TBL_DAT_CODALTITM, "Cód.Alt.");
            vecCab.add(INT_TBL_DAT_CODALTDOS, "Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOMITM,    "Nombre del item");
            vecCab.add(INT_TBL_DAT_UNIITM,    "Unidad");

            vecCab.add(INT_TBL_DAT_CODEMPING,    "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCING,    "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCING, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESTIPDOCING, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCING,    "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_CODREGING,    "Cód.Reg.Ing.");
            vecCab.add(INT_TBL_DAT_NUMDOCING,    "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOCING,    "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODBODING,    "Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOMBODING,    "Bodega");
            vecCab.add(INT_TBL_DAT_CANTOTING,    "Cantidad");
            vecCab.add(INT_TBL_DAT_PESTOTING,    "Peso (Kg)");
            vecCab.add(INT_TBL_DAT_CODCLIING,    "Cód.Prv.");
            vecCab.add(INT_TBL_DAT_NOMCLIING,    "Proveedor");            
            vecCab.add(INT_TBL_DAT_BUTDOCING,    "..");

            vecCab.add(INT_TBL_DAT_CHKPENING, "Pendiente");
            vecCab.add(INT_TBL_DAT_CHKPARING, "Parcial");
            vecCab.add(INT_TBL_DAT_CHKTOTING, "Total");    
            vecCab.add(INT_TBL_DAT_CANCONING, "Can.Tot.Con.");    
            vecCab.add(INT_TBL_DAT_BUTCONING, "..");

            vecCab.add(INT_TBL_DAT_CODEMPEGR,     "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCEGR,     "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCEGR,  "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESTIPDOCCEGR, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCEGR,     "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_CODREGEGR,     "Cód.Reg.Egr.");
            vecCab.add(INT_TBL_DAT_NUMDOCEGR,     "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOCEGR,     "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODBODEGR,     "Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOMBODEGR,     "Bodega");
            vecCab.add(INT_TBL_DAT_BUTDOCEGR,     "..");

            vecCab.add(INT_TBL_DAT_CHKPENEGR, "Pendiente");
            vecCab.add(INT_TBL_DAT_CHKPAREGR, "Parcial");
            vecCab.add(INT_TBL_DAT_CHKTOTEGR, "Total");    
            vecCab.add(INT_TBL_DAT_CANCONEGR, "Can.Tot.Con.");    
            vecCab.add(INT_TBL_DAT_BUTCONEGR, "..");

            vecCab.add(INT_TBL_DAT_BUTGUIREM, "Gui.Rem.");
            vecCab.add(INT_TBL_DAT_ACCMNU, "");            
            
            //Configurar Jtable: Cabecera
            objTblModTblDat=new ZafTblMod();
            objTblModTblDat.setHeader(vecCab);
            tblDat.setModel(objTblModTblDat);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

             //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModTblDat.setColumnDataType(INT_TBL_DAT_CANTOTING, objTblModTblDat.INT_COL_DBL, new Integer(0), null);
            objTblModTblDat.setColumnDataType(INT_TBL_DAT_PESTOTING, objTblModTblDat.INT_COL_DBL, new Integer(0), null);
            objTblModTblDat.setColumnDataType(INT_TBL_DAT_CANCONING, objTblModTblDat.INT_COL_DBL, new Integer(0), null);
            objTblModTblDat.setColumnDataType(INT_TBL_DAT_CANCONEGR, objTblModTblDat.INT_COL_DBL, new Integer(0), null);            
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);               

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);      
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);                     
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTDOCING);
            vecAux.add("" + INT_TBL_DAT_BUTCONING);
            vecAux.add("" + INT_TBL_DAT_BUTDOCEGR);
            vecAux.add("" + INT_TBL_DAT_BUTCONEGR);
            vecAux.add("" + INT_TBL_DAT_BUTGUIREM);
            objTblModTblDat.addColumnasEditables(vecAux);
            vecAux=null;            
            
            //Configurar JTable: Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CODALTITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_DAT_NOMITM).setPreferredWidth(140);
            tcmAux.getColumn(INT_TBL_DAT_UNIITM).setPreferredWidth(50);

            tcmAux.getColumn(INT_TBL_DAT_DESTIPDOCING).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCING).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCING).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODBODING).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODING).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_CANTOTING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PESTOTING).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODCLIING).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_NOMCLIING).setPreferredWidth(120);            
            tcmAux.getColumn(INT_TBL_DAT_BUTDOCING).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_DAT_CHKPENING).setPreferredWidth(INT_TAM_CHKESTDOC);
            tcmAux.getColumn(INT_TBL_DAT_CHKPARING).setPreferredWidth(INT_TAM_CHKESTDOC);
            tcmAux.getColumn(INT_TBL_DAT_CHKTOTING).setPreferredWidth(INT_TAM_CHKESTDOC);
            tcmAux.getColumn(INT_TBL_DAT_CANCONING).setPreferredWidth(50);        
            tcmAux.getColumn(INT_TBL_DAT_BUTCONING).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_DAT_DESTIPDOCCEGR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCEGR).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCEGR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODBODEGR).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODEGR).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_BUTDOCEGR).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_DAT_CHKPENEGR).setPreferredWidth(INT_TAM_CHKESTDOC);
            tcmAux.getColumn(INT_TBL_DAT_CHKPAREGR).setPreferredWidth(INT_TAM_CHKESTDOC);
            tcmAux.getColumn(INT_TBL_DAT_CHKTOTEGR).setPreferredWidth(INT_TAM_CHKESTDOC);
            tcmAux.getColumn(INT_TBL_DAT_CANCONEGR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONEGR).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_DAT_BUTGUIREM).setPreferredWidth(40);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMPITM, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODITM, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMPING, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOCING, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCING, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOCING, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODREGING, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMPEGR, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOCEGR, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCEGR, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOCEGR, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_CODREGEGR, tblDat);
            objTblModTblDat.addSystemHiddenColumn(INT_TBL_DAT_ACCMNU, tblDat);
 
            //Agrupamiento de columnas
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);

            //Item
            ZafTblHeaColGrp objTblHeaColGrpTit = new ZafTblHeaColGrp("");
            objTblHeaColGrpTit.setHeight(16);

            ZafTblHeaColGrp objTblHeaColGrpSubTit = new ZafTblHeaColGrp("Item");
            objTblHeaColGrpSubTit.setHeight(16);

            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPITM));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODITM));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODALTITM));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODALTDOS));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_NOMITM));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_UNIITM));

            objTblHeaColGrpTit.add(objTblHeaColGrpSubTit);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            objTblHeaColGrpSubTit = null;
            objTblHeaColGrpTit = null;

            //Ingresos
            objTblHeaColGrpTit = new ZafTblHeaColGrp("INGRESOS");
            objTblHeaColGrpTit.setHeight(16);

            objTblHeaColGrpSubTit = new ZafTblHeaColGrp("Ingresos a Bodega" );
            objTblHeaColGrpSubTit.setHeight(16);

            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_DESTIPDOCING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_FECDOCING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODBODING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CANTOTING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_PESTOTING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODCLIING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_NOMCLIING));            
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_BUTDOCING));

            objTblHeaColGrpTit.add(objTblHeaColGrpSubTit);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            objTblHeaColGrpSubTit = null;

            objTblHeaColGrpSubTit = new ZafTblHeaColGrp("Confirmaciones" );
            objTblHeaColGrpSubTit.setHeight(16);

            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CHKPENING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CHKPARING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CHKTOTING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CANCONING));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONING));

            objTblHeaColGrpTit.add(objTblHeaColGrpSubTit);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            objTblHeaColGrpSubTit = null;
            objTblHeaColGrpTit = null;

            //Egresos
            objTblHeaColGrpTit = new ZafTblHeaColGrp("EGRESOS" );
            objTblHeaColGrpTit.setHeight(16);

            objTblHeaColGrpSubTit = new ZafTblHeaColGrp("Ordenes de Despacho" );
            objTblHeaColGrpSubTit.setHeight(16);

            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_DESTIPDOCCEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_FECDOCEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CODBODEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_BUTDOCEGR));

            objTblHeaColGrpTit.add(objTblHeaColGrpSubTit);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            objTblHeaColGrpSubTit = null;

            objTblHeaColGrpSubTit = new ZafTblHeaColGrp("Confirmaciones");
            objTblHeaColGrpSubTit.setHeight(16);

            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CHKPENEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CHKPAREGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CHKTOTEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_CANCONEGR));
            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONEGR));

            objTblHeaColGrpTit.add(objTblHeaColGrpSubTit);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            objTblHeaColGrpSubTit = null;

            objTblHeaColGrpSubTit = new ZafTblHeaColGrp("");
            objTblHeaColGrpSubTit.setHeight(16);

            objTblHeaColGrpSubTit.add(tcmAux.getColumn(INT_TBL_DAT_BUTGUIREM));

            objTblHeaColGrpTit.add(objTblHeaColGrpSubTit);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            objTblHeaColGrpSubTit=null;

            objTblHeaColGrpTit=null;            
            
            //Establecer formato
            objTblCelRenLblCen.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setCellRenderer(objTblCelRenLblCen);
            objTblCelRenLblCen = null;
            
            java.awt.Color colFonCol;
            colFonCol = new java.awt.Color(228,228,203);

            objTblCelRenLblNumIng.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumIng.setTipoFormato(objTblCelRenLblNumIng.INT_FOR_NUM);
            objTblCelRenLblNumIng.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            objTblCelRenLblNumIng.setBackground(colFonCol);

            tcmAux.getColumn(INT_TBL_DAT_CANTOTING).setCellRenderer(objTblCelRenLblNumIng);
            tcmAux.getColumn(INT_TBL_DAT_PESTOTING).setCellRenderer(objTblCelRenLblNumIng);
            tcmAux.getColumn(INT_TBL_DAT_CANCONING).setCellRenderer(objTblCelRenLblNumIng);

            objTblCelRenLblDatIng.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_DAT_DESTIPDOCING).setCellRenderer(objTblCelRenLblDatIng);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCING).setCellRenderer(objTblCelRenLblDatIng);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCING).setCellRenderer(objTblCelRenLblDatIng);
            tcmAux.getColumn(INT_TBL_DAT_CODBODING).setCellRenderer(objTblCelRenLblDatIng);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODING).setCellRenderer(objTblCelRenLblDatIng);
            tcmAux.getColumn(INT_TBL_DAT_CODCLIING).setCellRenderer(objTblCelRenLblDatIng);
            tcmAux.getColumn(INT_TBL_DAT_NOMCLIING).setCellRenderer(objTblCelRenLblDatIng);            

            ZafTblCelRenChkDat = new ZafTblCelRenChk();
            ZafTblCelRenChkDat.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_DAT_CHKPENING).setCellRenderer(ZafTblCelRenChkDat);
            tcmAux.getColumn(INT_TBL_DAT_CHKPARING).setCellRenderer(ZafTblCelRenChkDat);
            tcmAux.getColumn(INT_TBL_DAT_CHKTOTING).setCellRenderer(ZafTblCelRenChkDat);
            ZafTblCelRenChkDat = null;

            colFonCol = new java.awt.Color(255,221,187);

            objTblCelRenLblDatEgr.setBackground(colFonCol);        
            tcmAux.getColumn(INT_TBL_DAT_DESTIPDOCCEGR).setCellRenderer(objTblCelRenLblDatEgr);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCEGR).setCellRenderer(objTblCelRenLblDatEgr);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCEGR).setCellRenderer(objTblCelRenLblDatEgr);
            tcmAux.getColumn(INT_TBL_DAT_CODBODEGR).setCellRenderer(objTblCelRenLblDatEgr);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODEGR).setCellRenderer(objTblCelRenLblDatEgr);
            objTblCelRenLblDatEgr = null;

            ZafTblCelRenChkDat = new ZafTblCelRenChk();
            ZafTblCelRenChkDat.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_DAT_CHKPENEGR).setCellRenderer(ZafTblCelRenChkDat);
            tcmAux.getColumn(INT_TBL_DAT_CHKPAREGR).setCellRenderer(ZafTblCelRenChkDat);
            tcmAux.getColumn(INT_TBL_DAT_CHKTOTEGR).setCellRenderer(ZafTblCelRenChkDat);
            ZafTblCelRenChkDat = null;

            objTblCelRenLblNumEgr.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumEgr.setTipoFormato(objTblCelRenLblNumEgr.INT_FOR_NUM);
            objTblCelRenLblNumEgr.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            objTblCelRenLblNumEgr.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_DAT_CANCONEGR).setCellRenderer(objTblCelRenLblNumEgr);
            objTblCelRenLblNumEgr = null;

            objTblCelRenButDG = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTDOCING).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONING).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTDOCEGR).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONEGR).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTGUIREM).setCellRenderer(objTblCelRenButDG);            

            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter(){
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt){
                switch (objTblCelRenButDG.getColumnRender()){
                    case INT_TBL_DAT_BUTDOCING:
                        if( objPerUsr.isOpcionEnabled(2480) ){
                            if (objTblModTblDat.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_CODEMPING)==null){
                                objTblCelRenButDG.setText("");
                            } else{
                                objTblCelRenButDG.setText("...");
                            }                            
                        } else{
                            objTblCelRenButDG.setText("");
                        }
                        break;

                    case INT_TBL_DAT_BUTCONING:
                        if (objTblModTblDat.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_CHKPENING).toString().equals("true"))
                            objTblCelRenButDG.setText("");
                        else
                            objTblCelRenButDG.setText("...");
                        break;

                    case INT_TBL_DAT_BUTDOCEGR:
                        if (objTblModTblDat.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_CODEMPEGR)==null){
                            objTblCelRenButDG.setText("");
                        } else{
                            objTblCelRenButDG.setText("...");
                        }
                        break;

                    case INT_TBL_DAT_BUTCONEGR:
                        if (objTblModTblDat.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_CODEMPEGR)==null){
                            objTblCelRenButDG.setText("");
                        } else{
                            if (objTblModTblDat.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_CHKPENEGR).toString().equals("true"))
                                objTblCelRenButDG.setText("");
                            else
                                objTblCelRenButDG.setText("...");
                        }
                        break;

                    case INT_TBL_DAT_BUTGUIREM:
                        if (objTblModTblDat.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_CODEMPEGR)==null){
                            objTblCelRenButDG.setText("");
                        } else{
                            if(objTblModTblDat.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DAT_CHKPENEGR).toString().equals("true"))
                                objTblCelRenButDG.setText("");
                            else
                                objTblCelRenButDG.setText("...");
                        }
                        break;
                    }
                }
            });

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGenDG = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTDOCING).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONING).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTDOCEGR).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONEGR).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_DAT_BUTGUIREM).setCellEditor(objTblCelEdiButGenDG);

            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter(){
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();

                    if (intFilSel!=-1){
                        switch (intColSel){
                            case INT_TBL_DAT_BUTDOCING:
                                if( objPerUsr.isOpcionEnabled(2480) ){
                                    if (objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPING)==null)
                                        objTblCelEdiButGenDG.setCancelarEdicion(true);                                
                                } else {
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                }
                                break;

                            case INT_TBL_DAT_BUTCONING:
                                if(objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKPENING).toString().equals("true"))
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                break;                           

                            case INT_TBL_DAT_BUTDOCEGR:
                                if (objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPEGR)==null)
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                break;

                            case INT_TBL_DAT_BUTCONEGR:
                                if (objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPEGR)==null){
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                } else{                            
                                    if(objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKPENEGR).toString().equals("true"))
                                        objTblCelEdiButGenDG.setCancelarEdicion(true);
                                }
                                break;                        

                            case INT_TBL_DAT_BUTGUIREM:
                                if (objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPEGR)==null){
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                } else{                            
                                    if(objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKPENEGR).toString().equals("true"))
                                        objTblCelEdiButGenDG.setCancelarEdicion(true);
                                }
                                break;  
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intColSel=tblDat.getSelectedColumn();

                    if (intFilSel!=-1){
                        switch (intColSel){
                            case INT_TBL_DAT_BUTDOCING:
                                abrirFrm(objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPING).toString()
                                        ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOCING).toString()
                                        ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOCING).toString()
                                        ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOCING).toString()
                                );
                                break;

                            case INT_TBL_DAT_BUTCONING:
                                mostrarVentanaConf(  objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPING).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOCING).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOCING).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOCING).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODREGING).toString()
                                                    , 2
                                );
                                break;                            

                            case INT_TBL_DAT_BUTDOCEGR:
                                mostrarVentanaOrdDes( objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPEGR).toString()
                                                     ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOCEGR).toString()
                                                     ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOCEGR).toString()
                                                     ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOCEGR).toString()
                                );
                                break;                            

                            case INT_TBL_DAT_BUTCONEGR:
                                mostrarVentanaConf(  objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPEGR).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOCEGR).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOCEGR).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOCEGR).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODREGEGR).toString()
                                                    , 1
                                );
                                break;

                            case INT_TBL_DAT_BUTGUIREM:
                                mostrarVentanaGuiRem(objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPEGR).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOCEGR).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOCEGR).toString()
                                                    ,objTblModTblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOCEGR).toString()
                                );
                                break;
                        }
                    }
                }
                });

            //Establecer modo de Operación
            objTblModTblDat.setModoOperacion(objTblModTblDat.INT_TBL_EDI);
            
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

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            
            switch (intCol){
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODEMPITM:
                    strMsg="Código de la empresa del ítem";
                    break;
                case INT_TBL_DAT_CODITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_CODALTITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_CODALTDOS:
                    strMsg="Código alterno del item 2";
                    break;                    
                case INT_TBL_DAT_NOMITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_UNIITM:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CODEMPING:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOCING:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOCING:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DESTIPDOCING:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_CODDOCING:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUMDOCING:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FECDOCING:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_CODBODING:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_DAT_NOMBODING:
                    strMsg="Nombre de la bodega";
                    break;
                case INT_TBL_DAT_CANTOTING:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_DAT_PESTOTING:
                    strMsg="Peso (Kg)";
                    break;
                case INT_TBL_DAT_CODCLIING:
                    strMsg="Código del proveedor";
                    break;
                case INT_TBL_DAT_NOMCLIING:
                    strMsg="Nombre del proveedor";
                    break;                    
                case INT_TBL_DAT_BUTDOCING:
                    strMsg="Muestra el documento de ingreso a bodega";
                    break;
                case INT_TBL_DAT_CHKPENING:
                    strMsg="Confirmación pendiente";
                    break;
                case INT_TBL_DAT_CHKPARING:
                    strMsg="Confirmación parcial";
                    break;
                case INT_TBL_DAT_CHKTOTING:
                    strMsg="Confirmación total";
                    break;
                case INT_TBL_DAT_CANCONING:
                    strMsg="Cantidad total confirmada";
                    break;
                case INT_TBL_DAT_BUTCONING:
                    strMsg="Muestra el listado de confirmaciones";
                    break; 
                case INT_TBL_DAT_CODEMPEGR:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOCEGR:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOCEGR:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DESTIPDOCCEGR:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_CODDOCEGR:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUMDOCEGR:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FECDOCEGR:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_CODBODEGR:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_DAT_NOMBODEGR:
                    strMsg="Nombre de la bodega";
                    break;
                case INT_TBL_DAT_BUTDOCEGR:
                    strMsg="Muestra la orden de despacho";
                    break;
                case INT_TBL_DAT_CHKPENEGR:
                    strMsg="Confirmación pendiente";
                    break;
                case INT_TBL_DAT_CHKPAREGR:
                    strMsg="Confirmación parcial";
                    break;
                case INT_TBL_DAT_CHKTOTEGR:
                    strMsg="Confirmación total";
                    break;
                case INT_TBL_DAT_CANCONEGR:
                    strMsg="Cantidad total confirmada";
                    break;
                case INT_TBL_DAT_BUTCONEGR:
                    strMsg="Muestra el listado de confirmaciones";
                    break;                    
                case INT_TBL_DAT_BUTGUIREM:
                    strMsg="Muestra el listado de guías de remisión";
                    break;
            }
            
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private void mostrarVentanaOrdDes(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
           Compras.ZafCom23.ZafCom23 obj1 = new  Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
           this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
           obj1.show();
    }

    private void mostrarVentanaConf(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodReg, int inTipConf ){
           Compras.ZafCom47.ZafCom47_01 obj1 = new  Compras.ZafCom47.ZafCom47_01(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strCodReg, inTipConf );
           this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
           obj1.show();
    }
  
    private void mostrarVentanaGuiRem(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
           Compras.ZafCom47.ZafCom47_02 obj1 = new  Compras.ZafCom47.ZafCom47_02(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
           this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
           obj1.show();
    }

    private boolean abrirFrm(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
        String strAux;
        int intFilSel;
        boolean blnRes=true;
        
        try{
            intFilSel=tblDat.getSelectedRow();
            if (!((tblDat.getSelectedColumn()==-1) || (intFilSel==-1))) {
                strAux=tblDat.getValueAt(intFilSel,INT_TBL_DAT_ACCMNU)==null?"":tblDat.getValueAt(intFilSel,INT_TBL_DAT_ACCMNU).toString();
                if (!strAux.equals("")){
                   invocarClase(strAux, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc) );
                }
            }
        }
        catch(Exception e){
           blnRes=false;
           objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean invocarClase(String clase, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[5];
            objCla[0]=objParSis.getClass();
            objCla[1]= Integer.class; //   String.class;
            objCla[2]= Integer.class;
            objCla[3]= Integer.class;
            objCla[4]= Integer.class;
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[5];
            objObj[0]=objParSis;
            objObj[1]= Integer.valueOf(intCodEmp);
            objObj[2]= Integer.valueOf(intCodLoc);;
            objObj[3]= Integer.valueOf(intCodTipDoc);;
            objObj[4]= Integer.valueOf(intCodDoc);;

            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
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
        catch (ClassNotFoundException e)   {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (NoSuchMethodException e)    {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (SecurityException e)        {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (InstantiationException e)   {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (IllegalAccessException e)   {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (IllegalArgumentException e) {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (java.lang.reflect.InvocationTargetException e)  {    blnRes=false;     objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }    
    
    private boolean configurarTblBodIng()
    {
        boolean blnRes = false;
        vecDat=new Vector();    //Almacena los datos
        vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_BIN_LIN,"");
        vecCab.add(INT_TBL_BIN_CHKBOD," ");
        vecCab.add(INT_TBL_BIN_CODBOD,"Cód.Bod");
        vecCab.add(INT_TBL_BIN_NOMBOD,"Bodega");

        objTblModTblBodIng=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModTblBodIng.setHeader(vecCab);
        tblBodIng.setModel(objTblModTblBodIng);

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblBodIng, INT_TBL_BIN_LIN);

        tblBodIng.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblBodIng.getColumnModel();
        tblBodIng.getTableHeader().setReorderingAllowed(false);
        
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        tblBodIng.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBodIng());

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_BIN_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_BIN_CHKBOD).setPreferredWidth(30);        
        tcmAux.getColumn(INT_TBL_BIN_CODBOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BIN_NOMBOD).setPreferredWidth(180);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_BIN_CHKBOD);
        objTblModTblBodIng.setColumnasEditables(vecAux);
        vecAux=null;
        
        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblBodIng);

        objTblCelRenChkBI = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_BIN_CHKBOD).setCellRenderer(objTblCelRenChkBI);
        objTblCelRenChkBI=null;

        objTblCelEdiChkBI = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_BIN_CHKBOD).setCellEditor(objTblCelEdiChkBI);
        objTblCelEdiChkBI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });
         
        //Configurar JTable: Establecer los listener para el TableHeader.
        tblBodIng.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClickedBI(evt);
            }
        });
        
        objTblModTblBodIng.setModoOperacion(objTblModTblBodIng.INT_TBL_EDI);
        
        return blnRes;
    }
    
    /**
    * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
    * del mouse (mover el mouse; arrastrar y soltar).
    * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
    * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
    * resulta muy corto para mostrar leyendas que requieren más espacio.
    */
    private class ZafMouMotAdaBodIng extends java.awt.event.MouseMotionAdapter{
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol = tblBodIng.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BIN_LIN:    strMsg=""; break;              
                case INT_TBL_BIN_CODBOD: strMsg="Código de la bodega"; break;
                case INT_TBL_BIN_NOMBOD: strMsg="Nombre de la bodega"; break;
                default: strMsg=""; break;
            }
            tblBodIng.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private void tblDatMouseClickedBI(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try
        {
            intNumFil=tblBodIng.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.

            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBodIng.columnAtPoint(evt.getPoint())==INT_TBL_BIN_CHKBOD) {
                if (blnMarTodCanTblBodIng){
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblBodIng.setValueAt( new Boolean(false) , i, INT_TBL_BIN_CHKBOD);
                    }
                    blnMarTodCanTblBodIng=false;
                } else {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblBodIng.setValueAt( new Boolean(true) , i, INT_TBL_BIN_CHKBOD);
                    }
                    blnMarTodCanTblBodIng=true;
                }
            }
        }catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    
    private boolean configurarTblBodEgr()
    {
        boolean blnRes=false;
        vecDat=new Vector();    //Almacena los datos
        vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_BEG_LIN,"");
        vecCab.add(INT_TBL_BEG_CHKBOD," ");
        vecCab.add(INT_TBL_BEG_CODBOD,"Cód.Bod");
        vecCab.add(INT_TBL_BEG_NOMBOD,"Bodega");

        objTblModTblBodEgr=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModTblBodEgr.setHeader(vecCab);
        tblBodEgr.setModel(objTblModTblBodEgr);

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblBodEgr, INT_TBL_BEG_LIN);

        tblBodEgr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblBodEgr.getColumnModel();
        tblBodEgr.getTableHeader().setReorderingAllowed(false);
       
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        tblBodEgr.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBodEgr());
        
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_BEG_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_BEG_CHKBOD).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_BEG_CODBOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BEG_NOMBOD).setPreferredWidth(180);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_BEG_CHKBOD);
        objTblModTblBodEgr.setColumnasEditables(vecAux);
        vecAux=null;
        
        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblBodEgr);

        objTblCelRenChkBE = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_BEG_CHKBOD).setCellRenderer(objTblCelRenChkBE);
        objTblCelRenChkBE=null;

        objTblCelEdiChkBE = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_BEG_CHKBOD).setCellEditor(objTblCelEdiChkBE);
        objTblCelEdiChkBE.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
        });

        //Configurar JTable: Establecer los listener para el TableHeader.
        tblBodEgr.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClickedBE(evt);
            }
        });

        objTblModTblBodEgr.setModoOperacion(objTblModTblBodEgr.INT_TBL_EDI);

        return blnRes;
    }
    
    /**
    * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
    * del mouse (mover el mouse; arrastrar y soltar).
    * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
    * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
    * resulta muy corto para mostrar leyendas que requieren más espacio.
    */
    private class ZafMouMotAdaBodEgr extends java.awt.event.MouseMotionAdapter{
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol = tblBodEgr.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BEG_LIN: strMsg=""; break;              
                case INT_TBL_BEG_CODBOD: strMsg="Código de la bodega"; break;
                case INT_TBL_BEG_NOMBOD: strMsg="Nombre de la bodega"; break;
                default: strMsg=""; break;
            }
            tblBodEgr.getTableHeader().setToolTipText(strMsg);
        }
    }
        
    private void tblDatMouseClickedBE(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try
        {
            intNumFil=tblBodEgr.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.

            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBodEgr.columnAtPoint(evt.getPoint())==INT_TBL_BEG_CHKBOD) {
                if (blnMarTodCanTblBodEgr){
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblBodEgr.setValueAt( new Boolean(false) , i, INT_TBL_BEG_CHKBOD);
                    }
                    blnMarTodCanTblBodEgr=false;
                } else {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblBodEgr.setValueAt( new Boolean(true) , i, INT_TBL_BEG_CHKBOD);
                    }
                    blnMarTodCanTblBodEgr=true;
                }
            }
        }catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    
    private boolean configurarVenConItm()
    {
        boolean blnRes = true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_itm");
            arlCam.add("tx_codAlt");
            arlCam.add("tx_nomItm");
            arlCam.add("tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e) {
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
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    } else{
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        } else{
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText())){
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    } else{
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE){
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        } else{
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
    * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
    * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
    * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
    * @return true: Si se pudo consultar los registros.
    * <BR>false: En el caso contrario.
    */
    private boolean cargarBod(String strTipBod){
        boolean blnRes=true;        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                if (objParSis.getCodigoUsuario()==1){ //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    strSQL+=" SELECT a2.co_bod, a2.tx_nom ";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    rst=stm.executeQuery(strSQL);
                }
                else {
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSQL+=" SELECT a.co_bod, a2.tx_nom FROM tbr_bodLocPrgUsr as a ";
                        strSQL+=" INNER JOIN tbm_bod as a2 ON (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod ) ";
                        strSQL+=" WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal();
                        strSQL+=" AND a.co_usr="+objParSis.getCodigoUsuario()+" AND  a.co_mnu="+objParSis.getCodigoMenu();
                        strSQL+=" AND a.tx_natBod IN  ('A', '" + strTipBod + "') "; 
                        strSQL+=" GROUP BY a.co_bod, a2.tx_nom";
                        strSQL+=" ORDER by co_bod ";
                    }
                    else{
                        strSQL+=" SELECT a1.co_bodgrp as co_bod, a2.tx_nom ";
                        strSQL+=" FROM tbr_bodLocPrgUsr as a ";
                        strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1  on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod )";
                        strSQL+=" INNER JOIN tbm_bod as a2  on (a2.co_emp=a1.co_empgrp and a2.co_bod=a1.co_bodgrp ) ";
                        strSQL+=" WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal();
                        strSQL+=" AND a.co_usr="+objParSis.getCodigoUsuario()+" AND  a.co_mnu="+objParSis.getCodigoMenu();
                        strSQL+=" AND a.tx_natBod IN  ('A', '" + strTipBod + "') ";
                        strSQL+=" GROUP BY a1.co_bodgrp, a2.tx_nom ";
                        strSQL+=" ORDER BY co_bodgrp ";
                    }
                }
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BIN_LIN, "");
                    vecReg.add(INT_TBL_BIN_CHKBOD, new Boolean(true));                    
                    vecReg.add(INT_TBL_BIN_CODBOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BIN_NOMBOD, rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                
                //Asignar vectores al modelo.              
                if(strTipBod.equals("I")){
                  objTblModTblBodIng.setData(vecDat);
                  tblBodIng.setModel(objTblModTblBodIng);
                } else{
                  objTblModTblBodEgr.setData(vecDat);
                  tblBodEgr.setModel(objTblModTblBodEgr);                  
                }
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)      {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        scrBar = new javax.swing.JScrollPane();
        panFilCab = new javax.swing.JPanel();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butSol = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNotRel = new javax.swing.JPanel();
        chkNotRelIngPen = new javax.swing.JCheckBox();
        chkNotRelIngPar = new javax.swing.JCheckBox();
        chkNotRelIngTot = new javax.swing.JCheckBox();
        panRel = new javax.swing.JPanel();
        chkRelIngPen = new javax.swing.JCheckBox();
        chkRelIngPar = new javax.swing.JCheckBox();
        chkRelIngTot = new javax.swing.JCheckBox();
        chkRelIngPenEgrTot = new javax.swing.JCheckBox();
        chkRelIngParEgrPar = new javax.swing.JCheckBox();
        chkRelIngParEgrTot = new javax.swing.JCheckBox();
        chkRelIngPenEgrPen = new javax.swing.JCheckBox();
        chkRelIngPenEgrPar = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBodIng = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblBodEgr = new javax.swing.JTable();
        txtCodAlt = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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

        lblTit.setText("jLabel1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        scrBar.setPreferredSize(new java.awt.Dimension(0, 600));

        panFilCab.setAutoscrolls(true);
        panFilCab.setPreferredSize(new java.awt.Dimension(0, 550));
        panFilCab.setLayout(null);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 20, 100, 15);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 20, 130, 20);

        panFilCab.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(356, 290, 260, 52);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Item:");
        panFilCab.add(lblCli);
        lblCli.setBounds(30, 268, 40, 20);
        panFilCab.add(txtCodItm);
        txtCodItm.setBounds(77, 269, 56, 20);

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
        panFilCab.add(txtNomItm);
        txtNomItm.setBounds(169, 269, 405, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panFilCab.add(butSol);
        butSol.setBounds(575, 269, 20, 20);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel5.setLayout(null);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Desde:");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(12, 20, 40, 15);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        jPanel5.add(txtCodAltDes);
        txtCodAltDes.setBounds(52, 20, 80, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Hasta:");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(148, 20, 31, 15);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        jPanel5.add(txtCodAltHas);
        txtCodAltHas.setBounds(184, 20, 80, 20);

        panFilCab.add(jPanel5);
        jPanel5.setBounds(25, 290, 330, 50);

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los Items");
        panFilCab.add(optTod);
        optTod.setBounds(10, 223, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los Items que cumplan el criterio seleccionado");
        panFilCab.add(optFil);
        optFil.setBounds(10, 243, 330, 20);

        panNotRel.setBorder(javax.swing.BorderFactory.createTitledBorder("No relacionadas"));
        panNotRel.setLayout(null);

        chkNotRelIngPen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkNotRelIngPen.setSelected(true);
        chkNotRelIngPen.setText("Ingresos: Pendientes");
        chkNotRelIngPen.setActionCommand("Ingresos: Pendiente");
        panNotRel.add(chkNotRelIngPen);
        chkNotRelIngPen.setBounds(10, 20, 220, 20);

        chkNotRelIngPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkNotRelIngPar.setSelected(true);
        chkNotRelIngPar.setText("Ingresos: Parcial");
        panNotRel.add(chkNotRelIngPar);
        chkNotRelIngPar.setBounds(10, 40, 220, 20);

        chkNotRelIngTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkNotRelIngTot.setText("Ingresos: Total");
        panNotRel.add(chkNotRelIngTot);
        chkNotRelIngTot.setBounds(10, 60, 220, 20);

        panFilCab.add(panNotRel);
        panNotRel.setBounds(340, 350, 310, 200);

        panRel.setBorder(javax.swing.BorderFactory.createTitledBorder("Relacionadas"));
        panRel.setLayout(null);

        chkRelIngPen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngPen.setSelected(true);
        chkRelIngPen.setText("Ingresos: Pendiente");
        chkRelIngPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRelIngPenActionPerformed(evt);
            }
        });
        panRel.add(chkRelIngPen);
        chkRelIngPen.setBounds(10, 20, 220, 20);

        chkRelIngPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngPar.setSelected(true);
        chkRelIngPar.setText("Ingresos: Parcial");
        chkRelIngPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRelIngParActionPerformed(evt);
            }
        });
        panRel.add(chkRelIngPar);
        chkRelIngPar.setBounds(10, 100, 220, 20);

        chkRelIngTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngTot.setText("Ingresos: Total");
        panRel.add(chkRelIngTot);
        chkRelIngTot.setBounds(10, 160, 220, 20);

        chkRelIngPenEgrTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngPenEgrTot.setSelected(true);
        chkRelIngPenEgrTot.setText("Egresos: Total");
        panRel.add(chkRelIngPenEgrTot);
        chkRelIngPenEgrTot.setBounds(30, 80, 220, 20);

        chkRelIngParEgrPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngParEgrPar.setSelected(true);
        chkRelIngParEgrPar.setText("Egresos: Parcial");
        panRel.add(chkRelIngParEgrPar);
        chkRelIngParEgrPar.setBounds(30, 120, 220, 20);

        chkRelIngParEgrTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngParEgrTot.setSelected(true);
        chkRelIngParEgrTot.setText("Egresos: Total");
        panRel.add(chkRelIngParEgrTot);
        chkRelIngParEgrTot.setBounds(30, 140, 220, 20);

        chkRelIngPenEgrPen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngPenEgrPen.setSelected(true);
        chkRelIngPenEgrPen.setText("Egresos: Pendiente");
        panRel.add(chkRelIngPenEgrPen);
        chkRelIngPenEgrPen.setBounds(30, 40, 220, 20);

        chkRelIngPenEgrPar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkRelIngPenEgrPar.setSelected(true);
        chkRelIngPenEgrPar.setText("Egresos: Parcial");
        panRel.add(chkRelIngPenEgrPar);
        chkRelIngPenEgrPar.setBounds(30, 60, 220, 20);

        panFilCab.add(panRel);
        panRel.setBounds(10, 350, 300, 200);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingresos: Listado de bodegas"));
        jPanel4.setPreferredSize(new java.awt.Dimension(294, 100));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        tblBodIng.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblBodIng);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panFilCab.add(jPanel4);
        jPanel4.setBounds(10, 83, 325, 140);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Egresos: Listado de bodegas"));
        jPanel7.setPreferredSize(new java.awt.Dimension(294, 100));
        jPanel7.setRequestFocusEnabled(false);
        jPanel7.setLayout(new java.awt.BorderLayout());

        tblBodEgr.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblBodEgr);

        jPanel7.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        panFilCab.add(jPanel7);
        jPanel7.setBounds(335, 83, 325, 140);

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
        panFilCab.add(txtCodAlt);
        txtCodAlt.setBounds(77, 269, 92, 20);

        scrBar.setViewportView(panFilCab);

        tabFrm.addTab("Filtro", scrBar);

        jPanel9.setLayout(new java.awt.BorderLayout());

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
        jScrollPane4.setViewportView(tblDat);

        jPanel9.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanel9);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

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

        jPanel2.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel2.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) {
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
}//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if ( txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
}//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
}//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
}//GEN-LAST:event_txtCodAltDesFocusGained

    private void butSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0){
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butSolActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm)){
            if (txtNomItm.getText().equals("")){
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            } else{
                mostrarVenConItm(2);
            }
        } else
            txtNomItm.setText(strNomItm);
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0){
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
}//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
}//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void chkRelIngPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRelIngPenActionPerformed
        chkRelIngPenEgrPen.setSelected(chkRelIngPen.isSelected());
        chkRelIngPenEgrPar.setSelected(chkRelIngPen.isSelected());
        chkRelIngPenEgrTot.setSelected(chkRelIngPen.isSelected());            
    }//GEN-LAST:event_chkRelIngPenActionPerformed

    private void chkRelIngParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRelIngParActionPerformed
        chkRelIngParEgrPar.setSelected(chkRelIngPar.isSelected());
        chkRelIngParEgrTot.setSelected(chkRelIngPar.isSelected());            
    }//GEN-LAST:event_chkRelIngParActionPerformed

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt)){
            if (txtCodAlt.getText().equals("")){
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            } else{
                mostrarVenConItm(1);
            }
        } else
            txtCodAlt.setText(strCodAlt);
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost


    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butSol;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkNotRelIngPar;
    private javax.swing.JCheckBox chkNotRelIngPen;
    private javax.swing.JCheckBox chkNotRelIngTot;
    private javax.swing.JCheckBox chkRelIngPar;
    private javax.swing.JCheckBox chkRelIngParEgrPar;
    private javax.swing.JCheckBox chkRelIngParEgrTot;
    private javax.swing.JCheckBox chkRelIngPen;
    private javax.swing.JCheckBox chkRelIngPenEgrPar;
    private javax.swing.JCheckBox chkRelIngPenEgrPen;
    private javax.swing.JCheckBox chkRelIngPenEgrTot;
    private javax.swing.JCheckBox chkRelIngTot;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panNotRel;
    private javax.swing.JPanel panRel;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrBar;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBodEgr;
    private javax.swing.JTable tblBodIng;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
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
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
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
    private String getConSQLAdiCamTer(String strCam, String strCad){
        byte i;
        String strRes="";
        try{
            if (strCad.length()>0){
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens()){
                    if (i==0)
                        strRes+=" (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
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
    
    private class ZafThreadGUI extends Thread{
        public void run(){
            pgrSis.setIndeterminate(true);
            if (!cargarDetReg()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }

            pgrSis.setIndeterminate(false);
            objThrGUI=null;
        }
    }

    /**
    * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
    * @return true: Si se pudo consultar los registros.
    * <BR>false: En el caso contrario.
    */
    private boolean cargarDetReg(){
        boolean blnRes = true;
        String strSql = "", sqlFec = "";
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null){
                stm = con.createStatement();

                //<editor-fold defaultstate="collapsed" desc="/* Filtros */">
                //Filtro fechas
                sqlFec = "";
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Búsqueda por rangos
                        sqlFec+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFec+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFec+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                
                //Filtro bodegas ingresos
                strBodIng = "";
                for (int j=0; j< tblBodIng.getRowCount(); j++){
                    if (tblBodIng.getValueAt(j, INT_TBL_BIN_CHKBOD)!=null){
                        if (tblBodIng.getValueAt(j, INT_TBL_BIN_CHKBOD).toString().equals("true")){
                            if(strBodIng.equals("")) strBodIng += tblBodIng.getValueAt(j, INT_TBL_BIN_CODBOD).toString();
                            else strBodIng += ","+tblBodIng.getValueAt(j, INT_TBL_BIN_CODBOD).toString();
                        }
                    }
                }

                //Filtro bodegas egresos
                strBodEgr = "";
                for (int j=0; j< tblBodEgr.getRowCount(); j++){
                    if (tblBodEgr.getValueAt(j, INT_TBL_BEG_CHKBOD)!=null) {
                        if (tblBodEgr.getValueAt(j, INT_TBL_BEG_CHKBOD).toString().equals("true")){
                            if(strBodEgr.equals("")) strBodEgr += tblBodEgr.getValueAt(j, INT_TBL_BEG_CODBOD).toString();
                            else strBodEgr += ","+tblBodEgr.getValueAt(j, INT_TBL_BEG_CODBOD).toString();
                        }
                    }
                }
                
                //Filtro item
                String sqlEstConRel = "", sqlEstConNotRel = "", sqlItmIng = "", sqlItmEgr = "";
                
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0){
                    sqlItmIng+=" AND ((LOWER(a7.tx_codalt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a7.tx_codalt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                    sqlItmEgr+=" AND ((LOWER(a5.tx_codalt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a5.tx_codalt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                
                if (txtCodAlt.getText().length()>0){
                    sqlItmIng+=" AND LOWER(a7.tx_codalt) LIKE '%"+txtCodAlt.getText().replaceAll("'", "''").toLowerCase()+"%' ";
                    sqlItmEgr+=" AND LOWER(a5.tx_codalt) LIKE '%"+txtCodAlt.getText().replaceAll("'", "''").toLowerCase()+"%' ";
                }
                
                if (txtCodAltItmTer.getText().length()>0){
                    sqlItmIng+=" AND "+getConSQLAdiCamTer("a7.tx_codAlt", txtCodAltItmTer.getText());
                    sqlItmEgr+=" AND "+getConSQLAdiCamTer("a5.tx_codAlt", txtCodAltItmTer.getText());
                }

                //Filtro estados relacionadas
                String strEstRelIng = "";
                if(chkRelIngPen.isSelected()){
                    strEstRelIng = "'P'";                    
                    String strEstEgr = "";
                    if(chkRelIngPenEgrPen.isSelected()){   strEstEgr = "'P'";   }

                    if(chkRelIngPenEgrPar.isSelected()){
                        if(!strEstEgr.equals("")) strEstEgr += ",";
                        strEstEgr += "'E'";
                    }

                    if(chkRelIngPenEgrTot.isSelected()){
                        if(!strEstEgr.equals("")) strEstEgr += ",";
                        strEstEgr += "'C'";
                    }

                    sqlEstConRel = "WHERE (st_confIng = 'P'";
                    
                    if(!strEstEgr.equals("")){       sqlEstConRel += " AND st_confEgr IN ("+strEstEgr+")";         }
                    
                    sqlEstConRel += ") ";
                }

                
                if(chkRelIngPar.isSelected()){
                    if(!strEstRelIng.equals("")) strEstRelIng+=",";
                    strEstRelIng+="'E'";
                    
                    String strEstEgr = "";
                    if(chkRelIngParEgrPar.isSelected()){
                        strEstEgr = "'E'";
                    }

                    if(chkRelIngParEgrTot.isSelected()){
                        if(!strEstEgr.equals("")) strEstEgr += ",";
                        strEstEgr += "'C'";
                    }

                    if(sqlEstConRel.equals("")){
                        sqlEstConRel = "WHERE (st_confIng = 'E'";
                    } else {
                        sqlEstConRel += "OR (st_confIng = 'E'";
                    }
                    
                    if(!strEstEgr.equals("")){
                        sqlEstConRel += " AND st_confEgr IN ("+strEstEgr+")";
                    }
                    
                    sqlEstConRel += ") ";
                }

                if(chkRelIngTot.isSelected()){
                    if(!strEstRelIng.equals("")) strEstRelIng+=",";
                    strEstRelIng+="'C'";
                    
                    if(sqlEstConRel.equals("")){
                        sqlEstConRel = "WHERE (st_confIng = 'C')";
                    } else {
                        sqlEstConRel += "OR (st_confIng = 'C')";
                    }
                    
                }

                //Filtro estados no relacionadas
                String strEstNotRelIng = "";
                if(chkNotRelIngPen.isSelected()){
                    strEstNotRelIng = "'P'";
                }

                if(chkNotRelIngPar.isSelected()){
                    if(!strEstNotRelIng.equals("")) strEstNotRelIng+=",";
                    strEstNotRelIng+="'E'";
                }

                if(chkNotRelIngTot.isSelected()){
                    if(!strEstNotRelIng.equals("")) strEstNotRelIng+=",";
                    strEstNotRelIng+="'C'";
                }

                if(!strEstNotRelIng.equals("")){
                   sqlEstConNotRel = "WHERE st_confIng IN ("+strEstNotRelIng+") ";
                }
                //</editor-fold>

                //Armar sentencia SQL
                if ( (!strBodIng.equals("")) || (!strBodEgr.equals("")) ){
                    if ( (!sqlEstConRel.equals("")) && (!strBodIng.equals("")) && (!strBodEgr.equals("")) ){
                        strSql =" "
                               +" SELECT * FROM (  "
                               +"        SELECT CASE WHEN st_meringegrfisbod = 'S'  "
                               +"               THEN CASE WHEN (canconing+cannunrecing+canmalesting) <= 0 THEN 'P'  "
                               +"                    ELSE CASE WHEN (canconing+cannunrecing+canmalesting) < caning THEN 'E' ELSE 'C' END END "
                               +"               ELSE CASE WHEN st_meringegrfisbod IS NULL THEN '' ELSE 'F' END END AS st_confIng "
                               +"             , CASE WHEN st_meregrfisbod = 'S' "
                               +"               THEN CASE WHEN (canconegr+cannunrecegr) <= 0 THEN 'P'  "
                               +"                    ELSE CASE WHEN (canconegr+cannunrecegr) < canegr THEN 'E' ELSE 'C' END END "
                               +"               ELSE 'F' END AS st_confEgr "
                               +"             , (caning * nd_pesitmkgr) AS pesing, canconing+cannunrecing+canmalesting AS cantotconing, canconegr+cannunrecegr AS cantotconegr, * "
                               +"        FROM ( "
                               +"              SELECT inv.co_emp, inv.co_itm, inv.tx_codalt, inv.tx_codAlt2, inv.tx_nomitm, a7.tx_unimed, a7.co_emp AS co_emping, a7.co_loc AS co_locing, a7.co_tipdoc AS co_tipdocing "
                               +"                   , a9.tx_descor AS tx_descoring, a7.co_doc AS co_docing, a7.co_reg AS co_reging, a.ne_numdoc, a.fe_doc AS fe_docing, a.co_cli, a.tx_nomcli "
                               +"                   , boding.co_bod AS co_boding, boding.tx_nom AS tx_boding, nd_pesitmkgr, a7.st_meringegrfisbod, a5.st_meregrfisbod, men.tx_acc "
                               +"                   , (CASE WHEN a7.nd_can IS NULL THEN 0 ELSE a7.nd_can END ) AS caning "
                               +"                   , (CASE WHEN a7.nd_cancon IS NULL THEN 0 ELSE a7.nd_cancon END ) AS canconing "
                               +"                   , (CASE WHEN a7.nd_cannunrec IS NULL THEN 0 ELSE a7.nd_cannunrec END ) AS cannunrecing "
                               +"                   , (CASE WHEN a7.nd_cantotmalest IS NULL THEN 0 ELSE a7.nd_cantotmalest END ) AS canmalesting "
                               +"                   , a5.co_emp AS co_empegr, a5.co_loc AS co_locegr, a5.co_tipdoc AS co_tipdocegr, a4.tx_descor AS tx_descoregr, a5.co_doc AS co_docegr "
                               +"                   , a5.co_reg AS co_regegr, a3.ne_numorddes, a3.fe_doc AS fe_docegr, bodegr.co_bod AS co_bodegr, bodegr.tx_nom AS tx_bodegr "
                               +"                   , (CASE WHEN a5.nd_can IS NULL THEN 0 ELSE a5.nd_can END ) AS canegr "
                               //+"                   , (CASE WHEN a5.nd_cancon IS NULL THEN 0 ELSE a5.nd_cancon END ) AS canconegr, "   //Antes
                               +"                   , ( CASE WHEN a5.nd_CanCon = 0 THEN abs(a7.nd_CanCon) ELSE abs(a5.nd_CanCon) END) AS canconegr "  //Ahora
                               //+"                   , (CASE WHEN a5.nd_cannunrec IS NULL THEN 0 ELSE a5.nd_cannunrec END ) AS cannunrecegr "  //Antes
                               +"                   , ( CASE WHEN a7.nd_canNunRec = 0 THEN abs(a5.nd_CanNunRec) ELSE abs(a7.nd_canNunRec) END  ) AS cannunrecegr "   //Ahora
                               +"              FROM tbm_cabguirem as a3 "
                               +"              INNER JOIN tbm_cabtipdoc AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipdoc=a3.co_tipdoc ) "
                               +"              INNER JOIN tbm_detguirem AS a5 ON (a5.co_emp=a3.co_emp AND a5.co_loc=a3.co_loc AND a5.co_tipdoc=a3.co_tipdoc AND a5.co_doc=a3.co_doc ) "
                               +"              INNER JOIN tbr_detmovinv AS a6 ON (a6.co_emp=a5.co_emprel AND a6.co_loc=a5.co_locrel AND a6.co_tipdoc=a5.co_tipdocrel AND a6.co_doc=a5.co_docrel AND a6.co_reg=a5.co_regrel) "
                               +"              INNER JOIN tbm_detmovinv AS a7 ON (a7.co_emp=a6.co_emprel AND a7.co_loc=a6.co_locrel AND a7.co_tipdoc=a6.co_tipdocrel AND a7.co_doc=a6.co_docrel AND a7.co_reg=a6.co_regrel) "
                               +"              INNER JOIN tbm_cabmovinv AS a ON (a.co_emp=a7.co_emp AND a.co_loc=a7.co_loc AND a.co_tipdoc=a7.co_tipdoc AND a.co_doc=a7.co_doc ) "
                               +"              INNER JOIN tbm_cabtipdoc AS a9 ON (a9.co_emp=a.co_emp AND a9.co_loc=a.co_loc AND a9.co_tipdoc=a.co_tipdoc ) "
                               +"              INNER JOIN tbm_inv AS inv ON (inv.co_emp=a5.co_emp AND inv.co_itm=a5.co_itm) "
                               +"              INNER JOIN tbr_bodEmpBodGrp AS bodgrping ON (bodgrping.co_emp=a3.co_emp AND bodgrping.co_bod=a3.co_ptodes) "
                               +"              INNER JOIN tbr_bodEmpBodGrp AS bodgrpegr ON (bodgrpegr.co_emp=a3.co_emp AND bodgrpegr.co_bod=a3.co_ptopar) "
                               +"              INNER JOIN tbm_bod AS boding ON (boding.co_emp=a3.co_emp AND boding.co_bod=a3.co_ptodes) "
                               +"              INNER JOIN tbm_bod AS bodegr ON (bodegr.co_emp=a3.co_emp AND bodegr.co_bod=a3.co_ptopar) "
                               +"              LEFT JOIN tbm_mnusis as men ON (men.co_mnu=a.co_mnu ) "
                               +"              WHERE bodgrping.co_empGrp = "+objParSis.getCodigoEmpresaGrupo()+" AND bodgrping.co_bodGrp IN ( "+strBodIng+" ) AND a.st_reg = 'A' AND a.st_coninv IN ("+strEstRelIng+") "
                               +"              AND bodgrpegr.co_empGrp = "+objParSis.getCodigoEmpresaGrupo()+" AND bodgrpegr.co_bodGrp IN ( "+strBodEgr+" ) AND a3.st_reg = 'A' "
                               +"              "+sqlFec+" "
                               +"              "+sqlItmIng+" "+sqlItmEgr+" "
                               +"        ) AS x "
                               +" ) AS x "
                               +" "+sqlEstConRel+" ";
                        
                        if (!sqlEstConNotRel.equals("")){
                            strSql += " UNION ALL ";
                        }                        
                    }
       
                    if ( (!sqlEstConNotRel.equals("")) && (!strBodIng.equals("")) ){
                        strSql+=" SELECT * FROM ( "
                               +"        SELECT CASE WHEN st_merIngEgrFisBod = 'S'  "
                               +"               THEN CASE WHEN (canConIng+canNunRecIng+canMalEstIng) <= 0 THEN 'P' "
                               +"                    ELSE CASE WHEN (canConIng+canNunRecIng+canMalEstIng) < caning THEN 'E' ELSE 'C' END END "
                               +"               ELSE CASE WHEN st_merIngEgrFisBod IS NULL THEN '' ELSE 'F' END END AS st_confIng "
                               +"             , 'F' AS st_confEgr "
                               +"             , (caning * nd_pesitmkgr) AS pesing, canConIng+canNunRecIng+canMalEstIng AS cantotconing, canconegr+cannunrecegr AS cantotconegr, * "
                               +"        FROM ( "
                               +"              SELECT inv.co_emp, inv.co_itm, inv.tx_codalt, inv.tx_codAlt2, inv.tx_nomitm, a7.tx_unimed, a7.co_emp AS co_emping, a7.co_loc AS co_locing, a7.co_tipdoc AS co_tipdocing"
                               +"                   , a2.tx_descor AS tx_descoring, a7.co_doc AS co_docing, a7.co_reg AS co_reging, a.ne_numdoc, a.fe_doc AS fe_docing, a.co_cli, a.tx_nomcli "
                               +"                   , boding.co_bod AS co_boding, boding.tx_nom AS tx_boding, nd_pesitmkgr, a7.st_merIngEgrFisBod, a7.st_merIngEgrFisBod AS st_meregrfisbod, men.tx_acc "
                               +"                   , (CASE WHEN a7.nd_can IS NULL THEN 0 ELSE a7.nd_can END ) AS caning "
                               +"                   , (CASE WHEN a7.nd_cancon IS NULL THEN 0 ELSE a7.nd_cancon END ) AS canConIng "
                               +"                   , (CASE WHEN a7.nd_cannunrec IS NULL THEN 0 ELSE a7.nd_cannunrec END ) AS canNunRecIng "
                               +"                   , (CASE WHEN a7.nd_cantotmalest IS NULL THEN 0 ELSE a7.nd_cantotmalest END ) AS canMalEstIng "
                               +"                   , null AS co_empegr, 0 AS co_locegr, 0 AS co_tipdocegr, '' AS tx_descoregr, 0 AS co_docegr, 0 AS co_regegr, 0 AS ne_numorddes, a.fe_doc AS fe_docegr "
                               +"                   , 0 AS co_bodegr, '' AS tx_bodegr, 0 AS canegr, 0 AS canconegr, 0 AS cannunrecegr "
                               +"              FROM tbm_cabmovinv AS a "
                               +"              INNER JOIN tbm_detmovinv AS a7 ON (a7.co_emp=a.co_emp and a7.co_loc=a.co_loc and a7.co_tipdoc=a.co_tipdoc and a7.co_doc=a.co_doc) "
                               +"              INNER JOIN tbm_cabtipdoc AS a2 ON (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) "
                               +"              INNER JOIN tbm_inv AS inv ON (inv.co_emp=a7.co_emp and inv.co_itm=a7.co_itm) "
                               +"              INNER JOIN tbr_bodEmpBodGrp AS bodgrping ON (bodgrping.co_emp=a7.co_emp AND bodgrping.co_bod=a7.co_bod) "
                               +"              INNER JOIN tbm_bod AS boding ON (boding.co_emp=a7.co_emp and boding.co_bod=a7.co_bod ) "
                               +"              LEFT JOIN tbm_mnusis as men ON (men.co_mnu=a.co_mnu ) "
                               +"              WHERE bodgrping.co_empGrp = "+objParSis.getCodigoEmpresaGrupo()+" AND bodgrping.co_bodGrp IN ( "+strBodIng+" ) AND a.st_reg = 'A' AND a.co_tipdoc = 2 AND co_com IS NOT NULL AND a.ne_numdoc > 0 AND a.st_coninv IN ("+strEstNotRelIng+") "
                               +"              "+sqlFec+" "
                               +"              "+sqlItmIng+" "
                               +"        ) AS x "
                               +" ) AS x "
                               +" "+sqlEstConNotRel+" ";
                    }
                    
                    if (!strSql.equals("")){
                        strSQL = "SELECT * FROM ( "+strSql+" ) AS X  ORDER BY tx_codalt ";
                        System.out.println("cargarDetReg: "+ strSQL );
                        rst = stm.executeQuery(strSQL);
                        vecDat.clear();
                        lblMsgSis.setText("Cargando datos...");
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_CODEMPITM, rst.getString("co_emp"));
                            vecReg.add(INT_TBL_DAT_CODITM,    rst.getString("co_itm"));
                            vecReg.add(INT_TBL_DAT_CODALTITM,    rst.getString("tx_codalt"));
                            vecReg.add(INT_TBL_DAT_CODALTDOS,    rst.getString("tx_codalt2"));
                            vecReg.add(INT_TBL_DAT_NOMITM,    rst.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DAT_UNIITM,    rst.getString("tx_unimed"));
                            vecReg.add(INT_TBL_DAT_CODEMPING, rst.getString("co_emping"));
                            vecReg.add(INT_TBL_DAT_CODLOCING, rst.getString("co_locing"));
                            vecReg.add(INT_TBL_DAT_CODTIPDOCING, rst.getString("co_tipdocing"));
                            vecReg.add(INT_TBL_DAT_DESTIPDOCING, rst.getString("tx_descoring"));
                            vecReg.add(INT_TBL_DAT_CODDOCING, rst.getString("co_docing"));
                            vecReg.add(INT_TBL_DAT_CODREGING, rst.getString("co_reging"));
                            vecReg.add(INT_TBL_DAT_NUMDOCING, rst.getString("ne_numdoc"));
                            vecReg.add(INT_TBL_DAT_FECDOCING, rst.getString("fe_docing"));
                            vecReg.add(INT_TBL_DAT_CODBODING, rst.getString("co_boding"));
                            vecReg.add(INT_TBL_DAT_NOMBODING, rst.getString("tx_boding"));
                            vecReg.add(INT_TBL_DAT_CANTOTING, rst.getString("caning"));
                            vecReg.add(INT_TBL_DAT_PESTOTING, rst.getString("pesing"));
                            vecReg.add(INT_TBL_DAT_CODCLIING, rst.getString("co_cli"));
                            vecReg.add(INT_TBL_DAT_NOMCLIING, (rst.getString("tx_nomcli")==null?"":(rst.getString("tx_nomcli").equals("null")?"":rst.getString("tx_nomcli"))) );                            
                            vecReg.add(INT_TBL_DAT_BUTDOCING,  "...");

                            if(rst.getString("st_confIng").equals("P")){
                                vecReg.add(INT_TBL_DAT_CHKPENING, new Boolean(true) );
                                vecReg.add(INT_TBL_DAT_CHKPARING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTING, new Boolean(false) );
                            }
                            else if(rst.getString("st_confIng").equals("C")){
                                vecReg.add(INT_TBL_DAT_CHKPENING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPARING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTING, new Boolean(true) );
                            }
                            else if(rst.getString("st_confIng").equals("E")){
                                vecReg.add(INT_TBL_DAT_CHKPENING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPARING, new Boolean(true) );
                                vecReg.add(INT_TBL_DAT_CHKTOTING, new Boolean(false) );
                            }
                            else if(rst.getString("st_confIng").equals("F")){
                                vecReg.add(INT_TBL_DAT_CHKPENING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPARING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTING, new Boolean(false) );
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_CHKPENING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPARING, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTING, new Boolean(false) );
                            }

                            vecReg.add(INT_TBL_DAT_CANCONING, rst.getString("cantotconing"));
                            vecReg.add(INT_TBL_DAT_BUTCONING, "...");
                            vecReg.add(INT_TBL_DAT_CODEMPEGR, rst.getString("co_empegr"));
                            vecReg.add(INT_TBL_DAT_CODLOCEGR, rst.getString("co_locegr"));
                            vecReg.add(INT_TBL_DAT_CODTIPDOCEGR, rst.getString("co_tipdocegr"));
                            vecReg.add(INT_TBL_DAT_DESTIPDOCCEGR, rst.getString("tx_descoregr"));
                            vecReg.add(INT_TBL_DAT_CODDOCEGR, rst.getString("co_docegr"));
                            vecReg.add(INT_TBL_DAT_CODREGEGR, rst.getString("co_regegr"));

                            if (rst.getString("co_empegr")!=null){
                                vecReg.add(INT_TBL_DAT_NUMDOCEGR, rst.getString("ne_numorddes"));
                                vecReg.add(INT_TBL_DAT_FECDOCEGR, rst.getString("fe_docegr"));
                                vecReg.add(INT_TBL_DAT_CODBODEGR, rst.getString("co_bodegr"));
                            } else{
                                vecReg.add(INT_TBL_DAT_NUMDOCEGR, "");
                                vecReg.add(INT_TBL_DAT_FECDOCEGR, "");
                                vecReg.add(INT_TBL_DAT_CODBODEGR, "");                                                                
                            }

                            vecReg.add(INT_TBL_DAT_NOMBODEGR, rst.getString("tx_bodegr"));
                            vecReg.add(INT_TBL_DAT_BUTDOCEGR, "..." );
                            
                            if(rst.getString("st_confEgr").equals("P")){
                                vecReg.add(INT_TBL_DAT_CHKPENEGR, new Boolean(true) );
                                vecReg.add(INT_TBL_DAT_CHKPAREGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTEGR, new Boolean(false) );
                            }
                            else if(rst.getString("st_confEgr").equals("C")){
                                vecReg.add(INT_TBL_DAT_CHKPENEGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPAREGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTEGR, new Boolean(true) );
                            }
                            else if(rst.getString("st_confEgr").equals("E")){
                                vecReg.add(INT_TBL_DAT_CHKPENEGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPAREGR, new Boolean(true) );
                                vecReg.add(INT_TBL_DAT_CHKTOTEGR, new Boolean(false) );
                            }
                            else if(rst.getString("st_confEgr").equals("F")){
                                vecReg.add(INT_TBL_DAT_CHKPENEGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPAREGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTEGR, new Boolean(false) );
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_CHKPENEGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKPAREGR, new Boolean(false) );
                                vecReg.add(INT_TBL_DAT_CHKTOTEGR, new Boolean(false) );
                            }

                            vecReg.add(INT_TBL_DAT_CANCONEGR, rst.getString("cantotconegr"));
                            vecReg.add(INT_TBL_DAT_BUTCONEGR, "...");
                            vecReg.add(INT_TBL_DAT_BUTGUIREM, "..." );
                            vecReg.add(INT_TBL_DAT_ACCMNU, rst.getString("tx_acc"));
                            vecDat.add(vecReg);
                        }
                        rst.close();
                        rst=null;

                        //Asignar vectores al modelo.
                        objTblModTblDat.setData(vecDat);
                        tblDat.setModel(objTblModTblDat);
                        vecDat.clear();  
                    }
                }
                stm.close();
                stm=null;
                con.close();
                con=null;

                lblMsgSis.setText("Se encontraron "+tblDat.getRowCount()+" registros.");
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    


}

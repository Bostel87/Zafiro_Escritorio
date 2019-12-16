/*
 * ZafVeh05.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */
package Vehiculos.ZafVeh05;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
/**
 *
 * @author  José Marín 
 */
public class ZafVeh05 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Tabla 
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_VEH=1;
    private static final int INT_TBL_DAT_PLA_VEH=2;
    private static final int INT_TBL_DAT_NOM_VEH=3;
    private static final int INT_TBL_DAT_TIP_VEH=4;
    private static final int INT_TBL_DAT_COD_MAR=5;
    private static final int INT_TBL_DAT_BUT_MAR=6;
    private static final int INT_TBL_DAT_NOM_MAR=7;
    private static final int INT_TBL_DAT_COD_COM=8;
    private static final int INT_TBL_DAT_BUT_COM=9;
    private static final int INT_TBL_DAT_NOM_COM=10;
    private static final int INT_TBL_DAT_PES_SOP=11;
    private static final int INT_TBL_DAT_CAP_TAN=12;
    private static final int INT_TBL_DAT_REN_VEH=13;
    private static final int INT_TBL_DAT_COD_CHO=14;
    private static final int INT_TBL_DAT_BUT_CHO=15;
    private static final int INT_TBL_DAT_NOM_CHO=16;
    private static final int INT_TBL_DAT_BUT=17;
    private static final int INT_TBL_DAT_KIL_VEH=18;
    
    //Variables  
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;                                     //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private ZafTblCelRenBut objTblCelRenBut;                     //Render: Presentar JButton en JTable.
    private ZafTblModLis objTblModLis;                           //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiButVco objTblCelEdiButVcoMar;            //Editor: JButton en celda 
    private ZafTblCelEdiButVco objTblCelEdiButVcoCho;            //Editor: JButton en celda 
    private ZafTblCelEdiButVco objTblCelEdiButVcoCom;
                           
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoMar;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCho;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCom;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafVenCon vcoMar;
    private ZafVenCon vcoCom;
    private ZafVenCon vcoCho;
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private ZafThreadGUI objThrGUI;

    private boolean blnHayCam;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo. 
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecTipVeh;
    
    private String strSQL, strAux;
    private String strCodMar, strNomMar;                       //Contenido del campo al obtener el foco.
    private String strCodCom, strNomCom;                       //Contenido del campo al obtener el foco.
    
    /** Creates new form ZafVeh05 */
    public ZafVeh05(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
           
            initComponents();
            if (!configurarFrm()) 
                exitForm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
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
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);    
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.3 ");
            lblTit.setText(strAux);
            
             //ConfigurarCombos
            
        

            
            
            //Configurar los JTables.
            configurarTblDat();
            cargarComboJtableTipoVeh();
            configurarComboFiltroTipVeh();
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3718)) //Si no tiene el acceso se oculta el boton guardar
            {
                butGua.setVisible(false);
            }
            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
   /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_VEH,"Cód.Veh.");
            vecCab.add(INT_TBL_DAT_PLA_VEH,"Pla.Veh.");
            vecCab.add(INT_TBL_DAT_NOM_VEH,"Vehículo");
            vecCab.add(INT_TBL_DAT_TIP_VEH,"Tip.Veh.");
            vecCab.add(INT_TBL_DAT_COD_MAR,"Cód.Mar.");
            vecCab.add(INT_TBL_DAT_BUT_MAR,"");
            vecCab.add(INT_TBL_DAT_NOM_MAR,"Marca");
            vecCab.add(INT_TBL_DAT_COD_COM,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_BUT_COM,"");
            vecCab.add(INT_TBL_DAT_NOM_COM,"Combustible");
            vecCab.add(INT_TBL_DAT_PES_SOP,"Pes.Sop.");
            vecCab.add(INT_TBL_DAT_CAP_TAN,"Cap.Tan.");
            vecCab.add(INT_TBL_DAT_REN_VEH,"Ren.Veh.");
            vecCab.add(INT_TBL_DAT_COD_CHO,"Cód.Cho.");
            vecCab.add(INT_TBL_DAT_BUT_CHO,"");
            vecCab.add(INT_TBL_DAT_NOM_CHO,"Chofer");
            vecCab.add(INT_TBL_DAT_BUT,"");
            vecCab.add(INT_TBL_DAT_KIL_VEH,"Últ.Kil.");
              
            //
            configurarMarcas();
            configurarCombustible();
            configurarChoferes();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDat.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_VEH).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_PLA_VEH).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEH).setPreferredWidth(350);
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_MAR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_MAR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_PES_SOP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAP_TAN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_REN_VEH).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CHO).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_KIL_VEH).setPreferredWidth(100);
            
            //Columnas Ocultas
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAR, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_COM, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CHO, tblDat);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_MAR).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHO).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setResizable(false);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_SOP).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAP_TAN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_REN_VEH).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_KIL_VEH).setCellRenderer(objTblCelRenLblNum);
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setCellRenderer(objTblCelRenLblCod);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_SOP, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAP_TAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_REN_VEH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_KIL_VEH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            
            //Agrupamiento de Columnas.
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);

            ZafTblHeaColGrp objTblHeaColGrpMar = new ZafTblHeaColGrp("Marcas");
            objTblHeaColGrpMar.setHeight(16);
            objTblHeaColGrpMar.add(tcmAux.getColumn(INT_TBL_DAT_COD_MAR));
            objTblHeaColGrpMar.add(tcmAux.getColumn(INT_TBL_DAT_NOM_MAR));
            objTblHeaColGrpMar.add(tcmAux.getColumn(INT_TBL_DAT_BUT_MAR));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMar);
            objTblHeaColGrpMar = null;

            ZafTblHeaColGrp objTblHeaColGrpCom = new ZafTblHeaColGrp("Combustibles");
            objTblHeaColGrpCom.setHeight(16);
            objTblHeaColGrpCom.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM));
            objTblHeaColGrpCom.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM));
            objTblHeaColGrpCom.add(tcmAux.getColumn(INT_TBL_DAT_BUT_COM));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCom);
            objTblHeaColGrpCom = null;
            
            ZafTblHeaColGrp objTblHeaColGrpCho = new ZafTblHeaColGrp("Choferes");
            objTblHeaColGrpCho.setHeight(16);
            objTblHeaColGrpCho.add(tcmAux.getColumn(INT_TBL_DAT_COD_CHO));
            objTblHeaColGrpCho.add(tcmAux.getColumn(INT_TBL_DAT_NOM_CHO));
            objTblHeaColGrpCho.add(tcmAux.getColumn(INT_TBL_DAT_BUT_CHO));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCho);
            objTblHeaColGrpCho = null;
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TIP_VEH);
            vecAux.add("" + INT_TBL_DAT_COD_MAR);
            vecAux.add("" + INT_TBL_DAT_BUT_MAR);
            
            if(objPerUsr.isOpcionEnabled(3720)) // Si no tiene el acceso no es editable
            {    
                vecAux.add("" + INT_TBL_DAT_COD_COM);
                vecAux.add("" + INT_TBL_DAT_BUT_COM);
            }
            vecAux.add("" + INT_TBL_DAT_PES_SOP);
            
            if(objPerUsr.isOpcionEnabled(3721)) // Si no tiene el acceso no es editable
                vecAux.add("" + INT_TBL_DAT_CAP_TAN);
            
            if(objPerUsr.isOpcionEnabled(3722)) // Si no tiene el acceso no es editable
                vecAux.add("" + INT_TBL_DAT_REN_VEH);
            
            vecAux.add("" + INT_TBL_DAT_COD_CHO);
            vecAux.add("" + INT_TBL_DAT_BUT_CHO);
            
            if(objPerUsr.isOpcionEnabled(3729)) // Si no tiene el acceso no es editable
                vecAux.add("" + INT_TBL_DAT_KIL_VEH);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            objTblCelRenCmbBox=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setCellRenderer(objTblCelRenCmbBox);
            
            objTblCelEdiCmbBox=new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setCellEditor(objTblCelEdiCmbBox);
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_MAR).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHO).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
                    
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_MAR;
            intColTbl[1]=INT_TBL_DAT_NOM_MAR;
            
            objTblCelEdiTxtVcoMar=new ZafTblCelEdiTxtVco(tblDat, vcoMar, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setCellEditor(objTblCelEdiTxtVcoMar);
            objTblCelEdiTxtVcoMar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMar.setCampoBusqueda(0);
                    vcoMar.setCriterio1(11);
                }
            });
            
            objTblCelEdiButVcoMar=new ZafTblCelEdiButVco(tblDat, vcoMar, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_MAR).setCellEditor(objTblCelEdiButVcoMar);
            objTblCelEdiButVcoMar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            intColVen=null;
            intColTbl=null;
            
            //new
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_COM;
            intColTbl[1]=INT_TBL_DAT_NOM_COM;
            
            objTblCelEdiTxtVcoCom=new ZafTblCelEdiTxtVco(tblDat, vcoCom, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM).setCellEditor(objTblCelEdiTxtVcoCom);
            objTblCelEdiTxtVcoCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    vcoCom.setCampoBusqueda(0);
                    vcoCom.setCriterio1(11);
                }               
            });
            
            objTblCelEdiButVcoCom=new ZafTblCelEdiButVco(tblDat, vcoCom, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM).setCellEditor(objTblCelEdiButVcoCom);
            objTblCelEdiButVcoCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_CHO;
            intColTbl[1]=INT_TBL_DAT_NOM_CHO;
            
            objTblCelEdiTxtVcoCho=new ZafTblCelEdiTxtVco(tblDat, vcoCho, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setCellEditor(objTblCelEdiTxtVcoCho);
            objTblCelEdiTxtVcoCho.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    vcoCho.setCampoBusqueda(0);
                    vcoCho.setCriterio1(11);
                }               
            });
            
            objTblCelEdiButVcoCho=new ZafTblCelEdiButVco(tblDat, vcoCho, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHO).setCellEditor(objTblCelEdiButVcoCho);
            objTblCelEdiButVcoCho.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            
            intColVen=null;
            intColTbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PES_SOP).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES_SOP))<0){
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_PES_SOP);
                        }
                }
            });
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAP_TAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAP_TAN))<0){
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAP_TAN);
                        }
                }
            });
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_REN_VEH).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_REN_VEH))<0){
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_REN_VEH);
                        }
                }
            });
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_KIL_VEH).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    Double precio;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    precio=objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_KIL_VEH));
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_KIL_VEH))<1){
                            objTblMod.setValueAt(precio, tblDat.getSelectedRow(), INT_TBL_DAT_KIL_VEH);
                        }
                }
            });
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);   

            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            System.out.println("Se configuara la tabla!!!!... ");
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
                case INT_TBL_DAT_COD_VEH: strMsg="Código del Vehículo"; break;
                case INT_TBL_DAT_PLA_VEH: strMsg="Placa del Vehículo"; break;
                case INT_TBL_DAT_NOM_VEH: strMsg="Descripción larga del Vehículo"; break;
                case INT_TBL_DAT_TIP_VEH: strMsg="Tipo del Vehículo"; break;
                case INT_TBL_DAT_COD_MAR: strMsg="Código de la Marca"; break;
                case INT_TBL_DAT_BUT_MAR: strMsg="Muestra la ventana de consulta para elegir la 'Marca'"; break;
                case INT_TBL_DAT_NOM_MAR: strMsg="Descripción larga de la Marca"; break;
                case INT_TBL_DAT_COD_COM: strMsg="Código de combustible"; break;
                case INT_TBL_DAT_BUT_COM: strMsg="Muestra la ventana de consulta para elegir el 'Combustible'"; break;
                case INT_TBL_DAT_NOM_COM: strMsg="Descripción larga de combustible"; break;
                case INT_TBL_DAT_PES_SOP: strMsg="Peso soportado (Kilogramos)"; break;
                case INT_TBL_DAT_CAP_TAN: strMsg="Capacidad del tanque"; break;
                case INT_TBL_DAT_REN_VEH: strMsg="Rendimiento del vehículo"; break;
                case INT_TBL_DAT_COD_CHO: strMsg="Código del chofer"; break;
                case INT_TBL_DAT_BUT_CHO: strMsg="Muestra la ventana de consulta para elegir el 'Chofer'"; break;
                case INT_TBL_DAT_NOM_CHO: strMsg="Nombres y apellidos del chofer"; break;
                case INT_TBL_DAT_KIL_VEH: strMsg="Ültimo kilometraje del vehículo"; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    //Combo de formulario  del filtro   
    private boolean configurarComboFiltroTipVeh() {
        boolean blnRes = true;
        try {
            //cboTipVeh.removeAllItems();
            vecTipVeh = new Vector();
            vecTipVeh.add("");
            vecTipVeh.add("C");
            vecTipVeh.add("M");
            vecTipVeh.add("N");
            vecTipVeh.add("O");
            cboTipVeh.addItem("(Todos)");
            cboTipVeh.addItem("C: Carro");
            cboTipVeh.addItem("M: Moto");
            cboTipVeh.addItem("N: Montacarga");
            cboTipVeh.addItem("O: Otros");
            cboTipVeh.setSelectedIndex(0);
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.   EN LA TABLA
     */
    private boolean cargarComboJtableTipoVeh(){
        objTblCelEdiCmbBox.removeAllItems();
        objTblCelEdiCmbBox.addItem(" ");
        objTblCelEdiCmbBox.addItem("Carro");
        objTblCelEdiCmbBox.addItem("Moto");
        objTblCelEdiCmbBox.addItem("Montacarga");
        objTblCelEdiCmbBox.addItem("Otros");
        return true;
    }
    
    /**
     * Configurar Marcas
     * @return 
     */
    private boolean configurarMarcas()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mar");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_mar, a1.tx_deslar";
            strSQL+=" FROM tbm_marVeh a1 WHERE a1.st_reg NOT IN('I','E')";
            strSQL+=" ORDER BY a1.tx_deslar";
            System.out.println("configurarMarcas: " + strSQL);
            vcoMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Marcas", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMar.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarMarcas(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
               case 0: //Mostrar la ventana de consulta.
                    vcoMar.setCampoBusqueda(2);
                    vcoMar.setVisible(true);
                    if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoMar.buscar("a1.co_mar", txtCodMar.getText()))
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    else
                    {
                        vcoMar.setCampoBusqueda(0);
                        vcoMar.setCriterio1(11);
                        vcoMar.cargarDatos();
                        vcoMar.setVisible(true);
                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMar.setText(vcoMar.getValueAt(1));
                            txtNomMar.setText(vcoMar.getValueAt(2));
                        }
                        else
                        {
                            txtCodMar.setText(strCodMar);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoMar.buscar("a1.tx_deslar", txtNomMar.getText()))
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    else
                    {
                        vcoMar.setCampoBusqueda(2);
                        vcoMar.setCriterio1(11);
                        vcoMar.cargarDatos();
                        vcoMar.setVisible(true);
                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMar.setText(vcoMar.getValueAt(1));
                            txtNomMar.setText(vcoMar.getValueAt(3));
                        }
                        else
                        {
                            txtNomMar.setText(strNomMar);
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
  
    private boolean configurarChoferes(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("nombre");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" select distinct a2.co_tra, (a2.tx_ape || ' ' ||a2.tx_nom) as nombre ";
            strSQL+=" from tbm_traEmp as a1 INNER JOIN tbm_tra as a2 ON (a1.co_tra = a2.co_tra)";
            strSQL+=" WHERE a1.co_dep in (26,27,28,29,30)";
            strSQL+=" ORDER BY nombre";
            System.out.println("configurarChoferes:.." + strSQL);
            vcoCho=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Choferes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCho.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

 
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarChoferes(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            System.out.println("mostrarChoferes:.....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCho.setCampoBusqueda(1);
                    vcoCho.show();
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
     * Configurar Consulta de Combustible.
     * @return 
     */
    private boolean configurarCombustible()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_com");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_com, a1.tx_deslar";
            strSQL+=" FROM tbm_comVeh a1 WHERE a1.st_reg ='A'";
            strSQL+=" ORDER BY a1.tx_deslar";
            System.out.println("configurarCombustible:.." + strSQL);
            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Combustibles", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCom.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarCombustibles(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
               case 0: //Mostrar la ventana de consulta.
                    vcoCom.setCampoBusqueda(2);
                    vcoCom.setVisible(true);
                    if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtNomCom.setText(vcoCom.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCom.buscar("a1.co_com", txtCodCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtNomCom.setText(vcoCom.getValueAt(2));
                    }
                    else
                    {
                        vcoCom.setCampoBusqueda(0);
                        vcoCom.setCriterio1(11);
                        vcoCom.cargarDatos();
                        vcoCom.setVisible(true);
                        if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCom.setText(vcoCom.getValueAt(1));
                            txtNomCom.setText(vcoCom.getValueAt(2));
                        }
                        else
                        {
                            txtCodCom.setText(strCodCom);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoCom.buscar("a1.tx_deslar", txtNomCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtNomCom.setText(vcoCom.getValueAt(2));
                    }
                    else
                    {
                        vcoCom.setCampoBusqueda(2);
                        vcoCom.setCriterio1(11);
                        vcoCom.cargarDatos();
                        vcoCom.setVisible(true);
                        if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCom.setText(vcoCom.getValueAt(1));
                            txtNomCom.setText(vcoCom.getValueAt(3));
                        }
                        else
                        {
                            txtNomCom.setText(strNomCom);
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
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblMarca = new javax.swing.JLabel();
        txtCodMar = new javax.swing.JTextField();
        txtNomMar = new javax.swing.JTextField();
        butMar = new javax.swing.JButton();
        lblTipCom = new javax.swing.JLabel();
        txtCodCom = new javax.swing.JTextField();
        txtNomCom = new javax.swing.JTextField();
        butCom = new javax.swing.JButton();
        lblTipVeh = new javax.swing.JLabel();
        cboTipVeh = new javax.swing.JComboBox();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Actualizacion Masiva de vehiculos");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(610, 130));
        panCab.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los vehículos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                optTodFocusGained(evt);
            }
        });
        panCab.add(optTod);
        optTod.setBounds(0, 10, 430, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los vehículos que cumplan el criterio seleccionado");
        panCab.add(optFil);
        optFil.setBounds(0, 30, 430, 20);

        lblMarca.setText("Marca:");
        panCab.add(lblMarca);
        lblMarca.setBounds(30, 60, 140, 20);

        txtCodMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMarActionPerformed(evt);
            }
        });
        txtCodMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMarFocusLost(evt);
            }
        });
        panCab.add(txtCodMar);
        txtCodMar.setBounds(170, 60, 90, 20);

        txtNomMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomMarActionPerformed(evt);
            }
        });
        txtNomMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomMarFocusLost(evt);
            }
        });
        panCab.add(txtNomMar);
        txtNomMar.setBounds(260, 60, 240, 20);

        butMar.setText("...");
        butMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMarActionPerformed(evt);
            }
        });
        panCab.add(butMar);
        butMar.setBounds(500, 60, 20, 20);

        lblTipCom.setText("Tipo de combustible:"); // NOI18N
        panCab.add(lblTipCom);
        lblTipCom.setBounds(30, 80, 140, 20);

        txtCodCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodComActionPerformed(evt);
            }
        });
        txtCodCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodComFocusLost(evt);
            }
        });
        panCab.add(txtCodCom);
        txtCodCom.setBounds(170, 80, 90, 20);

        txtNomCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomComActionPerformed(evt);
            }
        });
        txtNomCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomComFocusLost(evt);
            }
        });
        panCab.add(txtNomCom);
        txtNomCom.setBounds(260, 80, 240, 20);

        butCom.setText("...");
        butCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butComActionPerformed(evt);
            }
        });
        panCab.add(butCom);
        butCom.setBounds(500, 80, 20, 20);

        lblTipVeh.setText("Tipo de vehículo:"); // NOI18N
        panCab.add(lblTipVeh);
        lblTipVeh.setBounds(30, 100, 130, 20);

        cboTipVeh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTipVehItemStateChanged(evt);
            }
        });
        cboTipVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipVehActionPerformed(evt);
            }
        });
        panCab.add(cboTipVeh);
        cboTipVeh.setBounds(170, 100, 210, 20);

        panDat.add(panCab, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("Filtro", panDat);

        panRpt.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
    String strTit, strMsg;
    try
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            if(con!=null){
                con.close();
                con=null;
            }
            dispose();
        }
    }
    catch (java.sql.SQLException e)
    {
        dispose();
    }        
}//GEN-LAST:event_exitForm

    private void butMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMarActionPerformed
        strCodMar=txtCodMar.getText();
        mostrarMarcas(0);
        optFil.setSelected(true);
    }//GEN-LAST:event_butMarActionPerformed

    private void txtNomMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomMarActionPerformed
        txtNomMar.transferFocus();
    }//GEN-LAST:event_txtNomMarActionPerformed

    private void txtNomMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMarFocusGained
        strNomMar=txtNomMar.getText();
        txtNomMar.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtNomMarFocusGained

    private void txtNomMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMarFocusLost
        if (!txtNomMar.getText().equalsIgnoreCase(strNomMar)){
            if (txtNomMar.getText().equals("")){
                txtCodMar.setText("");
                txtNomMar.setText("");
            }
            else{
                mostrarMarcas(2);
            }
        }
        else
            txtNomMar.setText(strNomMar);
    }//GEN-LAST:event_txtNomMarFocusLost

    private void txtCodMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMarActionPerformed
        txtCodMar.transferFocus();
    }//GEN-LAST:event_txtCodMarActionPerformed

    private void txtCodMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusGained
        strCodMar=txtCodMar.getText();
        txtCodMar.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodMarFocusGained

    private void txtCodMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusLost
        if (!txtCodMar.getText().equalsIgnoreCase(strCodMar)){
            if (txtCodMar.getText().equals("")){
                txtCodMar.setText("");
                txtNomMar.setText("");
            }
            else
            mostrarMarcas(1);
        }
        else
            txtCodMar.setText(strCodMar);
    }//GEN-LAST:event_txtCodMarFocusLost

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodMar.setText("");
            txtNomMar.setText("");
            txtCodCom.setText("");
            txtNomCom.setText("");
            //vehiculo.setSelectedItem("");
            //combustible.setSelectedItem("");
            configurarComboFiltroTipVeh();
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void cboTipVehItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTipVehItemStateChanged
    }//GEN-LAST:event_cboTipVehItemStateChanged

    private void cboTipVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipVehActionPerformed
        if(cboTipVeh.isFocusOwner())
        optFil.setSelected(true);
    }//GEN-LAST:event_cboTipVehActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafVeh05.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if (actualizarDet())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    System.out.println("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_optTodFocusGained
        if (optTod.isSelected())
        {
            txtCodMar.setText("");
            txtNomMar.setText("");
            txtCodCom.setText("");
            txtNomCom.setText("");
            //vehiculo.setSelectedItem("");
            //combustible.setSelectedItem("");
            configurarComboFiltroTipVeh();
        }
    }//GEN-LAST:event_optTodFocusGained

    private void txtCodComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodComActionPerformed
        txtCodCom.transferFocus();
    }//GEN-LAST:event_txtCodComActionPerformed

    private void txtCodComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusGained
        strCodCom=txtCodCom.getText();
        txtCodCom.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodComFocusGained

    private void txtCodComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusLost
         if (!txtCodCom.getText().equalsIgnoreCase(strCodCom)){
            if (txtCodCom.getText().equals("")){
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else
            mostrarCombustibles(1);
        }
        else
            txtCodCom.setText(strCodCom);
    }//GEN-LAST:event_txtCodComFocusLost

    private void txtNomComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomComActionPerformed
         txtNomCom.transferFocus();
    }//GEN-LAST:event_txtNomComActionPerformed

    private void txtNomComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusGained
        strNomCom=txtNomCom.getText();
        txtNomCom.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtNomComFocusGained

    private void txtNomComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusLost
          if (!txtNomCom.getText().equalsIgnoreCase(strNomCom)){
            if (txtNomCom.getText().equals("")){
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else{
                mostrarCombustibles(2);
            }
        }
        else
            txtNomCom.setText(strNomCom);
    }//GEN-LAST:event_txtNomComFocusLost

    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        strCodCom=txtCodCom.getText();
        mostrarCombustibles(0);
        optFil.setSelected(true);
    }//GEN-LAST:event_butComActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCom;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butMar;
    private javax.swing.JComboBox cboTipVeh;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipCom;
    private javax.swing.JLabel lblTipVeh;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCom;
    private javax.swing.JTextField txtCodMar;
    private javax.swing.JTextField txtNomCom;
    private javax.swing.JTextField txtNomMar;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
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
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
    
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
            if (con != null) 
            {
                stm = con.createStatement();      
                
                //Armar la sentencia SQL.
                strSQL =""; 
                strSQL+=" SELECT a1.co_veh, a1.tx_pla, a1.tx_deslar as NomVeh, a2.co_mar,a2.tx_deslar as NomMar,";
                strSQL+="        CASE WHEN a1.tx_tipVeh='C' THEN CAST('Carro' AS CHARACTER VARYING) ";
                strSQL+="        ELSE CASE WHEN a1.tx_tipVeh='M' THEN CAST('Moto' AS CHARACTER VARYING) ";
                strSQL+="        ELSE CASE WHEN a1.tx_tipVeh='N' THEN CAST('Montacarga' AS CHARACTER VARYING) ";  
                strSQL+="        ELSE CASE WHEN a1.tx_tipVeh='O' THEN CAST('Otros' AS CHARACTER VARYING) END END END END AS tx_tipVeh, ";   
                strSQL+="        round(nd_pessopkgr,2) as nd_pessopkgr, a4.co_com, a4.tx_deslar as tx_tipcom,";
                strSQL+="        round(nd_captan,2) as nd_captan, round(nd_renveh,2) as nd_renveh,";
                strSQL+="        a3.co_tra, (a3.tx_nom ||' ' || a3.tx_ape) as chofer, a1.ne_ultKil";
                strSQL+=" FROM tbm_veh as a1";
                strSQL+=" LEFT OUTER JOIN tbm_marVeh as a2 ON (a1.co_mar=a2.co_mar)";
                strSQL+=" LEFT OUTER JOIN tbm_tra as a3 ON (a1.co_cho=a3.co_tra)";
                strSQL+=" LEFT OUTER JOIN tbm_comVeh as a4 ON(a1.co_com=a4.co_com)";
                strSQL+=" WHERE 1=1";
                if(optFil.isSelected())
                {
                    if(txtCodMar.getText().length()>0)
                        strSQL+=" AND a2.co_mar=" + txtCodMar.getText();

                    if(txtCodCom.getText().length()>0)
                        strSQL+=" AND a1.co_com=" + txtCodCom.getText();

                    if (cboTipVeh.getSelectedIndex() > 0) {
                        strSQL += " AND tx_tipVeh='" + vecTipVeh.get(cboTipVeh.getSelectedIndex()) + "'";
                    }
                }
                strSQL+=" ORDER BY a1.co_veh";
                
                System.out.println("cargarDetReg: "+strSQL);   
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
                        vecReg.add(INT_TBL_DAT_COD_VEH, rst.getString("co_veh")==null?"":rst.getString("co_veh"));
                        vecReg.add(INT_TBL_DAT_PLA_VEH, rst.getString("tx_pla")==null?"":rst.getString("tx_pla"));
                        vecReg.add(INT_TBL_DAT_NOM_VEH, rst.getString("nomveh")==null?"":rst.getString("nomveh"));
                        vecReg.add(INT_TBL_DAT_TIP_VEH, rst.getString("tx_tipveh")==null?"":rst.getString("tx_tipveh"));
                        vecReg.add(INT_TBL_DAT_COD_MAR, rst.getString("co_mar")==null?"":rst.getString("co_mar"));
                        vecReg.add(INT_TBL_DAT_BUT_MAR, "");
                        vecReg.add(INT_TBL_DAT_NOM_MAR, rst.getString("nommar")==null?"":rst.getString("nommar"));
                        vecReg.add(INT_TBL_DAT_COD_COM, rst.getString("co_com")==null?"":rst.getString("co_com"));
                        vecReg.add(INT_TBL_DAT_BUT_COM, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM, rst.getString("tx_tipcom")==null?"":rst.getString("tx_tipcom"));
                        vecReg.add(INT_TBL_DAT_PES_SOP, rst.getString("nd_pessopkgr")==null?"":rst.getString("nd_pessopkgr"));
                        vecReg.add(INT_TBL_DAT_CAP_TAN, rst.getString("nd_captan")==null?"":rst.getString("nd_captan"));
                        vecReg.add(INT_TBL_DAT_REN_VEH, rst.getString("nd_renveh")==null?"":rst.getString("nd_renveh"));
                        vecReg.add(INT_TBL_DAT_COD_CHO, rst.getString("co_tra")==null?"":rst.getString("co_tra"));
                        vecReg.add(INT_TBL_DAT_BUT_CHO, "");
                        vecReg.add(INT_TBL_DAT_NOM_CHO, rst.getString("chofer")==null?"":rst.getString("chofer"));
                        vecReg.add(INT_TBL_DAT_BUT, "");
                        vecReg.add(INT_TBL_DAT_KIL_VEH, rst.getString("ne_ultKil")==null?"":rst.getString("ne_ultKil"));
                        vecDat.add(vecReg);    
                    }
                    else
                        break;
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
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int intNumFil, i, intMaxHis=-1;
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                con.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++){
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                        
                        strSQL="";
                        strSQL+=" SELECT co_veh, MAX(co_his) + 1 as max FROM tbh_veh where co_veh="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_VEH);
                        strSQL+=" GROUP BY co_veh ";
                        rstLoc=stm.executeQuery(strSQL);
                        if(rstLoc.next()){
                            intMaxHis = rstLoc.getInt("max");
                        }else{
                            intMaxHis = 1;
                        }
                        rstLoc.close();
                        rstLoc=null;
                        
                        strSQL="";
                        strSQL+=" INSERT INTO tbh_veh (co_veh,co_his,tx_pla,tx_desLar,co_mar,tx_tipVeh,nd_pesSopKgr,co_cho,nd_capTan,nd_renVeh,\n";        
                        strSQL+="                      tx_obs1,fe_ing,fe_ultMod,co_usrIng,co_usrMod,st_reg,co_com,ne_ultKil) \n";
                        strSQL+=" SELECT co_veh,"+intMaxHis+" as co_his, tx_pla,tx_desLar,co_mar,tx_tipVeh,nd_pesSopKgr,co_cho,nd_capTan,nd_renVeh,  \n";
                        strSQL+="        tx_obs1,fe_ing,fe_ultMod,co_usrIng,co_usrMod,st_reg,co_com,ne_ultKil \n";
                        strSQL+=" FROM tbm_veh \n";
                        strSQL+=" WHERE co_veh = "+objTblMod.getValueAt(i,INT_TBL_DAT_COD_VEH)+" ; \n";
                        
                        stm.executeUpdate(strSQL);
                        
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_veh";
                        strSQL+=" SET tx_pla='" + objTblMod.getValueAt(i,INT_TBL_DAT_PLA_VEH)+"'";
                        strSQL+=",tx_deslar='" + objTblMod.getValueAt(i,INT_TBL_DAT_NOM_VEH) +"'";
                        strSQL+=",co_mar=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAR),2) +"";  
                        if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().length()>0)
                        {
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Carro"))
                                {strSQL+=",tx_tipVeh='C'";}
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Moto"))
                                {strSQL+=",tx_tipVeh='M'";}
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Montacarga"))
                                {strSQL+=",tx_tipVeh='N'";}
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Otros"))
                                {strSQL+=",tx_tipVeh='O'";}
                        }
                        strSQL+=",co_com=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_COM),2) +"";
                        strSQL+=",nd_pessopkgr=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PES_SOP),2) +"";
                        strSQL+=",nd_capTan=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_CAP_TAN),2) +"";
                        strSQL+=",nd_renveh=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_REN_VEH),2) +"";
                        strSQL+=",co_cho=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CHO),2) +"";
                        strSQL+=",ne_ultKil=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_KIL_VEH),2) +"";
                        strSQL+=",fe_ultMod='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())  + "'";
                        strSQL+=",co_usrmod="+ objParSis.getCodigoUsuario() + "";
                        strSQL+=",st_reg='A'";
                        
                        strSQL+=" WHERE co_veh=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_VEH) + "";
                        stm.executeUpdate(strSQL);
                        con.commit();
                    }
                }
                stm.close();
                con.close();
                stm=null;
                con=null;
                datFecAux=null;
                //Inicializo el estado de las filas.
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
   
    
    
    
    
}


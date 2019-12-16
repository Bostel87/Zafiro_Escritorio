/*
 * ZafCom86.java
 *
 * Created on 26 de Mayo del 2016, 16:17
 */

package Compras.ZafCom86;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JFrame;
import java.util.Vector;
import javax.swing.JTextField;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafCom86 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN=0;                          //Línea
    private static final int INT_TBL_DAT_CODSEG=1;                       //Código de Seguimiento.
    private static final int INT_TBL_DAT_CODEMPORDDES=2;                 //Código de Empresa Orden de Despacho.
    private static final int INT_TBL_DAT_CODLOCORDDES=3;                 //Código de Local Orden de Despacho.
    private static final int INT_TBL_DAT_CODTIPDOCORDDES=4;              //Código de Tipo de Documento Orden de Despacho.
    private static final int INT_TBL_DAT_CODDOCORDDES=5;                 //Código de Documento Orden de Despacho.
    private static final int INT_TBL_DAT_DESCORTIPDOCORDDES=6;           //Descripción corta de Tipo de Documento Orden de Despacho.
    private static final int INT_TBL_DAT_DESLARTIPDOCORDDES=7;           //Descripción larga de Tipo de Documento Orden de Despacho.
    private static final int INT_TBL_DAT_NUMDOCORDDES=8;                 //Número de Orden de Despacho.
    private static final int INT_TBL_DAT_MOTTRA=9;                       //Motivo de Traslado.
    private static final int INT_TBL_DAT_FECDOCORDDES=10;                //Fecha de Orden de Despacho.
    private static final int INT_TBL_DAT_CODBODORIORDDES=11;             //Código de Bodega Origen.
    private static final int INT_TBL_DAT_NOMBODORIORDDES=12;             //Nombre de Bodega Origen.
    private static final int INT_TBL_DAT_CODBODDESORDDES=13;             //Código de Bodega Destino.
    private static final int INT_TBL_DAT_NOMBODDESORDDES=14;             //Nombre de Bodega Destino.
    private static final int INT_TBL_DAT_CODCLIDES=15;                   //Código de Cliente Destino.
    private static final int INT_TBL_DAT_NOMCLIDES=16;                   //Nombre de Cliente Destino.
    private static final int INT_TBL_DAT_DIRCLIDES=17;                   //Dirección de Cliente Destino.
    private static final int INT_TBL_DAT_CODREGORDDES=18;                //Código de Registro.
    private static final int INT_TBL_DAT_CODEMPMOVINV=19;                //Código de Empresa.
    private static final int INT_TBL_DAT_CODLOCMOVINV=20;                //Código de Local.
    private static final int INT_TBL_DAT_CODTIPDOCMOVINV=21;             //Código de Tipo de Documento.
    private static final int INT_TBL_DAT_CODDOCMOVINV=22;                //Código de Documento.
    private static final int INT_TBL_DAT_DESCORTIPDOCMOVINV=23;          //Descripción corta de Tipo de Documento.
    private static final int INT_TBL_DAT_DESLARTIPDOCMOVINV=24;          //Descripción larga de Tipo de Documento.
    private static final int INT_TBL_DAT_NUMDOCMOVINV=25;                //Número de Documento.
    private static final int INT_TBL_DAT_FECDOCMOVINV=26;                //Fecha de Documento.
    private static final int INT_TBL_DAT_CODITM=27;                      //Código de Item.
    private static final int INT_TBL_DAT_CODALTITM=28;                   //Código alterno de Item.
    private static final int INT_TBL_DAT_CODALTDOS=29;                   //Código alterno 2 de Item.
    private static final int INT_TBL_DAT_NOMITM=30;                      //Nombre de Item.
    private static final int INT_TBL_DAT_UNIMED=31;                      //Unidad de Medida.
    private static final int INT_TBL_DAT_CANORDDES=32;                   //Cantidad de Orden de Despacho.
    private static final int INT_TBL_DAT_CANEGRBOD=33;                   //Cantidad por Egresar a Bodega.
    private static final int INT_TBL_DAT_PESTOTEGRBOD=34;                //Peso Total por Egresar a Bodega.
    private static final int INT_TBL_DAT_CANEGRDES=35;                   //Cantidad por Egresar a Despacho.
    private static final int INT_TBL_DAT_PESTOTEGRDES=36;                //Peso Total por Egresar a Despacho.
    private static final int INT_TBL_DAT_DIASINCONF=37;                  //Días sin Confirmar. 
    private static final int INT_TBL_DAT_ESTCONINV=38;                   //Estado de Confirmacion de Documento de Egreso.
    private static final int INT_TBL_DAT_BUTCONORDDES=39;                //Botón de Orden de Despacho.
    private static final int INT_TBL_DAT_BUTCONDOCINV=40;                //Botón de Documento de Inventario.
    
    //JTable: Tabla de Bodega Origen.
    private static final int INT_TBL_BODORI_LIN=0;
    private static final int INT_TBL_BODORI_CHKBOD=1;
    private static final int INT_TBL_BODORI_CODBOD=2;
    private static final int INT_TBL_BODORI_NOMBOD=3;
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;    
    private ZafTblCelEdiChk objTblCelEdiChk;                                    //Check Locales.
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenChk objTblCelRenChk;                                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelRenLbl objTblCelRenLblColNum;
    private ZafTblMod objTblModDat, objTblModBodOri;                            //Modelo de Jtable.
    private ZafMouMotAdaDat objMouMotAdaDat;                                    //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                                //Permisos Usuarios.
    private ZafVenCon vcoItm;                                                   //Ventana de consulta.
    private ZafSelFec objSelFec;                                                //Selector de Fecha.
    
    private boolean blnMarTodChkTblBodOri=true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    
    private Vector vecDat, vecCab, vecReg, vecEstReg, vecAux;
    private String strSQL, strAux;
    private String strCodItm="", strCodAlt="", strCodAlt2="",  strNomItm="";
    
    private String strVersion=" v0.5 ";                                           //Versión del Programa.  
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom86(ZafParSis obj) 
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
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenChk = new ZafTblCelRenChk();
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();            
            objTblCelRenLblColNum = new ZafTblCelRenLbl();
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
                
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setFlechaDerechaSeleccionada(true);
            objSelFec.setFlechaIzquierdaSeleccionada(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setBounds(6, 4, 600, 75);
            panFil.add(objSelFec);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3348)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3349)) 
            {
                butCer.setVisible(false);
            }
            
            txtCodItm.setVisible(false);
            
            //Configurar ZafVenCon
            configurarVenConItm();
            
            //Configurar los JTables.
            configurarTblBodOri();
            configurarTblDat();
            
            //Carga Bodegas
            cargarBodOri();
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="/* Ingresos: Solicitudes de Transferencia de Inventario */">
    private boolean configurarTblDat()
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras        
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_CODEMPORDDES,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCORDDES,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCORDDES,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCORDDES,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOCORDDES,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOCORDDES,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOCORDDES,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_MOTTRA,"Mot.Tra.");
            vecCab.add(INT_TBL_DAT_FECDOCORDDES,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODBODORIORDDES,"Cód.Bod.Ori.");
            vecCab.add(INT_TBL_DAT_NOMBODORIORDDES,"Bod.Ori.");
             vecCab.add(INT_TBL_DAT_CODBODDESORDDES,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_NOMBODDESORDDES,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_CODCLIDES,"Cód.Cli.Des.");
            vecCab.add(INT_TBL_DAT_NOMCLIDES,"Destinatario");
            vecCab.add(INT_TBL_DAT_DIRCLIDES,"Destino");
            vecCab.add(INT_TBL_DAT_CODREGORDDES,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_CODEMPMOVINV,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCMOVINV,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCMOVINV,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCMOVINV,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOCMOVINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOCMOVINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOCMOVINV,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOCMOVINV,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODITM,"Cód.Itm.");        
            vecCab.add(INT_TBL_DAT_CODALTITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_CODALTDOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOMITM,"Nom.Itm.");
            vecCab.add(INT_TBL_DAT_UNIMED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_CANORDDES,"Can.Gui.Ord.Des.");
            vecCab.add(INT_TBL_DAT_CANEGRBOD,"Can.Egr.Bod.");
            vecCab.add(INT_TBL_DAT_PESTOTEGRBOD,"Pes.Tot.Egr.Bod.");
            vecCab.add(INT_TBL_DAT_CANEGRDES,"Can.Egr.Des.");
            vecCab.add(INT_TBL_DAT_PESTOTEGRDES,"Pes.Tot.Egr.Des.");
            vecCab.add(INT_TBL_DAT_DIASINCONF,"Días");
            vecCab.add(INT_TBL_DAT_ESTCONINV,"Est.Con.Inv.");
            vecCab.add(INT_TBL_DAT_BUTCONORDDES,"...");
            vecCab.add(INT_TBL_DAT_BUTCONDOCINV,"...");
            
            objTblModDat=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat); 
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);

            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAdaDat objMouMotAda=new ZafMouMotAdaDat();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
             //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel(); 
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Establece tamaño.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCORDDES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCORDDES).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCORDDES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_MOTTRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCORDDES).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODORIORDDES).setPreferredWidth(100);
             tcmAux.getColumn(INT_TBL_DAT_NOMBODDESORDDES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOMCLIDES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DIRCLIDES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_CODITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CODALTITM).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMITM).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_DAT_UNIMED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CANORDDES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CANEGRDES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PESTOTEGRDES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CANEGRBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PESTOTEGRBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIASINCONF).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ESTCONINV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONORDDES).setPreferredWidth(20);
            
            //Columnas Ocultas
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMPORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOCORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOCORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODBODORIORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODBODDESORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODCLIDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODREGORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMPMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOCMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOCMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_NUMDOCMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_FECDOCMOVINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODITM, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CANORDDES, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_BUTCONDOCINV, tblDat);
                       
            if(objParSis.getCodigoUsuario()!= 1)
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_ESTCONINV, tblDat);
            
            //Agrupamiento de Columnas.
            ZafTblHeaGrp objTblHeaGrpEgr=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpEgr.setHeight(16*2);

            ZafTblHeaColGrp objTblHeaColGrpEgr=new ZafTblHeaColGrp(" EGRESOS: SOLICITUDES DE TRANSFERENCIAS DE INVENTARIO ");
            objTblHeaColGrpEgr.setHeight(16);
            
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_MOTTRA));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_FECDOCORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODBODORIORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODORIORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODBODDESORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODDESORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODCLIDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_NOMCLIDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_DIRCLIDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODREGORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODITM));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODALTITM));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CODALTDOS));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_NOMITM));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_UNIMED));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CANORDDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CANEGRBOD));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_PESTOTEGRBOD));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_CANEGRDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_PESTOTEGRDES));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_DIASINCONF));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_ESTCONINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV));
            objTblHeaColGrpEgr.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONORDDES));

            objTblHeaGrpEgr.addColumnGroup(objTblHeaColGrpEgr);
            objTblHeaColGrpEgr=null;

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTCONDOCINV);
            vecAux.add("" + INT_TBL_DAT_BUTCONORDDES);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux=null;

            //Establecer Botón.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONORDDES).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUTCONDOCINV:
                            if (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMPMOVINV).toString().equals("")) 
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;

                        case INT_TBL_DAT_BUTCONORDDES:
                            if(objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMPORDDES).toString().equals(""))
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONORDDES).setCellEditor(objTblCelEdiButGen);
           
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUTCONDOCINV:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPMOVINV).toString().equals("")))
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            case INT_TBL_DAT_BUTCONORDDES:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMPORDDES).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUTCONDOCINV:
                                cargarVentanaMovInv(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMPMOVINV).toString(), 
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOCMOVINV).toString(),
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOCMOVINV).toString(),
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOCMOVINV).toString()
                                                    );
                                break;
                            case INT_TBL_DAT_BUTCONORDDES:
                                cargarVentanaGuiRem(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMPORDDES).toString(), 
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOCORDDES).toString(),
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOCORDDES).toString(), 
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOCORDDES).toString() 
                                                   );
                                break;
                        }
                    }
                }
            });           
            
            //Establecer Tabla Editable.
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);                  
            
            //Formato
            tcmAux.getColumn(INT_TBL_DAT_CANORDDES).setCellRenderer(objTblCelRenLblColNum);
            tcmAux.getColumn(INT_TBL_DAT_CANEGRBOD).setCellRenderer(objTblCelRenLblColNum);
            tcmAux.getColumn(INT_TBL_DAT_PESTOTEGRBOD).setCellRenderer(objTblCelRenLblColNum);
            tcmAux.getColumn(INT_TBL_DAT_CANEGRDES).setCellRenderer(objTblCelRenLblColNum);
            tcmAux.getColumn(INT_TBL_DAT_PESTOTEGRDES).setCellRenderer(objTblCelRenLblColNum);
   
            objTblCelRenLblColNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblColNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            //Formato Código Alterno 2.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            
            tcmAux=null;
            blnRes = true;

        } 
        catch (Exception e) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaDat extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            
            switch (intCol) 
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código Seguimiento";
                    break;   
                case INT_TBL_DAT_CODEMPORDDES:
                    strMsg="Código de la empresa Orden de Despacho";
                    break;
                case INT_TBL_DAT_CODLOCORDDES:
                    strMsg="Código del local Orden de Despacho";
                    break;
                case INT_TBL_DAT_CODTIPDOCORDDES:
                    strMsg="Código del tipo de documento Orden de Despacho";
                    break;
                case INT_TBL_DAT_CODDOCORDDES:
                    strMsg="Código del documento Orden de Despacho";
                    break;
                case INT_TBL_DAT_DESCORTIPDOCORDDES:
                    strMsg="Descripción corta del tipo de documento Orden de Despacho";
                    break;
                case INT_TBL_DAT_DESLARTIPDOCORDDES:
                    strMsg="Descripción larga del tipo de documento Orden de Despacho";
                    break;
                case INT_TBL_DAT_NUMDOCORDDES:
                    strMsg="Número de Orden de Despacho";
                    break;
                case INT_TBL_DAT_MOTTRA:
                    strMsg="Motivo de Traslado";
                    break;
                case INT_TBL_DAT_FECDOCORDDES:
                    strMsg="Fecha de Orden de Despacho";
                    break;
                case INT_TBL_DAT_CODBODORIORDDES:
                    strMsg="Código Bodega Origen";
                    break;
                case INT_TBL_DAT_NOMBODORIORDDES:
                    strMsg="Nombre de Bodega Origen";
                    break;
                case INT_TBL_DAT_CODBODDESORDDES:
                    strMsg="Código Bodega Destino";
                    break;
                case INT_TBL_DAT_NOMBODDESORDDES:
                    strMsg="Nombre de Bodega Destino";
                    break;
                case INT_TBL_DAT_CODCLIDES:
                    strMsg="Código de Cliente Destino";
                    break;
                case INT_TBL_DAT_NOMCLIDES:
                    strMsg="Nombre de Cliente Destino";
                    break;
                case INT_TBL_DAT_DIRCLIDES:
                    strMsg="Dirección de Destino";
                    break;
                case INT_TBL_DAT_CODREGORDDES:
                    strMsg="Código de Registro";
                    break;
                case INT_TBL_DAT_CODEMPMOVINV:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOCMOVINV:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOCMOVINV:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_CODDOCMOVINV:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOCMOVINV:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DESLARTIPDOCMOVINV:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUMDOCMOVINV:
                    strMsg="Número de Documento";
                    break;
                case INT_TBL_DAT_FECDOCMOVINV:
                    strMsg="Fecha de Documento";
                    break;    
                case INT_TBL_DAT_CODITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_CODALTITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_CODALTDOS:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOMITM:
                    strMsg="Nombre del item";
                    break;   
                case INT_TBL_DAT_UNIMED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CANORDDES:
                    strMsg="Cantidad de Orden de Despacho";
                    break;
                case INT_TBL_DAT_CANEGRDES:
                    strMsg="Cantidad por Egresar de Despacho";
                    break;
                case INT_TBL_DAT_PESTOTEGRDES:
                    strMsg="Peso total (Kg) por Egresar de Despacho";
                    break;
                case INT_TBL_DAT_CANEGRBOD:
                    strMsg="Cantidad por Egresar de Bodega";
                    break;
                case INT_TBL_DAT_PESTOTEGRBOD:
                    strMsg="Peso total (Kg) por Egresar de Bodega";
                    break;   
                case INT_TBL_DAT_DIASINCONF:
                    strMsg="Días sin confirmar";
                    break;
                case INT_TBL_DAT_ESTCONINV:
                    strMsg="Estado de Confirmacion de Inventario";
                    break;
                case INT_TBL_DAT_BUTCONDOCINV:
                    strMsg="Botón Consulta Documento de Inventario";
                    break;
                case INT_TBL_DAT_BUTCONORDDES:
                    strMsg="Botón Consulta Orden de Despacho";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Movimiento de Inventario*/">
    private void cargarVentanaMovInv(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        int intCodEmp=Integer.valueOf(strCodEmp);
        int intCodLoc=Integer.valueOf(strCodLoc);
        int intCodTipDoc=Integer.valueOf(strCodTipDoc);
        int intCodDoc=Integer.valueOf(strCodDoc);
        
        Compras.ZafCom20.ZafCom20 obj = new Compras.ZafCom20.ZafCom20(objParSis, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Orden de Despacho */">
    private void cargarVentanaGuiRem(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        int intCodMnu=3497;
        Compras.ZafCom23.ZafCom23 obj1 = new Compras.ZafCom23.ZafCom23(objParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }
    //</editor-fold>
    
    
    
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
    

    /**
     * Esta función configura el JTable "tblBodOri".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBodOri()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BODORI_LIN,"");
            vecCab.add(INT_TBL_BODORI_CHKBOD,"");
            vecCab.add(INT_TBL_BODORI_CODBOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BODORI_NOMBOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBodOri=new ZafTblMod();
            objTblModBodOri.setHeader(vecCab);
            tblBod.setModel(objTblModBodOri);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BODORI_NOMBOD).setPreferredWidth(380);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBodOri());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodOriMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BODORI_CHKBOD);
            objTblModBodOri.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            //objTblEdi=new ZafTblEdi(tblBodOri);
            //Configurar JTable: Editor de búsqueda.
            //objTblBus=new ZafTblBus(tblBodOri);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
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
     * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
     * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBodOri()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
               
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a2.co_bod, a2.tx_nom, a2.st_reg ";
                    strSQL+=" FROM tbm_emp AS a1 "; 
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+" AND a2.st_reg='A' ";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod ";
                }
                else
                {
                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_bodGrp as co_bod, a2.tx_nom ";
                        strSQL+=" FROM tbr_bodlocprgusr as a ";
                        strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                        strSQL+=" INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                        strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu(); 
                        strSQL+=" AND a.co_usr="+ objParSis.getCodigoUsuario();
                        strSQL+=" AND a.tx_natBod in ('E', 'A')  ";
                        strSQL+=" ORDER BY a1.co_bodGrp ";
                    }
                    else
                    {
                        strSQL="";
                        strSQL+=" SELECT a.co_bod, a2.tx_nom ";
                        strSQL+=" FROM tbr_bodlocprgusr as a  ";
                        strSQL+=" INNER JOIN tbm_bod as a2 ON (a.co_Emp=a2.co_emp and a.co_bod=a2.co_bod) ";
                        strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu(); 
                        strSQL+=" AND a.co_usr="+ objParSis.getCodigoUsuario();
                        strSQL+=" AND a.tx_natBod in ('E', 'A') ";
                        strSQL+=" ORDER BY a.co_bod ";
                    }
                }
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_BODORI_LIN,"");
                    vecReg.add(INT_TBL_BODORI_CHKBOD,true);
                    vecReg.add(INT_TBL_BODORI_CODBOD, rst.getString("co_bod") );
                    vecReg.add(INT_TBL_BODORI_NOMBOD, rst.getString("tx_nom") );
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBodOri.setData(vecDat);
                tblBod.setModel(objTblModBodOri);
                vecDat.clear();
                blnMarTodChkTblBodOri=false;
            }
        }
        catch (java.sql.SQLException e)  {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
   
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodOriMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBodOri.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BODORI_CHKBOD)
            {
                if (blnMarTodChkTblBodOri)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodOri.setChecked(true, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodOri.setChecked(false, i, INT_TBL_BODORI_CHKBOD);
                    }
                    blnMarTodChkTblBodOri=true;
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
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    

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
            arlAli.add("Cód.Alt.2");
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
            strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a.st_reg NOT IN ('U','T') ";  
            strSQL+=" ORDER BY a.tx_codalt ";
            //System.out.println("configurarVenConItm: "+strSQL);
            
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
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
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
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
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
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
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
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
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
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBodOri extends java.awt.event.MouseMotionAdapter
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BODORI_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BODORI_CHKBOD:
                    strMsg="";
                    break;
                case INT_TBL_BODORI_CODBOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BODORI_NOMBOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
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
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkItmCanPenBod = new javax.swing.JCheckBox();
        chkItmCanPenDes = new javax.swing.JCheckBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
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
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()
        {
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
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
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
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setPreferredSize(new java.awt.Dimension(0, 360));

        panFil.setPreferredSize(new java.awt.Dimension(10, 430));
        panFil.setLayout(null);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

        spnBod.setPreferredSize(new java.awt.Dimension(445, 380));

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

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(5, 90, 620, 100);

        chkItmCanPenBod.setSelected(true);
        chkItmCanPenBod.setText("Mostrar los ítems con cantidad pendiente de egresar de bodega.");
        chkItmCanPenBod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkItmCanPenBodStateChanged(evt);
            }
        });
        panFil.add(chkItmCanPenBod);
        chkItmCanPenBod.setBounds(30, 350, 550, 23);

        chkItmCanPenDes.setSelected(true);
        chkItmCanPenDes.setText("Mostrar los ítems con cantidad pendiente de egresar de despacho.");
        chkItmCanPenDes.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkItmCanPenDesStateChanged(evt);
            }
        });
        panFil.add(chkItmCanPenDes);
        chkItmCanPenDes.setBounds(30, 370, 550, 23);

        optTod.setSelected(true);
        optTod.setText("Todos los ítems");
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
        panFil.add(optTod);
        optTod.setBounds(10, 200, 400, 20);

        optFil.setText("Sólo los  ítems que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 220, 400, 20);

        lblItm.setText("Item:");
        panFil.add(lblItm);
        lblItm.setBounds(30, 250, 80, 14);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(70, 250, 30, 20);

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
        panFil.add(txtCodAltItm);
        txtCodAltItm.setBounds(100, 250, 84, 20);

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
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(184, 250, 67, 20);

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
        panFil.add(txtNomItm);
        txtNomItm.setBounds(251, 250, 370, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(620, 250, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmDesHas.setLayout(null);

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
        txtCodAltItmDes.setBounds(60, 20, 100, 20);

        lblHasItm.setText("Hasta:");
        panItmDesHas.add(lblHasItm);
        lblHasItm.setBounds(170, 20, 50, 16);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(220, 20, 100, 20);

        panFil.add(panItmDesHas);
        panItmDesHas.setBounds(30, 290, 330, 48);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmTer.setLayout(null);

        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(20, 20, 120, 16);

        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(140, 20, 92, 20);

        panFil.add(panItmTer);
        panItmTer.setBounds(380, 290, 260, 48);

        spnFil.setViewportView(panFil);
        panFil.getAccessibleContext().setAccessibleParent(spnFil);

        tabFrm.addTab("Filtro", spnFil);
        spnFil.getAccessibleContext().setAccessibleParent(tabFrm);

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setOpaque(false);
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

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(430, 180, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

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

    /* Cerrar la aplicación */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
            chkItmCanPenBod.setSelected(true);
            chkItmCanPenDes.setSelected(true);
          
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected())
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2 = txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
        {
            txtCodAlt2.setText(strCodAlt2);
        }

        if (txtCodAlt2.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm = txtNomItm.getText();
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
        {
            txtNomItm.setText(strNomItm);
        }

        if (txtNomItm.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        if (txtCodAlt2.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        if (txtCodAltItmDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt2.setText("");
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
            txtCodAltItm.setText("");
            txtCodAlt2.setText("");
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
            txtCodAltItm.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        strCodAlt=txtCodAltItm.getText();
        txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAltItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(1);
            }
        } 
        else
          txtCodAltItm.setText(strCodAlt);
        
        if (txtCodAltItm.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
        
    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        txtCodAltItm.transferFocus();
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void chkItmCanPenBodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkItmCanPenBodStateChanged
        if((!chkItmCanPenBod.isSelected()) && (!chkItmCanPenDes.isSelected()))
            chkItmCanPenBod.setSelected(true);
    }//GEN-LAST:event_chkItmCanPenBodStateChanged

    private void chkItmCanPenDesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkItmCanPenDesStateChanged
        if((!chkItmCanPenBod.isSelected()) && (!chkItmCanPenDes.isSelected()))
            chkItmCanPenDes.setSelected(true);
    }//GEN-LAST:event_chkItmCanPenDesStateChanged
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkItmCanPenBod;
    private javax.swing.JCheckBox chkItmCanPenDes;
    private javax.swing.JLabel lblDesItm;
    private javax.swing.JLabel lblHasItm;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
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
    
    
    private void exitForm() 
    {
        dispose();
    } 
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblBodOri, i, j;
        
        //Validar que esté seleccionado al menos una bodega (Origen).
        intNumFilTblBodOri=objTblModBodOri.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblBodOri; j++)
        {
            if (objTblModBodOri.isChecked(j, INT_TBL_BODORI_CHKBOD))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos una bodega Origen.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            tblBod.requestFocus();
            return false;
        }
   
        return true;
    }
    
    
    private String sqlConFil() 
    {
        String sqlFil="";
        String strTerItm="";
        boolean blnTerItm=false;
        
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Fecha del documento. */">
        if(objSelFec.isCheckBoxChecked())
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    sqlFil += " AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil += " AND a.fe_doc <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil += " AND a.fe_doc >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Origen. */">
        String strAuxBod="";
        for (int j = 0; j < tblBod.getRowCount(); j++) 
        {
            if (tblBod.getValueAt(j, INT_TBL_BODORI_CHKBOD) != null) 
            {
                if (tblBod.getValueAt(j, INT_TBL_BODORI_CHKBOD).toString().equals("true")) 
                {
                    if(strAuxBod.equals("")) strAuxBod +=  tblBod.getValueAt(j, INT_TBL_BODORI_CODBOD).toString();
                    else strAuxBod += ","+tblBod.getValueAt(j, INT_TBL_BODORI_CODBOD).toString();
                } 
            }
        }
        sqlFil+=" AND ( b.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND b.co_bodGrp IN ( "+strAuxBod+" ) )";
        //</editor-fold>
        
        if (optFil.isSelected()) 
        {
            //Búsqueda por ítem.
            if (txtCodItm.getText().length()>0)
                sqlFil+=" AND a4.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_itm=" + txtCodItm.getText() +")";
              
            if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
               sqlFil+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
        
            //Items Terminan Con.
            if (txtCodAltItmTer.getText().length()>0) {
                sqlFil+=getConSQLAdiCamTer("a1.tx_codAlt",txtCodAltItmTer.getText().trim());
            }
        }
        
        System.out.println("Filtro: "+sqlFil);
        return sqlFil;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
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
                strSQL+="SELECT Seg.co_Seg, z.* FROM ( \n";
                strSQL+=" SELECT * FROM  \n";
                strSQL+=" ( \n";
                strSQL+=" 	SELECT CASE WHEN x.tx_tipMov IN ('E','V') THEN 'VENTA' ELSE 'REPOSICIÓN' END as MotTra, \n";
                strSQL+=" 	       CASE WHEN st_ConInvGui IN ('P','E') THEN st_conInvMov ELSE st_ConInvGui END as EstConInv, \n";
                strSQL+=" 	       x.CodEmpOrdDes, x.CodLocOrdDes, x.CodTipDocOrdDes, x.DesCorOrdDes, x.DesLarOrdDes, \n";
                strSQL+="              x.CodDocOrdDes, x.NumOrdDes,  x.FecDocOrdDes, x.CodBodOrg, x.NomBodOrg, x.CodBodDes , x.NomBodDes, \n";
                strSQL+=" 	       x.CodRegMovInv, x.co_itm, x.tx_codAlt, x.tx_codAlt2, x.tx_nomItm, x.tx_uniMed, \n"; 
                strSQL+=" 	       x.nd_can, (x.nd_Can - (x.nd_CanCon + x.nd_canNunRec + x.nd_canTra)) as CanPenTot, \n";  
                strSQL+=" 	       x.CanPenBod, (x.nd_pesItmKgr * x.CanPenBod) as nd_pesTotKgrEgrBod, \n";
                strSQL+=" 	       x.CanPenDes, (x.nd_pesItmKgr * x.CanPenDes) as nd_pesTotKgrEgrDes, \n";
                strSQL+=" 	       x.co_cli, x.tx_nomCli, x.tx_dirCliDes,  x.CodEmpMovInv, x.CodLocMovInv, x.CodTipDocMovInv, x.CodDocMovInv, x.NumDocMovInv, x.FecDocMovInv , x.diasSinConf \n";
                strSQL+=" 	FROM   \n";
                strSQL+="  	(      \n";
                strSQL+="  	    SELECT a3.co_emp as CodEmpOrdDes, a3.co_loc as CodLocOrdDes, a3.co_tipDoc as CodTipDocOrdDes, t.tx_descor as DesCorOrdDes, t.tx_deslar as DesLarOrdDes, \n";
                strSQL+="  	           a3.co_doc as CodDocOrdDes, a3.ne_numOrdDes as NumOrdDes, a3.fe_doc as FecDocOrdDes,  \n";
                strSQL+="  	           b.co_bodGrp as CodBodOrg, b1.tx_nom as NomBodOrg,   \n";
	        strSQL+="  	           CASE WHEN Sol.CodBodDes IS NULL THEN 0 ELSE Sol.CodBodDes END as CodBodDes, Sol.NomBodDes, \n";
                strSQL+=" 		   a1.co_reg as CodRegMovInv, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm,  \n";	    
                strSQL+="  		   a1.tx_uniMed, inv.nd_pesItmKgr, a1.nd_can*-1 as nd_can,  \n";
                strSQL+="  		   CASE WHEN a2.nd_CanCon = 0 THEN a1.nd_CanCon*-1 ELSE abs(a2.nd_CanCon) END as nd_CanCon, \n";
                strSQL+="  		   CASE WHEN a1.nd_canNunRec = 0 THEN abs(a2.nd_CanNunRec) ELSE a1.nd_canNunRec*-1 END as nd_canNunRec,  \n";
                strSQL+="  		   CASE WHEN a1.nd_canTra IS NULL THEN 0 ELSE a1.nd_canTra*-1 END as nd_canTra,  \n";
                strSQL+="  		   CASE WHEN a1.nd_CanEgrBod IS NULL THEN 0 ELSE a1.nd_CanEgrBod*-1 END as CanPenBod,   \n";
                strSQL+="  		   CASE WHEN a1.nd_CanDesEntCli IS NULL THEN 0 ELSE a1.nd_CanDesEntCli*-1  END as CanPenDes,   \n";
                strSQL+="  		   a3.co_clides  as co_cli, a3.tx_nomclides as tx_nomCli, a3.tx_dirCliDes, a.tx_tipMov , (current_date - a3.fe_Doc) as diasSinConf,  \n";
                strSQL+="  		   CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END as st_conInvMov, a3.st_ConInv as st_ConInvGui,  \n";
                strSQL+="  		   a.co_emp as CodEmpMovInv, a.co_loc as CodLocMovInv, a.co_tipDoc as CodTipDocMovInv, a.co_doc as CodDocMovInv, a.ne_numDoc as NumDocMovInv, a.fe_Doc as FecDocMovInv \n";
                strSQL+="  	    FROM tbm_cabMovInv AS a  \n"; 
                strSQL+="  	    INNER JOIN tbm_detMovInv AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc  \n";
                strSQL+="  	    INNER JOIN tbm_detGuiRem as a2 ON (a2.co_empRel=a.co_emp AND a2.co_locRel=a.co_loc AND a2.co_tipDocRel=a.co_tipDoc AND a2.co_docRel=a.co_doc AND a2.co_regRel=a1.co_Reg)    \n";
                strSQL+="  	    INNER JOIN tbm_cabGuiRem as a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_TipDoc=a2.co_tiPDoc AND a3.co_doc=a2.co_doc) \n";
                strSQL+="  	    INNER JOIN tbm_cabTipDoc AS t ON (t.co_emp=a3.co_emp AND t.co_loc=a3.co_loc AND t.co_tipDoc=a3.co_tipDoc) \n";  
                strSQL+="  	    INNER JOIN tbr_bodEmpBodGrp AS b   \n";
                strSQL+="  	    ON ( case when a.co_tipDoc =206 and a.fe_doc>='2016-07-26' and a.fe_doc<'2016-09-09'  then  (b.co_bodgrp=a1.co_bod )  else (b.co_emp=a.co_emp and b.co_bod=a1.co_bod) end )   \n";
                strSQL+="  	    INNER JOIN tbm_bod as b1 ON (b1.co_Emp=b.co_empGrp AND b1.co_bod=b.co_bodGrp) \n";
                strSQL+="  	    LEFT OUTER JOIN  \n";
                strSQL+="  	    ( \n";
                strSQL+="  	    	select a.co_emp as CodEmpSol, a.co_loc as CodLocSol, a.co_tipDoc as CodTipDocSol, a.co_doc as CodDocSol,  \n";
                strSQL+="  	    	       t1.tx_DesCor as DesCorSol, t1.tx_DesLar as DesLarSol, a.ne_numDoc as NumDocSol, \n";
                strSQL+="  	    	       a.co_bodDes as CodBodDes, b1.tx_nom as NomBodDes \n";
                strSQL+="  	    	from tbm_cabSolTraInv as a  \n";
		strSQL+="  	    	inner join tbm_cabTipDoc as t1 on (t1.co_emp=a.co_emp AND t1.co_loc=a.co_loc AND t1.co_tipDoc=a.co_tipDoc)  \n";
		strSQL+="  	    	inner join tbm_bod as b1 on (b1.co_Emp=a.co_emp AND b1.co_bod=a.co_bodDes) \n";
		strSQL+="  	    	where a.st_reg='A' \n";
                strSQL+="  	    ) as Sol ON (Sol.CodEmpSol=a.co_empRelCabSolTraInv AND Sol.CodLocSol=a.co_locRelCabSolTraInv AND Sol.CodTipDocSol=a.co_tipDocRelCabSolTraInv AND Sol.CodDocSol=a.co_docRelCabSolTraInv)  \n";  
                strSQL+="  	    INNER JOIN tbm_equinv as a4 ON (a4.co_emp=a1.co_emp and a4.co_itm=a1.co_itm ) \n";
                strSQL+="  	    INNER JOIN tbm_inv as inv ON (inv.co_Emp=a1.co_Emp AND inv.co_itm=a1.co_itm)  \n";
                strSQL+="           WHERE a3.ne_numDoc=0 AND a.st_reg='A' AND a1.nd_can < 0 AND a1.st_meringegrfisbod='S' AND a3.st_conInv in ('P', 'E') AND a.tx_tipMov IS NOT NULL \n";
                strSQL+="          "+strFil+" \n";
                strSQL+="   	    GROUP BY  a3.co_emp, a3.co_loc, a3.co_tipDoc, t.tx_descor, t.tx_deslar, a3.co_doc, a3.ne_numOrdDes, a3.fe_doc, \n";
                strSQL+="   	              b.co_bodGrp, b1.tx_nom, Sol.CodBodDes, Sol.NomBodDes, \n";
                strSQL+="		      a1.co_reg, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.tx_uniMed, inv.nd_pesItmKgr, ";
                strSQL+="		      a1.nd_can, a1.nd_canCon, a1.nd_canNunRec, a2.nd_canCon, a2.nd_canNunRec, a1.nd_canTra, a1.nd_canPen, \n";
                strSQL+="                     a1.nd_CanEgrBod, a1.nd_CanDesEntCli, a3.co_clides, a3.tx_dirCliDes, a3.tx_nomclides ,a.tx_tipMov, a.st_conInv, a3.st_ConInv, \n";
                strSQL+="                     a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc , a.ne_numDoc, a.fe_Doc \n";
                strSQL+=" 	) as x WHERE x.st_conInvMov  NOT IN ('F') \n";
                strSQL+=" ) as y WHERE  \n";
                
                if(chkItmCanPenBod.isSelected() && chkItmCanPenDes.isSelected())
                    strSQL+=" (y.CanPenBod>0 OR y.CanPenDes>0) OR y.CanPenTot>0  ";
                else
                {
                    if(chkItmCanPenBod.isSelected())
                        strSQL+=" (y.CanPenBod>0)   ";
                    else if(chkItmCanPenDes.isSelected())
                        strSQL+=" (y.CanPenDes>0)  ";
                }
                strSQL+=" \n ) as z  LEFT OUTER JOIN tbm_cabSegMovInv as Seg  ";
                strSQL+=" ON (Seg.co_empRelCabGuiRem=z.CodEmpOrdDes AND Seg.co_locRelCabGuiRem=z.CodLocOrdDes AND Seg.co_tipDocRelCabGuiRem=z.CodTipDocOrdDes AND Seg.co_DocRelCabGuiRem=z.CodDocOrdDes) \n";
                strSQL+=" GROUP BY Seg.co_Seg, z.MotTra, z.EstConInv, z.CodEmpOrdDes, z.CodLocOrdDes, z.CodTipDocOrdDes, z.DesCorOrdDes, z.DesLarOrdDes, z.CodDocOrdDes, z.NumOrdDes, z.FecDocOrdDes, \n";
                strSQL+="          z.CodBodOrg, z.NomBodOrg, z.CodBodDes , z.NomBodDes, z.CodRegMovInv, z.co_itm, z.tx_codAlt, z.tx_codAlt2, z.tx_nomItm, z.tx_uniMed, z.nd_can, z.CanPenTot, \n";
 	        strSQL+="          z.CanPenBod,  z.CanPenDes, z.nd_pesTotKgrEgrBod, z.nd_pesTotKgrEgrDes, z.co_cli, z.tx_nomCli, z.tx_dirCliDes, z.CodEmpMovInv, z.CodLocMovInv, z.CodTipDocMovInv, \n";
 	        strSQL+="          z.CodDocMovInv, z.NumDocMovInv, z.FecDocMovInv , z.diasSinConf \n";
                strSQL+=" ORDER BY z.CodBodOrg,  z.NumOrdDes , z.FecDocOrdDes \n";
                
                //System.out.println("cargarDetReg: "+strSQL);   
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
                        vecReg.add(INT_TBL_DAT_CODSEG, rst.getString("co_seg")==null?"":rst.getString("co_seg"));
                        vecReg.add(INT_TBL_DAT_CODEMPORDDES, rst.getString("CodEmpOrdDes")==null?"":rst.getString("CodEmpOrdDes")  );
                        vecReg.add(INT_TBL_DAT_CODLOCORDDES, rst.getString("CodLocOrdDes")==null?"":rst.getString("CodLocOrdDes")  );
                        vecReg.add(INT_TBL_DAT_CODTIPDOCORDDES, rst.getString("CodTipDocOrdDes")==null?"":rst.getString("CodTipDocOrdDes")  );
                        vecReg.add(INT_TBL_DAT_CODDOCORDDES, rst.getString("CodDocOrdDes")==null?"":rst.getString("CodDocOrdDes")  );
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOCORDDES, rst.getString("DesCorOrdDes")==null?"":rst.getString("DesCorOrdDes")  );
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOCORDDES, rst.getString("DesLarOrdDes")==null?"":rst.getString("DesLarOrdDes")  );
                        vecReg.add(INT_TBL_DAT_NUMDOCORDDES, rst.getString("NumOrdDes")==null?"":rst.getString("NumOrdDes")  );
                        vecReg.add(INT_TBL_DAT_MOTTRA, rst.getString("MotTra")==null?"":rst.getString("MotTra") );
                        vecReg.add(INT_TBL_DAT_FECDOCORDDES, rst.getString("FecDocOrdDes")==null?"":rst.getString("FecDocOrdDes")  );
                        vecReg.add(INT_TBL_DAT_CODBODORIORDDES, rst.getString("CodBodOrg")==null?"":rst.getString("CodBodOrg")  );                  
                        vecReg.add(INT_TBL_DAT_NOMBODORIORDDES, rst.getString("NomBodOrg")==null?"":rst.getString("NomBodOrg")  );
                        vecReg.add(INT_TBL_DAT_CODBODDESORDDES, rst.getString("CodBodDes")==null?"":rst.getString("CodBodDes")  );                  
                        vecReg.add(INT_TBL_DAT_NOMBODDESORDDES, rst.getString("NomBodDes")==null?"":rst.getString("NomBodDes")  );
                        vecReg.add(INT_TBL_DAT_CODCLIDES, rst.getString("co_cli")==null?"":rst.getString("co_cli")  );
                        vecReg.add(INT_TBL_DAT_NOMCLIDES, rst.getString("tx_nomCli")==null?"":rst.getString("tx_nomCli")  );
                        vecReg.add(INT_TBL_DAT_DIRCLIDES, rst.getString("tx_dirCliDes")==null?"":rst.getString("tx_dirCliDes")  );
                        vecReg.add(INT_TBL_DAT_CODREGORDDES,  rst.getString("CodRegMovInv")==null?"":rst.getString("CodRegMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODEMPMOVINV, rst.getString("CodEmpMovInv")==null?"":rst.getString("CodEmpMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODLOCMOVINV, rst.getString("CodLocMovInv")==null?"":rst.getString("CodLocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODTIPDOCMOVINV,  rst.getString("CodTipDocMovInv")==null?"":rst.getString("CodTipDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODDOCMOVINV, rst.getString("CodDocMovInv")==null?"":rst.getString("CodDocMovInv")  );
//                        vecReg.add(INT_TBL_DAT_DESCORTIPDOCMOVINV, rst.getString("DesCorMovInv")==null?"":rst.getString("DesCorMovInv")  );
//                        vecReg.add(INT_TBL_DAT_DESLARTIPDOCMOVINV, rst.getString("DesLarMovInv")==null?"":rst.getString("DesLarMovInv")  );
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOCMOVINV, ""  );
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOCMOVINV, "" );
                        vecReg.add(INT_TBL_DAT_NUMDOCMOVINV, rst.getString("NumDocMovInv")==null?"":rst.getString("NumDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_FECDOCMOVINV, rst.getString("FecDocMovInv")==null?"":rst.getString("FecDocMovInv")  );
                        
                        vecReg.add(INT_TBL_DAT_CODITM, rst.getString("co_itm")==null?"":rst.getString("co_itm")  );
                        vecReg.add(INT_TBL_DAT_CODALTITM, rst.getString("tx_CodAlt")==null?"":rst.getString("tx_CodAlt")  );
                        vecReg.add(INT_TBL_DAT_CODALTDOS, rst.getString("tx_codAlt2")==null?"":rst.getString("tx_codAlt2")  );
                        vecReg.add(INT_TBL_DAT_NOMITM, rst.getString("tx_nomItm")==null?"":rst.getString("tx_nomItm")  );
                        vecReg.add(INT_TBL_DAT_UNIMED, rst.getString("tx_uniMed")==null?"":rst.getString("tx_uniMed")  );
                        vecReg.add(INT_TBL_DAT_CANORDDES, rst.getString("nd_can")==null?"":rst.getString("nd_can")  );
                        vecReg.add(INT_TBL_DAT_CANEGRBOD, rst.getString("CanPenBod")==null?"":rst.getString("CanPenBod")  );
                        vecReg.add(INT_TBL_DAT_PESTOTEGRBOD,  rst.getString("nd_pesTotKgrEgrBod")==null?"":rst.getString("nd_pesTotKgrEgrBod")  );
                        vecReg.add(INT_TBL_DAT_CANEGRDES, rst.getString("CanPenDes")==null?"":rst.getString("CanPenDes")  );
                        vecReg.add(INT_TBL_DAT_PESTOTEGRDES, rst.getString("nd_pesTotKgrEgrDes")==null?"":rst.getString("nd_pesTotKgrEgrDes")  );
                        vecReg.add(INT_TBL_DAT_DIASINCONF, rst.getString("diasSinConf")==null?"":rst.getString("diasSinConf")  );
                        vecReg.add(INT_TBL_DAT_ESTCONINV, rst.getString("EstConInv")==null?"":rst.getString("EstConInv")  );
                        vecReg.add(INT_TBL_DAT_BUTCONORDDES, "" );      
                        vecReg.add(INT_TBL_DAT_BUTCONDOCINV, "" );
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
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
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
        

}

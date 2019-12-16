/*
 * ZafCom85.java
 *
 * Created on 19 de Abril del 2016, 15:26
 */

package Compras.ZafCom85;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
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
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
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
import Librerias.ZafUtil.ZafLocPrgUsr;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafCom85 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;                               //Linea.
    private static final int INT_TBL_DAT_CODSEG = 1;                            //Código de Seguimiento.
    
    private static final int INT_TBL_DAT_CODEMP_COTVEN = 2;                     //Código de Empresa de Cotización de Venta.
    private static final int INT_TBL_DAT_CODLOC_COTVEN = 3;                     //Código de Local de Cotización de Venta.
    private static final int INT_TBL_DAT_NOMLOC_COTVEN = 4;                     //Nombre del Local de Cotización de Venta.
    private static final int INT_TBL_DAT_FECDOC_COTVEN = 5;                     //Fecha de Cotización de Venta.
    private static final int INT_TBL_DAT_CODCOT_COTVEN = 6;                     //Código de Cotización de Venta.
    private static final int INT_TBL_DAT_BUT_COTVEN = 7;                        //Botón "Cotización de Venta".
    
    private static final int INT_TBL_DAT_CODEMP_SOLTRAINV = 8;                  //Código de Empresa de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODLOC_SOLTRAINV = 9;                  //Código de Local de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODTIPDOC_SOLTRAINV = 10;              //Código de Tipo Documento de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV = 11;           //Descripción Corta Tipo Documento de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV = 12;           //Descripción Larga Tipo Documento de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODDOC_SOLTRAINV = 13;                 //Código de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_NUMDOC_SOLTRAINV = 14;                 //Número de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_FECDOC_SOLTRAINV = 15;                 //Fecha de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODDES_SOLTRAINV = 16;                 //Código de Destinatario de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_NOMDES_SOLTRAINV = 17;                 //Nombre de Destinatario de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODBODORI_SOLTRAINV = 18;              //Bodega Origen Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_NOMBODORI_SOLTRAINV = 19;              //Bodega Origen Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODBODDES_SOLTRAINV = 20;              //Bodega Destino Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_NOMBODDES_SOLTRAINV = 21;              //Bodega Destino Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_PESKGR_SOLTRAINV = 22;                 //Peso Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_BUT_SOLTRAINV = 23;                    //Botón "Solicitudes de Transferencias de Inventario".
    
    private static final int INT_TBL_DAT_CHKAUT_AUTSOLTRAINV = 24;              //Solicitudes de Transferencias de Inventario Autorizadas.
    private static final int INT_TBL_DAT_CHKDEG_AUTSOLTRAINV = 25;              //Solicitudes de Transferencias de Inventario Denegadas.
    private static final int INT_TBL_DAT_CHKANU_AUTSOLTRAINV = 26;              //Solicitudes de Transferencias de Inventario Anuladas.
    private static final int INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV = 27;           //Solicitudes de Transferencias de Inventario Preautorizadas.
    private static final int INT_TBL_DAT_FECAUT_AUTSOLTRAINV = 28;              //Fecha de Autorización de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV = 29;          //Observación de Autorización de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV = 30;          //Observación de Autorización de Solicitudes de Transferencias de Inventario.
    
    private static final int INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD = 31;            //Establece si tiene OD Pedidos a Otras Bodegas.
    private static final int INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD = 32;       //Botón "Detalle del proceso de los ítems"
    
    private static final int INT_TBL_DAT_CODEMP_FACVEN = 33;                    //Código de Empresa de Facturas de Venta.
    private static final int INT_TBL_DAT_CODLOC_FACVEN = 34;                    //Código de Local de Facturas de Venta.
    private static final int INT_TBL_DAT_CODTIPDOC_FACVEN = 35;                 //Código de Tipo Documento de Facturas de Venta.
    private static final int INT_TBL_DAT_DESCORTIPDOC_FACVEN = 36;              //Descripción Corta Tipo Documento de Facturas de Venta.
    private static final int INT_TBL_DAT_DESLARTIPDOC_FACVEN = 37;              //Descripción Larga Tipo Documento de Facturas de Venta.
    private static final int INT_TBL_DAT_CODDOC_FACVEN = 38;                    //Código de Facturas de Venta.
    private static final int INT_TBL_DAT_NUMDOC_FACVEN = 39;                    //Número de Facturas de Venta.
    private static final int INT_TBL_DAT_BUT_FACVEN= 40;                        //Botón "Facturas de Venta".
    
    private static final int INT_TBL_DAT_CODEMP_ORDDESCLI = 41;                 //Código de Empresa de Ordenes de Despacho (Cliente).
    private static final int INT_TBL_DAT_CODLOC_ORDDESCLI = 42;                 //Código de Local de Ordenes de Despacho (Cliente).
    private static final int INT_TBL_DAT_CODTIPDOC_ORDDESCLI = 43;              //Código de Tipo Documento de Ordenes de Despacho (Cliente).
    private static final int INT_TBL_DAT_DESCORTIPDOC_ORDDESCLI = 44;           //Descripción Corta Tipo Documento de Ordenes de Despacho (Cliente).
    private static final int INT_TBL_DAT_DESLARTIPDOC_ORDDESCLI = 45;           //Descripción Larga Tipo Documento de Ordenes de Despacho (Cliente).
    private static final int INT_TBL_DAT_CODDOC_ORDDESCLI = 46;                 //Código de Ordenes de Despacho (Cliente).
    private static final int INT_TBL_DAT_NUMDOC_ORDDESCLI = 47;                 //Número de Ordenes de Despacho (Cliente).
    private static final int INT_TBL_DAT_BUT_ORDDESCLI = 48;                    //Botón "Ordenes de Despacho (Cliente)".
        
    private static final int INT_TBL_DAT_CHK_PROCOM = 49;                       //Proceso Completo.
    
    
    //JTable: Tabla de Bodega Origen.
    private static final int INT_TBL_BODORI_LIN=0;
    private static final int INT_TBL_BODORI_CHKBOD=1;
    private static final int INT_TBL_BODORI_CODBOD=2;
    private static final int INT_TBL_BODORI_NOMBOD=3;
    
    //JTable: Tabla de Bodega Destino.
    private static final int INT_TBL_BODDES_LIN=0;
    private static final int INT_TBL_BODDES_CHKBOD=1;
    private static final int INT_TBL_BODDES_CODBOD=2;
    private static final int INT_TBL_BODDES_NOMBOD=3;
    
    //Códigos de Menú.
    private static final int INT_COD_MNU_VEN=3519;
    private static final int INT_COD_MNU_INV=3344;
    
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
    private ZafTblMod objTblModDat, objTblModBodOri, objTblModBodDes;           //Modelo de Jtable.
    private ZafMouMotAda objMouMotAdaDat;                                       //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                                //Permisos Usuarios.
    private ZafLocPrgUsr objLocPrgUsr;                                          //Objeto que almacena los locales por usuario y programa.
    private ZafVenCon vcoTipDoc;                                                //Ventana de consulta.
    private ZafVenCon vcoDes;                                                   //Ventana de consulta.
    private ZafVenCon vcoLoc;                                                   //Ventana de consulta.
    private ZafSelFec objSelFec;                                                //Selector de Fecha.
    
    private boolean blnMarTodChkTblBodOri=true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnMarTodChkTblBodDes=true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    
    private Vector vecDat, vecCab, vecReg, vecEstReg, vecAux;
    private String strSQL, strAux;
    private String strDesCorTipDoc, strDesLarTipDoc;                            //Contenido del campo al obtener el foco.
    private String strCodCli, strIdeCli, strNomCli;                             //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                                        //Contenido del campo al obtener el foco.
    private String strCodLoc, strNomLoc;                                        //Contenido del campo al obtener el foco.
    private String strFilFecCot="", strFilSolTipDoc="";                               
    private int intCodFacVen=14;                                                //Código del Menú de Factura Comercial.
    
    private String strVersion=" v0.1.3";                                        //Versión del Programa.  
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom85(ZafParSis obj) 
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
            
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr=new ZafLocPrgUsr(objParSis);
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
                    
            //Configurar Programas por Código de Menu.
            configurarFrmMnu();
    	    
            txtCodTipDoc.setVisible(false);
            txtCodEmpLoc.setVisible(false);
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                txtCodDes.setEditable(false);
                txtIdeDes.setEditable(false);
            }
            else
            {
                txtCodDes.setEditable(true);
                txtIdeDes.setEditable(true);
            }
            
            //Configurar las ZafVenCon.
            configurarVenConLoc();
            configurarVenConTipDoc();
            configurarVenConDes();
            
            //Configurar los JTables.
            configurarTblBodOri();
            configurarTblBodDes();
            configurarTblDat();
            
            //Configurar Combos.
            configurarComboEstReg();  
            
            //Carga Bodegas
            cargarBodOri();
            cargarBodDes();
            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /** Configurar Programas por Código de Menú */
    private void configurarFrmMnu()
    {
        //Menu Ventas
        if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN )
        {
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3520)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3521)) 
            {
                butCer.setVisible(false);
            }
            
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false); 
            objSelFec.setTitulo("Fecha de Cotización ");
            panFil.add(objSelFec);
            objSelFec.setBounds(6, 4, 490, 75);
            configurarFechaDesde();
            
            //Otras Configuraciones.
            panBodOri.setVisible(false);
            panBodDes.setVisible(false);
            
            lblTipDoc.setVisible(false);
            txtCodTipDoc.setVisible(false);
            txtDesCorTipDoc.setVisible(false);
            txtDesLarTipDoc.setVisible(false);
            butTipDoc.setVisible(false);
            
            panSolTraDesHas.setVisible(false);
            lblEstReg.setVisible(false);
            cboEstReg.setVisible(false);
            
            optTod.setBounds(0, 94, 400, 20);
            optFil.setBounds(0, 114, 400, 20);
            
            lblLoc.setBounds(15, 140, 100, 20);
            txtCodEmpLoc.setBounds(119, 140, 32, 20);
            txtCodLoc.setBounds(150, 140, 94, 20);
            txtNomLoc.setBounds(244, 140, 396, 20);
            butLoc.setBounds(640, 140, 20, 20);
            
//            lblTipDoc.setBounds(15, 156, 130, 20);
//            txtCodTipDoc.setBounds(119, 156, 32, 20);
//            txtDesCorTipDoc.setBounds(150, 156, 94, 20);
//            txtDesLarTipDoc.setBounds(244, 156, 396, 20);
//            butTipDoc.setBounds(640, 156, 20, 20);
            
            lblDes.setBounds(15, 160, 130, 20);
            txtCodDes.setBounds(150, 160, 60, 20);
            txtIdeDes.setBounds(210, 160, 109, 20);
            txtNomDes.setBounds(319, 160, 321, 20);
            butDes.setBounds(640, 160, 20, 20);
            
            panCotVenDesHas.setBounds(15, 194, 325, 48);
            
            chkSolTraProPen.setBounds(15, 254, 400, 20);
            chkSolTraProCom.setBounds(15, 274, 400, 20);
            
        }
        //Menu Inventario
        else if(objParSis.getCodigoMenu()==INT_COD_MNU_INV )
        {
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3345)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3346)) 
            {
                butCer.setVisible(false);
            }
            
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false); 
            objSelFec.setTitulo("Fecha de Solicitud ");
            panFil.add(objSelFec);
            objSelFec.setBounds(6, 4, 490, 75); 
            configurarFechaDesde();
            
            //Otras Configuraciones.
            panCotVenDesHas.setVisible(false);
            lblLoc.setVisible(false);
            txtCodEmpLoc.setVisible(false);
            txtCodLoc.setVisible(false);
            txtNomLoc.setVisible(false);
            butLoc.setVisible(false);
        }
            
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
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            
            vecCab.add(INT_TBL_DAT_CODEMP_COTVEN,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_COTVEN,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOMLOC_COTVEN,"Local");
            vecCab.add(INT_TBL_DAT_FECDOC_COTVEN,"Fec.Cot.");
            vecCab.add(INT_TBL_DAT_CODCOT_COTVEN,"Cotización");  
            vecCab.add(INT_TBL_DAT_BUT_COTVEN," ");
            
            vecCab.add(INT_TBL_DAT_CODEMP_SOLTRAINV,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_SOLTRAINV,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC_SOLTRAINV,"Cód.Tip.Doc.");  
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC_SOLTRAINV,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC_SOLTRAINV,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC_SOLTRAINV,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODDES_SOLTRAINV,"Cód.Des.");
            vecCab.add(INT_TBL_DAT_NOMDES_SOLTRAINV,"Destinatario");
            vecCab.add(INT_TBL_DAT_CODBODORI_SOLTRAINV,"Cód.Bod.Ori.");
            vecCab.add(INT_TBL_DAT_NOMBODORI_SOLTRAINV,"Bod.Ori.");
            vecCab.add(INT_TBL_DAT_CODBODDES_SOLTRAINV,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_NOMBODDES_SOLTRAINV,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_PESKGR_SOLTRAINV,"Peso");
            vecCab.add(INT_TBL_DAT_BUT_SOLTRAINV," ");
            
            vecCab.add(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV,"Autorizada");
            vecCab.add(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV,"Denegada");
            vecCab.add(INT_TBL_DAT_CHKANU_AUTSOLTRAINV,"Anulada");
            vecCab.add(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV,"Preautorizada");
            vecCab.add(INT_TBL_DAT_FECAUT_AUTSOLTRAINV,"Fec.Aut.");
            vecCab.add(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV,"Obs.Aut.");
            vecCab.add(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV,"Obs.Aut.");
            vecCab.add(INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD,"Cód.Emp.Ord.Des.");
            vecCab.add(INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD," ");
                        
            
            vecCab.add(INT_TBL_DAT_CODEMP_FACVEN,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_FACVEN,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC_FACVEN,"Cód.Tip.Doc.");  
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC_FACVEN,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC_FACVEN,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC_FACVEN,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC_FACVEN,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_BUT_FACVEN," "); 
            
            vecCab.add(INT_TBL_DAT_CODEMP_ORDDESCLI,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_ORDDESCLI,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC_ORDDESCLI,"Cód.Tip.Doc.");  
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC_ORDDESCLI,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC_ORDDESCLI,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC_ORDDESCLI,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC_ORDDESCLI,"Núm.Ord.");
            vecCab.add(INT_TBL_DAT_BUT_ORDDESCLI," "); 
            
            vecCab.add(INT_TBL_DAT_CHK_PROCOM,"Proceso Completo");
            
            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_SOLTRAINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC_SOLTRAINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC_SOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_SOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODDES_SOLTRAINV).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOMDES_SOLTRAINV).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_DAT_CODBODORI_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODORI_SOLTRAINV).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_CODBODDES_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODDES_SOLTRAINV).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_PESKGR_SOLTRAINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FECAUT_AUTSOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_FACVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_FACVEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_FACVEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_FACVEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_FACVEN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC_FACVEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC_FACVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_ORDDESCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_ORDDESCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_ORDDESCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_ORDDESCLI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_ORDDESCLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC_ORDDESCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC_ORDDESCLI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ORDDESCLI).setPreferredWidth(20);
        
            tcmAux.getColumn(INT_TBL_DAT_CHK_PROCOM).setPreferredWidth(110);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Agrupar Columnas.
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            
            //Grupo: Cotizaciones de Venta.
            ZafTblHeaColGrp objTblHeaColGrpCotVen=new ZafTblHeaColGrp("Cotizaciones de Venta");
            objTblHeaColGrpCotVen.setHeight(16);
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_NOMLOC_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_FECDOC_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_CODCOT_COTVEN));
            objTblHeaColGrpCotVen.add(tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN));
            
            //Grupo: Solicitudes de Transferencia de Inventario.
            ZafTblHeaColGrp objTblHeaColGrpSolTraInv=new ZafTblHeaColGrp("Solicitudes de Transferencia de Inventario");
            objTblHeaColGrpSolTraInv.setHeight(16);
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODDOC_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOC_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_FECDOC_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODDES_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMDES_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODORI_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODORI_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODDES_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODDES_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_PESKGR_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV));
            
            //Grupo: Autorizacion.
            ZafTblHeaColGrp objTblHeaColGrpAutSolTraInv=new ZafTblHeaColGrp("Autorización Solicitud");
            objTblHeaColGrpAutSolTraInv.setHeight(16);
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_FECAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV));
            
            //Grupo: Orden de Despacho (Pedidos a Otras Bodegas).
            ZafTblHeaColGrp objTblHeaColGrpOrdDesPedOtrBod=new ZafTblHeaColGrp("Ordenes de Despacho(Pedidos a Otras Bodegas)");
            objTblHeaColGrpOrdDesPedOtrBod.setHeight(16);
            objTblHeaColGrpOrdDesPedOtrBod.add(tcmAux.getColumn(INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD));
            objTblHeaColGrpOrdDesPedOtrBod.add(tcmAux.getColumn(INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD));
            
            //Grupo: Factura de Venta.
            ZafTblHeaColGrp objTblHeaColGrpFacVen=new ZafTblHeaColGrp("Factura de Venta");
            objTblHeaColGrpFacVen.setHeight(16);
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_CODDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOC_FACVEN));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN));
            
            //Grupo: Orden de Despacho (Cliente).
            ZafTblHeaColGrp objTblHeaColGrpOrdDesCli=new ZafTblHeaColGrp("Orden de Despacho (Cliente)");
            objTblHeaColGrpOrdDesCli.setHeight(16);
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_CODEMP_ORDDESCLI));
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_CODLOC_ORDDESCLI));
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_ORDDESCLI));
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_ORDDESCLI));
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_ORDDESCLI));
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_CODDOC_ORDDESCLI));
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOC_ORDDESCLI));
            objTblHeaColGrpOrdDesCli.add(tcmAux.getColumn(INT_TBL_DAT_BUT_ORDDESCLI));
            
            //Agregar todos los grupos del Jtable.
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCotVen);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSolTraInv);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAutSolTraInv);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpOrdDesPedOtrBod);
//            objTblHeaGrp.addColumnGroup(objTblHeaColGrpProCom);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpFacVen);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpOrdDesCli);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_COTVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_COTVEN, tblDat);
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOC_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDES_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODBODORI_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODBODDES_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_PESKGR_SOLTRAINV, tblDat);
            
            if(objParSis.getCodigoMenu() == INT_COD_MNU_VEN)
            {
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV, tblDat);
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV, tblDat);
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV, tblDat);
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV, tblDat);
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_FECAUT_AUTSOLTRAINV, tblDat);
            }
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV, tblDat);
            
            if(objParSis.getCodigoMenu() == INT_COD_MNU_VEN)
            {
                objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV, tblDat);
            }
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_FACVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_FACVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC_FACVEN, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOC_FACVEN, tblDat);
            
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_ORDDESCLI, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_ORDDESCLI, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC_ORDDESCLI, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOC_ORDDESCLI, tblDat);
           
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDat=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
//            //Configurar JTable: Renderizar celdas.
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setForeground(Color.BLACK.darker());
//            tcmAux.getColumn(INT_TBL_DAT_CHK_PROCOM).setHeaderRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PESKGR_SOLTRAINV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV).setResizable(false);
         
            tcmAux.getColumn(INT_TBL_DAT_CHK_PROCOM).setResizable(false);
            
            //Configurar JTable: Establecer el check en columnas
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk);
            
            tcmAux.getColumn(INT_TBL_DAT_CHK_PROCOM).setCellRenderer(objTblCelRenChk);
            
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi objZafTblEdi = new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_COTVEN);
            vecAux.add("" + INT_TBL_DAT_BUT_SOLTRAINV);
            vecAux.add("" + INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV);
            vecAux.add("" + INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD);
            vecAux.add("" + INT_TBL_DAT_BUT_FACVEN);
            vecAux.add("" + INT_TBL_DAT_BUT_ORDDESCLI);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
                       
            //Configurar JTable:  Botones.
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ORDDESCLI).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_COTVEN:
                            if (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_COTVEN).toString().equals("")) 
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;

                        case INT_TBL_DAT_BUT_SOLTRAINV:
                            if(objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_SOLTRAINV).toString().equals(""))
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;

                        case INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV:
                            if(objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV).toString().equals(""))
                                objTblCelRenBut.setText("");
                            else
                                objTblCelRenBut.setText("...");
                        break;
                        case INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD:
                            if( (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD).toString().equals("N")))
                                objTblCelRenBut.setText("");
                            else
                                objTblCelRenBut.setText("...");
                        break;
                            
                        case INT_TBL_DAT_BUT_FACVEN:
                            if( (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_FACVEN).toString().equals("")))
                                objTblCelRenBut.setText("");
                            else
                                objTblCelRenBut.setText("...");
                        break;   
                            
                        case INT_TBL_DAT_BUT_ORDDESCLI:
                            if( (objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_ORDDESCLI).toString().equals("")))
                                objTblCelRenBut.setText("");
                            else
                                objTblCelRenBut.setText("...");
                        break;      
                    }
                }
            });
            
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DAT_BUT_COTVEN).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_FACVEN).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ORDDESCLI).setCellEditor(objTblCelEdiButGen);

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
                            case INT_TBL_DAT_BUT_COTVEN:
                                if(objParSis.getCodigoMenu()==INT_COD_MNU_INV )
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_COTVEN).toString().equals("")))
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            case INT_TBL_DAT_BUT_SOLTRAINV:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_SOLTRAINV).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            case INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            case INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD).toString().equals("N")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;   
                            case INT_TBL_DAT_BUT_FACVEN:
                                if(objParSis.getCodigoMenu()==INT_COD_MNU_INV )
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_FACVEN).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break; 
                            case INT_TBL_DAT_BUT_ORDDESCLI:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_ORDDESCLI).toString().equals("")))
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
                            case INT_TBL_DAT_BUT_COTVEN:
                                cargarVentanaCotizacion(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_COTVEN).toString(), 
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_COTVEN).toString(),
                                                        tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODCOT_COTVEN).toString() );
                                break;
                            case INT_TBL_DAT_BUT_SOLTRAINV:
                                cargarVentanaSolTraInv(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_SOLTRAINV).toString(), 
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_SOLTRAINV).toString(),
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC_SOLTRAINV).toString(), 
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC_SOLTRAINV).toString() );
                                break;
                            case INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV:
                                cargarVentanaObsAut(tblDat.getSelectedRow(), INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV );
                                break;
                            case INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD:
                                cargarVentanaDetalleOrdDesPedOtrBod(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_SOLTRAINV).toString(), 
                                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_SOLTRAINV).toString(),
                                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC_SOLTRAINV).toString(), 
                                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC_SOLTRAINV).toString() );
                                break;
                            case INT_TBL_DAT_BUT_FACVEN:
                                cargarVentanaFactura(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_FACVEN).toString(), 
                                                     tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_FACVEN).toString(),
                                                     tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC_FACVEN).toString(), 
                                                     tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC_FACVEN).toString(), intCodFacVen);
                                break;
                            case INT_TBL_DAT_BUT_ORDDESCLI:
                                cargarVentanaOrdDesCli(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_ORDDESCLI).toString(), 
                                                             tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_ORDDESCLI).toString(),
                                                             tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC_ORDDESCLI).toString(), 
                                                             tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC_ORDDESCLI).toString() );
                                break;
                        }
                    }
                }
            });
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
      
    //<editor-fold defaultstate="collapsed" desc="/* Botón Cotización */">
    private void cargarVentanaCotizacion(String strCodEmp, String strCodLoc,  String strCodCot)
    {
        Ventas.ZafVen01.ZafVen01 obj = new Ventas.ZafVen01.ZafVen01(objParSis, strCodEmp, strCodLoc, strCodCot);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Solicitud de Transferencia de Inventario */">
    private void cargarVentanaSolTraInv(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        Compras.ZafCom91.ZafCom91 obj = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Observacion Autorizacion Solicitud */">
    private void cargarVentanaObsAut( int intFil, int intCol)
    {
        String strObs = (tblDat.getValueAt(intFil, intCol) == null ? "" : tblDat.getValueAt(intFil, intCol).toString());
        Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs obj = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs(JOptionPane.getFrameForComponent(this), true, strObs);
        obj.show();
        if (obj.getAceptar()) 
        {
            tblDat.setValueAt(obj.getObser(), intFil, intCol);
        }
        obj = null;
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="/* Botón Orden de Despacho (Pedidos a Otras Bodegas) */">
    private void cargarVentanaOrdDesPedOtrBod(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Detalle Orden de Despacho (Pedidos a Otras Bodegas) */">
    private void cargarVentanaDetalleOrdDesPedOtrBod(/*String strCodSeg, */String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        Compras.ZafCom85.ZafCom85_01 obj = new Compras.ZafCom85.ZafCom85_01(objParSis,/*strCodSeg,*/ strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Factura de Ventas */">
    private void cargarVentanaFactura(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc, int intCodMnu)
    {
        Ventas.ZafVen02.ZafVen02 obj = new Ventas.ZafVen02.ZafVen02(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, intCodMnu) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Orden de Despacho (Clientes) */">
    private void cargarVentanaOrdDesCli(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        int intCodMnu=3497;
        //Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        Compras.ZafCom23.ZafCom23 obj = new Compras.ZafCom23.ZafCom23(objParSis, intCodMnu, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
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
    
    private boolean configurarComboEstReg() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("Y");
            vecEstReg.add("D");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("N: Pendientes de Autorización");
            cboEstReg.addItem("A: Autorizadas");
            cboEstReg.addItem("Y: Preautorizadas");
            cboEstReg.addItem("D: Denegadas");
            cboEstReg.addItem("I: Anuladas");
            cboEstReg.setSelectedIndex(0); 
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
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
            tblBodOri.setModel(objTblModBodOri);
            //Configurar JTable: Establecer tipo de selección.
            tblBodOri.setRowSelectionAllowed(true);
            tblBodOri.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBodOri);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBodOri.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBodOri.getColumnModel();
            tcmAux.getColumn(INT_TBL_BODORI_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODORI_CODBOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BODORI_NOMBOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BODORI_CHKBOD).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBodOri.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBodOri.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBodOri());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBodOri.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
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
            objTblFilCab=new ZafTblFilCab(tblBodOri);
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
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBodOri);
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
     * Esta función configura el JTable "tblBodDes".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBodDes()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BODDES_LIN,"");
            vecCab.add(INT_TBL_BODDES_CHKBOD,"");
            vecCab.add(INT_TBL_BODDES_CODBOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BODDES_NOMBOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBodDes=new ZafTblMod();
            objTblModBodDes.setHeader(vecCab);
            tblBodDes.setModel(objTblModBodDes);
            //Configurar JTable: Establecer tipo de selección.
            tblBodDes.setRowSelectionAllowed(true);
            tblBodDes.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBodDes);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBodDes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBodDes.getColumnModel();
            tcmAux.getColumn(INT_TBL_BODDES_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODDES_CHKBOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BODDES_CODBOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BODDES_NOMBOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BODDES_CHKBOD).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBodDes.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBodDes.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBodDes());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBodDes.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodDesMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BODDES_CHKBOD);
            objTblModBodDes.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            //objTblEdi=new ZafTblEdi(tblBodDes);
            //Configurar JTable: Editor de búsqueda.
            //objTblBus=new ZafTblBus(tblBodDes);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBodDes);
            tcmAux.getColumn(INT_TBL_BODDES_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BODDES_CHKBOD).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BODDES_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBodDes);
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
                tblBodOri.setModel(objTblModBodOri);
                vecDat.clear();
                blnMarTodChkTblBodOri=false;
            }
        }
        catch (java.sql.SQLException e)  {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    private boolean cargarBodDes()
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
                    //Armar la sentencia SQL.
                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL="";
                        strSQL+=" SELECT a1.co_bodGrp as co_bod, a2.tx_nom ";
                        strSQL+=" FROM tbr_bodlocprgusr as a ";
                        strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                        strSQL+=" INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                        strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu(); 
                        strSQL+=" AND a.co_usr="+ objParSis.getCodigoUsuario();
                        strSQL+=" AND a.tx_natBod in ('I', 'A') ";
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
                        strSQL+=" AND a.tx_natBod in ('I', 'A') ";
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
                    vecReg.add(INT_TBL_BODDES_LIN,"");
                    vecReg.add(INT_TBL_BODDES_CHKBOD,true);
                    vecReg.add(INT_TBL_BODDES_CODBOD, rst.getString("co_bod") );
                    vecReg.add(INT_TBL_BODDES_NOMBOD, rst.getString("tx_nom") );
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBodDes.setData(vecDat);
                tblBodDes.setModel(objTblModBodDes);
                vecDat.clear();
                blnMarTodChkTblBodDes=false;
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
            if (evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblBodOri.columnAtPoint(evt.getPoint())==INT_TBL_BODORI_CHKBOD)
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
    

    private void tblBodDesMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBodDes.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblBodDes.columnAtPoint(evt.getPoint())==INT_TBL_BODDES_CHKBOD)
            {
                if (blnMarTodChkTblBodDes)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodDes.setChecked(true, i, INT_TBL_BODDES_CHKBOD);
                    }
                    blnMarTodChkTblBodDes=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBodDes.setChecked(false, i, INT_TBL_BODDES_CHKBOD);
                    }
                    blnMarTodChkTblBodDes=true;
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
     * Esta función obtiene el query de los Tipos de Documentos 
     * configurados de acuerdo al usuario.
     */
    private String obtenerQueryTipoDocumento(int intTipo)
    {
        String strTipDoc="";
        try
        {
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
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
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc ";
                if(intTipo!=1)
                  strSQL+="  ,a1.tx_desCor, a1.tx_desLar ";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            strTipDoc=strSQL;
            System.out.println("strTipDoc: "+strTipDoc);
        }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);   }
        return strTipDoc;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        int intTipLoc=2; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.co_emp");
            arlCam.add("a1.Emp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Loc.");
            arlAli.add("Local");
            arlAli.add("Cód.Emp.");
            arlAli.add("Empresa");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("374");
            arlAncCol.add("50");
            arlAncCol.add("374");
            
            //Armar la sentencia SQL.
            strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) 
            {
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) //Grupo
                {
                    strSQL = objLocPrgUsr.cargarLocGrp(intTipLoc, objParSis.getCodigoEmpresaGrupo());
                }
                else 
                {
                    strSQL = objLocPrgUsr.cargarLoc(intTipLoc);
                }
            }
            else
            {
                strSQL = objLocPrgUsr.cargarLocUsr(intTipLoc);
            }
            System.out.println("ConfigurarVenConLoc: "+strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=3;  //Código Empresa
            intColOcu[1]=4;  //Nombre Empresa
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Locales", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){  blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Destinatarios".
     */
    private boolean configurarVenConDes()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            
            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
            {
                //Armar la sentencia SQL.
                if (objParSis.getCodigoUsuario() == 1)
                {
                    strSQL ="";
                    strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom ";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    if(txtCodEmpLoc.getText().length()>0)
                        strSQL+=" WHERE a1.co_emp="+txtCodEmpLoc.getText()+ " AND a2.co_loc="+txtCodLoc.getText();
                    else
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg='A' ";
                    strSQL+=" AND a1.st_cli='S'";
                    strSQL+=" GROUP BY a1.co_cli, a1.tx_ide, a1.tx_nom ";
                    strSQL+=" ORDER BY a1.tx_nom";
                } 
                else 
                {
                    strSQL ="";
                    strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom ";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN)
                    {     
                        if(txtCodEmpLoc.getText().length()>0)
                            strSQL+=" WHERE a1.co_emp="+txtCodEmpLoc.getText()+ " AND a2.co_loc="+txtCodLoc.getText();
                        else
                        {
                            strSQL+=" INNER JOIN  ";
                            strSQL+=" (  ";
                            strSQL+="      SELECT a2.co_emp as EmpAcc, a2.co_loc as LocAcc, a.co_emp, a.co_loc  FROM tbm_loc as a    ";
                            strSQL+="      INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp)   ";
                            strSQL+="      INNER JOIN tbr_locPrgUsr as a2 ON (a.co_Emp=a2.co_empRel AND a.co_loc=a2.co_locRel)   ";
                            strSQL+="      WHERE a2.co_emp="+objParSis.getCodigoEmpresa();
                            strSQL+="      AND a2.co_loc="+objParSis.getCodigoLocal();
                            strSQL+="      AND a2.co_mnu="+objParSis.getCodigoMenu();
                            strSQL+="      AND a2.co_usr="+objParSis.getCodigoUsuario();
                            strSQL+="      ORDER BY a.co_emp, a.co_loc   ";
                            strSQL+=" ) as b ON (a1.co_emp=b.co_emp and a2.co_loc=b.co_loc)";
                        }
                    }
                    else if (objParSis.getCodigoMenu()==INT_COD_MNU_INV)
                    {
                        strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresa();
                        //strSQL+=" AND a2.co_loc="+objParSis.getCodigoLocal();
                    }
                        
                    strSQL+=" AND a1.st_reg='A' ";
                    strSQL+=" AND a1.st_cli='S'";
                    strSQL+=" GROUP BY a1.co_cli, a1.tx_ide, a1.tx_nom ";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
            }
            else 
            {
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1)
                {
                    strSQL ="";
                    strSQL+=" SELECT '' as co_cli, c.* ";
                    strSQL+=" FROM ";
                    strSQL+=" ( ";
                    strSQL+="   SELECT a1.tx_ide, a1.tx_nom ";
                    strSQL+="   FROM tbm_cli AS a1  ";
                    strSQL+="   INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  ";
                    if(txtCodEmpLoc.getText().length()>0)
                        strSQL+=" WHERE a1.co_emp="+txtCodEmpLoc.getText()+ " AND a2.co_loc="+txtCodLoc.getText();
                    strSQL+="   AND a1.st_reg='A' AND a1.st_cli='S'  ";
                    strSQL+="   GROUP BY  a1.tx_ide, a1.tx_nom ";
                    strSQL+="   ORDER BY a1.tx_nom ";
                    strSQL+="   ) as c ";
                }
                else
                {
                    strSQL ="";
                    strSQL+=" SELECT '' as co_cli, c.* ";
                    strSQL+=" FROM ";
                    strSQL+=" ( ";
                    strSQL+="   SELECT a1.tx_ide, a1.tx_nom ";
                    strSQL+="   FROM tbm_cli AS a1  ";
                    strSQL+="   INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  ";
                    if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN)
                    { 
                        if(txtCodEmpLoc.getText().length()>0)
                            strSQL+=" WHERE a1.co_emp="+txtCodEmpLoc.getText()+ " AND a2.co_loc="+txtCodLoc.getText();
                        else
                        {
                            strSQL+="   INNER JOIN  ";
                            strSQL+="   (  ";
                            strSQL+="      SELECT a2.co_emp as EmpAcc, a2.co_loc as LocAcc, a.co_emp, a.co_loc  FROM tbm_loc as a    ";
                            strSQL+="      INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp)   ";
                            strSQL+="      INNER JOIN tbr_locPrgUsr as a2 ON (a.co_Emp=a2.co_empRel AND a.co_loc=a2.co_locRel)   ";
                            strSQL+="      WHERE a2.co_emp="+objParSis.getCodigoEmpresa();
                            strSQL+="      AND a2.co_loc="+objParSis.getCodigoLocal();
                            strSQL+="      AND a2.co_mnu="+objParSis.getCodigoMenu();
                            strSQL+="      AND a2.co_usr="+objParSis.getCodigoUsuario();
                            strSQL+="      ORDER BY a.co_emp, a.co_loc   ";
                            strSQL+="   ) as b ON (a1.co_emp=b.co_emp and a2.co_loc=b.co_loc) ";
                        }
                    }
                    strSQL+="   AND a1.st_reg='A' AND a1.st_cli='S'  ";
                    strSQL+="   GROUP BY  a1.tx_ide, a1.tx_nom ";
                    strSQL+="   ORDER BY a1.tx_nom ";
                    strSQL+="   ) as c ";
                }
            }   
            //System.out.println("configurarVenConDes: "+strSQL);
            vcoDes=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de destinatarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDes.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)  {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
   
    
     /**
     * Esta funcion permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una busqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se está buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de busqueda a realizar.
     * @return true: Si no se presenta ningun problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoLoc.setCampoBusqueda(1);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                        txtCodDes.setText("");
                        txtIdeDes.setText("");
                        txtNomDes.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                        txtCodDes.setText("");
                        txtIdeDes.setText("");
                        txtNomDes.setText("");
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                            txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                            txtCodDes.setText("");
                            txtIdeDes.setText("");
                            txtNomDes.setText("");
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                        txtCodDes.setText("");
                        txtIdeDes.setText("");
                        txtNomDes.setText("");
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(1);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                            txtCodEmpLoc.setText(vcoLoc.getValueAt(3));
                            txtCodDes.setText("");
                            txtIdeDes.setText("");
                            txtNomDes.setText("");
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConDes(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDes.setCampoBusqueda(2);
                    vcoDes.setVisible(true);
                    if (vcoDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDes.setText(vcoDes.getValueAt(1));
                        txtIdeDes.setText(vcoDes.getValueAt(2));
                        txtNomDes.setText(vcoDes.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoDes.buscar("a1.co_cli", txtCodDes.getText()))
                    {
                        txtCodDes.setText(vcoDes.getValueAt(1));
                        txtIdeDes.setText(vcoDes.getValueAt(2));
                        txtNomDes.setText(vcoDes.getValueAt(3));
                    }
                    else
                    {
                        vcoDes.setCampoBusqueda(0);
                        vcoDes.setCriterio1(11);
                        vcoDes.cargarDatos();
                        vcoDes.setVisible(true);
                        if (vcoDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDes.setText(vcoDes.getValueAt(1));
                            txtIdeDes.setText(vcoDes.getValueAt(2));
                            txtNomDes.setText(vcoDes.getValueAt(3));
                        }
                        else
                        {
                            txtCodDes.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    if (vcoDes.buscar("a1.tx_ide", txtIdeDes.getText()))
                    {
                        txtCodDes.setText(vcoDes.getValueAt(1));
                        txtIdeDes.setText(vcoDes.getValueAt(2));
                        txtNomDes.setText(vcoDes.getValueAt(3));
                    }
                    else
                    {
                        vcoDes.setCampoBusqueda(1);
                        vcoDes.setCriterio1(11);
                        vcoDes.cargarDatos();
                        vcoDes.setVisible(true);
                        if (vcoDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDes.setText(vcoDes.getValueAt(1));
                            txtIdeDes.setText(vcoDes.getValueAt(2));
                            txtNomDes.setText(vcoDes.getValueAt(3));
                        }
                        else
                        {
                            txtIdeDes.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoDes.buscar("a1.tx_nom", txtNomDes.getText()))
                    {
                        txtCodDes.setText(vcoDes.getValueAt(1));
                        txtIdeDes.setText(vcoDes.getValueAt(2));
                        txtNomDes.setText(vcoDes.getValueAt(3));
                    }
                    else
                    {
                        vcoDes.setCampoBusqueda(2);
                        vcoDes.setCriterio1(11);
                        vcoDes.cargarDatos();
                        vcoDes.setVisible(true);
                        if (vcoDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDes.setText(vcoDes.getValueAt(1));
                            txtIdeDes.setText(vcoDes.getValueAt(2));
                            txtNomDes.setText(vcoDes.getValueAt(3));
                        }
                        else
                        {
                            txtNomDes.setText(strNomCli);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)   {   blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    

    /**
     * ToolTips de Jtable: Datos.
     */
     private class ZafMouMotAda extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
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
                case INT_TBL_DAT_CODEMP_COTVEN:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOC_COTVEN:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_NOMLOC_COTVEN:
                    strMsg="Nombre del Local";
                    break;
                case INT_TBL_DAT_FECDOC_COTVEN:
                    strMsg="Fecha de la cotización";
                    break;
                case INT_TBL_DAT_CODCOT_COTVEN:
                    strMsg="Número de la Cotización";
                    break;
                case INT_TBL_DAT_BUT_COTVEN:
                    strMsg="Muestra la Cotización de Venta";
                    break;
                case INT_TBL_DAT_CODEMP_SOLTRAINV:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOC_SOLTRAINV:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOC_SOLTRAINV:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_CODDOC_SOLTRAINV:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUMDOC_SOLTRAINV:
                    strMsg="Número de la solicitud";
                    break;
                 case INT_TBL_DAT_FECDOC_SOLTRAINV:
                    strMsg="Fecha de la solicitud";
                    break;
               case INT_TBL_DAT_CODDES_SOLTRAINV:
                    strMsg="Código del destinatario";
                    break;
                case INT_TBL_DAT_NOMDES_SOLTRAINV:
                    strMsg="Nombre del destinatario";
                    break;
                case INT_TBL_DAT_CODBODORI_SOLTRAINV:
                    strMsg="Código de la bodega origen";
                    break; 
                case INT_TBL_DAT_NOMBODORI_SOLTRAINV:
                    strMsg="Bodega Origen";
                    break;
                case INT_TBL_DAT_CODBODDES_SOLTRAINV:
                    strMsg="Código de la bodega destino";
                    break;
                case INT_TBL_DAT_NOMBODDES_SOLTRAINV:
                    strMsg="Bodega Destino";
                    break;
                case INT_TBL_DAT_PESKGR_SOLTRAINV:
                    strMsg="Peso total (Kgr)";
                    break;
                case INT_TBL_DAT_BUT_SOLTRAINV:
                    strMsg="Muestra la solicitud de transferencia de inventario";
                    break;
                case INT_TBL_DAT_CHKAUT_AUTSOLTRAINV:
                    strMsg="Autorizada";
                    break;                    
                case INT_TBL_DAT_CHKDEG_AUTSOLTRAINV:
                    strMsg="Denegada";
                    break;                
                case INT_TBL_DAT_CHKANU_AUTSOLTRAINV:
                    strMsg="Anulada por el sistema";
                    break;        
                case INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV:
                    strMsg="Preautorizada";
                    break;        
                case INT_TBL_DAT_FECAUT_AUTSOLTRAINV:
                    strMsg="Fecha de autorización";
                    break;  
                case INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV:
                    strMsg="Observacion Aut.";
                    break;                       
                case INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV:
                    strMsg="Observación de la autorización";
                    break;                    
                case INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD:
                    strMsg="Código de la empresa";
                    break;                
                case INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD:
                    strMsg="Muestra el detalle de los ítems(Pedidos a otras bodegas)";
                    break;    
                case INT_TBL_DAT_CODEMP_FACVEN:
                    strMsg="Código de la empresa";
                    break;        
                case INT_TBL_DAT_CODLOC_FACVEN:
                    strMsg="Código del local";
                    break;  
                case INT_TBL_DAT_CODTIPDOC_FACVEN:
                    strMsg="Código del tipo de documento";
                    break;        
                case INT_TBL_DAT_DESCORTIPDOC_FACVEN:
                    strMsg="Descripción corta del tipo de documento";
                    break;  
                case INT_TBL_DAT_DESLARTIPDOC_FACVEN:
                    strMsg="Descripción larga del tipo de documento";
                    break;        
                case INT_TBL_DAT_CODDOC_FACVEN:
                    strMsg="Código del documento";
                    break;  
                case INT_TBL_DAT_NUMDOC_FACVEN:
                    strMsg="Número de la factura de venta";
                    break; 
                case INT_TBL_DAT_BUT_FACVEN:
                    strMsg="Muestra la Factura de venta";
                    break; 
                case INT_TBL_DAT_CODEMP_ORDDESCLI:
                    strMsg="Código de la empresa";
                    break; 
                case INT_TBL_DAT_CODLOC_ORDDESCLI:
                    strMsg="Código del local";
                    break; 
                case INT_TBL_DAT_CODTIPDOC_ORDDESCLI:
                    strMsg="Código del tipo de documento";
                    break; 
                case INT_TBL_DAT_DESCORTIPDOC_ORDDESCLI:
                    strMsg="Descripción corta del tipo de documento";
                    break; 
                case INT_TBL_DAT_DESLARTIPDOC_ORDDESCLI:
                    strMsg="Descripción larga del tipo de documento";
                    break; 
                case INT_TBL_DAT_CODDOC_ORDDESCLI:
                    strMsg="Código del documento";
                    break; 
                case INT_TBL_DAT_NUMDOC_ORDDESCLI:
                    strMsg="Número de la orden de despacho (Cliente)";
                    break; 
                case INT_TBL_DAT_BUT_ORDDESCLI:
                    strMsg="Muestra orden de despacho (Cliente)";
                    break; 
                case INT_TBL_DAT_CHK_PROCOM:
                    strMsg="Proceso Completo";
                    break; 
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
            int intCol=tblBodOri.columnAtPoint(evt.getPoint());
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
            tblBodOri.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private class ZafMouMotAdaBodDes extends java.awt.event.MouseMotionAdapter
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBodDes.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BODDES_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BODDES_CHKBOD:
                    strMsg="";
                    break;
                case INT_TBL_BODDES_CODBOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BODDES_NOMBOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBodDes.getTableHeader().setToolTipText(strMsg);
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
        panBodOri = new javax.swing.JPanel();
        spnBodOri = new javax.swing.JScrollPane();
        tblBodOri = new javax.swing.JTable();
        panBodDes = new javax.swing.JPanel();
        spnBodDes = new javax.swing.JScrollPane();
        tblBodDes = new javax.swing.JTable();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodEmpLoc = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblDes = new javax.swing.JLabel();
        txtCodDes = new javax.swing.JTextField();
        txtIdeDes = new javax.swing.JTextField();
        txtNomDes = new javax.swing.JTextField();
        butDes = new javax.swing.JButton();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        panSolTraDesHas = new javax.swing.JPanel();
        lblSolTraDes = new javax.swing.JLabel();
        txtSolTraDes = new javax.swing.JTextField();
        lblSolTraHas = new javax.swing.JLabel();
        txtSolTraHas = new javax.swing.JTextField();
        panCotVenDesHas = new javax.swing.JPanel();
        lblCotVenDes = new javax.swing.JLabel();
        txtCotVenDes = new javax.swing.JTextField();
        lblCotVenHas = new javax.swing.JLabel();
        txtCotVenHas = new javax.swing.JTextField();
        chkSolTraProPen = new javax.swing.JCheckBox();
        chkSolTraProCom = new javax.swing.JCheckBox();
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

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        spnFil.setPreferredSize(new java.awt.Dimension(0, 375));

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(10, 475));
        panFil.setLayout(null);

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

        panFil.add(panBodOri);
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

        panFil.add(panBodDes);
        panBodDes.setBounds(347, 94, 310, 100);

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
        panFil.add(optTod);
        optTod.setBounds(0, 200, 400, 20);

        optFil.setSelected(true);
        optFil.setText("Sólo los  documentos que cumplan el criterio seleccionado");
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
        panFil.add(optFil);
        optFil.setBounds(0, 220, 400, 20);
        optFil.getAccessibleContext().setAccessibleParent(spnFil);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Vendedor/Comprador");
        panFil.add(lblLoc);
        lblLoc.setBounds(15, 250, 100, 20);
        panFil.add(txtCodEmpLoc);
        txtCodEmpLoc.setBounds(119, 250, 32, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(150, 250, 94, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(244, 250, 396, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(640, 250, 20, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(15, 270, 130, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(119, 270, 32, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(150, 270, 94, 20);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(244, 270, 396, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(640, 270, 20, 20);

        lblDes.setText("Destinatario:");
        lblDes.setToolTipText("Beneficiario");
        panFil.add(lblDes);
        lblDes.setBounds(15, 290, 130, 20);

        txtCodDes.setEditable(false);
        txtCodDes.setBackground(new java.awt.Color(255, 255, 255));
        txtCodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDesFocusLost(evt);
            }
        });
        txtCodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDesActionPerformed(evt);
            }
        });
        panFil.add(txtCodDes);
        txtCodDes.setBounds(150, 290, 60, 20);

        txtIdeDes.setEditable(false);
        txtIdeDes.setBackground(new java.awt.Color(255, 255, 255));
        txtIdeDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeDesFocusLost(evt);
            }
        });
        txtIdeDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeDesActionPerformed(evt);
            }
        });
        panFil.add(txtIdeDes);
        txtIdeDes.setBounds(210, 290, 109, 20);

        txtNomDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDesFocusLost(evt);
            }
        });
        txtNomDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDesActionPerformed(evt);
            }
        });
        panFil.add(txtNomDes);
        txtNomDes.setBounds(319, 290, 321, 20);

        butDes.setText("...");
        butDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDesActionPerformed(evt);
            }
        });
        panFil.add(butDes);
        butDes.setBounds(640, 290, 20, 20);

        lblEstReg.setText("Estado de autorizacion de  la solicitud:");
        lblEstReg.setToolTipText("Estado del registro:");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(17, 430, 250, 16);

        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });
        panFil.add(cboEstReg);
        cboEstReg.setBounds(260, 430, 210, 20);

        panSolTraDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Solicitud de Transferencia"));
        panSolTraDesHas.setLayout(null);

        lblSolTraDes.setText("Desde:");
        panSolTraDesHas.add(lblSolTraDes);
        lblSolTraDes.setBounds(15, 20, 60, 16);

        txtSolTraDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraDesFocusLost(evt);
            }
        });
        panSolTraDesHas.add(txtSolTraDes);
        txtSolTraDes.setBounds(65, 20, 100, 20);

        lblSolTraHas.setText("Hasta:");
        panSolTraDesHas.add(lblSolTraHas);
        lblSolTraHas.setBounds(172, 20, 50, 16);

        txtSolTraHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusLost(evt);
            }
        });
        panSolTraDesHas.add(txtSolTraHas);
        txtSolTraHas.setBounds(217, 20, 100, 20);

        panFil.add(panSolTraDesHas);
        panSolTraDesHas.setBounds(15, 320, 325, 48);

        panCotVenDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Cotización"));
        panCotVenDesHas.setLayout(null);

        lblCotVenDes.setText("Desde:");
        panCotVenDesHas.add(lblCotVenDes);
        lblCotVenDes.setBounds(15, 20, 60, 16);

        txtCotVenDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCotVenDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCotVenDesFocusLost(evt);
            }
        });
        panCotVenDesHas.add(txtCotVenDes);
        txtCotVenDes.setBounds(60, 20, 100, 20);

        lblCotVenHas.setText("Hasta:");
        panCotVenDesHas.add(lblCotVenHas);
        lblCotVenHas.setBounds(172, 20, 50, 16);

        txtCotVenHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCotVenHasFocusGained(evt);
            }
        });
        panCotVenDesHas.add(txtCotVenHas);
        txtCotVenHas.setBounds(215, 20, 100, 20);

        panFil.add(panCotVenDesHas);
        panCotVenDesHas.setBounds(338, 320, 322, 48);

        chkSolTraProPen.setSelected(true);
        chkSolTraProPen.setText("Mostrar las solicitudes que tienen procesos pendientes.");
        panFil.add(chkSolTraProPen);
        chkSolTraProPen.setBounds(15, 380, 400, 20);

        chkSolTraProCom.setText("Mostrar las solicitudes que tienen el proceso completo.");
        panFil.add(chkSolTraProCom);
        chkSolTraProCom.setBounds(15, 400, 400, 20);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
//        try
//        {
//            if (con != null) 
//            {
//                rst.close();
//                stm.close();
//                con.close();
//                rst=null;
//                stm=null;
//                con=null;
//                System.out.println("Cerrada Conexion");
//                exitForm(null);
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtCodEmpLoc.setText("");
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodDes.setText("");
            txtIdeDes.setText("");
            txtNomDes.setText("");
            txtSolTraDes.setText("");
            txtSolTraHas.setText("");
            txtCotVenDes.setText("");
            txtCotVenHas.setText("");
            chkSolTraProPen.setSelected(false);
            chkSolTraProCom.setSelected(false);
            cboEstReg.setSelectedIndex(0);
            
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        if (cboEstReg.getSelectedIndex() > 0) 
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodActionPerformed

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

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butTipDocActionPerformed

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

    private void txtCodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDesFocusGained
        strCodCli=txtCodDes.getText();
        txtCodDes.selectAll();
    }//GEN-LAST:event_txtCodDesFocusGained

    private void txtCodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDesFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodDes.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodDes.getText().equals(""))
            {
                txtCodDes.setText("");
                txtIdeDes.setText("");
                txtNomDes.setText("");
            }
            else
            {
                configurarVenConDes();
                mostrarVenConDes(1);
            }
        }
        else
        txtCodDes.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodDes.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodDesFocusLost

    private void txtCodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDesActionPerformed
        txtCodDes.transferFocus();
    }//GEN-LAST:event_txtCodDesActionPerformed

    private void txtNomDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusGained
        strNomCli=txtNomDes.getText();
        txtNomDes.selectAll();
    }//GEN-LAST:event_txtNomDesFocusGained

    private void txtNomDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDesFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomDes.getText().equalsIgnoreCase(strNomCli))
        {
            if (txtNomDes.getText().equals(""))
            {
                txtCodDes.setText("");
                txtIdeDes.setText("");
                txtNomDes.setText("");
            }
            else
            {
                configurarVenConDes();
                mostrarVenConDes(3);
            }
        }
        else
           txtNomDes.setText(strNomCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomDes.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomDesFocusLost

    private void txtNomDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDesActionPerformed
        txtNomDes.transferFocus();
    }//GEN-LAST:event_txtNomDesActionPerformed

    private void butDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDesActionPerformed
        configurarVenConDes();
        mostrarVenConDes(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodDes.getText().length()>0 || txtIdeDes.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butDesActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodVen=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodVen)) 
        {
            if (txtCodLoc.getText().equals(""))
            {
                txtCodEmpLoc.setText("");
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } 
            else
            {
                mostrarVenConLoc(1);
            }
        } 
        else
        {
            txtCodLoc.setText(strCodVen);
        }
        
        if (txtCodLoc.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomVen=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomVen)) 
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodEmpLoc.setText("");
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } 
            else
            {
                mostrarVenConLoc(2);
            }
        } 
        else
        {
            txtNomLoc.setText(strNomVen);
        }
        
        if (txtNomLoc.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }  
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butLocActionPerformed

    private void txtSolTraDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusGained
        txtSolTraDes.selectAll();
    }//GEN-LAST:event_txtSolTraDesFocusGained

    private void txtSolTraDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraDesFocusLost
        if (txtSolTraDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            if (txtSolTraHas.getText().length() == 0)
            {
                txtSolTraHas.setText(txtSolTraDes.getText());
            }
        }
    }//GEN-LAST:event_txtSolTraDesFocusLost

    private void txtSolTraHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusGained
        txtSolTraHas.selectAll();
    }//GEN-LAST:event_txtSolTraHasFocusGained

    private void txtSolTraHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSolTraHasFocusLost
        if (txtSolTraHas.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtSolTraHasFocusLost

    private void txtIdeDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeDesFocusGained
        strIdeCli = txtIdeDes.getText();
        txtIdeDes.selectAll();
    }//GEN-LAST:event_txtIdeDesFocusGained

    private void txtIdeDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeDesFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtIdeDes.getText().equalsIgnoreCase(strIdeCli))
        {
            if (txtIdeDes.getText().equals(""))
            {
                txtCodDes.setText("");
                txtIdeDes.setText("");
                txtNomDes.setText("");
            }
            else
            {
                configurarVenConDes();
                mostrarVenConDes(2);
            }
        }
        else
           txtIdeDes.setText(strIdeCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtIdeDes.getText().length()>0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtIdeDesFocusLost

    private void txtIdeDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeDesActionPerformed
        txtIdeDes.transferFocus();
    }//GEN-LAST:event_txtIdeDesActionPerformed

    private void txtCotVenDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotVenDesFocusGained
         txtCotVenDes.selectAll();
    }//GEN-LAST:event_txtCotVenDesFocusGained

    private void txtCotVenDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotVenDesFocusLost
        if (txtCotVenDes.getText().length() > 0) 
        {
            optFil.setSelected(true);
            if (txtCotVenHas.getText().length() == 0) 
                txtCotVenHas.setText(txtCotVenDes.getText());
        }
    }//GEN-LAST:event_txtCotVenDesFocusLost

    private void txtCotVenHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCotVenHasFocusGained
        txtCotVenHas.selectAll();
    }//GEN-LAST:event_txtCotVenHasFocusGained

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilStateChanged
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDes;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JCheckBox chkSolTraProCom;
    private javax.swing.JCheckBox chkSolTraProPen;
    private javax.swing.JLabel lblCotVenDes;
    private javax.swing.JLabel lblCotVenHas;
    private javax.swing.JLabel lblDes;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblSolTraDes;
    private javax.swing.JLabel lblSolTraHas;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBodDes;
    private javax.swing.JPanel panBodOri;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCotVenDesHas;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panSolTraDesHas;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBodDes;
    private javax.swing.JScrollPane spnBodOri;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBodDes;
    private javax.swing.JTable tblBodOri;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDes;
    private javax.swing.JTextField txtCodEmpLoc;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCotVenDes;
    private javax.swing.JTextField txtCotVenHas;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtIdeDes;
    private javax.swing.JTextField txtNomDes;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtSolTraDes;
    private javax.swing.JTextField txtSolTraHas;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
    
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
        int intNumFilTblBodOri, intNumFilTblBodDes, i, j;
        if(objParSis.getCodigoMenu()!=INT_COD_MNU_VEN)
        {
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
                tblBodOri.requestFocus();
                return false;
            }


            //Validar que esté seleccionado al menos una bodega (Destino).
            intNumFilTblBodDes=objTblModBodDes.getRowCountTrue();
            i=0;
            for (j=0; j<intNumFilTblBodDes; j++)
            {
                if (objTblModBodDes.isChecked(j, INT_TBL_BODDES_CHKBOD))
                {
                    i++;
                    break;
                }
            }
            if (i==0)
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe seleccionar al menos una bodega Destino.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
                tblBodDes.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    
    private String sqlConFil() 
    {
        String sqlFil="";
        String strFec="", strAuxMnu="", strFecDoc="", strTipDoc="", strNumCot="";
        String strBodOrg="", strBodDes="", strAuxEstConInv="";
        int intTipLoc=1, intTipDoc=1;
        boolean blnBodOrg=false, blnBodDes=false;
        strFilFecCot="";
        strFilSolTipDoc=" ";
        
        //Filtro: Configuraciones de Menú.
        if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN)
        {
            strAuxMnu="Cot";
            strFec="Cot.fe_Cot";
        }
        else if (objParSis.getCodigoMenu()==INT_COD_MNU_INV)
        {
            strAuxMnu="Sol";
            strFec="Sol.fe_doc";
        }
        
        //Tipos de Documentos
        if (txtCodTipDoc.getText().length() > 0) {
            strTipDoc=" WHERE Sol.co_tipDoc = " + txtCodTipDoc.getText().replaceAll("'", "''") + " ";
        }  
        else
        {
            strTipDoc=" WHERE Sol.co_tipDoc in ( " + obtenerQueryTipoDocumento(intTipDoc) + ") ";
        }
        sqlFil+=strTipDoc;
        strFilSolTipDoc=" "+strTipDoc;
        
        
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Fecha del documento. */">
        if(objSelFec.isCheckBoxChecked() )
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    strFecDoc= " "+strFec+" BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strFecDoc= " "+strFec+" <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strFecDoc= " "+strFec+" >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
            sqlFil+=" AND "+strFecDoc;
            if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN)
               strFilFecCot=" WHERE "+strFecDoc;
        }

        //</editor-fold>
        
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Cotización. */">
        if(txtCotVenDes.getText().length()>0 && txtCotVenHas.getText().length() > 0)
        {
            strNumCot=" Cot.co_cot BETWEEN "+objUti.codificar(txtCotVenDes.getText())+" AND "+objUti.codificar(txtCotVenHas.getText());//JM 
            sqlFil+=" AND "+strNumCot;
            if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN)
            {
                if(strFilFecCot.equalsIgnoreCase(""))
                {
                    strFilFecCot+="WHERE "+strNumCot;
                }
                else
                {
                    strFilFecCot+="AND "+strNumCot;
                }
            }
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Origen. */">
        for (int j = 0; j < tblBodOri.getRowCount(); j++) 
        {
            if (tblBodOri.getValueAt(j, INT_TBL_BODORI_CHKBOD).toString().equals("true")) 
            {
                if(!blnBodOrg) //Si es Primera vez que ingresa.
                {
                    strBodOrg+=" AND Sol.co_bodOrg in ( ";
                }
                else
                {
                    strBodOrg+=", ";
                }
                strBodOrg+= tblBodOri.getValueAt(j, INT_TBL_BODORI_CODBOD).toString() ;
                blnBodOrg=true;
            }
        }
        if (blnBodOrg)
        {
            strBodOrg+= " ) ";
            sqlFil+= strBodOrg;
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="/* Filtro: Bodega Destino. */">
        for (int j = 0; j < tblBodDes.getRowCount(); j++) 
        {
            if (tblBodDes.getValueAt(j, INT_TBL_BODDES_CHKBOD).toString().equals("true")) 
            {
                if(!blnBodDes) //Si es Primera vez que ingresa.
                {
                    strBodDes+=" AND Sol.co_bodDes in ( ";
                }
                else
                {
                    strBodDes+=", ";
                }
                strBodDes+= tblBodDes.getValueAt(j, INT_TBL_BODDES_CODBOD).toString() ;
                blnBodDes=true;
            }
        }
        if (blnBodDes)
        {
            strBodDes+= " ) ";
            sqlFil+= strBodDes;
        }
        //</editor-fold>
        
            
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Solicitud. */">
        if (txtSolTraDes.getText().length()>0 && txtSolTraHas.getText().length()>0)
        {
            sqlFil+="  AND Sol.ne_numDoc BETWEEN "+objUti.codificar(txtSolTraDes.getText())+" AND "+objUti.codificar(txtSolTraHas.getText());
        }
        //</editor-fold>

    
        //Otros Filtros
        if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN )
        {
            if (txtCodLoc.getText().length() > 0) {
                sqlFil+=" AND "+strAuxMnu+".co_emp = " + txtCodEmpLoc.getText().replaceAll("'", "''") + "  AND "+strAuxMnu+".co_loc = " + txtCodLoc.getText().replaceAll("'", "''") + "";
            }   
            else 
            {
                if (objParSis.getCodigoUsuario() != 1)
                {
                    //Valida si el usuario tiene acceso a locales.
                    if ((objLocPrgUsr.validaLocUsr())) 
                    {
                        sqlFil+=" AND "+strAuxMnu+".co_emp = " + objParSis.getCodigoEmpresa()+ "  AND "+strAuxMnu+".co_loc in ( "+objParSis.getCodigoLocal()+" )";
                       // sqlFil+=" AND a1.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                    }
                    else 
                    {
                        sqlFil+=" AND "+strAuxMnu+".co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                    }
                }
            }
        }
        
        
        
        //Destinatario
        if (txtIdeDes.getText().length() > 0) 
        {
            if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) 
            {
                sqlFil+=" AND trim(NomCli) like '%" + txtNomDes.getText() + "%'";
            }
            else
            {
                //sqlFil+=" AND tx_ide like '" + txtIdeDes.getText().trim() + "'";
                sqlFil+=" AND co_cli = '" + txtCodDes.getText().trim() + "'";
            }
        }
        
        if (cboEstReg.getSelectedIndex() > 1) 
        {
            sqlFil += " AND Sol.EstAut = '" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
        }
        else if (cboEstReg.getSelectedIndex() == 1) 
        {
            sqlFil += " AND Sol.EstAut IS NULL" ;
        }
  
        
        strAuxEstConInv="";
        //Solicitudes Proceso Pendiente.
        if (chkSolTraProPen.isSelected()) 
        {
            if (strAuxEstConInv.equals("")) {
                strAuxEstConInv += "'P', 'E'";
            } else {
                strAuxEstConInv += ",'P', 'E'";
            }
        }
        //Solicitudes Proceso Completo
        if (chkSolTraProCom.isSelected()) 
        {
            if (strAuxEstConInv.equals("")) {
                strAuxEstConInv += "'C', 'N'";
            } else {
                strAuxEstConInv += ",'C', 'N'";
            }
        }
        //Filtro
        if (!strAuxEstConInv.equals("")) 
        {  
            if (chkSolTraProPen.isSelected()) 
            {
                sqlFil+= " AND ( Sol.st_conInv IN (" + strAuxEstConInv + ") OR Sol.st_conInv IS NULL )";
            }
            else
            {
                sqlFil+= " AND Sol.st_conInv IN (" + strAuxEstConInv + ") ";
            }
        }    
        
        //System.out.println("Filtro: "+sqlFil);
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
        String strAuxAut="", strConInv="";
        
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
                strSQL="";                
                strSQL+=" SELECT Seg.co_Seg, Cot.co_emp as CodEmpCot, Cot.co_Loc as CodLocCot, cot.tx_nom as NomLocCot, Cot.fe_cot, Cot.co_Cot, \n";        
                strSQL+="        Sol.co_emp as CodEmpSol, Sol.co_loc as CodLocSol, Sol.co_tipDoc as CodTipDocSol, \n";    
                strSQL+="        Sol.tx_DesCor as DesCorSol, Sol.tx_DesLar as DesLarSol, Sol.co_doc as CodDocSol, \n";          
                strSQL+="        Sol.ne_numDoc as NumSol, Sol.fe_doc as FecDocSol, \n";
                strSQL+="        CASE WHEN Cot.co_cli IS NOT NULL THEN Cot.co_cli ELSE Sol.CodCliDes END as co_cli, \n";
                strSQL+="        CASE WHEN Cot.NomCli IS NOT NULL THEN Cot.NomCli ELSE Sol.NomCliDes END as NomCli, \n";      
                strSQL+="        Sol.co_bodOrg, Sol.NomBodOrg, Sol.co_bodDes, Sol.NomBodDes, \n";
                strSQL+="        Sol.nd_pesTotKgr, Sol.EstAut, Sol.fe_Aut, Sol.tx_obsAut, Sol.st_ConInv, \n";
                strSQL+="        Fac.CodEmpFacVen, Fac.CodLocFacVen, Fac.CodTipDocFacVen, Fac.DesCorFacVen, \n";
                strSQL+="        Fac.DesLarFacVen, Fac.CodDocFacVen, Fac.NumDocFacVen, \n";
                strSQL+="        OrdCli.CodEmpOrdCli, OrdCli.CodLocOrdCli, OrdCli.CodTipDocOrdCli, OrdCli.DesCorOrdCli, \n";
                strSQL+="        OrdCli.DesLarOrdCli, OrdCli.CodDocOrdCli, OrdCli.ne_numOrdDes, \n";
                strSQL+="        CASE WHEN Sol.st_ConInv='N' THEN  'N' ELSE 'S' END as OrdPed \n";
                strSQL+=" FROM tbm_cabSegMovInv Seg \n";
                strSQL+=" LEFT OUTER JOIN \n";
                strSQL+=" ( \n";
                strSQL+="    SELECT a4.co_Seg, cot.co_emp, cot.co_Loc, a2.tx_nom, cot.fe_cot, cot.co_Cot, cot.co_cli, a3.tx_ide, a3.tx_nom as NomCli \n";
                strSQL+="    FROM tbm_cabCotVen as cot \n";  
                strSQL+="    INNER JOIN tbm_cabSegMovInv as a4 ON (a4.co_empRelCabCotVen=cot.co_emp AND a4.co_LocRelCabCotVen=cot.co_Loc AND a4.co_CotRelCabCotVen=cot.co_Cot) \n";
                strSQL+="    INNER JOIN tbm_cli as a3 ON (cot.co_emp=a3.co_emp AND cot.co_cli=a3.co_cli) \n";
                strSQL+="    INNER JOIN tbm_Loc as a2 ON (a2.co_emp=cot.co_Emp AND a2.co_Loc=cot.co_Loc) \n";
                strSQL+="    "+strFilFecCot+"\n";
                strSQL+=" ) as Cot ON (Cot.co_seg=Seg.co_seg ) \n";
                strSQL+=" LEFT OUTER JOIN  \n";
                strSQL+=" ( \n";   
                strSQL+="    SELECT a8.co_seg, sol.co_emp, sol.co_Loc, sol.co_TipDoc, a4.tx_desCor, a4.tx_desLar, sol.co_doc, sol.ne_numDoc, sol.fe_doc, \n";
                strSQL+="           a3.co_empDueBod as CodEmpDueBodDes, a6.co_cliEmpOrg as CodCliDes, a7.tx_ide as IdeCliDes, a5.tx_nom as NomCliDes, sol.co_bodOrg, a2.tx_nom as NomBodOrg, \n";
                strSQL+="           sol.co_bodDes, a3.tx_nom as NomBodDes, sol.nd_pesTotKgr, sol.st_Aut, sol.fe_aut, sol.tx_obsAut, sol.st_ConInv, \n";
                strSQL+="           CASE WHEN  a10.co_tipDoc IS NOT NULL THEN 'Y'  ELSE sol.st_Aut END as EstAut \n";
                strSQL+="    FROM tbm_cabSoltraInv as sol \n";   
                strSQL+="    LEFT OUTER JOIN tbm_cabSegMovInv as a8 \n";
                strSQL+="    ON (a8.co_empRelCabSolTraInv=sol.co_emp AND a8.co_LocRelCabSolTraInv=sol.co_Loc AND a8.co_tipDocRelCabSolTraInv=sol.co_tipDoc AND a8.co_docRelCabSolTraInv=sol.co_doc) \n";
                strSQL+="    INNER JOIN tbm_bod as a2 ON (a2.co_emp=sol.co_emp AND a2.co_bod=sol.co_bodOrg) \n";     
                strSQL+="    INNER JOIN tbm_bod as a3 ON (a3.co_emp=sol.co_emp AND a3.co_bod=sol.co_bodDes) \n";  
                strSQL+="    LEFT JOIN tbm_cfgEmpRel as a6 ON (a6.co_empOrg="+objParSis.getCodigoEmpresa()+" AND a6.co_EmpDes=a3.co_EmpDueBod) \n";
                strSQL+="    LEFT JOIN tbm_cli as a7 ON (a7.co_emp=a6.co_empOrg AND a7.co_cli=a6.co_cliEmpOrg) \n";   
                strSQL+="    LEFT JOIN tbm_emp as a5 ON (a5.co_emp=a3.co_empDueBod) \n";        
                strSQL+="    INNER JOIN tbm_CabTipDoc as a4 ON (a4.co_emp=sol.co_emp AND a4.co_loc=sol.co_loc AND a4.co_tipDoc=sol.co_tipDoc) \n";
                strSQL+="    LEFT JOIN \n";
                strSQL+="    ( \n";
                strSQL+="    	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_Doc, a1.ne_numDoc, a.tx_obs1 \n";	
                strSQL+="    	FROM tbm_cfgtipdocutiproaut as a \n";
                strSQL+="    	INNER JOIN tbm_cabSolTraInv as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc) \n";
                strSQL+="    	WHERE a.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND a.co_cfg in (2000, 2001) \n"; 
                strSQL+="    ) as a10 ON (a10.co_emp=sol.co_emp AND a10.co_loc=sol.co_loc AND a10.co_tiPDoc=sol.co_tipDoc AND a10.co_doc=sol.co_doc)  \n";
                strSQL+="    "+strFilSolTipDoc+" \n"; 
                strSQL+=" ) as Sol ON (Sol.co_seg=Seg.co_seg ) \n"; 
                strSQL+=" LEFT OUTER JOIN \n"; 
                strSQL+=" ( \n";
                strSQL+="     SELECT a3.co_seg, a1.ne_numCot, a1.co_emp as CodEmpFacVen, a1.co_loc as CodLocFacVen, a1.co_tipDoc as CodTipDocFacVen, a2.tx_DesCor as DesCorFacVen, \n";
                strSQL+="            a2.tx_DesLar as DesLarFacVen, a1.co_doc as CodDocFacVen, a1.ne_numDoc as NumDocFacVen  \n";
                strSQL+="     FROM  tbm_cabMovInv as a1   \n";            
                strSQL+="     INNER JOIN tbm_cabSegMovInv as a3  \n";
                strSQL+="     ON (a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_Doc=a3.co_docRelCabMovInv)  \n";
                strSQL+="     INNER JOIN tbm_cabCotVen as a5 ON(a5.co_emp=a1.co_Emp AND a5.co_loc=a1.co_loc AND a5.co_Cot=a1.ne_numCot) \n"; 
                strSQL+="     INNER JOIN tbm_cabTipDoc as a2 ON (a1.co_emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) \n";    
                strSQL+="     INNER JOIN tbm_cli as a4 ON (a4.co_emp=a1.co_emp AND a4.co_cli=a1.co_cli ) \n";          
                strSQL+="     WHERE a1.ne_numCot >0 AND a1.co_tipDoc=228 /*AND a4.co_empGrp IS NULL*/ \n";
                strSQL+=" ) as Fac ON (Fac.co_seg=Seg.co_seg) \n";
                strSQL+=" LEFT OUTER JOIN  \n";
                strSQL+=" ( \n";
                strSQL+="      SELECT a4.co_Seg, a.co_emp as CodEmpOrdCli, a.co_loc as CodLocOrdCli, a.co_tipDoc as CodTipDocOrdCli, a3.tx_DesCor as DesCorOrdCli,  \n";
                strSQL+="             a3.tx_DesLar as DesLarOrdCli, a.co_doc as CodDocOrdCli, a.ne_numOrdDes, a.fe_Doc, \n";    
                strSQL+="             a1.co_EmpRel as CodEmpFacVen, a1.co_locRel as CodLocFacVen, a1.co_tipDocRel as CodTipDocFacVen, a1.co_docRel as CodDocFacVen  \n";
                strSQL+="      FROM tbm_cabGuiRem as a   \n";
                strSQL+="      INNER JOIN tbm_detGuiRem as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_Doc)  \n";
                strSQL+="      INNER JOIN tbm_cabSegMovInv as a4  \n";
                strSQL+="      ON (a.co_emp=a4.co_empRelCabGuiRem AND a.co_loc=a4.co_locRelCabGuiRem AND a.co_tipDoc=a4.co_tipDocRelCabGuiRem AND a.co_doc=a4.co_docRelCabGuiRem )  \n";
                strSQL+="      INNER JOIN tbm_cabTipDoc as a3 ON (a.co_Emp=a3.co_emp AND a.co_loc=a3.co_loc AND a.co_tipDoc=a3.co_TipDoc)  \n";
                strSQL+="      WHERE a.ne_numOrdDes>0 \n";
                strSQL+="      GROUP BY a4.co_Seg, a.co_emp, a.co_loc, a.co_tipDoc, a3.tx_DesCor, a3.tx_DesLar, a.co_doc, a.ne_numOrdDes, a.fe_Doc,  \n";
                strSQL+="               a1.co_EmpRel, a1.co_locRel, a1.co_tipDocRel , a1.co_docRel  \n";
                strSQL+=" ) as OrdCli ON (OrdCli.co_Seg=Seg.co_Seg AND Fac.CodEmpFacVen=OrdCli.CodEmpFacVen AND Fac.CodLocFacVen=OrdCli.CodLocFacVen \n";
                strSQL+="                 AND Fac.CodTipDocFacVen=OrdCli.CodTipDocFacVen AND Fac.CodDocFacVen=OrdCli.CodDocFacVen ) \n";
                strSQL+=strFil+" \n";
                strSQL+=" GROUP BY Seg.co_Seg, Cot.co_emp, Cot.co_Loc, cot.tx_nom, Cot.fe_cot, Cot.co_Cot,  \n";        
                strSQL+="          Sol.co_emp, Sol.co_loc, Sol.co_tipDoc, Sol.tx_DesCor, Sol.tx_DesLar, Sol.co_doc,  \n";
                strSQL+="          Sol.ne_numDoc, Sol.fe_doc, Cot.co_cli, Sol.CodCliDes, Cot.NomCli, Sol.NomCliDes,  \n";
                strSQL+="          Sol.co_bodOrg, Sol.NomBodOrg, Sol.co_bodDes, Sol.NomBodDes,  \n";        
                strSQL+="          Sol.nd_pesTotKgr, Sol.EstAut, Sol.fe_Aut, Sol.tx_obsAut, Sol.st_ConInv,  \n";
                strSQL+=" 	   Fac.CodEmpFacVen, Fac.CodLocFacVen, Fac.CodTipDocFacVen, Fac.DesCorFacVen,  \n";         
                strSQL+="          Fac.DesLarFacVen, Fac.CodDocFacVen, Fac.NumDocFacVen,   \n";
                strSQL+="          OrdCli.CodEmpOrdCli, OrdCli.CodLocOrdCli, OrdCli.CodTipDocOrdCli, OrdCli.DesCorOrdCli,  \n";          
                strSQL+="          OrdCli.DesLarOrdCli, OrdCli.CodDocOrdCli, OrdCli.ne_numOrdDes \n";
                if(objParSis.getCodigoMenu()==INT_COD_MNU_VEN)
                   strSQL+="  ORDER BY Cot.co_emp, Cot.co_Loc, Cot.fe_cot, Cot.co_Cot, Sol.co_emp, Sol.co_loc, Sol.co_tipDoc,Sol.ne_numDoc \n";
                else if (objParSis.getCodigoMenu()==INT_COD_MNU_INV)
                    strSQL+="  ORDER BY Cot.co_emp, Cot.co_Loc, Cot.fe_cot, Cot.co_Cot, Sol.co_tipDoc, Sol.ne_numDoc \n";
                    
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
                        vecReg.add(INT_TBL_DAT_CODSEG,rst.getString("co_Seg")==null?"":rst.getString("co_Seg"));
                        vecReg.add(INT_TBL_DAT_CODEMP_COTVEN,rst.getString("CodEmpCot")==null?"":rst.getString("CodEmpCot"));
                        vecReg.add(INT_TBL_DAT_CODLOC_COTVEN,rst.getString("CodLocCot")==null?"":rst.getString("CodLocCot"));
                        vecReg.add(INT_TBL_DAT_NOMLOC_COTVEN,rst.getString("NomLocCot")==null?"":rst.getString("NomLocCot"));
                        vecReg.add(INT_TBL_DAT_FECDOC_COTVEN,rst.getString("fe_Cot")==null?"":rst.getString("fe_Cot"));
                        vecReg.add(INT_TBL_DAT_CODCOT_COTVEN,rst.getString("co_Cot")==null?"":rst.getString("co_Cot"));
                        vecReg.add(INT_TBL_DAT_BUT_COTVEN,"");
                        
                        vecReg.add(INT_TBL_DAT_CODEMP_SOLTRAINV,rst.getString("CodEmpSol")==null?"":rst.getString("CodEmpSol"));                        
                        vecReg.add(INT_TBL_DAT_CODLOC_SOLTRAINV,rst.getString("CodLocSol")==null?"":rst.getString("CodLocSol"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC_SOLTRAINV,rst.getString("CodTipDocSol")==null?"":rst.getString("CodTipDocSol"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV,rst.getString("DesCorSol")==null?"":rst.getString("DesCorSol"));
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV,rst.getString("DesLarSol")==null?"":rst.getString("DesLarSol"));
                        vecReg.add(INT_TBL_DAT_CODDOC_SOLTRAINV,rst.getString("CodDocSol")==null?"":rst.getString("CodDocSol"));
                        vecReg.add(INT_TBL_DAT_NUMDOC_SOLTRAINV,rst.getString("NumSol")==null?"":rst.getString("NumSol"));
                        vecReg.add(INT_TBL_DAT_FECDOC_SOLTRAINV,rst.getString("FecDocSol")==null?"":rst.getString("FecDocSol"));
                        vecReg.add(INT_TBL_DAT_CODDES_SOLTRAINV,rst.getString("co_cli")==null?"":rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOMDES_SOLTRAINV,rst.getString("NomCli")==null?"":rst.getString("NomCli"));
                        vecReg.add(INT_TBL_DAT_CODBODORI_SOLTRAINV,rst.getString("co_bodOrg")==null?"":rst.getString("co_bodOrg"));
                        vecReg.add(INT_TBL_DAT_NOMBODORI_SOLTRAINV,rst.getString("NomBodOrg")==null?"":rst.getString("NomBodOrg"));
                        vecReg.add(INT_TBL_DAT_CODBODDES_SOLTRAINV,rst.getString("co_bodDes")==null?"":rst.getString("co_bodDes"));
                        vecReg.add(INT_TBL_DAT_NOMBODDES_SOLTRAINV,rst.getString("NomBodDes")==null?"":rst.getString("NomBodDes"));
                        vecReg.add(INT_TBL_DAT_PESKGR_SOLTRAINV,rst.getString("nd_pesTotKgr")==null?"":rst.getString("nd_pesTotKgr"));
                        vecReg.add(INT_TBL_DAT_BUT_SOLTRAINV,"");
                        
                        if(rst.getString("EstAut")!=null)   strAuxAut=rst.getString("EstAut");   else       strAuxAut="";

                        vecReg.add(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV,((strAuxAut.compareTo("A")==0)? Boolean.TRUE:Boolean.FALSE));
                        vecReg.add(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV,((strAuxAut.compareTo("D")==0)? Boolean.TRUE:Boolean.FALSE));
                        vecReg.add(INT_TBL_DAT_CHKANU_AUTSOLTRAINV,((strAuxAut.compareTo("I")==0)? Boolean.TRUE:Boolean.FALSE));
                        vecReg.add(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV,((strAuxAut.compareTo("Y")==0)? Boolean.TRUE:Boolean.FALSE) );
                        vecReg.add(INT_TBL_DAT_FECAUT_AUTSOLTRAINV,rst.getString("fe_Aut")==null?"":rst.getString("fe_Aut"));
                        vecReg.add(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV,rst.getString("tx_obsAut")==null?"":rst.getString("tx_obsAut"));
                        vecReg.add(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV,"");
                        
                        vecReg.add(INT_TBL_DAT_TIENE_ORDDESPEDOTRBOD,rst.getString("OrdPed"));
                        vecReg.add(INT_TBL_DAT_BUT_DETITM_ORDDESPEDOTRBOD,"");
           
                        vecReg.add(INT_TBL_DAT_CODEMP_FACVEN,rst.getString("CodEmpFacVen")==null?"":rst.getString("CodEmpFacVen"));
                        vecReg.add(INT_TBL_DAT_CODLOC_FACVEN,rst.getString("CodLocFacVen")==null?"":rst.getString("CodLocFacVen"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC_FACVEN,rst.getString("CodTipDocFacVen")==null?"":rst.getString("CodTipDocFacVen"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC_FACVEN,rst.getString("DesCorFacVen")==null?"":rst.getString("DesCorFacVen"));
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOC_FACVEN,rst.getString("DesLarFacVen")==null?"":rst.getString("DesLarFacVen"));
                        vecReg.add(INT_TBL_DAT_CODDOC_FACVEN,rst.getString("CodDocFacVen")==null?"":rst.getString("CodDocFacVen"));
                        vecReg.add(INT_TBL_DAT_NUMDOC_FACVEN,rst.getString("NumDocFacVen")==null?"":rst.getString("NumDocFacVen"));
                        vecReg.add(INT_TBL_DAT_BUT_FACVEN,"");
                        vecReg.add(INT_TBL_DAT_CODEMP_ORDDESCLI,rst.getString("CodEmpOrdCli")==null?"":rst.getString("CodEmpOrdCli"));
                        vecReg.add(INT_TBL_DAT_CODLOC_ORDDESCLI,rst.getString("CodLocOrdCli")==null?"":rst.getString("CodLocOrdCli"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC_ORDDESCLI,rst.getString("CodTipDocOrdCli")==null?"":rst.getString("CodTipDocOrdCli"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC_ORDDESCLI,rst.getString("DesCorOrdCli")==null?"":rst.getString("DesCorOrdCli"));
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOC_ORDDESCLI,rst.getString("DesLarOrdCli")==null?"":rst.getString("DesLarOrdCli"));
                        vecReg.add(INT_TBL_DAT_CODDOC_ORDDESCLI,rst.getString("CodDocOrdCli")==null?"":rst.getString("CodDocOrdCli"));
                        vecReg.add(INT_TBL_DAT_NUMDOC_ORDDESCLI,rst.getString("ne_numOrdDes")==null?"":rst.getString("ne_numOrdDes"));
                        vecReg.add(INT_TBL_DAT_BUT_ORDDESCLI,"");
                        
                        if(rst.getString("st_ConInv")!=null)  strConInv=rst.getString("st_ConInv");    else    strConInv="";
                  
                        vecReg.add(INT_TBL_DAT_CHK_PROCOM,((strConInv.compareTo("C")==0)? Boolean.TRUE:(strConInv.compareTo("N")==0)? Boolean.TRUE: Boolean.FALSE));
               
                        vecDat.add(vecReg);
                        
                    }
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

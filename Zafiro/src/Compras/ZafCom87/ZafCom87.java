
package Compras.ZafCom87;
import Compras.ZafCom83.ZafCom83;
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
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

/**
 *
 * @author  Rosa Zambrano
 * "Listado de Solicitudes de Transferencias de Inventario Autorizadas."
 */
public class ZafCom87 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;                               //Linea.
    private static final int INT_TBL_DAT_CODSEG = 1;                            //Código de Seguimiento.
    private static final int INT_TBL_DAT_CODEMP_SOLTRAINV = 2;                  //Código de Empresa de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODLOC_SOLTRAINV = 3;                  //Código de Local de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODTIPDOC_SOLTRAINV = 4;               //Código de Tipo Documento de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV = 5;            //Descripción Corta Tipo Documento de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV = 6;            //Descripción Larga Tipo Documento de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODDOC_SOLTRAINV = 7;                  //Código de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_NUMDOC_SOLTRAINV = 8;                  //Número de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_FECDOC_SOLTRAINV = 9;                  //Fecha de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODBODORI_SOLTRAINV = 10;              //Bodega Origen Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_NOMBODORI_SOLTRAINV = 11;              //Bodega Origen Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_CODBODDES_SOLTRAINV = 12;              //Bodega Destino Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_NOMBODDES_SOLTRAINV = 13;              //Bodega Destino Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_TXT_OBS_SOLTRAINV = 14;                //Observación de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_PESKGR_SOLTRAINV = 15;                 //Peso Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_BUT_SOLTRAINV = 16;                    //Botón "Solicitudes de Transferencias de Inventario".
    private static final int INT_TBL_DAT_CHKAUT_AUTSOLTRAINV = 17;              //Solicitudes de Transferencias de Inventario Autorizadas.
    private static final int INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV = 18;           //Solicitudes de Transferencias de Inventario Preautorizadas.
    private static final int INT_TBL_DAT_CHKDEG_AUTSOLTRAINV = 19;              //Solicitudes de Transferencias de Inventario Denegadas.
    private static final int INT_TBL_DAT_CHKANU_AUTSOLTRAINV = 20;              //Solicitudes de Transferencias de Inventario Anuladas.
    private static final int INT_TBL_DAT_CHKCAN_AUTSOLTRAINV = 21;              //Solicitudes de Transferencias de Inventario con Cancelación.
    private static final int INT_TBL_DAT_FECAUT_AUTSOLTRAINV = 22;              //Fecha de Autorización de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV = 23;          //Observación de Autorización de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV = 24;          //Observación de Autorización de Solicitudes de Transferencias de Inventario.
    private static final int INT_TBL_DAT_BUT_SEGSOLCAB = 25;                    //Muestra los seguimientos de la solicitud FORMATO#1 CABECERA
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
    private ZafVenCon vcoTipDoc;                                                //Ventana de consulta.
    private ZafSelFec objSelFec;                                                //Selector de Fecha.
    
    private boolean blnMarTodChkTblBodOri=true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnMarTodChkTblBodDes=true;                                 //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private String strSQL, strAux;
    private String strDesCorTipDoc, strDesLarTipDoc;                            //Contenido del campo al obtener el foco.
    
    private String strVersion=" v0.3 ";                                         //Versión del Programa.  
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCom87(ZafParSis obj) 
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
            
             //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
          
            //Permisos de Usuarios
            objPerUsr=new ZafPerUsr(objParSis);
             
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3351)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3352)) 
            {
                butCer.setVisible(false);
            }
            
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false); 
            objSelFec.setTitulo("Fecha de Documento ");
            panFil.add(objSelFec);
            objSelFec.setBounds(6, 4, 490, 75);
            configurarFechaDesde();
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            
            //Configurar los JTables.
            configurarTblBodOri();
            configurarTblBodDes();
            configurarTblDat();
            
            //Carga Bodegas
            cargarBodOri();
            cargarBodDes();
            
            //Otras Configuraciones
            txtCodTipDoc.setVisible(false);
            
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
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_CODEMP_SOLTRAINV,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC_SOLTRAINV,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC_SOLTRAINV,"Cód.Tip.Doc.");  
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC_SOLTRAINV,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC_SOLTRAINV,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC_SOLTRAINV,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODBODORI_SOLTRAINV,"Cód.Bod.Ori.");
            vecCab.add(INT_TBL_DAT_NOMBODORI_SOLTRAINV,"Bod.Ori.");
            vecCab.add(INT_TBL_DAT_CODBODDES_SOLTRAINV,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_NOMBODDES_SOLTRAINV,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_TXT_OBS_SOLTRAINV,"Obs.");
            vecCab.add(INT_TBL_DAT_PESKGR_SOLTRAINV,"Peso Total (Kgr)");
            vecCab.add(INT_TBL_DAT_BUT_SOLTRAINV," ");
            
            vecCab.add(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV,"Autorizada");
            vecCab.add(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV,"Preautorizada");
            vecCab.add(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV,"Denegada");
            vecCab.add(INT_TBL_DAT_CHKANU_AUTSOLTRAINV,"Anulada");
            vecCab.add(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV,"Cancelada");
            vecCab.add(INT_TBL_DAT_FECAUT_AUTSOLTRAINV,"Fec.Aut.");
            vecCab.add(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV,"Obs.Aut.");
            vecCab.add(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV,"Obs.Aut.");
            vecCab.add(INT_TBL_DAT_BUT_SEGSOLCAB, "Seg.Cab.");
            
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
            
            tcmAux.getColumn(INT_TBL_DAT_CODEMP_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC_SOLTRAINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC_SOLTRAINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC_SOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC_SOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODBODORI_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODORI_SOLTRAINV).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_CODBODDES_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODDES_SOLTRAINV).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_TXT_OBS_SOLTRAINV).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_PESKGR_SOLTRAINV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FECAUT_AUTSOLTRAINV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV).setPreferredWidth(30);  
            tcmAux.getColumn(INT_TBL_DAT_BUT_SEGSOLCAB).setPreferredWidth(30);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Agrupar Columnas.
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            
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
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODORI_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODORI_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODDES_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODDES_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_TXT_OBS_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_PESKGR_SOLTRAINV));
            objTblHeaColGrpSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV));
            
            //Grupo: Autorizacion.
            ZafTblHeaColGrp objTblHeaColGrpAutSolTraInv=new ZafTblHeaColGrp("Estado de la Solicitud");
            objTblHeaColGrpAutSolTraInv.setHeight(16);
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_FECAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV));
            objTblHeaColGrpAutSolTraInv.add(tcmAux.getColumn(INT_TBL_DAT_BUT_SEGSOLCAB));
            
            //Agregar todos los grupos del Jtable.
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSolTraInv);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAutSolTraInv);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODEMP_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODLOC_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODDOC_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODBODORI_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CODBODDES_SOLTRAINV, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV, tblDat);
            
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
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PESKGR_SOLTRAINV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV).setResizable(false);
         
            //Configurar JTable: Establecer el check en columnas
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHKANU_AUTSOLTRAINV).setCellRenderer(objTblCelRenChk); 
            tcmAux.getColumn(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV).setCellRenderer(objTblCelRenLbl);
            
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi objZafTblEdi = new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_SOLTRAINV);
            vecAux.add("" + INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV);
            vecAux.add("" + INT_TBL_DAT_BUT_SEGSOLCAB);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
                       
            //Configurar JTable:  Botones.
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SEGSOLCAB).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
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
                            
                        case INT_TBL_DAT_BUT_SEGSOLCAB:
                            if(objTblModDat.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CODEMP_SOLTRAINV).toString().equals(""))
                               objTblCelRenBut.setText("");
                            else
                               objTblCelRenBut.setText("...");
                        break;
                    }
                }
            });
            
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOLTRAINV).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SEGSOLCAB).setCellEditor(objTblCelEdiButGen);

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
                            case INT_TBL_DAT_BUT_SOLTRAINV:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_SOLTRAINV).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break; 
                            case INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV:
                                if( (objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            case INT_TBL_DAT_BUT_SEGSOLCAB:
                                if((objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_SOLTRAINV).toString().equals("")))
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
                            case INT_TBL_DAT_BUT_SOLTRAINV:
                                cargarVentanaSolTraInv(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP_SOLTRAINV).toString(), 
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC_SOLTRAINV).toString(),
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC_SOLTRAINV).toString(), 
                                                       tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC_SOLTRAINV).toString() );
                                break;
                            case INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV:
                                cargarVentanaObsAut(tblDat.getSelectedRow(), INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV );
                                break;
                            case INT_TBL_DAT_BUT_SEGSOLCAB:
                                llamarSegSolTraInvCab(objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP_SOLTRAINV).toString(), objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC_SOLTRAINV).toString(), objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC_SOLTRAINV).toString(), objTblModDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC_SOLTRAINV).toString());
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

    //<editor-fold defaultstate="collapsed" desc="/* Botón Solicitud de Transferencia de Inventario */">
    private void cargarVentanaSolTraInv(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
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
                    //<editor-fold defaultstate="collapsed" desc="/*comentado*/">
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
                    //</editor-fold>
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
                    //<editor-fold defaultstate="collapsed" desc="/*comentado*/">
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
                    //</editor-fold>
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
                    vecReg.add(INT_TBL_BODORI_CHKBOD,false);
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
                    vecReg.add(INT_TBL_BODDES_CHKBOD,false);
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
                //<editor-fold defaultstate="collapsed" desc="/*comentado*/">
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
                //</editor-fold>
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
                //<editor-fold defaultstate="collapsed" desc="/*comentado*/">
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
                //</editor-fold>
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
            //System.out.println("strTipDoc: "+strTipDoc);
        }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);   }
        return strTipDoc;
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
                case INT_TBL_DAT_TXT_OBS_SOLTRAINV:
                    strMsg="Observación de la solicitud. En algunos casos el número de pedido.";
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
                case INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV:
                    strMsg="Preautorizada";
                    break;    
                case INT_TBL_DAT_CHKDEG_AUTSOLTRAINV:
                    strMsg="Denegada";
                    break;                
                case INT_TBL_DAT_CHKANU_AUTSOLTRAINV:
                    strMsg="Anulada por el sistema";
                    break;
                case INT_TBL_DAT_CHKCAN_AUTSOLTRAINV:
                    strMsg="Contiene cancelaciones";
                    break;
                case INT_TBL_DAT_FECAUT_AUTSOLTRAINV:
                    strMsg="Fecha de autorización";
                    break;    
                case INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV:
                    strMsg="Observación de la autorización";
                    break;  
               case INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV:
                    strMsg="Observación de la autorización";
                    break;      
               case INT_TBL_DAT_BUT_SEGSOLCAB:
                   strMsg="Seguimiento de solicitudes de transferencia Cabecera";
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
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        panSolTraDesHas = new javax.swing.JPanel();
        lblSolTraDes = new javax.swing.JLabel();
        txtSolTraDes = new javax.swing.JTextField();
        lblSolTraHas = new javax.swing.JLabel();
        txtSolTraHas = new javax.swing.JTextField();
        chkSolPenAut = new javax.swing.JCheckBox();
        chkSolAut = new javax.swing.JCheckBox();
        chkSolCan = new javax.swing.JCheckBox();
        chkSolDenAnu = new javax.swing.JCheckBox();
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
        panBodOri.setBounds(10, 90, 320, 100);

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
        panBodDes.setBounds(330, 90, 310, 100);

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
        optTod.setBounds(8, 200, 400, 20);

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
        optFil.setBounds(8, 220, 400, 20);
        optFil.getAccessibleContext().setAccessibleParent(spnFil);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(30, 250, 120, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(130, 250, 32, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(160, 250, 94, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(254, 250, 370, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(623, 250, 20, 20);

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
        txtSolTraDes.setBounds(65, 20, 70, 20);

        lblSolTraHas.setText("Hasta:");
        panSolTraDesHas.add(lblSolTraHas);
        lblSolTraHas.setBounds(150, 20, 50, 16);

        txtSolTraHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSolTraHasFocusLost(evt);
            }
        });
        panSolTraDesHas.add(txtSolTraHas);
        txtSolTraHas.setBounds(200, 20, 80, 20);

        panFil.add(panSolTraDesHas);
        panSolTraDesHas.setBounds(350, 290, 290, 48);

        chkSolPenAut.setSelected(true);
        chkSolPenAut.setText("Mostrar solicitudes pendientes de autorización.");
        chkSolPenAut.setToolTipText("");
        chkSolPenAut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSolPenAutItemStateChanged(evt);
            }
        });
        panFil.add(chkSolPenAut);
        chkSolPenAut.setBounds(30, 280, 320, 20);

        chkSolAut.setText("Mostrar solicitudes autorizadas.");
        chkSolAut.setToolTipText("");
        chkSolAut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSolAutItemStateChanged(evt);
            }
        });
        panFil.add(chkSolAut);
        chkSolAut.setBounds(30, 300, 320, 20);

        chkSolCan.setText("Mostrar solicitudes canceladas.");
        chkSolCan.setToolTipText("");
        chkSolCan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSolCanItemStateChanged(evt);
            }
        });
        panFil.add(chkSolCan);
        chkSolCan.setBounds(30, 340, 320, 20);

        chkSolDenAnu.setText("Mostrar solicitudes denegadas y/o anuladas.");
        chkSolDenAnu.setToolTipText("");
        chkSolDenAnu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSolDenAnuItemStateChanged(evt);
            }
        });
        panFil.add(chkSolDenAnu);
        chkSolDenAnu.setBounds(30, 320, 320, 20);

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

        setBounds(0, 0, 700, 509);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
                 
            chkSolPenAut.setSelected(false);
            chkSolAut.setSelected(false);
            chkSolCan.setSelected(false);
            
            txtSolTraDes.setText("");
            txtSolTraHas.setText("");         
            
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

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

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected())
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilStateChanged

    private void chkSolPenAutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSolPenAutItemStateChanged
        if (chkSolPenAut.isSelected() && !optFil.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolPenAutItemStateChanged

    private void chkSolAutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSolAutItemStateChanged
        if (chkSolAut.isSelected() && !optFil.isSelected())
             optFil.setSelected(true);
    }//GEN-LAST:event_chkSolAutItemStateChanged

    private void chkSolCanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSolCanItemStateChanged
         if (chkSolCan.isSelected() && !optFil.isSelected())
             optFil.setSelected(true);
    }//GEN-LAST:event_chkSolCanItemStateChanged

    private void chkSolDenAnuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSolDenAnuItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSolDenAnuItemStateChanged
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkSolAut;
    private javax.swing.JCheckBox chkSolCan;
    private javax.swing.JCheckBox chkSolDenAnu;
    private javax.swing.JCheckBox chkSolPenAut;
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
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtSolTraDes;
    private javax.swing.JTextField txtSolTraHas;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando
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
            
        return true;
    }
    
    
    private String sqlConFil() 
    {
        String sqlFil="";
        String strBodOrg="", strBodDes="";
        int intTipDoc=1;
        boolean blnBodOrg=false, blnBodDes=false;
        
        //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Fecha del documento. */">
        if(objSelFec.isCheckBoxChecked() )
        {
            switch (objSelFec.getTipoSeleccion()) 
            {
                case 0: //Búsqueda por rangos
                    sqlFil+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil+=" AND a1.fe_doc <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil+=" AND a1.fe_doc >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
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
                    strBodOrg+=" AND a1.co_bodOrg in ( ";
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
                    strBodDes+=" AND a1.co_bodDes in ( ";
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
        
        if (optFil.isSelected()) 
        {
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Solicitud. */">
            if (txtSolTraDes.getText().length()>0 && txtSolTraHas.getText().length()>0)
                sqlFil+="  AND a1.ne_numDoc BETWEEN "+objUti.codificar(txtSolTraDes.getText())+" AND "+objUti.codificar(txtSolTraHas.getText());
            //</editor-fold>
            
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Tipo de Documento. */">
            if (txtCodTipDoc.getText().length() > 0) {
                sqlFil += " AND a1.co_tipDoc = " + txtCodTipDoc.getText().replaceAll("'", "''") + " ";
            }  
            else
            {
                sqlFil += " AND a1.co_tipDoc in ( " + obtenerQueryTipoDocumento(intTipDoc) + ") ";
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Filtro: Estado de Autorizacion. */">
            strAux = "";
            //Solicitudes Autorizadas.
            if (chkSolAut.isSelected()) 
            {
                if (strAux.equals("")) 
                    strAux+= "'A'";
                else
                    strAux+= ",'A'";
            }
            //Solicitudes Denegadas y/o Anuladas.
            if (chkSolDenAnu.isSelected()) 
            {
                if (strAux.equals("")) 
                    strAux += "'D','I'";
                else 
                    strAux += ",'D','I'";
            }
            //Filtro
            if (!strAux.equals("")) 
            {
                if (chkSolPenAut.isSelected()) 
                {
                    sqlFil+=" AND ( a1.st_Aut IN (" + strAux + ") OR a1.st_Aut IS NULL )";
                }
                else
                {
                    sqlFil+=" AND a1.st_Aut IN (" + strAux + ")   ";
                }
            }
            else
            {
                if (chkSolPenAut.isSelected()) 
                {
                     sqlFil+=" AND a1.st_Aut IS NULL   ";
                }
            }
            //</editor-fold>
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
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                
                if (!chkSolCan.isSelected()){
                    //Armar la sentencia SQL.
                    strSQL =" SELECT a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="        a1.co_bodOrg, a2.tx_nom as NomBodOrg, a1.co_bodDes, a3.tx_nom as NomBodDes, \n";
                    strSQL+="        a1.nd_pesTotKgr, a1.tx_obs1, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, \n";
                    strSQL+="        CASE WHEN a6.co_tipDoc IS NOT NULL THEN 'Y' ELSE a1.st_Aut END as st_Aut,null as co_tipdocrelcabingegrmerbod \n";
                    strSQL+=" FROM tbm_cabSoltraInv as a1  \n";
                    strSQL+=" INNER JOIN tbm_bod as a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bodOrg)  \n";
                    strSQL+=" INNER JOIN tbm_bod as a3 ON (a3.co_emp=a1.co_emp AND a3.co_bod=a1.co_bodDes)  \n";
                    strSQL+=" INNER JOIN tbm_CabTipDoc as a4 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" LEFT JOIN tbm_cabSegMovInv as a5 ON (a5.co_empRelCabSolTraInv=a1.co_emp AND a5.co_LocRelCabSolTraInv=a1.co_Loc AND a5.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a5.co_docRelCabSolTraInv=a1.co_doc) \n";
                    strSQL+=" LEFT JOIN \n";
                    strSQL+=" ( \n";
                    strSQL+=" 	SELECT a1.co_tipDoc FROM tbm_cfgtipdocutiproaut as a \n";
                    strSQL+=" 	INNER JOIN tbm_cabSolTraInv as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" 	WHERE a.co_emp=0 AND a.co_cfg in (2000, 2001)  \n";
                    strSQL+=" 	GROUP BY a1.co_tipDoc \n";
                    strSQL+=" ) as a6 ON ( a6.co_tiPDoc=a1.co_tipDoc ) \n";
                    strSQL+=" WHERE a1.st_reg='A' "+strFil+" \n";
                    strSQL+=" GROUP BY a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="          a1.co_bodOrg, a2.tx_nom, a1.co_bodDes, a3.tx_nom, \n";
                    strSQL+="          a1.nd_pesTotKgr, a1.tx_obs1, a1.st_Aut, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, a6.co_tipDoc, co_tipdocrelcabingegrmerbod  \n";
                    strSQL+=" ORDER BY a5.co_seg, a1.fe_doc, a1.co_tipDoc desc, a1.ne_numDoc \n";
                }else if (chkSolCan.isSelected() && (chkSolAut.isSelected() || chkSolDenAnu.isSelected() || chkSolPenAut.isSelected() )) {
                    strSQL =" SELECT a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="        a1.co_bodOrg, a2.tx_nom as NomBodOrg, a1.co_bodDes, a3.tx_nom as NomBodDes, \n";
                    strSQL+="        a1.nd_pesTotKgr, a1.tx_obs1, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, \n";
                    strSQL+="        CASE WHEN a6.co_tipDoc IS NOT NULL THEN 'Y' ELSE a1.st_Aut END as st_Aut,null as co_tipdocrelcabingegrmerbod \n";
                    strSQL+=" FROM tbm_cabSoltraInv as a1  \n";
                    strSQL+=" INNER JOIN tbm_bod as a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bodOrg)  \n";
                    strSQL+=" INNER JOIN tbm_bod as a3 ON (a3.co_emp=a1.co_emp AND a3.co_bod=a1.co_bodDes)  \n";
                    strSQL+=" INNER JOIN tbm_CabTipDoc as a4 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" LEFT JOIN tbm_cabSegMovInv as a5 ON (a5.co_empRelCabSolTraInv=a1.co_emp AND a5.co_LocRelCabSolTraInv=a1.co_Loc AND a5.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a5.co_docRelCabSolTraInv=a1.co_doc) \n";
                    strSQL+=" LEFT JOIN \n";
                    strSQL+=" ( \n";
                    strSQL+=" 	SELECT a1.co_tipDoc FROM tbm_cfgtipdocutiproaut as a \n";
                    strSQL+=" 	INNER JOIN tbm_cabSolTraInv as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" 	WHERE a.co_emp=0 AND a.co_cfg in (2000, 2001)  \n";
                    strSQL+=" 	GROUP BY a1.co_tipDoc \n";
                    strSQL+=" ) as a6 ON ( a6.co_tiPDoc=a1.co_tipDoc ) \n";
                    strSQL+=" WHERE a1.st_reg='A' "+strFil+" \n";
                    strSQL+=" GROUP BY a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="          a1.co_bodOrg, a2.tx_nom, a1.co_bodDes, a3.tx_nom, \n";
                    strSQL+="          a1.nd_pesTotKgr, a1.tx_obs1, a1.st_Aut, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, a6.co_tipDoc  \n";
                    //strSQL+=" ORDER BY a5.co_seg, a1.fe_doc, a1.co_tipDoc desc, a1.ne_numDoc \n";
                    strSQL+=" UNION ";
                    strSQL+=" SELECT a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="        a1.co_bodOrg, a2.tx_nom as NomBodOrg, a1.co_bodDes, a3.tx_nom as NomBodDes, \n";
                    strSQL+="        a1.nd_pesTotKgr, a1.tx_obs1, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, \n";
                    strSQL+="        CASE WHEN a6.co_tipDoc IS NOT NULL THEN 'Y' ELSE a1.st_Aut END as st_Aut, a7.co_tipdocrelcabingegrmerbod \n";
                    strSQL+=" FROM tbm_cabSoltraInv as a1  \n";
                    strSQL+=" INNER JOIN tbm_bod as a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bodOrg)  \n";
                    strSQL+=" INNER JOIN tbm_bod as a3 ON (a3.co_emp=a1.co_emp AND a3.co_bod=a1.co_bodDes)  \n";
                    strSQL+=" INNER JOIN tbm_CabTipDoc as a4 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" LEFT JOIN tbm_cabSegMovInv as a5 ON (a5.co_empRelCabSolTraInv=a1.co_emp AND a5.co_LocRelCabSolTraInv=a1.co_Loc AND a5.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a5.co_docRelCabSolTraInv=a1.co_doc) \n";
                    strSQL+=" inner join  tbm_cabSegMovInv as a7 ON (a7.co_seg=a5.co_seg and a7.co_tipdocrelcabingegrmerbod in(276)) \n";
                    strSQL+=" LEFT JOIN \n";
                    strSQL+=" ( \n";
                    strSQL+=" 	SELECT a1.co_tipDoc FROM tbm_cfgtipdocutiproaut as a \n";
                    strSQL+=" 	INNER JOIN tbm_cabSolTraInv as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" 	WHERE a.co_emp=0 AND a.co_cfg in (2000, 2001)  \n";
                    strSQL+=" 	GROUP BY a1.co_tipDoc \n";
                    strSQL+=" ) as a6 ON ( a6.co_tiPDoc=a1.co_tipDoc ) \n";
                    strSQL+=" WHERE a1.st_reg='A' "+strFil+" \n";
                    strSQL+=" GROUP BY a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="          a1.co_bodOrg, a2.tx_nom, a1.co_bodDes, a3.tx_nom, \n";
                    strSQL+="          a1.nd_pesTotKgr, a1.tx_obs1, a1.st_Aut, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, a6.co_tipDoc, a7.co_tipdocrelcabingegrmerbod  \n";
                    strSQL+=" ORDER BY co_seg, fe_doc, co_tipDoc desc, ne_numDoc \n";
                }else{
                    strSQL=" SELECT a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="        a1.co_bodOrg, a2.tx_nom as NomBodOrg, a1.co_bodDes, a3.tx_nom as NomBodDes, \n";
                    strSQL+="        a1.nd_pesTotKgr, a1.tx_obs1, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, \n";
                    strSQL+="        CASE WHEN a6.co_tipDoc IS NOT NULL THEN 'Y' ELSE a1.st_Aut END as st_Aut, a7.co_tipdocrelcabingegrmerbod \n";
                    strSQL+=" FROM tbm_cabSoltraInv as a1  \n";
                    strSQL+=" INNER JOIN tbm_bod as a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bodOrg)  \n";
                    strSQL+=" INNER JOIN tbm_bod as a3 ON (a3.co_emp=a1.co_emp AND a3.co_bod=a1.co_bodDes)  \n";
                    strSQL+=" INNER JOIN tbm_CabTipDoc as a4 ON (a4.co_emp=a1.co_emp AND a4.co_loc=a1.co_loc AND a4.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" LEFT JOIN tbm_cabSegMovInv as a5 ON (a5.co_empRelCabSolTraInv=a1.co_emp AND a5.co_LocRelCabSolTraInv=a1.co_Loc AND a5.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a5.co_docRelCabSolTraInv=a1.co_doc) \n";
                    strSQL+=" inner join  tbm_cabSegMovInv as a7 ON (a7.co_seg=a5.co_seg and a7.co_tipdocrelcabingegrmerbod in(276)) \n";
                    strSQL+=" LEFT JOIN \n";
                    strSQL+=" ( \n";
                    strSQL+=" 	SELECT a1.co_tipDoc FROM tbm_cfgtipdocutiproaut as a \n";
                    strSQL+=" 	INNER JOIN tbm_cabSolTraInv as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc) \n";
                    strSQL+=" 	WHERE a.co_emp=0 AND a.co_cfg in (2000, 2001)  \n";
                    strSQL+=" 	GROUP BY a1.co_tipDoc \n";
                    strSQL+=" ) as a6 ON ( a6.co_tiPDoc=a1.co_tipDoc ) \n";
                    strSQL+=" WHERE a1.st_reg='A' "+strFil+" \n";
                    strSQL+=" GROUP BY a5.co_seg, a1.co_emp, a1.co_Loc, a1.co_TipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, \n";
                    strSQL+="          a1.co_bodOrg, a2.tx_nom, a1.co_bodDes, a3.tx_nom, \n";
                    strSQL+="          a1.nd_pesTotKgr, a1.tx_obs1, a1.st_Aut, a1.fe_aut, a1.tx_obsAut, a1.st_ConInv, a6.co_tipDoc, a7.co_tipdocrelcabingegrmerbod  \n";
                    strSQL+=" ORDER BY co_seg, fe_doc, co_tipDoc desc, ne_numDoc \n";
                }
               
               
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
                        String strAuxAut="";
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CODSEG,rst.getString("co_Seg")==null?"":rst.getString("co_Seg"));
                        vecReg.add(INT_TBL_DAT_CODEMP_SOLTRAINV,rst.getString("co_emp")==null?"":rst.getString("co_emp"));                        
                        vecReg.add(INT_TBL_DAT_CODLOC_SOLTRAINV,rst.getString("co_Loc")==null?"":rst.getString("co_Loc"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC_SOLTRAINV,rst.getString("co_TipDoc")==null?"":rst.getString("co_TipDoc"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC_SOLTRAINV,rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOC_SOLTRAINV,rst.getString("tx_desLar")==null?"":rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_CODDOC_SOLTRAINV,rst.getString("co_doc")==null?"":rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUMDOC_SOLTRAINV,rst.getString("ne_numDoc")==null?"":rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FECDOC_SOLTRAINV,rst.getString("fe_doc")==null?"":rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_CODBODORI_SOLTRAINV,rst.getString("co_bodOrg")==null?"":rst.getString("co_bodOrg"));
                        vecReg.add(INT_TBL_DAT_NOMBODORI_SOLTRAINV,rst.getString("NomBodOrg")==null?"":rst.getString("NomBodOrg"));
                        vecReg.add(INT_TBL_DAT_CODBODDES_SOLTRAINV,rst.getString("co_bodDes")==null?"":rst.getString("co_bodDes"));
                        vecReg.add(INT_TBL_DAT_NOMBODDES_SOLTRAINV,rst.getString("NomBodDes")==null?"":rst.getString("NomBodDes"));
                        vecReg.add(INT_TBL_DAT_TXT_OBS_SOLTRAINV,rst.getString("tx_obs1")==null?"":rst.getString("tx_obs1"));
                        vecReg.add(INT_TBL_DAT_PESKGR_SOLTRAINV,rst.getString("nd_pesTotKgr")==null?"":rst.getString("nd_pesTotKgr"));
                        vecReg.add(INT_TBL_DAT_BUT_SOLTRAINV,"");
                        
                        if(rst.getString("st_Aut")!=null)   strAuxAut=rst.getString("st_Aut");    else    strAuxAut="";

                        vecReg.add(INT_TBL_DAT_CHKAUT_AUTSOLTRAINV,((strAuxAut.compareTo("A")==0)? Boolean.TRUE:Boolean.FALSE));
                        vecReg.add(INT_TBL_DAT_CHKPREAUT_AUTSOLTRAINV,((strAuxAut.compareTo("Y")==0)? Boolean.TRUE:Boolean.FALSE));
                        vecReg.add(INT_TBL_DAT_CHKDEG_AUTSOLTRAINV,((strAuxAut.compareTo("D")==0)? Boolean.TRUE:Boolean.FALSE));
                        vecReg.add(INT_TBL_DAT_CHKANU_AUTSOLTRAINV,((strAuxAut.compareTo("I")==0)? Boolean.TRUE:Boolean.FALSE));
                        //vecReg.add(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV,((strAuxAut.compareTo("I")==0)? Boolean.TRUE:Boolean.FALSE));
                        if(rst.getString("co_tipdocrelcabingegrmerbod")!=null && rst.getString("co_tipdocrelcabingegrmerbod").equals("276")){
                            vecReg.add(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV,Boolean.TRUE);
                        }else{
                            vecReg.add(INT_TBL_DAT_CHKCAN_AUTSOLTRAINV,Boolean.FALSE);
                        }
                        
                        vecReg.add(INT_TBL_DAT_FECAUT_AUTSOLTRAINV,rst.getString("fe_Aut")==null?"":rst.getString("fe_Aut"));
                        vecReg.add(INT_TBL_DAT_TXT_OBSAUT_AUTSOLTRAINV,rst.getString("tx_obsAut")==null?"":rst.getString("tx_obsAut"));
                        vecReg.add(INT_TBL_DAT_BUT_OBSAUT_AUTSOLTRAINV,"");
                        vecReg.add(INT_TBL_DAT_BUT_SEGSOLCAB,"");
                        
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
    //<editor-fold defaultstate="collapsed" desc="Llama a la pantalla de seguimimento de solicitudes (Cabecera)"> 
    private void llamarSegSolTraInvCab(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        HashMap map = new HashMap();
        map.put("objParSis", objParSis);
        map.put("strCodEmp", strCodEmp);
        map.put("strCodLoc", strCodLoc);
        map.put("strCodTipDoc", strCodTipDoc);
        map.put("strCodDoc", strCodDoc);
        try {
            ZafCom83 obj1 = new ZafCom83(map);
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        } catch (Exception Evt) {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }//</editor-fold> 
    
}

/*
 * 
 *  Created on 08 de Junio de 2006  
 */
      
package Ventas.ZafVen02;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafAut.ZafAut;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorElePrg.ZafCorElePrg;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRepInv.ZafRepInv;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTableColBut.ZafTableColBut;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco2.ZafTblCelEdiTxtVco2;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafTipDoc;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafEstFacEle.ZafCabEstFacEle;
import Librerias.ZafEstFacEle.ZafEstFacEle;
import Ventas.ZafVen21.ZafVen21_01;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
       
//////José Marín 1/Abr/2015
import Librerias.ZafPulFacEle.ZafPulFacEle; 
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
//////

 /**
  * @author  Javier Ayapata Garate
  * FACTURA COMERCIAL.
  */
public class ZafVen02 extends javax.swing.JInternalFrame 
{
   private mitoolbar objTooBar;
   private Connection conCot, con_remota=null, conCab , CONN_GLO=null;   //Coneccion a la base donde se encuentra la cotizacion
   private Statement stmCotDet2, stmCab, stmCot;    //Statement para la cotizacion
   private ResultSet rstCotCab2=null, rstCotDet2, rstCab;    //Resultset que tendra los datos de la cabecera
    
   private ZafParSis objZafParSis;
   private ZafObtConCen  objObtConCen;
   private UltDocPrint  objUltDocPrint2;  // Para trabajar con la informacion de tipo de documento
   private ZafTipDoc objTipDoc;
   private ZafInvItm objInvItm; 
    
   private ZafAutPrg objAutPrg;
   private ZafAsiDia objAsiDia; // Asiento de Diario
   private ZafCtaCtb_dat objCtaCta_Dat;  //Datos de Cuentas contables
   private ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
   private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
   private ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
   private ZafTblMod objTblMod, objTblModPag;
   private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
   private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.
   private ZafTblModLis objTblModLis;
   private ZafMouMotAda objMouMotAda;
   private ZafTblCelEdiButVco objTblCelEdiButVcoItm;   //Editor: JButton en celda.
   private ZafTblPopMnu ZafTblPopMn;
   private ZafTblCelEdiTxtVco2 objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.
   private ZafRepInv objZafRep;   //Clase que maneja las reposiciones de inventario
   private ZafThreadGUI objThrGUI;
   private ZafRptSis objRptSis;                               //Reportes del Sistema.
   
   private boolean blnChangeData=false;
   private boolean blnLimpiar = true; //Limpiar o no en el metodo clnTextos
   private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento 

   private UltDocPrint  objUltDocPrint;    // Para trabajar con la informacion de tipo de documento
        
   private String strBeforeValue,strAfterValue;
   private String strCodCli, strDesLarCli;
   private String strCodVen, strDesLarVen;
   private String sSQL,strSQL, strFiltro;//EL filtro de la Consulta actual
   private String strAux,strstReg_glo="";
   private String dateFecCot; //Variable que contiene la fecha de cotizacion.
   private String strFecSis;
   private String strCodTipPerCli;
   private String strTipPer_glo = "";
   private String strTipPer_emp="";
   private String GLO_strnomEmp="";
   private String GLO_strdirEmp="";
   private String stIvaVen="S";
   private String STR_ESTREG="";
   private String GLO_strArreItm="";
   private String Codemp=""; 
   private String Codloc=""; 
   private String Codtipdoc=""; 
   private String Coddoc=""; 
   private String strMerIngEgr="", strTipIngEgr="";
   private String strarreglosop[] = new String[10];
   private String VERSION = " v11.9";
   //Jota 21/Ene/2015         
   private int int_Cod_Emp;
   private int int_Cod_Loc;
   private int int_Tip_Doc;
   private int int_Cod_Doc;
   private int Glo_intCodSec=0;
   private int intCodTipPerEmp;
   
   private int intCodMotBien=0;  //  se almacena el codigo del motivo del codumento para las retenciones para un bien
   private int intCodMotServ=0;  //  se almacena el codigo del motivo del codumento para las retenciones para un servicio
   private int intCodMotTran=0;  //  se almacena el codigo del motivo del codumento para las retenciones para un transporte

   private int intCodEmp =0;
   private int intCodLoc =0;
   private int intCodCot=0;
   private int intValBus = 0;
   private int NumLineas=0;
   private int intCodBodPre=0;
   private int intNumDiaVal=0;// Variable que define el numero de dias validos de la cotizacion...
   private int intTipMod = 0; // Varible que define si es una modificacion
   private int intCodMnu;
   private int intNumDec= 2 ; //Numero de decimales a presentar
   private int  INTCODREGCEN=0;
   private int intTipConFac=0;
   private int intarreglonum[] = new int[10];
   private int intarreglodia[] = new int[10];
   private int intCanArr[]= new int[1];
   private String strarritmotr[][] = null;
   
   private double dblSubtotalCot;
   private double dblPorIva,dblPorIvaaux,dblPorIvaCon ;  //Porcentaje de Iva para la empresa enviado en zafParSys
   private double dblTotalCot, dblIvaCot;
   private double dblComSol=0; // JM 18/Ene/2017
   private double Glo_dlbMaxDes=0;
   private double Glo_dlbMarUti=0;
    
   private ZafUtil objUti;
   private LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    
   private ZafVenCon objVenCon; //*****************
   private ZafVenCon objVenCon2; //*****************
   private ZafVenCon objVenConCli; //*****************
   private ZafVenCon objVenConVen; //*****************
   private ZafVenCon objVenConTipdoc; //*****************
  
   private static final int INT_TBL_DIACRE      = 1 ;            //Codigo del item (codigo interno del sistema)
   private static final int INT_TBL_LINEA       = 0 ;
   private static final int INT_TBL_ITMALT      = 1 ;
   private static final int INT_TBL_BUTITM      = 2 ;
   private static final int INT_TBL_DESITM      = 3 ;
   private static final int INT_TBL_UNIDAD      = 4 ;
   private static final int INT_TBL_CODBOD      = 5 ;
   private static final int INT_TBL_BUTBOD      = 6 ;
   private static final int INT_TBL_CANMOV      = 7 ;           //Cantidad del movimiento (venta o compra)
   private static final int INT_TBL_STKBOD      = 8 ; //16      //Stock actual de la bodega
   private static final int INT_TBL_ULTCOS      = 9 ;  //17     //Ultimo costo
   private static final int INT_TBL_PREUNI      = 10 ; //8      //Precio de Venta
   private static final int INT_TBL_UTILIDAD    = 11 ;          //Utilidad
   private static final int INT_TBL_PREUNI2     = 12 ;          //Precio Unitario
   private static final int INT_TBL_PORDES      = 13 ; //11     //Porcentaje de descuento
   private static final int INT_TBL_BLNIVA      = 14;           //Boolean Iva
   private static final int INT_TBL_TOTAL       = 15;           //Total de la venta o compra del producto
   private static final int INT_TBL_CODITM      = 16;
   private static final int INT_TBL_ESTADO      = 17;
   private static final int INT_TBL_IVATXT      = 18;
   private static final int INT_TBL_BLNPRE      = 19;            //Columna que contiene verdadero si es problema de precio
   private static final int INT_TBL_CANORI      = 20;            //Codigo del bodega de una o/c cargada
   private static final int INT_TBL_BODORI      = 21;
   private static final int INT_TBL_CODORI      = 22;
   private static final int INT_TBL_ITMALT2     = 23;
   private static final int INT_TBL_ITMSER      = 24;            //Columna que contiene SI el item es de servicio S o N
   private static final int INT_TBL_ITMTER      = 25;            //Columna que contiene "S" (el item es terminal L ) "N"  no corresponde a la terminal L
   private static final int INT_TBL_CODLOCREL   = 26;
   private static final int INT_TBL_CODTIPREL   = 27;
   private static final int INT_TBL_CODDOCREL   = 28;
   private static final int INT_TBL_CODREGREL   = 29;
   private static final int INT_TBL_CODEMPREL   = 30;
   private static final int INT_TBL_CODITMACT   = 31;            // codigo del item actual..
   private static final int INT_TBL_IEBODFIS    = 32;            // estado que dice si ingresa/egresa fisicamente en bodega
   private static final int INT_TBL_CODCTAEGR   = 33;
   
   //Constantes del ArrayList Elementos Eliminados
   private static final int INT_ARR_CODITM   = 0;
   private static final int INT_ARR_CODBOD   = 1;
   private static final int INT_ARR_CANMOV   = 2;
   private static final int INT_ARR_IEBODFIS = 3;
   
   /*
    *  Valiables que se almacena los datos cuando quieran ver la cotizacion desde la ventana autorizacion
    */
   final int INT_TBL_PAGLIN=0; //Linea de pago
   final int INT_TBL_PAGCRE=1; // Dias de credito
   final int INT_TBL_PAGFEC=2; // Fecha de vencimiento
   final int INT_TBL_PAGRET=3; // porcentaje de retencion
   final int INT_TBL_PAGMON=4; // monto de pago
   private final int INT_TBL_PAGGRA=5; // dias de gracias
   private final int INT_TBL_PAGCOD=6; // codigo de retencion
   private final int INT_TBL_PAGSOP=7; // soporte de cheque

   private JTextField txtCodForPag = new JTextField();
   private JTextField txtcli_identificacion = new JTextField();
   private JTextField txtcli_telefono = new JTextField();
   private JTextField txtcli_ciudad = new JTextField();
   private JInternalFrame jfrThis; //Hace referencia a this
   private Vector vecForPag; //Vector que contiene el codigo del pago
   private Vector vecTransporte; //Vector que contiene el codigo del Transporte

   private int intTipForPag2=0;

   private double dblPes=0;
   private double dblPes2=0;

   private ZafPerUsr objPerUsr;
   
   private String strTit="Mensaje del Sistema Zafiro";  
   
   private boolean blnIsOrdAnulada=false;
   private String strNumOrdDes="";
   
   private boolean blnIsOrdRelModificada=false;
   private String strNumOrdDesRel="", strCorEleDes ="", strCodDocRel;

    private ZafVenCon objVenConForPag; 
    private ZafVenCon objVenConTarCre;
    private ZafVenCon objVenConTipCre;
    private ZafVenCon objVenConMesCre;
    private ZafVenCon objVenConEmisor;
    private String strCodForPag="", strDesLarForPag="", strCodTarCre="", strNomTarCre="", strCodTipCre="", strNomTipCre="", strCodEmisor="", strNomEmisor="", strCodMesCre="", strNomMesCre="";
    private double dblPorDesTarCre=0;
    private JTextField txtCodRedTarCre = new JTextField();
    private JTextField txtTipCre = new JTextField();
    private JTextField txtPorComTarCre = new JTextField();
    private JTextField txtPorDesVenTarCre = new JTextField();    
   
      private ZafPulFacEle objPulFacEle;  // 1/Abril/2015 JoséMario 
    
    private boolean ctrl = false;
    private String keyHit = "", strDesForPagSri;
    private boolean booFormatA4=false;
    private boolean booFormatA5=true;
    private StringBuffer sb=new StringBuffer("");
    private int intCont=0;
    public ZafVen02(Librerias.ZafParSis.ZafParSis obj) 
    {
        try
        {
            //  KeyListener listener = new MyKeyListener();
            //	addKeyListener(listener);
            //	setFocusable(true);
                KeyboardFocusManager.getCurrentKeyboardFocusManager()
  .addKeyEventDispatcher(new KeyEventDispatcher() {
      @Override
      public boolean dispatchKeyEvent(KeyEvent e) {
          
        if (e.getKeyCode()==KeyEvent.VK_CONTROL){  ctrl = true;  
       }else if (e.getKeyCode()==KeyEvent.VK_H){   
        keyHit = KeyEvent.getKeyText( e.getKeyCode() );}
       else{
       sb = new StringBuffer("");
       }
                     if (ctrl) {
                        sb.append("ctrl ");
                    }
                    sb.append(keyHit);
                    //System.out.println(sb.toString());
                    ctrl = false;
                    keyHit = "";
                    if (intCont==0) {
                        if (!sb.toString().startsWith("c")) {
                        sb = new StringBuffer("");    
                        }
              if (sb.toString().contains("ctrl") && sb.toString().contains("H")) {
                        intCont++;
                        sb = new StringBuffer("");
                        if (booFormatA5) {
                            sb = new StringBuffer("");
                            mostrarMsg("Se cambió a formato A4.");
                            booFormatA4 = true;
                            booFormatA5 = false;
                            intCont=0;
                        } else {
                            sb = new StringBuffer("");
                            mostrarMsg("Se cambió a formato A5.");
                            booFormatA4 = false;
                            booFormatA5 = true;
                            intCont=0;
                        }
                    }
          }else{
              sb = new StringBuffer("");
                    }
                    
        //System.out.println("Got key event!");
        return false;
      }
});
                
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;
            objUti = new ZafUtil();
            this.setTitle(objZafParSis.getNombreMenu() + VERSION);
            
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;
            
            objInvItm=new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
            intCodMnu = objZafParSis.getCodigoMenu();
            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);
            
            //***********************************************************************
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtTipDocCod.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtTipDocCod.getText()));
                }
            });
            //***********************************************************************

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis); 

            intCodEmp = objZafParSis.getCodigoEmpresa();
            intCodLoc = objZafParSis.getCodigoLocal();
            
            initComponents();

            Abrir_Conexion();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */
            
            objAutPrg=new ZafAutPrg(this);
            objUltDocPrint = new UltDocPrint(objZafParSis);
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
            strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
            
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            
            //**********************
            lblfecha.setText(" ");
            //**********************

            objPerUsr=new ZafPerUsr(this.objZafParSis);

             if(!(objZafParSis.getCodigoUsuario()==1)){
               if(objZafParSis.getCodigoMenu()==14){
                if(!objPerUsr.isOpcionEnabled(2965) ) {
                       butCliRet.setVisible(false);
                   }
               }
             }
             /* ++++++++++++++++++++++++++++++++++++++++ */
             /* wmoran  */
//             if(!(objZafParSis.getCodigoUsuario()==1)){
                if(objZafParSis.getCodigoMenu()==14){
//                    if(!(objZafParSis.getCodigoUsuario()==102)){  /* 1/Abril/2015 JoséMario: Permiso para facturacion electronica en linea solo */
                        butFacEle.setVisible(true);
//                    }
                }
//             }
            /* +++++++++++++++++++++++++++++++++++++++ */
            lblfecha.setVisible(false);
            pan2.add(txtFecDoc);
            txtFecDoc.setBounds(550,10,92,20);
            txtMesGra.setVisible(false);
            
            //Nombres de los tabs
            tabCotCom.setTitleAt(0,"General");
            tabCotCom.setTitleAt(1,"Forma de Pago");
            tabCotCom.setTitleAt(2,"Otros");
            tabCotCom.setTitleAt(3,"Asiento de Diario");
            objTooBar = new mitoolbar(this);
            vecForPag = new java.util.Vector();
            vecTransporte = new java.util.Vector();
            
            dblPorIvaaux = getIva(CONN_GLO);
            
            this.getContentPane().add(objTooBar,"South");
            panAsiento.add(objAsiDia,java.awt.BorderLayout.CENTER);
            pack();

            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butCliRet);
            
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butFacEle);  /* 1/Abril/2015 JoséMario  */
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butGetXmlFac);
            
            objUti.llenarCbo_F1(this, 
                                objZafParSis.getStringConexion(), 
                                objZafParSis.getUsuarioBaseDatos(), 
                                objZafParSis.getClaveBaseDatos(),
                                "SELECT co_reg, tx_deslar FROM tbm_var where co_grp = 7 order by co_reg", 
                                cboMotivoTransporte, 
                                vecTransporte); 
            
        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
        /*
         * Listener para detectar cambios en combo de pagos
         */
           
            
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
            
            objUti.verDecimalesEnTabla(tblPag,4, intNumDec);
            addListenerCambio();
            tblPag.getModel().addTableModelListener(new LisCambioTbl());
            
            //objZafInv = new Librerias.ZafInventario.ZafInventario(this, objZafParSis);
            objZafRep = new Librerias.ZafRepInv.ZafRepInv(objZafParSis);
            objZafRep.setTipoDoc(-1);
            
            cargarTipEmp(CONN_GLO);
            FormaRetencion(CONN_GLO);
            
            intCodBodPre =  getBodPrede(CONN_GLO);
            // ObtenerNumLineas();
            
            Cerrar_Conexion();
            
            objUti.desactivarCom(this);
            
        }catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
      
     /** Creates new form zafCotCom */
    public ZafVen02(Librerias.ZafParSis.ZafParSis obj, String codemp, String codloc, String codtipdoc, String coddoc, String strCodMnu ) {
        this(obj);
        intTipConFac=1;
        Codemp=codemp;
        Codloc=codloc;
        Codtipdoc=codtipdoc;
        Coddoc=coddoc;
        
        objZafParSis.setCodigoMenu(Integer.parseInt(strCodMnu));
        intCodMnu=Integer.parseInt(strCodMnu);
        butCliRet.setVisible(false);
        butFacEle.setVisible(false);
        butGetXmlFac.setVisible(false);
    }       
    
    /** Creates new form zafCotCom */
    public ZafVen02(Librerias.ZafParSis.ZafParSis obj, int codemp, int codloc, int codtipdoc, int coddoc, int intCodMenu ) {
        this(obj);
        intTipConFac=1;
        int_Cod_Emp=codemp;
        int_Cod_Loc=codloc;
        int_Tip_Doc=codtipdoc;
        int_Cod_Doc=coddoc;
       
        objZafParSis.setCodigoMenu(intCodMenu);
        intCodMnu=intCodMenu;
        butCliRet.setVisible(false);
        butFacEle.setVisible(false);
        butGetXmlFac.setVisible(false);
    }   

    public ZafVen02(Librerias.ZafParSis.ZafParSis obj, String codemp, String codloc, String codtipdoc, String coddoc, int intCodMnu ) {
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = Ventas.ZafVen02.ZafVen02.this;

            objUti = new ZafUtil();
            this.setTitle(objZafParSis.getNombreMenu() + VERSION);

            intTipConFac=1;
            Codemp=codemp;
            Codloc=codloc;
            Codtipdoc=codtipdoc;
            Coddoc=coddoc;

            objZafParSis.setCodigoMenu( intCodMnu );
            //intCodMnu= intCodMnu;

            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;

            objInvItm=new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
            intCodMnu = objZafParSis.getCodigoMenu();
            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);

            //***********************************************************************
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtTipDocCod.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtTipDocCod.getText()));
                }
            });
            //***********************************************************************


            initComponents();

            Abrir_Conexion();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */

            objAutPrg=new ZafAutPrg(this);
            objUltDocPrint = new UltDocPrint(objZafParSis);
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
            strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);

            //**********************
            lblfecha.setText(" ");
            //**********************
            txtMesGra.setVisible(false);
            lblfecha.setVisible(false);
            pan2.add(txtFecDoc);
            txtFecDoc.setBounds(550, 10, 92, 20);

            //Nombres de los tabs
            tabCotCom.setTitleAt(0,"General");
            tabCotCom.setTitleAt(1,"Forma de Pago");
            tabCotCom.setTitleAt(2,"Otros");
            tabCotCom.setTitleAt(3,"Asiento de Diario");
            objTooBar = new mitoolbar(this);
            vecForPag = new java.util.Vector();
            vecTransporte = new java.util.Vector();

            dblPorIvaaux = getIva(CONN_GLO);

           // this.getContentPane().add(objTooBar,"South");
            panAsiento.add(objAsiDia,java.awt.BorderLayout.CENTER);
            pack();


            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
            objZafParSis.getClaveBaseDatos(), "SELECT co_reg, tx_deslar FROM tbm_var where co_grp = 7 order by co_reg", cboMotivoTransporte, vecTransporte);



        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
     
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */

            objUti.verDecimalesEnTabla(tblPag,4, intNumDec);
            addListenerCambio();
            Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblPag,0);
            tblPag.getModel().addTableModelListener(new LisCambioTbl());

            //objZafInv = new Librerias.ZafInventario.ZafInventario(this, objZafParSis);
            objZafRep = new Librerias.ZafRepInv.ZafRepInv(objZafParSis);
            objZafRep.setTipoDoc(-1);

            cargarTipEmp(CONN_GLO);
            FormaRetencion(CONN_GLO);

            intCodBodPre =  getBodPrede(CONN_GLO);
            // ObtenerNumLineas();
            butCliRet.setVisible(false);
            butFacEle.setVisible(false);
            butGetXmlFac.setVisible(false);
            
            Cerrar_Conexion();

            objUti.desactivarCom(this);

        }catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    //***************************************************************************
    
    /**
     * Constructor utilizado como HashMap para usarlo cuando se llama a la clase en forma dinamica.
     * @author TonySanginez
     * @fecha 18/May/2016
     * @param map 
     */
    public ZafVen02(HashMap map) 
    {
        Librerias.ZafParSis.ZafParSis obj;
        String codemp;
        String codloc;
        String codtipdoc; 
        String coddoc; 
        int intCodMnu; 
        try
        {
            obj        = (ZafParSis)map.get("objParSis");
            codemp     = (String)map.get("strCodEmp");
            codloc     = (String)map.get("strCodLoc");
            codtipdoc  = (String)map.get("strCodTipDoc");
            coddoc     = (String)map.get("strCodDoc");
            intCodMnu  = (Integer)map.get("intCodMnu");
            
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = Ventas.ZafVen02.ZafVen02.this;

            objUti = new ZafUtil();
            this.setTitle(objZafParSis.getNombreMenu() + VERSION);

            intTipConFac=1;
            Codemp=codemp;
            Codloc=codloc;
            Codtipdoc=codtipdoc;
            Coddoc=coddoc;

            objZafParSis.setCodigoMenu( intCodMnu );
            //intCodMnu= intCodMnu;

            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;

            objInvItm=new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
            intCodMnu = objZafParSis.getCodigoMenu();
            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objZafParSis);

            //***********************************************************************
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtTipDocCod.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtTipDocCod.getText()));
                }
            });
            //***********************************************************************


            initComponents();

            Abrir_Conexion();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */

            objAutPrg=new ZafAutPrg(this);
            objUltDocPrint = new UltDocPrint(objZafParSis);
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
            strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);

            //**********************
            lblfecha.setText(" ");
            //**********************
            txtMesGra.setVisible(false);
            lblfecha.setVisible(false);
            pan2.add(txtFecDoc);
            txtFecDoc.setBounds(550, 10, 92, 20);

            //Nombres de los tabs
            tabCotCom.setTitleAt(0,"General");
            tabCotCom.setTitleAt(1,"Forma de Pago");
            tabCotCom.setTitleAt(2,"Otros");
            tabCotCom.setTitleAt(3,"Asiento de Diario");
            objTooBar = new mitoolbar(this);
            vecForPag = new java.util.Vector();
            vecTransporte = new java.util.Vector();

            dblPorIvaaux = getIva(CONN_GLO);

           // this.getContentPane().add(objTooBar,"South");
            panAsiento.add(objAsiDia,java.awt.BorderLayout.CENTER);
            pack();


            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
            objZafParSis.getClaveBaseDatos(), "SELECT co_reg, tx_deslar FROM tbm_var where co_grp = 7 order by co_reg", cboMotivoTransporte, vecTransporte);



            /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
             NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
     
            /* Metodo para agregar o eliminar lineas con enter y con escape
            */

            objUti.verDecimalesEnTabla(tblPag,4, intNumDec);
            addListenerCambio();
            Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblPag,0);
            tblPag.getModel().addTableModelListener(new LisCambioTbl());

            //objZafInv = new Librerias.ZafInventario.ZafInventario(this, objZafParSis);
            objZafRep = new Librerias.ZafRepInv.ZafRepInv(objZafParSis);
            objZafRep.setTipoDoc(-1);

            cargarTipEmp(CONN_GLO);
            FormaRetencion(CONN_GLO);

            intCodBodPre =  getBodPrede(CONN_GLO);
            // ObtenerNumLineas();
            butCliRet.setVisible(false);
            butFacEle.setVisible(false);
            butGetXmlFac.setVisible(false);

            Cerrar_Conexion();

            objUti.desactivarCom(this);

        }
        catch (CloneNotSupportedException e){     objUti.mostrarMsgErr_F1(this, e);     }
    }
    
    //***************************************************************************
    
    private void Abrir_Conexion(){
        try{
            CONN_GLO=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    private void Cerrar_Conexion(){
        try{
            CONN_GLO.close();
            CONN_GLO=null;
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    public boolean Abrir_Conexion_Remota(){
        boolean blnres=false;
        try{
            int intIndEmp=INTCODREGCEN;
            if(INTCODREGCEN!=0){
                con_remota=DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
                con_remota.setAutoCommit(false);
            }
            blnres=true;
            
        }
        catch (java.sql.SQLException e) {
            mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            return false;
        }
        return blnres;
    }
    
    public void mostrarMsg(String strMsg) {
        //JOptionPane oppMsg=new JOptionPane();
        //String strTit;
        //strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.OK_OPTION);
    }
    
    //**************************** CLASE PARA DARLE COLOR AL FONDO Y COLOR AL TEXTO CUANO HAY PROBLEMAS DE PRECIO *****////////
    private class RenderDecimales extends JLabel implements TableCellRenderer {
        int intNumDecimales = 0;
        public RenderDecimales(int intNumDecimales) {
            this.intNumDecimales=intNumDecimales ;
            setHorizontalAlignment(JLabel.RIGHT);
            setOpaque(true);
            setBackground(new Color(255, 255, 255));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            double dblSubPro = Double.parseDouble( (value == null)?"0":""+value);
            ZafUtil objutil = new ZafUtil();
            if(isSelected){
                setBorder(new LineBorder(UIManager.getDefaults().getColor("Button.focus"), 1));
            }else{
                setBorder(null);
            }
            
            this.setText("" + objutil.redondeo(dblSubPro, intNumDecimales));
            this.setFont(new java.awt.Font("SansSerif", 0, 11));
            this.setOpaque(true);
            setBackground(new Color(201,223,245));
            setForeground(new Color(0, 0, 0));
            
            // this.setOpaque(false);
            if(tblDat.getValueAt(row, INT_TBL_BLNPRE)!=null){
                if((tblDat.getValueAt(row, INT_TBL_BLNPRE).toString().equals("true"))){
                    this.setOpaque(true);
                    setBackground(new Color(0, 0, 0));
                    setForeground(new Color(255,255,255));
                    setFont(new Font("MS Sans Serif", 1, 18));
                }
            }
            return this;
        }
    }
    
    //**********************************************************************************************
    
    private boolean FormaRetencion(java.sql.Connection conTmp){
        boolean blnRes=false;
        java.sql.Statement stmTmp;
        java.sql.ResultSet rst;
        try {
            if (conTmp!=null) {
                stmTmp = conTmp.createStatement();
                String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND tx_tipmot in ('B','S','T') ";
                rst=stmTmp.executeQuery(sql);
                while(rst.next()){
                    if(rst.getString("tx_tipmot").equals("B")) intCodMotBien= rst.getInt("co_mot");
                    else if(rst.getString("tx_tipmot").equals("S")) intCodMotServ= rst.getInt("co_mot");
                    else if(rst.getString("tx_tipmot").equals("T")) intCodMotTran= rst.getInt("co_mot");
                    blnRes=true;
                }
                
                rst.close();
                stmTmp.close();
                rst=null;
                stmTmp=null;
            }
        }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        return blnRes;
    }
    
    
    
    
    
    private void cargarTipEmp(java.sql.Connection conTmp){
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        try{
            if (conTmp!=null){
                stmTipEmp=conTmp.createStatement();
                
                sSql="select b.co_tipper,  b.tx_descor from tbm_emp as a " +
                " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
                " where a.co_emp="+objZafParSis.getCodigoEmpresa();
                
                rstEmp = stmTipEmp.executeQuery(sSql);
                if(rstEmp.next()){
                    strTipPer_emp=rstEmp.getString("tx_descor");
                    intCodTipPerEmp = rstEmp.getInt("co_tipper");
                } rstEmp.close();
                stmTipEmp.close();
                stmTipEmp = null;
                rstEmp = null;
            }
        }catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
    
    private int getBodPrede(java.sql.Connection conTmp){
        java.sql.Statement stmInv;   //Statement para el recosteo
        java.sql.ResultSet rstInv;   //Resultset que tendra los datos de la existencia y el valor de la Existencia
        int intBodPre = 0;
        String sSQLBod;
        try{
            if (conTmp!=null){
                stmInv=conTmp.createStatement();
                
                sSQLBod=  " select co_bod   " +
                " from tbr_bodloc " +
                " where           " +
                " co_emp =        " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc =        " + objZafParSis.getCodigoLocal()   + " and " +
                " st_reg ='P'";
                
                rstInv=stmInv.executeQuery(sSQLBod);
                if (rstInv.next())
                    intBodPre = rstInv.getInt("co_bod");
                
                rstInv.close();
                stmInv.close();
                rstInv = null;
                stmInv = null;
            }
        }
        catch(java.sql.SQLException Evt)  {  return -1;   }
        catch(Exception Evt) {  return -1;  }
        return intBodPre;
    }
    
    
    public void Configura_ventana_consulta(){
        configurarVenConProducto();
        configurarVenConClientes();   //**********************
        configurarVenConVendedor();  //************************
        configurarVenConTipDoc();
        
        Configurar_tabla();
        configurarTablaPago();        
        
        configurarVentanaConsultaFormaPago();
        configurarVentanaConsultaTarjetaCredito();
        configurarVentanaConsultaTipoCredito();
        configurarVentanaConsultaEmisor();
        configurarVentanaConsultaMesesCredito();
        deshabilitarCamposTarCre();                

        if(intTipConFac==1){            
            consultar2(Codemp, Codloc, Codtipdoc, Coddoc);            
            objTooBar.cargarTipoDoc();            
        }          
        
    }

private boolean configurarTablaPago(){
 boolean blnRes=false;
 try{
      Vector vecCabPAg=new Vector();    //Almacena cabeceras
      vecCabPAg.clear();
      vecCabPAg.add(INT_TBL_PAGLIN,"");
      vecCabPAg.add(INT_TBL_PAGCRE,"Días.Crédito");
      vecCabPAg.add(INT_TBL_PAGFEC,"Fec.Vencimiento");
      vecCabPAg.add(INT_TBL_PAGRET,"%Retención");
      vecCabPAg.add(INT_TBL_PAGMON,"Monto");
      vecCabPAg.add(INT_TBL_PAGGRA,"Días.Gracia");
      vecCabPAg.add(INT_TBL_PAGCOD,"Cod.Ret");
      vecCabPAg.add(INT_TBL_PAGSOP,"Tx_Sop");
      objTblModPag=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
      objTblModPag.setHeader(vecCabPAg);

      tblPag.setModel(objTblModPag);
      tblPag.setRowSelectionAllowed(true);
      tblPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      ZafColNumerada zafColNumerada = new ZafColNumerada(tblPag,INT_TBL_PAGLIN);

      objTblModPag.setColumnDataType(INT_TBL_PAGCRE, ZafTblMod.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGRET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGMON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
      objTblModPag.setColumnDataType(INT_TBL_PAGGRA, ZafTblMod.INT_COL_DBL, new Integer(0), null);


      tblPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
      javax.swing.table.TableColumnModel tcmAux=tblPag.getColumnModel();
      //Configurar JTable: Establecer el ancho de las columnas.
      //Tamaño de las celdas
      tcmAux.getColumn(INT_TBL_PAGLIN).setPreferredWidth(25);
      tcmAux.getColumn(INT_TBL_PAGCRE).setPreferredWidth(90);
      tcmAux.getColumn(INT_TBL_PAGFEC).setPreferredWidth(100);
      tcmAux.getColumn(INT_TBL_PAGRET).setPreferredWidth(90);
      tcmAux.getColumn(INT_TBL_PAGMON).setPreferredWidth(90);
      tcmAux.getColumn(INT_TBL_PAGGRA).setPreferredWidth(90);
      tcmAux.getColumn(INT_TBL_PAGCOD).setPreferredWidth(60);
      tcmAux.getColumn(INT_TBL_PAGSOP).setPreferredWidth(60);

      Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLblPag=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

      objTblCelRenLblPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
      objTblCelRenLblPag.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
      objTblCelRenLblPag.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
      tcmAux.getColumn(INT_TBL_PAGMON).setCellRenderer(objTblCelRenLblPag);
      tcmAux.getColumn(INT_TBL_PAGGRA).setCellRenderer(objTblCelRenLblPag);
      tcmAux.getColumn(INT_TBL_PAGRET).setCellRenderer(objTblCelRenLblPag);

      objTblCelRenLblPag=null;

      tblPag.getTableHeader().setReorderingAllowed(false);
      tcmAux=null;

  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  return blnRes;
}
    
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try 
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias");
            arlAli.add("Descripción");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("85");
            arlAncCol.add("105");
            arlAncCol.add("350");
            
            //Armar la sentencia SQL.  
            String Str_Sql="";                       
            
            if(objZafParSis.getCodigoUsuario()==1)
            {
                Str_Sql=" SELECT distinct a.co_tipdoc,a.tx_descor,a.tx_deslar "
                       +" FROM tbr_tipdocprg as b "
                       +" LEFT OUTER JOIN tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" 
                       +" WHERE b.co_emp = " + objZafParSis.getCodigoEmpresa() 
                       +" AND b.co_loc = " + objZafParSis.getCodigoLocal() 
                       +" AND b.co_mnu = " + objZafParSis.getCodigoMenu()
                       +" ORDER BY a.tx_descor ";
            }
            else 
            {     
                Str_Sql=" SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "
                       +" FROM tbr_tipDocUsr AS a1 "
                       +" INNER JOIN tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"
                       +" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa()
                       +" AND a1.co_loc=" + objZafParSis.getCodigoLocal()
                       +" AND a1.co_mnu=" + objZafParSis.getCodigoMenu()
                       +" AND a1.co_usr=" + objZafParSis.getCodigoUsuario()
                       +" ORDER BY a.tx_descor ";
            } 
            
            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
                
    private boolean configurarVenConVendedor() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL="";
            strSQL+="select a.co_usr,a.tx_nom from tbm_usr as a order by a.tx_nom";
            
            objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    //
    //       private boolean configurarVenConClientes()
    //    {
    //        boolean blnRes=true;
    //        try
    //        {
    //            ArrayList arlCam=new ArrayList();
    //            arlCam.add("a.co_cli");
    //            arlCam.add("a.tx_nom");
    //            arlCam.add("a.tx_dir");
    //            arlCam.add("a.tx_tel");
    //            arlCam.add("a.tx_ide");
    //            arlCam.add("a.tx_tipper");
    //            arlCam.add("a.co_tipper");
    //
    ////            arlCam.add("a.tx_percon");
    //
    //            ArrayList arlAli=new ArrayList();
    //            arlAli.add("Código");
    //            arlAli.add("Nom.Cli.");
    //            arlAli.add("Dirección");
    //            arlAli.add("Telefono");
    //            arlAli.add("RUC/CI");
    //            arlAli.add("TIPO");
    //            arlAli.add("CO_TIPRET");
    //
    ////            arlAli.add("tx_percon");
    //
    //
    //            ArrayList arlAncCol=new ArrayList();
    //            arlAncCol.add("50");
    //            arlAncCol.add("165");
    //            arlAncCol.add("160");
    //            arlAncCol.add("80");
    //            arlAncCol.add("80");
    //            arlAncCol.add("20");
    //            arlAncCol.add("20");
    //
    //            //Armar la sentencia SQL.
    //             String  strSQL="";
    //             strSQL+="select co_cli,tx_nom,tx_dir,tx_tel,tx_ide,tx_tipper, co_tipper  from tbm_cli as a where a.co_emp = " + objZafParSis.getCodigoEmpresa() +" and  a.st_reg='A' and  a.st_cli='S' order by a.tx_nom";
    //
    //
    //            int intColOcu[]=new int[2];
    //            intColOcu[0]=6;
    //            intColOcu[1]=7;
    //
    //            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
    //            arlCam=null;
    //            arlAli=null;
    //            arlAncCol=null;
    //
    //        }
    //        catch (Exception e)
    //        {
    //            blnRes=false;
    //            objUti.mostrarMsgErr_F1(this, e);
    //        }
    //        return blnRes;
    //    }
    //
                   
    private boolean configurarVenConClientes() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_tipper");
            arlCam.add("a.nd_maxdes");
            arlCam.add("a.nd_maruti");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.co_ven");
            arlCam.add("a.vendedor");
            arlCam.add("a.co_tipper");
            arlCam.add("a.st_ivaven");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("TIPO");
            arlAli.add("Desc");
            arlAli.add("Utili");
            arlAli.add("Ciudad");
            arlAli.add("co_vende");
            arlAli.add("Vendedor");
            arlAli.add("CoDTipPer");
            arlAli.add("Iva.Ven");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("40");
            arlAncCol.add("20");
            arlAncCol.add("20");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            
            String strCodEmp="";
            if(objZafParSis.getCodigoEmpresa()==1)
              strCodEmp="603, 2600, 1039";
            if(objZafParSis.getCodigoEmpresa()==2)
              strCodEmp="2854, 2105, 789";
            if(objZafParSis.getCodigoEmpresa()==3)
              strCodEmp="2858, 453, 832";
            if(objZafParSis.getCodigoEmpresa()==4)
              strCodEmp="3117,  498,  2294";
            
                
             strSQL="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper, st_ivaven " +
           // "  ne_tipforpag,  nd_pes , ne_diagra , co_forpag , tx_des  " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
            " , b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag , b.tx_des, a.st_ivaven  FROM tbr_cliloc AS a1 " +
            " INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "+
            " WHERE a1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc ="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'  and a.st_cli='S' order by a.tx_nom  " +
            ") AS a " +
                 //  " WHERE  a.co_cli IN ("+strCodEmp+") "+
             "";
            
            int intColOcu[]=new int[7];
            intColOcu[0]=6;
            intColOcu[1]=7;
            intColOcu[2]=8;
            intColOcu[3]=9;
            intColOcu[4]=10;
            intColOcu[5]=11;
            intColOcu[6]=12;
            
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private boolean configurarVenConProducto() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.tx_codAlt2");
            arlCam.add("a7.tx_nomItm");
            arlCam.add("a7.nd_stkAct");
            arlCam.add("a7.nd_stkActcen");
            arlCam.add("a7.nd_preVta1");
            arlCam.add("a7.st_ivaVen");
            arlCam.add("a7.tx_descor");
            arlCam.add("a7.st_ser");
            arlCam.add("a7.isterL");
            arlCam.add("a7.co_itm");
            arlCam.add("a7.nd_maruti");
            arlCam.add("a7.proconf");
            arlCam.add("a7.st_permodnomitmven");
            arlCam.add("a7.co_ctaegr");
            
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Tuv.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Nombre");
            arlAli.add("Stock");
            arlAli.add("StkCen");
            arlAli.add("Precio");
            arlAli.add("Iva.");
            arlAli.add("Uni.");
            arlAli.add("Ser.");
            arlAli.add("st_Ter");
            arlAli.add("Cód.Sis.");
            arlAli.add("Mar.Uti.");
            arlAli.add("I/E.Bod.Fis.");
            arlAli.add("Mod.Itm");
            arlAli.add("Cód.Cta.Egr.");
            
            ArrayList arlAncCol=new ArrayList();
            
            
            
            String Str_Sql="";
            if(objZafParSis.getCodigoEmpresa()==2 || objZafParSis.getCodigoEmpresa()==4){
                
                Str_Sql = objInvItm.getSqlInvVen();
                
                System.out.println("-->"+Str_Sql  );

                arlAncCol.add("75");
                arlAncCol.add("75");
                arlAncCol.add("210");
                arlAncCol.add("50");
                arlAncCol.add("50");
                arlAncCol.add("50");
                arlAncCol.add("25");
                arlAncCol.add("33");
                arlAncCol.add("33");
                arlAncCol.add("47");
                arlAncCol.add("40");
                arlAncCol.add("40");
                
                int intColOcu[] =new int[6];
                intColOcu[0]=8;
                intColOcu[1]=9;
                intColOcu[2]=10;
                intColOcu[3]=11;
                intColOcu[4]=12;
                intColOcu[5]=15;
                
                objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
                
                
            } else {
                
                Str_Sql = objInvItm.getSqlInvVen();
                
                
                arlAncCol.add("80");
                arlAncCol.add("75");
                arlAncCol.add("200");
                arlAncCol.add("55");
                arlAncCol.add("50");
                arlAncCol.add("55");
                arlAncCol.add("30");
                arlAncCol.add("46");
                arlAncCol.add("33");
                arlAncCol.add("47");
                arlAncCol.add("40");
                arlAncCol.add("40");
                
                int intColOcu[]=new int[6];
                intColOcu[0]=5;
                intColOcu[1]=9;
                intColOcu[2]=10;
                intColOcu[3]=11;
                intColOcu[4]=12;
                intColOcu[5]=15;
                
                objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
                
            }
            
            
            //Ocultar columnas.
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenCon2.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(7, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    
    //     public void  LlamrVentana(String codalt,int codcli) {
    //        Ventas.ZafVen01.ZafVen01_01 obj1 = new  Ventas.ZafVen01.ZafVen01_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
    //           String  strSQL="";
    //           int intCodMod=1;
    //               strSQL+="select distinct(b.co_doc),a.ne_numcot,a.ne_numdoc,a.tx_nomcli,a.fe_doc,b.co_tipdoc, ( b.nd_can * -1 ) as can, b.nd_preuni,b.nd_pordes";
    //               strSQL+=" from tbm_detmovinv as b,tbm_cabmovinv as a,tbm_cabtipdoc as x";
    //               strSQL+=" where";
    //               strSQL+=" x.ne_mod="+ intCodMod +" and b.co_tipdoc = x.co_tipdoc  and ";
    //               strSQL+=" b.tx_codalt= '"+ codalt +"' and b.co_emp="+ objZafParSis.getCodigoEmpresa() +" and b.co_loc= "+ objZafParSis.getCodigoLocal();
    //               strSQL+=" and a.co_doc = b.co_doc and a.co_emp = b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc = b.co_tipdoc ";
    //               strSQL+=" and a.st_reg IN ('A','R','D','C','F') ";
    //
    //               if(codcli != 0 )
    //                   strSQL+=" and  a.co_cli = "+ codcli;
    //
    //               strSQL+= " order by a.fe_doc";
    //
    //        obj1.cargaTexto(strSQL,codcli);
    //        obj1.show();
    //        obj1.dispose();
    //        obj1=null;
    //     }
    //
    
    private boolean configurarVentanaConsultaFormaPago(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_forpag");
            arlCam.add("tx_des");
            arlCam.add("ne_tipforpag");
            arlCam.add("nd_pes");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.For.Pag");
            arlAli.add("Descripcion");
            arlAli.add("Tip.Pag");
            arlAli.add("Tip.Pes");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("260");
            arlAncCol.add("0");
            arlAncCol.add("0");
            
            int intColOcu[] =new int[2];
            intColOcu[0]=3;
            intColOcu[1]=4;

            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="SELECT co_forpag, tx_des, ne_tipforpag, nd_pes FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " and st_reg not in ('I','E') order by co_forpag";
            
            objVenConForPag=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    private boolean mostrarVenConFormaPago(int intTipBus){
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConForPag.setCampoBusqueda(0);
                    objVenConForPag.setVisible(true);
                    if (objVenConForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodForPag.setText(objVenConForPag.getValueAt(1));
                        txtNomForPag.setText(objVenConForPag.getValueAt(2));
                        txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                        intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                        //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                        dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                        if (intTipForPag2==4){
                            habilitarCamposTarCre();
                        }else{
                            deshabilitarCamposTarCre();
                            calculaDescuento();
                            CalculoPago();                            
                        }                                   
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConForPag.buscar("co_forpag", txtCodigoFormaPago.getText())) {
                        txtCodForPag.setText(objVenConForPag.getValueAt(1));
                        txtNomForPag.setText(objVenConForPag.getValueAt(2));
                        txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                        intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                        //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                        dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                        if (intTipForPag2==4){
                            habilitarCamposTarCre();
                        }else{
                            deshabilitarCamposTarCre(); 
                            calculaDescuento(); 
                            CalculoPago();            
                        }                                   
                    }else{
                        objVenConForPag.setCampoBusqueda(0);
                        objVenConForPag.setCriterio1(11);
                        objVenConForPag.cargarDatos();
                        objVenConForPag.setVisible(true);
                        if (objVenConForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodForPag.setText(objVenConForPag.getValueAt(1) );
                            txtNomForPag.setText(objVenConForPag.getValueAt(2) );
                            txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1) );
                            intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                            //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                            dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                            if (intTipForPag2==4){
                                habilitarCamposTarCre();
                            }else{
                                deshabilitarCamposTarCre(); 
                                calculaDescuento(); 
                                CalculoPago();            
                            }                                   
                        }else 
                            txtCodForPag.setText(strCodForPag);
                        
                    }
                    break;
                case 2: 
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (objVenConForPag.buscar("tx_des", txtNomForPag.getText())) {
                        txtCodForPag.setText(objVenConForPag.getValueAt(1));
                        txtNomForPag.setText(objVenConForPag.getValueAt(2));
                        txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                        intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                        //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                        dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                        if (intTipForPag2==4){
                            habilitarCamposTarCre();
                        }else{
                            deshabilitarCamposTarCre(); 
                            calculaDescuento(); 
                            CalculoPago();            
                        }                                   
                    }else {
                        objVenConForPag.setCampoBusqueda(1);
                        objVenConForPag.setCriterio1(11);
                        objVenConForPag.cargarDatos();
                        objVenConForPag.setVisible(true);
                        if (objVenConForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodForPag.setText(objVenConForPag.getValueAt(1));
                            txtNomForPag.setText(objVenConForPag.getValueAt(2));
                            txtCodigoFormaPago.setText(objVenConForPag.getValueAt(1));
                            intTipForPag2 = Integer.parseInt(objVenConForPag.getValueAt(3));
                            //dblPes2 = Double.parseDouble(objVenConForPag.getValueAt(4));
                            dblPes2 = objUti.parseDouble(objVenConForPag.getValueAt(4) );
                            if (intTipForPag2==4){
                                habilitarCamposTarCre();
                            }else{
                                deshabilitarCamposTarCre(); 
                                calculaDescuento(); 
                                CalculoPago();            
                            }                                   
                        }else 
                            txtNomForPag.setText(strDesLarForPag);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVentanaConsultaTarjetaCredito(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_tarcre");
            arlCam.add("tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Tar.Cre");
            arlAli.add("Tar.Cre");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("260");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select co_tarcre, tx_deslar from tbm_tarCre where st_reg != 'I' ";
            
            objVenConTarCre=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }
    
    private boolean configurarVentanaConsultaTipoCredito(){
        boolean blnRes=false;
        try
        {
            //System.out.println("ZafVen01.configurarVentanaConsultaTarjetaCredito");
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_redTarCre");
            arlCam.add("tx_nomRedTarCre");
            arlCam.add("co_tipCre");
            arlCam.add("tx_desLar"); 
            arlCam.add("tx_emiTarCre");
            arlCam.add("nd_porDesVen");
            arlCam.add("ne_mesGra");
            arlCam.add("nd_porCom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Red.Tar.Cré.");// no se muestra 
            arlAli.add("Nom.Red.Tar.Cré."); 
            arlAli.add("Cod.Tip.Cré.");  //3
            arlAli.add("Tip.Cré."); //4
            arlAli.add("Emi.Tar.Cré."); //5
            arlAli.add("% Des.");//6
            arlAli.add("Mes.Gra.");//7
            arlAli.add("Por.Com.");//8
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("40");
            arlAncCol.add("60");
            arlAncCol.add("40");
            arlAncCol.add("300");
            arlAncCol.add("300");
            arlAncCol.add("40");
            arlAncCol.add("40");
            arlAncCol.add("40");
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+=" SELECT a1.co_redTarCre,a2.tx_desLar as tx_nomRedTarCre ,a1.co_tipCre,a1.tx_desLar,a1.tx_emiTarCre, a1.tx_tipCre,a1.nd_porDesVen, a1.ne_mesGra, a1.nd_porCom ";
            strSQL+=" FROM tbm_tipCreRedTarCre as a1";
            strSQL+=" INNER JOIN tbm_redTarCre as a2 ON (a1.co_redTarCre=a2.co_redTarCre)";
            strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A' AND a2.st_reg='A'";
            strSQL+=" ";
            //Ocultar columnas.
            int intColOcu[] =new int[4];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=7;
            intColOcu[3]=8;
            objVenConTarCre=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
           
            //Configurar columnas.
            objVenConTarCre.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            objVenConTarCre.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
            objVenConTarCre.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    private boolean mostrarVenConTipoCredito(int intTipBus){
      boolean blnRes=true;
        try {
            //System.out.println("ZafVen01.mostrarVenConTarjetaCredito");
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    objVenConTarCre.setCampoBusqueda(0);
                    objVenConTarCre.setVisible(true);
                    if (objVenConTarCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodTarCre.setText(objVenConTarCre.getValueAt(3));
                        txtNomTarCre.setText(objVenConTarCre.getValueAt(4));
                        txtNomEmiTar.setText(objVenConTarCre.getValueAt(5));
                        txtMesGra.setText(objVenConTarCre.getValueAt(7));   /// MESES DE GRACIA
                        dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTarCre.getValueAt(6) ), 2 );
                        txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) ); 
                        txtPorComTarCre.setText(objVenConTarCre.getValueAt(8) );
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (objVenConTarCre.buscar("co_tipCre", txtCodTarCre.getText())) {
                        txtCodTarCre.setText(objVenConTarCre.getValueAt(3));
                        txtNomTarCre.setText(objVenConTarCre.getValueAt(4));
                        txtNomEmiTar.setText(objVenConTarCre.getValueAt(5));
                        txtMesGra.setText(objVenConTarCre.getValueAt(7));
                        dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTarCre.getValueAt(6) ), 2 );
                        txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) ); 
                        txtPorComTarCre.setText(objVenConTarCre.getValueAt(8) );
                    }else{
                        objVenConTarCre.setCampoBusqueda(0);
                        objVenConTarCre.setCriterio1(11);
                        objVenConTarCre.cargarDatos();
                        objVenConTarCre.setVisible(true);
                        if (objVenConTarCre.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodTarCre.setText(objVenConTarCre.getValueAt(3));
                        txtNomTarCre.setText(objVenConTarCre.getValueAt(4));
                        txtNomEmiTar.setText(objVenConTarCre.getValueAt(5));
                        txtMesGra.setText(objVenConTarCre.getValueAt(7));
                        dblPorDesTarCre=objUti.redondear(objUti.parseDouble(objVenConTarCre.getValueAt(6) ), 2 );
                        txtPorDesVenTarCre.setText(String.valueOf(dblPorDesTarCre) ); 
                        txtPorComTarCre.setText(objVenConTarCre.getValueAt(8) );
                        }else 
                            txtCodTarCre.setText(strCodTarCre);                        
                    }
                    break;
            }
                      
            
                calculaDescuento();
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVentanaConsultaEmisor(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_emitarcre");
            arlCam.add("tx_deslar");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Emi.Tar.Cre");
            arlAli.add("Emi.Tar.Cre");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("200");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select co_emitarcre, tx_deslar " +
                              " from( " +
                              " select co_reg as co_emitarcre, tx_deslar " +
                              " from tbm_var " + 
                              " where co_grp=8 and st_reg != 'I' " + 
                              " union all " + 
                              " select a.co_emitarcre, b.tx_deslar " + 
                              " from tbr_emiRedTarCre as a " +
                              " inner join tbm_var as b on (a.co_emitarcre=b.co_reg) " +
                              " where a.co_redtarcre in (1, 2) " +
                              " and b.st_reg != 'I' " + 
                              " ) as x " +
                              " group by co_emitarcre, tx_deslar " + 
                              " order by 1 ";
            
            objVenConEmisor=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

    
    private boolean configurarVentanaConsultaMesesCredito(){
        boolean blnRes=false;
        try{
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_mescre");
            arlCam.add("tx_desmescre");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Mes.Cre");
            arlAli.add("Des.Mes.Cre");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("100");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL+="select 3 as co_mescre, '3 Meses' :: text as tx_desmescre union all "
                    + " select 6 as co_mescre, '6 Meses' :: text as tx_desmescre union all "
                    + " select 9 as co_mescre, '9 Meses' :: text as tx_desmescre union all "
                    + " select 12 as co_mescre, '12 Meses' :: text as tx_desmescre union all "
                    + " select 15 as co_mescre, '15 Meses' :: text as tx_desmescre union all "
                    + " select 18 as co_mescre, '18 Meses' :: text as tx_desmescre union all "
                    + " select 24 as co_mescre, '24 Meses' :: text as tx_desmescre union all "
                    + " select 36 as co_mescre, '36 Meses' :: text as tx_desmescre ";
            
            objVenConMesCre=new ZafVenCon(JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        } catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }

  
    
    private void habilitarCamposTarCre(){
        this.txtCodTarCre.setEnabled(true);
        this.txtNomTarCre.setEnabled(true);
//        this.butConTarCre.setEnabled(true);
        
        this.txtCodTarCre.setEnabled(true);
        this.txtNomTarCre.setEnabled(true);
        this.butConTipCre.setEnabled(true);
        
//        this.txtCodEmiTar.setEnabled(true);
        this.txtNomEmiTar.setEnabled(true);
//        this.butConEmiTar.setEnabled(true);
        
//        this.txtCodMesCre.setEnabled(true);
//        this.txtNomMesCre.setEnabled(true);
//        this.butConMesCre.setEnabled(true);
        
        //this.txtCodRedTarCre.setText("");
        //this.txtTipCre.setText("");
        //this.txtPorComTarCre.setText("");
        //this.txtPorDesVenTarCre.setText("");
        //this.txtMesGra.setEnabled(true);        
    }
    
    private void deshabilitarCamposTarCre(){
        this.txtCodTarCre.setEnabled(false); txtCodTarCre.setText("");
        this.txtNomTarCre.setEnabled(false); txtNomTarCre.setText("");

        
        this.txtCodTarCre.setEnabled(false); txtCodTarCre.setText("");
        this.txtNomTarCre.setEnabled(false); txtNomTarCre.setText("");
        this.butConTipCre.setEnabled(false);
        

        this.txtNomEmiTar.setEnabled(false); txtNomEmiTar.setText("");
        
        
  
        
        this.txtCodRedTarCre.setText("");
        this.txtTipCre.setText("");
        
        this.txtMesGra.setEnabled(false); txtMesGra.setText(""); 
        
        this.dblPorDesTarCre=0;
        this.txtPorComTarCre.setText("");
        this.txtPorDesVenTarCre.setText("");
    }
    
    public void calculaDescuento(){        
        double dblPorDesCli=Glo_dlbMaxDes;
        if (intTipForPag2 == 4){
            for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                    tblDat.setValueAt(""+objUti.redondear(dblPorDesTarCre, 2) , i, INT_TBL_PORDES);
                }
            }            
//        }else{
//            for(int i=0; i<tblDat.getRowCount();i++){
//                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
//                    tblDat.setValueAt(""+objUti.redondear(dblPorDesCli, 2) , i, INT_TBL_PORDES);
//                }
//            }            
        }
        
        calculaSubTotTodLosItm();
    }           
    
    private boolean Configurar_tabla() {
        boolean blnRes=false;
        try{
            
            //dblPorIvaaux = getIva();
            lblIva.setText("IVA " + dblPorIva + "%");
            
            //Configurar JTable: Establecer el modelo.
            Vector vecDat=new Vector();    //Almacena los datos
            Vector vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_ITMALT,"Cod. Item");
            vecCab.add(INT_TBL_BUTITM,"");
            vecCab.add(INT_TBL_DESITM,"Descripcion");
            vecCab.add(INT_TBL_UNIDAD,"Unidad");
            vecCab.add(INT_TBL_CODBOD,"Bodega");
            vecCab.add(INT_TBL_BUTBOD,"");
            vecCab.add(INT_TBL_CANMOV,"Cantidad");
            vecCab.add(INT_TBL_STKBOD,"Stock Con");
            vecCab.add(INT_TBL_ULTCOS, "Ult. Costo");
            vecCab.add(INT_TBL_PREUNI,"Precio");
            vecCab.add(INT_TBL_UTILIDAD,"Utilidad %");
            vecCab.add(INT_TBL_PREUNI2, "Precio Unitario");
            vecCab.add(INT_TBL_PORDES,"%Desc");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ESTADO,"");
            vecCab.add(INT_TBL_IVATXT,"");
            vecCab.add(INT_TBL_BLNPRE,"");
            vecCab.add(INT_TBL_CANORI,"Can.Ori");
            vecCab.add(INT_TBL_BODORI,"Bod.Ori");
            vecCab.add(INT_TBL_CODORI,"Cod.Ori");
            vecCab.add(INT_TBL_ITMALT2,"ItmAlt2");
            vecCab.add(INT_TBL_ITMSER,"Itm.SER.");
            vecCab.add(INT_TBL_ITMTER," isTerL");
            vecCab.add(INT_TBL_CODLOCREL ," CODLOC");
            vecCab.add(INT_TBL_CODTIPREL ," CODTIPDOC");
            vecCab.add(INT_TBL_CODDOCREL ," CODDOC");
            vecCab.add(INT_TBL_CODREGREL ," CODREG");
            vecCab.add(INT_TBL_CODEMPREL ," CODEMP");
            vecCab.add(INT_TBL_CODITMACT , " CodItmAct ");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod"); 
            vecCab.add(INT_TBL_CODCTAEGR, "Cód.Cta.Egr."); 
            
            
            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_ULTCOS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI2, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANORI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            ArrayList arlAux=new ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
            arlAux=new ArrayList();
            arlAux.add("" + INT_TBL_CODORI);
            arlAux.add("" + INT_TBL_BODORI);
            arlAux.add("" + INT_TBL_CANORI);
            arlAux.add("" + INT_TBL_IEBODFIS);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(55);
            
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(55);
            
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(25);
            
           // OcultarColumna(INT_TBL_CODBOD, tcmAux);
           // OcultarColumna(INT_TBL_BUTBOD, tcmAux);
            OcultarColumna(INT_TBL_CODITM, tcmAux);
            OcultarColumna(INT_TBL_IVATXT, tcmAux);
            OcultarColumna(INT_TBL_BLNPRE, tcmAux);
            OcultarColumna(INT_TBL_STKBOD, tcmAux);
            OcultarColumna(INT_TBL_ULTCOS, tcmAux);
            OcultarColumna(INT_TBL_UTILIDAD, tcmAux);
            OcultarColumna(INT_TBL_PREUNI2, tcmAux);
            OcultarColumna(INT_TBL_ESTADO, tcmAux);
            OcultarColumna(INT_TBL_CANORI, tcmAux);
            OcultarColumna(INT_TBL_BODORI, tcmAux);
            OcultarColumna(INT_TBL_CODORI, tcmAux);
            OcultarColumna(INT_TBL_ITMALT2, tcmAux);
            OcultarColumna(INT_TBL_ITMSER, tcmAux);
            OcultarColumna(INT_TBL_ITMTER, tcmAux);
            OcultarColumna(INT_TBL_CODLOCREL, tcmAux);
            OcultarColumna(INT_TBL_CODTIPREL, tcmAux);
            OcultarColumna(INT_TBL_CODDOCREL, tcmAux);
            OcultarColumna(INT_TBL_CODREGREL, tcmAux);
            OcultarColumna(INT_TBL_CODEMPREL, tcmAux);
            OcultarColumna(INT_TBL_CODITMACT, tcmAux);
            OcultarColumna(INT_TBL_CODCTAEGR, tcmAux);
            
            
            
            
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            
          /*
           * Dando tamano a la tabla de pagos
           */
            tblPag.getTableHeader().setReorderingAllowed(false);
            tblPag.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblPag.getColumnModel().getColumn(0).setResizable(false);
            tblPag.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblPag.getColumnModel().getColumn(1).setResizable(false);
            tblPag.getColumnModel().getColumn(2).setPreferredWidth(140);
            tblPag.getColumnModel().getColumn(2).setResizable(false);
            tblPag.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblPag.getColumnModel().getColumn(3).setResizable(false);
            tblPag.getColumnModel().getColumn(4).setPreferredWidth(140);
            tblPag.getColumnModel().getColumn(4).setResizable(false);
            tblPag.getColumnModel().getColumn(5).setPreferredWidth(130);
            tblPag.getColumnModel().getColumn(5).setResizable(false);
            
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            /**/            vecAux.add("" + INT_TBL_ITMALT);
            /**/            vecAux.add("" + INT_TBL_BUTITM);
            //              vecAux.add("" + INT_TBL_CODBOD);
            /**/            vecAux.add("" + INT_TBL_BUTBOD);
            vecAux.add("" + INT_TBL_CANMOV);
            vecAux.add("" + INT_TBL_PREUNI);
            vecAux.add("" + INT_TBL_PORDES);
            vecAux.add("" + INT_TBL_DESITM);
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    
                    blnChangeData = false;
                    if(tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA) != null)
                        strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA).toString();
                    
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }
                }
                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA) != null)
                        strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_BLNIVA).toString();
                    
                    if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                        tblDat.setValueAt("D", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);
                    }
                    
                    if (!tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        calculaSubtotal();
                        blnChangeData = true;
                    }
                }
            });
            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UNIDAD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_ULTCOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREUNI2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANORI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            
            //Configurar JTable: Editor de celdas.
            
            
            
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            //            int intColVen2[]=new int[8];
            //            intColVen2[0]=1;
            //            intColVen2[1]=3;
            //            intColVen2[2]=5;
            //            intColVen2[3]=2;
            //            intColVen2[4]=6;
            //            intColVen2[5]=7;
            //            intColVen2[6]=8;
            //            intColVen2[7]=10;
            //            int intColTbl2[]=new int[8];
            //            intColTbl2[0]=INT_TBL_ITMALT;
            //            intColTbl2[1]=INT_TBL_DESITM;
            //            intColTbl2[2]=INT_TBL_PREUNI;
            //            intColTbl2[3]=INT_TBL_CODITM;
            //            intColTbl2[4]=INT_TBL_IVATXT;
            //            intColTbl2[5]=INT_TBL_UNIDAD;
            //            intColTbl2[6]=INT_TBL_ITMALT2;
            //            intColTbl2[7]=INT_TBL_ITMTER;
            
            
            
            int intColVen2[]=new int[11];
            intColVen2[0]=1;
            intColVen2[1]=3;
            intColVen2[2]=6;
            intColVen2[3]=11;
            intColVen2[4]=7;
            intColVen2[5]=8;
            intColVen2[6]=2;
            intColVen2[7]=9;
            intColVen2[8]=10;
            intColVen2[9]=12;
            intColVen2[10]=15;
            
            int intColTbl2[]=new int[11];
            intColTbl2[0]=INT_TBL_ITMALT;
            intColTbl2[1]=INT_TBL_DESITM;
            intColTbl2[2]=INT_TBL_PREUNI;
            intColTbl2[3]=INT_TBL_CODITM;
            intColTbl2[4]=INT_TBL_IVATXT;
            intColTbl2[5]=INT_TBL_UNIDAD;
            intColTbl2[6]=INT_TBL_ITMALT2;
            intColTbl2[7]=INT_TBL_ITMSER;
            intColTbl2[8]=INT_TBL_ITMTER;
            intColTbl2[9]=INT_TBL_IEBODFIS;
            intColTbl2[10]=INT_TBL_CODCTAEGR;
            
            
                    
            
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco2(tblDat, objVenCon2, intColVen2, intColTbl2,objZafParSis);  //********
            tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);  //******
            objTblCelEdiTxtVcoItm.addTableEditorListener(new ZafTableAdapter() {   //******
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    blnChangeData = false;
                    
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                    else
                        strBeforeValue = "";
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }
                }
                
                
                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                        strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                    else
                        strAfterValue ="";
                    if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                        tblDat.setValueAt("D", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);
                    }
                    
                    String strValpre="0.00";
                    strValpre = tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).toString();
                    if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).equals("")) strValpre="0";
                    if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI)==null) strValpre="0";
                    tblDat.setValueAt(strValpre,intNumFil,INT_TBL_PREUNI);
                    
                    if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)
                        tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV);
                    
                    
                    if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)
                        tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES);
                    
                    
                    
                    tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);
                    
                    if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
                        if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).equals("S")) {
                            tblDat.setValueAt(true, intNumFil, INT_TBL_BLNIVA);
                            if(tblDat.getValueAt(0,INT_TBL_PORDES) != null)
                                tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
                        } else {
                            tblDat.setValueAt(false, intNumFil, INT_TBL_BLNIVA);
                            if(tblDat.getValueAt(0,INT_TBL_PORDES) != null)
                                tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
                        }
                    }
                    
                    if (!tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;
                        tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);
                        
                    }
                    
                    calculaSubtotal();
                    
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intNumFil, INT_TBL_CANMOV);
                }
            });
            
            
            
            //**************************************************************************
            // ButFndItm ObjFndItm = new ButFndItm(tblDat, INT_TBL_BUTITM);   //*****
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, objVenCon2, intColVen2, intColTbl2);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada()) {
                        eventoVenCon();
                    }
                }
            });
            //*******************************************************************************
            
            
            intColVen2=null;
            intColTbl2=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(new RenderDecimales(intNumDec) );  //*********
            
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        blnChangeData = false;
                        String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                        
                        if(strTipTer.trim().equals("S")) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        }else {
                            
                            if (tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn())!=null)
                                strBeforeValue = tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn()).toString();
                            else
                                strBeforeValue = "";
                            if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                                tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                            }}}
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    
                    String strTipTer = ((tblDat.getValueAt(intNumFil, INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_ITMTER).toString());
                    
                    if(strTipTer.trim().equals("S")) {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }else {
                        
                        tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);
                        
                        if (tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn())!=null)
                            strAfterValue = tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn()).toString();
                        else
                            strAfterValue ="";
                        if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                            tblDat.setValueAt("D", intNumFil, INT_TBL_ESTADO);
                        }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                            if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                                tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);
                        }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                            if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                                tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                        }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                            tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);
                        }
                        
                        if (tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(intNumFil,tblDat.getSelectedColumn()).equals(""))
                            tblDat.setValueAt("0",intNumFil,tblDat.getSelectedColumn());
                        
                        if (tblDat.getSelectedColumn()==INT_TBL_PORDES){
                            if(strAfterValue.equals("")) strAfterValue="0.00";
                            if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                                MensajeInf("Error! Porcentaje de Descuento ");
                                tblDat.setValueAt(strBeforeValue, intNumFil, INT_TBL_PORDES);
                            }
                            if (intTipForPag2 == 4){
                               calculaSubTot2();                          
                            }
                        }
                                                
                        //if (!tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;
                        calculaSubtotal();
                        if (intTipForPag2 == 4){
                           calculaSubTot2();                          
                        }
                        // }
                                                
                    }}
            });
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
            //Configurar JTable: Centrar columnas.
            ZafTblPopMn = new ZafTblPopMnu(tblDat);
            ZafTblPopMn.setInsertarFilaVisible(false);
            ZafTblPopMn.setInsertarFilasVisible(false);
            
            //***********************************************************
            ZafTblPopMn.addTblPopMnuListener(new ZafTblPopMnuAdapter() {
                @Override
                public void beforeClick(ZafTblPopMnuEvent evt) {
                    
                    String strTipTer = ((tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMTER)==null)?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMTER).toString());
                    int i, intFilSel[];
                    intFilSel=tblDat.getSelectedRows();
                    
                    if (ZafTblPopMn.isClickInsertarFila()) {
                        //System.out.println("beforeClick: isClickInsertarFila");
                        //Cancelar la edición cuado sea necesario.
                        if (tblDat.getSelectedRow()==1)
                            ZafTblPopMn.cancelarClick();
                    }
                    else if (ZafTblPopMn.isClickEliminarFila()) {
                        for (i=0; i<intFilSel.length; i++){
                            strTipTer = ((tblDat.getValueAt((intFilSel[i]), INT_TBL_ITMTER)==null)?"":tblDat.getValueAt((intFilSel[i]), INT_TBL_ITMTER).toString());
                            //  System.out.println(">> "+ strTipTer +" <> "+ (intFilSel[i])  +" >> "+ intFilSel.length +" <> "+ i );
                            if(strTipTer.trim().equals("S")){
                                JOptionPane.showMessageDialog(null, "No es posible eliminar la fila por que corresponde a una terminal L .");
                                ZafTblPopMn.cancelarClick();
                                break;
                            }}
                    }
                    
                    intFilSel=null;
                    
                }
                @Override
                public void afterClick(ZafTblPopMnuEvent evt) {
                    if (ZafTblPopMn.isClickInsertarFila()) {
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                        //System.out.println("afterClick: isClickInsertarFila");
                    }
                    else if (ZafTblPopMn.isClickEliminarFila()) {
                        //System.out.println("afterClick: isClickEliminarFila");
                        //  javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
                    }
                }
            });
            //***********************************************************
            ButBodEmp butBodEmp = new ButBodEmp(tblDat, INT_TBL_BUTBOD);
             
            
            blnRes=true;
        }catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;
    }
    
    
    
    public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);  // INT_TBL_INS
            
        }else{
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }
    
    
    
private class ButBodEmp extends ZafTableColBut{
 public ButBodEmp(JTable tbl, int intIdx){
 super(tbl,intIdx);
 }
 
 @Override
 public void butCLick() {
  int intNumFil = tblDat.getSelectedRow();
  if(intNumFil >= 0 ) {
    blnChangeData = false;
    String strCodItm = ((tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(intNumFil, INT_TBL_CODITM).toString());
    llamarVentanaFac(intNumFil, strCodItm);
     
  }}
}


private void llamarVentanaFac(int intCol, String strCodItm){
  String strSql="";
  
  if(!strCodItm.equals("")){    
  
  strSql="select a.co_bod, a1.tx_nom, a.nd_stkact from tbm_invbod as a " +
  " INNER JOIN  tbm_bod AS a1 ON (a1.co_emp=a.co_emp AND a1.co_bod=a.co_bod)  " +
  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm="+strCodItm+" order by a.co_bod";
   Ventas.ZafVen02.ZafVen02_02 obj = new  Ventas.ZafVen02.ZafVen02_02(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis, strSql);
   obj.show();  
           
       if(obj.acepta()){ 
           tblDat.setValueAt(obj.GetCamSel(1), intCol, INT_TBL_CODBOD);
           //tblDat.setValueAt(obj.GetCamSel(2), intCol, INT_TBL_NOMBOD);
        }     
    obj.dispose();
    obj=null;
    
   }else 
        Mensaje("AGREGE UN ITEM EN LA COTIZACIÓN. ");
  
            
}




private void Mensaje(String strNomCampo){
        //JOptionPane obj =new JOptionPane();
        String strMsg;
        //String strTit="Mensaje del sistema Zafiro";
        strMsg=strNomCampo;
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
    
   
   
    
    
    
    //*************************************************************************************
    
    
    public void OcultarColumna(int Columna, javax.swing.table.TableColumnModel tcmAux){
        try{
            tcmAux.getColumn(Columna).setWidth(0);
            tcmAux.getColumn(Columna).setMaxWidth(0);
            tcmAux.getColumn(Columna).setMinWidth(0);
            tcmAux.getColumn(Columna).setPreferredWidth(0);
            tcmAux.getColumn(Columna).setResizable(false);
            
        }catch(Exception e){ objUti.mostrarMsgErr_F1(this,e);  }
    }
    
    
    
    
    //*********************************************************************************************************************
    //*********************************************************************************************************************
    
    
    private void eventoVenCon(){
        int intNumFil = tblDat.getSelectedRow();
        if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
            strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
        else
            strAfterValue ="";
        
        String strValpre="0.00";
        strValpre = tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).toString();
        if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI).equals("")) strValpre="0";
        if(tblDat.getValueAt(intNumFil,INT_TBL_PREUNI)==null) strValpre="0";
        tblDat.setValueAt(strValpre,intNumFil,INT_TBL_PREUNI);
        
        if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV);
        
        //** if(tblDat.getValueAt(intNumFil,INT_TBL_PRE_COS)==null) tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PRE_COS);
        
        if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES);
        
        tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);
        
        if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
            if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).toString().trim().equalsIgnoreCase("S"))
                tblDat.setValueAt(true, intNumFil, INT_TBL_BLNIVA);
            else
                tblDat.setValueAt(false, intNumFil, INT_TBL_BLNIVA);
        }
        
        tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);
        
        if(tblDat.getValueAt(0,INT_TBL_PORDES) != null) tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
        
        calculaSubtotal();
        
        tblDat.repaint();
        tblDat.requestFocus();
        tblDat.editCellAt(intNumFil, INT_TBL_CANMOV);
    }
    
    
    
    //*********************************************************************************************************************
    //*********************************************************************************************************************
    
    public void calculaSubtotal(){
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
        
        int intFilSel=tblDat.getSelectedRow();
        
        dblCan    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV).toString())));
        dblPre    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI).toString())));
        dblDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PORDES).toString()))),2);
        dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100));
        dblTotal  = (dblCan * dblPre)- dblValDes;
        dblTotal =  objUti.redondear(dblTotal,3);
        dblTotal =  objUti.redondear(dblTotal,2);
        tblDat.setValueAt(dblTotal+"",intFilSel,INT_TBL_TOTAL);
        calculaTotal(dblPorIva);
        calculaPagos();
        generaAsiento();
    }
    
    public void calculaSubTot2(){
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00; 
        double dblPorDesReal=0, dblPreLis=0 ;
        double dblValDesTarCre=0, dblTotalTarCre=0, dblPorDesRealTarCre=0;
        int intColActPre=0, intFilSel=tblDat.getSelectedRow();
        
        /*boolean blnActPreDes=objTooBar.getValidaVigencia();
        if(blnActPreDes) intColActPre=INT_TBL_PRELISITM2;
         else intColActPre=INT_TBL_PRELISITM; */
        
        dblCan    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV).toString())));
        dblPre    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI).toString())));
        dblDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PORDES).toString()))),2);
        dblPreLis=objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI2)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI2).toString()))),2);
        
        dblValDes = objUti.redondear(((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100)), 6);
        dblTotal  = (dblCan * dblPre)- dblValDes;
        dblTotal =  objUti.redondear(dblTotal, 6 );
        dblTotal =  objUti.redondear(dblTotal, 6 );        
        dblPorDesReal=objUti.redondear(((dblTotal - dblPreLis)/ ((dblPreLis==0)?1:dblPreLis) ), 6 ) ;
                
        dblValDesTarCre = objUti.redondear(((dblCan * dblPreLis)==0)?0:((dblCan * dblPreLis) * (dblPorDesTarCre / 100)), 6);
        dblTotalTarCre = (dblCan * dblPreLis)- dblValDesTarCre;
        dblTotalTarCre =  objUti.redondear(dblTotalTarCre, 6 );
        dblTotalTarCre =  objUti.redondear(dblTotalTarCre,6 );
        dblPorDesRealTarCre=objUti.redondear( ((dblTotalTarCre - dblPreLis)/((dblPreLis==0)?1:dblPreLis)), 6 );
        
        dblPorDesReal=Math.abs(dblPorDesReal);
        dblPorDesRealTarCre=Math.abs(dblPorDesRealTarCre);
        
        //System.out.println("ZafVen02.calculaSubTot2: Porcentaje Modificado " + dblPorDesReal + " vs. Porcentaje Tarjeta " + dblPorDesRealTarCre);
        
        if (dblPorDesReal > dblPorDesRealTarCre){
            MensajeInf("No puede exceder el porcentaje de descuento asignado a la tarjeta"); 
            calculaDescuento();
        }
        
        //tblDat.setValueAt(dblTotal+"",intFilSel,INT_TBL_TOTAL);
        //calculaTot();
        //calculaPag(); 
    }

    public void calculaSubTotTodLosItm(){
        String strIva="S";
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
        double dblSub = 0, dblIva = 0, dblDes2 = 0, dblTmp=0, dblSub2=0;
        int intFilSel=0;  //tblDat.getSelectedRow();
        for (int intNumFil=0; intNumFil<tblDat.getRowCount(); intNumFil++){
            if(tblDat.getValueAt(intNumFil,INT_TBL_CODITM) != null ){
                intFilSel=intNumFil;
                dblCan    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV).toString())));
                dblPre    =  Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI).toString())));
                dblDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt(intFilSel, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(intFilSel, INT_TBL_PORDES).toString()))),2);
                dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100));
                dblTotal  = (dblCan * dblPre)- dblValDes;
                dblTotal =  objUti.redondear(dblTotal,3);
                dblTotal =  objUti.redondear(dblTotal,2);
                tblDat.setValueAt(dblTotal+"",intFilSel,INT_TBL_TOTAL);
                
                dblSub2 = ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString()));
                dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                
               // if(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)==null)
                //    dblIva = dblIva + 0;
                //else{
                    
                    strIva=((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)==null?"S":(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("")?"S":tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true")?"S":"N")));
                    if(strIva.equals("S")){   
                       dblTmp=((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString()));
                       dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
                    }
                    else  dblIva = dblIva + 0;
                    
                    //dblTmp = ((tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(intFilSel, INT_TBL_TOTAL)==null||tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intFilSel, INT_TBL_TOTAL).toString())) : 0 );
                   
                //}
            }}
        
        /////////////////////////////////////////////////////////////
        dblSubtotalCot = dblSub;
        dblIvaCot = objUti.redondear(dblIva,intNumDec);
        dblTotalCot = dblSubtotalCot + dblIvaCot;
        dblTotalCot = objUti.redondear(dblTotalCot ,intNumDec);
        dblSubtotalCot = objUti.redondear(dblSubtotalCot ,intNumDec);
        
        txtSub.setText( "" + dblSubtotalCot );
        txtIva.setText( "" + dblIvaCot );
        
        txtTot.setText( ""+ dblTotalCot);
        /////////////////////////////////////////////////////////////
        
        calculaTotal(dblPorIva);
        calculaPagos();
        generaAsiento();
    }        
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      tabCotCom = new javax.swing.JTabbedPane();
      panCotGen = new javax.swing.JPanel();
      panCotGenNor = new javax.swing.JPanel();
      pan2 = new javax.swing.JPanel();
      jLabel2 = new javax.swing.JLabel();
      txtTipDocAli = new javax.swing.JTextField();
      txtTipDocNom = new javax.swing.JTextField();
      butTipDoc = new javax.swing.JButton();
      jLabel1 = new javax.swing.JLabel();
      txtCot = new javax.swing.JTextField();
      lblFecDoc = new javax.swing.JLabel();
      lblCom = new javax.swing.JLabel();
      txtVenCod = new javax.swing.JTextField();
      txtVenNom = new javax.swing.JTextField();
      butVenCon = new javax.swing.JButton();
      lblfecha = new javax.swing.JLabel();
      butGetXmlFac = new javax.swing.JButton();
      panCotGenNorNor = new javax.swing.JPanel();
      txtDoc = new javax.swing.JTextField();
      lblDoc = new javax.swing.JLabel();
      txtFac = new javax.swing.JTextField();
      lblFac = new javax.swing.JLabel();
      lblAte = new javax.swing.JLabel();
      txtPed = new javax.swing.JTextField();
      txtAte = new javax.swing.JTextField();
      lblPed = new javax.swing.JLabel();
      butCliRet = new javax.swing.JButton();
      butFacEle = new javax.swing.JButton();
      panCotGenNorSur = new javax.swing.JPanel();
      lblPrv = new javax.swing.JLabel();
      lblDir = new javax.swing.JLabel();
      txtCliDir = new javax.swing.JTextField();
      txtCliCod = new javax.swing.JTextField();
      txtCliNom = new javax.swing.JTextField();
      butCliCon = new javax.swing.JButton();
      lblGuia = new javax.swing.JLabel();
      txtGuia = new javax.swing.JTextField();
      butGuiRem = new javax.swing.JButton();
      lblGuia1 = new javax.swing.JLabel();
      txtOD = new javax.swing.JTextField();
      chkMasInfo = new javax.swing.JCheckBox();
      butItmPen = new javax.swing.JButton();
      spnCon = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      panCotGenSur = new javax.swing.JPanel();
      panCotGenSurCen = new javax.swing.JPanel();
      panObs = new javax.swing.JPanel();
      panObs1 = new javax.swing.JPanel();
      lblObs1 = new javax.swing.JLabel();
      spnObs1 = new javax.swing.JScrollPane();
      txaObs1 = new javax.swing.JTextArea();
      panObs2 = new javax.swing.JPanel();
      lblObs2 = new javax.swing.JLabel();
      spnObs2 = new javax.swing.JScrollPane();
      txaObs2 = new javax.swing.JTextArea();
      panCotGenSurEst = new javax.swing.JPanel();
      lblSubTot = new javax.swing.JLabel();
      txtSub = new javax.swing.JTextField();
      lblIva = new javax.swing.JLabel();
      txtIva = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      txtComSol = new javax.swing.JTextField();
      lblTot = new javax.swing.JLabel();
      txtTot = new javax.swing.JTextField();
      panCotForPag = new javax.swing.JPanel();
      panCotForPagNor = new javax.swing.JPanel();
      lblForPag = new javax.swing.JLabel();
      txtCodigoFormaPago = new javax.swing.JTextField();
      txtNomForPag = new javax.swing.JTextField();
      butCliCon1 = new javax.swing.JButton();
      lblTipCre = new javax.swing.JLabel();
      txtCodTarCre = new javax.swing.JTextField();
      txtNomTarCre = new javax.swing.JTextField();
      butConTipCre = new javax.swing.JButton();
      txtNomEmiTar = new javax.swing.JTextField();
      lblTipCre1 = new javax.swing.JLabel();
      txtMesGra = new javax.swing.JTextField();
      panCotForPagCentral = new javax.swing.JPanel();
      spnForPag = new javax.swing.JScrollPane();
      tblPag = new javax.swing.JTable();
      PanOtro = new javax.swing.JPanel();
      panNorOtros = new javax.swing.JPanel();
      lblPartida = new javax.swing.JLabel();
      txtPartida = new javax.swing.JTextField();
      lblTransporte = new javax.swing.JLabel();
      txtTransporte = new javax.swing.JTextField();
      lblMotivo = new javax.swing.JLabel();
      cboMotivoTransporte = new javax.swing.JComboBox();
      panAsiento = new javax.swing.JPanel();
      panCotNor = new javax.swing.JPanel();
      lblCotNumDes = new javax.swing.JLabel();

      setClosable(true);
      setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
      setIconifiable(true);
      setMaximizable(true);
      setResizable(true);
      setPreferredSize(new java.awt.Dimension(700, 450));
      addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
         public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
         }
         public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
         }
         public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            CerrarVentana(evt);
         }
         public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
         }
         public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
         }
         public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            formInternalFrameIconified(evt);
         }
         public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            formInternalFrameOpened(evt);
         }
      });

      tabCotCom.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
      tabCotCom.setName("General"); // NOI18N

      panCotGen.setLayout(new java.awt.BorderLayout());

      panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 135));
      panCotGenNor.setLayout(new java.awt.BorderLayout());

      pan2.setPreferredSize(new java.awt.Dimension(600, 50));
      pan2.setLayout(null);

      jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jLabel2.setText("Tipo Documento:"); // NOI18N
      pan2.add(jLabel2);
      jLabel2.setBounds(4, 12, 110, 15);

      txtTipDocAli.setBackground(objZafParSis.getColorCamposObligatorios());
      txtTipDocAli.setMinimumSize(new java.awt.Dimension(0, 0));
      txtTipDocAli.setPreferredSize(new java.awt.Dimension(25, 20));
      txtTipDocAli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtTipDocAliActionPerformed(evt);
         }
      });
      pan2.add(txtTipDocAli);
      txtTipDocAli.setBounds(100, 10, 64, 20);

      txtTipDocNom.setBackground(objZafParSis.getColorCamposObligatorios());
      txtTipDocNom.setPreferredSize(new java.awt.Dimension(100, 20));
      txtTipDocNom.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtTipDocNomActionPerformed(evt);
         }
      });
      pan2.add(txtTipDocNom);
      txtTipDocNom.setBounds(164, 10, 240, 20);

      butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      butTipDoc.setText("..."); // NOI18N
      butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
      butTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butTipDocActionPerformed(evt);
         }
      });
      pan2.add(butTipDoc);
      butTipDoc.setBounds(403, 10, 20, 20);

      jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jLabel1.setText("No. Cotización:"); // NOI18N
      pan2.add(jLabel1);
      jLabel1.setBounds(6, 30, 110, 15);

      objZafParSis.getColorCamposObligatorios();
      txtCot.setMaximumSize(null);
      txtCot.setPreferredSize(new java.awt.Dimension(70, 20));
      txtCot.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCotActionPerformed(evt);
         }
      });
      pan2.add(txtCot);
      txtCot.setBounds(100, 30, 92, 20);

      lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblFecDoc.setText("Fecha documento:"); // NOI18N
      lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
      pan2.add(lblFecDoc);
      lblFecDoc.setBounds(450, 12, 108, 15);

      lblCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblCom.setText("Vendedor:"); // NOI18N
      lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
      pan2.add(lblCom);
      lblCom.setBounds(450, 30, 64, 15);

      txtVenCod.setBackground(objZafParSis.getColorCamposObligatorios());
      txtVenCod.setMinimumSize(new java.awt.Dimension(0, 0));
      txtVenCod.setPreferredSize(new java.awt.Dimension(25, 20));
      txtVenCod.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtVenCodActionPerformed(evt);
         }
      });
      txtVenCod.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtVenCodFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtVenCodFocusLost(evt);
         }
      });
      pan2.add(txtVenCod);
      txtVenCod.setBounds(515, 30, 35, 20);

      txtVenNom.setBackground(objZafParSis.getColorCamposObligatorios());
      txtVenNom.setPreferredSize(new java.awt.Dimension(100, 20));
      txtVenNom.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtVenNomActionPerformed(evt);
         }
      });
      txtVenNom.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtVenNomFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtVenNomFocusLost(evt);
         }
      });
      pan2.add(txtVenNom);
      txtVenNom.setBounds(550, 30, 110, 20);

      butVenCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      butVenCon.setText("..."); // NOI18N
      butVenCon.setPreferredSize(new java.awt.Dimension(20, 20));
      butVenCon.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butVenConActionPerformed(evt);
         }
      });
      pan2.add(butVenCon);
      butVenCon.setBounds(660, 30, 20, 20);

      lblfecha.setBackground(new java.awt.Color(255, 255, 255));
      lblfecha.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblfecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
      lblfecha.setOpaque(true);
      pan2.add(lblfecha);
      lblfecha.setBounds(550, 6, 77, 18);

      butGetXmlFac.setText("Obtener XML");
      butGetXmlFac.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butGetXmlFacActionPerformed(evt);
         }
      });
      pan2.add(butGetXmlFac);
      butGetXmlFac.setBounds(230, 30, 110, 23);

      panCotGenNor.add(pan2, java.awt.BorderLayout.NORTH);

      panCotGenNorNor.setPreferredSize(new java.awt.Dimension(600, 55));
      panCotGenNorNor.setLayout(null);

      txtDoc.setBackground(objZafParSis.getColorCamposSistema()
      );
      txtDoc.setMaximumSize(null);
      txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
      panCotGenNorNor.add(txtDoc);
      txtDoc.setBounds(100, 0, 92, 20);

      lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblDoc.setText("Cód. Documento:"); // NOI18N
      panCotGenNorNor.add(lblDoc);
      lblDoc.setBounds(6, 0, 110, 15);

      txtFac.setBackground(objZafParSis.getColorCamposObligatorios()
      );
      txtFac.setMaximumSize(null);
      txtFac.setPreferredSize(new java.awt.Dimension(70, 20));
      txtFac.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtFacActionPerformed(evt);
         }
      });
      txtFac.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
            txtFacKeyPressed(evt);
         }
      });
      panCotGenNorNor.add(txtFac);
      txtFac.setBounds(100, 20, 92, 20);

      lblFac.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblFac.setText("No. Factura:"); // NOI18N
      panCotGenNorNor.add(lblFac);
      lblFac.setBounds(6, 20, 110, 15);

      lblAte.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblAte.setText("Atención:"); // NOI18N
      lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
      panCotGenNorNor.add(lblAte);
      lblAte.setBounds(450, 0, 60, 15);
      panCotGenNorNor.add(txtPed);
      txtPed.setBounds(552, 20, 110, 20);

      txtAte.setMaximumSize(null);
      txtAte.setPreferredSize(new java.awt.Dimension(70, 20));
      panCotGenNorNor.add(txtAte);
      txtAte.setBounds(534, 0, 146, 20);

      lblPed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblPed.setText("No. Pedido:"); // NOI18N
      panCotGenNorNor.add(lblPed);
      lblPed.setBounds(450, 20, 72, 15);

      butCliRet.setText("Pedidos");
      butCliRet.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCliRetActionPerformed(evt);
         }
      });
      panCotGenNorNor.add(butCliRet);
      butCliRet.setBounds(230, 10, 110, 23);

      butFacEle.setText("Enviar SRI");
      butFacEle.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butFacEleActionPerformed(evt);
         }
      });
      panCotGenNorNor.add(butFacEle);
      butFacEle.setBounds(340, 10, 110, 23);

      panCotGenNor.add(panCotGenNorNor, java.awt.BorderLayout.CENTER);

      panCotGenNorSur.setPreferredSize(new java.awt.Dimension(600, 45));
      panCotGenNorSur.setLayout(null);

      lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblPrv.setText("Cliente::"); // NOI18N
      panCotGenNorSur.add(lblPrv);
      lblPrv.setBounds(6, 0, 72, 15);

      lblDir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblDir.setText("Dirección:"); // NOI18N
      panCotGenNorSur.add(lblDir);
      lblDir.setBounds(6, 20, 60, 15);

      txtCliDir.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
      txtCliDir.setPreferredSize(new java.awt.Dimension(70, 20));
      txtCliDir.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCliDirActionPerformed(evt);
         }
      });
      txtCliDir.addCaretListener(new javax.swing.event.CaretListener() {
         public void caretUpdate(javax.swing.event.CaretEvent evt) {
            txtCliDirCaretUpdate(evt);
         }
      });
      panCotGenNorSur.add(txtCliDir);
      txtCliDir.setBounds(100, 20, 320, 20);

      txtCliCod.setBackground(objZafParSis.getColorCamposObligatorios());
      txtCliCod.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
      txtCliCod.setMinimumSize(new java.awt.Dimension(0, 0));
      txtCliCod.setPreferredSize(new java.awt.Dimension(25, 20));
      txtCliCod.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCliCodActionPerformed(evt);
         }
      });
      txtCliCod.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCliCodFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCliCodFocusLost(evt);
         }
      });
      panCotGenNorSur.add(txtCliCod);
      txtCliCod.setBounds(100, 0, 35, 20);

      txtCliNom.setBackground(objZafParSis.getColorCamposObligatorios());
      txtCliNom.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
      txtCliNom.setPreferredSize(new java.awt.Dimension(100, 20));
      txtCliNom.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCliNomActionPerformed(evt);
         }
      });
      txtCliNom.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCliNomFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCliNomFocusLost(evt);
         }
      });
      txtCliNom.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
         public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
            txtCliNomVetoableChange(evt);
         }
      });
      panCotGenNorSur.add(txtCliNom);
      txtCliNom.setBounds(134, 0, 270, 20);

      butCliCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      butCliCon.setText("..."); // NOI18N
      butCliCon.setPreferredSize(new java.awt.Dimension(20, 20));
      butCliCon.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCliConActionPerformed(evt);
         }
      });
      panCotGenNorSur.add(butCliCon);
      butCliCon.setBounds(403, 0, 22, 20);

      lblGuia.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblGuia.setText("No. Ord.Des.:"); // NOI18N
      lblGuia.setPreferredSize(new java.awt.Dimension(100, 15));
      panCotGenNorSur.add(lblGuia);
      lblGuia.setBounds(450, 20, 100, 15);
      panCotGenNorSur.add(txtGuia);
      txtGuia.setBounds(550, 1, 80, 20);

      butGuiRem.setText("G"); // NOI18N
      butGuiRem.setToolTipText("Ver las Guías de remisión");
      butGuiRem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butGuiRemActionPerformed(evt);
         }
      });
      panCotGenNorSur.add(butGuiRem);
      butGuiRem.setBounds(630, 0, 45, 20);

      lblGuia1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblGuia1.setText("No. Guia Remisión:"); // NOI18N
      lblGuia1.setPreferredSize(new java.awt.Dimension(100, 15));
      panCotGenNorSur.add(lblGuia1);
      lblGuia1.setBounds(450, 0, 100, 15);
      panCotGenNorSur.add(txtOD);
      txtOD.setBounds(550, 22, 80, 20);

      chkMasInfo.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      chkMasInfo.setText("Mas Informacion"); // NOI18N
      chkMasInfo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            chkMasInfoActionPerformed(evt);
         }
      });
      panCotGenNorSur.add(chkMasInfo);
      chkMasInfo.setBounds(800, 20, 116, 23);

      butItmPen.setText("P");
      butItmPen.setToolTipText("Ver Items pendientes");
      butItmPen.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butItmPenActionPerformed(evt);
         }
      });
      panCotGenNorSur.add(butItmPen);
      butItmPen.setBounds(630, 22, 45, 20);

      panCotGenNor.add(panCotGenNorSur, java.awt.BorderLayout.SOUTH);

      panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

      tblDat.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {null, null, null, null, null, null, null, null, null, null, null, null}
         },
         new String [] {
            "Linea", "Codigo", "...", "Descripción", "Unidad", "Cantidad", "Precio", "%Desc", "Iva", "Total", "Codigo", "BLVPRE"
         }
      ) {
         Class[] types = new Class [] {
            java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.Short.class, java.lang.Boolean.class
         };
         boolean[] canEdit = new boolean [] {
            false, true, true, true, false, true, true, true, true, false, false, false
         };

         public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
         }

         public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
         }
      });
      tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
      tblDat.setCellSelectionEnabled(true);
      spnCon.setViewportView(tblDat);

      panCotGen.add(spnCon, java.awt.BorderLayout.CENTER);

      panCotGenSur.setPreferredSize(new java.awt.Dimension(430, 73));
      panCotGenSur.setLayout(new java.awt.BorderLayout());

      panCotGenSurCen.setLayout(new java.awt.BorderLayout());

      panObs.setLayout(new java.awt.GridLayout(2, 1));

      panObs1.setPreferredSize(new java.awt.Dimension(250, 25));
      panObs1.setLayout(new java.awt.BorderLayout());

      lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblObs1.setText("Observación 1:"); // NOI18N
      panObs1.add(lblObs1, java.awt.BorderLayout.WEST);

      txaObs1.setLineWrap(true);
      spnObs1.setViewportView(txaObs1);

      panObs1.add(spnObs1, java.awt.BorderLayout.CENTER);

      panObs.add(panObs1);

      panObs2.setPreferredSize(new java.awt.Dimension(250, 25));
      panObs2.setLayout(new java.awt.BorderLayout());

      lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblObs2.setText("Observación 2:"); // NOI18N
      panObs2.add(lblObs2, java.awt.BorderLayout.WEST);

      txaObs2.setLineWrap(true);
      spnObs2.setViewportView(txaObs2);

      panObs2.add(spnObs2, java.awt.BorderLayout.CENTER);

      panObs.add(panObs2);

      panCotGenSurCen.add(panObs, java.awt.BorderLayout.CENTER);

      panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

      panCotGenSurEst.setLayout(new java.awt.GridLayout(4, 2));

      lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblSubTot.setText("SubTotal:"); // NOI18N
      lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
      panCotGenSurEst.add(lblSubTot);

      txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCotGenSurEst.add(txtSub);

      lblIva.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblIva.setText("IVA 12%:"); // NOI18N
      lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
      panCotGenSurEst.add(lblIva);

      txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCotGenSurEst.add(txtIva);

      jLabel3.setText("Compensación(-):");
      panCotGenSurEst.add(jLabel3);

      txtComSol.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCotGenSurEst.add(txtComSol);

      lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
      lblTot.setText("Total:"); // NOI18N
      lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
      panCotGenSurEst.add(lblTot);

      txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
      txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCotGenSurEst.add(txtTot);

      panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

      panCotGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

      tabCotCom.addTab("tab1", panCotGen);

      panCotForPag.setLayout(new java.awt.BorderLayout());

      panCotForPagNor.setFocusTraversalPolicyProvider(true);
      panCotForPagNor.setPreferredSize(new java.awt.Dimension(249, 85));
      panCotForPagNor.setLayout(null);

      lblForPag.setText("Forma de Pago:"); // NOI18N
      panCotForPagNor.add(lblForPag);
      lblForPag.setBounds(10, 10, 100, 19);

      txtCodigoFormaPago.setBackground(objZafParSis.getColorCamposObligatorios());
      txtCodigoFormaPago.setPreferredSize(new java.awt.Dimension(180, 21));
      txtCodigoFormaPago.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCodigoFormaPagoFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodigoFormaPagoFocusLost(evt);
         }
      });
      txtCodigoFormaPago.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodigoFormaPagoActionPerformed(evt);
         }
      });
      panCotForPagNor.add(txtCodigoFormaPago);
      txtCodigoFormaPago.setBounds(170, 10, 30, 21);

      txtNomForPag.setBackground(objZafParSis.getColorCamposObligatorios());
      txtNomForPag.setPreferredSize(new java.awt.Dimension(180, 21));
      txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNomForPagActionPerformed(evt);
         }
      });
      panCotForPagNor.add(txtNomForPag);
      txtNomForPag.setBounds(200, 10, 440, 21);

      butCliCon1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      butCliCon1.setText("..."); // NOI18N
      butCliCon1.setPreferredSize(new java.awt.Dimension(20, 20));
      butCliCon1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCliCon1ActionPerformed(evt);
         }
      });
      panCotForPagNor.add(butCliCon1);
      butCliCon1.setBounds(640, 10, 25, 20);

      lblTipCre.setText("Emisor de tarjeta:"); // NOI18N
      panCotForPagNor.add(lblTipCre);
      lblTipCre.setBounds(10, 52, 130, 19);

      txtCodTarCre.setBackground(objZafParSis.getColorCamposObligatorios());
      txtCodTarCre.setPreferredSize(new java.awt.Dimension(180, 21));
      txtCodTarCre.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtCodTarCreFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtCodTarCreFocusLost(evt);
         }
      });
      txtCodTarCre.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodTarCreActionPerformed(evt);
         }
      });
      panCotForPagNor.add(txtCodTarCre);
      txtCodTarCre.setBounds(170, 31, 30, 21);

      txtNomTarCre.setBackground(objZafParSis.getColorCamposObligatorios());
      txtNomTarCre.setPreferredSize(new java.awt.Dimension(180, 21));
      txtNomTarCre.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNomTarCreFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNomTarCreFocusLost(evt);
         }
      });
      txtNomTarCre.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNomTarCreActionPerformed(evt);
         }
      });
      panCotForPagNor.add(txtNomTarCre);
      txtNomTarCre.setBounds(200, 31, 440, 21);

      butConTipCre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      butConTipCre.setText("..."); // NOI18N
      butConTipCre.setPreferredSize(new java.awt.Dimension(20, 20));
      butConTipCre.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butConTipCreActionPerformed(evt);
         }
      });
      panCotForPagNor.add(butConTipCre);
      butConTipCre.setBounds(640, 31, 25, 20);

      txtNomEmiTar.setBackground(objZafParSis.getColorCamposObligatorios());
      txtNomEmiTar.setPreferredSize(new java.awt.Dimension(180, 21));
      txtNomEmiTar.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNomEmiTarFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNomEmiTarFocusLost(evt);
         }
      });
      txtNomEmiTar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNomEmiTarActionPerformed(evt);
         }
      });
      panCotForPagNor.add(txtNomEmiTar);
      txtNomEmiTar.setBounds(170, 52, 470, 21);

      lblTipCre1.setText("Tarjeta de credito/debito:"); // NOI18N
      panCotForPagNor.add(lblTipCre1);
      lblTipCre1.setBounds(10, 31, 150, 19);

      txtMesGra.setBackground(objZafParSis.getColorCamposObligatorios());
      txtMesGra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtMesGra.setEnabled(false);
      txtMesGra.setPreferredSize(new java.awt.Dimension(180, 21));
      panCotForPagNor.add(txtMesGra);
      txtMesGra.setBounds(150, 50, 10, 20);

      panCotForPag.add(panCotForPagNor, java.awt.BorderLayout.NORTH);

      panCotForPagCentral.setEnabled(false);
      panCotForPagCentral.setPreferredSize(new java.awt.Dimension(249, 50));
      panCotForPagCentral.setLayout(new java.awt.BorderLayout());

      tblPag.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {null, null, null, null, null, null, null, null}
         },
         new String [] {
            " Línea", "Dias de crédito", "Fecha de Vencimiento", "Retención", "Monto de Pago", "Días de gracia", "CodRet", "tx_sop"
         }
      ) {
         Class[] types = new Class [] {
            java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
         };
         boolean[] canEdit = new boolean [] {
            false, false, false, false, true, true, false, false
         };

         public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
         }

         public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
         }
      });
      tblPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
      tblPag.setColumnSelectionAllowed(true);
      spnForPag.setViewportView(tblPag);

      panCotForPagCentral.add(spnForPag, java.awt.BorderLayout.CENTER);

      panCotForPag.add(panCotForPagCentral, java.awt.BorderLayout.CENTER);

      tabCotCom.addTab("tab2", panCotForPag);

      PanOtro.setLayout(new java.awt.BorderLayout());

      panNorOtros.setMinimumSize(new java.awt.Dimension(600, 200));
      panNorOtros.setPreferredSize(new java.awt.Dimension(600, 300));
      panNorOtros.setLayout(null);

      lblPartida.setText("Punto de partida"); // NOI18N
      panNorOtros.add(lblPartida);
      lblPartida.setBounds(0, 0, 344, 21);
      panNorOtros.add(txtPartida);
      txtPartida.setBounds(344, 0, 344, 20);

      lblTransporte.setText("Transporte"); // NOI18N
      panNorOtros.add(lblTransporte);
      lblTransporte.setBounds(0, 21, 344, 21);
      panNorOtros.add(txtTransporte);
      txtTransporte.setBounds(344, 21, 344, 20);

      lblMotivo.setText("Motivo de Transporte"); // NOI18N
      panNorOtros.add(lblMotivo);
      lblMotivo.setBounds(0, 42, 344, 21);
      panNorOtros.add(cboMotivoTransporte);
      cboMotivoTransporte.setBounds(344, 42, 344, 20);

      PanOtro.add(panNorOtros, java.awt.BorderLayout.NORTH);

      tabCotCom.addTab("tab3", PanOtro);

      panAsiento.setLayout(new java.awt.BorderLayout());
      tabCotCom.addTab("tab4", panAsiento);

      getContentPane().add(tabCotCom, java.awt.BorderLayout.CENTER);

      lblCotNumDes.setText("Factura"); // NOI18N
      lblCotNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
      lblCotNumDes.setOpaque(true);
      panCotNor.add(lblCotNumDes);

      getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
   }// </editor-fold>//GEN-END:initComponents
    
    private void txtFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacKeyPressed
        // TODO add your handling code here:
        
        if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
            objTooBar.consultar();
            objTooBar.setEstado('w');

            butGuiRem.setEnabled(true);
            butItmPen.setEnabled(true);

        }
        
        
    }//GEN-LAST:event_txtFacKeyPressed
    
    private void txtFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacActionPerformed
    
    private void txtVenNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusGained
        // TODO add your handling code here:
        strDesLarVen=txtVenNom.getText();
        txtVenNom.selectAll();
    }//GEN-LAST:event_txtVenNomFocusGained
    
    private void txtVenCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusGained
        // TODO add your handling code here:
        strCodVen=txtVenCod.getText();
        txtVenCod.selectAll();
    }//GEN-LAST:event_txtVenCodFocusGained
    
    private void txtCliCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusGained
        // TODO add your handling code here:
        strCodCli=txtCliCod.getText();
        txtCliCod.selectAll();
    }//GEN-LAST:event_txtCliCodFocusGained
    
    private void txtCliNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtCliNom.getText();
        txtCliNom.selectAll();
    }//GEN-LAST:event_txtCliNomFocusGained
    
    private void butGuiRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuiRemActionPerformed
        mostrarGuiasRemision( String.valueOf(objZafParSis.getCodigoEmpresa()), String.valueOf(objZafParSis.getCodigoLocal()), txtTipDocCod.getText(), txtDoc.getText() );
    }//GEN-LAST:event_butGuiRemActionPerformed



private void mostrarGuiasRemision(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
  String strSql02="";
  try{


    /*strSql02="select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc  from tbm_detmovinv as  a "
    + " inner join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc and a1.co_regrel=a.co_reg ) "
    + " inner join tbr_detguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
    + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg ) "
    + " inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  ) "
    + " inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
    + " where a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+" and a.co_doc="+strCodDoc+" and a4.st_reg='A' "
    + " group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
    + " order by  a4.ne_numdoc "; */

    strSql02="select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc  from tbm_detmovinv as  a "
    + " inner join tbm_detguirem as a1 on (a1.co_emprel=a.co_emp and a1.co_locrel=a.co_loc and a1.co_tipdocrel=a.co_tipdoc and a1.co_docrel=a.co_doc and a1.co_regrel=a.co_reg ) "
    + " inner join tbr_detguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
    + " inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg ) "
    + " inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  ) "
    + " inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc) "
    + " where a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDoc+" and a.co_doc="+strCodDoc+" and a4.st_reg='A' "
    + " group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc "
//    + " order by  a4.ne_numdoc "
    + " UNION ALL  "
    + " select x8.co_emp, x8.co_loc, x8.co_tipdoc, x8.co_doc, x9.tx_descor, x8.ne_numdoc, x8.fe_doc "
    + " from tbr_detmovinv as x "
    + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel and ( x1.co_emp!=x.co_emp or x1.co_loc!=x.co_loc or x1.co_tipdoc!=x.co_tipdoc or x1.co_doc!=x.co_doc ) ) "
    + " inner join tbr_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg ) "
    + " inner join tbm_detmovinv as x5 on (x5.co_emp=x2.co_emp and x5.co_loc=x2.co_loc and x5.co_tipdoc=x2.co_tipdoc and x5.co_doc=x2.co_doc and x5.co_reg=x2.co_reg ) "
    + " inner join tbm_detguirem as x3 on (x3.co_emprel=x5.co_emp and x3.co_locrel=x5.co_loc and x3.co_tipdocrel=x5.co_tipdoc and x3.co_docrel=x5.co_doc and x3.co_regrel=x5.co_reg ) "
    + " inner join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc ) "
    + " inner join tbm_bod as x6 on (x6.co_emp=x4.co_emp and x6.co_bod=x4.co_ptopar ) "
    + " inner join tbr_cabguirem as x7 on (x7.co_emp=x4.co_emp and x7.co_locrel=x4.co_loc and x7.co_tipdocrel=x4.co_tipdoc and x7.co_docrel=x4.co_doc) "
    + " inner join tbm_cabguirem as x8 on (x8.co_emp=x7.co_emp and x8.co_loc=x7.co_loc and x8.co_tipdoc=x7.co_tipdoc and x8.co_doc=x7.co_doc ) "
    + " inner join tbm_cabtipdoc as x9 on (x9.co_emp=x8.co_emp and x9.co_loc=x8.co_loc and x9.co_tipdoc=x8.co_tipdoc ) "
    + " where x.co_emp="+strCodEmp+" and x.co_loc="+strCodLoc+" and x.co_tipdoc="+strCodTipDoc+" and x.co_doc="+strCodDoc+" and x.co_emprel="+strCodEmp+" and x8.st_reg='A'  "
    + " group by x8.co_emp, x8.co_loc, x8.co_tipdoc, x8.co_doc, x9.tx_descor, x8.ne_numdoc, x8.fe_doc "
    + " order by 5, 1, 2, 3, 6";
    
    //System.out.println("ZafVen02.mostrarGuiasRemision: " + strSql02);
    
     ZafVen21_01 obj1 = new ZafVen21_01(objZafParSis, this, strSql02 );
     this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER );
     obj1.show();
     
  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}



    private void chkMasInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMasInfoActionPerformed
        // TODO add your handling code here:
        masInformacion(chkMasInfo.isSelected());
        
    }//GEN-LAST:event_chkMasInfoActionPerformed
    
    
    private void masInformacion(boolean blnMostrar){
        double dblCosUni=0;
        double dblStock=0;
        if(blnMostrar){
            
            for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                    // dblCosUni =  getCosUni2((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                    dblCosUni =  getCosUniEmpresa((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                    dblStock =  StockGrupo((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                    tblDat.setValueAt(new Double(dblCosUni), i, INT_TBL_ULTCOS);
                    tblDat.setValueAt(new Double(dblStock), i, INT_TBL_STKBOD);
                }}
            
            MostrarCol(INT_TBL_STKBOD) ;
            MostrarCol(INT_TBL_ULTCOS) ;
            MostrarCol(INT_TBL_UTILIDAD);
            MostrarCol(INT_TBL_PREUNI2) ;
            
        }else{
            ocultaCol(INT_TBL_STKBOD) ;
            ocultaCol(INT_TBL_ULTCOS) ;
            ocultaCol(INT_TBL_UTILIDAD);
            ocultaCol(INT_TBL_PREUNI2);
        }
        
    }
    
    
    private void MostrarCol(int intCol){
        tblDat.getColumnModel().getColumn(intCol).setWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(80);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    private void ocultaCol(int intCol){
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);
    }
    
    
    private double getCosUni2(String int_co_itm){
        double dblPreUni = 0;
        try{
            java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conUni!=null){
                java.sql.Statement stmUni = conUni.createStatement();
                String sSQL = "  SELECT a1.nd_cosUni"+
                "  FROM tbm_inv AS a1"+
                "  INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                "  WHERE a2.co_emp="+ objZafParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objZafParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next()){
                    dblPreUni = rstUni.getDouble("nd_cosuni");
                }
                rstUni.close();
                stmUni.close();
                conUni.close();
                rstUni = null;
                stmUni = null;
                conUni = null;
            }
        }
        catch(java.sql.SQLException Evt) {  return dblPreUni;  }
        catch(Exception Evt) { return dblPreUni;  }
        return dblPreUni;
    }
    
    
    
    private double getCosUniEmpresa(String int_co_itm){
        double dblPreUni = 0;
        try{
            java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conUni!=null){
                java.sql.Statement stmUni = conUni.createStatement();
                String sSQL = "  SELECT a.nd_cosUni"+
                "  FROM tbm_inv AS a"+
                "  WHERE a.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND a.co_itm="+ int_co_itm +" ";
                //System.out.println("<> "+sSQL);
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next()){
                    dblPreUni = rstUni.getDouble("nd_cosuni");
                }
                rstUni.close();
                stmUni.close();
                conUni.close();
                rstUni = null;
                stmUni = null;
                conUni = null;
            }
        }
        catch(java.sql.SQLException Evt) {  return dblPreUni;  }
        catch(Exception Evt) { return dblPreUni;  }
        return dblPreUni;
    }
    
    
    public double StockGrupo(String int_co_itm){
        double dblStock = 0;
        try{
            java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conUni!=null){
                java.sql.Statement stmUni = conUni.createStatement();
                
                String Str_Sql = "SELECT SUM(a2.nd_stkAct) AS nd_stkAct" +
                " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
                " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
                " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
                " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+ int_co_itm +")";
                
                java.sql.ResultSet rstUni = stmUni.executeQuery(Str_Sql);
                if(rstUni.next()){
                    dblStock = rstUni.getDouble(1);
                }
                rstUni.close();
                stmUni.close();
                conUni.close();
                rstUni = null;
                stmUni = null;
                conUni = null;
            }
        }
        catch(java.sql.SQLException Evt)  {  return dblStock;   }
        catch(Exception Evt) { return dblStock;   }
        return dblStock;
    }
    
    
    
    
    private void txtTipDocNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipDocNomActionPerformed
    
    private void txtTipDocAliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocAliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipDocAliActionPerformed
    
    private void txtCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCotActionPerformed
        // TODO add your handling code here:
        buscarCotizacion();
        
    }//GEN-LAST:event_txtCotActionPerformed
    
    public void buscarCotizacion(){
        Connection conCot;
        Statement  stmCot;
        ResultSet rstCotCab;
        try{//odbc,usuario,password
            conCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCot!=null){
                stmCot=conCot.createStatement();
                
                // Agregando el Sql de Consulta para la cotizacion
                
                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                " Usr.co_usr as co_com, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                " CotCab.st_reg,  forpag.tx_des, forpag.co_forpag  " + // Campo para saber si esta anulado o no
                " FROM tbm_cabCotven as CotCab "
                + " left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) "
                + " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) "
                + " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=CotCab.co_emp and forpag.co_forpag = CotCab.co_forPag )  "
                +" Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                " and CotCab.co_loc = " + objZafParSis.getCodigoLocal() +// Consultando en el local en el ke se esta trabajando
                " and CotCab.co_cot = " + txtCot.getText();

                //System.out.println("ZafVen02.buscarCotizacion: " + sSQL);
                
                rstCotCab=stmCot.executeQuery(sSQL);
                if(rstCotCab.next()){
                    cargarRegdeCotizacion(rstCotCab);
                    java.awt.Color color = txtCot.getBackground();
                    txtCot.setEditable(false);
                    txtCot.setBackground(color);
                    
                }
                else{
                    clnTextos();
                }
                
                rstCotCab.close();
                stmCot.close();
                conCot.close();
                
                rstCotCab = null;
                stmCot = null;
                conCot = null;
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
    
    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        listaTipdoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed
    
    private void listaTipdoc(String campo,String strDesBusqueda ,int intTipo){
        objVenConTipdoc.setTitle("Listado Tipos de Documentos");
        switch (intTipo){
            case 0:
                break;
            case 1:
                objVenConTipdoc.setCampoBusqueda(1);
                if (objVenConTipdoc.buscar(campo, strDesBusqueda )){ }
                break;
            case 2:
                objVenConTipdoc.setCampoBusqueda(2);
                if (objVenConTipdoc.buscar(campo, strDesBusqueda )){ }
                break;
        }
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            if(!objVenConTipdoc.getValueAt(1).equals("")){
                cargarCabTipoDoc(Integer.parseInt(objVenConTipdoc.getValueAt(1)));
                
                
            }
        }
    }
    
    
    private void cargarCabTipoDoc(int TipoDoc) {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtTipDocCod.setText(""+objTipDoc.getco_tipdoc());
        txtTipDocNom.setText(objTipDoc.gettx_deslar());
        txtTipDocAli.setText(objTipDoc.gettx_descor());
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();
        strTipIngEgr=objTipDoc.gettx_natdoc(); 
        
        objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));
        
        
        // getIva();
    }
        
    
//
//    private void CalculoPago(){
//        try{
//            java.sql.Connection conPag = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if (conPag!=null){
//
//                if(cboForPag.getSelectedIndex()!=-1){
//
//                    String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre , A2.st_sop " +
//                    " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
//                    " Where A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) +// Clausulas Where para las tablas maestras
//                    "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
//                    "       and A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
//                    "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
//                    "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando
//
//                    String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
//                    " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
//                    " Where A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) +// Clausulas Where para las tablas maestras
//                    "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
//                    "       and A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
//                    "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando
//
//
//
//                    Statement stmDoc2   = conPag.createStatement();
//                    ResultSet rstDocCab2= stmDoc2.executeQuery(sSQL3);
//                    rstDocCab2.next();
//                    intCanArr[0] = rstDocCab2.getInt(1);
//
//                    stmDoc2   = conPag.createStatement();
//                    rstDocCab2= stmDoc2.executeQuery(sSQL2);
//                    int x=0;
//                    while(rstDocCab2.next()){
//                        intarreglodia[x]=rstDocCab2.getInt(2);
//                        intarreglonum[x]=rstDocCab2.getInt(1);
//                        strarreglosop[x]=rstDocCab2.getString("st_sop");
//                        x++;
//                    }
//                }
//                conPag.close();
//                conPag = null ;
//            }
//        }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
//
//        calculaPagos();
//    }

    private void CalculoPago(){
        try{
            java.sql.Connection conPag = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conPag!=null){

            if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){

                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre, A2.st_sop " +
                " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where A1.co_forPag = "  + txtCodForPag.getText() +// Clausulas Where para las tablas maestras
                "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
                "       and A2.co_emp = A1.co_emp " + // Consultando en la empresa en la ke se esta trabajando
                "       order by A2.co_reg ";// Consultando en el local en el ke se esta trabajando

                String sSQL3 = "SELECT count(A2.ne_diaCre) as c " +
                " FROM tbm_cabForPag as A1, tbm_detForPag as A2 " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where A1.co_forPag = "  + txtCodForPag.getText() +// Clausulas Where para las tablas maestras
                "       and A2.co_forPag = A1.co_forPag " + // Consultando en la empresa en la ke se esta trabajando
                "       and A1.co_emp = " + intCodEmp + // Consultando en la empresa en la ke se esta trabajando
                "       and A2.co_emp = A1.co_emp ";  // Consultando en la empresa en la ke se esta trabajando

                //System.out.println("-> "+sSQL3 );
                Statement stmDoc2   = conPag.createStatement();
                ResultSet rstDocCab2= stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);

                stmDoc2   = conPag.createStatement();
                rstDocCab2= stmDoc2.executeQuery(sSQL2);
                int x=0;
                while(rstDocCab2.next()){
                    intarreglodia[x]=rstDocCab2.getInt(2);
                    intarreglonum[x]=rstDocCab2.getInt(1);
                    strarreglosop[x]=rstDocCab2.getString("st_sop");
                    x++;
                }
                 calculaPagos();
          }
                conPag.close();
                conPag = null ;
      }}catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
       
    }


    
    
    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        
        
        //           if(intValBus==1){
        //              txtCot.setText(""+ intCodCot);
        //             consultar2(new Integer(intCodEmp),new Integer(intCodLoc));
        //             objTooBar.setEstado('w');
        //           }
        
    }//GEN-LAST:event_formInternalFrameOpened
    
    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameIconified
    
    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        // TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        //String strTit;
        //strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0 ) {
            cerrarObj();
            //System.gc();
            Runtime.getRuntime().gc();
            dispose();
        }
    }//GEN-LAST:event_CerrarVentana
    
    
    public void cerrarObj(){
        try{
            objVenCon2.dispose(); //***
            objVenCon2=null;  //****
            objVenConVen.dispose();
            objVenConVen=null;
            objVenConCli.dispose();
            objVenConCli=null;
            objUti=null;
            objTooBar=null;
            objAutPrg=null;
            objInvItm=null;
            objZafRep=null;
            objlisCambios=null;
            objZafParSis=null;
            txtFecDoc=null;
            objTblCelEdiTxtVcoItm=null;
            objUltDocPrint=null;
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        
        
    }
    
    
    private void txtCliNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusLost
        // TODO add your handling code here:
        if (!txtCliNom.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtCliNom.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {
                BuscarCliente("a.tx_nom",txtCliNom.getText(),1);
            }
        }
        else
            txtCliNom.setText(strDesLarCli);
        
        
        
        
        
        
    }//GEN-LAST:event_txtCliNomFocusLost
    
    private void txtVenNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNomFocusLost
        // TODO add your handling code here:
        if (!txtVenNom.getText().equalsIgnoreCase(strDesLarVen)) {
            if (txtVenNom.getText().equals("")) {
                txtVenCod.setText("");
                txtVenNom.setText("");
            }
            else {
                BuscarVendedor("a.tx_nom",txtVenNom.getText(),1);
            }
        }
        else
            txtVenNom.setText(strDesLarVen);
        
    }//GEN-LAST:event_txtVenNomFocusLost
    
    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed
    
    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated
    
    private void txtCliDirCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCliDirCaretUpdate
        
    }//GEN-LAST:event_txtCliDirCaretUpdate
    
    private void txtCliNomVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtCliNomVetoableChange
        // Prueba
    }//GEN-LAST:event_txtCliNomVetoableChange
    
    private void txtCliCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusLost
        
        if (!txtCliCod.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCliCod.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {
                BuscarCliente("a.co_cli",txtCliCod.getText(),0);
            }
        }
        else
            txtCliCod.setText(strCodCli);
        
    }//GEN-LAST:event_txtCliCodFocusLost
    
    private void txtVenCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenCodFocusLost
        
        if (!txtVenCod.getText().equalsIgnoreCase(strCodVen)) {
            if (txtVenCod.getText().equals("")) {
                txtVenCod.setText("");
                txtVenNom.setText("");
            }
            else {
                BuscarVendedor("a.co_usr",txtVenCod.getText(),0);
            }
        }
        else
            txtVenCod.setText(strCodVen);
        
    }//GEN-LAST:event_txtVenCodFocusLost
    
    private void txtCliNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliNomActionPerformed
        txtCliNom.transferFocus();
        
    }//GEN-LAST:event_txtCliNomActionPerformed
    
    private void txtVenCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenCodActionPerformed
        txtVenCod.transferFocus();
        
    }//GEN-LAST:event_txtVenCodActionPerformed
    
    
  private void txtCliCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliCodActionPerformed
      txtCliCod.transferFocus();
      
  }//GEN-LAST:event_txtCliCodActionPerformed
  
  //
  //    public void BuscarCliente(String campo,String strBusqueda,int tipo){
  //   // configurarVenConClientes();   //**********************
  //       objVenConCli.setTitle("Listado de Clientes");
  //        if (objVenConCli.buscar(campo, strBusqueda ))
  //        {
  //              txtCliCod.setText(objVenConCli.getValueAt(1));
  //              txtCliNom.setText(objVenConCli.getValueAt(2));
  //              txtCliDir.setText(objVenConCli.getValueAt(3));
  //              strTipPer_glo = objVenConCli.getValueAt(6);
  //              strCodTipPerCli =  objVenConCli.getValueAt(7);
  //              FndVenToCli();
  //              CalculoPago();
  //        }
  //        else
  //        {     objVenConCli.setCampoBusqueda(tipo);
  //              objVenConCli.cargarDatos();
  //              objVenConCli.show();
  //             if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE)
  //            {
  //                txtCliCod.setText(objVenConCli.getValueAt(1));
  //                txtCliNom.setText(objVenConCli.getValueAt(2));
  //                txtCliDir.setText(objVenConCli.getValueAt(3));
  //                strTipPer_glo = objVenConCli.getValueAt(6);
  //                strCodTipPerCli =  objVenConCli.getValueAt(7);
  //                FndVenToCli();
  //                CalculoPago();
  //             }
  //              else{
  //                    txtCliCod.setText(strCodCli);
  //                    txtCliNom.setText(strDesLarCli);
  //                  }
  //        }
  //
  //
  //
  //
  //
  //   }
  //
  
  
  
  
  
  public void BuscarCliente(String campo,String strBusqueda,int tipo){
      objVenConCli.setTitle("Listado de Clientes");
      if (objVenConCli.buscar(campo, strBusqueda )) {
     
          txtCliCod.setText(objVenConCli.getValueAt(1));
          txtCliNom.setText(objVenConCli.getValueAt(2));
          txtCliDir.setText(objVenConCli.getValueAt(3));
          strTipPer_glo = objVenConCli.getValueAt(6);
          Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
          Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
          txtcli_telefono.setText(objVenConCli.getValueAt(4));
          txtcli_identificacion.setText(objVenConCli.getValueAt(5));
          txtcli_ciudad.setText(objVenConCli.getValueAt(9));
          txtVenCod.setText(objVenConCli.getValueAt(10));
          txtVenNom.setText(objVenConCli.getValueAt(11));
          strCodTipPerCli =  objVenConCli.getValueAt(12);
          stIvaVen =  objVenConCli.getValueAt(13);
          CalculoPago();
        
      }
      else
      {     objVenConCli.setCampoBusqueda(tipo);
            objVenConCli.cargarDatos();
            objVenConCli.show();
            if (objVenConCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
             
                txtCliCod.setText(objVenConCli.getValueAt(1));
                txtCliNom.setText(objVenConCli.getValueAt(2));
                txtCliDir.setText(objVenConCli.getValueAt(3));
                strTipPer_glo = objVenConCli.getValueAt(6);
                Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
                Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
                txtcli_telefono.setText(objVenConCli.getValueAt(4));
                txtcli_identificacion.setText(objVenConCli.getValueAt(5));
                txtcli_ciudad.setText(objVenConCli.getValueAt(9));
                txtVenCod.setText(objVenConCli.getValueAt(10));
                txtVenNom.setText(objVenConCli.getValueAt(11));
                strCodTipPerCli =  objVenConCli.getValueAt(12);
                stIvaVen =  objVenConCli.getValueAt(13);
                CalculoPago();
               
            }
            else{
             
                txtCliCod.setText(strCodCli);
                txtCliNom.setText(strDesLarCli);
               
            }
      }
      
  }
  
  
  
  
  
  private void txtVenNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVenNomActionPerformed
      
      txtVenNom.transferFocus();
      
  }//GEN-LAST:event_txtVenNomActionPerformed
  
  
  
  private void FndVenToCli(){
            /*
             * Obteniendo el maximo porcentaje de descuento para el cliente
             */
      try{
          if(objTooBar.getEstado() == 'n' ||  objTooBar.getEstado()=='m'){
              java.sql.Connection conVenToCli = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
              if (conVenToCli!=null){
                  Statement stmCliVen = conVenToCli.createStatement();
                  ResultSet rstCliVen = stmCliVen.executeQuery(
                  "select cli.co_ven, ven.tx_nom from tbm_cli as cli , tbm_usr as ven where " +
                  " cli.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                  " cli.co_cli = " + Integer.parseInt(txtCliCod.getText()) +  " and " +
                  " ven.co_usr = cli.co_ven "
                  );
                  
                  if(rstCliVen.next()){
                      txtVenCod.setText(rstCliVen.getInt("co_ven")+"");
                      txtVenNom.setText(
                      (rstCliVen.getString("tx_nom")==null)?"":rstCliVen.getString("tx_nom")
                      );
                  }
                  rstCliVen.close();
                  stmCliVen.close();
                  conVenToCli.close();
                  rstCliVen = null;
                  stmCliVen = null;
                  conVenToCli = null;
              }
          }
      }
      
      catch(SQLException Evt) {
          objUti.mostrarMsgErr_F1(jfrThis, Evt);
      }
      
      catch(Exception Evt) {
          objUti.mostrarMsgErr_F1(jfrThis, Evt);
      }
      
      
  }
  
  
    /*
     * Agrega el listener para detectar que hubo algun cambio en la caja de texto
     */
  private void addListenerCambio(){
      objlisCambios = new LisTextos();
      //Cabecera
      txtCot.setText("");
      txtCot.getDocument().addDocumentListener(objlisCambios);
      txtCliCod.getDocument().addDocumentListener(objlisCambios);
      txtCliNom.getDocument().addDocumentListener(objlisCambios);
      txtCliDir.getDocument().addDocumentListener(objlisCambios);
      //txtFecDoc.getDocument().addDocumentListener(objlisCambios);
      txtVenCod.getDocument().addDocumentListener(objlisCambios);
      txtVenNom.getDocument().addDocumentListener(objlisCambios);
      txtAte.getDocument().addDocumentListener(objlisCambios);
      
      
      //Pie de pagina
      txaObs1.getDocument().addDocumentListener(objlisCambios);
      txaObs2.getDocument().addDocumentListener(objlisCambios);
      txtSub.getDocument().addDocumentListener(objlisCambios);
      txtIva.getDocument().addDocumentListener(objlisCambios);
      //txtDes.setText("0");
      txtTot.getDocument().addDocumentListener(objlisCambios);
      
  }
  
    /*
     * Clase de tipo documenet listener para detectar los cambios en
     * los textcomponent
     */
  class LisTextos implements javax.swing.event.DocumentListener {
      @Override
      public void changedUpdate(javax.swing.event.DocumentEvent e) {
          blnHayCam = true;
      }
      
      @Override
      public void insertUpdate(javax.swing.event.DocumentEvent e) {
          blnHayCam = true;
      }
      
      @Override
      public void removeUpdate(javax.swing.event.DocumentEvent e) {
          blnHayCam = true;
      }
  }
  
  class LisCambioTbl implements javax.swing.event.TableModelListener{
      @Override
      public void tableChanged(javax.swing.event.TableModelEvent e){
          calculaTotal(dblPorIva);
      }
  }
  
    private void butVenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenConActionPerformed
        BuscarVendedor("a.co_usr","",0);
    }//GEN-LAST:event_butVenConActionPerformed
    
    
    
    
    public void BuscarVendedor(String campo,String strBusqueda,int tipo){
        // configurarVenConVendedor();  //************************
        objVenConVen.setTitle("Listado de vendedores");
        if (objVenConVen.buscar(campo, strBusqueda )) {
            txtVenCod.setText(objVenConVen.getValueAt(1));
            txtVenNom.setText(objVenConVen.getValueAt(2));
        }
        else
        {     objVenConVen.setCampoBusqueda(tipo);
              objVenConVen.cargarDatos();
              objVenConVen.show();
              if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                  txtVenCod.setText(objVenConVen.getValueAt(1));
                  txtVenNom.setText(objVenConVen.getValueAt(2));
              }
              else{
                  txtVenCod.setText(strCodVen);
                  txtVenNom.setText(strDesLarVen);
              }
        }
        
    }
    
    
    
    private void butCliConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliConActionPerformed
        
        
        objVenConCli.setTitle("Listado de Clientes");
        objVenConCli.setCampoBusqueda(1);
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCliCod.setText(objVenConCli.getValueAt(1));
            txtCliNom.setText(objVenConCli.getValueAt(2));
            txtCliDir.setText(objVenConCli.getValueAt(3));
            strTipPer_glo = objVenConCli.getValueAt(6);
            Glo_dlbMaxDes = Double.parseDouble(objVenConCli.getValueAt(7));
            Glo_dlbMarUti = Double.parseDouble(objVenConCli.getValueAt(8));
            txtcli_telefono.setText(objVenConCli.getValueAt(4));
            txtcli_identificacion.setText(objVenConCli.getValueAt(5));
            txtcli_ciudad.setText(objVenConCli.getValueAt(9));
            txtVenCod.setText(objVenConCli.getValueAt(10));
            txtVenNom.setText(objVenConCli.getValueAt(11));
            strCodTipPerCli =  objVenConCli.getValueAt(12);
            stIvaVen =  objVenConCli.getValueAt(13);
            
            CalculoPago();
            
            
        }
        
        
    }//GEN-LAST:event_butCliConActionPerformed

    private void butCliCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliCon1ActionPerformed
        // TODO add your handling code here:
        /*Ventas.ZafVen01.ZafVen01_04 obj = new  Ventas.ZafVen01.ZafVen01_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

        int codcli=Integer.parseInt((((txtCliCod.getText()==null) || (txtCliCod.getText().equalsIgnoreCase("")))?"0":txtCliCod.getText()));
        obj.SetvalCodCli(codcli);

        obj.show();
        if(obj.acepta()){
            txtCodForPag.setText(obj.GetCamSel(1));
            txtNomForPag.setText(obj.GetCamSel(2));

            intTipForPag2 = Integer.parseInt(obj.GetCamSel(3));
            dblPes2 = Double.parseDouble(obj.GetCamSel(4));


            blnHayCam=true;
            CalculoPago();

        }*/
        
        mostrarVenConFormaPago(0);
        blnHayCam=true;
        if (intTipForPag2==4){
            habilitarCamposTarCre();
        }else{
            deshabilitarCamposTarCre();
            //CalculoPago();            
        }
        CalculoPago();          

    }//GEN-LAST:event_butCliCon1ActionPerformed

    private void butItmPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmPenActionPerformed
        mostrarItmPenConfEgr( String.valueOf(objZafParSis.getCodigoEmpresa()), String.valueOf(objZafParSis.getCodigoLocal()), txtTipDocCod.getText(), txtDoc.getText() );
    }//GEN-LAST:event_butItmPenActionPerformed

    private void butCliRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliRetActionPerformed
       if(validad()){

       ZafVen02_04 obj7 = new  ZafVen02_04( javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis,  Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtDoc.getText()) );
       obj7.show();

    }


    }//GEN-LAST:event_butCliRetActionPerformed

    private void txtCodTarCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTarCreActionPerformed
        // TODO add your handling code here:
        txtCodTarCre.transferFocus();
    }//GEN-LAST:event_txtCodTarCreActionPerformed

    private void txtCodTarCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTarCreFocusGained
        // TODO add your handling code here:
        strCodTipCre=txtCodTarCre.getText();
        txtCodTarCre.selectAll();
    }//GEN-LAST:event_txtCodTarCreFocusGained

    private void txtCodTarCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTarCreFocusLost
        // TODO add your handling code here:
        if(objTooBar.getEstado()=='c' || objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m'){
            if (txtCodTarCre.getText().equals("")) {
                txtCodTarCre.setText("");
                txtNomTarCre.setText("");
            }else
            mostrarVenConTipoCredito(1);
        }
        
    }//GEN-LAST:event_txtCodTarCreFocusLost

    private void txtNomTarCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTarCreActionPerformed
        // TODO add your handling code here:
        txtNomTarCre.transferFocus();
    }//GEN-LAST:event_txtNomTarCreActionPerformed

    private void txtNomTarCreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTarCreFocusGained
        // TODO add your handling code here:
        strNomTipCre=txtNomTarCre.getText();
        txtNomTarCre.selectAll();
    }//GEN-LAST:event_txtNomTarCreFocusGained

    private void txtNomTarCreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTarCreFocusLost
        // TODO add your handling code here:
        if (txtNomTarCre.getText().equals("")) {
            txtCodTarCre.setText("");
            txtNomTarCre.setText("");
        }else
        mostrarVenConTipoCredito(3);
    }//GEN-LAST:event_txtNomTarCreFocusLost

    private void butConTipCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConTipCreActionPerformed
        // TODO add your handling code here:
        mostrarVenConTipoCredito(0);
    }//GEN-LAST:event_butConTipCreActionPerformed

    private void txtNomEmiTarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmiTarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNomEmiTarActionPerformed

    private void txtNomEmiTarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmiTarFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNomEmiTarFocusGained

    private void txtNomEmiTarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmiTarFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNomEmiTarFocusLost

    private void txtCodigoFormaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoFormaPagoActionPerformed
        // TODO add your handling code here:
        txtCodigoFormaPago.transferFocus();
    }//GEN-LAST:event_txtCodigoFormaPagoActionPerformed

    private void txtCodigoFormaPagoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFormaPagoFocusGained
        // TODO add your handling code here:
        strCodForPag=txtCodigoFormaPago.getText();
        txtCodigoFormaPago.selectAll();
    }//GEN-LAST:event_txtCodigoFormaPagoFocusGained

    private void txtCodigoFormaPagoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFormaPagoFocusLost
        // TODO add your handling code here:
        if(objTooBar.getEstado()=='c' || objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m'){
            if (txtCodigoFormaPago.getText().equals("")) {
                txtCodigoFormaPago.setText("");
                txtNomForPag.setText("");
            }else
            mostrarVenConFormaPago(1);

            CalculoPago();
        }
        else{
            System.out.println("ESTADO TooBar:"+objTooBar.getEstado());
        }
    }//GEN-LAST:event_txtCodigoFormaPagoFocusLost

    private void butFacEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butFacEleActionPerformed
        // TODO add your handling code here:
        enviarFacturaEnLineaSRI();
    }//GEN-LAST:event_butFacEleActionPerformed

    private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomForPagActionPerformed

   private void butGetXmlFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGetXmlFacActionPerformed
      // TODO add your handling code here:
      obtenerXMLFactura();
   }//GEN-LAST:event_butGetXmlFacActionPerformed



private boolean validad(){
   boolean blnRes=true;
   
   if(txtDoc.getText().equals("")){
       mostrarMsg("NO HAY CODIGO DE DOCUMENTO PARA LA BUSQUEDA..");
       blnRes=false;
   }
   
   
   return blnRes;
}



private void mostrarItmPenConfEgr(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc )
{
  String strSql02="";
  try
  {
        strSql02 =" SELECT * FROM ";
        strSql02+=" ( ";
        strSql02+="     SELECT x.*, ";
        strSql02+="            CASE WHEN (x.st_meregrfisbod = 'N' AND nd_CanTotGui>0) THEN (nd_Can - nd_canTotGui) ELSE CASE WHEN (x.st_meregrfisbod = 'A' ) THEN nd_canPen ";
        strSql02+="            ELSE (nd_Can - (nd_CanCon + nd_canNunRec + nd_canTra)) END END as nd_canTotPen ";
        strSql02+="     FROM  ";
        strSql02+="     (     ";
        strSql02+="         SELECT a.st_meregrfisbod, a.tx_codalt, b.tx_codalt2, a.tx_nomitm,   ";              
        strSql02+="                CASE WHEN c.nd_Can IS NULL THEN 0 ELSE abs(c.nd_Can) END as nd_Can, ";             
        strSql02+="                CASE WHEN a.nd_CanCon = 0 THEN abs(c.nd_CanCon) ELSE abs(a.nd_CanCon) END as nd_CanCon,  ";             
        strSql02+="                CASE WHEN c.nd_canNunRec = 0 THEN abs(a.nd_CanNunRec) ELSE abs(c.nd_canNunRec) END as nd_canNunRec,  ";           
        strSql02+="                CASE WHEN c.nd_canTra IS NULL THEN 0 ELSE abs(c.nd_canTra) END as nd_canTra, ";              
        strSql02+="                CASE WHEN c.nd_canPen IS NULL THEN 0 ELSE abs(c.nd_canPen) END as nd_canPen, ";            
        strSql02+="                CASE WHEN a.nd_canTotGuiSec IS NULL THEN 0 ELSE abs(a.nd_canTotGuiSec) END as nd_canTotGui  ";
        strSql02+="         FROM tbm_detguirem as a  ";     
        strSql02+="         INNER JOIN tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) ";       
        strSql02+="         INNER JOIN tbm_detmovinv as c on (a.co_emprel=c.co_emp and a.co_locrel=c.co_loc and a.co_tipdocrel=c.co_tipdoc and a.co_docrel=c.co_doc and a.co_regrel=c.co_reg) ";      
        strSql02+="         WHERE c.co_emp="+strCodEmp;
        strSql02+="         AND c.co_loc="+strCodLoc;   
        strSql02+="         AND c.co_tipdoc="+strCodTipDoc;
        strSql02+="         AND c.co_doc="+strCodDoc;
        strSql02+="     ) as x ";
        strSql02+=" ) as b ";
        strSql02+=" WHERE b.nd_canTotPen>0 ";
    
        System.out.println("ZafVen02.mostrarItmPenConfEgr: " + strSql02);

        Ventas.ZafVen21.ZafVen21_02 obj1 = new  Ventas.ZafVen21.ZafVen21_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, this, strSql02 );
        obj1.show();

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this,e);  }
}



    
    private double getIva(java.sql.Connection ConTem){
        double dblIvaActual = 0;
        try{
            if (ConTem!=null){
                stmCot=ConTem.createStatement();
                sSQL= "SELECT nd_ivaVen as porIva from tbm_emp " +
                "where co_emp = " + objZafParSis.getCodigoEmpresa();  //<== Verificando que seleccione la empresa actual
                ResultSet rstIva = stmCot.executeQuery(sSQL);
                if(rstIva.next()){
                    dblIvaActual = objUti.redondeo(rstIva.getDouble("porIva"),2);
                }
                rstIva.close();
                stmCot.close();
                rstIva=null;
                stmCot=null;
            }
        }
        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
        catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
        return dblIvaActual;
    }
    
    
    
    public void  noEditable(boolean blnEditable){
        java.awt.Color colBack = txtCot.getBackground();
        
        txtDoc.setEditable(blnEditable);
        
        colBack = txtSub.getBackground();
        
        
        txtSub.setEditable(blnEditable);
        txtSub.setBackground(colBack);
        
        txtIva.setEditable(blnEditable);
        txtIva.setBackground(colBack);
        
        txtComSol.setEditable(blnEditable);
        txtComSol.setBackground(colBack);
        
        txtTot.setEditable(blnEditable);
        txtTot.setBackground(colBack);
    }
    
    public void  clnTextos(){
        if(blnLimpiar){
            //Cabecera
            txtTipDocCod.setText("");
            txtTipDocAli.setText("");
            txtTipDocNom.setText("");
            txtFac.setText("");
            txtGuia.setText("");
            txtDoc.setText("");
            txtAte.setText("");
            txtPed.setText("");
            
            strCodCli="";
            strDesLarCli="";
            strCodCli="";
            strDesLarCli="";
            
            
            txtCot.setText("");
            txtCliCod.setText("");
            txtCliNom.setText("");
            txtCliDir.setText("");
            txtFecDoc.setText("");
            txtVenCod.setText("");
            txtVenNom.setText("");
            
            
            //Detalle
            objTblMod.removeAllRows();
             //Detalle
            objTblModPag.removeAllRows();
            
            //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            //txtDes.setText("0");
            txtComSol.setText("0");
            txtTot.setText("0");
            lblCotNumDes.setText("Factura");
            
            
            
            // Tab de Otros
            txtPartida.setText("");
            txtTransporte.setText("");
            
            //Tab de Asiento Contable
            objAsiDia.inicializar();
            objAsiDia.setEditable(true);
            
            //Tarjeta de Credito.
            deshabilitarCamposTarCre();            
            
        }
        
    }
    
    public void  calculaTotal(double dblPorIvaCon){
        double PorIva=dblPorIvaCon;
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        
        for (int i=0;i<tblDat.getRowCount();i++){
            dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
            dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
            
            
            if(tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)
                dblIva = dblIva + 0;
            else{
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * PorIva)==0)?0:(dblTmp * PorIva)/100) ;
            }
            
        }
        
        /////////////////////////////////////////////////////////////
        dblSubtotalCot = dblSub;
        dblIvaCot = objUti.redondear(dblIva,intNumDec);
        dblTotalCot = dblSubtotalCot + dblIvaCot;
        dblTotalCot = objUti.redondear(dblTotalCot ,intNumDec);
        dblSubtotalCot = objUti.redondear(dblSubtotalCot ,intNumDec);
        
        
//        if(stIvaVen.equals("N")){
//             txtSub.setText( "" + dblSubtotalCot );
//             txtIva.setText( "0.00"  );
//             txtTot.setText( ""+ dblSubtotalCot);
//             dblTotalCot=dblSubtotalCot;
//               dblIvaCot=0;
//        }else{
            txtSub.setText( "" + dblSubtotalCot );
            txtIva.setText( "" + dblIvaCot );
            txtTot.setText( ""+ dblTotalCot);
       // }
        /////////////////////////////////////////////////////////////
        
    }
    
    
    
    
    
    
    public String getUnidad(String int_co_itm){
        String strDesUni = "";
        try{
            java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conUni!=null){
                java.sql.Statement stmUni = conUni.createStatement();
                String sSQL= "SELECT var.tx_descor from tbm_inv as inv " +
                " inner join tbm_var as var on (inv.co_uni = var.co_reg) "+
                " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                "   var.co_grp = 5  and inv.co_itm = " + int_co_itm ;
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next())
                    strDesUni = (rstUni.getString("tx_descor")==null)?"":rstUni.getString("tx_descor");
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
            }
        }
        catch(java.sql.SQLException Evt) {  System.out.println(Evt); return strDesUni+"";  }
        catch(Exception Evt) {  System.out.println(Evt);  return strDesUni+"";  }
        return strDesUni;
    }
    
    
    
    
    
    public void  refrescaDatos(){
        try{
            int intNumCot = 0;
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(rstCab != null){
                //Detalle
                intNumCot = rstCab.getInt("co_doc");
                
                
                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+rstCab.getInt("co_emp")+" AND co_loc="+rstCab.getInt("co_loc")+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";
                
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+rstCab.getInt("co_emp")+" and a.co_loc="+rstCab.getInt("co_loc")+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
            
                
                sSQL  = "SELECT a.tx_codalt, a.tx_unimed, a.tx_nomitm, a.co_bod, a.nd_can, a.nd_preuni , inv.st_ser, inv.co_ctaegr " +
                " ,a.nd_pordes, a.st_ivaCom, a.co_itm, a.tx_codalt2 ";
                sSQL +=strAux;
                sSQL +=strAux2;
                sSQL +=" "
                //+ " ,tr.co_locrel ,tr.co_tipdocrel , tr.co_docrel  ,tr.co_regrel, tr.co_emprel "
                + ", a.co_itmact  FROM tbm_detMovInv AS a  " +
                " INNER JOIN tbm_inv AS inv on (a.co_emp = inv.co_emp and a.co_itm = inv.co_itm) " +
               // " LEFT JOIN tbr_detmovinv AS tr ON (tr.co_emp=a.co_emp and tr.co_loc=a.co_loc and tr.co_tipdoc=a.co_tipdoc and tr.co_doc=a.co_doc and tr.co_reg=a.co_reg) "+
                "WHERE " +
                " a.co_emp = " + rstCab.getInt("co_emp") + " and " +
                " a.co_loc = " + rstCab.getInt("co_loc")  + " and " +
                " a.co_doc = " + intNumCot                       + " and " +
                " a.co_tipDoc = " + txtTipDocCod.getText() + " order by a.co_reg";
                
                
                
              //  System.out.println(" TOTAL >>  "+ sSQL );
                
                java.sql.Statement stmCab=con.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                
                
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = con.createStatement();
                String strUnidad="",strCodAlt="",strSer="", strTer="";
                Vector vecData = new Vector();
                for(int i=0 ; rst.next() ; i++){
                    java.util.Vector vecReg = new java.util.Vector();
                    
                    strCodAlt =  rst.getString("tx_codalt"); // (rstAux.getString("tx_codalt")==null?"":rstAux.getString("tx_codalt"));
                    strUnidad = rst.getString("tx_unimed"); // (rstAux.getString("tx_descor")==null?"":rstAux.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                    
                    
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    dblCan    = Math.abs(rst.getDouble("nd_can")) ;
                    vecReg.add(INT_TBL_CANMOV, new Double(dblCan));
                    
                    vecReg.add(INT_TBL_STKBOD,"");
                    vecReg.add(INT_TBL_ULTCOS,"");
                    
                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    
                    vecReg.add(INT_TBL_UTILIDAD,"");
                    vecReg.add(INT_TBL_PREUNI2, new Double(rst.getDouble("nd_preuni")) );
                    
                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivaCom");
                    Boolean blnIva = strIva.equals("S")?true:false;
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    
                    dblPre    = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    //*********************System.out.println("TOTAL >>  "+dblValDes +" >> "+ dblTotal);
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);
                    
                    
                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, "");
                    
                    vecReg.add(INT_TBL_BLNPRE,"");
                    vecReg.add(INT_TBL_CANORI,new Double(dblCan));
                    vecReg.add(INT_TBL_BODORI,new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_CODORI,rst.getString("co_itm"));
                    
                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_ITMTER, strTer );
                    
                    vecReg.add(INT_TBL_CODLOCREL, null ); // rst.getString("co_locrel") );
                    vecReg.add(INT_TBL_CODTIPREL, null ); //  rst.getString("co_tipdocrel") );
                    vecReg.add(INT_TBL_CODDOCREL,  null ); // rst.getString("co_docrel") );
                    vecReg.add(INT_TBL_CODREGREL,  null ); // rst.getString("co_regrel") );
                    vecReg.add(INT_TBL_CODEMPREL,  null ); // rst.getString("co_emprel") );
                    vecReg.add(INT_TBL_CODITMACT,   rst.getString("co_itmact"));
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                    
                    vecReg.add(INT_TBL_CODCTAEGR, rst.getString("co_ctaegr") );  
                             
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                stmAux.close();
                rst.close();
                //   System.out.println("DETALLE>>>>OKI ");
//                calculaTotal(dblPorIva);   // 
                
                //   System.out.println("DETALLE>>>>OKI 1 ");
                
                lblCotNumDes.setText("Factura No. " + txtFac.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");
                
                //  System.out.println("DETALLE>>>>OKI 3 ");
                
                 /*
                  * CARGANDO DATOS DEL TAB FORMA DE PAGO
                  */
                
                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag");
                
                txtCodForPag.setText(""+strCo_ForPag);
                txtNomForPag.setText(""+ rstCab.getString("tx_des"));

                
                refrescaPagos(con, rstCab.getInt("co_emp"), rstCab.getInt("co_loc"), rstCab.getInt("co_tipdoc"), rstCab.getInt("co_doc"), rstCab.getInt("co_forPag"));
                
                
                
                    /*
                     * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                     */
                objAsiDia.consultarDiario( rstCab.getInt("co_emp") , rstCab.getInt("co_loc"), Integer.parseInt(txtTipDocCod.getText()), intNumCot);
                
                
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCab.getString("st_reg");
                
                switch(strStatus.charAt(0)){
                    case 'I':
                        objTooBar.setEstadoRegistro("Anulado");
                        break;
                    case 'A':
                        objTooBar.setEstadoRegistro("Activo");
                        break;
                    case 'P':
                        objTooBar.setEstadoRegistro("Pendiente");
                        break;
                    case 'D':
                        objTooBar.setEstadoRegistro("Denegado");
                        break;
                    case 'R':
                        objTooBar.setEstadoRegistro("Falta Imprimir");
                        break;
                    default:
                        objTooBar.setEstadoRegistro(strStatus);
                        break;
                }
                
                
                stmAux.close();
                stmCab.close();
                con.close();
            }
            blnHayCam = false; // Seteando que no se ha hecho cambios
            
        }//fin Try
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            
        }
        
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        
    }
    



    public void _getCargarDetReg()
    {
        try
        {
            int intNumCot = 0;
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(rstCab != null)
            {
                //Detalle
                intNumCot = rstCab.getInt("co_doc");

                String strAux = ",CASE WHEN (" +
                                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+rstCab.getInt("co_emp")+" AND co_loc="+rstCab.getInt("co_loc")+" " +
                                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                                " ))) THEN 'S' ELSE 'N' END  as isterL";

                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                                " WHERE a.co_emp="+rstCab.getInt("co_emp")+" and a.co_loc="+rstCab.getInt("co_loc")+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                                " THEN 'S' ELSE 'N' END AS proconf  ";


                sSQL  = "SELECT a.tx_codalt, a.tx_unimed, a.tx_nomitm, a.co_bod, a.nd_can, a.nd_preuni , inv.st_ser, inv.co_ctaegr  " +
                        "      ,a.nd_pordes, a.st_ivaCom, a.co_itm, a.tx_codalt2 ";
                sSQL +=strAux;
                sSQL +=strAux2;
                sSQL +=" "
                     //        + " ,tr.co_locrel ,tr.co_tipdocrel , tr.co_docrel  ,tr.co_regrel, tr.co_emprel "
                     + " , a.co_itmact  FROM tbm_detMovInv AS a  " 
                     + " INNER JOIN tbm_inv AS inv on (a.co_emp = inv.co_emp and a.co_itm = inv.co_itm) " +
                     //" LEFT JOIN tbr_detmovinv AS tr ON (tr.co_emp=a.co_emp and tr.co_loc=a.co_loc and tr.co_tipdoc=a.co_tipdoc and tr.co_doc=a.co_doc and tr.co_reg=a.co_reg) "+
                     " WHERE a.co_emp = " + rstCab.getInt("co_emp") +
                     " AND a.co_loc = " + rstCab.getInt("co_loc")  + 
                     " AND a.co_doc = " + intNumCot +
                     " AND a.co_tipDoc = " + txtTipDocCod.getText() + 
                     " ORDER BY a.co_reg";

                //System.out.println("TOTAL >>  "+ sSQL );

                java.sql.Statement stmCab=con.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;


                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = con.createStatement();
                String strUnidad="",strCodAlt="",strSer="", strTer="";
                Vector vecData = new Vector();
                for(int i=0 ; rst.next() ; i++)
                {
                    java.util.Vector vecReg = new java.util.Vector();

                    strCodAlt =  rst.getString("tx_codalt"); // (rstAux.getString("tx_codalt")==null?"":rstAux.getString("tx_codalt"));
                    strUnidad = rst.getString("tx_unimed"); // (rstAux.getString("tx_descor")==null?"":rstAux.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));


                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    dblCan    = Math.abs(rst.getDouble("nd_can")) ;
                    vecReg.add(INT_TBL_CANMOV, new Double(dblCan));

                    vecReg.add(INT_TBL_STKBOD,"");
                    vecReg.add(INT_TBL_ULTCOS,"");

                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));

                    vecReg.add(INT_TBL_UTILIDAD,"");
                    vecReg.add(INT_TBL_PREUNI2, new Double(rst.getDouble("nd_preuni")) );

                    vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    String strIva = rst.getString("st_ivaCom");
                    Boolean blnIva = strIva.equals("S")?true:false;
                    vecReg.add(INT_TBL_BLNIVA, blnIva);

                    dblPre    = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");

                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    //*********************System.out.println("TOTAL >>  "+dblValDes +" >> "+ dblTotal);
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);


                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, "");

                    vecReg.add(INT_TBL_BLNPRE,"");
                    vecReg.add(INT_TBL_CANORI,new Double(dblCan));
                    vecReg.add(INT_TBL_BODORI,new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_CODORI,rst.getString("co_itm"));

                    vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codalt2"));
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_ITMTER, strTer );

                    vecReg.add(INT_TBL_CODLOCREL,  null ); //       rst.getString("co_locrel") );
                    vecReg.add(INT_TBL_CODTIPREL,  null ); // rst.getString("co_tipdocrel") );
                    vecReg.add(INT_TBL_CODDOCREL,  null ); // rst.getString("co_docrel") );
                    vecReg.add(INT_TBL_CODREGREL,  null ); // rst.getString("co_regrel") );
                    vecReg.add(INT_TBL_CODEMPREL,  null ); // rst.getString("co_emprel") );
                    vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact"));
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                    
                    vecReg.add(INT_TBL_CODCTAEGR, rst.getString("co_ctaegr"));



                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                stmAux.close();
                rst.close();
                //  System.out.println("DETALLE>>>>OKI ");
//                calculaTotal(dblPorIva);
                //  System.out.println("DETALLE>>>>OKI 1 ");

                lblCotNumDes.setText("Factura No. " + txtFac.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");

                //  System.out.println("DETALLE>>>>OKI 3 ");

                 /*
                  * CARGANDO DATOS DEL TAB FORMA DE PAGO
                  */
                // refrescaPagos(intNumCot, rstDatosDocCab.getInt("co_forPag"));
                String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag");

                txtCodForPag.setText(""+strCo_ForPag);
                txtNomForPag.setText(""+ rstCab.getString("tx_des"));

            

                refrescaPagos(con,rstCab.getInt("co_emp"), rstCab.getInt("co_loc"), rstCab.getInt("co_tipdoc"), rstCab.getInt("co_doc"), rstCab.getInt("co_forPag"));



                    /*
                     * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                     */
                objAsiDia.consultarDiario( rstCab.getInt("co_emp") , rstCab.getInt("co_loc"), Integer.parseInt(txtTipDocCod.getText()), intNumCot);


                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCab.getString("st_reg");

                switch(strStatus.charAt(0)){
                    case 'I':
                        objTooBar.setEstadoRegistro("Anulado");
                        break;
                    case 'A':
                        objTooBar.setEstadoRegistro("Activo");
                        break;
                    case 'P':
                        objTooBar.setEstadoRegistro("Pendiente");
                        break;
                    case 'D':
                        objTooBar.setEstadoRegistro("Denegado");
                        break;
                    case 'R':
                        objTooBar.setEstadoRegistro("Falta Imprimir");
                        break;
                    default:
                        objTooBar.setEstadoRegistro(strStatus);
                        break;
                }


                stmAux.close();
                stmCab.close();
                con.close();
            }
            blnHayCam = false; // Seteando que no se ha hecho cambios

        }//fin Try
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);

        }

        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }

    }
    
public void refrescaPagos(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc,  int intCodDoc, int intCodForPag ){
  java.sql.ResultSet rstDetPag;
  java.sql.Statement stmDetPag;
  String strSql="";
  try{
     stmDetPag = conn.createStatement();
     Vector vecData = new Vector();

     java.util.Calendar calFecPag = java.util.Calendar.getInstance();
     Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");


      strSql = "Select * from tbm_pagMovInv where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipDoc="+intCodTipDoc+" "
      + " and co_doc="+intCodDoc+" and st_reg IN ('A','F') ORDER BY co_reg ";
      rstDetPag = stmDetPag.executeQuery(strSql);
      while(rstDetPag.next()){
        java.util.Vector vecReg = new java.util.Vector();

        calFecPag.setTime(rstDetPag.getDate("fe_ven"));
        dtePckPag.setAnio(calFecPag.get(java.util.Calendar.YEAR));
        dtePckPag.setMes((calFecPag.get(java.util.Calendar.MONTH)+1));
        dtePckPag.setDia(calFecPag.get(java.util.Calendar.DAY_OF_MONTH));

        vecReg.add(INT_TBL_PAGLIN, "");
        vecReg.add(INT_TBL_PAGCRE, rstDetPag.getString("ne_diaCre"));
        vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText());
        vecReg.add(INT_TBL_PAGRET, rstDetPag.getString("nd_porRet"));
        vecReg.add(INT_TBL_PAGMON, (rstDetPag.getDouble("mo_pag") * -1)+ "" );
        vecReg.add(INT_TBL_PAGGRA, rstDetPag.getString("ne_diaGra"));
        vecReg.add(INT_TBL_PAGCOD, rstDetPag.getString("co_tipret"));
        vecReg.add(INT_TBL_PAGSOP, rstDetPag.getString("st_sop"));
        vecData.add(vecReg);
      }
      rstDetPag.close();
      rstDetPag=null;

      objTblModPag.setData(vecData);
      tblPag.setModel(objTblModPag);

      stmDetPag.close();
      stmDetPag=null;

      dtePckPag=null;
           

            
//            //Borrando los datos actuales del jtable
//
//            javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblPag.getModel());
//            modTbl.setRowCount(0);
//
//                    /*
//                     * LLenando la tabla de pagos
//                     */
//
//            java.util.Calendar calFecPag = java.util.Calendar.getInstance();
//            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
//
//            while(rstDetPag.next()){
//
//                java.util.Vector dataPagos = new java.util.Vector();
//
//                dataPagos.addElement(new Object());
//                dataPagos.addElement(rstDetPag.getObject("ne_diaCre"));
//                        /*
//                         * Formateando la fecha que viene desde la base para presentarla en d/m/y
//                         */
//                calFecPag.setTime(rstDetPag.getDate("fe_ven"));
//                dtePckPag.setAnio(calFecPag.get(java.util.Calendar.YEAR));
//                dtePckPag.setMes((calFecPag.get(java.util.Calendar.MONTH)+1));
//                dtePckPag.setDia(calFecPag.get(java.util.Calendar.DAY_OF_MONTH));
//
//                dataPagos.addElement(dtePckPag.getText());
//                dataPagos.addElement(rstDetPag.getObject("nd_porRet"));
//                dataPagos.addElement((rstDetPag.getDouble("mo_pag") * -1)+ "" );
//                dataPagos.addElement(rstDetPag.getObject("ne_diaGra"));
//                dataPagos.addElement(rstDetPag.getObject("co_tipret"));
//                dataPagos.addElement(rstDetPag.getString("st_sop"));
//
//                ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(dataPagos);
//            }
//
//            rstDetPag.close();
//            stmDetPag.close();
//


 }catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  catch(Exception Evt)  { objUti.mostrarMsgErr_F1(jfrThis, Evt); }

}    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        //JOptionPane oppMsg=new JOptionPane();
        //String strTit;
        //strTit="Mensaje del sistema Zafiro";
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro() {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
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
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    
    private boolean cargarRegdeCotizacion(ResultSet rstCotCab) {
        boolean blnRes=true;
        try {
            if (cargarCabReg2(rstCotCab)) {
                //System.out.println("Aqui........");
                refrescaDatos2(rstCotCab);
                generaAsiento();
            }
            else {
                MensajeInf("Error al cargar registro");
                blnHayCam=false;
            }
        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean cargarCabReg2(ResultSet rstCotCab) {
        int intPosRel;
        boolean blnRes=true;
        try {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null) {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";
                strSQL= "SELECT cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                " Usr.co_usr as co_ven, Usr.tx_nom as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                " CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                " CotCab.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                " CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                " CotCab.st_reg, CotCab.ne_val " + // Campo para saber si esta anulado o no
                " ,Cli,tx_tipper , Cli.co_tipper " +
                " , Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar, Cli.st_ivaven " +
                " FROM tbm_cabCotVen as CotCab " +
                " left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_cli = cli.co_cli) " +
                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)  " +
                " left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_ven ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                " Where  CotCab.co_emp=" +  rstCotCab.getString("co_emp") +
                " AND CotCab.co_loc=" +  rstCotCab.getString("co_loc") +
                " AND CotCab.co_cot=" +  rstCotCab.getString("co_cot") ;
                
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next()) {
                    txtCot.setText(rst.getString("co_cot"));
                    txtCliCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtCliNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtCliDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    
                    
                    txtcli_telefono.setText( rst.getString("tx_tel") );
                    txtcli_identificacion.setText( rst.getString("tx_ide") );
                    txtcli_ciudad.setText( rst.getString("tx_desLar") );
                    
                    
                    strTipPer_glo=rst.getString("tx_tipper");
                    strCodTipPerCli =  rst.getString("co_tipper");
                    stIvaVen =  rst.getString("st_ivaven");
                    
                    //   rst2.close();
                    //  rst2=null;
                    
                    //*****************
                    intNumDiaVal = rst.getInt("ne_val");
                    dateFecCot = objUti.formatearFecha(rst.getDate("fe_cot"),"dd/MM/yyyy");
                    intTipMod = 2;
                    //*****************
                    
                    if(rst.getDate("fe_cot")==null){
                        txtFecDoc.setText("");
                    }else{
                        java.util.Date dateObj = rst.getDate("fe_cot");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH)+1     ,
                        calObj.get(java.util.Calendar.YEAR)        );
                    }
                    
                    txtVenCod.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                    txtVenNom.setText(((rst.getString("nomcom")==null)?"":rst.getString("nomcom")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
                    double dblSub = ((rst.getString("nd_sub")==null)?0:objUti.redondear(rst.getDouble("nd_sub"),objZafParSis.getDecimalesMostrar()));
                    txtSub.setText(""+dblSub);
                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));

                    txtCodigoFormaPago.setText(String.valueOf(rstCotCab.getInt("co_forpag")));
                    txtNomForPag.setText(rstCotCab.getString("tx_des"));
                    txtCodForPag.setText(txtCodigoFormaPago.getText());                
                    Glo_dlbMaxDes=objUti.parseDouble( rst.getDouble("nd_maxdes"));
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    strstReg_glo = strAux;
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                
                blnHayCam=false;
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    
    
    public void refrescaDatos2(ResultSet rstCotCab){
        try{
            int intNumCot = 0;
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(rstCotCab != null){
                //Detalle                
                
                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc=1 AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";
                
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
                
                sSQL  = " SELECT a.tx_codalt, a.tx_codalt2, var.tx_descor ,a.tx_nomitm, a.co_bod, a.nd_can ,a.nd_preuni, a.nd_pordes, a.st_ivaven, a.co_itm , inv.st_ser, inv.co_ctaegr ";
                sSQL +=strAux;
                sSQL +=strAux2;
                sSQL +="  FROM tbm_detCotven AS a " +
                " INNER JOIN tbm_inv AS inv ON (a.co_emp = inv.co_emp and a.co_itm = inv.co_itm) " +
                " LEFT outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                " WHERE a.co_emp = "+objZafParSis.getCodigoEmpresa()+" and " +
                " a.co_loc = "+objZafParSis.getCodigoLocal()+"   and " +
                " a.co_cot = "+rstCotCab.getInt("co_cot")+" order by a.co_reg";                
                
                //System.out.println("ZafVen02.refrescaDatos2:"+ sSQL );
                
                java.sql.Statement stmCab=con.createStatement();
                java.sql.ResultSet rst = stmCab.executeQuery(sSQL);
                double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0;
                
                java.sql.Statement stmAux;
                java.sql.ResultSet rstAux;
                stmAux = con.createStatement();
                String strUnidad="",strCodAlt="", strCodAlt2="",strSer="", strTer="";
                Vector vecData = new Vector();
                for(int i=0 ; rst.next() ; i++){
                    java.util.Vector vecReg = new java.util.Vector();
                    
                    //                         strSQL = "";
                    //                         strSQL = " Select detcot.tx_codalt,detcot.tx_codalt2,tx_descor from tbm_detcotven as detcot left outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) left outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                    //                                 " where detcot.co_emp  = " + objZafParSis.getCodigoEmpresa() +" and detcot.co_loc = " + objZafParSis.getCodigoLocal() + " and detcot.co_cot = " + rst.getInt("co_cot") +" and detcot.co_itm = " + rst.getString("co_itm") ;
                    //
                    //                        rstAux = stmAux.executeQuery(strSQL);
                    //                         if (rstAux.next())
                    //                         {
                    strCodAlt = (rst.getString("tx_codalt")==null?"":rst.getString("tx_codalt"));
                    strCodAlt2 = (rst.getString("tx_codalt2")==null?"":rst.getString("tx_codalt2"));
                    strUnidad = (rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                    strTer = (rst.getString("isterl")==null?"":rst.getString("isterl"));
                    
                    //                         }
                    //                         rstAux.close();
                    
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_ITMALT, strCodAlt);
                    vecReg.add(INT_TBL_BUTITM, "");
                    vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                    vecReg.add(INT_TBL_UNIDAD, strUnidad);
                    vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_BUTBOD, "");
                    vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
                    
                    vecReg.add(INT_TBL_STKBOD,"");
                    vecReg.add(INT_TBL_ULTCOS,"");
                    
                    vecReg.add(INT_TBL_PREUNI, new Double(rst.getDouble("nd_preuni")));
                    
                    vecReg.add(INT_TBL_UTILIDAD,"");
                    vecReg.add(INT_TBL_PREUNI2, new Double(rst.getDouble("nd_preuni")) );
                    
                    //vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                    vecReg.add(INT_TBL_PORDES, objUti.parseDouble(rst.getDouble("nd_pordes")) );
                    String strIva = rst.getString("st_ivaven");
                    Boolean blnIva = strIva.equals("S")?true:false;
                    vecReg.add(INT_TBL_BLNIVA, blnIva);
                    dblCan    = rst.getDouble("nd_can");
                    dblPre    = rst.getDouble("nd_preuni");
                    dblPorDes = rst.getDouble("nd_pordes");
                    
                    dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblPorDes / 100)) ;
                    dblTotal  = (dblCan * dblPre)- dblValDes ;
                    //*********************System.out.println("TOTAL >>  "+dblValDes +" >> "+ dblTotal);
                    dblTotal =  objUti.redondear(dblTotal,3);
                    dblTotal =  objUti.redondear(dblTotal,2);
                    
                    
                    vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                    vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_ESTADO, "E");
                    vecReg.add(INT_TBL_IVATXT, "");
                    
                    
                    vecReg.add(INT_TBL_BLNPRE,"");
                    vecReg.add(INT_TBL_CANORI,new Double(dblCan));
                    vecReg.add(INT_TBL_BODORI,new Integer(rst.getInt("co_bod")));
                    vecReg.add(INT_TBL_CODORI,rst.getString("co_itm"));
                    
                    vecReg.add(INT_TBL_ITMALT2, strCodAlt2);
                    vecReg.add(INT_TBL_ITMSER, strSer );
                    vecReg.add(INT_TBL_ITMTER, strTer );
                    
                    vecReg.add(INT_TBL_CODLOCREL, "" );
                    vecReg.add(INT_TBL_CODTIPREL, "" );
                    vecReg.add(INT_TBL_CODDOCREL, "" );
                    vecReg.add(INT_TBL_CODREGREL, "" );
                    vecReg.add(INT_TBL_CODEMPREL, "" );
                    
                    vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itm"));
                    vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                    
                    vecReg.add(INT_TBL_CODCTAEGR, rst.getString("co_ctaegr"));
                    
                    
                    vecData.add(vecReg);
                }
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
               
                rst.close();
                calculaTotal(dblPorIva);
                lblCotNumDes.setText("Factura No. " + txtFac.getText() +  " (" + txtCliNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");
                
                
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */
                
                String strCo_ForPag = (rstCotCab.getString("co_forPag")==null)?"":rstCotCab.getString("co_forPag") ;
                
                      
                //Extrayendo los datos del detalle respectivo a ESTE PAGO
                sSQL  = "Select * from tbm_pagCotCom where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                " co_cot = " + intNumCot                       + " order by co_reg";
                
               // rst = stmAux.executeQuery(sSQL);
                
              
                
                
                
                calculaPagos();
                
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                String strStatus = rstCotCab.getString("st_reg");
                if(strStatus.equals("I")){
                    lblCotNumDes.setText( lblCotNumDes.getText()+ "#ANULADO#");
                    objUti.desactivarCom(jfrThis);
                }else{
                    if (objTooBar.getEstado()== 'm'){
                        objUti.activarCom(jfrThis);
                        noEditable(false);
                    }
                }
                stmAux.close();
                stmCab.close();
                con.close();
            }
            blnHayCam = false; // Seteando que no se ha hecho cambios
            
        }//fin Try
        catch(SQLException Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            
        }
        
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        
    }
    
    
    //*****************************/*//////////////////////////////////////////////////////////////
    
    
    private boolean cargarReg() {
        boolean blnRes=true;
        try {
            if (cargarCabReg()) {
                refrescaDatos();
               // butGuiRem.setEnabled(true);
               
            }
            else {
                MensajeInf("Error al cargar registro o Registro Eliminado..");
                blnHayCam=false;
            }
            
        }
        catch (Exception e) {
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean _getCargarReg()
    {
        boolean blnRes=true;
        try 
        {
            if (_getCargarCabReg()) 
            {
               _getCargarDetReg();
            }
            else 
            {
                MensajeInf("Error al cargar registro o Registro Eliminado..");
                blnHayCam=false;
            }
        }
        catch (Exception e) {  blnRes=false;    }
        return blnRes;
    }  
    
    private boolean cargarCabReg() {
        int intPosRel;
        int intTipForPag=0;
        boolean blnRes=false;
        try {
            Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null) {
                Statement stm=con.createStatement();
                String strSQL="";
                
                int intNumCot = 0;
                intNumCot = rstCab.getInt("co_doc");                
                intTipForPag=rstCab.getInt("ne_tipforpag");         
                
                strSQL= "SELECT DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numped, DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                " DocCab.co_forPag, forpag.tx_des as desForPag, forpag.ne_tipforpag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                " DocCab.tx_obs1, DocCab.tx_obs2, ROUND(-DocCab.nd_sub,2) as nd_sub, DocCab.nd_porIva,ROUND(-DocCab.nd_tot,2) as nd_tot,"+
                " ROUND(-DocCab.nd_valIva,2) as nd_valIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra, DocCab.nd_poriva, DocCab.ne_secgrp "   + //Campos del Tab OTROS
                " ,DocCab.st_regrep  ,Cli.tx_tipper , Cli.co_tipper  " +
                " ,Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar, Cli.st_ivaven " +
                " ,DocCab.ne_numorddes, ROUND(DocCab.nd_valComSol,2) as nd_valComSol ";
                if (intTipForPag==4){
                    strSQL+=" ,DocCab.co_tipCre, a1.tx_deslar as nomTarCre,a3.tx_emiTarCre,  "
                            + " a3.tx_deslar as nomTipCre, a3.tx_tipcre, a3.ne_mesgra, a3.nd_pordesven, "
                            + " a3.ne_mesCre, a3.ne_mesgra, DocCab.nd_porComTarCre, DocCab.nd_porDesVenTarCre ";
                }
                strSQL+=" FROM tbm_cabMovInv as DocCab " +
                " LEFT JOIN  tbm_cli AS Cli on (cli.co_emp = DocCab.co_emp  AND Cli.co_cli = DocCab.co_cli) " +
                " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu) " +
                " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag ) ";
                if (intTipForPag==4){
                    strSQL+=" left outer join tbm_tipCreRedTarCre as a3 on (a3.co_emp=DocCab.co_emp AND a3.co_tipCre=DocCab.co_tipCre ) " +   
                        " left outer join tbm_redTarCre as a1 on (a3.co_redtarcre=a1.co_redTarCre ) " ;
                }
                strSQL+=" where DocCab.co_emp = " + rstCab.getInt("co_emp") + // Consultando en la empresa en la ke se esta trabajando
                " and DocCab.co_loc = " +  rstCab.getInt("co_loc")  +
                " and DocCab.st_reg not in ('E') " +
                " and DocCab.co_doc = " + intNumCot +// Consultando en el local en el ke se esta trabajando
                " and DocCab.co_tipdoc = " + rstCab.getInt("co_tipdoc")+" "+
                " ";
                
                System.out.println("ZafVen02.cargarCabReg: " +strSQL);
                
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next()) {
                    
                    dblPorIva=rst.getDouble("nd_poriva");  //)?0:rst.getDouble("nd_poriva");
                    //txtTipDocNom
                    STR_ESTREG=rst.getString("st_regrep");
                    txtDoc.setText(""+intNumCot);
                    txtTipDocCod.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
                    txtCot.setText(""+rst.getInt("ne_numCot"));
                    txtFac.setText(""+rst.getInt("ne_numDoc"));
                    txtPed.setText(((rst.getString("tx_numped")==null)?"":rst.getString("tx_numped")+""));
                    txtGuia.setText(""+rst.getInt("ne_numGui"));
                    txtOD.setText(""+rst.getInt("ne_numorddes"));
                    txtCliCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtCliNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtCliDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));

                     strCodCli=rst.getString("co_cli");
                     strDesLarCli=rst.getString("nomcli");
                    
                    Glo_intCodSec = rst.getInt("ne_secgrp");
                    
                    txtcli_telefono.setText( rst.getString("tx_tel") );
                    txtcli_identificacion.setText( rst.getString("tx_ide") );
                    txtcli_ciudad.setText( rst.getString("tx_desLar") );
                    
                    
                    strTipPer_glo=rst.getString("tx_tipper");
                    strCodTipPerCli =  rst.getString("co_tipper");
                    stIvaVen =  rst.getString("st_ivaven");
                    
                    
                    /* JoseMario 11/Sep/2015 */
                    txtSub.setText(rst.getString("nd_sub"));
                    txtIva.setText(rst.getString("nd_valIva"));
                    txtComSol.setText(rst.getString("nd_valComSol"));
                    txtTot.setText(rst.getString("nd_tot"));
                    /* JoseMario 11/Sep/2015 */
                    
                    //    rst2.close();
                    //     rst2=null;
                    
                    //*****************
                    
                    dateFecCot = objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy");
                    intTipMod = 2;
                    //*****************
                    if(rst.getDate("fe_doc")==null){
                        txtFecDoc.setText("");
                        lblfecha.setText("");
                    }else{
                        java.util.Date dateObj = rst.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH)+1     ,
                        calObj.get(java.util.Calendar.YEAR)        );
                        
                        lblfecha.setText(dateFecCot);
                        
                    }                                       
                    
                    txtVenCod.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                    txtVenNom.setText(((rst.getString("nomven")==null)?"":rst.getString("nomven")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));                                       
                    
                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    
                    
                      /*
                       * CARGANDO DATOS DEL TAB MOTIVO DE TRANSPORTE
                       */
                    txtPartida.setText(((rst.getString("tx_ptoPar")==null)?"":rst.getString("tx_ptoPar")));
                    txtTransporte.setText(((rst.getString("tx_tra")==null)?"":rst.getString("tx_tra")));
                    cboMotivoTransporte.setSelectedIndex(vecTransporte.indexOf(rst.getInt("co_motTra")+""));
                    
                    txtCodigoFormaPago.setText(rst.getString("co_forPag") );
                    txtNomForPag.setText(rst.getString("desForPag"));
                    txtCodForPag.setText(txtCodigoFormaPago.getText());                

                    /** TARJETA DE CREDITO. */ 
                    if (intTipForPag==4){
                        txtCodTarCre.setText(rst.getString("co_tipCre"));
                        txtNomTarCre.setText(rst.getString("nomTipCre"));

//                        txtCodRedTarCre.setText(rst.getString("co_redTarCre"));
//                        txtCodTarCre.setText(rst.getString("co_tipCre")); 
//                        txtNomTarCre.setText(rst.getString("nomTipCre"));
                        txtTipCre.setText(rst.getString("tx_tipcre"));
                        dblPorDesTarCre= objUti.redondear(objUti.parseDouble((rst.getString("nd_pordesven")==null)?"0":rst.getString("nd_pordesven") ), 2 ); 

            
                        txtNomEmiTar.setText(rst.getString("tx_emiTarCre"));

                        txtMesGra.setText(rst.getString("ne_mesgra"));                        
                        txtPorComTarCre.setText(rst.getString("nd_porComTarCre") );
                        txtPorDesVenTarCre.setText(rst.getString("nd_porDesVenTarCre") );
                    }else{
                        txtCodTarCre.setText("");
                        txtNomTarCre.setText("");

                        txtCodRedTarCre.setText("");
                        txtCodTarCre.setText(""); 
                        txtNomTarCre.setText("");
                        txtTipCre.setText("");
                        dblPorDesTarCre= 0; 


                        txtNomEmiTar.setText("");

                        txtMesGra.setText("");                        
                        txtPorComTarCre.setText("");
                        txtPorDesVenTarCre.setText("");
                    }

                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    strstReg_glo = strAux;
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);                    
                    
                    blnRes=true;
                    
                }else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                    blnRes=false;
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Mostrar la posición relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                blnHayCam=false;
                
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    
    
     private boolean _getCargarCabReg() 
     {
        boolean blnRes=false;
        int intPosRel, intTipForPag=0;
        try 
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";

                int intNumCot = 0;
                intNumCot = rstCab.getInt("co_doc");
                intTipForPag=rstCab.getInt("ne_tipforpag");         

                strSQL= "SELECT tipdoc.tx_descor, tipdoc.tx_deslar as deslartipdoc,  DocCab.co_cli, DocCab.tx_nomCli as nomcli, DocCab.tx_dirCli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                        " DocCab.co_com as co_ven, DocCab.tx_nomVen as nomven, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                        " DocCab.co_doc, DocCab.fe_doc, DocCab.co_tipDoc, DocCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                        " DocCab.ne_numCot, DocCab.ne_numDoc,  DocCab.tx_numped, DocCab.ne_numGui, " + // Numero de cotizacion, factura de imprenta, guia y pedido
                        " DocCab.co_forPag, forpag.tx_des as desForPag,  " + //<==Campos que aparecen en la parte superior del 2do Tab
                        " DocCab.tx_obs1, DocCab.tx_obs2, DocCab.nd_sub, DocCab.nd_porIva, ROUND(-DocCab.nd_sub,2) as nd_subTot, ROUND(-DocCab.nd_valIva,2) as nd_valIva, ROUND(-DocCab.nd_tot,2) as nd_tot,  " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                        " DocCab.st_reg, " + // Campo para saber si esta anulado o no. Codigo de Bodega
                        " DocCab.tx_ptoPar, DocCab.tx_tra, DocCab.co_motTra, DocCab.tx_desMotTra, DocCab.nd_poriva, DocCab.ne_secgrp "   + //Campos del Tab OTROS
                        " ,DocCab.st_regrep  ,Cli.tx_tipper , Cli.co_tipper  " +
                        " ,Cli.nd_maxdes, Cli.nd_maruti, Cli.tx_tel, Cli.tx_ide , ciu.tx_desLar, Cli.st_ivaven, DocCab.nd_valComSol " ;
                if (intTipForPag==4)
                {
                    strSQL+=" ,DocCab.co_tipCre, a1.tx_deslar as nomTarCre,a3.tx_emiTarCre,  "
                            + " a3.tx_deslar as nomTipCre, a3.tx_tipcre, a3.ne_mesgra, a3.nd_pordesven, "
                            + " a3.ne_mesCre, a3.ne_mesgra, DocCab.nd_porComTarCre, DocCab.nd_porDesVenTarCre ";
                }
                
                strSQL+=" FROM tbm_cabMovInv as DocCab " +
                        " LEFT JOIN  tbm_cli AS Cli on (cli.co_emp = DocCab.co_emp  AND Cli.co_cli = DocCab.co_cli) " +
                        " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=Cli.co_ciu)   " +
                        " LEFT JOIN  tbm_cabtipdoc AS tipdoc on (tipdoc.co_emp = DocCab.co_emp  AND tipdoc.co_loc = DocCab.co_loc and tipdoc.co_tipdoc=DocCab.co_tipdoc  ) " +
                        " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag ) " ;
                
                if (intTipForPag==4)
                {
                    strSQL+=" left outer join tbm_tipCreRedTarCre as a3 on (a3.co_emp=DocCab.co_emp AND a3.co_tipCre=DocCab.co_tipCre ) " +   
                            " left outer join tbm_redTarCre as a1 on (a3.co_redtarcre=a1.co_redTarCre ) " ;
                }
                
                strSQL+=" where DocCab.co_emp = " + rstCab.getInt("co_emp") + // Consultando en la empresa en la ke se esta trabajando
                        " and DocCab.co_loc = " +  rstCab.getInt("co_loc")  +
                        " and DocCab.st_reg not in ('E')  and DocCab.co_doc = " + intNumCot +// Consultando en el local en el ke se esta trabajando
                        " and DocCab.co_tipdoc = " + rstCab.getInt("co_tipdoc")+" "+
                        " ";
                
                System.out.println("ZafVen01._getCargarCabReg: " + strSQL);
                
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next()) 
                {
                    txtTipDocAli.setText(""+rst.getString("tx_descor"));
                    txtTipDocNom.setText(""+rst.getString("deslartipdoc"));

                    dblPorIva=rst.getDouble("nd_poriva");  
                    STR_ESTREG=rst.getString("st_regrep");
                    txtDoc.setText(""+intNumCot);
                    txtTipDocCod.setText(((rst.getString("co_tipdoc")==null)?"":rst.getString("co_tipdoc")));
                    txtCot.setText(""+rst.getInt("ne_numCot"));
                    txtFac.setText(""+rst.getInt("ne_numDoc"));
                    txtPed.setText(((rst.getString("tx_numped")==null)?"":rst.getString("tx_numped")+""));
                    txtGuia.setText(""+rst.getInt("ne_numGui"));
                    txtCliCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    txtCliNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtCliDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));

                    Glo_intCodSec = rst.getInt("ne_secgrp");

                    txtcli_telefono.setText( rst.getString("tx_tel") );
                    txtcli_identificacion.setText( rst.getString("tx_ide") );
                    txtcli_ciudad.setText( rst.getString("tx_desLar") );

                    strTipPer_glo=rst.getString("tx_tipper");
                    strCodTipPerCli =  rst.getString("co_tipper");
                    stIvaVen =  rst.getString("st_ivaven");

                    //******************
                    txtSub.setText(rst.getString("nd_subTot"));
                    txtIva.setText(rst.getString("nd_valIva"));
                    txtComSol.setText(rst.getString("nd_valComSol"));
                    txtTot.setText(rst.getString("nd_tot"));
                    //*****************

                    dateFecCot = objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy");
                    intTipMod = 2;
                    //*****************
                    if(rst.getDate("fe_doc")==null)
                    {
                        txtFecDoc.setText("");
                        lblfecha.setText("");
                    }
                    else
                    {
                        java.util.Date dateObj = rst.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH)+1     ,
                        calObj.get(java.util.Calendar.YEAR)        );

                        lblfecha.setText(dateFecCot);
                    }
                    
                    txtVenCod.setText(((rst.getString("co_ven")==null)?"":rst.getString("co_ven")));
                    txtVenNom.setText(((rst.getString("nomven")==null)?"":rst.getString("nomven")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));

                    //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));

                      /*
                       * CARGANDO DATOS DEL TAB MOTIVO DE TRANSPORTE
                       */
                    txtPartida.setText(((rst.getString("tx_ptoPar")==null)?"":rst.getString("tx_ptoPar")));
                    txtTransporte.setText(((rst.getString("tx_tra")==null)?"":rst.getString("tx_tra")));
                    cboMotivoTransporte.setSelectedIndex(vecTransporte.indexOf(rst.getInt("co_motTra")+""));

                    txtCodigoFormaPago.setText(rst.getString("co_forPag") );
                    txtNomForPag.setText(rst.getString("desForPag"));
                    txtCodForPag.setText(txtCodigoFormaPago.getText());                

                    /** TARJETA DE CREDITO. */
                    if (intTipForPag==4)
                    {
                        txtCodTarCre.setText(rst.getString("co_tipCre"));
                        txtNomTarCre.setText(rst.getString("nomTipCre"));
//                        txtCodRedTarCre.setText(rst.getString("co_redTarCre"));
//                        txtCodTarCre.setText(rst.getString("co_tipCre")); 
//                        txtNomTarCre.setText(rst.getString("nomTipCre"));
                        txtTipCre.setText(rst.getString("tx_tipcre"));
                        dblPorDesTarCre= objUti.redondear(objUti.parseDouble((rst.getString("nd_pordesven")==null)?"0":rst.getString("nd_pordesven") ), 2 ); 
                       
                        txtNomEmiTar.setText(rst.getString("tx_emiTarCre"));
                       
                        txtMesGra.setText(rst.getString("ne_mesgra"));
                        txtPorComTarCre.setText(rst.getString("nd_porComTarCre") );
                        txtPorDesVenTarCre.setText(rst.getString("nd_porDesVenTarCre") );
                    }
                    else
                    {
                        txtCodTarCre.setText("");
                        txtNomTarCre.setText("");

                        txtCodRedTarCre.setText("");
                        txtCodTarCre.setText(""); 
                        txtNomTarCre.setText("");
                        txtTipCre.setText("");
                        dblPorDesTarCre= 0; 
                        
                        txtNomEmiTar.setText("");
                  
                        txtMesGra.setText("");
                        txtPorComTarCre.setText("");
                        txtPorDesVenTarCre.setText("");
                    }
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    strstReg_glo = strAux;
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);

                    blnRes=true;
                }
                else 
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                    blnRes=false;
                }

                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Mostrar la posición relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                blnHayCam=false;

            }
        }
        catch (java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    public class mitoolbar extends ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
        }

public boolean _getVerificaEstAutAnuDoc(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
 boolean blnRes = true;
 try{
   java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    if (con!=null){
        String strSQL = "select CASE WHEN a.st_autAnu IN ('A') THEN 'S' ELSE 'N' END AS autanu , a1.st_necAutAnuDoc " +
        " from tbm_cabmovinv as a " +
        " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) " +
        " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+"  and a1.st_necAutAnuDoc = 'S'";
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rst = stm.executeQuery(strSQL);
        if(rst.next()){
          if(rst.getString("autanu").equals("N")){
               MensajeInf("<html>Este documento <FONT COLOR=\"blue\"> Necesita Autorización para se anulado </FONT> </html>");
               blnRes = false;
        }}
        rst.close();
        rst=null;
        stm.close();
        stm=null;
        con.close();
        con=null;
        
 }}catch(java.sql.SQLException e){ blnRes = false;  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
 return blnRes;
}



public boolean _getVerificaRegRelRecChq(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
 boolean blnRes = true;
 try{
    if(conn!=null){
        String strSQL = "select co_doc from tbr_detrecdocpagmovinv as a  " +
        "  where co_emprel="+intCodEmp+" and co_locrel="+intCodLoc+" and co_tipdocrel="+intCodTipDoc+" and co_docrel="+intCodDoc+" and a.st_reg = 'A'";
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet rst = stm.executeQuery(strSQL);
        if(rst.next()){
            MensajeInf("<html>Este documento <FONT COLOR=\"blue\"> Tiene asociado con una recepción de cheque.  </FONT> No se puede anular. </html>");
            blnRes = false;
        }
        rst.close();
        rst=null;
        stm.close();
        stm=null;
      
 }}catch(java.sql.SQLException e){ blnRes = false;  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
 return blnRes;
}


@Override
public boolean anular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }

            if (txaObs2.getText().contains("Generado por volver a facturar.")){
                MensajeInf("No es posible anular este documento.");
                return false;
                
            }
            boolean blnIsCosenco=false,blnIsEcuatosa=false,blnIsDetopacio=false;
            //Saber si la empresa que ingreso es COSENCO
            blnIsCosenco = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false;
            blnIsEcuatosa = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") > -1)?true:false;
            blnIsDetopacio = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") > -1)?true:false;
            //     System.out.println("---------------------------->>>>>>>>>>>>>>>>"  + objZafParSis.getNombreEmpresa());
            //     System.out.println("blnIsCosenco" + blnIsCosenco);
            //     System.out.println("blnIsEcuatosa" + blnIsEcuatosa);
            //     System.out.println("blnIsDetopacio" + blnIsDetopacio);
            //System.out.println("TIPO DE DOCUMENTOS ======> "+txtTipDocCod.getText());
            if(blnIsCosenco || blnIsEcuatosa ||blnIsDetopacio){  
                //System.out.println("EMPRESA: " + objZafParSis.getNombreEmpresa());
           }
           else{
                if(objZafParSis.getCodigoUsuario()!=1){
                    //System.out.println("validacion " + txtFac.getText());

                    //No es Admin
                    if(Integer.parseInt(txtFac.getText())>0){
                        MensajeInf("No es posible anular este documento. \nSolo se pueden anular preFacturas.");
                        return false;
                    }

                }
           }
            
            

      

          String  fechaServidor = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFecha());
          if(!(fechaServidor.trim().equals(lblfecha.getText().trim()))){
            
               if( !_getVerificaEstAutAnuDoc(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText())))
               return false;
          }

            
            if(!verificaItmUni())
               return false;
            
            
            if(!objUltDocPrint2.Verificar_Doc_Asociado(txtDoc.getText(),txtTipDocCod.getText()))
                return false;
            
            if (objUltDocPrint2.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText())))
                return false;
            
            
            if(!objUltDocPrint2.verificarsiesconfirmado(txtDoc.getText(),txtTipDocCod.getText()) )
                      return false;
            
            if (!verificaConfirmacionRelacionada(txtTipDocCod.getText(), txtDoc.getText()))
                return false;
//
//            if (objUltDocPrint2.isVerificarGuiaRemTrans(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),  txtDoc.getText(),txtTipDocCod.getText()))
//                return false;
//
//
            
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        

boolean blnEstHayAnulOC=false;
boolean blnEstTransAut=false;
String strOCAnu="";

/**
 * MODIFICADO EFLORESA 2012-05-11
 * NOTIFICAR A BODEGA LA ANULACION DE LA FACTURA. 
 */
public boolean anularReg(){
 boolean blnRes=false;
 Connection conn;
 String strCorEle1="", strCorEle2="", strCorEle3="";
 String strMensCorEle="";
 try{
    conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    if(conn!=null){
      conn.setAutoCommit(false);

       blnEstHayAnulOC=false;
       blnEstTransAut=false;
       strOCAnu="";

       ZafCorElePrg objCorEle=new ZafCorElePrg(objZafParSis, jfrThis);
       strCorEle1=objCorEle._getCorEleFacVen_01(objZafParSis.getCodigoEmpresa(), intCodBodPre);
       strCorEle2=objCorEle._getCorEleFacVen_02();

    if(isAnulada(conn, " ANULAR ")){
     if(_getVerificaRegRelRecChq(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
      if(objUltDocPrint2.isGenOCCosenco(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),  Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtDoc.getText()) )){
       if(objUltDocPrint2.verificarGuiRemSiTieGuiSec(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),  Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtDoc.getText()) )){

           
          if(anularReg(conn)){

              conn.commit();
              objTooBar.setEstadoRegistro("Anulado");              

              if(blnEstHayAnulOC) MensajeInf("LAS SIGUIENTES ÓRDENES DE COMPRA FUERON ANULADAS \n"+ strOCAnu + " ");

              strMensCorEle="NUMERO DE FACTURA : "+txtFac.getText()+" <br>  NUMERO DE GUIA # "+txtGuia.getText()+"  <br> " +
              "  CLIENTE : "+txtCliNom.getText();

              if(blnEstHayAnulOC) strMensCorEle+=" LAS SIGUIENTES ORDENES DE COMPRA FUERON ANULADAS <br> "+ strOCAnu + " ";


              if( (!txtFac.getText().trim().equals("0")) && (!txtGuia.getText().trim().equals("0")) )
                  objInvItm.enviarCorreo(strCorEle1, strMensCorEle );
              else{
                   if(blnEstHayAnulOC) objInvItm.enviarCorreo(strCorEle1, strMensCorEle );
              }

              if(blnEnvCorEleProCosenco){
                   strMensCorEle = " LA SIGUIENTE ORDEN DE COMPRA FUE ANULADA <br> "+ strNumOcCons + "  " ;
                   objInvItm.enviarCorreo(strCorEle2, strMensCorEle );
              }
              
              if(blnIsOrdAnulada){
                  strCorEle3=objCorEle.getCorEleBod();
                  strMensCorEle = " LA SIGUIENTE ORDEN DE DESPACHO FUE ANULADA <br> "+ strNumOrdDes + "  " ;
                  objInvItm.enviarCorreo(strCorEle3, strMensCorEle );
                  objInvItm.enviarRequisitoImp(conn, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtDoc.getText()), "F"  ); 
              }
                            
              blnRes=true;
              
          }else conn.rollback();
      }else conn.rollback();
     }else conn.rollback();
    }else conn.rollback();
   }else conn.rollback();
   
   objCorEle=null;
   conn.close();
   conn=null;
   
 }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
   catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}

/*
 * MODIFICADO EFLORESA 2012-04-10
 * PERSONAL DE VENTAS PUEDA ANULAR PRE-FACTURAS DE DIAS ANTERIORES
 * 
 * MODIFICADO EFLORESA 2012-05-11
 * NOTIFICAR A BODEGA LA ANULACION DE LA FACTURA
 */
public boolean anularReg(Connection conn){
 boolean blnRes=false;
 boolean blnIsPreFac=false;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 String  fechaServidor;
 blnIsOrdAnulada=false;
 try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      if(objZafParSis.getCodigoUsuario()==102){  /*Wendy Moran*/ 
       strSql="select  co_loc, st_imp, st_reg ,  ne_numdoc from tbm_cabmovinv where " +
       " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtTipDocCod.getText()+" and co_doc="+txtDoc.getText()+" and st_imp='N'";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
            // ok SI PUEDE ANULAR..
       }else{
           mostrarMsg("NO SE PUEDE ANULAR ESTA FACTURA \n SOLO SE PUEDE ANULAR FACTURAS QUE NO HAYAN SIDO IMPRESAS.  ");
           rstLoc.close();
           rstLoc=null;
           stmLoc.close();
           stmLoc=null;
           return false;
       }
      rstLoc.close();
      rstLoc=null;
     }
      
      //PRE-FACTURAS
      strSql="select ne_numdoc, st_imp " +
                   " from tbm_cabmovinv " +
                   " where co_emp="+objZafParSis.getCodigoEmpresa() +
                   " and co_loc="+objZafParSis.getCodigoLocal() +
                   " and co_tipdoc="+txtTipDocCod.getText() + 
                   " and co_doc="+txtDoc.getText();
      rstLoc=stmLoc.executeQuery(strSql);
      if (rstLoc.next()){
          if (rstLoc.getInt("ne_numdoc") == 0 )
              blnIsPreFac = true;
          else 
              blnIsPreFac= false;
      }
      rstLoc.close();
      rstLoc = null;
//      
//      if (blnIsPreFac){
//          if (!((objZafParSis.getCodigoUsuario()==7)   || //PSOLORZANO
//                (objZafParSis.getCodigoUsuario()==17)  || //PPEZO
//                (objZafParSis.getCodigoUsuario()==27)  || //MVILLALTA 
//                (objZafParSis.getCodigoUsuario()==33)  || //GMOSQUERA
//                (objZafParSis.getCodigoUsuario()==58)  || //AZULETA
//                (objZafParSis.getCodigoUsuario()==101) || //JRODAS
//                (objZafParSis.getCodigoUsuario()==102) || //WMORAN
//                (objZafParSis.getCodigoUsuario()==39)  || // PGRAF
//                (objZafParSis.getCodigoUsuario()==130) || //LYUMISEBA
//                //(objZafParSis.getCodigoUsuario()==126) || //FZAMBRANO
//                (objZafParSis.getCodigoUsuario()==122) || //FRUIZ 
//                (objZafParSis.getCodigoUsuario()==1) 
//               ) 
//              ) {
//              fechaServidor = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFecha());
//              //if(!(fechaServidor.trim().equals(lblfecha.getText().trim()))){
//                 // mostrarMsg("NO SE PUEDE ANULAR ESTA FACTURA \n SOLO SE PUEDE ANULAR PRE-FACTURAS QUE CORRESPONDAN AL DIA DE HOY..");
//                  //return false;
//              //}
//          }
//      }else
//        if(!((objZafParSis.getCodigoUsuario()==101) || //JRODAS
//                (objZafParSis.getCodigoUsuario()==102) || //WMORAN
//                (objZafParSis.getCodigoUsuario()==1)    ) ){
//        fechaServidor = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFecha());
//        if(!(fechaServidor.trim().equals(lblfecha.getText().trim()))){
//                mostrarMsg("NO SE PUEDE ANULAR ESTA FACTURA \n SOLO SE PUEDE ANULAR FACTURAS QUE CORRESPONDAN AL DIA DE HOY..");
//                return false;
//        }}
      
    /*****************************************************************************/
    /* if(!((objZafParSis.getCodigoUsuario()==101) || //JRODAS
             (objZafParSis.getCodigoUsuario()==102) || //WMORAN
             (objZafParSis.getCodigoUsuario()==1)    ) ){
       fechaServidor = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFecha());
       if(!(fechaServidor.trim().equals(lblfecha.getText().trim()))){
            mostrarMsg("NO SE PUEDE ANULAR ESTA FACTURA \n SOLO SE PUEDE ANULAR FACTURAS QUE CORRESPONDAN AL DIA DE HOY..");
            stmLoc.close();
            stmLoc=null;
            return false;
     }}*/
    
   /*****************************************************************************************/
     
     
     

    strSql="SELECT b.ne_numdoc FROM tbr_cabmovinv AS a " +
    " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
    " WHERE  a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc="+txtTipDocCod.getText()+" AND a.co_doc="+txtDoc.getText()+" "+
    " AND a.co_tipdocrel=2   ";
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
      blnEstHayAnulOC=true;
      strOCAnu +=rstLoc.getString(1)+"\n";
    }
    rstLoc.close();
    rstLoc=null;
    if(blnEstHayAnulOC)
      if(( mensaje("LAS SIGUIENTES ORDENES DE COMPRA SERAN ANULADAS \n"+strOCAnu +" \n ¿DESEA SEGUIR CON EL PROCESO DE ANULACION ?")!=JOptionPane.YES_OPTION)){
        stmLoc.close();
        stmLoc=null;
        return false;
      }
  
  /***************************************************************************************/

 if(anueliCab(conn,"I")){
  if(objAsiDia.anularDiario(conn, objZafParSis.getCodigoEmpresa(),  objZafParSis.getCodigoLocal() , Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtDoc.getText()))){
   if(invertirInvDet(conn, con_remota)){
    if(anulaDet_OC(conn, con_remota,  objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), txtTipDocCod.getText() , txtDoc.getText() ) ){
     if(invertir_Trans( objZafParSis.getCodigoLocal(), con_remota, conn, txtTipDocCod.getText() , txtDoc.getText() )) {
          
         blnRes=true;

      }else { blnRes=false; }
     }else { blnRes=false; }
    }else { blnRes=false; }
   }else { blnRes=false; }
  }else { blnRes=false; }
   objInvItm.limpiarObjeto();
   stmLoc.close();
   stmLoc=null;


 }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
   catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}

    public boolean verificaConfirmacionRelacionada(String cotipdoc, String coddoc){
        boolean blnRes=true;
        Connection conn=null;
        PreparedStatement pstLoc=null;
        ResultSet rstLoc=null;
        String strSQL=" select distinct(x4.ne_numorddes), x4.st_coninv, case when x5.st_cliretemprel is null then 'N' else x5.st_cliretemprel end as st_cliretemprel "
                    + " from tbr_detmovinv as x "
                    + " inner join tbr_detmovinv as x1 on (x1.co_emprel=x.co_emprel and x1.co_locrel=x.co_locrel and x1.co_tipdocrel=x.co_tipdocrel and x1.co_docrel=x.co_docrel and x1.co_regrel=x.co_regrel and ( x1.co_emp!=x.co_emp or x1.co_loc!=x.co_loc or x1.co_tipdoc!=x.co_tipdoc or x1.co_doc!=x.co_doc )) "
                    + " inner join tbr_detmovinv as x2 on (x2.co_emp=x1.co_emp and x2.co_loc=x1.co_loc and x2.co_tipdoc=x1.co_tipdoc and x2.co_doc=x1.co_doc and x2.co_reg=x1.co_reg ) "
                    + " inner join tbm_detmovinv as x5 on (x5.co_emp=x2.co_emp and x5.co_loc=x2.co_loc and x5.co_tipdoc=x2.co_tipdoc and x5.co_doc=x2.co_doc and x5.co_reg=x2.co_reg ) "
                    + " left join tbm_detguirem as x3 on (x3.co_emprel=x5.co_emp and x3.co_locrel=x5.co_loc and x3.co_tipdocrel=x5.co_tipdoc and x3.co_docrel=x5.co_doc and x3.co_regrel=x5.co_reg ) "
                    + " left join tbm_cabguirem as x4 on (x4.co_emp=x3.co_emp and x4.co_loc=x3.co_loc and x4.co_tipdoc=x3.co_tipdoc and x4.co_doc=x3.co_doc ) "
                    + " left join tbm_bod as x6 on (x6.co_emp=x4.co_emp and x6.co_bod=x4.co_ptopar ) "
                    + " where x.co_emp=? "
                    + " and x.co_loc=? "
                    + " and x.co_tipdoc=? "
                    + " and x.co_doc=? "
                    + " and x.co_emprel=? "; 
        
        try{ 
            //System.out.println("ZafVen02.verificaConfirmacionRelacionada: " + strSQL);
            conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conn!=null){
                pstLoc = conn.prepareStatement(strSQL);
                pstLoc.setInt(1, objZafParSis.getCodigoEmpresa() );
                pstLoc.setInt(2, objZafParSis.getCodigoLocal() );
                pstLoc.setInt(3, Integer.parseInt(cotipdoc) );
                pstLoc.setInt(4, Integer.parseInt(coddoc) );
                pstLoc.setInt(5, objZafParSis.getCodigoEmpresa() );
                rstLoc = pstLoc.executeQuery();
                while(rstLoc.next()){
                    if( (rstLoc.getString("st_cliretemprel").equals("S")) ){
                        //if( (rstLoc.getString("st_coninv").equals("E")) || (rstLoc.getString("st_coninv").equals("C")) ){
                            String srt=" El documento que esta intentando anular tiene marcada la opcion de CLIENTE RETIRA. \n "
                                     + " Se debe solicitar autorizacion para proceder. ";
                            MensajeInf( srt );
                            blnRes=false;
                            break;
                        }  
                    //}
                }
            }
        }catch(SQLException e){  
            objUti.mostrarMsgErr_F1(this,e); 
            blnRes=false; 
        }catch(Exception e){  
            objUti.mostrarMsgErr_F1(this,e); 
            blnRes=false; 
        }finally{
            try{
                if (rstLoc != null)
                    rstLoc.close();
                rstLoc=null;
                
                if (pstLoc != null)
                    pstLoc.close();
                pstLoc=null;
                
                if (conn != null)
                    conn.close();
                conn=null;
            }catch(Throwable e){
                e.printStackTrace();
            }
        }
        return blnRes;
    }    
    
        @Override
        public void clickAceptar() {
            //  setEstadoBotonMakeFac(); 
        }
        
        @Override
        public void clickAnterior() {
            try
            {   if(rstCab != null ) {
                    if (!rstCab.isFirst()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.previous();
                                cargarReg();
                            }
                        }
                        else {
                            rstCab.previous();
                            cargarReg();
                        }
                    }}
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        @Override
        public void clickAnular() {
            setEditable(false);
        }
        
        
        @Override
        public void clickConsultar() {
            txtVenCod.setEnabled(true);
            
            
            cargarTipoDoc();
           
           
            
            java.awt.Color color = txtSub.getBackground();
            txtSub.setEditable(false);
            txtIva.setEditable(false);
            txtComSol.setEditable(false);
            txtTot.setEditable(false);
            txtSub.setBackground(color);
            txtIva.setBackground(color);
            txtComSol.setBackground(color);
            txtTot.setBackground(color);
            
            txtFecDoc.setText("");
            
        }
        
        @Override
        public void clickEliminar() {
            setEditable(false);
        }
        
        @Override
        public void clickFin() {
            try
            {   if(rstCab != null ) {
                    if (!rstCab.isLast()) {
                        if (blnHayCam) {
                            if (isRegPro()) {
                                rstCab.last();
                                cargarReg();
                            }
                        }
                        else {
                            rstCab.last();
                            cargarReg();
                        }
                    }}
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        @Override
        public void clickInicio() {
            try
            {  if(rstCab != null ) {
                   if (!rstCab.isFirst()) {
                       if (blnHayCam) {
                           if (isRegPro()) {
                               rstCab.first();
                               cargarReg();
                           }
                       }
                       else {
                           rstCab.first();
                           cargarReg();
                       }
                   }}
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        
        
        
        
        
        
        
        
        
        
        private void cargaNum_Doc_Preimpreso(){
            int intNumeroDoc = objUltDocPrint.getUltDoc(Integer.parseInt(txtTipDocCod.getText()));
            intNumeroDoc++;
            txtFac.setText("" + intNumeroDoc);
            txtGuia.setText("" + intNumeroDoc);
        }
        
        
        
             
        public void cargarTipoDoc(){
             String sqlPrede="";
            try{
                
                java.sql.Connection  conDefault =  DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                java.sql.Statement stmPrede=conDefault.createStatement();
                java.sql.ResultSet rstPrede;
                
                if(objZafParSis.getCodigoUsuario()==1){
                 sqlPrede = "Select distinct doc.co_tipdoc,doc.tx_deslar,doc.tx_descor, doc.st_meringegrfisbod, doc.tx_natdoc " +
                 " FROM tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                 " where   menu.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                 " menu.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                 " menu.co_mnu = " + intCodMnu  + " and " +
                 " menu.co_tipdoc = doc.co_tipdoc and menu.st_reg = 'S'";
                }else{
                    sqlPrede ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a.st_meringegrfisbod, a.tx_natdoc "+
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE a1.st_reg = 'S'  "+
                    " AND a1.co_emp=" + objZafParSis.getCodigoEmpresa()+""+
                    " AND a1.co_loc=" + objZafParSis.getCodigoLocal()+""+
                    " AND a1.co_mnu=" + objZafParSis.getCodigoMenu()+""+
                    " AND a1.co_usr=" + objZafParSis.getCodigoUsuario();
                }       
                
                rstPrede=stmPrede.executeQuery(sqlPrede);
                
                if(rstPrede.next()){
                    txtTipDocCod.setText(((rstPrede.getString("co_tipdoc")==null)?"":rstPrede.getString("co_tipdoc")));
                    txtTipDocAli.setText(((rstPrede.getString("tx_descor")==null)?"":rstPrede.getString("tx_descor")));
                    txtTipDocNom.setText(((rstPrede.getString("tx_deslar")==null)?"":rstPrede.getString("tx_deslar")));
                    strMerIngEgr=(rstPrede.getString("st_meringegrfisbod")==null)?"":rstPrede.getString("st_meringegrfisbod");  
                    strTipIngEgr=(rstPrede.getString("tx_natdoc")==null)?"":rstPrede.getString("tx_natdoc");  
        
                    objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objZafParSis,  Integer.parseInt( (txtTipDocCod.getText().equals(""))?"0":txtTipDocCod.getText() ));
                    
                }
                
                stmPrede.close();
                rstPrede.close();
                conDefault.close();
                stmPrede   = null;
                rstPrede   = null;
                conDefault = null;
            }
            catch(SQLException Evt)  {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
            catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
            
        }
        
        
        
        
        
        @Override
        public void clickInsertar() {
            //Limpiando las cajas de textos y el detalle
            clnTextos();
            noEditable(false);
            txtFecDoc.setHoy();


           
            
            cboMotivoTransporte.setSelectedIndex(0);
            
            
            
            setEditable(true);
            blnHayCam=false;
            
            cargarTipoDoc();
            
            
            cargaNum_Doc_Preimpreso();
            
            
            // System.out.println("CodMenu "+ objZafParSis.getCodigoMenu() );
            
            dblPorIva=dblPorIvaaux;
            lblIva.setText("IVA " + dblPorIva + "%");
            
                /*
                 * Estableciendo el Tipo de documentoque se va a utilizar
                 */
            
            
        }
        
        
        
        
        
        
        
        @Override
        public void clickSiguiente() {
            try
            {  if(rstCab != null ) {
                   if (!rstCab.isLast()) {
                       if (blnHayCam) {
                           if (isRegPro()) {
                               rstCab.next();
                               cargarReg();
                           }
                       }
                       else {
                           rstCab.next();
                           cargarReg();
                       }
                   } }
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        @Override
        public boolean eliminar() {
            try {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado")) {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                
                if (txaObs2.getText().contains("Generado por volver a facturar.")){
                    MensajeInf("No es posible eliminar este documento.");
                    return false;

                }
            
                if(!verificaItmUni())
                  return false;
            
                    
                
                if(!objUltDocPrint2.verificarsiesconfirmado(txtDoc.getText(),txtTipDocCod.getText()))
                    return false;
                
                
                if(!objUltDocPrint2.Verificar_Doc_Asociado(txtDoc.getText(),txtTipDocCod.getText()))
                    return false;
                
                
                if (objUltDocPrint2.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtDoc.getText())))
                    return false;
                
                
                
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                }
                else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e) {
                return true;
            }
            return true;
        }
        
        @Override
        public boolean insertar()
        {  strstReg_glo="";
           if (!validaCampos())
               return false;
           if (!insertarReg())
               return false;
           blnHayCam=false;
           
           
           return true;
        }
        
        
        
        //  tbm_cabtipdoc
        
        private void calcularSaldo(){
            try {
                java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (con!=null) {
                    //  System.out.println(">>Glo "+ GLO_strArreItm +"  >> "+ Glo_intCodSec);
                    java.sql.PreparedStatement pst;
                    java.sql.ResultSet rstMae;
                    java.sql.ResultSet rstMae2;
                    java.sql.Statement stmMonCre = con.createStatement();
                    java.sql.Statement stmMonCre2 = con.createStatement();
                    String sSQL="select distinct(a.co_itm) from tbm_inv as a where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_itm in("+GLO_strArreItm+") order by co_itm";
                    ///  System.out.println("SQL >> "+ sSQL );
                    rstMae2 = stmMonCre2.executeQuery(sSQL);
                    
                    while(rstMae2.next()){
                        
                        String strSec="";
                /*
                 sSQL="select max(a.ne_secemp) " +
                 " from tbm_cabmovinv as a " +
                 " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                 " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                 " where  a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and a.st_reg<>'I'  and b.co_itm="+rstMae2.getInt(1)+" and a.ne_secemp <  "+Glo_intCodSec; //+" "+
                 //" order by a.ne_secemp";
                 */
                        
                        sSQL=" SELECT max(ne_secgrp) FROM (" +
                        " SELECT a1.ne_secGrp  FROM tbm_cabMovInv AS a1 " +
                        " INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " +
                        " INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)" +
                        " INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod) " +
                        " INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)" +
                        " INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm) " +
                        " WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+rstMae2.getInt(1)+")" +
                        " ORDER BY a1.ne_secGrp, a2.co_reg " +
                        " ) as x  WHERE x.ne_secgrp<"+Glo_intCodSec;
                        
                        //// System.out.println("SQL 2 >> "+ sSQL );
                        
                        rstMae = stmMonCre.executeQuery(sSQL);
                        if(rstMae.next()) strSec=rstMae.getString(1);
                        rstMae.close();
                        
                        
                        //    System.out.println("SEC >> "+ strSec );
                        
                        
               /*
                 sSQL="select a.ne_secemp ,round(b.nd_exigrp,2) as ndexigrp,round(b.nd_valexiGRP,2) as ndvalexigrp" +
                 " from tbm_cabmovinv as a " +
                 " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                 " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                 " where  b.co_itm="+rstMae2.getInt(1)+" and a.ne_secgrp="+strSec+" "+
                 " order by a.ne_secemp,b.co_reg  desc";
                */
                        
                        sSQL="select * from ( " +
                        "SELECT a1.ne_secGrp ,a2.nd_exigrp, a2.nd_valexigrp, a2.nd_cosprogrp " +
                        "FROM tbm_cabMovInv AS a1 " +
                        "INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)" +
                        "INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)" +
                        "INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)" +
                        "INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)" +
                        "INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)" +
                        " WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+rstMae2.getInt(1)+") " +
                        " AND  a1.ne_secgrp = "+strSec+" "+
                        " ORDER BY a1.ne_secGrp, a2.co_reg  DESC ) as x";
                        
                        //   System.out.println(" SQL 3 >> "+sSQL);
                        rstMae = stmMonCre.executeQuery(sSQL);
                        double ndexigrp=0;
                        double ndvalexigrp=0;
                        
                        
                        if(rstMae.next()){
                            //   System.out.println("SEC>> "+ rstMae.getInt(1) );
                            ndexigrp=rstMae.getDouble(2);
                            ndvalexigrp=rstMae.getDouble(3);
                        }
                        
                        
                        
                        sSQL= "select a.ne_secemp" +
                        ",a.co_Emp,a.co_loc ,a.co_doc,a.co_tipdoc,b.co_itm,b.nd_can,b.nd_pordes,b.nd_cosuni,b.nd_exi,b.nd_valexi" +
                        ", b.nd_costotgrp as  total" +
                        " ,a.st_reg,b.co_reg as coreg FROM  tbm_cabmovinv as a" +
                        " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                        " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                        " where  b.co_itm is not null and a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                        " and c.co_itm="+rstMae2.getInt(1)+"  and a.ne_secgrp >= "+Glo_intCodSec+"  order by a.ne_secemp,a.co_emp";
                        
                        ////   System.out.println("SQL 4 >> "+ sSQL );
                        
                        rstMae = stmMonCre.executeQuery(sSQL);  //and a.ne_secemp >=  "+rstMae.getInt(1)+"
                        
                        String streg="";
                        while(rstMae.next()){
                            streg = rstMae.getString("st_reg");
                            if(streg.equalsIgnoreCase("I")){ } else {
                                ndexigrp=ndexigrp + rstMae.getDouble(7);
                                ndvalexigrp = ndvalexigrp - rstMae.getDouble("total");
                            }
                            
                            sSQL= "UPDATE tbm_detmovinv SET nd_exigrp="+ndexigrp+",nd_valexigrp="+ndvalexigrp+"  WHERE co_emp="+rstMae.getInt(2)+" " +
                            " and co_loc="+rstMae.getInt(3)+" and co_tipdoc="+rstMae.getInt(5)+" and " +
                            " co_doc="+rstMae.getInt(4)+" and co_itm="+rstMae.getInt(6)+" and co_reg="+rstMae.getInt("coreg");
                            ///  System.out.println("SQL 5 >> "+ sSQL );
                            pst = con.prepareStatement(sSQL);
                            pst.executeUpdate();
                            
                        }
                        //   System.out.println(">> "+ rstMae2.getInt(1) );
                    }
                    rstMae=null;
                    rstMae2.close();
                    rstMae2=null;
                }
                ///  System.out.println("FIN ......OK ");
                con.close();
                con=null;
            }catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
        }
        
        
        
        
        
        
        
        
        
        
        @Override
        public boolean modificar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            
            
            if(!verificaItmUni())
                return false;
            
            
            if(!objUltDocPrint2.verificarsiesconfirmado(txtDoc.getText(),txtTipDocCod.getText()))
                return false;
            
            
            if(!objUltDocPrint2.Verificar_Doc_Asociado(txtDoc.getText(),txtTipDocCod.getText()))
                return false;
            
            
            if (objUltDocPrint2.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtDoc.getText()))){
                return false;
            }
            
            if (!validaCampos())
                return false;
            
            
          
            
            
            if (!actualizarReg())
                return false;
            blnHayCam=false;
            
            //   calcularSaldo();
            
            return true;
        }
        


private boolean verificaItmUni(){
boolean  blnRes=true;
 try{
 for(int i=0; i<tblDat.getRowCount();i++){
    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
           int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
           int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));

               if(intCodItm != intCodItmAct){
                   MensajeInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                   blnRes=false;
                   break;
               }

   }}
 }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
return blnRes;     
}


        
        
        @Override
        public boolean cancelar() {
            boolean blnRes=true;
            try {
                if (blnHayCam) {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            blnHayCam=false;
            return blnRes;
        }
        
        @Override
        public boolean aceptar() {
            return true;
        }
        
        @Override
        public boolean afterAceptar() {
            return true;
        }
        
        @Override
        public boolean afterAnular() {
            return true;
        }
        
        @Override
        public boolean afterCancelar() {
            return true;
        }
        
        @Override
        public boolean afterConsultar() {
            
            butGuiRem.setEnabled(true);
            butItmPen.setEnabled(true);
            butCliRet.setEnabled(true);
            butFacEle.setEnabled(true);
            butGetXmlFac.setEnabled(true);
            return true;
        }
        
        @Override
        public boolean afterEliminar() {
            return true;
        }
        
        @Override
        public boolean afterImprimir() {
            return true;
        }
        
        @Override
        public boolean afterInsertar() {
            
            this.setEstado('w');
            
            return true;
        }
        
        @Override
        public boolean afterModificar() {
            blnHayCam=false;
            return true;
        }
        
        @Override
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        public boolean PreVtaMenorPreUni(){                                    
            boolean blnRes = true;
            int INT_TBL_COITM = 17 ;
            int INT_TBL_PREVEN= 11 ;
            double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = getDesCli(txtCliCod.getText());
            double dblMinimo, dblValorVenta, dblCostoItm, dblPorGanancia = getMarUti(txtCliCod.getText());
            int intCoItm;
            for(int i = 0 ; i < tblDat.getRowCount();i++){
                if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                    dblPreUsr    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString()))),6);
                    dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                    dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                    dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
                    if(dblPreUniItm!=0){
                        dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
                    }else{
                        double dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                        if(dblStkAct != 0)
                            dblCostoItm  =   getCosUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                        else {
                            
                            dblCostoItm = 0.00;
                        }
                        dblMinimo    =   dblCostoItm * dblPorGanancia;
                        dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
                    }
                    ///  System.out.println("OKKK " + dblValorVenta  + " --- " + dblMinimo  );
                    if(dblValorVenta<dblMinimo){
                        blnRes = false;
                        tblDat.setValueAt(true,i,INT_TBL_BLNPRE);
                    }
                }}
            return blnRes;
        }
        
        
        
        private double getCosUni(String int_co_itm){
            double dblPreUni = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL = "  SELECT a1.nd_cosUni FROM tbm_inv AS a1"+
                    " INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                    " WHERE a2.co_emp="+ objZafParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objZafParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblPreUni = rstUni.getDouble("nd_cosuni");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblPreUni; }
            catch(Exception Evt) {  return dblPreUni; }
            return dblPreUni;
        }
        
        
        
        
        
        private double getMarUti(String int_co_cli){
            double dblMarUti = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL= "SELECT nd_maruti from tbm_cli as cli " +
                    " where cli.co_emp = " + objZafParSis.getCodigoEmpresa()+" and cli.co_cli = " + int_co_cli;
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblMarUti = rstUni.getDouble("nd_maruti");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {   return dblMarUti;  }
            catch(Exception Evt) {  return dblMarUti;  }
            return dblMarUti;
        }
        
        
        private double getDesCli(String int_co_cli){
            double dblDesCli = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL= "SELECT nd_maxdes from tbm_cli as cli " +
                    " where cli.co_emp = "+objZafParSis.getCodigoEmpresa()+" and cli.co_cli = "+int_co_cli;
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblDesCli = rstUni.getDouble("nd_maxdes");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblDesCli;  }
            catch(Exception Evt) {  return dblDesCli;  }
            return dblDesCli;
        }
        
        
         
        

public void mostrarMsg(String strMsg) {
    //JOptionPane oppMsg=new JOptionPane();
    //String strTit;
    //strTit="Mensaje del sistema Zafiro";
    JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.OK_OPTION);
}

        
        
        
        
public boolean insertarReg(){
 boolean blnRes=false;
 java.sql.Connection conInsCot;
 String Estado="A";
 int ESTPRE=0;
 int  Est=0;
 int intSecEmp=0,intSecGrp=0;
 try{
   calculaPagos();
   conInsCot=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conInsCot!=null){
     
    dblPorIva = objUti.redondeo(getIva(conInsCot),2);
                    
    if(!Abrir_Conexion_Remota())
        return false;


     if(INTCODREGCEN!=0){
         con_remota.setAutoCommit(true);
         intSecEmp=objUltDocPrint2.getNumSecDoc(conInsCot, objZafParSis.getCodigoEmpresa() );
         intSecGrp=objUltDocPrint2.getNumSecDoc(conInsCot, objZafParSis.getCodigoEmpresaGrupo() );
         con_remota.setAutoCommit(false);
     }else{
       intSecEmp=objUltDocPrint2.getNumSecDoc(conInsCot, objZafParSis.getCodigoEmpresa() );
       intSecGrp=objUltDocPrint2.getNumSecDoc(conInsCot, objZafParSis.getCodigoEmpresaGrupo() );
     }


        

    
     conInsCot.setAutoCommit(false);



   //********nuevo
    ESTPRE = objAutPrg.checkCtlsCot("tbm_cabautcotven", "tbm_detautcotven" , Integer.parseInt((txtCot.getText().equals("")?"0":txtCot.getText())), conInsCot);
   //************
    int intNumDoc = getUltCoDoc(conInsCot);
    if(ESTPRE == 2) Est = 2;
    if(ESTPRE == 3) Est = 3;

    Est=0;

    if(Est==0){
     if(intNumDoc !=-1 ){
      if(insertarCab(conInsCot, intNumDoc,  strMerIngEgr , intSecEmp , intSecGrp  )){
       if(insertarDet(conInsCot, con_remota, intNumDoc, strMerIngEgr, strTipIngEgr )){ 
        if(insertDetPag(intNumDoc, conInsCot )){  
           int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
           java.util.Date dteFecha = new java.util.Date(Fecha[2] , Fecha[1], Fecha[0]);
          if(objAsiDia.insertarDiario(conInsCot,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(txtTipDocCod.getText()), txtDoc.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) { 
           if(CambiarEstCot(conInsCot)){
            if(INTCODREGCEN!=0) con_remota.commit();
               conInsCot.commit();
            STR_ESTREG="I";
            blnRes = true;
            txtDoc.setText(intNumDoc+"");
            consultar3();  //******************
            String strMsg;
            //strTit="Mensaje del sistema Zafiro";
            strMsg="¿Desea imprimir la Factura?";
            //JOptionPane obj =new JOptionPane();
            if(JOptionPane.showConfirmDialog(jfrThis ,strMsg,strTit,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE) == 0){
                imprimir();
            }
          }else{ if(INTCODREGCEN!=0) con_remota.rollback();  conInsCot.rollback(); }
         }else { if(INTCODREGCEN!=0) con_remota.rollback();  conInsCot.rollback(); }
        }else { if(INTCODREGCEN!=0) con_remota.rollback();  conInsCot.rollback(); }
       }else { if(INTCODREGCEN!=0) con_remota.rollback();  conInsCot.rollback(); }
      }else { if(INTCODREGCEN!=0) con_remota.rollback();   conInsCot.rollback(); }
                            
     }else
        conInsCot.rollback();
   }
                    
  if(Est == 2 ) {  ///************
    Estado="P";
   if(intNumDoc !=-1 ){
    if(txtCot.getText().equals("")){
     int intNumCot = getUltCoDocCot(conInsCot);
     if(insertarCabCot(conInsCot, intNumCot, false, Estado )){
      if(insertarDetCot(conInsCot, intNumCot )){
       //if(insertDetPagCot(intNumCot, conInsCot )){
         int intCods[] = {objZafParSis.getCodigoLocal(),   intNumCot };
         if(objAutPrg.insertarCabDetAut(conInsCot, intCods , 3) ){
           conInsCot.commit();
           blnRes = false;
           txtCot.setText(intNumCot+"");
           java.awt.Color color = txtCot.getBackground();
           txtCot.setEditable(false);
           txtCot.setBackground(color);
           String strMsg;
           //strTit="Mensaje del sistema Zafiro";
           strMsg="Se Genero la Cotizacion #"+ intNumCot +" Pendiente de Autorizar.\n La Factura no fue Ingresada";
           //JOptionPane obj =new JOptionPane();
           JOptionPane.showMessageDialog(jfrThis ,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        }else conInsCot.rollback();
      // }else conInsCot.rollback();
      }else  conInsCot.rollback();
     }else  conInsCot.rollback();
   }
   else{
      int intCods[] = {objZafParSis.getCodigoLocal(),   Integer.parseInt((txtCot.getText().equals("")?"0":txtCot.getText())) };
      if(objAutPrg.insertarCabDetAut(conInsCot, intCods , 3) ){
        conInsCot.commit();
        blnRes = false;
        blnHayCam = false;
        String strMsg;
        //strTit="Mensaje del sistema Zafiro";
        strMsg="La Cotizacion #"+ txtCot.getText() +" Esta Pendiente de Autorizar.\n La Factura no fue Ingresada";
        //JOptionPane obj =new JOptionPane();
        JOptionPane.showMessageDialog(jfrThis ,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
     }else conInsCot.rollback();
  }
 }else conInsCot.rollback();
}
if(Est == 3)  blnRes=false;
 //************
 if(INTCODREGCEN!=0)  con_remota.close();
 conInsCot.close();
 if(INTCODREGCEN!=0)  con_remota=null;
 conInsCot=null;

 }}catch(SQLException  Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;  }
   catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
 return blnRes;
}

 
  
        //////////*************** INSERTA COTIZACION ****///////////////
        
        
        private boolean insertarCabCot(java.sql.Connection conIns, int intNumDoc,  boolean blnSolAut, String Estado ){
            boolean blnRes = false;
            try{
                String strFecha = "#" + txtFecDoc.getFecha("/", "y/m/d") + "#";
                String sqlCab = "INSERT INTO  tbm_cabCotVen" +
                "(co_emp, co_loc, co_cot, " + //CAMPOS PrimayKey
                " fe_cot, "                 + //Fecha de la cotizacion
                " co_cli, co_ven, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                " tx_obs1, tx_obs2, nd_sub, nd_porIva, " +//<==Campos que aparecen en la parte inferior del 1er Tab
                " co_forPag, fe_ing, fe_ultmod, co_usring ,co_usrmod, st_reg, nd_tot,ne_val " +
                " ,st_regrep )" +
                "VALUES (" +
                objZafParSis.getCodigoEmpresa() + ", " +
                objZafParSis.getCodigoLocal()   + ", " +
                intNumDoc + ", '" +
                strFecha  + "', " +
                txtCliCod.getText() + ",   " +
                txtVenCod.getText() + ",  '" +
                txtAte.getText()    + "', '" +
                txaObs1.getText()   + "', '" +
                txaObs2.getText()   + "',  " +
                dblSubtotalCot    + ",   " +
                dblPorIva   + " , " +
                txtCodForPag.getText() + ",CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, " +
                objZafParSis.getCodigoUsuario() + " , " +
                objZafParSis.getCodigoUsuario() + " , " +
                ((blnSolAut)?"'P'":"'"+ Estado +"'") +  ","+ txtTot.getText() + ",15,'I')";   //);
                
                java.sql.PreparedStatement pstCabCot = conIns.prepareStatement(sqlCab);
                pstCabCot.executeUpdate();
                blnRes=true;
            }
            catch (java.sql.SQLException e){
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }
        
        
        
        private boolean insertarDetCot(java.sql.Connection conInsCot, int intNumDoc){
            boolean blnRes = false;
            try{
                int Co_Bod = getBodPrede(conInsCot);
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                        String strQry =
                        "INSERT INTO  tbm_detCotVen" +
                        "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                        " co_itm, tx_codalt,tx_codalt2, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                        " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen " +//<==Campos que aparecen en la parte inferior del 1er Tab
                        " ,st_regrep  )" +
                        "VALUES ("  +
                        objZafParSis.getCodigoEmpresa() + ", " +
                        objZafParSis.getCodigoLocal()   + ", " +
                        intNumDoc + ", " +
                        (i+1)       + ", " +
                        tblDat.getValueAt(i, INT_TBL_CODITM) + ",'" +
                        tblDat.getValueAt(i, INT_TBL_ITMALT) + "','" +
                        tblDat.getValueAt(i, INT_TBL_ITMALT2) + "','" +
                        tblDat.getValueAt(i, INT_TBL_DESITM) + "'," +
                        Co_Bod +", "+
                        tblDat.getValueAt(i, INT_TBL_CANMOV) + " ," +
                        Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) + ", " +
                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),6) + ", '" +
                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "' " +
                        ",'I' )";
                        java.sql.PreparedStatement pstDetCot = conInsCot.prepareStatement(strQry);
                        pstDetCot.executeUpdate();
                    } }
                
                blnRes=true;
            }
            catch (Exception e)  {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }
        
        
        
        
        
        
        private boolean insertDetPagCot(int intNumDoc, java.sql.Connection conInsCot ){
            String sql="";
            try{
                
                for(int i=0; i<tblPag.getRowCount();i++){
                    
                    int FecPagDoc[] =  txtFecDoc.getFecha(tblPag.getValueAt(i, 2).toString());
                    String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                    
                    sql = "INSERT INTO  tbm_pagCotVen" +
                    "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                    " ne_diaCre, fe_ven, " +//<==
                    " mo_pag, ne_diaGra, nd_porRet " +//<==
                    " , st_regrep , co_tipret ) " +
                    " VALUES(" +
                    objZafParSis.getCodigoEmpresa() + ", " +
                    objZafParSis.getCodigoLocal()   + ", " +
                    intNumDoc + ", " +
                    (i+1)       + ", " +
                    ((tblPag.getValueAt(i, 1)==null || tblPag.getValueAt(i, 1).toString().equals(""))?"0":tblPag.getValueAt(i, 1)) + ",'#" +
                    strFechaPag + "#'," +
                    objUti.redondeo(Double.parseDouble( (tblPag.getValueAt(i, 4)==null)?"0":tblPag.getValueAt(i, 4).toString() ),6) + ", " +
                    ((tblPag.getValueAt(i, 5)==null)?"0":tblPag.getValueAt(i, 5).toString() ) + ", " +
                    ((tblPag.getValueAt(i, 3)==null || tblPag.getValueAt(i, 3).toString().equals(""))?"0":tblPag.getValueAt(i, 3).toString()) +
                    ",'I' , "+ Integer.parseInt(((tblPag.getValueAt(i, 6)==null)?null:tblPag.getValueAt(i, 6).toString()))+" )";
                    java.sql.PreparedStatement pstDetCot = conInsCot.prepareStatement(sql);
                    pstDetCot.executeUpdate();
                    
                }
            }
            catch(SQLException Evt)
            {   objUti.mostrarMsgErr_F1(jfrThis, Evt);
                
                return false;
            }
            catch(Exception Evt)
            {   objUti.mostrarMsgErr_F1(jfrThis, Evt);
                return false;
            }
            return true;
        }
        
        
        
        //         private boolean insertDetPagCot(int intNumDoc, java.sql.Connection conInsCot ){
        //               try{
        //
        //                    for(int i=0; i<tblCotForPag.getRowCount();i++){
        //
        //                        int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
        //                        String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
        //                        java.sql.PreparedStatement pstDetCot = conInsCot.prepareStatement(
        //                                 "INSERT INTO  tbm_pagCotVen" +
        //                                   "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
        //                                   " ne_diaCre, fe_ven, " +//<==
        //                                   " mo_pag, ne_diaGra, nd_porRet ,st_regrep " +//<==
        //                                   "  )" +
        //                                   "VALUES (" +
        //                                   objZafParSis.getCodigoEmpresa() + ", " +
        //                                   objZafParSis.getCodigoLocal()   + ", " +
        //                                   intNumDoc + ", " +
        //                                   (i+1)       + ", " +
        //                                   tblCotForPag.getValueAt(i, 1) + ",'#" +
        //                                   strFechaPag + "#'," +
        //                                   objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) + ", " +
        //                                   ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
        //                                   ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"null":tblCotForPag.getValueAt(i, 3).toString()) +
        //                                   ",'I' )");
        //                                  pstDetCot.executeUpdate();
        //                      }
        //               }
        //                catch(SQLException Evt)
        //                {   objUti.mostrarMsgErr_F1(jfrThis, Evt);
        //                   return false;
        //                }
        //                catch(Exception Evt)
        //                {   objUti.mostrarMsgErr_F1(jfrThis, Evt);
        //                    return false;
        //                 }
        //              return true;
        //            }
        //
        
        
        
        
        
        ///*************** MODIFICACION DE COTIZACION EN CASO AUTORIZADO
        
        
        private boolean modificarCabCot(java.sql.Connection conModCot, boolean blnSolAut, String Estado){
            boolean blnRes = false;
            try{
                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                dateFecCot = Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0];
                dateFecCot = objUti.formatearFecha(dateFecCot,"yyyy/MM/dd","dd/MM/yyyy");
                /*********************************************\
                 * Armando el Update para los datos          *
                 * de la cabecera de cotizacion              *
                 * \*********************************************/
                java.sql.PreparedStatement pstCabCot = conModCot.prepareStatement(
                "Update tbm_cabCotVen set " +
                " fe_cot  = '" + strFecha            + "', " +
                " co_cli  = "  + txtCliCod.getText() + ",  " +
                " co_ven  = "  + txtVenCod.getText() + ",  " +
                " tx_ate  = '" + txtAte.getText()    + "', " +
                " nd_tot  = '" + txtTot.getText()    + "', " +
                " tx_obs1 = '" + txaObs1.getText()   + "', " +
                " tx_obs2 = '" + txaObs2.getText()   + "', " +
                " nd_sub  = "  + objUti.redondeo(Double.parseDouble(txtSub.getText()),6)    + ",  " +
                " nd_porIva = "+ dblPorIva           +  ", " +
                " co_forPag = "+ txtCodForPag.getText() + ", " +
                " fe_ultMod   =  CURRENT_TIMESTAMP                , " +
                " co_usrMod   = " + objZafParSis.getCodigoUsuario() + ", " +
                " st_reg   = " + "'"+ Estado +"'" + " " +
                "  where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                " co_cot = " +  txtCot.getText());
                /* Ejecutando el Update */
                pstCabCot.executeUpdate();
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            return blnRes;
        }
        
        private boolean modificarDetCot(java.sql.Connection conModCot){
            boolean blnRes = false;
            try{
                /*********************************************\
                 * Armando el Delete para quitar los         *
                 * registro de detalle de cotizacion         *
                 * actuales.                                 *
                 * \*********************************************/
                java.sql.PreparedStatement pstDelDetCot = conModCot.prepareStatement(
                " DELETE FROM tbm_detCotVen " +
                "  where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                " co_cot = " +  txtCot.getText());
                /* Ejecutando el Delete */
                pstDelDetCot.executeUpdate();
                /*********************************************\
                 * Armando el Insert para los datos          *
                 * del detalle de cotizacion                 *
                 * \*********************************************/
                String strSqlDet="";
                
                
                
                int Co_Bod = getBodPrede(conModCot);
                
                ///  System.out.println(">> 01 "+ Co_Bod );
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                        
                        String sql = "INSERT INTO  tbm_detCotVen" +
                        "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
                        " co_itm, tx_codalt, tx_nomItm, " +//<==Campos que aparecen en la parte superior del 1er Tab
                        " co_bod, nd_can, nd_preUni, nd_porDes, st_ivaVen " +//<==Campos que aparecen en la parte inferior del 1er Tab
                        " )" +
                        "VALUES ("  +
                        objZafParSis.getCodigoEmpresa() + ", " +
                        objZafParSis.getCodigoLocal()   + ", " +
                        txtCot.getText() + ", " +
                        (i+1)       + " , ";
                        sql +=  tblDat.getValueAt(i, INT_TBL_CODITM) + ",'";
                        sql +=  tblDat.getValueAt(i, INT_TBL_ITMALT) + "','";
                        sql +=  tblDat.getValueAt(i, INT_TBL_DESITM) + "',";
                        sql +=   Co_Bod + ", ";
                        sql +=    tblDat.getValueAt(i, INT_TBL_CANMOV) + " ,";
                        sql +=    Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(i, INT_TBL_PREUNI).toString()) + ", ";
                        sql +=    objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),2) + ", '";
                        sql +=   ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'";
                        sql +=    " )";
                        /* Ejecutando el insert */
                        java.sql.PreparedStatement pstDetCot = conModCot.prepareStatement(sql);
                        pstDetCot.executeUpdate();
                    } }
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
            catch (Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
        
        
        
        
        private boolean modificarPagCot(java.sql.Connection conModCot){
            boolean blnRes = false;
            String sql;
            try{
//                /*********************************************\
//                 * Armando el Delete para quitar los         *
//                 * registro de detalle de Pago               *
//                 * actuales.                                 *
//                 * \*********************************************/
//                java.sql.PreparedStatement pstDelCabPagCot = conModCot.prepareStatement(
//                " DELETE FROM tbm_pagCotVen " +
//                "  where " +
//                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
//                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
//                " co_cot = " +  txtCot.getText());
//                pstDelCabPagCot.executeUpdate();
//                /*********************************************\
//                 * Armando el Insert para los datos          *
//                 * del detalle de PAgo                         *
//                 * \*********************************************/
//                for(int i=0; i<tblCotForPag.getRowCount();i++){
//                    int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
//                    String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
//
//                    sql =   "INSERT INTO  tbm_pagCotVen" +
//                    "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
//                    " ne_diaCre, fe_ven, " +//<==
//                    " mo_pag, ne_diaGra, nd_porRet   " +//<==
//                    " , co_tipret )" +
//                    "VALUES (" +
//                    objZafParSis.getCodigoEmpresa() + ", " +
//                    objZafParSis.getCodigoLocal()   + ", " +
//                    txtCot.getText() + ", " +
//                    (i+1)       + ", " +
//                    ((tblCotForPag.getValueAt(i, 1)==null || tblCotForPag.getValueAt(i, 1).toString().equals(""))?"0":tblCotForPag.getValueAt(i, 1)) + ",'#" +
//                    strFechaPag + "#'," +
//                    objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) + ", " +
//                    ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
//                    ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"null":tblCotForPag.getValueAt(i, 3).toString()) +
//                    ", "+Integer.parseInt(((tblCotForPag.getValueAt(i, 6)==null)?"0":tblCotForPag.getValueAt(i, 6).toString()))+" )";
//                    java.sql.PreparedStatement pstInsPagCot = conModCot.prepareStatement(sql);
//                    pstInsPagCot.executeUpdate();
//
//                }
                blnRes=true;
            }
//            catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
            return blnRes;
        }
        
        
        
        
        
        
        //            private boolean modificarPagCot(java.sql.Connection conModCot ){
        //                boolean blnRes = false;
        //                try{
        //                    /*********************************************\
        //                     * Armando el Delete para quitar los         *
        //                     * registro de detalle de Pago               *
        //                     * actuales.                                 *
        //                    \*********************************************/
        //                    java.sql.PreparedStatement pstDelCabPagCot = conModCot.prepareStatement(
        //                          " DELETE FROM tbm_pagCotVen " +
        //                           "  where " +
        //                           " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
        //                           " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
        //                           " co_cot = " +  txtCot.getText());
        //
        //                    /* Ejecutando el Delete */
        //                    pstDelCabPagCot.executeUpdate();
        //                    /*********************************************\
        //                     * Armando el Insert para los datos          *
        //                     * del detalle de PAgo                       *
        //                    \*********************************************/
        //                    for(int i=0; i<tblCotForPag.getRowCount();i++){
        //                        int FecPagDoc[] =  txtFecDoc.getFecha(tblCotForPag.getValueAt(i, 2).toString());
        //                        String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
        //                                    java.sql.PreparedStatement pstInsPagCot = conModCot.prepareStatement(
        //                                          "INSERT INTO  tbm_pagCotVen" +
        //                                           "(co_emp, co_loc, co_cot, co_reg, " + //CAMPOS PrimayKey
        //                                           " ne_diaCre, fe_ven, " +//<==
        //                                           " mo_pag, ne_diaGra, nd_porRet   " +//<==
        //                                           "  )" +
        //                                           "VALUES (" +
        //                                           objZafParSis.getCodigoEmpresa() + ", " +
        //                                           objZafParSis.getCodigoLocal()   + ", " +
        //                                           txtCot.getText() + ", " +
        //                                           (i+1)       + ", " +
        //                                           tblCotForPag.getValueAt(i, 1) + ",'#" +
        //                                           strFechaPag + "#'," +
        //                                           objUti.redondeo(Double.parseDouble( (tblCotForPag.getValueAt(i, 4)==null)?"0":tblCotForPag.getValueAt(i, 4).toString() ),6) + ", " +
        //                                           ((tblCotForPag.getValueAt(i, 5)==null)?"0":tblCotForPag.getValueAt(i, 5).toString() ) + ", " +
        //                                           ((tblCotForPag.getValueAt(i, 3)==null || tblCotForPag.getValueAt(i, 3).toString().equals(""))?"null":tblCotForPag.getValueAt(i, 3).toString()) +
        //                                   " )");
        //                        /* Ejecutando el insert */
        //                        pstInsPagCot.executeUpdate();
        //                      }
        //                    blnRes=true;
        //                }
        //                catch (java.sql.SQLException e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
        //                catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
        //                return blnRes;
        //            }
        //
        //
        //
        
        
        /////////////////////**********************************///////////////
        /////////////////////////////////////////////////////////////////////
        
        
        
        
        /////////////////////////////////////*********************************/////////////////////////////////////////
        /////////////////////////////////////*********************************/////////////////////////////////////////
        
        
        
        
        
        private boolean insertarCab(java.sql.Connection conIns, int intNumDoc, String strMerIngEgr, int intSecEmp, int  intSecGrp ){
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            String strSql="";
            try{
                ///******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
                Glo_intCodSec = intSecGrp;
                stmLoc=conIns.createStatement();
                ///*******************************************************************///
                //************  PERMITE SABER SI EL NUMERO DE FACTURA ESTA DUPLICADO  *****************/
                String sql = "select count(ne_numdoc) as num from tbm_cabmovinv  where " +
                " co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" " +
                "and co_tipdoc="+txtTipDocCod.getText()+" and ne_numdoc="+txtFac.getText();
                java.sql.Statement stm2 = conIns.createStatement();
                java.sql.ResultSet rstNum2 = stm2.executeQuery(sql);
                if(rstNum2.next()){
                    if(rstNum2.getInt(1) >= 1 ){
                        //JOptionPane oppMsg = new JOptionPane();
                        String strMsg;
                        //strTit="Mensaje del sistema Zafiro";
                        strMsg=" No. de Factura ya existe... ?";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE,null);
                        return false;
                    }
                }
                ////************************************************************************************
                rstNum2.close();
                rstNum2=null;
                stm2.close();
                stm2=null;
                
                String strconinv="F";
                for(int i=0; i<tblDat.getRowCount();i++){
                 if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                   String strEst=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                     if(strEst.equals("N")){
                          strconinv="P";
                }}}

                if(strconinv.equals("P"))
                 if(strMerIngEgr.equals("N")) strconinv="F";
               //INT_TBL_IEBODFIS  st_coninv  st_autingegrinvcon
                
                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                dblPorIva = objUti.redondeo(getIva(conIns),2);
                                
                /*strSql="INSERT INTO  tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, " + //CAMPOS PrimayKey
                " ne_secemp, ne_secgrp, fe_doc, "                 + //secuencial de documentos y Fecha
                " co_cli, co_com, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, "                                   +//INformacion del vendedor
                " ne_numDoc, ne_numCot, tx_numped, ne_numGui, " +//<==Campos que aparecen en la parte superior del 1er Tab
                " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot, nd_valiva, co_forPag,  " +
                " tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, "   + //Campos del Tab OTROS
                " fe_ing, co_usrIng, fe_ultMod, co_usrMod, tx_desforpag, st_reg , st_regrep, st_coninv " + //Campos de Auditoria Fecha de Ingreso Y Usuario q ingreso.
                //" ,ne_secempant , ne_secgrpant  " +
                " )" +
                "VALUES("+ objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+
                ""+txtTipDocCod.getText()+","+intNumDoc+", "+intSecEmp+", "+intSecGrp+", '"+strFecha+"', "+
                ""+txtCliCod.getText()+","+txtVenCod.getText()+", '"+txtAte.getText()+"', "+        
                "'"+txtCliNom.getText()+"', '"+txtCliDir.getText()+"', '"+txtcli_identificacion.getText()+"', "+        
                "'"+txtcli_telefono.getText()+"','"+txtcli_ciudad.getText()+"', '"+txtVenNom.getText()+"',"+        
                ""+txtFac.getText()+", "+((txtCot.getText().equals(""))?"0":txtCot.getText())+", "+         
                ""+((txtPed.getText().equals(""))?"0":txtPed.getText())+", "+
                ""+((txtGuia.getText().equals(""))?"0":txtGuia.getText())+", "+        
                "'"+txaObs1.getText()+"', '"+txaObs2.getText()+"', "+ (objUti.redondeo(dblSubtotalCot,6)* -1) +","+ 
                ""+objUti.redondeo(dblPorIva , 2)+", "+(objUti.redondeo(dblTotalCot , 6)* -1)+", "+
                ""+(Double.parseDouble(txtIva.getText())* -1)+", "+        
                ""+txtCodForPag.getText()+",'"+txtPartida.getText()+"',"+
                "'"+txtTransporte.getText()+"', "+vecTransporte.get(cboMotivoTransporte.getSelectedIndex())+", "+
                "'"+cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex())+"',"+         
                ""+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+","+
                ""+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+","+
                "'"+txtNomForPag.getText()+"','A','I','"+strconinv+"')";*/
                
                strSql="INSERT INTO  tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, " + //CAMPOS PrimayKey
                " ne_secemp, ne_secgrp, fe_doc, "                 + //secuencial de documentos y Fecha
                " co_cli, co_com, tx_ate, " +//<==Campos que aparecen en la parte superior del 1er Tab
                " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, "                                   +//INformacion del vendedor
                " ne_numDoc, ne_numCot, tx_numped, ne_numGui, " +//<==Campos que aparecen en la parte superior del 1er Tab
                " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot, nd_valiva, co_forPag,  " +
                " tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, "   + //Campos del Tab OTROS
                " fe_ing, co_usrIng, fe_ultMod, co_usrMod, tx_desforpag, st_reg , st_regrep, st_coninv "; //Campos de Auditoria Fecha de Ingreso Y Usuario q ingreso.
                if (intTipForPag2 == 4) { // José Marín M. 10/Feb/2014
                    strSql+=" ,co_tipCre, nd_porComTarCre, nd_porDesVenTarCre ";
                }
                strSql+=" )" +
                "VALUES("+ objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+
                ""+txtTipDocCod.getText()+","+intNumDoc+", "+intSecEmp+", "+intSecGrp+", '"+strFecha+"', "+
                ""+txtCliCod.getText()+","+txtVenCod.getText()+", '"+txtAte.getText()+"', "+        
                "'"+txtCliNom.getText()+"', '"+txtCliDir.getText()+"', '"+txtcli_identificacion.getText()+"', "+        
                "'"+txtcli_telefono.getText()+"','"+txtcli_ciudad.getText()+"', '"+txtVenNom.getText()+"',"+        
                ""+txtFac.getText()+", "+((txtCot.getText().equals(""))?"0":txtCot.getText())+", "+         
                ""+((txtPed.getText().equals(""))?"0":txtPed.getText())+", "+
                ""+((txtGuia.getText().equals(""))?"0":txtGuia.getText())+", "+        
                "'"+txaObs1.getText()+"', '"+txaObs2.getText()+"', "+ (objUti.redondeo(dblSubtotalCot,6)* -1) +","+ 
                ""+objUti.redondeo(dblPorIva , 2)+", "+(objUti.redondeo(dblTotalCot , 6)* -1)+", "+
                ""+(Double.parseDouble(txtIva.getText())* -1)+", "+        
                ""+txtCodForPag.getText()+",'"+txtPartida.getText()+"',"+
                "'"+txtTransporte.getText()+"', "+vecTransporte.get(cboMotivoTransporte.getSelectedIndex())+", "+
                "'"+cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex())+"',"+         
                ""+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+","+
                ""+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+","+
                "'"+txtNomForPag.getText()+"','A','I',"+ objUti.codificar(strconinv )+" ";
                if (intTipForPag2 == 4) { // José Marín M 10/Feb/2014
                    strSql+=" ," + objUti.codificar(txtCodTarCre.getText() )  + ", " + 
                            objUti.codificar(txtPorComTarCre.getText() ) + ", " +
                            objUti.codificar(txtPorDesVenTarCre.getText() ) + " ";
                }
                strSql+= "  )";
                 
                stmLoc.executeUpdate(strSql);
                
                stmLoc.close();
                stmLoc=null;
                
               blnRes=true;
        
            }catch(java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
             catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
           return blnRes;
         }
        
        
        
        //InvertirInventario
        
private boolean insertarDet(java.sql.Connection conIns, java.sql.Connection con_remota, int intNumDoc, String strMerIngEgr, String strTipIngEgr){
  boolean blnRes = false;
  StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
  String str_MerIEFisBod="S";
  String strSql="";
  int intest=0;
  int intControl=0;
  int intCodItmAct=0;
  int intCodBod=0;
  int intReaActStk=0;
  double dlbcostouni=0.00;
  double bldcanmov=0.00;
  double bldcostot=0.00;
  double dbl_canConIE=0.00;
  double dlbCan=0;
  java.sql.Statement stmLoc;
  try{
     int intTipCli=3;
     intTipCli= objUltDocPrint2.ValidarCodigoCliente(txtCliCod.getText(),conIns);
     stmLoc=conIns.createStatement(); 
     objInvItm.limpiarObjeto();
     objInvItm.inicializaObjeto();
     GLO_strArreItm="";
     for(int i=0; i<tblDat.getRowCount();i++){
      if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
     
        intCodItmAct=Integer.parseInt( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_CODITM) )); 
        intCodBod   =Integer.parseInt( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_CODBOD) ));  
        dlbCan   = Double.parseDouble( objInvItm.getIntDatoValidado( tblDat.getValueAt(i, INT_TBL_CANMOV) ));  
        
        dbl_canConIE=dlbCan;
        /***************VERFIFICA SI EL ITEM IE MER FIS BOD************************/   
        String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
        str_MerIEFisBod="N";
        
        if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){
         if(strEstFisBod.equals("N")){
           if(strMerIngEgr.equals("S")){   
             str_MerIEFisBod="S";
             dbl_canConIE=0;
         }}
         
         //String strTerminal = tblDat.getValueAt(i, INT_TBL_ITMALT).toString().substring(tblDat.getValueAt(i, INT_TBL_ITMALT).toString().length() -1, tblDat.getValueAt(i, INT_TBL_ITMALT).toString().length());
         
         //if (!(strTerminal.equals("L") || strTerminal.equals("S"))){
          objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItmAct, intCodBod, dlbCan, -1, strEstFisBod, strMerIngEgr, strTipIngEgr, 1);      
          intReaActStk=1;
         //}
        }
                                
        dlbcostouni=objInvItm.getCostoItm(conIns, objZafParSis.getCodigoEmpresa(), intCodItmAct); 
        bldcostot = (dlbcostouni * dlbCan);
        bldcostot = (bldcostot*-1);
        bldcanmov = (dlbCan * -1);
                      
       if(intControl!=0)
         GLO_strArreItm=GLO_strArreItm+",";
       GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
       intControl++;
       
       if(i>0) stb.append(" UNION ALL ");
       /*stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtTipDocCod.getText()+", "+
       intNumDoc+", "+(i+1)+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"', '"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"', "+
       tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM)+",'"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+
       tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+tblDat.getValueAt(i, INT_TBL_CODBOD)+", "+
       bldcanmov+", "+objUti.redondear((Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i,  INT_TBL_TOTAL).toString())*-1),2)+", "+
       dlbcostouni+", "+bldcostot+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), 6)+", "+ 
       objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null  )?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString())  , 2) + ", '" +
       ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"', 'P', "+bldcostot+", 'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" ");*/
       
       stb.append("SELECT "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtTipDocCod.getText()+", "+
       intNumDoc+", "+(i+1)+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"', '"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"', "+
       tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM)+", "+objUti.codificar(tblDat.getValueAt(i, INT_TBL_DESITM) )+" ,'"+
       tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+tblDat.getValueAt(i, INT_TBL_CODBOD)+", "+
       bldcanmov+", "+objUti.redondear((Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i,  INT_TBL_TOTAL).toString())*-1),2)+", "+
       dlbcostouni+", "+bldcostot+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), 6)+", "+ 
       objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null  )?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString())  , 2) + ", '" +
       ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"', 'P', "+bldcostot+", 'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" ");
       intest=1;
    }}
                
   if(intest==1){
     strSql="INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, "+ //CAMPOS PrimayKey
     " tx_codAlt, tx_codAlt2, co_itm,co_itmact, tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
     " co_bod, nd_can, nd_tot, nd_cosUnigrp, nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
     " ,st_reg , nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon  )" +stb.toString();
     stb=null;
     stmLoc.executeUpdate(strSql);
     stmLoc.close();
     stmLoc=null;
   }
  
  if(intReaActStk==1){
    if(!objInvItm.ejecutaActStock(conIns, intTipCli))
      return false;
    if(con_remota!=null){
      if(!objInvItm.ejecutaActStock(con_remota, intTipCli))
       return false;
      if(!objInvItm.ejecutaVerificacionStock(con_remota, intTipCli))
       return false; 
    }else{
      if(!objInvItm.ejecutaVerificacionStock(conIns, intTipCli))
        return false; 
  }}
  objInvItm.limpiarObjeto();
    blnRes=true;
  }catch(java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
   catch(Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
 return blnRes;
}

        private boolean CambiarEstCot(java.sql.Connection conIns ){
            String  Estado="F";
            boolean blnRes=true;
            try{
                if(txtCot.getText().equals("")){
                }else{
                    if(modificarCabCot(conIns, false,Estado) ){
                        if(modificarDetCot(conIns)){
                            if(modificarPagCot( conIns )){
                                blnRes=true;
                            }else
                                return false;
                        }else
                            return false;
                    }else
                        return false;
                }}
            catch(Exception Evt)   {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;   }
            return blnRes;
        }
        
        
        
        
        private boolean insertDetPag(int intNumDoc, java.sql.Connection conIns ){
            try{
                for(int i=0; i<tblPag.getRowCount();i++){
                    
                    int FecPagDoc[] =  txtFecDoc.getFecha(tblPag.getValueAt(i, 2).toString());
                    String strFechaPag = "#" + FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0] + "#";
                    String strSop = ((tblPag.getValueAt(i, 7)==null)?"":tblPag.getValueAt(i, 7).toString() );
                    
                    String sql = "INSERT INTO  tbm_pagMovInv" +
                    "(co_emp, co_loc, co_doc, co_tipDoc, co_reg, " + //CAMPOS PrimayKey
                    " ne_diaCre, fe_ven, " +//<==
                    " mo_pag, ne_diaGra, nd_porRet , st_regrep ,st_sop, co_tipret" +//<==
                    "  )" +
                    "VALUES (" +
                    objZafParSis.getCodigoEmpresa() + ", " +
                    objZafParSis.getCodigoLocal()   + ", " +
                    intNumDoc + ", " +
                    txtTipDocCod.getText() + ", " +
                    (i+1)       + ", " +
                    objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCRE))+ ",'"+strFechaPag+"',"+
                    (objUti.redondear(Double.parseDouble( objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGMON) ) ),intNumDec) * -1 )+ ", " +
                    objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGGRA) )+", " +
                    objInvItm.getIntDatoValidado( tblPag.getValueAt(i, INT_TBL_PAGRET) )+
                    " ,'I','"+strSop+"' ,"+Integer.parseInt(objInvItm.getIntDatoValidado(tblPag.getValueAt(i, INT_TBL_PAGCOD)))+")";
                   
                    java.sql.PreparedStatement pstDetPag = conIns.prepareStatement(sql);

                    pstDetPag.executeUpdate();
                }
                
               
            }
            catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
            catch(Exception Evt) {   objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;   }
            return true;
        }
        
        
        
        
        
        
        private int getUltCoDoc(java.sql.Connection con){
            int valReturn = 0;
            try{
                Statement stmMaxDoc = con.createStatement();
                String strSQLMax= "SELECT Max(co_doc) as co_doc  FROM tbm_cabMovInv where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() +
                " and co_loc = " + objZafParSis.getCodigoLocal() +
                " and co_tipDoc = " + txtTipDocCod.getText() ;
                ResultSet rstNum = stmMaxDoc.executeQuery(strSQLMax);
                if(rstNum.next()){
                    valReturn = rstNum.getInt("co_doc");
                }
                valReturn++;
            }
            catch(java.sql.SQLException e){ valReturn= -1; }
            catch(Exception e){ valReturn= -1;  }
            return valReturn;
        }
        
        
        private int getUltCoDocCot(java.sql.Connection conMAX){
            int valReturn = 0;
            try{
                java.sql.Statement stmMaxCot = conMAX.createStatement();
                sSQL= "SELECT Max(co_cot) as co_cot FROM tbm_cabCotVen where " +
                " co_emp = "+objZafParSis.getCodigoEmpresa()+" and co_loc = " + objZafParSis.getCodigoLocal();
                ResultSet rstNum = stmMaxCot.executeQuery(sSQL);
                if(rstNum.next())
                    valReturn = rstNum.getInt("co_cot");
                valReturn++;
                stmMaxCot.close();
                stmMaxCot=null;
            }
            catch(java.sql.SQLException e){ valReturn= -1; }
            catch(Exception e){ valReturn= -1; }
            return valReturn;
        }
        
        
        
        /////////////////////////////////////*********************************/////////////////////////////////////////
        /////////////////////////////////////*********************************/////////////////////////////////////////
        
        
        
        
        
        public boolean consultar3() {
            return _consultar(FilSqlBus());
        }
        
        

private boolean validarTipoDoc(){

   if(txtTipDocCod.getText().equals("")){
       MensajeInf("NO HAY TIPO DE DOCUMENTO PARA LA BUSQUEDA..");
       return false;
   } else
      return true;

}


        
        @Override
        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */

          if(validarTipoDoc()){
            return _consultar(FilSqlBus());
          }else
             return false;

        }
        
        @Override
        public void clickModificar(){
            setEditable(true);
            // txtFecDoc.setHoy();
            java.awt.Color color = txtSub.getBackground();
            
            txtDoc.setEditable(false);
            txtCot.setEditable(false);
            txtCot.setBackground(color);
            txtFac.setEditable(false);
            txtGuia.setEditable(false);
            txtGuia.setBackground(color);
            txtTipDocAli.setEditable(false);
            txtTipDocNom.setEditable(false);
            butTipDoc.setEnabled(false);
            txtSub.setEditable(false);
            txtIva.setEditable(false);
            txtTot.setEditable(false);
            txtSub.setBackground(color);
            txtIva.setBackground(color);
            txtTot.setBackground(color);
            
            strCodCli="";
            strDesLarCli="";
            strCodCli="";
            strDesLarCli="";
            
            dblPorIva=dblPorIvaaux;
            lblIva.setText("IVA " + dblPorIva + "%");
            
            objAsiDia.setEditable(true);
            
        }
        
        //**********************************************************************
//        public boolean IngresarInventario(java.sql.Connection conIns, java.sql.Connection con_remota, int intCodItm, int intCodBod, double intCodCan, Librerias.ZafUtil.ZafTipItm objZafTipItm, String strNomItem, String strEstFisBod){
//            boolean blnRes=true;
//            try{
//                if(!objZafTipItm.isServicio(intCodItm)){
//                    
//                    
//                    objZafInv.movInventario_solo_FAC(con_remota, intCodItm, intCodBod, -1, intCodCan, conIns, strEstFisBod, strMerIngEgr, strTipIngEgr );
//                    
//                    
//                    
//                    int intTipCli=3;
//                    intTipCli= objUltDocPrint2.ValidarCodigoCliente(txtCliCod.getText(),conIns);
//                    
//                    
//                    if(intTipCli==0){
//                        if(!objSisCon.existeStockItemEmpresa_Factura_solo_FAC(conIns, con_remota, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), intCodItm , intCodCan , intCodBod )){
//                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
//                            String strTit, strMsg;
//                            strTit="Mensaje del sistema Zafiro";
//                            strMsg=" ***El producto :"+ strNomItem +"  No tiene stock suficiente "+ "\n No se puede facturar!!" ;
//                            oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                            return false;
//                        }}
//                    
//                    
//                    if(intTipCli==1){
//                        if(!objSisCon.actualizarInventario_2_solo_OC(conIns, con_remota,
//                        objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),
//                        intCodItm , (intCodCan * -1), intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
//                            
//                            return false;
//                        }
//                        
//                        if(!objSisCon.existeStockItem_2_solo_FAC(conIns, con_remota, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), intCodItm , intCodCan , intCodBod )){
//                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
//                            String strTit, strMsg;
//                            strTit="Mensaje del sistema Zafiro";
//                            strMsg=" ***El producto : " +  strNomItem + " No tiene stock suficiente "+ "\n No se puede facturar!!" ;
//                            oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                            return false;
//                        }
//                        
//                    }
//                    
//                    
//                    
//                    
//                    
//                    
//                }
//                blnRes=true;
//            }
//            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
//            return blnRes;
//        }
//        
        //**********************************************************************
        
        public boolean InvertirInventario(java.sql.Connection conAnu, java.sql.Connection con_remota, int intCodItm, int intCodBod, double intCodCan, Librerias.ZafUtil.ZafTipItm objZafTipItm, String strEstFisBod ){
            boolean blnRes=true;
            try{
                String  strActInv = "", strActInv2 = "", strOrAct= "";
                
                if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO
                    
                    
                                String strAux="";
                                if(strEstFisBod.equals("N")){
                                 if(strMerIngEgr.equals("S")){
                                   if(strTipIngEgr.equals("I")){ 
                                       if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
                                       if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
                                   }
                                   if(strTipIngEgr.equals("E")){
                                       if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
                                       if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
                                   } 
                                 }} 
                                
                                
                    String sql ="update  TBM_INVBOD set  " +
                    "     nd_stkact = nd_stkact + " + (intCodCan)  +
                    " "+strAux+" "+        
                    " WHERE " +
                    " co_emp = " + objZafParSis.getCodigoEmpresa() + "and " +
                    " co_bod = " + intCodBod + " and " +
                    " co_itm = " + intCodItm;
                    
                    java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                    pstUptInvBod.executeUpdate();
                    if(INTCODREGCEN!=0){
                        pstUptInvBod = con_remota.prepareStatement(sql);
                        pstUptInvBod.executeUpdate();
                    }
                    strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
                    strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
                    strOrAct  =" or ";
                    
                    
                    int intTipCli=3;
                    intTipCli= objUltDocPrint2.ValidarCodigoCliente(txtCliCod.getText(),conAnu);
                    
                    
//                    if(intTipCli==1){
//                        if(!objSisCon.actualizarInventario_2_solo_OC(conAnu, con_remota, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), intCodItm, Math.abs(intCodCan), intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
//                            return false;
//                        }}
                    
                    
                } //********  FIN DE ITEM SERVICIO
                
                
                if(!strActInv.equals("")){
                    String strUpdate = " UPDATE tbm_inv " +
                    " SET st_regrep='M',  nd_stkAct=b1.nd_stkAct " +
                    " FROM  " +
                    "( " +
                    " SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct " +
                    " FROM tbm_invBod AS a2 " +
                    " WHERE a2.co_emp=1     " +
                    " and ( " + strActInv2 +" ) " +
                    " GROUP BY a2.co_emp, a2.co_itm " +
                    " ORDER BY a2.co_itm " +
                    ") AS b1 " +
                    " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " +
                    " AND ( " + strActInv +" ) " +
                    " AND tbm_inv.co_emp=1";
                    java.sql.PreparedStatement pstActStkInv = conAnu.prepareStatement(strUpdate);
                    pstActStkInv.executeUpdate();
                    if(INTCODREGCEN!=0){
                        pstActStkInv = con_remota.prepareStatement(strUpdate);
                        pstActStkInv.executeUpdate();
                    }
                }
                
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
        ///**********************************///**********************************************
        
        
        public boolean AnularRegEliminados(java.sql.Connection conAnu, java.sql.Connection con_remota, Librerias.ZafUtil.ZafTipItm  objZafTipItm){
            boolean blnRes=true;
            try{
                java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                if (arlAux!=null){
                    for (int i=0;i<arlAux.size();i++){
                        int intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
                        int intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
                        double intCodCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
                        //// System.out.println(">>>  ELEMENTO ELIMINADO >> ");
                        
                        String strEstFisBod= objUti.getStringValueAt(arlAux, i, INT_ARR_IEBODFIS);
                        
                        if(InvertirInventario(conAnu, con_remota,  intCodItm, intCodBod, intCodCan, objZafTipItm, strEstFisBod)){
                            //No actualiza grupo
                        }else{
                            return false;
                        }
                    }
                }
                blnRes=true;
            }
            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
        //********************************************************
//        public boolean AnularRegdeDetalle(java.sql.Connection conMod, java.sql.Connection con_remota, Librerias.ZafUtil.ZafTipItm  objZafTipItm){
//            boolean blnRes=true;
//            try{
//                for(int i=0; i<tblDat.getRowCount();i++){
//                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                        
//                        int intCodItm =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_CODORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_CODORI).toString()));
//                        int intCodBod =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_BODORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_BODORI).toString()));
//                        double intCodCan = Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_CANORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANORI).toString()));
//                        
//                        int intCodItm2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
//                        int intCodBod2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
//                        double intCodCan2 = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
//                        String strNomItem =  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString());
//                        
//                        String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
//                        
//                        if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("N")){
//                            System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION >> ");
//                            if(IngresarInventario(conMod, con_remota,  intCodItm2, intCodBod2, intCodCan2, objZafTipItm, strNomItem, strEstFisBod)){
//                                blnRes=true;
//                            }else{
//                                return false;
//                            }
//                            
//                            
//                        }else {
//                            if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("E")){
//                                System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO >> ");
//                            }
//                            if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
//                                
//                                if(intCodItm!=intCodItm2){
//                                    /**/   System.out.println("** REALIZA DEVOLUCION DEL INVENTARIO DEL ITEM ORIGEN >> "+intCodItm+ " CANTIDAD "+ intCodCan );
//                                    if(InvertirInventario(conMod, con_remota, intCodItm, intCodBod, intCodCan, objZafTipItm, strEstFisBod)){
//                                        blnRes=true;
//                                    }else{ return false;  }
//                                    
//                                    
//                                    /**/  System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION 2 >> ");
//                                    if(IngresarInventario(conMod, con_remota, intCodItm2, intCodBod2, intCodCan2, objZafTipItm, strNomItem, strEstFisBod)){
//                                        blnRes=true;
//                                    }else{ return false;  }
//                                    
//                                    
//                                    
//                                }
//                                else if(intCodCan!=intCodCan2){
//                                    
//                                    if(intCodCan < intCodCan2){
//                                        double dblDif = intCodCan-intCodCan2;
//                                        dblDif=Math.abs(dblDif);
//                                        /**/ System.out.println("** REALIZA AUMENTO EN EL INVENTARIO AL ITEM >> "+ dblDif);
//                                        // if(InvertirInventario(conMod, intCodItm, intCodBod, intCodCan, objZafTipItm)){
//                                        //     blnRes=true;
//                                        // }else{ return false;   }
//                                        if(IngresarInventario(conMod, con_remota, intCodItm2, intCodBod2, dblDif, objZafTipItm, strNomItem, strEstFisBod)){
//                                            blnRes=true;
//                                        }else{ return false;  }
//                                        
//                                    }
//                                    if(intCodCan > intCodCan2) {
//                                        double dblDif = intCodCan-intCodCan2;
//                                        dblDif=Math.abs(dblDif);
//                                        /**/System.out.println("**  REALIZA DISMINUCION EN INVENTARIO  AL ITEM >> "+ dblDif);
//                                        if(InvertirInventario(conMod, con_remota, intCodItm, intCodBod, dblDif, objZafTipItm, strEstFisBod)){
//                                            blnRes=true;
//                                        }else{ return false;   }
//                                        // if(IngresarInventario(conMod, intCodItm2, intCodBod2, intCodCan2, objZafTipItm, strNomItem)){
//                                        //    blnRes=true;
//                                        // }else{ return false;  }
//                                    }
//                                    
//                                    
//                                    
//                                }else
//                                    System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO 2 >> ");
//                                
//                                
//                            }
//                            
//                        }
//                    }}
//                blnRes=true;
//            }
//            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
//            return blnRes;
//        }
//        
        //*************************************************************************************************
        
//        public boolean actualizarReg_respaldo(){
//            boolean blnRes = false;
//            int ESTPRE=0;
//            int  Est=0;
//            Librerias.ZafUtil.ZafTipItm  objZafTipItm = new Librerias.ZafUtil.ZafTipItm(objZafParSis);
//            
//            try{
//                java.sql.Connection conMod =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                if (conMod!=null){
//                    conMod.setAutoCommit(false);
//                    
//                    
//                    //*******************************************************************************************************
//                    if(!Abrir_Conexion_Remota())
//                        return false;
//                    
//                    //*******************************************************************************************************
//                    
//                    
//                    
//                    //********nuevo
//                    ESTPRE = objAutPrg.checkCtlsCot("tbm_cabautcotven", "tbm_detautcotven" , Integer.parseInt((txtCot.getText().equals("")?"0":txtCot.getText())), conMod );
//                    //************
//                    if(ESTPRE == 2 )
//                        Est = 2;
//                    if(ESTPRE == 3 )
//                        return false;
//                    
//                    
//                    if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";
//                    //**** PARA CASO 1  SI TODO ESTA BIEN
//                    if(Est==0){
//                        
//                        if(modificaCab(conMod,STR_ESTREG)){
////                            if(AnularRegEliminados(conMod, con_remota, objZafTipItm)){
////                                if(AnularRegdeDetalle(conMod, con_remota, objZafTipItm)){
////                                    if(InvertirDet(conMod)){
////                                        if(modificaPag( conMod )){
////                                            if(cambiarEstadOdDetPag(conMod, "tbm_detmovinv",STR_ESTREG)) {
////                                                if(cambiarEstadOdDetPag(conMod, "tbm_pagmovinv",STR_ESTREG)) {
////                                                    // generaAsiento();
////                                                    int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
////                                                    java.util.Date dteFecha = new java.util.Date(Fecha[2] , Fecha[1], Fecha[0]);
////                                                    if (objAsiDia.actualizarDiario(conMod, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtDoc.getText()),txtFac.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")) {
////                                                        if(INTCODREGCEN!=0) con_remota.commit();
////                                                        conMod.commit();
////                                                        consultar3();  //***************
////                                                        blnRes = true;
////                                                    }else { if(INTCODREGCEN!=0) con_remota.rollback();
////                                                    conMod.rollback(); }
////                                                }else { if(INTCODREGCEN!=0) con_remota.rollback();
////                                                conMod.rollback(); }
////                                            }else { if(INTCODREGCEN!=0) con_remota.rollback();
////                                            conMod.rollback(); }
////                                        }else  { if(INTCODREGCEN!=0) con_remota.rollback();
////                                        conMod.rollback(); }
////                                    }else  { if(INTCODREGCEN!=0) con_remota.rollback();
////                                    conMod.rollback();  }
////                                }else  { if(INTCODREGCEN!=0) con_remota.rollback();
////                                conMod.rollback();  }
////                            }else   { if(INTCODREGCEN!=0) con_remota.rollback();
////                            conMod.rollback();   }
//                        }else  { if(INTCODREGCEN!=0) con_remota.rollback();
//                        conMod.rollback();   }
//                    }
//                    //***** FIN CASO 1    */
//                    if(INTCODREGCEN!=0) con_remota.close();
//                    conMod.close();
//                    if(INTCODREGCEN!=0) con_remota=null;
//                    conMod=null;
//                }}
//            catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes = false;  }
//            catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes = false;  }
//            
//            return blnRes;
//        }
//        
//          IngresarInventario
        
          
      
public boolean actualizarReg(){
 boolean blnRes = false;
 java.sql.Connection conMod; 
 int ESTPRE=0;
 int  Est=0;
 try{
  conMod=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
  if(conMod!=null){
    conMod.setAutoCommit(false);
     /*******************************************************************************************************/
      if(!Abrir_Conexion_Remota())
        return false;  
     /*******************************************************************************************************/
     ESTPRE = objAutPrg.checkCtlsCot("tbm_cabautcotven", "tbm_detautcotven" , Integer.parseInt((txtCot.getText().equals("")?"0":txtCot.getText())), conMod );
     if(ESTPRE == 2 ) Est = 2;
     if(ESTPRE == 3 ) return false;
                    
     if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";
     /**** PARA CASO 1  SI TODO ESTA BIEN*****/
      if(Est==0){
       if(modificaCab(conMod,STR_ESTREG)){
        if(invertirInvDet(conMod, con_remota)){   
         if(eliminaDet(conMod)){
          if(insertarDet(conMod, con_remota, Integer.parseInt(txtDoc.getText()) , strMerIngEgr, strTipIngEgr ) ){ 
           if(modificaPag( conMod )){
            if (objAsiDia.actualizarDiario(conMod, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),Integer.parseInt(txtTipDocCod.getText()),Integer.parseInt(txtDoc.getText()),txtFac.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")) {
            if(INTCODREGCEN!=0) con_remota.commit();
             conMod.commit();
             consultar3();  //***************
             blnRes = true; 
            }else  { if(INTCODREGCEN!=0) con_remota.rollback(); conMod.rollback(); } 
           }else  { if(INTCODREGCEN!=0) con_remota.rollback(); conMod.rollback(); }
          }else  { if(INTCODREGCEN!=0) con_remota.rollback(); conMod.rollback();  }
         }else  { if(INTCODREGCEN!=0) con_remota.rollback(); conMod.rollback();  }
        }else  { if(INTCODREGCEN!=0) con_remota.rollback(); conMod.rollback();   }
      }else  { if(INTCODREGCEN!=0) con_remota.rollback();     conMod.rollback();   }
    }
   /***** FIN CASO 1    ***/
   objInvItm.limpiarObjeto();     
   if(INTCODREGCEN!=0){
       con_remota.close();
       con_remota=null;
   }
   conMod.close();
   conMod=null;

 }}catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes=false;  }
   catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes=false;  }
 return blnRes;
}
        
           





public boolean invertirInvDet(java.sql.Connection conn, java.sql.Connection con_remota){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc; 
 String strSQL="";
 int intTipCli=3;
 int intCodItm =0;
 int intCodBod=0;
 int intTipStk=0;
 double dlbCanMov=0.00;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();
    intTipCli= objUltDocPrint2.ValidarCodigoCliente( txtCliCod.getText(), conn );

    objInvItm.limpiarObjeto(); 
    objInvItm.inicializaObjeto();
      
    String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
    " THEN 'S' ELSE 'N' END AS proconf  ";

    strSQL="SELECT a.co_itm, a.co_bod, abs(a.nd_can) as nd_can  " +
    strAux2+" FROM tbm_detMovInv as a " +
    " INNER JOIN tbm_inv AS b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm ) "+     
    " WHERE " +
    " a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc ="+objZafParSis.getCodigoLocal()+" " +
    " AND a.co_tipDoc = "+txtTipDocCod.getText()+" AND a.co_doc ="+txtDoc.getText()+" AND b.st_ser='N'";
    rstLoc = stmLoc.executeQuery(strSQL);
    while(rstLoc.next()){
       String strEstFisBod=rstLoc.getString("proconf");                          
       
       intCodItm=rstLoc.getInt("co_itm");
       intCodBod=rstLoc.getInt("co_bod");
       dlbCanMov=rstLoc.getDouble("nd_can");
       
       strEstFisBod=rstLoc.getString("proconf");
       objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, 1, strEstFisBod, strMerIngEgr, strTipIngEgr, -1);  
       intTipStk=1; 
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    
        if(intTipStk==1){
           if(!objInvItm.ejecutaActStock(conn, intTipCli))
             return false;
          
           if(con_remota!=null){
              if(!objInvItm.ejecutaActStock(con_remota, intTipCli))
              return false;
              if(!objInvItm.ejecutaVerificacionStock(con_remota, intTipCli ))
              return false;
           }else{    
              if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
               return false;
            } 
            
         }
         objInvItm.limpiarObjeto();  
         blnRes=true;
     }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
       catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
   return blnRes;         
 }
             
  
   
        //anularReg
        
        //************************************ MODIFICAR DATOS DE LA FACTURA
        
        
        private boolean modificaPag(java.sql.Connection conMod){
            boolean blnRes=false;
            try{
                java.sql.PreparedStatement pstPagMovInv = conMod.prepareStatement(
                " DELETE FROM tbm_pagMovInv " +
                "  where " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                " co_doc = " +  txtDoc.getText() + " and "+
                " co_tipDoc = " + txtTipDocCod.getText()) ;
                pstPagMovInv.executeUpdate();   /* Ejecutando el Delete */
                blnRes=insertDetPag(Integer.parseInt(txtDoc.getText()), conMod);
            }
            catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
            catch (Exception e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            return blnRes;
        }
        
        
        //*****************************************************************************************************
        
//        private boolean InvertirDet(java.sql.Connection conMod){
//            boolean blnRes=true;
//            try{
//                ///********** ELIMINA EL DETALLLE
//
//
//                java.sql.PreparedStatement pstDelDetMovInv = conMod.prepareStatement(
//                " DELETE FROM tbr_detMovInv " +
//                "  where co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
//                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
//                " co_doc = " +  txtDoc.getText() + " and co_tipDoc = " + txtTipDocCod.getText()) ;
//                pstDelDetMovInv.executeUpdate();   /* Ejecutando el Delete */
//
//
//                pstDelDetMovInv = conMod.prepareStatement(
//                " DELETE FROM tbm_detMovInv " +
//                "  where co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
//                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
//                " co_doc = " +  txtDoc.getText() + " and co_tipDoc = " + txtTipDocCod.getText()) ;
//                pstDelDetMovInv.executeUpdate();   /* Ejecutando el Delete */
//
//                ///********** INSERTA EL DETALLLE
//                double dlbcostouni=0.00;
//                double bldcanmov=0.00;
//                double bldcostot=0.00;
//
//                int intControl=0;
//                GLO_strArreItm="";
//
//                for(int i=0; i<tblDat.getRowCount();i++){
//                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//
//
//                        dlbcostouni = 0; //objSisCon.getCostoConsolidado2(conMod , objZafParSis.getCodigoEmpresa(), Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM )));
//                        bldcanmov = Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_CANMOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV ).toString());
//                        bldcostot = (dlbcostouni * bldcanmov);
//                        bldcanmov = (bldcanmov * -1);
//
//                        if(intControl!=0)
//                            GLO_strArreItm=GLO_strArreItm+",";
//
//                        GLO_strArreItm=GLO_strArreItm + tblDat.getValueAt(i, INT_TBL_CODITM).toString();
//
//                        intControl++;
//
//                        java.sql.PreparedStatement pstDetMovInv = conMod.prepareStatement(
//                        "INSERT INTO  tbm_detMovInv" +
//                        "(co_emp, co_loc, co_tipdoc , co_doc, co_reg, " + //CAMPOS PrimayKey
//                        " tx_codAlt,tx_codAlt2, co_itm,co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
//                        " co_bod, "            +//Codigo de la bodega de donde se saco el producto
//                        " nd_can, nd_tot, nd_cosUnigrp, nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
//                        " ,st_reg )" +
//                        "VALUES ( "  +
//                        objZafParSis.getCodigoEmpresa() + ", " +
//                        objZafParSis.getCodigoLocal()   + ", " +
//                        txtTipDocCod.getText()          + ", " +
//                        txtDoc.getText() + ", " +
//                        (i+1)       + ",'" +
//                        tblDat.getValueAt(i, INT_TBL_ITMALT) + "', '" +
//                        tblDat.getValueAt(i, INT_TBL_ITMALT2) + "', " +
//                        tblDat.getValueAt(i, INT_TBL_CODITM) + "," +
//                        tblDat.getValueAt(i, INT_TBL_CODITMACT) + ",'" +
//                        tblDat.getValueAt(i, INT_TBL_DESITM) + "','" +
//                        tblDat.getValueAt(i, INT_TBL_UNIDAD) + "'," +
//                        tblDat.getValueAt(i, INT_TBL_CODBOD) + ", " +
//                        bldcanmov + ", "+objUti.redondear(Double.parseDouble((tblDat.getValueAt(i, INT_TBL_TOTAL )==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL ).toString()),2) + ", " +
//                        //objZafInv.getCosto(Integer.parseInt(""+tblFacDet.getValueAt(i, INT_TBL_CODITM )))+ ", " +
//                        dlbcostouni + ", " + bldcostot + ", "+
//                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,  INT_TBL_PREUNI )==null)?"0":tblDat.getValueAt(i,  INT_TBL_PREUNI ).toString()), 6) + ", " +
//                        objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null  )?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString())  , 2) + ", '" +
//                        ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'  " +
//                        ",'P' )");
//
//                        pstDetMovInv.executeUpdate();
//
//
//                        if(tblDat.getValueAt(i, INT_TBL_CODDOCREL) != null){
//                            sSQL ="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel, co_emprel)"+
//                            " values("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+
//                            txtTipDocCod.getText()+","+txtDoc.getText()+"," +
//                            (i+1)+",'A',"+objZafParSis.getCodigoLocal()+","+
//                            tblDat.getValueAt(i, INT_TBL_CODTIPREL).toString()+","+tblDat.getValueAt(i, INT_TBL_CODDOCREL).toString()+", " +
//                            " "+tblDat.getValueAt(i, INT_TBL_CODREGREL).toString()+","+tblDat.getValueAt(i, INT_TBL_CODEMPREL).toString()+")";
//                            java.sql.PreparedStatement tbrMovInv = conMod.prepareStatement(sSQL);
//                            tbrMovInv.executeUpdate();
//                        }
//
//
//
//                    }}
//
//                blnRes=true;
//            }
//            catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
//            catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
//            return blnRes;
//        }
//        
        // anulaDet_Itm_OC
        //*****************************************************************************************************
        

private boolean modificaCab(java.sql.Connection conMod, String str_estreg){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    String strFecha = "#" + txtFecDoc.getFecha("/","y/m/d") + "#";
    
    stmLoc=conMod.createStatement();

    strSql="UPDATE tbm_cabMovInv SET fe_doc='"+strFecha+"', co_cli="+txtCliCod.getText()+",  "+
    " tx_numped='"+((txtPed.getText().equals(""))?"0":txtPed.getText())+"', "+
    " tx_nomCli='"+txtCliNom.getText()+"', tx_dirCli='" + txtCliDir.getText() + "', " +
    " tx_ruc='"    + txtcli_identificacion.getText() + "', " +
    " tx_telCli='" + txtcli_telefono.getText()       + "', tx_ciuCli='" + txtcli_ciudad.getText() + "', " +
    " co_com  = "  + txtVenCod.getText() + ", tx_nomven='" + txtVenNom.getText() + "', " +
    " tx_ate  = '" + txtAte.getText()    + "', tx_obs1 = '" + txaObs1.getText()   + "', " +
    " tx_obs2 = '" + txaObs2.getText()   + "', nd_sub  = "  + (dblSubtotalCot * -1 ) + ",  " +
    " nd_porIva = "+ dblPorIva           +  " , nd_tot    = "+ (dblTotalCot * -1 )  +  " , " +
    " nd_valiva    = "+ (Double.parseDouble(txtIva.getText()) * -1 )  +  " , " +
    " co_forPag = "+ txtCodForPag.getText() + ", " +
    " tx_desforpag = '"+ txtNomForPag.getText() +  "', tx_ptoPar = '" + txtPartida.getText()       + "', " +
    " tx_tra    = '" + txtTransporte.getText()    + "', " +
    " co_motTra = "  + vecTransporte.get(cboMotivoTransporte.getSelectedIndex())             + " , " +
    " tx_desMotTra='"+ cboMotivoTransporte.getItemAt(cboMotivoTransporte.getSelectedIndex()) + "', " +
    " fe_ultMod   =  CURRENT_TIMESTAMP                , " +
    " co_usrMod   = "+ objZafParSis.getCodigoUsuario() + ", st_coninv='P', st_regrep='"+str_estreg+"' ";
    if (intTipForPag2 == 4){
        strSql+=", "+  " co_tarCre= "+ objUti.codificar(txtCodTarCre.getText() ) +", " + 
                            " co_redTarCre= "+ objUti.codificar(txtCodRedTarCre.getText() ) +", " + 
                            " co_tipCre= "+ objUti.codificar(txtCodTarCre.getText() ) +", " + 
//                            " co_emiTarCre= "+ objUti.codificar(txtCodEmiTar.getText() ) +", " + 
//                            " ne_mesCre= "+ objUti.codificar(txtCodMesCre.getText() ) +", " +
                            " nd_porComTarCre= "+ objUti.codificar(txtPorComTarCre.getText() ) +", " +
                            " nd_porDesVenTarCre= "+ objUti.codificar(txtPorDesVenTarCre.getText() ) +" ";
    }        
    strSql+=" where co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
    " co_loc = " + objZafParSis.getCodigoLocal() + " and "+
    " co_doc = " +  txtDoc.getText() + " and "+
    " co_tipDoc = " + txtTipDocCod.getText();
    stmLoc.executeUpdate(strSql);
  
    stmLoc.close();
    stmLoc=null;
    blnRes=true;
}
catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
return blnRes;
}



private boolean eliminaDet(java.sql.Connection conMod ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    stmLoc=conMod.createStatement();
    strSql="DELETE FROM tbm_detMovInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
    " AND co_doc="+txtDoc.getText()+" AND co_tipDoc="+txtTipDocCod.getText();
    stmLoc.executeUpdate(strSql);
   stmLoc.close();
   stmLoc=null;
   blnRes=true;
 }catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}


                
        
/**
 * MODIFICADO EFLORESA 2012-05-11
 * NOTIFICAR A BODEGA LA ANULACION DE LA FACTURA.
 * 
 */   
private boolean anueliCab(Connection conn, String strEst){
 boolean blnRes=false;
 Statement stmLoc, stmLoc01;
 ResultSet rstLoc, rstLoc2;
 String strSql="";
 strNumOrdDes="";
 try{
  if(conn!=null){
   stmLoc=conn.createStatement();
   stmLoc01=conn.createStatement();
        
   strSql="UPDATE tbm_cabMovInv SET st_regrep='M', st_reg='"+strEst+"', fe_ultMod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+
   " co_usrMod="+objZafParSis.getCodigoUsuario()+" "+
   " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND "+
   " co_tipDoc="+txtTipDocCod.getText()+" AND co_doc="+txtDoc.getText();
   
   strSql+=" ; UPDATE tbm_cabguirem set st_reg='I', st_regrep='M'   FROM ( " +
   " select co_emp, co_loc, co_tipdoc, co_Doc FROM  tbm_detguirem as a " +
   " where a.co_emprel="+objZafParSis.getCodigoEmpresa()+" and a.co_locrel ="+objZafParSis.getCodigoLocal()+"  and  a.co_docrel="+txtDoc.getText()+"  and  a.co_tipDocrel ="+txtTipDocCod.getText()+" " +
   " group by co_emp, co_loc, co_tipdoc, co_Doc " +
   " ) as x where tbm_cabguirem.co_emp=x.co_emp and tbm_cabguirem.co_loc=x.co_loc and tbm_cabguirem.co_tipdoc=x.co_tipdoc and tbm_cabguirem.co_doc=x.co_doc ";
   
   //System.out.println("ZafVen02.anueliCab: " + strSql);
   
   stmLoc.executeUpdate(strSql);     

   strSql="SELECT ne_numcot FROM tbm_cabMovInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND "+
   " co_tipDoc="+txtTipDocCod.getText()+" AND co_doc="+txtDoc.getText()+" and ne_numdoc = 0 ";
   rstLoc=stmLoc.executeQuery(strSql);
   if(rstLoc.next()){
      strSql=" UPDATE tbm_cabcotven SET st_reg='U', st_regrep='M' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_cot="+rstLoc.getString("ne_numcot");
      stmLoc01.executeUpdate(strSql);
   }
   
   strSql=" select case when b.ne_numorddes is null then 0 else b.ne_numorddes end as ne_numorddes " +
          " from tbm_detguirem as a " +
          " inner join tbm_cabguirem as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_Doc=b.co_Doc ) " +
          " where a.co_emprel="+ objZafParSis.getCodigoEmpresa() +
          " and a.co_locrel="+ objZafParSis.getCodigoLocal() +
          " and a.co_tipDocrel="+ txtTipDocCod.getText() +
          " and a.co_docrel="+ txtDoc.getText();
   rstLoc2=stmLoc.executeQuery(strSql);
   if(rstLoc2.next()){
       if (!rstLoc2.getString("ne_numorddes").equals("0")){
           strNumOrdDes=rstLoc2.getString("ne_numorddes");
           blnIsOrdAnulada=true;
       }
   }
   
   rstLoc.close();
   rstLoc=null;
   
   stmLoc.close();
   stmLoc=null;
   stmLoc01.close();
   stmLoc01=null;
   blnRes=true;
 }}catch (SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
 return blnRes;
}
              
        
        

        
          
public boolean eliminarReg(){
 boolean blnRes=false;
 java.sql.Connection conAnu;
 String strCodOc="";
 int i=0;
 try{
    conAnu=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    if(conAnu!=null){
      conAnu.setAutoCommit(false);
      /*******************************************************************************************************/
       if(!Abrir_Conexion_Remota())
        return false;
      /*******************************************************************************************************/
        String sql = "SELECT b.ne_numdoc FROM tbr_cabmovinv AS a " +
        " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
        " WHERE " +
        " a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc="+txtTipDocCod.getText()+" AND a.co_doc="+txtDoc.getText();
        java.sql.Statement stm=conAnu.createStatement();
        java.sql.ResultSet rst = stm.executeQuery(sql);
        while(rst.next()){
            i=1;
            strCodOc +=rst.getString(1)+"\n";
        }
        rst.close(); rst=null;
        stm.close(); stm=null;
        if(i>0)
          if(( mensaje("LAS SIGUIENTES ORDENES DE COMPRA SERAN ELIMINADAS \n"+strCodOc +" \n ¿DESEA SEGUIR CON EL PROCESO DE ELIMINACIÓN ?")!=javax.swing.JOptionPane.YES_OPTION)){
              return false;
          }
      /*******************************************************/
        
        
      if(anueliCab(conAnu,"E")){
       if(objAsiDia.eliminarDiario(conAnu, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtDoc.getText()))){
        if( EliminarDet_OC(conAnu, con_remota,  objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), txtTipDocCod.getText() , txtDoc.getText() ) ){
            
             if(!isAnulada()){
                if(invertirInvDet(conAnu, con_remota)){
                 if(EliminarDet_OC_2(conAnu, con_remota,  objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), txtTipDocCod.getText() , txtDoc.getText() ) ){
                    
                   if(INTCODREGCEN!=0) con_remota.commit();
                   conAnu.commit();
                   objTooBar.setEstadoRegistro("Anulado");
                   blnRes=true;
                   objInvItm.limpiarObjeto();   
                 }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }   
                }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback();  }  
             }else{            
                 conAnu.commit();
                 blnRes=true;
             }
        }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback(); }    
       }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback();  }  
      }else{ if(INTCODREGCEN!=0) con_remota.rollback(); conAnu.rollback();  }  
          
    if(blnRes==true){
      if(i>0) MensajeInf("LAS SIGUIENTES ÓRDENES DE COMPRA FUERON ELIMINADAS \n"+ strCodOc + " ");
    }
    objInvItm.limpiarObjeto();                     
    if(INTCODREGCEN!=0){ 
        con_remota.close();
        con_remota=null;
    }
    conAnu.close();
    conAnu=null;
  
  }}catch(SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    catch(Exception Evt){   blnRes=false;   objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
  return blnRes;
}
        
        


        




//
//private boolean enviarCorEleBodEgr(java.sql.Connection conn, String strSql ) {
// boolean blnRes=false;
// java.sql.Statement stmLoc, stmLoc01;
// java.sql.ResultSet rstLoc, rstLoc01;
// String strSql2="", strMensCorEle="";
// String strCorEle="jaya_gar@hotmail.com";
// int intEstDocAnu=0;
// try{
//    stmLoc=conn.createStatement();
//    stmLoc01=conn.createStatement();
//
//    strSql2= "select corele from ( "+ strSql + " ) as x  where   corele != 'null'  group by  corele  ";
//    rstLoc=stmLoc.executeQuery(strSql2);
//    while(rstLoc.next()){
//
//            //strCorEle=rstLoc.getString("corele");
//            strMensCorEle = " LOS SIGUIENTES DOCUMENTOS SE ANULARON ";
//            intEstDocAnu=0;
//            strSql2= strSql + " where   corele = '"+rstLoc.getString("corele")+"'  ";
//            rstLoc01=stmLoc01.executeQuery(strSql2);
//            while(rstLoc01.next()){
//                 strMensCorEle +=" <br> "+rstLoc01.getString("docanu");
//                 intEstDocAnu=1;
//            }
//            rstLoc01.close();
//            rstLoc01=null;
//
//            if(intEstDocAnu==1) objInvItm.enviarCorreo(strCorEle, strMensCorEle );
//
//    }
//    rstLoc.close();
//    rstLoc=null;
//
//
//  stmLoc.close();
//  stmLoc=null;
//  stmLoc01.close();
//  stmLoc01=null;
//
// }catch(java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//  catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
// return blnRes;
//}
//

        
        
        
        

private boolean invertir_Trans(int intCodCot, java.sql.Connection con_remota, java.sql.Connection con_local, String strTipDoc, String strCodDoc ) {
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rst;
 String strSql="";
 try{
    stmLoc=con_local.createStatement();
  if(objZafParSis.getCodigoEmpresa()==4 || objZafParSis.getCodigoEmpresa()==2){
    strSql="SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel, b.ne_numdoc FROM tbr_cabmovinv AS a " +
    " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emprel AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
    " WHERE " +
    " a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.co_tipdoc="+strTipDoc+" AND a.co_doc="+strCodDoc+" and co_emprel="+objZafParSis.getCodigoEmpresaGrupo();
    rst=stmLoc.executeQuery(strSql);
    if(rst.next()){
        System.out.println("Inicia trans... ");
        Ventas.ZafVen02.ZafVen02_TRA obj1;
        obj1 = new  Ventas.ZafVen02.ZafVen02_TRA(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, intCodCot, 779,  con_remota, con_local , Integer.parseInt(strTipDoc) , Integer.parseInt(strCodDoc) ,  txtFac.getText()  );
        obj1.show();
        if(obj1.acepta()){
            blnRes=true;
        }
        obj1.dispose();
        obj1=null;
    }else blnRes=true;
   rst.close();
   rst=null; 
  }else  blnRes=true;
   
  stmLoc.close();
  stmLoc=null;
    
 }catch(java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
  catch(Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

        
          
        
            

   boolean blnEnvCorEleProCosenco=false;
   String strNumOcCons="";
        
        private boolean anulaDet_OC(java.sql.Connection conAnu, java.sql.Connection conRemoto, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
            boolean blnRes=false;
            String sql = "";
            try{
                blnEnvCorEleProCosenco=false;
                strNumOcCons="";
                sql = "SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel, b.ne_numdoc, b.co_cli FROM tbr_cabmovinv AS a " +
                " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                " WHERE " +
                " a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc+" "+
                " AND a.co_tipdocrel=2 ";
                java.sql.Statement stm=conAnu.createStatement();
                java.sql.ResultSet rst = stm.executeQuery(sql);
                
                while(rst.next()){
                    //----------------  PROCESO PARA OC
                    if(!objUltDocPrint2.Verificar_Doc_Asociado_OC_anu_Fac(conAnu, rst.getString(3),rst.getString(2), strCodTipDoc ))
                        return false;
                    if(!objUltDocPrint2.verificarsiesconfirmado(rst.getString(3),rst.getString(2) ))
                        return false;
                    if (objUltDocPrint2.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),rst.getInt(2), rst.getInt(3), rst.getString("ne_numdoc") ))
                        return false;
                    //----------------------------------
                    
                    if(!anulaDet_Itm_OC(conAnu, conRemoto,  intCodEmp, intCodLoc, rst.getString(2) , rst.getString(3) ) )
                        return false;
                    if(!anulaDet_Doc_OC(conAnu, intCodEmp, intCodLoc, rst.getString(2) , rst.getString(3) ) )
                        return false;

                    if(objZafParSis.getCodigoEmpresa()==1){
                       if(rst.getInt("co_cli")==3938){
                           strNumOcCons = rst.getString("ne_numdoc");
                           blnEnvCorEleProCosenco=true;
                       }
                    }
                    if(objZafParSis.getCodigoEmpresa()==2){
                       if(rst.getInt("co_cli")==3080){
                           strNumOcCons = rst.getString("ne_numdoc");
                           blnEnvCorEleProCosenco=true;
                       }
                    }
                    if(objZafParSis.getCodigoEmpresa()==4){
                       if(rst.getInt("co_cli")==3370){
                           strNumOcCons = rst.getString("ne_numdoc");
                           blnEnvCorEleProCosenco=true;
                       }
                    }
                    
                }
                
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
        
        
        



private boolean EliminarDet_OC(java.sql.Connection conAnu, java.sql.Connection conRemoto, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc; 
 String strSql = "";
 try{
    stmLoc=conAnu.createStatement();
    
    strSql="SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel FROM tbr_cabmovinv AS a " +
    " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
    " WHERE  a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
        //----------------  PROCESO PARA OC
        if(!objUltDocPrint2.Verificar_Doc_Asociado_OC_anu_Fac(conAnu, rstLoc.getString(3),rstLoc.getString(2), strCodTipDoc ))
            return false;
        if(!objUltDocPrint2.verificarsiesconfirmado(rstLoc.getString(3),rstLoc.getString(2) ))
            return false;
        if (objUltDocPrint2.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),rstLoc.getInt(2), rstLoc.getInt(3) ))
            return false;
        //----------------------------------
        if(!EliminarDet_Doc_OC(conAnu, intCodEmp, intCodLoc, rstLoc.getString(2) , rstLoc.getString(3) ) )
            return false;
   }
   rstLoc.close();
   rstLoc=null;
   stmLoc.close();
   stmLoc=null;
  blnRes=true;

 }catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  } 
  catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}



        
        
        
        
        private boolean EliminarDet_OC_2(java.sql.Connection conAnu, java.sql.Connection conRemoto, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
            boolean blnRes=false;
            String sql = "";
            try{
                sql = "SELECT a.co_locrel,a.co_tipdocrel,a.co_Docrel FROM tbr_cabmovinv AS a " +
                " INNER JOIN tbm_cabmovinv AS b ON(b.co_emp=a.co_emp AND b.co_loc=a.co_locrel AND b.co_tipdoc=a.co_tipdocrel AND b.co_doc=a.co_docrel)" +
                " WHERE " +
                " a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
                java.sql.Statement stm=conAnu.createStatement();
                java.sql.ResultSet rst = stm.executeQuery(sql);
                
                while(rst.next()){
                    
                    if(!anulaDet_Itm_OC(conAnu, conRemoto,  intCodEmp, intCodLoc, rst.getString(2) , rst.getString(3) ) )
                        return false;
                    
                    
                }
                
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
        
        

private boolean EliminarDet_Doc_OC(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
 boolean blnRes=false;
 String sql = "";
 try{
    sql="UPDATE tbm_cabMovInv SET " +
    " st_reg  = 'E', fe_ultMod ='"+strFecSis+"', co_usrMod = "+objZafParSis.getCodigoUsuario()+" " +
    "  WHERE co_emp = "+intCodEmp+" AND co_loc = "+intCodLoc+" AND "+
    " co_doc = "+strCodDoc+" AND co_tipDoc = "+strCodTipDoc;
    java.sql.PreparedStatement Pstm = conAnu.prepareStatement(sql);
    Pstm.executeUpdate();

    if (!objAsiDia.eliminarDiario(conAnu, intCodEmp, intCodLoc, Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)))
        return false;

    blnRes=true;
 }catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
  catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}



        
        


private boolean anulaDet_Doc_OC(java.sql.Connection conAnu, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
 boolean blnRes=false;
 String sql = "";
 try{
    sql="UPDATE tbm_cabMovInv SET " +
    " st_reg  = 'I', fe_ultMod ='"+strFecSis+"', co_usrMod = "+objZafParSis.getCodigoUsuario()+" " +
    "  WHERE co_emp = "+intCodEmp+" AND co_loc = "+intCodLoc+" AND "+
    " co_doc = "+strCodDoc+" AND co_tipDoc = "+strCodTipDoc;
    java.sql.PreparedStatement Pstm = conAnu.prepareStatement(sql);
    Pstm.executeUpdate();

    if (!objAsiDia.anularDiario(conAnu, intCodEmp, intCodLoc, Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)))
        return false;

    blnRes=true;
 }catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
  catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}



        
        
        

private boolean anulaDet_Itm_OC(java.sql.Connection conAnu, java.sql.Connection con_remota, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strMerIngEgrOc="", strTipIngEgrOc="";
 String strSql="";
 int i=0;
 int intTipCli=3;
 int intCodItm =0;
 int intCodBod=0;
 int intTipStk=0;
 double dlbCanMov=0;
 try{
    stmLoc=conAnu.createStatement();
   
    strSql="SELECT st_meringegrfisbod, tx_natdoc FROM tbm_cabtipdoc WHERE  co_emp="+intCodEmp+" "+
    " AND co_loc="+intCodLoc+" and co_tipDoc="+strCodTipDoc;
    rstLoc = stmLoc.executeQuery(strSql);
    if(rstLoc.next()){
       strMerIngEgrOc=rstLoc.getString("st_meringegrfisbod");  
       strTipIngEgrOc=rstLoc.getString("tx_natdoc");  
    }
    rstLoc.close();
    rstLoc=null;
     objInvItm.limpiarObjeto(); 
     objInvItm.inicializaObjeto();
     String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
    " THEN 'S' ELSE 'N' END AS proconf  ";

     strSql="SELECT co_itm, abs(nd_can) as nd_can, co_bod ,tx_codalt ,b.co_cli " +
    " "+strAux2+" FROM tbm_detmovinv AS a" +
    " INNER JOIN tbm_cabmovinv as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc)" +
    " WHERE  a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+"  " +
    " AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+strCodDoc;
    rstLoc = stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
      i++;
      if(i==1)
        intTipCli= objUltDocPrint2.ValidarCodigoCliente(rstLoc.getString("co_cli"),conAnu);
                    
       String strEstFisBod=rstLoc.getString("proconf");                          
       
       intCodItm=rstLoc.getInt("co_itm");
       intCodBod=rstLoc.getInt("co_bod");
       dlbCanMov=rstLoc.getDouble("nd_can");
       
       objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, strMerIngEgrOc, strTipIngEgrOc, -1);  
       intTipStk=1; 
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    
    if(intTipStk==1){
       if(!objInvItm.ejecutaActStock(conAnu, intTipCli))
         return false;
          
        if(con_remota!=null){
          if(!objInvItm.ejecutaActStock(con_remota, intTipCli))
            return false;
          if(!objInvItm.ejecutaVerificacionStock(con_remota, intTipCli ))
            return false;
        }else{    
           if(!objInvItm.ejecutaVerificacionStock(conAnu, intTipCli ))
             return false;
         } 
    }
    objInvItm.limpiarObjeto(); 
    blnRes=true;
  
  }catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
   catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}
        

        //anulaDet
        



        private int mensaje(String strMsg){
            //JOptionPane oppMsg=new JOptionPane();
            //String strTit="Sistema Zafiro";
            return JOptionPane.showConfirmDialog(jfrThis, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        
        
        
        private boolean cambiarEstadOdDetPag(java.sql.Connection conAnu, String tabla, String Estreg){
            boolean blnRes=false;
            try{
                
                String sql="UPDATE "+tabla+" SET st_regrep='"+Estreg+"' WHERE " +
                " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
                " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
                " co_doc = " +  txtDoc.getText()               + " and "+
                " co_tipDoc = " + txtTipDocCod.getText();
                
                
                java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                pstUptInvBod.executeUpdate();
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
            catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
            return blnRes;
        }
        
        
        
        
        
        private boolean anulaDet(java.sql.Connection conAnu, java.sql.Connection con_remota ){
            boolean blnRes=false;
            try{
                Librerias.ZafUtil.ZafTipItm  objZafTipItm = new Librerias.ZafUtil.ZafTipItm(objZafParSis);
                String  strActInv = "", strActInv2 = "", strOrAct= "";
                
                
                int intTipCli=3;
                intTipCli= objUltDocPrint2.ValidarCodigoCliente(txtCliCod.getText(),conAnu);
                
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                        if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("N")){
                            System.out.println("ELEMENTO NUEVO NO HACE MOVIMIENDO EN LA ANULACION >> ");
                        }else {
                            System.out.println("ELEMENTO ORIGINAL O MODIFICADO >> ");
                            
                            int intCodItm =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODORI).toString()));
                            int intCodBod =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_BODORI).toString()));
                            double intCodCan = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANORI).toString()));
                            
                            String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                        
                            
                            if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO
                                
                                   
                        String strAux="";
                        if(strEstFisBod.equals("N")){
                         if(strMerIngEgr.equals("S")){
                           if(strTipIngEgr.equals("I")){ 
                               if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
                           }
                           if(strTipIngEgr.equals("E")){
                               if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
                           } 
                         }} 

                        
                                String sql ="update  TBM_INVBOD set  " +
                                "     nd_stkact = nd_stkact + " + (intCodCan)  +
                                strAux+ " WHERE " +
                                " co_emp = " + objZafParSis.getCodigoEmpresa() + "and " +
                                " co_bod = " + intCodBod + " and " +
                                " co_itm = " + intCodItm;
                                java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                                pstUptInvBod.executeUpdate();
                                
                                if(INTCODREGCEN!=0){
                                    pstUptInvBod = con_remota.prepareStatement(sql);
                                    pstUptInvBod.executeUpdate();
                                }
                                
                                strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
                                strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
                                strOrAct  =" or ";
                                
//                                if(intTipCli==1){
//                                    if(!objSisCon.actualizarInventario_2_solo_OC(conAnu, con_remota,
//                                    objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),
//                                    intCodItm, Math.abs(intCodCan), intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
//                                        if(INTCODREGCEN!=0) con_remota.rollback();
//                                        conAnu.rollback();
//                                        return false;
//                                    }}
                                
                            } //********  FIN DE ITEM SERVICIO
                            
                        }
                    }}
                
                //********* BUSCAR ELEMENTO ELIMADOS EN LA TABLA
                java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                if (arlAux!=null){
                    for (int i=0;i<arlAux.size();i++){
                        int intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
                        int intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
                        double intCodCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
                        String strEstFisBod= objUti.getStringValueAt(arlAux, i, INT_ARR_IEBODFIS);
                        
                        if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO
                            
                            
                        String strAux="";
                        if(strEstFisBod.equals("N")){
                         if(strMerIngEgr.equals("S")){
                           if(strTipIngEgr.equals("I")){ 
                               if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
                           }
                           if(strTipIngEgr.equals("E")){
                               if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
                               if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
                           } 
                         }} 

                
                            String sql="update  TBM_INVBOD set  " +
                            "     nd_stkact = nd_stkact + " + (intCodCan)  +
                            strAux+ " WHERE " +
                            " co_emp = " + objZafParSis.getCodigoEmpresa() + "and " +
                            " co_bod = " + intCodBod + " and " +
                            " co_itm = " + intCodItm;
                            
                            java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(sql);
                            pstUptInvBod.executeUpdate();
                            if(INTCODREGCEN!=0) {
                                pstUptInvBod = con_remota.prepareStatement(sql);
                                pstUptInvBod.executeUpdate();
                            }
                            
                            strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
                            strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
                            strOrAct  =" or ";
                            
//                            if(intTipCli==1){
//                                if(!objSisCon.actualizarInventario_2_solo_OC(conAnu, con_remota,
//                                objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),
//                                intCodItm, Math.abs(intCodCan), intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
//                                    if(INTCODREGCEN!=0) con_remota.rollback();
//                                    conAnu.rollback();
//                                    return false;
//                                }}
                            
                            
                        } //********  FIN DE ITEM SERVICIO
                        //System.out.println("ELEMENTO ELIMINADO CODITEM >> "+intCodItm);
                        // System.out.println("ELEMENTO ELIMINADO CODIBOD >> "+intCodBod);
                        // System.out.println("ELEMENTO ELIMINADO CODCAN >> "+intCodCan);
                    }
                }
                
                
                if(!strActInv.equals("")){
                    String strUpdate = " UPDATE tbm_inv " +
                    " SET st_regrep='M', nd_stkAct=b1.nd_stkAct " +
                    " FROM  " +
                    "( " +
                    " SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct " +
                    " FROM tbm_invBod AS a2 " +
                    " WHERE a2.co_emp=1     " +
                    " and ( " + strActInv2 +" ) " +
                    " GROUP BY a2.co_emp, a2.co_itm " +
                    " ORDER BY a2.co_itm " +
                    ") AS b1 " +
                    " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " +
                    " AND ( " + strActInv +" ) " +
                    " AND tbm_inv.co_emp=1";
                    java.sql.PreparedStatement pstActStkInv = conAnu.prepareStatement(strUpdate);
                    pstActStkInv.executeUpdate();
                    if(INTCODREGCEN!=0){
                        pstActStkInv = con_remota.prepareStatement(strUpdate);
                        pstActStkInv.executeUpdate();
                    }       
                }
                blnRes=true;
            }
            catch (java.sql.SQLException e) {  
                blnRes=false;  
                objUti.mostrarMsgErr_F1(this, e);  
            }
            catch (Exception e) { 
                blnRes=false; 
                objUti.mostrarMsgErr_F1(this, e); 
            }
            return blnRes;
        }
        
          
        //******************************************************************************************************
        @Override
        public boolean vistaPreliminar()
        {
            cargarReporte(1);
            return true;
        }


        private void cargarReporte(int intTipo)
        {
            if(objZafParSis.getCodigoUsuario()==1) // Jota 21/Enero/2015
            { 
                if (objThrGUI==null)
                 {
                     objThrGUI=new ZafThreadGUI();
                     objThrGUI.setIndFunEje(intTipo);
                     objThrGUI.start();
                 }
            }
            else
            {
                //if(validarAutFacEleSRI()){   // Jota 21/Enero/2015
                     if (objThrGUI==null)
                     {
                         objThrGUI=new ZafThreadGUI();
                         objThrGUI.setIndFunEje(intTipo);
                         objThrGUI.start();
                     }   
                //}
            }
        }

        @Override
        public boolean imprimir()
        {
            cargarReporte(0);
            return true;
        }
        
        //******************************************************************************************************
        
        
        @Override
        public void clickImprimir(){
            
        }
        
        @Override
        public void clickVisPreliminar(){
            
        }
        
        @Override
        public void clickCancelar(){
            //System.gc();
            Runtime.getRuntime().gc();
        }
        
        public void cierraConnections(){
            try{
                if(conCab !=null)
                    if(!conCab.isClosed()){
                        conCab.close();
                        conCab = null;
                        rstCab = null;
                        stmCab = null;
                    }
            }
            
            catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
            catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        }
        
        @Override
        public boolean beforeAceptar() {
            return true;
        }
        
        @Override
        public boolean beforeAnular() {
            return true;
        }
        
        @Override
        public boolean beforeCancelar() {
            return true;
        }
        
        @Override
        public boolean beforeConsultar() {
            
            objVenConCli.dispose();
            
            
                    
            return true;
        }
        
        @Override
        public boolean beforeEliminar() {
            return true;
        }
        
        @Override
        public boolean beforeImprimir() {
            return true;
        }
        
        @Override
        public boolean beforeInsertar() {            
            return true;
        }
        
        @Override
        public boolean beforeModificar() {
            return true;
        }
        
        @Override
        public boolean beforeVistaPreliminar() {
            return true;
        }
        
    }
    
    
    
    private String FilSql2() {
        String sqlFiltro = "";
        //Agregando filtro por codigo de Tipo de Documento
        if(!txtTipDocCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc = "+txtTipDocCod.getText()+" ";
        
        //Agregando filtro por Numero de Documento
        if(!txtDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_doc = "+txtDoc.getText()+" ";
        
        if(!txtFac.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc = "+txtFac.getText()+" ";
        
        //Agregando filtro por Fecha
        if(txtFecDoc.isFecha()){
            int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and DocCab.fe_doc = '" +  strFecSql + "'";
        }
        //Agregando filtro por Codigo de Cliente
        if(!txtCliCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_cli = "+txtCliCod.getText()+" ";
        
        sqlFiltro = sqlFiltro +  " and " +
        " menu.co_mnu = " + intCodMnu                       + " and " +
        " menu.co_tipdoc = doc.co_tipdoc and DocCab.co_tipdoc = doc.co_tipdoc ";
        return sqlFiltro ;
    }
    
    
    
    
    private String FilSql() {
        String sqlFiltro = "";
        //Agregando filtro por codigo de Tipo de Documento
        if(!txtTipDocCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc =  "+txtTipDocCod.getText()+" ";
        
        //Agregando filtro por Numero de Documento
        if(!txtDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_doc = "+txtDoc.getText()+" ";
        
        if(!txtFac.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc ="+txtFac.getText()+" ";
        
        //Agregando filtro por Fecha
        if(txtFecDoc.isFecha()){
            int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and DocCab.fe_doc = '" +  strFecSql + "'";
        }
        //Agregando filtro por Codigo de Cliente
        if(!txtCliCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_cli = " + txtCliCod.getText() + " ";
        
        //Agregando filtro por Codigo de Vendedor
        if(!txtVenCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_com = "+ txtVenCod.getText() +" ";
        
        //Agregando filtro por Codigo de Vendedor
        if(!txtAte.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.tx_ate LIKE '" + txtAte.getText() + "'";
        
        //Agregando filtro por Nombre del cliente
        if(!txtVenNom.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.tx_nomVen LIKE '" + txtVenNom.getText() + "'";
        
        sqlFiltro = sqlFiltro +  " and " +
        " menu.co_mnu = " + intCodMnu                       + " and " +
        " menu.co_tipdoc = doc.co_tipdoc and DocCab.co_tipdoc = doc.co_tipdoc ";
        return sqlFiltro ;
    }
    
    
      private String FilSqlBus() {
        String sqlFiltro = "";
        //Agregando filtro por codigo de Tipo de Documento
        /*if(!txtTipDocCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_tipdoc =  "+txtTipDocCod.getText()+" ";*/

        //Agregando filtro por Numero de Documento
        if(!txtDoc.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_doc = "+txtDoc.getText()+" ";

        if(!txtFac.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.ne_numDoc ="+txtFac.getText()+" ";

        //Agregando filtro por Fecha
        if(txtFecDoc.isFecha()){
            int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
            String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            sqlFiltro = sqlFiltro + " and DocCab.fe_doc = '" +  strFecSql + "'";
        }
        //Agregando filtro por Codigo de Cliente
        if(!txtCliCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_cli = " + txtCliCod.getText() + " ";

        //Agregando filtro por Codigo de Vendedor
        if(!txtVenCod.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.co_com = "+ txtVenCod.getText() +" ";

        //Agregando filtro por Codigo de Vendedor
        if(!txtAte.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.tx_ate LIKE '" + txtAte.getText() + "'";

        //Agregando filtro por Nombre del cliente
        /*if(!txtVenNom.getText().equals(""))
            sqlFiltro = sqlFiltro + " and DocCab.tx_nomVen LIKE '" + txtVenNom.getText() + "'";*/

        return sqlFiltro ;
    }    
    
    private void MensajeValidaCampo(String strNomCampo){
        //JOptionPane obj =new JOptionPane();
        String strMsg;
        //String strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
     
    public boolean consultar2(String strCodeemp, String strCodLoc, String strCodtipdoc, String strCoddoc) {
        return _consultar2(FilSql(),  strCodeemp,  strCodLoc,  strCodtipdoc,  strCoddoc );
    }   
    
    private boolean _consultar2(String strFil, String strCodeemp, String strCodLoc, String strCodtipdoc, String strCoddoc)
    {
        strFiltro= strFil;
        try
        {
            if(conCab == null)
                conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conCab.isClosed())
                conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            
            if (conCab!=null)
            {
                //stmDoc=conDoc.createStatement(); stmCab
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
                sSQL= "SELECT DocCab.co_emp, DocCab.co_loc, DocCab.co_doc, DocCab.co_tipdoc, DocCab.co_forPag, DocCab.st_reg, "
                    + "       forpag.tx_des, forpag.ne_tipforpag "
                    + " FROM tbm_cabMovInv as DocCab "
                    + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=DocCab.co_emp and doc.co_loc=DocCab.co_loc and doc.co_tipdoc=DocCab.co_tipdoc ) "
                    + " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag )  "
                    + " WHERE  DocCab.co_emp = "+strCodeemp
                    + " AND DocCab.co_loc = " +strCodLoc
                    + " AND DocCab.co_tipdoc="+strCodtipdoc
                    + " AND DocCab.co_doc="+strCoddoc
                    + " AND DocCab.st_reg not in ('E') "
                    + " ORDER BY DocCab.ne_numdoc ";              

                System.out.println("CONSULTA-FACTURA: "+ sSQL);
                rstCab=stmCab.executeQuery(sSQL);          
                
                
                if(rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    _getCargarReg();
                    //refrescaDatos();
                    butGuiRem.setEnabled(false);
                }
                else
                {
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                    // cierraConnections();
                    return false;
                }
            }
        }
        catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;  }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
        
        
        //System.gc();
        Runtime.getRuntime().gc();
        return true;
        
    }

/**
 * MODIFICADO EFLORESA 2012-05-15
 * CONSULTA DE FACTURAS DE VENTAS.
 */    
private boolean _consultar(String strFil){
  strFiltro= strFil;
  try{
   if(conCab == null)
      conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
      if(conCab.isClosed())
        conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(conCab!=null){
               
          stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
        /*sSQL= "SELECT DocCab.co_emp, DocCab.co_loc, DocCab.co_doc, DocCab.co_tipdoc,DocCab.co_forPag,DocCab.st_reg "
        + "  ,forpag.tx_des  "
        +"  FROM tbm_cabMovInv as DocCab "
        + " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=DocCab.co_emp and doc.co_loc=DocCab.co_loc and doc.co_tipdoc=DocCab.co_tipdoc ) "
        + " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag )  "
        + " Where  DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() +
        "  and DocCab.co_loc = " + objZafParSis.getCodigoLocal() +
        "  and DocCab.st_reg not in ('E') "+
        " "+ strFiltro + "  ORDER BY DocCab.ne_numdoc";*/
                
        /*sSQL= " select DocCab.co_emp, DocCab.co_loc, DocCab.co_doc, DocCab.co_tipdoc, DocCab.co_forPag, DocCab.st_reg, forpag.tx_des, DocCab.fe_doc, DocCab.co_cli, DocCab.ne_numdoc " +
              " from tbm_cabMovInv as DocCab " + 
              " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=DocCab.co_emp and doc.co_loc=DocCab.co_loc and doc.co_tipdoc=DocCab.co_tipdoc ) " + 
              " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag ) " + 
              " where DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + 
              " and DocCab.co_loc = " + objZafParSis.getCodigoLocal() + 
              " and DocCab.st_reg not in ('E') " +
              " "+ strFiltro + 
              " order by DocCab.fe_doc, DocCab.ne_numdoc";*/
                
        /*sSQL= " select DocCab.co_emp, DocCab.co_loc, DocCab.co_doc, DocCab.co_tipdoc, DocCab.co_forPag, DocCab.st_reg, forpag.tx_des, DocCab.fe_doc, DocCab.co_cli, DocCab.ne_numdoc " +
              " from tbm_cabMovInv as DocCab " + 
              " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=DocCab.co_emp and doc.co_loc=DocCab.co_loc and doc.co_tipdoc=DocCab.co_tipdoc ) " + 
              " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag ) " + 
              " where DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + 
              " and DocCab.co_loc = " + objZafParSis.getCodigoLocal() + 
              " and DocCab.co_tipdoc =  "+txtTipDocCod.getText()+" " +
              " and DocCab.st_reg not in ('E') " ;*/
        
        sSQL= " select DocCab.co_emp, DocCab.co_loc, DocCab.co_doc, DocCab.co_tipdoc, DocCab.co_forPag, " +
              " DocCab.st_reg, forpag.tx_des, DocCab.fe_doc, DocCab.co_cli, DocCab.ne_numdoc, forpag.ne_tipforpag "+
             // " DocCab.co_tarCre, a1.tx_deslar as nomTarCre, DocCab.co_redTarCre, a2.tx_deslar as nomRedTarCre, DocCab.co_tipCre, a3.tx_deslar as nomTipCre, " +
              //" a3.tx_tipcre, a3.ne_mesgra, a3.nd_pordesven, DocCab.co_emiTarCre, a4.tx_deslar as nomEmiTar, DocCab.ne_mesCre " +
              " from tbm_cabMovInv as DocCab " + 
              " INNER JOIN tbm_cabtipdoc as doc ON (doc.co_emp=DocCab.co_emp and doc.co_loc=DocCab.co_loc and doc.co_tipdoc=DocCab.co_tipdoc ) " + 
              " LEFT JOIN tbm_cabForPag as forpag on (forpag.co_emp=DocCab.co_emp and forpag.co_forpag = DocCab.co_forPag ) " + 
              //" left outer join tbm_tarCre as a1 on (a1.co_tarcre=DocCab.co_tarCre ) " +
              //" left outer join tbm_redTarCre as a2 on (a2.co_redtarcre=DocCab.co_redTarCre ) " +   
              //" left outer join tbm_tipCreRedTarCre as a3 on (a3.co_emp=DocCab.co_emp and a3.co_redtarcre=DocCab.co_redTarCre and a3.co_tipcre=DocCab.co_tipCre ) " +
              //" left outer join tbm_var as a4 on (a4.co_reg=DocCab.co_emiTarCre) " +
              " where DocCab.co_emp = " + objZafParSis.getCodigoEmpresa() + 
              " and DocCab.co_loc = " + objZafParSis.getCodigoLocal() + 
              " and DocCab.co_tipdoc =  "+txtTipDocCod.getText()+" " +
              " and DocCab.st_reg not in ('E') " ;
        
        if (!strFiltro.equals("")) 
            sSQL= sSQL+strFiltro + " order by DocCab.fe_doc, DocCab.ne_numdoc ";
        else 
            sSQL= sSQL + " order by DocCab.ne_numdoc "; 
        
        System.out.println("ZafVen02._consultar: "+ sSQL);
                rstCab=stmCab.executeQuery(sSQL);                
                
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    cargarReg();
                    //refrescaDatos();
                    butGuiRem.setEnabled(false);
                    
                }else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                    // cierraConnections();
                    return false;
                }
            }
        }catch(SQLException Evt) { 
            objUti.mostrarMsgErr_F1(jfrThis, Evt); return false;  
        }catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  
        } 
  
        //System.gc();
        Runtime.getRuntime().gc();
        return true;
        
    }
    
    private void cargarRegistroInsert(){
        try{//odbc,usuario,password
             conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCab!=null){
                
                //stmCot=conCot.createStatement();
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                
                                 /*
                                  * Agregando el Sql de Consulta para la cotizacion
                                  */
                sSQL= "SELECT CotCab.co_emp,CotCab.co_loc,CotCab.co_cot,CotCab.st_reg " + // Campo para saber si esta anulado o no
                " FROM tbm_cabCotven as CotCab " +
                " Where CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                "  and CotCab.co_loc = "  + objZafParSis.getCodigoLocal() + // Consultando en el local en el ke se esta trabajando
                "  and CotCab.co_cot = "  + txtCot.getText();
                
                //System.out.println(sSQL);
                rstCab=stmCab.executeQuery(sSQL);
                
                if(rstCab.next()){
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                }
                else{
                    objTooBar.setMenSis("0 Registros encontrados");
                    clnTextos();
                }
            }
        }catch(SQLException Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }catch(Exception Evt){
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
    }
        
    private boolean validaCampos(){
      try{
          
        if(txtCliCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Cliente");
            txtCliCod.requestFocus();
            return false;
        }
        if(!txtFecDoc.isFecha()){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Fecha de Cotización");
            txtFecDoc.requestFocus();
            return false;
        }
        if(txtVenCod.getText().equals("") ){
            tabCotCom.setSelectedIndex(0);
            MensajeValidaCampo("Vendedor");
            txtVenCod.requestFocus();
            return false;
        }
               /*
                * VAlidando los datos del Pago
                */
       if((txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
            tabCotCom.setSelectedIndex(1);
            MensajeValidaCampo("Forma de pago");

            return false;
        }
        

           if(tblPag.getRowCount() <= 0 ){
             tabCotCom.setSelectedIndex(1);
             //JOptionPane obj = new JOptionPane();
             String strMsg;
             //String strTit="Mensaje del sistema Zafiro";
             strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
             JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
             return false;
         }

        
        
        for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
            
            if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                
                double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                
                if(dblCan <= 0.00 ){
                    //JOptionPane obj = new JOptionPane();
                    String strMsg;
                    //strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                    return false;
                }
                
                
                
                double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PREUNI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PREUNI).toString());
                if(dblCos <= 0.00 ){
                    //JOptionPane obj = new JOptionPane();
                    String strMsg;
                    //strTit="Mensaje del sistema Zafiro";
                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                    JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
                    tblDat.repaint();
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_PREUNI);
                    return false;
                }
                
                
            }
        }
        
        
        
        
        if(!objAsiDia.isDiarioCuadrado()){
            //JOptionPane obj = new JOptionPane();
            String strMsg;
            //strTit="Mensaje del sistema Zafiro";
            strMsg="EL diario no esta cuadrado, no se puede grabar";
            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        
        
        // System.out.println(">>>> "+ cboMotivoTransporte.getSelectedIndex() );
        if(cboMotivoTransporte.getSelectedIndex()==-1){
            //JOptionPane obj = new JOptionPane();
            String strMsg;
            //strTit="Mensaje del sistema Zafiro";
            strMsg="Seleccione el Motivo de Transporte.";
            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            tabCotCom.setSelectedIndex(2);
            return false;
        }
        
        
        
                /*
                 * VAlidando que la suma de los montos de pagos sean igual a total de cotizacion
                 */
        double dblTotalMonto=0;
        for(int rowIdx = 0; rowIdx<tblPag.getRowCount() ;rowIdx++){
            double dblSubMonto = Double.parseDouble((tblPag.getValueAt(rowIdx, 4)==null)?"0":tblPag.getValueAt(rowIdx, 4).toString());
            dblTotalMonto = dblTotalMonto + dblSubMonto ;
        }
        
        if( objUti.redondeo(dblTotalMonto,intNumDec)!= objUti.redondeo(Double.parseDouble(txtTot.getText()), intNumDec) ){
            tabCotCom.setSelectedIndex(1);
            //JOptionPane obj = new JOptionPane();
            String strMsg;
            //strTit="Mensaje del sistema Zafiro";
            strMsg="La suma de los montos a pagar es mayor al total de la CotizaciÃ³n.\nCorrija y vuelva a intentarlo.";
            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
            
            return false;
        }
        String strSql = "select * from tbm_cli where " +
        "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
        "co_cli = " + Integer.parseInt(txtCliCod.getText());
       
            java.sql.Connection conPorDes = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conPorDes!=null){
                
                Statement stmCliDesc = conPorDes.createStatement();
                ResultSet rstCliDesc = stmCliDesc.executeQuery(strSql);
                if(rstCliDesc.next()){
                    double dblPorDesCli  = rstCliDesc.getDouble("nd_maxDes");
                    
                    //*************************************
                    
                    int Estado=0;
                    
                    //*********************************
                }
                rstCliDesc.close();
                stmCliDesc.close();
                conPorDes.close();
                rstCliDesc = null;
                stmCliDesc = null;
                conPorDes = null;                
            }

            /** CAMPOS TARJETA DE CREDITO. */
            if (intTipForPag2 == 4){                    
                if (txtCodTarCre.getText().equals("")){
                    tabCotCom.setSelectedIndex(1);
                    MensajeValidaCampo("Tipo de Credito");            
                    return false;
                }
            
            }            
            
            
        }catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  
        }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   return false;  
        }
        return true;
    }
    
    
    private double getStkAct(String int_co_itm){
        double dblStkAct = 0;
        try{
            java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conUni!=null){
                java.sql.Statement stmUni = conUni.createStatement();
                String sSQL= "SELECT nd_stkact " +
                " from tbm_inv as inv " +
                " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                "       inv.co_itm = " + int_co_itm ;
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next()){
                    dblStkAct = rstUni.getDouble("nd_stkact");
                }
                rstUni.close();
                stmUni.close();
                conUni.close();
                rstUni = null;
                stmUni = null;
                conUni = null;
            }
        }
        catch(java.sql.SQLException Evt) {  return dblStkAct;  }
        catch(Exception Evt) { return dblStkAct;  }
        return dblStkAct;
    }
    
    
    
    private double getPreUni(String int_co_itm){
        double dblPreUni = 0;
        try{
            java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conUni!=null){
                java.sql.Statement stmUni = conUni.createStatement();
                String sSQL= "SELECT nd_prevta1 " +
                " from tbm_inv as inv " +
                " where inv.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                "       inv.co_itm = " + int_co_itm ;
                java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                if(rstUni.next()){
                    dblPreUni = rstUni.getDouble("nd_prevta1");
                }
                rstUni.close();
                stmUni.close();
                conUni.close();
                rstUni = null;
                stmUni = null;
                conUni = null;
            }
        }
        catch(java.sql.SQLException Evt) {  return dblPreUni; }
        catch(Exception Evt) {  return dblPreUni;  }
        return dblPreUni;
    }
    
    
    
    /*
     *  Listener para hacer que se calcule el pago despues segun el total de cotizacion
     */
    private boolean isAnulada() {
        boolean blnRes = false;
        java.sql.Connection conTmp ;;
        java.sql.Statement stmTmp;
        String strSQL = "";
        try {
            conTmp = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conTmp!=null) {
                stmTmp = conTmp.createStatement();
                strSQL = "";
                strSQL = " Select count(*) from tbm_cabmovinv ";
                strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and co_doc = " + txtDoc.getText();
                strSQL += " and co_tipdoc = " + txtTipDocCod.getText() + " and st_reg = 'I' " ;
                if (objUti.getNumeroRegistro(this,objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0){
                    blnRes = true;
                }else
                    blnRes = false;
                stmTmp.close();
                conTmp.close();
            }
        }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);   blnRes = true;  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   blnRes = true;  }
        return blnRes;
    }



private boolean isAnulada(java.sql.Connection conn, String strMesj) {
 boolean blnRes = true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL = "";
 try {
 if(conn!=null) {
    stmLoc = conn.createStatement();

    strSQL = " Select co_doc from tbm_cabmovinv ";
    strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and co_doc = " + txtDoc.getText();
    strSQL += " and co_tipdoc = " + txtTipDocCod.getText() + " and st_reg = 'I' " ;
    rstLoc=stmLoc.executeQuery(strSQL);
    if(rstLoc.next()){
       MensajeInf("DOCUMENTO ANULADO NO ES POSIBLE "+strMesj+"");
       blnRes = false;
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
}}catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);   blnRes = false;  }
 catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   blnRes = false;  }
return blnRes;
}




    private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        //String strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(jfrThis,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    
    public class ActLisForPag implements java.awt.event.ActionListener{
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            // calculaPagos();
        }
    }
    
    
    /**
     * Metodos para generar el Asiento Contable
     * iNteractuan directamente con generaAsiento
     */
    private java.util.Vector vecFila(int intCodCtaCtb){
        java.util.Vector vecRowAsiento = new java.util.Vector();
        /**Linea*/
        vecRowAsiento.add(null);
        
        /** Codigo de cuenta */
        vecRowAsiento.add(intCodCtaCtb+"");
        
        /** Codigo de ALterno de cuenta */
        vecRowAsiento.add(objCtaCta_Dat.getCodAltCta(intCodCtaCtb));
        
        /** Boton buscar cuenta */
        vecRowAsiento.add(null);
        
        /** NOmbre de cuenta */
        vecRowAsiento.add(objCtaCta_Dat.getNomCta(intCodCtaCtb));
        
        /** Valor que va en el debe */
        vecRowAsiento.add("0");
        
        /** Valor que va en el Haber  */
        vecRowAsiento.add("0");
        
        /** Referencia */
        vecRowAsiento.add(null);
        
        return vecRowAsiento;
        
    }
    
    
    
    
    private java.util.Vector vecFila2(int intCodCtaCtb,String codcta,String Nomcta){
        java.util.Vector vecRowAsiento = new java.util.Vector();
        /**Linea*/
        vecRowAsiento.add(null);
        
        /** Codigo de cuenta */
        vecRowAsiento.add(intCodCtaCtb+"");
        
        /** Codigo de ALterno de cuenta */
        vecRowAsiento.add(codcta);  //objCtaCta_Dat.getCodAltCta(intCodCtaCtb));
        
        /** Boton buscar cuenta */
        vecRowAsiento.add(null);
        
        /** NOmbre de cuenta */
        vecRowAsiento.add(Nomcta);  //objCtaCta_Dat.getNomCta(intCodCtaCtb));
        
        /** Valor que va en el debe */
        vecRowAsiento.add("0");
        
        /** Valor que va en el Haber  */
        vecRowAsiento.add("0");
        
        /** Referencia */
        vecRowAsiento.add(null);

         vecRowAsiento.add(null);
        
        return vecRowAsiento;
        
    }
    
    /**
     * Metodo que genera el asiento Contable
     */
    public void generaAsiento(){
        
        
        objAsiDia.inicializar();
        java.util.Vector vecAsiento = new java.util.Vector();
        
        double dblValTotOtr = getTotalItemOtros();
        
        if(dblTotalCot>0){
            java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDeb(),objCtaCta_Dat.getCtaCodDeb(),objCtaCta_Dat.getCtaNomDeb());
            vecRowAsientoDeb.setElementAt(new Double(objUti.redondeo(dblTotalCot,6)), 5);
            vecAsiento.add(vecRowAsientoDeb);
        }
        /*
         * Agregando la cuenta de descuento
         */
        
        double dblValTotDes = getTotalDescuento();
        
        //  System.out.println(">>>>>   SOLO DESCUENTO >>>>>>>>>>>  "+ dblValTotDes );
        
        if( dblValTotDes > 0){
            
            java.util.Vector vecRowAsientoDeb = vecFila2(objCtaCta_Dat.getCtaDescVentas(),objCtaCta_Dat.getCtaCodDes(),objCtaCta_Dat.getCtaNomDes());
            vecRowAsientoDeb.setElementAt(new Double(dblValTotDes), 5);
            vecAsiento.add(vecRowAsientoDeb);
        }
        
        /*
         * Agregando la cuenta de Ventas
         */
        if( (dblValTotDes+dblSubtotalCot-dblValTotOtr) > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaHab(),objCtaCta_Dat.getCtaCodHab(),objCtaCta_Dat.getCtaNomHab());
            vecRowAsientoHab.setElementAt(new Double(objUti.redondeo(dblValTotDes+objUti.redondeo(dblSubtotalCot,6)-dblValTotOtr,6)), 6);
            vecAsiento.add(vecRowAsientoHab);
        }
        /*
         * Agregando la cuenta Iva en Ventas
         */
        if( dblIvaCot > 0){
            java.util.Vector vecRowAsientoHab = vecFila2(objCtaCta_Dat.getCtaIvaVentas(),objCtaCta_Dat.getCtaCodIvaVen(),objCtaCta_Dat.getCtaNomIvaVen());
            vecRowAsientoHab.setElementAt(new Double(objUti.redondeo(dblIvaCot,6)), 6);
            vecAsiento.add(vecRowAsientoHab);
        }
        
        for (int x=0; x<strarritmotr.length; x++) {
            if ( strarritmotr[x][0] != null ) {
                java.util.Vector vecRowAsientoHab = vecFila2(Integer.parseInt(strarritmotr[x][0]),strarritmotr[x][2],strarritmotr[x][3]); 
                vecRowAsientoHab.setElementAt(new Double(objUti.redondear(strarritmotr[x][1],2)), 6);
                vecAsiento.add(vecRowAsientoHab);
            }
        }
        
        objAsiDia.setDetalleDiario(vecAsiento);
    }
    
    
    
    
    
    
    public double getTotalDescuento(){
        double dblTotalDescuento=0;
        for(int filaActual=0; filaActual< tblDat.getRowCount(); filaActual++){
            if(tblDat.getValueAt(filaActual, INT_TBL_CODITM)!=null){
                double dblCan    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_CANMOV).toString()))),6),
                dblPre    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PREUNI).toString()))),6),
                dblPorDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(filaActual, INT_TBL_PORDES )==null)?"0":(tblDat.getValueAt(filaActual, INT_TBL_PORDES).toString()))),2),
                dblValDes =  objUti.redondeo(((dblCan * dblPre)==0)?0:(objUti.redondeo((dblCan * dblPre)* dblPorDes, 6)  / 100),6);         
                //     System.out.println(">>>>>   SOLO DESCUENTO 1 >>>>>>>>>>>  "+ dblTotalDescuento + " >> "+ dblValDes +" >> "+ objUti.redondeo(dblValDes,6) );                
                // dblTotalDescuento =  objUti.redondear(dblTotalDescuento + objUti.redondeo(dblValDes,6),2);
                dblTotalDescuento =  dblTotalDescuento + dblValDes;             
            } }
        
        // System.out.println(">>>>>   SOLO DESCUENTO >>>>>>>>>>>  "+ dblTotalDescuento );
        
        dblTotalDescuento =  objUti.redondear(objUti.redondear(dblTotalDescuento,4),2);
        
        //  System.out.println(">>>>>   SOLO DESCUENTO >>>>>>>>>>>  "+ dblTotalDescuento );
        
        
        
        
        return dblTotalDescuento;
    }
    
    public double getTotalItemOtros() {
        
        double dblSub = 0, dblTotalItemOtros = 0;
        double subtot = 0.00;
        strarritmotr = new String[tblDat.getRowCount()][4];
        for (int filaActual=0; filaActual< tblDat.getRowCount(); filaActual++) {
            if ((tblDat.getValueAt(filaActual, INT_TBL_CODITM)!=null) && (tblDat.getValueAt(filaActual, INT_TBL_ITMSER).equals("O")) && (tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR)!=null)) {
                dblSub = ((tblDat.getValueAt(filaActual, INT_TBL_TOTAL)==null||tblDat.getValueAt(filaActual, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(filaActual, INT_TBL_TOTAL).toString()));
                dblTotalItemOtros = dblTotalItemOtros + dblSub;
                
                for (int x=0; x<strarritmotr.length; x++){
                    if ( strarritmotr[x][0] != null ) {
                        if ( strarritmotr[x][0].equals(tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR))) {
                            subtot = dblSub + Double.parseDouble(strarritmotr[x][1]);
                            strarritmotr[x][1] = Double.toString(subtot);
                            break;                            
                        }
                    }else{                            
                        strarritmotr[x][0] = tblDat.getValueAt(filaActual, INT_TBL_CODCTAEGR).toString();
                        subtot = dblSub;
                        strarritmotr[x][1] = Double.toString(subtot);
                        strarritmotr[x][2] = objCtaCta_Dat.getCodAltCta(Integer.parseInt(strarritmotr[x][0]));
                        strarritmotr[x][3] = objCtaCta_Dat.getNomCta(Integer.parseInt(strarritmotr[x][0]));

                        break;                            
                    }
                }
                
            }
        }
        return dblTotalItemOtros;
    }

    public double getSubtotalSinTrans(){
        
        double dblSub = 0, dblSubCot = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        for (int i=0;i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                if(!(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("T"))){
                    
                    dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
                    dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                    
                }}}
        
        dblSubCot = objUti.redondear(dblSub ,intNumDec);
        return dblSubCot;
    }
    
    
    
    public double getSubtotalTrans(){
        
        double dblSub = 0, dblSubCot = 0, dblDes = 0, dblTmp=0, dblSub2=0;
        for (int i=0;i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("T")){
                    
                    dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));
                    dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
                    
                }}}
        
        dblSubCot = objUti.redondear(dblSub ,intNumDec);
        return dblSubCot;
    }
    
    
Vector vecDataTblPag;
double dblRetFueGlo=0, dblRetIvaGlo=0;

public void calculaPagos(){
   int intVal =  intCanArr[0];
   int intsizearre = intarreglodia.length;
   intVal= intsizearre - (intsizearre-intVal);
   //java.sql.Statement stm;
   java.sql.ResultSet rst;
   double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
   double dblPago=0.00;
   double dblPagos=0.00;
   double dblRete=0;
   int i=0;
   try{
      java.util.Calendar objFec = java.util.Calendar.getInstance();
      int fecDoc [] = txtFecDoc.getFecha(txtFecDoc.getText());
      if(fecDoc!=null){
         objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
         objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
         objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
      }
      dblBaseDePagos = dblTotalCot;

      dblRetFueGlo = 0.0;
      dblRetIvaGlo = 0.00;

    if(strCodTipPerCli.equals("")){
       if(!(txtCliCod.getText().equals("")))
          Mensaje("AL CLIENTE LE FALTA TIPO DE PERSONA\n. REVISE LOS DATOS DEL CLIENTE Y CORRIJA ");
       objTblModPag.removeAllRows();
    }else{


     if(dblBaseDePagos>0){

     vecDataTblPag = new Vector();

     if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
         Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
        //*************************************************************************************///
         java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
         if(conn!=null){



/******************************************************************************************************************************/

                   calculaSubtotalServiNoServi(conn, "T");

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotTran );
                   }

/******************************************************************************************************************************/

                   calculaSubtotalServiNoServi(conn, "N");

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotBien );
                   }

/******************************************************************************************************************************/

                   calculaSubtotalServiNoServi(conn, "S");

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotServ );
                   }

/******************************************************************************************************************************/

                   calculaSubtotalServiNoServi(conn, "O");

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotServ );
                   }

/******************************************************************************************************************************/


     conn.close();
     conn=null;
    }
    //*************************************************************************************///
    //dblRete = dblRetFueFle+dblRetFue+dblRetIva;
    dblRete = dblRetFueGlo+dblRetIvaGlo;
    dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);



    for(i=0; i < intVal; i++) {
        java.util.Calendar objFecPagActual = Calendar.getInstance();
        objFecPagActual.setTime(objFec.getTime());

        int diaCre = intarreglodia[i];
        int numPag = intarreglonum[i];
        String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);

        if (diaCre!=0)
            objFecPagActual.add(java.util.Calendar.DATE, diaCre);

        dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
        dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));

        java.util.Vector vecReg = new java.util.Vector();
        vecReg.add(INT_TBL_PAGLIN, "");
        vecReg.add(INT_TBL_PAGCRE, ""+diaCre);
        vecReg.add(INT_TBL_PAGFEC, dtePckPag.getText() );

        dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
        dblPago += dblPagos;
        dblPagos = objUti.redondear(dblPagos ,intNumDec);

        vecReg.add(INT_TBL_PAGRET, "");

        if(i==(intVal-1))
            dblPagos= objUti.redondear( dblPagos + (dblTotalCot  - (dblPago + dblRete )), intNumDec);

        vecReg.add(INT_TBL_PAGMON, ""+dblPagos);
        vecReg.add(INT_TBL_PAGGRA, "0");
        vecReg.add(INT_TBL_PAGCOD, "");
        vecReg.add(INT_TBL_PAGSOP, strSop);
       vecDataTblPag.add(vecReg);
    }
   objTblModPag.setData(vecDataTblPag);
   tblPag.setModel(objTblModPag);

vecDataTblPag=null;



/***********************************************************************************************/

    double dblValRet=0;
    String strFecCor="";
    for(int x=0; x<tblPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)) );
      if(dblValRet==0.00){
          strFecCor= tblPag.getValueAt(x, INT_TBL_PAGFEC).toString();
          break;
      }
    }



//
//     String strF1 = objUti.formatearFecha(strFecSisBase, "yyyy-MM-dd", "yyyy/MM/dd");
//     java.util.Date  fac1 = objUti.parseDate(strF1, "yyyy/MM/dd");
//     int intAnioAct= (fac1.getYear()+1900);
//
//     //  por alfredo.  año nuevo 31 dic año anterior
//     String strF = objUti.formatearFecha(strFecCor, "dd/MM/yyyy", "yyyy/MM/dd");
//     java.util.Date  fac = objUti.parseDate(strF, "yyyy/MM/dd");
//     int intAnioCre= (fac.getYear()+1900);
//
//
//     if(intAnioCre > intAnioAct ){
//         strFecCor="31/12/"+intAnioAct;
//     }




    for(int x=0; x<tblPag.getRowCount();x++){
      dblValRet =  Double.parseDouble( objInvItm.getIntDatoValidado(tblPag.getValueAt(x, INT_TBL_PAGRET)) );
      if(dblValRet > 0.00 )
        tblPag.setValueAt(strFecCor,  x, INT_TBL_PAGFEC);

    }

/***********************************************************************************************/

 }
     }}}catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
    catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}

    
//Vector vecDataTblPag;
//double dblRetFueGlo=0, dblRetIvaGlo=0;
//
//    public void calculaPagos(){
//        int intVal =  intCanArr[0];
//        int intsizearre = intarreglodia.length;
//        intVal= intsizearre - (intsizearre-intVal);
//        //java.sql.Statement stm;
//        //java.sql.ResultSet rst;
//        int intFilUltPag = 0;
//        double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0, dblRetFueFle = 0;
//        try{
//            java.util.Calendar objFec = java.util.Calendar.getInstance();
//            int fecDoc [] = txtFecDoc.getFecha(txtFecDoc.getText());
//
//            if(fecDoc!=null){
//                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
//                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
//                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
//            }
//
//            dblRetFueGlo = 0.0;
//            dblRetIvaGlo = 0.00;
//            vecDataTblPag = new Vector();
//
//            dblBaseDePagos = dblTotalCot;
//
//            //Borrando los datos actuales del jtable
//            javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblPag.getModel());
//            modTbl.setRowCount(0);
//
//            if(dblBaseDePagos>0){
//
//                if(cboForPag.getSelectedIndex()!=-1){
//
//                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
//
//                    //*************************************************************************************///
//
//                    java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                    if (conn!=null){
//                        /*
//                        dblRetFueFle = getSubtotalTrans();
//                         if(!(strCodTipPerCli==null)){
//                            if(!(strCodTipPerCli.trim().equals("1"))){
//                                if(dblRetFueFle>0){
//                                    ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
//                                    intFilUltPag = tblCotForPag.getRowCount()-1;
//                                    tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
//                                    tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
//                                    tblCotForPag.setValueAt("1.000", intFilUltPag, 3);
//                                    dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
//                                    System.out.println(">>> "+ dblRetFueFle );
//                                    tblCotForPag.setValueAt( new Double(dblRetFueFle) , intFilUltPag, 4);
//                                }} else { dblRetFueFle=0;  }
//                        }else { dblRetFueFle=0; }
//                        */
//
//                       // System.out.println("Empresa : "+ objZafParSis.getNombreEmpresa().substring(0,6) );
//                       if(objZafParSis.getNombreEmpresa().toString().length() >= 6 ){
//                        if(objZafParSis.getNombreEmpresa().substring(0,6).toUpperCase().equals("ECUATO")){
//                            //intCodMotDoc=125632;
//                        }
//                       }
//
//
//
///******************************************************************************************************************************/
//
//                   calculaSubtotalServiNoServi(conn, "T");
//
//                   if(dblSubSerNoSer > 0.00 ){
//                       cargaForPag(conn, intCodMotTran );
//                   }
//
///******************************************************************************************************************************/
//
//                   calculaSubtotalServiNoServi(conn, "N");
//
//                   if(dblSubSerNoSer > 0.00 ){
//                       cargaForPag(conn, intCodMotBien );
//                   }
//
///******************************************************************************************************************************/
//                   calculaSubtotalServiNoServi(conn, "S");
//
//                   if(dblSubSerNoSer > 0.00 ){
//                       cargaForPag(conn, intCodMotServ );
//                   }
//
///******************************************************************************************************************************/
//
//
//                            /*
//                        String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
//                        strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
//                        strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
//                        "  AND polret.st_reg='A'   AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
//                        stm = conn.createStatement();
//                        rst = stm.executeQuery(strSQL);
//                        while (rst.next()){
//
//                            ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
//                            intFilUltPag = tblCotForPag.getRowCount()-1;
//                            tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
//                            tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
//
//                            if (rst.getString("tx_aplret").equals("S")){
//                                dblRetFue = getSubtotalSinTrans();
//                                if(!(dblRetFueFle>0))
//                                    dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
//                                else
//                                    dblRetFue = objUti.redondeo( (    dblRetFue  * ( rst.getDouble("nd_porret")/100)),2 );
//
//
//
//                                tblCotForPag.setValueAt(rst.getString("nd_porret"), intFilUltPag, 3);
//                                tblCotForPag.setValueAt( new Double(dblRetFue) , intFilUltPag, 4);
//                            }
//                            if (rst.getString("tx_aplret").equals("I")){
//                                dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 );
//                                tblCotForPag.setValueAt(rst.getString("nd_porret") , intFilUltPag, 3);
//                                tblCotForPag.setValueAt( new Double(dblRetIva) , intFilUltPag, 4);
//                            }
//
//                            tblCotForPag.setValueAt(rst.getString("co_tipret") , intFilUltPag, 6);
//                        }
//                        rst.close();
//                        rst=null;
//                        stm.close();
//                        stm=null;
//                        */
//                        conn.close();
//                        conn=null;
//
//
//                    }
//
//                    //*************************************************************************************///
//                    double dblPago=0.00;
//                    double dblPagos=0.00;
//                    int i=0;
//
//                    double dblRete=0;
//                    dblRete = dblRetFueFle+dblRetFue+dblRetIva;
//                    dblBaseDePagos = objUti.redondear( ( dblTotalCot - dblRete ),intNumDec);
//
//
//                    //  dblBaseDePagos = objUti.redondear( ( dblTotalCot - ( dblRetFueFle + dblRetFue + dblRetIva)),intNumDec);
//
//                    for(i=0; i < intVal; i++) {
//                        java.util.Calendar objFecPagActual = objFec.getInstance();
//                        objFecPagActual.setTime(objFec.getTime());
//
//                        int diaCre = intarreglodia[i];
//                        int numPag = intarreglonum[i];
//                        String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);
//
//                        if (diaCre!=0)
//                            objFecPagActual.add(java.util.Calendar.DATE, diaCre);
//
//                        dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
//                        dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
//                        dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
//
//                        ((javax.swing.table.DefaultTableModel)tblPag.getModel()).addRow(new java.util.Vector());
//                        intFilUltPag = tblPag.getRowCount()-1;
//                        tblPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
//                        tblPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
//                        dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
//                        dblPago += dblPagos;
//                        dblPagos = objUti.redondear(dblPagos ,intNumDec);
//                        tblPag.setValueAt( new Double(dblPagos) , intFilUltPag, 4);
//                        tblPag.setValueAt(new Integer(0),  intFilUltPag, 5);
//
//                        tblPag.setValueAt(strSop,  intFilUltPag, 7);
//
//                    }
//
//
//                    double dblultpag =  objUti.redondear(Double.parseDouble(tblPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
//                    // dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago + dblRetIva + dblRetFue) ),intNumDec);
//                    dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago + dblRete ) ),intNumDec);
//
//
//                    tblPag.setValueAt( new Double(dblultpag) , intFilUltPag , 4);
//
//                    //*************************************************************************************///
//                    //*************************************************************************************///
//                } }
//        }
//        catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
//        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
//    }
//
    
    


double dblSubSerNoSer=0, dblIvaSerNoSer=0;

public void calculaSubtotalServiNoServi(java.sql.Connection conn, String strServi){
 double dblCan,dblDes,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
 double dblSub = 0, dblIva = 0,  dblTmp=0;
 int intFilSel=0;
 dblSubSerNoSer=0;
 dblIvaSerNoSer=0;
 try{
   if(conn!=null){

 for (intFilSel=0; intFilSel<tblDat.getRowCount(); intFilSel++){
  if(tblDat.getValueAt(intFilSel,INT_TBL_CODITM) != null ){
    if( objInvItm.getEstItm(conn, objZafParSis.getCodigoEmpresa(), Integer.parseInt(tblDat.getValueAt(intFilSel,INT_TBL_CODITM).toString()), strServi ) ){

     dblCan = Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_CANMOV)) );
     dblPre = Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PREUNI)) );
     dblDes = objUti.redondear( Double.parseDouble( objInvItm.getIntDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_PORDES) ) ),2);

     dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * (dblDes / 100));
     dblTotal  = (dblCan * dblPre)- dblValDes;
     dblTotal =  objUti.redondear(dblTotal,3);
     dblTotal =  objUti.redondear(dblTotal,2);

     dblSub +=  dblTotal;

    if(objInvItm.getStringDatoValidado(tblDat.getValueAt(intFilSel, INT_TBL_BLNIVA)).equals("true")){
        dblTmp = dblTotal;
        dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
    }
    else  dblIva = dblIva + 0;

}}
}

 if(stIvaVen.equals("N")) dblIvaSerNoSer = 0.00;
 else dblIvaSerNoSer = objUti.redondear(dblIva,intNumDec);

     dblSubSerNoSer = objUti.redondear(dblSub ,intNumDec);

 }}catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}


private void cargaForPag(java.sql.Connection conn, int intCodMot ){
   java.sql.Statement stm;
   java.sql.ResultSet rst;
   String strSQL="";
   double dblRetFue=0, dblRetIva=0;
   try{
       
        if (intTipForPag2==4){
            return;
        }
        
     if(conn!=null){

      stm=conn.createStatement();

      strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
      strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
      strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMot+" and co_sujret = "+ intCodTipPerEmp +" and co_ageret  = "+strCodTipPerCli+" "+
      " AND polret.st_reg='A' AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
      System.out.println("--> "+strSQL );
      rst = stm.executeQuery(strSQL);
      while(rst.next()){
           
          if(rst.getString("tx_aplret").equals("S")){

             java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_PAGLIN, "");
             vecReg.add(INT_TBL_PAGCRE, "");
             vecReg.add(INT_TBL_PAGFEC, txtFecDoc.getText() );
             dblRetFue = objUti.redondeo( (    dblSubSerNoSer  * ( rst.getDouble("nd_porret")/100)),2 );
             dblRetFueGlo+=dblRetFue;
             vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret") );
             vecReg.add(INT_TBL_PAGMON, ""+dblRetFue);
             vecReg.add(INT_TBL_PAGGRA, "");
             vecReg.add(INT_TBL_PAGCOD, rst.getString("co_tipret") );
             vecReg.add(INT_TBL_PAGSOP, "N");
             vecDataTblPag.add(vecReg);

          }
          if(rst.getString("tx_aplret").equals("I")){
           if(dblIvaSerNoSer > 0 ){

            java.util.Vector vecReg = new java.util.Vector();
            vecReg.add(INT_TBL_PAGLIN, "");
            vecReg.add(INT_TBL_PAGCRE, "");
            vecReg.add(INT_TBL_PAGFEC, txtFecDoc.getText() );
            dblRetIva = objUti.redondeo(  (  dblIvaSerNoSer  * ( rst.getDouble("nd_porret")/100)),2 );
            dblRetIvaGlo+=dblRetIva;
            vecReg.add(INT_TBL_PAGRET, rst.getString("nd_porret") );
            vecReg.add(INT_TBL_PAGMON, ""+dblRetIva);
            vecReg.add(INT_TBL_PAGGRA, "");
            vecReg.add(INT_TBL_PAGCOD, rst.getString("co_tipret") );
            vecReg.add(INT_TBL_PAGSOP, "N");
            vecDataTblPag.add(vecReg);

           }
          }
        
        }
        rst.close();
        rst=null;

   }}catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
     catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}




    
    
    //
    //         public void calculaPagos(){
    //          int intVal =  intCanArr[0];
    //          int intsizearre = intarreglodia.length;
    //          intVal= intsizearre - (intsizearre-intVal);
    //          java.sql.Statement stm;
    //          java.sql.ResultSet rst;
    //          int intFilUltPag = 0;
    //          double dblBaseDePagos = 0 , dblRetIva = 0 , dblRetFue = 0;
    //          try{
    //                java.util.Calendar objFec = java.util.Calendar.getInstance();
    //                int fecDoc [] = txtFecDoc.getFecha(txtFecDoc.getText());
    //
    //                if(fecDoc!=null){
    //                    objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
    //                        objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
    //                        objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
    //                }
    //
    //                dblBaseDePagos = dblTotalCot;
    //
    //                            //Borrando los datos actuales del jtable
    //                            javax.swing.table.DefaultTableModel modTbl = ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel());
    //                            modTbl.setRowCount(0);
    //
    //                            if(dblBaseDePagos>0){
    //
    //                                // if(!(txtCodForPag.getText()==null || txtCodForPag.getText().equalsIgnoreCase("") || txtCodForPag.getText().equalsIgnoreCase("0") )){
    //                                 if(cboForPag.getSelectedIndex()!=-1){
    //
    //                                    Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
    //
    //                                    //*************************************************************************************///
    //
    //                                      java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    //                                      if (conn!=null){
    //                                          String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta";
    //                                          strSQL += " from tbm_polret as polret left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
    //                                          strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotDoc+" and co_sujret = " + intCodTipPerEmp +" and  co_ageret  = "+strCodTipPerCli;
    //                                          stm = conn.createStatement();
    //                                          rst = stm.executeQuery(strSQL);
    //                                           while (rst.next()){
    //
    //                                               ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
    //                                                intFilUltPag = tblCotForPag.getRowCount()-1;
    //                                                tblCotForPag.setValueAt(new String("") ,intFilUltPag , 1);
    //                                                tblCotForPag.setValueAt(txtFecDoc.getText(), intFilUltPag, 2);
    //
    //                                                 if (rst.getString("tx_aplret").equals("S")){
    //                                                    dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
    //                                                    tblCotForPag.setValueAt(rst.getString("nd_porret"), intFilUltPag, 3);
    //                                                    tblCotForPag.setValueAt( new Double(dblRetFue) , intFilUltPag, 4);
    //                                                 }
    //                                                 if (rst.getString("tx_aplret").equals("I")){
    //                                                     dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 );
    //                                                     tblCotForPag.setValueAt(rst.getString("nd_porret") , intFilUltPag, 3);
    //                                                     tblCotForPag.setValueAt( new Double(dblRetIva) , intFilUltPag, 4);
    //                                                 }
    //
    //                                            tblCotForPag.setValueAt(rst.getString("co_tipret") , intFilUltPag, 6);
    //                                         }
    //                                         rst.close();
    //                                         rst=null;
    //                                         stm.close();
    //                                         stm=null;
    //                                         conn.close();
    //                                         conn=null;
    //                                      }
    //
    //                                    //*************************************************************************************///
    //                                     double dblPago=0.00;
    //                                     double dblPagos=0.00;
    //                                     int i=0;
    //
    //                                     dblBaseDePagos = objUti.redondear( ( dblTotalCot - ( dblRetFue + dblRetIva)),intNumDec);
    //
    //                                for(i=0; i < intVal; i++) {
    //                                           java.util.Calendar objFecPagActual = objFec.getInstance();
    //                                           objFecPagActual.setTime(objFec.getTime());
    //
    //                                           int diaCre = intarreglodia[i];
    //                                           int numPag = intarreglonum[i];
    //                                           String strSop = ((strarreglosop[i]==null)?"N":strarreglosop[i]);
    //
    //                                            if (diaCre!=0)
    //                                               objFecPagActual.add(java.util.Calendar.DATE, diaCre);
    //
    //                                            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
    //                                            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
    //                                            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
    //
    //                                            ((javax.swing.table.DefaultTableModel)tblCotForPag.getModel()).addRow(new java.util.Vector());
    //                                            intFilUltPag = tblCotForPag.getRowCount()-1;
    //                                            tblCotForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
    //                                            tblCotForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
    //                                            dblPagos = objUti.redondear( (numPag==0)?0:(dblBaseDePagos/numPag) ,intNumDec);
    //                                            dblPago += dblPagos;
    //                                            dblPagos = objUti.redondear(dblPagos ,intNumDec);
    //                                            tblCotForPag.setValueAt( new Double(dblPagos) , intFilUltPag, 4);
    //                                            tblCotForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
    //
    //                                            tblCotForPag.setValueAt(strSop,  intFilUltPag, 7);
    //
    //                                    }
    //
    //
    //                                     double dblultpag =  objUti.redondear(Double.parseDouble(tblCotForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
    //                                     dblultpag = objUti.redondear(dblultpag + (dblTotalCot  - (dblPago +dblRetIva + dblRetFue) ),intNumDec);
    //                                     tblCotForPag.setValueAt( new Double(dblultpag) , intFilUltPag , 4);
    //
    //                                    //*************************************************************************************///
    //                                    //*************************************************************************************///
    //                 } }
    //               }
    //               catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
    //               catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
    //     }
    //
    //
    //
    
    
    public class ButFndTbl extends Librerias.ZafTableColBut.ZafTableColBut{
        
        public ButFndTbl(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        
        @Override
        public void butCLick() {
            listaItem("");
        }
    }
    
    
    
    
    private void listaItem(String strDesBusqueda){
        Librerias.ZafConsulta.ZafConsulta  obj =
        new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
        "Codigo,Nombre,Stock,Precio,Iva[S/N],Cod.Sistema","tx_codAlt, tx_nomItm, nd_stkAct, nd_preVta1, st_ivaVen, co_itm",
        "select tx_codAlt,tx_nomItm,nd_stkAct,nd_preVta1,st_ivaVen,co_itm from tbm_inv where co_emp = "+ objZafParSis.getCodigoEmpresa(), "",
        objZafParSis.getStringConexion(),
        objZafParSis.getUsuarioBaseDatos(),
        objZafParSis.getClaveBaseDatos()
        );
        
        obj.setTitle("Listado Productos");
        
        if(tblDat.getValueAt(tblDat.getSelectedRow(), 1) == null || tblDat.getValueAt(tblDat.getSelectedRow(), 1).toString().equals("") )
            obj.show();
        else
            if(!strDesBusqueda.equals("")){
                if(!obj.buscar("tx_codalt = '" + tblDat.getValueAt(tblDat.getSelectedRow(), 1).toString()+"'") )
                    obj.show();
            }else
                obj.show();
        
        if(!obj.GetCamSel(1).equals("")){
            //Poniendo el codigo de producto, que el usuario eligio en la consulta, en la tabla
            tblDat.setValueAt(obj.GetCamSel(1),tblDat.getSelectedRow(),1);
            //Poniendo La descripcion de producto, que el usuario eligio en la consulta, en la tabla
            tblDat.setValueAt(obj.GetCamSel(2),tblDat.getSelectedRow(),3);
            
            Boolean blnIva = false;
            
            if(obj.GetCamSel(5).equals("S"))
                blnIva = true;
            
            tblDat.setValueAt(new Double(obj.GetCamSel(4)),tblDat.getSelectedRow(),6);
            tblDat.setValueAt(blnIva,tblDat.getSelectedRow(),8);
            tblDat.setValueAt(obj.GetCamSel(6),tblDat.getSelectedRow(),10);
            tblDat.changeSelection(tblDat.getSelectedRow(), 5, false, false);
            tblDat.editCellAt(tblDat.getSelectedRow(),5);
        }
    }
    
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JPanel PanOtro;
   private javax.swing.JButton butCliCon;
   private javax.swing.JButton butCliCon1;
   private javax.swing.JButton butCliRet;
   private javax.swing.JButton butConTipCre;
   private javax.swing.JButton butFacEle;
   private javax.swing.JButton butGetXmlFac;
   private javax.swing.JButton butGuiRem;
   private javax.swing.JButton butItmPen;
   private javax.swing.JButton butTipDoc;
   private javax.swing.JButton butVenCon;
   private javax.swing.JComboBox cboMotivoTransporte;
   private javax.swing.JCheckBox chkMasInfo;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel lblAte;
   private javax.swing.JLabel lblCom;
   private javax.swing.JLabel lblCotNumDes;
   private javax.swing.JLabel lblDir;
   private javax.swing.JLabel lblDoc;
   private javax.swing.JLabel lblFac;
   private javax.swing.JLabel lblFecDoc;
   private javax.swing.JLabel lblForPag;
   private javax.swing.JLabel lblGuia;
   private javax.swing.JLabel lblGuia1;
   private javax.swing.JLabel lblIva;
   private javax.swing.JLabel lblMotivo;
   private javax.swing.JLabel lblObs1;
   private javax.swing.JLabel lblObs2;
   private javax.swing.JLabel lblPartida;
   private javax.swing.JLabel lblPed;
   private javax.swing.JLabel lblPrv;
   private javax.swing.JLabel lblSubTot;
   private javax.swing.JLabel lblTipCre;
   private javax.swing.JLabel lblTipCre1;
   private javax.swing.JLabel lblTot;
   private javax.swing.JLabel lblTransporte;
   private javax.swing.JLabel lblfecha;
   private javax.swing.JPanel pan2;
   private javax.swing.JPanel panAsiento;
   private javax.swing.JPanel panCotForPag;
   private javax.swing.JPanel panCotForPagCentral;
   private javax.swing.JPanel panCotForPagNor;
   private javax.swing.JPanel panCotGen;
   private javax.swing.JPanel panCotGenNor;
   private javax.swing.JPanel panCotGenNorNor;
   private javax.swing.JPanel panCotGenNorSur;
   private javax.swing.JPanel panCotGenSur;
   private javax.swing.JPanel panCotGenSurCen;
   private javax.swing.JPanel panCotGenSurEst;
   private javax.swing.JPanel panCotNor;
   private javax.swing.JPanel panNorOtros;
   private javax.swing.JPanel panObs;
   private javax.swing.JPanel panObs1;
   private javax.swing.JPanel panObs2;
   private javax.swing.JScrollPane spnCon;
   private javax.swing.JScrollPane spnForPag;
   private javax.swing.JScrollPane spnObs1;
   private javax.swing.JScrollPane spnObs2;
   private javax.swing.JTabbedPane tabCotCom;
   private javax.swing.JTable tblDat;
   private javax.swing.JTable tblPag;
   private javax.swing.JTextArea txaObs1;
   private javax.swing.JTextArea txaObs2;
   private javax.swing.JTextField txtAte;
   private javax.swing.JTextField txtCliCod;
   private javax.swing.JTextField txtCliDir;
   private javax.swing.JTextField txtCliNom;
   private javax.swing.JTextField txtCodTarCre;
   private javax.swing.JTextField txtCodigoFormaPago;
   private javax.swing.JTextField txtComSol;
   private javax.swing.JTextField txtCot;
   private javax.swing.JTextField txtDoc;
   private javax.swing.JTextField txtFac;
   private javax.swing.JTextField txtGuia;
   private javax.swing.JTextField txtIva;
   private javax.swing.JTextField txtMesGra;
   private javax.swing.JTextField txtNomEmiTar;
   private javax.swing.JTextField txtNomForPag;
   private javax.swing.JTextField txtNomTarCre;
   private javax.swing.JTextField txtOD;
   private javax.swing.JTextField txtPartida;
   private javax.swing.JTextField txtPed;
   private javax.swing.JTextField txtSub;
   private javax.swing.JTextField txtTipDocAli;
   private javax.swing.JTextField txtTipDocNom;
   private javax.swing.JTextField txtTot;
   private javax.swing.JTextField txtTransporte;
   private javax.swing.JTextField txtVenCod;
   private javax.swing.JTextField txtVenNom;
   // End of variables declaration//GEN-END:variables
    private ZafDatePicker txtFecDoc;
    
    private JTextField txtTipDocCod =  new JTextField();
    private JTextField txtCod =  new JTextField();
    private JTextField txtDes =  new JTextField();
    private JTextField txtVehRet =  new JTextField();
    private JTextField txtChoRet =  new JTextField();                      
    
    public void retNomEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_nom, tx_dir from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    GLO_strnomEmp=rstTipDoc.getString("tx_nom");
                    GLO_strdirEmp=rstTipDoc.getString("tx_dir");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
    }
    
    
    
    
    private class ZafMouMotAda extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent evt) {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_ITMALT:
                    strMsg="Codigo Item";
                    break;
                case INT_TBL_DESITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_CANMOV:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_PREUNI:
                    strMsg="Precio";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
        
    }
    
    //     private void listaProductos(String strDesBusqueda){
    //           objVenCon2.setTitle("Listado de Item");
    //            int intNumFil=tblDat.getSelectedRow();
    //
    //           objVenCon2.show();
    //           if (objVenCon2.getSelectedButton()==objVenCon2.INT_BUT_ACE)
    //           {
    //              tblDat.setValueAt(objVenCon2.getValueAt(1),intNumFil,INT_TBL_ITMALT);
    //              tblDat.setValueAt(objVenCon2.getValueAt(2),intNumFil,INT_TBL_CODITM);
    //              tblDat.setValueAt(objVenCon2.getValueAt(3),intNumFil,INT_TBL_DESITM);
    //              tblDat.setValueAt(objVenCon2.getValueAt(7),intNumFil,INT_TBL_UNIDAD);
    //
    //              tblDat.setValueAt(objVenCon2.getValueAt(8),intNumFil,INT_TBL_ITMALT2);
    //
    //               String strValpre="0.00";
    //               strValpre = objVenCon2.getValueAt(5);
    //               if(objVenCon2.getValueAt(5).equals("")) strValpre="0";
    //               if(objVenCon2.getValueAt(5)==null) strValpre="0";
    //               tblDat.setValueAt(strValpre,intNumFil,INT_TBL_PREUNI);
    //
    //              tblDat.setValueAt(objVenCon2.getValueAt(6),intNumFil,INT_TBL_IVATXT);
    //
    //              tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);
    //
    //               if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)
    //                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV);
    //               if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)
    //                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES);
    //               if(tblDat.getValueAt(intNumFil,INT_TBL_TOTAL)==null)
    //                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_TOTAL);
    //
    //
    //
    //                    blnChangeData = false;
    //                    if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
    //                        strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
    //                    else
    //			strBeforeValue = "";
    //                    if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
    //                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
    //                    }
    //
    //                    if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
    //                        strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
    //                    else
    //                        strAfterValue ="";
    //                    if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
    //                            tblDat.setValueAt("D",intNumFil , INT_TBL_ESTADO);
    //                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
    //                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
    //                            tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);
    //                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
    //                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
    //                            tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
    //                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
    //                        tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);
    //                    }
    //
    //                    if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
    //                        if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).equals("S"))
    //                            tblDat.setValueAt(new Boolean(true), intNumFil, INT_TBL_BLNIVA);
    //                        else
    //                            tblDat.setValueAt(new Boolean(false), intNumFil, INT_TBL_BLNIVA);
    //                    }
    //
    //
    //                     if(tblDat.getValueAt(0,INT_TBL_PORDES) != null)
    //                         tblDat.setValueAt(tblDat.getValueAt(0,INT_TBL_PORDES),intNumFil,INT_TBL_PORDES);
    //
    //
    //                     tblDat.setValueAt(null,intNumFil,INT_TBL_BLNPRE);
    //                  calculaSubtotal();
    //
    //           }
    //    }
    //
    
    //     private class ButFndItm extends Librerias.ZafTableColBut.ZafTableColBut{
    //        public ButFndItm(javax.swing.JTable tbl, int intIdx){
    //            super(tbl,intIdx);
    //        }
    //        public void butCLick() {
    //             listaProductos("");
    //             tblDat.requestFocus();
    //             tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_CANMOV, false, false);
    //        }
    //    }
    
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener {
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e) {
            switch (e.getType()) {
                case javax.swing.event.TableModelEvent.INSERT:
                    //                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calculaTotal(dblPorIvaCon);
                    calculaPagos();
                    generaAsiento();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
     
    public class ZafAutPrg extends ZafAut {
        public ZafAutPrg(JInternalFrame ifr) {
            super(ifr,objZafParSis);
        }
        
        /**
         * Esta función determina si es necesaria una autorización para grabar el documento.
         * Por lo general al hacer un cheque no es necesaria autorización. Pero, hay ocasiones
         * en las que se puede establecer el campo "tbm_plaCta.st_aut" para que se solicite
         * autorización para poder emitir un cheque de una determinada cuenta bancaria.
         * @return true: Si el documento requiere autorización.
         * <BR>false: En el caso contrario.
         */
        //------------------------------------------------------------------------------
        /**
         * CONTROL: de ZafVen02 (Facturas de Ventas)
         * Descripcion:
         * El precio de venta digitado
         * por el usuario no debe ser
         * menor al precio unitario del articulo.
         */
        public boolean isPreVtaMenorPreUni(){
            boolean blnRes = true;
            
            //*** boolean  blnValPre=true;
            
            double dblPreUsr, dblPreUniItm, dblDescDigitado , dblDescAsignado   = getDesCli(txtCliCod.getText()),
            dblMinimo, dblValorVenta, dblCostoItm, dblPorGanancia = getMarUti(txtCliCod.getText());
            int intCoItm;
            
            //**********************
            
            
            
            for(int i = 0 ; i < tblDat.getRowCount();i++){
                if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                    dblPreUsr    =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PREUNI)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PREUNI).toString()))),6);
                    dblPreUniItm = getPreUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                    
                    dblDescDigitado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                    dblValorVenta=  (dblDescDigitado!=0)?(dblPreUsr- (dblPreUsr*dblDescDigitado/100)):dblPreUsr;
                    
                    if(dblPreUniItm!=0){
                        dblMinimo    =  (dblDescAsignado!=0)?(dblPreUniItm- (dblPreUniItm*dblDescAsignado/100)):dblPreUniItm;
                    }else{
                        
                        double dblStkAct = getStkAct((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() );
                        if(dblStkAct != 0)
                            dblCostoItm  =   getCosUni((tblDat.getValueAt(i, INT_TBL_CODITM)==null)?"":tblDat.getValueAt(i, INT_TBL_CODITM).toString() ) ;
                        else{
                            // if(tblDat.getValueAt(i,INT_TBL_PRE_COS) == null) dblCostoItm=0.00;
                            //   else   dblCostoItm = Double.parseDouble(tblDat.getValueAt(i,INT_TBL_PRE_COS).toString());
                            dblCostoItm=0.00;
                        }
                        dblMinimo    =   dblCostoItm * dblPorGanancia;
                        dblMinimo    =   dblCostoItm    + ((dblMinimo==0)?0:(dblMinimo/100));
                    }
                    
                    dblValorVenta=objUti.redondear(dblValorVenta,2);
                    dblMinimo=objUti.redondear(dblMinimo,2);
                    
                    if(dblValorVenta<dblMinimo){
                        ///  System.out.println("VALIDAD PRECIOS.............");
                        blnRes = false;
                        tblDat.setValueAt(true,i,INT_TBL_BLNPRE);
                    }
                } }
            //  }
            return blnRes;
        }
        
        
        
        private double getMarUti(String int_co_cli){
            double dblMarUti = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL= "SELECT nd_maruti from tbm_cli as cli " +
                    " where cli.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                    "       cli.co_cli = " + int_co_cli ;
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblMarUti = rstUni.getDouble("nd_maruti");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblMarUti;  }
            catch(Exception Evt) { return dblMarUti; }
            return dblMarUti;
        }
        
        
        private double getDesCli(String int_co_cli){
            double dblDesCli = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL= "SELECT nd_maxdes from tbm_cli as cli " +
                    " where cli.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                    "       cli.co_cli = " + int_co_cli ;
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblDesCli = rstUni.getDouble("nd_maxdes");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt)  {  return dblDesCli; }
            catch(Exception Evt){ return dblDesCli;  }
            return dblDesCli;
        }
        
        
        //**********************************************//////*************************************************
        
        
        private double getCosUni(String int_co_itm){
            double dblPreUni = 0;
            try{
                java.sql.Connection conUni=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conUni!=null){
                    java.sql.Statement stmUni = conUni.createStatement();
                    String sSQL = "  SELECT a1.nd_cosUni FROM tbm_inv AS a1"+
                    " INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
                    " WHERE a2.co_emp="+ objZafParSis.getCodigoEmpresaGrupo() +" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp= "+ objZafParSis.getCodigoEmpresa() +" AND co_itm="+ int_co_itm +")";
                    java.sql.ResultSet rstUni = stmUni.executeQuery(sSQL);
                    if(rstUni.next()){
                        dblPreUni = rstUni.getDouble("nd_cosuni");
                    }
                    rstUni.close();
                    stmUni.close();
                    conUni.close();
                    rstUni = null;
                    stmUni = null;
                    conUni = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblPreUni; }
            catch(Exception Evt){ return dblPreUni;  }
            return dblPreUni;
        }
        
        
        //**********************************************//////*************************************************
        
        /**
         * CONTROL: de ZafVen02 (Facturas de Ventas)
         * Descripcion:(descuento para vendedor)
         * El porcentaje de descuento debe estar dentro de los paramtros
         * establecidos para este vendedor
         */
        public boolean isDesForVen(){
            for(int i = 0 ; i < tblDat.getRowCount()-1;i++){
                double dblDescAsignado =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt(i, INT_TBL_PORDES).toString()))),2);
                if(dblDescAsignado>25)
                    return false;
            }
            return true;
        }
        
        /**
         * Recorre la tabla de detalle de pagos para verificar
         * ke los dias de creditp sean 0 en cuyo caso retornara true
         * @return  true si la venta se esta ralizando de contado
         */
        private boolean isVtaCon(){
            boolean blnIsVtaCon = true;
            for(int i= 0 ; i<tblPag.getRowCount() && blnIsVtaCon; i++){
                if( ((tblPag.getValueAt(i,  INT_TBL_DIACRE)==null)?0:Integer.parseInt(tblPag.getValueAt(i,  INT_TBL_DIACRE)+""))>0){
                    blnIsVtaCon = false;
                }
            }
            return blnIsVtaCon;
        }
        
        /**
         * CONTROL: Que el client no tenga documentos vencidos.
         */
        public boolean isDocVen(){
            boolean blnisDocVen = true;
            try{
                java.sql.Connection conMoncre=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conMoncre!=null){
                    java.sql.Statement stmMonCre = conMoncre.createStatement();
                    if(isVtaCon()) {
                        
                        String sSQL = "SELECT a2.nd_abo  FROM tbm_cabMovInv AS a1 "+
                        " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc "+
                        " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "+
                        " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND "+
                        " a1.co_tipDoc=a3.co_tipDoc) WHERE a1.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND a1.co_cli=" + txtCliCod.getText()+
                        " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0 AND a2.fe_ven<=CURRENT_TIMESTAMP"+
                        " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                        java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                        if(rstMonCre.next())
                            blnisDocVen = false;
                    }else{
                        String sSQL = "SELECT a2.nd_abo  FROM tbm_cabMovInv AS a1 "+
                        " INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc "+
                        " AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) "+
                        " INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND "+
                        " a1.co_tipDoc=a3.co_tipDoc) WHERE a1.co_emp="+ objZafParSis.getCodigoEmpresa() +" AND a1.co_cli=" + txtCliCod.getText()+
                        " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<0 AND a2.fe_ven<=CURRENT_TIMESTAMP"+
                        " ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                        java.sql.ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                        if(rstMonCre.next())
                            blnisDocVen = false;
                        rstMonCre.close();
                        rstMonCre = null;
                    }
                    stmMonCre.close();
                    conMoncre.close();
                    stmMonCre = null;
                    conMoncre = null;
                }
            }
            catch(java.sql.SQLException Evt) {  blnisDocVen = false;  }
            catch(Exception Evt) { blnisDocVen = false;  }
            
            //// System.out.println("VALIDAD DOCU VEN............."+ blnisDocVen);
            
            return blnisDocVen ;
        }
        
        /**
         *  CONTROL: Que el cliente no sobrepase su cupo de credito establecido
         */
        public boolean isCreCli(){
            boolean blnCrecli=true;
            //Obteniendo el cupo de credito para este cliente
            double dblMonCre = getMonCre(txtCliCod.getText());
            //Obteniendo el las cuentas por cobrar a este cliente.
            double dblCxC = getCxC(txtCliCod.getText());
            if(Math.abs(dblTotalCot+dblCxC) > dblMonCre)
                blnCrecli=false;
            
            ///   System.out.println("VALIDAD DOCU VEN............."+ blnCrecli );
            return blnCrecli;
        }
        
        /**
         * Metdo que devuelve el total de las cuentas por cobrar de un cliente
         */
        private double getCxC(String int_co_cli){
            double dblCxC = 0;
            try{
                Connection conMoncre=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conMoncre!=null){
                    Statement stmMonCre = conMoncre.createStatement();
                    String sSQL ="select  sum((abs(pag.mo_pag) - pag.nd_abo)) as nd_deuda           " +
                    "from tbm_pagmovinv as pag, tbm_cabmovinv as cab, tbm_cabtipdoc as tipdoc " +
                    "where                                                                    " +
                    " pag.co_emp =   "  + objZafParSis.getCodigoEmpresa() +   "   and   "  +
                    " pag.co_emp = cab.co_emp and      " +
                    " pag.co_emp = tipdoc.co_emp and   " +
                    " pag.co_loc =   "  + objZafParSis.getCodigoLocal()   +   "   and   " +
                    " pag.co_loc = cab.co_loc                              and   " +
                    " pag.co_loc = tipdoc.co_loc                           and   " +
                    " pag.co_tipDoc = cab.co_tipdoc                        and   " +
                    " pag.co_tipDoc = tipdoc.co_tipdoc                     and   " +
                    " pag.co_doc     = cab.co_doc                          and   " +
                    " tipdoc.co_tipdoc  = " + 1                        + " and " +
                    " (abs(pag.mo_pag) - pag.nd_abo) > 0                   and   " +
                    " cab.st_reg in ('C','A','R','F')                      and   " +
                    " pag.st_reg in ('C','A')                              and   " +
                    " cab.co_cli = " + int_co_cli  ;
                    
                    ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next()){
                        dblCxC = rstMonCre.getDouble("nd_deuda");
                    }
                    
                    rstMonCre.close();
                    stmMonCre.close();
                    conMoncre.close();
                    rstMonCre = null;
                    stmMonCre = null;
                    conMoncre = null;
                }
            }
            catch(java.sql.SQLException Evt)  {  return dblCxC;  }
            catch(Exception Evt) {  return dblCxC;  }
            return dblCxC;
        }
        
        /**
         * Metdo que devuelve el cupo de credito de un cliente
         */
        private double getMonCre(String int_co_cli){
            double dblMonCre = 0;
            try{
                Connection conMoncre=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conMoncre!=null){
                    Statement stmMonCre = conMoncre.createStatement();
                    
                    String sSQL= "SELECT nd_monCre from tbm_cli as cli " +
                    " where cli.co_emp = " + objZafParSis.getCodigoEmpresa() +  " and " +
                    "       cli.co_cli = " + int_co_cli ;
                    ResultSet rstMonCre = stmMonCre.executeQuery(sSQL);
                    if(rstMonCre.next()){
                        dblMonCre = rstMonCre.getDouble("nd_monCre");
                    }
                    rstMonCre.close();
                    stmMonCre.close();
                    conMoncre.close();
                    rstMonCre = null;
                    stmMonCre = null;
                    conMoncre = null;
                }
            }
            catch(java.sql.SQLException Evt) {  return dblMonCre; }
            catch(Exception Evt) { return dblMonCre; }
            return dblMonCre;
        }
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
        

        @Override
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
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer, strSQLRep="", strLogo="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
//                //Obtener la fecha y hora del servidor.
//                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;
//                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                datFecAux=null;
                
                if(txtTipDocCod.getText().toString().equals("228")){
                    strSQLRep="";
                    strSQLRep+=" SELECT (substring(loc.tx_secdoc,1,3) || '-' || substring(loc.tx_secdoc,5,3) || '-' || to_char(ne_numdoc, '000000000')) as tx_numdoc \n";
                    strSQLRep+="         , DocCab.tx_numautfacele, DocCab.fe_autfacele,DocCab.tx_claaccfacele,usr.tx_nom as nomusring,DocCab.tx_obs1, DocCab.tx_ate, DocCab.tx_numGuiRem\n";
                    strSQLRep+="         , loc.tx_dir as diremp,emp.tx_nom as nomemp, DocCab.tx_telcli, DocCab.tx_ruc,/*DocCab.ne_numdoc,*/ \n";
                    strSQLRep+="         case when  DocCab.co_cli = 828 then 'Manta,' else '' end as feciu,DocCab.fe_doc,DocCab.tx_numped, \n";
                    strSQLRep+="         DocCab.ne_numgui,DocCab.tx_desforpag, DocCab.tx_nomcli,DocCab.co_cli,DocCab.tx_nomven,DocCab.tx_dircli, \n";
                    strSQLRep+="         DocCab.tx_vehret,DocCab.tx_choret,a3.tx_deslar,abs(DocCab.nd_sub) as subtotal, \n";
                    strSQLRep+="         abs(DocCab.nd_sub*(DocCab.nd_poriva/100)) as iva,  \n";
                    strSQLRep+="         abs((DocCab.nd_sub*(DocCab.nd_poriva/100))+DocCab.nd_sub) as total, \n";
                    strSQLRep+="         case when inv.st_ser = 'S' then '(*) '|| a2.tx_codalt2 else a2.tx_codalt2 end as tx_codalt2, \n";
                    strSQLRep+="         /*CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' END as tx_nomitm,   */\n";
                    strSQLRep+="         CASE WHEN inv.st_ser = 'S' then 'SERVICIO: '|| a2.tx_nomItm ELSE \n";
                    strSQLRep+="         CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' \n";
                    strSQLRep+="         END END  as tx_nomitm,abs(a2.nd_can) as can,a2.tx_unimed,a2.nd_preuni, a2.nd_pordes,abs(a2.nd_can*a2.nd_preuni) as precan, \n";
                    strSQLRep+="         ((a2.nd_can*abs(a2.nd_preuni))*(a2.nd_pordes/100)) as des, \n";
                    strSQLRep+="         ROUND(  abs((a2.nd_can*abs(a2.nd_preuni))-((a2.nd_can*abs(a2.nd_preuni))*(a2.nd_pordes/100))) ,2) as tot, \n";
                    strSQLRep+="         abs(DocCab.nd_sub) as sub,abs(DocCab.nd_valiva) as valiva, abs(DocCab.nd_tot) as  total_bas, \n";
                    strSQLRep+="         DocCab.tx_ciucli as cuidad,emp.tx_desconesp as contri1, \n";
                    strSQLRep+="         case when (ne_numdocree is not null) then ( 'Factura No ' || ne_numdoc  || ' Reemplaza a la Factura No ' ||  ne_numdocree ) end as Reem, \n";
                    strSQLRep+="        (select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo \n";
                    strSQLRep+="         from tbm_detmovinv as x\n";
                    strSQLRep+="         inner join tbm_inv as inv on (x.co_emp=inv.co_emp and x.co_itm=inv.co_itm) \n";
                    strSQLRep+="         WHERE x.co_emp=DocCab.co_emp and x.co_loc=DocCab.co_loc  and x.co_tipdoc=DocCab.co_tipdoc and x.co_doc=DocCab.co_doc ) as kgramo, \n";
                    strSQLRep+="        (select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo \n";
                    strSQLRep+="         from tbm_detmovinv as x \n";
                    strSQLRep+="         inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm) \n";
                    strSQLRep+="         WHERE x.co_emp=DocCab.co_emp and x.co_loc=DocCab.co_loc  and x.co_tipdoc=DocCab.co_tipdoc and x.co_doc=DocCab.co_doc ) as kilo \n";
                    strSQLRep+="               ,emp.tx_desConEsp";
                    strSQLRep+="  FROM tbm_cabMovInv as DocCab \n";
                    strSQLRep+="  INNER JOIN tbm_detmovinv as a2  ON(DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc) \n";
                    strSQLRep+="  LEFT OUTER JOIN tbm_var as a3 ON (DocCab.co_forret = a3.co_reg ) \n";
                    strSQLRep+="  inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm) \n";
                    strSQLRep+="  inner JOIN tbm_loc as loc ON ( doccab.co_emp = loc.co_emp and  doccab.co_loc=loc.co_loc) \n";
                    strSQLRep+="  inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu) \n";
                    strSQLRep+="  inner JOIN tbm_emp as emp ON ( emp.co_emp = DocCab.co_emp) \n";
                    strSQLRep+="  inner JOIN tbm_usr as usr ON ( usr.co_usr = DocCab.co_usring) \n";
                    strSQLRep+="  WHERE DocCab.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQLRep+="        and DocCab.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQLRep+="        and DocCab.co_tipdoc=" + txtTipDocCod.getText() ;
                    strSQLRep+="        and DocCab.co_doc=" + txtDoc.getText();
                    System.out.println("FACTURA ELECTRONICA " + strSQLRep );
                }
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 19:
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                strLogo=objRptSis.getRutaReporte(i);
                                if(objZafParSis.getCodigoEmpresa()==1) {
                                    strLogo+="Logos/logTuv.png";
                                }
                                else if(objZafParSis.getCodigoEmpresa()==2){
                                    strLogo+="Logos/logCas.png";
                                }
                                else{
                                    strLogo+="Logos/logDim.png";
                                }
                                System.out.println("LOGOS : " + strLogo );
                                System.out.println("codTipDoc : "+ txtTipDocCod.getText() );
                                System.out.println("codDoc : "+ txtDoc.getText() );
                                java.util.Map mapPar=new java.util.HashMap();
                                
                                if(txtTipDocCod.getText().toString().equals("1")){   
                                    mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                    mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()) );
                                    mapPar.put("codTipDoc", new Integer( Integer.parseInt(txtTipDocCod.getText())) );
                                    mapPar.put("codDoc", new Integer( Integer.parseInt(txtDoc.getText())) );
                                }
                                else if(txtTipDocCod.getText().toString().equals("228")){
                                    //La funcion getCodForPag() se encarga de llenar la variable global strDesForPagSri
                                    getCodForPag(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtTipDocCod.getText()), Integer.parseInt(txtDoc.getText()));
                                    //mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                    mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()) );
                                    mapPar.put("codTipDoc", new Integer(Integer.parseInt(txtTipDocCod.getText())) );
                                    mapPar.put("codDoc", new Integer(Integer.parseInt(txtDoc.getText())) );
                                    mapPar.put("ambiente", 2); //1 = Pruebas; 2 = Produccion
                                    mapPar.put("DesForPagSri", strDesForPagSri);
                                    mapPar.put("RUTA_LOGO", strLogo);
                                }
//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                if (booFormatA4) {
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                }else if (booFormatA5) {
                                    objRptSis.generarReporteA5(strRutRpt, strNomRpt, mapPar, intTipRpt);  
                                }else{
                                    objRptSis.generarReporteA5(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                }
                                //objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    @Override
    protected void finalize() throws Throwable {   
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
        ///    System.out.println ("Se libera Objeto...");
        
    }
    
    private boolean getCodForPag(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
      boolean blnRes = true;
      int intCodForPag, intCodForPagSri, intTipForPag;
      java.sql.Connection conLoc;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      
      try
      {
         conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
         strDesForPagSri = ""; //Variable global
         
         if (conLoc != null)
         {  stmLoc = conLoc.createStatement();
            intCodForPag = 0;   
            strSQL =  "SELECT co_forpag ";
            strSQL += "FROM      tbm_cabmovinv ";
            strSQL += "WHERE     co_emp = " + intCodEmp;
            strSQL += "          and co_loc = " + intCodLoc;
            strSQL += "          and co_tipdoc = " + intCodTipDoc;
            strSQL += "          and co_doc = " + intCodDoc;
            rstLoc = stmLoc.executeQuery(strSQL);

            if (rstLoc.next())
               intCodForPag = rstLoc.getInt("co_forpag");
            
            intTipForPag = 0;
            strSQL = "select ne_tipforpag from tbm_cabforpag where co_emp = " + intCodEmp + " and co_forpag = " + intCodForPag;
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next())
               intTipForPag = rstLoc.getInt("ne_tipforpag");
            
            switch (intTipForPag)
            {  case 1: //Contado
                  intCodForPagSri = 1; //Sin utilizacion del sistema financiero
                  strDesForPagSri = "Sin utilización del sistema financiero";
                  break;
               case 4: //Tarjeta de credito/Debito
                  intCodForPagSri = 19; //Tarjeta de credito
                  strDesForPagSri = "Tarjeta de crédito";
                  break;
               default:
                  intCodForPagSri = 20; //Otros con utilizacion del sistema financiero
                  strDesForPagSri = "Otros con utilización del sistema financiero";
                  break;
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
      
      catch(Exception e)
      {  
         blnRes = false;
      }
      
      return blnRes;
    } //Funcion getCodForPag()
    
    private boolean validarAutFacEleSRI(){
        boolean blnRes=false;// Jota 21/Enero/2015
        String strCadena="",strMsg, strUtiFacEle;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            stmLoc= conLoc.createStatement();
            
            strCadena= "SELECT st_utiFacEle ";
            strCadena+="FROM   tbm_cabTipDoc ";
            strCadena+="WHERE  st_reg = 'A' ";
            strCadena+="       and co_emp = " + objZafParSis.getCodigoEmpresa();
            strCadena+="       and co_loc = " + objZafParSis.getCodigoLocal();
            strCadena+="       and co_tipdoc = " + txtTipDocCod.getText();
            strUtiFacEle = "";
            rstLoc=stmLoc.executeQuery(strCadena);
            
            if (rstLoc.next())
            {  strUtiFacEle = rstLoc.getString("st_utiFacEle") == null? "" :rstLoc.getString("st_utiFacEle");
               
               if (strUtiFacEle.equals("") || strUtiFacEle.equals("N"))
                  blnRes = true; //Como este TipDoc no usa Fac.Ele, la funcion debe devolver true para que permita imprimir la factura
            }
            
            if (strUtiFacEle.equals("S"))
            {  strCadena="";
               strCadena+=" SELECT st_autFacEle, fe_autFacEle, tx_claAccFacEle,tx_numAutFacEle,tx_desErrFacEle";
               strCadena+=" FROM tbm_cabMovInv ";
               strCadena+=" WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " AND ";
               strCadena+="       co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipDoc=228 AND " ;
               strCadena+="       co_doc=" + txtDoc.getText();
               strCadena+=" ";
               System.out.println("validarDocEle " + strCadena);
               rstLoc=stmLoc.executeQuery(strCadena);
               if(rstLoc.next()){
                    if(rstLoc.getString("st_autFacEle")==null || !(rstLoc.getString("st_autFacEle").equals("A"))){
                       blnRes=false;
                       strMsg="<html> La factura no ha sido AUTORIZADA POR EL SRI. <BR>" ;
                       strMsg+="Por favor volver a intentar despues de unos minutos. <html>";
                       JOptionPane.showMessageDialog(jfrThis, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        blnRes=true;
                    } 
               }
            }
            
            rstLoc.close();
            rstLoc=null;
            stmLoc.close();
            stmLoc=null;           
            conLoc.close();
            conLoc=null;
         }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void enviarFacturaEnLineaSRI(){
        java.sql.Connection conLoc;
        String strMsg;
         try{
            conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());        
           if(consultarEstadoFacturaContingencia(conLoc)){
               if(cambiarEstadoFacturacionElectronica(conLoc)){
                  if(enviarPulsoFacturacionElectronica()){
                      butFacEle.setEnabled(false);
                      strMsg="<html> Su factura ha sido enviada con éxito al SRI. <BR>" ;
                      strMsg+="Por favor espere pacientemente a que el SRI la autorice. <html>";
                      String strTit="Mensaje del sistema Zafiro";
                      JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                  } 
               }
           }
          
            
            
            
        }
        catch (java.sql.SQLException e) { 
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
    }
    
    private void obtenerXMLFactura()
    {
        int intCodTipDoc, intCodDoc;
        java.sql.Connection conLoc;
        String strMsg, strEstFacEle, strPrmSer;
        
        try
        {
           if (txtTipDocCod.getText().equals("") || txtDoc.getText().equals(""))
           {  strMsg =  "<HTML>Debe tener los datos de la factura cargados en pantalla.<BR>";
              strMsg += "Verifique esto y vuelva a intentarlo.<HTML>";
              String strTit = "Mensaje del sistema Zafiro";
              JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
              return;
           }
           
           conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
           intCodTipDoc = Integer.parseInt(txtTipDocCod.getText());
           intCodDoc = Integer.parseInt(txtDoc.getText());
           
           ZafCabEstFacEle objZafCabEstFacEle = new ZafCabEstFacEle();
           objZafCabEstFacEle.setCodEmp(objZafParSis.getCodigoEmpresa());
           objZafCabEstFacEle.setCodLoc(objZafParSis.getCodigoLocal());
           objZafCabEstFacEle.setCodTipDoc(intCodTipDoc);
           objZafCabEstFacEle.setCodDoc(intCodDoc);
           objZafCabEstFacEle.setNomTbl("tbm_cabmovinv");
           ZafEstFacEle objZafEstFacEle = new ZafEstFacEle(objZafCabEstFacEle, conLoc);
           strEstFacEle = objZafEstFacEle.getEstadoDocEle();
           
           if (!strEstFacEle.equals("A"))
           {  strMsg =  "<HTML>La factura no está autorizada.<BR>";
              strMsg += "Verifique esto y vuelva a intentarlo.<HTML>";
              String strTit = "Mensaje del sistema Zafiro";
              JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
              return;
           }
           
           //Ejemplo de strPrmSer = "1-4-228-29978-FAC-admin";
           strPrmSer = objZafParSis.getCodigoEmpresa() + "-" + objZafParSis.getCodigoLocal() + "-" + intCodTipDoc + "-" + intCodDoc + "-FAC-" + objZafParSis.getNombreUsuario();
           
           if (enviarPulsoServicio15(strPrmSer))
           {  butGetXmlFac.setEnabled(false);
              strMsg =  "<HTML>Su requerimiento para obtener los archivos XML y PDF ha sido enviado con éxito.<BR>";
              strMsg += "Por favor espere pacientemente la recepción del correo con los archivos solicitados.<HTML>";
              String strTit = "Mensaje del sistema Zafiro";
              JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
           }
        }
        
        catch (java.sql.SQLException e)
        { 
           objUti.mostrarMsgErr_F1(this, e);  
        }
        
        catch(Exception  Evt)
        {
           objUti.mostrarMsgErr_F1(this, Evt);
        }  
    }
    
    private boolean consultarEstadoFacturaContingencia(java.sql.Connection conn){
        boolean blnRes=false;
        String strSql;
        String strMsg, strRes;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strSql="";
                strSql+=" SELECT tx_deserrfacele,st_autfacele, fe_ing, * \n";
                strSql+=" FROM tbm_cabmovinv  \n";
                strSql+=" WHERE co_emp= "+objZafParSis.getCodigoEmpresa()+" AND \n";
                strSql+="       co_loc= "+objZafParSis.getCodigoLocal()+" AND \n";
                strSql+="       co_tipDoc=228 AND co_doc="+txtDoc.getText()+" \n";
                strSql+=" \n";
                System.out.println("consultarEstadoFacturaContingencia: \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getString("st_autfacele")!=null){
                        strRes=rstLoc.getString("st_autfacele");
                        if(strRes.equals("C")){
                           blnRes=true;
                        }
                        else if(strRes.equals("X") || strRes.equals("E")){
                            strMsg="<html> Su factura fue enviada al SRI, pero presentó errores. <BR>" ;
                            strMsg+="Por Favor comuniquese con el administrador del sistema. <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                            blnRes=false;
                        }
                        else if(strRes.equals("A")){
                            strMsg="<html> Su factura ya fue Autorizada. " ;
                            strMsg+=" <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                            blnRes=false;
                        }
                        else if(strRes.equals("S")){
                            strMsg="<html> Su factura ya fue enviada al SRI. <BR>" ;
                        strMsg+="Por favor espere pacientemente a que el SRI la autorice. <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                            blnRes=false;
                        }
                        else{
                            strMsg="<html> Su factura fue enviada al SRI, pero presentó errores. <BR>" ;
                            strMsg+="Por Favor comuniquese con el administrador del sistema. <html>";
                            String strTit="Mensaje del sistema Zafiro";
                            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                            blnRes=false;
                        }
                    }
                    else{
                        strMsg="<html> Su factura está en proceso. <BR>" ;
                        strMsg+="Por favor espere pacientemente a que el Sistema la procese. <html>";
                        String strTit="Mensaje del sistema Zafiro";
                        JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                        blnRes=false;
                    }
                        
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc=null;
                rstLoc=null;
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
    
      
    private boolean cambiarEstadoFacturacionElectronica(java.sql.Connection conn){
        boolean blnRes=false;
        String strSql;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabmovinv SET st_autfacele='S' \n";
                strSql+=" WHERE co_emp= "+objZafParSis.getCodigoEmpresa()+" AND \n";
                strSql+="       co_loc= "+objZafParSis.getCodigoLocal()+" AND \n";
                strSql+="       co_tipDoc=228 AND co_doc="+txtDoc.getText()+" ;\n";
                System.out.println("cambiarEstadoFacturacionElectronica" + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
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
    
    
    private boolean enviarPulsoFacturacionElectronica()
    {
        objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciar();
        System.out.println(" PULSO::::::  enviarPulsoFacturacionElectronica  ");
        return true;
    }
    
    private boolean enviarPulsoServicio15(String strPrmSer)
    {
       objPulFacEle = new ZafPulFacEle();
       objPulFacEle.iniciarEnvioPulsoServicio15(strPrmSer);
       System.out.println(" PULSO::::::  enviarPulsoServicio15  ");
       return true;
    }
//public class MyKeyListener implements KeyListener {
//		@Override
//		public void keyTyped(KeyEvent e) {
//                    StringBuffer sb = new StringBuffer("");
//                    if (ctrl) {
//                        sb.append("ctrl+");
//                    }
//                    sb.append(keyHit);
//                    System.out.println(sb.toString());
//                    ctrl = false;
//                    keyHit = "";
//                    if (sb.toString().equals("ctrl+H")) {
//                        if (booFormatA5) {
//                            mostrarMsg("Se cambió a formato A4.");
//                            booFormatA4 = true;
//                            booFormatA5 = false;
//                        } else {
//                            mostrarMsg("Se cambió a formato A5.");
//                            booFormatA4 = false;
//                            booFormatA5 = true;
//                        }
//                    }
//		}
//
//		@Override
//		public void keyPressed(KeyEvent e) {
//			 if (e.getKeyCode()==KeyEvent.VK_CONTROL)  ctrl = true;  
//                         else keyHit = KeyEvent.getKeyText( e.getKeyCode() );
//		}
//
//		@Override
//		public void keyReleased(KeyEvent e) {
//			//System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
//		}
//	}

}


/*
 *    Created on 6 de septiembre de 2004, 15:54   tbr_bodemp 
 */      
          
package Compras.ZafCom02;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
//import javax.swing.*;
  
/**
 * ORDENES DE COMPRA.
 * @author  Javier Ayapata G. 
 */

public class ZafCom02 extends javax.swing.JInternalFrame{
    private mitoolbar objTooBar;
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafUtil.ZafUtil objUti;
    private Librerias.ZafAsiDia.ZafAsiDia objDiario;
    private Librerias.ZafUtil.ZafTipDoc objTipDoc;
    private Librerias.ZafUtil.ZafCliente_dat objCliente_dat; // Para Obtener la informacion del proveedor
    private Librerias.ZafUtil.ZafCiudad_dat objCiudad_dat; // Para Obtener la informacion de la ciudad del proveedor
    private Librerias.ZafUtil.UltDocPrint objUltDocPrint;  // Para trabajar con la informacion de tipo de documento    
    private Librerias.ZafUtil.ZafCtaCtb objCtaCtb;  // Para obtener  los codigos y nombres de ctas ctbles
    //private Librerias.ZafInventario.ZafInvItm objInvItm;
    final int INT_ARL_STK_INV_STK_DIS=10;  // nd_stkDisp
    final int INT_ARL_STK_INV_STK=0;  // nd_stk
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut objTblCelEdiBut2;        //Editor: JButton en celda.   
    private Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon objTblCelEdiTxtCon2;  //Editor: JTextField de consulta en celda.
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private boolean blnChangeData=false;
    private String strBeforeValue,strAfterValue;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private String strCodPrv, strDesLarPrv;   
    private String strCodCom, strDesLarCom;   
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.   
    private Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu ZafTblPopMn;

    private ZafVenCon objVenCon2; //*****************  
    private ZafVenCon objVenConTipdoc; //***************** 
    private ZafVenCon objVenConPrv; //*****************  
    private ZafVenCon objVenConVen; //*****************  

    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.
    private int intCodMnu; //obtiene el codigo del menu
    private double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    private double dblPorDesOc=0; // PORCENTAJE DE DESCUENTO PARA LAS ORDENER DE COMPRA.
    private int intNumDec, intNumDoc= 0,  op=1 ; //Numero de decimales a presentar
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    private LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    private String strTipEmp,strAux;
    private javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private Librerias.ZafObtConCen.ZafObtConCen  objObtConCen;
    private int  INTCODREGCEN=0;
    private int  INTVERCONCEN=0;

    private boolean blnLimpiar = true; //Limpiar o no en el metodo clnTextos
    //Constantes de la tabla
    private static final int INT_TBL_LINEA    = 0 ; 
    private static final int INT_TBL_ITMALT   = 1 ;
    private static final int INT_TBL_BUTITM   = 2 ;
    private static final int INT_TBL_DESITM   = 3 ;
    private static final int INT_TBL_UNIDAD   = 4 ;
    private static final int INT_TBL_CODBOD   = 5 ;
    private static final int INT_TBL_BUTBOD   = 6 ;
    private static final int INT_TBL_CANORI   = 7 ;
    private static final int INT_TBL_CANMOV   = 8 ;            //Cantidad del movimiento (venta o compra)
    private static final int INT_TBL_PRECOS   = 9 ;           //Precio de Venta
    private static final int INT_TBL_PORDES   = 10 ;           //Porcentaje de descuento
    private static final int INT_TBL_BLNIVA   = 11;           //Boolean Iva
    private static final int INT_TBL_TOTAL    = 12;           //Total de la venta o compra del producto
    private static final int INT_TBL_CODITM   = 13;
    private static final int INT_TBL_ITMORI   = 14;    //Codigo del item de una o/c cargada
    private static final int INT_TBL_BODORI   = 15;    //Codigo del bodega de una o/c cargada
    private static final int INT_TBL_ESTADO   = 16;
    private static final int INT_TBL_IVATXT   = 17;
    private static final int INT_TBL_ITMALT2  = 18 ;
    private static final int INT_TBL_ITMSER   = 19;            //Columna que contiene SI el item es de servicio S o N
    private static final int INT_TBL_CODREG   = 20;            //Columna que contiene SI el item es de servicio S o N
    private static final int INT_TBL_CODITMACT= 21;  //codigo  alterno codigo actual
    private static final int INT_TBL_IEBODFIS = 22;  // estado que dice si ingresa/egresa fisicamente en bodega
    private static final int INT_TBL_COSUNIORI= 23;  //codigo  alterno codigo actual
    private static final int INT_TBL_PORDESORI= 24;  // estado que dice si ingresa/egresa fisicamente en bodega
    private static final int INT_TBL_PRECOSRES= 25;  
    private static final int INT_TBL_PORDESRES= 26;  

    //Constantes del ArrayList Elementos Eliminados
    private final int INT_ARR_CODITM   = 0;
    private final int INT_ARR_CODBOD   = 1;
    private final int INT_ARR_CANMOV   = 2;
    private final int INT_ARR_ITMORI   = 3;
    private final int INT_ARR_BODORI   = 4;
    private final int INT_ARR_CANORI   = 5;

    private final int INT_DET_COD_EMP    =0;            //Código de la empresa.
    private final int INT_DET_COD_LOC    =1;            //Código del local.
    private final int INT_DET_COD_TIP_DOC=2;            //Código del tipo de documento.
    private final int INT_DET_COD_DOC    =3;            //Código del documento.
    private final int INT_DET_COD_REG    =4;            //Código del documento.
    private final int INT_DET_CAL_COS_UNI=5;            //Calcular costo unitario.
    private final int INT_DET_EST_REG    =6;            //Estado del registro.
    private final int INT_DET_CAN        =7;            //Cantidad.
    private final int INT_DET_COS_UNI    =8;            //Costo unitario.
    private final int INT_DET_POR_DES    =9;            //Porcentaje de descuento.
    private final int INT_DET_SAL_UNI    =10;           //Saldo en unidades.
    private final int INT_DET_SAL_VAL    =11;           //Saldo en valores.
    private final int INT_DET_COS_UNI_DOC=12;           //Costo unitario del item en el documento.

    private int intCodEmp =0;   
    private int intCodLoc =0;   
    private int intCodCot=0;  
    private int intValBus = 0; 

    private String GLO_strArreItm="";
    private int Glo_intCodSec=0;

    private int intCodTipPerEmp;
    private String strCodTipPerCli;

    private String  STR_ESTREG="";
    private String stIvaCom="S";        
    private final String strVersion = " v8.28 ";
    private String strCorEleTo="sistemas1@tuvalsa.com";  //sistemas1@tuvalsa.com";

    private final int intarreglonum[] = new int[10]; 
    private final int intarreglodia[] = new int[10]; 
    private final int intCanArr[]= new int[1];
    private final ArrayList arreBod=new ArrayList();
    private double dblTotalCom, dblIvaCom,dblSubtotalCom;  
    private String strTipPer_glo = ""; 

    //Constante Mensaje Resaltador
    private final String strColorHtml = "<FONT COLOR=\"blue\">";
    private Vector vecForPag; //Vector que contiene el codigo del pago
    private Vector vecDetDiario;      

    private String strFecSis;
    //Conection
    private Connection conCab2,conCab, CONN_GLO, conRemGlo;      //Coneccion a la base donde se encuentra la cotizacion
    private Statement stmaux,stmCab;   //Statement para la cotizacion 
    private ResultSet rstaux;//Resultset que tendra los datos de la cabecera de la cotizacion
    private ResultSet rstCab;//Resultset que tendra los datos de la cabecera
    private ResultSet rstMovInvDet;//Resultset que tendra los datos del Detalle
    private String sSQL, strFiltro;//EL filtro de la Consulta actual
    private String strMerIngEgr="", strTipIngEgr="";

    private String GLOCODCLI;

    private double dlbValorOriTotOrd=0.00;

    private JPanel panForRet = new JPanel();
    private JLabel lblForRet = new JLabel();
    private JTextField txtCod = new JTextField();
    private JTextField txtDes = new JTextField();
    private JButton butForret = new JButton();
    private JLabel lblvehRet = new JLabel();
    private JTextField txtVehRet = new JTextField();
    private JTextField txtChoRet = new JTextField(); 
    private JLabel lblChoret = new JLabel();

    private String strCodLocOc="";
    private int intCodTipDocOc=0;
    private int intCodDocOc=0;
    private int intTipCons=0;

    private String strCodEmpOC="";
    private String strCodLocOC="";
    private String strCodTipOC="";
    private String strCodDocOC="";
    private boolean blnEstCarOC=false;

    /** Creates new form zafCotCom */  
    public ZafCom02(Librerias.ZafParSis.ZafParSis obj) {
      try{  
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        jfrThis = this;
        initComponents();
        this.setTitle(objZafParSis.getNombreMenu()+strVersion);
        objUti = new ZafUtil();
        objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);
        
         //***********************************************************************
             //  objAsiDia=new ZafAsiDia(objParSis);
               objDiario.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                   @Override
                 public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objDiario.setCodigoTipoDocumento(-1);
                    else
                        objDiario.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                  }
               });
            //***********************************************************************
               
               
            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;

             objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

          
       /* Iniciando clase de informacion de cliente*/
        objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);
        objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objZafParSis);
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);         
        //objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);         
       
        tabOrdCom.addTab("Diario",objDiario);    
        
       
        objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
        txtCodTipDoc.setVisible(false);        
       
        
        Abrir_Conexion();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */
          
        cargaTipoDocPredeterminado();
        
       
        
        intCodMnu = objZafParSis.getCodigoMenu();
        intNumDec = 2; // objZafParSis.getDecimalesMostrar();
        strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText(strFecSis);
        panCotGenNor.add(txtFecDoc);
        txtFecDoc.setBounds(530, 2, 92, 20);
        txtFecDoc.setEnabled(false);
        
        
           //********************************
         panForRet.setLayout(null);

        panForRet.add(txtDes);
        txtDes.setBounds(130, 20, 220, 21);

        lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblForRet.setText("Forma de Retiro:");
        panForRet.add(lblForRet);
        lblForRet.setBounds(20, 20, 100, 15);

        lblvehRet.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblvehRet.setText("Vehiculo de Retiro:");
        panForRet.add(lblvehRet);
        lblvehRet.setBounds(20, 45, 100, 15);

        lblChoret.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblChoret.setText("Chofer de Retiro:");
        panForRet.add(lblChoret);
        lblChoret.setBounds(20, 75, 100, 15);

        panForRet.add(txtChoRet);
        txtChoRet.setBounds(130, 70, 220, 21);

        panForRet.add(txtVehRet);
        txtVehRet.setBounds(130, 45, 220, 21);

        txtCod.setEditable(false);
        //panForRet.add(txtCod);
        txtCod.setBounds(130, 20, 40, 21);
        
         panForRet.add(butForret);
         
         
        butForret.setText("...");
        butForret.setPreferredSize(new java.awt.Dimension(35, 30));
        butForret.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              BuscarFormaRetiro();
            }
        });
         butForret.setBounds(350, 20, 22, 20);
        
         tabOrdCom.addTab("Forma de Retiro", panForRet);
        
        //********************************
        
        
        //Nombres de los tabs
       
        
        tabOrdCom.setTitleAt(0,"General");
        tabOrdCom.setTitleAt(1,"Forma de Pago");
        tabOrdCom.setTitleAt(2,"Asiento de Diario");
        objTooBar = new mitoolbar(this);
          
        vecForPag = new java.util.Vector();
        this.getContentPane().add(objTooBar,"South");
        pack();
        
        cargarTipEmp(CONN_GLO);
        getPorDesOC(CONN_GLO);
       
        /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
            NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
      
        
        /* 
         * Listener para detectar cambios en combo de pagos
         */
       objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                            objZafParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);      
       
         cboForPag.addItem("< Refrescar >");
        
         ActLisForPag objActLis = new ActLisForPag();
         cboForPag.addActionListener(objActLis);
        
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
        objUti.verDecimalesEnTabla(tblOrdComForPag,4, objZafParSis.getDecimalesMostrar());
        addListenerCambio();

        /* Clase ke agrega numeracion a el JTable
         */
         Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblOrdComForPag,0);         
        
         FormaRetencion(CONN_GLO);
         CargarBodegas(CONN_GLO);
         
         Cerrar_Conexion();
         
         objUti.desactivarCom(this);
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }
    }

    

    
    
     public ZafCom02(Librerias.ZafParSis.ZafParSis obj, Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, Integer intCodDoc ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        jfrThis = this;
        initComponents();
        this.setTitle(objZafParSis.getNombreMenu()+strVersion);
        objUti = new ZafUtil();
        objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);

             strCodEmpOC= String.valueOf( intCodEmp.intValue() );
             strCodLocOC= String.valueOf( intCodLoc.intValue() );;
             strCodTipOC= String.valueOf( intCodTipDoc.intValue() );;
             strCodDocOC= String.valueOf( intCodDoc.intValue() );;
             blnEstCarOC=true;

         //***********************************************************************
             //  objAsiDia=new ZafAsiDia(objParSis);
               objDiario.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                   @Override
                 public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objDiario.setCodigoTipoDocumento(-1);
                    else
                        objDiario.setCodigoTipoDocumento(Integer.parseInt( strCodTipOC ));
                  }
               });
            //***********************************************************************


           


            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;


       /* Iniciando clase de informacion de cliente*/
        objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);
        objCiudad_dat  = new Librerias.ZafUtil.ZafCiudad_dat(objZafParSis);
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
        //objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);

        tabOrdCom.addTab("Diario",objDiario);


        objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
        txtCodTipDoc.setVisible(false);


        Abrir_Conexion();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */

        cargaTipoDocPredeterminado();


        intCodMnu = objZafParSis.getCodigoMenu();
        intNumDec = 2;  //objZafParSis.getDecimalesMostrar();
        strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText(strFecSis);
        panCotGenNor.add(txtFecDoc);
        txtFecDoc.setBounds(530, 2, 92, 20);
        txtFecDoc.setEnabled(false);


           //********************************
         panForRet.setLayout(null);

        panForRet.add(txtDes);
        txtDes.setBounds(130, 20, 220, 21);

        lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblForRet.setText("Forma de Retiro:");
        panForRet.add(lblForRet);
        lblForRet.setBounds(20, 20, 100, 15);

        lblvehRet.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblvehRet.setText("Vehiculo de Retiro:");
        panForRet.add(lblvehRet);
        lblvehRet.setBounds(20, 45, 100, 15);

        lblChoret.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblChoret.setText("Chofer de Retiro:");
        panForRet.add(lblChoret);
        lblChoret.setBounds(20, 75, 100, 15);

        panForRet.add(txtChoRet);
        txtChoRet.setBounds(130, 70, 220, 21);

        panForRet.add(txtVehRet);
        txtVehRet.setBounds(130, 45, 220, 21);

        txtCod.setEditable(false);
        //panForRet.add(txtCod);
        txtCod.setBounds(130, 20, 40, 21);

         panForRet.add(butForret);


        butForret.setText("...");
        butForret.setPreferredSize(new java.awt.Dimension(35, 30));
        butForret.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              BuscarFormaRetiro();
            }
        });
         butForret.setBounds(350, 20, 22, 20);

         tabOrdCom.addTab("Forma de Retiro", panForRet);

        //********************************


        //Nombres de los tabs


        tabOrdCom.setTitleAt(0,"General");
        tabOrdCom.setTitleAt(1,"Forma de Pago");
        tabOrdCom.setTitleAt(2,"Asiento de Diario");
        objTooBar = new mitoolbar(this);

        vecForPag = new java.util.Vector();
       // this.getContentPane().add(objTooBar,"South");
        pack();

        cargarTipEmp(CONN_GLO);

        /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
            NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));


        /*
         * Listener para detectar cambios en combo de pagos
         */
       objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                            objZafParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);

         cboForPag.addItem("< Refrescar >");

         ActLisForPag objActLis = new ActLisForPag();
         cboForPag.addActionListener(objActLis);

        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
        objUti.verDecimalesEnTabla(tblOrdComForPag,4, objZafParSis.getDecimalesMostrar());
        addListenerCambio();

        /* Clase ke agrega numeracion a el JTable
         */
         Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblOrdComForPag,0);

         FormaRetencion(CONN_GLO);
         CargarBodegas(CONN_GLO);

         Cerrar_Conexion();

         objUti.desactivarCom(this);
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }
    }


    /**
     * Constructor utilizado como HashMap para usarlo cuando se llama a la clase
     * en forma dinamica.
     *
     * @author TonySanginez
     * @fecha 15/Jul/2016
     * @param map
     */
    public ZafCom02(HashMap map) 
    {
        Librerias.ZafParSis.ZafParSis obj;
        Integer intCodEmp = null;
        Integer intCodLoc = null;
        Integer intCodTipDoc = null;
        Integer intCodDoc = null;
        try 
        {
            obj = (ZafParSis) map.get("objParSis");
            intCodEmp = Integer.valueOf(new String(map.get("strCodEmp").toString()));
            intCodLoc = Integer.valueOf(new String(map.get("strCodLoc").toString()));
            intCodTipDoc = Integer.valueOf(new String(map.get("strCodTipDoc").toString()));
            intCodDoc = Integer.valueOf(new String(map.get("strCodDoc").toString()));
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu() + strVersion);
            objUti = new ZafUtil();
            objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);

            strCodEmpOC = String.valueOf(intCodEmp.intValue());
            strCodLocOC = String.valueOf(intCodLoc.intValue());;
            strCodTipOC = String.valueOf(intCodTipDoc.intValue());;
            strCodDocOC = String.valueOf(intCodDoc.intValue());;
            blnEstCarOC = true;

         //***********************************************************************
            //  objAsiDia=new ZafAsiDia(objParSis);
            objDiario.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                @Override
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals("")) {
                        objDiario.setCodigoTipoDocumento(-1);
                    } else {
                        objDiario.setCodigoTipoDocumento(Integer.parseInt(strCodTipOC));
                    }
                }
            });
            //***********************************************************************

            objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
            INTCODREGCEN = objObtConCen.intCodReg;


            /* Iniciando clase de informacion de cliente*/
            objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);
            objCiudad_dat = new Librerias.ZafUtil.ZafCiudad_dat(objZafParSis);
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
            //objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);

            tabOrdCom.addTab("Diario", objDiario);

            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
            txtCodTipDoc.setVisible(false);

            Abrir_Conexion();  /* REALIZA LA CONEXION CON LA BASE DE DATOS GLOBAL  */

            
            //cargarCabTipoDoc(intCodTipDoc);

            intCodMnu = objZafParSis.getCodigoMenu();
            intNumDec = 2;  //objZafParSis.getDecimalesMostrar();
            strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaHoraBaseDatos());
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) jfrThis.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText(strFecSis);
            panCotGenNor.add(txtFecDoc);
            txtFecDoc.setBounds(530, 2, 92, 20);
            txtFecDoc.setEnabled(false);

            //********************************
            panForRet.setLayout(null);

            panForRet.add(txtDes);
            txtDes.setBounds(130, 20, 220, 21);

            lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblForRet.setText("Forma de Retiro:");
            panForRet.add(lblForRet);
            lblForRet.setBounds(20, 20, 100, 15);

            lblvehRet.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblvehRet.setText("Vehiculo de Retiro:");
            panForRet.add(lblvehRet);
            lblvehRet.setBounds(20, 45, 100, 15);

            lblChoret.setFont(new java.awt.Font("SansSerif", 0, 11));
            lblChoret.setText("Chofer de Retiro:");
            panForRet.add(lblChoret);
            lblChoret.setBounds(20, 75, 100, 15);

            panForRet.add(txtChoRet);
            txtChoRet.setBounds(130, 70, 220, 21);

            panForRet.add(txtVehRet);
            txtVehRet.setBounds(130, 45, 220, 21);

            txtCod.setEditable(false);
            //panForRet.add(txtCod);
            txtCod.setBounds(130, 20, 40, 21);

            panForRet.add(butForret);

            butForret.setText("...");
            butForret.setPreferredSize(new java.awt.Dimension(35, 30));
            butForret.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    BuscarFormaRetiro();
                }
            });
            butForret.setBounds(350, 20, 22, 20);

            tabOrdCom.addTab("Forma de Retiro", panForRet);

        //********************************
        //Nombres de los tabs
            tabOrdCom.setTitleAt(0, "General");
            tabOrdCom.setTitleAt(1, "Forma de Pago");
            tabOrdCom.setTitleAt(2, "Asiento de Diario");
            objTooBar = new mitoolbar(this);

            vecForPag = new java.util.Vector();
            // this.getContentPane().add(objTooBar,"South");
            pack();

            cargarTipEmp(CONN_GLO);

            /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
             NO se altere el formulario y se vea bonito  */
            spnObs1.setPreferredSize(new java.awt.Dimension(350, 30));


            /*
             * Listener para detectar cambios en combo de pagos
             */
            objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);

            cboForPag.addItem("< Refrescar >");

            ActLisForPag objActLis = new ActLisForPag();
            cboForPag.addActionListener(objActLis);

            /* Metodo para agregar o eliminar lineas con enter y con escape
             */
            objUti.verDecimalesEnTabla(tblOrdComForPag, 4, objZafParSis.getDecimalesMostrar());
            addListenerCambio();

            /* Clase ke agrega numeracion a el JTable
             */
            Librerias.ZafColNumerada.ZafColNumerada ObjNumeradorCotForPag = new Librerias.ZafColNumerada.ZafColNumerada(tblOrdComForPag, 0);

            FormaRetencion(CONN_GLO);
            CargarBodegas(CONN_GLO);

            Cerrar_Conexion();

            objUti.desactivarCom(this);
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
     
     
     
     
     
     
     
     
     
   //************************//*************************************
     
     
     
     
     
     
     
     
     public boolean abrirConRem(){
        boolean blnres=false;
        try{
            int intIndEmp=INTCODREGCEN;
            if(intIndEmp != 0){
                conRemGlo=DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
                conRemGlo.setAutoCommit(false);
            }
            blnres=true;
        }
        catch (java.sql.SQLException e) {
            mostrarMsg("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            INTVERCONCEN=1;
            return false;
        }
        return blnres;
    }
    
    private void mostrarMsg(String strMsg) {
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.OK_OPTION);
    }
    
      
   
/**
 * obtiene el correo electronico al cual tiene que ser enviado.
 * @return
 */
private boolean _getCorEleToSistemas(){
 boolean blnRes=false;
 java.sql.Connection  conn;
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 String strSql="";
 try{
   conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
   if(conn != null ){
       stm=conn.createStatement();

      strSql="select a1.tx_corele  from tbr_bodloc as a " +
      " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod )  " +
      " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.st_reg='P'   ";
      rst=stm.executeQuery(strSql);
      if(rst.next()){
          //System.out.println("CorEle: "+rst.getString("tx_corEle")  );
          strCorEleTo=rst.getString("tx_corEle");
      }
      rst.close();
      rst=null;
      stm.close();
      stm=null;
   conn.close();
   conn=null;
 }}catch(java.sql.SQLException e)  {  blnRes=false;  System.out.println(""+e );    }
   catch(Exception Evt)  { blnRes=false;   System.out.println(""+Evt ); }
  return blnRes;
}
 
  
    
    //************************//*************************************
    
     public ZafCom02(Librerias.ZafParSis.ZafParSis objZafParSis,int CodEmp,int CodLoc,int CodCot) {
           this(objZafParSis);
           intCodEmp = CodEmp;
           intCodLoc = CodLoc;
           intCodCot= CodCot;
           intValBus = 1;
     }
    
   //************************//*************************************
    
    //************************//*************************************

     public ZafCom02(Librerias.ZafParSis.ZafParSis objZafParSis,int CodEmp,int CodLoc, int intCodTpDoc, int CodDoc, int intCodMnuOC ) {
           this(objZafParSis);
           intCodEmp = CodEmp;
           intCodLoc = CodLoc;
           intCodTipDocOc=intCodTpDoc;
           intCodDocOc= CodDoc;
           intCodMnu=intCodMnuOC;
           intValBus = 2;
     }

   //************************//*************************************

     
     
     
     
    
      public void Abrir_Conexion(){
        try{
            //System.out.println("ABRIR CONEXION...."); 
            CONN_GLO=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());          
           }
           catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
     }
      
     public void Cerrar_Conexion(){
        try{
           ///System.out.println("CERRANDO CONEXION....");
            CONN_GLO.close();
            CONN_GLO=null;  //=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());          
           }
           catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    }
    
  
      

int intCodMotBien=0;
int intCodMotServ=0;
int intCodMotTran=0;

public boolean FormaRetencion(java.sql.Connection conTmp){
 boolean blnRes=false;
 java.sql.Statement stmTmp;
 java.sql.ResultSet rst;
 try{
   if (conTmp!=null)
   {
      stmTmp = conTmp.createStatement();
      String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND tx_tipmot in ('B','S','T')";
      rst=stmTmp.executeQuery(sql);
      while(rst.next()){
         
          if(rst.getString("tx_tipmot").equals("B")) intCodMotBien= rst.getInt("co_mot");
          else if(rst.getString("tx_tipmot").equals("S")) intCodMotServ= rst.getInt("co_mot");
          else if(rst.getString("tx_tipmot").equals("T")) intCodMotTran= rst.getInt("co_mot");

      }

      rst.close();
      stmTmp.close();
      rst=null;
      stmTmp=null;
      blnRes=true;
  }
 }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
  catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
 return blnRes;
}


        
     
    
    public void CargarBodegas(java.sql.Connection conTmp){
         java.sql.Statement stmTmp;
         java.sql.ResultSet rst;
         try
          {
           if (conTmp!=null)
           {
              stmTmp = conTmp.createStatement();
              String sql = "SELECT distinct(co_bod) FROM tbr_bodLoc WHERE co_emp="+objZafParSis.getCodigoEmpresa(); 
              rst=stmTmp.executeQuery(sql);
              while(rst.next()){
                arreBod.add(""+rst.getInt(1));    
              }
              
              rst.close();
              stmTmp.close();
              rst=null;
              stmTmp=null;
          }
       }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
       
    }
    
    
    
      
   public void BuscarFormaRetiro(){
                Compras.ZafCom02.ZafCom02_01 obj = new  Compras.ZafCom02.ZafCom02_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
                obj.show();  
                if(obj.acepta()){ 
                      txtCod.setText(obj.GetCamSel(1));
                      txtDes.setText(obj.GetCamSel(2));
                 }     
   }
    
   
   public void Configura_ventana_consulta(){
        configurarVenConProducto();
        configurarVenConTipDoc();
        configurarVenConProveedor();
        configurarVenConVendedor();
        
         objDiario.setEditable(false);

           _getCorEleToSistemas();

     if(blnEstCarOC){

       cargarDatos( strCodEmpOC, strCodLocOC, strCodTipOC, strCodDocOC  );

      }

    }

private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc ){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
  
       cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
       cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
       
      conn.close();
      conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}



private boolean cargarCabReg(java.sql.Connection con, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc)
{

    boolean blnRes=true;
    try
    {
        if (con!=null)
        {
            java.sql.Statement stm=con.createStatement();
            String strSQL="";


          strSQL= "SELECT a.co_cli, a.tx_nomcli as nomcli, a.tx_dircli as dircli,  a.co_com , a.tx_nomven as nomcom, "+
          "a.co_doc, a.fe_doc, a.tx_ate,  a.co_forPag,  a.tx_obs1, a.tx_obs2, a.nd_sub, a.nd_porIva, a.nd_tot, a.st_reg, a.ne_numcot as num_cot,"+
          "a.ne_numdoc "+
          "as num_doc, a.tx_numDoc2,  a.tx_numped as num_ped, a.co_tipdoc, a.co_forret,b.tx_deslar,a.tx_vehret,a.tx_choret,a.ne_secemp  "+
          " ,a.st_regrep ,cli.tx_tipper ,cli.co_tipper, cli.st_ivacom " +
          " , a1.tx_descor, a1.tx_deslar as nomtipdoc  " +
          " FROM tbm_cabMovInv as a " +
          " LEFT OUTER JOIN tbm_var as b ON (b.co_reg=a.co_forret)"+
          " INNER JOIN tbm_cabtipdoc as a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )"+
          " inner join tbm_cli as cli on(cli.co_emp=a.co_emp and cli.co_cli=a.co_cli) " +
          "where  a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipDoc="+ intCodTipDoc+" AND "+
          " a.st_reg not in('E') and  a.co_doc="+intCodDoc;

            java.sql.ResultSet rst=stm.executeQuery(strSQL);


            if (rst.next())
            {

              Glo_intCodSec=rst.getInt("ne_secemp");

                intNumDoc = rst.getInt("co_doc");
                txtDoc.setText(""+intNumDoc);


                txtNomTipDoc.setText(((rst.getString("tx_descor")==null)?"":rst.getString("tx_descor")));
                txtDetTipDoc.setText(((rst.getString("nomtipdoc")==null)?"":rst.getString("nomtipdoc")));


                txtPrvCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                GLOCODCLI=txtPrvCod.getText();
                txtPrvNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                txtPrvDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                txtCot.setText(((rst.getString("num_cot")==null)?"":rst.getString("num_cot")));
                txtOrdCom.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                txtNumPed.setText(((rst.getString("tx_numdoc2")==null)?"":rst.getString("tx_numdoc2")));
                txtFacPrv.setText(((rst.getString("num_ped")==null)?"":rst.getString("num_ped")));

                STR_ESTREG=rst.getString("st_regrep");
                strTipPer_glo=rst.getString("tx_tipper");
                strCodTipPerCli=((rst.getString("co_tipper")==null)?"":rst.getString("co_tipper"));


                txtCod.setText(((rst.getString("co_forret")==null)?"":rst.getString("co_forret")));
                txtDes.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                txtVehRet.setText(((rst.getString("tx_vehret")==null)?"":rst.getString("tx_vehret")));
                txtChoRet.setText(((rst.getString("tx_choret")==null)?"":rst.getString("tx_choret")));

                stIvaCom=rst.getString("st_ivacom");

               // cargarCabTipoDoc(rst.getInt("co_tipdoc"));

                if(rst.getDate("fe_doc")==null){
                  txtFecDoc.setText("");
                }else{
                    java.util.Date dateObj = rst.getDate("fe_doc");
                    java.util.Calendar calObj = java.util.Calendar.getInstance();
                    calObj.setTime(dateObj);
                    txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                      calObj.get(java.util.Calendar.MONTH)+1     ,
                                      calObj.get(java.util.Calendar.YEAR)        );
                }


                txtComCod.setText(((rst.getString("co_com")==null)?"":rst.getString("co_com")));
                txtComNom.setText(((rst.getString("nomcom")==null)?"":rst.getString("nomcom")));
                txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));

                double dblSub = ((rst.getString("nd_sub")==null)?0:objUti.redondear(rst.getDouble("nd_sub"),objZafParSis.getDecimalesMostrar()));



                //Pie de pagina

                txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));

                lblCotNumDes.setText("Orden de Compra No. " + txtOrdCom.getText() +  " (" + txtPrvNom.getText() + ") ");
                lblIva.setText("IVA " + dblPorIva + "%");
                /*
                 * CARGANDO DATOS DEL TAB FORMA DE PAGO
                 */



                String strCo_ForPag = (rst.getString("co_forPag")==null)?"":rst.getString("co_forPag") ;
                cboForPag.setSelectedIndex(vecForPag.indexOf(strCo_ForPag));


                //refrescaPagos(intNumDoc);





              txtSub.setText(""+dblSub);






            }


            rst.close();
            stm.close();
            rst=null;
            stm=null;
            //Mostrar la posición relativa del registro.
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


public void  cargarDetReg(java.sql.Connection con, String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc){
 try{//odbc,usuario,password

        String strAux2 =" , 'S' as proconf ";

        String strSer="";
        //Extrayendo los datos del detalle respectivo a esta orden de compra
        sSQL  = "Select a.co_itm,a.tx_codalt,a.tx_codalt2,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
        " ,b.st_ser , a.co_reg , a.co_itmact, a.st_meringegrfisbod, a.nd_preunivenlis, a.nd_pordesvenmax  " +
        " "+strAux2+" FROM tbm_detMovInv AS a" +
        " INNER JOIN tbm_inv AS b ON(a.co_emp=b.co_emp and a.co_itm=b.co_itm) WHERE " +
        " a.co_emp = " + intCodEmp + " and " +
        " a.co_loc = " + intCodLoc  + " and " +
        " a.co_tipdoc = " +intCodTipDoc  + " and " +
        " a.co_doc = " + intCodDoc  + " ORDER BY a.co_reg";

        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rst= stm.executeQuery(sSQL);
         double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;

         java.util.Vector vecData = new java.util.Vector();
         while (rst.next()){
               strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));

             java.util.Vector vecReg = new java.util.Vector();
             vecReg.add(INT_TBL_LINEA, "");
             vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
             vecReg.add(INT_TBL_BUTITM, "");
             vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
             vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
             vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
             vecReg.add(INT_TBL_BUTBOD, "");
             vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_can")));
             vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));
             vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_cosuni")));
             vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
             String strIva = rst.getString("st_ivacom");
             Boolean blnIva = new Boolean(strIva.equals("S")?true:false);
             vecReg.add(INT_TBL_BLNIVA, blnIva);
             dblCan    = rst.getDouble("nd_can");
             dblPre    = rst.getDouble("nd_cosuni");
             dblPorDes = rst.getDouble("nd_pordes");
             dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
//             dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;

             dblTotal = (dblCan * dblPre) - dblValDes ;
             dblTotal = objUti.redondear(dblTotal, 3);
             dblTotal = objUti.redondear(dblTotal, objZafParSis.getDecimalesMostrar());
             
             if (blnIva.booleanValue()){
                dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objZafParSis.getDecimalesMostrar()) ;
             }

             vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
             vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
             vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
             vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
             vecReg.add(INT_TBL_ESTADO, "E");
             vecReg.add(INT_TBL_IVATXT, "");
             vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));
             vecReg.add(INT_TBL_ITMSER, strSer );
             vecReg.add(INT_TBL_CODREG, rst.getString("co_reg") );
             vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact"));
             vecReg.add(INT_TBL_IEBODFIS, rst.getString("st_meringegrfisbod"));
             vecReg.add(INT_TBL_COSUNIORI, rst.getString("nd_preunivenlis"));
             vecReg.add(INT_TBL_PORDESORI, rst.getString("nd_pordesvenmax"));
             vecReg.add(INT_TBL_PRECOSRES, rst.getString("nd_cosuni"));
             vecReg.add(INT_TBL_PORDESRES, rst.getString("nd_pordes"));

             vecData.add(vecReg);
         }
         objTblMod.setData(vecData);
         tblDat.setModel(objTblMod);

         calculaTotal();


        objDiario.consultarDiarioCompleto( Integer.parseInt( intCodEmp ), Integer.parseInt( intCodLoc ), Integer.parseInt( intCodTipDoc ), Integer.parseInt( intCodDoc )  );


        stm.close();
        rst.close();

            

   }//fin Try
   catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt) {    objUti.mostrarMsgErr_F1(jfrThis, Evt); }
}






     private boolean configurarVenConVendedor()
      {
        boolean blnRes=true;
        try
        {
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
             String  strSQL="";
             strSQL+="select a.co_usr,a.tx_nom from tbm_usr as a   WHERE a.st_reg NOT IN('I')  order by a.tx_nom"; 
              
            objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
   
    
     
       
     private boolean configurarVenConProveedor()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_tipper");
            arlCam.add("a.co_tipper");
            arlCam.add("a.st_ivacom");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("TIPO");
            arlAli.add("CO_TIPO");
            arlAli.add("Iva.Com");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("20");
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
            
             
            // strSQL+="select co_cli,tx_nom,tx_dir,tx_tel,tx_ide, tx_tipper ,co_tipper  from tbm_cli as a where a.co_emp = " + objZafParSis.getCodigoEmpresa() +" and a.st_reg='A'  and a.st_prv='S' order by a.tx_nom"; 
            strSQL="SELECT a.co_cli,tx_nom,tx_dir,tx_tel,tx_ide, tx_tipper ,co_tipper, a.st_ivacom  FROM tbr_cliloc as a1 " +
            " INNER JOIN tbm_cli AS a on (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli) " +
            " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'  " +
            " and a.st_prv='S' " +
                   // " AND  a.co_cli IN ("+strCodEmp+") "+ 
             " order by a.tx_nom";
             

            objVenConPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
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
            
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
                  
           if(objZafParSis.getCodigoUsuario()==1){
              Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
              " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
              " where   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
              " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
              " b.co_mnu = " + objZafParSis.getCodigoMenu();
             }else {     
                    Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE "+
                    "  a1.co_emp=" + objZafParSis.getCodigoEmpresa()+""+
                    " AND a1.co_loc=" + objZafParSis.getCodigoLocal()+""+
                    " AND a1.co_mnu=" + objZafParSis.getCodigoMenu()+""+
                    " AND a1.co_usr=" + objZafParSis.getCodigoUsuario();
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
    
      private boolean configurarVenConProducto()
    {
        boolean blnRes=true;
        try
        {
             //********************************************************************************** 
              
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.tx_codAlt");
            arlCam.add("a7.co_itm");
            arlCam.add("a7.tx_nomItm");
            arlCam.add("a7.nd_stkAct");
            arlCam.add("a7.nd_CosUni");
            arlCam.add("a7.st_ivaCom");
            arlCam.add("a7.tx_descor");
            arlCam.add("a7.tx_codAlt2");
            arlCam.add("a7.st_ser");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cód.Sis.");
            arlAli.add("Nombre");
            arlAli.add("Stock");
            arlAli.add("Costo");
            arlAli.add("Iva.");
            arlAli.add("Uni.");
            arlAli.add("Código2");
            arlAli.add("Itm.Ser."); 
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("210");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("35");
            arlAncCol.add("40");
            arlAncCol.add("64");
            arlAncCol.add("20");
            
              
            int co_tipdoc=1; 
                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";
            
                String Str_Sql="";
                Str_Sql=" SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser " +
                " FROM ( " +
                "        SELECT d1.tx_codAlt, d1.co_itm, d1.tx_nomItm,  d3.nd_stkAct, d2.nd_cosUni, d1.st_ivacom, d4.tx_desCor, d1.tx_codAlt2,d1.st_ser ";
                //Str_Sql+=strAux; 
                Str_Sql+=" FROM ( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_uni, a1.st_ivacom,a1.st_ser";
                Str_Sql+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                Str_Sql+=" WHERE a1.st_reg not in('T','I','E','U') and  a1.co_emp="+objZafParSis.getCodigoEmpresa()+" ) AS d1";
                Str_Sql+=" INNER JOIN ( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1";
                Str_Sql+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                Str_Sql+=" WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae)";
                Str_Sql+=" INNER JOIN (SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct";
                Str_Sql+=" FROM tbm_equInv AS c1 INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                Str_Sql+=" INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)";
                Str_Sql+=" WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae";
                Str_Sql+=" ) AS d3 ON (d1.co_itmMae=d3.co_itmMae)";
                Str_Sql+=" LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg) " +
                " )  AS x WHERE                    upper(tx_codAlt) like '%S'" +
              
//                " CASE WHEN (" +
//                " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
//                " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
//                " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
                    " ORDER BY tx_codAlt";
                   
              
             
                //System.out.println(">> "+ Str_Sql );
                
              
             //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=7;
            intColOcu[1]=9;
              
            objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenCon2.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            objVenCon2.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
            
         
        }catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
   
   
   private boolean Configurar_tabla() {
        boolean blnRes=false;
        try{
            
                txtCod.setBackground(objZafParSis.getColorCamposObligatorios());
                txtDes.setBackground(objZafParSis.getColorCamposObligatorios());
            
              
            
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
            vecCab.add(INT_TBL_CANORI,"");
            vecCab.add(INT_TBL_CANMOV,"Cantidad");
            vecCab.add(INT_TBL_PRECOS,"Costo");            
            vecCab.add(INT_TBL_PORDES,"Descuento");
            vecCab.add(INT_TBL_BLNIVA,"IVA");
            vecCab.add(INT_TBL_TOTAL,"Total");            
            vecCab.add(INT_TBL_CODITM,"");
            vecCab.add(INT_TBL_ITMORI,"");
            vecCab.add(INT_TBL_BODORI,"");
            vecCab.add(INT_TBL_ESTADO,"");
            vecCab.add(INT_TBL_IVATXT,"");
            vecCab.add(INT_TBL_ITMALT2,"Cod.Item2");
            vecCab.add(INT_TBL_ITMSER,"Ser.");
            vecCab.add(INT_TBL_CODREG , "CodReg");
            vecCab.add(INT_TBL_CODITMACT, "CodAct");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod"); 
            vecCab.add(INT_TBL_COSUNIORI, "CosUniOri");
            vecCab.add(INT_TBL_PORDESORI, "PorDesOri");
            vecCab.add(INT_TBL_PRECOSRES, "CosUniRes");
            vecCab.add(INT_TBL_PORDESRES, "PorDesRes");

            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_CANORI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PRECOS, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_ITMALT);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer las columnas eleminadas
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_CODITM);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            arlAux.add("" + INT_TBL_ITMORI);
            arlAux.add("" + INT_TBL_BODORI);
            arlAux.add("" + INT_TBL_CANORI);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
                  
             
            //Configurar JTable: Establecer el menú de contexto.
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer el ancho de las columnas.
            
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(145);
            tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_BUTBOD).setPreferredWidth(10);         
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_PRECOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(80);         
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);         
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(80);     
                     
            
             /* Aqui se agrega las columnas que van
                 ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_ITMSER);
            arlColHid.add(""+INT_TBL_IEBODFIS);
            arlColHid.add(""+INT_TBL_CODITM);
            arlColHid.add(""+INT_TBL_IVATXT);
            arlColHid.add(""+INT_TBL_CANORI);
            arlColHid.add(""+INT_TBL_ITMORI);
            arlColHid.add(""+INT_TBL_BODORI);
            arlColHid.add(""+INT_TBL_ESTADO);
            arlColHid.add(""+INT_TBL_ITMALT2);
            arlColHid.add(""+INT_TBL_BUTITM);
            arlColHid.add(""+INT_TBL_CODREG);
            arlColHid.add(""+INT_TBL_CODITMACT);
            arlColHid.add(""+INT_TBL_COSUNIORI);
            arlColHid.add(""+INT_TBL_PORDESORI);
            arlColHid.add(""+INT_TBL_PRECOSRES);
            arlColHid.add(""+INT_TBL_PORDESRES);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;

            
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
          
              vecAux.add("" + INT_TBL_DESITM);
              vecAux.add("" + INT_TBL_PRECOS);
              vecAux.add("" + INT_TBL_PORDES);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    blnChangeData = false;        
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null){
                                tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V")){
                            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)!=null)
                                tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("N")){
        	            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null)
                                tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("D")){
        	            tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                        }
                                
                        if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_BLNIVA)!=null){
                            calculaSubtotal();
                            blnChangeData = true;
                        }                        
                }
            });


            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_UNIDAD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                       
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANORI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PRECOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            String strSQL="";            
            strSQL= " SELECT distinct(a1.co_bod), a1.tx_nom, a2.nd_stkAct FROM tbm_bod AS a1 right outer join tbr_bodLoc as bodloc on ( a1.co_emp = bodloc.co_emp and a1.co_bod = bodloc.co_bod) RIGHT OUTER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)"+
                    " WHERE a2.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a1.st_reg='A'";            
            int intColVenBod[]=new int[1];
            intColVenBod[0]=1;
            int intColTblBod[]=new int[1];
            intColTblBod[0]=INT_TBL_CODBOD;
            objTblCelEdiTxtCon2=new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon("Listado Bodega", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL,  "a1.co_bod, a1.tx_nom,a2.nd_stkAct","Codigo,Nombre,Stock", intColVenBod, intColTblBod);
            objTblCelEdiTxtCon2.setIndiceCampoBusqueda(0);
            objTblCelEdiTxtCon2.setCampoBusqueda("a1.co_bod", ZafTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtCon2.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellEditor(objTblCelEdiTxtCon2);            
            objTblCelEdiTxtCon2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM).toString();
                    objTblCelEdiTxtCon2.setCondicionesSQL(" AND a2.co_itm=" + strAux + "");
                }
                @Override
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (!objTblCelEdiTxtCon2.isConsultaAceptada()){
                    }
                }                
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strBeforeValue ="";
 
                }
                    
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strAfterValue ="";
                    if (!strBeforeValue.equals(strAfterValue))
                        generaAsiento();
                }
            });
            
            objTblCelEdiBut2=new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut("Listado Bodega", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom,a2.nd_stkAct","Codigo,Nombre,Stock", intColVenBod, intColTblBod);
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellEditor(objTblCelEdiBut2);
            objTblCelEdiBut2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM).toString();
                    objTblCelEdiBut2.setCondicionesSQL(" AND a2.co_itm=" + strAux + "");
                    
                }
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strBeforeValue ="";
 
                }                    
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strAfterValue ="";
                    if (!strBeforeValue.equals(strAfterValue))
                        generaAsiento();
                }
            });
            intColVenBod=null;
            intColTblBod=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
          
  
            int intColVen2[]=new int[8];
            intColVen2[0]=1;
            intColVen2[1]=3;
            intColVen2[2]=5;
            intColVen2[3]=2;            
            intColVen2[4]=6;          
            intColVen2[5]=7;         
            intColVen2[6]=8;
            intColVen2[7]=9;
            int intColTbl2[]=new int[8];
            intColTbl2[0]=INT_TBL_ITMALT;
            intColTbl2[1]=INT_TBL_DESITM;
            intColTbl2[2]=INT_TBL_PRECOS;
            intColTbl2[3]=INT_TBL_CODITM;
            intColTbl2[4]=INT_TBL_IVATXT;
            intColTbl2[5]=INT_TBL_UNIDAD;
            intColTbl2[6]=INT_TBL_ITMALT2;
            intColTbl2[7]=INT_TBL_ITMSER;
            
              objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, objVenCon2, intColVen2, intColTbl2);  //********
              tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);  //******
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   //******  
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    blnChangeData = false;                            
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else 
			strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else
                        strAfterValue ="";
                    if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null){
                            tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }
                    
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_IVATXT)!=null){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_IVATXT).equals("S"))
                            tblDat.setValueAt(true, tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                        else
                            tblDat.setValueAt(false, tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                    }
                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;        
                       tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), tblDat.getSelectedRow(), INT_TBL_CODBOD);
                        calculaSubtotal();
                    }                        
                }
            });

               ButFndItm ObjFndItm = new ButFndItm(tblDat, INT_TBL_BUTITM);   //*****
            
///////////////////////////////////////////////////////////////////***********************//////////////////////////////////

            
            
            
            intColVen2=null;
            intColTbl2=null;
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PRECOS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PRECOS).setCellRenderer(new RenderDecimales(intNumDec) );            
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

                    blnChangeData = false;
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).toString();
                    else
                        strAfterValue ="";
                    if ((tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null){
                            tblDat.setValueAt("D", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", tblDat.getSelectedRow(), INT_TBL_ESTADO);                            
                    }
                           
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).equals(""))
                        tblDat.setValueAt("0",tblDat.getSelectedRow(),tblDat.getSelectedColumn());
                    if (tblDat.getSelectedColumn()==INT_TBL_PORDES){
                        if(strAfterValue.equals("")) strAfterValue="0.00";
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            MensajeInf("Error! Porcentaje de Descuento ");
                            tblDat.setValueAt(strBeforeValue, tblDat.getSelectedRow(), INT_TBL_PORDES);
                        }

                       if(!getVerificaMarPor(tblDat.getSelectedRow())){
                           MensajeInf("Ha sobrepasado el porcentaje establecido del "+dblPorDesOc+"%  ");
                           tblDat.setValueAt( tblDat.getValueAt(  tblDat.getSelectedRow(), INT_TBL_PORDESRES), tblDat.getSelectedRow(), INT_TBL_PORDES);
                        }
                    }
                    

                    if (tblDat.getSelectedColumn()==INT_TBL_PRECOS){
                        if(!getVerificaMarPor(tblDat.getSelectedRow())){
                           MensajeInf("Ha sobrepasado el porcentaje establecido del "+dblPorDesOc+"%  ");
                           tblDat.setValueAt( tblDat.getValueAt(  tblDat.getSelectedRow(), INT_TBL_PRECOSRES), tblDat.getSelectedRow(), INT_TBL_PRECOS);
                        }
                    }

                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true; 
                        calculaSubtotal();
                    }
                    
                    
                }
            });

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);

            //Libero los objetos auxiliares.
            tcmAux=null;
            setEditable(false);
            //Configurar JTable: Centrar columnas.
           
            ZafTblPopMn = new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            ZafTblPopMn.setEliminarFilaVisible(false);
            ZafTblPopMn.setInsertarFilasVisible(false);
            ZafTblPopMn.setInsertarFilaVisible(false);
                   
            
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;                        
    }    



/**
 * verifica que el total de la orden de compra no sobrepase el marguen  establecido del
 * 0.5 % + -  con el valor nuevo
 * @return true : si esta dentro del marguen
 * @return false : si ha sobrepasado el marguen
 *
 */
private boolean getVerificaMarPor(int intNumFilTbl ){
boolean blnRes=true;
double dblPorDesAct=0, dblPorDesOri=0;
double dblCosUniAct=0, dblCosUniOri=0;
double dblCosFinAct=0, dblCosFinOri=0;
double dblMarDes=0;
try{

 dblPorDesAct = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_PORDES)), 5 );
 dblPorDesOri = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_PORDESORI)), 5 );
 dblCosUniAct = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_PRECOS)), 5 );
 dblCosUniOri = objUti.redondear( getIntDatoValidado(tblDat.getValueAt( intNumFilTbl, INT_TBL_COSUNIORI)), 5 );

 dblCosFinAct = ( dblCosUniAct - ( dblCosUniAct * (dblPorDesAct/100) ) );
 dblCosFinAct = objUti.redondear( dblCosFinAct , 5 );

 dblCosFinOri = ( dblCosUniOri - ( dblCosUniOri * (dblPorDesOri/100) ) );
 dblCosFinOri = objUti.redondear( dblCosFinOri , 5 );

 dblMarDes =  dblCosFinAct - dblCosFinOri;
 dblMarDes = objUti.redondear( dblMarDes , 5 );
 dblMarDes = ( (dblMarDes*100)/dblCosFinOri);
 dblMarDes = objUti.redondear( dblMarDes , 2 );

 System.out.println( "--> "+dblMarDes +"  "+ dblPorDesOc);

 if(dblMarDes > 0 ){
  dblMarDes = Math.abs( dblMarDes );
  if( dblMarDes > dblPorDesOc )  blnRes=false;
 }

 }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}


   public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);  // INS
            
        }else{
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }       
       
   }
             
    private void cargaTipoDocPredeterminado()
    {
        objTipDoc.DocumentoPredeterminado_usr();
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomTipDoc.setText(objTipDoc.gettx_descor());
        txtDetTipDoc.setText(objTipDoc.gettx_deslar());
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();
        strTipIngEgr=objTipDoc.gettx_natdoc();  
        
         objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
        
        
        getIva();
    }


   


    private void cargarCabTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomTipDoc.setText(objTipDoc.gettx_descor());
        txtDetTipDoc.setText(objTipDoc.gettx_deslar());      
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();
        strTipIngEgr=objTipDoc.gettx_natdoc();  
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
    
         generaAsiento();
        
        getIva();
    }
    
    
    
    
    
      public void cargarTipEmp(java.sql.Connection conTipEmp){
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        try{
                if (conTipEmp!=null){
                    stmTipEmp=conTipEmp.createStatement();
                    
                    sSql="select b.co_tipper, b.tx_descor, round(a.nd_ivaCom,2) as porIva from tbm_emp as a " +  /*JoseMario 6/Jun/2016*/
                    " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
                    " where a.co_emp="+objZafParSis.getCodigoEmpresa();
                     
                    rstEmp = stmTipEmp.executeQuery(sSql);
                    if(rstEmp.next()){
                        strTipEmp=rstEmp.getString("tx_descor");
                        intCodTipPerEmp=rstEmp.getInt("co_tipper");

                         dblPorIva = rstEmp.getDouble("porIva");
                         lblIva.setText("IVA " + dblPorIva + "%");

                    }rstEmp.close();
                }
            }catch(SQLException Evt){
                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
            }catch(Exception Evt){
                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
            }    
    }


public void getPorDesOC(java.sql.Connection conTipEmp){
 Statement stmTipEmp;
 ResultSet rstEmp;
 String sSql;
 try{
        if (conTipEmp!=null){
            stmTipEmp=conTipEmp.createStatement();

            sSql="select nd_maxporvarperordcomfacprv from tbm_loc where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_loc="+objZafParSis.getCodigoLocal()+" ";
            rstEmp = stmTipEmp.executeQuery(sSql);
            if(rstEmp.next()){
              dblPorDesOc=rstEmp.getDouble("nd_maxporvarperordcomfacprv");
            }
            rstEmp.close();
            rstEmp=null;
 }}catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
}


      
    
//    public void cargarTipEmp(){
//        Connection conTipEmp;
//        Statement stmTipEmp;
//        ResultSet rstEmp;
//        String sSql;
//        
//        try{//odbc,usuario,password
//             conTipEmp=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                if (conTipEmp!=null){
//                    stmTipEmp=conTipEmp.createStatement();
//                    
//                    sSql="Select tx_tipper from tbm_emp " +
//                         "where " +
//                         "co_emp = " + objZafParSis.getCodigoEmpresa();
//
//                    rstEmp = stmTipEmp.executeQuery(sSql);
//                    if(rstEmp.next())
//                        strTipEmp=rstEmp.getString("tx_tipper");
//                    rstEmp.close();
//                }
//                conTipEmp.close();
//            }catch(SQLException Evt){
//                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
//            }catch(Exception Evt){
//                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
//            }    
//                    
//    }
    
    
   /*
    * Regresa si el tipo de documento es ingreso o egreso 
    * Retorna  un int  
    *   1  si es ingreso 
    *  -1  si es egreso  
    */
   public int getAccionDoc(){
        if (objTipDoc.gettx_natdoc().equals("I"))
            return 1;
        else
            return -1;
   }    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabOrdCom = new javax.swing.JTabbedPane();
        panCotGen = new javax.swing.JPanel();
        panCotGenNor = new javax.swing.JPanel();
        lblCom = new javax.swing.JLabel();
        lblFecDoc = new javax.swing.JLabel();
        txtAte = new javax.swing.JTextField();
        lblAte = new javax.swing.JLabel();
        txtComCod = new javax.swing.JTextField();
        txtComNom = new javax.swing.JTextField();
        butCom = new javax.swing.JButton();
        txtCot = new javax.swing.JTextField();
        lblNumCot = new javax.swing.JLabel();
        lblPro = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtPrvDir = new javax.swing.JTextField();
        txtPrvCod = new javax.swing.JTextField();
        txtPrvNom = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblTipDoc1 = new javax.swing.JLabel();
        txtNomTipDoc = new javax.swing.JTextField();
        txtDetTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblNumPed = new javax.swing.JLabel();
        txtFacPrv = new javax.swing.JTextField();
        lblNumDoc = new javax.swing.JLabel();
        txtDoc = new javax.swing.JTextField();
        lblOrdCom = new javax.swing.JLabel();
        txtOrdCom = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        chkEmiChq = new javax.swing.JCheckBox();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumPed = new javax.swing.JTextField();
        spnCon = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCotGenSur = new javax.swing.JPanel();
        panCotGenSurCen = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblObs2 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panCotGenSurEst = new javax.swing.JPanel();
        lblSubTot = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        panCotForPag = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblOrdComForPag = new javax.swing.JTable();
        panCotForPagNo = new javax.swing.JPanel();
        panCotForPagNorCen = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        cboForPag = new javax.swing.JComboBox();
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
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        tabOrdCom.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabOrdCom.setName("General"); // NOI18N

        panCotGen.setLayout(new java.awt.BorderLayout());

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 148));
        panCotGenNor.setLayout(null);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCom.setText("Comprador:");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNor.add(lblCom);
        lblCom.setBounds(408, 24, 70, 15);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNor.add(lblFecDoc);
        lblFecDoc.setBounds(408, 4, 108, 15);
        panCotGenNor.add(txtAte);
        txtAte.setBounds(516, 40, 144, 20);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblAte.setText("Atención:");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNor.add(lblAte);
        lblAte.setBounds(408, 44, 60, 15);

        txtComCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtComCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtComCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtComCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComCodActionPerformed(evt);
            }
        });
        txtComCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComCodFocusLost(evt);
            }
        });
        panCotGenNor.add(txtComCod);
        txtComCod.setBounds(480, 20, 35, 20);

        txtComNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtComNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtComNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComNomActionPerformed(evt);
            }
        });
        txtComNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComNomFocusLost(evt);
            }
        });
        panCotGenNor.add(txtComNom);
        txtComNom.setBounds(516, 20, 145, 20);

        butCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCom.setText("...");
        butCom.setPreferredSize(new java.awt.Dimension(20, 20));
        butCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butComActionPerformed(evt);
            }
        });
        panCotGenNor.add(butCom);
        butCom.setBounds(662, 20, 20, 20);

        txtCot.setMaximumSize(null);
        txtCot.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCotActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtCot);
        txtCot.setBounds(112, 24, 92, 20);

        lblNumCot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumCot.setText("No. Cotización:");
        panCotGenNor.add(lblNumCot);
        lblNumCot.setBounds(6, 26, 102, 15);

        lblPro.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPro.setText("Proveedor:");
        panCotGenNor.add(lblPro);
        lblPro.setBounds(6, 86, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDir.setText("Dirección:");
        panCotGenNor.add(lblDir);
        lblDir.setBounds(6, 106, 60, 15);

        txtPrvDir.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPrvDir.setPreferredSize(new java.awt.Dimension(70, 20));
        txtPrvDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvDirActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtPrvDir);
        txtPrvDir.setBounds(112, 104, 350, 20);

        txtPrvCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPrvCod.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPrvCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtPrvCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtPrvCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvCodActionPerformed(evt);
            }
        });
        txtPrvCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrvCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrvCodFocusLost(evt);
            }
        });
        panCotGenNor.add(txtPrvCod);
        txtPrvCod.setBounds(112, 84, 35, 20);

        txtPrvNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPrvNom.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPrvNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtPrvNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvNomActionPerformed(evt);
            }
        });
        txtPrvNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrvNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrvNomFocusLost(evt);
            }
        });
        panCotGenNor.add(txtPrvNom);
        txtPrvNom.setBounds(146, 84, 290, 20);

        butPrv.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butPrv.setText("...");
        butPrv.setPreferredSize(new java.awt.Dimension(20, 20));
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panCotGenNor.add(butPrv);
        butPrv.setBounds(440, 84, 22, 20);

        lblTipDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc1.setText("Tipo de documento:");
        panCotGenNor.add(lblTipDoc1);
        lblTipDoc1.setBounds(6, 8, 100, 15);

        txtNomTipDoc.setBackground(objZafParSis.getColorCamposObligatorios()
        );
        txtNomTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtNomTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        txtNomTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipDocActionPerformed(evt);
            }
        });
        txtNomTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTipDocFocusLost(evt);
            }
        });
        panCotGenNor.add(txtNomTipDoc);
        txtNomTipDoc.setBounds(112, 4, 63, 20);

        txtDetTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDetTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDetTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDetTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtDetTipDoc);
        txtDetTipDoc.setBounds(176, 4, 210, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(butTipDoc);
        butTipDoc.setBounds(386, 5, 20, 20);

        lblNumPed.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumPed.setText("No. Fac. Proveedor:");
        lblNumPed.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNor.add(lblNumPed);
        lblNumPed.setBounds(408, 64, 100, 15);

        txtFacPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFacPrvFocusLost(evt);
            }
        });
        panCotGenNor.add(txtFacPrv);
        txtFacPrv.setBounds(516, 60, 144, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc.setText("No. Pedido:");
        panCotGenNor.add(lblNumDoc);
        lblNumDoc.setBounds(214, 66, 66, 15);

        txtDoc.setBackground(objZafParSis.getColorCamposSistema()
        );
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtDoc);
        txtDoc.setBounds(112, 44, 92, 20);

        lblOrdCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblOrdCom.setText("No. Orden Compra:");
        panCotGenNor.add(lblOrdCom);
        lblOrdCom.setBounds(6, 66, 102, 15);

        txtOrdCom.setBackground(objZafParSis.getColorCamposSistema()
        );
        txtOrdCom.setMaximumSize(null);
        txtOrdCom.setPreferredSize(new java.awt.Dimension(70, 20));
        txtOrdCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdComActionPerformed(evt);
            }
        });
        txtOrdCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOrdComFocusLost(evt);
            }
        });
        txtOrdCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOrdComKeyPressed(evt);
            }
        });
        panCotGenNor.add(txtOrdCom);
        txtOrdCom.setBounds(112, 64, 92, 20);
        panCotGenNor.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 4, 20, 20);

        chkEmiChq.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        chkEmiChq.setText("Emitir cheque antes de recibir la factura del proveedor");
        panCotGenNor.add(chkEmiChq);
        chkEmiChq.setBounds(5, 126, 500, 20);

        lblNumDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumDoc1.setText("No. Documento:");
        panCotGenNor.add(lblNumDoc1);
        lblNumDoc1.setBounds(6, 46, 102, 15);

        txtNumPed.setBackground(objZafParSis.getColorCamposObligatorios()
        );
        txtNumPed.setMaximumSize(null);
        txtNumPed.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtNumPed);
        txtNumPed.setBounds(280, 64, 124, 20);

        panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "Codigo", "...", "Descripción", "Unidad", "Bodega", "...", "Cantidad", "Costo", "%Desc", "Iva", "Total", "Codigo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, true, true, true, true, true, false, false
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

        panCotGenSur.setLayout(new java.awt.BorderLayout());

        panCotGenSurCen.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs2.setText("Observación 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCotGenSurEst.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblSubTot.setText("SubTotal:");
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenSurEst.add(lblSubTot);

        txtSub.setEditable(false);
        txtSub.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubActionPerformed(evt);
            }
        });
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblIva.setText("IVA 12%:");
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);

        txtIva.setEditable(false);
        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtIva);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setEditable(false);
        txtTot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panCotGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabOrdCom.addTab("tab1", panCotGen);

        panCotForPag.setLayout(new java.awt.BorderLayout());

        tblOrdComForPag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "% Retención", "Monto de Pago", "Días de gracia", "CodRet", "Cod.Sri.", "tx_apliret", "BasImp"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrdComForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblOrdComForPag.setColumnSelectionAllowed(true);
        spnForPag.setViewportView(tblOrdComForPag);

        panCotForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        panCotForPagNo.setEnabled(false);
        panCotForPagNo.setPreferredSize(new java.awt.Dimension(249, 60));
        panCotForPagNo.setLayout(new java.awt.BorderLayout());

        panCotForPagNorCen.setPreferredSize(new java.awt.Dimension(249, 40));

        lblForPag.setText("Forma de Pago:");
        panCotForPagNorCen.add(lblForPag);

        cboForPag.setPreferredSize(new java.awt.Dimension(200, 25));
        cboForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboForPagActionPerformed(evt);
            }
        });
        panCotForPagNorCen.add(cboForPag);

        panCotForPagNo.add(panCotForPagNorCen, java.awt.BorderLayout.CENTER);

        panCotForPag.add(panCotForPagNo, java.awt.BorderLayout.NORTH);

        tabOrdCom.addTab("tab2", panCotForPag);

        getContentPane().add(tabOrdCom, java.awt.BorderLayout.CENTER);

        lblCotNumDes.setText("Orden de Compra ");
        lblCotNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCotNumDes.setOpaque(true);
        panCotNor.add(lblCotNumDes);

        getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtOrdComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOrdComKeyPressed
        // TODO add your handling code here:
        
        if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
            objTooBar.consultar();
            objTooBar.setEstado('w');
        }
        
    }//GEN-LAST:event_txtOrdComKeyPressed

    private void txtComCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComCodFocusGained
       strCodCom=txtComCod.getText();
        txtComCod.selectAll();
    }//GEN-LAST:event_txtComCodFocusGained

    private void txtComNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComNomFocusGained
         strDesLarCom=txtComNom.getText();
        txtComNom.selectAll();
    }//GEN-LAST:event_txtComNomFocusGained

    private void txtComNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComNomFocusLost
          if (!txtComNom.getText().equalsIgnoreCase(strDesLarCom))
         {
            if (txtComNom.getText().equals(""))
            {
                txtComCod.setText("");
                txtComNom.setText("");
            }
            else
            {
               BuscarVendedor("a.tx_nom",txtComNom.getText(),1);
            }
        }
        else
            txtComNom.setText(strDesLarCom); 
       
    }//GEN-LAST:event_txtComNomFocusLost

    private void txtPrvNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvNomFocusGained
          strDesLarPrv=txtPrvNom.getText();
        txtPrvNom.selectAll();
    }//GEN-LAST:event_txtPrvNomFocusGained

    private void txtPrvNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvNomFocusLost
        // TODO add your handling code here:
           if (!txtPrvNom.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtPrvNom.getText().equals(""))
            {
                txtPrvCod.setText("");
                txtPrvNom.setText("");
            }
            else
            {
               BuscarProveedor("a.tx_nom",txtPrvNom.getText(),1);
            }
        }
        else
            txtPrvNom.setText(strDesLarPrv);
       
    }//GEN-LAST:event_txtPrvNomFocusLost

    private void txtPrvCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvCodFocusGained
        // TODO add your handling code here:
          strCodPrv=txtPrvCod.getText();
        txtPrvCod.selectAll();
    }//GEN-LAST:event_txtPrvCodFocusGained

    private void cboForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboForPagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboForPagActionPerformed

    private void txtPrvDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrvDirActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configurar_tabla();
        
        Configura_ventana_consulta();
          
            if(intValBus==1){
                txtDoc.setText(""+ intCodCot);
                consultar2(intCodEmp ,intCodLoc);
                objTooBar.setEstado('w');      
           }
           if(intValBus==2){
                objZafParSis.setCodigoMenu( intCodMnu );
                txtCodTipDoc.setText(""+intCodTipDocOc);

               
                txtDoc.setText(""+intCodDocOc);
                consultar2(intCodEmp ,intCodLoc);
                objTooBar.setEstado('w');
           }

         

                  
          
    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        // TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
            
           cerrarObj();
             System.gc(); 
            dispose(); 
            
        }    
        
    }//GEN-LAST:event_CerrarVentana

    private void txtFacPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFacPrvFocusLost
  
    }//GEN-LAST:event_txtFacPrvFocusLost

    private void txtDetTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDetTipDocActionPerformed
        // TODO add your handling code here:
       listaTipdoc("a.tx_deslar",txtDetTipDoc.getText(),2);
    }//GEN-LAST:event_txtDetTipDocActionPerformed

    private void txtOrdComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOrdComFocusLost
        // TODO add your handling code here:
        if (txtCodTipDoc.getText().equals(""))
        {
            txtOrdCom.setText("");
            txtCodTipDoc.requestFocus();
        }
    }//GEN-LAST:event_txtOrdComFocusLost

    private void txtOrdComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdComActionPerformed
        // TODO add your handling code here:
        if (txtCodTipDoc.getText().equals(""))
        {
            txtOrdCom.setText("");
            txtCodTipDoc.requestFocus();
        }
    }//GEN-LAST:event_txtOrdComActionPerformed

    private void txtCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCotActionPerformed
        // TODO add your handling code here:
        if (!txtCot.getText().equals(""))
            cargarDatCot();
    }//GEN-LAST:event_txtCotActionPerformed

    
    
    
 private void cargarDatCot(){
   java.sql.Connection conn; 
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   String strSql="";
     try{
        conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(conn !=null){
          stmLoc = conn.createStatement();                                
         
          strSql="SELECT cli.co_cli, cli.tx_nom as nomcli, cli.tx_dir as dircli, " + //<== Campos con los datos del CLiente para la cabecera
          " Usr.co_usr as co_com, Usr.tx_nom as nomcom,  CotCab.co_cot, CotCab.fe_cot, CotCab.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
          " CotCab.co_forPag, CotCab.tx_obs1, CotCab.tx_obs2, CotCab.nd_sub, CotCab.nd_porIva, CotCab.nd_valDes, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
          " CotCab.st_reg ,Cli.co_tipper, Cli.st_ivacom FROM tbm_cabCotCom as CotCab " +
          " left outer join  tbm_cli as Cli on (cotcab.co_emp = cli.co_emp and cotcab.co_prv = cli.co_cli) left outer join tbm_usr as Usr on (Usr.co_usr = CotCab.co_com ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
          " Where CotCab.co_cot = " + txtCot.getText() +
          "       and CotCab.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
          "       and CotCab.co_loc = " + objZafParSis.getCodigoLocal()+" " +
          " ORDER BY CotCab.co_cot";
          rstLoc = stmLoc.executeQuery(strSql);      
          if(rstLoc.next()){
            if(rstLoc.getString("st_reg").equals("A"))
                 refrescaDatosCot(conn, rstLoc);
             else
                 MensajeInf("Cotizacion ya fue generada.");
           }else{
                 txtCot.setText("");                                    
          }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();  
        stmLoc=null;
        conn.close();
        conn=null;
      }}catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
        catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   }
    
 
 
  
 
    
    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:         
        //FndTipoDocumento("",0);
        listaTipdoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNomTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipDocFocusLost
        // TODO add your handling code here:
        //if (txtNomTipDoc.getText().equals("")) FndTipoDocumento(txtNomTipDoc.getText(),1);
    }//GEN-LAST:event_txtNomTipDocFocusLost

    
    
      public void cerrarObj(){
       try{
          objVenCon2.dispose(); 
          objVenCon2=null; 
          objVenConVen.dispose();
          objVenConVen=null;  
          objVenConPrv.dispose();
          objVenConPrv=null;
          
         objUti=null;
         objCiudad_dat=null;
         objTooBar=null;
         objlisCambios=null;
         objZafParSis=null;
         txtFecDoc=null;
        
         objTblCelEdiTxtVcoItm=null;
         objUltDocPrint=null;
        }
         catch (Exception e)  { objUti.mostrarMsgErr_F1(this, e);  }
     }
      
    
    
    
    private void txtNomTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipDocActionPerformed
        // TODO add your handling code here:
       
        listaTipdoc("a.tx_descor",txtNomTipDoc.getText(),1);
    }//GEN-LAST:event_txtNomTipDocActionPerformed
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
              if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
              {
               if(!objVenConTipdoc.getValueAt(1).equals("")){
                   cargarCabTipoDoc(Integer.parseInt(objVenConTipdoc.getValueAt(1)));            
                   if (objTooBar.getEstado()=='n') cargaNum_Doc_OrdCom();
                  // generaAsiento();
                }
             }      
    }
     
    
    //    private void FndTipoDocumento(String strBusqueda,int TipoBusqueda)
//    {
//        Librerias.ZafConsulta.ZafConsulta  objFndTipDoc = 
//        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
//           "Codigo,Alias,Descripcion","cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar",
//             "Select distinct cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)" +
//                " where   docprg.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
//                        " docprg.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
//                        " docprg.co_mnu = " + objZafParSis.getCodigoMenu()  ,strBusqueda, 
//         objZafParSis.getStringConexion(), 
//         objZafParSis.getUsuarioBaseDatos(), 
//         objZafParSis.getClaveBaseDatos()
//         );        
//
//        objFndTipDoc.setTitle("Listado Tipos de Documentos");
//        
//         if(strBusqueda.equals("")){
//            objFndTipDoc.show();
//         }else{
//             if (TipoBusqueda==1){
//                 objFndTipDoc.setSelectedCamBus(1);
//                if(!objFndTipDoc.buscar("doc.tx_descor = '" + txtNomTipDoc.getText()+ "'"))
//                    objFndTipDoc.show();
//                 
//             }else{
//                 objFndTipDoc.setSelectedCamBus(2);
//                if(!objFndTipDoc.buscar("doc.tx_deslar = '" + txtDetTipDoc.getText()+"'"))
//                    objFndTipDoc.show();                 
//             }
//         }
//        if(!objFndTipDoc.GetCamSel(1).equals("")){
//            cargarCabTipoDoc(Integer.parseInt(objFndTipDoc.GetCamSel(1)));                   
//            if (objTooBar.getEstado()=='n' ) cargaNum_Doc_OrdCom();
//        }
//    }
//    
    private void txtPrvCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvCodFocusLost
        // TODO add your handling code here:
        
         if (!txtPrvCod.getText().equalsIgnoreCase(strCodPrv))
        {
            if (txtPrvCod.getText().equals(""))
            {
                txtPrvCod.setText("");
                txtPrvNom.setText("");
              
            }
            else
            {
               BuscarProveedor("a.co_cli",txtPrvCod.getText(),0);
            }
        }
        else
            txtPrvCod.setText(strCodPrv);
          
    }//GEN-LAST:event_txtPrvCodFocusLost

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        // TODO add your handling code here:
      objVenConPrv.setTitle("Listado de Clientes");         
              objVenConPrv.setCampoBusqueda(1);
              objVenConPrv.show();
             if (objVenConPrv.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtPrvCod.setText(objVenConPrv.getValueAt(1));
                txtPrvNom.setText(objVenConPrv.getValueAt(2));
                txtPrvDir.setText(objVenConPrv.getValueAt(3));
                strTipPer_glo = objVenConPrv.getValueAt(6);
                strCodTipPerCli =  objVenConPrv.getValueAt(7); 
                stIvaCom =  objVenConPrv.getValueAt(8);   
                FndVenToCli();
                calculaPagos();
             } 
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtPrvNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvNomActionPerformed
         txtPrvNom.transferFocus();
         
    }//GEN-LAST:event_txtPrvNomActionPerformed

    private void txtPrvCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvCodActionPerformed
     txtPrvCod.transferFocus();
        
    }//GEN-LAST:event_txtPrvCodActionPerformed

     public void BuscarProveedor(String campo,String strBusqueda,int tipo){
        objVenConPrv.setTitle("Listado de Clientes"); 
        if (objVenConPrv.buscar(campo, strBusqueda ))
        {
              txtPrvCod.setText(objVenConPrv.getValueAt(1));
              txtPrvNom.setText(objVenConPrv.getValueAt(2));
              txtPrvDir.setText(objVenConPrv.getValueAt(3));
               strTipPer_glo = objVenConPrv.getValueAt(6);
              strCodTipPerCli =  objVenConPrv.getValueAt(7);  
              stIvaCom =  objVenConPrv.getValueAt(8);
              FndVenToCli();
               calculaPagos();
        }
        else
        {     objVenConPrv.setCampoBusqueda(tipo);
              objVenConPrv.cargarDatos();
              objVenConPrv.show();
             if (objVenConPrv.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtPrvCod.setText(objVenConPrv.getValueAt(1));
                txtPrvNom.setText(objVenConPrv.getValueAt(2));
                txtPrvDir.setText(objVenConPrv.getValueAt(3));
                 strTipPer_glo = objVenConPrv.getValueAt(6);
                strCodTipPerCli =  objVenConPrv.getValueAt(7);   
                stIvaCom =  objVenConPrv.getValueAt(8);
                 calculaPagos();
                if (txtComCod.getText().equals("")) FndVenToCli();
             }
               else{
                    txtPrvCod.setText(strCodPrv);
                    txtPrvNom.setText(strDesLarPrv);
                  }
        }
         
           
          if(!txtPrvCod.getText().equals(""))
             objCliente_dat.setCliente(Integer.parseInt( txtPrvCod.getText()  ));
          else {
              if (objCliente_dat==null) objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);       
              }
   }
  
    
    
//    private void FndProveedor(String strBusqueda,int TipoBus)
//    {
//        Librerias.ZafConsulta.ZafConsulta  objFnd = 
//         new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
//           "Codigo,Nombre,Direccion,RUC/CI","co_cli,tx_nom,tx_dir",
//           "select co_cli,tx_nom,tx_dir,tx_ide from tbm_cli where st_prv = 'S' and co_emp = " + objZafParSis.getCodigoEmpresa(), strBusqueda, 
//                 objZafParSis.getStringConexion(), 
//                 objZafParSis.getUsuarioBaseDatos(), 
//                 objZafParSis.getClaveBaseDatos()
//           );        
//         
//         objFnd.setTitle("Listado Proveedores");
//         switch (TipoBus){
//             case 1:
//                objFnd.setSelectedCamBus(0);
//                if(!objFnd.buscar("co_cli = " +strBusqueda))
//                    objFnd.show();
//                break;
//             case 2:
//                 objFnd.setSelectedCamBus(1);
//                 if(!objFnd.buscar("tx_nom = '" + strBusqueda+ "'"))
//                    objFnd.show();
//                 break;
//             default:
//                objFnd.show();
//                break;         
//         }
//        if(!objFnd.GetCamSel(1).equals("")){
//            txtPrvCod.setText(objFnd.GetCamSel(1));
//            txtPrvNom.setText(objFnd.GetCamSel(2));
//            txtPrvDir.setText(objFnd.GetCamSel(3));
//            if (txtComCod.getText().equals("")) FndVenToCli();
//        }
//         if(!txtPrvCod.getText().equals(""))
//             objCliente_dat.setCliente(Integer.parseInt(txtPrvCod.getText()));
//         else {
//             if (objCliente_dat==null) objCliente_dat = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);       
//         }
//    }
//    
    
    private void txtComCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComCodFocusLost
         if (!txtComCod.getText().equalsIgnoreCase(strCodCom))
         {
            if (txtComCod.getText().equals(""))
            {
                txtComCod.setText("");
                txtComNom.setText("");
            }
            else
            {
               BuscarVendedor("a.co_usr",txtComCod.getText(),0);
            }
        }
        else
            txtComCod.setText(strCodCom);
        
    }//GEN-LAST:event_txtComCodFocusLost

    private void txtComNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComNomActionPerformed
       txtComNom.transferFocus();
     
    }//GEN-LAST:event_txtComNomActionPerformed

    private void txtComCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComCodActionPerformed
       txtComCod.transferFocus();
       
    }//GEN-LAST:event_txtComCodActionPerformed

    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        // TODO add your handling code here:        
       // FndVendedor("",0);
         BuscarVendedor("a.co_usr","",0);
    }//GEN-LAST:event_butComActionPerformed

    public void BuscarVendedor(String campo,String strBusqueda,int tipo){
        objVenConVen.setTitle("Listado de Compradores"); 
        if (objVenConVen.buscar(campo, strBusqueda ))
        {
            txtComCod.setText(objVenConVen.getValueAt(1));
            txtComNom.setText(objVenConVen.getValueAt(2));
        }
        else
        {     objVenConVen.setCampoBusqueda(tipo);
              objVenConVen.cargarDatos();
              objVenConVen.show();
             if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
             {
                txtComCod.setText(objVenConVen.getValueAt(1));
                txtComNom.setText(objVenConVen.getValueAt(2));
             }
              else{
                    txtComCod.setText(strCodCom);
                    txtComNom.setText(strDesLarCom);
                  }
        }
    }
  
    
//    private void FndVendedor(String strBusqueda, int TipoBus)
//    {
//        Librerias.ZafConsulta.ZafConsulta  objFndVen =         
//         new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this),
//           "Codigo,Nombre","co_usr,tx_nom",
//           "Select co_usr,tx_nom from tbm_usr  ", strBusqueda, 
//             objZafParSis.getStringConexion(), 
//             objZafParSis.getUsuarioBaseDatos(), 
//             objZafParSis.getClaveBaseDatos()
//             );
//        objFndVen.setTitle("Listado Compradores");
//        
//        switch (TipoBus)
//        {
//            case 1:
//                objFndVen.setSelectedCamBus(0);
//                if(!objFndVen.buscar("co_usr = " + txtComCod.getText()))
//                    objFndVen.show();
//                break;
//            case 2:
//                objFndVen.setSelectedCamBus(1);
//                if(!objFndVen.buscar("tx_nom = " + txtComNom.getText()))
//                    objFndVen.show();                
//                break;
//            default:
//                objFndVen.show();
//                break;
//        }
//        
//        if(!objFndVen.GetCamSel(1).equals("")){
//            txtComCod.setText(objFndVen.GetCamSel(1));
//            txtComNom.setText(objFndVen.GetCamSel(2));
//        }        
//        
//    }
//    
    
    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubActionPerformed

    private void FndVenToCli(){
         /*
          *  String para el hacer el query en la tabla de clientes y obtener 
          *  el vendedor de ese cliente
          */
               
               String strSql = "select co_ven from tbm_cli where " +
                               "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                               "co_cli = " + Integer.parseInt(txtPrvCod.getText());

               try{
                    /*
                     * Obteniendo el maximo porcentaje de descuento para el cliente 
                     */
                   java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                   if (con!=null)
                   {
                        Statement stmCliVen = con.createStatement();
                        ResultSet rstCliVen = stmCliVen.executeQuery(strSql);

                        if(rstCliVen.next()){
                            int intCodVen  = rstCliVen.getInt("co_ven");
                            Librerias.ZafConsulta.ZafConsulta  objFndVen = 

                             new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent( this),
                               "Codigo,Nombre","co_usr,tx_nom",
                               "Select co_usr,tx_nom from tbm_usr  ", "", 
                                 objZafParSis.getStringConexion(), 
                                 objZafParSis.getUsuarioBaseDatos(), 
                                 objZafParSis.getClaveBaseDatos()
                                 );
                            objFndVen.setTitle("Listado vendedores");

                            if(objFndVen.buscar("co_usr = " + intCodVen )){
                                txtComCod.setText(objFndVen.GetCamSel(1));
                                txtComNom.setText(objFndVen.GetCamSel(2));
                            }    
                        }
                        rstCliVen.close();
                        stmCliVen.close();
                        con.close();
                   }
               }
               catch(SQLException Evt)
               {
                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
                }
                catch(Exception Evt)
                {
                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
                }                       
    }
                    
    private void cargaNum_Doc_OrdCom(){        
        int intNumeroDoc = objUltDocPrint.getUltDoc(Integer.parseInt(txtCodTipDoc.getText()));
        intNumeroDoc=(intNumeroDoc+1);
        txtOrdCom.setText("" + intNumeroDoc); 
   }
    /*
     * Agrega el listener para detectar que hubo algun cambio en la caja de texto  
     */
    private void addListenerCambio(){
        objlisCambios = new LisTextos();
        //Cabecera
            txtCot.setText("");
            txtCot.getDocument().addDocumentListener(objlisCambios);
            txtPrvCod.getDocument().addDocumentListener(objlisCambios);
            txtPrvNom.getDocument().addDocumentListener(objlisCambios);
            txtPrvDir.getDocument().addDocumentListener(objlisCambios);
            txtComCod.getDocument().addDocumentListener(objlisCambios);
            txtComNom.getDocument().addDocumentListener(objlisCambios);
            txtAte.getDocument().addDocumentListener(objlisCambios);
        

        //Pie de pagina
            txaObs1.getDocument().addDocumentListener(objlisCambios);
            txaObs2.getDocument().addDocumentListener(objlisCambios);
            txtSub.getDocument().addDocumentListener(objlisCambios);
            txtIva.getDocument().addDocumentListener(objlisCambios);
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
      
    /* Obtiene el Iva que se debe cobrar en la empresa actual */
    public void getIva(){
        if (objTipDoc.getst_iva().equals("S")){
            dblPorIva = objCtaCtb.getPorcentajeIvaCompras();
        }else{
            dblPorIva = 0;                        
        }
        lblIva.setText("IVA " + dblPorIva + "%");
    }
    
    public void  noEditable(boolean blnEditable){
        java.awt.Color colBack = txtCot.getBackground();

            txtDoc.setEditable(blnEditable);
            
            txtSub.setEditable(blnEditable);
            txtSub.setBackground(colBack);        
            
            txtIva.setEditable(blnEditable);
            txtIva.setBackground(colBack);        
            
            txtTot.setEditable(blnEditable);
            txtTot.setBackground(colBack);        
    }
  
    public void  clnTextos(){
          if(blnLimpiar){
        //Cabecera 
              
              strCodPrv="";
              strDesLarPrv="";   
              strCodCom="";
              strDesLarCom="";
     
            txtCot.setText("");
            txtDoc.setText("");
            txtAte.setText("");
            txtFacPrv.setText("");
            txtPrvCod.setText("");
            txtPrvNom.setText("");
            txtPrvDir.setText("");
            txtFecDoc.setText("");
            txtComCod.setText("");
            txtComNom.setText("");
            txtOrdCom.setText("");
            txtNumPed.setText("");
            
            txtCod.setText("");
            txtDes.setText("");
            txtVehRet.setText("");
            txtChoRet.setText("");
            
           // cargaTipoDocPredeterminado();
        
            objTblMod.removeAllRows();       

            //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            txtTot.setText("0");
            lblCotNumDes.setText("Orden de Compra");

             //Detalle  de PAGOS
            while(tblOrdComForPag.getRowCount()>0)
                ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).removeRow(0);
            //Diario
            objDiario.inicializar();

          }
    }
   
    public void calculaSubtotal(){
       try{
        double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00; 
        int intNumFil=tblDat.getSelectedRow();
  
        if (tblDat.getValueAt( intNumFil , INT_TBL_CANMOV)==null)
            tblDat.setValueAt("0", intNumFil , INT_TBL_CANMOV);
        if (tblDat.getValueAt( intNumFil , INT_TBL_PRECOS)==null)
            tblDat.setValueAt("0",intNumFil, INT_TBL_PRECOS);
        if (tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null)
            tblDat.setValueAt("0", intNumFil , INT_TBL_PORDES);
        
        dblCan = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_CANMOV).toString());
        dblCosto = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_PRECOS).toString());
        
        dblDes =  objUti.redondear(Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_PORDES).toString()))), objZafParSis.getDecimalesBaseDatos()  );
        
        dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));
//                   dblTotal  = (dblCan * dblCosto)- dblValDes;  
//                   dblTotal =  objUti.redondear(dblTotal,  objZafParSis.getDecimalesMostrar()  );  

        dblTotal = (dblCan * dblCosto) - dblValDes;
        dblTotal = objUti.redondear(dblTotal, 3);
        dblTotal = objUti.redondear(dblTotal, objZafParSis.getDecimalesMostrar());
                         
        tblDat.setValueAt(""+dblTotal, intNumFil ,INT_TBL_TOTAL);


//        System.out.println(" dblTotal ..  "+dblTotal + "- "+tblDat.getValueAt( intNumFil , INT_TBL_TOTAL)  );
//
//        System.out.println(" dATOD ..  "+dblCan+"  -  "+dblCosto+"  - "+dblValDes );
//
        calculaTotal();
        calculaPagos();
        generaAsiento();
        
        }catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    
    
    //****************************************************************
     public void calculaSubtotalTodosLosItem(){
      
         double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00; 
         double dblSub = 0, dblIva = 0, dblDes2 = 0, dblTmp=0, dblSub2=0;
         int intNumFil=0;  //tblDat.getSelectedRow();
         for (intNumFil=0; intNumFil<tblDat.getRowCount(); intNumFil++){
             if(tblDat.getValueAt(intNumFil,INT_TBL_CODITM) != null ){  
                 
             if (tblDat.getValueAt( intNumFil , INT_TBL_CANMOV)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_CANMOV);
             if (tblDat.getValueAt( intNumFil , INT_TBL_PRECOS)==null) tblDat.setValueAt("0",intNumFil, INT_TBL_PRECOS);
             if (tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_PORDES);
        
            dblCan = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_CANMOV).toString());
            dblCosto = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_PRECOS).toString());
            dblDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_PORDES).toString()))),2);
        
        //    System.out.println("COSTO DE COMPRA>>>>> "+dblCosto);
            
        dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));
//                   dblTotal  = (dblCan * dblCosto)- dblValDes;  
//                   dblTotal =  objUti.redondear(dblTotal,2);
                   
            dblTotal = (dblCan * dblCosto) - dblValDes ;
            dblTotal = objUti.redondear(dblTotal, 3);
            dblTotal = objUti.redondear(dblTotal, objZafParSis.getDecimalesMostrar());
                         
            tblDat.setValueAt(dblTotal+"", intNumFil ,INT_TBL_TOTAL);
        
        
            dblSub2 = objUti.redondear(((tblDat.getValueAt(intNumFil, INT_TBL_TOTAL)==null||tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesMostrar());  
            dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
             if(tblDat.getValueAt(intNumFil, INT_TBL_BLNIVA)==null)
                   dblIva = dblIva + 0;
             else{
                dblTmp = ((tblDat.getValueAt(intNumFil, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(intNumFil, INT_TBL_TOTAL)==null||tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
            }
       }}
        
       //************************************************///
        dblSubtotalCom = dblSub;
        dblIvaCom = objUti.redondear(dblIva,intNumDec);
        dblTotalCom = dblSubtotalCom + dblIvaCom;
        dblTotalCom = objUti.redondear(dblTotalCom ,intNumDec);
        dblSubtotalCom = objUti.redondear(dblSubtotalCom ,intNumDec);
       
       
        
        if(stIvaCom.equals("N")){
         txtSub.setText( "" + dblSubtotalCom );
         txtIva.setText( "0.00" );
         txtTot.setText( ""+ dblSubtotalCom );
         dblTotalCom=dblSubtotalCom;
           dblIvaCom=0;
        }else{
         txtSub.setText( "" + dblSubtotalCom );
         txtIva.setText( "" + dblIvaCom );
         txtTot.setText( ""+ dblTotalCom);
        }
        
        
        //************************************************///
        
        calculaPagos();
//        System.out.println("ESTADO SI MODIFICAR: " + objTooBar.getEstado());
        if(objTooBar.getEstado()!='m'){  /* JoséMario 30/Nov/2015 */  
            generaAsiento();    
        }
    }
    
      
      


double dblSubSerNoSer=0, dblIvaSerNoSer=0;

public void calculaSubtotalServiNoServi(String strServi){
 double dblCan,dblDes,dblCosto,dblTotal=0.00,dblPre=0.00,dblValDes=0.00;
 double dblSub = 0, dblIva = 0,  dblTmp=0, dblSub2=0;
 int intNumFil=0;
 dblSubSerNoSer=0;
 dblIvaSerNoSer=0;
 try{
     
 for (intNumFil=0; intNumFil<tblDat.getRowCount(); intNumFil++){
   if(tblDat.getValueAt(intNumFil,INT_TBL_CODITM) != null ){
    if(tblDat.getValueAt(intNumFil,INT_TBL_ITMSER).toString().equals(strServi)){


     if (tblDat.getValueAt( intNumFil , INT_TBL_CANMOV)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_CANMOV);
     if (tblDat.getValueAt( intNumFil , INT_TBL_PRECOS)==null) tblDat.setValueAt("0",intNumFil, INT_TBL_PRECOS);
     if (tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null) tblDat.setValueAt("0", intNumFil , INT_TBL_PORDES);

    dblCan = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_CANMOV).toString());
    dblCosto = Double.parseDouble(tblDat.getValueAt( intNumFil , INT_TBL_PRECOS).toString());
    dblDes =  Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_PORDES).toString())) );


           dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));
//           dblTotal  = (dblCan * dblCosto)- dblValDes;
//           dblTotal =  objUti.redondear(dblTotal, objZafParSis.getDecimalesMostrar() );
           
            dblTotal = (dblCan * dblCosto) - dblValDes ;
            dblTotal = objUti.redondear(dblTotal, 3);
            dblTotal = objUti.redondear(dblTotal, objZafParSis.getDecimalesMostrar());           

           tblDat.setValueAt(""+dblTotal, intNumFil ,INT_TBL_TOTAL);






    dblSub2 = objUti.redondear(((tblDat.getValueAt(intNumFil, INT_TBL_TOTAL)==null||tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesMostrar());
    dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
     if(tblDat.getValueAt(intNumFil, INT_TBL_BLNIVA)==null)
           dblIva = dblIva + 0;
     else{
        dblTmp = ((tblDat.getValueAt(intNumFil, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(intNumFil, INT_TBL_TOTAL)==null||tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_TOTAL).toString())) : 0 );
        dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
    }

}}
}

 if(stIvaCom.equals("N")) dblIvaSerNoSer = 0.00;
 else dblIvaSerNoSer = objUti.redondear(dblIva,intNumDec);

        dblSubSerNoSer = objUti.redondear(dblSub ,intNumDec);

 }catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}











      
   //*************************************************************************************   
    
    public void  calculaTotal(){
        double dblSub = 0, dblIva = 0, dblTmp=0, dblSub2=0;
        for (int i=0;i<tblDat.getRowCount();i++){ 
            dblSub2 = objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesBaseDatos() );  
            dblSub = dblSub + objUti.redondear(dblSub2,objZafParSis.getDecimalesBaseDatos());
            
           
             if(tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)
                dblIva = dblIva + 0;
             else{
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                //Valor de iva no se redondeaba//
                dblIva = dblIva + objUti.redondear((((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100),objZafParSis.getDecimalesBaseDatos()) ;
            }
        }
                    
        
     
        //************************************************///
        dblSubtotalCom = dblSub;
        dblIvaCom = objUti.redondear(dblIva, objZafParSis.getDecimalesMostrar() );
        dblTotalCom = dblSubtotalCom + dblIvaCom;
        dblTotalCom = objUti.redondear(dblTotalCom ,objZafParSis.getDecimalesMostrar());
        dblSubtotalCom = objUti.redondear(dblSubtotalCom ,objZafParSis.getDecimalesMostrar());
       
        if(stIvaCom.equals("N")){
         txtSub.setText( "" + dblSubtotalCom );
         txtIva.setText( "0.00" );
         txtTot.setText( ""+ dblSubtotalCom );
         dblTotalCom=dblSubtotalCom;
           dblIvaCom=0;
        }else{
        
         txtSub.setText( "" + dblSubtotalCom );
         txtIva.setText( "" + dblIvaCom );
         txtTot.setText( ""+ dblTotalCom);
        }
        
         //************************************************///
        
    }
    
    
    /**
     * Metodo que genera vector de diario para la clase ZafAsiDia
     * Genera segun el tipo de documento y su naturaleza, bodegas y si genera o no IVA
     *@autor: jayapata
     */
     private void generaAsiento(){
          objDiario.inicializar();
          double dblSubtotal=0, dblTotSer=0;
         int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
         int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
         int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
         int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
         int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
         int INT_VEC_DEBE   = 5; //5) Debe. 
         int INT_VEC_HABER  = 6; //6) Haber. 
         int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
         int INT_VEC_NUEVO    = 8; //7) Referencia: Se debe asignar una cadena vacía o null

         int intEst=0;
         
         //********************************************************
         
         for(int i=0; i<tblDat.getRowCount(); i++){
             if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                  if(!(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N"))) {
                      dblTotSer += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
                      intEst=1;
                   }
             }
         }
      
        //********************************************************
     
         
           if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
             else vecDetDiario.removeAllElements();
          
         java.util.Vector vecReg; 
         
         int intCodBodT,intCodBod;
         
         
        /// System.out.println("Num Bod. "+ arreBod.size() );
         
        if(objTipDoc.gettx_natdoc().equals("I"))
        {
         for (int j=0;j<arreBod.size();j++){
            intCodBod = Integer.parseInt(arreBod.get(j).toString());
            dblSubtotal=0;
             for (int i=0;i<tblDat.getRowCount();i++){            
                if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                {
                  if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) {
                    if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                    {
                       intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                        if(intCodBod==intCodBodT)
                          dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
             }}}}
            
                   
            
                      if(dblSubtotal > 0 ){
                       vecReg = new java.util.Vector();
                       vecReg.add(INT_LINEA, null);                
                       vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                       vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                       vecReg.add(INT_VEC_BOTON, null);                
                       vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                       vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf(dblSubtotal));  
                       vecReg.add(INT_VEC_HABER, new Double(0));
                       vecReg.add(INT_VEC_REF, null);
                       vecReg.add(INT_VEC_NUEVO, null);

                       vecDetDiario.add(vecReg);
                      }
         }   
          if(stIvaCom.equals("S")){
           if (objTipDoc.getst_iva().equals("S"))
            {
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());   
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                 
                vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf((objUti.redondear(txtIva.getText(),objZafParSis.getDecimalesMostrar()))));
                vecReg.add(INT_VEC_HABER, new Double(0)); 
                vecReg.add(INT_VEC_REF, null);
                 vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);                       
            } }      
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(0)); 
            vecReg.add(INT_VEC_HABER, java.math.BigDecimal.valueOf((objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar()))));
            vecReg.add(INT_VEC_REF, null);
             vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);  
         
        }else{
              
            //System.out.println("Num Bod. "+ arreBod.size() );
            
          for (int j=0;j<arreBod.size();j++){
            intCodBod = Integer.parseInt(arreBod.get(j).toString());
            dblSubtotal=0;
             for (int i=0;i<tblDat.getRowCount();i++){            
                if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                {
                  if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) { 
                    if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                    {
                       intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                        if(intCodBod==intCodBodT)
                          dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
             }}}}
                      if(dblSubtotal > 0 ){
                       vecReg = new java.util.Vector();
                       vecReg.add(INT_LINEA, null);                
                       vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                       vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                       vecReg.add(INT_VEC_BOTON, null);                
                       vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                       vecReg.add(INT_VEC_DEBE, new Double(0));  
                       vecReg.add(INT_VEC_HABER, java.math.BigDecimal.valueOf(dblSubtotal));
                       vecReg.add(INT_VEC_REF, null);
                        vecReg.add(INT_VEC_NUEVO, null);
                       vecDetDiario.add(vecReg);
                      }
         }  
              
            if(stIvaCom.equals("S")){   
              if (objTipDoc.getst_iva().equals("S"))
            {
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaIvaCompras()));
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());  
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                
                vecReg.add(INT_VEC_DEBE, new Double(0)); 
                vecReg.add(INT_VEC_HABER, java.math.BigDecimal.valueOf(objUti.redondear(txtIva.getText(),objZafParSis.getDecimalesMostrar())));   
                vecReg.add(INT_VEC_REF, null);
                 vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);                       
            }       
            }
              
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer( objCtaCtb.getCtaCompra()  ));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf(objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar()))); 
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
             vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);  
        }
              
          
         
         
            if(intEst==1){
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA,new Integer(objCtaCtb.getCtaSerCompras()));
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaSerCom());   
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaSerCom());                 
                vecReg.add(INT_VEC_DEBE, java.math.BigDecimal.valueOf(dblTotSer));
                vecReg.add(INT_VEC_HABER, new Double(0)); 
                vecReg.add(INT_VEC_REF, null);
                 vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);      
            }
         
         
        objDiario.setDetalleDiario(vecDetDiario);         
     }
    
     
     
    
  //**************************************************************************************************  
    
    
    
    
    
    
     
      
    private boolean refrescaDatosCot(java.sql.Connection conn,  java.sql.ResultSet rstDatCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.Statement stmLocPri;
        java.sql.ResultSet rstLoc;
        java.sql.ResultSet rstLocPri;
        String strSql="";
        int intNumCot=0;
        double dblCan=0, dblPre=0, dblPorDes=0, dblValDes=0, dblTotal=0;
        String strUnidad="",strCodAlt="",strCodAlt2="";
        String strSer="";
        try{
            if(conn!=null){ 
               stmLoc=conn.createStatement();
               stmLocPri=conn.createStatement();
                if(rstDatCot != null){
                    intNumCot = rstDatCot.getInt("co_cot");
                    txtCot.setText(""+intNumCot);
                    txtPrvCod.setText(((rstDatCot.getString("co_cli")==null)?"":rstDatCot.getString("co_cli")));
                    txtPrvNom.setText(((rstDatCot.getString("nomcli")==null)?"":rstDatCot.getString("nomcli")));
                    txtPrvDir.setText(((rstDatCot.getString("dircli")==null)?"":rstDatCot.getString("dircli")));
                    strCodTipPerCli=((rstDatCot.getString("co_tipper")==null)?"":rstDatCot.getString("co_tipper"));
                      
                    if(!txtPrvCod.getText().equals(""))
                       objCliente_dat.setCliente(Integer.parseInt( txtPrvCod.getText()));                    
                    txtFecDoc.setHoy();
                    txtComCod.setText(((rstDatCot.getString("co_com")==null)?"":rstDatCot.getString("co_com")));
                    txtComNom.setText(((rstDatCot.getString("nomcom")==null)?"":rstDatCot.getString("nomcom")));
                    txtAte.setText(((rstDatCot.getString("tx_ate")==null)?"":rstDatCot.getString("tx_ate")));
                    stIvaCom =   rstDatCot.getString("st_ivaCom");  
                    txaObs1.setText(((rstDatCot.getString("tx_obs1")==null)?"":rstDatCot.getString("tx_obs1")));
                    txaObs2.setText(((rstDatCot.getString("tx_obs2")==null)?"":rstDatCot.getString("tx_obs2")));
                   //Detalle        
                   
                    
                    
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
                
                
                    strSql = "SELECT a.tx_nomitm, a.co_bod, a.nd_can, a.nd_cosuni, a.nd_pordes, a.st_ivacom, a.co_itm" +
                    " ,inv.tx_codalt, inv.tx_codalt2, var.tx_descor, inv.st_ser " +
                    " "+strAux2+" " +
                    " FROM tbm_detCotCom AS a " +
                    " INNER JOIN tbm_inv AS inv on (a.co_emp=inv.co_emp AND  a.co_itm=inv.co_itm) " +
                    " left outer join tbm_var as var on (inv.co_uni = var.co_reg) " +
                    " WHERE " +
                    " a.co_emp = " + objZafParSis.getCodigoEmpresa() + " AND " +
                    " a.co_loc = " + objZafParSis.getCodigoLocal()   + " AND " +
                    " a.co_cot = " + intNumCot                       + " ORDER BY a.co_reg";
                    rstLoc = stmLoc.executeQuery(strSql);
                    
                    Vector vecData = new Vector();
                    while(rstLoc.next()){
                         java.util.Vector vecReg = new java.util.Vector();
                         
//                         strSql = " Select tx_codalt,tx_codalt2,tx_descor,inv.st_ser from tbm_detcotcom as detcot " +
//                         " left outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) " +
//                         " left outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
//                         " where detcot.co_emp  = " + objZafParSis.getCodigoEmpresa() +" and detcot.co_loc = " + objZafParSis.getCodigoLocal() + " and detcot.co_cot = " + intNumCot +" and detcot.co_itm = " + rstLoc.getString("co_itm") ;                        
//                         rstLocPri = stmLocPri.executeQuery(strSql);
//                         if(rstLocPri.next()){
                             strCodAlt = (rstLoc.getString("tx_codalt")==null?"":rstLoc.getString("tx_codalt"));
                             strCodAlt2= (rstLoc.getString("tx_codalt2")==null?"":rstLoc.getString("tx_codalt2"));
                             strUnidad = (rstLoc.getString("tx_descor")==null?"":rstLoc.getString("tx_descor"));
                             strSer    = (rstLoc.getString("st_ser")==null?"":rstLoc.getString("st_ser"));
//                         }
//                         rstLocPri.close();
//                         rstLocPri=null;

                         vecReg.add(INT_TBL_LINEA, "");
                         vecReg.add(INT_TBL_ITMALT, strCodAlt);
                         vecReg.add(INT_TBL_BUTITM, "");
                         vecReg.add(INT_TBL_DESITM, rstLoc.getString("tx_nomitm"));
                         vecReg.add(INT_TBL_UNIDAD, strUnidad);
                         vecReg.add(INT_TBL_CODBOD, new Integer(rstLoc.getInt("co_bod")));
                         vecReg.add(INT_TBL_BUTBOD, "");
                         vecReg.add(INT_TBL_CANORI, new Double(0));
                         vecReg.add(INT_TBL_CANMOV, new Double(rstLoc.getDouble("nd_can")));                                                                           
                         vecReg.add(INT_TBL_PRECOS, new Double(rstLoc.getDouble("nd_cosuni")));
                         vecReg.add(INT_TBL_PORDES, new Double(rstLoc.getDouble("nd_pordes")));
                         String strIva = rstLoc.getString("st_ivacom");
                         Boolean blnIva = new Boolean(strIva.equals("S")?true:false);                         
                         vecReg.add(INT_TBL_BLNIVA, blnIva);
                         dblCan    = rstLoc.getDouble("nd_can");
                         dblPre    = rstLoc.getDouble("nd_cosuni");
                         dblPorDes = rstLoc.getDouble("nd_pordes");
                         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
//                         dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;
                          
                         dblTotal = (dblCan * dblPre) - dblValDes ;
                         dblTotal = objUti.redondear(dblTotal, 3);
                         dblTotal = objUti.redondear(dblTotal, objZafParSis.getDecimalesMostrar());
                         
                         vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                         vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                         vecReg.add(INT_TBL_ITMORI, "");
                         vecReg.add(INT_TBL_BODORI, "");
                         vecReg.add(INT_TBL_ESTADO, "E");
                         vecReg.add(INT_TBL_IVATXT, "");
                         vecReg.add(INT_TBL_ITMALT2, strCodAlt2);
                         vecReg.add(INT_TBL_ITMSER, strSer );
                         vecReg.add(INT_TBL_CODREG, ""); 
                         vecReg.add(INT_TBL_CODITMACT,  rstLoc.getString("co_itm") ); 
                         vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));

                         vecReg.add(INT_TBL_COSUNIORI, rstLoc.getString("nd_cosuni"));
                         vecReg.add(INT_TBL_PORDESORI, rstLoc.getString("nd_pordes"));
                         vecReg.add(INT_TBL_PRECOSRES, rstLoc.getString("nd_cosuni"));
                         vecReg.add(INT_TBL_PORDESRES, rstLoc.getString("nd_pordes"));

                       vecData.add(vecReg);                         
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);                                 
                     
                     calculaTotal();
                     generaAsiento();
                     /* CARGANDO DATOS DEL TAB FORMA DE PAGO*/                     
                     String strCo_ForPag = (rstDatCot.getString("co_forPag")==null)?"":rstDatCot.getString("co_forPag") ;
                    
                     /* LLenando el combo de descripciones de pagos */
                     objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                                            objZafParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag, strCo_ForPag);      
                      cboForPag.addItem("< Refrescar >");                    
                      calculaPagos();
                      String strStatus = rstDatCot.getString("st_reg");
                      if(strStatus.equals("I")){
                             objUti.desactivarCom(jfrThis);
                       }else{
                           if (objTooBar.getEstado() == 'a'){
                               objUti.activarCom(jfrThis);
                               noEditable(false);
                }}}
               stmLoc.close();
               stmLoc=null;
               stmLocPri.close();
               stmLocPri=null;
            blnRes=true;
            blnHayCam = false; // Seteando que no se ha hecho cambios

        }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
          catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
     return blnRes;
    }
        
    
    
    
    
    
    
    
    public void refrescaPagos(int intNumeroDocumento){
        
        try{       
            java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());        
            if (con!=null)
            {
                    java.sql.Statement stmDetPag = con.createStatement();
                    java.sql.ResultSet rstDetPag ;
//                    String strCo_ForPag = (rstCab.getString("co_forPag")==null)?"":rstCab.getString("co_forPag") ;
//                    cboForPag.setSelectedIndex(vecForPag.indexOf(strCo_ForPag));
         
                    
                    //Extrayendo los datos del detalle respectivo a ESTE PAGO
                    sSQL  = "Select ne_diacre,fe_ven,nd_porret,tx_aplret,mo_pag,ne_diagra, co_tipret, co_reg, tx_codsri, tx_aplret " +
                            " ,nd_basimp  from tbm_pagMovInv where " +
                            " co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                            " co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                            " co_tipdoc = " + rstCab.getString("co_tipdoc")     + " and " +
                            " co_doc = " + rstCab.getString("co_doc")      + "  and st_reg IN ('A','F') order by co_reg  ";
//                            System.out.println(sSQL);
                    stmDetPag=con.createStatement();                    
                    rstDetPag = stmDetPag.executeQuery(sSQL);
                    
                    /*LLenando el combo de descripciones de pagos */
                    //Borrando los datos actuales del jtable
                    while(tblOrdComForPag.getRowCount()>0)
                        ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).removeRow(0);

                    /* LLenando la tabla de pagos*/
                    
                    for(int i=0 ; rstDetPag.next() ; i++){
                        ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());    
                        
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("ne_diaCre") , i, 1);
                        /* Formateando la fecha que viene desde la base para presentarla en d/m/y */
                        java.util.Calendar calFecPag = java.util.Calendar.getInstance();
                        
                        calFecPag.setTime(rstDetPag.getDate("fe_ven"));
                        Librerias.ZafDate.ZafDatePicker dtePckPag = 
                         new Librerias.ZafDate.ZafDatePicker(calFecPag.get(java.util.Calendar.DAY_OF_MONTH), 
                                                            (calFecPag.get(java.util.Calendar.MONTH)+1), 
                                                            calFecPag.get(java.util.Calendar.YEAR), new javax.swing.JFrame(), "d/m/y"     
                         );
                        
                        tblOrdComForPag.setValueAt(dtePckPag.getText() , i, 2);
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("nd_porRet") , i, 3);
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("mo_pag") , i, 4);
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("ne_diaGra") , i, 5);  // ne_diaGra
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("co_tipret") , i, 6);
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("tx_codsri") , i, 7);
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("tx_aplret") , i, 8);
                        tblOrdComForPag.setValueAt(rstDetPag.getObject("nd_basimp") , i, 9);  // nd_basimp

                    }
        
                    rstDetPag.close();
                    stmDetPag.close();
                    con.close();
            }
        }
        catch(SQLException Evt)
        {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
              
        }
        catch(Exception Evt)
        {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }            
     }
      
    public void  refrescaDatos(){
       
        try{//odbc,usuario,password        
            int intNumDoc = 0;
       
                if(rstCab != null){                    
                    java.sql.Connection   conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

                    
                    
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
                
                
                        String strSer="";
                    //Extrayendo los datos del detalle respectivo a esta orden de compra
                    sSQL  = "Select a.co_itm,a.tx_codalt,a.tx_codalt2,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
                            " ,b.st_ser , a.co_reg , a.co_itmact, a.st_meringegrfisbod, a.nd_preunivenlis, a.nd_pordesvenmax, a.nd_tot  " +
                            " "+strAux2+" FROM tbm_detMovInv AS a" +
                            " INNER JOIN tbm_inv AS b ON(a.co_emp=b.co_emp and a.co_itm=b.co_itm) WHERE " +
                            " a.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                            " a.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                            " a.co_tipdoc = " +rstCab.getString("co_tipdoc")    + " and " +
                            " a.co_doc = " + rstCab.getString("co_doc")      + " ORDER BY a.co_reg";
                    System.out.println("ZafCom02: " + sSQL);
                     
                    
                    java.sql.Statement stm = conCab.createStatement();
                    java.sql.ResultSet rst= stm.executeQuery(sSQL);
                    double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;

                    java.util.Vector vecData = new java.util.Vector();
                    while (rst.next()){ 
                        strSer = (rst.getString("st_ser")==null?"":rst.getString("st_ser"));
                           
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_BUTITM, "");
                        vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
                        vecReg.add(INT_TBL_BUTBOD, "");
                        vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_can")));
                        vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")));                                                                           
                        vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_cosuni")));
                        vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                        String strIva = rst.getString("st_ivacom");
                        Boolean blnIva = strIva.equals("S")?true:false;                         
                        vecReg.add(INT_TBL_BLNIVA, blnIva);
                        dblCan    = rst.getDouble("nd_can");
                        dblPre    = rst.getDouble("nd_cosuni");
                        dblPorDes = rst.getDouble("nd_pordes");
                        dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                        //REVISAR
                        dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;
                        if (blnIva.booleanValue()){                            
                            dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objZafParSis.getDecimalesMostrar()) ;
                        }
                        vecReg.add(INT_TBL_TOTAL, rst.getString("nd_tot"));  //new Double(dblTotal));
                        vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                        vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
                        vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
                        vecReg.add(INT_TBL_ESTADO, "E");
                        vecReg.add(INT_TBL_IVATXT, "");
                        vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_ITMSER, strSer );
                        vecReg.add(INT_TBL_CODREG, rst.getString("co_reg") );   
                        vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact"));
                        vecReg.add(INT_TBL_IEBODFIS, rst.getString("st_meringegrfisbod"));
                        vecReg.add(INT_TBL_COSUNIORI, rst.getString("nd_preunivenlis"));
                        vecReg.add(INT_TBL_PORDESORI, rst.getString("nd_pordesvenmax"));
                        vecReg.add(INT_TBL_PRECOSRES, rst.getString("nd_cosuni"));
                        vecReg.add(INT_TBL_PORDESRES, rst.getString("nd_pordes"));
                        vecData.add(vecReg);                         
                    }
                    objTblMod.setData(vecData);
                    tblDat .setModel(objTblMod);                                 

                    calculaTotal();

                   
                       
                    // txtIva.setText(""+dblIva);
                    // txtTot.setText(""+objUti.redondear((objUti.redondear(txtSub.getText(),objZafParSis.getDecimalesMostrar())+dblIva),objZafParSis.getDecimalesMostrar()));
                    
                    
                    /*
                    * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                    */
                    objDiario.consultarDiarioCompleto(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), rstCab.getInt("co_tipdoc"), rstCab.getInt("co_doc"));

                  
                    stm.close();
                    rst.close();
                    conCab.close();
                }
            objTblMod.setDataModelChanged(false);
            blnHayCam = false; // Seteando que no se ha hecho cambios
            
   
        }//fin Try
       catch(SQLException Evt)
       {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
              
        }

        catch(Exception Evt)
        {
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                          
    }
    
    
    
    private int Mensaje(){
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Desea guardar los cambios efectuados a éste registro?\n";
        strMsg+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();

        return JOptionPane.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
    }

    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            JOptionPane.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    private void MensajeValidaCampo(String strNomCampo){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    private int mensaje(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit="Sistema Zafiro";        
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
    }     
    private void MsgError(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit="Sistema Zafiro";        
        JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);                
    }     
    
      /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                objTblMod.setDataModelChanged(false);
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
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {                      
                refrescaDatos();                
            }
            else
            {
                MensajeInf("Error al cargar registro");
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }       
    
    
    
      
    private boolean cargarCabReg()
    {                              
        int intPosRel;
        boolean blnRes=true;
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";

                
      strSQL= "SELECT a.st_emiChqAntRecFacPrv, a.co_cli, a.tx_nomcli as nomcli, a.tx_dircli as dircli,  a.co_com , a.tx_nomven as nomcom, "+
              "a.co_doc, a.fe_doc, a.tx_ate,  a.co_forPag,  a.tx_obs1, a.tx_obs2, a.nd_sub, a.nd_porIva, a.nd_tot, a.st_reg, a.ne_numcot as num_cot,"+
              "a.ne_numdoc "+
              "as num_doc,  a.tx_numDoc2, a.tx_numped as num_ped, a.co_tipdoc, a.co_forret,b.tx_deslar,a.tx_vehret,a.tx_choret,a.ne_secemp  "+
              " ,a.st_regrep ,cli.tx_tipper ,cli.co_tipper, cli.st_ivacom, a.nd_porIva /*JoseMario IVA 6/Jun/2016*/ FROM tbm_cabMovInv as a LEFT OUTER JOIN tbm_var as b ON (b.co_reg=a.co_forret)"+
              " inner join tbm_cli as cli on(cli.co_emp=a.co_emp and cli.co_cli=a.co_cli) " +
              "where  a.co_emp="+rstCab.getString("co_emp")+" AND a.co_loc="+rstCab.getString("co_loc")+" AND a.co_tipDoc="+ rstCab.getString("co_tipDoc")+" AND "+ 
              " a.st_reg not in('E') and  a.co_doc="+rstCab.getString("co_doc") + " order by a.fe_doc,  a.ne_numdoc " ;
                System.out.println("ZafCom02.CabReg.: " + strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
               
                  
                if (rst.next())
                {
                    
                  Glo_intCodSec=rst.getInt("ne_secemp");
                    
                    intNumDoc = rst.getInt("co_doc");
                    txtDoc.setText(""+intNumDoc);
                    
                    chkEmiChq.setSelected( (rst.getString("st_emiChqAntRecFacPrv")==null?false:(rst.getString("st_emiChqAntRecFacPrv").equals("S")?true:false) ) );


                    txtPrvCod.setText(((rst.getString("co_cli")==null)?"":rst.getString("co_cli")));
                    GLOCODCLI=txtPrvCod.getText();
                    txtPrvNom.setText(((rst.getString("nomcli")==null)?"":rst.getString("nomcli")));
                    txtPrvDir.setText(((rst.getString("dircli")==null)?"":rst.getString("dircli")));
                    txtCot.setText(((rst.getString("num_cot")==null)?"":rst.getString("num_cot")));
                    txtOrdCom.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                    
                    txtNumPed.setText(((rst.getString("tx_numdoc2")==null)?"":rst.getString("tx_numdoc2")));                    
                    
                    txtFacPrv.setText(((rst.getString("num_ped")==null)?"":rst.getString("num_ped")));
                   
                    STR_ESTREG=rst.getString("st_regrep");
                    strTipPer_glo=rst.getString("tx_tipper");
                    strCodTipPerCli=((rst.getString("co_tipper")==null)?"":rst.getString("co_tipper"));
                    
                    
                    txtCod.setText(((rst.getString("co_forret")==null)?"":rst.getString("co_forret")));
                    txtDes.setText(((rst.getString("tx_deslar")==null)?"":rst.getString("tx_deslar")));
                    txtVehRet.setText(((rst.getString("tx_vehret")==null)?"":rst.getString("tx_vehret")));
                    txtChoRet.setText(((rst.getString("tx_choret")==null)?"":rst.getString("tx_choret")));
                    
                    stIvaCom=rst.getString("st_ivacom");
                    
                   // cargarCabTipoDoc(rst.getInt("co_tipdoc"));                   
                    
                    if(rst.getDate("fe_doc")==null){
                      txtFecDoc.setText("");  
                    }else{
                        java.util.Date dateObj = rst.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                    }
                    
                      
                    txtComCod.setText(((rst.getString("co_com")==null)?"":rst.getString("co_com")));
                    txtComNom.setText(((rst.getString("nomcom")==null)?"":rst.getString("nomcom")));
                    txtAte.setText(((rst.getString("tx_ate")==null)?"":rst.getString("tx_ate")));
                  
                    double dblSub = ((rst.getString("nd_sub")==null)?0:objUti.redondear(rst.getDouble("nd_sub"),objZafParSis.getDecimalesMostrar()));
                   
                   dblPorIva=rst.getDouble("nd_porIva");  /*JoseMario - 6/Jun/2016*/
                    
                    //Pie de pagina
                      
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                   
                    lblCotNumDes.setText("Orden de Compra No. " + txtOrdCom.getText() +  " (" + txtPrvNom.getText() + ") ");
                    lblIva.setText("IVA " + dblPorIva + "%");
                    /*
                     * CARGANDO DATOS DEL TAB FORMA DE PAGO
                     */
                    
                    
                    
                    String strCo_ForPag = (rst.getString("co_forPag")==null)?"":rst.getString("co_forPag") ;
                    cboForPag.setSelectedIndex(vecForPag.indexOf(strCo_ForPag));
                   
                     
                    refrescaPagos(intNumDoc);
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                    
                     
                    
                    if(rst.getString("st_reg").equals("I")){
                        objUti.desactivarCom(jfrThis);
                    }else{
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(jfrThis);
                            noEditable(false);
                        }
                    }

                    
                  txtSub.setText(""+dblSub);
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else if (strAux.equals("C"))
                        strAux="Pendiente de Confirmacion";
                    else if (strAux.equals("F"))
                        strAux="Diferencia Inventario";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    
                    dlbValorOriTotOrd=((rst.getString("nd_tot")==null)?0:objUti.redondear(rst.getDouble("nd_tot"),objZafParSis.getDecimalesMostrar()));
                   
                    System.out.println(" >>> "+ dlbValorOriTotOrd );
                        
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
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
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
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
         
    
    
    
    private void cargarRegistroInsert(){
       try{//odbc,usuario,password
                conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (conCab!=null){
                    stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                    sSQL= "SELECT co_emp,co_loc,co_tipdoc, co_doc " +
                          " FROM tbm_cabMovInv" +
                          " Where co_emp = "  + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                          " and co_loc = "    + objZafParSis.getCodigoLocal() +
                          " AND co_tipDoc="   + objTipDoc.getco_tipdoc() +
                          " AND co_doc="      + txtDoc.getText() ;
                                System.out.println(sSQL);
                    rstCab=stmCab.executeQuery(sSQL);
                    if(rstCab.next()){
                        rstCab.last();
                        objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                        for (int i=0;i<objTblMod.getRowCountTrue();i++){                                            
                            tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CODITM).toString(), i, INT_TBL_ITMORI);
                            tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CODBOD).toString(), i, INT_TBL_BODORI);
                            tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CANMOV).toString(), i, INT_TBL_CANORI);
                        }                               
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
    
   
    public class mitoolbar extends ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
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
            @Override
        public boolean anular()
        {
            strAux=objTooBar.getEstadoRegistro();
            
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
             
            
            if(!verificaItmUni())
                return false;
            
             
            if(!objUltDocPrint.Verificar_Doc_Asociado(txtDoc.getText(),txtCodTipDoc.getText()))
                      return false;
          
            if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
                      return false;
               
            
            if (objUltDocPrint.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText())))
                       return false;
            
            
            
            if (!anularReg())
                return false;
            
            
             recostearItmGrp();
            
            objTooBar.setEstadoRegistro("Anulado");
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return true;
        }

         
        
            @Override
        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (objTblMod.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

            @Override
        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (objTblMod.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

            @Override
        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (objTblMod.isDataModelChanged())
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        
        

         
        

            @Override
        public void clickInsertar()
        {
            try
            { 
                 
                 if (objTblMod.isDataModelChanged())
                {
                   
                    isRegPro();
                }
                 
                
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }

                    clnTextos();
                  
                     datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                     java.util.Date dateObj =datFecAux;
                     java.util.Calendar calObj = java.util.Calendar.getInstance();
                     calObj.setTime(dateObj);
                     txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                     
                     /*JoseMario 6/Jun/2016*/
                    java.sql.Connection conLoc;
                    conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                    if(conLoc!=null){
                        cargarTipEmp(conLoc);
                    }
                    conLoc.close();
                    conLoc=null;
                     /*JoseMario 6/Jun/2016*/
                    
                     
                    txtCot.setEnabled(true);
                    txtSub.setEditable(false); //***
                    txtIva.setEditable(false);  //***
                    txtTot.setEditable(false);  //***
                    
            
                    cboForPag.setSelectedIndex(0);
                    if (!txtCodTipDoc.getText().equals("")){
                        cargaNum_Doc_OrdCom();                        
                        txtOrdCom.requestFocus();
                        txtOrdCom.setSelectionStart(0);
                        txtOrdCom.setSelectionEnd(txtOrdCom.getText().length());
                    }
                    
              
                    
                    cargaListadoFormaPago();
                    objDiario.setEditable(true);
                    setEditable(true);
                     objTblMod.setDataModelChanged(false);
                     blnHayCam=false;
                
                 
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        
        
        
        
        
            @Override
        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        
        
         
        
            @Override
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        
        
            @Override
        public boolean eliminar()
        {
            try
            {
                strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                
                 if(!verificaItmUni())
                     return false;
                
                
                 if(!objUltDocPrint.Verificar_Doc_Asociado(txtDoc.getText(),txtCodTipDoc.getText()))
                      return false;
          
                
                 if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
                      return false;
                
                   
                 if (objUltDocPrint.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText())))
                       return false;
           
                   
                      if (!eliminarReg())
                       return false;
          
                
                     recostearItmGrp();
                 
          
                
                
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
               objTblMod.setDataModelChanged(false);    
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        
        @Override
        public boolean insertar()
        {
            boolean blnRes = false;

            if (validaCampos()) {
             if(insertarReg())
             {
                blnRes = true;
                recostearItmGrp();
                objTblMod.setDataModelChanged(false);
                blnHayCam = false;
             }
            }
            return blnRes;
        }

        
        
        
    //**************************************************************
         
           private void calcularSaldoMOD(){
            try
             {
              java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
              if (con!=null)
              {   
         /////    System.out.println(">>Glo "+ GLO_strArreItm +"  >> "+ Glo_intCodSec);
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
                  sSQL="select max(a.ne_secemp) " +
                 " from tbm_cabmovinv as a " +
                 " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                 " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                 " where  a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and b.co_itm="+rstMae2.getInt(1)+" and a.ne_secemp <  "+Glo_intCodSec; //+" "+
                 //" order by a.ne_secemp";
               
              ////  System.out.println("SQL 2 >> "+ sSQL );
                  
                  rstMae = stmMonCre.executeQuery(sSQL);    
                  if(rstMae.next()) strSec=rstMae.getString(1);
                  rstMae.close();  
                     
                  
              ////     System.out.println("SEC >> "+ strSec );
                  
                     /*
                  sSQL="select a.ne_secemp ,round(b.nd_exi,2) as ndexi,round(b.nd_valexi,2) as ndvalexi" +
                 " from tbm_cabmovinv as a " +
                 " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                 " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                 " where  a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and b.co_itm="+rstMae2.getInt(1)+" and a.ne_secemp <  "+Glo_intCodSec+" " +
                 " order by a.ne_secemp";
                     rstMae = stmMonCre.executeQuery(sSQL);
                     rstMae.last();
                      System.out.println("SEC>> "+ rstMae.getInt(1) );
                  
                      double ndexi=rstMae.getDouble(2);
                      double ndvalexi=rstMae.getDouble(3);
                    */
                     
                 sSQL="select a.ne_secemp ,round(b.nd_exi,2) as ndexi,round(b.nd_valexi,2) as ndvalexi" +
                 " from tbm_cabmovinv as a " +
                 " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                 " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                 " where  a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and b.co_itm="+rstMae2.getInt(1)+" and a.ne_secemp="+strSec+" "+
                 " order by a.ne_secemp,b.co_reg  desc";
            ///    System.out.println(" SQL 3 >> "+sSQL);
                      rstMae = stmMonCre.executeQuery(sSQL);
                      double ndexi=0;
                      double ndvalexi=0;
                     
                    
                      if(rstMae.next()){
                    ///      System.out.println("SEC>> "+ rstMae.getInt(1) );
                         ndexi=rstMae.getDouble(2);
                         ndvalexi=rstMae.getDouble(3);
                     }
                      
                      
                      
                    sSQL= "select a.ne_secemp" +
                    ",a.co_Emp,a.co_loc ,a.co_doc,a.co_tipdoc,b.co_itm,b.nd_can,b.nd_pordes,b.nd_cosuni,b.nd_exi,b.nd_valexi" +
                    ",round( ( (b.nd_can*b.nd_cosuni) - ( (b.nd_can*b.nd_cosuni)*b.nd_pordes/100 )),2) as  total" +
                    " ,a.st_reg,b.co_reg as coreg FROM  tbm_cabmovinv as a" +
                    " inner join tbm_detmovinv as b on(a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and " +
                    " a.co_doc=b.co_doc) inner join tbm_inv as c on(b.co_itm=c.co_itm and a.co_Emp=c.co_emp)" +
                    " where  b.co_itm is not null and a.co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                    " and c.co_itm="+rstMae2.getInt(1)+"  and a.ne_secemp >= "+Glo_intCodSec+"  order by a.ne_secemp,a.co_emp"; 
                  
                 ///    System.out.println("SQL 4 >> "+ sSQL );
                    
                    rstMae = stmMonCre.executeQuery(sSQL);  //and a.ne_secemp >=  "+rstMae.getInt(1)+"
                  
                    String streg="";
                    while(rstMae.next()){
                      streg = rstMae.getString("st_reg");
                        if(streg.equalsIgnoreCase("I")){ } else {
                           ndexi=ndexi + rstMae.getDouble(7);
                           ndvalexi = ndvalexi + rstMae.getDouble("total");
                        }
                      
                           sSQL= "UPDATE tbm_detmovinv SET nd_exi="+ndexi+",nd_valexi="+ndvalexi+"  WHERE co_emp="+rstMae.getInt(2)+" " +
                                 " and co_loc="+rstMae.getInt(3)+" and co_tipdoc="+rstMae.getInt(5)+" and " +
                                 " co_doc="+rstMae.getInt(4)+" and co_itm="+rstMae.getInt(6)+" and co_reg="+rstMae.getInt("coreg");
                     ///     System.out.println("SQL 5 >> "+ sSQL );
                                 pst = con.prepareStatement(sSQL);
                                 pst.executeUpdate();
                         
                      }
                    // System.out.println(">> "+ rstMae2.getInt(1) );
                    } 
                 rstMae=null;
                 rstMae2.close();
                 rstMae2=null;
               }
           ///   System.out.println("FIN ......OK ");
              con.close();
              con=null;
            }catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
        }
        
       
        
      
          
        //*********************************************************************************************
        //*********************************************************************************************
                   
        
         
         private void recostearItmGrp()
         {
            int i,j;
             try
             {    
              java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
              if (con!=null)
              {
                     for(i=0; i<tblDat.getRowCount(); i++){  
                      if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
                              objUti.recostearItm2009DesdeFecha( this, objZafParSis, con,  objZafParSis.getCodigoEmpresa(), tblDat.getValueAt(i,INT_TBL_CODITM).toString(), txtFecDoc.getText(), "dd/MM/yyyy");
                     }}
              }
              con.close();
              con=null;
             }
            catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
         }
          
         
         
/**
 * verifica que el total de la orden de compra no sobrepase el marguen  establecido del
 * 0.5 % + -  con el valor nuevo   
 * @return true : si esta dentro del marguen
 * @return false : si ha sobrepasado el marguen
 * 
 */
private boolean verificaMarTot(){
 boolean blnRes=false;
 try{
 double dlbTotOrd = objUti.redondear( (txtTot.getText().equals("")?"0":txtTot.getText()) , objZafParSis.getDecimalesMostrar() );
 dlbTotOrd = dlbTotOrd -  dlbValorOriTotOrd;
 if(dlbTotOrd > 0 ){
 double dlbPor = objUti.redondear(  ((dlbTotOrd*100)/dlbValorOriTotOrd)  , 2);
 dlbPor = Math.abs( dlbPor );
          
  if( dlbPor <= 0.50 )
      blnRes=true;
 }else  blnRes=true;
 
 }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;   
}

         
        
private boolean verificaRecFacPrv(){
  boolean blnRes=true;
  java.sql.Connection con;
  java.sql.Statement stm;
  java.sql.ResultSet rst;
  String strSql="";
   try{
      con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
      if(con!=null){
        stm=con.createStatement();
 
        strSql=" SELECT co_reg  " +
        "  FROM tbm_pagmovinv  WHERE  co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND " +
        " co_tipDoc = "+txtCodTipDoc.getText()+" AND co_doc = "+txtDoc.getText()+"  and nd_monchq > 0  ";
        rst=stm.executeQuery(strSql);
        if(rst.next()){
          blnRes=false;
        }
       rst.close();
       rst=null;
       stm.close();
       stm=null;
       con.close();
       con=null;

   }}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  return blnRes;
 }



private boolean verificaNumPag(){
  boolean blnRes=false;
  java.sql.Connection con;
  java.sql.Statement stm;
  java.sql.ResultSet rst;
  String strSql="";
  int intNumPag=0;
  int intSumDiaCreOri=0;
  int intSumDiaCrePag=0;
  try{
      con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
      if(con!=null){
        stm=con.createStatement();
        
        intNumPag=tblOrdComForPag.getRowCount();
        
        strSql=" SELECT count(co_reg) as numpag , sum(ne_diacre) as diascre " +
        "  FROM tbm_pagmovinv  WHERE  co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND " +
        " co_tipDoc = "+txtCodTipDoc.getText()+" AND co_doc = "+txtDoc.getText();   
        rst=stm.executeQuery(strSql);
        if(rst.next()){
           intSumDiaCreOri= rst.getInt("diascre");
           if(intNumPag==rst.getInt("numpag"))
            blnRes=true;
          
        }
       rst.close();
       rst=null;
       stm.close();
       stm=null;
       con.close();
       con=null;
       
       if(!blnRes){
          return false;
       }
       
       for(int i=0; i<tblOrdComForPag.getRowCount(); i++){
          intSumDiaCrePag += Integer.parseInt((tblOrdComForPag.getValueAt(i, 1)==null?"0":(tblOrdComForPag.getValueAt(i, 1).toString().equals("")?"0":tblOrdComForPag.getValueAt(i, 1).toString()))); 
       }
       if(intSumDiaCreOri==intSumDiaCrePag)
         blnRes=true;
         else  blnRes=false;
       
       System.out.println("DiaCre: "+ intSumDiaCreOri +" -- "+ intSumDiaCrePag );
       
   }}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
  return blnRes;
 }
         
            @Override
        public boolean modificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            
         
            if(!verificaItmUni())
                return false;
            
            
            
            if(!objUltDocPrint.Verificar_Doc_Asociado(txtDoc.getText(),txtCodTipDoc.getText()))
                  return false;
            
             if (objUltDocPrint.isPagoDocumento_aso(objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
                       return false;
             } 
            

            
            
            if(!verificaNumPag()){
                MensajeInf("No es posible modificar.\nEl Número de pagos no es igual al original.");
                return false;
            }


            if(!verificaRecFacPrv()){
                MensajeInf("No es posible modificar.\n Tiene asociado a factura del proveedor. ");
                return false;
            }

            
            
              
            if (!validaCampos())
                return false;
            
         
            
            if (!actualizarReg())
                return false;
            
            
             recostearItmGrp();
            
              objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return true;
        }
        
        
          
          
                   
         private boolean verificaItmUni(){
            boolean  blnRes=true;
             try{
             for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                       int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                       int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));
                      // if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                           if(intCodItm != intCodItmAct){
                               MensajeInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                               blnRes=false;
                               break;
                           }
                      // }
               }}
             }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
           return blnRes;     
         }
          
            @Override
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (objTblMod.isDataModelChanged())
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                clnTextos();
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            objTblMod.setDataModelChanged(false);
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
            cargarRegistroInsert();
             objTblMod.setDataModelChanged(false);
            return true;
        }
        
            @Override
        public boolean afterModificar() {
            return true;
        }
        
            @Override
        public boolean afterVistaPreliminar() {
            return true;
        }
                

     public void Actualizar_valores(){
        java.sql.Connection conTipDoc;
        java.sql.PreparedStatement pstReg;
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                String sql="UPDATE tbm_cabmovinv set nd_sub="+txtSub.getText()+", nd_valiva="+txtIva.getText()+",nd_tot="+txtTot.getText()+" where co_emp="+objZafParSis.getCodigoEmpresa()+"" +
                " and co_loc="+objZafParSis.getCodigoLocal()+" and co_tipdoc="+txtCodTipDoc.getText()+" and co_doc="+txtDoc.getText();
                pstReg = conTipDoc.prepareStatement(sql);
                pstReg.executeUpdate();
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
   }


   
                  
        
        
            @Override
   public boolean imprimir(){

        cargarRepote(0);
//
//        int intCodEmp=objZafParSis.getCodigoEmpresa();
//        int intCodLoc=objZafParSis.getCodigoLocal();
//        String strTit=objZafParSis.getNombreMenu();
//        String strNomEmp=retNomEmp(intCodEmp);
//        String strTelCli=""; //retTelCli();
//        String strRucCli=""; //retRucCli();
//        String strCodForPag = cboForPag.getSelectedItem() +"";
//
//         Actualizar_valores();
//
//        Connection conIns;
//        try
//        {
//            conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//        try
//        {
//            if(conIns!=null){
//
//
//                String strNombreEmp = objZafParSis.getNombreEmpresa().substring(0,3);
//
//
//                String DIRECC_RET="";
//                DIRECC_RET=DIRECCION_REPORTE;
//
//                if(objZafParSis.getCodigoEmpresa()==3) DIRECC_RET=DIRECCION_REPORTE2;
//
//                if(objZafParSis.getCodigoEmpresa()==2) DIRECC_RET=DIRECCION_REPORTE_CAS;
//                if(objZafParSis.getCodigoEmpresa()==4) DIRECC_RET=DIRECCION_REPORTE_DIM;
//
//
//                 if(strNombreEmp.toUpperCase().trim().equals("ECU")){
//                    DIRECC_RET=DIRECCION_REPORTE3;
//                }
//
//
//
//
//
//
//
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECC_RET);
//                JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
//
//                // Second, create a map of parameters to pass to the report.
//                Map parameters = new HashMap();
//                parameters.put("tit", strTit);
//                parameters.put("numDoc", txtDoc.getText());
//                parameters.put("codEmp", ""+intCodEmp);
//                parameters.put("codLoc", ""+intCodLoc);
//                parameters.put("fecCot", txtFecDoc.getText());
//                parameters.put("codCli", txtPrvCod.getText());
//                parameters.put("nomCli", txtPrvNom.getText());
//                parameters.put("rucCli", strRucCli);
//                parameters.put("dirCli", txtPrvDir.getText());
//                parameters.put("nomVen", txtComNom.getText());
//                parameters.put("obs1", txaObs1.getText());
//                parameters.put("obs2", txaObs2.getText());
//                parameters.put("subTot", new Double(txtSub.getText()));
//                parameters.put("iva", new Double(txtIva.getText()));
//                parameters.put("totFin", new Double(txtTot.getText()));
//                parameters.put("nomEmp", strNomEmp);
//                parameters.put("telCli", strTelCli);
//                parameters.put("forPag", strCodForPag);
//                parameters.put("atencion", txtAte.getText());
//                parameters.put("CodTipDoc", txtCodTipDoc.getText());
//                parameters.put("CodOrdCom", txtOrdCom.getText());
//
//
//
//                JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//
//                //para imprimir
//               // JasperManager.printReport(report, true);
//
//
//
//                 //Configurar el servicio de impresión directa.
//                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
//                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
//                /*
//                 * Ejemplos de nombres de impresora:
//                 * Impresora local: Lexmark 640 Series
//                 * Impresora de red: \\\\CONTABILIDAD2\\Epson LQ-870 ESC/P 2 (Copiar 2)
//                 */
//                String strImp="pint_compras";
//                javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strImp , null);
//                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
//                printServiceAttributeSet.add(printerName);
//                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, report);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//                objJRPSerExp.exportReport();
//
//                conIns.close();
//                conIns=null;
//
//                  //para vista preliminar
//                      /// JasperViewer.viewReport(report, false);
//
//
//            }
//        }
//        catch (JRException e)
//        {
//            System.out.println("Excepción: " + e.toString());
//        }
//
//        }
//
//        catch(SQLException ex)
//        {
//            System.out.println("Error al conectarse a la base");
//        }
               return true;
     }
            
            


private void cargarRepote(int intTipo){
   if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.setIndFunEje(intTipo);
        objThrGUI.start();
    }
}



        @Override
  public boolean vistaPreliminar(){

       cargarRepote(1);

//        int intCodEmp=objZafParSis.getCodigoEmpresa();
//        int intCodLoc=objZafParSis.getCodigoLocal();
//        String strTit=objZafParSis.getNombreMenu();
//        String strNomEmp=retNomEmp(intCodEmp);
//        String strTelCli=""; //retTelCli();
//        String strRucCli=""; //retRucCli();
//        String strCodForPag = cboForPag.getSelectedItem() +"";
//
//         Actualizar_valores();
//
//        Connection conIns;
//        try
//        {
//            conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//        try
//        {
//            if(conIns!=null){
//
//
//                String strNombreEmp = objZafParSis.getNombreEmpresa().substring(0,3);
//
//                String DIRECC_RET="";
//                if(objZafParSis.getCodigoEmpresa()==3) DIRECC_RET=DIRECCION_REPORTE2;
//                else   DIRECC_RET=DIRECCION_REPORTE;
//
//
//
//                if(strNombreEmp.toUpperCase().trim().equals("ECU")){
//                    DIRECC_RET=DIRECCION_REPORTE3;
//                }
//
//
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECC_RET);
//                JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
//
//                // Second, create a map of parameters to pass to the report.
//                Map parameters = new HashMap();
//                parameters.put("tit", strTit);
//                parameters.put("numDoc", txtDoc.getText());
//                parameters.put("codEmp", ""+intCodEmp);
//                parameters.put("codLoc", ""+intCodLoc);
//                parameters.put("fecCot", txtFecDoc.getText());
//                parameters.put("codCli", txtPrvCod.getText());
//                parameters.put("nomCli", txtPrvNom.getText());
//                parameters.put("rucCli", strRucCli);
//                parameters.put("dirCli", txtPrvDir.getText());
//                parameters.put("nomVen", txtComNom.getText());
//                parameters.put("obs1", txaObs1.getText());
//                parameters.put("obs2", txaObs2.getText());
//                parameters.put("subTot", new Double(txtSub.getText()));
//                parameters.put("iva", new Double(txtIva.getText()));
//                parameters.put("totFin", new Double(txtTot.getText()));
//                parameters.put("nomEmp", strNomEmp);
//                parameters.put("telCli", strTelCli);
//                parameters.put("forPag", strCodForPag);
//                parameters.put("atencion", txtAte.getText());
//                parameters.put("CodTipDoc", txtCodTipDoc.getText());
//                parameters.put("CodOrdCom", txtOrdCom.getText());
//
//                JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                JasperViewer.viewReport(report, false);
//            }
//        }
//        catch (JRException e)
//        {
//             objUti.mostrarMsgErr_F1(jfrThis, e);
//             return false;
//        }
//
//        }
//
//        catch(SQLException ex)
//        {
//            objUti.mostrarMsgErr_F1(jfrThis, ex);
//            return false;
//        }
               return true;
         }
      
  
  
  
  
  /**
 * Aqui se verifica si el proveedor estan dado de alto por el respectivas personas
 * 
 * @param conn conecion de la base
 * @return true: si esta todo bien
 * @return false: si necesita ser dado de alta 
 */        
private boolean verificaEstCliPrv(java.sql.Connection conn){
  boolean blnRes=true;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strMesj="";
  try{
      if(conn!=null){
          stmLoc=conn.createStatement();
          
          strSql="SELECT tx_nom FROM TBM_CLI WHERE CO_EMP="+objZafParSis.getCodigoEmpresa()+" AND CO_CLI="+txtPrvCod.getText()+" and ST_REG='N'";
          rstLoc=stmLoc.executeQuery(strSql);
          if(rstLoc.next()){
              strMesj+="<html>LOS DATOS DEL PROVEEDOR: <FONT COLOR=\"blue\"> "+rstLoc.getString("tx_nom")+"</font>  TIENE QUE SER REVISADO.</html>\n";
              blnRes=false;
          }
          if(!blnRes){
             mostrarMsg(strMesj);
          }
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;
     }}catch (java.sql.SQLException e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
       catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    return blnRes;
  }       
        
  
  

public boolean insertarReg(){
   boolean blnRes=false;
   java.sql.Connection conIns;
   int intSecEmp=0, intSecGrp=0;
   try{ 
       calculaSubtotalTodosLosItem();  
       conIns=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
       
        if(!abrirConRem())
             return false;

           if (conIns!=null){
                

           if(INTCODREGCEN!=0){
                conRemGlo.setAutoCommit(true);
                intSecEmp = objUltDocPrint.getNumSecDoc(conRemGlo, objZafParSis.getCodigoEmpresa() );
                intSecGrp = objUltDocPrint.getNumSecDoc(conRemGlo, objZafParSis.getCodigoEmpresaGrupo() );
                conRemGlo.setAutoCommit(false);
           }else{
                intSecEmp = objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresa() );
                intSecGrp = objUltDocPrint.getNumSecDoc(conIns, objZafParSis.getCodigoEmpresaGrupo() );
           }

           conIns.setAutoCommit(false);

               if(!verificaEstCliPrv(conIns))
                   return false;
                
                objCiudad_dat.setCiudad(objCliente_dat.getCodciudad());
                txtDoc.setText(""+objUltDocPrint.getCodigoDocumento(conIns, objTipDoc.getco_tipdoc()));
                if(Integer.parseInt(txtOrdCom.getText())>0){
                    if(objUltDocPrint.getExisteDoc( Integer.parseInt( txtOrdCom.getText()) ,objTipDoc.getco_tipdoc(), Integer.parseInt(txtDoc.getText()) ) ){
                        MensajeInf("No. de Orden de Compra ya duplicada ");
                        txtOrdCom.requestFocus();
                        txtOrdCom.selectAll();
                        conIns.close();
                        conIns=null;
                        return false;
                }}
              intNumDoc = Integer.parseInt(txtDoc.getText());

                                if(insertarCab(conIns, intNumDoc, intSecEmp, intSecGrp )){
                                    if(insertarDet(conIns, conRemGlo, intNumDoc )){
                                      if(insertDetPag(intNumDoc, conIns, "I")){  
                                        if(objDiario.insertarDiario(conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  txtDoc.getText(), txtOrdCom.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
                                           conIns.commit();
                                                if(INTCODREGCEN!=0){
                                                    conRemGlo.commit();
                                                }
                                           blnRes=true;
                                       }else { conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }
                                     }else{ conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }
                                  }else{ conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }
                                }else{ conIns.rollback();  if(INTCODREGCEN!=0) conRemGlo.rollback(); }


                              if(blnRes){
                               for(int i=0; i<tblDat.getRowCount(); i++){ 
                                    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
                                          tblDat.setValueAt("E", i, INT_TBL_ESTADO);
                                          tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODITM).toString() , i, INT_TBL_ITMORI);
                                          tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODBOD).toString() , i, INT_TBL_BODORI);
                                          tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CANMOV).toString() , i, INT_TBL_CANORI);
                             }}}           

                             conIns.close();
                             conIns=null;
                             //objInvItm.limpiarObjeto();    
                             if(INTCODREGCEN!=0){
                                    conRemGlo.close();
                                    conRemGlo=null;
                                 }

                             STR_ESTREG="I";  
                             objTblMod.clearDataSavedBeforeRemoveRow();
                             objTblMod.initRowsState();
                 }}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
                   catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
      return blnRes;                  
    }



        
        
        
        
                      
                      
     private boolean insertarCab(java.sql.Connection conIns, int intNumDoc, int intSecEmp, int intSecGrp  ){
          boolean blnRes = true;
          java.sql.Statement stm;
          java.sql.Statement stmLoc;
          java.sql.ResultSet rstLoc;
          String strNomBen="";
          int intCodBen=0;
          String strSQL, strFecha, strFecSis;
          try{
              stmLoc=conIns.createStatement();
              
                Glo_intCodSec=intSecEmp;
                strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                     
                String strconinv="F";
                for(int i=0; i<tblDat.getRowCount();i++){
                 if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
                   String strEst=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                   String strEstSer=(tblDat.getValueAt(i, INT_TBL_ITMSER)==null?"":tblDat.getValueAt(i, INT_TBL_ITMSER).toString());
                   if( (strEst.equals("N")) && (strEstSer.equals("N")) ){
                          strconinv="P";
                }}}

                if(strconinv.equals("P"))
                 if(strMerIngEgr.equals("N")) strconinv="F";
               //INT_TBL_IEBODFIS  st_coninv  st_autingegrinvcon
                   
                  strSQL="SELECT co_reg, tx_benchq FROM tbm_benchq WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
                  " AND co_cli="+txtPrvCod.getText()+" and st_reg='P'";
                  rstLoc=stmLoc.executeQuery(strSQL);
                  if(rstLoc.next()){
                     intCodBen=rstLoc.getInt("co_reg");
                     strNomBen=rstLoc.getString("tx_benchq");
                  }
                  rstLoc.close();
                  rstLoc=null;
                  stmLoc.close();
                  stmLoc=null;


                   String strEmiChq="";
                  if(chkEmiChq.isSelected()) strEmiChq="S";

                 strSQL = "INSERT INTO  tbm_cabMovInv (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli,"+
                 " tx_nomven, ne_numDoc, ne_numCot, tx_numPed, tx_obs1, tx_obs2, nd_sub , nd_tot, nd_valiva, nd_porIva, co_forPag, fe_ing, co_usrIng,fe_ultMod, co_usrMod , tx_desforpag ,st_reg" + 
                 " ,co_forret,tx_vehret,tx_choret,ne_secgrp,ne_secemp, " +
			// " ne_secgrpant, ne_secempant  , " +
			 "st_regrep ,st_coninv, co_ben, tx_benchq , st_emiChqAntRecFacPrv, st_imp, tx_numDoc2 ) " +
                 " VALUES ("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+intNumDoc+",'"+strFecha+"',"+txtPrvCod.getText()+","+txtComCod.getText()+",'"+txtAte.getText()+"','"+
                 txtPrvNom.getText()+"','"+txtPrvDir.getText()+"','"+objCliente_dat.getIdentificacion()+"','"+objCliente_dat.getTelefono()+"','"+objCiudad_dat.getNombrelargo()+"','"+
                 txtComNom.getText()+"',"+txtOrdCom.getText()+","+((txtCot.getText().equals(""))?"0":txtCot.getText())+","+objUti.codificar(txtFacPrv.getText())+",'"+
                 txaObs1.getText()+"','"+txaObs2.getText()+"',"+objUti.redondeo(Double.parseDouble(txtSub.getText()),6)+","+objUti.redondeo(Double.parseDouble(txtTot.getText()),6)+","+
                 txtIva.getText()+","+objUti.redondeo(dblPorIva , 2) +","+Integer.parseInt(""+vecForPag.get(cboForPag.getSelectedIndex()))+",'"+ strFecSis+"',"+
                 objZafParSis.getCodigoUsuario()+",'"+ strFecSis+"',"+objZafParSis.getCodigoUsuario()+",'"+cboForPag.getSelectedItem()+ "','A'" +
                 ","+ txtCod.getText() +",'"+ txtVehRet.getText()+"','"+ txtChoRet.getText()+"',"+intSecGrp+","+intSecEmp+"  " +
			 //","+(intSecGrp-1)+","+(intSecEmp-1)+" " +
			 ",'I', '"+strconinv+"', "+intCodBen+", '"+strNomBen+"' ,"+(strEmiChq.equals("")?null:"'"+strEmiChq+"'")+" ,'S', " + objUti.codificar(txtNumPed.getText()) + " )";
                 strSQL += " ; Update tbm_cabcotcom set st_reg = 'F' where co_emp = " + objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() + " and co_cot = " + ((txtCot.getText().equals(""))?"0":txtCot.getText());
                GLOCODCLI=txtPrvCod.getText(); 
                stm = conIns.createStatement();
                stm.executeUpdate(strSQL);
                
                strSQL="INSERT INTO tbm_retmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_motdoc, st_regrep) " + 
                " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intNumDoc+", " +
                " 1, "+intCodMotBien+", 'I' )";
                stm.executeUpdate(strSQL);
                          
                          
                stm.close();
                stm=null;
          }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
         return blnRes;
      }
        
        
     
     
      
     
     

private boolean insertarDet(java.sql.Connection conIns, java.sql.Connection connRemota, int intNumDoc  ){
  boolean blnRes = true;
  int intControl=0;
  double dlbCanMov=0;
  String strSQL;
  int intTipCli=-1;
  int intCodItm=0;
  int intCodBod=0;
  int intReaActStk=0;
  StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
  java.sql.Statement stmDet;
  try{
      GLO_strArreItm="";
   
      String str_MerIEFisBod="S";
      double dbl_canConIE=0.00;
      intTipCli= objUltDocPrint.ValidarCodigoCliente(txtPrvCod.getText(),conIns);
       
      //objInvItm.inicializaObjeto();
      
      for(int i=0; i<tblDat.getRowCount(); i++){ 
        if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
          
          intCodItm=Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM)); 
          intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
          dlbCanMov=Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
            
          dbl_canConIE = Double.parseDouble(  (tblDat.getValueAt(i, INT_TBL_CANMOV )==null)?"0":tblDat.getValueAt(i, INT_TBL_CANMOV ).toString());
          String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
          str_MerIEFisBod="N";  
        /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
          if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
             if(strEstFisBod.equals("N")){
              if(strMerIngEgr.equals("S")){   
                str_MerIEFisBod="S";
                dbl_canConIE=0;
             }}
             ZafStkInv invStock= new ZafStkInv(objZafParSis); 
            //objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, 1, strEstFisBod, strMerIngEgr, strTipIngEgr, 1); 
            if(invStock.actualizaInventario(conIns, objZafParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK_DIS, "+", 1, obtenerArrItmInv(conIns, objZafParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                            if(!invStock.actualizaInventario(conIns, objZafParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK, "+", 0, obtenerArrItmInv(conIns, objZafParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                                  return false;
                            }
                        }else{
                            return false;
                        }
            intReaActStk=1;
          }
        /****************************************************************************************************/
           if(intControl!=0)    
             GLO_strArreItm=GLO_strArreItm+",";
    
             GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
             intControl++;

             if (i>0)
             stbins.append(" UNION ALL ");/* JoséMario 25/Sep/2015 */
             stbins.append("SELECT "+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+intNumDoc+","+(i+1)+",'"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"', " +
             ""+tblDat.getValueAt(i, INT_TBL_CODITM)+","+tblDat.getValueAt(i, INT_TBL_CODITM)+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"','"+objUti.remplazarComillaSimple(tblDat.getValueAt(i, INT_TBL_DESITM))+"', " +
             ""+tblDat.getValueAt(i, INT_TBL_CODBOD)+","+Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV))+","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+"," +
             ""+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+"," +
             ""+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objZafParSis.getDecimalesMostrar())+",'"+((tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)?"N":(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "'," +
             ""+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+"," +
             ""+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos()) + ",'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" " +
             ","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+","+
             ""+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objZafParSis.getDecimalesMostrar())+" "+
             " ");

             tblDat.setValueAt(""+(i+1) , i, INT_TBL_CODREG);
      }}
                                                 
      if(intControl > 0 ){
        strSQL = "INSERT INTO tbm_detMovInv (co_emp, co_loc, co_tipdoc , co_doc, co_reg, tx_unimed, co_itm, co_itmact, tx_codAlt,tx_codAlt2, tx_nomItm, co_bod, " +
        " nd_can, nd_cosUni, nd_cosUniGrp , nd_preuni, nd_porDes, st_ivaCom , nd_costot, nd_costotgrp, nd_tot , st_regrep, st_meringegrfisbod , nd_cancon " +
        " ,nd_preunivenlis, nd_pordesvenmax ) "+stbins.toString();
        stmDet=conIns.createStatement();
        stmDet.executeUpdate(strSQL);
        stmDet.close();
        stmDet=null;
      }
      stbins=null;
      if(intReaActStk==1){
//        if(!objInvItm.ejecutaActStock(conIns, intTipCli))
//           return false;
//         
//        if(connRemota!=null){
//           if(!objInvItm.ejecutaActStock(connRemota, intTipCli ))
//             return false;
//           if(!objInvItm.ejecutaVerificacionStock(connRemota, intTipCli))
//             return false;
//        }else{
//          if(!objInvItm.ejecutaVerificacionStock(conIns, intTipCli ))
//            return false;
//        } 
      
      }
     //objInvItm.limpiarObjeto();      
 }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
  catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}
        
        
        
        
           
      
      
        
        
              
private boolean insertDetPag(int intNumDoc, java.sql.Connection conInsPag, String strRegRep){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strApliRet="";
 String strSql="";
 try{
    stmLoc=conInsPag.createStatement();
  for(int i=0; i<tblOrdComForPag.getRowCount();i++){
        
     strApliRet=(tblOrdComForPag.getValueAt(i, 8)==null)?"":tblOrdComForPag.getValueAt(i, 8).toString(); 
     
     int FecPagDoc[] =  txtFecDoc.getFecha(tblOrdComForPag.getValueAt(i, 2).toString());
     String strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];
     
     strSql="INSERT INTO  tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, " + 
     " ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra , co_tipret, tx_codsri ,tx_aplret, nd_basimp, st_regrep )"+
     " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+
     intNumDoc+", "+(i+1)+", "+((tblOrdComForPag.getValueAt(i, 1).equals(""))?"0":tblOrdComForPag.getValueAt(i, 1))+", "+
     "'"+strFechaPag+"', "+((tblOrdComForPag.getValueAt(i, 3)==null)?"0":tblOrdComForPag.getValueAt(i, 3))+", "+
     ""+objUti.redondeo(Double.parseDouble( (tblOrdComForPag.getValueAt(i, 4)==null)?"0":tblOrdComForPag.getValueAt(i, 4).toString() ),6)+", "+
     ((tblOrdComForPag.getValueAt(i, 5)==null)?"0":tblOrdComForPag.getValueAt(i, 5).toString())+","+
     ((tblOrdComForPag.getValueAt(i, 6)==null)?"0":tblOrdComForPag.getValueAt(i, 6).toString())+",'"+
     ((tblOrdComForPag.getValueAt(i, 7)==null)?"":tblOrdComForPag.getValueAt(i, 7).toString())+"','"+
     ((tblOrdComForPag.getValueAt(i, 8)==null)?"":tblOrdComForPag.getValueAt(i, 8).toString())+"',"+
               // ANTERIOR  (strApliRet.equals("S")?txtSub.getText():(strApliRet.equals("I")?txtIva.getText():"0") )+",'I' )";
     ((tblOrdComForPag.getValueAt(i, 9)==null)?"0":tblOrdComForPag.getValueAt(i, 9).toString())+",'I' )";

     // System.out.println("> "+ strSql );
     stmLoc.executeUpdate(strSql);  
             
//     if(strApliRet.equals("S")){
//       strSql="INSERT INTO tbm_retmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_motdoc, nd_porret, nd_basimp, nd_valret, st_regrep) " +
//       " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intNumDoc+", " +
//       ""+(i+1)+"" )";  
//     }
  }
  stmLoc.close();
  stmLoc=null;
  
  blnRes=true;  
  }catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}
            
        
        
        
        
        
          
        
        
        @Override
        public void clickConsultar(){
//            clnTextos();
            txtCot.setEnabled(false);
             txtSub.setEditable(false); //***
                    txtIva.setEditable(false);  //***
                    txtTot.setEditable(false);  //***
           
             //if(!txtFecDoc.isFecha())
               /// txtFecDoc.setHoy(); 
                    
                    txtFecDoc.setText("");
                    
            objDiario.setEditable(false);
            //setEditable(false);
        }


private boolean validarTipoDoc(){

   if(txtCodTipDoc.getText().equals("")){
       MensajeInf("NO HAY TIPO DE DOCUMENTO PARA LA BUSQUEDA..");
       return false;
   } else
      return true;

}



        public boolean consultarReg() {
            /* Esto Hace en caso de que el modo de operacion sea Consulta */         
            
          if(validarTipoDoc()){
            return _consultar(FilSql());
          }else
             return false;

        }

        @Override
        public void clickModificar(){
                //txtCot.setEditable(false);
                txtSub.setEditable(false); //***
                txtIva.setEditable(false);  //***
                txtTot.setEditable(false);  //***
                    
            objDiario.setEditable(true);  
           
            
            txtNomTipDoc.setEditable(false);
            txtDetTipDoc.setEditable(false);
            txtDoc.setEditable(false);
           
            txtOrdCom.setEditable(false);
            txtNumPed.setEditable(false);
          
             strCodPrv="";
              strDesLarPrv="";   
              strCodCom="";
              strDesLarCom="";
            
            java.awt.Color color = txtCot.getBackground();
            txtCot.setEditable(false);
            txtCot.setBackground(color);
            
            butTipDoc.setEnabled(false);
            
            butPrv.setEnabled(false);
            color = txtPrvCod.getBackground();
            txtPrvCod.setEditable(false);
            txtPrvCod.setBackground(color); 
            color = txtPrvNom.getBackground();
            txtPrvNom.setEditable(false);
            txtPrvNom.setBackground(color);
            color = txtPrvDir.getBackground();
            txtPrvDir.setEditable(false);
            txtPrvDir.setBackground(color);
                    
            setEditable(true);
            
            txtFecDoc.setEnabled(false);
           
            objTblMod.setDataModelChanged(true);
            blnHayCam=false;
            
        }
        /**
         * Metodo verificar que no este anulada
         * @autor: jayapata
         */

private boolean isAnulada(){
 boolean blnRes = false;
 java.sql.Connection conTmp ;
 java.sql.Statement stmTmp;
 String strSQL = "";
 try{
  conTmp = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
  if(conTmp!=null){
     stmTmp = conTmp.createStatement();
     strSQL = "";
     strSQL = " Select count(*) from tbm_cabmovinv ";
     strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and  co_tipDoc = " + txtCodTipDoc.getText() + " and  co_doc = " +  txtDoc.getText() + " and st_reg = 'I' " ;
     if (objUti.getNumeroRegistro(this,objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0){
       blnRes = true;
//      MensajeInf("Documento anulado no se puede modificar");
      }else
          blnRes = false;
      stmTmp.close();
      conTmp.close();
  }

}catch(SQLException Evt){    
    objUti.mostrarMsgErr_F1(jfrThis, Evt);
    blnRes = true;
}catch(Exception Evt)  {
    objUti.mostrarMsgErr_F1(jfrThis, Evt);
    blnRes = true;
}
return blnRes;
}            

            
             ////////////////////////////////************************///**************************************
       
            
            public boolean actualizarReg(){
        
              
                 calculaSubtotalTodosLosItem();
                 
           
              
                           double dblDif;
                           java.util.ArrayList arlActInv=new java.util.ArrayList();
                           strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                          
                           
                           if (!objUltDocPrint.getDocReposicion(Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt( txtOrdCom.getText())))
                           {
                                MensajeInf("Orden de Compra no puede ser modificada.");
                                txtOrdCom.requestFocus();
                                return false;                               
                           }

                           objCiudad_dat.setCiudad(objCliente_dat.getCodciudad());
                           java.sql.Connection conMod;
                            try{
                                   conMod =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                               try{
                                   java.sql.PreparedStatement pstModCabMovInv ;
                                    if (conMod!=null)
                                    {
                                        
                                    conMod.setAutoCommit(false);
                                    /**
                                     * verificar que no este anulada
                                     * @autor: jsalazar
                                     */
                                      if (isAnulada()){
                                           MensajeInf("Documento anulado no se puede modificar");
                                          return false;                                          
                                      }
                                         /*
                                         * Convirtiendo la fecha en formato aaaa/mm/dd
                                         * para poder grabarlo en la base
                                         */
                                        int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                                        String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                                        
                                        /*********************************************\    
                                         * Armando el Update para los datos          *   
                                         * de la cabecera de cotizacion              *
                                        \*********************************************/
                                      
                                    
                
                                         
                                    if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  
                                           
                                          String SQL="Update tbm_cabMovInv set " +
                                               " fe_doc  = '" + strFecha            + "', " +
                                               " co_cli  = "  + txtPrvCod.getText() + ",  " + 
                                               " ne_numCot="  + (txtCot.getText().equals("")?"0":txtCot.getText()) + ",  " + 
                                               " ne_numDoc="  + txtOrdCom.getText()    + ",  " + 
                                               " tx_numdoc2="  + objUti.codificar(txtNumPed.getText())    + ",  " + 
                                               " tx_nomCli='" + txtPrvNom.getText() + "', " +
                                               " tx_dirCli='" + txtPrvDir.getText() + "', " +
                                               " tx_ruc='"    + objCliente_dat.getIdentificacion() + "', " +
                                               " tx_telCli='" + objCliente_dat.getTelefono()       + "', " +
                                               " tx_ciuCli='" + objCiudad_dat.getNombrelargo() + "', " +
                                               " co_com  = "  + txtComCod.getText() + ",  " +
                                               " tx_nomven='" + txtComNom.getText() + "', " +
                                               " tx_ate  = '" + txtAte.getText()    + "', " + 
                                               " tx_obs1 = '" + txaObs1.getText().replaceAll("'","")   + "', " + 
                                               " tx_obs2 = '" + txaObs2.getText().replaceAll("'","")   + "', " + 
                                               " nd_sub  = "  + objUti.redondeo(Double.parseDouble(txtSub.getText()),objZafParSis.getDecimalesBaseDatos())    + ",  " +
                                               " nd_tot  = "  + objUti.redondeo(Double.parseDouble(txtTot.getText()),objZafParSis.getDecimalesBaseDatos())    + ",  " +
                                              
                                               " nd_valiva  = "  + objUti.redondeo(Double.parseDouble(txtIva.getText()),objZafParSis.getDecimalesBaseDatos())    + ",  " +
                                               
                                               " nd_porIva = "+ dblPorIva           +  ", " +
                                               " co_forPag = "+ vecForPag.get(cboForPag.getSelectedIndex()) + ", " + 
                                               " tx_desforpag = '"+ cboForPag.getSelectedItem() +  "' , " +
                                               " fe_ultMod   =  '"+strFecSis+"', " +
                                               " co_usrMod   = "+ objZafParSis.getCodigoUsuario() + ", " +
                                               " tx_numped = " + objUti.codificar(txtFacPrv.getText()) + " " +
                                                " ,st_regrep='"+STR_ESTREG+"' "+
                                               " where " +
                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                                               " co_tipDoc = " + txtCodTipDoc.getText()       + " and "+  
                                               " co_doc = " +  txtDoc.getText();    
//                                        System.out.println(SQL);
                                        pstModCabMovInv = conMod.prepareStatement(SQL);                                     
                                        /* Ejecutando el Update */
                                        pstModCabMovInv.executeUpdate();


                                    
                                                  
                                //****************************************************************                 
                                int intTipCli=3;
                                    intTipCli= objUltDocPrint.ValidarCodigoCliente(txtPrvCod.getText(),conMod);
                                          
                          //****************************************************************                   
                                          
                                        
                                        
                                        
                                     
                                       int intControl=0;
                                        GLO_strArreItm="";
                                        String strCodReg="";
                                          for(int i=0; i<tblDat.getRowCount(); i++){  //**************
                                            if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){  //*********
                                                if(tblDat.getValueAt(i,INT_TBL_CODREG) != null ){  //*********  
                                                
                                                 if(intControl!=0)    
                                                    GLO_strArreItm=GLO_strArreItm+",";
                                      
                                                 GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
                                                 
                                                 strCodReg = tblDat.getValueAt(i,INT_TBL_CODREG).toString();
                                                   
                                         intControl++;
                                             
                                         SQL ="UPDATE tbm_detMovInv SET  " +
                                              " nd_cosUni="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())   + ", " +
                                              " nd_cosUniGrp="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+ ", " +
                                              " nd_preuni="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())   + ", " +
                                              " nd_porDes="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objZafParSis.getDecimalesMostrar())     + ", " +
                                              " nd_costot="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())     + ", " +
                                              " nd_costotGrp="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())  + ", " +
                                              " nd_tot="+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())        + " " + 
                                              " WHERE " +
                                              " co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
                                              " co_loc="+objZafParSis.getCodigoLocal()+" AND " +
                                              " co_tipDoc="+txtCodTipDoc.getText()+" AND " +
                                              " co_doc="+txtDoc.getText()+" AND  co_reg="+strCodReg;
                                             pstModCabMovInv = conMod.prepareStatement(SQL); 
                                             pstModCabMovInv.executeUpdate(); 
                                          
                                        }}  
                                      }   //******************    
                                       
                                        
                                   
                                      
                                        

       java.sql.Statement stm = conMod.createStatement();
       java.sql.Statement stmLoc = conMod.createStatement();
       java.sql.ResultSet rstLoc;
       String sql ="", strSql="";
       String strFechaPag="", strAplRet="";
       int intDiaCre=0;   
       double dlbPorRet=0;
       double dblMon=0;
       String strPorRet="";
       int intConPorRet=0;
       int intNumReg=1;
       String strCodSri="";
       for(int i=0; i<tblOrdComForPag.getRowCount(); i++){
         intDiaCre = Integer.parseInt((tblOrdComForPag.getValueAt(i, 1)==null?"0":(tblOrdComForPag.getValueAt(i, 1).toString().equals("")?"0":tblOrdComForPag.getValueAt(i, 1).toString()))); 
         dlbPorRet = objUti.redondear((tblOrdComForPag.getValueAt(i, 3)==null?"0":(tblOrdComForPag.getValueAt(i, 3).toString().equals("")?"0":tblOrdComForPag.getValueAt(i, 3).toString())), 2); 
        
         strAplRet = (tblOrdComForPag.getValueAt(i, 8)==null?"":tblOrdComForPag.getValueAt(i, 8).toString()); 

         strCodSri = (tblOrdComForPag.getValueAt(i, 7)==null?"":tblOrdComForPag.getValueAt(i, 7).toString());
         if(!strCodSri.equals(""))
             strCodSri=" and tx_codsri='"+strCodSri+"'";
         else strCodSri="";

         int FecPagDoc[] =  txtFecDoc.getFecha(tblOrdComForPag.getValueAt(i, 2).toString());
         strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];
         dblMon= objUti.redondear( (tblOrdComForPag.getValueAt(i, 4)==null?"0":(tblOrdComForPag.getValueAt(i, 4).toString().equals("")?"0":tblOrdComForPag.getValueAt(i, 4).toString())), 6 );
         

         String strBasImp="0";

        strBasImp = (tblOrdComForPag.getValueAt(i, 9)==null?"0":tblOrdComForPag.getValueAt(i, 9).toString());

         

                 
         if(dlbPorRet>0){

           if(intConPorRet==1)  strPorRet += ",";
           strPorRet +=  ""+dlbPorRet;
           intConPorRet=1;
           
           strSql="SELECT co_doc FROM tbm_pagmovinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
           " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText()+" " +
           " AND nd_porret="+dlbPorRet+" "+strCodSri+" ";
           System.out.println("--> "+strSql);
           rstLoc=stmLoc.executeQuery(strSql);
           if(rstLoc.next()){
              sql = "UPDATE tbm_pagMovInv SET fe_ven='"+strFechaPag+"', mo_pag="+dblMon+", nd_basimp="+strBasImp+" "+
              " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
              " AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText()+" AND nd_porRet="+dlbPorRet+" "+strCodSri+" ";
           }else{
                if(intNumReg==1) intNumReg += tblOrdComForPag.getRowCount();
                else  intNumReg++;
    
                sql="INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, " +
                " ne_diaCre, fe_ven, nd_porRet, mo_pag, ne_diaGra , co_tipret, tx_codsri ,tx_aplret, nd_basimp, st_regrep )"+
                " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+
                intNumDoc+", "+intNumReg+", "+((tblOrdComForPag.getValueAt(i, 1).equals(""))?"0":tblOrdComForPag.getValueAt(i, 1))+", "+
                "'"+strFechaPag+"', "+((tblOrdComForPag.getValueAt(i, 3)==null)?"0":tblOrdComForPag.getValueAt(i, 3))+", "+
                ""+objUti.redondeo(Double.parseDouble( (tblOrdComForPag.getValueAt(i, 4)==null)?"0":tblOrdComForPag.getValueAt(i, 4).toString() ),6)+", "+
                ((tblOrdComForPag.getValueAt(i, 5)==null)?"0":tblOrdComForPag.getValueAt(i, 5).toString())+","+
                ((tblOrdComForPag.getValueAt(i, 6)==null)?"0":tblOrdComForPag.getValueAt(i, 6).toString())+",'"+
                ((tblOrdComForPag.getValueAt(i, 7)==null)?"":tblOrdComForPag.getValueAt(i, 7).toString())+"','"+
                ((tblOrdComForPag.getValueAt(i, 8)==null)?"":tblOrdComForPag.getValueAt(i, 8).toString())+"',"+
                strBasImp+",'I' )";
            }
           rstLoc.close();
           rstLoc=null;

           }else{
           sql = "UPDATE tbm_pagMovInv SET fe_ven='"+strFechaPag+"', mo_pag="+dblMon+", nd_basimp="+strBasImp+" "+
           " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
           " AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText()+" AND ne_diaCre="+intDiaCre+" AND nd_porRet=0";
         }
        stm.executeUpdate(sql); 
         
      }
                                        

    strSql="DELETE FROM tbm_pagmovinv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
    " AND co_tipdoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText()+"  and nd_porret not in ( "+strPorRet+" ) and nd_porret > 0 ";
    stmLoc.executeUpdate(strSql);

    stmLoc.close();
    stmLoc=null;

                                        
//                                            String sql = " DELETE FROM tbm_pagMovInv " +
//                                               " where " +
//                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+
//                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+
//                                               " co_tipDoc = " +  txtCodTipDoc.getText()      + " and "+
//                                               " co_doc = " + txtDoc.getText();
//                                               pstModCabMovInv = conMod.prepareStatement(sql);
//                                               pstModCabMovInv.executeUpdate();
//
//
//                                        /*********************************************\
//                                         * Armando el Insert para los datos          *
//                                         * del detalle de PAgo                       *
//                                        \*********************************************/
//                                       insertDetPag(Integer.parseInt(txtDoc.getText()), conMod, STR_ESTREG);
                                        
                                        
             
                                        
                                        stm.close();
                                        stm=null;
                                        
                                        if (!objDiario.actualizarDiario(conMod, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()),txtOrdCom.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){
                                            conMod.rollback();
                                            conMod.close();
                                            return false;
                                        }
                                        
                                       /**  Insertando el detalle de Diario.*/  
                                       
                                        conMod.commit();
                                        conMod.setAutoCommit(true);  
                                        conMod.close();            
                                        GLOCODCLI=txtPrvCod.getText();
                                        objTblMod.clearDataSavedBeforeRemoveRow();
                                        objTblMod.initRowsState();                                        
                             }    
                       }catch(SQLException Evt){    
                        conMod.rollback();
                        conMod.close();
                        objUti.mostrarMsgErr_F1(jfrThis, Evt);
                        return false;
                       }
                   }catch(SQLException Evt){    
                        objUti.mostrarMsgErr_F1(jfrThis, Evt);
                        return false;
                    }
                return true;
            }
            
            
            
            ////////////////////////////////************************///**************************************
            
            
            
        @Override 
        public void clickEliminar(){
                txtCot.setEnabled(false);
                 txtSub.setEditable(false); //***
                    txtIva.setEditable(false);  //***
                    txtTot.setEditable(false);  //***
                objDiario.setEditable(false);
            }
            
            
            
            
            
public boolean eliminarReg(){
 boolean blnRes=false;
 try{ 
  java.sql.Connection conAnu=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                
  if(conAnu!=null){
   conAnu.setAutoCommit(false);                                                                                                                    

   if(anularCab(conAnu,"E")){
    if(objDiario.anularDiario(conAnu,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
      blnRes=true;
    }else conAnu.rollback();
   }else conAnu.rollback();
    
   if(blnRes==true){
    if(!isAnulada()){   
      if(anularDet(conAnu)){
        conAnu.commit(); 
        blnRes=true;
      }else{  blnRes=false; conAnu.rollback(); }
    }else  conAnu.commit();    
   }    
   //objInvItm.limpiarObjeto();     
  conAnu.close();
  conAnu=null;
 }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}    




            
            
            
        @Override 
        public void clickAnular(){
                txtCot.setEnabled(false);
                 txtSub.setEditable(false); //***
                    txtIva.setEditable(false);  //***
                    txtTot.setEditable(false);  //***
                objDiario.setEditable(false);
            }
            
            
            
            

public boolean anularReg(){
 boolean blnRes=false;
 try{ 
  java.sql.Connection conAnu =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                
  if(conAnu!=null){
   conAnu.setAutoCommit(false);                                                                                                                    

   if(anularCab(conAnu,"I")){
    if(anularDet(conAnu)){
     if(objDiario.anularDiario(conAnu,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
       conAnu.commit();
       blnRes=true;

       String strMensCorEle="ORDEN DE COMPRA : " + txtOrdCom.getText() + " Pedido #: " + txtNumPed.getText() + ""  +
       "     PROVEEDOR : "+txtPrvNom.getText();
       //objInvItm.enviarCorreo( strCorEleTo , strMensCorEle );


    }else conAnu.rollback();
   }else conAnu.rollback();
  }else conAnu.rollback();
  //objInvItm.limpiarObjeto();      
 conAnu.close();
 conAnu=null;
}}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
return blnRes;
}    


            
            
            


private boolean anularCab(java.sql.Connection conAnu, String strEstReg){
 boolean blnRes=false;
 String strSql="";
 java.sql.Statement stm;
 try{
    strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
    stm=conAnu.createStatement();     
    strSql="UPDATE tbm_cabMovInv SET st_reg  ='"+strEstReg+"' ,fe_ultMod='"+strFecSis+"',co_usrMod="+objZafParSis.getCodigoUsuario()+",st_regrep='"+STR_ESTREG+"'"+
    " WHERE  co_emp="+objZafParSis.getCodigoEmpresa()+" AND  co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText()+" AND co_doc="+txtDoc.getText();
     if (!txtCot.getText().equals(""))
      strSql += " ; Update tbm_cabcotcom set st_reg = 'A' where co_emp = " + objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() + " and co_cot = " + ((txtCot.getText().equals(""))?"0":txtCot.getText());

    stm.executeUpdate(strSql);
    blnRes=true;
}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
return blnRes;
}


 private boolean anularDet(java.sql.Connection conAnu){
 boolean blnRes=true;
 String strSql="";
 int intControl=0;
 double dlbCanMov=0;
 int intTipCli=-1;
 int intReaActStk=0;
 java.sql.Statement stmDet;
 java.sql.ResultSet rst;
 try{
     //objInvItm.inicializaObjeto();

    String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
    " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
    " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
    " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
    " THEN 'S' ELSE 'N' END AS proconf  ";
             
    strSql = "SELECT  a.co_itm, a.co_bod, sum(abs(a.nd_can)) as cantidad " +
    " "+strAux2+" FROM tbm_detmovinv as a " +
    " INNER JOIN tbm_inv as inv on (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) " +
    " WHERE a.co_Emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc="+txtCodTipDoc.getText()+" " +
    " and a.co_doc="+txtDoc.getText()+" and inv.st_ser='N' " +
    " GROUP BY a.co_itm,a.co_bod, inv.tx_codalt ";
    System.out.println(""+ strSql );
    stmDet=conAnu.createStatement();
    rst= stmDet.executeQuery(strSql);
    while(rst.next()){
       dlbCanMov=rst.getDouble("cantidad");
       int intCodItm=rst.getInt("co_itm");
       int intCodBod=rst.getInt("co_bod");
       String strEstFisBod=rst.getString("proconf");

       if(intControl!=0)    
        GLO_strArreItm=GLO_strArreItm+",";

        GLO_strArreItm=GLO_strArreItm+rst.getString("co_itm");
        intControl++;
        ZafStkInv invStock= new ZafStkInv(objZafParSis);
        //objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dlbCanMov, -1, strEstFisBod, strMerIngEgr, strTipIngEgr, -1);
        if(invStock.actualizaInventario(conAnu, objZafParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK_DIS, "-", 1, obtenerArrItmInv(conAnu, objZafParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                            if(!invStock.actualizaInventario(conAnu, objZafParSis.getCodigoEmpresa(), INT_ARL_STK_INV_STK, "-", 0, obtenerArrItmInv(conAnu, objZafParSis.getCodigoEmpresa(), intCodItm,dlbCanMov,intCodBod))){
                                  return false;
                            }
                        }else{
                            return false;
                        }
        intReaActStk=1;
    }
    rst.close();
    rst=null;       
    stmDet.close();
    stmDet=null;
    
    intTipCli= objUltDocPrint.ValidarCodigoCliente(txtPrvCod.getText(),conAnu);
    if(intReaActStk==1){
//    if(!objInvItm.ejecutaActStock(conAnu, intTipCli))
//       return false;
//
//      if(!objInvItm.ejecutaVerificacionStock(conAnu, intTipCli ))
//        return false;
    }
//   objInvItm.limpiarObjeto();    
}catch(SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
return blnRes;
}

            
            @Override
            public void clickImprimir(){
            }
            @Override
            public void clickVisPreliminar(){
            }
            @Override
            public void clickAceptar(){
               
            }
            @Override
            public void clickCancelar(){
                
            }
          
    
     
            private String FilSql() {
                
                String sqlFiltro = "";
//                if(txtCodTipDoc.getText().equals("")){
//                    cargaTipoDocPredeterminado();
//                }
                
                //Agregando filtro por Codigo Tipo de Documento
               if(!txtCodTipDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_tipdoc = " + txtCodTipDoc.getText()+ "";

                //Agregando filtro por Numero de Documento
                if(!txtDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_doc = " + txtDoc.getText()+ "";

                //Agregando filtro por Numero de Cotizacion
                if(!txtOrdCom.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.ne_numdoc = " + txtOrdCom.getText()+ "";
                
                //Agregando filtro por Numero de Pedido
                if(!txtNumPed.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.tx_numdoc2 = " + txtNumPed.getText()+ "";                

                //Agregando filtro por Numero de Orden de Compra
                if(!txtCot.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.ne_numcot = " + txtCot.getText()+ "";
                
                //Agregando filtro por Fecha
                if(txtFecDoc.isFecha()){
                    int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
                    sqlFiltro = sqlFiltro + " and CabMovInv.fe_doc = '" +  strFecSql + "'";
                }    
                //Agregando filtro por Codigo de Cliente
                if(!txtPrvCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_cli = " + txtPrvCod.getText() + "";
                    
                //Agregando filtro por Codigo de Vendedor
                if(!txtComCod.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_com = " + txtComCod.getText() + "";

                    
                    sqlFiltro = sqlFiltro   + " and menu.co_mnu = " + intCodMnu  ;
              return sqlFiltro ;
            }

            
            private boolean _consultar(String strFil){
                   strFiltro= strFil;
                   try{//odbc,usuario,password
                            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                            if (conCab!=null){

                                
                String strCodEmp="";
                    if(objZafParSis.getCodigoEmpresa()==1)
                      strCodEmp="603, 2600, 1039";
                    if(objZafParSis.getCodigoEmpresa()==2)
                      strCodEmp="2854, 2105, 789";
                    if(objZafParSis.getCodigoEmpresa()==3)
                      strCodEmp="2858, 453, 832";
                    if(objZafParSis.getCodigoEmpresa()==4)
                      strCodEmp="3117,  498,  2294";
                 
                                
                                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                sSQL= "SELECT CabMovInv.co_emp,CabMovInv.co_loc,CabMovInv.co_tipdoc, co_doc "
                                        + " FROM tbm_cabMovInv as CabMovInv "
                                        + " left outer join tbm_cabTipDoc as Doc on (cabmovinv.co_emp = doc.co_emp and cabmovinv.co_loc = doc.co_loc and cabmovinv.co_tipdoc = doc.co_tipdoc) "
                                        + " left outer join tbr_tipdocprg as menu  on (menu.co_emp = doc.co_emp and menu.co_loc = doc.co_loc and menu.co_tipdoc = doc.co_tipdoc) "
                                        + " Where CabMovInv.co_emp = " + objZafParSis.getCodigoEmpresa()  // Consultando en la empresa en la ke se esta trabajando
                                        + " and CabMovInv.st_reg not in ('E') "
                                        + " and CabMovInv.co_loc = " + objZafParSis.getCodigoLocal()+" "+
                                    
                                         // " AND  CabMovInv.co_cli IN("+strCodEmp+") "+
                                      
                                      " ";
                                
                                sSQL= sSQL + strFiltro + " ORDER BY CabMovInv.fe_doc, CabMovInv.ne_numdoc";                                
                                
                                rstCab=stmCab.executeQuery(sSQL);
                                 
                                
                                 
                                if(rstCab.next()){
                                    rstCab.last();
                                    setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                                   
                                  
                                    cargarReg();
                                }
                                else{
                                    setMenSis("0 Registros encontrados");
                                    clnTextos();
                                    return false;
                                }
                            }
                   }
                   catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
                   catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); return false; }
               return true;     
            }   

    } 
    
    
    
      public double getSubtotalTrans(){

       double dblSub = 0, dblSubCot=0, dblSub2=0;
       for (int i=0;i<tblDat.getRowCount();i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null){
              if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("T")){
                
                    dblSub2 = ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString()));  
                    dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
              
        }}}
       
     
        dblSubCot = objUti.redondear(dblSub ,intNumDec);
        return dblSubCot;
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
 
    
    
      
    /*
     *  Listener para hacer que se calcule el pago despues segun el total de cotizacion
     */   
   public void calculaPagos_anterior(){
          int intVal =  intCanArr[0];
          int intsizearre = intarreglodia.length; 
          intVal= intsizearre - (intsizearre-intVal);
          java.sql.Statement stm;       
          java.sql.ResultSet rst;
             
             try{


                    java.util.Calendar objFec = java.util.Calendar.getInstance();
                    int fecCot [] = txtFecDoc.getFecha(txtFecDoc.getText());

                    if(fecCot!=null){
                        objFec.set(java.util.Calendar.DAY_OF_MONTH, fecCot[0]);
                        objFec.set(java.util.Calendar.MONTH, fecCot[1] - 1); 
                        objFec.set(java.util.Calendar.YEAR, fecCot[2]);
                    }

                    java.util.Vector vecPag = new java.util.Vector();

                    double dblRetFue = 0.0, dblRetFueFle = 0;    
                    int intFilUltPag = 0;
                    double dblRetIva =0;
                    double dblMonCal=0; //Monto a Calcular los pagos (sin retenciones)                                        
                    double dblTotalDoc = objUti.redondeo((Double.parseDouble(txtTot.getText().equals("")?"0":txtTot.getText())),objZafParSis.getDecimalesBaseDatos());

                    while(tblOrdComForPag.getRowCount()>0)
                        ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).removeRow(0);
     
                     if(cboForPag.getSelectedIndex()!=-1){
                         
                               Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                               int diaCre=0, numPag, diaCreAcum = 0,diaCreAnt = 0;
                               double dblDifPago=0,dblSumPago=0;
                               double dblPagos=0.00;
                               double dblPago=0.00;
                               int i=0;

                                     //obteniendo la fecha actual
                                            int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                                            if(Fecha!=null){
                                                String strFecha = Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0];
                                            }

                                      
                                    java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                                     if (conn!=null){       
                                         
                                         
                                            dblRetFueFle = getSubtotalTrans();
                                           
                                         
                                             if(dblRetFueFle>0){
                                                ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());    
                                                 tblOrdComForPag.setValueAt("",tblOrdComForPag.getRowCount()-1, 1);
                                                 tblOrdComForPag.setValueAt(txtFecDoc.getText(), tblOrdComForPag.getRowCount()-1, 2);
                                                 dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
                                                 tblOrdComForPag.setValueAt("1.000", tblOrdComForPag.getRowCount()-1, 3);
                                                 tblOrdComForPag.setValueAt( new Double(dblRetFueFle) , tblOrdComForPag.getRowCount()-1, 4);
                                            }


                                      String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta, polret.tx_codsri";
                                      strSQL +=" from tbm_polret as polret " +
                                               " left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) " +
                                               " left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
                                      strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotBien+"  and co_ageret = " + intCodTipPerEmp +" and co_sujret = "+strCodTipPerCli+" " +
                                      "  AND polret.st_reg='A'  AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";

                                      stm = conn.createStatement();
                                      rst = stm.executeQuery(strSQL);
                                      while (rst.next()){

                                               ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());
                                                tblOrdComForPag.setValueAt("",tblOrdComForPag.getRowCount()-1, 1);
                                                tblOrdComForPag.setValueAt(txtFecDoc.getText(), tblOrdComForPag.getRowCount()-1, 2);

                                                 if (rst.getString("tx_aplret").equals("S")){

                                                      dblRetFue = getSubtotalSinTrans();

                                                      String fecha = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd");
                                                      java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
                                                      java.util.Date fe2 = objUti.parseDate("2007/07/01","yyyy/MM/dd");

                                                    //   System.out.println("Rete >> "+ dblRetFue );

                                       if(fe1.before(fe2)) { // maneja el porsentaje anterior antes del 1 de julio del 2007
                                                    if(!(dblRetFueFle>0))  dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * 0.01 ),2 );
                                                    else dblRetFue = objUti.redondeo( (    dblRetFue  * 0.01 ),2 );
                                                   // System.out.println("Rete >> "+ dblRetFue );
                                                    tblOrdComForPag.setValueAt("1.000", tblOrdComForPag.getRowCount()-1, 3);
                                                    tblOrdComForPag.setValueAt( new Double(dblRetFue) , tblOrdComForPag.getRowCount()-1, 4);
                                                    tblOrdComForPag.setValueAt("1" , tblOrdComForPag.getRowCount()-1, 6);
                                                    tblOrdComForPag.setValueAt(""  , tblOrdComForPag.getRowCount()-1, 7);

                                       }
                                       else {
                                                   if(!(dblRetFueFle>0))  dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
                                                   else dblRetFue = objUti.redondeo( (    dblRetFue  * ( rst.getDouble("nd_porret")/100)),2 );
                                                   tblOrdComForPag.setValueAt(rst.getString("nd_porret"), tblOrdComForPag.getRowCount()-1, 3);
                                                   tblOrdComForPag.setValueAt( new Double(dblRetFue) , tblOrdComForPag.getRowCount()-1, 4);
                                                    tblOrdComForPag.setValueAt(rst.getString("co_tipret") , tblOrdComForPag.getRowCount()-1, 6);
                                                    tblOrdComForPag.setValueAt(rst.getString("tx_codsri") , tblOrdComForPag.getRowCount()-1, 7);
                                            }

                                       }
                                                 if (rst.getString("tx_aplret").equals("I")){
                                                     dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 );
                                                     tblOrdComForPag.setValueAt(rst.getString("nd_porret") , tblOrdComForPag.getRowCount()-1, 3);
                                                     tblOrdComForPag.setValueAt( new Double(dblRetIva) , tblOrdComForPag.getRowCount()-1, 4);
                                                     tblOrdComForPag.setValueAt(rst.getString("co_tipret") , tblOrdComForPag.getRowCount()-1, 6);
                                                     tblOrdComForPag.setValueAt(rst.getString("tx_codsri") , tblOrdComForPag.getRowCount()-1, 7);
                                                 }

                                           tblOrdComForPag.setValueAt(rst.getString("tx_aplret") , tblOrdComForPag.getRowCount()-1, 8);

                                      }
                                      rst.close();
                                      rst=null;

                                      stm.close();
                                      stm=null;
                                     
                                      conn.close();
                                      conn=null;
                                     }
                                    
                                    
                                     double dblRete = 0;
                                     dblRete = dblRetFueFle+dblRetFue+dblRetIva;
                                     
                                     //dblMonCal = objUti.redondear( ( dblTotalDoc - ( dblRetFue + dblRetIva)),intNumDec);
                                     dblMonCal = objUti.redondear( ( dblTotalDoc - dblRete ),intNumDec);
                                    
                                    
                                     for(i=0; i < intVal; i++) {
                                            diaCreAnt = diaCre;
                                            diaCre = intarreglodia[i]; 
                                            numPag = intarreglonum[i];
                                            diaCreAcum = diaCre - diaCreAnt;
                                            if (diaCre!=0)
                                                objFec.add(java.util.Calendar.DATE, diaCreAcum);                                                  
                                           
                                            
                                            dtePckPag.setAnio( objFec.get(java.util.Calendar.YEAR));
                                            dtePckPag.setMes( objFec.get(java.util.Calendar.MONTH)+1);
                                            dtePckPag.setDia(objFec.get(java.util.Calendar.DAY_OF_MONTH));
                                            double dblValPag =0;
                                            
                                               dblPagos = objUti.redondear( (numPag==0)?0:(dblMonCal/numPag) ,intNumDec);
                                               dblPago += dblPagos; 
                                               dblPagos = objUti.redondear(dblPagos ,intNumDec);
                                               dblValPag = dblPagos;
                                               
                                               ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());    
                                                intFilUltPag = tblOrdComForPag.getRowCount()-1;
                                                tblOrdComForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
                                                tblOrdComForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
                                                tblOrdComForPag.setValueAt(new Double(dblValPag), intFilUltPag, 4);
                                                tblOrdComForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
                                    }
                                  
                                     double dblultpag =  objUti.redondear(Double.parseDouble(tblOrdComForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
                                    // dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRetFue + dblRetIva ) ),intNumDec);
                                       dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRete ) ),intNumDec);
                                    
                                     
                                       
                                     tblOrdComForPag.setValueAt( new Double(dblultpag) , intFilUltPag, 4);
                                   }
                                 }
                                catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
                                catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
             
             }                             
       
      
    
    
    
        /*
     *  Listener para hacer que se calcule el pago despues segun el total de cotizacion
     */
 double dblRetFueGlo = 0.0, dblRetIvaGlo = 0;
 public void calculaPagos()
 {
          int intVal =  intCanArr[0];
          int intsizearre = intarreglodia.length;
          intVal= intsizearre - (intsizearre-intVal);
          java.sql.Statement stm;
          java.sql.ResultSet rst;
         // double dblRetFueFle=0.00;
             try{

                    dblRetFueGlo = 0.0;
                    dblRetIvaGlo = 0.00;
                    java.util.Calendar objFec = java.util.Calendar.getInstance();
                    int fecCot [] = txtFecDoc.getFecha(txtFecDoc.getText());

                    if(fecCot!=null){
                        objFec.set(java.util.Calendar.DAY_OF_MONTH, fecCot[0]);
                        objFec.set(java.util.Calendar.MONTH, fecCot[1] - 1);
                        objFec.set(java.util.Calendar.YEAR, fecCot[2]);
                    }

                   // java.util.Vector vecPag = new java.util.Vector();

                    
                    int intFilUltPag = 0;
                    double dblMonCal=0; //Monto a Calcular los pagos (sin retenciones)
                    double dblTotalDoc = objUti.redondeo((Double.parseDouble(txtTot.getText().equals("")?"0":txtTot.getText())),objZafParSis.getDecimalesBaseDatos());

                    while(tblOrdComForPag.getRowCount()>0)
                        ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).removeRow(0);

                     if(cboForPag.getSelectedIndex()!=-1){

                               Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                               int diaCre=0, numPag, diaCreAcum = 0,diaCreAnt = 0;
                              // double dblDifPago=0,dblSumPago=0;
                               double dblPagos=0.00;
                               double dblPago=0.00;
                               int i=0;

                                     //obteniendo la fecha actual
//                                            int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
//                                            if(Fecha!=null){
//                                                String strFecha = Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0];
//                                            }


            java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
             if (conn!=null){


/******************************************************************************************************************************/

                    calculaSubtotalServiNoServi("T");
                    System.out.println("No Servicio Sub:  "+dblSubSerNoSer );
                    System.out.println("No Servicio Iva:  "+dblIvaSerNoSer );

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotTran );
                   }

/******************************************************************************************************************************/

                    calculaSubtotalServiNoServi("N");
                    System.out.println("No Servicio Sub:  "+dblSubSerNoSer );
                    System.out.println("No Servicio Iva:  "+dblIvaSerNoSer );

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotBien );
                   }

/******************************************************************************************************************************/
                   calculaSubtotalServiNoServi("S");
                   System.out.println("Servicio Sub:  "+dblSubSerNoSer );
                   System.out.println("Servicio Iva:  "+dblIvaSerNoSer );

                   if(dblSubSerNoSer > 0.00 ){
                       cargaForPag(conn, intCodMotServ );
                   }
/******************************************************************************************************************************/



                                      conn.close();
                                      conn=null;
                                     }


                                     double dblRete = 0;
                                     dblRete = dblRetFueGlo+dblRetIvaGlo;

                                     dblMonCal = objUti.redondear( ( dblTotalDoc - dblRete ),intNumDec);


                                     for(i=0; i < intVal; i++) {
                                            diaCreAnt = diaCre;
                                            diaCre = intarreglodia[i];
                                            numPag = intarreglonum[i];
                                            diaCreAcum = diaCre - diaCreAnt;
                                            if (diaCre!=0)
                                                objFec.add(java.util.Calendar.DATE, diaCreAcum);


                                            dtePckPag.setAnio( objFec.get(java.util.Calendar.YEAR));
                                            dtePckPag.setMes( objFec.get(java.util.Calendar.MONTH)+1);
                                            dtePckPag.setDia(objFec.get(java.util.Calendar.DAY_OF_MONTH));
                                            double dblValPag =0;

                                               dblPagos = objUti.redondear( (numPag==0)?0:(dblMonCal/numPag) ,intNumDec);
                                               dblPago += dblPagos;
                                               dblPagos = objUti.redondear(dblPagos ,intNumDec);
                                               dblValPag = dblPagos;

                                               ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());
                                                intFilUltPag = tblOrdComForPag.getRowCount()-1;
                                                tblOrdComForPag.setValueAt(new Integer(diaCre) , intFilUltPag, 1);
                                                tblOrdComForPag.setValueAt(dtePckPag.getText(), intFilUltPag, 2);
                                                tblOrdComForPag.setValueAt(new Double(dblValPag), intFilUltPag, 4);
                                                tblOrdComForPag.setValueAt(new Integer(0),  intFilUltPag, 5);
                                    }

                                     double dblultpag =  objUti.redondear(Double.parseDouble(tblOrdComForPag.getValueAt( intFilUltPag, 4).toString()),intNumDec);
                                    // dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRetFue + dblRetIva ) ),intNumDec);
                                       dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRete ) ),intNumDec);



                                     tblOrdComForPag.setValueAt( new Double(dblultpag) , intFilUltPag, 4);
                                   }
                                 }
                                catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
                                catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }

             }



             
   //*******************************************************************************************************
   
     
private void cargaForPag(java.sql.Connection conn, int intCodMot ){
   java.sql.Statement stm;
   java.sql.ResultSet rst;
   int intCodEmp=0;
   String strSQL="";
   double dblRetFue=0, dblRetIva=0;
   double dblBasImp=0;
   try{
     if(conn!=null){
     if(blnEstCarOC) 
     {
        intCodEmp= Integer.parseInt(strCodEmpOC); 
     }
     else
     {
        intCodEmp=objZafParSis.getCodigoEmpresa();
     }
      
      stm=conn.createStatement();

      strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta, polret.tx_codsri "+
      " from tbm_polret as polret " +
      " left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) " +
      " left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret) "+
      " where polret.co_emp = "+ intCodEmp +" and co_mot = "+intCodMot+"  and co_ageret = " + intCodTipPerEmp +" and co_sujret = "+strCodTipPerCli+" " +
      "  AND polret.st_reg='A'  AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
      System.out.println("cargaForPag: "+strSQL);
      rst = stm.executeQuery(strSQL);
      while (rst.next()){

          
             if(rst.getString("tx_aplret").equals("S")){

               ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());
               tblOrdComForPag.setValueAt("",tblOrdComForPag.getRowCount()-1, 1);
               tblOrdComForPag.setValueAt(txtFecDoc.getText(), tblOrdComForPag.getRowCount()-1, 2);


                  dblRetFue = dblSubSerNoSer;  

                  String fecha = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd");
                  java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
                  java.util.Date fe2 = objUti.parseDate("2007/07/01","yyyy/MM/dd");

                  dblBasImp=dblSubSerNoSer;

                   if(fe1.before(fe2)) { // maneja el porsentaje anterior antes del 1 de julio del 2007
                        dblRetFue = objUti.redondeo( (   dblSubSerNoSer  * 0.01 ),2 );
                        dblRetFueGlo+=dblRetFue;
                        tblOrdComForPag.setValueAt("1.000", tblOrdComForPag.getRowCount()-1, 3);
                        tblOrdComForPag.setValueAt( new Double(dblRetFue) , tblOrdComForPag.getRowCount()-1, 4);
                        tblOrdComForPag.setValueAt("1" , tblOrdComForPag.getRowCount()-1, 6);
                        tblOrdComForPag.setValueAt(""  , tblOrdComForPag.getRowCount()-1, 7);

                   }else {
                       dblRetFue = objUti.redondeo( (   dblSubSerNoSer  * ( rst.getDouble("nd_porret")/100)),2 );
                       dblRetFueGlo+=dblRetFue;
                       tblOrdComForPag.setValueAt(rst.getString("nd_porret"), tblOrdComForPag.getRowCount()-1, 3);
                       tblOrdComForPag.setValueAt( new Double(dblRetFue) , tblOrdComForPag.getRowCount()-1, 4);
                       tblOrdComForPag.setValueAt(rst.getString("co_tipret") , tblOrdComForPag.getRowCount()-1, 6);
                       tblOrdComForPag.setValueAt(rst.getString("tx_codsri") , tblOrdComForPag.getRowCount()-1, 7);
                     }

                 tblOrdComForPag.setValueAt(rst.getString("tx_aplret") , tblOrdComForPag.getRowCount()-1, 8);
                 tblOrdComForPag.setValueAt(  new Double(objUti.redondear(dblBasImp,2))  , tblOrdComForPag.getRowCount()-1, 9);

                }

           
               if (rst.getString("tx_aplret").equals("I")){
                 if(dblIvaSerNoSer > 0 ){


               ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());
               tblOrdComForPag.setValueAt("",tblOrdComForPag.getRowCount()-1, 1);
               tblOrdComForPag.setValueAt(txtFecDoc.getText(), tblOrdComForPag.getRowCount()-1, 2);


                     dblBasImp=dblIvaSerNoSer;
                     dblRetIva = objUti.redondeo(  (  dblIvaSerNoSer  * ( rst.getDouble("nd_porret")/100)),2 );
                     dblRetIvaGlo+=dblRetIva;
                     tblOrdComForPag.setValueAt(rst.getString("nd_porret") , tblOrdComForPag.getRowCount()-1, 3);
                     tblOrdComForPag.setValueAt( new Double(dblRetIva) , tblOrdComForPag.getRowCount()-1, 4);
                     tblOrdComForPag.setValueAt(rst.getString("co_tipret") , tblOrdComForPag.getRowCount()-1, 6);
                     tblOrdComForPag.setValueAt(rst.getString("tx_codsri") , tblOrdComForPag.getRowCount()-1, 7);

               tblOrdComForPag.setValueAt(rst.getString("tx_aplret") , tblOrdComForPag.getRowCount()-1, 8);
               tblOrdComForPag.setValueAt(  new Double(objUti.redondear(dblBasImp,2))  , tblOrdComForPag.getRowCount()-1, 9);

               }
              }

              
        }
        rst.close();
        rst=null;

   }}catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
     catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}

   
   
    public class ActLisForPag implements java.awt.event.ActionListener{
        @Override
         public void actionPerformed(java.awt.event.ActionEvent e) {
              if(cboForPag.getSelectedIndex()>-1 ){
                if (cboForPag.getSelectedItem().toString().equals("< Refrescar >")){                                   
                    cargaListadoFormaPago();
                }
              }
              
              
               try{
                    java.sql.Connection conPag = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                    if (conPag!=null){     

                         if(cboForPag.getSelectedIndex()!=-1){

                         

                             String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre " +
                                          " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                          "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) + " Order by A2.co_reg";
                                            
                            String sSQL3 = "SELECT count(A2.ne_diaCre) as c  " +
                                          " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                          "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex());
                               
 
                           // System.out.println(">> "+ sSQL2 );
                            
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
                                          x++;  
                                    }
                           }
                         conPag.close();
                         conPag = null ;
                    }
                }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
              
             calculaPagos(); 
             
         }
    }    
    private void cargaListadoFormaPago(){
        objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                             objZafParSis.getClaveBaseDatos(), "SELECT co_forpag, tx_des FROM tbm_cabForPag Where co_emp = " + objZafParSis.getCodigoEmpresa() + " order by co_forpag", cboForPag, vecForPag);      
        cboForPag.addItem("< Refrescar >");
        cboForPag.setSelectedIndex(0);
    }
    

    
    
           public boolean consultar2(int CodEmp,int CodLoc) {
                return _consultar2(FilSql2(),CodEmp,CodLoc);
            }
     
    
                   
           private String FilSql2() {
                
                String sqlFiltro = "";
    
                //Agregando filtro por Codigo Tipo de Documento
               if(!txtCodTipDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_tipdoc = " + txtCodTipDoc.getText()+ "";

                //Agregando filtro por Numero de Documento
                if(!txtDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_doc = " + txtDoc.getText()+ "";

               
                    
                    sqlFiltro = sqlFiltro   + " and menu.co_mnu = " + intCodMnu  ;
              return sqlFiltro ;
            }
            
           
              private boolean _consultar2(String strFil, int CodEmp,int CodLoc){
                   strFiltro= strFil;
                   try{//odbc,usuario,password
                            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                            if (conCab!=null){

                                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                sSQL= "SELECT CabMovInv.co_emp,CabMovInv.co_loc,CabMovInv.co_tipdoc, co_doc " +                                      
                                      " FROM tbm_cabMovInv as CabMovInv left outer join tbm_cabTipDoc as Doc on (cabmovinv.co_emp = doc.co_emp and cabmovinv.co_loc = doc.co_loc and cabmovinv.co_tipdoc = doc.co_tipdoc) left outer join tbr_tipdocprg as menu  on (menu.co_emp = doc.co_emp and menu.co_loc = doc.co_loc and menu.co_tipdoc = doc.co_tipdoc) " +
                                      " Where CabMovInv.co_emp = " + CodEmp + // Consultando en la empresa en la ke se esta trabajando
                                      "  and CabMovInv.st_reg not in ('E')     and CabMovInv.co_loc = " + CodLoc;                                                                      
                                sSQL= sSQL + strFiltro + " ORDER BY CabMovInv.co_doc";
                                
                             
                                rstCab=stmCab.executeQuery(sSQL);
                                 
                                if(rstCab.next()){
                                    rstCab.last();
                                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                                    cargarReg();
                                }
                                else{
                                    objTooBar.setMenSis("0 Registros encontrados");
                                    clnTextos();
                                    return false;
                                }
                            }
                   }
                   catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  return false;  }
                   catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); return false; }
               return true;     
            }   
           
    
      private boolean validaCampos(){
               if(txtCodTipDoc.getText().equals("") ){
                   tabOrdCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Codigo de Tipo de Documento");
                   txtCodTipDoc.requestFocus();
                   return false;
               }
               
               if(txtOrdCom.getText().equals("") ){
                   tabOrdCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("No. Orden de Compra");
                   txtOrdCom.requestFocus();
                   return false;
               }
               
               if(objTooBar.getEstado()=='n'){
                if(txtNumPed.getText().equals("") ){
                    tabOrdCom.setSelectedIndex(0);                  
                    MensajeValidaCampo("No. de Pedido");
                    txtNumPed.requestFocus();
                    return false;
                }
               }


               //fin valida txtOrdCom
               
               if(txtPrvCod.getText().equals("") ){
                   tabOrdCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Proveedor");
                   txtPrvCod.requestFocus();
                   return false;
               }
               if(!txtFecDoc.isFecha()){
                   tabOrdCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Fecha de Compra");
                   txtFecDoc.requestFocus();
                   return false;
               }
               if( txtComCod.getText().equals("") ){
                   tabOrdCom.setSelectedIndex(0);                  
                   MensajeValidaCampo("Vendedor");
                   txtComCod.requestFocus();
                   return false;
               }
               // objTblMod.removeEmptyRows();
                if (!objTblMod.isAllRowsComplete()){
                    MensajeInf("Existen datos incompletos. \nRevise filas sombreadas");
                    return false;                                    
                }
               /* 
                * VAlidando los datos del Pago
                */
               if(cboForPag.getSelectedIndex() ==-1 ){
                   tabOrdCom.setSelectedIndex(1);
                   MensajeValidaCampo("Forma de pago");
                   cboForPag.requestFocus();
                   return false;
               }
               
               int intColObligadasPag[] = {2};

               
               
               //*********************************************************************************************
               
                   for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                  
                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                                        
                     double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                    
                    if(dblCan <= 0.00 ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                        return false; 
                    }
                    
                     
                    
                     double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PRECOS).toString());
                    if(dblCos <= 0.00 ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.editCellAt(intRowVal, INT_TBL_PRECOS);
                        return false; 
                    }
                    
                      
                   }
                 }
               //*****************************************************************************************************
               
                if (!objDiario.isDiarioCuadrado()){
                    MensajeInf("Asiento descuadrado.");
                    tabOrdCom.setSelectedIndex(2);
                    return false;                                    
                }
                
               /*
                * VAlidando que la suma de los montos de pagos sean igual a total de cotizacion
                */
               
               double dblTotalMonto=0;
               
               double dblTotalDoc = objUti.redondeo((Double.parseDouble(txtTot.getText())),6);
                                             
               for(int rowIdx = 0; rowIdx<tblOrdComForPag.getRowCount() ;rowIdx++){
                    double dblSubMonto = Double.parseDouble((tblOrdComForPag.getValueAt(rowIdx, 4)==null)?"0":tblOrdComForPag.getValueAt(rowIdx, 4).toString());
                    dblTotalMonto = dblTotalMonto + dblSubMonto ; 
                }
               
                
                if( objUti.redondear(dblTotalMonto, intNumDec)!= objUti.redondear(dblTotalDoc, intNumDec) && objTooBar.getEstado()=='n'){
                    tabOrdCom.setSelectedIndex(1);
                    MensajeInf("La suma de los montos a pagar es mayor al total de la Cotización.\nCorrija y vuelva a intentarlo.");
                    return false;
                }
               if (!objDiario.isDocumentoCuadrado(dblTotalDoc)){
                    MensajeInf("Asiento y Monto Total Descuadrado.");
                    tabOrdCom.setSelectedIndex(2);
                    return false;                                                       
               }
               
             
              //*******************************************************************
                
                 
                    
              //********************************************************************
               
                 if( txtCod.getText().equals("") ){
                    MensajeValidaCampo("Forma de Retiro");
                    txtCod.requestFocus();
                    tabOrdCom.setSelectedIndex(3);
                    return false;
              }
              
               
               return true; 
          }
        
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCom;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboForPag;
    private javax.swing.JCheckBox chkEmiChq;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblCotNumDes;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblNumCot;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblOrdCom;
    private javax.swing.JLabel lblPro;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTipDoc1;
    private javax.swing.JLabel lblTot;
    private javax.swing.JPanel panCotForPag;
    private javax.swing.JPanel panCotForPagNo;
    private javax.swing.JPanel panCotForPagNorCen;
    private javax.swing.JPanel panCotGen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panCotNor;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabOrdCom;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblOrdComForPag;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtComCod;
    private javax.swing.JTextField txtComNom;
    private javax.swing.JTextField txtCot;
    private javax.swing.JTextField txtDetTipDoc;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtFacPrv;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomTipDoc;
    private javax.swing.JTextField txtNumPed;
    private javax.swing.JTextField txtOrdCom;
    private javax.swing.JTextField txtPrvCod;
    private javax.swing.JTextField txtPrvDir;
    private javax.swing.JTextField txtPrvNom;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
   
  
//    
//    public String retRucCli(){
//        java.sql.Connection conTipDoc;
//        java.sql.Statement stmTipDoc;
//        java.sql.ResultSet rstTipDoc;
//        String que, auxTipDoc="";
//        try{
//            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
//            if(conTipDoc!=null){
//                stmTipDoc=conTipDoc.createStatement();
//                que="";
//                que+="select tx_ide from tbm_cli";
//                que+=" where co_emp=" + objZafParSis.getCodigoEmpresa()+ "";
//                que+=" and co_cli=" + txtPrvCod.getText() + "";
//               // System.out.println("el query del nombre de la empresa es:"+que);
//                rstTipDoc=stmTipDoc.executeQuery(que);
//                if (rstTipDoc.next()){
//                    auxTipDoc=rstTipDoc.getString("tx_ide");
//                }
//            }
//            conTipDoc.close();
//            conTipDoc=null;
//        }
//        catch (java.sql.SQLException e)
//        {
//           // objutil.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            //objutil.mostrarMsgErr_F1(this, e);
//        }
//        return auxTipDoc;
//
//    }

  
    
//   public String retTelCli(){
//        java.sql.Connection conTipDoc;
//        java.sql.Statement stmTipDoc;
//        java.sql.ResultSet rstTipDoc;
//        String que, auxTipDoc="";
//        
//        
//        try{
//            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
//            if(conTipDoc!=null){
//                stmTipDoc=conTipDoc.createStatement();
//                que="";
//                que+="select tx_tel from tbm_cli";
//                que+=" where co_emp=" + objZafParSis.getCodigoEmpresa()+ "";
//                que+=" and co_cli=" + txtPrvCod.getText() + "";
//              // System.out.println("el query del nombre de la empresa es:"+que);
//                rstTipDoc=stmTipDoc.executeQuery(que);
//                if (rstTipDoc.next()){
//                    auxTipDoc=rstTipDoc.getString("tx_tel");
//                }
//            }
//            conTipDoc.close();
//            conTipDoc=null;
//        }
//        catch (java.sql.SQLException e)
//        {
//           // objutil.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//           // objutil.mostrarMsgErr_F1(this, e);
//        }
//        return auxTipDoc;
//
//    }


    
             
public String retNomEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_nom from tbm_emp";
                que+=" where co_emp=" + codEmp + "";
             //   System.out.println("el query del nombre de la empresa es:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
           // objutil.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
           // objutil.mostrarMsgErr_F1(this, e);
        }
        return auxTipDoc;

    }

    
    
 private void listaProductos(String strDesBusqueda){
           objVenCon2.setTitle("Listado de Item"); 
            int intNumFil=tblDat.getSelectedRow();
           
           objVenCon2.show();
           if (objVenCon2.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
           {
              tblDat.setValueAt(objVenCon2.getValueAt(1),intNumFil,INT_TBL_ITMALT);
              tblDat.setValueAt(objVenCon2.getValueAt(2),intNumFil,INT_TBL_CODITM);
              tblDat.setValueAt(objVenCon2.getValueAt(3),intNumFil,INT_TBL_DESITM);
              tblDat.setValueAt(objVenCon2.getValueAt(7),intNumFil,INT_TBL_UNIDAD);
              tblDat.setValueAt(objVenCon2.getValueAt(5),intNumFil,INT_TBL_PRECOS);
              tblDat.setValueAt(objVenCon2.getValueAt(6),intNumFil,INT_TBL_IVATXT); 
              tblDat.setValueAt(objVenCon2.getValueAt(8),intNumFil,INT_TBL_ITMALT2);
              tblDat.setValueAt(objVenCon2.getValueAt(9),intNumFil,INT_TBL_ITMSER);
            
              
              tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), intNumFil, INT_TBL_CODBOD);
              
               if(tblDat.getValueAt(intNumFil,INT_TBL_CANMOV)==null)
                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_CANMOV); 
               if(tblDat.getValueAt(intNumFil,INT_TBL_PORDES)==null)
                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_PORDES); 
               if(tblDat.getValueAt(intNumFil,INT_TBL_TOTAL)==null)
                  tblDat.setValueAt(new Double("0.00"),intNumFil,INT_TBL_TOTAL); 
              
                    blnChangeData = false;                            
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                    else 
			strBeforeValue = "";
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);
                    }

              
                    if (tblDat.getValueAt(intNumFil,INT_TBL_ITMALT)!=null)
                        strAfterValue = tblDat.getValueAt(intNumFil,INT_TBL_ITMALT).toString();
                    else
                        strAfterValue ="";
                    if ((tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("M") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E")) && tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null){
                            tblDat.setValueAt("D",intNumFil , INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("V")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)!=null)
                            tblDat.setValueAt("N", intNumFil, INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("N")){
                        if (tblDat.getValueAt(intNumFil, INT_TBL_ITMALT)==null)
                            tblDat.setValueAt("V", intNumFil, INT_TBL_ESTADO);                            
                    }else if (tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("E") || tblDat.getValueAt(intNumFil,INT_TBL_ESTADO).toString().equals("D")){
                        tblDat.setValueAt("M", intNumFil, INT_TBL_ESTADO);                            
                    }
                    
                    if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT)!=null){
                        if (tblDat.getValueAt(intNumFil,INT_TBL_IVATXT).equals("S"))
                            tblDat.setValueAt(true, intNumFil, INT_TBL_BLNIVA);
                        else
                            tblDat.setValueAt(false, intNumFil, INT_TBL_BLNIVA);
                    }
              
                   calculaSubtotal();
           }
    }
    
    
     private class ButFndItm extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButFndItm(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
         @Override
        public void butCLick() {
             listaProductos("");
             tblDat.requestFocus();
             tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_CANMOV, false, false);
        }
    }



    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {                   
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_ITMALT:
                    strMsg="Codigo Item";
                    break;
                case INT_TBL_DESITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_CODBOD:
                    strMsg="Codigo Bodega";
                    break;
                case INT_TBL_CANMOV:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_PRECOS:
                    strMsg="Costo";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    } 

    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        @Override
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
              ///      tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calculaTotal();
                    calculaPagos();
                    generaAsiento();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
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
        String strRutRpt, strNomRpt, strFecHorSer;
        String strRutImgLogo = " ";
        int i, intNumTotRpt;
        boolean blnRes=true;

        try
        {
            Map mapPar=new HashMap();
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
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 19:
                            case 28: // Ordenes de Compra (Cosenco)
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                
                                //Inicializar los parametros que se van a pasar al reporte.                                
                                mapPar.put("codEmp", new Integer( objZafParSis.getCodigoEmpresa() ) );
                                mapPar.put("codLoc", new Integer( objZafParSis.getCodigoLocal() ) );
                                mapPar.put("CodTipDoc", new Integer( txtCodTipDoc.getText() ) );
                                mapPar.put("codDoc", new Integer( txtDoc.getText() ) );
                                    
                                //mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;

                            default: // Rose: Grupo LSC 
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                strRutImgLogo=objRptSis.getRutaReporte(i);
                                if(objZafParSis.getCodigoEmpresa()==1) {
                                    strRutImgLogo+="Logos/logTuv.png";
                                }
                                else if(objZafParSis.getCodigoEmpresa()==2){
                                    strRutImgLogo+="Logos/logCas.png";
                                }
                                else{
                                    strRutImgLogo+="Logos/logDim.png";
                                }
                                
                                mapPar.put("codEmp", new Integer( objZafParSis.getCodigoEmpresa() ) );
                                mapPar.put("codLoc", new Integer( objZafParSis.getCodigoLocal() ) );
                                mapPar.put("CodTipDoc", new Integer( txtCodTipDoc.getText() ) );
                                mapPar.put("codDoc", new Integer( txtDoc.getText() ) );
                                mapPar.put("RUTA_LOGO", strRutImgLogo );
                                
                                //mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
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
       super.finalize();
       System.out.println ("Se libera Objeto...");
   }

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
            this.setFont(new java.awt.Font("Tahoma", 0, 11));
            this.setOpaque(true);
            setBackground(new Color(201,223,245));
            setForeground(new Color(0, 0, 0));
            
            return this;
        }
    }
   private ArrayList obtenerArrItmInv(Connection cnx, int intCodEmp,int intCodItm, double dblCan, int intCodBod){
     String strSql="";
     Statement stmSql=null;
     ResultSet rsItmInv=null;
     ArrayList lstItmInv=new ArrayList();
     ArrayList lstItm=new ArrayList();
     try{
         stmSql=cnx.createStatement();
         strSql=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, \n" +
                "	CASE WHEN a1.nd_cosUni IS NULL THEN \n" +
                "		0 \n" +
                "	ELSE \n" +
                "		a1.nd_cosUni \n" +
                "	END as nd_cosUni, \n" +
                "        a1.nd_preVta1, a1.st_ivaVen, \n" +
                "        CASE WHEN a1.tx_codAlt2 IS NULL THEN \n" +
                "		'' \n" +
                "	ELSE \n" +
                "		a1.tx_codAlt2 \n" +
                "	END as tx_codAlt2, \n" +
                "	a2.co_itmMae, \n" +
                "        CASE WHEN a1.co_uni IS NULL THEN \n" +
                "		0 \n" +
                "	ELSE \n" +
                "		a1.co_uni \n" +
                "	END as co_uni, \n" +
                "        CASE WHEN a1.nd_pesItmKgr IS NULL THEN \n" +
                "		0 \n" +
                "	ELSE \n" +
                "		a1.nd_pesItmKgr \n" +
                "	END as nd_pesItmKgr , \n" +
                "	GRU.co_itm as co_itmGru,a1.st_ivaCom, a1.st_ivaVen, a3.tx_desCor\n" +
                " FROM tbm_inv as a1 \n" +
                " INNER JOIN tbm_equInv as a2 \n" +
                " ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n" +
                " INNER JOIN tbm_equInv as GRU \n" +
                " ON (a2.co_ItmMae=GRU.co_itmMae AND GRU.co_emp=0) \n" +
                " LEFT OUTER JOIN tbm_var as a3 \n" +
                " ON (a1.co_uni=a3.co_reg) \n" +
                " WHERE a1.co_emp="+intCodEmp+" and a1.st_reg='A' AND a1.co_itm="+intCodItm;
         rsItmInv=stmSql.executeQuery(strSql);
         while (rsItmInv.next()){
            lstItmInv.add(rsItmInv.getString("co_itmGru"));
            lstItmInv.add(rsItmInv.getString("co_itm"));
            lstItmInv.add(rsItmInv.getString("co_itmMae"));
            lstItmInv.add(rsItmInv.getString("tx_codAlt"));
            lstItmInv.add((Double.valueOf(dblCan<0?(dblCan*-1):dblCan)).toString());
            lstItmInv.add(Integer.valueOf(intCodBod).toString());
            lstItm.add(lstItmInv);
         }         
     }catch(Exception ex){
         ex.printStackTrace();     
     }
     return lstItm;
 }
   /**
 * Función que validad si es nulo asignas "0" caso contrario devuelve el valor que tiene.
 * @param objTbl Objeto de la celda 
 * @return "0" si el nulo ó  vacion "" el valor que tiene
 */
public String getIntDatoValidado( Object objTbl ){
 String strVar="0";
 try{
    if(objTbl==null) strVar="0";
    else if(objTbl.toString().equals("")) strVar="0";
    else strVar=objTbl.toString();
  }catch(Exception e){ e.printStackTrace(); }
 return strVar;   
}
}
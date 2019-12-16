/*
 *    zafVen01_OC.java  co_ben,    tbr_det
 *
 *    Created on 6 de septiembre de 2004, 15:54        
 */          
package Ventas.ZafVen01;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafUtil.ZafCtaCtb_dat;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
  
/*
 * @author  jayapata  
 */
public class ZafVen01_OC extends javax.swing.JDialog { // extends javax.swing.JInternalFrame{
    Connection CONN_GLO=null;      //Coneccion a la base donde se encuentra la cotizacion
    ResultSet rstCab;//Resultset que tendra los datos de la cabecera
    
    private ZafParSis objZafParSis;
    Librerias.ZafUtil.ZafUtil               objUti;
    Librerias.ZafAsiDia.ZafAsiDia           objDiario;  
    Librerias.ZafUtil.ZafTipDoc             objTipDoc;
    Librerias.ZafUtil.UltDocPrint           objUltDocPrint;  // Para trabajar con la informacion de tipo de documento    
    Librerias.ZafUtil.ZafCtaCtb             objCtaCtb;  // Para obtener  los codigos y nombres de ctas ctbles
    Librerias.ZafObtConCen.ZafObtConCen  objObtConCen;
    Librerias.ZafDate.ZafDatePicker txtFecDoc;
    Librerias.ZafInventario.ZafInvItm objInvItm;
    
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    
    String strFecSis,strFecSisBase;
    String strBeforeValue,strAfterValue,strMsgError = "";
    String strTipEmp,strAux;
    String sSQL, strFiltro;//EL filtro de la Consulta actual
    String strCodTipPerCli;
    String GLOCODCLI;
    String stIvaCom="S";
    String GLO_strArreItm="";
    String STR_ESTREG=""; 
    String DIRECCION_REPORTE="C://Zafiro//Reportes_impresos//RptOrdCom.jrxml";
    String strTipPer_glo = ""; 
    String VERSION = " v. 5.16";//se arregla el problema de precisión al momento de guardar la O/C
    // Terminales L   
    
    int intCodTipPerEmp;
    int  INTCODREGCEN=0;
    int intTipDocFac=0;
    int intCodDocFac=0;
    int intCodEmp =0;
    int intCodLoc =0;
    int intCodCot=0;
    int intCodPrv = 0;
    int Glo_intCodSec=0;
    int intCodMnu; //obtiene el codigo del menu
    int intNumDec, intNumDoc= 0,  op=1 ; //Numero de decimales a presentar
    
    double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    double dblTotalCom, dblIvaCom,dblSubtotalCom;  
    
    double dblMaxDesCom=0;

    boolean blnAcepta = false;
    boolean blnEst=false;
    boolean blnChangeData=false;
    boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
     
    ZafVenCon objVenConPrv; //*****************  
    ZafVenCon objVenConVen; //*****************  
    LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    
    //Constantes de la tabla
    final int INT_TBL_LINEA    = 0 ; 
    final int INT_TBL_ITMALT   = 1 ;
    final int INT_TBL_BUTITM   = 2 ;
    final int INT_TBL_DESITM   = 3 ;
    final int INT_TBL_UNIDAD   = 4 ;
    final int INT_TBL_CODBOD   = 5 ;
    final int INT_TBL_CANORI   = 6 ;
    final int INT_TBL_CANMOV   = 7 ;            //Cantidad del movimiento (venta o compra)
    final int INT_TBL_PRECOS   = 8 ;           //Precio de Venta
    final int INT_TBL_PORDES   = 9 ;           //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 10;           //Boolean Iva
    final int INT_TBL_TOTAL    = 11;           //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 12;
    final int INT_TBL_ITMORI   = 13;    //Codigo del item de una o/c cargada
    final int INT_TBL_BODORI   = 14;    //Codigo del bodega de una o/c cargada
    final int INT_TBL_ESTADO   = 15;
    final int INT_TBL_IVATXT   = 16;
    final int INT_TBL_ITMALT2   = 17 ;
    final int INT_TBL_PRECOSORI = 18;
    final int INT_TBL_ITMSER   = 19;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_NUMREL   = 20;
    final int INT_TBL_IEBODFIS   = 21;  // estado que dice si ingresa/egresa fisicamente en bodega
    final int INT_TBL_PORDESORI   = 22 ;
    
    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODITM   = 0;
    final int INT_ARR_CODBOD   = 1;
    final int INT_ARR_CANMOV   = 2;
    final int INT_ARR_ITMORI   = 3;
    final int INT_ARR_BODORI   = 4;
    final int INT_ARR_CANORI   = 5;
    
    final int INT_DET_COD_EMP=0;                //Código de la empresa.
    final int INT_DET_COD_LOC=1;                //Código del local.
    final int INT_DET_COD_TIP_DOC=2;            //Código del tipo de documento.
    final int INT_DET_COD_DOC=3;                //Código del documento.
    final int INT_DET_COD_REG=4;                //Código del documento.
    final int INT_DET_CAL_COS_UNI=5;             //Calcular costo unitario.
    final int INT_DET_EST_REG=6;                //Estado del registro.
    final int INT_DET_CAN=7;                    //Cantidad.
    final int INT_DET_COS_UNI=8;                //Costo unitario.
    final int INT_DET_POR_DES=9;                 //Porcentaje de descuento.
    final int INT_DET_SAL_UNI=10;               //Saldo en unidades.
    final int INT_DET_SAL_VAL=11;               //Saldo en valores.
    final int INT_DET_COS_UNI_DOC=12;           //Costo unitario del item en el documento.
    
    final int intarreglonum[] = new int[10]; 
    final int intarreglodia[] = new int[10]; 
    final int intCanArr[]= new int[1];
    final ArrayList arreBod=new ArrayList();
    final String strColorHtml = "<FONT COLOR=\"blue\">";
    
    java.util.Vector vecForPag; //Vector que contiene el codigo del pago
    java.util.Vector vecDetDiario;    
    javax.swing.JPanel panForRet = new javax.swing.JPanel();
    javax.swing.JLabel lblForRet = new javax.swing.JLabel();
    javax.swing.JTextField txtCod = new javax.swing.JTextField();
    javax.swing.JTextField txtDes = new javax.swing.JTextField();
    javax.swing.JButton butForret = new javax.swing.JButton();
    javax.swing.JLabel lblvehRet = new javax.swing.JLabel();
    javax.swing.JTextField txtVehRet = new javax.swing.JTextField();
    javax.swing.JTextField txtChoRet = new javax.swing.JTextField(); 
    javax.swing.JLabel lblChoret = new javax.swing.JLabel();
    javax.swing.JDialog jfrThis; //Hace referencia a this
    javax.swing.JTextArea txtsql;
    javax.swing.JTextArea txtcli_identificacion = new javax.swing.JTextArea();
    javax.swing.JTextArea txtcli_telefono = new javax.swing.JTextArea();
    javax.swing.JTextArea txtcli_ciudad = new javax.swing.JTextArea();

    public StringBuffer stbDocRelEmp= new StringBuffer();
    private java.util.Date datFecAux;   
    
    private ZafStkInv objStkInv;
    int intCodEmpRep=0;
    int intCodLocRep=0;
    int intCodTipDocRep=0;
    int intCodDocRep=0;
    int intCodPrvRep=0;
    int intTipGenOrdCom=0;

    String strConfInv="F";

/** Creates new form zafCotCom */
public ZafVen01_OC(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, java.sql.Connection con_local,  int intNummmOrc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodPrv, int intTipVis  ) {
  super(parent, modal);
  this.objZafParSis = obj;
  intCodMnu = intNummmOrc;
  intCodEmpRep=intCodEmp;
  intCodLocRep=intCodLoc;
  intCodTipDocRep=intCodTipDoc;
  intCodDocRep=intCodDoc;
  intCodPrvRep=intCodPrv;
  intTipGenOrdCom=1;

 
  CONN_GLO = con_local;

  jfrThis = this;
  initComponents();
  this.setTitle("Orden de Compra. "+VERSION);
  objUti = new ZafUtil();
  txtsql = new javax.swing.JTextArea();
  objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);
  objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
  objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
  objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);

  tabOrdCom.addTab("Diario",objDiario);

  if(intTipVis==1)
    butguardar.setVisible(false);

  txtCodTipDoc.setVisible(false);
  txtPrvCod.setText(""+intCodPrv);
  txtComCod.setText(""+objZafParSis.getCodigoUsuario());
  txtComNom.setText( objZafParSis.getNombreUsuario() );
  txtComCod.setEditable(false);
  txtComNom.setEditable(false);
  txtCot.setEnabled(false);
  txtOrdCom.setEditable(false);
  txtDoc.setEditable(false);
  butCom.setEnabled(false);


  txtPrvCod.setEnabled(false);
  txtPrvNom.setEditable(false);
  txtPrvDir.setEditable(false);
  butPrv.setEnabled(false);


  cargaTipoDocPredeterminado();
  intNumDec = 2;
  strFecSis     = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
  strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

  txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
  txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
  txtFecDoc.setText(strFecSis);
  panCotGenNor.add(txtFecDoc);
  txtFecDoc.setBounds(530, 2, 92, 20);


 //txtFecDoc.setEnabled(false);


    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                     java.util.Date dateObj =datFecAux;
                     java.util.Calendar calObj = java.util.Calendar.getInstance();
                     calObj.setTime(dateObj);
                     txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );

  /********************************/
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
  txtCod.setBounds(130, 20, 40, 21);
  panForRet.add(butForret);

   butForret.setText("...");
   butForret.setPreferredSize(new java.awt.Dimension(35, 30));
   butForret.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        BuscarFormaRetiro();
    }});
    butForret.setBounds(350, 20, 22, 20);
    tabOrdCom.addTab("Forma de Retiro", panForRet);

    //********************************
    tabOrdCom.setTitleAt(0,"General");
    tabOrdCom.setTitleAt(1,"Forma de Pago");
    tabOrdCom.setTitleAt(2,"Asiento de Diario");
    vecForPag = new java.util.Vector();

    cargarTipEmp(CONN_GLO);
    FormaRetencion(CONN_GLO);
    /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
            NO se altere el formulario y se vea bonito  */
     spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
     ActLisForPag objActLis = new ActLisForPag();
     cboForPag.addActionListener(objActLis);
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
       objUti.verDecimalesEnTabla(tblOrdComForPag,4, objZafParSis.getDecimalesMostrar());
       addListenerCambio();
        /* Clase ke agrega numeracion a el JTable
         */
     ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblOrdComForPag,0);
     CargarBodegas(CONN_GLO);
  }

 private boolean blnIsSolTraInv;
    

/* Viene de ZafVen01 */
/** Creates new form zafCotCom */
public ZafVen01_OC(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCodCot2, int intCodPrv2, int intNummmOrc, String codcom, String nomCom , java.sql.Connection con_local, int intTipDoc , int intCodDoc , int intNumConCen, int intTipVis, boolean blnSolTraInv  ) {
  super(parent, modal);
  this.objZafParSis = obj;
  intCodMnu = intNummmOrc; //45 "Ordenes de compra..."
  intCodCot = intCodCot2;
  intCodPrv=intCodPrv2;
  intTipDocFac=intTipDoc;  // 1 FACVEN o 228 FACVENE
   blnIsSolTraInv=blnSolTraInv;
  intCodDocFac=intCodDoc;
 
  intTipGenOrdCom=2;
    objStkInv = new Librerias.ZafStkInv.ZafStkInv(objZafParSis);
  CONN_GLO = con_local;
  INTCODREGCEN=intNumConCen;   
  jfrThis = this;
  initComponents();
  this.setTitle("Orden de Compra. "+VERSION);
  objUti = new ZafUtil();
  txtsql = new javax.swing.JTextArea();
  objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);
  objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);         
  objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);         
  objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
 
  tabOrdCom.addTab("Diario",objDiario);    

  if(intTipVis==1)
    butguardar.setVisible(false);
      
  txtCodTipDoc.setVisible(false);        
  txtPrvCod.setText(""+intCodPrv);
  txtComCod.setText(codcom);
  txtComNom.setText(nomCom);
  txtComCod.setEditable(false);
  txtComNom.setEditable(false);
  txtCot.setEnabled(false);
  txtOrdCom.setEditable(false);
  txtDoc.setEditable(false);
  butCom.setEnabled(false);

  cargaTipoDocPredeterminado();
  intNumDec = 2;
  strFecSis     = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
  strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

  txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
  txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
  txtFecDoc.setText(strFecSis);
  panCotGenNor.add(txtFecDoc);
  txtFecDoc.setBounds(530, 2, 92, 20);
  txtFecDoc.setEnabled(false);

  
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                     java.util.Date dateObj =datFecAux;
                     java.util.Calendar calObj = java.util.Calendar.getInstance();
                     calObj.setTime(dateObj);
                     txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
  
  /********************************/
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
  txtCod.setBounds(130, 20, 40, 21);
  panForRet.add(butForret);
           
   butForret.setText("...");
   butForret.setPreferredSize(new java.awt.Dimension(35, 30));
   butForret.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        BuscarFormaRetiro();
    }});
    butForret.setBounds(350, 20, 22, 20);
    tabOrdCom.addTab("Forma de Retiro", panForRet);
        
    //********************************
    tabOrdCom.setTitleAt(0,"General");
    tabOrdCom.setTitleAt(1,"Forma de Pago");
    tabOrdCom.setTitleAt(2,"Asiento de Diario");
    vecForPag = new java.util.Vector();
    
    cargarTipEmp(CONN_GLO);
    FormaRetencion(CONN_GLO);
    /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
            NO se altere el formulario y se vea bonito  */
     spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
     ActLisForPag objActLis = new ActLisForPag();
     cboForPag.addActionListener(objActLis);
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
       objUti.verDecimalesEnTabla(tblOrdComForPag,4, objZafParSis.getDecimalesMostrar());
       addListenerCambio();
      /* Clase ke agrega numeracion a el JTable
      */
     new Librerias.ZafColNumerada.ZafColNumerada(tblOrdComForPag,0);         
     CargarBodegas(CONN_GLO);
  }

     
       


    /* Viene de ZafVen42 Reservas OC  */
    
    javax.swing.JFrame FrmPrincipal;    
    public ZafVen01_OC(java.awt.Frame parent, Librerias.ZafParSis.ZafParSis obj, int intCodCot2, int intCodPrv2, int intNummmOrc, String codcom, String nomCom , java.sql.Connection conExt,  int intCodDoc, boolean blnSolTraInv  ) {

      this.objZafParSis = obj;
      intCodMnu = intNummmOrc; //45 "Ordenes de compra..."
      intCodCot = intCodCot2;
      intCodPrv=intCodPrv2;
      intTipDocFac=228;  // 1 FACVEN o 228 FACVENE
       blnIsSolTraInv=blnSolTraInv;
      intCodDocFac=intCodDoc;

      intTipGenOrdCom=2;
        objStkInv = new Librerias.ZafStkInv.ZafStkInv(objZafParSis);
     

      jfrThis = this;
      initComponents();
      this.setTitle("Orden de Compra. "+VERSION);
      objUti = new ZafUtil();
      txtsql = new javax.swing.JTextArea();
      objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);
      objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);         
      objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);         
      objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);

      tabOrdCom.addTab("Diario",objDiario);    

      txtCodTipDoc.setVisible(false);        
      txtPrvCod.setText(""+intCodPrv);
      txtComCod.setText(codcom);
      txtComNom.setText(nomCom);
      txtComCod.setEditable(false);
      txtComNom.setEditable(false);
      txtCot.setEnabled(false);
      txtOrdCom.setEditable(false);
      txtDoc.setEditable(false);
      butCom.setEnabled(false);

      cargaTipoDocPredeterminado();
      intNumDec = 2;
      strFecSis     = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());      
      strFecSisBase = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(), objZafParSis.getFormatoFechaBaseDatos());

      txtFecDoc = new Librerias.ZafDate.ZafDatePicker(FrmPrincipal,"d/m/y"); 
      txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
      txtFecDoc.setText(strFecSis);
      panCotGenNor.add(txtFecDoc);
      txtFecDoc.setBounds(530, 2, 92, 20);
      txtFecDoc.setEnabled(false);


        datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                         java.util.Date dateObj =datFecAux;
                         java.util.Calendar calObj = java.util.Calendar.getInstance();
                         calObj.setTime(dateObj);
                         txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                              calObj.get(java.util.Calendar.MONTH)+1     ,
                                              calObj.get(java.util.Calendar.YEAR)        );

      /********************************/
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
      txtCod.setBounds(130, 20, 40, 21);
      panForRet.add(butForret);

       butForret.setText("...");
       butForret.setPreferredSize(new java.awt.Dimension(35, 30));
       butForret.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BuscarFormaRetiro();
        }});
        butForret.setBounds(350, 20, 22, 20);
        tabOrdCom.addTab("Forma de Retiro", panForRet);

        //********************************
        tabOrdCom.setTitleAt(0,"General");
        tabOrdCom.setTitleAt(1,"Forma de Pago");
        tabOrdCom.setTitleAt(2,"Asiento de Diario");
        vecForPag = new java.util.Vector();

        cargarTipEmp(conExt);
        FormaRetencion(conExt);
        /*  Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
                NO se altere el formulario y se vea bonito  */
         spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
         ActLisForPag objActLis = new ActLisForPag();
         cboForPag.addActionListener(objActLis);
            /* Metodo para agregar o eliminar lineas con enter y con escape
             */
           objUti.verDecimalesEnTabla(tblOrdComForPag,4, objZafParSis.getDecimalesMostrar());
           addListenerCambio();
          /* Clase ke agrega numeracion a el JTable
          */
         new Librerias.ZafColNumerada.ZafColNumerada(tblOrdComForPag,0);         
         CargarBodegas(conExt);
      }



     
    
    private void cerrarVentana(){
        String strMsg = "¿Está Seguro que desea cancelar el proceso de Facturación.?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
        {   
               blnAcepta = true;
               blnEst=true; 
              
           cerrarObj();
           System.gc(); 
           dispose(); 
        }    
    }
    
    
      public boolean acepta(){
        return blnEst;
    }
    
    
       public String GetCamSel(int Idx){
        if(!(Str_RegSel==null)){
            if(Idx <= 0 || Idx > Str_RegSel.length)
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length) ;
            else
                return Str_RegSel[Idx-1];
        }else{
            return "";
        }
    }
    
    
  
    
   
    public void mostrarMsg(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.OK_OPTION);
    }
    
    
int intCodMotBien=0;
int intCodMotServ=0;
int intCodMotTran=0;

      public boolean FormaRetencion(java.sql.Connection conTmp){
         boolean blnRes=false; 
         java.sql.Statement stmTmp;
         java.sql.ResultSet rst;
         try
          {
           if (conTmp!=null)
           {
              stmTmp = conTmp.createStatement(); 
              String sql = "SELECT tx_tipmot, co_mot FROM tbm_motdoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND tx_tipmot in ('B','S','T')";
               System.out.println("FormaRetencion " + sql);
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
              /* José Marín M. 5/Nov/2014 Solicitado por Ingrid Lino, Para nuevo manejo de OC  */
              String sql;
//              if (objZafParSis.getCodigoUsuario()==1){
                  sql = "SELECT distinct(co_bod) FROM tbr_bodLoc WHERE co_emp="+objZafParSis.getCodigoEmpresa(); 
//              }
//              else{ /* José Marín M. 5/Nov/2014 Solicitado por Ingrid Lino, Para nuevo manejo de OC  */
//                  sql = " SELECT (a1.co_bod)  \n";
//                  sql +=" FROM tbr_bodLoc as a1 \n";
//                  sql +=" INNER JOIN tbr_bodTipDocPrgUsr as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_bod=a2.co_bod) \n";
//                  sql +=" WHERE a2.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND a2.co_loc= " + objZafParSis.getCodigoLocal() + " \n";
//                  sql +="       AND a2.co_tipDoc=2 AND a2.co_mnu=" + objZafParSis.getCodigoMenu() + " AND \n";
//                  sql +="       a2.co_usr=" + objZafParSis.getCodigoUsuario() + " AND a2.st_reg IN ('A','P') AND \n";
//                  sql +="       a2.tx_natBod = 'I' AND a1.st_reg IN ('A','P') \n";
//              }
//              System.out.println("CargarBodegas-----> " + sql);
              /* José Marín M. 5/Nov/2014 Solicitado por Ingrid Lino, Para nuevo manejo de OC  */
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
        configurarVenConProveedor();
        configurarVenConVendedor();
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
             strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" + 
             " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
             " where b.co_emp="+objZafParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";
            
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
            arlCam.add("a.tx_desLar");
            arlCam.add("a.tx_tipper");
            arlCam.add("a.co_tipper");
            arlCam.add("a.st_ivacom");
            arlCam.add("a.nd_maxdescom");
            
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("Ciudad");
            arlAli.add("Tip.Per");
            arlAli.add("Cod.Per");
            arlAli.add("Iva.Com");
            arlAli.add("Desc.Com");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("30");
            //Armar la sentencia SQL.
             String  strSQL="";
             
            strSQL="SELECT  a.co_cli,tx_nom,tx_dir,tx_tel,tx_ide ,ciu.tx_desLar, a.tx_tipper ,a.co_tipper, a.st_ivacom, a.nd_maxdescom  " +
            " FROM tbr_cliloc as a1 " +
            "INNER JOIN tbm_cli AS a on (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli) " +
            "LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu) " +
            "WHERE  a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc="+objZafParSis.getCodigoLocal()+"  and a.st_reg='A'  and a.st_prv='S' order by a.tx_nom";
             
            int intColOcu[]=new int[3];
            intColOcu[0]=6;
            intColOcu[1]=7;
            intColOcu[2]=8;
             
            objVenConPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
     
     
   

private boolean Configurar_tabla() {
 boolean blnRes=false;
 try{
     
     System.out.println("ZafVen01_OC Configurar_tabla");
    txtCod.setBackground(objZafParSis.getColorCamposObligatorios());
    txtDes.setBackground(objZafParSis.getColorCamposObligatorios());
    //Configurar JTable: Establecer el modelo.
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();
            
    vecCab.add(INT_TBL_LINEA,"");            
    vecCab.add(INT_TBL_ITMALT,"Cod. Item");
    vecCab.add(INT_TBL_BUTITM,"");
    vecCab.add(INT_TBL_DESITM,"Descripcion");            
    vecCab.add(INT_TBL_UNIDAD,"Unidad");
    vecCab.add(INT_TBL_CODBOD,"Cód.Bod");
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
    vecCab.add(INT_TBL_PRECOSORI,"CosOri.");
    vecCab.add(INT_TBL_ITMSER,"Ser."); 
    vecCab.add(INT_TBL_NUMREL,"NumRel");
    vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod"); 
    vecCab.add(INT_TBL_PORDESORI, ""); 
    
    
    
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);   
    //Configurar JTable: Establecer tipo de selección.
    tblDat.setRowSelectionAllowed(true);
    tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            
    //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_CANORI, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CANMOV, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PRECOS, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PORDES, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_TOTAL, objTblMod.INT_COL_DBL, new Integer(0), null);
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
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(10);         
    tcmAux.getColumn(INT_TBL_ITMALT).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
    tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(155);
    tcmAux.getColumn(INT_TBL_UNIDAD).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);         
    tcmAux.getColumn(INT_TBL_PRECOS).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(80);         
    tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);         
    tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(80);     
    tcmAux.getColumn(INT_TBL_ITMSER).setPreferredWidth(10);     
   



       
  /* Aqui se agrega las columnas que van 
        ha hacer ocultas 
    * */  
    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_ITMORI);
   // arlColHid.add(""+INT_TBL_CODBOD);   ACTIVAME!!!!  
    arlColHid.add(""+INT_TBL_CODITM);
    arlColHid.add(""+INT_TBL_IVATXT);
    arlColHid.add(""+INT_TBL_CANORI);
    arlColHid.add(""+INT_TBL_BODORI);
    arlColHid.add(""+INT_TBL_ESTADO);
    arlColHid.add(""+INT_TBL_ITMALT2);
    arlColHid.add(""+INT_TBL_ITMSER);
    arlColHid.add(""+INT_TBL_BUTITM);
   //************************************* arlColHid.add(""+INT_TBL_IEBODFIS);
    arlColHid.add(""+INT_TBL_PRECOSORI);
    arlColHid.add(""+INT_TBL_NUMREL);
    arlColHid.add(""+INT_TBL_PORDESORI);
    
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;
            
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
    //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
    tblDat.getTableHeader().setReorderingAllowed(false);            
    objMouMotAda=new ZafMouMotAda();            
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    if(intTipGenOrdCom!=1){
     vecAux.add("" + INT_TBL_DESITM);
     vecAux.add("" + INT_TBL_PRECOS);
     vecAux.add("" + INT_TBL_PORDES);
    }
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
    objTblCelRenChk=null;

    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
    tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_DESITM).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_UNIDAD).setCellRenderer(objTblCelRenLbl);
    objTblCelRenLbl=null;

    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
    objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
    objTblCelRenLbl.setFormatoNumerico("######",true,true);
    tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);            
    objTblCelRenLbl=null;

    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
    objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
    objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
    tcmAux.getColumn(INT_TBL_CANORI).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_PRECOS).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
    objTblCelRenLbl=null;
            
    objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
    tcmAux.getColumn(INT_TBL_PRECOS).setCellEditor(objTblCelEdiTxt);
    tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
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

               double dlbDesCom=Double.parseDouble( objInvItm.getIntDatoValidado( tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES) ) );

                if ( dlbDesCom > dblMaxDesCom ){
                    MensajeInf("Error! Ha sobrepasado el máximo Porcentaje de Descuento de asignado al proveedor. ");
                    tblDat.setValueAt(strBeforeValue, tblDat.getSelectedRow(), INT_TBL_PORDES);
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
    blnRes=true;    
 }catch(Exception e){ blnRes=false;   objUti.mostrarMsgErr_F1(this,e); }
 return blnRes;                        
}    

   


       
   
   public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
       
   }
             
    private void cargaTipoDocPredeterminado()
    {
        System.out.println("ZafVen01_OC cargaTipoDocPredeterminado ");
        objTipDoc.DocumentoPredeterminado(intCodMnu);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomTipDoc.setText(objTipDoc.gettx_descor());
        txtDetTipDoc.setText(objTipDoc.gettx_deslar());
          
       objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
       
        getIva();
    }
    
   

public void cargarTipEmp(java.sql.Connection conTipEmp){
 Statement stmTipEmp;
 ResultSet rstEmp;
 String sSql;
 try{
  if(conTipEmp!=null){
     stmTipEmp=conTipEmp.createStatement();
     sSql="select b.co_tipper, b.tx_descor  from tbm_emp as a " +
     " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
     " where a.co_emp="+objZafParSis.getCodigoEmpresa();
      rstEmp = stmTipEmp.executeQuery(sSql);
     if(rstEmp.next()){
        strTipEmp=rstEmp.getString("tx_descor");
        intCodTipPerEmp=rstEmp.getInt("co_tipper");
     }
     rstEmp.close();
     rstEmp=null;
     stmTipEmp.close();
     stmTipEmp=null;
 }}catch(SQLException Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); }    
}


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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        butguardar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
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
        panCotGenNor.add(txtComNom);
        txtComNom.setBounds(516, 20, 145, 20);

        butCom.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCom.setText("...");
        butCom.setOpaque(false);
        butCom.setPreferredSize(new java.awt.Dimension(20, 20));
        butCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butComActionPerformed(evt);
            }
        });
        panCotGenNor.add(butCom);
        butCom.setBounds(664, 20, 20, 20);

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
        txtNomTipDoc.setEditable(false);
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
        txtNomTipDoc.setBounds(112, 4, 60, 20);

        txtDetTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDetTipDoc.setEditable(false);
        txtDetTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDetTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDetTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtDetTipDoc);
        txtDetTipDoc.setBounds(176, 4, 190, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.setEnabled(false);
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(butTipDoc);
        butTipDoc.setBounds(370, 5, 20, 20);

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
        lblNumDoc.setText("No. Documento:");
        panCotGenNor.add(lblNumDoc);
        lblNumDoc.setBounds(6, 46, 102, 15);

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
        panCotGenNor.add(txtOrdCom);
        txtOrdCom.setBounds(112, 64, 92, 20);
        panCotGenNor.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(310, 30, 57, 20);

        chkEmiChq.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        chkEmiChq.setText("Emitir cheque antes de recivir la factura del proveedor");
        panCotGenNor.add(chkEmiChq);
        chkEmiChq.setBounds(5, 126, 500, 20);

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

        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

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
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblIva.setText("IVA 14%:");
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
        cboForPag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboForPagItemStateChanged(evt);
            }
        });
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

        jPanel1.setMinimumSize(new java.awt.Dimension(40, 10));
        jPanel1.setPreferredSize(new java.awt.Dimension(60, 45));
        jPanel1.setLayout(new java.awt.BorderLayout());

        butguardar.setText("Guardar");
        butguardar.setPreferredSize(new java.awt.Dimension(90, 25));
        butguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butguardarActionPerformed(evt);
            }
        });
        jPanel2.add(butguardar);

        jButton1.setText("Cancelar.");
        jButton1.setActionCommand("Cerrar");
        jButton1.setMaximumSize(new java.awt.Dimension(100, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(700, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
         
          boolean a = ValidaFormaRet();
               if(a == true){  blnEst=true; cerrarVentana(); }
          
        
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
       //  boolean a = ValidaFormaRet();
         //         if(a == true){  
                      
                       
                     
                      cerrarVentana(); //}
         
         
       
        
    }//GEN-LAST:event_jButton1ActionPerformed

    public boolean ValidaFormaRet(){
             if( txtDoc.getText().equals("") ){
                   javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                   String strTit, strMsg;
                   strTit="Mensaje del sistema Zafiro";
                   strMsg="<< Es obligatorio que la Orden de compra  sea guardada  >>";
                   obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                   return false;
              }
        return true;
     }    
    
        
  
    
    private void butguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butguardarActionPerformed
        // TODO add your handling code here:
        System.out.println("ZafVen01_OC: butguardarActionPerformed");
     if(intTipGenOrdCom==1){
         strConfInv="P";
         insertarOrdRep();
     }else
      insertarOrd();
        
      
          
         
    }//GEN-LAST:event_butguardarActionPerformed


private void insertarOrd(){
     if(!insertar()){
           butguardar.setEnabled(false);
       }else{

            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="La operación INSERTAR se realizó con éxito.";
            oppMsg.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);

           butguardar.setEnabled(false);
           setEditable(false);
           objDiario.setEditable(false);

            boolean est=false;

            java.awt.Color color = txtTot.getBackground();
            txtCot.setEditable(est);
            txtCot.setBackground(color);

            color = txtDoc.getBackground();
            txtDoc.setEditable(est);
            txtDoc.setBackground(color);

            color = txtAte.getBackground();
            txtAte.setEditable(est);
            txtAte.setBackground(color);

            color = txtFacPrv.getBackground();
            txtFacPrv.setEditable(est);
            txtFacPrv.setBackground(color);

            color = txtPrvCod.getBackground();
            txtPrvCod.setEditable(est);
            txtPrvCod.setBackground(color);

            color = txtPrvNom.getBackground();
            txtPrvNom.setEditable(est);
            txtPrvNom.setBackground(color);

            color = txtPrvDir.getBackground();
            txtPrvDir.setEditable(est);
            txtPrvDir.setBackground(color);

            txtFecDoc.setEnabled(est);

            color = txtComCod.getBackground();
            txtComCod.setEditable(est);
            txtComCod.setBackground(color);

            color = txtComNom.getBackground();
            txtComNom.setEditable(est);
            txtComNom.setBackground(color);

            color = txtOrdCom.getBackground();
            txtOrdCom.setEditable(est);
            txtOrdCom.setBackground(color);

            color = txtCod.getBackground();
            txtCod.setEditable(est);
            txtCod.setBackground(color);

            color = txtDes.getBackground();
            txtDes.setEditable(est);
            txtDes.setBackground(color);

            color = txtVehRet.getBackground();
            txtVehRet.setEditable(est);
            txtVehRet.setBackground(color);

            color = txtChoRet.getBackground();
            txtChoRet.setEditable(est);
            txtChoRet.setBackground(color);

            color = txtNomTipDoc.getBackground();
            txtNomTipDoc.setEditable(est);
            txtNomTipDoc.setBackground(color);

            color = txtDetTipDoc.getBackground();
            txtDetTipDoc.setEditable(est);
            txtDetTipDoc.setBackground(color);

            butForret.setEnabled(est);
            butTipDoc.setEnabled(est);
            butPrv.setEnabled(est);
            butCom.setEnabled(est);
            cboForPag.setEnabled(est);

           cerrarObj();
           System.gc();
           dispose();

       }

}


private void insertarOrdRep(){
    System.out.println("ZafVen01_OC: insertarOrdRep");
     if(!insertarRep()){
           butguardar.setEnabled(true);
       }else{

            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="La operación INSERTAR se realizó con éxito.";
            oppMsg.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);

           butguardar.setEnabled(false);
           setEditable(false);
           objDiario.setEditable(false);

            boolean est=false;

            java.awt.Color color = txtTot.getBackground();
            txtCot.setEditable(est);
            txtCot.setBackground(color);

            color = txtDoc.getBackground();
            txtDoc.setEditable(est);
            txtDoc.setBackground(color);

            color = txtAte.getBackground();
            txtAte.setEditable(est);
            txtAte.setBackground(color);

            color = txtFacPrv.getBackground();
            txtFacPrv.setEditable(est);
            txtFacPrv.setBackground(color);

            color = txtPrvCod.getBackground();
            txtPrvCod.setEditable(est);
            txtPrvCod.setBackground(color);

            color = txtPrvNom.getBackground();
            txtPrvNom.setEditable(est);
            txtPrvNom.setBackground(color);

            color = txtPrvDir.getBackground();
            txtPrvDir.setEditable(est);
            txtPrvDir.setBackground(color);

            txtFecDoc.setEnabled(est);

            color = txtComCod.getBackground();
            txtComCod.setEditable(est);
            txtComCod.setBackground(color);

            color = txtComNom.getBackground();
            txtComNom.setEditable(est);
            txtComNom.setBackground(color);

            color = txtOrdCom.getBackground();
            txtOrdCom.setEditable(est);
            txtOrdCom.setBackground(color);

            color = txtCod.getBackground();
            txtCod.setEditable(est);
            txtCod.setBackground(color);

            color = txtDes.getBackground();
            txtDes.setEditable(est);
            txtDes.setBackground(color);

            color = txtVehRet.getBackground();
            txtVehRet.setEditable(est);
            txtVehRet.setBackground(color);

            color = txtChoRet.getBackground();
            txtChoRet.setEditable(est);
            txtChoRet.setBackground(color);

            color = txtNomTipDoc.getBackground();
            txtNomTipDoc.setEditable(est);
            txtNomTipDoc.setBackground(color);

            color = txtDetTipDoc.getBackground();
            txtDetTipDoc.setEditable(est);
            txtDetTipDoc.setBackground(color);

            butForret.setEnabled(est);
            butTipDoc.setEnabled(est);
            butPrv.setEnabled(est);
            butCom.setEnabled(est);
            cboForPag.setEnabled(est);

           cerrarObj();
           System.gc();
           dispose();

       }

}
    /**
     * Viene desde Reservas de Inventario
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */

    public boolean cargarOCReservas(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot, int CodTipDocOC, int CodDocOC){
        boolean blnRes=true;
        try{
            Configura_ventana_consulta();
            Configurar_tabla();
            BuscarProveedor("a.co_cli",txtPrvCod.getText(),0);
            if(!refrescaDatosReserva(conExt,CodEmp,CodLoc,CodCot,CodTipDocOC,CodDocOC)){
                System.out.println("cargarOCReservas ERROR no deber entra aki");
                blnRes=false;
            }
            
            objDiario.setEditable(false);
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }



    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
           Configura_ventana_consulta();
           Configurar_tabla();
          
           BuscarProveedor("a.co_cli",txtPrvCod.getText(),0);

           if(intTipGenOrdCom==1){

               refrescaDatosRep();

           }else
            refrescaDatos();

           

           objDiario.setEditable(false);
           
    }//GEN-LAST:event_formWindowOpened

    private void cboForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboForPagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboForPagActionPerformed

    private void txtPrvDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrvDirActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
      
          
    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        // TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
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
    //   listaTipdoc("a.tx_deslar",txtDetTipDoc.getText(),2);
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
    }//GEN-LAST:event_txtOrdComActionPerformed

    private void txtCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCotActionPerformed
       
    }//GEN-LAST:event_txtCotActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:         
     
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNomTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipDocFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNomTipDocFocusLost

    
    
      public void cerrarObj(){
       try{
          objVenConVen.dispose();
          objVenConVen=null;  
          objVenConPrv.dispose();
          objVenConPrv=null;
          
         objUti=null;
         objInvItm=null;
         objlisCambios=null;
         objZafParSis=null;
         txtFecDoc=null;
        
         objUltDocPrint=null;
         System.out.println("CERRANDO LOS OBJETOS..");
        }
         catch (Exception e)  { objUti.mostrarMsgErr_F1(this, e);  }
     }
      
    
    
    
    private void txtNomTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipDocActionPerformed
        // TODO add your handling code here:
       
      
    }//GEN-LAST:event_txtNomTipDocActionPerformed

  
    private void txtPrvCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrvCodFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrvCodFocusLost

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        // TODO add your handling code here:
      objVenConPrv.setTitle("Listado de Clientes");         
              objVenConPrv.setCampoBusqueda(1);
              objVenConPrv.show();
             if (objVenConPrv.getSelectedButton()==objVenConPrv.INT_BUT_ACE)
            {
                txtPrvCod.setText(objVenConPrv.getValueAt(1));
                txtPrvNom.setText(objVenConPrv.getValueAt(2));
                txtPrvDir.setText(objVenConPrv.getValueAt(3));
                txtcli_telefono.setText(objVenConPrv.getValueAt(4));
                txtcli_identificacion.setText(objVenConPrv.getValueAt(5));
                txtcli_ciudad.setText(objVenConPrv.getValueAt(6));
                strTipPer_glo = objVenConPrv.getValueAt(7);
                strCodTipPerCli =  objVenConPrv.getValueAt(8); 
                stIvaCom =  objVenConPrv.getValueAt(9);

                dblMaxDesCom =  Double.parseDouble( objVenConPrv.getValueAt(10) );

                calculaPagos();
             } 
    }//GEN-LAST:event_butPrvActionPerformed

    private void txtPrvNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvNomActionPerformed
        // TODO add your handling code here:
  
          BuscarProveedor("a.tx_nom",txtPrvNom.getText(),1);
    }//GEN-LAST:event_txtPrvNomActionPerformed

    private void txtPrvCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvCodActionPerformed
        // TODO add your handling code here:
   
          BuscarProveedor("a.co_cli",txtPrvCod.getText(),0);
    }//GEN-LAST:event_txtPrvCodActionPerformed

     public void BuscarProveedor(String campo,String strBusqueda,int tipo){
        objVenConPrv.setTitle("Listado de Clientes"); 
        if (objVenConPrv.buscar(campo, strBusqueda ))
        {
              txtPrvCod.setText(objVenConPrv.getValueAt(1));
              txtPrvNom.setText(objVenConPrv.getValueAt(2));
              txtPrvDir.setText(objVenConPrv.getValueAt(3));
              txtcli_telefono.setText(objVenConPrv.getValueAt(4));
              txtcli_identificacion.setText(objVenConPrv.getValueAt(5));
              txtcli_ciudad.setText(objVenConPrv.getValueAt(6));
              strTipPer_glo = objVenConPrv.getValueAt(7);
              strCodTipPerCli =  objVenConPrv.getValueAt(8); 
              stIvaCom =  objVenConPrv.getValueAt(9);

              dblMaxDesCom =  Double.parseDouble( objVenConPrv.getValueAt(10) );

              calculaPagos();
        }   
        else
        {     objVenConPrv.setCampoBusqueda(tipo);
              objVenConPrv.cargarDatos();
              objVenConPrv.show();
             if (objVenConPrv.getSelectedButton()==objVenConPrv.INT_BUT_ACE)
            {
                txtPrvCod.setText(objVenConPrv.getValueAt(1));
                txtPrvNom.setText(objVenConPrv.getValueAt(2));
                txtPrvDir.setText(objVenConPrv.getValueAt(3));
                txtcli_telefono.setText(objVenConPrv.getValueAt(4));
                txtcli_identificacion.setText(objVenConPrv.getValueAt(5));
                txtcli_ciudad.setText(objVenConPrv.getValueAt(6));
                strTipPer_glo = objVenConPrv.getValueAt(7);
                strCodTipPerCli =  objVenConPrv.getValueAt(8); 
                stIvaCom =  objVenConPrv.getValueAt(9);

                dblMaxDesCom =  Double.parseDouble( objVenConPrv.getValueAt(10) );

                calculaPagos();
             }
        }
         
     
   }
  
 
    
    private void txtComCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComCodFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComCodFocusLost

    private void txtComNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComNomActionPerformed
        // TODO add your handling code here:
       
          BuscarVendedor("a.tx_nom",txtComNom.getText(),1);
    }//GEN-LAST:event_txtComNomActionPerformed

    private void txtComCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComCodActionPerformed
        // TODO add your handling code here:
    
          BuscarVendedor("a.co_usr",txtComCod.getText(),0);
    }//GEN-LAST:event_txtComCodActionPerformed

    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        // TODO add your handling code here:        
     
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
             if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE)
             {
                txtComCod.setText(objVenConVen.getValueAt(1));
                txtComNom.setText(objVenConVen.getValueAt(2));
             }
        }
    }
  
 
    
    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void cboForPagItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboForPagItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboForPagItemStateChanged

  
    private void cargaNum_Doc_OrdCom(){        
//        int intNumeroDoc = objUltDocPrint.getUltDoc(Integer.parseInt(txtCodTipDoc.getText()));
//          intNumeroDoc=(intNumeroDoc+1);
//        txtOrdCom.setText(" " + intNumeroDoc); //(objTipDoc.getne_ultdoc()   ));
//        
        txtOrdCom.setText("0");
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
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

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
            
            txtSub.setEnabled(blnEditable);
            txtSub.setBackground(colBack);        
            
            txtIva.setEditable(blnEditable);
            txtIva.setBackground(colBack);        
            
            txtTot.setEditable(blnEditable);
            txtTot.setBackground(colBack);        
    }
  
    public void  clnTextos(){
        //Cabecera 
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
   
    public void calculaSubtotal(){
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
        
        dblDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_PORDES).toString()))),2);
        
        dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));
                   dblTotal  = (dblCan * dblCosto)- dblValDes;  
                   dblTotal =  objUti.redondear(dblTotal,2);  
                   tblDat.setValueAt(dblTotal+"", intNumFil ,INT_TBL_TOTAL);
        
        
        calculaTotal();
        calculaPagos();
        generaAsiento();
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
        
        dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));
                   dblTotal  = (dblCan * dblCosto)- dblValDes;  
                   dblTotal =  objUti.redondear(dblTotal,2);  
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
            txtIva.setText( "0.00"  );
            txtTot.setText( ""+ dblSubtotalCom);
            dblTotalCom=dblSubtotalCom;
             dblIvaCom=0;       
        }else{
        txtSub.setText( "" + dblSubtotalCom );
        txtIva.setText( "" + dblIvaCom );
        txtTot.setText( ""+ dblTotalCom);
        }
        //************************************************///
        
        calculaPagos();
        generaAsiento();
    }
    
      
      
      
      
   //*************************************************************************************   
    
    public void  calculaTotal(){
        double dblSub = 0, dblIva = 0, dblTmp=0, dblSub2=0;
        for (int i=0;i<tblDat.getRowCount();i++){ 
            dblSub2 = objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesMostrar());  
            dblSub = dblSub + objUti.redondear(dblSub2,intNumDec);
            
           
             if(tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)
                dblIva = dblIva + 0;
             else{
                dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? ((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())) : 0 );
                dblIva = dblIva + (((dblTmp * dblPorIva)==0)?0:(dblTmp * dblPorIva)/100) ;
            }
        }
                    
        
     
        //************************************************///
        dblSubtotalCom = dblSub;
        dblIvaCom = objUti.redondear(dblIva,intNumDec);
        dblTotalCom = dblSubtotalCom + dblIvaCom;
        dblTotalCom = objUti.redondear(dblTotalCom ,intNumDec);
        dblSubtotalCom = objUti.redondear(dblSubtotalCom ,intNumDec);
      if(stIvaCom.equals("N")){ 
        txtSub.setText( "" + dblSubtotalCom );
        txtIva.setText( "0.00"  );
        txtTot.setText( ""+ dblSubtotalCom);
        dblTotalCom=dblSubtotalCom;
          dblIvaCom=0;
      }else{
        txtSub.setText( "" + dblSubtotalCom );
        txtIva.setText( "" + dblIvaCom );
        txtTot.setText( ""+ dblTotalCom);
      } 
        //************************************************///
        
    }
    
    
    
    private ZafCtaCtb_dat objZafCtaCtb_dat;  // Para obtener  los codigos y nombres de ctas ctbles
    private int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
    private int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
    private int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
    private int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
    private int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
    private int INT_VEC_DEBE   = 5; //5) Debe. 
    private int INT_VEC_HABER  = 6; //6) Haber. 
    private int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
    private int INT_VEC_NUEVO    = 8;
    
     private void generaAsiento(){
        java.util.Vector vecReg; 
        int intEst=0, intCodBodT, intCodBod, intCodCtaIvaTra;
        String strSQL, strNumCtaIvaTra, strNomCtaIvaTra;
        boolean blnIsCos_Ecu_Det;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        
        try{     
            
            objDiario.inicializar();
            double dblSubtotal=0, dblTotSer=0;
            
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                     if(!(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N"))) {
                         dblTotSer += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
                         intEst=1;
                      }
                }
            }
      
            if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
            else vecDetDiario.removeAllElements();
          
            System.out.println("Num Bod. "+ arreBod.size() );
            
            intCodCtaIvaTra = 0;
            strNumCtaIvaTra = "";
            strNomCtaIvaTra = "";

            blnIsCos_Ecu_Det = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO") != -1
               || objZafParSis.getNombreEmpresa().toUpperCase().indexOf("ECUATOSA") != -1
               || objZafParSis.getNombreEmpresa().toUpperCase().indexOf("DETOPACIO") != -1)? true: false;

            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            
            if (conLoc != null)
            {  if (blnIsCos_Ecu_Det == false)
               {  stmLoc = conLoc.createStatement();
               
                  switch (objZafParSis.getCodigoEmpresa())
                  {  case 1:
                        intCodCtaIvaTra = 2949;
                        break;

                     case 2:
                        intCodCtaIvaTra = 1250;
                        break;

                     case 4:
                        intCodCtaIvaTra = 2226;
                        break;
                  }
                  
                  strSQL =  "SELECT co_emp, co_cta, tx_codcta, tx_deslar ";
                  strSQL += "FROM tbm_placta ";
                  strSQL += "WHERE st_reg = 'A' and co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_cta = " + intCodCtaIvaTra;
                  rstLoc = stmLoc.executeQuery(strSQL);
                  
                  if (rstLoc.next())
                  {  strNumCtaIvaTra = rstLoc.getString("tx_codcta") == null? "" :rstLoc.getString("tx_codcta");
                     strNomCtaIvaTra = rstLoc.getString("tx_deslar") == null? "" :rstLoc.getString("tx_deslar");
                  }
                  
                  rstLoc.close();
                  rstLoc = null;
                  stmLoc.close();
                  stmLoc = null;
               }
            } //if (conLoc != null)
            
            conLoc.close();
            conLoc = null;
            
            if(objTipDoc.gettx_natdoc().equals("I")){
                for (int j=0;j<arreBod.size();j++){
                   intCodBod = Integer.parseInt(arreBod.get(j).toString());
                   dblSubtotal=0;
                    for (int i=0;i<tblDat.getRowCount();i++){            
                        if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null){
                            if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) {
                                if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0){
                                    intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                                    if(intCodBod==intCodBodT)
                                        dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
                                }
                            }
                        }
                    }
                    
                    if(dblSubtotal > 0 ){
                        vecReg = new java.util.Vector();
                        vecReg.add(INT_LINEA, null);                
                        vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                        vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                        vecReg.add(INT_VEC_BOTON, null);                
                        vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                        vecReg.add(INT_VEC_DEBE, new Double(dblSubtotal));  
                        vecReg.add(INT_VEC_HABER, new Double(0));
                        vecReg.add(INT_VEC_REF, null);
                        vecReg.add(INT_VEC_NUEVO, null);
                        vecDetDiario.add(vecReg);
                    }  
                }   
                /* 2017-05-17 */
                if (stIvaCom.equals("S"))
                {   if (objTipDoc.getst_iva().equals("S"))
                    {   vecReg = new java.util.Vector();
                        vecReg.add(INT_LINEA, null);
                        
                        if (blnIsCos_Ecu_Det == true)
                        {  vecReg.add(INT_VEC_CODCTA, new Integer(objZafCtaCtb_dat.getCtaIvaCompras()));
                           vecReg.add(INT_VEC_NUMCTA, objZafCtaCtb_dat.getNumCtaIva());
                        }
                        else
                        {  vecReg.add(INT_VEC_CODCTA, intCodCtaIvaTra);
                           vecReg.add(INT_VEC_NUMCTA, strNumCtaIvaTra);
                        }
                        
                        vecReg.add(INT_VEC_BOTON, null);
                        
                        if (blnIsCos_Ecu_Det == true)
                        {  vecReg.add(INT_VEC_NOMCTA, objZafCtaCtb_dat.getNomCtaIva());
                        }
                        else
                        {  vecReg.add(INT_VEC_NOMCTA, strNomCtaIvaTra);
                        }
                        
                        vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtIva.getText(),objZafParSis.getDecimalesMostrar())));
                        vecReg.add(INT_VEC_HABER, new Double(0)); 
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
                vecReg.add(INT_VEC_DEBE, new Double(0)); 
                vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar())));
                vecReg.add(INT_VEC_REF, null);
                vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);  
            }
            else{
                System.out.println("Num Bod. "+ arreBod.size() );
                for (int j=0;j<arreBod.size();j++){
                    intCodBod = Integer.parseInt(arreBod.get(j).toString());
                    dblSubtotal=0;
                    for (int i=0;i<tblDat.getRowCount();i++){            
                        if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null){
                            if(tblDat.getValueAt(i,INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")) { 
                                if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0){
                                    intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                                    if(intCodBod==intCodBodT)
                                        dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
                                }
                            }
                        }
                    }
                    if(dblSubtotal > 0 ){
                     vecReg = new java.util.Vector();
                     vecReg.add(INT_LINEA, null);                
                     vecReg.add(INT_VEC_CODCTA, new Integer(objCtaCtb.getCtaExistencia( intCodBod )) );
                     vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getCtaNumExistencia(intCodBod) );
                     vecReg.add(INT_VEC_BOTON, null);                
                     vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getCtaNomExistencia(intCodBod) );
                     vecReg.add(INT_VEC_DEBE, new Double(0));  
                     vecReg.add(INT_VEC_HABER, new Double(dblSubtotal));
                     vecReg.add(INT_VEC_REF, null);
                      vecReg.add(INT_VEC_NUEVO, null);
                     vecDetDiario.add(vecReg);
                    }
                }  
            
                if (stIvaCom.equals("S"))
                {   if (objTipDoc.getst_iva().equals("S"))
                    {   vecReg = new java.util.Vector();
                        vecReg.add(INT_LINEA, null);
                        
                        if (blnIsCos_Ecu_Det == true)
                        {  vecReg.add(INT_VEC_CODCTA, new Integer(objZafCtaCtb_dat.getCtaIvaCompras()));
                           vecReg.add(INT_VEC_NUMCTA, objZafCtaCtb_dat.getNumCtaIva());
                        }
                        else
                        {  vecReg.add(INT_VEC_CODCTA, intCodCtaIvaTra);
                           vecReg.add(INT_VEC_NUMCTA, strNumCtaIvaTra);
                        }
                        
                        vecReg.add(INT_VEC_BOTON, null);
                        
                        if (blnIsCos_Ecu_Det == true)
                        {  vecReg.add(INT_VEC_NOMCTA, objZafCtaCtb_dat.getNomCtaIva());
                        }
                        else
                        {  vecReg.add(INT_VEC_NOMCTA, strNomCtaIvaTra);
                        }
                        
                        vecReg.add(INT_VEC_DEBE, new Double(0)); 
                        vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtIva.getText(),objZafParSis.getDecimalesMostrar())));   
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
                vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar()))); 
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
                vecReg.add(INT_VEC_DEBE, new Double(dblTotSer));
                vecReg.add(INT_VEC_HABER, new Double(0)); 
                vecReg.add(INT_VEC_REF, null);
                 vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);      
            }
         
            objDiario.setDetalleDiario(vecDetDiario);   
            
            objZafCtaCtb_dat=null;
        } 
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(jfrThis, Evt);  
        } 
    }
    
   


    private void MensajeInf(String strMensaje){
            //javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            JOptionPane.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    private void MensajeValidaCampo(String strNomCampo){
            //javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
            JOptionPane.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
  
      /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */


  
    
    
     
    
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

                                   //  obteniendo la fecha actual
                                            int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                                            if(Fecha!=null){
                                                String strFecha = Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0];
                                            }

                                            
                                          
                                            
                                      
                                    java.sql.Connection  conn = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                                     if (conn!=null){       
                                          
                                         
                                          
                                            dblRetFueFle = getSubtotalTrans();
                                          
                                             if(dblRetFueFle>0){
                                                ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());    
                                                 tblOrdComForPag.setValueAt(new String("") ,tblOrdComForPag.getRowCount()-1, 1);
                                                 tblOrdComForPag.setValueAt(txtFecDoc.getText(), tblOrdComForPag.getRowCount()-1, 2);
                                                 dblRetFueFle = objUti.redondeo( (    dblRetFueFle  * 0.01 ),2 );
                                                 tblOrdComForPag.setValueAt("1.000", tblOrdComForPag.getRowCount()-1, 3);
                                                 tblOrdComForPag.setValueAt( new Double(dblRetFueFle) , tblOrdComForPag.getRowCount()-1, 4);
                                            }
                                        
                                              
                                               
                                            
                                              
                                      String strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta, polret.tx_codsri";
                                      strSQL +=" from tbm_polret as polret " +
                                               " left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) " +
                                               " left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret)" ;
                                      strSQL += " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMotBien+" and co_ageret = " + intCodTipPerEmp +" and co_sujret = "+strCodTipPerCli+" "+
                                      "  AND polret.st_reg='A'  AND  '"+strFecSisBase+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
                                      System.out.println("ZafVen01_OC calculaPagos_anterior > "+ strSQL );
                                      stm = conn.createStatement();    
                                      rst = stm.executeQuery(strSQL);
                                      while (rst.next()){
                                            
                                               ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());    
                                                tblOrdComForPag.setValueAt(new String("") ,tblOrdComForPag.getRowCount()-1, 1);
                                                tblOrdComForPag.setValueAt(txtFecDoc.getText(), tblOrdComForPag.getRowCount()-1, 2);
                                               
                                                 if (rst.getString("tx_aplret").equals("S")){
                                                     dblRetFue = getSubtotalSinTrans();
                                                     if(!(dblRetFueFle>0))
                                                        dblRetFue = objUti.redondeo( (    Double.parseDouble(txtSub.getText().equals("")?"0":txtSub.getText())  * ( rst.getDouble("nd_porret")/100)),2 );
                                                     else
                                                        dblRetFue = objUti.redondeo( (    dblRetFue  * ( rst.getDouble("nd_porret")/100)),2 );
                                                     
                                                    tblOrdComForPag.setValueAt(rst.getString("nd_porret"), tblOrdComForPag.getRowCount()-1, 3);
                                                    tblOrdComForPag.setValueAt( new Double(dblRetFue) , tblOrdComForPag.getRowCount()-1, 4);
                                                 }
                                                 if (rst.getString("tx_aplret").equals("I")){
                                                     dblRetIva = objUti.redondeo(  (   Double.parseDouble((txtIva.getText().equals("")?"0.0":txtIva.getText()))  * ( rst.getDouble("nd_porret")/100)),2 ); 
                                                     tblOrdComForPag.setValueAt(rst.getString("nd_porret") , tblOrdComForPag.getRowCount()-1, 3);
                                                     tblOrdComForPag.setValueAt( new Double(dblRetIva) , tblOrdComForPag.getRowCount()-1, 4);
                                                 }    
                                                  
                                            tblOrdComForPag.setValueAt(rst.getString("co_tipret") , tblOrdComForPag.getRowCount()-1, 6);
                                            
                                            tblOrdComForPag.setValueAt(rst.getString("tx_codsri") , tblOrdComForPag.getRowCount()-1, 7);
                                            
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
                                     
                                     dblMonCal = objUti.redondear( ( dblTotalDoc - ( dblRetFue + dblRetIva)),intNumDec);
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
                                     dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRetFue + dblRetIva ) ),intNumDec);
                                       dblultpag = objUti.redondear(dblultpag + (dblTotalDoc  - (dblPago + dblRete ) ),intNumDec);
                                    
                                     
                                       
                                     tblOrdComForPag.setValueAt( new Double(dblultpag) , intFilUltPag, 4);
                                   }
                                 }
                                catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
                                catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
             
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
    dblDes =  objUti.redondeo(Double.parseDouble(((tblDat.getValueAt( intNumFil , INT_TBL_PORDES)==null)?"0":(tblDat.getValueAt( intNumFil , INT_TBL_PORDES).toString()))),2);

           dblValDes =   ((dblCan * dblCosto)==0)?0:((dblCan * dblCosto) * (dblDes / 100));
           dblTotal  = (dblCan * dblCosto)- dblValDes;
           dblTotal =  objUti.redondear(dblTotal,2);
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
}

 if(stIvaCom.equals("N")) dblIvaSerNoSer = 0.00;
 else dblIvaSerNoSer = objUti.redondear(dblIva,intNumDec);


      //  dblIvaSerNoSer = objUti.redondear(dblIva,intNumDec);
        dblSubSerNoSer = objUti.redondear(dblSub ,intNumDec);

 }catch(Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}









    
   double dblRetFueGlo = 0.0, dblRetIvaGlo = 0;
 public void calculaPagos(){
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

                    System.out.println("tblOrdComForPag: " + tblOrdComForPag.getRowCount());
                    
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


 private void cargaForPag(java.sql.Connection conn, int intCodMot ){
   java.sql.Statement stm;
   java.sql.ResultSet rst;
   String strSQL="";
   double dblRetFue=0, dblRetIva=0;
   double dblBasImp=0;
   try{
     if(conn!=null){

      stm=conn.createStatement();

      strSQL =  " select tipret.co_tipret,tipret.tx_descor,tipret.tx_deslar,nd_porret,tx_aplret,co_cta, polret.tx_codsri "+
      " from tbm_polret as polret " +
      " left outer join tbm_motdoc as mot on (polret.co_emp = mot.co_emp and polret.co_mottra = mot.co_mot) " +
      " left outer join tbm_cabtipret as tipret on (polret.co_emp= tipret.co_emp and polret.co_tipret = tipret.co_tipret) "+
      " where polret.co_emp = "+ objZafParSis.getCodigoEmpresa()+" and co_mot = "+intCodMot+"  and co_ageret = " + intCodTipPerEmp +" and co_sujret = "+strCodTipPerCli+" " +
      "  AND polret.st_reg='A'  AND  '"+objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd")+"'  BETWEEN polret.fe_vigdes AND  CASE  when polret.fe_vighas is null then '3000-01-01' else polret.fe_vighas end ";
      System.out.println("ZafVen01_OC  cargaForPag :" + strSQL);
      rst = stm.executeQuery(strSQL);
      while (rst.next()){

           
             if(rst.getString("tx_aplret").equals("S")){

                 ((javax.swing.table.DefaultTableModel)tblOrdComForPag.getModel()).addRow(new java.util.Vector());
                 tblOrdComForPag.setValueAt(new String("") ,tblOrdComForPag.getRowCount()-1, 1);
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
                    tblOrdComForPag.setValueAt(new String("") ,tblOrdComForPag.getRowCount()-1, 1);
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

               }}

              
        }
        rst.close();
        rst=null;

   }}catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
     catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}

  
    
    
   //*******************************************************************************************************
   
   
   
   
    public class ActLisForPag implements java.awt.event.ActionListener{
         public void actionPerformed(java.awt.event.ActionEvent e) {
              if(cboForPag.getSelectedIndex()>-1 ){
                if (cboForPag.getSelectedItem().toString().equals("< Refrescar >")){                                   
                    cargaListadoFormaPago();
                }
              }
               try{
                    if (CONN_GLO!=null){     

                         if(cboForPag.getSelectedIndex()!=-1){

                            

                             String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre " +
                                          " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                          "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) + " Order by A2.co_reg";
                                            
                            String sSQL3 = "SELECT count(A2.ne_diaCre) as c  " +
                                          " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                                          " Where A1.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando 
                                          "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex());
                                System.out.println("ZafVen01_OC  ActLisForPag (1) :" + sSQL2);
                                    Statement stmDoc2   = CONN_GLO.createStatement();                                  
                                    ResultSet rstDocCab2= stmDoc2.executeQuery(sSQL3);
                                    rstDocCab2.next();
                                    intCanArr[0] = rstDocCab2.getInt(1);
                                   System.out.println("ZafVen01_OC  ActLisForPag (2) :" + sSQL3);
                                    stmDoc2   = CONN_GLO.createStatement();                                  
                                    rstDocCab2= stmDoc2.executeQuery(sSQL2);
                                    int x=0;
                                    while(rstDocCab2.next()){
                                          intarreglodia[x]=rstDocCab2.getInt(2);
                                          intarreglonum[x]=rstDocCab2.getInt(1);
                                          x++;  
                                    }
                           }
                       
                    }
                }catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);   }
              
             calculaPagos(); 
             
         }
    }    
    
    
    private void cargaListadoFormaPago(){
        System.out.println("cargarListadoFormaPago ");
        objUti.llenarCbo_F1(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), 
                             objZafParSis.getClaveBaseDatos(), "SELECT a1.co_forpag, a1.tx_des FROM tbm_cabForPag as a1 "
                                     + "Where a1.co_emp = " + objZafParSis.getCodigoEmpresa() + " order by a1.co_forpag", cboForPag, vecForPag);      
        cboForPag.addItem("< Refrescar >");
        cboForPag.setSelectedIndex(0);
    }
    
    
 
    private void cargaListadoFormaPagoOCReservada(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        System.out.println("cargarListadoFormaPago Reservas");
        String strSql="";
        strSql+=" SELECT a1.co_forpag, a1.tx_des \n";
        strSql+=" FROM tbm_cabForPag as a1 \n";
        strSql+=" INNER JOIN tbm_cabMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag) \n";
        strSql+=" WHERE a2.co_emp="+CodEmp+" AND a2.co_loc="+CodLoc+" AND a2.co_tipDoc="+CodTipDoc+" AND a2.co_doc="+CodDoc+"  \n";
        strSql+=" order by a1.co_forpag \n";
        System.out.println("cargaListadoFormaPagoOCReservada: " + strSql);
        llenarCbo_F1(conExt, strSql  );      
        cboForPag.setSelectedIndex(0);
        System.out.println("cargaListadoFormaPagoOCReservada DATO: " + cboForPag.getItemAt(0));
    }
    
    public boolean llenarCbo_F1(java.sql.Connection ConExt, String sentenciaSQL )
    {
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        boolean blnRes=true;
        try
        {
            cboForPag.setSelectedIndex(-1);
            cboForPag.removeAllItems();
            vecForPag.clear();
             
            if (ConExt != null)
            {
                stm = ConExt.createStatement();	
                rst = stm.executeQuery(sentenciaSQL);
                while (rst.next())
                {
                    vecForPag.add(rst.getString(1));
                    cboForPag.addItem(rst.getString(2));
                }
                cboForPag.setSelectedIndex(-1);
                rst.close(); 
                stm.close();
                
                rst=null;
                stm=null;
                 
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, e);
        }
        return blnRes;
    }
    
    
 
    
    
      private boolean validaCampos(){
          System.out.println("ZafVen01_OC: validaCampos");
          
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
               
              
               
               //*********************************************************************************************
               
                   for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                  
                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                                        
                     double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                    
                    if(dblCan <= 0.00 ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
                        return false; 
                    }
                   
                    /********************************************************************/  
                    double dblCos = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PRECOS).toString());
                    if(dblCos <= 0.00 ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();  
                        tblDat.editCellAt(intRowVal, INT_TBL_PRECOS);
                        return false; 
                    }
                   /********************************************************************/
                    
                    
                    /********************************************************************/  
                    
                    
                      
                    double dblDesc = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PORDES).toString());
                    double dblCosto = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PRECOS).toString());
                    double dblCosConDesc= ( dblCosto - (dblCosto * (dblDesc/100) ));
                       
                    
                    //double dblCosOri = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PRECOSORI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PRECOSORI).toString());
                    
                    double dblCosConDesOri = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PORDESORI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PORDESORI).toString());
                  
                    if(dblCosConDesc > dblCosConDesOri ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="EL COSTO MENOS DESCUENTO NO PUEDE SER MAYOR AL COSTO MENOS DESCUENTO ORIGEN.\n VERIFIQUE LOS DATOS.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();  
                        tblDat.editCellAt(intRowVal, INT_TBL_PRECOS);
                        return false; 
                    }
                   /********************************************************************/
                    
                    
                    
//                     
//                    /********************************************************************/  
//                    double dblDes = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PORDES).toString());
//                    double dblDesOri = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_PORDESORI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_PORDESORI).toString());
//                    if(dblDes < dblDesOri ){
//                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
//                        String strTit, strMsg;
//                        strTit="Mensaje del sistema Zafiro";
//                        strMsg="El Descuento no puede ser mayor al descuento origen .\nDigite el descuento correcto.";
//                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                        tblDat.repaint();    
//                        tblDat.requestFocus();  
//                        tblDat.editCellAt(intRowVal, INT_TBL_PORDES);
//                        return false; 
//                    }
//                   /********************************************************************/
//                    
//                      
                    
                            
                            
                    
                    
                        double dblCanOri = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANORI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_CANORI).toString());
                        if(dblCan > dblCanOri ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="La Cantidad no puede ser Mayor a la Cantidad Origen .\nEscriba en el campo y vuelva a intentarlo.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.editCellAt(intRowVal, INT_TBL_CANMOV);
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
               
                 
                if( objUti.redondear(dblTotalMonto, intNumDec)!= objUti.redondear(dblTotalDoc, intNumDec)){
                    tabOrdCom.setSelectedIndex(1);
                    MensajeInf("La suma de los montos a pagar es mayor al total de la Cotización.\nCorrija y vuelva a intentarlo.");
                    return false;
                }
                  
                dblTotalDoc=objUti.redondear(dblTotalDoc, intNumDec);              
                System.out.println("Asiento y Monto Total Descuadrado. " + dblTotalDoc);
               if (!objDiario.isDocumentoCuadradoBde(Double.parseDouble(txtTot.getText()))){
                    MensajeInf("Asiento y Monto Total Descuadrado.");
                    tabOrdCom.setSelectedIndex(2);
                    return false;                                                       
               }

               
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
    private javax.swing.JButton butguardar;
    private javax.swing.JComboBox cboForPag;
    private javax.swing.JCheckBox chkEmiChq;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JTextField txtOrdCom;
    private javax.swing.JTextField txtPrvCod;
    private javax.swing.JTextField txtPrvDir;
    private javax.swing.JTextField txtPrvNom;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
    private String Str_RegSel[];
    
  
    
             
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
           
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);  }
        return auxTipDoc;
      }
         
     

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
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
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
          
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
    
    
    
    
    
    
    
     
    public void  refrescaDatos(){
       
        try{//odbc,usuario,password        
            int intNumDoc = 0;
       
                             
                   // java.sql.Connection   conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

                      if (CONN_GLO!=null){  
                          
                    //Extrayendo los datos del detalle respectivo a esta orden de compra
                
                
                
                 String strAux = ",CASE WHEN (" +
                   " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                   " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                   " AND co_tipdoc="+intTipDocFac+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
                   " ))) THEN 'S' ELSE 'N' END  as isterL";
                
                 //JoséMario Facturación Electronica 6/Oct/2014
                 
            String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
            " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
            " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
            " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
            " THEN 'S' ELSE 'N' END AS proconf  ";
          
                 
                 
                  sSQL = " SELECT * FROM ( " +
                          " SELECT a.nd_pordesprecom,  a.co_reg, a.co_itm,inv.tx_codalt,a.tx_nomitm,  inv.tx_nomitm as nomitminv,  var.tx_descor, a.co_bod , a.co_bodcom, a.nd_can,a.nd_precom,inv.st_ivacom,inv.tx_codalt2, inv.st_ser as servicio ";
                  sSQL +=strAux;
                  sSQL +=strAux2;
                  sSQL +=" FROM tbm_detcotven as a " +
                " inner join tbm_inv as inv on (inv.co_emp=a.co_emp and inv.co_itm=a.co_itm)  " +
                " left outer join tbm_var as var on (inv.co_uni = var.co_reg)  " +
                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_cot="+intCodCot+"  and a.co_prv="+intCodPrv+" "+
                " ) AS x  WHERE  x.isterl='S'   ORDER BY x.co_reg";
                 
                  System.out.println("ZafCen01_OC refrescaDatos " + sSQL);
                    java.sql.Statement stm = CONN_GLO.createStatement();
                    java.sql.ResultSet rst= stm.executeQuery(sSQL);
                     double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;
                      
                     java.util.Vector vecData = new java.util.Vector();
                     while (rst.next()){ 
                        // double dblStkAct = getStkAct(rst.getString("co_itm"));
                         dblCan    = rst.getDouble("nd_can");
                         double dobCanmov = dblCan;  //Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                                      //dobCanmov = dblStkAct-dobCanmov;
                                       //if(dobCanmov < 0){
                                           dblCan = java.lang.Math.abs(dobCanmov);
                         java.util.Vector vecReg = new java.util.Vector();
                         vecReg.add(INT_TBL_LINEA, "");
                         vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                         vecReg.add(INT_TBL_BUTITM, "");
                           
                         String strIva = rst.getString("st_ivacom");
                         if(strIva.equals("S"))
                          vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                         else
                           vecReg.add(INT_TBL_DESITM, rst.getString("nomitminv"));
                         
                         vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_descor"));
                     
                         vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bodcom")));
                     
                         vecReg.add(INT_TBL_CANORI, new Double(dblCan));
                         vecReg.add(INT_TBL_CANMOV, new Double(dblCan));                                                                           
                         vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_precom")));
                         vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordesprecom")) );



                         Boolean blnIva = new Boolean(strIva.equals("S"));                         
                         vecReg.add(INT_TBL_BLNIVA, blnIva);
                          
                         dblPre    = rst.getDouble("nd_precom");
                         dblPorDes = rst.getDouble("nd_pordesprecom");
                         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                         dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;
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
                         vecReg.add(INT_TBL_PRECOSORI, new Double(rst.getDouble("nd_precom")));
                         vecReg.add(INT_TBL_ITMSER, rst.getString("servicio"));
                         vecReg.add(INT_TBL_NUMREL, rst.getString("co_reg"));

                         


                     if( rst.getString("co_bodcom")!=null  ){

                        if(rst.getInt("co_bodcom")==rst.getInt("co_bod")){
                            vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf") );
                        }else
                            vecReg.add(INT_TBL_IEBODFIS, "N" );

                     }else vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf") );


                       //  vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                         
                         double dblCosConDesc= ( rst.getDouble("nd_precom") - (rst.getDouble("nd_precom") * (rst.getDouble("nd_pordesprecom")/100) ));
                         
                         
                         vecReg.add(INT_TBL_PORDESORI, new Double(dblCosConDesc) );
                         
                         vecData.add(vecReg);    
                                     //  }
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);                                 
                     
                      calculaTotal();
                   
                    
                 /*
                 * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                 */
                
                  //  cboForPag.setSelectedIndex(0);
                    
                    if (!txtCodTipDoc.getText().equals("")){
                        cargaNum_Doc_OrdCom();                        
                        txtOrdCom.requestFocus();
                        txtOrdCom.setSelectionStart(0);
                        txtOrdCom.setSelectionEnd(txtOrdCom.getText().length());
                    }
                    
                    cargaListadoFormaPago();
                    objDiario.setEditable(true);
                   
                    generaAsiento();
                    
                   
                    
                    setEditable(true);
               
                      
                    stm.close();
                    rst.close();
                    
                }
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
    
    
    /**
     * Carga los datos de la orden de compra para generar los pagos
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
     
    public boolean refrescaDatosReserva(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot, int CodTipDocOC, int CodDocOC){
       boolean blnRes=false;
        try{ 
            int intNumDoc = 0;
            if (conExt!=null){  
                 String strAux = ",CASE WHEN (" +
                   " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
                   " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" " +
                   " AND co_tipdoc="+intTipDocFac+" AND co_usr=(SELECT co_usrIng from tbm_cabCotVen WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_cot="+CodCot+" )  AND st_reg='A' AND st_tipmov='C' " +
                   " ))) THEN 'S' ELSE 'N' END  as isterL";
                 
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+CodEmp+" and a.co_loc="+CodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
          
                 
                 
                  sSQL = " SELECT * FROM ( " +
                          " SELECT a.nd_pordesprecom,  a.co_reg, a.co_itm,inv.tx_codalt,a.tx_nomitm,  inv.tx_nomitm as nomitminv,  var.tx_descor, a.co_bod , a.co_bodcom, a.nd_can,a.nd_precom,inv.st_ivacom,inv.tx_codalt2, inv.st_ser as servicio ";
                  sSQL +=strAux;
                  sSQL +=strAux2;
                  sSQL +=" FROM tbm_detcotven as a " +
                " inner join tbm_inv as inv on (inv.co_emp=a.co_emp and inv.co_itm=a.co_itm)  " +
                " left outer join tbm_var as var on (inv.co_uni = var.co_reg)  " +
                " WHERE a.co_emp="+CodEmp+" and a.co_loc="+CodLoc+" and a.co_cot="+CodCot+"  and a.co_prv="+intCodPrv+" "+
                " ) AS x  WHERE  x.isterl='S'   ORDER BY x.co_reg";
                 
                  System.out.println("ZafCen01_OC refrescaDatosReserva: " + sSQL);
                    java.sql.Statement stm = conExt.createStatement();
                    java.sql.ResultSet rst= stm.executeQuery(sSQL);
                     double dblCan    =  0, dblPre    =  0, dblPorDes =  0, dblValDes =  0, dblTotal  =  0,dblIva = 0;
                      
                     java.util.Vector vecData = new java.util.Vector();
                     while (rst.next()){ 
                         System.out.println("entra...");
                         blnRes=true;
                        // double dblStkAct = getStkAct(rst.getString("co_itm"));
                         dblCan    = rst.getDouble("nd_can");
                         double dobCanmov = dblCan;  //Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_CANMOV)==null)?"":tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
                                      //dobCanmov = dblStkAct-dobCanmov;
                                       //if(dobCanmov < 0){
                                           dblCan = java.lang.Math.abs(dobCanmov);
                         java.util.Vector vecReg = new java.util.Vector();
                         vecReg.add(INT_TBL_LINEA, "");
                         vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
                         vecReg.add(INT_TBL_BUTITM, "");
                           
                         String strIva = rst.getString("st_ivacom");
                         if(strIva.equals("S"))
                          vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
                         else
                           vecReg.add(INT_TBL_DESITM, rst.getString("nomitminv"));
                         
                         vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_descor"));
                     
                         vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bodcom")));
                     
                         vecReg.add(INT_TBL_CANORI, new Double(dblCan));
                         vecReg.add(INT_TBL_CANMOV, new Double(dblCan));                                                                           
                         vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_precom")));
                         vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordesprecom")) );



                         Boolean blnIva = new Boolean(strIva.equals("S"));                         
                         vecReg.add(INT_TBL_BLNIVA, blnIva);
                          
                         dblPre    = rst.getDouble("nd_precom");
                         dblPorDes = rst.getDouble("nd_pordesprecom");
                         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                         dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;
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
                         vecReg.add(INT_TBL_PRECOSORI, new Double(rst.getDouble("nd_precom")));
                         vecReg.add(INT_TBL_ITMSER, rst.getString("servicio"));
                         vecReg.add(INT_TBL_NUMREL, rst.getString("co_reg"));

                         


                     if( rst.getString("co_bodcom")!=null  ){
                        if(rst.getInt("co_bodcom")==rst.getInt("co_bod")){
                            vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf") );
                        }else
                            vecReg.add(INT_TBL_IEBODFIS, "N" );
                     }else vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf") );


                       
                         
                         double dblCosConDesc= ( rst.getDouble("nd_precom") - (rst.getDouble("nd_precom") * (rst.getDouble("nd_pordesprecom")/100) ));
                         
                         
                         vecReg.add(INT_TBL_PORDESORI, new Double(dblCosConDesc) );
                         
                         vecData.add(vecReg);    
                                   
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);                                 
                     
                      calculaTotal();
                      
                   
                    
                 /*
                 * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                 */
                
                  //  cboForPag.setSelectedIndex(0);
                    
                    if (!txtCodTipDoc.getText().equals("")){
                        cargaNum_Doc_OrdCom();                        
                        txtOrdCom.requestFocus();
                        txtOrdCom.setSelectionStart(0);
                        txtOrdCom.setSelectionEnd(txtOrdCom.getText().length());
                    }
                    
                    cargaListadoFormaPagoOCReservada(conExt, CodEmp, CodLoc, CodTipDocOC, CodDocOC);
                    
                    
                    objDiario.setEditable(true);
                   
                    generaAsiento();
                    
                    
                    if(cargarPagos(conExt, CodEmp)){
                        calculaPagos();
                    }
                    
                    
                    setEditable(true);
               
                      
                    stm.close();
                    rst.close();
                    
                }
            blnHayCam = false; // Seteando que no se ha hecho cambios
            
   
        }//fin Try
       catch(SQLException Evt){
           blnRes=false;
           objUti.mostrarMsgErr_F1(jfrThis, Evt);
              
       }

        catch(Exception Evt){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }           
        return blnRes;
    }
    
    /**
     * JOTA made it
     * @param conExt
     * @param CodEmp
     * @return 
     */
    
    private boolean cargarPagos(java.sql.Connection conExt, int CodEmp){
        boolean blnRes=true;
        try{
            if(conExt!=null){
                String sSQL2 = "SELECT A1.ne_numPag, A2.ne_diaCre " +
                        " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_emp = " + CodEmp + 
                        "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex()) + " Order by A2.co_reg";

                String sSQL3 = "SELECT count(A2.ne_diaCre) as c  " +
                        " FROM tbm_cabForPag as A1 left outer join tbm_detForPag as A2  on (A2.co_emp = A1.co_emp and A2.co_forPag = A1.co_forPag ) " + // Tablas enlas cuales se trabajara y sus respectivos alias
                        " Where A1.co_emp = " + CodEmp +  
                        "       and A1.co_forPag = "  + vecForPag.get(cboForPag.getSelectedIndex());
                System.out.println("ZafVen01_OC  cargarPagos (1) :" + sSQL2);
                Statement stmDoc2   = conExt.createStatement();                                  
                ResultSet rstDocCab2= stmDoc2.executeQuery(sSQL3);
                rstDocCab2.next();
                intCanArr[0] = rstDocCab2.getInt(1);
                System.out.println("ZafVen01_OC  cargarPagos (2) :" + sSQL3);
                stmDoc2   = conExt.createStatement();                                  
                rstDocCab2= stmDoc2.executeQuery(sSQL2);
                int x=0;
                while(rstDocCab2.next()){
                    intarreglodia[x]=rstDocCab2.getInt(2);
                    intarreglonum[x]=rstDocCab2.getInt(1);
                    x++;  
                }
                rstDocCab2.close();
                rstDocCab2=null;
                stmDoc2.close();
                stmDoc2=null;
            }
        }
        catch(SQLException Evt){
           blnRes=false;
           objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }
        catch(Exception Evt){
            blnRes=false;
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }           
        return blnRes;
    }
    
    

public void refrescaDatosRep(){
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 String strAux="",strAux2="";
 double dblCan=0, dblPre=0, dblPorDes=0, dblValDes=0, dblTotal=0, dblIva=0;
 double dblCosConDesc=0;
 try{      
   if(CONN_GLO!=null){
       stm = CONN_GLO.createStatement();

       java.util.Vector vecData = new java.util.Vector();
       
       strAux = ",CASE WHEN (" +
       " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
       " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
       " AND co_tipdoc="+intTipDocFac+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='C' " +
       " ))) THEN 'S' ELSE 'N' END  as isterL";

       strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
       " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
       " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
       " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
       " THEN 'S' ELSE 'N' END AS proconf  ";

       sSQL="SELECT * "+strAux2+" FROM ( SELECT  inv.co_itm, inv.tx_codalt, inv.tx_nomitm, var.tx_descor, a2.co_bod as co_bod, a.nd_cancom, a.nd_preuni, a.nd_pordes, inv.st_ivacom " +
       ", inv.tx_codalt2, inv.st_ser as servicio  FROM tbm_procomitmrepinvprv as a " +
       "  inner join tbm_detrepinvprv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg) " +
       "   inner join tbm_cabrepinvprv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc )  "+
       " inner join tbm_inv as inv on (inv.co_emp=a1.co_emp and inv.co_itm=a1.co_itm) " +
       "  left outer join tbm_var as var on (inv.co_uni = var.co_reg)   " +
       "  where a.co_emp="+intCodEmpRep+" AND a.co_loc="+intCodLocRep+" and a.co_tipdoc="+intCodTipDocRep+" and a.co_doc="+intCodDocRep+"  " +
       " and a.nd_cancom > 0 and  a.nd_preuni > 0 and a.st_cotgen='S' and a.st_comgen='N' " +
       "  and a.co_prv="+intCodPrvRep+"  ) AS x ";

       System.out.println("ZafVen01_OC refrescaDatosRep --> "+ sSQL );

      rst= stm.executeQuery(sSQL);
      while(rst.next()){
               
        dblCan = rst.getDouble("nd_cancom");
       
        java.util.Vector vecReg = new java.util.Vector();
         vecReg.add(INT_TBL_LINEA, "");
         vecReg.add(INT_TBL_ITMALT, rst.getString("tx_codAlt"));
         vecReg.add(INT_TBL_BUTITM, "");
         vecReg.add(INT_TBL_DESITM, rst.getString("tx_nomitm"));
         vecReg.add(INT_TBL_UNIDAD, rst.getString("tx_descor"));
         vecReg.add(INT_TBL_CODBOD, new Integer(rst.getInt("co_bod")));
         vecReg.add(INT_TBL_CANORI, new Double(dblCan));
         vecReg.add(INT_TBL_CANMOV, new Double(dblCan));
         vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_preuni")));
         vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")) );
         vecReg.add(INT_TBL_BLNIVA, new Boolean((rst.getString("st_ivacom").equals("S")?true:false)) );

         dblPre    = rst.getDouble("nd_preuni");
         dblPorDes = rst.getDouble("nd_pordes");
         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
         dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;

         if(rst.getString("st_ivacom").equals("S")){
            dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objZafParSis.getDecimalesMostrar()) ;
         }

         vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
         vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
         vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
         vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
         vecReg.add(INT_TBL_ESTADO, "E");
         vecReg.add(INT_TBL_IVATXT, "");
         vecReg.add(INT_TBL_ITMALT2, rst.getString("tx_codAlt2"));
         vecReg.add(INT_TBL_PRECOSORI, new Double(rst.getDouble("nd_preuni")));
         vecReg.add(INT_TBL_ITMSER, rst.getString("servicio"));
         vecReg.add(INT_TBL_NUMREL, "");
         vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));

         dblCosConDesc= ( dblPre - ( dblPre * (dblPorDes/100) ));

         vecReg.add(INT_TBL_PORDESORI, new Double(dblCosConDesc) );
         vecData.add(vecReg);
        }
        rst.close();
        rst=null;

         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);

        calculaTotal();

       if (!txtCodTipDoc.getText().equals("")){
            cargaNum_Doc_OrdCom();
            txtOrdCom.requestFocus();
            txtOrdCom.setSelectionStart(0);
            txtOrdCom.setSelectionEnd(txtOrdCom.getText().length());
        }

        cargaListadoFormaPago();
        objDiario.setEditable(true);

        generaAsiento();
        setEditable(true);

       stm.close();
    

     }
    blnHayCam = false; // Seteando que no se ha hecho cambios

   }catch(SQLException Evt) {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
    catch(Exception Evt)  {   objUti.mostrarMsgErr_F1(jfrThis, Evt);    }
 }




public boolean insertarRep(){
    System.out.println("ZafVen01_OC: insertarRep");
 if (!validaCampos())
    return false;

 if(!insertarRegRep(CONN_GLO))
    return false;

 blnHayCam=false;
 return true;
}



    
    
    
      public boolean insertar()
        {
            if (!validaCampos())
                return false;
          
            if(!insertarReg(CONN_GLO))
                return false;
                                 
            // recostearItmGrp();
            blnHayCam=false;
            return true;
        }
 
 
          private int intNumDocOC;
 
private boolean insertarCab(java.sql.Connection conn, int intCodDoc, String strConfInv ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecha=""; 
  String strNomBen="";
  int intCodBen=0;
  int intSecGrp=0;
  int intSecEmp=0;
  try{
      stmLoc=conn.createStatement(); 
      strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
   
      intSecEmp = objUltDocPrint.getNumSecDoc(conn, objZafParSis.getCodigoEmpresa());
      intSecGrp = objUltDocPrint.getNumSecDoc(conn, objZafParSis.getCodigoEmpresaGrupo());
      
      Glo_intCodSec=intSecEmp;
      int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
      strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
      
      
      strSql="SELECT co_reg, tx_benchq FROM tbm_benchq  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
      " AND co_cli="+txtPrvCod.getText()+" and st_reg='P'";
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         intCodBen=rstLoc.getInt("co_reg");
         strNomBen=rstLoc.getString("tx_benchq");
      }
      
        strSql = "SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc FROM tbm_cabTipDoc WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipDoc=" + txtCodTipDoc.getText();
        rstLoc = stmLoc.executeQuery(strSql);
        if (rstLoc.next())
        {
            intNumDocOC = rstLoc.getInt("ultnum");
        }
        
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;

      String strEmiChq="";
      if(chkEmiChq.isSelected()) strEmiChq="S";

  
      stbDocRelEmp.append(" SELECT "+objZafParSis.getCodigoEmpresa()+" AS COEMP, "+objZafParSis.getCodigoLocal()+" AS COLOC , "+txtCodTipDoc.getText()+" AS COTIPDOC, "+intCodDoc+" AS CODOC " );

      strSql="INSERT INTO  tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
      " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, tx_numPed, "+
      " tx_obs1, tx_obs2, nd_sub , nd_tot, nd_valiva, nd_porIva, co_forPag, fe_ing, co_usrIng,fe_ultMod, " +
      " co_usrMod , tx_desforpag ,st_reg, co_forret,tx_vehret,tx_choret,ne_secgrp,ne_secemp , st_regrep , st_tipdev, st_coninv, co_ben, tx_benchq "
      + " , st_emiChqAntRecFacPrv , st_imp,tx_tipMov  ) "+
      " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+
      intCodDoc+",'"+strFecha+"', "+txtPrvCod.getText()+", "+txtComCod.getText()+", '"+txtAte.getText()+"','"+
      txtPrvNom.getText()+"','"+txtPrvDir.getText()+"', '"+txtcli_identificacion.getText()+"','"+txtcli_telefono.getText()+"','"+ 
      txtcli_ciudad.getText()+"','"+txtComNom.getText()+ "',"+txtOrdCom.getText()+","+((txtCot.getText().equals(""))?"0":txtCot.getText())+","+
      objUti.codificar(txtFacPrv.getText())+",'"+txaObs1.getText()+"','"+txaObs2.getText()+"',"+ 
      objUti.redondeo(Double.parseDouble(txtSub.getText()),6)+","+objUti.redondeo(Double.parseDouble(txtTot.getText()),6)+","+
      txtIva.getText()+", "+objUti.redondeo(dblPorIva , 2)+","+vecForPag.get(cboForPag.getSelectedIndex()).toString()+","+ 
      " '"+strFecSis+"', "+objZafParSis.getCodigoUsuario()+", '"+ strFecSis+ "',"+objZafParSis.getCodigoUsuario()+",'"+
      cboForPag.getSelectedItem()+"','R', "+txtCod.getText()+",'"+ txtVehRet.getText()+"','"+ txtChoRet.getText()+"', " +
      intSecGrp+","+intSecEmp+" ,'I','C','"+strConfInv+"', "+intCodBen+", '"+strNomBen+"'"
      + "  ,"+(strEmiChq.equals("")?null:"'"+strEmiChq+"'")+" ,'S','I'  ) ";  // ", "+(intSecGrp-1)+", "+(intSecEmp-1)+" " +
      txtsql.append(strSql + "; \n");

      
      strSql="INSERT INTO tbm_retmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_motdoc, st_regrep) " + 
      " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+", " +
      " 1, "+intCodMotBien+", 'I' )";
      txtsql.append(strSql + "; \n");
      
//    if(!blnIsSolTraInv){
//        strSql ="INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel,  co_locrel,co_tipdocrel,co_docrel)"+
//        " values("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+
//        intTipDocFac+","+intCodDocFac+",'A',"+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+
//        txtCodTipDoc.getText()+","+intCodDoc+")";
//        txtsql.append(strSql + "; \n");
//    }
      
      blnRes=true;
      
  }catch(Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}      



      
private boolean insertarCabRep(java.sql.Connection conn, int intCodDoc, String strConfInv ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecha="";
  String strNomBen="";
  int intCodBen=0;
  int intSecGrp=0;
  int intSecEmp=0;
  try{
      stmLoc=conn.createStatement();
      strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());

      Glo_intCodSec=intSecEmp;
      int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
      strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";


      strSql="SELECT co_reg, tx_benchq FROM tbm_benchq WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" " +
      " AND co_cli="+txtPrvCod.getText()+" and st_reg='P'";
      rstLoc=stmLoc.executeQuery(strSql);
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

      stbDocRelEmp.append(" SELECT "+objZafParSis.getCodigoEmpresa()+" AS COEMP, "+objZafParSis.getCodigoLocal()+" AS COLOC , "+txtCodTipDoc.getText()+" AS COTIPDOC, "+intCodDoc+" AS CODOC " );

      strSql="INSERT INTO  tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
      " tx_nomCli, tx_dirCli, tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, tx_numPed, "+
      " tx_obs1, tx_obs2, nd_sub , nd_tot, nd_valiva, nd_porIva, co_forPag, fe_ing, co_usrIng,fe_ultMod, " +
      " co_usrMod , tx_desforpag ,st_reg, co_forret,tx_vehret,tx_choret,ne_secgrp,ne_secemp , st_regrep , st_tipdev, st_coninv, co_ben, tx_benchq "
      + "  , st_emiChqAntRecFacPrv , st_imp  ) "+
      " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+
      intCodDoc+",'"+strFecha+"', "+txtPrvCod.getText()+", "+txtComCod.getText()+", '"+txtAte.getText()+"','"+
      txtPrvNom.getText()+"','"+txtPrvDir.getText()+"', '"+txtcli_identificacion.getText()+"','"+txtcli_telefono.getText()+"','"+
      txtcli_ciudad.getText()+"','"+txtComNom.getText()+ "',"+txtOrdCom.getText()+","+((txtCot.getText().equals(""))?"0":txtCot.getText())+","+
      objUti.codificar(txtFacPrv.getText())+",'"+txaObs1.getText()+"','"+txaObs2.getText()+"',"+
      objUti.redondeo(Double.parseDouble(txtSub.getText()),6)+","+objUti.redondeo(Double.parseDouble(txtTot.getText()),6)+","+
      txtIva.getText()+", "+objUti.redondeo(dblPorIva , 2)+","+vecForPag.get(cboForPag.getSelectedIndex()).toString()+","+
      " '"+strFecSis+"', "+objZafParSis.getCodigoUsuario()+", '"+ strFecSis+ "',"+objZafParSis.getCodigoUsuario()+",'"+
      cboForPag.getSelectedItem()+"','R', "+txtCod.getText()+",'"+ txtVehRet.getText()+"','"+ txtChoRet.getText()+"', " +
      intSecGrp+","+intSecEmp+" ,'I','C','"+strConfInv+"', "+intCodBen+", '"+strNomBen+"'"
      + " ,"+(strEmiChq.equals("")?null:"'"+strEmiChq+"'")+",'S'   )";  // ", "+(intSecGrp-1)+", "+(intSecEmp-1)+" " +
      txtsql.append(strSql + "; \n");

      
      strSql="INSERT INTO tbm_retmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_motdoc, st_regrep) " +
      " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", "+intCodDoc+", " +
      " 1, "+intCodMotBien+", 'I' )";
      txtsql.append(strSql + "; \n");
   
      blnRes=true;

  }catch(Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}


        private int intBodGrp(int intCodEmp,int intCodBodEmp){
            int intCodBodGrp=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT co_empGrp, co_bodGrp  ";
                    strCadena+="  FROM tbr_bodEmpBodGrp  ";
                    strCadena+=" WHERE co_bod="+ intCodBodEmp ;
                    strCadena+=" AND co_emp="+intCodEmp +" AND co_empGrp="+objZafParSis.getCodigoEmpresaGrupo();
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                            intCodBodGrp=rstLoc.getInt("co_bodGrp");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodBodGrp=0;
            }
            return intCodBodGrp;
        }
  
private boolean insertarDet(int intCodDoc, String strMerIngEgr ){
  boolean blnRes=false, blnIsInmaconsaOD=false;
  String strSql="";
   
  String str_MerIEFisBod="A";
  double dblCanPen=0.00;
  int intControl=0;
  boolean blnIsSer=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    java.sql.Connection conLoc;
  try{
        conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        if(conLoc!=null){
            stmLoc=conLoc.createStatement();
            GLO_strArreItm="";
            for(int i=0; i<tblDat.getRowCount(); i++){  
               if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){  
                   if(intControl!=0)    
                     GLO_strArreItm=GLO_strArreItm+",";
                   GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
                   intControl++;
 
                   String strCadena="";
                   strCadena+=" SELECT st_ser ";
                   strCadena+=" FROM tbm_inv ";
                   strCadena+=" WHERE co_emp="+objZafParSis.getCodigoEmpresa() + " AND co_itm="+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
                   rstLoc=stmLoc.executeQuery(strCadena);
                   if(rstLoc.next()){
                       blnIsSer=rstLoc.getString("st_ser").equals("S")?true:false;
                   }
                   rstLoc.close();
                   rstLoc=null;
                   
//                   /***********   VERFIFICA SI EL ITEM NO ES DE SERVICIO  ************************/
//                    if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){
//                         if(strEstFisBod.equals("N")){
//                           str_MerIEFisBod="S";
//                        }
//                    }
//                    
                    
                    if(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString())==15){
                        String strTerminal=tblDat.getValueAt(i, INT_TBL_ITMALT).toString().substring(tblDat.getValueAt(i, INT_TBL_ITMALT).toString().length()-1);
                        int intBodGrp = intBodGrp(objZafParSis.getCodigoEmpresa(),Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                        String strCfgCon = objUti.getCfgConfirma(jfrThis, objZafParSis,  objZafParSis.getCodigoEmpresaGrupo(), intBodGrp, strTerminal);
                        if(strCfgCon.equals("S") && blnIsSer==false){
                            str_MerIEFisBod="S";
                            dblCanPen=Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANMOV).toString());
                            blnIsInmaconsaOD=true;
                        }
                        else{
                            dblCanPen=0.0; 
                        }
                    }



                   /************************************************************************/
                    /* JM 24/Mayo/2017 */
                    
                    strSql="INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_unimed, co_itm, co_itmact, ";
                    strSql+=" tx_codAlt,tx_codAlt2, tx_nomItm, co_bod, nd_can, nd_cosUni, nd_cosUniGrp , nd_preuni, nd_porDes, st_ivaCom  ";
                    strSql+=",nd_costot, nd_costotgrp, nd_tot , st_regrep, nd_cancon, st_meringegrfisbod, nd_preunivenlis, nd_pordesvenmax";
                    strSql+=",nd_canIngBod,nd_canPen )";
                    strSql+=" VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+",";
                    strSql+=intCodDoc+", "+(i+1)+", '"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"', "+tblDat.getValueAt(i, INT_TBL_CODITM)+", " ;
                    strSql+=  tblDat.getValueAt(i, INT_TBL_CODITM)+", '"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"', '"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"', ";
                    strSql+=  " "+objUti.codificar(tblDat.getValueAt(i, INT_TBL_DESITM))+", "+tblDat.getValueAt(i, INT_TBL_CODBOD)+", "+tblDat.getValueAt(i, INT_TBL_CANMOV)+", " ;
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+",";
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", ";
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+",";
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objZafParSis.getDecimalesMostrar())+",'";
                    if(stIvaCom.equals("N")){
                        strSql+="N',";
                    }
                    else{
                        strSql+=  ((tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)?"N":(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"' , ";
                    }
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+", ";
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+",";
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+",'I',";
                    strSql+=  " 0, '"+str_MerIEFisBod+"', ";
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+" , ";
                    strSql+=  objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objZafParSis.getDecimalesMostrar())+" ";
                    strSql+=  " , "+dblCanPen+", " +dblCanPen+"); ";
                    /************************************************************************/
                  txtsql.append(strSql + "; \n");
                  System.out.println("ZafVen01_OC: insertarDet: " + strSql);
               }
            }
            stmLoc.close();
            stmLoc=null;

            if(blnIsInmaconsaOD){
                strSql=" UPDATE tbm_cabMovInv SET st_conInv='P' WHERE co_emp="+ objZafParSis.getCodigoEmpresa()+" AND ";
                strSql+=" co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText()+" " ;
                strSql+=" AND co_doc="+intCodDoc+"; "; 
                System.out.println("ZafVen01_OC: UPDATE CABECERA ES INMACONSA : " + strSql);
                txtsql.append(strSql + "; \n");
            }
            blnRes=true;        
        }else{blnRes=false;}
        
     conLoc.close();
     conLoc=null;


    
  }
  catch(Exception evt){ 
      blnRes=false; 
      objUti.mostrarMsgErr_F1(this, evt); 
  }
   return blnRes;
}      
      
private boolean insertarDetRep(int intCodDoc, String strMerIngEgr ){
  boolean blnRes=false;
  String strSql="";
  String str_MerIEFisBod="S";
  String strEstFisBod="";
  int intControl=0;
  try{
      GLO_strArreItm="";

   for(int i=0; i<tblDat.getRowCount(); i++){
    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
      if(intControl!=0)
        GLO_strArreItm=GLO_strArreItm+",";
      GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
     intControl++;

      strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());
      str_MerIEFisBod="N";
      /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
       if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){
          if(strEstFisBod.equals("N")){
            if(strMerIngEgr.equals("S"))
             str_MerIEFisBod="S";
       }}
      /************************************************************************/
     strSql="INSERT INTO tbm_detMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_unimed, co_itm, co_itmact, " +
     " tx_codAlt,tx_codAlt2, tx_nomItm, co_bod, nd_can, nd_cosUni, nd_cosUniGrp , nd_preuni, nd_porDes, st_ivaCom ,st_reg "+
     ",nd_costot, nd_costotgrp, nd_tot , st_regrep, nd_cancon, st_meringegrfisbod, nd_preunivenlis, nd_pordesvenmax )" +
     " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+"," +
     intCodDoc+", "+(i+1)+", '"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"', "+tblDat.getValueAt(i, INT_TBL_CODITM)+", " +
     tblDat.getValueAt(i, INT_TBL_CODITM)+", '"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"', '"+tblDat.getValueAt(i, INT_TBL_ITMALT2)+"', " +
     " '"+tblDat.getValueAt(i, INT_TBL_DESITM)+"', "+tblDat.getValueAt(i, INT_TBL_CODBOD)+", "+tblDat.getValueAt(i, INT_TBL_CANMOV)+", " +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+"," +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+"," +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objZafParSis.getDecimalesMostrar())+",'" +
     ((tblDat.getValueAt(i, INT_TBL_BLNIVA)==null)?"N":(tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N")+"','S', " +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+"," +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i, INT_TBL_TOTAL).toString()),objZafParSis.getDecimalesBaseDatos())+",'I',"+
     " 0, '"+str_MerIEFisBod+"', " +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+" , " +
     objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i, INT_TBL_PORDES).toString()),objZafParSis.getDecimalesMostrar())+" " +
     " ) ";
     
     txtsql.append(strSql + "; \n");

  }}
  blnRes=true;
  }catch(Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
   return blnRes;
}










    private boolean insertarPag(java.sql.Connection conn, int intCodDoc){
     boolean blnRes=false;
     java.sql.Statement stmLoc;
     String strSql="", strApliRet="";
     String strFechaPag="";
     try{
        if(conn!=null){
            for(int i=0; i<tblOrdComForPag.getRowCount();i++){                       
               int FecPagDoc[] =  txtFecDoc.getFecha(tblOrdComForPag.getValueAt(i, 2).toString());
               strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];

               strApliRet=(tblOrdComForPag.getValueAt(i, 8)==null)?"":tblOrdComForPag.getValueAt(i, 8).toString(); 

               strSql="INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diaCre, fe_ven, "+
               " nd_porRet, mo_pag, ne_diaGra ,st_regrep , co_tipret, tx_codsri, tx_aplret, nd_basimp )"+
               " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", " +
               intCodDoc+", "+(i+1)+", "+(tblOrdComForPag.getValueAt(i, 1).equals("")?"0":tblOrdComForPag.getValueAt(i, 1))+"," +
               "'"+strFechaPag+"', "+((tblOrdComForPag.getValueAt(i, 3)==null)?"0":tblOrdComForPag.getValueAt(i, 3))+","+
               objUti.redondeo(Double.parseDouble( (tblOrdComForPag.getValueAt(i, 4)==null)?"0":tblOrdComForPag.getValueAt(i, 4).toString() ),6)+","+
               " "+((tblOrdComForPag.getValueAt(i, 5)==null)?"0":tblOrdComForPag.getValueAt(i, 5).toString())+", 'I', "+
               ((tblOrdComForPag.getValueAt(i, 6)==null)?"0":tblOrdComForPag.getValueAt(i, 6).toString())+", "+
               " '"+((tblOrdComForPag.getValueAt(i, 7)==null)?"":tblOrdComForPag.getValueAt(i, 7).toString())+ "', " +
               " '"+((tblOrdComForPag.getValueAt(i, 8)==null)?"":tblOrdComForPag.getValueAt(i, 8).toString())+ "', " +
              // (strApliRet.equals("S")?txtSub.getText():(strApliRet.equals("I")?txtIva.getText():"0") )+" )";
               ((tblOrdComForPag.getValueAt(i, 9)==null)?"0":tblOrdComForPag.getValueAt(i, 9).toString())+"  )";
               txtsql.append(strSql + "; \n");
            }
            stmLoc=conn.createStatement();
            stmLoc.executeUpdate(txtsql.getText());
            stmLoc.close();
            stmLoc=null;
            blnRes= objDiario.insertarDiario_OC(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), String.valueOf(intCodDoc), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), 45  );
        }
        }catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(jfrThis, Evt); 
        }   
        return blnRes;
    }
            

private boolean insertarPagRep(java.sql.Connection conn, java.sql.Connection connRemota, int intCodDoc){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="", strApliRet="";
 String strFechaPag="";
 try{
    if(conn!=null){


    for(int i=0; i<tblOrdComForPag.getRowCount();i++){
       int FecPagDoc[] =  txtFecDoc.getFecha(tblOrdComForPag.getValueAt(i, 2).toString());
       strFechaPag = FecPagDoc[2] + "/"+FecPagDoc[1] + "/" + FecPagDoc[0];

       strApliRet=(tblOrdComForPag.getValueAt(i, 8)==null)?"":tblOrdComForPag.getValueAt(i, 8).toString();

       strSql="INSERT INTO tbm_pagMovInv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diaCre, fe_ven, "+
       " nd_porRet, mo_pag, ne_diaGra ,st_regrep , co_tipret, tx_codsri, tx_aplret, nd_basimp )"+
       " VALUES("+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+", "+txtCodTipDoc.getText()+", " +
       intCodDoc+", "+(i+1)+", "+(tblOrdComForPag.getValueAt(i, 1).equals("")?"0":tblOrdComForPag.getValueAt(i, 1))+"," +
       "'"+strFechaPag+"', "+((tblOrdComForPag.getValueAt(i, 3)==null)?"0":tblOrdComForPag.getValueAt(i, 3))+","+
       objUti.redondeo(Double.parseDouble( (tblOrdComForPag.getValueAt(i, 4)==null)?"0":tblOrdComForPag.getValueAt(i, 4).toString() ),6)+","+
       " "+((tblOrdComForPag.getValueAt(i, 5)==null)?"0":tblOrdComForPag.getValueAt(i, 5).toString())+", 'I', "+
       ((tblOrdComForPag.getValueAt(i, 6)==null)?"0":tblOrdComForPag.getValueAt(i, 6).toString())+", "+
       " '"+((tblOrdComForPag.getValueAt(i, 7)==null)?"":tblOrdComForPag.getValueAt(i, 7).toString())+ "', " +
       " '"+((tblOrdComForPag.getValueAt(i, 8)==null)?"":tblOrdComForPag.getValueAt(i, 8).toString())+ "', " +
      // (strApliRet.equals("S")?txtSub.getText():(strApliRet.equals("I")?txtIva.getText():"0") )+" )";
       ((tblOrdComForPag.getValueAt(i, 9)==null)?"0":tblOrdComForPag.getValueAt(i, 9).toString())+"  )";
       txtsql.append(strSql + "; \n");


    }


     if(connRemota!=null){
          stmLoc=connRemota.createStatement();
          stmLoc.executeUpdate(txtsql.getText());
          stmLoc.close();
          stmLoc=null;
     }else{
          stmLoc=conn.createStatement();
          stmLoc.executeUpdate(txtsql.getText());
          stmLoc.close();
          stmLoc=null;
     }


    blnRes=true;
 }}catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;
}

 
     /**
     * Función que permite obtener el nombre del campo que se desea actualizar
     * @param indiceNombreCampo 
     *          <HTML>
     *              <BR>  0: Actualiza en campo "nd_stkAct"
     *              <BR>  1: Actualiza en campo "nd_canPerIng"
     *              <BR>  2: Actualiza en campo "nd_canPerEgr"
     *              <BR>  3: Actualiza en campo "nd_canBodIng"
     *              <BR>  4: Actualiza en campo "nd_canBodEgr"
     *              <BR>  5: Actualiza en campo "nd_canDesIng"
     *              <BR>  6: Actualiza en campo "nd_canDesEgr"
     *              <BR>  7: Actualiza en campo "nd_canTra"
     *              <BR>  8: Actualiza en campo "nd_canRev"
     *              <BR>  9: Actualiza en campo "nd_canRes"
     *              <BR> 10: Actualiza en campo "nd_canDis"
     *          </HTML>
     * @return true: Si se pudo obtener el nombre del campo
     * <BR> false: Caso contrario
     */
    final int INT_ARL_STK_INV_STK_ACT=0;  // nd_stkAct
    final int INT_ARL_STK_INV_NOM_CAM_ACT=1;
    final int INT_ARL_STK_INV_NOM_CAM_ACT_2=2;
    final int INT_ARL_STK_INV_CAN_ING_BOD=3;  // nd_canBodIng --> transferencia afectar ingreso 
    final int INT_ARL_STK_INV_CAN_EGR_BOD=4;  // nd_canBodEgr --> transferencia afectar egreso
    final int INT_ARL_STK_INV_CAN_DES_ENT_BOD=5;
    final int INT_ARL_STK_INV_CAN_DES_ENT_CLI=6;
    final int INT_ARL_STK_INV_CAN_TRA=7;
    final int INT_ARL_STK_INV_CAN_REV=8;
    final int INT_ARL_STK_INV_CAN_RES=9;
    final int INT_ARL_STK_INV_CAN_DIS=10;  // nd_canDis
    final int INT_ARL_STK_INV_CAN_RES_VEN=11; // Cantidad en reserva de venta 


    private boolean actualizaStock(java.sql.Connection conn, String strMerIngEgr ){
      boolean blnRes=false;
      String str_MerIEFisBod="I";
      String strEstFisBod="";
      int intTipCli=3;
      int  intCodItm =0;
      int intCodBod=0;
      int intTipStk=0;
      double dlbCanMov=0.00;
      try{
          System.out.println("actualizarStock.. ");
          intTipCli = objUltDocPrint.ValidarCodigoCliente(txtPrvCod.getText(), conn);
          objInvItm.inicializaObjeto();

         for(int i=0; i<tblDat.getRowCount(); i++){  
            if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){  

                strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
               /***********   VERFIFICA SI EL ITEM NO ES DE SERVICIO  ************************/
               // if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ 

                   intCodItm=Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString());
                   intCodBod=Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString());
                   dlbCanMov=objUti.redondear( objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANMOV)), 6);
                   arlDatStkInvItm = new ArrayList(); 
                    if(generaNuevoContenedorItemsMovimientoStock(intCodItm,dlbCanMov,intCodBod)){                           
                         if(objStkInv.actualizaInventario(conn, objZafParSis.getCodigoEmpresa(),INT_ARL_STK_INV_STK_ACT, "+", 1, arlDatStkInvItm)){
                             if(intCodBod==15){
                                 if(objStkInv.actualizaInventario(conn, objZafParSis.getCodigoEmpresa(),INT_ARL_STK_INV_CAN_ING_BOD, "+", 0, arlDatStkInvItm)){
                                     System.out.println("OC INMACONSA >> ZafVen01_OC.Mover Inventario ZafStkInv....  ");
                                     blnRes=true;
                                 }else{blnRes=false;}
                             }else{
                                 System.out.println("OC >> ZafVen01_OC.Mover Inventario ZafStkInv....  ");
                                 blnRes=true;
                             }
                             arlDatStkInvItm.clear();
                         }else{blnRes=false;}
                     }else{blnRes=false;}
            }
        }
          objInvItm.limpiarObjeto();           
      }
      catch(Exception Evt)   { 
           blnRes=false; 
           objUti.mostrarMsgErr_F1(jfrThis, Evt);     
      }
    return blnRes;
    }        
        
        
    /* NUEVO CONTENEDOR PARA ITEMS ZafStkInv MovimientoStock */

        private static final int INT_STK_INV_COD_ITM_GRP=0;
        private static final int INT_STK_INV_COD_ITM_EMP=1;
        private static final int INT_STK_INV_COD_ITM_MAE=2;    
        private static final int INT_STK_INV_COD_LET_ITM=3;     
        private static final int INT_STK_INV_CAN_ITM=4;
        private static final int INT_STK_INV_COD_BOD_EMP=5; 
        private ArrayList arlRegStkInvItm, arlDatStkInvItm;
    
        private boolean generaNuevoContenedorItemsMovimientoStock(int intCodItm, double dlbCanMov,int intCodBod){
        boolean blnRes=true;
        double dblAux;
        int intCodigoItemGrupo=0, intCodigoItemMaestro=0;
        String strCodTresLetras="";
        try{
            intCodigoItemGrupo=getCodigoItemGrupo(objZafParSis.getCodigoEmpresa(),intCodItm);
            intCodigoItemMaestro=getCodigoMaestroItemGrupo(objZafParSis.getCodigoEmpresa(),intCodItm);
            strCodTresLetras=getCodigoLetraItem(objZafParSis.getCodigoEmpresa(),intCodItm);
            if(intCodigoItemGrupo==0 || intCodigoItemMaestro==0 || strCodTresLetras.equals("")){
                blnRes=false;
            }
            
            arlRegStkInvItm = new ArrayList();
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_GRP,intCodigoItemGrupo);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_EMP,intCodItm);
            arlRegStkInvItm.add(INT_STK_INV_COD_ITM_MAE,intCodigoItemMaestro);
            arlRegStkInvItm.add(INT_STK_INV_COD_LET_ITM, strCodTresLetras);
            dblAux=dlbCanMov;
            if(dblAux<0){
                dblAux=dblAux*-1;
            }
            arlRegStkInvItm.add(INT_STK_INV_CAN_ITM,dblAux );
            arlRegStkInvItm.add(INT_STK_INV_COD_BOD_EMP,intCodBod );
            arlDatStkInvItm.add(arlRegStkInvItm);
            
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    } 
        
         private String getCodigoLetraItem(int intCodEmp, int intCodItm){
            String strCodLetItm="";
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2 \n";
                    strCadena+=" FROM tbm_inv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        strCodLetItm=rstLoc.getString("tx_codAlt2");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                strCodLetItm="";
            }
            return strCodLetItm;
        }
        
        private int getCodigoItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmGru=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT co_itm \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_itmMae = ( \n";
                    strCadena+="                        select co_itmMae  \n";
                    strCadena+="                        from tbm_Equinv as a1 \n";
                    strCadena+="                        where co_emp="+intCodEmp+" and co_itm="+intCodItm+")  \n";
                    strCadena+=" and x1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        intCodItmGru=rstLoc.getInt("co_itm");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodItmGru=0;
            }
            return intCodItmGru;
        }
        
        
        private int getCodigoMaestroItemGrupo(int intCodEmp, int intCodItm){
            int intCodItmMae=0;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLoc;
            String strCadena;
            try{
                conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conLoc != null) {
                    stmLoc=conLoc.createStatement();
                    strCadena="";
                    strCadena+=" SELECT x1.co_itmMae \n";
                    strCadena+=" FROM tbm_equInv as x1 \n";
                    strCadena+=" WHERE x1.co_emp="+intCodEmp+" and x1.co_itm="+intCodItm+" \n";
                    rstLoc=stmLoc.executeQuery(strCadena);
                    if(rstLoc.next()){
                        intCodItmMae=rstLoc.getInt("co_itmMae");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                intCodItmMae=0;
            }
            return intCodItmMae;
        }

  
/**
 * Inserta la orden de Compra
 * 
 * JM
 * JotaOC
 * @param conn
 * @param connRemota
 * @return 
 */

public boolean blnIsGenOC=false;

private boolean insertarReg(java.sql.Connection conn ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strMerIngEgr="",strTipIngEgr="";
  String strConfInv="F";
  int intCodDoc=0;
  try{
     System.out.println("ZafVen01_OC: insertarReg");
     if(conn!=null){
        stmLoc=conn.createStatement();
        txtsql.setText("");
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();
        strTipIngEgr=objTipDoc.gettx_natdoc();
        objRptSisCom = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objZafParSis);
 
        intCodDoc=objUltDocPrint.getCodigoDocumento(conn, objTipDoc.getco_tipdoc());
         
        if(Integer.parseInt(tblDat.getValueAt(0, INT_TBL_CODBOD).toString())==15){
            strConfInv="P";
        }
        else{
            strConfInv=_getEstConf();
        }
        
        setRutaReporteAutomaticos();                                        //Se Asigna las Rutas de los Reportes a Usarse.       
        
        if(insertarCab(conn,  intCodDoc, strConfInv )){    
         if(insertarDet(intCodDoc, strMerIngEgr )){    
          if(insertarPag(conn,  intCodDoc )){
          // if(objDiario.insertarDiario_OC(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), String.valueOf(intCodDoc), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), 45  ) ){
            if(actualizaStock(conn, strMerIngEgr )){
               if(asignaNumeroOC(conn,intCodDoc)){/*JoseMario 10/Mayo/2016*/
                    if(insertarSegTra(conn,intCodDoc)){
                        if(objUti.costearDocumento(jfrThis, objZafParSis, conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc)){
                            blnIsGenOC=true;
                            System.out.println("OK OC");
                            String strMsg = "Imprima\n la orden de compra numero : " + intNumDocOC;
                            this.mostrarMsg(strMsg);
                            imprimeOrdCom(conn, objZafParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(), intCodDoc);

                            blnRes=true;
                        }else{ blnRes=false;   }
                        
                    }else blnRes=false;   
                   txtsql.setText(""); 
                }else blnRes=false;   
            }else blnRes=false;   
          // }else blnRes=false;
          }else blnRes=false;  
         }else blnRes=false;
        }else blnRes=false; 
        
        objInvItm.limpiarObjeto();
        stmLoc.close();
        stmLoc=null;
  }}catch(SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
    catch(Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}



    
    
    public boolean insertarRegPagos(java.sql.Connection conn, int CodDoc){
      boolean blnRes=false;
      java.sql.Statement stmLoc;
      String strMerIngEgr="",strTipIngEgr="";
      String strConfInv="F";
      int intCodDoc=-1;
      String strCorEleTo,strTitCorEle="Zafiro: Sistema de Reserva de Mercaderias.";
      try{
          System.out.println(">>> ZafVen01_OC: insertarRegPagos <<<< ");
         if(conn!=null){
            stmLoc=conn.createStatement();
            txtsql.setText("");
            strMerIngEgr=objTipDoc.getst_meringegrfisbod();
            strTipIngEgr=objTipDoc.gettx_natdoc();
            intCodDoc=CodDoc;
              
             if(Integer.parseInt(tblDat.getValueAt(0, INT_TBL_CODBOD).toString())==15){
                 strConfInv="P";
             }
             else{
                 strConfInv=_getEstConf();
             }
            if(insertarPag(conn,  intCodDoc )){
                if(actualizaStock(conn,  strMerIngEgr )){
                    if(asignaNumeroOC(conn,intCodDoc)){/*JoseMario 10/Mayo/2016*/
                        if(insertarSegTra(conn,intCodDoc)){
                            if(objUti.costearDocumento(jfrThis, objZafParSis, conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoEmpresa(), Integer.parseInt(txtCodTipDoc.getText()), intCodDoc)){
                                 blnIsGenOC=true;
                                System.out.println("OK OC");
                                blnRes=true;
                            }else {blnRes=false;}
                        }else blnRes=false;   
                        txtsql.setText(""); 
                    }else blnRes=false;   
                }else blnRes=false;   
            }else blnRes=false;  
              

            objInvItm.limpiarObjeto();
            stmLoc.close();
            stmLoc=null;
      }}catch(SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
        catch(Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
      return blnRes;
    }



        
        /* Modificado Rose 27/May/2015 -- Traido por Jota 26/Oct/2016
         * 
         * IMPRESION DE ORDEN DE COMPRA LINUX/WINDOWS
         */

    private ZafRptSis objRptSisCom;                   //Reportes del Sistema.
    private final int intCodMnuOrdCom = 45;
    private String DIRECCION_REPORTE_COMPRA = "";                               //Direccion de Reporte de Ordenes de Compra.
    private String strImpDirectaOC = "compras";                                   //Nombre de la Impresora de Ordenes de Compra.
    private final int intTipRptOrdCom = 1;
        
        public boolean imprimeOrdCom(Connection conIns, int codLoc, int codTipDoc, int codDoc) 
        {
            int intCodEmp = objZafParSis.getCodigoEmpresa();  
            int intNumTotRpt;
            String strRutImgLogo = " ";
            boolean blnIsLinux;
            blnIsLinux = System.getProperty("os.name").equals("Linux") ? true : false; //Conocer el tipo de S.O
            try 
            {
                if (blnIsLinux) 
                {
                    if (conIns != null) 
                    {
                        objRptSisCom.cargarListadoReportes(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodMnuOrdCom, objZafParSis.getCodigoUsuario());
                        objRptSisCom.setVisible(true);
                        if (objRptSisCom.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) 
                        {
                            intNumTotRpt = objRptSisCom.getNumeroTotalReportes();
                            for (int i = 0; i < intNumTotRpt; i++) 
                            {
                                if (objRptSisCom.isReporteSeleccionado(i))
                                {
                                    switch (Integer.parseInt(objRptSisCom.getCodigoReporte(i))) 
                                    {
                                        case 19:
                                        default:
                                            strRutImgLogo = objRptSisCom.getRutaReporte(i);
                                            if (objZafParSis.getCodigoEmpresa() == 1) {
                                                strRutImgLogo += "Logos/logTuv.png";
                                            } else if (objZafParSis.getCodigoEmpresa() == 2) {
                                                strRutImgLogo += "Logos/logCas.png";
                                            } else {
                                                strRutImgLogo += "Logos/logDim.png";
                                            }
                                            Map parameters = new HashMap();
                                            parameters.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()));
                                            parameters.put("codLoc", new Integer(objZafParSis.getCodigoLocal()));
                                            parameters.put("CodTipDoc", new Integer(codTipDoc));
                                            parameters.put("codDoc", new Integer(codDoc));
                                            parameters.put("RUTA_LOGO", strRutImgLogo); //Rose

                                            if ((intCodEmp == 1) || (intCodEmp == 4) || ((intCodEmp == 2) && objZafParSis.getCodigoLocal() != 6)) {
                                                if (System.getProperty("os.name").equals("Linux")) {
                                                    javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                                                    objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                                                    JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_COMPRA, parameters, conIns);
                                                    javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strImpDirectaOC, null);
                                                    javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                                                    printServiceAttributeSet.add(printerName);
                                                    net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                                                    objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                                                    objJRPSerExp.exportReport();
                                                } else {
                                                    JasperPrint report = JasperFillManager.fillReport(DIRECCION_REPORTE_COMPRA, parameters, conIns);
                                                    JasperPrintManager.printReport(report, true);
                                                }
                                            } 
                                            else 
                                            {
                                                JasperPrint report = JasperFillManager.fillReport(DIRECCION_REPORTE_COMPRA, parameters, conIns);
                                                JasperPrintManager.printReport(report, true);
                                            }
                                            break;
                                    }
                                }
                            }
                        }
                    }
                } 
                else //Windows 
                {
                    impresionOrdenCompra(intTipRptOrdCom, codTipDoc, codDoc);
                }
            } catch (JRException e) {
                objUti.mostrarMsgErr_F1(jfrThis, e);
            }
            return true;
        }
        
        private void setRutaReporteAutomaticos() //Rose
        {
            if (objZafParSis.getCodigoEmpresa() == 1) // Tuval
            {
                DIRECCION_REPORTE_COMPRA        = "//Zafiro/Reportes/Compras/ZafCom02/ZafRptCom02.jasper";
            } 
            else if ((objZafParSis.getCodigoEmpresa() == 2) && (objZafParSis.getCodigoLocal() == 1)) // Castek - Quito
            {
                DIRECCION_REPORTE_COMPRA        = "//Zafiro/Reportes/Compras/ZafCom02/ZafRptCom02.jasper";
            } 
            else if ((objZafParSis.getCodigoEmpresa() == 2) && (objZafParSis.getCodigoLocal() == 4)) // Castek - Manta
            {
                DIRECCION_REPORTE_COMPRA        = "//Zafiro/Reportes/Compras/ZafCom02/ZafRptCom02.jasper";
            } 
            else if ((objZafParSis.getCodigoEmpresa() == 2) && (objZafParSis.getCodigoLocal() == 6)) // Castek - Sto Domingo
            {
                DIRECCION_REPORTE_COMPRA        = "//Zafiro/Reportes/Compras/ZafCom02/ZafRptCom02.jasper";
            } 
            else if ((objZafParSis.getCodigoEmpresa() == 2) && (objZafParSis.getCodigoLocal() == 10)) // Castek - Cuenca: JoséMario Marín M. 3/Oct/2014
            {
                DIRECCION_REPORTE_COMPRA        = "//Zafiro/Reportes/Compras/ZafCom02/ZafRptCom02.jasper";
            }
            if (objZafParSis.getCodigoEmpresa() == 4) 
            {
                DIRECCION_REPORTE_COMPRA        = "//Zafiro/Reportes/Compras/ZafCom02/ZafRptCom02.jasper";
            }
        }

        
        public boolean impresionOrdenCompra(int intTipRpt, int codtipdoc, int codoc) 
        {
            String strRutRpt, strNomRpt, strRutImgLogo;
            Connection conIns = CONN_GLO;
            int i, intNumTotRpt;
            boolean blnRes = true, blnLstRep = false;
            try 
            {
                blnLstRep = existeRep(conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodMnuOrdCom, objZafParSis.getCodigoUsuario());
                if (blnLstRep) 
                {
                    objRptSisCom.cargarListadoReportes(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), intCodMnuOrdCom, objZafParSis.getCodigoUsuario());
                    objRptSisCom.setVisible(true);
                    if (objRptSisCom.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) 
                    {
                        intNumTotRpt = objRptSisCom.getNumeroTotalReportes();
                        for (i = 0; i < intNumTotRpt; i++) 
                        {
                            if (objRptSisCom.isReporteSeleccionado(i)) 
                            {
                                switch (Integer.parseInt(objRptSisCom.getCodigoReporte(i))) 
                                {
                                    case 19:
                                    default:
                                        strRutRpt = objRptSisCom.getRutaReporte(i);
                                        strNomRpt = objRptSisCom.getNombreReporte(i);

                                        strRutImgLogo = objRptSisCom.getRutaReporte(i);
                                        if (objZafParSis.getCodigoEmpresa() == 1) {
                                            strRutImgLogo += "Logos/logTuv.png";
                                        } else if (objZafParSis.getCodigoEmpresa() == 2) {
                                            strRutImgLogo += "Logos/logCas.png";
                                        } else {
                                            strRutImgLogo += "Logos/logDim.png";
                                        }

                                        //Inicializar los parametros que se van a pasar al reporte.
                                        Map mapPar = new HashMap();
                                        mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()));
                                        mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()));
                                        mapPar.put("CodTipDoc", new Integer(codtipdoc));
                                        mapPar.put("codDoc", new Integer(codoc));
                                        mapPar.put("RUTA_LOGO", strRutImgLogo);     //Rose

                                        objRptSisCom.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                }
                            }
                        }
                    }
                }
            } 
            catch (Exception e) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(null, e);
            }
            return blnRes;
        }
        
        
    private boolean existeRep(Connection conn, int codEmp, int codLoc, int codMnu, int codUsr) {
        boolean blnRes = false;
        ResultSet rst;
        Statement stm;

        String strSql = " select count(*) as cantmnu "
                + " from tbm_rptSis as a1 "
                + " inner join tbr_rptSisUsr as a2 on (a1.co_rpt=a2.co_rpt) "
                + " where a2.co_emp=" + codEmp + " "
                + " and a2.co_loc=" + codLoc + " "
                + " and a2.co_mnu=" + codMnu + " "
                + " and a2.co_usr=" + codUsr + " "
                + " and a1.st_reg='A' "
                + " and a2.st_reg in ('A','S') ";
        try {

            if (codUsr == 1) {
                return true;
            }

            stm = conn.createStatement();
            rst = stm.executeQuery(strSql);
            if (rst.next()) {
                if (rst.getInt("cantmnu") > 0) {
                    blnRes = true;
                } else {
                    blnRes = false;
                }
            }
        } catch (SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    /*JoseMario 10/Mayo/2016*/
    /* Inserta en tabla de seguimiento de Transferencias de inventario */
     private boolean insertarSegTra(java.sql.Connection conn,int intCodDoc){
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc;
        String strCadena;
        boolean blnRes=false;
        int intCodSeg=0, intNumSeg=0;
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                stmLoc01=conn.createStatement();
                strCadena = " SELECT MAX(co_reg)+1 as co_reg, a1.co_seg  \n";
                strCadena+= " FROM tbm_cabSegMovInv as a1 \n";
                strCadena+= " WHERE a1.co_seg = ( \n";
                strCadena+= "     SELECT co_seg \n";
                strCadena+= "     FROM tbm_cabSegMovInv \n";
                strCadena+= "     WHERE co_empRelCabCotVen="+objZafParSis.getCodigoEmpresa()+" AND  \n";
                strCadena+= "     co_locRelCabCotVen="+objZafParSis.getCodigoLocal()+" AND co_cotRelCabCotVen="+intCodCot+" \n  ";
                strCadena+= " ) \n GROUP BY a1.co_seg";    
                System.out.println("ObtenerNumeroSeguimiento: " + strCadena);
                rstLoc = stmLoc.executeQuery(strCadena);
                if (rstLoc.next()) {
                    intNumSeg = rstLoc.getInt("co_reg");
                    intCodSeg = rstLoc.getInt("co_seg");
                }
                strCadena=" ";
                strCadena+=" INSERT INTO tbm_cabSegMovInv (co_seg,co_reg,co_empRelCabMovInv, co_locRelCabMovInv, co_tipDocRelCabMovInv, co_docRelCabMovInv) ";
                strCadena+=" VALUES ("+intCodSeg+", "+intNumSeg+", "+objZafParSis.getCodigoEmpresa()+", "+objZafParSis.getCodigoLocal()+","+txtCodTipDoc.getText()+","+intCodDoc+" );";
                
//                strCadena+=" UPDATE tbm_cabCotVen SET st_reg='E' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal();
//                strCadena+=" AND co_cot="+intCodCot+"; ";
                 
                System.out.println("InsertarEnTablaSeguimiento  : "+strCadena);
                
                stmLoc01.executeUpdate(strCadena);
                blnRes=true;
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                stmLoc01.close();
                stmLoc01 = null;
            }
        }
        catch(SQLException evt){   
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        catch(Exception evt){   
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        return blnRes;
    }

     /* LA OC sale con numero desde el inicio JM JoseMario 1/Junio/2016 */
     
     private boolean asignaNumeroOC(java.sql.Connection conn,int intCodDoc){
         boolean blnRes=true;
         String strSql;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{
             if(conn!=null){
                stmLoc=conn.createStatement();
                strSql = "SELECT CASE WHEN (ne_ultDoc+1) IS NULL THEN 1 ELSE (ne_ultDoc+1) END AS ultnum, st_predoc FROM tbm_cabTipDoc WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                       + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipDoc=" + txtCodTipDoc.getText();
                System.out.println("asignaNumeroOC: previa " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()){
                    intNumDocOC = rstLoc.getInt("ultnum");
                }
                rstLoc.close();
                rstLoc=null;
                 strSql = "UPDATE tbm_cabTipDoc SET ne_ultDoc=" + intNumDocOC + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipDoc=" + txtCodTipDoc.getText();
                strSql += ";UPDATE tbm_cabmovinv SET ne_numdoc=" + intNumDocOC + ", st_reg='A', tx_obs2=tx_obs2 || ' Cot:' || "+intCodCot+" "
                        + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + " AND co_loc=" + objZafParSis.getCodigoLocal() + " AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_doc=" +intCodDoc;

                strSql += " ; UPDATE tbm_cabdia SET tx_numdia='" + intNumDocOC + "' "
                        + " WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + " AND co_loc=" + objZafParSis.getCodigoLocal()+ " AND co_tipdoc=" + txtCodTipDoc.getText() + " AND co_dia="+ intCodDoc;
                System.out.println("asignaNumeroOC: " + strSql);
                stmLoc.executeUpdate(strSql);
             }
        }
        catch(SQLException evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        catch(Exception evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, evt); 
        }
        return blnRes;
     }
    

private String _getEstConf(){
 String strConf="F";
 try{
  for(int i=0; i<tblDat.getRowCount(); i++){
    if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
      if(!tblDat.getValueAt(i,INT_TBL_CODBOD).toString().equals(tblDat.getValueAt(i,INT_TBL_BODORI).toString())){
          strConf="P";
      }
  }}
 }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
 return strConf;
}


private boolean insertarRegRep(java.sql.Connection conn ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strMerIngEgr="",strTipIngEgr="";
  
  int intCodDoc=0;
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();
        txtsql.setText("");
        strMerIngEgr=objTipDoc.getst_meringegrfisbod();
        strTipIngEgr=objTipDoc.gettx_natdoc();
        intCodDoc=objUltDocPrint.getCodigoDocumento(conn, objTipDoc.getco_tipdoc());

        if(insertarCabRep(conn, intCodDoc, strConfInv )){
         if(insertarDetRep(intCodDoc, strMerIngEgr )){
          if(insertarPagRep(conn, null, intCodDoc )){
           if(objDiario.insertarDiario_OC(conn, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), String.valueOf(intCodDoc), String.valueOf(intCodDoc), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), 45  ) ){
            if(actualizaStock(conn, strMerIngEgr )){
               txtsql.setText("");

               recostearItmGrp(conn);

               blnRes=true;
            }else blnRes=false;
           }else blnRes=false;
          }else blnRes=false;
         }else blnRes=false;
        }else blnRes=false;

        objInvItm.limpiarObjeto();
        stmLoc.close();
        stmLoc=null;
  }}catch(SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
    catch(Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
  return blnRes;
}


  
       
        

    private void recostearItmGrp(java.sql.Connection conn ){
        int i;
        try{
            for(i=0; i<tblDat.getRowCount(); i++){
              if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
                      objUti.recostearItm2009DesdeFecha( this, objZafParSis, conn,  objZafParSis.getCodigoEmpresa(), tblDat.getValueAt(i,INT_TBL_CODITM).toString(), txtFecDoc.getText(), "dd/MM/yyyy");
                }
            }
        }
        catch (Exception e){ 
            objUti.mostrarMsgErr_F1(this, e); 
        }
    }


     
              
            
    
     protected void finalize() throws Throwable 
   {  
       super.finalize();
       System.out.println ("Se libera Objeto...");

   }
    
}
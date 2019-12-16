    /*
 * zafCotCom.java     
 *             
 * Created on 6 de septiembre de 2004, 15:54  
 */    
      
package Compras.ZafCom03;

import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;  //*************
import java.util.ArrayList;  //*******************
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;   //******
import Librerias.ZafRptSis.ZafRptSis;
   
/*   
 * @author : jayapata      
 *    
 *    
 */
public class ZafCom03 extends javax.swing.JInternalFrame{
    
    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafAsiDia.ZafAsiDia objDiario;
    Librerias.ZafUtil.ZafTipDoc objTipDoc;
    Librerias.ZafUtil.ZafCtaCtb_Ing_Mot objCtaCtb;   //  PARA OBTENER  LAS CUENTAS   
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;  // Para trabajar con la informacion de tipo de documento
    Librerias.ZafInventario.ZafInvItm objInvItm;
    private ZafTblCelEdiTxtVco  objTblCelEdiTxtVcoBod;

    ZafVenCon objVenCon2; //*****************  
    ZafVenCon objVenConTipdoc; //*****************  
    ZafVenCon objVenConPrv; //*****************  
    ZafVenCon objVenConBod;

    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JButton en celda.    
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private boolean blnChangeData=false;
    private String strBeforeValue,strAfterValue;
    private ZafThreadGUI objThrGUI;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;   //Editor: JTextField de consulta en celda.
   
    
    ZafUtil objUti;
    mitoolbar objTooBar;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    Connection conCab;  //Coneccion a la base donde se encuentra la Documento
 
    Statement stmCab;   //Statement para la Documento 
    ResultSet rstCab;//Resultset que tendra los datos de la cabecera de la Documento
    String sSQL, strFiltro,strAux;//EL filtro de la Consulta actual
    int intCodMnu; //obtiene el codigo del menu
    double dblPorIva ;  //Porcentaje de Iva para la empresa enviado en zafParSys
    double dblSubtotalCot, dblTotalDoc, dblIvaCot;
    int intNumDec, intNumDoc= 0, intNumOrdCom=0, op=1 ; //Numero de decimales a presentar
    int intCodBodPre=0;
    boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    String strTipEmp;             
    //Constantes
    final int INT_TBL_LINEA    = 0 ; 
    final int INT_TBL_ITMALT   = 1;
    final int INT_TBL_BUTITM   = 2 ;
    final int INT_TBL_DESITM   = 3;
    final int INT_TBL_UNIDAD   = 4;
    final int INT_TBL_CODBOD   = 5 ;
    final int INT_TBL_BUTBOD   = 6 ;
    final int INT_TBL_CANORI   = 7;
    final int INT_TBL_CANMOV   = 8 ;            //Cantidad del movimiento (venta o compra)
    final int INT_TBL_PRECOS   = 9 ;           //Precio de Venta
    final int INT_TBL_PORDES   = 10 ;           //Porcentaje de descuento
    final int INT_TBL_BLNIVA   = 11;           //Boolean Iva
    final int INT_TBL_TOTAL    = 12;           //Total de la venta o compra del producto
    final int INT_TBL_CODITM   = 13;
    final int INT_TBL_ITMORI   = 14;    //Codigo del item de una o/c cargada
    final int INT_TBL_BODORI   = 15;    //Codigo del bodega de una o/c cargada
    final int INT_TBL_ESTADO   = 16;
    final int INT_TBL_IVATXT   = 17;
    final int INT_TBL_ITMSER   = 18;            //Columna que contiene SI el item es de servicio S o N
    final int INT_TBL_CODITMACT = 19;     //Codigo del item actual    
    final int INT_TBL_IEBODFIS  = 20;  // estado que dice si ingresa/egresa fisicamente en bodega
  
       
      
    //Constantes del ArrayList Elementos Eliminados
    final int INT_ARR_CODITM   = 0;
    final int INT_ARR_CODBOD   = 1;
    final int INT_ARR_CANMOV   = 2;
    final int INT_ARR_ITMORI   = 3;
    final int INT_ARR_BODORI   = 4;
    final int INT_ARR_CANORI   = 5;
    final int INT_ARR_IEBODFIS = 6;
      
    java.util.Vector vecDetDiario; //vector que genera y actuliza el objDiario (ZafAsiDia)   
    final String VERSION = " v 4.5";
     
      String  STR_ESTREG="";  
      
    final ArrayList arreBod=new ArrayList();
    
    String GLO_strArreItm="";
    String strMerIngEgr="", strTipIngEgr="";


    String strCodEmpOC="";
    String strCodLocOC="";
    String strCodTipOC="";
    String strCodDocOC="";
    boolean blnEstCarOC=false;

    String strFecResDoc=""; 

    
    /** Creates new form zafCotCom */
    public ZafCom03(Librerias.ZafParSis.ZafParSis obj) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        jfrThis = this;
        
        initComponents();
        objUti         = new ZafUtil();
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);        
        //objZafInv      = new Librerias.ZafInventario.ZafInventario(this, objZafParSis);         
        //objSisCon      = new Librerias.ZafSisCon.ZafSisCon(objZafParSis);
        this.setTitle(objZafParSis.getNombreMenu()+VERSION);
        intCodMnu = objZafParSis.getCodigoMenu();
        intNumDec = objZafParSis.getDecimalesMostrar();        
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText(""); 
        txtFecDoc.setBounds(570, 6, 92, 20);
        panCotGenNor.add(txtFecDoc);
        objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);
        objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
        
         //***********************************************************************
              objDiario.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                 public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objDiario.setCodigoTipoDocumento(-1);
                    else
                        objDiario.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                  }
               });
            //***********************************************************************
               
         objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

        
        tabDocVar.addTab("Diario",objDiario);    
       // objDiario.setEditable(true);
        
        objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis,1);
        
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb_Ing_Mot(objZafParSis);
        txtCodTipDoc.setVisible(false);
        //Nombres de los tabs
        tabDocVar.setTitleAt(0,"General");
        tabDocVar.setTitleAt(1,"Asiento de Diario");
        objTooBar = new mitoolbar(this);
        
        getIva();
        this.getContentPane().add(objTooBar,"South");
        
        pack();
        
        cargarTipEmp();
        
        getObtenerBodPre();

        
        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));
        
        
        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
        addListenerCambio();
        objUti.desactivarCom(this);
         
          CargarBodegas();
         
        // this.setBounds(10,10, 700,450);
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }         
    }

     /** Creates new form zafCotCom */
    public ZafCom03(Librerias.ZafParSis.ZafParSis obj, Integer intCodEmp, Integer intCodLoc, Integer intCodTipDoc, Integer intCodDoc ) {
      try{
        this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        jfrThis = this;

        initComponents();
        objUti         = new ZafUtil();
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
        this.setTitle(objZafParSis.getNombreMenu()+VERSION);

             strCodEmpOC= String.valueOf( intCodEmp.intValue() );
             strCodLocOC= String.valueOf( intCodLoc.intValue() );;
             strCodTipOC= String.valueOf( intCodTipDoc.intValue() );;
             strCodDocOC= String.valueOf( intCodDoc.intValue() );;
             blnEstCarOC=true;

        objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);


        intCodMnu = objZafParSis.getCodigoMenu();
        intNumDec = objZafParSis.getDecimalesMostrar();
        txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
        txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtFecDoc.setText("");
        txtFecDoc.setBounds(570, 6, 92, 20);
        panCotGenNor.add(txtFecDoc);
        objDiario = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);
        objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);

         //***********************************************************************
              objDiario.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                 public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objDiario.setCodigoTipoDocumento(-1);
                    else
                        objDiario.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                  }
               });
            //***********************************************************************



        tabDocVar.addTab("Diario",objDiario);
       // objDiario.setEditable(true);

        objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis,1);

        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb_Ing_Mot(objZafParSis);
        txtCodTipDoc.setVisible(false);
        //Nombres de los tabs
        tabDocVar.setTitleAt(0,"General");
        tabDocVar.setTitleAt(1,"Asiento de Diario");
        objTooBar = new mitoolbar(this);

        getIva();
        //this.getContentPane().add(objTooBar,"South");

        pack();

        cargarTipEmp();


        /* Dando tamano preferido para Scroll de txaObs1 para que al hacer grande la pantalla
           NO se altere el formulario y se vea bonito  */
        spnObs1.setPreferredSize(new java.awt.Dimension(350,30));


        /* Metodo para agregar o eliminar lineas con enter y con escape
         */
        addListenerCambio();
        objUti.desactivarCom(this);

          CargarBodegas();

        // this.setBounds(10,10, 700,450);
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }
    }


      
     public void CargarBodegas(){
         java.sql.Connection conTmp ;
         java.sql.Statement stmTmp;
         java.sql.ResultSet rst;
         try
          {
           conTmp = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
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
              conTmp.close();
              rst=null;
              stmTmp=null;
              conTmp=null;
              
          }
       }catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);     }
       
    }
   
     
    
    public void Configura_ventana_consulta(){
        configurarVenConProducto();
        configurarVenConTipDoc();
        configurarVenConProveedor();
        configurarVenConBod();
        
         Configurar_tabla();

        if(blnEstCarOC){
           cargarDatos( strCodEmpOC, strCodLocOC, strCodTipOC, strCodDocOC  );
         }

    }
    

private boolean configurarVenConBod() {
boolean blnRes=true;
try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_bod");
    arlCam.add("a.tx_nom");
    
    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Nom.Bod.");
  
    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("350");
  
    //Armar la sentencia SQL.   a7.nd_stkTot,
    String Str_Sql="";

     if(objZafParSis.getCodigoUsuario()==1){
      Str_Sql="select a.co_bod, a.tx_nom from tbm_bod as a  where a.co_emp="+objZafParSis.getCodigoEmpresa()+" order by co_bod ";
     }else {
            Str_Sql ="SELECT * FROM (    select a.co_bod, a1.tx_nom  from tbr_bodtipdocprgusr as a " +
            " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) " +
            " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_mnu="+objZafParSis.getCodigoMenu()+" " +
            " and a.co_usr="+objZafParSis.getCodigoUsuario()+" ) AS a   ";
     }
  
    objVenConBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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



private boolean cargarDatos(String intCodEmp, String intCodLoc, String intCodTipDoc, String intCodDoc ){
  boolean blnRes=true;
  java.sql.Connection conn;
  try{
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){

       cargarCabReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
       cargarDetReg(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );

      conn.close();
      conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


 private boolean cargarCabReg(java.sql.Connection con, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc )
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL="";
                strSQL= "SELECT CabMovInv.co_cli, CabMovInv.tx_nomcli as nomcli, CabMovInv.tx_dircli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                      " CabMovInv.co_com as co_com, CabMovInv.tx_nomven as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                      " CabMovInv.co_doc, CabMovInv.fe_doc, CabMovInv.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                      " CabMovInv.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                      " CabMovInv.tx_obs1, CabMovInv.tx_obs2, CabMovInv.nd_sub, CabMovInv.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                      " CabMovInv.st_reg, CabMovInv.ne_numcot as num_cot, CabMovInv.ne_numdoc as num_doc, " + // Campo para saber si esta anulado o no
                      " CabMovInv.tx_numped as num_ped, CabMovInv.co_tipdoc, CabMovInv.ne_secemp, CabMovInv.st_regrep " +
                      " FROM tbm_cabMovInv as CabMovInv " +
                      " Where CabMovInv.st_reg not in('E') and  CabMovInv.co_emp=" + strCodEmp +
                      " AND CabMovInv.co_loc="    + strCodLoc +
                      " AND CabMovInv.co_tipDoc=" + strCodTipDoc +
                      " AND CabMovInv.co_doc="    + strCodDoc;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intNumDoc = rst.getInt("co_doc");
                    txtDoc.setText(""+intNumDoc);
                    cargarCabTipoDoc(rst.getInt("co_tipdoc"));
                    txtOrdCom.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                    objDiario.consultarDiarioCompleto( Integer.parseInt( strCodEmp ), Integer.parseInt( strCodLoc ), Integer.parseInt( strCodTipDoc ), Integer.parseInt( strCodDoc )  );
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

                      STR_ESTREG=rst.getString("st_regrep");

                   //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));

                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
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

                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
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

public void  cargarDetReg(java.sql.Connection con, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
        try{//odbc,usuario,password
            int intNumDoc = 0;
            String strSer="";


                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";


                       //Extrayendo los datos del detalle respectivo a esta orden de compra
                    sSQL  = "Select a.co_itm,a.tx_codalt,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
                            ",b.st_ser, a.co_itmact " +
                            " "+strAux2+" FROM  tbm_detMovInv as a " +
                            " inner join tbm_inv as b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm) where " +
                            " a.co_emp = " + strCodEmp + " and " +
                            " a.co_loc = " + strCodLoc   + " and " +
                            " a.co_tipdoc = " + strCodTipDoc   + " and " +
                            " a.co_doc = " + strCodDoc  + " order by a.co_reg";

                    

//                    System.out.println(sSQL);
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
                         Boolean blnIva = new Boolean(strIva.equals("S"));
                         vecReg.add(INT_TBL_BLNIVA, blnIva);
                         
                         dblCan    = rst.getDouble("nd_can");
                         dblPre    = rst.getDouble("nd_cosuni");
                         dblPorDes = rst.getDouble("nd_pordes");
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
                         vecReg.add(INT_TBL_ITMSER, strSer );
                         vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact") );
                         vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));


                         vecData.add(vecReg);
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod);
                    calculaTotal();
                    lblCotNumDes.setText("Ajuste de Inventario No. " + txtOrdCom.getText() );


               
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
              Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
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
        String Str_Sql="";
        try
        {
        
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
            arlCam.add("a7.proconf");
            
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
            arlAli.add("IE.FisBod."); 
            
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
            arlAncCol.add("20");
                
            //Armar la sentencia SQL.   a7.nd_stkTot,
            /*
                int co_tipdoc=1; 
                String strAux = ",CASE WHEN (" +
                " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
                " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " ))) THEN 'S' ELSE 'N' END  as isterL";
            
                
             String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) IN ( " +
            " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
            " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
            " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
            " THEN 'S' ELSE 'N' END AS proconf  ";
             
             
                
                Str_Sql=" SELECT tx_codAlt, co_itm, tx_nomItm,  nd_stkAct, nd_cosUni, st_ivacom, tx_desCor, tx_codAlt2, st_ser " +
                " "+strAux2+" "+
                " FROM ( " +
                "        SELECT d1.tx_codAlt, d1.co_itm, d1.tx_nomItm,  d3.nd_stkAct, d2.nd_cosUni, d1.st_ivacom, d4.tx_desCor, d1.tx_codAlt2,d1.st_ser ";
                Str_Sql+=strAux;
                Str_Sql+=" FROM ( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.co_uni, a1.st_ivacom,a1.st_ser";
                Str_Sql+=" FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                Str_Sql+=" WHERE a1.st_reg not in('T','I','U') and  a1.co_emp="+objZafParSis.getCodigoEmpresa()+" ) AS d1";
                Str_Sql+=" INNER JOIN ( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1";
                Str_Sql+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                Str_Sql+=" WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae)";
                Str_Sql+=" INNER JOIN (SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct";
                Str_Sql+=" FROM tbm_equInv AS c1 INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)";
                Str_Sql+=" INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)";
                Str_Sql+=" WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae";
                Str_Sql+=" ) AS d3 ON (d1.co_itmMae=d3.co_itmMae)";
                Str_Sql+=" LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg) " +
                " )  AS x WHERE " +
                " CASE WHEN (" +
                " SELECT COUNT(*) FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
                " AND co_tipdoc="+co_tipdoc+" AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
                " )>=1 THEN x.isterl='S' ELSE  x.isterl='N'  END " +
                " ORDER BY tx_codAlt";
            */
            Str_Sql = objInvItm.getSqlInvIngEgr();
            
            Str_Sql = "SELECT * FROM ( "+Str_Sql+" ) AS a7 WHERE a7.st_ser='N' ";
                       

             //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=7;
            intColOcu[1]=9;
              
            objVenCon2=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            objVenCon2.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            objVenCon2.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, objVenCon2.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, objVenCon2.INT_FOR_NUM, objZafParSis.getFormatoNumero(),false,true);
            objVenCon2.setConfiguracionColumna(6, javax.swing.JLabel.CENTER); 
            
        
        }
        catch (Exception e)  {  objUti.mostrarMsgErr_F1(this, e);  } 
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
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
             String  strSQL="";
           //  strSQL+="select co_cli,tx_nom,tx_dir,tx_tel,tx_ide from tbm_cli as a where a.co_emp = " + objZafParSis.getCodigoEmpresa() +" and a.st_reg='A'  and a.st_prv='S' order by a.tx_nom"; 
            strSQL="SELECT  a.co_cli,tx_nom,tx_dir,tx_tel,tx_ide FROM tbr_cliloc as a1 " +
            " INNER JOIN tbm_cli AS a on (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli) " +
            " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'  and a.st_prv='S' order by a.tx_nom";
             
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
     
       
      
    
   private boolean Configurar_tabla() {
        boolean blnRes=false;
        try{
          
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
            vecCab.add(INT_TBL_ESTADO,"Estado");
            vecCab.add(INT_TBL_IVATXT,"");
            vecCab.add(INT_TBL_ITMSER,"Ser.");
            vecCab.add(INT_TBL_CODITMACT, "");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod"); 
           
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);

            tblDat.setModel(objTblMod);   
            //Configurar JTable: Establecer tipo de selección.
//            tblDat.setRowSelectionAllowed(false);
//            tblDat.setCellSelectionEnabled(true);
//            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
            arlAux.add("" + INT_TBL_ITMORI);
            arlAux.add("" + INT_TBL_CODBOD);
            arlAux.add("" + INT_TBL_CANMOV);
            arlAux.add("" + INT_TBL_ITMALT);   // INT_TBL_ITMORI 
            arlAux.add("" + INT_TBL_BODORI);
            arlAux.add("" + INT_TBL_CANORI);
            arlAux.add("" + INT_TBL_IEBODFIS);
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
            
            tcmAux.getColumn(INT_TBL_CODITM).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_IVATXT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITMORI).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_BODORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BODORI).setPreferredWidth(0);            
            tcmAux.getColumn(INT_TBL_CANORI).setWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CANORI).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_ITMSER).setWidth(0);
            tcmAux.getColumn(INT_TBL_ITMSER).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ITMSER).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ITMSER).setPreferredWidth(0);
            
            tcmAux.getColumn(INT_TBL_CODITMACT).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODITMACT).setPreferredWidth(0);
             
            
            
              
            
            //Configurar JTable: Ocultar columnas del sistema.
            /**
             * Estado : 
                V:vacio.- si al momento de carga no tenia codigo asociado
                N:Nuevo.- Si fue modificado despues de la carga y estaba vacio
                E:Existe.- Si al momento de carga tenia codigo asociado
                M:Modificado.- Si fue modificado despues de la carga y estaba existe
             */
            tcmAux.getColumn(INT_TBL_ESTADO).setWidth(0);  
            tcmAux.getColumn(INT_TBL_ESTADO).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_ESTADO).setResizable(false);                         
                                
              
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_ITMALT);
            vecAux.add("" + INT_TBL_BUTITM);
            vecAux.add("" + INT_TBL_CODBOD);
            vecAux.add("" + INT_TBL_BUTBOD);
            vecAux.add("" + INT_TBL_CANMOV);
            vecAux.add("" + INT_TBL_PRECOS);
            vecAux.add("" + INT_TBL_PORDES);
            vecAux.add("" + INT_TBL_BLNIVA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){                    
                    blnChangeData = false;        
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                  
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

                     calculaTotal();
                     generaAsiento();                            
                     blnChangeData = true;
                }
            });


            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_ITMALT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
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
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANORI).setCellRenderer(objTblCelRenLbl);
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
            //strSQL= " SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct FROM tbm_bod AS a1 right outer join tbr_bodLoc as bodloc on ( a1.co_emp = bodloc.co_emp and a1.co_bod = bodloc.co_bod) RIGHT OUTER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)"+
            //        " WHERE a2.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a1.st_reg='A'";


            /*
            strSQL =" select a1.co_bod, a1.tx_nom, a2.nd_stkact  from tbm_invbod as a2 " +
            " left join  tbm_bod as a1 on (a1.co_emp=a2.co_emp and a1.co_bod=a2.co_bod) " +
            " where a2.co_emp="+objZafParSis.getCodigoEmpresa();


            int intColVenBod[]=new int[1];
            intColVenBod[0]=1;
            int intColTblBod[]=new int[1];
            intColTblBod[0]=INT_TBL_CODBOD;
            objTblCelEdiTxtCon2=new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon("Listado Bodega", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL,  "a1.co_bod, a1.tx_nom,a2.nd_stkAct","Codigo,Nombre,Stock", intColVenBod, intColTblBod);
            objTblCelEdiTxtCon2.setIndiceCampoBusqueda(0);
            objTblCelEdiTxtCon2.setCampoBusqueda("a1.co_bod", objTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtCon2.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_CODBOD).setCellEditor(objTblCelEdiTxtCon2);            
            objTblCelEdiTxtCon2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM).toString();
                    objTblCelEdiTxtCon2.setCondicionesSQL(" AND a2.co_itm=" + strAux + "");
                    
                      blnChangeData = false;                            
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else 
			strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                    
                }                
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strBeforeValue ="";
 
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strAfterValue ="";
                    if (!strBeforeValue.equals(strAfterValue))
                        generaAsiento();
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
                     
                }
            });
            */
             
        int intColFac[]=new int[1];
        intColFac[0]=1;

        int intColTblFac[]=new int[1];
        intColTblFac[0]=INT_TBL_CODBOD;

        objTblCelEdiTxtVcoBod=new ZafTblCelEdiTxtVco(tblDat, objVenConBod, intColFac, intColTblFac );
        tcmAux.getColumn(INT_TBL_CODBOD).setCellEditor(objTblCelEdiTxtVcoBod);
        objTblCelEdiTxtVcoBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
               int intNumFil = tblDat.getSelectedRow();

               if( (tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null))
                 objTblCelEdiTxtVcoBod.setCancelarEdicion(true);

                if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null)
                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);


            }
            public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                objVenConBod.setCampoBusqueda(9);
                objVenConBod.setCriterio1(11);
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                int intNumFil = tblDat.getSelectedRow();

                 if( tblDat.getValueAt( intNumFil, INT_TBL_CODITM)==null){
                     objTblCelEdiTxtVcoBod.setCancelarEdicion(true);
                 }


                   generaAsiento();

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

            }
        });

        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTBOD).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;

        new ButBod(tblDat, INT_TBL_BUTBOD);



            /*
            objTblCelEdiBut2=new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut("Listado Bodega", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL,"a1.co_bod, a1.tx_nom,a2.nd_stkAct","Codigo,Nombre,Stock", intColVenBod, intColTblBod);
            tcmAux.getColumn(INT_TBL_BUTBOD).setCellEditor(objTblCelEdiBut2);
            objTblCelEdiBut2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODITM).toString();
                    objTblCelEdiBut2.setCondicionesSQL(" AND a2.co_itm=" + strAux + "");
                    
                }                
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strBeforeValue ="";
 
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD)!=null)
                        strAfterValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_CODBOD).toString();
                    else
                        strAfterValue ="";
                    if (!strBeforeValue.equals(strAfterValue))
                        generaAsiento();
                     
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
                     
                     
                     
                     
                }
            });
            
            
            intColVenBod=null;
            intColTblBod=null;

             */
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            strSQL="";
          
   
  
            int intColVen[]=new int[9];
            intColVen[0]=1;
            intColVen[1]=3;
            intColVen[2]=5;
            intColVen[3]=2;            
            intColVen[4]=6;          
            intColVen[5]=7; 
            intColVen[6]=9;
            intColVen[7]=2;
            intColVen[8]=10;
            int intColTbl[]=new int[9];
            intColTbl[0]=INT_TBL_ITMALT;
            intColTbl[1]=INT_TBL_DESITM;
            intColTbl[2]=INT_TBL_PRECOS;
            intColTbl[3]=INT_TBL_CODITM;
            intColTbl[4]=INT_TBL_IVATXT;
            intColTbl[5]=INT_TBL_UNIDAD;
            intColTbl[6]=INT_TBL_ITMSER;
            intColTbl[7]=INT_TBL_CODITMACT;
            intColTbl[8]=INT_TBL_IEBODFIS;
            
                
            
           ////**********objTblCelEdiTxtCon=new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon(tblDat, intColVen, intColTbl,objVenCon2);
          
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, objVenCon2, intColVen, intColTbl);
            
            // objTblCelEdiTxtCon.setIndiceCampoBusqueda(0);
          //  objTblCelEdiTxtCon.setCampoBusqueda("tx_codAlt", objTblCelEdiTxtCon.INT_CAM_STR);
          //  objTblCelEdiTxtCon.setIndiceTipoBusqueda(2);

          ///************  tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtCon);
          
           tcmAux.getColumn(INT_TBL_ITMALT).setCellEditor(objTblCelEdiTxtVcoItm);
             
          ///********* objTblCelEdiTxtCon.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
           objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                     
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//                    System.out.println("beforeEdit:"+blnChangeData);
                    blnChangeData = false;                            
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT)!=null)
                        strBeforeValue = tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ITMALT).toString();
                    else 
			strBeforeValue = "";
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO)==null){
                        tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    }
                }                
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
                            tblDat.setValueAt(new Boolean(true), tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                        else
                            tblDat.setValueAt(new Boolean(false), tblDat.getSelectedRow(), INT_TBL_BLNIVA);
                    }
                    if (!tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString().equals("V") && !(strBeforeValue.equals(strAfterValue)) && !blnChangeData){
                        blnChangeData = true;
                        if(objZafParSis.getCodigoUsuario()==1)
                          tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), tblDat.getSelectedRow(), INT_TBL_CODBOD);
                        else
                          tblDat.setValueAt(new Integer(intCodBodPre), tblDat.getSelectedRow(), INT_TBL_CODBOD);


                        calculaSubtotal();
                    }                        
                }
            });

            
     
            
               ButFndPrv ObjFndPrv = new ButFndPrv(tblDat, INT_TBL_BUTITM);   //*****
            

           
            
            
               
            
            intColVen=null;
            intColTbl=null;
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PRECOS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
//                    System.out.println("beforeEdit:"+blnChangeData);
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
//                    System.out.println("afterEdit:"+strAfterValue);
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
                           
//                    System.out.println("txt Fila:"+tblDat.getSelectedRow()+" Estado: "+tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_ESTADO).toString());
                    if (tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn())==null ||  tblDat.getValueAt(tblDat.getSelectedRow(),tblDat.getSelectedColumn()).equals(""))
                        tblDat.setValueAt("0",tblDat.getSelectedRow(),tblDat.getSelectedColumn());
//                    System.out.println("before: "  + strBeforeValue + " - after: " + strAfterValue);
                    if (tblDat.getSelectedColumn()==INT_TBL_PORDES){
                         if(strAfterValue.equals("")) strAfterValue="0.00"; 
                        if ( Double.parseDouble(strAfterValue) >100 || Double.parseDouble(strAfterValue) <0 ){
                            MensajeInf("Error! Porcentaje de Descuento ");
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
            //Configurar JTable: Centrar columnas.
              new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
           // System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;                        
    }    












 private class ButBod extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButBod(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {
      String strEstApl=  (tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_CODITM).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_CODITM).toString()));

      if(!strEstApl.equals("N")){
        listaBodega(intNumFil);
      }
    }
 }}

     private void listaBodega(int intNumFil){

        objVenConBod.setTitle("Listado de Bodega");
        objVenConBod.show();
        if (objVenConBod.getSelectedButton()==objVenConBod.INT_BUT_ACE) {
            tblDat.setValueAt(objVenConBod.getValueAt(1),intNumFil,INT_TBL_CODBOD);

                     generaAsiento();

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

            //eventoVenConCli(intNumFil);
        }

    }


   public void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
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
        
        objCtaCtb = new Librerias.ZafUtil.ZafCtaCtb_Ing_Mot(objZafParSis, Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ), objTipDoc.gettx_natdoc() );            
        
        getIva();
    }
      
    public void cargarTipEmp(){
        Statement stmTipEmp;
        ResultSet rstEmp;
        String sSql;
        
        try{//odbc,usuario,password
             java.sql.Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if (con!=null){
                    stmTipEmp=con.createStatement();                    
                    sSql="Select tx_tipper from tbm_emp where co_emp = " + objZafParSis.getCodigoEmpresa();
                    rstEmp = stmTipEmp.executeQuery(sSql);
                    if(rstEmp.next())
                        strTipEmp=rstEmp.getString("tx_tipper");
                    rstEmp.close();
                    stmTipEmp.close();
                    con.close();
                }
            }catch(SQLException Evt){
                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
            }catch(Exception Evt){
                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
            }                        
    }

public void getObtenerBodPre(){
  java.sql.Connection conn;
  Statement stmLoc;
  ResultSet rstLoc;
  String strSql="";
  try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
      if(conn!=null){
         stmLoc=conn.createStatement();
        
         strSql ="SELECT a.co_bod FROM (    select a.co_bod, a1.tx_nom  from tbr_bodtipdocprgusr as a " +
         " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod ) " +
         " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_mnu="+objZafParSis.getCodigoMenu()+" " +
         " and a.co_usr="+objZafParSis.getCodigoUsuario()+" and a.st_reg='P' ) AS a   ";
         rstLoc = stmLoc.executeQuery(strSql);
         if(rstLoc.next())
                intCodBodPre=rstLoc.getInt("co_bod");
         rstLoc.close();
         rstLoc=null;
         stmLoc.close();
         stmLoc=null;
         conn.close();
         conn=null;
 }}catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
}



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabDocVar = new javax.swing.JTabbedPane();
        panCotGen = new javax.swing.JPanel();
        panCotGenNor = new javax.swing.JPanel();
        lblFecDoc = new javax.swing.JLabel();
        lblTipDoc1 = new javax.swing.JLabel();
        txtNomTipDoc = new javax.swing.JTextField();
        txtDetTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtDoc = new javax.swing.JTextField();
        lblOrdCom = new javax.swing.JLabel();
        txtOrdCom = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtPrvNom = new javax.swing.JTextField();
        lblPro = new javax.swing.JLabel();
        txtPrvCod = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblDir = new javax.swing.JLabel();
        txtPrvDir = new javax.swing.JTextField();
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

        tabDocVar.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabDocVar.setName("General"); // NOI18N

        panCotGen.setLayout(new java.awt.BorderLayout());

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 110));
        panCotGenNor.setLayout(null);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCotGenNor.add(lblFecDoc);
        lblFecDoc.setBounds(468, 8, 108, 15);

        lblTipDoc1.setFont(new java.awt.Font("SansSerif", 0, 11));
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
        txtNomTipDoc.setBounds(110, 4, 65, 20);

        txtDetTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDetTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDetTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDetTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtDetTipDoc);
        txtDetTipDoc.setBounds(176, 4, 220, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCotGenNor.add(butTipDoc);
        butTipDoc.setBounds(400, 5, 20, 20);

        lblNumDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNumDoc.setText("Cod. Documento:");
        panCotGenNor.add(lblNumDoc);
        lblNumDoc.setBounds(5, 28, 102, 15);

        txtDoc.setBackground(objZafParSis.getColorCamposSistema()
        );
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtDoc);
        txtDoc.setBounds(110, 25, 92, 20);

        lblOrdCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblOrdCom.setText("No. Documento:");
        panCotGenNor.add(lblOrdCom);
        lblOrdCom.setBounds(5, 48, 102, 15);

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
        txtOrdCom.setBounds(110, 45, 92, 20);
        panCotGenNor.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(240, 30, 40, 20);

        txtPrvNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPrvNom.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtPrvNom.setPreferredSize(new java.awt.Dimension(100, 20));
        txtPrvNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvNomActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtPrvNom);
        txtPrvNom.setBounds(146, 68, 290, 20);

        lblPro.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPro.setText("Proveedor:");
        panCotGenNor.add(lblPro);
        lblPro.setBounds(8, 66, 72, 15);

        txtPrvCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPrvCod.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtPrvCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtPrvCod.setPreferredSize(new java.awt.Dimension(25, 20));
        txtPrvCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrvCodActionPerformed(evt);
            }
        });
        panCotGenNor.add(txtPrvCod);
        txtPrvCod.setBounds(110, 68, 35, 20);

        butPrv.setFont(new java.awt.Font("SansSerif", 0, 11));
        butPrv.setText("...");
        butPrv.setPreferredSize(new java.awt.Dimension(20, 20));
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panCotGenNor.add(butPrv);
        butPrv.setBounds(438, 68, 22, 20);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Dirección:");
        panCotGenNor.add(lblDir);
        lblDir.setBounds(6, 90, 60, 15);

        txtPrvDir.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtPrvDir.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNor.add(txtPrvDir);
        txtPrvDir.setBounds(110, 88, 350, 20);

        panCotGen.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Codigo", "...", "Descripción", "Unidad", "Bodega", "...", "Cantidad", "Costo", "%Desc", "Iva", "Total", "Codigo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false, true, true, true, true, false, false, false, false
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

        lblObs2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs2.setText("Observación 1:");
        jPanel4.add(lblObs2, java.awt.BorderLayout.WEST);

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs1.setText("Observación 2:");
        jPanel3.add(lblObs1, java.awt.BorderLayout.WEST);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCotGenSurEst.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSubTot.setText("SubTotal:");
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenSurEst.add(lblSubTot);
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblIva.setText("IVA 12%:");
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);
        panCotGenSurEst.add(txtIva);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12));
        lblTot.setText("Total:");
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12));
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panCotGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabDocVar.addTab("tab1", panCotGen);

        getContentPane().add(tabDocVar, java.awt.BorderLayout.CENTER);

        lblCotNumDes.setText("Ingresos/Egresos por Varios");
        lblCotNumDes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCotNumDes.setOpaque(true);
        panCotNor.add(lblCotNumDes);

        getContentPane().add(panCotNor, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrvCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvCodActionPerformed
        // TODO add your handling code here:
           BuscarProveedor("a.co_cli",txtPrvCod.getText(),0);
    }//GEN-LAST:event_txtPrvCodActionPerformed

    private void txtPrvNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrvNomActionPerformed
        // TODO add your handling code here:
          BuscarProveedor("a.tx_nom",txtPrvNom.getText(),1);
    }//GEN-LAST:event_txtPrvNomActionPerformed

    
     public void BuscarProveedor(String campo,String strBusqueda,int tipo){
        objVenConPrv.setTitle("Listado de Clientes"); 
        if (objVenConPrv.buscar(campo, strBusqueda ))
        {
              txtPrvCod.setText(objVenConPrv.getValueAt(1));
              txtPrvNom.setText(objVenConPrv.getValueAt(2));
              txtPrvDir.setText(objVenConPrv.getValueAt(3));
             // FndVenToCli();
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
               // FndVenToCli();
             }
        }
         
   }
  
       
     
    
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
                //FndVenToCli();
             } 
              
    }//GEN-LAST:event_butPrvActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
         
    }//GEN-LAST:event_formInternalFrameOpened

    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        // TODO add your handling code here:

          String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
        
        
    }//GEN-LAST:event_CerrarVentana

    private void txtDetTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDetTipDocActionPerformed
        // TODO add your handling code here:
         listaTipdoc("a.tx_deslar",txtDetTipDoc.getText(),2);
    }//GEN-LAST:event_txtDetTipDocActionPerformed

    private void txtNomTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipDocActionPerformed
        // TODO add your handling code here:
        listaTipdoc("a.tx_descor",txtNomTipDoc.getText(),1);
    }//GEN-LAST:event_txtNomTipDocActionPerformed

    private void txtNomTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipDocFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
         listaTipdoc("","",0);
    }//GEN-LAST:event_butTipDocActionPerformed
//    private void listaDocPrg(String strBusqueda,int intTipo)
//    {
//        Librerias.ZafConsulta.ZafConsulta  objFndTipDoc =
//        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(jfrThis),
//           "Codigo,Alias,Descripcion","cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar",
//             "Select distinct cabtip.co_tipdoc,cabtip.tx_descor,cabtip.tx_deslar from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)" +
//                " where   docprg.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
//                        " docprg.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
//                        " docprg.co_mnu = " + objZafParSis.getCodigoMenu()  ,strBusqueda, 
//        objZafParSis.getStringConexion(),
//        objZafParSis.getUsuarioBaseDatos(),
//        objZafParSis.getClaveBaseDatos()
//        );
//        
//        objFndTipDoc.setTitle("Listado Tipos de Documentos");
//               
//        switch (intTipo){
//            case 0:
//                objFndTipDoc.show();
//                break;
//            case 1:
//                if(!txtNomTipDoc.getText().equals("")){
//                    objFndTipDoc.setSelectedCamBus(1);
//                    if(!objFndTipDoc.buscar("cabtip.tx_descor = '" + txtNomTipDoc.getText()+"'")){
//                        objFndTipDoc.show();
//                    }
//
//                }
//                break;
//            case 2:
//                if(!txtDetTipDoc.getText().equals("")){
//                    objFndTipDoc.setSelectedCamBus(2);
//                    if(!objFndTipDoc.buscar("cabtip.tx_deslar = '" + txtDetTipDoc.getText()+"'")){
//                        objFndTipDoc.show();
//                    }
//                }                
//                break;
//        }
//        if(!objFndTipDoc.GetCamSel(1).equals("")){
//            cargarCabTipoDoc(Integer.parseInt(objFndTipDoc.GetCamSel(1)));            
//            if (objTooBar.getEstado()=='n') cargaNum_Doc_OrdCom();
//            generaAsiento();
//        }        
//        
//    }
//    
    private void txtOrdComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdComActionPerformed
        // TODO add your handling code here:
//        if (txtCodTipDoc.getText().equals("")) {
//            txtOrdCom.setText("");
//            txtCodTipDoc.requestFocus();
//        }
    }//GEN-LAST:event_txtOrdComActionPerformed

    private void txtOrdComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOrdComFocusLost
        // TODO add your handling code here:
//        if (txtCodTipDoc.getText().equals("")) {
//            txtOrdCom.setText("");
//            txtCodTipDoc.requestFocus();
//        }
    }//GEN-LAST:event_txtOrdComFocusLost

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void cargaNum_Doc_OrdCom(){
          intNumDoc = 0;
          intNumOrdCom = 0;
               
           try{//odbc,usuario,password
                    if (objTooBar.getEstado()=='n'){                        
                        intNumOrdCom=objTipDoc.getne_ultdoc()+1;
                        txtOrdCom.setText("" + intNumOrdCom);
                  }
           }catch(Exception Evt){
                objUti.mostrarMsgErr_F1(jfrThis, Evt);
           }                       
   }
     
    /*
     * Agrega el listener para detectar que hubo algun cambio en la caja de texto
     */
    private void addListenerCambio(){
        objlisCambios = new LisTextos();
        

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
    
        
    /*
     * Obtiene el Iva que se debe cobrar en la empresa actual
     */
  public void getIva(){
        if (objTipDoc.getst_iva().equals("S")){
            dblPorIva = objCtaCtb.getPorcentajeIvaCompras();
        }else{
            dblPorIva = 0;                        
        }
        lblIva.setText("IVA " + dblPorIva + "%");
    }    
    
    public void  noEditable(boolean blnEditable){
            java.awt.Color colBack = txtCodTipDoc.getBackground();

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
            txtDoc.setText("");
            txtFecDoc.setText("");
            txtOrdCom.setText("");
            txtCodTipDoc.setText("");
            txtNomTipDoc.setText("");
            txtDetTipDoc.setText("");
            
            txtPrvCod.setText("");
            txtPrvNom.setText("");
            txtPrvDir.setText("");
                
            
        objTblMod.removeAllRows();          
        //Diario
            objDiario.inicializar();
        //Pie de pagina
            txaObs1.setText("");
            txaObs2.setText("");
            txtSub.setText("0");
            txtIva.setText("0");
            txtTot.setText("0");
            lblCotNumDes.setText("Ingresos/Egresos por Varios");

    }
   
    
    public void calculaSubtotal(){
       try{
            double dblCan,dblDes,dblCosto;
            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV)==null)
                tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_CANMOV);
            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PRECOS)==null)
                tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PRECOS);
            if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES)==null)
                tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PORDES);
            dblCan = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV).toString());
            dblCosto = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PRECOS).toString());
            if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES).toString())/100);
            else dblDes = 0;
            tblDat.setValueAt(String.valueOf(objUti.redondear(((dblCan * dblCosto )-dblDes), objZafParSis.getDecimalesMostrar())),tblDat.getSelectedRow(), INT_TBL_TOTAL);
            calculaTotal();
            generaAsiento();
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(jfrThis, e );
       }
    }
    
    
    public void  calculaTotal(){
        double dblSub = 0, dblIva = 0, dblDes = 0, dblTmp=0;
        try{
            for (int i=0;i<tblDat.getRowCount();i++){ 
                dblSub += objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesMostrar());  

                if(tblDat.getValueAt(i, INT_TBL_BLNIVA)!=null){
                    dblTmp = ((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))? objUti.redondear(((tblDat.getValueAt(i, INT_TBL_TOTAL)==null||tblDat.getValueAt(i, INT_TBL_TOTAL).toString().equals(""))?0:Double.parseDouble(tblDat.getValueAt(i, INT_TBL_TOTAL).toString())),objZafParSis.getDecimalesMostrar()): 0 );
                    dblIva += objUti.redondear((((dblTmp * dblPorIva)==0)?0:dblTmp * (dblPorIva/100)),objZafParSis.getDecimalesMostrar()) ;
                }
            }
           
            //System.out.println();
            txtSub.setText( "" + objUti.redondear(dblSub,intNumDec) );
            txtIva.setText( "" + objUti.redondear(dblIva,intNumDec) );
            txtTot.setText( "" + objUti.redondear(dblSub + dblIva ,intNumDec)   );
        }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(jfrThis, e );
       }
    }
    
    
    
    /**
     * Metodo que genera vector de diario para la clase ZafAsiDia
     * Genera segun el tipo de documento y su naturaleza, bodegas y si genera o no IVA
     *@autor: jayapata
     */
    
    private void generaAsiento(){
        
          objDiario.inicializar();
          double dblSubtotal;
         int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
         int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
         int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
         int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
         int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
         int INT_VEC_DEBE   = 5; //5) Debe. 
         int INT_VEC_HABER  = 6; //6) Haber. 
         int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
          int INT_VEC_NUEVO    = 8;

           if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
             else vecDetDiario.removeAllElements();
          
         java.util.Vector vecReg; 
         
         int intCodBodT,intCodBod;
         
        if(objTipDoc.gettx_natdoc().equals("I"))
        {
            
         for (int j=0;j<arreBod.size();j++){
            intCodBod = Integer.parseInt(arreBod.get(j).toString());
            dblSubtotal=0;
             for (int i=0;i<tblDat.getRowCount();i++){            
                if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                {
                    if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                    {
                       intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                       if(intCodBod==intCodBodT)
                          dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
             }}}
                      if(dblSubtotal > 0 ){
                       vecReg = new java.util.Vector();
                       vecReg.add(INT_LINEA, null);                
                       vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaExistencia( intCodBod ) );
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
             
           if (objTipDoc.getst_iva().equals("S"))
            {
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaIvaCompras() );
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());   
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                 
                vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtIva.getText(),objZafParSis.getDecimalesMostrar())));
                vecReg.add(INT_VEC_HABER, new Double(0)); 
                vecReg.add(INT_VEC_REF, null);
                 vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);                       
            }       
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaCompra() );
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(0)); 
            vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar())));
            vecReg.add(INT_VEC_REF, null);
             vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);  
           
        }else{  
               
                 
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaCompra() );
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCom() );
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCtaCom() );
            vecReg.add(INT_VEC_DEBE, new Double(objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar()))); 
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
             vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);  
            
              
             for (int j=0;j<arreBod.size();j++){
            intCodBod = Integer.parseInt(arreBod.get(j).toString());
            dblSubtotal=0;
             for (int i=0;i<tblDat.getRowCount();i++){            
                if (tblDat.getValueAt(i, INT_TBL_CODITM)!=null && tblDat.getValueAt(i,INT_TBL_TOTAL)!=null)
                {
                    if (Double.parseDouble(tblDat.getValueAt(i,INT_TBL_TOTAL).toString())>0)
                    {
                       intCodBodT = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD)==null?"0":tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
                        if(intCodBod==intCodBodT)
                          dblSubtotal += objUti.redondear(tblDat.getValueAt(i,INT_TBL_TOTAL)==null?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString(),objZafParSis.getDecimalesMostrar());
             }}}
                      if(dblSubtotal > 0 ){
                       vecReg = new java.util.Vector();
                       vecReg.add(INT_LINEA, null);                
                       vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaExistencia( intCodBod ) );
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
            
            
              if (objTipDoc.getst_iva().equals("S"))
            {
                vecReg = new java.util.Vector();
                vecReg.add(INT_LINEA, null);                
                vecReg.add(INT_VEC_CODCTA, objCtaCtb.getCuentaIvaCompras() );
                vecReg.add(INT_VEC_NUMCTA, objCtaCtb.getNumCtaIva());  
                vecReg.add(INT_VEC_BOTON, null);
                vecReg.add(INT_VEC_NOMCTA, objCtaCtb.getNomCtaIva());                
                vecReg.add(INT_VEC_DEBE, new Double(0)); 
                vecReg.add(INT_VEC_HABER, new Double(objUti.redondear(txtIva.getText(),objZafParSis.getDecimalesMostrar())));   
                vecReg.add(INT_VEC_REF, null);
                 vecReg.add(INT_VEC_NUEVO, null);
                vecDetDiario.add(vecReg);                       
            }       
          }   
        objDiario.setDetalleDiario(vecDetDiario);         
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
   
    
    public void  refrescaDatos(){
        try{//odbc,usuario,password        
            int intNumDoc = 0;
            conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if(rstCab != null){
                //Detalle        
                    String strSer="";
                    
                    
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(b.tx_codalt), length(b.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
            
                
                       //Extrayendo los datos del detalle respectivo a esta orden de compra
                    sSQL  = "Select a.co_itm,a.tx_codalt,a.tx_nomitm,a.tx_unimed,a.co_bod,a.nd_can,a.nd_canorg,a.nd_cosuni,a.nd_preuni,a.nd_pordes,a.st_ivacom" +
                            ",b.st_ser, a.co_itmact " +
                            " "+strAux2+" FROM  tbm_detMovInv as a " +
                            " inner join tbm_inv as b on(a.co_emp=b.co_emp and a.co_itm=b.co_itm) where " +
                            " a.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                            " a.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                            " a.co_tipdoc = " + objTipDoc.getco_tipdoc()     + " and " +
                            " a.co_doc = " + rstCab.getString("co_doc")      + " order by a.co_reg";
                    
                    
                    
//                    System.out.println(sSQL);
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
                         vecReg.add(INT_TBL_CANORI, new Double(rst.getDouble("nd_can")*getAccionDoc()));
                         vecReg.add(INT_TBL_CANMOV, new Double(rst.getDouble("nd_can")*getAccionDoc()));                                                                           
                         vecReg.add(INT_TBL_PRECOS, new Double(rst.getDouble("nd_cosuni")));
                         vecReg.add(INT_TBL_PORDES, new Double(rst.getDouble("nd_pordes")));
                         String strIva = rst.getString("st_ivacom");
                         Boolean blnIva = new Boolean(strIva.equals("S"));                         
                         vecReg.add(INT_TBL_BLNIVA, blnIva);
                         dblCan    = rst.getDouble("nd_can")* Double.parseDouble(""+getAccionDoc());
                         dblPre    = rst.getDouble("nd_cosuni");
                         dblPorDes = rst.getDouble("nd_pordes");
                         dblValDes =   ((dblCan * dblPre)==0)?0:((dblCan * dblPre) * dblPorDes / 100) ;
                         dblTotal  = objUti.redondear((dblCan * dblPre)- dblValDes,objZafParSis.getDecimalesMostrar()) ;
                         if (blnIva.booleanValue()){                            
                            dblIva = objUti.redondear(dblIva+(((dblTotal * dblPorIva)==0)?0:dblTotal * (dblPorIva/100)),objZafParSis.getDecimalesMostrar()) ;
                         }
                         
                         
                         
                         /*
                           double dblCan,dblDes,dblCosto;
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_CANMOV);
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PRECOS)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PRECOS);
        if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES)==null)
            tblDat.setValueAt("0",tblDat.getSelectedRow(), INT_TBL_PORDES);
        dblCan = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CANMOV).toString());
        dblCosto = Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PRECOS).toString());
        if (dblCan>0) dblDes = ((dblCan * dblCosto )*Double.parseDouble(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_PORDES).toString())/100);
        else dblDes = 0;
        tblDat.setValueAt(String.valueOf(objUti.redondear(((dblCan * dblCosto )-dblDes), objZafParSis.getDecimalesMostrar())),tblDat.getSelectedRow(), INT_TBL_TOTAL);
        calculaTotal();
        generaAsiento();
                         
                         */ 
                              
                         vecReg.add(INT_TBL_TOTAL, new Double(dblTotal));
                         vecReg.add(INT_TBL_CODITM, rst.getString("co_itm"));
                         vecReg.add(INT_TBL_ITMORI, rst.getString("co_itm"));
                         vecReg.add(INT_TBL_BODORI, new Integer(rst.getInt("co_bod")));
                         vecReg.add(INT_TBL_ESTADO, "E");
                         vecReg.add(INT_TBL_IVATXT, "");
                         vecReg.add(INT_TBL_ITMSER, strSer );
                         vecReg.add(INT_TBL_CODITMACT, rst.getString("co_itmact") );
                         vecReg.add(INT_TBL_IEBODFIS, rst.getString("proconf"));
                    
                         
                         vecData.add(vecReg);                         
                     }
                     objTblMod.setData(vecData);
                     tblDat .setModel(objTblMod); 
                    calculaTotal();
                    lblCotNumDes.setText("Ajuste de Inventario No. " + txtOrdCom.getText() );
                  
                                       
                }
            conCab.close();
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

    public boolean isServicio(int cod_itm){
        boolean blnIsServicio=false;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
               try{
                   con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   if (con!=null)
                   {
                       /*
                        *  String para el hacer el query en la tabla de clientes y obtener 
                        *  sus datos .
                        */

                            String strSql = "select st_ser from tbm_inv where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_itm = " + cod_itm;
                            stm = con.createStatement();
                            rst = stm.executeQuery(strSql);
                            if(rst.next()){
                                blnIsServicio =  (rst.getString("st_ser").charAt(0)=='S')? true: false;
                            }
                            rst.close();
                            stm.close();
                            con.close();
                   }
               }catch(java.sql.SQLException Evt){
                       return false;
               }catch(Exception Evt){
                       return false;
               }
         return blnIsServicio;
    }
    private boolean isAnulada()
    {
       boolean blnRes = false;
      java.sql.Connection conTmp ;
      java.sql.Statement stmTmp;
      String strSQL = "";
      try
      {
          conTmp = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
          if (conTmp!=null)
          {
              stmTmp = conTmp.createStatement();
              strSQL = "";
              strSQL = " Select count(*) from tbm_cabmovinv ";
              strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and  co_tipDoc = " + txtCodTipDoc.getText() + " and  co_doc = " +  txtDoc.getText() + " and st_reg = 'I' " ;
              if (objUti.getNumeroRegistro(this,objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0){
                  blnRes = true;
//                  MensajeInf("Documento anulado no se puede modificar");
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
    private void MensajeInf(String strMsg){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
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
                strSQL= "SELECT CabMovInv.co_cli, CabMovInv.tx_nomcli as nomcli, CabMovInv.tx_dircli as dircli, " + //<== Campos con los datos del CLiente para la cabecera
                      " CabMovInv.co_com as co_com, CabMovInv.tx_nomven as nomcom, " +        //<==Campos con los datos del Vendedor para poner en cabecera
                      " CabMovInv.co_doc, CabMovInv.fe_doc, CabMovInv.tx_ate,  " + //<==Campos que aparecen en la parte superior del 1er Tab
                      " CabMovInv.co_forPag, " + //<==Campos que aparecen en la parte superior del 2do Tab
                      " CabMovInv.tx_obs1, CabMovInv.tx_obs2, CabMovInv.nd_sub, CabMovInv.nd_porIva, " + //<==Campos que aparecen en la parte Inferior del 1er Tab
                      " CabMovInv.st_reg, CabMovInv.ne_numcot as num_cot, CabMovInv.ne_numdoc as num_doc, " + // Campo para saber si esta anulado o no
                      " CabMovInv.tx_numped as num_ped, CabMovInv.co_tipdoc, CabMovInv.ne_secemp, CabMovInv.st_regrep " +
                      " FROM tbm_cabMovInv as CabMovInv " +
                      " Where CabMovInv.st_reg not in('E') and  CabMovInv.co_emp=" + rstCab.getString("co_emp") +
                      " AND CabMovInv.co_loc="    + rstCab.getString("co_loc") +
                      " AND CabMovInv.co_tipDoc=" + rstCab.getString("co_tipDoc") +
                      " AND CabMovInv.co_doc="    + rstCab.getString("co_doc") ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intNumDoc = rst.getInt("co_doc");
                    txtDoc.setText(""+intNumDoc);                    
                    cargarCabTipoDoc(rst.getInt("co_tipdoc"));
                    txtOrdCom.setText(((rst.getString("num_doc")==null)?"":rst.getString("num_doc")));
                    objDiario.consultarDiarioCompleto(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(), rst.getInt("co_doc"));
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

                    strFecResDoc= rst.getString("fe_doc");

                      STR_ESTREG=rst.getString("st_regrep");
                      
                   //Pie de pagina
                    txaObs1.setText(((rst.getString("tx_obs1")==null)?"":rst.getString("tx_obs1")));
                    txaObs2.setText(((rst.getString("tx_obs2")==null)?"":rst.getString("tx_obs2")));
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
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
       
//    private void cargarRegistroInsert(){
//       try{//odbc,usuario,password
//                conCab = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                if (conCab!=null){
//                    stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
//
//                    sSQL= "SELECT co_emp,co_loc,co_tipdoc, co_doc " +
//                          " FROM tbm_cabMovInv" +
//                          " Where co_emp = "  + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
//                          " and co_loc = "    + objZafParSis.getCodigoLocal() +
//                          " AND co_tipDoc="   + objTipDoc.getco_tipdoc() +
//                          " AND co_doc="      + txtDoc.getText() ;
////                                System.out.println(sSQL);
//                    rstCab=stmCab.executeQuery(sSQL);
//                    if(rstCab.next()){
//                        rstCab.last();
//                        objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
//                        for (int i=0;i<objTblMod.getRowCountTrue();i++){                                            
//                            tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CODITM).toString(), i, INT_TBL_ITMORI);
//                            tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CODBOD).toString(), i, INT_TBL_BODORI);
//                            tblDat.setValueAt(tblDat.getValueAt(i,INT_TBL_CANMOV).toString(), i, INT_TBL_CANORI);
//                        }               
//                    }
//                    else{
//                        objTooBar.setMenSis("0 Registros encontrados");
//                        clnTextos();
//                    }
//                }
//       }catch(SQLException Evt){
//              objUti.mostrarMsgErr_F1(jfrThis, Evt);
//       }catch(Exception Evt){
//              objUti.mostrarMsgErr_F1(jfrThis, Evt);
//       }                       
//    }                   
//    
  
    public class mitoolbar extends ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
        }

        public boolean beforeAceptar() {
            return true;
        }        
        public boolean beforeAnular() {
            return true;
        }        
        public boolean beforeCancelar() {
            return true;
        }        
        public boolean beforeConsultar() {
            return true;
        }        
        public boolean beforeEliminar() {
            return true;
        }        
        public boolean beforeImprimir() {
            return true;
        }        
        public boolean beforeInsertar() {
            return true;
        }        
        public boolean beforeModificar() {
            return true;
        }        
        public boolean beforeVistaPreliminar() {
            return true;
        }
          
        
        
public boolean anular()
{
    strAux=objTooBar.getEstadoRegistro();
    if (strAux.equals("Eliminado")){
        MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
        return false;
    }
    if (strAux.equals("Anulado")){
        MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
        return false;
    }
   if(!verificaItmUniAnu())
     return false;

   if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
     return false;

   if(!anularReg())
      return false;

    recostearItmGrp();

    objTooBar.setEstadoRegistro("Anulado");
    blnHayCam=false;
    return true;
}



        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
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

        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
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

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
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

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
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
                noEditable(false);                
                objDiario.setEditable(true);
                txtFecDoc.setHoy();
                if (objTipDoc.getco_tipdoc()==-1)
                    cargaTipoDocPredeterminado();
                else
                    cargarCabTipoDoc(objTipDoc.getco_tipdoc());
                
                
                if (!txtCodTipDoc.getText().equals(""))
                    cargaNum_Doc_OrdCom();

                txtOrdCom.requestFocus();
                txtOrdCom.setSelectionStart(0);
                txtOrdCom.setSelectionEnd(txtOrdCom.getText().length());
                setEditable(true);
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

        
public boolean eliminar(){
 try{
    strAux=objTooBar.getEstadoRegistro();
    if (strAux.equals("Eliminado")){
        MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
        return false;
    }
   if(!verificaItmUniAnu())
      return false;

   if(!objUltDocPrint.verificarsiesconfirmado(txtDoc.getText(),txtCodTipDoc.getText()))
      return false;

    if (!eliminarReg())
        return false;
    //Desplazarse al siguiente registro si es posible.
    if (!rstCab.isLast()){
        rstCab.next();
        cargarReg();
    }else{
        objTooBar.setEstadoRegistro("Eliminado");
        clnTextos();
    }
    blnHayCam=false;
}catch (java.sql.SQLException e){  return true; }
return true;
}

        



public boolean insertar()
{
  if(!validaCampos())
    return false;

  if(!insertarReg())
    return false;

  recostearItmGrp();

  blnHayCam=false;
 return true;
}

        
        
       
        
        
private void recostearItmGrp(){
 int i,j; 
 java.sql.Connection con;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="", strFecResDes="";
 try{    
  con = DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
  if(con!=null){  
      stmLoc=con.createStatement();

       strSql="select *, case when fecori < fecact then fecori else fecact end  as fecres "
       + " from ( select  date('"+strFecResDoc+"') as fecori, date('"+objUti.formatearFecha( txtFecDoc.getText(), "dd/MM/yyyy", "yyyy-MM-dd" )+"') as fecact ) as x ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
           strFecResDes=rstLoc.getString("fecres");
           strFecResDoc=rstLoc.getString("fecres");
       }
       rstLoc.close();
       rstLoc=null;

      strFecResDes = objUti.formatearFecha( strFecResDes, "yyyy-MM-dd", "yyyy/MM/dd" );
      for(i=0; i<tblDat.getRowCount(); i++){
        if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){
          objUti.recostearItm2009DesdeFecha(this, objZafParSis, con, objZafParSis.getCodigoEmpresa(), tblDat.getValueAt(i,INT_TBL_CODITM).toString(), strFecResDes, "yyyy/MM/dd");
      }}

   con.close();
   con=null;
 }}catch (java.sql.SQLException e){ objUti.mostrarMsgErr_F1(this, e); }
}





public boolean modificar(){
   strAux=objTooBar.getEstadoRegistro();
   if (strAux.equals("Eliminado")){
        MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
        return false;
   }
   if (strAux.equals("Anulado")){
        MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
        return false;
   }
   if(!verificaItmUni())
      return false;

   if (!validaCampos())
       return false;

   if (!actualizarReg())
      return false;

   recostearItmGrp();

 blnHayCam=false;
 return true;
}


        
        
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam)
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
            blnHayCam=false;
            return blnRes;
        }
          
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            this.setEstado('w');
           // cargarRegistroInsert();            
            return true;
        }
        
        public boolean afterModificar() {
                butTipDoc.setEnabled(false);
                txtNomTipDoc.setEditable(false);
                txtDetTipDoc.setEditable(false);
                txtDoc.setEditable(false);
                txtOrdCom.setEditable(false);
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }        
        
        public boolean imprimir() {
            
              cargarRepote(0);

//            Librerias.ZafRpt.ZafRptXLS.ZafRptXLS objPRINT  = new Librerias.ZafRpt.ZafRptXLS.ZafRptXLS(objZafParSis);
//            if(!txtCodTipDoc.getText().equals("") && !txtDoc.getText().equals("") ){
//                objPRINT.GenerarDoc( Integer.parseInt(txtCodTipDoc.getText()) , Integer.parseInt(txtDoc.getText()));
//            }
//            objPRINT = null;
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


             
  public boolean vistaPreliminar(){

        cargarRepote(1);

//        int intCodEmp=objZafParSis.getCodigoEmpresa();
//        int intCodLoc=objZafParSis.getCodigoLocal();
//
//        Connection conIns;
//        try
//        {
//            conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//        try
//        {
//            if(conIns!=null){
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
//
//                // Second, create a map of parameters to pass to the report.
//                Map parameters = new HashMap();
//                parameters.put("tit", "");
//                parameters.put("codEmp", ""+intCodEmp);
//                parameters.put("codLoc", ""+intCodLoc);
//                parameters.put("CodTipDoc",txtCodTipDoc.getText());
//                parameters.put("codDoc",  txtDoc.getText() );
//
//                JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                JasperViewer.viewReport(report, false);
//
//                conIns.close();
//                conIns=null;
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
      
  
        
        
        
        
        
        
        
        
        
        
     
        


public boolean insertarReg(){   
 boolean blnRes=false;
 java.sql.Connection conIns;
 int intSecEmp=0, intSecGrp=0;
 if( objUltDocPrint.getExisteDoc(Integer.parseInt( txtOrdCom.getText()) ,  Integer.parseInt(txtCodTipDoc.getText()) ) ){
    MensajeInf("No. de Doc ya Existe");
    txtOrdCom.requestFocus();
    txtOrdCom.selectAll();
    return false;
 }
 try{
     conIns=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conIns!=null){


        intSecEmp = objUltDocPrint.getNumSecDoc( conIns, objZafParSis.getCodigoEmpresa()   );
        intSecGrp = objUltDocPrint.getNumSecDoc( conIns, objZafParSis.getCodigoEmpresaGrupo()  );


        conIns.setAutoCommit(false);

        txtDoc.setText(""+objUltDocPrint.getCodigoDocumento(conIns, objTipDoc.getco_tipdoc()));
        intNumDoc = Integer.parseInt(txtDoc.getText());                                 
        if(insertarCab(conIns, intNumDoc, intSecEmp, intSecGrp )){
         if(insertarDet(conIns, intNumDoc)){
           if(objDiario.insertarDiario(conIns, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()),  txtDoc.getText(), txtOrdCom.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy") )) {
               conIns.commit();
               blnRes=true;
           }else conIns.rollback();   
          }else conIns.rollback();   
        }else conIns.rollback();

        if(blnRes){
           for(int i=0; i<tblDat.getRowCount(); i++){ 
                if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
                      tblDat.setValueAt("E", i, INT_TBL_ESTADO);
                      tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODITM).toString() , i, INT_TBL_ITMORI);
                      tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODBOD).toString() , i, INT_TBL_BODORI);
                      tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CANMOV).toString() , i, INT_TBL_CANORI);
         }}}   

        STR_ESTREG="I";
        conIns.close();                                            
        conIns=null;
        objTblMod.clearDataSavedBeforeRemoveRow();
        objTblMod.initRowsState();
 }}catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
   catch(Exception Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(jfrThis, Evt); }
 return blnRes;                  
}  





        
        
        
        
        
        

private boolean insertarCab(java.sql.Connection conIns, int intNumDoc, int intSecEmp, int intSecGrp   ){
  boolean blnRes = true;
  java.sql.Statement stm;
  String strSQL, strFecha, strFecSis;
  try{
        strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
        int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
        strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";

        strFecResDoc= strFecha;

        strSQL="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, ne_secemp, ne_secgrp,"+ //" ne_secgrpant, ne_secempant,  " +
        " fe_doc,  ne_numDoc, tx_obs1, tx_obs2, nd_sub,nd_tot, fe_ing," +
        " co_usrIng,st_reg , co_cli , tx_nomCli, tx_dirCli , st_regrep ) " +
        " VALUES("+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal() + "," + Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+intSecEmp+", " +
        " "+intSecGrp+","+ //" "+(intSecGrp-1)+", "+(intSecEmp-1)+", " +
        " '"+strFecha+"',"+txtOrdCom.getText()+",'"+txaObs1.getText().replaceAll("'","") + "','" + txaObs2.getText().replaceAll("'","") + "', " +
        " "+(objUti.redondear(txtSub.getText(),objZafParSis.getDecimalesMostrar()) * getAccionDoc())+","+(objUti.redondear(txtTot.getText(),objZafParSis.getDecimalesMostrar()) * getAccionDoc())+", " +
        " '"+strFecSis+"',"+objZafParSis.getCodigoUsuario()+",'A',"+ objUti.codificar(txtPrvCod.getText(), 2 ) +",'"+txtPrvNom.getText()+"','"+txtPrvDir.getText()+"','I')";

        stm = conIns.createStatement();
        stm.executeUpdate(strSQL);
        stm.close();
        stm=null;
  }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
  catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

        
     
     
     
        
        
      private boolean insertarDet_respaldo(java.sql.Connection conIns, int intNumDoc  ){
          boolean blnRes = true;
          int intControl=0;
          double dlbCanMov=0;
          String strSQL;
          int intSer=0;
          int INTCODEMPGRP=0;
          StringBuffer stbinvemp=new StringBuffer(); //VARIABLE TIPO BUFFER
          StringBuffer stbinvbodemp=new StringBuffer(); //VARIABLE TIPO BUFFER
          StringBuffer stbinvgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
          StringBuffer stbinvbodgrp=new StringBuffer(); //VARIABLE TIPO BUFFER
          StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
          StringBuffer stbEmp=new StringBuffer(); //VARIABLE TIPO BUFFER
          StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
          java.sql.Statement stmDet;
          java.sql.ResultSet rst;
          try{
               GLO_strArreItm="";
               INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
               
                String str_MerIEFisBod="S";
                double dbl_canConIE=0.00;
                for(int i=0; i<tblDat.getRowCount(); i++){ 
                  if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
                             
                     //***********   VERFIFICA SI EL ITEM IE MER FIS BOD  ************************/
                      dbl_canConIE  = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
                      String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                       str_MerIEFisBod="N";
                       
                        //***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                                if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                                    if(strEstFisBod.equals("N")){
                                      if(strMerIngEgr.equals("S")){   
                                        str_MerIEFisBod="S";
                                        dbl_canConIE=0;
                                    }}
                                }
                        /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                                
                       
                       if(intControl!=0)    
                         GLO_strArreItm=GLO_strArreItm+",";
                                  
                         GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
                         intControl++;
                                               
                         if (i>0)
                         stbins.append(" UNION ALL ");
                         stbins.append("SELECT "+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+(i+1)+","+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+", " +
                         ""+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))+", " +
                         ""+(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)) * getAccionDoc()) +","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
                         ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
                         ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i,INT_TBL_PORDES).toString()),2) + ",'" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "','P'," +
                         ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() ) +","+  (objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() )+", " +
                         ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2)  * getAccionDoc() ) + ",'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" ");
                 }}
                                                 
                 if(intControl > 0 ){
                    strSQL = " INSERT INTO  tbm_detMovInv " +
                    " (co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_itm, co_itmact,  tx_codAlt, tx_nomItm, tx_unimed,co_bod, nd_can, nd_cosUni, " +
                    "  nd_cosUniGrp, nd_preUni,  nd_porDes, st_ivaCom,st_reg,nd_costot,nd_costotgrp, nd_tot ,st_regrep, st_meringegrfisbod , nd_cancon ) "+stbins.toString();
                    stmDet=conIns.createStatement();
                    stmDet.executeUpdate(strSQL);
                    stmDet.close();
                    stmDet=null;
                }else return false;
               
                
               
                String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
                " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
                " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
                " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
                " THEN 'S' ELSE 'N' END AS proconf  ";
            
                        strSQL = "SELECT  a.co_itm, a.co_bod, sum(a.nd_can) as cantidad "+strAux2+" FROM tbm_detmovinv as a " +
                        " INNER JOIN tbm_inv as inv on (a.co_emp=inv.co_emp and a.co_itm=inv.co_itm) " +
                        " WHERE a.co_Emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc="+txtCodTipDoc.getText()+" " +
                        " and a.co_doc="+intNumDoc+" and inv.st_ser='N' " +
                        " GROUP BY a.co_itm,a.co_bod, proconf";
                        //System.out.println(" "+ strSQL );
                        stmDet=conIns.createStatement();
                        rst= stmDet.executeQuery(strSQL);
                        while(rst.next()){
                            dlbCanMov=rst.getDouble("cantidad");
                            int intCodItm=rst.getInt("co_itm");
                            int intCodBod=rst.getInt("co_bod");
                            
                            String strEstFisBod=rst.getString("proconf");
                            String strAux=" ,0 as caningbod, 0 as canegrbod ";
                            if(strEstFisBod.equals("N")){
                             if(strMerIngEgr.equals("S")){
                               if(strTipIngEgr.equals("I")){ 
                                   strAux=", abs("+dlbCanMov+") as caningbod, 0 as canegrbod ";
                               }
                               if(strTipIngEgr.equals("E")){
                                   strAux=", abs("+dlbCanMov+") as canegrbod, 0 as caningbod  ";
                               } 
                             }} 
                              
                           if (intSer>0)
                                stbinvemp.append(" UNION ALL ");
                                stbinvemp.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp,"+intCodItm+" AS coitm,"+dlbCanMov+" AS stock");
                            
                            if (intSer>0)
                                stbinvbodemp.append(" UNION ALL ");
                                stbinvbodemp.append("SELECT "+objZafParSis.getCodigoEmpresa()+" AS coemp,"+intCodBod+" as cobod,"+intCodItm+" AS coitm,"+dlbCanMov+" AS stock "+strAux+" ");
                            
                            /********************* EMPRESA GRUPO  **********************/
                              
                            if (intSer>0)
                                stbinvgrp.append(" UNION ALL ");
                                stbinvgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                                "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+intCodItm+")) as coitm," +
                                ""+dlbCanMov+" AS stock");
                            
                            
                            if (intSer>0)
                                stbinvbodgrp.append(" UNION ALL ");
                                stbinvbodgrp.append("SELECT "+INTCODEMPGRP+" AS coempgrp, " +
                                "(Select co_itm from tbm_equinv where co_emp="+INTCODEMPGRP+" and co_itmmae IN  (Select co_itmmae from tbm_equinv where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_itm="+intCodItm+")) as coitm," +
                                "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_bod="+intCodBod+") as cobod, "+
                                ""+dlbCanMov+" AS stock "+strAux+" ");
                            
                            /****************************************************************/
                                
                             //**********************  STOCK DEL ITEM **************************//
                            if (intSer>0)
                                stbEmp.append(" UNION ALL ");
                                stbEmp.append(" SELECT  nd_stkact ,  "+rst.getInt("co_itm")+" as coitm FROM tbm_invbod   where co_emp="+INTCODEMPGRP+" and co_itm = " +
                                " ( Select co_itm from tbm_equinv where co_emp= "+INTCODEMPGRP+" and co_itmmae IN " +
                                " ( Select co_itmmae from tbm_equinv where co_emp = "+objZafParSis.getCodigoEmpresa()+" and co_itm= "+rst.getInt("co_itm")+" ) )  " +
                                "  and co_bod = ( SELECT co_bodgrp FROM  tbr_bodempbodgrp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_bod= "+rst.getInt("co_bod")+") ");
                                    

                            intSer=1;
                        }
                        
                         
                        
                         if(intSer==1){
                            if(intControl > 0){
                               //******************************* EMPRESA *********************************************
                               strSQL ="Update tbm_inv set st_regrep='M', nd_stkAct = nd_stkAct + a.stock " +
                               " FROM ("+stbinvemp.toString()+") AS a where  co_emp=a.coemp AND co_itm=a.coitm";
                              // stmDet.executeUpdate(strSQL);
                               
                               strSQL +=" ; Update tbm_invBod set  nd_stkAct  = nd_stkAct + a.stock , nd_caningbod=nd_caningbod+a.caningbod, nd_canegrbod=nd_canegrbod+a.canegrbod " +
                               " FROM ("+stbinvbodemp.toString()+") AS a where  co_emp=a.coemp  AND  co_bod=a.cobod and co_itm=a.coitm";
                               System.out.println(" "+ strSQL );
                               stmDet.executeUpdate(strSQL);
                             
                               //******************************* GRUPO *********************************************
                                strSQL ="Update tbm_inv  set  st_regrep='M', nd_stkact = nd_stkact + a.stock " +
                                " FROM("+stbinvgrp.toString()+") AS a where co_emp=a.coempgrp and co_itm = a.coitm";
                               // stmDet.executeUpdate(strSQL);
                                 
                                strSQL +=" ; Update tbm_invbod  set nd_stkact = nd_stkact + a.stock, nd_caningbod=nd_caningbod+a.caningbod, nd_canegrbod=nd_canegrbod+a.canegrbod " +
                                " FROM("+stbinvbodgrp.toString()+") AS a where co_emp=a.coempgrp AND co_itm=a.coitm AND co_bod=a.cobod";
                                System.out.println(" "+ strSQL );
                                stmDet.executeUpdate(strSQL);
                                //*************************************************************************************    
                          }}
                          
                        if(getAccionDoc() == -1 ){
                          rst = stmDet.executeQuery(stbEmp.toString());  // LOCAL
                           while(rst.next()){
                               if(rst.getFloat(1) < 0.00){
                                  for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                                        if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                                            if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM).toString().equals(rst.getString(2))){
                                                    javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                                                    String strTit, strMsg;
                                                    strTit="Mensaje del sistema Zafiro";
                                                    strMsg="El producto :  " + ((tblDat.getValueAt(intRowVal, INT_TBL_ITMALT)==null || tblDat.getValueAt(intRowVal, INT_TBL_ITMALT).toString().equals(""))?"0":tblDat.getValueAt(intRowVal, INT_TBL_ITMALT).toString())
                                                    + "  -   " + ((tblDat.getValueAt(intRowVal, INT_TBL_DESITM)==null || tblDat.getValueAt(intRowVal, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(intRowVal, INT_TBL_DESITM).toString()) + " No tiene stock suficiente "+ "\n No se puede Realizar la transaccion..!!" ;
                                                    oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                                    blnRes=false;
                                                    break;
                                                }}}
                                  }
                            }
                        }    
                         rst.close();
                         rst=null;       
                         stmDet.close();
                         stmDet=null;
                         stbinvemp=null;
                         stbinvbodemp=null;
                         stbinvgrp=null;
                         stbinvbodgrp=null;
                         stb=null;
                         stbEmp=null;
                         stbins=null;
         }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
         return blnRes;
      }
        
        
        
        
        
        
        

private boolean insertarDet(java.sql.Connection conIns, int intNumDoc  ){
  boolean blnRes = true;
  java.sql.Statement stmDet;
  double dblCanIngFisBod=0;
  double dblCanEgrFisBod=0;
  double dblCan=0;
  double dbl_canConIE=0.00;
  int intCodItm=0;
  int intCodBod=0;
  int intControl=0;
  int intReaActStk=0;
  int intTipMov=0;
  String strSQL;
  String str_MerIEFisBod="S";
  String strEstFisBod="";
  StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
  try{
       GLO_strArreItm="";

       objInvItm.inicializaObjeto();
       intTipMov=getAccionDoc();

        for(int i=0; i<tblDat.getRowCount(); i++){ 
          if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
             intCodItm=Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM)); 
             intCodBod=Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
             dblCan=Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
             dblCanIngFisBod=0;
             dblCanEgrFisBod=0;
             //***********   VERFIFICA SI EL ITEM IE MER FIS BOD  ************************/
              dbl_canConIE  = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
              strEstFisBod="N"; //(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
              str_MerIEFisBod="N"; 
             //***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                      if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                            if(strEstFisBod.equals("N")){
                              if(strMerIngEgr.equals("S")){   
                                str_MerIEFisBod="S";
                                dbl_canConIE=0;
                          }}
                           objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dblCan, intTipMov, strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                           intReaActStk=1;
                      }
                /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
               if(intControl!=0)    
                 GLO_strArreItm=GLO_strArreItm+",";

                 GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
                 intControl++;

                 if (i>0)
                 stbins.append(" UNION ALL ");
                 stbins.append("SELECT "+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+(i+1)+","+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+", " +
                 ""+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))+", " +
                 ""+(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)) * getAccionDoc()) +","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
                 ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
                 ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i,INT_TBL_PORDES).toString()),2) + ",'" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "','P'," +
                 ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() ) +","+  (objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() )+", " +
                 ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2)  * getAccionDoc() ) + ",'I', '"+str_MerIEFisBod+"', "+dbl_canConIE+" ");
         }}

         if(intControl > 0 ){
            strSQL = " INSERT INTO  tbm_detMovInv " +
            " (co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_itm, co_itmact,  tx_codAlt, tx_nomItm, tx_unimed,co_bod, nd_can, nd_cosUni, " +
            "  nd_cosUniGrp, nd_preUni,  nd_porDes, st_ivaCom,st_reg,nd_costot,nd_costotgrp, nd_tot ,st_regrep, st_meringegrfisbod , nd_cancon ) "+stbins.toString();
            stmDet=conIns.createStatement();
            stmDet.executeUpdate(strSQL);
            stmDet.close();
            stmDet=null;
         }else return false;

         if(intReaActStk==1){
           if(!objInvItm.ejecutaActStock(conIns, 1))
               return false;
           if(!objInvItm.ejecutaVerificacionStock(conIns, 1))
               return false; 
         }
     stbins=null;
 }catch(SQLException Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
  catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}





        
        
                            
            public void clickConsultar(){
                //clnTextos();
                if(!txtFecDoc.isFecha()){
                    txtFecDoc.setHoy();
                }            
                objDiario.setEditable(false);
               // setEditable(false);
            }
             
             public boolean consultar3() {
                return _consultar(FilSql());
             }
            
            public boolean consultar() {
              return _consultar(FilSql());
            }
            
            public void clickModificar(){
                objDiario.setEditable(true);
                setEditable(true);
                butTipDoc.setEnabled(false);
                txtNomTipDoc.setEditable(false);
                txtDetTipDoc.setEditable(false);
                txtDoc.setEditable(false);
                 txtOrdCom.setEditable(false);
                java.awt.Color colBack = txtSub.getBackground();
                txtSub.setEditable(false);
                txtSub.setBackground(colBack);
                txtIva.setEditable(false);
                txtIva.setBackground(colBack);
                txtTot.setEditable(false);
                txtTot.setBackground(colBack);
                
                
            }
            
            
            
            
             ///**********************************///**********************************************    
               
//               
//                public boolean AnularRegEliminados_respaldo(java.sql.Connection conAnu, Librerias.ZafUtil.ZafTipItm  objZafTipItm){
//                    boolean blnRes=true;
//                     try{
//                            java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
//                            if (arlAux!=null){
//                                  for (int i=0;i<arlAux.size();i++){
//                                       int intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
//                                       int intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
//                                       double intCodCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
//                                    
//                                       String strEstFisBod = objUti.getStringValueAt(arlAux, i, INT_ARR_IEBODFIS);
//                                    
//                                               
//                                       intCodCan = intCodCan * (getAccionDoc()*-1);
//                                        if(InvertirInventario(conAnu, intCodItm, intCodBod, intCodCan, objZafTipItm, strEstFisBod )){
//                                               //No actualiza grupo
//                                         }else{
//                                             return false;
//                                         }
//                                         
//                                         String sql = "SELECT a4.tx_codalt,  SUM(a2.nd_stkAct) AS nd_stkAct" +
//                                         " FROM tbm_equInv AS a1 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)" +
//                                         "  INNER JOIN tbm_inv AS a4 ON (a4.co_emp=a2.co_emp AND a4.co_itm=a2.co_itm) "+
//                                         " INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)" +
//                                         " WHERE a3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a3.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv" +
//                                         " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_itm="+intCodItm+")   group by a4.tx_codalt  ";
//                                         java.sql.ResultSet rst2;
//                                         java.sql.Statement stm2= conAnu.createStatement();   
//                                         rst2 = stm2.executeQuery(sql);
//                                         if(rst2.next()){
//                                            double douStk = rst2.getDouble("nd_stkAct");
//                                    //        System.out.println("Stock ==> "+ douStk ); 
//                                         if(douStk < 0.00) {  //objUti.getStringValueAt(arlAux, i, INT_ARR_ITMORI)
//                                              if (mensaje("Ocurrio  un problema en el inventario \n ¿Desea seguir verificando?")==javax.swing.JOptionPane.YES_OPTION){
//                                                 MensajeInf(""+ (i+1)+" .- " + rst2.getString("tx_codalt")  +"  falta  Stock  para completar el pedido"); 
//                                              }
//                                           return false;
//                                        }}
//                                       
//                                  }
//                             }
//                    blnRes=true;
//                 }
//                 catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
//                 return blnRes;   
//            }
//                  
                 

                
public boolean AnularRegEliminados(java.sql.Connection conAnu){
  boolean blnRes=true;
  int intReaActStk=0;
  int intTipMov=0;
  int intCodItm=0;
  int intCodBod=0; 
  double dblCan=0;
  String strEstFisBod="";
  java.util.ArrayList arlAux;
  try{
     arlAux=objTblMod.getDataSavedBeforeRemoveRow();
     if(arlAux!=null){
       intTipMov=getAccionDoc();
       objInvItm.inicializaObjeto();
       
       for(int i=0;i<arlAux.size();i++){
         intCodItm = objUti.getIntValueAt(arlAux, i, INT_ARR_CODITM);
         intCodBod = objUti.getIntValueAt(arlAux, i, INT_ARR_CODBOD);
         dblCan = objUti.getDoubleValueAt(arlAux, i, INT_ARR_CANMOV);
         strEstFisBod="N"; //objUti.getStringValueAt(arlAux, i, INT_ARR_IEBODFIS);
         //***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
           if(objInvItm.isItmServicio(conAnu, intCodItm)){ // no es de servicio
              objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dblCan, (intTipMov*-1), strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
              intReaActStk=1;
           }
        /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
      }
      if(intReaActStk==1){
        if(!objInvItm.ejecutaActStock(conAnu, 1))
            return false;
        if(!objInvItm.ejecutaVerificacionStock(conAnu, 1))
           return false;
      }
   }
    blnRes=true;
  }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
 return blnRes;   
}




         //********************************************************
            //**********************************************************************
        
//        public boolean InvertirInventario(java.sql.Connection conAnu, int intCodItm, int intCodBod, double intCodCan, Librerias.ZafUtil.ZafTipItm objZafTipItm, String strEstFisBod){
//            boolean blnRes=true;
//                     try{
//                           String  strActInv = "", strActInv2 = "", strOrAct= "";
//                           
//                            if(!objZafTipItm.isServicio(intCodItm)){ //********* ES ITEM DE SERVICIO 
//                                
//                                String strAux="";
//                                if(strEstFisBod.equals("N")){
//                                 if(strMerIngEgr.equals("S")){
//                                   if(strTipIngEgr.equals("I")){ 
//                                       if(intCodCan > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+intCodCan+") ";
//                                       if(intCodCan < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+intCodCan+") ";
//                                   }
//                                   if(strTipIngEgr.equals("E")){
//                                       if(intCodCan > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+intCodCan+") ";
//                                       if(intCodCan < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+intCodCan+") ";
//                                   } 
//                                 }} 
//                                
//                                                      java.sql.PreparedStatement pstUptInvBod = conAnu.prepareStatement(
//                                                       "update  TBM_INVBOD set  " +
//                                                       "     nd_stkact = nd_stkact + " + (intCodCan)  +
//                                                       " "+strAux+" "+
//                                                       " WHERE " +
//                                                       " co_emp = " + objZafParSis.getCodigoEmpresa() + "and " +
//                                                       " co_bod = " + intCodBod + " and " +
//                                                       " co_itm = " + intCodItm);
//                                                                                  
//                                                       pstUptInvBod.executeUpdate();                                                            
//                                                       strActInv  = strActInv  + strOrAct + " tbm_inv.co_itm = " + intCodItm;
//                                                       strActInv2 = strActInv2 + strOrAct + " a2.co_itm = " + intCodItm;
//                                                       strOrAct  =" or ";
//                                                         
//                                                       
//                                                       
//                                                            if(!objSisCon.actualizarInventario_2(conAnu, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), intCodItm, intCodCan, intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
//                                                            return false;
//                                                        }
//                                       
//                                              } //********  FIN DE ITEM SERVICIO
//                                         
//                            
//                           if(!strActInv.equals("")){
//                              String strUpdate = " UPDATE tbm_inv " +
//                                                 " SET nd_stkAct=b1.nd_stkAct " +
//                                                 " FROM  " +
//                                                 "( " +
//                                                 " SELECT a2.co_emp, a2.co_itm, SUM(a2.nd_stkAct) AS nd_stkAct " +
//                                                 " FROM tbm_invBod AS a2 " +
//                                                 " WHERE a2.co_emp="+objZafParSis.getCodigoEmpresa()+"   " +
//                                                 " and ( " + strActInv2 +" ) " +
//                                                 " GROUP BY a2.co_emp, a2.co_itm " +
//                                                 " ORDER BY a2.co_itm " +
//                                                 ") AS b1 " +   
//                                                 " WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm " + 
//                                                 " AND ( " + strActInv +" ) " +
//                                                 " AND tbm_inv.co_emp="+objZafParSis.getCodigoEmpresa();
//                             java.sql.PreparedStatement pstActStkInv = conAnu.prepareStatement(strUpdate);
//                              /*ecutando el insert */
//                             pstActStkInv.executeUpdate();    
//                         }   
//                         
//                      blnRes=true;
//                }
//                catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
//                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
//                return blnRes;   
//         }
//        
        
        
        
       /**
        *Esta funcion permite verificar que exista stock,
        *caso cotrario mostrara un mensaje indicando que item tiene el problema de inventario
        * @Param    conAnu : Coneccion de la base 
        * @Param    intCodItm : codigo del item 
        * @Param    strCodAlt : codigo alterno del item que se esta verificando
        *
        * @return  Retorna un valor true en caso de no tener el stock en negativo 
        *          caso contrario devuelve un valor falso.  
        */
        public boolean verificaStock(java.sql.Connection conAnu, int intCodItm, int intCodBod, String strCodAlt ){
            boolean blnRes=true;
                     try{
                                        
                           String sql = "SELECT  a.nd_stkact , b.tx_codalt  FROM tbm_invbod as a inner join tbm_inv as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) " +
                           " where a.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" and a.co_itm = (Select co_itm from tbm_equinv where co_emp= "+objZafParSis.getCodigoEmpresaGrupo()+" " +
                           " and co_itmmae IN  ( Select co_itmmae from tbm_equinv where co_emp =  "+objZafParSis.getCodigoEmpresa()+" and co_itm="+intCodItm+" ) )  " +
                           " and a.co_bod = ( SELECT co_bodgrp FROM  tbr_bodempbodgrp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_bod= "+intCodBod+" ) ";
            
                                         java.sql.ResultSet rst2;
                                         java.sql.Statement stm2= conAnu.createStatement();   
                                         rst2 = stm2.executeQuery(sql);
                                         if(rst2.next()){
                                            double douStk = rst2.getDouble("nd_stkAct");
                                   //        System.out.println("Stock ==> "+ sql ); 
                                         if(douStk < 0.00) {
                                              if (mensaje("Ocurrio  un problema en el inventario \n ¿Desea seguir verificando?")==javax.swing.JOptionPane.YES_OPTION){
                                                 MensajeInf(" " + rst2.getString("tx_codalt")  +"  falta  Stock  para completar el pedido"); 
                                              }
                                           return false;  
                                        }}
                                         rst2.close();
                                         rst2=null;
                                         stm2.close();
                                         stm2=null;
             blnRes=true;  
                }
                catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
                return blnRes;   
         }
        
        
        
              
        
        

private boolean modificaCab(java.sql.Connection conMod, String strestreg){
  boolean blnRes=false;
  java.sql.PreparedStatement pstModMovInv;
  String strFecSis="";
  String strFecha="";
  String strSqlAux="",strSql="";
  try{
     strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
     int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
     strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
       
    if(txtPrvCod.getText().equals("")) strSqlAux =" co_cli=null "; 
     else strSqlAux =" co_cli="+txtPrvCod.getText()+" "; 

    strSqlAux += ",tx_nomCli='"+txtPrvNom.getText()+"'";
    strSqlAux += ",tx_dirCli='"+txtPrvDir.getText()+"',";

 

    strSql="UPDATE tbm_cabMovInv SET "+strSqlAux+" "+ 
    " fe_doc='"+strFecha+"', ne_numDoc="+txtOrdCom.getText()+", "+ 
    " tx_obs1='"+txaObs1.getText()+"', tx_obs2='"+txaObs2.getText()+"', "+ 
    " nd_sub="+(objUti.redondeo(Double.parseDouble(txtSub.getText()),6)*getAccionDoc())+", "+
    " nd_porIva="+dblPorIva+", fe_ultMod='"+strFecSis+"', " +
    " co_usrMod="+objZafParSis.getCodigoUsuario()+" ,st_regrep='"+strestreg+"' "+
    " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND "+  
    " co_loc="+objZafParSis.getCodigoLocal()+ " AND co_tipDoc="+txtCodTipDoc.getText()+ " " +
    " AND co_doc="+txtDoc.getText(); 
    pstModMovInv = conMod.prepareStatement(strSql);    
    pstModMovInv.executeUpdate(); /* Ejecutando el Update */
  blnRes=true;

 }catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }                
return blnRes;
}

            
            
            
            
            

//********************************************************
public boolean AnularRegdeDetalle(java.sql.Connection conMod){
 boolean blnRes=false;
 int intReaActStk=0;
 int intTipMov=1;
 int intCodItmOri=0;
 int intCodBodOri=0;
 int intCodItm2=0;
 int intCodBod2=0;
 double dblCanOri=0;
 double dblCan=0;
 String strNomItem="";
 String strEstFisBod="";
 try{  
    objInvItm.inicializaObjeto();
    intTipMov=getAccionDoc();
         
   for(int i=0; i<tblDat.getRowCount();i++){
    if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){

      intCodItmOri =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_ITMORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_ITMORI).toString()));
      intCodBodOri =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_BODORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_BODORI).toString())); 
      dblCanOri = Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_CANORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANORI).toString()));   

      intCodItm2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
      intCodBod2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
      strNomItem =  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString());

      dblCan=Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANMOV).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
      strEstFisBod="N"; //(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
         
      if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("N")){
        /**/ System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION >> ");
        /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
           if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
              objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCan, intTipMov, strEstFisBod, strMerIngEgr, strTipIngEgr, 1  );
              intReaActStk=1;
           }
        /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
      }else{
             if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("E")){
                  System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO >> ");    
             }
             if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                 if(intCodItmOri!=intCodItm2){
                   //   System.out.println("** REALIZA DEVOLUCION DEL ITEM ORIGEN >> "+intCodItmOri+ " CANTIDAD "+ dblCanOri ); 
                    if(objInvItm.isItmServicio(conMod, intCodItmOri)){ // no es de servicio.
                         objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItmOri, intCodBodOri, dblCanOri, (intTipMov*-1),  strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                         intReaActStk=1;
                     }
                    //  System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION 2 >> ");  
                    /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                    if(objInvItm.isItmServicio(conMod, intCodItm2)){ // no es de servicio.
                        objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCan, intTipMov,  strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                        intReaActStk=1;
                    }
                   /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                }else if(intCodBodOri!=intCodBod2){
                   //   System.out.println("** REALIZA DEVOLUCION DEL INVENTARIO DEL ITEM ORIGEN >> "+intCodItmOri+ " CANTIDAD "+ dblCanOri ); 
                   if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                       objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItmOri, intCodBodOri, dblCanOri, (intTipMov*-1), strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                        intReaActStk=1;
                   }
                   /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
                   //  System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION 2 >> ");  
                   if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                      objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCan, intTipMov,  strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                      intReaActStk=1;
                   }
                   /*************************  FIN DE VERIFICA ITEM  DE SERVICIO *******************************************************/
               }else if(dblCanOri!=dblCan){
                if(dblCan > dblCanOri){ 
                     double dblCanDif = dblCan-dblCanOri;
                     dblCanDif=Math.abs(dblCanDif);
                      // System.out.println("** REALIZA AUMENTO EN EL INVENTARIO AL ITEM >> "+ dblCanDif);
                      /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                      if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                          objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCanDif, intTipMov,  strEstFisBod, strMerIngEgr, strTipIngEgr, 1 );
                          intReaActStk=1;
                       }
                 }
                 if(dblCan < dblCanOri){  
                    double dblCanDif = dblCanOri-dblCan;
                    dblCanDif=Math.abs(dblCanDif);
                    //System.out.println("**  REALIZA DISMINUCION EN INVENTARIO  AL ITEM >> "+ dblCanDif);
                    /***********   VERFIFICA SI EL ITEM ES DE SERVICIO  ************************/
                    if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){ // no es de servicio.
                         objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm2, intCodBod2, dblCanDif, (intTipMov*-1),  strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
                         intReaActStk=1;
                    } 
                 }
              }else  System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO 2 >> ");    
          }
       }
     }}
     blnRes=true;     
    if(intReaActStk==1){
       if(!objInvItm.ejecutaActStock(conMod, 1))
           blnRes=false;
      if(!objInvItm.ejecutaVerificacionStock(conMod, 1))
           blnRes=false;
    }     
   
 }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
 return blnRes;   
}

//*************************************************************************    

                 
//            
//               //********************************************************
//                  public boolean AnularRegdeDetalle_respaldo(java.sql.Connection conMod, Librerias.ZafUtil.ZafTipItm  objZafTipItm){
//                    boolean blnRes=true;
//                     try{  
//                         for(int i=0; i<tblDat.getRowCount();i++){
//                           if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
//                             
//                             int intCodItm =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_ITMORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_ITMORI).toString()));
//                             int intCodBod =   Integer.parseInt(((tblDat.getValueAt(i, INT_TBL_BODORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_BODORI).toString())); 
//                             double intCodCan = Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_CANORI)==null)?"0":tblDat.getValueAt(i, INT_TBL_CANORI).toString()));   
//                              
//                             int intCodItm2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
//                             int intCodBod2 =   Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODBOD).toString()));
//                             double intCodCan2 = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANMOV).toString()));
//                             String strNomItem =  ((tblDat.getValueAt(i, INT_TBL_DESITM)==null || tblDat.getValueAt(i, INT_TBL_DESITM).toString().equals(""))?"0":tblDat.getValueAt(i, INT_TBL_DESITM).toString());
//                             
//                             String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
//                        
//                             
//                           if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("N")){
//                              /**/ System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION >> ");
//                                       if(IngresarInventario(conMod, intCodItm2, intCodBod2, intCodCan2, objZafTipItm, strNomItem, strEstFisBod )){
//                                                blnRes=true;
//                                       }else{
//                                           return false;
//                                        }
//                               
//                           
//                           }else {
//                                  if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("E")){
//                                       System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO >> ");    
//                                  }
//                                  if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
//                                   
//                                          if(intCodItm!=intCodItm2){
//                                          /**/   System.out.println("** REALIZA DEVOLUCION DEL INVENTARIO DEL ITEM ORIGEN >> "+intCodItm+ " CANTIDAD "+ intCodCan ); 
//                                             if(InvertirInventario(conMod, intCodItm, intCodBod, (intCodCan * (getAccionDoc()*-1)), objZafTipItm, strEstFisBod )){
//                                                blnRes=true;
//                                            }else{ return false;  }
//                                          
//                                          
//                                                if(!verificaStock(conMod, intCodItm, intCodBod , tblDat.getValueAt(i, INT_TBL_ITMALT).toString() ))
//                                                      return false;
//                                           
//                                                  
//                                          /**/  System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION 2 >> ");  
//                                            if(IngresarInventario(conMod, intCodItm2, intCodBod2, intCodCan2, objZafTipItm, strNomItem, strEstFisBod )){
//                                               blnRes=true;
//                                            }else{ return false;  }
//                                        
//                                              
//                                              
//                                          }
//                                          
//                                          
//                                           if(intCodBod!=intCodBod2){
//                                          /**/   System.out.println("** REALIZA DEVOLUCION DEL INVENTARIO DEL ITEM ORIGEN >> "+intCodItm+ " CANTIDAD "+ intCodCan ); 
//                                             if(InvertirInventario(conMod, intCodItm, intCodBod, (intCodCan * (getAccionDoc()*-1)), objZafTipItm, strEstFisBod )){
//                                                blnRes=true;
//                                            }else{ return false;  }
//                                              
//                                          
//                                               if(!verificaStock(conMod, intCodItm, intCodBod , tblDat.getValueAt(i, INT_TBL_ITMALT).toString() ))
//                                                      return false;
//                                          
//                                              
//                                          /**/  System.out.println("** ELEMENTO NUEVO HACE MOVIMIENDO EN LA MODIFICACION 2 >> ");  
//                                            if(IngresarInventario(conMod, intCodItm2, intCodBod2, intCodCan2, objZafTipItm, strNomItem, strEstFisBod )){
//                                               blnRes=true;
//                                            }else{ return false;  }
//                                          }
//                                          
//                                          
//                                          else if(intCodCan!=intCodCan2){
//                                              
//                                             if(intCodCan < intCodCan2){ 
//                                                 double dblDif = intCodCan-intCodCan2;
//                                                 dblDif=Math.abs(dblDif);
//                                                 /**/ System.out.println("** REALIZA AUMENTO EN EL INVENTARIO AL ITEM >> "+ dblDif);
//                                               // if(InvertirInventario(conMod, intCodItm, intCodBod, intCodCan, objZafTipItm)){
//                                               //     blnRes=true;
//                                               // }else{ return false;   }
//                                                 if(IngresarInventario(conMod, intCodItm2, intCodBod2, dblDif, objZafTipItm, strNomItem, strEstFisBod )){
//                                                    blnRes=true;
//                                                }else{ return false;  }
//                                             
//                                             }
//                                             if(intCodCan > intCodCan2) {  
//                                                  double dblDif = intCodCan-intCodCan2;
//                                                  dblDif=Math.abs(dblDif);
//                                                 /**/System.out.println("**  REALIZA DISMINUCION EN INVENTARIO  AL ITEM >> "+ dblDif);
//                                                   if(InvertirInventario(conMod, intCodItm, intCodBod, (dblDif*(getAccionDoc()*-1)), objZafTipItm, strEstFisBod)){
//                                                      blnRes=true;
//                                                   }else{ return false;   }
//                                                 
//                                                 
//                                                  if(!verificaStock(conMod, intCodItm, intCodBod, tblDat.getValueAt(i, INT_TBL_ITMALT).toString() ))
//                                                      return false;
//                                                 
//                                                 
//                                                  // if(IngresarInventario(conMod, intCodItm2, intCodBod2, intCodCan2, objZafTipItm, strNomItem)){
//                                                  //    blnRes=true;
//                                                  // }else{ return false;  }
//                                             }
//
//                                                   
//                                     
//                                       }else 
//                                           System.out.println("** NO REALIZA MOVIMIENTO MODIFICADO 2 >> ");    
//                                  
//                                      
//                                  }
//                                 
//                               }
//                     }}
//                   blnRes=true;
//                }
//                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
//                return blnRes;   
//         }
//                
//        //*************************************************************************    
//            
                    
                  
                  
         private boolean verificaItmUni(){
            boolean  blnRes=true;
             try{
             for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                       int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                       int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));
                       if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                           if(intCodItm != intCodItmAct){
                               MensajeInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                               blnRes=false;
                               break;
                           }
                       }
               }}
             }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
           return blnRes;     
         }
                  
          
         
          private boolean verificaItmUniAnu(){
            boolean  blnRes=true;
             try{
             for(int i=0; i<tblDat.getRowCount();i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM) != null){
                       int intCodItm    = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITM).toString()));
                       int intCodItmAct = Integer.parseInt((tblDat.getValueAt(i, INT_TBL_CODITMACT).toString()));
                     //  if(tblDat.getValueAt(i, INT_TBL_ESTADO).equals("M")){
                           if(intCodItm != intCodItmAct){
                               MensajeInf("El Item "+tblDat.getValueAt(i, INT_TBL_ITMALT).toString()+ " esta Unificado \n No es posible realizar cambio." );
                               blnRes=false;
                               break;
                           }
                     //  }
               }}
             }catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }      
           return blnRes;     
         }
                    
        //**********************************************************************
//         public boolean IngresarInventario(java.sql.Connection conIns, int intCodItm, int intCodBod, double intCodCan, Librerias.ZafUtil.ZafTipItm objZafTipItm, String strNomItem, String strEstFisBod){
//            boolean blnRes=true;
//                     try{
//                          if(!objZafTipItm.isServicio(intCodItm)){ 
//                        
//                           
//                             objZafInv.movInventario(intCodItm, intCodBod, getAccionDoc(), intCodCan, conIns, "", "", "");
//                                 
//                                              if(!objSisCon.actualizarInventario_2(conIns, 
//                                                                                objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(),
//                                                                                intCodItm , (intCodCan * getAccionDoc()), intCodBod, strEstFisBod, strMerIngEgr, strTipIngEgr )){
//                                                    
//                                                     return false;
//                                             }
//                                        
//                                       
//                         
//                                         if(!verificaStock(conIns, intCodItm, intCodBod, " H " ))
//                                                   return false;
//                                         
//                             
//                           
//                             
//                                }
//                      blnRes=true;
//                 }
//                catch (Exception e) { blnRes=false; objUti.mostrarMsgErr_F1(this, e); }                
//                return blnRes;   
//            }

//**********************************************************************

private boolean InvertirDet(java.sql.Connection conMod, String strEstReg){
  boolean blnRes=true;
  String strSQL="";
  StringBuffer stbins=new StringBuffer(); //VARIABLE TIPO BUFFER
  java.sql.Statement stmDet;
  int intControl=0;
  try{
     stmDet=conMod.createStatement();                                                                            
    //********** ELIMINA EL DETALLLE 
       strSQL=" DELETE FROM tbm_detMovInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
       " AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_tipDoc="+txtCodTipDoc.getText()+" " +
       " AND co_doc="+txtDoc.getText();
       stmDet.executeUpdate(strSQL);
    //********** INSERTA EL DETALLLE 
     GLO_strArreItm="";
     for(int i=0; i<tblDat.getRowCount(); i++){ 
      if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 

        if(intControl!=0)    
         GLO_strArreItm=GLO_strArreItm+",";

         GLO_strArreItm=GLO_strArreItm+tblDat.getValueAt(i,INT_TBL_CODITM).toString();
         intControl++;

         if (i>0)
         stbins.append(" UNION ALL ");
         stbins.append("SELECT "+objZafParSis.getCodigoEmpresa()+","+objZafParSis.getCodigoLocal()+","+Integer.parseInt(txtCodTipDoc.getText())+","+intNumDoc+","+(i+1)+","+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITM))+", " +
         ""+Integer.parseInt(""+tblDat.getValueAt(i,INT_TBL_CODITMACT))+",'"+tblDat.getValueAt(i, INT_TBL_ITMALT)+"','"+tblDat.getValueAt(i, INT_TBL_DESITM)+"','"+tblDat.getValueAt(i, INT_TBL_UNIDAD)+"',"+Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD))+", " +
         ""+(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)) * getAccionDoc()) +","+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
         ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", "+objUti.redondeo(Double.parseDouble( (tblDat.getValueAt(i,INT_TBL_PRECOS)==null)?"0":tblDat.getValueAt(i, INT_TBL_PRECOS).toString()),objZafParSis.getDecimalesBaseDatos())+", " +
         ""+objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_PORDES)==null)?"0":tblDat.getValueAt(i,INT_TBL_PORDES).toString()),2) + ",'" +((tblDat.getValueAt(i, INT_TBL_BLNIVA).toString().equals("true"))?"S":"N") + "','P'," +
         ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() ) +","+  (objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2) * getAccionDoc() )+", " +
         ""+(objUti.redondear(Double.parseDouble( (tblDat.getValueAt(i, INT_TBL_TOTAL)==null)?"0":tblDat.getValueAt(i,INT_TBL_TOTAL).toString()),2)  * getAccionDoc() ) + ",'"+strEstReg+"'");
   }}

    if(intControl > 0 ){
        strSQL = " INSERT INTO  tbm_detMovInv " +
        " (co_emp, co_loc, co_tipdoc , co_doc, co_reg, co_itm, co_itmact,  tx_codAlt, tx_nomItm, tx_unimed,co_bod, nd_can, nd_cosUni, " +
        "  nd_cosUniGrp, nd_preUni,  nd_porDes, st_ivaCom,st_reg,nd_costot,nd_costotgrp, nd_tot ,st_regrep ) "+stbins.toString();
        stmDet.executeUpdate(strSQL);
        stmDet.close();
        stmDet=null;
    }else return false;

  blnRes=true;
 }catch (java.sql.SQLException e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }                
 return blnRes;
}


              
              
              
              
              
              
/********************************************************************************/      
public boolean actualizarReg(){
 boolean blnRes = false;
 java.sql.Connection conMod; 
 try{
    conMod =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    if(conMod!=null){
       conMod.setAutoCommit(false);

       if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  

       if(modificaCab(conMod,STR_ESTREG)){
        if(AnularRegEliminados(conMod)){
         if(AnularRegdeDetalle(conMod)){
          if(InvertirDet(conMod, STR_ESTREG)){
           if(objDiario.actualizarDiario(conMod, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()),txtOrdCom.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A")){
              blnRes = true;
              conMod.commit();
              conMod.setAutoCommit(true);  
              objTblMod.clearDataSavedBeforeRemoveRow();
              objTblMod.initRowsState();   
            }else conMod.rollback(); 
           }else conMod.rollback();  
          }else conMod.rollback();  
         }else conMod.rollback();  
      }else conMod.rollback();  
   
     if(blnRes){
         for(int i=0; i<tblDat.getRowCount(); i++){ 
           if(tblDat.getValueAt(i,INT_TBL_CODITM) != null ){ 
               tblDat.setValueAt("E", i, INT_TBL_ESTADO);
               tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODITM).toString() , i, INT_TBL_ITMORI);
               tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CODBOD).toString() , i, INT_TBL_BODORI);
               tblDat.setValueAt( tblDat.getValueAt(i, INT_TBL_CANMOV).toString() , i, INT_TBL_CANORI);
     }}}

    conMod.close();
    conMod=null;
 }}catch(SQLException Evt){  objUti.mostrarMsgErr_F1(jfrThis, Evt);  blnRes = false;  }
   catch(Exception Evt){ objUti.mostrarMsgErr_F1(jfrThis, Evt); blnRes = false;  } 
  return blnRes;      
}
/*****************************************************************************************/   
            
            
            
            
            public void clickEliminar(){
                objDiario.setEditable(false);
                setEditable(false);
            }
            
    
            
            
            
private boolean eliminarReg(){
 boolean blnRes=false;   
 java.sql.Connection conAnu;   
 try{
    conAnu =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                
    if(conAnu!=null){
       conAnu.setAutoCommit(false);                                                                                         
       
      if(!isAnulada()){  
          if(anueliCab(conAnu, "E")){
           if(anueliDet(conAnu)){ 
            if(objDiario.eliminarDiario(conAnu,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
              conAnu.commit();
              blnRes=true;
            }else conAnu.rollback();
           }else conAnu.rollback();
          }else conAnu.rollback();  
      }else{
            if(anueliCab(conAnu, "E")){
             if(objDiario.eliminarDiario(conAnu,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
               conAnu.commit();
               blnRes=true;
             }else conAnu.rollback();
            }else conAnu.rollback();
      }     
     conAnu.close();    
     conAnu=null;     
   }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
     catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
  return blnRes;
 }         
       
                                 
                                     
                             

            
            
//              public boolean eliminarReg_respaldo(){
//                  
//                           try{
//                              java.sql.Connection conAnu =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                
//                              java.util.ArrayList arlActInv=new java.util.ArrayList();
//                              String strMsgError="";
//                                    try{//odbc,usuario,password
//                                
//                                     if (conAnu!=null){
//                                        java.sql.PreparedStatement pstAnuMovInv;
//                                        conAnu.setAutoCommit(false);                                                                                         
//                                       
//                                     String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
//      
//                                        /*********************************************\    
//                                         * Armando el Update para ANULAR             *   
//                                         * la Orden de Compra                        *
//                                        \*********************************************/
//                                     
//                                     if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  
//                                     
//                                         pstAnuMovInv = conAnu.prepareStatement(   
//                                               "Update tbm_cabMovInv set " +
//                                               " st_reg  = '"  + "E" + "' " + 
//                                                " ,st_regrep  = '"+STR_ESTREG+"' " + 
//                                               " ,fe_ultMod   = '" + strFecSis+"', " +
//                                               " co_usrMod   = "+ objZafParSis.getCodigoUsuario() + 
//                                               "  where " +
//                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
//                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
//                                               " co_doc = " +  txtDoc.getText()               + " and "+  
//                                               " co_tipDoc = " + txtCodTipDoc.getText()
//                                               );
//                                        /* Ejecutando el Update */
//                                          pstAnuMovInv.executeUpdate();
//                                           
//                                           if (!isAnulada()){
//                                      objTblMod.removeEmptyRows();
//                                        for (int i=0;i<objTblMod.getRowCountTrue();i++){  
//                                             if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){
//                                               
//                                                arlActInv.add(tblDat.getValueAt(i, INT_TBL_CODITM).toString());
//                                                 
//                                                  boolean blnEstado = false;  
//                                                    blnEstado  = objZafInv.movInventario_nuevo(conAnu, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString()),Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString()), Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)),getAccionDoc()*-1, "", "", "");
//                                                 
//                                                         
//                                                   if(blnEstado==true)  {  
//                          //*********************************
//                        int codbod = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
//                        int coditm = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM));
//
//                        String  sql = "SELECT  d3.nd_stkAct,d1.co_itmmae"+
//                       " FROM( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.co_uni, a1.st_ivacom"+
//                       " FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
//                       " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+""+
//                       " ) AS d1 INNER JOIN( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1"+
//                       " INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)"+
//                       " WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae) "+
//                       " INNER JOIN(SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct FROM tbm_equInv AS c1"+
//                       " INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)"+
//                       " INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)"+
//                       " WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae"+
//                       " ) AS d3 ON (d1.co_itmMae=d3.co_itmMae) LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg)"+
//                       " where  d1.co_itm="+coditm;
//                        
//                        
//                        java.sql.ResultSet rst2;
//                        java.sql.Statement stm2= conAnu.createStatement();   
//                        rst2 = stm2.executeQuery(sql);
//                           if(rst2.next()){
//                           double douStk = rst2.getDouble(1);
//                           
//                           if(douStk < 0.00) {
//                              // System.out.println("Stock ==> "+ douStk ); 
//                                if (strMsgError.equals("")){                                                            
//                                        if (mensaje("Ocurrio un problema en el inventario \n ¿Desea seguir verificando?")!=javax.swing.JOptionPane.YES_OPTION){
//                                               MensajeInf(""+ (i+1)+" .- " + tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +"  falta  Stock  para completar el pedido.");  //<FONT COLOR=\"blue\">" + douStk*-1 + "</font>
//                                               conAnu.rollback();
//                                               conAnu.close();
//                                               return false;
//                                          }                                                                
//                                }
//                                 strMsgError += "\n "+ (i+1)+" .- "+ tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +"  falta  Stock  para completar el pedido.";
//                            }
//                          }                    
//                           //*********************************
//                                 }
//                                                     if(blnEstado==false)  { 
//                                                
//                                                   double dblFalta = objSisCon.getStockConsolidado()-java.lang.Math.abs(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)));                                                    
//                                                        if (strMsgError.equals("")){                                                            
//                                                            if (mensaje("Ocurrio un problema en el inventario \n ¿Desea seguir verificando?")!=javax.swing.JOptionPane.YES_OPTION){
//                                                                MensajeInf("\n "+ (i+1)+" .- " + tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +"  falta  Stock  para completar el pedido."); 
//                                                                conAnu.rollback();
//                                                                conAnu.close();
//                                                                return false;
//                                                            }                                                                
//                                                        }
//                                                        strMsgError += "\n "+ (i+1)+" .- "+ tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +" falta  Stock  para completar el pedido.";
//                                                }        
//                                            }                                            
//                                        }
//                                        if (arlActInv.size()>0){
//                                            if (!objZafInv.UpdateInventarioMaestro(conAnu, objZafParSis.getCodigoEmpresa(), arlActInv)){
//                                                conAnu.rollback();
//                                                return false;                                                
//                                            }
//                                        }
//                                        arlActInv=null;
//                                       if (!strMsgError.equals("")){
//                                            MensajeInf("Los sgts items tuvieron problemas:" + strMsgError);
//                                            conAnu.rollback();
//                                            conAnu.close();
//                                            return false;
//                                        }
//                                    }                 
//                                       
//                                     if (!objDiario.eliminarDiario(conAnu,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
//                                            conAnu.rollback();
//                                            conAnu.close();
//                                            return false;
//                                        }
//                                        conAnu.commit();
//                                        conAnu.setAutoCommit(true);                                         
//                                        conAnu.close();
//                                    }
//                                 }              
//                               catch(SQLException Evt)
//                               {
//                                      try{
//                                              conAnu.rollback();
//                                              conAnu.close();
//                                          }catch(SQLException Evts){
//                                              objUti.mostrarMsgErr_F1(jfrThis, Evts);
//                                          }
//                                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                          return false;
//                                }
//
//                                catch(Exception Evt)
//                                {
//                                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                      return false;
//                                }
//                           }
//                           catch(SQLException Evt)
//                           {
//                                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                  return false;
//                            }       
//                return true;
//            }    
//            
            
              
            public void clickAnular(){
                objDiario.setEditable(false);
                setEditable(false);
            }
            
            
            

private boolean anueliCab(java.sql.Connection conn, String strEst){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  String strFecSis="";
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();
        
        strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
      
        if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  
                                     
        strSql="UPDATE tbm_cabMovInv SET st_reg='"+strEst+"', st_regrep='"+STR_ESTREG+"' "+
        " ,fe_ultMod='"+strFecSis+"', co_usrMod="+objZafParSis.getCodigoUsuario()+ 
        " WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+"" +
        " AND co_doc="+txtDoc.getText()+" AND co_tipDoc="+txtCodTipDoc.getText();
        stmLoc.executeUpdate(strSql);                                       
                                         
        stmLoc.close();
        stmLoc=null;
        blnRes=true;
  }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  return blnRes;
}            
            
          



private boolean anueliDet(java.sql.Connection conn){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strEstFisBod="";
  int intReaActStk=0;
  int intTipMov=0;
  int intCodItm=0;
  int intCodBod=0;
  double dblCan=0;
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();
        
        intTipMov=getAccionDoc();
        objInvItm.inicializaObjeto();
       
        String strAux2 =" , CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1)) IN ( " +
        " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
        " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
        " THEN 'S' ELSE 'N' END AS proconf  ";
          
        strSql="SELECT a.co_itmact, a.co_bod,  abs(a.nd_can) as ndcan FROM tbm_detMovInv AS a " +
        " INNER JOIN tbm_inv AS a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) "+        
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+"" +
        " AND a.co_doc="+txtDoc.getText()+" AND a.co_tipDoc="+txtCodTipDoc.getText()+" AND a1.st_ser='N' ";
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){
            intCodItm =rstLoc.getInt("co_itmact");
            intCodBod =rstLoc.getInt("co_bod");
            dblCan=rstLoc.getDouble("ndcan");
            strEstFisBod="N";
            
            objInvItm.generaQueryStock(objZafParSis.getCodigoEmpresa(), intCodItm, intCodBod, dblCan, (intTipMov*-1), strEstFisBod, strMerIngEgr, strTipIngEgr, -1 );
            intReaActStk=1;
        }    
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
 
      if(intReaActStk==1){
        if(!objInvItm.ejecutaActStock(conn, 1))
            return false;
        if(!objInvItm.ejecutaVerificacionStock(conn, 1))
           return false;
      }
     blnRes=true;
  }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); }
  return blnRes;
}            
                        
            

 

            
private boolean anularReg(){
  boolean blnRes=false; 
  java.sql.Connection conAnu;
  try{
     conAnu =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                
     if(conAnu!=null){
        conAnu.setAutoCommit(false);     
         
        if(anueliCab(conAnu, "I")){
         if(anueliDet(conAnu)){ 
          if(objDiario.anularDiario(conAnu,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText()))){
             conAnu.commit();
             blnRes=true;
         }else conAnu.rollback();
        }else conAnu.rollback(); 
       }else conAnu.rollback();   
     conAnu.close();    
     conAnu=null;     
   }}catch(SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
     catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(jfrThis, Evt); } 
  return blnRes;
 } 
                                           
                      
            
            
            
            
//            
//            public boolean anularReg_repaldo(){
//                           try{
//                              java.sql.Connection conAnu =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());                
//                              java.util.ArrayList arlActInv=new java.util.ArrayList();
//                              String strMsgError="";
//                                    try{//odbc,usuario,password
//                                
//                                     if (conAnu!=null){
//                                        java.sql.PreparedStatement pstAnuMovInv;
//                                        conAnu.setAutoCommit(false);                                                                                         
//                                       
//                                        String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
//      
//                                        /*********************************************\    
//                                         * Armando el Update para ANULAR             *   
//                                         * la Orden de Compra                        *
//                                        \*********************************************/
//                                     
//                                      if(STR_ESTREG.equalsIgnoreCase("P")) STR_ESTREG="M";  
//                                     
//                                         pstAnuMovInv = conAnu.prepareStatement(   
//                                               "Update tbm_cabMovInv set " +
//                                               " st_reg  = '"  + "I" + "' " + 
//                                               " ,st_regrep  = '"+STR_ESTREG+"' " + 
//                                               " ,fe_ultMod   = '" + strFecSis+"', " +
//                                               " co_usrMod   = "+ objZafParSis.getCodigoUsuario() + 
//                                               "  where " +
//                                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
//                                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
//                                               " co_doc = " +  txtDoc.getText()               + " and "+  
//                                               " co_tipDoc = " + txtCodTipDoc.getText()
//                                               );
//                                        /* Ejecutando el Update */
//                                          pstAnuMovInv.executeUpdate();
//                                           
//                                          
//                                      objTblMod.removeEmptyRows();
//                                        for (int i=0;i<objTblMod.getRowCountTrue();i++){  
//                                             if(tblDat.getValueAt(i, INT_TBL_ITMSER).toString().trim().equalsIgnoreCase("N")){
//                                               
//                                                arlActInv.add(tblDat.getValueAt(i, INT_TBL_CODITM).toString());
//                        
//                                                String strEstFisBod=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
//                         
//                                                
//                                                  boolean blnEstado = false;  
//                                                    blnEstado  = objZafInv.movInventario_nuevo(conAnu, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(), Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString()),Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODBOD).toString()), Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)),getAccionDoc()*-1, strEstFisBod, strMerIngEgr, strTipIngEgr );
//                                                 
//                                                         
//                                                   if(blnEstado==true)  {  
//                          //*********************************
//                        int codbod = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODBOD));
//                        int coditm = Integer.parseInt(""+tblDat.getValueAt(i, INT_TBL_CODITM));
////                        String sql  = "select nd_stkact from tbm_invBod"+ 
////                          " where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_bod ="+codbod+"  and co_itm ="+coditm;            
////                      
//                          String  sql = "SELECT  d3.nd_stkAct,d1.co_itmmae"+
//                       " FROM( SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.co_uni, a1.st_ivacom"+
//                       " FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)"+
//                       " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+""+
//                       " ) AS d1 INNER JOIN( SELECT b2.co_itmMae, b1.nd_cosUni FROM tbm_inv AS b1"+
//                       " INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)"+
//                       " WHERE b1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" ) AS d2 ON (d1.co_itmMae=d2.co_itmMae) "+
//                       " INNER JOIN(SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct FROM tbm_equInv AS c1"+
//                       " INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)"+
//                       " INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)"+
//                       " WHERE c3.co_emp="+objZafParSis.getCodigoEmpresa()+" AND c3.co_loc="+objZafParSis.getCodigoLocal()+" GROUP BY c1.co_itmMae"+
//                       " ) AS d3 ON (d1.co_itmMae=d3.co_itmMae) LEFT OUTER JOIN tbm_var AS d4 ON (d1.co_uni=d4.co_reg)"+
//                       " where  d1.co_itm="+coditm;
//                        
//                        
//                        java.sql.ResultSet rst2;
//                        java.sql.Statement stm2= conAnu.createStatement();   
//                        rst2 = stm2.executeQuery(sql);
//                           if(rst2.next()){
//                           double douStk = rst2.getDouble(1);
//                           
//                           if(douStk < 0.00) {
//                              // System.out.println("Stock ==> "+ douStk ); 
//                                if (strMsgError.equals("")){                                                            
//                                        if (mensaje("Ocurrio un problema en el inventario \n ¿Desea seguir verificando?")!=javax.swing.JOptionPane.YES_OPTION){
//                                               MensajeInf(""+ (i+1)+" .- " + tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +"  falta  Stock  para completar el pedido.");  //<FONT COLOR=\"blue\">" + douStk*-1 + "</font>
//                                               conAnu.rollback();
//                                               conAnu.close();
//                                               return false;
//                                          }                                                                
//                                }
//                                 strMsgError += "\n "+ (i+1)+" .- "+ tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +"  falta  Stock  para completar el pedido.";
//                            }
//                          }                    
//                           //*********************************
//                                 }
//                                                     if(blnEstado==false)  { 
//                                                
//                                                   double dblFalta = objSisCon.getStockConsolidado()-java.lang.Math.abs(Double.parseDouble(""+tblDat.getValueAt(i, INT_TBL_CANMOV)));                                                    
//                                                        if (strMsgError.equals("")){                                                            
//                                                            if (mensaje("Ocurrio un problema en el inventario \n ¿Desea seguir verificando?")!=javax.swing.JOptionPane.YES_OPTION){
//                                                                MensajeInf("\n "+ (i+1)+" .- " + tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +"  falta  Stock  para completar el pedido."); 
//                                                                conAnu.rollback();
//                                                                conAnu.close();
//                                                                return false;
//                                                            }                                                                
//                                                        }
//                                                        strMsgError += "\n "+ (i+1)+" .- "+ tblDat.getValueAt(i, INT_TBL_ITMALT).toString() +" falta  Stock  para completar el pedido.";
//                                                }        
//                                            }                                            
//                                        }
//                                        if (arlActInv.size()>0){
//                                            if (!objZafInv.UpdateInventarioMaestro(conAnu, objZafParSis.getCodigoEmpresa(), arlActInv)){
//                                                conAnu.rollback();
//                                                return false;                                                
//                                            }
//                                        }
//                                        arlActInv=null;
//                                       if (!strMsgError.equals("")){
//                                            MensajeInf("Los sgts items tuvieron problemas:" + strMsgError);
//                                            conAnu.rollback();
//                                            conAnu.close();
//                                            return false;
//                                        }
//                                                    
//                                        if (!objDiario.anularDiario(conAnu,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),Integer.parseInt(txtDoc.getText())))
//                                        {
//                                            conAnu.rollback();
//                                            conAnu.close();
//                                            return false;
//                                        }
//                                        conAnu.commit();
//                                        conAnu.setAutoCommit(true);                                         
//                                        conAnu.close();
//                                    }
//                                 }              
//                               catch(SQLException Evt)
//                               {
//                                      try{
//                                              conAnu.rollback();
//                                              conAnu.close();
//                                          }catch(SQLException Evts){
//                                              objUti.mostrarMsgErr_F1(jfrThis, Evts);
//                                          }
//                                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                          return false;
//                                }
//
//                                catch(Exception Evt)
//                                {
//                                      objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                      return false;
//                                }
//                           }
//                           catch(SQLException Evt)
//                           {
//                                  objUti.mostrarMsgErr_F1(jfrThis, Evt);
//                                  return false;
//                            }       
//                return true;
//            }    
//                
//            
            
            
            public void clickImprimir(){
            }
            public void clickVisPreliminar(){
            }
            public void clickAceptar(){

            }
            public void clickCancelar(){

            }
            private String FilSql() {
                String sqlFiltro = "";
//                if(txtCodTipDoc.getText().equals("")){
//                    if (objTipDoc.DocumentoPredeterminado()){
//                        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
//                        txtNomTipDoc.setText(objTipDoc.gettx_descor());
//                        txtDetTipDoc.setText(objTipDoc.gettx_deslar());
//                    }
//                        
//                }
                //Agregando filtro por Codigo Tipo de Documento
               if(!txtCodTipDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_tipdoc = " + txtCodTipDoc.getText()+ "";

                //Agregando filtro por Numero de Documento
                if(!txtDoc.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.co_doc = " + txtDoc.getText()+ "";

                //Agregando filtro por Numero de Documento
                if(!txtOrdCom.getText().equals(""))
                    sqlFiltro = sqlFiltro + " and CabMovInv.ne_numdoc = " + txtOrdCom.getText()+ "";

                
                //Agregando filtro por Fecha
                if(txtFecDoc.isFecha()){
                    int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
                    sqlFiltro = sqlFiltro + " and CabMovInv.fe_doc = '" +  strFecSql + "'";
                }    
              return sqlFiltro ;
            }

            private int Mensaje(){
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="¿Desea guardar los cambios efectuados a éste registro?\n";
                strMsg+="Si no guarda los cambios perderá toda la información que no haya guardado.";
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();                
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
            }
            
            private void MensajeValidaCampo(String strNomCampo){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
             private int mensaje(String strMsg){
                javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                String strTit="Sistema Zafiro";        
                return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
            }     
            private void MsgError(String strMsg){
                javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                String strTit="Sistema Zafiro";        
                oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);                
            }                 
            private boolean _consultar(String strFil){
                   strFiltro= strFil;
                   try{//odbc,usuario,password
                            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                            if (conCab!=null){

                                //stmCab=conCab.createStatement();
                                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                                
                                 /*
                                 * Agregando el Sql de Consulta para la Documento
                                 */
                               sSQL= "SELECT CabMovInv.co_emp,CabMovInv.co_loc,CabMovInv.co_tipdoc, co_doc " +                                                                            
                                      " FROM tbm_cabMovInv as CabMovInv left outer join tbm_cabTipDoc as Doc on (cabmovinv.co_emp = doc.co_emp and cabmovinv.co_loc = doc.co_loc and cabmovinv.co_tipdoc = doc.co_tipdoc) "+
				      " left outer join tbr_tipdocprg as docprg on (docprg.co_emp = Doc.co_emp and docprg.co_loc = Doc.co_loc and docprg.co_tipdoc = Doc.co_tipdoc)" +
                                      " Where  CabMovInv.st_reg not in('E') and CabMovInv.co_emp = " + objZafParSis.getCodigoEmpresa() + // Consultando en la empresa en la ke se esta trabajando
                                      "       and CabMovInv.co_loc = " + objZafParSis.getCodigoLocal()  +" and docprg.co_mnu = " + objZafParSis.getCodigoMenu() + 
                                      strFiltro + " ORDER BY CabMovInv.ne_secemp";
//                                System.out.println(sSQL);
                                rstCab=stmCab.executeQuery(sSQL);
                                
                                if(rstCab.next()){
                                    rstCab.last();
//                                    objTipDoc.cargarTipoDoc(rstCab.getInt("co_tipdoc"));                                    
                                    setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                                    cargarReg();
                                }
                                else{
                                    setMenSis("0 Registros encontrados");
                                    clnTextos();
                                    conCab.close();
                                    return false;
                                }
                                conCab.close();
                            }
                   }
                   catch(SQLException Evt)
                   {
                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
                          return false;
                    }

                    catch(Exception Evt)
                    {
                          objUti.mostrarMsgErr_F1(jfrThis, Evt);
                          return false;
                    }                       
            return true;     
            }
    
            
            
            
            private boolean validaCampos(){
               if(txtCodTipDoc.getText().equals("") ){
                   tabDocVar.setSelectedIndex(0);                  
                   MensajeValidaCampo("Codigo de Tipo de Documento");
                   txtCodTipDoc.requestFocus();
                   return false;
               }
               
               if(txtOrdCom.getText().equals("") ){
                   tabDocVar.setSelectedIndex(0);                  
                   MensajeValidaCampo("No. Ingreso/Egreso");
                   txtOrdCom.requestFocus();
                   return false;
               }
              

               if(!txtFecDoc.isFecha()){
                   tabDocVar.setSelectedIndex(0);                  
                   MensajeValidaCampo("Fecha de Documento");
                   txtFecDoc.requestFocus();
                   return false;
               }

             if (!objDiario.isDiarioCuadrado()){
                    MensajeInf("Asiento descuadrado.");
                    tabDocVar.setSelectedIndex(2);
                    return false;                                    
               }
               if (!objDiario.isDocumentoCuadrado(txtTot.getText())){
                    MensajeInf("Asiento y Total del documento esta descuadrado.");
                    tabDocVar.setSelectedIndex(1);
                    return false;                                                       
               }
               
           
                for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                    if(tblDat.getValueAt(intRowVal,INT_TBL_CODITM) != null ){
                        
                    double dblCan = Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_CANMOV).toString());
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
                    
                     double dblCos = Double.parseDouble(tblDat.getValueAt(intRowVal, INT_TBL_PRECOS).toString());
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
                    
                }}
               
               if(!objDiario.isDocumentoCuadrado(txtTot.getText()))               
                 return false;
               
               return true; 
          }
            
            
            
   }   
   
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblCotNumDes;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblOrdCom;
    private javax.swing.JLabel lblPro;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTipDoc1;
    private javax.swing.JLabel lblTot;
    private javax.swing.JPanel panCotGen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panCotNor;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabDocVar;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDetTipDoc;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtNomTipDoc;
    private javax.swing.JTextField txtOrdCom;
    private javax.swing.JTextField txtPrvCod;
    private javax.swing.JTextField txtPrvDir;
    private javax.swing.JTextField txtPrvNom;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;

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

     private void listaProductos(String strDesBusqueda){
           objVenCon2.setTitle("Listado de Item"); 
            int intNumFil=tblDat.getSelectedRow();
           
           objVenCon2.show();
           if (objVenCon2.getSelectedButton()==objVenCon2.INT_BUT_ACE)
           {
              tblDat.setValueAt(objVenCon2.getValueAt(1),intNumFil,INT_TBL_ITMALT);
              tblDat.setValueAt(objVenCon2.getValueAt(2),intNumFil,INT_TBL_CODITM);
              tblDat.setValueAt(objVenCon2.getValueAt(3),intNumFil,INT_TBL_DESITM);
              tblDat.setValueAt(objVenCon2.getValueAt(7),intNumFil,INT_TBL_UNIDAD);
              tblDat.setValueAt(objVenCon2.getValueAt(5),intNumFil,INT_TBL_PRECOS);
              tblDat.setValueAt(objVenCon2.getValueAt(6),intNumFil,INT_TBL_IVATXT); 
              tblDat.setValueAt(objVenCon2.getValueAt(9),intNumFil,INT_TBL_ITMSER);
              tblDat.setValueAt(objVenCon2.getValueAt(2),intNumFil,INT_TBL_CODITMACT); 
              
              tblDat.setValueAt(objVenCon2.getValueAt(10),intNumFil,INT_TBL_IEBODFIS); 
              
              
            if(objZafParSis.getCodigoUsuario()==1)
              tblDat.setValueAt(new Integer(objCtaCtb.getBodPredeterminada()), intNumFil, INT_TBL_CODBOD);
            else tblDat.setValueAt(new Integer(intCodBodPre), intNumFil, INT_TBL_CODBOD);


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
                            tblDat.setValueAt(new Boolean(true), intNumFil, INT_TBL_BLNIVA);
                        else
                            tblDat.setValueAt(new Boolean(false), intNumFil, INT_TBL_BLNIVA);
                    }
              
                   calculaSubtotal();
           }
    }
    
    
     private class ButFndPrv extends Librerias.ZafTableColBut.ZafTableColBut{
        public ButFndPrv(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx);
        }
        public void butCLick() {
             listaProductos("");
             tblDat.requestFocus();
             tblDat.changeSelection(tblDat.getSelectedRow(), INT_TBL_CANMOV, false, false);
        }
    }
    
    
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
              if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE)
              {
               if(!objVenConTipdoc.getValueAt(1).equals("")){
                   cargarCabTipoDoc(Integer.parseInt(objVenConTipdoc.getValueAt(1)));            
                   if (objTooBar.getEstado()=='n') cargaNum_Doc_OrdCom();
                   generaAsiento();
                }
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
//                    tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calculaTotal();
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
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
           
                                

            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
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
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.

                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("codEmp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("codLoc", new Integer(objZafParSis.getCodigoLocal()) );
                                mapPar.put("codTipDoc", new Integer( Integer.parseInt(txtCodTipDoc.getText())) );
                                mapPar.put("codDoc", new Integer( Integer.parseInt(txtDoc.getText())) );
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                
//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }}
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
        ///    System.out.println ("Se libera Objeto...");

    }
    



}


/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Ventas.ZafVen01;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
                 
/**     
 * @author  jayapata
 */
public class ZafVen01_06 extends javax.swing.JDialog {
    Connection CONN_GLO=null;
   
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objZafParSis;
    private ZafTblMod objTblMod;
    private ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    private UltDocPrint objUltDocPrint;
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafUtil objUti;
    private Connection con;
    private java.util.Date datFecAux;      
    private Statement stm;
    private ResultSet rst;
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODITM=1;      //CODIGO DEL ITEM 
    final int INT_TBL_CODEMP=2;      //CODIGO DE LA EMPRESA
    final int INT_TBL_CODBOD=3;      //CODIGO DE LA BODEGA
    final int INT_TBL_NOMEMP=4;      //NOMBRE DE LA EMPRESA
    final int INT_TBL_NOMBOD=5;      //NOMBRE DE LA BODEGA
    final int INT_TBL_STKACT=6;      //STOCK ACTUAL 
    final int INT_TBL_ESTBOD=7;      //
    final int INT_TBL_CANCOM=8;      //CANTIDAD QUE SE COMPR
    final int INT_TBL_CODALT=9;      //CODIGO ALTERNO 
    final int INT_TBL_NOMITM=10;     //NOMBRE DEL ITEM 
    final int INT_TBL_UNIMED=11;     //UNIDAD DE MEDIDA 
    final int INT_TBL_COSUNI=12;     //COSTO UNITARIO
    final int INT_TBL_DATCANCOM=13;  //
    final int INT_TBL_CODITMMAE=14;  //CODIGO DEL ITEM MAESTRO
    final int INT_TBL_INGEGRFIS=15;  // 
    final int INT_TBL_CHKNECAUT=16;  // JM NECESITA AUTORIZACIÓN
    final int INT_TBL_DATCANAUT=17;  // JM CANTIDAD AUTORIZADA
    final int INT_TBL_TIPUNIMED=18;  //TIPO DE MEDIDA
    final int INT_TBL_CHKRETREM=19;  //CLIENTE RETIRA 
    final int INT_TBL_ISBODPRE=20;   // PARA SABER SI ES LA BODEGA PREDETERMINADA DE CADA PUNTO
    
    final private int INT_TBL_CODEMPCOT=21;   // PARA SABER SI ES LA BODEGA PREDETERMINADA DE CADA PUNTO
    final private int INT_TBL_CODLOCCOT=22;   // PARA SABER SI ES LA BODEGA PREDETERMINADA DE CADA PUNTO
    final private int INT_TBL_CODDOCCOT=23;   // PARA SABER SI ES LA BODEGA PREDETERMINADA DE CADA PUNTO
    final private int INT_TBL_CODREGCOT=24;   // PARA SABER SI ES LA BODEGA PREDETERMINADA DE CADA PUNTO
    final private int INT_TBL_ISITMRES=25;   // PARA SABER SI ES LA BODEGA PREDETERMINADA DE CADA PUNTO
    
    
    
    int intNumCotBus=0;
    int intConStbBod=0;
    int[][] intColBodEmp = new int[30][2];
    
    private String strTit="Mensaje del Sistema Zafiro";
    
    private boolean blnCliRet;
     
    Vector vecCab=new Vector(); 
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen; 
    String strCodCli="";
    String strDesCli="";
    String strCodSol="";
    String strDesSol="";
    String strTipPer_emp="";
    StringBuffer stbDevTemp, stbDevInvTemp, stbLisBodItmTemp, stbDocRelEmpTemp;
    public StringBuffer stbLisBodItm;
    public StringBuffer stbDocRelEmp;
    double bldivaEmp=0;
    double Glo_dblCanFalCom=0.00;
    double Glo_dblCanFalDevCom=0.00;
    double Glo_dblCanFalDevVen=0.00;

    private ZafDatePicker txtFecCliRet;

    
    double Glo_dblTotDevCom=0.00;
    double Glo_dblTotDevVen=0.00;

    boolean blnEstDevVen=false;
    boolean blnEstDevCom=false;

    double Glo_dblCanTotDevCom=0.00;
    int intConStbBodTemp=0;
    int intCodBodPre=0;
    int intCodTipPerEmp=0;
    int intEstReaOcFac=0;
    int INTCODREGCEN=0;
    int intDocRelEmp=0, intDocRelEmpTemp=0;
    int intBodPreDevCom=0;
    double dblCanDevComPar=0;
    private String Str_RegSel[]; 
    public boolean blnAcepta = false; 
    ArrayList arlAuxColEdi=new java.util.ArrayList();
    ArrayList arlDat = new ArrayList();
    ArrayList arlReg = new ArrayList();

    StringBuffer stbDatVen=null;
    StringBuffer stbDatDevVenCom=null;
    StringBuffer stbDatInsVen=null;
    int intDatVen=0;
    int intDatDevVenCom=0;
    
    int intCodTipDocFacEle_ZafVen01_06;
    
    String StrEstConfDevVen="P";
    String strNomBodIng="";

    int codDoc;
    StringBuffer stbDatDevCom=null;
    StringBuffer stbDatDevVen=null;
    int intDatDevCom=0;
    int intDatDevVen=0;
    
    private String strTitu="Pedidos a otras bodegas";
    public String strVersion="v 0.05";
    
    
    ArrayList arlItmRec;
    private Ventas.ZafVen01.ZafVen01 objVen01;
    
//    private final boolean blnReaDevComVen=true;
    private final boolean blnReaDevComVen=false;  /// NO DEVOLUCIONES EN VENTA
    
    private boolean blnGenFacIni=false;
    /*Variable creada para el nuevo servicio 1 usada para generar las ordenes de despacho en compras-ventas entre empresas*/
    private String strEmpCompVen="";
    private String strEmpTrans="";
    /*Variable creada para el nuevo servicio 1 usada para generar las ordenes de despacho en compras-ventas entre empresas*/

    /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
   final int INT_ARL_COD_EMP=0;
   final int INT_ARL_COD_LOC=1;
   final int INT_ARL_COD_TIP_DOC=2;
   final int INT_ARL_COD_BOD_GRP=3;
   final int INT_ARL_COD_ITM=4;
   final int INT_ARL_COD_ITM_MAE=5;
   final int INT_ARL_COD_BOD=6;
   final int INT_ARL_NOM_BOD=7;
   final int INT_ARL_CAN_COM=8;
   final int INT_ARL_CHK_CLI_RET=9;
   final int INT_ARL_EST_BOD=10;
   final int INT_ARL_ING_EGR_FIS_BOD=11;
   final int INT_ARL_COD_BOD_ING_MER=12;
   
   
    ArrayList arlSolTraDat = new ArrayList();
    ArrayList arlSolTraReg = new ArrayList();
    private ZafTblOrd objTblOrd;
   
   
      /* CONSTANTES PARA CONTENEDOR A ENVIAR SOLICITUD DE TRANSFERENCIA JoséMario 26/Abril/2015  */
   final int INT_ARL_SOL_TRA_COD_BOD_ING=0;
   final int INT_ARL_SOL_TRA_COD_BOD_EGR=1;
   final int INT_ARL_SOL_TRA_ITM_GRP=2;
   final int INT_ARL_SOL_TRA_CAN=3;
   final int INT_ARL_SOL_PES_UNI=4;
   final int INT_ARL_SOL_PES_TOT=5;
   
   public boolean blnGenSolTra=false;
            
   private int intCodBodEmp;
   private String strNomBodEmp;
   private java.awt.Frame Frame;
   
   
    public ZafVen01_06() {
    }
    
    
    /** Creates new form pantalla dialogo */
    public ZafVen01_06(Frame parent, boolean modal, ZafParSis ZafParSis, int intNumCot, Connection conn, int  INTCODREGCENBD, Ventas.ZafVen01.ZafVen01 objVen01, int intCodTipDocFacEle) {
       super(parent, modal);
       try{ 
           Frame=parent;
                //  System.out.println(" ZafVen01_06 ");
             this.objZafParSis = ZafParSis;
             objUti = new ZafUtil();
             objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
             this.objVen01 = objVen01;
            
             objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
             objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
             objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
             objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

             ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
             stbLisBodItm= new StringBuffer();
             stbDocRelEmp= new StringBuffer();
             codDoc=objVen01.CodCot();
             initComponents();
           
             CONN_GLO=conn;
             INTCODREGCEN=INTCODREGCENBD;

             //JoséMario Facrutación Electronica 6/Oct/2014
             intCodTipDocFacEle_ZafVen01_06=intCodTipDocFacEle; 

             txtFecCliRet = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
             txtFecCliRet.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecCliRet.setText("");
             pancliret.add(txtFecCliRet);
             txtFecCliRet.setBounds(150, 5, 92, 20);

             intNumCotBus=intNumCot;
             cargarTipEmp();   
             cargarFormaPago();

             this.setTitle( strTitu );
             lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014
        
       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   

    private int intCodForPag; // 3 ES CREDITO LO DEMAS AL INICIO 
    
    private void cargarFormaPago(){
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena="";   
        try{
            conLoc=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());        
            stmLoc= conLoc.createStatement();
            strCadena="";
            strCadena+=" select a1.co_emp,a1.co_loc,a1.co_cot,a2.co_forPag, a2.ne_tipForPag \n";
            strCadena+=" from tbm_cabCotVen as a1   \n";
            strCadena+=" inner join tbm_cabForPag as a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag) \n";
            strCadena+=" WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc="+objZafParSis.getCodigoLocal()+"  \n";
            strCadena+="        and a1.co_cot="+codDoc+" \n";
            rstLoc=stmLoc.executeQuery(strCadena);
            if(rstLoc.next()){
                intCodForPag=rstLoc.getInt("ne_tipForPag");
            }
        }
        catch (java.sql.SQLException e) { 
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
    }
    
private void cargarTipEmp(){
 java.sql.Statement stmTipEmp; 
 java.sql.ResultSet rstEmp;
 String sSql;
 try{  
     //  System.out.println(" ZafVen01_06 cargarTipEmp");
    if(CONN_GLO!=null){
        stmTipEmp=CONN_GLO.createStatement();

        sSql="select b.co_tipper , b.tx_descor , round(a.nd_ivaVen,2) as porIva , bod.co_bod, bod1.tx_nom FROM  tbm_emp as a " +
        " left join tbm_tipper as b on(b.co_emp=a.co_emp and b.co_tipper=a.co_tipper)" +
        " left join tbr_bodloc as bod on(bod.co_emp=a.co_emp and bod.co_loc="+objZafParSis.getCodigoLocal()+" and bod.st_reg='P')  " +
        " left join tbm_bod as bod1 on(bod1.co_emp=bod.co_emp and bod1.co_bod=bod.co_bod )   " +
        " where a.co_emp="+objZafParSis.getCodigoEmpresa();
            
        rstEmp = stmTipEmp.executeQuery(sSql);
        if(rstEmp.next()){
            strTipPer_emp = rstEmp.getString("tx_descor");
            bldivaEmp   =  rstEmp.getDouble("porIva");
            intCodBodPre = rstEmp.getInt("co_bod");
            intCodTipPerEmp = rstEmp.getInt("co_tipper");
            strNomBodIng= rstEmp.getString("tx_nom");
        }

        rstEmp.close();
        stmTipEmp.close();
        stmTipEmp = null;
        rstEmp = null;
    }
}catch(java.sql.SQLException Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
 catch(Exception Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}



        
private void configurarFrm(){
  
      configuraTbl();
      
      configurarVenConClientes();
      configurarVenConVendedor();

      
      cargarDat(CONN_GLO);

   
      if(intEstReaOcFac==0){
        blnAcepta = true;
        dispose();
      }
      
} 
  
    
  

 
private boolean cargarDat(Connection conn){
 boolean blnRes=false;
 Statement stmLoc, stmLoc01, stmLoc02, stmLoc03, stmLocDat;
 ResultSet rstLoc, rstLoc01, rstLoc02, rstLocDat;
 String strSql="", strAuxDesc="",strSql2="" ;
 Vector vecData;
 StringBuffer stbDev;
 StringBuffer stbDevInv;
 StringBuffer stbDatCot;
 int intEstDev=0;
 int intEstDevError=0;
 int intEstDatCot=0;
 try{
      
      if(conn!=null){
       
    
      stbDev=new StringBuffer();
      stbDevInv=new StringBuffer();
      stbDatCot=new StringBuffer();

      stmLoc01=conn.createStatement();
      stmLoc02=conn.createStatement();
      stmLoc03=conn.createStatement();

      stmLoc=conn.createStatement();  

      stmLocDat=CONN_GLO.createStatement();

      vecData = new Vector();

       if(objZafParSis.getCodigoEmpresa() != 1)
          strAuxDesc=" desc ";

 
      strSql= " SELECT a.co_Emp,a.co_loc,a.co_cot, a.co_reg, a.co_itm, a1.tx_codalt , a1.tx_nomitm, var.tx_descor, a.co_bod, a1.nd_prevta1, a1.nd_cosuni, a.nd_can as can, \n" ;
      strSql+="        CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1))   \n " ;
      strSql+="             IN ( SELECT UPPER(trim(tx_cad)) FROM tbm_reginv \n";
      strSql+="                  WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+"  AND co_tipdoc="+ intCodTipDocFacEle_ZafVen01_06 +" \n";
      strSql+="                        AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I'  ))) \n";
      strSql+="        THEN 'S' ELSE 'N' END  as isterL, \n"; 
      strSql+="        CASE WHEN var.tx_tipunimed IS NULL THEN 'F' ELSE var.tx_tipunimed end AS txtipunimed,  \n "; 
      strSql+="        CASE WHEN a.co_cotRel IS NULL THEN 0 ELSE a.co_cotRel END AS co_cotRel \n";
      strSql+=" FROM tbm_inv AS a1 \n";
      strSql+=" INNER JOIN ( \n";
      strSql+="             SELECT a1.co_emp,a1.co_loc, a1.co_cot, a1.co_itm, a1.co_bod, sum(a1.nd_can) as nd_can, a1.co_reg, a3.co_cot as co_cotRel \n" ;
      strSql+="             FROM tbm_detCotVen AS a1 \n" ;
      strSql+="             LEFT OUTER JOIN tbr_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n";
      strSql+="                                                     a1.co_cot=a2.co_cot AND a1.co_reg=a2.co_reg)  \n";
      strSql+="             LEFT OUTER JOIN tbm_detCotVen as a3 ON (a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND \n";
      strSql+="                                                     a2.co_cotRel=a3.co_cot AND a2.co_regRel=a3.co_reg)  \n";
      strSql+="             WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" AND a1.co_cot="+intNumCotBus;
      strSql+="             GROUP BY a1.co_emp,a1.co_loc, a1.co_cot, a1.co_itm, a1.co_bod,a1.co_reg, a3.co_cot \n";
      strSql+="             ORDER BY a1.co_reg  \n" ;
      strSql+=" ) AS a ON( a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) \n";
      strSql+=" LEFT JOIN tbm_var AS var ON(var.co_reg=a1.co_uni)  \n" ;
      strSql+=" WHERE a1.st_ser='N'  \n";
        System.out.println("ZafVen01_06 cargarDat " + strSql);
      rstLocDat=stmLocDat.executeQuery(strSql);
      while(rstLocDat.next()){

          if(intEstDatCot==1) stbDatCot.append(" UNION ALL ");
          
          strSql2="";
          strSql2+=" SELECT "+rstLocDat.getInt("co_emp")+" as co_emp,"+rstLocDat.getInt("co_loc")+" as co_loc,";
          strSql2+=""+rstLocDat.getInt("co_cot")+" as co_cot,"+rstLocDat.getInt("co_reg")+" as co_reg,";
          strSql2+=""+rstLocDat.getInt("co_cotRel")+" as co_cotRel,";
          strSql2+=""+rstLocDat.getInt("co_itm")+" as co_itm,text('"+rstLocDat.getString("tx_codalt")+"') as tx_codalt, ";
          strSql2+=""+objUti.codificar(rstLocDat.getString("tx_nomitm"))+" as tx_nomitm, text('"+rstLocDat.getString("tx_descor")+"') as tx_descor, \n" ;
          strSql2+=""+rstLocDat.getInt("co_bod")+" as co_bod, ";
          strSql2+=""+rstLocDat.getDouble("nd_prevta1")+" as nd_prevta1,  "+rstLocDat.getDouble("nd_cosuni")+" as nd_cosuni, ";
          strSql2+=""+rstLocDat.getDouble("can")+" as can,  text('"+rstLocDat.getString("isterl")+"') as isterl, ";
          strSql2+=""+ " text('"+rstLocDat.getString("txtipunimed")+"') as txtipunimed  \n";
          
          
 
          
          stbDatCot.append(strSql2);
          
          intEstDatCot=1;
          
      }
      
      rstLocDat.close();
      rstLocDat=null;
      stmLocDat.close();
      stmLocDat=null;
/**      
 * José Marín M.   1/Sep/2014
 * '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
 *                              CANTIDAD BODEGA PREDETERMINADA
 * '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
 * SE RESTA LO QUE TENEMOS EN BODEGA PREDETERMINADA
  */    

      strSql= " SELECT *, CASE WHEN  (TRIM(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) in ('S')) then 'S' else 'N' end as Term    \n" ;
      strSql+=" FROM ( \n";
      strSql+="         SELECT a2.co_itmmae, x.co_emp,x.co_loc,x.co_cot,x.co_reg, x.co_itm, \n";
      strSql+="                x.co_bod, x.tx_codalt, x.tx_descor, x.tx_nomitm, a1.nd_canDis , x.can, \n";
      strSql+="                CASE WHEN  x.txtipunimed = 'F' THEN abs(a1.nd_canDis - x.can) \n";
      strSql+= "               ELSE abs(TRUNC(a1.nd_canDis) - x.can)  END  AS canfal , \n" ;
      strSql+="                x.nd_prevta1, x.nd_cosuni, x.txtipunimed,x.co_cotRel    \n" ;
      strSql+="         FROM(     \n";
      strSql+="                 SELECT *  \n" ;
      strSql+="                 FROM ( \n";
      strSql+=" \n";
      strSql+=   stbDatCot.toString()+" \n";
      strSql+=" \n";
      strSql+="                 ) as x  \n" ;
      strSql+="                 WHERE x.isterl='S'  \n";
      strSql+="         ) as x \n" ;
      strSql+="         INNER JOIN tbm_invbod AS a1 ON(a1.co_emp=x.co_emp AND a1.co_bod=x.co_bod AND a1.co_itm=x.co_itm )  \n";
      strSql+="         INNER JOIN tbm_equinv AS a2 ON(a2.co_emp=x.co_emp and a2.co_itm=x.co_itm ) \n" ;
      strSql+=" ) AS x \n "; //José Marín 
      strSql+=" /* WHERE  (nd_canDis - can)  < 0*/ \n ORDER BY x.co_reg \n";
      System.out.println("ZafVen01_06.cargarDat agregadosLocItems: \n"+strSql );
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){

//       Glo_dblCanFalCom=rstLoc.getDouble("canfal");  // PARA QUE SOLO PIDA LO QUE LE ESTA FALTANDO 1/SEP/2014
          
        Glo_dblCanFalCom=rstLoc.getDouble("can");  // PARA QUE PIDA COMO QUE NO TIENE NADA 
  
      
/********************************************/

 
/*
 * José Marín M.   1/Sep/2014
 *                          SI NO HAY CANTIDAD FALTANTE NO SE DEBERIA HACER ESTA PARTE 
 */

       if(Glo_dblCanFalCom > 0){   
        // if( rstLoc.getString("Term").equals("N")){


           //  System.out.println("--> "+Glo_dblCanFalCom );

         java.util.Vector vecReg2 = new java.util.Vector();
          vecReg2.add(INT_TBL_LINEA,"");
          vecReg2.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg2.add( INT_TBL_CODEMP, "" );
          vecReg2.add( INT_TBL_CODBOD, "" );
          //
          //vecReg2.add( INT_TBL_NOMEMP, rstLoc.getString("tx_codalt") );
          vecReg2.add( INT_TBL_NOMEMP, rstLoc.getString("tx_codalt") );
          //
          vecReg2.add( INT_TBL_NOMBOD, rstLoc.getString("tx_nomitm") );
          vecReg2.add( INT_TBL_STKACT,"" );
          vecReg2.add( INT_TBL_ESTBOD,"" );
          vecReg2.add( INT_TBL_CANCOM,  ""+Glo_dblCanFalCom );
          vecReg2.add( INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
          vecReg2.add( INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
          vecReg2.add( INT_TBL_UNIMED, rstLoc.getString("tx_descor") );
          vecReg2.add( INT_TBL_COSUNI, rstLoc.getString("nd_cosuni") );

          vecReg2.add( INT_TBL_DATCANCOM ,""+Glo_dblCanFalCom );
          vecReg2.add( INT_TBL_CODITMMAE ,"" );
          vecReg2.add( INT_TBL_INGEGRFIS ,"" );
          vecReg2.add( INT_TBL_CHKNECAUT ,"" );
          vecReg2.add( INT_TBL_DATCANAUT ,"" );
          vecReg2.add( INT_TBL_TIPUNIMED , rstLoc.getString("txtipunimed") );
          vecReg2.add( INT_TBL_CHKRETREM , false);
          vecReg2.add(INT_TBL_ISBODPRE,"N");
          
          vecReg2.add(INT_TBL_CODEMPCOT,rstLoc.getString("co_emp"));
          vecReg2.add(INT_TBL_CODLOCCOT,rstLoc.getString("co_loc"));
          vecReg2.add(INT_TBL_CODDOCCOT,rstLoc.getString("co_cot"));
          vecReg2.add(INT_TBL_CODREGCOT,rstLoc.getString("co_reg"));
          vecReg2.add(INT_TBL_ISITMRES,rstLoc.getInt("co_cotRel")==0?"N":"S");
 
    
//llenar con lo autorizado José Marín 
      
      codDoc=objVen01.CodCot();
      
      
    vecData.add(vecReg2);    
    strSql="";
    strSql+=" SELECT DISTINCT a.stk,a.canIng,a.st_reg,a.tx_nom,a.co_bodGrp,a.ingEgrFis , b.nd_can, \n";
    strSql+=" case when b.st_necAut like 'S' then 'S' else 'N'end as st_necAut, b.nd_canAut, \n";
    strSql+=" case when b.st_cliRet like 'S' then 'S' else 'N' end as st_cliRet ,\n";
    strSql+=" CASE WHEN a.st_regBodPre IS NULL THEN 'N' ELSE a.st_regBodPre END AS st_regBodPre \n";
    strSql+=" FROM ( \n";
    strSql+="       SELECT x.stk, x.caning, x.st_reg, x.tx_nom, x.co_bodgrp  , \n";
    strSql+="              case when  a.co_empgrp is not null then 'N' else 'S' end as ingegrfis, b.st_reg as st_regBodPre , \n        ";
    strSql+="              "+rstLoc.getString("co_itmmae")+" as co_itmMae  \n";
    strSql+="       FROM (  \n";
    strSql+="             SELECT sum(x.stk) as stk , sum(x.caning) as caning,  x.st_reg,   x2.tx_nom,  x1.co_bodgrp  \n";
    strSql+="             FROM ( \n";
    strSql+="                    SELECT a.co_emp, a.co_loc, a.co_empper, a2.tx_nom, a.co_bodper, a3.tx_nom as nombod, a.st_reg  \n";
    strSql+="                           ,(  SELECT nd_canDis    \n";
    strSql+="                               FROM tbm_invbod AS x \n";
    strSql+="                               INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm )   \n";
    strSql+="                               INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm)  \n ";
    strSql+="                               WHERE x.co_emp=a.co_empper  AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+"  \n";
    strSql+="                             ) as stk  \n";
    strSql+="                           ,(  SELECT nd_caningbod   \n";
    strSql+="                               FROM tbm_invbod AS x  \n";
    strSql+="                               INNER JOIN tbm_equinv AS x1 ON(x1.co_emp=x.co_emp and x1.co_itm=x.co_itm ) \n";
    strSql+= "                              INNER JOIN tbm_equinv AS x2 ON(x2.co_emp=x1.co_emp and x2.co_itm=x1.co_itm)  \n ";
    strSql+="                               WHERE x.co_emp=a.co_empper  AND  x.co_bod=a.co_bodper  AND  x2.co_itmmae = "+rstLoc.getString("co_itmmae")+"    \n";
    strSql+="                           ) as caning  \n";
    strSql+= "                  FROM tbr_bodemp as a   \n";
    strSql+= "                  INNER JOIN tbm_emp as a2 ON (a2.co_emp=a.co_empper)   \n";
    strSql+= "                  INNER JOIN tbm_bod as a3 ON (a3.co_emp=a.co_empper and a3.co_bod=a.co_bodper)   \n";
    strSql+="                   WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.st_reg NOT IN ('I') \n ";
    strSql+="                   ORDER BY a.co_empper, a.co_bodper   \n";
    strSql+="                   ) as x  \n";
    strSql+="           INNER JOIN  tbr_bodEmpBodGrp as x1 on (x1.co_emp=x.co_empper and x1.co_bod=x.co_bodper ) \n";
    strSql+="           INNER JOIN tbm_bod as x2 ON (x2.co_emp=x1.co_empgrp and x2.co_bod=x1.co_bodgrp )  \n \n";
    strSql+="           GROUP BY  x.st_reg, x2.tx_nom, x1.co_bodgrp \n" ;
    strSql+="           ) as x \n" ;
    strSql+="        LEFT JOIN tbr_bodEmpBodGrp as a on ( a.co_emp="+objZafParSis.getCodigoEmpresa()+" and \n";
    strSql+="                                             a.co_bod="+intCodBodPre+" and a.co_empgrp="+objZafParSis.getCodigoEmpresaGrupo()+" and  \n" ;
    strSql+="                                             a.co_bodgrp=x.co_bodgrp )  \n";
    strSql+="        LEFT JOIN tbr_bodLoc as b ON (a.co_emp=b.co_emp  and b.co_loc="+objZafParSis.getCodigoLocal()+" and a.co_bod=b.co_bod)  \n"; /*JoseMario 20/Jul/2016*/
    strSql+="        ORDER BY x.co_bodgrp \n";
    strSql+=" ) as a \n";
     ///////////////José Marín 2013  -- Modificado Para reservas 2017
     strSql+=" LEFT OUTER JOIN (    \n";
     strSql+="      SELECT a3.co_itmMae,a4.co_bodGrp, a2.co_emp,a2.co_loc,a2.co_cot,a2.co_reg,a2.co_itm, a1.nd_can, \n";
     strSql+="              a1.nd_canAut, a1.st_necAut, a1.st_cliRet  \n";
     strSql+="      FROM tbm_pedOtrBodCotVen AS a1  \n";
     strSql+="      INNER JOIN tbm_detCotVen AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND  \n";
     strSql+="                                         a1.co_cot=a2.co_cot AND a1.co_reg=a2.co_reg)  \n";
     strSql+="      INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
     strSql+="      INNER JOIN tbr_bodEmpBodGrp AS a4 ON (a1.co_empRel=a4.co_emp AND a1.co_bodRel=a4.co_bod AND \n";
     strSql+="                                               a4.co_empGrp="+objZafParSis.getCodigoEmpresaGrupo()+" ) \n";
     strSql+="      WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa() + " AND \n";
     strSql+="            a1.co_loc=" + objZafParSis.getCodigoLocal() + " AND \n";
     strSql+="            a1.co_cot="+ codDoc +" AND a1.co_reg="+rstLoc.getInt("co_reg")+"   \n";
     strSql+="  )as b ON (a.co_bodgrp=b.co_bodGrp AND a.co_itmMae=b.co_itmMae) \n";
     strSql+=" ORDER BY a.co_bodgrp";
        System.out.println("<<<<<<<<<<<<<<<<<<<<<  ZafVen01_06.cargarDat JOTA : "+strSql );

       rstLoc01=stmLoc01.executeQuery(strSql);
       while(rstLoc01.next()){ 
           
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
          vecReg.add( INT_TBL_CODEMP, "2"); // rstLoc01.getString("co_empper") );
          vecReg.add( INT_TBL_CODBOD, ""); //rstLoc01.getString("co_bodper") );
          vecReg.add( INT_TBL_NOMEMP, rstLoc01.getString("co_bodgrp") );  //rstLoc01.getString("tx_nom") );
          vecReg.add( INT_TBL_NOMBOD, rstLoc01.getString("tx_nom") );  //rstLoc01.getString("nombod") );
          vecReg.add( INT_TBL_STKACT, rstLoc01.getString("stk") );
          vecReg.add( INT_TBL_ESTBOD, rstLoc01.getString("st_reg") );
          
          vecReg.add( INT_TBL_CANCOM,  rstLoc01.getString("nd_can")); // --- Jose Marin 
          //vecReg.add( INT_TBL_CANCOM, ""); // --- Jose Marin //
          
          vecReg.add( INT_TBL_CODALT,  rstLoc.getString("tx_codalt") );
          vecReg.add( INT_TBL_NOMITM,  rstLoc.getString("tx_nomitm") );
          vecReg.add( INT_TBL_UNIMED,  rstLoc.getString("tx_descor") );
          vecReg.add( INT_TBL_COSUNI,  rstLoc.getString("nd_cosuni") );
          vecReg.add( INT_TBL_DATCANCOM ,"");
          vecReg.add( INT_TBL_CODITMMAE,  rstLoc.getString("co_itmmae") );
          vecReg.add( INT_TBL_INGEGRFIS, rstLoc01.getString("ingegrfis") );
          //////////Jose Marin
          if(rstLoc01.getString("st_necAut").equals("S"))
              vecReg.add( INT_TBL_CHKNECAUT ,true);
          else 
              vecReg.add( INT_TBL_CHKNECAUT ,false);
          vecReg.add( INT_TBL_DATCANAUT ,rstLoc01.getString("nd_canAut"));//Jose Marin 
          ////////
          vecReg.add( INT_TBL_TIPUNIMED , rstLoc.getString("txtipunimed")  );
          
          if(rstLoc01.getString("st_cliRet").equals("S"))
            vecReg.add( INT_TBL_CHKRETREM , true);
          else
              vecReg.add( INT_TBL_CHKRETREM , false);  // José Marín 5/Feb/2014
         vecReg.add(INT_TBL_ISBODPRE,rstLoc01.getString("st_regBodPre"));  /*  JoseMario 1/Julio/2016  */
         
         vecReg.add(INT_TBL_CODEMPCOT,rstLoc.getString("co_emp"));
         vecReg.add(INT_TBL_CODLOCCOT,rstLoc.getString("co_loc"));
         vecReg.add(INT_TBL_CODDOCCOT,rstLoc.getString("co_cot"));
         vecReg.add(INT_TBL_CODREGCOT,rstLoc.getString("co_reg"));
         vecReg.add(INT_TBL_ISITMRES ,rstLoc.getInt("co_cotRel")==0?"N":"S");
 
          
          vecData.add(vecReg);
       }
       rstLoc01.close();
       rstLoc01=null;
       
       
         vecReg2 = new java.util.Vector();
          vecReg2.add(INT_TBL_LINEA,"");
          vecReg2.add( INT_TBL_CODITM, "" );
          vecReg2.add( INT_TBL_CODEMP, "0" );
          vecReg2.add( INT_TBL_CODBOD, "" );
          vecReg2.add( INT_TBL_NOMEMP, "" );
          vecReg2.add( INT_TBL_NOMBOD, "" );
          vecReg2.add( INT_TBL_STKACT,"" );
          vecReg2.add( INT_TBL_ESTBOD,"" );
          vecReg2.add( INT_TBL_CANCOM,"" );
          vecReg2.add( INT_TBL_CODALT, "" );
          vecReg2.add( INT_TBL_NOMITM, "" );
          vecReg2.add( INT_TBL_UNIMED, "" );
          vecReg2.add( INT_TBL_COSUNI, "" );
          vecReg2.add( INT_TBL_DATCANCOM ,"");
          vecReg2.add( INT_TBL_CODITMMAE ,"" );
          vecReg2.add( INT_TBL_INGEGRFIS ,"" );
          vecReg2.add( INT_TBL_CHKNECAUT ,"" );
          vecReg2.add( INT_TBL_DATCANAUT ,"" );
          
          vecReg2.add( INT_TBL_TIPUNIMED ,"N" );
          vecReg2.add( INT_TBL_CHKRETREM , "" );
          vecReg2.add(INT_TBL_ISBODPRE   , "" );  /*  JoseMario 1/Julio/2016  */
          vecReg2.add(INT_TBL_CODEMPCOT  , "" );
          vecReg2.add(INT_TBL_CODLOCCOT  , "" );
          vecReg2.add(INT_TBL_CODDOCCOT  , "" );
          vecReg2.add(INT_TBL_CODREGCOT  , "" );
          vecReg2.add(INT_TBL_ISITMRES   , "" );
          vecData.add(vecReg2);    
       
         intEstReaOcFac=1;
        } //}

      
           
      }  
      rstLoc.close();
      rstLoc=null;

       
       stbDev=null;
       stbDevInv=null;
       stbDatCot=null;
       
      objTblMod.setData(vecData);
      tblDat .setModel(objTblMod);         
        

        
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
      stmLoc02.close();
      stmLoc02=null;
      stmLoc03.close();
      stmLoc03=null;
   
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);    
     
      objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
     
    blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   





    double dblCabFalComAut=0.00;




/*****************************************************************************************/

 
 

    private boolean verificaCanTotItm(String strCodItm){
        boolean blnRes=false; 
        double dblCanTot=0;
        double dlbCanTotSal=3; 
        String strCanSal ="";
        String strCodItm2="";
        for(int i=0; i < tblDat.getRowCount(); i++){ 
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                if(strCodItm2.equals(strCodItm)){  
                    if( ( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
                        strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
                        dlbCanTotSal = objUti.redondear(strCanSal, 6); 
                    }else {
                        strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
                        dblCanTot += objUti.redondear(strCanSal, 6);
                    }
                }
            }
        }

        dblCanTot = objUti.redondear(dblCanTot, 6);
        System.out.println("dblCanTot-> "+dblCanTot +" dlbCanTotSal-> "+dlbCanTotSal );

        if(dblCanTot > dlbCanTotSal) 
            blnRes=true;

        return blnRes;   
    }




    private double getTotComItm(String strCodItm){
        double dblCanTot=0;
        double dblCanSal=0.00;
        String strCodItm2="";
        for(int i=0; i < tblDat.getRowCount(); i++){ 
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                if(strCodItm2.equals(strCodItm)){
                    if(!( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
                        dblCanSal = Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
                        dblCanTot += objUti.redondear(dblCanSal, 6);
                    } 
                }
            }
        }
        dblCanTot = objUti.redondear(dblCanTot, 6);
        return dblCanTot;   
    }



    private double getCanFalItm(String strCodItm){
        double dblCanTot=0;
        double dblCanSal =0.00;
        String strCodItm2="";
        for(int i=0; i < tblDat.getRowCount(); i++){ 
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                if(strCodItm2.equals(strCodItm)){
                    if(( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
                        dblCanSal += Double.parseDouble((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
                        dblCanTot = objUti.redondear(dblCanSal, 6); 
                    }
                }
            }
        }
     return dblCanTot;   
    }

 
    private void MensajeInf(String strMensaje){
        String strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

    
    
  

  


    
private boolean configurarVenConClientes() {
    boolean blnRes=true;
    try {
        //  System.out.println(" ZafVen01_06 configurarVenConClientes");
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
        arlAncCol.add("180");
        arlAncCol.add("120");
        arlAncCol.add("80");
        arlAncCol.add("100");           

        //Armar la sentencia SQL.
        String  strSQL;
        strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 " +
        " INNER JOIN tbm_cli as a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) " +
        " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND a.st_cli='S' AND a.st_reg NOT IN('I','T')  order by a.tx_nom ";
//  System.out.println(" ZafVen01_06 "+strSQL);       
        objVenConCLi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
}
        
   

private boolean configurarVenConVendedor() {
    boolean blnRes=true;
    try {
        //  System.out.println(" ZafVen01_06 configurarVenConVendedor");
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
        //  System.out.println(" ZafVen01_06 configurarVenConVendedor"+strSQL);       
        objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }
    

              /* Ventana de cliente retira */

    private void permitirMoverMomentoFactura(int intMom){
   //     System.out.println("permitirMoverMomentoFactura");
        if(intMom==1){
            rdoInicio.setEnabled(true);
            rdoFinal.setEnabled(true);
        }else if(intMom==2){ /* NO APLICA */
            rdoInicio.setEnabled(false);
            rdoFinal.setEnabled(false);
            rdoNA.setSelected(true);
        }else if(intMom==3){  // BLOQUEA TODO
            rdoInicio.setEnabled(false);
            rdoFinal.setEnabled(false);
        }
    }

  
    private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"..");
            vecCab.add(INT_TBL_CODITM,"Cód.Itm");
            vecCab.add(INT_TBL_CODEMP,"Cód.Emp");
            vecCab.add(INT_TBL_CODBOD,"Cód.Bod");
            vecCab.add(INT_TBL_NOMEMP,"Cód.Bod");
            vecCab.add(INT_TBL_NOMBOD,"Bodega");
            vecCab.add(INT_TBL_STKACT,"Stock");
            vecCab.add( INT_TBL_ESTBOD,"EST.BOD." ); // V A U : Jose Marin //C Cliente retira 3/Sep/2014
            vecCab.add( INT_TBL_CANCOM,"Can.Ped." );
            vecCab.add( INT_TBL_CODALT,"Cód.Itm" );
            vecCab.add( INT_TBL_NOMITM,"Nom.Itm" );
            vecCab.add( INT_TBL_UNIMED,"Uni.Med" );
            vecCab.add( INT_TBL_COSUNI,"Cos.Uni" );
            vecCab.add( INT_TBL_DATCANCOM ,"");
            vecCab.add( INT_TBL_CODITMMAE ,"");
            vecCab.add( INT_TBL_INGEGRFIS ,"" );
            vecCab.add( INT_TBL_CHKNECAUT ,"Nec.Aut." );
            vecCab.add( INT_TBL_DATCANAUT ,"Can.Aut." );
            vecCab.add( INT_TBL_TIPUNIMED ,"TipUniMed" );
            vecCab.add( INT_TBL_CHKRETREM ,"Cli.Ret." );
            vecCab.add( INT_TBL_ISBODPRE,"Bod.Pre.");  /*JoseMario 1/Jul/2016*/

            vecCab.add( INT_TBL_CODEMPCOT,"Cod.Emp.Cot.");  /*JoseMario 10/Ago/2017*/
            vecCab.add( INT_TBL_CODLOCCOT,"Cod.Loc.Cot.");  
            vecCab.add( INT_TBL_CODDOCCOT,"Cod.Doc.Cot.");   
            vecCab.add( INT_TBL_CODREGCOT,"Cod.Reg.Cot.");  
            vecCab.add( INT_TBL_ISITMRES,"Is.Itm.Res.");   
            
             

	    objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);


            ZafMouMotAda objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_STKACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANCOM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CHKNECAUT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DATCANAUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_TIPUNIMED).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_CHKRETREM).setPreferredWidth(50); 
            
            tcmAux.getColumn(INT_TBL_ISBODPRE).setPreferredWidth(10); 
            tcmAux.getColumn(INT_TBL_CODEMPCOT).setPreferredWidth(10); 
            tcmAux.getColumn(INT_TBL_CODLOCCOT).setPreferredWidth(10); 
            tcmAux.getColumn(INT_TBL_CODDOCCOT).setPreferredWidth(10); 
            tcmAux.getColumn(INT_TBL_CODREGCOT).setPreferredWidth(10); 
            tcmAux.getColumn(INT_TBL_ISITMRES).setPreferredWidth(10); 
            
              
            
            permitirMoverMomentoFactura(2);//Jota
            
            tcmAux.getColumn(INT_TBL_CHKRETREM).setCellRenderer(ZafTblCelRenChkDat); 
            ZafTblCelRenChkDat.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.

                    int intCell=ZafTblCelRenChkDat.getRowRender();

                    String strEstBod = (( (tblDat.getValueAt(intCell, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString());
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                  
                    if(str.equals("")){
                        ZafTblCelRenChkDat.setBackground(Color.BLUE);
                        ZafTblCelRenChkDat.setForeground(Color.BLUE);
                    }
                    if(str.equals("2")){
                      if(!(strEstBod.trim().equals("V"))) {
                         ZafTblCelRenChkDat.setBackground(Color.LIGHT_GRAY);
                         ZafTblCelRenChkDat.setForeground(Color.BLACK);
                      }else{
                        ZafTblCelRenChkDat.setBackground(Color.WHITE);
                        ZafTblCelRenChkDat.setForeground(Color.BLACK);
                        }
                    }
                    if(str.equals("0")){
                        ZafTblCelRenChkDat.setBackground(Color.BLACK);
                        ZafTblCelRenChkDat.setForeground(Color.BLACK);
                    }
                }
            });


           objTblCelEdiChkBE = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKRETREM).setCellEditor(objTblCelEdiChkBE);
            objTblCelEdiChkBE.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        if((strEstBod.trim().equals("V"))) 
                            objTblCelEdiChkBE.setCancelarEdicion(true);

                        double dblCan = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)), 4);
                        if(dblCan <= 0 )
                          objTblCelEdiChkBE.setCancelarEdicion(true);
                   }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                    String strNomBodSal = ((tblDat.getValueAt(intNumFil, INT_TBL_NOMBOD)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_NOMBOD).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_NOMBOD).toString()));
                    String strCodBodGrp = ((tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString()));
                   if(tblDat.getValueAt(intNumFil, INT_TBL_CHKRETREM).equals(true)){
                       if(objZafParSis.getCodigoEmpresa()==1) {
                            if (strCodBodGrp.equals("3")) {
                                tblDat.setValueAt(false, intNumFil, INT_TBL_CHKRETREM); //Jose Marin
                                MensajeInf("No esta permitido seleccionar la opcion cliente retira en " + strNomBodSal);
                            }
                       }
                       if (strCodBodGrp.equals("5") || strCodBodGrp.equals("11")) {
                            objTblCelEdiChkBE.setCancelarEdicion(true);tblDat.setValueAt(false, intNumFil, INT_TBL_CHKRETREM); //José Marín 4/Sep/2014
                                MensajeInf("Es obligatorio el cliente retira en " + strNomBodSal);
                               blnCliRet=true;
                        }
                       
                   }
                   if(tblDat.getValueAt(intNumFil, INT_TBL_CHKRETREM).equals(false)){
                       if ((strEstBod.trim().equals("C"))) {
                            objTblCelEdiChkBE.setCancelarEdicion(true);
                            tblDat.setValueAt(true, intNumFil, INT_TBL_CHKRETREM); //Jose Marin 2/Sep/2014 
                            MensajeInf("Solo esta permitido seleccionar la opcion cliente retira en " + strNomBodSal);
                        }
                       else{
                           objTblCelEdiChkBE.setCancelarEdicion(false);
                            tblDat.setValueAt(false, intNumFil, INT_TBL_CHKRETREM); //Jose Marin 2/Sep/2014 
                       }
                   }
                       
                   
                    
                }
            });
            ///////////////////////////////////
            //Jose Marin 
            tcmAux.getColumn(INT_TBL_CHKNECAUT).setCellRenderer(ZafTblCelRenChkDat); 
            ZafTblCelRenChkDat.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.

                    int intCell=ZafTblCelRenChkDat.getRowRender();

                    String strEstBod = (( (tblDat.getValueAt(intCell, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString());
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                  
                    if(str.equals("")){
                        ZafTblCelRenChkDat.setBackground(Color.BLUE);
                        ZafTblCelRenChkDat.setForeground(Color.BLUE);
                    }
                    if(str.equals("2")){
                      if(!(strEstBod.trim().equals("V"))) {
//                         ZafTblCelRenChkDat.setBackground(Color.LIGHT_GRAY);
//                         ZafTblCelRenChkDat.setForeground(Color.BLACK);
                          ZafTblCelRenChkDat.setBackground(Color.WHITE);
                        ZafTblCelRenChkDat.setForeground(Color.BLACK);
                      }else{
                        ZafTblCelRenChkDat.setBackground(Color.WHITE);
                        ZafTblCelRenChkDat.setForeground(Color.BLACK);
                        }
                    }
                    if(str.equals("0")){
                        ZafTblCelRenChkDat.setBackground(Color.BLACK);
                        ZafTblCelRenChkDat.setForeground(Color.BLACK);
                    }
                }
            });


            objTblCelEdiChkBE = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKNECAUT).setCellEditor(objTblCelEdiChkBE);
            objTblCelEdiChkBE.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        
                        if((strEstBod.trim().equals("V"))) 
                            objTblCelEdiChkBE.setCancelarEdicion(true);

                        double dblCan = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)), 4);
                        if(dblCan <= 0 )
                          objTblCelEdiChkBE.setCancelarEdicion(true);
                        
//                        /** BLOQUEO CLIENTE RETIRA . */
//                        String strCodBodGrp = ((tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString()));
//                        if (!strCodBodGrp.equals("15"))
//                          objTblCelEdiChkBE.setCancelarEdicion(true);                            
                    }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    double dblCan = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)), 4);
                    String strCodBodGrp = ((tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString()));
                }
            });

            /////////////////////////////////////
            
          objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
          tcmAux.getColumn(INT_TBL_NOMBOD).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_NOMEMP).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODBOD).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_CODITM).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_ESTBOD).setCellRenderer(objTblCelRenLbl);


           objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    
                    int intCell=objTblCelRenLbl.getRowRender();
                     
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    if(str.equals("")){
                        objTblCelRenLbl.setBackground(Color.BLUE);
                        objTblCelRenLbl.setFont(new Font(objTblCelRenLbl.getFont().getFontName(), Font.BOLD,   objTblCelRenLbl.getFont().getSize()));
                        objTblCelRenLbl.setForeground(Color.WHITE);
                    } 
                    if(str.equals("1")){
                        objTblCelRenLbl.setBackground(Color.ORANGE);
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    } 
                    if(str.equals("2")){
                        objTblCelRenLbl.setBackground(Color.WHITE);
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    } 
                    if(str.equals("4")){
                        objTblCelRenLbl.setBackground(Color.YELLOW);
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    }
                    
                    if(str.equals("0")){
                        objTblCelRenLbl.setBackground(Color.BLACK);
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    } 
                    
                }
            });
             
          objTblCelRenLbl2.setBackground(objZafParSis.getColorCamposObligatorios());
          objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
          objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
          objTblCelRenLbl2.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_STKACT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl2);
          objTblMod.setColumnDataType(INT_TBL_CANCOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl2);
          objTblMod.setColumnDataType(INT_TBL_DATCANAUT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_DATCANAUT).setCellRenderer(objTblCelRenLbl2);
          
          objTblCelRenLbl2.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLbl2.getRowRender();
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    if(str.equals("")){
                        objTblCelRenLbl2.setBackground(Color.BLUE);
                        objTblCelRenLbl2.setFont(new Font(objTblCelRenLbl2.getFont().getFontName(), Font.BOLD,   objTblCelRenLbl2.getFont().getSize()));
                        objTblCelRenLbl2.setForeground(Color.WHITE);
                    } 
                    if(str.equals("1")){
                        objTblCelRenLbl2.setBackground(Color.ORANGE);
                        objTblCelRenLbl2.setForeground(Color.BLACK);
                    } 
                    if(str.equals("2")){
                        objTblCelRenLbl2.setBackground(Color.WHITE);
                        objTblCelRenLbl2.setForeground(Color.BLACK);
                    } 
                    if(str.equals("4")){
                        objTblCelRenLbl2.setBackground(Color.YELLOW);
                        objTblCelRenLbl2.setForeground(Color.BLACK);
                    }
                    
                    if(str.equals("0")){
                        objTblCelRenLbl2.setBackground(Color.BLACK);
                        objTblCelRenLbl2.setForeground(Color.BLACK);
                    } 
                }
            });
          
          
          objTblCelRenLbl3.setBackground(objZafParSis.getColorCamposObligatorios());
          objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
          objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
          
          objTblCelRenLbl3.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_STKACT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl3);
           
          objTblCelRenLbl3.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_CANCOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl3);
          objTblCelRenLbl3.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLbl3.getRowRender();
                    
                    String strEstBod = (( (tblDat.getValueAt(intCell, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intCell, INT_TBL_ESTBOD).toString());
                    String str=tblDat.getValueAt(intCell, INT_TBL_CODEMP).toString();
                    String strCanCom = (( (tblDat.getValueAt(intCell, INT_TBL_DATCANCOM)==null) || (tblDat.getValueAt(intCell, INT_TBL_DATCANCOM).toString().equals("")))?"":tblDat.getValueAt(intCell, INT_TBL_DATCANCOM).toString());

                    if(str.equals("")){
                        objTblCelRenLbl3.setToolTipText(" "+strCanCom  );
                        objTblCelRenLbl3.setBackground(Color.BLUE);
                        objTblCelRenLbl3.setFont(new Font(objTblCelRenLbl2.getFont().getFontName(), Font.BOLD, objTblCelRenLbl2.getFont().getSize()));
                        objTblCelRenLbl3.setForeground(Color.WHITE);
                    } 
                    if(str.equals("1")){
                        if(!(strEstBod.trim().equals("V"))) {
                           objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                           objTblCelRenLbl3.setBackground(Color.LIGHT_GRAY);
                           objTblCelRenLbl3.setForeground(Color.BLACK);
                        }else{
                           objTblCelRenLbl3.setBackground(Color.ORANGE);
                           objTblCelRenLbl3.setForeground(Color.BLACK);
                        }  
                    } 
                    if(str.equals("2")){
                        if(!(strEstBod.trim().equals("V"))) {
                           objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                           objTblCelRenLbl3.setBackground(Color.LIGHT_GRAY);
                           objTblCelRenLbl3.setForeground(Color.BLACK);
                        }else{
                           objTblCelRenLbl3.setBackground(Color.WHITE);
                           objTblCelRenLbl3.setForeground(Color.BLACK);
                        }
                    }
                    if(str.equals("4")){
                        if(!(strEstBod.trim().equals("V"))) {  
                           objTblCelRenLbl3.setToolTipText(" "+objTblCelRenLbl3.getText());
                           objTblCelRenLbl3.setBackground(Color.LIGHT_GRAY);
                           objTblCelRenLbl3.setForeground(Color.BLACK);
                        }else{
                           objTblCelRenLbl3.setBackground(Color.YELLOW);
                           objTblCelRenLbl3.setForeground(Color.BLACK);
                        }
                    }
                    
                    if(str.equals("0")){
                        objTblCelRenLbl3.setBackground(Color.BLACK);
                        objTblCelRenLbl3.setForeground(Color.BLACK);
                    } 
                  
                }
           });
          
           
          
          //////////////////////////////////////////
           
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODITM);
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_ESTBOD);
            arlColHid.add(""+INT_TBL_CODBOD);
            arlColHid.add(""+INT_TBL_UNIMED);
            arlColHid.add(""+INT_TBL_NOMITM);
            arlColHid.add(""+INT_TBL_CODALT);
            arlColHid.add(""+INT_TBL_COSUNI);
            arlColHid.add(""+INT_TBL_DATCANCOM);
            arlColHid.add(""+INT_TBL_CODITMMAE);
            arlColHid.add(""+INT_TBL_INGEGRFIS);
            arlColHid.add(""+INT_TBL_TIPUNIMED);
            arlColHid.add(""+INT_TBL_ISBODPRE);/*JoseMario 1/Jul/2016 */
            
            arlColHid.add(""+INT_TBL_CODEMPCOT);/*JoseMario 10/Ago/2017 */
            arlColHid.add(""+INT_TBL_CODLOCCOT);/*JoseMario 10/Ago/2017 */
            arlColHid.add(""+INT_TBL_CODDOCCOT);/*JoseMario 10/Ago/2017 */
            arlColHid.add(""+INT_TBL_CODREGCOT);/*JoseMario 10/Ago/2017 */
            arlColHid.add(""+INT_TBL_ISITMRES);/*JoseMario 10/Ago/2017 */
            
             
            
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
             vecAux.add("" + INT_TBL_CANCOM);
            // vecAux.add("" + INT_TBL_CHKRETREM);   //José Marín M. 15/Dic/2014 
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANCOM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                boolean isItmRes=false;
                double dblTempCan=0.00;
                @Override
                public void beforeEdit(ZafTableEvent evt){                  
                    int intNumFil = tblDat.getSelectedRow();
                    if(tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)!=null){
                        dblTempCan=Double.parseDouble(tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString());
                    }
                    else{
                        dblTempCan=0.00;
                    }
                    
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        if((strEstBod.trim().equals("V"))) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        }
                        
                        if(tblDat.getValueAt(intNumFil, INT_TBL_ISITMRES)!=null){
                            if(tblDat.getValueAt(intNumFil, INT_TBL_ISITMRES).toString().equals("N")){
                                isItmRes=false;
//                                System.out.println("J: item sin reserva");
                            }
                            else{
                                isItmRes=true;
//                                System.out.println("J: item Reserva");
                            }
                        }
                    }
                }
                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                   
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        if(isItmRes){
                            MensajeInf("No se puede modificar, el items es de una reserva... ");
                            tblDat.setValueAt(dblTempCan, intNumFil,INT_TBL_CANCOM);
                        }
                        else{ /* ITEM RESERVA */
                            String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                            if((strEstBod.trim().equals("V"))) {
                                objTblCelEdiTxt.setCancelarEdicion(true);
                            }
                            else{
                                String strCodItm = ((tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null)?"0":tblDat.getValueAt(intNumFil, INT_TBL_CODITM).toString());
                                String strCodBodGrp = ((tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_NOMEMP).toString()));

                                double dlbCanIng =  objUti.redondear( tblDat.getValueAt(intNumFil,INT_TBL_CANCOM).toString(), 4 );
                                String strTipUniMed=objInvItm.getStringDatoValidado(tblDat.getValueAt(intNumFil,INT_TBL_TIPUNIMED));
                                if(strTipUniMed.equals("E")){
                                     double dlbResVal = objInvItm.Truncar( objUti.redondear( tblDat.getValueAt(intNumFil,INT_TBL_CANCOM).toString(), 4 ), 0 );
                                     dlbResVal= objUti.redondear( (dlbCanIng-dlbResVal) , 4);
                                     if(dlbResVal > 0 ){
                                        MensajeInf("SOLO SE PERMITE INGRESAR VALORES ENTEROS ");
                                        tblDat.setValueAt("0", intNumFil,INT_TBL_CANCOM);
                                     }
                                }

                                double dlbCan =  Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_STKACT)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString()));
                                double dlbCanCom = Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString()));
                                if((strEstBod.trim().equals("U"))) { // Jose Marin                            
                                    if(dlbCanCom>0)
                                        tblDat.setValueAt(true, intNumFil, INT_TBL_CHKNECAUT);  //Jose Marin 
                                    else
                                        tblDat.setValueAt(false, intNumFil, INT_TBL_CHKNECAUT); //Jose Marin 
                                }

                                if(dlbCanCom > dlbCan){
                                   MensajeInf("La cantidad es mayor a la existente.1.");
                                   tblDat.setValueAt("0", intNumFil, INT_TBL_CANCOM);
                                }
//                                if(verificaCanTotItm(strCodItm)){
//                                  MensajeInf("La cantidad es mayor a la existente.2.");
//                                  tblDat.setValueAt("0", intNumFil, INT_TBL_CANCOM);
//                                }   
                                if((strEstBod.trim().equals("C"))){ // Jose Marin - 1/Sep/2014
                                    if(dlbCanCom>0) {
                                        tblDat.setValueAt(true, intNumFil, INT_TBL_CHKRETREM);
                                    }  
                                    else {
                                        tblDat.setValueAt(false, intNumFil, INT_TBL_CHKRETREM);
                                    }  
                                }
                                String strIsBodPre=((tblDat.getValueAt(intNumFil, INT_TBL_ISBODPRE)==null)?"N":tblDat.getValueAt(intNumFil, INT_TBL_ISBODPRE).toString());
                                if(strIsBodPre.equals("N")){// NO ES BODEGA PREDETERMINADA
                                    rdoFinal.setSelected(true);
                                    permitirMoverMomentoFactura(3);// 3 BLOQUEA TODO 
                                }
                                revisaContenedorMomentoFacura();
         // <editor-fold defaultstate="collapsed" desc=" /* José Marín:  temporalmente por solicitud del Ing Eddye 17/Agosto/2016... Solo facturar al final  */ ">                       
                                /* JoséMario: temporalmente por solicitud del Ing Eddye 17/Agosto/2016 */
    //                                if(intCodForPag!=3){ // LA FORMA DE PAGO NO ES CREDITO
    //                                    permitirMoverMomentoFactura(0);
    //                                    rdoInicio.setSelected(true);
    //                                }
    //                                else{
    //                                    permitirMoverMomentoFactura(1);
    //                                    rdoFinal.setSelected(true);
    //                                }
    /* JoséMario: temporalmente por solicitud del Ing Eddye 17/Agosto/2016... Solo facturar al final    */
                         //</editor-fold>       
                            } 
                        }   /* ITEM RESERVA */
                                                
                    }                   
                    
                   
                    
                }
            });
            
            
            
            
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpInsGenFac = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        rdoFinal = new javax.swing.JRadioButton();
        rdoNA = new javax.swing.JRadioButton();
        rdoInicio = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        pancliret = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtVehRet = new javax.swing.JTextField();
        txtPlaRet = new javax.swing.JTextField();
        txtPerRet = new javax.swing.JTextField();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(100, 48));
        panTit.setLayout(null);

        lblTit.setText("titulo");
        panTit.add(lblTit);
        lblTit.setBounds(210, 7, 300, 14);

        grpInsGenFac.add(rdoFinal);
        rdoFinal.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        rdoFinal.setText("Final");
        panTit.add(rdoFinal);
        rdoFinal.setBounds(230, 24, 50, 21);

        grpInsGenFac.add(rdoNA);
        rdoNA.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        rdoNA.setSelected(true);
        rdoNA.setText("NA");
        rdoNA.setEnabled(false);
        panTit.add(rdoNA);
        rdoNA.setBounds(120, 24, 40, 21);

        grpInsGenFac.add(rdoInicio);
        rdoInicio.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        rdoInicio.setText("Inicio");
        panTit.add(rdoInicio);
        rdoInicio.setBounds(170, 24, 50, 21);

        jLabel5.setText("Emitir factura al:");
        panTit.add(jLabel5);
        jLabel5.setBounds(10, 25, 110, 14);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

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
        scrTbl.setViewportView(tblDat);

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panObs.setPreferredSize(new java.awt.Dimension(166, 40));
        panObs.setLayout(new java.awt.BorderLayout());

        txtObs.setColumns(20);
        txtObs.setLineWrap(true);
        txtObs.setRows(5);
        jScrollPane1.setViewportView(txtObs);

        panObs.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(panObs, java.awt.BorderLayout.SOUTH);

        pancliret.setLayout(null);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel1.setText("Persona que retira :");
        pancliret.add(jLabel1);
        jLabel1.setBounds(10, 30, 150, 20);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setText("Fecha que Cliente Retira:");
        pancliret.add(jLabel2);
        jLabel2.setBounds(10, 5, 150, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setText("Placa de vehiculo que retira:");
        pancliret.add(jLabel3);
        jLabel3.setBounds(340, 30, 160, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Vehiculo que retira:");
        pancliret.add(jLabel4);
        jLabel4.setBounds(340, 5, 150, 20);

        txtVehRet.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVehRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVehRetActionPerformed(evt);
            }
        });
        pancliret.add(txtVehRet);
        txtVehRet.setBounds(490, 5, 130, 20);

        txtPlaRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlaRetActionPerformed(evt);
            }
        });
        pancliret.add(txtPlaRet);
        txtPlaRet.setBounds(490, 30, 130, 20);

        txtPerRet.setBackground(objZafParSis.getColorCamposObligatorios());
        txtPerRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPerRetActionPerformed(evt);
            }
        });
        pancliret.add(txtPerRet);
        txtPerRet.setBounds(110, 30, 220, 20);

        jPanel1.add(pancliret, java.awt.BorderLayout.CENTER);

        panTabGen.add(jPanel1, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

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

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(641, 470));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
    configurarFrm();            
}//GEN-LAST:event_formWindowOpened

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
    try{
        if(CONN_GLO!=null){
            if(validaCantidadesElegidas()){
                if(validarDatCliRet()){
                    if(validaditm()){
                        if(generaContenedor()){
                            if(modificaCotizacionInstanciaGeneracionFactura(CONN_GLO,codDoc)){  
                                if(dblCanFalGlo>0){
                                    if(insertaTablaSeguimientoTransferenciaInventario(CONN_GLO,codDoc)){
                                        if(generaContenedorSolicitudTransferencia(CONN_GLO)){
                                            Librerias.ZafMovIngEgrInv.ZafReaMovInv objReaMovInv = new Librerias.ZafMovIngEgrInv.ZafReaMovInv(objZafParSis,Frame );  
                                            if(objReaMovInv.inicia(CONN_GLO, arlDat,datFecAux,arlDatSolTra,null)){
                                                blnAcepta = true; 
                                            }else{ blnAcepta = false; }
                                            objReaMovInv = null;
                                        }else{ blnAcepta = false; }
                                    }else{ blnAcepta = false; }
                                }else{ blnAcepta = true; /* NO NECESITA PRESTAMOS */}
                            }else{ blnAcepta = false; }
                        }else{ blnAcepta = false; }
                    }else{ blnAcepta = false; }
                }else{ blnAcepta = false; }
                dispose();
            }
            else{
                MensajeInf("Las cantidades ingresadas son incorrectas...");
                blnAcepta = false;
            }
        }
        else{
            blnAcepta = false; 
        }
    }  
      catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(null, Evt); 
      }
}//GEN-LAST:event_butAceActionPerformed

    /**
     * Se modifica la cotización segun el momento en que se debe generar la factura
     * 
     * @param conn
     * @param intCodCot
     * @return 
     */

    private boolean modificaCotizacionInstanciaGeneracionFactura(java.sql.Connection conn, int intCodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc; 
        String strCadena ;
        try{
            if(conn!=null){
                if(!rdoNA.isSelected()){
                    stmLoc=conn.createStatement();
                    strCadena="";
                    strCadena+=" UPDATE tbm_cabCotVen SET tx_momGenFac=";
                    if(rdoInicio.isSelected()){
                        strCadena+="'P'";
                    }
                    else if(rdoFinal.isSelected()){
                        strCadena+="'F'";
                    }
                    strCadena+=" WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" AND co_cot="+intCodCot+";";
                    stmLoc.executeUpdate(strCadena);
                    blnRes=true;
                    stmLoc.close();
                    stmLoc = null;
                }
                else{
                    blnRes=true;
                }
               
            }
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            blnRes=false;
        }
        return blnRes;
    }
    
    

    private boolean generaContenedor(){
        boolean blnRes=false;
        try{                
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                    if(tblDat.getValueAt(i, INT_TBL_ESTBOD)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("A") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("U") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("C") ){   
                            if(tblDat.getValueAt(i, INT_TBL_CANCOM)!=null) {
                                if(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().length()>0){
                                    if(tblDat.getValueAt(i, INT_TBL_ISITMRES).toString().equals("N")){// Los items que no son de reserva
                                        if(obtenerBodegaEmpresa(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NOMEMP).toString()) )){
                                            if(obtenerCantidadNecesaria( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString()),
                                                                            Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString()),
                                                                            Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NOMEMP).toString()))){
                                                if(dblCanFal>0){
                                                    arlReg = new ArrayList();
                                                    arlReg.add(INT_ARL_COD_EMP, objZafParSis.getCodigoEmpresa());
                                                    arlReg.add(INT_ARL_COD_LOC, objZafParSis.getCodigoLocal());
                                                    arlReg.add(INT_ARL_COD_TIP_DOC, intCodTipDocFacEle_ZafVen01_06);
                                                    arlReg.add(INT_ARL_COD_BOD_GRP, tblDat.getValueAt(i, INT_TBL_NOMEMP).toString()); // BODEGA DE GRUPO DE DONDE SALE LA MERCADERIA
                                                    arlReg.add(INT_ARL_COD_ITM, tblDat.getValueAt(i, INT_TBL_CODITM).toString());  // 1 
                                                    arlReg.add(INT_ARL_COD_ITM_MAE, tblDat.getValueAt(i, INT_TBL_CODITMMAE).toString());  //14 
                                                    arlReg.add(INT_ARL_COD_BOD, intCodBodEmp); // BODEGA POR EMPRESA DE DONDE SALE LA MERCADERIA
                                                    arlReg.add(INT_ARL_NOM_BOD, strNomBodEmp);
                                                    arlReg.add(INT_ARL_CAN_COM, dblCanFal);  // CAntidad para la necesidad
                                                    arlReg.add(INT_ARL_CHK_CLI_RET, tblDat.getValueAt(i, INT_TBL_CHKRETREM).toString());  // Cliente Retira
                                                    arlReg.add(INT_ARL_EST_BOD, tblDat.getValueAt(i, INT_TBL_ESTBOD).toString());  // A U C   Para saber si necesita pedir autorizaciones o debe ser cliente retira
                                                    arlReg.add(INT_ARL_ING_EGR_FIS_BOD, "IE");
                                                    arlReg.add(INT_ARL_COD_BOD_ING_MER, bodegaPredeterminada()); // BODEGA DE GRUPO DONDE INGRESA LA MERCADERIA
                                                    arlDat.add(arlReg);
                                                }
                                            }
                                        }
                                    }
                                    
                                }
                                
                            }
                        }
                    }
                }
            }
            blnRes=true;
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    } 

    
    private boolean revisaContenedorMomentoFacura(){
        boolean blnRes=true, blnNoAplica=true;
        String strIsBodPre;
    //    System.out.println("revisaContenedorMomentoFacura... ");
        try{                
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                    if(tblDat.getValueAt(i, INT_TBL_ESTBOD)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("A") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("U") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("C") ){
                            if(tblDat.getValueAt(i, INT_TBL_CANCOM)!=null && 
                                    tblDat.getValueAt(i, INT_TBL_CANCOM).toString().length()>0 && 
                                        tblDat.getValueAt(i, INT_TBL_ISBODPRE)!=null) {
                                strIsBodPre=((tblDat.getValueAt(i, INT_TBL_ISBODPRE)==null)?"N":tblDat.getValueAt(i, INT_TBL_ISBODPRE).toString());
                                if(Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString())>0.00 && strIsBodPre.equals("N")){// NO ES BODEGA PREDETERMINADA
                                    rdoFinal.setSelected(true);
                                    permitirMoverMomentoFactura(3);// 3 BLOQUEA TODO 
                                    blnNoAplica=false;
                                }
                            }
                        }
                    }
                }
            }
            if(blnNoAplica){
                permitirMoverMomentoFactura(2);// NO APLICA
            }
            
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }
    
    public ArrayList arlDatSolTra;
            
    
    private boolean generaContenedorSolicitudTransferencia(java.sql.Connection conn){
        boolean blnRes=true;
        try{          
            objTblOrd=new ZafTblOrd(tblDat);
            objTblOrd.ordenar(INT_TBL_NOMEMP); // NUevo metodo de ordenamiento en la tabla
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                    if(tblDat.getValueAt(i, INT_TBL_ESTBOD)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("A") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("U") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("C") ){   
                            if(tblDat.getValueAt(i, INT_TBL_CANCOM)!=null) {
                                //if(bodegaPredeterminadaGrupo()!=Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NOMEMP).toString())){  JM 5/Sep/2016
                                    if(obtenerCantidadNecesaria( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString()),
                                                                    Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString()),
                                                                    Integer.parseInt(tblDat.getValueAt(i, INT_TBL_NOMEMP).toString()))){
                                        if(dblCanFal>0){
                                            arlSolTraReg = new ArrayList();
                                            arlSolTraReg.add(INT_ARL_SOL_TRA_COD_BOD_ING, bodegaPredeterminadaGrupo()); // BODEGA DE GRUPO
                                            arlSolTraReg.add(INT_ARL_SOL_TRA_COD_BOD_EGR, tblDat.getValueAt(i, INT_TBL_NOMEMP).toString());  //BODEGA DE GRUPO DE DONDE SALE LA MERCA                                 
                                            arlSolTraReg.add(INT_ARL_SOL_TRA_ITM_GRP, obtenerItemEmpresaGrupo(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITMMAE).toString())) );  //14 
                                            arlSolTraReg.add(INT_ARL_SOL_TRA_CAN, dblCanFal); // 
                                            arlSolTraReg.add(INT_ARL_SOL_PES_UNI, obtenerPesoUnidad(obtenerItemEmpresaGrupo(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITMMAE).toString())) ) ); // PESO POR ITEM
                                            arlSolTraReg.add(INT_ARL_SOL_PES_TOT, Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString())  *  
                                                    obtenerPesoUnidad(obtenerItemEmpresaGrupo(Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITMMAE).toString())) )); // PESO TOTAL
                                            arlSolTraDat.add(arlSolTraReg);
                                            blnGenSolTra=true;
                                            blnRes=false; // Si se intenta generar el contenedor debe generar la solicitud para poder pasar
                                        }
                                    }                                    
                                //}
                            }
                        }
                    }
                }
            }
            if(blnGenSolTra){
                Compras.ZafCom91.ZafCom91 objZafCom91 = new Compras.ZafCom91.ZafCom91(objZafParSis,1);
                arlDatSolTra=(objZafCom91.insertarSolicitudTransferenciaPreFactura(conn,arlSolTraDat,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objVen01.CodCot()));
                System.out.println("GeneraSolicitud");
                blnRes=true;
            } 
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    } 
    
    
    
    private double obtenerPesoUnidad(int intCodItm){
        double dblPesItm=0;
        String strSql;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" select CASE WHEN a1.nd_pesItmKgr IS NULL THEN 0 ELSE a1.nd_pesItmKgr END AS nd_pesItmKgr ";
                strSql+=" from tbm_inv as a1 ";
                strSql+=" where a1.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" AND ";
                strSql+=" a1.co_itm="+intCodItm;
//                System.out.println("obtenerPesoUnidad: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    dblPesItm = rstLoc.getDouble("nd_pesItmKgr");
                }
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            dblPesItm=0;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            dblPesItm=0;
        }
        return dblPesItm;
    }
    
    private int obtenerItemEmpresaGrupo(int intCodItmMae){
        int intCodItm=0;
        String strSql;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_itm ";
                strSql+=" FROM tbm_inv as a1  ";
                strSql+=" INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSql+=" WHERE a2.co_emp="+objZafParSis.getCodigoEmpresaGrupo()+" and a2.co_itmMae ="+intCodItmMae+" \n";
                System.out.println("ZafVen01_06.obtenerItemEmpresaGrupo: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodItm = rstLoc.getInt("co_itm");
                }
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            intCodItm=0;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            intCodItm=0;
        }
        return intCodItm;
    }
    
    
    
    private int bodegaPredeterminadaGrupo(){
        int intBodPre=0;
        String strSql;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" select a2.co_emp, a2.co_bod, a2.co_empGrp, a2.co_bodGrp ";
                strSql+=" from tbr_bodloc as a1 ";
                strSql+=" inner join tbr_bodEmpBodGrp as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) ";
                strSql+=" where a1.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='P' AND ";
                strSql+=" a1.co_emp="+objZafParSis.getCodigoEmpresa() + " AND a2.co_empGrp="+objZafParSis.getCodigoEmpresaGrupo();
//                System.out.println("BODEGA PREDETERMINADA GRUPO: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intBodPre = rstLoc.getInt("co_bodGrp");
                }
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            intBodPre=0;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            intBodPre=0;
        }
        return intBodPre;
    }
    
    
    
     private int bodegaPredeterminada(){
        int intBodPre=0;
        String strSql;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" select a2.co_emp, a2.co_bod, a2.co_empGrp, a2.co_bodGrp ";
                strSql+=" from tbr_bodloc as a1 ";
                strSql+=" inner join tbr_bodEmpBodGrp as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) ";
                strSql+=" where a1.co_loc="+objZafParSis.getCodigoLocal()+" and a1.st_reg='P' AND ";
                strSql+=" a1.co_emp="+objZafParSis.getCodigoEmpresa();
//                System.out.println("ZafVen01_06:bodegaPredeterminada: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intBodPre = rstLoc.getInt("co_bodGrp");
                }
                strSql="";
                strSql="";
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            intBodPre=0;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(null, Evt);  
            intBodPre=0;
        }
        return intBodPre;
    }
    
    private Double dblCanFal=0.0;
    private Double dblCanFalGlo=0.0;
    
    /**
     *  dblCanNec: la cantidad que se pide en la ventana de pedidos
     *  intCodItm: el item que se esta trabajando, por empresa
     *  intCodBod: la bodega donde se esta pidiendo, por empresa
     *  Funcion se penso necesaria para solo enviar lo que se necesita en el contenedor, es decir lo que no posee la empresa que vende
     *  dentro de su bodega predeterminado, antes de hacer calculos se debe confirmar que se este vendiendo de la bodega predeterminada
     * 
     *  Jota
     */
    private boolean obtenerCantidadNecesaria(Double dblCanNec, int intCodItm, int intCodBod){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena;
        int intBodPre=0, intBodPreGrp=0;
        int intCodBodPre=0;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strCadena="";
            strCadena+=" SELECT * FROM tbr_bodLoc WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc=" + objZafParSis.getCodigoLocal() + " AND st_reg = 'P'";
            rstLoc = stmLoc.executeQuery(strCadena);
            if (rstLoc.next()) {
                intBodPre=rstLoc.getInt("co_bod");// BODEGA PREDETERMINADA POR EMPRESA
            }
            
            strCadena="";
            strCadena+=" SELECT * FROM tbr_bodEmpBodGrp WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_bod=" + intBodPre + " ";
            rstLoc = stmLoc.executeQuery(strCadena);
            if (rstLoc.next()) {
                intBodPreGrp=rstLoc.getInt("co_bodGrp"); // bodega de grupo 
            }
            if(intBodPreGrp==intCodBod){
                if(intBodPre!=0){
                    strCadena="";
                    strCadena+=" SELECT co_emp, co_bod, co_itm,CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END as nd_canDis ";
                    strCadena+=" FROM tbm_invBod WHERE co_emp="+objZafParSis.getCodigoEmpresa()+"";
                    strCadena+=" and co_bod="+intBodPre+" and co_itm="+intCodItm;
                    strCadena+="";
                    System.out.println("ZafVen01_06.obtenerCantidadNecesaria:.. tengo: " + strCadena);
                    rstLoc = stmLoc.executeQuery(strCadena);
                    if (rstLoc.next()) {
                        if(dblCanNec>rstLoc.getDouble("nd_canDis"))   {         /* JoseMario 12/May/2016 nd_stkAct==>nd_canDis*/
                            dblCanFal=dblCanNec-rstLoc.getDouble("nd_canDis");  // SI NECESITO PEDIR ALGO QUE NO TENGA EN EL STOCK
                            dblCanFalGlo+=dblCanFal;
                            blnRes=true;
                        }else{
                            dblCanFal=0.0;
                            blnRes=true;
                        } 
                    }
                }
            }else{
                dblCanFal=dblCanNec;
                dblCanFalGlo+=dblCanFal;
                blnRes=true;
            }
            conLoc.close();
            stmLoc.close();
            rstLoc.close();
            conLoc=null;
            stmLoc=null;
            rstLoc=null;
        }
        catch(java.sql.SQLException Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);     
            blnRes=false;
        }
        return blnRes;
    }
    

    private boolean insertaTablaSeguimientoTransferenciaInventario(java.sql.Connection conn, int intCodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
         
        String strCadena;
        try{
            if(conn!=null){
                stmLoc = conn.createStatement();
                strCadena ="";
//                strCadena+=" SELECT co_seg from tbm_cabSegMovInv WHERE ";
//                strCadena+=" co_empRelCabCotVen="+objZafParSis.getCodigoEmpresa();
//                strCadena+=" AND co_locRelCabCotVen="+objZafParSis.getCodigoLocal();
//                strCadena+=" AND co_cotRelCabCotVen="+intCodCot;
//                rstLoc = stmLoc.executeQuery(strCadena);
//                if (rstLoc.next()) {
//                    intCodSeg=rstLoc.getInt("co_seg");
//                }
                strCadena+=" UPDATE tbm_cabCotVen set st_reg='E' WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal();
                strCadena+=" AND co_cot="+intCodCot+"; ";
                
                //System.out.println("insertaTablaSeguimientoTransferenciaInventario " + strCadena);
                stmLoc.executeUpdate(strCadena);
                stmLoc.close();
                 
                stmLoc=null;
                
            }
            
        }
        catch(java.sql.SQLException Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);     
            blnRes=false;
        }
        
        return blnRes;
    }

 

/* Jose Marin  2013 : JM: YA NO SE USA 14/Agosto/2017 JM  */


public boolean validarAutoDatCliRet(){ 
   boolean blnRes=true;
   String strMsg2 = "Bodega necesita autorización ¿Desea solicitarla?";
   String strTit="Mensaje del sistema Zafiro";
   BigDecimal bdeCanCom=BigDecimal.ZERO, bdeCanAut=new BigDecimal("0");
   int intFlg=0;
   try{
       //  System.out.println("Aki iria la validación de checkbox  can autorizada");
       for(int i=0; i<tblDat.getRowCount(); i++){
            if(tblDat.getValueAt(i, INT_TBL_CHKNECAUT).toString().equals("true")){
         //       ////  System.out.println("dentro del if de necAut ");
                bdeCanCom=new BigDecimal((objTblMod.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(objTblMod.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_CANCOM).toString()));
                bdeCanAut=new BigDecimal((objTblMod.getValueAt(i, INT_TBL_DATCANAUT)==null)?"0":(objTblMod.getValueAt(i, INT_TBL_DATCANAUT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DATCANAUT).toString()));
                if(bdeCanCom.compareTo(bdeCanAut) != 0) {
                    intFlg=1;
                } 
            }
       }
       
       if(intFlg==1){
           if(JOptionPane.showConfirmDialog(this,strMsg2,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
//               ////  System.out.println("ir a isNecAutPedOtrBod ");
               con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
               if(con!=null){
                   con.setAutoCommit(false);
                   if(isNecAutPedOtrBod()) {
                       JOptionPane.showMessageDialog(this,"Su solicitud ha sido enviada",strTit,JOptionPane.INFORMATION_MESSAGE);
                       con.commit();
                       //dispose();
                       blnRes=false;
                   }
                   else {
                       con.rollback();
                       blnRes=false;
                   }
               }
           }
           else
              return false;
           dispose();
           
       }
       if(intFlg==0)
           return true; // SI ENTRA AKI NO NECESITA SER AUTORIZADO
       
   }
   catch(Exception Evt){ 
       blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); 
   }
    return blnRes;
}

private boolean isNecAutPedOtrBod(){
     BigDecimal bdeCanCom=BigDecimal.ZERO, bdeCanAut=new BigDecimal("0");
     boolean blnRes=true;
     int intAut=0,reg=0;
     String strSQLUpd="";
     try{
         if (con!=null)
         {
            stm=con.createStatement();
            //  System.out.println("Solicitar Autorizacion isNecAutPedOtrBod");
            
                    strSQL="";
                    strSQL+=" SELECT a1.*";
                    strSQL+=" FROM tbm_pedOtrBodCotVen as a1";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" and a1.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL+=" and a1.co_cot=" + codDoc;
                    strSQL+=" and a1.st_necAut='S' " ;
                    strSQL+=" ";
                    ////  System.out.println("Autorizado "+ strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                            //ya esta ingresado, pero no esta autorizado
                        intAut=1;
                    }
                    else{
                        // NO esta ingresado el pedido
                        intAut=2;
                    }
                    if(intAut==1){//ya esta ingresado, pero no esta autorizado
                        
                            strSQL="";
                            strSQL+=" DELETE FROM tbm_pedOtrBodCotVen where";
                            strSQL+=" co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQL+=" and co_loc=" + objZafParSis.getCodigoLocal();
                            strSQL+=" and co_cot=" + codDoc + ";";
                            strSQL+=" DELETE FROM tbm_detAutCotVen where";
                            strSQL+=" co_emp=" + objZafParSis.getCodigoEmpresa();
                            strSQL+=" and co_loc=" + objZafParSis.getCodigoLocal();
                            strSQL+=" and co_regNeg=16";
                            strSQL+=" and co_cot=" + codDoc + ";";
                            
//                            ////  System.out.println("SI ENTRO EN EXISTE " + strSQL);
                            strSQLUpd+=strSQL;
                    }
                        
//                    if(intAut==2) // NO esta ingresado el pedido
//                     {
                         strSQL="";
                         strSQL+=" UPDATE tbm_cabCotVen set st_reg='P' where ";
                         strSQL+=" co_emp=" + objZafParSis.getCodigoEmpresa();
                         strSQL+=" and co_loc=" + objZafParSis.getCodigoLocal();
                         strSQL+=" and co_cot=" + codDoc;
                         strSQL+=" ;";
                         strSQL+=" UPDATE tbm_cabAutCotVen set st_reg='P' where ";
                         strSQL+=" co_emp=" + objZafParSis.getCodigoEmpresa();
                         strSQL+=" and co_loc=" + objZafParSis.getCodigoLocal();
                         strSQL+=" and co_cot=" + codDoc;
                         strSQL+=" ;";
                         strSQL+=" INSERT INTO tbm_detAutCotVen (co_emp,co_loc,co_cot,st_reg,co_regNeg,st_cum,co_aut,co_reg)";
                         strSQL+=" (select co_emp,co_loc,co_cot" ;
                         strSQL+=" ,'P'";
                         strSQL+=" ,16,'N',1,MAX(co_reg)+1 from tbm_detAutCotVen where co_emp= "+ objZafParSis.getCodigoEmpresa() + " ";
                         strSQL+=" and co_loc=" + objZafParSis.getCodigoLocal() + " ";
                         strSQL+=" and co_cot=" + codDoc ;                         
                         strSQL+=" group by co_emp,co_loc,co_cot);";
                         strSQLUpd+=strSQL;
//                     }
                         int intConReg=1;
                    for(int i=1; i<objTblMod.getRowCount(); i++){
                        if(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_CODEMP)).equals("2")){
                            if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_CANCOM)).length()>0){
                                 //LENADO TABLA NUEVA
                                     strSQL="";
                                     strSQL+=" INSERT INTO tbm_pedOtrBodCotVen (co_emp,co_loc,co_cot,co_reg,co_empRel,co_bodRel,co_itmRel,st_necAut,nd_can,st_cliRet)";
                                     strSQL+=" (SELECT " + objZafParSis.getCodigoEmpresa() + ",";
                                     strSQL+=" "+ objZafParSis.getCodigoLocal() + ",";
                                     strSQL+=" "+ codDoc +",";
                                     strSQL+=" "+ intConReg +",";
                                     //strSQL+=" MAX(a2.co_reg)+1,";  // LINEA ANTERIOR 
                                     strSQL+=" "+ objZafParSis.getCodigoEmpresaGrupo() +",";
                                     strSQL+=" "+ objTblMod.getValueAt(i,INT_TBL_NOMEMP) +","; // codigo de la bodega relacionada 
                                     strSQL+=" a1.co_itm,";
                                     if(objTblMod.getValueAt(i, INT_TBL_ESTBOD).equals("U")){
                                        strSQL+=" 'S',";}
                                     else{
                                         strSQL+=" null,";}
                                     strSQL+=" "+ objTblMod.getValueAt(i,INT_TBL_CANCOM) +"";
                                     strSQL+=", ";
                                     if(tblDat.getValueAt(i, INT_TBL_CHKRETREM).equals(true)){
                                        strSQL+=" 'S'";}
                                     else{
                                         strSQL+=" null";}
                                     strSQL+=" FROM tbm_equInv as a1 LEFT OUTER JOIN tbm_pedOtrBodCotVen as a2 ON (a1.co_emp!=a2.co_cot ) ";
                                     strSQL+=" WHERE a1.co_itmMae=" + objTblMod.getValueAt(i,INT_TBL_CODITMMAE) +" and";
                                     strSQL+=" a1.co_emp="+ objZafParSis.getCodigoEmpresaGrupo() +"";
                                     strSQL+=" group by a1.co_itm );";
                                     strSQLUpd+=strSQL;
                                     intConReg++;
                             }
                        }                            
                    }
         //  System.out.println("isNecAutPedOtrBod INSERCIONES: " + strSQLUpd);
         stm.executeUpdate(strSQLUpd);            
         stm.close();
         stm=null;
         }
     }
    catch(java.sql.SQLException Evt){ 
        blnRes=false; 
        objUti.mostrarMsgErr_F1(this, Evt);     
    }
     catch(Exception Evt){ 
         blnRes=false; 
        objUti.mostrarMsgErr_F1(this, Evt); 
    }
     return blnRes;
        
}

//////////


public boolean validarDatCliRet(){
   boolean blnRes=false;
   try{
       //  System.out.println(" ZafVen01_06 validarDatCliRet");
       for(int i=0; i<tblDat.getRowCount(); i++){
         String strSelCliRet=( (tblDat.getValueAt(i, INT_TBL_CHKRETREM)==null)?"":tblDat.getValueAt(i, INT_TBL_CHKRETREM).toString() );

         if(strSelCliRet.equals("true")){
        
              blnRes=true;
              blnCliRet=true;

         }
       }

      if(blnRes){

        if(!txtFecCliRet.isFecha()){
            MensajeInf("FECHA QUE CLIENTE RETIRA");
            txtFecCliRet.requestFocus();
            return false;
        }
       if(txtVehRet.getText().trim().equals("")){
          MensajeInf("DATO DE VEHICULO DE RETIRO ESTA VACIO INGRESE INFORMACIÓN....");
          txtVehRet.requestFocus();
          return false;
       }
       if(txtPerRet.getText().trim().equals("")){
          MensajeInf("PERSONA QUE RETIRA ESTA VACIO INGRESE INFORMACIÓN....");
          txtPerRet.requestFocus();
          return false;
       }
       
       

      }else blnRes=true;


   }catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}





private void txtVehRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVehRetActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_txtVehRetActionPerformed

private void txtPlaRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlaRetActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_txtPlaRetActionPerformed

private void txtPerRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPerRetActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_txtPerRetActionPerformed

 
    
/*******************************************************************************************/

 

    public int getNumeroOrdenGrupo(java.sql.Connection con){ 
        int intgrp = 1;
        try{
            try{
                //  System.out.println(" ZafVen01_06 getNumeroOrdenGrupo");
                if (con!=null){

                    java.sql.Statement stm =con.createStatement();
                    String strSQL= " SELECT Max(ne_secgrp)+1 as grupo  FROM tbm_cabMovInv";
                    //  System.out.println(" ZafVen01_06 getNumeroOrdenGrupo " + strSQL);       
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    if(rst.next()){
                        intgrp = rst.getInt(1);
                    }
                    rst.close();
                    stm.close();
                }
            }catch(java.sql.SQLException e){
                con.close();
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return intgrp;
    }
 
      
    private boolean validaditm(){
        boolean blnRes=true;  
        String strCodItm="",strCodAlt="";
        for(int i=0; i<tblDat.getRowCount(); i++){
            if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) { 
                if( ( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
                    strCodItm = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                    strCodAlt = tblDat.getValueAt(i, INT_TBL_NOMEMP).toString();
                    if(verificaCanTotItmAce(strCodItm)){
                        MensajeInf("El item "+strCodAlt+" no cumple con la  cantidad es insuficiente para la venta."); 
                        blnRes=false;  
                        break;
                    }   
                }
            }
        }
      return blnRes;
    }         
    
    
    
    
    private boolean validacionesTabla(int indice){
        boolean blnRes=false;
        try{
            if(tblDat.getValueAt(indice, INT_TBL_ESTBOD)!=null) {
                if(tblDat.getValueAt(indice, INT_TBL_ESTBOD).toString().equals("A") || 
                    tblDat.getValueAt(indice, INT_TBL_ESTBOD).toString().equals("U") || 
                    tblDat.getValueAt(indice, INT_TBL_ESTBOD).toString().equals("C") ){   
                    if(tblDat.getValueAt(indice, INT_TBL_CANCOM)!=null) {
                        if(!tblDat.getValueAt(indice, INT_TBL_CANCOM).toString().equals("")){
                            blnRes=true;
                        }
                    }
                }
             }
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            blnRes = false; 
        }
         return blnRes;
    }
    
    
    /**
     * Validacion creada por que pueden elegir cantidades menores a las que deben solicitar
     * @return 
     */
    private boolean validaCantidadesElegidas(){
        boolean blnRes=true;
        double dblCanSol=0.00, bdlCanPedPorBod=0.00;
        String strItem="";
        try{
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                    if(i==0){
                        strItem=tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                    }
                    if(strItem.equals(tblDat.getValueAt(i, INT_TBL_CODITM).toString())){
                        if(!tblDat.getValueAt(i, INT_TBL_DATCANCOM).toString().equals("")){
                            dblCanSol=Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DATCANCOM).toString());
                        }
                        if(validacionesTabla(i)){
                            bdlCanPedPorBod = objUti.redondear(bdlCanPedPorBod + Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString()), objZafParSis.getDecimalesMostrar());
                        }
                    }else{
//                        System.out.println("dblCanSol " + dblCanSol);
//                        System.out.println("bdlCanPedPorBod" + bdlCanPedPorBod);
                        if(dblCanSol==bdlCanPedPorBod){
                            System.out.println("OK");
                            bdlCanPedPorBod=0.00;dblCanSol=0.00;
                        }
                        else{
                            System.out.println("NOT OK " + tblDat.getValueAt(i, INT_TBL_CODALT).toString());
                            blnRes=false;
                        }
                        if(i+1<tblDat.getRowCount()){
                            strItem=tblDat.getValueAt(i+1, INT_TBL_CODITM).toString();
                        }
                    }
                }
            }
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(null, Evt); 
            blnRes = false; 
        }
        return blnRes;
    }
    
    
    

    private boolean verificaCanTotItmAce(String strCodItm){
        boolean blnRes=false; 
        double dblCanTot=0.00;
        double dlbCanTotSal=0.00; 
        dlbCanTotSal = getCanFalItm(strCodItm);  
        dblCanTot = getTotComItm(strCodItm);
        System.out.println("verificaCanTotItmAce: dlbCanTotSal= " + dlbCanTotSal + " - dblCanTot: " + dblCanTot);
        if( dblCanTot > dlbCanTotSal ) return true;
        if( dblCanTot < dlbCanTotSal ) return true;
        return blnRes;   
    }
       
    private void cerrarVen(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            System.gc();
            blnAcepta=false;
            dispose();
        }
    }
    

   
    public boolean acepta(){
      return blnAcepta;
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup grpInsGenFac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel pancliret;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton rdoFinal;
    private javax.swing.JRadioButton rdoInicio;
    private javax.swing.JRadioButton rdoNA;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txtObs;
    private javax.swing.JTextField txtPerRet;
    private javax.swing.JTextField txtPlaRet;
    private javax.swing.JTextField txtVehRet;
    // End of variables declaration//GEN-END:variables

    public boolean isBlnCliRet() {
        return blnCliRet;
    }

    public void setBlnCliRet(boolean blnCliRet) {
        this.blnCliRet = blnCliRet;
    }




     private class ZafMouMotAda extends MouseMotionAdapter
    {
         @Override
        public void mouseMoved(MouseEvent evt)
        {
        int intCol=tblDat.columnAtPoint(evt.getPoint());
        String strMsg="";
        switch (intCol)
        {
            case INT_TBL_LINEA:
                strMsg="";
            break;
            
            case INT_TBL_CODITM:
                strMsg="";
            break;
            case INT_TBL_CHKRETREM:
                strMsg="Cliente retira";
            break;
            case INT_TBL_NOMEMP:
                strMsg="Código de la bodega";
            break;
            case INT_TBL_NOMBOD:
                strMsg="Nombre de la bodega";
            break;
            case INT_TBL_STKACT:
                strMsg="Stock actual";
            break;
            case INT_TBL_CANCOM:
                strMsg="Cantidad pedida a otras bodegas";
            break;
            default:
                strMsg=" ";
            break;
            case INT_TBL_CHKNECAUT:
                strMsg="Necesita autorización";
            break;
            case INT_TBL_DATCANAUT:
                strMsg="Cantidad autorizada";
            break;
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }    
    
     public String getStrEmpCompVen() {
        return strEmpCompVen;
    }

    public String getStrEmpTrans() {
        return strEmpTrans;
    }

    
    
    private boolean obtenerBodegaEmpresa(int intCodBodGrp){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena;
        try{
            conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strCadena = "";
            strCadena+= " SELECT a1.co_bod, a1.tx_nom \n";
            strCadena+= " FROM tbm_bod as a1  \n";
            strCadena+= " INNER JOIN tbr_bodEmpBodGrp as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) \n";
            strCadena+= " WHERE a2.co_bodGrp="+ intCodBodGrp +" and a1.co_emp="+objZafParSis.getCodigoEmpresa()+" \n";
            rstLoc = stmLoc.executeQuery(strCadena);
            if (rstLoc.next()) {
                intCodBodEmp=rstLoc.getInt("co_bod");
                strNomBodEmp=rstLoc.getString("tx_nom");
            }
            conLoc.close();
            stmLoc.close();
            rstLoc.close();
            conLoc=null;
            stmLoc=null;
            rstLoc=null;
        }
        catch(java.sql.SQLException Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        catch(Exception Evt){ 
            objUti.mostrarMsgErr_F1(this, Evt);     
            blnRes=false;
        }
        return blnRes;
    } 
  /* Para saber si se genero una transferencia de inventario */
    public boolean isSolTraInv(){
        return blnGenSolTra;
    }
    
    public ArrayList getSolTraInv(){
        return arlDatSolTra;
    }
    
}


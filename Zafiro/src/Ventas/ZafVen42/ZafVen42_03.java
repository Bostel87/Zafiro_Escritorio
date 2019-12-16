

/*
 * Created on 13 de agosto de 2008, 10:45   fe_retemprel
 */
           
package Ventas.ZafVen42; 
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
 * @author  Jota
 */
public class ZafVen42_03 extends javax.swing.JDialog {
    Connection CONN_GLO=null;
    Connection CONN_GLO_REM=null;
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
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
    final int INT_TBL_BODGRP=4;      //NOMBRE DE LA EMPRESA
    final int INT_TBL_NOMBOD=5;      //NOMBRE DE LA BODEGA
    final int INT_TBL_STKACT=6;      //STOCK ACTUAL 
    final int INT_TBL_ESTBOD=7;      //
    final int INT_TBL_CANCOM=8;      //CANTIDAD QUE SE COMPR
    final int INT_TBL_CODALT=9;      //CODIGO ALTERNO 
    final int INT_TBL_NOMITM=10;     //NOMBRE DEL ITEM 
    final int INT_TBL_UNIMED=11;     //UNIDAD DE MEDIDA 
    final int INT_TBL_DATCANCOM=12;  //
    final int INT_TBL_CODITMMAE=13;  //CODIGO DEL ITEM MAESTRO
    final int INT_TBL_INGEGRFIS=14;  // 
    final int INT_TBL_TIPUNIMED=15;  //TIPO DE MEDIDA
    
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
    private ArrayList arlDat;
    private ArrayList arlReg;

    StringBuffer stbDatVen=null;
    StringBuffer stbDatDevVenCom=null;
    StringBuffer stbDatInsVen=null;
    int intDatVen=0;
    int intDatDevVenCom=0;
     
    String StrEstConfDevVen="P";
    String strNomBodIng="";

    int codDoc;
    StringBuffer stbDatDevCom=null;
    StringBuffer stbDatDevVen=null;
    int intDatDevCom=0;
    int intDatDevVen=0;
    
    private String strTitu="Pedidos a otras bodegas";
    private String strVersion=" v0.02";
    
    
    ArrayList arlItmRec;
 
    
//    private final boolean blnReaDevComVen=true;
    private final boolean blnReaDevComVen=false;  /// NO DEVOLUCIONES EN VENTA
    
    private boolean blnGenFacIni=false;
   
    private String strEmpCompVen="";
    private String strEmpTrans="";
    

/* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 21/Sep/2015  */
   private final int INT_ARL_COD_EMP=0;
   private final int INT_ARL_COD_LOC=1;
   private final int INT_ARL_COD_COT=2;   
   private final int INT_ARL_COD_ITM=3;
   private final int INT_ARL_CAN_COM=4;
   private final int INT_ARL_COD_EMP_EGR=5;
   private final int INT_ARL_COD_BOD_EMP_EGR=6;
   private final int INT_ARL_COD_ITM_EGR=7;
   private final int INT_ARL_CAN_UTI=8;
   private final int INT_ARL_COD_BOD_EGR_GRP=9;
   
   
   
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
   

    public ZafVen42_03() {
    }
    
    private int intCodEmpGenVen, intCodLocGenVen, intCodCotGenVen;
    /** Creates new form pantalla dialogo */
    public ZafVen42_03(Frame parent,  ZafParSis ZafParSis, int intCodEmp, int intCodLoc, int intCodCot) {
        super(parent, true);
        try{ 
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
          objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
          

          objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
          objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

          ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
          stbLisBodItm= new StringBuffer();
          stbDocRelEmp= new StringBuffer();

          initComponents();
          intCodEmpGenVen=intCodEmp;intCodLocGenVen=intCodLoc;intCodCotGenVen=intCodCot;
          configurarFrm();       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
private void configurarFrm(){
     
      configuraTbl();      
      cargarDat(intCodEmpGenVen,intCodLocGenVen,intCodCotGenVen);
      
      if(intEstReaOcFac==0){
        blnAcepta = true;
        dispose();
      }
      
} 
  
    
private int  intCodTipDocFacEle_ZafVen01_06 = 228;

 
    private boolean cargarDat(  int intCodEmp, int intCodLoc, int intCodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
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
             System.out.println("cargarDat");
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stbDev=new StringBuffer();
                stbDevInv=new StringBuffer();
                stbDatCot=new StringBuffer();
                stmLoc01=conLoc.createStatement();
                stmLoc02=conLoc.createStatement();
                stmLoc03=conLoc.createStatement();

                stmLoc=conLoc.createStatement();  
                stmLocDat=conLoc.createStatement();  
                

                vecData = new Vector();

                 if(objParSis.getCodigoEmpresa() != 1)
                    strAuxDesc=" desc ";


                strSql= " SELECT  a.co_Emp, a.co_itm, a1.tx_codalt , a1.tx_nomitm, var.tx_descor, a.co_bod, a1.nd_prevta1, a1.nd_cosuni, a.nd_can as can, " ;
                strSql+="         CASE WHEN ( (trim(SUBSTR (UPPER(a1.tx_codalt), length(a1.tx_codalt) ,1))    " ;
                strSql+="             IN ( SELECT UPPER(trim(tx_cad)) FROM tbm_reginv ";
                strSql+="                  WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+"  AND co_tipdoc="+ intCodTipDocFacEle_ZafVen01_06 +"";
                strSql+="                        AND co_usr="+objParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I'  ))) ";
                strSql+="         THEN 'S' ELSE 'N' END  as isterL, "; 
                strSql+="         CASE WHEN var.tx_tipunimed IS NULL THEN 'F' ELSE var.tx_tipunimed end AS txtipunimed  "; 
                strSql+=" FROM tbm_inv AS a1 ";
                strSql+=" INNER JOIN ( " ;
                strSql+="             SELECT co_emp, co_itm, co_bod, sum(nd_can) as nd_can, min(co_reg) AS co_reg " ;
                strSql+="             FROM tbm_detcotven " ;
                strSql+="             WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_cot="+intCodCot+" /*AND tx_codAlt like '%I'*/ "; /*JM: 28/Ago/2017 si solo queria items terminal I*/
                strSql+="             GROUP BY co_emp, co_itm, co_bod ";
                strSql+="             ORDER BY co_reg ) AS a ON( a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm) " ;
                strSql+=" LEFT JOIN tbm_var AS var ON(var.co_reg=a1.co_uni)  " ;
                strSql+=" WHERE a1.st_ser='N'  ";
                  System.out.println("ZafVenXX_03 cargarDat " + strSql);
                rstLocDat=stmLocDat.executeQuery(strSql);
                while(rstLocDat.next()){
                    if(intEstDatCot==1) stbDatCot.append(" UNION ALL ");
                    stbDatCot.append(" SELECT "+rstLocDat.getInt("co_emp")+" as co_emp ,"+rstLocDat.getInt("co_itm")+" as co_itm,text('"+rstLocDat.getString("tx_codalt")+"') as tx_codalt, " +
                                     " "+objUti.codificar(rstLocDat.getString("tx_nomitm"))+" as tx_nomitm, text('"+rstLocDat.getString("tx_descor")+"') as tx_descor, "+rstLocDat.getInt("co_bod")+" as co_bod, " +
                                     " "+rstLocDat.getDouble("nd_prevta1")+" as nd_prevta1,  "+rstLocDat.getDouble("nd_cosuni")+" as nd_cosuni, "+rstLocDat.getDouble("can")+" as can,  text('"+rstLocDat.getString("isterl")+"') as isterl "
                                     + " , text('"+rstLocDat.getString("txtipunimed")+"') as txtipunimed  ");
                    intEstDatCot=1;

                }

                rstLocDat.close();
                rstLocDat=null;
                stmLocDat.close();
                stmLocDat=null;
          /*      
           * José Marín M.   1/Sep/2014
           * '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
           *                              CANTIDAD BODEGA PREDETERMINADA
           * '''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
           * SE RESTA LO QUE TENEMOS EN BODEGA PREDETERMINADA
           */    
                strSql= " SELECT *, CASE WHEN  (TRIM(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1)) in ('S')) then 'S' else 'N' end as Term    \n" ;
                strSql+=" FROM ( \n";
                strSql+="         SELECT a2.co_itmmae, x.co_emp, x.co_itm, x.co_bod, x.tx_codalt, x.tx_descor, x.tx_nomitm, a1.nd_canDis , x.can, \n";
                strSql+="                CASE WHEN  x.txtipunimed = 'F' THEN abs(a1.nd_canDis - x.can) \n";
                strSql+= "                ELSE abs(TRUNC(a1.nd_canDis) - x.can)  END  AS canfal , \n" ;
                strSql+="                 x.nd_prevta1, x.nd_cosuni, x.txtipunimed     \n" ;
                strSql+="         FROM(     ";
                strSql+="                 SELECT * FROM ( " ;
                strSql+=" \n";

                strSql+=   stbDatCot.toString();
                strSql+="                               ) as x WHERE x.isterl='S'  " ;
                strSql+="             ) as x " ;
                strSql+="         INNER JOIN tbm_invbod AS a1 ON(a1.co_emp=x.co_emp AND a1.co_bod=x.co_bod AND a1.co_itm=x.co_itm )  ";
                strSql+="         INNER JOIN tbm_equinv AS a2 ON(a2.co_emp=x.co_emp and a2.co_itm=x.co_itm ) " ;
                strSql+=" ) AS x  /* WHERE  (nd_canDis - can)  < 0*/ "; //José Marín 
                System.out.println("ZafVenXX_03.cargarDat: "+strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                     Glo_dblCanFalCom=rstLoc.getDouble("can");  // PARA QUE PIDA COMO QUE NO TIENE NADA  
                     if(Glo_dblCanFalCom > 0){    
                            java.util.Vector vecReg2 = new java.util.Vector();
                           vecReg2.add(INT_TBL_LINEA,"");
                           vecReg2.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
                           vecReg2.add( INT_TBL_CODEMP, "" );
                           vecReg2.add( INT_TBL_CODBOD, "" );
                           // 
                           vecReg2.add( INT_TBL_BODGRP, rstLoc.getString("tx_codalt") );
                           //
                           vecReg2.add( INT_TBL_NOMBOD, rstLoc.getString("tx_nomitm") );
                           vecReg2.add( INT_TBL_STKACT,"" );
                           vecReg2.add( INT_TBL_ESTBOD,"" );
                           vecReg2.add( INT_TBL_CANCOM,  ""+Glo_dblCanFalCom );
                           vecReg2.add( INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
                           vecReg2.add( INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
                           vecReg2.add( INT_TBL_UNIMED, rstLoc.getString("tx_descor") );


                           vecReg2.add( INT_TBL_DATCANCOM ,""+Glo_dblCanFalCom );
                           vecReg2.add( INT_TBL_CODITMMAE ,"" );
                           vecReg2.add( INT_TBL_INGEGRFIS ,"" );

                           vecReg2.add( INT_TBL_TIPUNIMED , rstLoc.getString("txtipunimed") );


                          vecData.add(vecReg2);    
                           strSql="";
                           strSql+=" SELECT DISTINCT a.stk,a.canIng,a.st_reg,a.tx_nom,a.co_bodGrp,a.ingEgrFis , \n";
                           strSql+=" CASE WHEN a.st_regBodPre IS NULL THEN 'N' ELSE a.st_regBodPre END AS st_regBodPre \n";
                           strSql+=" FROM ( \n";
                           strSql+="       SELECT x.stk, x.caning, x.st_reg, x.tx_nom, x.co_bodgrp  , \n";
                           strSql+="              case when  a.co_empgrp is not null then 'N' else 'S' end as ingegrfis, b.st_reg as st_regBodPre  \n        ";
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
                           strSql+="                   WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.st_reg NOT IN ('I') \n ";
                           strSql+="                   ORDER BY a.co_empper, a.co_bodper   \n";
                           strSql+="                   ) as x  \n";
                           strSql+="           INNER JOIN  tbr_bodEmpBodGrp as x1 on (x1.co_emp=x.co_empper and x1.co_bod=x.co_bodper ) \n";
                           strSql+="           INNER JOIN tbm_bod as x2 ON (x2.co_emp=x1.co_empgrp and x2.co_bod=x1.co_bodgrp )  \n \n";
                           strSql+="           GROUP BY  x.st_reg, x2.tx_nom, x1.co_bodgrp \n" ;
                           strSql+="           ) as x \n" ;
                           strSql+="        LEFT JOIN tbr_bodEmpBodGrp as a on ( a.co_emp="+intCodEmp+" and \n";
                           strSql+="                                             a.co_bod="+intCodBodPre+" and a.co_empgrp="+objParSis.getCodigoEmpresaGrupo()+" and  \n" ;
                           strSql+="                                             a.co_bodgrp=x.co_bodgrp )  \n";
                           strSql+="        LEFT JOIN tbr_bodLoc as b ON (a.co_emp=b.co_emp  and b.co_loc="+objParSis.getCodigoLocal()+" and a.co_bod=b.co_bod)  \n"; /*JoseMario 20/Jul/2016*/
                           strSql+="        ORDER BY x.co_bodgrp \n";
                           strSql+=" ) as a \n";
                           strSql+=" \n ORDER BY a.co_bodgrp";
                           System.out.println("<<<<<<<<<<<<<<<<<<<<<  ZafVen01_06.cargarDat  . ..  Modificar: "+strSql );

                           rstLoc01=stmLoc01.executeQuery(strSql);
                           while(rstLoc01.next()){ 
                               java.util.Vector vecReg = new java.util.Vector();
                               vecReg.add(INT_TBL_LINEA,"");
                               vecReg.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
                               vecReg.add( INT_TBL_CODEMP, "2"); // rstLoc01.getString("co_empper") );
                               vecReg.add( INT_TBL_CODBOD, ""); //rstLoc01.getString("co_bodper") );
                               vecReg.add( INT_TBL_BODGRP, rstLoc01.getString("co_bodgrp") );  //rstLoc01.getString("tx_nom") );
                               vecReg.add( INT_TBL_NOMBOD, rstLoc01.getString("tx_nom") );  //rstLoc01.getString("nombod") );
                               vecReg.add( INT_TBL_STKACT, rstLoc01.getString("stk") );
                               vecReg.add( INT_TBL_ESTBOD, rstLoc01.getString("st_reg") );
                               vecReg.add( INT_TBL_CANCOM,  ""); // --- Jose Marin 
                               vecReg.add( INT_TBL_CODALT,  rstLoc.getString("tx_codalt") );
                               vecReg.add( INT_TBL_NOMITM,  rstLoc.getString("tx_nomitm") );
                               vecReg.add( INT_TBL_UNIMED,  rstLoc.getString("tx_descor") );
                               vecReg.add( INT_TBL_DATCANCOM ,"");
                               vecReg.add( INT_TBL_CODITMMAE,  rstLoc.getString("co_itmmae") );
                               vecReg.add( INT_TBL_INGEGRFIS, rstLoc01.getString("ingegrfis") );
                               vecReg.add( INT_TBL_TIPUNIMED , rstLoc.getString("txtipunimed")  );
                               vecData.add(vecReg);
                            }
                            rstLoc01.close();
                            rstLoc01=null;
                            vecReg2 = new java.util.Vector();
                            vecReg2.add(INT_TBL_LINEA,"");
                            vecReg2.add( INT_TBL_CODITM, "" );
                            vecReg2.add( INT_TBL_CODEMP, "0" );
                            vecReg2.add( INT_TBL_CODBOD, "" );
                            vecReg2.add( INT_TBL_BODGRP, "" );
                            vecReg2.add( INT_TBL_NOMBOD, "" );
                            vecReg2.add( INT_TBL_STKACT,"" );
                            vecReg2.add( INT_TBL_ESTBOD,"" );
                            vecReg2.add( INT_TBL_CANCOM,"" );
                            vecReg2.add( INT_TBL_CODALT, "" );
                            vecReg2.add( INT_TBL_NOMITM, "" );
                            vecReg2.add( INT_TBL_UNIMED, "" );
                            vecReg2.add( INT_TBL_DATCANCOM ,"");
                            vecReg2.add( INT_TBL_CODITMMAE ,"" );
                            vecReg2.add( INT_TBL_INGEGRFIS ,"" );
                            vecReg2.add( INT_TBL_TIPUNIMED ,"" );
                            vecReg2.add( INT_TBL_TIPUNIMED ,"N" );
                            vecData.add(vecReg2);    
                           intEstReaOcFac=1;
                      }  
                }  
                rstLoc.close();
                rstLoc=null;


                 stbDev=null;
                 stbDevInv=null;
                 stbDatCot=null;

                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);         


              conLoc.close();
              conLoc=null;

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
        //  System.out.println(" ZafVen01_06 verificaCanTotItm");
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
          }}}

        dblCanTot = objUti.redondear(dblCanTot, 6);
        //  System.out.println("dblCanTot-> "+dblCanTot +" dlbCanTotSal-> "+dlbCanTotSal );


        if( dblCanTot > dlbCanTotSal ) blnRes=true;
       return blnRes;   
    }




private double getTotComItm(String strCodItm){
  double dblCanTot=0;
  String strCanSal ="";
  String strCodItm2="";
  //  System.out.println(" ZafVen01_06 getTotComItm");
   for(int i=0; i < tblDat.getRowCount(); i++){ 
    if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
     strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
     if(strCodItm2.equals(strCodItm)){
      if(!( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
          strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
          dblCanTot += objUti.redondear(strCanSal, 6);
      }
    }}}

  dblCanTot = objUti.redondear(dblCanTot, 6);
 return dblCanTot;   
}



private double getCanFalItm(String strCodItm){
  double dblCanTot=0;
  String strCanSal ="";
  String strCodItm2="";
  //  System.out.println(" ZafVen01_06 getCanFalItm");
   for(int i=0; i < tblDat.getRowCount(); i++){ 
    if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
     strCodItm2 = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
     if(strCodItm2.equals(strCodItm)){
      if(( (tblDat.getValueAt(i, INT_TBL_CODEMP)==null) || (tblDat.getValueAt(i, INT_TBL_CODEMP).toString().equals("")) )   ) {
          strCanSal = ((tblDat.getValueAt(i, INT_TBL_CANCOM)==null)?"0":(tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_CANCOM).toString()));
          dblCanTot = objUti.redondear(strCanSal, 6); 
      }
    }}}
 return dblCanTot;   
}

 
private void MensajeInf(String strMensaje){
 //JOptionPane obj =new JOptionPane();
 String strTit="Mensaje del sistema Zafiro";
 JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
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
            vecCab.add(INT_TBL_BODGRP,"Cód.Bod");
            vecCab.add(INT_TBL_NOMBOD,"Bodega");
            vecCab.add(INT_TBL_STKACT,"Stock");
            vecCab.add( INT_TBL_ESTBOD,"EST.BOD." ); // V A U : Jose Marin //C Cliente retira 3/Sep/2014
            vecCab.add( INT_TBL_CANCOM,"Can.Ped." );
            vecCab.add( INT_TBL_CODALT,"Cód.Itm" );
            vecCab.add( INT_TBL_NOMITM,"Nom.Itm" );
            vecCab.add( INT_TBL_UNIMED,"Uni.Med" );
            vecCab.add( INT_TBL_DATCANCOM ,"");
            vecCab.add( INT_TBL_CODITMMAE ,"");
            vecCab.add( INT_TBL_INGEGRFIS ,"" );
            vecCab.add( INT_TBL_TIPUNIMED ,"TipUniMed" );
            
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
            tcmAux.getColumn(INT_TBL_BODGRP).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_STKACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANCOM).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_TIPUNIMED).setPreferredWidth(10);

            /////////////////////////////////////
            
          objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
          tcmAux.getColumn(INT_TBL_NOMBOD).setCellRenderer(objTblCelRenLbl);
          tcmAux.getColumn(INT_TBL_BODGRP).setCellRenderer(objTblCelRenLbl);
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
             
          objTblCelRenLbl2.setBackground(objParSis.getColorCamposObligatorios());
          objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
          objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
          objTblCelRenLbl2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_STKACT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl2);
          objTblMod.setColumnDataType(INT_TBL_CANCOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl2);
           
          
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
                   //objTblCelRenLbl2.setToolTipText(objTblCelRenLbl.getText());
                }
            });
           
          objTblCelRenLbl3.setBackground(objParSis.getColorCamposObligatorios());
          objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
          objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
          
          objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
          objTblMod.setColumnDataType(INT_TBL_STKACT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
          tcmAux.getColumn(INT_TBL_STKACT).setCellRenderer(objTblCelRenLbl3);
           
          objTblCelRenLbl3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
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
                    }}
                    
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
           
            arlColHid.add(""+INT_TBL_DATCANCOM);
            arlColHid.add(""+INT_TBL_CODITMMAE);
            arlColHid.add(""+INT_TBL_INGEGRFIS);
            arlColHid.add(""+INT_TBL_TIPUNIMED);
         
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
                @Override
                public void beforeEdit(ZafTableEvent evt){                  
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        
                        if((strEstBod.trim().equals("V"))) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        } 
                    }
                }
                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                   
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        String strEstBod = (( (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString().equals("")))?"V":tblDat.getValueAt(intNumFil, INT_TBL_ESTBOD).toString());
                        
                        if((strEstBod.trim().equals("V"))) {
                            objTblCelEdiTxt.setCancelarEdicion(true);
                         }else{
                        
                            String strCodItm = ((tblDat.getValueAt(intNumFil, INT_TBL_CODITM)==null)?"0":tblDat.getValueAt(intNumFil, INT_TBL_CODITM).toString());
                            String strCodBodGrp = ((tblDat.getValueAt(intNumFil, INT_TBL_BODGRP)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_BODGRP).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_BODGRP).toString()));
                    

                           /***************************************************************************************/
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
                          /***************************************************************************************/



                            double dlbCan =  Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_STKACT)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_STKACT).toString()));
                            double dlbCanCom = Double.parseDouble((((tblDat.getValueAt(intNumFil, INT_TBL_CANCOM)==null) || (tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString().equals("")))?"0":tblDat.getValueAt(intNumFil, INT_TBL_CANCOM).toString()));
                           
                            //  System.out.println("muestra >>>>>>>>>>>>>>>>>>>>< " + strCodBodGrp);
                            
                            if(dlbCanCom > dlbCan){
                               MensajeInf("La cantidad es mayor a la existente.1.");
                               tblDat.setValueAt("0", intNumFil, INT_TBL_CANCOM);
                            }
                            if(verificaCanTotItm(strCodItm)){
                              MensajeInf("La cantidad es mayor a la existente.2.");
                              tblDat.setValueAt("0", intNumFil, INT_TBL_CANCOM);
                            }   
                        }                         
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
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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

        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-641)/2, (screenSize.height-470)/2, 641, 470);
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
         
}//GEN-LAST:event_formWindowOpened

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
     
    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
    try{
        blnAcepta = false;
        if(validaCantidadesElegidas()){
            if(generaContenedor()){
                System.out.println(">>> = ZafVen42_03 arlDat " + arlDat.toString());
                blnAcepta = true; 
            }
        }else{
            MensajeInf("Las cantidades ingresadas son incorrectas...");
        }
                
        
         //dispose();
        this.setVisible(false);
    }  
      catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(null, Evt); 
          blnAcepta = false; 
      }
}//GEN-LAST:event_butAceActionPerformed

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
                            bdlCanPedPorBod = bdlCanPedPorBod + Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString());
                        }
                    }else{
                        
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
     * 
     * @return 
     */
    
    private boolean generaContenedor(){
        boolean blnRes=true;
        try{          
            arlDat = new ArrayList();
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i, INT_TBL_CODITM)!=null) {
                    if(tblDat.getValueAt(i, INT_TBL_ESTBOD)!=null) {
                        if(tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("A") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("U") || 
                            tblDat.getValueAt(i, INT_TBL_ESTBOD).toString().equals("C") ){   
                            if(tblDat.getValueAt(i, INT_TBL_CANCOM)!=null) {
                                if(!tblDat.getValueAt(i, INT_TBL_CANCOM).toString().equals("")){
                                    if(obtenerCantidadNecesaria( Double.parseDouble(tblDat.getValueAt(i, INT_TBL_CANCOM).toString()),
                                                                    Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITMMAE).toString()),
                                                                    Integer.parseInt(tblDat.getValueAt(i, INT_TBL_BODGRP).toString()),
                                                                    Integer.parseInt(tblDat.getValueAt(i, INT_TBL_CODITM).toString()))   ){
                                    
                                        
                                        blnRes=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    } 

    
    public ArrayList getArlDatRes() {
        return arlDat;
    }
    
    public ArrayList arlDatSolTra;
            
    
    private Double dblCanFal=0.0;
    private Double dblCanFalGlo=0.0;
    private String strSql;
    /**
     *  dblCanNec: la cantidad que se pide en la ventana de pedidos
     *  intCodItm: el item que se esta trabajando, por empresa
     *  intCodBod: la bodega donde se esta pidiendo, por empresa
     *  Funcion se penso necesaria para solo enviar lo que se necesita en el contenedor, es decir lo que no posee la empresa que vende
     *  dentro de su bodega predeterminado, antes de hacer calculos se debe confirmar que se este vendiendo de la bodega predeterminada
     * 
     *  Jota
     */
    private boolean obtenerCantidadNecesaria(Double dblCanNec, int intCodItmMae, int intCodBodGrp, int intCodItmEmp){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena;
        int i=0;
        double  dblCanVenEmp=0.0, dblCanTra;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            
            
            System.out.println("CANTIDAD: " + dblCanNec + " ITEM MAE: " + intCodItmMae + " BODEGA GRUPO " + intCodBodGrp);
           
            if(dblCanNec>0){
                strSql= " SELECT *   \n";
                strSql+=" FROM ( \n";
                strSql+="     SELECT a.co_emp , a.co_bod, a.co_itm  \n";
                strSql+="            , CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END)  \n";
                strSql+="               ELSE CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END END AS nd_canDis ";
                strSql+="     FROM tbm_invbod as a \n" ;
                strSql+="     INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod)  \n";
                strSql+="     INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm )   \n";
                strSql+="     LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni ) \n";
                strSql+="     INNER JOIN tbm_equInv as equInv ON (a.co_emp=equInv.co_emp AND a.co_itm=equInv.co_itm)  \n";
                strSql+="     WHERE a.co_emp="+intCodEmpGenVen+" AND equInv.co_itmMae= "+intCodItmMae;
                strSql+="           AND ( a1.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.co_bodGrp= "+intCodBodGrp+"   ) \n";
                strSql+=" ) AS x \n  ";
                strSql+=" WHERE nd_canDis > 0 \n";
                strSql+=" ORDER BY nd_canDis DESC \n";
                System.out.println("TRANSFERENCIA \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql); 
                while(rstLoc.next()){ 
                    if( rstLoc.getDouble("nd_canDis") >= dblCanNec ){
                        dblCanTra=dblCanNec;
                        dblCanNec=0.00;  // YA NO SE NECESITA NADA MAS
                    }else{ // SI EN EL STOCK HAY MENOS SE IRA POR TRANSFERENCIA TODO Y LA DIFERENCIA POR PRESTAMO
                       dblCanTra=rstLoc.getDouble("nd_canDis");// TODO EN TRANSFERENCIA
                       dblCanNec=dblCanNec - dblCanTra; //PARA SABER CUANTO QUEDARIA PENDIENTE DESPUES DE LA TRANSFERENCIA
                    }
                    // GUARDAR RESERVA aki mete nuevo proceso
                    arlReg = new ArrayList();
                    arlReg.add(INT_ARL_COD_EMP, intCodEmpGenVen);
                    arlReg.add(INT_ARL_COD_LOC, intCodLocGenVen);
                    arlReg.add(INT_ARL_COD_COT, intCodCotGenVen);
                    arlReg.add(INT_ARL_COD_ITM, intCodItmEmp);  // CODIGO DEL ITEM POR EMPRESA 
                    arlReg.add(INT_ARL_CAN_COM, dblCanTra);  // CAntidad para la necesidad
                    arlReg.add(INT_ARL_COD_EMP_EGR, rstLoc.getInt("co_emp"));  // EMPRESA DE EGRESO
                    arlReg.add(INT_ARL_COD_BOD_EMP_EGR, rstLoc.getInt("co_bod")); // BODEGA POR EMPRESA DE DONDE SALE LA MERCADERIA
                    arlReg.add(INT_ARL_COD_ITM_EGR, rstLoc.getInt("co_itm"));  // CODIGO DEL ITEM POR EMPRESA 
                    arlReg.add(INT_ARL_CAN_UTI, 0.00);  // CAntidad usada 
                    arlReg.add(INT_ARL_COD_BOD_EGR_GRP, intCodBodGrp);
                    arlDat.add(arlReg);
                    i++;
                    System.out.println("Transferencia: " + rstLoc.getDouble("nd_canDis"));
                }
                rstLoc.close();
            
                if(dblCanNec>0){ /* DESPUES DE LA TRANSFERENCIA ALGO SIGUE PENDIENTE ENTRA EN PRESTAMOS O VENTAS ENTRE EMPRESA */
                    strCadena="";
                    strCadena+=" SELECT *  \n";
                    strCadena+=" FROM (  \n";
                    strCadena+="        SELECT a.co_emp , a.co_bod, a.co_itm  \n";
                    strCadena+="               ,CASE WHEN var.tx_tipunimed = 'E' THEN  TRUNC(CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END)   \n";
                    strCadena+="               ELSE CASE WHEN a.nd_canDis IS NULL THEN 0 ELSE a.nd_canDis END END AS nd_canDis   \n";
                    strCadena+="        FROM tbm_invbod as a  \n";
                    strCadena+="        INNER JOIN tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm )     \n";
                    strCadena+="        INNER JOIN tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod)  \n";
                    strCadena+="        INNER JOIN tbm_inv AS inv ON(inv.co_emp=a.co_emp AND inv.co_itm=a.co_itm )     \n";
                    strCadena+="        LEFT JOIN tbm_var AS var ON(var.co_reg=inv.co_uni )  \n";
                    strCadena+="        WHERE a.co_emp !="+intCodEmpGenVen+" AND ( a1.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.co_bodGrp= "+intCodBodGrp+")   \n";
                    strCadena+="        AND a2.co_itmmae= "+intCodItmMae+" \n";
                    strCadena+="       ) AS x \n ";
                    strCadena+=" WHERE nd_canDis > 0  \n";
                    strCadena+=" ORDER BY nd_canDis Desc \n";
                    strCadena+=" \n";
                    System.out.println("COMPRA VENTA DE DONDE SE TOMA \n " + strCadena);
                    rstLoc=stmLoc.executeQuery(strCadena);
                    while(rstLoc.next()){
                        if( rstLoc.getDouble("nd_canDis") >= dblCanNec ){ // SI HAY MAS EN STOCK 
                            dblCanVenEmp=dblCanNec;
                            dblCanNec=0.0;
                        }
                        else{
                            dblCanVenEmp = rstLoc.getDouble("nd_canDis");  
                           dblCanNec = dblCanNec - rstLoc.getDouble("nd_canDis");  // Sigue faltando algo                               
                           dblCanNec=dblCanNec<0?dblCanNec*-1:dblCanNec; // SI ES NEGATIVA LA CANTIDAD 
                        }
                        if(dblCanVenEmp>0){  // si todavia hay algo pendiente
                           // GUARDAR RESERVA aki mete nuevo proceso
                            arlReg = new ArrayList();
                            arlReg.add(INT_ARL_COD_EMP, intCodEmpGenVen);
                            arlReg.add(INT_ARL_COD_LOC, intCodLocGenVen);
                            arlReg.add(INT_ARL_COD_COT, intCodCotGenVen);
                            arlReg.add(INT_ARL_COD_ITM, intCodItmEmp);  // CODIGO DEL ITEM POR EMPRESA 
                            arlReg.add(INT_ARL_CAN_COM, dblCanVenEmp);  // CAntidad para la necesidad
                            arlReg.add(INT_ARL_COD_EMP_EGR, rstLoc.getInt("co_emp"));  // EMPRESA DE EGRESO
                            arlReg.add(INT_ARL_COD_BOD_EMP_EGR, rstLoc.getInt("co_bod")); // BODEGA POR EMPRESA DE DONDE SALE LA MERCADERIA
                            arlReg.add(INT_ARL_COD_ITM_EGR, rstLoc.getInt("co_itm"));  // CODIGO DEL ITEM POR EMPRESA 
                            arlReg.add(INT_ARL_CAN_UTI, 0.00);  // CAntidad usada 
                            arlReg.add(INT_ARL_COD_BOD_EGR_GRP, intCodBodGrp);
                            arlDat.add(arlReg);
                            i++;
                        }
                        dblCanVenEmp=0.0; // Ya se envio ese dato de regresa a 0 para no enviar cuando ya tenga todo pedido
                    }
                    rstLoc.close();
                }
            
            
                blnRes=true;
                conLoc.close();
                stmLoc.close();

                conLoc=null;
                stmLoc=null;
                rstLoc=null;
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
    
    




/*******************************************************************************************/



private boolean verificaCanTotItmAce(String strCodItm){
    //  System.out.println(" ZafVen01_06 verificaCanTotItmAce");
  boolean blnRes=false; 
  double dblCanTot=0;
  double dlbCanTotSal=3; 
   
   dlbCanTotSal = getCanFalItm(strCodItm);  
   dblCanTot = getTotComItm(strCodItm);
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
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
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
             
            case INT_TBL_BODGRP:
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
            
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }    
 

    
    
    private boolean obtenerBodegaEmpresa(int intCodBodGrp){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strCadena;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strCadena = "";
            strCadena+= " SELECT a1.co_bod, a1.tx_nom \n";
            strCadena+= " FROM tbm_bod as a1  \n";
            strCadena+= " INNER JOIN tbr_bodEmpBodGrp as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) \n";
            strCadena+= " WHERE a2.co_bodGrp="+ intCodBodGrp +" and a1.co_emp="+intCodEmpGenVen+" \n";
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
  
    
}


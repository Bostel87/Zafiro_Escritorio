

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
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
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
public class ZafVen42_07 extends javax.swing.JDialog {
    Connection CONN_GLO=null;
    Connection CONN_GLO_REM=null;
    private String strSQL, strSql="";
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafUtil objUti;
    private Connection con;
    private java.util.Date datFecAux;      
    private Statement stm;
    private ResultSet rst;
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODITM=1;      //CODIGO DEL ITEM 
    final int INT_TBL_CODALT=2;      //CODIGO ALTERNO 
    final int INT_TBL_NOMITM=3;     //NOMBRE DEL ITEM 
    final int INT_TBL_CANCOM=4;      //CANTIDAD QUE SE COMPR
    final int INT_TBL_COD_EMP=5;      //CODIGO DE LA EMPRESA
    final int INT_TBL_COD_REG=6;      //CODIGO DE REGISTRO
    final int INT_TBL_BODGRP=7;      //NOMBRE DE LA EMPRESA
    final int INT_TBL_NOMBOD=8;      //NOMBRE DE LA BODEGA
    final int INT_TBL_CAN_PED_OTR_BOD=9;      //STOCK ACTUAL 
    final int INT_TBL_ESTBOD=10;      //
    
    final int INT_TBL_COD_BOD_PRE=11;     //Bodega tbm_detCotVen
    final int INT_TBL_DATCANCOM=12;  //
    final int INT_TBL_CODITMMAE=13;  //CODIGO DEL ITEM MAESTRO
    final int INT_TBL_INGEGRFIS=14;  // 
    final int INT_TBL_TIPUNIMED=15;  //TIPO DE MEDIDA
    final int INT_TBL_CHK_PED_OTR_BOD=16;  //Ped.Otr.Bod.
    final int INT_TBL_CHK_SOL_ENV_PED=17;  //Sol.Env.Ped.
    
    final int INT_TBL_BOD_ORG_ENV_BOD=18;  //Enviar a Bodega
    final int INT_TBL_BOD_ORG_ENV_CLI=19;  //Enviar al Cliente
    final int INT_TBL_BOD_ORG_CLI_RET=20;  //Cliente retira
    
    final int INT_TBL_BOD_DES_ENV_CLI=21;  //Enviar al Cliente
    final int INT_TBL_BOD_DES_CLI_RET=22;  //Cliente retira
    
    final int INT_TBL_COD_EMP_REL=23;   
    final int INT_TBL_COD_BOD_REL=24;   
    final int INT_TBL_COD_ITM_REL=25;   
    
    final int INT_TBL_CANT_MAX_BOD_ORG=26; // VALIDACIONES
    final int INT_TBL_CANT_MAX_BOD_DES=27; // VALIDACIONES
    
    private ZafTblCelRenChk objTblCelRenChk;
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
    
    private String strTitu="Pedidos a otras bodegas segun el despacho ";
    private String strVersion=" v0.1";
   
    
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
            
 
   private java.awt.Frame Frame;
   

    public ZafVen42_07() {
    }
    
    private int intCodEmpGenVen, intCodLocGenVen, intCodCotGenVen;
    /** Creates new form pantalla dialogo */
    public ZafVen42_07(Frame parent,  ZafParSis ZafParSis,java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intCodCot) {
        super(parent, true);
        try{ 
          Frame=parent;
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
          
          objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
           
            objTblCelRenLblColOcp = new ZafTblCelRenLbl();
            objTblCelRenLblColChk = new ZafTblCelRenLbl();
          ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
          stbLisBodItm= new StringBuffer();
          stbDocRelEmp= new StringBuffer();

          initComponents();
          intCodEmpGenVen=intCodEmp;intCodLocGenVen=intCodLoc;intCodCotGenVen=intCodCot;
          configurarFrm(conExt);       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
    private void configurarFrm(java.sql.Connection conExt){
        try{
            configuraTbl();      
            if(!cargarDat(conExt,intCodEmpGenVen,intCodLocGenVen,intCodCotGenVen)){
                blnAcepta=false;
                dispose();
            }
            
        }
        catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
        } 
    } 
   

 
    private boolean cargarDat(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         Statement stmLoc;
         ResultSet rstLoc;
         Vector vecData;
         int i=0;
         double dblCanMaxBodOrg, dblCanMaxBodDes;
         try{    
            vecData = new Vector();
            if(conExt!=null){
                stmLoc=conExt.createStatement();  
                strSql="";
                strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a2.co_bod as co_bodPre,a2.co_reg,a2.co_itm,a2.tx_codALt,a6.tx_nomItm,a2.nd_can, a4.co_bodGrp, a5.tx_nom as tx_nomBod, \n";
                strSql+="        a3.co_empRel,a3.co_bodRel,a3.co_itmRel, a3.nd_can as nd_canPedOtrBod,a3.nd_bodOrgCanEnvBod,a3.nd_bodOrgCanEnvCli, \n";
                strSql+="        a3.nd_bodOrgCanCliRet,a3.nd_bodDesCanEnvCli,a3.nd_bodDesCanCliRet, a3.st_pedOtrBod,a3.st_solEnvPed,  \n";
                strSql+="        a3.co_empRel,a3.co_bodRel,a3.co_itmRel";
                strSql+=" FROM tbm_cabCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_pedOtrBodCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND \n";
                strSql+="                                          a2.co_cot=a3.co_cot AND a2.co_reg=a3.co_reg) \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a4 ON (a3.co_empRel=a4.co_emp AND a3.co_bodRel=a4.co_bod) \n";
                strSql+=" INNER JOIN tbm_bod as a5 ON (a4.co_empGrp=a5.co_emp AND a4.co_bodGrp=a5.co_bod) \n";
                strSql+=" INNER JOIN tbm_inv as a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" \n";
                strSql+=" ORDER BY a2.co_reg, a5.tx_nom  \n";
                strSql+=" \n";
                System.out.println("ZafVen42_07.cargarDat: "+strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add( INT_TBL_CODITM, rstLoc.getString("co_itm") );
                    vecReg.add( INT_TBL_CODALT,  rstLoc.getString("tx_codalt") );
                    vecReg.add( INT_TBL_NOMITM,  rstLoc.getString("tx_nomitm") );
                    vecReg.add( INT_TBL_CANCOM,  rstLoc.getString("nd_can"));   
                     
                    vecReg.add( INT_TBL_COD_EMP, rstLoc.getString("co_emp"));  
                    vecReg.add( INT_TBL_COD_REG,rstLoc.getString("co_reg"));  
                    vecReg.add( INT_TBL_BODGRP, rstLoc.getString("co_bodGrp") );  
                    vecReg.add( INT_TBL_NOMBOD, rstLoc.getString("tx_nomBod") );   
                    vecReg.add( INT_TBL_CAN_PED_OTR_BOD, rstLoc.getString("nd_canPedOtrBod") );
                    vecReg.add( INT_TBL_ESTBOD, "A" );
                    
                   
                    
                    vecReg.add( INT_TBL_COD_BOD_PRE,  rstLoc.getString("co_bodPre") );
                    vecReg.add( INT_TBL_DATCANCOM ,"");
                    vecReg.add( INT_TBL_CODITMMAE, "" );
                    vecReg.add( INT_TBL_INGEGRFIS, "" );
                    vecReg.add( INT_TBL_TIPUNIMED , "" );
                    
                    vecReg.add( INT_TBL_CHK_PED_OTR_BOD , rstLoc.getString("st_pedOtrBod")==null?false:true);
                    vecReg.add( INT_TBL_CHK_SOL_ENV_PED , rstLoc.getString("st_solEnvPed")==null?false:true );
                    
                    vecReg.add( INT_TBL_BOD_ORG_ENV_BOD , rstLoc.getString("nd_bodOrgCanEnvBod") );
                    vecReg.add( INT_TBL_BOD_ORG_ENV_CLI , rstLoc.getString("nd_bodOrgCanEnvCli") );
                    vecReg.add( INT_TBL_BOD_ORG_CLI_RET , rstLoc.getString("nd_bodOrgCanCliRet") );
                    
                    vecReg.add( INT_TBL_BOD_DES_ENV_CLI ,rstLoc.getString("nd_bodDesCanEnvCli") );
                    vecReg.add( INT_TBL_BOD_DES_CLI_RET ,rstLoc.getString("nd_bodDesCanCliRet") );
                    
                    vecReg.add( INT_TBL_COD_EMP_REL,  rstLoc.getString("co_empRel") );
                    vecReg.add( INT_TBL_COD_BOD_REL,  rstLoc.getString("co_bodRel") );
                    vecReg.add( INT_TBL_COD_ITM_REL,  rstLoc.getString("co_itmRel") );
                     
                    dblCanMaxBodOrg = objUti.redondear(rstLoc.getDouble("nd_bodOrgCanEnvBod")+rstLoc.getDouble("nd_bodOrgCanEnvCli")+rstLoc.getDouble("nd_bodOrgCanCliRet"), objParSis.getDecimalesMostrar());
                    dblCanMaxBodDes = objUti.redondear(rstLoc.getDouble("nd_bodDesCanEnvCli")+rstLoc.getDouble("nd_bodDesCanCliRet"), objParSis.getDecimalesMostrar());
                    
                    vecReg.add( INT_TBL_CANT_MAX_BOD_ORG,  dblCanMaxBodOrg );
                    vecReg.add( INT_TBL_CANT_MAX_BOD_DES,  dblCanMaxBodDes );
                    
                    
                    vecData.add(vecReg);
                    i++;
                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
            }  
            objTblMod.setData(vecData);
            tblDat.setModel(objTblMod);         
           
            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);    
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);

            blnRes=true;
        }
        catch(java.sql.SQLException Evt) {  
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;          
    }   



 

 
    private void MensajeInf(String strMensaje){
        String strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
 
    private ZafTblCelRenLbl objTblCelRenLblColOcp;
    private ZafTblCelRenLbl objTblCelRenLblColChk;
    
    private final Color colFonCol =new Color(255,221,187);  //otro color de celdas naranja
    private ZafTblFilCab objTblFilCab;

    private boolean configuraTbl(){
           boolean blnRes=false;
           try{
                vecCab.clear();
                vecCab.add(INT_TBL_LINEA,"");
                vecCab.add(INT_TBL_CODITM,"Cód.Itm");
                vecCab.add( INT_TBL_CODALT,"Cód.Itm" );
                vecCab.add( INT_TBL_NOMITM,"Nom.Itm" );
                vecCab.add( INT_TBL_CANCOM,"Can.Ped." );
                
                vecCab.add(INT_TBL_COD_EMP,"Cód.Emp.");
                vecCab.add(INT_TBL_COD_REG,"Cód.Reg.");
                vecCab.add(INT_TBL_BODGRP,"Cód.Bod");
                vecCab.add(INT_TBL_NOMBOD,"Bodega");
                vecCab.add(INT_TBL_CAN_PED_OTR_BOD,"Ped.Otr.Bod.");
                vecCab.add( INT_TBL_ESTBOD,"Est.Bod." ); // V A U : Jose Marin //C Cliente retira 3/Sep/2014
                
                
                
                vecCab.add( INT_TBL_COD_BOD_PRE,"Cod.Bod.Pre." );
                vecCab.add( INT_TBL_DATCANCOM ,"");
                vecCab.add( INT_TBL_CODITMMAE ,"");
                vecCab.add( INT_TBL_INGEGRFIS ,"" );
                vecCab.add( INT_TBL_TIPUNIMED ,"TipUniMed" );
                vecCab.add( INT_TBL_CHK_PED_OTR_BOD ,"Ped.Otr.Bod.");
                vecCab.add( INT_TBL_CHK_SOL_ENV_PED ,"Sol.Env.Ped." );
                vecCab.add( INT_TBL_BOD_ORG_ENV_BOD ,"Enviar a Bodega" );
                vecCab.add( INT_TBL_BOD_ORG_ENV_CLI ,"Enviar al Cliente" );
                vecCab.add( INT_TBL_BOD_ORG_CLI_RET ,"Cliente retira" );
                vecCab.add( INT_TBL_BOD_DES_ENV_CLI ,"Enviar al Cliente" );
                vecCab.add( INT_TBL_BOD_DES_CLI_RET ,"Cliente retira" );
                
                vecCab.add( INT_TBL_COD_EMP_REL ,"Emp.Rel." );
                vecCab.add( INT_TBL_COD_BOD_REL ,"Bod.Rel." );
                vecCab.add( INT_TBL_COD_ITM_REL ,"Itm.Rel." );
                
                vecCab.add( INT_TBL_CANT_MAX_BOD_ORG ,"Val.Max.Bod.Org." );
                vecCab.add( INT_TBL_CANT_MAX_BOD_DES ,"Val.Max.Bod.Des." );
                
                
                
                objTblMod=new ZafTblMod();
                objTblMod.setHeader(vecCab);
                tblDat.setModel(objTblMod);

                //Configurar JTable: Establecer tipo de selección.
                tblDat.setRowSelectionAllowed(true);
                tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
               


                
            
                ZafMouMotAda objMouMotAda=new ZafMouMotAda();
                tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                TableColumnModel tcmAux=tblDat.getColumnModel();

                //Tamaño de las celdas
                tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
                tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(30);
                tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(100);
                tcmAux.getColumn(INT_TBL_CANCOM).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_BODGRP).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(100);
                tcmAux.getColumn(INT_TBL_CAN_PED_OTR_BOD).setPreferredWidth(60);
                

                
                tcmAux.getColumn(INT_TBL_TIPUNIMED).setPreferredWidth(10);

                tcmAux.getColumn(INT_TBL_CHK_PED_OTR_BOD).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CHK_SOL_ENV_PED).setPreferredWidth(60);
                
                tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_BOD).setPreferredWidth(70);
                tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_CLI).setPreferredWidth(70);
                tcmAux.getColumn(INT_TBL_BOD_ORG_CLI_RET).setPreferredWidth(70);
                
                tcmAux.getColumn(INT_TBL_BOD_DES_ENV_CLI).setPreferredWidth(70);
                tcmAux.getColumn(INT_TBL_BOD_DES_CLI_RET).setPreferredWidth(70);


                tcmAux.getColumn(INT_TBL_COD_EMP_REL).setPreferredWidth(10);
                tcmAux.getColumn(INT_TBL_COD_BOD_REL).setPreferredWidth(10);
                tcmAux.getColumn(INT_TBL_COD_ITM_REL).setPreferredWidth(10);
                tcmAux.getColumn(INT_TBL_CANT_MAX_BOD_ORG).setPreferredWidth(10);
                tcmAux.getColumn(INT_TBL_CANT_MAX_BOD_DES).setPreferredWidth(10);
                
                
                 
                objTblFilCab=new ZafTblFilCab(tblDat);
                tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
                objTblFilCab=null;
               
              objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
              objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
              objTblCelRenLbl2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
              
              objTblMod.setColumnDataType(INT_TBL_CAN_PED_OTR_BOD, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN_PED_OTR_BOD).setCellRenderer(objTblCelRenLbl2);
             
              objTblMod.setColumnDataType(INT_TBL_CANCOM, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLbl2);
              
              objTblMod.setColumnDataType(INT_TBL_BOD_ORG_ENV_BOD, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_BOD).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_BOD_ORG_ENV_CLI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_CLI).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_BOD_ORG_CLI_RET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_BOD_ORG_CLI_RET).setCellRenderer(objTblCelRenLbl2);
              
              objTblMod.setColumnDataType(INT_TBL_BOD_DES_ENV_CLI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_BOD_DES_ENV_CLI).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_BOD_DES_CLI_RET, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_BOD_DES_CLI_RET).setCellRenderer(objTblCelRenLbl2);
              
 
              

            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODITM);
            arlColHid.add(""+INT_TBL_BODGRP);
            
            arlColHid.add(""+INT_TBL_COD_EMP);
            arlColHid.add(""+INT_TBL_ESTBOD);
            arlColHid.add(""+INT_TBL_COD_REG);
            arlColHid.add(""+INT_TBL_COD_BOD_PRE);
 
//            arlColHid.add(""+INT_TBL_CAN_PED_OTR_BOD);

            arlColHid.add(""+INT_TBL_DATCANCOM);
            arlColHid.add(""+INT_TBL_CODITMMAE);
            arlColHid.add(""+INT_TBL_INGEGRFIS);
            arlColHid.add(""+INT_TBL_TIPUNIMED);

            arlColHid.add(""+INT_TBL_COD_EMP_REL);
            arlColHid.add(""+INT_TBL_COD_BOD_REL);
            arlColHid.add(""+INT_TBL_COD_ITM_REL);
            arlColHid.add(""+INT_TBL_CANT_MAX_BOD_ORG);
            arlColHid.add(""+INT_TBL_CANT_MAX_BOD_DES);
             
            arlColHid.add(""+INT_TBL_BOD_ORG_ENV_BOD);
            arlColHid.add(""+INT_TBL_BOD_ORG_ENV_CLI);
            arlColHid.add(""+INT_TBL_BOD_ORG_CLI_RET);
            
            arlColHid.add(""+INT_TBL_BOD_DES_ENV_CLI);
            arlColHid.add(""+INT_TBL_BOD_DES_CLI_RET);
     
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            //Configurar JTable: Establecer columnas editables.
//            Vector vecAux=new Vector();
//            vecAux.add("" + INT_TBL_BOD_ORG_ENV_BOD);   /*Pendiente */  
//            vecAux.add("" + INT_TBL_BOD_ORG_ENV_CLI);
//            vecAux.add("" + INT_TBL_BOD_ORG_CLI_RET);
//            vecAux.add("" + INT_TBL_BOD_DES_ENV_CLI);
//            vecAux.add("" + INT_TBL_BOD_DES_CLI_RET);     /*Pendiente JM */  
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;





            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);

            ZafTblHeaColGrp objTblHeaColGrpCot=new ZafTblHeaColGrp("Pedidos a Otras Bodegas");
            objTblHeaColGrpCot.setHeight(16);
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_CODITM)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_CODALT));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_NOMITM));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_CANCOM));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_COD_EMP)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_COD_REG)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_BODGRP)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_NOMBOD));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_CAN_PED_OTR_BOD));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_ESTBOD));
            
            
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_COD_BOD_PRE));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DATCANCOM));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_CODITMMAE));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_INGEGRFIS));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_TIPUNIMED));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_CHK_PED_OTR_BOD));
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_CHK_SOL_ENV_PED));

            ZafTblHeaColGrp objTblHeaColGrpSol=new ZafTblHeaColGrp("Bodega de Origen");
            objTblHeaColGrpSol.setHeight(16);
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_BOD));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_CLI));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_BOD_ORG_CLI_RET));

            ZafTblHeaColGrp objTblHeaColGrp=new ZafTblHeaColGrp("Bodega de Destino");
            objTblHeaColGrp.setHeight(16);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_BOD_DES_ENV_CLI)); 
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_BOD_DES_CLI_RET)); 

            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCot);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSol);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            
            objTblHeaColGrpCot=null;
            objTblHeaColGrp=null;
            objTblHeaColGrpSol=null;
            
            
            
            /* vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv */
            //tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_CODITM).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_CODALT).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_NOMITM).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_CANCOM).setCellRenderer(objTblCelRenLblColOcp);
            
            tcmAux.getColumn(INT_TBL_COD_EMP).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_COD_REG).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_BODGRP).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_NOMBOD).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_CAN_PED_OTR_BOD).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_ESTBOD).setCellRenderer(objTblCelRenLblColOcp);
            
            
            tcmAux.getColumn(INT_TBL_COD_BOD_PRE).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_DATCANCOM).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_CODITMMAE).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_INGEGRFIS).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_TIPUNIMED).setCellRenderer(objTblCelRenLblColOcp);
             
            tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_BOD).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_CLI).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_BOD_ORG_CLI_RET).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_BOD_DES_ENV_CLI).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_BOD_DES_CLI_RET).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_COD_EMP_REL).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_COD_BOD_REL).setCellRenderer(objTblCelRenLblColOcp);
            tcmAux.getColumn(INT_TBL_COD_ITM_REL).setCellRenderer(objTblCelRenLblColOcp);
            
//            tcmAux.getColumn(INT_TBL_CHK_PED_OTR_BOD).setCellRenderer(objTblCelRenLblColChk);
//            tcmAux.getColumn(INT_TBL_CHK_SOL_ENV_PED).setCellRenderer(objTblCelRenLblColChk);
            
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHK_PED_OTR_BOD).setCellRenderer(objTblCelRenChk);  // SOLICITAR RESERVA
            tcmAux.getColumn(INT_TBL_CHK_SOL_ENV_PED).setCellRenderer(objTblCelRenChk);   // FACTURA AUTOMATICA
            //objTblCelRenChk = null;
            
            
            objTblCelRenLblColOcp.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
            int intCell=objTblCelRenLblColOcp.getRowRender();
                    if(intCell % 2 ==0 ){
                    
                        objTblCelRenLblColOcp.setBackground(Color.white);
                        objTblCelRenLblColOcp.setForeground(Color.BLACK);
                        objTblCelRenLblColOcp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblColOcp.setTipoFormato(1);
                        objTblCelRenLblColOcp.setFormatoNumerico("###,###.##", false, true);
                    } else {
                        objTblCelRenLblColOcp.setBackground(colFonCol);
                        objTblCelRenLblColOcp.setForeground(Color.BLACK);
                        objTblCelRenLblColOcp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblColOcp.setTipoFormato(1);
                        objTblCelRenLblColOcp.setFormatoNumerico("###,###.##", false, true);
                    }
                }
            });
            
//            objTblCelRenLblColChk.addTblCelRenListener(new ZafTblCelRenAdapter() {
//            @Override
//            public void beforeRender(ZafTblCelRenEvent evt) {
//            int intCell=objTblCelRenLblColChk.getRowRender();
//                    if(intCell % 2 ==0 ){
//                        objTblCelRenLblColChk.setBackground(Color.white);
//                        objTblCelRenLblColChk.setForeground(Color.BLACK);
//                    } else {
//                        objTblCelRenLblColChk.setBackground(colFonCol);
//                        objTblCelRenLblColChk.setForeground(Color.BLACK);
//                    }
//                }
//            });
             
        objTblCelRenChk.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
            int intCell=objTblCelRenChk.getRowRender();
                    if(intCell % 2 ==0 ){
                        objTblCelRenChk.setBackground(Color.white);
                        objTblCelRenChk.setForeground(Color.BLACK);
                    } else {
                        objTblCelRenChk.setBackground(colFonCol);
                        objTblCelRenChk.setForeground(Color.BLACK);
                    }
                }
            });
            
            
            
            /* vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv */
            
           
            
            
            /* Validaciones */
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_BOD).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                double dblAux=0.0;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_BOD)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_BOD).toString().length()>0){
                                dblAux=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_BOD).toString(), 4 );
                            }
                        }
                    }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        double dlbResVal=0;
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_BOD)!=null ){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_BOD).toString().length()>0){
                                dlbResVal=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_BOD).toString(), 4 );
                            }
                        }
                        if(dlbResVal < 0 ){
                            MensajeInf("SOLO SE PERMITE INGRESAR VALORES ENTEROS ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_ORG_ENV_BOD);
                        }
                        if(!validacionDatosBodegaOrigen(intNumFil)){
                            MensajeInf("LA CANTIDAD INGRESADA CREA UN EXCEDENTE A LA CANTIDAD... ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_ORG_ENV_BOD);
                        }
                    }                   
                }
            });
            /*-----*/
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_BOD_ORG_ENV_CLI).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                double dblAux=0.0;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_CLI)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_CLI).toString().length()>0){
                                dblAux=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_CLI).toString(), 4 );
                            }
                        }
                    }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        double dlbResVal=0;
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_CLI)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_CLI).toString().length()>0){
                                dlbResVal=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_ENV_CLI).toString(), 4 );
                            }
                        }
                        if(dlbResVal < 0 ){
                            MensajeInf("SOLO SE PERMITE INGRESAR VALORES ENTEROS ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_ORG_ENV_CLI);
                        }
                        if(!validacionDatosBodegaOrigen(intNumFil)){
                            MensajeInf("LA CANTIDAD INGRESADA CREA UN EXCEDENTE A LA CANTIDAD... ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_ORG_ENV_CLI);
                        }
                    }                   
                }
            });
            /*-----*/
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_BOD_ORG_CLI_RET).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                double dblAux=0.0;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_CLI_RET)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_CLI_RET).toString().length()>0){
                                dblAux=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_CLI_RET).toString(), 4 );
                            }
                        }
                    }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        double dlbResVal=0;
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_CLI_RET)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_CLI_RET).toString().length()>0){
                                dlbResVal=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_ORG_CLI_RET).toString(), 4 );
                            }
                        }
                        if(dlbResVal < 0 ){
                            MensajeInf("SOLO SE PERMITE INGRESAR VALORES ENTEROS ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_ORG_CLI_RET);
                        }
                        if(!validacionDatosBodegaOrigen(intNumFil)){
                            MensajeInf("LA CANTIDAD INGRESADA CREA UN EXCEDENTE A LA CANTIDAD... ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_ORG_CLI_RET);
                        }
                    }                   
                }
            });
            /* -----  BODEGA DESTINO ------ */
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_BOD_DES_ENV_CLI).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                double dblAux=0.0;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_ENV_CLI)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_ENV_CLI).toString().length()>0){
                                dblAux=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_ENV_CLI).toString(), 4 );
                            }
                        }
                    }
                }
                
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        double dlbResVal=0;
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_ENV_CLI)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_ENV_CLI).toString().length()>0){
                                dlbResVal=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_ENV_CLI).toString(), 4 );
                            }
                        }
                        if(dlbResVal < 0 ){
                            MensajeInf("SOLO SE PERMITE INGRESAR VALORES ENTEROS ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_DES_ENV_CLI);
                        }
                        if(!validacionDatosBodegaDestino(intNumFil)){
                            MensajeInf("LA CANTIDAD INGRESADA CREA UN EXCEDENTE A LA CANTIDAD... ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_DES_ENV_CLI);
                        }
                    }                   
                }
            });
            /* ----------- */
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_BOD_DES_CLI_RET).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                double dblAux=0.0;
                @Override
                public void beforeEdit(ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_CLI_RET)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_CLI_RET).toString().length()>0){
                                dblAux=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_CLI_RET).toString(), 4 );
                            }
                        }
                    }
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intNumFil = tblDat.getSelectedRow();
                    if(intNumFil >= 0 ) {
                        double dlbResVal=0;
                        if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_CLI_RET)!=null){
                            if(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_CLI_RET).toString().length()>0){
                                dlbResVal=objUti.redondear(tblDat.getValueAt(intNumFil,INT_TBL_BOD_DES_CLI_RET).toString(), 4 );
                            }
                        } 
                        if(dlbResVal < 0 ){
                            MensajeInf("SOLO SE PERMITE INGRESAR VALORES ENTEROS ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_DES_CLI_RET);
                        }
                        if(!validacionDatosBodegaDestino(intNumFil)){
                            MensajeInf("LA CANTIDAD INGRESADA CREA UN EXCEDENTE A LA CANTIDAD... ");
                            tblDat.setValueAt("0", intNumFil,INT_TBL_BOD_DES_CLI_RET);
                        }
                    }                   
                }
            });
            /* ----------- */
            
              
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

            blnRes=true;
        }
        catch(Exception e) {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this,e);  
        }
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
        tblDat = new javax.swing.JTable()  {    protected javax.swing.table.JTableHeader createDefaultTableHeader()    {       return new ZafTblHeaGrp(columnModel);    } };
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
    try{
        blnAcepta = false; 
        if(isValDat()){
            if(generaContenedor()){
                System.out.println(">>> xD ZafVen42_07 OK!!! ");
                blnAcepta = true; 
            }
        }
        else{
            MensajeInf("LOS DATOS INGRESADOS ESTA INCORRECTOS FAVOR VOLVER A INTENTARLO");
        }
        this.setVisible(false);
    }  
    catch(Exception Evt){ 
        objUti.mostrarMsgErr_F1(null, Evt); 
        blnAcepta = false; 
    }
}//GEN-LAST:event_butAceActionPerformed


// <editor-fold defaultstate="collapsed" desc=" /* José Marín: No se uso  */ ">
//    private boolean isBodegaDestinoPosibleModificar(int Row){
//        boolean blnRes=false;
//        try{
//            if(tblDat.getValueAt(Row,INT_TBL_COD_EMP)!=null && tblDat.getValueAt(Row,INT_TBL_COD_EMP_REL)!=null &&
//                tblDat.getValueAt(Row,INT_TBL_COD_BOD_PRE)!=null && tblDat.getValueAt(Row,INT_TBL_COD_BOD_REL)!=null ){
//                 if( (   (tblDat.getValueAt(Row,INT_TBL_COD_EMP).toString().equals(tblDat.getValueAt(Row,INT_TBL_COD_EMP_REL).toString() ) ) && 
//                         (tblDat.getValueAt(Row,INT_TBL_COD_BOD_PRE).toString().equals(tblDat.getValueAt(Row,INT_TBL_COD_BOD_REL).toString() ) )  
//                      ) ){
//                       //System.out.println("BODEGA PREDETERMINADA.... BODEGA DESTINO SE PUEDE MODIFICAR");
//                       blnRes=true;
//                 }
//             }
//        }
//        catch(Exception Evt){ 
//            objUti.mostrarMsgErr_F1(null, Evt); 
//            blnRes = false; 
//        }
//        return blnRes;
//    }
//
//</editor-fold>
 
    private boolean isValDat(){
        boolean blnRes=true;
        try{
            double dblCanPedOtrBod,dblBodOrgEnvBod,dblBodOrgEnvCli,dblBodOrgCliRet,dblBodDesEnvCli,dblBodDesCliRet;
            for(int indice=0; indice<tblDat.getRowCount(); indice++){
                if(tblDat.getValueAt(indice,INT_TBL_COD_EMP)!=null && tblDat.getValueAt(indice,INT_TBL_COD_EMP_REL)!=null &&
                   tblDat.getValueAt(indice,INT_TBL_COD_BOD_PRE)!=null && tblDat.getValueAt(indice,INT_TBL_COD_BOD_REL)!=null ){
                    
                    dblBodOrgEnvBod = Double.parseDouble((tblDat.getValueAt(indice, INT_TBL_BOD_ORG_ENV_BOD)==null)?"0":(tblDat.getValueAt(indice, INT_TBL_BOD_ORG_ENV_BOD).toString().equals("")?"0":tblDat.getValueAt(indice, INT_TBL_BOD_ORG_ENV_BOD).toString()));
                    dblBodOrgEnvCli = Double.parseDouble((tblDat.getValueAt(indice, INT_TBL_BOD_ORG_ENV_CLI)==null)?"0":(tblDat.getValueAt(indice, INT_TBL_BOD_ORG_ENV_CLI).toString().equals("")?"0":tblDat.getValueAt(indice, INT_TBL_BOD_ORG_ENV_CLI).toString()));
                    dblBodOrgCliRet = Double.parseDouble((tblDat.getValueAt(indice, INT_TBL_BOD_ORG_CLI_RET)==null)?"0":(tblDat.getValueAt(indice, INT_TBL_BOD_ORG_CLI_RET).toString().equals("")?"0":tblDat.getValueAt(indice, INT_TBL_BOD_ORG_CLI_RET).toString()));
                    dblCanPedOtrBod = Double.parseDouble((tblDat.getValueAt(indice, INT_TBL_CANT_MAX_BOD_ORG)==null)?"0":(tblDat.getValueAt(indice, INT_TBL_CANT_MAX_BOD_ORG).toString().equals("")?"0":tblDat.getValueAt(indice, INT_TBL_CANT_MAX_BOD_ORG).toString()));
                    if(dblCanPedOtrBod!=dblBodOrgEnvBod+dblBodOrgEnvCli+dblBodOrgCliRet){
                        blnRes=false;
                    }

                    dblBodDesEnvCli = Double.parseDouble((tblDat.getValueAt(indice, INT_TBL_BOD_DES_ENV_CLI)==null)?"0":(tblDat.getValueAt(indice, INT_TBL_BOD_DES_ENV_CLI).toString().equals("")?"0":tblDat.getValueAt(indice, INT_TBL_BOD_DES_ENV_CLI).toString()));
                    dblBodDesCliRet = Double.parseDouble((tblDat.getValueAt(indice, INT_TBL_BOD_DES_CLI_RET)==null)?"0":(tblDat.getValueAt(indice, INT_TBL_BOD_DES_CLI_RET).toString().equals("")?"0":tblDat.getValueAt(indice, INT_TBL_BOD_DES_CLI_RET).toString()));
                    dblCanPedOtrBod = Double.parseDouble((tblDat.getValueAt(indice, INT_TBL_CANT_MAX_BOD_DES)==null)?"0":(tblDat.getValueAt(indice, INT_TBL_CANT_MAX_BOD_DES).toString().equals("")?"0":tblDat.getValueAt(indice, INT_TBL_CANT_MAX_BOD_DES).toString()));
                    if(dblCanPedOtrBod!=dblBodDesEnvCli+dblBodDesCliRet){
                        blnRes=false;
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

    private boolean validacionDatosBodegaOrigen(int Row){
        boolean blnRes=false;
        try{
            double dblBodOrgEnvBod = Double.parseDouble((tblDat.getValueAt(Row, INT_TBL_BOD_ORG_ENV_BOD)==null)?"0":(tblDat.getValueAt(Row, INT_TBL_BOD_ORG_ENV_BOD).toString().equals("")?"0":tblDat.getValueAt(Row, INT_TBL_BOD_ORG_ENV_BOD).toString()));
            double dblBodOrgEnvCli = Double.parseDouble((tblDat.getValueAt(Row, INT_TBL_BOD_ORG_ENV_CLI)==null)?"0":(tblDat.getValueAt(Row, INT_TBL_BOD_ORG_ENV_CLI).toString().equals("")?"0":tblDat.getValueAt(Row, INT_TBL_BOD_ORG_ENV_CLI).toString()));
            double dblBodOrgCliRet = Double.parseDouble((tblDat.getValueAt(Row, INT_TBL_BOD_ORG_CLI_RET)==null)?"0":(tblDat.getValueAt(Row, INT_TBL_BOD_ORG_CLI_RET).toString().equals("")?"0":tblDat.getValueAt(Row, INT_TBL_BOD_ORG_CLI_RET).toString()));
            double dblCanPedOtrBod = Double.parseDouble((tblDat.getValueAt(Row, INT_TBL_CANT_MAX_BOD_ORG)==null)?"0":(tblDat.getValueAt(Row, INT_TBL_CANT_MAX_BOD_ORG).toString().equals("")?"0":tblDat.getValueAt(Row, INT_TBL_CANT_MAX_BOD_ORG).toString()));
            if(dblCanPedOtrBod>=dblBodOrgEnvBod+dblBodOrgEnvCli+dblBodOrgCliRet){
                blnRes=true;
            }
        }
        catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(null, Evt); 
          blnRes = false; 
        }
        return blnRes;
    }
    
    
    private boolean validacionDatosBodegaDestino(int Row){
        boolean blnRes=false;
        try{
            double dblBodDesEnvCli = Double.parseDouble((tblDat.getValueAt(Row, INT_TBL_BOD_DES_ENV_CLI)==null)?"0":(tblDat.getValueAt(Row, INT_TBL_BOD_DES_ENV_CLI).toString().equals("")?"0":tblDat.getValueAt(Row, INT_TBL_BOD_DES_ENV_CLI).toString()));
            double dblBodDesCliRet = Double.parseDouble((tblDat.getValueAt(Row, INT_TBL_BOD_DES_CLI_RET)==null)?"0":(tblDat.getValueAt(Row, INT_TBL_BOD_DES_CLI_RET).toString().equals("")?"0":tblDat.getValueAt(Row, INT_TBL_BOD_DES_CLI_RET).toString()));
            double dblCanPedOtrBod = Double.parseDouble((tblDat.getValueAt(Row, INT_TBL_CANT_MAX_BOD_DES)==null)?"0":(tblDat.getValueAt(Row, INT_TBL_CANT_MAX_BOD_DES).toString().equals("")?"0":tblDat.getValueAt(Row, INT_TBL_CANT_MAX_BOD_DES).toString()));
            if(dblCanPedOtrBod>=dblBodDesEnvCli+dblBodDesCliRet){
                blnRes=true;
            }
        }
        catch(Exception Evt){ 
          objUti.mostrarMsgErr_F1(null, Evt); 
          blnRes = false; 
        }
        return blnRes;
    }
    
    private String strCadRes="";
       
    private boolean generaContenedor(){
        boolean blnRes=true;
        strCadRes="";
        try{          
            for(int i=0; i<tblDat.getRowCount(); i++){
                if(tblDat.getValueAt(i,INT_TBL_COD_EMP_REL)!=null){
                    strSql ="";
                    strSql+=" UPDATE tbm_pedOtrBodCotVen SET ";
                    strSql+=" nd_bodOrgCanEnvBod="+objUti.codificar(tblDat.getValueAt(i,INT_TBL_BOD_ORG_ENV_BOD)==null?0:tblDat.getValueAt(i,INT_TBL_BOD_ORG_ENV_BOD).toString(), 2)+",";
                    strSql+=" nd_bodOrgCanEnvCli="+objUti.codificar(tblDat.getValueAt(i,INT_TBL_BOD_ORG_ENV_CLI)==null?0:tblDat.getValueAt(i,INT_TBL_BOD_ORG_ENV_CLI).toString(), 2)+",";
                    strSql+=" nd_bodOrgCanCliRet="+objUti.codificar(tblDat.getValueAt(i,INT_TBL_BOD_ORG_CLI_RET)==null?0:tblDat.getValueAt(i,INT_TBL_BOD_ORG_CLI_RET).toString(), 2)+",";
                    strSql+=" nd_bodDesCanEnvCli="+objUti.codificar(tblDat.getValueAt(i,INT_TBL_BOD_DES_ENV_CLI)==null?0:tblDat.getValueAt(i,INT_TBL_BOD_DES_ENV_CLI).toString(), 2)+",";
                    strSql+=" nd_bodDesCanCliRet="+objUti.codificar(tblDat.getValueAt(i,INT_TBL_BOD_DES_CLI_RET)==null?0:tblDat.getValueAt(i,INT_TBL_BOD_DES_CLI_RET).toString(), 2)+" \n";
                    strSql+=" WHERE co_emp="+intCodEmpGenVen+" AND co_loc="+intCodLocGenVen+" AND co_cot="+intCodCotGenVen+" AND ";
                    strSql+=" co_reg="+tblDat.getValueAt(i,INT_TBL_COD_REG).toString()+" AND ";
                    strSql+=" co_empRel="+tblDat.getValueAt(i,INT_TBL_COD_EMP_REL).toString()+" AND ";
                    strSql+=" co_bodRel="+tblDat.getValueAt(i,INT_TBL_COD_BOD_REL).toString()+" AND ";
                    strSql+=" co_itmRel="+tblDat.getValueAt(i,INT_TBL_COD_ITM_REL).toString()+" ; ";
                    System.out.println("GeneraContenedor:: "+strSql);
                }
            }
            strCadRes+=strSql;
        }
        catch(Exception Evt){ 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    } 

    
     public String getCadenaInsertBodegasDespacho(){
         return strCadRes;
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
            case INT_TBL_CAN_PED_OTR_BOD:
                strMsg="Cantidad pedida a otras bodegas";
            break;
                
//            case INT_TBL_CANCOM:
//                strMsg="Cantidad pedida a otras bodegas";
//            break;
                
            case INT_TBL_CHK_PED_OTR_BOD:
                strMsg="Pedidos a otras bodegas";
            break;    
            case INT_TBL_CHK_SOL_ENV_PED:
                strMsg="Solicitud de envio de pedidos";
            break;
                
            case INT_TBL_BOD_ORG_ENV_BOD:
                strMsg="Bodega Origen: Enviar a Bodega";
            break;
            case INT_TBL_BOD_ORG_ENV_CLI:
                strMsg="Bodega Origen: Enviar al Cliente";
            break;
            case INT_TBL_BOD_ORG_CLI_RET:
                strMsg="Bodega Origen: Cliente Retira";
            break;
                
            case INT_TBL_BOD_DES_ENV_CLI:
                strMsg="Bodega Destino: Enviar al Cliente";
            break;
            case INT_TBL_BOD_DES_CLI_RET:
                strMsg="Bodega Destino: Cliente Retira";
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
     
    
}


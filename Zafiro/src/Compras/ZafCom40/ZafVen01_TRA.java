/*
 * ZafCon20.java  Algunos items no tienen   ne_secempant 
 *  
 * Created on 02 de febrero de 2005, 17:03 PM     
 */
package Compras.ZafCom40;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafAsiDia.ZafAsiDia;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import Librerias.ZafVenCon.ZafVenCon;  //**************************   
import java.util.ArrayList;  //*******************
/**
 *
 * @author  Eddye Lino 
 */
public class ZafVen01_TRA extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;                        //Línea
    final int INT_TBL_DAT_COD_SIS=1;                    //Código del item (Sistema).
    final int INT_TBL_DAT_COD_ALT=2;                    //Código del item (Alterno).
    final int INT_TBL_DAT_BUT_ITM=3;                    //Botón del item.
    final int INT_TBL_DAT_NOM_ITM=4;                    //Nombre del item.
    final int INT_TBL_DAT_DEC_UNI=5;                    //Descripción corta de la unidad de medida.
    final int INT_TBL_DAT_COD_BOD_ORG=6;                //Código de la bodega origen.
    final int INT_TBL_DAT_BUT_BOD_ORG=7;                //Botón de la bodega origen.
    final int INT_TBL_DAT_DEL_BOD_ORG=8;                //Descripción de la bodega origen.
    final int INT_TBL_DAT_COD_BOD_DES=9;                //Código de la bodega destino.
    final int INT_TBL_DAT_BUT_BOD_DES=10;               //Botón de la bodega destino.
    final int INT_TBL_DAT_DEL_BOD_DES=11;               //Descripción de la bodega destino.
    final int INT_TBL_DAT_CAN=12;                       //Cantidad.
    final int INT_TBL_DAT_COS_UNI=13;                   //Costo unitario.
    final int INT_TBL_DAT_COS_TOT=14;                   //Costo total.
    final int INT_TBL_DAT_AUX_COD_SIS=15;               //Auxiliar:Código del item (Sistema).
    final int INT_TBL_DAT_AUX_COD_BOD_ORG=16;           //Auxiliar:Código de la bodega origen.
    final int INT_TBL_DAT_AUX_COD_BOD_DES=17;           //Auxiliar:Código de la bodega destino.
    final int INT_TBL_DAT_AUX_CAN=18;                   //Auxiliar:Cantidad.
    final int INT_TBL_NUMREL=19;
    final int INT_TBL_IEBODFIS   = 20;  // estado que dice si ingresa/egresa fisicamente en bodega
    
    final int INT_CFE_COD_SIS=0;                        //Columnas de las filas eliminadas: Código del item.
    final int INT_CFE_COD_BOD_ORG=1;                    //Columnas de las filas eliminadas: Código de la bodega origen.
    final int INT_CFE_COD_BOD_DES=2;                    //Columnas de las filas eliminadas: Código de la bodega destino.
    final int INT_CFE_CAN=3;                            //Columnas de las filas eliminadas: Cantidad.
    
    //Variables generales.
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;            //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    private ZafTblCelEdiBut objTblCelEdiBut;            //Editor: JButton en celda.
    private ZafTblCelEdiBut objTblCelEdiButBodOrg;      //Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiBut objTblCelEdiButBodDes;      //Editor: JButton en celda (Bodega destino).
    private ZafTblCelEdiTxtCon objTblCelEdiTxtCon;      //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConBod;   //Editor: JTextField de consulta en celda (Bodegas).
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblModLis objTblModLis;                  //Detectar cambios de valores en las celdas.
    private ZafAsiDia objAsiDia;
    private Connection con, conCab, CONN_GLO=null,CONN_GLO_LOC=null, CONN_REMOTO=null;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private String strCodTipDoc, strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    //Variables de la clase.
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;  // Para trabajar con la informacion de tipo de documento 
    
     public boolean blnAcepta = false;
     public boolean blnEst=false;
     javax.swing.JDialog jfrThis; //Hace referencia a this 
     ZafVenCon objVenConTipdoc; //*****************  
     int intCodMnuPrg=0;
     int intCodEmpGrp=0; 
     int intCodCot;
     int intTipDocFac=0;
     int intCodDocFac=0;
     int intCodLocGrp=1;
    
    /** Crea una nueva instancia de la clase ZafCon06. */    
public ZafVen01_TRA(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCodCot2, int intCodMnu,  java.sql.Connection con_remoto ,  java.sql.Connection con_local, int  intTipDoc , int intCodDoc  ) {
 super(parent, modal);
 try{
   initComponents();  
   //Inicializar objetos.
    objParSis=(ZafParSis)obj.clone();
    intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
    intCodMnuPrg=intCodMnu;
    intCodCot=intCodCot2;
    intTipDocFac=intTipDoc;
    intCodDocFac=intCodDoc;

    CONN_REMOTO=con_remoto;

    if(con_remoto!=null)
       CONN_GLO = con_remoto;
    else
        CONN_GLO = con_local;
    
    CONN_GLO_LOC = con_local; 

   //Inicializar objetos.
    objUti=new ZafUtil();
    objAsiDia=new ZafAsiDia(objParSis);
    objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);   
   jfrThis = this;
 
 }catch (CloneNotSupportedException e) {  this.setTitle(this.getTitle() + " [ERROR]"); }
}

        
     


public void Configura_ventana_consulta(){
 configurarVenConTipDoc();

   if (!configurarFrm())
      exitForm();
   cargarTipoDoc();

   refrescaDatos();
}
     
     
     
     

public void  refrescaDatos(){
  java.sql.Statement stmLoc;
  java.sql.Statement stmLoc01;
  java.sql.ResultSet rstLoc;
  int intCodBod;
  int intVal=0;
  String strSql="", strSqlAux="", strSqlAux2="";
  double dblCan=0;
  double dblCosUni=0;
 try{
    if(CONN_GLO_LOC!=null){  
       stmLoc  =CONN_GLO_LOC.createStatement();
       stmLoc01=CONN_GLO.createStatement();
       StringBuffer stb=new StringBuffer();
       
       strSqlAux=",CASE WHEN ("+
       " (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1))  IN (" +
       " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objParSis.getCodigoEmpresa()+" AND co_loc="+objParSis.getCodigoLocal()+" " +
       " AND co_tipdoc=1 AND co_usr="+objParSis.getCodigoUsuario()+" AND st_reg='A' AND st_tipmov='I' " +
       " ))) THEN 'S' ELSE 'N' END  as isterL";
                
       strSqlAux2=",CASE WHEN ( (trim(SUBSTR (UPPER(inv.tx_codalt), length(inv.tx_codalt) ,1)) IN ( " +
       " SELECT  UPPER(trim(a1.tx_cad))  FROM tbr_bodloc as a " +
       " inner join tbm_reginvmernuningegrfisbod as a1 ON(a1.co_emp=a.co_Emp and a1.co_bod=a.co_bod) " +
       " WHERE a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a1.st_reg='A' and   a.st_reg='P' ))) " +
       " THEN 'S' ELSE 'N' END AS proconf  ";
                    
       strSql=" SELECT sum(nd_can) as can,  co_itm as coitm, co_reg as coreg , st_traauttot FROM ( SELECT " +
       "  nd_can, a.co_itm , a.co_reg , a.st_traauttot  "+strSqlAux+"  "+
       " FROM tbm_detcotven as a " +
       " inner join tbm_inv as inv on (inv.co_emp=a.co_emp and inv.co_itm=a.co_itm and inv.st_ser='N' )   " +
       " where a.co_emp="+objParSis.getCodigoEmpresa()+" and a.co_loc="+objParSis.getCodigoLocal()+" and a.co_cot="+intCodCot+" " +
       " ) AS x  WHERE  x.isterl='S' " +
       " GROUP BY x.co_itm, x.co_reg , x.st_traauttot ";
       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){
          if(intVal>0) stb.append(" UNION ALL ");    
           stb.append("SELECT "+rstLoc.getString("can")+" AS can ,"+rstLoc.getString("coitm")+" as coitm , "+rstLoc.getString("coreg")+" as coreg , text '"+rstLoc.getString("st_traauttot")+"' as esttratot  " );
           intVal=1;     
       } 
       rstLoc.close();
       rstLoc=null;
                     
       strSql="SELECT  inv1.co_itm, abs(canmovtrans) as nd_can, inv.tx_nomitm, inv.tx_codalt, var.tx_descor, co_bodPer " +
       ", a2.tx_nom, inv1.nd_cosuni, (abs(canmovtrans)*inv1.nd_cosuni) as total "+strSqlAux2+" "+
       " FROM ( SELECT * FROM (  SELECT coitm,   (nd_stkAct - abs(can)) as canmov  , a3.co_bodPer  "+
       " ,case when esttratot = 'S' then   abs(can)*-1 else  nd_stkAct - abs(can)  end  as canmovtrans "+
       " FROM ( SELECT SUM(can) AS can, coitm, esttratot FROM( "+stb.toString()+" "+
       " ) AS x GROUP BY  coitm, esttratot "+         
       " ) as  x  inner join tbm_equInv as a1 ON(a1.co_emp="+objParSis.getCodigoEmpresa()+" and a1.co_itm=x.coitm) " +
       " inner join tbm_equInv as eq ON(eq.co_itmmae=a1.co_itmmae and eq.co_emp="+objParSis.getCodigoEmpresaGrupo()+") " +
       " INNER JOIN tbm_invBod AS a2 ON (eq.co_emp=a2.co_emp AND eq.co_itm=a2.co_itm )  " +
       " INNER JOIN tbr_bodEmp AS a3 ON (a3.co_emp="+objParSis.getCodigoEmpresa()+" and a3.co_loc="+objParSis.getCodigoLocal()+" and  " +
       "  a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer )  ) as  x where  canmovtrans < 0 "+ //canmov <0 " +
       " ) as  x " +
       " inner join tbm_equinv as equ on (equ.co_emp="+objParSis.getCodigoEmpresa()+" and equ.co_itm=x.coitm)   " +
       " inner join tbm_equinv as equ1 on (equ1.co_itmmae=equ.co_itmmae  and equ1.co_emp="+objParSis.getCodigoEmpresaGrupo()+")  " +
       " inner join tbm_inv as inv on (inv.co_emp="+objParSis.getCodigoEmpresa()+" and inv.co_itm=x.coitm) " +
       " inner join tbm_inv as inv1 on (inv1.co_emp=equ1.co_emp and inv1.co_itm=equ1.co_itm)  " +
       " left outer join tbm_var as var on (inv.co_uni = var.co_reg ) " +
       " INNER JOIN tbm_bod AS a2 ON (a2.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND a2.co_bod=x.co_bodPer)";
       stb=null;

       rstLoc=stmLoc01.executeQuery(strSql);
       java.util.Vector vecData = new java.util.Vector();
       while(rstLoc.next()){ 
          dblCan=rstLoc.getDouble("nd_can");
          dblCosUni=rstLoc.getDouble("nd_cosuni");                  
                        
         vecReg=new Vector();
         vecReg.add(INT_TBL_DAT_LIN,"");

         vecReg.add(INT_TBL_DAT_COD_SIS, rstLoc.getString("co_itm") );
         vecReg.add(INT_TBL_DAT_COD_ALT,rstLoc.getString("tx_codAlt"));
         vecReg.add(INT_TBL_DAT_BUT_ITM,null);
         vecReg.add(INT_TBL_DAT_NOM_ITM,rstLoc.getString("tx_nomItm"));
         vecReg.add(INT_TBL_DAT_DEC_UNI,rstLoc.getString("tx_descor"));
         intCodBod=rstLoc.getInt("co_bodPer");
         vecReg.add(INT_TBL_DAT_COD_BOD_ORG,"" );
         vecReg.add(INT_TBL_DAT_BUT_BOD_ORG,null);
         vecReg.add(INT_TBL_DAT_DEL_BOD_ORG,"" );
         vecReg.add(INT_TBL_DAT_COD_BOD_DES, ""+ intCodBod );
         vecReg.add(INT_TBL_DAT_BUT_BOD_DES,null);
         vecReg.add(INT_TBL_DAT_DEL_BOD_DES, rstLoc.getString("tx_nom") );
         vecReg.add(INT_TBL_DAT_CAN, new Double(dblCan) );
         vecReg.add(INT_TBL_DAT_COS_UNI, new Double(dblCosUni) );
         dblCosUni = rstLoc.getDouble("total");          //dblCosUni = (dblCosUni*dblCan);
         vecReg.add(INT_TBL_DAT_COS_TOT, new Double(dblCosUni) );
         vecReg.add(INT_TBL_DAT_AUX_COD_SIS, rstLoc.getString("co_itm"));
         vecReg.add(INT_TBL_DAT_AUX_COD_BOD_ORG,"" + intCodBod);
         vecReg.add(INT_TBL_DAT_AUX_COD_BOD_DES,null);
         vecReg.add(INT_TBL_DAT_AUX_CAN,null);
         vecReg.add(INT_TBL_NUMREL, ""); //rstLoc.getString("coreg"));
         vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("proconf"));
       vecData.add(vecReg);
     }
     objTblMod.setData(vecData);
     tblDat .setModel(objTblMod);                                 
     rstLoc.close();
     rstLoc=null;
     calcularTotDoc();
     objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                   
     stmLoc.close();
     stmLoc01.close();
     stmLoc=null;
     stmLoc01=null;
 
  }}catch(SQLException Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt); }
    catch(Exception Evt) { objUti.mostrarMsgErr_F1(jfrThis, Evt);  }                          
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
             if(objParSis.getCodigoUsuario()==1){
                  Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                          " where   b.co_emp = " +intCodEmpGrp+ " and " +
                          " b.co_mnu = " +intCodMnuPrg;
              }else {     
                    Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+
                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                    " WHERE "+
                    "  a1.co_emp=" +intCodEmpGrp+""+
                    " AND a1.co_mnu=" + intCodMnuPrg +""+
                    " AND a1.co_usr=" +objParSis.getCodigoUsuario();
             }
                      
                  
            
            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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
    
         
     
     
     
     
        
         public void cargarTipoDoc(){
             String sqlPrede = "";
              try{
                    
                    //java.sql.Connection  conDefault =  DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    java.sql.Statement stmPrede=CONN_GLO.createStatement();
                    java.sql.ResultSet rstPrede;
                    
                    if(objParSis.getCodigoUsuario()==1){
                              sqlPrede = "Select distinct doc.co_tipdoc,doc.tx_deslar,doc.tx_descor from tbm_cabtipdoc as doc, tbr_tipdocprg as menu " +
                                        " where   menu.co_emp = " +intCodEmpGrp+ " and " +
                                                " menu.co_loc = " +intCodLocGrp+ " and " +
                                                " menu.co_mnu = " + intCodMnuPrg  + " and " +  
                                                " menu.co_tipdoc = doc.co_tipdoc and menu.st_reg = 'S'";
                   }else {     
                        sqlPrede  ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar "+
                                    " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                                    " WHERE "+
                                    "  a1.co_emp=" +intCodEmpGrp+""+
                                    " AND a1.co_mnu=" + intCodMnuPrg +" AND a1.st_reg = 'S' "+
                                    " AND a1.co_usr=" +objParSis.getCodigoUsuario();
             }
                    
                    rstPrede=stmPrede.executeQuery(sqlPrede);
                    
                    if(rstPrede.next()){
                        txtCodTipDoc.setText(((rstPrede.getString("co_tipdoc")==null)?"":rstPrede.getString("co_tipdoc")));
                        txtDesCorTipDoc.setText(((rstPrede.getString("tx_descor")==null)?"":rstPrede.getString("tx_descor")));
                        txtDesLarTipDoc.setText(((rstPrede.getString("tx_deslar")==null)?"":rstPrede.getString("tx_deslar")));
                        strCodTipDoc=txtCodTipDoc.getText();
                        strDesCorTipDoc=txtDesCorTipDoc.getText();
                        strDesLarTipDoc=txtDesLarTipDoc.getText();
                       //objCtaCta_Dat = new Librerias.ZafUtil.ZafCtaCtb_dat(objParSis,  Integer.parseInt( (txtCodTipDoc.getText().equals(""))?"0":txtCodTipDoc.getText() ));            
       
                    }
                    
                    stmPrede.close();
                    rstPrede.close();
                   // conDefault.close();
                    stmPrede   = null;
                    rstPrede   = null;
                 //   conDefault = null;
                }
                catch(SQLException Evt)  {   objUti.mostrarMsgErr_F1(jfrThis, Evt);  }
                catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(jfrThis, Evt);  } 
             
         }
         
       
     
         
//    public ZafVen01_TRA(ZafParSis obj, Integer codigoCuenta)
//    {
//        this(obj);
////        txtCodSis.setText(codigoCuenta.toString());
//        objTooBar.setEstado('c');
//        objTooBar.consultar();
//        objTooBar.setEstado('w');
//    }
    
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
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        butguardar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Título de la ventana");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 64));
        panGenCab.setLayout(null);

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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(100, 4, 56, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 100, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 24, 80, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(392, 4, 20, 20);

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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(156, 4, 236, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(416, 4, 100, 20);

        lblNumDoc1.setText("Número de documento:");
        lblNumDoc1.setToolTipText("Número de documento");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(416, 24, 100, 20);

        txtNumDoc1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtNumDoc1);
        txtNumDoc1.setBounds(516, 24, 148, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 4, 32, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panGenCab.add(lblMonDoc);
        lblMonDoc.setBounds(416, 44, 100, 20);

        txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtMonDoc);
        txtMonDoc.setBounds(516, 44, 148, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());

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
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

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

        panFrm.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
 
    private void butguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butguardarActionPerformed
        // TODO add your handling code here:
    if(isCamVal()){
        if(insertar()){
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="La operación INSERTAR se realizó con éxito.";
            oppMsg.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
             //txtNumDoc1
            cerrarObj();
            System.gc(); 
            dispose(); 
        }else{
             cerrarVentana2(); 
             //System.out.println("ERROR................................."); 
       }
     }
       
    }//GEN-LAST:event_butguardarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
          boolean a = ValidaFormaRet();
               if(a == true){  blnEst=true; cerrarVentana(); }
          
    }//GEN-LAST:event_formWindowClosing

    
     public boolean ValidaFormaRet(){
             if( txtCodDoc.getText().equals("") ){
                   javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                   String strTit, strMsg;
                   strTit="Mensaje del sistema Zafiro";
                   strMsg="<< Es obligatorio que la Transferencia sea guardada  >>";
                   obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                   return false;
              }
        return true;
     }    
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
           cerrarVentana(); 
         
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
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
    
     
     
       private void cerrarVentana2(){
           blnAcepta = true;
           blnEst=true; 
           cerrarObj();
           System.gc(); 
           dispose(); 
          
    }
    
      public boolean acepta(){
        return blnEst;
    }
    
      
      
      public void cerrarObj(){
       try{
          objVenConTipdoc.dispose();
          objVenConTipdoc=null;  
          objUti=null;
         
          //System.out.println("CERRANDO LOS OBJETOS..");
        }
         catch (Exception e)  { objUti.mostrarMsgErr_F1(this, e);  }
     }
      
    
    
         
    
    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
            {
                if (txtDesLarTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesCorTipDoc.setText("");
                }
                else
                {
                    BuscarTipoDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
                }
            }
            else
                txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
            {
                if (txtDesCorTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesLarTipDoc.setText("");
                }
                else
                {
                    BuscarTipoDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
                }
            }
            else
                txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    
    
    
      public void BuscarTipoDoc(String campo,String strBusqueda,int tipo){
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos."); 
        if (objVenConTipdoc.buscar(campo, strBusqueda ))
        {
              txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
              txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
              txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
              strCodTipDoc=objVenConTipdoc.getValueAt(1);
              strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
              strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
              
        }
        else
        {     objVenConTipdoc.setCampoBusqueda(tipo);
              objVenConTipdoc.cargarDatos();
              objVenConTipdoc.show();
             if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE)
            {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc=objVenConTipdoc.getValueAt(1);
                strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
                strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
            
             }
              else{
                    txtDesCorTipDoc.setText(strDesCorTipDoc);
                    txtDesLarTipDoc.setText(strDesLarTipDoc);
                  }
        }
    
   }
    
    
    
    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
   
        
              objVenConTipdoc.setTitle("Listado de Tipo de Documentos.");         
              objVenConTipdoc.setCampoBusqueda(1);
              objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE)
            {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc=objVenConTipdoc.getValueAt(1);
                strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
                strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
            } 
              
              
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butguardar;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtMonDoc;
    private javax.swing.JTextField txtNumDoc1;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(516, 4, 148, 20);

            this.setTitle(objParSis.getNombreMenu() + " v0.4");
            lblTit.setText(objParSis.getNombreMenu());
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtMonDoc.setBackground(objParSis.getColorCamposSistema());
           
          
            
             datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
             dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
             datFecAux=null;
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(19);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Código");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_COD_BOD_ORG,"Cód.Bod.Org.");
            vecCab.add(INT_TBL_DAT_BUT_BOD_ORG,"");
            vecCab.add(INT_TBL_DAT_DEL_BOD_ORG,"Bod.Org.");
            vecCab.add(INT_TBL_DAT_COD_BOD_DES,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_BUT_BOD_DES,"");
            vecCab.add(INT_TBL_DAT_DEL_BOD_DES,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT,"Cos.Tot.");
            vecCab.add(INT_TBL_DAT_AUX_COD_SIS,"Aux.Cód.Sis.");
            vecCab.add(INT_TBL_DAT_AUX_COD_BOD_ORG,"Aux.Cód.Bod.Org.");
            vecCab.add(INT_TBL_DAT_AUX_COD_BOD_DES,"Aux.Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_AUX_CAN,"Aux.Can.");
            vecCab.add(INT_TBL_NUMREL,"Cod Reg");
            vecCab.add(INT_TBL_IEBODFIS, "IE.Fis.Bod"); 
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_ALT);
            arlAux.add("" + INT_TBL_DAT_COD_BOD_ORG);
            arlAux.add("" + INT_TBL_DAT_COD_BOD_DES);
            arlAux.add("" + INT_TBL_DAT_CAN);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            arlAux=new java.util.ArrayList();
            arlAux.add(INT_CFE_COD_SIS,"" + INT_TBL_DAT_AUX_COD_SIS);
            arlAux.add(INT_CFE_COD_BOD_ORG,"" + INT_TBL_DAT_AUX_COD_BOD_ORG);
            arlAux.add(INT_CFE_COD_BOD_DES,"" + INT_TBL_DAT_AUX_COD_BOD_DES);
            arlAux.add(INT_CFE_CAN,"" + INT_TBL_DAT_AUX_CAN);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
          //  objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(132);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_ORG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_DES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setResizable(false);
   
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_SIS).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_ORG).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_COD_BOD_DES).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUX_CAN).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            //vecAux.add("" + INT_TBL_DAT_COD_ALT);
           // vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_BOD_ORG);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD_ORG);
            vecAux.add("" + INT_TBL_DAT_COD_BOD_DES);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD_DES);
           // vecAux.add("" + INT_TBL_DAT_COS_UNI);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
          
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_ORG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD_DES).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
            strSQL+=" INNER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
            int intColVen[]=new int[5];
            intColVen[0]=2;
            intColVen[1]=3;
            intColVen[2]=4;
            intColVen[3]=5;
            intColVen[4]=7;
            int intColTbl[]=new int[5];
            intColTbl[0]=INT_TBL_DAT_COD_SIS;
            intColTbl[1]=INT_TBL_DAT_COD_ALT;
            intColTbl[2]=INT_TBL_DAT_NOM_ITM; 
            intColTbl[3]=INT_TBL_DAT_DEC_UNI;
            intColTbl[4]=INT_TBL_DAT_COS_UNI;
            objTblCelEdiTxtCon=new ZafTblCelEdiTxtCon("Listado de inventario", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni", "Maestro,Sistema,Alterno,Nombre,Unidad,Stock,Costo", intColVen, intColTbl);
            objTblCelEdiTxtCon.setIndiceCampoBusqueda(2);
            objTblCelEdiTxtCon.setCampoBusqueda("LOWER(a1.tx_codAlt)", objTblCelEdiTxtCon.INT_CAM_STR);
            objTblCelEdiTxtCon.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellEditor(objTblCelEdiTxtCon);
            objTblCelEdiTxtCon.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Convertir la cadena a minúscula para evitar problemas con mayúsculas/minúsculas.
                    objTblCelEdiTxtCon.setText(objTblCelEdiTxtCon.getText().toLowerCase());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtCon.isConsultaAceptada())
                    {
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
            });
            
            objTblCelEdiBut=new ZafTblCelEdiBut("Listado de inventario", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni", "Maestro,Sistema,Alterno,Nombre,Unidad,Stock,Costo", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularToFil();
                    calcularTotDoc();
                    objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                }
            });
            intColVen=null;
            intColTbl=null;
              
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_bod, a1.tx_nom";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" INNER JOIN tbr_bodLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
            strSQL+=" AND a2.co_loc=" +intCodLocGrp;
            strSQL+=" AND a1.st_reg='A'";
            
           
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_BOD_ORG;
            intColTbl[1]=INT_TBL_DAT_DEL_BOD_ORG;
            objTblCelEdiTxtConBod=new ZafTblCelEdiTxtCon("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom", "Código,Nombre", intColVen, intColTbl);
            objTblCelEdiTxtConBod.setIndiceCampoBusqueda(1);
            objTblCelEdiTxtConBod.setCampoBusqueda("a1.co_bod", objTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtConBod.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_ORG).setCellEditor(objTblCelEdiTxtConBod);
            objTblCelEdiTxtConBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiTxtConBod.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                   
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                   
                }
                
            });
            
            //Armar la sentencia SQL.
            strSQL="";
//            strSQL+="SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct";
//            strSQL+=" FROM tbm_bod AS a1";
//            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
//            strSQL+=" INNER JOIN tbr_bodLoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
//            strSQL+=" WHERE a1.co_emp=" +intCodEmpGrp;
//            strSQL+=" AND a3.co_loc=" +intCodLocGrp;
//            strSQL+=" AND a1.st_reg='A'";
                 
            strSQL="SELECT a3.co_bod, a3.tx_nom, a4.nd_stkAct FROM tbr_bodusr AS a1 "+
            " INNER JOIN tbr_bodLoc AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) "+
            " INNER JOIN tbm_bod AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_bod=a2.co_bod) "+
            " INNER JOIN tbm_invBod AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_bod=a3.co_bod) "+        
            " WHERE a1.co_emp="+intCodEmpGrp+" AND a2.co_loc="+intCodLocGrp+" AND  a1.co_usr="+objParSis.getCodigoUsuario()+" AND a3.st_reg='A'  ";
                   
            objTblCelEdiButBodOrg=new ZafTblCelEdiBut("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a3.co_bod, a3.tx_nom, a4.nd_stkAct", "Código,Nombre,Stock", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_ORG).setCellEditor(objTblCelEdiButBodOrg);
            objTblCelEdiButBodOrg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiButBodOrg.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                    
                }
                
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                   
                        calcularToFil();
                        calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                   
                }
                
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiButBodOrg.setCondicionesSQL(" AND a4.co_itm=" + strAux);
                }
            });
            intColVen=null;
            intColTbl=null;
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_bod, a1.tx_nom";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" INNER JOIN tbr_bodLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
            strSQL+=" AND a2.co_loc=" +intCodLocGrp;
            strSQL+=" AND a1.st_reg='A'";
            
                      
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_BOD_DES;
            intColTbl[1]=INT_TBL_DAT_DEL_BOD_DES;
            objTblCelEdiTxtConBod=new ZafTblCelEdiTxtCon("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a1.co_bod, a1.tx_nom", "Código,Nombre", intColVen, intColTbl);
            objTblCelEdiTxtConBod.setIndiceCampoBusqueda(1);
            objTblCelEdiTxtConBod.setCampoBusqueda("a1.co_bod", objTblCelEdiTxtCon.INT_CAM_NUM);
            objTblCelEdiTxtConBod.setIndiceTipoBusqueda(2);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD_DES).setCellEditor(objTblCelEdiTxtConBod);
            objTblCelEdiTxtConBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiTxtConBod.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
            });
               
            //Armar la sentencia SQL.
            strSQL="";
//            strSQL+="SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct";
//            strSQL+=" FROM tbm_bod AS a1";
//            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
//            strSQL+=" INNER JOIN tbr_bodLoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
//            strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
//            strSQL+=" AND a3.co_loc=" +intCodLocGrp;
//            strSQL+=" AND a1.st_reg='A'";
            
             strSQL="SELECT a3.co_bod, a3.tx_nom, a4.nd_stkAct FROM tbr_bodusr AS a1 "+
            " INNER JOIN tbr_bodLoc AS a2 ON (a2.co_emp=a1.co_emp AND a2.co_bod=a1.co_bod) "+
            " INNER JOIN tbm_bod AS a3 ON (a3.co_emp=a2.co_emp AND a3.co_bod=a2.co_bod) "+
            " INNER JOIN tbm_invBod AS a4 ON (a4.co_emp=a3.co_emp AND a4.co_bod=a3.co_bod) "+        
            " WHERE a1.co_emp="+intCodEmpGrp+" AND a2.co_loc="+intCodLocGrp+" AND  a1.co_usr="+objParSis.getCodigoUsuario()+" AND a3.st_reg='A'  ";
              
            objTblCelEdiButBodDes=new ZafTblCelEdiBut("Listado de bodegas", tblDat, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, "a3.co_bod, a3.tx_nom, a4.nd_stkAct", "Código,Nombre,Stock", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD_DES).setCellEditor(objTblCelEdiButBodDes);
            objTblCelEdiButBodDes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_SIS));
                    if (strAux.equals(""))
                    {
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiButBodDes.setCancelarEdicion(true);
                    }else{
                         calcularToFil();
                         calcularTotDoc();
                        objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiButBodDes.setCondicionesSQL(" AND a4.co_itm=" + strAux);
                }
            });
            intColVen=null;
            intColTbl=null;
             
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularToFil();
                    calcularTotDoc();
                    objAsiDia.generarDiarioTransferencias_cotizacion(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT, CONN_GLO);
                }
            });
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
             
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    
    
    
    
      public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }
   
    
    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
       
        
        
          for(int intRowVal=0; intRowVal<tblDat.getRowCount(); intRowVal++){
                  
                    if(tblDat.getValueAt(intRowVal,INT_TBL_DAT_COD_SIS) != null ){
                                        
                     double dblCan = Double.parseDouble((tblDat.getValueAt(intRowVal, INT_TBL_DAT_COS_UNI)==null)?"0":tblDat.getValueAt(intRowVal, INT_TBL_DAT_COS_UNI).toString());
                     
                     double dblCodBodOri = Double.parseDouble( (tblDat.getValueAt(intRowVal, INT_TBL_DAT_COD_BOD_ORG)==null || tblDat.getValueAt(intRowVal, INT_TBL_DAT_COD_BOD_ORG).toString().equals("") )?"0":tblDat.getValueAt(intRowVal, INT_TBL_DAT_COD_BOD_ORG).toString());
                    
                     
                     
                      
                    if(dblCan <= 0.00 ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                  
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.changeSelection(intRowVal, INT_TBL_DAT_COS_UNI, true, true);
                        return false; 
                    }
                     
                      if(dblCodBodOri <= 0.00 ){
                        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                  
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        tblDat.repaint();    
                        tblDat.requestFocus();
                        tblDat.changeSelection(intRowVal, INT_TBL_DAT_COD_BOD_ORG, true, true);
                        return false; 
                    }
                       
                     
          }}          
        
        
        //Validar que el "Diario esté cuadrado".
        if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que el "Monto del diario" sea igual al monto del documento.
        if (!objAsiDia.isDocumentoCuadrado(txtMonDoc.getText()))
        {
            mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
            txtMonDoc.selectAll();
            txtMonDoc.requestFocus();
            return false;
        }
      
        return true;
    }

    
    
    
    
    
    
    
    
    
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
private boolean insertarReg(){
  boolean blnRes=false;
  String strMerIngEgr="", strTipIngEgr="";
  int  intNumDoc_Tra=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
     if(CONN_GLO!=null) {
         stmLoc=CONN_GLO.createStatement();
           
         strSQL = "SELECT ne_ultDoc, st_predoc,  st_meringegrfisbod, tx_natdoc  FROM tbm_cabTipDoc WHERE co_emp="+intCodEmpGrp+" AND co_loc="+intCodLocGrp+" AND co_tipDoc="+txtCodTipDoc.getText();
         rstLoc=stmLoc.executeQuery(strSQL);
         if(rstLoc.next()){
            intNumDoc_Tra = rstLoc.getInt("ne_ultDoc");
            strMerIngEgr=rstLoc.getString("st_meringegrfisbod");  
            strTipIngEgr=rstLoc.getString("tx_natdoc");
          }
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;
                
          if(insertarCab( intNumDoc_Tra, strMerIngEgr, strTipIngEgr )){
            if(insertarDet(strMerIngEgr, strTipIngEgr)){
              if(actualizarTbmInvBod(0)){
                if(VerificaStock()){    
                  if(objAsiDia.insertarDiario_OC( CONN_GLO, intCodEmpGrp, intCodLocGrp, Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtCodDoc.getText(),  objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), 779 )){
                       recostearItm();
                       blnRes=true;
                   }else{ CONN_GLO.rollback();  if(CONN_REMOTO!=null) CONN_GLO_LOC.rollback(); } 
                 }else {  CONN_GLO.rollback();  if(CONN_REMOTO!=null) CONN_GLO_LOC.rollback(); } 
                }else {  CONN_GLO.rollback();  if(CONN_REMOTO!=null) CONN_GLO_LOC.rollback(); } 
               }else {  CONN_GLO.rollback();  if(CONN_REMOTO!=null) CONN_GLO_LOC.rollback(); } 
             }else {  CONN_GLO.rollback();  if(CONN_REMOTO!=null) CONN_GLO_LOC.rollback(); }
      
  }}catch(java.sql.SQLException e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch (Exception e) { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
  return blnRes;
}

    
    
   
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(int intNumDoc_Tra, String strMerIngEgr, String strTipIngEgr)
    {
        int intCodUsr, intUltSecGrp, intUltSecEmp, intUltReg=1; //, intNumDoc_Tra=0;
        boolean blnRes=true;
//        String strMerIngEgr="", strTipIngEgr="";
        try
        {
            if (CONN_GLO!=null)
            {
                stm=CONN_GLO.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
             
                
                ///******************* OBTENER EL MAX DE SEC GRUPO Y EMPRESA ****//////
//                           intUltSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(CONN_GLO);
//                           intUltSecGrp = objUltDocPrint.getNumeroOrdenGrupo(CONN_GLO);


                           intUltSecEmp = 0;
                           intUltSecGrp = 0;
                ///*******************************************************************///
                                        
                
                
                
                //Obtener el código para "co_doc".
                strSQL="";
                strSQL+="SELECT  case when  MAX(a1.co_doc)+1 is null then 1 else MAX(a1.co_doc)+1 end as codoc ";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
                strSQL+=" AND a1.co_loc=" + intCodLocGrp;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if(rst.next()) intUltReg=rst.getInt(1);
                
     
                 intNumDoc_Tra++;
                
                
                 strSQL ="Update tbm_cabTipDoc set ne_ultDoc ="+intNumDoc_Tra+" WHERE co_emp="+intCodEmpGrp+" AND co_loc="+intCodLocGrp+" AND co_tipDoc="+txtCodTipDoc.getText();      
                 stm.executeUpdate(strSQL);                                               
                                                                
                                                                
                String strconinv="F";
                for(int i=0; i<tblDat.getRowCount();i++){
                 if(tblDat.getValueAt(i, INT_TBL_DAT_COD_SIS)!=null){
                   String strEst=(tblDat.getValueAt(i, INT_TBL_IEBODFIS)==null?"":tblDat.getValueAt(i, INT_TBL_IEBODFIS).toString());                          
                     if(strEst.equals("N")){
                          strconinv="P";
                }}}
                if(strconinv.equals("P"))
                 if(strMerIngEgr.equals("N")) strconinv="F";                                                
                                                                
                                                                
                 //INT_TBL_IEBODFIS  st_coninv  st_autingegrinvcon  
                txtCodDoc.setText("" + intUltReg);
                txtNumDoc1.setText("" + intNumDoc_Tra);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv (co_emp, co_loc, co_tipDoc, co_doc, ne_secGrp, ne_secEmp " +
                        //", ne_secgrpant, ne_secempant " +
                        ",  ne_numCot, ne_numDoc, tx_numPed";
                strSQL+=", ne_numGui, co_dia, fe_doc, co_cli, tx_ruc, tx_nomCli, tx_dirCli, tx_telCli, tx_ciuCli, co_com, tx_nomVen, tx_ate, co_forPag";
                strSQL+=", tx_desForPag, nd_sub, nd_porIva, nd_valIva, nd_tot, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, co_cta, co_motDoc, co_mnu, tx_obs1 ,tx_obs2";
                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, fe_con, tx_obs3, co_forRet, tx_vehRet, tx_choRet, st_coninv, st_autingegrinvcon  )";
                strSQL+=" VALUES (";
                strSQL+=intCodEmpGrp;
                strSQL+=", " + intCodLocGrp;
                strSQL+=", " + txtCodTipDoc.getText();
                strSQL+=", " + intUltReg;
                strSQL+=", " + intUltSecGrp; //ne_secGrp
                strSQL+=", " + intUltSecEmp; //ne_secEmp
                //strSQL+=", " + (intUltSecGrp-1); //ne_secGrpant
                //strSQL+=", " + (intUltSecEmp-1); //ne_secEmpant
                strSQL+=", Null"; //ne_numCot
                strSQL+=", " +intNumDoc_Tra; //ne_numDoc
                strSQL+=", Null"; //tx_numPed
                strSQL+=", Null"; //ne_numGui
                strSQL+=", Null"; //co_dia
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_doc
                strSQL+=", Null"; //co_cli
                strSQL+=", Null"; //tx_ruc
                strSQL+=", Null"; //tx_nomCli
                strSQL+=", Null"; //tx_dirCli
                strSQL+=", Null"; //tx_telCli
                strSQL+=", Null"; //tx_ciuCli
                strSQL+=", Null"; //co_com
                strSQL+=", Null"; //tx_nomVen
                strSQL+=", Null"; //tx_ate
                strSQL+=", Null"; //co_forPag
                strSQL+=", Null"; //tx_desForPag
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3); //nd_sub
                strSQL+=", 0"; //nd_porIva
                strSQL+=", 0"; //nd_valIva
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3); //nd_tot
                strSQL+=", Null"; //tx_ptoPar
                strSQL+=", Null"; //tx_tra
                strSQL+=", Null"; //co_motTra
                strSQL+=", Null"; //tx_desMotTra
                strSQL+=", Null"; //co_cta
                strSQL+=", Null"; //co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                strSQL+=", 'A'"; //st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+=", " + intCodUsr; //co_usrIng
                strSQL+=", " + intCodUsr; //co_usrMod
                strSQL+=", Null"; //fe_con
                strSQL+=", Null"; //tx_obs3
                strSQL+=", Null"; //co_forRet
                strSQL+=", Null"; //tx_vehRet
                strSQL+=", Null"; //tx_choRet
                strSQL+=", '"+strconinv+"', 'N' )";  // st_coninv, st_autingegrinvcon
                //System.out.println( " " + strSQL );
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                       
                                   
                if(CONN_REMOTO!=null) {  
                    //System.out.println("Not Null.. ");   
                    java.sql.Statement stmloc=CONN_GLO_LOC.createStatement();
                    //System.out.println( " " + strSQL );
                    stmloc.executeUpdate(strSQL);    
                     strSQL ="INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel)"+
                                               " values("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
                                                intTipDocFac+","+intCodDocFac+",'A',"+intCodEmpGrp+","+intCodLocGrp+","+
                                                txtCodTipDoc.getText()+","+intUltReg+")";
                    //  System.out.println( " " + strSQL );
                       stmloc.executeUpdate(strSQL);             
                        stmloc.close();
                        stmloc=null;                  
                    //  System.out.println( "OK " );
                }else{
                        //System.out.println("Nullo.. ");      
                     
                        java.sql.Statement stm=CONN_GLO.createStatement();
                       
                        strSQL ="INSERT INTO  tbr_cabMovInv(co_emp,co_loc,co_tipdoc,co_doc,st_reg, co_emprel, co_locrel,co_tipdocrel,co_docrel)"+
                                               " values("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
                                                intTipDocFac+","+intCodDocFac+",'A',"+intCodEmpGrp+","+intCodLocGrp+","+
                                                txtCodTipDoc.getText()+","+intUltReg+")";
                        //System.out.println("Nullo.. "+ strSQL ); 
                          
                        stm.executeUpdate(strSQL);             
                        stm.close();
                        stm=null;          
                    
                }
                 
                   
                
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
    
    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet( String strMerIngEgr, String strTipIngEgr)
    {
        int intCodEmp, i, j;
        String strCodTipDoc, strCodDoc;
        boolean blnRes=true;
        try
        {
            if (CONN_GLO!=null)
            {
                stm=CONN_GLO.createStatement();
                intCodEmp=intCodEmpGrp;
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;
                
                String str_MerIEFisBod="S";
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    
                   //***********   VERFIFICA SI EL ITEM IE MER FIS BOD  ************************/  
                    str_MerIEFisBod="N";
                    String strSQLFisBod="";
                     String strEst=objTblMod.getValueAt(i,INT_TBL_IEBODFIS).toString();                          
                     if(strEst.equals("N")){
                      if(strMerIngEgr.equals("S")){
                        str_MerIEFisBod="S";  
                        if(strTipIngEgr.equals("A")){ 
                          strSQLFisBod="UPDATE tbm_invbod SET nd_canegrbod=nd_canegrbod+"+objTblMod.getValueAt(i,INT_TBL_DAT_CAN).toString()+"" +
                          " WHERE co_emp="+intCodEmp+" AND co_itm="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString()+" " +
                          " AND co_bod="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG).toString()+" ";
                         }
                      }}
                    //*****************************************************************************
                     
                    //Armar la sentencia SQL (Egreso).
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detMovInv (co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_numfil, co_itm, co_itmact,  tx_codAlt, tx_codAlt2";
                    strSQL+=", tx_nomItm, tx_uniMed, co_bod, nd_can, nd_canOrg, nd_cosUni, nd_preUni, nd_porDes, st_ivaCom, nd_tot, nd_cosTot, nd_exi";
                    strSQL+=", nd_valExi, nd_cosPro, nd_cosUniGrp, nd_cosTotGrp, nd_exiGrp, nd_valExiGrp, nd_cosProGrp, st_reg, nd_canCon, tx_obs1, co_usrCon, st_meringegrfisbod )";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLocGrp;
                    strSQL+=", " + strCodTipDoc;
                    strSQL+=", " + strCodDoc;
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + (i+1); //ne_numfil
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS); //co_itm
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS); //co_itm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT)); //tx_codAlt
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT)); //tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM)); //tx_nomItm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEC_UNI)); //tx_uniMed
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG); //co_bod
                    strSQL+=", -" + objTblMod.getValueAt(i,INT_TBL_DAT_CAN); //tx_can
                    strSQL+=", Null"; //nd_canOrg
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUni
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_preUni
                    strSQL+=", 0"; //nd_porDes
                    strSQL+=", 'N'"; //st_ivaCom
                    strSQL+=", -" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_tot
                    strSQL+=", -" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTot
                    strSQL+=", Null"; //nd_exi
                    strSQL+=", Null"; //nd_valExi
                    strSQL+=", Null"; //nd_cosPro
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUniGrp
                    strSQL+=", -" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTotGrp
                    strSQL+=", Null"; //nd_exiGrp
                    strSQL+=", Null"; //nd_valExiGrp
                    strSQL+=", Null"; //nd_cosProGrp
                    strSQL+=", 'S'"; //st_reg
                    strSQL+=", 0"; //nd_canCon
                    strSQL+=", Null"; //tx_obs1
                    strSQL+=", Null"; //co_usrCon
                    strSQL+=", '"+str_MerIEFisBod+"' ";
                    strSQL+=")";
                    //System.out.println("strSQL: " + strSQL);
                    stm.executeUpdate(strSQL);
                  
                     if(!(strSQLFisBod.equals(""))) stm.executeUpdate(strSQLFisBod);
                     
                      if(CONN_REMOTO!=null) {  
                        //System.out.println("Not Null.. ");    
                         java.sql.Statement stm2=CONN_GLO_LOC.createStatement();
                         stm2.executeUpdate(strSQL);  
                         if(!(strSQLFisBod.equals(""))) stm2.executeUpdate(strSQLFisBod);  
                         stm2.close();
                         stm2=null;                  

                    }
                      //else{  
//                            //System.out.println("Nullo.. ");  
//
//                            java.sql.Statement stm2=CONN_GLO.createStatement();
//
//                            strSQL ="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel,co_emprel)"+
//                                                   " values("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
//                                                    intTipDocFac+","+intCodDocFac+"," +
//                                                    objTblMod.getValueAt(i,INT_TBL_NUMREL).toString()+",'A',"+intCodLoc+","+
//                                                    strCodTipDoc+","+strCodDoc+","+j+","+intCodEmpGrp+")";
//                              System.out.println("Nullo.. "+ strSQL ); 
//                            stm2.executeUpdate(strSQL);             
//                            stm2.close();
//                            stm2=null;          
//
//                    }
//                    
//                    
                    
                    j++;
                    
                    
                     //***********   VERFIFICA SI EL ITEM IE MER FIS BOD  ************************/  
                     strSQLFisBod="";
                     str_MerIEFisBod="N";
                     if(strEst.equals("N")){
                      if(strMerIngEgr.equals("S")){
                        str_MerIEFisBod="S";  
                        if(strTipIngEgr.equals("A")){ 
                          strSQLFisBod="UPDATE tbm_invbod SET nd_caningbod=nd_caningbod+"+objTblMod.getValueAt(i,INT_TBL_DAT_CAN).toString()+"" +
                          " WHERE co_emp="+intCodEmp+" AND co_itm="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString()+" " +
                          " AND co_bod="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES).toString()+" ";
                         }
                      }}
                    //*****************************************************************************
               
                     
                    //Armar la sentencia SQL (Ingreso).
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detMovInv (co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_numfil, co_itm, co_itmact , tx_codAlt, tx_codAlt2";
                    strSQL+=", tx_nomItm, tx_uniMed, co_bod, nd_can, nd_canOrg, nd_cosUni, nd_preUni, nd_porDes, st_ivaCom, nd_tot, nd_cosTot, nd_exi";
                    strSQL+=", nd_valExi, nd_cosPro, nd_cosUniGrp, nd_cosTotGrp, nd_exiGrp, nd_valExiGrp, nd_cosProGrp, st_reg, nd_canCon, tx_obs1, co_usrCon, st_meringegrfisbod )";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLocGrp;
                    strSQL+=", " + strCodTipDoc;
                    strSQL+=", " + strCodDoc;
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + (i+1); //ne_numfil
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS); //co_itm
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS); //co_itm
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT)); //tx_codAlt
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT)); //tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM)); //tx_nomItm 
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEC_UNI)); //tx_uniMed
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES); //co_bod
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN); //tx_can
                    strSQL+=", Null"; //nd_canOrg
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUni
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_preUni
                    strSQL+=", 0"; //nd_porDes
                    strSQL+=", 'N'"; //st_ivaCom
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_tot
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTot  
                    strSQL+=", Null"; //nd_exi
                    strSQL+=", Null"; //nd_valExi
                    strSQL+=", Null"; //nd_cosPro
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI); //nd_cosUniGrp
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT); //nd_cosTotGrp
                    strSQL+=", Null"; //nd_exiGrp
                    strSQL+=", Null"; //nd_valExiGrp
                    strSQL+=", Null"; //nd_cosProGrp  
                    strSQL+=", 'S'"; //st_reg
                    strSQL+=", 0"; //nd_canCon
                    strSQL+=", Null"; //tx_obs1
                    strSQL+=", Null"; //co_usrCon
                    strSQL+=", '"+str_MerIEFisBod+"' "; 
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                    
                    if(!(strSQLFisBod.equals(""))) stm.executeUpdate(strSQLFisBod);
                     
                     if(CONN_REMOTO!=null) {  
                        //System.out.println("Not Null.. ");    
                         java.sql.Statement stm2=CONN_GLO_LOC.createStatement();
                         stm2.executeUpdate(strSQL);   
                         if(!(strSQLFisBod.equals(""))) stm2.executeUpdate(strSQLFisBod);   
                         stm2.close();
                         stm2=null;                  
                    }
                    
                      
                      
                    
                    
//               if(CONN_REMOTO!=null) {  
//                    //System.out.println("Not Null.. ");    
//                    java.sql.Statement stm2=CONN_GLO_LOC.createStatement();
//                     stm2.executeUpdate(strSQL);     
//                     strSQL ="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel,co_emprel)"+
//                                               " values("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
//                                                intTipDocFac+","+intCodDocFac+"," +
//                                                objTblMod.getValueAt(i,INT_TBL_NUMREL).toString()+",'A',"+intCodLocGrp+","+
//                                                strCodTipDoc+","+strCodDoc+","+j+","+intCodEmpGrp+")";
//                               
//                              System.out.println(" >>> "+ strSQL );
//                              stm2.executeUpdate(strSQL);             
//                        stm2.close();
//                        stm2=null;                  
//                      
//                }else{
//                        //System.out.println("Nullo.. ");  
//                     
//                        java.sql.Statement stm2=CONN_GLO.createStatement();
//                       
//                        strSQL ="INSERT INTO  tbr_detMovInv(co_emp,co_loc,co_tipdoc,co_doc, co_reg,st_reg,co_locrel,co_tipdocrel,co_docrel,co_regrel,co_emprel)"+
//                                               " values("+objParSis.getCodigoEmpresa()+","+objParSis.getCodigoLocal()+","+
//                                                intTipDocFac+","+intCodDocFac+"," +
//                                                objTblMod.getValueAt(i,INT_TBL_NUMREL).toString()+",'A',"+intCodLocGrp+","+
//                                                strCodTipDoc+","+strCodDoc+","+j+","+intCodEmpGrp+")";
//                          System.out.println("Nullo.. "+ strSQL ); 
//                        stm2.executeUpdate(strSQL);             
//                        stm2.close();  
//                        stm2=null;          
//                    
//                }
//                    
                    
                   j++; 
                    
                    
                }
                stm.close();
                stm=null;
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
      
    /**
     * Esta función permite actualizar la tabla "tbm_invBod".  
     * @param intTipOpe El tipo de operación.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operación</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.  
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmInvBod(int intTipOpe)
    {
        int intCodEmp, i;
        double dblCan1, dblCan2;
        boolean blnRes=true;
        try
        {
           // if (!objTooBar.getEstadoRegistro().equals("Anulado"))    
           // {
                if (CONN_GLO!=null)  
                {
                    stm=CONN_GLO.createStatement();
                    
                    java.sql.Statement stmloc=CONN_GLO_LOC.createStatement();
                   
                    
                    intCodEmp=intCodEmpGrp;
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        dblCan1=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN));
                        dblCan2=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN));
                        switch (intTipOpe)
                        {
                            case 0:
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct-" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                  if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                break;
                            case 1:
                                strAux=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN));
                                if (strAux.equals("M"))
                                {
                                    if (objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS)==objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS) && objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG)==objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_BOD_ORG) && objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES)==objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_BOD_DES))
                                    {
                                        //Caso 1: El usuario sólo modificó la cantidad.
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct" + ((dblCan1>dblCan2)?"-" + objUti.redondear(dblCan1-dblCan2,objParSis.getDecimalesBaseDatos()):"+" + objUti.redondear(dblCan2-dblCan1,objParSis.getDecimalesBaseDatos()));
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                          if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct" + ((dblCan1>dblCan2)?"+" + objUti.redondear(dblCan1-dblCan2,objParSis.getDecimalesBaseDatos()):"-" + objUti.redondear(dblCan2-dblCan1,objParSis.getDecimalesBaseDatos()));
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                          if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                    }
                                    else
                                    {
                                        //Caso 2 (Paso 1): El usuario cambió algo que debe ser considerado como una eliminación.
                                        java.util.ArrayList arlAux=new java.util.ArrayList();
                                        arlAux.add(INT_CFE_COD_SIS,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS));
                                        arlAux.add(INT_CFE_COD_BOD_ORG,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_BOD_ORG));
                                        arlAux.add(INT_CFE_COD_BOD_DES,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_BOD_DES));
                                        arlAux.add(INT_CFE_CAN,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN));
                                        objTblMod.addRowDataSavedBeforeRemoveRow(arlAux);
                                        //Caso 2 (Paso 2): Considerar la modificación como una inserción.
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct-" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                          if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                        stm.executeUpdate(strSQL);
                                          if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                    }
                                }      
                                else if (strAux.equals("I"))
                                {
                                    //Caso 3: El usuario insertó una fila.
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_invBod";
                                    strSQL+=" SET nd_stkAct=nd_stkAct-" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                    strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                    stm.executeUpdate(strSQL);
                                      if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_invBod";
                                    strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                    strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                    stm.executeUpdate(strSQL);
                                      if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                }
                                break;
                            case 2:
                            case 3:
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                  if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct-" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_DES);
                                strSQL+=" AND co_itm=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS);
                                stm.executeUpdate(strSQL);
                                  if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                break;
                        }
                    }
                    switch (intTipOpe)
                    {
                        case 1:
                            //Caso 4: El usuario eliminó una o más filas.
                            java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                            for (i=0;i<arlAux.size();i++)
                            {
                                dblCan1=objUti.getDoubleValueAt(arlAux,i,INT_CFE_CAN);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_BOD_ORG);
                                strSQL+=" AND co_itm=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_SIS);
                                stm.executeUpdate(strSQL);
                                  if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct-" + objUti.redondear(dblCan1,objParSis.getDecimalesBaseDatos());
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_BOD_DES);
                                strSQL+=" AND co_itm=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_SIS);
                                stm.executeUpdate(strSQL);
                                  if(CONN_REMOTO!=null) stmloc.executeUpdate(strSQL);
                            }
                            arlAux=null;
                            break;
                    }
                    stm.close();
                    stm=null;
                    stmloc.close();
                    stmloc=null;
                }
           // }
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
        
       
    
    /*
     *Esta Funcion permite verificar el stock 
     *de la bodega de donde se extrae 
     */
    private boolean VerificaStock(){
        boolean blnRes=false;
        StringBuffer stb=new StringBuffer(); //VARIABLE TIPO BUFFER
        try{  
         for (int i=0;i<objTblMod.getRowCountTrue();i++)
         {
             
               //**********************  STOCK DEL ITEM **************************//
                     if (i>0)
                        stb.append(" UNION ALL ");
                        stb.append("SELECT a2.nd_stkAct , a2.co_itm "+
                                   " FROM  tbm_invBod AS a2  " +
                                   " WHERE a2.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND a2.co_bod="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD_ORG)+" " +
                                   " AND co_itm="+objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS) );
                                
                          
         }
         int Estado=0;
         if(CONN_GLO!=null){
            stm=CONN_GLO.createStatement();
             ResultSet rst_stock = stm.executeQuery(stb.toString());
             while(rst_stock.next()){
                                      if(rst_stock.getFloat(1) < 0.00){
                                       for (int i=0;i<objTblMod.getRowCountTrue();i++){
                                          if(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS) != null ){
                                           if( objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString().equals(rst_stock.getString(2))){    
                                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                                            String strTit, strMsg;
                                            strTit="Mensaje del sistema Zafiro";
                                            strMsg="El producto : " +  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT))  
                                            + "   -   " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM))  + " No tiene stock suficiente "+ "\n No se puede Realizar la transferencia !!" ;
                                            oppMsg.showMessageDialog(jfrThis, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
                                            Estado=1;      
                                            break;
                                       }}}
                              }
                           }  
            if(Estado==0) blnRes=true;
            
         }
        } 
        catch (java.sql.SQLException e){  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
       return blnRes;
    }
    
    
   
   
    /**
     * Esta función calcula el total de la fila.
     */
    private void calcularToFil()
    {
        int intFilSel;
        intFilSel=tblDat.getSelectedRow();
        objTblMod.setValueAt("" + objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN))*objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI)), intFilSel, INT_TBL_DAT_COS_TOT);
    }
    
    /**
     * Esta función calcula el total del documento.
     */
    private void calcularTotDoc()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {
            intFilPro=objTblMod.getRowCount();
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            //Calcular la diferencia.
            txtMonDoc.setText("" + objUti.redondear(dblTot,objParSis.getDecimalesBaseDatos()));
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
        }
    }

   
    /**  
     * Esta función permite recostear los itmes de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean recostearItm()
    {
        int i, j;
        String strFecRan[][];
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                if (intCodEmpGrp==objParSis.getCodigoEmpresaGrupo())
                {
                    strFecRan=objUti.getIntervalosMensualesRangoFechas(dtpFecDoc.getText(), dtpFecDoc.getText(), "dd/MM/yyyy");
                    //Recostear los items del "Grupo".
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        for (j=0; j<strFecRan.length; j++)
                            objUti.recostearItmGrp(this, objParSis, con,  intCodEmpGrp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), strFecRan[j][0], strFecRan[j][1], "yyyy/MM/dd");
                    }
                }
                else
                {
                    strFecRan=objUti.getIntervalosAnualesRangoFechas(dtpFecDoc.getText(), dtpFecDoc.getText(), "dd/MM/yyyy");
                    //Recostear los items de la "Empresa seleccionada".
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        for (j=0; j<strFecRan.length; j++)
                            objUti.recostearItm(this, objParSis, con,  intCodEmpGrp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), strFecRan[j][0], strFecRan[j][1], "yyyy/MM/dd");
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
  
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_COD_BOD_ORG:
                    strMsg="Código de la bodega origen";
                    break;
                case INT_TBL_DAT_DEL_BOD_ORG:
                    strMsg="Nombre de la bodega origen";
                    break;
                case INT_TBL_DAT_COD_BOD_DES:
                    strMsg="Código de la bodega destino";
                    break;
                case INT_TBL_DAT_DEL_BOD_DES:
                    strMsg="Nombre de la bodega destino";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg="Costo total";
                    break;
                default:
                    strMsg="";
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
                    calcularTotDoc();
                    objAsiDia.generarDiarioTransferencias(tblDat, INT_TBL_DAT_COD_BOD_ORG, INT_TBL_DAT_COD_BOD_DES, INT_TBL_DAT_COS_TOT);
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
    
}
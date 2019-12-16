/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package RecursosHumanos.ZafRecHum15;

import Librerias.ZafMae.ZafMaeVen.ZafVenEstCiv;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenCiu;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenOfi;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.JasperManager;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.view.JasperViewer;
//import java.util.Map;
//import java.util.HashMap;

/**
 *
 * @author Roberto Flores
 * Guayaquil 26/11/2012
 */
public class ZafRecHum15 extends javax.swing.JInternalFrame {

  Librerias.ZafParSis.ZafParSis objZafParSis;
  ZafUtil objUti; /**/
  //private ZafSelFec objSelFec;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLblCol;
  Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLblCol2;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
//  private Librerias.ZafDate.ZafDatePicker txtFec;
  private ZafThreadGUI objThrGUI;
  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
  private ZafTblTot objTblTot;                                //JTable de totales.
  private ZafVenOfi zafVenOfi;
  
  ZafVenCiu objVenConCiu;                                     //Ventana de consulta de Ciudad.
  private ZafVenEstCiv zafVenEstCiv;                          //Ventana de consulta de Estado Civil.
  ZafVenCon objVenConUsr;
  
//  private ZafVenCon objVenConOfi;

  private final int INT_TBL_LINEA=0;            // NUMERO DE LINEAS
  private final int INT_TBL_TIPO=1;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_COD_RUB=2;          // CODIGO DEL EMPLEADO
  private final int INT_TBL_NOM_RUB=3;          // CODIGO DEL EMPLEADO
  private final int INT_TBL_SEL_ACT=4;          // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX1=5;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX2=6;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX3=7;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX4=8;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX5=9;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX6=10;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX7=11;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX8=12;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX9=13;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_AUX10=14;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_SUBTOTAL=15;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_TOTAL=16;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_PORC=17;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_VAL_TOT=18;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_BUT_ANEXO=19;             // CODIGO DEL EMPLEADO
  private final int INT_TBL_EST=20;             // CODIGO DEL EMPLEADO
  
//  final int INT_TBL_EMP=2;           // NOMBRE DEL EMPLEADO

  String strVersion=" v1.01";
  
  private ZafVenCon vcoEmp;                                               //Ventana de consulta.
  private ZafVenCon vcoTra;
  
  String strCodTipDoc="";
  String strDesCodTipDoc="";
  String strDesLarTipDoc="";
  String strCodLoc="";
  String strDesLoc="";
  String strCodUsr="";
  String strUsr="";

  Vector vecCab=new Vector();
  java.util.Vector vecCoTra = new java.util.Vector();
  javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
  
  String strSqlLoc="";
  String strNomOfi = "";
  String strCodOfi="";
  
  private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private String strCodMotJus, strDesMot;
    
    private ZafTblCelEdiButGen objTblCelEdiButGen;
  
  private int intCanReg=0;
  
  private ZafTblOrd objTblOrd;
  private ZafTblBus objTblBus;
  
  final java.awt.Color colFonCol =new java.awt.Color(255,255,204);
  
  private String strFecDes="";
  private String strFecHas="";
  
  private ZafRecHum15_01 objRecHum15_01;

    /** Creates new form ZafRecHum10 */
    public ZafRecHum15(Librerias.ZafParSis.ZafParSis obj) {
          try{ /**/
              this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();


              

              initComponents();
              
              /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
          cargarAños();

              this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
              lblTit.setText(objZafParSis.getNombreMenu());  /**/

//              txtFec = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
//              txtFec.setBackground(objZafParSis.getColorCamposObligatorios());
//              txtFec.setPreferredSize(new java.awt.Dimension(70, 20));
//              //txtFec.setText("");
//              panFil.add(txtFec);
//              txtFec.setBounds(120, 40, 92, 20);
//              //txtFecDoc.setEnabled(false);
//              //txtFec.setBackground(objZafParSis.getColorCamposObligatorios());
//              txtFec.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
//              txtFec.addActionListener(new java.awt.event.ActionListener() {
//                  public void actionPerformed(java.awt.event.ActionEvent evt) {
//                      txtFecActionPerformed(evt);
//                  }
//
//                private void txtFecActionPerformed(ActionEvent evt) {
//                    txtFec.transferFocus();
//                }
//              });
              
              objUti = new ZafUtil(); /**/
              objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

              zafVenOfi = new ZafVenOfi(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Oficinas");
             
         }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }
    
    public ZafRecHum15(Librerias.ZafParSis.ZafParSis obj, String strCoTra, String strNomTra, String strNeAni, String strNeMes, String strNePer) {
        try{ /**/
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();


             initComponents();
              
              /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
            cargarAños();
             
             cboPerAAAA.setSelectedIndex(Integer.valueOf(strNeAni));
             cboPerMes.setSelectedIndex(Integer.valueOf(strNeMes));
             cboPer.setSelectedIndex(Integer.valueOf(strNePer));
             this.txtCodTra.setText(strCoTra);
             this.txtNomTra.setText(strNomTra);
             this.txtCodEmp.setText(String.valueOf(objZafParSis.getCodigoEmpresa()));
             this.txtNomEmp.setText(objZafParSis.getNombreEmpresa());

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            objUti = new ZafUtil(); /**/
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            zafVenOfi = new ZafVenOfi(JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Oficinas");
            
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }

           }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }
    
    private boolean cargarAños(){
    boolean blnRes=true;
    java.sql.Connection con = null; 
    java.sql.Statement stmLoc = null;
    java.sql.ResultSet rstLoc = null; 
    String strSQL="";

    try{
        con =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(con!=null){ 
            
            stmLoc=con.createStatement();
            
            strSQL="select distinct ne_ani from tbm_ingEgrMenTra order by ne_ani desc";
            rstLoc=stmLoc.executeQuery(strSQL);
            while(rstLoc.next()){
                cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
            }
        }
    }catch (java.sql.SQLException Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    } catch (Exception Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
    }
    return blnRes;
  } 



     /**
      * Carga ventanas de consulta y configuracion de la tabla
      */

    public void Configura_ventana_consulta(){

//        configurarVenConOficinas();
        configurarVenConTra();
        configurarVenConEmp();
        ConfigurarTabla();
    }
    
    
    
    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            //Armar la sentencia SQL.

//            String strAux = "";
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                strAux = " and a1.co_emp in ("+objParSis.getCodigoEmpresa() +")";
//            }
            String strSQL="";
            if(objZafParSis.getCodigoUsuario()==1){
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')" + strAux;
//                strSQL+=" ORDER BY a1.co_emp";
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objZafParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objZafParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objZafParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");
            
            String strSQL="";
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

//private boolean configurarVenConOficinas() {
//      //strAliCamSenSQL  
//      boolean blnRes=true;
//        try {
//            ArrayList arlCam=new ArrayList();
//            arlCam.add("a.co_ofi");
//            arlCam.add("a.tx_nom");
//            ArrayList arlAli=new ArrayList();
//            arlAli.add("Código");
//            arlAli.add("Nombre");
//            ArrayList arlAncCol=new ArrayList();
//            arlAncCol.add("70");
//            arlAncCol.add("110");
//            //Armar la sentencia SQL.
//            String  strSQL="";
//            strSQL="select a.co_ofi, a.tx_nom  from tbm_ofi as a" +
//                    " where a.st_reg not in ('I','E') order by a.tx_nom";
//            objVenConOfi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
//            arlCam=null;
//            arlAli=null;
//            arlAncCol=null;
//        }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
//        return blnRes;
//    }

private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{

        //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_TIPO,"");
        vecCab.add(INT_TBL_COD_RUB,"Cód.Rub.");
        vecCab.add(INT_TBL_NOM_RUB,"Rubro");
        vecCab.add(INT_TBL_SEL_ACT,"");
        vecCab.add(INT_TBL_AUX1,"Aux.1.");
        vecCab.add(INT_TBL_AUX2,"Aux.2.");
        vecCab.add(INT_TBL_AUX3,"Aux.3.");
        vecCab.add(INT_TBL_AUX4,"Aux.4.");
        vecCab.add(INT_TBL_AUX5,"Aux.5.");
        vecCab.add(INT_TBL_AUX6,"Aux.6.");
        vecCab.add(INT_TBL_AUX7,"Aux.7.");
        vecCab.add(INT_TBL_AUX8,"Aux.8.");
        vecCab.add(INT_TBL_AUX9,"Aux.9.");
        vecCab.add(INT_TBL_AUX10,"Aux.10.");
        vecCab.add(INT_TBL_SUBTOTAL,"Subtotal");
        vecCab.add(INT_TBL_TOTAL,"Total");
        vecCab.add(INT_TBL_PORC,"");
        vecCab.add(INT_TBL_VAL_TOT,"");
        vecCab.add(INT_TBL_BUT_ANEXO,"");
        vecCab.add(INT_TBL_EST,"");
        
        
	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        
        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
        objTblMod.setColumnDataType(INT_TBL_PORC, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_SUBTOTAL, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_TOTAL, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_VAL_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_AUX7, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_AUX8, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_AUX9, objTblMod.INT_COL_DBL, new Integer(0), null);
        objTblMod.setColumnDataType(INT_TBL_AUX10, objTblMod.INT_COL_DBL, new Integer(0), null);
////        objTblMod.setColumnDataType(INT_TBL_AUX1, objTblMod.INT_COL_INT, new Integer(0), null);
////        objTblMod.setColumnDataType(INT_TBL_AUX2, objTblMod.INT_COL_INT, new Integer(0), null);
////        objTblMod.setColumnDataType(INT_TBL_AUX3, objTblMod.INT_COL_INT, new Integer(0), null);
////        objTblMod.setColumnDataType(INT_TBL_AUX4, objTblMod.INT_COL_INT, new Integer(0), null);
//        objTblMod.setColumnDataType(INT_TBL_AUX5, objTblMod.INT_COL_INT, new Integer(0), null);
        
        //Configurar JTable: Establecer el modelo de la tabla.
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblDat.getTableHeader().setReorderingAllowed(false);
        
	//Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        //objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_BUT_ANEXO);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
         ZafMouMotAda objMouMotAda=new ZafMouMotAda();
         tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_TIPO).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_COD_RUB).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_NOM_RUB).setPreferredWidth(180);
        tcmAux.getColumn(INT_TBL_SEL_ACT).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_AUX1).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX2).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX3).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX4).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX5).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX6).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX7).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX8).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX9).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_AUX10).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_SUBTOTAL).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_PORC).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_VAL_TOT).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_BUT_ANEXO).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_EST).setPreferredWidth(20);

        /* Aqui se agrega las columnas que van a ser ocultas*/
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_COD_RUB);
        arlColHid.add(""+INT_TBL_EST);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;
        
        
        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT_ANEXO).setCellRenderer(objTblCelRenBut);

            objTblCelRenBut.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenBut.getColumnRender())
                    {

                       case INT_TBL_BUT_ANEXO:
                           System.out.println("sucede esto: " + objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_SUBTOTAL).toString());
                           
                           if(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_SUBTOTAL).toString().compareTo("4")==0
                                   || objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_COD_RUB).toString().compareTo("24")==0){
                               objTblCelRenBut.setText("");
                           }else{
                               
                               if(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_SUBTOTAL).toString())==0){
                                   objTblCelRenBut.setText("");
                               }else 
                                   if(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_SUBTOTAL).toString().compareTo("")==0){

                                   }else{
                                       objTblCelRenBut.setText("...");
                                   }
                           }
                      break;
                    }
                }
            });

//             //Configurar JTable: Editor de celdas.
//            objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
//            tcmAux.getColumn(INT_TBLD_BUTGUIA).setCellEditor(objTblCelEdiButGenDG);
//            tcmAux.getColumn(INT_TBLD_BUTCONF).setCellEditor(objTblCelEdiButGenDG);
//            tcmAux.getColumn(INT_TBLD_BUTOD).setCellEditor(objTblCelEdiButGenDG);
//            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                int intFilSel, intColSel;
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    intFilSel=tblDat.getSelectedRow();
//                    intColSel=tblDat.getSelectedColumn();
//                    if (intFilSel!=-1)
//                    {
//                        switch (intColSel)
//                        {
//                          case INT_TBLD_BUTOD:
//
//                         break;
//
//
//                         case INT_TBLD_BUTCONF:
//
//                             if(objTblModDT.getValueAt(intFilSel, INT_TBLD_STCONPEN).toString().equals("true"))
//                                  objTblCelEdiButGenDG.setCancelarEdicion(true);
//
//
//                         break;
//
//                          case INT_TBLD_BUTGUIA:
//
//                              if(objTblModDT.getValueAt(intFilSel, INT_TBLD_STCONPEN).toString().equals("true"))
//                                  objTblCelEdiButGenDG.setCancelarEdicion(true);
//
//
//                         break;
//
//                       }
//                     }
//                    }
//                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    intColSel=tblDat.getSelectedColumn();
//                    if (intFilSel!=-1)
//                    {
//                        switch (intColSel)
//                        {
//
//                         case INT_TBLD_BUTOD:
//
//                             llamarPantGuia( objTblModDT.getValueAt(intFilSel, INT_TBLD_CODEMP).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODLOC).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODTID).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODDOC).toString()
//                                 );
//                             
//                         break;
//
//
//                          case INT_TBLD_BUTGUIA:
//
//                           if(objTblModDT.getValueAt(intFilSel, INT_TBLD_STCONPEN).toString().equals("false")){
//
//                             mostrarGuiasRemision( objTblModDT.getValueAt(intFilSel, INT_TBLD_CODEMP).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODLOC).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODTID).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODDOC).toString()
//                                 );
//                              }
//                           break;
//
//                           case INT_TBLD_BUTCONF:
//
//                            if(objTblModDT.getValueAt(intFilSel, INT_TBLD_STCONPEN).toString().equals("false")){
//
//                              llamarVtaConf( objTblModDT.getValueAt(intFilSel, INT_TBLD_CODEMP).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODLOC).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODTID).toString()
//                                    ,objTblModDT.getValueAt(intFilSel, INT_TBLD_CODDOC).toString()
//                                 );
//                               }
//                            
//                           break;
//
//
//                        }
//                }}
//           });

//        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
//            tcmAux.getColumn(INT_TBL_BUT_ANEXO).setCellRenderer(zafTblDocCelRenBut);
            
            objRecHum15_01=new ZafRecHum15_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BUT_ANEXO).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {

                          case INT_TBL_BUT_ANEXO:

                              if(objTblMod.getValueAt(intFilSel, INT_TBL_SUBTOTAL).toString().equals("")){
                                  if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().compareTo("4")==0){
                                      if(objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_SUBTOTAL).toString())==0){
                                      
                                        objTblCelEdiButGen.setCancelarEdicion(true);
                                      }else{
                                          objTblCelEdiButGen.setCancelarEdicion(false);
                                      }
                                          
                                  }else{
                                      objTblCelEdiButGen.setCancelarEdicion(false);
                                  }
                                  
                              }else{
                                  objTblCelEdiButGen.setCancelarEdicion(false);
                              }
                         break;

                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {

                        System.out.println("rubro clickeados: " + objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB));

                        if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("1")==0){
//                            RecursosHumanos.ZafRecHum15.ZafRecHum15_01 obj1 = new  RecursosHumanos.ZafRecHum15.ZafRecHum15_01(javax.swing.JOptionPane.getFrameForComponent(ZafRecHum15.this), true ,objZafParSis );
////                            this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
//                            obj1.show();
                            if(objTblMod.getValueAt(intFilSel, INT_TBL_SUBTOTAL).toString().compareTo("")!=0){
                                objRecHum15_01.setParDlg(objZafParSis,ZafRecHum15.this, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),
                                    Integer.valueOf(txtCodTra.getText()), Integer.valueOf(cboPerAAAA.getSelectedItem().toString()), 
                                    cboPerMes.getSelectedIndex(),
                                    cboPer.getSelectedIndex(),
                                    1, Double.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_SUBTOTAL).toString())
                                    );
                                objRecHum15_01.cargarReg(1);
                                objRecHum15_01.show();
                            }
                        }
                        
                        if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("2")==0){
                            
                            objRecHum15_01.setParDlg(objZafParSis,ZafRecHum15.this, objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),
                                    Integer.valueOf(txtCodTra.getText()), Integer.valueOf(cboPerAAAA.getSelectedItem().toString()), 
                                    cboPerMes.getSelectedIndex(),
                                    cboPer.getSelectedIndex(),
                                    2, Double.valueOf(objTblMod.getValueAt(intFilSel, INT_TBL_SUBTOTAL).toString())
                                    );
                            objRecHum15_01.cargarReg(2);
                            objRecHum15_01.show();
                            
                        }else 
                            if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("8")!=0 && 
                                objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("5")!=0){
                            
                            if(objUti.isNumero(objTblMod.getValueAt(intFilSel, INT_TBL_AUX1).toString())){
                                
                                if(objTblMod.getValueAt(intFilSel, INT_TBL_AUX1).toString().compareTo("")!=0){
                                
                                    if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("2")==0 
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("6")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("7")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("9")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("10")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("11")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("12")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("13")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("14")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("15")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("17")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("18")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("20")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("27")==0
                                            || objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("28")==0
                                            ){
                                        
                                                llamarPantIngEgrPrg(String.valueOf(objZafParSis.getCodigoEmpresa()),
                                                    objTblMod.getValueAt(intFilSel, INT_TBL_AUX1).toString(),
                                                    txtCodTra.getText().toString()
                                                    );
                                    }else if (objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("3")==0){
                                        if(objTblMod.getValueAt(intFilSel, INT_TBL_AUX2).toString().compareTo("")==0){
                                            llamarPantIngEgrPrg(String.valueOf(objZafParSis.getCodigoEmpresa()),
                                                    objTblMod.getValueAt(intFilSel, INT_TBL_AUX1).toString(),
                                                    txtCodTra.getText().toString()
                                                    );
                                        }else{
                                            llamarPantSolHSE( objTblMod.getValueAt(intFilSel, INT_TBL_AUX1).toString()
                                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_AUX2).toString()
                                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_AUX3).toString()
                                                    ,objTblMod.getValueAt(intFilSel, INT_TBL_AUX4).toString()
                                            );
                                        }
                                    }else if (objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("1")==0 || 
                                                objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("16")==0 || 
                                                objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("21")==0){
                                        int intOp=0;
                                        if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("1")==0){
                                            intOp=1;
                                        }else if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("16")==0){
                                            intOp=2;
                                        }else if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("21")==0){
                                            intOp=3;
                                        }
                                        if(objUti.isNumero(objTblMod.getValueAt(intFilSel, INT_TBL_AUX7).toString())){
                                            llamarPantMarc(txtCodEmp.getText().toString(),
                                                    txtCodTra.getText().toString(), 
                                                    strFecDes,
                                                    strFecHas , intOp);
                                        }
                                    }
                                }
                            }
                            else{
                                
                                if (
                                        objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("16")==0 || 
                                        objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("21")==0){
                                
                                    int intOp=0;
                                    
                                    if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("1")==0){
                                        intOp=1;
                                    }else if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("16")==0){
                                        intOp=2;
                                    }else if(objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("21")==0){
                                        intOp=3;
                                    }
                                    
                                    if(objUti.isNumero(objTblMod.getValueAt(intFilSel, INT_TBL_SUBTOTAL).toString())){
                                        
                                        llamarPantMarc(txtCodEmp.getText().toString(),
                                                    txtCodTra.getText().toString(), 
                                                    strFecDes,
                                                    strFecHas, intOp);
                                    }
                                }
                                
//                                if(objUti.isNumero(objTblMod.getValueAt(intFilSel, INT_TBL_AUX1).toString())){
//                                    
//                                    if(objUti.isNumero(objTblMod.getValueAt(intFilSel, INT_TBL_AUX7).toString())){
//                                        
//                                        
//                                        if(objTblMod.getValueAt(intFilSel, INT_TBL_AUX7).toString().compareTo("")!=0){
//                                    
////                                    if(objTblMod.getValueAt(intFilSel, INT_TBL_VAL_TOT).toString().trim().compareTo("")!=0 && 
////                                            objTblMod.getValueAt(intFilSel, INT_TBL_VAL_TOT).toString().trim().compareTo("0.0")!=0){
//                                        
//                                            if (
//                                                objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("16")==0 || 
//                                                objTblMod.getValueAt(intFilSel, INT_TBL_COD_RUB).toString().trim().compareTo("21")==0){
//                                        
//                                                llamarPantMarc(txtCodEmp.getText().toString(),
//                                                    txtCodTra.getText().toString(), 
//                                                    strFecDes,
//                                                    strFecHas);
//                                            }
//                                        }
//                                    }
//                                    
//                                    
//                                    
//                                }
                            }
                        }
                }}
           });

        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
////        objTblCelRenLbl.setBackground(colFonCol);
//        tcmAux.getColumn(INT_TBL_AUX1).setCellRenderer(objTblCelRenLbl);
//        tcmAux.getColumn(INT_TBL_AUX2).setCellRenderer(objTblCelRenLbl);
//        tcmAux.getColumn(INT_TBL_AUX3).setCellRenderer(objTblCelRenLbl);
//        tcmAux.getColumn(INT_TBL_AUX4).setCellRenderer(objTblCelRenLbl);
//        tcmAux.getColumn(INT_TBL_AUX5).setCellRenderer(objTblCelRenLbl);
//        tcmAux.getColumn(INT_TBL_AUX6).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;
        
      
        
            objTblCelRenLblCol = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLblCol.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblCol.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblCol.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblCol2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLblCol2.setHorizontalAlignment(javax.swing.JLabel.LEFT);

            tcmAux.getColumn(INT_TBL_TIPO).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_NOM_RUB).setCellRenderer(objTblCelRenLblCol2);
            tcmAux.getColumn(INT_TBL_SEL_ACT).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_AUX7).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_AUX8).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_AUX9).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_AUX10).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_SUBTOTAL).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_PORC).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_VAL_TOT).setCellRenderer(objTblCelRenLblCol);
            
            
            objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell=objTblCelRenLblCol.getRowRender();
                String str=tblDat.getValueAt(intCell, INT_TBL_EST).toString();

                if(str.equals("S")){
                    
                    objTblCelRenLblCol.setBackground(java.awt.Color.WHITE);
                    objTblCelRenLblCol2.setBackground(java.awt.Color.WHITE);
                    
                    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
                    tcmAux.getColumn(INT_TBL_AUX1).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX2).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX3).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX4).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX5).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX6).setCellRenderer(objTblCelRenLblCol2);
                    
                }else {
                    objTblCelRenLblCol.setBackground(colFonCol);
                    objTblCelRenLblCol2.setBackground(colFonCol);
                    
                    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
                    tcmAux.getColumn(INT_TBL_AUX1).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX1).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX2).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX3).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX4).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX5).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX6).setCellRenderer(objTblCelRenLblCol2);
                    
                }
            }
            public void afterRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell=objTblCelRenLbl.getRowRender();

                String str=tblDat.getValueAt(intCell, INT_TBL_EST).toString();

                if(str.equals("S")){
                    objTblCelRenLblCol.setBackground(java.awt.Color.WHITE);
                    objTblCelRenLblCol2.setBackground(java.awt.Color.WHITE);
                    
                    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
                    tcmAux.getColumn(INT_TBL_AUX1).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX2).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX3).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX4).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX5).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX6).setCellRenderer(objTblCelRenLblCol2);
                    
                }else {
                    
                    objTblCelRenLblCol.setBackground(colFonCol);
                    objTblCelRenLblCol2.setBackground(colFonCol);
                    
                    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
                    tcmAux.getColumn(INT_TBL_AUX1).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX2).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX3).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX4).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX5).setCellRenderer(objTblCelRenLblCol2);
                    tcmAux.getColumn(INT_TBL_AUX6).setCellRenderer(objTblCelRenLblCol2);
                   
                }
            }
        });
            
            
            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new ZafTblCelRenChk();
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_SEL_ACT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_SEL_ACT).setCellEditor(objTblCelEdiChk);
        
        //Configurar JTable: Establecer el ordenamiento en la tabla.
        //objTblOrd=new ZafTblOrd(tblDat);
                
        //Configurar JTable: Editor de búsqueda.
        objTblBus=new ZafTblBus(tblDat);
        
        int intCol[]={INT_TBL_VAL_TOT};
        
        objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
        objTblTot.setValueAt("Total:", 0, 1);
        
        tcmAux=null;
        setEditable(true);

        blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

private void llamarPantMarc(String strCodEmp, String strCoTra, String strFecDes, String strFecHas, int intOp){
        RecursosHumanos.ZafRecHum09.ZafRecHum09 obj1 = new  RecursosHumanos.ZafRecHum09.ZafRecHum09(objZafParSis, strCodEmp, strCoTra, strFecDes, strFecHas, intOp );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
    }

private void llamarPantIngEgrPrg(String strCodEmp, String strCodEgr, String strCodTra){
        RecursosHumanos.ZafRecHum34.ZafRecHum34 obj1 = new  RecursosHumanos.ZafRecHum34.ZafRecHum34(objZafParSis, strCodEmp, strCodEgr, strCodTra );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
    }
private void llamarPantSolHSE(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc ){
        RecursosHumanos.ZafRecHum27.ZafRecHum27 obj1 = new  RecursosHumanos.ZafRecHum27.ZafRecHum27(objZafParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
    }


public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}


 /*private class ButRecDoc extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButRecDoc(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {

        ventRecChq(intNumFil);

    }
 }}*/

 /*private void ventRecChq(int intNumFil){
  String strCodEmp="",strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
  try{

         strCodEmp=tblDat.getValueAt(intNumFil, INT_TBL_CODEMP).toString();
         strCodLoc=tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString();
         strCodTipDoc=tblDat.getValueAt(intNumFil, INT_TBL_COTIPDOC).toString();
         strCodDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODDOC).toString();
         strCodReg=tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString();

         CxC.ZafCxC11.ZafCxC11 obj1 = new CxC.ZafCxC11.ZafCxC11(objZafParSis,  new Integer(Integer.parseInt(strCodEmp)), new Integer(Integer.parseInt(strCodLoc)), new Integer(Integer.parseInt(strCodTipDoc)), new Integer(Integer.parseInt(strCodDoc)), strCodReg, 1739 );
         this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
         obj1.show();

   }catch(Exception evt){ objUti.mostrarMsgErr_F1(this, evt); }
 }*/

  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabGen = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        cboPer = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jChkNovFuePer = new javax.swing.JCheckBox();
        jChkNovPer = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()
        ;
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panCab = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtDesde = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtHas = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCerr = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Período:"); // NOI18N
        panFil.add(jLabel7);
        jLabel7.setBounds(10, 30, 110, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panFil.add(cboPerAAAA);
        cboPerAAAA.setBounds(110, 30, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panFil.add(cboPerMes);
        cboPerMes.setBounds(180, 30, 100, 20);

        cboPer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Período", "Primera quincena", "Fin de mes" }));
        panFil.add(cboPer);
        cboPer.setBounds(280, 30, 110, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(10, 60, 100, 20);

        txtCodEmp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(110, 60, 60, 20);

        txtNomEmp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(170, 60, 380, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(550, 60, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(10, 80, 100, 20);

        txtCodTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(110, 80, 60, 20);

        txtNomTra.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(170, 80, 380, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(550, 80, 20, 20);

        jChkNovFuePer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkNovFuePer.setText("Novedades que están fuera del  período");
        jChkNovFuePer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jChkNovFuePerMouseClicked(evt);
            }
        });
        jChkNovFuePer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkNovFuePerActionPerformed(evt);
            }
        });
        panFil.add(jChkNovFuePer);
        jChkNovFuePer.setBounds(10, 120, 420, 22);

        jChkNovPer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jChkNovPer.setSelected(true);
        jChkNovPer.setText("Novedades del  período");
        jChkNovPer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jChkNovPerMouseClicked(evt);
            }
        });
        panFil.add(jChkNovPer);
        jChkNovPer.setBounds(10, 100, 420, 22);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        jPanel1.add(spnTot, java.awt.BorderLayout.SOUTH);

        panCab.setPreferredSize(new java.awt.Dimension(0, 60));
        panCab.setLayout(null);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel8.setText("Desde:"); // NOI18N
        panCab.add(jLabel8);
        jLabel8.setBounds(10, 25, 50, 20);

        txtDesde.setEditable(false);
        txtDesde.setBackground(objZafParSis.getColorCamposSistema());
        txtDesde.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtDesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesdeActionPerformed(evt);
            }
        });
        txtDesde.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesdeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesdeFocusLost(evt);
            }
        });
        panCab.add(txtDesde);
        txtDesde.setBounds(50, 25, 110, 20);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel9.setText("Hasta:"); // NOI18N
        panCab.add(jLabel9);
        jLabel9.setBounds(170, 25, 50, 20);

        txtHas.setEditable(false);
        txtHas.setBackground(objZafParSis.getColorCamposSistema());
        txtHas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtHas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHasActionPerformed(evt);
            }
        });
        txtHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHasFocusLost(evt);
            }
        });
        panCab.add(txtHas);
        txtHas.setBounds(210, 25, 110, 20);

        jPanel1.add(panCab, java.awt.BorderLayout.PAGE_START);

        tabGen.addTab("Reporte", jPanel1);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCerr.setText("Cerrar");
        butCerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butCerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrActionPerformed(evt);
            }
        });
        jPanel5.add(butCerr);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDocActionPerformed(java.awt.event.ActionEvent evt) {
//        txtFec.transferFocus();
    } 
    
    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
        // TODO add your handling code here:
         exitForm();
}//GEN-LAST:event_butCerrActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:

            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
    }//GEN-LAST:event_butConActionPerformed

    public void BuscarEmp(String campo,String strBusqueda,int tipo){
        
        vcoEmp.setTitle("Listado de Empresas");
        if(vcoEmp.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
  }}}
    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
  }}}
    
//    public void BuscarOfi(String campo,String strBusqueda,int tipo){
//        objVenConOfi.setTitle("Listado de Oficinas");
//        if(objVenConOfi.buscar(campo, strBusqueda )) {
//            txtCodOfi.setText(objVenConOfi.getValueAt(1));
//            txtNomOfi.setText(objVenConOfi.getValueAt(2));
//        }else{
//            objVenConOfi.setCampoBusqueda(tipo);
//            objVenConOfi.cargarDatos();
//            objVenConOfi.show();
//            if (objVenConOfi.getSelectedButton()==objVenConOfi.INT_BUT_ACE) {
//                txtCodOfi.setText(objVenConOfi.getValueAt(1));
//                txtNomOfi.setText(objVenConOfi.getValueAt(2));
//        }else{
//                txtCodOfi.setText(strCodOfi);
//                txtNomOfi.setText(strNomOfi);
//  }}}
    
    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
//        optFil.setSelected(true);
//        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (txtNomEmp.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
            {
                if (txtNomEmp.getText().equals(""))
                {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                }
                else
                {
                    mostrarVenConEmp(2);
                }
            }
            else
            txtNomEmp.setText(strNomEmp);
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
            txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra=txtCodTra.getText();
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void jChkNovFuePerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkNovFuePerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jChkNovFuePerActionPerformed

    private void txtDesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesdeActionPerformed
        txtDesde.transferFocus();
    }//GEN-LAST:event_txtDesdeActionPerformed

    private void txtDesdeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesdeFocusGained
//        strDesCorTipDoc=txtDesde.getText();
        txtDesde.selectAll();
    }//GEN-LAST:event_txtDesdeFocusGained

    private void txtDesdeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesdeFocusLost

//        if (txtDesde.isEditable())
//        {
//            //Validar el contenido de la celda sólo si ha cambiado.
//            if (!txtDesde.getText().equalsIgnoreCase(strDesCorTipDoc))
//            {
//                if (txtDesde.getText().equals(""))
//                {
//                    txtCodTipDoc.setText("");
//                    txtDesLarTipDoc.setText("");
//                }
//                else
//                {
//                    mostrarVenConTipDoc(1);
//                }
//            }
//            else
//            txtDesde.setText(strDesCorTipDoc);
//        }
    }//GEN-LAST:event_txtDesdeFocusLost

    private void txtHasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHasActionPerformed

    private void txtHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHasFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHasFocusGained

    private void txtHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHasFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHasFocusLost

    private void jChkNovPerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jChkNovPerMouseClicked
        // TODO add your handling code here:
        jChkNovFuePer.setSelected(false);
    }//GEN-LAST:event_jChkNovPerMouseClicked

    private void jChkNovFuePerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jChkNovFuePerMouseClicked
        // TODO add your handling code here:
        jChkNovPer.setSelected(false);
    }//GEN-LAST:event_jChkNovFuePerMouseClicked

private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

        if(validarCamObl()){
            if(setFechaDesFechaHas()){
                if(cargarDat()){
                    tabGen.setSelectedIndex(1);
                }
            }
            
        }else{
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
            }
        
        objThrGUI=null;
    }
}

    /**
     * Valida la obligatoriedad de los campos de la ventana
     * @return Retorna true si campos obligatorios están llenos y false si no lo están
     */
    private boolean validarCamObl(){
        String strNomCam = null;
        boolean blnOk = true;

        
//        if(!txtFec.isFecha()){
//            txtFec.requestFocus();
//            txtFec.selectAll();
//            MensajeValidaCampo("Fecha");
//            return false;
//        }
        
        if(cboPerAAAA.getSelectedItem().toString().compareTo("Año")==0){
            cboPerAAAA.requestFocus();
            cboPerAAAA.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Año</FONT> no ha sido seleccionado.<BR>Seleccione un año y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(cboPerMes.getSelectedItem().toString().compareTo("Mes")==0){
            cboPerMes.requestFocus();
            cboPerMes.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Mes</FONT> no ha sido seleccionado.<BR>Seleccione un mes y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(cboPer.getSelectedItem().toString().compareTo("Período")==0){
            cboPer.requestFocus();
            cboPer.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Período</FONT> no ha sido seleccionado.<BR>Seleccione un período y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(txtCodEmp.getText().length()==0){
            txtCodEmp.requestFocus();
            txtCodEmp.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Empresa</FONT> es obligatorio.<BR>Escriba o seleccione una empresa y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        if(txtCodTra.getText().length()==0){
            txtCodTra.requestFocus();
            txtCodTra.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Empleado</FONT> es obligatorio.<BR>Escriba o seleccione una empresa y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        return blnOk;
    }
    
    private void MensajeValidaCampo(String strNomCampo){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        obj.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public int obtenerCantidadRegistros(){
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="", sqlAux="";
        int intCantReg=0;
        try{
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
        
      if(conn!=null){
          
          stmLoc=conn.createStatement();
          java.util.Vector vecData = new java.util.Vector();

//          if(!(txtFec.getText().equals(""))){
//              String strFec = txtFec.getText().replace("/", "");
//              String strDD = strFec.substring(0, 2);
//              String strMM = strFec.substring(3, 4);
//              String strYYY = strFec.substring(4, 8);
//              String strFecha = strYYY+"-"+strMM+"-"+strDD;
//              sqlAux+=" WHERE a.fe_dia = '"+ strFecha + "' ";
//          }
          
//          if(!(txtCodOfi.getText().equals(""))){
//              sqlAux+=" AND c.co_ofi="+txtCodOfi.getText()+" ";
//          }

          strSql = "select a.co_tra, (b.tx_nom || ' ' || b.tx_ape) as nomCom from tbm_cabconasitra a " +
                   "inner join tbm_tra b on (a.co_tra=b.co_tra) "+
                   "inner join tbm_traemp c on (a.co_tra=c.co_tra and c.st_recAlm like 'S') "+sqlAux + " and a.ho_ent is not null " + 
                   "order by b.co_tra";

           System.out.println(strSql);
           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){
               intCantReg++;
           }
           
           rstLoc.close();
           rstLoc=null;

           stmLoc.close();
           stmLoc=null;
           conn.close();
           conn=null;
      }
        }catch(java.sql.SQLException Evt) { Evt.printStackTrace(); objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception Evt) { Evt.printStackTrace();  objUti.mostrarMsgErr_F1(this, Evt);  }
        intCanReg=intCantReg;
        return intCanReg;
    }
    
    
    
 private boolean setFechaDesFechaHas(){
     boolean blnRes=false;
     java.sql.Connection conn;
     java.sql.Statement stmLoc;
     java.sql.ResultSet rstLoc;
     String strSql="", sqlAux="";
     try{
         conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
         if(conn!=null){
            stmLoc=conn.createStatement();
            strSql="";
            if(cboPer.getSelectedItem().toString().compareTo("Primera quincena")==0){
                strSql+="select * from tbm_feccorrolpag";
                strSql+=" where co_emp="+txtCodEmp.getText();
                strSql+=" and ne_ani="+cboPerAAAA.getSelectedItem().toString();
                strSql+=" and ne_mes="+cboPerMes.getSelectedIndex();
                strSql+=" and ne_per="+cboPer.getSelectedIndex();
                
                System.out.println(strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){

                    Date date = rstLoc.getDate("fe_des");
                    txtDesde.setText(objUti.formatearFecha(date, "dd/MMM/yyyy"));
                    strFecDes=objUti.formatearFecha(date, "dd/MM/yyyy");
                    date = rstLoc.getDate("fe_has");
                    txtHas.setText(objUti.formatearFecha(date, "dd/MMM/yyyy"));
                    strFecHas=objUti.formatearFecha(date, "dd/MM/yyyy");
                    blnRes=true;
                }
                
            }else{
                strSql+="select fe_des from tbm_feccorrolpag";
                strSql+=" where co_emp="+txtCodEmp.getText();
                strSql+=" and ne_ani="+cboPerAAAA.getSelectedItem().toString();
                strSql+=" and ne_mes="+cboPerMes.getSelectedIndex();
                strSql+=" and ne_per=1";
                System.out.println(strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){

                    Date date = rstLoc.getDate("fe_des");
                    txtDesde.setText(objUti.formatearFecha(date, "dd/MMM/yyyy"));
                    strFecDes=objUti.formatearFecha(date, "dd/MM/yyyy");
                }
                
                strSql="";
                strSql+="select fe_has from tbm_feccorrolpag";
                strSql+=" where co_emp="+txtCodEmp.getText();
                strSql+=" and ne_ani="+cboPerAAAA.getSelectedItem().toString();
                strSql+=" and ne_mes="+cboPerMes.getSelectedIndex();
                strSql+=" and ne_per=2";
                System.out.println(strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){

                    Date date = rstLoc.getDate("fe_has");
                    txtHas.setText(objUti.formatearFecha(date, "dd/MMM/yyyy"));
                    strFecHas=objUti.formatearFecha(date, "dd/MM/yyyy");
                    blnRes=true;
                }
            }
        }
     }catch(java.sql.SQLException Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     System.gc();
     return blnRes;
 }   
    
/**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="", sqlAux="";
  
  java.util.Vector vecReg = null;
  java.util.Vector vecRegTotalRubro = null;
  java.util.Vector vecRegDetalleRubro = null;
  
   try{
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
          
          stmLoc=conn.createStatement();
          java.util.Vector vecData = new java.util.Vector();

          String strPer="";
          if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                strPer="1";
            }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
                strPer="2";
            }
          
          String strAño=cboPerAAAA.getSelectedItem().toString();
          String strMes=String.valueOf(cboPerMes.getSelectedIndex());
          String strCoTra=txtCodTra.getText().toString().trim();
          String strCoEmp=txtCodEmp.getText().toString().trim();
          
          String[] strFec = null;
          String strFecHas  = "";
          if(jChkNovFuePer.isSelected()){
              
              System.out.println(objUti.formatearFecha(txtHas.getText(), "dd/MMM/yyyy", "yyyy-MM-dd"));
              strFecHas  = objUti.formatearFecha(txtHas.getText(), "dd/MMM/yyyy", "yyyy-MM-dd");
              strFec = strFecHas.split("-");
              java.util.Calendar CalVal =  java.util.Calendar.getInstance();
              CalVal.set(java.util.Calendar.YEAR,Integer.parseInt(strFec[0]));
              CalVal.set(java.util.Calendar.MONTH,Integer.parseInt(strFec[1])-1);
              String strDiaMaxMes = String.valueOf(CalVal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
              strFecHas = strFec[0] + "-" + strFec[1] + "-" + strDiaMaxMes;
              txtHas.setText(objUti.formatearFecha(strFecHas.toString(), "yyyy-MM-dd", "dd/MMM/yyyy"));
              this.strFecHas=objUti.formatearFecha(txtHas.getText(), "dd/MMM/yyyy", "dd/MM/yyyy");
              strFecHas = objUti.codificar(strFecHas);
          }else{
              
              if(jChkNovPer.isSelected()){
                  
                  if(cboPer.getSelectedIndex()==1){
                      strFecHas=objUti.codificar(objUti.formatearFecha(txtHas.getText(), "dd/MMM/yyyy", "yyyy-MM-dd"));
//                      strFec = strFecHas.split("-");
//                      strFecHas = strFec[0] + "-" + strFec[1] + "-" + strFec[2];
                  }else{
                      strFecHas = "select fe_has as fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2";
                  }
              }
          }
          String strCodGrp="";
          if(objZafParSis.getCodigoMenu()==2862){
              strCodGrp= " in (1) ";
          }
          else if(objZafParSis.getCodigoMenu()==3485){
              strCodGrp=" in (2,3) ";
          }
          
          strSql+="select * from (";
          strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
          strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
          strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";

            strSql+=" '' as butAne, c.fe_ing,";
            strSql+=" (";
            strSql+=" select sum(nd_valrub) from tbm_ingegrmentra  a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub and c.st_reg='A'";
            strSql+=" and c.fe_ing BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp=a.co_emp and ne_ani = "+strAño+" and ne_mes = "+strMes+" and ne_per=1) ";
            strSql+=" and (select fe_has as fe_des from tbm_feccorrolpag where co_emp=a.co_emp and ne_ani = "+strAño+" and ne_mes = "+strMes+" and ne_per=2)";
            strSql+=" )";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and nd_valrub is not null";
            strSql+=" group by a.co_emp, a.co_tra, a.ne_ani, a.ne_mes, a.ne_per";
            strSql+=" )  as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub  and c.st_reg='A'";
            strSql+=" and c.fe_ing BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp=a.co_emp and ne_ani = "+strAño+" and ne_mes = "+strMes+" and ne_per=1) ";
            //strSql+=" and (select fe_has as fe_des from tbm_feccorrolpag where co_emp=a.co_emp and ne_ani = "+strAño+" and ne_mes = "+strMes+" and ne_per=2)";
            strSql+=" and ("+strFecHas+")  ";
            strSql+=" )";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=1";



            strSql+=" union";

            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            //strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub)";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=2";


            strSql+=" union";

            String strHoSupExt="";
            
            strSql+=" (";

                    strHoSupExt+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
                    //strHoSupExt+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
                    strHoSupExt+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
                    strHoSupExt+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
                    strHoSupExt+=" nd_valrub as subtotal,";
                    strHoSupExt+=" nd_valrub as total, ";
                    strHoSupExt+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
                    strHoSupExt+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
                    strHoSupExt+=" '' as butAne, c.fe_ing,null as totalRecibir";
                    strHoSupExt+=" from tbm_ingegrmentra a";
                    strHoSupExt+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
                    strHoSupExt+=" inner join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub  and c.tx_tipcuo='M'";
                    strHoSupExt+=" and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
                    //strHoSupExt+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
                    strHoSupExt+=" and ("+strFecHas+")  ";
                    strHoSupExt+=" )";
                    strHoSupExt+=" where a.co_emp = " + strCoEmp;
                    strHoSupExt+=" and a.ne_ani = "+ strAño;
                    strHoSupExt+=" and a.ne_mes = " + strMes;
                    strHoSupExt+=" and a.co_tra = " + strCoTra;
                    strHoSupExt+=" and ne_per = " + strPer;
                    strHoSupExt+=" and a.co_rub=3";

                    strHoSupExt+=" UNION";

                    strHoSupExt+=" select p.co_emp, p.co_tra, p.ne_ani, p.ne_mes , p.co_rub, j.tx_tiprub ,'' as tx_tipegrprg ,a.co_doc,j.tx_nom,"; //false as blnSelPer , ";
                    strHoSupExt+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
                    strHoSupExt+=" case (a.co_emp is not null) when true then a.co_emp::varchar else '' end as aux1, ";
                    strHoSupExt+=" case (a.co_loc is not null) when true then a.co_loc::varchar else '' end as aux2, ";
                    strHoSupExt+=" case (a.co_tipdoc is not null) when true then a.co_tipdoc::varchar else '' end as aux2, ";
                    strHoSupExt+=" case (a.co_doc is not null) when true then a.co_doc::varchar else '' end as aux4, ";
                    strHoSupExt+=" a.fe_doc::varchar as aux5, ";
                    strHoSupExt+=" a.fe_aut::varchar as aux6, ";

                    strHoSupExt+=" (";

                    strHoSupExt+=" (extract(hours from (case ((e.ho_sal-ho_autdes)<(ho_authas-ho_autdes)) when true then (e.ho_sal-ho_autdes) else (ho_authas-ho_autdes) end)  )*60) +";
                    strHoSupExt+=" (extract(minutes from (case ((e.ho_sal-ho_autdes)<(ho_authas-ho_autdes)) when true then (e.ho_sal-ho_autdes) else (ho_authas-ho_autdes) end)  ))+";
                    strHoSupExt+=" (extract(seconds from (case ((e.ho_sal-ho_autdes)<(ho_authas-ho_autdes)) when true then (e.ho_sal-ho_autdes) else (ho_authas-ho_autdes) end)  )*0.0166666667)";

                    strHoSupExt+=" ) * ( ((((h.nd_valrub/30)/8)/60) * ((nd_porpag+100)/100)) ) as subtotal,";
                    strHoSupExt+=" null as total, ";
                    strHoSupExt+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
                    strHoSupExt+=" case (p.co_rub in (1,2,6,7,8,24,25,26)) when true then p.nd_valrub else (p.nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
                    strHoSupExt+=" '' as butAne, a.fe_aut,0 as totalRecibir";


                    strHoSupExt+=" from tbm_cabsolhorsupext a";
                    strHoSupExt+=" inner join tbm_detsolhorsupext b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)";
                    strHoSupExt+=" inner join tbm_traemp c on (b.co_emppag=c.co_emp and b.co_tra=c.co_tra)";
                    strHoSupExt+=" inner join tbm_tra d on (c.co_tra=d.co_tra)";
                    strHoSupExt+=" left outer join tbm_cabconasitra e on (e.co_tra=d.co_tra and e.fe_dia=a.fe_sol)";
                    strHoSupExt+=" inner join tbm_callab f on (e.fe_dia=f.fe_dia and f.co_hor=c.co_hor)";
                    strHoSupExt+=" inner join tbm_porPagHorSupExt g on (g.tx_tiphorpag in (";
                    strHoSupExt+=" case (extract(DOW from e.fe_dia) in (1,2,3,4,5) and f.st_dialab='S' and  ";
                    strHoSupExt+=" (a.ho_autdes between ho_des and ho_has) and (a.ho_authas between ho_des and ho_has)";
                    strHoSupExt+=" ) when true then 'S' else 'E' end))";
                    strHoSupExt+=" inner join tbm_suetra h on (h.co_tra=c.co_tra and h.co_emp=c.co_emp and h.co_rub=1)";
                    strHoSupExt+=" inner join tbm_dethortra i on(c.co_hor=i.co_hor and i.ne_dia in(extract(dow from e.fe_dia)))";
                    strHoSupExt+=" inner join tbm_rubrolpag j on (j.co_rub=3)";
                    strHoSupExt+=" inner join tbm_ingegrmentra p on (p.co_emp="+strCoEmp+" and p.ne_mes="+strMes+" and p.ne_ani="+strAño+" and p.co_tra="+strCoTra+" and p.co_rub=3 and ne_per="+strPer+") ";

                    strHoSupExt+=" where fe_aut between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
                    //strHoSupExt+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
                    strHoSupExt+=" and ("+strFecHas+")  ";
                    strHoSupExt+=" and  b.co_tra not in (90)";
                    strHoSupExt+=" and (e.ho_ent is not null and e.ho_sal is not null)";
                    strHoSupExt+=" and a.st_reg='A'";
                    strHoSupExt+=" and a.st_aut='T'";

                    strHoSupExt+=" and (";

                    strHoSupExt+=" extract(hours from (case ((e.ho_sal-ho_autdes)<(ho_authas-ho_autdes)) when true then (e.ho_sal-ho_autdes) else (ho_authas-ho_autdes) end)  )*60 +";
                    strHoSupExt+=" extract(minutes from (case ((e.ho_sal-ho_autdes)<(ho_authas-ho_autdes)) when true then (e.ho_sal-ho_autdes) else (ho_authas-ho_autdes) end)  )+";
                    strHoSupExt+=" (extract(seconds from (case ((e.ho_sal-ho_autdes)<(ho_authas-ho_autdes)) when true then (e.ho_sal-ho_autdes) else (ho_authas-ho_autdes) end)  )/60)";

                    strHoSupExt+=" )>1.78";

                    strHoSupExt+=" and b.co_tra = " + strCoTra;
                    
            strSql+= " " + strHoSupExt;
            strSql+=" )";

            strSql+=" union";
            
            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (nd_valrub is not null) when true then (nd_valrub/d.nd_valalm)::varchar else '' end as aux1, ";
            strSql+=" d.nd_valalm::varchar as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub)";
            strSql+=" inner join tbm_traemp d on (d.co_tra=a.co_tra)";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+="and a.ne_mes = " + strMes;
            strSql+="and a.co_tra = " + strCoTra;
            strSql+="and ne_per = " + strPer;
            strSql+="and a.co_rub=4";
            

            strSql+=" union";

            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" (";
            strSql+=" select nd_valrub::varchar";
            strSql+=" from tbm_ingegrmentra";
            strSql+=" where co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and ne_mes = " + strMes;
            strSql+=" and co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and co_rub=1";

            strSql+=" ) as aux1,  ";

            strSql+=" (";

            strSql+=" select nd_valrub::varchar";
            strSql+=" from tbm_ingegrmentra";
            strSql+=" where co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and ne_mes = " + strMes;
            strSql+=" and co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and co_rub=2";

            strSql+=" ) as aux2,  ";

            strSql+=" '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub)";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=5";


            strSql+=" union";



            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            //strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub)";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=6 and co_grp " + strCodGrp;

            strSql+=" union";



            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            //strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub)";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=7 and co_grp " + strCodGrp;

            strSql+=" union";

            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";
            strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer , ";
            strSql+=" case (d.nd_apoperies is not null) when true then d.nd_apoperies::varchar else '' end as aux1, ";

            strSql+=" (";
            strSql+=" select nd_valrub::varchar";
            strSql+=" from tbm_ingegrmentra";
            strSql+=" where co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and ne_mes = " + strMes;
            strSql+=" and co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and co_rub=1";

            strSql+=" ) as aux2,  ";

            strSql+=" (";

            strSql+=" select nd_valrub::varchar";
            strSql+=" from tbm_ingegrmentra";
            strSql+=" where co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and ne_mes = " + strMes;
            strSql+=" and co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and co_rub=2";

            strSql+=" ) as aux3,  ";
            strSql+=" (";

            strSql+=" select nd_valrub::varchar";
            strSql+=" from tbm_ingegrmentra";
            strSql+=" where co_emp = " + strCoEmp;
            strSql+=" and ne_ani = " + strAño;
            strSql+=" and ne_mes = " + strMes;
            strSql+=" and co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and co_rub=3";

            strSql+=" ) as aux4,";

            strSql+=" '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub)";
            strSql+=" inner join tbm_traemp d on (d.co_tra=a.co_tra)";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=8";

            strSql+=" union";


            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            //strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub and c.st_reg='A')";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=11";


            strSql+=" union";

////            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,"; //false as blnSelPer , ";
////            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
////            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
////            strSql+=" nd_valrub as subtotal,";
////            strSql+=" nd_valrub as total, ";
////            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
////            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
////            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
////            strSql+=" from tbm_ingegrmentra a";
////            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
////            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub ";
////            strSql+=" and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
////            //strSql+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
////            strSql+=" and ("+strFecHas+")  ";
////            strSql+=" )";
////            strSql+=" where a.co_emp = " + strCoEmp;
////            strSql+=" and a.ne_ani = " + strAño;
////            strSql+=" and a.ne_mes = " + strMes;
////            strSql+=" and a.co_tra = " + strCoTra;
////            strSql+=" and ne_per = " + strPer;
////            strSql+=" and a.co_rub=20";
////
////            strSql+=" union";

            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub ";
            strSql+=" and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
            //strSql+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
            strSql+=" and ("+strFecHas+")  ";
            strSql+=" )";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=22";

            strSql+=" union";

            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" nd_valrub as subtotal,";
            strSql+=" nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub ";
            strSql+=" and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
            //strSql+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
            strSql+=" and ("+strFecHas+")  ";
            strSql+=" )";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub=23 and co_grp " + strCodGrp;

            strSql+=" union";

            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,"; //false as blnSelPer , ";
            //strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
//            strSql+=" nd_valrub as subtotal,";
//            strSql+=" nd_valrub as total, ";
            strSql+=" case (a.co_rub in(12)) when true then  (nd_valegrprg*-1) else nd_valrub end as subtotal,";
            strSql+=" case (a.co_rub in(12)) when true then  (nd_valegrprg*-1) else nd_valrub end as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else ((case (a.co_rub in(12)) when true then  (nd_valegrprg*-1) else nd_valrub end) * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub ";
            strSql+=" and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
            //strSql+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
            strSql+=" and ("+strFecHas+")  ";
            strSql+=" )";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub in (9,10,12,13,17,19,24)";
           
            
            strSql+=" union ";
            
            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,"; //false as blnSelPer , ";
            //strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
//            strSql+=" nd_valrub as subtotal,";
//            strSql+=" nd_valrub as total, ";
            strSql+=" case (a.co_rub in(12)) when true then  (nd_valegrprg*-1) else nd_valrub end as subtotal,";
            strSql+=" case (a.co_rub in(12)) when true then  (nd_valegrprg*-1) else nd_valrub end as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else ((case (a.co_rub in(12)) when true then  (nd_valegrprg*-1) else nd_valrub end) * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub ";
            strSql+=" and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
            //strSql+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
            strSql+=" and ("+strFecHas+")  ";
            strSql+=" )";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub in (25,26,27,28)" + " and co_grp " + strCodGrp;
            
            strSql+=" union";
            
            
            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom, ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
//            strSql+=" nd_valrub as subtotal,";
//            strSql+=" nd_valrub as total, ";
            strSql+=" case (a.co_rub in(20)) when true then  (nd_valcuo*-1) else nd_valrub end as subtotal,";
            strSql+=" case (a.co_rub in(20)) when true then  (nd_valcuo*-1) else nd_valrub end as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else ((case (a.co_rub in(20)) when true then  (nd_valcuo*-1) else nd_valrub end) * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub and tx_tiprub='E')";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub )";
            strSql+=" inner join tbm_detingegrprgtra d on (d.co_emp=c.co_emp and d.co_egr=c.co_egr)";
            strSql+=" where EXTRACT(YEAR FROM fe_cuo) = " + strAño;
            strSql+=" and EXTRACT(MONTH FROM fe_cuo) = " + strMes;
            strSql+=" and tx_tipegrprg like 'D' and b.st_reg like 'A'  ";
            strSql+=" and a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" and a.co_rub in (14,15,18,20)";
            
            strSql+=" union";
            
//                select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom, false as blnSelPer , 
//                case (a.nd_valrub is not null) when true then (round((a.nd_valrub*-1/(d.nd_valrub/20/60/8)),0))::varchar else '' end as aux1, 
//                (d.nd_valrub/20/60/8)::varchar as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, 
//                a.nd_valrub as subtotal,
//                a.nd_valrub as total, 
//                case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, 
//                case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then a.nd_valrub else (a.nd_valrub * (nd_porrubpag)) end as nd_ValTot,
//                '' as butAne, c.fe_ing,null as totalRecibir
//                from tbm_ingegrmentra a
//                inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)
//                left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub 
//                and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=2012 and ne_mes=11 and ne_per=1)
//                and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=2012 and ne_mes=11 and ne_per=2)
//                )
//                inner join tbm_suetra d on (d.co_emp=a.co_emp and d.co_tra=a.co_tra and d.co_rub=1)
//                where a.co_emp=1
//                and a.ne_ani=2012
//                and a.ne_mes=11
//                and a.co_tra=21
//                and ne_per=2
//                and a.co_rub in (16)
            
            String strDescAtr="";
            
//            strDescAtr+=" (";
//
//            strDescAtr+=" select  p.co_emp, p.co_tra, ne_ani, ne_mes , t2.co_rub, t1.tx_tiprub ,t3.tx_tipegrprg ,t3.co_egr,t1.tx_nom,";//, false as blnSelPer,";
//            strDescAtr+=" case (nd_porrubpag>0.00001 and (case (t2.co_rub in (1,2,6,7,8,24,25,26)) when true then (case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/20)/8)/60))*-1 else null end) else (";
//            strDescAtr+=" (case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/20)/8)/60))*-1 else null end) * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
//            strDescAtr+=" case (sum(totalFavor) < sum(totalContra)) when true then (extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))::varchar else ''::varchar end as aux1,";
//            strDescAtr+=" (((t.nd_valrub/20)/8)/60)::varchar as aux2,";
//            strDescAtr+=" '' as aux3, '' as aux4, '' as aux5, '' as aux6,";
//            strDescAtr+=" case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/20)/8)/60))*-1 else null end as subtotal,";
//            strDescAtr+=" case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/20)/8)/60))*-1 else null end as total,";
//            strDescAtr+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje,";
//            strDescAtr+=" case (t2.co_rub in (1,2,6,7,8,24,25,26)) when true then (case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/20)/8)/60))*-1 else null end) else (";
//            strDescAtr+=" (case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/20)/8)/60))*-1 else null end) * (nd_porrubpag)) end as nd_ValTot,";
//            strDescAtr+=" '' as butAne, t3.fe_ing,null as totalRecibir";
//            strDescAtr+=" from ( ";
//
//            strDescAtr+=" select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, g.ho_ent as horaEntrada, g.ho_sal as horaSalida, g.ho_mingraent as minutosGrac, c.ho_ent, ";
//            strDescAtr+=" c.ho_sal, st_jusatr, fe_jusatr,  ";
//            strDescAtr+=" (case (g.ho_ent > c.ho_ent) when true then (g.ho_ent-c.ho_ent) else null end) as minutosAdelantadoEntrada, (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) as minutosAtrasadoEntrada, ";
//            strDescAtr+=" (case (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else null end) as minutosAntesSalida, ";
//            strDescAtr+=" (case (g.ho_sal< c.ho_sal) when true then (c.ho_sal-g.ho_sal) else null end) as minutosDespuesSalida, ";
//            strDescAtr+=" case (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))>((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then (c.ho_sal-g.ho_sal)-(c.ho_ent - g.ho_ent) else null end as totalFavor, ";
//            strDescAtr+=" case st_jusatr is null and (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))<((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then (c.ho_ent - g.ho_ent)-(c.ho_sal-g.ho_sal) else null end as totalContra , f.co_emp  FROM ( ";
//
//            strDescAtr+=" select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg, ";
//            strDescAtr+=" a.fe_jusatr  from tbm_cabconasitra a  ";
//            strDescAtr+=" inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A')c  ";
//            strDescAtr+=" inner join tbm_traemp f on (f.co_tra=c.co_tra)  ";
//            strDescAtr+=" left outer join tbm_dep d on d.co_dep=f.co_dep   ";
//            strDescAtr+=" inner join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))    ";
//            strDescAtr+=" WHERE c.fe_dia BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1) ";
//            //strDescAtr+=" and (strFecHas)  ";
//            strDescAtr+=" and ("+strFecHas+")  ";
//            strDescAtr+=" AND not(c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null) ";
//            strDescAtr+=" AND c.co_tra not in (4,6,8,19,24,27,30,31,32,33,37,39,41,42,45,46,80,81) ";
//
//            strDescAtr+=" UNION  ";
//
//            
//            
//            strDescAtr+=" select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, g.ho_ent as horaEntrada, g.ho_sal as horaSalida, g.ho_mingraent as minutosGrac, ";
//            strDescAtr+=" c.ho_ent, c.ho_sal, st_jusatr, fe_jusatr,  ";
//            strDescAtr+=" (case (g.ho_ent > c.ho_ent) when true then (g.ho_ent-c.ho_ent) else null end) as minutosAdelantadoEntrada, ";
//            strDescAtr+=" (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) as minutosAtrasadoEntrada, ";
//            strDescAtr+=" (case (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else null end) as minutosAntesSalida, ";
//            strDescAtr+=" (case (g.ho_sal < c.ho_sal) when true then (c.ho_sal-g.ho_sal) else null end) as minutosDespuesSalida, ";
//            strDescAtr+=" case (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))>((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then (c.ho_sal-g.ho_sal)-(c.ho_ent - g.ho_ent) else null end as totalFavor, ";
//            strDescAtr+=" case st_jusatr is null and (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))<((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then (c.ho_ent - g.ho_ent)-(c.ho_sal-g.ho_sal) else null end as totalContra , f.co_emp  FROM ( ";
//
//            strDescAtr+=" select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal, a.tx_obsjusfal,b.st_reg, ";
//            strDescAtr+=" a.fe_jusatr  from tbm_cabconasitra a  ";
//            strDescAtr+=" inner join tbm_tra b on (a.co_tra = b.co_tra and b.st_reg like 'A' and (a.ho_ent is not null or a.ho_sal is not null)))c  ";
//            strDescAtr+=" inner join tbm_traemp f on (f.co_tra=c.co_tra and f.st_horfij like 'N' )  ";
//            strDescAtr+=" left outer join tbm_dep d on d.co_dep=f.co_dep   ";
//            strDescAtr+=" left join tbm_dethortra g on(f.co_hor=g.co_hor and g.ne_dia in(extract(dow from c.fe_dia)))    ";
//            strDescAtr+=" WHERE c.fe_dia BETWEEN (select fe_des as fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1) ";
//            strDescAtr+=" and ("+strFecHas+")  ";
//            strDescAtr+=" AND not(c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null) ";
//            strDescAtr+=" AND c.co_tra not in (4,6,8,19,24,27,30,31,32,33,37,39,41,42,45,46,80,81) ";
//            strDescAtr+=" order by fe_dia asc,tx_ape, tx_nom ) p ";
//
//            strDescAtr+=" inner join tbm_suetra t on (p.co_emp=t.co_emp and p.co_tra=t.co_tra and t.co_rub = 1) ";
//            strDescAtr+=" inner join tbm_ingegrmentra t2 on (t2.co_emp=p.co_emp and t2.co_tra=p.co_tra and t2.ne_ani="+strAño+" and t2.ne_mes="+strMes+" and t2.ne_per="+strPer+" and t2.co_rub=16)";
//            strDescAtr+=" inner join tbm_rubrolpag t1 on (t1.co_rub=t2.co_rub)";
//            strDescAtr+=" left outer join tbm_cabingegrprgtra t3 on (t3.co_tra=t2.co_tra and t3.co_emp=t2.co_emp and t3.co_rub=t2.co_rub)";
//            strDescAtr+=" where p.co_tra = " + strCoTra;
//            strDescAtr+=" group by p.co_emp,p.co_tra, p.tx_ape || ' ' || p.tx_nom, t.nd_valrub , t2.ne_ani, t2.ne_mes, t2.ne_per,t2.co_rub, t2.nd_porrubpag,t1.tx_tiprub ,t3.tx_tipegrprg ,t3.co_egr,t1.tx_nom, t3.fe_ing";

            strDescAtr+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            //strSql+=" case (nd_porrubpag>0.00001) when true then TRUE::boolean else FALSE::boolean end as blnSelPer, ";
            strDescAtr+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strDescAtr+=" case (c.co_egr is not null) when true then c.co_egr::varchar else '' end as aux1, '' as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strDescAtr+=" nd_valrub as subtotal,";
            strDescAtr+=" nd_valrub as total, ";
            strDescAtr+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strDescAtr+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then nd_valrub else (nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strDescAtr+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strDescAtr+=" from tbm_ingegrmentra a";
            strDescAtr+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strDescAtr+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub)";
            strDescAtr+=" where a.co_emp = " + strCoEmp;
            strDescAtr+=" and a.ne_ani = " + strAño;
            strDescAtr+=" and a.ne_mes = " + strMes;
            strDescAtr+=" and a.co_tra = " + strCoTra;
            strDescAtr+=" and ne_per = " + strPer;
            strDescAtr+=" and a.co_rub=16";
            
            strSql+=" " + strDescAtr;
            
//            strSql+=" )";
            
            strSql+=" union";
            
            strSql+=" select a.co_emp, a.co_tra, a.ne_ani, a.ne_mes , a.co_rub, b.tx_tiprub ,c.tx_tipegrprg ,c.co_egr,b.tx_nom,";// false as blnSelPer , ";
            strSql+=" case (nd_porrubpag>0.00001 and (case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then a.nd_valrub else (a.nd_valrub * (nd_porrubpag)) end) is not null) when true then TRUE::boolean else FALSE::boolean end as blnSelPer,";
            strSql+=" case (a.nd_valrub is not null) when true then (round((a.nd_valrub*-1/(d.nd_valrub/30/60/8)),0))::varchar else ''::varchar end as aux1, ";
            strSql+=" (d.nd_valrub/30/60/8)::varchar as aux2, '' as aux3, '' as aux4, '' as aux5, '' as aux6, ";
            strSql+=" a.nd_valrub as subtotal,";
            strSql+=" a.nd_valrub as total, ";
            strSql+=" case (ne_per in(1)) when true then (nd_porrubpag*100) else 100 end as porcentaje, ";
            strSql+=" case (a.co_rub in (1,2,6,7,8,24,25,26)) when true then a.nd_valrub else (a.nd_valrub * (nd_porrubpag)) end as nd_ValTot,";
            strSql+=" '' as butAne, c.fe_ing,null as totalRecibir";
            strSql+=" from tbm_ingegrmentra a";
            strSql+=" inner join tbm_rubrolpag b on (b.co_rub=a.co_rub)";
            strSql+=" left outer join tbm_cabingegrprgtra c on (c.co_tra=a.co_tra and c.co_emp=a.co_emp and c.co_rub=a.co_rub ";
            strSql+=" and c.fe_ing between (select fe_des from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=1)";
            //strSql+=" and (select fe_has from tbm_feccorrolpag where co_emp="+strCoEmp+" and ne_ani="+strAño+" and ne_mes="+strMes+" and ne_per=2)";
            strSql+=" and ("+strFecHas+")  ";
            strSql+=" )";
            strSql+=" inner join tbm_suetra d on (d.co_emp=a.co_emp and d.co_tra=a.co_tra and d.co_rub=1)";
            strSql+=" where a.co_emp = " + strCoEmp;
            strSql+=" and a.ne_ani = " + strAño;
            strSql+=" and a.ne_mes = " + strMes;
            strSql+=" and a.co_tra = " + strCoTra;
            strSql+=" and ne_per = " + strPer;
            strSql+=" AND a.co_tra not in (4,6,8,19,24,27,30,31,32,33,37,39,41,42,45,46,80,81) ";
            strSql+=" and a.co_rub in (21)";
       
            strSql+=" )x";
            strSql+=" order by tx_tiprub desc,co_rub";

           System.out.println(strSql);
           rstLoc=stmLoc.executeQuery(strSql);

           
           int intCont=1;
           int intEsPI=1;
           while(rstLoc.next()){
               
               System.out.println("procesando rubro: " + rstLoc.getString("co_rub"));
               vecReg = new java.util.Vector();
               
               if(rstLoc.getString("aux1").compareTo("")==0){
                   vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_TIPO, "");
                    vecReg.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                    vecReg.add(INT_TBL_NOM_RUB, rstLoc.getString("tx_nom") );
                    vecReg.add(INT_TBL_SEL_ACT, "" );
                    vecReg.add(INT_TBL_AUX1, "" );
                    vecReg.add(INT_TBL_AUX2, "" );
                    vecReg.add(INT_TBL_AUX3, "" );
                    vecReg.add(INT_TBL_AUX4, "" );
                    vecReg.add(INT_TBL_AUX5, "" );
                    vecReg.add(INT_TBL_AUX6, "" );
                    vecReg.add(INT_TBL_AUX7, "" );
                    vecReg.add(INT_TBL_AUX8, "" );
                    vecReg.add(INT_TBL_AUX9, "" );
                    vecReg.add(INT_TBL_AUX10, "" );
                    if(rstLoc.getString("co_rub").compareTo("8")==0){
                        vecReg.add(INT_TBL_SUBTOTAL, "0" );
                    }else{
                        vecReg.add(INT_TBL_SUBTOTAL, rstLoc.getDouble("subtotal") );
                    }
                    
                    vecReg.add(INT_TBL_TOTAL, "" );
                    vecReg.add(INT_TBL_PORC, "" );
                    vecReg.add(INT_TBL_VAL_TOT, "" );
                    vecReg.add(INT_TBL_BUT_ANEXO, "" );
                    if(intEsPI%2==1){
                        vecReg.add(INT_TBL_EST,"S");
                    }else{
                        vecReg.add(INT_TBL_EST,"V");
                    }
                    vecData.add(vecReg);
                    
                    
                    vecRegTotalRubro = new java.util.Vector();
                    vecRegTotalRubro.add(INT_TBL_LINEA, "");
                    vecRegTotalRubro.add(INT_TBL_TIPO, "");
                    vecRegTotalRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                    vecRegTotalRubro.add(INT_TBL_NOM_RUB, "" );
                    vecRegTotalRubro.add(INT_TBL_SEL_ACT, rstLoc.getBoolean("blnSelPer") );
                    vecRegTotalRubro.add(INT_TBL_AUX1, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX2, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX3, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX4, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX5, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX6, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX7, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX8, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX9, "" );
                    vecRegTotalRubro.add(INT_TBL_AUX10, "" );
                    vecRegTotalRubro.add(INT_TBL_SUBTOTAL, "" );
                    vecRegTotalRubro.add(INT_TBL_TOTAL, rstLoc.getDouble("total") );
                    if(rstLoc.getDouble("porcentaje")==0){
                        vecRegTotalRubro.add(INT_TBL_PORC, "0%");
                    }else{
                        vecRegTotalRubro.add(INT_TBL_PORC, String.valueOf(rstLoc.getDouble("porcentaje"))+"%" );
                    }

                    vecRegTotalRubro.add(INT_TBL_VAL_TOT, rstLoc.getDouble("nd_valtot") );
                    vecRegTotalRubro.add(INT_TBL_BUT_ANEXO, "" );
                    if(intEsPI%2==1){
                        vecRegTotalRubro.add(INT_TBL_EST,"S");
                    }else{
                        vecRegTotalRubro.add(INT_TBL_EST,"V");
                    }
                    vecData.add(vecRegTotalRubro);
                    
               }else{
                   
                   vecReg.add(INT_TBL_LINEA, "");
                   vecReg.add(INT_TBL_TIPO, "");
                   vecReg.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                   vecReg.add(INT_TBL_NOM_RUB, rstLoc.getString("tx_nom") );
                   vecReg.add(INT_TBL_SEL_ACT, "" );
                   vecReg.add(INT_TBL_AUX1, "" );
                   vecReg.add(INT_TBL_AUX2, "" );
                   vecReg.add(INT_TBL_AUX3, "" );
                   vecReg.add(INT_TBL_AUX4, "" );
                   vecReg.add(INT_TBL_AUX5, "" );
                   vecReg.add(INT_TBL_AUX6, "" );
                   vecReg.add(INT_TBL_AUX7, "" );
                   vecReg.add(INT_TBL_AUX8, "" );
                   vecReg.add(INT_TBL_AUX9, "" );
                   vecReg.add(INT_TBL_AUX10, "" );
                   if(rstLoc.getString("co_rub").compareTo("8")==0 || rstLoc.getString("co_rub").compareTo("5")==0 
                           || rstLoc.getString("co_rub").compareTo("4")==0 || rstLoc.getString("co_rub").compareTo("16")==0
                           || rstLoc.getString("co_rub").compareTo("21")==0
                           || rstLoc.getString("co_rub").compareTo("14")==0
                           || rstLoc.getString("co_rub").compareTo("15")==0
                           || rstLoc.getString("co_rub").compareTo("18")==0){
                        vecReg.add(INT_TBL_SUBTOTAL, "0" );
                    }else{
                        vecReg.add(INT_TBL_SUBTOTAL, rstLoc.getDouble("subtotal") );
                    }
                   vecReg.add(INT_TBL_TOTAL, "" );
                   vecReg.add(INT_TBL_PORC, "" );
                   vecReg.add(INT_TBL_VAL_TOT, "" );
                   vecReg.add(INT_TBL_BUT_ANEXO, "" );
                   if(intEsPI%2==1){
                        vecReg.add(INT_TBL_EST,"S");
                    }else{
                        vecReg.add(INT_TBL_EST,"V");
                    }
                   vecData.add(vecReg);
                   
                   
                   if(rstLoc.getString("tx_tiprub").compareTo("E")==0){
                       
                       if(rstLoc.getString("co_rub").compareTo("8")==0){
                           
                           java.util.Vector vecRegCabDetalleRubro = new java.util.Vector();
                            vecRegCabDetalleRubro.add(INT_TBL_LINEA, "");
                            vecRegCabDetalleRubro.add(INT_TBL_TIPO, "");
                            vecRegCabDetalleRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                            vecRegCabDetalleRubro.add(INT_TBL_NOM_RUB, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_SEL_ACT, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX1, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX2, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX3, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX4, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX5, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX6, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX7, "%" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX8, "Sueldo" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX9, "Horas Extras 1" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX10, "Horas Extras 2" );
                            vecRegCabDetalleRubro.add(INT_TBL_SUBTOTAL, "Valor" );
                            vecRegCabDetalleRubro.add(INT_TBL_TOTAL, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_PORC, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_VAL_TOT, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
                            if(intEsPI%2==1){
                                vecRegCabDetalleRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_EST,"V");
                            }
                            vecData.add(vecRegCabDetalleRubro);

                            vecRegDetalleRubro = new java.util.Vector();
                             vecRegDetalleRubro.add(INT_TBL_LINEA, "");
                             vecRegDetalleRubro.add(INT_TBL_TIPO, "");
                             vecRegDetalleRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                             vecRegDetalleRubro.add(INT_TBL_NOM_RUB, "" );
                             vecRegDetalleRubro.add(INT_TBL_SEL_ACT, rstLoc.getBoolean("blnSelPer") );
                             vecRegDetalleRubro.add(INT_TBL_AUX1, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX2, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX3, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX4, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX5, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX6, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX7, rstLoc.getString("aux1") );
                             vecRegDetalleRubro.add(INT_TBL_AUX8, rstLoc.getDouble("aux2") );
                             vecRegDetalleRubro.add(INT_TBL_AUX9, rstLoc.getDouble("aux3") );
                             vecRegDetalleRubro.add(INT_TBL_AUX10, rstLoc.getDouble("aux4") );
                             vecRegDetalleRubro.add(INT_TBL_SUBTOTAL, rstLoc.getDouble("total") );
                             vecRegDetalleRubro.add(INT_TBL_TOTAL, "" );
                             vecRegDetalleRubro.add(INT_TBL_PORC, "" );
                             vecRegDetalleRubro.add(INT_TBL_VAL_TOT, "" );
                             vecRegDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
                             if(intEsPI%2==1){
                                vecRegDetalleRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegDetalleRubro.add(INT_TBL_EST,"V");
                            }
                             vecData.add(vecRegDetalleRubro);
                             
                            vecRegTotalRubro = new java.util.Vector();
                            vecRegTotalRubro.add(INT_TBL_LINEA, "");
                            vecRegTotalRubro.add(INT_TBL_TIPO, "");
                            vecRegTotalRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                            vecRegTotalRubro.add(INT_TBL_NOM_RUB, "" );
                            vecRegTotalRubro.add(INT_TBL_SEL_ACT, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX1, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX2, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX3, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX4, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX5, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX6, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX7, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX8, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX9, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX10, "" );
                            vecRegTotalRubro.add(INT_TBL_SUBTOTAL, "" );
                            vecRegTotalRubro.add(INT_TBL_TOTAL, rstLoc.getDouble("total") );
                            if(rstLoc.getDouble("porcentaje")==0){
                                vecRegTotalRubro.add(INT_TBL_PORC, "0%");
                            }else{
                                vecRegTotalRubro.add(INT_TBL_PORC, String.valueOf(rstLoc.getDouble("porcentaje"))+"%" );
                            }

                            vecRegTotalRubro.add(INT_TBL_VAL_TOT, rstLoc.getDouble("nd_valtot") );
                            vecRegTotalRubro.add(INT_TBL_BUT_ANEXO, "" );
                            if(intEsPI%2==1){
                                vecRegTotalRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegTotalRubro.add(INT_TBL_EST,"V");
                            }
                            vecData.add(vecRegTotalRubro);
                           
                       }else{
                           java.util.Vector vecRegCabDetalleRubro = new java.util.Vector();
                            vecRegCabDetalleRubro.add(INT_TBL_LINEA, "");
                            vecRegCabDetalleRubro.add(INT_TBL_TIPO, "");
                            vecRegCabDetalleRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                            vecRegCabDetalleRubro.add(INT_TBL_NOM_RUB, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_SEL_ACT, "" );
                            if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX1, "Minutos a descontar" );
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_AUX1, "Cód.Egr.Prg." );
                            }
                            
                            if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX2, "" );
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_AUX2, "" );
                            }
                            
                            
                            vecRegCabDetalleRubro.add(INT_TBL_AUX3, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX4, "" );
                            if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX5, "" );
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_AUX5, "Fec.Ing." );
                            }
                            
                            vecRegCabDetalleRubro.add(INT_TBL_AUX6, "" );
                            if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX7, "Valor por minuto" );
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_AUX7, "" );
                            }
                            
                            vecRegCabDetalleRubro.add(INT_TBL_AUX8, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX9, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX10, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_SUBTOTAL, "Valor" );
                            vecRegCabDetalleRubro.add(INT_TBL_TOTAL, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_PORC, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_VAL_TOT, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
                            if(intEsPI%2==1){
                                vecRegCabDetalleRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_EST,"V");
                            }
                            vecData.add(vecRegCabDetalleRubro);

                            vecRegDetalleRubro = new java.util.Vector();
                             vecRegDetalleRubro.add(INT_TBL_LINEA, "");
                             vecRegDetalleRubro.add(INT_TBL_TIPO, "");
                             vecRegDetalleRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                             vecRegDetalleRubro.add(INT_TBL_NOM_RUB, "" );
                             vecRegDetalleRubro.add(INT_TBL_SEL_ACT, rstLoc.getBoolean("blnSelPer") );
                             if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                 vecRegDetalleRubro.add(INT_TBL_AUX1, rstLoc.getString("aux1") );
                             }else{
                                 vecRegDetalleRubro.add(INT_TBL_AUX1, rstLoc.getString("aux1") );
                             }
                             
                             if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                 vecRegDetalleRubro.add(INT_TBL_AUX2, "" );
                             }else{
                                 vecRegDetalleRubro.add(INT_TBL_AUX2, rstLoc.getString("aux2") );
                             }
                             
                             vecRegDetalleRubro.add(INT_TBL_AUX3, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX4, "" );
                             
                             

                            Date date = rstLoc.getDate("fe_ing");
                            if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0 ){
                                vecRegDetalleRubro.add(INT_TBL_AUX5, "" );
                            }else{
                                vecRegDetalleRubro.add(INT_TBL_AUX5, objUti.formatearFecha(date, "dd/MMM/yyyy") );
                            }
                             
                             vecRegDetalleRubro.add(INT_TBL_AUX6, "" );
                             if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                 vecRegDetalleRubro.add(INT_TBL_AUX7, rstLoc.getString("aux2"));
                             }else{
                                 vecRegDetalleRubro.add(INT_TBL_AUX7, "" );
                             }
                             
                             
                             if(rstLoc.getString("co_rub").compareTo("16")==0 || rstLoc.getString("co_rub").compareTo("21")==0){
                                 vecRegDetalleRubro.add(INT_TBL_AUX8, "" );
                             }else{
                                 vecRegDetalleRubro.add(INT_TBL_AUX8, "" );
                             }
                             
                             
                             vecRegDetalleRubro.add(INT_TBL_AUX9, "" );
                             vecRegDetalleRubro.add(INT_TBL_AUX10, "" );
                             vecRegDetalleRubro.add(INT_TBL_SUBTOTAL, rstLoc.getDouble("total") );
                             vecRegDetalleRubro.add(INT_TBL_TOTAL, "" );
                             vecRegDetalleRubro.add(INT_TBL_PORC, "" );
                             vecRegDetalleRubro.add(INT_TBL_VAL_TOT, "" );
                             vecRegDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
                             if(intEsPI%2==1){
                                vecRegDetalleRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegDetalleRubro.add(INT_TBL_EST,"V");
                            }
                             vecData.add(vecRegDetalleRubro);
                             
                            vecRegTotalRubro = new java.util.Vector();
                            vecRegTotalRubro.add(INT_TBL_LINEA, "");
                            vecRegTotalRubro.add(INT_TBL_TIPO, "");
                            vecRegTotalRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                            vecRegTotalRubro.add(INT_TBL_NOM_RUB, "" );
                            vecRegTotalRubro.add(INT_TBL_SEL_ACT, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX1, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX2, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX3, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX4, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX5, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX6, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX7, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX8, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX9, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX10, "" );
                            vecRegTotalRubro.add(INT_TBL_SUBTOTAL, "" );
                            vecRegTotalRubro.add(INT_TBL_TOTAL, rstLoc.getDouble("total") );
                            if(rstLoc.getDouble("porcentaje")==0){
                                vecRegTotalRubro.add(INT_TBL_PORC, "0%");
                            }else{
                                vecRegTotalRubro.add(INT_TBL_PORC, String.valueOf(rstLoc.getDouble("porcentaje"))+"%" );
                            }

                            vecRegTotalRubro.add(INT_TBL_VAL_TOT, rstLoc.getDouble("nd_valtot") );
                            vecRegTotalRubro.add(INT_TBL_BUT_ANEXO, "" );
                            if(intEsPI%2==1){
                                vecRegTotalRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegTotalRubro.add(INT_TBL_EST,"V");
                            }
                            vecData.add(vecRegTotalRubro);
                       }
                       
                       
                       
                   }else{
                       java.util.Vector vecRegCabDetalleRubro = new java.util.Vector();
                       
                       if(rstLoc.getString("co_rub").compareTo("1")==0 || rstLoc.getString("co_rub").compareTo("2")==0 || rstLoc.getString("co_rub").compareTo("3")==0 
                               || rstLoc.getString("co_rub").compareTo("4")==0  || rstLoc.getString("co_rub").compareTo("5")==0 
                               || rstLoc.getString("co_rub").compareTo("6")==0 || rstLoc.getString("co_rub").compareTo("7")==0){
                           
                           vecRegCabDetalleRubro.add(INT_TBL_LINEA, "");
                            vecRegCabDetalleRubro.add(INT_TBL_TIPO, "");
                            vecRegCabDetalleRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                            vecRegCabDetalleRubro.add(INT_TBL_NOM_RUB, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_SEL_ACT, "" );
                            
                            if(rstLoc.getString("co_rub").compareTo("4")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX1, "Día.Lab." );
                            }else if (rstLoc.getString("co_rub").compareTo("5")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX1, "" );
                            }else{ 
                                vecRegCabDetalleRubro.add(INT_TBL_AUX1, "Cód.Ing.Prg." );
                            }
                            
//                            if(rstLoc.getString("co_rub").compareTo("4")==0){
//                                
//                                vecRegCabDetalleRubro.add(INT_TBL_AUX2, "Val.Alm." );
//                            }else{
                                
                                vecRegCabDetalleRubro.add(INT_TBL_AUX2, "" );
//                            }
                            
                            vecRegCabDetalleRubro.add(INT_TBL_AUX3, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX4, "" );
                            if(rstLoc.getString("co_rub").compareTo("5")==0 || rstLoc.getString("co_rub").compareTo("4")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX5, "");
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_AUX5, "Fec.Ing." );
                            }
                            
                            vecRegCabDetalleRubro.add(INT_TBL_AUX6, "" );
                            if(rstLoc.getString("co_rub").compareTo("5")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX7, "Sueldo" );
                            } else if(rstLoc.getString("co_rub").compareTo("4")==0){
                                
                                vecRegCabDetalleRubro.add(INT_TBL_AUX7, "Val.Alm." );
                            }
                            else {
                                vecRegCabDetalleRubro.add(INT_TBL_AUX7, "" );
                            }
                            
                            
                            if(rstLoc.getString("co_rub").compareTo("5")==0){
                                vecRegCabDetalleRubro.add(INT_TBL_AUX8, "Horas Extras 1" );
                            }else {
                                vecRegCabDetalleRubro.add(INT_TBL_AUX8, "" );
                            }
                            
                            vecRegCabDetalleRubro.add(INT_TBL_AUX9, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_AUX10, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_SUBTOTAL, "Valor" );
                            vecRegCabDetalleRubro.add(INT_TBL_TOTAL, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_PORC, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_VAL_TOT, "" );
                            vecRegCabDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
                            if(intEsPI%2==1){
                                vecRegCabDetalleRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegCabDetalleRubro.add(INT_TBL_EST,"V");
                            }
                            vecData.add(vecRegCabDetalleRubro);
                            
                            vecRegDetalleRubro = new java.util.Vector();
                            vecRegDetalleRubro.add(INT_TBL_LINEA, "");
                            vecRegDetalleRubro.add(INT_TBL_TIPO, "");
                            vecRegDetalleRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                            vecRegDetalleRubro.add(INT_TBL_NOM_RUB, "" );
                            vecRegDetalleRubro.add(INT_TBL_SEL_ACT, rstLoc.getBoolean("blnSelPer") );
                            if(rstLoc.getString("co_rub").compareTo("5")==0){
                                vecRegDetalleRubro.add(INT_TBL_AUX1, "" );
                            }else{
                                vecRegDetalleRubro.add(INT_TBL_AUX1, rstLoc.getInt("aux1") );
                            }
                            
                            if(rstLoc.getString("co_rub").compareTo("5")==0){
                                vecRegDetalleRubro.add(INT_TBL_AUX2, "" );
                            }else if (rstLoc.getString("co_rub").compareTo("4")==0){
                                vecRegDetalleRubro.add(INT_TBL_AUX2, "");
                            }
                            else{

                                if(rstLoc.getString("aux2").compareTo("")==0){
                                    vecRegDetalleRubro.add(INT_TBL_AUX2, "");
                                }else{
                                    vecRegDetalleRubro.add(INT_TBL_AUX2, rstLoc.getInt("aux2") );
                                }
                                
                                
                            }
                            
                            vecRegDetalleRubro.add(INT_TBL_AUX3, rstLoc.getString("aux3") );
                            vecRegDetalleRubro.add(INT_TBL_AUX4, rstLoc.getString("aux4") );
                            Date date = rstLoc.getDate("fe_ing");
                            if(rstLoc.getString("co_rub").compareTo("2")==0 || rstLoc.getString("co_rub").compareTo("3")==0){
                                vecRegDetalleRubro.add(INT_TBL_AUX5, objUti.formatearFecha(date, "dd/MMM/yyyy") );
                            }else{
                                vecRegDetalleRubro.add(INT_TBL_AUX5, rstLoc.getString("fe_ing") );
                            }
                            
                            
                            if(rstLoc.getString("co_rub").compareTo("3")==0){
                                if(rstLoc.getString("aux6").compareTo("")!=0){
                                    date = rstLoc.getDate("aux6");
                                    vecRegDetalleRubro.add(INT_TBL_AUX6, objUti.formatearFecha(date, "dd/MMM/yyyy") );
                                }else{
                                    vecRegDetalleRubro.add(INT_TBL_AUX6, "" );
                                }
                                
                                
                            }else{
                                vecRegDetalleRubro.add(INT_TBL_AUX6, "" );
                            }
                            
                            if(rstLoc.getString("co_rub").compareTo("5")==0){
                                vecRegDetalleRubro.add(INT_TBL_AUX7, rstLoc.getString("aux1") );
                                
                            }else if (rstLoc.getString("co_rub").compareTo("4")==0){
                                vecRegDetalleRubro.add(INT_TBL_AUX7, rstLoc.getDouble("aux2") );
                            }
                            else{
                                vecRegDetalleRubro.add(INT_TBL_AUX7, "" );
                            }
                            
                            if(rstLoc.getString("co_rub").compareTo("5")==0){
                                vecRegDetalleRubro.add(INT_TBL_AUX8, rstLoc.getString("aux2") );
                            }else{
                                
                                vecRegDetalleRubro.add(INT_TBL_AUX8, "" );
                            }
                            
                            //vecRegDetalleRubro.add(INT_TBL_AUX7, "" );
                            //vecRegDetalleRubro.add(INT_TBL_AUX8, "" );
                            vecRegDetalleRubro.add(INT_TBL_AUX9, "" );
                            vecRegDetalleRubro.add(INT_TBL_AUX10, "" );
                            vecRegDetalleRubro.add(INT_TBL_SUBTOTAL, rstLoc.getDouble("total") );
                            vecRegDetalleRubro.add(INT_TBL_TOTAL, "" );
                            vecRegDetalleRubro.add(INT_TBL_PORC, "" );
                            vecRegDetalleRubro.add(INT_TBL_VAL_TOT, "" );
                            vecRegDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
                            if(intEsPI%2==1){
                                vecRegDetalleRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegDetalleRubro.add(INT_TBL_EST,"V");
                            }
                            vecData.add(vecRegDetalleRubro);
                            
                            
                            vecRegTotalRubro = new java.util.Vector();
                            vecRegTotalRubro.add(INT_TBL_LINEA, "");
                            vecRegTotalRubro.add(INT_TBL_TIPO, "");
                            vecRegTotalRubro.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
                            vecRegTotalRubro.add(INT_TBL_NOM_RUB, "" );
                            vecRegTotalRubro.add(INT_TBL_SEL_ACT, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX1, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX2, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX3, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX4, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX5, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX6, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX7, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX8, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX9, "" );
                            vecRegTotalRubro.add(INT_TBL_AUX10, "" );
                            vecRegTotalRubro.add(INT_TBL_SUBTOTAL, "" );
                            vecRegTotalRubro.add(INT_TBL_TOTAL, rstLoc.getDouble("subtotal") );
                            if(rstLoc.getDouble("porcentaje")==0){
                                vecRegTotalRubro.add(INT_TBL_PORC, "0%");
                            }else{
                                vecRegTotalRubro.add(INT_TBL_PORC, String.valueOf(rstLoc.getDouble("porcentaje"))+"%" );
                            }

                            if(rstLoc.getString("co_rub").compareTo("3")==0){
                                vecRegTotalRubro.add(INT_TBL_VAL_TOT, rstLoc.getDouble("subtotal") );
                            }else{
                                vecRegTotalRubro.add(INT_TBL_VAL_TOT, rstLoc.getDouble("subtotal") );
                            }
                            
                            vecRegTotalRubro.add(INT_TBL_BUT_ANEXO, "" );
                            if(intEsPI%2==1){
                                vecRegTotalRubro.add(INT_TBL_EST,"S");
                            }else{
                                vecRegTotalRubro.add(INT_TBL_EST,"V");
                            }
                            vecData.add(vecRegTotalRubro);
                            
                           
                       }else{
                           
                       }
                       
                       
                   }
               }
               
               
               if(intCont==1){//intCantRubAct
                       intEsPI++; 
                       intCont=0;
                    }
               intCont++;
               
           }
           
//           while(rstLoc.next()){
//
//                java.util.Vector vecReg = new java.util.Vector();
//                
//                
//                
//                if(rstLoc.getString("aux1").compareTo("0")!=0){
//                    
//                    vecReg.add(INT_TBL_LINEA, "");
//                    vecReg.add(INT_TBL_TIPO, "");
//                    vecReg.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
//                    vecReg.add(INT_TBL_NOM_RUB, rstLoc.getString("tx_nom") );
//                    vecReg.add(INT_TBL_SEL_ACT, "" );
//                    vecReg.add(INT_TBL_AUX1, "" );
//                    vecReg.add(INT_TBL_AUX2, "" );
//                    vecReg.add(INT_TBL_AUX3, "" );
//                    vecReg.add(INT_TBL_AUX4, "" );
//                    vecReg.add(INT_TBL_AUX5, "" );
//                    vecReg.add(INT_TBL_AUX6, "" );
//                    vecReg.add(INT_TBL_SUBTOTAL, "0" );
//                    vecReg.add(INT_TBL_TOTAL, "" );
//                    vecReg.add(INT_TBL_PORC, "" );
//                    vecReg.add(INT_TBL_VAL_TOT, "" );
//                    vecReg.add(INT_TBL_BUT_ANEXO, "" );
//                    vecData.add(vecReg);
//                    
//                    java.util.Vector vecRegCabDetalleRubro = new java.util.Vector();
//                    vecRegCabDetalleRubro.add(INT_TBL_LINEA, "");
//                    vecRegCabDetalleRubro.add(INT_TBL_TIPO, "");
//                    vecRegCabDetalleRubro.add(INT_TBL_COD_RUB, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_NOM_RUB, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_SEL_ACT, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_AUX1, "Cód.Egr.Prg." );
//                    vecRegCabDetalleRubro.add(INT_TBL_AUX2, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_AUX3, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_AUX4, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_AUX5, "Fec.Ing." );
//                    vecRegCabDetalleRubro.add(INT_TBL_AUX6, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_SUBTOTAL, "Valor" );
//                    vecRegCabDetalleRubro.add(INT_TBL_TOTAL, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_PORC, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_VAL_TOT, "" );
//                    vecRegCabDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
//                    vecData.add(vecRegCabDetalleRubro);
//                    
//                    java.util.Vector vecRegDetalleRubro = new java.util.Vector();
//                    vecRegDetalleRubro.add(INT_TBL_LINEA, "");
//                    vecRegDetalleRubro.add(INT_TBL_TIPO, "");
//                    vecRegDetalleRubro.add(INT_TBL_COD_RUB, "" );
//                    vecRegDetalleRubro.add(INT_TBL_NOM_RUB, "" );
//                    vecRegDetalleRubro.add(INT_TBL_SEL_ACT, "" );
//                    vecRegDetalleRubro.add(INT_TBL_AUX1, rstLoc.getString("aux1") );
//                    vecRegDetalleRubro.add(INT_TBL_AUX2, "" );
//                    vecRegDetalleRubro.add(INT_TBL_AUX3, "" );
//                    vecRegDetalleRubro.add(INT_TBL_AUX4, "" );
//                    vecRegDetalleRubro.add(INT_TBL_AUX5, rstLoc.getString("fe_ing") );
//                    vecRegDetalleRubro.add(INT_TBL_AUX6, "" );
//                    vecRegDetalleRubro.add(INT_TBL_SUBTOTAL, rstLoc.getDouble("total") );
//                    vecRegDetalleRubro.add(INT_TBL_TOTAL, "" );
//                    vecRegDetalleRubro.add(INT_TBL_PORC, "" );
//                    vecRegDetalleRubro.add(INT_TBL_VAL_TOT, "" );
//                    vecRegDetalleRubro.add(INT_TBL_BUT_ANEXO, "" );
//                    vecData.add(vecRegDetalleRubro);
//                    
//                }else{
//                    vecReg.add(INT_TBL_LINEA, "");
//                    vecReg.add(INT_TBL_TIPO, "");
//                    vecReg.add(INT_TBL_COD_RUB, rstLoc.getString("co_rub") );
//                    vecReg.add(INT_TBL_NOM_RUB, rstLoc.getString("tx_nom") );
//                    vecReg.add(INT_TBL_SEL_ACT, "" );
//                    vecReg.add(INT_TBL_AUX1, "" );
//                    vecReg.add(INT_TBL_AUX2, "" );
//                    vecReg.add(INT_TBL_AUX3, "" );
//                    vecReg.add(INT_TBL_AUX4, "" );
//                    vecReg.add(INT_TBL_AUX5, "" );
//                    vecReg.add(INT_TBL_AUX6, "" );
//                    vecReg.add(INT_TBL_SUBTOTAL, rstLoc.getDouble("subtotal") );
//                    vecReg.add(INT_TBL_TOTAL, "" );
//                    vecReg.add(INT_TBL_PORC, "" );
//                    vecReg.add(INT_TBL_VAL_TOT, "" );
//                    vecReg.add(INT_TBL_BUT_ANEXO, "" );
//                    vecData.add(vecReg);
//                    
//                }
//                
                
//                
//                dblTot=rstLoc.getDouble("totalrecibir");
//                
//                //vecData = insertarRegistro(vecData);
//           }
           rstLoc.close();
           rstLoc=null;
           
//           java.util.Vector vecRegTotal = new java.util.Vector();
//           vecRegTotal.add(INT_TBL_LINEA, "");
//           vecRegTotal.add(INT_TBL_TIPO, "");
//           vecRegTotal.add(INT_TBL_COD_RUB, "" );
//           vecRegTotal.add(INT_TBL_NOM_RUB, "" );
//           vecRegTotal.add(INT_TBL_SEL_ACT, "" );
//           vecRegTotal.add(INT_TBL_AUX1, "" );
//           vecRegTotal.add(INT_TBL_AUX2, "" );
//           vecRegTotal.add(INT_TBL_AUX3, "" );
//           vecRegTotal.add(INT_TBL_AUX4, "" );
//           vecRegTotal.add(INT_TBL_AUX5, "" );
//           vecRegTotal.add(INT_TBL_AUX6, "" );
//           vecRegTotal.add(INT_TBL_SUBTOTAL, "" );
//           vecRegTotal.add(INT_TBL_TOTAL, "" );
//           vecRegTotal.add(INT_TBL_PORC, "" );
//           vecRegTotal.add(INT_TBL_VAL_TOT, dblTot );
//           vecRegTotal.add(INT_TBL_BUT_ANEXO, "" );
//           vecData.add(vecRegTotal);

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);
           
           objTblTot.calcularTotales();

           lblMsgSis.setText("Listo");
           pgrSis.setValue(0);
           pgrSis.setIndeterminate(false);

           stmLoc.close();
           stmLoc=null;
           conn.close();
           conn=null;
           vecData.clear();
           blnRes=true;

   }}catch(java.sql.SQLException Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { Evt.printStackTrace();blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}

    private Vector insertarRegistro(Vector vecData){
        java.util.Vector vecReg = new java.util.Vector();
        vecReg.add(INT_TBL_LINEA, "");
        vecReg.add(INT_TBL_TIPO, "");
        vecReg.add(INT_TBL_COD_RUB, "");
        vecReg.add(INT_TBL_NOM_RUB, "");
        vecReg.add(INT_TBL_SEL_ACT, "" );
                vecReg.add(INT_TBL_AUX1, "" );
                vecReg.add(INT_TBL_AUX2, "" );
                vecReg.add(INT_TBL_AUX3, "" );
                vecReg.add(INT_TBL_AUX4, "" );
                vecReg.add(INT_TBL_AUX5, "" );
                vecReg.add(INT_TBL_AUX6, "" );
                vecReg.add(INT_TBL_SUBTOTAL, "");
                vecReg.add(INT_TBL_TOTAL, "" );
                vecReg.add(INT_TBL_PORC, "" );
                vecReg.add(INT_TBL_VAL_TOT, "" );
                vecReg.add(INT_TBL_BUT_ANEXO, "..." );
                vecData.add(vecReg);
                return vecData;
    }

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPer;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JCheckBox jChkNovFuePer;
    private javax.swing.JCheckBox jChkNovPer;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtDesde;
    private javax.swing.JTextField txtHas;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables




private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    
     int intCol=tblDat.columnAtPoint(evt.getPoint());
     String strMsg="";

    switch (intCol){

        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_TIPO:
            strMsg="";
            break;
        case INT_TBL_COD_RUB:
            strMsg="Código del rubro";
            break;
        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}

private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
                        }
                    }
                    break;
            }
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
    }



private void cargarRepote(int intTipo){
   if (objThrGUIRpt==null)
    {
        objThrGUIRpt=new ZafThreadGUIRpt();
        objThrGUIRpt.setIndFunEje(intTipo);
        objThrGUIRpt.start();
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
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                   // objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                  //  objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                 //   objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                   // objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt=null;
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
        String strRutRpt, strNomRpt, strNomUsr="",  strFecHorSer="", strCamAudRpt = "";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String sqlAux="", strSql = "";
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {

                
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        
                        //Inicializar los parametros que se van a pasar al reporte.

//                        if(!(txtFec.getText().equals(""))){
//                            String strFec = txtFec.getText().replace("/", "");
//                            String strDD = strFec.substring(0, 2);
//                            String strMM = strFec.substring(3, 4);
//                            String strYYY = strFec.substring(4, 8);
//                            String strFecha = strYYY+"-"+strMM+"-"+strDD;
//                            sqlAux+=" WHERE a.fe_dia = '"+ strFecha + "' ";
//                        }
          
//                        if(!(txtCodOfi.getText().equals(""))){
//                            sqlAux+=" AND c.co_ofi="+txtCodOfi.getText()+" ";
//                        }

                       
                        
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            //case 415:
                            default:

                                 //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }
                                
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                               
                                obtenerCantidadRegistros();
                                
                                strSql="select a.co_tra, (b.tx_ape || ' ' || b.tx_nom) as nomcom, d.tx_nom as oficina from tbm_cabconasitra a "+
                                       "inner join tbm_tra b on (a.co_tra=b.co_tra) "+
                                       "inner join tbm_traemp c on (a.co_tra=c.co_tra and c.st_recAlm like 'S') "+
                                       "inner join tbm_ofi d on (c.co_ofi=d.co_ofi) " +
                                         sqlAux + " and a.ho_ent is not null " + 
                                       "order by nomcom";
                                
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("sql", strSql );
                                mapPar.put("cantotreg", intCanReg );
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("fecha", datFecAux);
                                
                                /*java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("coemp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("strAux",  sqlAux );
                                mapPar.put("nomusr", strNomUsr );
                                mapPar.put("fecimp", strFecHorSer );
                                mapPar.put("SUBREPORT_DIR", strRutRpt );
                                mapPar.put("strCamAudRpt", "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                */

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}
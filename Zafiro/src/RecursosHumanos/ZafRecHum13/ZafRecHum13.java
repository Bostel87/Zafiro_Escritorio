/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package RecursosHumanos.ZafRecHum13;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


/**
 * Primera quincena y fin de mes (Datos utilizados para generar...)
 * @author Roberto Flores
 * Guayaquil 22/08/2012
 */
public class ZafRecHum13 extends javax.swing.JInternalFrame {


  private Librerias.ZafParSis.ZafParSis objZafParSis;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  private ZafTblFilCab objTblFilCab;
  private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
  private ZafUtil objUti;
  
  private ZafTblCelEdiTxt objTblCelEdiTxt;                                            //Editor: JTextField en celda.
  private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
  private ZafThreadGUI objThrGUI;
  private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMenú en JTable.
  
  private static final int INT_TBL_LINEA=0;
  private static final int INT_TBL_CODEMP=1;
  private static final int INT_TBL_NOMEMP=2;
  private static final int INT_TBL_CODTRA=3;
  private static final int INT_TBL_NOMTRA=4;
  private static final int INT_TBL_FEINICON=5;
  private static final int INT_TBL_BUT_ANE=6;
//  private static final int INT_TBL_ND_POR_RUB=6;
  private static final int INT_TBL_DIAS_LAB=7;
  private static final int INT_TBL_ND_IESS=8;
  private static final int INT_TBL_HE_FI=9;
  
  private static final int INT_TBL_DAT_NUM_TOT_CDI=16;                   //Número total de columnas dinámicas.

  Vector vecTipDoc,vecDetDiario; //Vector que contiene el codigo de tipos de documentos  
  
  String strVer=" v2.0";
  
  private Vector vecCab = new Vector();                                 //Vector que contiene la cabecera del Table.
  private Vector vecDat;

  private int intCodEmp;                                                 //Código de la empresa.
  private boolean blnMod;                                                //Indica si han habido cambios en el documento
  private boolean blnConsDat;
  private ZafTblBus objTblBus;
  private ZafTblTot objTblTot;                        //JTable de totales.
  
  private String strCodEmp, strNomEmp;
  private String strCodDep = "";
  private String strDesLarDep = "";
  private String strCodTra = "";
  private String strNomTra = "";
    
  private ZafVenCon vcoEmp;                                   //Ventana de consulta.
  private ZafVenCon vcoDep;                                   //Ventana de consulta.
  private ZafVenCon vcoTra;
  
  private Connection con, conCab;
  private Statement stm, stmCab;
  private ResultSet rst;//, rstCab;
  
  private String strSQL, strAux, strSQLCon;
  
  private ZafPerUsr objPerUsr;
  
  private int intCanIng=0;
  private int intCanEgr=0;
  int intCanColTot=0;
  
  private int intConIni=0;
  
  private ZafTblCelEdiButGen objTblCelEdiButGen;
  
  private ArrayList<String> arrLst=null;//almacena los rubros del rol de pagos
    private ArrayList<Tbm_traemp> arrLstTbmTraEmp=new ArrayList<Tbm_traemp>();//almacena datos de empleados tbm_traemp
    
    Librerias.ZafUtil.ZafCtaCtb objCtaCtb;                      // Para obtener  los codigos y nombres de ctas ctbles
  private ZafAsiDia objAsiDia;  
  
  private String strCodCta="";
  private String strDesCorCta="";
  private String strDesLarCta="";
  
  private String strUbiCta, strAutCta;                        //Campos: Ubicacián y Estado de autorizacián de la cuenta.
  
  private double dblValDoc=0;
  
  private ZafTblCelRenLbl objTblCelRenLbl2;
  final java.awt.Color colFonCol =new java.awt.Color(255,255,0);
  
    /** Creates new form ZafRecHum13 */
    public ZafRecHum13(Librerias.ZafParSis.ZafParSis obj) {

        try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          initComponents();
          
          objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

          objUti = new ZafUtil();  
          
          objPerUsr=new ZafPerUsr(objZafParSis);
          
          butCon.setEnabled(false);
          butCerr.setEnabled(false);
          if(objZafParSis.getCodigoUsuario()!=1){
              if(  (objPerUsr.isOpcionEnabled(2857))){
                    butCon.setEnabled(true);
              }
              if(  (objPerUsr.isOpcionEnabled(2858))){
                  butCerr.setEnabled(true);
              }
          }else{
              butCon.setEnabled(true);
              butCerr.setEnabled(true);
          }
          
          if(objZafParSis.getCodigoUsuario()==220 || objZafParSis.getCodigoUsuario()==225){
              cboRol.addItem("Sueldo");
          }else{
              cboRol.addItem("Sueldo");
              cboRol.addItem("Bono");
              cboRol.addItem("Movilización");
              cboRol.addItem("Consolidado");
          }
          
          /*INSERTAR LOS AÑOS EXISTENTES EN TBM_INGEGRMENTRA*/
          cargarAños();
          seleccionarPeriodoActual();

          intCodEmp=objZafParSis.getCodigoEmpresa();
          
          objAsiDia=new ZafAsiDia(objZafParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
//                    if (txtCodTipDoc.getText().equals(""))
//                        objAsiDia.setCodigoTipoDocumento(-1);
//                    else
//                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            
            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
              panAsiDia.setVisible(false);
              objAsiDia.setVisible(false);
              tabGen.remove(2);
          }
            
//            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
//                
//            }
          
          this.setTitle(objZafParSis.getNombreMenu()+ " " + strVer);
          lblTit.setText(objZafParSis.getNombreMenu()); 
      }catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);e.printStackTrace();}
      catch(Exception e) {objUti.mostrarMsgErr_F1(this, e);e.printStackTrace();}
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

    private boolean seleccionarPeriodoActual(){
    boolean blnRes=true;
    String strSQL="";

    try{
        
        con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
        if (con!=null)
        {
            
            stm=con.createStatement();
            
            strSQL="select case when min(ne_per) is null then -1 else min(ne_per) END as ne_pero from tbm_ingegrmentra where st_rolpaggen is null and ne_per not in (0)";
            if (objZafParSis.getCodigoEmpresa()!=0) {
            strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
            }
            
            rst=stm.executeQuery(strSQL);

            int intNePer=0;
            if(rst.next()){
                intNePer=rst.getInt("ne_pero");
            }

            int intAño = 0;
            strSQL="select case when max(ne_ani) is null then 0 else max(ne_ani) END as ne_anio from tbm_ingegrmentra where st_rolpaggen is null";
            if (objZafParSis.getCodigoEmpresa()!=0) {
            strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
            }
            rst=stm.executeQuery(strSQL);

            if(rst.next()){
                intAño=rst.getInt("ne_anio");
            }

            int intMes = 0;
            strSQL="select case when max(ne_mes) is null then 0 else max(ne_mes) END as ne_meso from tbm_ingegrmentra where st_rolpaggen is null";
            if (objZafParSis.getCodigoEmpresa()!=0) {
            strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
            }
            strSQL+=" and ne_ani = " + intAño;
            rst=stm.executeQuery(strSQL);

            if(rst.next()){
                intMes=rst.getInt("ne_meso");
            }

            cboPerAAAA.setSelectedItem(String.valueOf(intAño));
            cboPerMes.setSelectedIndex(intMes);
            cboPer.setSelectedIndex(intNePer);
        }
     }catch (java.sql.SQLException Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
     }catch (Exception Evt) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, Evt);
    }finally {
        try{rst.close();}catch(Throwable ignore){}
        try{stm.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
    }
    return blnRes;
  } 
    
    public class MyTableModelListener implements TableModelListener {
    JTable table;

    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    MyTableModelListener(JTable table) {
        this.table = table;
    }

    public void tableChanged(TableModelEvent e) {
        if(!blnConsDat){
            switch (e.getType()) {
              case TableModelEvent.UPDATE:
                  blnMod = true;
                break;
            }
        }
        blnConsDat = false;
    }
}
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
//    private boolean configurarTabla()
//    {
//        boolean blnRes=true;
//        try
//        {
//            //Configurar JTable: Establecer el modelo.
//            //vecDat=new Vector();    //Almacena los datos
//            vecCab=new Vector();  //Almacena las cabeceras
//            vecCab.clear();
//            
//            vecCab.add(INT_TBL_LINEA,"");
//            vecCab.add(INT_TBL_CODEMP,"Cód.Emp.");
//            vecCab.add(INT_TBL_NOMEMP,"Empresa");
//            vecCab.add(INT_TBL_CODTRA,"Código");
//            vecCab.add(INT_TBL_NOMTRA,"Empleado");
//            vecCab.add(INT_TBL_BUT_ANE,"");
//            
//            /*RUBROS INGRESOS DE MANERA DINAMICA*/
//                      
//            Connection conIns = null;
//            Statement stmLoc = null;
//            ResultSet rstLoc = null;
//            
//            intCanIng=0;
//            intCanEgr=0;
//            
//            try{
//                conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                
//                    if(conIns!=null){
//                        
//                        stmLoc=conIns.createStatement();
//                                
//                        int intAño = 0;
//                        int intMes = 0;
//                        
////                        intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
////                        intMes=cboPerMes.getSelectedIndex();
//                        
//                        if(intConIni==0){
//                            
//                            if(objZafParSis.getCodigoEmpresa()!=objZafParSis.getCodigoEmpresaGrupo()){
//                                
//                            
//                                
//                                strSQL="select case when min(ne_ani) is null then 0 else min(ne_ani) END as ne_anio from tbm_ingegrmentra where st_rolpaggen is null";
//                                strSQL+=" and co_emp = " + objZafParSis.getCodigoEmpresa();
//                                System.out.println("sql ne_ani : "+strSQL);
//                                rstLoc=stmLoc.executeQuery(strSQL);
//
//                                if(rstLoc.next()){
//                                    intAño=rstLoc.getInt("ne_anio");
//                                }
//
//
//                                strSQL="select case when min(ne_mes) is null then 0 else min(ne_mes) END as ne_meso from tbm_ingegrmentra where st_rolpaggen is null";
//                                strSQL+=" and ne_per not in (0) and co_emp = " + objZafParSis.getCodigoEmpresa();
//                                System.out.println("sql ne_mes : "+strSQL);
//                                rstLoc=stmLoc.executeQuery(strSQL);
//
//                                if(rstLoc.next()){
//                                    intMes=rstLoc.getInt("ne_meso");
//                                }
//                                
//                            }
//                            
//                            
//                            
//                        }else{
//                            intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
//                            intMes=cboPerMes.getSelectedIndex();
//                        }
//                        
//
//                        arrLst = new ArrayList<String>();
//                        
//                        
//                        strSQL="";
//                        //strSQL="select * from tbm_rubrolpag where co_rub in (select distinct co_rub from tbm_ingEgrMenTra) order by co_rub asc";
////                        strSQL="select * from tbm_rubrolpag";
////                        strSQL+=" where co_rub in (select distinct co_rub from tbm_ingEgrMenTra)";
////                        strSQL+=" and extract(year from fe_ing)<="+intAño;
////                        strSQL+=" and extract(month from fe_ing)<="+intMes;
////                        strSQL+=" order by tx_tiprub desc,co_rub asc";
//                        strSQL="select * from tbm_rubrolpag";
//                        strSQL+=" where co_rub in (select distinct co_rub from tbm_ingEgrMenTra)";
//                        //strSQL+=" and extract(year from fe_ing)<="+intAño;
//                        //strSQL+=" and extract(month from fe_ing)<="+intMes;
//                        strSQL+=" order by tx_tiprub desc,co_rub asc";
//                        
//                        rstLoc=stmLoc.executeQuery(strSQL);
//                            while(rstLoc.next()){
//                                vecCab.add(vecCab.size(),rstLoc.getString("tx_nom"));
//                                
//                                intCanColTot++;
//                                if(rstLoc.getString("tx_tiprub").equals("I")){
//                                    intCanIng++;
//                                }else{
//                                    intCanEgr++;
//                                }
//                                
//                                arrLst.add(rstLoc.getString("co_rub"));
//                            }
//                    }
//            }catch(SQLException ex)
//            {
//                objUti.mostrarMsgErr_F1(this, ex);
//                return false;
//            }finally {
//                try{stmLoc.close();}catch(Throwable ignore){}
//                try{rstLoc.close();}catch(Throwable ignore){}
//                try{conIns.close();}catch(Throwable ignore){}
//           }
//            
//            vecCab.add(vecCab.size(),"Ingresos");
//            vecCab.add(vecCab.size(),"Egresos");
//            vecCab.add(vecCab.size(),"Total");
//            
//            System.out.println("tamaño de col : " + vecCab.size());
//            
//            objTblMod=new ZafTblMod();
//            objTblMod.setHeader(vecCab);
//            
//            //Configurar JTable: Establecer el modelo de la tabla.
//            tblDat.setModel(objTblMod);
//            //Configurar JTable: Establecer tipo de selección.
//            tblDat.setRowSelectionAllowed(true);
//            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//            //Configurar JTable: Establecer el menú de contexto.
//            objTblPopMnu=new ZafTblPopMnu(tblDat);
//            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//            //Configurar JTable: Establecer el ancho de las columnas.
//            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
//
//            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(80);
//            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(40);
//            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(220);
//            tcmAux.getColumn(INT_TBL_BUT_ANE).setPreferredWidth(20);
//            
//            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_LINEA).setResizable(false);
//            tcmAux.getColumn(INT_TBL_CODTRA).setResizable(false);
//            tcmAux.getColumn(INT_TBL_NOMTRA).setResizable(false);
//            tcmAux.getColumn(INT_TBL_BUT_ANE).setResizable(false);
//            
//            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//            tblDat.getTableHeader().setReorderingAllowed(false);
//            
//            //Configurar JTable: Establecer columnas editables.
//            Vector vecAux=new Vector();
//            vecAux.add("" + INT_TBL_BUT_ANE);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
//            
//            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMP, tblDat);
//            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            objMouMotAda=new ZafMouMotAda();
//            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
//            
//            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
//            
//            //Configurar JTable: Establecer la fila de cabecera.
//            objTblFilCab=new ZafTblFilCab(tblDat);
//            
//            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
//            
//            //cambios realizados a la tabla
//            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
//            
//            
//            //Configurar JTable: Renderizar celdas.
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
//            //objTblCelRenLbl=null;
//            
//            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
//
//            int intP=INT_TBL_BUT_ANE+1;
//            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            for (int i=0; i<1; i++)
//            {
//                for(int x=intP;x<tblDat.getColumnCount();x++){
//                    objTblMod.setColumnDataType(x+i, objTblMod.INT_COL_DBL, new Integer(0), null);
//                    tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
//                    tcmAux.getColumn(x+i).setCellRenderer(objTblCelRenLbl);
//                    tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
//                    
//                    
//                    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                        int intFilSel;//=tblDat.getSelectedRow();
//                        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                        }
//
//                        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                            
//                        }
//                    });
//                }
//            }
//            
//            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
//            tcmAux.getColumn(INT_TBL_BUT_ANE).setCellRenderer(zafTblDocCelRenBut);
//            
//            //Configurar JTable: Editor de celdas.
//            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
//            tcmAux.getColumn(INT_TBL_BUT_ANE).setCellEditor(objTblCelEdiButGen);
//            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                int intFilSel, intColSel;
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    
//                    }
//                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                     intFilSel=tblDat.getSelectedRow();
//                        if (intFilSel!=-1)
//                        {
//                            llamarRepNovEmp(objZafParSis,
//                                    objTblMod.getValueAt(intFilSel, INT_TBL_CODTRA).toString(),
//                                    objTblMod.getValueAt(intFilSel, INT_TBL_NOMTRA).toString(),
//                                    String.valueOf(cboPerAAAA.getSelectedIndex()),
//                                    String.valueOf(cboPerMes.getSelectedIndex()),
//                                    String.valueOf(cboPer.getSelectedIndex())
//                                    );
//                        }
//                 }
//                    
//           });
//            
//            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
//            int intTblCalTotColIni=INT_TBL_BUT_ANE+1;
//            int intCol[]= new int[objTblMod.getColumnCount()-INT_TBL_BUT_ANE-1];
//            int intColPos=0;
//            for(int i=intTblCalTotColIni;i<objTblMod.getColumnCount();i++){
//                intCol[intColPos]=i;
//                intColPos++;
//            }
//            
//
//            objTblTot=new ZafTblTot(scrTbl, spnTot, tblDat, tblTot, intCol);
//            
//            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblDat);
//            
//            //Inicializar las variables que indican cambios.
//            objTblMod.setDataModelChanged(false);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    private boolean configurarTabla(int intSel)
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            
            vecCab.add(INT_TBL_LINEA,"");
             vecCab.add(INT_TBL_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_CODTRA,"Código");
            vecCab.add(INT_TBL_NOMTRA,"Empleado");
            vecCab.add(INT_TBL_FEINICON,"Fec.Ini.Con");
            vecCab.add(INT_TBL_BUT_ANE,"");
            vecCab.add(INT_TBL_DIAS_LAB,"Dias. Lab.");
            vecCab.add(INT_TBL_ND_IESS,"Sue. IESS");
            vecCab.add(INT_TBL_HE_FI,"HE. Fi.");
            
            /*RUBROS INGRESOS DE MANERA DINAMICA*/
                      
            Connection conIns = null;
            Statement stmLoc = null;
            ResultSet rstLoc = null;
            
            intCanIng=0;
            intCanEgr=0;
            
            try{
                conIns =DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                
                
                
                    if(conIns!=null){
                        
                        stmLoc=conIns.createStatement();

                        arrLst = new ArrayList<String>();
                        strSQL="";

                        String strCodTipDoc="";
                        if (intSel==1){
                            strCodTipDoc="";
                        }
                        else if(intSel==2){
                            strCodTipDoc="199";
                        }
                        else if(intSel==3){
                            strCodTipDoc="202";
                        }
                        
                        switch(intSel){
                            
                                
                            
                            
                            case 1: //consulta lo actual

                                strSQL="select * from tbm_rubrolpag";
                                strSQL+=" where co_grp = 1";
                                break;
                             case 2:
                                 strSQL="select * from tbm_rubrolpag";
                                 strSQL+=" where co_grp = 2";
                                 break;
                            case 3:     
                                strSQL="select * from tbm_rubrolpag";
                                strSQL+=" where co_grp = 3";
                                break;
                            case 4:
                                strSQL="select * from tbm_rubrolpag";
                                break;

                        }
                        
                        strSQL+=" order by tx_tiprub desc,co_rub asc";
                        stmLoc=conIns.createStatement();
                        rstLoc=stmLoc.executeQuery(strSQL);
                            while(rstLoc.next()){
                                vecCab.add(vecCab.size(),rstLoc.getString("tx_nom"));
                                
                                intCanColTot++;
                                if(rstLoc.getString("tx_tiprub").equals("I")){
                                    intCanIng++;
                                }else{
                                    intCanEgr++;
                                }
                                arrLst.add(rstLoc.getString("co_rub"));
                            }
                    }
            }catch(SQLException ex)
            {
                objUti.mostrarMsgErr_F1(this, ex);
                return false;
            }finally {
                try{stmLoc.close();}catch(Throwable ignore){}
                try{rstLoc.close();}catch(Throwable ignore){}
                try{conIns.close();}catch(Throwable ignore){}
           }
            
            vecCab.add(vecCab.size(),"Ingresos");
            vecCab.add(vecCab.size(),"Egresos");
            vecCab.add(vecCab.size(),"Total");
            
            System.out.println("tamaño de columnas vecCab (todos los empleados deben de tener esta cantidad de columnas): " + vecCab.size());
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
//            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_CUOTA, objTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODTRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMTRA).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_FEINICON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_BUT_ANE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DIAS_LAB).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_ND_IESS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_HE_FI).setPreferredWidth(50);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LINEA).setResizable(false);
            tcmAux.getColumn(INT_TBL_CODTRA).setResizable(false);
//            tcmAux.getColumn(INT_TBL_NOMTRA).setResizable(false);
//            tcmAux.getColumn(INT_TBL_FEINICON).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(vecCab.size()-1, tblDat);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_CODEMP, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
//            vecAux.add("" + INT_TBL_BUT_ANE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            
            tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            /*objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);*/
            
            //cambios realizados a la tabla
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            //Configurar JTable: Renderizar celdas.
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODTRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DIAS_LAB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);  
            tcmAux.getColumn(INT_TBL_ND_IESS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_HE_FI).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);           
            //objTblCelRenLbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);

            int intP=INT_TBL_HE_FI+1;
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            for (int i=0; i<1; i++)
            {
                for(int x=intP;x<tblDat.getColumnCount();x++){
                    objTblMod.setColumnDataType(x+i, objTblMod.INT_COL_DBL, new Integer(0), null);
                    tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);
                    tcmAux.getColumn(x+i).setCellRenderer(objTblCelRenLbl);
                    tcmAux.getColumn(x+i).setCellEditor(objTblCelEdiTxt);

                    
                    
                    objTblCelRenLbl2=new ZafTblCelRenLbl();
                    objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.LEFT);
                    objTblCelRenLbl2.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                    objTblCelRenLbl2.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);     
////                    tcmAux.getColumn(INT_TBL_LINEA).setCellRenderer(objTblCelRenLbl2);
//                    tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl2);
////                    tcmAux.getColumn(INT_TBL_NOMEMP).setCellRenderer(objTblCelRenLbl2);
//                    tcmAux.getColumn(INT_TBL_CODTRA).setCellRenderer(objTblCelRenLbl2);
                    tcmAux.getColumn(INT_TBL_NOMTRA).setCellRenderer(objTblCelRenLbl2);
                    tcmAux.getColumn(INT_TBL_FEINICON).setCellRenderer(objTblCelRenLbl2);
////                    tcmAux.getColumn(INT_TBL_DIAS_LAB).setCellRenderer(objTblCelRenLbl);
//                    tcmAux.getColumn(INT_TBL_ND_IESS).setCellRenderer(objTblCelRenLbl2);
//                    tcmAux.getColumn(INT_TBL_HE_FI).setCellRenderer(objTblCelRenLbl2);
//                    tcmAux.getColumn(x+i).setCellRenderer(objTblCelRenLbl2);
//                    
                    objTblCelRenLbl2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                    public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                            
                            int intCell=objTblCelRenLbl2.getRowRender();

                            //                intCantRubAct
                            String str=tblDat.getValueAt(intCell, INT_TBL_LINEA).toString();
//
                            if(str.equals("S")){
                                objTblCelRenLbl2.setBackground(colFonCol);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }else {
                                objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }
                        }
                        public void afterRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                            //Mostrar de color gris las columnas impares.

                            int intCell=objTblCelRenLbl2.getRowRender();

                            //                intCantRubAct
                            String str=tblDat.getValueAt(intCell, INT_TBL_LINEA).toString();
//
                            if(str.equals("S")){
                                objTblCelRenLbl2.setBackground(colFonCol);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }else {
                                objTblCelRenLbl2.setBackground(java.awt.Color.WHITE);
                                objTblCelRenLbl2.setForeground(java.awt.Color.BLACK);
                            }
                        }
                    });
                    
                    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                        int intFilSel;//=tblDat.getSelectedRow();
                        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        }

                        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                            
                        }
                    });
                }
            }

            
//            objTblCelEdiTxtIng=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
//            for(int x=intP;x<tblDat.getColumnCount();x++){
//                
//            }
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT_ANE).setCellRenderer(zafTblDocCelRenBut);
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BUT_ANE).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                     intFilSel=tblDat.getSelectedRow();
                        if (intFilSel!=-1)
                        {
                            llamarRepNovEmp(objZafParSis,
                                    objTblMod.getValueAt(intFilSel, INT_TBL_CODTRA).toString(),
                                    objTblMod.getValueAt(intFilSel, INT_TBL_NOMTRA).toString(),
                                    String.valueOf(cboPerAAAA.getSelectedIndex()),
                                    String.valueOf(cboPerMes.getSelectedIndex()),
                                    String.valueOf(cboPer.getSelectedIndex())
                                    );
                        }
                 }
                    
           });
            
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intTblCalTotColIni=INT_TBL_HE_FI+1;
            int intCol[]= new int[objTblMod.getColumnCount()-INT_TBL_HE_FI-1];
            int intColPos=0;
            for(int i=intTblCalTotColIni;i<objTblMod.getColumnCount();i++){
                intCol[intColPos]=i;
                intColPos++;
            }
            JTableHeader obj = new JTableHeader();//tony se agregó para evitar null
            tblTot.setTableHeader(obj);
            objTblTot=new ZafTblTot(scrTbl, spnTot, tblDat, tblTot, intCol);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void llamarRepNovEmp(ZafParSis objZafParSis, String strCoTra, String strNomTra, String strNeAni, String strNeMes, String strNePer ){
        
        RecursosHumanos.ZafRecHum15.ZafRecHum15 obj1 = new  RecursosHumanos.ZafRecHum15.ZafRecHum15(objZafParSis, strCoTra, strNomTra, strNeAni, strNeMes, strNePer);
        this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
        
    }
    
    public void configuraVentanaConsulta(){
       
        //Configurar las ZafVenCon.
        configurarVenConDep();
        configurarVenConTra();
        configurarVenConEmp();
        configurarTabla(1);
        agregarColTblDat();
   }
    
   private boolean agregarColTblDat()
    {
        
        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        boolean blnRes=true;

        try
        {

            intNumFil=objTblMod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();

            for (i=0; i<1; i++)
            {
                int intCanColIng =0;
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
                /*
                L_CODEMP=1;
  private static final int INT_TBL_NOMEMP=2;
  private static final int INT_TBL_CODTRA=3;
  private static final int INT_TBL_NOMTRA=4;
  private static final int INT_TBL_FEINICON=5;
  private static final int INT_TBL_BUT_ANE=6;
                */
                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_LINEA));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODEMP));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_NOMEMP));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_CODTRA));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_NOMTRA));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_FEINICON));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_BUT_ANE));
                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Base");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
//                intCanColIng = INT_TBL_HE_FI+1+intCanIng;
//                for(int t=6;t<intCanColIng;t++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DIAS_LAB));
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_ND_IESS));
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_HE_FI));
//                }
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Ingresos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                
                intCanColIng = INT_TBL_HE_FI+1+intCanIng;
                for(int t=6;t<intCanColIng;t++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t+i*INT_TBL_DAT_NUM_TOT_CDI));
                }
                
                
                int intCanColEgr=intCanColIng+intCanEgr;
                
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Egresos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);


                for(int t=intCanColIng;t<intCanColEgr;t++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t+i*INT_TBL_DAT_NUM_TOT_CDI));
                }
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Totales");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);


                for(int t=intCanColEgr;t<tblDat.getColumnCount();t++){
                    objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(t+i*INT_TBL_DAT_NUM_TOT_CDI));
                }

            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
    }
  
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
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        panCab = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        cboPer = new javax.swing.JComboBox();
        cboRol = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        butEjeSer = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panAsiDia = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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
                formInternalFrameIconified(evt);
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

        buttonGroup1.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 100, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setText("Sólo los empleados que cumplan con el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 120, 370, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 140, 100, 20);

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
        txtCodEmp.setBounds(140, 140, 60, 20);

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
        txtNomEmp.setBounds(200, 140, 250, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(450, 140, 20, 20);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Rol:"); // NOI18N
        panCab.add(jLabel7);
        jLabel7.setBounds(10, 30, 110, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panCab.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 60, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panCab.add(cboPerMes);
        cboPerMes.setBounds(210, 60, 100, 20);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel9.setText("Período:"); // NOI18N
        panCab.add(jLabel9);
        jLabel9.setBounds(10, 60, 110, 20);

        cboPer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Período", "Primera quincena", "Fin de mes" }));
        panCab.add(cboPer);
        cboPer.setBounds(310, 60, 110, 20);

        panCab.add(cboRol);
        cboRol.setBounds(140, 30, 170, 20);

        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 100);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 160, 100, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(140, 160, 60, 20);

        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(200, 160, 250, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(450, 160, 20, 20);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(40, 180, 100, 20);

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
        txtCodTra.setBounds(140, 180, 60, 20);

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
        txtNomTra.setBounds(200, 180, 250, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(450, 180, 20, 20);

        butEjeSer.setText("Ejecutar Servicio");
        butEjeSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEjeSerActionPerformed(evt);
            }
        });
        panFil.add(butEjeSer);
        butEjeSer.setBounds(40, 230, 190, 23);

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
        scrTbl.setViewportView(tblDat);

        jPanel1.add(scrTbl, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 20));

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

        tabGen.addTab("Reporte", jPanel1);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabGen.addTab("Asiento de diario", panAsiDia);

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

        butVisPre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVisPre.setText("Vista Preliminar");
        butVisPre.setPreferredSize(new java.awt.Dimension(90, 23));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        jPanel5.add(butVisPre);

        butImp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butImp.setText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(90, 23));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        jPanel5.add(butImp);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        //consultarRepEmp();
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    public void consultarRepTra(){

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";

        try{
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if(conn!=null && txtCodTra.getText().compareTo("")!=0){

                stmLoc=conn.createStatement();

                strSql="SELECT tx_ape , tx_nom from tbm_tra where co_tra =  " + txtCodTra.getText();
                rstLoc=stmLoc.executeQuery(strSql);

                if(rstLoc.next()){
                    txtNomTra.setText(rstLoc.getString("tx_ape") + " " + rstLoc.getString("tx_nom"));
                    txtNomTra.setHorizontalAlignment(2);
                    txtNomTra.setEnabled(false);
                }else{
                   mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                   txtNomTra.setText("");
                   txtCodTra.setText("");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
            }
        }catch(java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
        catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }

    public void consultarRepEmp(){

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";

        try{

            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if(conn!=null && txtCodEmp.getText().compareTo("")!=0){

                stmLoc=conn.createStatement();

                strSql="SELECT co_emp , tx_nom from tbm_emp where co_emp =  " + txtCodEmp.getText() + " and st_reg like 'A' and co_emp not in (0) ";
                rstLoc=stmLoc.executeQuery(strSql);

                if(rstLoc.next()){
                    txtNomEmp.setText(rstLoc.getString("tx_nom"));
                    txtNomEmp.setHorizontalAlignment(2);
                    txtNomEmp.setEnabled(false);
                }else{
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomEmp.setText("");
                    txtCodEmp.setText("");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
            }
        }catch(java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void consultarRepDep(){

        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";

        try{

            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if(conn!=null && txtCodDep.getText().compareTo("")!=0){

                stmLoc=conn.createStatement();

                strSql="SELECT co_dep,tx_deslar from tbm_dep where co_dep =  " + txtCodDep.getText();
                rstLoc=stmLoc.executeQuery(strSql);

                if(rstLoc.next()){
                    txtNomDep.setText(rstLoc.getString("tx_deslar"));
                    txtNomDep.setHorizontalAlignment(2);
                    txtNomDep.setEnabled(false);
                }else{
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    txtNomDep.setText("");
                    txtCodDep.setText("");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
            }
        }catch(java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");
        }catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
            txtCodDep.setText("");
            txtNomDep.setText("");
            
        }
    }

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

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        //txtCodEmp.selectAll();
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
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
    
    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        //txtNomEmp.transferFocus();
        txtNomEmp.transferFocus();
        
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
    strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);

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
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configuraVentanaConsulta();
  }//GEN-LAST:event_formInternalFrameOpened

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        cargarReporte(1);
    }//GEN-LAST:event_butVisPreActionPerformed

    private void butCerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrActionPerformed
         exitForm();
}//GEN-LAST:event_butCerrActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (objThrGUI==null) {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        cargarReporte(0);
    }//GEN-LAST:event_butImpActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed

}//GEN-LAST:event_optFilActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed

        if(optTod.isSelected()){
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtNomDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
}//GEN-LAST:event_optTodItemStateChanged

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        // TODO add your handling code here:
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        // TODO add your handling code here:
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtNomDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo,String strBusqueda,int tipo){
        
        vcoDep.setTitle("Listado de Departamentos");
        if(vcoDep.buscar(campo, strBusqueda )) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton()==vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
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
    
    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed

        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
        strDesLarDep=txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
        if (txtNomDep.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep))
            {
                if (txtNomDep.getText().equals(""))
                {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                }
                else
                {
                    mostrarVenConDep(2);
                }
            }
            else
                txtNomDep.setText(strDesLarDep);
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep=txtCodDep.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        //consultarRepTra();
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
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
        optFil.setSelected(true);
        optTod.setSelected(false);
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
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameIconified

    private void butEjeSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEjeSerActionPerformed
        GregorianCalendar objCal = new GregorianCalendar();
        if (objCal.get(Calendar.DATE)!=1) {
            new RRHHDao(objUti, objZafParSis).callServicio9();
            mostrarMsgInf("Se ejecutará el servicio. Por favor espere unos minutos antes de consultar.");
        }else{
            mostrarMsgInf("Los primeros días de cada mes no se puede ejecutar el servicio. Para más información consulte a SISTEMAS.");
        }
        
    }//GEN-LAST:event_butEjeSerActionPerformed

    private class ZafThreadGUI extends Thread
    {
        public void run(){

            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            if(validaCampos()){
                if(cargarDat()){
                    if(tblDat.getRowCount()>0){
                        
                        lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                        calcularValorDocumento();
                        if(objZafParSis.getCodigoEmpresa()!=objZafParSis.getCodigoEmpresaGrupo()){
                            if(generaAsiento()){
                                if(!objAsiDia.isDiarioCuadrado()){
    //                                if(objZafParSis.getCodigoEmpresa()!=objZafParSis.getCodigoEmpresaGrupo()){

                                        llamarManCtaCon(String.valueOf(objZafParSis.getCodigoEmpresa()),
                                            String.valueOf(cboPerAAAA.getSelectedItem()),
                                            String.valueOf(cboPerMes.getSelectedIndex()),
                                            String.valueOf(cboPer.getSelectedIndex())
                                        );
    //                                }
                                }
                            }
                        }
                        objAsiDia.setEditable(true);
                    }
                }
            }else{
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
            }
            

            objThrGUI=null;
        }
    }
    
    private void llamarManCtaCon(String strCodEmp, String strNeAni, String strNeMes, String strNePer){
        RecursosHumanos.ZafRecHum37.ZafRecHum37_01 obj1 = new  RecursosHumanos.ZafRecHum37.ZafRecHum37_01(objZafParSis, strCodEmp, strNeAni, strNeMes, strNePer );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
    }

    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean validaCampos()
    {
        //Validar el "Año".
        int intPerAAAA=cboPerAAAA.getSelectedIndex();
        if(intPerAAAA<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año</FONT> es obligatorio.<BR>Seleccione el año y vuelva a intentarlo.</HTML>");
            limpiaDetalle();
            cboPerAAAA.requestFocus();
            return false;
        }
        //Validar el "Mes".
        int intPerMes=cboPerMes.getSelectedIndex();
        if(intPerMes<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes</FONT> es obligatorio.<BR>Seleccione el mes y vuelva a intentarlo.</HTML>");
            limpiaDetalle();
            cboPerMes.requestFocus();
            return false;
        }
        //Validar el "Período".
        int intPer=cboPer.getSelectedIndex();
        if(intPer<=0){
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Periodo</FONT> es obligatorio.<BR>Seleccione el período y vuelva a intentarlo.</HTML>");
            limpiaDetalle();
            cboPer.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Se encarga de cargar la informacion en la tabla
     * @return  true si esta correcto y false  si hay algun error
     */
    private boolean cargarDat(){

        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null; 
        String strSQL="", sqlAux="";
        
        intConIni=1;
        configurarTabla(cboRol.getSelectedIndex()+1);
        agregarColTblDat();


            try{
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {

                stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmLoc=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                
                int intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                int intMes=cboPerMes.getSelectedIndex();
                int intNePer=cboPer.getSelectedIndex();
                
                int intRolCon=cboRol.getSelectedIndex();
                
                if(!(txtCodEmp.getText().equals(""))){
                    sqlAux+=" AND c.co_emp="+txtCodEmp.getText()+" ";
                }

                if(!(txtCodDep.getText().equals(""))){
                      sqlAux+=" AND c.co_dep="+txtCodDep.getText()+" ";
                    }

                if(!(txtCodTra.getText().equals(""))){
                    sqlAux+=" AND c.co_tra="+txtCodTra.getText()+" ";
                }

                String strSqlDep="";
                if(objZafParSis.getCodigoUsuario()!=1){
                    strSqlDep="left outer join tbr_depprgusr g on(g.co_dep=c.co_dep and c.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objZafParSis.getCodigoUsuario()+" "+
                                             "and co_mnu="+objZafParSis.getCodigoMenu()+")) ";
                }

                String strSqlEmp="";
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    //strSqlEmp=" and d.co_emp not in (0,4) ";
                    strSqlEmp=" b.co_emp not in (0,3) ";
                }else{
                    //strSqlEmp=" and d.co_emp in ("+ objParSis.getCodigoEmpresa() +") ";
                    strSqlEmp=" b.co_emp in ("+ objZafParSis.getCodigoEmpresa() +") ";
                }

                
                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                    
                    strSQL="select distinct d.co_emp,d.tx_nom,a.co_tra, b.tx_ide, (b.tx_ape || ' ' || b.tx_nom) as empleado, c.tx_numCtaBan, c.tx_tipCtaBan, ";
                    strSQL+="f1.nd_valrub as sueldo, f2.nd_valrub as he_fi, f3.nd_valrub as nd_valBon, f4.nd_valrub as nd_valMov  ,e.ne_numdialab , f5.nd_porrubpag,  ";
                    strSQL+="case ( (EXTRACT (MONTH FROM fe_inicon)="+intMes+") AND (EXTRACT (YEAR FROM fe_inicon)="+intAño+") ) when true then true else false end as blnIsTraNew , c.fe_inicon ";
                    strSQL+="from tbm_ingEgrMenTra a ";
                    strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                    strSQL+=" inner join tbm_traemp c on (c.co_tra=b.co_tra  and c.co_emp=a.co_emp and c.st_reg='A')";
//                    strSQL+=" " + strSqlDep + " ";
                    strSQL+=" inner join tbm_emp d on (d.co_emp=c.co_emp)";
                    //strSQL+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" left outer join tbm_datgeningegrmentra e on (e.co_emp = a.co_emp and e.co_Tra = a.co_tra and e.ne_ani = a.ne_ani  and e.ne_mes = a.ne_mes and e.ne_per = 0)";
                    strSQL+=" inner join tbm_suetra f1 on (f1.co_emp = a.co_emp and f1.co_tra = a.co_tra and f1.co_rub = 1)";
                    strSQL+=" left outer join tbm_suetra f2 on (f2.co_emp = a.co_emp and f2.co_tra = a.co_tra and f2.co_rub = 2)";//**left
                    strSQL+=" left outer join tbm_suetra f3 on (f3.co_emp = a.co_emp and f3.co_tra = a.co_tra and f3.co_rub = 6)";
                    strSQL+=" left outer join tbm_suetra f4 on (f4.co_emp = a.co_emp and f4.co_tra = a.co_tra and f4.co_rub = 7)";
                    strSQL+=" left outer join tbm_ingEgrMenTra f5 on (f5.co_emp = a.co_emp and f5.co_tra = a.co_tra and f5.ne_ani = a.ne_ani  and f5.ne_mes = a.ne_mes and f5.ne_per = 1 and f5.co_rub=f1.co_rub)";
                    strSQL+=" where a.ne_ani = " + intAño;
                    strSQL+=" and a.ne_mes = " + intMes;
                    strSQL+=" and a.ne_per = " + intNePer;
                    strSQL+=" and e.ne_numdialab > 0 ";
                    strSQL+=" " + sqlAux + " ";
                    strSQL+=" order by b.tx_ape || ' ' || b.tx_nom asc, a.co_tra";
                    
                }else{
                    
                    strSQL="select distinct d.co_emp,d.tx_nom,a.co_tra, b.tx_ide, (b.tx_ape || ' ' || b.tx_nom) as empleado, c.tx_numCtaBan, c.tx_tipCtaBan, ";
                    strSQL+="f1.nd_valrub as sueldo, f2.nd_valrub as he_fi, f3.nd_valrub as nd_valBon, f4.nd_valrub as nd_valMov  ,e.ne_numdialab , f5.nd_porrubpag,  ";
                    strSQL+="case ( (EXTRACT (MONTH FROM fe_inicon)="+intMes+") AND (EXTRACT (YEAR FROM fe_inicon)="+intAño+") ) when true then true else false end as blnIsTraNew , c.fe_inicon ";
                    strSQL+="from tbm_ingEgrMenTra a ";
                    strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                    strSQL+=" inner join tbm_traemp c on (b.co_tra=c.co_tra  and a.co_emp=c.co_emp  and c.st_reg='A')";
//                    strSQL+=" " + strSqlDep + " ";
                    strSQL+=" inner join tbm_emp d on (d.co_emp=c.co_emp)";
                    strSQL+=" left outer join tbm_datgeningegrmentra e on (e.co_emp = a.co_emp and e.co_Tra = a.co_tra and e.ne_ani = a.ne_ani  and e.ne_mes = a.ne_mes and e.ne_per = 0)";
                    strSQL+=" inner join tbm_suetra f1 on (f1.co_emp = a.co_emp and f1.co_tra = a.co_tra and f1.co_rub = 1)";
                    strSQL+=" left outer join tbm_suetra f2 on (f2.co_emp = a.co_emp and f2.co_tra = a.co_tra and f2.co_rub = 2)";
                    strSQL+=" left outer join tbm_suetra f3 on (f3.co_emp = a.co_emp and f3.co_tra = a.co_tra and f3.co_rub = 6)";
                    strSQL+=" left outer join tbm_suetra f4 on (f4.co_emp = a.co_emp and f4.co_tra = a.co_tra and f4.co_rub = 7)";
                    strSQL+=" left outer join tbm_ingEgrMenTra f5 on (f5.co_emp = a.co_emp and f5.co_tra = a.co_tra and f5.ne_ani = a.ne_ani  and f5.ne_mes = a.ne_mes and f5.ne_per = 1 and f5.co_rub=f1.co_rub)";
                    strSQL+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                    strSQL+=" and a.ne_ani = " + intAño;
                    strSQL+=" and a.ne_mes = " + intMes;
                    strSQL+=" and a.ne_per = " + intNePer;
                    strSQL+=" and e.ne_numdialab > 0 ";
                    strSQL+=" " + sqlAux + " ";
                    strSQL+=" order by b.tx_ape || ' ' || b.tx_nom asc, a.co_tra";
                    
                }

                System.out.println("sql cargar rol per : "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat = new Vector();

                int intPosCol=INT_TBL_HE_FI+1;
                int intCont=0;


                while(rst.next()){


                    String strTxNomEmp=rst.getString("tx_nom");
                    
                    Tbm_traemp tbm_traemp = new Tbm_traemp();
                    tbm_traemp.setStrTx_Ide(rst.getString("tx_ide"));
                    tbm_traemp.setStrEmpleado(rst.getString("empleado"));
                    tbm_traemp.setStrTx_TipCtaBan(rst.getString("tx_tipCtaBan"));
                    tbm_traemp.setStrTx_NumCtaBan(rst.getString("tx_numCtaBan"));
                    arrLstTbmTraEmp.add(tbm_traemp);

                    String strGrp="";
                    if(intRolCon+1>=4){
                        strGrp=" )";
                    }else{
                        strGrp=" and co_grp="+(intRolCon+1)+")";
                    }
                    
                    strSQL="select a.*,(b.tx_ape || ' ' || b.tx_nom) as nomcom,c.tx_tiprub from tbm_ingEgrMenTra a";
                    strSQL+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                    strSQL+=" inner join tbm_rubrolpag c on (c.co_rub=a.co_rub " + strGrp + " ";
                    strSQL+=" where co_emp = " + rst.getString("co_emp");
                    strSQL+=" and a.co_tra = " + rst.getString("co_tra");
                    strSQL+=" and ne_ani = " + intAño;
                    strSQL+=" and ne_mes = " + intMes;
                    strSQL+=" and ne_per = " + intNePer;
                    //strSQL+=" order by co_rub asc";
                    strSQL+=" order by c.tx_tiprub desc,a.co_rub";
                    System.out.println("query obt rob rol pag : " + strSQL);
                    
                    rstLoc=stmLoc.executeQuery(strSQL);

                    double dblTotIng=0;
                    double dblTotEgr=0;
                    double dblBono=0;

                    if(rstLoc.next()){

                        Vector vecReg=new Vector();
                        Boolean bln=(Boolean)rst.getBoolean("blnIsTraNew");
                        if(bln){
                            vecReg.add(INT_TBL_LINEA,"S");
                        }else{
                            vecReg.add(INT_TBL_LINEA,"");
                        }
                        
                        vecReg.add(INT_TBL_CODEMP,rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_NOMEMP,strTxNomEmp);
                        vecReg.add(INT_TBL_CODTRA,rstLoc.getString("co_tra"));
                        vecReg.add(INT_TBL_NOMTRA,rstLoc.getString("nomcom"));
                        String fecha = objUti.formatearFecha(rst.getString("fe_inicon"),"yyyy-MM-dd","dd/MM/yyyy");
                        vecReg.add(INT_TBL_FEINICON,fecha);
                        vecReg.add(INT_TBL_BUT_ANE,"...");
                        
                        vecReg.add(INT_TBL_DIAS_LAB,rst.getInt("ne_numdialab"));
                            vecReg.add(INT_TBL_ND_IESS,rst.getDouble("sueldo"));
                            vecReg.add(INT_TBL_HE_FI,rst.getDouble("he_fi"));
                        
//                        if(cboRol.getSelectedIndex()==0){
//                            vecReg.add(INT_TBL_DIAS_LAB,rst.getInt("ne_numdialab"));
//                            vecReg.add(INT_TBL_ND_IESS,rst.getDouble("sueldo"));
//                            vecReg.add(INT_TBL_HE_FI,rst.getDouble("he_fi"));
//                        }
//                        else if(cboRol.getSelectedIndex()==1){
//                            if(rst.getDouble("nd_valBon")==0){
//                                vecReg.add(INT_TBL_DIAS_LAB,null);
//                            }else{
//                                vecReg.add(INT_TBL_DIAS_LAB,rst.getInt("ne_numdialab"));
//                            }
//                            vecReg.add(INT_TBL_ND_IESS,rst.getDouble("nd_valBon"));
//                            vecReg.add(INT_TBL_HE_FI,null);
//                        }
//                        else if(cboRol.getSelectedIndex()==2){
//                            if(rst.getDouble("nd_valMov")==0){
//                                vecReg.add(INT_TBL_DIAS_LAB,null);
//                            }else{
//                                vecReg.add(INT_TBL_DIAS_LAB,rst.getInt("ne_numdialab"));
//                            }
//                            vecReg.add(INT_TBL_ND_IESS,rst.getDouble("nd_valMov"));
//                            vecReg.add(INT_TBL_HE_FI,null);
//                        }

                        vecReg.add(intPosCol,rstLoc.getDouble("nd_valrub"));
                        dblTotIng+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                        intPosCol++;

                        while (rstLoc.next())
                            {
                                vecReg.add(intPosCol,rstLoc.getDouble("nd_valrub"));
                                if(rstLoc.getString("tx_tiprub").equals("I")){
                                    if(rstLoc.getInt("co_rub")==6){
                                        dblBono=rstLoc.getDouble("nd_valrub");
                                    }
                                    dblTotIng+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                }else if(rstLoc.getString("tx_tiprub").equals("E")){
                                    dblTotEgr+=objUti.redondear(objUti.parseDouble(rstLoc.getDouble("nd_valrub")), objZafParSis.getDecimalesMostrar());
                                }
                                
                                intCont++;
                                intPosCol++;
                            }

//                        vecReg.add(vecReg.size(),dblTotIng);
//                        intPosCol++;
//                        vecReg.add(vecReg.size(),dblTotEgr);
//                        vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(dblTotIng+dblTotEgr), objZafParSis.getDecimalesMostrar()));
//                        vecDat.add(vecReg);
                        vecReg.add(vecReg.size(),dblTotIng);
                            intPosCol++;
                            vecReg.add(vecReg.size(),dblTotEgr);
                            vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble(dblTotIng+dblTotEgr), objZafParSis.getDecimalesMostrar()));
                            vecReg.add(vecReg.size(),objUti.redondear(objUti.parseDouble((dblTotIng+dblTotEgr)-dblBono), objZafParSis.getDecimalesMostrar()));
                            vecReg.add(vecReg.size(),objUti.redondear("", objZafParSis.getDecimalesMostrar()));
                            vecReg.add(vecReg.size(),objUti.redondear("", objZafParSis.getDecimalesMostrar()));
                            vecDat.add(vecReg);
                    }
                    intPosCol=INT_TBL_HE_FI+1;
                }

                if(vecDat.size()==0){
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    limpiaDetalle();
                    lblMsgSis.setText("Listo");
                }else{
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    objTblTot.calcularTotales();
                    tabGen.setSelectedIndex(1);
                    lblMsgSis.setText("Listo");
                }
                
                pgrSis.setIndeterminate(false);
                vecDat.clear();
                }
        }catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{rst.close();}catch(Throwable ignore){}
            try{stm.close();}catch(Throwable ignore){}
            try{con.close();}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    private void calcularValorDocumento(){
        
        dblValDoc=objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount()-1)), objZafParSis.getDecimalesMostrar());
        System.out.println("hola....");
    }
    
    private boolean generaAsiento(){
        
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        
        String strTipDocDes="";
        
        BigDecimal bgdDebe, bgdHaber;
        int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE   = 5; //5) Debe. 
        int INT_VEC_HABER  = 6; //6) Haber. 
        int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO    = 8;
        
//        int intEst=0;
        int intColFij=INT_TBL_HE_FI;
        int intColDin=INT_TBL_HE_FI;
        
        try
        {
        
            objAsiDia.inicializar();
            
            if (vecDetDiario==null) vecDetDiario = new java.util.Vector();
             else vecDetDiario.removeAllElements();
            
            
            java.util.Vector vecReg = new Vector();

            String strMes=cboPerMes.getSelectedItem().toString().toUpperCase();
            String strAño=cboPerAAAA.getSelectedItem().toString().toUpperCase();
            
            String strCodTipDoc="";
                if(cboRol.getSelectedIndex()+1==1){
                    strCodTipDoc="192";
                }else if(cboRol.getSelectedIndex()+1==2){
                    strCodTipDoc="199";
                }else if(cboRol.getSelectedIndex()+1==3){
                    strCodTipDoc="202";
                }
                else if(cboRol.getSelectedIndex()+1==4){
                    strCodTipDoc="199";
                }
            
            if(strCodTipDoc.compareTo("192")==0){
                strTipDocDes="PAGOS";
            }else if(strCodTipDoc.compareTo("199")==0){
                strTipDocDes="BONO";
            }else if(strCodTipDoc.compareTo("202")==0){
                strTipDocDes="MOVILIZACIÓN";
            }
            
            if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                
                objAsiDia.setGlosa("ROL DE "+strTipDocDes+" - 1ERA QUINCENA "+strMes+"/"+strAño+"");
                mostrarCtaPre(1);
            }else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){
                objAsiDia.setGlosa("ROL DE "+strTipDocDes+" - 2DA QUINCENA "+strMes+"/"+strAño+"");
                mostrarCtaPre(2);
            }
            
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
            
                stm=con.createStatement();
                stmLoc=con.createStatement();

                if(cboPer.getSelectedItem().toString().equals("Primera quincena")){
                    
                    /*segmentar personas que se realizan anticipos con cheque..*/
                    //int intPer=cboPer.getSelectedIndex();
                    
                    String strCoCtaDeb="";
                    String strNumCtaDeb="";
                    String strDesLarCtaDeb="";
                    
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctadeb";
                    strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + strCodTipDoc;
                    strSQL+=" AND a2.st_reg='A'";
                    rst=stm.executeQuery(strSQL);
                    
                    if(rst.next()){
                        strCoCtaDeb=rst.getString("co_cta");
                        strNumCtaDeb=rst.getString("tx_codcta");
                        strDesLarCtaDeb=rst.getString("tx_deslar");
                    }
                    
                    String strSql="";
                    BigDecimal bgdTotNInc=BigDecimal.ZERO;//controlar valores que no incluyen en bancos
                    BigDecimal bgddblTot=BigDecimal.ZERO;
                    
                    for(int intFil=0; intFil<tblDat.getRowCount(); intFil++){

                        bgdTotNInc=BigDecimal.ZERO;
                        strSql="";
                        strSql+="select tx_numctaban from tbm_traemp";
                        strSql+=" where co_tra="+tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql+=" and co_emp="+tblDat.getValueAt(intFil, INT_TBL_CODEMP).toString();
                        strSql+=" and tx_numctaban is null";
                        System.out.println("comp: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);

                        if(rstLoc.next()){
                            if(objZafParSis.getCodigoMenu()==3138){
                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount()-1).toString(), objZafParSis.getDecimalesMostrar()));
                            }else{
                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount()-1).toString(), objZafParSis.getDecimalesMostrar()));
                            }
                            
                            bgddblTot=bgddblTot.add(bgdTotNInc);

//                            vecReg = new Vector();
//                            vecReg.add(INT_LINEA, null);
//                            vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
//                            vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
//                            vecReg.add(INT_VEC_BOTON, null);
//                            vecReg.add(INT_VEC_NOMCTA,  strDesLarCtaDeb);
//
//                            vecReg.add(INT_VEC_DEBE, bgdTotNInc);
//                            vecReg.add(INT_VEC_HABER, new BigDecimal(0));
//                            System.err.println(tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString());
//                            vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada" );
//                            vecReg.add(INT_VEC_NUEVO, null);
//                            vecDetDiario.add(vecReg);

                        }
                    }
                    
                    //cuenta debe.. anticipos.. diferencia de las personas que si tienen cuenta bancaria...
                    
                    BigDecimal bgdValDoc= new  BigDecimal(dblValDoc);
                    bgdValDoc=bgdValDoc.subtract(bgddblTot);

                    vecReg=new Vector();
                    vecReg.add(INT_LINEA, null);
                    vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
                    vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA,  strDesLarCtaDeb);

//                    vecReg.add(INT_VEC_DEBE, bgdValDoc);
                    vecReg.add(INT_VEC_DEBE, dblValDoc);
                    vecReg.add(INT_VEC_HABER, new BigDecimal(0));
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);
                    
                    
                     for(int intFil=0; intFil<tblDat.getRowCount(); intFil++){
//
                        bgdTotNInc=BigDecimal.ZERO;
                        strSql="";
                        strSql+="select tx_numctaban from tbm_traemp";
                        strSql+=" where co_tra="+tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                        strSql+=" and co_emp="+tblDat.getValueAt(intFil, INT_TBL_CODEMP).toString();
                        strSql+=" and tx_numctaban is null";
                        System.out.println("comp: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);

                        if(rstLoc.next()){
                            if(objZafParSis.getCodigoMenu()==3138){
                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount()-1).toString(), objZafParSis.getDecimalesMostrar()));
                            }else{
                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, objTblMod.getColumnCount()-1).toString(), objZafParSis.getDecimalesMostrar()));
                            }
                            
                            bgddblTot=bgddblTot.add(bgdTotNInc);

                            vecReg = new Vector();
                            vecReg.add(INT_LINEA, null);
                            vecReg.add(INT_VEC_CODCTA, strCoCtaDeb);
                            vecReg.add(INT_VEC_NUMCTA, strNumCtaDeb);
                            vecReg.add(INT_VEC_BOTON, null);
                            vecReg.add(INT_VEC_NOMCTA,  strDesLarCtaDeb);

                            vecReg.add(INT_VEC_DEBE, new BigDecimal(0));
                            vecReg.add(INT_VEC_HABER, bgdTotNInc);
                            System.err.println(tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString());
                            vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada" );
                            vecReg.add(INT_VEC_NUEVO, null);
                            vecDetDiario.add(vecReg);

                        }
                    }
                    
                    //cuenta bancos...haber
                    
                    vecReg=new Vector();
                    vecReg.add(INT_LINEA, null);
                    vecReg.add(INT_VEC_CODCTA, strCodCta);
                    vecReg.add(INT_VEC_NUMCTA, strDesCorCta);
                    vecReg.add(INT_VEC_BOTON, null);
                    vecReg.add(INT_VEC_NOMCTA,  strDesLarCta);

                    vecReg.add(INT_VEC_DEBE, new Double(0));
                    vecReg.add(INT_VEC_HABER, bgdValDoc );
                    vecReg.add(INT_VEC_REF, null);
                    vecReg.add(INT_VEC_NUEVO, null);
                    vecDetDiario.add(vecReg);
                    

                    objAsiDia.setDetalleDiario(vecDetDiario);
                }

                else if(cboPer.getSelectedItem().toString().equals("Fin de mes")){


                        for(Iterator<String> itLstRub = arrLst.iterator();itLstRub.hasNext();){

                            String strCodRub = itLstRub.next();
                            intColDin=intColDin+1;

                            String strSql="";
                            strSql+="select distinct a.co_cta,b.tx_deslar,b.tx_codcta,b.co_emp,c.tx_tiprub from tbm_suetra a ";
                            strSql+=" inner join tbm_placta b on (a.co_emp=b.co_emp and a.co_cta=b.co_cta)";
                            strSql+=" inner join tbm_rubrolpag c on (c.co_rub="+strCodRub+")";
                            if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                                strSql+=" where a.co_rub = " + strCodRub;
                                strSql+=" and a.co_cta is not null";
                                strSql+=" order by tx_codcta asc";
                            }else{
                                strSql+=" where a.co_emp = " + objZafParSis.getCodigoEmpresa();
                                strSql+=" and a.co_rub = " + strCodRub;
                                strSql+=" and a.co_cta is not null";
                                strSql+=" order by tx_codcta asc";
                            }
                            
                            System.out.println("asiento diario ctas rub "+ strCodRub + "   ---->  " + strSql);
                            rst=stm.executeQuery(strSql);

                            while (rst.next()){


                                bgdDebe=BigDecimal.ZERO;
                                vecReg = new Vector();
                                vecReg.add(INT_LINEA, null);
                                vecReg.add(INT_VEC_CODCTA, rst.getString("co_cta"));
                                vecReg.add(INT_VEC_NUMCTA, rst.getString("tx_codcta"));
                                vecReg.add(INT_VEC_BOTON, null);
                                vecReg.add(INT_VEC_NOMCTA,  rst.getString("tx_deslar"));

                                int intFil=0;
                                for(intFil=0; intFil<tblDat.getRowCount(); intFil++){


                                    strSql="select * from tbm_suetra where co_emp = " + rst.getString("co_emp");
                                    strSql+=" and co_rub = " + strCodRub;
                                    strSql+=" and co_cta = " + rst.getString("co_cta");
                                    strSql+=" and co_tra = " + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                                    System.out.println("comprob si cod_tra: " + tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString() + " pertenece a la cta : " + rst.getString("co_cta") + "   --------->  "+strSql);
                                    rstLoc = stmLoc.executeQuery(strSql);

                                    if(rstLoc.next()){
                                        bgdDebe=bgdDebe.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, intColDin).toString(), objZafParSis.getDecimalesMostrar()));
                                    }
                                }

                                System.out.println("intFil: " + intFil);

                                if(bgdDebe.signum()==1){
                                    vecReg.add(INT_VEC_DEBE, bgdDebe);
                                    vecReg.add(INT_VEC_HABER, new Double(0));
                                    vecReg.add(INT_VEC_REF, null);
                                    vecReg.add(INT_VEC_NUEVO, null);
                                    vecDetDiario.add(vecReg);
                                }else if (bgdDebe.signum()==-1){
                                    vecReg.add(INT_VEC_DEBE, new Double(0));
                                    vecReg.add(INT_VEC_HABER, bgdDebe.multiply(BigDecimal.valueOf(-1)));
                                    vecReg.add(INT_VEC_REF, null);
                                    vecReg.add(INT_VEC_NUEVO, null);
                                    vecDetDiario.add(vecReg);
                                }
                            }
                        }

                        String strSql="";
                        BigDecimal bgdTotNInc=BigDecimal.ZERO;//controlar valores que no incluyen en bancos
                        BigDecimal bgddblTot=BigDecimal.ZERO;

                        for(int intFil=0; intFil<tblDat.getRowCount(); intFil++){

                            bgdTotNInc=BigDecimal.ZERO;
                            strSql="";
                            strSql+="select tx_numctaban from tbm_traemp";
                            strSql+=" where co_tra="+tblDat.getValueAt(intFil, INT_TBL_CODTRA).toString();
                            strSql+=" and co_emp="+tblDat.getValueAt(intFil, INT_TBL_CODEMP).toString();
                            strSql+=" and tx_numctaban is null";
                            System.out.println("comp: " + strSql);
                            rstLoc = stmLoc.executeQuery(strSql);

                            if(rstLoc.next()){

                                bgdTotNInc=bgdTotNInc.add(objUti.redondearBigDecimal(tblDat.getValueAt(intFil, tblDat.getColumnCount()-1).toString(), objZafParSis.getDecimalesMostrar()));
                                bgddblTot=bgddblTot.add(bgdTotNInc);

                                vecReg = new Vector();
                                vecReg.add(INT_LINEA, null);
                                vecReg.add(INT_VEC_CODCTA, strCodCta);
                                vecReg.add(INT_VEC_NUMCTA, strDesCorCta);
                                vecReg.add(INT_VEC_BOTON, null);
                                vecReg.add(INT_VEC_NOMCTA,  strDesLarCta);

                                vecReg.add(INT_VEC_DEBE, new Double(0));
                                vecReg.add(INT_VEC_HABER, bgdTotNInc);
                                vecReg.add(INT_VEC_REF, "Se realiza el pago a: " + tblDat.getValueAt(intFil, INT_TBL_NOMTRA).toString() + " con cheque porque no tiene cuenta bancaria asignada" );
                                vecReg.add(INT_VEC_NUEVO, null);
                                vecDetDiario.add(vecReg);
                            }
                        }


                        //BigDecimal bgdValDoc= new  BigDecimal(txtValDoc.getText());
                        //bgdValDoc=bgdValDoc.subtract(bgddblTot);

                        vecReg=new Vector();
                        vecReg.add(INT_LINEA, null);
                        vecReg.add(INT_VEC_CODCTA, strCodCta);
                        vecReg.add(INT_VEC_NUMCTA, strDesCorCta);
                        vecReg.add(INT_VEC_BOTON, null);
                        vecReg.add(INT_VEC_NOMCTA,  strDesLarCta);

                        vecReg.add(INT_VEC_DEBE, new Double(0));
                        
//                        vecReg.add(INT_VEC_HABER, objUti.redondear(objUti.parseDouble(objTblTot.getValueAt(0, objTblMod.getColumnCount())), objZafParSis.getDecimalesMostrar()));
                        BigDecimal bgdValDoc= new  BigDecimal(dblValDoc);
                        bgdValDoc=bgdValDoc.subtract(bgddblTot);
                        vecReg.add(INT_VEC_HABER, bgdValDoc);
                        vecReg.add(INT_VEC_REF, null);
                        vecReg.add(INT_VEC_NUEVO, null);
                        vecDetDiario.add(vecReg);

                        objAsiDia.setDetalleDiario(vecDetDiario);
                
                }
            }
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
            
        }finally {
            try{rst.close();rst=null;}catch(Throwable ignore){}
            try{stm.close();stm=null;}catch(Throwable ignore){}
            try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    private void limpiaDetalle(){
        objTblMod.removeAllRows();
        for(int i=0;i<tblTot.getColumnCount();i++){
            objTblTot.setValueAt("", 0, i);
        }
    }
    
    private String getDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    } 

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salida.
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
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEjeSer;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butTra;
    private javax.swing.JButton butVisPre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPer;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JComboBox cboRol;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomDep;
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
                case INT_TBL_CODEMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_NOMEMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_CODTRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_NOMTRA:
                    strMsg="Nombres y Apellidos del empleado";
                    break;
                case INT_TBL_FEINICON:
                    strMsg="Fecha de inicio del contrato";
                    break;
                case INT_TBL_BUT_ANE:
                    strMsg="Muestra las \"Novedades de la quincena y del fin de mes\"";
                    break;
                case INT_TBL_DIAS_LAB:
                    strMsg="Días laborados por el empleado";
                    break;
                case INT_TBL_ND_IESS:
                    strMsg="Sueldo IESS";
                    break;
                case INT_TBL_HE_FI:
                    strMsg="Horas Extras Fijas";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }

    private void cargarReporte(int intTipo){
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
                   //google objTooBar.setEnabledVistaPreliminar(true);
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
        String strRutRpt, strNomRpt, strFecHorSer="", strCamAudRpt = "";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String strSql = "", sqlFil = "", sqlAux = "";
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
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {

                            default:

                                //Inicializar los parametros que se van a pasar al reporte.

                                //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }

                                int intAño=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                                int intMes=cboPerMes.getSelectedIndex();
                                int intNePer=cboPer.getSelectedIndex();
                                                               
                                if(!(txtCodEmp.getText().equals(""))){
                                    sqlAux+=" AND c.co_emp="+txtCodEmp.getText()+" ";
                                }

                                if(!(txtCodDep.getText().equals(""))){
                                      sqlAux+=" AND c.co_dep="+txtCodDep.getText()+" ";
                                    }

                                if(!(txtCodTra.getText().equals(""))){
                                    sqlAux+=" AND c.co_tra="+txtCodTra.getText()+" ";
                                }

                                String strSqlDep="";
                                if(objZafParSis.getCodigoUsuario()!=1){
                                    strSqlDep="left outer join tbr_depprgusr g on(g.co_dep=c.co_dep and c.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objZafParSis.getCodigoUsuario()+" "+
                                                             "and co_mnu="+objZafParSis.getCodigoMenu()+")) ";
                                }

                                String strSqlEmp="";
                                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                                    strSqlEmp=" b.co_emp not in (0,3) ";
                                }else{
                                    strSqlEmp=" b.co_emp in ("+ objZafParSis.getCodigoEmpresa() +") ";
                                }

                               
                                String strGrp="";
                                if(cboRol.getSelectedIndex()+1>=4){
                                    strGrp=" )";
                                }else{
                                    strGrp=" and co_grp="+(cboRol.getSelectedIndex()+1)+")";
                                }
                                
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v1.0    ";

                                if(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()){
                   
                                    if(objZafParSis.getCodigoUsuario()==1){
                                        strSql="";
                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and a.co_emp=c.co_emp and c.st_reg='A')";
                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub " + strGrp + " ";
                                        strSql+=" where a.ne_ani="+intAño;
                                        strSql+=" and a.ne_mes="+intMes;
                                        strSql+=" and a.ne_per="+intNePer;
                                        strSql+=" " + sqlAux + " ";
                                    }else{
                                        
                                        strSql="";
                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and a.co_emp=c.co_emp and c.st_reg='A')";
                                        strSql+=" " + strSqlDep + " ";
                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub " + strGrp + " ";
                                        strSql+=" where a.ne_ani="+intAño;
                                        strSql+=" and a.ne_mes="+intMes;
                                        strSql+=" and a.ne_per="+intNePer;
                                        strSql+=" " + sqlAux + " ";
                                    }
                                }else{
                    
                                    if(objZafParSis.getCodigoUsuario()==1){
                                        strSql="";
                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and c.st_reg='A' and a.co_emp=c.co_emp and c.co_emp="+objZafParSis.getCodigoEmpresa()+")";
                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub  " + strGrp + " ";
                                        strSql+=" where a.ne_ani="+intAño;
                                        strSql+=" and a.ne_mes="+intMes;
                                        strSql+=" and a.ne_per="+intNePer;
                                        strSql+=" " + sqlAux + " ";
                                    }else{
                                        
                                        strSql="";
                                        strSql="select c.co_emp, c.co_tra, c.co_dep, a.ne_ani, a.ne_mes, a.ne_per, d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub, (b.tx_ape ||' '|| b.tx_nom) as empleado,b.tx_ape as apellido,b.tx_nom as nombre  from tbm_ingegrmentra a";
                                        strSql+=" inner join tbm_tra b on (a.co_tra=b.co_tra)";
                                        strSql+=" inner join tbm_traemp c on (a.co_tra=c.co_tra and c.st_reg='A' and a.co_emp=c.co_emp and c.co_emp="+objZafParSis.getCodigoEmpresa()+")";
                                        strSql+=" " + strSqlDep + " ";
                                        strSql+=" inner join tbm_rubrolpag d on (a.co_rub=d.co_rub " + strGrp + " ";
                                        strSql+=" where a.ne_ani="+intAño;
                                        strSql+=" and a.ne_mes="+intMes;
                                        strSql+=" and a.ne_per="+intNePer;
                                        strSql+=" " + sqlAux + " ";
                                    }
                                }
                                
                                strSql+=" group by a.co_emp,c.co_emp,a.co_rub,c.co_tra,c.co_dep,a.ne_ani,a.ne_mes,a.ne_per,d.co_rub,d.tx_nom,d.tx_tiprub,a.nd_valrub,b.tx_ape,b.tx_nom";
                                //strSql+=" order by a.co_emp, a.ne_ani, a.ne_mes, empleado,a.co_rub,tx_tiprub desc";
                                strSql+=" order by a.co_emp, a.ne_ani, a.ne_mes, empleado,tx_tiprub desc";
                                
                                
                                String strQFDS="";
                                if(intNePer==1){
                                    strQFDS="Primera Quincena";
                                }else if(intNePer==2){
                                    strQFDS="Segunda Quincena";
                                }
                                
                                String strPeriodo=intAño+" - "+ cboPerMes.getSelectedItem().toString() + " - " + strQFDS;
                                
                                System.out.println("vista reporte:" +strSql);
                                
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strsql", strSql);
                                mapPar.put("empresa", objZafParSis.getNombreEmpresa() );
                                mapPar.put("periodo", strPeriodo);
                                mapPar.put("fecha", datFecAux);
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                //mapPar.put("prueba",vecDataCon);
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
        }catch (Exception e){
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
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
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
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
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
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
                    if (vcoTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
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
                        if (vcoTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
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
                        if (vcoTra.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
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
            arlAncCol.add("414");
            
            //Armar la sentencia SQL.
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConDep(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
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

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            arlAncCol.add("40");
            
            String strSQL="";
            if(objZafParSis.getCodigoUsuario()==1){
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objZafParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objZafParSis.getCodigoMenu()+")";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            vcoDep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' and co_emp = "+ objZafParSis.getCodigoEmpresa() + " " +
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarCtaPre(int intPer)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String strCodTipDoc="";
                if(cboRol.getSelectedIndex()+1==1){
                    strCodTipDoc="192";
                }else if(cboRol.getSelectedIndex()+1==2){
                    strCodTipDoc="199";
                }else if(cboRol.getSelectedIndex()+1==3){
                    strCodTipDoc="202";
                }else if(cboRol.getSelectedIndex()+1==4){
                    strCodTipDoc="199";
                }
                
                switch (intPer){
                    case 1:
                        
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                        strSQL+=" FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctahab";
                        strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                        strSQL+=" AND a2.co_tipDoc=" + strCodTipDoc;
                        strSQL+=" AND a2.st_reg='A'";

                        
                        break;
                        
                    case 2:
                                               
                        
                        strSQL="";
                        strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                        strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" AND a2.co_emp=" + objZafParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_loc=" + objZafParSis.getCodigoLocal();
                        strSQL+=" AND a2.co_tipDoc=" + strCodTipDoc;
                        strSQL+=" AND a2.st_reg='S'";
                        
                }
                
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strCodCta=rst.getString("co_cta");
                    strDesCorCta=rst.getString("tx_codCta");
                    strDesLarCta=rst.getString("tx_desLar");
                    switch (intPer){
                        case 2:
                            strUbiCta=rst.getString("tx_ubiCta");
                            break;
                    }
                    
                    
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
    
}
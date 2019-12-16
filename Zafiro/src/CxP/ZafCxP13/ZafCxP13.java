/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxP13.java
 *
 * Created on Sep 28, 2017, 14:58:00
 */
  
package CxP.ZafCxP13;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
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
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Dennis Betancourt
 * 
 * Cambio de beneficiario.
 */
public class ZafCxP13 extends javax.swing.JInternalFrame {
  private ZafParSis objZafParSis;
  private ZafUtil objUti; /**/
  private ZafTblMod objTblMod; /**/
  private ZafTblCelRenLbl objTblCelRenLbl;
  private ZafTblCelRenBut objTblCelRenBut;
  private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
  private ZafTblPopMnu objTblPopMnu;
 
  private ZafVenCon objVenConTipdoc;
  private ZafCxP13_VenCon objCon64_VenCon;
  private Connection CONN_GLO=null;
  private Statement  STM_GLO=null;

  private String strVersion=" v0.1.1 ";
  private String strCodTipDoc="";
  private String strDesCodTipDoc="";
  private String strDesLarTipDoc="";
  
  private static final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
  private static final int INT_TBL_BUTCLI=1; // BUTON PARA BUSCAR DOCUMENTO
  private static final int INT_TBL_CODCLI=2; // CODIGO CLIENTE
  private static final int INT_TBL_NOMCLI=3; // NOMBRE CLIENTE
  private static final int INT_TBL_CODBEN=4; // CODIGO BENEFICIARIO
  private static final int INT_TBL_NOMBEN=5; // NOMBRE BENEFICIARIO
  private static final int INT_TBL_CODEMP=6; // CODIGO EMPRESA
  private static final int INT_TBL_CODLOC=7; // CODIGO DEL LOCAL
  private static final int INT_TBL_CODTID=8; // CODIGO TIPO DE DOCUMENTO
  private static final int INT_TBL_CODDOC=9; // CODIGO DOCUMENTO
  private static final int INT_TBL_DCTIPDOC=10; // DESCRIPCION CORTA TIPO DOCUMENTO
  private static final int INT_TBL_DLTIPDOC=11; // DESCRIPCION LARGA TIPO DOCUMENTO
  private static final int INT_TBL_NUMDOC=12; // NUMERO DOCUMENTO
  private static final int INT_TBL_FECDOC=13; // FECHA DOCUMENTO
  private static final int INT_TBL_VALDOC=14; // VALOR DOCUMENTO
  
  private JTextField txtCodTipDoc= new JTextField();
  private Vector vecCab=new Vector();    //Almacena las cabeceras  /**/
  private boolean blnHayCam=false;  
  private String strCodEmp="";
  private String strCodLoc="";
  private String strCodTip="";
  private String strCodDoc="";

    /** Creates new form ZafCxC01 */
    public ZafCxP13(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }


    public ZafCxP13(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodMnu) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();

            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            objZafParSis.setCodigoMenu(intCodMnu);

	     objUti = new ZafUtil(); /**/
	    
             strCodEmp= String.valueOf( intCodEmp );
             strCodLoc= String.valueOf( intCodLoc );
             strCodTip= String.valueOf( intCodTipDoc );
             strCodDoc= String.valueOf( intCodDoc );
	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }

public void Configura_ventana_consulta(){

    boolean blnIsTuv_Cas_Dim;
   
    configurarVenConTipDoc();
    configurarVenConDoc();
    ConfigurarTabla();
    
    objUti.activarCom(this);
    java.awt.Color colBack;
    colBack = txtNueBen.getBackground();
    txtNueBen.setEditable(false);
    txtNueBen.setBackground(colBack);
    
    blnIsTuv_Cas_Dim = (objZafParSis.getNombreEmpresa().toUpperCase().indexOf("TUVAL") != -1
                || objZafParSis.getNombreEmpresa().toUpperCase().indexOf("CASTEK") != -1
                || objZafParSis.getNombreEmpresa().toUpperCase().indexOf("DIMULTI") != -1)? true: false;
    
    if (blnIsTuv_Cas_Dim == true)
    {  //Si viene por aqui, la empresa es Tuval, Castek o Dimulti.
       txtNueBen.setText(objZafParSis.getNombreEmpresa());
    }
    else
    {  //Si viene por aqui, la empresa es Cosenco, Ecuatosa o Detopacio.
       txtNueBen.setText("");
    }
    
    setEditable(true);
    objTblMod.setDataModelChanged(false);
}

private boolean configurarVenConDoc(){
 boolean blnRes=true;
 try{
    objCon64_VenCon=new ZafCxP13_VenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de documentos sin autorización de pago", txtCodTipDoc.getText());
    objCon64_VenCon.setTipoConsulta();
  }catch (Exception e){   blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_BUTCLI,"");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli");
        vecCab.add(INT_TBL_NOMCLI,"Nom.CLi");
        vecCab.add(INT_TBL_CODBEN,"Cód.Ben");
        vecCab.add(INT_TBL_NOMBEN,"Nom.Ben");
        vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
        vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
        vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
        vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
        vecCab.add(INT_TBL_DCTIPDOC,"Des.Cor");
        vecCab.add(INT_TBL_DLTIPDOC,"Des.Lar");
        vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
        vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
        vecCab.add(INT_TBL_VALDOC,"Val.Doc");

	objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  /**/
        TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_VALDOC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALDOC).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        ZafMouMotAda objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_BUTCLI).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_CODBEN).setPreferredWidth(40);
        tcmAux.getColumn(INT_TBL_NOMBEN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);

        /* Aqui se agrega las columnas que van a ocultar
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_CODTID);
        arlColHid.add(""+INT_TBL_CODDOC);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_BUTCLI);
        //vecAux.add("" + INT_TBL_NUMDOC);

        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        
        objTblCelRenBut=new ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTCLI).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;
        
          //------------------------------------------------------------------
    //Eddye_ventana de documentos pendientes//
    objTblCelEdiButGen=new ZafTblCelEdiButGen();
    tblDat.getColumnModel().getColumn(INT_TBL_BUTCLI).setCellEditor(objTblCelEdiButGen);
    objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
        @Override
        public void actionPerformed(ZafTableEvent evt) {
            int intFilSel, intFilSelVenCon[], i, j;
            String strCodCli, strNomCli, strValDoc;
            String strCodBen, strNomBen, strDesCorTipDoc, strNumDoc, strFecDoc;

            int intNumFil = tblDat.getSelectedRow();
            int intConFil=0;
            int x1 =  intNumFil-1;

            if (tblDat.getSelectedColumn()==INT_TBL_BUTCLI)
            {   if (txtCodTipDoc.getText().equals(""))
                {  MensajeInf("Primero debe seleccionar un Tipo de Documento antes de pulsar el botón de búsqueda de Clientes");
                   txtDesCodTitpDoc.requestFocus();
                   return;
                }
                configurarVenConDoc();
                objCon64_VenCon.setVisible(true);
            }
            if (objCon64_VenCon.getSelectedButton()==ZafCxP13_VenCon.INT_BUT_ACE)
            {
                abrirCon();
                intFilSel=tblDat.getSelectedRow();
                intFilSelVenCon=objCon64_VenCon.getFilasSeleccionadas();
                strCodCli=objCon64_VenCon.getCodigoCliente();
                strNomCli=objCon64_VenCon.getNombreCliente();
                j=intFilSel;
                for (i=0; i<intFilSelVenCon.length; i++)
                {
                    if (! objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_LIN).toString().equals("P"))
                    {
                        objTblMod.insertRow(j);
                        strCodBen = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_COD_BEN);
                        strNomBen = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_NOM_BEN);
                        strCodLoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_COD_LOC);
                        strCodTipDoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_COD_TIP_DOC);
                        strDesCorTipDoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_DEC_TIP_DOC);
                        strDesLarTipDoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_DEL_TIP_DOC);
                        strCodDoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_COD_DOC);
                        strNumDoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_NUM_DOC);
                        strFecDoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_FEC_DOC);
                        strValDoc = objCon64_VenCon.getValueAt(intFilSelVenCon[i], ZafCxP13_VenCon.INT_TBL_DAT_VAL_DOC);
                        
                        objTblMod.setValueAt(strCodCli, j, INT_TBL_CODCLI);
                        objTblMod.setValueAt(strNomCli, j, INT_TBL_NOMCLI);
                        objTblMod.setValueAt(strCodBen, j, INT_TBL_CODBEN);
                        objTblMod.setValueAt(strNomBen, j, INT_TBL_NOMBEN);
                        objTblMod.setValueAt(objZafParSis.getCodigoEmpresa(), j, INT_TBL_CODEMP);
                        objTblMod.setValueAt(strCodLoc, j, INT_TBL_CODLOC);
                        objTblMod.setValueAt(strCodTipDoc, j, INT_TBL_CODTID);
                        objTblMod.setValueAt(strCodDoc, j, INT_TBL_CODDOC);
                        objTblMod.setValueAt(strDesCorTipDoc, j, INT_TBL_DCTIPDOC);
                        objTblMod.setValueAt(strDesLarTipDoc, j, INT_TBL_DLTIPDOC);
                        objTblMod.setValueAt(strNumDoc, j, INT_TBL_NUMDOC);
                        objTblMod.setValueAt(strFecDoc, j, INT_TBL_FECDOC);
                        objTblMod.setValueAt(strValDoc, j, INT_TBL_VALDOC);
                        
                              if(intConFil==0){
                               for(int intNumFilDat=x1; intNumFilDat >=  0;   intNumFilDat-- ){
                                  if( intNumFilDat >= 0 ){
                                       String strCodCli1 =  (tblDat.getValueAt(intNumFil, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFil,INT_TBL_CODCLI).toString());
                                       String strCodCli2 =  (tblDat.getValueAt(intNumFilDat, INT_TBL_CODCLI)==null?"":tblDat.getValueAt(intNumFilDat,INT_TBL_CODCLI).toString());
                                       if(strCodCli1.equals(strCodCli2)){
                                             if(intConFil==0){
                                                intConFil=1;
                                             }
                                           break;
                                       }
                                  }else break;
                               }
                              }

                        objCon64_VenCon.setFilaProcesada(intFilSelVenCon[i]);
                        j++;
                    }
                }
                tblDat.requestFocus();
                CerrarCon();
                //objTblMod.removeEmptyRows();
            } //if (objCon64_VenCon.getSelectedButton()==ZafCxP13_VenCon.INT_BUT_ACE)
        }
    });
    
    objTblPopMnu = new ZafTblPopMnu(tblDat);
    objTblPopMnu.setInsertarFilasVisible(false);
    objTblPopMnu.setInsertarFilaVisible(false);

    objTblPopMnu.addTblPopMnuListener(new ZafTblPopMnuAdapter() {
        @Override
        public void beforeClick(ZafTblPopMnuEvent evt) {
            if(objTblPopMnu.isClickEliminarFila()){
            }
        }
        @Override
        public void afterClick(ZafTblPopMnuEvent evt) {
            if (objTblPopMnu.isClickInsertarFila())
            {
                //Escriba aquí el código que se debe realizar luego de insertar la fila.
                System.out.println("afterClick: isClickInsertarFila");
            }
            else if (objTblPopMnu.isClickEliminarFila())
            {
                System.out.println("afterClick: isClickEliminarFila");
                //javax.swing.JOptionPane.showMessageDialog(null, "Las filas se eliminaron con éxito.");
            }
        }
    });
    tcmAux=null;

    blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);

 }else{  objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI); }
}

private boolean configurarVenConTipDoc() {
boolean blnRes=true;
try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_tipdoc");
    arlCam.add("a.tx_descor");
    arlCam.add("a.tx_deslar");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Des.Cor.");
    arlAli.add("Des.Lar.");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("110");
    arlAncCol.add("350");

    //Armar la sentencia SQL.
    String Str_Sql="";

     if(objZafParSis.getCodigoUsuario()==1){
      Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
      " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
      " where   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
      " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
      " b.co_mnu = " + objZafParSis.getCodigoMenu();
     }else {
            Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar  "+
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
catch (Exception e) {
    blnRes=false;
    objUti.mostrarMsgErr_F1(this, e);
}
return blnRes;
}

public void abrirCon(){
    try{
        CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
    }
    catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}

public void CerrarCon(){
    try{
        CONN_GLO.close();
        CONN_GLO=null;
    }
    catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
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
      panCabGen = new javax.swing.JPanel();
      panCabCob = new javax.swing.JPanel();
      jLabel3 = new javax.swing.JLabel();
      txtDesCodTitpDoc = new javax.swing.JTextField();
      txtDesLarTipDoc = new javax.swing.JTextField();
      butTipDoc = new javax.swing.JButton();
      lblNueBen = new javax.swing.JLabel();
      txtNueBen = new javax.swing.JTextField();
      panDetRecChq = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      panBar = new javax.swing.JPanel();
      panBot = new javax.swing.JPanel();
      butLim = new javax.swing.JButton();
      butGua = new javax.swing.JButton();
      butCer = new javax.swing.JButton();
      panBarEst = new javax.swing.JPanel();
      lblMsgSis = new javax.swing.JLabel();
      jPanel6 = new javax.swing.JPanel();
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
      lblTit.setText("CAMBIO de beneficiario"); // NOI18N
      panNor.add(lblTit, java.awt.BorderLayout.CENTER);

      getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

      panCabGen.setLayout(new java.awt.BorderLayout());

      panCabCob.setPreferredSize(new java.awt.Dimension(100, 65));
      panCabCob.setLayout(null);

      jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jLabel3.setText("Tipo de documento:"); // NOI18N
      panCabCob.add(jLabel3);
      jLabel3.setBounds(10, 5, 110, 20);

      txtDesCodTitpDoc.setBackground(objZafParSis.getColorCamposObligatorios());
      txtDesCodTitpDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtDesCodTitpDocActionPerformed(evt);
         }
      });
      txtDesCodTitpDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtDesCodTitpDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtDesCodTitpDocFocusLost(evt);
         }
      });
      panCabCob.add(txtDesCodTitpDoc);
      txtDesCodTitpDoc.setBounds(120, 5, 70, 20);

      txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
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
      panCabCob.add(txtDesLarTipDoc);
      txtDesLarTipDoc.setBounds(190, 5, 230, 20);

      butTipDoc.setText(".."); // NOI18N
      butTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butTipDocActionPerformed(evt);
         }
      });
      panCabCob.add(butTipDoc);
      butTipDoc.setBounds(420, 5, 20, 20);

      lblNueBen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      lblNueBen.setText("Nuevo beneficiario:"); // NOI18N
      panCabCob.add(lblNueBen);
      lblNueBen.setBounds(10, 25, 120, 20);

      txtNueBen.setBackground(objZafParSis.getColorCamposSistema());
      panCabCob.add(txtNueBen);
      txtNueBen.setBounds(120, 25, 190, 20);

      panCabGen.add(panCabCob, java.awt.BorderLayout.NORTH);

      panDetRecChq.setLayout(new java.awt.BorderLayout());

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
      jScrollPane1.setViewportView(tblDat);

      panDetRecChq.add(jScrollPane1, java.awt.BorderLayout.CENTER);

      panCabGen.add(panDetRecChq, java.awt.BorderLayout.CENTER);

      panBar.setLayout(new java.awt.BorderLayout());

      panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

      butLim.setText("Limpiar");
      butLim.setToolTipText("Limpia la pantalla");
      butLim.setPreferredSize(new java.awt.Dimension(92, 25));
      butLim.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butLimActionPerformed(evt);
         }
      });
      panBot.add(butLim);

      butGua.setText("Guardar");
      butGua.setToolTipText("Guardar datos");
      butGua.setPreferredSize(new java.awt.Dimension(92, 25));
      butGua.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butGuaActionPerformed(evt);
         }
      });
      panBot.add(butGua);

      butCer.setText("Cerrar");
      butCer.setToolTipText("Cierra la ventana.");
      butCer.setPreferredSize(new java.awt.Dimension(92, 25));
      butCer.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCerActionPerformed(evt);
         }
      });
      panBot.add(butCer);

      panBar.add(panBot, java.awt.BorderLayout.CENTER);

      panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
      panBarEst.setLayout(new java.awt.BorderLayout());

      lblMsgSis.setText("Listo");
      lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

      jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
      jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
      jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

      pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      pgrSis.setBorderPainted(false);
      pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
      pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
      jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

      panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

      panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

      panCabGen.add(panBar, java.awt.BorderLayout.SOUTH);

      tabGen.addTab("General", panCabGen);

      getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
   }// </editor-fold>//GEN-END:initComponents

    private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
        // TODO add your handling code here:
        txtDesCodTitpDoc.transferFocus();
}//GEN-LAST:event_txtDesCodTitpDocActionPerformed

    private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
        // TODO add your handling code here:
        strDesCodTipDoc=txtDesCodTitpDoc.getText();
        txtDesCodTitpDoc.selectAll();
}//GEN-LAST:event_txtDesCodTitpDocFocusGained

    private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
        // TODO add your handling code here:

        if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCodTipDoc)) {
            if (txtDesCodTitpDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else
                BuscarTipDoc("a.tx_descor",txtDesCodTitpDoc.getText(),1);
        }else
            txtDesCodTitpDoc.setText(strDesCodTipDoc);
}//GEN-LAST:event_txtDesCodTitpDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        // TODO add your handling code here:
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");

            }else
                BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
        }else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
        
    }//GEN-LAST:event_formInternalFrameClosing

   private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
      //Realizar accion de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
      limpiar();
   }//GEN-LAST:event_butLimActionPerformed

   private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      // TODO add your handling code here:
      int intConFil;
      String strAux;
      
      if (txtNueBen.getText().equals(""))
      {  mostrarMsgInf("No es posible grabar si el campo <FONT COLOR=\"blue\">Nuevo beneficiario</FONT> está en blanco.");                 
         return;
      }
      
      intConFil = 0;
      
      for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
      {  strAux = objTblMod.getValueAt(i, INT_TBL_CODCLI) == null? "" :objTblMod.getValueAt(i, INT_TBL_CODCLI).toString();
         
         if (!strAux.equals(""))
         {  intConFil++;
            break;
         }
      }
      
      if (intConFil == 0)
      {  mostrarMsgInf("No es posible grabar sin detalle.\nIngrese el detalle para poder grabar.");                 
         return;
      }
      
      if (guardar())
         mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
      else
         mostrarMsgInf("<HTML>La información no se pudo guardar.</HTML>");
      
   }//GEN-LAST:event_butGuaActionPerformed

   private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
      exitForm();
   }//GEN-LAST:event_butCerActionPerformed

   /**
     * Esta función limpia la ventana de consulta. Es decir, la ventana de consulta
     * queda como si todavía no se hubiera consultado nada.
     */
    public void limpiar()
    {
        txtCodTipDoc.setText("");
        txtDesCodTitpDoc.setText("");
        txtDesLarTipDoc.setText("");
        objTblMod.removeAllRows();
        lblMsgSis.setText("Listo");
    }
   
     /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        //JOptionPane oppMsg=new JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
            Runtime.getRuntime().gc();
            dispose();
        }

    }

public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      strCodTipDoc=objVenConTipdoc.getValueAt(1);

  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
         }else{
           txtCodTipDoc.setText(strCodTipDoc);
           txtDesCodTitpDoc.setText(strDesCodTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton butCer;
   private javax.swing.JButton butGua;
   private javax.swing.JButton butLim;
   private javax.swing.JButton butTipDoc;
   private javax.swing.ButtonGroup buttonGroup1;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JPanel jPanel6;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JLabel lblMsgSis;
   private javax.swing.JLabel lblNueBen;
   private javax.swing.JLabel lblTit;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panBarEst;
   private javax.swing.JPanel panBot;
   private javax.swing.JPanel panCabCob;
   private javax.swing.JPanel panCabGen;
   private javax.swing.JPanel panDetRecChq;
   private javax.swing.JPanel panNor;
   private javax.swing.JProgressBar pgrSis;
   private javax.swing.JTabbedPane tabGen;
   private javax.swing.JTable tblDat;
   private javax.swing.JTextField txtDesCodTitpDoc;
   private javax.swing.JTextField txtDesLarTipDoc;
   private javax.swing.JTextField txtNueBen;
   // End of variables declaration//GEN-END:variables

    private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 @Override
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
            case INT_TBL_LINEA:
            strMsg="";
            break;
            
            case INT_TBL_CODCLI:
            strMsg="Código de cliente.";
            break;

            case INT_TBL_NOMCLI:
            strMsg="Nombre del cliente.";
            break;
               
            case INT_TBL_CODBEN:
            strMsg="Código de beneficiario.";
            break;

            case INT_TBL_NOMBEN:
            strMsg="Nombre del beneficiario.";
            break;

            case INT_TBL_CODLOC:
            strMsg="Código del Local.";
            break;

            case INT_TBL_NUMDOC:
            strMsg="Numero de documento.";
            break;

            case INT_TBL_FECDOC:
            strMsg="Fecha de documento.";
            break;

            case INT_TBL_DCTIPDOC:
            strMsg="Descripción corta del tipo de documento.";
            break;

            case INT_TBL_DLTIPDOC:
            strMsg="Descripción larga del tipo de documento.";
            break;

            case INT_TBL_VALDOC:
            strMsg="Valor del documento.";
            break;

        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}

    protected void finalize() throws Throwable {
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
    }
    
    private boolean guardar(){
        boolean blnRes=true;
        try{
            abrirCon();
            if(CONN_GLO!=null){
                CONN_GLO.setAutoCommit(false);
                if(guardarDatos()){
                    CONN_GLO.commit();
                    cargarDetReg();
                }
                else{
                    CONN_GLO.rollback();
                    blnRes=false;
                }
            }
            CerrarCon();
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar en tbm_cabPag los campos para entrega de cheques.
     * @return true: Si se pudo modificar.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarDatos()
    {
       boolean blnRes = true;
       int  intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodCliEmp, intCodCli, intCodBen_PagMovInv, intCodBen_BenChq;
       String strSQL, strAux, strFecSis;
       java.util.Date datFecAux;
       Statement stm;
       ResultSet rst;
       
       try
       {  
          if (CONN_GLO != null)
          {  stm = CONN_GLO.createStatement();
             rst = null;
             intCodCliEmp = 0;
             
             if (objZafParSis.getCodigoEmpresa() == 1)
             {  intCodCliEmp = 3516; //Este es el cod_cliente para Tuval guardado en tbm_cli.co_cli
             }
             else if (objZafParSis.getCodigoEmpresa() == 2)
             {  intCodCliEmp = 446; //Este es el cod_cliente para Castek guardado en tbm_cli.co_cli
             }  
             else if (objZafParSis.getCodigoEmpresa() == 4)
             {  intCodCliEmp = 886; //Este es el cod_cliente para Dimulti guardado en tbm_cli.co_cli
             }
             
             datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
             strFecSis = objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
             
             for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
             {  intCodEmp = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODEMP).toString());
                intCodLoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODLOC).toString());
                intCodTipDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODTID).toString());
                intCodDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODDOC).toString());
                intCodCli = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODCLI).toString());
                strAux = objTblMod.getValueAt(i, INT_TBL_CODBEN) == null? "" :objTblMod.getValueAt(i, INT_TBL_CODBEN).toString();
             
                if (strAux.equals(""))
                {  intCodBen_PagMovInv = 0;
                }
                else
                {  intCodBen_PagMovInv = Integer.parseInt(strAux);
                }
                
                //Se va a verificar si la empresa (Tuval, Castek, Dimulti) existe en tbm_benchq.
                strSQL =  "select * from tbm_benchq ";
                strSQL += "where st_reg = 'P' and co_emp = " + objZafParSis.getCodigoEmpresa() + " ";
                strSQL += "and co_cli = " + intCodCli + " and tx_benchq = '" + objZafParSis.getNombreEmpresa() + "'";
                rst = stm.executeQuery(strSQL);

                if (rst.next())
                {  
                   intCodBen_BenChq = rst.getInt("co_reg");
                }
                else
                {  //Como la empresa (Tuval, Castek, Dimulti) no existe en tbm_benchq, se va a realizar un INSERT en dicha tabla.
                   //Antes de realizar el INSERT, se va a realizar un UPDATE poniendo tbm_benchq.st_reg = 'A' a todos los registros.
                   strSQL =  "UPDATE tbm_benchq ";
                   strSQL += "SET st_reg = 'A' ";
                   strSQL += "WHERE st_reg in ('A','P') and co_emp = " + objZafParSis.getCodigoEmpresa();
                   strSQL += " and co_cli = " + intCodCli;
                   stm.executeUpdate(strSQL);
                   
                   //Ahora se va a realizar el INSERT poniendo tbm_benchq.st_reg = 'P' al registro donde ese esta insertando la empresa (Tuval, Castek, Dimulti).
                   strSQL =  "INSERT INTO tbm_benchq VALUES (";
                   strSQL += intCodEmp + ", "; //co_emp
                   strSQL += intCodCli + ", "; //co_cli
                   strSQL += intCodCliEmp + ", "; //co_reg
                   strSQL += "'" + objZafParSis.getNombreEmpresa() + "', "; //tx_benchq
                   strSQL += "'P', "; //st_reg
                   strSQL += "'I'"; //st_regrep
                   strSQL += ")";
                   stm.executeUpdate(strSQL);
                   intCodBen_BenChq = intCodCliEmp;
                }
                
                //En la tabla tbm_cabmovinv se van a actualizar los campos co_ben y tx_benchq.
                strSQL =  "UPDATE tbm_cabmovinv ";
                strSQL += "SET co_ben = " + intCodBen_BenChq + ",";
                strSQL += " tx_benchq = '" + objZafParSis.getNombreEmpresa() + "',";
                strSQL += " co_usrmod = " + objZafParSis.getCodigoUsuario() + ",";
                strSQL += " fe_ultmod = '" + strFecSis + "',";
                strSQL += " tx_commod = '" + objZafParSis.getNombreComputadoraConDirIP() + "' ";
                strSQL += "WHERE co_emp = " + intCodEmp;
                strSQL += " and co_loc = " + intCodLoc;
                strSQL += " and co_tipdoc = " + intCodTipDoc;
                strSQL += " and co_doc = " + intCodDoc;
                stm.executeUpdate(strSQL);
             } //for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
             
             rst.close();
             rst = null;
             stm.close();
             stm = null;
          } //if (CONN_GLO != null)
       }
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       return blnRes;
    }
    
    private boolean cargarDetReg()
    {
       boolean blnRes = false;
       int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
       String strSQL, strCodCli;
       Statement stm;
       ResultSet rst;
         
       try
       {
          if (CONN_GLO != null)
          {  stm = CONN_GLO.createStatement();
             rst = null;
             
             for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
             {  strCodCli = objTblMod.getValueAt(i, INT_TBL_CODCLI) == null? "" :objTblMod.getValueAt(i, INT_TBL_CODCLI).toString();
                if (!strCodCli.equals(""))
                {  intCodEmp = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODEMP).toString());
                   intCodLoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODLOC).toString());
                   intCodTipDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODTID).toString());
                   intCodDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_CODDOC).toString());
                   
                   strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_ben, tx_benchq ";
                   strSQL += "FROM tbm_cabmovinv ";
                   strSQL += "WHERE co_emp = " + intCodEmp;
                   strSQL += " and co_loc = " + intCodLoc;
                   strSQL += " and co_tipdoc = " + intCodTipDoc;
                   strSQL += " and co_doc = " + intCodDoc;
                   rst = stm.executeQuery(strSQL);
                   
                   if (rst.next())
                   {  objTblMod.setValueAt(rst.getString("co_ben"), i, INT_TBL_CODBEN);
                      objTblMod.setValueAt(rst.getString("tx_benchq"), i, INT_TBL_NOMBEN);
                   }
                }
             }

             rst.close();
             rst = null;
             stm.close();
             stm = null;
          } //if (CONN_GLO != null)
       } //try
       
       catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
       catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

       return blnRes;
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
}
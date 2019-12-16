/*
 * Created on 13 de agosto de 2008, 10:26  ne_numguides
 */

package Ventas.ZafVen25;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPulFacEle.ZafPulFacEle;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Ventas.ZafVen11.ZafVen11;
import Ventas.ZafVen11.ZafVen11_fac;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import Ventas.ZafVen28.ZafVen28;

/**
 *
 * @author  jayapata
 * AUTORIZACION DE SOLICITUDES DE DEVOLUCION DE VENTAS
 *
 */

public class ZafVen25 extends JInternalFrame {
    private ZafParSis objZafParSis;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelRenChk objTblCelRenChk2;
	/*SE AGREGA PARA PRESENTAR SI LA FACTURA TIENE GUIA REMISION*/    
    private ZafTblCelRenChk objTblCelRenChkTiGuiRem;
    /*SE AGREGA PARA PRESENTAR SI LA FACTURA TIENE GUIA REMISION*/    
    private ZafObtConCen  objObtConCen;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
    private ZafInvItm objInvItm;
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    
    private ZafVen25_02 objZafVen25_02;
    private ZafVen28 objVen28;
    
    private static final int INT_TBL_LINEA =0;
    private static final int INT_TBL_CODEMP =1;
    private static final int INT_TBL_CODLOC =2;
    private static final int INT_TBL_CODTIPDOC =3;
    private static final int INT_TBL_DCOTIPDOC =4;
    private static final int INT_TBL_DCATIPDOC =5;
    private static final int INT_TBL_CODDOC =6;
    private static final int INT_TBL_NUMDOC =7;
    private static final int INT_TBL_FECDOC =8;
    private static final int INT_TBL_CODCLI =9;
    private static final int INT_TBL_DESCLI =10;
    /*CAMBIO AGREGAR COLUMNA PARA SABER SI TIENE GUIA DE REMISION UNA FACTURA*/
    private static final int INT_TBL_TIGUIREM =11;
    /*CAMBIO AGREGAR COLUMNA PARA SABER SI TIENE GUIA DE REMISION UNA FACTURA*/
    private static final int INT_TBL_VOLFAC =12;
    private static final int INT_TBL_USRSOL =13;
    private static final int INT_TBL_BUTFAC =14;
    private static final int INT_TBL_BUTSOL =15;
    private static final int INT_TBL_AUTDEV =16;
    private static final int INT_TBL_DEGDEV =17;
    private static final int INT_TBL_CANDEV =18;
    private static final int INT_TBL_OBSSOLDEV =19;
    private static final int INT_TBL_CODLOCREL =20;
    private static final int INT_TBL_CONTIPDOCREL =21;
    private static final int INT_TBL_CODDOCREL =22;
    private static final int INT_TBL_ESTCONF =23;
	
    private static final int INT_TBL_TIPDEV =24;


    private Vector vecCab=new Vector();
    private Connection conRemGlo=null, CONN_GLO=null;
    private int INTCODREGCEN=0;
    private int INTVERCONCEN=0;

    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.

    private int intDiaCanSolDevVen=15;  /*  
                                            30/Dic/2015
                                            JoséMario:  
                                            Cantidad de dias para cancelar las solicitudes de devoluciones que esten pendientes o Autorizadas
                                            que no han sido tramitadas (NO ha habido ingreso de la mercaderia), solicitado por WCampoverde 
                                        */  
    
    
    private String strVersion = " v4.3 "; // 
   
    private String strTit = "Mensaje del Sistema Zafiro";
    private ZafPulFacEle objPulFacEle;

    /** Creates new form pantalla1 */
    public ZafVen25(ZafParSis obj) {
        try{
            this.objZafParSis = (ZafParSis) obj.clone();
            initComponents();

            this.setTitle(objZafParSis.getNombreMenu() + strVersion);
            lblTit.setText(objZafParSis.getNombreMenu());

            objInvItm = new ZafInvItm(this, objZafParSis);
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objZafParSis);
            objUti = new ZafUtil();
            objTblCelRenLbl = new ZafTblCelRenLbl();
            configuraTbl();
            objObtConCen = new ZafObtConCen(objZafParSis);
            INTCODREGCEN=objObtConCen.intCodReg;
        }catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }


      private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        //String strTit;
        //strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }


    public boolean abrirConRem(){
        boolean blnres=false;
        try{
            System.out.println("ZafVen25.abrirConRem");
            int intIndEmp=INTCODREGCEN;
            if(intIndEmp != 0){
                conRemGlo=DriverManager.getConnection(objZafParSis.getStringConexion(intIndEmp), objZafParSis.getUsuarioBaseDatos(intIndEmp), objZafParSis.getClaveBaseDatos(intIndEmp));
                conRemGlo.setAutoCommit(false);
            }
            blnres=true;
        }
        catch (SQLException e) {
            MensajeInf("NO SE PUEDE ESTABLECER LA CONEXION REMOTA CON LA BASE CENTRAL..");
            INTVERCONCEN=1;
            return false;
        }
        return blnres;
    }
    
    private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
           System.out.println("ZafVen25.configuraTbl");
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
	    vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
	    vecCab.add(INT_TBL_CODTIPDOC,"Cod.TipDoc.");
	    vecCab.add(INT_TBL_DCOTIPDOC,"Tip.CorDoc.");
	    vecCab.add(INT_TBL_DCATIPDOC,"Des.LarTipDoc.");
	    vecCab.add(INT_TBL_CODDOC,"Cod.Doc.");
	    vecCab.add(INT_TBL_NUMDOC,"Num.Doc.");
	    vecCab.add(INT_TBL_FECDOC,"Fec.Doc.");
	    vecCab.add(INT_TBL_CODCLI,"Cod.Cli.");
            vecCab.add(INT_TBL_DESCLI,"Des.Cli");

 /*CAMBIO AGREGAR COLUMNA PARA SABER SI TIENE GUIA DE REMISION UNA FACTURA*/
            vecCab.add(INT_TBL_TIGUIREM,"Gui.Rem.");
            /*CAMBIO AGREGAR COLUMNA PARA SABER SI TIENE GUIA DE REMISION UNA FACTURA*/
            vecCab.add(INT_TBL_VOLFAC,"Vol.Fac.");
	    vecCab.add(INT_TBL_USRSOL,"Usr.Sol");
	    vecCab.add(INT_TBL_BUTFAC,"...");
	    vecCab.add(INT_TBL_BUTSOL,"...");
	    vecCab.add(INT_TBL_AUTDEV,"Autorizado.");
	    vecCab.add(INT_TBL_DEGDEV,"Denegado.");
	    vecCab.add(INT_TBL_CANDEV,"Cancelado.");
	    vecCab.add(INT_TBL_OBSSOLDEV,"Obs.Sol.Dev");
	    vecCab.add(INT_TBL_CODLOCREL,"");
	    vecCab.add(INT_TBL_CONTIPDOCREL,"");
	    vecCab.add(INT_TBL_CODDOCREL,"");
	    vecCab.add(INT_TBL_ESTCONF,"");
            vecCab.add(INT_TBL_TIPDEV,"");

	    objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);

	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux=tblDat.getColumnModel();

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTFAC);
            vecAux.add("" + INT_TBL_BUTSOL);
            vecAux.add("" + INT_TBL_AUTDEV);
            vecAux.add("" + INT_TBL_DEGDEV);
            vecAux.add("" + INT_TBL_CANDEV);
            vecAux.add("" + INT_TBL_OBSSOLDEV);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DCOTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DCATIPDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_USRSOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUTFAC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTSOL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_AUTDEV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DEGDEV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANDEV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_OBSSOLDEV).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_VOLFAC).setPreferredWidth(50);
			tcmAux.getColumn(INT_TBL_TIGUIREM).setPreferredWidth(50);

            /* Aqui se agrega las columnas que van
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_CODDOC);
            arlColHid.add(""+INT_TBL_CODLOCREL);
            arlColHid.add(""+INT_TBL_CONTIPDOCREL);
            arlColHid.add(""+INT_TBL_CODDOCREL);
            arlColHid.add(""+INT_TBL_ESTCONF);
            arlColHid.add(""+INT_TBL_TIPDEV);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            ZafTblCelRenBut objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            ButCotFac butCotFac = new ButCotFac(tblDat, INT_TBL_BUTFAC); //*****
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTSOL).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            ButCotSol butCotSol = new ButCotSol(tblDat, INT_TBL_BUTSOL); //*****
            ZafTblCelRenChk objTblCelRenChk;
            ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_AUTDEV).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_AUTDEV).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                    
                    @Override
                    public void beforeEdit(ZafTableEvent evt){

                    }

                    @Override
                    public void afterEdit(ZafTableEvent evt) {
                        int intCol = tblDat.getSelectedRow();
                        tblDat.setValueAt( false, intCol, INT_TBL_DEGDEV);
                        tblDat.setValueAt( false, intCol, INT_TBL_CANDEV);
                        if(tblDat.getValueAt(intCol, INT_TBL_AUTDEV).toString().equals("true")){
                            if( _getVerMerStk( tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString(), tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString(),
                                 tblDat.getValueAt(intCol, INT_TBL_CODTIPDOC).toString(), tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString() )){
                                 MensajeInf("HAY MERCADERIA QUE ESTA SOLICITANDO QUEDAR EN STOCK. ");
                            }
                         }

                    }
               });

            objTblCelRenChk=null;
            objTblCelEdiChk=null;
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DEGDEV).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DEGDEV).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intRow = tblDat.getSelectedRow();
                    if(tblDat.getValueAt(intRow, INT_TBL_DEGDEV).equals(true)) {  // José Mario 7/Ago/2014
                            tblDat.setValueAt( false, intRow, INT_TBL_AUTDEV);
                            tblDat.setValueAt( false, intRow, INT_TBL_CANDEV);
                        if(!getEstSolDev( tblDat.getValueAt(intRow, INT_TBL_CODEMP).toString(), tblDat.getValueAt(intRow, INT_TBL_CODLOC).toString(),
                                          tblDat.getValueAt(intRow, INT_TBL_CODTIPDOC).toString(), tblDat.getValueAt(intRow, INT_TBL_CODDOC).toString() )){
                            MensajeInf("NO SE PUEDE CANCELAR POR QUE YA ESTA EN EL SIGUIENTE PASO.. ");
                            tblDat.setValueAt( false, intRow, INT_TBL_DEGDEV);
                            tblDat.setValueAt( true, intRow, INT_TBL_AUTDEV);
                            tblDat.setValueAt( false, intRow, INT_TBL_CANDEV);
                       }   
                    }
                }
            });
            objTblCelRenChk=null;
            objTblCelEdiChk=null;
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CANDEV).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_CANDEV).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt){
                }
                @Override
                public void afterEdit(ZafTableEvent evt) {
                    int intRow = tblDat.getSelectedRow();
                    if(tblDat.getValueAt(intRow, INT_TBL_CANDEV).equals(true)) { // José Mario 7/Ago/2014
                        tblDat.setValueAt( false, intRow, INT_TBL_AUTDEV);
                        tblDat.setValueAt( false, intRow, INT_TBL_DEGDEV);
                        if(!getEstSolDev( tblDat.getValueAt(intRow, INT_TBL_CODEMP).toString(), tblDat.getValueAt(intRow, INT_TBL_CODLOC).toString(),
                                          tblDat.getValueAt(intRow, INT_TBL_CODTIPDOC).toString(), tblDat.getValueAt(intRow, INT_TBL_CODDOC).toString() )){
                           MensajeInf("NO SE PUEDE CANCELAR POR QUE YA ESTA EN EL SIGUIENTE PASO.. ");
                           tblDat.setValueAt( false, intRow, INT_TBL_CANDEV);
                           tblDat.setValueAt( true, intRow, INT_TBL_AUTDEV);
                           tblDat.setValueAt( false, intRow, INT_TBL_DEGDEV);
                       }
                    }
                }
               });
            objTblCelRenChk=null;
            objTblCelEdiChk=null;
            /**************************************************************************/
            tcmAux.getColumn(INT_TBL_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODLOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DCOTIPDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DCATIPDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUMDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FECDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CODCLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DESCLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_USRSOL).setCellRenderer(objTblCelRenLbl);

            objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLbl.getRowRender();
                    String str=tblDat.getValueAt(intCell, INT_TBL_ESTCONF).toString();
                    if(!(str.equals("P"))){
                        objTblCelRenLbl.setBackground(Color.ORANGE);
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    }else {
                        objTblCelRenLbl.setBackground(Color.WHITE);
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    }
                }
            });

            /**************************************************************************/

            objTblCelRenChk2 = new ZafTblCelRenChk();
            //objTblCelRenChk2.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_VOLFAC).setCellRenderer(objTblCelRenChk2);
            objTblCelRenChk2.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
                    //Mostrar de color gris las columnas impares.
                   int intCell=objTblCelRenChk2.getRowRender();
                    String str=tblDat.getValueAt(intCell, INT_TBL_ESTCONF).toString();
                    if(!(str.equals("P"))){
                        objTblCelRenChk2.setBackground(Color.ORANGE);
                        objTblCelRenChk2.setForeground(Color.BLACK);
                    }else {
                        objTblCelRenChk2.setBackground(Color.WHITE);
                        objTblCelRenChk2.setForeground(Color.BLACK);
                    }
               }
            });
            
  objTblCelRenChkTiGuiRem = new ZafTblCelRenChk();
            //objTblCelRenChk2.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_TIGUIREM).setCellRenderer(objTblCelRenChkTiGuiRem);
            objTblCelRenChkTiGuiRem.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) {
//                    //Mostrar de color gris las columnas impares.
//                   int intCell=objTblCelRenChkTiGuiRem.getRowRender();
//                    String str=tblDat.getValueAt(intCell, INT_TBL_ESTCONF).toString();
//                    if(!(str.equals("P"))){
//                        objTblCelRenChkTiGuiRem.setBackground(Color.ORANGE);
//                        objTblCelRenChkTiGuiRem.setForeground(Color.BLACK);
//                    }else {
//                        objTblCelRenChkTiGuiRem.setBackground(Color.WHITE);
//                        objTblCelRenChkTiGuiRem.setForeground(Color.BLACK);
//                    }
               }
            });
            /**************************************************************************/
            
           objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
           tcmAux=null;
           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
    // José Mario 7/Ago/2014
    private boolean getEstSolDev(String codEmp, String codLoc, String codTipDoc, String codDoc ){
        boolean blnRes=true;// José Mario 7/Ago/2014
        String strSql="",strSt_impGuiRemAut="", strSt_recMerDev="",str_st_confCont="";
        Statement stmLoc;
        ResultSet rstLoc;
        try{
             abrirCon();
             if(CONN_GLO!=null) {  
                  stmLoc=CONN_GLO.createStatement();
                  strSql=" SELECT a1.st_impGuiRemAut, a1.st_recMerDev \n";
                  strSql+=" FROM tbm_cabSolDevVen as a1 \n";
                  strSql+=" WHERE a1.co_emp=" + codEmp + " and a1.co_loc="+ codLoc +" and \n";
                  strSql+=" a1.co_tipDoc="+ codTipDoc +" and a1.co_doc=" + codDoc + "\n";
                  System.out.println("PASO BODEGA: " + strSql);
                  rstLoc=stmLoc.executeQuery(strSql);
                  if(rstLoc.next()){
                      strSt_impGuiRemAut=rstLoc.getString("st_impGuiRemAut");
                      strSt_recMerDev=rstLoc.getString("st_recMerDev");
                }
               if(strSt_recMerDev.equals("S") ) {  // Ya fue ingresada la mercaderia // 5 Agosto 2014
                     blnRes=false;
               }
               if(strSt_impGuiRemAut.equals("N") ) {  // No se imprimio la guia paso directo a contabilidad
                   strSql=" SELECT a2.co_emp, a2.co_loc,a2.co_tipDoc,a2.co_doc, CASE WHEN a2.co_doc IS NOT NULL THEN 'S' ELSE 'N' END AS st_confCont \n";
                   strSql+=" FROM tbm_cabSolDevVen as a1 \n";
                   strSql+=" INNER JOIN tbr_cabMovInv as a2 ON (a1.co_emp=a2.co_empRel AND a1.co_docRel=a2.co_docRel \n";
                   strSql+="                                    AND a1.co_tipDocRel=a2.co_tipDocRel AND a1.co_docRel=a2.co_docRel) \n";
                   strSql+=" WHERE a1.co_emp=" + codEmp + " and a1.co_loc="+ codLoc +" and \n";
                   strSql+=" a1.co_tipDoc="+ codTipDoc +" and a1.co_doc=" + codDoc + "\n";
                   System.out.println("PASO CONTA: " + strSql);
                   rstLoc=stmLoc.executeQuery(strSql);
                   if(rstLoc.next()){
                       str_st_confCont=rstLoc.getString("st_confCont");
                   }
                   if(str_st_confCont.equals("S") ) {  // Ya existe Nota de credito // 5 Agosto 2014
                        blnRes=false;
                   }
               }
               rstLoc.close();
               rstLoc=null;
               stmLoc.close();
               stmLoc=null;
               CerrarCon();
            }
        }
        catch(SQLException Evt) {
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt);  
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
       return blnRes;
    }
    // José Mario 7/Agos/2014
    
    
    
    public void abrirCon(){
        try{
            System.out.println("ZafVen25.abrirCon");
            CONN_GLO=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }
    
    public void CerrarCon(){
        try{
            System.out.println("ZafVen25.CerrarCon");
            CONN_GLO.close();
            CONN_GLO=null;
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

/**
 *
 * @param conn c
 * @return
 *
 * verifica si hay mercaderia que no se volvera a facturar y si queda en stock esto esta orientado para terminales L
 *
 */
private boolean _getVerMerStk(String strCodEmpSol, String strCodLocSol, String  strCodTipDocSol, String strCodDocSol  ){
  boolean blnRes=false;
  String strSql="";
  Statement stmLoc;
  ResultSet rstLoc;
  try{
        System.out.println("ZafVen25._getVerMerStk");
        abrirCon();
        if(CONN_GLO!=null) {
            stmLoc=CONN_GLO.createStatement();
            strSql=" SELECT a.co_doc FROM tbm_cabsoldevven AS a " +
                   " INNER JOIN tbm_detsoldevven AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) " +
                   " WHERE a.co_emp="+strCodEmpSol+" AND a.co_loc="+strCodLocSol+" AND a.co_tipdoc="+strCodTipDocSol+" AND a.co_doc="+strCodDocSol+" " +
                   " AND a1.nd_cansolnodevprv > 0 ";
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
           blnRes=true;
        }
        rstLoc.close();
        rstLoc=null;
        stmLoc.close();
        stmLoc=null;
        CerrarCon();
       }
  }
  catch(SQLException Evt) {  
      blnRes=false; 
      objUti.mostrarMsgErr_F1(this, Evt);  
  }
  catch(Exception Evt) { 
      blnRes=false; 
      objUti.mostrarMsgErr_F1(this, Evt); 
  }
 return blnRes;
}

/*
 private class RenderDecimales extends javax.swing.JLabel implements javax.swing.table.TableCellRenderer {

        public RenderDecimales() {
            //this.intNumDecimales=intNumDecimales ;
           // setHorizontalAlignment(javax.swing.JLabel.RIGHT);
           // setOpaque(true);
           // setBackground(new java.awt.Color(255, 255, 255));
        }

        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            try{

               // double dblSubPro = Double.parseDouble( (value == null)?"0":""+value);
             //  String strText=
                //  Librerias.ZafUtil.ZafUtil objutil = new Librerias.ZafUtil.ZafUtil();
                if(isSelected){
                    setBorder(new javax.swing.border.LineBorder(javax.swing.UIManager.getDefaults().getColor("Button.focus"), 1));
                }else{
                    setBorder(null);
                }

              if(table.getValueAt(row, INT_TBL_NUMDOC)!=null){
                 if((table.getValueAt(row, INT_TBL_NUMDOC).toString().equals("33"))){


                this.setText(" "+value);
                this.setFont(new java.awt.Font("SansSerif", 0, 11));
               // this.setOpaque(true);
                setBackground(new java.awt.Color(201,223,245));
                setForeground(new java.awt.Color(204, 0, 0));

                this.setToolTipText("dfgf");
                 }}

//                if(tblDat.getValueAt(row, INT_TBL_BLNPRE)!=null){
//                    if((tblDat.getValueAt(row, INT_TBL_BLNPRE).toString().equals("true"))){
//                        this.setOpaque(true);
//                        setBackground(new java.awt.Color(0, 0, 0));
//                        setForeground(new java.awt.Color(255,255,255));
//                        setFont(new java.awt.Font("MS Sans Serif", 1, 18));
//                    }
//                }

            }catch(Exception e){ objUti.mostrarMsgErr_F1(this, e);
              tblDat.setValueAt("0", row, column);        }
            return this;
        }
    }
   */

private class ButCotFac extends ZafTableColBut_uni{
    public ButCotFac(JTable tbl, int intIdx){
        super(tbl,intIdx, "Ver Factura Comercial.");
    }
    
    @Override
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaFac(intCol);
    }
}

private void llamarVentanaFac(int intCol){
    System.out.println("ZafVen25.llamarVentanaFac");
    // int intCodEmp= objZafParSis.getCodigoEmpresa();
     int intCodEmp = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODEMP  ).toString()) );
     String strCocLoc = (tblDat.getValueAt(intCol,  INT_TBL_CODLOCREL  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODLOCREL  ).toString());
     String strDocTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CONTIPDOCREL  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CONTIPDOCREL  ).toString());
     String strCocDoc =  (tblDat.getValueAt(intCol,  INT_TBL_CODDOCREL  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODDOCREL  ).toString());
     ZafVen11_fac obj = new ZafVen11_fac(JOptionPane.getFrameForComponent(this), false, objZafParSis, intCodEmp, strCocLoc, strDocTipDoc, strCocDoc );
     obj.show();
}

private class ButCotSol extends ZafTableColBut_uni{
    public ButCotSol(JTable tbl, int intIdx){
        super(tbl,intIdx, "Ver Solicitud de Devolución.");
    }
    
    @Override
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaSol(intCol);
    }
}

private void llamarVentanaSol(int intCol){
   //int intCodEmp=objZafParSis.getCodigoEmpresa();
  // int intCodLoc=objZafParSis.getCodigoLocal();

   int intCodEmp = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODEMP  ).toString()) );
   int intCodLoc = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODLOC  ).toString()) );


   int intCodTipDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODTIPDOC).toString() );
   int intCodDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString() );

   ZafVen11 obj = new ZafVen11(objZafParSis,  intCodEmp, intCodLoc,  intCodTipDoc , intCodDoc );
   this.getParent().add(obj,JLayeredPane.DEFAULT_LAYER);
   obj.show();

}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panFilTabGen = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        chkSolAut = new javax.swing.JCheckBox();
        panBot = new javax.swing.JPanel();
        panButPanBut = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
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
            }
        });

        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFilTabGen.setLayout(new java.awt.BorderLayout());

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

        panFilTabGen.add(scrTbl, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 30));
        jPanel1.setLayout(null);

        chkSolAut.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkSolAut.setText("Mostrar las solicitudes que han sido autorizadas.");
        chkSolAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolAutActionPerformed(evt);
            }
        });
        jPanel1.add(chkSolAut);
        chkSolAut.setBounds(0, 0, 360, 20);

        panFilTabGen.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tabGen.addTab("Filtro", panFilTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panButPanBut.add(butCon);

        butGua.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(79, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panButPanBut.add(butGua);

        butCer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(79, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panButPanBut.add(butCer);

        panBot.add(panButPanBut, java.awt.BorderLayout.EAST);

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

        panBot.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
         cerrarVen();
    }//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        System.out.println("ZafVen25.butCon---> ZafThreadGUI");
        arlDevVenDat = new ArrayList();
        if (objThrGUI==null) {
            objThrGUI=new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private class ZafThreadGUI extends Thread {
        @Override
        public void run() {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            if(chkSolAut.isSelected()) {
                consultarDatAut();
            }
            else {
                consultarDat();
            }
            objThrGUI=null;
        }
    }

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        System.out.println("ZafVen25.butGuaActionPerformed: ");
        guardarDatAut();
        if (objThrGUI==null){ 
            objThrGUI=new ZafThreadGUI(); 
            objThrGUI.start();
        }
    }//GEN-LAST:event_butGuaActionPerformed



private boolean guardarDatAut()
{
    boolean blnRes=false;
    Connection conn;
    try
    {
        System.out.println("ZafVen25.guardarDatAut: ");
        conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        if(conn!=null)
        {
             conn.setAutoCommit(false);
             objZafVen25_02 = new ZafVen25_02(this, objZafParSis);
             if(chkSolAut.isSelected())
             {
                  if(guardarDenCan(conn)){
                     conn.commit();
                  }else{ conn.rollback(); }
             }
             else
             {
                   if(guardarAutorizado(conn)){
                       conn.commit();
                        if(procesoDevVenAuto()){
                            System.out.println("GENERA!");
                       }
                       
                   }
                   else{ 
                       conn.rollback(); 
                   }
             }
         objZafVen25_02=null;

       conn.close();
       conn=null;
       blnRes=true;
     }
    }
    catch(SQLException Evt) 
    {  
        blnRes=false; 
        objUti.mostrarMsgErr_F1(this, Evt);  
    }

    catch(Exception Evt) 
    { 
        blnRes=false; 
        objUti.mostrarMsgErr_F1(this, Evt); 
    }
    return blnRes;
}


    private boolean procesoDevVenAuto(){
        boolean blnRes=true;
        try{
            for(int i=0;i<arlDevVenDat.size();i++){
                if(!procesaDevolucion(objUti.getIntValueAt(arlDevVenDat, i,INT_ARL_COD_EMP_DEV_VEN), 
                        objUti.getIntValueAt(arlDevVenDat, i,INT_ARL_COD_LOC_DEV_VEN), 
                        objUti.getIntValueAt(arlDevVenDat, i,INT_ARL_COD_TIP_DOC_DEV_VEN),
                        objUti.getIntValueAt(arlDevVenDat, i,INT_ARL_COD_DOC_DEV_VEN))){
                    blnRes=false;
                }
            }
            arlDevVenDat = new ArrayList();
        }
        catch(Exception Evt) { 
            blnRes=false; 
            objUti.mostrarMsgErr_F1(this, Evt); 
        }
        return blnRes;
    }

StringBuffer stbAutPre;
boolean blnAutPre=false;

private boolean guardarDenCan(Connection conn){
 boolean blnRes=false;
 Statement stmLoc;
 String strTipDev="", strObs="";
 int intCodEmp=0, intCodLoc=0, intCodTipDoc=0, intCodDoc=0;
 try{
     System.out.println("ZafVen25.guardarDenCan");
   if(conn!=null){
     stmLoc=conn.createStatement();

       for(int i=0; i<tblDat.getRowCount(); i++){

           strTipDev=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_TIPDEV));
           intCodEmp=Integer.parseInt(objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODEMP)));
           intCodLoc=Integer.parseInt(objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODLOC)));
           intCodTipDoc=Integer.parseInt(objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)));
           intCodDoc=Integer.parseInt(objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODDOC)));
           strObs=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_OBSSOLDEV));


         if( tblDat.getValueAt(i, INT_TBL_DEGDEV).toString().equals("true") ){ // Denegado

              if(denegarCancelarSolDevAut(conn, strTipDev, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,  strObs, "D" )){
                 blnRes=true;
              }else{ blnRes=false; break; }


          }else if( tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("true") ){ // Cancelación

              if(denegarCancelarSolDevAut(conn, strTipDev, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc,  strObs, "C" )){
                 blnRes=true;
              }else{ blnRes=false; break; }

          }
          
       }

     stmLoc.close();
     stmLoc=null;

  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean denegarCancelarSolDevAut(Connection conn, String strTipDev, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strObs, String strTipAcc ){
 boolean blnRes=false;
 Statement stmLoc;
 String strSql="";
 int intEstSol=0;
 try{
   if(conn!=null){
       stmLoc=conn.createStatement();
        System.out.println("ZafVen25.denegarCancelarSolDevAut");
       strSql="UPDATE tbm_cabsoldevven SET co_usrAut="+objZafParSis.getCodigoUsuario()+", tx_comAut='"+objZafParSis.getNombreComputadoraConDirIP()+"',"
       + " st_aut='"+strTipAcc+"', tx_obsaut='"+strObs+"', fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "
       + " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc;
       System.out.println("ZafVen25.denegarCancelarSolDevAut: " + strSql);
       stmLoc.executeUpdate(strSql);


     if(strTipDev.equals("C")){


         intEstSol=objZafVen25_02._getVerEstSolDevVen(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);

         switch(intEstSol){
             /*case 0:
                   // NO HAY OD IMPRESO
             break;*/

             case 0: 
                 blnRes=true;  // NO HAY OD IMPRESO
             break;

             case 1:
                  blnRes=true;  // ESQUEMA ANTERIOR DE GUIAS
             break;

             case 2:
                if(_getODImp(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                    blnRes=true;
                 }
             break;

             case 3:
                 if(_getODNoImp(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                    blnRes=true;
                 }
             break;

         }

     }else{
         blnRes=true;
     }

     stmLoc.close();
     stmLoc=null;

  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean _getODImp(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 Statement stmLoc;
 int intEstSol=0;
 try{
     System.out.println("ZafVen25._getODImp");
   if(conn!=null){
       stmLoc=conn.createStatement();

         intEstSol=objZafVen25_02._getVerOD(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);

         switch(intEstSol){
             case 0:
                   // NO EXISTE GENERADO NUEVA OD  ESTA CONFIRMANADO
                  if(objZafVen25_02._getVerConfEgrOD(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                      blnRes=true;
                  }else MensajeInf("LA ORDEN DE DESPACHO ESTA IMPRESO NO ES POSIBLE DENEGAR/CANCELAR LA SOLICITUD. ");
             break;

             case 1:   // EXISTE GENERADO NUEVA OD  NO SE PUEDE DEN, CAN.
                 if(objZafVen25_02._getVerConfEgrOD(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                      blnRes=true;
                 }else MensajeInf("LA ORDEN DE DESPACHO ESTA IMPRESO NO ES POSIBLE DENEGAR/CANCELAR LA SOLICITUD. ");
             break;

         }
     stmLoc.close();
     stmLoc=null;

  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean _getODNoImp(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 Statement stmLoc;
 int intEstSol=0;
 try{
     System.out.println("ZafVen25._getODNoImp");
   if(conn!=null){
       stmLoc=conn.createStatement();

         intEstSol=objZafVen25_02._getVerOD(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);

         switch(intEstSol){
             case 0:
                   // NO EXISTE GENERADO NUEVA OD
                if(objZafVen25_02._activarODSinImp(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                   blnRes=true;
                }
             break;

             case 1:   // EXISTE GENERADO NUEVA OD
                if(objZafVen25_02._generaNuvaODCanDenSol(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                   blnRes=true;
                }
             break;


         }
     stmLoc.close();
     stmLoc=null;

  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

    private ArrayList arlDevVenReg, arlDevVenDat;
    private static final int INT_ARL_COD_EMP_DEV_VEN = 0;
    private static final int INT_ARL_COD_LOC_DEV_VEN = 1;
    private static final int INT_ARL_COD_TIP_DOC_DEV_VEN = 2;
    private static final int INT_ARL_COD_DOC_DEV_VEN = 3;

private boolean guardarAutorizado(Connection conn){
 boolean blnRes=false;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 String strTipDev="";
 String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strObs="";
 try{
     System.out.println("ZafVen25.guardarAutorizado: ");
   if(conn!=null){
     stmLoc=conn.createStatement();
     stbAutPre=new StringBuffer();
     blnAutPre=false;
   for(int i=0; i<tblDat.getRowCount(); i++){
       strTipDev=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_TIPDEV));
       strCodEmp=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODEMP));
       strCodLoc=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODLOC));
       strCodTipDoc=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODTIPDOC));
       strCodDoc=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODDOC));
       strObs=objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_OBSSOLDEV));

      if( tblDat.getValueAt(i, INT_TBL_AUTDEV).toString().equals("true") ){  // Autorizado
          System.out.println("ZafVen25.guardarAutorizado: // Autorizado");
          if(autorizado(conn, strTipDev, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), 
                  Integer.parseInt(strCodDoc),  strObs )){
                blnRes=true;
//                intCodEmpSolDevVen=Integer.parseInt(strCodEmp);
//                intCodLocSolDevVen=Integer.parseInt(strCodLoc); 
//                intCodTipDocSolDevVen=Integer.parseInt(strCodTipDoc);
//                intCodDocSolDevVen=Integer.parseInt(strCodDoc);  /* JoseMario 30/Sep/2016 */ 
                arlDevVenReg = new ArrayList();
                arlDevVenReg.add(INT_ARL_COD_EMP_DEV_VEN,Integer.parseInt(strCodEmp) );
                arlDevVenReg.add(INT_ARL_COD_LOC_DEV_VEN,Integer.parseInt(strCodLoc) );
                arlDevVenReg.add(INT_ARL_COD_TIP_DOC_DEV_VEN,Integer.parseInt(strCodTipDoc) );
                arlDevVenReg.add(INT_ARL_COD_DOC_DEV_VEN,Integer.parseInt(strCodDoc) );
                arlDevVenDat.add(arlDevVenReg);
                enviarPulsoImprimeDevVen("imprimeDevVen"+"-"+strCodEmp+"-"+strCodLoc+"-"+strCodTipDoc+"-"+strCodDoc+"-"+objZafParSis.getCodigoMenu()+"-"+ objZafParSis.getNombreUsuario(),6000);
         }else{ blnRes=false; break; }
      }else if( tblDat.getValueAt(i, INT_TBL_DEGDEV).toString().equals("true") ){ // Denegado
            System.out.println("ZafVen25.guardarAutorizado: // Denegado");
          if(cancelar_denegar(conn, "D", strCodEmp, strCodLoc, strCodTipDoc, strCodDoc,  strObs)){
              blnRes=true;
          }else{ blnRes=false; break; }

      }else if( tblDat.getValueAt(i, INT_TBL_CANDEV).toString().equals("true") ){ // Cancelación
            System.out.println("ZafVen25.guardarAutorizado: // Cancelación");
          if(cancelar_denegar(conn, "C", strCodEmp, strCodLoc, strCodTipDoc, strCodDoc,  strObs)){
              blnRes=true;
          }else{ blnRes=false; break; }
      }
       
   }
   

   
   
   if(blnAutPre==true){

      strSql="SELECT co_emp, co_loc, co_tipdoc, tx_descor, co_doc, ne_numdoc  FROM ( "+stbAutPre.toString()+" "+
      " ) AS x   GROUP BY co_emp, co_loc, co_tipdoc, tx_descor, co_doc, ne_numdoc   ";
      System.out.println("zafVen25.guardarAutorizado: query(1)" + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){

         ZafVen25_01 obj = new ZafVen25_01(JOptionPane.getFrameForComponent(this), true, objZafParSis, stbAutPre );
         obj.show();
         if(obj.acepta())
            blnRes=true;
         else
             blnRes=false;

         obj=null;

      }
      rstLoc.close();
      rstLoc=null;

   }
   
     stbAutPre=null;
     stmLoc.close();
     stmLoc=null;

  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean cancelar_denegar(Connection conn, String strTipEst, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strObs ){
 boolean blnRes=true;
 Statement stmLoc;
 String strSql="";
 try{
     System.out.println("ZafVen25.cancelar_denegar");
   if(conn!=null){
       stmLoc=conn.createStatement();

       strSql="UPDATE tbm_cabsoldevven SET  co_usrAut="+objZafParSis.getCodigoUsuario()+", tx_comAut='"+objZafParSis.getNombreComputadoraConDirIP()+"', "
       + " st_aut='"+strTipEst+"', tx_obsaut='"+strObs+"', fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+" "
       + " WHERE co_emp="+strCodEmp+" AND co_loc="+strCodLoc+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc;
       stmLoc.executeUpdate(strSql);
       System.out.println("zafVen25.cancelar_denegar: " + strSql);
     stmLoc.close();
     stmLoc=null;

  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

/*
 * MODIFICADO EFLORESA 2012-04-17
 * DESCUENTO ESPECIAL
 */
private boolean autorizado(Connection conn, String strTipDev, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strObs ){
 boolean blnRes=false;
 int intResCasItm =0 ; // varieble que captura con que esquema trabaja para caso dev. por cantidad ó item
 try{
     System.out.println("\n ZafVen25.autorizado... \n");
   if(conn!=null){
     if(strTipDev.equals("P") || strTipDev.equals("E")){  // SOLICITUD DE DEVOLUCION POR PORCENTAJE DE DESCUENTO
         System.out.println("ZafVen25.autorizado: // SOLICITUD DE DEVOLUCION POR PORCENTAJE DE DESCUENTO");
         if(_autorizadoPrecio(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strObs )){
            blnRes=true;
         }
     }
     else if (strTipDev.equals("V")) {  // SOLICITUD DE DEVOLUCION POR VALOR  PRECIO
         System.out.println("ZafVen25.autorizado:  // SOLICITUD DE DEVOLUCION POR VALOR  PRECIO");
         if(objZafVen25_02.autorizadoSolCanDes(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strObs)){
             blnRes=true;
         }
     }
     else if (strTipDev.equals("C")) {  // SOLICITUD DE DEVOLUCION POR ITEM CANTIDAD
         System.out.println("ZafVen25.autorizado:  // SOLICITUD DE DEVOLUCION POR ITEM CANTIDAD");
            if(objZafVen25_02.autorizadoSolCanDes(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strObs)){
                intResCasItm = objZafVen25_02._getImpGui(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);  // SI ES COSENCO SIEMPRE (2) // NO HACEN GUIAS
                  switch(intResCasItm){
                      case 0:  // ERROR
                           System.out.println("ZafVen25.autorizado:  <ERROR> intResCasItm: " + intResCasItm);
                          blnRes=false;
                      break;
                          
                      case 1:  // ESQUEMA ANTERIOR 
                          System.out.println("ZafVen25.autorizado:  // ESQUEMA ANTERIOR intResCasItm: " + intResCasItm);
                          if(objZafVen25_02._getVerificaItmEgrFisBod(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                             blnRes=true;
                          }
                      break;

                      case 2:  // ESQUEMA ACTUAL
                          System.out.println("ZafVen25.autorizado:  // ESQUEMA ACTUAL intResCasItm: " +intResCasItm);
                          if(autorizado_EsqNue_Itm(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                            blnRes=true;
                          }
                      break;
                  }
            }
     }
  }
 }
 catch(Exception Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(this, Evt); 
 }
 return blnRes;
}

/**
 */
private boolean autorizado_EsqNue_Itm(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=false;
 int intResCasItm =0 ; // varieble que captura con que esquema trabaja para caso dev. por cantidad ó item
 try{
     System.out.println("ZafVen25.autorizado_EsqNue_Itm: ");
   if(conn!=null){

     intResCasItm = objZafVen25_02._getImpOrdDes(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
//  Retorna 1: si tiene la Orden de Despacho
     
        switch(intResCasItm){  
            // ERROR
            case 0: blnRes=false; break;
            // ESTA IMPRESO    
            case 1: System.out.println("ZafVen25.autorizado_EsqNue_Itm: //1 ESTA IMPRESO  intResCasItm:" + intResCasItm );
                    if(objZafVen25_02._getVerGenConf(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) 
                        blnRes=true; 
                    break;
            // NO ESTA IMPRESO // NO SE ASIGNO NUMERO A LA O/D    
            //case 2: if(objZafVen25_02._anularOrdDesGenODNueSal(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)) blnRes=true; break;
            case 2: 
                    System.out.println("ZafVen25.autorizado_EsqNue_Itm: //2 NO ESTA IMPRESO // NO SE ASIGNO NUMERO A LA O/D   intResCasItm: " + intResCasItm);
                    blnRes=objZafVen25_02.isOrdenNoImpresa(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); 
                    break;
            case 3: 
                    System.out.println("ZafVen25.autorizado_EsqNue_Itm: //3 NO ESTA IMPRESO // NO SE ASIGNO NUMERO A LA O/D   intResCasItm: " + intResCasItm);
                    blnRes=objZafVen25_02.isOrdenNoImpresa(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc); 
                    break;
        }
  }}catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean _autorizadoPrecio(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strObs ){
 boolean blnRes=false;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 try{
     System.out.println("zafVen25._autorizadoPrecio: ");
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT * FROM ( " +
      " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a3.tx_Descor, a.co_doc, a.ne_numdoc, text('"+strObs+"') as tx_obs " +
      " ,round(( a2.nd_preunivenlis * (1-( a2.nd_pordesvenmax /100))),2) as preLis " +
      " ,round(( a2.nd_preuni * (1-( (a2.nd_pordes+a1.nd_candev) /100))),2) as preVta " +
      " FROM tbm_cabsoldevven AS a " +
      " INNER JOIN tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc) " +
      " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_locrel AND a2.co_tipdoc=a1.co_tipdocrel AND a2.co_doc=a1.co_docrel AND a2.co_reg=a1.co_reg) " +
      " INNER JOIN tbm_cabtipdoc AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc AND a3.co_tipdoc=a.co_tipdoc)  " +
      " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" " +
      " AND a.co_tipdoc="+intCodTipDoc+" AND a.co_doc="+intCodDoc+" AND a.st_tipdev='P' " +
      " ) AS x WHERE  preLis > preVta ";
      System.out.println("zafVen25._autorizadoPrecio: query(1)" + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         if(blnAutPre==true) stbAutPre.append(" UNION ALL ");
         stbAutPre.append( strSql );
         blnAutPre=true;

      }else{

         strSql="UPDATE tbm_cabsoldevven SET  co_usrAut="+objZafParSis.getCodigoUsuario()+", tx_comAut='"+objZafParSis.getNombreComputadoraConDirIP()+"'"
         + " , st_aut='A', tx_obsaut='"+strObs+"' " +
         ", fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+"   "
         + " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc;
         System.out.println("zafVen25._autorizadoPrecio: query else(2)" + strSql);
         stmLoc.executeUpdate(strSql);
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

    private void chkSolAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolAutActionPerformed
        // TODO add your handling code here:
    if (objThrGUI==null) {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.start();
    }

    }//GEN-LAST:event_chkSolAutActionPerformed

private boolean consultarDatAut(){
 boolean blnRes=false;
 Connection conn;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="",strSQLAux="", strAux="", strAux01="";
 Vector vecData;
 try{
     System.out.println("ZafVen25.consultarDatAut ");
   conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      vecData = new Vector();


      if( objZafParSis.getCodigoMenu()==2144){
         strAux =" AND a.fe_doc='"+objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos())+"' ";
      }


      if(!(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()))
          strSQLAux=" a.co_emp= "+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+"   AND ";


     strAux01 = " case when " +
     " ( select (sum(nd_canrec) + sum(nd_canrevbodace)) as valortot from tbm_detsoldevven  " +
     " where co_emp=a.co_emp and co_loc=a.co_loc and co_tipdoc=a.co_tipdoc and co_doc=a.co_doc ) " +
     "  > 0 then 'S' else 'N' end as estado, ";


      strSql="SELECT * FROM ( SELECT "+strAux01+" a.st_tipdev,  " +
      " (    select x1.st_coninv from tbm_detguirem as x " +
      "  inner join tbm_cabguirem as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  " +
      " where x.co_emprel=a.co_emp and x.co_locrel=a.co_locrel and x.co_tipdocrel=a.co_tipdocrel and x.co_docrel=a.co_docrel and x1.st_reg='A'  group by x1.st_coninv " +
      "  ) as st_coninv," +
 /*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/              
//      " (select distinct 1 "+
//      " from tbr_cabguirem as r "+
//      " inner join tbm_detguirem as dod "+
//      " on (dod.co_loc=r.co_locrel and dod.co_tipdoc=r.co_tipdocrel and dod.co_doc=r.co_docrel) "+
//      " where dod.co_emprel=a.co_emp and dod.co_locrel=a.co_locrel and dod.co_tipdocrel=a.co_tipdocrel and dod.co_docrel=a.co_docrel) as tiene_guia,"+
              
    " (select distinct 1 "+ 
    " from tbm_detguirem as dod "+ 
    " inner join tbr_cabguirem as r "+
    //" on dod.co_loc=r.co_locrel and dod.co_tipdoc=r.co_tipdocrel and dod.co_doc=r.co_docrel  "+
    " on dod.co_loc=r.co_locrel and dod.co_tipdoc=r.co_tipdocrel and dod.co_doc=r.co_docrel and r.co_emp=a.co_emp"+              
    " where dod.co_emprel=a.co_emp "+
    " and dod.co_locrel=a.co_locrel "+
    " and dod.co_tipdocrel=a.co_tipdocrel "+
    " and dod.co_docrel=  (select min (f2.co_doc)  "+
                           " from tbm_cabmovinv as f "+ 
                           " inner join tbm_cabmovinv as f2 "+
                           " on (f.co_emp= f2.co_emp "+
                           " and f.co_loc=f2.co_loc "+
                           " and f.ne_numcot=f2.ne_numcot) "+ 
                           " where f.co_emp=a.co_emp "+
                           " and f.co_loc=a.co_locrel "+
                           " and f.co_tipdoc=a.co_tipdocrel "+
                           " and f.co_doc=a.co_docrel)) as tiene_guia, "+
              
    /*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/ 
      " a.co_emp, a.co_loc, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc " +
      " ,a.co_cli, a.tx_nomcli, a.tx_nomusrsol, a.co_locrel, a.co_tipdocrel, a.co_docrel, a.tx_obsaut ,a.st_volfacmersindev  " +
      " FROM tbm_cabsoldevven AS a " +
      " INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc) " +
      " WHERE "+strSQLAux+"    a.st_reg='A' AND a.st_aut='A' "+strAux+" "+   // AND a.st_tipdev='C' " +
      " and a.st_recMerDev='N' and st_revTecMerDev='N' AND a.st_revBodMerDev = 'N'  AND a.st_merAceIngSis = 'N' and st_exiMerRec='N' and st_merRecDevCli='N' and st_merAceIngSis='N' "+
      " ORDER BY a.co_emp, a.co_loc, a.fe_doc, a.ne_numdoc  ) AS x WHERE  estado='N' ";

      System.out.println("ZafVen25.consultarDatAut "+strSql);

      //rstLoc=stmLoc.executeQuery(strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
	  vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
	  vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc") );
	  vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("tx_descor") );
	  vecReg.add(INT_TBL_DCATIPDOC, rstLoc.getString("tx_deslar") );
	  vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
	  vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
	  vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
	  vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
          vecReg.add(INT_TBL_DESCLI, rstLoc.getString("tx_nomcli") );
 /*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/
          vecReg.add(INT_TBL_TIGUIREM, (rstLoc.getInt("tiene_guia")>0)?true:false);
          /*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/
          
          vecReg.add(INT_TBL_VOLFAC, (rstLoc.getString("st_volfacmersindev").equals("N")?false:true));
	  vecReg.add(INT_TBL_USRSOL,  rstLoc.getString("tx_nomusrsol") );
	  vecReg.add(INT_TBL_BUTFAC,"..");
	  vecReg.add(INT_TBL_BUTSOL,"..");
	  vecReg.add(INT_TBL_AUTDEV, true);
	  vecReg.add(INT_TBL_DEGDEV, false);
	  vecReg.add(INT_TBL_CANDEV, false);
	  vecReg.add(INT_TBL_OBSSOLDEV, rstLoc.getString("tx_obsaut") );
          vecReg.add(INT_TBL_CODLOCREL, rstLoc.getString("co_locrel") );
	  vecReg.add(INT_TBL_CONTIPDOCREL, rstLoc.getString("co_tipdocrel") );
          vecReg.add(INT_TBL_CODDOCREL, rstLoc.getString("co_docrel") );
	  vecReg.add(INT_TBL_ESTCONF, (rstLoc.getString("st_coninv")==null?"":rstLoc.getString("st_coninv")) );
          vecReg.add(INT_TBL_TIPDEV,  rstLoc.getString("st_tipdev") );
         vecData.add(vecReg);
       }
         objTblMod.setData(vecData);
         tblDat.setModel(objTblMod);
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    conn.close();
    conn=null;
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);
    blnRes=true;
  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

private boolean consultarDat(){
 boolean blnRes=false,blnCor=false;
 Connection conn;
 Statement stmLoc, stmLoc01;
 ResultSet rstLoc;
 String strSql="",strSQLAux="", strAux="", strAux01="", strCadenas="";
 Vector vecData;
 try{
     System.out.println("ZafVen25.consultarDat");
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      vecData = new Vector();

      if( objZafParSis.getCodigoMenu()==2144){
         strAux =" AND a.fe_doc='"+objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos())+"' ";
      }


    if(!(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()))
          strSQLAux=" a.co_emp= "+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+"   AND ";


     strAux01 = " CASE WHEN \n" +
     " ( SELECT (sum(nd_canrec) + sum(nd_canrevbodace)) as valortot \n FROM tbm_detsoldevven \n " +
     " WHERE co_emp=a.co_emp and co_loc=a.co_loc and co_tipdoc=a.co_tipdoc and co_doc=a.co_doc ) " +
     "  > 0 THEN 'S' ELSE 'N' END AS estado, \n";

      strSql= "SELECT * \n";
      strSql+="FROM ( \n";
      strSql+="     SELECT "+strAux01+" a.tx_obsaut, a2.st_coninv, a.co_emp, a.co_loc, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc \n";
      strSql+="             ,a.co_cli, a.tx_nomcli, a.tx_nomusrsol, a.co_locrel, a.co_tipdocrel, a.co_docrel,a.st_aut, DATE(a.fe_aut) as fe_aut, a.co_usrAut  \n" ;
      strSql+="     FROM tbm_cabsoldevven AS a \n";
      strSql+="     INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc) \n";
      strSql+="     INNER JOIN tbm_cabmovinv as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel) \n";
      strSql+="     WHERE "+strSQLAux+" a.st_reg='A' AND a.st_aut IN ('A','P')   \n";
      //strSql+="           AND a.fe_Doc < date("+objZafParSis.getFuncionFechaHoraBaseDatos()+")-"+intDiaCanSolDevVen+" \n";  /* JoséMario 30/Dic/2015 */
      strSql+="           AND a.st_recMerDev='N' AND a.st_merAceIngSis='N'  AND a.st_merRecDevCli='N' AND a.st_revTecMerDev='N' AND a.st_revBodMerDev='N' \n";
      strSql+="           AND ((a.st_aut='A' AND DATE(a.fe_aut)<=date(CURRENT_DATE)-"+intDiaCanSolDevVen+") OR (a.st_aut='P' AND a.fe_Doc<=date(CURRENT_TIMESTAMP)-"+intDiaCanSolDevVen+"))";
      strSql+="     ORDER BY  a.co_emp, a.co_loc, a.fe_doc, a.ne_numdoc \n ";/* JoséMario 30/Dic/2015 */
      strSql+=") as x where estado='N' \n";
      /* JoséMario 30/Dic/2015 */
      System.out.println("ZafVen25.consultarDat:(1) "+strSql );

      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
           String strTxt=(rstLoc.getString("tx_obsaut")==null?"":rstLoc.getString("tx_obsaut"));
           strTxt+= "CANCELADA PORQUE VENCIO EL PLAZO DE VIGENCIA DE "+intDiaCanSolDevVen+" DÍAS...";  /* JoséMario 30/Dic/2015 */
           strSql="UPDATE tbm_cabsoldevven SET  st_aut='C', tx_obsaut='"+strTxt+"' \n" ;
           strSql+="                          ,fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+"  \n";
           strSql+="WHERE co_emp="+rstLoc.getString("co_emp")+" AND co_loc="+rstLoc.getString("co_loc")+" \n";
           strSql+="      AND co_tipdoc="+rstLoc.getString("co_tipdoc")+" AND co_doc="+rstLoc.getString("co_doc")+"\n";
           System.out.println("ZafVen25.consultarDat:(2) " + strSql);
           strCadenas+="SELECT * FROM tbm_cabSolDevVen WHERE co_emp=" + rstLoc.getString("co_emp") +" AND co_loc= " + rstLoc.getString("co_loc") + " ";
           strCadenas+=" AND co_tipDoc=" + rstLoc.getString("co_tipdoc") + " AND co_doc=" + rstLoc.getString("co_doc") + ";  ";
           strCadenas+=" -- st_aut="+rstLoc.getString("st_aut")+", fe_aut="+rstLoc.getString("fe_aut")+", co_usrAut="+rstLoc.getString("co_usrAut")+" \n" ;
           stmLoc01.executeUpdate(strSql);
           blnCor=true;
      }
      rstLoc.close();
      rstLoc=null;

      
      //if(blnCor==true){
      //    enviarCorreoMasivo("sistemas6@tuvalsa.com", "Anulacion Solicitudes Devolucion de Ventas", strCadenas);
      //}
      
      strSql="SELECT a.st_tipdev, " +
      " ( select x1.st_coninv from tbm_detguirem as x " +
      "  inner join tbm_cabguirem as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  " +
      "  where x.co_emprel=a.co_emp and x.co_locrel=a.co_locrel and x.co_tipdocrel=a.co_tipdocrel and x.co_docrel=a.co_docrel  "+
      "  and x1.st_reg='A' group by x1.st_coninv " +
      "  ) as st_coninv," +
/*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/              
//      " (select distinct 1 "+
//      " from tbr_cabguirem as r "+
//      " inner join tbm_detguirem as dod "+
//      " on (dod.co_loc=r.co_locrel and dod.co_tipdoc=r.co_tipdocrel and dod.co_doc=r.co_docrel) "+
//      " where dod.co_emprel=a.co_emp and dod.co_locrel=a.co_locrel and dod.co_tipdocrel=a.co_tipdocrel and dod.co_docrel=a.co_docrel) as tiene_guia,"+
              
    " (select distinct 1 "+ 
    " from tbm_detguirem as dod "+ 
    " inner join tbr_cabguirem as r "+
    //" on dod.co_loc=r.co_locrel and dod.co_tipdoc=r.co_tipdocrel and dod.co_doc=r.co_docrel "+
    " on dod.co_loc=r.co_locrel and dod.co_tipdoc=r.co_tipdocrel and dod.co_doc=r.co_docrel and r.co_emp=a.co_emp"+                            
    " where dod.co_emprel=a.co_emp "+
    " and dod.co_locrel=a.co_locrel "+
    " and dod.co_tipdocrel=a.co_tipdocrel "+
    " and dod.co_docrel=  (select min (f2.co_doc)  "+
                           " from tbm_cabmovinv as f "+ 
                           " inner join tbm_cabmovinv as f2 "+
                           " on (f.co_emp= f2.co_emp "+
                           " and f.co_loc=f2.co_loc "+
                           " and f.ne_numcot=f2.ne_numcot) "+ 
                           " where f.co_emp=a.co_emp "+
                           " and f.co_loc=a.co_locrel "+
                           " and f.co_tipdoc=a.co_tipdocrel "+
                           " and f.co_doc=a.co_docrel)) as tiene_guia, "+
              
    /*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/  
      "  a.co_emp, a.co_loc, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc " +
      " ,a.co_cli, a.tx_nomcli, a.tx_nomusrsol, a.co_locrel, a.co_tipdocrel, a.co_docrel , a.st_volfacmersindev  " +
      " FROM tbm_cabsoldevven AS a " +
      " INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc) " +
     // " INNER JOIN tbm_cabmovinv as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_locrel and a2.co_tipdoc=a.co_tipdocrel and a2.co_doc=a.co_docrel) "+
      " WHERE "+strSQLAux+" a.st_reg='A' AND a.st_aut='P' "+strAux+" "+
      " ORDER BY  a.co_emp, a.co_loc, a.fe_doc, a.ne_numdoc  ";

      System.out.println("ZafVen25.consultarDat:(3) "+strSql );

      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
            Vector vecReg = new Vector();
            vecReg.add(INT_TBL_LINEA,"");
            vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
            vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
            vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc") );
            vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_DCATIPDOC, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
            vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
            vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
            vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
            vecReg.add(INT_TBL_DESCLI, rstLoc.getString("tx_nomcli") );
 /*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/
            vecReg.add(INT_TBL_TIGUIREM, (rstLoc.getInt("tiene_guia")>0)?true:false);
            /*AGREGADO PARA MARCAR CHECKBOX CUANDO TENGA GUIA UNA FACTURA QUE SE DESEA HACER DEVOLUCION*/
            
            vecReg.add(INT_TBL_VOLFAC, (rstLoc.getString("st_volfacmersindev").equals("N")?false:true));
            vecReg.add(INT_TBL_USRSOL,  rstLoc.getString("tx_nomusrsol") );
            vecReg.add(INT_TBL_BUTFAC,"..");
            vecReg.add(INT_TBL_BUTSOL,"..");
            vecReg.add(INT_TBL_AUTDEV, false);
            vecReg.add(INT_TBL_DEGDEV, false);
            vecReg.add(INT_TBL_CANDEV, false);
            vecReg.add(INT_TBL_OBSSOLDEV,"");
            vecReg.add(INT_TBL_CODLOCREL, rstLoc.getString("co_locrel") );
            vecReg.add(INT_TBL_CONTIPDOCREL, rstLoc.getString("co_tipdocrel") );
            vecReg.add(INT_TBL_CODDOCREL, rstLoc.getString("co_docrel") );
            vecReg.add(INT_TBL_ESTCONF,  (rstLoc.getString("st_coninv")==null?"":rstLoc.getString("st_coninv")) );
            vecReg.add(INT_TBL_TIPDEV,  rstLoc.getString("st_tipdev") );
            vecData.add(vecReg);
       }

         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;
    conn.close();
    conn=null;
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);
    blnRes=true;
  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

    private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    //JOptionPane oppMsg=new JOptionPane();
    //String strTit="Mensaje del sistema Zafiro";
    if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
        //System.gc();
        Runtime.getRuntime().gc();
        dispose();
   }}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JCheckBox chkSolAut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panButPanBut;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilTabGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables



private class ZafMouMotAda extends MouseMotionAdapter{
 @Override
 public void mouseMoved(MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_CODLOC:
            strMsg="Cóidigo Local.";
            break;
        case INT_TBL_CODTIPDOC:
            strMsg="Cóidigo tipo de Documento.";
            break;
        case INT_TBL_DCOTIPDOC:
            strMsg="Descripción corta del tipo de documento.";
            break;
        case INT_TBL_DCATIPDOC:
            strMsg="Descripción larga del tipo de documento.";
            break;
        case INT_TBL_CODDOC:
            strMsg="Cóidigo del documento.";
            break;
        case INT_TBL_NUMDOC:
            strMsg="Número del documento.";
            break;
        case INT_TBL_FECDOC:
            strMsg="Fecha del documento.";
            break;
        case INT_TBL_CODCLI:
            strMsg="Cóidigo del cliente.";
            break;
        case INT_TBL_DESCLI:
            strMsg="Nombre del Cliente.";
            break;
        case INT_TBL_USRSOL:
            strMsg="Usuario que solicita la devolución.";
            break;
        case INT_TBL_BUTFAC:
            strMsg="Visualizar Factura.";
            break;
        case INT_TBL_BUTSOL:
            strMsg="Visualizar solicitud de devolución.";
            break;
        case INT_TBL_AUTDEV:
            strMsg="Autorización solicitud de devolución.";
            break;
        case INT_TBL_DEGDEV:
            strMsg="Denegar solicitud de devolución.";
            break;
        case INT_TBL_CANDEV:
            strMsg="Cancelar solicitud de devolución.";
            break;

        case INT_TBL_OBSSOLDEV:
            strMsg="Observación para la solicitud de devolución.";
            break;

         case INT_TBL_VOLFAC:
           strMsg="Volver a facturar a otro cliente. ";
         break;


        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}

 
    /**
    * EFLORESA - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: cuentaFicticia@tuvalsa.com; cuentaFicticia2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String subject, String strMsj ){
        try {

            String server = "mail.tuvalsa.com";
            String userName = "zafiro@tuvalsa.com";
            String password = "#tuv*sis28/10=";
            String fromAddres = "zafiro@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = "";
            String bcc = "";
            boolean htmlFormat = false;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }
    
    
    public boolean sendMailTuvMas(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) {
        boolean blnRes=false;
        try{

           Properties props = System.getProperties();
//           props.put("mail.smtp.auth", "true");
//           props.put("mail.smtp.ssl.trust", server);
//           props.put("mail.smtp.port", "587");
//           props.put("mail.smtp.starttls.enable", "true");
           //System.out.println("---2---");
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.ssl.trust", server);
           props.put("mail.smtp.host", server);
           props.put("mail.smtp.user", userName);
           props.put("mail.smtp.password", password);
           props.put("mail.smtp.port", "587");
           props.put("mail.smtp.auth", "true");  

            Authenticator auth = new MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);
            session.setDebug(true);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to addresses
            StringTokenizer tokens = new StringTokenizer(toAddress, ";");
            while (tokens.hasMoreTokens())
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(tokens.nextToken()));
            tokens=null;
            
            if (! ccAddress.equals("")){
                tokens = new StringTokenizer(ccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }
            
            if (! bccAddress.equals("")){
                tokens = new StringTokenizer(bccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }

            // Set the subject
            message.setSubject(subject);

            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            Transport.send(message);

            blnRes=true;

        }catch(MessagingException e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }catch(Exception e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }
        return blnRes;
    }
    
        static class MyAuthenticator extends Authenticator {
        PasswordAuthentication l = new PasswordAuthentication("zafiro@tuvalsa.com", "#tuv*sis28/10=");
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return l;
        }
    }
        
    /**
     * Procesa devolucion de venta de manera automática.
     * @param intCodEmp Código de la empresa de la solicitud.
     * @param intCodLoc Código del local de la solicitud.
     * @param intCodTipDoc Código del tipo de documento de la solicitud.
     * @param intCodDoc Código de documento de la solicitud.
     * @return 
     */    
    private boolean procesaDevolucion(int intCodEmp, int intCodLoc, int intCodTipDoc,int intCodDoc)
    {
        boolean blnRes=true;
        java.sql.Connection  conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try
        {
            conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conn!=null){
                stmLoc=conn.createStatement();
                System.out.println("PROCESA>>> " + intCodEmp + " - " + intCodLoc + " - " + intCodTipDoc + " - " +intCodDoc);
                strSql="";
                strSql+="  select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a2.tx_descor, a2.tx_deslar, a.co_cli, a.tx_nomcli, a.fe_doc,   a.ne_numdoc, a3.ne_numdoc as numfac  ,a.fe_aut \n";
                strSql+="   from( \n";
                strSql+="       select * \n";
                strSql+="       from( \n";
                strSql+="           select \n";
                strSql+="                   ( \n";
                strSql+="                       select  sum(x1.nd_cansolnodevprv) as  cansolstk   \n";
                strSql+="                       from tbm_cabsoldevven as x  \n";
                strSql+="                       inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc)   \n";
                strSql+="                       where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc   \n";
                strSql+="                   ) as   cansolstk, \n";
                strSql+="                   CASE WHEN (  \n";
                strSql+="                               select sum((x1.nd_candev - x1.nd_canvolfac)) as dat from  tbm_cabsoldevven as x  \n";
                strSql+="                               inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )  \n";
                strSql+="                               where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc \n";
                strSql+="                   ) != 0  THEN 'N' ELSE 'S' END as st_volfac, \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                /* (SELECT sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon)) as nd_canconguirem */
                strSql+="               ( \n";
                strSql+="                   SELECT case when y2.co_tipdoc=102 then sum((y2.nd_can - (y1.nd_candev - y1.nd_canvolfac) - y2.nd_cancon))  \n";
                strSql+="                           else sum((abs(det.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(det.nd_cancon))) end as nd_canconguirem     \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */   
                strSql+="                   FROM tbm_detsoldevven as y1  \n";
                strSql+="                   INNER JOIN tbm_detguirem as y2 on (y2.co_emprel=y1.co_emp  and y2.co_locrel=y1.co_locrel and y2.co_tipdocrel=y1.co_tipdocrel and y2.co_docrel=y1.co_docrel and y2.co_regrel=y1.co_regrel )  \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                strSql+="                   INNER JOIN tbm_detmovinv as det on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg ) \n";
                /*MODIFICADO POR EL PROYECTO TRANSFERENCIAS 19-07-2016 */
                strSql+="                   WHERE y1.co_emp=a.co_emp and y1.co_loc=a.co_loc and y1.co_tipdoc=a.co_tipdoc and y1.co_doc=a.co_doc and y1.co_reg=a1.co_reg and y1.nd_candev > 0 and y2.st_meregrfisbod = 'S'  \n";
                strSql+="                   group by y2.co_tipdoc  \n";
                
                /*MODIFICADO POR RESERVAS DE MERCADERIAS */
                strSql+=" union  \n";

		strSql+="  SELECT case when a5.co_tipdoc=102 then  \n";
		strSql+="  sum((a5.nd_can - (y1.nd_candev - y1.nd_canvolfac) - a5.nd_cancon))  \n";
		strSql+="  else  \n";
		strSql+="  sum((abs(detres.nd_can) - (y1.nd_candev - y1.nd_canvolfac) - abs(detres.nd_cancon)))  \n";
		strSql+="  end as nd_canconguirem  \n";
		strSql+="  FROM tbm_detsoldevven as y1  \n";
		strSql+="    INNER JOIN tbm_detmovinv as det on (y1.co_emp=det.co_emp  and y1.co_locrel=det.co_loc and y1.co_tipdocrel=det.co_tipdoc and y1.co_docrel=det.co_doc and y1.co_regrel=det.co_reg )  \n";
		strSql+="  INNER JOIN tbm_cabsegmovinv as s  \n";
		strSql+="  on (s.co_emprelcabmovinv =det.co_emp and s.co_locrelcabmovinv=det.co_loc and s.co_tipdocrelcabmovinv=det.co_tipdoc and s.co_docrelcabmovinv=det.co_doc)  \n";
		strSql+="  INNER JOIN tbm_cabsegmovinv as s2  \n";
		strSql+="  on (s.co_seg=s2.co_seg and s2.co_tipdocrelcabguirem=271)  \n";
		strSql+="  INNER JOIN tbm_cabguirem as a3  \n";
		strSql+="  on ( a3.co_emp=s2.co_emprelcabguirem and a3.co_loc=s2.co_locrelcabguirem and a3.co_tipdoc=s2.co_tipdocrelcabguirem and a3.co_doc=s2.co_docrelcabguirem )  \n";
		strSql+="  INNER JOIN tbm_detguirem as a5  \n";
		strSql+="  on(a5.co_emp=a3.co_emp and a5.co_loc=a3.co_loc and a5.co_tipdoc=a3.co_tipdoc and a5.co_doc=a3.co_doc and a5.co_reg=y1.co_reg)  \n";
		strSql+="  INNER JOIN tbm_detmovinv as detres  \n";
		strSql+="  on (detres.co_emp=a5.co_emprel  and detres.co_loc=a5.co_locrel  and detres.co_tipdoc=a5.co_tipdocrel and detres.co_doc=a5.co_docrel and detres.co_reg=a5.co_regrel and detres.co_tipdoc=294)  \n";
		strSql+="  WHERE y1.co_emp=a.co_emp  \n";
		strSql+="  and y1.co_loc=a.co_loc  \n";
		strSql+="  and y1.co_tipdoc=a.co_tipdoc  \n";
		strSql+="  and y1.co_doc=a.co_doc  \n";
		strSql+="  and y1.co_reg=a1.co_reg  \n";
		strSql+="  and y1.nd_candev > 0  \n";
		strSql+="  and a5.st_meregrfisbod = 'S'  \n";
		strSql+="   group by a5.co_tipdoc  \n";
                strSql+="               ) as nd_canconguirem,  \n";
                /*MODIFICADO POR RESERVAS DE MERCADERIAS */
                
                strSql+="               a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc , a.co_locrel, a.co_tipdocrel,   \n";
                strSql+="               a.co_docrel, a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a.fe_aut ,a.st_tipDev, a.st_RevBodMerDev, a.st_meraceingsis, a1.nd_canRevBodAce , a.st_impguiremaut   \n";
                strSql+="           FROM tbm_cabsoldevven AS a  \n";
                strSql+="           INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc )  \n";
                strSql+="           WHERE a.co_Emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipDoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and \n";
                strSql+="                   a.st_reg ='A'  AND a.st_aut ='A' AND a.st_meraceingsis='N'  \n";
                strSql+="       ) AS x \n";
                strSql+=" WHERE CASE WHEN ((st_impguiremaut = 'S') or (st_impguiremaut = 'N' and nd_canconguirem < 0)) THEN  \n";
                strSql+="       CASE WHEN x.st_volfac IN ('N') THEN  \n";
                strSql+="       CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0  \n";
                strSql+="           ELSE x.st_meraceingsis='N' END ELSE x.st_meraceingsis='N' END  \n";
                strSql+="           ELSE  CASE  WHEN x.cansolstk = 0 THEN x.st_meraceingsis='N'  \n";
                strSql+="               ELSE CASE WHEN x.st_tipDev  IN ('C') THEN  x.st_RevBodMerDev='S' AND x.st_meraceingsis='N' AND x.nd_canRevBodAce > 0  \n";
                strSql+="               ELSE x.st_meraceingsis='N' END  \n";
                strSql+="           END  \n";
                strSql+="       END   \n";
                strSql+=" ) AS a  \n";
                strSql+=" INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc)  \n";
                strSql+=" INNER JOIN tbm_cabmovinv AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_locrel AND a3.co_tipdoc=a.co_tipdocrel AND a3.co_doc=a.co_docrel)  \n";
                strSql+=" GROUP BY a.co_emp, a.co_loc, a.co_tipdoc,  a.co_doc,  a2.tx_descor, a2.tx_deslar,  a.co_cli, a.tx_nomcli, a.fe_doc,  a.ne_numdoc, a3.ne_numdoc,a.fe_aut \n";
                strSql+=" ORDER BY a.ne_numdoc \n";
                strSql+=" \n";
                strSql+=" \n";
                System.out.println("JOTA >>> ZafVen27_01 consultarDat (Modificado Transferencias de Inventario JM):  " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next())
                {
                     objVen28=new ZafVen28(objZafParSis,intCodEmp,intCodLoc,intCodTipDoc,intCodDoc, 
                                              String.valueOf(intCodEmp), String.valueOf(intCodLoc), String.valueOf(intCodTipDoc), 
                                              String.valueOf(intCodDoc), this );
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc=null;
                rstLoc=null;
                conn.close();
                conn=null;

            }

        }
        catch(java.sql.SQLException e) 
        {   
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;  
        }
        catch(Exception Evt)  
        {  
            objUti.mostrarMsgErr_F1(this, Evt);
            blnRes=false;  
        }

        return blnRes;
    }
   
//    public String[] obtenerRptImpDev(int intCodEmp, int intCodLoc){
//        String strRtaRptDev="",strNomImp="",strEmp="",strLoc="";
//        String[] strArrRet=new String[4];
//        strRtaRptDev=obtenerRtaRpt();
//        if(intCodEmp==1 && intCodLoc==4){//TUVAL
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strRtaRptDev="D:/ZafiroNuevo 2016/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            strNomImp="od_califormia";  
//            //strNomImp="sistemas_inmaconsa";  
//            strEmp="1";
//            strLoc="4";
//            
//        }else if(intCodEmp==4 && intCodLoc==3){//DIMULTI
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            strNomImp="od_dimulti";
//            strEmp="4";
//            strLoc="3";            
//            
//        }else if(intCodEmp==2 && intCodLoc==1){//QUITO
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            strNomImp="od_quito";    
//            strEmp="2";
//            strLoc="1";                        
//            
//        }else if(intCodEmp==2 && intCodLoc==4){//MANTA
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            strNomImp="od_manta";   
//            strEmp="2";
//            strLoc="4";                                    
//            
//        }else if(intCodEmp==1 && intCodLoc==6){//IMPORTACIONES
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            strNomImp="PrintBodega2XXX";   
//            strEmp="1";
//            strLoc="6";
//            
//        }else if(intCodEmp==2 && intCodLoc==6){//STO DOMINGO
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            strNomImp="od_stodgo";      
//            strEmp="2";
//            strLoc="6";            
//            
//        }else if(intCodEmp==1 && intCodLoc==10){//INMACONSA
//            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper_xxx";
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //C:\Users\postgres\Desktop\zafiro_desarrollo\proyecto\Reportes\Compras\ZafCom23
//            //strNomImp="od_inmaconsa_xxx";   
//            strNomImp="od_inmaconsa";
//            strEmp="1";
//            strLoc="10";
//            
//        }else if(intCodEmp==2 && intCodLoc==10){//CUENCA
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            strNomImp="od_cuenca";   
//            strEmp="2";
//            strLoc="10";
//        }
//        strArrRet[0]=strRtaRptDev;
//        strArrRet[1]=strNomImp;
//        strArrRet[2]=strEmp;
//        strArrRet[3]=strLoc;        
//        
//        return strArrRet;
//    }   
//    /**
// * Metodo utilizado para imprimir solo OD locales
// * @param conn objeto conexion.
// * @param intCodEmp codigo de empresa.
// * @param intCodLoc codigo de local.
// * @param intCodTipDoc codigo de tipo documento.
// * @param intCodDoc codigo de documento.
// * @param codbod codigo de bodega.
// * @return 
// */
//public boolean impresionDev(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
//  String DIRECCION_REPORTE_GUIA="";
//  String strDirSis="";
//  String strRutSubRpt="";
//  
//  try{
//    if(conn!=null){
//        Statement stmQry=conn.createStatement();
//        ResultSet rsCab=null;
//        String strSQL2="";
//        strSQL2+= " select a3.st_genorddes\n";
//        strSQL2+= " from tbm_cabSolDevVen as a1\n";
//        strSQL2+= " inner join tbm_cabmovinv as a3 on (a1.co_emp=a3.co_emp and a1.co_locrel=a3.co_loc and a1.co_tipdocrel=a3.co_tipdoc and a1.co_docrel=a3.co_doc)\n";
//        strSQL2+= " where a1.co_emp="+ intCodEmp + "";
//        strSQL2+= " and a1.co_loc="+ intCodLoc + "";
//        strSQL2+= " and a1.co_tipdoc="+ intCodTipDoc + "";
//        strSQL2+= " and a1.co_doc="+ intCodDoc + "";
//        rsCab=stmQry.executeQuery(strSQL2);
//        
//        if(rsCab!=null && rsCab.next() && (rsCab.getString("st_genorddes").equalsIgnoreCase("S"))){
//        
//        
//            //strDirSis=getDirectorioSistemaImpOrd(); Esto es solo para servicios
//            //cobodgru=obtenerBodGru(conn, intCodEmp, codbod);
//            //ZafGenGuiRem objGenGuiRem=new ZafGenGuiRem();
//            String[] strRutaRpt2=obtenerRptImpDev(intCodEmp,intCodLoc);
//
//            DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2[0];
//            String strNomImpBod2=strRutaRpt2[1];
//
//            strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptVen11.jasper"));
//
//            //System.out.println("Ruta Reporte OD ->  "+strRutSubRpt );
//            //System.out.println("Normal 2 Ruta Reporte OD ->  "+DIRECCION_REPORTE_GUIA );
//
//            Map parameters = new HashMap();
//            parameters.put("co_emp", new Integer(intCodEmp) );
//            parameters.put("co_loc", new Integer(intCodLoc) );
//            parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
//            parameters.put("co_doc",  new Integer(intCodDoc) );
//            parameters.put("nomUsrImp",  objZafParSis.getNombreUsuario() );
//
//            parameters.put("SUBREPORT_DIR", strRutSubRpt );
//
//            //System.out.println("hjh");
//            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
//            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
//
//            JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
//            int intCantPaginas=0;
//            if(reportGuiaRem.getPages()!=null && reportGuiaRem.getPages().size() >0){
//                intCantPaginas=reportGuiaRem.getPages().size();
//            }
//
//            //System.out.println("Nombre de la impresora: "+strNomImpBod2);
//
//            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
//            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
//            printServiceAttributeSet.add(printerName);
//            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//            objJRPSerExp.exportReport();
//            objPriReqAttSet=null;
//
//            //System.out.println("IMprimiendo en archivo OD");
//            //almacenarArchHis("OD: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" #OD:"+intNumOrdDes+" Impresora: "+strNomImpBod2+" Cant. Pg.: "+intCantPaginas);
//            //almacenarArchHis("OD: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" Impresora: "+strNomImpBod2+" Cant. Pg.: "+intCantPaginas);        
//
//              
//        }
//        /*
//        byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
//        JasperViewer.viewReport(reportGuiaRem);
//
//        PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
//        aset.add(MediaSizeName.ISO_A4);
//
//        aset.add(Sides.ONE_SIDED);
//        strNomImpBod2="\\\\http://172.16.8.4:631\\imp_sistemas";
//        //aset.add(new PrinterName(strNomImpBod2, Locale.US));
//        PrintService[] pservices=PrinterJob.lookupPrintServices();
//        if (pservices.length>0) {
//            int indice=imp.traeIndice(pservices, strNomImpBod2);
//            DocPrintJob pj = pservices[indice].createPrintJob();
//            System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
//            Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
//                //pj.print(doc, aset);
//        }*/
//        
//    }
//  }catch (JRException e) {  
//      e.printStackTrace();
//      //objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
//  }catch (Exception e) {  
//      e.printStackTrace();
//      //objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
//  }
// return true;
//}  
///**
//     * Metodo que obtiene la bodega de grupo por empresa - bodega.
//     * @param con objeto conexion
//     * @param intEmp
//     * @param intBod
//     * @return 
//     */
//    public int obtenerBodGru(Connection con,int intEmp, int intBod){
//        int intBodGrp=0;
//        Statement stmQry=null;
//        ResultSet rsDat=null;        
//        String strSql=" select co_bodgrp "+
//                      " from tbr_bodempbodgrp where co_emp="+intEmp+ " and co_bod="+intBod;
//        try{
//            stmQry=con.createStatement();
//            rsDat=stmQry.executeQuery(strSql);
//            if(rsDat.next()){
//                intBodGrp=rsDat.getInt("co_bodgrp");
//            }            
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }finally{
//            try{
//                rsDat.close();
//                rsDat=null;
//                stmQry.close();
//                stmQry=null;
//            }catch(Exception ex){
//                ex.printStackTrace();
//            }
//        }
//        return intBodGrp;
//    }  
//    
//    private String obtenerRtaRpt(){
//        String strRuta="";
//        Connection con;
//            Statement stmQry=null;
//            ResultSet rsDat=null;
//        try{
//            con =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if (con!=null) {
//                 String strSql="SELECT a1.co_rpt, a1.tx_desCor, a1.tx_desLar, a1.tx_rutAbsRpt, a1.tx_rutRelRpt, a1.tx_nomRpt, 'A' AS st_reg\n" +
//                    "                    FROM tbm_rptSis AS a1\n" +
//                    "                     INNER JOIN tbr_rptSisPrg AS a2 ON (a1.co_rpt=a2.co_rpt)\n" +
//                    "                     WHERE a2.co_mnu="+ objZafParSis.getCodigoMenu() +
//                    "                     AND a1.st_reg='A'\n" +
//                    "                     ORDER BY a1.co_rpt";
//            try{
//                stmQry=con.createStatement();
//                rsDat=stmQry.executeQuery(strSql);
//                if(rsDat.next()){
//                    strRuta=rsDat.getString("tx_rutAbsRpt");
//                }
//            }catch(Exception ex){
//                ex.printStackTrace();
//            }finally{
//                try{
//                    rsDat.close();
//                    rsDat=null;
//                    stmQry.close();
//                    stmQry=null;
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                }
//            }
//            }
//        }catch(SQLException ex){
//            ex.printStackTrace();
//        }
//        return strRuta;
//    }
    private void enviarPulsoImprimeDevVen(String strDat, int intPuerto){
         objPulFacEle = new ZafPulFacEle();
        objPulFacEle.iniciarImpresion(strDat, intPuerto);        //objPulFacEle.iniciar();        
        System.out.println(" PULSO::::::  enviarPulsoimprimeDevVen  ");
    }
}

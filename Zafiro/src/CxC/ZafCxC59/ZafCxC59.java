/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC59.java
 *
 * Created on Jun 2, 2010, 12:04:56 PM
 */
  
package CxC.ZafCxC59;

import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafRptSis.ZafRptSis;
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
 * @author jayapata
 */
public class ZafCxC59 extends javax.swing.JInternalFrame {

  Librerias.ZafParSis.ZafParSis objZafParSis;
  ZafUtil objUti; /**/
  private ZafSelFec objSelFec;
  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
  private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
  private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
  private ZafTblTot objTblTot;
  private ZafThreadGUI objThrGUI;
  private java.util.Date datFecAux; 
  private ZafThreadGUIRpt objThrGUIRpt;
  private ZafRptSis objRptSis;
  
  ZafVenCon objVenConLoc;
  ZafVenCon objVenConTipdoc;
  ZafVenCon objVenConUsr;


  final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
  final int INT_TBL_CODEMP=1;  // CODIGO DEL EMPRESA
  final int INT_TBL_CODLOC=2;  // CODIGO DEL LOCAL
  final int INT_TBL_COTIPDOC=3;  // CODIGO TIPO DOCUMENTO
  final int INT_TBL_DCTIPDOC=4;  // DESCRIPCION CORTA DEL TIPO DOCUMENTO
  final int INT_TBL_DLTIPDOC=5;  // DESCRIPCION LARGA DEL TIPO DOCUMENTO
  final int INT_TBL_CODDOC=6;  // CODIGO DOCUMENTO
  final int INT_TBL_CODREG=7;  // CODIGO REGISTRO
  final int INT_TBL_NUMDOC=8;  // NUMERO DE DOCUMENTO
  final int INT_TBL_FECDOC=9;  // FECHA DEL DOCUMENTO
  final int INT_TBL_CODCLI=10; // CODIGO DEL CLIENTE
  final int INT_TBL_NOMCLI=11;  // NOMBRE DEL CLIENTE
  final int INT_TBL_CODBAN=12;  // CODIGO DEL BANCO
  final int INT_TBL_DSCBAN=13;  // DESCRIPCION CORTA DEL BANCO
  final int INT_TBL_NOMBAN=14;  // NOMBRE DEL BANCO
  final int INT_TBL_NUMCTA=15;  // NUMERO DE CUENTA DEL BANCO
  final int INT_TBL_NUMCHQ=16;  // NUMERO DEL CHEQUE
  final int INT_TBL_VALCHQ=17;  // VALOR DEL CHEQUE
  final int INT_TBL_FECVEN=18;  // FECHA DE VENCIMIENTO
  final int INT_TBL_BUTRECDOC=19; // BUTON PARA VER RECEPCION


  String strVersion=" Ver 0.4 ";
  
  String strCodTipDoc="";
  String strDesCodTipDoc="";
  String strDesLarTipDoc="";
  String strCodLoc="";
  String strDesLoc="";
  String strCodUsr="";
  String strUsr="";

  Vector vecCab=new Vector(); 
  javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
  
  String strSqlLoc="";

    /** Creates new form ZafCxC59 */
    public ZafCxC59(Librerias.ZafParSis.ZafParSis obj) {
          try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();

             initComponents();

             this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
             lblTit.setText(objZafParSis.getNombreMenu());  /**/

	     objUti = new ZafUtil(); /**/

             objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);

            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(false);
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
             
         }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }



     /**
 * Carga ventanas de consulta y configuracion de la tabla
 */
public void Configura_ventana_consulta(){

    configurarVenConLoc();
    configurarVenConTipDoc();
    configurarVenConUsr();

    ConfigurarTabla();

}



    private boolean configurarVenConLoc() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_loc");
            arlCam.add("a.tx_nom");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Local.");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("460");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";

            Str_Sql="select a.co_loc, a.tx_nom  from tbm_loc as a where a.co_emp="+objZafParSis.getCodigoEmpresa()+" order by a.co_loc ";

            if(!(objZafParSis.getCodigoUsuario()==1) ){

               Str_Sql="select a.co_loc, a.tx_nom  from tbr_locusr as a1 " +
               " inner join tbm_loc as a on (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc ) " +
               " where a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.co_usr="+objZafParSis.getCodigoUsuario()+" order by a1.co_loc ";

               if(!objUti.utilizarClientesEmpresa(objZafParSis, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objZafParSis.getCodigoUsuario())){
                  Str_Sql="select a.co_loc, a.tx_nom  from tbr_locusr as a1 " +
                  " inner join tbm_loc as a on (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc ) " +
                  " where a1.co_emp="+objZafParSis.getCodigoEmpresa()+" and " +
                  " a1.co_loc="+objZafParSis.getCodigoLocal()+" and a1.co_usr="+objZafParSis.getCodigoUsuario()+" order by a1.co_loc ";

               }
            }
            
            strSqlLoc="SELECT co_loc FROM ( "+Str_Sql+" ) AS x ";

            objVenConLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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

            //Armar la sentencia SQL.   a7.nd_stkTot,
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

   private boolean configurarVenConUsr() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_usr");
            arlCam.add("a.tx_nom");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nom.Usr.");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("400");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            switch (objZafParSis.getCodigoUsuario()) {
                case 1:
                    Str_Sql="Select a.co_usr, a.tx_usr, a.tx_nom from tbm_usr as a where a.co_grpusr=5 and a.st_reg='A' ";
                    break;
                case 102:
                    Str_Sql="Select a.co_usr, a.tx_usr, a.tx_nom from tbm_usr as a where a.co_grpusr=5 and a.st_reg='A' ";    
                    break;
                default:
                    Str_Sql="Select a.co_usr, a.tx_usr, a.tx_nom from tbm_usr as a where a.co_grpusr=5 and a.st_reg='A' and co_usr = "+objZafParSis.getCodigoUsuario();
                    break;
            }

            objVenConUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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


private boolean ConfigurarTabla() {
 boolean blnRes=false;
 try{
     //Configurar JTable: Establecer el modelo.
        vecCab.clear();
        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_CODEMP,"Cód.Emp");
        vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
        vecCab.add(INT_TBL_COTIPDOC,"Cód.Tip.Doc.");
        vecCab.add(INT_TBL_DCTIPDOC,"Des.Cor.Doc");
        vecCab.add(INT_TBL_DLTIPDOC,"Des.Lar.Doc");
        vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
        vecCab.add(INT_TBL_CODREG,"Cód.Reg");
        vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
        vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
        vecCab.add(INT_TBL_CODCLI,"Cód.Cli");
        vecCab.add(INT_TBL_NOMCLI,"Cliente.");
        vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
        vecCab.add(INT_TBL_DSCBAN,"Banco.");
        vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
        vecCab.add(INT_TBL_NUMCTA,"Núm.Cta.");
        vecCab.add(INT_TBL_NUMCHQ,"Núm.Chq.");
        vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
        vecCab.add(INT_TBL_FECVEN,"Fec.Ven.");
        vecCab.add(INT_TBL_BUTRECDOC,"");


	objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);


        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

        //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
        objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());


        tblDat.getTableHeader().setReorderingAllowed(false);

	//Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/

        objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);

        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_BUTRECDOC);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;

        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;

         //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
         ZafMouMotAda objMouMotAda=new ZafMouMotAda();
         tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

         //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_BUTRECDOC).setPreferredWidth(20);


        /* Aqui se agrega las columnas que van
             ha hacer ocultas
         * */
        ArrayList arlColHid=new ArrayList();
        arlColHid.add(""+INT_TBL_CODEMP);
        arlColHid.add(""+INT_TBL_COTIPDOC);
        arlColHid.add(""+INT_TBL_CODDOC);
        arlColHid.add(""+INT_TBL_CODREG);
        arlColHid.add(""+INT_TBL_CODBAN);
        objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;



        objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
        tcmAux.getColumn(INT_TBL_BUTRECDOC).setCellRenderer(objTblCelRenBut);
        objTblCelRenBut=null;

        new ButRecDoc(tblDat, INT_TBL_BUTRECDOC);


        // new ZafTblOrd(tblDat);
        // new ZafTblBus(tblDat);

        int intCol[]={ INT_TBL_VALCHQ };
        objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

        new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);


     tcmAux=null;
     setEditable(true);

      blnRes=true;
   }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
    return blnRes;
}

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}


 private class ButRecDoc extends Librerias.ZafTableColBut.ZafTableColBut{
    public ButRecDoc(javax.swing.JTable tbl, int intIdx){
         super(tbl,intIdx);
    }
    public void butCLick() {

     int intNumFil = tblDat.getSelectedRow();
      if(intNumFil >= 0 ) {

        ventRecChq(intNumFil);

    }
 }}

 private void ventRecChq(int intNumFil){
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
        jLabel3 = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtDesLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodUsr = new javax.swing.JTextField();
        txtusr = new javax.swing.JTextField();
        butUsr = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11));
        optTod.setSelected(true);
        optTod.setText("Todos las documentos");
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
        optTod.setBounds(10, 90, 330, 20);

        buttonGroup1.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11));
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
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
        optFil.setBounds(10, 110, 330, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Tipo de documento:"); // NOI18N
        panFil.add(jLabel3);
        jLabel3.setBounds(40, 150, 100, 20);

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
        panFil.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(140, 150, 60, 20);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 150, 200, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(400, 150, 20, 20);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel4.setText("Local:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 130, 100, 20);

        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(140, 130, 60, 20);

        txtDesLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLocActionPerformed(evt);
            }
        });
        txtDesLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLocFocusLost(evt);
            }
        });
        panFil.add(txtDesLoc);
        txtDesLoc.setBounds(200, 130, 200, 20);

        butLoc.setText(".."); // NOI18N
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(400, 130, 20, 20);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setText("Usuario:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(40, 170, 80, 20);

        txtCodUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodUsrActionPerformed(evt);
            }
        });
        txtCodUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodUsrFocusLost(evt);
            }
        });
        panFil.add(txtCodUsr);
        txtCodUsr.setBounds(140, 170, 60, 20);

        txtusr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusrActionPerformed(evt);
            }
        });
        txtusr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtusrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtusrFocusLost(evt);
            }
        });
        panFil.add(txtusr);
        txtusr.setBounds(200, 170, 200, 20);

        butUsr.setText(".."); // NOI18N
        butUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrActionPerformed(evt);
            }
        });
        panFil.add(butUsr);
        butUsr.setBounds(400, 170, 20, 20);

        tabGen.addTab("Filtro", null, panFil, "Filtro");

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        tabGen.addTab("Reporte", jPanel1);

        getContentPane().add(tabGen, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butVisPre.setFont(new java.awt.Font("SansSerif", 0, 11));
        butVisPre.setText("Vista Preliminar");
        butVisPre.setPreferredSize(new java.awt.Dimension(90, 23));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        jPanel5.add(butVisPre);

        butImp.setFont(new java.awt.Font("SansSerif", 0, 11));
        butImp.setText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(90, 23));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        jPanel5.add(butImp);

        butCerr.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCerr.setText("Cancelar");
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
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {

        txtCodLoc.setText("");
        txtDesLoc.setText("");
        strCodLoc="";
        strDesLoc="";

        txtCodTipDoc.setText("");
        txtDesCodTitpDoc.setText("");
        txtDesLarTipDoc.setText("");
        strCodTipDoc="";
        strDesCodTipDoc="";
        strDesLarTipDoc="";

        }
}//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:


        txtCodLoc.setText("");
        txtDesLoc.setText("");
        strCodLoc="";
        strDesLoc="";

        txtCodTipDoc.setText("");
        txtDesCodTitpDoc.setText("");
        txtDesLarTipDoc.setText("");
        strCodTipDoc="";
        strDesCodTipDoc="";
        strDesLarTipDoc="";

        txtCodUsr.setText("");
        txtusr.setText("");

}//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_optFilActionPerformed

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
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        // TODO add your handling code here:
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
         if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)) {
            if (txtCodLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtDesLoc.setText("");
            }else
                BuscarLoc("a.co_loc",txtCodLoc.getText(),1);
        }else
            txtCodLoc.setText(strCodLoc);
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtDesLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLocActionPerformed
        // TODO add your handling code here:
        txtDesLoc.transferFocus();
    }//GEN-LAST:event_txtDesLocActionPerformed

    private void txtDesLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLocFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesLocFocusGained

    private void txtDesLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLocFocusLost
        // TODO add your handling code here:
        if (!txtDesLoc.getText().equalsIgnoreCase(strDesLoc)) {
            if (txtDesLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtDesLoc.setText("");
            }else
                BuscarLoc("a.tx_nom",txtDesLoc.getText(),2);
        }else
            txtDesLoc.setText(strDesLoc);

    }//GEN-LAST:event_txtDesLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        // TODO add your handling code here:
        objVenConLoc.setTitle("Listado de Local");
        objVenConLoc.setCampoBusqueda(1);
        objVenConLoc.show();
        if (objVenConLoc.getSelectedButton()==objVenConLoc.INT_BUT_ACE) {
            txtCodLoc.setText(objVenConLoc.getValueAt(1));
            txtDesLoc.setText(objVenConLoc.getValueAt(2));
            strCodLoc=objVenConLoc.getValueAt(1);
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butLocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:

         cargarRepote(1);
         
//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafCxC59/ZafCxC59.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafCxC59/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafCxC59/ZafCxC59.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafCxC59/ZafCxC59_01.jrxml";
//                }
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
//
//                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
//                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
//
//
//                 switch (objSelFec.getTipoSeleccion())
//                 {
//                    case 0: //Búsqueda por rangos
//                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 1: //Fechas menores o iguales que "Hasta".
//                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 2: //Fechas mayores o iguales que "Desde".
//                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 3: //Todo.
//                        break;
//                 }
//
//                  if(!(txtCodLoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
//                  }else{
//                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
//                  }
//
//                  if(!(txtCodTipDoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
//                  }
//
//
//                 if(!(txtCodUsr.getText().equals(""))){
//                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
//                 }
//
//
//                 //Obtener la fecha y hora del servidor.
//                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux!=null){
//                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                      datFecAux=null;
//                    }
//                    strNomUsr=objZafParSis.getNombreUsuario();
//
//                   Map parameters = new HashMap();
//
//                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
//                   parameters.put("strAux",  sqlAux );
//                   parameters.put("nomusr", strNomUsr );
//                   parameters.put("fecimp", strFecHorSer );
//                   parameters.put("SUBREPORT", subReport);
//
//                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
//                  JasperViewer.viewReport(report, false);
//
//                  conIns.close();
//                  conIns=null;
//
//        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
//          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }

        

    }//GEN-LAST:event_butVisPreActionPerformed

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

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        // TODO add your handling code here:

         cargarRepote(0);

//         java.sql.Connection conIns;
//         String DIRECCION_REPORTE="C:/zafiro/reportes_impresos/ZafCxC59/ZafCxC59.jrxml";
//         String DIRECCION_REPORTE_DETALLE="C:/zafiro/reportes_impresos/ZafCxC59/ZafCxC59_01.jrxml";
//         String sqlAux="";
//         String strNomUsr="";
//         String strFecHorSer="";
//         try{
//             conIns =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//            if(conIns!=null){
//
//                if(System.getProperty("os.name").equals("Linux")){
//                   DIRECCION_REPORTE="//zafiro/reportes_impresos/ZafCxC59/ZafCxC59.jrxml";
//                   DIRECCION_REPORTE_DETALLE="//zafiro/reportes_impresos/ZafCxC59/ZafCxC59_01.jrxml";
//                }
//
//                JasperDesign jasperDesign = JasperManager.loadXmlDesign(DIRECCION_REPORTE);
//                JasperDesign jasperDesign2 = JasperManager.loadXmlDesign(DIRECCION_REPORTE_DETALLE);
//
//                JasperReport jasperReport2 = JasperManager.compileReport(jasperDesign);
//                JasperReport subReport = JasperManager.compileReport(jasperDesign2);
//
//
//                 switch (objSelFec.getTipoSeleccion())
//                 {
//                    case 0: //Búsqueda por rangos
//                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 1: //Fechas menores o iguales que "Hasta".
//                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 2: //Fechas mayores o iguales que "Desde".
//                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
//                        break;
//                    case 3: //Todo.
//                        break;
//                 }
//
//                  if(!(txtCodLoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
//                  }else {
//                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
//                  }
//
//                  if(!(txtCodTipDoc.getText().equals(""))){
//                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
//                  }
//
//
//                 if(!(txtCodUsr.getText().equals(""))){
//                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
//                 }
//
//
//                   //Obtener la fecha y hora del servidor.
//                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
//                    if (datFecAux!=null){
//                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
//                      datFecAux=null;
//                    }
//                    strNomUsr=objZafParSis.getNombreUsuario();
//
//                   Map parameters = new HashMap();
//
//                   parameters.put("coemp", ""+objZafParSis.getCodigoEmpresa() );
//                   parameters.put("strAux",  sqlAux );
//                   parameters.put("nomusr", strNomUsr );
//                   parameters.put("fecimp", strFecHorSer );
//                   parameters.put("SUBREPORT", subReport);
//
//                  JasperPrint report = JasperFillManager.fillReport(jasperReport2, parameters, conIns);
//                  JasperManager.printReport(report,false);
//
//                  conIns.close();
//                  conIns=null;
//
//        }}catch (JRException e){  System.out.println("Excepción: " + e.toString()); }
//          catch(java.sql.SQLException ex) { System.out.println("Error al conectarse a la base"); }



    }//GEN-LAST:event_butImpActionPerformed

    private void txtCodUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodUsrActionPerformed
        // TODO add your handling code here:
         txtCodUsr.transferFocus();
    }//GEN-LAST:event_txtCodUsrActionPerformed

    private void txtCodUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUsrFocusGained
        // TODO add your handling code here:
        strCodUsr=txtCodUsr.getText();
        txtCodUsr.selectAll();
    }//GEN-LAST:event_txtCodUsrFocusGained

    private void txtCodUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodUsrFocusLost
        // TODO add your handling code here:
         if (!txtCodUsr.getText().equalsIgnoreCase(strCodUsr)) {
            if (txtCodUsr.getText().equals("")) {
                txtCodUsr.setText("");
                txtusr.setText("");

            }else
                BuscarUsr("a.co_usr",txtCodUsr.getText(),0);
        }else
            txtCodUsr.setText(strCodUsr);

    }//GEN-LAST:event_txtCodUsrFocusLost

    private void txtusrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusrActionPerformed
        // TODO add your handling code here:
          txtusr.transferFocus();
    }//GEN-LAST:event_txtusrActionPerformed

    private void txtusrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtusrFocusGained
        // TODO add your handling code here:
        strUsr=txtusr.getText();
        txtusr.selectAll();
    }//GEN-LAST:event_txtusrFocusGained

    private void txtusrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtusrFocusLost
        // TODO add your handling code here:
         if (!txtusr.getText().equalsIgnoreCase(strUsr)) {
            if (txtusr.getText().equals("")) {
                txtCodUsr.setText("");
                txtusr.setText("");

            }else
                BuscarUsr("a.tx_nom",txtusr.getText(),1);
        }else
            txtusr.setText(strUsr);

    }//GEN-LAST:event_txtusrFocusLost

    private void butUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrActionPerformed
        // TODO add your handling code here:
        objVenConUsr.setTitle("Listado de Usuarios");
        objVenConUsr.setCampoBusqueda(1);
        objVenConUsr.show();
        if (objVenConUsr.getSelectedButton()==objVenConUsr.INT_BUT_ACE) {
            txtCodUsr.setText(objVenConUsr.getValueAt(1));
            txtusr.setText(objVenConUsr.getValueAt(2));
            optFil.setSelected(true);
        }

    }//GEN-LAST:event_butUsrActionPerformed

private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);

    
    cargarDat();

    tabGen.setSelectedIndex(1);

   objThrGUI=null;
}
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
  double dblValChq=0;
  String strSql="", sqlAux="";
   try{
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();

          switch (objSelFec.getTipoSeleccion())
          {
                    case 0: //Búsqueda por rangos
                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
          }

          if(!(txtCodLoc.getText().equals(""))){
              sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
          }else{
              sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
          }

          if(!(txtCodTipDoc.getText().equals(""))){
              sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
          }


          if(!(txtCodUsr.getText().equals(""))){
              sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
          }


           strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a4.tx_descor, a4.tx_deslar, a.co_doc, a1.co_reg, a.ne_numdoc1, a.fe_doc " +
           ",a1.co_cli, a3.tx_nom, a1.co_banchq, a2.tx_descor as dcban, a2.tx_deslar as dlban, a1.tx_numctachq, a1.tx_numchq, a1.nd_monchq, a1.fe_venchq " +
           " FROM tbm_cabrecdoc as a " +
           " INNER JOIN tbm_detrecdoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc) " +
           " INNER JOIN tbm_cabtipdoc as a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc ) " +
           " INNER JOIN tbm_cli as a3 ON (a3.co_emp=a1.co_emp and a3.co_cli=a1.co_cli) " +
           " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a1.co_banchq ) " +
           " WHERE  a.co_emp="+objZafParSis.getCodigoEmpresa()+"  "+sqlAux+"   and a1.st_reg='A' and a.st_reg not in ('E','I') " +
           " ORDER BY a.fe_doc ";



           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_LINEA, "");
                vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
                vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
                vecReg.add(INT_TBL_COTIPDOC, rstLoc.getString("co_tipdoc") );
                vecReg.add(INT_TBL_DCTIPDOC, rstLoc.getString("tx_descor") );
                vecReg.add(INT_TBL_DLTIPDOC, rstLoc.getString("tx_deslar") );
                vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
                vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc1") );
                vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
                vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
                vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
                vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
                vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("dcban") );
                vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("dlban") );
                vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
                vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
                vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
                vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_venchq") );
                vecReg.add(INT_TBL_BUTRECDOC, ".." );

               vecData.add(vecReg);

               dblValChq+=rstLoc.getDouble("nd_monchq");

           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);


           dblValChq=objUti.redondear(dblValChq, 2);
           objTblTot.setValueAt( new Double(dblValChq) ,0, 17 );


        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
        pgrSis.setIndeterminate(false);

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
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


 public void BuscarLoc(String campo,String strBusqueda,int tipo){
  objVenConLoc.setTitle("Listado de Local");
  if(objVenConLoc.buscar(campo, strBusqueda )) {
      txtCodLoc.setText(objVenConLoc.getValueAt(1));
      txtDesLoc.setText(objVenConLoc.getValueAt(2));
      strCodLoc=objVenConLoc.getValueAt(1);
      optFil.setSelected(true);

  }else{
        objVenConLoc.setCampoBusqueda(tipo);
        objVenConLoc.cargarDatos();
        objVenConLoc.show();
        if (objVenConLoc.getSelectedButton()==objVenConLoc.INT_BUT_ACE) {
           txtCodLoc.setText(objVenConLoc.getValueAt(1));
           txtDesLoc.setText(objVenConLoc.getValueAt(2));
           strCodLoc=objVenConLoc.getValueAt(1);
           optFil.setSelected(true);
         }else{
           txtCodLoc.setText(strCodLoc);
           txtDesLoc.setText(strDesLoc);
  }}}


 public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      strCodTipDoc=objVenConTipdoc.getValueAt(1);
      optFil.setSelected(true);

  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
           optFil.setSelected(true);
         }else{
           txtCodTipDoc.setText(strCodTipDoc);
           txtDesCodTitpDoc.setText(strDesCodTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}


 public void BuscarUsr(String campo,String strBusqueda,int tipo){
  objVenConUsr.setTitle("Listado de Usuario");
  if(objVenConUsr.buscar(campo, strBusqueda )) {
      txtCodUsr.setText(objVenConUsr.getValueAt(1));
      txtusr.setText(objVenConUsr.getValueAt(2));
      strCodUsr=objVenConUsr.getValueAt(1);
      optFil.setSelected(true);

  }else{
        objVenConUsr.setCampoBusqueda(tipo);
        objVenConUsr.cargarDatos();
        objVenConUsr.show();
        if (objVenConUsr.getSelectedButton()==objVenConUsr.INT_BUT_ACE) {
           txtCodUsr.setText(objVenConUsr.getValueAt(1));
           txtusr.setText(objVenConUsr.getValueAt(2));
           strCodUsr=objVenConUsr.getValueAt(1);
           optFil.setSelected(true);
         }else{
           txtCodUsr.setText(strCodUsr);
           txtusr.setText(strUsr);

  }}}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerr;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butUsr;
    private javax.swing.JButton butVisPre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodUsr;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLoc;
    private javax.swing.JTextField txtusr;
    // End of variables declaration//GEN-END:variables




private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
            case INT_TBL_CODLOC:
            strMsg="Código de local.";
            break;


             case INT_TBL_DCTIPDOC:
            strMsg="Descripción corta del tipo de documento.";
            break;

             case INT_TBL_DLTIPDOC:
            strMsg="Descripción larga del tipo de documento.";
            break;


            case INT_TBL_CODREG:
            strMsg="Código de registro.";
            break;


             case INT_TBL_NUMDOC:
            strMsg="Número de documento.";
            break;

             case INT_TBL_FECDOC:
            strMsg="Fecha del documento.";
            break;
     
            case INT_TBL_CODCLI:
            strMsg="Código de cliente.";
            break;

            case INT_TBL_NOMCLI:
            strMsg="Nombre del cliente.";
            break;

            case INT_TBL_DSCBAN:
            strMsg="Banco.";
            break;

            case INT_TBL_NOMBAN:
            strMsg="Nombre del Banco.";
            break;

            case INT_TBL_NUMCTA:
            strMsg="Número de cuenta.";
            break;

            case INT_TBL_NUMCHQ:
            strMsg="Número de cheque.";
            break;

            case INT_TBL_VALCHQ:
            strMsg="valor del cheque.";
            break;

           
            case INT_TBL_FECVEN:
            strMsg="Fecha de vencimiento del cheque.";
            break;

            case INT_TBL_BUTRECDOC:
            strMsg="Presenta el documento. ";
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
        String strRutRpt, strNomRpt, strNomUsr="",  strFecHorSer="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String sqlAux="";
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


                                 switch (objSelFec.getTipoSeleccion())
                                 {
                                    case 0: //Búsqueda por rangos
                                        sqlAux+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                                        break;
                                    case 1: //Fechas menores o iguales que "Hasta".
                                        sqlAux+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                                        break;
                                    case 2: //Fechas mayores o iguales que "Desde".
                                        sqlAux+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                                        break;
                                    case 3: //Todo.
                                        break;
                                 }

                                  if(!(txtCodLoc.getText().equals(""))){
                                      sqlAux+=" AND a.co_loc="+txtCodLoc.getText()+" ";
                                  }else{
                                     sqlAux+=" AND a.co_loc in ( "+strSqlLoc+" ) ";
                                  }

                                  if(!(txtCodTipDoc.getText().equals(""))){
                                      sqlAux+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                                  }


                                 if(!(txtCodUsr.getText().equals(""))){
                                     sqlAux+=" AND a.co_usring="+txtCodUsr.getText()+" ";
                                 }


                                 //Obtener la fecha y hora del servidor.
                                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                    if (datFecAux!=null){
                                      strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                      datFecAux=null;
                                    }
                                    strNomUsr=objZafParSis.getNombreUsuario();



                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("coemp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("strAux",  sqlAux );
                                mapPar.put("nomusr", strNomUsr );
                                mapPar.put("fecimp", strFecHorSer );
                                mapPar.put("SUBREPORT_DIR", strRutRpt );

//                                mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
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






   

}

/*
 * ZafVen25.java
 *
 * Created on 06 de Agosto de 2008
 */

package Ventas.ZafVen11;
import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import Librerias.ZafVenCon.ZafVenCon; 
import java.util.ArrayList;

/**
 *
 * @author  jayapata
 */
public class ZafVen11_fac extends javax.swing.JDialog {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    String strAux=""; 
     
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblModPag; /**/
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
     
    String strFecSis;
    ZafUtil objUti; /**/
   
    final int INT_TBL_LINEA=0; 
    final int INT_TBL_CODALT=1;
    final int INT_TBL_BUTITM=2;
    final int INT_TBL_NOMITM=3;
    final int INT_TBL_UNIMED=4;
    final int INT_TBL_CANMOV=5;
    final int INT_TBL_PREUNI=6;
    final int INT_TBL_PORDES=7;
    final int INT_TBL_BLNIVA=8;
    final int INT_TBL_TOTAL =9;
    
    //************************ FORMA DE PAGO 
    final int INT_TBL_LINPAG=0; 
    final int INT_TBL_DIACRE=1;
    final int INT_TBL_FECVEN=2;
    final int INT_TBL_RETENC=3;
    final int INT_TBL_MONPAG=4;
    final int INT_TBL_DIAGRA=5;
    
    ZafVenCon objVenConTipdoc; //*****************
    ZafVenCon objVenConVen;
            
    String strCodTipDoc="", strDesCorTipDoc="",strDesLarTipDoc="";
    javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
    javax.swing.JTextField txtCodTipDocFac = new javax.swing.JTextField();
    javax.swing.JTextField txtNumDocSolOcu = new javax.swing.JTextField();
    
    java.sql.Connection CONN_GLO=null,conCab=null;
    java.sql.Statement  STM_GLO=null;
    java.sql.ResultSet  rstCab=null;
    
    boolean blnHayCam=false;
    Vector vecCab=new Vector();    //Almacena las cabeceras  /**/
                  
    int[] intCodCodUsr;
    int intSecCodUsr=0;
    String strCodSol="";
    String strDesCodTitpDoc="";
    String strDesLarTipDocSol="";
    String strDesSol="";
    int intEstDev1=0,intEstDev2=1,intEstDev3=1;
    double dblPorIva=0; 
    String stIvaVen="S";
    int intEstCon=0;
    
    int intCodEmp=0;
    int intCodLoc=0;
    int intCodTipDoc=0;
    int intCodDoc=0;
    
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    
    String strEstAut="";
     
    /** Creates new form ZafCom33 */
    public ZafVen11_fac(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCod_Emp, String strCocLoc, String strDocTipDoc, String strCocDoc ) {
         super(parent, modal);
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
	    
            this.setTitle(objZafParSis.getNombreMenu()+"  v0.1 "); /**/
            //lblTit.setText(objZafParSis.getNombreMenu());  /**/
            
	     objUti = new ZafUtil(); /**/
             
            intCodEmp=intCod_Emp;
            intCodLoc=Integer.parseInt(strCocLoc);
            intCodTipDoc=Integer.parseInt(strDocTipDoc);
            intCodDoc=Integer.parseInt(strCocDoc);
             
	      
             txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y"); 
             txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
             txtFecDoc.setText("");
             pan2.add(txtFecDoc);
             txtFecDoc.setBounds(550, 8, 92, 20);
             
            
	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
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
     
      
  
  
   private boolean ConfigurartablaPag() {
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINPAG,"");
	    vecCab.add(INT_TBL_DIACRE,"Dias de Credito.");
	    vecCab.add(INT_TBL_FECVEN,"Fecha de vencimiento.");
            vecCab.add(INT_TBL_RETENC,"Retención.");
	    vecCab.add(INT_TBL_MONPAG,"Monto de Pago.");
            vecCab.add(INT_TBL_DIAGRA,"Dias de Gracias.");
	   
	    objTblModPag=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModPag.setHeader(vecCab);
            tblDatPag.setModel(objTblModPag);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDatPag.setRowSelectionAllowed(true);
            tblDatPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDatPag,INT_TBL_LINEA);
    	    
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModPag.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            
	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux=tblDatPag.getColumnModel();  /**/
	    
            objTblModPag.setColumnDataType(INT_TBL_RETENC, objTblModPag.INT_COL_DBL, new Integer(0), null);
            objTblModPag.setColumnDataType(INT_TBL_MONPAG, objTblModPag.INT_COL_DBL, new Integer(0), null);
            
            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_RETENC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_MONPAG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
           
             //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINPAG).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_RETENC).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_MONPAG).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DIAGRA).setPreferredWidth(110);
            
            tblDatPag.getTableHeader().setReorderingAllowed(false);
            tcmAux=null;
	     
             blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
             
            
            
   private boolean Configurartabla() {
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
            
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
	    vecCab.add(INT_TBL_CODALT,"Cod.Alt.");
	    vecCab.add(INT_TBL_BUTITM,"");
            vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
	    vecCab.add(INT_TBL_UNIMED,"Uni.Med");
            vecCab.add(INT_TBL_CANMOV,"Can.Mov.");
	    vecCab.add(INT_TBL_PREUNI,"Pre.Uni.");
	    vecCab.add(INT_TBL_PORDES,"Por.Des.");
	    vecCab.add(INT_TBL_BLNIVA,"Iva.");
	    vecCab.add(INT_TBL_TOTAL,"Total.");
	         
      
	    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
    	    
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            
	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/
	    
            objTblMod.setColumnDataType(INT_TBL_CANMOV, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PREUNI, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_PORDES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_TOTAL, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
             
	 
             //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUTITM).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_UNIMED).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PREUNI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PORDES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BLNIVA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_TOTAL).setPreferredWidth(55);
            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PREUNI).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_PORDES).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }   
            });

            
            
            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BLNIVA).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_BLNIVA).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
               });
            objTblCelRenChk=null;
            objTblCelEdiChk=null; 
            
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            new ButBod(tblDat, INT_TBL_BUTITM);   //*****
            
            
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
          
	    tcmAux=null;
	     
	   // setEditable(true);
           
             
             blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
    
      
    
    
           
private class ButBod extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButBod(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaFac(intCol);
    }
}
private void llamarVentanaFac(int intCol){
 
}
    
   
   
   
   
      
    public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
       
    
    
    public void Configura_ventana_consulta(){
        configurarVenConTipDoc();
        configurarVenConVendedor();
                
     
        Configurartabla(); 
        ConfigurartablaPag();
        
        noEditable(false);
      cargarFac();
    }
    
    
   
    private void noEditable(boolean blnEst){
        java.awt.Color colBack = txtTipDocAli.getBackground();
         bloquea(txtTipDocAli, colBack, blnEst);
         bloquea(txtTipDocNom, colBack, blnEst);
         bloquea(txtCot, colBack, blnEst);
         bloquea(txtVenCod, colBack, blnEst);
         bloquea(txtVenNom, colBack, blnEst);
         bloquea(txtDoc, colBack, blnEst);
         bloquea(txtFac, colBack, blnEst);
         bloquea(txtAte, colBack, blnEst);
         bloquea(txtPed, colBack, blnEst);
         bloquea(txtCliCod, colBack, blnEst);
         bloquea(txtCliNom, colBack, blnEst);
         bloquea(txtCliDir, colBack, blnEst);
         bloquea(txtGuia, colBack, blnEst);
         bloquea(txtSub, colBack, blnEst);
         bloquea(txtIva, colBack, blnEst);
         bloquea(txtTot, colBack, blnEst);
         bloquea(txtForPag, colBack, blnEst);
        txaObs1.setEditable(blnEst);
        txaObs2.setEditable(blnEst);
      }

     private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
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
            Str_Sql="Select a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
	    " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
            " where   b.co_emp="+objZafParSis.getCodigoEmpresa()+" and " +
            " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
            " b.co_mnu = "+objZafParSis.getCodigoMenu();
            
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
    
    
    
    
private boolean configurarVenConVendedor() {
    boolean blnRes=true;
    try {
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

        objVenConVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
 }
    
        
    
    
    

    
    
    
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpRdaBut = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panDatTabGen = new javax.swing.JPanel();
        panTabGenNor = new javax.swing.JPanel();
        panCotGenNor = new javax.swing.JPanel();
        pan2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTipDocAli = new javax.swing.JTextField();
        txtTipDocNom = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCot = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblCom = new javax.swing.JLabel();
        txtVenCod = new javax.swing.JTextField();
        txtVenNom = new javax.swing.JTextField();
        panCotGenNorNor = new javax.swing.JPanel();
        txtDoc = new javax.swing.JTextField();
        lblDoc = new javax.swing.JLabel();
        txtFac = new javax.swing.JTextField();
        lblFac = new javax.swing.JLabel();
        lblAte = new javax.swing.JLabel();
        txtPed = new javax.swing.JTextField();
        txtAte = new javax.swing.JTextField();
        lblPed = new javax.swing.JLabel();
        panCotGenNorSur = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        txtCliDir = new javax.swing.JTextField();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        lblGuia = new javax.swing.JLabel();
        txtGuia = new javax.swing.JTextField();
        panTabGenCen = new javax.swing.JPanel();
        scrollTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCotGenSur = new javax.swing.JPanel();
        panCotGenSurCen = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblObs3 = new javax.swing.JLabel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        lblObs4 = new javax.swing.JLabel();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panCotGenSurEst = new javax.swing.JPanel();
        lblSubTot = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        lblIva = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblTot = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        panForPag = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblDatPag = new javax.swing.JTable();
        panCotForPagNo = new javax.swing.JPanel();
        panCotForPagNorCen = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        txtForPag = new javax.swing.JTextField();
        panBut = new javax.swing.JPanel();
        panButEst = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Factura Comercial");
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panDatTabGen.setLayout(new java.awt.BorderLayout());

        panTabGenNor.setPreferredSize(new java.awt.Dimension(100, 135));
        panTabGenNor.setLayout(new java.awt.BorderLayout());

        panCotGenNor.setPreferredSize(new java.awt.Dimension(800, 135));
        panCotGenNor.setLayout(new java.awt.BorderLayout());

        pan2.setPreferredSize(new java.awt.Dimension(600, 50));
        pan2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Tipo Documento:"); // NOI18N
        pan2.add(jLabel2);
        jLabel2.setBounds(4, 12, 110, 15);

        txtTipDocAli.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipDocAli.setMinimumSize(new java.awt.Dimension(0, 0));
        txtTipDocAli.setPreferredSize(new java.awt.Dimension(25, 20));
        txtTipDocAli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocAliActionPerformed(evt);
            }
        });
        pan2.add(txtTipDocAli);
        txtTipDocAli.setBounds(100, 10, 64, 20);

        txtTipDocNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipDocNom.setPreferredSize(new java.awt.Dimension(100, 20));
        pan2.add(txtTipDocNom);
        txtTipDocNom.setBounds(164, 10, 240, 20);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel1.setText("No. Cotización:"); // NOI18N
        pan2.add(jLabel1);
        jLabel1.setBounds(6, 30, 110, 15);

        txtCot.setMaximumSize(null);
        txtCot.setPreferredSize(new java.awt.Dimension(70, 20));
        pan2.add(txtCot);
        txtCot.setBounds(100, 30, 92, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha documento:"); // NOI18N
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        pan2.add(lblFecDoc);
        lblFecDoc.setBounds(450, 12, 108, 15);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCom.setText("Vendedor:"); // NOI18N
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        pan2.add(lblCom);
        lblCom.setBounds(450, 30, 64, 15);

        txtVenCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtVenCod.setPreferredSize(new java.awt.Dimension(25, 20));
        pan2.add(txtVenCod);
        txtVenCod.setBounds(515, 30, 35, 20);

        txtVenNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtVenNom.setPreferredSize(new java.awt.Dimension(100, 20));
        pan2.add(txtVenNom);
        txtVenNom.setBounds(550, 30, 110, 20);

        panCotGenNor.add(pan2, java.awt.BorderLayout.NORTH);

        panCotGenNorNor.setPreferredSize(new java.awt.Dimension(600, 55));
        panCotGenNorNor.setLayout(null);

        txtDoc.setBackground(objZafParSis.getColorCamposSistema()
        );
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtDoc);
        txtDoc.setBounds(100, 0, 92, 20);

        lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDoc.setText("No. Documento:"); // NOI18N
        panCotGenNorNor.add(lblDoc);
        lblDoc.setBounds(6, 0, 110, 15);

        txtFac.setBackground(objZafParSis.getColorCamposObligatorios()
        );
        txtFac.setMaximumSize(null);
        txtFac.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtFac);
        txtFac.setBounds(100, 20, 92, 20);

        lblFac.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFac.setText("No. Factura:"); // NOI18N
        panCotGenNorNor.add(lblFac);
        lblFac.setBounds(6, 20, 110, 15);

        lblAte.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblAte.setText("Atención:"); // NOI18N
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorNor.add(lblAte);
        lblAte.setBounds(450, 0, 60, 15);
        panCotGenNorNor.add(txtPed);
        txtPed.setBounds(552, 20, 110, 20);

        txtAte.setMaximumSize(null);
        txtAte.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtAte);
        txtAte.setBounds(534, 0, 146, 20);

        lblPed.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPed.setText("No. Pedido:"); // NOI18N
        panCotGenNorNor.add(lblPed);
        lblPed.setBounds(450, 20, 72, 15);

        panCotGenNor.add(panCotGenNorNor, java.awt.BorderLayout.CENTER);

        panCotGenNorSur.setPreferredSize(new java.awt.Dimension(600, 45));
        panCotGenNorSur.setLayout(null);

        lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPrv.setText("Cliente::"); // NOI18N
        panCotGenNorSur.add(lblPrv);
        lblPrv.setBounds(6, 0, 72, 15);

        lblDir.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDir.setText("Dirección:"); // NOI18N
        panCotGenNorSur.add(lblDir);
        lblDir.setBounds(6, 20, 60, 15);

        txtCliDir.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliDir.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCliDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliDirActionPerformed(evt);
            }
        });
        panCotGenNorSur.add(txtCliDir);
        txtCliDir.setBounds(100, 20, 320, 20);

        txtCliCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliCod.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCliCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNorSur.add(txtCliCod);
        txtCliCod.setBounds(100, 0, 35, 20);

        txtCliNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliNom.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtCliNom.setPreferredSize(new java.awt.Dimension(100, 20));
        panCotGenNorSur.add(txtCliNom);
        txtCliNom.setBounds(134, 0, 286, 20);

        lblGuia.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblGuia.setText("No. Guia Remisión:"); // NOI18N
        lblGuia.setPreferredSize(new java.awt.Dimension(100, 15));
        panCotGenNorSur.add(lblGuia);
        lblGuia.setBounds(450, 0, 100, 15);
        panCotGenNorSur.add(txtGuia);
        txtGuia.setBounds(552, 0, 80, 20);

        panCotGenNor.add(panCotGenNorSur, java.awt.BorderLayout.SOUTH);

        panTabGenNor.add(panCotGenNor, java.awt.BorderLayout.NORTH);

        panDatTabGen.add(panTabGenNor, java.awt.BorderLayout.NORTH);

        panTabGenCen.setLayout(new java.awt.BorderLayout());

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
        scrollTbl.setViewportView(tblDat);

        panTabGenCen.add(scrollTbl, java.awt.BorderLayout.CENTER);

        panDatTabGen.add(panTabGenCen, java.awt.BorderLayout.CENTER);

        panCotGenSur.setLayout(new java.awt.BorderLayout());

        panCotGenSurCen.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblObs3.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs3.setText("Observación 1:"); // NOI18N
        jPanel4.add(lblObs3, java.awt.BorderLayout.WEST);

        spnObs1.setViewportView(txaObs1);

        jPanel4.add(spnObs1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lblObs4.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblObs4.setText("Observación 2:"); // NOI18N
        jPanel3.add(lblObs4, java.awt.BorderLayout.WEST);

        spnObs2.setViewportView(txaObs2);

        jPanel3.add(spnObs2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        panCotGenSurCen.add(jPanel5, java.awt.BorderLayout.CENTER);

        panCotGenSur.add(panCotGenSurCen, java.awt.BorderLayout.CENTER);

        panCotGenSurEst.setLayout(new java.awt.GridLayout(3, 2));

        lblSubTot.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSubTot.setText("SubTotal:"); // NOI18N
        lblSubTot.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenSurEst.add(lblSubTot);

        txtSub.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtSub);

        lblIva.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblIva.setText("IVA 12%:"); // NOI18N
        lblIva.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblIva);

        txtIva.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtIva);

        lblTot.setFont(new java.awt.Font("SansSerif", 1, 12));
        lblTot.setText("Total:"); // NOI18N
        lblTot.setPreferredSize(new java.awt.Dimension(60, 14));
        panCotGenSurEst.add(lblTot);

        txtTot.setFont(new java.awt.Font("Arial", 1, 12));
        txtTot.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        panCotGenSurEst.add(txtTot);

        panCotGenSur.add(panCotGenSurEst, java.awt.BorderLayout.EAST);

        panDatTabGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panDatTabGen);

        panForPag.setLayout(new java.awt.BorderLayout());

        tblDatPag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                " Línea", "Dias de crédito", "Fecha de Vencimiento", "Retención", "Monto de Pago", "Días de gracia", "CodRet", "tx_sop"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDatPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDatPag.setColumnSelectionAllowed(true);
        spnForPag.setViewportView(tblDatPag);

        panForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        panCotForPagNo.setEnabled(false);
        panCotForPagNo.setPreferredSize(new java.awt.Dimension(249, 60));
        panCotForPagNo.setLayout(new java.awt.BorderLayout());

        panCotForPagNorCen.setPreferredSize(new java.awt.Dimension(249, 40));

        lblForPag.setText("Forma de Pago:"); // NOI18N
        panCotForPagNorCen.add(lblForPag);

        txtForPag.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtForPag.setPreferredSize(new java.awt.Dimension(300, 20));
        txtForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForPagActionPerformed(evt);
            }
        });
        panCotForPagNorCen.add(txtForPag);

        panCotForPagNo.add(panCotForPagNorCen, java.awt.BorderLayout.CENTER);

        panForPag.add(panCotForPagNo, java.awt.BorderLayout.NORTH);

        tabGen.addTab("Forma de Pago", panForPag);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBut.setPreferredSize(new java.awt.Dimension(100, 35));
        panBut.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(90, 23));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panButEst.add(butCan);

        panBut.add(panButEst, java.awt.BorderLayout.LINE_END);

        getContentPane().add(panBut, java.awt.BorderLayout.PAGE_END);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
	// TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
	// TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
           // cerrarObj();
            System.gc();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	// TODO add your handling code here:
	
    }//GEN-LAST:event_formInternalFrameOpened

    
//    private void cargarVenBusFac(){
//        Ventas.ZafVen11.ZafVen11_01 obj = new Ventas.ZafVen11.ZafVen11_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis );
//       obj.show();
//        if(obj.acepta()){ 
//             txtNumDocFac.setText(obj.GetCamSel(4));
//             if(!cargarFac(txtNumDocFac.getText()))
//                 cargarVenBusFac();
//        }
//       obj.dispose();
//       obj=null;
//    }
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        
        cerrarVen();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_formWindowClosing

    private void txtTipDocAliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocAliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipDocAliActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
       Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void txtForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForPagActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtForPagActionPerformed
    
     
           
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        dispose();
    }
 }


     
     
     
    
        
    
 
  

 
 


/**
  * Esta funcion se encarga de buscar la factura y cargar los datos 
  */    
private boolean cargarFac(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
                    
      strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_cli, a.tx_nomcli, a.co_doc, a.fe_Doc, a1.tx_nom as nomemp, a2.tx_descor, a2.tx_deslar, a.tx_obs1, a.tx_obs2, a.nd_poriva " +
      " ,a.ne_numcot, a.ne_numdoc, a.ne_numgui, a.tx_dircli, a.tx_numped, a.tx_ate, a.co_com , a3.tx_nom, abs(a.nd_tot) as nd_tot ,abs(a.nd_sub) as nd_sub, abs(a.nd_valiva) as nd_valiva, a.co_forpag, a4.tx_des " +
      " FROM tbm_cabmovinv AS a  " +
      " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc)  " +
      " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) " +
      " LEFT JOIN tbm_usr AS a3 ON(a3.co_usr=a.co_com )   "+
      " LEFT JOIN tbm_cabforpag as a4 ON(a4.co_emp=a.co_emp AND a4.co_forpag=a.co_forpag) "+        
      " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" " +
      " AND  a.co_tipdoc ="+intCodTipDoc+" AND  a.co_doc="+intCodDoc;
      System.out.println("2 " + strSql );
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
       if(cargarFacCab(conn, rstLoc)){  
        if(cargarFacDet(conn, rstLoc)){
         if(cargarFacPag(conn, rstLoc)){   
            blnRes=true;
      }}}}
      rstLoc.close(); 
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    conn.close();
    conn=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
     






private boolean cargarFacCab(java.sql.Connection conn, java.sql.ResultSet rstDat){
 boolean blnRes=false;
 try{
   if(conn!=null){
       txtCodTipDocFac.setText(rstDat.getString("co_tipdoc"));
       txtTipDocAli.setText(rstDat.getString("tx_descor"));
       txtTipDocNom.setText(rstDat.getString("tx_deslar"));
       txtFac.setText(rstDat.getString("ne_numdoc"));
       txtGuia.setText(rstDat.getString("ne_numgui"));
       txtCliDir.setText(rstDat.getString("tx_dircli"));
       txtCliCod.setText(rstDat.getString("co_cli"));
       txtCliNom.setText(rstDat.getString("tx_nomcli"));
       txtDoc.setText(rstDat.getString("co_doc"));
       txtCot.setText(rstDat.getString("ne_numcot"));
       txtVenCod.setText(rstDat.getString("co_com"));
       txtVenNom.setText(rstDat.getString("tx_nom"));
       txtAte.setText(rstDat.getString("tx_ate"));
       txtPed.setText(rstDat.getString("tx_numped"));
       
       txaObs1.setText(rstDat.getString("tx_obs1"));
       txaObs2.setText(rstDat.getString("tx_obs2"));
       dblPorIva=rstDat.getDouble("nd_poriva");
       
       txtSub.setText(""+ objUti.redondear(rstDat.getString("nd_sub"),2) );
       txtIva.setText(""+ objUti.redondear(rstDat.getString("nd_valiva"),2) );
       txtTot.setText(""+ objUti.redondear(rstDat.getString("nd_tot"),2) );
       
       txtForPag.setText(rstDat.getString("tx_des")); 
       
        if(rstDat.getDate("fe_doc")==null){
              txtFecDoc.setText("");
        }else{
            java.util.Date dateObj = rstDat.getDate("fe_doc");
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            calObj.setTime(dateObj);
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
            calObj.get(java.util.Calendar.MONTH)+1     ,
            calObj.get(java.util.Calendar.YEAR)        );
        }
                    
       
     blnRes=true;
  }}catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   



private boolean cargarFacDet(java.sql.Connection conn, java.sql.ResultSet rstDat){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
   if(conn!=null){
       stmLoc=conn.createStatement();
       
       vecData = new Vector();
       strSql="SELECT  a.tx_codalt, a.tx_nomitm, a.tx_unimed,  abs(a.nd_can) as nd_can, a.nd_preuni, a.nd_pordes, a.st_ivacom , abs(a.nd_tot) as nd_tot " +
       " FROM tbm_detmovinv AS a " +
       " WHERE a.co_emp="+rstDat.getString("co_emp")+" AND  a.co_loc="+rstDat.getString("co_loc")+"  AND a.co_tipdoc="+rstDat.getString("co_tipdoc")+" AND a.co_doc="+rstDat.getString("co_doc")+" order by a.co_reg ";
       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
          vecReg.add(INT_TBL_BUTITM,"");
          vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
          vecReg.add(INT_TBL_UNIMED, rstLoc.getString("tx_unimed") );
          vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can") );
          vecReg.add(INT_TBL_PREUNI, rstLoc.getString("nd_preuni") );
          vecReg.add(INT_TBL_PORDES, rstLoc.getString("nd_pordes") );
          vecReg.add(INT_TBL_BLNIVA, ( (rstLoc.getString("st_ivacom").equals("S"))?new Boolean(true):new Boolean(false)) );
          vecReg.add(INT_TBL_TOTAL, rstLoc.getString("nd_tot") ); 
          vecData.add(vecReg);
       }
         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);
       rstLoc.close(); 
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
     


    
  
    


private boolean cargarFacPag(java.sql.Connection conn, java.sql.ResultSet rstDat){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
   if(conn!=null){
       stmLoc=conn.createStatement();
       
       vecData = new Vector();
       strSql="SELECT  a.ne_diacre, a.fe_ven, a.nd_porret, abs(a.mo_pag) as mo_pag, a.ne_diagra  FROM tbm_pagmovinv AS a " +
       " WHERE a.co_emp="+rstDat.getString("co_emp")+" AND  a.co_loc="+rstDat.getString("co_loc")+"  AND a.co_tipdoc="+rstDat.getString("co_tipdoc")+" AND a.co_doc="+rstDat.getString("co_doc")+" AND a.st_reg IN ('A','F') order by a.co_reg ";
       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINPAG,"");
          vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre") );
          vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven") );
          vecReg.add(INT_TBL_RETENC, rstLoc.getString("nd_porret") );
          vecReg.add(INT_TBL_MONPAG, rstLoc.getString("mo_pag") );
          vecReg.add(INT_TBL_DIAGRA, rstLoc.getString("ne_diagra") );
          vecData.add(vecReg);
       }
         objTblModPag.setData(vecData);
         tblDatPag .setModel(objTblModPag);
       rstLoc.close(); 
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
     blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
     

    
 
     
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup grpRdaBut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblFac;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblGuia;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblObs3;
    private javax.swing.JLabel lblObs4;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTot;
    private javax.swing.JPanel pan2;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panButEst;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panCotForPagNo;
    private javax.swing.JPanel panCotForPagNorCen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenNorNor;
    private javax.swing.JPanel panCotGenNorSur;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panCotGenSurEst;
    private javax.swing.JPanel panDatTabGen;
    private javax.swing.JPanel panForPag;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panTabGenCen;
    private javax.swing.JPanel panTabGenNor;
    private javax.swing.JScrollPane scrollTbl;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatPag;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAte;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliDir;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtCot;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtFac;
    private javax.swing.JTextField txtForPag;
    private javax.swing.JTextField txtGuia;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtPed;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTipDocAli;
    private javax.swing.JTextField txtTipDocNom;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtVenCod;
    private javax.swing.JTextField txtVenNom;
    // End of variables declaration//GEN-END:variables
    
      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
      
 
    
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_CODALT:
            strMsg="Cóidigo Alterno del item.";
            break;
        case INT_TBL_NOMITM:
            strMsg="Nombre del item.";
            break;
        case INT_TBL_UNIMED:
            strMsg="Descripción de la unidad de medida.";
            break;
        case INT_TBL_CANMOV:
            strMsg="Cantidad del item Facturado.";
            break;
        case INT_TBL_PREUNI:
            strMsg="Precio Unitario del Item.";
            break;
        case INT_TBL_PORDES:
            strMsg="Porcentaje de Descuento.";
            break; 
        case INT_TBL_BLNIVA:
            strMsg="Marca Iva el Item.";
            break;
        case INT_TBL_TOTAL:
            strMsg="Total.";
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
    
    
    
}

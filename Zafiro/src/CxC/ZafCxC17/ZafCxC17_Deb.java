/*
 * ZafVen25.java
 *
 * Created on 06 de Agosto de 2008
 */



package CxC.ZafCxC17;
import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import Librerias.ZafVenCon.ZafVenCon; 
import java.util.ArrayList;

/**
 *
 * @author  jayapata
 */
public class ZafCxC17_Deb extends javax.swing.JDialog {
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
    ZafVenCon objVenConTipdoc;
    ZafVenCon objVenConVen;
    ZafVenCon objVenConCta;  
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
    Integer strDocTipDoc;
    int intCodEmp=0;
    int intCodLoc=0;
    int intCodTipDoc=0;
    int intCodDoc=0;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    String strEstAut="";
    /** Creates new form ZafCom33 */
    public ZafCxC17_Deb(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, int intCod_Emp, String strCocLoc, String strDocTipDoc, String strCocDoc ) {
         super(parent, modal);
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
            this.setTitle(objZafParSis.getNombreMenu()+"  v0.1 "); /**/
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

   private boolean Configurartabla() {
       boolean blnRes=false;
       try{
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
        super(tbl,intIdx, "Factura Comercial");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
         llamarVentanaDeb(intCol);
    }
}

private void llamarVentanaDeb(int intCol){    
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
        configurarVenConCta();
        Configurartabla(); 
        noEditable(false);
        cargarFac();
    }
    private void noEditable(boolean blnEst){
        java.awt.Color colBack = txtTipDocAli.getBackground();
         bloquea(txtTipDocAli, colBack, blnEst);
         bloquea(txtTipDocNom, colBack, blnEst);
         bloquea(txtCodCta, colBack, blnEst);
         bloquea(txtNomCta, colBack, blnEst);
         bloquea(txtDoc, colBack, blnEst);
         bloquea(txtPed, colBack, blnEst);
         bloquea(txtCliCod, colBack, blnEst);
         bloquea(txtCliNom, colBack, blnEst);
         bloquea(txtGuia, colBack, blnEst);
         bloquea(txtSub, colBack, blnEst);
        txaObs1.setEditable(blnEst);
        txaObs2.setEditable(blnEst);
      }
     private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }
       private boolean configurarVenConCta() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.tx_codcta");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Lar.Cta");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            String Str_Sql="";
            Str_Sql=" SELECT  a8.tx_codcta, a8.tx_deslar ";
	    Str_Sql=" FROM tbm_cabmovinv AS a INNER JOIN tbm_placta AS a8 on (a.co_emp=a8.co_emp and a.co_cta=a8.co_cta) " ;
            Str_Sql=" where  a.co_emp= "+objZafParSis.getCodigoEmpresa();
            Str_Sql=" a.co_loc = " + objZafParSis.getCodigoLocal();
            Str_Sql=" a.co_tipdoc=" +intCodTipDoc ;
            Str_Sql=" a.co_doc=" +intCodDoc ;
            objVenConCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            objVenConCta.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
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
        lblFecDoc = new javax.swing.JLabel();
        lblCta = new javax.swing.JLabel();
        txtCodCta = new javax.swing.JTextField();
        txtNomCta = new javax.swing.JTextField();
        lblGuia = new javax.swing.JLabel();
        txtGuia = new javax.swing.JTextField();
        panCotGenNorNor = new javax.swing.JPanel();
        txtDoc = new javax.swing.JTextField();
        lblDoc = new javax.swing.JLabel();
        txtPed = new javax.swing.JTextField();
        lblPed = new javax.swing.JLabel();
        lblPrv = new javax.swing.JLabel();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        lblSubTot1 = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
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

        lblTit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Nota de Débito");
        lblTit.setPreferredSize(new java.awt.Dimension(72, 34));
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
        txtTipDocAli.setBounds(100, 10, 80, 20);

        txtTipDocNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtTipDocNom.setPreferredSize(new java.awt.Dimension(70, 20));
        txtTipDocNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocNomActionPerformed(evt);
            }
        });
        pan2.add(txtTipDocNom);
        txtTipDocNom.setBounds(180, 10, 240, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha documento:"); // NOI18N
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        pan2.add(lblFecDoc);
        lblFecDoc.setBounds(450, 12, 108, 15);

        lblCta.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCta.setText("Cuenta:");
        lblCta.setPreferredSize(new java.awt.Dimension(110, 15));
        pan2.add(lblCta);
        lblCta.setBounds(10, 30, 70, 15);

        txtCodCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaActionPerformed(evt);
            }
        });
        txtCodCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCtaFocusLost(evt);
            }
        });
        pan2.add(txtCodCta);
        txtCodCta.setBounds(100, 30, 80, 20);

        txtNomCta.setMaximumSize(null);
        txtNomCta.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCtaActionPerformed(evt);
            }
        });
        txtNomCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCtaFocusLost(evt);
            }
        });
        pan2.add(txtNomCta);
        txtNomCta.setBounds(180, 30, 240, 20);

        lblGuia.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblGuia.setText("Nº. Alterno 1:"); // NOI18N
        lblGuia.setPreferredSize(new java.awt.Dimension(100, 15));
        pan2.add(lblGuia);
        lblGuia.setBounds(450, 30, 100, 15);
        pan2.add(txtGuia);
        txtGuia.setBounds(570, 30, 80, 20);

        panCotGenNor.add(pan2, java.awt.BorderLayout.NORTH);

        panCotGenNorNor.setPreferredSize(new java.awt.Dimension(600, 55));
        panCotGenNorNor.setLayout(null);

        txtDoc.setBackground(objZafParSis.getColorCamposSistema()
        );
        txtDoc.setMaximumSize(null);
        txtDoc.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNorNor.add(txtDoc);
        txtDoc.setBounds(100, 20, 80, 20);

        lblDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDoc.setText("No. Documento:"); // NOI18N
        panCotGenNorNor.add(lblDoc);
        lblDoc.setBounds(10, 20, 110, 15);
        panCotGenNorNor.add(txtPed);
        txtPed.setBounds(570, 0, 80, 20);

        lblPed.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPed.setText("Nº Allterno 2:"); // NOI18N
        panCotGenNorNor.add(lblPed);
        lblPed.setBounds(450, 0, 80, 15);

        lblPrv.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblPrv.setText("Cliente:"); // NOI18N
        panCotGenNorNor.add(lblPrv);
        lblPrv.setBounds(10, 0, 72, 15);

        txtCliCod.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliCod.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCliCod.setMinimumSize(new java.awt.Dimension(0, 0));
        txtCliCod.setPreferredSize(new java.awt.Dimension(25, 20));
        panCotGenNorNor.add(txtCliCod);
        txtCliCod.setBounds(100, 0, 80, 20);

        txtCliNom.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCliNom.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCliNom.setPreferredSize(new java.awt.Dimension(70, 20));
        panCotGenNorNor.add(txtCliNom);
        txtCliNom.setBounds(180, 0, 240, 20);

        lblSubTot1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblSubTot1.setText("Monto del Documento"); // NOI18N
        lblSubTot1.setPreferredSize(new java.awt.Dimension(90, 14));
        panCotGenNorNor.add(lblSubTot1);
        lblSubTot1.setBounds(450, 20, 110, 14);

        txtSub.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubActionPerformed(evt);
            }
        });
        panCotGenNorNor.add(txtSub);
        txtSub.setBounds(570, 20, 80, 20);

        panCotGenNor.add(panCotGenNorNor, java.awt.BorderLayout.CENTER);

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

        jPanel5.setPreferredSize(new java.awt.Dimension(650, 70));
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

        panDatTabGen.add(panCotGenSur, java.awt.BorderLayout.SOUTH);

        tabGen.addTab("General", panDatTabGen);

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

    }//GEN-LAST:event_formInternalFrameOpened

    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
  
        cerrarVen();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        cerrarVen();
    }//GEN-LAST:event_formWindowClosing

    private void txtTipDocAliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocAliActionPerformed

    }//GEN-LAST:event_txtTipDocAliActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

       Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened

private void txtCodCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaActionPerformed

}//GEN-LAST:event_txtCodCtaActionPerformed

private void txtCodCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCtaFocusGained

}//GEN-LAST:event_txtCodCtaFocusGained

private void txtCodCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCtaFocusLost

}//GEN-LAST:event_txtCodCtaFocusLost

private void txtNomCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCtaActionPerformed

}//GEN-LAST:event_txtNomCtaActionPerformed

private void txtNomCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaFocusGained

}//GEN-LAST:event_txtNomCtaFocusGained

private void txtNomCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCtaFocusLost

}//GEN-LAST:event_txtNomCtaFocusLost

private void txtSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubActionPerformed

}//GEN-LAST:event_txtSubActionPerformed

private void txtTipDocNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocNomActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtTipDocNomActionPerformed

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
      strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a8.tx_codcta, a8.tx_deslar as deslar, a.co_cli, a.tx_nomcli, a.co_doc, a.fe_Doc, a1.tx_nom as nomemp, a2.tx_descor, a2.tx_deslar, a.tx_obs1, a.tx_obs2, a.nd_poriva " +
      " ,a.ne_numcot, a.ne_numdoc, a.tx_dircli, a.tx_numped, a.tx_ate, a.co_com , a3.tx_nom, abs(a.nd_tot) as nd_tot ,abs(a.nd_sub) as nd_sub, abs(a.nd_valiva) as nd_valiva, a.co_forpag, a4.tx_des " +
      " FROM tbm_cabmovinv AS a  " +
      " INNER JOIN tbm_loc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc)  " +
      " INNER JOIN tbm_cabtipdoc AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc) " +
      " INNER JOIN tbm_placta AS a8 on (a.co_emp=a8.co_emp and a.co_cta=a8.co_cta) "+
      " LEFT JOIN tbm_usr AS a3 ON(a3.co_usr=a.co_com )   "+
      " LEFT JOIN tbm_cabforpag as a4 ON(a4.co_emp=a.co_emp AND a4.co_forpag=a.co_forpag) "+        
      " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" " +
      " AND  a.co_tipdoc ="+intCodTipDoc+" AND  a.co_doc="+intCodDoc;
      System.out.println("cargarNotaDebito()= " + strSql );
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
       if(cargarFacCab(conn, rstLoc)){  
            blnRes=true;
      }}
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
       txtCodCta.setText(rstDat.getString("tx_codcta"));
       txtNomCta.setText(rstDat.getString("deslar"));
       txtGuia.setText(rstDat.getString("ne_numdoc"));
       txtCliCod.setText(rstDat.getString("co_cli"));
       txtCliNom.setText(rstDat.getString("tx_nomcli"));
       txtDoc.setText(rstDat.getString("co_doc"));
       txtPed.setText(rstDat.getString("tx_numped"));
       txaObs1.setText(rstDat.getString("tx_obs1"));
       txaObs2.setText(rstDat.getString("tx_obs2"));
       txtSub.setText(""+ objUti.redondear(rstDat.getString("nd_sub"),2) );
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup grpRdaBut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblCta;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblGuia;
    private javax.swing.JLabel lblObs3;
    private javax.swing.JLabel lblObs4;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSubTot1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel pan2;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panButEst;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panCotGenNor;
    private javax.swing.JPanel panCotGenNorNor;
    private javax.swing.JPanel panCotGenSur;
    private javax.swing.JPanel panCotGenSurCen;
    private javax.swing.JPanel panDatTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panTabGenCen;
    private javax.swing.JPanel panTabGenNor;
    private javax.swing.JScrollPane scrollTbl;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtGuia;
    private javax.swing.JTextField txtNomCta;
    private javax.swing.JTextField txtPed;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTipDocAli;
    private javax.swing.JTextField txtTipDocNom;
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

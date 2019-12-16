/*
 * revisionTecMer.java  
 *
 * Created on 13 de agosto de 2008, 11:41  
 */
   
package Compras.ZafCom59;

import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafVenCon.ZafVenCon; 
import java.util.ArrayList;
import Librerias.ZafSelFec.ZafSelFec;

import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;


/**
 *
 * @author  jayapata
 */
public class ZafCom59 extends javax.swing.JInternalFrame {

  private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
  Librerias.ZafParSis.ZafParSis objZafParSis;
  private ZafSelFec objSelFec;
  private ZafThreadGUI objThrGUI;
  private ZafMouMotAda objMouMotAda;
  Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelLbl;
  ZafUtil objUti;
  
     final int INT_TBL_LINEA=0;
     final int INT_TBL_CODLOC=1;
     final int INT_TBL_CODTIPDOC=2;
     final int INT_TBL_DESCORTIPDOC=3;
     final int INT_TBL_DESLARTIPDOC=4;
     final int INT_TBL_CODDOC=5;
     final int INT_TBL_NUMDOC=6;
     final int INT_TBL_FECDOC=7;
     final int INT_TBL_PROCOM=8;
     final int INT_TBL_BUTANE=9;
     final int INT_TBL_CODBOD=10;
     
     String strDesCorTipDoc="";
     String strDesLarTipDoc="";
     String strCodTipDoc="";
     String strCodSol="";
     String strDesSol="";
     String strCodCli="";
     String strNomCli="";
     
     final int  INT_TBL_BUTSOLDEV=11;
     private Vector vecAux;
     Vector vecCab=new Vector();
     Vector vecCboTipDev=new Vector();
     
     ZafVenCon objVenConTipdoc; 
     ZafVenCon objVenConBodUsr;
     ZafVenCon objVenConLoc;
     Vector vecEmp;
     String strCodBod="", strNomBod="";

     javax.swing.JTextField txtCodTipDoc = new javax.swing.JTextField();
     
    /** Creates new form revisionTecMer */
    public ZafCom59(Librerias.ZafParSis.ZafParSis obj) {
    try
       {
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          initComponents();
          this.setTitle(objZafParSis.getNombreMenu()+"  v0.1 ");
          lblTit.setText(objZafParSis.getNombreMenu()); 
          objUti = new ZafUtil();  
          
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panFilFec.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            
            vecCboTipDev.add("");
            vecCboTipDev.add("N");
            vecCboTipDev.add("S");
            cboPaso.addItem(" <<Todos>> ");
            cboPaso.addItem("Proceso Incompleto ");
            cboPaso.addItem("Proceso Completo ");
         
          
             
              //*****************************************************************************
                Librerias.ZafDate.ZafDatePicker txtFecDoc;
                txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
                txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
                txtFecDoc.setHoy();
                java.util.Calendar objFec = java.util.Calendar.getInstance();
                Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
                int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
                if(fecDoc!=null){
                    objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                    objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                    objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
                }
                java.util.Calendar objFecPagActual = objFec.getInstance();
                objFecPagActual.setTime(objFec.getTime());
                objFecPagActual.add(java.util.Calendar.DATE, -30 );

                dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
                dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
                dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
                String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
                java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
                objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
                    
            //******************************************************************************* 
            
       }
       catch(CloneNotSupportedException e)
       {objUti.mostrarMsgErr_F1(this, e);} 
    }

    
      public void setEditable(boolean editable) {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }




      
public void Configura_ventana_consulta()
{
    configurarVenConLocal();
    configurarVenConTipDoc();
    configurarVenConBodUsr();
    configurarTabla(); 
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
    
    
      
private boolean configurarVenConLocal() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_loc");
        arlCam.add("a.tx_nom");
       
        ArrayList arlAli=new ArrayList();
        arlAli.add("Local");
        arlAli.add("Nom.Loc.");
        
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("400");
       
        //Armar la sentencia SQL.
        String  strSQL;
       
        strSQL="select a.co_loc, a.tx_nom  from tbm_loc as a where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a.st_reg not in ('I') ";

        objVenConLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
   return blnRes;
}
        
   

private boolean configurarVenConBodUsr() {
boolean blnRes=true;
try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("a.co_bod");
    arlCam.add("a.tx_nom");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Código");
    arlAli.add("Nom.Bod");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("350");

    //Armar la sentencia SQL.   a7.nd_stkTot,
    String Str_Sql="";
    Str_Sql="SELECT co_bod, tx_nom FROM ( " +
    " select a.co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
    " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
    " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
    " and a.co_usr="+objZafParSis.getCodigoUsuario()+" and a.co_mnu="+objZafParSis.getCodigoMenu()+"  " +
    " ) as a";

    objVenConBodUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
    arlCam=null;
    arlAli=null;
    arlAncCol=null;

    objVenConBodUsr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
}
catch (Exception e) {
    blnRes=false;
    objUti.mostrarMsgErr_F1(this, e);
}
return blnRes;
}

        
      
      
      
   
      
private boolean configurarTabla(){
  boolean blnRes=false;
  try{
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
    vecCab.add(INT_TBL_CODTIPDOC,"Cod.TipDoc.");
    vecCab.add(INT_TBL_DESCORTIPDOC,"Tip.CorDoc.");
    vecCab.add(INT_TBL_DESLARTIPDOC,"Des.LarTipDoc.");
    vecCab.add(INT_TBL_CODDOC,"Cod.Doc.");
    vecCab.add(INT_TBL_NUMDOC,"Num.Doc.");
    vecCab.add(INT_TBL_FECDOC,"Fec.Doc.");
    vecCab.add(INT_TBL_PROCOM,"Pro.Con.");
    vecCab.add(INT_TBL_BUTANE,"...");
    vecCab.add(INT_TBL_CODBOD,"Bod");


     objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
     objTblMod.setHeader(vecCab);
     tblDat.setModel(objTblMod); 
     
     tblDat.setRowSelectionAllowed(true);
     tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
     
    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
     tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  
     javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel(); 

     
      //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
          objMouMotAda=new ZafMouMotAda();
          tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
          
//     para hacer editable las celdas
    vecAux=new Vector();
    vecAux.add("" + INT_TBL_BUTANE);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
            
    new ZafTblEdi(tblDat);
  
   //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_DESCORTIPDOC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_DESLARTIPDOC).setPreferredWidth(200);
    tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_PROCOM).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_BUTANE).setPreferredWidth(25);

    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_CODLOC);
    arlColHid.add(""+INT_TBL_CODDOC);
    arlColHid.add(""+INT_TBL_CODBOD);
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;

    Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_PROCOM).setCellRenderer(objTblCelRenChk);
    
    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_PROCOM).setCellEditor(objTblCelEdiChk);
    objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
    }        
    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
    }
   });
   objTblCelRenChk=null;
   objTblCelEdiChk=null;

            
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTANE).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            new ButCotSol(tblDat, INT_TBL_BUTANE);   //*****
            
            
          new ZafTblOrd(tblDat);
         
          tblDat.getTableHeader().setReorderingAllowed(false);
          
        tcmAux=null;
        setEditable(true); 
        blnRes=true;
     
      }catch (Exception e){
          blnRes=false;
          objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
      }
    







   
private class ButCotSol extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButCotSol(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Ver Solicitud de Devolución.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaSol(intCol);
    }
}
private void llamarVentanaSol(int intCol){
   int intCodLoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString() );
   int intCodTipDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODTIPDOC).toString() );
   int intCodDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString() );
   int intCodBod=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODBOD).toString() );

   Compras.ZafCom58.ZafCom58 obj = new Compras.ZafCom58.ZafCom58(objZafParSis,  objZafParSis.getCodigoEmpresa(), intCodLoc,  intCodTipDoc , intCodDoc, intCodBod );
   this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
   obj.show(); 
                 
}
    


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        butGrp = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panCen = new javax.swing.JPanel();
        tabFil = new javax.swing.JTabbedPane();
        panTabFil = new javax.swing.JPanel();
        panFilFec = new javax.swing.JPanel();
        panFildat = new javax.swing.JPanel();
        radTotDoc = new javax.swing.JRadioButton();
        optCri = new javax.swing.JRadioButton();
        lblTipdoc = new javax.swing.JLabel();
        lblCli1 = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        butBusCli = new javax.swing.JButton();
        lblEstAut = new javax.swing.JLabel();
        cboPaso = new javax.swing.JComboBox();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBusBod = new javax.swing.JButton();
        PanDat = new javax.swing.JPanel();
        panFilTbl = new javax.swing.JPanel();
        scrTblSegSolDev = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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

        panCen.setLayout(new java.awt.BorderLayout());

        tabFil.setFont(new java.awt.Font("SansSerif", 0, 11));
        tabFil.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabFil.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabFil.setLayout(new java.awt.BorderLayout());

        panFilFec.setPreferredSize(new java.awt.Dimension(100, 85));

        javax.swing.GroupLayout panFilFecLayout = new javax.swing.GroupLayout(panFilFec);
        panFilFec.setLayout(panFilFecLayout);
        panFilFecLayout.setHorizontalGroup(
            panFilFecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 685, Short.MAX_VALUE)
        );
        panFilFecLayout.setVerticalGroup(
            panFilFecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );

        panTabFil.add(panFilFec, java.awt.BorderLayout.NORTH);

        panFildat.setPreferredSize(new java.awt.Dimension(100, 200));
        panFildat.setRequestFocusEnabled(false);
        panFildat.setLayout(null);

        butGrp.add(radTotDoc);
        radTotDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        radTotDoc.setSelected(true);
        radTotDoc.setText("Todos los Documentos");
        radTotDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTotDocActionPerformed(evt);
            }
        });
        panFildat.add(radTotDoc);
        radTotDoc.setBounds(10, 0, 160, 20);

        butGrp.add(optCri);
        optCri.setFont(new java.awt.Font("SansSerif", 0, 11));
        optCri.setText("Solo los documentos que cumplan el criterio seleccionado");
        panFildat.add(optCri);
        optCri.setBounds(10, 20, 340, 20);

        lblTipdoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipdoc.setText("Tipo de Documento:");
        panFildat.add(lblTipdoc);
        lblTipdoc.setBounds(40, 60, 100, 20);

        lblCli1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli1.setText("Local:");
        panFildat.add(lblCli1);
        lblCli1.setBounds(40, 40, 60, 20);

        txtDesCorTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
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
        panFildat.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(170, 60, 60, 20);

        txtDesLarTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
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
        panFildat.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(230, 60, 260, 20);

        txtCodLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panFildat.add(txtCodLoc);
        txtCodLoc.setBounds(170, 40, 60, 20);

        txtNomLoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        panFildat.add(txtNomLoc);
        txtNomLoc.setBounds(230, 40, 260, 20);

        butBusTipDoc.setText("jButton2");
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        panFildat.add(butBusTipDoc);
        butBusTipDoc.setBounds(490, 60, 20, 20);

        butBusCli.setText("jButton2");
        butBusCli.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusCliActionPerformed(evt);
            }
        });
        panFildat.add(butBusCli);
        butBusCli.setBounds(490, 40, 20, 20);

        lblEstAut.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEstAut.setText("Paso:");
        panFildat.add(lblEstAut);
        lblEstAut.setBounds(40, 100, 27, 20);

        cboPaso.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cboPaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPasoActionPerformed(evt);
            }
        });
        panFildat.add(cboPaso);
        cboPaso.setBounds(170, 100, 340, 20);

        lblBod.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblBod.setText("Bodega:");
        panFildat.add(lblBod);
        lblBod.setBounds(40, 80, 110, 20);

        txtCodBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        panFildat.add(txtCodBod);
        txtCodBod.setBounds(170, 80, 60, 20);

        txtNomBod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        panFildat.add(txtNomBod);
        txtNomBod.setBounds(230, 80, 260, 20);

        butBusBod.setText("jButton2");
        butBusBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusBodActionPerformed(evt);
            }
        });
        panFildat.add(butBusBod);
        butBusBod.setBounds(490, 80, 20, 20);

        panTabFil.add(panFildat, java.awt.BorderLayout.CENTER);

        tabFil.addTab("Filtro", panTabFil);

        PanDat.setLayout(new java.awt.BorderLayout());

        panFilTbl.setLayout(new java.awt.BorderLayout());

        scrTblSegSolDev.setPreferredSize(new java.awt.Dimension(454, 400));

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
        scrTblSegSolDev.setViewportView(tblDat);

        panFilTbl.add(scrTblSegSolDev, java.awt.BorderLayout.CENTER);

        PanDat.add(panFilTbl, java.awt.BorderLayout.CENTER);

        tabFil.addTab("Datos", PanDat);

        panCen.add(tabFil, java.awt.BorderLayout.CENTER);
        tabFil.getAccessibleContext().setAccessibleName("Filtro");

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 21));

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(199, 30));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void radTotDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radTotDocActionPerformed
// TODO add your handling code here:
    
    limpiarText();
    
}//GEN-LAST:event_radTotDocActionPerformed


private void limpiarText(){
            
// txtCodLoc.setText("");
// txtNomLoc.setText("");
 txtDesCorTipDoc.setText("");
 txtDesLarTipDoc.setText("");
 txtCodLoc.setText("");
 txtNomLoc.setText("");

 cboPaso.setSelectedIndex(0);

 txtCodBod.setText("");
 txtNomBod.setText("");
 strCodBod="";
 strNomBod="";

}


private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

    if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.start();
    }   
     
     
}//GEN-LAST:event_butConActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed

    cerrarVen();
//GEN-LAST:event_butCerActionPerformed
}                                      


       
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        //blnAcepta=false;
        dispose();
    }
 }
    


private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
  strDesCorTipDoc=txtDesCorTipDoc.getText();
  txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
   if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else 
                BuscarTipDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
        }else
            txtDesCorTipDoc.setText(strDesCorTipDoc); 
   
}//GEN-LAST:event_txtDesCorTipDocFocusLost


    
 public void BuscarTipDoc(String campo,String strBusqueda,int tipo){
  objVenConTipdoc.setTitle("Listado de Vendedores");
  if(objVenConTipdoc.buscar(campo, strBusqueda )) {
      txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
      txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
      txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
      strCodTipDoc=objVenConTipdoc.getValueAt(1);
      strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
      strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
      optCri.setSelected(true);
  }else{
        objVenConTipdoc.setCampoBusqueda(tipo);
        objVenConTipdoc.cargarDatos();
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
           strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
           strDesLarTipDoc=objVenConTipdoc.getValueAt(3); 
           optCri.setSelected(true);
        }else{
           txtCodTipDoc.setText(strCodTipDoc); 
           txtDesCorTipDoc.setText(strDesCorTipDoc);
           txtDesLarTipDoc.setText(strDesLarTipDoc);
  }}}
    
    


private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
 strDesLarTipDoc=txtDesLarTipDoc.getText();
 txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }else 
                BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
        }else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
 objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.show();
        if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE) {
           txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
           txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
           txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
           
           strCodTipDoc=objVenConTipdoc.getValueAt(1);
           strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
           strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
           
           optCri.setSelected(true);
        }   
        
       
}//GEN-LAST:event_butBusTipDocActionPerformed





private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
  txtCodLoc.transferFocus();
}//GEN-LAST:event_txtCodLocActionPerformed

private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
 strCodCli=txtCodLoc.getText();
 txtCodLoc.selectAll();
}//GEN-LAST:event_txtCodLocFocusGained

private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
   // TODO add your handling code here:
         if (!txtCodLoc.getText().equalsIgnoreCase(strCodCli)) {
            if(txtCodLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }else 
                BuscarLocal("a.co_loc",txtCodLoc.getText(),0);
        }else
            txtCodLoc.setText(strCodCli);
     
}//GEN-LAST:event_txtCodLocFocusLost


   
public void BuscarLocal(String campo,String strBusqueda,int tipo){
  objVenConLoc.setTitle("Listado de Locales");
  
  if(objVenConLoc.buscar(campo, strBusqueda )) {
      txtCodLoc.setText(objVenConLoc.getValueAt(1));
      txtNomLoc.setText(objVenConLoc.getValueAt(2));
      strCodCli=objVenConLoc.getValueAt(1);
      strNomCli=objVenConLoc.getValueAt(2);
      optCri.setSelected(true);
  }else{
        objVenConLoc.setCampoBusqueda(tipo);
        objVenConLoc.cargarDatos();
        objVenConLoc.show();
        if (objVenConLoc.getSelectedButton()==objVenConLoc.INT_BUT_ACE) {
           txtCodLoc.setText(objVenConLoc.getValueAt(1));
           txtNomLoc.setText(objVenConLoc.getValueAt(2));
           strCodCli=objVenConLoc.getValueAt(1);
           strNomCli=objVenConLoc.getValueAt(2);
           optCri.setSelected(true);
        }else{
            txtCodLoc.setText(strCodCli);
            txtNomLoc.setText(strNomCli);
  }}}
     


private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
   txtNomLoc.transferFocus();
}//GEN-LAST:event_txtNomLocActionPerformed

private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
 strNomCli=txtNomLoc.getText();
 txtNomLoc.selectAll();
}//GEN-LAST:event_txtNomLocFocusGained

private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
  // TODO add your handling code here:
          if (!txtNomLoc.getText().equalsIgnoreCase(strNomCli)) {
            if (txtNomLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }else 
                BuscarLocal("a.tx_nom",txtNomLoc.getText(),1);
        }else
            txtNomLoc.setText(strNomCli);
    
}//GEN-LAST:event_txtNomLocFocusLost

private void butBusCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusCliActionPerformed
    // TODO add your handling code here:
    
    objVenConLoc.setTitle("Listado de Locales");

            objVenConLoc.setCampoBusqueda(1);
            objVenConLoc.show();
            if (objVenConLoc.getSelectedButton()==objVenConLoc.INT_BUT_ACE) {
               txtCodLoc.setText(objVenConLoc.getValueAt(1));
               txtNomLoc.setText(objVenConLoc.getValueAt(2));
               strCodCli=objVenConLoc.getValueAt(1);
               strNomCli=objVenConLoc.getValueAt(2);
               optCri.setSelected(true);
            }      
}//GEN-LAST:event_butBusCliActionPerformed

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void cboPasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPasoActionPerformed
    // TODO add your handling code here:
    if(cboPaso.getSelectedIndex() > 0 ){
        optCri.setSelected(true);
    }
}//GEN-LAST:event_cboPasoActionPerformed

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formInternalFrameClosing

private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
    // TODO add your handling code here:
    txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
    // TODO add your handling code here:
    strCodBod=txtCodBod.getText();
    txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
    // TODO add your handling code here:
    if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
        if (txtCodBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        }else
            BuscarBod("a.co_bod",txtCodBod.getText(),0);
    }else
        txtCodBod.setText(strCodBod);
}//GEN-LAST:event_txtCodBodFocusLost

private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
    // TODO add your handling code here:
    txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
    // TODO add your handling code here:
    strNomBod=txtNomBod.getText();
    txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
    // TODO add your handling code here:
    if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
        if (txtNomBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        }else
            BuscarBod("a.tx_nom",txtNomBod.getText(),1);
    }else
        txtNomBod.setText(strNomBod);
}//GEN-LAST:event_txtNomBodFocusLost

private void butBusBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusBodActionPerformed
    // TODO add your handling code here:
    objVenConBodUsr.setTitle("Listado de Bodegas");
    objVenConBodUsr.setCampoBusqueda(1);
    objVenConBodUsr.show();
    if (objVenConBodUsr.getSelectedButton()==objVenConBodUsr.INT_BUT_ACE) {
        txtCodBod.setText(objVenConBodUsr.getValueAt(1));
        txtNomBod.setText(objVenConBodUsr.getValueAt(2));
        strCodBod=objVenConBodUsr.getValueAt(1);
        strNomBod=objVenConBodUsr.getValueAt(2);
        optCri.setSelected(true);
    }
}//GEN-LAST:event_butBusBodActionPerformed

 public void BuscarBod(String campo,String strBusqueda,int tipo){
  objVenConBodUsr.setTitle("Listado de Bodegas");
  if(objVenConBodUsr.buscar(campo, strBusqueda )) {
        txtCodBod.setText(objVenConBodUsr.getValueAt(1));
        txtNomBod.setText(objVenConBodUsr.getValueAt(2));
        strCodBod=objVenConBodUsr.getValueAt(1);
        strNomBod=objVenConBodUsr.getValueAt(2);
        optCri.setSelected(true);
  }else{
        objVenConBodUsr.setCampoBusqueda(tipo);
        objVenConBodUsr.cargarDatos();
        objVenConBodUsr.show();
        if (objVenConBodUsr.getSelectedButton()==objVenConBodUsr.INT_BUT_ACE) {
           txtCodBod.setText(objVenConBodUsr.getValueAt(1));
           txtNomBod.setText(objVenConBodUsr.getValueAt(2));
           strCodBod=objVenConBodUsr.getValueAt(1);
           strNomBod=objVenConBodUsr.getValueAt(2);
           optCri.setSelected(true);
        }else{
           txtCodBod.setText(strCodBod);
           txtNomBod.setText(strNomBod);
  }}}



 
private String condiconSql(){
   String sqlFil="";
       
      if(optCri.isSelected()){
        
        if(!txtCodTipDoc.getText().equals(""))
            sqlFil+=" AND a.co_tipdoc="+txtCodTipDoc.getText();
          
        if(!txtCodLoc.getText().equals(""))
            sqlFil+=" AND a.co_loc="+txtCodLoc.getText();
        
        if(!txtCodBod.getText().equals(""))
            sqlFil+=" AND a.co_bod="+txtCodBod.getText();
     
        if(cboPaso.getSelectedIndex() > 0 )
           sqlFil+=" AND a.st_procom='"+vecCboTipDev.get(cboPaso.getSelectedIndex()).toString()+"'";
        
      }
        
       if(objSelFec.isCheckBoxChecked() ){
         switch (objSelFec.getTipoSeleccion())
         {
                    case 0: //Búsqueda por rangos
                        sqlFil+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objZafParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
        }}
   return sqlFil;             
}



private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);    

    consultar(condiconSql());  
    tabFil.setSelectedIndex(1);
    
   objThrGUI=null;
}
}

    
     
     

private void consultar(String strFil){
java.sql.Connection conn;
java.sql.Statement  stm;
java.sql.ResultSet rst;
String strSql="";
try{
   conn = java.sql.DriverManager.getConnection( objZafParSis.getStringConexion() , objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
   if (conn!=null){

        stm=conn.createStatement();

        strSql="select a.co_loc, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc , a.st_procom, a.co_bod " +
        " from tbm_cabrepinvprv as a " +
        " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
        " where a.co_emp="+objZafParSis.getCodigoEmpresa()+"  "+strFil+"  ORDER BY a.co_emp, a.co_loc, a.ne_numdoc ";

        System.out.println(" "+ strSql );
        Vector vecData = new Vector();
        rst = stm.executeQuery(strSql);
        while(rst.next()){
                 java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_CODTIPDOC, rst.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_DESCORTIPDOC, rst.getString("tx_descor") );
                    vecReg.add(INT_TBL_DESLARTIPDOC, rst.getString("tx_deslar") );
                    vecReg.add(INT_TBL_CODDOC, rst.getString("co_Doc") );
                    vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc") );
                    vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc") );
                    vecReg.add(INT_TBL_PROCOM, new Boolean( (rst.getString("st_procom").equals("S")?true:false)  ));
                    vecReg.add(INT_TBL_BUTANE,"...");
                    vecReg.add(INT_TBL_CODBOD, rst.getString("co_bod") );
                  vecData.add(vecReg); 
         }
         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);
   
      rst.close();
      rst=null;
      stm.close();
      stm=null;
      conn.close();
      conn=null;
    }
     lblMsgSis.setText("Listo");
     pgrSis.setValue(0);
     pgrSis.setIndeterminate(false);    

}
catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);   }
catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);          }
}




 
 
//   Para adicionar el Tooltip

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                    
                case INT_TBL_CODLOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DESCORTIPDOC:
                    strMsg="Descripción corta tipo de documento";
                    break;
                case INT_TBL_DESLARTIPDOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CODDOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_FECDOC:
                    strMsg="Fecha del documento";
                    break;
           
              case INT_TBL_PROCOM:
                    strMsg="Proceso completo";
                    break;   
             }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }}
 
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanDat;
    private javax.swing.JButton butBusBod;
    private javax.swing.JButton butBusCli;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.ButtonGroup butGrp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboPaso;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCli1;
    private javax.swing.JLabel lblEstAut;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optCri;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFilTbl;
    private javax.swing.JPanel panFildat;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTabFil;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radTotDoc;
    private javax.swing.JScrollPane scrTblSegSolDev;
    private javax.swing.JTabbedPane tabFil;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables

}

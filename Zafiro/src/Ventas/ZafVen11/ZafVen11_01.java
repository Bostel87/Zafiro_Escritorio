/*
 * ZafVen25_01.java
 *
 * Created on August 6, 2008, 4:03 PM
 */

package Ventas.ZafVen11;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**  
 *
 * @author  jayapata
 */
public class ZafVen11_01 extends javax.swing.JDialog {
    private ZafParSis objZafParSis;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblMod;
    private ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
     
    private static final int INT_TBL_LINEA     = 0; 
    private static final int INT_TBL_CODLOC    = 1;
    private static final int INT_TBL_CODTIPDOC = 2;
    private static final int INT_TBL_DCOTIPDOC = 3;
    private static final int INT_TBL_DCATIPDOC = 4;
    private static final int INT_TBL_CODDOC    = 5;
    private static final int INT_TBL_NUMDOC    = 6;
    private static final int INT_TBL_FECDOC    = 7;
    private static final int INT_TBL_CODCLI    = 8;
    private static final int INT_TBL_DESCLI    = 9;
    private static final int INT_TBL_TOTFAC    = 10;
    private static final int INT_TBL_BUTBUS    = 11;
    
    private Vector vecCab=new Vector(); 
    private ZafVenCon objVenConCLi, objVenConVen; 
    private String strCodCli="";
    private String strDesCli="";
    private String strCodVen="";
    private String strDesVen="";
    private String Str_RegSel[]; 
    public boolean blnAcepta = false; 
    
    /** Creates new form ZafVen25_01 */
public ZafVen11_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis ZafParSis) {
        super(parent, modal);
       try{ 
        this.objZafParSis = ZafParSis;
        objUti = new ZafUtil();
        initComponents();
        this.setTitle( objZafParSis.getNombreMenu() );
        lblTit.setText( objZafParSis.getNombreMenu());
        
        }catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);  } 
    }    
    
private void configurarFrm(){
      configuraTbl();
      
      configurarVenConClientes();
      configurarVenConVendedor();
  } 
    
private boolean configurarVenConClientes() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("a.co_cli");
        arlCam.add("a.tx_nom");
        arlCam.add("a.tx_dir");
        arlCam.add("a.tx_tel");
        arlCam.add("a.tx_ide");

        ArrayList arlAli=new ArrayList();
        arlAli.add("Código");
        arlAli.add("Nom.Cli.");
        arlAli.add("Dirección");
        arlAli.add("Telefono");
        arlAli.add("RUC/CI");

        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("180");
        arlAncCol.add("120");
        arlAncCol.add("80");
        arlAncCol.add("100");           

        //Armar la sentencia SQL.
        String  strSQL;
        //strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbm_cli as a " +
        //" WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.st_cli='S' order by a.tx_nom "; 

        strSQL="SELECT a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide FROM tbr_cliloc as a1 " +
        " INNER JOIN tbm_cli as a ON(a.co_emp=a1.co_emp AND a.co_cli=a1.co_cli) " +
        " WHERE a1.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND a.st_cli='S' AND a.st_reg NOT IN('I','T')  order by a.tx_nom ";

        objVenConCLi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }catch (Exception e) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
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
    
        
  
private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
	    vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
	    vecCab.add(INT_TBL_CODTIPDOC,"Cod.TipDoc.");
	    vecCab.add(INT_TBL_DCOTIPDOC,"Tip.CorDoc.");
	    vecCab.add(INT_TBL_DCATIPDOC,"Des.LarTipDoc.");
	    vecCab.add(INT_TBL_CODDOC,"Cod.Doc.");
	    vecCab.add(INT_TBL_NUMDOC,"Num.Doc.");
	    vecCab.add(INT_TBL_FECDOC,"Fec.Doc.");
	    vecCab.add(INT_TBL_CODCLI,"Cod.Cli.");
            vecCab.add(INT_TBL_DESCLI,"Des.Cli");
	    vecCab.add(INT_TBL_TOTFAC,"Total.");
	    vecCab.add(INT_TBL_BUTBUS,"...");
                 
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
	    
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTBUS);
	    objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            objTblMod.setColumnDataType(INT_TBL_TOTFAC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DCOTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCLI).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_TOTFAC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_BUTBUS).setPreferredWidth(25);
            
            ZafTblCelRenLbl objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_TOTFAC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_DCATIPDOC);
            arlColHid.add(""+INT_TBL_CODDOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            ZafTblCelRenBut objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBUS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            ButCot butCot = new ButCot(tblDat, INT_TBL_BUTBUS); //*****
           
            Str_RegSel = new String[4];
            
             tblDat.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if(! ((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow() ==-1)) )
                        if(evt.getClickCount()==2){

                            Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC ).toString();
                            Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC ).toString();
                            Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC ).toString();
                            Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMDOC ).toString();
                            blnAcepta = true;
                            dispose();
                        }
                }
            });
            
           //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panCabGen.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            //*****************************************************************************
            ZafDatePicker txtFecDoc;
            txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            txtFecDoc.setHoy();
            Calendar objFec = Calendar.getInstance();
            ZafDatePicker dtePckPag = new ZafDatePicker(new JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
                objFec.set(Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(Calendar.YEAR, fecDoc[2]);
            }
            Calendar objFecPagActual = Calendar.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(Calendar.DATE, -30 );
            
            dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            // System.out.println("Fecha: "+ objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            // System.out.println("Fecha: "+ objSelFec.getFechaDesde() );
            
            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //******************************************************************************* 
            
            ZafTblEdi zafTblEdi1 = new ZafTblEdi(tblDat);
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
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
   
    public boolean acepta(){
        return blnAcepta;
    }
         
private class ButCot extends ZafTableColBut_uni{
    
    public ButCot(JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    
    @Override
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentana(intCol);
        
    }
}
       
   private void llamarVentana(int intCol){
         int intCodEmp= objZafParSis.getCodigoEmpresa();
         String strCocLoc = (tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODLOC  ).toString());
         String strDocTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  ).toString());
         String strCocDoc =  (tblDat.getValueAt(intCol,  INT_TBL_CODDOC  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODDOC  ).toString());
           
         ZafVen11_fac obj = new ZafVen11_fac(JOptionPane.getFrameForComponent(this), true, objZafParSis, intCodEmp, strCocLoc, strDocTipDoc, strCocDoc );
         obj.show(); 
   }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butgrp = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panTabGen = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        panCabGen = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        radCri = new javax.swing.JRadioButton();
        radTotDoc = new javax.swing.JRadioButton();
        txtNomCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        butcli = new javax.swing.JButton();
        lblCli1 = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblCli2 = new javax.swing.JLabel();
        txtnumdoc = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panButEst = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butLim = new javax.swing.JButton();
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

        panTit.setPreferredSize(new java.awt.Dimension(100, 20));

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        panTabGen.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        panCabGen.setPreferredSize(new java.awt.Dimension(100, 165));
        panCabGen.setLayout(null);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        panCabGen.add(lblCli);
        lblCli.setBounds(30, 120, 60, 20);

        butgrp.add(radCri);
        radCri.setFont(new java.awt.Font("SansSerif", 0, 11));
        radCri.setText("Todos los documentos que cumplan el criterio de busqueda");
        panCabGen.add(radCri);
        radCri.setBounds(10, 100, 340, 20);

        butgrp.add(radTotDoc);
        radTotDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        radTotDoc.setSelected(true);
        radTotDoc.setText("Todos los documentos");
        radTotDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTotDocActionPerformed(evt);
            }
        });
        panCabGen.add(radTotDoc);
        radTotDoc.setBounds(10, 80, 150, 20);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panCabGen.add(txtNomCli);
        txtNomCli.setBounds(140, 120, 280, 20);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panCabGen.add(txtCodCli);
        txtCodCli.setBounds(90, 120, 50, 20);

        butcli.setFont(new java.awt.Font("SansSerif", 0, 11));
        butcli.setText("...");
        butcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcliActionPerformed(evt);
            }
        });
        panCabGen.add(butcli);
        butcli.setBounds(420, 120, 20, 20);

        lblCli1.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli1.setText("Vendedor:");
        panCabGen.add(lblCli1);
        lblCli1.setBounds(30, 140, 60, 20);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        panCabGen.add(txtCodVen);
        txtCodVen.setBounds(90, 140, 50, 20);

        txtNomVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panCabGen.add(txtNomVen);
        txtNomVen.setBounds(140, 140, 280, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 0, 11));
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panCabGen.add(butVen);
        butVen.setBounds(420, 140, 20, 20);

        lblCli2.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli2.setText("Número de documento:");
        panCabGen.add(lblCli2);
        lblCli2.setBounds(460, 120, 120, 20);

        txtnumdoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        txtnumdoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnumdocKeyPressed(evt);
            }
        });
        panCabGen.add(txtnumdoc);
        txtnumdoc.setBounds(580, 120, 70, 20);

        jPanel4.add(panCabGen, java.awt.BorderLayout.NORTH);

        jPanel6.setLayout(new java.awt.BorderLayout());

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

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel4);

        panTabGen.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(panTabGen, java.awt.BorderLayout.CENTER);

        panBut.setPreferredSize(new java.awt.Dimension(100, 50));
        panBut.setLayout(new java.awt.BorderLayout());

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panButEst.add(butCon);

        butLim.setText("Limpiar");
        butLim.setPreferredSize(new java.awt.Dimension(90, 23));
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panButEst.add(butLim);

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(90, 23));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panButEst.add(butAce);

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(90, 23));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panButEst.add(butCan);

        panBut.add(panButEst, java.awt.BorderLayout.LINE_END);

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

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.PAGE_END);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        configurarFrm();
    }//GEN-LAST:event_formWindowOpened

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        
        cerrarVen();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
          cerrarVen();
    }//GEN-LAST:event_formWindowClosing

    private void butcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcliActionPerformed
        // TODO add your handling code here:
            objVenConCLi.setTitle("Listado de Clientes");
            objVenConCLi.setCampoBusqueda(1);
            objVenConCLi.show();
            if (objVenConCLi.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
               txtCodCli.setText(objVenConCLi.getValueAt(1));
               txtNomCli.setText(objVenConCLi.getValueAt(2));
               radCri.setSelected(true);
            }      
    }//GEN-LAST:event_butcliActionPerformed

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
         txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        // TODO add your handling code here:
         txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
         strDesCli=txtNomCli.getText();
         txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
         strCodCli=txtCodCli.getText();
         txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
          if (!txtNomCli.getText().equalsIgnoreCase(strDesCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else 
                BuscarCliente("a.tx_nom",txtNomCli.getText(),1);
        }else
            txtNomCli.setText(strDesCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
         if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if(txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else 
                BuscarCliente("a.co_cli",txtCodCli.getText(),0);
        }else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        // TODO add your handling code here:
          if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if(txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else 
                BuscarVendedor("a.tx_nom",txtNomVen.getText(),1);
        }else
            txtNomVen.setText(strDesVen);
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        // TODO add your handling code here:
          if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if(txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else 
                BuscarVendedor("a.co_usr",txtCodVen.getText(),0);
        }else
            txtCodVen.setText(strCodVen);
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        // TODO add your handling code here:
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        // TODO add your handling code here:
         strCodVen=txtCodVen.getText();
         txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        // TODO add your handling code here:
         strDesVen=txtNomVen.getText();
         txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        // TODO add your handling code here:
        objVenConVen.setTitle("Listado de Clientes");
        objVenConVen.setCampoBusqueda(1);
        //objVenConVen.show();
        objVenConVen.setVisible(true);
        if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodVen.setText(objVenConVen.getValueAt(1));
           txtNomVen.setText(objVenConVen.getValueAt(2));
           radCri.setSelected(true);
        }     
    }//GEN-LAST:event_butVenActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        
    if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.start();
    }   
         
        
    }//GEN-LAST:event_butConActionPerformed

    
    private String sqlConFil(){
       String sqlFil="";
       
      if(radCri.isSelected()){
        
        if(!txtCodCli.getText().equals(""))
            sqlFil+=" AND a.co_cli="+txtCodCli.getText();
        
        if(!txtNomCli.getText().equals(""))
            sqlFil+=" AND a.tx_nomcli lIKE '"+txtNomCli.getText()+"'";
         
        if(!txtCodVen.getText().equals(""))
            sqlFil+=" AND a.co_com="+txtCodVen.getText();
       
        if(!txtnumdoc.getText().equals(""))
            sqlFil+=" AND a.ne_numdoc="+txtnumdoc.getText();
               
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
        @Override
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);    

    consultarDat(sqlConFil());
    
   
     
   objThrGUI=null;
}
}
   
    
    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        // TODO add your handling code here:
        
        
        objTblMod.removeAllRows();
        
                
    }//GEN-LAST:event_butLimActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:
   if(! ((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow() ==-1)) ){
        Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC ).toString();
        Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC ).toString();
        Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC ).toString();
        Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMDOC ).toString();
        blnAcepta = true;
    }
        dispose();
                            
    }//GEN-LAST:event_butAceActionPerformed

    private void txtnumdocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnumdocKeyPressed
        // TODO add your handling code here:
        radCri.setSelected(true);
    }//GEN-LAST:event_txtnumdocKeyPressed

    private void radTotDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radTotDocActionPerformed
        // TODO add your handling code here:
         strCodCli="";  strDesCli="";
         strCodVen="";  strDesVen="";
    
        txtnumdoc.setText("");
        txtCodCli.setText("");
        txtNomCli.setText("");
        txtCodVen.setText("");
        txtNomVen.setText("");
                
    }//GEN-LAST:event_radTotDocActionPerformed
    
private boolean consultarDat(String sqlFil){
 boolean blnRes=false;
 Connection conn;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
   conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      vecData = new Vector();
      
      /*strSql="SELECT  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a1.tx_descor, a1.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli, round(abs(a.nd_tot),2) as nd_tot " +
      " FROM  tbm_cabmovinv as a " +
      " INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc) " +
      " WHERE  a.co_Emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
      " AND a.co_tipdoc IN ( " +
      "  SELECT b.co_tipdoc FROM tbr_tipdocdetprg as b WHERE b.co_emp="+objZafParSis.getCodigoEmpresa()+" AND " +
      " b.co_loc = "+objZafParSis.getCodigoLocal()+" AND  b.co_mnu ="+objZafParSis.getCodigoMenu()+" ) "+
      " AND a.ne_numdoc > 0 AND a.st_reg NOT IN('I','E') "+sqlFil+"  ORDER BY a.fe_Doc ";*/
      
      strSql=" SELECT  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc  ,a1.tx_descor, a1.tx_deslar, a.ne_numdoc, a.fe_doc, a.co_cli, a.tx_nomcli, round(abs(a.nd_tot),2) as nd_tot " +
      " FROM tbm_cabmovinv as a " +
      " INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc) " +
      " WHERE a.co_Emp="+objZafParSis.getCodigoEmpresa() +
      " AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
      " AND a.co_tipdoc IN ( " +
      " SELECT b.co_tipdoc " +
      " FROM tbr_tipdocdetprg as b " +
      " WHERE b.co_emp="+objZafParSis.getCodigoEmpresa() +
      " AND b.co_loc = "+objZafParSis.getCodigoLocal() +
      " AND  b.co_mnu ="+objZafParSis.getCodigoMenu() +
      " ) " +
      " AND a.ne_numdoc > 0 AND a.st_reg NOT IN('I','E') " + 
      sqlFil +
      " ORDER BY a.fe_Doc,a.ne_numdoc ";
      
     // System.out.println(""+strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
	  vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
	  vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc") );
	  vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("tx_descor") );
	  vecReg.add(INT_TBL_DCATIPDOC, rstLoc.getString("tx_deslar") );
	  vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
	  vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
	  vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
	  vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
          vecReg.add(INT_TBL_DESCLI, rstLoc.getString("tx_nomcli") );
	  vecReg.add(INT_TBL_TOTFAC, rstLoc.getString("nd_tot") );
	  vecReg.add(INT_TBL_BUTBUS,"..."); 
          vecData.add(vecReg);
       }
      objTblMod.setData(vecData);
      tblDat .setModel(objTblMod);
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
  }
 }catch(SQLException Evt) {  
     blnRes=false; 
     objUti.mostrarMsgErr_F1(this, Evt);  
 }catch(Exception Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(this, Evt); 
 }
 return blnRes;          
}   
    
    
    
    
public void BuscarCliente(String campo,String strBusqueda,int tipo){
  objVenConCLi.setTitle("Listado de Clientes");
  if(objVenConCLi.buscar(campo, strBusqueda )) {
      txtCodCli.setText(objVenConCLi.getValueAt(1));
      txtNomCli.setText(objVenConCLi.getValueAt(2));
      radCri.setSelected(true);
  }else{
        objVenConCLi.setCampoBusqueda(tipo);
        objVenConCLi.cargarDatos();
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
           txtCodCli.setText(objVenConCLi.getValueAt(1));
           txtNomCli.setText(objVenConCLi.getValueAt(2));
           radCri.setSelected(true);
        }else{
            txtCodCli.setText(strCodCli);
            txtNomCli.setText(strDesCli);
  }}}
     
     
public void BuscarVendedor(String campo,String strBusqueda,int tipo){
  objVenConVen.setTitle("Listado de Vendedores");
  if(objVenConVen.buscar(campo, strBusqueda )) {
      txtCodVen.setText(objVenConVen.getValueAt(1));
      txtNomVen.setText(objVenConVen.getValueAt(2));
      radCri.setSelected(true);
  }else{
        objVenConVen.setCampoBusqueda(tipo);
        objVenConVen.cargarDatos();
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            radCri.setSelected(true);
        }else{
            txtCodVen.setText(strCodVen);
            txtNomVen.setText(strDesVen);
  }}}
  
        
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    //JOptionPane oppMsg=new JOptionPane();
    String strTit="Mensaje del sistema Zafiro";
    if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
        //System.gc();
        Runtime.getRuntime().gc();
        dispose();
    }
 }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLim;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butcli;
    private javax.swing.ButtonGroup butgrp;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCli1;
    private javax.swing.JLabel lblCli2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panButEst;
    private javax.swing.JPanel panCabGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radCri;
    private javax.swing.JRadioButton radTotDoc;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtnumdoc;
    // End of variables declaration//GEN-END:variables
    
    @Override
    protected void finalize() throws Throwable {   
        //System.gc();
        Runtime.getRuntime().gc();
        super.finalize();
    }
    
       
}

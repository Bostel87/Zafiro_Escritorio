/*
 * Created on 13 de agosto de 2008, 10:26
 */

package Ventas.ZafVen30;

import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafSelFec.ZafSelFec;

/**
 *
 * @author  jayapata
 */
public class ZafVen30 extends javax.swing.JInternalFrame {
     Librerias.ZafParSis.ZafParSis objZafParSis;
     private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
     ZafUtil objUti;
     private ZafThreadGUI objThrGUI;
     private ZafSelFec objSelFec;
        
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODEMP=1;
    final int INT_TBL_CODLOC=2;
    final int INT_TBL_CODTIPDOC=3;
    final int INT_TBL_DCOTIPDOC=4;
    final int INT_TBL_DCATIPDOC=5;
    final int INT_TBL_CODDOC =6;
    final int INT_TBL_NUMDOC =7;
    final int INT_TBL_FECDOC =8;
    final int INT_TBL_CODCLI =9;
    final int INT_TBL_DESCLI =10;
    final int INT_TBL_USRSOL =11;
    final int INT_TBL_BUTFAC =12;
    final int INT_TBL_BUTSOL =13;
    final int INT_TBL_VOLFAC =14;
    final int INT_TBL_CODLOCREL =15;
    final int INT_TBL_CONTIPDOCREL =16;
    final int INT_TBL_CODDOCREL =17;
    
    Vector vecCab=new Vector(); 
    
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    
    /** Creates new form pantalla1 */
    public ZafVen30(Librerias.ZafParSis.ZafParSis obj) {
        try{
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
	    
            this.setTitle(objZafParSis.getNombreMenu()+"  v0.1 ");
            lblTit.setText(objZafParSis.getNombreMenu());
            
	    objUti = new ZafUtil();
	    
            configuraTbl();
            
             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxVisible(true);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
             
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

            // System.out.println("Fecha: "+ objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            // System.out.println("Fecha: "+ objSelFec.getFechaDesde() );
            
            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //******************************************************************************* 
           
            
	 }catch (CloneNotSupportedException e){
               objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    
    
    
    
private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
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
	    vecCab.add(INT_TBL_USRSOL,"Usr.Sol");
	    vecCab.add(INT_TBL_BUTFAC,"...");
	    vecCab.add(INT_TBL_BUTSOL,"...");
	    vecCab.add(INT_TBL_VOLFAC,"Vol.Fac.");
	    vecCab.add(INT_TBL_CODLOCREL,"");
	    vecCab.add(INT_TBL_CONTIPDOCREL,"");
	    vecCab.add(INT_TBL_CODDOCREL,"");
	    
            
	    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
    	    
	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
	    
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTFAC);
            vecAux.add("" + INT_TBL_BUTSOL);
            vecAux.add("" + INT_TBL_VOLFAC);
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
            tcmAux.getColumn(INT_TBL_VOLFAC).setPreferredWidth(70);
            
            
            //tcmAux.getColumn(INT_TBL_DESCLI).setCellRenderer(new RenderDecimales() );
             
             
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_CODDOC);
            arlColHid.add(""+INT_TBL_CODLOCREL);
            arlColHid.add(""+INT_TBL_CONTIPDOCREL);
            arlColHid.add(""+INT_TBL_CODDOCREL);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTFAC).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            new ButCotFac(tblDat, INT_TBL_BUTFAC);   //*****
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTSOL).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            new ButCotSol(tblDat, INT_TBL_BUTSOL);   //*****
                
            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_VOLFAC).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_VOLFAC).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
               });
            objTblCelRenChk=null;
            objTblCelEdiChk=null; 
            
         
            
         
             
           objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
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



             
private class ButCotFac extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButCotFac(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Ver Factura Comercial.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaFac(intCol);
    }
}
private void llamarVentanaFac(int intCol){
    // int intCodEmp= objZafParSis.getCodigoEmpresa();
   
     int intCodEmp = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODEMP  ).toString()) );
   
     String strCocLoc = (tblDat.getValueAt(intCol,  INT_TBL_CODLOCREL  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODLOCREL  ).toString());
     String strDocTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CONTIPDOCREL  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CONTIPDOCREL  ).toString());
     String strCocDoc =  (tblDat.getValueAt(intCol,  INT_TBL_CODDOCREL  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODDOCREL  ).toString());

     Ventas.ZafVen11.ZafVen11_fac obj = new Ventas.ZafVen11.ZafVen11_fac(javax.swing.JOptionPane.getFrameForComponent(this), false, objZafParSis, intCodEmp, strCocLoc, strDocTipDoc, strCocDoc );
     obj.show();       
          
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
   //int intCodEmp=objZafParSis.getCodigoEmpresa();
  // int intCodLoc=objZafParSis.getCodigoLocal();
   
   int intCodEmp = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODEMP  ).toString()) );
   int intCodLoc = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODLOC  ).toString()) );
   
   
   int intCodTipDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODTIPDOC).toString() );
   int intCodDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString() );
           
   Ventas.ZafVen11.ZafVen11 obj = new Ventas.ZafVen11.ZafVen11(objZafParSis,  intCodEmp, intCodLoc,  intCodTipDoc , intCodDoc );
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

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panFilTabGen = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCab = new javax.swing.JPanel();
        chkSolFacPen = new javax.swing.JCheckBox();
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

        lblTit.setText("titulo");
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

        panCab.setPreferredSize(new java.awt.Dimension(0, 105));
        panCab.setLayout(null);

        chkSolFacPen.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkSolFacPen.setText("Mostrar las solicitudes que tienen factura pendiente de emitir que fueron canceladas.");
        chkSolFacPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolFacPenActionPerformed(evt);
            }
        });
        panCab.add(chkSolFacPen);
        chkSolFacPen.setBounds(0, 80, 480, 20);

        panFilTabGen.add(panCab, java.awt.BorderLayout.PAGE_START);

        tabGen.addTab("Filtro", panFilTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panButPanBut.add(butCon);

        butGua.setFont(new java.awt.Font("SansSerif", 0, 11));
        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(79, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panButPanBut.add(butGua);

        butCer.setFont(new java.awt.Font("SansSerif", 0, 11));
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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
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
        
    if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.start();
    }   
         
         
         
       
    }//GEN-LAST:event_butConActionPerformed

    
    
    
private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);    

    consultarDat();
     
   objThrGUI=null;
}
}


    
    
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
       guardarDat(); 
       
       consultarDat();
       
    }//GEN-LAST:event_butGuaActionPerformed

    private void chkSolFacPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolFacPenActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_chkSolFacPenActionPerformed
    
    
    
     
       
    
private boolean guardarDat(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 String strSql="";
 String strVolFac="N";
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
       conn.setAutoCommit(false);
       stmLoc=conn.createStatement();
     
      for(int i=0; i<tblDat.getRowCount(); i++){
       if( tblDat.getValueAt(i, INT_TBL_CODEMP)!= null ){
           strVolFac="N";
            if((tblDat.getValueAt(i, INT_TBL_VOLFAC).toString().equals("true")))
               strVolFac="S";

            strSql="UPDATE tbm_cabsoldevven SET  st_volfacmersindev='"+strVolFac+"' " +
            " WHERE co_emp="+tblDat.getValueAt(i, INT_TBL_CODEMP).toString()+" AND co_loc="+tblDat.getValueAt(i, INT_TBL_CODLOC).toString()+" " +
            " AND co_tipdoc="+tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()+" AND co_doc="+tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
            stmLoc.executeUpdate(strSql);
     }}
      stmLoc.close();
      stmLoc=null;
     conn.commit(); 
    conn.close();
    conn=null;
    blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
    
    
      
    


    
        
    
    
    
    
private boolean consultarDat(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="",strSQLAux="", sqlFil="";
 String strVolFac="S";
 Vector vecData;
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      vecData = new Vector();
      
      if(chkSolFacPen.isSelected()) strVolFac="N";
          
          
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
      
       if(!(objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo()))
          strSQLAux=" a.co_emp= "+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+"   AND ";
     
        
      strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.co_doc, a.ne_numdoc, a.fe_doc, a.co_cli, " +
      " a.tx_nomcli, a.tx_nomusrsol ,a.co_locrel, a.co_tipdocrel, a.co_docrel, a.st_volfacmersindev FROM tbm_cabsoldevven as a " +
      " INNER JOIN tbm_cabtipdoc as a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc ) " +
      " WHERE "+strSQLAux+" a.st_reg='A' AND a.st_volfacmersindev='"+strVolFac+"' AND a.st_mersindevfac='N' "+sqlFil+" "+
      " ORDER BY a.fe_doc, a.ne_numdoc ";
      //System.out.println(""+strSql);
      rstLoc=stmLoc.executeQuery(strSql);
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
	  vecReg.add(INT_TBL_USRSOL,  rstLoc.getString("tx_nomusrsol") );
	  vecReg.add(INT_TBL_BUTFAC,"..");
	  vecReg.add(INT_TBL_BUTSOL,"..");
	  vecReg.add(INT_TBL_VOLFAC, new Boolean( (rstLoc.getString("st_volfacmersindev").equals("S")?true:false) ) );
          vecReg.add(INT_TBL_CODLOCREL, rstLoc.getString("co_locrel") );
	  vecReg.add(INT_TBL_CONTIPDOCREL, rstLoc.getString("co_tipdocrel") );
          vecReg.add(INT_TBL_CODDOCREL, rstLoc.getString("co_docrel") );
	  
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
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
    
    
      
    
    

    
    
   
    private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        dispose();
   }}
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JCheckBox chkSolFacPen;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panButPanBut;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFilTabGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
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
        case INT_TBL_VOLFAC:
            strMsg="Volver a facturar mercaderia no devuelta.";
            break;
       
        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}

	 
}

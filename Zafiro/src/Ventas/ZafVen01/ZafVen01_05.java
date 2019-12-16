/*
 * pantalladialogo.java
 *
 * Created on 13 de agosto de 2008, 10:45
 */

package Ventas.ZafVen01;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
/**
 *
 * @author  jayapata
 */
public class ZafVen01_05 extends javax.swing.JDialog {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    ZafUtil objUti;
    private ZafThreadGUI objThrGUI;
     
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_NUMFAC=1;
    final int INT_TBL_CODLOC=2;
    final int INT_TBL_CODTIPDOC=3;
    final int INT_TBL_CODDOC =4;
    final int INT_TBL_NUMDOCSOL=5;
    final int INT_TBL_CODCLI =6;
    final int INT_TBL_DESCLI =7;
    final int INT_TBL_NUMCOT =8;
    
    Vector vecCab=new Vector(); 
    ZafVenCon objVenConCLi;
    ZafVenCon objVenConVen; 
    String strCodCli="";
    String strDesCli="";
    String strCodSol="";
    String strDesSol="";
    private String Str_RegSel[]; 
    public boolean blnAcepta = false; 
    
    /** Creates new form pantalladialogo */
    public ZafVen01_05(java.awt.Frame parent, boolean modal ,Librerias.ZafParSis.ZafParSis ZafParSis ) {
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
            vecCab.add(INT_TBL_NUMFAC,"Factura.");
	    vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
	    vecCab.add(INT_TBL_CODTIPDOC,"Cod.TipDoc.");
	    vecCab.add(INT_TBL_CODDOC,"Cod.Doc.");
            vecCab.add(INT_TBL_NUMDOCSOL,"Num.Doc.");
	    vecCab.add(INT_TBL_CODCLI,"Cod.Cli.");
            vecCab.add(INT_TBL_DESCLI,"Des.Cli");
	    vecCab.add(INT_TBL_NUMCOT,"Num.Cot.");
            
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
	    
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_NUMFAC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMDOCSOL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESCLI).setPreferredWidth(350);
            tcmAux.getColumn(INT_TBL_NUMCOT).setPreferredWidth(60);
               
            
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_CODDOC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
           
            Str_RegSel = new String[4];
            
             tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if(! ((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow() ==-1)) )
                        if(evt.getClickCount()==2){

                            Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC ).toString();
                            Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC ).toString();
                            Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC ).toString();
                            Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMCOT ).toString();
                            blnAcepta = true;
                            dispose();
                        }
                }
            });
            
           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
    
    


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butradgrp = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
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

        panTit.setPreferredSize(new java.awt.Dimension(100, 24));

        lblTit.setText("titulo");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

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

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panSubBot.add(butCon);

        butLim.setFont(new java.awt.Font("SansSerif", 0, 11));
        butLim.setText("Limpiar");
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panSubBot.add(butLim);

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11));
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

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

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-650)/2, (screenSize.height-400)/2, 650, 400);
    }// </editor-fold>//GEN-END:initComponents

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




 
private boolean consultarDat(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      vecData = new Vector();
          strSql="SELECT a.co_loc, a.co_tipdoc, a.co_doc, a.co_cli, a.tx_nomcli, a1.ne_numdoc as numfac, a1.ne_numcot, a.ne_numdoc  FROM tbm_cabsoldevven AS a " +
          " INNER JOIN tbm_cabmovinv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel ) " +
          " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" AND a.st_reg='A' AND a.st_aut='A' AND a.st_tipdev='C' " +
          " AND a.st_meraceingsis='S' AND a.st_volfacmersindev='S' AND a.st_mersindevfac='N' ";
      //System.out.println(""+strSql);  
      rstLoc=stmLoc.executeQuery(strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_NUMFAC, rstLoc.getString("numfac") );
	  vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
	  vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc") );
	  vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
          vecReg.add(INT_TBL_NUMDOCSOL, rstLoc.getString("ne_numdoc") );
	  vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
          vecReg.add(INT_TBL_DESCLI, rstLoc.getString("tx_nomcli") );
          vecReg.add(INT_TBL_NUMCOT, rstLoc.getString("ne_numcot") );
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
    
    






private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
    configurarFrm();
            
}//GEN-LAST:event_formWindowOpened

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
    // TODO add your handling code here:
     if(! ((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow() ==-1)) ){
        Str_RegSel[0] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC ).toString();
        Str_RegSel[1] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODTIPDOC ).toString();
        Str_RegSel[2] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC ).toString();
        Str_RegSel[3] = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_NUMCOT ).toString();
        blnAcepta = true;
    }
        dispose();
             
}//GEN-LAST:event_butAceActionPerformed

private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
    // TODO add your handling code here:
     objTblMod.removeAllRows();
     
}//GEN-LAST:event_butLimActionPerformed
   



       
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        blnAcepta=false;
        dispose();
    }
 }
    


 public boolean acepta(){
   return blnAcepta;
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLim;
    private javax.swing.ButtonGroup butradgrp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
     
}

/*
 * Created on 13 de agosto de 2008, 10:26
 */

package Compras.ZafCom50;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
/**
 *
 * @author  jayapata 
 */
public class ZafCom50_01 extends javax.swing.JInternalFrame {
     Librerias.ZafParSis.ZafParSis objZafParSis;
     private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
     ZafUtil objUti;
     private ZafThreadGUI objThrGUI;
     
    final int INT_TBL_LINEA   =0; 
    final int INT_TBL_CODITM  =1;
    final int INT_TBL_CODALT  =2;
    final int INT_TBL_NOMITM  =3;
    final int INT_TBL_UNIMED  =4;
    final int INT_TBL_CANTID  =5;
   
    Vector vecCab=new Vector(); 
    
    private ZafMouMotAda objMouMotAda;                 
    
    int CodEmp;
    int CodLoc;
    int CodTipDoc;
    int CodDoc;
    
    
    /** Creates new form ListadoSolDevVenAut */
    public ZafCom50_01(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc) {
       try{
	  this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	   initComponents();
	   
           CodEmp=intCodEmp;
           CodLoc=intCodLoc;
           CodTipDoc=intCodTipDoc;
           CodDoc=intCodDoc;
           
           this.setTitle(objZafParSis.getNombreMenu()+"  v0.1 ");
           //lblTit.setText(objZafParSis.getNombreMenu());
            
	    objUti = new ZafUtil();
	    
            configuraTbl();
            
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }   
         
            
            
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
	    vecCab.add(INT_TBL_CODITM,"Cod.Itm.");
	    vecCab.add(INT_TBL_CODALT,"Cod.Alt.");
	    vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
	    vecCab.add(INT_TBL_UNIMED,"Unidad.");
	    vecCab.add(INT_TBL_CANTID,"Cantidad.");
	    
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
            
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_UNIMED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CANTID).setPreferredWidth(60);
            
            objTblMod.setColumnDataType(INT_TBL_CANTID, objTblMod.INT_COL_DBL, new Integer(0), null);
           
             Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
             objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
             objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
             objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
             objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
             tcmAux.getColumn(INT_TBL_CANTID).setCellRenderer(objTblCelRenLbl);
             objTblCelRenLbl=null;

            
            
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODITM);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
           objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
          tcmAux=null; 
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

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panFilTabGen = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBot = new javax.swing.JPanel();
        panButPanBut = new javax.swing.JPanel();
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

        panTit.setFont(new java.awt.Font("SansSerif", 1, 12));
        panTit.setPreferredSize(new java.awt.Dimension(46, 24));

        lblTit.setText("Resumen de la solicitud de devolución de ventas.");
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

        tabGen.addTab("Filtro", panFilTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.BorderLayout());

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
        setBounds((screenSize.width-499)/2, (screenSize.height-339)/2, 499, 339);
    }// </editor-fold>//GEN-END:initComponents

    
    
private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);    

      consultarDat();
    
   
     
   objThrGUI=null;
}
}


    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_formInternalFrameClosing
    
private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
    System.gc();
    dispose();
}}

    
    
    
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

     if( (CodTipDoc==101) || (CodTipDoc==102) ) {
      strSql="select a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, (a1.nd_can-(a1.nd_cancon+a1.nd_cannunrec)) as canpenconf " +
      " from tbm_cabguirem as a " +
      " inner join tbm_detguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
      " where a.co_emp="+CodEmp+" and a.co_loc="+CodLoc+" and a.co_tipdoc="+CodTipDoc+" and a.co_doc="+CodDoc+" and nd_can > 0  and st_meregrfisbod='S' ";
     }else {
      strSql="select a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, (a1.nd_can-(a1.nd_cancon+a1.nd_cannunrec)) as canpenconf " +
      " from tbm_cabmovinv as a " +
      " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
      " where a.co_emp="+CodEmp+" and a.co_loc="+CodLoc+" and a.co_tipdoc="+CodTipDoc+" and a.co_doc="+CodDoc+" and nd_can > 0 and a1.st_meringegrfisbod='S' ";
     }


      System.out.println(" --> "+strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
	  vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
	  vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
	  vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
	  vecReg.add(INT_TBL_UNIMED, rstLoc.getString("tx_unimed") );
	  vecReg.add(INT_TBL_CANTID, rstLoc.getString("canpenconf") );
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
    
    
      
    
    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
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
    
    
    
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_CODITM:
            strMsg="Cóidigo item.";
            break;
        case INT_TBL_CODALT:
            strMsg="Cóidigo alterno del item.";
            break;
        case INT_TBL_NOMITM:
            strMsg="Nombre del item.";
            break;
        case INT_TBL_UNIMED:
            strMsg="Unidad de medida del item.";
            break;
        case INT_TBL_CANTID:
            strMsg="Cantidad.";
            break; 
       
        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}

    
    
}

/*
 * ZafVen25.java
 *
 * Created on 06 de Agosto de 2008 st_recMerDev
 */ 

package Maestros.ZafMae50;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/


/**
 *
 * @author  jayapata
 */ 
public class ZafMae50 extends javax.swing.JInternalFrame {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    mitoolbar objTooBar;
    ZafUtil objUti; /**/
    String strAux="";
    java.sql.ResultSet rstCab;
    java.sql.Statement STM_GLO;
    java.sql.Connection CONN_GLO=null;

    /** Creates new form ZafCom33 */
    public ZafMae50(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
	    
         this.setTitle(objZafParSis.getNombreMenu()+"  v 0.4 "); /**/
         lblTit.setText(objZafParSis.getNombreMenu());  /**/

         if( objZafParSis.getTipGrpClaInvPreUsr()=='P')
           radPub.setSelected(true);

         if( objZafParSis.getTipGrpClaInvPreUsr()=='R')
           radPri.setSelected(true);


	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");
            
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


    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpRdaBut = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblCodDoc1 = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        lblCodDoc2 = new javax.swing.JLabel();
        lblCodDoc4 = new javax.swing.JLabel();
        txtDesLar = new javax.swing.JTextField();
        txtDesCor = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        lbltipgrp = new javax.swing.JLabel();
        radPub = new javax.swing.JRadioButton();
        radPri = new javax.swing.JRadioButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(null);

        lblObs1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación:"); // NOI18N
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panGenTabGen.add(lblObs1);
        lblObs1.setBounds(10, 110, 92, 20);

        lblCodDoc1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc1.setText("Código:"); // NOI18N
        panGenTabGen.add(lblCodDoc1);
        lblCodDoc1.setBounds(10, 20, 60, 20);

        txtCod.setBackground(objZafParSis.getColorCamposSistema());
        panGenTabGen.add(txtCod);
        txtCod.setBounds(110, 20, 90, 20);

        lblCodDoc2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc2.setText("Descripción Larga:"); // NOI18N
        panGenTabGen.add(lblCodDoc2);
        lblCodDoc2.setBounds(10, 60, 100, 20);

        lblCodDoc4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodDoc4.setText("Descripción Corta:"); // NOI18N
        panGenTabGen.add(lblCodDoc4);
        lblCodDoc4.setBounds(10, 40, 120, 20);

        txtDesLar.setBackground( objZafParSis.getColorCamposObligatorios());
        panGenTabGen.add(txtDesLar);
        txtDesLar.setBounds(110, 62, 400, 20);
        panGenTabGen.add(txtDesCor);
        txtDesCor.setBounds(110, 41, 90, 20);

        txtObs.setLineWrap(true);
        jScrollPane5.setViewportView(txtObs);

        panGenTabGen.add(jScrollPane5);
        jScrollPane5.setBounds(110, 110, 400, 44);

        lbltipgrp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lbltipgrp.setText("Tipo de grupo:");
        panGenTabGen.add(lbltipgrp);
        lbltipgrp.setBounds(10, 80, 100, 20);

        grpRdaBut.add(radPub);
        radPub.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPub.setText("Público");
        panGenTabGen.add(radPub);
        radPub.setBounds(110, 85, 70, 20);

        grpRdaBut.add(radPri);
        radPri.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPri.setText("Privado");
        panGenTabGen.add(radPri);
        radPri.setBounds(180, 85, 110, 20);

        tabGen.addTab("General", panGenTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

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



private void bloquea(javax.swing.JTextField txtFiel,  boolean blnEst){
    java.awt.Color colBack = txtFiel.getBackground();
    txtFiel.setEditable(blnEst);
    txtFiel.setBackground(colBack);
}



    
  
    
    
 
     
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grpRdaBut;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblCodDoc1;
    private javax.swing.JLabel lblCodDoc2;
    private javax.swing.JLabel lblCodDoc4;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lbltipgrp;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JRadioButton radPri;
    private javax.swing.JRadioButton radPub;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtDesCor;
    private javax.swing.JTextField txtDesLar;
    private javax.swing.JTextArea txtObs;
    // End of variables declaration//GEN-END:variables
    
      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
      
      

public class mitoolbar extends ZafToolBar{
 public mitoolbar(javax.swing.JInternalFrame jfrThis){
    super(jfrThis, objZafParSis);
 }

	
public boolean anular() {
  boolean blnRes=false;
  java.sql.Connection  conn;
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       
        strAux=objTooBar.getEstadoRegistro();
        if (strAux.equals("Eliminado")) {
            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
            blnRes=true;
        }
        if (strAux.equals("Anulado")) {
            MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
            blnRes=true;
        }
        

        if(anularReg(conn)){
            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Anulado");
            
        }else conn.rollback();
        
      conn.close();
      conn=null;
   }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

	
        
        
private boolean anularReg(java.sql.Connection conn){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmEmp;
  java.sql.ResultSet rstEmp;
  String strSql=""; 
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();
       
    stmEmp=conn.createStatement();

    strSql="SELECT co_emp FROM tbm_emp   ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){

        strSql="UPDATE tbm_grpclainv SET st_regrep='M', st_reg='I', fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usrmod="+objZafParSis.getCodigoUsuario()+" " +
        " WHERE co_emp="+rstEmp.getInt("co_emp")+" AND co_grp="+txtCod.getText()+" ";
        stmLoc.executeUpdate(strSql);
    }
    rstEmp.close();
    rstEmp=null;

    stmLoc.close();
    stmLoc=null;
    stmEmp.close();
    stmEmp=null;

     blnRes=true;
  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

        
     
	
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }
        
	
	
public void clickAnterior() {
 try{
  if(rstCab != null ) {
     abrirCon();
     if(!rstCab.isFirst()) {
        
               rstCab.previous();
               refrescaDatos(CONN_GLO, rstCab);
       }
     CerrarCon();
  }} catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}
        


        
        public void clickAnular() {

        }
        
        
        public void clickConsultar() {
            clnTextos();
         
	}
        
   
        
        
        
public void clickFin(){
 try{
    if(rstCab != null ){
     abrirCon();
      if(!rstCab.isLast()){
            rstCab.last();
            refrescaDatos(CONN_GLO, rstCab);
      }
    CerrarCon();
   }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}
        

 



public void clickInicio(){
 try{
   if(rstCab != null ){
     abrirCon();
     if(!rstCab.isFirst()) {
            rstCab.first();
            refrescaDatos(CONN_GLO, rstCab);
       }
    CerrarCon();
  }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e);  }
}




public void clickInsertar() {
 try{
    clnTextos();

    java.awt.Color colBack;
    colBack = txtCod.getBackground();
    txtCod.setEditable(false);
    txtCod.setBackground(colBack);
    colBack=null;

      if(rstCab!=null) {
          rstCab.close();
          rstCab=null;

          if(STM_GLO!=null){
            STM_GLO.close();
            STM_GLO=null;
          }
      }

   }catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
}

        
        
        
        public void setEstadoBotonMakeFac(){
            switch(getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }
          
        
        
public void clickSiguiente(){ 
 try{
   if(rstCab != null ){
      abrirCon();
      if(!rstCab.isLast()) {
               rstCab.next();
               refrescaDatos( CONN_GLO, rstCab);
       }
      CerrarCon();
  }}catch (Exception e) { objUti.mostrarMsgErr_F1(this, e); }
}

        


public void clickEliminar() {

}
      
       
        
        
public boolean eliminar() {
  boolean blnRes=false;
  java.sql.Connection  conn;
  try{
     conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);
       
        strAux=objTooBar.getEstadoRegistro();
        if (strAux.equals("Eliminado")) {
            MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
            blnRes=true;
        }
        
      if(!blnRes){ 
        if(eliminarReg(conn)){
            conn.commit();
            blnRes=true;
            objTooBar.setEstadoRegistro("Eliminado");
          
        }else conn.rollback();
       }else blnRes=false;
        
      conn.close();
      conn=null;
   }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

        
          
        
private boolean eliminarReg(java.sql.Connection conn){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmEmp;
  java.sql.ResultSet rstEmp;
  String strSql=""; 
  try{
     if(conn!=null){
        stmLoc=conn.createStatement();

         stmEmp=conn.createStatement();

    strSql="SELECT co_emp FROM tbm_emp   ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){

        strSql="UPDATE tbm_grpclainv SET st_regrep='M', st_reg='E', fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usrmod="+objZafParSis.getCodigoUsuario()+" " +
        " WHERE co_emp="+rstEmp.getInt("co_emp")+" AND co_grp="+txtCod.getText()+" ";
        stmLoc.executeUpdate(strSql);
    }
    rstEmp.close();
    rstEmp=null;
    
    stmLoc.close();
    stmLoc=null;
    stmEmp.close();
    stmEmp=null;

     blnRes=true;
  }}catch(java.sql.SQLException e)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

        
         
      
        

 private boolean validaCampos(){
  if(txtDesCor.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Descripción Corta >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtDesCor.requestFocus();
    return false;
    }

  if(txtDesLar.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo << Descripción Larga >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtDesLar.requestFocus();
    return false;
    }
                 
             
             
   return true;  
 }
        
 
 
 
 
        
        
public boolean insertar() {
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
    if(validaCampos()){
    
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
      conn.setAutoCommit(false);
          
         if(insertarReg(conn)){
            conn.commit();
            blnRes=true;
         }else conn.rollback();

       conn.close();
       conn=null;
     }
     
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


 
        
        
private boolean insertarReg(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmEmp;
 java.sql.ResultSet rstLoc, rstEmp;
 String strSql="";
 String strTipGrp="";
 int intCodReg=0;
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
        stmEmp=conn.createStatement();

        if(radPub.isSelected()) strTipGrp="P";
        if(radPri.isSelected()) strTipGrp="R";
   
    strSql="SELECT co_emp FROM tbm_emp   ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){

        strSql="SELECT CASE WHEN max(co_grp)+1 IS NULL THEN 1 ELSE max(co_grp)+1 END AS cogrp FROM tbm_grpClaInv WHERE co_emp="+rstEmp.getInt("co_emp");
        rstLoc=stmLoc.executeQuery(strSql);
        if(rstLoc.next()){
           intCodReg=rstLoc.getInt("cogrp");
        }
        rstLoc.close();
        rstLoc=null;
        
        strSql="INSERT INTO tbm_grpClaInv(co_emp, co_grp, tx_descor, tx_deslar, tx_obs1, st_reg, fe_ing, co_usring, st_regrep, tx_tipgrp ) " +
        " VALUES("+rstEmp.getInt("co_emp")+", "+intCodReg+", '"+txtDesCor.getText()+"', '"+txtDesLar.getText()+"', '"+txtObs.getText()+"'" +
        " ,'A', "+objZafParSis.getFuncionFechaHoraBaseDatos()+", "+objZafParSis.getCodigoUsuario()+", 'I', '"+strTipGrp+"' ) ";
        stmLoc.executeUpdate(strSql);

      if(radPri.isSelected()){
        strSql="INSERT INTO tbr_grpclainvusr(co_emp, co_grp, co_usr, st_regrep ) "+
        " VALUES("+rstEmp.getInt("co_emp")+", "+intCodReg+", "+objZafParSis.getCodigoUsuario()+", 'I' ) ";
        stmLoc.executeUpdate(strSql);
      }
    }
    rstEmp.close();
    rstEmp=null;

    stmLoc.close();
    stmLoc=null;
    stmEmp.close();
    stmEmp=null;
        
     blnRes=true;
     txtCod.setText(""+intCodReg);
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

        

    
       
   

public boolean modificar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
    
      strAux=objTooBar.getEstadoRegistro();
      if(strAux.equals("Eliminado")) {
          MensajeInf("El documento está ELIMINADO.\nNo es posible modifcar un documento eliminado.");
          return false;
      }
      if(strAux.equals("Anulado")) {
          MensajeInf("El documento ya está ANULADO.\nNo es posible modifcar un documento anulado.");
          return false;
      }

              
    if(validaCampos()){
    
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
      conn.setAutoCommit(false);
      
         if(modificarCab(conn)){
            conn.commit();
            blnRes=true;
        }else conn.rollback();
      
      
       conn.close();
       conn=null;
     }
     
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}








 
       
private boolean modificarCab(java.sql.Connection conn){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmEmp;
 java.sql.ResultSet rstEmp;
 String strSql="";
 try{
    if(conn!=null){
        stmLoc=conn.createStatement();
        stmEmp=conn.createStatement();
        
    strSql="SELECT co_emp FROM tbm_emp  ORDER BY co_emp";
    rstEmp=stmEmp.executeQuery(strSql);
    while(rstEmp.next()){

         strSql="UPDATE tbm_grpclainv SET  st_regrep='M', tx_descor='"+txtDesCor.getText()+"', tx_deslar='"+txtDesLar.getText()+"' " +
         " , tx_obs1='"+txtObs.getText()+"', fe_ultmod="+objZafParSis.getFuncionFechaHoraBaseDatos()+", co_usrmod="+objZafParSis.getCodigoUsuario()+" " +
         " WHERE co_emp="+rstEmp.getInt("co_emp")+" AND  co_grp="+txtCod.getText()+" ";
         stmLoc.executeUpdate(strSql);
    }
    rstEmp.close();
    rstEmp=null;

    stmLoc.close();
    stmLoc=null;
    stmEmp.close();
    stmEmp=null;

    blnRes=true;  
 }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

  
  


     private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }
    
   
      
        
 public void  clnTextos(){
      txtCod.setText("");
      txtDesCor.setText("");
      txtDesLar.setText("");
      txtObs.setText("");

 }

    
        
      
        public boolean cancelar() {
            boolean blnRes=true;
            
            try {
//                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m') {
//                        if (!isRegPro())
//                            return false;
//                    }
                if (rstCab!=null) {
                    rstCab.close();
                    rstCab=null;
                     if(STM_GLO!=null){
                        STM_GLO.close();
                        STM_GLO=null;
                      }
                }
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
             
            return blnRes;
        }
        
        
        
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterCancelar() {
            
            return true;
        }
        
        public boolean afterConsultar() {
          
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            this.setEstado('w');
         
            return true;
        }
        
        public boolean afterModificar() {
          
           this.setEstado('w');
           
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        
        
        
        
   
        
          /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
        
        
        
        
        
        
          /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro() {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
        
          
        
        
        
        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
           return _consultar(FilSql());
        }
        
        
        
          
        
private boolean _consultar(String strFil){
  boolean blnRes=false;
  String strSql="";
  String strTipGrp="";
   try{
     
        abrirCon();
        if(CONN_GLO!=null) {
            STM_GLO=CONN_GLO.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY );


          if(radPub.isSelected()) strTipGrp="P";
          if(radPri.isSelected()) strTipGrp="R";

           strSql="select a.co_emp, a.co_grp from tbr_grpclainvusr as a" +
           " inner join  tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp  ) " +
           " where a.co_emp="+objZafParSis.getCodigoEmpresa()+" and a1.st_reg NOT IN('E') and a.co_usr="+objZafParSis.getCodigoUsuario()+" " +
           " and a1.tx_tipgrp='"+strTipGrp+"'  "+strFil+" ORDER BY a.co_grp ";


           rstCab=STM_GLO.executeQuery(strSql);
           if(rstCab.next()){
              rstCab.last();
              setMenSis("Se encontraron " + rstCab.getRow() + " registros");
              refrescaDatos(CONN_GLO, rstCab);
              blnRes=true;
           }else{
                setMenSis("0 Registros encontrados");
                clnTextos();
           } 

      CerrarCon();
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}
    
   


private boolean refrescaDatos(java.sql.Connection conn, java.sql.ResultSet rstDatCla){
    boolean blnRes=false;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql="";
    try{
       if(conn!=null){
          stmLoc=conn.createStatement();

          strSql="SELECT co_grp, tx_descor, tx_deslar, tx_obs1, st_reg, tx_tipgrp  FROM tbm_grpclainv " +
          " WHERE co_emp="+rstDatCla.getString("co_emp")+" AND co_grp="+rstDatCla.getString("co_grp");
          rstLoc=stmLoc.executeQuery(strSql);
          if(rstLoc.next()){

            strAux=rstLoc.getString("st_reg");
            txtCod.setText(rstLoc.getString("co_grp"));
            txtDesCor.setText(rstLoc.getString("tx_descor"));
            txtDesLar.setText(rstLoc.getString("tx_deslar"));
            txtObs.setText(rstLoc.getString("tx_Obs1"));
        
            radPri.setSelected(false);
            radPub.setSelected(false);
            if(rstLoc.getString("tx_tipgrp").equals("R")) radPri.setSelected(true);
            if(rstLoc.getString("tx_tipgrp").equals("P")) radPub.setSelected(true);


           if(strAux.equals("A"))
             strAux="Activo";
           else if (strAux.equals("I"))
             strAux="Anulado";
           else if (strAux.equals("E"))
             strAux="Eliminado";
           else
               strAux="Otro";
            objTooBar.setEstadoRegistro(strAux);
  
          } 
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;

          int intPosRel=rstDatCla.getRow();
          rstDatCla.last();
          objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstDatCla.getRow());
          rstDatCla.absolute(intPosRel);
         
  }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  return blnRes;
}

      
          

private String FilSql() {
    String sqlFiltro = "";
    //Agregando filtro por Numero de Cotizacion

    if(!txtCod.getText().equals(""))
        sqlFiltro = sqlFiltro + " and a.co_grp="+txtCod.getText();
 

    return sqlFiltro ;
}

        
        
    

public void clickModificar(){
 
    java.awt.Color colBack;
    colBack = txtCod.getBackground();
    txtCod.setEditable(false);
    txtCod.setBackground(colBack);
    colBack=null;


 this.setEnabledConsultar(false);

}



        
        
        
        
       
       
        //******************************************************************************************************
        
        public boolean vistaPreliminar(){
           
            return true;
        }
        
        
        
        
        
        
        
        
        
        public boolean imprimir(){
            
            return true;
        }
        
        
        //******************************************************************************************************
        
        
        public void clickImprimir(){
        }
        public void clickVisPreliminar(){
        }
        
        public void clickCancelar(){
         
        }
        
        public void cierraConnections(){
            
        }
        
        
        public boolean beforeAceptar() {
            return true;
        }
        public boolean beforeAnular() {
            return true;
        }
        public boolean beforeCancelar() {
            return true;
        }
        public boolean beforeConsultar() {
            return true;
        }
        public boolean beforeEliminar() {
            return true;
        }
        public boolean beforeImprimir() {
          
            return true;
        }
        public boolean beforeInsertar() {
            return true;
        }
        public boolean beforeModificar() {
            return true;
        }
        public boolean beforeVistaPreliminar() {
       
            return true;
        }
        
      
        
        
    }
    
  
      
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
    
    
}

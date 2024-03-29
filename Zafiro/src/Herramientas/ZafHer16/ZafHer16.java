/*
 * ZafMae33.java
 *
 * Created on May 23, 2007, 8:51 AM
 */

package Herramientas.ZafHer16;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.SpinnerNumberModel;
/**
 *
 * @author  ilino
 */
public class ZafHer16 extends javax.swing.JInternalFrame {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    final int intJspValMin=0001;
    final int intJspValMax=9999;
    final int intJspValIni=2007;
    final int intJspValInc=1;
    private String strSQL;
    private ZafThreadGUI objThrGUI;
    private char chrTipCie='A';
    private java.util.Date datFecAux;
    private javax.swing.JOptionPane oppMsg;
    
    private int intAniAbr;
    private String strTipCie;
    private int intUltReg;
    
    /** Creates new form ZafMae34 */
    public ZafHer16(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            jspAni.setModel(new SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));

            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panCen = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        butPro = new javax.swing.JButton();
        lblAni = new javax.swing.JLabel();
        jspAni = new javax.swing.JSpinner();
        butCer = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setName("");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
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

        panCen.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("lblTit");
        jPanel1.add(lblTit, java.awt.BorderLayout.CENTER);

        panCen.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(null);

        butPro.setMnemonic('P');
        butPro.setText("Procesar");
        butPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProActionPerformed(evt);
            }
        });

        jPanel2.add(butPro);
        butPro.setBounds(70, 100, 100, 23);

        lblAni.setText("A\u00f1o:");
        jPanel2.add(lblAni);
        lblAni.setBounds(100, 20, 50, 18);

        jPanel2.add(jspAni);
        jspAni.setBounds(150, 20, 80, 18);

        butCer.setMnemonic('C');
        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        jPanel2.add(butCer);
        butCer.setBounds(180, 100, 100, 23);

        panCen.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-350)/2, (screenSize.height-200)/2, 350, 200);
    }//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }
    }//GEN-LAST:event_butCerActionPerformed

    private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
        // TODO add your handling code here:
        System.out.println("Se presiono el boton de Procesar");
        // TODO add your handling code here:
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
            }
            else{
                objThrGUI=null;
                objThrGUI=new ZafThreadGUI();
            }
            objThrGUI.start();
            jspAni.setEnabled(false);
    }//GEN-LAST:event_butProActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void exitForm(){
        dispose();
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butPro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jspAni;
    private javax.swing.JLabel lblAni;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCen;
    // End of variables declaration//GEN-END:variables

    private boolean configurarFrm(){
        boolean blnRes=true;
        lblTit.setText(objParSis.getNombreMenu() + "v0.1");
        this.setTitle(objParSis.getNombreMenu());
        return blnRes;
    }
    
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    private boolean beforeProcesar(){
        
        int intJspAni=intJspAni=Integer.parseInt(""+jspAni.getValue());
        if(intJspAni<2006){
            mostrarMsgInf("<HTML>No puede reaperturar años que sean menores al presente año</HTML>");
            return false;
        }
        
        existeAnioAbrir();
        if(  (intAniAbr==Integer.parseInt("" + jspAni.getValue()) ) &&  (strTipCie.toString().equals("M"))  ){
            mostrarMsgInf("<HTML>El año <FONT COLOR=\"blue\"> " + jspAni.getValue() + "</FONT> se encuentra abierto.<BR>No es posible abrir un año que ya está abierto.</HTML>");
            butPro.requestFocus();
            return false;
        }
        if(  intAniAbr==0 ){
            mostrarMsgInf("<HTML>El año <FONT COLOR=\"blue\"> " + jspAni.getValue() + "</FONT> no se ha cerrado, por tanto no es mecesario realizar el proceso de reapertura.</HTML>");
            butPro.requestFocus();
            return false;
        }        
        return true;
    }
    
    
    private void existeAnioAbrir(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="select ne_ani, tx_tipCie from tbm_cabciesis";
                strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and ne_ani=" + jspAni.getValue() + "";
                System.out.println("EN SI EXISTE AÑO: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intAniAbr=rst.getInt("ne_ani");
                    strTipCie=rst.getString("tx_tipCie");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }        
    }
    
     private class ZafThreadGUI extends Thread
    {
        public void run(){
            
            if (mostrarMsgConPro("<HTML>¿Está seguro que desea realizar esta operación?<br>Luego de realizar esta operación deberá reaperturar el/los meses que necesite corregir.</HTML>")==0){
                if( procesarCierre() ){
                    mostrarMsgInf("La operación de Reapertura Anual del Sistema se realizó con éxito!");
                }
                else{
                    mostrarMsgInf("La operación de Reapertura Anual del Sistema no se pudo realizar!");
                }
            }
            jspAni.setEnabled(true);
            intAniAbr=0;
            strTipCie="";
            intUltReg=0;
        }
    }    
    
    
    private boolean procesarCierre(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);            
            if(con!=null){
                //SI INGRESA ES PORQ EL AÑO SE ENCUENTRA ABIERTO
                if( !(  beforeProcesar() ) ){
                    blnRes=false;                    
                }
                else{
                    if(insertaHistoricoCabecera()){
                        if(insertaHistoricoDetalle()){
                            if(insertaHistoricoSaldos()){
                                if( modificarCabecera() ){
                                    if( eliminarDetalle() ){
                                        con.commit();
                                    }
                                    else{
                                        con.rollback();
                                        blnRes=false;
                                    }
                                }
                                else{
                                    con.rollback();
                                    blnRes=false;
                                }
                            }
                            else{
                                con.rollback();
                                blnRes=false;
                            }
                        }
                        else{
                            con.rollback();
                            blnRes=false;
                        }
                    }
                    else{
                        con.rollback();
                        blnRes=false;
                    }
                }
                con.close();
                con=null;
            }            
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }
        return blnRes;
    }
    
    //CAMBIA tbm_cabciesis
    private boolean modificarCabecera(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="UPDATE tbm_cabciesis";
                    strSQL+=" SET tx_tipCie='M'";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                    System.out.println("SQL DE MODIFICACION DE CABECERA: " + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;                    
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }
        return blnRes;        
    }
    
    
    private boolean eliminarDetalle(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="DELETE FROM tbm_detciesis";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                    System.out.println("SQL DE ELIMINACION DE DETALLE: " + strSQL);
                    stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }
        return blnRes;        
    }


    private int mostrarMsgConPro(String strMsg){
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    

    
    private boolean insertaHistoricoCabecera(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                strSQL="";
                strSQL+="SELECT MAX(a1.co_his)";
                strSQL+=" FROM tbh_cabciesis AS a1";
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                intUltReg=Integer.parseInt(""+intUltReg);
                intUltReg++;                

                stm=con.createStatement();
                strSQL="";
                strSQL+="INSERT INTO tbh_cabciesis(";
                strSQL+="co_emp, ne_ani, co_his, tx_tipcie, fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrhis";
                strSQL+=")";
                strSQL+="(";
                strSQL+=" SELECT co_emp, ne_ani, " + intUltReg + " as co_his, tx_tipcie, fe_ing, fe_ultmod, co_usring, co_usrmod, ";
                strSQL+="'"  + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' as fe_his,";
                strSQL+=""  + objParSis.getCodigoUsuario() + " as co_usrhis";
                strSQL+=" FROM tbm_cabciesis";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and ne_ani=" + jspAni.getValue() + "";
                strSQL+=" and tx_tipcie='A'";
                strSQL+=")";
                System.out.println("SQL DE INSERT DE HISTORICO DE CABECERA: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }            
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }
        return blnRes;        
    }    
    
    private boolean insertaHistoricoDetalle(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="INSERT INTO tbh_detciesis(";
                strSQL+="co_emp, ne_ani, ne_mes, co_his";
                strSQL+=")";
                strSQL+="(";
                strSQL+=" SELECT co_emp, ne_ani, ne_mes," + intUltReg + " as co_his";
                strSQL+=" FROM tbm_detciesis";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and ne_ani=" + jspAni.getValue() + "";
                strSQL+=")";
                System.out.println("SQL DE INSERT DE HISTORICO DE DETALLE: " + strSQL);
                stm.executeUpdate(strSQL);                    
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }
        return blnRes;        
    }
    
    private boolean insertaHistoricoSaldos(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="INSERT INTO tbh_salcta(";
                strSQL+="co_emp, co_cta, co_per, co_his, nd_salcta, fe_his, co_usrhis, tx_tipCie";
                strSQL+=")";
                strSQL+="(";
                strSQL+=" SELECT co_emp, co_cta, co_per, " + intUltReg + " as co_his, nd_salcta, ";
                strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' as fe_his,";
                strSQL+=""  + objParSis.getCodigoUsuario() + " as co_usrhis, 'A' as tx_tipCie";
                strSQL+=" FROM tbm_salcta";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and co_per between " + jspAni.getValue() +  "01 and " + jspAni.getValue() + "12";
                strSQL+=")";
                System.out.println("SQL DE INSERT DE HISTORICO DE SALDOS: " + strSQL);
                stm.executeUpdate(strSQL);                    
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }
        return blnRes;        
    }
    
}

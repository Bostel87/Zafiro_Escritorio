/*
 * ZafMae33.java
 *
 * Created on May 23, 2007, 8:51 AM
 */

package Contabilidad.ZafCon43;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.SpinnerNumberModel;
import java.util.ArrayList;
/**
 *
 * @author  ilino
 */
public class ZafCon43 extends javax.swing.JInternalFrame {
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
    
    ArrayList arlRegMesCie, arlDatMesCie;
    final int INT_ARL_ANI=0;
    final int INT_ARL_TIP_CIE=1;
    final int INT_ARL_MES=2;
    
    
    
    /** Creates new form ZafMae33 */
    public ZafCon43(ZafParSis obj) {
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
        strTipCie="";
        intAniAbr=0;
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
            mostrarMsgInf("<HTML>No puede cerrar años que sean menores al año 2006</HTML>");
            return false;
        }
        
        strSQL="";
        strSQL+="SELECT a1.ne_ani";
        strSQL+=" FROM tbm_cabEstFinPre AS a1 ";
        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
        strSQL+=" AND a1.ne_ani=" + jspAni.getValue() + "";
        if (objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
            mostrarMsgInf("<HTML>El año <FONT COLOR=\"blue\"> " + jspAni.getValue() + "</FONT> no ha sido presupuestado.<BR>No es posible cerrar un año no ingresado.</HTML>");
            return false;
        }
        
        existeAnioCerrar();
        if(    (intAniAbr==Integer.parseInt("" + jspAni.getValue()) )    &&   (strTipCie.toString().equals("A"))         ){
            mostrarMsgInf("<HTML>El año <FONT COLOR=\"blue\"> " + jspAni.getValue() + "</FONT> ya se encuentra cerrado.<BR>No es posible cerrar un año que ya está cerrado.</HTML>");
            butPro.requestFocus();
            return false;            
        }
        
        if(    (intAniAbr==Integer.parseInt("" + jspAni.getValue()) )    &&   (strTipCie.toString().equals("M"))         ){            
            return true;            
        }        
        
        return true;
    }
    
    
    private void existeAnioCerrar(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="select ne_ani, st_cie from tbm_cabEstFinPre";
                strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and ne_ani=" + jspAni.getValue() + "";
                System.out.println("SI EXISTE AÑO: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intAniAbr=rst.getInt("ne_ani");
                    strTipCie=rst.getString("st_cie");
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
    
    
    private boolean existeMesCerrar(){
        boolean blnRes=true;
        arlDatMesCie=new ArrayList();
        arlDatMesCie.clear();
        //ArrayList arlRegMesCie, arlDatMesCie;
        try{
            Connection conTmp;
            Statement stmTmp;
            ResultSet rstTmp;
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="SELECT a1.ne_ani, a1.tx_tipCie, a2.ne_mes FROM tbm_cabCieSis AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_detCieSis AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.ne_ani=a2.ne_ani ";
                strSQL+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and a1.ne_ani=" + jspAni.getValue() + "";
                System.out.println("SI EXISTE AÑO: " + strSQL);
                rstTmp=stmTmp.executeQuery(strSQL);
                while(rstTmp.next()){
                    arlRegMesCie=new ArrayList();
                    arlRegMesCie.add(INT_ARL_ANI, (rstTmp.getString("ne_ani")==null?"":rstTmp.getString("ne_ani")) );
                    arlRegMesCie.add(INT_ARL_TIP_CIE, (rstTmp.getString("tx_tipCie")==null?"":rstTmp.getString("tx_tipCie")) );
                    arlRegMesCie.add(INT_ARL_MES, (rstTmp.getString("ne_mes")==null?"":rstTmp.getString("ne_mes")) );
                    arlDatMesCie.add(arlRegMesCie);
                }
                conTmp.close();
                conTmp=null;
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;
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
    
    
    
     private class ZafThreadGUI extends Thread
    {
        public void run(){
            
            if (mostrarMsgConPro("¿Está seguro que desea realizar esta operación?")==0){
                if( procesarCierre() )
                    mostrarMsgInf("La operación de Cierre Anual del Sistema se realizó con éxito!");
                else
                    mostrarMsgInf("La operación de Cierre Anual del Sistema no se pudo realizar!");
            }
            jspAni.setEnabled(true);
            strTipCie="";
            intAniAbr=0;
        }
    }    
    
    
    private boolean procesarCierre(){
        boolean blnRes=true;
        intUltReg=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);            
            if(con!=null){
                //SI INGRESA ES PORQ NO EXISTE ESE AÑO CERRADO
                if( !(  beforeProcesar() ) ){
                    blnRes=false;
                }
                else{
                    if( modificaTbmCabEstFinPre() ){
                        con.commit();
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
    

    

    private int mostrarMsgConPro(String strMsg){
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    
    

    
    //INSERTA tbm_cabciesis
    private boolean modificaTbmCabEstFinPre(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                //SI INGRESA ES PORQ NO EXISTE ESE AÑO CERRADO
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="UPDATE tbm_cabEstFinPre";
                    strSQL+=" SET st_cie='A',";
                    strSQL+=" fe_ultmod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                    strSQL+=" co_usrmod=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                    System.out.println("SQL DE MODIFICA CABECERA: " + strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                    datFecAux=null;
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

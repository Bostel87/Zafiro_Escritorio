/*
 * ZafMae33.java
 *
 * Created on May 23, 2007, 8:51 AM
 */

package Contabilidad.ZafCon44;
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
public class ZafCon44 extends javax.swing.JInternalFrame {
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
    public ZafCon44(ZafParSis obj) {
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
            mostrarMsgInf("<HTML>No puede reaperturar años que sean menores al año 2006</HTML>");
            return false;
        }
        
        
        strSQL="";
        strSQL+="SELECT a1.ne_ani";
        strSQL+=" FROM tbm_cabEstFinPre AS a1 ";
        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
        strSQL+=" AND a1.ne_ani=" + jspAni.getValue() + "";
        if (objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
            mostrarMsgInf("<HTML>El año <FONT COLOR=\"blue\"> " + jspAni.getValue() + "</FONT> no ha sido presupuestado.<BR>No es posible reaperturar un año no ingresado.</HTML>");
            return false;
        }
        
        
        strSQL="";
        strSQL+="SELECT a1.st_cie";
        strSQL+=" FROM tbm_cabEstFinPre AS a1 ";
        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
        strSQL+=" AND a1.ne_ani=" + jspAni.getValue() + "";
        strSQL+=" AND a1.st_cie='O'";
        if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
            mostrarMsgInf("<HTML>El año seleccionado se encuentra abierto.<BR>No puede reaperturar un año que está abierto</HTML>");
            return false;
        }
        
        
        strSQL="";
        strSQL+="SELECT a1.st_cie";
        strSQL+=" FROM tbm_cabEstFinPre AS a1 ";
        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
        strSQL+=" AND a1.ne_ani=" + jspAni.getValue() + "";
        strSQL+=" AND a1.st_cie='M'";
        if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)){
            mostrarMsgInf("<HTML>El año seleccionado se encuentra abierto, pero tiene cierre mensual.<BR>No puede reaperturar un año que está abierto, reaperture por mes</HTML>");
            return false;
        }
        
        
        
        return true;
    }
    
    
     private class ZafThreadGUI extends Thread{
        public void run(){
            
            if (mostrarMsgConPro("<HTML>¿Está seguro que desea realizar esta operación?<br></HTML>")==0){
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
        

    private int mostrarMsgConPro(String strMsg){
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    

    

    private String extraeCampoCierreTbmCabEstFinPre(){
        String strTipCie="";
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="SELECT a1.st_cie";
                strSQL+=" FROM tbm_cabEstFinPre AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.ne_ani=" + jspAni.getValue() + "";
                rstTmp=stmTmp.executeQuery(strSQL);
                if(rstTmp.next()){
                    strTipCie=rstTmp.getString("st_cie");
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
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strTipCie;
    }
    
    
    private String existeCierreTbmCieMenEstFinPre(){
        String strTipCie="";
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="SELECT a1.ne_ani";
                strSQL+=" FROM tbm_cieMenEstFinPre AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.ne_ani=" + jspAni.getValue() + "";
                strSQL+=" GROUP BY a1.ne_ani";
                rstTmp=stmTmp.executeQuery(strSQL);
                if(rstTmp.next()){
                    strTipCie=rstTmp.getString("ne_ani");
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
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strTipCie;
    }
    
    
    
    

    private boolean procesarCierre(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                //SI INGRESA ES PORQ EL AÑO SE ENCUENTRA ABIERTO
                if( !beforeProcesar() ){
                    blnRes=false;                    
                }
                else{
                    if(updateTbmCabEstFinPre()){
                        if(insertaTbhCabEstFinPre()){
                            if(insertaTbhDetEstFinPre()){
                                if( insertaTbhDetCtaEstFinPre() ){
                                    con.commit();
                                    blnRes=true;
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
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
    
    private boolean updateTbmCabEstFinPre(){
        boolean blnRes=true;
        String strAniExi="", strTipCieSav="";
        strAniExi=existeCierreTbmCieMenEstFinPre();
        if(strAniExi.toString().equals(""))
            strTipCieSav="O";
        else
            strTipCieSav="M";

        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabestfinpre";
                strSQL+=" SET st_cie='" + strTipCieSav + "',";
                strSQL+=" fe_ultmod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+=" co_usrmod=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
                System.out.println("SQL DE UPDATE EN Tbm_cabEstFinPre: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean insertaTbhCabEstFinPre(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            strSQL="";
            strSQL+="SELECT MAX(a1.co_his)";
            strSQL+=" FROM tbh_cabestfinpre AS a1";
            intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
            intUltReg=Integer.parseInt(""+intUltReg);
            intUltReg++;
            stm=con.createStatement();
            strSQL="";
            strSQL+="INSERT INTO tbh_cabestfinpre(";
            strSQL+=" co_emp, ne_ani, co_his, tx_obs1, st_reg, st_cie, fe_ing, fe_ultmod,";
            strSQL+=" co_usring, co_usrmod, fe_his, co_usrhis)";
            strSQL+=" (";
            strSQL+=" SELECT co_emp, ne_ani, " + intUltReg + " as co_his, tx_obs1, st_reg, st_cie, fe_ing, fe_ultmod, co_usring, co_usrmod,";
            strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' as fe_his,";
            strSQL+=""  + objParSis.getCodigoUsuario() + " as co_usrhis";
            strSQL+=" FROM tbm_cabEstFinPre";
            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" and ne_ani=" + jspAni.getValue() + "";
            strSQL+=" AND st_cie<>'A'";
            strSQL+=")";
            System.out.println("SQL DE insertaTbhCabEstFinPre: " + strSQL);
            stm.executeUpdate(strSQL);
            datFecAux=null;
            stm.close();
            stm=null;
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }        
        return blnRes;
    }
        
    private boolean insertaTbhDetEstFinPre(){
        boolean blnRes=true;        
        try{
            stm=con.createStatement();
            strSQL="";
            strSQL+="INSERT INTO tbh_detestfinpre(";
            strSQL+=" co_emp, ne_ani, co_his, co_cta, ne_mes, nd_val, tx_obs1, fe_ultmod,";
            strSQL+=" co_usrmod)";
            strSQL+=" (";
            strSQL+=" SELECT co_emp, ne_ani, " + intUltReg + " as co_his, co_cta, ne_mes, nd_val, tx_obs1, fe_ultmod, co_usrmod";
            strSQL+=" FROM tbm_detestfinpre";
            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
            strSQL+=")";
            System.out.println("SQL DE insertaTbhDetEstFinPre: " + strSQL);
            stm.executeUpdate(strSQL);
            stm.close();
            stm=null;
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }        
        return blnRes;
    }

    private boolean insertaTbhDetCtaEstFinPre(){
        boolean blnRes=true;
        try{
            stm=con.createStatement();
            strSQL="";
            strSQL+="INSERT INTO tbh_detctaestfinpre(";
            strSQL+=" co_emp, ne_ani, co_his, co_cta, ne_mes, co_reg, tx_nom, nd_val,";
            strSQL+=" fe_ultmod, co_usrmod)";
            strSQL+=" (";
            strSQL+=" SELECT co_emp, ne_ani, " + intUltReg + " as co_his, co_cta, ne_mes, co_reg, tx_nom, nd_val, fe_ultmod, co_usrmod";
            strSQL+=" FROM tbm_detctaestfinpre";
            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND ne_ani=" + jspAni.getValue() + "";
            strSQL+=")";
            System.out.println("SQL DE insertaTbhDetCtaEstFinPre: " + strSQL);
            stm.executeUpdate(strSQL);
            stm.close();
            stm=null;
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }        
        return blnRes;
    }
    
    
    
    
    
    
    
    
    
    
    
}
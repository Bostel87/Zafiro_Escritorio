/*
 * ZafMae33.java
 *
 * Created on May 23, 2007, 8:51 AM
 */

package Herramientas.ZafHer21;
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
public class ZafHer21 extends javax.swing.JInternalFrame {
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
    private ArrayList arlReg, arlDat;
    final int INT_ARL_ANI=0;
    
    
    /** Creates new form ZafMae33 */
    public ZafHer21(ZafParSis obj) {
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
        catch (CloneNotSupportedException e){
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
        strMsg="aEsta seguro que desea cerrar este programa?";
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
        strMsg="aEsta seguro que desea cerrar este programa?";
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
        lblTit.setText(objParSis.getNombreMenu() + "v0.1.3");
        this.setTitle(objParSis.getNombreMenu());
        arlDat=new ArrayList();
        arlDat.clear();
        return blnRes;
    }
    
    
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    
    
    
    private boolean beforeProcesar(){
        int intArlAni=0;
        int intJspAni=intJspAni=Integer.parseInt(""+jspAni.getValue());
        
        if(intJspAni<2007){
            mostrarMsgInf("<HTML>No puede iniciar años que sean menores al presente año</HTML>");
            return false;
        }
        
        if( ! (existeAnioInicia())  ){
            return false;
        }
        else{
            for(int i=0; i<arlDat.size(); i++){
                intArlAni=objUti.getIntValueAt(arlDat, i, INT_ARL_ANI);
                if(intJspAni==intArlAni){
                    mostrarMsgInf("<HTML>El año que desea iniciar ya fue procesado</HTML>");
                    return false;
                }
            }
        }

        
        return true;
    }
    
    
    
     private class ZafThreadGUI extends Thread{
        public void run(){
            if (mostrarMsgConPro("aEsta seguro que desea realizar esta operacian?")==0){
                if( procesaDatos() ){
                }
            }
            jspAni.setEnabled(true);
            arlDat.clear();
        }
    }
    
    
    private boolean procesaDatos(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if( (beforeProcesar())  ){
                    if(insertaTbmAniProSis()){
                        if(insertaCuentasTbmSalCta()){
                            mostrarMsgInf("La operación de inicio de año se realiza con éxito!");
                            con.commit();
                        }
                        else{
                            mostrarMsgInf("La operacian de inicio de año no se pudo realizar!");
                            con.rollback();
                        }
                    }
                    else{
                        mostrarMsgInf("La operacian de inicio de año no se pudo realizar!");
                        con.rollback();
                    }
                }
                else{
                    mostrarMsgInf("La operacian de inicio de año no se pudo realizar!");
                    con.rollback();
                }
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
     
     
     
    private boolean existeAnioInicia(){
        boolean blnRes=true;
        arlDat.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="select ne_ani from tbm_aniCreSis where co_emp=" + objParSis.getCodigoEmpresa() + "";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlReg=new ArrayList();
                    arlReg.add(INT_ARL_ANI, "" + rst.getInt("ne_ani"));
                    arlDat.add(arlReg);
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
        return blnRes;
    }
     
 
    //INSERTA tbm_cabciesis
    private boolean insertaTbmAniProSis(){
        boolean blnRes=true;
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        try{
            if(con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="INSERT INTO tbm_aniCreSis(";
                    strSQL+="co_emp, ne_ani, fe_ing, co_usring";
                    strSQL+=")";
                    strSQL+=" VALUES ( " + objParSis.getCodigoEmpresa() + ",";
                    strSQL+=""  + jspAni.getValue() + ",";
                    strSQL+="'"  + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                    strSQL+=""  + objParSis.getCodigoUsuario() + "";
                    strSQL+=")";
                    System.out.println("SQL DE insertaTbmAniProSis: " + strSQL);
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

    
    private boolean insertaCuentasTbmSalCta(){
        boolean blnRes=true;
        int intJspAni=Integer.parseInt(""+jspAni.getValue());
        String strMes="";
        try{
            if(con!=null){
                for(int i=1; i<=12; i++){
                    strMes=(i<=9?"0"+i:""+i);
                    stm=con.createStatement();
                    strSQL="";
                    //Cuentas normales
                    strSQL+="INSERT INTO tbm_salcta(";
                    strSQL+=" co_emp, co_cta, co_per, nd_salcta)";
                    strSQL+=" ( SELECT co_emp, co_cta, " + intJspAni + "" + strMes + " as co_per,";
                    strSQL+="   0.00 as nd_salcta";
                    strSQL+="   FROM tbm_placta";
                    strSQL+="   WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and st_reg='A'";
                    strSQL+="   ORDER BY co_cta";
                    strSQL+=" );";
                    //Cuentas de Efectivo
                    strSQL+="INSERT INTO tbm_salCtaEfe(";
                    strSQL+=" co_emp, co_cta, co_per, nd_salcta)";
                    strSQL+=" ( SELECT co_emp, co_cta, " + intJspAni + "" + strMes + " as co_per,";
                    strSQL+="   0.00 as nd_salcta";
                    strSQL+="   FROM tbm_placta";
                    strSQL+="   WHERE co_emp=" + objParSis.getCodigoEmpresa() + " and st_reg='A'";
                    strSQL+=");";
                    //Cuentas de Formato de Mercado de Valores
                    strSQL+="INSERT INTO tbm_salCtaForPlaCta(";
                    strSQL+=" co_emp, co_for, co_cta, co_per, nd_salcta)";
                    strSQL+=" ( SELECT a1.co_emp, a1.co_for, co_cta, " + intJspAni + "" + strMes + " as co_per,";
                    strSQL+="   0.00 as nd_salcta";
                    strSQL+="   FROM tbm_cabForPlaCta AS a1 INNER JOIN tbm_detForPlaCta AS a2";
                    strSQL+="   ON a1.co_emp=a2.co_emp AND a1.co_for=a2.co_for";
                    strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.st_reg='A'";
                    strSQL+=");";
                    System.out.println("SQL DE insertaCuentasTbmSalCta: " + strSQL);
                    stm.executeUpdate(strSQL);
                }
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

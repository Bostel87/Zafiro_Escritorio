/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenFacAut;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;

/**
 *
 * @author Sistemas6
 */
public class ZafCostearDocumento extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSql;

    /**
     * Creates new form ZafCostearDocumento
     */
    public ZafCostearDocumento() {
        initComponents();
    }
    
    public ZafCostearDocumento(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            initComponents();
        }
        catch (CloneNotSupportedException e){      
            this.setTitle(this.getTitle() + " [ERROR]");      
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCodEmp = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCostear = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Costear Documento");

        jLabel1.setText("co_emp");

        jLabel2.setText("co_loc");

        jLabel3.setText("co_tipDoc");

        jLabel4.setText("co_doc");

        btnCostear.setText("Costear");
        btnCostear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCostearActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodDoc)
                            .addComponent(txtCodTipDoc)
                            .addComponent(txtCodLoc)
                            .addComponent(txtCodEmp)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCostear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(btnSalir)))
                .addGap(221, 221, 221))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodTipDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCostear)
                    .addComponent(btnSalir))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCostearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCostearActionPerformed
        // TODO add your handling code here:
        iniciaCostearDocumento();
    }//GEN-LAST:event_btnCostearActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCostear;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    // End of variables declaration//GEN-END:variables


    /**
     * 
     * @return 
     */
    
    private boolean iniciaCostearDocumento(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            if(txtCodEmp.getText().length()>0 && txtCodLoc.getText().length()>0 && txtCodTipDoc.getText().length()>0 && txtCodDoc.getText().length()>0 ){
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                if(conLoc!=null){
                    conLoc.setAutoCommit(false);
                    if(existe(conLoc, Integer.parseInt(txtCodEmp.getText()), Integer.parseInt(txtCodLoc.getText()),Integer.parseInt(txtCodTipDoc.getText()),Integer.parseInt(txtCodDoc.getText()) )){
                        if(objUti.costearDocumento(this, objParSis, conLoc, Integer.parseInt(txtCodEmp.getText()), Integer.parseInt(txtCodLoc.getText()), 
                                                                            Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                            blnRes=true;
                            conLoc.commit();
                            System.out.println("OK OK OK OK ");
                        }
                        else{
                            conLoc.rollback();
                        }
                    }
                    conLoc.close();
                    conLoc=null;
                }
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);      
        }  
        return blnRes;
    }
    
    /**
     * Verifica si existe el documento a costear 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    
    private boolean existe(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc ){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT co_emp, co_loc, co_tipDoc, co_doc FROM tbm_cabMovInv  ";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);      
        }  
        return blnRes;
    }

}

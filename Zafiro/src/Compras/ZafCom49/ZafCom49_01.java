/*
 *
 * Created on 9 de marzo de 2006, 9:12
 *
 * v0.1 
 */

package Compras.ZafCom49;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
/**
 *
 * @author  Javier Ayapata
 */

public class ZafCom49_01 extends javax.swing.JDialog {
    //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strObsIni, strObsCam;
    private String strPanEdi;

    
      
     public ZafCom49_01(java.awt.Frame parent, boolean modal, ZafParSis obj, String editable) {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        strPanEdi=editable;
        if(strPanEdi.equals("N")){
            txtPanObs.setEditable(false);
            butCan.setVisible(false);
        }
        else if(strPanEdi.equals("S")){
            txtPanObs.setEditable(true);
            butCan.setVisible(true);
        }
        else{
            txtPanObs.setEditable(false);
            butCan.setVisible(false);
        }

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panObs = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        txtPanObs = new javax.swing.JTextPane();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panBotExe = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        panGen.setLayout(new java.awt.BorderLayout());

        panObs.setPreferredSize(new java.awt.Dimension(9, 120));
        panObs.setLayout(new java.awt.BorderLayout());

        txtPanObs.setEditable(false);
        spnDat.setViewportView(txtPanObs);

        panObs.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panObs, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butAce.setMnemonic('A');
        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBotExe.add(butAce);

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBotExe.add(butCan);

        panBot.add(panBotExe, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        txtPanObs.setText("");
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:
        if(strPanEdi.equals("S")){
            strObsCam=txtPanObs.getText()==null?"":txtPanObs.getText();//se cambia la observación inicial por la nueva ingresada por el usuario
        }
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        if(strPanEdi.equals("S")){
            strObsCam=strObsIni;//no se cambia la observación inicial
            if( ! strObsIni.equals(txtPanObs.getText())){
                String strTit, strMsg;
                javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                strTit="Mensaje del sistema Zafiro";
                strMsg="<HTML>¿Está seguro que desea cerrar la ventana? <BR>Se perderán los datos que no haya guardado.</HTML>";
                if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                {
                    dispose();
                }
            }
            else
                dispose();
        }

    }//GEN-LAST:event_butCanActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBotExe;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextPane txtPanObs;
    // End of variables declaration//GEN-END:variables
       

    
    /** Cerrar la aplicaci�n. */
    private void exitForm(){
        dispose();
    }    
    

    public void setContenido(String observacion){
        txtPanObs.setText(observacion);
        strObsIni=observacion;
    }
    
    public String getContenido(){
        return strObsCam;
    }
}

/*
 *
 * Created on 03 de septiembre de 2015, 9:12
 *
 * v0.1 
 */

package RecursosHumanos.ZafRecHum88;
/**
 *
 * @author Tony Sanginez
 */
public class ZafRecHum88_01 extends javax.swing.JDialog {
    //Variable
    boolean blnRes=false;   
    
    public ZafRecHum88_01(java.awt.Frame parent, boolean modal, String strObs,boolean blnActButAce) {
        super(parent, modal);
        initComponents();
        if (!blnActButAce) {
            btnAce.setEnabled(false);
        }else{
            btnAce.setEnabled(true);
        }
        //Inicializar objetos.
        txaObs.setText(strObs);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCon1 = new javax.swing.JPanel();
        panCon2 = new javax.swing.JPanel();
        spnTxtObs = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        panCon3 = new javax.swing.JPanel();
        panCon4 = new javax.swing.JPanel();
        btnAce = new javax.swing.JButton();
        btnCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panCon1.setLayout(new java.awt.BorderLayout());

        panCon2.setLayout(new java.awt.BorderLayout());

        txaObs.setColumns(20);
        txaObs.setRows(5);
        spnTxtObs.setViewportView(txaObs);

        panCon2.add(spnTxtObs, java.awt.BorderLayout.CENTER);

        panCon1.add(panCon2, java.awt.BorderLayout.CENTER);

        panCon3.setLayout(new java.awt.BorderLayout());

        panCon4.setLayout(new java.awt.GridLayout(1, 0));

        btnAce.setText("Aceptar");
        btnAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceActionPerformed(evt);
            }
        });
        panCon4.add(btnAce);

        btnCan.setText("Cancelar");
        btnCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanActionPerformed(evt);
            }
        });
        panCon4.add(btnCan);

        panCon3.add(panCon4, java.awt.BorderLayout.PAGE_END);

        panCon1.add(panCon3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(panCon1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(301, 206));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       blnRes=false; 
       dispose();
    }//GEN-LAST:event_formWindowClosing

    private void btnAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceActionPerformed
        blnRes=true;
        dispose();
    }//GEN-LAST:event_btnAceActionPerformed

    private void btnCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanActionPerformed
       blnRes=false; 
       this.dispose();
    }//GEN-LAST:event_btnCanActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAce;
    private javax.swing.JButton btnCan;
    private javax.swing.JPanel panCon1;
    private javax.swing.JPanel panCon2;
    private javax.swing.JPanel panCon3;
    private javax.swing.JPanel panCon4;
    private javax.swing.JScrollPane spnTxtObs;
    private javax.swing.JTextArea txaObs;
    // End of variables declaration//GEN-END:variables
       
    /** Cerrar la aplicaci�n. */
    private void exitForm(){
        dispose();
    }    
    
     /**
     * @param args the command line arguments
     */
  
    public String getObser(){
       return  txaObs.getText().trim();
    }
    
    public boolean getAceptar(){
       return  blnRes;
    } 
}

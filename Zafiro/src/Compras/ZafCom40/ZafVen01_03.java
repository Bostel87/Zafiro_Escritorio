/*
 * ZafVen01_03.java
 *
 * Created on 30 de marzo de 2006, 11:30
 */

package Compras.ZafCom40;

import Librerias.ZafParSis.ZafParSis;
/**
 *  Ver 1.0
 * @author  jayapata
 */
public class ZafVen01_03 extends javax.swing.JDialog {
     private ZafParSis objParSis;
    /** Creates new form ZafVen01_03 */
    
     public boolean blnAcepta = false;
     public boolean blnEst=false;
      
     public ZafVen01_03(java.awt.Frame parent, boolean modal, ZafParSis obj) {
        super(parent, modal);
        initComponents();

         objParSis=obj;
    
        txtCod.setBackground(objParSis.getColorCamposObligatorios());
        txtDes.setBackground(objParSis.getColorCamposObligatorios());
        
            
            String  strAux=objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        lblForRet = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        txtDes = new javax.swing.JTextField();
        butForret = new javax.swing.JButton();
        lblvehRet = new javax.swing.JLabel();
        txtVehRet = new javax.swing.JTextField();
        txtChoRet = new javax.swing.JTextField();
        lblChoret = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAcep = new javax.swing.JButton();
        butAcep1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                CerrarFormaRetiro(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(null);

        lblForRet.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblForRet.setText("Forma de Retiro:");
        panGenDet.add(lblForRet);
        lblForRet.setBounds(20, 20, 100, 15);

        txtCod.setEditable(false);
        txtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodActionPerformed(evt);
            }
        });

        panGenDet.add(txtCod);
        txtCod.setBounds(130, 20, 40, 21);

        txtDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesActionPerformed(evt);
            }
        });

        panGenDet.add(txtDes);
        txtDes.setBounds(170, 20, 180, 21);

        butForret.setText("...");
        butForret.setPreferredSize(new java.awt.Dimension(35, 30));
        butForret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForretActionPerformed(evt);
            }
        });

        panGenDet.add(butForret);
        butForret.setBounds(350, 20, 22, 20);

        lblvehRet.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblvehRet.setText("Vehiculo de Retiro:");
        panGenDet.add(lblvehRet);
        lblvehRet.setBounds(20, 45, 100, 15);

        panGenDet.add(txtVehRet);
        txtVehRet.setBounds(130, 45, 220, 21);

        panGenDet.add(txtChoRet);
        txtChoRet.setBounds(130, 70, 220, 21);

        lblChoret.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblChoret.setText("Chofer de Retiro:");
        panGenDet.add(lblChoret);
        lblChoret.setBounds(20, 75, 100, 15);

        jPanel1.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Forma de Retiro", jPanel1);

        PanFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAcep.setText("Aceptar");
        butAcep.setPreferredSize(new java.awt.Dimension(92, 25));
        butAcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAcepActionPerformed(evt);
            }
        });

        panBot.add(butAcep);

        butAcep1.setText("Cancelar");
        butAcep1.setPreferredSize(new java.awt.Dimension(92, 25));
        butAcep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAcep1ActionPerformed(evt);
            }
        });

        panBot.add(butAcep1);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-400)/2, 400, 400);
    }//GEN-END:initComponents

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodActionPerformed

    private void txtDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesActionPerformed

    private void butAcep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAcep1ActionPerformed
        // TODO add your handling code here:
        
        blnEst=false;
        salir();
    }//GEN-LAST:event_butAcep1ActionPerformed

    private void butAcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAcepActionPerformed
        // TODO add your handling code here:
         boolean a = ValidaFormaRet();
                  if(a == true){  blnEst=true; salir(); }

        
    }//GEN-LAST:event_butAcepActionPerformed

    private void butForretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForretActionPerformed
        // TODO add your handling code here:
         Compras.ZafCom40.ZafVen01_02 obj = new  Compras.ZafCom40.ZafVen01_02(javax.swing.JOptionPane.getFrameForComponent(this), true,  objParSis);
         obj.show();  
           
           if(obj.acepta()){ 
               txtCod.setText(obj.GetCamSel(1));
               txtDes.setText(obj.GetCamSel(2));
         
           }     
    }//GEN-LAST:event_butForretActionPerformed

    private void CerrarFormaRetiro(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_CerrarFormaRetiro
        // TODO add your handling code here:
       
//         boolean a = ValidaFormaRet();
//                  if(a == true)
//                       if(a == true) {  blnEst=true; salir(); }
                    
                      
          blnEst=false;
          salir();
                
    }//GEN-LAST:event_CerrarFormaRetiro
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    
    private void salir(){
              Str_RegSel = new String[4];
                      
               Str_RegSel[0] = txtCod.getText();
               Str_RegSel[1] = txtDes.getText();
               Str_RegSel[2] = txtVehRet.getText();
               Str_RegSel[3] = txtChoRet.getText();
                      
               blnAcepta = true;
                      
         dispose();
    }
    
    
     public boolean acepta(){
        return blnEst;
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
    
    
    private void MensajeValidaCampo(String strNomCampo){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    obj.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
    
       public boolean ValidaFormaRet(){
             if( txtCod.getText().equals("") ){
                   MensajeValidaCampo("Forma de Retiro");
                   txtCod.requestFocus();
                   return false;
              }
////              if( txtVehRet.getText().equals("") ){
////                   tabCotCom.setSelectedIndex(2);                  
////                   MensajeValidaCampo("Vehiculo de Retiro");
////                   txtVehRet.requestFocus();
////                   return false;
////              }
////              if( txtChoRet.getText().equals("") ){
////                   tabCotCom.setSelectedIndex(2);                  
////                   MensajeValidaCampo("Chofer de Retiro");
////                   txtChoRet.requestFocus();
////                   return false;
////              }
             return true;
        }       
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAcep;
    private javax.swing.JButton butAcep1;
    private javax.swing.JButton butForret;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblChoret;
    private javax.swing.JLabel lblForRet;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblvehRet;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtChoRet;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtDes;
    private javax.swing.JTextField txtVehRet;
    // End of variables declaration//GEN-END:variables
    private String Str_RegSel[];
}

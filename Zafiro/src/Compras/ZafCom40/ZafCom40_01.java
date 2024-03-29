/*
 *
 *
 * v0.1 
 */

package Compras.ZafCom40;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import javax.swing.JTextField;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom40_01 extends javax.swing.JDialog {
    private ZafParSis objParSis;
    private java.awt.Container cnt01;
    private int intPro;// valor de 0 si no se realiza nada, valor de 1 si se procesa algo.
    private ZafUtil objUti;
//    private ArrayList arlDat;
    private int intTipCot;
    private String strSQL;
    
    
     //public ZafCom40_01(java.awt.Frame parent, boolean modal, ZafParSis obj, java.awt.Container container, ArrayList arlDatFac) {
     public ZafCom40_01(java.awt.Frame parent, boolean modal, ZafParSis obj, java.awt.Container container) {
         super(parent, modal);
        initComponents();
          //Inicializar objetos.
        objParSis=obj;
        cnt01=container;
//        System.out.println("VER 01 :  " + cnt01);
        intPro=2;
        objUti=new ZafUtil();
//        arlDat=new ArrayList();
//        arlDat.clear();
//        arlDat=arlDatFac;
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCon = new javax.swing.JPanel();
        optNueCot = new javax.swing.JRadioButton();
        optExiCot = new javax.swing.JRadioButton();
        txtNumCot = new javax.swing.JTextField();
        lblCot = new javax.swing.JLabel();
        butCot = new javax.swing.JButton();
        butBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panCon.setLayout(null);

        optNueCot.setSelected(true);
        optNueCot.setText("Crear nueva cotización");
        optNueCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optNueCotActionPerformed(evt);
            }
        });
        panCon.add(optNueCot);
        optNueCot.setBounds(2, 2, 290, 16);

        optExiCot.setText("Utilizar cotización existente");
        optExiCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optExiCotActionPerformed(evt);
            }
        });
        panCon.add(optExiCot);
        optExiCot.setBounds(2, 20, 300, 16);

        txtNumCot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumCotFocusLost(evt);
            }
        });
        panCon.add(txtNumCot);
        txtNumCot.setBounds(100, 40, 70, 20);

        lblCot.setText("# Cotización:");
        panCon.add(lblCot);
        lblCot.setBounds(26, 42, 74, 18);

        butCot.setText("jButton1");
        butCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCotActionPerformed(evt);
            }
        });
        panCon.add(butCot);
        butCot.setBounds(170, 39, 24, 23);

        PanFrm.add(panCon, java.awt.BorderLayout.CENTER);

        butBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        butBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        butBot.add(butCan);

        PanFrm.add(butBot, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-435)/2, (screenSize.height-164)/2, 435, 164);
    }// </editor-fold>//GEN-END:initComponents

private void optExiCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optExiCotActionPerformed
// TODO add your handling code here:
    if(optExiCot.isSelected())
        optNueCot.setSelected(false);
    else
        optNueCot.setSelected(true);
}//GEN-LAST:event_optExiCotActionPerformed

private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
    if(isCamVal()){
        //mostrarMsgInf("PARAMETROS Q SE LE ENVIAN A VICO PARA Q GENERE TODO EL PROCESO DE LA FACTURACION....");
        if(optNueCot.isSelected()){
            intTipCot=0;
        }
        else if(optExiCot.isSelected()){
            intTipCot=Integer.parseInt(txtNumCot.getText().toString());
        }
        intPro=1;
        this.dispose();
    }
}//GEN-LAST:event_butAceActionPerformed

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
// TODO add your handling code here:
    exitForm(null);
}//GEN-LAST:event_butCanActionPerformed

private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                intPro=0;
                intTipCot=2;
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
}//GEN-LAST:event_exitForm

private void butCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCotActionPerformed
    Compras.ZafCom40.ZafCom40_02 objCom40_02=new Compras.ZafCom40.ZafCom40_02(javax.swing.JOptionPane.getFrameForComponent(this), true,objParSis, cnt01);
    //this.getParent().add(objCom40_01,javax.swing.JLayeredPane.DEFAULT_LAYER);
//    System.out.println("40_01 - FRANCIS: " + this.getParent());
    objCom40_02.setNumCotSel(txtNumCot.getText());
    objCom40_02.show();
    txtNumCot.setText(""+objCom40_02.getNumCotSel());
    optExiCot.setSelected(true);
    optNueCot.setSelected(false);
}//GEN-LAST:event_butCotActionPerformed

private void optNueCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optNueCotActionPerformed
// TODO add your handling code here:
    if(optNueCot.isSelected())
        optExiCot.setSelected(false);
    else
        optExiCot.setSelected(true);
}//GEN-LAST:event_optNueCotActionPerformed

private void txtNumCotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCotFocusLost
// TODO add your handling code here:
    if( (txtNumCot.getText().toString().length()>0) ){
        optExiCot.setSelected(true);
        optNueCot.setSelected(false);
    }
    else{
        optExiCot.setSelected(false);
        optNueCot.setSelected(true);
    }
}//GEN-LAST:event_txtNumCotFocusLost
   

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JPanel butBot;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCot;
    private javax.swing.JLabel lblCot;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optExiCot;
    private javax.swing.JRadioButton optNueCot;
    private javax.swing.JPanel panCon;
    private javax.swing.JTextField txtNumCot;
    // End of variables declaration//GEN-END:variables
       
    
   
    /** Cerrar la aplicaci�n. */
    private void exitForm(){
        dispose();
    }    
    
    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private boolean isCamVal(){
        if(optExiCot.isSelected()){
            if(txtNumCot.getText().toString().equals("")){
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de cotización</FONT> es obligatorio.<BR>Digite o seleccione una cotización existente y vuelva a intentarlo.</HTML>");
                return false;
            }
            if (!txtNumCot.getText().equals("")){
                strSQL="";
                strSQL+="SELECT a1.co_cot";
                strSQL+=" FROM tbm_cabcotven AS a1 LEFT OUTER JOIN tbm_usr AS a2 ";
                strSQL+=" 	 ON a1.co_ven=a2.co_usr";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a3 ";
                strSQL+=" 	 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli";
                strSQL+=" LEFT OUTER JOIN tbm_detcotven AS a4";
                strSQL+=" 	 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cot=a4.co_cot";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="  AND a1.st_reg IN('A','U','P','E')";
                strSQL+="  AND a1.co_cot=" + txtNumCot.getText() + "";
                
                
                if (objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {
                    mostrarMsgInf("<HTML>El número de cotización <FONT COLOR=\"blue\">" + txtNumCot.getText() + "</FONT> no existe o ya fue facturada.<BR>Escriba otro número de cotización y vuelva a intentarlo.</HTML>");
                    txtNumCot.selectAll();
                    txtNumCot.requestFocus();
                    return false;
                }
            }
        }

        return true;
    }

    public int getProcesoRealizado() {
        return intPro;
    }

    public void setProcesoRealizado(int intPro) {
        this.intPro = intPro;
    }

    public int getTipoCotizacion() {
        return intTipCot;
    }

    public void setTipoCotizacion(int intTipCot) {
        this.intTipCot = intTipCot;
    }

    public int getNumeroCotizacion() {
        return Integer.parseInt(txtNumCot.getText().toString());
    }
    
    
    
    
//    public ArrayList getArregloDatosFacturar() {
//        return arlDat;
//    }
//
//    public void setArregloDatosFacturar(ArrayList arlDat) {
//        this.arlDat = arlDat;
//    }

    

    
    
   
}

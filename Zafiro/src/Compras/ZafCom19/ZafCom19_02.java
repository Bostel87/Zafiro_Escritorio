/*
 * ZafCom19_02.java
 *
 * Created on 25 de noviembre de 2008, 9:41
 */
package Compras.ZafCom19;

/**
 *
 * @author jayapata
 */
public class ZafCom19_02 extends javax.swing.JDialog 
{

    boolean blnEst = false;
    boolean blnAcepta = false;
    private String Str_RegSel[];

    /**
     * Creates new form ZafCom19_02
     */
    public ZafCom19_02(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panCab = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panDat = new javax.swing.JPanel();
        panTab = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        optIngMer = new javax.swing.JRadioButton();
        optEgrMer = new javax.swing.JRadioButton();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblTit.setText("Seleccione una Opción");
        panCab.add(lblTit);

        getContentPane().add(panCab, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        panGen.setLayout(null);

        buttonGroup1.add(optIngMer);
        optIngMer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optIngMer.setSelected(true);
        optIngMer.setText("Ingreso de Mercaderia.");
        panGen.add(optIngMer);
        optIngMer.setBounds(20, 20, 137, 23);

        buttonGroup1.add(optEgrMer);
        optEgrMer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optEgrMer.setText("Egreso de Mercaderia.");
        panGen.add(optEgrMer);
        optEgrMer.setBounds(20, 50, 170, 23);

        panTab.addTab("General", panGen);

        panDat.add(panTab, java.awt.BorderLayout.CENTER);

        getContentPane().add(panDat, java.awt.BorderLayout.CENTER);

        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(350, 250));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        blnEst = false;
        dispose();

    }//GEN-LAST:event_formWindowClosing

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        salir();
    }//GEN-LAST:event_butAceActionPerformed

    public boolean acepta() {
        return blnEst;
    }

    private void salir() {
        Str_RegSel = new String[1];
        if (optIngMer.isSelected()) // Ingreso
        {
            Str_RegSel[0] = "1"; 
        }
        if (optEgrMer.isSelected()) // Egreso
        {
            Str_RegSel[0] = "2"; 
        }
        blnAcepta = true;
        blnEst = true;
        dispose();
    }

    public String GetCamSel(int Idx) {
        if (!(Str_RegSel == null)) {
            if (Idx <= 0 || Idx > Str_RegSel.length) {
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length);
            } else {
                return Str_RegSel[Idx - 1];
            }
        } else {
            return "";
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optEgrMer;
    private javax.swing.JRadioButton optIngMer;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panGen;
    private javax.swing.JTabbedPane panTab;
    // End of variables declaration//GEN-END:variables
}

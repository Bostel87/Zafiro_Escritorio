/*
 * ZafCon47_01.java
 *
 * Created on 8 de agosto de 2007, 03:28 PM
 */

package Contabilidad.ZafCon47;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;  
import java.util.ArrayList;  
import Librerias.ZafUtil.ZafUtil;

/**
 *    
 * @author  jayapata     
 */
public class ZafCon47_01 extends javax.swing.JDialog {
    
     private ZafParSis objParSis;
     javax.swing.JDialog jfrThis; //Hace referencia a this
     
     ZafVenCon objVenCtaMay; //*****************
     ZafVenCon objVenCtaDet; //*****************   
     Librerias.ZafParSis.ZafParSis objZafParSis;
     ZafUtil objUti;
     String strCodMay="",strMay="", strNumCtaMay="",strCodDet="",strDet="", strNumCtaDet="",strEmpGrpDet="",strEmpGrpMay="";
     
     public boolean blnAcepta = false;
     public boolean blnEst=false;
     private String Str_RegSel[];
     
     javax.swing.JTextField txtNumCtaMay = new javax.swing.JTextField();
     javax.swing.JTextField txtNumCtaDet = new javax.swing.JTextField();
     javax.swing.JTextField txtCodEmpgrpMay = new javax.swing.JTextField();
     javax.swing.JTextField txtCodEmpgrpDet = new javax.swing.JTextField();
       
     
     
      
    /** Creates new form ZafCon47_01 */
    public ZafCon47_01(java.awt.Frame parent, boolean modal, ZafParSis obj) {
        super(parent, modal);
        try{
            objUti = new ZafUtil();
            initComponents(); 
            objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        }catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
      
    
     public void Configura_ventana_consulta(){
        configurarVenConCtaMayor();
        configurarVenConCtaDetalle();
        
        java.awt.Color colBack = txtCodMay.getBackground();
        txtCodMay.setEditable(false);
        txtCodMay.setBackground(colBack);
        txtMay.setEditable(false);
        txtMay.setBackground(colBack);
        butMay.setEnabled(false);
        
        txtCodDet.setEditable(false);
        txtCodDet.setBackground(colBack);
        txtDet.setEditable(false);
        txtDet.setBackground(colBack);
        butDet.setEnabled(false);
     }
    
      
     
    
      private boolean configurarVenConCtaDetalle()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cta");
            arlCam.add("a.tx_codcta");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.co_emp");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("No.Cuenta");
            arlAli.add("Nombre.");
            arlAli.add("Emp.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("100"); 
            arlAncCol.add("370");
            arlAncCol.add("20");
            
            //Armar la sentencia SQL.
             String  strSQL=""; 
             
            if(objZafParSis.getCodigoEmpresaGrupo()==objZafParSis.getCodigoEmpresa()){
             
             strSQL="SELECT * FROM (" +
             " select " +
             " case when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=0) > 0 then (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=0) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=1) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=1) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=2) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=2) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=3) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=3) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=4) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=4) " +
             " end   as co_cta  ,  tx_codcta , tx_deslar " +
             ", " +
             " case when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=0) > 0 then  0 " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=1) > 0 then  1 " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=2) > 0 then  2 " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=3) > 0 then  3  " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=4) > 0 then  4  " +
             "	end   as co_emp    " +
             "  FROM ( " +
             "  SELECT  a.tx_codcta,  a.tx_deslar FROM tbm_placta AS a WHERE  a.tx_tipcta='D'   " +
             "  group by  a.tx_codcta,  a.tx_deslar  " +
             " order by tx_codcta  ) as x ) as a order by tx_codcta";
             
             }else{
               strSQL="SELECT a.co_cta, a.tx_codcta,  a.tx_deslar, a.co_Emp  FROM tbm_placta AS a WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.tx_tipcta='D'"; 
              }
               
             
            objVenCtaDet=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
      
     
                
              
     
     private boolean configurarVenConCtaMayor()
      {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cta");
            arlCam.add("a.tx_codcta");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.co_emp");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("No.Cuenta");
            arlAli.add("Nombre.");
            arlAli.add("Emp.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("100"); 
            arlAncCol.add("370");
            arlAncCol.add("20");
            //Armar la sentencia SQL.    
             String  strSQL="";
                
           if(objZafParSis.getCodigoEmpresaGrupo()==objZafParSis.getCodigoEmpresa()){
             
             strSQL="SELECT * FROM (" +
             " select " +
             " case when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=0) > 0 then (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=0) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=1) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=1) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=2) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=2) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=3) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=3) " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=4) > 0 then  (select co_cta from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=4) " +
             " end   as co_cta  ,  tx_codcta , tx_deslar " +
             ", " +
             " case when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=0) > 0 then  0 " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=1) > 0 then  1 " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=2) > 0 then  2 " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=3) > 0 then  3  " +
             " when (select count(*) from tbm_placta where tx_codcta=x.tx_codcta and tx_deslar=x.tx_deslar and co_emp=4) > 0 then  4  " +
             "	end   as co_emp    " +
             "  FROM ( " +
             "  SELECT  a.tx_codcta,  a.tx_deslar FROM tbm_placta AS a WHERE  a.tx_tipcta='C'   " +
             "  group by  a.tx_codcta,  a.tx_deslar  " +
             " order by tx_codcta  ) as x ) as a order by tx_codcta";
             
             }else{
                strSQL="SELECT a.co_cta, a.tx_codcta,  a.tx_deslar , a.co_emp FROM tbm_placta AS a WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  AND a.tx_tipcta='C'"; 
             }
                  
            objVenCtaMay=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
        
    
    
    /** This method is called from within the constructor to 
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        optFil = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        PanBut = new javax.swing.JPanel();
        panBut2 = new javax.swing.JPanel();
        ButAce = new javax.swing.JButton();
        ButCan = new javax.swing.JButton();
        TabGen = new javax.swing.JTabbedPane();
        PanGen = new javax.swing.JPanel();
        optGrp = new javax.swing.JRadioButton();
        optMay = new javax.swing.JRadioButton();
        optDet = new javax.swing.JRadioButton();
        txtGrp = new javax.swing.JTextField();
        txtCodMay = new javax.swing.JTextField();
        txtMay = new javax.swing.JTextField();
        butMay = new javax.swing.JButton();
        txtCodDet = new javax.swing.JTextField();
        txtDet = new javax.swing.JTextField();
        butDet = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        PanBut.setLayout(new java.awt.BorderLayout());

        ButAce.setText("Aceptar");
        ButAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButAceActionPerformed(evt);
            }
        });

        panBut2.add(ButAce);

        ButCan.setText("Cancelar");
        ButCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButCanActionPerformed(evt);
            }
        });

        panBut2.add(ButCan);

        PanBut.add(panBut2, java.awt.BorderLayout.EAST);

        jPanel1.add(PanBut, java.awt.BorderLayout.SOUTH);

        PanGen.setLayout(null);

        optGrp.setSelected(true);
        optGrp.setText("Grupo");
        optFil.add(optGrp);
        optGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optGrpActionPerformed(evt);
            }
        });

        PanGen.add(optGrp);
        optGrp.setBounds(20, 8, 180, 20);

        optMay.setText("Cuenta de Mayor");
        optFil.add(optMay);
        optMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optMayActionPerformed(evt);
            }
        });

        PanGen.add(optMay);
        optMay.setBounds(20, 56, 196, 20);

        optDet.setText("Cuenta de Detalle");
        optFil.add(optDet);
        optDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optDetActionPerformed(evt);
            }
        });

        PanGen.add(optDet);
        optDet.setBounds(20, 112, 200, 20);

        txtGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGrpActionPerformed(evt);
            }
        });
        txtGrp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGrpKeyPressed(evt);
            }
        });

        PanGen.add(txtGrp);
        txtGrp.setBounds(44, 28, 228, 20);

        txtCodMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMayActionPerformed(evt);
            }
        });
        txtCodMay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMayFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMayFocusLost(evt);
            }
        });

        PanGen.add(txtCodMay);
        txtCodMay.setBounds(44, 80, 36, 20);

        txtMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMayActionPerformed(evt);
            }
        });
        txtMay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMayFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMayFocusLost(evt);
            }
        });

        PanGen.add(txtMay);
        txtMay.setBounds(80, 80, 192, 20);

        butMay.setText("...");
        butMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMayActionPerformed(evt);
            }
        });

        PanGen.add(butMay);
        butMay.setBounds(272, 80, 20, 21);

        txtCodDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDetActionPerformed(evt);
            }
        });
        txtCodDet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDetFocusLost(evt);
            }
        });

        PanGen.add(txtCodDet);
        txtCodDet.setBounds(44, 136, 36, 20);

        txtDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDetActionPerformed(evt);
            }
        });
        txtDet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDetFocusLost(evt);
            }
        });

        PanGen.add(txtDet);
        txtDet.setBounds(80, 136, 192, 20);

        butDet.setText("...");
        butDet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDetActionPerformed(evt);
            }
        });

        PanGen.add(butDet);
        butDet.setBounds(272, 136, 20, 21);

        TabGen.addTab("General", PanGen);

        jPanel1.add(TabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-300)/2, 400, 300);
    }//GEN-END:initComponents

    private void txtGrpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrpKeyPressed
        // TODO add your handling code here:
         if(java.awt.event.KeyEvent.VK_ENTER==evt.getKeyCode()) {
             boolean a = validarCampos();
                  if(a == true){  blnEst=true; salir(); }
         }  
    }//GEN-LAST:event_txtGrpKeyPressed

    private void txtGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGrpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGrpActionPerformed

    
    
    
    
    private void optDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDetActionPerformed
        // TODO add your handling code here:
        
        java.awt.Color colBack = txtCodMay.getBackground();
        txtGrp.setEditable(false);
        txtGrp.setBackground(colBack);
       
        txtCodDet.setEditable(true);
        txtDet.setEditable(true);
        butDet.setEnabled(true);
        
        txtCodMay.setEditable(false);
        txtCodMay.setBackground(colBack);
        txtMay.setEditable(false);
        txtMay.setBackground(colBack);
        butMay.setEnabled(false);
        
         if(!optDet.isSelected()) {
            txtCodDet.setText("");
            txtDet.setText("");
            txtNumCtaDet.setText("");
            strCodDet="";
            strNumCtaDet="";
            strDet="";
         }
         if(!optMay.isSelected()) {
            txtCodMay.setText("");
            txtMay.setText("");
            txtMay.setText("");
            txtNumCtaMay.setText("");
            strCodMay="";
            strNumCtaMay="";
            strMay="";  
         }
         if(!optGrp.isSelected()) {
            txtGrp.setText("");
         }  
        
    }//GEN-LAST:event_optDetActionPerformed

     
    
    
    private void optMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optMayActionPerformed
        // TODO add your handling code here:
       
        java.awt.Color colBack = txtCodMay.getBackground();
        txtGrp.setEditable(false);
        txtGrp.setBackground(colBack);
       
        txtCodMay.setEditable(true);
        txtMay.setEditable(true);
        butMay.setEnabled(true);
        
        txtCodDet.setEditable(false);
        txtCodDet.setBackground(colBack);
        txtDet.setEditable(false);
        txtDet.setBackground(colBack);
        butDet.setEnabled(false);
        
        if(!optDet.isSelected()) {
            txtCodDet.setText("");
            txtDet.setText("");
            strCodDet="";
            strDet="";
         }
         if(!optMay.isSelected()) {
            txtCodMay.setText("");
            txtMay.setText("");
            strCodMay="";
            strMay="";  
         }
         if(!optGrp.isSelected()) {
            txtGrp.setText("");
         }  
        
    }//GEN-LAST:event_optMayActionPerformed

    private void optGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optGrpActionPerformed
        // TODO add your handling code here:
       
        txtGrp.setEditable(true);
        
        java.awt.Color colBack = txtCodMay.getBackground();
        txtCodMay.setEditable(false);
        txtCodMay.setBackground(colBack);
        txtMay.setEditable(false);
        txtMay.setBackground(colBack);
        butMay.setEnabled(false);
        
        txtCodDet.setEditable(false);
        txtCodDet.setBackground(colBack);
        txtDet.setEditable(false);
        txtDet.setBackground(colBack);
        butDet.setEnabled(false);
        
        if(!optDet.isSelected()) {
            txtCodDet.setText("");
            txtDet.setText("");
            strCodDet="";
            strDet="";
         }
         if(!optMay.isSelected()) {
            txtCodMay.setText("");
            txtMay.setText("");
            strCodMay="";
            strMay="";  
         }
         if(!optGrp.isSelected()) {
            txtGrp.setText("");
         }  
    }//GEN-LAST:event_optGrpActionPerformed

    private void txtDetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDetFocusLost
        // TODO add your handling code here:
        
        
          if (!txtDet.getText().equalsIgnoreCase(strDet)) 
         {
            if (txtDet.getText().equals(""))
            {
                txtCodDet.setText("");
                txtDet.setText("");
            }
            else
            {
                 BuscarCuentasDetalle("a.tx_deslar",txtDet.getText(),1);
            }
        }
        else
            txtDet.setText(strDet);
        
          
          
    }//GEN-LAST:event_txtDetFocusLost

    private void txtDetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDetFocusGained
        // TODO add your handling code here:
        
         strDet=txtDet.getText();
         txtDet.selectAll();
        
    }//GEN-LAST:event_txtDetFocusGained

    private void txtCodDetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDetFocusLost
        // TODO add your handling code here:
        
         if (!txtCodDet.getText().equalsIgnoreCase(strCodDet))
         {
            if (txtCodDet.getText().equals(""))
            {
                txtCodDet.setText("");
                txtDet.setText("");
            }
            else
            {
                BuscarCuentasDetalle("a.co_cta",txtCodDet.getText(),0);
            }
        }
        else
            txtCodDet.setText(strCodDet);
         
         
    }//GEN-LAST:event_txtCodDetFocusLost

    private void txtCodDetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDetFocusGained
        // TODO add your handling code here:
         strCodDet=txtCodDet.getText();
         txtCodDet.selectAll();
    }//GEN-LAST:event_txtCodDetFocusGained

    private void txtCodDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDetActionPerformed
        // TODO add your handling code here:
        txtCodDet.transferFocus();
    }//GEN-LAST:event_txtCodDetActionPerformed

    private void butDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDetActionPerformed
        // TODO add your handling code here:
        // BuscarCuentasDetalle("a.co_cta","",0);      
           
        BuscarCuentasDetalle("a.co_cta","",0);
        
//            objVenCtaDet.setTitle("Listado de cuentas de detalle..  ");        
//            objVenCtaDet.setCampoBusqueda(1);
//            objVenCtaDet.show();
//            if (objVenCtaDet.getSelectedButton()==objVenCtaDet.INT_BUT_ACE)
//            {
//                txtCodDet.setText(objVenCtaDet.getValueAt(1));
//                txtNumCtaDet.setText(objVenCtaDet.getValueAt(2));
//                txtDet.setText(objVenCtaDet.getValueAt(3));
//             } 
              
                 
    }//GEN-LAST:event_butDetActionPerformed

    private void txtMayFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMayFocusLost
        // TODO add your handling code here:
          
          
         if (!txtMay.getText().equalsIgnoreCase(strMay))
         {
            if (txtMay.getText().equals(""))
            {
                txtCodMay.setText("");
                txtMay.setText("");
            }
            else
            {
                 BuscarCuentasMayor("a.tx_deslar",txtMay.getText(),1);
            }
        }
        else
            txtMay.setText(strMay);
        
             
           
    }//GEN-LAST:event_txtMayFocusLost

    private void txtMayFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMayFocusGained
        // TODO add your handling code here:
         strMay=txtMay.getText();
         txtMay.selectAll();
    }//GEN-LAST:event_txtMayFocusGained

    private void txtMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMayActionPerformed
        // TODO add your handling code here:
         txtMay.transferFocus();
    }//GEN-LAST:event_txtMayActionPerformed

    private void txtCodMayFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMayFocusLost
        // TODO add your handling code here:
        
         if (!txtCodMay.getText().equalsIgnoreCase(strCodMay))
         {
            if (txtCodMay.getText().equals(""))
            {
                txtCodMay.setText("");
                txtMay.setText("");
            }
            else
            {
                BuscarCuentasMayor("a.co_cta",txtCodMay.getText(),0);
            }
        }
        else
            txtCodMay.setText(strCodMay);
        
    }//GEN-LAST:event_txtCodMayFocusLost

    private void txtCodMayFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMayFocusGained
        // TODO add your handling code here:
         strCodMay=txtCodMay.getText();
         txtCodMay.selectAll();
    }//GEN-LAST:event_txtCodMayFocusGained

    private void txtCodMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMayActionPerformed
        // TODO add your handling code here:
         txtCodMay.transferFocus();
    }//GEN-LAST:event_txtCodMayActionPerformed

    private void butMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMayActionPerformed
        // TODO add your handling code here:
        BuscarCuentasMayor("a.co_cta","",0);
      
//              objVenCtaMay.setTitle("Listado de cuentas de mayor..  ");        
//              objVenCtaMay.setCampoBusqueda(1);
//              objVenCtaMay.show();
//            if (objVenCtaMay.getSelectedButton()==objVenCtaMay.INT_BUT_ACE)
//            {
//               txtCodMay.setText(objVenCtaMay.getValueAt(1));
//                txtNumCtaMay.setText(objVenCtaMay.getValueAt(2));
//                txtMay.setText(objVenCtaMay.getValueAt(3));
//               
//            } 
              
              
        
    }//GEN-LAST:event_butMayActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formWindowOpened

    private void ButAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButAceActionPerformed
        // TODO add your handling code here:
         
         
         
         boolean a = validarCampos();
                  if(a == true){  blnEst=true; salir(); }
         
         
          
    }//GEN-LAST:event_ButAceActionPerformed

    
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
      
        
                  
        
        
     private void salir(){
               Str_RegSel = new String[5];
              
               if(optGrp.isSelected()){
                  Str_RegSel[0] =  "";
                  Str_RegSel[1] = txtGrp.getText();
                  Str_RegSel[2] = "P";
                  Str_RegSel[3] = "";
                  Str_RegSel[4] = "";
                  
               }
               if(optMay.isSelected()){
                  Str_RegSel[0] = txtCodMay.getText();
                  Str_RegSel[1] = txtMay.getText();
                  Str_RegSel[2] = "C";
                  Str_RegSel[3] = txtNumCtaMay.getText();
                  if(objZafParSis.getCodigoEmpresaGrupo()==objZafParSis.getCodigoEmpresa())  Str_RegSel[4] = txtCodEmpgrpMay.getText();
                   else Str_RegSel[4] = String.valueOf(objZafParSis.getCodigoEmpresa());   
               }
               if(optDet.isSelected()){
                  Str_RegSel[0] = txtCodDet.getText();
                  Str_RegSel[1] = txtDet.getText();
                  Str_RegSel[2] = "D";
                  Str_RegSel[3] = txtNumCtaDet.getText(); 
                   if(objZafParSis.getCodigoEmpresaGrupo()==objZafParSis.getCodigoEmpresa())  Str_RegSel[4] = txtCodEmpgrpDet.getText();
                   else Str_RegSel[4] = String.valueOf(objZafParSis.getCodigoEmpresa());   
               }
                        
               blnAcepta = true;
                      
               
               txtGrp.setText("");
               txtCodMay.setText("");
               txtMay.setText("");
               txtNumCtaMay.setText("");
               txtCodEmpgrpMay.setText("");
               
               txtCodDet.setText("");
               txtDet.setText("");
               txtNumCtaDet.setText("");
               txtCodEmpgrpDet.setText("");  
               
         dispose();
    }
     
       
     
     
       
         
    
     public void BuscarCuentasMayor(String campo,String strBusqueda,int tipo){
      // configurarVenConVendedor();  //************************
        objVenCtaMay.setTitle("Listado de cuentas de mayor..  "); 
        if (objVenCtaMay.buscar(campo, strBusqueda ))
        {
            txtCodMay.setText(objVenCtaMay.getValueAt(1));
            txtNumCtaMay.setText(objVenCtaMay.getValueAt(2));
            txtMay.setText(objVenCtaMay.getValueAt(3));
            txtCodEmpgrpMay.setText(objVenCtaMay.getValueAt(4));
            
            strCodMay=txtCodMay.getText();
            strNumCtaMay=txtNumCtaMay.getText();
            strMay=txtMay.getText();
            strEmpGrpMay=txtCodEmpgrpMay.getText();  
                    
        }
        else
        {     objVenCtaMay.setCampoBusqueda(tipo);
              objVenCtaMay.cargarDatos();
              objVenCtaMay.show();
             if (objVenCtaMay.getSelectedButton()==objVenCtaMay.INT_BUT_ACE)
             {
                txtCodMay.setText(objVenCtaMay.getValueAt(1));
                txtNumCtaMay.setText(objVenCtaMay.getValueAt(2));
                txtMay.setText(objVenCtaMay.getValueAt(3));
                txtCodEmpgrpMay.setText(objVenCtaMay.getValueAt(4));
                
                strCodMay=txtCodMay.getText();
                strNumCtaMay=txtNumCtaMay.getText();
                strMay=txtMay.getText();
                strEmpGrpMay=txtCodEmpgrpMay.getText();

             }
               else{
                    txtCodMay.setText(strCodMay);
                    txtNumCtaMay.setText(strNumCtaMay);
                    txtMay.setText(strMay);
                    txtCodEmpgrpMay.setText(strEmpGrpMay);
                  }
        }
    }
      
     
        
     
     
       
      public void BuscarCuentasDetalle(String campo,String strBusqueda,int tipo){
        objVenCtaDet.setTitle("Listado de cuentas de detalle.. "); 
        if (objVenCtaDet.buscar(campo, strBusqueda ))
        {
            txtCodDet.setText(objVenCtaDet.getValueAt(1));
            txtNumCtaDet.setText(objVenCtaDet.getValueAt(2));  
            txtDet.setText(objVenCtaDet.getValueAt(3));
            txtCodEmpgrpDet.setText(objVenCtaDet.getValueAt(4));
            
            strCodDet=txtCodDet.getText();
            strNumCtaDet=txtNumCtaDet.getText();
            strDet=txtDet.getText();
            strEmpGrpDet=txtCodEmpgrpDet.getText();
            
        }
        else
        {     objVenCtaDet.setCampoBusqueda(tipo);
              objVenCtaDet.cargarDatos();
              objVenCtaDet.show();
             if (objVenCtaDet.getSelectedButton()==objVenCtaDet.INT_BUT_ACE)
             {
                txtCodDet.setText(objVenCtaDet.getValueAt(1));
                txtNumCtaDet.setText(objVenCtaDet.getValueAt(2));
                txtDet.setText(objVenCtaDet.getValueAt(3));
                txtCodEmpgrpDet.setText(objVenCtaDet.getValueAt(4));
                
                    strCodDet=txtCodDet.getText();
                    strNumCtaDet=txtNumCtaDet.getText();
                    strDet=txtDet.getText();
                    strEmpGrpDet=txtCodEmpgrpDet.getText();
            
             }
               else{
                    txtCodDet.setText(strCodDet);
                    txtNumCtaDet.setText(strNumCtaDet);
                    txtDet.setText(strDet);
                    txtCodEmpgrpDet.setText(strEmpGrpDet);
                  }
        }
    }
    
       
    
    
    private boolean validarCampos(){
        if(optGrp.isSelected()){
            if(txtGrp.getText().trim().equals("")){
                MensajeInf("Ingrese nombre del Grupo.");
                txtGrp.requestFocus();
                return false;        
        }}
        if(optMay.isSelected()){
            if(txtCodMay.getText().trim().equals("")){
                MensajeInf("Ingrese cuenta de Mayor.");
                txtCodMay.requestFocus();
                return false;  
            }
             if(txtMay.getText().trim().equals("")){
                MensajeInf("Ingrese cuenta de Mayor.");
                txtCodMay.requestFocus();
                return false;  
        }}
        if(optDet.isSelected()){
             if(txtCodDet.getText().trim().equals("")){
                MensajeInf("Ingrese cuenta de Detalle.");
                txtCodDet.requestFocus();
                return false;  
            }
             if(txtDet.getText().trim().equals("")){
                MensajeInf("Ingrese cuenta de Detalle."); 
                txtCodDet.requestFocus();
                return false;  
       }}
           
      return true;  
    }
    
    
    
          
    
    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Mensaje del sistema Zafiro";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    
    private void ButCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButCanActionPerformed
        // TODO add your handling code here:
          
               txtGrp.setText("");
               txtCodMay.setText("");
               txtMay.setText("");
               txtNumCtaMay.setText("");
               txtCodEmpgrpMay.setText("");
               
               txtCodDet.setText("");
               txtDet.setText("");
               txtNumCtaDet.setText("");
               txtCodEmpgrpDet.setText("");  
               blnEst=false;
         dispose();     
    }//GEN-LAST:event_ButCanActionPerformed
   
    private void txtDetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDetActionPerformed
        // TODO add your handling code here:
         txtDet.transferFocus();
    }//GEN-LAST:event_txtDetActionPerformed
         
    /**     
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButAce;
    private javax.swing.JButton ButCan;
    private javax.swing.JPanel PanBut;
    private javax.swing.JPanel PanGen;
    private javax.swing.JTabbedPane TabGen;
    private javax.swing.JButton butDet;
    private javax.swing.JButton butMay;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton optDet;
    private javax.swing.ButtonGroup optFil;
    private javax.swing.JRadioButton optGrp;
    private javax.swing.JRadioButton optMay;
    private javax.swing.JPanel panBut2;
    private javax.swing.JTextField txtCodDet;
    private javax.swing.JTextField txtCodMay;
    private javax.swing.JTextField txtDet;
    private javax.swing.JTextField txtGrp;
    private javax.swing.JTextField txtMay;
    // End of variables declaration//GEN-END:variables
    
}

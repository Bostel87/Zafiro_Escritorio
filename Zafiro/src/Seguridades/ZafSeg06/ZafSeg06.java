/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Seguridades.ZafSeg06;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae04.ZafMae04;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.Border;
/**
 *
 * @author BosteL
 */
public class ZafSeg06 extends javax.swing.JInternalFrame {

    
  //ArrayList para consultar
    private ArrayList arlDatConUsr, arlRegConUsr;
    private static final int INT_ARL_CON_USR_COD_USR=0;  
    private int intIndiceUsr=0;
    
    //Variables
    private Connection con;                          //Variable para conexion a la Base de Datos
    private Statement stm;                           //Variable para ejecucion de sentencias SQL
    private ResultSet rst;                           //Variable para manipular registro de la tabla en ejecucion
    private ZafParSis objParSis;                     //Objeto que me permitira obtener los parametros del sistema, como podria ser la ruta de la base de datos, etc.  
    private ZafUtil objUti;                          //Objeto del tipo de la clase ZafUtil, el cual me va a permitir manipular los diferentes metodos de esta clase
    private MiToolBar objTooBar;                     //Objeto de tipo MiToolBar para poder manipular la clase ZafMiToolBar
    private ZafDocLis objDocLis;
    private ZafVenCon vcoUsr;                        //Ventana de Consulta Usuarios
    private ZafVenCon vcoGrpUsr;                     //Ventana de Consulta Usuarios
    private ZafVenCon vcoTra;                        //Ventana de Consulta Usuarios.
    private ZafTblMod objTblModEmpUsr, objTblModLocUsr, objTblModBodUsr;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblFilCab objTblFilCab;
    private ZafTblPopMnu objTblPopMnu;               //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                     //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                  //JTable de ordenamiento.
    private java.util.Date datFecAux;  //Auxiliar: Para almacenar fechas.
    
     private String strDesCorUsr, strDesLarUsr; 
    private Vector vecDat, vecCab, vecReg;
    private Vector vecEstCiv, vecEmp,vecLoc;                        //Vector para almacenar el codigo del estado en uso
    private boolean blnHayCam;                       //Determina si hay cambios en el formulario.
    
    private StringBuffer stbSQL;                     //Variable StringBuffer para ejecutar sentencias SQL.
    private String strSQL, strAux;                   //Variable auxiliar de tipo string la cual servira para guardar aquellas cadenas en las cuales me esten enviando algun caracter invalido
    private String strCodUsr, strUsr, strCedUsr;
    private String strCodGrpUsr, strGrpUsr;
    private String strVer=" v0.1.1 ";
    /**
     * Creates new form ZafSeg06
     */
    public ZafSeg06(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti= new ZafUtil();
            int coemp = objParSis.getCodigoEmpresa();
            String emp = objParSis.getNombreEmpresa();
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
             initComponents();
             lblEmp.setVisible(false);
             lblLoc.setVisible(false);
             cboEmp.setVisible(false);
             cboLoc.setVisible(false);
             butCopPer.setVisible(true);
             butElimPer.setVisible(true);
          
//             txtCodDe.setEnable(false);
//             txtNomDe.setEnable(false);
//             butCodUsrDe.setEnabled(false);
//             txtCodPar.setEnabled(false);
//             txtNomPar.setEnabled(false);
//             butCodPar.setEnabled(false);
             if (!configurarFrm())
                exitForm();
               // agregarDocLis();
            }
            else if (emp.equals("COSENCO") ){
             initComponents();
             lblEmp.setVisible(false);
             lblLoc.setVisible(false);
             cboEmp.setVisible(false);
             cboLoc.setVisible(false);
             butCopPer.setVisible(true);
             butElimPer.setVisible(true);
       
//             txtCodDe.setEnable(false);
//             txtNomDe.setEnable(false);
//             butCodUsrDe.setEnabled(false);
//             txtCodPar.setEnabled(false);
//             txtNomPar.setEnabled(false);
//             butCodPar.setEnabled(false);
             if (!configurarFrm())
                exitForm();  
                
            }
            else if (emp.equals("ECUATOSA S.A.") ){
             initComponents();
             lblEmp.setVisible(false);
             lblLoc.setVisible(false);
             cboEmp.setVisible(false);
             cboLoc.setVisible(false);
             butCopPer.setVisible(true);
             butElimPer.setVisible(true);
         
//             txtCodDe.setEnable(false);
//             txtNomDe.setEnable(false);
//             butCodUsrDe.setEnabled(false);
//             txtCodPar.setEnabled(false);
//             txtNomPar.setEnabled(false);
//             butCodPar.setEnabled(false);
             if (!configurarFrm())
                exitForm();  
                
            }
            else if (emp.equals("DETOPACIO S.A.") ){
             initComponents();
             lblEmp.setVisible(false);
             lblLoc.setVisible(false);
             cboEmp.setVisible(false);
             cboLoc.setVisible(false);
             butCopPer.setVisible(true);
             butElimPer.setVisible(true);
         
//             txtCodDe.setEnable(false);
//             txtNomDe.setEnable(false);
//             butCodUsrDe.setEnabled(false);
//             txtCodPar.setEnabled(false);
//             txtNomPar.setEnabled(false);
//             butCodPar.setEnabled(false);
             if (!configurarFrm())
                exitForm();  
                
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO,COSENCO,ECUATOSA,DETOPACIO.");
                dispose();
            }
        }
        catch(CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + "ERROR");
        }
       
    }
    
       private void exitForm() {
       String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }
    
     /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            //lblTit.setText(strAux);
            lblTit1.setText(strAux);
            //Inicializar objetos.
            objDocLis=new ZafDocLis();
            //objTooBar= new MiToolBar(this);
            //panBar.add(objTooBar);
         
           // txtCodUsr.setBorder(new RoundedBorder((5)));
            //txtCed.setBorder(new RoundedBorder((5)));
            //txtDir.setBorder(new RoundedBorder((5)));
            
//            butElimPer.setEnabled(false);
//            butCopPer.setEnabled(false);
butElimPer.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        butElimPer.setBackground(Color.RED);
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
        butElimPer.setBackground(UIManager.getColor("control"));
    }
});
            txtCodPar.setBackground(objParSis.getColorCamposObligatorios());
            txtNomPar.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDe.setBackground(objParSis.getColorCamposObligatorios());
            txtNomDe.setBackground(objParSis.getColorCamposObligatorios());
            butElimPer.setBorder(new RoundedBorder((5)));
            butCodNuvUsr.setBorder(new RoundedBorder((5)));
            butCodUsrCop.setBorder(new RoundedBorder((5)));
            butCopPer.setBorder(new RoundedBorder((5)));
            //butCodUsr.setBorder(new RoundedBorder((5)));
            //butGrpUsr.setBorder(new RoundedBorder((5)));
            
            //Configurar los Tab.
            //configurarTabGen();
//            configurarTabEmpUsr();
//            configurarTabLocUsr();
//            configurarTabBodUsr();
            
            blnHayCam=false;
        } 
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panCopPer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodPar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCodDe = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNomDe = new javax.swing.JTextField();
        txtNomPar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        butCodNuvUsr = new javax.swing.JButton();
        butCodUsrCop = new javax.swing.JButton();
        butElimPer = new javax.swing.JButton();
        butCopPer = new javax.swing.JButton();
        lblEmp = new javax.swing.JLabel();
        cboEmp = new javax.swing.JComboBox<>();
        lblLoc = new javax.swing.JLabel();
        cboLoc = new javax.swing.JComboBox<>();
        lblTit1 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
        setPreferredSize(new java.awt.Dimension(468, 462));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        panFrm.setMinimumSize(new java.awt.Dimension(143, 144));
        panFrm.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Eliminar\\Para:");

        txtCodPar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodParFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodParFocusLost(evt);
            }
        });

        jLabel2.setText("De:");

        txtCodDe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDeFocusLost(evt);
            }
        });
        txtCodDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDeActionPerformed(evt);
            }
        });

        jLabel3.setText("Codigo:");

        txtNomDe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDeFocusLost(evt);
            }
        });

        txtNomPar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomParFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomParFocusLost(evt);
            }
        });

        jLabel4.setText("Codigo:");

        butCodNuvUsr.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCodNuvUsr.setText("...");
        butCodNuvUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodNuvUsrActionPerformed(evt);
            }
        });

        butCodUsrCop.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCodUsrCop.setText("...");
        butCodUsrCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodUsrCopActionPerformed(evt);
            }
        });

        butElimPer.setText("Eliminar Permisos");
        butElimPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butElimPerActionPerformed(evt);
            }
        });

        butCopPer.setText("Copiar Permisos");
        butCopPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCopPerActionPerformed(evt);
            }
        });

        lblEmp.setText("Empresa:");

        cboEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpActionPerformed(evt);
            }
        });

        lblLoc.setText("Local:");

        lblTit1.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit1.setText("Título de la ventana");

        javax.swing.GroupLayout panCopPerLayout = new javax.swing.GroupLayout(panCopPer);
        panCopPer.setLayout(panCopPerLayout);
        panCopPerLayout.setHorizontalGroup(
            panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCopPerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTit1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panCopPerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCopPerLayout.createSequentialGroup()
                        .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(lblEmp)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panCopPerLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panCopPerLayout.createSequentialGroup()
                                        .addComponent(txtCodPar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(txtNomPar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(butCodNuvUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panCopPerLayout.createSequentialGroup()
                                        .addComponent(txtCodDe, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(txtNomDe)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(butCodUsrCop, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(69, 69, 69)
                                .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(butCopPer, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(butElimPer)))
                            .addGroup(panCopPerLayout.createSequentialGroup()
                                .addComponent(cboEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(lblLoc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboLoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(51, 51, 51))
        );
        panCopPerLayout.setVerticalGroup(
            panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCopPerLayout.createSequentialGroup()
                .addComponent(lblTit1)
                .addGap(4, 4, 4)
                .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmp)
                    .addComponent(cboEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoc)
                    .addComponent(cboLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCodDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butCodUsrCop, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butCopPer))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(panCopPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(butCodNuvUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodPar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomPar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butElimPer))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        tabFrm.addTab("Copiar Permisos", panCopPer);

        panFrm.add(tabFrm, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 435);
    }// </editor-fold>//GEN-END:initComponents

    private void butCodNuvUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodNuvUsrActionPerformed
        configurarVenConUsrNuv();
        mostrarVenConUsrNuv(0);
    }//GEN-LAST:event_butCodNuvUsrActionPerformed

    private void butCodUsrCopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodUsrCopActionPerformed
        configurarVenConUsrCop();
        mostrarVenConUsrCop(0);
    }//GEN-LAST:event_butCodUsrCopActionPerformed

    private void butElimPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butElimPerActionPerformed
        eliminarPer();
    }//GEN-LAST:event_butElimPerActionPerformed

    private void butCopPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCopPerActionPerformed
        CopiarPer();
    }//GEN-LAST:event_butCopPerActionPerformed

    private void txtCodParFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodParFocusLost
           //Validar el contenido de la celda sólo si ha cambiado.
        strCodUsr=txtCodPar.getText();
           if (txtCodPar.getText().equalsIgnoreCase(strCodUsr))
        {
            if (txtCodPar.getText().trim().equals("")) {
                txtNomPar.setText("");
            }
            else {
                consultarRegNue(" AND a.co_usr=" + strCodUsr.replaceAll("'", "''") + "");
            }
        }
        else
        txtCodPar.setText(strCodUsr);
    }//GEN-LAST:event_txtCodParFocusLost

    private void txtCodParFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodParFocusGained
        strCodUsr=txtCodPar.getText();
        txtCodPar.selectAll();
    }//GEN-LAST:event_txtCodParFocusGained

    private void txtCodDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDeActionPerformed
       
    }//GEN-LAST:event_txtCodDeActionPerformed

    private void txtCodDeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDeFocusLost
         strCodUsr=txtCodDe.getText();
        if (txtCodDe.getText().equalsIgnoreCase(strCodUsr))
        {
            if (txtCodDe.getText().trim().equals("")) {
                txtNomDe.setText("");
            }
            else {
                consultarRegCop(" AND a.co_usr=" + strCodUsr.replaceAll("'", "''") + "");
            }
        }
        else
        txtCodDe.setText(strCodUsr);
    }//GEN-LAST:event_txtCodDeFocusLost

    private void txtCodDeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDeFocusGained
        strCodUsr=txtCodDe.getText();
        txtCodDe.selectAll();
    }//GEN-LAST:event_txtCodDeFocusGained

    private void txtNomParFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomParFocusLost
        if (!txtNomPar.getText().equalsIgnoreCase(strDesLarUsr))
        {
            if (txtNomPar.getText().equals(""))
            {
                txtCodPar.setText("");
                //txtDesCorUsr.setText("");
            }
            else
            {
                configurarVenConUsrNuv();
                mostrarVenConUsrNuv(2);
            }
        }
        else
            txtNomPar.setText(strDesLarUsr);
    }//GEN-LAST:event_txtNomParFocusLost

    private void txtNomDeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDeFocusLost
        if (!txtNomDe.getText().equalsIgnoreCase(strDesLarUsr))
        {
            if (txtNomDe.getText().equals(""))
            {
                txtCodDe.setText("");
                //txtDesCorUsr.setText("");
            }
            else
            {
                configurarVenConUsrCop();
                mostrarVenConUsrCop(2);
            }
        }
        else
            txtNomDe.setText(strDesLarUsr);
    }//GEN-LAST:event_txtNomDeFocusLost

    private void txtNomParFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomParFocusGained
       strDesLarUsr=txtNomPar.getText();
        txtNomPar.selectAll();
    }//GEN-LAST:event_txtNomParFocusGained

    private void txtNomDeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDeFocusGained
    strDesLarUsr=txtNomDe.getText();
        txtNomDe.selectAll();
    }//GEN-LAST:event_txtNomDeFocusGained

    private void cboEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpActionPerformed
        try{
           if(cboEmp.getSelectedIndex() != -1){
               if(CodEmp()== 0){
                   cboLoc.removeAllItems();
               }else{
                  configurarComboLocales(CodEmp());
                  
               }
        }else{
            //cboLoc.removeAllItems();
        } 
           
        }catch(Exception e){
            System.out.println(e);
        }
        
        
    }//GEN-LAST:event_cboEmpActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
   
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCodNuvUsr;
    private javax.swing.JButton butCodUsrCop;
    private javax.swing.JButton butCopPer;
    private javax.swing.JButton butElimPer;
    private javax.swing.JComboBox<String> cboEmp;
    private javax.swing.JComboBox<String> cboLoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblTit1;
    private javax.swing.JPanel panCopPer;
    private javax.swing.JPanel panFrm;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTextField txtCodDe;
    private javax.swing.JTextField txtCodPar;
    private javax.swing.JTextField txtNomDe;
    private javax.swing.JTextField txtNomPar;
    // End of variables declaration//GEN-END:variables

  /**
     * Esta función configura Tab "General" 
     */    
    private boolean configurarTabGen()
    {
        boolean blnRes=true;
        try
        {
            //Configurar Campos
//            txtCed.setBackground(objParSis.getColorCamposObligatorios());
//            txtNom.setBackground(objParSis.getColorCamposObligatorios());
//            txtUsr.setBackground(objParSis.getColorCamposObligatorios());
//            txtPwd.setBackground(objParSis.getColorCamposObligatorios());
//            txtCodGrpUsr.setBackground(objParSis.getColorCamposObligatorios());
//            txtGrpUsr.setBackground(objParSis.getColorCamposObligatorios());
//            
//            txtDir.setBackground(objParSis.getColorCamposSistema());
//            txtTel.setBackground(objParSis.getColorCamposSistema());
//            txtCorEle.setBackground(objParSis.getColorCamposSistema());
            
            //Configurar las ZafVenCon.
            //configurarVenConUsr();
            //configurarVenConTra();
            //configurarVenConGrpUsr();
            //configurarComboEstCiv();
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
     private boolean configurarVenConUsr() //bostel Para que en un tipo de usuario salgan solo activos y enn otro todos
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            arlAli.add("Estado Usuario");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT co_usr, tx_usr, tx_nom, tx_ced, st_Reg";
            strSQL+=" FROM tbm_usr";
            strSQL+=" ORDER BY tx_nom";
            
            vcoUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUsr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
     private boolean configurarVenConUsrNuv()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            arlAli.add("Estado Usuario");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT co_usr, tx_usr, tx_nom, tx_ced, st_Reg";
            strSQL+=" FROM tbm_usr";
            strSQL+=" WHERE st_reg in ('A','I')";
            strSQL+=" ORDER BY tx_nom";
            
            vcoUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUsr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
     
      private boolean configurarVenConUsrCop()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            arlAli.add("Estado Usuario");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT co_usr, tx_usr, tx_nom, tx_ced, st_Reg";
            strSQL+=" FROM tbm_usr";
            strSQL+=" WHERE st_reg = 'A'";
            strSQL+=" ORDER BY tx_nom";
            
            vcoUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUsr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
     
     /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentá ningún problema.
     * <BR>false: En el caso contrario.
     */    
//    private boolean mostrarVenConUsr(int intTipBus)  
//    {
//        boolean blnRes=true;
//        try
//        {
//            switch (intTipBus)
//            {
//                case 0: //Mostrar la ventana de consulta.
//                    vcoUsr.setCampoBusqueda(1);
//                    vcoUsr.setVisible(true);
//                   if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                    {
//                        //txtCodUsr.setText(vcoUsr.getValueAt(1));
//                        txtCodPar.setText(vcoUsr.getValueAt(1));
//                        //txtUsr.setText(vcoUsr.getValueAt(2));
//                        //txtNom.setText(vcoUsr.getValueAt(3));
//                        //txtNomNuevUsr.setText(vcoUsr.getValueAt(3));
//                    }
//                    break;
//                case 1: //Búsqueda directa por "Codigo de Usuario".
//                    if (vcoUsr.buscar("a1.co_usr", txtCodUsr.getText()))
//                    {
//                        txtCodUsr.setText(vcoUsr.getValueAt(1));
//                        //txtCodNueUsr.setText(vcoUsr.getValueAt(1));
//                        txtUsr.setText(vcoUsr.getValueAt(2));
//                        txtNom.setText(vcoUsr.getValueAt(3));
//                        //txtNomNuevUsr.setText(vcoUsr.getValueAt(3));
//                    }
//                    else
//                    {
//                        vcoUsr.setCampoBusqueda(0);
//                        vcoUsr.setCriterio1(11);
//                        vcoUsr.cargarDatos();
//                        vcoUsr.setVisible(true);
//                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                        txtCodUsr.setText(vcoUsr.getValueAt(1));
//                        //txtCodNueUsr.setText(vcoUsr.getValueAt(1));
//                        txtUsr.setText(vcoUsr.getValueAt(2));
//                        txtNom.setText(vcoUsr.getValueAt(3));
//                        //txtNomNuevUsr.setText(vcoUsr.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtCodUsr.setText(strCodUsr);
//                        }
//                    }
//                    break;
//               case 2: //Búsqueda directa por "Nombre".
//                    if (vcoUsr.buscar("a1.tx_nom", txtNomPar.getText()))
//                    {
//                       // txtCodNueUsr.setText(vcoUsr.getValueAt(1));
//                       txtCodUsr.setText(vcoUsr.getValueAt(1));
//                       txtUsr.setText(vcoUsr.getValueAt(2));
//                       txtNom.setText(vcoUsr.getValueAt(3));
//                    }
//                    else
//                    {
//                        vcoUsr.setCampoBusqueda(1);
//                        vcoUsr.setCriterio1(11);
//                        vcoUsr.cargarDatos();
//                        vcoUsr.setVisible(true);
//                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                           // txtCodNueUsr.setText(vcoUsr.getValueAt(1));
//                          //  txtNomNuevUsr.setText(vcoUsr.getValueAt(3));
//                            txtCodUsr.setText(vcoUsr.getValueAt(1));
//                            txtUsr.setText(vcoUsr.getValueAt(2));
//                            txtNom.setText(vcoUsr.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtUsr.setText(strUsr);
//                        }
//                    }
//                    break;
//            }
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
    private boolean mostrarVenConUsrNuv(int intTipBus)  
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsr.setCampoBusqueda(1);
                    vcoUsr.setVisible(true);
                   if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodPar.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomPar.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Usuario".
                    if (vcoUsr.buscar("a1.co_usr", txtCodPar.getText()))
                    {
                         //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodPar.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomPar.setText(vcoUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoUsr.setCampoBusqueda(0);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.setVisible(true);
                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                        //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodPar.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomPar.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtCodPar.setText(strCodUsr);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Nombre".
                   
                    //if (!vcoUsr.buscar("a1.tx_nom", "%"+bus+"%"))
                    if (vcoUsr.buscar("a1.tx_nom", txtNomPar.getText()))
                    {
                       //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodPar.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomPar.setText(vcoUsr.getValueAt(3));
//                        txtCodNueUsr.setText(vcoUsr.getValueAt(1));
//                        txtCodUsr.setText(vcoUsr.getValueAt(1));
//                        txtUsr.setText(vcoUsr.getValueAt(2));
//                        txtNom.setText(vcoUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoUsr.setCampoBusqueda(2);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.setVisible(true);
                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                             txtCodPar.setText(vcoUsr.getValueAt(1));
                             txtNomPar.setText(vcoUsr.getValueAt(3));
//                            txtCodUsr.setText(vcoUsr.getValueAt(1));
//                            txtUsr.setText(vcoUsr.getValueAt(2));
//                            txtNom.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtCodPar.setText(strUsr);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     private boolean mostrarVenConUsrCop(int intTipBus)  
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsr.setCampoBusqueda(1);
                    vcoUsr.setVisible(true);
                   if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodDe.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomDe.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Usuario".
                    if (vcoUsr.buscar("a1.co_usr", txtCodDe.getText()))
                    {
                        //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodDe.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomDe.setText(vcoUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoUsr.setCampoBusqueda(0);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.setVisible(true);
                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                          //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodDe.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomDe.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtCodDe.setText(strCodUsr);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Nombre".
                    if (vcoUsr.buscar("a1.tx_nom", txtNomDe.getText()))
                    {
                        //txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtCodDe.setText(vcoUsr.getValueAt(1));
                        //txtUsr.setText(vcoUsr.getValueAt(2));
                        //txtNom.setText(vcoUsr.getValueAt(3));
                        txtNomDe.setText(vcoUsr.getValueAt(3));
//                        txtCodNueUsr.setText(vcoUsr.getValueAt(1));
//                        txtCodUsr.setText(vcoUsr.getValueAt(1));
//                        txtUsr.setText(vcoUsr.getValueAt(2));
//                        txtNom.setText(vcoUsr.getValueAt(3));
                    }
                    else
                    {
                        vcoUsr.setCampoBusqueda(2);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.setVisible(true);
                        if (vcoUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDe.setText(vcoUsr.getValueAt(1));
                            txtNomDe.setText(vcoUsr.getValueAt(3));
//                            txtCodUsr.setText(vcoUsr.getValueAt(1));
//                            txtUsr.setText(vcoUsr.getValueAt(2));
//                            txtNom.setText(vcoUsr.getValueAt(3));
                        }
                        else
                        {
                            txtCodDe.setText(strUsr);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Grupos de Usuarios".
     */        
    private boolean configurarVenConGrpUsr()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_grp");
            arlCam.add("a1.tx_descor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Grupo Usuario");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT co_grp, tx_descor, tx_desLar FROM tbm_grpUsr ";
            strSQL+=" WHERE st_Reg='A' ";
            strSQL+=" ORDER BY co_grp ";
            vcoGrpUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Grupos de Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoGrpUsr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
//    private boolean mostrarVenConGrpUsr(int intTipBus)  
//    {
//        boolean blnRes=true;
//        try
//        {
//            switch (intTipBus)
//            {
//                case 0: //Mostrar la ventana de consulta.
//                    vcoGrpUsr.setCampoBusqueda(0);
//                    vcoGrpUsr.setVisible(true);
//                   if (vcoGrpUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                    {
//                        txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
//                        txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
//                    }
//                    break;
//                case 1: //Búsqueda directa por "Codigo de Grupo Usuario".
//                    if (vcoGrpUsr.buscar("a1.co_grp", txtCodGrpUsr.getText()))
//                    {
//                        txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
//                        txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
//                    }
//                    else
//                    {
//                        vcoGrpUsr.setCampoBusqueda(1);
//                        vcoGrpUsr.setCriterio1(11);
//                        vcoGrpUsr.cargarDatos();
//                        vcoGrpUsr.setVisible(true);
//                        if (vcoGrpUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
//                            txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtCodGrpUsr.setText(strCodGrpUsr);
//                        }
//                    }
//                    break; 
//               case 2: //Búsqueda directa por "Grupo Usuario".
//                    if (vcoGrpUsr.buscar("a1.tx_desLar", txtGrpUsr.getText()))
//                    {
//                        txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
//                        txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
//                    }
//                    else
//                    {
//                        vcoGrpUsr.setCampoBusqueda(2);
//                        vcoGrpUsr.setCriterio1(11);
//                        vcoGrpUsr.cargarDatos();
//                        vcoGrpUsr.setVisible(true);
//                        if (vcoGrpUsr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            txtCodGrpUsr.setText(vcoGrpUsr.getValueAt(1));
//                            txtGrpUsr.setText(vcoGrpUsr.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtGrpUsr.setText(strGrpUsr);
//                        }
//                    }
//                    break;
//            }     
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }  
    
//    private boolean configurarComboEstCiv() {
//        boolean blnRes = true;
//        try 
//        {
//            //Inicializando el vector del Estado Civil
//            vecEstCiv = new Vector();
//            strSQL="SELECT co_reg, tx_desLar, co_grp FROM tbm_var WHERE co_grp =1 AND st_reg='A' ORDER BY co_reg";
//            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, cboEstCiv, vecEstCiv);
//            cboEstCiv.setSelectedIndex(0);
//        } 
//        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
//        return blnRes;
//    }       
    
      private boolean configurarComboEmpresa() {
        boolean blnRes = true;
        try 
        {
            //Inicializando el vector del Empresa 
            
            vecEmp = new Vector();
            strSQL="SELECT co_emp, tx_nom FROM tbm_emp WHERE  st_reg='A' ORDER BY co_emp";
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, cboEmp, vecEmp);
            //cboEmp.setSelectedIndex(-1);
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }  
      
            private boolean configurarComboLocales(int co_Emp) { //bostel
        boolean blnRes = true;
        try 
        {
            //Inicializando el vector del Locales 
            
            vecLoc = new Vector();
            strSQL="SELECT co_emp, tx_nom FROM tbm_loc WHERE co_emp ='"+co_Emp+"' ORDER BY co_emp";
            objUti.llenarCbo_F1(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL, cboLoc, vecLoc);
            //cboEmp.setSelectedIndex(-1);
        } 
        catch (Exception e) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }  
    
       /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            //txtCodUsr.setText("");
            //txtCed.setText("");
            //txtNom.setText("");
            //txtUsr.setText("");
            //txtPwd.setText("");
            //txtDir.setText("");
            //txtTel.setText("");
            //txtCorEle.setText("");
            //txtCodGrpUsr.setText("");
            //txtGrpUsr.setText("");
            txtNomPar.setText("");
            txtCodPar.setText("");
            txtCodDe.setText("");
            txtNomDe.setText("");
            //cboSex.setSelectedIndex(0);
            //cboEstCiv.setSelectedIndex(0);
            //txaObs1.setText("");
            //txaObs2.setText("");
            //tabFrm.setSelectedIndex(0);        
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }  
    
     /**
     * Función que permite obtener los filtros de búsquedas
     * @return 
     */
//    private String sqlFil() 
//    {
//        String strFil= "";
//        //Filtro: Por Código de Usuario
////        if (!txtCodNueUsr.getText().equals("") ) {
////            //Cuando se ingresa el código de usuario, no se realiza búsqueda por demás filtros.
////            strFil+=" AND a.co_usr=" + txtCodNueUsr.getText().replaceAll("'", "''") + "";
////        }  
////        else 
//        if ( !txtCodUsr.getText().equals("")){
//            strFil+=" AND a.co_usr=" + txtCodUsr.getText().replaceAll("'", "''") + "";
////        }else if (!txtCodUsrCop.getText().equals("")){
////            strFil+=" AND a.co_usr=" + txtCodUsrCop.getText().replaceAll("'", "''") + "";
//        }
//        else //Si no existe código de usuario ingresado, se busca por los demás filtros.
//        {
//            //Filtro: Por Cédula
//            if (!txtCed.getText().equals("")) {
//                strFil+=" AND tx_ced LIKE '%" + txtCed.getText().trim() + "%'";
//            }
//            //Filtro: Por código de Grupo de Usuario
//            if (!txtCodGrpUsr.getText().equals("")) {
//                strFil+=" AND co_grpUsr = " + txtCodGrpUsr.getText()+ "";
//            }
//            //Filtro: Por User
//            if (!txtUsr.getText().equals("")) {
//                strFil+=" AND LOWER(tx_usr) LIKE "+objUti.codificar(txtUsr.getText().trim().toLowerCase())+"";
//            }
//            //Filtro: Por Nombre
//            /*if (!txtNom.getText().equals("")) {
//                strFil+=" AND LOWER(tx_nom) LIKE '" + txtNom.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
//            }
//            //Filtro: Por Dirección
//            if (!txtDir.getText().equals("")) {
//                strFil+=" AND LOWER(tx_dir) LIKE '" + txtDir.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
//            }
//            //Filtro: Por Teléfono
//            if (!txtTel.getText().equals("")) {
//                strFil+=" AND tx_tel LIKE '" + txtTel.getText().replaceAll("'", "''") + "%'";
//            }
//            //Filtro: Por Correo Electrónico 
//            if (!txtCorEle.getText().equals("")) {
//                strFil+=" AND LOWER(tx_corEle) LIKE '" + txtCorEle.getText().replaceAll("'", "''").replace('*', '%').toLowerCase() + "' ";
//            }   */         
//        }
//        return strFil;
//    }
    
//     private boolean consultarReg(String strFil)
//    {
//        boolean blnRes=true;
//        try
//        {
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null)
//            {
//                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//                //Armar sentencia SQL
//                strSQL ="";
//                strSQL+=" SELECT a.co_usr " ;                                                                           
//                strSQL+=" FROM tbm_usr as a ";
//                strSQL+=" WHERE a.st_reg NOT IN('E')";     
//                strSQL+=" "+strFil;     //Filtro de Búsqueda        
//                strSQL+=" ORDER BY a.co_usr";                
//                rst=stm.executeQuery(strSQL);
//                arlDatConUsr = new ArrayList();
//                while(rst.next())
//                {
//                    arlRegConUsr = new ArrayList();
//                    arlRegConUsr.add(INT_ARL_CON_USR_COD_USR,   rst.getInt("co_usr"));
//                    arlDatConUsr.add(arlRegConUsr);
//                }                
//                stm.close();
//                stm=null;
//                rst.close();
//                rst=null;
//                con.close();
//                con=null;                
//
//                if(arlDatConUsr.size()>0){
//                    objTooBar.setMenSis("Se encontraron " + (arlDatConUsr.size()) + " registros");
//                    intIndiceUsr=arlDatConUsr.size()-1;
//                    cargarReg();
//                }
//                else{
//                    //mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
//                    //limpiarFrm();
//                    objTooBar.setEstado('n');
//                    objTooBar.setMenSis("Listo");
//                }
//            }
//        }
//        catch (java.sql.SQLException e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
     
     private boolean consultarRegNue(String strFil)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_usr " ;                                                                           
                strSQL+=" FROM tbm_usr as a ";
                strSQL+=" WHERE a.st_reg NOT IN('E')";     
                strSQL+=" "+strFil;     //Filtro de Búsqueda        
                strSQL+=" ORDER BY a.co_usr";                
                rst=stm.executeQuery(strSQL);
                arlDatConUsr = new ArrayList();
                while(rst.next())
                {
                    arlRegConUsr = new ArrayList();
                    arlRegConUsr.add(INT_ARL_CON_USR_COD_USR,   rst.getInt("co_usr"));
                    arlDatConUsr.add(arlRegConUsr);
                }                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;                

                if(arlDatConUsr.size()>0){
                    //objTooBar.setMenSis("Se encontraron " + (arlDatConUsr.size()) + " registros");
                    intIndiceUsr=arlDatConUsr.size()-1;
                    cargarRegNue();
                }
                else{
                    //mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    //limpiarFrm();
                    //objTooBar.setEstado('n');
                    //objTooBar.setMenSis("Listo");
                    mostrarMsgInf("No se ha Encontrado Ningun Registro");
                    txtCodPar.setText("");
                    txtNomPar.setText("");
                }
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
      private boolean consultarRegCop(String strFil)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_usr " ;                                                                           
                strSQL+=" FROM tbm_usr as a ";
                strSQL+=" WHERE a.st_reg NOT IN('E')";     
                strSQL+=" "+strFil;     //Filtro de Búsqueda        
                strSQL+=" ORDER BY a.co_usr";                
                rst=stm.executeQuery(strSQL);
                arlDatConUsr = new ArrayList();
                while(rst.next())
                {
                    arlRegConUsr = new ArrayList();
                    arlRegConUsr.add(INT_ARL_CON_USR_COD_USR,   rst.getInt("co_usr"));
                    arlDatConUsr.add(arlRegConUsr);
                }                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;                

                if(arlDatConUsr.size()>0){
                    //objTooBar.setMenSis("Se encontraron " + (arlDatConUsr.size()) + " registros");
                    intIndiceUsr=arlDatConUsr.size()-1;
                    cargarRegCop();
                }
                else{
                    //mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    //limpiarFrm();
                    //objTooBar.setEstado('n');
                    //objTooBar.setMenSis("Listo");
                    mostrarMsgInf("No se ha Encontrado Ningun Registro");
                    txtCodDe.setText("");
                    txtNomDe.setText("");
                }
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
//     private boolean cargarReg(){
//        boolean blnRes=false;
//        try{
//            if (cargarTabGen()){
//               // if (cargarTabEmpUsr()){
//                    //if (cargarTabLocUsr()){
//                        //if (cargarTabBodUsr()){
//                            blnRes=true;
//                       // }
//                    //}
//                //}
//            }  
//            blnHayCam=false;
//        }
//        catch (Exception e){
//            blnRes=false;
//        }
//        return blnRes;
//    }
     
      private boolean cargarRegNue(){
        boolean blnRes=false;
        try{
            if (cargarTabGenNue()){
               // if (cargarTabEmpUsr()){
                    //if (cargarTabLocUsr()){
                        //if (cargarTabBodUsr()){
                            blnRes=true;
                       // }
                    //}
                //}
            }  
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
      
       private boolean cargarRegCop(){
        boolean blnRes=false;
        try{
            if (cargarTabGenCop()){
               // if (cargarTabEmpUsr()){
                    //if (cargarTabLocUsr()){
                        //if (cargarTabBodUsr()){
                            blnRes=true;
                       // }
                    //}
                //}
            }  
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
     
       /**
     * Esta función permite `gar el Tab "General"
     * @return true: Si se pudo cargar el tab.
     * <BR>false: En el caso contrario.
     */
//    private boolean cargarTabGen(){
//        int intPosRel;
//        boolean blnRes=true;
//        try{
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null){
//                stm=con.createStatement();
//                strSQL="";
//                strSQL+=" SELECT a1.*, a2.co_grp as co_grpUsr, a2.tx_desLar as tx_nomGrpUsr";
//                strSQL+=" FROM tbm_usr as a1";
//                strSQL+=" INNER JOIN tbm_grpUsr as a2 ON a1.co_grpUsr=a2.co_grp";
//                strSQL+=" WHERE a1.co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR);
//                rst=stm.executeQuery(strSQL);
//                if (rst.next())
//                {
//                    strAux=rst.getString("co_usr");
//                    txtCodUsr.setText((strAux==null)?"":strAux);
//                    strAux=rst.getString("tx_ced");
//                    txtCed.setText((strAux==null)?"":strAux);
//                    strAux=rst.getString("tx_nom");
//                    txtNom.setText((strAux==null)?"":strAux);
//                    strAux=rst.getString("tx_dir");
//                    txtDir.setText((strAux==null)?"":strAux);    
//                    strAux=rst.getString("tx_tel");
//                    txtTel.setText((strAux==null)?"":strAux);                        
//                    strAux=rst.getString("tx_usr");
//                    txtUsr.setText((strAux==null)?"":strAux);                       
//                    strAux=rst.getString("tx_pwd");
//                    txtPwd.setText((strAux==null)?"":strAux);     
//                    strAux=rst.getString("tx_corEle");
//                    txtCorEle.setText((strAux==null)?"":strAux);                         
//                    strAux=rst.getString("tx_obs1");
//                    txaObs1.setText((strAux==null)?"":strAux);    
//                    strAux=rst.getString("co_grpUsr");
//                    txtCodGrpUsr.setText((strAux==null)?"":strAux);       
//                    strAux=rst.getString("tx_nomGrpUsr");
//                    txtGrpUsr.setText((strAux==null)?"":strAux);                          
//                    
//                    objUti.setItemCombo(cboEstCiv, vecEstCiv, rst.getString("co_estCiv"));
//                    
//                    strAux=(rst.getObject("tx_sex")==null?"N":rst.getString("tx_sex"));
//                    cboSex.setSelectedIndex((strAux.equals("M")?1:(strAux.equals("F")?2:0)));
//
//                    //Mostrar el estado del registro.
//                    strAux=rst.getString("st_reg");
//                    if (strAux.equals("A")){
//                        strAux="Activo";
//                        cboEstReg.setSelectedIndex(0);
//                    }
//                    else if (strAux.equals("I")){
//                        strAux="Anulado";
//                        cboEstReg.setSelectedIndex(1);
//                    }
//                    else{
//                        strAux="Otro";
//                        cboEstReg.setSelectedIndex(0);
//                    }
//                    //objTooBar.setEstadoRegistro(strAux);
//                }
//                else{
//                    objTooBar.setEstadoRegistro("Eliminado");
//                    limpiarFrm();
//                    blnRes=false;
//                }
//            }
//            rst.close();
//            stm.close();
//            con.close();
//            rst=null;
//            stm=null;
//            con=null;
//            //Mostrar la posición relativa del registro.
//            intPosRel = intIndiceUsr+1;
//            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConUsr.size()) );
//        }
//        catch (java.sql.SQLException e){
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e){
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }     
    
    private boolean cargarTabGenNue(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.*, a2.co_grp as co_grpUsr, a2.tx_desLar as tx_nomGrpUsr";
                strSQL+=" FROM tbm_usr as a1";
                strSQL+=" INNER JOIN tbm_grpUsr as a2 ON a1.co_grpUsr=a2.co_grp";
                strSQL+=" WHERE a1.co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   
                    strAux=rst.getString("co_usr");
                    txtCodPar.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nom");
                    txtNomPar.setText((strAux==null)?"":strAux);
                   
                   
                   
                   // objTooBar.setEstadoRegistro(strAux);
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceUsr+1;
            //objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConUsr.size()) );
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarTabGenCop(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.*, a2.co_grp as co_grpUsr, a2.tx_desLar as tx_nomGrpUsr";
                strSQL+=" FROM tbm_usr as a1";
                strSQL+=" INNER JOIN tbm_grpUsr as a2 ON a1.co_grpUsr=a2.co_grp";
                strSQL+=" WHERE a1.co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   
                    strAux=rst.getString("co_usr");
                    txtCodDe.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nom");
                    txtNomDe.setText((strAux==null)?"":strAux);
                   
                   
                   
                    //objTooBar.setEstadoRegistro(strAux);
                }
                else{
                    //objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceUsr+1;
            //objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConUsr.size()) );
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private int CodEmp(){ //bostel
        int intPosRel;
        int blnRes = 10;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                if(cboEmp.getSelectedIndex() != -1){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_emp";
                strSQL+=" FROM tbm_emp as a1";
                strSQL+=" WHERE a1.st_reg like 'A' and tx_nom like '%"+cboEmp.getSelectedItem()+"%'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   
                    strAux=rst.getString("co_emp");
                    blnRes=Integer.parseInt(strAux);
                }
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            //intPosRel = intIndiceUsr+1;
            //objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConUsr.size()) );
        }
        catch (java.sql.SQLException e){
            //blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
           // blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private int CodLoc(){ //bostel
        int intPosRel;
        int blnRes = 10;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                if(cboEmp.getSelectedIndex() != -1){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_loc";
                strSQL+=" FROM tbm_loc as a1";
                strSQL+=" WHERE tx_nom like '%"+cboLoc.getSelectedItem()+"%'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   
                    strAux=rst.getString("co_loc");
                    blnRes=Integer.parseInt(strAux);System.out.println(blnRes);
                }
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            //intPosRel = intIndiceUsr+1;
            //objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConUsr.size()) );
        }
        catch (java.sql.SQLException e){
            //blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
           // blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
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
    
     private int mostrarMsgCon(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
     /**
     * Esta clase implementa la interface DocumentListener que observa los
     * cambios que se presentan en los objetos de tipo texto. Por ejemplo:
     * JTextField, JTextArea, etc. Se la usa en el sistema para determinar si
     * existe algún cambio que se deba grabar antes de abandonar uno de los
     * modos o desplazarse a otro registro. Por ejemplo: si se ha hecho cambios
     * a un registro y quiere cancelar o moverse a otro registro se presentará
     * un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }
    
     /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
//    private void agregarDocLis() 
//    {
//        txtCodUsr.getDocument().addDocumentListener(objDocLis);
//        txtUsr.getDocument().addDocumentListener(objDocLis);
//        txtPwd.getDocument().addDocumentListener(objDocLis);
//        txtCed.getDocument().addDocumentListener(objDocLis);
//        txtNom.getDocument().addDocumentListener(objDocLis);
//        txtDir.getDocument().addDocumentListener(objDocLis);
//        txtTel.getDocument().addDocumentListener(objDocLis);
//        txtCorEle.getDocument().addDocumentListener(objDocLis);
//        txaObs1.getDocument().addDocumentListener(objDocLis);
//        txtCodGrpUsr.getDocument().addDocumentListener(objDocLis);
//        txtGrpUsr.getDocument().addDocumentListener(objDocLis);
//    }
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            if(objTooBar.insertar()){  
                                if(objTooBar.afterInsertar()){  
                                    blnRes=true;
                                }
                            }                      
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            if(objTooBar.modificar()){  
                                if(objTooBar.afterModificar()){  
                                    blnRes=true;
                                }
                            }                      
                        } 
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    public class MiToolBar extends ZafToolBar {

        public MiToolBar(javax.swing.JInternalFrame ifrTmp) {
            super(ifrTmp, objParSis);
        }
        


        public void clickInicio() {
            try{
//                if(arlDatConUsr.size()>0){
//                    if(intIndiceUsr>0){
//                        if (blnHayCam) {
//                            if (isRegPro()) {
//                                intIndiceUsr=0;
//                                cargarReg();
//                            }
//                        }
//                        else {
//                            intIndiceUsr=0;
//                            cargarReg();
//                        }
//                    }
//                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }
        
        public void clickAnterior() {
            try{
//                if(arlDatConUsr.size()>0){
//                    if(intIndiceUsr>0){
//                        if (blnHayCam){
//                            if (isRegPro()) {
//                                intIndiceUsr--;
//                                cargarReg();
//                            }
//                        }
//                        else {
//                            intIndiceUsr--;
//                            cargarReg();
//                        }
//                    }
//                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }                        
        }

        public void clickSiguiente() {
            try{
//                if(arlDatConUsr.size()>0){
//                    if(intIndiceUsr < arlDatConUsr.size()-1){
//                        if (blnHayCam ){
//                            if (isRegPro()) {
//                                intIndiceUsr++;
//                                cargarReg();
//                            }
//                        }
//                        else {
//                            intIndiceUsr++;
//                            cargarReg();
//                        }
//                    }
//                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }              
        }     
        
        public void clickFin() {
            try{
//                if(arlDatConUsr.size()>0){
//                    if(intIndiceUsr<arlDatConUsr.size()-1){
//                        if (blnHayCam) {
//                            if (isRegPro()) {
//                                intIndiceUsr=arlDatConUsr.size()-1;
//                                cargarReg();
//                            }
//                        }
//                        else {
//                            intIndiceUsr=arlDatConUsr.size()-1;
//                            cargarReg();
//                        }
//                    }
//                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            try {
                if (blnHayCam)  {
                    isRegPro();
                }
                //limpiarFrm();

                blnHayCam = false;
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {
//            txtCodUsr.setEditable(true);
//            txtCodUsr.setEnabled(true);
//            txtUsr.setEnabled(true);
            
      //configurarComboEmpresa(); cargar combo de Empresas Bostel
            blnHayCam=false;
        }

        public void clickModificar() {
//            txtCodUsr.setEnabled(false);
//            butCodUsr.setEnabled(false);
//            txtCed.setEnabled(false);
//            txtUsr.setEnabled(false);
//            txtPwd.setEnabled(false);
//            txtNom.requestFocus();
            blnHayCam=false;
        }
        public void clickEliminar() {
            //consultarReg(sqlFil());
            eliminarPer();
        }
        public void clickAceptar() {
        }

        public void clickAnular() {
        }

        public void clickCancelar() {
        }        

        public void clickImprimir() {
        }        

        public void clickVisPreliminar() {
        }        
        
        public boolean beforeInsertar() {
            //if (!isCamVal())
               // return false;
                  
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            //if (!isCamVal())
                //return false;
            
            return true;            
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }  
        
        public boolean consultar() {
            //consultarReg(sqlFil());
            CopiarPer();
            return true;
        }          

        public boolean insertar() {
//            if (!insertarReg())
//                return false;
            //blnHayCam = false;
            return true;
        }

        public boolean modificar() {
//            if (!actualizarReg())
//                return false;
//            blnHayCam = false;
            return true;
        }
         
        public boolean anular()
        {
//            if (!anularReg())
//                return false;
//            objTooBar.setEstadoRegistro("Anulado");
//            blnHayCam=false;
            return true;
        }        

        public boolean cancelar() {
            boolean blnRes=true;
            try
            {
                if (blnHayCam)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
        
        public boolean eliminar() {
            try
            {
              eliminarPer();  
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return true;            
        }        

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }  
        
        public boolean aceptar() { //bostel
            return true;
        }        

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            return true;
        }

        public boolean afterModificar() {
//            blnHayCam = false;
//            objTooBar.setEstado('w');
//            objTooBar.afterConsultar();

            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

    }  
    
     /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
//    private boolean isCamVal()
//    {
//        try
//        {
//            if(objTooBar.getEstado()=='n'){
//                //Validar el "Cédula de Identidad".
//                if (txtCed.getText().trim().equals(""))
//                {
//                    tabFrm.setSelectedIndex(0);
//                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de Identidad</FONT> es obligatorio.<BR>Escriba o seleccione una identificación y vuelva a intentarlo.</HTML>");
//                    txtCed.requestFocus();
//                    return false;
//                } 
//                //Valida "Cédula de Identidad" debe tener 10 digitos.
//                if (txtCed.getText().trim().length() != 10)
//                {  
//                    tabFrm.setSelectedIndex(0);
//                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de identidad</FONT> debe contener 10 digitos.<BR>Verifique y vuelva a intentarlo.</HTML>");
//                    txtCed.requestFocus();
//                    return false;
//                } 
//                //Valida "Cédula de Identidad" solo valores numéricos.
//                if (objUti.isNumero(txtCed.getText().trim()) == false) {
//                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de identidad</FONT> solo acepta valores numéricos.<BR>Digite caracteres válidos y vuelva a intentarlo.</HTML>");
//                    txtCed.selectAll();
//                    txtCed.requestFocus();
//                    return false;
//                }
//                //Valida "Cédula de Identidad" si ya existe usuario creado con la misma cédula
//                if (!validaExiIdeUsr()) {
//                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cedula de identidad</FONT> ya existe en el sistema.<BR>Verifique y vuelva a intentarlo.</HTML>");
//                    txtCed.selectAll();
//                    txtCed.requestFocus();
//                    return false;
//                }    
//                //Valida "Usuario" si ya existe usuario creado con el mismo user
//                if (!validaExiTxtUsr()) {
//                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">User</FONT> ya existe en el sistema.<BR>Verifique que no exista un usuario con el mismo user y vuelva a intentarlo.</HTML>");
//                    txtCed.selectAll();
//                    txtCed.requestFocus();
//                    return false;
//                }                   
//            }
//            
//            //Validar "Nombre del usuario"
//            if (txtNom.getText().trim().equals("")) {
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre</FONT> es obligatorio.<BR>Escriba un nombre y vuelva a intentarlo.</HTML>");
//                txtNom.requestFocus();
//                return false;
//            }            
//            
//            //Validar el "Usuario".
//            if (txtUsr.getText().trim().equals(""))
//            {
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">User</FONT> es obligatorio.<BR>Escriba un usuario y vuelva a intentarlo.</HTML>");
//                txtUsr.requestFocus();
//                return false;
//            }
//            
//            //Validar el "PassWord".
//            if (txtPwd.getText().trim().equals(""))
//            {
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"PassWord\">User</FONT> es obligatorio.<BR>Escriba un PassWord y vuelva a intentarlo.</HTML>");
//                txtPwd.requestFocus();
//                return false;
//            }
//            
//            //Validar el "Código de Grupo de Usuario".
//            if (txtCodGrpUsr.getText().trim().equals(""))
//            {
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Grupo de Usuario</FONT> es obligatorio.<BR>Asigne un grupo al usuario y vuelva a intentarlo.</HTML>");
//                txtCodGrpUsr.requestFocus();
//                return false;
//            }            
//            
//            //Valida si el usuario está inactivo, cuando se desee modificar
//            if (objTooBar.getEstadoRegistro().equals("Anulado") && objTooBar.getEstado()=='m') {
//                if (mostrarMsgYesNo("Para modificar primero debe reactivar el usuario \n ¿Desea reactivarlo?")==0) {
//                    return reactivarUsr();
//                }
//            }
//        }
//        catch(Exception Evt)
//        {  
//           return false;
//        }
//        return true;
//    }
   
       /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
//    private boolean insertarReg(){
//        boolean blnRes = false;
//        try{
//             //Obtener la fecha del servidor.
//            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//            if (datFecAux==null)
//                return false;   
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            con.setAutoCommit(false);
//            if (con!=null){
//                if (insertarTabGen()){
////                    if (insertarTabEmpUsr()){
//                        //if (insertarTabLocUsr()){
//                            //if (insertarTabBodUsr()){
//                                con.commit();
//                                blnRes=true; 
//                            }
////                            else
////                                con.rollback();  
////                        }
////                        else
////                            con.rollback();                            
////                    }
////                    else
////                        con.rollback();
////                }
//                else
//                    con.rollback();
//            }
//            con.close();
//            con=null;            
//        }
//        catch (java.sql.SQLException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }    
    
     /**
     * Esta función permite insertar datos del Tab "General"
     * @return true: Si se pudo insertar datos.
     * <BR>false: En el caso contrario.
     */
//    private boolean insertarTabGen(){
//        boolean blnRes=true;
//        try{
//            if (con!=null){
//                stm=con.createStatement();
//             
//                //Armar sentencia SQL
//                stbSQL=new StringBuffer();
//                stbSQL.append(" INSERT INTO tbm_usr(co_usr, tx_usr, tx_pwd, tx_pwd1, tx_pwd2, st_usrSis, co_grpUsr"
//                             +" , tx_ced, tx_nom, tx_dir, tx_tel, tx_corEle, tx_sex, co_estCiv, tx_obs1, tx_obs2"
//                             +" , st_reg, fe_ing, fe_ultMod, st_regRep, ne_diaProActCla, fe_proActCla, tx_comUltActCla"
//                             +" , st_usrIngCuaEqu, tx_tipIdeUtiIngSisEsc, tx_tipIdeUtiIngSisWeb)"
//                             +" VALUES ("
//                             +" (SELECT CASE WHEN MAX (co_usr) IS NULL THEN 1 ELSE MAX(co_usr)+1 END AS co_usr FROM tbm_usr) " //co_usr
//                             +" , " + objUti.codificar(txtUsr.getText().trim()) + ""       //tx_usr
//                             +" , MD5(" + objUti.codificar(txtPwd.getText().trim()) + ") " //tx_pwd
//                             +" , Null " //tx_pwd1
//                             +" , Null " //tx_pwd2
//                             +" , 'S' "  //st_usrSis
//                             +" , " + txtCodGrpUsr.getText() + ""  //co_grpUsr
//                             +" , " + objUti.codificar(txtCed.getText().trim()) + ""      //tx_ced
//                             +" , " + objUti.codificar(txtNom.getText().trim()) + ""      //tx_nom
//                             +" , " + objUti.codificar(txtDir.getText().trim()) + ""      //tx_dir
//                             +" , " + objUti.codificar(txtTel.getText().trim()) + ""      //tx_tel
//                             +" , " + objUti.codificar(txtCorEle.getText().trim()) + ""   //txtCorEle
//                             +" , " + objUti.codificar((cboSex.getSelectedIndex()==1?"M":(cboSex.getSelectedIndex()==2?"F":"N")))+"" //tx_sex
//                             +" , " + Integer.parseInt(vecEstCiv.get(cboEstCiv.getSelectedIndex()).toString())+"" //co_estCiv
//                             +" , " + objUti.codificar(txaObs1.getText().trim()) + ""        //tx_obs1
//                             +" , " + objUti.codificar(txaObs2.getText().trim()) + ""        //tx_obs2
//                             +" , " + objUti.codificar((cboEstReg.getSelectedIndex()==0?"A":"I"))+"" //st_reg
//                             +" , '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'" //fe_ing                                     
//                             +" , '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'" //fe_ultMod 
//                             +" , 'P' "  //st_regRep
//                             +" , 30  "  //ne_diaProActCla
//                             +" , Null"  //fe_proActCla                                     
//                             +" , Null"  //tx_comUltActCla     
//                             +" , 'N' "  //st_usrIngCuaEqu
//                             +" , 'M' "  //tx_tipIdeUtiIngSisEsc
//                             +" , 'I' "  //tx_tipIdeUtiIngSisWeb
//                             +" ); \n");           
//                             
//               
//                stm.executeUpdate(stbSQL.toString());
//                stm.close();
//                stm=null;
//                stbSQL=null;
//            }
//        }
//        catch (java.sql.SQLException e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    } 
    
//     private boolean actualizarReg(){
//        boolean blnRes = false;
//        try{
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            con.setAutoCommit(false);
//            if (con!=null){
//                if (actualizarTabGen()){
//                    //if (actualizarTabEmpUsr()){
//                        //if (actualizarTabLocUsr()){
//                            //if (actualizarTabBodUsr()){
//                                con.commit();
//                                blnRes=true;  
//                            }
////                            else
////                                con.rollback();
////                        }
////                        else
////                            con.rollback();
////                    }
////                    else
////                        con.rollback();
////                }
//                else
//                    con.rollback();
//            }
//            con.close();
//            con=null;            
//        }
//        catch (java.sql.SQLException e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e) {
//            blnRes = false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }    
     
     /**
     * Esta función permite actualizar datos del Tab "General"
     * @return true: Si se pudo actualizar datos.
     * <BR>false: En el caso contrario.
     */
//    private boolean actualizarTabGen(){
//        boolean blnRes=true;
//        try{
//            if (con!=null){
//                stm=con.createStatement();
//                //Obtener la fecha del servidor.
//                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//                if (datFecAux==null)
//                    return false;                
//                //Armar sentencia SQL
//                stbSQL=new StringBuffer();
//                stbSQL.append(" UPDATE tbm_usr SET "
//                             +"   tx_nom=" + objUti.codificar(txtNom.getText().trim()) + ""
//                             +" , co_grpUsr=" + txtCodGrpUsr.getText() + ""
//                             //+" , tx_ced=" + objUti.codificar(txtCed.getText().trim()) + ""
//                             //+" , tx_usr=" + objUti.codificar(txtUsr.getText().trim()) + ""
//                             +" , tx_dir=" + objUti.codificar(txtDir.getText().trim()) + "" 
//                             +" , tx_tel=" + objUti.codificar(txtTel.getText().trim()) + ""
//                             +" , tx_corEle=" + objUti.codificar(txtCorEle.getText().trim()) + ""
//                             +" , tx_sex=" + objUti.codificar((cboSex.getSelectedIndex()==1?"M":(cboSex.getSelectedIndex()==2?"F":"N")))+""
//                             +" , co_estCiv=" + Integer.parseInt(vecEstCiv.get(cboEstCiv.getSelectedIndex()).toString())+""
//                             +" , tx_obs1=" + objUti.codificar(txaObs1.getText().trim()) + "" 
//                             +" , tx_obs2=" + objUti.codificar(txaObs2.getText().trim()) + ""
//                             +" , st_reg=" + objUti.codificar((cboEstReg.getSelectedIndex()==0?"A":"I"))+""
//                             +" , fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'"
//                             +" , st_regRep='M'"
//                             //+" , st_usrIngCuaEqu='N'"
//                             //+" , tx_tipIdeUtiIngSisEsc='M'"
//                             //+" , tx_tipIdeUtiIngSisWeb='I'"
//                             +" WHERE co_usr="+txtCodUsr.getText()+""
//                             +" ; \n"); 
//                
//                if(chkEdiPwd.isSelected()){
//                    stbSQL.append(" UPDATE tbm_usr SET tx_pwd=MD5(" + objUti.codificar(txtPwd.getText().trim()) + ") "
//                                 //+" , tx_pwd1=MD5(" + objUti.codificar(txtPwd1.getText().trim()) + ") "
//                                 //+" , tx_pwd2=MD5(" + objUti.codificar(txtPwd2.getText().trim()) + ") "
//                                 //+" , ne_diaProActCla=30"
//                                 +" , fe_proActCla='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos()) + "'"
//                                 +" , tx_comUltActCla="+ objUti.codificar(objParSis.getNombreComputadoraConDirIP())+ ""
//                                 +" WHERE co_usr="+txtCodUsr.getText()+"; \n");
//                }
//                System.out.println("actualizarTabGen: " + stbSQL);
//                stm.executeUpdate(stbSQL.toString());
//                stm.close();
//                stm=null;
//                stbSQL=null;
//            }
//        }
//        catch (java.sql.SQLException e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    
     /**
     * Esta función permite eliminar datos
     * @return true: Si se pudo eliminar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Eliminar datos de usuario
                stbSQL.append(" DELETE FROM tbr_usrEmp WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
                //stbSQL.append(" DELETE FROM tbr_locUsr WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
                //stbSQL.append(" DELETE FROM tbr_bodUsr WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
                stbSQL.append(" DELETE FROM tbm_usr WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
                System.out.println("eliminarReg: " + stbSQL);
                stm.executeUpdate(stbSQL.toString());
                stm.close();
                stm=null;
                stbSQL=null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    /* Metodo para eliminar los permisos del usuario*/
    
     private boolean eliminarPer(){
        boolean blnRes=true;
        try{
             con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                if(!txtCodPar.getText().equals("") || txtNomPar.getText().equals("")){
                if(mostrarMsgYesNo("¿Está seguro que desea ELIMINAR los Permisos de "+txtNomPar.getText()+ "?")==0){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Eliminar datos de usuario
                stbSQL.append(" DELETE FROM tbm_equUsrIngSis WHERE co_usr=" + txtCodPar.getText() +";");
                stbSQL.append(" DELETE FROM tbr_usremp WHERE co_usr=" + txtCodPar.getText() +";");
                stbSQL.append(" DELETE FROM tbr_locUsr WHERE co_usr=" + txtCodPar.getText() +";");
                stbSQL.append(" DELETE FROM tbr_perUsr WHERE co_usr=" + txtCodPar.getText()+";");
                stbSQL.append(" DELETE FROM tbr_bodTipDocPrgUsr WHERE co_usr=" + txtCodPar.getText()+";");
                stbSQL.append(" DELETE FROM tbr_tipDocUsr WHERE co_usr=" + txtCodPar.getText()+";");
                stbSQL.append(" DELETE FROM tbr_tipDocDetUsr WHERE co_usr=" + txtCodPar.getText()+";");
                stbSQL.append(" DELETE FROM tbr_rptsisusr WHERE co_usr=" + txtCodPar.getText()+";");
                stbSQL.append(" DELETE FROM tbr_bodLocPrgUsr WHERE co_usr=" + txtCodPar.getText()+";");
                stbSQL.append(" DELETE FROM tbr_bodUsr WHERE co_usr=" + txtCodPar.getText()+";");
                stbSQL.append(" DELETE FROM tbm_regInv WHERE co_usr=" + txtCodPar.getText()+";");
                System.out.println("eliminarReg: " + stbSQL);
                stm.executeUpdate(stbSQL.toString());
                stm.close();
                stm=null;
                stbSQL=null;
                mostrarMsgInf("Permisos Eliminados Satisfactoriamente");
                }
            }else{
                  mostrarMsgInf("El Campo Eliminar/Para: debe contener datos");  
                }
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    
    /**
     * Esta función valida que no exista un usuario con la misma cedula de identidad
     * @return false: Si existe usuario registrado con la misma cedula
     * <BR>true: En el caso contrario.
     */
//    private boolean validaExiIdeUsr()
//    {
//        boolean blnRes=true;
//        try
//        {
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null)
//            {
//                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//                //Armar sentencia SQL
//                strSQL ="";
//                strSQL+=" SELECT tx_ced FROM tbm_usr WHERE tx_ced='" + txtCed.getText().trim() + "'";
//                rst=stm.executeQuery(strSQL);
//                if(rst.next())
//                {
//                    blnRes=false;
//                }                
//                stm.close();
//                stm=null;
//                rst.close();
//                rst=null;
//                con.close();
//                con=null;                
//            }
//        }
//        catch (java.sql.SQLException e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    } 
    
     /**
     * Esta función valida que no exista el mismo usuario
     * @return false: Si existe usuario registrado con el mismo user
     * <BR>true: En el caso contrario.
     */
//    private boolean validaExiTxtUsr()
//    {
//        boolean blnRes=true;
//        try
//        {
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if (con!=null)
//            {
//                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//                //Armar sentencia SQL
//                strSQL ="";
//                strSQL+=" SELECT tx_usr FROM tbm_usr WHERE tx_usr='" + txtUsr.getText().trim() + "'";
//                rst=stm.executeQuery(strSQL);
//                if(rst.next())
//                {
//                    blnRes=false;
//                }                
//                stm.close();
//                stm=null;
//                rst.close();
//                rst=null;
//                con.close();
//                con=null;                
//            }
//        }
//        catch (java.sql.SQLException e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e) {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }     
    
     /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgYesNo(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_OPTION, javax.swing.JOptionPane.YES_OPTION);
    }
    
    /**
     * Esta función permite reactivar un usuario
     * @return true: Si se pudo reactivar usuario
     * <BR>false: En el caso contrario.
     */
    private boolean reactivarUsr(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL=" UPDATE tbm_usr SET st_reg= 'A' WHERE co_usr = " + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR);
                System.out.println("reactivarUsr: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                objTooBar.setEstadoRegistro("Activo");
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite anular el registro seleccionado.
     * @return true: Si se pudo anular datos.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Anular datos de usuario
                stbSQL.append(" UPDATE tbm_usr SET st_reg='I' WHERE co_usr=" + objUti.getIntValueAt(arlDatConUsr, intIndiceUsr, INT_ARL_CON_USR_COD_USR)+";");
                System.out.println("anularReg: " + stbSQL);
                stm.executeUpdate(stbSQL.toString());
                stm.close();
                stm=null;
                stbSQL=null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     
    class RoundedBorder implements Border {

    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
    
         /**
     * Esta funcion permite Copiar los permisos de un usuario a otro
     * @return true: Si se pudo insertar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean CopiarPer(){
        boolean blnRes=true;
        try{
            String emp = objParSis.getStringConexion();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
          if(!txtCodDe.getText().equals("") || txtCodPar.getText().equals("") || txtNomDe.getText().equals("") ||  txtNomPar.getText().equals("") ){ // Validar campos de texto
                if(mostrarMsgYesNo("¿Está seguro que desea COPIAR los Permisos de "+ txtNomDe.getText() +" al Usuario de "+txtNomPar.getText()+" ?")==0){
                stm=con.createStatement();
                //int CodEmp = CodEmp();
                //int CodLoc = CodLoc();
                //Armar sentencia SQL
                stbSQL=new StringBuffer();
                //Equipos Por usuarios
                stbSQL.append(" INSERT INTO tbm_equUsrIngSis(co_usr, co_reg, tx_tipSis, tx_dirIp, tx_macAdd)"
                             +"SELECT "+txtCodPar.getText() +" as co_usr, a.co_reg, a.tx_tipSis, a.tx_dirIp, a.tx_macAdd"
                             +" FROM("
                             +"SELECT co_reg, tx_tipSis, tx_dirIp, tx_macAdd"
                             +" FROM tbm_equUsrIngSis"
                             +" WHERE co_usr= "+txtCodDe.getText()
                             +" EXCEPT"       
                             +" SELECT co_reg, tx_tipSis, tx_dirIp, tx_macAdd "
                             +"FROM tbm_equUsrIngSis " 
                             +" WHERE co_usr=" + txtCodPar.getText()
                             +") as a  " 
                             +"ORDER BY a.co_reg;"+"\n");
                            System.out.println("stb 1: "+stbSQL);
                //Empresas
                             stbSQL.append(" INSERT INTO tbr_usremp"
                             +"("
                             +" SELECT co_emp,"+txtCodPar.getText() +" as co_usr, st_ven, st_com, ne_nummaxvencon, nd_monmaxvencon, "
                             +" ne_nummaxcomcon, nd_monmaxcomcon, tx_obs1, tx_obs2, st_reg, fe_ing, "
                             +" fe_ultmod, co_usring, co_usrmod"
                             +" FROM tbr_usremp"
                             +" WHERE co_usr="+ txtCodDe.getText()
                             +" except"
                             +" select co_emp, co_usr, st_ven, st_com, ne_nummaxvencon, nd_monmaxvencon, "
                             +" ne_nummaxcomcon, nd_monmaxcomcon, tx_obs1, tx_obs2, st_reg, fe_ing, "   
                             +" fe_ultmod, co_usring, co_usrmod " 
                             +" FROM tbr_usremp " 
                             +" WHERE co_usr=" + txtCodPar.getText() 
                             +") ; "  
                             );
                             System.out.println("stb 2: "+stbSQL);
               //Locales
                            stbSQL.append("insert into tbr_locUsr"
                             +"("
                             +" select co_emp, co_loc, "+txtCodPar.getText()+" AS co_usr, st_reg"
                             +" FROM tbr_locUsr"
                             +" WHERE co_usr=" + txtCodDe.getText()
                             +" except"
                             +" select co_emp, co_loc, co_usr, st_reg"
                             +" FROM tbr_locUsr"
                             +" WHERE co_usr=" + txtCodPar.getText()
                             +");"+"\n"
                            );
                            System.out.println("stb 3: "+stbSQL);
                /*PERMISOS*/
                            stbSQL.append("insert into tbr_perUsr"
                             +"("
                             +" select a1.co_emp, a1.co_loc, "+txtCodPar.getText() +"AS co_usr, a1.co_mnu"
                             +" FROM tbr_perUsr AS a1 INNER JOIN tbm_mnusis AS a2"
                             +" ON a1.co_mnu=a2.co_mnu"
                             +" WHERE a1.co_usr=" +txtCodDe.getText()
                             +" except"
                             +" select co_emp, co_loc, co_usr, co_mnu"
                             +" FROM tbr_perUsr"
                             +" WHERE co_usr=" +txtCodPar.getText()
                             +");"+"\n"
                            );
                            System.out.println("stb 4: "+stbSQL);
                            
                    //Tipos de Documentos        
                            stbSQL.append("insert into tbr_tipDocUsr"
                            +"("
                            +" select co_emp, co_loc, co_tipdoc, co_mnu, "+ txtCodPar.getText()+" as co_usr, st_reg, ne_tipresmodfecdoc, ne_tipresmoddoc"       
                            +" from tbr_tipDocUsr"
                            +" where co_usr=" + txtCodDe.getText()
                            +" except"
                            +" select co_emp, co_loc, co_tipdoc, co_mnu, co_usr, st_reg, ne_tipresmodfecdoc, ne_tipresmoddoc"
                            +" from tbr_tipDocUsr"
                            +" where co_usr="+ txtCodPar.getText()
                            +");"+"\n"
                            );
                            System.out.println("stb 5: "+stbSQL);
                    //Tipos de Documentos (detalle)        
                            stbSQL.append("insert into tbr_tipDocDetUsr"
                            +"("
                            +" select co_emp, co_loc, co_tipdoc, co_mnu, "+ txtCodPar.getText()+" as co_usr, st_reg"
                            +" from tbr_tipDocDetUsr"
                            +" where co_usr="+ txtCodDe.getText()
                            +" except"
                            +" select co_emp, co_loc, co_tipdoc, co_mnu, co_usr, st_reg"
                            +" from tbr_tipDocDetUsr"
                            +" where co_usr="+ txtCodPar.getText()
                            +");"   +"\n"
                            );
                            System.out.println("stb 6: "+stbSQL);
                    //Reportes
                           stbSQL.append("insert into tbr_rptsisusr"
                           +"("
                           +" select co_emp, co_loc, co_rpt, co_mnu, "+ txtCodPar.getText()+" AS co_usr, ne_ord, st_reg"
                           +" from tbr_rptsisusr where co_usr="+ txtCodDe.getText()
                           +" except"
                           +" select co_emp, co_loc, co_rpt, co_mnu, co_usr, ne_ord, st_reg"
                           +" from tbr_rptsisusr where co_usr="+ txtCodPar.getText()
                           +");"+"\n"
                           );
                           System.out.println("stb 7: "+stbSQL);
                   //Bodegas
                           stbSQL.append("insert into tbr_bodLocPrgUsr"
                           +"("
                           +" select co_emp, co_loc, co_mnu, " + txtCodPar.getText()+" as co_usr, co_bod, st_reg, tx_natBod"
                           +" from tbr_bodLocPrgUsr"
                           +" where co_usr=" +txtCodDe.getText()
                           +" except"
                           +" select co_emp, co_loc, co_mnu, co_usr, co_bod, st_reg, tx_natBod"
                           +" from tbr_bodLocPrgUsr"
                           +" where co_usr=" +txtCodPar.getText()
                           +");"+"\n"
                           );
                    System.out.println("stb 8: "+stbSQL);
                           
                           stbSQL.append("insert into tbr_bodTipDocPrgUsr"
                           +"("
                           +" select co_emp, co_loc, co_tipdoc, co_mnu, "+ txtCodPar.getText()+" as co_usr, co_bod, tx_natBod, st_reg"
                           +" from tbr_bodTipDocPrgUsr"
                           +" where co_usr="+ txtCodDe.getText()
                           +" except"
                           +" select co_emp, co_loc, co_tipdoc, co_mnu, co_usr, co_bod, tx_natBod, st_reg"
                           +" from tbr_bodTipDocPrgUsr"
                           +" where co_usr="+ txtCodPar.getText()
                           +");"+"\n"
                           );
                           System.out.println("stb 9: "+stbSQL);
                           
                           
                    strSQL="";
                    strSQL+=" SELECT a1.*, a2.co_grp as co_grpUsr, a2.tx_desLar as tx_nomGrpUsr";
                    strSQL+=" FROM tbm_usr as a1";
                    strSQL+=" INNER JOIN tbm_grpUsr as a2 ON a1.co_grpUsr=a2.co_grp";
                    strSQL+=" WHERE a1.co_usr=" + txtCodPar.getText();
                    rst=stm.executeQuery(strSQL);
                    if (rst.next())
                    {
                   
                    strAux=rst.getString("co_grpUsr");
                    if(strAux.equals("9")){
                      
                             //Solo Bodegeros
                           stbSQL.append("INSERT INTO tbr_bodUsr"
                           +"("
                           +"co_emp, co_bod, co_usr, st_reg"
                           +")"
                           +"VALUES"
                           +"(0,15,"+Integer.parseInt(txtCodPar.getText())+",'A'"
                           +");"
                           );
                            System.out.println("stb 10: "+stbSQL);
                    
                    }
          
                   // objTooBar.setEstadoRegistro(strAux);
                }
//                else{
//                    objTooBar.setEstadoRegistro("Eliminado");
//                    limpiarFrm();
//                    blnRes=false;
//                }
                 

            //stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.*, a2.co_grp as co_grpUsr, a2.tx_desLar as tx_nomGrpUsr";
                strSQL+=" FROM tbm_usr as a1";
                strSQL+=" INNER JOIN tbm_grpUsr as a2 ON a1.co_grpUsr=a2.co_grp";
                strSQL+=" WHERE a1.co_usr=" + txtCodPar.getText();
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   
                   strAux=rst.getString("co_grpUsr");
                    if(strAux.equals("3")){
                        //SOLO PARA VENDEDORES
                    //Reglas de Inventario
                           stbSQL.append("INSERT INTO tbm_regInv(co_reg, co_emp, co_loc, co_tipdoc, co_usr, co_cri, tx_cad, st_reg, fe_ing, co_usring, st_tipmov)"
                           +" SELECT a.co_reg, a.co_emp, a.co_loc, a.co_tipdoc, "+ txtCodPar.getText()+" as co_usr, a.co_cri, a.tx_cad, a.st_reg, current_timestamp as fe_ing, 1 as co_usring, a.st_tipmov "
                           +" FROM("
                           +" SELECT co_reg, co_emp, co_loc, co_tipdoc, co_cri, tx_cad, st_reg, st_tipmov"
                           +" FROM tbm_regInv "
                           +" WHERE co_usr="+ txtCodDe.getText()
                           +" except"
                           +" SELECT co_reg, co_emp, co_loc, co_tipdoc, co_cri, tx_cad, st_reg, st_tipmov"
                           +" FROM tbm_regInv "
                           +" WHERE co_usr="+ txtCodPar.getText()
                           +" ) as a "
                           +" ORDER BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_Reg, a.tx_Cad;"
                           );
                           System.out.println("stb 10: "+stbSQL);
                           
                           
                           
                    }
          
                   // objTooBar.setEstadoRegistro(strAux);
                }
                stm.executeUpdate(stbSQL.toString()); // Cuando no aqui y bota excepcion
                stm.close();
                stm=null;
                stbSQL=null;
                mostrarMsgInf("Permisos Copiados Satisfactoriamente");
                }
                //Termino de iff 
          } //termino if validar campos
          else{
             mostrarMsgInf("Error: Los Campos deben tener Informacion");
          }
          
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
}
